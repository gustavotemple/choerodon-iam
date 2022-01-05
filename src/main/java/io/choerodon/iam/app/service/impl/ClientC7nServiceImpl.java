package io.choerodon.iam.app.service.impl;

import static io.choerodon.iam.infra.utils.SagaTopic.Organization.DELETE_CLIENT;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.hzero.iam.api.dto.MemberRoleSearchDTO;
import org.hzero.iam.app.service.ClientService;
import org.hzero.iam.app.service.MemberRoleService;
import org.hzero.iam.app.service.RoleService;
import org.hzero.iam.domain.entity.Client;
import org.hzero.iam.domain.entity.MemberRole;
import org.hzero.iam.domain.repository.ClientRepository;
import org.hzero.iam.domain.repository.RoleRepository;
import org.hzero.iam.infra.constant.Constants;
import org.hzero.iam.infra.constant.HiamMemberType;
import org.hzero.iam.infra.mapper.ClientMapper;
import org.hzero.iam.infra.mapper.MemberRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import io.choerodon.asgard.saga.annotation.Saga;
import io.choerodon.asgard.saga.producer.StartSagaBuilder;
import io.choerodon.asgard.saga.producer.TransactionalProducer;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.iam.api.eventhandler.payload.ClientPayload;
import io.choerodon.iam.api.vo.ClientRoleQueryVO;
import io.choerodon.iam.api.vo.ClientVO;
import io.choerodon.iam.app.service.ClientC7nService;
import io.choerodon.iam.infra.asserts.ClientAssertHelper;
import io.choerodon.iam.infra.constant.MemberRoleConstants;
import io.choerodon.iam.infra.constant.MisConstants;
import io.choerodon.iam.infra.dto.OauthClientResourceDTO;
import io.choerodon.iam.infra.mapper.ClientC7nMapper;
import io.choerodon.iam.infra.mapper.OauthClientResourceMapper;
import io.choerodon.iam.infra.utils.CommonExAssertUtil;
import io.choerodon.iam.infra.utils.ParamUtils;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;


/**
 * @author scp
 * @since 2020/3/27
 */
@Service
public class ClientC7nServiceImpl implements ClientC7nService {
    private static final String ORGANIZATION_ID_NOT_EQUAL_EXCEPTION = "error.organizationId.not.same";

    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ClientC7nMapper clientC7nMapper;
    @Autowired
    private ClientAssertHelper clientAssertHelper;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    @Lazy
    private RoleRepository roleRepository;
    @Autowired
    private MemberRoleMapper memberRoleMapper;
    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OauthClientResourceMapper oauthClientResourceMapper;
    @Autowired
    private TransactionalProducer producer;


    @Override
    public Client getDefaultCreateData(Long orgId) {
        Client client = new Client();
        client.setName(generateUniqueName());
        client.setSecret(RandomStringUtils.randomAlphanumeric(16));
        return client;
    }

    @Override
    public Client queryByName(Long orgId, String clientName) {
        Client dto = clientAssertHelper.clientNotExisted(clientName);
        if (!orgId.equals(dto.getOrganizationId())) {
            throw new CommonException(ORGANIZATION_ID_NOT_EQUAL_EXCEPTION);
        }
        return dto;
    }

    @Override
    public Page<Client> pagingQueryUsersByRoleId(PageRequest pageRequest, ResourceLevel resourceType, Long sourceId, ClientRoleQueryVO clientRoleQueryVO, Long roleId) {
        String param = Optional.ofNullable(clientRoleQueryVO).map(dto -> ParamUtils.arrToStr(dto.getParam())).orElse(null);
        return PageHelper.doPage(pageRequest, () -> clientC7nMapper.selectClientsByRoleIdAndOptions(roleId, sourceId, resourceType.value(), clientRoleQueryVO, param));
    }

    @Override
    public void assignRoles(Long organizationId, Long clientId, List<Long> newRoleIds) {
        Client client = clientRepository.selectByPrimaryKey(clientId);
        CommonExAssertUtil.assertTrue(organizationId.equals(client.getOrganizationId()), MisConstants.ERROR_OPERATING_RESOURCE_IN_OTHER_ORGANIZATION);

        List<Long> existingRoleIds = roleRepository.selectMemberRoles(clientId, HiamMemberType.CLIENT, new MemberRoleSearchDTO(), new PageRequest(0, 0)).getContent().stream().map(org.hzero.iam.domain.vo.RoleVO::getId).collect(Collectors.toList());
        //交集，传入的roleId与数据库里存在的roleId相交
        List<Long> intersection = existingRoleIds.stream().filter(newRoleIds::contains).collect(Collectors.toList());
        //传入的roleId与交集的差集为要插入的roleId
        List<Long> insertList = newRoleIds.stream().filter(item ->
                !intersection.contains(item)).collect(Collectors.toList());
        //数据库存在的roleId与交集的差集为要删除的roleId
        List<Long> deleteList = existingRoleIds.stream().filter(item ->
                !intersection.contains(item)).collect(Collectors.toList());

        List<MemberRole> addMemberRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(insertList)) {
            insertList.forEach(t -> addMemberRoles.add(getMemberRole(clientId, t, organizationId)));
            memberRoleService.batchAssignMemberRoleInternal(addMemberRoles);
        }
        List<MemberRole> delMemberRoles = new ArrayList<>();

        if (!CollectionUtils.isEmpty(deleteList)) {
            // 删除 脏数据
            List<Long> tempList = new ArrayList<>(deleteList);
            tempList.forEach(t -> {
                if (!roleService.selectRoleDetails(t).getTenantId().equals(organizationId)) {
                    MemberRole memberRole = new MemberRole();
                    memberRole.setRoleId(t);
                    memberRole.setMemberId(clientId);
                    memberRole.setMemberType(HiamMemberType.CLIENT.value());
                    // 防止数据删多
                    memberRoleMapper.deleteByPrimaryKey(memberRoleMapper.selectOne(memberRole).getId());
                    deleteList.remove(t);
                }
            });
            if (!CollectionUtils.isEmpty(deleteList)) {
                deleteList.forEach(t -> {
                    MemberRole delMemberRole = getMemberRole(clientId, t, organizationId);
                    delMemberRoles.add(delMemberRole);
                });
                memberRoleService.batchDeleteMemberRole(organizationId, delMemberRoles);
            }
        }
    }

    @Override
    public MemberRole getMemberRole(Long clientId, Long roleId, Long organizationId) {
        MemberRole memberRole = new MemberRole();
        memberRole.setMemberId(clientId);
        memberRole.setMemberType(Constants.MemberType.CLIENT);
        memberRole.setRoleId(roleId);
        memberRole.setSourceType(ResourceLevel.ORGANIZATION.value());
        memberRole.setSourceId(organizationId);
        memberRole.setAssignLevelValue(organizationId);
        Map<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(MemberRoleConstants.MEMBER_TYPE, MemberRoleConstants.MEMBER_TYPE_CHOERODON);
        memberRole.setAdditionalParams(additionalParams);
        return memberRole;
    }

    private String generateUniqueName() {
        String uniqueName;
        Client dto = new Client();
        while (true) {
            uniqueName = RandomStringUtils.randomAlphanumeric(12);
            dto.setName(uniqueName);
            if (clientMapper.selectOne(dto) == null) {
                break;
            }
        }
        return uniqueName;
    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    public ClientVO create(ClientVO clientVO) {
        Client clientToCreate = new Client();
        clientToCreate.setMenuIdFlag(0);
        BeanUtils.copyProperties(clientVO, clientToCreate);
        clientService.create(clientToCreate);
        if (clientVO.getSourceId() != null) {
            OauthClientResourceDTO oauthClientResourceDTO = new OauthClientResourceDTO();
            oauthClientResourceDTO.setClientId(clientToCreate.getId());
            oauthClientResourceDTO.setSourceId(clientVO.getSourceId());
            oauthClientResourceDTO.setSourceType(clientVO.getSourceType());
            if (oauthClientResourceMapper.insertSelective(oauthClientResourceDTO) != 1) {
                throw new CommonException("error.clientResource.create");
            }
        }
        clientVO.setId(clientToCreate.getId());
        return clientVO;
    }

    @Override
    @Transactional
    @Saga(code = DELETE_CLIENT,
            description = "删除客户端角色", inputSchema = "{}")
    public void delete(Long tenantId, Long clientId) {
        Client clientToDelete = clientRepository.selectByPrimaryKey(clientId);
        if (clientToDelete == null) {
            return;
        }
        CommonExAssertUtil.assertTrue(tenantId.equals(clientToDelete.getOrganizationId()), MisConstants.ERROR_OPERATING_RESOURCE_IN_OTHER_ORGANIZATION);
        Client client = new Client();
        client.setOrganizationId(tenantId);
        client.setId(clientId);
        client.setName(clientToDelete.getName());
        clientService.delete(client);
        OauthClientResourceDTO oauthClientResourceDTO = new OauthClientResourceDTO();
        oauthClientResourceDTO.setClientId(clientId);
        oauthClientResourceMapper.delete(oauthClientResourceDTO);
        sendEvent(clientId, tenantId);
    }


    public void sendEvent(Long clientId, Long tenantId) {
        try {
            ClientPayload clientPayload = new ClientPayload(clientId, tenantId);
            producer.apply(StartSagaBuilder.newBuilder()
                            .withSagaCode(DELETE_CLIENT)
                            .withPayloadAndSerialize(clientPayload)
                            .withRefType("users")
                            .withRefId(clientId + "")
                            .withLevel(ResourceLevel.ORGANIZATION)
                            .withSourceId(tenantId),
                    builder -> {
                    });
        } catch (Exception e) {
            throw new CommonException("error.iRoleMemberServiceImpl.updateMemberRole.event", e);
        }
    }

    @Override
    public void deleteClientRole(Long clientId, Long tenantId) {
        // 删除client对应角色
        List<MemberRole> memberRoleList = new ArrayList<>();
        List<Long> existingRoleIds = roleRepository.selectMemberRoles(clientId, HiamMemberType.CLIENT, new MemberRoleSearchDTO(), new PageRequest(0, 0)).getContent().stream().map(org.hzero.iam.domain.vo.RoleVO::getId).collect(Collectors.toList());
        existingRoleIds.forEach(t -> {
            MemberRole delMemberRole = getMemberRole(clientId, t, tenantId);
            memberRoleList.add(delMemberRole);
        });
        memberRoleService.batchDeleteMemberRole(tenantId, memberRoleList);
    }

    @Override
    public Page<Client> pageClient(Long organizationId, String name, String params, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> clientC7nMapper.listClientsByTenantId(organizationId, name, params));
    }
}

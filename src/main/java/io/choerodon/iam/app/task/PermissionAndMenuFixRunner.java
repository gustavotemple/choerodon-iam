package io.choerodon.iam.app.task;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hzero.iam.domain.entity.Role;
import org.hzero.iam.domain.entity.RolePermission;
import org.hzero.iam.infra.constant.Constants;
import org.hzero.iam.infra.constant.RolePermissionType;
import org.hzero.iam.infra.mapper.RolePermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.choerodon.core.exception.CommonException;
import io.choerodon.iam.app.service.FixService;
import io.choerodon.iam.app.service.PermissionC7nService;
import io.choerodon.iam.infra.enums.RoleLabelEnum;
import io.choerodon.iam.infra.mapper.RoleC7nMapper;
import io.choerodon.iam.infra.mapper.RolePermissionC7nMapper;
import io.choerodon.iam.infra.utils.C7nCollectionUtils;


/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  16:44 2019/3/11
 * Description:
 */
@Component
public class PermissionAndMenuFixRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAndMenuFixRunner.class);

    @Autowired
    private RoleC7nMapper roleC7nMapper;
    @Autowired
    private RolePermissionC7nMapper rolePermissionC7nMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Value("${choerodon.fix.data.page.size:200}")
    private Integer pageSize;

    @Value("${choerodon.fix.data.page.sleep.time: 500}")
    private Integer sleepTime;
    @Value("${choerodon.fix.data.flag: true}")
    private Boolean fixDataFlag;
    @Autowired
    private PermissionC7nService permissionC7nService;
    @Autowired
    private FixService fixService;

    @Override
    public void run(String... strings) {
        try {
            if (Boolean.TRUE.equals(fixDataFlag)) {
                // 修复菜单层级
                fixService.fixMenuLevelPath(true);
                // 修复子角色权限（保持和模板角色权限一致）
                LOGGER.info(">>>>>>>>>>>>>>> start fix role permission >>>>>>>>>>>>>>");
                fixChildPermission();
                LOGGER.info(">>>>>>>>>>>>>>>>>>> end fix role permission >>>>>>>>>>>>>>>>>>>>>>");
            }
        } catch (Exception e) {
            throw new CommonException("error.fix.role.permission.data", e);
        }

    }

    private void fixChildPermission() {
        // 查询模板角色
        List<Role> tplRoles = roleC7nMapper.listByLabelNames(0L, RoleLabelEnum.TENANT_ROLE_TPL.value());

        tplRoles.forEach(tplRole -> {
            // 查询模板角色拥有的权限
            List<RolePermission> tplPs = rolePermissionC7nMapper.listRolePermissionIds(tplRole.getId());
            Set<Long> tplPsIds = tplPs.stream().map(RolePermission::getPermissionSetId).collect(Collectors.toSet());
            Map<Long, RolePermission> tplPsMap = tplPs.stream().collect(Collectors.toMap(RolePermission::getPermissionSetId, v -> v));
            // 查询模板子角色
            List<Role> childRoles = roleC7nMapper.listChildRoleByTplRoleId(tplRole.getId());

            // 修复子角色权限
            childRoles.forEach(childRole -> {
                List<RolePermission> childPs = rolePermissionC7nMapper.listRolePermissionIds(childRole.getId());
                Map<Long, RolePermission> childPsMap = childPs.stream().collect(Collectors.toMap(RolePermission::getPermissionSetId, v -> v));
                Set<Long> childPsIds = childPs.stream().map(RolePermission::getPermissionSetId).collect(Collectors.toSet());

                Set<Long> addPsIds = new HashSet<>();
                Set<Long> delPsIds = new HashSet<>();
//                List<RolePermission> updateRolePsList = new ArrayList<>();

                if (CollectionUtils.isEmpty(tplPsIds)) {
                    delPsIds = childPsIds;
                } else {
                    addPsIds = tplPsIds.stream().filter(id -> !childPsIds.contains(id)
                            && StringUtils.equals(Constants.YesNoFlag.YES, tplPsMap.get(id).getCreateFlag()))
                            .collect(Collectors.toSet());
                    delPsIds = childPsIds.stream().filter(id -> !tplPsIds.contains(id)
                            || StringUtils.equals(Constants.YesNoFlag.DELETE, tplPsMap.get(id).getCreateFlag()))
                            .collect(Collectors.toSet());
//                    updateRolePsList = childPs.stream()
//                            .filter(ps -> !StringUtils.equals(ps.getInheritFlag(), tplPsMap.get(ps.getPermissionSetId()).getCreateFlag()))
//                            .map(ps -> {
//                                RolePermission rolePermission = ConvertUtils.convertObject(ps, RolePermission.class);
//                                String createFlag = StringUtils.equals(Constants.YesNoFlag.DELETE, tplPsMap.get(ps.getId()).getCreateFlag()) ? Constants.YesNoFlag.DELETE : Constants.YesNoFlag.NO;
//                                String inheritFlag = StringUtils.equals(Constants.YesNoFlag.DELETE, tplPsMap.get(ps.getId()).getCreateFlag()) ? Constants.YesNoFlag.DELETE : Constants.YesNoFlag.YES;
//                                rolePermission.setCreateFlag(createFlag);
//                                rolePermission.setInheritFlag(inheritFlag);
//                                return rolePermission;
//                            })
//                            .collect(Collectors.toList());
                }

                // 删除子角色权限
                if (!CollectionUtils.isEmpty(delPsIds)) {
                    // 要删除的role-permission-id
                    Set<Long> delRpIds = new HashSet<>();
                    delPsIds.forEach(id -> {
                        RolePermission rolePermission = childPsMap.get(id);
                        delRpIds.add(rolePermission.getId());
                    });
                    rolePermissionC7nMapper.batchDeleteById(delRpIds);
                }

                // 更新子角色权限
//                if (!CollectionUtils.isEmpty(updateRolePsList)) {
//                    // 要删除的role-permission-id
//                    updateRolePsList.forEach(ps -> {
//                        rolePermissionMapper.updateByPrimaryKeySelective(ps);
//                    });
//                }

                // 新增子角色权限
                List<RolePermission> rolePermissionList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(addPsIds)) {
                    addPsIds.forEach(id -> {
                        RolePermission rolePermission = new RolePermission();
//                        String createFlag = StringUtils.equals(Constants.YesNoFlag.DELETE, tplPsMap.get(id).getCreateFlag()) ? Constants.YesNoFlag.DELETE : Constants.YesNoFlag.NO;
//                        String inheritFlag = StringUtils.equals(Constants.YesNoFlag.DELETE, tplPsMap.get(id).getCreateFlag()) ? Constants.YesNoFlag.DELETE : Constants.YesNoFlag.YES;
                        rolePermission.setCreateFlag(Constants.YesNoFlag.NO);
                        rolePermission.setInheritFlag(Constants.YesNoFlag.YES);
                        rolePermission.setRoleId(childRole.getId());
                        rolePermission.setPermissionSetId(id);
                        rolePermission.setType(RolePermissionType.PS.name());
                        rolePermission.setCreationDate(new Date());
                        rolePermission.setCreatedBy(0L);
                        rolePermission.setLastUpdateDate(new Date());
                        rolePermission.setLastUpdatedBy(0L);
                        rolePermissionList.add(rolePermission);
                    });

                    if (rolePermissionList.size() > pageSize) {

                        List<List<RolePermission>> fragmentList = C7nCollectionUtils.fragmentList(rolePermissionList, pageSize);
                        for (List<RolePermission> permissions : fragmentList) {
                            rolePermissionC7nMapper.batchInsert(permissions);
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                LOGGER.error(e.getMessage(), e);
                            }
                        }
                    } else {
                        rolePermissionC7nMapper.batchInsert(rolePermissionList);
                    }
                }
            });
        });
    }

}

package io.choerodon.iam.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hzero.iam.domain.entity.Menu;
import org.hzero.iam.domain.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.iam.app.service.ProjectPermissionService;
import io.choerodon.iam.app.service.RolePermissionC7nService;
import io.choerodon.iam.infra.dto.ProjectPermissionDTO;
import io.choerodon.iam.infra.mapper.RolePermissionC7nMapper;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2020/4/23 11:51
 */
@Service
public class RolePermissionC7nServiceImpl implements RolePermissionC7nService {

    private static final String ERROR_ROLE_ID_NOT_BE_NULL = "error.role.id.not.be.null";

    @Autowired
    private RolePermissionC7nMapper rolePermissionC7nMapper;
    @Autowired
    @Lazy
    private ProjectPermissionService projectPermissionService;


    @Override
    public List<RolePermission> listRolePermissionByRoleId(Long roleId) {
        Assert.notNull(roleId, ERROR_ROLE_ID_NOT_BE_NULL);
        return rolePermissionC7nMapper.listRolePermissionIds(roleId);
    }

    @Override
    @Transactional
    public void batchDelete(Long roleId, Set<Long> deletePermissionIds) {
        Assert.notNull(roleId, ERROR_ROLE_ID_NOT_BE_NULL);
        rolePermissionC7nMapper.batchDelete(roleId, deletePermissionIds);
    }

    @Override
    public List<Menu> listRolePermissionByRoleIdAndLabels(Long roleId, Set<String> labelNames) {
        return rolePermissionC7nMapper.listRolePermissionByRoleIdAndLabels(roleId, labelNames);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId) {
        Assert.notNull(roleId, ERROR_ROLE_ID_NOT_BE_NULL);
        rolePermissionC7nMapper.deleteByRoleId(roleId);
    }

    @Override
    public void assignUsersProjectRoles(Long projectId, List<ProjectPermissionDTO> projectUserDTOList) {
        Long operatorId = DetailsHelper.getUserDetails().getUserId();
        Map<Long, List<ProjectPermissionDTO>> map = projectUserDTOList.stream().collect(Collectors.groupingBy(ProjectPermissionDTO::getMemberId));
        map.forEach((k, v) -> projectPermissionService.addProjectRolesForUser(projectId, k, v.stream().map(ProjectPermissionDTO::getRoleId).collect(Collectors.toSet()), operatorId));
    }

}

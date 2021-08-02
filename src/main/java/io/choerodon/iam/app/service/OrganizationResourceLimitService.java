package io.choerodon.iam.app.service;

import io.choerodon.iam.api.vo.ResourceLimitVO;
import io.choerodon.iam.infra.dto.ProjectDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2020/5/19 22:08
 */
public interface OrganizationResourceLimitService {
    /**
     * 检查是否还能创建组织用户
     *
     * @param organizationId
     * @return true 或者 false
     */
    Boolean checkEnableCreateOrganizationUser(Long organizationId);

    /**
     * 检查是否还能创建项目用户
     *
     * @param projectId
     * @return true 或者 false
     */
    Boolean checkEnableCreateProjectUser(Long projectId);

    /**
     * 校验组织是否是注册组织
     *
     * @param tenantId
     * @return 默认值false
     */
    Boolean checkOrganizationIsRegister(Long tenantId);


    /**
     * 校验组织是否是saas注册组织
     *
     * @param tenantId
     * @return 默认值false
     */
    Boolean checkOrganizationIsSaas(Long tenantId);

    /**
     * 校验组织是否还能创建项目
     *
     * @param organizationId
     */
    void checkEnableCreateProjectOrThrowE(Long organizationId);

    /**
     * 检查组织下是否还能创建项目
     *
     * @param organizationId
     * @return true 或者 false
     */
    Boolean checkEnableCreateProject(Long organizationId);

    /**
     * 检查组织层是否还能创建用户
     *
     * @param organizationId
     * @param userNum
     */
    void checkEnableCreateUserOrThrowE(Long organizationId, int userNum);

    /**
     * 检查组织层是否还导入用户（给用户分配角色）
     *
     * @param organizationId
     * @param userNum
     */
    void checkEnableImportUserOrThrowE(Long organizationId, Long userId, int userNum);


    /**
     * 检查组织下是资源限制
     *
     * @param organizationId
     * @return
     */
    ResourceLimitVO queryResourceLimit(Long organizationId);


    /**
     * 检查能否创建该项目类型
     *
     * @param organizationId
     * @param projectDTO
     */
    void checkEnableCreateProjectType(Long organizationId, ProjectDTO projectDTO);

    /**
     * 检查能否启用、添加成员
     *
     * @param tenantId
     * @return
     */
    void checkEnableAddMember(Long tenantId);

}

package io.choerodon.iam.infra.mapper;

import io.choerodon.iam.api.vo.ProjectOverViewVO;
import io.choerodon.iam.api.vo.TenantVO;
import io.choerodon.iam.infra.dto.OrganizationSimplifyDTO;

import org.apache.ibatis.annotations.Param;
import org.hzero.iam.api.dto.TenantDTO;
import org.hzero.iam.domain.entity.Tenant;
import org.hzero.iam.domain.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author wuguokai
 */
public interface TenantC7nMapper {

    List<TenantVO> fulltextSearch(
            @Param("name") String name,
            @Param("code") String code,
            @Param("ownerRealName") String ownerRealName,
            @Param("enabled") Boolean enabled,
            @Param("homePage") String homePage,
            @Param("params") String params,
            @Param("isRegister") String isRegister);

    Set<TenantVO> selectFromMemberRoleByMemberId(@Param("memberId") Long memberId,
                                                 @Param("includedDisabled") Boolean includedDisabled);

    List<TenantVO> selectOrganizationsWithRoles(
            @Param("id") Long id,
            @Param("start") Integer start,
            @Param("size") Integer size,
            @Param("params") String params);


    Boolean organizationEnabled(@Param("sourceId") Long sourceId);


    /**
     * 获取 指定id范围 的 组织简要信息
     *
     * @param orgIds  指定的组织范围
     * @param name    组织名查询参数
     * @param code    组织编码查询参数
     * @param enabled 组织启停用查询参数
     * @param params  全局模糊搜索查询参数
     * @return 查询结果
     */
    List<Tenant> selectSpecified(@Param("orgIds") Set<Long> orgIds,
                                 @Param("name") String name,
                                 @Param("code") String code,
                                 @Param("enabled") Boolean enabled,
                                 @Param("params") String params);


    /**
     * 根据用户Id查询用户所属组织信息.
     *
     * @param userId 用户Id
     * @return 用户所属组织信息
     */
    Tenant selectOwnOrgByUserId(@Param("userId") Long userId);

    ProjectOverViewVO projectOverview(@Param("organizationId") Long organizationId);

    List<Long> getOrganizationByName(@Param("name") String name);

    List<Tenant> selectByIds(@Param("ids") Set<Long> ids);


    List<Tenant> selectByOrgIds(@Param("ids") Set<Long> ids);

    /**
     * 获取所有组织{id,name}
     *
     * @return 组织{id,name}
     */
    List<OrganizationSimplifyDTO> selectAllOrgIdAndName();

    /**
     * 查询用户可以看到的组织列表
     *
     * @param params params.userId必填
     * @return TenantDTO列表
     */
    List<TenantDTO> listVisibleTentant(@Param("params") TenantDTO params);

    /**
     * 查询组织下的所有用户
     *
     * @param tenantId
     * @return User列表
     */
    List<User> listMemberIds(@Param("tenantId") Long tenantId);

    Long queryCurrentUserNum(@Param("tenantId") Long tenantId);

    List<Long> querySingleTl();

    void insertTenantTl(@Param("tenantId") Long tenantId, @Param("lang") String lang, @Param("tenantName") String tenantName);

}

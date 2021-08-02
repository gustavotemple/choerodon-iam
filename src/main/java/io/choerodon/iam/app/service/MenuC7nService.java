package io.choerodon.iam.app.service;

import org.hzero.iam.domain.entity.Menu;

import java.util.List;
import java.util.Set;

import io.choerodon.iam.infra.dto.ProjectDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2020/4/23 17:36
 */
public interface MenuC7nService {

    List<Menu> listPermissionSetTree(Long tenantId, String menuLevel);

    List<Menu> listNavMenuTree(Set<String> labels, Long tenantId, Long projectId);

    List<Menu> listMenuByLabel(Set<String> labels);

    /**
     * 查询个人信息菜单,仅包含type = menu的
     *
     * @return menu列表
     */
    List<Menu> listUserInfoMenuOnlyTypeMenu();

    List<Menu> listMenuByLabelAndType(Set<String> labelNames, String type);

    List<Menu> listMenuByLevel(String code);

    Boolean hasSiteMenuPermission();

    void saveVisitInfo(ProjectDTO projectDTO);
}

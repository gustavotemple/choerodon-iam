package io.choerodon.iam.app.service.impl;

import static io.choerodon.iam.app.service.impl.ProjectC7nServiceImpl.ERROR_PROJECT_NOT_EXIST;
import static io.choerodon.iam.infra.constant.ProjectVisitInfoConstants.PROJECT_VISIT_INFO_KEY_TEMPLATE;
import static io.choerodon.iam.infra.constant.ProjectVisitInfoConstants.USER_VISIT_INFO_KEY_TEMPLATE;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.iam.api.dto.MenuTreeQueryDTO;
import org.hzero.iam.api.dto.RoleDTO;
import org.hzero.iam.domain.entity.Menu;
import org.hzero.iam.domain.entity.Role;
import org.hzero.iam.domain.entity.User;
import org.hzero.iam.domain.repository.MenuRepository;
import org.hzero.iam.domain.repository.UserRepository;
import org.hzero.iam.infra.common.utils.HiamMenuUtils;
import org.hzero.iam.infra.common.utils.UserUtils;
import org.hzero.iam.infra.constant.HiamResourceLevel;
import org.hzero.iam.infra.mapper.MenuMapper;
import org.hzero.iam.infra.mapper.UserMapper;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.websocket.helper.SocketSendHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.MenuType;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.iam.api.vo.ProjectVisitInfoVO;
import io.choerodon.iam.app.service.MenuC7nService;
import io.choerodon.iam.app.service.OrganizationProjectC7nService;
import io.choerodon.iam.app.service.ProjectC7nService;
import io.choerodon.iam.app.service.StarProjectService;
import io.choerodon.iam.infra.dto.ProjectCategoryDTO;
import io.choerodon.iam.infra.dto.ProjectDTO;
import io.choerodon.iam.infra.enums.MenuLabelEnum;
import io.choerodon.iam.infra.mapper.*;
import io.choerodon.iam.infra.utils.JsonHelper;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2020/4/23 17:36
 */
public class MenuC7nServiceImpl implements MenuC7nService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuC7nServiceImpl.class);
    private static final String USER_MENU = "USER_MENU";

    // 查询菜单的线程池
    private final ThreadPoolExecutor SELECT_MENU_POOL = new ThreadPoolExecutor(20, 180, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2000), new ThreadFactoryBuilder().setNameFormat("C7n-selMenuPool-%d").build());
    @Autowired
    private OrganizationProjectC7nService organizationProjectC7nService;
    @Autowired
    private SocketSendHelper socketSendHelper;

    protected MenuC7nMapper menuC7nMapper;
    protected MenuRepository menuRepository;
    protected ProjectMapCategoryMapper projectMapCategoryMapper;
    protected UserRepository userRepository;
    protected MenuMapper menuMapper;
    protected MemberRoleC7nMapper memberRoleC7nMapper;
    protected RoleC7nMapper roleC7nMapper;
    protected ProjectPermissionMapper projectPermissionMapper;
    protected ProjectC7nService projectC7nService;
    protected UserC7nMapper userC7nMapper;
    protected RedisTemplate<String, String> redisTemplate;
    protected ProjectMapper projectMapper;
    protected UserMapper userMapper;
    protected StarProjectService starProjectService;

    public MenuC7nServiceImpl(MenuC7nMapper menuC7nMapper,
                              ProjectMapCategoryMapper projectMapCategoryMapper,
                              MenuRepository menuRepository,
                              MenuMapper menuMapper,
                              UserRepository userRepository,
                              MemberRoleC7nMapper memberRoleC7nMapper,
                              RoleC7nMapper roleC7nMapper,
                              ProjectPermissionMapper projectPermissionMapper,
                              ProjectC7nService projectC7nService,
                              UserC7nMapper userC7nMapper,
                              RedisTemplate<String, String> redisTemplate,
                              ProjectMapper projectMapper,
                              UserMapper userMapper,
                              StarProjectService starProjectService
    ) {
        this.starProjectService = starProjectService;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
        this.redisTemplate = redisTemplate;
        this.menuC7nMapper = menuC7nMapper;
        this.projectMapCategoryMapper = projectMapCategoryMapper;
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.userRepository = userRepository;
        this.memberRoleC7nMapper = memberRoleC7nMapper;
        this.roleC7nMapper = roleC7nMapper;
        this.projectPermissionMapper = projectPermissionMapper;
        this.projectC7nService = projectC7nService;
        this.userC7nMapper = userC7nMapper;
    }

    @Override
    public List<Menu> listPermissionSetTree(Long tenantId, String menuLevel) {
        Set<String> labels = new HashSet<>();
        if (ResourceLevel.ORGANIZATION.value().equals(menuLevel)) {
            labels.add(MenuLabelEnum.TENANT_MENU.value());
            labels.add(MenuLabelEnum.TENANT_GENERAL.value());
        }
        if (ResourceLevel.PROJECT.value().equals(menuLevel)) {
            labels.add(MenuLabelEnum.N_GENERAL_PROJECT_MENU.value());
            labels.add(MenuLabelEnum.N_AGILE_MENU.value());
            labels.add(MenuLabelEnum.N_REQUIREMENT_MENU.value());
            labels.add(MenuLabelEnum.N_PROGRAM_PROJECT_MENU.value());
            labels.add(MenuLabelEnum.N_TEST_MENU.value());
            labels.add(MenuLabelEnum.N_DEVOPS_MENU.value());
            labels.add(MenuLabelEnum.N_OPERATIONS_MENU.value());
            labels.add(MenuLabelEnum.N_PROGRAM_MENU.value());
        }
        SecurityTokenHelper.close();
        Set<String> typeNames = new HashSet<>();
        typeNames.add(MenuType.ROOT.value());
        typeNames.add(MenuType.MENU.value());
        typeNames.add(MenuType.DIR.value());
        List<Menu> menus = menuC7nMapper.listMenuByLabelAndType(labels, typeNames);
        SecurityTokenHelper.clear();

        Set<Long> ids = menus.stream().map(Menu::getId).collect(Collectors.toSet());
        List<Menu> permissionSetList = menuC7nMapper.listPermissionSetByParentIds(ids);
        menus.addAll(permissionSetList);

        return menus;

    }

    @Override
    public List<Menu> listNavMenuTree(Set<String> labels, Long tenantId, Long projectId) {
        if (labels == null && projectId == null) {
            throw new CommonException("error.menu.params");
        }
        String finalLang = LanguageHelper.language();
        // 查询项目层菜单，（可以考虑单独抽出一个新接口）
        if (projectId != null) {
            ProjectDTO projectDTO = projectMapper.selectCategoryByPrimaryKey(projectId);
            if (projectDTO == null) {
                throw new CommonException(ERROR_PROJECT_NOT_EXIST);
            }
            // 查询用户在项目下的角色
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();

            List<Long> roleIds = new ArrayList<>();

            if (Boolean.TRUE.equals(userDetails.getAdmin())
                    || Boolean.TRUE.equals(userC7nMapper.isOrgAdministrator(projectDTO.getOrganizationId(), userDetails.getUserId()))) {
                Role tenantAdminRole = roleC7nMapper.getTenantAdminRole(projectDTO.getOrganizationId());
                roleIds.add(tenantAdminRole.getId());
            } else {
                List<RoleDTO> roleDTOS = projectPermissionMapper.listRolesByProjectIdAndUserId(projectId, userDetails.getUserId());
                if (CollectionUtils.isEmpty(roleDTOS)) {
                    throw new CommonException("error.not.project.member");
                }
                roleIds = roleDTOS.stream().map(RoleDTO::getId).collect(Collectors.toList());
            }
            saveVisitInfo(projectDTO);
            // 添加项目类型

            List<ProjectCategoryDTO> list = projectMapCategoryMapper.selectProjectCategoryNames(projectId);
            if (CollectionUtils.isEmpty(list)) {
                throw new CommonException("error.project.category");
            }

            // 查询角色的菜单
            Set<String> finalLabels1 = list.stream().map(ProjectCategoryDTO::getLabelCode).collect(Collectors.toSet());
            // 添加所有项目类型都能看到的菜单标签
            finalLabels1.add(MenuLabelEnum.N_GENERAL_PROJECT_MENU.value());

            List<Long> finalRoleIds = roleIds;
            CompletableFuture<List<Menu>> f1 = CompletableFuture.supplyAsync(() -> {
                SecurityTokenHelper.close();
                List<Menu> menus = this.menuMapper.selectLevelMenus(projectDTO.getOrganizationId(), finalLang, HiamResourceLevel.ORGANIZATION.value(), finalLabels1, true);
                SecurityTokenHelper.clear();
                return menus;
            }, SELECT_MENU_POOL);
            CompletableFuture<List<Menu>> cf = f1
                    // 转换成树形结构
                    .thenApply((menus) -> HiamMenuUtils.formatMenuListToTree(menus, Boolean.FALSE))
                    .exceptionally((e) -> {
                        LOGGER.warn("select menus error, ex = {}", e.getMessage(), e);
                        return Collections.emptyList();
                    });
            return cf.join();
        }

        if (labels.contains(USER_MENU)) {
            CompletableFuture<List<Menu>> f1;
            Set<String> finalLabels = new HashSet<>(labels);
            f1 = CompletableFuture.supplyAsync(() -> menuC7nMapper.selectUserMenus(finalLang, finalLabels), SELECT_MENU_POOL);
            CompletableFuture<List<Menu>> cf = f1
                    // 转换成树形结构
                    .thenApply((menus) -> HiamMenuUtils.formatMenuListToTree(menus, Boolean.FALSE))
                    .exceptionally((e) -> {
                        LOGGER.warn("select menus error, ex = {}", e.getMessage(), e);
                        return Collections.emptyList();
                    });
            return cf.join();
        } else {
            // 组织平台层菜单调用
            MenuTreeQueryDTO menuTreeQueryDTO = new MenuTreeQueryDTO();
            menuTreeQueryDTO.setLabels(labels);
            menuTreeQueryDTO.setLang(finalLang);
            menuTreeQueryDTO.setUnionLabel(true);
            return menuRepository.selectRoleMenuTree(menuTreeQueryDTO);
        }

    }

    @Override
    public List<Menu> listMenuByLabel(Set<String> labels) {
        return menuC7nMapper.listMenuByLabel(labels);
    }

    @Override
    public List<Menu> listUserInfoMenuOnlyTypeMenu() {
        return menuC7nMapper.listUserInfoMenuOnlyTypeMenu();
    }

    @Override
    public List<Menu> listMenuByLabelAndType(Set<String> labelNames, String type) {
        Set<String> typeNames = new HashSet<>();
        typeNames.add(type);
        return menuC7nMapper.listMenuByLabelAndType(labelNames, typeNames);
    }


    @Override
    public List<Menu> listMenuByLevel(String code) {
        code = getCode(code);
        Set<String> labelNames = new HashSet<>();
        if (ResourceLevel.SITE.value().equals(code)) {
            labelNames.add(MenuLabelEnum.SITE_MENU.value());
        }
        if (ResourceLevel.ORGANIZATION.value().equals(code)) {
            labelNames.add(MenuLabelEnum.TENANT_MENU.value());
        }
        if (ResourceLevel.PROJECT.value().equals(code)) {
            labelNames.add(MenuLabelEnum.N_GENERAL_PROJECT_MENU.value());
            labelNames.add(MenuLabelEnum.N_AGILE_MENU.value());
            labelNames.add(MenuLabelEnum.N_REQUIREMENT_MENU.value());
            labelNames.add(MenuLabelEnum.N_PROGRAM_PROJECT_MENU.value());
            labelNames.add(MenuLabelEnum.N_TEST_MENU.value());
            labelNames.add(MenuLabelEnum.N_DEVOPS_MENU.value());
            labelNames.add(MenuLabelEnum.N_OPERATIONS_MENU.value());
            labelNames.add(MenuLabelEnum.N_PROGRAM_MENU.value());
        }
        if (ResourceLevel.USER.value().equals(code)) {
            labelNames.add(MenuLabelEnum.USER_MENU.value());
        }
        Set<String> typeNames = new HashSet<>();
        typeNames.add(MenuType.MENU.value());
        return menuC7nMapper.listMenuByLabelAndType(labelNames, typeNames);
    }

    @Override
    public Boolean hasSiteMenuPermission() {
        CustomUserDetails userDetails = UserUtils.getUserDetails();
        User user = userRepository.selectByPrimaryKey(userDetails.getUserId());

        // root用户能够访问
        if (Boolean.TRUE.equals(user.getAdmin())) {
            return true;
        }

        // 拥有平台层菜单也能访问
        List<Role> roleList = memberRoleC7nMapper.listRoleByUserIdAndLevel(user.getId(), ResourceLevel.SITE.value());
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        }

        Set<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toSet());
        long menuCount = menuC7nMapper.countPermissionSetByRoleIdsAndLevel(roleIds, ResourceLevel.SITE.value());
        return menuCount > 0;
    }

    @Override
    public void saveVisitInfo(ProjectDTO projectDTO) {
        if (projectDTO != null) {
            Long userId = DetailsHelper.getUserDetails().getUserId();
            String userVisitInfoKey = String.format(USER_VISIT_INFO_KEY_TEMPLATE, userId, projectDTO.getOrganizationId());
            String projectVisitInfoKey = String.format(PROJECT_VISIT_INFO_KEY_TEMPLATE, projectDTO.getId());
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(userVisitInfoKey);

            ProjectVisitInfoVO projectVisitInfoVO = new ProjectVisitInfoVO()
                    .setLastVisitTime(new Date())
                    .setProjectId(projectDTO.getId());
            entries.put(projectVisitInfoKey, JsonHelper.marshalByJackson(projectVisitInfoVO));
            redisTemplate.opsForHash().putAll(userVisitInfoKey, entries);

            socketSendHelper.sendByUserId(userId, "latest_visit", JsonHelper.marshalByJackson(organizationProjectC7nService.queryLatestVisitProjectInfo(projectDTO.getOrganizationId())));
        }
    }

    private String getCode(String code) {
        int index = code.lastIndexOf('.');
        return code.substring(index + 1);
    }

}

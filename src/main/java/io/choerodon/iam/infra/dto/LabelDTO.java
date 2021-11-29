package io.choerodon.iam.infra.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hzero.iam.domain.entity.Label;

/**
 * @author superlee
 * @since 2019-04-23
 */
public class LabelDTO extends Label {
    @ApiModelProperty(value = "是否获取gitlab相关标签")
    private Boolean gitlabLabel;

    @ApiModelProperty(value = "roleId")
    private Long roleId;

    public LabelDTO() {
    }

    public Boolean getGitlabLabel() {
        return gitlabLabel;
    }

    public void setGitlabLabel(Boolean gitlabLabel) {
        this.gitlabLabel = gitlabLabel;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

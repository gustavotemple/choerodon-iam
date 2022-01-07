package io.choerodon.iam.api.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zmf
 * @since 2020/12/30
 */
public class UserCountAllVO {
    @ApiModelProperty("用户的数量")
    private Integer count;

    public UserCountAllVO() {
    }

    public UserCountAllVO(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

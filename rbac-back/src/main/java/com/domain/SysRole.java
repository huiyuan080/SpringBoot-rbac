package com.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huiyuan080
 * @date 2023/3/15
 * @apiNote
 */

/**
 * 角色信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 显示顺序
     */
    private Integer roleSort;
    /**
     * 角色备注
     */
    private String remark;
    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;
    /**
     * 删除标志（0存在 2删除）
     */
    private Integer delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
}
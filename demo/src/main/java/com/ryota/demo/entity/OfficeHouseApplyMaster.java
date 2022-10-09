package com.ryota.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 办公用房申请主表
 * </p>
 *
 * @author ryota
 * @since 2022-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OfficeHouseApplyMaster对象", description="办公用房申请主表")
public class OfficeHouseApplyMaster implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("OLD_ID")
    private String oldId;

    @ApiModelProperty(value = "填报人")
    @TableField("FILLED_BY")
    private String filledBy;

    @ApiModelProperty(value = "填报单位")
    @TableField("FILLED_BY_ORG")
    private String filledByOrg;

    @ApiModelProperty(value = "填报单位ID")
    @TableField("FILLED_BY_ORG_ID")
    private String filledByOrgId;

    @ApiModelProperty(value = "填报时间")
    @TableField("FILLED_DATE")
    private Date filledDate;

    @ApiModelProperty(value = "标题")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "主管部门领导")
    @TableField("DEPARTMENT_LEADER")
    private String departmentLeader;

    @ApiModelProperty(value = "纪检组")
    @TableField("DISCIPLINE_GROUP")
    private String disciplineGroup;

    @ApiModelProperty(value = "海油发展办公室")
    @TableField("HF_OFFICE")
    private String hfOffice;

    @ApiModelProperty(value = "流程实例ID")
    @TableField("PROCESS_ID")
    private String processId;

    @ApiModelProperty(value = "流程状态")
    @TableField("PROCRSS_STATUS")
    private Integer procrssStatus;

    @ApiModelProperty(value = "是否被驳回（0-被驳回，1-没有）")
    @TableField("IS_TURN_DOWN")
    private Integer isTurnDown;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "当前待处理的工作项id")
    @TableField("CURRENT_WORK_ITEM_ID")
    private String currentWorkItemId;

    @ApiModelProperty(value = "当前处理人")
    @TableField("CURRENT_PERSON")
    private String currentPerson;

    @ApiModelProperty(value = "当前处理人id")
    @TableField("CURRENT_PERSON_ID")
    private String currentPersonId;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATOR_ID")
    private String creatorId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty(value = "更新人id")
    @TableField("UPDATE_ID")
    private String updateId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "流程引擎类型：0-BPS,1-F2")
    @TableField("ENGINE_TYPE")
    private String engineType;


}

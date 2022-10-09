package com.ryota.demo.entity;

import java.math.BigDecimal;
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
 * 办公用房申请详细信息表
 * </p>
 *
 * @author ryota
 * @since 2022-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OfficeHouseApplyInformation对象", description="办公用房申请详细信息表")
public class OfficeHouseApplyInformation implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "主表关联id")
    @TableField("APPLY_MASTER_ID")
    private String applyMasterId;

    @ApiModelProperty(value = "填报人")
    @TableField("FILLED_BY")
    private String filledBy;

    @ApiModelProperty(value = "填报单位")
    @TableField("FILLED_BY_ORG")
    private String filledByOrg;

    @ApiModelProperty(value = "创建人单位id")
    @TableField("FILLED_BY_ORG_ID")
    private String filledByOrgId;

    @ApiModelProperty(value = "填报日期")
    @TableField("FILLED_DATE")
    private Date filledDate;

    @ApiModelProperty(value = "申请人")
    @TableField("USER")
    private String user;

    @ApiModelProperty(value = "申请人ID")
    @TableField("USER_ID")
    private String userId;

    @ApiModelProperty(value = "岗位")
    @TableField("JOB")
    private String job;

    @ApiModelProperty(value = "职位")
    @TableField("POSITION")
    private String position;

    @ApiModelProperty(value = "职位id")
    @TableField("POSITION_ID")
    private String positionId;

    @ApiModelProperty(value = "级别类别")
    @TableField("LEVEL_TYPE")
    private String levelType;

    @ApiModelProperty(value = "级别")
    @TableField("LEVEL_NUM")
    private String levelNum;

    @ApiModelProperty(value = "单位id")
    @TableField("ORG")
    private String org;

    @ApiModelProperty(value = "单位名")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "单位备注")
    @TableField("ORG_REMARK")
    private String orgRemark;

    @ApiModelProperty(value = "办公用房使用标准面积")
    @TableField("OFFICE_AREA_STANDARD")
    private BigDecimal officeAreaStandard;

    @ApiModelProperty(value = "办公地点")
    @TableField("OFFICE_LOCATION")
    private String officeLocation;

    @ApiModelProperty(value = "是否有办公有房（1-有，0-没有）")
    @TableField("IS_OFFICE_HOUSE")
    private Integer isOfficeHouse;

    @ApiModelProperty(value = "办公用饭总数量")
    @TableField("OFFICE_HOUSE_NUM")
    private Integer officeHouseNum;

    @ApiModelProperty(value = "办公室面积")
    @TableField("OFFICE_AREA")
    private BigDecimal officeArea;

    @ApiModelProperty(value = "休息室面积")
    @TableField("LOUNGE_AREA")
    private BigDecimal loungeArea;

    @ApiModelProperty(value = "卫生间面积")
    @TableField("BATHROOM_AREA")
    private BigDecimal bathroomArea;

    @ApiModelProperty(value = "其他面积")
    @TableField("ELSE_AREA")
    private BigDecimal elseArea;

    @ApiModelProperty(value = "实际使用总面积")
    @TableField("TOTAL_AREA")
    private BigDecimal totalArea;

    @ApiModelProperty(value = "是否超标（1-超标，2-未超标）")
    @TableField("IS_EXCEEIVE")
    private String isExceeive;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATOR_ID")
    private String creatorId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty(value = "更新人ID")
    @TableField("UPDATE_ID")
    private String updateId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识（0-未删除，1-已删除）")
    @TableField("DEL_FLAG")
    private Integer delFlag;

    @ApiModelProperty(value = "流程引擎类型：0-BPS,1-F2")
    @TableField("ENGINE_TYPE")
    private String engineType;

    @ApiModelProperty(value = "使用状态 1再用 2已退还")
    @TableField("USE_STATUS")
    private String useStatus;

    @ApiModelProperty(value = "开始使用日期")
    @TableField("USE_START_DATE")
    private Date useStartDate;

    @ApiModelProperty(value = "使用结束日期")
    @TableField("USE_END_DATE")
    private Date useEndDate;

    @ApiModelProperty(value = "房间号")
    @TableField("ROOM_NUMBER")
    private String roomNumber;


    private String masterId;


}

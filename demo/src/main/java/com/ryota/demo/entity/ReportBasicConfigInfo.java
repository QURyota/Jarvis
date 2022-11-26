package com.ryota.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ryota
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ReportBasicConfigInfo对象", description="")
public class ReportBasicConfigInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "公司名")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "公司id")
    @TableField("ORG_ID")
    private String orgId;

    @ApiModelProperty(value = "公司地址")
    @TableField("REPORT_ORG_ADDRESS")
    private String reportOrgAddress;

    @ApiModelProperty(value = "举报邮箱")
    @TableField("REPORT_EMAIL")
    private String reportEmail;

    @ApiModelProperty(value = "举报电话")
    @TableField("REPORT_TEL")
    private String reportTel;

    @ApiModelProperty(value = "抄送邮箱")
    @TableField("CC_EMAIL")
    private String ccEmail;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "邮编")
    @TableField("POSTCODE")
    private String postcode;

    @ApiModelProperty(value = "zip密码")
    @TableField("ZIP_PASSWORD")
    private String zipPassword;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    @TableField("VERSION")
    private Integer version;

    @ApiModelProperty(value = "默认")
    @TableField("DEFAULT_FLAG")
    private Integer defaultFlag;

    @ApiModelProperty(value = "逻辑删除")
    @TableField("DEL_FLAG")
    private Boolean delFlag;

    @ApiModelProperty(value = "历史最大版本发布")
    @TableField("PUBLISHED")
    private String published;


}

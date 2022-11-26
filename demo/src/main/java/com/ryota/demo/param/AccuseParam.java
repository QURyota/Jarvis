package com.ryota.demo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022/11/26 18:18
 * @Author ryota
 */
@Data
public class AccuseParam {

    @ApiModelProperty("举报人姓名")
    private String accuseName;
    @ApiModelProperty("举报人身份证号")
    private String accuseIdcard;
    @ApiModelProperty("举报人联系方式")
    private String accusePhone;
    @ApiModelProperty("举报人政治面貌")
    private String politicsStatus;
    @ApiModelProperty("举报人现居地址")
    private String accuseAddress;
    @ApiModelProperty("被举报人姓名")
    private String defendantName;
    @ApiModelProperty("被告人单位")
    private String defendantOrg;
    @ApiModelProperty("职务")
    private String defendantPosition;
    @ApiModelProperty("所在地区")
    private String defendantAddress;
    @ApiModelProperty("举报标题")
    private String title;
    @ApiModelProperty("主要问题")
    private String mainProblem;
    @ApiModelProperty("附件")
    private List<MailFileInfo> fileParams;

    private String orgId;

}

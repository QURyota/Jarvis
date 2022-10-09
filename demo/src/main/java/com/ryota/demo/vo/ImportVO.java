package com.ryota.demo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022/10/9 18:53
 * @Author ryota
 */
@Data
public class ImportVO {

    @Excel(name = "申请单id")
    private String id;

    @Excel(name = "填报人")
    private String filledBy;

    @Excel(name = "填报单位")
    private String filledByOrg;

    @Excel(name = "填报时间")
    private Date filledDate;

    @Excel(name = "标题")
    private String title;

    @Excel(name = "备注（申请单）")
    private String mainRemark;

    @Excel(name = "使用人")
    private String user;

    @Excel(name = "使用人单位")
    private String orgName;

    @Excel(name = "使用人岗位")
    private String job;

    @Excel(name = "使用人职位")
    private String position;

    @Excel(name = "使用人级别")
    private String levelType;

    @Excel(name = "面积标准")
    private BigDecimal officeAreaStandard;

    @Excel(name = "办公地点")
    private String officeLocation;

    @Excel(name = "在兼职子企业是否有办公用房")
    private String isOfficeHouse;

    @Excel(name = "办公用房总数量")
    private String officeHouseNum;

    @Excel(name = "办公室")
    private BigDecimal officeArea;

    @Excel(name = "休息室")
    private BigDecimal loungeArea;

    @Excel(name = "卫生间")
    private BigDecimal bathroomArea;

    @Excel(name = "其他")
    private BigDecimal elseArea;

    @Excel(name = "实际使用面积")
    private BigDecimal totalArea;

    @Excel(name = "是否超标")
    private String isExceeive;

    @Excel(name = "备注（用房信息）")
    private String infoRemark;

    @Excel(name = "用房状态（0：使用中;1：审批中；-1:历史信息；-2：信息删除待确认；）")
    private String useStatus;



}

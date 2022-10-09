package com.ryota.demo.controller;


import com.ryota.demo.common.Result;
import com.ryota.demo.service.OfficeHouseApplyMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 办公用房申请主表 前端控制器
 * </p>
 *
 * @author ryota
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/office-house-apply-master")
public class OfficeHouseApplyMasterController {

    @Autowired
    private OfficeHouseApplyMasterService officeHouseApplyMasterService;

    @PostMapping("importExcel")
    public Result importExcel(@RequestParam("file") MultipartFile file) {
        return officeHouseApplyMasterService.importExcel(file);
    }

}


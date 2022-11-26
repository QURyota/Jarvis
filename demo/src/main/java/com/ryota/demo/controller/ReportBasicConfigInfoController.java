package com.ryota.demo.controller;


import com.ryota.demo.common.Result;
import com.ryota.demo.param.AccuseParam;
import com.ryota.demo.service.ReportBasicConfigInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ryota
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/report-basic-config-info")
public class ReportBasicConfigInfoController {

    @Autowired
    private ReportBasicConfigInfoService reportBasicConfigInfoService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("postMail")
    @ApiOperation("发送举报邮件")
    public Result postMail(@RequestBody AccuseParam accuseParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return reportBasicConfigInfoService.postMail(accuseParam,request,response);
    }

    @GetMapping("getPath")
    public Result getPath() throws IOException {

        String path = "";
        path = "templates/model.ftl";
        File file = ResourceUtils.getFile( path);
        String path1 = file.getPath();
        String absolutePath = file.getAbsolutePath();
        System.out.println("classpath:" + path1+"--"+absolutePath);
        Resource resource = new ClassPathResource("model.ftl");
        System.out.println(resource.getURL().getPath());
        return Result.success(resource.getURL().getPath());
    }

}


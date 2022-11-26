package com.ryota.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ryota.demo.common.Result;
import com.ryota.demo.entity.ReportBasicConfigInfo;
import com.ryota.demo.param.AccuseParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ryota
 * @since 2022-11-26
 */
public interface ReportBasicConfigInfoService extends IService<ReportBasicConfigInfo> {

    /**
     * 发送举报邮件
     * @param accuseParam
     * @param request
     * @param response
     * @return
     */
    Result postMail(AccuseParam accuseParam, HttpServletRequest request, HttpServletResponse response) throws IOException;
}

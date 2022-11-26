package com.ryota.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryota.demo.common.Result;
import com.ryota.demo.entity.ReportBasicConfigInfo;
import com.ryota.demo.mapper.ReportBasicConfigInfoMapper;
import com.ryota.demo.param.AccuseParam;
import com.ryota.demo.param.MailFileInfo;
import com.ryota.demo.service.ReportBasicConfigInfoService;
import com.ryota.demo.utils.FileUtils;
import com.ryota.demo.utils.HttpsUrlValidator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryota
 * @since 2022-11-26
 */
@Service
@Slf4j
public class ReportBasicConfigInfoServiceImpl extends ServiceImpl<ReportBasicConfigInfoMapper, ReportBasicConfigInfo> implements ReportBasicConfigInfoService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public Result postMail(AccuseParam accuseParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        String path = "";
        path = "templates/model.ftl";
        Template template = configuration.getTemplate("model.ftl");

        /** 用于组装word页面需要的数据 */
        Map<String, String> dataMap = new HashMap<String, String>();
        //--发邮件
        //被举报人信息
        String defendantName = accuseParam.getDefendantName();
        String defendantOrg = accuseParam.getDefendantOrg();
        String defendantAddress = accuseParam.getDefendantAddress();
        String defendantPosition = accuseParam.getDefendantPosition();
        //主要问题
        String mainProblem = accuseParam.getMainProblem();
        String title = accuseParam.getTitle();
        if (accuseParam.getAccuseName() == null || accuseParam.getAccuseName().isEmpty()) {
            dataMap.put("accuseName", "");
        } else {
            dataMap.put("accuseName", accuseParam.getAccuseName());
        }
        if (accuseParam.getAccuseIdcard() == null || accuseParam.getAccuseIdcard().isEmpty()) {
            dataMap.put("accuseIdCard", "");
        } else {
            dataMap.put("accuseIdCard", accuseParam.getAccuseIdcard());
        }
        if (accuseParam.getAccusePhone() == null || accuseParam.getAccusePhone().isEmpty()) {
            dataMap.put("accusePhone", "");
        } else {
            dataMap.put("accusePhone", accuseParam.getAccusePhone());
        }
        if (accuseParam.getPoliticsStatus() == null || accuseParam.getPoliticsStatus().isEmpty()) {
            dataMap.put("politicsStatus", "");
        } else {
            dataMap.put("politicsStatus", accuseParam.getPoliticsStatus());
        }
        if (accuseParam.getAccuseAddress() == null || accuseParam.getAccuseAddress().isEmpty()) {
            dataMap.put("accuseAddress", "");
        } else {
            dataMap.put("accuseAddress", accuseParam.getAccuseAddress());
        }
        dataMap.put("title", title);
        dataMap.put("defendantName", defendantName);
        dataMap.put("defendantOrg", defendantOrg);
        dataMap.put("defendantPosition", defendantPosition);
        dataMap.put("defendantAddress", defendantAddress);
        dataMap.put("mainProblem", mainProblem);
        File outFile = new File("举报信息.doc");

        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
        try {
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        //查询zip密码
        ReportBasicConfigInfo infoByOrgId = this.getOne(new QueryWrapper<ReportBasicConfigInfo>().eq("ORG_ID",accuseParam.getOrgId()));
        String passwd = infoByOrgId.getZipPassword();
        //查询收件人邮箱
        String email = infoByOrgId.getReportEmail();
        //查询是否有抄送人
        List<String> ccEmailList = new ArrayList<>();
        if(StringUtils.isNotEmpty(infoByOrgId.getCcEmail())){
            String ccEmail = infoByOrgId.getCcEmail();
            if(ccEmail.contains(";")){
                ccEmailList = Arrays.stream(ccEmail.split(";")).collect(Collectors.toList());
            }else {
                ccEmailList.add(ccEmail);
            }
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile("举报材料.zip");
        } catch (ZipException e) {
            throw new RuntimeException(e);
        }
        if (!accuseParam.getFileParams().isEmpty()) {
            try {
                //创建临时文件list
                ArrayList<File> filesToAdd = new ArrayList<File>();
                //获取文件信息  将临时文件放入list中
                List<MailFileInfo> fileParams = accuseParam.getFileParams();
                for (MailFileInfo fileParam : fileParams) {
                    File filePath = new File("");
                    HttpsUrlValidator.retrieveResponseFromServer(fileParam.getFileUrl());
                    File file = FileUtils.urlToFile(fileParam.getFileUrl(), filePath, fileParam.getFileName());
                    String absolutePath = file.getAbsolutePath();
                    System.out.println("url转文件路径:" + absolutePath + "-----------------------------");
                    filesToAdd.add(file);
                }
                String absolutePath = outFile.getAbsolutePath();
                System.out.println("生成doc文件路径:" + absolutePath + "-----------------------------");
                filesToAdd.add(outFile);
                ZipParameters parameters = new ZipParameters();
                //压缩方式
                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                //压缩级别
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                parameters.setEncryptFiles(true);
                //加密方式
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                //设置密码
                parameters.setPassword(passwd);
                //向zip加入文件
                log.info(parameters.toString());
                log.info(filesToAdd.toString());
                zipFile.addFiles(filesToAdd, parameters);
                //加入压缩包之后 删除所有临时文件
                for (File file : filesToAdd) {
                    file.delete();
                }
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                //设置zip名并选择本地zip文件作为附件
                messageHelper.addAttachment(MimeUtility.encodeWord("举报材料.zip"), new FileSystemResource(new File("举报材料.zip")));
                //获取并设置主题
                messageHelper.setSubject("海油发展纪检在线举报");
                //发送人
                messageHelper.setFrom("quyifanryota@163.com");
                log.info("发件人为:quyifanryota@163.com");
                messageHelper.setText("您收到一封在线举报，请下载附件查看");
                //收件人
                messageHelper.setTo(email);
                //判断是否存在抄送地址
                if(CollectionUtils.isNotEmpty(ccEmailList)){
                    String[] strings = ccEmailList.toArray(new String[ccEmailList.size()]);
                    messageHelper.setCc(strings);
                    log.info("收件人是:{},抄送人是{}",email,strings);
                }
                log.info("收件人是:{}",email);
                //发送邮件
                mailSender.send(mimeMessage);
                log.info("邮件已经发送");
                outFile.delete();
                File zip = new File("举报材料.zip");
                zip.delete();
            } catch (Exception e) {
                return Result.error("邮件发送失败");
            }
            return Result.success();
        } else {
            try {
                String absolutePath = outFile.getAbsolutePath();
                System.out.println("生成doc文件路径:" + absolutePath + "-----------------------------");
                ZipParameters parameters = new ZipParameters();
                //压缩方式
                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                //压缩级别
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                parameters.setEncryptFiles(true);
                //加密方式
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                //设置密码
                parameters.setPassword(passwd);
                //向zip加入文件
                zipFile.addFile(outFile, parameters);
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                //设置zip名并选择本地zip文件作为附件
                messageHelper.addAttachment(MimeUtility.encodeWord("举报材料.zip"), new FileSystemResource(new File("举报材料.zip")));

                //获取并设置主题
                messageHelper.setSubject("海油发展纪检在线举报");
                //发送人
                messageHelper.setFrom("quyifanryota@163.com");
                log.info("发件人为:quyifanryota@163.com");

                messageHelper.setText("您收到一封在线举报，请下载附件查看");
                //收件人
                messageHelper.setTo(email);
                //判断是否存在抄送地址
                if(CollectionUtils.isNotEmpty(ccEmailList)){
                    String[] strings = ccEmailList.toArray(new String[ccEmailList.size()]);
                    messageHelper.setCc(strings);
                    log.info("收件人是:{},抄送人是{}",email,strings);
                }
                log.info("收件人是:{}",email);


                //发送邮件
                mailSender.send(mimeMessage);
                log.info("邮件已经发送");
                outFile.delete();
                File zip = new File("举报材料.zip");
                zip.delete();

            } catch (Exception e) {
                return Result.error("邮件发送失败");
            }
            return Result.success();
        }
    }
}

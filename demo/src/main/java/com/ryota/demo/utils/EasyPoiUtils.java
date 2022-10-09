package com.ryota.demo.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022/10/9 18:54
 * @Author ryota
 */
@Slf4j
public class EasyPoiUtils {

    public static <T> List<T> importExcelNew(MultipartFile file, Integer titleRows, Integer headerRows, boolean needVerfiy,
                                             Class<T> pojoClass) throws Exception {
        if (file == null) {
            return null;
        } else {
            return baseImport(file.getInputStream(), titleRows, headerRows, needVerfiy, pojoClass);
        }

    }

    /**
     * 最基础导入
     *
     * @param inputStream
     * @param titleRows   表格标题行数,默认0
     * @param headerRows  表头行数,默认1
     * @param needVerify  是否需要检测excel
     * @param pojoClass   导入的对象
     * @return
     */
    private static <T> List<T> baseImport(InputStream inputStream, Integer titleRows, Integer headerRows,
                                          boolean needVerify, Class<T> pojoClass) throws Exception {
        if (inputStream == null) {
            return null;
        } else {
            final ImportParams params = new ImportParams();
            params.setTitleRows(titleRows);
            params.setHeadRows(headerRows);
//            params.setSaveUrl("/excel/");
            params.setNeedSave(false);
            params.setNeedVerfiy(needVerify);
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        }

    }

}



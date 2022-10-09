package com.ryota.demo.service.impl;

import com.ryota.demo.common.Result;
import com.ryota.demo.entity.OfficeHouseApplyInformation;
import com.ryota.demo.entity.OfficeHouseApplyMaster;
import com.ryota.demo.mapper.OfficeHouseApplyMasterMapper;
import com.ryota.demo.service.OfficeHouseApplyInformationService;
import com.ryota.demo.service.OfficeHouseApplyMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryota.demo.utils.EasyPoiUtils;
import com.ryota.demo.vo.ImportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 办公用房申请主表 服务实现类
 * </p>
 *
 * @author ryota
 * @since 2022-10-09
 */
@Service
public class OfficeHouseApplyMasterServiceImpl extends ServiceImpl<OfficeHouseApplyMasterMapper, OfficeHouseApplyMaster> implements OfficeHouseApplyMasterService {

    @Autowired
    private OfficeHouseApplyInformationService officeHInfoService;

    @Override
    public Result importExcel(MultipartFile file) {

        List<OfficeHouseApplyMaster> officeHouseApplyMasters = new ArrayList<>();
        List<OfficeHouseApplyInformation> officeHouseApplyInformations = new ArrayList<>();
        try {
            List<ImportVO> importVOList = EasyPoiUtils.importExcelNew(file, 0, 1, false, ImportVO.class);
            if (CollectionUtils.isEmpty(importVOList)) {
                return Result.error();
            }
            for (ImportVO importVO : importVOList) {
                OfficeHouseApplyInformation information = new OfficeHouseApplyInformation();
                //如果申请单id为空直接保存在info表中
                if (importVO.getId().equals("NULL")){
                    if(importVO.getIsOfficeHouse().equals("无")){
                        information.setIsOfficeHouse(0);
                    }else {
                        information.setIsOfficeHouse(1);
                    }
                    information.setId(UUID.randomUUID().toString().substring(1,16));
                    officeHInfoService.save(information);
                }else {
                    BeanUtils.copyProperties(importVO,information);
                    information.setMasterId(importVO.getId());
                    information.setId(UUID.randomUUID().toString().substring(1,16));
                    officeHInfoService.save(information);
                }
                //获取使用人域账号查询

                //获取单位查询单位id

                //获取填报人单位,查询填报人单位id

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}

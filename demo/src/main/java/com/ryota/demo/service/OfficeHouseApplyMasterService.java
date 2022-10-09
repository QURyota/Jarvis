package com.ryota.demo.service;

import com.ryota.demo.common.Result;
import com.ryota.demo.entity.OfficeHouseApplyMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 办公用房申请主表 服务类
 * </p>
 *
 * @author ryota
 * @since 2022-10-09
 */
public interface OfficeHouseApplyMasterService extends IService<OfficeHouseApplyMaster> {

    Result importExcel(MultipartFile file);
}

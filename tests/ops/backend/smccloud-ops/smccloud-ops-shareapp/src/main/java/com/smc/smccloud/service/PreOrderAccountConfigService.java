package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.PreOrderAccountRequest;
import com.smc.smccloud.model.prestock.PreOrderAccountConfigDO;
import com.smc.smccloud.model.prestock.PreOrderAccountConfigDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wuweidong
 * @create 2024/1/2 14:58
 * @description
 */
public interface PreOrderAccountConfigService {

    ResultVo<PageInfo<PreOrderAccountConfigDO>> listPreOrderAccountConfig(PreOrderAccountRequest request);

    ResultVo<List<PreOrderAccountConfigDTO>> getPreOrderAccountConfig(PreOrderAccountRequest request);

    ResultVo<String> writeAccountConfigByBath(List<PreOrderAccountConfigDO> list);
    ResultVo<String> writeAndUpdatePreOrderAccountConfig(PreOrderAccountConfigDO configDO);

    ResultVo<String>  UpdateAccountConfigById(PreOrderAccountConfigDO configDO);
    ResultVo<String>  importPreOrderAccountConfig(MultipartFile file);

    void exportPreOrderAccountConfig(PreOrderAccountRequest request, HttpServletResponse response);

    ResultVo<String> deletePreOrderAccountConfigById(Long id);
}

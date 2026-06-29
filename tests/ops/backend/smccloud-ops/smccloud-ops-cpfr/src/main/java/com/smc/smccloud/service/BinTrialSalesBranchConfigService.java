package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.BinTrialSalesBranchConfigRequestDTO;
import com.smc.smccloud.model.binorder.BinTrialSalesBranchConfigVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/6/2 15:28
 * @description
 */
public interface BinTrialSalesBranchConfigService {

    ResultVo< PageInfo<BinTrialSalesBranchConfigVO>> listBinTrialSalesBranchConfigData(BinTrialSalesBranchConfigRequestDTO request);

    /**
     * 更新配置
     * @param configVO
     * @return
     */
    ResultVo<String> saveBinTrialSalesBranchConfig(BinTrialSalesBranchConfigVO configVO) ;

    ResultVo<String> deleteBinTrialSalesBranchConfig(Long jobId,List<Long> Ids);

    /**
     * 引用原配置数据
     * @param jobId
     * @param warehouseCode
     * @return
     */
    ResultVo<String> createBinTrialSalesBranchConfig(Long jobId,String warehouseCode) ;

    /**
     * 追加配置
     * @param jobId
     * @param warehouseCode
     * @param branches
     * @return
     */
    ResultVo<String> addBinTrialSalesBranchConfig(Long jobId,String warehouseCode,List<String> branches) ;

    /**
     * 还原第一仓库
     * @param jobId
     * @param ids
     * @return
     */
    ResultVo<String> restoreBinTrialSalesBranchConfig(Long jobId,List<Long> ids) ;

    ResultVo<List<String>>  getWarehouseCodeByMasters(Long jobId,String masterCode);

    /**
     *导入配置文件
     * @param jobId
     * @param file
     * @return
     */
    ResultVo<String>  importBinConfigureData(Long jobId,MultipartFile file);

    void exportBinConfigureData(BinTrialSalesBranchConfigRequestDTO request, HttpServletResponse response) ;

}

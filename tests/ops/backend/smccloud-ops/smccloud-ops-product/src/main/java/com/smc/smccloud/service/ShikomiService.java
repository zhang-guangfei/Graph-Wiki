package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.SpecStatisticsVO;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.model.supplier.SupplierVo;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public interface ShikomiService {

    /**
     * 是否可以使用shikomi
     *
     * @param shikomiNo
     * @return
     */
    ResultVo<ShikomiVO> canUseShikomi(String modelNo, String shikomiNo, String customerNo);


    /**
     * 查询客户可用的shikomi
     *
     * @param modelNo
     * @param customerNo
     * @return
     */
    List<ShikomiVO> listCustomerShikomi(String modelNo, String customerNo);


    /**
     * 文件下载并上传至数据库
     *
     * @return
     */
    ResultVo<String> downloadFile();

    /**
     * 查询分页数据
     *
     * @param data
     * @return
     */
    PageInfo<ShikomiVO> listShikomi(ShikomiDataVO data);

    /**
     * 保存或修改shikomi
     *
     * @param info
     * @return
     */
    ResultVo<String> saveShikomidata(ShikomiTotalDO info);

    ResultVo<String> saveCustomerdata(ShikomiCustomerVO customerVO);

    ResultVo<String> importCNReplyFile(MultipartFile file);

    ResultVo<String> importJPReplyFile(MultipartFile file);

    ResultVo<String> prepareShikomi(ShikomiPrepareDTO dto);

    /**
     * 添加关联型号
     *
     * @param shikomiVO
     * @return
     */
    ResultVo<String> addRelationModel(ShikomiVO shikomiVO);

    ResultVo<String> sendInspectionReport();

    ResultVo<String> answerInspection(ShikomiInspectionAnsewrDTO dto);

    ResultVo<List<ShikomiInspectionVO>> findInspectionInfoById(String id);

    ResultVo<List<ShikomiCustomerVO>> findCustomerDataByNo(String shikomiNo);

    ResultVo<String> inspectApply(ShikomiInspectionDTO info);

    ResultVo<String> inspectProcess(ShikomiInspectionDTO info);

    void exportShikomi(ShikomiDataVO data);

    ResultVo<List<ShikomiVO>> getCanUseShikomi(String modelNo);

    void calShikomiUpdateInfo();

    Boolean checkShikomiStcokInsufficient();

    /**
     * 门户 shikimo回调接口
     */
    ResultVo<String> callbackToSMCShikomi(ShikomiCallbackVO shikomiCallbackVO);

    ResultVo<String> importCNShikomiData(MultipartFile file);

    ResultVo<PageInfo<ShikomiInspectionVO>> listPageShikomiInspection(ShikomiInspectionRequest request);

    void exportShikomiInspectionData(ShikomiInspectionRequest request);

    ResultVo<List<ShikomiRegistDateCallbackDTO>> listShikomiRegistData(List<String> applyNos);

    ResultVo<String> checkShikomiInspectionByDept();

    ResultVo<String> cancelPrepareShikomi(ShikomiPrepareDTO dto);

    ResultVo<String> delShikomiCustomerById(Integer id);

    ResultVo<String> delShikomiByNo(String shikomiNo,String modelNo, String supplierCode);

    ResultVo<List<SupplierVo>> getShikomiSupplier();

    ResultVo<String> saveShikomiCache();

    /**
     * 导入日本未订货月数
     * @param file
     * @return
     */
    ResultVo<String> importShikomiNoord(MultipartFile file);

    /**
     * 每月计算中国shikomi未订货月数
     * @return
     */
    ResultVo<String> syncCNShikomiNoordQty();


    ResultVo<String> calcShikomiQtyWarning();

    ResultVo<ShikomiVO> getShikomiDataByNo(String shikomiNo, String modelNo, String supplierNo);

    /**
     * 计算点检
     */
    ResultVo<String> calcShikomiInspection(MultipartFile file);

    ResultVo<String> checkStatus();

    /**
     * shikomi残数警告发送给门户
     * @return
     */
    ResultVo<String> shikomiWarningSendMH();

    ResultVo<String> updateShikomiWarnQty(ShikomiWarnCallBackDTO dto);

    ResultVo<PageInfo<ShikomiBudgetVO>> findShikomiBudget(ShikomiBudgetRequest request);

    void exportShikomiBudget(ShikomiBudgetRequest request, HttpServletResponse response);

    /**
     * 点检计算(新)
     */
    ResultVo<String> shikomiInspectionCalcNew();

    void conventShikomiBaseData();

    ResultVo<String> upShikomiBudjet(ShikomiBudgetVO shikomiBudgetVO);

    ResultVo<List<ShikomiBudgetDO>> findAdjustBatchNo(String batchNo);

    // 根据shikomi返回供应商
    ResultVo<ShikomiVO> getSupplierByShikomi(String shikomiNo);

}

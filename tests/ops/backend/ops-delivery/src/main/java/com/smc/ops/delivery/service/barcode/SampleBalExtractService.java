package com.smc.ops.delivery.service.barcode;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.SampleBal;
import com.sales.ops.dto.expdetail.ExpdetailDto;
import com.sales.ops.dto.expdetail.SampleOrderApplyDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.model.samplebal.OpsSamplebalDO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 样品出库订单抽取
 */
public interface SampleBalExtractService {

    /**
     * 样品出库订单抽取
     * @param prodFlag 是否拆分
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    CommonResult<String> extractSampleBal(Boolean prodFlag) throws OpsException;

    // 根据订单号+型号合并数量
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailDto> mergeByOrderNoWithModelNo(List<ExpdetailDto> list) throws OpsException;

    // 获取字典表的申请类型和结转类型对应关系
    Map<String, String> getDictData(String classCode) throws OpsException;

    // 查找接单信息
    Rcvdetail findRcvDetail(ExpdetailDto expdetailVO) throws OpsException;

    // 获取SampleBal清单
    List<SampleBal> getSampleBalList(String orderFno) throws OpsException;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    void upExpdetailOptCodeById(List<Long> idList, Integer optCode) throws OpsException;

    // 拆分型号订单修改状态
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    void updateOptCodeProdFlag(String order_fno, Integer optCode) throws OpsException;

    SampleOrderApplyDto getSampleApply(String orderFno) throws OpsException;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    ResultVo<String> getDeptNoByHRSalesDeptNos(String hrSalesDeptNo) throws OpsException;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    Boolean isAlreadyInto(List<SampleBal> list) throws OpsException;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    SampleBal InsertSampleBal(SampleOrderApplyDto sampleOrderApplyDto,ExpdetailDto item,List<SampleBal> samplebalList, Map<String, String> map,String prodFlag) throws OpsException;

    /**
     * 记录抽取日志
     * @param expdetailDto
     * @return
     * @throws OpsException
     */
    int sendOrderLog(ExpdetailDto expdetailDto) throws OpsException;

    // 样品订单抽取异常时，加入异常邮件提醒
    void sendErrorMail(String message);


    /**
     * 新增Sample_bal_property_assign表，用于记录sample_bal表的资产构成明细
     * @param sampleBal
     * @return
     * @throws OpsException
     */
    int sampleBalPropertyAssignAdd(OpsSamplebalDO sampleBal);


}

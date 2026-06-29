package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author edp02 @Date 2021/10/25 10:52
 */
public interface BinorderService {

    PageInfo<ModelExpFreqVO> listModelExpFreq(ModelExpFreqRequest request);

    List<ModelExpDetailVO> listModeldetail(ModelExpFreqRequest request);

    List<ModelExpDetailVO> listModeldetailByJob(ModelExpFreqRequest request);




    /**
     * 新增计算号数据
     * @param dto
     * @return
     */
    ResultVo<BinOrderCalcVO> newBinOrderCalcId(BinOrderCalcRequestDTO dto);

    /**
     * 计算bin订货
     * @param dto
     * @return
     */
    ResultVo<BinOrderCalcVO> calcBinOrder(BinOrderCalcRequestDTO dto);


    /**
     * bin订货审批查询
     * @param dto
     * @return
     */
    ResultVo<PageInfo<BinOrderAppVO>> listBinOrderApp(BinOrderAppRequestDTO dto);

    /**
     * bin订货审批
     * @param dto
     * @return
     */
    ResultVo<String> approveBinOrder(BinOrderApproveRequestDTO dto);


    /**
     * 推送给门户补库
     * @param dto
     * @return
     */
    ResultVo<String> sendToInStock(BinOrderApproveRequestDTO dto);

    /**
     * 申请bin订货
     * @param dto
     * @return
     */
    ResultVo<String> applyBinOrder(BinOrderApplyRequestDTO dto);

//    void createOrderByAppId(Long appId);

    /**
     * bin计算批次查询
     * @param
     * @return
     */
    ResultVo<List<BinOrderCalcVO>> listBinOrderCalc();

    ResultVo<List<BinOrderCalcVO>> listBinOrderCalcByWarehouseCode(String warehouseCode);

    /**
     * 修改bin交货期
     * @param dto
     * @return
     */
    ResultVo<String> updateBinOrderDlvDate(BinOrderUpdateDlvDateRequestDTO dto);

    ResultVo<String> batchUpdateBinOrder(BinOrderBatchUpdateDTO dto);

    ResultVo<String> clearTransQty(Long calcId);


    /**
     * 获取binorderDetailSplit
     * @param fromid
     * @return
     */
    ResultVo<List<BinOrderDetailVO>> listBinOrderDetailSplit(Long fromid);
    /**
     * 修改binorder明细
     * @param dto
     * @return
     */
    ResultVo<String> updateBinOrderDetail(BinOrderDetailUpdateDTO dto);

    /**
     * 批量添加bin订货明细
     * @param list
     * @return
     */
    ResultVo<String> saveBinOrderDetails(List<BinOrderDetailVO> list);

    /**
     * 定时计算型号订货频率
     */
    ResultVo<String> calcmodelExpFreq(int type);

    /**
     * 查找客户bin
     */
    ResultVo<PageInfo<BindataVO>> listCstmBindata(BindataRequest request);

    ResultVo<String> importBindata(MultipartFile file);

    /**
     * 结束Bin计算申请
     */
    ResultVo<String> finishbinordercalc(Long id);

    ResultVo<String> checkImpBindataStatus(String key);

    ResultVo<List<RedisMessageVO>> listBinRedisMessage();

    void exportCalcBinOrderData(BinOrderCalcQueryDTO dto);

    ResultVo<PageInfo<BinOrderDetailSplitVO>> listBinDetailSplit(BinOrderDetailSplitRequestDTO dto);

    ResultVo<List<OrdingOrderVO>> listOrdingOrder(String modelNo,String warehouseCode,Long id);

    ResultVo<ModelExpFreqMainVO> getModelExpFreqMain();

    void exportBinDetailSplit(BinOrderDetailSplitRequestDTO dto);

    /**
     * 生成订单
     * @param dto
     * @return
     */
    ResultVo<String> createOrder(BinOrderApproveRequestDTO dto);

    ResultVo<List<PrepareOrderVO>> listPrepareOrderView(String modelNo, String warehouseCode, Long id);

    ResultVo<BinOrderCalcVO> calcBinOrderSync(BinOrderCalcRequestDTO dto);
}

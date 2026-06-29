package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2021-10-19
 */
public interface BinOrderCalcService extends IService<BinOrderCalcDO> {

   ResultVo<PageInfo<BinOrderDetailVO>> listBinOrderDetail(BinOrderCalcQueryDTO dto);


   void asycalcBinOrder(BinOrderCalcRequestDTO dto);

   void addModelExpFreqCsv();

   void exportModelExpFreq(ModelExpFreqRequest request,HttpServletResponse response);

   /**
    *  按
    * @param modelNo
    * @return
    */
   ResultVo<List<BinOrderInventoryInfoVO>> listBinWarehouseStock(String modelNo);

   /**
    * 按库房存储
    * @param modelNo
    * @return
    */
   Map<String, BinOrderInventoryInfoVO> getInventoryInfomap(String modelNo);

   /**
    * 获取上次采购交货期
    */
   Date getLastPurchaseDlvDate(String modelNo, String warehouseCode);

   void saveCalcState(RedisMessageVO messageVO);

   RedisMessageVO getCalcState(Long calcId);

//   /**
//    * 生成订单
//    * @param dto
//    * @return
//    */
//   ResultVo<String> createOrder(BinOrderApproveRequestDTO dto);
}

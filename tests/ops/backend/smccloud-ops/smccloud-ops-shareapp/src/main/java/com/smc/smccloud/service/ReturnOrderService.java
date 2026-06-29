package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.opsreturn.RefuseRcvRequest;
import com.smc.smccloud.model.opsreturn.ReturnOrderBackCallVO;
import com.smc.smccloud.model.returnorder.*;
import com.smc.smccloud.model.warehouse.WareHouseInfo;
import org.apache.xpath.operations.Bool;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
public interface ReturnOrderService extends IService<ReturnOrderDO> {

   ResultVo<String> saveReturnOrder(List<ReturnOrderVO> list);

   ResultVo<PageInfo<ReturnOrderVO>> findAll(ReturnOrderRequest returnOrderRequest, Page page);

   void exportReturnOrder(ReturnOrderRequest returnOrderRequest);

   /**
    * 预览打印退货单
    */
   void printReturnOrder(PrintReturnOrderParams params, HttpServletResponse response);


   /**
    * 退货回调门户接口
    */
   ResultVo<String> dealReturnOrder(List<ReturnOrderBackCallVO> orderBackCallVOS);

   // 包裹拒收
   ResultVo<String> refuseRcv(RefuseRcvRequest refuseRcvRequest);

   /**
    * 同步已收货订单到cs_impdata
    * @return
    */
   ResultVo<String> sysReturnOrderToImpData();

   ResultVo<String> importInvoiceData();

//   ResultVo<String> returnWTOrderToWMS(String orderNo);

   /**
    *  根据客户获取仓库
    */
   ResultVo<List<WareHouseInfo>> getWareHouseByCustomerNo(String customerNo);

   ResultVo<String> importInvoiceDataToEvent(String orderNo, String applyNo);

   ResultVo<String> returnOrderConfirm(String applyNo,String orderNo, Integer status);

   /**
    * 收货登记
    * @return
    */
   ResultVo<String> receiveGoods(RcvOrderReGisterDTO reGisterDTO);

   /**
    * 将财务处理的开票数据下发WMS
    * @return
    */
   ResultVo<String> impInvoiceResult();

   ResultVo<String> cancelReturnOrder(CancelReturnOrderVO cancelReturnOrderVO);

   ResultVo<String> cancelReturnOrderToMenHu(CancelReturnToMenHuDTO cancelReturnToMenHuDTO);


   /**
    * 变更退货状态
    */
   ResultVo<String> updateReturnOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO);

   /**
    * 同步退货状态给门户
    * @return
    */
   ResultVo<String> dealReturnOrderToMH();

   /**
    * 委托在库退货拒收
    * @return
    */
   ResultVo<String> cancelCsReturnOrder(String applyNo, String orderNo);

   /**
    * 生成退货申请号
    * @return
    */
   ResultVo<String> creatApplyNo();

   /**
    * 添加退货单
    * @return
    */
   ResultVo<String> addReturnOrder(String jsonData, MultipartFile[] fileList);

   void downloadReturnFile(String fileName, String applyNo);

   /**
    * 同步退货单发票信息
    * @return
    */
   ResultVo<String> syncReturnInvoiceInfo();

   void saveTableToRedis(String optUser, String uiViewId,String jsonTableStr);
   
   ResultVo<String> getTableFromRedis(String optUser, String uiViewId);

    ResultVo<String> getAllTableFromRedis(String optUser, String uiViewId);
}

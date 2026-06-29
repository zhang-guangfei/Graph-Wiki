package com.smc.smccloud.web;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.opsreturn.RefuseRcvRequest;
import com.smc.smccloud.model.opsreturn.ReturnOrderBackCallVO;
import com.smc.smccloud.model.opsreturn.SaveTableSettingVO;
import com.smc.smccloud.model.returnorder.*;
import com.smc.smccloud.model.warehouse.WareHouseInfo;
import com.smc.smccloud.service.ReturnOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/shareapp/returnorder")
public class ReturnOrderController {

    @Resource
    private ReturnOrderService returnOrderService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public ResultVo<PageInfo<ReturnOrderVO>> findAll(@RequestBody ReturnOrderRequest returnOrderRequest, Page page){
        return returnOrderService.findAll(returnOrderRequest,page);
    }

    @RequestMapping(value = "/addReturnOrder",method = RequestMethod.POST)
    public ResultVo<String> saveReturnOrder(@Valid @RequestBody List<ReturnOrderVO> list) {
        return returnOrderService.saveReturnOrder(list);
    }

    /**
     * 变更退货申请状态
     */
    @RequestMapping(value = "/updateReturnOrderStatus",method = RequestMethod.POST)
    public ResultVo<String> updateReturnOrderStatus(@RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        return returnOrderService.updateReturnOrderStatus(updateOrderStatusDTO);
    }

    /**
     * 预览打印退货单
     * @param params
     * @return
     */
    @RequestMapping(value = "/printReturnOrder",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.POST)
    public void printReturnOrder(@RequestBody PrintReturnOrderParams params, HttpServletResponse response){
         returnOrderService.printReturnOrder(params,response);
    }

    /**
     * 退货回调门户接口
     */
    @PostMapping("/dealReturnOrder")
    public ResultVo<String> dealReturnOrder(@RequestBody List<ReturnOrderBackCallVO> orderBackCallVOS){
       return returnOrderService.dealReturnOrder(orderBackCallVOS);
    }

    /**
     * 确认退货(退货登记) [废弃]
     * @param reGisterDTO
     * @return
     */
//    @PostMapping("/rcvOrderReGister")
//    public ResultVo<String> rcvOrderReGister(@Valid @RequestBody RcvOrderReGisterDTO reGisterDTO) {
//        return returnOrderService.rcvOrderReGister(reGisterDTO);
//    }

    /**
     * 包裹拒收
     */
    @PostMapping("/refuseRcv")
    public ResultVo<String> refuseRcv (@RequestBody RefuseRcvRequest refuseRcvRequest) {
        return returnOrderService.refuseRcv(refuseRcvRequest);
    }

//    @PostMapping("/returnWTOrderToWMS")
//    public ResultVo<String> returnWTOrderToWMS(@RequestParam("orderNo") String orderNo) {
//        return returnOrderService.returnWTOrderToWMS(orderNo);
//    }

    /**
     *  根据客户获取仓库
     */
    @GetMapping("/getWareHouseByCustomerNo")
    public ResultVo<List<WareHouseInfo>> getWareHouseByCustomerNo(@RequestParam("customerNo") String customerNo) {
        return returnOrderService.getWareHouseByCustomerNo(customerNo);
    }

    @RequestMapping(value = "/returnOrderConfirm",method = RequestMethod.GET)
    public ResultVo<String> returnOrderConfirm(@RequestParam("applyNo") String applyNo,@RequestParam("orderNo") String orderNo,@RequestParam("status") Integer status) {
        return returnOrderService.returnOrderConfirm(applyNo,orderNo,status);
    }

    /**
     * 收货登记
     * @param reGisterDTO
     * @return
     */
    @RequestMapping(value = "/receiveGoods",method = RequestMethod.POST)
    public ResultVo<String> receiveGoods(@RequestBody RcvOrderReGisterDTO reGisterDTO) {
        return returnOrderService.receiveGoods(reGisterDTO);
    }


    /**
     * 取消退货
     */
    @RequestMapping(value = "/cancelReturnOrder",method = RequestMethod.POST)
    public ResultVo<String> cancelReturnOrder(@RequestBody CancelReturnOrderVO cancelReturnOrderVO) {
        return returnOrderService.cancelReturnOrder(cancelReturnOrderVO);
    }

    /**
     * 取消退货(给门户回调) (暂未开发完毕)
     */
    @RequestMapping(value = "/cancelReturnOrderToMenHu",method = RequestMethod.POST)
    public ResultVo<String> cancelReturnOrderToMenHu(@RequestBody CancelReturnToMenHuDTO cancelReturnToMenHuDTO) {
        return returnOrderService.cancelReturnOrderToMenHu(cancelReturnToMenHuDTO);
    }


    /**
     * 退货导出
     * @param returnOrderRequest
     */
    @RequestMapping(value = "/exportReturnOrder",method = RequestMethod.POST)
    public void exportReturnOrder(@RequestBody ReturnOrderRequest returnOrderRequest) {
        returnOrderService.exportReturnOrder(returnOrderRequest);
    }

    /**
     * 生成申请号
     * @return
     */
    @RequestMapping(value = "/creatApplyNo",method = RequestMethod.GET)
    public ResultVo<String> creatApplyNo() {
        return returnOrderService.creatApplyNo();
    }

    @RequestMapping(value = "/insertReturnOrder",method = RequestMethod.POST)
    public ResultVo<String> addReturnOrder(@RequestParam("addOrderData") String jsonData, @RequestParam("fileList")MultipartFile[] fileList) {
        return returnOrderService.addReturnOrder(jsonData, fileList);
    }


    @RequestMapping(value = "/downloadReturnFile",method = RequestMethod.POST)
    public void downloadReturnFile(@RequestParam("fileName") String fileName,@RequestParam("applyNo") String applyNo) {
        returnOrderService.downloadReturnFile(fileName, applyNo);
    }

    /**
     * 缓存前端退货页面table设置到redis
     */
    @RequestMapping(value = "/saveTableToRedis",method = RequestMethod.POST)
    public void saveTableToRedis(@RequestBody SaveTableSettingVO saveTableSettingVO) {
        returnOrderService.saveTableToRedis(saveTableSettingVO.getOptUser(),saveTableSettingVO.getUiViewId(), JSONUtil.toJsonStr(saveTableSettingVO.getJsonTableStr()));
    }

    @RequestMapping(value = "/getTableFromRedis",method = RequestMethod.POST)
    public ResultVo<String> getTableFromRedis(@RequestBody SaveTableSettingVO saveTableSettingVO) {
       return returnOrderService.getTableFromRedis(saveTableSettingVO.getOptUser(),saveTableSettingVO.getUiViewId());
    }

    @RequestMapping(value = "/getAllTableFromRedis",method = RequestMethod.POST)
    public ResultVo<String> getAllTableFromRedis(@RequestBody SaveTableSettingVO saveTableSettingVO) {
        return returnOrderService.getAllTableFromRedis(saveTableSettingVO.getOptUser(),saveTableSettingVO.getUiViewId());
    }
}

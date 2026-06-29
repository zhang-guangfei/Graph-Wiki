package com.sales.ops.web.controller.wmOrder;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.replacement.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.wmOrder.DoPcoLogicCenterService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description： 通知发货
 * @date ：Created in 2024/11/29 14:38
 */
@CrossOrigin
@RestController
@RequestMapping("/notify/shipment/")
public class NotifyShipmentPlanController {

    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;


    /**
     * 门户查询
     * @param param
     * @return
     */
    @PostMapping("search")
    public ResultVo<List<NotifyShipmentPlanPageDto>> findNotifyShipment(@RequestBody NotifyShipmentFindParam param){
        List<NotifyShipmentPlanPageDto> notifyShipments = doPcoLogicCenterService.findNotifyShipment(param);
        for(NotifyShipmentPlanPageDto obj : notifyShipments ){
            List<NotifyShipmentPlanItemPageDto> notifyShipmentPlanItemItem = doPcoLogicCenterService.findNotifyShipmentPlanItemItem(obj.getPlanNo());
            obj.setDetailList(notifyShipmentPlanItemItem);
        }
        return ResultVo.success(notifyShipments);
    }

    /**
     * 门户 创建计划
     * @param
     * @return
     */
    @PostMapping("create/save/List")
    public ResultVo<NotifyShipmentPlanResult> saveList(@RequestBody List<NotifyShipmentPlanSaveDto> dataList, @RequestParam(value = "userName") String userName ){
        NotifyShipmentPlanResult result = new NotifyShipmentPlanResult();
        List<NotifyShipmentPlanImportErrorDto> errList = new ArrayList<>();
        for(NotifyShipmentPlanSaveDto obj : dataList){
            NotifyShipmentPlanDto param = new NotifyShipmentPlanDto();
            BeanCopyUtil.copy(obj,param);
            try {
                if(StringUtils.isEmpty(param.getPlanNo())){
                    throw Exceptions.OpsException("请传计划号");
                }
                param.setCreator(userName);
                doPcoLogicCenterService.createNotifyShipmentPlan(param);
            } catch (Exception e) {
                NotifyShipmentPlanImportErrorDto error = new NotifyShipmentPlanImportErrorDto(param.getOrderFno(),param.getModelNo(),e.getMessage());
                errList.add(error);
                //写日志
                if(StringUtils.isNotEmpty(param.getOrderId())){
                    doPcoLogicCenterService.writeLog(param.getOrderId(),param.getOrderItem(),e.getMessage(),"拆分订单失败");
                }
            }
        }
        result.setErrList(errList);
        result.setSize(dataList.size());
        return ResultVo.success(result);
    }


    /**
     * 页面列表
     * @param pageModel
     * @return
     */
    @PostMapping("page/search")
    public CommonResult<PageInfo<NotifyShipmentPlanPageDto>> search(@RequestBody PageModel<NotifyShipmentPlanPageDto> pageModel) {
        try {
            PageInfo<NotifyShipmentPlanPageDto> result = doPcoLogicCenterService.findNotifyShipmentPlanPage(pageModel);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 页面列表
     * @param planNo
     * @return
     */
    @GetMapping("page/itemPlan")
    public CommonResult<List<NotifyShipmentPlanItemPageDto>> findItem(@RequestParam("planNo")String planNo) {
        try {
            List<NotifyShipmentPlanItemPageDto> notifyShipmentPlanItemItem = doPcoLogicCenterService.findNotifyShipmentPlanItemItem(planNo);
            return notifyShipmentPlanItemItem.isEmpty() ?
                    CommonResult.success(null) : CommonResult.success(notifyShipmentPlanItemItem);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }




    /**
     * 输入客户货期获取物流货期
     * @param param
     * @return
     */
    @PostMapping("create/wl")
    public CommonResult<Date> getWldDate(@RequestBody NotifyShipmentPlanDto param){
        try {
            Date notifyShipmentWlDate = doPcoLogicCenterService.getNotifyShipmentWlDate(param.getHopeDate(), param.getOrderId(), param.getOrderItem());
            return CommonResult.success(notifyShipmentWlDate);
        } catch (OpsException e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 验证
     * @param orderFno
     * @return
     */
    @GetMapping("create/verify")
    public CommonResult<NotifyShipmentVerifyDto> getVerifyDto(@RequestParam String orderFno){
        try {
            NotifyShipmentVerifyDto notifyShipmentVerifyDto = doPcoLogicCenterService.getNotifyShipmentVerifyForPage(orderFno);
            return CommonResult.success(notifyShipmentVerifyDto);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 页面保存
     * @param param
     * @return
     */
    @PostMapping("create/save")
    public CommonResult<NotifyShipmentPlanResult> saveOne(@RequestBody NotifyShipmentPlanDto param ){
        try {
            doPcoLogicCenterService.createNotifyShipmentPlan(param);
            return CommonResult.success();
        } catch (Exception e) {
            //写日志
            if(StringUtils.isNotEmpty(param.getOrderId())){
                doPcoLogicCenterService.writeLog(param.getOrderId(),param.getOrderItem(),e.getMessage(),"拆分订单失败");
            }
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("excel/import")
    public CommonResult<NotifyShipmentPlanResult> excelImport(@RequestParam(value = "file") MultipartFile file ,@RequestParam(value = "userName") String userName){

        try (InputStream inputStream = file.getInputStream()) {
            //默认每次100条
            List<NotifyShipmentPlanDto> list = new ArrayList<>();
            EasyExcel.read(inputStream, NotifyShipmentPlanImportDto.class, new PageReadListener<NotifyShipmentPlanImportDto>(dataList -> {
                for(NotifyShipmentPlanImportDto obj : dataList){
                    NotifyShipmentPlanDto target = new NotifyShipmentPlanDto();
                    BeanCopyUtil.copy(obj,target);
                    list.add(target);
                }
            })).sheet().doRead();

            NotifyShipmentPlanResult result = new NotifyShipmentPlanResult();
            List<NotifyShipmentPlanImportErrorDto> errList = new ArrayList<>();
            for(NotifyShipmentPlanDto param : list){
                try {
                    param.setCreator(userName);
                    doPcoLogicCenterService.createNotifyShipmentPlan(param);
                } catch (Exception e) {
                    NotifyShipmentPlanImportErrorDto error = new NotifyShipmentPlanImportErrorDto(param.getOrderFno(),param.getModelNo(),e.getMessage());
                    errList.add(error);
                    //写日志
                    if(StringUtils.isNotEmpty(param.getOrderId())){
                        doPcoLogicCenterService.writeLog(param.getOrderId(),param.getOrderItem(),e.getMessage(),"拆分订单失败");
                    }
                }
            }
            result.setErrList(errList);
            result.setSize(list.size());
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("导入文件失败: " + e.getMessage());
        }
    }

    /**
     * 导出数据
     * @param param 查询条件
     * @return 查询结果
     */
    @PostMapping("excel/export/data")
    public CommonResult<List<NotifyShipmentPlanPageDto>> excelExport(@RequestBody NotifyShipmentPlanExportPageDto param){
        //1.查询通知发货计划
        List<NotifyShipmentPlanPageDto> list  = doPcoLogicCenterService.findNotifyShipmentPlanListExport(param);
        try {
            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

}

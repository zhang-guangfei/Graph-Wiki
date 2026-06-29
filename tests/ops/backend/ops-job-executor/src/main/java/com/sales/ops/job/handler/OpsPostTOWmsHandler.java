package com.sales.ops.job.handler;

import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.dto.inventory.WmRoBarcodeDto;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsRoAddItemDto;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WmOrderTaskEnum;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops定时提交数据到wms
 * @date ：Created in 2021/10/27 17:52
 */

@Component
public class OpsPostTOWmsHandler {

    @Autowired
    private OpsWmFeignApi opsWmFeignApi;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;
    @Autowired
    private OPSProductFeignApi opsProductFeignApi;

    /**
     * bugid:17771 c14717 20250630 调库计划完纳job
     * @throws Exception
     */
    @XxlJob("finishPlanJob")
    public void finishPlanJob() throws Exception{
        XxlJobHelper.log("开始处理");
        CommonResult<String> commonResult = opsWmFeignApi.finishPlanJob();
        if(!commonResult.isSuccess()){
            XxlJobHelper.handleFail(commonResult.getMessage());
        }
        XxlJobHelper.log("已处理完成");
    }


    /**
     * 定时任务 3点更新 task读状态
     * @throws Exception
     */
    @XxlJob("updateTaskFlagToThree")
    public void updateTaskFlagToThree() throws Exception{
        XxlJobHelper.log("开始处理");
        opsWmFeignApi.updateTaskFlagToThree();
        XxlJobHelper.log("已处理完成");
    }

    /**
     * 定时任务 更新rcvdetail异常状态为初始状态
     * @throws Exception
     */
    @XxlJob("updateRcvdetailStatusTenToInit")
    public void updateRcvdetailStatusTenToInit() throws Exception{
        XxlJobHelper.log("开始处理");
        opsWmFeignApi.updateRcvdetailStatusTenToInit();
        XxlJobHelper.log("已处理完成");
    }


    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatus")
    public void sendDoToWMSChangeStatus() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatus(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatusOne")
    public void sendDoToWMSChangeStatusOne() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatusOne(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatusTwo")
    public void sendDoToWMSChangeStatusTwo() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatusTwo(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatusThree")
    public void sendDoToWMSChangeStatusThree() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatusThree(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatusFour")
    public void sendDoToWMSChangeStatusFour() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatusFour(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendDoToWMSChangeStatusFive")
    public void sendDoToWMSChangeStatusFive() throws Exception{
        XxlJobHelper.log("执行 deliveryGoodsFinishDo ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendDoToWMSChangeStatusFive(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatus")
    public void sendPcoToWMSChangeStatus() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatus(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }

    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatusOne")
    public void sendPcoToWMSChangeStatusOne() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatusOne(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }
    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatusTwo")
    public void sendPcoToWMSChangeStatusTwo() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatusTwo(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }
    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatusThree")
    public void sendPcoToWMSChangeStatusThree() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatusThree(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }
    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatusFour")
    public void sendPcoToWMSChangeStatusFour() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatusFour(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }
    /**
     * 定时 查询出库货齐 然后改下发状态 加工单
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("sendPcoToWMSChangeStatusFive")
    public void sendPcoToWMSChangeStatusFive() throws Exception{
        XxlJobHelper.log("执行 sendPcoToWMSChangeStatus ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (StringUtils.isBlank(param)) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        CommonResult bomResult =  opsWmFeignApi.sendPcoToWMSChangeStatusFive(param);
        if(bomResult != null && bomResult.isSuccess()){
            XxlJobHelper.log("已处理："+bomResult.getData().toString());
        }else{
            XxlJobHelper.log("处理失败");
        }
    }


    /**
     * 定时 updateBomToWms
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsUpdateBomToWms")
    public void opsUpdateBomToWmsHandler() throws Exception{
        XxlJobHelper.log("执行 opsUpdateBomToWmsHandler ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        CommonResult<List<ProductBom>> bomResult =  opsProductFeignApi.getBomByLimitApi(param);
        if(bomResult != null && bomResult.isSuccess()){
            List<ProductBom> list = bomResult.getData();
            for(ProductBom bom: list){
                try {
                    CommonResult updateBomResult =opsCallWmsFeignApi.updateBomToWms(bom);
                    XxlJobHelper.log(updateBomResult.getMessage());
                } catch (Exception e) {
                    XxlJobHelper.log(e.getMessage());
                }
            }
        }else{
            XxlJobHelper.log(bomResult.getMessage());
        }
    }

    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRo")
    public void opsPostWmsRoHandler() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTask(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }
    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoOne")
    public void opsPostWmsRoHandlerOne() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskOne(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }
    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoTwo")
    public void opsPostWmsRoHandlerTwo() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskTwo(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }
    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoThree")
    public void opsPostWmsRoHandlerThree() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskThree(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }
    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoFour")
    public void opsPostWmsRoHandlerFour() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskFour(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }
    /**
     * 4.3定时提交收货数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoFive")
    public void opsPostWmsRoHandlerFive() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRo----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认100条数据
            XxlJobHelper.log("param["+ param +"] invalid.");

        }

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskFive(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostRo(commonResult);
    }

    private void handlePostRo( CommonResult<List<OpsWmOrderTask>> commonResult){
        if (commonResult != null && commonResult.isSuccess()) {
            List<OpsWmOrderTask> list = commonResult.getData();
            if (list.isEmpty()) {
                XxlJobHelper.log("无可执行数据");
                return;
            }
            for (OpsWmOrderTask opsWmOrderTask : list) {
                try {
                    //2.get ro and roItem
                    CommonResult<OpsRoAddItemDto> res = opsWmFeignApi.findRoToWms(opsWmOrderTask.getWmOrderId());
                    if (res.isSuccess()) {
                        OpsRoAddItemDto opsRoAddItemDto = res.getData();
                        //3.post to wms
                        opsRoAddItemDto.setOpsWmOrderTaskId(opsWmOrderTask.getId());
                        CommonResult resWm = opsCallWmsFeignApi.updateRoToWms(opsRoAddItemDto);
                        XxlJobHelper.log(resWm.getMessage());
                    } else {
                        opsCallWmsFeignApi.updateOpsWmOrderTaskStatus(opsWmOrderTask.getId(),WmOrderTaskEnum.RO.getType(),WmOrderTaskEnum.ORDER.getType(),opsWmOrderTask.getWmOrderId(),res.getMessage());
                        XxlJobHelper.log("roId:" + opsWmOrderTask.getWmOrderId() + " res: " + res.getMessage());
                    }

                } catch (Exception e) {
                    XxlJobHelper.log("roId:" + opsWmOrderTask.getWmOrderId() + " e: " + e.getMessage());
                }
            }
        } else {
            XxlJobHelper.log(commonResult.getMessage());
        }
    }

    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCode")
    public void opsPostWmsRoBarCodeHandler() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTask(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }

    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCodeOne")
    public void opsPostWmsRoBarCodeHandlerOne() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskOne(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }

    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCodeTwo")
    public void opsPostWmsRoBarCodeHandlerTwo() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskTwo(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }

    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCodeThree")
    public void opsPostWmsRoBarCodeHandlerThree() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskThree(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }

    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCodeFour")
    public void opsPostWmsRoBarCodeHandlerFour() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskFour(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }


    /**
     * 定时提交收货箱号数据 4.4.	入库对应箱号/序列号下发
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsRoBarCodeFive")
    public void opsPostWmsRoBarCodeHandlerFive() throws Exception{
        XxlJobHelper.log("执行opsPostWmsRoBarCode----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "100";//默认长度
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        try {
            commonResult = opsWmFeignApi.findWmOrderTaskFive(condition);
        } catch (Exception e) {
            XxlJobHelper.log("orderTask无数据");
        }
        handlePostBarcode(commonResult);
    }

    private void handlePostBarcode(CommonResult<List<OpsWmOrderTask>> commonResult){
        if (commonResult != null && commonResult.isSuccess()) {
            List<OpsWmOrderTask> list = commonResult.getData();
            if (list.isEmpty()) {
                XxlJobHelper.log("无可执行数据");
                return;
            }
            for (OpsWmOrderTask opsWmOrderTask : list) {
                try {
                    //2.get barcode list
                    CommonResult<List<OpsRoBarcode>> res = opsWmFeignApi.findRoBarCodeToWms(opsWmOrderTask.getWmOrderId());
                    if (res.isSuccess()) {
                        List<OpsRoBarcode> barCodelist = res.getData();
                        WmRoBarcodeDto wmObj = new WmRoBarcodeDto();
                        wmObj.setBarCodelist(barCodelist);
                        wmObj.setWmOrderTaskId(opsWmOrderTask.getId());
                        //3.post to wms
                        CommonResult resWm = opsCallWmsFeignApi.updateRoBarCodeToWms(wmObj);
                        XxlJobHelper.log(resWm.getMessage());
                    } else {
                        opsCallWmsFeignApi.updateOpsWmOrderTaskStatus(opsWmOrderTask.getId(),WmOrderTaskEnum.RO.getType(),WmOrderTaskEnum.BARCODE.getType(),opsWmOrderTask.getWmOrderId(),res.getMessage());
                        XxlJobHelper.log("roId:" + opsWmOrderTask.getWmOrderId() + " res: " + res.getMessage());
                    }
                } catch (Exception e) {
                    XxlJobHelper.log("roId:" + opsWmOrderTask.getWmOrderId() + " e: " + e.getMessage());
                }
            }
        } else {
            XxlJobHelper.log(commonResult.getMessage());
        }
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDo")
    public void opsPostWmsDoHandler() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTask(condition);
        handPostDo(commonResult);
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDoOne")
    public void opsPostWmsDoHandlerOne() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskOne(condition);
        handPostDo(commonResult);
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDoTwo")
    public void opsPostWmsDoHandlerTwo() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskTwo(condition);
        handPostDo(commonResult);
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDoThree")
    public void opsPostWmsDoHandlerThree() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskThree(condition);
        handPostDo(commonResult);
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDoFour")
    public void opsPostWmsDoHandlerFour() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskFour(condition);
        handPostDo(commonResult);
    }

    /**
     * 定时提交发货 4.6
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsDoFive")
    public void opsPostWmsDoHandlerFive() throws Exception{
        XxlJobHelper.log("执行opsPostWmsDoHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//60条数据
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderType(WmOrderTaskEnum.DO.getType());
        condition.setLimit(param);
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskFive(condition);
        handPostDo(commonResult);
    }

    private void handPostDo(CommonResult<List<OpsWmOrderTask>> commonResult){
        if (commonResult.isSuccess()) {
            List<OpsWmOrderTask> list = commonResult.getData();
            if (list.isEmpty()) {
                XxlJobHelper.log("无可执行数据");
                return;
            }
            for (OpsWmOrderTask opsWmOrderTask : list) {
                try {
                    //2.get do and doItem
                    // bugid:12601 12836 c14717 20231212
                    CommonResult<OpsDoAndItemDto> result = opsWmFeignApi.handPostDo(opsWmOrderTask);
                    XxlJobHelper.log("doid:" + opsWmOrderTask.getWmOrderId() + " res: " + result.getMessage());
                } catch (Exception e) {
                    XxlJobHelper.log("doid:" + opsWmOrderTask.getWmOrderId() + " e: " + e.getMessage());
                }
            }
        } else {
            XxlJobHelper.log(commonResult.getMessage());
        }
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPco")
    public void opsPostWmsPcoHandler() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTask(condition);
        handlePostPco(commonResult);
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPcoOne")
    public void opsPostWmsPcoHandlerOne() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskOne(condition);
        handlePostPco(commonResult);
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPcoTwo")
    public void opsPostWmsPcoHandlerTwo() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskTwo(condition);
        handlePostPco(commonResult);
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPcoThree")
    public void opsPostWmsPcoHandlerThree() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskThree(condition);
        handlePostPco(commonResult);
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPcoFour")
    public void opsPostWmsPcoHandlerFour() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskFour(condition);
        handlePostPco(commonResult);
    }

    /**
     * 定时提交加工指令数据
     * 参数为查询行数
     * @throws Exception
     */
    @XxlJob("opsPostWmsPcoFive")
    public void opsPostWmsPcoHandlerFive() {
        XxlJobHelper.log("执行opsPostWmsPco----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");
        }
        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(param);
        condition.setWmOrderType(WmOrderTaskEnum.PCO.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult =opsWmFeignApi.findWmOrderTaskFive(condition);
        handlePostPco(commonResult);
    }

    private void handlePostPco(CommonResult<List<OpsWmOrderTask>> commonResult){
        if (commonResult.isSuccess()) {
            List<OpsWmOrderTask> list = commonResult.getData();
            if (list.isEmpty()) {
                XxlJobHelper.log("无可执行数据");
                return;
            }
            for (OpsWmOrderTask opsWmOrderTask : list) {
                try {
                    //2.get ro and roItem
                    CommonResult<OpsPcoAddItemDto> res = opsWmFeignApi.findPcoToWms(opsWmOrderTask.getWmOrderId());
                    if (res.isSuccess()) {
                        OpsPcoAddItemDto opsPcoAddItemDto = res.getData();
                        opsPcoAddItemDto.setWmOrderTaskId(opsWmOrderTask.getId());
                        //3.post to wms
                        CommonResult resWm = opsCallWmsFeignApi.updatePcoToWms(opsPcoAddItemDto);
                        XxlJobHelper.log(resWm.getMessage());
                    } else {
                        opsCallWmsFeignApi.updateOpsWmOrderTaskStatus(opsWmOrderTask.getId(),WmOrderTaskEnum.PCO.getType(),WmOrderTaskEnum.ORDER.getType(),opsWmOrderTask.getWmOrderId(),res.getMessage());
                        XxlJobHelper.log("pcoId:" + opsWmOrderTask.getWmOrderId() + " res: " + res.getMessage());
                    }
                } catch (Exception e) {
                    XxlJobHelper.log("pcoId:" + opsWmOrderTask.getWmOrderId() + " e: " + e.getMessage());
                }
            }
        } else {
            XxlJobHelper.log(commonResult.getMessage());
        }
    }
}

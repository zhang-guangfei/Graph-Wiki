package com.smc.smccloud.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.PageModel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.mapper.DictDataMapper;
import com.smc.smccloud.mapper.OrderLimitPromptMapper;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.enums.OrderLimItTypeEnum;
import com.smc.smccloud.model.order.OrderLimitDto;
import com.smc.smccloud.model.order.OrderRemindDto;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OrderLimitPromptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid:18113 c14717 增加限制客户订单提示
 * 1.excel导出
 * 2.页面查询
 * 3.查询缓冲期天数
 * 4.更新缓冲期天数
 * 5.定时job
 * 6.持久化表 *
 * @date ：Created in 2025/7/11 13:15
 */
@Slf4j
@Service
public class OrderLimitPromptServiceImpl implements OrderLimitPromptService {
    @Autowired
    private OrderLimitPromptMapper limitMapper;
    @Autowired
    private DictCommonService dictCommonService;
    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 1.excel 导出
     * @param condition
     * @return
     */
    @Override
    public ResultVo<List<OrderRemindDto>> exportExcelData(OrderRemindDto condition){
        if (condition.getRemindDateArray() != null) {
            DateTime end = DateUtil.endOfDay(condition.getRemindDateArray()[1]);
            condition.setEndRemindDate(end);
            DateTime start = DateUtil.beginOfDay(condition.getRemindDateArray()[0]);
            condition.setStartRemindDate(start);
        }
        List<OrderRemindDto> orderRemindView = limitMapper.getOrderRemindView(condition);
        if(!CollectionUtils.isEmpty(orderRemindView)){
            for(OrderRemindDto obj : orderRemindView){
                obj.setRemindType(OrderLimItTypeEnum.getDesc(obj.getRemindType()));
                obj.setOrderType(OrderTypeEnum.getCodeName(obj.getOrderType()));
            }
        }
        return ResultVo.success(orderRemindView);
    }

    /**
     * 2.页面查询
     * @param pageModel
     * @return
     * @throws OpsException
     */
    @Override
    public PageInfo<OrderRemindDto> searchStockTransferPlanByPage(PageModel<OrderRemindDto> pageModel) throws OpsException {
        OrderRemindDto condition = pageModel.getCondition();
        if (condition.getRemindDateArray() != null) {
            DateTime end = DateUtil.endOfDay(condition.getRemindDateArray()[1]);
            condition.setEndRemindDate(end);
            DateTime start = DateUtil.beginOfDay(condition.getRemindDateArray()[0]);
            condition.setStartRemindDate(start);
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo<>(limitMapper.getOrderRemindView(condition));
    }

    /**
     * 3.查询缓冲期天数
     * @return
     */
    @Override
    public ResultVo selectBufferDays(){
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "29");
        if (!dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("客户限制订单参数获取失败");
        }
        return ResultVo.success(dataTypeCodesInfo.getData().getRemark());
    }

    /**
     * 4.更新缓冲期天数
     * @param bufferDays
     * @return
     */
    @Override
    public ResultVo updateBufferDays(String bufferDays){
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "29");
        if (!dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("客户限制订单参数获取失败");
        }
        DataTypeDO updateData = new DataTypeDO();
        updateData.setId(dataTypeCodesInfo.getData().getId());
        updateData.setRemark(bufferDays);
        int i = dictDataMapper.updateById(updateData);
        return ResultVo.success();
    }

    /**
     * 5.定时job
     * @return
     */
    @Override
    public ResultVo handle(){
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "29");
        if (!dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("客户限制订单参数获取失败");
        }
        String orgCountryIds = dataTypeCodesInfo.getData().getExtNote1(); // orgCountryId
        String supplyIds = dataTypeCodesInfo.getData().getExtNote2(); // supplyId 多个用逗号隔开
        String limitTypes = dataTypeCodesInfo.getData().getExtNote3(); // 限制类型
        String bufferDays = dataTypeCodesInfo.getData().getRemark();// 缓冲天数 50
        if(StringUtils.isEmpty(orgCountryIds)
                || StringUtils.isEmpty(supplyIds)
                ||StringUtils.isEmpty(limitTypes)
                ||StringUtils.isEmpty(bufferDays)){
            return ResultVo.failure("客户限制订单参数获取失败");
        }
        //0.初始化检验数据
        HashMap<String,Integer> modelNoMap = new HashMap<String,Integer>();
        HashMap<String,Integer> customerNoMap = new HashMap<String,Integer>();

        //1.产品限制条件
        String[] splitOrgCountryIds = orgCountryIds.split(",");
        String[] splitSupplyIds = supplyIds.split(",");
        List<String> pdList = limitMapper.getProductDelivery(splitOrgCountryIds, splitSupplyIds);
        if(!CollectionUtils.isEmpty(pdList)){
            for(String modelNo : pdList){
                modelNoMap.put(modelNo,1);
            }
        }
        //2.产品限制条件
        List<String> plList = limitMapper.getProductLimit();
        if(!CollectionUtils.isEmpty(plList)){
            for(String modelNo : plList){
                modelNoMap.put(modelNo,1);
            }
        }
        //3.缓冲期加客户限制条件
        Integer i = Integer.parseInt(bufferDays);
        String[] splitLimitTypes = limitTypes.split(",");
        List<String> clList = limitMapper.getCustomerLimit(i,splitLimitTypes);
        if(!CollectionUtils.isEmpty(clList)){
            for(String customerNo : clList){
                customerNoMap.put(customerNo,1);
            }
        }
        //4.查询未完成订单数据，游标5000查询未完成订单数据status not in (7,8,9,13); 已发货 已退货 订单删除 已开票
        List<OrderLimitDto> orderList = limitMapper.getAllOrder();
        /*long objectSize = ObjectSizeCalculator.getObjectSize(orderList);
        String s = assigSize(objectSize);
        System.out.println(s);*/
        if(modelNoMap.isEmpty() || customerNoMap.isEmpty()){
            return ResultVo.failure("限制表无数据");
        }
        // 批处理100条
        List<OrderLimitDto> batch = new ArrayList<>(200);
        for (OrderLimitDto order : orderList) {
            if(modelNoMap.containsKey(order.getModelNo()) && customerNoMap.containsKey(order.getEndUser())){
                order.setCreateUser("job");
                order.setRemindType(OrderLimItTypeEnum.ORDER_LIMIT.getType());
                order.setRemindNode("");
                batch.add(order);
                if (batch.size() >= 200) {
                    //5. 持久化表
                    processBatch(batch); // 处理当前批次
                    batch.clear();       // 清空批次
                }
            }
        }
        if (!batch.isEmpty()) {
            //5. 持久化表
            processBatch(batch); // 处理剩余数据
        }
        return ResultVo.success();
    }

    // 6.持久化表
    public void processBatch(List<OrderLimitDto> list){
        if(!CollectionUtils.isEmpty(list)){
            limitMapper.insertByBatch(list);
        }
    }

    public String assigSize(long fileByte){

            if (fileByte < 1024) {
                return fileByte + " B";
            }
            if (fileByte < 1024 * 1024) {
                return String.format("%.1f", (double) fileByte / 1024) + " KB";
            }
            if (fileByte < 1024 * 1024 * 1024) {
                return String.format("%.1f", (double) fileByte / (1024 * 1024)) + " MB";
            }
            return String.format("%.1f", (double) fileByte / (1024 * 1024 * 1024)) + " GB";

    }
}

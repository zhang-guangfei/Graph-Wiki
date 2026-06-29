package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.dto.purchase.OrderSalesGPDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.mapper.purchase.PurchaseSendOrderMapper;
import com.smc.smccloud.service.PurchaseGzOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author B91717
 * @date 2022/5/11
 * @apiNote
 */
@Service
public class PurchaseGzOrderServiceImpl implements PurchaseGzOrderService {

    @Resource
    private PurchaseSendOrderMapper purchaseSendOrderMapper;

    /**
     * 广州制造订单写入
     *
     * @param jsonArray
     * @return
     */
//    @Override
//    public CommonResult<String> insertGZ(JSONArray jsonArray) {
//        // 解析json集合
//        List<OrderSalesGPDto> list = JSONUtil.toList(jsonArray, OrderSalesGPDto.class);
//        List<OrderSalesGPDto> temp;
//        Boolean result = true;
//        // 插入参数一共24个，根据参数 数量动态计算 分组数
//        int paramsSize = 80;
//        // 超过2000条时，分批操作
//        for (int i = 0; i < list.size(); i++) {
//            if (i % 90 == 0) {
//                temp = null;
//                if (i + 90 < list.size()) {
//                    temp = list.subList(i, i + 90);
//                } else {
//                    temp = list.subList(i, list.size());
//                }
//                result = purchaseSendOrderMapper.insertGzOrder(temp);
//            }
//        }
//        if (result) {
//            return CommonResult.success("写入广州制造成功");
//        }
//        return CommonResult.failure("写入广州制造失败");
//    }

    /**
     * bug12884 采购广州制造发单，参数超过2100，重写广州制造发单方法
     * @param jsonArray
     * @return
     * @throws Exception
     */
    @Override
    public CommonResult<String> insertGZ(JSONArray jsonArray) throws Exception {
        // 解析json集合
        List<OrderSalesGPDto> list = JSONUtil.toList(jsonArray, OrderSalesGPDto.class);
        StringBuilder errMsg = new StringBuilder();
        // bug12884 采购广州制造发单，参数超过2100，重写广州制造发单方法
        // 调用公共 拆分参数方法
        Map<Integer, List<OrderSalesGPDto>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, OrderSalesGPDto.class);
        for (Map.Entry<Integer, List<OrderSalesGPDto>> entry : mapBarcode.entrySet()) {
            try {
                // 写入广州制造表
                purchaseSendOrderMapper.insertGzOrder(entry.getValue());
            } catch (Exception ex) {
                errMsg.append("采购发单写入广州制造中间表失败").append(ex.getMessage());
                throw new Exception("采购发单写入广州制造中间表失败" + "->错误：" + ex);
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return CommonResult.failure(errMsg.toString());
        }
        return CommonResult.success("写入广州制造成功，共计"+ list.size());
    }
}

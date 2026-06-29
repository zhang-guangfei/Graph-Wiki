package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DlvSiteEnum;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.DeliveryAddressInfo;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.UpdateOrderInfoResultVO;
import com.smc.smccloud.model.order.orderEdit.UpMasterInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderAddressInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderDlvDateInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderExpDlvType;
import com.smc.smccloud.service.OrderEditService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2022/10/20 14:21
 * @Descripton TODO
 */
@Slf4j
@Service
public class OrderEditServiceImpl implements OrderEditService {

    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;

    /**
     * params: orderNo, dlvDate  warehouseSendDate
     * 批量变更货期
     */
    @Override
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpDlvDate(List<UpOrderDlvDateInfo> orderDeliveryDate) {

        if (CollectionUtils.isEmpty(orderDeliveryDate)){
            return null;
        }
        log.info("批量变更货期 {}", JSONUtil.toJsonPrettyStr(orderDeliveryDate));

        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult = null;
        UpdateOrderInfoResultVO resultVO;
        Map<String,UpdateOrderInfoResultVO> map = new HashMap<>(orderDeliveryDate.size());

        for (UpOrderDlvDateInfo item : orderDeliveryDate) {

            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
            opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());

            opsOrderModifyDto.setMaster(false);

            if (item.getDlvDate() != null) {
                // 是否修改货期
                opsOrderModifyDto.setUpdateDate(true);
                // 设置客户货期
                opsOrderModifyDto.setDlvDate(item.getDlvDate());
            }
            opsOrderModifyDto.setUpdateAddress(false);
            UserDto userDto = new UserDto();
            userDto.setUserName(item.getLoginUserId());
            opsOrderModifyDto.setUserDto(userDto);
            opsOrderModifyDto.setReason("门户申请变更");
            log.info("批量修改订单交货期(batchUpdateDlvDate) 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);

            log.info("批量修改订单交货期[batchUpdateDlvDate] 结果 = {}", JSON.toJSONString(commonResult));
            if (commonResult == null) {
                return null;
            }
            resultVO = new UpdateOrderInfoResultVO();
            if ("200".equals(String.valueOf(commonResult.getCode()))) {
                resultVO.setSuccess(true);
            } else {
                resultVO.setSuccess(false);
            }
            resultVO.setCode(String.valueOf(commonResult.getCode()));
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            if (resultMap.containsKey("变更交货期")) {
                String val = resultMap.get("变更交货期");
                resultVO.setMessage(val);
            }
            map.put(orderNoInfo.getFullOrderNo(), resultVO);

        }
        return ResultVo.success(map);
    }

    /**
     * 批量变更地址
     * @param upOrderAddressInfo 完整订单号 操作人 地址实体
     * @return
     */
    @Override
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpdateAddress(List<UpOrderAddressInfo> upOrderAddressInfo) {
        if (CollectionUtils.isEmpty(upOrderAddressInfo)){
            return null;
        }
        log.info("批量变更地址 {}", JSONUtil.toJsonPrettyStr(upOrderAddressInfo));

        OpsOrderModifyDto opsOrderModifyDto;
        Orderdlvdata orderdlvdata;
        UserDto userDto;
        CommonResult commonResult;
        UpdateOrderInfoResultVO resultVO;

        Map<String,UpdateOrderInfoResultVO> map = new HashMap<>(upOrderAddressInfo.size());
        //dlvFlag的取值范围
        List<String> dlvFlagEnumValues = new ArrayList<>();
        for (DlvSiteEnum value : DlvSiteEnum.values()) {
            dlvFlagEnumValues.add(value.getCode().toString());
        }
        for (UpOrderAddressInfo item : upOrderAddressInfo) {

            if (item.getAddress() == null || StringUtils.isBlank(item.getOrderNo())) {
                continue;
            }
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            //参数校验
            DeliveryAddressInfo deliveryAddressInfo = item.getAddress();
            if (StringUtils.isBlank(deliveryAddressInfo.getDlvFlag())) {
                    resultVO = new UpdateOrderInfoResultVO();
                    resultVO.setSuccess(false);
                    resultVO.setCode("500");
                    resultVO.setMessage("发货方式不能为空");
                    log.info("修改地址[batchUpdateAddress]{},{}", orderNoInfo.getFullOrderNo(), JSON.toJSONString(resultVO));
                    map.put(orderNoInfo.getFullOrderNo(), resultVO);
                    continue;
            } else {
                if (!dlvFlagEnumValues.contains(deliveryAddressInfo.getDlvFlag())) {
                    resultVO = new UpdateOrderInfoResultVO();
                    resultVO.setSuccess(false);
                    resultVO.setCode("500");
                    resultVO.setMessage("发货方式解析失败，只能为1直发客户，2直发营业所，3自提");
                    log.info("修改地址[batchUpdateAddress]{},{}", orderNoInfo.getFullOrderNo(), JSON.toJSONString(resultVO));
                    map.put(orderNoInfo.getFullOrderNo(), resultVO);
                    continue;
                }
            }
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
            opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());
            orderdlvdata = new Orderdlvdata();

            DeliveryAddressInfo addressInfo = item.getAddress();

            Class<? extends DeliveryAddressInfo> aClass = addressInfo.getClass();
            Field[] fields = aClass.getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (null != field.get(addressInfo) && StringUtils.isBlank(field.get(addressInfo).toString())) {
                        field.set(addressInfo, null);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 是否修改地址
            opsOrderModifyDto.setUpdateAddress(true);
            orderdlvdata.setOrderno(orderNoInfo.getOrderNo());
            orderdlvdata.setItemno(orderNoInfo.getItemNo());
            // 收货地址
            orderdlvdata.setDlvaddress(addressInfo.getDlvAddress());
            // 联系人
            orderdlvdata.setContactpsn(addressInfo.getContactPerson());
            // 联系电话
            orderdlvdata.setTelno(addressInfo.getContactPhone());
            // 省.市.区
            orderdlvdata.setProvince(addressInfo.getProvince());
            orderdlvdata.setCity(addressInfo.getCity());
            orderdlvdata.setDistrict(addressInfo.getRegion());
            // 身份证号
            orderdlvdata.setIdcard(addressInfo.getPersonId());
            // 客户名称
            orderdlvdata.setCstmname(addressInfo.getCustomerName());
            orderdlvdata.setPostcode(addressInfo.getPostCode());
            // 发货方式  发货方式-直发客户/直发营业所（1：直发客户；2：直发营业所;3:自提)
            orderdlvdata.setDlvflag(addressInfo.getDlvFlag());

            if (StringUtils.isNotBlank(addressInfo.getEmail())) {
                orderdlvdata.setEmail(addressInfo.getEmail().replaceAll("/",";"));
            }

            opsOrderModifyDto.setAddress(orderdlvdata);

            // 操作人
            userDto = new UserDto();
            userDto.setUserName(item.getLoginUserId());
            opsOrderModifyDto.setUserDto(userDto);
            // 变更原因
            opsOrderModifyDto.setReason("门户申请变更");
            log.info("修改地址参数[batchUpdateAddress] = {}", JSONObject.toJSONString(opsOrderModifyDto));
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            log.info("修改地址[batchUpdateAddress]结果 = {}", JSON.toJSONString(commonResult));
            if (commonResult == null) {
                return null;
            }
            resultVO = new UpdateOrderInfoResultVO();
            if ("200".equals(String.valueOf(commonResult.getCode()))) {
                resultVO.setSuccess(true);
            } else {
                resultVO.setSuccess(false);
            }
            resultVO.setCode(String.valueOf(commonResult.getCode()));
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            if (resultMap.containsKey("变更发货地址")) {
                String val = resultMap.get("变更发货地址");
                resultVO.setMessage(val);
            }
            map.put(orderNoInfo.getFullOrderNo(), resultVO);
        }
        return ResultVo.success(map);
    }

    /**
     * 批量变更特发
     * @param upOrderExpDlvType  完整订单号 操作人 特发/普通
     * @return
     */
    @Override
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpExpDlvType(List<UpOrderExpDlvType> upOrderExpDlvType) {
        if (CollectionUtils.isEmpty(upOrderExpDlvType)){
            return null;
        }
        log.info("批量修改特发 {}",JSONUtil.toJsonPrettyStr(upOrderExpDlvType));

        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult = null;
        UpdateOrderInfoResultVO resultVO;
        Map<String,UpdateOrderInfoResultVO> map = new HashMap<>(upOrderExpDlvType.size());

        for (UpOrderExpDlvType item : upOrderExpDlvType) {

            if (StringUtils.isBlank(item.getOrderNo()) || StringUtils.isBlank(item.getSpecialNormal())) {
                continue;
            }

            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
            opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());

            // 是否特发
            if ("普通".equals(item.getSpecialNormal())) {
                opsOrderModifyDto.setDlvSpecial(false);
            } else if ("特发".equals(item.getSpecialNormal())) {
                opsOrderModifyDto.setDlvSpecial(true);
            }
            // 操作人
            UserDto userDto = new UserDto();
            userDto.setUserName(item.getLoginUserId());
            opsOrderModifyDto.setUserDto(userDto);
            opsOrderModifyDto.setReason("门户申请变更");
            log.info("修改特发普通(batchUpExpDlvType) 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            log.info("修改特发普通[batchUpExpDlvType] 结果 = {}", JSON.toJSONString(commonResult));
            if (commonResult == null) {
                return null;
            }
            resultVO = new UpdateOrderInfoResultVO();
            if ("200".equals(String.valueOf(commonResult.getCode()))) {
                resultVO.setSuccess(true);
            } else {
                resultVO.setSuccess(false);
            }
            resultVO.setCode(String.valueOf(commonResult.getCode()));
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            if (resultMap.containsKey("变更特发")) {
                String val = resultMap.get("变更特发");
                resultVO.setMessage(val);
            }
            map.put(orderNoInfo.getFullOrderNo(), resultVO);
        }
        return ResultVo.success(map);
    }

    @Override
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpMastInfo(List<UpMasterInfo> upMasterInfos) {

        if (CollectionUtils.isEmpty(upMasterInfos)) {
            return ResultVo.success();
        }

        log.info("批量变更主单信息参数: {}", JSONArray.toJSONString(upMasterInfos));
        long lstart = System.currentTimeMillis();
        OpsOrderModifyDto opsOrderModifyDto;
        UserDto userDto;
        CommonResult commonResult;
        UpdateOrderInfoResultVO resultVO;
        Map<String,UpdateOrderInfoResultVO> map = new HashMap<>(upMasterInfos.size());
        for (UpMasterInfo item : upMasterInfos) {
            // 修改的是订单主要信息
            opsOrderModifyDto = new OpsOrderModifyDto();
            if (StringUtils.isBlank(item.getOrderNo())) {
                continue;
            }
            opsOrderModifyDto.setOrderId(item.getOrderNo());
            opsOrderModifyDto.setMaster(true);

            if (StringUtils.isBlank(item.getIntensiveNo()) && StringUtils.isBlank(item.getDeliveryEntireNo())) {
                resultVO = new UpdateOrderInfoResultVO();
                resultVO.setSuccess(false);
                resultVO.setCode("500");
                resultVO.setMessage(item.getOrderNo()+"变更集约方式和出库方式的值为空");
                map.put(item.getOrderNo(), resultVO);
                continue;
            }

            if(StringUtils.isNotBlank(item.getIntensiveNo())) {
                opsOrderModifyDto.setDlvType(item.getIntensiveNo());
            }
            if (StringUtils.isNotBlank(item.getDeliveryEntireNo())) {
                opsOrderModifyDto.setDlvEntire(item.getDeliveryEntireNo());
            }
            userDto = new UserDto();
            userDto.setUserName(item.getLoginUserId());
            opsOrderModifyDto.setUserDto(userDto);
            opsOrderModifyDto.setReason("门户申请变更");
            log.info("订单注单修改处理耗时(s) {}, 毫秒(ms) : {} ", (System.currentTimeMillis() - lstart)/1000,(System.currentTimeMillis() - lstart));
            log.info("订单主单修改接口参数[UpdateOrderInfoResultVO] = {}", JSON.toJSONString(opsOrderModifyDto));
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            log.info("订单主单修改接口结果[UpdateOrderInfoResultVO] = {}", JSON.toJSONString(commonResult));
            log.info("订单注单修改接口处理耗时(s) {}, 毫秒(ms) : {} ", (System.currentTimeMillis() - lstart)/1000,(System.currentTimeMillis() - lstart));
            if (commonResult == null) {
                return null;
            }
            resultVO = new UpdateOrderInfoResultVO();
            if ("200".equals(String.valueOf(commonResult.getCode()))) {
                resultVO.setSuccess(true);
            } else {
                resultVO.setSuccess(false);
            }
            resultVO.setCode(String.valueOf(commonResult.getCode()));
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            resultVO.setData(resultMap);
            map.put(item.getOrderNo(), resultVO);
        }
        return ResultVo.success(map);
    }

}

package com.sales.ops.serviceimpl.impinvoice;


import com.alibaba.fastjson.JSON;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.AdapterOpsTranslationConfigMapper;
import com.sales.ops.db.dao.OpsImpinvoiceFailLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.ImpInvoiceSysnDao;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;
import com.sales.ops.enums.invoice.ImpInvoiceConstants;
import com.sales.ops.enums.invoice.InvoiceTypeAdapterEnum;
import com.sales.ops.feign.OpsMailApi;
import com.sales.ops.service.impinvoice.ImpInvoiceSysnCommonService;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImpInvoiceSysnCommonServiceImpl implements ImpInvoiceSysnCommonService {


    @Resource
    private OPSRedisUtils opsRedisUtils;
    @Resource
    private OpsImpinvoiceFailLogMapper poImpFailLogMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private ImpInvoiceSysnDao impInvoiceSysnDao;

    @Resource
    private AdapterOpsTranslationConfigMapper adapterOpsTranslationConfigMapper;

    @Resource
    private OpsMailApi opsMailApi;


    @Override
    public String getWarehouseCodeBySMCCode(String smcCode, int extNote) {
        String key = "ops:datacode:2048";
        Object obj = opsRedisUtils.hGet(key, smcCode);
        DataCodeVO codeInfo;
        if (obj == null) {
            String classCode = "2048";
            ResultVo<List<DataCodeVO>> listResult = dictDataServiceFeignApi.getDataCodes(classCode);
            if (!listResult.isSuccess()) {
                log.error("getSMCCodeToWarehouseMap error: {}", listResult.getMessage());
                return null;
            }

            Map<String, Object> map = new HashMap<>(listResult.getData().size());
            for (DataCodeVO codeVO : listResult.getData()) {
                map.put(codeVO.getCode(), JSON.toJSONString(codeVO));
            }
            opsRedisUtils.hPutAll(key, map);
            opsRedisUtils.expire(key, 60 * 60 * 8);
            obj = map.get(smcCode);
        }

        if (obj != null) {
            codeInfo = JSON.parseObject(obj.toString(), DataCodeVO.class);
            if (extNote == 2) {
                if (StringUtils.isNotBlank(codeInfo.getExtNote2())) {
                    return codeInfo.getExtNote2();
                }
            } else if (extNote == 3) {
                if (StringUtils.isNotBlank(codeInfo.getExtNote3())) {
                    return codeInfo.getExtNote3();
                }
            }
            return codeInfo.getExtNote1();
        }

        return null;
    }


    @Override
    public Date calcEsArrivalDate(String warehouseCode, String supplierCode, String transType, Date dlvDate) {
        if (PublicUtil.isEmpty(warehouseCode)) {
            return calcEsArrivalDate(supplierCode, transType, dlvDate);
        }
        String key = ImpInvoiceConstants.REDIS_KEY_TRANS_DAYS;
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey==null || !hasKey) {
            ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("2073");
            if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
                throw new BusinessException("获取数据字典失败 2073");
            }
            for (DataCodeVO dataCodeVO : dataCodes.getData()) {
                opsRedisUtils.hPut(key, dataCodeVO.getCode(), dataCodeVO.getExtNote1());
            }
            opsRedisUtils.expire(key, 86400);
        }

        Object objTransDays = opsRedisUtils.hGet(key, supplierCode + "-" + warehouseCode);
        if (objTransDays != null) {
            String strDays = objTransDays.toString();
            String[] arrsDays = strDays.split("-");
            if (arrsDays.length == 4) {
                switch (Optional.ofNullable(transType).orElse("")) {
                    case "1": // 空运
                        return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
                    case "4": // 快船
                        return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
                    case "0": // 海运
                        return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
                    default:
                        return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
                }
            }
        } else {
            if ("GZ,CN,CM,CT,YZ".contains(supplierCode)) {
                objTransDays = opsRedisUtils.hGet(key, "cnother");
                if (objTransDays != null) {
                    String strDays = objTransDays.toString();
                    String[] arrsDays = strDays.split("-");
                    if (arrsDays.length == 4) {
                        switch (transType) {
                            case "1": // 空运
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
                            case "3": // 陆运
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
                            case "5": // 铁路
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
                            default:
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
                        }
                    }
                }
            } else {
                objTransDays = opsRedisUtils.hGet(key, "other");
                if (objTransDays != null) {
                    String strDays = objTransDays.toString();
                    String[] arrsDays = strDays.split("-");
                    if (arrsDays.length == 4) {
                        switch (Optional.ofNullable(transType).orElse("")) {
                            case "1": // 空运
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
                            case "4": // 快船
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
                            case "0": // 海运
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
                            default:
                                return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
                        }
                    }
                }
            }
        }

        return null;
    }


    /**
     * 计算预计到达日期
     *
     * @param supplierCode
     * @param transType
     * @param dlvDate
     * @return
     */
    @Override
    public Date calcEsArrivalDate(String supplierCode, String transType, Date dlvDate) {
        if (PublicUtil.isEmpty(supplierCode) ||
                PublicUtil.isEmpty(dlvDate)) {
            return null;
        }
        if (PublicUtil.isEmpty(transType)) {
            transType = "0";
        }
        int dayNum = 0;
        // 海(0) 18天 快船(4) 12 空(1) 7
        switch (supplierCode) {
            case "JP":  // 日本
                switch (transType) {
                    case "1": // 空运
                        // 从redis中获取所需运输天数
                        Object o = opsRedisUtils.get("ops:EsArrivalDateByTranType:" + "JP" + 1);
                        if (o == null) {
                            ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
                            if (jp.isSuccess() && jp.getData() != null) {
                                DataTypeVO data = jp.getData();
                                opsRedisUtils.set("ops:EsArrivalDateByTranType:" + "JP" + 1, data.getExtNote1(), 3600 * 24 * 7);
                                dayNum = Integer.parseInt(data.getExtNote1());
                            }
                        } else {
                            dayNum = Integer.parseInt(o.toString());
                        }
                        return DateUtil.addDay(dlvDate, dayNum);
                    case "4": // 快船
                        // 从redis中获取所需运输天数
                        Object obj = opsRedisUtils.get("ops:EsArrivalDateByTranType:" + "JP" + 4);
                        if (obj == null) {
                            ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
                            if (jp.isSuccess() && jp.getData() != null) {
                                DataTypeVO data = jp.getData();
                                opsRedisUtils.set("ops:EsArrivalDateByTranType:" + "JP" + 4, data.getExtNote2(), 3600 * 24 * 7);
                                dayNum = Integer.parseInt(data.getExtNote2());
                            }
                        } else {
                            dayNum = Integer.parseInt(obj.toString());
                        }
                        return DateUtil.addDay(dlvDate, dayNum);
                    case "0": // 海运
                        // 从redis中获取所需运输天数
                        Object obj2 = opsRedisUtils.get("ops:EsArrivalDateByTranType:" + "JP" + 0);
                        if (obj2 == null) {
                            ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
                            if (jp.isSuccess() && jp.getData() != null) {
                                DataTypeVO data = jp.getData();
                                opsRedisUtils.set("ops:EsArrivalDateByTranType:" + "JP" + 0, data.getExtNote3(), 3600 * 24 * 7);
                                dayNum = Integer.parseInt(data.getExtNote3());
                            }
                        } else {
                            dayNum = Integer.parseInt(obj2.toString());
                        }
                        return DateUtil.addDay(dlvDate, dayNum);
                    default:
                        return DateUtil.addDay(dlvDate, 18);
                }
            case "HK": // 香港
                return DateUtil.addDay(dlvDate, 4);
            case "CM": // 北京
                return dlvDate;
            case "GZ": // 广州
                return dlvDate;
            case "CT": // 广州
                return dlvDate;
            default:
                return dlvDate;
        }
    }

    /**
     * 根据供应商代码获取关务发票类型
     * @param supplierCode
     * @return
     */
    @Override
    public int invoiceTypeConvert(String supplierCode,String type) {
        if (StringUtils.isBlank(supplierCode)) {
            return InvoiceTypeAdapterEnum.OTHER.getCode();
        }
        if ("JP".equals(supplierCode)) {
            return InvoiceTypeAdapterEnum.JP.getCode();
        }
        if (type.equalsIgnoreCase("GW")) {
            if ("CN,CM,CT,TZ".contains(supplierCode)) {
                // 三方: 中国工厂/北京工厂/天津工厂/上海
                return InvoiceTypeAdapterEnum.TRIPARTITE.getCode();
            } else {
                return InvoiceTypeAdapterEnum.THREE_COUNTRY.getCode();
            }
        }
        else {
            if ("CN,CM,CT,TZ,GZ".contains(supplierCode)) {
                // 国内工厂: 中国工厂/北京工厂/天津工厂/上海
                return InvoiceTypeAdapterEnum.CM.getCode();
            } else if ("GN".equalsIgnoreCase(supplierCode)) { // bug-9080
                return InvoiceTypeAdapterEnum.OTHER.getCode();
            } else {
                return InvoiceTypeAdapterEnum.THREE_COUNTRY.getCode();
            }
        }
    }


    // 构建供应商iD,name转换mapper
    @Override
    public Map<String, String> getSuppilyNameMapper() {
        List<Supplier> supplierList =  impInvoiceSysnDao.getSupplierName();
        return supplierList.stream().collect(Collectors.toMap(
                Supplier::getId, Supplier::getName,
                (val1, val2) -> val2
        ));
    }

    /**
     * 获取状态
     *
     * @param statusCode
     *            0-预到货; 1-到货
     * @return 预计到货: 0 --> 1; 已到货: 1 --> 2;
     */
    @Override
    public Integer getGWMasterStatus(String statusCode) {
        if ("0".equals(statusCode)) {
            return 1; // "0" --> 1 预计到货
        } else if ("1".equals(statusCode)) {
            return 2; // "1" --> 2 已到货
        } else if ("2".equals(statusCode)) {
            return 2; // "2" --> 2 已到货
        } else {
            return null;
        }
    }

    /**
     * 判断是否无商业价值SMCCode
     *
     * @param smcCode
     * @return boolean
     */
    @Override
    public boolean isNoCommercialValue(String smcCode) {
        // boolean rtnval= Arrays.binarySearch(Constants.NO_COMMERCIAL_VALUE,
        // smcCode) > 0; //查询的数组必须是有序的，如果不是有序的话，使用此方法是没有用的
        boolean rtnval = Arrays.asList(ImpInvoiceConstants.NO_COMMERCIAL_VALUE).contains(smcCode);
        return rtnval;
    }

    /**
            * 日本ship data出厂地转供应商代码
     * @param whereCode
     * @return
     */
    @Override
    public String getSupplierCodeFromJPWhereCode(String whereCode) {
        if(PublicUtil.isNotEmpty(whereCode))
        {
            return "";
        }
        switch (whereCode)
        {
            case "J":
                return "JP";
            case "S":
                return "SG";
            case "K":
                return "KR";
            case "V":
                return "VN";
            case "I":
                return "IN";
            case "U":
                return "US";
            case "C":
                return "CN";
            case "T":
                return "TJ";
            default:
                return whereCode;
        }
    }

    @Override
    public String convertCNMSupplierCode(String code) {
        switch (code) {
            case "BJ":
                return "CM";
            case "CN":
                return "CN";
            case "SH":
                return "TZ";
            case "TJ":
                return "CT";
            default:
                return code;
        }
    }

//    public ResultVo<Date> getLastExecTimeFromSyncMidManage(String jobName) {
//        OpsPoSyncMiddManage
//
//        LambdaQueryWrapper<OpsPoSyncMiddManageDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .eq(OpsPoSyncMiddManageDO::getJobName,jobName)
//                .eq(OpsPoSyncMiddManageDO::getDelFlag,0) // 未删除
//                .eq(OpsPoSyncMiddManageDO::getJobStatus,1); // 开启
//        OpsPoSyncMiddManageDO opsPoSyncMiddManageDO = opsPoSyncMiddManageMapper.selectOne(queryWrapper);
//        if (opsPoSyncMiddManageDO == null || opsPoSyncMiddManageDO.getExecLastTime() == null) {
//            return ResultVo.failure(jobName+"无效或者未找到上次执行时间");
//        }
//        return ResultVo.success(opsPoSyncMiddManageDO.getExecLastTime());
//    }

//    @Override
//    public ResultVo<String> upSyncMidManageLastExcTime(String jobName, Date date) {
//        if (StringUtils.isBlank(jobName) || date == null) {
//            return ResultVo.failure("job名称/执行日期不可为空");
//        }
//        LambdaUpdateWrapper<OpsPoSyncMiddManageDO> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper
//                .eq(OpsPoSyncMiddManageDO::getJobName,jobName)
//                .set(OpsPoSyncMiddManageDO::getExecLastTime,date)
//                .set(OpsPoSyncMiddManageDO::getUpdateTime, date);
//        opsPoSyncMiddManageMapper.update(null,updateWrapper);
//        return ResultVo.success("设置成功");
//    }


    /**
     * 同步适配器数据时，异常记录写入异常表中
     *
     * @param sourceType
     * @param sourceId
     * @param sourceTable
     * @param errorDataStr
     * @param remark
     * @return
     */
    @Override
    public ResultVo<String> insertPoFailLog(String sourceType, String sourceId, String sourceTable, String errorDataStr, String remark) {
        // 同一个id,存在未处理单据时，不再写入
        OpsImpinvoiceFailLogExample example = new OpsImpinvoiceFailLogExample();
        example.createCriteria().andSourceIdEqualTo(sourceId).andSourceTableEqualTo(sourceTable).andHandleStatusEqualTo(0);
        if (poImpFailLogMapper.countByExample(example) > 0) {
            return ResultVo.failure("该单据已存在未处理记录");
        }
        OpsImpinvoiceFailLog opsImpinvoiceFailLogDO = new OpsImpinvoiceFailLog();
        opsImpinvoiceFailLogDO.setHandleStatus(0);
        opsImpinvoiceFailLogDO.setContent(errorDataStr);
        opsImpinvoiceFailLogDO.setSourceId(sourceId);
        opsImpinvoiceFailLogDO.setSourceTable(sourceTable);
        opsImpinvoiceFailLogDO.setSourceType(sourceType);
        opsImpinvoiceFailLogDO.setCreateTime(new Date());
        opsImpinvoiceFailLogDO.setCreateUser(ImpInvoiceConstants.IMP_INVOICE_SYSN);
        opsImpinvoiceFailLogDO.setRemark(remark);
        poImpFailLogMapper.insertSelective(opsImpinvoiceFailLogDO);
        // 写入异常表成功后，同时推送异常邮件
        impInvoiceMailMessage(errorDataStr);
        return ResultVo.success("写入成功");
    }

    @Override
    public ResultVo<List<OpsImpinvoiceFailLog>> getUnHandleFailLog(String sourceType) {
        OpsImpinvoiceFailLogExample example = new OpsImpinvoiceFailLogExample();
        example.createCriteria().andSourceIdIsNotNull().andHandleStatusEqualTo(0).andSourceTypeEqualTo(sourceType);
        List<OpsImpinvoiceFailLog> opsImpinvoiceFailLogDOList = poImpFailLogMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(opsImpinvoiceFailLogDOList)) {
            return ResultVo.failure();
        }
        return ResultVo.success(opsImpinvoiceFailLogDOList);
    }

    @Override
    public ResultVo<String> updateHandleFailLog(List<OpsImpinvoiceFailLog> list) {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("未找到对应异常数据！");
        }
        list.forEach(poImpFailLogDO -> {
            poImpFailLogDO.setHandleStatus(1);
            poImpFailLogDO.setHandleTime(new Date());
            poImpFailLogMapper.updateByPrimaryKeySelective(poImpFailLogDO);
        });
        return ResultVo.success("更新成功");
    }

    /**
     * 根据配置，获取转换适配器供应商为OPS供应商
     * @param code
     * @return
     */
    @Override
    public ResultVo<String> getOpsSupplierByConfig(String code, String codeType) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(codeType)) {
            return ResultVo.failure("入参不可为空");
        }
        String key ="";
        if(codeType.equalsIgnoreCase("supplierid")) {
            key = ImpInvoiceConstants.REDIS_KEY_SUPPLIER_TRANS + code;
        } else {
            key = ImpInvoiceConstants.REDIS_KEY_OPS_TRANSTYPE + code;
        }
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey != null && hasKey) {
            Object o = opsRedisUtils.get(key);
            if (o != null) {
                return ResultVo.success(o.toString());
            }
        }
        // bug15213 交付系统，新imp从集团采购导入时，适配器issuer_code字段转换调整
        AdapterOpsTranslationConfigExample example = new AdapterOpsTranslationConfigExample();
        example.createCriteria().andAdapterCodeEqualTo(code).andCodeTypeEqualTo(codeType);
        List<AdapterOpsTranslationConfig> opsImpinvoiceFailLogDOList = adapterOpsTranslationConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(opsImpinvoiceFailLogDOList)) {
            AdapterOpsTranslationConfig opsImpinvoiceFailLogDO = opsImpinvoiceFailLogDOList.get(0);
            opsRedisUtils.set(key,opsImpinvoiceFailLogDO.getOpsCode(),60 * 60 * 24);
            return ResultVo.success(opsImpinvoiceFailLogDO.getOpsCode());
        }
        return ResultVo.failure("未获取到"+code+"对应的OPS转换关系"+codeType);
    }


    /**
     * imp导入异常时，写入异常表的同时，增加异常邮件的发送
     *
     * @param message
     */
    @Override
    public CommonResult impInvoiceMailMessage(String message) {
        MailDto mailVo = new MailDto();
        mailVo.setTo("duanxiaofeng@smc.com.cn,jiaweiyi@smc.com.cn");
        mailVo.setNickName("SMC(中国)有限公司");
//        mailVo.setCc("xuxiaoying@smc.com.cn,smccnorder@smc.com.cn,order@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn,tangqixu@smc.com.cn,duanxiaofeng@smc.com.cn,wangqian@smc.com.cn");
        mailVo.setSubject("新impinvoice导入出现错误,请ops_impinvoice_fail_log表中的异常信息");
        StringBuffer con = new StringBuffer();
        con.append("<h4>" + StringUtils.substring(message, 0, 200) + "</h4>\r\n ");
        mailVo.setText(con.toString());
        CommonResult r = opsMailApi.sendMailToDb(mailVo);
        return r;
    }

}

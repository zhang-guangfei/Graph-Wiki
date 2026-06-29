package com.sales.ops.serviceimpl.ips;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.db.batchdao.PoBatchDao;
import com.sales.ops.db.dao.OpsPoAutosendRemarkConfigMapper;
import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.ips.PsiDataType;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.ips.PsiPurchaseSendOverSeaService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: B91717
 * @time: 2025/10/23 16:19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PsiPurchaseSendOverSeaServiceImpl implements PsiPurchaseSendOverSeaService {
    @Resource
    private OpsPoAutosendRemarkConfigMapper opsPoAutosendRemarkConfigMapper;

    @Resource
    private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;

    /**
     * OPS向PSI推送采购订单-海外三国
     *
     */
    @Override
    public ResultVo<String> writeToExcel(String warehouseid, String supplierNo, List<OpsPurchaseinvoice> list, String fullname,
                                     Integer payment, OpsWarehouse opswarehouse, Map<String, Boolean> resMap,
                                     Map<String, OpsCustomer> customerMap, List<OpsPoAutosendRemarkConfig> configList, PsiDataType psiDataType, String batchno,String pdfPath) throws Exception {
        // 将数据转换为PSI可以接受的格式
        String fileEmail = "";
        // 备注加客户英文名的国家
        int enameflag = 0;
        ResultVo<List<DataCodeVO>> ename = dictDataServiceFeignApi.getDataCodes("3010");
        if (ename.isSuccess() && !CollectionUtils.isEmpty(ename.getData())) {
            for (DataCodeVO temp : ename.getData()) {
                if (StringUtils.equals(temp.getCode(), supplierNo)) {
                    enameflag = 1;
                    break;
                }
            }
        }
        for (OpsPurchaseinvoice item : list) {
            // bug11473自动发单贩卖限制品备注增加客户英文名
            // bug 20054,用end_user替换userno
            String remark = StringUtils.isNotBlank(item.getServerremark()) ? item.getServerremark() : "";
            if (resMap.containsKey(item.getModelno()) || enameflag == 1) {
                if (StringUtils.isBlank(item.getEndUser()) && StringUtils.isNotBlank(item.getCustomerno())
                        && customerMap.containsKey(item.getCustomerno())) {
                    if (StringUtils.isNotBlank(customerMap.get(item.getCustomerno()).getEname()))
                        remark = remark + " " + customerMap.get(item.getCustomerno()).getEname();
                    else if (StringUtils.isNotBlank(customerMap.get(item.getCustomerno()).getAliasEname())) {
                        remark = remark + " " + customerMap.get(item.getCustomerno()).getAliasEname();
                    } else {
                        if (enameflag == 1) {
                            remark = remark + " " + customerMap.get(item.getCustomerno()).getName();
                        }
                    }
                } else if (StringUtils.isNotBlank(item.getEndUser()) && customerMap.containsKey(item.getEndUser())) {
                    if (StringUtils.isNotBlank(customerMap.get(item.getEndUser()).getEname()))
                        remark = remark + " " + customerMap.get(item.getEndUser()).getEname();
                    else if (StringUtils.isNotBlank(customerMap.get(item.getEndUser()).getAliasEname())) {
                        remark = remark + " " + customerMap.get(item.getEndUser()).getAliasEname();
                    } else {
                        if (enameflag == 1) {
                            remark = remark + " " + customerMap.get(item.getEndUser()).getName();
                        }
                    }
                }
            }
            // bug11473物料号
            List<OpsPoAutosendRemarkConfig> temp = configList.stream().filter(s -> StringUtils.equals(item.getModelno(),
                            s.getModelno())
                            && (StringUtils.equals(item.getDeptno(), s.getDeptno()) || StringUtils.isBlank(s.getDeptno())))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(temp)) {
                for (OpsPoAutosendRemarkConfig o : temp) {
                    remark = remark + " " + o.getRemark();
                }
            }
            item.setServerremark(remark);
        }
        // 发送给PSI新系统
        List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert = ipsPurchaseSendCommonService
                .ipsSendOrderConvert(list, null,  supplierNo, batchno, payment,"hopeReceiveWarehouse");
        String batchCnno = ipsSendOrderConvert.get(0).getBatchNo(); // CNNO批次号优化
        // 打印发送json信息
        log.info("Oversea发送到IPS的json信息：" + JSON.toJSONString(ipsSendOrderConvert));
        // 调用feign接口，发送到IPS表中
        CommonResult<String> ipsResult = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsSendOrderConvert);
        if (!ipsResult.isSuccess()) { // 抛出异常回滚
            throw Exceptions.OpsException(ipsResult.getMessage());
        }
        return ResultVo.success("PSI海外订单推送成功，共计推送" + list.size() + "条数据");
    }
}

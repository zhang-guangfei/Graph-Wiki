package com.sales.ops.serviceimpl.po;

import com.sales.ops.service.po.PurchaseImpInfoToPSIService;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.AdapterOpsTranslationConfigMapper;
import com.sales.ops.db.entity.AdapterOpsTranslationConfig;
import com.sales.ops.db.entity.AdapterOpsTranslationConfigExample;
import com.sales.ops.enums.invoice.ImpInvoiceConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PurchaseImpInfoToPSIServiceImpl implements PurchaseImpInfoToPSIService {

    @Resource
    private OPSRedisUtils opsRedisUtils;

    @Resource
    private AdapterOpsTranslationConfigMapper adapterOpsTranslationConfigMapper;

    /**
     * 根据配置，转换适配器供应商为OPS供应商
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

    // 根据配置，转换OPS供应商为适配器供应商
    @Override
    public ResultVo<String> getAdapterSupplierByConfig(String codeFromOps, String codeType) {
        if (StringUtils.isBlank(codeFromOps) || StringUtils.isBlank(codeType)) {
            return ResultVo.failure("入参不可为空");
        }
        // bug15213 交付系统，新imp从集团采购导入时，适配器issuer_code字段转换调整
        AdapterOpsTranslationConfigExample example = new AdapterOpsTranslationConfigExample();
        example.createCriteria().andOpsCodeEqualTo(codeFromOps).andCodeTypeEqualTo(codeType);
        List<AdapterOpsTranslationConfig> opsImpinvoiceFailLogDOList = adapterOpsTranslationConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(opsImpinvoiceFailLogDOList)) {
            AdapterOpsTranslationConfig opsImpinvoiceFailLogDO = opsImpinvoiceFailLogDOList.get(0);
            return ResultVo.success(opsImpinvoiceFailLogDO.getAdapterCode());
        }
        return ResultVo.failure("未获取到"+codeFromOps+"对应的PSI转换关系"+codeType);
    }

}

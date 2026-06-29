package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.db.entity.OpsPoDestinationConfig;
import com.sales.ops.db.extdao.SplitSmcCodeDao;
import com.sales.ops.service.purchase.SplitSmcCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：C14717
 * @version: $ bugid 19186 合并smccode
 * @description：
 * @date ：Created in 2025/11/27 8:57
 */
@Service
public class SplitSmcCodeServiceImpl implements SplitSmcCodeService {

    @Autowired
    private SplitSmcCodeDao splitSmcCodeDao;

    /**
     *
     * @param supplierId
     * @param modelNo
     * @param warehouseCode
     * @param orderType
     * @param transType
     * @return
     */
    @Override
    public OpsPoDestinationConfig getSubCode(String supplierId, String modelNo, String warehouseCode
            , String orderType, String transType){
        if(StringUtils.isBlank(supplierId) || StringUtils.isBlank(modelNo)
                || StringUtils.isBlank(warehouseCode) || StringUtils.isBlank(orderType)
                || StringUtils.isBlank(transType) || !"JP".equals(supplierId)){
            return null;
        }
        // 判断3C/冷干机
        String productType = splitSmcCodeDao.getProductType(modelNo);
        if(StringUtils.isBlank(productType) || (!"1".equals(productType) && !"2".equals(productType))){
            return null;
        }
        // 获取smcCode subCode
        List<OpsPoDestinationConfig> smcCodeAndSubCode = splitSmcCodeDao.getSmcCodeAndSubCode(supplierId, warehouseCode
                , Integer.parseInt(productType), orderType, transType);
        if(CollectionUtils.isEmpty(smcCodeAndSubCode)){
            return null;
        }
        return smcCodeAndSubCode.get(0);
    }
}

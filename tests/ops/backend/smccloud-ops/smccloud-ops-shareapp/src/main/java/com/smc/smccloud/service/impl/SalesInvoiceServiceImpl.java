package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.salesInvoice.SalesInvoiceMidInfoMapper;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.returnorder.RcvOrderReGisterDTO;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceMidInfoDO;
import com.smc.smccloud.service.SalesInvoiceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-02-05
 */
@Service
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Resource
    private SalesInvoiceMidInfoMapper salesInvoiceMidInfoMapper;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    @DS("opsdb")
    @Override
    public ResultVo<String> insertSalesInvoiceMidInfo(ReturnOrderDO returnOrderDO, RcvOrderReGisterDTO reGisterDTO) {
        if (reGisterDTO.getGoodNumber() == null || reGisterDTO.getGoodNumber() == 0) {
            return ResultVo.failure("写入开票数量不可为空");
        }
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrderDO.getOrderNo());
        SalesInvoiceMidInfoDO item = new SalesInvoiceMidInfoDO();
        item.setOrderNo(returnOrderDO.getOrderNo());
        item.setCustomerNo(StringUtils.isBlank(returnOrderDO.getCustomerNo()) ? "" : returnOrderDO.getCustomerNo());
        item.setUserNo(StringUtils.isBlank(returnOrderDO.getUserNo()) ? "" : returnOrderDO.getUserNo());
        item.setDeptNo(StringUtils.isBlank(returnOrderDO.getDeptNo()) ? "" : returnOrderDO.getDeptNo());
        item.setModelNo(StringUtils.isBlank(returnOrderDO.getModelNo()) ? "" : returnOrderDO.getModelNo());
        item.setQuantity(reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber());
        item.setPrice(returnOrderDO.getSalesPrice() == null ? BigDecimal.ZERO : returnOrderDO.getSalesPrice());
        item.setFeeRate(returnOrderDO.getFeeRate() == null ? BigDecimal.ZERO : returnOrderDO.getFeeRate());
        item.setTradeCompanyId(StringUtils.isBlank(returnOrderDO.getTradeComanyid()) ? "" : returnOrderDO.getTradeComanyid());
        item.setType("TH");
        item.setRemark(StringUtils.isBlank(returnOrderDO.getRemark()) ? "" : returnOrderDO.getRemark());
        item.setInsertTime(new Date());
        item.setUpdateTime(new Date());
        item.setIsNew("0");
        item.setApplyNo(returnOrderDO.getApplyNo());
        item.setApplynoItem(returnOrderDO.getItemNo());
        item.setCanBackQuantity(reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber());
        item.setToCustomerStock(String.valueOf(returnOrderDO.getToUserStock()));
        item.setRorderNo(orderNoInfo.getOrderNo());
        item.setRorderItem(orderNoInfo.getItemNo());
        int insert = salesInvoiceMidInfoMapper.insert(item);
        if (insert != 0) {
            return ResultVo.success();
        }
        return ResultVo.failure("申请号: " + returnOrderDO.getApplyNo() + " 订单号: " + returnOrderDO.getOrderNo() + "写入发票中间表失败");
    }
}

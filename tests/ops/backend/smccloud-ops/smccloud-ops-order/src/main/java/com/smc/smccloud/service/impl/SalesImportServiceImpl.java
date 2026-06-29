package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.CommonMapper;
import com.smc.smccloud.mapper.ImpInvoiceErrorMapper;
import com.smc.smccloud.mapper.SalesImpMapper;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.invoice.ImpInvoiceErrorDO;
import com.smc.smccloud.model.invoice.OpsPoInvoiceDO;
import com.smc.smccloud.model.invoice.PoInvoiceDetailDO;
import com.smc.smccloud.model.invoice.SalesImpDO;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.SalesImportService;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SalesImportServiceImpl implements SalesImportService {

    @Resource
    private SalesImpMapper salesImpMapper;

    @Resource
    private ImpInvoiceErrorMapper impInvoiceErrorMapper;

    @Resource
    private StockAssemblyFeignApi stockAssemblyFeignApi;

    @Resource
    private CommonService commonService;
    @Resource
    private DictCommonService dictCommonService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS("costdb")
    public ResultVo<String> addSalesImp(List<PoInvoiceDetailDO> detailList, Date costDate, OpsPoInvoiceDO poMasterDO) {
        LambdaQueryWrapper<SalesImpDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesImpDO::getInvoiceId, detailList.get(0).getInvoiceId())
                    .ne(SalesImpDO::getOptCode,0);
        int count = salesImpMapper.selectCount(queryWrapper);
        if(count>0){
            throw new RuntimeException(detailList.get(0).getInvoiceNo()+"成本已处理，不能再成本结算");
        }

        LambdaQueryWrapper<SalesImpDO> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SalesImpDO::getInvoiceId, detailList.get(0).getInvoiceId());
        salesImpMapper.delete(deleteWrapper);

        SalesImpDO impDO;
        for (PoInvoiceDetailDO info : detailList) {
//            if(StringUtils.isBlank(info.getEcode())){
//                throw new RuntimeException("型号"+info.getModelNo()+"没有ECode,不能成本结算");
//            }
            impDO = new SalesImpDO();
//            impDO.setId(info.getId().intValue());
            impDO.setImpdate(DateUtil.getDate(costDate));
            impDO.setInvoiceNo(info.getInvoiceNo());
            impDO.setInvoiceId(info.getInvoiceId());
            impDO.setEcode(info.getEcode());
            impDO.setOrderNo(info.getOrderNo());
            impDO.setModelNo(info.getModelNo());
            impDO.setQuantity(info.getQuantity());
            impDO.setPrice(info.getPriceTotal());
            impDO.setAmount(info.getAmountTotal());
            impDO.setPriceJP(info.getPrice());
            impDO.setAmountJP(info.getAmount());
            impDO.setOptCode("0");
            impDO.setOptDate(new Date());
            impDO.setDataSource("PO");
            // bugid:17653 C14717 20250611
            impDO.setStockCode(poMasterDO.getArrivedWarehouseCode());
            impDO.setSupplierId(info.getSupplierCode());
            impDO.setTransFee(info.getTransFee());
            impDO.setOtherFee(info.getOtherFee());
            impDO.setCustomsFee(info.getCustomsFee());
            //    <!--add by WuWeiDong 20240402  bug 13644 增加消费税 -->
            impDO.setExciseTax (Optional.ofNullable(info.getExciseTax()).orElse(BigDecimal.ZERO) );

            impDO.setExchangeRate(poMasterDO.getExchangeRate());
            impDO.setOriginalInvoiceNo(info.getOverseaInvoiceNo()); //bug8747
            // bug 14230,成本Salesimp表需要新增交易成交方式字段
            impDO.setBargainType(poMasterDO.getBargainType());
            // 16383 c14717 20250106 发票入库成本修改
            //int roSignTimeGT = commonService.getOpsRoSignTimeGT(info.getInvoiceId());
            int confirmDateGT = commonService.getImpInvoiceMasterConfirmDateGT(info.getInvoiceId());
            if(confirmDateGT > 0 ){
                impDO.setOwnerCompanyId("CN0");
                if(StringUtils.isNotBlank(poMasterDO.getSupplierCode())) {
                    ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("2166", poMasterDO.getSupplierCode());
                    if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                        impDO.setOwnerCompanyId(dataTypeCodesInfo.getData().getExtNote1());
                    }
                }
            }
            int addCount = salesImpMapper.insert(impDO);
            if (addCount <= 0) {
                throw new RuntimeException("录入成本结算失败");
            }
        }
        return ResultVo.success();
    }

    /**
     * 取消 统一到createStockAssembleFromInvoiceError
     * 异步3C生成组换
     *
     * @param InvoiceId
     * @return
     */
    @Override
    @Async
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asyncToCreateStockAssembly(Long InvoiceId, String warehouseCode) {
        List<ImpInvoiceErrorDO> list = impInvoiceErrorMapper.listImpInvoiceErrorToStoc(InvoiceId);
        if (list.isEmpty()) {
            return;
        }
        StockAssemblyApplyDTO dto = new StockAssemblyApplyDTO();
        dto.setStatus("2");
        dto.setAssembleType("1");
        dto.setApplyType("1");
        dto.setApplyDate(DateUtil.getNow());
        dto.setApprovePsn("rcv");
        dto.setBillNo(list.get(0).getInvoiceNo());
        dto.setRemark("发票入库-3C型号加组换");

        List<StockAssemblyItemDTO> outItems = new ArrayList<>(list.size());
        List<StockAssemblyItemDTO> inItems = new ArrayList<>(list.size());
        StockAssemblyItemDTO itemDTO;
        double quantity;
        for (ImpInvoiceErrorDO info : list) {
            // 调出
            quantity = Double.valueOf(String.valueOf(info.getQty()));
            itemDTO = new StockAssemblyItemDTO();
            itemDTO.setModelNo(info.getModelNo());
            itemDTO.setIsTransOut(Boolean.TRUE);
            itemDTO.setQuantity(-quantity);
            itemDTO.setWarehouseCode(warehouseCode);
            itemDTO.setInventoryType(InventoryTypeEnum.TY.getCode());
            itemDTO.setOptCode(2);
            outItems.add(itemDTO);

            // 调入
            itemDTO = new StockAssemblyItemDTO();
            itemDTO.setModelNo(info.getPoModelNo());
            itemDTO.setIsTransOut(Boolean.FALSE);
            itemDTO.setQuantity(quantity);
            itemDTO.setWarehouseCode(warehouseCode);
            itemDTO.setInventoryType(InventoryTypeEnum.TY.getCode()); //TY 通用在库
            itemDTO.setOptCode(2);
            inItems.add(itemDTO);
        }
        dto.setOutItems(outItems);
        dto.setInItems(inItems);
        stockAssemblyFeignApi.createStockAssemblyApply(dto);
    }
}

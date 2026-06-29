package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.ImpInvoiceMasterDO;
import com.smc.smccloud.model.invoice.InvoiceNoAndShipDateVO;
import com.smc.smccloud.model.invoice.OPSTExportRequestToSalesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author edp02 @Date 2022/4/7 13:40
 */
@Mapper
public interface OPSTExportRequestToSalesMapper extends BaseMapper<OPSTExportRequestToSalesDO> {
    @Select("select distinct InvoiceNo as invoiceNo,convert(varchar(10),[ExecuteTime],121) as shipdate" +
            " FROM [OPS_T_ExportRequestToSales]" +
            " where [出库日期]>=#{optdate}")
    List<InvoiceNoAndShipDateVO> listOPSTExportIvoiceNo(@Param("optdate") String optdate);

    @Select("select * FROM [OPS_T_ExportRequestToSales] with(nolock)" +
            " where convert(varchar(10),ExecuteTime,121)=#{vo.shipdate} and [InvoiceNo]=#{vo.invoiceNo}")
    List<OPSTExportRequestToSalesDO> listOPSTExportRequestToSales(@Param("vo") InvoiceNoAndShipDateVO vo);

}

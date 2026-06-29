package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.ImpInvoiceStatusFrmCMSDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/5/27 13:08
 * @Descripton TODO
 */
@Mapper
@DS("cmdb")
public interface OPSVImpInvoiceStatusFrmCMSMapper extends BaseMapper<ImpInvoiceStatusFrmCMSDO> {


    @Select("select distinct 发票号 as InvoiceNo , 原始发票号 as oldInvoiceNo,\n" +
            "  出港日 as preArricalDate,  预到货日期 as esArriveDate , 发货日期 as sendOrderDate ,\n" +
            "  实际到港日期 as realyArricalDate,到厂日期 as arriveFactoryDate,申报日期 as customsDate, 更新时间 as updateTime \n" +
            "  from Manufacture.dbo.OPS_V_ImpInvoiceStatusFrmCMS " +
            "where 更新时间 < #{currentDate} and 更新时间 > #{lastDate} order by 更新时间 DESC")
    List<ImpInvoiceStatusFrmCMSDO> findInvoiceStatusFromCMS(@Param("lastDate") Date lastDate, @Param("currentDate") Date currentDate);
}

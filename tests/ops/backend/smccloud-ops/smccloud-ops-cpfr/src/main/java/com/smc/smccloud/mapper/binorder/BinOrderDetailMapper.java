package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.binorder.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-05
 */
@Mapper
public interface BinOrderDetailMapper extends BaseMapper<BinOrderDetailDO> {

    @Select("select Max(calc_id) as calcId from bin_order_detail ")
    Long getMaxCalcId();

    @Select("select top 1 hopeExportDate  FROM ops_requestPurchase with(nolock) " +
            " where purchaseType in ('K') and stateCode<>'6' and stateCode<>'9' and purchaseWarehouseId=#{warehouseCode} and modelno=#{modelNo} " +
            " order by hopeExportDate desc")
    Date getLastPurchaseDlvDate(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode);

    @Select("select code,code_name as codeName from ops_ui.dbo.tbl_datatype where class_code=#{classcode} ")
    List<DataCodeVO> getDatatype(String classcode);


    @Select("<script> "+
            "select requestWarehouseId,orderNo as rorderNo,itemNo,modelNo,quantity-qtyImport as quantity,requestTime,hopeExportDate,transType,qtyImport,stateCode,supplierId " +
            " from ording_po_view " +
            " where "+
            "<if test = 'warehouseCodes != null and  warehouseCodes.size() &gt; 0' >" +
            " requestWarehouseId in "+
            "  <foreach collection='warehouseCodes' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item}  " +
            "  </foreach>" +
            " and  </if>"  +
            " modelNo=#{modelNo}    and inventoryPropertyId=1 "+
            " union all "+
            " select   to_warehouse_code,trans_no,cast(item_no AS varchar(5)),model_no,quantity,create_time,wms_dlv_date,trans_type,in_quantity,status,to_warehouse_code "+
            " from  trans_order where "+
             "<if test = 'warehouseCodes != null and  warehouseCodes.size() &gt; 0' >" +
            " to_warehouse_code in " +
            "  <foreach collection='warehouseCodes' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item}   " +
            "  </foreach>" +
            " and  </if>"  +
            " model_no=#{modelNo}   and  to_inventory_property_id=1 and status not in ('9','6','-1')"+
            "  union all "+
            " select  warehouse_code, associate_no as rorderNo,associate_no_item as itemno,modelno, quantity,cre_time,cre_time,inventory_status as transtype, " +
            " 0 as qtyimport,null as statecode,null as supplierId" +
            " from ops_inventory_move where "+
            " warehouse_code in " +
            "<if test = 'warehouseCodes != null and  warehouseCodes.size() &gt; 0' >" +
            "  <foreach collection='warehouseCodes' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item}    " +
            "  </foreach>" +
            " and  </if>"  +
            " modelno=#{modelNo}   " +
            " and ((source_type='1' and orderno like '99%') or source_type='2')   and  inventory_property_id=1 and opt_status=1 and delflag=0 " +
            "</script>")
    List<OrdingOrderVO> listOrdingOrder(@Param("modelNo") String modelNo,@Param("warehouseCodes") List<String> warehouseCodes);

    @DS("opsreport")
    @Select("select top 1 id,DateFromDate,DateToDate,CalcTime,FinishTime from model_exp_freq_main order by CalcTime desc ")
    ModelExpFreqMainVO getModelExpFreqMain();


}

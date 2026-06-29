package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinOrderCalcDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-10-19
 */
@Mapper
public interface BinOrderCalcMapper extends BaseMapper<BinOrderCalcDO> {

    @Select("{#{Result,mode=OUT,jdbcType=INTEGER}=call dbo.BinOrder_CalcOrder(#{calcId,mode=IN,jdbcType=INTEGER},"
            + "#{calcType,mode=IN,jdbcType=INTEGER},#{warehouseCode,mode=IN,jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void calcOrder(Map<String, Object> map);

    @Select("select PickCode from MaxCode where OptType=#{optType}")
    String getMaxCode(String optType);

    @Update("update MaxCode set PickCode=#{pickCode} where OptType=#{optType}")
    Integer updMaxCode(@Param("pickCode") String pickCode, @Param("optType") String optType);



    @Update("update bin_order_detail set bjexcessQt=quantity-prepare_quantity-b.QtyBin*b.bincell" +
            " from bin_order_detail d " +
            " inner join ops_inventory i on d.model_no=i.modelno" +
            " inner join Bindata b on d.model_no=b.ModelNo " +
            " where b.warehouse_code='KBJ' and i.warehouse_code='KBJ' and calc_id=#{calcId}")
    Integer updBinOrderGZexcessQt(Long calcId);

    @Select("select quantity-prepare_quantity-b.QtyBin*b.bincell" +
            " from ops_inventory i" +
            " inner join Bindata b on d.model_no=b.ModelNo and i.warehouse_code=b.warehouse_code " +
            " where d.model_no=#{modelNo} and i.warehouse_code=#{warehouseCode}")
    Integer getExcessQty(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode);

    @Select("select parent_code from ops_warehouse where warehouse_code=#{warehouseCode}")
    String getParentCode(String warehouseCode);

    @Select("select top 50 * from bin_order_calc order by status,id Desc")
    List<BinOrderCalcDO> listBinOrderCalc();

    @Select("select  * from bin_order_calc where warehouse_code =#{warehouseCode} and status=1")
    List<BinOrderCalcDO> listBinOrderCalcByWarehouseCode(String warehouseCode);


    @Select(" select OptFlag from Tbl_WorkDay_Year where workday_date=#{workDate}")
    Integer GetWorkDateType(@Param("workDate") String workDate);


    @Update("update bin_order_detail_split set del_flag=1 where calc_id=#{calcId} and quantity>0 and order_type='1' and status in (0,1);" +
            "update bin_order_detail set trans_qty=0,confirm_qty=po_qty where calc_id=#{calcId} and trans_qty>0 and status=1")
    void cancelTransQty(@Param("calcId") Long calcId);

    @Update("update bin_order_detail set order_type='K' where calc_id=#{calcId} and order_type='B';\n" +
            "update bin_order_detail_split set order_type='K' where calc_id=#{calcId} and order_type='B'\n")
    void updateOrderTypeB2K(@Param("calcId") long calcId);
}

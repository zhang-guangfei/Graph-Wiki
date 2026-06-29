package com.smc.smccloud.mapper.csstock;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsStockSettingDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
public interface CsStockSettingMapper extends BaseMapper<CsStockSettingDO> {

    @Update("update ops_sharedb.dbo.cs_stock_setting set init_stock_qty = 0,init_unit_qty = 0 where sponsor = #{sponsor} ")
    int updateQty(@Param("sponsor") String sponsor);


    @Select("{call ops_sharedb.dbo.csRepl_calcQty(#{warehouseCode,jdbcType=VARCHAR,mode=IN})}")
    @Options(statementType = StatementType.CALLABLE)
    void csRelCalcQty(@Param("warehouseCode") String warehouseCode);

    @Select("{call ops_sharedb.dbo.csRepl_sumCanUseQty(#{warehouseCode,jdbcType=VARCHAR,mode=IN})}")
    @Options(statementType = StatementType.CALLABLE)
    void csReplSumCanUseQty(@Param("warehouseCode") String warehouseCode);

    @Select("SELECT eprice,ord_qty,trans_qty,qty_onhand FROM tmp_cs_stock WHERE stock_code = #{warhouseCode}")
    List<CsStockSettingDO> selectCsModelByNo(@Param("warhouseCode") String warhouseCode);
}

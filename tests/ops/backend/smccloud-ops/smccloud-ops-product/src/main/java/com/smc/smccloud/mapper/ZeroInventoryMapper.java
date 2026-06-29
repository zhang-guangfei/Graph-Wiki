package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.ZeroInventoryDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;
import java.util.List;

/**
 * @author edp04
 * @title: ZeroInventoryMapper
 * @date 2022/05/07 16:10
 */
@Mapper
@DS("reportdb")
public interface ZeroInventoryMapper extends BaseMapper<ZeroInventoryDO> {

    @Select("{ call dbo.calc_zeroInventory }")
    @Options(statementType = StatementType.CALLABLE)
    void updateZeroInventory();

    @Select("SELECT DISTINCT ModelNo FROM ZeroInventory where CalcDate >=#{calcDate}")
    List<String> listZeroInventoryModel(@Param("calcDate") Date calcDate);

    @Update("UPDATE ZeroInventory SET Days = (DATEDIFF(day, FromDate, CalcDate)+1) WHERE CalcDate >=#{calcDate}")
    void updateDays(@Param("calcDate") Date calcDate);


    @Update("UPDATE ZeroInventory SET Days = (b.Days + 1),FromDate = b.FromDate FROM ZeroInventory a INNER JOIN " +
            "(SELECT * From ZeroInventory WHERE CalcDate = #{lastDate} AND WarehouseCode = #{warehouseCode}) b " +
            "ON a.ModelNo = b.modelNo WHERE a.CalcDate = #{upDate} AND a.WarehouseCode = #{warehouseCode}")
    int setDays(@Param("lastDate") String lastDate,@Param("upDate") String upDate, @Param("warehouseCode") String warehouseCode);


//    @Update("UPDATE b SET b.Mean = a.qty From (SELECT sum(Mean) qty,ModelNo FROM ZeroInventory WHERE WarehouseCode != 'ALL' AND CalcDate = #{upDate} GROUP BY modelno) a \n" +
//            "INNER JOIN (SELECT * FROM ZeroInventory WHERE CalcDate = #{upDate} AND WarehouseCode = 'ALL' ) b " +
//            "on a.modelno = b.modelno")
//    int bbb(@Param("upDate") String upDate);
}

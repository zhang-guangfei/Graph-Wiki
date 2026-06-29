package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opsdb")
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehouseDO> {

    @Select("select warehouse_code from ops_warehouse where warehouse_type= #{warehouseType} and delflag=0 ")
    List<String> getWarehouseCodesByWarehouseType(@Param("warehouseType") String warehouseType);


    @Select("<script>" +
            "select top(20) warehouse_code, warehouse_type, warehouse_name from ops_warehouse" +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test='code != null and code != \"\" '> " +
            " ( warehouse_code like #{code} or warehouse_name like #{code} ) and " +
            "</if> " +
            "<if test='type != null and type != \"\" '> " +
            " warehouse_type = #{type} " +
            "</if> " +
            "</trim>"+
            "</script>")
    List<WarehouseDO> findWareHouseInfoWithLike(@Param("code") String code,@Param("type") String type);
}

package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.model.WarehouseSalesBranchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opsdb")
@Mapper
public interface OpsWarehouseMapper extends BaseMapper<WarehouseDO> {

    @Select("select warehouse_code from ops_warehouse where warehouse_type= #{warehouseType} and delflag=0 ")
    List<String> getWarehouseCodesByWarehouseType(@Param("warehouseType") String warehouseType);


    @Select("select warehouse_code from ops_warehouse where (warehouse_code= #{warehouseCode} or parent_code= #{warehouseCode})" +
            "and delflag=0 and warehouse_type in ('SUB','MASTER')")
    List<String> getWarehouseCodesByWarehouseCodeForMasterAndSub(@Param("warehouseCode") String warehouseCode);

    @Select("<script> " +
            " select a.sales_branch_id,a.warehouse_code From ( "
            +" select sales_branch_id,warehouse_code,priority,ROW_NUMBER() over(partition by sales_branch_id  order by priority) as idx "
            +" From  ops_warehouse_salesbranch_config) A join   hr_organization b on a.sales_branch_id=b.Id "
            +" where a.idx=1 and a.warehouse_code in " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            " </script>")
    List<WarehouseSalesBranchDTO> getWarehouseSalesBranchList(@Param("list") List<String> warehouseCodes );


}

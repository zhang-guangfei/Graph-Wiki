package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.bindata.BindataClientWarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BindataClientWarehouseMapper extends BaseMapper<BindataClientWarehouseDO> {

    // 只返回计算持久化 client 所需字段，减少结果对象填充成本。
    @Select("<script>" +
            "select bindata_id as bindataId, warehouse_code as warehouseCode " +
            "from bindata_client_warehouse " +
            "where del_flag=0 and bindata_id in " +
            "<foreach item='bindataId' collection='bindataIds' open='(' separator=',' close=')'>#{bindataId}</foreach>" +
            "</script>")
    List<BindataClientWarehouseDO> selectClientsByBindataIds(@Param("bindataIds") List<Long> bindataIds);
}

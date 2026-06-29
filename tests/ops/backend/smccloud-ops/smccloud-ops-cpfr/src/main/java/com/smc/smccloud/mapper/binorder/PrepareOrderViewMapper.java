package com.smc.smccloud.mapper.binorder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.PrepareOrderViewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author edp02 @Date 2022/7/16 9:24
 */
@Mapper
public interface PrepareOrderViewMapper extends BaseMapper<PrepareOrderViewDO> {

    @Select("select order_id,order_item,modelNo,qty,remark from prepare_order_view" +
            " where qty<=(quantity-qtyImport) and  modelNo=#{modelNo} and requestWarehouseId=#{warehouseCode} ")
    List<PrepareOrderViewDO> listprepareOrderView(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode);

}

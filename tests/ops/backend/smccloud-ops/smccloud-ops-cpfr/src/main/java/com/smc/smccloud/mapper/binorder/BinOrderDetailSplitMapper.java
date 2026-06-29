package com.smc.smccloud.mapper.binorder;

import com.smc.smccloud.model.binorder.BinOrderBatchUpdateDTO;
import com.smc.smccloud.model.binorder.BinOrderDetailSplitDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface BinOrderDetailSplitMapper extends BaseMapper<BinOrderDetailSplitDO> {

    @Select("<script> " +
            " insert into bin_order_detail_split( from_id,calc_id,status,model_no,quantity,order_type,supplier_code," +
            "del_flag,create_time,update_time ,update_user,create_user,warehouse_code) VALUES" +
            "<if test = 'data != null and  data.size() &gt; 0' >" +
            "  <foreach collection='data' item='item' index='index'  separator=',' > " +
            " (#{item.fromId}, #{item.calcId}, #{item.status},#{item.modelNo},#{item.confirmQty},#{item.orderType},#{item.supplierCode}, " +
            " #{item.delFlag},#{item.createTime},#{item.updateTime},#{item.updateUser},#{item.createUser},#{item.warehouseCode})" +
            "  </foreach>" +
            "</if>" +
            "</script>")
    void  InsertByBatch(@Param("data") List<BinOrderDetailSplitDO> list);


}

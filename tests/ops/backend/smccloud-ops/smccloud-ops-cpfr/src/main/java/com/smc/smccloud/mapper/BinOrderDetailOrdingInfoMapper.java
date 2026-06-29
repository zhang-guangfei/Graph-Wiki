package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinOrderDetailOrdingInfoDO;
import com.smc.smccloud.model.binorder.TkckOrderFnoKey;
import com.smc.smccloud.model.binorder.TkckNotOutQtyLite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2025-12-17
 */
@Mapper
public interface BinOrderDetailOrdingInfoMapper extends BaseMapper<BinOrderDetailOrdingInfoDO> {

    @Select("<script>" +
            "select distinct m.inventory_id from ops_inventory_move m\n" +
            "inner join ops_do_item_inventory ii on ii.inventory_id=m.inventory_id and ii.delflag=0 and ii.inventory_table_type='T'\n" +
            "inner join ops_do d on ii.do_id=d.do_id and d.delflag=0 and d.do_type='JYCK'\n" +
            "where m.inventory_id in " +
            "<foreach item='id' collection='inventoryIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    public List<Long> selectDBCKMoveNotPrepareByJYCK(@Param("inventoryIds") List<Long> inventoryIds);

    @Select("<script>" +
            "select distinct m.inventory_id from ops_inventory_move m\n" +
            "inner join ops_pco_item_inventory pii on m.inventory_id=pii.inventory_id and pii.delflag=0 and pii.inventory_table_type='T'\n" +
            "where m.inventory_id in " +
            "<foreach item='id' collection='inventoryIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    public List<Long> selectDBCKMoveNotPrepareByPCO(@Param("inventoryIds") List<Long> inventoryIds);

    @Select("<script>" +
            "select  isnull(sum(isnull(ii.use_qty,0)- isnull (ii.out_qty,0)),0) as quantity \n" +
            "from ops_do d \n" +
            "inner join ops_do_item_inventory ii on ii.do_id=d.do_id \n" +
            "where  d.delflag=0 and ii.delflag=0 and d.do_type='TKCK' and ii.inventory_table_type='T' \n" +
            "and d.order_id+ '-'+d.order_item=#{orderFno} \n" +
            "</script>")
    public Integer selectTKCKNotOutQty(String orderFno);

    @Select("<script>" +
            "select  isnull(sum(isnull(ii.use_qty,0)- isnull (ii.out_qty,0)),0) as quantity \n" +
            "from ops_do d \n" +
            "inner join ops_do_item_inventory ii on ii.do_id=d.do_id \n" +
            "where  d.delflag=0 and ii.delflag=0 and d.do_type='TKCK' and ii.inventory_table_type='T' \n" +
            "and d.order_id=#{orderId,jdbcType=VARCHAR} and d.order_item=#{orderItem,jdbcType=VARCHAR} \n" +
            "</script>")
    public Integer selectTKCKNotOutQtyByOrderKey(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Select("<script>" +
            "select c.order_id + '-' + c.order_item as orderFno, " +
            "isnull(sum(isnull(ii.use_qty,0)- isnull (ii.out_qty,0)),0) as quantity \n" +
            "from (values " +
            "<foreach item='orderKey' collection='orderKeys' separator=','>" +
            "(#{orderKey.orderId,jdbcType=VARCHAR}, #{orderKey.orderItem,jdbcType=VARCHAR})" +
            "</foreach>" +
            ") c(order_id, order_item) \n" +
            "inner join ops_do d on d.order_id=c.order_id and d.order_item=c.order_item \n" +
            "inner join ops_do_item_inventory ii on ii.do_id=d.do_id \n" +
            "where  d.delflag=0 and ii.delflag=0 and d.do_type='TKCK' and ii.inventory_table_type='T' \n" +
            "group by c.order_id, c.order_item \n" +
            "</script>")
    public List<TkckNotOutQtyLite> selectTKCKNotOutQtyBatch(@Param("orderKeys") List<TkckOrderFnoKey> orderKeys);
}

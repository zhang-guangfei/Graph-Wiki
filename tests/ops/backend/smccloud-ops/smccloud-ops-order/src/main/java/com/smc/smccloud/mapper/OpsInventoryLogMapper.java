package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.InventoryLogDTO;
import com.smc.smccloud.model.inventory.OpsInventoryLogVO;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author C18117
 * @title: OpsInventoryLogMapper
 * @date 2022/11/18 11:23
 */
@Mapper
@DS("opsdb")
public interface OpsInventoryLogMapper {

    @Select("select voucher_type, order_no,item_no,a.modelno,MIN(cre_time) as creTime,property_id,inventory_id " +
            "from ops_inventory_log a \n" +
            "inner join product_price b on b.modelNo=a.modelno and b.is_deleted=0 and b.minQuantity>1 \n" +
            "where a.cre_time>#{fromDate} and type=1 and voucher_type like 'CG%' \n" +
            "group by order_no,item_no,voucher_type,property_id,inventory_id,a.modelno order by MIN(cre_time)")
    List<OpsInventoryLogVO> listInventoryLogCGOrerNo(@Param("fromDate") Date fromDate);

    /**
     * --非采购入库入库记录
     * @return
     */
    @Select("select voucher_type, order_no,item_no,a.modelno,MIN(cre_time) as cre_time,property_id,inventory_id " +
            "from ops_inventory_log a \n" +
            "inner join product_price b on b.modelNo=a.modelno and b.is_deleted=0 and b.minQuantity>1 " +
            "where a.cre_time>#{fromDate} and type=1 and voucher_type not like 'CG%' " +
            "group by order_no,item_no,voucher_type,property_id,inventory_id,a.modelno order by MIN(cre_time)")
    List<OpsInventoryLogVO> listInventoryLogDBOrerNo(@Param("fromDate") Date fromDate);

    @Select("select * from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and type=0 ORDER BY cre_time ASC")
    List<OpsInventoryLogVO> getFromInventoryLog(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo);

    //查询入库的总数量
    @Select("select sum(quantity) from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and modelNo=#{modelNo} and type=1")
    Integer getInventoryLogImpSumQty(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo,@Param("modelNo") String modelNo);


    @Select("select top 1 inventory_id from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and modelno=#{modelNo} and type=0 ")
    Long getOutInventoryIdByOrder(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo,@Param("modelNo") String modelNo);


    /**
     * 查询入库的InventoryId
     * @param orderNo
     * @param itemNo
     * @param modelNo
     * @return
     */
    @Select("select top 1 inventory_id from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and modelno=#{modelNo} and type=1 ")
    Long getImpInventoryIdByOrder(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo,@Param("modelNo") String modelNo);





    @Select("select top 100 a.voucher_type,a.modelno,a.quantity, a.order_no,a.item_no ,b.lotPrice,b.lotQty\n" +
            "from ops_inventory_log a left join ops_report.dbo.stocklog b on a.order_no=b.orderNo and a.item_no=b.itemNo\n" +
            "where a.modelno=#{modelNo} \n" +
            " and a.inventory_id=#{inventoryId} and cre_time<#{creTime} and type=1\n" +
            "order by cre_time desc")
    List<InventoryLogDTO> listInventoryAndStockLog(@Param("modelNo") String modelNo,@Param("inventoryId") Long inventoryId,@Param("creTime") Date creTime);

//    /**
//     * 有批量价格无E价的出库记录
//     * @return
//     */
//    @Select("select voucher_type, order_no,item_no,SUM(a.quantity) as qty,MIN(cre_time) as minTime,property_id,inventory_id\n" +
//            "from ops_inventory_log a \n" +
//            "inner join [10.116.32.17].smcdb30.dbo.pricemast_mult b on b.modelNo=a.modelno \n" +
//            "where a.cre_time>'2022-08-01' and type=0 \n" +
//            "group by order_no,item_no,voucher_type,property_id,inventory_id")
//    List<OpsInventoryLogVO> listInventoryLogCKOrder();
}

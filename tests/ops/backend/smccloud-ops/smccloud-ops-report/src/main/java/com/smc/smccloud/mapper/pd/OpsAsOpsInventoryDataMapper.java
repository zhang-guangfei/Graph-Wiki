package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsOpsInventoryDataDO;
import com.smc.smccloud.model.pd.OpsInventoryOpeningDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Mapper
@DS("reportdb")
public interface OpsAsOpsInventoryDataMapper extends BaseMapper<OpsAsOpsInventoryDataDO> {

    @Delete("delete from ops_as_ops_inventory_data where data_type = #{dataType} and pd_batch_no = #{batchNo} ")
    void delOpsAsOpsInventoryData(@Param("dataType") String dataType,@Param("batchNo") String batchNo);

    // ops库存数据
//    @Select("select modelno as modelNo,warehouse_code=sign_warehouse_code,qty=sum(quantity) from ${dataSource}.ops_inventory_move \n" +
//            "where delflag=0 and source_type=1 and quantity<>0 \n" +
//            "group by modelno,sign_warehouse_code")
    @Select("select * from(\n" +
            "SELECT modelno as modelNo ,a.warehouse_code,qty=sum(quantity) FROM ${dataSource}.[ops_inventory] a \n" +
            "inner join ${dataSource}.ops_warehouse b on a.warehouse_code=b.warehouse_code and b.delflag=0\n" +
            "where a.delflag=0\n" +
            "group by modelno,a.warehouse_code) P \n" +
            "where P.qty<>0")
    List<OpsAsOpsInventoryDataDO> opsInventory(@Param("dataSource") String dataSource);

    @Select("select * from ops_core.dbo.ops_inventory where delflag = 0 and (quantity - prepare_quantity <> 0  or  quantity <> 0) ")
    List<OpsInventoryOpeningDO> getOpsInventory();

    @Update(" update ops_core.dbo.ops_inventory set initial_quantity = quantity,initial_time = getDate() where id = #{id} ")
    void updateOpsInventoryTnitialQuantity(@Param("id") Long id);

    @Select("select model_no=modelno,warehouse_code=sign_warehouse_code,qty=sum(quantity),data_type='2' from ${dataSource}.ops_inventory_move \n" +
            "where delflag=0 and inventory_status='W' and source_type=0 group by modelno,sign_warehouse_code")
    List<OpsAsOpsInventoryDataDO> opsArriveNotInWithCG(@Param("dataSource") String dataSource);

//    @Select("SELECT doi.modelno,warehouse_code=sign_warehouse_code,qty=sum(doi.qty),data_type='5' \n" +
//            " FROM ${dataSource}.[ops_inventory_move] ops_inventory_move\n" +
//            " left join ${dataSource}.ops_do do on ops_inventory_move.orderNo = do.order_id\n" +
//            " left join ${dataSource}.ops_do_item doi on do.do_id = doi.do_id\n" +
//            " WHERE inventory_status = 'W' AND [source_type] = '3' AND [opt_status] in ('4','5') and ops_inventory_move.delflag=0 \n" +
//            " group by doi.modelno,sign_warehouse_code")
//    @Select("WITH inv AS (\n" +
//            "SELECT DISTINCT\n" +
//            "orderNo,\n" +
//            "sign_warehouse_code \n" +
//            "FROM\n" +
//            "ops_core.dbo.ops_inventory_move \n" +
//            "WHERE\n" +
//            "inventory_status = 'W' \n" +
//            "AND source_type = '3' \n" +
//            "AND opt_status IN ( '4', '5' ) \n" +
//            "AND delflag = 0 \n" +
//            "),\n" +
//            "do_head AS ( \n" +
//            "SELECT DISTINCT do_id, order_id \n" +
//            "FROM ops_core.dbo.ops_do ),\n" +
//            "item AS (\n" +
//            "SELECT do_id, modelno, SUM ( qty ) AS qty FROM ops_core.dbo.ops_do_item GROUP BY do_id, modelno ) SELECT\n" +
//            "item.modelno,\n" +
//            "SUM ( item.qty ) AS qty \n" +
//            "FROM\n" +
//            "inv\n" +
//            "JOIN do_head d ON inv.orderNo = d.order_id\n" +
//            "JOIN item ON d.do_id = item.do_id \n" +
//            "GROUP BY\n" +
//            "item.modelno,\n" +
//            "inv.sign_warehouse_code;")
    @Select("WITH inv AS (\n" +
            "SELECT DISTINCT\n" +
            "orderNo,                \n" +
            "sign_warehouse_code,\n" +
            " '5' AS data_type,\n" +
            "sign_warehouse_code AS warehouse_code  \n" +
            "FROM\n" +
            "ops_core.dbo.ops_inventory_move \n" +
            "WHERE\n" +
            "inventory_status = 'W' \n" +
            "AND source_type = '3' \n" +
            "AND opt_status IN ( '4', '5' ) \n" +
            "AND delflag = 0 \n" +
            "),\n" +
            "do_head AS (\n" +
            "SELECT DISTINCT\n" +
            "do_id,\n" +
            "order_id\n" +
            "FROM ops_core.dbo.ops_do\n" +
            "),\n" +
            "item AS (\n" +
            "SELECT\n" +
            "do_id,\n" +
            "modelno,\n" +
            "SUM(qty) AS qty\n" +
            "FROM ops_core.dbo.ops_do_item\n" +
            "GROUP BY do_id, modelno\n" +
            ")\n" +
            "SELECT\n" +
            "item.modelno,\n" +
            "inv.warehouse_code, \n" +
            "inv.data_type,\n" +
            "SUM(item.qty) AS qty\n" +
            "FROM\n" +
            "inv\n" +
            "JOIN do_head d ON inv.orderNo = d.order_id\n" +
            "JOIN item ON d.do_id = item.do_id\n" +
            "GROUP BY\n" +
            "item.modelno,\n" +
            "inv.warehouse_code,  \n" +
            "inv.data_type;")
    List<OpsAsOpsInventoryDataDO> opszhdhwr(@Param("dataSource") String dataSource);

    @Select("select model_no=modelno,warehouse_code=sign_warehouse_code,qty=sum(quantity),data_type='4' from ${dataSource}.ops_inventory_move \n" +
            "where delflag=0 and inventory_status='W' and source_type=2 group by modelno,sign_warehouse_code")
    List<OpsAsOpsInventoryDataDO> opsArriveNotInWithTH(@Param("dataSource") String dataSource);

    @Select("select model_no=modelno,warehouse_code=sign_warehouse_code,qty=sum(quantity),data_type='3' from ${dataSource}.ops_inventory_move \n" +
            "where delflag=0 and inventory_status='W' and source_type=1 group by modelno,sign_warehouse_code")
    List<OpsAsOpsInventoryDataDO> opsArriveNotInWithDB(@Param("dataSource") String dataSource);
}

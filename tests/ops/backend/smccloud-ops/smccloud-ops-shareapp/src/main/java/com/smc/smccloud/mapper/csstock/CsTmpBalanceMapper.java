package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsModelBalanceDO;
import com.smc.smccloud.model.csstock.CsTmpBalanceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author edp04
 * @title: CsTmpBalanceMapper
 * @date 2022/06/27 12:07
 */
@Mapper
@DS("sharedb")
public interface CsTmpBalanceMapper extends BaseMapper<CsTmpBalanceDO> {

    @Select("\tselect warehouse_code, modelno,sum(quantity) as onhandQty\n" +
            "\tfrom ops_core.dbo.ops_inventory i \n" +
            "\tinner join ops_core.dbo.ops_inventory_property p on p.inventory_property_id=i.inventory_property_id \n" +
            "\twhere i.inventory_status='N' AND quantity >0 \n" +
            "\tand i.warehouse_code like 'W%'\n" +
            "\tgroup by p.inventory_type_code,modelno,warehouse_code")
    List<CsTmpBalanceDO> listOnHandQty();

    @Select("select agent_no,warehouse_code,model_no,sum(quantity) as inQty\n" +
            "   FROM cs_impdata\n" +
            "   where status=2  and imp_type=1 and receive_time>=#{fromDate} and receive_time<=#{toDate}\n" +
            "   group by agent_no,warehouse_code,model_no" +
            "   having sum(quantity)>0")
    List<CsTmpBalanceDO> listImpQty(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

    @Select("select agent_no,warehouse_code,model_no ,SUM(exp_qty)  AS outQty \n" +
            "   from cs_expdetail \n" +
            "   where (status=2) and exp_time>=#{fromDate} and exp_time<=#{toDate}\n" +
            "   group by agent_no,warehouse_code, model_no")
    List<CsTmpBalanceDO> listExpQty(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

    @Select("select  agent_no,warehouse_code,model_no,-sum(quantity) as returnQty\n" +
            "     from cs_impdata \n" +
            "\t where  status=2  and imp_type=2 and receive_time>=#{fromDate} and receive_time<=#{toDate}\n" +
            "     group by agent_no,warehouse_code,model_no")
    List<CsTmpBalanceDO> listReturnQty(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

    @Select("select  agent_no,warehouse_code,model_no,sum(quantity) as qtyShip\n" +
            "    from cs_impdata \n" +
            "\twhere balance_costDate>=#{fromDate} and balance_costDate<=#{toDate} AND status <> 9\n" +
            "    group by agent_no,warehouse_code,model_no")
    List<CsTmpBalanceDO> listShipQty(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

    @Select("select agent_no,warehouse_code,model_no,qty_balance as openingQty,qty_balance_cost as qtyOpeningCost\n" +
            "    from cs_balance  \n" +
            "    where month_date=#{lastMothDate}")
    List<CsTmpBalanceDO> selectLastMothBalanceData(@Param("lastMothDate") Date lastMothDate);

    @Update("delete from tmp_cs_balance")
    void deleteTmp();

    @Select("select agent_no,warehouse_code,model_no,sum(qty_opening) as openingQty,\n" +
            "\tsum(qty_onhand) as onhandQty,sum(qty_in) as inQty ,sum(qty_out) as outQty,sum(qty_return) as returnQty,\n" +
            "\tsum(qty_ship) as qtyShip,sum(qty_invoice)as qtyInvoice,sum(qty_opening_cost) as qtyOpeningCost,\n" +
            "\tsum(qty_opening)+sum(qty_in)-sum(qty_out)-sum(qty_return) as balanceQty,\n" +
            "\tsum(qty_opening_cost)+sum(qty_ship)-sum(qty_invoice) as qtyBalanceCost\n" +
            "\tfrom tmp_cs_balance\n" +
            "\tgroup by agent_no,warehouse_code,model_no")
    List<CsModelBalanceDO> listCsBalanceData();
}

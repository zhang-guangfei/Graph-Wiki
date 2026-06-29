package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  入库接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
@DS("sharedb")
public interface CsImpdataMapper extends BaseMapper<CsImpdataDO> {

//    /**
//     * 委托在库入库清单表
//     */
//    @Select("insert into ops_sharedb.dbo.cs_impdata (agent_no,warehouse_code,order_no,status,ship_date,model_no,quantity,return_qty,exp_qty,barcode,case_no,delivery_no,create_time,create_user)\n" +
//            "select a.customer_no,b.stock_code,a.order_fno,1,a.ship_date,a.model_no,a.quantity,0,0,a.barcode,a.case_no,a.delivery_no,getdate() " +
//            "from ops_core.dbo.expdetail a " +
//            "inner join cs_apply b on  a.order_no=b.orderNo\n" +
//            "left join ops_sharedb.dbo.cs_impdata c on a.order_fno=c.order_no " +
//            "where a.order_type=3 and c.id is null")
//    void insertCsImpData();

    @Select("SELECT id, agent_no, warehouse_code, order_no, status, imp_date, ship_date, " +
            " model_no, quantity, return_qty, exp_qty, left_qty, barcode, case_no, delivery_no," +
            " ppl_no, project_no, invoice_no, imp_type, receive_time, receive_psn, create_time," +
            " update_time, update_user, create_user, location_No, user_no, applyType, balance_date, " +
            " balance_costDate, ro_id, sign_time, sign_psn FROM cs_impdata WHERE status='1' and isnull(ro_id,'')=''")
    List<CsImpdataVO> listImpData();
}

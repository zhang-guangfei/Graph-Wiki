package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.ExpdetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-01-20
 */

@Mapper
@DS("opsdb")
public interface ExpdetailMapper extends BaseMapper<ExpdetailDO> {



    //bugid: 16354 c14717 20250103
    @Select("SELECT sum(quantity) as over_qty " +
            "from branch_inventory_transaction where del_flag =0 and model_no = #{modelNo} group by model_no")
    Integer queryBranchSumDo(@Param("modelNo") String modelNo);

//    @Select("<script>" +
//            "SELECT id, invoice_no, delivery_no, order_no, item_no, order_fno, model_no, quantity, barcode, " +
//            " customer_no, user_no, ship_date, express_no, express_company, warehouse_code, price, " +
//            " opt_code, corder_no, cmodel_no, case_no, weight, order_type, invoice_flag, invoice_time, " +
//            " sign_time, stock_code, dlv_date, orOrderNo, sign_status, sender, dlv_site, volume, box_type, " +
//            " create_time, create_user, update_time, update_user, dept_no FROM dbo.expdetail " +
//            " where order_fno in " +
//            " <foreach collection='orderNoList' item='item' index='index' open='(' separator=',' close=')'> " +
//            "   #{item} " +
//            " </foreach> " +
//            "</script>")
//    List<ExpdetailDO> selectExpDetailList(@Param("orderNoList") List<String> orderNoList);
//
//    @Select("{call ops_sharedb.dbo.Proc_ImpdataForManu}")
//    @Options(statementType = StatementType.CALLABLE)
//    void ProcImpdataForManu();
//
//    @Select("{call ops_sharedb.dbo.exp_ExpdetailForGZ}")
//    @Options(statementType = StatementType.CALLABLE)
//    void callExpExpdetailForGZ();
}

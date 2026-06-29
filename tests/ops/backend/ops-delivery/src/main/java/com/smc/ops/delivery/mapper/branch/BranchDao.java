package com.smc.ops.delivery.mapper.branch;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.db.entity.OpsPcoItem;
import com.smc.ops.delivery.model.branch.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 10:36
 */
@DS("opsdb")
public interface BranchDao {
    @Insert("<script> insert into dbo.branch_inventory_transaction  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            " <if test='companyId != null'> company_id,</if>  <if test='modelNo != null'>  model_no,</if> " +
            " <if test='warehouseCode != null'> warehouse_code,</if>  <if test='inventoryType != null'> inventory_type, </if> " +
            " <if test='voucherCode != null'> voucher_code,</if> <if test='quantity != null'>  quantity,  </if> " +
            " <if test='fromInventoryId != null'>  from_inventory_id, </if>         <if test='orderNo != null'> order_no,  </if> " +
            " <if test='orderItem != null'>  order_item, </if> <if test='shipTime != null'>  ship_time,  </if> " +
            " </trim> " +
            " <trim prefix='values (' suffix=')' suffixOverrides=','> " +
            " <if test='companyId != null'> #{companyId,jdbcType=VARCHAR},  </if> " +
            " <if test='modelNo != null'>  #{modelNo,jdbcType=VARCHAR},  </if> " +
            " <if test='warehouseCode != null'>  #{warehouseCode,jdbcType=VARCHAR},  </if> " +
            " <if test='inventoryType != null'>  #{inventoryType,jdbcType=VARCHAR},  </if> " +
            " <if test='voucherCode != null'>  #{voucherCode,jdbcType=VARCHAR}, </if> " +
            " <if test='quantity != null'>  #{quantity,jdbcType=INTEGER},  </if> " +
            " <if test='fromInventoryId != null'>   #{fromInventoryId,jdbcType=VARCHAR},  </if> " +
            " <if test='orderNo != null'> #{orderNo,jdbcType=VARCHAR},  </if> " +
            " <if test='orderItem != null'>  #{orderItem,jdbcType=INTEGER},  </if> " +
            " <if test='shipTime != null'>  #{shipTime,jdbcType=TIMESTAMP},  </if> " +
            " </trim>  </script>")
    public Integer insertBranchInvTrans(BranchInvTransDo record);


    @Insert("<script> insert into dbo.expdetail_property  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            " <if test='expdetailId != null'> expdetail_id,</if>  <if test='modelNo != null'>  model_no,</if> " +
            " <if test='companyId != null'> company_id,</if>  <if test='quantity != null'> quantity, </if> " +
            " <if test='propertyQuantity != null'> property_quantity,</if>  " +
            " </trim> " +
            " <trim prefix='values (' suffix=')' suffixOverrides=','> " +
            " <if test='expdetailId != null'> #{expdetailId,jdbcType=BIGINT},  </if> " +
            " <if test='modelNo != null'>  #{modelNo,jdbcType=VARCHAR},  </if> " +
            " <if test='companyId != null'>  #{companyId,jdbcType=VARCHAR},  </if> " +
            " <if test='quantity != null'>  #{quantity,jdbcType=INTEGER},  </if> " +
            " <if test='propertyQuantity != null'>  #{propertyQuantity,jdbcType=INTEGER}, </if> " +
            " </trim>  </script>")
    public Integer insertExpdetailProperty(ExpdetailPropertyDo record);

    @Insert("<script> insert into dbo.ops_exception_handle  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            " <if test=\"businessType != null\">\n" +
            "   business_type,\n" +
            " </if>\n" +
            " <if test=\"errType != null\">\n" +
            "   err_type,\n" +
            " </if>\n" +
            " <if test=\"apiName != null\">\n" +
            "   api_name,\n" +
            " </if>\n" +
            " <if test=\"status != null\">\n" +
            "   status,\n" +
            " </if>\n" +
            " <if test=\"handleStatus != null\">\n" +
            "   handle_status,\n" +
            " </if>\n" +
            " <if test=\"msgId != null\">\n" +
            "   msg_id,\n" +
            " </if>\n" +
            " <if test=\"inputMsg != null\">\n" +
            "   input_msg,\n" +
            " </if>\n" +
            " <if test=\"outputMsg != null\">\n" +
            "   output_msg,\n" +
            " </if>\n" +
            " <if test=\"parameter1 != null\">\n" +
            "   parameter_1,\n" +
            " </if>\n" +
            " <if test=\"parameter2 != null\">\n" +
            "   parameter_2,\n" +
            " </if>\n" +
            " <if test=\"parameter3 != null\">\n" +
            "   parameter_3,\n" +
            " </if>\n" +
            " <if test=\"parameter4 != null\">\n" +
            "   parameter_4,\n" +
            " </if>\n" +
            " <if test=\"parameter5 != null\">\n" +
            "   parameter_5,\n" +
            " </if>\n" +
            " <if test=\"parameter6 != null\">\n" +
            "   parameter_6,\n" +
            " </if>\n" +
            " <if test=\"parameter7 != null\">\n" +
            "   parameter_7,\n" +
            " </if>\n" +
            " <if test=\"parameter8 != null\">\n" +
            "   parameter_8,\n" +
            " </if>\n" +
            " <if test=\"parameter9 != null\">\n" +
            "   parameter_9,\n" +
            " </if>\n" +
            " <if test=\"remark != null\">\n" +
            "   remark,\n" +
            " </if>\n" +
            " <if test=\"createUser != null\">\n" +
            "   create_user,\n" +
            " </if>\n" +
            " <if test=\"createTime != null\">\n" +
            "   create_time,\n" +
            " </if>\n" +
            " <if test=\"updateUser != null\">\n" +
            "   update_user,\n" +
            " </if>\n" +
            " <if test=\"updateTime != null\">\n" +
            "   update_time,\n" +
            " </if>\n" +
            " <if test=\"readQty != null\">\n" +
            "   read_qty,\n" +
            " </if> " +
            " </trim> " +
            " <trim prefix='values (' suffix=')' suffixOverrides=','> " +
            " <if test=\"businessType != null\">\n" +
            "        #{businessType,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"errType != null\">\n" +
            "   #{errType,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"apiName != null\">\n" +
            "   #{apiName,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"status != null\">\n" +
            "   #{status,jdbcType=INTEGER},\n" +
            " </if>\n" +
            " <if test=\"handleStatus != null\">\n" +
            "   #{handleStatus,jdbcType=INTEGER},\n" +
            " </if>\n" +
            " <if test=\"msgId != null\">\n" +
            "   #{msgId,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"inputMsg != null\">\n" +
            "   #{inputMsg,jdbcType=NVARCHAR},\n" +
            " </if>\n" +
            " <if test=\"outputMsg != null\">\n" +
            "   #{outputMsg,jdbcType=NVARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter1 != null\">\n" +
            "   #{parameter1,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter2 != null\">\n" +
            "   #{parameter2,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter3 != null\">\n" +
            "   #{parameter3,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter4 != null\">\n" +
            "   #{parameter4,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter5 != null\">\n" +
            "   #{parameter5,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter6 != null\">\n" +
            "   #{parameter6,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter7 != null\">\n" +
            "   #{parameter7,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter8 != null\">\n" +
            "   #{parameter8,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"parameter9 != null\">\n" +
            "   #{parameter9,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"remark != null\">\n" +
            "   #{remark,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"createUser != null\">\n" +
            "   #{createUser,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"createTime != null\">\n" +
            "   #{createTime,jdbcType=TIMESTAMP},\n" +
            " </if>\n" +
            " <if test=\"updateUser != null\">\n" +
            "   #{updateUser,jdbcType=VARCHAR},\n" +
            " </if>\n" +
            " <if test=\"updateTime != null\">\n" +
            "   #{updateTime,jdbcType=TIMESTAMP},\n" +
            " </if>\n" +
            " <if test=\"readQty != null\">\n" +
            "   #{readQty,jdbcType=INTEGER},\n" +
            " </if>" +
            " </trim>  </script>")
    public Integer insertOpsExceptionHandle(OpsExceptionHandle record);



    @Select("SELECT top 10000 r.trade_companyId ,e.warehouse_code ,e.order_no ,e.item_no " +
            ",e.ship_date ,e.model_no,e.quantity,e.create_time ,e.id ,e.branch_flag ,e.delivery_no  \n" +
            "from expdetail e \n" +
            "left join rcvmaster r on e.order_no = r.rorder_no  \n" +
            "where branch_flag is NULL and e.create_time > '2025-01-01' order by create_time ")
    List<ExpdetailInfoDo> queryExpdetailInfoDo();

    @Select("SELECT sum(quantity) as over_qty " +
            "from branch_inventory_transaction where del_flag=0 and model_no = #{modelNo} group by model_no")
    Integer queryBranchSumQty(@Param("modelNo") String modelNo);
    @Select("SELECT model_no ,company_id ,sum(quantity) as over_qty " +
            "from branch_inventory_transaction where del_flag=0 and model_no = #{modelNo} group by model_no ,company_id ")
    List<BranchSumDo> queryBranchSumDo(@Param("modelNo") String modelNo);

    @Select("<script>" +
            " select sum(quantity) as quantity, sum(prepare_quantity) as prepareQuantity " +
            " from ops_inventory i with(nolock) "+
            " where " +
            " i.modelno=#{modelNo} " +
            " and i.inventory_status='N' and i.qa_status=0 and i.delflag=0  and quantity>0 and quantity > prepare_quantity" +
            "</script>")
    InventoryVO getInvByModel(@Param("modelNo") String modelNo);

    @Select("SELECT count(*) from ops_pco where delflag =0 and order_id = #{orderId} and order_item = #{orderItem} ")
    Integer countPco(@Param("orderId") String orderId, @Param("orderItem") String orderItem);


    @Select("SELECT inventory_id  from ops_do_item_inventory where delflag =0 and do_id = #{doId}")
    List<Long> queryDoItemInvIds(@Param("doId") String doId);

    @Select("SELECT num  from ops_do where delflag =0  and do_id = #{doId}")
    Integer queryDoNum(@Param("doId") String doId);

    @Select("SELECT qty  from ops_do_item where delflag =0 and do_id = #{doId}")
    Integer queryDoItemQty(@Param("doId") String doId);

    @Select("SELECT pco_id  from ops_pco where delflag =0 and order_id = #{orderId} and order_item = #{orderItem} and num = #{num}")
    String queryPcoId(@Param("orderId") String orderId, @Param("orderItem") String orderItem, @Param("num") Integer num);


    @Select("SELECT pco_id ,pco_item ,sub_modelno ,sub_qty  from ops_pco_item where delflag =0 and pco_id = #{pcoId}")
    List<OpsPcoItem> queryPcoItems(@Param("pcoId") String pcoId);


    @Select("SELECT inventory_id  from ops_pco_item_inventory where delflag =0 and pco_id = #{pcoId}  and pco_item = #{pcoItem} ")
    List<Long> queryPcoItemInvIds(@Param("pcoId") String pcoId , @Param("pcoItem") Integer pcoItem);


    @Update("UPDATE expdetail set branch_flag = #{branchFlag}  where id = #{id}  ")
    void updateExpdetail(@Param("id") Long id , @Param("branchFlag") Integer branchFlag);

    @Select("SELECT d.order_id ,d.order_item ,i.do_id from ops_do d " +
            "left join ops_do_item i on d.do_id = i.do_id  " +
            "where d.delflag =0 and i.delflag =0 and  order_id =#{orderId} and i.modelno =#{modelno} ")
    DoDoItemDo queryDoDoItemInfo(@Param("orderId") String orderId , @Param("modelno") String modelno);


    @Select("SELECT count(do_id) from ops_do_item_inventory where delflag =0 and inventory_table_type = 'T' and do_id = #{doId}")
    Integer countDoItemInvType(@Param("doId") String doId);

    @Select("SELECT count(pco_id) from ops_pco_item_inventory where delflag =0 and inventory_table_type = 'T' and pco_id = #{pcoId}")
    Integer countPcoItemInvType(@Param("pcoId") String pcoId );

}

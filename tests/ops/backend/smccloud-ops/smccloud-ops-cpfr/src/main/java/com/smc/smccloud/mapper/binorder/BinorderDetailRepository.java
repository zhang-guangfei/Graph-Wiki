package com.smc.smccloud.mapper.binorder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import com.smc.smccloud.model.binorder.BinOrderDetailOrdingInfoDO;
import com.smc.smccloud.model.binorder.BinOrderDetailPreInfoDO;
import com.smc.smccloud.model.binorder.BinOrderDetailSplitDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BinorderDetailRepository extends BaseMapper<BinOrderDetailDO> {

    @Select("select count(*) from bin_order_detail where calc_id=#{calcId} and status!=1 ")
    Integer findBinOrderDetailNotCalcuing(Long calcId);

    @Select("select * from bin_order_detail where calc_type=#{calcType} and calc_id=#{calcId} ")
    List<BinOrderDetailDO> findBinOrderDetailByCalcType(Long calcId, Integer calcType);

    @Select("select * from bin_order_detail where calc_id=#{calcId} order by id ")
    List<BinOrderDetailDO> findBinOrderDetailByCalcId(Long calcId);

    // 首次生成仓库 BIN detail 时使用；只写入生成阶段已赋值字段，后续统计字段仍由批量 update 补齐。
    @Insert("<script>" +
            "INSERT INTO [dbo].[bin_order_detail]\n" +
            "([calc_id],[warehouse_code],[calc_type],[model_no],[direct_purchase],[center_flag]," +
            "[bincell],[qtybin],[po_type],[property_id],[inventory_type_code],[customerNo]," +
            "[group_customer_no],[ppl],[project_no],[order_type],[main_supplier_code],[set_order_type]," +
            "[supplier_code],[recomm_months],[freq],[mean],[model_class],[bin_class],[client])\n" +
            "VALUES\n" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">\n" +
            "(\n" +
            "#{item.calcId},\n" +
            "#{item.warehouseCode},\n" +
            "#{item.calcType},\n" +
            "#{item.modelNo},\n" +
            "#{item.directpurchase},\n" +
            "#{item.centerFlag},\n" +
            "#{item.bincell},\n" +
            "#{item.qtybin},\n" +
            "#{item.poType},\n" +
            "#{item.propertyId},\n" +
            "#{item.inventoryTypeCode},\n" +
            "#{item.customerNo},\n" +
            "#{item.groupCustomerNo},\n" +
            "#{item.ppl},\n" +
            "#{item.projectNo},\n" +
            "#{item.orderType},\n" +
            "#{item.mainSupplierCode},\n" +
            "#{item.setOrderType},\n" +
            "#{item.supplierCode},\n" +
            "#{item.recommMonths},\n" +
            "#{item.freq},\n" +
            "#{item.mean},\n" +
            "#{item.modelClass},\n" +
            "#{item.binClass},\n" +
            "#{item.client}\n" +
            ")\n" +
            "</foreach>\n" +
            "</script>")
    void insertBatchDetail(List<BinOrderDetailDO> list);

    @Insert("<script>" +
            "INSERT INTO [dbo].[bin_order_detail_split]\n" +
            "([from_id],[calc_id],[status],[app_id],[model_no],[quantity],[order_no],[trans_type],[order_type],[dlv_date],[supplier_code],[del_flag],[create_time],[update_time],[update_user],[create_user],[item_no],[warehouse_code])     \n" +
            "VALUES \n" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">\n" +
            "( \n" +
            "#{item.fromId},\n" +
            "#{item.calcId},\n" +
            "#{item.status},\n" +
            "#{item.appId},\n" +
            "#{item.modelNo},\n" +
            "#{item.confirmQty},\n" +
            "#{item.orderNo},\n" +
            "#{item.transType},\n" +
            "#{item.orderType},\n" +
            "#{item.dlvDate},\n" +
            "#{item.supplierCode},\n" +
            "#{item.delFlag},\n" +
            "#{item.createTime},\n" +
            "#{item.updateTime},\n" +
            "#{item.updateUser},\n" +
            "#{item.createUser},\n" +
            "#{item.itemNo},\n" +
            "#{item.warehouseCode}\n" +
            ")\n" +
            "</foreach>\n" +
            "</script>")
    void insertBatchSplit(List<BinOrderDetailSplitDO> list);
    @Insert("<script>" +
            "INSERT INTO [dbo].[bin_order_detail_ording_info]\n" +
            "([calc_id],[bin_detail_id],[order_type],[order_no],[item_no],[split_no],[modelno],[warehouse_code],[quantity],[orderDate],[expDate],[supplier],[trans_type],[inQty],[del_flag],[create_time],[create_user],[update_time],[update_user])     VALUES\n" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">\n" +
            "( \n" +
            "#{item.calcId},\n" +
            "#{item.binDetailId},\n" +
            "#{item.orderType},\n" +
            "#{item.orderNo},\n" +
            "#{item.itemNo},\n" +
            "#{item.splitNo},\n" +
            "#{item.modelno},\n" +
            "#{item.warehouseCode},\n" +
            "#{item.quantity},\n" +
            "#{item.orderDate,jdbcType=TIMESTAMP},\n" +
            "#{item.expDate,jdbcType=TIMESTAMP},\n" +
            "#{item.supplier},\n" +
            "#{item.transType},\n" +
            "#{item.inQty},\n" +
            "#{item.delFlag},\n" +
            "#{item.createTime,jdbcType=TIMESTAMP},\n" +
            "#{item.createUser},\n" +
            "#{item.updateTime,jdbcType=TIMESTAMP},\n" +
            "#{item.updateUser}\n" +
            ")\n" +
            "</foreach>\n" +
            "</script>")
    void insertBatchOrding(List<BinOrderDetailOrdingInfoDO> list);


    @Insert("<script>" +
            "INSERT INTO [dbo].[bin_order_detail_pre_info]\n" +
            "([calc_id],[bin_detail_id],[order_type],[po_order_no],[order_no],[modelno],[quantity],[del_flag],[create_time],[create_user],[update_time],[update_user])     VALUES\n" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">\n" +
            "( \n" +
            "#{item.calcId},\n" +
            "#{item.binDetailId},\n" +
            "#{item.orderType},\n" +
            "#{item.poOrderNo},\n" +
            "#{item.orderNo},\n" +
            "#{item.modelno},\n" +
            "#{item.quantity},\n" +
            "#{item.delFlag},\n" +
            "#{item.createTime,jdbcType=TIMESTAMP},\n" +
            "#{item.createUser},\n" +
            "#{item.updateTime,jdbcType=TIMESTAMP},\n" +
            "#{item.updateUser}\n" +
            ")\n" +
            "</foreach>\n" +
            "</script>")
    void insertBatchPre(List<BinOrderDetailPreInfoDO> list);


}

package com.smc.smccloud.mapper.purchase;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.smc.smccloud.model.purchase.PurchaseModifyDO;
import com.smc.smccloud.model.purchase.PurchaseModifyRequest;
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
 * @since 2023-10-24
 */
@Mapper
@DS("sharedb")
public interface PurchaseModifyMapper extends BaseMapper<PurchaseModifyDO> {


    @Select(" <script> select \n" +
            " a.id,modify_type,status,batch_no,apply_no,apply_content,apply_reason,apply_person_no,a.apply_dept_no\n" +
            " ,apply_time,orderFno,a.orderNo,a.itemNo,a.splitItemNo\n" +
            " ,b.modelno,b.quantity,b.deptNo dept_no,b.customerNo customer_no,b.userNo user_no,b.stateCode purchase_statecode\n" +
            "  ,b.supplierId supplierId,a.netweight,b.transType,b.hopeExportDate\n" +
            "  ,handler,handle_result,handle_time,a.remark,create_time,create_user,update_time,update_user,supplier_orderno\n" +
            "from purchase_modify a left join ops_core.dbo.${tablename} b on  a.orderNo = b.orderNo and a.itemNo = b.itemNo and ((a.splitItemNo = b.splitItemNo) OR  (a.splitItemNo IS NULL AND b.splitItemNo IS NULL))  \n" +
            "  where 1=1 \n" +
            "    <if test=\"request.applyNo != null  and request.applyNo != ''\">and apply_No = #{request.applyNo}</if>\n" +
            "    <if test=\"request.orderNo != null  and request.orderNo != ''\">and  orderFno like concat('%', #{request.orderNo}, '%')</if>\n" +
            "    <if test=\"request.modifyType != null  and request.modifyType != ''\">and modify_type = #{request.modifyType}</if>\n" +
            "    <if test=\"request.modelno != null  and request.modelno != ''\">and b.modelno like concat('%', #{request.modelno}, '%')</if>\n" +
            "    <if test=\"request.status != null  and request.status != ''\">and status = #{request.status}</if>\n" +
            "    <if test=\"request.deptNo != null  and request.deptNo != ''\">and b.deptNo = #{request.deptNo}</if>\n" +
            "    <if test=\"request.supplierId != null  and request.supplierId != ''\">and b.supplierId = #{request.supplierId}</if>\n" +
            "    <if test=\"request.transType != null  and request.transType != ''\">and b.transType = #{request.transType}</if>\n" +
            "    <if test=\"request.applyTimeStart != null\">and apply_time   between #{request.applyTimeStart} and  #{request.applyTimeEnd}</if>\n" +
            "\t<if test=\"request.handleTimeStart != null\">and handle_time   between #{request.handleTimeStart} and  #{request.handleTimeEnd}</if>\n" +
            "\t<if test = 'request.orderNoList != null and request.orderNoList.size() &gt; 0 ' > and orderFno in \n" +
            "\t<foreach collection = 'request.orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' >\n" +
            "\t#{item}\n" +
            "\t</foreach>\n" +
            "\t</if>\n" +
            " </script> ")
    List<PurchaseModifyDO> selectByPurchase(@Param("request") PurchaseModifyRequest request,@Param("tablename") String tablename);

    @Select(" <script> select \n" +
        " b.* \n" +
        "from purchase_modify a inner join ops_core.dbo.ops_purchaseOrder b on  a.orderNo = b.orderNo and a.itemNo = b.itemNo and ((a.splitItemNo = b.splitItemNo) OR  (a.splitItemNo IS NULL AND b.splitItemNo IS NULL))  \n" +
        " where 1=1 \n" +
        "    <if test= ' request.applyNo != null  and request.applyNo != \"\" '>and apply_No = #{request.applyNo}</if>\n" +
        "    <if test= ' request.orderNo != null  and request.orderNo != \"\" '>and  orderFno like concat('%', #{request.orderNo}, '%')</if>\n" +
        "    <if test= ' request.modifyType != null  and request.modifyType != \"\" '>and modify_type = #{request.modifyType}</if>\n" +
        "    <if test= ' request.modelno != null  and request.modelno != \"\" '>and b.modelno like concat('%', #{request.modelno}, '%')</if>\n" +
        "    <if test= ' request.status != null  and request.status != \"\" '>and status = #{request.status}</if>\n" +
        "    <if test= ' request.deptNo != null  and request.deptNo != \"\" '>and b.deptNo = #{request.deptNo}</if>\n" +
        "    <if test= ' request.supplierId != null  and request.supplierId != \"\" '>and b.supplierId = #{request.supplierId}</if>\n" +
        "    <if test= ' request.transType != null  and request.transType != \"\" '>and b.transType = #{request.transType}</if>\n" +
        "    <if test= ' request.applyTimeStart != null '>and apply_time   between #{request.applyTimeStart} and  #{request.applyTimeEnd}</if>\n" +
        " <if test= ' request.handleTimeStart != null '>and handle_time   between #{request.handleTimeStart} and  #{request.handleTimeEnd}</if>\n" +
        "\t<if test = 'request.orderNoList != null and request.orderNoList.size() &gt; 0 '> and orderFno in \n" +
        "\t<foreach collection = 'request.orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' >\n" +
        "\t#{item}\n" +
        "\t</foreach>\n" +
        "\t</if>\n" +
        " </script>")
    List<OpsPurchaseorder> getPurchaseOrder(@Param("request") PurchaseModifyRequest request);


    @Select(" <script> select \n" +
            " b.* \n" +
            "from purchase_modify a inner join ops_core.dbo.ops_requestPurchase b on  a.orderNo = b.orderNo and a.itemNo = b.itemNo and ((a.splitItemNo = b.splitItemNo) OR  (a.splitItemNo IS NULL AND b.splitItemNo IS NULL))  \n" +
            " where 1=1 \n" +
            "    <if test=\"request.applyNo != null  and request.applyNo != ''\">and apply_No = #{request.applyNo}</if>\n" +
            "    <if test=\"request.orderNo != null  and request.orderNo != ''\">and  orderFno like concat('%', #{request.orderNo}, '%')</if>\n" +
            "    <if test=\"request.modifyType != null  and request.modifyType != ''\">and modify_type = #{request.modifyType}</if>\n" +
            "    <if test=\"request.modelno != null  and request.modelno != ''\">and b.modelno like concat('%', #{request.modelno}, '%')</if>\n" +
            "    <if test=\"request.status != null  and request.status != ''\">and status = #{request.status}</if>\n" +
            "    <if test=\"request.deptNo != null  and request.deptNo != ''\">and b.deptNo = #{request.deptNo}</if>\n" +
            "    <if test=\"request.supplierId != null  and request.supplierId != ''\">and b.supplierId = #{request.supplierId}</if>\n" +
            "    <if test=\"request.transType != null  and request.transType != ''\">and b.transType = #{request.transType}</if>\n" +
            "    <if test=\"request.applyTimeStart != null\">and apply_time   between #{request.applyTimeStart} and  #{request.applyTimeEnd}</if>\n" +
            "\t<if test=\"request.handleTimeStart != null\">and handle_time   between #{request.handleTimeStart} and  #{request.handleTimeEnd}</if>\n" +
            "\t<if test =\"request.orderNoList != null and request.orderNoList.size() &gt; 0 \"> and orderFno in \n" +
            "\t<foreach collection = 'request.orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' >\n" +
            "\t#{item}\n" +
            "\t</foreach>\n" +
            "\t</if>\n" +
            "  </script> ")
    List<OpsRequestpurchase> getRequestPurchase(@Param("request") PurchaseModifyRequest request);
}

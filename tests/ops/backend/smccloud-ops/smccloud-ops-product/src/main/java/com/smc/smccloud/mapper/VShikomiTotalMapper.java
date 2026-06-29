package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.ShikomiCondition;
import com.smc.smccloud.model.shikomi.ShikomiTotalDO;
import com.smc.smccloud.model.shikomi.VShikomiTotalDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author edp04
 * @title: VShikomiTotalMapper
 * @date 2022/04/29 16:13
 */
@Mapper
@DS("opsdb")
public interface VShikomiTotalMapper extends BaseMapper<VShikomiTotalDO> {

    @Select("<script>"+
            "SELECT ApplyNo,LotQty,CustomerNo,DeptNo,RemainQty,ShikomiNo,ApplyQty,CompanyCode,modelno,QtyWarning,ClassCode,QtyOrdPre FROM v_shikomi_total" +
            " WHERE Status = 1 AND ShikomiNo in " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    List<VShikomiTotalDO> selectListByNo(@Param("list") List<String> list);

    @Select("<script>"+
            "SELECT a.ApplyNo,a.ShikomiNo,a.CustomerNo,a.DeptNo,a.RemainQty,a.ApplyQty,a.CompanyCode,b.modelno,a.QtyWarning,a.ClassCode,a.LotQty,a.ClassType,a.SubsidiaryCode,a.QtyOrdPre\n" +
            "FROM shikomi_total a with(nolock) INNER JOIN shikomi_model b with(nolock) ON a.ShikomiNo = b.shikomino" +
            " WHERE Status = 1 AND b.modelno in " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    List<VShikomiTotalDO> findByModelNoList2(@Param("list") List<String> list);

    @Select(
            "SELECT a.ApplyNo,a.ShikomiNo,a.CustomerNo,a.DeptNo,a.RemainQty,a.ApplyQty,a.CompanyCode,b.modelno,a.QtyWarning,a.ClassCode,a.LotQty,a.ClassType,a.SubsidiaryCode,a.QtyOrdPre\n" +
            "FROM shikomi_total a with(nolock) INNER JOIN shikomi_model b with(nolock) ON a.ShikomiNo = b.shikomino" +
            " WHERE Status = 1 AND b.modelno = #{modelNo} ")
    List<VShikomiTotalDO> findByModelNoList3(@Param("modelNo") String modelNo);

    @Select("SELECT DISTINCT modelno FROM v_shikomi_total where serialModel<>'' ")
    List<String> getSerialModel();

    @Select("SELECT DISTINCT modelno,ShikomiNo FROM v_shikomi_total where serialModel<>'' AND modelno like '%*%'")
    List<VShikomiTotalDO> getSerialModel2();

    @Select("<script>"+
            "SELECT (sum(qtyP-pqtyP) + sum(qtyC-pqtyC) + sum(qtyD-pqtyD)) as qtyPO,sum(qtyN -pqtyN) as qtyOnhand FROM inventory_report where " +
            "modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "and customer_no=#{customerNo}" +
            "</script>")
    ShikomiTotalDO getpurchaseQty3(@Param("modelNos") List<String> modelNos,@Param("customerNo") String customerNo);


    @Select("<script>"+
            "SELECT (M31+M32+M33+M34+M35+M36) as avgOrdQty FROM ops_report.dbo.model_exp_freq WHERE " +
            " ModelType=1 AND StockType =3 AND StockCode =#{customerNo} and ModelNo in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    List<Integer> getOrdersByNo(@Param("modelNos") List<String> modelNos,@Param("customerNo") String customerNo);


    @Select("<script>"+
            "SELECT (sum(qtyP-pqtyP) + sum(qtyC-pqtyC) + sum(qtyD-pqtyD)) as qtyPO,sum(qtyN -pqtyN) as qtyOnhand FROM inventory_report where modelno in  " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    ShikomiTotalDO getpurchaseQty2(@Param("modelNos") List<String> modelNos);


    @Select("<script>"+
            "SELECT (M31+M32+M33+M34+M35+M36) as avgOrdQty FROM ops_report.dbo.model_exp_freq WHERE " +
            "ModelType=1 AND StockType =0 AND StockCode = 'ALL' and ModelNo in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    List<Integer> getOrdersByNo2(@Param("modelNos") List<String> modelNos);


    @Select("<script>"+
            "SELECT (sum(qtyP-pqtyP) + sum(qtyC-pqtyC) + sum(qtyD-pqtyD)) as qtyPO,sum(qtyN -pqtyN) as qtyOnhand FROM inventory_report where " +
            "modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "and inventory_type_code='TY'" +
            "</script>")
    ShikomiTotalDO getpurchaseQty(@Param("modelNos") List<String> list);
}

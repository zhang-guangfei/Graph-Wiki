package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.RcvMasterDO;
import org.apache.ibatis.annotations.Mapper;


@DS("opsdb")
@Mapper
public interface RcvmasterMapper extends BaseMapper<RcvMasterDO> {

//    @Select("<script>" +
//            "\t select DISTINCT rm.* from rcvmaster rm INNER JOIN rcvdetail rd\n" +
//            "\t on rm.rorder_no = rd.rorder_no\n" +
//            "\t where 1=1 " +
//            "<if test = 'request.customerNo != null and request.customerNo != \"\" ' >" +
//            " and rm.customer_no like #{request.customerNo}"+
//            "</if>" +
//            "<if test = 'request.rorderNo != null and request.rorderNo != \"\" ' >" +
//            " and rm.rorder_no like #{request.rorderNo}"+
//            "</if>" +
//            "<if test = 'request.purchaseNo != null and request.purchaseNo != \"\" ' >" +
//            " and rm.PurchaseNO like #{request.purchaseNo}"+
//            "</if>" +
//            "<if test = 'request.hlCode != null and request.hlCode != \"\" ' >" +
//            " and rm.HLCode = #{request.hlCode}"+
//            "</if>" +
//            "<if test = 'request.strStartDate != null and request.strStartDate != \"\" ' >" +
//            " and rm.ROrdDate &gt;= #{request.strStartDate}"+
//            "</if>" +
//            "<if test = 'request.strEndDate != null and request.strEndDate != \"\" ' >" +
//            " and rm.ROrdDate &lt;= #{request.strEndDate}"+
//            "</if>" +
//            "<if test = 'request.modelNo != null and request.modelNo != \"\" ' >" +
//            " and rd.model_no like #{request.modelNo}"+
//            "</if>" +
//            "<if test = 'request.cproductNo != null and request.cproductNo != \"\" ' >" +
//            " and rd.cproduct_no like #{request.cproductNo}"+
//            "</if>" +
//            " order by rm.ROrdDate desc"+
//            "</script>")
//    List<RcvMasterDO> listData(@Param("request") RcvMasterRequest request);
//
//    @Select("select dlv_entire,customer_no,user_no from rcvmaster where rorder_no = #{orderNo} ")
//    DlvEntireVO getDlvEntireByOrOrderNo(@Param("orderNo") String orderNo);


}

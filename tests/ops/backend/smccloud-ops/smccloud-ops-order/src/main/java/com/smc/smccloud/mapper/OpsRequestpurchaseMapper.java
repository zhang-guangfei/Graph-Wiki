package com.smc.smccloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.requestpurchase.OpsRequestpurchaseDO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-07-24
 */
@Mapper
@DS("opsdb")
public interface OpsRequestpurchaseMapper extends BaseMapper<OpsRequestpurchaseDO> {

	@Select("select RequestPurchaseId from ops_req_po_mapping where PurchaseOrderNo = #{mrNo}")
	List<String> getRequestPurchaseIdByMrNo(@Param("mrNo") String mrNo);

	// bug13662 废除采购合并表，请购表中增加采购单号
	@Select("select * from ops_requestPurchase where po_order_no = #{mrNo}")
	List<OpsRequestpurchaseDO> getRequestPurchaseByMrNo(@Param("mrNo") String mrNo);

}

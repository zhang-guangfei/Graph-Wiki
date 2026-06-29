package com.smc.smccloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.adapter.order.POOrderNOVO;

@Mapper
@DS("opsdb")
public interface OpsPurchaseInvoiceMapper extends MPJBaseMapper<OpsPurchaseInvoiceDO> {
	// <!--add by WuWeiDong 20221104 task 2089 -->
	@Select("<script>"
			+ "select  poNo,lineItem,modelNo,quantity,leftQuantity=quantity-qtyImport ,stateCode,supplierId,receiveWarehouseId,purchaseDate "
			+ " from ops_purchaseInvoice  where " + "<if test = 'orderNos != null and  orderNos.size() &gt; 0' >"
			+ "  <foreach collection = 'orderNos' item = 'item' index='index' open='(' close=')'  separator='or'> "
			+ "   (poNo= #{item.poNo} and lineItem=#{item.lineItem}) " + "  </foreach>" + "</if>" + "</script>")
	List<POOrderNOVO> listPOOrder(List<POOrderNOVO> orderNos);

	@Select("select * from ops_purchaseInvoice where pono=#{pono} and lineitem=#{lineitem}")
	OpsPurchaseInvoiceDO getByPono(String pono, Integer lineitem);
}

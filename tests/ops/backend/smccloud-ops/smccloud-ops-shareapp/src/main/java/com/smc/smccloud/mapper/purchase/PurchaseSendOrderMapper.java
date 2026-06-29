package com.smc.smccloud.mapper.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.dto.purchase.OrderSalesGPDto;
import com.smc.smccloud.model.purchase.SMCOrderDO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author B91717
 * @since 2021-11-03 CTC订单写入
 */
@DS("gzfddb")
@Mapper
public interface PurchaseSendOrderMapper extends BaseMapper<SMCOrderDO> {

	// bug 13517 增加客户订单号及客户型号
	@Insert("<script> INSERT INTO OrderSales_GZ (RorderNo,rorderitem,Workday,Status,CustomerNo,UserNo,ModelNo,"
			+ "Quantity,price,Account,Dlvdate,RcvClassify,StockCode,ExpInvCode,OrdTransType,DlvEntire,"
			+ "TransFee,TransChannel,OptDate,OrderSourceEntity,AddressId,Remark,VIPCode,VIPPriority,CProductNo"
			+ ",COrderNo) values   <foreach collection='list' item='item' index='index' separator=','>  "
			+ " (#{item.rorderno},#{item.rorderitem},#{item.workday},#{item.status},#{item.customerno},"
			+ "#{item.userno},#{item.modelno},"
			+ "#{item.quantity},#{item.price},#{item.account},#{item.dlvdate},#{item.rcvclassify},"
			+ "#{item.stockcode},#{item.expinvcode},#{item.ordtranstype} "
			+ ",#{item.dlventire} ,#{item.transfee} ,#{item.transchannel} ,#{item.optdate} ,"
			+ "#{item.ordersourceentity},#{item.addressid},#{item.remark},#{item.vipcode},#{item.vippriority}"
			+ ",#{item.cproductno},#{item.corderno})  </foreach> </script> ")
	Boolean insertGzOrder(@Param("list") List<OrderSalesGPDto> list);

}

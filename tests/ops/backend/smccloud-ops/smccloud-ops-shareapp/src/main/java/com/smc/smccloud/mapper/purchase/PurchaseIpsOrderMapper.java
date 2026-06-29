package com.smc.smccloud.mapper.purchase;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author B91717
 * @since 2021-11-03 CTC订单写入
 */
@DS("ipssharedb")
@Mapper
public interface PurchaseIpsOrderMapper extends BaseMapper<IpsReceiveOrderAllOriginalInfoDto> {

	// bug 13517 增加客户订单号及客户型号
	@Insert("<script> INSERT INTO ips_receive_order_all_original_info (" +
			"source_type,source_system,src_order_no,src_order_item_no\n" +
			",content,batch_no,batch_num,create_time,create_user" +
			") values   <foreach collection='list' item='item' index='index' separator=','>  "
			+ " (#{item.sourceType},#{item.sourceSystem},#{item.srcOrderNo},#{item.srcOrderItemNo},#{item.content,jdbcType=VARCHAR},"
			+ "#{item.batchNo},#{item.batchNum},#{item.createTime},#{item.createUser}"
			+ " )  </foreach> </script> ")
	Boolean insertIpsReceiveOrder(@Param("list") List<IpsReceiveOrderAllOriginalInfoDto> list);

}

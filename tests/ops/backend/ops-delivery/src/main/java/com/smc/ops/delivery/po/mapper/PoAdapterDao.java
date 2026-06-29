package com.smc.ops.delivery.po.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.poDeliver.ChangeFromSupplier;

@DS("poadapterdb")
@Mapper
public interface PoAdapterDao {

	@Select("select a.id,a.code as maincode,a.pono,a.line_item,a.supplier_code,c.supplier_order_main_no"
			+ ",c.supplier_order_line_no,c.receive_order_date,c.fact_manufacturer as good_source_code_remark,c.model_no"
			+ ",c.transtype_code as replytranstype,c.goods_source_code,c.produce_unit,c.produce_start_time,c.send_file_path"
			+ ",b.id as planid,b.latest_delivery_time,b.src_delivery_time"
			+ ",b.transtype_code as plantranstype,b.remark as planreamrk,b.quantity as planqty"
			+ ",b.expected_production_completion_dateH as deliverytimeHPlan"
			+ ",b.expected_logistics_arrival_dateW as deliverytimeWPlan"
			+ ",d.delivery_time_W as deliverytimeWFact,d.transtype_code as facttranstype"
			+ ",d.delivery_time_H as deliverytimeHFact,d.id as factid,d.quantity as factqty"
			+ " from [ops_po_changed_log_from_supplier] a left join [ops_po_delivery_plan_log_from_supplier] b"
			+ " on a.id=b.pid left join [ops_po_acked_log_from_supplier] c"
			+ " on a.id=c.pid left join [ops_po_exporting_log_from_supplier] d"
			+ " on a.id=d.pid where a.id>=#{id} and a.id<#{endid} and (a.del_flag=0 or a.del_flag is null)"
			+ " and (b.del_flag=0 or b.del_flag is null) and (c.del_flag=0 or c.del_flag is null)"
			+ " and (d.del_flag=0 or d.del_flag is null) order by a.id")
	List<ChangeFromSupplier> selectMainLog(@Param("id") long id, @Param("endid") long endid);
}

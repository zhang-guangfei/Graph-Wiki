package com.sales.ops.db.extdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.dto.delivery.ModelHistoryDeliveryDayDto;
import com.sales.ops.dto.delivery.OpsCalPoModelBase;
import com.sales.ops.dto.delivery.OpsCalPoSupplierWarehouseTransBase;
import com.sales.ops.dto.delivery.SupplierHistoryDeliveryDayDto;
import com.sales.ops.dto.delivery.TypeHistoryDeliveryDayDto;

public interface OpsCalPoModelBaseDao {

	// 获取限制海运的配置表
	@Select("select * from ops_po_transtype_config where transtype='0'")
	List<OpsPoTranstypeConfig> selectSeaConfig();

	// 生成计算基础供应商及型号信息
	@Select("select top 1 * from ops_cal_po_model_base where model_no=#{modelNo}")
	public OpsCalPoModelBase selectModelBase(@Param("modelNo") String modelNo);

	@Select("select * from ops_cal_po_supplier_warehouse_trans_base order by supplier_class,trans_day")
	public List<OpsCalPoSupplierWarehouseTransBase> selectSupplierTransBase();

	@Select("SELECT [型号] as modelNo,[数量区间开始] as qtyStart,[数量区间结束] as qtyEnd,[出荷天数70%] as deliveryDay"
			+ " FROM dm_History_delivery_70days_model")
	public List<ModelHistoryDeliveryDayDto> selectModelHistoryDay();

	@Select("SELECT [型号] as modelNo,[数量区间开始] as qtyStart,[数量区间结束] as qtyEnd,[出荷天数70%] as deliveryDay"
			+ " FROM dm_History_delivery_70days_model where [型号] = #{modelNo}")
	public List<ModelHistoryDeliveryDayDto> selectModelHistoryDayByModel(@Param("modelNo") String modelNo);

	@Select("SELECT [产品系列] as typeClassId,[产品属性] as designName,[数量区间开始] as qtyStart"
			+ ",[数量区间结束] as qtyEnd,[出荷天数70%] as deliveryDay FROM dm_History_delivery_70days_type")
	public List<TypeHistoryDeliveryDayDto> selectTypeHistoryDay();

	@Select("SELECT [产品系列] as typeClassId,[产品属性] as designName,[数量区间开始] as qtyStart"
			+ ",[数量区间结束] as qtyEnd,[出荷天数70%] as deliveryDay FROM dm_History_delivery_70days_type where [产品系列] = #{typeClassId}")
	public List<TypeHistoryDeliveryDayDto> selectTypeHistoryDayByTypeClassId(@Param("typeClassId") String typeClassId);

	@Select("select [供应商] as supplierId,[产品属性] as designName,[出荷天数70%] as deliveryDay"
			+ " from dm_History_delivery_70days_supplier ")
	public List<SupplierHistoryDeliveryDayDto> selectSupplierHistoryDay();
}

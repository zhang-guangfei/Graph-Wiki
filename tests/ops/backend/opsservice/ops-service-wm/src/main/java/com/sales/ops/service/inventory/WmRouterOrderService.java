package com.sales.ops.service.inventory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.RcvdetailExample;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.inventory.AssBom;
import com.sales.ops.dto.inventory.CrePcoByInventoryDto;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryDispatchDto;
import com.sales.ops.dto.inventory.WmOrderByInventoryDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.RcvOrderStatusEnum;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/3 12:43 @description：生成WMS的订单（路由调度）
 */
public interface WmRouterOrderService {

	void updateRcvdetail(Rcvdetail rcvdetail, RcvdetailExample exam);

	// List<OpsOrderDispatchView> getOpsOrderDispatchViews(String rorderNo, int
	// rorderItem);

    //获得一定数量的偶数id的rcvdetail
    List<Rcvdetail> getRcvdetailByLimitAndModEvenNumberId(int limit, Short status);
    //获得一定数量的奇数id的rcvdetail
    List<Rcvdetail> getRcvdetailByLimitAndModOddNumberId(int limit, Short status);

	List<Rcvdetail> getRcvdetailList(int limit, Short status);

	List<Rcvdetail> getRcvdetailListByBeforeMoth(int limit, Short status, int beforeMonth);

	List<Rcvdetail> getRcvdetailListByOrder(String rorderNo);

	Rcvmaster getRcvmaster(String rorderNo);

	void updateRcvDetailToHandled(Long id, String rorderNo, Integer rorderItem, RcvOrderStatusEnum rcvOrderStatusEnum, Date wmDlvDate) throws OpsException;

	void updateRcvDetailByIdToStatus(Long id, String rorderNo, Integer rorderItem,  RcvOrderStatusEnum rcvOrderStatusEnum,String expMsg);

	Rcvdetail getRcvdetail(String rorderNo, String rorderItem) throws OpsException;

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 12:52
	 * @description：货齐生成调度指令规则
	 */
	void GatherOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException;

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 12:52
	 * @description：不做集约发货,生成调度指令
	 */
	void UngatherOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException;

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/11/22 14:39
	 * @description：按照采购创建销售出库单
	 */
	void CreateCGDo(InventoryCkByOrderInputDto inputDto, WmOrderByInventoryDto inventoryDto) throws OpsException;

	CrePcoByInventoryDto InitPcoByInventory(InventoryCkByOrderInputDto inputDto, String doType, BigDecimal weight,
			String pcoid, String gatherhouse, AssBom assBom, Map<Object, Integer> inventoryMapSorted);
}

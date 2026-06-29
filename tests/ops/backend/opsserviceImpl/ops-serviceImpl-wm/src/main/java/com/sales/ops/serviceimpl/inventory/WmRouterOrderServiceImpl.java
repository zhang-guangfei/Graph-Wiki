package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.dao.RcvmasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.db.extdao.OpsRcvDetailDao;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author C02483
 * @version 1.0
 * @description: 生成WMS的订单（路由调度）
 * @date 2021/10/3 12:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WmRouterOrderServiceImpl implements WmRouterOrderService {

	@Autowired
	private RcvmasterMapper rcvmasterMapper;
	@Autowired
	private RcvdetailMapper rcvdetailMapper;

	@Autowired
	private OpsRcvDetailDao opsRcvDetailDao;
	@Autowired
    private OpsCustomerOrderService opsCustomerOrderService;

	@Autowired
	private OPSWarehouseDao opsWarehouseDao;
	@Autowired
	private BaseCustomerOrderService baseCustomerOrderService;
	@Autowired
	private OpsWarehouseService opsWarehouseService;

	/**
	 * 更新 异常 改状态 status 10为异常单
	 */
	public void updateRcvdetail(Rcvdetail rcvdetail, RcvdetailExample exam) {
		rcvdetailMapper.updateByExampleSelective(rcvdetail, exam);
	}


    @Override
    public List<Rcvdetail> getRcvdetailByLimitAndModEvenNumberId(int limit, Short status) {
		Date nowDate = new Date();
		Date creDate = DateUtil.addMonth(nowDate,-1);//查询前1个月的数据处理
        return opsRcvDetailDao.getRcvdetailByLimitAndModEvenNumberId(limit, status,DateUtil.dateToDateString(creDate));
    }

    @Override
    public List<Rcvdetail> getRcvdetailByLimitAndModOddNumberId(int limit, Short status) {
		Date nowDate = new Date();
		Date creDate = DateUtil.addMonth(nowDate,-1);//查询前1个月的数据处理
        return opsRcvDetailDao.getRcvdetailByLimitAndModOddNumberId(limit, status,DateUtil.dateToDateString(creDate));
    }

    /**
	 * @author ：c02483
	 * @date ：Created in 2021/11/17 19:06
	 * @description：获取待发送的明细
	 */
	@Override
	public List<Rcvdetail> getRcvdetailList(int limit, Short status) {
		Date nowDate = new Date();
		Date creDate = DateUtil.addMonth(nowDate,-1);//查询前1个月的数据处理
		return opsRcvDetailDao.getRcvdetailByLimit(limit, status,DateUtil.dateToDateString(creDate));
	}

	/**
	 * bugid:10618 C14717 20230808 订单自动处理增加补偿程序
	 * @param limit
	 * @param status
	 * @param beforeMonth
	 * @return
	 */
	@Override
	public List<Rcvdetail> getRcvdetailListByBeforeMoth(int limit, Short status,int beforeMonth) {
		Date nowDate = new Date();
		Date creDate = DateUtil.addMonth(nowDate,-1*beforeMonth);//查询前n个月的数据处理
		return opsRcvDetailDao.getRcvdetailByLimit(limit, status,DateUtil.dateToDateString(creDate));
	}

	@Override
	public List<Rcvdetail> getRcvdetailListByOrder(String rorderNo) {
		RcvdetailExample example = new RcvdetailExample();
		example.createCriteria().andRorderNoEqualTo(rorderNo).andStatusEqualTo((short) 1);
		List<Rcvdetail> list = rcvdetailMapper.selectByExample(example);
		return list;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/11/19 9:43
	 * @description：操作成功的
	 */
	@Override
	public void updateRcvDetailToHandled(Long id, String rorderNo, Integer rorderItem,
			RcvOrderStatusEnum rcvOrderStatusEnum,Date wmDlvDate) throws OpsException {
		Rcvdetail rcvdetail = new Rcvdetail();
		//rcvdetail.setId(id);
		rcvdetail.setRorderNo(rorderNo);
		rcvdetail.setRorderItem(rorderItem);
		rcvdetail.setStatus(rcvOrderStatusEnum.getType());
		Rcvdetail detail = baseCustomerOrderService.findRcvDetailByNo(rorderNo, rorderItem);
		RcvOrderStatusEnum statusEnum = RcvOrderStatusEnum.getEnumByType(detail.getStatus());
        opsCustomerOrderService.checkChangeable(statusEnum, rcvOrderStatusEnum);
		rcvdetail.setWmsDlvDate(wmDlvDate);
		rcvdetail.setUpdateTime(DateUtil.getNow());
		rcvdetailMapper.updateByPrimaryKeySelective(rcvdetail);
	}

	@Override
	public void updateRcvDetailByIdToStatus(Long id, String rorderNo, Integer rorderItem,
											RcvOrderStatusEnum rcvOrderStatusEnum,String expMsg){
		Rcvdetail rcvdetail = new Rcvdetail();
		//rcvdetail.setId(id);
		rcvdetail.setRorderNo(rorderNo);
		rcvdetail.setRorderItem(rorderItem);
		rcvdetail.setStatus(rcvOrderStatusEnum.getType());
		rcvdetail.setExpMsg(expMsg);
		rcvdetail.setUpdateTime(new Date());
		rcvdetailMapper.updateByPrimaryKeySelective(rcvdetail);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/11/17 19:06
	 * @description：获取客户主单信息
	 */
	@Override
	public Rcvmaster getRcvmaster(String rorderNo) {
		return rcvmasterMapper.selectByPrimaryKey(rorderNo);
	}

	@Override
	public Rcvdetail getRcvdetail(String rorderNo,String rorderItem) throws OpsException{
		return baseCustomerOrderService.findRcvDetailByNo(rorderNo,rorderItem);
	}


	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 13:21
	 * @description：单项货齐整型号生成指令
	 */
	private void itemGatherOrderWhole(InventoryCkByOrderInputDto inputDto, String warehouseCode,
			InventoryDispatchDto dispatchDto, List<InventoryDispatchDto.InvQty> invQtyList,
			List<InventoryDispatchDto.InvMoveQty> invMoveQtyList, WmOrderByInventoryDto inventoryDto, UserDto userDto)
			throws OpsException {
		String doid = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), inputDto.getOrderId(),
				String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
				String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
		DoWaitTypeEnum doWaitTypeEnum = DoWaitTypeEnum.OK;
		if (!Objects.equals(dispatchDto.getGatherhouse(), warehouseCode)) {
			doWaitTypeEnum = DoWaitTypeEnum.WaitDB;
		}

		CreDoByInventoryDto doByInventoryDto = InitDoByInventory(inputDto, DoTypeEnum.JYCK.getType(), doid,
				inputDto.getModelno(), warehouseCode, dispatchDto.getGatherhouse(), inputDto.getAssItem(), 0,
				dispatchDto.getDoSourceEnum(), doWaitTypeEnum, invQtyList, invMoveQtyList,
				dispatchDto.getInventoryMapSorted(), userDto);
		if (!Objects.equals(dispatchDto.getGatherhouse(), warehouseCode)) {
			String dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), inputDto.getOrderId(),
					String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
					String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
			String dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), inputDto.getOrderId(),
					String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
					String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
			inputDto.setCustOrderStatusEnum(RcvOrderStatusEnum.DBING);
			CreDoByInventoryDto DBdoByInventoryDto = InitDoByInventory(inputDto, DoTypeEnum.DBCK.getType(), dbckid,
					inputDto.getModelno(), warehouseCode, dispatchDto.getGatherhouse(), inputDto.getAssItem(), 0,
					dispatchDto.getDoSourceEnum(), DoWaitTypeEnum.OK, invQtyList, invMoveQtyList,
					dispatchDto.getInventoryMapSorted(), userDto);
			inventoryDto.getDolist().add(DBdoByInventoryDto); // 调拨出库单

			CreRoByInventoryDto roByInventoryDto = InitRoByInventory(inputDto, RoTypeEnum.DBRK.getType(), dbrkid,
					dispatchDto.getGatherhouse(), inputDto.getAssItem(), 0, invQtyList, invMoveQtyList, userDto);
			if (DoSourceEnum.ALL.getType().equals(dispatchDto.getDoSourceEnum().getType()) || DoSourceEnum.CG.getType().equals(dispatchDto.getDoSourceEnum().getType())) {
				roByInventoryDto.getOpsRo().setNum(0);//整项设置为0
			}
			inventoryDto.getRoList().add(roByInventoryDto);// 调拨入库单 doWaitTypeEnum,
			doByInventoryDto.setItemInventorys(null);
		}

		inventoryDto.getDolist().add(doByInventoryDto); // 销售出库单
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 13:52
	 * @description：拆分型号，生成调拨指令
	 */
	private void AssitemGatherDBOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			String warehouseCode, WmOrderByInventoryDto inventoryDto, List<InventoryDispatchDto.InvQty> invQtyList,
			List<InventoryDispatchDto.InvMoveQty> invMoveQtyList, UserDto userDto) throws OpsException {
		if (!Objects.equals(dispatchDto.getGatherhouse(), warehouseCode)) {
			// 调拨出库单（收货单和调拨出是否越库？）
			// 调拨入库单
			Map<String, List<InventoryDispatchDto.InvQty>> map = new HashMap<>();
			Map<String, List<InventoryDispatchDto.InvMoveQty>> mapmove = new HashMap<>();
			Set<String> set = new HashSet<>();
			if (CollectionUtils.isNotEmpty(invQtyList)) {
				for (InventoryDispatchDto.InvQty invQty : invQtyList) {
					set.add(invQty.getInventory().getModelno());
					if (map.containsKey(invQty.getInventory().getModelno())) {
						map.get(invQty.getInventory().getModelno()).add(invQty);
					} else {
						List<InventoryDispatchDto.InvQty> list = new ArrayList<>();
						list.add(invQty);
						map.put(invQty.getInventory().getModelno(), list);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(invMoveQtyList)) {
				for (InventoryDispatchDto.InvMoveQty invMoveQty : invMoveQtyList) {
					set.add(invMoveQty.getInventory().getModelno());
					if (mapmove.containsKey(invMoveQty.getInventory().getModelno())) {
						mapmove.get(invMoveQty.getInventory().getModelno()).add(invMoveQty);
					} else {
						List<InventoryDispatchDto.InvMoveQty> list = new ArrayList<>();
						list.add(invMoveQty);
						mapmove.put(invMoveQty.getInventory().getModelno(), list);
					}
				}
			}
			for (Iterator it = set.iterator(); it.hasNext();) {
				String modelno = (String) it.next();
				AssBomDetail assBomDetail = dispatchDto.getAssBomDetail(modelno);
				assBomDetail.addExtitem();

				String dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), inputDto.getOrderId(),
						String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
						String.format("%03d", assBomDetail.getAssitem()),
						String.format("%03d", assBomDetail.getExtitem()));
				String dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), inputDto.getOrderId(),
						String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
						String.format("%03d", assBomDetail.getAssitem()),
						String.format("%03d", assBomDetail.getExtitem()));
				inputDto.setCustOrderStatusEnum(RcvOrderStatusEnum.DBING);

				CreDoByInventoryDto DBdoByInventoryDto = InitDoByInventory(inputDto, DoTypeEnum.DBCK.getType(), dbckid,
						modelno, warehouseCode, dispatchDto.getGatherhouse(), assBomDetail.getAssitem(),
						assBomDetail.getExtitem(), DoSourceEnum.ASSModelNo, DoWaitTypeEnum.OK, map.get(modelno),
						mapmove.get(modelno), dispatchDto.getInventoryMapSorted(), userDto);
				inventoryDto.getDolist().add(DBdoByInventoryDto); // 调拨出库单 （收货单和调拨出是否越库？）
				CreRoByInventoryDto DBroByInventoryDto = InitRoByInventory(inputDto, RoTypeEnum.DBRK.getType(), dbrkid,
						dispatchDto.getGatherhouse(), assBomDetail.getAssitem(), assBomDetail.getExtitem(),
						map.get(modelno), mapmove.get(modelno), userDto);
				inventoryDto.getRoList().add(DBroByInventoryDto);// 调拨入库单

			}
		}
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 13:52
	 * @description：拆分型号的调度指令生成
	 */
	private void AssModelOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException {
		BigDecimal weight = BigDecimal.ZERO;
		//CommonResult<Product> productCommonResult = productFeignApi.searchProduct(inputDto.getModelno());
		//if (Objects.nonNull(productCommonResult)) {
			// weight=productCommonResult.getData().
		//}
		// inputDto.addQtyItem();
		String doid = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), inputDto.getOrderId(),
				String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
				String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
		String pcoid = String.format(OrderIDFormatEnum.PCO_FORMAT.getFormat(), inputDto.getOrderId(),
				String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
				String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
		CreDoByInventoryDto doByInventoryDto = InitDoByInventoryBom(inputDto, DoTypeEnum.JYCK.getType(),
				dispatchDto.getAssBom().getQty(), doid, dispatchDto.getGatherhouse(), dispatchDto.getGatherhouse(),
				DoSourceEnum.ASSModelNo, DoWaitTypeEnum.WaitJG, userDto);
		inventoryDto.getDolist().add(doByInventoryDto); // 销售出库单
		CrePcoByInventoryDto pcoByInventoryDto = InitPcoByInventory(inputDto, PcoTypeEnum.PTJG.getType(), weight, pcoid,
				dispatchDto.getGatherhouse(), dispatchDto.getAssBom(), dispatchDto.getInventoryMapSorted());
		//bugid:17799 c14717 20250619
		if(Objects.nonNull(dispatchDto.getSpecialBom()) && dispatchDto.getSpecialBom()){
			pcoByInventoryDto.getOpsPco().setSpecialBomId(dispatchDto.getAssBom().getProductBom().getBomid());
		}

		DoWaitTypeEnum doWaitTypeEnum = DoWaitTypeEnum.OK;

		if (!dispatchDto.getBominvQtyMap().isEmpty()) {
			Map<String, List<InventoryDispatchDto.InvQty>> AssmapInvqty = dispatchDto.getBominvQtyMap()
					.get(dispatchDto.getAssBom().getProductBom());
			Iterator assmapIterator = AssmapInvqty.keySet().iterator();
			while (assmapIterator.hasNext()) {
				String warehouseCode = (String) assmapIterator.next();
				List<InventoryDispatchDto.InvQty> invQtyList = AssmapInvqty.get(warehouseCode);
				AssitemGatherDBOrder(inputDto, dispatchDto, warehouseCode, inventoryDto, invQtyList, null, userDto);
				if (!Objects.equals(dispatchDto.getGatherhouse(), warehouseCode)) {
					doWaitTypeEnum = DoWaitTypeEnum.WaitDB;
					inputDto.setCustOrderStatusEnum(RcvOrderStatusEnum.DBING);
				}
			}
		}
		if (!dispatchDto.getBominvMoveQtyMap().isEmpty()) {
			Map<String, List<InventoryDispatchDto.InvMoveQty>> AssmapInvqty = dispatchDto.getBominvMoveQtyMap()
					.get(dispatchDto.getAssBom().getProductBom());
			Iterator assmapIterator = AssmapInvqty.keySet().iterator();
			while (assmapIterator.hasNext()) {
				String warehouseCode = (String) assmapIterator.next();
				List<InventoryDispatchDto.InvMoveQty> invMoveQtyList = AssmapInvqty.get(warehouseCode);
				AssitemGatherDBOrder(inputDto, dispatchDto, warehouseCode, inventoryDto, null, invMoveQtyList, userDto);
				if (!Objects.equals(dispatchDto.getGatherhouse(), warehouseCode)) {
					doWaitTypeEnum = DoWaitTypeEnum.WaitDB;
					inputDto.setCustOrderStatusEnum(RcvOrderStatusEnum.DBING);
				}
			}
		}

		inventoryDto.getPcoList().add(pcoByInventoryDto);

	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 12:52
	 * @description：货齐生成调度指令规则
	 */
	@Override
	public void GatherOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException {
		if (!dispatchDto.getInvQtyMap().isEmpty()) {
			Iterator iterator = dispatchDto.getInvQtyMap().keySet().iterator();
			while (iterator.hasNext()) {
				inputDto.addQtyItem();
				String warehouseCode = (String) iterator.next();
				List<InventoryDispatchDto.InvQty> invQtyList = dispatchDto.getInvQtyMap().get(warehouseCode);
				itemGatherOrderWhole(inputDto, warehouseCode, dispatchDto, invQtyList, null, inventoryDto, userDto);
			}
		}

		if (!dispatchDto.getInvMoveQtyMap().isEmpty()) {
			Iterator iterator = dispatchDto.getInvMoveQtyMap().keySet().iterator();
			while (iterator.hasNext()) {
				inputDto.addQtyItem();
				String warehouseCode = (String) iterator.next();
				List<InventoryDispatchDto.InvMoveQty> invMoveQtyList = dispatchDto.getInvMoveQtyMap()
						.get(warehouseCode);
				itemGatherOrderWhole(inputDto, warehouseCode, dispatchDto, null, invMoveQtyList, inventoryDto, userDto);
			}
		}
		assModelOrder(inputDto, dispatchDto, inventoryDto, userDto);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 14:35
	 * @description：拆分型号单独方法
	 */
	private void assModelOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException {
		if (Objects.nonNull(dispatchDto.getAssBom())) {
			inputDto.addQtyItem();//整型号
			AssModelOrder(inputDto, dispatchDto, inventoryDto, userDto);
		}
	}

	@Override
	public void CreateCGDo(InventoryCkByOrderInputDto inputDto, WmOrderByInventoryDto inventoryDto)
			throws OpsException {
		String doid = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), inputDto.getOrderId(),
				String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
				String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
		int qty = inputDto.getQuantity() - inputDto.getAllotQuantity();
		OpsDoItem opsDoItem = initOpsDoItem(inputDto, doid, inputDto.getModelno(), inputDto.getUserDto());
		opsDoItem.setQty(qty);
		DoSourceEnum doSourceEnum = DoSourceEnum.ASSQTY;
		if (0 == inputDto.getAllotQuantity()) {
			doSourceEnum = DoSourceEnum.CG;
		}

		OpsDo opsDo = initOpsDo(inputDto, DoTypeEnum.JYCK.getType(), doid, inputDto.getWarehouseCode(),
				inputDto.getWarehouseCode(), inputDto.getAssItem(), 0, doSourceEnum, DoWaitTypeEnum.WaitCG,
				inputDto.getUserDto());
		CreDoByInventoryDto doByInventoryDto = new CreDoByInventoryDto(opsDo, opsDoItem, null);
		inventoryDto.getDolist().add(doByInventoryDto); // 销售出库单
		inputDto.setCustOrderStatusEnum(RcvOrderStatusEnum.CGING);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 12:52
	 * @description：不做集约发货,生成调度指令
	 */
	@Override
	public void UngatherOrder(InventoryCkByOrderInputDto inputDto, InventoryDispatchDto dispatchDto,
			WmOrderByInventoryDto inventoryDto, UserDto userDto) throws OpsException {

		if (!dispatchDto.getInvQtyMap().isEmpty()) {
			Iterator iterator = dispatchDto.getInvQtyMap().keySet().iterator();
			while (iterator.hasNext()) {
				inputDto.addQtyItem();
				String warehouseCode = (String) iterator.next();
				List<InventoryDispatchDto.InvQty> invQtyList = dispatchDto.getInvQtyMap().get(warehouseCode);

				String doid = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), inputDto.getOrderId(),
						String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
						String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
				CreDoByInventoryDto doByInventoryDto = InitDoByInventory(inputDto, DoTypeEnum.JYCK.getType(), doid,
						inputDto.getModelno(), warehouseCode, warehouseCode, inputDto.getAssItem(), 0, dispatchDto.getDoSourceEnum(),
						DoWaitTypeEnum.OK, invQtyList, null, dispatchDto.getInventoryMapSorted(), userDto);
				inventoryDto.getDolist().add(doByInventoryDto); // 销售出库单
			}
		}

		if (!dispatchDto.getInvMoveQtyMap().isEmpty()) {
			Iterator iterator = dispatchDto.getInvMoveQtyMap().keySet().iterator();
			while (iterator.hasNext()) {
				inputDto.addQtyItem();
				String warehouseCode = (String) iterator.next();
				List<InventoryDispatchDto.InvMoveQty> invQtyList = dispatchDto.getInvMoveQtyMap().get(warehouseCode);
				String doid = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), inputDto.getOrderId(),
						String.format("%03d", Integer.parseInt(inputDto.getOrderItem())),
						String.format("%03d", inputDto.getQtyItem()), String.format("%03d", inputDto.getAssItem()));
				CreDoByInventoryDto doByInventoryDto = InitDoByInventory(inputDto, DoTypeEnum.JYCK.getType(), doid,
						inputDto.getModelno(), warehouseCode, warehouseCode, inputDto.getAssItem(), 0, dispatchDto.getDoSourceEnum(),
						DoWaitTypeEnum.OK, null, invQtyList, dispatchDto.getInventoryMapSorted(), userDto);
				inventoryDto.getDolist().add(doByInventoryDto); // 销售出库单
			}
		}
		assModelOrder(inputDto, dispatchDto, inventoryDto, userDto);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/1 17:57
	 * @description：依据库存信息，创建发货单
	 */
	private CreDoByInventoryDto InitDoByInventory(InventoryCkByOrderInputDto inputDto, String doType, String doid,
			String modelno, String warehouseCode, String gatherhouse, int assItem, int extraItem,
			DoSourceEnum doSourceEnum, DoWaitTypeEnum doWaitTypeEnum, List<InventoryDispatchDto.InvQty> invQtyList,
			List<InventoryDispatchDto.InvMoveQty> invMoveQtyList, Map<Object, Integer> inventoryMapSorted,
			UserDto userDto) throws OpsException {

		OpsDoItem opsDoItem = initOpsDoItem(inputDto, doid, modelno, userDto);
		List<OpsDoItemInventory> doItemInventoryList = initOpsDoItemInventorys(doid, invQtyList, opsDoItem,
				invMoveQtyList, inventoryMapSorted, userDto);
		OpsDo opsDo = initOpsDo(inputDto, doType, doid, warehouseCode, gatherhouse, assItem, extraItem, doSourceEnum,
				doWaitTypeEnum, userDto);
		return new CreDoByInventoryDto(opsDo, opsDoItem, doItemInventoryList);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 16:27
	 * @description：加工对应销售单生成
	 */
	private CreDoByInventoryDto InitDoByInventoryBom(InventoryCkByOrderInputDto inputDto, String doType, Integer qty,
			String doid, String warehouseCode, String gatherhouse, DoSourceEnum doSourceEnum,
			DoWaitTypeEnum doWaitTypeEnum, UserDto userDto) throws OpsException {

		OpsDoItem opsDoItem = initOpsDoItem(inputDto, doid, inputDto.getModelno(), userDto);
		opsDoItem.setQty(qty);
		OpsDo opsDo = initOpsDo(inputDto, doType, doid, warehouseCode, gatherhouse, inputDto.getAssItem(), 0,
				doSourceEnum, doWaitTypeEnum, userDto);
		return new CreDoByInventoryDto(opsDo, opsDoItem, null);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 14:16
	 * @description：依据库存信息，创建收货单
	 */
	private CreRoByInventoryDto InitRoByInventory(InventoryCkByOrderInputDto inputDto, String roType, String roid,
			String gatherhouse, int assItem, int extraItem, List<InventoryDispatchDto.InvQty> invQtyList,
			List<InventoryDispatchDto.InvMoveQty> invMoveQtyList, UserDto userDto) {

		OpsRo opsRo = initOpsRo(inputDto, roType, roid, assItem, extraItem, gatherhouse, userDto);

		//List<OpsRoItem> roItemList = initOpsRoItem(roid, invQtyList, invMoveQtyList, userDto);
        List<OpsRoItem> roItemList = initOpsRoItemNew(inputDto,roid, invQtyList, invMoveQtyList, userDto);//BUGID:8568 c14717 20221105

        return new CreRoByInventoryDto(opsRo, roItemList, null);
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 19:15
	 * @description：依据库存信息，创建加工单
	 */
	@Override
	public CrePcoByInventoryDto InitPcoByInventory(InventoryCkByOrderInputDto inputDto, String doType,
			BigDecimal weight, String pcoid, String gatherhouse, AssBom assBom,
			Map<Object, Integer> inventoryMapSorted) {
		OpsPco opsPco = initOpsPco(inputDto, doType, assBom, weight, pcoid, gatherhouse);
		List<OpsPcoItem> opsPcoItemList = new ArrayList<>();
		List<OpsPcoItemInventory> itemInventorys = new ArrayList<>();
		initOpsPcoItemList(pcoid, assBom, gatherhouse, inputDto.getUserDto(), opsPcoItemList, itemInventorys,
				inventoryMapSorted);
		return new CrePcoByInventoryDto(opsPco, opsPcoItemList, itemInventorys);
	}

	private OpsPcoItemInventory initOpsPcoItemInventory(String pcoId, int pcoItem, Integer useQty, Integer sortnum,
			Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, String creator) {
		OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
		pcoItemInventory.setPcoId(pcoId);
		pcoItemInventory.setPcoItem(pcoItem);
		pcoItemInventory.setPcoType(1);
		pcoItemInventory.setUseQty(useQty);
		pcoItemInventory.setInventoryId(inventoryId);
		pcoItemInventory.setInventoryTableType(inventoryTableTypeEnum.getType());
		pcoItemInventory.setVersion(0L);
		pcoItemInventory.setDelflag(0);
		pcoItemInventory.setCreTime(new Date());
		pcoItemInventory.setCreator(creator);
		pcoItemInventory.setSortnum(sortnum);
		return pcoItemInventory;
	}

	private OpsPcoItem initOpsPcoItem(String pcoId, int item, String subModelno, Integer subQty, String creator) {
		OpsPcoItem opsPcoItem = new OpsPcoItem();
		opsPcoItem.setPcoId(pcoId);
		opsPcoItem.setPcoItem(item);
		opsPcoItem.setSubModelno(subModelno);
		opsPcoItem.setSubQty(subQty);
		opsPcoItem.setWaitType(DoWaitTypeEnum.OK.getType());
		opsPcoItem.setDelflag(0);
		opsPcoItem.setCreator(creator);
		opsPcoItem.setCreTime(new Date());
		opsPcoItem.setWeight(null);//// TODO: 重量
		return opsPcoItem;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 18:08
	 * @description：加工指令明细
	 */
	private void initOpsPcoItemList(String pcoId, AssBom assBom, String gatherhouse, UserDto userDto,
			List<OpsPcoItem> opsPcoItemList, List<OpsPcoItemInventory> itemInventorys,
			Map<Object, Integer> inventoryMapSorted) {

		int item = 0;
		if (CollectionUtils.isNotEmpty(assBom.getDetailList())) {
			for (AssBomDetail bomDetail : assBom.getDetailList()) {
				OpsPcoItem opsPcoItem = initOpsPcoItem(pcoId, ++item, bomDetail.getProductBomDetail().getModelno(),
						bomDetail.getAssQty(), userDto.getUserName());
				bomDetail.setAssitem(item);
				opsPcoItemList.add(opsPcoItem);
				if (bomDetail.getMapMoveqty() != null) {
					for (Map.Entry<OpsInventoryMove, Integer> entry : bomDetail.getMapMoveqty().entrySet()) {
						if (gatherhouse.equals(entry.getKey().getWarehouseCode())) {
							OpsPcoItemInventory pcoItemInventory = initOpsPcoItemInventory(pcoId, item,
									entry.getValue(), inventoryMapSorted.get(entry.getKey()),
									entry.getKey().getInventoryId(), InventoryTableTypeEnum.TRANS,
									userDto.getUserName());
							itemInventorys.add(pcoItemInventory);
						} else {
							opsPcoItem.setWaitType(DoWaitTypeEnum.WaitDB.getType());
						}
					}
					for (Map.Entry<OpsInventory, Integer> entry : bomDetail.getMapqty().entrySet()) {
						if (gatherhouse.equals(entry.getKey().getWarehouseCode())) {
							OpsPcoItemInventory pcoItemInventory = initOpsPcoItemInventory(pcoId, item,
									entry.getValue(), inventoryMapSorted.get(entry.getKey()),
									entry.getKey().getInventoryId(), InventoryTableTypeEnum.NORMAL,
									userDto.getUserName());
							itemInventorys.add(pcoItemInventory);
						} else {
							opsPcoItem.setWaitType(DoWaitTypeEnum.WaitDB.getType());
						}
					}
				}

				if (bomDetail.getAssQty() > bomDetail.getAssAllotQty()) {
					opsPcoItem.setWaitType(DoWaitTypeEnum.WaitCG.getType());
				}

			}

		}
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 17:29
	 * @description：加工单
	 */
	private OpsPco initOpsPco(InventoryCkByOrderInputDto inputDto, String PcoType, AssBom assBom, BigDecimal weight,
			String pcoid, String gatherhouse) {
		OpsPco opsPco = new OpsPco();
		opsPco.setPcoId(pcoid);
		opsPco.setPcoSource(0);
		opsPco.setOrderId(inputDto.getOrderId());
		opsPco.setOrderItem(inputDto.getOrderItem());
		opsPco.setNum(inputDto.getQtyItem());
		opsPco.setAssNum(inputDto.getAssItem());
		opsPco.setHopeDate(inputDto.getHopeDate());
        opsPco.setWlDate(inputDto.getWlDate());
        opsPco.setSpceialFlag(inputDto.getSpceialFlag());
		opsPco.setPcoType(1);// todo 拆分
		opsPco.setProdType(PcoType);// todo 生成标识：1.同捆2.组立3.装配生成
		opsPco.setWarehouseCode(gatherhouse);
		opsPco.setPcoStatus(PcoStatusEnum.INIT.getStatus());// todo 加工标识(0初始状态（未占库存） 1等待加工、2加工中、3加工完成)
		opsPco.setRemark("");// todo 加工备注
		opsPco.setDelflag(0);
		opsPco.setCreator(inputDto.getUserDto().getUserName());
		opsPco.setCreTime(new Date());
		opsPco.setModelNo(inputDto.getModelno());
		opsPco.setQty(assBom.getQty());
		opsPco.setCustomerNo(inputDto.getCustomer());
		opsPco.setWeight(weight);
		opsPco.setOrderId(inputDto.getOrderId());
		opsPco.setBomid(assBom.getProductBom().getBomid());
		return opsPco;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/1 15:11
	 * @description：订单分配初始化数据
	 */
	private OpsDo initOpsDo(InventoryCkByOrderInputDto inputDto, String doType, String doid, String warehouseCode,
			String gatherhouse, int assItem, int extraItem, DoSourceEnum doSourceEnum, DoWaitTypeEnum doWaitTypeEnum,
			UserDto userDto ) {
		OpsDo opsDo = new OpsDo();
		opsDo.setDoId(doid);
		//发货邮箱 bugid:12391 c14717 2023/10/27
		opsDo.setEmail(inputDto.getEmail());
		opsDo.setOrderId(inputDto.getOrderId());
		opsDo.setOrderItem(inputDto.getOrderItem());
		opsDo.setNum(inputDto.getQtyItem());
		opsDo.setAssNum(assItem);
		opsDo.setExtNum(extraItem);
		opsDo.setDoSource(doSourceEnum.getType());
		opsDo.setDoType(doType);
		opsDo.setWarehouseCode(warehouseCode);
		opsDo.setGatherWarehouseCode(gatherhouse);
		opsDo.setCustomerNo(inputDto.getCustomer());
		opsDo.setSpceialFlag(inputDto.getSpceialFlag());
		opsDo.setPakageType(inputDto.getPakageType());
		opsDo.setCorderNo(inputDto.getCorderNo());
		opsDo.setDoStatus(0);// 初始
		opsDo.setRemark(inputDto.getRemark1()); // 指定备注内容
		opsDo.setVersion(0);
		opsDo.setDelflag(0);
		opsDo.setCreator(userDto.getUserName());
		opsDo.setCreTime(new Date());
		opsDo.setWaitType(doWaitTypeEnum.getType());
		opsDo.setHopeDate(inputDto.getHopeDate());
		opsDo.setWlDate(inputDto.getWlDate());

		if (DoTypeEnum.DBCK.getType().equals(doType)) {
			OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(gatherhouse);
			//CommonResult<OpsWarehouse> result = opsWarehouseFeignApi.searchWarehouse(gatherhouse); redis
			//OpsWarehouse opsWarehouse = result.getData();
			//bugid:11445 c14717 2023/07/26 仓库多地址
			opsWarehouseService.getMultAdressSetAddress(opsWarehouse,opsDo.getDoType());
			opsDo.setProvince(opsWarehouse.getProvince());
			opsDo.setCity(opsWarehouse.getCity());
			opsDo.setDistrict(opsWarehouse.getDistrict());
			//opsDo.setStreet(opsWarehouse.getAdress());
			opsDo.setAddress(opsWarehouse.getAdress());
			opsDo.setLinkman(opsWarehouse.getLinkman());
			if (!StringUtils.isEmpty(opsWarehouse.getLinkMobile())) {//手机号
				if (StringPhoneUtil.isMobil(opsWarehouse.getLinkMobile())) {
					opsDo.setMobile(opsWarehouse.getLinkMobile());
				}
			}
			if (!StringUtils.isEmpty(opsWarehouse.getLinkPhone())) {
				//是电话 且不是手机号 存电话
				if (StringPhoneUtil.isPhone(opsWarehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(opsWarehouse.getLinkPhone())) {
					opsDo.setPhone(opsWarehouse.getLinkPhone());
				}
			}
			opsDo.setPostcode(opsWarehouse.getPostCode() + "");
		} else {
			if("3".equals(inputDto.getDlvSite())){//判断自提写仓库地址，门户确认不会改为不自提20220713开会确认
				OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(gatherhouse);
				//TODO 自提 bugid:11445 c14717 2023/07/26 仓库多地址
				//opsWarehouseService.getMultAdressSetAddress(opsWarehouse,"ZT");
				//CommonResult<OpsWarehouse> result = opsWarehouseFeignApi.searchWarehouse(gatherhouse); redis
				//OpsWarehouse opsWarehouse = result.getData();
				opsDo.setProvince(opsWarehouse.getProvince());
				opsDo.setCity(opsWarehouse.getCity());
				opsDo.setDistrict(opsWarehouse.getDistrict());
				//opsDo.setStreet(opsWarehouse.getAdress());
				opsDo.setAddress(opsWarehouse.getAdress());
				opsDo.setPostcode(opsWarehouse.getPostCode() + "");
			}else{
				opsDo.setProvince(inputDto.getProvince());
				opsDo.setCity(inputDto.getCity());
				opsDo.setDistrict(inputDto.getDistrict());
				//opsDo.setStreet(inputDto.getStreet());
				opsDo.setAddress(inputDto.getAddress());
				opsDo.setPostcode(inputDto.getPostcode());
			}

			opsDo.setMobile(inputDto.getMobile());
			opsDo.setLinkman(inputDto.getLinkman());
			opsDo.setPhone(inputDto.getPhone());
			opsDo.setCarried(inputDto.getCarried());// 指定承运商
		}
		opsDo.setDlvSite(inputDto.getDlvSite());// 是否直发
		opsDo.setDeptNo(inputDto.getDeptno());// 营业所代码
		opsDo.setExpDlvType(inputDto.getExpDlvType());
		opsDo.setExpLinkNo(inputDto.getExpLinkNo());
		opsDo.setDlvEntire(inputDto.getCKType().getCode());
		opsDo.setUserNo(StringUtils.isEmpty(inputDto.getUserNo()) ? inputDto.getCustomer() : inputDto.getUserNo());// 专备客户代码
		return opsDo;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/1 15:11
	 * @description：初始化数据
	 */
	private OpsDoItem initOpsDoItem(InventoryCkByOrderInputDto inputDto, String doid, String modelno, UserDto userDto) {
		OpsDoItem opsDoItem = new OpsDoItem();
		opsDoItem.setDoId(doid);
		opsDoItem.setDoItem(1);
		opsDoItem.setModelno(modelno);
		opsDoItem.setProductName(inputDto.getProductName());
		opsDoItem.setCproductNo(inputDto.getCproductNo());//客户产品名称
		opsDoItem.setContractNo(inputDto.getContractNo());//客户产品合同号
		opsDoItem.setPrice(inputDto.getPrice());
		if(OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.ROSH10Product)){//环保标识 rosh wms要H
			opsDoItem.setGreenCode("H");
		}else {
			//opsDoItem.setGreenCode("N");
		}
		opsDoItem.setQty(0);
		opsDoItem.setOutQty(0);
		opsDoItem.setVersion(0);
		opsDoItem.setDelflag(0);
		opsDoItem.setCreator(userDto.getUserName());
		opsDoItem.setModifier(userDto.getUserName());
		opsDoItem.setCreTime(new Date());
		opsDoItem.setModifyTime(new Date());
		return opsDoItem;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/1 15:11
	 * @description：初始化数据
	 */
	private List<OpsDoItemInventory> initOpsDoItemInventorys(String doid, List<InventoryDispatchDto.InvQty> invQtyList,
			OpsDoItem opsDoItem, List<InventoryDispatchDto.InvMoveQty> invMoveQtyList,
			Map<Object, Integer> inventoryMapSorted, UserDto userDto) {
		List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(invQtyList)) {
			for (InventoryDispatchDto.InvQty inqty : invQtyList) {
				int useqty = inqty.getUseqty();
				OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
				opsDoItemInventory.setDoId(doid);
				opsDoItemInventory.setDoItem(1);
				opsDoItemInventory.setInventoryId(inqty.getInventory().getInventoryId());
				opsDoItemInventory.setInventoryTableType("N");
				opsDoItemInventory.setUseQty(useqty);
				opsDoItemInventory.setVersion(0L);
				opsDoItemInventory.setDelflag(0);
				opsDoItemInventory.setCreTime(new Date());
				opsDoItemInventory.setCreator(userDto.getUserName());
				opsDoItemInventory.setSortnum(inventoryMapSorted.get(inqty.getInventory()));
				doItemInventoryList.add(opsDoItemInventory);
				opsDoItem.setQty(opsDoItem.getQty() + useqty);
			}
		}
		if (CollectionUtils.isNotEmpty(invMoveQtyList)) {
			for (InventoryDispatchDto.InvMoveQty invMoveQty : invMoveQtyList) {
				int useqty = invMoveQty.getUseqty();
				OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();

				opsDoItemInventory.setDoId(doid);
				opsDoItemInventory.setDoItem(1);
				opsDoItemInventory.setInventoryId(invMoveQty.getInventory().getInventoryId());
				opsDoItemInventory.setInventoryTableType("T");
				opsDoItemInventory.setUseQty(useqty);
				opsDoItemInventory.setVersion(0L);
				opsDoItemInventory.setDelflag(0);
				opsDoItemInventory.setCreTime(new Date());
				opsDoItemInventory.setCreator(userDto.getUserName());
				opsDoItemInventory.setSortnum(inventoryMapSorted.get(invMoveQty.getInventory()));
				doItemInventoryList.add(opsDoItemInventory);
				opsDoItem.setQty(opsDoItem.getQty() + useqty);
			}
		}
		return doItemInventoryList;
	}

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/2 14:07
	 * @description：初始化收货数据
	 */
	private OpsRo initOpsRo(InventoryCkByOrderInputDto inputDto, String roType, String roid, int assItem, int extraItem,
			String gatherhouse, UserDto userDto) {
		OpsRo opsRo = new OpsRo();
		opsRo.setRoId(roid);
		opsRo.setRoSource("");// todo 来源待定
		opsRo.setOrderId(inputDto.getOrderId());
		opsRo.setOrderItem(inputDto.getOrderItem());
		opsRo.setNum(inputDto.getQtyItem());
		opsRo.setAssNum(assItem);
		opsRo.setExtNum(extraItem);
		opsRo.setRoType(roType);
		opsRo.setWarehouseCode(gatherhouse);
		opsRo.setRoStatus(0);// 初始
		opsRo.setTransType("");// 运输方式
		opsRo.setCarried("");// 到货承运商
		opsRo.setExpressCode("");// 到货承运商
		opsRo.setCustomerNo(inputDto.getCustomer());
		opsRo.setInvoiceNo("");// 到货发票号
		opsRo.setSupplierId("");// 到货供应商
		opsRo.setDelflag(0);
		opsRo.setCreator(userDto.getUserName());
		opsRo.setCreTime(new Date());
		return opsRo;
	}


	/**
	 * @author ：c14717 roItem
	 * @date ：Created in 2022/11/4 17:25
	 * @description：初始化收货数据
	 */
	private List<OpsRoItem> initOpsRoItemNew(InventoryCkByOrderInputDto inputDto,String roid, List<InventoryDispatchDto.InvQty> invQtyList,
										  List<InventoryDispatchDto.InvMoveQty> invMoveQtyList, UserDto userDto) {
		List<OpsRoItem> roItemList = new ArrayList<>();
		OpsRoItem roItem = new OpsRoItem();
		roItem.setRoId(roid);
		//bugid:8804 c14717 20221130 调拨入需要加入环保标识
		if(OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.ROSH10Product)){//环保标识 rosh wms要H
			roItem.setGreenCode("H");
		}
		roItem.setRoItem(1);
		roItem.setQty(0);
		roItem.setRecQty(0);
		roItem.setVersion(0);
		roItem.setDelflag(0);
		roItem.setCreator(userDto.getUserName());
		roItem.setCreTime(new Date());
		if (CollectionUtils.isNotEmpty(invQtyList)) {
			for (InventoryDispatchDto.InvQty inqty : invQtyList) {
				int useqty = inqty.getUseqty();
				roItem.setQty(roItem.getQty() + useqty);
				roItem.setModelno(inqty.getInventory().getModelno());
			}
		}
		if (CollectionUtils.isNotEmpty(invMoveQtyList)) {
			for (InventoryDispatchDto.InvMoveQty inqty : invMoveQtyList) {
				int useqty = inqty.getUseqty();
				roItem.setQty(roItem.getQty() + useqty);
				roItem.setModelno(inqty.getInventory().getModelno());
			}
		}
		roItemList.add(roItem);
		return roItemList;
	}
}

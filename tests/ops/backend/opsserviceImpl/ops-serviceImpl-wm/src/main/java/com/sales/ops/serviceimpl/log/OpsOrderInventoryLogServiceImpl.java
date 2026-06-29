package com.sales.ops.serviceimpl.log;

import java.util.*;

import com.sales.ops.db.batchdao.AddBatchOrderInventoryLogDao;
import com.sales.ops.db.entity.OpsWmOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.ops.db.dao.OpsOrderInventoryLogMapper;
import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsOrderInventoryLog;
import com.sales.ops.dto.inventory.AssBomDetail;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryCkByOrderOutDto;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.inventory.OpsOrderInventoryLogService;

/**
 * @author C02483
 * @version 1.0
 * @description: 单据原始库存记录里（测试用）
 * @date 2022/1/25 16:43
 */
@Service
public class OpsOrderInventoryLogServiceImpl implements OpsOrderInventoryLogService {

	@Autowired
	private OpsOrderInventoryLogMapper orderInventoryLogMapper;

	@Autowired
	private AddBatchOrderInventoryLogDao addBatchOrderInventoryLogDao;

	@Override
	public void addOrderInventoryLog(InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto) {

		List<OpsOrderInventoryLog> logList = new ArrayList<OpsOrderInventoryLog>();
		if (!inputDto.isAllotStatus()) {
			OpsOrderInventoryLog log = new OpsOrderInventoryLog();
			log.setOrderQty(inputDto.getQuantity());
			log.setCreTime(new Date());
			log.setOrderId(inputDto.getOrderId());
			log.setOrderItem(inputDto.getOrderItem());
			log.setQty(inputDto.getQuantity() - inputDto.getAllotQuantity());
			logList.add(log);
			//orderInventoryLogMapper.insertSelective(log);
		}

		for (Map.Entry<OpsInventory, Integer> entry : outDto.getInventoryMap().entrySet()) {
			OpsOrderInventoryLog log = new OpsOrderInventoryLog();
			log.setInventoryId(entry.getKey().getInventoryId());
			log.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
			log.setCreTime(new Date());
			log.setOrderId(inputDto.getOrderId());
			log.setOrderItem(inputDto.getOrderItem());
			log.setQty(entry.getValue());
			log.setOrderQty(inputDto.getQuantity());
			logList.add(log);
			//orderInventoryLogMapper.insertSelective(log);
		}
		for (Map.Entry<OpsInventoryMove, Integer> entry : outDto.getInventoryMoveMap().entrySet()) {
			OpsOrderInventoryLog log = new OpsOrderInventoryLog();
			log.setInventoryId(entry.getKey().getInventoryId());
			log.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
			log.setCreTime(new Date());
			log.setOrderId(inputDto.getOrderId());
			log.setOrderItem(inputDto.getOrderItem());
			log.setQty(entry.getValue());
			log.setOrderQty(inputDto.getQuantity());
			logList.add(log);
			//orderInventoryLogMapper.insertSelective(log);
		}
		if (Objects.nonNull(outDto.getAssBom())) {
			for (AssBomDetail assBomDetail : outDto.getAssBom().getDetailList()) {
				if (Objects.nonNull(assBomDetail.getMapqty())) {
					for (Map.Entry<OpsInventory, Integer> entry : assBomDetail.getMapqty().entrySet()) {
						OpsOrderInventoryLog log = new OpsOrderInventoryLog();
						log.setInventoryId(entry.getKey().getInventoryId());
						log.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
						log.setCreTime(new Date());
						log.setOrderId(inputDto.getOrderId());
						log.setOrderItem(inputDto.getOrderItem());
						log.setQty(entry.getValue());
						log.setOrderQty(assBomDetail.getAssQty());
						logList.add(log);
						//orderInventoryLogMapper.insertSelective(log);
					}
					for (Map.Entry<OpsInventoryMove, Integer> entry : assBomDetail.getMapMoveqty().entrySet()) {
						OpsOrderInventoryLog log = new OpsOrderInventoryLog();
						log.setInventoryId(entry.getKey().getInventoryId());
						log.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
						log.setCreTime(new Date());
						log.setOrderId(inputDto.getOrderId());
						log.setOrderItem(inputDto.getOrderItem());
						log.setQty(entry.getValue());
						log.setOrderQty(assBomDetail.getAssQty());
						logList.add(log);
						//orderInventoryLogMapper.insertSelective(log);
					}
					if (assBomDetail.getAssQty() > assBomDetail.getAssAllotQty()) {
						OpsOrderInventoryLog log = new OpsOrderInventoryLog();
						log.setCreTime(new Date());
						log.setOrderId(inputDto.getOrderId());
						log.setOrderItem(inputDto.getOrderItem());
						log.setModelno(assBomDetail.getProductBomDetail().getModelno());
						log.setQty(assBomDetail.getAssQty() - assBomDetail.getAssAllotQty());
						log.setOrderQty(assBomDetail.getAssQty());
						logList.add(log);
						//orderInventoryLogMapper.insertSelective(log);
					}
				}
			}
		}
		//批量插入
		int CAL_SIZE = 262;
		for (int i = 0; i < logList.size(); i++) {
			if (i % CAL_SIZE == 0) {
				List<OpsOrderInventoryLog> temp = new ArrayList<OpsOrderInventoryLog>();
				if (i + CAL_SIZE < logList.size()) {
					temp = logList.subList(i, i + CAL_SIZE);
				} else {
					temp = logList.subList(i, logList.size());
				}
				addBatchOrderInventoryLogDao.batchInsertOrderBatchOrderInventoryLog(temp);
			}
		}

	}

}

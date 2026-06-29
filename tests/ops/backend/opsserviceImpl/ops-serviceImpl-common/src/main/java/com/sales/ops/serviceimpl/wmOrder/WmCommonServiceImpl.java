package com.sales.ops.serviceimpl.wmOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.RedisMQManager;
import com.sales.ops.db.dao.OpsPoWarehouseDeliverydayMapper;
import com.sales.ops.db.dao.OpsWarehouseMapper;
import com.sales.ops.db.dao.OpsWarehouseSalesbranchConfigMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.wm.WmCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author B28029
 * @date 2023/1/10 10:24
 */
@Slf4j
@Service
public class WmCommonServiceImpl implements WmCommonService {

	@Resource
	private OpsWarehouseMapper opsWarehouseMapper;

	@Resource
	private RedisMQManager redisUtil;
	@Resource
	private OpsWarehouseSalesbranchConfigMapper opsWarehouseSalesbranchConfigMapper;
	@Resource
	private OpsPoWarehouseDeliverydayMapper opsPoWarehouseDeliverydayMapper;

	/**
	 * 仓库信息
	 *
	 * @param warehouseCode
	 * @return
	 */
	@Override
	public ResultVo<OpsWarehouse> getWarehouseByCode(String warehouseCode) {
		if (StringUtils.isBlank(warehouseCode)) {
			return ResultVo.failure("仓库代码不可为空.");
		}
		Object warehouse = redisUtil.hGet(Constants.REDIS_WAREHOUSE_BY_CODE, warehouseCode);
		if (warehouse != null) {
			// 返回REDIS值
			return ResultVo.success(JSONObject.parseObject(warehouse.toString(), OpsWarehouse.class));
		}
		OpsWarehouseExample opsWarehouseExample = new OpsWarehouseExample();
		OpsWarehouseExample.Criteria criteria = opsWarehouseExample.createCriteria();
		criteria.andWarehouseCodeEqualTo(warehouseCode);
		criteria.andDelflagEqualTo(false);// 删除标记
		List<OpsWarehouse> opsWarehouseList = opsWarehouseMapper.selectByExample(opsWarehouseExample);
		if (opsWarehouseList.size() == 0) {
			return ResultVo.failure("仓库代码不可为空.");
		}
		if (opsWarehouseList.size() > 1) {
			return ResultVo.failure("仓库多行记录,请确认.");
		}
		redisUtil.hPut(Constants.REDIS_WAREHOUSE_BY_CODE, warehouseCode, JSON.toJSONString(opsWarehouseList.get(0)));
		redisUtil.expire(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, 604800);// 存7天
		return ResultVo.success(opsWarehouseList.get(0));
	}

	@Override
	public void refreshWarehouseCache(String warehouseCode) {
		OpsWarehouseExample opsWarehouseExample = new OpsWarehouseExample();
		OpsWarehouseExample.Criteria criteria = opsWarehouseExample.createCriteria();
		criteria.andWarehouseCodeEqualTo(warehouseCode);
		criteria.andDelflagEqualTo(false);// 删除标记
		List<OpsWarehouse> opsWarehouseList = opsWarehouseMapper.selectByExample(opsWarehouseExample);
		if (opsWarehouseList.size() == 1) {
			redisUtil.hPut(Constants.REDIS_WAREHOUSE_BY_CODE, warehouseCode, JSON.toJSONString(opsWarehouseList.get(0)));
			redisUtil.expire(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, 604800);// 存7天
		} else {
			redisUtil.hdel(Constants.REDIS_WAREHOUSE_BY_CODE, warehouseCode);
		}
	}


	/**
	 * 仓库类别 MASTER 物流中心、SUB分库、WT委托在库 取redis值
	 *
	 * @param warehouseCode
	 * @return
	 */
	@Override
	public ResultVo<String> getWarehouseTypeByCode(String warehouseCode) {
		if (StringUtils.isBlank(warehouseCode)) {
			return ResultVo.failure("仓库代码不可为空.");
		}
		Object warehouse = redisUtil.hGet(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode);
		if (warehouse != null) {
			// 返回REDIS值
			return ResultVo.success(warehouse.toString());
		}
		OpsWarehouseExample opsWarehouseExample = new OpsWarehouseExample();
		OpsWarehouseExample.Criteria criteria = opsWarehouseExample.createCriteria();
		criteria.andWarehouseCodeEqualTo(warehouseCode);
		criteria.andDelflagEqualTo(false);// 删除标记
		List<OpsWarehouse> opsWarehouseList = opsWarehouseMapper.selectByExample(opsWarehouseExample);
		if (opsWarehouseList.size() == 0) {
			return ResultVo.failure("无仓库信息.仓库号:" + warehouseCode);
		}
		if (opsWarehouseList.size() > 1) {
			return ResultVo.failure("错误,存在仓库多行记录");
		}
		redisUtil.hPut(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode,
				opsWarehouseList.get(0).getWarehouseType());
		redisUtil.expire(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, 604800);// 存7天
		return ResultVo.success(opsWarehouseList.get(0).getWarehouseType());
	}

	/**
	 * bug10347 马腾 获取可采购仓库的基础信息
	 */
	@Override
	public ResultVo<List<OpsWarehouse>> getWarehouseCanPurchase() {
		OpsWarehouseExample opsWarehouseExample = new OpsWarehouseExample();
		opsWarehouseExample.createCriteria().andPurchaseFlagEqualTo(true).andDelflagEqualTo(false);
		List<OpsWarehouse> opsWarehouseList = opsWarehouseMapper.selectByExample(opsWarehouseExample);
		if (opsWarehouseList.size() == 0) {
			return ResultVo.failure("无可采购仓库。");
		}
		return ResultVo.success(opsWarehouseList);
	}

	/**
	 * bugid:10345 20230410 c14717 获取国内运输时间
	 * 
	 * @param branchId
	 *            营业所代码
	 * @param warehouseCode
	 *            仓库号
	 * @return
	 */
	@Override
	public CommonResult<Integer> getWarehouseSalesbranchConfigByCode(String branchId, String warehouseCode) {
		if (StringUtils.isBlank(branchId) && StringUtils.isBlank(warehouseCode)) {
			return CommonResult.failure("参数不可为空.");
		}
		// 当数据中也无法取得时，设定默认值国内运输时间：7
		Integer deliveryDay = 7;
		StringBuilder param = new StringBuilder();
		param.append(branchId);
		param.append("_");
		param.append(warehouseCode);
		Object redisDeliveryDay = redisUtil
				.hGet(Constants.REDIS_WAREHOUSE_SALESBRANCH_CONFIG_BY_BRANCHID_WAREHOUSE_CODE, param.toString());
		if (Objects.nonNull(redisDeliveryDay)) {
			// 返回REDIS值
			return CommonResult.success((int) (redisDeliveryDay));
		}
		OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
		example.createCriteria().andDelflagEqualTo(0).andSalesBranchIdEqualTo(branchId)
				.andWarehouseCodeEqualTo(warehouseCode);
		List<OpsWarehouseSalesbranchConfig> resultList = opsWarehouseSalesbranchConfigMapper.selectByExample(example);
		if (resultList.size() == 1) {
			deliveryDay = resultList.get(0).getDeliveryDay();
		}
		if (resultList.size() > 1) {
			return CommonResult.failure("错误,存在多行记录:" + param.toString());
		}
		redisUtil.hPut(Constants.REDIS_WAREHOUSE_SALESBRANCH_CONFIG_BY_BRANCHID_WAREHOUSE_CODE, param.toString(),
				deliveryDay);
		redisUtil.expire(Constants.REDIS_WAREHOUSE_SALESBRANCH_CONFIG_BY_BRANCHID_WAREHOUSE_CODE, 604800);// 存7天
		return CommonResult.success(deliveryDay);
	}

	/**
	 * bugid:10345 20230410 c14717 获取仓间调拨运输时间
	 * 
	 * @param fromWarehouseCode
	 * @param toWarehouseCode
	 * @return
	 */
	@Override
	public CommonResult<Integer> getWarehouseDeliveryDayByCode(String fromWarehouseCode, String toWarehouseCode) {
		if (StringUtils.isBlank(fromWarehouseCode) && StringUtils.isBlank(toWarehouseCode)) {
			return CommonResult.failure("参数不可为空.");
		}
		// 当数据中也无法取得时，设定默认值仓间运输时间：4
		Integer deliveryDay = 4;
		StringBuilder param = new StringBuilder();
		param.append(fromWarehouseCode);
		param.append("_");
		param.append(toWarehouseCode);
		Object redisDeliveryDay = redisUtil.hGet(Constants.REDIS_WAREHOUSE_DELIVERDAY_BY_WAREHOUSE_CODE,
				param.toString());
		if (Objects.nonNull(redisDeliveryDay)) {
			// 返回REDIS值
			return CommonResult.success((int) redisDeliveryDay);
		}
		OpsPoWarehouseDeliverydayKey key = new OpsPoWarehouseDeliverydayKey();
		key.setFromwarehouse(fromWarehouseCode);
		key.setTowarehouse(toWarehouseCode);
		OpsPoWarehouseDeliveryday selectResult = opsPoWarehouseDeliverydayMapper.selectByPrimaryKey(key);
		if (Objects.nonNull(selectResult)) {
			deliveryDay = selectResult.getDeliveryday();
		}
		redisUtil.hPut(Constants.REDIS_WAREHOUSE_DELIVERDAY_BY_WAREHOUSE_CODE, param.toString(), deliveryDay);
		redisUtil.expire(Constants.REDIS_WAREHOUSE_DELIVERDAY_BY_WAREHOUSE_CODE, 604800);// 存7天
		return CommonResult.success(deliveryDay);
	}
}

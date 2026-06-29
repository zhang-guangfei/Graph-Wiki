package com.sales.ops.serviceimpl.ba;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsWarehouseSalesbranchConfigMapper;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfigExample;
import com.sales.ops.dto.query.WarehouseSalesbranchQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.CKTYPEEnum;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.serviceimpl.cache.StaticMapUtil;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OpsWarehouseSalesbranchServiceImpl implements OpsWarehouseSalesbranchService {

    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Resource
    OpsWarehouseSalesbranchConfigMapper opsWarehouseSalesbranchConfigMapper;


    @Override
    public Integer add(OpsWarehouseSalesbranchConfig record) {
        record.setDelflag(0);
        return opsWarehouseSalesbranchConfigMapper.insertSelective(record);
    }

    @Override
    public Integer modify(OpsWarehouseSalesbranchConfig record) {
        return opsWarehouseSalesbranchConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer removeList(List<Integer> ids) {
        int result = 0;
        for (Integer id : ids) {
            OpsWarehouseSalesbranchConfig deleteObject = new OpsWarehouseSalesbranchConfig();
            deleteObject.setId(id);
            deleteObject.setDelflag(1);
            result += opsWarehouseSalesbranchConfigMapper.updateByPrimaryKeySelective(deleteObject);
        }
        return result;
    }


    @Override
    public List<OpsWarehouseSalesbranchConfig> findAll() {
        return opsWarehouseSalesbranchConfigMapper.selectByExample(null);
    }

    /**
     * 分页条件查询配置信息
     */
    @Override
    public PageInfo<OpsWarehouseSalesbranchConfig> findByPage(PageModel<WarehouseSalesbranchQO> pageModel) {
        OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
        OpsWarehouseSalesbranchConfigExample.Criteria criteria = example.createCriteria();
        WarehouseSalesbranchQO condition = pageModel.getCondition();
        criteria.andDelflagEqualTo(0);
        if (condition.getWarehouseCode() != null) {
            criteria.andWarehouseCodeLike("%" + condition.getWarehouseCode() + "%");
        }
        if (!CollectionUtils.isEmpty(condition.getSalesBranchId())) {
            criteria.andSalesBranchIdIn(condition.getSalesBranchId());
        }
        if (condition.getDescription() != null) {
            criteria.andDescriptionLike("%" + condition.getDescription() + "%");
        }
        if (condition.getPriority() != null) {
            criteria.andPriorityEqualTo(condition.getPriority());
        }
        if (condition.getDeliveryDay() != null) {
            criteria.andDeliveryDayEqualTo(condition.getDeliveryDay());
        }
        if (pageModel.getOrderBy() != null && pageModel.getOrderBy() != "") {
            example.setOrderByClause(pageModel.getOrderBy());
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo(opsWarehouseSalesbranchConfigMapper.selectByExample(example));
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/13 11:36
     * @description：按照客户代码，查询对应可出库的仓库号
     */
    @Override
    public List<OpsWarehouseSalesbranchConfig> getBranchListBysalesBranchId(String orderType,String customerNo, String salesBranchId, String warehouseTypeCode, Integer expDlvType, String expLinkNo) throws OpsException {
        if (OrderTypeEnum.YIBANMAOYI.equals(orderType)) {
            //todo 一般贸易单处理逻辑 通过报关仓库获取出库顺序（问题：1.目前报关仓库号不知道从哪来；2.一般贸易发往国外地址怎么填写）|| or根据收货地营业所代码 那么代码就不用动
        }
        String warehousecode = "";
        boolean mustWt = false;
        //委托在库流程判断
        if (OrderSpecExpType.include(expDlvType, OrderSpecExpType.NotOrderToCSStock)) {//不出委托在库
            if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseTypeCode)) {
                throw Exceptions.OpsException("此单为不出服务备库订单，流程配置错误");
            }
        } else if (OrderSpecExpType.include(expDlvType, OrderSpecExpType.MustOrderToCSStock)) {//必须出委托在库
            if (!WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseTypeCode)) {
                throw Exceptions.OpsException("此单为必须出服务备库订单，流程配置错误");
            }
            mustWt = true;
        }
        //委托在库选择
        if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseTypeCode)) {//如果是委托在库 直接从warehouse 取仓库数据关联 代理店代码  后补单子以后无后补单子
            if (StringUtils.isBlank(customerNo)) {
                throw Exceptions.OpsException("服务备库代理店代码不容许空");
            }
            List<String> houseList = opsWarehouseService.findCodesByTypeCache(mustWt,customerNo, warehouseTypeCode, false, warehousecode);//已经排除委托在库贩卖机
            List<OpsWarehouseSalesbranchConfig> list = new ArrayList<>();
            if (houseList == null || houseList.size() == 0) {//
                if (mustWt) {
                    throw Exceptions.OpsException("必须出服务备库无此仓库，代理店代码：" + customerNo);
                } else {
                    log.debug("出服务备库，无服务备库。代理店代码：" + customerNo);
                }
            } else {
                if(mustWt && houseList.size() >1){// mustWt必须出委托
                    throw Exceptions.OpsException("必须出服务备库存在多仓，代理店代码：" + customerNo);
                }
                for (String wareHouseCode : houseList) {
                    OpsWarehouseSalesbranchConfig obj = new OpsWarehouseSalesbranchConfig();
                    obj.setWarehouseCode(wareHouseCode);
                    list.add(obj);
                }
            }

            return list;
        } else {//否则关联营业所代码关联仓库号
            List<String> houseList = opsWarehouseService.findCodesByTypeCache(false,"", warehouseTypeCode, false, "");//已经排除委托在库贩卖机
            if (houseList == null || houseList.size() == 0) {
                throw Exceptions.OpsException("无此仓库号");
            }
            OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
            OpsWarehouseSalesbranchConfigExample.Criteria criterion = example.createCriteria();
            criterion.andDelflagEqualTo(0);
            if (StringUtils.isNotBlank(salesBranchId)) {
                criterion.andSalesBranchIdEqualTo(salesBranchId);
            } else {
                throw Exceptions.OpsException("查询可出库仓库,查询条件salesBranchId不可为空!");
            }
            criterion.andWarehouseCodeIn(houseList);
            example.setOrderByClause(" priority  asc ");
            // bugid:20641
            List<OpsWarehouseSalesbranchConfig> opsWarehouseSalesbranchConfigList = new ArrayList<>();
            String key = salesBranchId + String.join(",", houseList);
            if (!StaticMapUtil.salesbranchConfigMap.containsKey(key)){
                opsWarehouseSalesbranchConfigList = opsWarehouseSalesbranchConfigMapper.selectByExample(example);
                StaticMapUtil.salesbranchConfigMap.put(key,opsWarehouseSalesbranchConfigList);
            }
            return StaticMapUtil.salesbranchConfigMap.get(key);
        }
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/24 14:34
     * @description：查询集约货位
     */
    @Override
    public String getGatherWarehousesWithHouse(String salesBranchId, List<String> houseList) throws OpsException {
        List<OpsWarehouse> opsWarehouseList = opsWarehouseService.findByIds(houseList);
        if (opsWarehouseList == null || opsWarehouseList.size() == 0) {
            return null;
        }
        List<String> exampleHouseList = new ArrayList<>();
        for (OpsWarehouse house : opsWarehouseList) {
            if (house.getCentralizeFlag()) {
                exampleHouseList.add(house.getWarehouseCode());
            }
        }
        List<OpsWarehouseSalesbranchConfig> opsWarehouseSalesbranchConfigList = getGatherWarehouses(salesBranchId, exampleHouseList);
        if (Objects.isNull(opsWarehouseSalesbranchConfigList) || opsWarehouseSalesbranchConfigList.isEmpty()) {
            throw Exceptions.OpsException("当前客户不存在集约仓!");
        } else {
            return opsWarehouseSalesbranchConfigList.get(0).getWarehouseCode();
        }
    }

    /**
     * 集约仓为属地集约,拆分型号 需要出加工中心有拆分能力的仓库
     * @param ckTypeSingle 发货方式是否为单仓 ;单仓时中心仓为属地集约 ，非单仓时 有加工能力的中心仓为属地集约
     * @param salesBranchId
     * @param doSourceEnum
     * @return
     * @throws OpsException
     */
    @Override
    public String getGatherWarehousesWithBranchId(Boolean ckTypeSingle, String salesBranchId, DoSourceEnum doSourceEnum) throws OpsException {
        //直接选择属地集约仓（拆分数量和型号）
        List<OpsWarehouseSalesbranchConfig> opsWarehouseList = getGatherWarehouses(salesBranchId, null);
        if (Objects.isNull(opsWarehouseList) || opsWarehouseList.isEmpty()) {
            throw Exceptions.OpsException("当前部门代码不存在可出库仓!" + salesBranchId, salesBranchId);
        }
        for (OpsWarehouseSalesbranchConfig warehouseSalesbranchConfig : opsWarehouseList) {
            OpsWarehouse opsWarehouse = opsWarehouseService.findById(warehouseSalesbranchConfig.getWarehouseCode());
            if(ckTypeSingle){
                if (opsWarehouse.getCentralizeFlag()) {
                    return opsWarehouse.getWarehouseCode();
                }
            } else {
                if (DoSourceEnum.ASSModelNo.equals(doSourceEnum)) {
                    if (opsWarehouse.getCentralizeFlag() && opsWarehouse.getAssemblyFlag()) {
                        return opsWarehouse.getWarehouseCode();
                    }
                } else  {
                    if (opsWarehouse.getCentralizeFlag()) {
                        return opsWarehouse.getWarehouseCode();
                    }
                }
            }

        }
        return "";
    }

    public List<OpsWarehouseSalesbranchConfig> getGatherWarehouses(String salesBranchId, List<String> houseList) throws OpsException {
        OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
        OpsWarehouseSalesbranchConfigExample.Criteria criterion = example.createCriteria();
        criterion.andDelflagEqualTo(0);
        if (StringUtils.isNotBlank(salesBranchId)) {
            criterion.andSalesBranchIdEqualTo(salesBranchId);
        } else {
            throw Exceptions.OpsException("查询可出库仓库,查询条件salesBranchId不可为空!");
        }
        if (!CollectionUtils.isEmpty(houseList)) {
            criterion.andWarehouseCodeIn(houseList);
        }
        example.setOrderByClause(" priority asc");

        return opsWarehouseSalesbranchConfigMapper.selectByExample(example);
    }

    /**
     * 按照客户代码，查询对应可出库的仓库号
     */
    @Override
    public List<OpsWarehouseSalesbranchConfig> getBranchListBysalesBranchId(String salesBranchId) {
        OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
        example.createCriteria().andDelflagEqualTo(0).andSalesBranchIdEqualTo(salesBranchId);
        return opsWarehouseSalesbranchConfigMapper.selectByExample(example);
    }

    @Override
    public String getWarehouseCodeWithMaxDeliveryDay(String deptNo, List<String> warehouses) throws OpsException {
        String stockCode = null;
        List<OpsWarehouseSalesbranchConfig> branches = getGatherWarehouses(deptNo, warehouses);
        if (branches.size() > 0) {
            for (OpsWarehouseSalesbranchConfig branch : branches) {
                if (branch.getDeliveryDay() == null) {
                    branch.setDeliveryDay(0);
                }
            }
            OpsWarehouseSalesbranchConfig branch = branches.stream().max(Comparator.comparing(OpsWarehouseSalesbranchConfig::getDeliveryDay)).get();
            stockCode = branch.getWarehouseCode();
        }
        return stockCode;
    }

    @Override
    public List<OpsWarehouseSalesbranchConfig> getConfigBySalesBranchIdAndWarehouse(String salesBranchId, String warehouse) {
        OpsWarehouseSalesbranchConfigExample example = new OpsWarehouseSalesbranchConfigExample();
        OpsWarehouseSalesbranchConfigExample.Criteria criterion = example.createCriteria();
        criterion.andDelflagEqualTo(0);
        criterion.andSalesBranchIdEqualTo(salesBranchId).andWarehouseCodeEqualTo(warehouse);
        example.setOrderByClause(" delivery_day desc");
        return opsWarehouseSalesbranchConfigMapper.selectByExample(example);
    }


}

package com.sales.ops.serviceimpl.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.service.warehouse.OPSWarehouseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：仓库相关缓存业务
 * @date ：Created in 2021/10/18 17:03
 */
@Service
public class OPSWarehouseServiceImpl implements OPSWarehouseService {

    @Autowired
    private OPSRedisUtils opsRedisUtils;

    @Autowired
    private OPSWarehouseDao opsWarehouseDao;

    /**
     * 查询仓库
     * @param warehouseCode
     * @return
     */
    @Override
    public OpsWarehouse selectWareHouseByCondition(String warehouseCode) {
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(warehouseCode);
        /*//引入redis
        //根据modelNo到redis内获取
        if(StringUtils.isBlank(warehouseCode)){
            return null;
        }
        Object obj  = opsRedisUtils.hGet("ops:warehouse:warehouseCode",warehouseCode);
        if(obj !=null){
            opsWarehouse = JSON.parseObject(obj.toString(),OpsWarehouse.class);
        }
        //若redis内不存在对应的product,则访问下游service
        if(opsWarehouse == null){
            opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(warehouseCode);
            if(opsWarehouse != null){
                //设置product到redis内
                opsRedisUtils.hPut("ops:warehouse:warehouseCode",warehouseCode,JSON.toJSONString(opsWarehouse));
            }
        }*/
        return opsWarehouse;
    }

    /**
     * 刷新数据 仓库数据
     * @param mi 分钟
     * @return
     */
    @Override
    public List<String> refreshWareHouseData(String mi) {
        Long time = Long.parseLong(mi);
        //productBomDetail
        List<String> warehouseCodes = opsWarehouseDao.refreshOpsWarehouseData(time);
        for(String warehouseCode : warehouseCodes){
            opsRedisUtils.hDelete("ops:warehouse:warehouseCode",warehouseCode);
        }
        if(warehouseCodes.size() == 0){//没有更新内容
            return null;
        }
        return warehouseCodes;
    }

    /**
     * 查询仓库和供应商关系表
     * @param warehouseCode
     * @return
     */
    @Override
    public List<OpsWarehouseSupplierConfig> selectWarehouseSupplierConfigByCondition(String warehouseCode) {
        List<OpsWarehouseSupplierConfig> list = new ArrayList<>();
        list = opsWarehouseDao.queryOpsWarehouseSupplierConfigByWarehouseCode(warehouseCode);
        /*if(StringUtils.isBlank(warehouseCode)){
            return null;
        }
        //根据modelNo到redis内获取
        Object obj = opsRedisUtils.hGet("ops:warehouseSupplierConfig:warehouseCode",warehouseCode);
        if(null != obj){
            JSONArray arr = JSONArray.parseArray(obj.toString());
            int arrSize = arr.size();
            for(int i=0;i<arrSize;i++){
                list.add(JSON.parseObject(arr.getString(i),OpsWarehouseSupplierConfig.class));
            }
        }
        //若redis内不存,则访问下游service
        if(list.isEmpty()){
            list = opsWarehouseDao.queryOpsWarehouseSupplierConfigByWarehouseCode(warehouseCode);
            if(CollectionUtils.isNotEmpty(list)){
                JSONArray resultArr = JSONArray.parseArray(JSON.toJSONString(list));
                opsRedisUtils.hPut("ops:warehouseSupplierConfig:warehouseCode",warehouseCode,resultArr.toString());
            }
        }*/
        return list;
    }

    /**
     * 刷新缓存 仓库和供应商关系表
     * @param mi 分钟
     * @return
     */
    @Override
    public List<String> refreshWarehouseSupplierConfigData(String mi) {
        Long time = Long.parseLong(mi);
        //productBomDetail
        List<String> warehouseCodes = opsWarehouseDao.refreshOpsWarehouseSupplierConfigData(time);
        for(String warehouseCode : warehouseCodes){
            opsRedisUtils.hDelete("ops:warehouseSupplierConfig:warehouseCode",warehouseCode);
        }
        if(CollectionUtils.isEmpty(warehouseCodes)){//没有更新内容
            return null;
        }
        return warehouseCodes;
    }

    /**
     * 查询仓库营业所关联关系表
     * @param warehouseCode
     * @return
     */
    @Override
    public List<OpsWarehouseSalesbranchConfig> selectOpsWarehouseSalesbranchConfig(String warehouseCode) {
        List<OpsWarehouseSalesbranchConfig> list = new ArrayList<>();
        list = opsWarehouseDao.queryOpsOpsWarehouseSalesbranchConfigByWarehouseCode(warehouseCode);
        /*if(StringUtils.isBlank(warehouseCode)){
            return null;
        }
        //根据modelNo到redis内获取
        Object obj = opsRedisUtils.hGet("ops:warehouseSalesbranchConfig:warehouseCode",warehouseCode);
        if(null != obj){
            JSONArray arr = JSONArray.parseArray(obj.toString());
            int arrSize = arr.size();
            for(int i=0;i<arrSize;i++){
                list.add(JSON.parseObject(arr.getString(i),OpsWarehouseSalesbranchConfig.class));
            }
        }
        //若redis内不存,则访问下游service
        if(list.isEmpty()){
            list = opsWarehouseDao.queryOpsOpsWarehouseSalesbranchConfigByWarehouseCode(warehouseCode);
            if(CollectionUtils.isNotEmpty(list)){
                JSONArray resultArr = JSONArray.parseArray(JSON.toJSONString(list));
                opsRedisUtils.hPut("ops:warehouseSalesbranchConfig:warehouseCode",warehouseCode,resultArr.toString());
            }
        }*/
        return list;
    }

    /**
     * 刷新 仓库营业所关联关系表
     * @param mi 分钟
     * @return
     */
    @Override
    public List<String> refreshWarehouseSalesbranchConfigData(String mi) {
        Long time = Long.parseLong(mi);
        //productBomDetail
        List<String> warehouseCodes = opsWarehouseDao.refreshOpsWarehouseSalesBranchConfig(time);
        for(String warehouseCode : warehouseCodes){
            opsRedisUtils.hDelete("ops:warehouseSalesbranchConfig:warehouseCode",warehouseCode);
        }
        if(CollectionUtils.isEmpty(warehouseCodes)){//没有更新内容
            return null;
        }
        return warehouseCodes;
    }
}

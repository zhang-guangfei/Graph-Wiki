package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.DictDataMapper;
import com.smc.smccloud.mapper.OpsWarehouseMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.service.DictCommonService;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author lyc
 * @Date 2023/1/10 16:40
 * @Descripton TODO
 */
@Service
public class DictCommonServiceImpl implements DictCommonService {

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private OpsWarehouseMapper opsWarehouseMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<Map<String,String>>  getDataCodesMap(String classCode){
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(classCode) ) {
            return ResultVo.failure("classCode  不可为空.");
        }
//        LambdaQueryWrapper<DataTypeDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(DataTypeDO::getClassCode,classCode)
//                    .select(DataTypeDO::getCode,DataTypeDO::getCodeName)
//                    .orderByAsc(DataTypeDO::getCode);
//
//        List<DataTypeDO> dataTypeDOS = dictDataMapper.selectList(queryWrapper);
//        for (DataTypeDO dataTypeDO : dataTypeDOS) {
//            map.put(dataTypeDO.getCode(), dataTypeDO.getCodeName());
//        }
       ResultVo<List<DataCodeVO> > resultVo =this.getDataCodes(classCode);
        if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())){
            return ResultVo.failure("暂无数据");
        }
        for (DataCodeVO vo : resultVo.getData()) {
            map.put(vo.getCode(), vo.getCodeName());
        }
        return ResultVo.success(map);
    }

    @Override
    @DS("opscmm")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<DataTypeVO> getDataTypeCodesInfo(String classCode, String code) {
        if (StringUtils.isBlank(classCode) || StringUtils.isBlank(code)) {
            return ResultVo.failure("classCode & code 不可为空.");
        }
        LambdaQueryWrapper<DataTypeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DataTypeDO::getClassCode,classCode)
                .eq(DataTypeDO::getCode,code);
        DataTypeDO dataTypeDO = dictDataMapper.selectOne(queryWrapper);
        if (dataTypeDO == null) {
            return ResultVo.failure("暂无数据");
        }
        DataTypeVO dataTypeVO = BeanCopyUtil.copy(dataTypeDO, DataTypeVO.class);
        return ResultVo.success(dataTypeVO);
    }

    @Override
    public ResultVo<DataTypeVO> getDataTypeCodesInfoWithCatch(String classCode, String code) {

        if (StringUtils.isBlank(classCode) || StringUtils.isBlank(code)) {
            return ResultVo.failure("classCode & code 不可为空.");
        }
        String key = CommonConstants.dict_info_catch_key + classCode + "_" + code;
        Object o = redisManager.get(key);
        if(o != null) {
            DataTypeVO dataTypeVO = JSON.parseObject(o.toString(), DataTypeVO.class);
            return ResultVo.success(dataTypeVO);
        }

        ResultVo<DataTypeVO> dataTypeCodesInfo = getDataTypeCodesInfo(classCode, code);
        if (dataTypeCodesInfo.isSuccess()) {
            DataTypeVO dataTypeVO = dataTypeCodesInfo.getData();
            redisManager.set(key, JSON.toJSONString(dataTypeVO), 60 * 60 * 24);
        }
        return dataTypeCodesInfo;
    }

    @Override
    public ResultVo<DataTypeVO> getDataTypeCodesInfoWithDS(String classCode, String code) {
        if (StringUtils.isBlank(classCode) || StringUtils.isBlank(code)) {
            return ResultVo.failure("classCode & code 不可为空.");
        }
        DataTypeDO dataTypeDO = dictDataMapper.selDictInfoByClassCodeAndCode(classCode, code);
        if (dataTypeDO == null) {
            return ResultVo.failure("暂无数据");
        }
        DataTypeVO dataTypeVO = BeanCopyUtil.copy(dataTypeDO, DataTypeVO.class);
        return ResultVo.success(dataTypeVO);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<Map<String,String>>  getWarehouseMap(String warehouseType){
        Map<String, String> map = new HashMap<>();
        LambdaQueryWrapper<WarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseDO::getWarehouseType,warehouseType)
                .select(WarehouseDO::getWarehouseCode,WarehouseDO::getWarehouseName)
                .orderByAsc(WarehouseDO::getWarehouseType,WarehouseDO::getWarehouseCode);

        List<WarehouseDO> warehouseDOS = opsWarehouseMapper.selectList(queryWrapper);
        for (WarehouseDO warehouseDO : warehouseDOS) {
            map.put(warehouseDO.getWarehouseCode(), warehouseDO.getWarehouseName());
        }
        return ResultVo.success(map);
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehouseType) {

        if (StringUtils.isBlank(warehouseType)) {
            return ResultVo.failure("仓库类型不可为空");
        }

        if (redisManager.hasKey(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + warehouseType)) {
            Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + warehouseType);
            if (o != null) {
                return ResultVo.success(JSON.parseArray(o.toString(), String.class));
            }
        }
        List<String> warehouseCodes = opsWarehouseMapper.getWarehouseCodesByWarehouseType(warehouseType);
        redisManager.set(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + warehouseType, JSON.toJSONString(warehouseCodes),60*60*24*7);
        return ResultVo.success(warehouseCodes);
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public  ResultVo<List<String>> getWarehouseCodesByWarehouseCodeForMasterAndSub(String warehouseCode){
        if (StringUtils.isBlank(warehouseCode)) {
            return ResultVo.failure("仓库代码不可为空");
        }
        String redisKey=Constants.REDIS_WAREHOUSE_CODES_FOR_SUB + warehouseCode;
        List<String> warehouseCodes=new ArrayList<>();
        if (redisManager.hasKey(redisKey)) {
            Object o = redisManager.get(redisKey);
            if (o != null) {
                warehouseCodes=JSON.parseArray(o.toString(), String.class);
                if (warehouseCodes.size()>0) {
                    return ResultVo.success(warehouseCodes);
                }
            }
        }
       warehouseCodes = opsWarehouseMapper.getWarehouseCodesByWarehouseCodeForMasterAndSub(warehouseCode);
        redisManager.set(redisKey, JSON.toJSONString(warehouseCodes),60*60*24);
        return ResultVo.success(warehouseCodes);
    }
    @Override
    @DS("opscmm")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<DataCodeVO>> getDataCodes(String classCode) {
        String key = CommonConstants.DICT_CLASS_CODE+classCode;
        List<DataCodeVO> dataCodeVOList = new ArrayList<>();
        if (redisManager.hasKey(key)) {
            Object o = redisManager.get(key);
            if (o != null) {
                dataCodeVOList = JSONArray.parseArray(o.toString(), DataCodeVO.class);
            }
        } else {
            List<DataTypeDO> listDO = getValidDataCodesDO(classCode);
            dataCodeVOList = BeanCopyUtil.copyList(listDO, DataCodeVO.class);
            redisManager.set(key,JSONArray.toJSONString(dataCodeVOList));
        }
        return ResultVo.success(dataCodeVOList);
    }

    @Override
    public ResultVo<List<DataCodeVO>> getDataCodesNotCache(String classCode) {
        List<DataTypeDO> listDO = getValidDataCodesDO(classCode);
        List<DataCodeVO> dataCodeVOList = BeanCopyUtil.copyList(listDO, DataCodeVO.class);
        return ResultVo.success(dataCodeVOList);
    }

    /**
     * 根据分类编码查询有效代码
     */
    private List<DataTypeDO> getValidDataCodesDO(String classCode) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(DataTypeDO::getClassCode, classCode)
                .eq(DataTypeDO::getStatus, 1)
                .eq(DataTypeDO::getParentCode, "")
                .orderByAsc(DataTypeDO::getSort, DataTypeDO::getCode);
        return dictDataMapper.selectList(query);
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<String> getMasterWarehouseCodes() {
        Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (o != null) {
            return JSON.parseArray(o.toString(), String.class);
        }
       List<String>  masterWareHouse =opsWarehouseMapper.getWarehouseCodesByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode());
//        if (PublicUtil.isNotEmpty(masterWareHouse)) {
//            redisManager.set(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode(), masterWareHouse);
//        }
        return masterWareHouse;
    }
    @Override
    public String getMasterWarehouseByCode(String warehouseCode){
        String masterWarehouse=null;
        List<String>  masterWareHouse=this.getMasterWarehouseCodes();
         if (masterWareHouse.contains(warehouseCode))
         {
             return warehouseCode;
         }
         LambdaQueryWrapper<WarehouseDO> queryWrapper=new LambdaQueryWrapper<>();
         queryWrapper.select(WarehouseDO::getParentCode)
                 .eq(WarehouseDO::getWarehouseCode,warehouseCode)
                 .eq(WarehouseDO::getDelflag,0);
       List<WarehouseDO>  warehouseDOS= opsWarehouseMapper.selectList(queryWrapper);
       if (PublicUtil.isNotEmpty(warehouseDOS) &&warehouseDOS.size()>=1)
       {
           masterWarehouse=warehouseDOS.get(0).getParentCode();
           if (StringUtil.isEmpty(masterWarehouse))
           {
               return "";
           }
           if (masterWareHouse.contains(masterWarehouse))
           {
               return masterWarehouse;
           }
           int i=0;
           while (masterWareHouse==null && i<=2) {
               masterWarehouse = this.getMasterWarehouseByCode(masterWarehouse);
               i+=1;
           }
       }
      return   masterWarehouse;
    }
}

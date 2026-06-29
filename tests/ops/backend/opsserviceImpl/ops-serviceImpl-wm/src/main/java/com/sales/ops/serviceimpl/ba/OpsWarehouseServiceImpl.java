package com.sales.ops.serviceimpl.ba;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsWarehouseAddressConfigMapper;
import com.sales.ops.db.dao.OpsWarehouseAddressMapper;
import com.sales.ops.db.dao.OpsWarehouseMapper;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.WarehouseAddressConfigDao;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.dto.warehouse.WarehouseAddressDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.serviceimpl.cache.StaticMapUtil;
import com.smc.smccloud.core.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OpsWarehouseServiceImpl implements OpsWarehouseService {

    @Resource
    private OpsWarehouseMapper opsWarehouseMapper;
    @Resource
    private WmCommonService wmCommonService;
    @Resource
    private SupplierMapper supplierMapper;
    @Resource
    private OpsWarehouseAddressMapper opsWarehouseAddressMapper;
    @Resource
    private WarehouseAddressConfigDao warehouseAddressConfigDao;

    @Resource
    private OpsWarehouseAddressConfigMapper opsWarehouseAddressConfigMapper;


    /**
     * 仓库多地址查询页面 list
     * @param pageModel
     * @return
     */
    @Override
    public PageInfo<OpsWarehouseAddress> findMulAddressByPage(PageModel<OpsWarehouseAddress> pageModel) {
        OpsWarehouseAddressExample example = new OpsWarehouseAddressExample();
        OpsWarehouseAddressExample.Criteria criteria = example.createCriteria();
        OpsWarehouseAddress condition = pageModel.getCondition();
        if (condition.getWarehouseCode() != null) {
            criteria.andWarehouseCodeLike("%" + condition.getWarehouseCode() + "%");
        }
        criteria.andDelflagEqualTo(false);
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo(opsWarehouseAddressMapper.selectByExample(example));
    }


    /**
     * 多地址添加
     * @param record
     * @return
     */
    @Override
    public Integer addMulAddress(OpsWarehouseAddress record) {
        record.setWarehouseCode(record.getWarehouseCode().toUpperCase());
        record.setDelflag(false);
        record.setCreateTime(DateUtil.getNow());
        return opsWarehouseAddressMapper.insertSelective(record);
    }


    /**
     * 多地址修改
     */
    @Override
    public Integer modifyMulAddress(OpsWarehouseAddress record) {
        record.setUpdateTime(DateUtil.getNow());
        int i = opsWarehouseAddressMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public Long getMulAddressCountByAddressId(List<Long> codes) {
        OpsWarehouseAddressConfigExample configExample = new OpsWarehouseAddressConfigExample();
        configExample.createCriteria().andDelflagEqualTo(false).andAddressIdIn(codes);
        return opsWarehouseAddressConfigMapper.countByExample(configExample);
    }

    /**
     * 批量逻辑删除
     * 软删除，添加新的数据时主键不能重复
     */
    @Override
    public Integer removeMulAddressList(List<Long> codes) {
        int result = 0;
        for (Long code : codes) {
            OpsWarehouseAddress deleteObject = new OpsWarehouseAddress();
            deleteObject.setId(code);
            deleteObject.setDelflag(true);
            result += opsWarehouseAddressMapper.updateByPrimaryKeySelective(deleteObject);
        }
        return result;
    }

    /**
     * 保存仓库多地址
     * @param warehouseAddressDto
     * @return
     */
    @Override
    public Integer saveMultAaddressConfig(WarehouseAddressDto warehouseAddressDto) throws OpsException {

        //1. 删除关联关系
        if(CollectionUtils.isNotEmpty(warehouseAddressDto.getDoTypeList())){
            OpsWarehouseAddressConfigExample configEx = new OpsWarehouseAddressConfigExample();
            configEx.createCriteria().andDelflagEqualTo(false).andWarehouseCodeEqualTo(warehouseAddressDto.getWarehouseCode())
                    .andAddressIdNotEqualTo(warehouseAddressDto.getAddressId()).andDoTypeIn(warehouseAddressDto.getDoTypeList());
            List<OpsWarehouseAddressConfig> addConList = opsWarehouseAddressConfigMapper.selectByExample(configEx);
            if(CollectionUtils.isNotEmpty(addConList)){
                StringBuffer buf = new StringBuffer();
                for(OpsWarehouseAddressConfig obj : addConList){
                    buf.append(DoTypeEnum.getType(obj.getDoType()).getDesc());
                    buf.append(";");
                }
                buf.append("地址重复");
                throw Exceptions.OpsException(buf.toString());
            }
        }

        int result = 0;
        //1. 删除关联关系
        OpsWarehouseAddressConfigExample configExample = new OpsWarehouseAddressConfigExample();
        configExample.createCriteria().andWarehouseCodeEqualTo(warehouseAddressDto.getWarehouseCode())
                .andAddressIdEqualTo(warehouseAddressDto.getAddressId());
        OpsWarehouseAddressConfig deleteFlagDto = new OpsWarehouseAddressConfig();
        deleteFlagDto.setDelflag(true);
        deleteFlagDto.setUpdator(warehouseAddressDto.getCreater());
        deleteFlagDto.setUpdateTime(DateUtil.getNow());
        opsWarehouseAddressConfigMapper.updateByExampleSelective(deleteFlagDto,configExample);
        for(String doType : warehouseAddressDto.getDoTypeList()){
            //2. 绑定关联关系
            if(Objects.nonNull(warehouseAddressDto.getAddressId())){
                OpsWarehouseAddressConfig insertConfig = new OpsWarehouseAddressConfig();
                insertConfig.setDelflag(false);
                insertConfig.setCreateTime(DateUtil.getNow());
                insertConfig.setCreator(warehouseAddressDto.getCreater());
                insertConfig.setAddressId(warehouseAddressDto.getAddressId());
                insertConfig.setWarehouseCode(warehouseAddressDto.getWarehouseCode());
                insertConfig.setDoType(doType);
                opsWarehouseAddressConfigMapper.insertSelective(insertConfig);
            }
        }
        return result;
    }

    /**
     * 查询仓库多地址
     * @param warehouseAddress
     * @return
     */
    @Override
    public List<OpsWarehouseAddress> getMultAdress(OpsWarehouseAddress warehouseAddress) {
        if(StringUtils.isNotBlank(warehouseAddress.getUpdator())){
            List<OpsWarehouseAddress> list =  new ArrayList<OpsWarehouseAddress>();
            OpsWarehouseAddress target =  warehouseAddressConfigDao.getWarehouseAddress(warehouseAddress.getWarehouseCode(),warehouseAddress.getUpdator());
            if(Objects.nonNull(target)){
                list.add(target);
            }
            return list;
        }else{
            OpsWarehouseAddressExample example = new OpsWarehouseAddressExample();
            OpsWarehouseAddressExample.Criteria criteria = example.createCriteria();
            criteria.andWarehouseCodeEqualTo(warehouseAddress.getWarehouseCode());
            criteria.andDelflagEqualTo(false);
            return  opsWarehouseAddressMapper.selectByExample(example);
        }
    }

    @Override
    public OpsWarehouseAddress getMultAdressById(Long id) {
        return opsWarehouseAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getMultAdressConfig(OpsWarehouseAddressConfig obj) {
        return warehouseAddressConfigDao.getWarehouseAddressConfig(obj.getWarehouseCode(),obj.getAddressId());
    }

    /**
     * bugid:11445 c14717 20230721
     * 查询仓库多地址
     * @param
     * @return
     */
    @Override
    public OpsWarehouse getMultAdressSetAddress(OpsWarehouse opsWarehouse,  String doType) {
        if(StringUtils.isNotBlank(doType)){
            // 1.查询物理仓库多地址 赋值地址到实体仓库
            OpsWarehouseAddress target =  warehouseAddressConfigDao.getWarehouseAddress(opsWarehouse.getWarehouseCode(),doType);
            if(Objects.nonNull(target)){
                opsWarehouse.setProvince(target.getProvince());
                opsWarehouse.setCity(target.getCity());
                opsWarehouse.setDistrict(target.getDistrict());

                opsWarehouse.setAdress(target.getAdress());
                opsWarehouse.setPostCode(target.getPostCode());
                opsWarehouse.setLinkman(target.getLinkman());

                opsWarehouse.setLinkPhone(target.getLinkPhone());
                opsWarehouse.setLinkMobile(target.getLinkMobile());
                opsWarehouse.setEnglishAddress(target.getEnglishAddress());

                opsWarehouse.setEnglishLinkman(target.getEnglishLinkman());
                opsWarehouse.setDelflag(target.getDelflag());
                opsWarehouse.setMail(target.getMail());
            }
        }
        return opsWarehouse;
    }

    /**
     * 添加一条数据
     */
    @Override
    public Integer add(OpsWarehouse record) {
        record.setDelflag(false);
        record.setCreator(UserDto.ADMIN.getUserName());
        record.setCreTime(new Date());
        return opsWarehouseMapper.insertSelective(record);
    }


    /**
     * 修改一条数据
     */
    @Override
    public Integer modify(OpsWarehouse record) {
        record.setModifier(UserDto.ADMIN.getUserName());
        record.setModifyTime(new Date());
        int i = opsWarehouseMapper.updateByPrimaryKeySelective(record);
        wmCommonService.refreshWarehouseCache(record.getWarehouseCode());
        return i;
    }

    /**
     * 批量逻辑删除
     * 软删除，添加新的数据时主键不能重复
     */
    @Override
    public Integer removeList(List<String> codes) {
        int result = 0;
        for (String code : codes) {
            OpsWarehouse deleteObject = new OpsWarehouse();
            deleteObject.setWarehouseCode(code);
            deleteObject.setDelflag(true);
            result += opsWarehouseMapper.updateByPrimaryKeySelective(deleteObject);
            wmCommonService.refreshWarehouseCache(code);
        }
        return result;
    }

    /**
     * 获取全部物理仓库信息
     */
    @Override
    public List<OpsWarehouse> findAll() {
        OpsWarehouseExample example = new OpsWarehouseExample();
        example.createCriteria().andDelflagEqualTo(false);
        return opsWarehouseMapper.selectByExample(example);
    }

    @Override
    public OpsWarehouse findById(String warehouseCode) {
        return opsWarehouseMapper.selectByPrimaryKey(warehouseCode);
    }

    @Override
    public List<OpsWarehouse> findByExample(OpsWarehouse condition) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseType())) {
            criteria.andWarehouseTypeEqualTo(condition.getWarehouseType());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseName())) {
            criteria.andWarehouseNameEqualTo(condition.getWarehouseName());
        }
        criteria.andDelflagEqualTo(false);
        return opsWarehouseMapper.selectByExample(example);
    }

    /**
     * 根据查询对象，分页查询符合条件的物理仓库
     * @param pageModel 分页三参数，仓库代码、仓库类别、仓库名称
     * @return 返回一个符合查询条件的集合，并且封装分页
     */
    @Override
    public PageInfo<OpsWarehouse> findByPage(PageModel<OpsWarehouse> pageModel) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        OpsWarehouse condition = pageModel.getCondition();
        if (condition.getWarehouseCode() != null) {
            criteria.andWarehouseCodeLike("%" + condition.getWarehouseCode() + "%");
        }
        if (condition.getWarehouseType() != null) {
            criteria.andWarehouseTypeLike("%" + condition.getWarehouseType() + "%");
        }
        if (condition.getWarehouseName() != null) {
            criteria.andWarehouseNameLike("%" + condition.getWarehouseName() + "%");
        }
        if (condition.getDelflag() != null && !condition.getDelflag()) {
            criteria.andDelflagEqualTo(false);
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo(opsWarehouseMapper.selectByExample(example));
    }


    /**
     * 获取仓库类别的枚举类
     * 用于提供库存查询模块的搜索栏下拉框数据
     */
    @Override
    public WarehouseTypeEnum[] findTypes() {
        return WarehouseTypeEnum.values();
    }

    /**
     * 根据仓库类别，动态获取符合的仓库名称的集合
     * 用于提供库存查询模块的搜索栏联动下拉框数据
     * @return 去重后的仓库名称集合
     */
    @Override
    public List<String> findNamesByType(String type) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(type)) {
            criteria.andWarehouseTypeEqualTo(type);
        }
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        List<String> houselist = new ArrayList<String>();
        for (OpsWarehouse warehouse : list) {
            houselist.add(warehouse.getWarehouseName());
        }
        return houselist;
    }
    @Override
    public List<OpsWarehouse> findCodeAndNameByType(String type) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(type)) {
            criteria.andWarehouseTypeEqualTo(type);
        }
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        List<OpsWarehouse> warehouseList = new ArrayList();
        for (OpsWarehouse warehouse : list) {
            OpsWarehouse item = new OpsWarehouse();
            item.setWarehouseCode(warehouse.getWarehouseCode());
            item.setWarehouseName(warehouse.getWarehouseName()+"["+warehouse.getWarehouseCode()+"]");
            warehouseList.add(item);
        }
        return warehouseList;
    }

    /**
     * 计算哈希值
     * @param
     * @return
     */
    public Long hashCodeTypeKey(Boolean mustwt,String customerNo, String type, Boolean disableFlag, String warehouseCode) {
        final int prime = 31;
        long result = 1;
        result = prime * result + (mustwt.hashCode());
        result = prime * result + (StaticMapUtil.hash(customerNo));
        // bugid:13426 c14717 20240122
        result = prime * result + (type.hashCode());
        result = prime * result + (disableFlag.hashCode());
        result = prime * result + (warehouseCode.hashCode());
        return result;
    }

    /**
     * bugid:20641
     * @param mustwt
     * @param customerNo
     * @param type
     * @param disableFlag
     * @param warehouseCode
     * @return
     */
    @Override
    public List<String> findCodesByTypeCache(boolean mustwt, String customerNo, String type, boolean disableFlag, String warehouseCode) {
        Long key = hashCodeTypeKey(mustwt, customerNo, type, disableFlag, warehouseCode);
        if(!StaticMapUtil.warehouseListHashMap.containsKey(key)){
            OpsWarehouseExample example = new OpsWarehouseExample();
            OpsWarehouseExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(warehouseCode)) {
                criteria.andWarehouseCodeEqualTo(warehouseCode);
            }
            if (StringUtils.isNotBlank(customerNo)) {//代理店代码 代理店不判断
                criteria.andCustomerNoEqualTo(customerNo);
            }
            if(!mustwt){
                criteria.andAutodispatchFlagEqualTo(true);//false 为委托在库贩卖机 是否参与自动分配
            }
            criteria.andWarehouseTypeEqualTo(type);
            criteria.andDisableFlagEqualTo(disableFlag);
            criteria.andDelflagEqualTo(false);
            List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
            List<String> houselist = new ArrayList<String>();
            for (OpsWarehouse warehouse : list) {
                houselist.add(warehouse.getWarehouseCode());
            }
            StaticMapUtil.warehouseListHashMap.put(key,houselist);
        }
        return StaticMapUtil.warehouseListHashMap.get(key);


    }

    @Override
    public List<String> findCodesByType(boolean mustwt,String customerNo, String type, boolean disableFlag, String warehouseCode) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(warehouseCode)) {
            criteria.andWarehouseCodeEqualTo(warehouseCode);
        }
        if (StringUtils.isNotBlank(customerNo)) {//代理店代码 代理店不判断
            criteria.andCustomerNoEqualTo(customerNo);
        }
        if(!mustwt){
            criteria.andAutodispatchFlagEqualTo(true);//false 为委托在库贩卖机 是否参与自动分配
        }
        criteria.andWarehouseTypeEqualTo(type);
        criteria.andDisableFlagEqualTo(disableFlag);
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        List<String> houselist = new ArrayList<String>();
        for (OpsWarehouse warehouse : list) {
            houselist.add(warehouse.getWarehouseCode());
        }
        return houselist;
    }

    @Override
    public List<OpsWarehouse> findByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        criteria.andWarehouseCodeIn(ids);
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        return list;
    }

    @Override
    public String findNameByCode(String code) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        criteria.andWarehouseCodeEqualTo(code);
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        if (!list.isEmpty()) {
            return list.get(0).getWarehouseName();
        } else {
            return null;
        }

    }

    @Override
    public OpsWarehouse findByName(String name) {
        OpsWarehouseExample example = new OpsWarehouseExample();
        OpsWarehouseExample.Criteria criteria = example.createCriteria();
        criteria.andWarehouseNameEqualTo(name);
        criteria.andDelflagEqualTo(false);
        List<OpsWarehouse> list = opsWarehouseMapper.selectByExample(example);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Supplier findSupplierByCode(String code) {
        return supplierMapper.selectByPrimaryKey(code);
    }
}

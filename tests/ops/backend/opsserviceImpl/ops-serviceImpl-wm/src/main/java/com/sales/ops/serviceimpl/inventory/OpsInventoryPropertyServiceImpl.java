package com.sales.ops.serviceimpl.inventory;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.errorEnum.SalesInfoErrorCodeEnum;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.AssertUtil;
import com.sales.ops.db.dao.InvPropViewMapper;
import com.sales.ops.db.dao.OpsInventoryPropertyMapper;
import com.sales.ops.db.dao.OpsInventoryTypeMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.InventoryPropertyDao;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsInventoryPropertyServiceImpl implements OpsInventoryPropertyService {

    private static final Logger logger = LoggerFactory.getLogger(OpsInventoryPropertyServiceImpl.class);
    @Resource
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;
    @Resource
    private InventoryPropertyDao inventoryPropertyDao;
    @Resource
    private OpsInventoryTypeMapper opsInventoryTypeMapper;
    @Autowired
    private InvPropViewMapper invPropViewMapper;


    /**
     * 库存查询 弱匹配
     * 前端调用，查询条件非必填
     * 根据库存类别、客户号、客户群号、ppl、pj查询批属性对象
     */
    @Override
    public List<OpsInventoryProperty> findByExample(OpsInventoryProperty condition) {
        OpsInventoryPropertyExample example = new OpsInventoryPropertyExample();
        OpsInventoryPropertyExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getInventoryTypeCode())) {
            criteria.andInventoryTypeCodeEqualTo(condition.getInventoryTypeCode());
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(condition.getCustomerNo());
        }
        if (StringUtils.isNotBlank(condition.getGroupCustomerNo())) {
            criteria.andGroupCustomerNoEqualTo(condition.getGroupCustomerNo());
        }
        if (StringUtils.isNotBlank(condition.getPpl())) {
            criteria.andPplEqualTo(condition.getPpl());
        }
        if (StringUtils.isNotBlank(condition.getProjectCode())) {
            criteria.andProjectCodeEqualTo(condition.getProjectCode());
        }
        criteria.andDelflagEqualTo(0);
        return opsInventoryPropertyMapper.selectByExample(example);
    }

    /**
     * 库存查询 强匹配
     * 服务调用，强制匹配空的查询条件
     * 根据库存类别、客户号、客户群号、ppl、pj查询批属性对象
     */
    @Override
    public List<OpsInventoryProperty> matchByExample(OpsInventoryProperty property) {
        OpsInventoryPropertyExample example = new OpsInventoryPropertyExample();
        OpsInventoryPropertyExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(property.getInventoryTypeCode())) {
            criteria.andInventoryTypeCodeEqualTo(property.getInventoryTypeCode());
        }
        if (StringUtils.isNotBlank(property.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(property.getCustomerNo());
        } else {
            criteria.andCustomerNoIsNull();
        }
        if (StringUtils.isNotBlank(property.getGroupCustomerNo())) {
            criteria.andGroupCustomerNoEqualTo(property.getGroupCustomerNo());
        } else {
            criteria.andGroupCustomerNoIsNull();
        }
        if (StringUtils.isNotBlank(property.getPpl())) {
            criteria.andPplEqualTo(property.getPpl());
        } else {
            criteria.andPplIsNull();
        }
        if (StringUtils.isNotBlank(property.getProjectCode())) {
            criteria.andProjectCodeEqualTo(property.getProjectCode());
        } else {
            criteria.andProjectCodeIsNull();
        }
        if (StringUtils.isNotBlank(property.getSalesInfoNo())) {
            criteria.andSalesInfoNoEqualTo(property.getSalesInfoNo());
        } else {
            criteria.andSalesInfoNoIsNull();
        }
        criteria.andDelflagEqualTo(0);
        return opsInventoryPropertyMapper.selectByExample(example);
    }

    @Override
    public OpsInventoryProperty findById(Long id) {
        return opsInventoryPropertyMapper.selectByPrimaryKey(id);
    }



    /**
     * @description 此方法返回一个批属性id，必填字段为InventoryTypeCode和对应的属性信息
     * @author C12961
     * @date 2022/10/18
     */
    @Override
    public synchronized Long findPropertyWithInsertByExample(OpsInventoryProperty property, UserDto userDto) throws OpsException {
        //校验property
        property = formatProperty(property);
        //计算唯一键
        String uKey = getPropertyUid(property);
        //查询有没有property
        Long id = findPropertyByUid(uKey);
        if (id != null) {
            log.info("查询到property:" + id);
            return id;
        }
        //如果没有找到，插入property
        property.setUid(uKey);
        property.setCreator(userDto.getUserName());
        try {
            inventoryPropertyDao.insertInventoryProperty(property);
            log.info("插入property:" + JSONUtil.toJsonStr(property));
        } catch (Exception e) {
            id = findPropertyByUid(uKey);
            if (id != null) {
                log.info("插入失败后，再次查询，查询到property:" + id);
                return id;
            } else {
                throw Exceptions.OpsException("插入失败后，再次查询，查询失败：" + e.getMessage());
            }
        }
        return property.getInventoryPropertyId();
    }

    @Override
    public String getPropertyUid(OpsInventoryProperty property) throws OpsException {
        String[] array = new String[6];

        array[0] = property.getInventoryTypeCode();
        array[1] = property.getCustomerNo();
        array[2] = property.getGroupCustomerNo();
        array[3] = property.getPpl();
        array[4] = property.getProjectCode();
        array[5] = property.getSalesInfoNo();

        for (int i = 0; i < 6; i++) {
            if (StringUtils.isBlank(array[i])) {
                array[i] = "NULL";
            }
        }
        String key = StringUtils.join(array, ",");
        return key;
    }

    @Override
    public OpsInventoryProperty formatProperty(OpsInventoryProperty property) throws OpsException {
        //不能为空，否则抛异常
        AssertUtil.notBlank(property.getInventoryTypeCode(), "请填写库存类别");
        //查询库存类别
        OpsInventoryType opsInventoryType = opsInventoryTypeMapper.selectByPrimaryKey(property.getInventoryTypeCode());
        //检测库存属性类别
        AssertUtil.notNull(opsInventoryType, "库存类别不存在：" + property.getInventoryTypeCode());
        //营业情报
        OpsInventoryProperty prop = new OpsInventoryProperty();
        prop.setInventoryTypeCode(property.getInventoryTypeCode());
        if ("QB_NO".equals(opsInventoryType.getInventoryTypeCode())) {
            AssertUtil.notBlank(property.getSalesInfoNo(), "请填写情报号");
            prop.setInventoryTypeCode("QB_NO");
            prop.setCustomerNo(property.getCustomerNo());
            prop.setSalesInfoNo(property.getSalesInfoNo());
            prop.setExpDate(property.getExpDate());
        } else {
            if (opsInventoryType.getMatchCustomerNo()) {
                AssertUtil.notBlank(property.getCustomerNo(), "请填写顾客代码");
                prop.setCustomerNo(property.getCustomerNo());
            }
            if (opsInventoryType.getMatchGroupCustomerNo()) {
                AssertUtil.notBlank(property.getGroupCustomerNo(), "请填写客户群代码");
                prop.setGroupCustomerNo(property.getGroupCustomerNo());
            }
            if (opsInventoryType.getMatchProjectCode()) {
                AssertUtil.notBlank(property.getProjectCode(), "请填写项目代码");
                prop.setProjectCode(property.getProjectCode());
            }
            if (opsInventoryType.getMathchPpl()) {
                AssertUtil.notBlank(property.getPpl(), "请填写PPL代码");
                prop.setPpl(property.getPpl());
            }
        }
        return prop;
    }

    private Long findPropertyByUid(String key) throws OpsException {

        OpsInventoryPropertyExample example = new OpsInventoryPropertyExample();
        example.createCriteria().andUidEqualTo(key);
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyMapper.selectByExample(example);
        if (propertyList.size() == 0) {
            return null;
        }
        if (propertyList.size() > 1) {
            throw Exceptions.OpsException("唯一键有重复");
        } else {
            return propertyList.get(0).getInventoryPropertyId();
        }

        //
        //example.clear();
        //OpsInventoryPropertyExample.Criteria criteria = example.createCriteria();
        //if (StringUtils.isNotBlank(property.getInventoryTypeCode())) {
        //    criteria.andInventoryTypeCodeEqualTo(property.getInventoryTypeCode());
        //} else {
        //    criteria.andInventoryTypeCodeIsNull();
        //}
        //if (StringUtils.isNotBlank(property.getCustomerNo())) {
        //    criteria.andCustomerNoEqualTo(property.getCustomerNo());
        //} else {
        //    criteria.andCustomerNoIsNull();
        //}
        //if (StringUtils.isNotBlank(property.getGroupCustomerNo())) {
        //    criteria.andGroupCustomerNoEqualTo(property.getGroupCustomerNo());
        //} else {
        //    criteria.andGroupCustomerNoIsNull();
        //}
        //if (StringUtils.isNotBlank(property.getPpl())) {
        //    criteria.andPplEqualTo(property.getPpl());
        //} else {
        //    criteria.andPplIsNull();
        //}
        //if (StringUtils.isNotBlank(property.getProjectCode())) {
        //    criteria.andProjectCodeEqualTo(property.getProjectCode());
        //} else {
        //    criteria.andProjectCodeIsNull();
        //}
        //if (StringUtils.isNotBlank(property.getSalesInfoNo())) {
        //    criteria.andSalesInfoNoEqualTo(property.getSalesInfoNo());
        //} else {
        //    criteria.andSalesInfoNoIsNull();
        //}
        //criteria.andDelflagEqualTo(0);
        //List<OpsInventoryProperty> propertyList = opsInventoryPropertyMapper.selectByExample(example);
        //if (propertyList.size() > 0) {
        //    return propertyList.get(0);
        //} else {
        //    return null;
        //}
    }


    /**
     * 查询并插入一条property
     * 老方法，已废弃
     */
    @Override
    public Long findPropertyWithInsertByExample2(OpsInventoryProperty property, UserDto userDto) throws OpsException {
        if (StringUtils.isBlank(property.getInventoryTypeCode())) {
            throw Exceptions.OpsException("请填写库存类别");
        }
        //查询库存类别
        OpsInventoryType opsInventoryType = opsInventoryTypeMapper.selectByPrimaryKey(property.getInventoryTypeCode());
        //营业情报只检测有没有情报号
        if ("QB_NO".equals(opsInventoryType.getInventoryTypeCode())) {
            if (StringUtils.isBlank(property.getSalesInfoNo())) {
                throw Exceptions.OpsException("请填写情报号");
            }
        } else {
            property.setSalesInfoNo(null);
            property.setExpDate(null);

            if (opsInventoryType == null) {
                throw Exceptions.OpsException("库存类别不存在：" + property.getInventoryTypeCode());
            }
            if (opsInventoryType.getMatchCustomerNo()) {
                if (StringUtils.isBlank(property.getCustomerNo())) {
                    throw Exceptions.OpsException("请填写顾客代码");
                }
            } else {
                property.setCustomerNo(null);
            }
            if (opsInventoryType.getMatchGroupCustomerNo()) {
                if (StringUtils.isBlank(property.getGroupCustomerNo())) {
                    throw Exceptions.OpsException("请填写客户群代码");
                }
            } else {
                property.setGroupCustomerNo(null);
            }
            if (opsInventoryType.getMatchProjectCode()) {
                if (StringUtils.isBlank(property.getProjectCode())) {
                    throw Exceptions.OpsException("请填写项目代码");
                }
            } else {
                property.setProjectCode(null);
            }
            if (opsInventoryType.getMathchPpl()) {
                if (StringUtils.isBlank(property.getPpl())) {
                    throw Exceptions.OpsException("请填写PPL代码");
                }
            } else {
                property.setPpl(null);
            }
        }
        List<OpsInventoryProperty> properties = matchByExample(property);
        if (properties.size() > 0) {
            logger.info("=====查询批属性成功=====");
            return properties.get(0).getInventoryPropertyId();
        } else {
            property.setVersion(0L);
            property.setDelflag(0);
            property.setCreator(userDto.getUserName());
            property.setCreTime(new Date());
            inventoryPropertyDao.insertInventoryProperty(property);
            return property.getInventoryPropertyId();
        }
    }

    /********************情报号查询****************************/
    /**
     * 查询带情报号的批属性
     * @author C12961
     * @date 2022/1/6 13:13
     */
    @Override
    public Long findPropertyIdBySalesInfoNo(String salesInfoNo) throws OpsException {
        OpsInventoryPropertyExample propertyExample = new OpsInventoryPropertyExample();
        propertyExample.createCriteria().andDelflagEqualTo(0)
                .andInventoryTypeCodeEqualTo(InventoryTypeEnum.QB.getType()).andSalesInfoNoEqualTo(salesInfoNo);
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyMapper.selectByExample(propertyExample);
        if (propertyList.size() == 1) {
            return propertyList.get(0).getInventoryPropertyId();
        } else if (propertyList.isEmpty()) {
            OpsInventoryPropertyExample example = new OpsInventoryPropertyExample();
            example.createCriteria().andDelflagEqualTo(1)
                    .andInventoryTypeCodeEqualTo(InventoryTypeEnum.QB.getType()).andSalesInfoNoEqualTo(salesInfoNo);
            List<OpsInventoryProperty> deletedList = opsInventoryPropertyMapper.selectByExample(example);
            if (!deletedList.isEmpty()) {
                OpsInventoryProperty opsInventoryProperty = deletedList.get(0);
                log.info("该情报号已删除:{}",salesInfoNo);
                return opsInventoryProperty.getInventoryPropertyId();
            } else {
                log.info("该情报号不存在:{}",salesInfoNo);
            }
            return null;
        } else {
            throw Exceptions.SalesInfoException(SalesInfoErrorCodeEnum.NON_UNIQUE_PROPERTY);
        }
    }


    /**
     * @description 查询到期的情报号
     * @author C12961
     * @date 2022/3/31 15:36
     */
    @Override
    public List<String> findExpireSalesInfo() throws OpsException {
        OpsInventoryPropertyExample propertyExample = new OpsInventoryPropertyExample();
        propertyExample.createCriteria().andDelflagEqualTo(0)
                .andInventoryTypeCodeEqualTo(InventoryTypeEnum.QB.getType()).andExpDateLessThan(new Date());
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyMapper.selectByExample(propertyExample);
        return propertyList.stream().map(OpsInventoryProperty::getSalesInfoNo).collect(Collectors.toList());
    }



    /**
     * @description 查询到期三天内的情报号
     * @author C12961
     * @date 2022/3/31 15:36
     */
    @Override
    public List<String> findExpireSalesInfoDeleted(Integer days) throws OpsException {
        OpsInventoryPropertyExample propertyExample = new OpsInventoryPropertyExample();
        propertyExample.createCriteria().andDelflagEqualTo(1)
                .andInventoryTypeCodeEqualTo(InventoryTypeEnum.QB.getType()).andExpDateLessThan(new Date())
                .andModifyTimeGreaterThan(DateUtils.addDays(new Date(), -days));
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyMapper.selectByExample(propertyExample);
        return propertyList.stream().map(OpsInventoryProperty::getSalesInfoNo).collect(Collectors.toList());
    }

    @Override
    public void deletePropertyById(Long id) {
        //如果已经删过，则不更新删除时间了，以免每天持续更新删除时间
        OpsInventoryProperty opsInventoryProperty = opsInventoryPropertyMapper.selectByPrimaryKey(id);
        if(opsInventoryProperty.getDelflag()==1){
            return;
        }
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryPropertyId(id);
        property.setDelflag(1);
        property.setModifyTime(new Date());
        opsInventoryPropertyMapper.updateByPrimaryKeySelective(property);
    }

    //查询库存批属性
    @Override
    public InvPropView getPropertyViewByInventoryId(Long inventoryId, String tableType) {
        InvPropViewExample invPropViewExample = new InvPropViewExample();
        invPropViewExample.createCriteria().andInventoryTableTypeEqualTo(tableType).andInventoryIdEqualTo(inventoryId);
        List<InvPropView> invPropViews = invPropViewMapper.selectByExample(invPropViewExample);
        if (invPropViews.size() == 1) {
            return invPropViews.get(0);
        } else {
            return null;
        }
    }
}

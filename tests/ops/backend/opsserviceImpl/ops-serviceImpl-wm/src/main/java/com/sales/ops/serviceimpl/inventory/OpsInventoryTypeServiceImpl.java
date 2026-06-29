package com.sales.ops.serviceimpl.inventory;

import cn.hutool.core.util.ObjectUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsInventoryTypeMapper;
import com.sales.ops.db.entity.OpsInventoryType;
import com.sales.ops.db.entity.OpsInventoryTypeExample;
import com.sales.ops.service.inventory.OpsInventoryTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/10/14 9:02
 * @auther C12961
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsInventoryTypeServiceImpl implements OpsInventoryTypeService {


    @Resource
    private OpsInventoryTypeMapper opsInventoryTypeMapper;

    @Override
    public List<OpsInventoryType> findAllTypes() {
        OpsInventoryTypeExample example = new OpsInventoryTypeExample();
        example.createCriteria().andDelflagEqualTo(0);
        return opsInventoryTypeMapper.selectByExample(example);
    }

    @Override
    public String findDescByType(String type) throws OpsException {
        OpsInventoryType inventoryType = opsInventoryTypeMapper.selectByPrimaryKey(type);
        if(ObjectUtil.isNotNull(inventoryType)){
            return inventoryType.getDescription();
        }else {
            throw Exceptions.OpsException("未查询到库存类别");
        }
    }

    public OpsInventoryType findInventoryTypeByCode(String inventoryTypeCode){
        return opsInventoryTypeMapper.selectByPrimaryKey(inventoryTypeCode);
    }

}

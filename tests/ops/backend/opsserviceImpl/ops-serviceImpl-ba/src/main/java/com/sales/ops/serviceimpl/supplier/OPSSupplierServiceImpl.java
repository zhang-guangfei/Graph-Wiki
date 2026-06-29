package com.sales.ops.serviceimpl.supplier;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.Supplier;
import com.sales.ops.db.extdao.OPSSupplierDao;
import com.sales.ops.service.supplier.OPSSupplierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/21 14:55
 */
@Service
public class OPSSupplierServiceImpl implements OPSSupplierService {

    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private OPSSupplierDao opsSupplierDao;

    @Override
    public Supplier findSupplierInfoById(String Id) {
        Supplier supplier = supplierMapper.selectByPrimaryKey(Id);
        /*if (StringUtils.isBlank(Id)) {
            return null;
        }
        //查询redis
        Object obj = opsRedisUtils.hGet("ops:supplier:Id", Id);
        //redis查询到了，返回redis解析对象
        if (obj != null) {
            supplier =  JSON.parseObject(obj.toString(),Supplier.class);
        }
        //redis查询不到，更新redis，返回数据库查到的对象
        if(supplier == null){
            supplier = supplierMapper.selectByPrimaryKey(Id);
        }*/
        return supplier;

    }

    @Override
    public List<String> refreshSupplierData(String mi) {
        Long time = Long.parseLong(mi);
        //productBomDetail
        List<String> Ids = opsSupplierDao.refreshSupplierData(time);
        for(String Id : Ids){
            opsRedisUtils.hDelete("ops:supplier:Id",Id);
        }
        if(Ids.size() == 0){//没有更新内容
            return null;
        }
        return Ids;
    }
}

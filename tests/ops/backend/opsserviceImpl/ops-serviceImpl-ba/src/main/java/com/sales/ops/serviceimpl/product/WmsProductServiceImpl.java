package com.sales.ops.serviceimpl.product;

import com.sales.ops.db.dao.ProductBomMapper;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.extdao.OpsPostWmsBomDao;
import com.sales.ops.service.product.WmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：富勒调用
 * @date ：Created in 2022/1/13 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WmsProductServiceImpl implements WmsProductService {

    @Autowired
    private OpsPostWmsBomDao opsPostWmsBomDao;

    @Autowired
    private ProductBomMapper productBomMapper;


    @Override
    public List<ProductBom> getBomByLimit (String limit){
        return opsPostWmsBomDao.getBomByLimit(Integer.parseInt(limit));
    }

    @Override
    public Integer updateBomStatus(ProductBom bom){
        return productBomMapper.updateByPrimaryKeySelective(bom);
    }



}

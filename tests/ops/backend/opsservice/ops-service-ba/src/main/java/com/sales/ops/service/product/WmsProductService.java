package com.sales.ops.service.product;

import com.sales.ops.db.dao.ProductBomMapper;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.extdao.OpsPostWmsBomDao;
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

public interface WmsProductService {

    public List<ProductBom> getBomByLimit (String limit);

    public Integer updateBomStatus(ProductBom bom);

}

package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.OrderModelInfoVO;
import com.smc.smccloud.model.productPrice.ProductPriceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
@Mapper
@DS("opsdb")
public interface ProductPriceMapper extends BaseMapper<ProductPriceDO> {


    @Select("<script>" +
            " SELECT TOP (1) a.ModelNo,a.min_pack_unit minPackQty,a.ChineseName as productName,b.EPrice " +
            " FROM product a inner join product_price b ON a.ModelNo = b.ModelNo " +
            " where a.ModelNo=#{modelNo} ORDER BY b.MinQuantity ASC "+
            "</script>")
    OrderModelInfoVO getModelInfoForOrder(@Param("modelNo") String modelNo);

}

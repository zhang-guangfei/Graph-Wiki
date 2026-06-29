package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.ProductPriceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
@DS("opsdb")
public interface ProductPriceMapper extends BaseMapper<ProductPriceDO> {


    @Select("select count(*) from product_price where is_deleted=0 and ModelNo=#{modelNo} and minQuantity>1")
    Integer hasLotProductPrice(String modelNo);

    @Select("select top 1 EPrice  from product_price where modelno=#{modelNo} and minQuantity<=#{lotQty} order by minQuantity Desc")
    BigDecimal getLotEPrice(String modelNo,Integer lotQty);

    /**
     * 查数量最小的E价
     * @param modelNo
     * @return
     */
    @Select("select top 1 EPrice  from product_price where modelno=#{modelNo} order by minQuantity asc")
    BigDecimal getEPriceOfMinQty(String modelNo);

    @Select("select top(1)* from product_price where modelNo = #{modelNo} and MinQuantity <= #{qty} order by MinQuantity Desc")
    ProductPriceDO getEpriceByModelNoAndMinQuantity(@Param("modelNo") String modelNo,@Param("qty") int qty);
}

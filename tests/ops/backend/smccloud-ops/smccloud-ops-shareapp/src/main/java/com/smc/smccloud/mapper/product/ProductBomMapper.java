package com.smc.smccloud.mapper.product;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import com.smc.smccloud.model.prestock.ProductBomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-04-07
 */
@DS("opsdb")
@Mapper
public interface ProductBomMapper extends BaseMapper<ProductBomDO> {

    @Select("SELECT sum(quantity - qtyImport) as quantity FROM ording_po_view WHERE customerNo = #{customerNo} AND ModelNo=#{modelNo}")
    Integer getModelInventoryOnHand(@Param("modelNo") String modelNo,@Param("customerNo") String customerNo);

    @Select("SELECT eprice FROM product_price WHERE modelNo=#{modelNo} AND MinQuantity = 1")
    BigDecimal getEprice(@Param("modelNo") String modelNo);

    @Select("SELECT quantity,prepare_quantity as prepareQuantity FROM ops_inventory WHERE modelno =#{modelNo} AND warehouse_code =#{warehouseCode}")
    List<OpsInventoryVO> listInventoryQtyByModelNo(@Param("modelNo") String modelNo,@Param("warehouseCode") String warehouseCode);

    @Select("SELECT min_pack_unit as minPackUnit FROM product WHERE ModelNo=#{modelNo}")
    Integer getMinPackUnit(@Param("modelNo") String modelNo);

    @Select("SELECT count(*) FROM Bindata WHERE modelno =#{modelNo} AND Stocktype = 1 AND delflag = 0")
    Integer getBinCountByModelNo(@Param("modelNo") String modelNo);

}

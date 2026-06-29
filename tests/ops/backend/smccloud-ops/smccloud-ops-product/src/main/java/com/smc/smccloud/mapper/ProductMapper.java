package com.smc.smccloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.ProductDO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.product.ProductPriceDTO;
import com.smc.smccloud.model.product.ProductRemark;

/**
 * Author: B90034 Date: 2022-01-26 15:57 Description:
 */
// bug10493删除product表中不用字段 2023/4/17
@DS("opsdb")
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {

	// bug-11611 2023/7/27
	@Select("<script>"
			+ " SELECT TOP 1 a.DesignTypeId, a.ModelNo, a.ECode, a.min_pack_unit as minPackUnit, a.ChineseName, "
            + " a.EnglishName, b.EPrice, b.LowestPrice as lotPrice "
			+ " FROM product a left join product_price b ON a.ModelNo = b.ModelNo "
			+ " where a.ModelNo=#{modelNo} order by b.MinQuantity ASC"
            + "</script>")
	ProductInfoVO getProductInfoByModelNo(@Param("modelNo") String modelNo);

	@Select("select EngName from product_ecode where Ecode=#{Ecode}")
	String getProductInvEngName(@Param("Ecode") String Ecode);

	@Select("SELECT TOP 1 p.EPrice,t.min_pack_unit as minPackUnit,s.netweight FROM product_price p LEFT JOIN product_physics s "
			+ "on p.ModelNo=s.ModelNo LEFT JOIN product t ON t.ModelNo = p.ModelNo WHERE p.ModelNo =#{modelNo}")
	ProductPriceDTO getModelData(@Param("modelNo") String modelNo);

	/**
	 * 根据型号查询产品备注信息
	 *
	 * @param modelNo 型号
	 * @return 产品备注信息
	 */
	@Select("select ModelNo, is_Even as isEven, min_pack_unit as minPackUnit from product where ModelNo=#{modelNo} and is_deleted = '0' ")
	ProductRemark getProductRemarkByModelNo(@Param("modelNo") String modelNo);

	@Select("{ call ops_sharedb.dbo.sync_prodmast_gp }")
	@Options(statementType = StatementType.CALLABLE)
	void importGPProductModel();

}

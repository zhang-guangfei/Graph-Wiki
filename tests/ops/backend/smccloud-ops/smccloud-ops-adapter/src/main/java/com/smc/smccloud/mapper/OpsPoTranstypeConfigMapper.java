package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.dto.po.core.PoTransTypeCoreDto;
import com.smc.smccloud.model.product.OpsPoTranstypeConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-12
 */
@Mapper
@DS("opsdb")
public interface OpsPoTranstypeConfigMapper extends BaseMapper<OpsPoTranstypeConfigDO> {

    @Select(" select * from ops_po_transtype_config ")
    List<OpsPoTranstypeConfig> getPoTransTypeConfigList();

    // 特殊尺寸
    @Select("SELECT nonstandard_sized_product from product where  is_deleted =0 and ModelNo = #{modelNo}")
    public String getPorductNonStandard(@Param("modelNo") String modelNo);

    // 获取温控器
    @Select("SELECT InspectionsGroupId  FROM ops_sharedb.dbo.MDM_V_InspectionsGroup where  Model = #{modelNo} and  InspectionsGroupId =1")
    public String getInspectionsGroup(@Param("modelNo") String modelNo);

    @Select("SELECT model_no ,trans_id ,compare_greater ,compare_quantity ,usage_type from  ops_po_transtype_core_config " +
            "where  delflag =0 and match_type=0  and model_no =  #{modelNo} ")
    public List<PoTransTypeCoreDto> getTransTypeCore(@Param("modelNo") String modelNo);

    @Select("SELECT model_no ,trans_id ,compare_greater ,compare_quantity ,usage_type from  ops_po_transtype_core_config " +
            "where  delflag =0 and match_type=1")
    public List<PoTransTypeCoreDto> getTransTypeMatchCore();
}

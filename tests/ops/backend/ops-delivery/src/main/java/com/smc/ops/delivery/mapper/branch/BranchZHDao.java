package com.smc.ops.delivery.mapper.branch;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.ops.delivery.model.branch.DelivyerCheckMoreCompanyDto;
import com.smc.ops.delivery.model.branch.StockAssemblyDetailPropertyDo;
import com.smc.ops.delivery.model.branch.StockAssemblyDetailViewDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/3 14:28
 */


@DS("sharedb")
public interface BranchZHDao {


    @Select(" SELECT ApplyId , detail_id ,ApplyNo ,IsTransOut ,Quantity ,AssembleType,ModelNo,detail_warehouseCode as warehouseCode  from stock_assembly_detail_view " +
            "where branch_flag is null and ApplyType =1 and OptCode =6 and create_time > '2025-01-01' ")
    List<StockAssemblyDetailViewDo> queryStockAssemblyDetailViewDo();

    @Select("SELECT  d.Quantity as detail_qty  ,d.branch_flag as detail_branch_flag,p.property_quantity as property_qty," +
            "p.del_flag as property_del_flag,p.company_id as property_company_id from " +
            "stock_assembly_detail d left join stock_assembly_detail_property p on d.id = p.detail_id  " +
            "where IsTransOut = 1 and  ApplyId =#{applyId} ")
    List<DelivyerCheckMoreCompanyDto> queryDelivyerCheckMoreCompany(@Param("applyId") Long applyId);

    @Select("SELECT TransTime  from stock_assembly where id =#{id} ")
    Date queryStockAssemblyOfTransTime(@Param("id") Long id );

    @Update("UPDATE stock_assembly_detail set branch_flag = #{branchFlag}  where id = #{id}  ")
    void updateStockAssemblyDetail(@Param("id") Long id , @Param("branchFlag") Integer branchFlag);

    @Insert("<script> insert into dbo.stock_assembly_detail_property  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            " <if test='detailId != null'> detail_id,</if>  <if test='modelNo != null'>  model_no,</if> " +
            " <if test='companyId != null'> company_id,</if>  <if test='quantity != null'> quantity, </if> " +
            " <if test='propertyQuantity != null'> property_quantity,</if>  " +
            " </trim> " +
            " <trim prefix='values (' suffix=')' suffixOverrides=','> " +
            " <if test='detailId != null'> #{detailId,jdbcType=BIGINT},  </if> " +
            " <if test='modelNo != null'>  #{modelNo,jdbcType=VARCHAR},  </if> " +
            " <if test='companyId != null'>  #{companyId,jdbcType=VARCHAR},  </if> " +
            " <if test='quantity != null'>  #{quantity,jdbcType=INTEGER},  </if> " +
            " <if test='propertyQuantity != null'>  #{propertyQuantity,jdbcType=INTEGER}, </if> " +
            " </trim>  </script>")
    public Integer insertProperty(StockAssemblyDetailPropertyDo record);
}

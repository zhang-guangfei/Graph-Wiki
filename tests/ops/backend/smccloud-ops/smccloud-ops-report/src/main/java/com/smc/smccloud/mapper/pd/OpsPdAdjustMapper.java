package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import com.smc.smccloud.model.pd.OpsPdAdjustDO;
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
 * @since 2024-08-07
 */
@Mapper
@DS("reportdb")
public interface OpsPdAdjustMapper extends BaseMapper<OpsPdAdjustDO> {


    @Select("select top(1)* from  ops_report.dbo.ops_pd_adjust where adjust_invoice_no = #{adjustInvoiceNo} order by id desc  ")
    List<OpsPdAdjustDO> getOpsAdJustByWarehouseCode(@Param("adjustInvoiceNo") String adjustInvoiceNo);


    @Select("<script>" +
            " select top(1000)* from  ops_report.dbo.ops_pd_adjust where hand_status = 0 " +
            " and pd_batch_no = #{pdBatchNo} " +
            "<if test=\"ids != null and ids.size() > 0\">" +
            " and id in " +
            " <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">" +
            "  #{id} " +
            " </foreach>" +
            " </if>" +
            "</script>")
    List<OpsPdAdjustDO> getNeedAdjustHandData(@Param("pdBatchNo") String pdBatchNo,
                                              @Param("ids") List<String> ids);
}

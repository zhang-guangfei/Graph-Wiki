package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryAdapterConfig;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.dto.inquiry.InquiryCodeConfigByParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dxf
 * @since 2024-1-24
 */
@DS("opsdb")
@Mapper
public interface InquiryCodeConfigMapper {


    @Select(" <script> select  * from ops_inquiry_code_config where code_type = #{codeType}  </script> ")
    List<OpsInquiryCodeConfig> selectSendConfig(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script> select  * from ops_inquiry_code_config where code_type = #{codeType}  and manu_reason_code is not null order by ops_reason_code </script> ")
    List<OpsInquiryCodeConfig> getManuCodeConfig(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script> select  * from ops_inquiry_code_config where code_type = #{codeType}  and as400_reason_code is not null </script> ")
    List<OpsInquiryCodeConfig> getAs400CodeConfig(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script> select  * from ops_inquiry_code_config where code_type = #{codeType}  and gz_reason_code is not null </script> ")
    List<OpsInquiryCodeConfig> getGzCodeConfig(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script>   select ops_inquiry_code_config.*,supplier_id,supplier_name\n" +
            ",param_name,param_type,data_length,is_fixed_length,other_type_info,param_remark \n" +
            "from ops_inquiry_code_config left join ops_inquiry_reason_param_config on ops_inquiry_code_config.ops_reason_code = ops_inquiry_reason_param_config.ops_reason_code where code_type = #{codeType}  </script> ")
    List<InquiryCodeConfigByParam> selectSendConfigByParam(@Param("codeType") String codeType); // 取出原因字典


    @Select(" <script> select * from ops_inquiry_adapter_config where supplier_id = #{supplierId} and is_deleted ='0'   </script> ")
    List<OpsInquiryAdapterConfig> getAdapterBySupplier(@Param("supplierId") String supplierId);

}

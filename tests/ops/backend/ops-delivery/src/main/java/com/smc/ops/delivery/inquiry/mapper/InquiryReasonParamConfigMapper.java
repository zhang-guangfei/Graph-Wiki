package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryReasonParamConfig;
import com.sales.ops.dto.inquiry.InquiryCodeConfigByParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
@DS("opsdb")
@Mapper
public interface InquiryReasonParamConfigMapper {


    @Select(" <script>  select * from ops_inquiry_reason_param_config where is_deleted = '0'  </script> ")
    List<OpsInquiryReasonParamConfig> getReasonParamConfig();

    @Select(" <script>   select ops_inquiry_code_config.*,supplier_id,supplier_name\n" +
            ",param_name,param_type,data_length,is_fixed_length,other_type_info,param_remark \n" +
            "from ops_inquiry_code_config left join ops_inquiry_reason_param_config on ops_inquiry_code_config.ops_reason_code = ops_inquiry_reason_param_config.ops_reason_code where code_type = #{codeType}  and manu_reason_code is not null order by ops_reason_code  </script> ")
    List<InquiryCodeConfigByParam> getManuCodeConfigByParam(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script>   select ops_inquiry_code_config.*,supplier_id,supplier_name\n" +
            ",param_name,param_type,data_length,is_fixed_length,other_type_info,param_remark \n" +
            "from ops_inquiry_code_config left join ops_inquiry_reason_param_config on ops_inquiry_code_config.ops_reason_code = ops_inquiry_reason_param_config.ops_reason_code where code_type = #{codeType}  and as400_reason_code is not null order by ops_reason_code  </script> ")
    List<InquiryCodeConfigByParam> getAs400CodeConfigByParam(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script>   select ops_inquiry_code_config.*,supplier_id,supplier_name\n" +
            ",param_name,param_type,data_length,is_fixed_length,other_type_info,param_remark \n" +
            "from ops_inquiry_code_config left join ops_inquiry_reason_param_config on ops_inquiry_code_config.ops_reason_code = ops_inquiry_reason_param_config.ops_reason_code where code_type = #{codeType}  and gz_reason_code is not null order by ops_reason_code  </script> ")
    List<InquiryCodeConfigByParam> getGzCodeConfigByParam(@Param("codeType") String codeType); // 取出原因字典

    @Select(" <script>  select * from ops_inquiry_reason_param_config where is_deleted = '0'  and ops_reason_code = #{ops_reason_code}  and  supplier_id = #{supplier_id} </script> ")
    List<OpsInquiryReasonParamConfig> getReasonParamConfigBySuppily(@Param("ops_reason_code") String ops_reason_code,@Param("supplier_id") String supplier_id);

}

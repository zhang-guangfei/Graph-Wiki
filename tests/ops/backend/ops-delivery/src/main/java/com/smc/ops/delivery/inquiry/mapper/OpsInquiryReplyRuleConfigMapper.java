package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.inqa.OpsInquiryReplyRuleConfigDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface OpsInquiryReplyRuleConfigMapper  extends BaseMapper<OpsInquiryReplyRuleConfigDo>  {


//    @Select("<script>select * from ops_inquiry_reply_rule_config where is_deleted = 0 and service_type=#{serviceType}  AND supplier_id in (select supplier_class from supplier where id = #{supplierId}) ORDER BY supplier_id,rule_type,priority </script>")
@Select("<script>select * from ops_inquiry_reply_rule_config where is_deleted = 0 and service_type=#{serviceType}  AND supplier_id = #{supplierId} ORDER BY supplier_id,rule_type,priority </script>")
public List<OpsInquiryReplyRuleConfigDo> getRulesByServiceTypeAndRuleType(String serviceType, String supplierId);


}
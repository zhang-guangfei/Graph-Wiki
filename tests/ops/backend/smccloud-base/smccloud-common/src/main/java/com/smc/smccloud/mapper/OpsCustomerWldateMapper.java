package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Customer.OpsCustomerWldateDO;
import com.smc.smccloud.model.customer.CustomerWldateRequest;
import com.smc.smccloud.model.customer.OpsCustomerWldateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 纳期客户清单表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-12-06
 */
@Mapper
@DS("opsdb")
public interface OpsCustomerWldateMapper extends BaseMapper<OpsCustomerWldateDO> {

    // update bu LiYingChao from bug 8759 in 2022-12-26
    @Select("<script>" +
            "select c.HLCode,c.HRUnitId,w.customer_no,c.name as customerName, w.del_flag, c.PSNSMCID,w.is_wldate,w.modify_time,w.modifier,o.Name as deptName " +
            " from ops_customer_wldate w left join ops_customer c on w.customer_no = c.customer_no " +
            " left join ops_ui.dbo.hr_organization o on ISNULL(c.HLCode, c.HRUnitId) = o.id " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test = 'request.customerNo != null and request.customerNo != \"\" ' >" +
            " ( w.customer_no = #{request.customerNo} or c.name = #{request.customerNo} ) and " +
            "</if> " +
            " w.del_flag = 0 "+
            "</trim>" +
            " order by w.modify_time desc " +
            "</script>")
    List<OpsCustomerWldateVO> getCustomerWldateList(@Param("request")CustomerWldateRequest request);


}

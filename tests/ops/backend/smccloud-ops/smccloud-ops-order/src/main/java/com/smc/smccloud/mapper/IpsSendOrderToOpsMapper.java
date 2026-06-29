package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import com.smc.smccloud.model.order.IpsSendOrderToOpsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * ips制造接单
 * </p>
 *
 * @author smc
 * @since 2024-12-25
 */
@Mapper
@DS("ipssharedb")
public interface IpsSendOrderToOpsMapper extends BaseMapper<IpsSendOrderToOpsDO> {


    @Select("<script>" +
            " select * from ips_send_order_to_ops where " +
            " <if test=' id != null '>" +
            "   id > #{id} " +
            " </if>  " +
            "</script>")
    List<IpsSendOrderToOpsDO> getManuOrderFromIpstoOps(String id);
}

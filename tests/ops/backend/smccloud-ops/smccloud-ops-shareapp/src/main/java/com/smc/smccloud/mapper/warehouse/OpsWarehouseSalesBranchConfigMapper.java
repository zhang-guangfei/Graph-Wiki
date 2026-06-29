package com.smc.smccloud.mapper.warehouse;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.CalTransDayEntity;
import com.smc.smccloud.model.prestock.OpsWarehouseSalesbranchConfigDO;
import com.smc.smccloud.model.warehouse.WareHouseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-27 16:09
 * Description:
 */
@DS("opsdb")
@Mapper
public interface OpsWarehouseSalesBranchConfigMapper extends BaseMapper<OpsWarehouseSalesbranchConfigDO> {

    /**
     * 根据营业所代码/代理店查询出库规则
     *
     * @param salesBranchId 营业所代码/代理店
     * @return 出库规则列表
     */
    @Select("select sales_branch_id, warehouse_code, priority, description, delivery_day " +
            " from ops_warehouse_salesbranch_config" +
            " where sales_branch_id=#{salesBranchId} and delflag=0 order by priority ")
    List<OpsWarehouseSalesbranchConfigDO> findOpsWarehouseSalesBranchConfigBySalesBranchId(@Param("salesBranchId") String salesBranchId);


    @Select(" select a.sales_branch_id,a.warehouse_code,a.priority,a.delivery_day,w.warehouse_type\n" +
            " from ops_warehouse_salesbranch_config a INNER JOIN ops_warehouse w on a.warehouse_code = w.warehouse_code\n" +
            " where a.sales_branch_id = #{deptNo} and a.delflag=0 and w.warehouse_type = 'MASTER' order by a.priority asc")
    List<CalTransDayEntity> getWareHouseType(@Param("deptNo") String deptNo);


    @Select(" select a.warehouse_code as wareHouseCode,a.priority,w.warehouse_type as wareHouseType,w.adress as wareHouseAddress ,w.warehouse_name as wareHouseName, \n" +
            " w.linkman as linkMan ,w.mail as linkMail, w.link_phone as linkPhone , w.link_mobile as linkMobile , w.rcv_linkman as rcvLinkMan, w.rcv_link_tel as rcvLinkTel, w.rcv_link_email as rcvLinkEmail "+
            " from ops_warehouse_salesbranch_config a INNER JOIN ops_warehouse w on a.warehouse_code = w.warehouse_code\n" +
            " where a.sales_branch_id = #{deptNo} and a.delflag=0 and w.warehouse_type = 'MASTER' order by a.priority asc")
    List<WareHouseInfo> getWareHouseInfoByDeptNo(@Param("deptNo") String deptNo);

}

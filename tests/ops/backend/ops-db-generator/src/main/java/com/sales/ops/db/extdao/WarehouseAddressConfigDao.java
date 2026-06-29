package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsWarehouseAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：仓库多地址维护
 * @date ：Created in 2023/7/24 8:49
 */
public interface WarehouseAddressConfigDao {


    @Select("SELECT top 1 owa.id,owa.warehouse_code,owa.province,owa.city ,owa.district ,owa.adress ,owa.post_code,owa.linkman," +
            "owa.link_phone ,owa.link_mobile ,owa.english_address ,owa.english_linkman , owa.mail " +
            "from ops_warehouse_address_config owac " +
            "join ops_warehouse_address owa on owac.address_id = owa.id " +
            "where owac.delflag = 0 and owa.delflag = 0 and owac.warehouse_code = #{warehouseCode} and owac.do_type = #{doType}")
    OpsWarehouseAddress getWarehouseAddress(@Param("warehouseCode") String warehouseCode,  @Param("doType") String doType);

    @Insert("INSERT INTO ops_warehouse_address (warehouse_code,province,city,district,adress,post_code,linkman,link_phone, " +
            "link_mobile,english_address,english_linkman,mail,delflag,create_time,creator) VALUES " +
            " (#{warehouseCode},#{province},#{city},#{district},#{adress},#{postCode;},#{linkman}," +
            " #{linkPhone},#{linkMobile},#{englishAddress},#{englishLinkman},#{mail},#{delflag},#{createTime},#{creator})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertWarehouseAddress(OpsWarehouseAddress address);

    @Select("SELECT do_type from ops_warehouse_address_config where delflag = 0 and warehouse_code = #{warehouseCode}" +
            " and address_id = #{addressId}")
    List<String> getWarehouseAddressConfig(@Param("warehouseCode") String warehouseCode, @Param("addressId") Long addressId);

}

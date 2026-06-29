package com.sales.ops.db.extdao;

import com.sales.ops.dto.ba.Address;
import org.apache.ibatis.annotations.Select;

public interface OpsAddressDao {


    @Select("select province,city,region,address,postcode" +
            " from ops_adress " +
            "where delflag=0 and id=#{id}")
    Address selectAddressById(int id);
}

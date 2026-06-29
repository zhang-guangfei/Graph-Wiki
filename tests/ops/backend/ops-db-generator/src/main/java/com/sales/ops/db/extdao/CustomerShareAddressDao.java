package com.sales.ops.db.extdao;

import com.sales.ops.dto.customer.CustomerPostalAddress;
import com.sales.ops.dto.customer.NationalArea;

import java.util.List;

public interface CustomerShareAddressDao {

    List<CustomerPostalAddress> selectCustomerPostalAddressByCustomerNo(List<String> customerNos);

    List<NationalArea> selectAreaNameByCityCode(List<String> cityCodes);
}

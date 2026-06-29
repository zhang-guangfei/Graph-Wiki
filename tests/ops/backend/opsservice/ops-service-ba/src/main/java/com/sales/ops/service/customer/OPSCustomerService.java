package com.sales.ops.service.customer;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.Customer;
import com.sales.ops.dto.customer.CustomerInformation;
import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsCustomerWarehouse;
import com.sales.ops.dto.ba.CustomerInfo;
import com.sales.ops.dto.util.PageModel;

import java.util.List;
import java.util.Map;

public interface OPSCustomerService {

    CustomerInfo findCustomerInfoByNo(String customerNo);

    List<String> refreshCustomerAddress(String mi);

    List<OpsCustomer> findCustomerInfoByName(String customerName);

    List<OpsCustomerWarehouse> searchOpsCustomerWarehouse(String customerNo);

    List<String> refreshOpsCustomerWarehouseData(String mi);

    List<Customer> findCustomerInfoByNoOrName(String customerNo);

    PageInfo<CustomerInformation> findCustomerInfoByCustomerNo(PageModel<String> page);

    List<Map<String, String>> findCustomerByCustomerNoOrName(String customerNoOrName);
}

package com.acme.customer.service.impl;

import com.acme.customer.mapper.CustomerMapper;

public class CustomerServiceImpl {
    private final CustomerMapper customerMapper = new CustomerMapper();

    public void createCustomer(String customerCode) {
        customerMapper.saveCustomer(customerCode);
    }

    public void updateCustomerLevel(String customerCode) {
        customerMapper.updateCustomerLevel(customerCode);
    }

    public void searchCustomers() {
        customerMapper.findCustomers();
    }
}

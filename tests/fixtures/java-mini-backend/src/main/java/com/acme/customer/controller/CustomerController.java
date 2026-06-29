package com.acme.customer.controller;

import com.acme.customer.service.impl.CustomerServiceImpl;

public class CustomerController {
    private final CustomerServiceImpl customerService = new CustomerServiceImpl();

    public void createCustomer(String customerCode) {
        customerService.createCustomer(customerCode);
    }

    public void updateCustomerLevel(String customerCode) {
        customerService.updateCustomerLevel(customerCode);
    }

    public void searchCustomers() {
        customerService.searchCustomers();
    }
}

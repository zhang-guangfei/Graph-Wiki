package com.smc.smccloud.model.Employee;

import lombok.Data;

@Data
public class EmployeeVO {

    private String id;

    private String name;

    private String sex;

    private String cellPhone;

    private String email;

    private String status;


    private int pageNumber;
    private int pageSize;

}

package com.smc.smccloud.model.Employee;

import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/12/12 08:56
 * @description
 */
@Data
public class EmployeePositionVO {

    public String employeeId ;

    public String orgId ;

    public String positionId ;

    public String isPrimaryPosition ;

    public String status ;

    public String isFuZeRen ;

    public Boolean isDeleted ;
}

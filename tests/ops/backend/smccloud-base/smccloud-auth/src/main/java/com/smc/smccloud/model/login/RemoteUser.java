package com.smc.smccloud.model.login;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RemoteUser implements Serializable
{
    private static final long serialVersionUID = 8162342822116363747L;
    private String userId;
    private String username;
    private String password;
    private String psnName;
    private String emId;
    private String status;
    private String type;
    private List<EmployeePosition> employeePositions = new ArrayList();
}

package com.smc.smccloud.service;

import com.smc.smccloud.model.login.EmployeePosition;

import java.util.List;
import java.util.Set;

public interface PositionService
{
     List<EmployeePosition> filterAfterPosition(String username, List<EmployeePosition> positions, boolean bool);

     Set<String> filterAfterRole(String username, List<EmployeePosition> positions);
}

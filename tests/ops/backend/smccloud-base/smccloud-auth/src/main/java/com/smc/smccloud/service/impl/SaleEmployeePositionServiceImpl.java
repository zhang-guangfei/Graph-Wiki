package com.smc.smccloud.service.impl;

import com.smc.smccloud.model.login.EmployeePosition;
import com.smc.smccloud.model.login.SaleEmployeePosition;
import com.smc.smccloud.service.employeeAndOrgan.SaleEmployeePositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleEmployeePositionServiceImpl implements SaleEmployeePositionService {
    @Override
    public List<EmployeePosition> findEmployeePositionsByEmployeeId(String employeeId) {
        return null;
    }

    @Override
    public List<SaleEmployeePosition> findApprover(String employeeId, String orgId, String businessType) {
        return null;
    }

    @Override
    public List<SaleEmployeePosition> findAllEmployeePositions() {
        return null;
    }

    @Override
    public List<EmployeePosition> findAllKdEmployeePositions() {
        return null;
    }
}

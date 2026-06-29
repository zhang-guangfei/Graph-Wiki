package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.DepartmentMapper;
import com.smc.smccloud.mapper.TblWorkdayYearMapper;
import com.smc.smccloud.model.Adapter.Department;
import com.smc.smccloud.model.DepartmentDO;
import com.smc.smccloud.service.AdapterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdapterServiceImpl implements AdapterService {

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private TblWorkdayYearMapper tblWorkdayYearMapper;

    @Override
    public ResultVo<List<Department>> findSaleDeptCommon(DataAuthoritySearchCondition condition, String deptNo) {
        if (PublicUtil.isEmpty(deptNo)) {
            return ResultVo.failure("部门代码不能为空");
        }

        List<String> deptNos = condition.getDeptCodeListByDataAuthority();
        if (deptNos == null) {
            deptNos = new ArrayList<>();
        }
        deptNos.add(deptNo);
        deptNos.add("220000");
        deptNos.add("240000");
        deptNos.add("239900");

        LambdaQueryWrapper<DepartmentDO> queryWrapper;
        List<Department> departmentList = new ArrayList<>(deptNos.size());
        Department department;

        for (String no : deptNos) {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DepartmentDO::getDeptNo, no);
            List<DepartmentDO> list = departmentMapper.selectList(queryWrapper);

            for (DepartmentDO departmentDO : list) {
                department = new Department();
                department.setDeptNo(departmentDO.getDeptNo());
                department.setHrUnitId(departmentDO.getOldDeptNo());
                department.setDeptDesc(departmentDO.getDeptName());
                department.setAddress(departmentDO.getAddress());
                department.setTeleNo(departmentDO.getTeleNo());
                department.setEmail_Sample(departmentDO.getEmailAddr());
                department.setSuperIntendent(departmentDO.getSuperIntendent());
                if (PublicUtil.isNotEmpty(departmentDO.getDlvDay())) {
                    department.setDlvFlag(String.valueOf(departmentDO.getDlvDay()));
                }
                departmentList.add(department);
            }
        }

        return ResultVo.success(departmentList);
    }

    @Override
    public Date calDlvDate(String deliveryDate, String today) {

        Date dlyDate = tblWorkdayYearMapper.calDlvDate(deliveryDate);

        Date toDay = DateUtil.stringToDate(today);
        // 若返回日期小于当天，则设置为当天发货
        if (dlyDate.before(toDay)) {
            dlyDate = toDay;
        }
        return dlyDate;
    }

    @Override
    public void syncWorkDay() {
        tblWorkdayYearMapper.syncWorkDay();
    }
}

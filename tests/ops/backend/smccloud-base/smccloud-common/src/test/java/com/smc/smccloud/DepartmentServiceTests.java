package com.smc.smccloud;

import com.smc.smccloud.mapper.HROrganizationMapper;
import com.smc.smccloud.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-06-21 10:49
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentServiceTests {

    @Resource
    private HROrganizationMapper hrOrganizationMapper;
    @Resource
    private DepartmentService departmentService;

    @Test
    public void getDeptNoByHRSalesDeptNoTest() {
        log.info("getDeptNoByHRSalesDeptNo {}", departmentService.getDeptNoByHRSalesDeptNo("223141"));
    }

    @Test
    public void getParentNumberByDeptNoTest() {
        log.info("getParentNumberByDeptNoTest {} ", departmentService.getParentNumberByDeptNo("235106"));
    }

    @Test
    public void getDeptTreeByDeptNo() {
        log.info("deptTree = {}", departmentService.getDeptTreeByNo(null));
    }

}

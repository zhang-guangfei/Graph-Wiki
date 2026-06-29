package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.smc.smccloud.model.EmployeeDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-07-27 17:01
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTests {

    @Resource
    private EmployeeService employeeService;

    @Test
    public void getEmployeeByDeptNoTest() {
        log.info("getEmployeeByDeptNoTest = {}", employeeService.getEmployeeCodeByDeptNo("235000"));
    }

    @Test
    public void test() {
        List<EmployeeDO> empList = new ArrayList<>();
        EmployeeDO emp1 = new EmployeeDO();
        emp1.setId("emp1");
        EmployeeDO emp2 = new EmployeeDO();
        emp1.setId("emp2");
        EmployeeDO emp3 = new EmployeeDO();
        emp1.setId("emp3");
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);

        Map<String, Object> map = empList.stream().collect(Collectors.toMap(EmployeeDO::getId, v -> JSON.toJSONString(v), (a, b) -> a));
        log.info("map = {}", map);
    }
}

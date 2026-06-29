package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.core.model.enums.InvoiceTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.EmployeeMapper;
import com.smc.smccloud.mapper.EmployeePositionMapper;
import com.smc.smccloud.model.Employee.EmployeePositionVO;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.EmployeeDO;
import com.smc.smccloud.model.EmployeePositionDO;
import com.smc.smccloud.model.customer.CstoAndUserInfoVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.CustomerService;
import com.smc.smccloud.service.EmployeeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-22 11:54
 * Description:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeePositionMapper employeePositionMapper;
    @Resource
    private CustomerService customerService;
    @Resource
    private RedisManager redisManager;

    @Override
    public ResultVo<PageInfo<EmployeeVO>> findEmployeeInfoByIdOrName(EmployeeVO employeeVO) {
        QueryWrapper<EmployeeDO> query = new QueryWrapper<>();
        query.eq("Status", 1);
        query.like("Id", employeeVO.getId());
        query.like("Name", employeeVO.getName());

        PageInfo<EmployeeVO> pageInfo = PageHelper.startPage(employeeVO.getPageNumber(), employeeVO.getPageSize())
                .doSelectPageInfo(() -> employeeMapper.selectList(query));

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> getEmployeeNameByNo(String employeeNo) {
        if (StringUtils.isBlank(employeeNo)) {
            return ResultVo.failure("该员工编号不存在");
        }
        ResultVo<EmployeeVO> resultVo = this.getEmployeeInfo(employeeNo);
        if (!resultVo.isSuccess()) {
            return ResultVo.failure(resultVo.getMessage());
        }
        return ResultVo.success(resultVo.getData().getName());
    }

    @Override
    public ResultVo<EmployeeVO> getEmployeeInfo(String employeeNo) {
        if (StringUtils.isBlank(employeeNo)) {
            return ResultVo.failure("该员工编号不存在");
        }
        Object employee = redisManager.hGet(Constants.REDIS_EMPLOYEE_INFO_ALL, employeeNo);
        if (employee == null) {
            LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EmployeeDO::getId, employeeNo);
            List<EmployeeDO> list = employeeMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                return ResultVo.failure("该员工编号不存在");
            }
            EmployeeDO employeeDO = list.get(0);
            redisManager.hPut(Constants.REDIS_EMPLOYEE_INFO_ALL, employeeNo, JSON.toJSONString(employeeDO));
            EmployeeVO employeeVO = BeanCopyUtil.copy(employeeDO, EmployeeVO.class);
            return ResultVo.success(employeeVO);
        } else {
            EmployeeVO employeeVO = JSONObject.parseObject(employee.toString(), EmployeeVO.class);
            return ResultVo.success(employeeVO);
        }
    }
    @Override
    public ResultVo<EmployeePositionVO> getEmployeePosition(String employeeId){
        if (StringUtils.isBlank(employeeId)) {
            return ResultVo.failure("请输入职位代码。");
        }
        LambdaQueryWrapper<EmployeePositionDO> queryWrap=new LambdaQueryWrapper<>();
        queryWrap.eq(EmployeePositionDO::getEmployeeId,employeeId)
                .eq(EmployeePositionDO::getIsDeleted,0);
       EmployeePositionDO employeePositionDO=  employeePositionMapper.selectOne(queryWrap);
       EmployeePositionVO vo =BeanCopyUtil.copy(employeePositionDO, EmployeePositionVO.class);
       return ResultVo.success(vo) ;
    }
    @Override
    // @Cacheable(cacheNames = "getCstoAndUserInfo", key = "'ops:userInfo:'+#p0+#p1+#p2+#p3+':cstoAndUser'", unless = "#result==null")
    public ResultVo<CstoAndUserInfoVO> getCstoAndUserInfo(String customerNo, String userNo, String empSale, String endUserNo) {
        CstoAndUserInfoVO cstoAndUserInfoVO = new CstoAndUserInfoVO();

        // 客户名称
        if (StringUtils.isNotBlank(customerNo)) {
            CustomerVO customerVO = customerService.getCustomerByCustomerNo(customerNo);
            if (customerVO != null && StringUtils.isNotBlank(customerVO.getName())) {
                cstoAndUserInfoVO.setCustomerName(customerVO.getName());
                cstoAndUserInfoVO.setInvoiceTypeName(InvoiceTypeEnum.getCodeName(customerVO.getInvoiceType()));
                cstoAndUserInfoVO.setCstmType(customerVO.getCustomerType());
            }
        }

        // 如果客户代码与用户代码一致 则客户名称 = 用户名称
        if (StringUtils.isNotBlank(customerNo) && StringUtils.isNotBlank(userNo) && customerNo.equals(userNo)) {
            cstoAndUserInfoVO.setUserName(cstoAndUserInfoVO.getCustomerName());
        }

        // 用户名称
        if (StringUtils.isBlank(cstoAndUserInfoVO.getUserName()) && StringUtils.isNotBlank(userNo)) {
            CustomerVO customerVO = customerService.getCustomerByCustomerNo(userNo);
            if (customerVO != null && StringUtils.isNotBlank(customerVO.getName())) {
                cstoAndUserInfoVO.setUserName(customerVO.getName());
            }
        }

        if (StringUtils.isNotBlank(endUserNo) && StringUtils.isNotBlank(userNo) && endUserNo.equals(userNo)) {
            cstoAndUserInfoVO.setEndUserName(cstoAndUserInfoVO.getUserName());
        }

        // 最终用户名称
        if (StringUtils.isBlank(cstoAndUserInfoVO.getEndUserName()) && StringUtils.isNotBlank(endUserNo)) {
            CustomerVO customerVO = customerService.getCustomerByCustomerNo(endUserNo);
            if (customerVO != null && StringUtils.isNotBlank(customerVO.getName())) {
                cstoAndUserInfoVO.setEndUserName(customerVO.getName());
            }
        }

        // 客户担当名称
        if (StringUtils.isNotBlank(empSale)) {
            ResultVo<String> employeeName = this.getEmployeeNameByNo(empSale);
            if (employeeName.isSuccess() && StringUtils.isNotBlank(employeeName.getData())) {
                cstoAndUserInfoVO.setEmpSaleName(employeeName.getData());
            }
        }
        return ResultVo.success(cstoAndUserInfoVO);
    }

    @Override
    public ResultVo<EmployeeVO> getIndManageInfoByDeptNo(String deptNo) {
        EmployeeVO employeeInfo = employeeMapper.getIndManageInfoByDeptNo(deptNo);
        return ResultVo.success(employeeInfo);
    }

    @Override
    public ResultVo<CodeName> getEmployeeCodeByDeptNo(String deptNo) {
        CodeName codeInfo = new CodeName(deptNo, deptNo);

        List<EmployeeVO> employeeVOList = employeeMapper.getEmployeeByDeptNo(deptNo);
        List<CodeName> codeList = new ArrayList<>(employeeVOList.size());
        CodeName employeeCode;
        for (EmployeeVO employeeInfo : employeeVOList) {
            employeeCode = new CodeName(employeeInfo.getId(), employeeInfo.getName());
            codeList.add(employeeCode);
        }
        codeInfo.setChildren(codeList);

        return ResultVo.success(codeInfo);
    }

    @Override
    public void cacheAllEmployee() {

        // 获取所有职员信息
        List<EmployeeDO> list = employeeMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (EmployeeDO info : list) {
            boolean b = redisManager.hHasKey(Constants.REDIS_EMPLOYEE_INFO_ALL, info.getId());
            if (b) {
                redisManager.hdel(Constants.REDIS_EMPLOYEE_INFO_ALL, info.getId());
            }
            redisManager.hPut(Constants.REDIS_EMPLOYEE_INFO_ALL, info.getId(), JSON.toJSONString(info));
        }

    }
}

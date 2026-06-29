package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.model.CarrierVO;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.Employee.EmployeePositionVO;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.HROrganizationVO;
import com.smc.smccloud.model.OpsWarehouseSalesbranchConfigVO;
import com.smc.smccloud.model.customer.CstoAndUserInfoVO;
import com.smc.smccloud.model.customer.CustomerParam;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.customer.TblGroupcustomerVO;
import com.smc.smccloud.model.supplier.SupplierRequest;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class CommonServiceFeignClient implements CommonServiceFeignApi {

    @Resource
    private CommonService commonService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private CustomerService customerService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private AdapterService adapterService;
    @Resource
    private CarrierService carrierService;
    @Resource
    private GroupCompanyService groupCompanyService;
    @Resource
    private TblGroupcustomerService tblGroupcustomerService;
    @Resource
    private RedissonUtil redissonUtil;

    @Override
    public ResultVo<String> generatorBillNo(String billType) {
        String key = "ops:rediss:billNo:create:" + billType;
        ResultVo<String> billNoResult = null;
        try {
            if (redissonUtil.tryLock(key, 60, 30, TimeUnit.SECONDS)) {
                billNoResult = commonService.generatorBillNo(billType);
            }
        } finally {
            redissonUtil.unlock(key);
        }
        return billNoResult;
    }

    @Override
    public ResultVo<Set<String>> batchGeneratorBillNo(String billType, Integer number) {
        return commonService.batchGeneratorBillNo(billType, number);
    }

    @Override
    public ResultVo<PageInfo<EmployeeVO>> findEmployeeInfoByIdOrName(EmployeeVO employeeVO) {
        return employeeService.findEmployeeInfoByIdOrName(employeeVO);
    }

    @Override
    public ResultVo<String> getEmployeeNameByNo(String employeeNo) {
        return employeeService.getEmployeeNameByNo(employeeNo);
    }

    @Override
    public ResultVo<EmployeeVO> getIndManageInfoByDeptNo(String deptNo) {
        return employeeService.getIndManageInfoByDeptNo(deptNo);
    }

    @Override
    public ResultVo<CodeName> getEmployeeCodeByDeptNo(String deptNo) {
        return employeeService.getEmployeeCodeByDeptNo(deptNo);
    }

    @Override
    public ResultVo<List<WarehouseVO>> listWarehouse(WarehouseQueryDTO dto) {
        return warehouseService.listWarehouse(dto);
    }

    @Override
    public ResultVo<WarehouseVO> getWarehouseInfoByCode(String warehouseCode) {
        return warehouseService.getWarehouseInfoByCode(warehouseCode);
    }
    @Override
    public ResultVo<String> getWarehouseName(String warehouseCode) {
        return warehouseService.getWarehouseName(warehouseCode);
    }

    @Override
    public ResultVo<CustomerVO> findCustomerByCustomerNo(String customerNo) {
        CustomerVO customerVO = customerService.getCustomerByCustomerNo(customerNo);
        if (customerVO != null) {
            return ResultVo.success(customerVO);
        }
        return ResultVo.failure("未查到客户信息");
    }

    @Override
    public List<CustomerVO> findCustomerListByCustomerNos(CustomerParam customerParam) {
        return customerService.getCustomerListInfoByCustomerNos(customerParam);
    }

    @Override
    public ResultVo<String> getCustomerNameByNo(String customerNo) {
        return customerService.getCustomerNameByNo(customerNo);
    }

    @Override
    public ResultVo<Integer> getDepartmentDlvDayByDeptNo(String deptNo) {
        return departmentService.getDepartmentDlvDayByDeptNo(deptNo);
    }

    @Override
    public ResultVo<String> getCustomerENameByNo(String customerNo) {
        return customerService.getCustomerENameByNo(customerNo);
    }

    @Override
    public ResultVo<DepartmentVO> getDepartmentInfo(String deptNo) {
        return departmentService.getDepartmentInfo(deptNo);
    }

    @Override
    public ResultVo<DepartmentVO> getDeptNoByOldNo(String oldDeptNo) {
        return departmentService.getDeptNoByOldNo(oldDeptNo);
    }

    @Override
    public ResultVo<String> getDeptNameByNo(String deptNo) {
        return departmentService.getDeptNameByNo(deptNo);
    }

    @Override
    public ResultVo<List<SupplierVo>> findSupplierInfo() {
        return supplierService.findSupplierInfo();
    }

    @Override
    public ResultVo<PageInfo<SupplierVo>> findSupplierList(SupplierRequest request) {
        return supplierService.findSupplierList(request);
    }

    @Override
    public ResultVo<List<SupplierVo>> findSupplierByIdOrName(String companyId, String name) {
        return supplierService.findSupplierByIdOrName(companyId, name);
    }

    @Override
    public ResultVo<List<SupplierVo>> findChinaSuppliers() {
        return supplierService.findChinaSuppliers();
    }

    @Override
    public ResultVo<String> updateSupplierData(SupplierVo supplierVo) {
        return supplierService.updateSupplierData(supplierVo);
    }

    @Override
    public ResultVo<String> getSupplierName(String supplierCode) {
        return supplierService.getSupplierName(supplierCode);
    }

    @Override
    public ResultVo<SupplierVo> findSupplierById(String id) {
        return supplierService.findSupplierById(id);
    }


    @Override
    public ResultVo<EmployeeVO> getEmployeeInfo(String employeeNo) {
        return employeeService.getEmployeeInfo(employeeNo);
    }
    @Override
     public  ResultVo<EmployeePositionVO> getEmployeePosition(String employeeId){
        return employeeService.getEmployeePosition(employeeId);
    }
    @Override
    public ResultVo<List<CustomerVO>> findCustomerInfoByNoOrName(String customerNo) {
        List<CustomerVO> list = customerService.findCustomerInfoByNoOrName(customerNo);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> getSixDeptNoByTwoDeptNo(String deptNo) {
        return departmentService.getSixDeptNoByTwoDeptNo(deptNo);
    }

    @Override
    public ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo) {
        return departmentService.getDeptNoByHRSalesDeptNo(hrSalesDeptNo);
    }

    @Override
    public ResultVo<String> getParentNumberByDeptNo(String deptNo) {
        return departmentService.getParentNumberByDeptNo(deptNo);
    }

    @Override
    public Date calDlvDate(String deliveryDate, String today) {
        return adapterService.calDlvDate(deliveryDate, today);
    }

    @Override
    public ResultVo<List<DepartmentVO>> listDepartment() {
        return departmentService.listDepartment();
    }

    @Override
    public ResultVo<List<WarehouseVO>> listWarehouseNoWT() {
        return warehouseService.listWarehouseNoWT();
    }
    @Override
    public ResultVo<List<WarehouseVO>> getSubWarehouse(String warehouseCode) {
        return warehouseService.getSubWarehouse(warehouseCode);
    }

    @Override
    public ResultVo<List<WarehouseVO>> getWarehouseByType(String wareHouseType){
        return warehouseService.getWarehouseByType(wareHouseType);
    }
    @Override
    public ResultVo<CarrierVO> findCarrierInfoById(String carrierId) {
        return carrierService.findCarrierInfoById(carrierId);
    }

    @Override
    public ResultVo<String> findcustGroupIdByName(String custName) {
        return groupCompanyService.findcustGroupIdByName(custName);
    }

    @Override
    public ResultVo<TblGroupcustomerVO> getTblGroupCustInfo(String customerNo) {
        return supplierService.getTblGroupCustInfo(customerNo);
    }

    @Override
    public ResultVo<CstoAndUserInfoVO> getCstoAndUserInfo(String customerNo, String userNo, String empSale, String endUserNo) {
        return employeeService.getCstoAndUserInfo(customerNo, userNo, empSale, endUserNo);
    }

    @Override
    public ResultVo<String> getDlvDeptNoByNo(String customerNo) {
        return tblGroupcustomerService.getDlvDeptNoByNo(customerNo);
    }

    @Override
    public ResultVo<String> getWarehouseParentCode(String warehouseCode) {
        return warehouseService.getWarehouseParentCode(warehouseCode);
    }

    @Override
    public ResultVo<HROrganizationVO> findHrOrganByDeptNo(String deptNo) {
        return departmentService.findHrOrganByDeptNo(deptNo);
    }

    @Override
    public ResultVo<List<HROrganizationVO>> listHrOrganAllData(String deptNo) {
        return departmentService.listHrOrganAllData(deptNo);
    }

    @Override
    public void cacheAllCustomerInfo() {
        customerService.cacheAllCustomerInfo();
    }

    @Override
    public void cacheAllEmployee() {
        employeeService.cacheAllEmployee();
    }

    @Override
    public ResultVo<String> getWarehouseType(String warehouseCode) {
        return warehouseService.getWarehouseType(warehouseCode);
    }

    @Override
    public ResultVo<DepartmentVO> getDepartmentInfoByNo(String deptNo) {
        return departmentService.getDepartmentInfo(deptNo);
    }

    @Override
    public ResultVo<List<CodeName>> getDeptTreeByNo(List<String> deptNos) {
        return departmentService.getDeptTreeByNo(deptNos);
    }

    @Override
    public ResultVo<List<String>> getIndustryMediamCodeToCstmNo(List<String> industryCode) {
        return customerService.getIndustryMediamCodeToCstmNo(industryCode);
    }

    @Override
    public ResultVo<Integer> getTransDayByDeptNo(String deptNo) {
        return warehouseService.getTransDayByDeptNo(deptNo);
    }

    @Override
    public Boolean judgeIsSubWareHouse(String wareHouse) {
        return warehouseService.judgeIsSubWareHouse(wareHouse);
    }

    @Override
    public void cacheAllDepartmentInfo() {
        departmentService.cacheAllDepartmentInfo();
    }

    @Override
    public ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehoueType) {
        return warehouseService.getWarehouseCodeByWarehouseType(warehoueType);
    }

    @Override
    public ResultVo<Boolean> isMasterWarehouse(String warehouseCode) {
        return warehouseService.isMasterWarehouse(warehouseCode);
    }

    @Override
    public ResultVo<List<OpsWarehouseSalesbranchConfigVO>>getWarehouseSalesBranchConfigForPriority(){
        return warehouseService.getWarehouseSalesBranchConfigForPriority();
    }
    @Override
    public ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriorityByMaster(){
        return warehouseService.getWarehouseSalesBranchConfigForPriorityByMaster();
    }

    @Override
    public ResultVo<String> translateCustomerNameToEnglish() {
        return customerService.translateCustomerNameToEnglish();
    }

    @Override
    public void syncWorkDay() {
        adapterService.syncWorkDay();
    }
}

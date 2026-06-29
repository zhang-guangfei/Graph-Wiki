package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.CodeName;
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
import com.smc.smccloud.service.CommonServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class CommonServiceFeignHystrix implements FallbackFactory<CommonServiceFeignApi> {


    @Override
    public CommonServiceFeignApi create(Throwable throwable) {
        return new CommonServiceFeignApi() {
            @Override
            public ResultVo<String> generatorBillNo(String billType) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Set<String>> batchGeneratorBillNo(String billType, Integer number) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<EmployeeVO>> findEmployeeInfoByIdOrName(EmployeeVO employeeVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getEmployeeNameByNo(String employeeNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<EmployeeVO> getIndManageInfoByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<CodeName> getEmployeeCodeByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<WarehouseVO>> listWarehouse(WarehouseQueryDTO dto) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<WarehouseVO> getWarehouseInfoByCode(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getWarehouseName(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<CustomerVO> findCustomerByCustomerNo(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public List<CustomerVO> findCustomerListByCustomerNos(CustomerParam customerParam) {
                return null;
            }

            @Override
            public ResultVo<String> getCustomerNameByNo(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getCustomerENameByNo(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<DepartmentVO> getDepartmentInfo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<DepartmentVO> getDeptNoByOldNo(String oldDeptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getDeptNameByNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<SupplierVo>> findSupplierInfo() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<SupplierVo>> findSupplierList(SupplierRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<SupplierVo>> findSupplierByIdOrName(String companyId, String name) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<SupplierVo>> findChinaSuppliers() {
                return null;
            }

            @Override
            public ResultVo<String> updateSupplierData(SupplierVo supplierVo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getSupplierName(String supplierCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<SupplierVo> findSupplierById(String id) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<EmployeeVO> getEmployeeInfo(String employeeNo) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public  ResultVo<EmployeePositionVO> getEmployeePosition(  String employeeId) {
                return ResultVo.failure("服务降级");
            }
            @Override

            public ResultVo<List<CustomerVO>> findCustomerInfoByNoOrName(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getSixDeptNoByTwoDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getParentNumberByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> getDepartmentDlvDayByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public Date calDlvDate(String deliveryDate, String today) {
                return null;
            }

            @Override
            public ResultVo<List<DepartmentVO>> listDepartment() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<WarehouseVO>> listWarehouseNoWT() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<WarehouseVO>> getSubWarehouse(@RequestParam(value = "warehouseCode") String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<WarehouseVO>> getWarehouseByType(@RequestParam(value = "wareHouseType") String wareHouseType)            {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<CarrierVO> findCarrierInfoById(String carrierId) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> findcustGroupIdByName(String custName) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<TblGroupcustomerVO> getTblGroupCustInfo(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<CstoAndUserInfoVO> getCstoAndUserInfo(String customerNo, String userNo, String empSale, String endUserNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getDlvDeptNoByNo(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getWarehouseParentCode(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<HROrganizationVO> findHrOrganByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<HROrganizationVO>> listHrOrganAllData(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void cacheAllCustomerInfo() {
            }

            @Override
            public void cacheAllEmployee() {
            }

            @Override
            public ResultVo<String> getWarehouseType(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<DepartmentVO> getDepartmentInfoByNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<CodeName>> getDeptTreeByNo(List<String> deptNos) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<String>> getIndustryMediamCodeToCstmNo(List<String> industryCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> getTransDayByDeptNo(String deptNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public Boolean judgeIsSubWareHouse(String wareHouse) {
                return null;
            }

            @Override
            public void cacheAllDepartmentInfo() {
                return;
            }

            @Override
            public ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehoueType) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Boolean> isMasterWarehouse(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<List<OpsWarehouseSalesbranchConfigVO>>getWarehouseSalesBranchConfigForPriority(){
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriorityByMaster(){
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> translateCustomerNameToEnglish() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void syncWorkDay() {

            }
        };
    }
}

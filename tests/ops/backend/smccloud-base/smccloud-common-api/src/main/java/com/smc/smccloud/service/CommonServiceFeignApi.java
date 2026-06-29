package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
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
import com.smc.smccloud.service.hystrix.CommonServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@FeignClient(name = "common-service",
        contextId = "common-service",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = CommonServiceFeignHystrix.class)
public interface CommonServiceFeignApi {

    /**
     * 生成单据号
     *
     * @param billType 单据类型
     * @return
     */
    @RequestMapping(value = "/common/code/generaterBillNo", method = RequestMethod.GET)
    ResultVo<String> generatorBillNo(@RequestParam("billType") String billType);

//    /**
//     * 生成一个带时间前缀的单号
//     * @param billType
//     * @return
//     */
//    @RequestMapping(value = "/common/code/generatorBillNoWithDatePrefix", method = RequestMethod.GET)
//    ResultVo<String> generatorBillNoWithDatePrefix(String billType);

    /**
     * 批量生成单据号
     *
     * @param billType 单据类型
     * @param number   生成数量
     * @return
     */
    @RequestMapping(value = "/common/code/batchGeneratorBillNo", method = RequestMethod.GET)
    ResultVo<Set<String>> batchGeneratorBillNo(@RequestParam("billType") String billType, @RequestParam("number") Integer number);

    /**
     * 根据工号或姓名查询员工信息
     *
     * @param employeeVO
     * @return
     */
    @RequestMapping(value = "/common/employee/findEmployeeInfo", method = RequestMethod.POST)
    ResultVo<PageInfo<EmployeeVO>> findEmployeeInfoByIdOrName(@RequestBody EmployeeVO employeeVO);

    /**
     * 根据员工工号获取员工姓名
     *
     * @param employeeNo 工号
     * @return 姓名
     */
    @RequestMapping(value = "/common/employee/getEmployeeNameByNo", method = RequestMethod.GET)
    ResultVo<String> getEmployeeNameByNo(@RequestParam(value = "employeeNo") String employeeNo);

    /**
     * 根据部门代码查询行业负责人信息
     */
    @RequestMapping(value = "/common/employee/getIndManageInfoByDeptNo", method = RequestMethod.GET)
    ResultVo<EmployeeVO> getIndManageInfoByDeptNo(@RequestParam("deptNo") String deptNo);

    /**
     * 根据部门代码查询部门所属员工
     *
     * @param deptNo 部门代码
     * @return CodeName
     */
    @RequestMapping(value = "/common/employee/getEmployeeCodeByDeptNo", method = RequestMethod.GET)
    ResultVo<CodeName> getEmployeeCodeByDeptNo(@RequestParam("deptNo") String deptNo);

    /**
     * 查询物体仓库信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/common/code/listWarehouse", method = RequestMethod.POST)
    ResultVo<List<WarehouseVO>> listWarehouse(@RequestBody WarehouseQueryDTO dto);

    @RequestMapping(value = "/common/code/getWarehouseInfoByCode", method = RequestMethod.POST)
    ResultVo<WarehouseVO> getWarehouseInfoByCode(@RequestParam(value = "warehouseCode") String warehouseCode);

    /**
     * 根据仓库代码查询仓库名称
     *
     * @param warehouseCode 仓库代码
     * @return 仓库名称
     */
    @RequestMapping(value = "/common/code/getWarehouseName", method = RequestMethod.GET)
    ResultVo<String> getWarehouseName(@RequestParam(value = "warehouseCode") String warehouseCode);

    /**
     * 根据客户编码查找客户信息
     *
     * @param customerNo 客户编码
     * @return
     */
    @RequestMapping(value = "/common/customer/findCustomerByCustomerNo", method = RequestMethod.GET)
    ResultVo<CustomerVO> findCustomerByCustomerNo(@RequestParam(value = "customerNo") String customerNo);

    @RequestMapping(value = "/common/customer/findCustomerListByCustomerNos")
    List<CustomerVO> findCustomerListByCustomerNos(@RequestBody CustomerParam customerParam);

    /**
     * 根据客户代码获取客户名称
     *
     * @param customerNo 客户代码
     * @return customerName
     */
    @RequestMapping(value = "/common/customer/getCustomerNameByNo", method = RequestMethod.GET)
    ResultVo<String> getCustomerNameByNo(@RequestParam(value = "customerNo") String customerNo);

    /**
     * 获取客户英文名称
     *
     * @param customerNo 客户代码
     * @return CustomerEName
     */
    @RequestMapping(value = "/common/customer/getCustomerENameByNo", method = RequestMethod.GET)
    ResultVo<String> getCustomerENameByNo(@RequestParam("customerNo") String customerNo);

    /**
     * 根据部门编号查找部门信息 (营业所)
     *
     * @param deptNo 部门编号
     * @return
     */
    @RequestMapping(value = "/common/department/getDepartmentInfo", method = RequestMethod.GET)
    ResultVo<DepartmentVO> getDepartmentInfo(@RequestParam(value = "deptNo") String deptNo);

    @GetMapping("/common/department/listDepartment")
    ResultVo<List<DepartmentVO>> listDepartment();

    /**
     * 根据旧部门编号查找新部门代码
     *
     * @param oldDeptNo 旧部门编号
     * @return
     */
    @RequestMapping(value = "/common/department/getDeptNoByOldNo", method = RequestMethod.GET)
    ResultVo<DepartmentVO> getDeptNoByOldNo(@RequestParam(value = "oldDeptNo") String oldDeptNo);

    /**
     * 根据部门编号获取部门名称
     *
     * @param deptNo 部门编号
     * @return deptName
     */
    @RequestMapping(value = "/common/department/getDeptNameByNo", method = RequestMethod.GET)
    ResultVo<String> getDeptNameByNo(@RequestParam(value = "deptNo") String deptNo);

    /**
     * 获取供应商信息
     *
     * @return
     */
    @RequestMapping(value = "/common/supplier/findSupplierInfo", method = RequestMethod.GET)
    ResultVo<List<SupplierVo>> findSupplierInfo();

    @RequestMapping(value = "/common/supplier/findSupplierList", method = RequestMethod.POST)
    ResultVo<PageInfo<SupplierVo>> findSupplierList(@RequestBody SupplierRequest request);


    @RequestMapping(value = "/common/supplier/findSupplierByIdOrName", method = RequestMethod.GET)
    ResultVo<List<SupplierVo>> findSupplierByIdOrName(@RequestParam(value = "companyId") String companyId, @RequestParam(value = "name") String name);

    // 获取供应商
    @GetMapping("/common/supplier/findChinaSuppliers")
    ResultVo<List<SupplierVo>> findChinaSuppliers();

    @RequestMapping(value = "/common/supplier/updateSupplierData", method = RequestMethod.POST)
    ResultVo<String> updateSupplierData(@RequestBody SupplierVo supplierVo);

    @RequestMapping(value = "/common/supplier/getSupplierName", method = RequestMethod.GET)
    ResultVo<String> getSupplierName(@RequestParam(value = "supplierCode") String supplierCode);

    @RequestMapping(value = "/common/supplier/findSupplierById", method = RequestMethod.GET)
    ResultVo<SupplierVo> findSupplierById(@RequestParam(value = "id") String id);

    /**
     * 根据职员工号获取职员信息
     */
    @RequestMapping(value = "/common/user/getEmployeeInfo", method = RequestMethod.GET)
    ResultVo<EmployeeVO> getEmployeeInfo(@RequestParam(value = "employeeNo") String employeeNo);

    /**
     * 获取职位信息
     * @param
     * @return
     */
    @RequestMapping(value = "/common/user/getEmployeePosition", method = RequestMethod.GET)
    ResultVo<EmployeePositionVO> getEmployeePosition(@RequestParam(value = "employeeId") String employeeId);
    /**
     * 模糊查询客户信息
     *
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "/common/customer/findCustomerInfoByNoOrName", method = RequestMethod.GET)
    ResultVo<List<CustomerVO>> findCustomerInfoByNoOrName(@RequestParam(value = "customerNo") String customerNo);


    /**
     * 部门2位码转6位码
     *
     * @param deptNo
     * @return
     */
    @RequestMapping(value = "/common/dept/getSixDeptNoByTwoDeptNo", method = RequestMethod.GET)
    ResultVo<String> getSixDeptNoByTwoDeptNo(@RequestParam(value = "deptNo") String deptNo);

    /**
     * 根据销售部门代码查询营业所代码
     *
     * @param hrSalesDeptNo 销售部门代码
     * @return 营业所代码
     */
    @RequestMapping(value = "/common/dept/getDeptNoByHRSalesDeptNo", method = RequestMethod.GET)
    ResultVo<String> getDeptNoByHRSalesDeptNo(@RequestParam(value = "hrSalesDeptNo") String hrSalesDeptNo);


    @RequestMapping(value = "/common/dept/getParentNumberByDeptNo", method = RequestMethod.GET)
    ResultVo<String> getParentNumberByDeptNo(@RequestParam(value = "deptNo") String deptNo);

    @RequestMapping(value = "/common/dept/getDlvDayByDeptNo", method = RequestMethod.GET)
    ResultVo<Integer> getDepartmentDlvDayByDeptNo(@RequestParam(value = "deptNo") String deptNo);

    /**
     * 计算发货日期是否为工作日Tbl_WorkDay_Year,返回有效发货日期(当前日期的邻近前一天工作日日期)
     */
    @RequestMapping(value = "/common/adapter/calDlvDate", method = RequestMethod.GET)
    Date calDlvDate(@RequestParam(value = "deliveryDate") String deliveryDate, @RequestParam(value = "today") String today);

    /**
     * 查询除委托在库外的仓库代码和名称
     *
     * @return
     */
    @RequestMapping(value = "/common/code/listWarehouseNoWT", method = RequestMethod.POST)
    ResultVo<List<WarehouseVO>> listWarehouseNoWT();

    /**
     * 根据父仓库查询子仓库
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/common/code/getSubWarehouse", method = RequestMethod.POST)
    ResultVo<List<WarehouseVO>> getSubWarehouse(@RequestParam(value = "warehouseCode") String warehouseCode);

    /**
     * 根据仓库类别查询，master,sub
     * @param wareHouseType
     * @return
     */
    @RequestMapping(value = "/common/code/getWarehouseByType", method = RequestMethod.POST)
    ResultVo<List<WarehouseVO>> getWarehouseByType(@RequestParam(value = "wareHouseType") String wareHouseType);
    /**
     * 根据承运商获取承运商信息
     */
    @RequestMapping(value = "/common/carrier/findCarrierInfoById", method = RequestMethod.GET)
    ResultVo<CarrierVO> findCarrierInfoById(@RequestParam(value = "carrierId") String carrierId);


    /**
     * 根据客户名称获取客户集团ID
     */
    @RequestMapping(value = "/common/custGroup/findcustGroupIdByName", method = RequestMethod.GET)
    ResultVo<String> findcustGroupIdByName(@RequestParam(value = "custName") String custName);

    /**
     * 根据 customerNo 查  Tbl_GroupCustomer
     */
    @RequestMapping(value = "/common/custGroup/getTblGroupCustInfo", method = RequestMethod.GET)
    ResultVo<TblGroupcustomerVO> getTblGroupCustInfo(@RequestParam(value = "customerNo") String customerNo);

    // 获取客户名称 客户担当 用户名称 最终用户名称
    @RequestMapping(value = "/common/cstoAndUser/getCstoAndUserInfo", method = RequestMethod.GET)
    ResultVo<CstoAndUserInfoVO> getCstoAndUserInfo(@RequestParam(value = "customerNo") String customerNo,
                                                   @RequestParam(value = "userNo") String userNo,
                                                   @RequestParam(value = "empSale") String empSale,
                                                   @RequestParam(value = "endUserNo") String endUserNo);

    /**
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "/common/tblCustomer/getDlvDeptNoByNo", method = RequestMethod.GET)
    ResultVo<String> getDlvDeptNoByNo(@RequestParam(value = "customerNo") String customerNo);

    @RequestMapping(value = "/common/code/getWarehouseParentCode", method = RequestMethod.GET)
    ResultVo<String> getWarehouseParentCode(@RequestParam(value = "warehouseCode") String warehouseCode);

    /**
     * 查 hr_organization 表
     */
    @RequestMapping(value = "/common/hrOrgan/findHrOrganByDeptNo", method = RequestMethod.GET)
    ResultVo<HROrganizationVO> findHrOrganByDeptNo(@RequestParam(value = "deptNo") String deptNo);

    @RequestMapping(value = "/common/hrOrgan/listHrOrganAllData", method = RequestMethod.GET)
    ResultVo<List<HROrganizationVO>> listHrOrganAllData(@RequestParam(value = "deptNo") String deptNo);

    /**
     * 缓存所有的客户信息  key : customerNo
     */
    @RequestMapping(value = "/common/customer/cacheAllCustomerInfo", method = RequestMethod.GET)
    void cacheAllCustomerInfo();

    /**
     * 缓存所有的职员信息  key : ID (职员编码)
     */
    @RequestMapping(value = "/common/employee/cacheAllEmployee", method = RequestMethod.GET)
    void cacheAllEmployee();

    /**
     * 根据仓库代码获取仓库类别
     */
    @RequestMapping(value = "/common/warehouse/getWarehouseType", method = RequestMethod.GET)
    ResultVo<String> getWarehouseType(@RequestParam(value = "warehouseCode") String warehouseCode);

    @RequestMapping(value = "/common/department/getDepartmentInfoByNo", method = RequestMethod.GET)
    ResultVo<DepartmentVO> getDepartmentInfoByNo(@RequestParam(value = "deptNo") String deptNo);

    /**
     * deptNo为空时,默认返回 (北京/上海/广州)分公司/行业开发部 的树状部门数据.
     * deptNo不为空时,按deptNo返回树状部门数据.
     *
     * @param deptNos (选填)
     */
    @RequestMapping(value = "/common/department/getDeptTreeByNo", method = RequestMethod.GET)
    ResultVo<List<CodeName>> getDeptTreeByNo(@RequestParam(value = "deptNos", required = false) List<String> deptNos);

    /**
     * 获取有行业代码的客户
     */
    @RequestMapping(value = "/common/customer/getIndustryMediamCodeToCstmNo", method = RequestMethod.POST)
    ResultVo<List<String>> getIndustryMediamCodeToCstmNo(@RequestBody List<String> industryCode);

    /**
     * 根据部门代码获取运输天数
     */
    @RequestMapping(value = "/common/department/getTransDayByDeptNo", method = RequestMethod.GET)
    ResultVo<Integer> getTransDayByDeptNo(@RequestParam("deptNo") String deptNo);

    // 判断是不是分库
    @RequestMapping(value = "/common/wareHouse/judgeIsSubWareHouse", method = RequestMethod.GET)
    Boolean judgeIsSubWareHouse(@RequestParam("wareHouse") String wareHouse);

    /**
     * 缓存所有的部门信息
     */
    @RequestMapping(value = "/common/department/cacheAllDepartmentInfo", method = RequestMethod.GET)
    void cacheAllDepartmentInfo();


    /**
     * 获取仓库类型获取仓库代码集合
     */
    @RequestMapping(value = "/common/warehouse/getWarehouseCodeByWarehouseType", method = RequestMethod.GET)
    ResultVo<List<String>> getWarehouseCodeByWarehouseType(@RequestParam("warehouseType") String warehouseType);

    /**
     * 根据仓库代码判断物流中心
     */
    @RequestMapping(value = "/common/warehouse/isMasterWarehouse", method = RequestMethod.GET)
    ResultVo<Boolean> isMasterWarehouse(@RequestParam("warehouseCode") String warehouseCode);

    @RequestMapping(value ="/common/warehouse/getWarehouseSalesBranchConfigForPriority", method = RequestMethod.POST)
    ResultVo<List<OpsWarehouseSalesbranchConfigVO>>getWarehouseSalesBranchConfigForPriority();

    @RequestMapping(value ="/common/warehouse/getWarehouseSalesBranchConfigForPriorityByMaster", method = RequestMethod.POST)
    ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriorityByMaster();

    @GetMapping("/common/translateCustomerNameToEnglish")
    ResultVo<String> translateCustomerNameToEnglish();

    @GetMapping("/common/syncWorkDay")
    void syncWorkDay();
}

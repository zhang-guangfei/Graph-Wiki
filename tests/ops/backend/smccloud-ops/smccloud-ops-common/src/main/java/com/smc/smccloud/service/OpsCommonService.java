package com.smc.smccloud.service;

import com.sales.ops.dto.purchase.BaseDataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ModelNoCheckBean;
import com.smc.smccloud.model.*;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/1/13 17:12
 * @Descripton TODO
 */
public interface OpsCommonService {

    HROrganizationDO findSalesDepartment(String deptCode);

    HROrganizationDO findRegion(String deptCode);

    /**
     * 根据部门编码查找组织信息
     */
    ResultVo<HROrganizationDO> getHrOrganInfoByCode(String code);

    /**
     * 根据部门编码查找营业所，营业部，分公司
     */
    ResultVo<OrganizationVO> getHrOrganMasterInfoByCode(String code);

    /**
     * 根据销售部门代码查询营业所代码
     *
     * @param hrSalesDeptNo 销售部门代码
     * @return 营业所代码
     */
    ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo);

    /**
     * 根据销售部门代码查询营业所代码
     * 包含level 课内机构(驻在所)
     * @param hrSalesDeptNo
     * @return
     */
    ResultVo<String> getDeptNoByHRSalesDeptNoWithZZ(String hrSalesDeptNo);

    /**
     * 根据部门编码获取部门信息
     */
    ResultVo<DepartmentVO> getDepartmentInfo(String deptNo);

    /**
     * 根据员工工号获取员工姓名
     */
    String getEmpSaleNameByNo(String empSale);

    /**
     * 根据部门编号获取部门名称
     *
     * @param deptNo 部门编码
     * @return
     */
    String getDeptNameByNo(String deptNo);

    /**
     * 根据客户代码查询客户信息
     *
     * @param customerNo 客户代码
     * @return 客户信息
     */
    CustomerVO getCustomerByCustomerNo(String customerNo);

    /**
     * 根据客户代码获取客户名称
     *
     * @param customerNo
     * @return
     */
    String getCustomerNameByNo(String customerNo);

    /**
     * 根据供应商编码获取供应商名称
     *
     * @param supplierCode
     * @return
     */
    String getSupplierNameByCode(String supplierCode);

    ResultVo<List<BaseDataDto>> getAllSupplier();


    /**
     * 根据仓库代码查询仓库名称
     *
     * @param wareHouseCode
     * @return
     */
    String getWarehouseNameByCode(String wareHouseCode);

    String getWarehouseTypeByCode(String wareHouseCode);

    /**
     * 根据承运商编码获取名称
     */
    String getCarrierNameByCode(String carrierCode);

    /**
     * 根据承运商名称获取编码
     *
     * @return
     */
    String getCarrierCodeByName(String carrierName);

    /**
     * 根据状态判断是否可删单
     */
    List<String> canDelOrderStatus();

    String checkInventoryCode(String inventoryTypeCode, String customerNO, String pplNo, String projectNo, String groupNo);

    List<WarehouseSalesBranchDTO> getWarehouseSalesBranchList(List<String> warehouseCodes);

    OpsWarehouseSalesBranchConfigDO getSalesBranchListFirstWarehouse(String branchId);

    String getDeptNameByEmployeeId(String userId);

    ResultVo<List<WarehouseVO>> getWarehouseByType(String wareHouseType);

    ResultVo<String> testCommonCallInterface(String parm);

    /**
     * 查询采购单信息
     */
    OpsPurchaseOrderDOCommon getPurOrderInfo(String orderNo, int itemNo, int splitItemNo);

    /**
     * 根据客户群获取客户群信息
     */
    SalesCustomerClusterDO getCustomerClusterInfo(String customerGroupNo);


    ResultVo<String> insertOpsMail(OpsMailDO opsMailDO);

    /**
     * 型号去空格校验
     */
    List<ModelNoCheckBean> modelNoListCheck(List<String> modelNoList);

    boolean existSupplierCode(String supplierCode);

    boolean existWarehouseCode(String supplierCode);

    /**
     * 是否特殊型号
     */
    boolean isSpecialModel(String modelNo);

    /**
     * 根据HL代码获取营业所代码
     * 如果是营业所 直接返回 如果是HL 查找营业所
     * 富士康驻在HL4→富士康驻在→郑州所  返回郑州所
     */
    List<HrOrganizationDto> getDeptNoByHLSalesDeptNo();

}

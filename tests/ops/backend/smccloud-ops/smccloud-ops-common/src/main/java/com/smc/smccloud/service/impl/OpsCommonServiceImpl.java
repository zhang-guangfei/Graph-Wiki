package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.ModelNoCheckBean;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OpsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2023/1/13 17:13
 * @Descripton TODO
 */
@Service
@Slf4j
public class OpsCommonServiceImpl implements OpsCommonService {

    @Resource
    private HROrganizationMapper hrOrganizationMapper;
    @Resource
    private HRHolonMapper hrHolonMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private SupplierMapper supplierMapper;
    @Resource
    private OpsWarehouseMapper opsWarehouseMapper;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private SalesCustomerClusterMapper salesCustomerClusterMapper;

    @Resource
    private OpsPurchaseOrderMapperCommon opsPurchaseOrderMapperCommon;

    @Resource
    private WarehouseSalesBranchConfigMapper warehouseSalesBranchConfigMapper;

    @Resource
    private OpsMailMapper opsMailMapper;

    @Resource
    private SalesProductSpecialModelExcludeMapper salesProductSpecialModelExcludeMapper;

    @Resource
    private ProductSpecialModelMapper productSpecialModelMapper;

    /**
     * 这个正则表达式匹配以下任意一个字符：.、,、-、_、+。
     * 例如，它可以匹配字符串中的点号、逗号、连字符、下划线和加号。
     */
    public static final Pattern p = Pattern.compile("[.,\\-_+]");

    /**
     * 这个正则表达式匹配以下任意一个字符：大写字母（A-Z）、数字（0-9）、圆括号、点号、逗号、连字符、下划线、斜杠、加号、星号、井号以及小写字母 ü。
     * 例如，它可以匹配字符串中的大写字母、数字、圆括号、点号等。
     */
    public static final Pattern p1 = Pattern.compile("[A-Z0-9().,\\-_/+*#ü]");

    /**
     * 这个正则表达式匹配以下任意一个字符：大写字母（A-Z）、数字（0-9）、小写字母 ü、星号、井号、圆括号和斜杠。
     * 例如，它可以匹配字符串中的大写字母、数字、小写字母 ü、星号、井号、圆括号和斜杠。
     */
    public static final Pattern p2 = Pattern.compile("[A-Z0-9ü*#()/]");

    /* 型号规则:Pattern实例 */
    /**
     * 这个正则表达式匹配以下任意一个字符：.、,、-、_、+、%。
     * 例如，它可以匹配字符串中的点号、逗号、连字符、下划线、加号和百分号。
     */
    private static final Pattern mn = Pattern.compile("[.,\\-_+%]");
    /**
     * 这个正则表达式匹配以下任意一个字符：大写字母（A-Z）、数字（0-9）、圆括号、点号、逗号、连字符、下划线、斜杠、加号、星号、井号以及大写字母 Ü、Ä、Ö 和百分号。
     * 例如，它可以匹配字符串中的大写字母、数字、圆括号、点号等，并且包括了德语中的特殊字符 Ü、Ä、Ö 和百分号。
     */
    private static final Pattern mn1 = Pattern.compile("[A-Z0-9().,\\-_/+*#ÜÄÖ%]");
    /**
     * 这个正则表达式匹配以下任意一个字符：大写字母（A-Z）、数字（0-9）、大写字母 Ü、Ä、Ö、星号、井号、圆括号和百分号。
     * 例如，它可以匹配字符串中的大写字母、数字、特殊字符 Ü、Ä、Ö、星号、井号、圆括号和百分号。
     */
    private static final Pattern mn2 = Pattern.compile("[A-Z0-9ÜÄÖ*#()/%]");

    /**
     * 查询营业所所在的营业部
     * @param deptCode
     * @return
     */
    @Override
    public HROrganizationDO findSalesDepartment(String deptCode){
        String redisKey = Constants.REDIS_KEY_ORGAN_SALES + deptCode;
        Object object = redisManager.get(redisKey);
        if (Objects.nonNull(object)) {
            return JSONArray.parseObject(object.toString(), HROrganizationDO.class);
        } else {
            HROrganizationDO organizationDO = getHrOrganInfoByCode(deptCode).getData();
            while (organizationDO != null) {
                if ("二级部".equals(organizationDO.getLevel())) {
                    return organizationDO;
                }
                organizationDO = getHrOrganInfoByCode(organizationDO.getParentId()).getData();
            }
            redisManager.set(redisKey, JSON.toJSONString(organizationDO), 60 * 60 * 24);
            return organizationDO;
        }
    }

    /**
     * 查询营业所所在的大区
     * @param deptCode
     * @return
     */
    @Override
    public HROrganizationDO findRegion(String deptCode){
        String redisKey = Constants.REDIS_KEY_ORGAN_REGION + deptCode;
        Object object = redisManager.get(redisKey);
        if (Objects.nonNull(object)) {
            return JSONArray.parseObject(object.toString(), HROrganizationDO.class);
        } else {
            HROrganizationDO organizationDO = getHrOrganInfoByCode(deptCode).getData();
            while (organizationDO != null) {
                if ("本部".equals(organizationDO.getLevel())) {
                    return organizationDO;
                }
                organizationDO = getHrOrganInfoByCode(organizationDO.getParentId()).getData();
            }
            redisManager.set(redisKey, JSON.toJSONString(organizationDO), 60 * 60 * 24);
            return organizationDO;
        }
    }
    @Override
    public ResultVo<HROrganizationDO> getHrOrganInfoByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return ResultVo.failure("参数不可为空");
        }
        HROrganizationDO hrOrganizationDO;
        String redisKey = Constants.REDIS_KEY_ORGAN_CODE + code;
        Object object = redisManager.get(redisKey);
        if (Objects.nonNull(object)) {
            hrOrganizationDO = JSONArray.parseObject(object.toString(), HROrganizationDO.class);
        } else {
            LambdaQueryWrapper<HROrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HROrganizationDO::getId, code);
            hrOrganizationDO = hrOrganizationMapper.selectOne(queryWrapper);
            if (Objects.nonNull(hrOrganizationDO)) {
                redisManager.set(redisKey, JSON.toJSONString(hrOrganizationDO), 60 * 60 * 24);
            } else {
                return ResultVo.failure(code + "暂无数据.");
            }
        }
        return ResultVo.success(hrOrganizationDO);
    }
    @Override
    public ResultVo<OrganizationVO> getHrOrganMasterInfoByCode(String code) {
        OrganizationVO vo = new OrganizationVO();
        Object object = redisManager.hGet(CommonConstants.OPS_ORGANIZATION_MASTER, code);
        if (ObjectUtils.isNotEmpty(object)) {
            vo = JSONObject.parseObject(object.toString(), OrganizationVO.class);
        } else {
            HROrganizationDO organizationDO = opsCommonService.getHrOrganInfoByCode(code).getData();
            if (ObjectUtils.isNotEmpty(organizationDO)) {
                String[] names = organizationDO.getFullName().split("_");
                String[] unitCodes = organizationDO.getUnitCode().split("!");
                String deptNO = organizationDO.getId();
                vo.setDeptNo(deptNO);
                vo.setDeptName(organizationDO.getName());
                int idCount = names.length;
                if (organizationDO.getLevel().contains("驻在所")) {
                    vo.setSalesNo(unitCodes[idCount - 2]);
                    vo.setSalesName(names[idCount - 2]);
                    vo.setParentNo(unitCodes[idCount - 3]);
                    vo.setParentName(names[idCount - 3]);
                    vo.setCompanyNo(unitCodes[idCount - 4]);
                    vo.setCompanyName(names[idCount - 4]);


                } else if (organizationDO.getLevel().contains("营业所")) {
                    vo.setSalesNo(unitCodes[idCount - 1]);
                    vo.setSalesName(names[idCount - 1]);
                    vo.setParentNo(unitCodes[idCount - 2]);
                    vo.setParentName(names[idCount - 2]);
                    vo.setCompanyNo(unitCodes[idCount - 3]);
                    vo.setCompanyName(names[idCount - 3]);
                } else {
                    vo.setParentNo(unitCodes[idCount - 2]);
                    vo.setParentName(names[idCount - 2]);
                    vo.setCompanyNo(unitCodes[idCount - 3]);
                    vo.setCompanyName(names[idCount - 3]);
                }
            } else {
                vo.setSalesNo(code);
                vo.setSalesName("");
                vo.setParentNo("");
                vo.setParentName("");
                vo.setCompanyNo("");
                vo.setCompanyName("");
            }
            redisManager.hPut(CommonConstants.OPS_ORGANIZATION_MASTER, code, JSONObject.toJSONString(vo));
        }
        return ResultVo.success(vo);
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opsdb")
    public ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo) {
        // 查询销售部门代码是否是Holon,如果是则返回所属的营业所代码,否则其本身就是营业所代码
//        LambdaQueryWrapper<HRHolonDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(HRHolonDO::getFNUMBER, HRHolonDO::getPARENTFNUMBER);
//        queryWrapper.eq(HRHolonDO::getFNUMBER, hrSalesDeptNo);
//        HRHolonDO hrHolonDO = hrHolonMapper.selectOne(queryWrapper);
//        if (hrHolonDO == null) {
//            return ResultVo.success(hrSalesDeptNo);
//        } else {
//            return ResultVo.success(hrHolonDO.getPARENTFNUMBER());
//        }
        if (StringUtils.isBlank(hrSalesDeptNo)) {
            return ResultVo.failure("入参不可为空");
        }
        List<String> hrLevel = new ArrayList<>();
        hrLevel.add("课内机构(HL)");
        hrLevel.add("驻在所HL");
        LambdaQueryWrapper<HROrganizationDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(HROrganizationDO::getId, HROrganizationDO::getParentId)
                .in(HROrganizationDO::getLevel, hrLevel)
                .eq(HROrganizationDO::getCompanyCode, "200000")
                .eq(HROrganizationDO::getId, hrSalesDeptNo);
        HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(lambdaQueryWrapper);
        if (hrOrganizationDO == null) {
            return ResultVo.success(hrSalesDeptNo);
        }
        return ResultVo.success(hrOrganizationDO.getParentId());
    }

    @Override
    public ResultVo<String> getDeptNoByHRSalesDeptNoWithZZ(String hrSalesDeptNo) {
        if (StringUtils.isBlank(hrSalesDeptNo)) {
            return ResultVo.failure("入参不可为空");
        }
        List<String> hrLevel = new ArrayList<>();
        hrLevel.add("课内机构(HL)");
        hrLevel.add("驻在所HL");
        hrLevel.add("课内机构(驻在所)");
        LambdaQueryWrapper<HROrganizationDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(HROrganizationDO::getId, HROrganizationDO::getParentId)
                .in(HROrganizationDO::getLevel, hrLevel)
                .eq(HROrganizationDO::getCompanyCode, "200000")
                .eq(HROrganizationDO::getId, hrSalesDeptNo);
        HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(lambdaQueryWrapper);
        if (hrOrganizationDO == null) {
            return ResultVo.success(hrSalesDeptNo);
        }
        return ResultVo.success(hrOrganizationDO.getParentId());
    }

    @Override
    public ResultVo<DepartmentVO> getDepartmentInfo(String deptNo) {
        Object o = redisManager.hGet(Constants.REDIS_DEPARTMENT_INFO, deptNo);
        if (o != null) {
            return ResultVo.success(JSONObject.parseObject(o.toString(), DepartmentVO.class));
        }
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepartmentDO::getDeptNo, deptNo);
        queryWrapper.eq(DepartmentDO::getIsValid, 1);
        List<DepartmentDO> departmentDOList = departmentMapper.selectList(queryWrapper);
        if (departmentDOList.isEmpty()) {
            return ResultVo.failure("部门不存在");
        }
        DepartmentVO departmentVO = BeanCopyUtil.copy(departmentDOList.get(0), DepartmentVO.class);
        redisManager.hPut(Constants.REDIS_DEPARTMENT_INFO, deptNo, JSONObject.toJSONString(departmentDOList.get(0)));
        return ResultVo.success(departmentVO);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opscmm")
    public String getEmpSaleNameByNo(String empSale) {
        if (StringUtils.isBlank(empSale)) {
            return null;
        }
        Object empSalesObj = redisManager.hGet(Constants.REDIS_EMPLOYEE_INFO_NAME, empSale);
        String name = null;
        if (empSalesObj == null) {
            LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(EmployeeDO::getId, EmployeeDO::getName);
            queryWrapper.eq(EmployeeDO::getId, empSale);
            List<EmployeeDO> list = employeeMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                name = empSale;
            } else {
                name = list.get(0).getName();
            }
            redisManager.hPut(Constants.REDIS_EMPLOYEE_INFO_NAME, empSale, name);
        } else {
            name = empSalesObj.toString();
        }
        return name;
    }

    @Override
    public String getDeptNameByNo(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return null;
        }
        String deptName = null;
        Object obj = redisManager.hGet(Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo);
        if (obj == null) {
            LambdaQueryWrapper<HROrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(HROrganizationDO::getId, HROrganizationDO::getName);
            queryWrapper.eq(HROrganizationDO::getId, deptNo);
            HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(queryWrapper);
            if (hrOrganizationDO == null) {
                deptName = deptNo;
            } else {
                deptName = hrOrganizationDO.getName();
            }
            redisManager.hPut(Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo, deptName);
        } else {
            deptName = obj.toString();
        }
        return deptName;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opsdb")
    public CustomerVO getCustomerByCustomerNo(String customerNo) {

        Object customer = redisManager.hGet(Constants.REDIS_ALL_CUSTOMER_INFO, customerNo);
        if (customer == null) {
            CustomerDO customerDO = customerMapper.selectById(customerNo);
            if (customerDO != null) {
                redisManager.hPut(Constants.REDIS_ALL_CUSTOMER_INFO, customerNo, JSON.toJSONString(customerDO));
                return BeanCopyUtil.copy(customerDO, CustomerVO.class);
            }
        } else {
            return JSONObject.parseObject(customer.toString(), CustomerVO.class);
        }
        return null;
    }

    @Override
    public String getCustomerNameByNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        Object customerName = redisManager.hGet(Constants.REDIS_CUSTOMER_NAME, customerNo);
        String name = null;
        if (customerName == null) {
            LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(CustomerDO::getCustomerNo, CustomerDO::getName);
            queryWrapper.eq(CustomerDO::getCustomerNo, customerNo);
            CustomerDO customerDO = customerMapper.selectOne(queryWrapper);
            if (customerDO == null) {
                name = customerNo;
            } else {
                name = customerDO.getName();
            }
            redisManager.hPut(Constants.REDIS_CUSTOMER_NAME, customerNo, name);

        } else {
            name = customerName.toString();
        }
        return name;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opsdb")
    public String getSupplierNameByCode(String supplierCode) {
        if (StringUtils.isBlank(supplierCode)) {
            return null;
        }
        Object obj = redisManager.hGet(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode);
        String supplierName = null;
        if (obj == null) {
            LambdaQueryWrapper<SupplierDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SupplierDO::getId, supplierCode);
            SupplierDO supplierDO = supplierMapper.selectOne(queryWrapper);
            if (supplierDO != null) {
                supplierName = supplierDO.getName();
                redisManager.hPut(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode, supplierName);
            } else {
                supplierName = supplierCode;
                redisManager.hPut(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode, supplierCode);
            }
        } else {
            supplierName = obj.toString();
        }
        return supplierName;
    }

    @Override
    public ResultVo<List<BaseDataDto>> getAllSupplier() {
        if (redisManager.hasKey(CommonConstants.ALL_SUPPLIER)) {
           Object object = redisManager.get(CommonConstants.ALL_SUPPLIER);
           if (object != null) {
               List<BaseDataDto> baseDataDtos = JSONArray.parseArray(object.toString(), BaseDataDto.class);
               return ResultVo.success(baseDataDtos);
           }
        }
        List<BaseDataDto> dataCodeVOList = supplierMapper.selectAllSupplier();
        redisManager.set(CommonConstants.ALL_SUPPLIER,JSONArray.toJSONString(dataCodeVOList),3600*24);
        return ResultVo.success(dataCodeVOList);
    }

    @Override
    public String getWarehouseNameByCode(String wareHouseCode) {
        if (StringUtils.isBlank(wareHouseCode)) {
            return null;
        }
        String wareHouseName = null;
        Object obj = redisManager.hGet(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, wareHouseCode);
        if (obj == null) {
            LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
            query.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName);
            query.eq(WarehouseDO::getWarehouseCode, wareHouseCode);
            WarehouseDO info = opsWarehouseMapper.selectOne(query);
            if (info == null) {
                redisManager.hPut(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, wareHouseCode, wareHouseCode);
                wareHouseName = wareHouseCode;
            } else {
                wareHouseName = info.getWarehouseName();
                redisManager.hPut(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, wareHouseCode, info.getWarehouseName());
            }
        } else {
            wareHouseName = obj.toString();
        }
        return wareHouseName;
    }

    @Override
    public String getWarehouseTypeByCode(String wareHouseCode) {
        LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
        query.eq(WarehouseDO::getWarehouseCode, wareHouseCode).eq(WarehouseDO::getDelflag,0);
        WarehouseDO info = opsWarehouseMapper.selectOne(query);
        if (info != null) {
            return info.getWarehouseType();
        }
        return null;
    }

    @Override
    public String getCarrierNameByCode(String carrierCode) {
        if (StringUtils.isBlank(carrierCode)) {
            return null;
        }
        String name = null;
        Object carriName = redisManager.hGet(Constants.REDIS_CARRIER_NAME, carrierCode);
        if (carriName == null) {
            ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes("1024");
            if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
                return null;
            }
            for (DataCodeVO item : dataCodes.getData()) {
                if (item.getCode().equals(carrierCode)) {
                    name = item.getCodeName();
                    redisManager.hPut(Constants.REDIS_CARRIER_NAME, carrierCode, name);
                    break;
                }
            }
        } else {
            name = carriName.toString();
        }
        return name;
    }

    @Override
    public String getCarrierCodeByName(String carrierName) {
        if (StringUtils.isBlank(carrierName)) {
            return null;
        }
        String code = null;
        Object obj = redisManager.hGet(Constants.REDIS_CARRIER_CODE, carrierName);
        if (obj == null) {
            ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes("1024");
            if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
                return null;
            }
            for (DataCodeVO item : dataCodes.getData()) {
                if (item.getCodeName().contains(carrierName)) {
                    code = item.getCode();
                    redisManager.hPut(Constants.REDIS_CARRIER_CODE, carrierName, code);
                    break;
                }
            }
        } else {
            code = obj.toString();
        }
        return code;
    }

    @Override
    public List<String> canDelOrderStatus() {
        List<String> list = new ArrayList<>();
        Object obj = redisManager.get(CommonConstants.CAN_DEL_STATUS);
        if (obj == null) {
            ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.ORDER_STATUS_DATAS);
            if (!dataCodes.isSuccess() || CollectionUtil.isEmpty(dataCodes.getData())) {
                return null;
            }
            List<DataCodeVO> data = dataCodes.getData();
            for (DataCodeVO item : data) {
                if (StringUtils.isNotBlank(item.getExtNote3()) && "N".equals(item.getExtNote3())) {
                    continue;
                }
                list.add(item.getCode());
            }
            redisManager.set(CommonConstants.CAN_DEL_STATUS, JSONArray.toJSONString(list), 3600 * 24 * 7);
        } else {
            list = JSONArray.parseArray(obj.toString(), String.class);
        }
        return list;
    }

    /**
     * 判断仓库类别，验证客户代码，pplno,projectNo，groupNo
     *
     * @param inventoryTypeCode 库存类别
     * @param customerNO        客户代码
     * @param pplNo
     * @param projectNo
     * @param groupNo           集团代码
     * @return
     */
    @Override
    public String checkInventoryCode(String inventoryTypeCode, String customerNO, String pplNo, String projectNo, String groupNo) {
        StringBuilder rtnval = new StringBuilder();
        Map<String, String> mapResultVo = dictCommonService.getDataCodesMap("2001").getData();
        Map<String, Boolean> customerMap = new HashMap<>();
        inventoryTypeCode = inventoryTypeCode.toUpperCase();
        if (!mapResultVo.keySet().contains(inventoryTypeCode)) {
            rtnval.append("库存类别不存在：" + inventoryTypeCode);
        } else {
            if (inventoryTypeCode.equalsIgnoreCase("TY")) {
                if (StringUtils.isNotBlank(customerNO) || StringUtils.isNotBlank(pplNo)
                        || StringUtils.isNotBlank(projectNo) || StringUtils.isNotBlank(groupNo)) {
                    rtnval.append("通用在库，不能存在客户代码等信息。");
                }
            } else {
                if (inventoryTypeCode.startsWith("GK")) {
                    if (StringUtils.isBlank(customerNO)) {
                        rtnval.append("顾客在库，客户代码不能为空。");
                    } else {
                        if (!customerMap.containsKey(customerNO)) {
                            customerMap.put(customerNO, (opsCommonService.getCustomerByCustomerNo(customerNO) != null));
                        }
                        if (!customerMap.get(customerNO)) {
                            rtnval.append("客户代码错误。");
                        }
                    }
                }
                if (inventoryTypeCode.endsWith("PPL") && StringUtils.isBlank(pplNo)) {
                    rtnval.append("PPL不能为空。");
                }
                if (inventoryTypeCode.endsWith("PJ") && StringUtils.isBlank(projectNo)) {
                    rtnval.append("项目代码不能为空。");
                }
                if ((inventoryTypeCode.endsWith("JT") || inventoryTypeCode.endsWith("HY")) && StringUtils.isBlank(groupNo)) {
                    rtnval.append("集团编号不能为空。");
                }
            }
        }
        return rtnval.toString();

    }

    @Override
    public List<WarehouseSalesBranchDTO> getWarehouseSalesBranchList(List<String> warehouseCodes) {
        return opsWarehouseMapper.getWarehouseSalesBranchList(warehouseCodes);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opsdb")
    public OpsWarehouseSalesBranchConfigDO getSalesBranchListFirstWarehouse(String branchId) {
        OpsWarehouseSalesBranchConfigDO configDO;
        String redisKey = Constants.REDIS_KEY_ORGAN_BRANCH_FIRSTWAREHOUSE + branchId;
        Object object = redisManager.get(redisKey);
        if (Objects.nonNull(object)) {
            configDO = JSONArray.parseObject(object.toString(), OpsWarehouseSalesBranchConfigDO.class);
        } else {
            LambdaQueryWrapper<OpsWarehouseSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OpsWarehouseSalesBranchConfigDO::getSalesBranchId, branchId)
                    .eq(OpsWarehouseSalesBranchConfigDO::getDelFlag, 0)
                    .orderByAsc(OpsWarehouseSalesBranchConfigDO::getPriority);
            List<OpsWarehouseSalesBranchConfigDO> configDOList = warehouseSalesBranchConfigMapper.selectList(queryWrapper);
            if (PublicUtil.isEmpty(configDOList)) {
                return null;
            }
            configDO = configDOList.get(0);
            redisManager.set(redisKey, JSON.toJSONString(configDO), 60 * 60 * 24);
        }
        return configDO;
    }

    @Override
    public String getDeptNameByEmployeeId(String userId) {

        return employeeMapper.getDeptNameByEmployeeId(userId);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
    @DS("opsdb")
    public ResultVo<List<WarehouseVO>> getWarehouseByType(String wareHouseType) {
        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> warehouseDOList = warehouseInfos.stream()
                .filter(f -> f.getDelflag().equals("0") && f.getWarehouseType().equalsIgnoreCase(wareHouseType))
                .collect(Collectors.toList());
        return ResultVo.success(BeanCopyUtil.copyList(warehouseDOList, WarehouseVO.class));
    }

    @Override
    public ResultVo<String> testCommonCallInterface(String parm) {
        log.info("成功进入接口testCallInterface:  参数:{}",parm);
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        Integer count = customerMapper.selectCount(queryWrapper);
        return ResultVo.success("testCallInterface响应成功."+count);
    }

    @Override
    public OpsPurchaseOrderDOCommon getPurOrderInfo(String orderNo, int itemNo, int splitItemNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        return opsPurchaseOrderMapperCommon.getInfoByOrderNoWithitemNoWithSplitItemNo(orderNo,itemNo,splitItemNo);
    }

    @Override
    public SalesCustomerClusterDO getCustomerClusterInfo(String customerGroupNo) {
        if (StringUtils.isBlank(customerGroupNo)) {
            return null;
        }

        /**
         * redis是否存在
         */
        Object object = redisManager.get(Constants.REDIS_KEY_CUSTOMER_CLUSTER_INFO + customerGroupNo);
        if (Objects.nonNull(object)) {
            return JSONObject.parseObject(object.toString(), SalesCustomerClusterDO.class);
        }
        LambdaQueryWrapper<SalesCustomerClusterDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesCustomerClusterDO::getCustomerBaseNo,customerGroupNo);
        List<SalesCustomerClusterDO> salesCustomerClusterDOS = salesCustomerClusterMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(salesCustomerClusterDOS)) {
            redisManager.set(Constants.REDIS_KEY_CUSTOMER_CLUSTER_INFO + customerGroupNo, JSONUtil.toJsonStr(salesCustomerClusterDOS.get(0)), 60 * 60 * 24);
            return salesCustomerClusterDOS.get(0);
        }
        return null;
    }

    @Override
    public ResultVo<String> insertOpsMail(OpsMailDO opsMailDO) {

        opsMailMapper.insert(opsMailDO);

        return ResultVo.success("写入成功");
    }

    @Override
    public List<ModelNoCheckBean> modelNoListCheck(List<String> modelNoList) {

        List<String> modelList = new ArrayList<String>();
        Object o = redisManager.get(Constants.SPECIAL_MODEL_NO);
        if (o != null) {
            modelList = JSONObject.parseArray(o.toString(), String.class);
        }

        if(CollectionUtils.isEmpty(modelList)) {
            modelList = salesProductSpecialModelExcludeMapper.getSpecialModelNoList();
            redisManager.set(Constants.SPECIAL_MODEL_NO, JSON.toJSONString(modelList), 60 * 60 * 24);
        }
        List<ModelNoCheckBean> modelNoCheckList = new ArrayList<>();
        for (String modelNo : modelNoList) {
            ModelNoCheckBean modelNoCheckBean = new ModelNoCheckBean();
            modelNoCheckBean.setModelNo(modelNo);
            modelNoCheckBean.setResult(true);
            if (StringUtils.isBlank(modelNo)) {
                modelNoCheckBean.setResult(false);
                modelNoCheckList.add(modelNoCheckBean);
                continue;
            }
            if (CollectionUtils.isNotEmpty(modelList) && modelList.contains(modelNo)) {
                modelNoCheckList.add(modelNoCheckBean);
                continue;
            }

            /**
             * A：开始遍历字符。
             * B：判断当前字符是否为首字符。
             * C：如果是首字符，检查是否符合p2规则。
             * D：如果不符合规则，设置结果为false并跳出循环。
             * E：记录前一个字符是否为特殊字符。
             * F：判断当前字符是否为尾字符。
             * G：如果是尾字符，检查是否符合p2规则或前一个字符是否为特殊字符。
             * H：继续处理下一个字符。
             * I：如果不是尾字符，检查当前字符和前一个字符是否都为特殊字符或当前字符是否符合p1规则。
             * J：记录前一个字符是否为特殊字符。
             * K：结束循环。
             */
            Boolean pre = false;
            for (int i = 0; i < modelNo.length(); i++) {
                String s = modelNo.substring(i, i + 1);
                Matcher m = p.matcher(s);
                Matcher m1 = p1.matcher(s);
                Matcher m2 = p2.matcher(s);
                if (i == 0) {
                    if (!m2.matches()) {
                        modelNoCheckBean.setResult(false);
                        break;
                    }
                    pre = m.matches();
                } else if (i == modelNo.length() - 1) {
                    if ((pre && m.matches()) || !m2.matches()) {
                        modelNoCheckBean.setResult(false);
                        break;
                    }
                } else {
                    if ((pre && m.matches()) || !m1.matches()) {
                        modelNoCheckBean.setResult(false);
                        break;
                    } else {
                        pre = m.matches();
                    }
                }
            }
            modelNoCheckList.add(modelNoCheckBean);
        }
        return modelNoCheckList;
    }

    @Override
    public boolean existSupplierCode(String supplierCode) {
        if(StringUtils.isBlank(supplierCode)) {
            return false;
        }
        LambdaQueryWrapper<SupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupplierDO::getId, supplierCode);
        SupplierDO supplierDO = supplierMapper.selectOne(queryWrapper);
        if (supplierDO == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existWarehouseCode(String supplierCode) {
        if (StringUtils.isBlank(supplierCode)) {
            return false;
        }
        LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
        query.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName);
        query.eq(WarehouseDO::getWarehouseCode, supplierCode).eq(WarehouseDO::getDelflag,0);
        WarehouseDO info = opsWarehouseMapper.selectOne(query);
        if (info == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public boolean isSpecialModel(String modelNo) {
        if(StringUtils.isBlank(modelNo)) {
            return false;
        }
        LambdaQueryWrapper<ProductSpecialModelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductSpecialModelDO::getModelNo, modelNo).eq(ProductSpecialModelDO::getIsDeleted, false);
        ProductSpecialModelDO productSpecialModelDO = productSpecialModelMapper.selectOne(queryWrapper);
        return productSpecialModelDO != null;
    }

    /**
     * 根据HL代码获取营业所代码（递归版本）
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<HrOrganizationDto> getDeptNoByHLSalesDeptNo() {
        return hrOrganizationMapper.getAllOrganization();
    }


    private List<WarehouseDO> getWarehouseInfo() {
        List<WarehouseDO> warehouseList;
        Object object = redisManager.get(Constants.REDIS_WAREHOUSE_INFO_ALL);
        if (Objects.nonNull(object)) {
            warehouseList = JSONArray.parseArray(object.toString(), WarehouseDO.class);
        } else {
            warehouseList = opsWarehouseMapper.selectList(null);
            if (PublicUtil.isNotEmpty(warehouseList)) {
                redisManager.set(Constants.REDIS_WAREHOUSE_INFO_ALL, JSON.toJSONString(warehouseList), 60 * 60 * 24);
            }
        }
        return warehouseList;
    }

}

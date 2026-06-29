package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.CustomerMapper;
import com.smc.smccloud.mapper.OpsCustomerNineIndustrycodeMapper;
import com.smc.smccloud.mapper.OpsCustomerWldateMapper;
import com.smc.smccloud.model.Customer.CustomerDO;
import com.smc.smccloud.model.Customer.OpsCustomerNineIndustrycodeDO;
import com.smc.smccloud.model.Customer.OpsCustomerWldateDO;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.TranslateCustomerNameVO;
import com.smc.smccloud.model.customer.*;
import com.smc.smccloud.service.CustomerService;
import com.smc.smccloud.service.DepartmentService;
import com.smc.smccloud.service.DictDataService;
import com.smc.smccloud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-01-12
 */

@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerDO> implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private OpsCustomerWldateMapper opsCustomerWldateMapper;
    @Resource
    private DepartmentService departmentService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private HttpServletResponse response;

    @Resource
    private OpsCustomerNineIndustrycodeMapper opsCustomerNineIndustrycodeMapper;

    private final static int CAL_SIZE = 1000;

    @Override
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
    public List<CustomerVO> getCustomerListInfoByCustomerNos(CustomerParam customerParam) {
        if (customerParam == null || customerParam.getCustomerNos() == null) {
            return null;
        }

        List<CustomerVO> list = new ArrayList<>(customerParam.getCustomerNos().size());
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        List<CustomerDO> customerDOS;
        List<CustomerVO> customerVOS;

        for (int i = 0; i < customerParam.getCustomerNos().size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<String> temp;
                if (i + CAL_SIZE < customerParam.getCustomerNos().size()) {
                    temp = customerParam.getCustomerNos().subList(i, i + CAL_SIZE);
                } else {
                    temp = customerParam.getCustomerNos().subList(i, customerParam.getCustomerNos().size());
                }
                // List<CustomerVO> l = customerDao.selectBatch(temp);
                queryWrapper.clear();
                queryWrapper.in(CustomerDO::getCustomerNo, temp);
                customerDOS = customerMapper.selectList(queryWrapper);
                customerVOS = BeanCopyUtil.copyList(customerDOS, CustomerVO.class);
                if (!customerVOS.isEmpty()) {
                    list.addAll(customerVOS);
                }
            }
        }
        return list;
    }

    @Override
    public ResultVo<List<CustomerVO>> getAllCustomerInfo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.success(new ArrayList<>());
        }
        List<CustomerVO> list = customerMapper.queryCustomer(customerNo);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }
        for (CustomerVO item : list) {
            item.setName("[" + item.getCustomerNo() + "]" + item.getName());
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> getCustomerNameByNo(String customerNo) {

        if (PublicUtil.isEmpty(customerNo)) {
            return ResultVo.failure("客户代码不存在");
        }

        String customerName = null;
        Object obj = redisManager.hGet(Constants.REDIS_CUSTOMER_NAME, customerNo);
        if (obj == null) {
            LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(CustomerDO::getCustomerNo, CustomerDO::getName);
            queryWrapper.eq(CustomerDO::getCustomerNo, customerNo);
            CustomerDO customerDO = customerMapper.selectOne(queryWrapper);

            if (PublicUtil.isNotEmpty(customerDO)) {
                customerName = customerDO.getName();
                redisManager.hPut(Constants.REDIS_CUSTOMER_NAME, customerNo, customerName);
            }
        } else {
            customerName = obj.toString();
        }
        return ResultVo.success(customerName);
    }

    @Override
    public ResultVo<String> getCustomerENameByNo(String customerNo) {
        if (PublicUtil.isEmpty(customerNo)) {
            return ResultVo.failure("客户代码不存在");
        }

        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CustomerDO::getCustomerNo, CustomerDO::getName, CustomerDO::getEname);
        queryWrapper.eq(CustomerDO::getCustomerNo, customerNo);
        CustomerDO customerInfo = customerMapper.selectOne(queryWrapper);
        if (customerInfo == null) {
            return ResultVo.failure("客户不存在");
        }

        String customerEName = customerInfo.getEname();

        if (StringUtils.isNotBlank(customerEName)) {
            redisManager.set(Constants.REDIS_CUSTOMER_ENAME_BY_NO + customerNo, customerEName, 259200); // 缓存3天
        }

        return ResultVo.success(customerEName);
    }

    @Override
    public List<CustomerVO> findCustomerInfoByNoOrName(String customerNo) {
        //customerNo为空则直接返回数据库前10条
        if (StringUtils.isBlank(customerNo)) {
            return customerMapper.queryCustomer(customerNo);
        }

        List<CustomerVO> list;
        //查询redis
        Object obj = redisManager.hGet(Constants.REDIS_CUSTOMER_BY_NO_OR_NAME, customerNo);
        if (obj == null) {
            list = customerMapper.queryCustomer(customerNo);
            if (PublicUtil.isNotEmpty(list)) {
                redisManager.hPut(Constants.REDIS_CUSTOMER_BY_NO_OR_NAME, customerNo, JSON.toJSONString(list));
            }
        } else {
            list = JSON.parseArray(obj.toString(), CustomerVO.class);
        }
        return list;
    }

    @Override
    public void cacheAllCustomerInfo() {
        // 获取所有客户信息
        QueryWrapper<CustomerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1", "1");
        List<CustomerDO> list = customerMapper.selectList(queryWrapper);
        if (null == list || list.isEmpty()) {
            return;
        }

        for (CustomerDO info : list) {
            boolean b = redisManager.hHasKey(Constants.REDIS_ALL_CUSTOMER_INFO, info.getCustomerNo());
            boolean b1 = redisManager.hHasKey(Constants.REDIS_CUSTOMER_NAME, info.getCustomerNo());
            if (b) {
                redisManager.hdel(Constants.REDIS_ALL_CUSTOMER_INFO, info.getCustomerNo());
            }
            if (b1) {
                redisManager.hdel(Constants.REDIS_CUSTOMER_NAME, info.getCustomerNo());
            }
            redisManager.hPut(Constants.REDIS_ALL_CUSTOMER_INFO, info.getCustomerNo(), JSON.toJSONString(info));
            redisManager.hPut(Constants.REDIS_CUSTOMER_NAME, info.getCustomerNo(), info.getName());
        }

    }

    @Override
    public ResultVo<List<String>> getIndustryMediamCodeToCstmNo(List<String> industryCode) {
        if (CollectionUtils.isEmpty(industryCode)) {
            return ResultVo.failure("暂无数据");
        }
        String joinStr = String.join(",", industryCode);
        Object object = redisManager.get(Constants.REDIS_INDUSTRY_CODE_CUSTOMERNO + joinStr);
        if (object != null) {
            List<String> list = JSONObject.parseObject(object.toString(), List.class);
            return ResultVo.success(list);
        } else {
            LambdaQueryWrapper<OpsCustomerNineIndustrycodeDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(OpsCustomerNineIndustrycodeDO::getCustomerNo).in(OpsCustomerNineIndustrycodeDO::getIndustryMediamCode, industryCode);
            List<OpsCustomerNineIndustrycodeDO> opsCustomerNineIndustrycodeDOS = opsCustomerNineIndustrycodeMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(opsCustomerNineIndustrycodeDOS)) {
                return ResultVo.failure("暂无数据");
            }
            List<String> dataList = opsCustomerNineIndustrycodeDOS.stream().map(OpsCustomerNineIndustrycodeDO::getCustomerNo).collect(Collectors.toList());
            redisManager.set(Constants.REDIS_INDUSTRY_CODE_CUSTOMERNO, joinStr);
            return ResultVo.success(dataList);
        }
    }

    @Override
    public ResultVo<PageInfo<OpsCustomerWldateVO>> getOpsCustomerWldateList(CustomerWldateRequest request) {

        if (request == null) {
            return ResultVo.success();
        }
//        if (StringUtils.isNotBlank(request.getCustomerNo())) {
//            request.setCustomerNo(request.getCustomerNo()+"%");
//        }

        Map<String, String> wldTypeMap = new HashMap<>();
        // 获取客户备案类型
        ResultVo<List<DataCodeVO>> dataCodes = dictDataService.getDataCodes(com.smc.smccloud.model.Constants.CUSTOMER_WLDATE_TYPE_CODE);
        if (!dataCodes.isSuccess()) {
            return ResultVo.failure("获取备案类型数据失败");
        }
        List<DataCodeVO> data = dataCodes.getData();
        for (DataCodeVO item : data) {
            wldTypeMap.put(item.getCode(), item.getCodeName());
        }

        PageInfo<OpsCustomerWldateVO> pageInfo = PageHelper.startPage(request.getPage().getPageNumber(), request.getPage().getPageSize()).doSelectPageInfo(() -> {
            opsCustomerWldateMapper.getCustomerWldateList(request);
        });

        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String, String> map = new HashMap<>();
            for (OpsCustomerWldateVO item : pageInfo.getList()) {
                // 部门
                // update by LiYingChao from bug 8759 in 2022-12-26
                item.setDeptNo(StringUtils.isBlank(item.getHlCode()) ? item.getHrUnitId() : item.getHlCode());
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    item.setDeptNo("[" + item.getDeptNo() + "]" + item.getDeptName());
                }
//                if (StringUtils.isNotBlank(item.getDeptNo())) {
//                    if (!map.containsKey(item.getDeptNo())) {
//                        ResultVo<String> deptNameByNo = departmentService.getDeptNameByNo(item.getDeptNo());
//                        map.put(item.getDeptNo(),"["+item.getDeptNo()+"]"+deptNameByNo.getData());
//                    }
//                   item.setDeptNo(map.get(item.getDeptNo()));
//                }

                // 客户担当
                if (StringUtils.isNotBlank(item.getPsnSmcId())) {
                    if (!map.containsKey(item.getPsnSmcId())) {
                        ResultVo<String> employeeNameByNo = employeeService.getEmployeeNameByNo(item.getPsnSmcId());
                        map.put(item.getPsnSmcId(), employeeNameByNo.getData());
                    }
                    item.setPsnSmcIdName(map.get(item.getPsnSmcId()));
                }
                // 备案类型
                if (item.getIsWldate() != null) {
//                    String codeNameByCode = CustomerWldateEnum.getCodeNameByCode(item.getIsWldate());
                    item.setWldTypeName("[" + item.getIsWldate() + "]" + wldTypeMap.get(String.valueOf(item.getIsWldate())));
                }
                if (StringUtils.isNotBlank(item.getModifier())) {
                    if (!map.containsKey(item.getModifier())) {
                        ResultVo<String> employeeNameByNo = employeeService.getEmployeeNameByNo(item.getModifier());
                        map.put(item.getModifier(), "[" + item.getModifier() + "]" + employeeNameByNo.getData());
                    }
                    item.setModifier(map.get(item.getModifier()));
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    // update by LiYingChao from bug 8759 in 2022-12-27
    public ResultVo<String> delCustomerWldByCustomerNos(CustomerWldDelVO customerWldDelVO) {
        List<OpsCustomerWldateVO> customerNoList = customerWldDelVO.getCustomerNoList();
        if (CollectionUtils.isEmpty(customerNoList)) {
            return ResultVo.failure("请选择所需删除的数据");
        }
        if (StringUtils.isBlank(customerWldDelVO.getLoginUserNo())) {
            return ResultVo.failure("操作人为空.");
        }
        StringBuilder msg = new StringBuilder();
        Date now = new Date();

        LambdaUpdateWrapper<OpsCustomerWldateDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsCustomerWldateVO customerNo : customerNoList) {
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsCustomerWldateDO::getCustomerNo, customerNo.getCustomerNo())
                    .eq(OpsCustomerWldateDO::getIsWldate, customerNo.getIsWldate())
                    .set(OpsCustomerWldateDO::getDelFlag, 1) // 1 已删除
                    .set(OpsCustomerWldateDO::getModifyTime, now)
                    .set(OpsCustomerWldateDO::getModifier, customerWldDelVO.getLoginUserNo());
            try {
                opsCustomerWldateMapper.update(null, updateWrapper);
            } catch (Exception e) {
                log.error("删除客户备案" + customerNo + "发生异常", e);
                msg.append(customerNo + "，");
            }
        }
        if (msg.length() > 0) {
            return ResultVo.failure(msg.append("删除失败").toString());
        }
        return ResultVo.success("删除成功.");
    }

    @Override
    public ResultVo<OpsCustomerWldateVO> getCustomerInfoByCustomerNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.failure("请输入客户编码");
        }

        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CustomerDO::getCustomerNo, CustomerDO::getName, CustomerDO::getHlCode, CustomerDO::getHRUnitId, CustomerDO::getPSNSMCID)
                .eq(CustomerDO::getCustomerNo, customerNo);
        CustomerDO customerDO = customerMapper.selectOne(queryWrapper);
        if (customerDO == null) {
            return ResultVo.failure(customerNo + "没有此客户信息");
        }
        OpsCustomerWldateVO item = new OpsCustomerWldateVO();
        item.setCustomerNo(customerDO.getCustomerNo());
        item.setCustomerName(customerDO.getName());
        String deptNo = StringUtils.isBlank(customerDO.getHlCode()) ? customerDO.getHRUnitId() : customerDO.getHlCode();
        ResultVo<String> deptNameByNo = departmentService.getDeptNameByNo(deptNo);
        if (deptNameByNo.isSuccess()) {
            item.setDeptNo("[" + deptNo + "]" + deptNameByNo.getData());
        } else {
            item.setDeptNo(deptNo);
        }
        if (StringUtils.isNotBlank(customerDO.getPSNSMCID())) {
            ResultVo<String> resultVo = employeeService.getEmployeeNameByNo(customerDO.getPSNSMCID());
            String psnSmcIdName = "";
            if (resultVo.isSuccess()) {
                psnSmcIdName = resultVo.getData();
            }
            item.setPsnSmcIdName(psnSmcIdName);
        }
        item.setPsnSmcId(customerDO.getPSNSMCID());
        item.setModifyTime(new Date());
        return ResultVo.success(item);
    }

    @Override
    public void exportCustomerWldList(CustomerWldateRequest request) {

        if (request == null) {
            return;
        }
//        if (StringUtils.isNotBlank(request.getCustomerNo())) {
//            request.setCustomerNo(request.getCustomerNo()+"%");
//        }

        Map<String, String> wldTypeMap = new HashMap<>();
        // 获取客户备案类型
        ResultVo<List<DataCodeVO>> dataCodes = dictDataService.getDataCodes(com.smc.smccloud.model.Constants.CUSTOMER_WLDATE_TYPE_CODE);
        if (!dataCodes.isSuccess()) {
            return;
        }
        List<DataCodeVO> data = dataCodes.getData();
        for (DataCodeVO item : data) {
            wldTypeMap.put(item.getCode(), item.getCodeName());
        }

        List<OpsCustomerWldateVO> customerWldateList = opsCustomerWldateMapper.getCustomerWldateList(request);

        if (CollectionUtils.isNotEmpty(customerWldateList)) {
            String path = "templates/" + com.smc.smccloud.model.Constants.CUSTOMER_WLD_EXPORT_FILENAME;
            ExcelUtil excel = new ExcelUtil(path);
            excel.openSheet(0);
            // 向模板中写入数据
            int row = 1;
            int cel;
            Map<String, String> map = new HashMap<>();
            for (OpsCustomerWldateVO item : customerWldateList) {
                // 部门
                item.setDeptNo(StringUtils.isBlank(item.getHlCode()) ? item.getHrUnitId() : item.getHlCode());
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    item.setDeptNo("[" + item.getDeptNo() + "]" + item.getDeptName());
                }
//                item.setDeptNo(StringUtils.isBlank(item.getHlCode()) ? item.getHrUnitId():item.getHlCode());
//                if (StringUtils.isNotBlank(item.getDeptNo())) {
//                    if (!map.containsKey(item.getDeptNo())) {
//                        ResultVo<String> deptNameByNo = departmentService.getDeptNameByNo(item.getDeptNo());
//                        map.put(item.getDeptNo(),"["+item.getDeptNo()+"]"+deptNameByNo.getData());
//                    }
//                    item.setDeptNo(map.get(item.getDeptNo()));
//                }

                // 客户担当
                if (StringUtils.isNotBlank(item.getPsnSmcId())) {
                    if (!map.containsKey(item.getPsnSmcId())) {
                        ResultVo<String> employeeNameByNo = employeeService.getEmployeeNameByNo(item.getPsnSmcId());
                        map.put(item.getPsnSmcId(), employeeNameByNo.getData());
                    }
                    item.setPsnSmcIdName(map.get(item.getPsnSmcId()));
                }
                // 备案类型
                if (item.getIsWldate() != null) {
//                    String codeNameByCode = CustomerWldateEnum.getCodeNameByCode(item.getIsWldate());
                    item.setWldTypeName("[" + item.getIsWldate() + "]" + wldTypeMap.get(String.valueOf(item.getIsWldate())));
                }
                if (StringUtils.isNotBlank(item.getModifier())) {
                    if (!map.containsKey(item.getModifier())) {
                        ResultVo<String> employeeNameByNo = employeeService.getEmployeeNameByNo(item.getModifier());
                        map.put(item.getModifier(), "[" + item.getModifier() + "]" + employeeNameByNo.getData());
                    }
                    item.setModifier(map.get(item.getModifier()));
                }

                cel = 0;
                excel.setCellValue(row, cel++, item.getDeptNo());
                excel.setCellValue(row, cel++, item.getCustomerNo());
                excel.setCellValue(row, cel++, item.getCustomerName());
                excel.setCellValue(row, cel++, item.getPsnSmcId());
                excel.setCellValue(row, cel++, item.getPsnSmcIdName());
                excel.setCellValue(row, cel++, item.getWldTypeName());
                excel.setCellValue(row, cel++, item.getModifyTime());
                excel.setCellValue(row, cel, item.getModifier());
                row++;
            }
            excel.writeToResponse(response, com.smc.smccloud.model.Constants.CUSTOMER_WLD_EXPORT_FILENAME);
        }
    }

    @Override
    // update by LiYingChao from bug 8759 in 2022-12-27
    public void downLoadExcel() {
        String path = "templates/" + com.smc.smccloud.model.Constants.CUSTOMER_WLD_FILENAME;
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, com.smc.smccloud.model.Constants.CUSTOMER_WLD_FILENAME);
    }

    @Override
    public ResultVo<String> addCustomerWldInfo(OpsCustomerWldateVO opsCustomerWldateVO) {

        if (opsCustomerWldateVO == null) {
            return ResultVo.failure("新增数据不可为空");
        }
        if (StringUtils.isBlank(opsCustomerWldateVO.getCustomerNo()) || opsCustomerWldateVO.getIsWldate() == null) {
            return ResultVo.failure("客户编码/备案类型不可为空.");
        }

        LambdaQueryWrapper<OpsCustomerWldateDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OpsCustomerWldateDO::getCustomerNo, opsCustomerWldateVO.getCustomerNo())
                .eq(OpsCustomerWldateDO::getIsWldate, opsCustomerWldateVO.getIsWldate())
        ;
        OpsCustomerWldateDO customerWldateDO = opsCustomerWldateMapper.selectOne(lambdaQueryWrapper);
        if (customerWldateDO == null) {
            OpsCustomerWldateDO item = new OpsCustomerWldateDO();
            item.setCustomerNo(opsCustomerWldateVO.getCustomerNo());
            item.setIsWldate(opsCustomerWldateVO.getIsWldate());
            item.setDelFlag(0);
            item.setCreTime(new Date());
            item.setCreator(opsCustomerWldateVO.getModifier());
            item.setModifyTime(new Date());
            item.setModifier(opsCustomerWldateVO.getModifier());
            item.setVersion(0);
            try {
                opsCustomerWldateMapper.insert(item);
            } catch (Exception e) {
                log.error("新增客户备案异常:", e);
                throw new BusinessException("新增客户备案失败");
            }
        } else {
            LambdaUpdateWrapper<OpsCustomerWldateDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper
                    .eq(OpsCustomerWldateDO::getCustomerNo, opsCustomerWldateVO.getCustomerNo())
                    .eq(OpsCustomerWldateDO::getIsWldate, opsCustomerWldateVO.getIsWldate())
                    .set(OpsCustomerWldateDO::getModifyTime, new Date())
                    .set(OpsCustomerWldateDO::getModifier, opsCustomerWldateVO.getModifier())
                    .set(OpsCustomerWldateDO::getDelFlag, 0);
            opsCustomerWldateMapper.update(null, lambdaUpdateWrapper);
        }
        return ResultVo.success("新增成功.");
    }

    @Override
    public ResultVo<String> importCustomerWld(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.");
        }

        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        OpsCustomerWldateDO item;
        StringBuilder msg = new StringBuilder();
        int count = 0;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String customerNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(customerNo)) {
                continue;
            }
            String wdlType = excel.getCellString(rows.getCell(5));
            if (StringUtils.isBlank(wdlType)) {
                msg.append(customerNo + "的客户备案类型不可为空;");
                continue;
            }

            LambdaQueryWrapper<OpsCustomerWldateDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OpsCustomerWldateDO::getCustomerNo, customerNo).eq(OpsCustomerWldateDO::getIsWldate, Integer.parseInt(wdlType));
            OpsCustomerWldateDO customerWldateDO = opsCustomerWldateMapper.selectOne(lambdaQueryWrapper);
            if (customerWldateDO == null) {
                item = new OpsCustomerWldateDO();
                item.setCustomerNo(customerNo);
                item.setIsWldate(Integer.parseInt(wdlType));
                item.setDelFlag(0);
                item.setCreTime(new Date());
                item.setCreator(loginUser);
                item.setModifyTime(new Date());
                item.setModifier(loginUser);
                item.setVersion(0);
                try {
                    opsCustomerWldateMapper.insert(item);
                } catch (Exception e) {
                    msg.append(customerNo + "写入失败;");
                    continue;
                }
            } else {
                LambdaUpdateWrapper<OpsCustomerWldateDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper
                        .eq(OpsCustomerWldateDO::getCustomerNo, customerNo)
                        .eq(OpsCustomerWldateDO::getIsWldate, Integer.parseInt(wdlType))
                        .set(OpsCustomerWldateDO::getModifyTime, new Date())
                        .set(OpsCustomerWldateDO::getModifier, loginUser)
                        .set(OpsCustomerWldateDO::getDelFlag, 0);
                opsCustomerWldateMapper.update(null, lambdaUpdateWrapper);
            }
            count++;
        }
        if (StringUtils.isBlank(msg.toString())) {
            return ResultVo.success("导入完毕,共计导入" + count + "条");
        } else {
            return ResultVo.success("导入完毕,共计导入" + count + "条, 导入失败: " + msg.toString());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @DS("opsdb")
    // update bu LiYingChao from bug 8759 in 2022-12-26
    public ResultVo<String> batchAddCustomerWld(BatchAddCustomerWldVO batchAddCustomerWldVO) {

        if (batchAddCustomerWldVO == null) {
            return ResultVo.failure("参数实体为空");
        }
        if (CollectionUtils.isEmpty(batchAddCustomerWldVO.getCustomerNos())) {
            return ResultVo.failure("请输入需要新增的客户编码");
        }

        List<OpsCustomerWldateVO> customerNos = batchAddCustomerWldVO.getCustomerNos();

        // 去重避免重复添加导致报错
        customerNos = customerNos.stream().filter(Objects::nonNull).filter(i -> StringUtils.isNotBlank(i.getCustomerNo())).collect(Collectors.toList());
        customerNos = customerNos.stream().distinct().collect(Collectors.toList());

        OpsCustomerWldateDO customerWldateDO;
        Date now = new Date();
        try {
            for (OpsCustomerWldateVO customerData : customerNos) {
                LambdaQueryWrapper<OpsCustomerWldateDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(OpsCustomerWldateDO::getCustomerNo, customerData.getCustomerNo())
                        .eq(OpsCustomerWldateDO::getIsWldate, customerData.getIsWldate())
                ;
                OpsCustomerWldateDO exists = opsCustomerWldateMapper.selectOne(lambdaQueryWrapper);
                if (exists == null) {
                    customerWldateDO = new OpsCustomerWldateDO();
                    customerWldateDO.setCustomerNo(customerData.getCustomerNo());
                    customerWldateDO.setIsWldate(customerData.getIsWldate());
                    customerWldateDO.setDelFlag(0);
                    customerWldateDO.setCreTime(now);
                    customerWldateDO.setCreator(batchAddCustomerWldVO.getLoginUserNo());
                    customerWldateDO.setModifyTime(now);
                    customerWldateDO.setModifier(batchAddCustomerWldVO.getLoginUserNo());
                    customerWldateDO.setVersion(0);
                    opsCustomerWldateMapper.insert(customerWldateDO);
                } else {
                    LambdaUpdateWrapper<OpsCustomerWldateDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper
                            .eq(OpsCustomerWldateDO::getCustomerNo, customerData.getCustomerNo())
                            .eq(OpsCustomerWldateDO::getIsWldate, customerData.getIsWldate())
                            .set(OpsCustomerWldateDO::getModifyTime, new Date())
                            .set(OpsCustomerWldateDO::getModifier, batchAddCustomerWldVO.getLoginUserNo())
                            .set(OpsCustomerWldateDO::getDelFlag, 0);
                    opsCustomerWldateMapper.update(null, lambdaUpdateWrapper);
                }
            }
        } catch (Exception e) {
            log.error("批量新增客户备案发生异常:", e);
            throw new BusinessException("批量新增客户备案发生异常"+ e);
        }
        return ResultVo.success("新增成功.");
    }

    @Override
    public ResultVo<String> BatchTranslateCustomerNameToEnglish() {
        List<CustomerDO> list = customerMapper.queryEnameIsNullList();
        StringBuilder str = new StringBuilder();
        List<String> customerNoList = new ArrayList<>();
        String resultStr;
        int errTryCount = 0;
        ResultVo<TranslateCustomerNameVO> resultVo;
        StringBuilder msg = new StringBuilder();
        int upOkNum = 0;
        int upErrorNum = 0;
        for (CustomerDO item : list) {
            if (PublicUtil.getWordByteLen(str.toString()) < 2000) {
                str.append(item.getName() + "#");
                customerNoList.add(item.getCustomerNo());
            } else {
                resultStr = FanYiUtil.converToEN(str.toString());
                if (StringUtils.isBlank(resultStr)) {
                    errTryCount++;
                    log.info("客户名称翻译接口为空数据: {}, 客户编码: {}", resultStr, JSONObject.toJSONString(customerNoList));
                    continue;
                }
                if (errTryCount == 3) {
                    return ResultVo.failure("百度翻译API接口翻译字符已经达到上限5w/月");
                }
                String[] split = resultStr.split("#");
                resultVo = updateCustomerName(customerNoList, Arrays.asList(split));
                log.info("翻译客户名称结果: {}", JSONUtil.toJsonPrettyStr(resultVo));
                if (resultVo.isSuccess()) {
                    upOkNum = upOkNum + resultVo.getData().getOkNum();
                    upErrorNum = upErrorNum + resultVo.getData().getErrorNum();
                    msg.append(resultVo.getData().getErrMsg() + ";");
                }
                customerNoList = new ArrayList<>();
                str = new StringBuilder();
            }
        }
        return ResultVo.success("翻译成功" + upOkNum + "条,翻译失败:" + upErrorNum + ",失败详情客户编码:" + msg.toString());
    }

    @Override
    public ResultVo<String> translateCustomerNameToEnglish() {
       return ResultVo.failure("接口不可用");
    }

    public ResultVo<TranslateCustomerNameVO> updateCustomerName(List<String> listCustomerNo, List<String> aliasEname) {
        if (CollectionUtils.isEmpty(listCustomerNo)) {
            return ResultVo.failure("无需要更新客户英文名称的数据");
        }
        if (CollectionUtils.isEmpty(aliasEname)) {
            return ResultVo.failure("英文名称翻译异常.空数据");
        }
        StringBuilder errMsg = new StringBuilder();
        int okCount = 0;
        int errCount = 0;
        LambdaUpdateWrapper<CustomerDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (int i = 0; i < listCustomerNo.size(); i++) {
            String customerNo = listCustomerNo.get(i);
            String ename = aliasEname.get(i);
            if (StringUtils.isBlank(customerNo) || StringUtils.isBlank(ename)) {
                continue;
            }
            updateWrapper.clear();
            updateWrapper.eq(CustomerDO::getCustomerNo, customerNo).set(CustomerDO::getAliasEname, ename);
            try {
                customerMapper.update(null, updateWrapper);
                okCount++;
            } catch (Exception e) {
                errCount++;
                errMsg.append(customerNo + "转换" + ename + "失败;");
            }
        }
        TranslateCustomerNameVO translateCustomerNameVO = new TranslateCustomerNameVO();
        translateCustomerNameVO.setOkNum(okCount);
        translateCustomerNameVO.setErrorNum(errCount);
        translateCustomerNameVO.setErrMsg(errMsg.toString());
        return ResultVo.success(translateCustomerNameVO);

    }

}

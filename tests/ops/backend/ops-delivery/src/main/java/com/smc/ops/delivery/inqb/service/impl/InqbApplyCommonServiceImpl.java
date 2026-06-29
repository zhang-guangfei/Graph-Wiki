package com.smc.ops.delivery.inqb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inqb.*;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.sales.ops.feign.inqb.InqbGroupPurchaseFeignApi;
import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.ops.delivery.inqb.service.InqbApplyHandleService;
import com.smc.ops.delivery.inquiry.mapper.InquiryWorkDayYearMapper;
import com.smc.ops.delivery.inquiry.utils.InquiryUtils;
import com.smc.ops.delivery.mapper.DictDataMapper;
import com.smc.ops.delivery.mapper.inqb.InqbApplyMapper;
import com.smc.ops.delivery.mapper.inqb.InqbCodeConfigMapper;
import com.smc.ops.delivery.mapper.inqb.InqbDetailMapper;
import com.smc.ops.delivery.mapper.inqb.InqbOpscoreMapper;
import com.smc.ops.delivery.model.DataTypeDO;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.model.inqb.OpsInqbApplyExcelVO;
import com.smc.ops.delivery.model.inqb.OpsInqbCodeConfigDO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 催促订单处理
 *
 * @author B91717
 * @date 2023-12-05
 */
@Service
@Slf4j
public class InqbApplyCommonServiceImpl implements InqbApplyCommonService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Resource
    private InqbApplyMapper inqbApplyMapper;

    @Resource
    private InqbDetailMapper detailsMapper;


    @Resource
    private RedisManager redisManager;

    @Resource
    private InquiryWorkDayYearMapper tblWorkDayYearMapper;

    @Resource
    private InqbOpscoreMapper inqbOpscoreMapper;

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private InqbCodeConfigMapper inqbCodeConfigMapper;

    @Resource
    private InqbGroupPurchaseFeignApi inqbGroupPurchaseFeignApi;
    @Resource
    private InqbApplyHandleService inqbApplyHandleService;

    /**
     * 前端查询INQB申请单，条件查询
     *
     * @param inqbQueryRequest
     * @param page
     * @return
     */
    @Override
    public ResultVo<PageInfo<OpsInqbQueryInfo>> queryInqbApplyInfo(InqbQueryRequest inqbQueryRequest, Page page) throws Exception {
        // 判断开始日期和结束日期
        if (inqbQueryRequest.getStartDate() != null || inqbQueryRequest.getEndDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(inqbQueryRequest.getEndDate());
            cal.add(Calendar.DATE, 1);
            inqbQueryRequest.setEndDate(cal.getTime());
        }
        PageInfo<OpsInqbQueryInfo> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> inqbApplyMapper.queryInqbInfo(inqbQueryRequest));
        // 如果查询条件不为空，根据主单去检索子表数据
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<OpsInqbQueryInfo> opsInqbApplyList = pageInfo.getList();
            for (OpsInqbQueryInfo opsInqbQueryInfo : opsInqbApplyList) {
                // 根据主单查询子项明细信息
                List<OpsInqbDetail> opsInqbDetailList = detailsMapper.getInqbDetailByApplyNo(opsInqbQueryInfo.getInqbApplyNo());
                if (!CollectionUtils.isEmpty(opsInqbDetailList)) {
                    opsInqbQueryInfo.setOpsInqbDetailList(opsInqbDetailList);
                }
            }
            pageInfo = new PageInfo<>(opsInqbApplyList);
        }
        return ResultVo.success(pageInfo);
    }


    @Override
    public void exportInqbApplyInfoExcel(InqbQueryRequest dto, HttpServletResponse response) {
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        if (endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.YEAR, -2);
            Date startDateLimit = cal.getTime();
            if (dto.getStartDate() == null || dto.getStartDate().before(startDateLimit)) {
                dto.setStartDate(startDateLimit);
            }
        }else {
            if(startDate != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.YEAR, 2);
                Date endDateLimit = cal.getTime();
                dto.setEndDate(endDateLimit);
            }else{
                Date today = DateUtil.getToday();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.YEAR, -2);
                Date startDateLimit = cal.getTime();
                dto.setStartDate(startDateLimit);
                dto.setEndDate(today);
            }
        }
        //end晚一天，以查询当天全天的数据
        Calendar cal = Calendar.getInstance();
        cal.setTime(dto.getEndDate());
        cal.add(Calendar.DATE, 1);
        dto.setEndDate(cal.getTime());

        List<OpsInqbQueryInfo> list = inqbApplyMapper.queryInqbInfo(dto);
        List<OpsInqbCodeConfig> configs = new ArrayList<>();
        try {
            configs = inqbApplyHandleService.getInqbCodeConfig();
        } catch (Exception e) {
            log.error("查询InqbClassify失败");
        }
        List<OpsInqbApplyExcelVO> excelList = new ArrayList<>();
        OpsInqbApplyExcelVO vo;
        // bug19099 INQ-B导出数据无供应商，OPS界面有，导出页面的修改
        // 1. 变更字段名称：供应商→期望供应商
        // 2. 导出数据增加供应商，另外增加拆分型号、拆分数量、子项申请号的字段；2.1 没有拆分时这三个字段可空，供应商显示整型号供应商；2.2有拆分时，供应商按各拆分型号匹配导出。
        // 查询供应商清单
        Map<String, Supplier> supplierMap = this.getSupplierInfoBycode();
        for (OpsInqbQueryInfo info : list) {
            // 根据主表的数据，去查询明细项数据
            List<OpsInqbDetail> opsInqbDetailList = detailsMapper.getInqbDetailByApplyNo(info.getInqbApplyNo());
            for (OpsInqbDetail detail : opsInqbDetailList) {
                vo = new OpsInqbApplyExcelVO();
                BeanUtil.copyProperties(info, vo);
                if (info.getInqbStatus() != null) {
                    InqbStatusEnum parse = InqbStatusEnum.parse(info.getInqbStatus());
                    if (parse != null) {
                        vo.setInqbStatus(parse.getDesc());
                    }
                }
                if (StringUtils.isNotBlank(info.getInqbValidity())) {
//                InqbValidityEnum parse = InqbValidityEnum.parse(info.getInqbClassify());
                    // bug17611 INQ-B导出问题,转换字段错误
                    InqbValidityEnum parse = InqbValidityEnum.parse(info.getInqbValidity());
                    if (parse != null) {
                        vo.setInqbValidity(parse.getDesc());
                    }
                }
                if (StringUtils.isNotBlank(info.getInqbClassify())) {
                    for (OpsInqbCodeConfig config : configs) {
                        if (StringUtils.equals(config.getOpsReasonCode(), info.getInqbClassify())) {
                            vo.setInqbClassify(config.getOpsReasonDesc());
                            break;
                        }
                    }
                }
                // bug19099 增加子项导出数据
                if (StringUtils.isNotBlank(detail.getSplitType())){
                    vo.setSplitType(InqbSplitTypeEnum.parse(detail.getSplitType()).getSplitDesc());
                }
                if (StringUtils.isNotBlank(detail.getSupplierCode()) && supplierMap.containsKey(detail.getSupplierCode())) {
                    vo.setSupplierCode(supplierMap.get(detail.getSupplierCode()).getName()); //需要翻译
                }
                vo.setSubModel(detail.getModelNo()); // 拆分型号
                vo.setSubQty(detail.getQuantity());
                vo.setTaskNo(detail.getTaskNo());
                if (StringUtils.isNotBlank(detail.getHandleResult())) {
                    vo.setHandleResult(detail.getHandleResult().equals("1") ? "是" : "否"); // 需要翻译
                }
                vo.setReplySupplierDept(detail.getReplySupplierDept());
                vo.setReplyNo(detail.getReplyNo());
                vo.setReplyDeliveryDays(detail.getReplyDeliveryDays());
                vo.setReplyUser(detail.getReplyUser());
                vo.setReplyTime(detail.getReplyTime());
                vo.setReplyReasonCode(detail.getReplyReasonCode());
                vo.setReplyReason(detail.getReplyReason());
                vo.setReplyRemark(detail.getReplyRemark());
                excelList.add(vo);
            }
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("数据导出", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), OpsInqbApplyExcelVO.class)
                    .sheet("数据导出")
                    .doWrite(excelList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成INQB申请单号
     *
     * @return
     */
    @Override
    public ResultVo<String> generateInqbApplyNo() {
        String inquiryCode = InqbCommonEnum.APPLYNOPREFIX.getType();
        String localDate = LocalDate.now().format(DATE_FORMATTER); // 使用线程安全的DateTimeFormatter
        String key = InqbCommonEnum.APPLYNOREDIS.getType(); // redis key
        String applnoReturn = inqbApplyMapper.getMaxApplyNo(localDate); // 获取当前最大申请号
        try {
            if (redisManager.hasKey(key)) {
                Object redisNow = redisManager.get(key);
                // redis中是否存在有效的申请号
                if (redisNow != null && StringUtils.isNotBlank(redisNow.toString())) {
                    applnoReturn = redisNow.toString();
                }
            }
        } catch (Exception e) {
            log.error("获取INQB申请号失败: {}", e.getMessage(), e);
        }
        // 递增申请号
        applnoReturn = InquiryUtils.createIncrementId(inquiryCode, new Date(), "yyyyMMdd", 5, applnoReturn);
        try {
            // 尽力尝试回写Redis，即使失败也不影响申请号的返回
            redisManager.set(key, applnoReturn, 60 * 60 * 24); // 回写redis
        } catch (Exception ex) {
            log.error("INQB申请号写入Redis失败，原因：{} {}", ex.getMessage(), ex);
        }
        return ResultVo.success(applnoReturn);
    }

    /**
     * 回复当日开始可用，有效期为回复时间次日起的5个工作日均有效，第5个工作日的23:59:59失效；
     *
     * @param info
     * @return
     */
    @Override
    public ResultVo<Date> calExpirationDate(InquiryWorkdayCondition info) {
        Date result = null; // 默认为 工作日
        if (info.getDay() == null || StringUtils.isBlank(info.getCountry())) {
            return ResultVo.failure("请求参数为空，请补充后重试");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TblWorkdayYear workdayYear = tblWorkDayYearMapper.getWorkDay(dateFormat.format(info.getDay()));
        // 默认
        if (workdayYear == null) {
            return ResultVo.failure("当前日期，未维护节假日信息，请联系IT");
        }
        if (info.getCountry().equalsIgnoreCase("JP")) {
            result = tblWorkDayYearMapper.getJPWorkDayDiff(dateFormat.format(info.getDay()), Integer.parseInt(InqbCommonEnum.DATEDEADLINE.getType()));
        } else {
            result = tblWorkDayYearMapper.getCNWorkDayDiff(dateFormat.format(info.getDay()), Integer.parseInt(InqbCommonEnum.DATEDEADLINE.getType()));
        }
        // bug14986 第五个工作日，23:59:59，再加一个自然日即为过期日
        if (result != null) {
            // bug15425 设置过期时间为23:59:59
            result = InquiryUtils.getDayTimeEnd(result);
        }
        return ResultVo.success(result);
    }

    @Override
    public OpsCustomer getCustomerByCustomerNo(String customerNo) {
        Object customer = redisManager.hGet(Constants.REDIS_ALL_CUSTOMER_INFO, customerNo);
        if (customer == null) {
            OpsCustomer customerDO = inqbOpscoreMapper.getOpsCustomerByNo(customerNo);
            if (customerDO != null) {
                redisManager.hPut(InqbConstants.REDIS_ALL_CUSTOMER_INFO, customerNo, JSON.toJSONString(customerDO));
                return customerDO;
            }
        } else {
            return JSONObject.parseObject(customer.toString(), OpsCustomer.class);
        }
        return null;
    }


    public ResultVo<String> findDeptNoByHRSalesDeptNo(String customerDept) {
        if (StringUtils.isBlank(customerDept)) {
            return ResultVo.failure("入参不可为空");
        }
        HrOrganization hrOrganizationDO = inqbOpscoreMapper.getHLOrganizationById(customerDept);
        if (hrOrganizationDO == null) {
            return ResultVo.success(customerDept);
        }
        return ResultVo.success(hrOrganizationDO.getParentid());
    }


    @Override
    public ResultVo<OrganizationDto> getHrOrganMasterInfoByCode(String code) {
        OrganizationDto vo = new OrganizationDto();
        try {
            // HL代码提一级
            ResultVo<String> resultVo = findDeptNoByHRSalesDeptNo(code);
            code = resultVo.getData();
            Object object = redisManager.hGet(InqbConstants.OPS_ORGANIZATION_MASTER, code);
            if (ObjectUtils.isNotEmpty(object)) {
                vo = JSONObject.parseObject(object.toString(), OrganizationDto.class);
            } else {
                HrOrganization organizationDO = inqbOpscoreMapper.getHrOrganizationById(code);
                if (ObjectUtils.isNotEmpty(organizationDO)) {
                    String[] names = organizationDO.getFullname().split("_");
                    String[] unitCodes = organizationDO.getUnitcode().split("!");
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
                    } else if (organizationDO.getLevel().contains("本部")) {
                        vo.setParentNo(unitCodes[idCount - 1]);
                        vo.setParentName(names[idCount - 1]);
                        vo.setCompanyNo(unitCodes[idCount - 2]);
                        vo.setCompanyName(names[idCount - 2]);
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
            }
            return ResultVo.success(vo);
        } catch (Exception e) {
            log.error("获取机构信息失败，原因：{}", e.getMessage());
            return ResultVo.failure("获取机构信息失败");
        }
    }

    @Override
    public DataTypeVO getInqbDataCodesInfo(String classCode, String code) {
        LambdaQueryWrapper<DataTypeDO> query = new LambdaQueryWrapper<>();
        query.eq(DataTypeDO::getClassCode, classCode).eq(DataTypeDO::getCode, code);
        DataTypeDO dataTypeDO = dictDataMapper.selectOne(query);
        if (PublicUtil.isEmpty(dataTypeDO)) {
            return null;
        }
        try {
            return BeanCopyUtil.copy(dataTypeDO, DataTypeVO.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResultVo<Boolean> updateInqbExtNote1(Long id, String extNote1, String curExtNote1) {
        LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DataTypeDO::getId, id).eq(DataTypeDO::getExtNote1, curExtNote1);
        DataTypeDO updated = new DataTypeDO();
        updated.setExtNote1(extNote1);
        if (1 == dictDataMapper.update(updated, updateWrapper)) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新失败");
    }


    /**
     * 根据供应商及催促分类，获取供应商对应的催促原因
     *
     * @param codetype   发送/回复
     * @param supplyCode 供应商代码
     * @return
     */
    @Override
    public Map<String, OpsInqbCodeConfigDO> getCodecinfigBySuppily(String codetype, String supplyCode) {
        LambdaQueryWrapper<OpsInqbCodeConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsInqbCodeConfigDO::getCodeType, codetype)
                .eq(OpsInqbCodeConfigDO::getSupplieCode, supplyCode)
                .eq(OpsInqbCodeConfigDO::getIsDeleted, false);
        List<OpsInqbCodeConfigDO> list = inqbCodeConfigMapper.selectList(queryWrapper);
        Map<String, OpsInqbCodeConfigDO> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            if (InqbCodeTypeConfigEnum.SEND.getType().equalsIgnoreCase(codetype)) {  // 校验是否为发送配置
                for (OpsInqbCodeConfigDO opsInqbCodeConfig : list) {
                    result.put(opsInqbCodeConfig.getOpsReasonCode(), opsInqbCodeConfig); // 构建配置map
                }
            } else { // 回复配置
                for (OpsInqbCodeConfigDO opsInqbCodeConfig : list) {
                    result.put(opsInqbCodeConfig.getSupplieReasonCode(), opsInqbCodeConfig); // 构建配置map
                }
            }
        }
        return result;
    }

    @Override
    public ResultVo<List<OpsInqbCodeConfig>> getInqbCodeConfigFromRedis() {
        String key = InqbCommonEnum.CODECONFIGKEY.getType();
        try {
            Object redisObj = redisManager.get(key);
            if (redisObj != null) {
                return ResultVo.success(JSON.parseArray(redisObj.toString(), OpsInqbCodeConfig.class));
            } else {
                List<OpsInqbCodeConfig> opsInqbCodeConfigs = null;
                ResultVo<List<OpsInqbCodeConfig>> resultVo = inqbGroupPurchaseFeignApi.getInqbReason();
                if (resultVo.isSuccess()) {
                    opsInqbCodeConfigs = resultVo.getData();
                    redisManager.set(key, JSON.toJSONString(opsInqbCodeConfigs), 60 * 60 * 24);
                    return ResultVo.success(opsInqbCodeConfigs);
                }
            }
        } catch (Exception ex) {
            log.error("INQB申请号写入Redis失败，原因：{} {}", ex.getMessage(), ex);
            return ResultVo.failure("redis读取InqbCodeConfig信息失败");
        }
        return ResultVo.failure("redis中暂无InqbCodeConfig信息");
    }

    /**
     * 刷新InqbCodeConfig信息
     *
     * @return
     */
    @Override
    public ResultVo<String> refreshInqbCodeConfig() {
        String key = InqbCommonEnum.CODECONFIGKEY.getType();
        List<OpsInqbCodeConfig> opsInqbCodeConfigs = null;
        ResultVo<List<OpsInqbCodeConfig>> resultVo = inqbGroupPurchaseFeignApi.getInqbReason();
        if (resultVo.isSuccess()) {
            opsInqbCodeConfigs = resultVo.getData();
            redisManager.set(key, JSON.toJSONString(opsInqbCodeConfigs), 60 * 60 * 24);
        }
        return ResultVo.success("刷新成功");
    }

    /**
     * 根据最终用户以及营业所代码，来确定订单的所属区域，南方订单OR北方订单
     * bug18271 OPS给PMS发单计算南北方标识的修改
     */
    @Override
    public ResultVo<String> getOrderAreaInfo(String endUser, String deptNo) {
        if (StringUtils.isBlank(endUser) && StringUtils.isBlank(deptNo)) {
            return ResultVo.failure("请求参数错误，参数为空");
        }
        String returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
        if (StringUtils.isNotBlank(endUser)) {
            OpsCustomer opsCustomer = this.getCustomerByCustomerNo(endUser);
            if (opsCustomer != null) {
                String subjectivities = opsCustomer.getTradesubjectid(); // 获取客户所属交易主体
                Set<String> southList = new HashSet<String>(InqbDeptProvinceEnum.southList()); // 南方
                Set<String> northList = new HashSet<String>(InqbDeptProvinceEnum.northList()); // 北方
                if (southList.contains(subjectivities)) {
                    returnProvince = InqbDeptProvinceEnum.SHProvince.getDesc();
                } else if (northList.contains(subjectivities)) {
                    returnProvince = InqbDeptProvinceEnum.BJProvince.getDesc();
                } else {
                    returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
                }
            }
        } else { // 当客户代码为空时，根据营业所代码获取所属区域
            ResultVo<OrganizationDto> resultVo = this.getHrOrganMasterInfoByCode(deptNo);
            if (resultVo.isSuccess()) {
                OrganizationDto organizationDto = resultVo.getData();
                Set<String> southList = new HashSet<String>(InqbDeptProvinceEnum.deptSouthList()); // 南方
                Set<String> northList = new HashSet<String>(InqbDeptProvinceEnum.deptNorthList()); // 北方
                if (southList.contains(organizationDto.getCompanyName()) || southList.contains(organizationDto.getParentName())) {
                    returnProvince = InqbDeptProvinceEnum.SHProvince.getDesc();
                } else if (northList.contains(organizationDto.getCompanyName())  || northList.contains(organizationDto.getParentName())) {
                    returnProvince = InqbDeptProvinceEnum.BJProvince.getDesc();
                } else {
                    returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
                }
            }
        }
        return ResultVo.success(returnProvince);

    }

    public Map<String, Supplier> getSupplierInfoBycode() {
        List<Supplier> supplierList = inqbOpscoreMapper.getSupplierList(); // 查询供应商的数据
        Map<String, Supplier> result = new HashMap<String, Supplier>();
        for (Supplier supplier : supplierList) {
            result.put(supplier.getId(), supplier);
        }
        return result;
    }

}

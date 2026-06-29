package com.smc.smccloud.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.OpsOrderDelayMapper;
import com.smc.smccloud.mapper.OrderDelayDataMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.OpsDelayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OpsDelayOrderServiceImpl implements OpsDelayOrderService {

    @Autowired
    private OpsOrderDelayMapper opsOrderDelayMapper;
    @Autowired
    private OrderDelayDataMapper orderDelayDataMapper;
    @Autowired
    private OpsCommonService opsCommonService;
    @Autowired
    private DictCommonService dictDataService;
    @Value("${ops-file-upload-path.url}")
    private String filePath;

    public enum ProductTypeEnum {
        T1("1", "标准品"),
        T2("2", "简易特注品"),
        T3("3", "特注品"),
        T4("4", "集成型号"),
        T5("5", "维修品"),
        T6("6", "阀岛型号"),

        ;

        ProductTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        private String code;
        private String name;

        public static String getNameByCode(String code) {
            if (StringUtils.isBlank(code)) {
                return null;
            }
            for (ProductTypeEnum productType : ProductTypeEnum.values()) {
                if (productType.getCode().equals(code)) {
                    return productType.getName();
                }
            }
            return null;
        }
    }

    public enum CustomerTypeEnum {
        T0("0", "直销"),
        T1("1", "代销"),
        T2("2", "经销商"),
        ;

        CustomerTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        private String code;
        private String name;

        public static String getNameByCode(String code) {
            if (StringUtils.isBlank(code)) {
                return null;
            }
            for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
                if (type.getCode().equals(code)) {
                    return type.getName();
                }
            }
            return null;
        }
    }

    @Override
    public String exportDelayOrderInfo() {
        //执行存储过程计算订单延期数据
        opsOrderDelayMapper.calDelayOrder();
        //分页查询结果表数据
        List<OrderDelayDataDO> list = new ArrayList<>();
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(5000);
        LambdaQueryWrapper<OrderDelayDataDO> qw = new LambdaQueryWrapper<>();
        qw.gt(OrderDelayDataDO::getCreateTime, DateUtil.addMinute(new Date(), -10));
        qw.orderByAsc(OrderDelayDataDO::getOrderDate);
        while (true) {
            PageInfo<OrderDelayDataDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        orderDelayDataMapper.selectList(qw);
                    });
            list.addAll(pageInfo.getList());
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        //补充详细信息
        for (OrderDelayDataDO orderDelayDataDO : list) {
            orderDelayDataDO.setProductType(ProductTypeEnum.getNameByCode(orderDelayDataDO.getProductType()));
            CustomerVO customer = opsCommonService.getCustomerByCustomerNo(orderDelayDataDO.getCustomerNo());
            if (customer != null) {
                orderDelayDataDO.setCustomerName(customer.getName());
                //直销代销经销商
                orderDelayDataDO.setCustomerType(CustomerTypeEnum.getNameByCode(customer.getCustomerType()));
            }
            //最终用户
            CustomerVO endUser = opsCommonService.getCustomerByCustomerNo(orderDelayDataDO.getEndUser());
            if (endUser != null) {
                orderDelayDataDO.setEndUserName(endUser.getName());
                //营业所
                orderDelayDataDO.setSalesBranchNo(endUser.getHRUnitId());
                ResultVo<HROrganizationDO> hrOrganInfo = opsCommonService.getHrOrganInfoByCode(endUser.getHRUnitId());
                if (hrOrganInfo.isSuccess()) {
                    //营业所
                    HROrganizationDO branch = hrOrganInfo.getData();
                    if (branch != null) {
                        orderDelayDataDO.setSalesBranchName(branch.getName());
                        //营业部
                        HROrganizationDO salesDepartment = opsCommonService.findSalesDepartment(branch.getId());
                        if (salesDepartment != null) {
                            orderDelayDataDO.setSalesDeptNo(salesDepartment.getName());
                        }
                        //大区
                        HROrganizationDO region = opsCommonService.findRegion(branch.getId());
                        if (region != null) {
                            orderDelayDataDO.setRegionNo(region.getName());
                        }
                    }
                }
            }
            Integer status = Integer.valueOf(orderDelayDataDO.getStatus());
            RcvOrderStatusEnum statusEnum = RcvOrderStatusEnum.getEnumByType(status);
            if (statusEnum != null) {
                orderDelayDataDO.setStatus(statusEnum.getDesc());
            }
        }
        //发送邮件
        String pathFile = filePath + File.separator + "超期未发货提醒" + File.separator;
        //String pathFile = System.getProperty("user.dir") + File.separator + "超期未发货提醒" + File.separator;
        String fileName = pathFile + "超期未发货订单_" + DateUtil.dateToString(new Date()) + ".xlsx";
        EasyExcel.write(fileName, OrderDelayDataDO.class).sheet("超期订单").doWrite(list);
        log.info("文件保存成功：{}", fileName);
        return fileName;
    }

    @Override
    public void sendEmail(String fileName) throws OpsException {
        ResultVo<DataTypeVO> dataTypeResultVo = dictDataService.getDataTypeCodesInfo("9004", "DELAY");
        if (!dataTypeResultVo.isSuccess()) {
            throw Exceptions.OpsException("发送邮件失败，未查询到收件人");
        }
        DataTypeVO data = dataTypeResultVo.getData();
        if (data != null) {
            if (StringUtils.isNotBlank(data.getExtNote1())) {
                OpsMailDO opsMailDO = new OpsMailDO();
                opsMailDO.setMailTo(data.getExtNote1().replaceAll(";", ","));
                //抄送
                opsMailDO.setCc(data.getExtNote2().replaceAll(";", ","));
                opsMailDO.setSubject("超期未发货提醒");
                opsMailDO.setContext("<span>各位，好：</span></br><span>附件为今日超期未发货订单</span>");
                opsMailDO.setSendDate(new Date());
                opsMailDO.setBcc(data.getExtNote3().replaceAll(";", ","));
                opsMailDO.setStatus(SendStatusEnum.INIT.getType());
                opsMailDO.setFileUrls(fileName);
                opsMailDO.setInsertTime(new Date());
                opsCommonService.insertOpsMail(opsMailDO);
            }
        }

    }

}

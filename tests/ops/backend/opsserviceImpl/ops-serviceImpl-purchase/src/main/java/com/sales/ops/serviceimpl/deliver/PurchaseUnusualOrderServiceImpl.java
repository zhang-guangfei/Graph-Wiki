package com.sales.ops.serviceimpl.deliver;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsUnusualDao;
import com.sales.ops.dto.purchase.UnusualQueryDto;
import com.sales.ops.dto.purchase.UnusualViewDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.service.deliver.ChangeLogService;
import com.sales.ops.service.deliver.PurchaseUnusualOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.PurchaseConvertService;
import com.sales.ops.service.purchase.PurchaseCtcDelService;
import com.sales.ops.service.purchase.PurchaseSendEmail;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
public class PurchaseUnusualOrderServiceImpl implements PurchaseUnusualOrderService {

    @Autowired
    private OpsPoDeliveryUnusualLogMapper opsPoDeliveryUnusualLogMapper;
    @Autowired
    private OpsPoDeliveryUnusualMapper opsPoDeliveryUnusualMapper;
    @Autowired
    private ChangeLogService changeLogService;
    @Autowired
    private OpsPoDeliveryUnusualConfMapper opsPoDeliveryUnusualConfMapper;
    @Autowired
    private OpsUnusualDao opsUnusualDao;
    @Autowired
    private PurchaseConvertService purchaseConvertService;
    @Autowired
    private OpsPurchaseorderCancelLogMapper opsPurchaseorderCancelLogMapper;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private PurchaseSendEmail purchaseSendEmail;
    @Autowired
    private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;
    @Autowired
    private PurchaseCtcDelService purchaseCtcDelService;
    @Autowired
    DictDataServiceFeignApi dictDataServiceFeignApi;


    private static final List<String> SRC_DELIVERY_TIME = Arrays.asList("44/44/44", "99/99/99", "99/00/00");
    private static final String JAPAN_SUPPLIER = "JP";
    // 配置表
    private List<OpsPoDeliveryUnusualConf> configs = new ArrayList<>();


    @Getter
    @AllArgsConstructor
    public enum OrderType {
        BIN("BIN", "是Bin"),
        NOBIN("NOBIN", "不是Bin"),
        ALL("ALL", "全部"),
        ;

        private final String code;
        private final String desc;

        public static OrderType isBin(Boolean bin) {
            return bin ? BIN : NOBIN;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum UnusualType {
        BEFORE(0, "接单前异常"),
        AFTER(1, "接单后异常"),
        ;

        private final Integer code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum UnusualRule {
        CODE("code", "代码匹配"),
        LIKE("like", "模糊匹配"),
        ;

        private final String code;
        private final String desc;

    }

    @Getter
    @AllArgsConstructor
    public enum HandleStatus {
        WAIT(1, "待处理"),
        REPLIED(2, "已回复供应商"),
        COMPLETE(3, "处理完成"),
        ;

        private final Integer code;
        private final String desc;

    }


    @Data
    public class OrderNo {
        private Integer orderType;
        private String orderNo;
        private Integer itemNo;
        private Integer splitItem;
        private boolean isError = false;

        // 三段拼接，为空或0则不继续拼
        public String getFullNo() {
            if (splitItem != null && splitItem != 0) {
                return String.join("-", orderNo, itemNo.toString(), splitItem.toString());
            } else if (itemNo != null && itemNo != 0) {
                return String.join("-", orderNo, itemNo.toString());
            }
            return orderNo;
        }
    }


    @Override
    public void handleUnusualForCNC(OpsPoDeliveryUnusual unusual, Map<Long, OpsPurchaseinvoice> map) {
        OpsPurchaseorder purchase = basePoService.getPurchase(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
        if (purchase != null && PurchaseOrderStatusEnum.YIFASONG.equals(purchase.getStatecode())) {
            OpsPurchaseorder update = new OpsPurchaseorder();
            update.setId(purchase.getId());
            update.setStatecode(PurchaseOrderStatusEnum.DAICHULI);
            update.setUnusualMessage(unusual.getUnusualDescEng());
            update.setUpdatetime(new Date());
            update.setOperator("unusualJob"); //长度10
            basePoService.updatePurchaseById(update);
            List<OpsPurchaseinvoice> purchaseInvoices = basePoService.getPurchaseInvoicesByNo(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
            //删除purchaseInvoice
            if (CollectionUtils.isNotEmpty(purchaseInvoices)) {
                List<String> suppliers = getSuppliers();
                for (OpsPurchaseinvoice purchaseInvoice : purchaseInvoices) {
                    int delResult = opsPurchaseinvoiceMapper.deleteByPrimaryKey(purchaseInvoice.getId());
                    //记录每个unusual的采购发票，用来推送邮件
                    map.put(unusual.getId(), purchaseInvoice);
                    // 调用CTC删除接口
                    if (delResult == 1) {
                        // BUG 20160
                        if (suppliers.contains(purchaseInvoice.getSupplierid())) {
                            purchaseCtcDelService.insertMid(Collections.singletonList(purchaseInvoice));
                        }
                    }
                }
            }
        }
        updateUnusualHandleStatus(unusual.getId(), HandleStatus.COMPLETE.getCode(), null);
    }

    private List<String> getSuppliers() {
        //查询6005
        List<String> suppliers = new ArrayList<>();
        try {
            ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("6005");
            if (dataCodes.isSuccess()) {
                suppliers = dataCodes.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("查询6005异常", e);
        }
        //如果没有查到，则默认
        if (CollectionUtils.isEmpty(suppliers)) {
            suppliers = Arrays.asList("CN", "CM", "YZ", "CT", "GZ", "TZ");
        }
        return suppliers;
    }

    public void updateUnusualHandleStatus(Long id, Integer status, String result) {
        OpsPoDeliveryUnusual update = new OpsPoDeliveryUnusual();
        update.setId(id);
        update.setHandleStatus(status);
        update.setHandleResult(result);
        update.setCompleteTime(new Date());
        update.setUpdateTime(new Date());
        update.setUpdateUser("AUTO_JOB");
        opsPoDeliveryUnusualMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public void handUsualListForCNCEmail(List<OpsPoDeliveryUnusual> unusualList, Map<Long, OpsPurchaseinvoice> map) {
        StringBuffer content = new StringBuffer();
        content.append("<h4>中国工厂订单发送出错，错误订单和信息：</h4>\n");
        //表格样式
        content.append("<table border=\"5\" style=\"border:solid 1px #000000;font-size=12px;;font-size:15px;\">");
        //创建表头
        content.append("<tr style=\"color:#000000\">");
        content.append("<th>订单号</th>");
        content.append("<th>订单项号</th>");
        content.append("<th>订单纳期</th>");
        content.append("<th>异常信息</th>");
        content.append("</tr>");
        //表信息
        for (OpsPoDeliveryUnusual unusual : unusualList) {
            if (!map.containsKey(unusual.getId())) {
                continue;
            }
            OpsPurchaseinvoice opsPurchaseinvoice = map.get(unusual.getId());
            if (opsPurchaseinvoice != null) {

                content.append("<tr>");
                String pono = opsPurchaseinvoice.getPono();
                Integer lineitem = opsPurchaseinvoice.getLineitem();
                Date hopeexportdate = opsPurchaseinvoice.getHopeexportdate();
                String date = "";
                if (hopeexportdate != null) {
                    date = DateUtil.formatDateTime(hopeexportdate);
                }
                content.append("<td align=\"center\">" + pono + "</td>"); //订单号
                content.append("<td align=\"center\">" + lineitem + "</td>"); //订单项号
                content.append("<td align=\"center\">" + date + "</td>"); //订单项号
                content.append("<td align=\"center\">" + unusual.getUnusualDescEng() + "</td>"); //异常信息
                content.append("</tr>");
            }
        }
        content.append("</table>\n");
        content.append("<br />");
        content.append("<h5 style=\"margin-left:30px\">如有疑问，请您与PSI系统管理员联系！</h5>\n");
        content.append("<h5 style=\"margin-left:30px\">此邮件为系统发送，请勿直接回复！！！</h5>\n");
        content.append("**********************************************************************************");
        purchaseSendEmail.PurchaseUnusualMailSend(content.toString());
    }


    @Override
    public void handleUnusualListForJP(List<OpsPoDeliveryUnusual> unusualList) {
        List<OpsPoDeliveryUnusual> poList = new ArrayList<>();
        for (OpsPoDeliveryUnusual unusual : unusualList) {
            //接单前异常
            if (UnusualType.BEFORE.getCode().toString().equals(unusual.getUnusualType())) {
                // ① 采购单(ops_purchaseOrder)状态为删除
                OpsPurchaseinvoice opsPurchaseinvoice = getOpsPurchaseinvoice(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                //查询采购单状态
                OpsPurchaseorder purchase = basePoService.getPurchase(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                if (purchase != null) {
                    //采购表状态符合的
                    if (PurchaseOrderStatusEnum.SHANCHU.equals(purchase.getStatecode())) {
                        poList.add(unusual);
                        continue;
                    }
                    if (opsPurchaseinvoice != null) {
                        opsPurchaseinvoice = BeanUtil.copyProperties(purchase, OpsPurchaseinvoice.class);
                    }
                } else {
                    //采购删单表状态
                    OpsPurchaseorderCancelLog opsPurchaseorderCancelLog = addCanceledPo(unusual, poList);
                    if (opsPurchaseorderCancelLog != null && PurchaseOrderStatusEnum.SHANCHU.equals(opsPurchaseorderCancelLog.getStatecode())) {
                        poList.add(unusual);
                        continue;
                    }
                    if (opsPurchaseinvoice != null) {
                        opsPurchaseinvoice = BeanUtil.copyProperties(opsPurchaseorderCancelLog, OpsPurchaseinvoice.class);
                    }
                }
                //②	供应商 ID ≠ "JP"（非日本供应商）
                if (opsPurchaseinvoice != null) {
                    if (!JAPAN_SUPPLIER.equals(opsPurchaseinvoice.getSupplierid())) {
                        poList.add(unusual);
                        continue;
                    }
                    // 日本有反馈日本手配号
                    else if (StringUtils.isNotBlank(opsPurchaseinvoice.getReplyorderno())) {
                        poList.add(unusual);
                        continue;
                    }
                }
            }
            // 接单后异常
            else if (UnusualType.AFTER.getCode().toString().equals(unusual.getUnusualType())) {
                //7.1 当符合下记条件之一时，状态即可由“已回复”变更为“处理完成”
                if (HandleStatus.REPLIED.getCode().equals(unusual.getHandleStatus())) {
                    // ①采购单(ops_purchaseOrder)状态为删除、转订删除或者变成运输中、已完成
                    OpsPurchaseinvoice opsPurchaseinvoice = getOpsPurchaseinvoice(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                    //查询采购单状态
                    OpsPurchaseorder purchase = basePoService.getPurchase(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                    if (purchase != null) {
                        //采购表状态符合的
                        if (PurchaseOrderStatusEnum.YUNSHUZHONG.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.YIWANCHENG.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.ZHAUNDINGSHANCHU.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.SHANCHU.equals(purchase.getStatecode())) {
                            poList.add(unusual);
                            continue;
                        }
                        if (opsPurchaseinvoice == null) {
                            opsPurchaseinvoice = BeanUtil.copyProperties(purchase, OpsPurchaseinvoice.class);
                        }

                    } else {
                        //采购删单表状态
                        OpsPurchaseorderCancelLog opsPurchaseorderCancelLog = addCanceledPo(unusual, poList);
                        if (opsPurchaseorderCancelLog != null) {
                            poList.add(unusual);
                            continue;
                        }
                        if (opsPurchaseinvoice == null) {
                            opsPurchaseinvoice = BeanUtil.copyProperties(opsPurchaseorderCancelLog, OpsPurchaseinvoice.class);
                        }
                    }
                    // ②系统日期(YYYYMMDD) -回复供应商日期 >= 1 AND 日本返信纳期(ops_purchaseInvoice)不为“44/44/44，99/99/99，99/00/00"
                    if (unusual.getReplyTime() != null && DateUtil.betweenDay(new Date(), unusual.getReplyTime(), true) >= 1) {
                        if (opsPurchaseinvoice != null) {
                            if (StringUtils.isNotBlank(opsPurchaseinvoice.getSrcDeliveryTime()) || !SRC_DELIVERY_TIME.contains(opsPurchaseinvoice.getSrcDeliveryTime())) {
                                poList.add(unusual);
                                continue;
                            }
                        }
                    }
                }
                //7.2 当符合下记条件之一时，状态即可由“待处理”变更为“处理完成”
                if (HandleStatus.WAIT.getCode().equals(unusual.getHandleStatus())) {
                    // ① 采购单(ops_purchaseOrder)状态为删除、转订删除或者变成运输中、已完成
                    OpsPurchaseinvoice opsPurchaseinvoice = getOpsPurchaseinvoice(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                    //查询采购单状态
                    OpsPurchaseorder purchase = basePoService.getPurchase(unusual.getOrderNo(), unusual.getItemNo(), unusual.getSplitItemNo());
                    if (purchase != null) {
                        //采购表状态符合的
                        if (PurchaseOrderStatusEnum.YUNSHUZHONG.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.YIWANCHENG.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.ZHAUNDINGSHANCHU.equals(purchase.getStatecode()) ||
                                PurchaseOrderStatusEnum.SHANCHU.equals(purchase.getStatecode())) {
                            poList.add(unusual);
                            continue;
                        }
                        if (opsPurchaseinvoice == null) {
                            opsPurchaseinvoice = BeanUtil.copyProperties(purchase, OpsPurchaseinvoice.class);
                        }
                    } else {
                        //采购删单表状态
                        OpsPurchaseorderCancelLog opsPurchaseorderCancelLog = addCanceledPo(unusual, poList);
                        if (opsPurchaseorderCancelLog != null) {
                            poList.add(unusual);
                            continue;
                        }
                        if (opsPurchaseinvoice == null) {
                            opsPurchaseinvoice = BeanUtil.copyProperties(opsPurchaseorderCancelLog, OpsPurchaseinvoice.class);
                        }
                    }
                    //②	供应商 ID ≠ "JP"（非日本供应商）
                    if (opsPurchaseinvoice != null) {
                        if (!JAPAN_SUPPLIER.equals(opsPurchaseinvoice.getSupplierid())) {
                            poList.add(unusual);
                            continue;
                        }
                    }
                    //③	返信纳期是正常返信  或者  大致返信，不确定返信从配置表中获取
                    if (opsPurchaseinvoice != null &&
                            (classify(opsPurchaseinvoice.getSrcDeliveryTime()) == ReplyTimeType.NORMAL ||
                                    classify(opsPurchaseinvoice.getSrcDeliveryTime()) == ReplyTimeType.APPROXIMATE)) {
                        poList.add(unusual);
                    }
                }
            }
        }
        //更新状态
        for (OpsPoDeliveryUnusual unusual : poList) {
            OpsPoDeliveryUnusual update = new OpsPoDeliveryUnusual();
            update.setId(unusual.getId());
            update.setHandleStatus(HandleStatus.COMPLETE.getCode());
            update.setCompleteTime(new Date());
            update.setUpdateTime(new Date());
            update.setUpdateUser("AUTO_JOB");
            opsPoDeliveryUnusualMapper.updateByPrimaryKeySelective(update);
        }
    }

    private OpsPurchaseinvoice getOpsPurchaseinvoice(String orderno, Integer itemno, Integer splitno) {
        List<OpsPurchaseinvoice> list = changeLogService.getPurchaseInvoice(orderno, itemno, splitno);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    private static final Set<String> UNCERTAIN_CODES = SetUtils.hashSet(
            "222222", "333333", "444444", "555555",
            "666666", "777777", "888888", "999999", "990000"
    );

    // 正常返信：YYMMDD 格式（6位纯数字，日期合理）
    private static final Pattern NORMAL_PATTERN = Pattern.compile(
            "^(\\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$"
    );

    // 大致返信：YYMM99 格式（前4位为年月，后2位为99）
    private static final Pattern APPROXIMATE_PATTERN = Pattern.compile(
            "^(\\d{2})(0[1-9]|1[0-2])99$"
    );

    public enum ReplyTimeType {
        UNCERTAIN,    // 不确定返信
        NORMAL,       // 正常返信（精确日期）
        APPROXIMATE,  // 大致返信（年月+99）
        UNKNOWN       // 无法识别
    }

    public static ReplyTimeType classify(String srcDeliveryTime) {
        if (srcDeliveryTime == null || srcDeliveryTime.isEmpty()) {
            return ReplyTimeType.UNKNOWN;
        }
        if (UNCERTAIN_CODES.contains(srcDeliveryTime)) {
            return ReplyTimeType.UNCERTAIN;
        }
        if (APPROXIMATE_PATTERN.matcher(srcDeliveryTime).matches()) {
            return ReplyTimeType.APPROXIMATE;
        }
        if (NORMAL_PATTERN.matcher(srcDeliveryTime).matches()) {
            return ReplyTimeType.NORMAL;
        }
        return ReplyTimeType.UNKNOWN;
    }

    private OpsPurchaseorderCancelLog addCanceledPo(OpsPoDeliveryUnusual unusual, List<OpsPoDeliveryUnusual> poList) {
        OpsPurchaseorderCancelLogExample e = new OpsPurchaseorderCancelLogExample();
        OpsPurchaseorderCancelLogExample.Criteria criteria = e.createCriteria();
        criteria.andOrdernoEqualTo(unusual.getOrderNo());
        if (unusual.getItemNo() != null) {
            criteria.andItemnoEqualTo(unusual.getItemNo());
        }
        if (unusual.getSplitItemNo() != null) {
            criteria.andSplititemnoEqualTo(unusual.getSplitItemNo());
        } else {
            criteria.andSplititemnoIsNull();
        }
        List<String> stateCodeList = Arrays.asList(PurchaseOrderStatusEnum.SHANCHU, PurchaseOrderStatusEnum.ZHAUNDINGSHANCHU);
        criteria.andStatecodeIn(stateCodeList);
        e.setOrderByClause("id desc");
        List<OpsPurchaseorderCancelLog> cancelLogs = opsPurchaseorderCancelLogMapper.selectByExample(e);
        if (CollectionUtils.isNotEmpty(cancelLogs)) {
            return cancelLogs.get(0);
        } else {
            log.error("采购异常处理：未找到对应订单号{}", unusual.getOrderFno());
        }
        return null;
    }

    @Override
    public Integer updateStatusByReplyJapan(Long id, UserDto userDto) {
        OpsPoDeliveryUnusual update = new OpsPoDeliveryUnusual();
        update.setId(id);
        update.setHandleStatus(HandleStatus.REPLIED.getCode());
        update.setUpdateTime(new Date());
        update.setUpdateUser(userDto != null ? userDto.getUserName() : "");
        //回复供应商日期
        update.setReplyTime(new Date());
        update.setHandleUserName(userDto != null ? userDto.getUserName() : "");
        return opsPoDeliveryUnusualMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public Integer deleteUnusualByIds(List<Long> ids, UserDto userDto) {
        OpsPoDeliveryUnusualExample e = new OpsPoDeliveryUnusualExample();
        e.createCriteria().andDelFlagEqualTo(false).andIdIn(ids);
        OpsPoDeliveryUnusual update = new OpsPoDeliveryUnusual();
        update.setDelFlag(true);
        update.setUpdateTime(new Date());
        update.setUpdateUser(userDto != null ? userDto.getUserName() : "");
        return opsPoDeliveryUnusualMapper.updateByExampleSelective(update, e);
    }

    @Override
    public List<UnusualViewDto> exportUnusual(UnusualQueryDto condition) {
        if (condition.getInquiryDate() != null && condition.getInquiryDate().length == 2) {
            condition.setStartInquiryDate(condition.getInquiryDate()[0]);
            condition.setEndInquiryDate(condition.getInquiryDate()[1]);
        }
        //模糊查询字段
        if (StringUtils.isNotBlank(condition.getOrderFno()) && !condition.getOrderFno().trim().isEmpty()) {
            condition.setOrderFno(condition.getOrderFno().trim() + "%");
        }
        if (StringUtils.isNotBlank(condition.getUnusualDescChn()) && !condition.getUnusualDescChn().trim().isEmpty()) {
            condition.setUnusualDescChn("%" + condition.getUnusualDescChn().trim() + "%");
        }
        // condition.getUserNo(),
        List<UnusualViewDto> list = opsUnusualDao.selectUnusualViewDto(
                condition.getOrderFno(), condition.getOrderFnoList(),
                condition.getDeptNo(), condition.getModelno(),
                condition.getCustomerNo(), condition.getEndUser(),
                condition.getStartInquiryDate(), condition.getEndInquiryDate(),
                condition.getJobDeptName(), condition.getHandleStatus(),
                condition.getRecordCount(), condition.getUnusualDescChn(),
                condition.getUnusualType()
        );
        return list;
    }


    @Override
    public PageInfo<UnusualViewDto> searchUnusual(PageModel<UnusualQueryDto> queryDto) {
        UnusualQueryDto condition = queryDto.getCondition();
        //日期查询字段
        if (condition.getInquiryDate() != null && condition.getInquiryDate().length == 2) {
            condition.setStartInquiryDate(condition.getInquiryDate()[0]);
            condition.setEndInquiryDate(condition.getInquiryDate()[1]);
        }
        //模糊查询字段
        if (StringUtils.isNotBlank(condition.getOrderFno()) && !condition.getOrderFno().trim().isEmpty()) {
            condition.setOrderFno(condition.getOrderFno().trim() + "%");
        }
        if (StringUtils.isNotBlank(condition.getUnusualDescChn()) && !condition.getUnusualDescChn().trim().isEmpty()) {
            condition.setUnusualDescChn("%" + condition.getUnusualDescChn().trim() + "%");
        }
        //排序字段
        if (StringUtils.contains(queryDto.getOrderBy(), "quantity")) {
            queryDto.setOrderBy("u." + queryDto.getOrderBy());
        }
        if (StringUtils.contains(queryDto.getOrderBy(), "modelno")) {
            queryDto.setOrderBy("u." + queryDto.getOrderBy());
        }
        //分页查询
        PageHelper.startPage(queryDto.getPageNumber(), queryDto.getPageSize(), queryDto.getOrderBy());
        List<UnusualViewDto> list = opsUnusualDao.selectUnusualViewDto(
                condition.getOrderFno(), condition.getOrderFnoList(),
                condition.getDeptNo(), condition.getModelno(),
                condition.getCustomerNo(), condition.getEndUser(),
                condition.getStartInquiryDate(), condition.getEndInquiryDate(),
                condition.getJobDeptName(), condition.getHandleStatus(),
                condition.getRecordCount(), condition.getUnusualDescChn(),
                condition.getUnusualType()
        );
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer pullUnusualsFromLog(OpsPoUnusualOrderLog orderLog) throws OpsException {
        // 创建采购异常信息和子表数据
        OpsPoDeliveryUnusual unusual = buildUnusualByLog(orderLog);
        OpsPoDeliveryUnusualLog unusualLog = buildUnusualLog(unusual);
        opsPoDeliveryUnusualLogMapper.insertSelective(unusualLog);
        // 更新主表信息记录条数，如果没有则插入
        OpsPoDeliveryUnusualExample example = new OpsPoDeliveryUnusualExample();
        OpsPoDeliveryUnusualExample.Criteria criteria = example.createCriteria().andDelFlagEqualTo(false)
                .andHandleStatusNotEqualTo(HandleStatus.COMPLETE.getCode())
                .andOrderNoEqualTo(unusual.getOrderNo()).andItemNoEqualTo(unusual.getItemNo());
        if (unusual.getSplitItemNo() == null) {
            criteria.andSplitItemNoIsNull();
        } else {
            criteria.andSplitItemNoEqualTo(unusual.getSplitItemNo());
        }
        List<OpsPoDeliveryUnusual> unusuals = opsPoDeliveryUnusualMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(unusuals)) {
            OpsPoDeliveryUnusual oldUnusual = unusuals.get(0);
            DateTime today = DateUtil.beginOfDay(DateUtil.date());
            //今天比更新日期晚
            if (today.after(oldUnusual.getRecordDate())) {
                unusual.setRecordCount(oldUnusual.getRecordCount() + 1);
            }
            return opsPoDeliveryUnusualMapper.updateByExampleSelective(unusual, example);
        } else {
            unusual.setRecordCount(1);
            return opsPoDeliveryUnusualMapper.insertSelective(unusual);
        }

    }


    // 根据记录匹配配置表

    /**
     * 根据订单UnusualType，判断接单前异常或接单后异常
     * 根据reasonCode是否为空判断 1.编码匹配 2.模糊匹配
     * 1.编码匹配：OrderUnusualReasonCode
     * 2.模糊匹配：OrderUnusualReason
     */
    public OpsPoDeliveryUnusualConf matchUnusualConf(OpsPoUnusualOrderLog unusualOrderLog, boolean bin, OrderNo poNo) throws OpsException {
        if (configs.isEmpty()) {
            OpsPoDeliveryUnusualConfExample ex = new OpsPoDeliveryUnusualConfExample();
            ex.createCriteria().andDelFlagEqualTo(false);
            ex.setOrderByClause("id asc");
            List<OpsPoDeliveryUnusualConf> list = opsPoDeliveryUnusualConfMapper.selectByExample(ex);
            if (list.isEmpty()) {
                throw Exceptions.OpsException("配置信息加载失败");
            }
            this.configs = list;
        }
        OpsPoDeliveryUnusualConf conf = null;
        // 接单前异常
        String reasonCode = unusualOrderLog.getOrderUnusualReasonCode();
        String reasonLike = unusualOrderLog.getOrderUnusualReason();
        if (UnusualType.BEFORE.getCode().equals(unusualOrderLog.getUnusualType())) {
            // 筛选出规则表中的接单前异常匹配规则
            List<OpsPoDeliveryUnusualConf> before = configs.stream().filter(item -> UnusualType.BEFORE.getCode().equals(Integer.valueOf(item.getUnusualType()))).collect(Collectors.toList());
            // 匹配规则：code匹配
            if (StringUtils.isNotBlank(reasonCode)) {
                Stream<OpsPoDeliveryUnusualConf> codeRule = before.stream().filter(item -> UnusualRule.CODE.getCode().equals(item.getUnusualRule()));
                conf = codeRule
                        // code匹配
                        .filter(item -> StringUtils.equals(item.getUnusualCode(), reasonCode))
                        // 是否是bin
                        .filter(item -> item.getOrderType().equals(OrderType.ALL.getCode()) || item.getOrderType().equals(OrderType.isBin(bin).getCode()))
                        .findFirst().orElse(null);
                // log.info("接单前异常，code匹配:{}，bin:{},conf:{}", unusualOrderLog.getOrderUnusualReasonCode(), bin, JSONUtil.toJsonStr(conf));
                return conf;
            }
            // 匹配规则：模糊匹配
            else {
                Stream<OpsPoDeliveryUnusualConf> likeRule = before.stream().filter(item -> UnusualRule.LIKE.getCode().equals(item.getUnusualRule()));
                conf = likeRule
                        // 模糊匹配
                        .filter(item -> reasonLike.contains(item.getUnusualRuleCondition()))
                        // 是否是bin
                        .filter(item -> item.getOrderType().equals(OrderType.ALL.getCode()) || item.getOrderType().equals(OrderType.isBin(bin).getCode()))
                        .findFirst().orElse(null);
                // log.info("接单前异常，模糊匹配:{}，bin:{},conf:{}", unusualOrderLog.getOrderUnusualReasonCode(), bin, JSONUtil.toJsonStr(conf));
            }
        }
        // 接单后异常
        else if (UnusualType.AFTER.getCode().equals(unusualOrderLog.getUnusualType())) {
            // 筛选规则表中的接单后异常匹配规则
            List<OpsPoDeliveryUnusualConf> after = configs.stream().filter(item -> UnusualType.AFTER.getCode().equals(Integer.valueOf(item.getUnusualType()))).collect(Collectors.toList());
            // 匹配规则：code匹配
            if (StringUtils.isNotBlank(reasonCode)) {
                //bug 20262
                //匹配 unusualOrderLog.getOrderUnusualReasonCode()为4或9
                //且OpsPurchaseinvoice.getSrcDeliveryTime()返信纳期为["44/44/44","99/99/99","99/00/00"]的规则
                List<String> list = Arrays.asList("4", "9");
                if (list.contains(reasonCode)) {
                    OpsPurchaseinvoice opsPurchaseinvoice = getOpsPurchaseinvoice(poNo.getOrderNo(), poNo.getItemNo(), poNo.getSplitItem());
                    if (opsPurchaseinvoice != null && StringUtils.isNotBlank(opsPurchaseinvoice.getSrcDeliveryTime())) {
                        Stream<OpsPoDeliveryUnusualConf> codeRule = after.stream().filter(item -> UnusualRule.CODE.getCode().equals(item.getUnusualRule()))
                                .filter(item -> item.getUnusualRuleCondition() != null);
                        conf = codeRule
                                .filter(item -> StringUtils.equals(item.getUnusualCode(), reasonCode))
                                .filter(item -> StringUtils.equals(item.getUnusualRuleCondition(), opsPurchaseinvoice.getSrcDeliveryTime()))
                                .filter(item -> item.getOrderType().equals(OrderType.ALL.getCode()) || item.getOrderType().equals(OrderType.isBin(bin).getCode()))
                                .findFirst().orElse(null);
                        // log.info("接单后异常，code匹配:{}，bin:{},conf:{}", unusualOrderLog.getOrderUnusualReasonCode(), bin, JSONUtil.toJsonStr(conf));
                        return conf;
                    }
                    //返信纳期没匹配上的，走默认逻辑，只匹配code4和9,不用匹配UnusualRuleCondition
                }
                Stream<OpsPoDeliveryUnusualConf> codeRule = after.stream().filter(item -> UnusualRule.CODE.getCode().equals(item.getUnusualRule()))
                        .filter(item -> item.getUnusualRuleCondition() == null);
                conf = codeRule
                        .filter(item -> StringUtils.equals(item.getUnusualCode(), reasonCode))
                        .filter(item -> item.getOrderType().equals(OrderType.ALL.getCode()) || item.getOrderType().equals(OrderType.isBin(bin).getCode()))
                        .findFirst().orElse(null);
                // log.info("接单后异常，code匹配:{}，bin:{},conf:{}", unusualOrderLog.getOrderUnusualReasonCode(), bin, JSONUtil.toJsonStr(conf));
                return conf;
            }
            // 匹配规则：模糊匹配
            else {
                Stream<OpsPoDeliveryUnusualConf> likeRule = after.stream().filter(item -> UnusualRule.LIKE.getCode().equals(item.getUnusualRule()));
                conf = likeRule.filter(item -> reasonLike.contains(item.getUnusualRuleCondition()))
                        .filter(item -> item.getOrderType().equals(OrderType.ALL.getCode()) || item.getOrderType().equals(OrderType.isBin(bin).getCode()))
                        .findFirst().orElse(null);
                // log.info("接单后异常，模糊匹配:{}，bin:{},conf:{}", unusualOrderLog.getOrderUnusualReasonCode(), bin, JSONUtil.toJsonStr(conf));
            }
        }
        return conf;
    }


    //构建子表信息
    private OpsPoDeliveryUnusualLog buildUnusualLog(OpsPoDeliveryUnusual unusual) {
        return BeanUtil.copyProperties(unusual, OpsPoDeliveryUnusualLog.class);
    }

    //构建异常信息实体
    private OpsPoDeliveryUnusual buildUnusualByLog(OpsPoUnusualOrderLog unusuallog) throws OpsException {
        // 查询采购单号，如果没有查到单号，则抛异常
        OrderNo poNo = parsePoNo(unusuallog.getPono().trim(), unusuallog.getLineItem());
        OpsPoDeliveryUnusual unusual = new OpsPoDeliveryUnusual();
        if (poNo.isError()) {
            log.error("单号解析失败：{}-{}", unusuallog.getPono().trim(), unusuallog.getLineItem());
        } else {
            //log.info("采购单号解析：{}", poNo.getFullNo());
        }
        // 是否为bin
        boolean bin = Integer.valueOf(OrderTypeEnum.BIN).equals(poNo.getOrderType());
        // 根据log信息，匹配配置信息
        OpsPoDeliveryUnusualConf conf = null;
        //日本订单要遵循匹配规则，CNC订单直接接入，先不需要看规则（可能规则还没制定，也可能没这个打算制定配置规则）
        if (StringUtils.equals(unusuallog.getSupplierCode(), "JP0")) {
            conf = matchUnusualConf(unusuallog, bin, poNo);
            if (conf == null) {
                throw Exceptions.OpsException("配置信息匹配失败：{}", unusuallog.getId());
            }
        } else if (StringUtils.equals(unusuallog.getSupplierCode(), "CNC")) {
            //    直接接入，不匹配规则表
        }
        List<OpsPurchaseinvoice> purchaseInvoices = basePoService.getPurchaseInvoicesByNo(poNo.getOrderNo(), poNo.getItemNo(), poNo.getSplitItem());
        if (purchaseInvoices != null && !purchaseInvoices.isEmpty()) {
            OpsPurchaseinvoice purchaseInvoice = purchaseInvoices.get(0);
            unusual.setQuantity(purchaseInvoice.getQuantity());
            unusual.setModelno(purchaseInvoice.getModelno());
        }
        // 创建并包装异常表信息
        unusual.setOrderFno(poNo.getFullNo());
        unusual.setOrderNo(poNo.getOrderNo());
        unusual.setItemNo(poNo.getItemNo());
        unusual.setSplitItemNo(poNo.getSplitItem());
        unusual.setSupplierCode(unusuallog.getSupplierCode());
        //unusual.setModelno(unusuallog.getModelNo());
        //unusual.setQuantity(unusuallog.getQuantity());
        if (conf != null) {
            unusual.setUnusualClassify(conf.getUnusualClassify());
            unusual.setUnusualType(conf.getUnusualType());
            unusual.setUnusualIdentificationCode(conf.getUnusualIdentificationCode());
        } else {
            unusual.setUnusualClassify("N");// 大分类 N：接单异常
            unusual.setUnusualType(String.valueOf(unusuallog.getUnusualType()));
            unusual.setUnusualIdentificationCode(null);
        }
        //16639 4.3 接入原始信息
        unusual.setUnusualDescEng(unusuallog.getOrderUnusualReason());
        unusual.setHandleTime(null);
        unusual.setHandleStatus(HandleStatus.WAIT.getCode());
        unusual.setHandleResult(null);
        unusual.setHandleUser(null);
        unusual.setHandleUserName(null);
        unusual.setRecordDate(DateUtil.date());
        unusual.setRecordCount(1);
        unusual.setRemark(null);
        unusual.setDelFlag(false);
        unusual.setCreateTime(DateUtil.date());
        unusual.setUpdateTime(DateUtil.date());
        unusual.setCreateUser("po-auto");
        unusual.setUpdateUser("po-auto");
        unusual.setInquiryDate(unusuallog.getSupplierSystemExecTime());
        unusual.setReplyContent(unusuallog.getSrcReplyContent());
        if (unusuallog.getSrcReplyDate() != null && unusuallog.getSrcReplyDate().length() == 8) {
            if (unusuallog.getSrcReplyDate().equals("00000000")) {
                unusual.setReplyDate(null);
            } else if (unusuallog.getSrcReplyDate().startsWith("20")) {
                DateTime yyyyMMdd = DateUtil.parse(unusuallog.getSrcReplyDate(), "yyyyMMdd");
                unusual.setReplyDate(yyyyMMdd);
            }
        }

        return unusual;
    }

    // 解析采购单号
    private OrderNo parsePoNo(String poNo, Integer lineItem) {
        OrderNo orderNo = new OrderNo();
        OrderNoInfo orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(poNo, lineItem.toString());
        orderNo.setOrderNo(orderNoInfo.getOrderNo());
        orderNo.setItemNo(orderNoInfo.getItemNo());
        orderNo.setSplitItem(orderNoInfo.getSplitItem());
        orderNo.setOrderType(orderNoInfo.getOrderType());
        orderNo.setError(orderNoInfo.isError());
        return orderNo;
    }


}

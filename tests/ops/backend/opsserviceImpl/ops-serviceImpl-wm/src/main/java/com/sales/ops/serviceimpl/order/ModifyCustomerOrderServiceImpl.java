package com.sales.ops.serviceimpl.order;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.OpsOrderUpdateLogMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.dao.RcvmasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRcvDetailDao;
import com.sales.ops.db.extdao.TblWorkDayYearDao;
import com.sales.ops.dto.order.UpdateForOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.log.OpsOrderModiDataService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.ModifyCustomerOrderService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OrderDlvDataService;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusService;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sales.ops.enums.CKTYPEEnum.*;

/**
 * @author C12961
 * @date 2023/3/10 14:39
 */
@Slf4j
@Service
public class ModifyCustomerOrderServiceImpl implements ModifyCustomerOrderService {

    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;

    @Autowired
    private OpsOrderModiDataService opsOrderModiDataService;
    @Autowired
    private RcvdetailMapper rcvdetailMapper;
    @Autowired
    private RcvmasterMapper rcvmasterMapper;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OrderDlvDataService orderDlvDataService;
    @Autowired
    private OpsRcvDetailDao opsRcvDetailDao;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    TblWorkDayYearDao tblWorkDayYearDao;
    @Autowired
    private OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;
    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private BaseDoService baseDoService;

    private final String REDIS_KEY_UPDATED_DLVDATE = "ops:order:updatedDlvDateLog:orderFno:";


    /*****************************变更订单操作*****************************/


    /**
     * @description 变更Po单号
     * @author C12961
     * @date 2022/5/21 16:03
     */
    @Override
    public int modifyPurchaseNo(String orderNo, String purchaseNo, UserDto userDto) throws OpsException {
        List<Rcvdetail> rcvDetailList = baseCustomerOrderService.findRcvDetailList(orderNo);
        // 判断主单状态能否变更
        Map<Rcvdetail, Integer> enable = new HashMap<>();
        Map<Rcvdetail, Integer> result = new HashMap<>();
        for (Rcvdetail rcvdetail : rcvDetailList) {
            // detail在开始出库之前，则可变更
            enable.put(rcvdetail, beforeWmStart(rcvdetail));
        }
        // 变更物流指令
        for (Map.Entry<Rcvdetail, Integer> entry : enable.entrySet()) {
            Rcvdetail key = entry.getKey();
            Integer value = entry.getValue();
            // 如果状态允许，detail存入队列
            if (value == 1) {
                Rcvdetail detail = new Rcvdetail();
                detail.setRorderNo(key.getRorderNo());
                detail.setRorderItem(key.getRorderItem());
                detail.setCorderNo(purchaseNo);
                modifyRcvDetail(detail, userDto);
                result.put(key, 1);
            }
            if (value == 2) {
                UpdateForOrderDto updateDto = new UpdateForOrderDto();
                updateDto.setOrderId(key.getRorderNo());
                updateDto.setOrderItem(key.getRorderItem());
                updateDto.setCorderNo(purchaseNo);
                // 操作人
                if (Objects.nonNull(userDto)) {
                    updateDto.setUserDto(userDto);
                } else {
                    updateDto.setUserDto(UserDto.ADMIN);
                }
                Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(updateDto);
                if (modifyDoResult.containsValue("1")) {
                    Rcvdetail detail = new Rcvdetail();
                    detail.setRorderNo(key.getRorderNo());
                    detail.setRorderItem(key.getRorderItem());
                    detail.setCorderNo(purchaseNo);
                    modifyRcvDetail(detail, userDto);
                }
                // 如果有一条指令成功，detail存入队列
                if (successModifyDo(modifyDoResult)) {
                    result.put(key, 2);
                }
            }
        }
        // 如果detail队列有值，则变更master
        if (result.size() > 0) {
            Rcvmaster rcvMaster = new Rcvmaster();
            rcvMaster.setRorderNo(orderNo);
            rcvMaster.setPurchaseno(purchaseNo);
            rcvMaster.setUpdateTime(new Date());
            return rcvmasterMapper.updateByPrimaryKeySelective(rcvMaster);
        }
        // 如果没有值，则没有允许变更的detail,master不允许变更
        else {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
    }


    /**
     * @description 变更出库方式和集约方式
     * @author C12961
     * @date 2022/5/21 16:03
     */
    @Override
    public int modifyDlvEntireAndType(String orderNo, String dlvEntire, String dlvType, UserDto userDto) throws OpsException {
        Rcvmaster rcvmaster = rcvmasterMapper.selectByPrimaryKey(orderNo);
        // 判断出库方式能否变更
        if (StringUtils.isNotBlank(dlvEntire)) {
            if (!CKTYPEEnum.enableModify(rcvmaster.getDlvEntire(), dlvEntire)) {
                throw Exceptions.OpsException("当前出库方式不允许变更");
            }
        }
        // bug：11245 C12961 2023-07-05
        if (StringUtils.isNotBlank(dlvType)) {
            if (DlvTypeEnum.parse(dlvType) == DlvTypeEnum.ORDER) {
                // 如果收货地址、联系人不同，则不允许修改集约方式
                if (diffAddressOrContactPerson(orderNo, null, null)) {
                    throw Exceptions.OpsException("订单集约发货，收货地址、联系人必须相同");
                }
            }
        }

        Map<Rcvdetail, Integer> enable = new HashMap<>();
        Map<Rcvdetail, Integer> result = new HashMap<>();
        List<Rcvdetail> rcvDetailList = baseCustomerOrderService.findRcvDetailList(orderNo);
        // 判断主单状态能否变更
        for (Rcvdetail rcvdetail : rcvDetailList) {
            // detail在开始出库之前，则可变更
            enable.put(rcvdetail, beforeWmStart(rcvdetail));
        }
        // 变更物流指令
        for (Map.Entry<Rcvdetail, Integer> entry : enable.entrySet()) {
            Rcvdetail key = entry.getKey();
            Integer value = entry.getValue();
            // 如果状态允许，detail存入队列
            if (value == 1) {
                result.put(key, 1);
            }
            if (value == 2) {
                UpdateForOrderDto updateDto = new UpdateForOrderDto();
                updateDto.setOrderId(key.getRorderNo());
                updateDto.setOrderItem(key.getRorderItem());
                updateDto.setDlvEntire(dlvEntire);
                updateDto.setDlvType(dlvType);
                // 操作人
                if (Objects.nonNull(userDto)) {
                    updateDto.setUserDto(userDto);
                } else {
                    updateDto.setUserDto(UserDto.ADMIN);
                }
                Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(updateDto);
                // 如果有一条指令成功，detail存入队列
                if (successModifyDo(modifyDoResult)) {
                    result.put(key, 2);
                }
            }
        }
        // 如果detail队列有值，则变更master
        if (result.size() > 0) {
            Rcvmaster rcvMaster = new Rcvmaster();
            rcvMaster.setRorderNo(orderNo);
            rcvMaster.setDlvEntire(dlvEntire);
            rcvMaster.setDlvtype(dlvType);
            rcvMaster.setUpdateTime(new Date());
            return rcvmasterMapper.updateByPrimaryKeySelective(rcvMaster);
        }
        // 如果没有值，则没有允许变更的detail,master不允许变更
        else {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }

    }

    /**
     * 两种用法，如果写了参数dlvaddress就用参数和全体rcvdetail比较是否一致
     * 如果没写参数，则全体rcvdetail之间对比是否一致
     *
     * @param orderNo
     * @param dlvAddress
     * @param contactPerson
     * @throws OpsException
     */
    private boolean diffAddressOrContactPerson(String orderNo, String dlvAddress, String contactPerson) throws OpsException {
        // 查询所有未删除的detail的地址信息dlvDataList
        List<Rcvdetail> rcvDetailList = baseCustomerOrderService.findRcvDetailList(orderNo);
        List<Integer> orderItems = new ArrayList<>();
        for (Rcvdetail rcvdetail : rcvDetailList) {
            Integer orderItem = rcvdetail.getRorderItem();
            if (rcvdetail.getAddressNo() == null) {
                orderItems.add(0);
            } else {
                orderItems.add(orderItem);
            }
        }
        List<Orderdlvdata> dlvDataList = orderDlvDataService.getOrderDlvDatasByOrder(orderNo, orderItems);
        if (CollectionUtils.isNotEmpty(dlvDataList)) {
            Orderdlvdata firstData = dlvDataList.get(0);
            // 地址表中只有一条
            if (dlvDataList.size() == 1) {
                if (dlvAddress == null && contactPerson == null) {
                    return false;
                } else {// 判断输入值是否与表中不同
                    boolean diffAddress = !StringUtils.equals(dlvAddress, firstData.getDlvaddress());
                    boolean diffContactPerson = !StringUtils.equals(contactPerson, firstData.getContactpsn());
                    return diffAddress || diffContactPerson;
                }
            }
            // 地址表中有多条，查看多条的地址和联系人是否相同：拿输入的值（为空则拿第一项的值）和其他的比较
            if (dlvAddress == null) {
                dlvAddress = firstData.getDlvaddress();
            }
            if (contactPerson == null) {
                contactPerson = firstData.getContactpsn();
            }
            for (Orderdlvdata dlvData : dlvDataList) {
                boolean diffAddress = !StringUtils.equals(dlvAddress, dlvData.getDlvaddress());
                boolean diffContactPerson = !StringUtils.equals(contactPerson, dlvData.getContactpsn());
                return diffAddress || diffContactPerson;
            }
        } else {
            throw Exceptions.OpsException("没有查询到地址表信息");
        }
        return false;
    }

    /**计算最晚可修改的客户货期,结果展示
     * 1.若入库日期（storageDate）为空，则允许修改，日期为null
     * 2.若系统日期小于入库日期加 180 天（自然日），则允许修改，日期为最晚入库日期+180天（自然日）
     * 3.若系统日期大于或等于入库日期加 180 天（自然日），且订单未曾修改过，则允许一次性修改，日期为系统日期+7个自动化工作日
     * 4.若系统日期大于或等于入库日期加 180 天，但订单修改过，有修改标识，则不允许修改，日期为null
     * 5.异常情况，不允许修改，日期为null
     */
    @Override
    public CommonResult calMaxUpdateDlvDateLimit2(String orderNo, Integer orderItem){
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            return new CommonResult(5,"rcv表找不到订单",null);
        }
        //0.分析客户订单号是否在备案表
        boolean existCustomerWldate = baseCustomerOrderService.isCustomerWldate(rcvdetail.getCustomCode(), 9);
        // 如果在备案表，则不做限制时间，如果不在，则校验下一步
        if (existCustomerWldate) {
            return new CommonResult(1,"不做限制",null);
        }
        //1.按照发货方式，规定颗粒度，查看是否全都有货齐时间，如果有一个为空，则不做限制时间
        // 如果全都入库，则校验下一步
        Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(orderNo);
        CKTYPEEnum dlvEntire = CKTYPEEnum.getCode(rcvMaster.getDlvEntire());
        List<CKTYPEEnum> list = Arrays.asList(ORDER_GETHER_SIGNLE_HOUSE, ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS, ORDER_GETHER_MULTI_HOUSE);
        Date storageDate = null;
        if (list.contains(dlvEntire)) {
            List<Rcvdetail> rcvDetailList = baseCustomerOrderService.findRcvDetailList(orderNo);
            boolean allEntry = rcvDetailList.stream().allMatch(rcv -> rcv.getEntryTime() != null);
            if (!allEntry) {
                return new CommonResult(1,"不做限制",null);
            }
            storageDate = rcvDetailList.stream().map(Rcvdetail::getEntryTime).max(Date::compareTo).get();
        } else {
            if (rcvdetail.getEntryTime() == null) {
                return new CommonResult(1,"不做限制",null);
            }
            storageDate = rcvdetail.getEntryTime();
        }
        // 2.若系统日期小于入库日期加 180 天（自然日），计算最晚可修改的客户货期为入库日期+180天（自然日）
        //DateTime dlvDateLimit = DateUtil.offsetDay(readyTime, 180);
        // bug 14738 2024-09-20
        Date dlvDateLimit = com.sales.ops.dto.util.DateUtil.addDay(storageDate, 180);
        Date now = new Date();
        if (now.before(dlvDateLimit)) {
            String message = "系统日期小于入库日期" + DateUtil.formatDateTime(storageDate) + "加 180 天:"+DateUtil.formatDateTime(dlvDateLimit);
            return new CommonResult(2, message, dlvDateLimit);
        }
        //3.若系统日期大于或等于入库日期加 180 天,且订单未曾修改过，则允许一次性修改
        else {
            //查询修改记录
            Boolean exist = opsRedisUtils.hasKey(REDIS_KEY_UPDATED_DLVDATE + rcvdetail.getRorderFno());
            if (exist != null && exist) {
                //4.若系统日期大于或等于入库日期加 180 天，但订单修改过，有修改标识，则不允许修改
                return new CommonResult(4, "订单只允许修改一次", null);
            }
            OpsOrderUpdateLogExample ex = new OpsOrderUpdateLogExample();
            ex.createCriteria().andOrderidEqualTo(orderNo).andOrderItemEqualTo(orderItem).andChangeFlagEqualTo(true);
            long count = opsOrderUpdateLogMapper.countByExample(ex);
            if (count > 0) {
                //4.若系统日期大于或等于入库日期加 180 天，但订单修改过，有修改标识，则不允许修改
                return new CommonResult(4,"订单只允许修改一次",null);
            }
            List<TblWorkdayYear> workdayList = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.formatDate(new Date()), 7 + 1);
            if (CollectionUtils.isEmpty(workdayList)) {
                return new CommonResult(5,"无法查询工作日",null);
            }
            Date date = workdayList.get(workdayList.size() - 1).getWorkdayDate();
            return new CommonResult(3,"允许一次性修改",date);
        }
    }



    /**
     * @description 变更客户交货期和物流交货期
     * @author C12961
     * @date 2022/5/21 16:01
     */
    @Override
    public int modifyDlvDate(String orderNo, Integer orderItem, Date dlvDate, Date wmsDlvDate, UserDto userDto) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        Integer dlvDateType = 0;
        if (dlvDate != null) {
            //计算最大修改限制
            Date maxUpdateDlvDate = null;
            //1：若入库日期（storageDate）为空，则允许修改，计算最晚可修改的客户货期为null
            //2：若系统日期小于入库日期加 180 天（自然日），则允许修改，计算最晚可修改的客户货期为入库日期+180天（自然日）
            //3：若系统日期大于或等于入库日期加 180 天，且订单未曾修改过，则允许一次性修改，计算最晚可修改的客户货期为系统日期+7个自动化工作日，确认修改后记录修改标识（change_flag）。
            //4：若系统日期大于或等于入库日期加 180 天，但订单修改过，有修改标识，则不允许修改
            //5：查询异常信息
            List<Integer> success = Arrays.asList(1, 2, 3);
            List<Integer> failure = Arrays.asList(4, 5);
            CommonResult<Date> result = calMaxUpdateDlvDateLimit2(orderNo, orderItem);
            if (failure.contains(result.getCode())) {
                throw Exceptions.OpsException(result.getMessage());
            } else if (success.contains(result.getCode())) {
                maxUpdateDlvDate = result.getData();
            }
            if (maxUpdateDlvDate != null && dlvDate.after(maxUpdateDlvDate)) {
                throw Exceptions.OpsException("客户交货期必须小于等于" + DateUtil.format(maxUpdateDlvDate, "yyyy-MM-dd"));
            }
            dlvDateType = result.getCode();
        }
        int enable = beforeWmStart(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 变更订单表
            Rcvdetail detail = new Rcvdetail();
            detail.setRorderNo(orderNo);
            detail.setRorderItem(orderItem);
            detail.setDlvDate(dlvDate);
            detail.setWmsDlvDate(wmsDlvDate);
            modifyRcvDetail(detail, userDto);
            // 插入订单变更日志表 modidata
            if (ObjectUtil.isNotNull(dlvDate) && !dlvDate.equals(rcvdetail.getDlvDate())) {
                opsOrderModiDataService.insertModiDataForModifyOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), rcvdetail.getRorderFno(), rcvdetail.getDlvDate(), dlvDate, userDto.getUserName());
                if(dlvDateType==3){
                    opsRedisUtils.set(REDIS_KEY_UPDATED_DLVDATE + rcvdetail.getRorderFno(),"1",30);
                }
            }
        }
        // 要改物流指令
        else if (enable == 2) {
            // 变更物流指令
            // bugid: 10345 20230410 c14717 重新计算物流交货期
            Map<String, String> modifyDoResult = opsDoService.updateDoPcoWlday(orderNo, orderItem.toString(), dlvDate, true, userDto);
            log.info("变更发货日期，物流模块，结果：" + JSONUtil.toJsonStr(modifyDoResult));
            // 只要有一条物流指令修改成功， 就算成功
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 插入订单变更日志表 modidata
            if (ObjectUtil.isNotNull(dlvDate) && !dlvDate.equals(rcvdetail.getDlvDate())) {
                opsOrderModiDataService.insertModiDataForModifyOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), rcvdetail.getRorderFno(), rcvdetail.getDlvDate(), dlvDate, userDto.getUserName());
                if(dlvDateType==3){
                    opsRedisUtils.set(REDIS_KEY_UPDATED_DLVDATE + rcvdetail.getRorderFno(),"1",30);
                }
            }
        }
        return dlvDateType;
    }

    /**
     * @description 变更客户交货期和物流交货期
     * @author C12961
     * @date 2022/5/21 16:01
     */
    @Override
    public int modifyCproductNo(String orderNo, Integer orderItem, String cproductNo, UserDto userDto) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        int enable = beforeWmStart(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 变更订单表
            if (cproductNo != null) {
                Rcvdetail detail = new Rcvdetail();
                detail.setRorderNo(orderNo);
                detail.setRorderItem(orderItem);
                detail.setCproductNo(cproductNo);
                modifyRcvDetail(detail, userDto);
            }

            return 1;
        }
        // 要改物流指令
        else if (enable == 2) {
            // 变更物流指令
            UpdateForOrderDto updateForOrderDto = new UpdateForOrderDto();
            updateForOrderDto.setOrderId(orderNo);
            updateForOrderDto.setOrderItem(orderItem);
            updateForOrderDto.setCproductNo(cproductNo);
            updateForOrderDto.setUserDto(userDto);
            Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(updateForOrderDto);
            log.info("变更物料号和po号，物流模块结果：" + JSONUtil.toJsonStr(modifyDoResult));
            // 只要有一条物流指令修改成功， 就算成功
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 变更订单表
            if (cproductNo != null) {
                Rcvdetail detail = new Rcvdetail();
                detail.setRorderNo(orderNo);
                detail.setRorderItem(orderItem);
                detail.setCproductNo(cproductNo);
                modifyRcvDetail(detail, userDto);
            }
            return 1;
        }
        return 0;
    }

    /**
     * @description 变更发货地址和联系人
     * @author C12961
     * @date 2022/5/21 16:02
     */
    @Override
    public int modifyDlvAddress(String orderNo, Integer orderItem, Orderdlvdata dlvdata, Boolean specialFlag, UserDto userDto) throws OpsException {
        if (StringUtils.isNotBlank(dlvdata.getDlvaddress())) {
            if (!DlvSiteEnum.SELF.getCode().toString().equals(dlvdata.getDlvflag())) {
                // bug19069 当修改自提的时候，不校验省市区代码为空 2025-10-15 C12961
                if (StringUtils.isBlank(dlvdata.getProvince()) || StringUtils.isBlank(dlvdata.getCity()) || StringUtils.isBlank(dlvdata.getDistrict())) {
                    throw Exceptions.OpsException("省市区代码不允许为空");
                }
            }
        }
        dlvdata.setOrderno(orderNo);
        dlvdata.setItemno(orderItem);
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        if(StringUtils.isBlank(dlvdata.getDlvflag())){
            throw Exceptions.OpsException("发货方式不允许为空");
        }
        //承运商判断
        if(StringUtils.isBlank(dlvdata.getCarrierid())){
            if (DlvSiteEnum.CUSTOMER.getCode().toString().equals(dlvdata.getDlvflag()) ||
                    DlvSiteEnum.DEPARTMENT.getCode().toString().equals(dlvdata.getDlvflag())) {
                dlvdata.setCarrierid(CarrierEnum.NON.getCode());
            }
            if (DlvSiteEnum.SELF.getCode().toString().equals(dlvdata.getDlvflag())) {
                dlvdata.setCarrierid(CarrierEnum.ZT.getCode());
            }
        }
        // 开始变更发货地址
        int enable = beforeWmPackage(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 如果只有主单地址，则插入一条和主单地址一模一样的，然后修改成子项地址，如果有子项地址，则直接修改
            orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
            // 经过刚才的操作，必定能查到数据
            Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
            // 变更rcvdetail的addressNo
            Rcvdetail modifyDetail = new Rcvdetail();
            modifyDetail.setRorderNo(orderNo);
            modifyDetail.setRorderItem(orderItem);
            modifyDetail.setAddressNo(update.getId().intValue());
            modifyRcvDetail(modifyDetail, userDto);
        }
        // 要改物流指令
        else if (enable == 2) {
            UpdateForOrderDto modifyDoDto = new UpdateForOrderDto(dlvdata);
            modifyDoDto.setUserDto(userDto);
            modifyDoDto.setOrderId(orderNo);
            modifyDoDto.setOrderItem(orderItem);
            modifyDoDto.setDlvSite(dlvdata.getDlvflag());//可以变更为1直发客户，2执法营业所，3自提
            // bugid:12391 C12961 2023-10-30
            if (modifyDoDto.getEmail() != null) {
                opsDoService.updateDoEmail(modifyDoDto);
            }
            // bugid:9471 c14717 20230208
            Map<String, String> modifyDoResult = opsDoService.updateDoAddressNew(modifyDoDto, specialFlag);
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                log.info(JSONUtil.toJsonStr(modifyDoResult));
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 变更地址表
            orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
            Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
            // 变更rcvdetail的addressNo
            Rcvdetail modifyDetail = new Rcvdetail();
            modifyDetail.setRorderNo(orderNo);
            modifyDetail.setRorderItem(orderItem);
            modifyDetail.setAddressNo(update.getId().intValue());
            modifyRcvDetail(modifyDetail, userDto);
        }
        return 0;
    }

    /**
     * @description 变更承运商
     * @author C12961
     * @date 2022/5/21 16:02
     */
    @Override
    public int modifyCarrier(String orderNo, Integer orderItem, String carrier, Boolean specialFlag, UserDto userDto) throws OpsException {
        // 变更dlvdata表的承运商
        Orderdlvdata dlvdata = new Orderdlvdata();
        dlvdata.setOrderno(orderNo);
        dlvdata.setItemno(orderItem);
        dlvdata.setCarrierid(carrier);
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        int enable = beforeWmPackage(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
            Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
            // 变更rcvdetail的addressNo
            Rcvdetail modifyDetail = new Rcvdetail();
            modifyDetail.setRorderNo(orderNo);
            modifyDetail.setRorderItem(orderItem);
            modifyDetail.setAddressNo(update.getId().intValue());
            modifyRcvDetail(modifyDetail, userDto);
        }
        // 要改物流指令
        else if (enable == 2) {
            UpdateForOrderDto modifyDoDto = new UpdateForOrderDto();
            modifyDoDto.setOrderId(dlvdata.getOrderno());
            modifyDoDto.setOrderItem(dlvdata.getItemno());
            modifyDoDto.setCarried(dlvdata.getCarrierid());
            // bugid:9471 c14717 20230208
            Map<String, String> modifyDoResult = opsDoService.updateDoAddressNew(modifyDoDto, specialFlag);
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                log.info(JSONUtil.toJsonStr(modifyDoResult));
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 变更地址表
            orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
            Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
            // 变更rcvdetail的addressNo
            Rcvdetail modifyDetail = new Rcvdetail();
            modifyDetail.setRorderNo(orderNo);
            modifyDetail.setRorderItem(orderItem);
            modifyDetail.setAddressNo(update.getId().intValue());
            modifyRcvDetail(modifyDetail, userDto);
        }
        return 0;
    }

    /**
     * @description 变更自提
     * 可以合并到变更地址方法中
     * @author C12961
     * @date 2022/5/21 16:02
     */
    @Deprecated
    @Override
    public int modifyDlvSite(String orderNo, Integer orderItem, Orderdlvdata dlvdata, Boolean specialFlag, UserDto userDto) throws OpsException {
        dlvdata.setOrderno(orderNo);
        dlvdata.setItemno(orderItem);
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        int enable = beforeWmPackage(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 1.变更dlvdata表的自提和自提地址、身份证号
            orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
            Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
            // 2.变更rcvdetail的addressNo
            Rcvdetail modifyDetail = new Rcvdetail();
            modifyDetail.setRorderNo(orderNo);
            modifyDetail.setRorderItem(orderItem);
            modifyDetail.setAddressNo(update.getId().intValue());
            modifyRcvDetail(modifyDetail, userDto);
        }
        // 要改物流指令
        else if (enable == 2) {
            if (DlvSiteEnum.SELF.getCode().toString().equals(dlvdata.getDlvflag())) {
                // 变更物流指令
                UpdateForOrderDto modifyDoDto = new UpdateForOrderDto(dlvdata);
                modifyDoDto.setOrderId(orderNo);
                modifyDoDto.setOrderItem(orderItem);
                modifyDoDto.setUserDto(userDto);
                modifyDoDto.setDlvSite(DlvSiteEnum.SELF.getCode().toString());
                // bugid:9471 c14717 20230208
                Map<String, String> modifyDoResult = opsDoService.updateDoAddressNew(modifyDoDto, specialFlag);
                if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                    log.info(JSONUtil.toJsonStr(modifyDoResult));
                    throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
                }

                // 1.变更dlvdata表的自提和自提地址、身份证号
                orderDlvDataService.updateOrderDlvData(dlvdata, userDto);
                Orderdlvdata update = orderDlvDataService.getOrderDlvDataByOrder(orderNo, orderItem);
                // 2.变更rcvdetail的addressNo
                Rcvdetail modifyDetail = new Rcvdetail();
                modifyDetail.setRorderNo(orderNo);
                modifyDetail.setRorderItem(orderItem);
                modifyDetail.setAddressNo(update.getId().intValue());
                modifyRcvDetail(modifyDetail, userDto);
            } else {
                UpdateForOrderDto modifyDoDto = new UpdateForOrderDto(dlvdata);
                modifyDoDto.setOrderId(orderNo);
                modifyDoDto.setOrderItem(orderItem);
                modifyDoDto.setUserDto(userDto);
                modifyDoDto.setDlvSite(dlvdata.getDlvflag());
                Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(modifyDoDto);
            }

        }
        return 0;
    }

    /**
     * @description 变更特发
     * @author C12961
     * @date 2022/5/21 16:02
     */
    @Override
    public int modifyDlvSpecial(String orderNo, Integer orderItem, boolean special, UserDto userDto) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (rcvdetail.getExpDlvType() == null) {
            rcvdetail.setExpDlvType(0);
        }
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        // 修改时效标记
        int expDlvType = 0;
        int specialFlag = 0;
        // 要变为特发
        if (special) {
            // 本来就是特发，则不做操作
            if (OrderSpecExpType.include(rcvdetail.getExpDlvType(), OrderSpecExpType.ApplySpecExport)) {
                return 1;
            } else {
                expDlvType = rcvdetail.getExpDlvType() + OrderSpecExpType.ApplySpecExport.code();
                specialFlag = SpecialFlagEnum.SPECIAL.getCode();
            }
        }
        // 要变为普通
        else {
            // 本来就不包括特发
            if (!OrderSpecExpType.include(rcvdetail.getExpDlvType(), OrderSpecExpType.ApplySpecExport)) {
                return 1;
            } else {// 包括特发
                expDlvType = rcvdetail.getExpDlvType() - OrderSpecExpType.ApplySpecExport.code();
                specialFlag = SpecialFlagEnum.NORMAL.getCode();
            }
        }

        int enable = beforeWmStart(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 变更订单表
            Rcvdetail detail = new Rcvdetail();
            detail.setRorderNo(orderNo);
            detail.setRorderItem(orderItem);
            detail.setExpDlvType(expDlvType);
            modifyRcvDetail(detail, userDto);
            return 1;
        }
        // 要改物流指令
        else if (enable == 2) {
            // 变更物流指令
            UpdateForOrderDto updateForOrderDto = new UpdateForOrderDto();
            updateForOrderDto.setOrderId(orderNo);
            updateForOrderDto.setOrderItem(orderItem);
            updateForOrderDto.setSpecialFlag(specialFlag);
            updateForOrderDto.setUserDto(userDto);
            Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(updateForOrderDto);
            log.info("变更特发和普通，物流模块，结果：" + JSONUtil.toJsonStr(modifyDoResult));
            // 只要有一条物流指令修改成功， 就算成功
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 变更订单表
            Rcvdetail detail = new Rcvdetail();
            detail.setRorderNo(orderNo);
            detail.setRorderItem(orderItem);
            detail.setExpDlvType(expDlvType);
            modifyRcvDetail(detail, userDto);
        }
        return 0;
    }

    /**
     * @description 变更拆分子型号发货
     * @author C12961
     * @date 2022/5/21 16:02
     */
    @Override
    public int modifyDlvSplit(String orderNo, Integer orderItem, UserDto userDto) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (rcvdetail.getExpDlvType() == null) {
            rcvdetail.setExpDlvType(0);
        }
        if (ObjectUtil.isNull(rcvdetail)) {
            throw Exceptions.OpsException("rcv表找不到订单项");
        }
        Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(orderNo);
        // 判断是否为随发分批、是否为型号拆分、是否为已设置
        if (!CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())
                && !CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())) {
            throw Exceptions.OpsException("订单出库类型必须为随发分批或通知发货");
        }
        if (!OrderSplitTypeEnum.ASSModelNo.getSplitType().equals(rcvdetail.getProdFlag())) {
            throw Exceptions.OpsException("订单拆分类型必须为型号拆分");
        }
        // 修改时效标记
        int expDlvType = 0;


        // 本来就是子型号发货，则不做操作
        if (OrderSpecExpType.include(rcvdetail.getExpDlvType(), OrderSpecExpType.AssChildToExport)) {
            return 1;
        } else {
            expDlvType = rcvdetail.getExpDlvType() + OrderSpecExpType.AssChildToExport.code();
        }
        int enable = beforeWmStart(rcvdetail);
        if (enable == 0) {
            throw Exceptions.OpsException("当前订单状态不允许变更");
        }
        // 不改物流指令
        else if (enable == 1) {
            // 变更订单表
            Rcvdetail detail = new Rcvdetail();
            detail.setRorderNo(orderNo);
            detail.setRorderItem(orderItem);
            detail.setExpDlvType(expDlvType);
            modifyRcvDetail(detail, userDto);
            return 1;
        }
        // 要改物流指令
        else if (enable == 2) {
            // 变更物流指令
            UpdateForOrderDto updateForOrderDto = new UpdateForOrderDto();
            updateForOrderDto.setOrderId(orderNo);
            updateForOrderDto.setOrderItem(orderItem);
            updateForOrderDto.setAssModelChangeDo(true);
            updateForOrderDto.setUserDto(userDto);
            Map<String, String> modifyDoResult = opsDoService.UpdateDoForOrder(updateForOrderDto);
            if (modifyDoResult.size() > 0 && !modifyDoResult.containsValue("1")) {
                log.info(JSONUtil.toJsonStr(modifyDoResult));
                throw Exceptions.OpsException("物流指令变更失败:" + JSONUtil.toJsonStr(modifyDoResult));
            }
            // 变更订单表
            Rcvdetail detail = new Rcvdetail();
            detail.setRorderNo(orderNo);
            detail.setRorderItem(orderItem);
            detail.setExpDlvType(expDlvType);
            // detail.setProdFlag("1");
            modifyRcvDetail(detail, userDto);
        }
        return 0;
    }

    @Override
    public List<String> getRorderFnoByCredit() throws OpsException {
        // 查询订单号
        return opsRcvDetailDao.getRorderFnoByCredit();

    }


    /**
     * @description 查询订单项是否允许变更发货地址和承运商
     * 0，不允许修改  1，直接变更订单表  2，需修改订单表和物流指令
     * @author C12961
     * @date 2022/4/11 14:08
     */
    @Override
    public int beforeWmStart(Rcvdetail rcvdetail) throws OpsException {
        // rcv状态为【待处理】，可直接变更
        if (rcvdetail.getStatus() == RcvOrderStatusEnum.INIT.getType()) {
            return 1;
        }
        // rcv状态从【待处理】后， 到 【待出库】，需变更物流指令
        else if (rcvdetail.getStatus() > RcvOrderStatusEnum.INIT.getType() && rcvdetail.getStatus() <= RcvOrderStatusEnum.WAITCK.getType()) {
            return 2;
        }
        // 出库中,开始出库之后
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.CKING.getType()) {
            return 0;
        }
        // rcv状态为【已出库】，不可变更
        else if (rcvdetail.getStatus() >= RcvOrderStatusEnum.CKED.getType() && rcvdetail.getStatus() <= RcvOrderStatusEnum.CANCEL.getType()) {
            return 0;
        }// 处理异常，可直接变更 todo 0
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.EXCEPT.getType()) {
            return 1;
        }
        // 暂不处理，可直接变更 todo 0
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.UNDEAL.getType()) {
            return 1;
        }
        // 信用拦截，不可变更//改为可以变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.CREDIT.getType()) {
            return 2;
        }
        // 已开票，不可变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.INVOICE.getType()) {
            return 0;
        }
        // 待解决，需变更物流指令
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.RESOLVE.getType()) {
            return 2;
        }
        return 0;
    }

    /**
     * @description 查询订单项是否允许变更发货地址和承运商
     * 0，不允许修改  1，直接变更订单表  2，需修改订单表和物流指令
     * @param rcvdetail
     * @return
     * @throws OpsException
     */
    @Override
    public int beforeWmPackage(Rcvdetail rcvdetail) throws OpsException {
        // rcv状态为【待处理】，可直接变更
        if (rcvdetail.getStatus() == RcvOrderStatusEnum.INIT.getType()) {
            return 1;
        }
        // rcv状态从【待处理】后， 到 【待出库】，需变更物流指令
        else if (rcvdetail.getStatus() > RcvOrderStatusEnum.INIT.getType() && rcvdetail.getStatus() <= RcvOrderStatusEnum.WAITCK.getType()) {
            return 2;
        }
        // 出库中,开始出库之后
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.CKING.getType()) {
            List<OpsDo> doList = baseDoService.findAllJYCKByOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem().toString());
            for (OpsDo opsDo : doList) {
                if (!WmStatusEnum.PACKAGE.before(opsDo.getWmsStatus())){
                    return 0;
                }
            }
            return 2;
        }
        // rcv状态为【已出库】，不可变更
        else if (rcvdetail.getStatus() >= RcvOrderStatusEnum.CKED.getType() && rcvdetail.getStatus() <= RcvOrderStatusEnum.CANCEL.getType()) {
            return 0;
        }// 处理异常，可直接变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.EXCEPT.getType()) {
            return 1;
        }
        // 暂不处理，可直接变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.UNDEAL.getType()) {
            return 1;
        }
        // 信用拦截，不可变更//改为可以变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.CREDIT.getType()) {
            return 2;
        }
        // 已开票，不可变更
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.INVOICE.getType()) {
            return 0;
        }
        // 待解决，需变更物流指令
        else if (rcvdetail.getStatus() == RcvOrderStatusEnum.RESOLVE.getType()) {
            return 2;
        }
        return 0;
    }


    @Override
    public boolean successModifyDo(Map<String, String> resultMap) {
        if (resultMap.size() == 0) {
            return true;
        }
        for (String value : resultMap.values()) {
            if ("1".equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void modifyRcvDetail(Rcvdetail update, UserDto userDto) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(update.getRorderNo(), update.getRorderItem());
        RcvdetailExample ex = new RcvdetailExample();
        ex.createCriteria()
                .andDeleteStatusEqualTo((short) 0)
                .andRorderNoEqualTo(rcvdetail.getRorderNo())
                .andRorderItemEqualTo(rcvdetail.getRorderItem())
                .andVersionEqualTo(rcvdetail.getVersion());
        update.setRorderNo(null);
        update.setRorderItem(null);
        update.setVersion(rcvdetail.getVersion() + 1);
        update.setUpdateTime(new Date());
        update.setUpdateUser(userDto.getUserName());
        int i = rcvdetailMapper.updateByExampleSelective(update, ex);
        if (i != 1) {
            throw Exceptions.OpsException("订单并发异常" + i + "，订单" + rcvdetail.getRorderFno() + "正在被其他线程占用，无法修改状态");
        }
    }

}

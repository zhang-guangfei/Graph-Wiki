package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.conf.MailAddress;
import com.sales.ops.common.conf.OpsCommonConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsExceptionHandleDao;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.ba.ChangeYYDto;
import com.sales.ops.dto.easyexcel.DoConfirmCompensateTenTimeDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.feign.event.OpsOrderEventFeignApi;
import com.sales.ops.service.inventory.OpsExceptionHandleService;
import com.sales.ops.service.inventory.OpsInventoryBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author
 * @version 1.0
 * @description: TODO
 * @date 2022/10/25 10:55
 */
@Slf4j
@Service
public class OpsExceptionHandleServiceImpl implements OpsExceptionHandleService {

    @Autowired
    private OpsExceptionHandleMapper exceptionHandleMapper;
    @Autowired
    private OpsExceptionHandleDao opsExceptionHandleDao;
    @Resource
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;
    @Resource
    private OpsInventoryMapper opsInventoryMapper;
    @Autowired
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;
    @Autowired
    private OpsMailMapper opsMailMapper;
    @Autowired
    private OpsInventoryDao opsInventoryDao;
    @Autowired
    private MailAddress mailAddress;
    @Autowired
    private OpsCommonConfig opsCommonConfig;
    @Autowired
    private OpsDoMapper opsDoMapper;

    @Autowired
    private OpsPcoMapper opsPcoMapper;
    @Resource
    private OpsInventoryLogMapper inventoryLogMapper;
    @Autowired
    private OpsInventoryBaseService opsInventoryBaseService;

    @Autowired
    private OpsOrderEventFeignApi opsOrderEventFeignApi;

    /**
     * bugid:10804
     * wms 下预约请求 判断do，pco是否删除
     * 如果未删除，则判断do_status是否为3，如果是3，则可下预约，反之不可。
     * @param list
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ChangeYYDto> changeYY(List<ChangeYYDto> list){
        List<String> pcoList = new ArrayList<>();
        List<String> doList = new ArrayList<>();
        //组装pco和do
        for(ChangeYYDto param : list) {
            String flag =  param.getDeliveryOrderCode().substring(0,3);
            if("PCO".equals(flag)){
                pcoList.add(param.getDeliveryOrderCode());
            } else {
                doList.add(param.getDeliveryOrderCode());
            }
        }
        List<OpsDo> opsDoList = new ArrayList<>();
        //不可下预约集合do
        Map<String, Integer> doMap = new HashMap<>();
        //不可下预约集合pco
        Map<String,Integer> pcoMap = new HashMap<String,Integer>();
        if(CollectionUtils.isNotEmpty(doList)) {
            //批量查询do
            OpsDoExample doExample = new OpsDoExample();
            OpsDoExample.Criteria docriteria = doExample.createCriteria();
            docriteria.andDelflagEqualTo(0);
            docriteria.andDoStatusNotEqualTo(3);
            docriteria.andDoIdIn(doList);
            opsDoList = opsDoMapper.selectByExample(doExample);
            //不可下预约集合do
            doMap = opsDoList.stream().collect(Collectors.toMap(OpsDo::getDoId, OpsDo::getDoStatus, (key1, key2) -> key1));
        }
        //批量查询pco
        List<OpsPco> opsPcoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(pcoList)){
            OpsPcoExample pcoExample = new OpsPcoExample();
            OpsPcoExample.Criteria pcocriteria = pcoExample.createCriteria();
            pcocriteria.andDelflagEqualTo(0);
            pcocriteria.andPcoIdIn(pcoList);
            opsPcoList = opsPcoMapper.selectByExample(pcoExample);

            // 判断子项特发 pco 删除转do  C14717 20240905 bugid:15135
            for(String pcoId: pcoList) {
                OpsDoExample doExample = new OpsDoExample();
                OpsDoExample.Criteria docriteria = doExample.createCriteria();
                docriteria.andDelflagEqualTo(0);
                docriteria.andDoStatusNotEqualTo(3);
                docriteria.andRelocationEqualTo(pcoId);
                opsDoList = opsDoMapper.selectByExample(doExample);
                if(CollectionUtils.isNotEmpty(opsDoList)){
                    //不可下预约集合do
                    pcoMap.put(pcoId,1);
                }
            }
        }

        for(OpsPco opsPco : opsPcoList){
            OpsDoExample doE = new OpsDoExample();
            OpsDoExample.Criteria doc = doE.createCriteria();
            doc.andDelflagEqualTo(0);
            doc.andDoStatusNotEqualTo(3);
            doc.andOrderIdEqualTo(opsPco.getOrderId());
            doc.andOrderItemEqualTo(opsPco.getOrderItem());
            //todo 微导项目提交代码 还要增加pco表relation校验
            //doc.andNumEqualTo(opsPco.getNum());
            doc.andDoTypeEqualTo(DoTypeEnum.JYCK.getType());
            //不可下预约集合do
            List<OpsDo> opsDos = opsDoMapper.selectByExample(doE);
            if(CollectionUtils.isNotEmpty(opsDos)){
                //不可下预约集合do
                pcoMap.put(opsPco.getPcoId(),1);
            }
        }
        //可下预约清单赋值为true
        for(ChangeYYDto param : list){
            String flag =  param.getDeliveryOrderCode().substring(0,3);
            if("PCO".equals(flag)){
                if(!pcoMap.containsKey(param.getDeliveryOrderCode())){
                    param.setChangeYYFlag(true);
                    param.setChangeYYMsg("可下预约，已删单或已发货");
                }
            } else {
                if(!doMap.containsKey(param.getDeliveryOrderCode())){
                    param.setChangeYYFlag(true);
                    param.setChangeYYMsg("可下预约，已删单或已发货");
                }
            }
        }
        return list;
    }

    /**
     * buid 8697
     * @param
     *  //1.查找doitemInvnetory or pcoItemInventory
     * //2.更新itemInventory
     * //3.更新库存数量
     * //4.超十次发邮件
     * 2022-11-13以前不用处理，调整过库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int handleExceptionHandleToErrDoConfirm(OpsExceptionHandle excObj) throws OpsException{
        if(StringUtils.isBlank(excObj.getParameter7())){//doItemInventory
            //查找doItemInventory doid delflag = 0 and src_inventory_id= parameter_5 and src_inventory_table_type = parameter_6  and use_qty >= parameter_3
            List<OpsDoItemInventory> doItemInventoryList =  getDoItemInventoryList(excObj);
            if(CollectionUtils.isNotEmpty(doItemInventoryList) && doItemInventoryList.size() == 1){//测试通过 有关联关系可以扣库存
                //查找do
                OpsDo opsDo =  findDoByDoId(excObj.getParameter1());
                for(OpsDoItemInventory obj :doItemInventoryList){
                    int qty = Integer.parseInt(excObj.getParameter3());
                    //等王工上线 打开注释
                    if(Objects.isNull(obj.getOutQty())){
                        obj.setOutQty(0);
                    }
                    if(qty <= obj.getUseQty()-obj.getOutQty()){
                        //扣库存
                        opsInventoryBaseService.subQtyInventoryForPre(obj.getInventoryId(), qty, qty, obj.getInventoryTableType(), new UserDto("doConfirm异常定时任务",""));
                        insertOpsInventoryLogForDo(opsDo,obj.getInventoryId(),obj.getInventoryTableType(),qty,new UserDto("doCon补偿",""));
                        //变更outqty
                        obj.setOutQty(obj.getOutQty() + qty);
                        obj.setModifyTime(new Date());
                        obj.setModifier("errDoCon");
                        opsDoItemInventoryMapper.updateByPrimaryKey(obj);
                        //bugid:17305 C14717 20250425 补偿传事件
                        opsOrderEventFeignApi.warehouseDeliveryFinished( opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                        //处理成功
                        return 1;
                    }
                }
                //处理失败
                return 0;
            }else{//根据条件没有找到关联关系
                return 0;
            }
        }else {//pcoItemInventory
            //查找pcoItemInventory doid delflag = 0 and src_table_type= parameter_5 and src_inventory_table_type = parameter_6  and use_qty >= parameter_3
            List<OpsPcoItemInventory> pcoItemInventoryList =  getPcoItemInventoryList(excObj);
            if(CollectionUtils.isNotEmpty(pcoItemInventoryList) && pcoItemInventoryList.size() == 1){//有关联关系可以扣库存
                //查找do
                OpsDo opsDo =  findDoByDoId(excObj.getParameter1());
                for(OpsPcoItemInventory obj :pcoItemInventoryList){
                    int qty = Integer.parseInt(excObj.getParameter3());
                    //等王工上线 打开注释
                    if(qty <= obj.getUseQty()-obj.getOutQty()){
                        //扣库存
                        opsInventoryBaseService.subQtyInventoryForPre(obj.getInventoryId(), qty, qty, obj.getInventoryTableType(), new UserDto("doConfirm异常定时任务",""));
                        insertOpsInventoryLogForDo(opsDo,obj.getInventoryId(),obj.getInventoryTableType(),qty,new UserDto("doCon补偿",""));
                        //变更outqty
                        obj.setOutQty(obj.getOutQty() + qty);
                        obj.setModifyTime(new Date());
                        obj.setModifier("errDoCon");
                        pcoItemInventoryMapper.updateByPrimaryKey(obj);
                        //bugid:18574 c14717 20250826
                        opsOrderEventFeignApi.warehouseDeliveryFinished( opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                        //处理成功
                        return 1;
                    }
                }
                //处理失败
                return 0;
            }else{//根据条件没有找到关联关系
                //处理失败
                return 0;
            }
        }
    }


    /**
     * @description 查询单条出库指令通过doId
     * @author c14717
     * @date 2022/12/8 13:55
     * bugid 8966 c14717
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public OpsDo findDoByDoId(String doId) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDoIdEqualTo(doId);
        criteria.andDelflagEqualTo(0);
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (doList.size() < 1) {
            return null;
        }
        if (doList.size() > 1) {
            throw Exceptions.OpsException("查询出" + doList.size() + "条出库指令" + doId);
        }
        return doList.get(0);
    }

    /**
     * 记录日志
     * @param opsDo
     * @param inventoryId
     * @param invnetoryTableType
     * @param qty
     * @param userDto
     * @throws OpsException
     * @date 2022/12/8 13:55
     *  bugid 8966 c14717
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertOpsInventoryLogForDo(OpsDo opsDo, long inventoryId, String invnetoryTableType, int qty, UserDto userDto) throws OpsException {
        OpsInventoryLog opsInventoryLog = new OpsInventoryLog();
        opsInventoryLog.setInventoryId(inventoryId);
        opsInventoryLog.setInventoryTableType(invnetoryTableType);
        opsInventoryLog.setQuantity(qty);
        opsInventoryLog.setVoucherCode(opsDo.getDoId());
        opsInventoryLog.setVoucherType(opsDo.getDoType());
        opsInventoryLog.setOrderNo(opsDo.getOrderId());
        opsInventoryLog.setItemNo(Integer.parseInt(opsDo.getOrderItem()));
        OpsInventory opsInventory =  opsInventoryMapper.selectByPrimaryKey(inventoryId);
        opsInventoryLog.setPropertyId(opsInventory.getInventoryPropertyId());
        opsInventoryLog.setModelno(opsInventory.getModelno());
        opsInventoryLog.setWarehouseCode(opsInventory.getWarehouseCode());
        opsInventoryLog.setInvoiceNo(null);
        opsInventoryLog.setVersion(0);
        opsInventoryLog.setDelflag(0);
        opsInventoryLog.setCreTime(new Date());
        opsInventoryLog.setCreator(userDto.getUserName());
        opsInventoryLog.setType(false);
        inventoryLogMapper.insertSelective(opsInventoryLog);
    }


    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OpsPcoItemInventory> getPcoItemInventoryList(OpsExceptionHandle excObj){
        OpsPcoItemInventoryExample pcoExa = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criPco = pcoExa.createCriteria();
        criPco.andDelflagEqualTo(0);
        criPco.andPcoIdEqualTo(excObj.getParameter7());//pcoId
        criPco.andPcoItemEqualTo(Integer.parseInt(excObj.getParameter8()));//pcoItem
        criPco.andSrcInventoryIdEqualTo(Long.parseLong(excObj.getParameter5()));
        criPco.andSrcInventoryTableTypeEqualTo(excObj.getParameter6());
        criPco.andUseQtyGreaterThanOrEqualTo(Integer.parseInt(excObj.getParameter3()));
        return pcoItemInventoryMapper.selectByExample(pcoExa);
    }
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OpsDoItemInventory> getDoItemInventoryList(OpsExceptionHandle excObj){
        OpsDoItemInventoryExample doExa = new OpsDoItemInventoryExample();
        OpsDoItemInventoryExample.Criteria criDo = doExa.createCriteria();
        criDo.andDelflagEqualTo(0);
        criDo.andDoIdEqualTo(excObj.getParameter1());
        criDo.andSrcInventoryIdEqualTo(Long.parseLong(excObj.getParameter5()));
        criDo.andSrcInventoryTableTypeEqualTo(excObj.getParameter6());
        return opsDoItemInventoryMapper.selectByExample(doExa);
    }

    /**
     *异常处理数据超出次数
     * BUGID
     * @param
     * @param
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMailToDb(String fileUrls){
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //附件地址
        opsMail.setFileUrls(fileUrls);
        //opsMail.setMailFrom(mail.getFrom());
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //抄送
        opsMail.setCc(mailAddress.getExceptionHandleCC());
        opsMail.setMailTo(mailAddress.getExceptionHandleMailTo());
        //主题
        opsMail.setSubject("异常处理数据超出10次数");
        StringBuffer con = new StringBuffer();
        con.append("<h4> ");
        con.append("异常表ops_exception_handle行记录:");
        con.append("请查看附件");
        con.append(" </h4>");

        //邮件内容
        opsMail.setContext(con.toString());
        //发件人名称
        opsMail.setNickName("ops邮件");
        opsMailMapper.insertSelective(opsMail);
    }

    /**
     * 变为已处理
     * @param excObj
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handStatus(OpsExceptionHandle excObj){
        OpsExceptionHandle failObj = new OpsExceptionHandle();
        failObj.setId(excObj.getId());
        failObj.setStatus(1);
        failObj.setUpdateTime(new Date());
        failObj.setUpdateUser("doConfirm异常定时任务");
        exceptionHandleMapper.updateByPrimaryKeySelective(failObj);
    }

    /**
     * 处理成功
     * @param excObj
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handSucess(OpsExceptionHandle excObj){
        OpsExceptionHandleExample handleSucess = new OpsExceptionHandleExample();
        OpsExceptionHandleExample.Criteria criSucess = handleSucess.createCriteria();
        criSucess.andIdEqualTo(excObj.getId());
        OpsExceptionHandle sucessHandleObj = new OpsExceptionHandle();
        sucessHandleObj.setStatus(1);//处理成功
        sucessHandleObj.setHandleStatus(0);//处理成功
        sucessHandleObj.setUpdateTime(new Date());
        sucessHandleObj.setUpdateUser("doConfirm异常定时任务");
        exceptionHandleMapper.updateByExampleSelective(sucessHandleObj,handleSucess);
    }

    /**
     * 处理失败
     * @param excObj
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handFail(OpsExceptionHandle excObj,List<DoConfirmCompensateTenTimeDto> excelDtoList){
        OpsExceptionHandle failObj = new OpsExceptionHandle();
        failObj.setId(excObj.getId());
        if(Objects.isNull(excObj.getReadQty())){
            excObj.setReadQty(0);
        }
        //拼接字符串
        if(excObj.getReadQty() <  opsCommonConfig.getCoConfirmErrorFrequency()){
            failObj.setReadQty(excObj.getReadQty()+1);
            failObj.setStatus(0);
            failObj.setHandleStatus(1);
            failObj.setUpdateTime(new Date());
            failObj.setUpdateUser("doConfirm异常定时任务");
            exceptionHandleMapper.updateByPrimaryKeySelective(failObj);
        }else{//异常发邮件
            //saveMailToDb(excObj.getId(), JSONUtil.toJsonStr(excObj));
            DoConfirmCompensateTenTimeDto dto = new DoConfirmCompensateTenTimeDto();
            OpsDo opsDo = null;
            try {
                opsDo = findDoByDoId(excObj.getParameter1());
                dto.setOrderNo(opsDo.getOrderId());
                dto.setOrderItem(opsDo.getOrderItem());
            } catch (OpsException e) {
                log.error("没有查到do",e);
            }
            dto.setDoid(excObj.getParameter1());
            dto.setWarehouseCode(excObj.getParameter4());
            dto.setQty(excObj.getParameter3());
            dto.setInventoryId(excObj.getParameter5());
            dto.setInventoryTableType(excObj.getParameter6());
            dto.setPcoId(excObj.getParameter7());
            dto.setPcoItem(excObj.getParameter8());
            excelDtoList.add(dto);
        }
    }



    private void watiTime(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }



}

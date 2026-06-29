package com.sales.ops;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.common.rabbitmq.constants.Constants;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.common.until.RedisMQManager;
import com.sales.ops.db.batchdao.AddBatchOrderInventoryLogDao;
import com.sales.ops.db.batchdao.OpsRoBarcodeBatchDao;
import com.sales.ops.db.batchdao.OpsRoConfirmLogBatchDao;
import com.sales.ops.db.batchdao.WmOrderTaskBatchDao;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidIDDto;
import com.sales.ops.dto.order.OpsWTInventoryDTO;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.rabbitmq.SendMessage;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.log.OpsRoPostService;
import com.sales.ops.service.log.RoConfirmLogService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.web.controller.wmOrder.WmDispatchController;
import com.sales.ops.web.controller.wmOrder.WmDoController;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.core.utils.JwtUtil;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：出库准备
 * @date ：Created in 2022/3/5 13:28
 */
@Slf4j
public class WmDo extends BaseTest {

    @Autowired
    private WmDoController wmDoService;

    @Autowired
    private WmDoService wmDoSe;

    @Autowired
    private OpsDoService opsDoService;

    @Autowired
    private BaseDoService baseDoService;



    @Autowired
    private OpsRoService opsRoService;

    @Autowired
    private WmDispatchService wmDispatchService;


    @Autowired
    private BinServiceFeignApi binServiceFeignApi;

    @Autowired
    private OPSProductFeignApi opsProductFeignApi;

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;
    @Autowired
    private AllotInvenToryService allotInvenToryService;

    @Resource
    private SendMessage sendMessage;
    @Resource
    private OrderdlvdataMapper orderdlvdataMapper;
    @Autowired
    private WmRouterOrderService wmRouterOrderService;

    @Autowired
    private WmOrderTaskFindService findOrderTaskService;

    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;


    @Autowired
    private WmOrderTaskBatchDao wmOrderTaskBatchDao;

    @Resource
    private OpsRoBarcodeBatchDao OpsRoBarcodeBatchDao;
    @Resource
    private OpsRoConfirmLogBatchDao opsRoConfirmLogBatchDao;

    @Resource
    private OpsInventoryMoveMapper opsInventoryMoveMapper;

    @Autowired
    private WmCommonService wmCommonService;

//
//    @Autowired
//    private BaseController baseController;

    @Value("${smccloud.oauth2.client.clientId}")
    private String clientId;

    @Value("${smccloud.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${smccloud.oauth2.client.access-token-uri}")
    private String accessTokeUri;

    private final static String TOKEN_HEADER = "Authorization";
    private final static String GRANT_TYPE_HEADER = "grant_type";
    @Resource
    private ExpdetailMapper expdetailMapper;

    @Autowired
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;

    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private TempDoconfirmMapper tempDoconfirmMapper;
    @Autowired
    private RoConfirmLogService roConfirmLogService;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private OpsRoPostService opsRoPostService;

    @Test
    public void testttt() {
        List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId("DBC15AACBK016001000");
        if (org.springframework.util.CollectionUtils.isEmpty(opsDoItemInventories)) {
            System.out.println(1);
        }
    }

    @Test
    public void testDoConfirm() {
        TempDoconfirmExample example = new TempDoconfirmExample();
        example.createCriteria().andOptStatusEqualTo("0");
        List<TempDoconfirm> list = tempDoconfirmMapper.selectByExample(example);
        for (TempDoconfirm ttt : list) {
            try {
                WmDoConfirmDto obj = new WmDoConfirmDto();
                obj.setDeliveryOrderCode(ttt.getDoId());
                WmDoItemConfirmDto item = new WmDoItemConfirmDto();
                item.setQty(Double.parseDouble(ttt.getQtyDiff() + ""));
                obj.setShipTime(ttt.getShipDate());
                obj.setItems(item);
                opsDoService.wmDoConfirm(obj);

                //更新中间表
                TempDoconfirmExample updateE = new TempDoconfirmExample();
                updateE.createCriteria().andDoIdEqualTo(ttt.getDoId());
                TempDoconfirm aaa = new TempDoconfirm();
                aaa.setOptStatus("1");
                aaa.setOptMsg("成功");
                tempDoconfirmMapper.updateByExampleSelective(aaa, updateE);
            } catch (Exception e) {
                TempDoconfirmExample updateE = new TempDoconfirmExample();
                updateE.createCriteria().andDoIdEqualTo(ttt.getDoId());
                TempDoconfirm aaa = new TempDoconfirm();
                aaa.setOptStatus("2");
                aaa.setOptMsg(e.getMessage());
                tempDoconfirmMapper.updateByExampleSelective(aaa, updateE);
            }
        }
    }


    @Test
    public void testPreQty() {
        //(Long inventoryId, int qty, String tableType, UserDto userDto)
        try {
            baseInventoryService.UpdatePreQtyInventory(626554L, -5, "N", new UserDto("TEST", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(123);
    }

    @Test
    public void testDOcOLL() {
        try {
            boolean isEnough = true;
            List<OpsDo> doList = baseDoService.findDoListByOrder("10026972", "8", null, DoTypeEnum.JYCK);
            OpsPcoAddItemDto opsPcoAddItemDto = opsPcoService.findPcoToWms("PCO10026972008001000");
            //调拨出
            OpsPco opsPco = opsPcoAddItemDto.getOpsPco();
            List<OpsPcoItem> opsPcoItems = opsPcoAddItemDto.getList();

            for (OpsDo opsDoSub : doList) { //判断子项是否都是ok
                if (DoWaitTypeEnum.WaitDB.getType().equals(opsDoSub.getWaitType()) || DoWaitTypeEnum.WaitCG.getType().equals(opsDoSub.getWaitType())) {
                    isEnough = false;
                    break;
                } else if (DoWaitTypeEnum.WaitJG.getType().equals(opsDoSub.getWaitType())) {
                    for (OpsPcoItem opsPcoItemSub : opsPcoItems) {
                        if (DoWaitTypeEnum.WaitDB.getType().equals(opsPcoItemSub.getWaitType()) || DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItemSub.getWaitType())) {
                            isEnough = false;
                            break;
                        }
                    }
                }
                if (!isEnough) {
                    break;
                }
            }
            if (isEnough) {//判断子项在库是否都ok
                for (OpsDo opsDoSub : doList) {
                    if (DoWaitTypeEnum.OK.getType().equals(opsDoSub.getWaitType())) {
                        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
                        example.createCriteria().andDoIdEqualTo(opsDoSub.getDoId()).andDelflagEqualTo(0)
                                .andUseQtyGreaterThan(0).andInventoryTableTypeEqualTo(InventoryStatusEnum.NORMAL.getCode());
                        List<OpsDoItemInventory> dtolist = opsDoItemInventoryMapper.selectByExample(example);
                        if (org.springframework.util.CollectionUtils.isEmpty(dtolist)) {
                            isEnough = false;
                            break;
                        }
                        int inventoryQty = 0;
                        for (OpsDoItemInventory opsDoItemInventory : dtolist) {
                            inventoryQty = inventoryQty + opsDoItemInventory.getUseQty();
                        }
                        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDoSub.getDoId());
                        if (inventoryQty != opsDoItem.getQty()) { //数量不够
                            isEnough = false;
                            break;
                        }
                    } else if (DoWaitTypeEnum.WaitJG.getType().equals(opsDoSub.getWaitType())) {//加工单货齐判断
                        List<OpsPco> opsPcoList = opsPcoService.GetPcolistByOrder(opsPco.getOrderId(), opsPco.getOrderItem());
                        for (OpsPco opsPcoSub : opsPcoList) {
                            if (!isEnough) {
                                break;
                            }
                            List<OpsPcoItem> opsPcoSubList = opsPcoService.selectItemBypcoId(opsPcoSub.getPcoId());
                            for (OpsPcoItem opsPcoItem : opsPcoSubList) {
                                if (!DoWaitTypeEnum.OK.getType().equals(opsPcoItem.getWaitType())) {
                                    isEnough = false;
                                    break;
                                }
                            }
                            if (isEnough) {
                                for (OpsPcoItem opsPcoItem : opsPcoSubList) {
                                    List<OpsPcoItemInventory> subPcoItemsInventoryList = opsPcoService.selectItemInventoryBypcoId(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem(), InventoryTableTypeEnum.NORMAL);
                                    int inventoryQty = 0;
                                    for (OpsPcoItemInventory opsPcoItemInventory : subPcoItemsInventoryList) {
                                        inventoryQty = inventoryQty + opsPcoItemInventory.getUseQty();
                                    }
                                    if (inventoryQty != opsPcoItem.getSubQty()) { //数量不够
                                        isEnough = false;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        isEnough = false;
                        break;
                    }
                }
            }


            System.out.println(isEnough);
        /*if (isEnough) {//满足出库条件

        }*/
        } catch (OpsException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private WmOrderTaskService wmOrderTaskService;

    @Autowired
    private NonseedTaskMapper nonseedTaskMapper;
    @Autowired
    private OpsDoItemMapper opsDoItemMapper;

    @Autowired
    private DoConfirmErrorDataMapper doConfirmErrorDataMapper; // opsDoItemMapper;

    /**
     * 更加doid 和数量 修改doItem-outQty 修改 doItemInvnetory - outQty 天加ops_inventory qty 和 预占数量
     */
    @Test
    public void upateYCData() {

            /*DoConfirmErrorDataExample example = new DoConfirmErrorDataExample();
            example.createCriteria().andHandleStatusEqualTo(0);
            List<DoConfirmErrorData> list = doConfirmErrorDataMapper.selectByExample(example);
            for(DoConfirmErrorData obj : list){*/
        String doid = "DO10014105011001000";
        Integer outQty = -1;//负数
        try {
            //1.修改doItem outQty数量
            OpsDoItemExample doItemSearchExample = new OpsDoItemExample();
            OpsDoItemExample.Criteria doItemSearchCriteria = doItemSearchExample.createCriteria();
            doItemSearchCriteria.andDoIdEqualTo(doid);//todo doid
            doItemSearchCriteria.andDelflagEqualTo(0);
            List<OpsDoItem> opsDoItems = opsDoItemMapper.selectByExample(doItemSearchExample);
            OpsDoItem opsDoItem = opsDoItems.get(0);
            opsDoItem.setOutQty(opsDoItem.getOutQty() + outQty);//TODO outqty
            opsDoItem.setVersion(opsDoItem.getVersion() + 1);
            OpsDoItemExample opsDoItemUpdateExample = new OpsDoItemExample();
            OpsDoItemExample.Criteria opsDoItemUpdateCriteria = opsDoItemUpdateExample.createCriteria();
            opsDoItemUpdateCriteria.andDoIdEqualTo(doid);//出库指令
            opsDoItemUpdateCriteria.andDelflagEqualTo(0);//删除标识
            opsDoItemUpdateCriteria.andVersionEqualTo(opsDoItem.getVersion() - 1);//并发控制
            opsDoItem.setId(null);
            opsDoItem.setDoId(null);
            opsDoItemMapper.updateByExampleSelective(opsDoItem, opsDoItemUpdateExample);
            //修改doItemInventory 数量 添加预占库存和数量
            OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
            exampleDoItemInventory.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doid);
            List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
            //out_qty 不够不做扣减
            if (opsDoItemInventoryList == null || opsDoItemInventoryList.isEmpty()) {
                //记录日志继续进行
                throw Exceptions.OpsException("库存无占用" + doid);
            } else {
                //直接扣减库存  加工单整型号不扣减库存
                Integer wmOutQty = outQty;
                for (OpsDoItemInventory item : opsDoItemInventoryList) {
                    //物流出库数量大于 当前item 继续循环
                    if (Objects.isNull(item.getOutQty())) {
                        item.setOutQty(0);
                    }
                    if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                        continue;
                    }
                    //wms 出库数量 减 （当前关联关系未出数量）
                    if (item.getDoId().equals("DO33AJHZZ001002000H")) {//存在2条doItemInventory
                        if (item.getId().equals(115052L)) {
                            baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                            item.setOutQty(item.getOutQty() + wmOutQty);
                            opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        }
                    } else if (item.getDoId().equals("DOH04VAAA005000000H")) {//存在2条doItemInventory
                        if (item.getId().equals(436385L)) {//436385
                            baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                            item.setOutQty(item.getOutQty() + wmOutQty);
                            opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        }

                    } else if (item.getDoId().equals("DOH04SKA7008000000H")) {//存在2条doItemInventory
                        if (item.getId().equals(68898L)) {//436385
                            baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                            item.setOutQty(item.getOutQty() + wmOutQty);
                            opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        }

                    } else if (item.getDoId().equals("DO72AD5TF013000000H")) {//存在2条doItemInventory
                        if (item.getId().equals(75010L)) {//436385
                            baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                            item.setOutQty(item.getOutQty() + wmOutQty);
                            opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        }

                    } else if (item.getDoId().equals("DO79AKXTJ003000000H")) {//存在2条doItemInventory
                        if (item.getId().equals(32888L)) {//436385
                            baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                            item.setOutQty(item.getOutQty() + wmOutQty);
                            opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        }

                    } else {
                        baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), new UserDto("doConfirm重复", ""));
                        item.setOutQty(item.getOutQty() + wmOutQty);
                        opsDoItemInventoryMapper.updateByPrimaryKey(item);
                    }
                }
            }
            //处理成功
                   /* DoConfirmErrorDataExample updateExp = new DoConfirmErrorDataExample();
                    example.createCriteria().andDeliveryNoEqualTo(obj.getDeliveryNo());
                    DoConfirmErrorData updateObj = new DoConfirmErrorData();
                    updateObj.setHandleMsg("成功");
                    updateObj.setHandleStatus(1);
                    doConfirmErrorDataMapper.updateByExampleSelective(updateObj,updateExp);*/
        } catch (Exception e) {
            //处理失败
                    /*DoConfirmErrorDataExample updateExp = new DoConfirmErrorDataExample();
                    example.createCriteria().andDeliveryNoEqualTo(obj.getDeliveryNo());
                    DoConfirmErrorData updateObj = new DoConfirmErrorData();
                    updateObj.setHandleMsg(e.getMessage());
                    updateObj.setHandleStatus(2);
                    doConfirmErrorDataMapper.updateByExampleSelective(updateObj,updateExp);*/
        }
        //}


    }

    @Test
    public void testCancelOrder() {//10046874-5-1;
        //根据doid取消wms指令,如果是加工单,自动带出单号;

        //DOE1AESK5015000000H
        wmOrderTaskService.getMidDoIdTableCancelWmsDoAndPco("DOE1AESK5015000000H", false);//DO10046874005001000 DBC10019089001002000

        /*NonseedTaskExample example = new NonseedTaskExample();
        example.createCriteria().andHandleStatusEqualTo(0);
        List<NonseedTask> list = nonseedTaskMapper.selectByExample(example);
        for(NonseedTask nonseedTask : list){
            boolean flag = false;
            try {
                flag = wmOrderTaskService.getMidDoIdTableCancelWmsDoAndPco(nonseedTask.getDoId(), false);//DO10046874005001000 DBC10019089001002000
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            NonseedTaskExample exa = new NonseedTaskExample();
            exa.createCriteria().andDoIdEqualTo(nonseedTask.getDoId());
            if(flag){
                nonseedTask.setHandleStatus(1);
            }else {
                nonseedTask.setHandleStatus(2);
            }

            nonseedTaskMapper.updateByExample(nonseedTask,example);
        }*/


    }

    @Test
    public void testPcoSendWms() {

        OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto();
        try {
            obj.setPcoId("PCO10026972008001000");  //PCOH032WAJ001000001H   PCO11ABQ0K015000001H  DO10020417001001000
            obj.setDoId("DO10026972008001000");//
            CommonResult result = wmDoSe.updateWMSPcoAddDoTwo(obj);
            System.out.println(result.getMessage());
        } catch (OpsException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void apply() {
        Object tokenObj = ThreadLocalMapUtil.get(TOKEN_HEADER);
        if (tokenObj != null) {
            String token = StringUtils.substringAfter(tokenObj.toString(), "Bearer ");
            boolean valid = JwtUtil.verifyToken(token);
            if (valid) {
                return;
            }
        }

        String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
        String s = HttpRequest.sendPost(accessTokeUri, param);

        ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
        // System.out.println("clientDetails = " + clientDetails.getAccess_token());
        // 设置token
        String token = "Bearer " + clientDetails.getAccess_token();
        ThreadLocalMapUtil.put(TOKEN_HEADER, token);

    }


    @Test
    public void testUser() {
        List<OpsInventoryMove> invMoveList = baseInventoryService.selectOpsInventoryMoveListByPo("XLF-40MH4-2",
                "11ABRT7", "6", 0, null);

        if (CollectionUtils.isNotEmpty(invMoveList)) {
            if (invMoveList.get(0).getQuantity() < 104) {
                System.out.println(123);
            } else {
            }
        }
    }



    @Test
    public void testBatchInsert() {

        try {
            List<OpsWmOrderTask> list = new ArrayList<OpsWmOrderTask>();
            OpsWmOrderTask obj1 = new OpsWmOrderTask();
            obj1.setWmOrderId("12345");
            obj1.setCreTime(new Date());
            list.add(obj1);

            wmOrderTaskBatchDao.wmOrderTaskbatchInsert(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 出库规则查询
     */
    @Test
    public void testp() throws OpsException {
        List<OpsDoItemInventory> doItemInventory = baseDoService.getDoItemInventoryByDoId("DO10157562003001000");
        if (doItemInventory.size() > 0) {
            List<OpsWTInventoryDTO> list = new ArrayList<OpsWTInventoryDTO>();
            for (OpsDoItemInventory itemInventory : doItemInventory) {
                InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                InventoryDTO dto = getInventoryDto(tableTypeEnum, itemInventory.getInventoryId());
                OpsInventoryProperty property = opsInventoryPropertyService.findById(dto.getInventoryPropertyId());
                OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                obj.setInventoryId(itemInventory.getInventoryId());
                obj.setInventoryType(property.getInventoryTypeCode());
                list.add(obj);
            }
            System.out.println(2);
        }
        System.out.println(1);
    }

    public InventoryDTO getInventoryDto(InventoryTableTypeEnum tableType, Long inventoryId) throws OpsException {
        if (InventoryTableTypeEnum.NORMAL.equals(tableType)) {
            OpsInventory inventory = baseInventoryService.getInventoryById(inventoryId);
            if (ObjectUtil.isNotNull(inventory)) {
                return new InventoryDTO(inventory);
            }
        }
        if (InventoryTableTypeEnum.TRANS.equals(tableType)) {
            OpsInventoryMove inventory = baseInventoryService.getInventoryMoveById(inventoryId);
            if (ObjectUtil.isNotNull(inventory)) {
                return new InventoryDTO(inventory);
            }
        }
        throw Exceptions.OpsException("没有查询到库存");
    }

    @Resource
    private RcvdetailMapper rcvdetailMapper;
/*
*
*
10124171-35
* */

    /**
     * 测试出库规则
     */
    @Test
    public void testCKRule() throws OpsException {
        try {
            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster("11ABU5Y");
            Rcvdetail rcvdetail = null;//wmRouterOrderService.getRcvdetail("10045764", "1");

            RcvdetailExample example1 = new RcvdetailExample();
            example1.createCriteria().andRorderNoEqualTo("11ABU5Y").andRorderItemEqualTo(1);
            List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example1);
            rcvdetail = rcvdetailList.get(0);
            OrderdlvdataKey orderdlvdataKey = new OrderdlvdataKey();
            orderdlvdataKey.setOrderno(rcvmaster.getRorderNo());
            orderdlvdataKey.setItemno(rcvdetail.getRorderItem());
            Orderdlvdata orderdlvdata = orderdlvdataMapper.selectByPrimaryKey(orderdlvdataKey);
            if (ObjectUtil.isNull(orderdlvdata)) {
                OrderdlvdataExample example = new OrderdlvdataExample();
                example.createCriteria().andOrdernoEqualTo(rcvmaster.getRorderNo()).andItemnoEqualTo(0);
                List<Orderdlvdata> orderdlvdataList = orderdlvdataMapper.selectByExample(example);
                if (orderdlvdataList.size() > 0) {
                    orderdlvdata = orderdlvdataList.get(0);
                }
            }
            if (Objects.isNull(orderdlvdata)) {
                throw Exceptions.OpsException("单号：" + rcvmaster.getRorderNo() + " ; orderdlvdata表无数据");
            }
            InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto(rcvmaster, rcvdetail, orderdlvdata);
            InventoryCkByOrderOutDto outDto = allotInvenToryService.getOpsInventoryListByCk(inputDto);
            if (!inputDto.isAllotStatus()) {
                if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {// 拆分型号不拆数量
                    if (outDto.getAssBom().getProductBom().getPriorityComplete()) {// 优先能型号采购
                        if (OrderTypeEnum.YIBANMAOYI.equals(inputDto.getOrderType())) {
                            throw Exceptions.OpsException("一般贸易单判断不采购");
                        }
                        if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {//
                            throw Exceptions.OpsException("特殊标识为必须出委托不采购");
                        }
                        outDto.setAssBom(null);
                        outDto.setDoSourceEnum(DoSourceEnum.CG);
                        inputDto.setAllotQuantity(0);
                    } else {
                        for (AssBomDetail assBomDetail : outDto.getAssBom().getDetailList()) {// 子型号不拆数量
                            if (!assBomDetail.IsEnough()) {
                                // 一般贸易单判断 不采购
                                if (OrderTypeEnum.YIBANMAOYI.equals(inputDto.getOrderType())) {
                                    throw Exceptions.OpsException("一般贸易单判断不采购");
                                }
                                if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {
                                    throw Exceptions.OpsException("特殊标识为必须出委托不采购");
                                }
                                assBomDetail.setAssAllotQty(0);
                                assBomDetail.setMapMoveqty(null);
                                assBomDetail.setMapqty(null);
                            }
                        }
                    }
                }
            }

            String gatherhouse = GetGatherHoues(inputDto, outDto);
            inputDto.setWarehouseCode(gatherhouse);
            outDto.setWarehouseCode(gatherhouse);
            InventoryDispatchDto dispatchDto = new InventoryDispatchDto(outDto);

            System.out.println(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Autowired
    private OpsWarehouseSalesbranchService opsWarehouseSalesbranchService;

    private String GetGatherHoues(InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto)
            throws OpsException {
        List<String> houseList = new ArrayList<String>(outDto.getWarehouseCodeSets());
        String gatherhouse = null;
        if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(inputDto.getCKType())
                || CKTYPEEnum.ITEM_UNLIMIT.equals(inputDto.getCKType())) {// 货齐单仓 选择默认集约仓库 随到随发 选择默认集约仓用于采购do
            List<OpsWarehouseSalesbranchConfig> opsWarehouseSalesbranchConfigList = opsWarehouseSalesbranchService
                    .getGatherWarehouses(inputDto.getDeptno(), null);
            if (Objects.isNull(opsWarehouseSalesbranchConfigList) || opsWarehouseSalesbranchConfigList.isEmpty()) {
                throw Exceptions.OpsException("当前部门代码不存在可出库仓!" + inputDto.getDeptno(), inputDto.getDeptno());
            } else {
                for (OpsWarehouseSalesbranchConfig warehouseSalesbranchConfig : opsWarehouseSalesbranchConfigList) {
                    OpsWarehouse opsWarehouse = opsWarehouseService
                            .findById(warehouseSalesbranchConfig.getWarehouseCode());
                    if (opsWarehouse.getCentralizeFlag()) {
                        return opsWarehouse.getWarehouseCode();
                    }
                }
            }
        }

        int houseListSize = houseList.size();
        if (inputDto.isAllotStatus()) {// 库存数量满足
            if (houseListSize > 1 || houseListSize == 0) {// 订单单项出货多仓 或没有仓库
                //todo
                //gatherhouse = opsWarehouseSalesbranchService.getGatherWarehousesWithBranchId(inputDto.getCKType(), inputDto.getDeptno(), outDto.getDoSourceEnum());
            } else { // 单仓
                // 货齐单仓出库
                gatherhouse = houseList.get(0);
            }
        } else {// 库存数量不满足map
            //todo
            //gatherhouse = opsWarehouseSalesbranchService.getGatherWarehousesWithBranchId(inputDto.getCKType(), inputDto.getDeptno(), outDto.getDoSourceEnum());
        }
        if (!CKTYPEEnum.ITEM_UNLIMIT.equals(inputDto.getCKType())) {//
            if (StringUtils.isEmpty(gatherhouse)) {
                throw Exceptions.OpsException("当前部门代码不存在可集约出库仓!" + inputDto.getDeptno(), inputDto.getDeptno());// 如果是拆分型号，需要看是否有集约能力和拆分能力
            }
        }

        return gatherhouse;
    }

    @Autowired
    private OpsCustomerMapper opsCustomerMapper;

    @Test
    public void test11() {
        if (StringUtils.isNotBlank("C22G7")) {
            OpsCustomer opsCustomer = opsCustomerMapper.selectByPrimaryKey("C22G7");
            if (Objects.nonNull(opsCustomer)) {
                System.out.println(123);
            } else {
                System.out.println("无数据");//公司名称
            }
        } else {
            System.out.println("无数据");//公司名称
        }
    }

    @Test
    public void testMQ() {
        try {
            RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
            OpsWebApiLog logVo = new OpsWebApiLog();
            logVo.setIp("10.116.1.1");
            logVo.setUrl("http://192.168.1.1");
            logVo.setDescription("test");
            logVo.setParams("test");
            logVo.setResult("test");
            logVo.setCreateTime(new Date());
            logVo.setUseTime(123L);

            rabbitMqMessage.setContent(JSON.toJSONString(logVo));
            rabbitMqMessage.setFlag(Constants.OPS_API_LOG);
            rabbitMqMessage.setDataType(Constants.APILOG);
            rabbitMqMessage.setSystem(Constants.OPS);
            boolean sendResult = sendMessage.sendApiLogMsg(rabbitMqMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private OrderStateServiceFeignApi test;

    @Test
    public void testMinPack() throws OpsException {
        CommonResult<Product> result = opsProductFeignApi.searchProduct("KK4P-06E");
        System.out.println(1);
   /*
        if (!result.isSuccess()) {
            throw Exceptions.OpsException("获取product信息失败-minPack");
        }
        if (result.getData().getMinPackUnit() == null) {
            //不用操作
            return;
        }
        if (result.getData().getMinPackUnit() == 0) {
            //不用操作
            return;
            //throw Exceptions.OpsException("获取产品最小包装单位为0-minPack");
        }
*/

        /*ResultVo<Boolean> isBin = binServiceFeignApi.isBinModel("KK4P-06E");
        if (!isBin.isSuccess()) {
            throw Exceptions.OpsException("获取bin库存配置信息失败-minPack");
        }
        if (!isBin.getData()) {//非bin
            //返回采购数量 （在库数量/最小包装）* 最小包装
            //int cgNum = 40 - (int) ((ckNum / result.getData().getMinPackUnit()) * result.getData().getMinPackUnit());
            //ckInventoryChange(outDto, inputDto, cgNum);
        } else {//bin 向上取整
           *//* //（(订单数量 - 在库数量) /最小包装数量）* 最小包装数量
            int cgNum = (int) Math.ceil((40 - 12) / (result.getData().getMinPackUnit() * 1.00)) * result.getData().getMinPackUnit();
            //ckInventoryChange(outDto, inputDto, cgNum);
            System.out.println(cgNum);*//*
        }*/
    }


    @Test
    public void testDo() {
        try {
            wmDoService.sendDoToWMSChangeStatus("10");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPco() {
        try {
            wmDoService.sendPcoToWMSChangeStatus("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testGenerateLogistics1() {
        List<OpsInventoryMove> moveList = baseInventoryService.selectOpsInventoryMoveListByPo("XGTP311-72641-NCN-X925",
                "11ABNBK", "4", 0, "LCN968");

        System.out.println("测试迁移数据 在途数据 migrate");
    }

    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private OpsRedissonLock opsRedissonLock;

    @Resource
    private RedisMQManager redisUtil;



    @Autowired
    private AddBatchOrderInventoryLogDao addBatchOrderInventoryLogDao;

    @Test
    public void createDoNewIdTest() {
        try {

            List<OpsOrderInventoryLog> temp = new ArrayList<>();
            OpsOrderInventoryLog log = new OpsOrderInventoryLog();
            log.setCreTime(new Date());
            log.setOrderId("123333");
            log.setOrderItem("1");
            log.setModelno("test");
            log.setQty(2);
            log.setOrderQty(2);
            temp.add(log);
            addBatchOrderInventoryLogDao.batchInsertOrderBatchOrderInventoryLog(temp);
            System.out.println(123);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Resource
    private ImpInvoiceEventLogMapper impInvoiceEventLogMapper;

    @Resource
    private WmDispatchController wmDispatchController;


    @Test
    public void logTest() throws OpsException {

        CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
        inputDto.setApplyNo("TH2204250008");
        inputDto.setItemNo(1);
        inputDto.setModelNo("KQ2U06-00A");
        inputDto.setQty(20);
        inputDto.setWarehouseCode("KBJ");
        System.out.println("123");
        ImpInvoiceEventLog impInvoiceEventLog = new ImpInvoiceEventLog();
        impInvoiceEventLog.setOpType("test");
        impInvoiceEventLog.setRequestParam(JSON.toJSONString(inputDto));
        impInvoiceEventLog.setOpStartTime(new Date());
        impInvoiceEventLog.setOpStatus(0);
        impInvoiceEventLogMapper.insertSelective(impInvoiceEventLog);
    }

    @Autowired
    private BaseInventoryService baseInventoryService;


    @Autowired
    private OpsInventoryMoveDatamigrationMasterMapper opsInventoryMoveDatamigrationMasterMapper;
    @Autowired
    private OpsInventoryMoveDatamigrationDetailMapper opsInventoryMoveDatamigrationDetailMapper;



    @Test
    public void addImpInvoiceEventLogTest() throws OpsException, InterruptedException {

        Date fromDate = new Date();
        ImpInvoiceEventLog log = new ImpInvoiceEventLog();
        log.setOpType("/order/test");
        log.setRequestParam("test");
        wmDispatchService.addImpInvoiceEventLog(log);

       /*   log = new ImpInvoiceEventLog();
        log.setOpType("/order/test");
        log.setRequestParam("test");
        log.setOpStartTime(fromDate);
        log.setReturnData("testtttt");

        wmDispatchService.addImpInvoiceEventLog(log );*/

    }




    @Test
    public void exitsOpsInventoryMoveListByInvoiceIdTest() throws OpsException, InterruptedException {


//        boolean result = baseInventoryService.exitsOpsInventoryMoveListByInvoiceId(7325L);
//        System.out.println(result);
    }





    @Test
    public void checkCreditTest() {
        String orderid = "HY12191";
        String item = "1";


    }

    @Test
    public void AddOpsRoBarcodeBatchTest() throws OpsException {
        List<OpsRoBarcode> list = new ArrayList<>();
        for (int i = 1; i <= 144; i++) {
            OpsRoBarcode barcode = new OpsRoBarcode();
            barcode.setPackageCode("1-1");
            barcode.setCreTime(new Date());
            barcode.setSplititemno(0);
            barcode.setItemno(1);
            barcode.setOrderno("R011");
            barcode.setBarcode("10000R011");
            barcode.setCreator("wsf");
            barcode.setWarehouseCode("KGZ");
            barcode.setInvoiceno("KGZ111");
            barcode.setModelno("KGZ111");
            barcode.setDelflag(0);
            barcode.setQty(11);
            barcode.setRoId("RO1111");
            barcode.setSn("SN");
            list.add(barcode);
        }
        opsRoBarcodeService.insertBatchBarcode(list);
//        OpsRoBarcodeBatchDao.insertRoBarcodeBatch(list);

    }

    @Test
    public void exitsCustmerWldateByCustomerNoTest() throws OpsException, InterruptedException, ParseException {

        String beginTime = "2022-07-13";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        OpsDo opsDo = baseDoService.getDoByDoId("DO11ABUHB033000000H");
        System.out.println("=>" + opsDo.getWlDate().compareTo(format.parse(beginTime)));


        opsDo = baseDoService.getDoByDoId("DOH048KAH016000000H");
        System.out.println("=>" + opsDo.getWlDate().compareTo(format.parse(beginTime)));

        opsDo = baseDoService.getDoByDoId("DO4650420003000000H");
        System.out.println("=>" + opsDo.getWlDate().compareTo(format.parse(beginTime)));

    }


    @Test
    public void insertBatchBarcodeTest() throws OpsException {
        List<OpsRoBarcode> barcodes = new ArrayList<>();
//        int maxCount=10000+new Random().nextInt(200000);
        int maxCount = 1;
        for (int i = 0; i < maxCount; i++) {
            OpsRoBarcode barcode = new OpsRoBarcode();
            barcode.setQty(1);
            barcode.setRoId("DO4650420003000000H=>" + i);
            barcode.setSn("S");
            barcode.setModelno("DO4650420003000000H" + i);
            barcode.setInvoiceno("DO4650420=>" + maxCount);
            barcode.setWarehouseCode("KGZ");
            barcode.setCreator("b2888855");
            barcode.setRoItem(1);
            barcode.setOrderno("DO4650420003000000H");
            barcode.setItemno(1);
            barcode.setDelflag(0);
            barcode.setBarcode(String.valueOf(i));
            barcode.setPackageCode("CASE001");
            barcode.setModifier("b2888855");
            barcode.setCreTime(new Date());
            barcode.setModifyTime(new Date());
//            barcode.setWeight(new BigDecimal("12.112"));
            barcodes.add(barcode);
        }
        opsRoBarcodeService.insertBatchBarcode(barcodes);
    }


    @Test
    public void updateOpsRoBarcodeScanTest() throws OpsException {
        UserDto userDto = new UserDto();
        userDto.setUserName("wms");
//        baseRoService.updateOpsRoBarcodeScan(394243L, "B28029");
    }

    @Test
    public void listDlvCustomersTest() throws OpsException {
        List<String> res = wmDispatchService.listDlvCustomers();
        System.out.println(res);
    }


    @Test
    public void insertRoConfirmLogBatchTest() throws OpsException {
        List<OpsRoConfirmLog> roBarcodes = new ArrayList<>();
        String invoiceNo = "YS001";
        for (int i = 1; i < 5678; i++) {
            OpsRoConfirmLog log = new OpsRoConfirmLog();
            log.setInvoiceNo(invoiceNo);
            log.setRoId("DBRVT00000147002000000");
            log.setReceiveType("INYY");
            log.setCreator("B28029");
            log.setCreTime(new Date());
            roBarcodes.add(log);
        }
        roConfirmLogService.insertBatchConfirmLog(roBarcodes);

    }




    @Test
    public void findRoBarCodeToWmsTest() throws OpsException {

//        List<OpsRoBarcode> barcodes = opsRoService.findRoBarCodeToWms("RO1013499163FZ005000");
//        OpsRoBarcode opsRoBarcode = barcodes.get(0);
//        if (Objects.isNull(opsRoBarcode.getWeight())) {
//            opsRoBarcode.setWeight(BigDecimal.ZERO);
//        }
//        String grossWeight = opsRoBarcode.getWeight().toString();//重量 20221013
//        System.out.println(barcodes);
//        System.out.println(grossWeight);
    }



    @Test
    public void getRoPostByAsnIdTest() throws OpsException {
        OpsRoPost opsRoPost = opsRoPostService.getRoPostByAsnId("RONX1804-6210020802023003", "ASN220919000802");
        if (null != opsRoPost) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }

    @Test
    public void handOpsRoQtyAdjustTest() throws OpsException {
        wmDispatchService.handOpsRoQtyAdjust(1);

    }


    @Test
    public void searchOpsWmOrderTaskByConditionFiveTest() throws OpsException {

        //1.getTask
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit("100");
        condition.setWmOrderType(WmOrderTaskEnum.RO.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(0);//默认初始化0
        CommonResult<List<OpsWmOrderTask>> commonResult = null;
        List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionFive(condition);

        System.out.println(result);


    }

    @Test
    public void wmServiceTest() {
        ResultVo<OpsWarehouse> opsWarehouse = wmCommonService.getWarehouseByCode("KGZ");
        ResultVo<String> warehouseType = wmCommonService.getWarehouseTypeByCode("KGZ");
        System.out.println(JSONObject.toJSONString(opsWarehouse));
        System.out.println(JSONObject.toJSONString(warehouseType));


    }

    @Test
    public void getOpsInventoryMoveListByInvoiceTest() {
//        List<OpsInventoryMove> list=  baseInventoryService.getOpsInventoryMoveListByInvoice("YGZ269",22087792,"KGZ");
//        System.out.println(JSONObject.toJSONString(list));

    }

    @Resource
    private OpsInventoryDao opsInventoryDao;
    @Test
    public void updateOptStatusInventoryMoveConfirmTest() {

         opsInventoryDao.updateOptStatusInventoryMoveConfirm("1121",230100421L ,5);
    }



}

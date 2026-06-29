package com.sales.ops.api.service.impl.wms;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.sales.ops.api.conf.opsApiConfig;
import com.sales.ops.api.dto.wms.*;
import com.sales.ops.api.service.wms.OpsPostApiToWmsService;
import com.sales.ops.api.vo.wms.WmsResultVO;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.OkHttpUtil;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.db.extdao.OpsPostWmsBomDao;
import com.sales.ops.db.extdao.TempWmsApiDao;
import com.sales.ops.db.extdao.WmOrderTaskDao;
import com.sales.ops.dto.flux.*;
import com.sales.ops.dto.inventory.InventoryDTO;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.model.csstock.CsExpdetailVO;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交数据到wms
 * @date ：Created in 2021/10/27 16:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OpsPostApiToWmsServiceImpl implements OpsPostApiToWmsService {

    @Autowired
    private opsApiConfig opsApiConfig;
    @Autowired
    private OkHttpUtil okHttpUtil;
    @Autowired
    private OpsPostWmsBomDao opsPostWmsBomDao;
    @Autowired
    private ProductBomDetailMapper productBomDetailMapper;
    @Autowired
    private ProductBomMapper productBomMapper;
    @Autowired
    private OPSProductFeignApi opsProductFeignApi;
    @Autowired
    private OpsRoMapper opsRoMapper;
    @Autowired
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;
    @Autowired
    private OpsDoMapper opsDoMapper;
    @Autowired
    private OpsPcoMapper opsPcoMapper;
    @Autowired
    private OpsWmFeignApi opsWmFeignApi;

    @Autowired
    private OpsCustomerMapper opsCustomerMapper;



    @Autowired
    private OPSWarehouseDao opsWarehouseDao;

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;

    @Resource
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;

    @Resource
    private OpsInventoryMoveMapper opsInventoryMoveMapper;

    @Resource
    private OpsInventoryMapper opsInventoryMapper;
    @Autowired
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;

    @Resource
    private OpsDoItemMapper opsDoItemMapper;


    @Autowired
    private TempWmsApiDao tempWmsApiDao;

    @Autowired
    private WmOrderTaskDao wmOrderTaskDao;

    /**
     * bugid:12187 c14717 下发委托指令
     * @param param
     * @param opsWarehouse
     * @return
     */
    @Override
    public CommonResult<String> postWTdo(OpsDoAndItemDto param, OpsWarehouse opsWarehouse){
        Rcvdetail rcvdetail = null;
        if(DoTypeEnum.TKCK.getType().equals(param.getOpsDo().getDoType())){
            return CommonResult.success("委托在库退货不写入csExpdetailVO","");
        }else{
            rcvdetail = getRcvdetail(param.getOpsDo().getOrderId(),param.getOpsDo().getOrderItem());
            if(Objects.isNull(rcvdetail)){
                CommonResult.failure("无RcvDetail");
            }
        }
        try {
            //获取委托在库库存属性
            List<OpsWTInventoryDTO> listDto = new ArrayList<OpsWTInventoryDTO>();
            collectOpsWTInventoryDTO(param,listDto);
            //委托在库调用接口
            List<CsExpdetailVO> list = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(listDto)){
                //实体类赋值
                collectCsExpDetaiVo(listDto,rcvdetail,param,opsWarehouse,list);
            }
            if(CollectionUtils.isEmpty(list)){
                return CommonResult.failure("无关联库存");
            }
            Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));
            if(Objects.nonNull(flag) && flag){
                return CommonResult.success("委托在库成功","");
            }else {
                return CommonResult.failure("存表失败");
            }
        } catch (Exception wmsResult) {
            if (wmsResult.getMessage().length() > 200) {
                return CommonResult.failure(wmsResult.getMessage().substring(0, 200));
            } else {
                return CommonResult.failure(wmsResult.getMessage());
            }
        }
    }

    //bugid:12187 c14717 下发委托指令
    public Rcvdetail getRcvdetail(String orderId,String orderItem){
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(orderId).andRorderItemEqualTo(Integer.valueOf(orderItem));
        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
        if (rcvdetailList.size() == 1) {
            return rcvdetailList.get(0);
        } else {
            return null;
        }
    }
    //bugid:12187 c14717 下发委托指令
    public void collectOpsWTInventoryDTO(OpsDoAndItemDto param,List<OpsWTInventoryDTO> listDto){
        OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
        OpsDoItemInventoryExample.Criteria criteria = exampleDoItemInventory.createCriteria()
                .andDoIdEqualTo(param.getOpsDo().getDoId()).andDelflagEqualTo(0);
        List<OpsDoItemInventory> doItemInvList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
        for (OpsDoItemInventory itemInventory : doItemInvList) {
            InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
            InventoryDTO dto = null;
            if (InventoryTableTypeEnum.NORMAL.equals(tableTypeEnum)) {
                OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                if (ObjectUtil.isNotNull(inventory)) {
                    dto =  new InventoryDTO(inventory);
                }
            }
            if (InventoryTableTypeEnum.TRANS.equals(tableTypeEnum)) {
                OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                if (ObjectUtil.isNotNull(inventory)) {
                    dto =  new InventoryDTO(inventory);
                }
            }
            OpsInventoryProperty property = opsInventoryPropertyMapper.selectByPrimaryKey(dto.getInventoryPropertyId());
            OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
            obj.setInventoryId(itemInventory.getInventoryId());
            obj.setInventoryType(property.getInventoryTypeCode());
            listDto.add(obj);
        }
    }
    //bugid:12187 c14717 下发委托指令
    public void collectCsExpDetaiVo(List<OpsWTInventoryDTO> listDto,Rcvdetail rcvdetail,OpsDoAndItemDto param,OpsWarehouse opsWarehouse,List<CsExpdetailVO> list){
        for(OpsWTInventoryDTO opsWTInventoryDTO : listDto){
            CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
            csExpdetailVO.setExpOrderNo(param.getOpsDo().getOrderId()+"-"+param.getOpsDo().getOrderItem()+"-"+param.getOpsDo().getNum());
            csExpdetailVO.setModelNo(param.getList().get(0).getModelno());
            csExpdetailVO.setExpQty(param.getList().get(0).getQty());
            csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
            csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
            csExpdetailVO.setUserNo(param.getOpsDo().getUserNo());
            csExpdetailVO.setCorderNo(param.getOpsDo().getCorderNo());
            csExpdetailVO.setPrice(param.getList().get(0).getPrice());
            if(Objects.nonNull(rcvdetail)){
                csExpdetailVO.setPplNo(rcvdetail.getPplNo());
                csExpdetailVO.setProjectNo(rcvdetail.getProjectNo());
                csExpdetailVO.setPrice(rcvdetail.getPrice());
                if(Objects.nonNull(rcvdetail.getPriceEnduser())){
                    csExpdetailVO.setPriceEnduser(rcvdetail.getPriceEnduser().toString());
                }
            }else{
                csExpdetailVO.setStatus(2);
            }
            csExpdetailVO.setCproductNo(param.getList().get(0).getCproductNo());
            csExpdetailVO.setInventoryId(opsWTInventoryDTO.getInventoryId());
            csExpdetailVO.setInventoryTypeCode(opsWTInventoryDTO.getInventoryType());
            csExpdetailVO.setDoId(param.getOpsDo().getDoId());
            if(  DoSourceEnum.ASSModelNo.getType().equals( param.getOpsDo().getDoSource())){
                csExpdetailVO.setItemType(2);
            }else {
                csExpdetailVO.setItemType(0);
            }
            list.add(csExpdetailVO);
        }
    }


    @Override
    public CommonResult<String> checkTempWmsApi(List<FinishOrderWmsReqDto> params){
        List<FinishOrderWmsResDto> resList = new ArrayList<FinishOrderWmsResDto>();
        for(FinishOrderWmsReqDto obj : params){
            FinishOrderWmsResDto res = new FinishOrderWmsResDto();
            res.setDocNo(obj.getDocNo());
            res.setFinishQty(obj.getFinishQty());
            Integer tempWmsApiQty = tempWmsApiDao.getTempWmsApi(obj.getDocNo());
            if(Objects.nonNull(tempWmsApiQty)){
                if(!tempWmsApiQty.equals(obj.getFinishQty())){
                    return CommonResult.failure("完纳失败，数量不匹配") ;
                }
                res.setFinishQty(tempWmsApiQty);
            }

            resList.add(res);
        }
        return CommonResult.success(JSON.toJSONString(resList)) ;
    }


    /**
     * //完纳问询 bugid:11758 20230814 c14717
     * @param params
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> askFinishDo(List<FinishOrderWmsReqDto> params) throws OpsException {
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray header = JSONArray.parseArray(JSON.toJSONString(params));
        data.put("header", header);
        obj.put("data", data);
        return CommonResult.success(obj);
    }

    /**
     * //完纳执行 bugid:11758 20230814 c14717
     * @param obj
     * @param url
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> opsToWmsReData(JSONObject obj, String url) throws OpsException {
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        try {
            WmsResultVO resultVO = JSON.parseObject(result, WmsResultVO.class);
            if(resultVO == null || !resultVO.isSuccess() ){
                return CommonResult.failure(result + "");
            }
            //数据验证
            if(CollectionUtils.isEmpty(resultVO.getResponse().getResult().getResultInfo())){
                return CommonResult.failure("完纳无返回数据");
            }
            String resultInfoList =  JSON.toJSONString(resultVO.getResponse().getResult().getResultInfo());
            return CommonResult.success("返回成功", resultInfoList);
        } catch (Exception e) {
            log.error("调用wms失败",e);
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }

    }

    @Override
    public CommonResult<String> opsToWms(JSONObject obj, String url) throws OpsException {
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success("上传成功", null);
        } else {
            return CommonResult.failure(result + "");
        }
    }

    /**
     * 更新bom下发状态
     *
     * @param type
     * @param param
     * @param flag
     * @param msg
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateOpsBomStatus(String type, String param, boolean flag, String msg) throws OpsException {
        if (flag) {
            ProductBom bom = new ProductBom();
            bom.setBomid(Long.parseLong(param));
            bom.setIsWms(1);//已经上传成功
            productBomMapper.updateByPrimaryKeySelective(bom);
            return CommonResult.success("上传成功", bom.getBomid() + "");
        } else {
            ProductBom bom = new ProductBom();
            bom.setBomid(Long.parseLong(param));
            bom.setIsWms(2);//上传失败
            productBomMapper.updateByPrimaryKeySelective(bom);
            return CommonResult.failure(msg);
        }
    }

    public CommonResult<String> updateOpsWmOrderTaskStatus(Long wmOrderTaskId, String wmOrderType, String taskType, String wmOrderId, String msg) throws OpsException {

        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setId(wmOrderTaskId);
        record.setFlag(2);//失败
        record.setModifyTime(new Date());
        record.setModifier("wms");
        record.setMsg(msg);//报错信息不能过长
        opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);
        return CommonResult.success("更改状态成功");
    }

    /**
     * 更新RO 下发状态
     *
     * @param type
     * @param
     * @param flag
     * @param msg
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateOpsROStatus(Long wmOrderTaskId, String type, String roTableId, String roId, boolean flag, String msg) throws OpsException {
        if (flag) {

            OpsWmOrderTask record = new OpsWmOrderTask();
            record.setId(wmOrderTaskId);
            record.setFlag(1);
            record.setModifyTime(new Date());
            record.setModifier("wms");
            opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);

            //更新order_task barcode状态
            OpsWmOrderTaskExample exaBarcode = new OpsWmOrderTaskExample();
            OpsWmOrderTaskExample.Criteria criBarcode = exaBarcode.createCriteria();
            criBarcode.andWmOrderIdEqualTo(roId);
            criBarcode.andDelflagEqualTo(0);
            criBarcode.andFlagEqualTo(3);//将挂起状态设置为 0初始状态

            criBarcode.andWmOrderTypeEqualTo(WmOrderTaskEnum.RO.getType());
            criBarcode.andTaskTypeEqualTo(WmOrderTaskEnum.BARCODE.getType());
            OpsWmOrderTask recordBarCode = new OpsWmOrderTask();
            recordBarCode.setFlag(0);
            recordBarCode.setModifyTime(new Date());
            recordBarCode.setModifier("wms");
            opsWmOrderTaskMapper.updateByExampleSelective(recordBarCode, exaBarcode);
            return CommonResult.success("下发成功");

        } else {
            // bugid:14060 c14717 20240425 下发失败不更新ro
            //更新状态
            OpsWmOrderTask record = new OpsWmOrderTask();
            record.setId(wmOrderTaskId);
            record.setFlag(2);//失败
            record.setModifyTime(new Date());
            record.setModifier("wms");
            record.setMsg(msg);//报错信息不能过长
            opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);

            return CommonResult.failure(record.getMsg());
        }
    }

    /**
     * 2023-01-11
     * 更新RO 下发状态 (委托在库)
     * 不实际下发RO到WMS。只更新RO表IsWms=1
     * 更新order_task flag=0
     *
     * @param msg
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateOpsROStatusToWT(Long roTableId, String roId, String msg) throws OpsException {
                //更新order_task by ro_id
        OpsWmOrderTaskExample exaRO = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria criRo = exaRO.createCriteria();
        criRo.andWmOrderIdEqualTo(roId);
        criRo.andDelflagEqualTo(0);
        criRo.andWmOrderTypeEqualTo(WmOrderTaskEnum.RO.getType());

        OpsWmOrderTask recordRo = new OpsWmOrderTask();
        recordRo.setFlag(1);
        recordRo.setModifyTime(new Date());
        recordRo.setModifier("wms");
        recordRo.setMsg(msg);
        opsWmOrderTaskMapper.updateByExampleSelective(recordRo, exaRO);
        return CommonResult.success("下发成功");
    }


    /**
     * BarCode
     *
     * @param type
     * @param param
     * @param flag
     * @param msg
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateOpsBARCODEStatus(Long wmOrderTaskId, String type, String param, boolean flag, String msg) throws OpsException {
        if (flag) {
            //更新order_task barcode状态

            OpsWmOrderTask recordBarCode = new OpsWmOrderTask();
            recordBarCode.setId(wmOrderTaskId);
            recordBarCode.setFlag(1);
            recordBarCode.setModifyTime(new Date());
            recordBarCode.setModifier("wms");
            opsWmOrderTaskMapper.updateByPrimaryKeySelective(recordBarCode);

            OpsRoExample e = new OpsRoExample();
            e.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(param);//param传roId
            //跟新RO 状态
            OpsRo up = new OpsRo();
            up.setIsWms(1);//更新成功
            up.setModifyTime(new Date());
            opsRoMapper.updateByExampleSelective(up,e);
            return CommonResult.success("barcode下发成功");
        } else {
            //更新状态
            OpsWmOrderTask record = new OpsWmOrderTask();
            record.setId(wmOrderTaskId);
            record.setFlag(2);//失败
            record.setModifyTime(new Date());
            record.setModifier("wms");
            record.setMsg(msg);//报错信息不能过长
            opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);
            return CommonResult.failure(record.getMsg());
        }
    }

    /**
     * 更新DO 下发状态
     *
     * @param type
     * @param
     * @param flag
     * @param msg
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateOpsDOStatus(Long wmOrderTaskId, String type, String doTableId, String doId, boolean flag, String msg) throws OpsException {
        if (flag) {
            //更新新DO状态
            updateDoIsWms(doTableId);
            //更新task
            updateTask(wmOrderTaskId,msg,1);
            //创建下发事件
            opsWmFeignApi.prepareOperation(doId);
            return CommonResult.success("下发wms成功");
        } else {
            // 关键字重试 bugid:13892 指令下发失败重试信息表 验证表中信息包含在返回错误信息
            boolean exist = checkErrorMsg(msg);
            if(exist){
                //验证表中信息包含在返回错误信息中，则直接返回信息，不更新task
                return CommonResult.failure(msg);
            }
            //更新状态
            updateTask(wmOrderTaskId,msg,2);
            return CommonResult.failure(msg);
        }
    }
    // 更新新DO状态
    public void updateDoIsWms(String id){
        //跟新DO状态
        OpsDo up = new OpsDo();
        up.setId(Long.parseLong(id));
        up.setIsWms(1);//更新成功
        up.setModifyTime(DateUtil.getNow());
        //bugid 13785 下发记录下发时间 20230325 下发时间
        up.setWmsTime(DateUtil.getNow());
        opsDoMapper.updateByPrimaryKeySelective(up);
    }
    // 更新pco状态
    public void updatePcoIsWms(String pcoTableId){
        //跟新PCO状态
        OpsPco up = new OpsPco();
        up.setId(Long.parseLong(pcoTableId));
        up.setIsWms(1);//更新成功
        up.setModifyTime(DateUtil.getNow());
        opsPcoMapper.updateByPrimaryKeySelective(up);
    }
    // 更新task状态
    public void updateTask(Long wmOrderTaskId, String msg ,Integer flag){
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setId(wmOrderTaskId);
        record.setMsg(msg);
        record.setFlag(flag);
        record.setModifyTime(DateUtil.getNow());
        record.setModifier("wms");
        //bugid:12294 c14717 20231019
        if(flag == 1){
            record.setDelflag(1);
            record.setSentSuccessTime(DateUtil.getNow());
        }
        opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 更新PCO 下发状态
     *
     * @param type
     * @param
     * @param flag
     * @param msg
     * @return
     * @throws OpsException
     */
    public CommonResult<String> updateOpsPCOStatus(Long wmOrderTaskId, String type, String pcoTableId, String pcoId, boolean flag, String msg) throws OpsException {
        if (flag) {
            //跟新PCO状态
            updatePcoIsWms(pcoTableId);
            //更新order_task order状态
            updateTask(wmOrderTaskId,msg,1);
            return CommonResult.success("下发成功");
        } else {
            // bugid:13892 指令下发失败重试信息表 验证表中信息包含在返回错误信息
            boolean exist = checkErrorMsg(msg);
            if(exist){
                //验证表中信息包含在返回错误信息中，则直接返回信息，不更新task
                return CommonResult.failure(msg);
            }
            //更新状态
            updateTask(wmOrderTaskId,msg,2);
            return CommonResult.failure(msg);
        }
    }

    /**
     * bugid:13892 指令下发失败重试信息表
     * @param msg
     * @return 如果包含重试信息 返回true 不更新task
     */
    @Override
    public boolean checkErrorMsg(String msg){
        if(StringUtils.isNotBlank(msg)){
            List<String> wmsReturnErrorRepeatMsgList = wmOrderTaskDao.getWMSReturnErrorRepeatMsg();
            if(CollectionUtils.isNotEmpty(wmsReturnErrorRepeatMsgList)){
                for(String msgKey : wmsReturnErrorRepeatMsgList){
                    if(msg.contains(msgKey)){
                        return true;
                    }
                }
            }
        }
        return false;
    }



    /**
     * ops提交bom和bomDetail
     * 4.2 BOM组件资料下发
     *
     * @param itemBom
     * @return
     */
    @Override
    public CommonResult<JSONObject> updateBomToWms(ProductBom itemBom) throws OpsException {
        CommonResult<Product> resultProduct = opsProductFeignApi.searchProduct(itemBom.getModelno());
        if (resultProduct.isSuccess()) {
            BasKitHeaderDto h = new BasKitHeaderDto();
            h.setCustomerId("SMC");
            h.setKitSku(itemBom.getModelno());
            h.setVersionNo(itemBom.getBomid() + "");
            //h.setKitSkuDescr1(resultProduct.getData().getChinesename());
            h.setKitSkuDescr1("test");
            h.setKitSkuDescr2("eName");
            h.setPackId("STANDARD");
            h.setActiveFlag("Y");
            //h.setPackMaterialFlag("默认为空");
            CommonResult<List<ProductBomDetail>> productBomDetailList = opsProductFeignApi.searchProductBomDetail(itemBom.getBomid() + "");
            if (productBomDetailList.isSuccess()) {
                List<BasKitDetailsDto> basKitDetailsDtoList = new ArrayList<BasKitDetailsDto>();
                for (ProductBomDetail itemDetail : productBomDetailList.getData()) {
                    CommonResult<Product> resultProductSub = opsProductFeignApi.searchProduct(itemBom.getModelno());
                    if (resultProductSub.isSuccess()) {
                        BasKitDetailsDto d = new BasKitDetailsDto();
                        d.setCustomerId("SMC");
                        d.setKitSku(itemBom.getModelno()); //成品
                        d.setSku(itemDetail.getModelno());//子型号
                        d.setPackId("STANDARD");
                        d.setVersionNo(itemDetail.getBomid() + "");
                        //d.setSkuDescr1(resultProductSub.getData().getChinesename());
                        d.setSkuDescr1("chineseName");
                        d.setSkuDescr2("eName");
                        d.setQty(itemDetail.getQuantity() + "");
                        d.setAccessoryFlag("N");
                        basKitDetailsDtoList.add(d);
                    }
                }
                h.setDetails(basKitDetailsDtoList);
                //拼接格式化报文
                JSONObject obj = new JSONObject();
                JSONObject data = new JSONObject();
                JSONArray header = new JSONArray();
                header.add(h);
                data.put("header", header);
                obj.put("data", data);
                return CommonResult.success(obj);
            }
        }
        return CommonResult.failure("ops无此型号");
    }

    /**
     * ops提交bom和bomDetail
     * 4.2 BOM组件资料下发  实时调用
     *
     * @param
     * @return
     */
    @Override
    public JSONObject updateBomToWms(String bomId, String modelNo) throws OpsException {
        BasKitHeaderDto h = new BasKitHeaderDto();
        h.setCustomerId("SMC");
        h.setKitSku(modelNo);
        h.setVersionNo(bomId);
        //h.setKitSkuDescr1(resultProduct.getData().getChinesename());
        h.setKitSkuDescr1("test");
        h.setKitSkuDescr2("eName");
        h.setPackId("STANDARD");
        h.setActiveFlag("Y");
        //h.setPackMaterialFlag("默认为空");
        CommonResult<List<ProductBomDetail>> productBomDetailList = opsProductFeignApi.searchProductBomDetail(bomId);
        if (productBomDetailList.isSuccess()) {
            List<BasKitDetailsDto> basKitDetailsDtoList = new ArrayList<BasKitDetailsDto>();
            for (ProductBomDetail itemDetail : productBomDetailList.getData()) {
                BasKitDetailsDto d = new BasKitDetailsDto();
                d.setCustomerId("SMC");
                d.setKitSku(modelNo);
                d.setSku(itemDetail.getModelno());
                d.setPackId("STANDARD");
                d.setVersionNo(itemDetail.getBomid() + "");
                //d.setSkuDescr1(resultProductSub.getData().getChinesename());
                d.setSkuDescr1("chineseName");
                d.setSkuDescr2("eName");
                d.setQty(itemDetail.getQuantity() + "");
                d.setAccessoryFlag("N");
                basKitDetailsDtoList.add(d);

            }
            h.setDetails(basKitDetailsDtoList);
            //拼接格式化报文
            JSONObject data = new JSONObject();
            JSONArray header = new JSONArray();
            header.add(h);
            data.put("header", header);

            return data;
        }
        return null;
    }

    /**
     * 4.3. 预期到货通知单（采购入库/仓间调拨/退货入库/盘点差异调账）
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updateRoToWms(OpsRoAddItemDto param) throws OpsException {
        //获取参数
        OpsRo opsRo = param.getRo();
        List<OpsRoItem> list = param.getList();
        // bugid 10024 C12961 2023-03-28
        // 无论调拨自提还是采购调拨，都不应该把入库单据无发票号推送到WMS。这种属于无实际价值意义的脏数据。
        if (StringUtils.isBlank(opsRo.getInvoiceNo())) {
            throw Exceptions.OpsException("发票号为空");
        }
        if (opsRo == null || CollectionUtils.isEmpty(list)) {
            return CommonResult.failure("ro或roItem为空");
        }
        DocAsnHeaderDto header = new DocAsnHeaderDto();
        header.setWarehouseId(opsRo.getWarehouseCode());//仓库id
        header.setCustomerId("SMC"); //货主id
        if (RoTypeEnum.getType(opsRo.getRoType()) != null) {
            header.setAsnType(RoTypeEnum.getType(opsRo.getRoType()).getWltype());
        } else {
            return CommonResult.failure("wm-ro 订单类型不匹配");
        }
        header.setDocNo(opsRo.getRoId());
        header.setAsnReferenceA(opsRo.getOrderId());//七位单号
        if (RoTypeEnum.DBRK.getType().equals(opsRo.getRoType())) {
            header.setAsnReferenceB(opsRo.getInvoiceNo());//todo 需要传承运商主单
        } else {
            header.setAsnReferenceB(opsRo.getInvoiceNo());
        }
        header.setAsnReferenceC("待定");
        header.setAsnReferenceD(opsRo.getOrderId() + "-" + opsRo.getOrderItem() + "-" + opsRo.getNum());//13位
        header.setAsnCreationTime(opsRo.getCreTime());
        //header.setExpectedArriveTime1();待定
        header.setSupplierId(opsRo.getSupplierId());
        header.setCarrierId(opsRo.getCarried());
        header.setCrossdockFlag("0");//需要判定：越库标记,默认0
        //begin bug:9385 增加发票号ID的使用 B28029 2023-02-03
        header.setHedi05(String.valueOf(opsRo.getInvoiceId()));//发票ID
        //end bug:9385
        List<DocAsnDetailsDto> details = new ArrayList<>();

        for (OpsRoItem item : list) {
            DocAsnDetailsDto detail = new DocAsnDetailsDto();
            detail.setReferenceNo(item.getRoId());
            detail.setLineNo(item.getRoItem() + "");
            detail.setSku(item.getModelno());
            detail.setExpectedQty(item.getQty() + "");
            detail.setLotAtt06(item.getGreenCode());
            detail.setLotAtt07("");
            detail.setLotAtt08("OK");//ops保存值0良品  1不良品   2 未检品  wms:OK=良品 NO=不良品
            detail.setLotAtt09(opsRo.getUserNo());
            detail.setDedi04("");
            detail.setTotalNetWeight("0");//默认0
            if (Objects.nonNull(item.getNetweight())) {
                detail.setTotalNetWeight(item.getNetweight().toString());//单个型号净重
            }
            details.add(detail);
        }
        header.setDetails(details);
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headerJson = new JSONArray();
        headerJson.add(header);
        data.put("header", headerJson);
        obj.put("data", data);
        return CommonResult.success(obj);

    }

    /**
     * 4.4.	入库对应箱号/序列号下发
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updateRoBarCodeToWms(List<OpsRoBarcode> param) throws OpsException {
        if (CollectionUtils.isEmpty(param)) {
            return CommonResult.failure("barcode 为空");
        }
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();
        for (OpsRoBarcode opsRoBarcode : param) {
            //适配数据
            DocAsnSerialNoHeaderDto header = new DocAsnSerialNoHeaderDto();
            DocAsnSubserialNoDetailsDto detail = new DocAsnSubserialNoDetailsDto();
            List<DocAsnSubserialNoDetailsDto> list = new ArrayList<>();
            header.setWarehouseId(opsRoBarcode.getWarehouseCode());
            header.setCustomerId("SMC");
            header.setDocNo(opsRoBarcode.getRoId());
            header.setUdf01(String.valueOf(opsRoBarcode.getRoItem()));//item
            header.setSerialNo(opsRoBarcode.getBarcode());
            header.setSku(opsRoBarcode.getModelno());
            header.setSecondSerialNo(opsRoBarcode.getPackageCode());//托号富勒要求唯一 所以托号加发票号
            header.setQty(opsRoBarcode.getQty());
            detail.setWarehouseId(opsRoBarcode.getWarehouseCode());
            detail.setAsnNo(opsRoBarcode.getRoId());
            detail.setSerialNo(opsRoBarcode.getBarcode());
            if (StringUtils.isNotBlank(opsRoBarcode.getSn())) {//wms是主键
                detail.setSubSerialNo(opsRoBarcode.getSn());
            } else {
                detail.setSubSerialNo(opsRoBarcode.getBarcode());
            }
            detail.setCustomerId("SMC");
            list.add(detail);
            header.setDetails(list);
            headers.add(header);
        }
        data.put("header", headers);
        obj.put("data", data);
        return CommonResult.success(obj);

    }

    /**
     * 4.6.	销售/发运订单/盘点差异调账下发
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updateDoToWms(OpsDoAndItemDto param) throws OpsException {
        //获取参数
        OpsDo opsDo = param.getOpsDo();
        List<OpsDoItem> list = param.getList();
        if (opsDo == null || CollectionUtils.isEmpty(list)) {
            return CommonResult.failure("Do或DoItem为空");
        }
        DocOrderHeaderDto header = new DocOrderHeaderDto();
        header.setCustomerId("SMC"); //货主id
        if (DoTypeEnum.getType(opsDo.getDoType()) != null) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                header.setWarehouseId(opsDo.getGatherWarehouseCode());//仓库id
                header.setConsigneeId(opsDo.getCustomerNo());
                if (StringUtils.isNotBlank(opsDo.getCustomerNo())) {
                    // bugid:20750  暂不提交代码 商城
                    if(Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                        header.setConsigneeName(opsDo.getCstmname());
                    }else {
                        OpsCustomer opsCustomer = opsCustomerMapper.selectByPrimaryKey(opsDo.getCustomerNo());
                        if (Objects.nonNull(opsCustomer)) {
                            header.setConsigneeName(opsCustomer.getName());//公司名称
                        } else {
                            return CommonResult.failure("客户表无数据" + opsDo.getCustomerNo());
                        }
                    }
                } else {
                    header.setConsigneeName("无数据2");//公司名称
                }
                if (!DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                    RcvdetailExample example = new RcvdetailExample();
                    example.createCriteria().andRorderNoEqualTo(opsDo.getOrderId()).andRorderItemEqualTo(Integer.valueOf(opsDo.getOrderItem()));
                    List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(example);

                    if(CollectionUtils.isNotEmpty(rcvdetails)){
                        Rcvdetail rcvdetail = rcvdetails.get(0);
                        if(StringUtils.isBlank(rcvdetail.getStockType())) {
                            header.setUserDefine2(OrderStockTypeEnum.ZK.getDesc());
                            header.setUserDefine3(opsDo.getWarehouseCode());
                        }else {
                            header.setUserDefine2(OrderStockTypeEnum.getDescFromType(rcvdetail.getStockType()));
                            header.setUserDefine3(rcvdetail.getStockCode());
                        }
                    }
                }
            } else {
                header.setConsigneeId(opsDo.getGatherWarehouseCode());//目的仓
                OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(opsDo.getGatherWarehouseCode());
                header.setConsigneeName(opsWarehouse.getWarehouseName());//目的仓库名称
                header.setWarehouseId(opsDo.getWarehouseCode());//仓库id
            }
            header.setOrderType(DoTypeEnum.getType(opsDo.getDoType()).getWltype());
            // bugid:20750  暂不提交代码 商城 商城 DS  指定顺丰
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())
                    && Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                header.setOrderType("DS");
                header.setCarrierId(opsDo.getCarried());
            }
        } else {
            return CommonResult.failure("wm-do 订单类型不匹配getDoType：" + opsDo.getDoType());
        }
        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                header.setHedi15("KT");
            }
        }
        // bugid:18126 c14717 20250703
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            header.setHedi18("Y");
        }

        //header.sethedi 15
        header.setDocNo(opsDo.getDoId());
        header.setCrossdockFlag("N"); // 越库标记  Y越库
        //读取表 应对与订单修改场景
        if (Objects.nonNull(opsDo.getRoCrossType()) && opsDo.getRoCrossType() == 2) {//0 上货架 1上预约 2越库
            header.setSoReferenceA(opsDo.getRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                header.setHedi15("KTYK");
            } else {
                header.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
            }
        }
        //外部到货实时调用
        if (StringUtils.isNotBlank(param.getYuKuRoId())) {
            header.setSoReferenceA(param.getYuKuRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                header.setHedi15("KTYK");
            } else {
                header.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
            }
        }
        header.setSoReferenceB(opsDo.getOrderId());
        header.setSoReferenceC(opsDo.getOrderId() + "-" + opsDo.getOrderItem());
        header.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getNum()); //13位
        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
            if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                header.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getAssNum()); //13位
            }
        }
        if (null == opsDo.getSpceialFlag()) {
            header.setPriority("2");//1为特发订单 2 为普通订单
        } else {
            header.setPriority(opsDo.getSpceialFlag() + "");//1为特发订单 2 为普通订单
        }
        header.setOrderTime(opsDo.getCreTime());
        header.setExpectedShipmentTime1(opsDo.getWlDate());
        header.setRequiredDeliveryTime(opsDo.getHopeDate());
        if (StringUtils.isEmpty(opsDo.getProvince())
                || StringUtils.isEmpty(opsDo.getCity())
                || StringUtils.isEmpty(opsDo.getDistrict())
                || StringUtils.isEmpty(opsDo.getAddress())
                || StringUtils.isEmpty(opsDo.getLinkman())
        ) {
            throw Exceptions.OpsException("此单无发货基础数据");//自提也需要维护发货基础数据
        }

        if (StringUtils.isEmpty(opsDo.getPhone()) && StringUtils.isEmpty(opsDo.getMobile())) {
            throw Exceptions.OpsException("此单无发货基础数据手机号");//自提也需要维护发货基础数据
        }

        header.setConsigneeContact(opsDo.getLinkman());
        header.setConsigneeCountry("86");//代码 86 中国 为什么传代码 TMS 用；
        if (StringUtils.isNotBlank(opsDo.getProvince())) {
            header.setConsigneeProvince(opsDo.getProvince().substring(0, 2));//代码
        }
        if (StringUtils.isNotBlank(opsDo.getCity())) {
            if (opsDo.getCity().length() > 3) {
                String target = opsDo.getCity().substring(0, 4);
                String result = opsDo.getCity().substring(0, 4);
                switch (target) {
                    case "1100":
                        result = "1101";
                        break;
                    case "1200":
                        result = "1201";
                        break;
                    case "3100":
                        result = "3101";
                        break;
                    case "5000":
                        result = "5001";
                        break;
                }
                header.setConsigneeCity(result);//代码
            } else {
                header.setConsigneeCity(opsDo.getCity());//代码
            }
        }

        /*if(StringUtils.isNotBlank(opsDo.getDistrict())){
            header.setConsigneeProvince(opsDo.getDistrict().substring(0,2));//代码
        }
        if(StringUtils.isNotBlank(opsDo.getDistrict())){
            if(opsDo.getDistrict().length()>3){
                header.setConsigneeCity(opsDo.getDistrict().substring(0,4));//代码
            }else {
                header.setConsigneeCity(opsDo.getDistrict());//代码
            }
        }*/
        //header.setConsigneeDistrict(opsDo.getDistrict());//不传区
        header.setConsigneeStreet("");//没有街道字段 超长
        header.setConsigneeAddress1(opsDo.getAddress());//地址
        header.setConsigneeAddress2(opsDo.getAddress());//地址
        header.setConsigneeTel1(opsDo.getMobile());//填写手机号
        header.setConsigneeTel2(opsDo.getPhone());//1111
        if (StringUtils.isNotBlank(opsDo.getPostcode())) {
            String str = "^[1-9]\\d{5}$";
            Pattern p = Pattern.compile(str);
            //Pattern中的matcher()方法传入要匹配的字符串与正则进行匹配i
            Matcher m = p.matcher(opsDo.getPostcode());
            //Matcher类中的matches()方法判断是否匹配成功
            boolean bo = m.matches();
            if (bo) {
                header.setConsigneeZip(opsDo.getPostcode());
            }
        }
        //大田 DTW 不传 bugid:18786 20250827
        /*if (Objects.nonNull(opsDo.getCarried()) && !"NON".equalsIgnoreCase(opsDo.getCarried()) && !"DTW".equalsIgnoreCase(opsDo.getCarried())) {
            header.setCarrierId(opsDo.getCarried());
        }*/
        header.setChannel("*");
        header.setHedi01(opsDo.getNum() + "");//opsDo.getOrderCount();
        if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.SpecPackage)) {
            header.setHedi02(opsDo.getExpLinkNo());//特殊包装要求
        }
        if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.WholeOrderToAssemble) || OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.OneItemToAssemble)) {
            if (Objects.nonNull(opsDo.getExpDlvType()) && Objects.nonNull(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()))) {
                header.setHedi03(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()).specName());
            }
        }
        header.setHedi04(opsDo.getUserNo());//最终客户
        header.setHedi05(opsDo.getPakageType());// 集约方式 分包  01 客户地址 02 代理店 03 客户// 0:按地址集约;1:按订单集约;2:按用户集约
        if (Objects.nonNull(opsDo.getPakageType()) && "1".equals(opsDo.getPakageType())) {
            header.setHedi06("Y");//是否按照七位订单号单出
        } else {
            header.setHedi06("N");//是否按照七位订单号单出
        }
        header.setHedi07(opsDo.getDlvSite());// 1：直发客户，2：直发营业所，3：营业所/客户自提
        if (StringUtils.isNotEmpty(opsDo.getDlvSite()) && "3".equals(opsDo.getDlvSite())) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                header.setPriority("3");//1为特发订单 2 为普通订单 3 自提
                header.setConsigneeAddress1("客户自提");//全地址
                header.setConsigneeAddress2("客户自提");
                // bugid:18786 20250827
                header.setCarrierId(CarrierEnum.ZT.getCode());
            } else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())){
                header.setPriority("2");
                header.setHedi07("1");// 1：直发客户
            }
        }
        header.setHedi12(opsDo.getCorderNo());//客户订单号
        if (DoTypeEnum.ZHCK.getType().equals(opsDo.getDoType())||DoTypeEnum.ZHCKOW.getType().equals(opsDo.getDoType())) {
            header.setConsigneeAddress1("库内组换");//组换单地址写仓内组换
            header.setConsigneeAddress2("库内组换");//组换单地址写仓内组换
            header.setConsigneeName("组换");//组换 //物流已经确认
            header.setHedi12(opsDo.getRemark());//组换特殊标识  物流提出需求== 客户订单号
        }
        header.setHedi08(opsDo.getDeptNo());//归属营业所代码
        header.setHedi11(opsDo.getUserNo());//专备客户
        header.setNotes(opsDo.getRemark());
        List<DocOrderDetailsDto> details = new ArrayList<>();
        for (OpsDoItem item : list) {
            DocOrderDetailsDto detail = new DocOrderDetailsDto();
            detail.setDedi08(opsDo.getRelocation());//下预约字段
            detail.setReferenceNo(item.getDoId());
            detail.setLineNo(item.getDoItem() + "");
            detail.setSku(item.getModelno());
            detail.setDedi05(item.getCproductNo());//getCproductNo
            header.setHedi16(item.getContractNo());//客户合同号
            detail.setQtyOrdered(item.getQty() + "");
            detail.setPrice(item.getPrice());
            detail.setLotAtt05("");//颜色待定
            detail.setLotAtt06(item.getGreenCode());
            detail.setLotAtt07("");//产地
            detail.setLotAtt08("OK");//质量状态
            detail.setLotAtt09("");//专备客户 opsDo.getUserNo()
            detail.setLotAtt10("");//特殊材质待定
            //读取表
            if (Objects.nonNull(opsDo.getRoCrossType()) && (opsDo.getRoCrossType() == 2 || opsDo.getRoCrossType() == 1)) {//0 上货架 1上预约 2越库
                detail.setLotAtt13(opsDo.getDoId());// 越库和上预约都带上此字段
            }
            //外部到货
            if (StringUtils.isNotBlank(param.getYuekuDoId())) {// 越库和上预约都带上此字段
                detail.setLotAtt13(param.getYuekuDoId());
            }
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                detail.setLotAtt13(opsDo.getDoId());
            }
            details.add(detail);
        }
        header.setDetails(details);
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();
        headers.add(header);
        data.put("header", headers);
        obj.put("data", data);
        return CommonResult.success(obj);
    }

    /**
     * 4.6.	加工单下发
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updatePcoToWms(OpsPcoAddItemDto param) throws OpsException {
        //获取参数
        OpsPco opsPco = param.getOpsPco();
        List<OpsPcoItem> list = param.getList();
        if (opsPco == null || CollectionUtils.isEmpty(list)) {
            return CommonResult.failure("Pco 或 pcoItem 为空");
        }
        DocOrderHeaderDto header = new DocOrderHeaderDto();
        header.setWarehouseId(opsPco.getWarehouseCode());//仓库id
        header.setCustomerId("SMC"); //货主id
        header.setOrderType("KT"); //加工单固定
        header.setDocNo(opsPco.getPcoId());
        header.setHedi15("KT");
        header.setCrossdockFlag("N");// 越库标记  Y越库
        //取表中数据，应对订单修改
        if (Objects.nonNull(opsPco.getRoId())) {
            header.setSoReferenceA(opsPco.getRoId());
            //header.setCrossdockFlag("Y");// 越库标记  Y越库 永远不传
            header.setHedi15("KTYK");
        }
        //外部到货
        if (StringUtils.isNotBlank(param.getYuKuRoId())) { //上预约不传RoId 只有越库才传RoId
            header.setSoReferenceA(param.getYuKuRoId());
            //header.setCrossdockFlag("Y");// 越库标记  Y越库
            header.setHedi15("KTYK");
        }
        header.setSoReferenceB(opsPco.getOrderId());
        header.setSoReferenceC(opsPco.getOrderId() + "-" + opsPco.getOrderItem());
        header.setSoReferenceD(opsPco.getOrderId() + "-" + opsPco.getOrderItem() + "-" + opsPco.getNum());
        if (null == opsPco.getSpceialFlag()) {
            header.setPriority("2");//1为特发订单 2 为普通订单
        } else {
            header.setPriority(opsPco.getSpceialFlag() + "");//1为特发订单 2 为普通订单  3为自提
        }
        header.setOrderTime(opsPco.getCreTime());

        header.setExpectedShipmentTime1(opsPco.getWlDate());
        header.setRequiredDeliveryTime(opsPco.getHopeDate());

        header.setConsigneeId("jg");
        header.setChannel("*");
        header.setHedi03("");//TODO 20220117; 需要和wms统一制定； 组装还是捆绑
        header.setHedi04(opsPco.getUserNo());
        header.setHedi06(opsPco.getPcoSource() + "");
        //header.setHedi09(opsPco.getUserNo());//专备客户
        //header.setHedi10();//客户订单号
        header.setNotes(opsPco.getRemark());
        header.setUserDefine1(param.getDoId());

        List<DocOrderDetailsDto> details = new ArrayList<>();
        for (OpsPcoItem item : list) {
            DocOrderDetailsDto detail = new DocOrderDetailsDto();
            detail.setDedi08(item.getRelocation());//pco下预约
            detail.setReferenceNo(opsPco.getPcoId());
            detail.setLineNo(item.getPcoItem() + "");
            detail.setSku(item.getSubModelno());
            detail.setQtyOrdered(item.getSubQty() + "");
            detail.setDedi04(opsPco.getBomid() + "");
            detail.setLotAtt05("");
            detail.setLotAtt06(opsPco.getGreenCode());
            detail.setLotAtt07("");//产地
            detail.setLotAtt08("OK");//质量状态
            detail.setLotAtt09(opsPco.getUserNo());
            detail.setLotAtt10("");
            //读取表中的批属性13
            if (Objects.nonNull(item.getIsCross()) && item.getIsCross()) { // 越库和上预约都带上此字段
                detail.setLotAtt13(opsPco.getPcoId());
            }
            //批属性13
            if (Objects.nonNull(param.getYuKuMap())) {//越库用 pcoId // 越库和上预约都带上此字段
                if (Objects.nonNull(param.getYuKuMap().get(item.getSubModelno()))) {
                    detail.setLotAtt13(param.getYuKuMap().get(item.getSubModelno()));
                }
            }
            //bugid 17544
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(opsPco.getOrderId()).andRorderItemEqualTo(Integer.valueOf(opsPco.getOrderItem()));
            List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(rcvdetails)) {
                Rcvdetail rcvdetail = rcvdetails.get(0);
                if(StringUtils.isBlank(rcvdetail.getStockType())){
                    detail.setDedi06(OrderStockTypeEnum.ZK.getDesc());
                    detail.setDedi07(opsPco.getWarehouseCode());
                }else {
                    detail.setDedi06(OrderStockTypeEnum.getDescFromType(rcvdetail.getStockType()));
                    detail.setDedi07(rcvdetail.getStockCode());
                }
            }

            details.add(detail);
        }
        header.setDetails(details);
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();
        headers.add(header);
        data.put("header", headers);
        obj.put("data", data);
        return CommonResult.success(obj);
    }

    @Override
    public CommonResult<String> cancelDocAsn(CancelDocAsnDto param) throws OpsException {
        JSONObject obj = new JSONObject();
        obj.put("data", param);
        String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=cancelASN&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success("成功");
        } else {
            return CommonResult.failure("失败:" + result);
        }
    }

    /**
     * bugid:14135 c14717 2024-5-9 取消ro
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> cancelDocAsnV2(CancelDocAsnV2Dto param) throws OpsException {
        JSONObject obj = new JSONObject();
        obj.put("data", param);
        String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=OPS_ASNC&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025";
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success(resultVO.getResponse().getResult().getReturnDesc());
        } else {
            return CommonResult.failure("失败:" + result);
        }
    }

    //bugid:12714 c14717 2023-11-27
    @Override
    public CommonResult<List<JSONObject>> cancelDocOrderV2(List<CancelDocOrderV2Dto> param) throws OpsException {
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("header", param);
        obj.put("data", data);
        String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=SO_CANCE2&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025";
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success("成功", null);
        } else {
            return CommonResult.failure("失败:" + result);
        }
    }

    /**
     * bugid 8.50 2022-11-17 c14717 新接口
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<List<JSONObject>> cancelDocOrderNew(List<CancelDocOrderDto> param) throws OpsException {
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("header", param);
        obj.put("data", data);
        String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=PL_SOC2&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析json异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success("成功", null);
        } else {
            return CommonResult.failure("失败:" + result);
        }
    }

    /**
     * param.setAddress("test——全地址 改小写");
     * param.setCustomerId("SMC");
     * param.setOrderNo("DOH004JAA20");
     * param.setWarehouseId("KBJ");
     * param.setProvince("110000");
     * param.setCity("110100");
     * param.setDistrict("110101");
     * param.setAddress2("详细地址 改小写");
     * param.setPriority("1");
     * param.setCarrierId("SF");//顺丰
     * <p>
     * 4.9 收货地址变更 //todo 增加 需要增加特发标识，增加指定承运商，收货地址是否选用地址编码 等7个字段；
     *
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> changeConsigneeAddress(ChangeConsigneeAddressDto param) throws OpsException {
        //test 数据
        JSONObject obj = new JSONObject();
        obj.put("data", param);
        String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=OPS_CADDRESS&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
        String result = okHttpUtil.doPostJson(url, obj.toJSONString());
        WmsResultVO resultVO = null;
        try {
            resultVO = JSON.parseObject(result, WmsResultVO.class);
        } catch (Exception e) {
            return CommonResult.failure(result + "");//解析 json 异常 Bad Gateway
        }
        if (resultVO != null && resultVO.isSuccess()) {
            return CommonResult.success("成功");
        } else {
            return CommonResult.failure("失败:" + result);
        }
    }


    /**
     * 下发DO-PCO（合并下发方法） || 包含委托在库下发
     * 先下发DO后下发PCO
     *指令下发 更新状态 用于首次下发
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> updateDoPcoToWmsOrToWT(OpsSendPcoAndDoMidDto obj) throws OpsException {
        if (Objects.isNull(obj) || Objects.isNull(obj.getDoDto()) || Objects.isNull(obj.getPcoDto())
                || Objects.isNull(obj.getDoDto().getWmOrderTaskId()) || Objects.isNull(obj.getPcoDto().getWmOrderTaskId())) {
            return CommonResult.failure("参数不能为空");
        }
        //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
        if (DoStatusEnum.PART.getStatus().equals(obj.getDoDto().getOpsDo().getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(obj.getDoDto().getOpsDo().getDoStatus())) {
            updateOpsPCOStatus(obj.getPcoDto().getWmOrderTaskId(), "pco", obj.getPcoDto().getOpsPco().getId() + "", obj.getPcoDto().getOpsPco().getPcoId(), false, "已发运");//失败原因去日志表查
            updateOpsDOStatus(obj.getDoDto().getWmOrderTaskId(), "do", obj.getDoDto().getOpsDo().getId() + "", obj.getDoDto().getOpsDo().getDoId(), false, "已发运");//失败原因去日志表查
            return CommonResult.failure("物流已发运");
        }
        if (!DoSourceEnum.ASSModelNo.getType().equals(obj.getDoDto().getOpsDo().getDoSource()) || !DoTypeEnum.JYCK.getType().equals(obj.getDoDto().getOpsDo().getDoType())) {
            return CommonResult.failure("do数据错误,do需要类型为交易出库单的加工单类型 doId:" + obj.getDoDto().getOpsDo().getDoId());
        }
        String doOrderId = obj.getDoDto().getOpsDo().getOrderId() + "" + obj.getDoDto().getOpsDo().getOrderItem();
        String pcoOrderId = obj.getPcoDto().getOpsPco().getOrderId() + "" + obj.getPcoDto().getOpsPco().getOrderItem();
        if (!doOrderId.equals(pcoOrderId)) {
            return CommonResult.failure("pco和do订单不匹配 doId:" + obj.getDoDto().getOpsDo().getDoId() + " pcoId:" + obj.getPcoDto().getOpsPco().getPcoId());
        }
        //查询仓库 区别下发wms委托在库
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(obj.getDoDto().getOpsDo().getWarehouseCode());
        if (Objects.nonNull(opsWarehouse)) {
            if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) { //下发委托在库
                //下发委托在库
                pco_doToWT(obj.getDoDto(), opsWarehouse);//下发委托在库调拨单 基本不存在
                pcoToWT(obj.getPcoDto(), opsWarehouse);//下发委托在库加工单
                return CommonResult.success("成功");
            } else {//非委托在库
                return sendPcoAndDoToWmsNew(obj.getDoDto(), obj.getPcoDto());
            }
        } else {
            return CommonResult.failure("下发失败，请检查仓库号：" + obj.getPcoDto().getOpsPco().getWarehouseCode());
        }
    }

    /**
     *  bugid:12187 c14717 20230921
     * 下发DO-PCO（合并下发方法） || 包含委托在库下发
     * 先下发DO后下发PCO
     * 指令下发 不更状态 用于二次下发
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> postWmsDoAndPcoNew(OpsSendPcoAndDoMidDto obj) throws OpsException {
        if (Objects.isNull(obj) || Objects.isNull(obj.getDoDto()) || Objects.isNull(obj.getPcoDto())) {
            return CommonResult.failure("参数不能为空");
        }
        //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
        if (DoStatusEnum.PART.getStatus().equals(obj.getDoDto().getOpsDo().getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(obj.getDoDto().getOpsDo().getDoStatus())) {
            return CommonResult.failure("物流已发运");
        }
        if (!DoSourceEnum.ASSModelNo.getType().equals(obj.getDoDto().getOpsDo().getDoSource()) || !DoTypeEnum.JYCK.getType().equals(obj.getDoDto().getOpsDo().getDoType())) {
            return CommonResult.failure("do数据错误,do需要类型为交易出库单的加工单类型 doId:" + obj.getDoDto().getOpsDo().getDoId());
        }
        String doOrderId = obj.getDoDto().getOpsDo().getOrderId() + "" + obj.getDoDto().getOpsDo().getOrderItem();
        String pcoOrderId = obj.getPcoDto().getOpsPco().getOrderId() + "" + obj.getPcoDto().getOpsPco().getOrderItem();
        if (!doOrderId.equals(pcoOrderId)) {
            return CommonResult.failure("pco和do订单不匹配 doId:" + obj.getDoDto().getOpsDo().getDoId() + " pcoId:" + obj.getPcoDto().getOpsPco().getPcoId());
        }
        //查询仓库 区别下发wms委托在库
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(obj.getDoDto().getOpsDo().getWarehouseCode());
        if (Objects.nonNull(opsWarehouse)) {
            if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) { //下发委托在库
                //下发委托在库
                return sendPcoAndDoToWT(obj.getDoDto(),obj.getPcoDto(), opsWarehouse);
            } else {//非委托在库
                return sendPcoAndDoToWms(obj.getDoDto(), obj.getPcoDto());
            }
        } else {
            return CommonResult.failure("下发失败，请检查仓库号：" + obj.getPcoDto().getOpsPco().getWarehouseCode());
        }

    }

    /**
     * 4.6.	销售/发运订单/盘点差异调账下发 --- 批量下发报文
     * @param params
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updateDoToWmsBatch(List<OpsDoAndItemDto> params) throws OpsException {
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();//指令内容
        for(OpsDoAndItemDto param : params){
            //获取参数
            OpsDo opsDo = param.getOpsDo();
            List<OpsDoItem> list = param.getList();
            if (opsDo == null || CollectionUtils.isEmpty(list)) {
                return CommonResult.failure("Do或DoItem为空");
            }
            DocOrderHeaderDto header = new DocOrderHeaderDto();
            header.setCustomerId("SMC"); //货主id
            if (DoTypeEnum.getType(opsDo.getDoType()) != null) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    header.setWarehouseId(opsDo.getGatherWarehouseCode());//仓库id
                    header.setConsigneeId(opsDo.getCustomerNo());
                    if (StringUtils.isNotBlank(opsDo.getCustomerNo())) {
                        // bugid:20750  暂不提交代码 商城
                        if(Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                            header.setConsigneeName(opsDo.getCstmname());
                        } else {
                            OpsCustomer opsCustomer = opsCustomerMapper.selectByPrimaryKey(opsDo.getCustomerNo());
                            if (Objects.nonNull(opsCustomer)) {
                                header.setConsigneeName(opsCustomer.getName());//公司名称
                            } else {
                                return CommonResult.failure("客户表无数据" + opsDo.getCustomerNo());
                            }
                        }
                    } else {
                        header.setConsigneeName("无数据2");//公司名称
                    }
                    if (!DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                        //bugid 17544
                        RcvdetailExample example = new RcvdetailExample();
                        example.createCriteria().andRorderNoEqualTo(opsDo.getOrderId()).andRorderItemEqualTo(Integer.valueOf(opsDo.getOrderItem()));
                        List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(rcvdetails)) {
                            Rcvdetail rcvdetail = rcvdetails.get(0);
                            if(StringUtils.isBlank(rcvdetail.getStockType())) {
                                header.setUserDefine2(OrderStockTypeEnum.ZK.getDesc());
                                header.setUserDefine3(opsDo.getWarehouseCode());
                            }else {
                                header.setUserDefine2(OrderStockTypeEnum.getDescFromType(rcvdetail.getStockType()));
                                header.setUserDefine3(rcvdetail.getStockCode());
                            }

                        }
                    }
                } else {
                    header.setConsigneeId(opsDo.getGatherWarehouseCode());//目的仓
                    OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(opsDo.getGatherWarehouseCode());
                    header.setConsigneeName(opsWarehouse.getWarehouseName());//目的仓库名称
                    header.setWarehouseId(opsDo.getWarehouseCode());//仓库id
                }
                header.setOrderType(DoTypeEnum.getType(opsDo.getDoType()).getWltype());
                // bugid:20750  暂不提交代码 商城 商城 DS  指定顺丰
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())
                        && Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                    header.setOrderType("DS");
                    header.setCarrierId(opsDo.getCarried());
                }
            } else {
                return CommonResult.failure("wm-do 订单类型不匹配getDoType：" + opsDo.getDoType());
            }
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    header.setHedi15("KT");
                }
            }
            // bugid:18126 c14717 20250703
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
                header.setHedi18("Y");
            }
            //header.sethedi 15
            header.setDocNo(opsDo.getDoId());
            header.setCrossdockFlag("N"); // 越库标记  Y越库
            //读取表 应对与订单修改场景
            if (Objects.nonNull(opsDo.getRoCrossType()) && opsDo.getRoCrossType() == 2) {//0 上货架 1上预约 2越库
                header.setSoReferenceA(opsDo.getRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
                if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    header.setHedi15("KTYK");
                } else {
                    header.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
                }
            }
            //外部到货实时调用
            if (StringUtils.isNotBlank(param.getYuKuRoId())) {
                header.setSoReferenceA(param.getYuKuRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
                if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    header.setHedi15("KTYK");
                } else {
                    header.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
                }
            }
            header.setSoReferenceB(opsDo.getOrderId());
            header.setSoReferenceC(opsDo.getOrderId() + "-" + opsDo.getOrderItem());
            header.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getNum()); //13位
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    header.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getAssNum()); //13位
                }
            }
            if (null == opsDo.getSpceialFlag()) {
                header.setPriority("2");//1为特发订单 2 为普通订单
            } else {
                header.setPriority(opsDo.getSpceialFlag() + "");//1为特发订单 2 为普通订单
            }
            header.setOrderTime(opsDo.getCreTime());
            header.setExpectedShipmentTime1(opsDo.getWlDate());
            header.setRequiredDeliveryTime(opsDo.getHopeDate());
            if (StringUtils.isEmpty(opsDo.getProvince())
                    || StringUtils.isEmpty(opsDo.getCity())
                    || StringUtils.isEmpty(opsDo.getDistrict())
                    || StringUtils.isEmpty(opsDo.getAddress())
                    || StringUtils.isEmpty(opsDo.getLinkman())
            ) {
                throw Exceptions.OpsException("此单无发货基础数据");//自提也需要维护发货基础数据
            }

            if (StringUtils.isEmpty(opsDo.getPhone()) && StringUtils.isEmpty(opsDo.getMobile())) {
                throw Exceptions.OpsException("此单无发货基础数据手机号");//自提也需要维护发货基础数据
            }

            header.setConsigneeContact(opsDo.getLinkman());
            header.setConsigneeCountry("86");//代码 86 中国 为什么传代码 TMS 用；
            if (StringUtils.isNotBlank(opsDo.getProvince())) {
                header.setConsigneeProvince(opsDo.getProvince().substring(0, 2));//代码
            }
            if (StringUtils.isNotBlank(opsDo.getCity())) {
                if (opsDo.getCity().length() > 3) {
                    String target = opsDo.getCity().substring(0, 4);
                    String result = opsDo.getCity().substring(0, 4);
                    switch (target) {
                        case "1100":
                            result = "1101";
                            break;
                        case "1200":
                            result = "1201";
                            break;
                        case "3100":
                            result = "3101";
                            break;
                        case "5000":
                            result = "5001";
                            break;
                    }
                    header.setConsigneeCity(result);//代码
                } else {
                    header.setConsigneeCity(opsDo.getCity());//代码
                }
            }

        /*if(StringUtils.isNotBlank(opsDo.getDistrict())){
            header.setConsigneeProvince(opsDo.getDistrict().substring(0,2));//代码
        }
        if(StringUtils.isNotBlank(opsDo.getDistrict())){
            if(opsDo.getDistrict().length()>3){
                header.setConsigneeCity(opsDo.getDistrict().substring(0,4));//代码
            }else {
                header.setConsigneeCity(opsDo.getDistrict());//代码
            }
        }*/
            //header.setConsigneeDistrict(opsDo.getDistrict());//不传区
            header.setConsigneeStreet("");//没有街道字段 超长
            header.setConsigneeAddress1(opsDo.getAddress());//地址
            header.setConsigneeAddress2(opsDo.getAddress());//地址
            header.setConsigneeTel1(opsDo.getMobile());//填写手机号
            header.setConsigneeTel2(opsDo.getPhone());//1111
            if (StringUtils.isNotBlank(opsDo.getPostcode())) {
                String str = "^[1-9]\\d{5}$";
                Pattern p = Pattern.compile(str);
                //Pattern中的matcher()方法传入要匹配的字符串与正则进行匹配i
                Matcher m = p.matcher(opsDo.getPostcode());
                //Matcher类中的matches()方法判断是否匹配成功
                boolean bo = m.matches();
                if (bo) {
                    header.setConsigneeZip(opsDo.getPostcode());
                }
            }
            //大田 DTW 不传
            /*if (Objects.nonNull(opsDo.getCarried()) && !"NON".equalsIgnoreCase(opsDo.getCarried()) && !"DTW".equalsIgnoreCase(opsDo.getCarried())) {
                header.setCarrierId(opsDo.getCarried());
            }*/
            header.setChannel("*");
            header.setHedi01(opsDo.getNum() + "");//opsDo.getOrderCount();
            if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.SpecPackage)) {
                header.setHedi02(opsDo.getExpLinkNo());//特殊包装要求
            }
            if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.WholeOrderToAssemble) || OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.OneItemToAssemble)) {
                if (Objects.nonNull(opsDo.getExpDlvType()) && Objects.nonNull(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()))) {
                    header.setHedi03(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()).specName());
                }
            }
            header.setHedi04(opsDo.getUserNo());//最终客户
            header.setHedi05(opsDo.getPakageType());// 集约方式 分包  01 客户地址 02 代理店 03 客户// 0:按地址集约;1:按订单集约;2:按用户集约
            if (Objects.nonNull(opsDo.getPakageType()) && "1".equals(opsDo.getPakageType())) {
                header.setHedi06("Y");//是否按照七位订单号单出
            } else {
                header.setHedi06("N");//是否按照七位订单号单出
            }
            header.setHedi07(opsDo.getDlvSite());// 1：直发客户，2：直发营业所，3：营业所/客户自提
            if (StringUtils.isNotEmpty(opsDo.getDlvSite()) && "3".equals(opsDo.getDlvSite())) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    header.setPriority("3");//1为特发订单 2 为普通订单 3 自提
                    header.setConsigneeAddress1("客户自提");//全地址
                    header.setConsigneeAddress2("客户自提");
                    // bugid:18786 20250827
                    header.setCarrierId(CarrierEnum.ZT.getCode());
                }else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())){
                    header.setPriority("2");
                    header.setHedi07("1");// 1：直发客户
                }
            }
            header.setHedi12(opsDo.getCorderNo());//客户订单号
            if (DoTypeEnum.ZHCK.getType().equals(opsDo.getDoType()) || DoTypeEnum.ZHCKOW.getType().equals(opsDo.getDoType())) {
                header.setConsigneeAddress1("库内组换");//组换单地址写仓内组换
                header.setConsigneeAddress2("库内组换");//组换单地址写仓内组换
                header.setConsigneeName("组换");//组换 //物流已经确认
                header.setHedi12(opsDo.getRemark());//组换特殊标识  物流提出需求== 客户订单号
            }
            header.setHedi08(opsDo.getDeptNo());//归属营业所代码
            header.setHedi11(opsDo.getUserNo());//专备客户
            header.setNotes(opsDo.getRemark());
            List<DocOrderDetailsDto> details = new ArrayList<>();
            for (OpsDoItem item : list) {
                DocOrderDetailsDto detail = new DocOrderDetailsDto();
                detail.setDedi08(opsDo.getRelocation());//下预约字段
                detail.setReferenceNo(item.getDoId());
                detail.setLineNo(item.getDoItem() + "");
                detail.setSku(item.getModelno());
                detail.setDedi05(item.getCproductNo());//getCproductNo
                header.setHedi16(item.getContractNo());//客户合同号
                detail.setQtyOrdered(item.getQty() + "");
                detail.setPrice(item.getPrice());
                detail.setLotAtt05("");//颜色待定
                detail.setLotAtt06(item.getGreenCode());
                detail.setLotAtt07("");//产地
                detail.setLotAtt08("OK");//质量状态
                detail.setLotAtt09("");//专备客户 opsDo.getUserNo()
                detail.setLotAtt10("");//特殊材质待定
                //读取表
                if (Objects.nonNull(opsDo.getRoCrossType()) && (opsDo.getRoCrossType() == 2 || opsDo.getRoCrossType() == 1)) {//0 上货架 1上预约 2越库
                    detail.setLotAtt13(opsDo.getDoId());// 越库和上预约都带上此字段
                }
                //外部到货
                if (StringUtils.isNotBlank(param.getYuekuDoId())) {// 越库和上预约都带上此字段
                    detail.setLotAtt13(param.getYuekuDoId());
                }
                if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    detail.setLotAtt13(opsDo.getDoId());
                }
                details.add(detail);
            }
            header.setDetails(details);
            headers.add(header);
        }
        data.put("header", headers);
        obj.put("data", data);
        return CommonResult.success(obj);
    }

    /**
     * 4.6.	销售/发运订单/盘点差异调账下发
     * 收集加工单和交易出库单报文
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<JSONObject> updateDoToAndPcoWms(OpsDoAndItemDto paramDo, OpsPcoAddItemDto paramPco) throws OpsException {
        //获取参数
        //--------------------组装交易出库单报文-------------------
        OpsDo opsDo = paramDo.getOpsDo();
        List<OpsDoItem> OpsDoItemList = paramDo.getList();
        if (opsDo == null || CollectionUtils.isEmpty(OpsDoItemList)) {
            return CommonResult.failure("Do或DoItem为空");
        }
        DocOrderHeaderDto headerDo = new DocOrderHeaderDto();
        headerDo.setCustomerId("SMC"); //货主id
        if (DoTypeEnum.getType(opsDo.getDoType()) != null) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                headerDo.setWarehouseId(opsDo.getGatherWarehouseCode());//仓库id
                headerDo.setConsigneeId(opsDo.getCustomerNo());
                if (StringUtils.isNotBlank(opsDo.getCustomerNo())) {
                    // bugid:20750  暂不提交代码 商城
                    if(Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                        headerDo.setConsigneeName(opsDo.getCstmname());
                    } else {
                        OpsCustomer opsCustomer = opsCustomerMapper.selectByPrimaryKey(opsDo.getCustomerNo());
                        if (Objects.nonNull(opsCustomer)) {
                            headerDo.setConsigneeName(opsCustomer.getName());//公司名称
                        } else {
                            return CommonResult.failure("客户表无数据" + opsDo.getCustomerNo());
                        }
                    }
                } else {
                    headerDo.setConsigneeName("无数据2");//公司名称
                }
            } else {
                headerDo.setConsigneeId(opsDo.getGatherWarehouseCode());//目的仓
                OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(opsDo.getGatherWarehouseCode());
                headerDo.setConsigneeName(opsWarehouse.getWarehouseName());//目的仓库名称
                headerDo.setWarehouseId(opsDo.getWarehouseCode());//仓库id
            }
            headerDo.setOrderType(DoTypeEnum.getType(opsDo.getDoType()).getWltype());
            //bugid:20750 商城 DS  指定顺丰
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())
                    && Objects.nonNull(opsDo.getSourceType()) && "D".equals(opsDo.getSourceType())){
                headerDo.setOrderType("DS");
                headerDo.setCarrierId(opsDo.getCarried());
            }
        } else {
            return CommonResult.failure("wm-do 订单类型不匹配getDoType：" + opsDo.getDoType());
        }
        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                headerDo.setHedi15("KT");
            }
        }
        // bugid:18126 c14717 20250703
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            headerDo.setHedi18("Y");
        }
        //header.sethedi 15
        headerDo.setDocNo(opsDo.getDoId());
        headerDo.setCrossdockFlag("N"); // 越库标记  Y越库
        //读取表 应对与订单修改场景
        if (Objects.nonNull(opsDo.getRoCrossType()) && opsDo.getRoCrossType() == 2) {//0 上货架 1上预约 2越库
            headerDo.setSoReferenceA(opsDo.getRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                headerDo.setHedi15("KTYK");
            } else {
                headerDo.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
            }
        }
        //外部到货实时调用
        if (StringUtils.isNotBlank(paramDo.getYuKuRoId())) {
            headerDo.setSoReferenceA(paramDo.getYuKuRoId());//上预约不传RoId 只有越库才传RoId 最后一项越库入库单为准
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                headerDo.setHedi15("KTYK");
            } else {
                headerDo.setCrossdockFlag("Y"); // 越库标记  Y越库 加工单不传
            }
        }
        headerDo.setSoReferenceB(opsDo.getOrderId());
        headerDo.setSoReferenceC(opsDo.getOrderId() + "-" + opsDo.getOrderItem());
        headerDo.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getNum()); //13位
        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
            if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                headerDo.setSoReferenceD(opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-" + opsDo.getAssNum()); //13位
            }
        }
        if (null == opsDo.getSpceialFlag()) {
            headerDo.setPriority("2");//1为特发订单 2 为普通订单
        } else {
            headerDo.setPriority(opsDo.getSpceialFlag() + "");//1为特发订单 2 为普通订单
        }
        headerDo.setOrderTime(opsDo.getCreTime());
        headerDo.setExpectedShipmentTime1(opsDo.getWlDate());
        headerDo.setRequiredDeliveryTime(opsDo.getHopeDate());
        if (StringUtils.isEmpty(opsDo.getProvince())
                || StringUtils.isEmpty(opsDo.getCity())
                || StringUtils.isEmpty(opsDo.getDistrict())
                || StringUtils.isEmpty(opsDo.getAddress())
                || StringUtils.isEmpty(opsDo.getLinkman())
        ) {
            return CommonResult.failure("此单无发货基础数据");//自提也需要维护发货基础数据
        }

        if (StringUtils.isEmpty(opsDo.getPhone()) && StringUtils.isEmpty(opsDo.getMobile())) {
            return CommonResult.failure("此单无发货基础数据手机号");//自提也需要维护发货基础数据
        }

        headerDo.setConsigneeContact(opsDo.getLinkman());
        headerDo.setConsigneeCountry("86");//代码 86 中国 为什么传代码 TMS 用；
        if (StringUtils.isNotBlank(opsDo.getProvince())) {
            headerDo.setConsigneeProvince(opsDo.getProvince().substring(0, 2));//代码
        }
        if (StringUtils.isNotBlank(opsDo.getCity())) {
            if (opsDo.getCity().length() > 3) {
                String target = opsDo.getCity().substring(0, 4);
                String result = opsDo.getCity().substring(0, 4);
                switch (target) {
                    case "1100":
                        result = "1101";
                        break;
                    case "1200":
                        result = "1201";
                        break;
                    case "3100":
                        result = "3101";
                        break;
                    case "5000":
                        result = "5001";
                        break;
                }
                headerDo.setConsigneeCity(result);//代码
            } else {
                headerDo.setConsigneeCity(opsDo.getCity());//代码
            }
        }
        //header.setConsigneeDistrict(opsDo.getDistrict());//不传区
        headerDo.setConsigneeStreet("");//没有街道字段 超长
        headerDo.setConsigneeAddress1(opsDo.getAddress());//地址
        headerDo.setConsigneeAddress2(opsDo.getAddress());//地址
        headerDo.setConsigneeTel1(opsDo.getMobile());//填写手机号
        headerDo.setConsigneeTel2(opsDo.getPhone());//1111
        if (StringUtils.isNotBlank(opsDo.getPostcode())) {
            String str = "^[1-9]\\d{5}$";
            Pattern p = Pattern.compile(str);
            //Pattern中的matcher()方法传入要匹配的字符串与正则进行匹配i
            Matcher m = p.matcher(opsDo.getPostcode());
            //Matcher类中的matches()方法判断是否匹配成功
            boolean bo = m.matches();
            if (bo) {
                headerDo.setConsigneeZip(opsDo.getPostcode());
            }
        }
        /*//大田 DTW 不传
        if (Objects.nonNull(opsDo.getCarried()) && !"NON".equalsIgnoreCase(opsDo.getCarried()) && !"DTW".equalsIgnoreCase(opsDo.getCarried())) {
            headerDo.setCarrierId(opsDo.getCarried());
        }*/
        headerDo.setChannel("*");
        headerDo.setHedi01(opsDo.getNum() + "");//opsDo.getOrderCount();
        if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.SpecPackage)) {
            headerDo.setHedi02(opsDo.getExpLinkNo());//特殊包装要求
        }
        if (OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.WholeOrderToAssemble) || OrderSpecExpType.include(opsDo.getExpDlvType(), OrderSpecExpType.OneItemToAssemble)) {
            if (Objects.nonNull(opsDo.getExpDlvType()) && Objects.nonNull(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()))) {
                headerDo.setHedi03(OrderSpecExpType.getSpecExpType(opsDo.getExpDlvType()).specName());
            }
        }
        headerDo.setHedi04(opsDo.getUserNo());//最终客户
        headerDo.setHedi05(opsDo.getPakageType());// 集约方式 分包  01 客户地址 02 代理店 03 客户// 0:按地址集约;1:按订单集约;2:按用户集约
        if (Objects.nonNull(opsDo.getPakageType()) && "1".equals(opsDo.getPakageType())) {
            headerDo.setHedi06("Y");//是否按照七位订单号单出
        } else {
            headerDo.setHedi06("N");//是否按照七位订单号单出
        }
        headerDo.setHedi07(opsDo.getDlvSite());// 1：直发客户，2：直发营业所，3：营业所/客户自提
        if (StringUtils.isNotEmpty(opsDo.getDlvSite()) && "3".equals(opsDo.getDlvSite())) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                headerDo.setPriority("3");//1为特发订单 2 为普通订单 3 自提
                headerDo.setConsigneeAddress1("客户自提");//全地址
                headerDo.setConsigneeAddress2("客户自提");
                // bugid:18786 20250827
                headerDo.setCarrierId(CarrierEnum.ZT.getCode());
            }else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())){
                headerDo.setPriority("2");
                headerDo.setHedi07("1");// 1：直发客户
            }
        }
        headerDo.setHedi12(opsDo.getCorderNo());//客户订单号
        if (DoTypeEnum.ZHCK.getType().equals(opsDo.getDoType()) ||DoTypeEnum.ZHCKOW.getType().equals(opsDo.getDoType()) ) {
            headerDo.setConsigneeAddress1("库内组换");//组换单地址写仓内组换
            headerDo.setConsigneeAddress2("库内组换");//组换单地址写仓内组换
            headerDo.setConsigneeName("组换");//组换 //物流已经确认
            headerDo.setHedi12(opsDo.getRemark());//组换特殊标识  物流提出需求== 客户订单号
        }
        headerDo.setHedi08(opsDo.getDeptNo());//归属营业所代码
        headerDo.setHedi11(opsDo.getUserNo());//专备客户
        headerDo.setNotes(opsDo.getRemark());

        List<DocOrderDetailsDto> detailDos = new ArrayList<>();
        for (OpsDoItem item : OpsDoItemList) {
            DocOrderDetailsDto detail = new DocOrderDetailsDto();
            detail.setDedi08(opsDo.getRelocation());//下预约字段
            detail.setReferenceNo(item.getDoId());
            detail.setLineNo(item.getDoItem() + "");
            detail.setSku(item.getModelno());
            detail.setDedi05(item.getCproductNo());//getCproductNo
            headerDo.setHedi16(item.getContractNo());//客户合同号
            detail.setQtyOrdered(item.getQty() + "");
            detail.setPrice(item.getPrice());
            detail.setLotAtt05("");//颜色待定
            detail.setLotAtt06(item.getGreenCode());
            detail.setLotAtt07("");//产地
            detail.setLotAtt08("OK");//质量状态
            detail.setLotAtt09("");//专备客户 opsDo.getUserNo()
            detail.setLotAtt10("");//特殊材质待定
            //读取表
            if (Objects.nonNull(opsDo.getRoCrossType()) && (opsDo.getRoCrossType() == 2 || opsDo.getRoCrossType() == 1)) {//0 上货架 1上预约 2越库
                detail.setLotAtt13(opsDo.getDoId());// 越库和上预约都带上此字段
            }
            //外部到货
            if (StringUtils.isNotBlank(paramDo.getYuekuDoId())) {// 越库和上预约都带上此字段
                detail.setLotAtt13(paramDo.getYuekuDoId());
            }
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                detail.setLotAtt13(opsDo.getDoId());
            }
            detailDos.add(detail);
        }
        headerDo.setDetails(detailDos);

        //--------------------组装加工单报文-------------------
        //获取参数
        OpsPco opsPco = paramPco.getOpsPco();
        List<OpsPcoItem> OpsPcoItemList = paramPco.getList();
        if (opsPco == null || CollectionUtils.isEmpty(OpsPcoItemList)) {
            return CommonResult.failure("Pco 或 pcoItem 为空");
        }
        DocOrderHeaderDto headerPco = new DocOrderHeaderDto();
        headerPco.setWarehouseId(opsPco.getWarehouseCode());//仓库id
        headerPco.setCustomerId("SMC"); //货主id
        headerPco.setOrderType("KT"); //加工单固定
        // bugid:18126 c14717 20250703
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            headerPco.setHedi18("Y");
        }
        headerPco.setDocNo(opsPco.getPcoId());
        headerPco.setHedi15("KT");
        headerPco.setCrossdockFlag("N");// 越库标记  Y越库
        //取表中数据，应对订单修改
        if (Objects.nonNull(opsPco.getRoId())) {
            headerPco.setSoReferenceA(opsPco.getRoId());
            //header.setCrossdockFlag("Y");// 越库标记  Y越库 永远不传
            headerPco.setHedi15("KTYK");
        }
        //外部到货
        if (StringUtils.isNotBlank(paramPco.getYuKuRoId())) { //上预约不传RoId 只有越库才传RoId
            headerPco.setSoReferenceA(paramPco.getYuKuRoId());
            //header.setCrossdockFlag("Y");// 越库标记  Y越库
            headerPco.setHedi15("KTYK");
        }
        headerPco.setSoReferenceB(opsPco.getOrderId());
        headerPco.setSoReferenceC(opsPco.getOrderId() + "-" + opsPco.getOrderItem());
        headerPco.setSoReferenceD(opsPco.getOrderId() + "-" + opsPco.getOrderItem() + "-" + opsPco.getNum());
        if (null == opsPco.getSpceialFlag()) {
            headerPco.setPriority("2");//1为特发订单 2 为普通订单
        } else {
            headerPco.setPriority(opsPco.getSpceialFlag() + "");//1为特发订单 2 为普通订单  3为自提
        }
        headerPco.setOrderTime(opsPco.getCreTime());
        headerPco.setExpectedShipmentTime1(opsPco.getWlDate());
        headerPco.setRequiredDeliveryTime(opsPco.getHopeDate());
        headerPco.setConsigneeId("jg");
        headerPco.setChannel("*");
        headerPco.setHedi03("");//TODO 20220117; 需要和wms统一制定； 组装还是捆绑
        headerPco.setHedi04(opsPco.getUserNo());
        headerPco.setHedi06(opsPco.getPcoSource() + "");
        //header.setHedi09(opsPco.getUserNo());//专备客户
        //header.setHedi10();//客户订单号
        headerPco.setNotes(opsPco.getRemark());
        headerPco.setUserDefine1(opsDo.getDoId());

        List<DocOrderDetailsDto> details = new ArrayList<>();
        for (OpsPcoItem item : OpsPcoItemList) {
            DocOrderDetailsDto detail = new DocOrderDetailsDto();
            detail.setDedi08(item.getRelocation());//pco下预约
            detail.setReferenceNo(opsPco.getPcoId());
            detail.setLineNo(item.getPcoItem() + "");
            detail.setSku(item.getSubModelno());
            detail.setQtyOrdered(item.getSubQty() + "");
            detail.setDedi04(opsPco.getBomid() + "");
            detail.setLotAtt05("");
            detail.setLotAtt06(opsPco.getGreenCode());
            detail.setLotAtt07("");//产地
            detail.setLotAtt08("OK");//质量状态
            detail.setLotAtt09(opsPco.getUserNo());
            detail.setLotAtt10("");
            //读取表中的批属性13
            if (Objects.nonNull(item.getIsCross()) && item.getIsCross()) { // 越库和上预约都带上此字段
                detail.setLotAtt13(opsPco.getPcoId());
            }
            //批属性13
            if (Objects.nonNull(paramPco.getYuKuMap())) {//越库用 pcoId // 越库和上预约都带上此字段
                if (Objects.nonNull(paramPco.getYuKuMap().get(item.getSubModelno()))) {
                    detail.setLotAtt13(paramPco.getYuKuMap().get(item.getSubModelno()));
                }
            }
            //bugid 17544
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(opsPco.getOrderId()).andRorderItemEqualTo(Integer.valueOf(opsPco.getOrderItem()));
            List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(rcvdetails)) {
                Rcvdetail rcvdetail = rcvdetails.get(0);
                if(StringUtils.isBlank(rcvdetail.getStockType())){
                    detail.setDedi06(OrderStockTypeEnum.ZK.getDesc());
                    detail.setDedi07(opsDo.getWarehouseCode());
                }else {
                    detail.setDedi06(OrderStockTypeEnum.getDescFromType(rcvdetail.getStockType()));
                    detail.setDedi07(rcvdetail.getStockCode());
                }
            }
            details.add(detail);
        }
        headerPco.setDetails(details);
        //拼接格式化报文
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();
        headers.add(headerPco);
        /*data.put("header", headers);
        obj.put("data", data);*/
        //拼接格式化报文
        /*JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray headers = new JSONArray();*/
        headers.add(headerDo);
        data.put("header", headers);
        obj.put("data", data);
        return CommonResult.success(obj);
    }


    /**
     * 打包下发pco和do 富勒新街口
     * 指令下发 更新状态 用于首次下发
     * @param doParam
     * @param pcoParam
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> sendPcoAndDoToWmsNew(OpsDoAndItemDto doParam, OpsPcoAddItemDto pcoParam) throws OpsException {
        //1.组装报文 先下发DO
        pcoParam.setDoId(doParam.getOpsDo().getDoId());//加工单存储交易出库单id
        CommonResult<JSONObject> doAndPcoResult = updateDoToAndPcoWms(doParam, pcoParam);
        if (doAndPcoResult.isSuccess()) {
            String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
            CommonResult wmsResult = opsToWms(doAndPcoResult.getData(), url);
            if (wmsResult.isSuccess()) {//do下发成功
                updateOpsDOStatus(doParam.getWmOrderTaskId(), "do", doParam.getOpsDo().getId() + "", doParam.getOpsDo().getDoId(), true, "成功");
                updateOpsPCOStatus(pcoParam.getWmOrderTaskId(), "pco", pcoParam.getOpsPco().getId() + "", doParam.getOpsDo().getDoId(), true, "成功");
            } else {//下发失败 直接返回失败结果
                String msg = "";
                if (wmsResult.getMessage().length() > 200) {
                    msg = wmsResult.getMessage().substring(0, 200);
                } else {
                    msg = wmsResult.getMessage();
                }
                //bugid:19278 wms记录已存在，更新do——iswms
                if(StringUtil.isNotEmpty(wmsResult.getMessage())
                        && wmsResult.getMessage().contains("记录已存在 不允许修改")){
                    if(pcoParam.getOpsPco().getIsWms() == 0){
                        updateOpsPCOStatus(pcoParam.getWmOrderTaskId(), "pco", pcoParam.getOpsPco().getId() + "", pcoParam.getOpsPco().getPcoId(), true, "wms记录已存在，更新pco_iswms");//失败原因去日志表查
                    }else {
                        updateOpsPCOStatus(pcoParam.getWmOrderTaskId(), "pco", pcoParam.getOpsPco().getId() + "", pcoParam.getOpsPco().getPcoId(), false, msg);//失败原因去日志表查
                    }
                    if(doParam.getOpsDo().getIsWms() == 0){
                        updateOpsDOStatus(doParam.getWmOrderTaskId(), "do", doParam.getOpsDo().getId() + "", doParam.getOpsDo().getDoId(), true, "wms记录已存在，更新do_iswms");//失败原因去日志表查
                    }else {
                        updateOpsDOStatus(doParam.getWmOrderTaskId(), "do", doParam.getOpsDo().getId() + "", doParam.getOpsDo().getDoId(), false, msg);//失败原因去日志表查
                    }
                }else {
                    updateOpsPCOStatus(pcoParam.getWmOrderTaskId(), "pco", pcoParam.getOpsPco().getId() + "", pcoParam.getOpsPco().getPcoId(), false, msg);//失败原因去日志表查
                    updateOpsDOStatus(doParam.getWmOrderTaskId(), "do", doParam.getOpsDo().getId() + "", doParam.getOpsDo().getDoId(), false, msg);//失败原因去日志表查

                }
                 return CommonResult.failure("do下发失败：" + doParam.getOpsDo().getDoId());
            }
        } else {
            String msg = "";
            if (doAndPcoResult.getMessage().length() > 200) {
                msg = doAndPcoResult.getMessage().substring(0, 200);
            } else {
                msg = doAndPcoResult.getMessage();
            }
            updateOpsPCOStatus(pcoParam.getWmOrderTaskId(), "pco", pcoParam.getOpsPco().getId() + "", pcoParam.getOpsPco().getPcoId(), false, msg);//失败原因去日志表查
            updateOpsDOStatus(doParam.getWmOrderTaskId(), "do", doParam.getOpsDo().getId() + "", doParam.getOpsDo().getDoId(), false, msg);//失败原因去日志表查
            return CommonResult.failure("do下发失败：" + doParam.getOpsDo().getDoId());

        }
        return CommonResult.success("全部下发");
    }

    /**
     * 打包下发
     * bugid:12187 c14717 20230921
     * 指令下发 不更状态 用于二次下发
     * @param doParam
     * @param pcoParam
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> sendPcoAndDoToWms(OpsDoAndItemDto doParam, OpsPcoAddItemDto pcoParam) throws OpsException {
        //1.组装报文 先下发DO
        pcoParam.setDoId(doParam.getOpsDo().getDoId());//加工单存储交易出库单id
        CommonResult<JSONObject> doAndPcoResult = updateDoToAndPcoWms(doParam, pcoParam);
        if (doAndPcoResult.isSuccess()) {
            String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
            CommonResult wmsResult = opsToWms(doAndPcoResult.getData(), url);
            if (wmsResult.isSuccess()) {//do下发成功
                CommonResult.success("全部下发");
            } else {//下发失败 直接返回失败结果
                String msg = "";
                if (wmsResult.getMessage().length() > 200) {
                    msg = wmsResult.getMessage().substring(0, 200);
                } else {
                    msg = wmsResult.getMessage();
                }
                return CommonResult.failure(msg);
            }
        } else {
            String msg = "";
            if (doAndPcoResult.getMessage().length() > 200) {
                msg = doAndPcoResult.getMessage().substring(0, 200);
            } else {
                msg = doAndPcoResult.getMessage();
            }
            return CommonResult.failure(msg);
        }
        return CommonResult.success("全部下发");
    }

    /**
     * 委托在库下发 加工单的交易出库单
     *
     * @param param
     * @param opsWarehouse
     * @throws OpsException
     */
    private void pco_doToWT(OpsDoAndItemDto param, OpsWarehouse opsWarehouse) throws OpsException {
        Rcvdetail rcvdetail = null;
        if (DoTypeEnum.TKCK.getType().equals(param.getOpsDo().getDoType())) {
            updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库退货不写入csExpdetailVO");//失败原因去日志表查
        } else {
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(param.getOpsDo().getOrderId()).andRorderItemEqualTo(Integer.valueOf(param.getOpsDo().getOrderItem()));
            List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
            if (rcvdetailList.size() == 1) {
                rcvdetail = rcvdetailList.get(0);
            } else {
                throw Exceptions.OpsException("查询出" + rcvdetailList.size() + "条RcvDetail");
            }
        }
        try {
            //委托在库调用接口
            List<CsExpdetailVO> list = new ArrayList<>();
            //获取委托在库库存属性
            OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
            OpsDoItemInventoryExample.Criteria criteria = exampleDoItemInventory.createCriteria().andDoIdEqualTo(param.getOpsDo().getDoId()).andDelflagEqualTo(0);
            List<OpsDoItemInventory> doItemInvList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
            List<OpsWTInventoryDTO> listDto = new ArrayList<OpsWTInventoryDTO>();
            for (OpsDoItemInventory itemInventory : doItemInvList) {
                InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                InventoryDTO dto = null;
                if (InventoryTableTypeEnum.NORMAL.equals(tableTypeEnum)) {
                    OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        dto = new InventoryDTO(inventory);
                    }
                }
                if (InventoryTableTypeEnum.TRANS.equals(tableTypeEnum)) {
                    OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        dto = new InventoryDTO(inventory);
                    }
                }
                OpsInventoryProperty property = opsInventoryPropertyMapper.selectByPrimaryKey(dto.getInventoryPropertyId());
                OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                obj.setInventoryId(itemInventory.getInventoryId());
                obj.setInventoryType(property.getInventoryTypeCode());
                listDto.add(obj);
            }
            //如果不占用库存不存入cs_expdetail表
            if (CollectionUtils.isNotEmpty(listDto)) {
                for (OpsWTInventoryDTO opsWTInventoryDTO : listDto) {
                    CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
                    csExpdetailVO.setExpOrderNo(param.getOpsDo().getOrderId() + "-" + param.getOpsDo().getOrderItem() + "-" + param.getOpsDo().getNum());
                    csExpdetailVO.setModelNo(param.getList().get(0).getModelno());
                    csExpdetailVO.setExpQty(param.getList().get(0).getQty());
                    csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                    csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                    csExpdetailVO.setUserNo(param.getOpsDo().getUserNo());
                    csExpdetailVO.setCorderNo(param.getOpsDo().getCorderNo());
                    csExpdetailVO.setPrice(param.getList().get(0).getPrice());
                    if (Objects.nonNull(rcvdetail)) {
                        csExpdetailVO.setPplNo(rcvdetail.getPplNo());
                        csExpdetailVO.setProjectNo(rcvdetail.getProjectNo());
                        csExpdetailVO.setPrice(rcvdetail.getPrice());
                        if (Objects.nonNull(rcvdetail.getPriceEnduser())) {
                            csExpdetailVO.setPriceEnduser(rcvdetail.getPriceEnduser().toString());
                        }
                    } else {
                        csExpdetailVO.setStatus(2);
                    }
                    csExpdetailVO.setCproductNo(param.getList().get(0).getCproductNo());
                    csExpdetailVO.setInventoryId(opsWTInventoryDTO.getInventoryId());
                    csExpdetailVO.setInventoryTypeCode(opsWTInventoryDTO.getInventoryType());
                    csExpdetailVO.setDoId(param.getOpsDo().getDoId());
                    if (DoSourceEnum.ASSModelNo.getType().equals(param.getOpsDo().getDoSource())) {
                        csExpdetailVO.setItemType(2);
                        // item_type=2
                    } else {
                        // item_type=0
                        csExpdetailVO.setItemType(0);
                    }
                    list.add(csExpdetailVO);
                }
            }
            if (CollectionUtils.isEmpty(list)) {
                updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库加工单");//失败原因去日志表查
            }
            Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));
            if (Objects.nonNull(flag) && flag) {
                updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库成功");//失败原因去日志表查
            } else {
                updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, "存表失败");//失败原因去日志表查
            }

        } catch (Exception wmsResult) {
            if (wmsResult.getMessage().length() > 200) {
                updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
            } else {
                updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage());//失败原因去日志表查
            }
        }
    }

    /**
     * 委托在库下发加工单
     *
     * @param param
     * @param opsWarehouse
     * @throws OpsException
     */
    private void pcoToWT(OpsPcoAddItemDto param, OpsWarehouse opsWarehouse) throws OpsException {
        Rcvdetail rcvdetail = null;
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(param.getOpsPco().getOrderId()).andRorderItemEqualTo(Integer.valueOf(param.getOpsPco().getOrderItem()));
        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
        if (rcvdetailList.size() == 1) {
            rcvdetail = rcvdetailList.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + rcvdetailList.size() + "条RcvDetail");
        }
        if (Objects.nonNull(rcvdetail)) {
            try {
                List<CsExpdetailVO> list = new ArrayList<>();
                for (OpsPcoItem opsPcoItem : param.getList()) {
                    //委托在库调用接口
                    OpsPcoItemInventoryExample examplePcoItemInv = new OpsPcoItemInventoryExample();
                    OpsPcoItemInventoryExample.Criteria criteria = examplePcoItemInv.createCriteria();
                    criteria.andPcoIdEqualTo(param.getOpsPco().getPcoId());
                    criteria.andPcoItemEqualTo(opsPcoItem.getPcoItem());
                    criteria.andDelflagEqualTo(0);//删除标
                    List<OpsPcoItemInventory> pcoItemInventory = pcoItemInventoryMapper.selectByExample(examplePcoItemInv);
                    List<OpsWTInventoryDTO> listDto = new ArrayList<OpsWTInventoryDTO>();
                    for (OpsPcoItemInventory itemInventory : pcoItemInventory) {
                        InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                        InventoryDTO dto = null;
                        if (InventoryTableTypeEnum.NORMAL.equals(tableTypeEnum)) {
                            OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                            if (ObjectUtil.isNotNull(inventory)) {
                                dto = new InventoryDTO(inventory);
                            }
                        }
                        if (InventoryTableTypeEnum.TRANS.equals(tableTypeEnum)) {
                            OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                            if (ObjectUtil.isNotNull(inventory)) {
                                dto = new InventoryDTO(inventory);
                            }
                        }
                        OpsInventoryProperty property = opsInventoryPropertyMapper.selectByPrimaryKey(dto.getInventoryPropertyId());
                        OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                        obj.setInventoryId(itemInventory.getInventoryId());
                        obj.setInventoryType(property.getInventoryTypeCode());
                        listDto.add(obj);
                    }
                    OpsDoExample exaDO = new OpsDoExample();
                    exaDO.createCriteria().andOrderIdEqualTo(param.getOpsPco().getOrderId()).andOrderItemEqualTo(param.getOpsPco().getOrderItem()).andDelflagEqualTo(0);
                    List<OpsDo> doList = opsDoMapper.selectByExample(exaDO);
                    if (CollectionUtils.isEmpty(listDto)) {
                        throw Exceptions.OpsException("加工单下发无成品交易单");
                    }
                    OpsDoItemExample exaDoItem = new OpsDoItemExample();
                    exaDoItem.createCriteria().andDoIdEqualTo(doList.get(0).getDoId()).andDelflagEqualTo(0);
                    List<OpsDoItem> opsDoItemList = opsDoItemMapper.selectByExample(exaDoItem);
                    if (CollectionUtils.isNotEmpty(listDto)) {
                        for (OpsWTInventoryDTO opsWTInventoryDTO : listDto) {
                            CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
                            csExpdetailVO.setExpOrderNo(param.getOpsPco().getOrderId() + "-" + param.getOpsPco().getOrderItem() + "-" + opsPcoItem.getPcoItem());
                            csExpdetailVO.setModelNo(opsPcoItem.getSubModelno());
                            csExpdetailVO.setExpQty(opsPcoItem.getSubQty());
                            csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                            csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                            csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                            csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                            csExpdetailVO.setUserNo(param.getOpsPco().getUserNo());
                            csExpdetailVO.setCorderNo(rcvdetail.getCorderNo());
                            csExpdetailVO.setPrice(rcvdetail.getPrice());
                            csExpdetailVO.setPplNo(rcvdetail.getPplNo());
                            csExpdetailVO.setProjectNo(rcvdetail.getProjectNo());
                            csExpdetailVO.setCproductNo(rcvdetail.getCproductNo());
                            csExpdetailVO.setInventoryId(opsWTInventoryDTO.getInventoryId());
                            csExpdetailVO.setInventoryTypeCode(opsWTInventoryDTO.getInventoryType());
                            csExpdetailVO.setDoId(doList.get(0).getDoId() + "-" + opsDoItemList.get(0).getQty());
                            //item_type=1
                            csExpdetailVO.setItemType(1);
                            csExpdetailVO.setSalesModelNo(opsDoItemList.get(0).getModelno());
                            list.add(csExpdetailVO);
                        }
                    }
                }
                Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));
                if (Objects.nonNull(flag) && flag) {
                    updateOpsPCOStatus(param.getWmOrderTaskId(), "pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), true, "成功");
                } else {
                    updateOpsPCOStatus(param.getWmOrderTaskId(), "pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, "失败");
                }
            } catch (Exception wmsResult) {
                if (wmsResult.getMessage().length() > 200) {
                    updateOpsPCOStatus(param.getWmOrderTaskId(), "pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    updateOpsPCOStatus(param.getWmOrderTaskId(), "pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage());//失败原因去日志表查
                }
            }
        }
    }



    /**
     * 委托在库下发 加工单的交易出库单
     *
     * @param param
     * @param opsWarehouse
     * @throws OpsException
     */
    private CommonResult<String> sendPcoAndDoToWT(OpsDoAndItemDto param,OpsPcoAddItemDto pcoObj, OpsWarehouse opsWarehouse) throws OpsException {
        Rcvdetail rcvdetail = null;
        if (DoTypeEnum.TKCK.getType().equals(param.getOpsDo().getDoType())) {
            updateOpsDOStatus(param.getWmOrderTaskId(), "do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库退货不写入csExpdetailVO");//失败原因去日志表查
        } else {
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(param.getOpsDo().getOrderId()).andRorderItemEqualTo(Integer.valueOf(param.getOpsDo().getOrderItem()));
            List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
            if (rcvdetailList.size() == 1) {
                rcvdetail = rcvdetailList.get(0);
            } else {
                throw Exceptions.OpsException("查询出" + rcvdetailList.size() + "条RcvDetail");
            }
        }
        try {
            //委托在库调用接口
            List<CsExpdetailVO> list = new ArrayList<>();
            //获取委托在库库存属性
            OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
            OpsDoItemInventoryExample.Criteria criteria = exampleDoItemInventory.createCriteria().andDoIdEqualTo(param.getOpsDo().getDoId()).andDelflagEqualTo(0);
            List<OpsDoItemInventory> doItemInvList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
            List<OpsWTInventoryDTO> listDto = new ArrayList<OpsWTInventoryDTO>();
            for (OpsDoItemInventory itemInventory : doItemInvList) {
                InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                InventoryDTO dto = null;
                if (InventoryTableTypeEnum.NORMAL.equals(tableTypeEnum)) {
                    OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        dto = new InventoryDTO(inventory);
                    }
                }
                if (InventoryTableTypeEnum.TRANS.equals(tableTypeEnum)) {
                    OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        dto = new InventoryDTO(inventory);
                    }
                }
                OpsInventoryProperty property = opsInventoryPropertyMapper.selectByPrimaryKey(dto.getInventoryPropertyId());
                OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                obj.setInventoryId(itemInventory.getInventoryId());
                obj.setInventoryType(property.getInventoryTypeCode());
                listDto.add(obj);
            }
            //如果不占用库存不存入cs_expdetail表
            if (CollectionUtils.isNotEmpty(listDto)) {
                for (OpsWTInventoryDTO opsWTInventoryDTO : listDto) {
                    CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
                    csExpdetailVO.setExpOrderNo(param.getOpsDo().getOrderId() + "-" + param.getOpsDo().getOrderItem() + "-" + param.getOpsDo().getNum());
                    csExpdetailVO.setModelNo(param.getList().get(0).getModelno());
                    csExpdetailVO.setExpQty(param.getList().get(0).getQty());
                    csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                    csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                    csExpdetailVO.setUserNo(param.getOpsDo().getUserNo());
                    csExpdetailVO.setCorderNo(param.getOpsDo().getCorderNo());
                    csExpdetailVO.setPrice(param.getList().get(0).getPrice());
                    if (Objects.nonNull(rcvdetail)) {
                        csExpdetailVO.setPplNo(rcvdetail.getPplNo());
                        csExpdetailVO.setProjectNo(rcvdetail.getProjectNo());
                        csExpdetailVO.setPrice(rcvdetail.getPrice());
                        if (Objects.nonNull(rcvdetail.getPriceEnduser())) {
                            csExpdetailVO.setPriceEnduser(rcvdetail.getPriceEnduser().toString());
                        }
                    } else {
                        csExpdetailVO.setStatus(2);
                    }
                    csExpdetailVO.setCproductNo(param.getList().get(0).getCproductNo());
                    csExpdetailVO.setInventoryId(opsWTInventoryDTO.getInventoryId());
                    csExpdetailVO.setInventoryTypeCode(opsWTInventoryDTO.getInventoryType());
                    csExpdetailVO.setDoId(param.getOpsDo().getDoId());
                    if (DoSourceEnum.ASSModelNo.getType().equals(param.getOpsDo().getDoSource())) {
                        csExpdetailVO.setItemType(2);
                        // item_type=2
                    } else {
                        // item_type=0
                        csExpdetailVO.setItemType(0);
                    }
                    list.add(csExpdetailVO);
                }
            }
            //收集pco
            for (OpsPcoItem opsPcoItem : pcoObj.getList()) {
                //委托在库调用接口
                OpsPcoItemInventoryExample examplePcoItemInv = new OpsPcoItemInventoryExample();
                OpsPcoItemInventoryExample.Criteria cri = examplePcoItemInv.createCriteria();
                cri.andPcoIdEqualTo(pcoObj.getOpsPco().getPcoId());
                cri.andPcoItemEqualTo(opsPcoItem.getPcoItem());
                cri.andDelflagEqualTo(0);//删除标
                List<OpsPcoItemInventory> pcoItemInventory = pcoItemInventoryMapper.selectByExample(examplePcoItemInv);
                List<OpsWTInventoryDTO> listPcoDto = new ArrayList<OpsWTInventoryDTO>();
                for (OpsPcoItemInventory itemInventory : pcoItemInventory) {
                    InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                    InventoryDTO dto = null;
                    if (InventoryTableTypeEnum.NORMAL.equals(tableTypeEnum)) {
                        OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                        if (ObjectUtil.isNotNull(inventory)) {
                            dto = new InventoryDTO(inventory);
                        }
                    }
                    if (InventoryTableTypeEnum.TRANS.equals(tableTypeEnum)) {
                        OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(itemInventory.getInventoryId());
                        if (ObjectUtil.isNotNull(inventory)) {
                            dto = new InventoryDTO(inventory);
                        }
                    }
                    OpsInventoryProperty property = opsInventoryPropertyMapper.selectByPrimaryKey(dto.getInventoryPropertyId());
                    OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                    obj.setInventoryId(itemInventory.getInventoryId());
                    obj.setInventoryType(property.getInventoryTypeCode());
                    listPcoDto.add(obj);
                }
                OpsDoExample exaDO = new OpsDoExample();
                exaDO.createCriteria().andOrderIdEqualTo(pcoObj.getOpsPco().getOrderId()).andOrderItemEqualTo(pcoObj.getOpsPco().getOrderItem()).andDelflagEqualTo(0);
                List<OpsDo> doList = opsDoMapper.selectByExample(exaDO);
                if (CollectionUtils.isEmpty(listPcoDto)) {
                    throw Exceptions.OpsException("加工单下发无成品交易单");
                }
                OpsDoItemExample exaDoItem = new OpsDoItemExample();
                exaDoItem.createCriteria().andDoIdEqualTo(doList.get(0).getDoId()).andDelflagEqualTo(0);
                List<OpsDoItem> opsDoItemList = opsDoItemMapper.selectByExample(exaDoItem);
                if (CollectionUtils.isNotEmpty(listPcoDto)) {
                    for (OpsWTInventoryDTO opsWTInventoryDTO : listPcoDto) {
                        CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
                        csExpdetailVO.setExpOrderNo(pcoObj.getOpsPco().getOrderId() + "-" + pcoObj.getOpsPco().getOrderItem() + "-" + opsPcoItem.getPcoItem());
                        csExpdetailVO.setModelNo(opsPcoItem.getSubModelno());
                        csExpdetailVO.setExpQty(opsPcoItem.getSubQty());
                        csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                        csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                        csExpdetailVO.setWarehouseCode(opsWarehouse.getWarehouseCode());
                        csExpdetailVO.setAgentNo(opsWarehouse.getCustomerNo());
                        csExpdetailVO.setUserNo(pcoObj.getOpsPco().getUserNo());
                        csExpdetailVO.setCorderNo(rcvdetail.getCorderNo());
                        csExpdetailVO.setPrice(rcvdetail.getPrice());
                        csExpdetailVO.setPplNo(rcvdetail.getPplNo());
                        csExpdetailVO.setProjectNo(rcvdetail.getProjectNo());
                        csExpdetailVO.setCproductNo(rcvdetail.getCproductNo());
                        csExpdetailVO.setInventoryId(opsWTInventoryDTO.getInventoryId());
                        csExpdetailVO.setInventoryTypeCode(opsWTInventoryDTO.getInventoryType());
                        csExpdetailVO.setDoId(doList.get(0).getDoId() + "-" + opsDoItemList.get(0).getQty());
                        //item_type=1
                        csExpdetailVO.setItemType(1);
                        csExpdetailVO.setSalesModelNo(opsDoItemList.get(0).getModelno());
                        list.add(csExpdetailVO);
                    }
                }
            }
            if (CollectionUtils.isEmpty(list)) {
                return CommonResult.failure("无库存关联关系");
            }
            Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));
            if (Objects.nonNull(flag) && flag) {
                return CommonResult.success();
            } else {
                return CommonResult.failure("存表失败");
            }
        } catch (Exception wmsResult) {
            if (wmsResult.getMessage().length() > 200) {
                return CommonResult.failure(wmsResult.getMessage().substring(0, 200));
            } else {
                return CommonResult.failure(wmsResult.getMessage());
            }
        }
    }


}

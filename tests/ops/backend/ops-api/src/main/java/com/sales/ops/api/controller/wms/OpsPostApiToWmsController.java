package com.sales.ops.api.controller.wms;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.sales.ops.api.annotation.Log;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.dto.flux.*;
import com.sales.ops.api.service.wms.OpsPostApiToWmsService;
import com.sales.ops.dto.inventory.InventoryDTO;
import com.sales.ops.dto.inventory.WmRoBarcodeDto;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OPSWarehouseFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.smc.smccloud.model.csstock.CsExpdetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：OpsPost数据到WMS OPS提交数据大wms ops调用wms
 * <p>
 * 4.2.	BOM组件资料下发
 * 4.3.	预期到货通知单（采购入库/仓间调拨/退货入库/盘点差异调账）下发
 * 4.4.	入库对应箱号/序列号下发
 * 4.5.	预期到货通知（采购入库/仓间调拨/退货入库/盘点差异调账）取消(整单)
 * 4.6.	销售/发运订单/加工单/盘点差异调账下发
 * 4.7.	销售/发运订单/加工单/盘点差异调账取消（整单）
 * 4.9.	收货人地址变更
 * @date ：Created in 2021/10/27 16:49
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/OpsPostApiToWms")
@Slf4j
public class OpsPostApiToWmsController {
    @Autowired
    private OpsPostApiToWmsService opsPostApiToWmsService;
    @Autowired
    private com.sales.ops.api.conf.opsApiConfig opsApiConfig;
    @Autowired
    private OPSWarehouseFeignApi oPSWarehouseFeignApi;

    @Autowired
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Autowired
    private OpsWmFeignApi opsWmFeignApi;

    @Resource
    private RcvdetailMapper rcvdetailMapper;

    @Autowired
    private OPSWarehouseDao opsWarehouseDao;

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
    private OpsDoMapper opsDoMapper;

    @Resource
    private OpsDoItemMapper opsDoItemMapper;


    /**
     * bugid:11758 20230814 c14717 完纳问询
     * @param params
     * @return
     */
    @RequestMapping("/do/askFinish")
    public CommonResult<String> askFinish(@RequestBody List<FinishOrderWmsReqDto> params) {
        try {
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.askFinishDo(params);
            //完纳问询接口
            if (searchResult.isSuccess()) {
                String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=SO_CLOSE1&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025";
                return opsPostApiToWmsService.opsToWmsReData(searchResult.getData(), url);
                //return opsPostApiToWmsService.checkTempWmsApi(params);
            }else {
                return CommonResult.failure("问询完纳组装报文失败");
            }
        }catch (OpsException ex) {
            log.error("问询完纳",ex);
            return CommonResult.failure(ex.getMessage());
        }
        catch (Exception ex) {
            log.error("问询完纳",ex);
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * bugid:11758 20230814 c14717
     * @param params
     * @return
     */
    @RequestMapping("/do/exeFinish")
    public CommonResult<String> exeFinish(@RequestBody List<FinishOrderWmsReqDto> params) {
        try {
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.askFinishDo(params);
            if (searchResult.isSuccess()) {
                // 完纳接口
                String url = opsApiConfig.getWmsApi() + "FluxWmsOpsJsonApi/?method=SO_CLOSE2&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025";
                return opsPostApiToWmsService.opsToWmsReData(searchResult.getData(), url);
                //return opsPostApiToWmsService.checkTempWmsApi(params);
            }else {
                return CommonResult.failure("执行完纳组装报文失败");
            }
        }catch (OpsException ex) {
            log.error("执行完纳",ex);
            return CommonResult.failure(ex.getMessage());
        }
        catch (Exception ex) {
            log.error("执行完纳",ex);
            return CommonResult.failure(ex.getMessage());
        }
    }



    /**
     * ops提交bom和bomDetail
     * 4.2 BOM组件资料下发
     *
     * @param
     * @return
     */
    /*@Log(apiName = "4.2 BOM组件资料下发", type = "BOM")*/
    @RequestMapping("/do/updateBomToWms")
    public CommonResult<String> updateBomToWms(@RequestBody ProductBom param) {
        try {
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateBomToWms(param);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSkuBom&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return opsPostApiToWmsService.updateOpsBomStatus("bom", param.getBomid() + "", true, "成功");
                }
                if (wmsResult.getMessage().length() > 200) {
                    return opsPostApiToWmsService.updateOpsBomStatus("bom", param.getBomid() + "", false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    return opsPostApiToWmsService.updateOpsBomStatus("bom", param.getBomid() + "", false, wmsResult.getMessage());//失败原因去日志表查
                }
            }
            return CommonResult.failure("ops无bom数据");
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /*@Log(apiName = "5.11 BOM信息拉取接口", type = "wms_call_BOM")*/
    @RequestMapping("/do/wmsGetBom")
    public CommonResult<JSONObject> updateBomToWms(@RequestParam("bomId") String bomId, @RequestParam("modelNo") String modelNo) {
        try {
            //查询
            JSONObject obj = opsPostApiToWmsService.updateBomToWms(bomId, modelNo);
            if (null != obj) {
                return CommonResult.success(obj);
            } else {
                return CommonResult.failure("无数据");
            }
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /*@Log(apiName = "5.11 BOM信息拉取接口Post", type = "wms_call_BOM_POST")*/
    @PostMapping("/post/do/wmsGetBom")
    public CommonResult<JSONObject> postBomToWms(@RequestBody JSONObject jsonObject) {

        try {
            String bomId = (String) jsonObject.get("bomId");
            String modelNo = (String)jsonObject.get("modelNo");
            //查询
            JSONObject obj = opsPostApiToWmsService.updateBomToWms(bomId, modelNo);
            if (null != obj) {
                return CommonResult.success(obj);
            } else {
                return CommonResult.failure("无数据");
            }
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 4.3.	预期到货通知单接口
     * ops提交wmsRO和ROitem
     *@Log(apiName = "4.3.预期到货通知单接口", type = "RO")
     * @param param
     * @return
     */

    @RequestMapping("/ro/updateRoToWms")
    public CommonResult<String> updateRoToWms(@RequestBody OpsRoAddItemDto param) {
        try {
            //查询是否是委托在库，如果是走委托在库流程
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(param.getRo().getWarehouseCode());
            if (Objects.nonNull(opsWarehouse)) {
                if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                    //return opsPostApiToWmsService.updateOpsROStatus(param.getOpsWmOrderTaskId(),"ro", param.getRo().getId() + "", param.getRo().getRoId(), false, "委托在库不下发wms");//失败原因去日志表查
                    //begin bug:9085 下发委托在库 更新 task.flag=1 b28029 2023-01-11
                    return opsPostApiToWmsService.updateOpsROStatusToWT(param.getRo().getId(), param.getRo().getRoId(), "委托在库不下发wms");
                    //end bug:9085
                }
            }

            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateRoToWms(param);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putASN&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return opsPostApiToWmsService.updateOpsROStatus(param.getOpsWmOrderTaskId(),"ro", param.getRo().getId() + "", param.getRo().getRoId(), true, "成功");
                }
                if (wmsResult.getMessage().length() > 200) {
                    return opsPostApiToWmsService.updateOpsROStatus(param.getOpsWmOrderTaskId(),"ro", param.getRo().getId() + "", param.getRo().getRoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    return opsPostApiToWmsService.updateOpsROStatus(param.getOpsWmOrderTaskId(),"ro", param.getRo().getId() + "", param.getRo().getRoId(), false, wmsResult.getMessage());//失败原因去日志表查
                }
            }
            return CommonResult.failure("opsRo数据" + searchResult.getMessage());
        } catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 无记录变更状态
     *@Log(apiName = "无记录变更状态", type = "status")
     * @return
     */

    @RequestMapping("/updateRoStatus")
    public CommonResult<String> updateOpsWmOrderTaskStatus(@RequestParam("wmOrderTaskId") Long wmOrderTaskId,@RequestParam("wmOrderType") String wmOrderType, @RequestParam("taskType")
            String taskType, @RequestParam("wmOrderId") String wmOrderId, @RequestParam("msg") String msg) {
        try {
            return opsPostApiToWmsService.updateOpsWmOrderTaskStatus(wmOrderTaskId,wmOrderType, taskType, wmOrderId, msg);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 4.4.	入库对应箱号/序列号下发
     * ops提交BarCode
     *@Log(apiName = "4.4.入库对应箱号/序列号下发", type = "BARCODE")
     * @param
     * @return
     */

    @RequestMapping("/ro/updateRoBarCodeToWms")
    public CommonResult<String> updateRoBarCodeToWms(@RequestBody WmRoBarcodeDto wmObj) {
        try {
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateRoBarCodeToWms(wmObj.getBarCodelist());
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putASNSerialno&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return opsPostApiToWmsService.updateOpsBARCODEStatus(wmObj.getWmOrderTaskId(),"barcode", wmObj.getBarCodelist().get(0).getRoId() + "", true, "成功");
                }
                if (wmsResult.getMessage().length() > 200) {
                    return opsPostApiToWmsService.updateOpsBARCODEStatus(wmObj.getWmOrderTaskId(),"barcode", wmObj.getBarCodelist().get(0).getRoId() + "", false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    return opsPostApiToWmsService.updateOpsBARCODEStatus(wmObj.getWmOrderTaskId(),"barcode", wmObj.getBarCodelist().get(0).getRoId() + "", false, wmsResult.getMessage());//失败原因去日志表查
                }
            }
            return CommonResult.failure("无数据");
        } catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }

    /**
     * 4.5.	预期到货通知（采购入库/仓间调拨/退货入库/盘点差异调账）  取消(整单)
     *@Log(apiName = "4.5.预期到货通知取消(整单)", type = "RO取消")
     * @param param
     * @return
     */

    @RequestMapping("/cancelDocAsn")
    public CommonResult<String> cancelDocAsn(@RequestBody CancelDocAsnDto param) {
        try {
            return opsPostApiToWmsService.cancelDocAsn(param);
        } catch (Exception ex) {
            log.error("指令取消",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }


    /**
     * bugid:14135 c14717 2024-5-9 取消ro
     * @param param
     * @return
     */
    @RequestMapping("/cancelDocAsnV2")
    public CommonResult<String> cancelDocAsnV2(@RequestBody CancelDocAsnV2Dto param) {
        try {
            return opsPostApiToWmsService.cancelDocAsnV2(param);
        } catch (Exception ex) {
            log.error("指令取消",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }
    /**
     * 下发do指令 用于首次下发 更新状态
     * 4.6.	销售/发运订单/加工单/盘点差异调账下发
     *@Log(apiName = "4.6.销售发运订单", type = "DO")
     * @param param
     * @return
     */

    @RequestMapping("/do/updateDoToWms")
    public CommonResult<String> updateDoToWms(@RequestBody OpsDoAndItemDto param) {
        try {
            //查询是否是委托在库，如果是走委托在库流程
            //CommonResult<OpsWarehouse> result = oPSWarehouseFeignApi.searchWarehouse(param.getOpsDo().getWarehouseCode());
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(param.getOpsDo().getWarehouseCode());
            if (Objects.nonNull(opsWarehouse)) {
                //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
                if (DoStatusEnum.PART.getStatus().equals(param.getOpsDo().getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(param.getOpsDo().getDoStatus())) {
                    return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, "已发运");//失败原因去日志表查
                }
                if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                    //CommonResult<Rcvdetail> resultRcvdetail = opsWmDispatchForOrderFeignApi.getRcvdetail(param.getOpsDo().getOrderId(),Integer.parseInt(param.getOpsDo().getOrderItem()));
                    Rcvdetail rcvdetail = null;
                    if(DoTypeEnum.TKCK.getType().equals(param.getOpsDo().getDoType())){
                        return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库退货不写入csExpdetailVO");//失败原因去日志表查

                    }else{
                        RcvdetailExample example = new RcvdetailExample();
                        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(param.getOpsDo().getOrderId()).andRorderItemEqualTo(Integer.valueOf(param.getOpsDo().getOrderItem()));
                        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
                        if (rcvdetailList.size() == 1) {
                            rcvdetail =  rcvdetailList.get(0);
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

                        if(CollectionUtils.isNotEmpty(listDto)){
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
                                   // item_type=2
                               }else {
                                   // item_type=0
                                   csExpdetailVO.setItemType(0);
                               }
                               list.add(csExpdetailVO);
                           }
                        }
                        if(CollectionUtils.isEmpty(list)){
                            return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库加工单");//失败原因去日志表查
                        }
                        Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));
                        if(Objects.nonNull(flag) && flag){
                            return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "委托在库成功");//失败原因去日志表查
                        }else {
                            return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, "存表失败");//失败原因去日志表查
                        }
                    } catch (Exception wmsResult) {
                        if (wmsResult.getMessage().length() > 200) {
                            return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                        } else {
                            return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage());//失败原因去日志表查
                        }
                    }

                }
            }else {
                return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, "失败rcvdetail");//失败原因去日志表查
            }
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateDoToWms(param);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "成功");
                }else {
                    //19728 c14717 20251028
                    if(StringUtil.isNotEmpty(wmsResult.getMessage())
                            && wmsResult.getMessage().contains("记录已存在 不允许修改")
                            && param.getOpsDo().getIsWms() == 0){
                        return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), true, "wms记录已存在，更新do_iswms");//失败原因去日志表查
                    }

                    if (wmsResult.getMessage().length() > 200) {
                        return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                    } else {
                        return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, wmsResult.getMessage());//失败原因去日志表查
                    }
                }
            }else {
                if (searchResult.getMessage().length() > 200) {
                    return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, searchResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    return opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, searchResult.getMessage());//失败原因去日志表查
                }
            }
            //return CommonResult.failure("无数据");
        }catch (OpsException ex) {
            log.error("指令下发",ex);
            try {
                opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, ex.getMessage());//失败原因去日志表查
            } catch (OpsException e) {
                return CommonResult.failure(ex.getMessage());
            }
            return CommonResult.failure(ex.getMessage());
        }
        catch (Exception ex) {
            log.error("指令下发",ex);
            try {
                opsPostApiToWmsService.updateOpsDOStatus(param.getWmOrderTaskId(),"do", param.getOpsDo().getId() + "", param.getOpsDo().getDoId(), false, "系统异常");//失败原因去日志表查
            } catch (OpsException e) {
                e.printStackTrace();
            }
            return CommonResult.failure(ex.getMessage());
        }
    }


    /**
     * bugids:12187 c14717 20230921
     * 下发do指令 用于二次下发 不更新状态
     * 4.6.	销售/发运订单/加工单/盘点差异调账下发
     *@Log(apiName = "4.6.销售发运订单", type = "DO")
     * @param param
     * @return
     */

    @RequestMapping("/do/updateDoToWmsNew")
    public CommonResult<String> updateDoToWmsNew(@RequestBody OpsDoAndItemDto param) {
        try {
            //查询是否是委托在库，如果是走委托在库流程
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(param.getOpsDo().getWarehouseCode());
            if (Objects.nonNull(opsWarehouse)) {
                if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                    return opsPostApiToWmsService.postWTdo(param,opsWarehouse);
                }
            }else {
                return CommonResult.failure("获取仓库失败");
            }
            //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
            if (DoStatusEnum.PART.getStatus().equals(param.getOpsDo().getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(param.getOpsDo().getDoStatus())) {
                return CommonResult.failure("物流已发运");
            }

            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateDoToWms(param);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult<String> wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return CommonResult.success();
                }else {
                    if (searchResult.getMessage().length() > 200) {
                        return CommonResult.failure(wmsResult.getMessage().substring(0, 200));
                    } else {
                        return CommonResult.failure(wmsResult.getMessage());
                    }
                }
            }else {
                if (searchResult.getMessage().length() > 200) {
                    return CommonResult.failure(searchResult.getMessage().substring(0, 200));
                } else {
                    return CommonResult.failure(searchResult.getMessage());
                }
            }
            //return CommonResult.failure("无数据");
        }catch (OpsException ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(ex.getMessage());
        }
        catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure("系统异常");
        }
    }



    /**
     * do批量下发wms 排除委托在库
     * @param //用于订单地址修改 不更新状态
     * @return
     */
    @RequestMapping("/do/updateDoToWmsBatch")
    public CommonResult<String> updateDoToWmsBatch(@RequestBody List<OpsDoAndItemDto> params) {
        try {
            //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
            for(OpsDoAndItemDto param : params) {
                if (DoStatusEnum.PART.getStatus().equals(param.getOpsDo().getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(param.getOpsDo().getDoStatus())) {
                    return CommonResult.failure("物流已发运");
                }
            }

            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updateDoToWmsBatch(params);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                return opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
            }else {
                return CommonResult.failure("组装报文失败");
            }
        }catch (OpsException ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(ex.getMessage());
        }
        catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(ex.getMessage());
        }
    }



    /**
     *
     * 4.6 ops提交wmsPco和Pcoitem
     * 下发pco指令 用于首次下发 停用，仅针对pco =0
     *@Log(apiName = "4.6.加工单", type = "PCO")
     * @param param
     * @return
     */

    @RequestMapping("/pco/updatePcoToWms")
    public CommonResult<String> updatePcoToWms(@RequestBody OpsPcoAddItemDto param) {
        try {
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(param.getOpsPco().getWarehouseCode());

            if (Objects.nonNull(opsWarehouse)) {
                //bugid:13871 c14717 20240401 下发时查询do status的状态，如果是2或者3的情况，不进行下发处理。
                if (DoStatusEnum.PART.getStatus().equals(param.getOpsPco().getPcoStatus()) || DoStatusEnum.FINISH.getStatus().equals(param.getOpsPco().getPcoStatus())) {
                    return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, "加工完成");//失败原因去日志表查
                }

                if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {

                        Rcvdetail rcvdetail = null;
                        RcvdetailExample example = new RcvdetailExample();
                        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(param.getOpsPco().getOrderId()).andRorderItemEqualTo(Integer.valueOf(param.getOpsPco().getOrderItem()));
                        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
                        if (rcvdetailList.size() == 1) {
                            rcvdetail =  rcvdetailList.get(0);
                        } else {
                            throw Exceptions.OpsException("查询出" + rcvdetailList.size() + "条RcvDetail");
                        }
                        if(Objects.nonNull(rcvdetail)){
                            try {
                                List<CsExpdetailVO> list = new ArrayList<>();
                                for (OpsPcoItem opsPcoItem : param.getList()){
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
                                    //委托在库无调拨单
                                    OpsDoExample exaDO = new OpsDoExample();
                                    exaDO.createCriteria().andOrderIdEqualTo(param.getOpsPco().getOrderId()).andOrderItemEqualTo(param.getOpsPco().getOrderItem()).andDelflagEqualTo(0);
                                    List<OpsDo> doList = opsDoMapper.selectByExample(exaDO);
                                    if(CollectionUtils.isEmpty(listDto)){
                                        throw Exceptions.OpsException("加工单下发无成品交易单");
                                    }
                                    OpsDoItemExample exaDoItem = new OpsDoItemExample();
                                    exaDoItem.createCriteria().andDoIdEqualTo(doList.get(0).getDoId()).andDelflagEqualTo(0);
                                    List<OpsDoItem> opsDoItemList= opsDoItemMapper.selectByExample(exaDoItem);
                                    if(CollectionUtils.isNotEmpty(listDto)){
                                        for(OpsWTInventoryDTO opsWTInventoryDTO : listDto){
                                            CsExpdetailVO csExpdetailVO = new CsExpdetailVO();
                                            csExpdetailVO.setExpOrderNo(param.getOpsPco().getOrderId()+"-"+param.getOpsPco().getOrderItem()+"-"+opsPcoItem.getPcoItem());
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
                                            csExpdetailVO.setDoId(doList.get(0).getDoId()+"-"+opsDoItemList.get(0).getQty());
                                            //item_type=1
                                            csExpdetailVO.setItemType(1);
                                            csExpdetailVO.setSalesModelNo(opsDoItemList.get(0).getModelno());
                                            list.add(csExpdetailVO);
                                        }
                                    }
                                }

                               Boolean flag = opsWmFeignApi.addExpData(JSONArray.parseArray(JSON.toJSONString(list)));

                               if(Objects.nonNull(flag) && flag){
                                   return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), true, "成功");

                               }else {
                                   return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, "失败");

                               }

                        } catch (Exception wmsResult) {
                            if (wmsResult.getMessage().length() > 200) {
                                return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                            } else {
                                return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage());//失败原因去日志表查
                            }
                        }
                    }else {
                        return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, "失败rcvdetail");//失败原因去日志表查
                    }
                }
            }else{
                return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, "失败rcvdetail");//失败原因去日志表查
            }
            OpsDoExample exaDO = new OpsDoExample();
            exaDO.createCriteria().andOrderIdEqualTo(param.getOpsPco().getOrderId())
                    .andOrderItemEqualTo(param.getOpsPco().getOrderItem())
                    .andDelflagEqualTo(0).andDoTypeEqualTo(DoTypeEnum.JYCK.getType());
            List<OpsDo> doList = opsDoMapper.selectByExample(exaDO);
            if(CollectionUtils.isNotEmpty(doList)){
                param.setDoId(doList.get(0).getDoId());
            }
            //查询
            CommonResult<JSONObject> searchResult = opsPostApiToWmsService.updatePcoToWms(param);
            if (searchResult.isSuccess()) {
                //调用富勒
                String url = opsApiConfig.getWmsApi() + "FluxWmsJsonApi/?method=putSalesOrder&apptoken=80AC1A3F-F949-492C-A024-7044B28C8025&timestamp=2021-11-09 10:00:00&sign=test&format=json";
                CommonResult wmsResult = opsPostApiToWmsService.opsToWms(searchResult.getData(), url);
                if (wmsResult.isSuccess()) {
                    //更新
                    return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), true, "成功");
                }
                // bugid:19278 20251028
                if(StringUtil.isNotEmpty(wmsResult.getMessage())
                        && wmsResult.getMessage().contains("记录已存在 不允许修改")
                        && param.getOpsPco().getIsWms() == 0){
                    return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), true,"wms记录已存在，更新pco_iswms");//失败原因去日志表查
                }

                if (wmsResult.getMessage().length() > 200) {
                    return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage().substring(0, 200));//失败原因去日志表查
                } else {
                    return opsPostApiToWmsService.updateOpsPCOStatus(param.getWmOrderTaskId(),"pco", param.getOpsPco().getId() + "", param.getOpsPco().getPcoId(), false, wmsResult.getMessage());//失败原因去日志表查
                }
            }
            return CommonResult.failure("无数据");
        } catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }


    /**
     * 4.7.	销售/发运订单/加工单/盘点差异调账取消（整单）
     *
     * @param param
     * @return
     */
    @Log(apiName = "4.7.销售/发运订单/加工单/盘点差异调账取消", type = "cancelOrder")
    @RequestMapping("/cancelDocOrder")
    public CommonResult<List<JSONObject>> cancelDocOrder(@RequestBody List<CancelDocOrderDto> param) {
        try {
            //return opsPostApiToWmsService.cancelDocOrder(param);
            //bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
            return opsPostApiToWmsService.cancelDocOrderNew(param);

        } catch (Exception ex) {
            log.error("指令取消",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }

    /**
     * 4.7.	销售/发运订单/加工单/盘点差异调账取消（整单）
     * bugid:12714 c14717 2023-11-27
     * @param param
     * @return
     */
    @Log(apiName = "4.7.销售/发运订单/加工单/盘点差异调账取消", type = "cancelDocOrderV2")
    @RequestMapping("/cancelDocOrderV2")
    public CommonResult<List<JSONObject>> cancelDocOrderV2(@RequestBody List<CancelDocOrderV2Dto> param) {
        try {
            return opsPostApiToWmsService.cancelDocOrderV2(param);
        } catch (Exception ex) {
            log.error("指令取消",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }

    /**
     * @Log(apiName = "4.9.收货人地址变更 承运商", type = "地址变更")
     * 4.9.	收货人地址变更 承运商
     * @param param
     * @return
     */

    @RequestMapping("/changeConsigneeAddress")
    public CommonResult<String> changeConsigneeAddress(@RequestBody ChangeConsigneeAddressDto param) {
        try {
            return opsPostApiToWmsService.changeConsigneeAddress(param);
        } catch (Exception ex) {
            log.error("地址变更",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }

    /**
     *@Log(apiName = "下发交易出库和加工单", type = "DoAndPco")
     * 指令下发 更新状态 用于首次下发
     * @param param
     * @return
     */

    @PostMapping("/updateDoPcoToWmsOrToWT")
    public CommonResult<String> updateDoPcoToWmsOrToWT(@RequestBody OpsSendPcoAndDoMidDto param) {
        try {
            return opsPostApiToWmsService.updateDoPcoToWmsOrToWT(param);
        } catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }


    /**
     * bugid:12187 c14717 20230921
     * 指令下发 不更状态 用于二次下发 doand pco
     * @param param
     * @return
     */
    @PostMapping("/postWmsDoAndPcoNew")
    public CommonResult<String> postWmsDoAndPcoNew(@RequestBody OpsSendPcoAndDoMidDto param) {
        try {
            return opsPostApiToWmsService.postWmsDoAndPcoNew(param);
        } catch (Exception ex) {
            log.error("指令下发",ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }
}

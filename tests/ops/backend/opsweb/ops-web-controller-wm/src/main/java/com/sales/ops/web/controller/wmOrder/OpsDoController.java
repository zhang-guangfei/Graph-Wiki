package com.sales.ops.web.controller.wmOrder;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.AbnormalCreateDbMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.WmDoConfirmDto;
import com.sales.ops.dto.inventory.WmDoStatusDto;
import com.sales.ops.dto.inventory.WmWTDoConfirmDto;
import com.sales.ops.dto.order.DoWaveParam;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.StartWaveParam;
import com.sales.ops.dto.query.OpsDeliveryOrderQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import com.sales.ops.serviceimpl.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description
 * @date 2021/9/23 12:05
 * @auther C12961
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/warehouseManage/")
public class OpsDoController {

    @Resource
    private OpsDoService opsDoService;
    @Resource
    private BaseDoService baseDoService;

    @Autowired
    private OpsRedissonLock opsRedissonLock;

    @Autowired
    private AbnormalCreateDbMapper abnormalCreateDbMapper;

    @Autowired
    private WmOrderTaskService wmOrderTaskService;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    @PostMapping("do/search")
    public CommonResult search(@RequestBody PageModel<OpsDeliveryOrderQO> pageModel) {
        try {
            PageInfo<OpsDo> result = opsDoService.searchDoByPage(pageModel);
            return result.getList().isEmpty() ?
                    CommonResult.success("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    @PostMapping("do_item/search")
    public CommonResult search(@RequestBody String id) {
        try {
            OpsDoItem result = baseDoService.getDoItemByDoId(id);
            return result == null ?
                    CommonResult.success("没有记录") : CommonResult.success(Collections.singletonList(result));
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * ops提交wmsDO和DOitem
     * bugid:12654 加锁 c14717 20231121
     */
    @PostMapping("do/findDoToWms")
    public CommonResult<OpsDoAndItemDto> findDoToWms(@RequestBody String odId) {
        boolean lock = false;
        String key = "";
        try {
            OpsDoAndItemDto result = opsDoService.findDoToWms(odId);
            if(Objects.nonNull(result)){
                key = "ops:order:key:" + result.getOpsDo().getOrderId() ;
                lock = opsRedissonLock.addLock(key);
            }
            return result == null ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
    }

    /**
     * ops提交wmsDO和DOitem
     * bugid:12654 加锁 c14717 20231121
     * //bugid:12601 12836 c14717 20231212
     */
    @PostMapping("do/handPostDo")
    public CommonResult<OpsDoAndItemDto> handPostDo(@RequestBody OpsWmOrderTask opsWmOrderTask) {
        boolean lock = false;
        String key = "";
        try {
            OpsDo doByDoId = baseDoService.getDoByDoId(opsWmOrderTask.getWmOrderId());
            if(Objects.nonNull(doByDoId)){
                key = "ops:order:key:" +doByDoId.getOrderId() ;
                lock = opsRedissonLock.addLock(key);
                OpsDoItem doItem = baseDoService.getDoItemByDoId(doByDoId.getDoId());
                OpsDoAndItemDto opsDoAndItemDto = new OpsDoAndItemDto();
                opsDoAndItemDto.setOpsDo(doByDoId);
                if(Objects.nonNull(doItem)){
                    List<OpsDoItem> itemList = new ArrayList<>();
                    itemList.add(doItem);
                    opsDoAndItemDto.setList(itemList);
                } else {
                    wmOrderTaskService.updateWmsOrderTaskFailureById(opsWmOrderTask.getId(),"无指令");
                    return CommonResult.failure("没有记录");
                }
                opsDoAndItemDto.setWmOrderTaskId(opsWmOrderTask.getId());
                //3.post to wms
                CommonResult<String> resWm = opsCallWmsFeignApi.updateDoToWms(opsDoAndItemDto);
                return CommonResult.success();
            }else {
                wmOrderTaskService.updateWmsOrderTaskFailureById(opsWmOrderTask.getId(),"无指令");
                return CommonResult.failure("没有记录");
            }
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
    }

    /**
     * 委托在库 出库发货确认接口
     */
    @PostMapping("do/wmWTDoConfirm")
    public CommonResult wmWTDoConfirm(@RequestBody WmWTDoConfirmDto param) {
        boolean lock = false;
        try {
            if(StringUtils.isEmpty(param.getDeliveryOrderCode())){
                return CommonResult.failure("失败，缺少物流指令");
            }
            lock = opsRedissonLock.addLock("ops:wmWTDoConfirm:" +param.getDeliveryOrderCode() );
            opsDoService.wmWTDoConfirm(param);
            return CommonResult.success("成功");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock("ops:wmWTDoConfirm:" +param.getDeliveryOrderCode());

            }
        }
    }


    /**
     * 5.3 出库发货确认接口 富勒
     */
    @PostMapping("do/wmDoConfirm")
    public CommonResult wmDoConfirm(@RequestBody WmDoConfirmDto param) {
        Boolean lock = opsRedissonLock.addLock("ops:wmDoComfirm:"+param.getDeliveryOrderCode());
        try {
            log.info("DoConfirm：{}",JSONUtil.toJsonStr(param));
            opsDoService.wmDoConfirm(param);
            return CommonResult.success("成功");
        } catch (OpsException e) {
            //这里记录失败日志
            if(Objects.nonNull(e.getArgs()[0])){
                String errStatus = e.getArgs()[0].toString();
                int qty = 0;
                if(Objects.nonNull(param.getItems())){
                    qty = param.getItems().getQty();
                }
                opsDoService.saveDOopsExceptionHandle("doConfirm",errStatus,0,
                        param.getExpressCodeChild(),param.toString(),e.getFullMessage(),
                        param.getDeliveryOrderCode(),"",qty
                        ,param.getWarehouseCode(),"","","","");

            }
            return CommonResult.failure(e.getFullMessage());
        }catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        } finally {
            opsRedissonLock.releaseLock("ops:wmDoComfirm:"+param.getDeliveryOrderCode());
        }
    }

    /**
     * 5.4 出库状态回传接口 富勒
     */
    @PostMapping("do/wmDoStatus")
    public CommonResult wmDoStatus(@RequestBody WmDoStatusDto param) {
        try {
            opsDoService.wmDoStatus(param);
            return CommonResult.success("成功");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        }
    }

    /**
     * 交易出库指令下发后，变更orderStatus表状态
     * @author C12961
     * @date 2022/3/5 13:26
     */
    @GetMapping("do/prepare")
    public CommonResult prepareOperation(@RequestParam String doId) {
        try {
            int result = opsDoService.prepareOperation(doId);
            if (result == 1) {
                return CommonResult.success("修改订单状态成功");
            }
            return CommonResult.failure("修改订单状态失败");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        }
    }

    /**
     * 物流开始出库
     *
     * @author C12961
     * @date 2022/3/5 10:01
     */
    @PostMapping("do/start")
    public CommonResult start(@RequestBody StartWaveParam startWaveParam) {
        try {
            opsDoService.startOperation(startWaveParam.getDoId(), startWaveParam.getWaveNo(), startWaveParam.getWaveDeliveryDate(), startWaveParam.getExpectedDeliveryDate());
            return CommonResult.success("物流开始出库");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        }
    }

    /**
     * 不调用
     */
    @PostMapping("do/startOperation")
    public CommonResult startOperation(@RequestBody DoWaveParam waveParam) {
        try {
            if (StringUtils.isEmpty(waveParam.getDoId())) {
                throw Exceptions.OpsException("物流指令号不能为空");
            }
            if (StringUtils.isEmpty(waveParam.getWaveNo())) {
                throw Exceptions.OpsException("波次号不能为空");
            }
            if (StringUtils.isEmpty(waveParam.getOptTime())) {
                throw Exceptions.OpsException("操作时间不能为空");
            }
            if (StringUtils.startsWith(waveParam.getDoId(),"PCO")) {
                return CommonResult.success("物流开始出库");
            }
            opsDoService.startOperation(waveParam.getDoId(), waveParam.getWaveNo(), waveParam.getOptTime(), waveParam.getOptTime());
            return CommonResult.success("物流开始出库");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        }
    }
    /**
     * 不调用
     */
    @SysLog("退波次")
    @PostMapping("do/releaseOperation")
    public CommonResult releaseOperation(@RequestBody DoWaveParam waveParam) {
        try {
            if (StringUtils.isEmpty(waveParam.getDoId())) {
                throw Exceptions.OpsException("物流指令号不能为空");
            }
            if (StringUtils.isEmpty(waveParam.getWaveNo())) {
                throw Exceptions.OpsException("波次号不能为空");
            }
            if (StringUtils.isEmpty(waveParam.getOptTime())) {
                throw Exceptions.OpsException("操作时间不能为空");
            }
            opsDoService.releaseOperation(waveParam.getDoId(), waveParam.getOptTime(), waveParam.getWaveNo());
            return CommonResult.success("物流释放作业");
        } catch (OpsException e) {
            return CommonResult.failure(e.getFullMessage());
        }
    }


    /**
     * 异常数据生成调拨单
     *
     * @author C14717
     * @date 2022/9/22 15:42
     */
    @GetMapping("do/handleAbnormalData")
    public CommonResult handleAbnormalData() {
        try {
            AbnormalCreateDbExample example = new AbnormalCreateDbExample();
            example.createCriteria().andHandFlagEqualTo(0);
            List<AbnormalCreateDb> list = abnormalCreateDbMapper.selectByExample(example);
            for(AbnormalCreateDb obj : list){
                try {
                    String dbDoId = opsDoService.handleAbnormalData(obj.getDoId(),obj.getPcoId(),obj.getPcoItem(),obj.getSignWarehouseCode(),obj.getRoId());
                    obj.setHandFlag(1);
                    obj.setHandMsg("成功");
                    obj.setCreateTime(new Date());
                    obj.setYyBatchid(dbDoId);
                    abnormalCreateDbMapper.updateByPrimaryKey(obj);
                } catch (OpsException e) {
                    obj.setHandFlag(2);
                    obj.setHandMsg(e.getFullMessage());
                    obj.setCreateTime(new Date());
                    abnormalCreateDbMapper.updateByPrimaryKey(obj);
                }
            }
            return CommonResult.success("整表处理完成");
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


}

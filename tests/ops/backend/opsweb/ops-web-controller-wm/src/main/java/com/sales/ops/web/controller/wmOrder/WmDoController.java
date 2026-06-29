package com.sales.ops.web.controller.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WmOrderTaskEnum;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.wmOrder.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：货齐更改下发wms状态
 * @date ：Created in 2022/3/5 13:18
 */
@CrossOrigin
@RestController
@RequestMapping("/wmDo")
@Slf4j
public class WmDoController {

    @Autowired
    private WmDoService wmDoService;

    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private OpsRedissonLock opsRedissonLock;

    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private WmOrderTaskService wmOrderTaskService;//下发富勒表

    @Autowired
    private WmOrderTaskFindService findOrderTaskService;//下发富勒表

    /**
     * 出库货齐判断 下发wms状态 出库单
     * @param num
     * @return
     */
    @GetMapping("/do")
    public CommonResult sendDoToWMSChangeStatus(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetail(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }

    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id one
     * @param num
     * @return
     */
    @GetMapping("/do/one")
    public CommonResult sendDoToWMSChangeStatusOne(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailOne(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }
    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id two
     * @param num
     * @return
     */
    @GetMapping("/do/two")
    public CommonResult sendDoToWMSChangeStatusTwo(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailTwo(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }
    /**
     * 出库货齐判断 下发wms状态 出库单 出库单 拆分id three
     * @param num
     * @return
     */
    @GetMapping("/do/three")
    public CommonResult sendDoToWMSChangeStatusThree(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailThree(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }
    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id Four
     * @param num
     * @return
     */
    @GetMapping("/do/four")
    public CommonResult sendDoToWMSChangeStatusFour(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailFour(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }
    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id Five
     * @param num
     * @return
     */
    @GetMapping("/do/five")
    public CommonResult sendDoToWMSChangeStatusFive(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理DO 和调拨
            List<OpsWmOrderTask> doResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailFive(num,WmOrderTaskEnum.DO,3);
            int returnSize = 0;
            returnSize = handDo(limit,returnSize,doResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (OpsException e) {
            log.error(null,e);
            return CommonResult.failure(e.getFullMessage());
        }
    }


    /**
     * bugid:12654 加锁 c14717 20231121
     * @param limit
     * @param returnSize
     * @param doResult
     * @return
     */
    private int handDo(int limit,int returnSize , List<OpsWmOrderTask> doResult) {
        for (OpsWmOrderTask opsWmOrderTask : doResult) {
            boolean lock = false;
            String key = "";
            try {
                OpsDo doByDoId = baseDoService.getDoByDoId(opsWmOrderTask.getWmOrderId());
                key = "ops:order:key:" + doByDoId.getOrderId();
                // 锁
                lock = opsRedissonLock.addLock(key);
                //校验重复数据
                OpsWmOrderTask checkTask = findOrderTaskService.searchOpsWmOrderTaskById(opsWmOrderTask.getId());
                if(checkTask.getFlag()==3){
                    int resultSize = wmDoService.sendDoToWMSChangeStatus(limit,opsWmOrderTask);
                    returnSize = resultSize + returnSize;
                }
            } catch (Exception e) {
                wmOrderTaskService.updateWmsOrderTaskFailureById(opsWmOrderTask.getId(),"更新下发wms状态失败");
                log.error("出库货齐判断 下发wms状态 加工单",e);
            }finally {
                // 释放锁
                if (lock) {
                    opsRedissonLock.releaseLock(key);
                }
            }
        }
        return returnSize;
    }

    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param num
     * @return
     */
    @GetMapping("/pco")
    public CommonResult sendPcoToWMSChangeStatus(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetail(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }

    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id one
     * @param num
     * @return
     */
    @GetMapping("/pco/one")
    public CommonResult sendPcoToWMSChangeStatusOne(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailOne(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }

    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id two
     * @param num
     * @return
     */
    @GetMapping("/pco/two")
    public CommonResult sendPcoToWMSChangeStatusTwo(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailTwo(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }
    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id three
     * @param num
     * @return
     */
    @GetMapping("/pco/three")
    public CommonResult sendPcoToWMSChangeStatusThree(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailThree(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }
    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id four
     * @param num
     * @return
     */
    @GetMapping("/pco/four")
    public CommonResult sendPcoToWMSChangeStatusFour(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailFour(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }
    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id one
     * @param num
     * @return
     */
    @GetMapping("/pco/five")
    public CommonResult sendPcoToWMSChangeStatusFive(@RequestParam String num) {
        try {
            //每次处理的条数
            int limit = Integer.parseInt(num);
            //处理PCO
            List<OpsWmOrderTask> pcoResult = findOrderTaskService.searchOpsWmOrderTaskByConditionDetailFive(num,WmOrderTaskEnum.PCO,3);
            int returnSize = 0;
            returnSize = handlePco(limit,returnSize,pcoResult);
            return CommonResult.success("成功:"+returnSize);
        } catch (Exception e) {
            log.error("出库货齐判断 下发wms状态 加工单",e);
            return CommonResult.failure("失败");
        }
    }

    /**
     * bugid:12654 加锁 c14717 20231121
     * @param limit
     * @param returnSize
     * @param pcoResult
     * @return
     */
    private int handlePco(int limit,int returnSize,List<OpsWmOrderTask> pcoResult){
        for (OpsWmOrderTask opsWmOrderTask : pcoResult) {
            boolean lock = false;
            String key = "";
            try {
                OpsPco pcoByPcoId = opsPcoService.getPcoByPcoId(opsWmOrderTask.getWmOrderId());
                key = "ops:order:key:" + pcoByPcoId.getOrderId();
                lock = opsRedissonLock.addLock(key);
                //校验重复数据
                OpsWmOrderTask checkTask = findOrderTaskService.searchOpsWmOrderTaskById(opsWmOrderTask.getId());
                if(checkTask.getFlag()==3){
                    int resultSize = wmDoService.sendPcoToWMSChangeStatus(limit,opsWmOrderTask);
                    returnSize = resultSize + returnSize;
                }

            } catch (Exception e) {
                wmOrderTaskService.updateWmsOrderTaskFailureById(opsWmOrderTask.getId(),"更新下发wms状态失败");
                log.error("出库货齐判断 下发wms状态 加工单",e);
            }finally {
                // 释放锁
                if (lock) {
                    opsRedissonLock.releaseLock(key);
                }
            }
        }
        return returnSize;
    }

}

package com.sales.ops.web.controller;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.entity.OpsPoDeliveryUnusual;
import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.extdao.PurchaseUnusualOrderDao;
import com.sales.ops.dto.purchase.UnusualQueryDto;
import com.sales.ops.dto.purchase.UnusualViewDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.feign.DeliveryPoFeignApi;
import com.sales.ops.service.deliver.PurchaseUnusualOrderService;
import com.sales.ops.webutil.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/purchase/unusual")
public class PurchaseUnusualController extends BaseController {

    @Autowired
    private PurchaseUnusualOrderService purchaseUnusualOrderService;
    @Autowired
    private PurchaseUnusualOrderDao purchaseUnusualOrderDao;
    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private DeliveryPoFeignApi deliveryPoFeignApi;

    public static final String JP = "JP0";
    public static final String CN = "CNC";

    @GetMapping("/handle/CN")
    public CommonResult handleUnusualFromCNC() {
        int count = purchaseUnusualOrderDao.countOpsPoUnusual(CN);
        if (count == 0) {
            return CommonResult.success("无可处理单据");
        }
        List<OpsPoDeliveryUnusual> unusualList = purchaseUnusualOrderDao.getOpsPoUnusual(CN);
        Map<Long, OpsPurchaseinvoice> map = new HashMap<>();
        for (OpsPoDeliveryUnusual unusual : unusualList) {
            try {
                purchaseUnusualOrderService.handleUnusualForCNC(unusual, map);
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
        //发送邮件
        if (CollectionUtils.isNotEmpty(unusualList) && CollectionUtils.isNotEmpty(map.keySet())) {
            purchaseUnusualOrderService.handUsualListForCNCEmail(unusualList, map);
        }
        return CommonResult.success();
    }


    /**
     * 采购异常信息处理
     * bugid： 14481 +16639
     *
     * @param num
     * @return
     */
    @GetMapping("/handle/JP")
    public CommonResult handleUnusualFromJP(@RequestParam(defaultValue = "1000") Integer num) {
        int count = purchaseUnusualOrderDao.countOpsPoUnusual(JP);
        if (count == 0) {
            return CommonResult.success();
        }
        //处理所有数据，一次处理1000条，避免一次查询太多导致数据库查询超时
        if (count <= num) {
            List<OpsPoDeliveryUnusual> list = purchaseUnusualOrderDao.getOpsPoUnusual(JP);
            purchaseUnusualOrderService.handleUnusualListForJP(list);
            return CommonResult.success(list);
        } else {
            //一共查多少页
            int maxPageNum = (count / num) + 1;
            List<List<OpsPoDeliveryUnusual>> listALl = new ArrayList<>();
            for (int i = 0; i < maxPageNum; i++) {
                List<OpsPoDeliveryUnusual> list = purchaseUnusualOrderDao.getOpsPoUnusualPage(JP, i, num);
                listALl.add(list);
            }
            for (List<OpsPoDeliveryUnusual> list : listALl) {
                purchaseUnusualOrderService.handleUnusualListForJP(list);
            }
            return CommonResult.success(listALl);
        }
    }


    @GetMapping("/handle")
    public CommonResult handleUnusualFromJP(Long id) {
        try {
            List<OpsPoDeliveryUnusual> list = purchaseUnusualOrderDao.getOpsPoUnusualById(id);
            purchaseUnusualOrderService.handleUnusualListForJP(list);
            return CommonResult.success(list);
        } catch (Exception e) {
            log.error("处理异常信息失败:", e);
            return CommonResult.failure(e.getMessage());
        }
    }


    @PostMapping("/export")
    public CommonResult export(@RequestBody UnusualQueryDto usualQueryDto) {
        List<UnusualViewDto> list = purchaseUnusualOrderService.exportUnusual(usualQueryDto);
        return CommonResult.success(list);
    }


    @PostMapping("/search")
    public CommonResult search(@RequestBody PageModel<UnusualQueryDto> usualQueryDto) {
        PageInfo<UnusualViewDto> pageInfo = purchaseUnusualOrderService.searchUnusual(usualQueryDto);
        return CommonResult.success(pageInfo);
    }

    @GetMapping("/update")
    public CommonResult updateById(@RequestParam Long id, @RequestParam(required = false) String username) {
        try {
            UserDto userDto = new UserDto(username);
            //getUserDto()方法总是获取到po-ms（原因未知），所以优先从前端获取username，如果前端没有，再从后端获取
            if(StringUtils.isBlank(username)){
                userDto = getUserDto();
            }
            purchaseUnusualOrderService.updateStatusByReplyJapan(id, userDto);
            return CommonResult.success();
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage(), "删除失败");
        }
    }

    @PostMapping("/delete")
    public CommonResult deleteByIds(@RequestBody List<Long> ids, @RequestParam(required = false) String username) {
        try {
            UserDto userDto = new UserDto(username);
            if(StringUtils.isBlank(username)){
                userDto = getUserDto();
            }
            purchaseUnusualOrderService.deleteUnusualByIds(ids, userDto);
            return CommonResult.success();
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage(), "删除失败");
        }
    }

    /**
     * 批量拉取采购异常订单信息
     *
     * @param num
     * @return
     */
    @GetMapping("/create")
    public CommonResult create(@RequestParam(defaultValue = "1000") Integer num) {
        Long maxId = null;
        String key = "ops:unusual:pull:maxId";
        Object obj = opsRedisUtils.get(key);
        if (obj != null) {
            maxId = new Long((Integer) obj);
        }
        if (maxId == null) {
            maxId = 0L;
        }
        //使用delivery提供的gps库查询方法
        //List<OpsPoUnusualOrderLog> logs = purchaseUnusualOrderDao.getUnusualOrderLogs(num, maxId, DateUtil.getMonthFirstDate(new Date()));
        CommonResult<List<OpsPoUnusualOrderLog>> result = deliveryPoFeignApi.getUnusualOrderLogs(num, maxId, DateUtil.stringToDate("2025-01-01"));
        if (!result.isSuccess()) {
            return CommonResult.failure("查询异常信息失败");
        }
        List<OpsPoUnusualOrderLog> logs = result.getData();
        if (CollectionUtils.isEmpty(logs)) {
            return CommonResult.success();
        }
        maxId = logs.get(logs.size() - 1).getId();
        opsRedisUtils.set(key, maxId);
        for (OpsPoUnusualOrderLog orderLog : logs) {
            try {
                purchaseUnusualOrderService.pullUnusualsFromLog(orderLog);
            } catch (OpsException e) {
                log.error("采购异常信息拉取失败 {}:{}", orderLog.getId(), e.getMessage());
            } catch (Exception e) {
                log.error("采购异常信息拉取失败 {}:{}", orderLog.getId(), e);
            }
        }
        return CommonResult.success();
    }

    /**
     * 采购异常信息拉取的简化版，根据源数据的id拉取单条数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/create/{id}")
    public CommonResult createById(@PathVariable Long id) {
        //OpsPoUnusualOrderLog orderLog = purchaseUnusualOrderDao.getUnusualOrderLogById(id);
        CommonResult<OpsPoUnusualOrderLog> result = deliveryPoFeignApi.getUnusualLogsById(id);
        if (!result.isSuccess()) {
            return CommonResult.failure("查询异常信息失败");
        }
        OpsPoUnusualOrderLog orderLog = result.getData();
        try {
            purchaseUnusualOrderService.pullUnusualsFromLog(orderLog);
        } catch (Exception e) {
            log.error("采购异常信息拉取失败 {}:{}", orderLog.getId(), e);
        }
        return CommonResult.success();
    }


}

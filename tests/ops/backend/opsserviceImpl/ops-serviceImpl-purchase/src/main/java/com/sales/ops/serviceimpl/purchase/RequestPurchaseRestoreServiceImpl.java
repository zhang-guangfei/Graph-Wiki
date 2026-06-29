package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import com.sales.ops.enums.RequestInterceptEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.purchase.RequestPurchaseRestoreService;
import com.sales.ops.service.purchase.RequestPurchaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequestPurchaseRestoreServiceImpl implements RequestPurchaseRestoreService {

    @Resource
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

    @Resource
    private RequestPurchaseService requestPurchaseService;

    @Override
    public String interceptRestore() throws Exception {
        // 完整清单
        List<OpsRequestpurchase> fullList = new ArrayList<>();
        // 单个查询条件清单
        List<OpsRequestpurchase> conditionList;
        OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
        // 取得需要重新计算的 拦截原因清单,循环处理
        List<String> restoreList = RequestInterceptEnum.restoreList();
        if (CollectionUtils.isNotEmpty(restoreList)) {
            for (String reason : restoreList) {
                conditionList = new ArrayList<>();
                example.clear();
                example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.LANJIE).andInterceptmsgLike("%"+ reason + "%");
                conditionList =  opsRequestpurchaseMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(conditionList)) {
                    if (CollectionUtils.isNotEmpty(fullList)) {
                        // 针对同时被多个原因拦截的订单，去除两个集合中相同的部分
                        conditionList.removeAll(fullList);
                        if (CollectionUtils.isEmpty(conditionList)) {
                            continue;
                        }
                    }
                    // 写入最终的集合
                    fullList.addAll(conditionList);
                }
            }
        }
        // 调用订单还原方法
        int result = 0;
        if (CollectionUtils.isNotEmpty(fullList)) {
            result =  requestPurchaseService.restoreBatch(fullList);
        }
        return "拦截订单自动还原成功，共计处理了"+result+"条订单";
    }
}

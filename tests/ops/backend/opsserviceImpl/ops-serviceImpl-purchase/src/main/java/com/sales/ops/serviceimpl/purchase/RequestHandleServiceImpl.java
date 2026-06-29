package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.service.purchase.RequestHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequestHandleServiceImpl implements RequestHandleService {

    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

    @Autowired
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    /**
     * bug 11997,手工拦截订单，不参与自动发单
     * @param list
     * @return
     */
    @Override
    public Integer requestIntercept(List<OpsRequestpurchase> list) throws OpsException {
        List<Long> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        OpsRequestpurchase request = new OpsRequestpurchase();
        request.setStatecode(RequestPurchaseStatusEnum.LANJIE);
        request.setUpdatetime(new Date());
        request.setOperator(list.get(0).getOperator());
        request.setInterceptmsg("请购订单暂不处理");
        OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
        int result = 0;
        List<Long> temp = new ArrayList<Long>();
        for (int i = 0; i < idList.size(); i++) {
            if (i % 2000 == 0) {
                temp = null;
                if (i + 2000 < idList.size()) {
                    temp = idList.subList(i, i + 2000);
                } else {
                    temp = idList.subList(i, idList.size());
                }
                example.clear();
                example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG).andIdIn(temp);
                result = result + opsRequestpurchaseMapper.updateByExampleSelective(request, example);
            }
        }
        // bug 11997
        // 给wm模块传，手工拦截拦截事件
        OpsRequestpurchaseExample exampleToWm = new OpsRequestpurchaseExample();
        exampleToWm.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.LANJIE).andIdIn(idList);
        List<OpsRequestpurchase> requestToWmList =  opsRequestpurchaseMapper.selectByExample(exampleToWm);
        opsWmDispatchForOrderFeignApi.interceptForRequestPo(requestToWmList);
        return result;
    }
}

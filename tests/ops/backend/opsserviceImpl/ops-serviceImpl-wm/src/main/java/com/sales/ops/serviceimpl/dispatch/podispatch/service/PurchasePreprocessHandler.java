package com.sales.ops.serviceimpl.dispatch.podispatch.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.service.po.BasePoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请购预处理回调接口
 */
@Slf4j
@Service
@AllArgsConstructor
public class PurchasePreprocessHandler {


    private BasePoService basePoService;

    /**
     * 请购预处理回执
     *
     * @param list
     * @throws OpsException
     */
    public void preprocessForRequestPo(List<OpsRequestpurchase> list) throws OpsException {

    }


}

package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsHandoverMapper;
import com.sales.ops.db.entity.OpsHandover;
import com.sales.ops.db.entity.OpsHandoverExample;
import com.sales.ops.service.log.OpsHandOverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 交接确认
 * @date 2022/1/26 16:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsHandOverServiceImpl implements OpsHandOverService {

    @Autowired
    private OpsHandoverMapper opsHandoverMapper;


    public void addHandOver(List<OpsHandover> opsHandovers) throws OpsException {
        //写入物流发货交接表
        for (OpsHandover item : opsHandovers) {
            opsHandoverMapper.insertSelective(item);
        }
    }

    @Override
    public List<OpsHandover> findHandOvers(String invoiceNo) {
        OpsHandoverExample example = new OpsHandoverExample();
        example.createCriteria().andDelflagEqualTo(0).andInvoicenoEqualTo(invoiceNo);
        List<OpsHandover> opsHandoversList = opsHandoverMapper.selectByExample(example);
        return opsHandoversList;
    }

    @Override
    public List<OpsHandover> findHandOvers(String doId, String invoiceNo) {
        OpsHandoverExample example = new OpsHandoverExample();
        OpsHandoverExample.Criteria criteria = example.createCriteria();
        criteria.andDoIdEqualTo(doId);
        if (StringUtils.isNotBlank(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        criteria.andDelflagEqualTo(0);
        List<OpsHandover> opsHandoversList = opsHandoverMapper.selectByExample(example);
        return opsHandoversList;
    }




}

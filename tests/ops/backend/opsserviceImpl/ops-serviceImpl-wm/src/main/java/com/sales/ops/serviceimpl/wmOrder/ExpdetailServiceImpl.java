package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.db.dao.ExpdetailMapper;
import com.sales.ops.db.entity.Expdetail;
import com.sales.ops.db.entity.ExpdetailExample;
import com.sales.ops.db.extdao.ExpdetailDao;
import com.sales.ops.service.wmOrder.ExpdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpdetailServiceImpl implements ExpdetailService {

    @Autowired
    private ExpdetailMapper expdetailMapper;

    @Autowired
    private ExpdetailDao expdetailDao;

    @Override
    public List<Expdetail> getExpdetails(String doId) {
        ExpdetailExample example = new ExpdetailExample();
        example.createCriteria().andDeliveryNoEqualTo(doId);
        example.setOrderByClause("ship_date desc");
        List<Expdetail> expdetails = expdetailMapper.selectByExample(example);
        return expdetails;
    }

    @Override
    public String getExpressNoByDoId(String doId) {
        return expdetailDao.getExpressNoByDoId(doId);
    }

    @Override
    public Expdetail getSihpTimeByOrderFno(String orderFno) {
        return expdetailDao.getExpdetailByOrderFno(orderFno);
    }

    @Override
    public int countExpdetailByOrderFnoAndInvoiceFlag(String orderFno) {
        return expdetailDao.countExpdetailByOrderFnoAndInvoiceFlag(orderFno);
    }

}

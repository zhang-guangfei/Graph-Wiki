package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.dao.OpsPurchasetoctcMapper;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoiceExample;
import com.sales.ops.db.entity.OpsPurchasetoctc;
import com.sales.ops.enums.PurchaseCtcEnum;
import com.sales.ops.service.purchase.PurchaseCtcFinishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/10/25
 * @apiNote bug 8426
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseCtcFinishServiceImpl implements PurchaseCtcFinishService {

    @Autowired
    private OpsPurchasetoctcMapper opsPurchasetoctcMapper;

    @Autowired
    private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

    /**
     * 采购单完成时，同步CTC表操作
     */
    @Override
    public void insertMid(String orderNo, Integer itemNo, Integer splitNo) {
        Date finishdate = new Date();
        OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
        OpsPurchaseinvoiceExample.Criteria criteria1 = opsPurchaseinvoiceExample.createCriteria();
        criteria1.andOrdernoEqualTo(orderNo)
                .andItemnoEqualTo(itemNo);
        if (splitNo != null) {
            criteria1.andSplititemnoEqualTo(splitNo);
        }
        List<OpsPurchaseinvoice> opsPurchaseinvoices = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
        opsPurchaseinvoices.forEach(f -> {
            OpsPurchasetoctc opsPurchasetoctc = new OpsPurchasetoctc();
            opsPurchasetoctc.setPono(f.getPono());
            opsPurchasetoctc.setLineitem(f.getLineitem());
            opsPurchasetoctc.setOrderno(f.getOrderno());
            opsPurchasetoctc.setItemno(f.getItemno());
            if (f.getSplititemno() != null) {
                opsPurchasetoctc.setSplititemno(f.getSplititemno());
            }
            opsPurchasetoctc.setQuantity(f.getQuantity());
            opsPurchasetoctc.setModelno(f.getModelno());
            opsPurchasetoctc.setStdprice(f.getStdprice());
            opsPurchasetoctc.setPurchasedate(f.getPurchasedate());
            opsPurchasetoctc.setHopedeliverydate(f.getHopedeliverydate());
            opsPurchasetoctc.setSupplierid(f.getSupplierid());
            opsPurchasetoctc.setReceivewarehouseid(f.getReceivewarehouseid());
            opsPurchasetoctc.setHopeexportdate(f.getHopeexportdate());
            if (f.getShikomianswerno() != null) {
                opsPurchasetoctc.setShikomianswerno(f.getShikomianswerno());
            }
            opsPurchasetoctc.setDeptno(f.getDeptno());
            if (f.getSmccode() != null) {
                opsPurchasetoctc.setSmccode(f.getSmccode());
            }
            opsPurchasetoctc.setCustomerno(f.getCustomerno());
            if (f.getUserno() != null) {
                opsPurchasetoctc.setUserno(f.getUserno());
            }
            opsPurchasetoctc.setPurchasetype(f.getPurchasetype());
            if (f.getCnno() != null) {
                opsPurchasetoctc.setCnno(f.getCnno());
            }
            if (f.getInvoiceid() != null) {
                opsPurchasetoctc.setInvoiceid(f.getInvoiceid());
            }
            if (f.getInvoiceno() != null) {
                opsPurchasetoctc.setInvoiceno(f.getInvoiceno());
            }
            if (f.getSendtime() != null) {
                opsPurchasetoctc.setSendtime(f.getSendtime());
            }
            opsPurchasetoctc.setUpdatetime(f.getUpdatetime());
            opsPurchasetoctc.setDealtype(PurchaseCtcEnum.FINISH.getType());
            opsPurchasetoctc.setFinishdate(finishdate);
            opsPurchasetoctcMapper.insertSelective(opsPurchasetoctc);
        });

    }
}

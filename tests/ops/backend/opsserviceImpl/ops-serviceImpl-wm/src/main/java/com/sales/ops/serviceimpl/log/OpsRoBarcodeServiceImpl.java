package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.batchdao.OpsRoBarcodeBatchDao;
import com.sales.ops.db.dao.OpsRoBarcodeMapper;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsRoBarcodeExample;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.wmOrder.BaseRoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/4/20 10:29
 */
@Slf4j
@Service
public class OpsRoBarcodeServiceImpl implements OpsRoBarcodeService {

    @Autowired
    private OpsRoBarcodeBatchDao opsRoBarcodeBatchDao;
    @Autowired
    private OpsRoBarcodeMapper opsRoBarcodeMapper;
    @Autowired
    private BaseRoService baseRoService;

    @Override
    public List<OpsRoBarcode> findRoBarcodeByRoId(String roId) {
        OpsRoBarcodeExample example = new OpsRoBarcodeExample();
        example.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(roId);
        example.setOrderByClause(" id asc ");
        return opsRoBarcodeMapper.selectByExample(example);
    }

    /**
     * 获取ROBarcode
     */
    @Override
    public OpsRoBarcode getRoBarcodeByBarcode(String roId, String barcdoe) {
        OpsRoBarcodeExample barcodeExample = new OpsRoBarcodeExample();
        OpsRoBarcodeExample.Criteria barcodeCriteria = barcodeExample.createCriteria();
        barcodeCriteria.andDelflagEqualTo(0);
        barcodeCriteria.andRoIdEqualTo(roId);
        barcodeCriteria.andBarcodeEqualTo(barcdoe);// 箱码
        List<OpsRoBarcode> opsRoBarcodes = opsRoBarcodeMapper.selectByExample(barcodeExample);
        if (CollectionUtils.isEmpty(opsRoBarcodes)) {
            return null;
        }
        return opsRoBarcodes.get(0);
    }

    @Override
    public OpsRoBarcode initOpsRoBarcodeForInvoice(String roId, String warehouseCode, OpsImpdata opsImpdata,
                                                   int qty, String orderno, Integer itemno, Integer splititemno) {
        OpsRoBarcode barcode = new OpsRoBarcode();
        barcode.setInvoiceno(opsImpdata.getInvoiceno());
        //begin bug:9385 发票ID B28029 2023-02-06
        barcode.setInvoiceid(opsImpdata.getInvoiceid());
        //end bug:9385
        barcode.setRoId(roId);
        barcode.setWarehouseCode(warehouseCode);
        barcode.setBarcode(opsImpdata.getBarcode());
        barcode.setPackageCode(opsImpdata.getCaseno());
        barcode.setModelno(opsImpdata.getModelno());
        barcode.setQty(qty);
        barcode.setDelflag(0);
        barcode.setCreator(UserDto.ADMIN.getUserName());
        barcode.setCreTime(new Date());
        barcode.setOrderno(orderno);
        barcode.setItemno(itemno);
        barcode.setSplititemno(splititemno);
        barcode.setRoItem(1);
        return barcode;
    }

    @Override
    public Integer insertBatchBarcode(List<OpsRoBarcode> list) {
        Integer count = 0;
        Map<Integer, List<OpsRoBarcode>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, OpsRoBarcode.class);
        for (Map.Entry<Integer, List<OpsRoBarcode>> entry : mapBarcode.entrySet()) {
            log.info("insertBatchBarcode Msg: 总行数:{},页码:{}/{},写入行数:{}", list.size(), entry.getKey() + 1, mapBarcode.size(), entry.getValue().size());
            count += opsRoBarcodeBatchDao.insertRoBarcodeBatch(entry.getValue());
        }
        return count;
    }

    @Override
    public void updateOpsRoBarcodeForRoConfirm(String roId, String userName) throws OpsException {
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(roId);
        if (opsRoItem.getRecQty() >= opsRoItem.getQty()) {
            OpsRoBarcode updateBarcode = new OpsRoBarcode();
            updateBarcode.setModifier(userName);
            updateBarcode.setModifyTime(new Date());
            updateBarcode.setScan(2);// 已扫描标记
            updateBarcode.setScantime(new Date());
            OpsRoBarcodeExample roBarcodeUpdateExample = new OpsRoBarcodeExample();
            OpsRoBarcodeExample.Criteria roBarcodeUpdateCriteria = roBarcodeUpdateExample.createCriteria();
            roBarcodeUpdateCriteria.andRoIdEqualTo(roId);// 收货指令
            roBarcodeUpdateCriteria.andDelflagEqualTo(0);// 删除标识
            opsRoBarcodeMapper.updateByExampleSelective(updateBarcode, roBarcodeUpdateExample);
        }
    }

    @Override
    public void delOpsRoBarcodeByRoId(String roId) throws OpsException {
        OpsRoBarcode updateBarcode = new OpsRoBarcode();
        updateBarcode.setModifier("删除");
        updateBarcode.setModifyTime(new Date());
        updateBarcode.setDelflag(1);// 删除标志
        OpsRoBarcodeExample roBarcodeUpdateExample = new OpsRoBarcodeExample();
        OpsRoBarcodeExample.Criteria roBarcodeUpdateCriteria = roBarcodeUpdateExample.createCriteria();
        roBarcodeUpdateCriteria.andRoIdEqualTo(roId);// 收货指令
        roBarcodeUpdateCriteria.andDelflagEqualTo(0);// 删除标识
        opsRoBarcodeMapper.updateByExampleSelective(updateBarcode, roBarcodeUpdateExample);
    }

}

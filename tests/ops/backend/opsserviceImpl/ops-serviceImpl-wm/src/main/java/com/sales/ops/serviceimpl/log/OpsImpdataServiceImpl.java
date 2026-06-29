package com.sales.ops.serviceimpl.log;

import com.sales.ops.db.dao.OpsImpdataMapper;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsImpdataExample;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.service.log.OpsImpdataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author C12961
 * @date 2023/4/20 11:42
 */
@Slf4j
@Service
public class OpsImpdataServiceImpl implements OpsImpdataService {
    @Autowired
    private OpsImpdataMapper opsImpdataMapper;

    /**
     * 根据发票号和入库扫描类型，来
     *
     * @param invoiceNo 发票号
     * @param opsRo     提供po号
     * @param scanType  扫描类型1：扫箱，2：扫托
     * @param roBarcode 箱码
     * @param caseNo    托号
     * @update_user: C12961
     * @updat_time: 2023-08-31
     */
    @Override
    public void updateImpdataForRoConfirm(String invoiceNo,Long invoiceId, OpsRo opsRo, String scanType, String roBarcode, String caseNo) {
        // 更新OPSImpdata.impTime for invoiceNo,barCode,
        OpsImpdataExample impdataExample = new OpsImpdataExample();
        OpsImpdataExample.Criteria impdataExampleCriteria = impdataExample.createCriteria();
        impdataExampleCriteria.andInvoicenoEqualTo(invoiceNo);
        // 12737 增加发票id
        if(invoiceId!=null){
            impdataExampleCriteria.andInvoiceidEqualTo(invoiceId);
        }
        // 11914 2023-08-31 根据扫描类型判断用什么条件查询
        // 扫描类型为1，则用barcode来查询，扫描类型为2，则用caseNo来查询，caseNo的计算方法为 barcode - invoiceNo
        if (StringUtils.equals(scanType, "1")) {
            impdataExampleCriteria.andBarcodeEqualTo(roBarcode);
        } else if (StringUtils.equals(scanType, "2")) {
            impdataExampleCriteria.andCasenoEqualTo(caseNo);
        } else {
            return;
        }
        // 11914结束
        impdataExampleCriteria.andOrdernoEqualTo(opsRo.getOrderId());
        impdataExampleCriteria.andItemnoEqualTo(Integer.valueOf(opsRo.getOrderItem()));
        if (opsRo.getNum() > 0) {
            impdataExampleCriteria.andSplititemnoEqualTo(opsRo.getNum());
        }
        OpsImpdata opsImpdata = new OpsImpdata();
        opsImpdata.setImptime(new Date());
        opsImpdata.setUpdatetime(new Date());
        opsImpdata.setStatecode("2");// 0待收货 1已签收 2已入库 9异常
        opsImpdataMapper.updateByExampleSelective(opsImpdata, impdataExample);
    }
}

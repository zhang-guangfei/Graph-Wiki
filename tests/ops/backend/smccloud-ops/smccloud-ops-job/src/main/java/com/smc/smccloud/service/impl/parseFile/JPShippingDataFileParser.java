package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.model.invoice.ImportOrderInfoVO;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.InvoiceServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日本SHPINF.TXT
 */
@Service
@Slf4j
public class JPShippingDataFileParser implements EmailFileParser {

    @Resource
    private InvoiceServiceFeignApi invoiceServiceFeignApi;

    @Override
    public String keyword() {
        return FileParseType.SHIPPING_FILE.keyword();
    }

    @Override
    public ResultVo<String> parserFile(File file) {
        log.info("文件 => {}, 解析器: JPShippingDataFileParser", file.getName());
        MultipartFile multipartFile;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            return invoiceServiceFeignApi.importJPShippingFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            return  ResultVo.failure(file.getName() + " JPShippingDataFileParser 解析异常:"+e.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                boolean delete = file.delete();
                log.info("删除JPShippingDataFileParser解析邮件附件结果: {}",delete);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
//    public ResultVo<String> parserFile(File file) {
//        log.info("文件 => {}, 解析器: JPShippingDataFileParser",file.getName());
//        //导入数据转成行数据
//        List<ImportOrderInfoVO> impInoviceList = new ArrayList<>();
//        //读取解析txt数据
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String temp;
//            ImportOrderInfoVO importOrderInfoVO;
//            while ((temp = reader.readLine()) != null) {
//                //解析数据就不写了
//                if (PublicUtil.isEmpty(temp) || temp.length() < 30) {
//                    continue;
//                }
//                if(temp.startsWith("95012")==false)
//                {
//                    return ResultVo.failure("非自动化公司发票");
//                }
//
//                importOrderInfoVO = convertJPShippingFileLine(temp);
//                if(importOrderInfoVO==null)
//                {
//                    continue;
//                }
//                //为发货数据(状态1才追加) 且发票号非CN北京或含CS
//                switch (importOrderInfoVO.getDataType())
//                {
//                    case 1:
//                        impInoviceList.add(importOrderInfoVO);
//                        break;
//                    case 2://GSS
//                        break;
//                    case 3://订单状态
//                        break;
//                }
//            }
//            if (impInoviceList.isEmpty()) {
//                return ResultVo.success("无数据");
//            }
//            log.info("解析分包明细：" + impInoviceList.size());
//            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//            ResultVo<String> result = this.invoiceServiceFeignApi.addImpInvoiceData(impInoviceList);
//            return  result;
//        } catch (Exception e) {
//            log.error("JPShippingDataFileParser->parserFile:error");
//            log.error(e.getMessage());
//            log.error(e.toString());
//            return  ResultVo.failure(e.getMessage());
//        }
//    }

    @Override
    public void setSender(String sender) {

    }

    @Override
    public boolean validEmail(String title) {
        if (PublicUtil.isNotEmpty(title)&& title.indexOf(keyword())!=-1) {
            return true;
        }
        return false;
    }

    @Override
    public void setEmailInfo(EmailInfo info) {

    }


//    /**
//     * 解析字符长度发票数据
//     * 共计字符259
//     *
//     * @param strLine
//     * @return
//     */
//    private ImportOrderInfoVO convertJPShippingFileLine(String strLine) {
//        ImportOrderInfoVO info = new ImportOrderInfoVO();
//        // 当发票号为空(shipdate也为空) 或者 shipStatus不等于A时 不解析
////        if (strLine.length() < 30 || !"A".equals(strLine.substring(30, 31)) ||
////                PublicUtil.isEmpty(strLine.substring(114, 124).trim())) {
////            return null;
////        }
//
//
//
//        if (strLine.length() < 30)
//        {
//            return null;
//        }
//
//        try {
//
//            info.setSupplierCode("JP");
//            String companyCode = strLine.substring(0, 5);
////            if ("9501202".equals(companyCode))//只不导入北京的GSS
////            {
////                return null;
////            }
//            //订单号
//            String orderNo = strLine.substring(7, 27).trim();
//            //订单类型
//            info.setOrderType(orderNo.substring(0, 1));
//            //订单项号子项
//            String itemNo = strLine.substring(27, 30);
//            info.setShipStatus(strLine.substring(30, 31));
//            if("A".equalsIgnoreCase(info.getShipStatus()))
//            {
//                info.setDataType(1);// 发货数据
//            }
//            else if("G".equalsIgnoreCase(info.getShipStatus()))
//            {
//                info.setDataType(2);
//            }
//            else if("N".equalsIgnoreCase(info.getShipStatus())|| "W".equalsIgnoreCase(info.getShipStatus()))
//            {
//                info.setDataType(3);//生成中
//            }
//            else {
//                return null;
//            }
//            //GSS补货订单
////            if (orderNo.startsWith("GCNG") || orderNo.startsWith("GCNZ")) {
////                if ("G".equals(info.getShipStatus())) {
////                    info.setOrderNo(orderNo);
////                    info.setStatus(2);
////                } else {
////                    info.setOrderNo(orderNo.substring(1, 11));
////                }
////            }
//            info.setOrderNo(orderNo.substring(1));
//            info.setItemNo(Integer.parseInt(itemNo));
//
//            //型号
//            info.setModelNo(strLine.substring(31, 61).trim());
//            //数量
//            info.setOrderTotalQty(Integer.parseInt(strLine.substring(61, 68)));
//            //此箱数量
//            info.setQuantity(Integer.parseInt(strLine.substring(68, 75)));
//            //出口仓库数量
//            info.setQtyInExport(Integer.parseInt(strLine.substring(75, 82)));
//            //日本承诺日期[日期格式=“YYMMDD”/6位]
//            info.setJPPromiseDate(strLine.substring(84, 90));
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
//            /*try {
//                info.setPromiseDate(simpleDateFormat.parse(info.getJPPromiseDate()));
//            } catch (Exception ex) {
//            }*/
//            if ("A".equals(info.getShipStatus())) {
//                //发货日期[日期格式=“YYMMDD”/6位]
//                String strShipDate = strLine.substring(90, 96);
//                try {
//                    info.setShippedDate(simpleDateFormat.parse(strShipDate));
//
//                } catch (Exception ex) {
//                }
//                info.setStatus(1);
//            }
//            //运输方式
//            //SEA :Ship
//            //AIR :Air Plane
//            //FEDEX
//            //COURIER
//            info.setShippingMethod(strLine.substring(96, 106).trim());
//            info.setShippingMethod(getTransType(info.getShippingMethod().replace("*", "").substring(0, 1)));
//            //发票号
//            info.setInvoiceNo(strLine.substring(114, 124).trim());
//            //箱号Skid#
//            info.setCaseNo(strLine.substring(124, 127).trim());
//            //59 Product Code
//            info.setECode(strLine.substring(145, 149).trim());
//            //FOB Price
//            String strPrice = strLine.substring(127, 136).trim();
//            //CIF Price
//            if (!PublicUtil.isEmpty(strPrice)) {
//                BigDecimal price = new BigDecimal(strPrice);
//                //金额需要除以100
//                info.setPrice(price.divide(new BigDecimal("100")));
//            }
//            //代码来自哪里
//            info.setWhereCode(strLine.substring(183, 184));
//            //序号
//            String str = strLine.substring(155, 163);
//            if (!PublicUtil.isEmpty(str)) {
//                info.setSeqNo(Integer.parseInt(str));
//            }
//            if (strLine.length() >= 197) {
//                //标签条形码
//                info.setShippingLabel(strLine.substring(184, 197));
//            }
//            //重量
//            if (strLine.length() >= 207) {
//                String strWeight = strLine.substring(198, 207).trim();
//                int weight = 0;
//                if (!PublicUtil.isEmpty(strWeight)) {
//                    int weightInt = Integer.parseInt(strWeight);
//                    info.setWeight(weightInt / 100000.0000);
//                }
//            }
//            //货架号
//            if (strLine.length() >= 207) {
//                info.setShelfNo(strLine.substring(207, 217).trim());
//            }
//            if (strLine.length() > 217) {
//                //原产国
//                info.setOrigin(strLine.substring(217, 218));
//            }
//            //RoHS代码
//            if (strLine.length() > 218) {
//                info.setRoHSCode(strLine.substring(218, 219));
//            }
//            //原产国2
//            if (strLine.length() > 220) {
//                String counrty = strLine.substring(220, 222);
//                //供应商代码
//                info.setSupplierCode(counrty);
//            }
//            //HS Code
//            if (strLine.length() > 222) {
//                info.setHSCode(strLine.substring(222, 237));
//            }
////            if (companyCode.startsWith("06006")) {
////                //广州制造公司代码
////                info.setOwnerCode("CNG");
////            } else {
////                //广州分公司代码
////                info.setOwnerCode("CNZ");
////            }
//            //备注
//            info.setRemark(orderNo + itemNo);
////            if (orderNo.contains("-DR")) {
////                info.setOrderType("R");
////            }
//        } catch (Exception ex) {
//            log.error("convertJPShippingFileLine error:");
//            log.error(ex.toString());
//            log.error(strLine);
//        }
//        return info;
//    }

    private static String getTransType(String str) {
        String transType;
        switch (str) {
            case "A":
                transType = "1";
                break;
            case "S":
                transType = "0";
                break;
            default:
                transType = "0";
        }
        return transType;
    }

}

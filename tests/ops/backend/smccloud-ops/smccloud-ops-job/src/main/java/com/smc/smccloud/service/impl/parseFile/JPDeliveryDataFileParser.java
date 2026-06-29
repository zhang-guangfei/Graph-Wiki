package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.model.orderstate.JPDeliveryDataVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class JPDeliveryDataFileParser implements EmailFileParser {

    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;


    @Override
    public String keyword() {
        return FileParseType.DELIVERY_FILE.keyword();
    }

    /**
     * 解析 .DAT文件
     *
     * @param file .DAT文件
     * @return boolean
     */
    @Override
    public ResultVo<String> parserFile(File file) {
        // 判断文件是否存在
        String fileName = file.getName();
        log.info("文件 => {}, 解析器: {}", file.getName(), "JPDeliveryDataFileParser");
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toUpperCase();
        if (!".DAT".equals(suffix)) {
            log.error("解析文件失败: 非(.DAT)后缀文件");
            return ResultVo.failure("解析文件失败: 非(.DAT)后缀文件");
        }
        List<JPDeliveryDataVO> listOrders=new ArrayList<>();
        String line;
        int lineIndex = 0;
        Date fileDate =null;
        try ( // 不需要关闭流
             FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.US_ASCII);
             BufferedReader bf = new BufferedReader(isr)
            )
        {

            long startTime = System.currentTimeMillis();
            while ((line = bf.readLine()) != null) {
                lineIndex++;
                if (lineIndex == 1) {
                    fileDate = getFileSendDate(line);
                    if (DateUtil.getDiffDay(fileDate,DateUtil.getToday())!=0) {
                        break;
                    }
                }
                else {
                    JPDeliveryDataVO orderVO = parseLineData(line);
                    if (orderVO == null) {
                        continue;
                    }
                    orderVO.setUpdDate(fileDate);
                    listOrders.add(orderVO);
                }
            }
            long endTime = System.currentTimeMillis();
            log.info("解析文件程序运行时间： " + (endTime - startTime) + "ms");  // 输出解析附件程序运行时间
        } catch (Exception e) {
            log.error("{}文件解析失败", keyword(), e);
            return ResultVo.failure();
        }

        if (listOrders.isEmpty()) {
            return ResultVo.success();
        }
        long startTime = System.currentTimeMillis();
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        // 导入日本delivery.dat
        ResultVo<String> stringResultVo = orderStateServiceFeignApi.importJPDeliveryData(listOrders);
        if (!stringResultVo.isSuccess()) {
            return ResultVo.failure(stringResultVo.getMessage());
        }
        long endTime = System.currentTimeMillis();
        log.info("将解析文件的数据推送到mq：" + (endTime - startTime) + "ms");  // 输出推送数据到mq完毕运行时间
        try {
            boolean delete = file.delete();
            log.info("删除JPDeliveryDataFileParser解析邮件附件结果: {}",delete);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ResultVo.success(stringResultVo.getMessage());
    }

    @Override
    public void setSender(String sender){

    }

    @Override
    public boolean validEmail(String title) {
        if (PublicUtil.isNotEmpty(title)&& title.contains(keyword())) {
            return true;
        }
        return false;
    }

    @Override
    public void setEmailInfo(EmailInfo info) {

    }



    private Date getFileSendDate(String lineData)
    {
        String sendDate = getSendDate(lineData.substring(30));
        Date date = DateUtil.stringToDate(sendDate);
        return date;
    }
    private JPDeliveryDataVO parseLineData(String lineData)
    {
        if (PublicUtil.isEmpty(lineData)) {
          return null;
        }

        if (lineData.length() < 102) {
            return null;
        }

        JPDeliveryDataVO info=new JPDeliveryDataVO();

        String orderNO = lineData.substring(0,23).trim();
        String modelNo = lineData.substring(30, 66).trim();
        String qty = lineData.substring(66, 73).trim();
        String reqDlvDate = lineData.substring(74, 82).trim();
        String jpdlvDate = lineData.substring(83, 91).trim();
        String jporderNo = lineData.substring(94, 102).trim();
        String transType = lineData.substring(23, 24).trim();
        String poExpType = lineData.substring(92,94).trim(); // 出在库
        String baling = "";
        if(lineData.length() >= 103) {
            baling = lineData.substring(102,103).trim(); // 已捆包
        }
        String orderItemNo = lineData.substring(25, 29).trim();

        info.setOrderNo(orderNO);
        info.setTransType(transType);
        info.setModelNo(modelNo);
        info.setQty(Integer.parseInt(qty));
        info.setDlvDate(reqDlvDate);
        info.setJpDlvDate(jpdlvDate);
        info.setSupplierOrderNo(jporderNo);
        info.setFactory(poExpType);
        info.setBaling(baling);
        info.setItem(orderItemNo);
        info.setPurchaseType(lineData.substring(56, 57).trim());

        int finishQty = 0;
        if (lineData.length() >= 110) {
            finishQty = Integer.parseInt(lineData.substring(104, 110).trim());
        }
        info.setFinishQty(finishQty);

        return info;
    }

//    private OrderStateVO resolveLineData(String lineData, int readLineCount) {
//        if (PublicUtil.isEmpty(lineData)) {
//            return null;
//        }
//        if (readLineCount == 1) {
//            if (lineData.length() < 38) {
//                return null;
//            }
//            OrderStateVO orderStateVO = new OrderStateVO();
//            String sendDate = getSendDate(lineData.substring(30));
//            orderStateVO.setStateDes(sendDate);
//            return orderStateVO;
//        }
//
//        OrderStateVO orderStateVO = new OrderStateVO();
//        if (lineData.length() < 102) {
//            return null;
//        }
//
//        String orderNo = lineData.substring(0, 13).trim();
//        orderStateVO.setOrderType(getOrderType(orderNo));
//
//        if (orderNo.length() < 8) {
//            return null;
//        }
//
//        String orderItemNo = lineData.substring(25, 29).trim();
//        orderStateVO.setOrderNo(orderNo.substring(1, 8).trim() + "-" + orderItemNo);
//
//        if (orderNo.startsWith("GCNG") || orderNo.startsWith("GCNZ")) {
//            orderStateVO.setOrderNo(orderNo.substring(1, 11));
//        }
//
//        if (orderStateVO.getOrderNo().length() < 10) {
//            return null;
//        }
//        String modelNo = lineData.substring(30, 66).trim();
//        String qty = lineData.substring(69, 73).trim();
//        String gzReqDlvDate = lineData.substring(74, 82).trim();
//        String dlvDate = lineData.substring(83, 91).trim();
//        String jporderNo = lineData.substring(94, 102).trim();
//        String shipType = lineData.substring(23, 24).trim();
//        String poExpType = lineData.substring(92,94).trim();
//        if (PublicUtil.isNotEmpty(poExpType)) {
//            orderStateVO.setPoExpType(poExpType);
//        }
//        int finishQty = 0;
//
//        if (lineData.length() >= 110) {
//            finishQty = Integer.parseInt(lineData.substring(104, 110).trim());
//        }
//        if (PublicUtil.isEmpty(dlvDate)) {
//            orderStateVO.setStateCode(21);
//            orderStateVO.setStateDes(lineData.substring(92, 94) + "处理中未确定货期");
//            orderStateVO.setStateDate(new Date());
//        }
//        String stateDesc = getStateDesc(dlvDate);
//        if (PublicUtil.isNotEmpty(stateDesc)) {
//            orderStateVO.setStateCode(25);
//            orderStateVO.setStateDes(stateDesc + " " + getTransTypeDesc(shipType));
//        } else {
//            if (PublicUtil.isNotEmpty(dlvDate)) {
//                String d = "";
//                Date dd = null;
//                d = "20" + dlvDate.replaceAll("/", "-");
//                dd = DateUtil.stringToDate(d);
//                String transTypeDesc = getTransTypeDesc(shipType);
//                orderStateVO.setStateDes(String.format("日本预计出厂日期: %s %s", d, transTypeDesc));
//                orderStateVO.setPoShipDate(dd);
//                orderStateVO.setStateCode(25);
//                orderStateVO.setPoReplyDate(dd); // 工厂纳期
//                orderStateVO.setStateDate(dd);
//                orderStateVO.setCustDlvDate(dd);
//                orderStateVO.setMaxDate(dd);
//                orderStateVO.setMinDate(dd);
//                orderStateVO.setFirstDate(dd);
//            }
//        }
//        if (finishQty > 0) {
//            orderStateVO.setStateDes(String.format("已完工/发出(%d)", finishQty));
//        }
//        orderStateVO.setTransType(getTransType(shipType));
//        orderStateVO.setProvider("JP");
//        orderStateVO.setSupplierCode("JP");
//        orderStateVO.setSupplierOrderNo(jporderNo);
//        orderStateVO.setStateType(Integer.valueOf(orderStateVO.getStateCode().toString().substring(0, 1)));
//        return orderStateVO;
//    }

//    private static String getStateDesc(String date) {
//        switch (date) {
//            case "11/11/11":
//                return "要图纸";
//            case "22/22/22":
//                return "型号确认中";
//            case "33/33/33":
//                return "开发部正在组装";
//            case "44/44/44":
//                return "紧急生产停止";
//            case "55/55/55":
//                return "部品不足";
//            case "66/66/66":
//                return "部品不足";
//            case "77/77/77":
//                return "正在确认中";
//            case "88/88/88":
//                return "询问图纸中";
//            case "99/99/99":
//                return "问题订单,等待修正";
//            case "99/00/00":
//                return "问题订单,等待修正";
//            default:
//                return "";
//        }
//    }

//    private static int getOrderType(String orderNo) {
//        String str = orderNo.substring(0, 1);
//        int orderType;
//        switch (str) {
//            case "A":
//                orderType = 1;
//                break;
//            case "B":
//                orderType = 20;
//                break;
//            case "k":
//                orderType = 20;
//                break;
//            default:
//                orderType = 21;
//        }
//        return orderType;
//    }

//    private static String getTransType(String str) {
//        String transType;
//        switch (str) {
//            case "A":
//                transType = "1";
//                break;
//            case "S":
//                transType = "0";
//                break;
//            default:
//                transType = "0";
//        }
//        return transType;
//    }

//    private static String getTransTypeDesc(String str) {
//        String transType;
//        switch (str) {
//            case "A":
//                transType = "空运";
//                break;
//            case "S":
//                transType = "海运";
//                break;
//            default:
//                transType = "空运";
//        }
//        return transType;
//    }

    private static String getSendDate(String sendDate) {
        String[] strDate = sendDate.trim().split(",");
        String[] monthOld = strDate[0].split(" ");
        String[] month = removeNullByArray(monthOld);
        int dateMonth = getDateMonth(month[0].trim());
        String Mon = String.valueOf(dateMonth);

        if (PublicUtil.isEmpty(dateMonth)) {
            return "";
        }
        int dateDay = Integer.parseInt(month[1].trim());
        String day = String.valueOf(dateDay);

        String dateYear = strDate[1].trim();

        if (dateMonth < 10) {
            Mon = "0"+ dateMonth;
        }
        if (dateDay < 10) {
            day = "0" + dateDay;
        }

        return dateYear + "-" + Mon + "-" + day;
    }

    public static String[] removeNullByArray(String[] strs) {
        List<String> tmp = new ArrayList<String>();
        for (String str : strs) {
            if (!StringUtils.isBlank(str)) {
                tmp.add(str);
            }
        }
        return tmp.toArray(new String[0]);
    }

    private static int getDateMonth(String month) {
        int mon;
        switch (month) {
            case "JANUARY":
                mon = 1;
                break;
            case "FEBRUARY":
                mon = 2;
                break;
            case "MARCH":
                mon = 3;
                break;
            case "APRIL":
                mon = 4;
                break;
            case "MAY":
                mon = 5;
                break;
            case "JUNE":
                mon = 6;
                break;
            case "JULY":
                mon = 7;
                break;
            case "AUGUST":
                mon = 8;
                break;
            case "SEPTEMBER":
                mon = 9;
                break;
            case "OCTOBER":
                mon = 10;
                break;
            case "NOVEMBER":
                mon = 11;
                break;
            case "DECEMBER":
                mon = 12;
                break;
            default:
                mon = 0;
        }
        return mon;
    }

}
package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sales.ops.db.entity.OrderState;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderLog.OrderLogDO;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausToSalesDO;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.orderstate.JPDeliveryDataVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-08-18 13:24
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderStateServiceTests {

    @Resource
    private OrderStateService orderStateService;

    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;
    @Resource
    private RedisManager redisManager;

    @Resource
    private OrderLogFeignApi orderLogFeignApi;

    @Resource
    private OPSVRequisitionStausToSalesMapper opsvRequisitionStausToSalesMapper;

    @Resource
    private OrderStateMapper orderStateMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;

    @Resource
    private OrderStateMapperReadOnlyMapper orderStateMapperReadOnly;

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private OrderModifyMapper orderModifyMapper;

    @Resource
    private OrderLogService orderLogService;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void testPaseDlvData() {
//        ResultVo<String> resultVo = orderStateService.importCNMOPSVRequisitionStatusToSales();
//        System.out.println("resultVo = " + resultVo.toString());
        ResultVo<DataTypeVO> dataTypeVOResultVo = orderStateService.testCommonService("1001", "1");
        System.out.println("dataTypeVOResultVo = " + dataTypeVOResultVo.toString());
        System.out.println("JSONUtil.toJsonPrettyStr = " + JSONUtil.toJsonPrettyStr(dataTypeVOResultVo));
    }

    @Test
    public void rcvOrderStateMQ() {
//        List<String> list = new ArrayList<>();
//        list.add("{\n" +
//                "        \"itemNo\":0,\n" +
//                "        \"orderNo\":\"99J1034003    \",\n" +
//                "        \"poReplyDate\":1677168000000,\n" +
//                "        \"provider\":\"POSUP\",\n" +
//                "        \"purchaseFlag\":0,\n" +
//                "        \"rorderNo\":\"99J1034003    \",\n" +
//                "        \"stateCode\":25,\n" +
//                "        \"stateDes\":\"美国AP:供应商返信纳期:2023-02-24\"\n" +
//                "    }");
//
//        for (String str : list) {
//            OrderStateVO orderStateVO = JSONObject.parseObject(str, OrderStateVO.class);
//            System.out.println("orderStateVO1 = " + JSONObject.toJSONString(orderStateVO));
//
//            ResultVo<String> resultVo = orderStateService.rcvOrderStateMQ(orderStateVO);
////            if (resultVo.isSuccess()) {
////                redisManager.del("ops:errorOrderStateMqMsg:"+orderStateVO.getOrderNo());
////            }
//            System.out.println("resultVo = " + resultVo.toString());
//        }
        LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderStateDO::getId,9773237);
        OrderStateDO orderStateDO = orderStateMapper.selectOne(queryWrapper);
        System.out.println("orderStateDO = " + JSONUtil.toJsonPrettyStr(orderStateDO));
        OrderStateVO obj = BeanCopyUtil.copy(orderStateDO, OrderStateVO.class);
        obj.setStateCode(25);
        obj.setStateDes("生产中");
        obj.setPoReplyDate(DateUtil.stringToDate("2023-03-06"));
        orderStateService.rcvOrderStateMQ(obj);


    }


    @Test
    public void testRe() {

        // List<String> list = getOrderNoList();
        // String[] arr = new String[]{"10198403-65","H04XFAE010","32ADSNN050","10232944-1","10221527-1","10171570-8","10075835-10","10213930-1","10231639-13","10229447-1","10216512-8","10185269-86","45ACCVT010","10226797-1","10133234-90","10054495-4","10223748-79","10174869-11","2374926040","45ACBQB020","10221189-2","10221910-9","10220481-1","10210119-8","10148897-29","6220575200","71AANEU070","10175449-3","10153493-1","10130875-60","10223748-85","10218376-1","10088516-3","45ACCTN010","10235043-1","10221910-11","10206039-107","H04VVAA030","32ACZZX010","10199112-2","10215124-28","10070859-2","10180314-1","10222985-2","10203866-49","S0003038-1","10224225-1","10223705-6","10220816-1","10215677-2","10156684-32","10158666-3","10079399-5","10167360-54","10198403-58","32ADSNN020","10238279-1","10210457-3","10222337-1","10173108-5","10231639-9","10236078-3","10198168-10","10161379-53","10050689-13","45AC2NT030","10216233-1","45ACBVX010","6220575030","10229486-2","10192831-3","10178221-15","10111184-7","10227906-1","10211426-1","10198322-2","10215591-12","10198259-27","10167360-25","10084800-3","32ADHH6020","10173983-30","10228976-4","45AC895040","10228716-1","10114605-11","78AHMVN010","10231360-16","10199694-8","10212205-1","10171145-1","10167360-28","10113120-1","33AJK55360","2265025020","10161097-33","10070859-4","10229641-1","10208339-1","10182634-3","10135953-76","10202264-9","10191724-3","10156684-42","10097058-2","237518L150","26303EY270","78AHJWG030","10167360-56","10198403-63","10009247-13","32ADSNN030","10237439-12","10201162-19","10223748-54","10194029-6","10241101-6","10231639-11","10221292-3","10208765-6","10190008-3","10161379-83","45AC2NU020","S0003340-1","10153829-4","10135378-5","10223748-68","10179213-17","10028588-1","45ACDK1020","10237519-228","10229424-1","10148897-25","10198403-71","10218796-1","10142642-4","10234176-16","10221138-3","10131467-5","C5530RX310","10194248-2","10186631-57","10173323-6","10017001-1","10228976-4","10173983-45","31AFS51080","45ACBVX020","10230408-11","10218300-42","10188617-3","10202193-6","10149511-8","10135113-1","10033180-2","526712G030","10167360-29","10078851-4","5182BSD150","10223748-53","10217685-3","10012966-24","35ACKXQ070","10231639-7","10236078-1","10220153-6","10161379-34","10073069-9","45AC0AX020","10065583-41","10183053-22","10234434-1","10228976-4","10176821-58","10125110-71","31AFS3D210","E6ACPW9050","10214048-4","10202333-119","10156684-21","10173853-22","10137089-13","10103336-5","D2AADKQ030","10167360-34","J104119010","10078851-3","10057889-63","10185087-21","21505QD060","32ADSNN140","35ACKXQ060","10221342-2","10195088-5","10175425-118","10161379-18","31AFSNP030","45ABZ44010","6745185020","10198320-20","10225893-9","10223748-62","10139659-28","H04V1A6200","10211039-188","10214709-16","10221780-35","10212368-11","10195901-16","10186568-66","5265164030","10032582-4","10158176-70","10020802-24","10223748-66","10171363-2","10171556-24","10144047-1","10238837-1","10220502-140","10199531-20","10190293-11","10180718-1","10143451-1","10148897-23","10218636-3","10200699-1","10054495-1","10237376-48","10223748-70","10165141-28","10174869-11","10232582-1","10216421-7","10167191-270","10148897-27","10152875-585","10026153-2","79AKYFU070","10198320-24","10237355-2","10223748-63","10155696-2","H044UAZ110","248180R020","10211039-234","10225538-2","10195901-40","10185958-22","10186568-67","10053947-71","5265164040","10188947-25","10228976-4","10184557-1","10170033-5","10078739-1","H306265010","E6ACSAV210","10234931-1","10227934-40","10231355-248","10156684-22","10150099-3","D2AACQ7020","10167360-52","10198403-66","10206039-94","10142642-2","10223748-58","10133176-1","10075835-35","C5530RX270","10237375-4","10228602-4","10186631-35","10190889-1","10182214-10","2866841010","5265957040","10223209-1","10166762-2","10237376-47","10223748-69","10187289-1","10183228-5","10220502-665","10223703-18","10167191-254","10148897-26","10140998-3","79AKMX5370","10198320-10","5267166260","10139659-27","H04V1A6130","2481634100","10223735-51","10221780-21","10186568-14","10092884-4","5265662060","10153493-2","10027769-1","6220978030","10237391-3","10088516-4","10110867-2","10060143-1","10235949-1","10198322-1","10183970-13","10164198-2","32ADHH6010","10198403-83","10232590-15","16A329K010","10223748-61","10215502-3","10111551-9","H04GVAN010","10209989-9","10212638-6","10186631-67","10186568-4","5265806010","J10401A010","10070859-6","10232079-1","10223748-97","10180341-5","32ADPAA050","10240902-2","10233789-1","10225310-2","10217268-2","10189701-1","10160214-1","5266172090","10175425-117","10198403-76","32ADSNN060","10236269-11","10207320-12","10176382-5","10075835-33","10234051-1","10231639-14","10231697-6","10186631-12","10164534-31","45ACCVT020","10198320-6","10161573-7","10082826-63","10234334-1","10205059-8","10156540-25","2481651010","10233978-2","10223735-48","10186568-7","10131491-7","5265662040","10198403-67","10144787-32","10142642-1","10207320-13","10223748-57","10075835-34","96614R2030","10228602-1","10220328-1","10186631-29","10178499-1","10170776-2","5265957030","10198403-5","10078851-7","10069882-60","10227934-28","10195254-1","10095483-4","35ACKXQ080","10231639-8","10236078-2","10231234-9","10161379-41","10073069-11","45AC2NT020","10069654-13","10198403-64","10210269-3","32ADSNN040","10223748-55","10117248-1","10075835-9","HX08194070","10241101-11","10231639-12","10221650-1","10171183-1","10159073-9","45ACBSY010","10214426-78","10228976-4","10070859-1","10231370-4","10139322-116","10023607-4","E6ACSAV220","10227934-65","10156684-31","10143747-1","10045014-6","H04TAAR190","10167360-53","10198403-81","10142642-5","16A32AM010","10231340-16","10223748-59","10212125-18","10132399-3","10201170-1","10186631-61","10186568-2","10149137-1","10157470-13","5265818030","J10395J010","10161097-34","10070859-5","10218712-33","10115116-19","10115338-22","10063706-14","10212886-1","10193393-3","10114370-8","10085602-3","237518L160","5266172080","78AHJWG050","10167360-57","10198403-93","10192272-123","10142642-3","10189753-6","10133176-20","10075835-36","C5530RX300","10226807-2","10228602-5","10209671-35","10197064-4","10186631-52","10114605-7","5265957070","10095502-7","10213866-34","10223748-80","10088516-2","45ACBQB040","10214122-2","10221910-10","10178797-8","10170882-6","10130470-35","10117107-7","10234148-2","10216359-1","10020802-22","10226119-1","10223748-67","10152356-22","72AD1CY010","10200586-12","10220502-158","10190293-14","10185328-2","10148897-24","10130316-3","10209736-3","10115338-19","10223748-65","10171363-1","10173376-31","72AD5DY010","10237519-231","10190293-3","10195901-59","10183673-6","10184718-4","10053947-72","5264904040","10218636-2","10054495-2","6220521090","10223748-71","10187289-1","23744BM020","45ACBQB010","10218180-2","10221910-8","10148897-28","10120458-1","10062992-1","6220583460","71AANEU020","226492Q020","10214690-3","10070859-3","10236713-4","10180314-2","10226457-32","10153630-15","10236176-1","10225310-3","10216717-1","10215677-5","10156684-41","10170172-2","H04UHA2010","78AHJWG020","10167360-55"};
        String[] arr = new String[]{"10240902-2"};
        List<String> list = Arrays.asList(arr);
        int count = 0;
        for (String no :list) {
//            LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(OrderStateDO::getOrderNo,no);
//            OrderStateDO orderStateDO = orderStateMapperReadOnly.selectOne(queryWrapper);
//
//            LambdaQueryWrapper<RcvDetailDO> query = new LambdaQueryWrapper<>();
//            query.eq(RcvDetailDO::getRorderFno,no);
//            RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(query);
//            if (rcvDetailDO == null) {
//                continue;
//            }
//            if (orderStateDO == null) {
//                continue;
//            }
//            if (rcvDetailDO.getStatus() != 9 && orderStateDO.getStateCode() != 90) {
//                continue;
//            }
            List<OrderCancelResult> cancelResultList = new ArrayList<>(1);
            OrderCancelResult cancelResult = new OrderCancelResult();
            // cancelResult.setNo(orderStateDO.getCorderNo());
            cancelResult.setOrderNo(no);
            cancelResult.setResult("2");
            cancelResult.setMessage("系统补推删单");
            cancelResult.setHandlePsnNo("SYSTEM");
            cancelResult.setHandlePsnName("系统处理");
            cancelResult.setHandleRemark("手动处理删单问题");
            cancelResultList.add(cancelResult);
            log.info("删单[客户单] 回调给门户队列的接口 => {} ",JSONObject.toJSONString(cancelResult));
            ResultVo<Boolean> resultVo = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(cancelResultList);
            if (resultVo.isSuccess()) {
                count++;
                // 变更order_modify的状态
                LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OrderModifyDO::getOrderNo,no).set(OrderModifyDO::getStatus,6);
                int update = orderModifyMapper.update(null, updateWrapper);
                if (update == 1) {
                    // 变更order_state的状态
                    LambdaUpdateWrapper<OrderStateDO> up = new LambdaUpdateWrapper<>();
                    up.eq(OrderStateDO::getOrderNo,no).set(OrderStateDO::getStateCode,90);
                    orderStateMapper.update(null,up);
                }
            }
            System.out.println("resultVo = " + JSONObject.toJSONString(resultVo));

        }
        System.out.println("count = " + count);
    }



    @Test
    public void importSupplierReplyDate() {
        try {
            MultipartFile file = FileUtil.getMultipartFile("供应商返信.xlsx", new FileInputStream(new File("F:\\xwechat_files\\wxid_642dlqqyk7kx22_afeb\\msg\\file\\2025-08\\供应商返信-8月14日AP 返信(1).xlsx")));
            ResultVo<String> resultVo = orderStateService.importSupplierReplyDate(file);
            System.out.println("resultVo = " + resultVo.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void importJPReceiveOrderFileTest() {
        try {
            MultipartFile file = FileUtil.getMultipartFile("ORDER-95012.TXT", new FileInputStream(new File("D:\\test\\ORDER-95012.TXT")));
            log.info("importJPReceiveOrderFileTest {}", orderStateService.importJPReceiveOrderFile(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownLoadJPCNFile() {
        ResultVo<String> resultVo = orderStateService.downloadJPDeliveryFile();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void testOrderStateLog() {
        OrderLogDO log = new OrderLogDO();
        log.setId(188819L);
        log.setOrderNo("aaa");
        int update = orderLogMapper.updateById(log);
        System.out.println("update = " + update);


    }


    @Test
    public void sendOrderStateToGZProdText(){
        OrderStateDO info=new OrderStateDO();
        info.setCustDlvDate(DateUtil.stringToDate("2022-09-07"));
        info.setId(13395536L);
        info.setModelNo("LVMK27-5J");
        info.setOptUserName("变更订单状态");
        info.setOrderNo("CN000183-1");
        info.setOrderType(11);
        info.setProvider("接单处理");
        info.setQuantity(376);
        info.setRorderNo("CN000183");
        info.setStateCode(50);
        info.setCorderNo("P003860010");
        info.setStateDate(DateUtil.stringToDate("2022-09-07"));
        info.setStateDes("2022-09-07接单处理:等待出库");
        info.setSupplierNo("KGZ");
        orderStateService.sendOrderStateToGZProd(info);
    }

    @Test
    public void importDatFile() {
        File file = new File("E:\\xxx\\log\\DELIVERY.DAT");
        // 判断文件是否存在
        String fileName = file.getName();
        log.info("文件 => {}, 解析器: {}", file.getName(), "JPDeliveryDataFileParser");
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toUpperCase();
        if (!".DAT".equals(suffix)) {
            log.error("解析文件失败: 非(.DAT)后缀文件");
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
//                    if (DateUtil.getDiffDay(fileDate,DateUtil.getToday())!=0) {
//                        break;
//                    }
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
        }

        if (listOrders.isEmpty()) {
            return;
        }
        long startTime = System.currentTimeMillis();
        // 导入日本delivery.dat
        ResultVo<String> stringResultVo = orderStateServiceFeignApi.importJPDeliveryData(listOrders);
        if (!stringResultVo.isSuccess()) {
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
        String poExpType = lineData.substring(92,94).trim();
        String orderItemNo = lineData.substring(25, 29).trim();


        info.setOrderNo(orderNO);
        info.setTransType(transType);
        info.setModelNo(modelNo);
        info.setQty(Integer.parseInt(qty));
        info.setDlvDate(reqDlvDate);
        info.setJpDlvDate(jpdlvDate);
        info.setSupplierOrderNo(jporderNo);
        info.setFactory(poExpType);
        info.setItem(orderItemNo);
        info.setPurchaseType(lineData.substring(56, 57).trim());


        int finishQty = 0;
        if (lineData.length() >= 110) {
            finishQty = Integer.parseInt(lineData.substring(104, 110).trim());
        }
        info.setFinishQty(finishQty);

        return info;
    }

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


    @Test
    public void testImplOPS_V_RequisitionStausToSales() {
//        List<OPSVRequisitionStausToSalesDO> reqStausToSalesListByDate = opsvRequisitionStausToSalesMapper.findReqStausToSalesListByDate("2022-10-17 00:000:00", "2022-10-18 23:59:59");
//        int count =0;
//        for (OPSVRequisitionStausToSalesDO item : reqStausToSalesListByDate) {
//            if (StringUtils.isBlank(item.getOrderNo())) {
//                continue;
//            }
//            if (item.getEsDlvDate() != null) {
//                LambdaUpdateWrapper<OrderStateDO> updateWrapper = new LambdaUpdateWrapper<>();
//                updateWrapper.eq(OrderStateDO::getOrderNo,item.getOrderNo()).set(OrderStateDO::getPoReplyDate,item.getEsDlvDate());
//                int update = orderStateMapper.update(null, updateWrapper);
//                if (update == 1) {
//                    count++;
//                }
//            }
//        }
//
//        System.out.println("解析 : count = " + count);

    }

    @Test
    public void testOrderLogUp() {
//        OrderLogDO orderLogDO = new OrderLogDO();
//        orderLogDO.setId(188819L);
//        orderLogDO.setOrderNo("wwwwww");
//        orderLogMapper.updateById(orderLogDO);


        LambdaUpdateWrapper<OrderLogDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderLogDO::getId,188819L).eq(OrderLogDO::getOptType,12)
                .set(OrderLogDO::getOrderNo,"aaaaaaa")
                .set(OrderLogDO::getOptUserId,null);
        int update = orderLogMapper.update(null, updateWrapper);
        System.out.println("update = " + update);

    }

    @Test
    public void testAllowUpdateStateTest()
    {
        Boolean result = orderStateService.isAllowChangeStatus(30,70);
        System.out.println(result);
    }

    @Test
    public void rcvOrderStateMQTest()
    {
        OrderStateVO orderStateVO =new OrderStateVO();
        orderStateVO.setRorderNo("1963060224-1");
        orderStateVO.setItemNo(1);
        orderStateVO.setStateCode(21);
        orderStateVO.setProvider("test");
        orderStateVO.setUpdateTime(new Date());

        orderStateVO.setPoReplyDate(DateUtil.stringToDate("2023-07-01"));
        orderStateVO.setStateDes("CN纳期2023-07-01");
        orderStateService.rcvOrderStateMQ(orderStateVO);

        orderStateVO.setStateDes("CN纳期2023-07-01 发2次");
        orderStateService.rcvOrderStateMQ(orderStateVO);

        orderStateVO.setStateDes("CN纳期2023-07-01 发3次-");
        orderStateService.rcvOrderStateMQ(orderStateVO);


//        orderStateVO.setPoReplyDate(DateUtil.stringToDate("2023-07-02"));
//        orderStateVO.setStateDes("CN纳期2023-07-02");
//        orderStateService.rcvOrderStateMQ(orderStateVO);
//
//        orderStateVO.setPoReplyDate(DateUtil.stringToDate("6666-06-06"));
//        orderStateVO.setStateDes("CN纳期66-66-66");
//        orderStateService.rcvOrderStateMQ(orderStateVO);
//
//        orderStateVO.setStateDes("CN纳期66-66-66 发两次");
//        orderStateService.rcvOrderStateMQ(orderStateVO);
//
//        orderStateVO.setPoReplyDate(DateUtil.stringToDate("2023-07-03"));
//        orderStateVO.setStateDes("CN纳期2023-07-03");
//        orderStateService.rcvOrderStateMQ(orderStateVO);
//        orderStateVO.setStateDes("CN纳期2023-07-03 发两次");
//        orderStateService.rcvOrderStateMQ(orderStateVO);
//
//        orderStateVO.setPoReplyDate(DateUtil.stringToDate("2023-07-04"));
//        orderStateVO.setStateDes("CN纳期2023-07-04");
//        orderStateService.rcvOrderStateMQ(orderStateVO);


    }

}

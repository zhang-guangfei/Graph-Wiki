package com.sales.ops;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OkHttpAddTokenUtil;
import com.sales.ops.common.until.OkHttpSSSUtil;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.extdao.OPSProductDao;
import com.sales.ops.dto.flux.CancelDocAsnDto;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.flux.ChangeConsigneeAddressDto;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.RoTypeEnum;
import com.sales.ops.enums.WmOrderTaskEnum;
import com.sales.ops.feign.*;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.service.BinServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2021/11/16 10:16
 */
@Slf4j
public class CustomerInfoTest extends BaseTest {

    @Autowired
    private OpsCustomerFeignApi opsCustomerFeignApi;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    @Autowired
    private OkHttpSSSUtil okHttpSSSUtil;

    @Autowired
    private XxlJobFeignApi xxlJobFeignApi;

    @Autowired
    private OPSSupplierFeignApi opsSupplierFeignApi;

    @Value("${smccloud.oauth2.client.clientId}")
    private String clientId;

    @Value("${smccloud.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${smccloud.oauth2.client.access-token-uri}")
    private String accessTokeUri;

    private final static  String TOKEN_HEADER="Authorization";
    private final static  String GRANT_TYPE_HEADER="grant_type";

    @Autowired
    private BinServiceFeignApi binServiceFeignApi;
    @Autowired
    private OpsWmFeignApi opsWmFeignApi;
    @Autowired
    private OPSWarehouseFeignApi oPSWarehouseFeignApi;

    @Test
    public void ttst(){
       /* CommonResult<OpsDoAndItemDto> res = opsWmFeignApi.findDoToWms("DO10022951009001000");
        if (res.isSuccess()) {
            OpsDoAndItemDto opsDoAndItemDto = res.getData();
            opsDoAndItemDto.setWmOrderTaskId(4785L);
            //3.post to wms
            CommonResult resWm = opsCallWmsFeignApi.updateDoToWms(opsDoAndItemDto);
            System.out.println("123");
        }*/
        try {
            /*ChangeConsigneeAddressDto param = new ChangeConsigneeAddressDto();
            param.setAddress("test——全地址 改小写");
            param.setCustomerId("SMC");
            param.setOrderNo("DO10013509003001000");
            param.setWarehouseId("KBJ");
            param.setProvince("11");
            param.setCity("1101");
            param.setDistrict("110101");
            param.setAddress2("详细地址 改小写");
            param.setPriority("2");
            param.setCarrierId("SF");//顺丰
            param.setConsigneeTel1("");
            param.setConsigneeTel2("123456789");
            param.setConsigneeZip("10086");
*/
            ChangeConsigneeAddressDto param = new ChangeConsigneeAddressDto();
            param.setCustomerId("SMC");
            param.setOrderNo("DO10013509003001000");
            param.setWarehouseId("KBJ");
            param.setPriority("1");
            //param.setConsigneeName("兆丰年");
            CommonResult result = opsCallWmsFeignApi.changeConsigneeAddress(param);
            System.out.println(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void t (){
        OpsWmOrderTaskCondition con = new OpsWmOrderTaskCondition();
        con.setWmOrderId("ROW10015182213160000");//getWmOrderId
        con.setLimit("3");
        con.setFlag(3);
        con.setWmOrderType(WmOrderTaskEnum.RO.getType());
        con.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        CommonResult<List<OpsWmOrderTask>>  resBarcode = opsWmFeignApi.findWmOrderTask(con);
        int a = 0;
    }

    public void tOpsException() throws OpsException{

        throw new NullPointerException();
        //System.out.println("to");
    }

    @Test
    public void testBin(){

    }
    @Test
    public void ttt(){
        //ReturnXxlJobResult result = xxlJobFeignApi.getStatusById(19);
        //xxlJobFeignApi.startById(19);
        //xxlJobFeignApi.stopById(19);
        String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
        String s = HttpRequest.sendPost(accessTokeUri, param);
        int i = 0;
    }
    @Autowired
    private OPSProductDao productMapper;

    @Test
    public void tttt(){
        try {

            List<Integer> re = new ArrayList<Integer>();

            JSONObject obj3s  = okHttpSSSUtil.postDataTOJsonObjUpdate("http://10.116.32.63/ops/return",
                    "http://10.116.32.63/token", "MDM", "a1b2c3d4f5", "client_credentials", re);
            System.out.println(12);
        } catch (Exception e) {
            log.error("回更return3s变更typeid报错：" + e);
            throw e;
        }
    }
    @Test
    public void testP(){
        //1.调取3s接口  * GET http://10.116.32.63/check/v1?modelNo= 10-AFF2C-01B-JR&fields=64&language= zh-CHS
        JSONObject obj3s = okHttpSSSUtil.getDataTOJsonObj("http://10.116.32.63/check/v1","10-AFF2C-01B-JR",
                "64","zh-CHS","http://10.116.32.63/token","MDM","a1b2c3d4f5","client_credentials");
        log.info("调用3s接口："+obj3s.toJSONString());
        //2.存入 bom以及bomdetial表
        if(Objects.nonNull(obj3s) && !obj3s.isEmpty()){
            if(obj3s.getString("message") != null && obj3s.getString("message").equals("Serial Product")){
                String madeIn =  obj3s.getString("madeIn");
                if(StringUtils.isBlank(madeIn)){
                    System.out.println("madeIn");
                }
                if(madeIn.equals("CN") || madeIn.equals("YZ") || madeIn.equals("CM")|| madeIn.equals("JP")){//中国 CN 中国工厂 YZ  制造6课 CM 北京工厂
                    JSONArray arr = obj3s.getJSONArray("splits");
                    if(arr == null || arr.size()==0){
                        System.out.println("madeIn");
                    }
                    int defaultFlag = 0;//取第一条 或 IsDefaultWay=true
                    for(int i=0;i< arr.size();i++){
                        if(arr.getJSONObject(i).getBoolean("isDefaultWay")){
                            defaultFlag = i;
                        }
                    }
                     arr.getJSONObject(defaultFlag).getJSONArray("details");
                     Boolean flag = updateBomAndBomDetail(arr.getJSONObject(defaultFlag).getJSONArray("details"),"10-AFF2C-01B-JR");
                    System.out.println(flag);

                }
            }
        }
    }

    //更新bom
    public Boolean updateBomAndBomDetail(JSONArray arrDetails,String modelNo){
        if(arrDetails.size()>0){

            return true;
        }else {
            return false;
        }
    }

    @Test
    public void testPcoOrDo(){ //todo 测试wms环境
      /*  CancelDocOrderDto param = new CancelDocOrderDto();
        param.setWmId(opsPco.getId());
        param.setCustomerId("SMC");// 固定不变
        param.setDocNo(opsPco.getPcoId());// do_id 或pco_id
        param.setErpCancelReason(inputDto.getReason());// 取消原因
        param.setOrderType("KT"); // 如果是pco 固定kt; 如果是do 参加DoTypeEnum枚举对应关系的wltype
        param.setWarehouseId(opsPco.getWarehouseCode());
        doAndPcoList.add(param);
        List<CancelDocOrderDto> list = new ArrayList<>();
        list.add(param);*/
       /* list.add(param1);
        list.add(param2);*/
        List<CancelDocOrderDto> list = new ArrayList<>();
        CancelDocOrderDto doParam = new CancelDocOrderDto();
        doParam.setWmId(268694L);
        doParam.setCustomerId("SMC");// 固定不变
        doParam.setDocNo("DO10017506001001000");// do_id 或pco_id

        doParam.setErpCancelReason("门户");// 取消原因
        doParam.setOrderType("CM"); // 如果是pco 固定kt; 如果是do CM参加DoTypeEnum枚举对应关系的wltype
        doParam.setWarehouseId("KGZ");
        list.add(doParam);
        try {
            CommonResult r = opsCallWmsFeignApi.cancelDocOrder(list);
            int o = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int o = 0;
    }

    @Test
    public void testflRo(){
        CancelDocAsnDto param = new CancelDocAsnDto();
        param.setCustomerId("SMC");//固定不变
        param.setDocNo("DBR6220044020001000"); //ro_id
        param.setErpCancelReason("测试");//取消原因
        param.setAsnType(DoTypeEnum.DBCK.getWltype());  //RoTypeEnum 枚举对应关系的wltype
        param.setWarehouseId("KSH");
        CommonResult r = opsCallWmsFeignApi.cancelDocAsn(param);
        int o = 0;
    }

    @Test
    public void customerInfo(){
        CommonResult commonResult = opsSupplierFeignApi.getSupplierInfo("JP   ");
        int i= 0;
    }

    @Autowired
    private OkHttpAddTokenUtil okHttpAddTokenUtil;

    /**
     * 测试备案数据
     */
    @Test
    public void tt(){
        /*
         *
         * @param dataUrl http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded
         * @param plantMark 厂别：CN/BJ/AM.. 必填
         * @param materialType 物料类型：I:料件，E:成品，2:设备 非必填
         * @param list 型号集合，最大支持 500 个型
         * @param tokenUrl http://10.116.1.234:10300/auth/buoath/token
         * @param userName sales
         * @param password ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=
         * */
        List list = new ArrayList();
        list.add("KQ2N04-99");
        list.add("10-KQN11-99");
        List arr = okHttpAddTokenUtil.getData(" http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded",
                "XX","XX",list,"http://10.116.1.234:10300/auth/buoath/token","sales","ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=");
        //return  opsCustomerFeignApi.getCustomerInfo("95001");
        //CommonResult result = opsProductFeignApi.searchProduct("132");
        log.info(arr.toString());
    }

}

package com.smc.smccloud;

import cn.hutool.core.util.RandomUtil;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.borrowstock.BrwApplyDTO;
import com.smc.smccloud.model.borrowstock.BrwDetailDTO;
import com.smc.smccloud.service.BorrowStockService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowStockServiceTests extends OpsShareAppApplicationTests {

    @Resource
    private BorrowStockService borrowStockService;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;


    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Test
    public void t(){
        List<CreInvMoveForReturnOrderDto> list = new ArrayList<>();
        CreInvMoveForReturnOrderDto dto = new CreInvMoveForReturnOrderDto();
        dto.setSplitItemNo(0);
        dto.setOrderNo("12170682");
        dto.setOrderItem(1);
        dto.setApplyNo("TH"+ RandomUtil.randomString(6));
        dto.setApplyNo("TH2412020022");
        dto.setItemNo(1);
        dto.setModelNo("CDJ2E16-200AZ-B");
        dto.setQty(6);
        dto.setWarehouseCode("KGZ");
        dto.setPsnNo("TestC12961");// 创建人
        dto.setToUserStock(false);  // true :GKTY, false:TY
        dto.setCustomerNo("C12961");

        //returnConfirmHandler.returnConfirm(Collections.singletonList(dto));
        CreInvMoveForReturnOrderDto odto = new CreInvMoveForReturnOrderDto();
        odto.setSplitItemNo(0);
        odto.setOrderNo("12170682");
        odto.setOrderItem(4);
        odto.setApplyNo("TH"+ RandomUtil.randomString(6));
        odto.setApplyNo("TH2412020022");
        odto.setItemNo(1);
        odto.setModelNo("CDJ2E16-200AZ-B");
        odto.setQty(6);
        odto.setWarehouseCode("KGZ");
        odto.setPsnNo("TestC12961");// 创建人
        odto.setToUserStock(false);  // true :GKTY, false:TY
        odto.setCustomerNo("C12961");
        list.add(odto);
        list.add(dto);
        CommonResult<String> result = opsWmDispatchForOrderFeignApi.handReturnOrderConfirm(list);
        System.out.println(123);

    }

    @Test
    public void saveBrwStockApplyTest() {
        BrwApplyDTO dto = new BrwApplyDTO();
        dto.setCustomerName("张三");
        dto.setCustomerNo("A001");
        dto.setUserNo("B001");
        dto.setUserName("李四");
        dto.setBrwPsn("王五");
        dto.setPurpose("测试");
        dto.setRemark("测试");
        dto.setEsReturnDate(new Date());
        dto.setBrwType("1");
        dto.setDlvType("2");
        dto.setSalesPsn("赵六");
        dto.setSalesPsnTel("13333333333");
        dto.setReceiverName("田七");
        dto.setReceiverPhone("18888888888");
        dto.setTransType(12);
        dto.setTransFee(22);
        dto.setReceiverAddress("广州");
        dto.setAddressNo("999");
        dto.setDeptNo("DS");
        dto.setBrwTypeName("测试");
        dto.setBrwPsnDept("测试");
        dto.setBrwPsnTel("19855555555");
        dto.setEmployee("测试");
        dto.setPassDate(new Date());
        dto.setReceiverCompany("广州");

        BrwDetailDTO dto1 = new BrwDetailDTO();
        dto1.setItemId(1);
        dto1.setModelNo("ABCD-PP2");
        dto1.setQuantity(22);
        dto1.setModelName("完整型号");
        dto1.setRemark("测试");
        dto1.setInventoryId(12L);
        dto1.setInventoryKeys("keys");
        dto1.setReturnType("2");
        dto1.setReturnQty(10);
        dto1.setOptStatus(1);
        dto1.setExpQty(5);
        dto1.setLateReturnReason("测试");
        dto1.setLateReplyDate(new Date());
        dto1.setLateReplyPsn("老八");
        dto1.setWarehouseDode("GZ");
        dto1.setShipDate(new Date());
        List<BrwDetailDTO> dtos = new ArrayList<>();
        dtos.add(dto1);
        dto.setDetails(dtos);

        borrowStockService.saveBrwStockApply(dto);

    }

    @Test
    public void tet(){
//        String monthCode = DateUtil.getSimpleYearMonthCode(new Date());
//        int day = DateUtil.getDay(new Date());
//        System.out.println(monthCode+ day);
//
//        String a= "{yymmdd}0001";
//        String substring = a.substring(8, 12);
//        System.out.println(monthCode+ day+substring);
        ResultVo<DataTypeVO> brwNo =dictDataServiceFeignApi.getDataTypeCodesInfo("1003", "CN0");
        DataTypeVO data = brwNo.getData();
        System.out.println(data.getExtNote1());
    }
}

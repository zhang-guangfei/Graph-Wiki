package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.common.BomSelectResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存出库算法输入参数
 * @date 2021/9/12 20:08
 */
public class InventoryCkByOrderInputDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4205238935030027070L;

    private HashMap<Long, List<OpsInventory>> invMap = new HashMap<Long, List<OpsInventory>>();//提前加载库存

    private HashMap<Long, List<OpsInventoryMove>> moveInvMap = new HashMap<Long, List<OpsInventoryMove>>();//提前加载库存

    private Boolean assModelRule = false;//拆分型号是否获取过库存  BUGID:17319

    private Boolean bomFlag = false;//是否获取过bom

    private HashMap<String, List<OpsInventoryProperty>> invPropertyMap = new HashMap<>();

    private HashMap<String, List<RiskDto>> riskMap = new HashMap<>();

    private BomSelectResult bomReuslt = null;

    private String orderFno;
    /* 客户单号必填 */
    private String OrderId;
    /* 客户行号必填 */
    private String OrderItem;
    /* 数量拆分 */
    private int qtyItem;
    /* 型号拆分 */
    private int assItem;
    /* 型号必填 */
    private String modelno;

    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    /* 要出库数量 */
    private Integer quantity;
    /* 已分配数量--无需填写 */
    private Integer AllotQuantity;
    /* 分配状态：T分配完成 F未完成 */
    private boolean AllotStatus;
    /* 阀汇流板标识 */
    private String specmark;
    /* 单据类型 */
    private String OrderType;
    /* 订单标识 */
    private String tag;
    /* 出库方式：货齐出库 */
    private CKTYPEEnum CKType;
    /* 交货期 */
    private Date hopeDate;
    /* 物流发货期 */
    private Date WlDate;
    /* 分包方式 */
    private String PakageType;
    /* 终端用户 */
    private String userNo;
    // rcvmaster 表上的endUser
    private String endUserNo;
    /* 客户号 */
    private String Customer;
    /* 客户类型 */
    private String CustomerType;
    /* 出库库存属性 */
    private String propertyType;
    /* 备注1 */
    private String remark1;
    /* 备注2 */
    private String remark2;
    /* 采购用到E价格 */
    private BigDecimal eprice;

    /* 价格 */
    private BigDecimal price;

    private Date acceptDate;
    private String deptno;
    /* 客户采购单号 */
    private String corderNo;
    /* 客户合同号 */
    private String contractNo;
    /* 客户产品名称 */
    private String productName;
    /* 客户产品编码 */
    private String cproductNo;
    /* 特殊标识 1特发2普通订单*/
    private Integer spceialFlag;

    private String province;

    private String city;

    private String district;

    private String street;

    private String address;

    private String linkman;

    private String mobile;

    private String phone;

    private String postcode;

    private UserDto userDto;

    private String warehouseCode;

    private String shikomiNo;

    // 营业情报号
    private String preSaleNo;
    // 80845871
    // rosh
    private String productTag;

    private String productTagRemark;

    private String DlvSite;

    private Integer expDlvType;

    private boolean itemUnlimit;//是不是随到随发

    private RcvOrderStatusEnum rcvOrderStatusEnum = RcvOrderStatusEnum.WAITCK;

    private String expLinkNo;//关联号

    private String applyDeptNo;

    private String carried;

    private Date ROrdDate;

    //bugid:10579 20230424 c14717 订单还原标识
    private Boolean orderInitFlag = false;

    //发货邮箱 bugid:12391 c14717 2023/10/27
    private String email;

    //
    private String inqBNo;

    //用于转定转采购
    public InventoryCkByOrderInputDto(Rcvmaster rcvmaster, Rcvdetail rcvdetail) {
        this.orderFno = rcvdetail.getRorderFno();
        this.ROrdDate = rcvmaster.getRorddate();
        this.OrderId = rcvmaster.getRorderNo();
        this.OrderItem = rcvdetail.getRorderItem().toString();
        this.modelno = rcvdetail.getModelNo();
        this.ppl = rcvdetail.getPplNo();
        this.projectCode = rcvdetail.getProjectNo();
        this.groupCustomerNo = rcvdetail.getGroupCustomerNo();
        this.quantity = rcvdetail.getQuantity();
        this.AllotQuantity = 0;
        this.AllotStatus = false;
        this.OrderType = String.valueOf(rcvdetail.getOrderType());
        this.specmark = rcvdetail.getSpecMark();
        if (!StringUtils.isEmpty(rcvdetail.getPplNo())
                || !StringUtils.isEmpty(rcvdetail.getProjectNo())
                || !StringUtils.isEmpty(rcvdetail.getGroupCustomerNo())) {
            this.propertyType = InventoryPropertyTypeEnum.FORWARD.getType();
        } else {
            this.propertyType = InventoryPropertyTypeEnum.NOMAL.getType();
        }
        //特殊标识判断
        if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NOT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ONE_ASS.getCode();
        }else  if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ALL_ASS.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.MUST_WT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT_ONE_ASS.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT_ALL_ASS.getCode();
        } else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS.getCode();
        } else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_ONE_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_ALL_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_MUST_WT.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT_ONE_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT_ALL_ASS.getCode();
        }

        //货齐出货: 0-随到随发; 1-货齐-单仓; 2-随发-分批; 3-货齐-多仓;
        this.CKType = CKTYPEEnum.getCode(rcvmaster.getDlvEntire());
        this.hopeDate = rcvdetail.getDlvDate();// 客户交货期
        this.WlDate = rcvdetail.getWmsDlvDate();// 物流发货期
        this.PakageType =  rcvmaster.getDlvtype(); // 集约方式; 分包
        this.userNo = rcvmaster.getUserNo();
        this.Customer = rcvmaster.getCustomerNo();
        //20240711 bugid:14624
        this.endUserNo = rcvmaster.getEndUser();
        /* this.CustomerType = "";//// TODO: 2021/10/26 无此字段 */
        this.remark1 = rcvdetail.getRemark();
        this.deptno = StringUtils.isEmpty(rcvmaster.getDeliveryDeptNo()) ? rcvmaster.getDeptNo() : rcvmaster.getDeliveryDeptNo();
        this.applyDeptNo = rcvmaster.getDeptNo();
        this.eprice = rcvdetail.getEprice();

        this.price = rcvdetail.getPrice();
        this.preSaleNo = rcvdetail.getSalesInfoNo();
        this.contractNo = rcvmaster.getContractno();
        this.corderNo = rcvdetail.getCorderNo();
        this.productName = rcvdetail.getProductName();
        this.cproductNo = rcvdetail.getCproductNo();
        this.shikomiNo = rcvdetail.getShikomiNo();
        if (rcvdetail.getExpDlvType() != null) {
            this.productTag = (rcvdetail.getExpDlvType() & 64) == 64 ? "0" : "9";
            this.productTagRemark = (rcvdetail.getExpDlvType() & 64) == 64 ? "rosh" : "其他";
        }
        this.expDlvType = rcvdetail.getExpDlvType();
        this.expLinkNo = rcvdetail.getExpLinkNo();
        if(CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){
            this.itemUnlimit = true;
        } else{
            this.itemUnlimit = false;
        }
        this.inqBNo = rcvdetail.getInqbApplyNo();

    }

    // 用于订单分配
    public InventoryCkByOrderInputDto(Rcvmaster rcvmaster, Rcvdetail rcvdetail, Orderdlvdata orderDlvData) {
        this.orderFno = rcvdetail.getRorderFno();
        /*
         * this.province = province; this.city = city; this.district = district;
         * this.street = street; this.address = address; this.linkman = linkman;
         * this.mobile = mobile; this.phone = phone; this.userDto = userDto;
         */
        //收货信息
        if(orderDlvData != null){//todo 如果没有是否报错 是否自提字段需要加上
            this.province = orderDlvData.getProvince();
            this.city = orderDlvData.getCity();
            this.district = orderDlvData.getDistrict();
            this.street = orderDlvData.getDlvaddress();
            this.address = orderDlvData.getDlvaddress();
            this.linkman = orderDlvData.getContactpsn();
            if (!StringUtils.isEmpty(orderDlvData.getTelno())) {
                String phoneSplit =  phoneUtilDto.replaceSpaceStr2(orderDlvData.getTelno());
                for (String phone : phoneSplit.split(";")) {
                    if(phoneUtilDto.isPhone(phone)){//电话
                        if(phoneUtilDto.isMobil(phone)){//手机号
                            this.mobile = phone;
                        }else{//固话
                            this.phone = phone;
                        }
                    }
                }
            }

            this.postcode = orderDlvData.getPostcode();
            //特殊标识判断
            if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ApplySpecExport) ){
                this.spceialFlag = 1;//1 为特发 2 普通订单 自提不在这里边
            }
            if(!StringUtils.isEmpty(orderDlvData.getCarrierid()) && !CarrierEnum.NON.getCode().equals( orderDlvData.getCarrierid())){
                this.carried = orderDlvData.getCarrierid(); //指定承运商
            }
            //发货邮箱 bugid:12391 c14717 2023/10/27
            this.email = orderDlvData.getEmail();
        }

        this.ROrdDate = rcvmaster.getRorddate();
        this.OrderId = rcvmaster.getRorderNo();
        this.OrderItem = rcvdetail.getRorderItem().toString();
        this.modelno = rcvdetail.getModelNo();
        this.ppl = rcvdetail.getPplNo();
        this.projectCode = rcvdetail.getProjectNo();
        this.groupCustomerNo = rcvdetail.getGroupCustomerNo();
        this.quantity = rcvdetail.getQuantity();
        this.AllotQuantity = 0;
        this.AllotStatus = false;
        this.OrderType = String.valueOf(rcvdetail.getOrderType());
        this.specmark = rcvdetail.getSpecMark();
        if (!StringUtils.isEmpty(rcvdetail.getPplNo())
                || !StringUtils.isEmpty(rcvdetail.getProjectNo())
                || !StringUtils.isEmpty(rcvdetail.getGroupCustomerNo())) {
            this.propertyType = InventoryPropertyTypeEnum.FORWARD.getType();
        } else {
            this.propertyType = InventoryPropertyTypeEnum.NOMAL.getType();
        }


        //特殊标识判断
        if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
        !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
        !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NOT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ONE_ASS.getCode();
        }else  if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ALL_ASS.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.MUST_WT.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT_ONE_ASS.getCode();
        }else if( !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.NO_WT_ALL_ASS.getCode();
        } else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS.getCode();
        } else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_ONE_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_ALL_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_MUST_WT.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT_ONE_ASS.getCode();
        }else if( OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.ROSH10Product) &&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.MustOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.NotOrderToCSStock) &&
                OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)&&
                !OrderSpecExpType.include(rcvdetail.getExpDlvType(),OrderSpecExpType.OneItemToAssemble)){
            this.tag = CKTagEnum.ROHS_NO_WT_ALL_ASS.getCode();
        }

        //货齐出货: 0-随到随发; 1-货齐-单仓; 2-随发-分批; 3-货齐-多仓;
        this.CKType = CKTYPEEnum.getCode(rcvmaster.getDlvEntire());

        this.hopeDate = rcvdetail.getDlvDate();// 客户交货期
        this.WlDate = rcvdetail.getWmsDlvDate();// 物流发货期
        this.PakageType =  rcvmaster.getDlvtype(); // 集约方式; 分包
        this.userNo = rcvmaster.getUserNo();
        this.Customer = rcvmaster.getCustomerNo();
        //20240711 bugid:14624
        this.endUserNo = rcvmaster.getEndUser();
        /* this.CustomerType = "";//// TODO: 2021/10/26 无此字段 */
        this.remark1 = rcvdetail.getRemark();
        this.deptno = StringUtils.isEmpty(rcvmaster.getDeliveryDeptNo()) ? rcvmaster.getDeptNo() : rcvmaster.getDeliveryDeptNo();
        this.applyDeptNo = rcvmaster.getDeptNo();
        this.eprice = rcvdetail.getEprice();

        this.price = rcvdetail.getPrice();
        this.preSaleNo = rcvdetail.getSalesInfoNo();
        this.contractNo = rcvmaster.getContractno();
        this.corderNo = rcvdetail.getCorderNo();
        this.productName = rcvdetail.getProductName();
        this.cproductNo = rcvdetail.getCproductNo();
        this.shikomiNo = rcvdetail.getShikomiNo();
        if (rcvdetail.getExpDlvType() != null) {
            this.productTag = (rcvdetail.getExpDlvType() & 64) == 64 ? "0" : "9";
            this.productTagRemark = (rcvdetail.getExpDlvType() & 64) == 64 ? "rosh" : "其他";
        }
        this.expDlvType = rcvdetail.getExpDlvType();
        this.DlvSite = orderDlvData.getDlvflag();// 是否直发 orderDlvData.getDlvflag()
        this.expLinkNo = rcvdetail.getExpLinkNo();
        if(CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){
            this.itemUnlimit = true;
        } else{
            this.itemUnlimit = false;
        }
        this.inqBNo = rcvdetail.getInqbApplyNo();


        /*
         * this.province = province; this.city = city; this.district = district;
         * this.street = street; this.address = address; this.linkman = linkman;
         * this.mobile = mobile; this.phone = phone; this.userDto = userDto;
         */

    }


    public InventoryCkByOrderInputDto(InventoryForAdjustDto adjustDto, String orderType) {
        this.modelno = adjustDto.getModelno();
        this.ppl = adjustDto.getPpl();
        this.projectCode = adjustDto.getProjectCode();
        this.groupCustomerNo = adjustDto.getGroupCustomerNo();
        this.quantity = adjustDto.getQty();
        this.AllotQuantity = 0;
        this.AllotStatus = false;
        this.OrderType = String.valueOf(orderType);

        if (!StringUtils.isEmpty(adjustDto.getPpl()) || !StringUtils.isEmpty(adjustDto.getProjectCode()) || !StringUtils.isEmpty(adjustDto.getGroupCustomerNo())) {
            this.propertyType = InventoryPropertyTypeEnum.FORWARD.getType();
        } else {
            this.propertyType = InventoryPropertyTypeEnum.NOMAL.getType();
        }
        this.Customer = adjustDto.getCustomerNo();
        this.remark1 = adjustDto.getRemark();
        this.deptno = adjustDto.getDeptno();
        this.preSaleNo = adjustDto.getSalesInfoNo();
    }

    public InventoryCkByOrderInputDto() {
    }

    public HashMap<Long, List<OpsInventory>> getInvMap() {
        return invMap;
    }

    public void setInvMap(HashMap<Long, List<OpsInventory>> invMap) {
        this.invMap = invMap;
    }

    public HashMap<Long, List<OpsInventoryMove>> getMoveInvMap() {
        return moveInvMap;
    }

    public void setMoveInvMap(HashMap<Long, List<OpsInventoryMove>> moveInvMap) {
        this.moveInvMap = moveInvMap;
    }

    public Date getROrdDate() {
        return ROrdDate;
    }

    public void setROrdDate(Date ROrdDate) {
        this.ROrdDate = ROrdDate;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public int getQtyItem() {
        return qtyItem;
    }

    public void addQtyItem() {
        this.qtyItem++;
    }

    public void setQtyItem(Integer qtyItem){
        this.qtyItem = qtyItem;
    }

    public int getAssItem() {
        return assItem;
    }

    public void addAssItem() {
        this.assItem++;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(String orderItem) {
        OrderItem = orderItem;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CKTYPEEnum getCKType() {
        return CKType;
    }

    public void setCKType(CKTYPEEnum CKType) {
        this.CKType = CKType;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public Date getWlDate() {
        return WlDate;
    }

    public void setWlDate(Date wlDate) {
        WlDate = wlDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public String getPakageType() {
        return PakageType;
    }

    public void setPakageType(String pakageType) {
        PakageType = pakageType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public Integer getAllotQuantity() {
        return AllotQuantity;
    }

    public void setAllotQuantity(Integer allotQuantity) {
        AllotQuantity = allotQuantity;
        if (!quantity.equals(AllotQuantity)) {
            AllotStatus = Boolean.FALSE;
        }
    }

    public void addAllotQuantity(Integer qty) {
        AllotQuantity = AllotQuantity + qty;
        if (quantity.equals(AllotQuantity)) {
            AllotStatus = Boolean.TRUE;
        }
    }
    public void subAllotQuantity(Integer qty) {
        AllotQuantity = AllotQuantity - qty;
        if (quantity.equals(AllotQuantity)) {
            AllotStatus = Boolean.TRUE;
        }else {
            AllotStatus = Boolean.FALSE;
        }
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isAllotStatus() {
        return AllotStatus;
    }

    public void setAllotStatus(boolean allotStatus) {
        AllotStatus = allotStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo;
    }

    public String getPreSaleNo() {
        return preSaleNo;
    }

    public void setPreSaleNo(String preSaleNo) {
        this.preSaleNo = preSaleNo;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    public String getProductTagRemark() {
        return productTagRemark;
    }

    public void setProductTagRemark(String productTagRemark) {
        this.productTagRemark = productTagRemark;
    }

    public RcvOrderStatusEnum getCustOrderStatusEnum() {
        return rcvOrderStatusEnum;
    }

    public void setCustOrderStatusEnum(RcvOrderStatusEnum rcvOrderStatusEnum) {
        this.rcvOrderStatusEnum = rcvOrderStatusEnum;
    }

    public String getDlvSite() {
        return DlvSite;
    }

    public void setDlvSite(String dlvSite) {
        DlvSite = dlvSite;
    }

    public Integer getExpDlvType() {
        return expDlvType;
    }

    public void setExpDlvType(Integer expDlvType) {
        this.expDlvType = expDlvType;
    }

    public String getExpLinkNo() {
        return expLinkNo;
    }

    public void setExpLinkNo(String expLinkNo) {
        this.expLinkNo = expLinkNo;
    }

    public boolean isItemUnlimit() {
        return itemUnlimit;
    }

    public void setItemUnlimit(boolean itemUnlimit) {
        this.itemUnlimit = itemUnlimit;
    }


    public Integer getSpceialFlag() {
        return spceialFlag;
    }

    public void setSpceialFlag(Integer spceialFlag) {
        this.spceialFlag = spceialFlag;
    }

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo;
    }

    public Boolean getOrderInitFlag() {
        return orderInitFlag;
    }

    public void setOrderInitFlag(Boolean orderInitFlag) {
        this.orderInitFlag = orderInitFlag;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getEmail() {
        return email;
    }

    public String getInqBNo() {
        return inqBNo;
    }

    public void setInqBNo(String inqBNo) {
        this.inqBNo = inqBNo;
    }

    public String getEndUserNo() {
        return endUserNo;
    }

    public void setEndUserNo(String endUserNo) {
        this.endUserNo = endUserNo;
    }

    public Boolean getAssModelRule() {
        return assModelRule;
    }

    public void setAssModelRule(Boolean assModelRule) {
        this.assModelRule = assModelRule;
    }

    public Boolean getBomFlag() {
        return bomFlag;
    }

    public void setBomFlag(Boolean bomFlag) {
        this.bomFlag = bomFlag;
    }

    public BomSelectResult getBomReuslt() {
        return bomReuslt;
    }

    public void setBomReuslt(BomSelectResult bomReuslt) {
        this.bomReuslt = bomReuslt;
    }

    public HashMap<String, List<OpsInventoryProperty>> getInvPropertyMap() {
        return invPropertyMap;
    }

    public void setInvPropertyMap(HashMap<String, List<OpsInventoryProperty>> invPropertyMap) {
        this.invPropertyMap = invPropertyMap;
    }

    public HashMap<String, List<RiskDto>> getRiskMap() {
        return riskMap;
    }

    public void setRiskMap(HashMap<String, List<RiskDto>> riskMap) {
        this.riskMap = riskMap;
    }
}

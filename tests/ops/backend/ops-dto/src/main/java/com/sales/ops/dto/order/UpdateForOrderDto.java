package com.sales.ops.dto.order;

import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.inventory.phoneUtilDto;
import com.sales.ops.dto.util.UserDto;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author C02483
 * @version 1.0
 * @description: 订单更新类
 * @date 2021/10/25 13:30
 */
public class UpdateForOrderDto {

    /* 客户单号 */
    private String orderId;
    /* 客户行号 */
    private Integer orderItem;

    /* 是否整单 */
    private Boolean master;
    /* 出库方式 */
    private String dlvEntire;
    /* 集约方式 */
    private String dlvType;

    /* 客户交货 交货期 */
    private Date dlvDate;
    /* 指定物流交货日期 */
    private Date wmsDlvDate;
    /*时效标记*/
    private Boolean expDlvTypeFlag = false; //0：普通 1：特发   true：特发  false：普通
    /*是否拆分子项特发*/
    private Boolean assModelChangeDo = false;  // true：子项特发 false：组装指令

    //交货地点 1：直发客户 2：直发营业所 3：所自提
    private String dlvSite;
    /* 是否特发 */
    private Integer specialFlag; //1 特发订单 2 普通订单
    /* 承运商 */
    private String carried;
    /*发货仓*/
    private String warehouseCode;

    /* 省 */
    private String province;
    /* 市 */
    private String city;
    /* 区 */
    private String district;
    /* 邮编 */
    private String postcode;


    private String customerNo;  //客户代码
    private String customerName;//客户名称
    private String dlvAddress;  //收货完整地址
    /* 联系人 */
    private String linkman; // 收货人 ContactPsn
    /* 电话 */
    private String phone;// TelNo
    /* 手机 */
    private String mobile;
    private String DeptNo;
    private String idCard;
    private Date arrivedDate;// 到货方式

    private String cproductNo;
    private String corderNo;


    /* 更改原因*/
    private String remark1;
    /* 更改内容 */
    private String remark2;

    /* 用户信息 */
    private UserDto userDto;

    /* 邮箱 */
    private String email;

    public UpdateForOrderDto() {
    }

    public UpdateForOrderDto(Orderdlvdata dlvdata) {
        this.orderId = dlvdata.getOrderno();
        this.orderItem = dlvdata.getItemno();
        this.customerName = dlvdata.getCstmname();
        this.dlvAddress = dlvdata.getDlvaddress();
        this.linkman = dlvdata.getContactpsn();
        if (!StringUtils.isBlank(dlvdata.getTelno())) {//C14717 2022/10/24 BUG号8435
            String phoneSplit =  phoneUtilDto.replaceSpaceStr2(dlvdata.getTelno());
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
        this.email = dlvdata.getEmail();
        this.idCard = dlvdata.getIdcard();
        this.province = dlvdata.getProvince();
        this.city = dlvdata.getCity();
        this.district = dlvdata.getDistrict();
        this.postcode = dlvdata.getPostcode();
        this.carried = dlvdata.getCarrierid();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }

    public String getDlvType() {
        return dlvType;
    }

    public void setDlvType(String dlvType) {
        this.dlvType = dlvType;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }

    public Boolean getExpDlvTypeFlag() {
        return expDlvTypeFlag;
    }

    public void setExpDlvTypeFlag(Boolean expDlvTypeFlag) {
        this.expDlvTypeFlag = expDlvTypeFlag;
    }

    public Boolean getAssModelChangeDo() {
        return assModelChangeDo;
    }

    public void setAssModelChangeDo(Boolean assModelChangeDo) {
        this.assModelChangeDo = assModelChangeDo;
    }

    public String getDlvSite() {
        return dlvSite;
    }

    public void setDlvSite(String dlvSite) {
        this.dlvSite = dlvSite;
    }

    public Integer getSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(Integer specialFlag) {
        this.specialFlag = specialFlag;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDlvAddress() {
        return dlvAddress;
    }

    public void setDlvAddress(String dlvAddress) {
        this.dlvAddress = dlvAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptNo() {
        return DeptNo;
    }

    public void setDeptNo(String deptNo) {
        DeptNo = deptNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UpdateForOrderDto{" +
                "orderId='" + orderId + '\'' +
                ", orderItem=" + orderItem +
                ", master=" + master +
                ", dlvEntire='" + dlvEntire + '\'' +
                ", dlvType='" + dlvType + '\'' +
                ", dlvDate=" + dlvDate +
                ", wmsDlvDate=" + wmsDlvDate +
                ", expDlvTypeFlag=" + expDlvTypeFlag +
                ", assModelChangeDo=" + assModelChangeDo +
                ", dlvSite='" + dlvSite + '\'' +
                ", specialFlag=" + specialFlag +
                ", carried='" + carried + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", postcode='" + postcode + '\'' +
                ", customerNo='" + customerNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", dlvAddress='" + dlvAddress + '\'' +
                ", linkman='" + linkman + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", DeptNo='" + DeptNo + '\'' +
                ", idCard='" + idCard + '\'' +
                ", arrivedDate=" + arrivedDate +
                ", cproductNo='" + cproductNo + '\'' +
                ", corderNo='" + corderNo + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", email='" + email + '\'' +
                ", userDto=" + userDto.getUserName() +
                '}';
    }


}

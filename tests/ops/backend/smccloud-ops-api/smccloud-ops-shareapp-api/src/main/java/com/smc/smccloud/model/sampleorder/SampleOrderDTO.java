package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SampleOrderDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 申请号
     */
    @NotEmpty
    private String applyNo;

    /**
     * 接单订单号
     */
    private String orderNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户单号
     */
    private String corderNo;

    /**
     * 客户代码
     */
    // @NotEmpty
    private String customerNo;

    /**
     * 用户代码
     */
    // @NotEmpty
    private String userNo;

    /**
     * 申请部门
     */
    @NotEmpty
    private String applyDeptNo;

    /**
     * 申请类型
     *  1  -  试用品免费试用
     *  2  -  试用品待转销售
     *  3  -  展览展示品
     *  4  -  盘亏报损
     *  5  -  CTC实验品
     *  6  -  故障替代品
     *  7  -  维修品
     */
    @NotEmpty
    private String applyType;

    /**
     * 订单结转类型
     *  101  -    赠品
     *  102  -    样品
     *  103  -    已报废处理
     *  104  -    维修品
     *  201  -    展览展示品
     *  301  -    转销售开票
     *  401  -    良品返回
     */
    @NotEmpty
    private String costType;

    /**
     *  申请类型名称
     */
    @NotEmpty
    private String applyTypeName;

    /**
     * 订单结转类型名称
     */
    @NotEmpty
    private String costTypeName;

    /**
     * 申请目的
     */
    private String purpose;

    /**
     * 费用承担部门
     */
    private String deptNo;

    // 索赔号
    private String claimNo;

    // 索赔金额
    private String claimAmount;

    // 索赔公司
    private String expressCompany;

    /**
     * 出货方式
     */
    @NotEmpty
    private String dlvEntire;

    /**
     * 出货类别1
     */
    private String dlvType1;

    /**
     * 出货类别2
     */
    private String dlvType2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收货地址类别
     */
    private Integer addressType;

    /**
     * 地址编号
     */
    private String addressNo;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 生成订单时间
     */
    private Date orderDate;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 处理人
     */
    private String answerPsn;

    /**
     * 处理时间
     */
    private Date answerTime;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 申请人
     */
    private String applyPsn;

    /**
     * 收货人地址
     */
    @NotEmpty
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @NotEmpty
    private String receiverPhone;

    /**
     * 收货人
     */
    @NotEmpty
    private String receiverName;

    /**
     * 收货公司
     */
    private String receiverCompany;

    // @NotEmpty
    private String tradeCompanyId;

    private String receiverPostCode;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverStateCode;

    private String receiverCarrierId;

    private String deliveryDeptNo;

    /**
     *  List<SampleOrderDetail>
     */
    @Valid
    @NotEmpty
    private List<SampleOrderDetailVO> detailItem;

}

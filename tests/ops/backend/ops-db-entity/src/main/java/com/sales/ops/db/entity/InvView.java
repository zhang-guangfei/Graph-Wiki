package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class InvView implements Serializable {
    private String 营业所;

    private String 接单号;

    private String 客户号;

    private String 用户号;

    private Date 接单时间;

    private Date 客户交货期;

    private Date 指定物流发货日;

    private Short 订单类型;

    private String 出货方式;

    private String 是否组装;

    private Integer 特殊标识;

    private String 接单型号;

    private Integer 接单数量;

    private Integer 拆分数量总和;

    private Integer 分配项号;

    private String 分配类型;

    private String 发货仓;

    private String 型号;

    private Integer 拆分数量;

    private String 出库区分类别;

    private String 出库区分代码;

    private String 出库区分分类;

    private static final long serialVersionUID = 1L;

    public String get营业所() {
        return 营业所;
    }

    public void set营业所(String 营业所) {
        this.营业所 = 营业所 == null ? null : 营业所.trim();
    }

    public String get接单号() {
        return 接单号;
    }

    public void set接单号(String 接单号) {
        this.接单号 = 接单号 == null ? null : 接单号.trim();
    }

    public String get客户号() {
        return 客户号;
    }

    public void set客户号(String 客户号) {
        this.客户号 = 客户号 == null ? null : 客户号.trim();
    }

    public String get用户号() {
        return 用户号;
    }

    public void set用户号(String 用户号) {
        this.用户号 = 用户号 == null ? null : 用户号.trim();
    }

    public Date get接单时间() {
        return 接单时间;
    }

    public void set接单时间(Date 接单时间) {
        this.接单时间 = 接单时间;
    }

    public Date get客户交货期() {
        return 客户交货期;
    }

    public void set客户交货期(Date 客户交货期) {
        this.客户交货期 = 客户交货期;
    }

    public Date get指定物流发货日() {
        return 指定物流发货日;
    }

    public void set指定物流发货日(Date 指定物流发货日) {
        this.指定物流发货日 = 指定物流发货日;
    }

    public Short get订单类型() {
        return 订单类型;
    }

    public void set订单类型(Short 订单类型) {
        this.订单类型 = 订单类型;
    }

    public String get出货方式() {
        return 出货方式;
    }

    public void set出货方式(String 出货方式) {
        this.出货方式 = 出货方式 == null ? null : 出货方式.trim();
    }

    public String get是否组装() {
        return 是否组装;
    }

    public void set是否组装(String 是否组装) {
        this.是否组装 = 是否组装 == null ? null : 是否组装.trim();
    }

    public Integer get特殊标识() {
        return 特殊标识;
    }

    public void set特殊标识(Integer 特殊标识) {
        this.特殊标识 = 特殊标识;
    }

    public String get接单型号() {
        return 接单型号;
    }

    public void set接单型号(String 接单型号) {
        this.接单型号 = 接单型号 == null ? null : 接单型号.trim();
    }

    public Integer get接单数量() {
        return 接单数量;
    }

    public void set接单数量(Integer 接单数量) {
        this.接单数量 = 接单数量;
    }

    public Integer get拆分数量总和() {
        return 拆分数量总和;
    }

    public void set拆分数量总和(Integer 拆分数量总和) {
        this.拆分数量总和 = 拆分数量总和;
    }

    public Integer get分配项号() {
        return 分配项号;
    }

    public void set分配项号(Integer 分配项号) {
        this.分配项号 = 分配项号;
    }

    public String get分配类型() {
        return 分配类型;
    }

    public void set分配类型(String 分配类型) {
        this.分配类型 = 分配类型 == null ? null : 分配类型.trim();
    }

    public String get发货仓() {
        return 发货仓;
    }

    public void set发货仓(String 发货仓) {
        this.发货仓 = 发货仓 == null ? null : 发货仓.trim();
    }

    public String get型号() {
        return 型号;
    }

    public void set型号(String 型号) {
        this.型号 = 型号 == null ? null : 型号.trim();
    }

    public Integer get拆分数量() {
        return 拆分数量;
    }

    public void set拆分数量(Integer 拆分数量) {
        this.拆分数量 = 拆分数量;
    }

    public String get出库区分类别() {
        return 出库区分类别;
    }

    public void set出库区分类别(String 出库区分类别) {
        this.出库区分类别 = 出库区分类别 == null ? null : 出库区分类别.trim();
    }

    public String get出库区分代码() {
        return 出库区分代码;
    }

    public void set出库区分代码(String 出库区分代码) {
        this.出库区分代码 = 出库区分代码 == null ? null : 出库区分代码.trim();
    }

    public String get出库区分分类() {
        return 出库区分分类;
    }

    public void set出库区分分类(String 出库区分分类) {
        this.出库区分分类 = 出库区分分类 == null ? null : 出库区分分类.trim();
    }
}
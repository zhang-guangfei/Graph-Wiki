package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigRcvdetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MigRcvdetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRordernoIsNull() {
            addCriterion("ROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("ROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("ROrderNo =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("ROrderNo <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("ROrderNo >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ROrderNo >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("ROrderNo <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("ROrderNo <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("ROrderNo like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("ROrderNo not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("ROrderNo in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("ROrderNo not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("ROrderNo between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("ROrderNo not between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNull() {
            addCriterion("ROrderItem is null");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNotNull() {
            addCriterion("ROrderItem is not null");
            return (Criteria) this;
        }

        public Criteria andRorderitemEqualTo(String value) {
            addCriterion("ROrderItem =", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotEqualTo(String value) {
            addCriterion("ROrderItem <>", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThan(String value) {
            addCriterion("ROrderItem >", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThanOrEqualTo(String value) {
            addCriterion("ROrderItem >=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThan(String value) {
            addCriterion("ROrderItem <", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThanOrEqualTo(String value) {
            addCriterion("ROrderItem <=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLike(String value) {
            addCriterion("ROrderItem like", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotLike(String value) {
            addCriterion("ROrderItem not like", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemIn(List<String> values) {
            addCriterion("ROrderItem in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotIn(List<String> values) {
            addCriterion("ROrderItem not in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemBetween(String value1, String value2) {
            addCriterion("ROrderItem between", value1, value2, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotBetween(String value1, String value2) {
            addCriterion("ROrderItem not between", value1, value2, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("Quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("Quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("Quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("Quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("Quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("Quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("Quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("Quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("Quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("Quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("Quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("Price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("Price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("Price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("Price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("Price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("Price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("Price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("Price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNull() {
            addCriterion("DlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("DlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterion("DlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterion("DlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterion("DlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterion("DlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterion("DlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterion("DlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterion("DlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate not between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNull() {
            addCriterion("DlvType is null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNotNull() {
            addCriterion("DlvType is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeEqualTo(String value) {
            addCriterion("DlvType =", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotEqualTo(String value) {
            addCriterion("DlvType <>", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThan(String value) {
            addCriterion("DlvType >", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("DlvType >=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThan(String value) {
            addCriterion("DlvType <", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThanOrEqualTo(String value) {
            addCriterion("DlvType <=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLike(String value) {
            addCriterion("DlvType like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotLike(String value) {
            addCriterion("DlvType not like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIn(List<String> values) {
            addCriterion("DlvType in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotIn(List<String> values) {
            addCriterion("DlvType not in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeBetween(String value1, String value2) {
            addCriterion("DlvType between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotBetween(String value1, String value2) {
            addCriterion("DlvType not between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNull() {
            addCriterion("OptCode is null");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNotNull() {
            addCriterion("OptCode is not null");
            return (Criteria) this;
        }

        public Criteria andOptcodeEqualTo(String value) {
            addCriterion("OptCode =", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotEqualTo(String value) {
            addCriterion("OptCode <>", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThan(String value) {
            addCriterion("OptCode >", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("OptCode >=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThan(String value) {
            addCriterion("OptCode <", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThanOrEqualTo(String value) {
            addCriterion("OptCode <=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLike(String value) {
            addCriterion("OptCode like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotLike(String value) {
            addCriterion("OptCode not like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeIn(List<String> values) {
            addCriterion("OptCode in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotIn(List<String> values) {
            addCriterion("OptCode not in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeBetween(String value1, String value2) {
            addCriterion("OptCode between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotBetween(String value1, String value2) {
            addCriterion("OptCode not between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNull() {
            addCriterion("OptState is null");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNotNull() {
            addCriterion("OptState is not null");
            return (Criteria) this;
        }

        public Criteria andOptstateEqualTo(String value) {
            addCriterion("OptState =", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotEqualTo(String value) {
            addCriterion("OptState <>", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThan(String value) {
            addCriterion("OptState >", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThanOrEqualTo(String value) {
            addCriterion("OptState >=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThan(String value) {
            addCriterion("OptState <", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThanOrEqualTo(String value) {
            addCriterion("OptState <=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLike(String value) {
            addCriterion("OptState like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotLike(String value) {
            addCriterion("OptState not like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateIn(List<String> values) {
            addCriterion("OptState in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotIn(List<String> values) {
            addCriterion("OptState not in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateBetween(String value1, String value2) {
            addCriterion("OptState between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotBetween(String value1, String value2) {
            addCriterion("OptState not between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIsNull() {
            addCriterion("RcvClassify is null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIsNotNull() {
            addCriterion("RcvClassify is not null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyEqualTo(String value) {
            addCriterion("RcvClassify =", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotEqualTo(String value) {
            addCriterion("RcvClassify <>", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThan(String value) {
            addCriterion("RcvClassify >", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThanOrEqualTo(String value) {
            addCriterion("RcvClassify >=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThan(String value) {
            addCriterion("RcvClassify <", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThanOrEqualTo(String value) {
            addCriterion("RcvClassify <=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLike(String value) {
            addCriterion("RcvClassify like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotLike(String value) {
            addCriterion("RcvClassify not like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIn(List<String> values) {
            addCriterion("RcvClassify in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotIn(List<String> values) {
            addCriterion("RcvClassify not in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyBetween(String value1, String value2) {
            addCriterion("RcvClassify between", value1, value2, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotBetween(String value1, String value2) {
            addCriterion("RcvClassify not between", value1, value2, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNull() {
            addCriterion("StockCode is null");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNotNull() {
            addCriterion("StockCode is not null");
            return (Criteria) this;
        }

        public Criteria andStockcodeEqualTo(String value) {
            addCriterion("StockCode =", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotEqualTo(String value) {
            addCriterion("StockCode <>", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThan(String value) {
            addCriterion("StockCode >", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThanOrEqualTo(String value) {
            addCriterion("StockCode >=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThan(String value) {
            addCriterion("StockCode <", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThanOrEqualTo(String value) {
            addCriterion("StockCode <=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLike(String value) {
            addCriterion("StockCode like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotLike(String value) {
            addCriterion("StockCode not like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeIn(List<String> values) {
            addCriterion("StockCode in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotIn(List<String> values) {
            addCriterion("StockCode not in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeBetween(String value1, String value2) {
            addCriterion("StockCode between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotBetween(String value1, String value2) {
            addCriterion("StockCode not between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andCproductnoIsNull() {
            addCriterion("CProductNo is null");
            return (Criteria) this;
        }

        public Criteria andCproductnoIsNotNull() {
            addCriterion("CProductNo is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnoEqualTo(String value) {
            addCriterion("CProductNo =", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotEqualTo(String value) {
            addCriterion("CProductNo <>", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThan(String value) {
            addCriterion("CProductNo >", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThanOrEqualTo(String value) {
            addCriterion("CProductNo >=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThan(String value) {
            addCriterion("CProductNo <", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThanOrEqualTo(String value) {
            addCriterion("CProductNo <=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLike(String value) {
            addCriterion("CProductNo like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotLike(String value) {
            addCriterion("CProductNo not like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoIn(List<String> values) {
            addCriterion("CProductNo in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotIn(List<String> values) {
            addCriterion("CProductNo not in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoBetween(String value1, String value2) {
            addCriterion("CProductNo between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotBetween(String value1, String value2) {
            addCriterion("CProductNo not between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeIsNull() {
            addCriterion("ExpInvCode is null");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeIsNotNull() {
            addCriterion("ExpInvCode is not null");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeEqualTo(String value) {
            addCriterion("ExpInvCode =", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeNotEqualTo(String value) {
            addCriterion("ExpInvCode <>", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeGreaterThan(String value) {
            addCriterion("ExpInvCode >", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ExpInvCode >=", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeLessThan(String value) {
            addCriterion("ExpInvCode <", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeLessThanOrEqualTo(String value) {
            addCriterion("ExpInvCode <=", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeLike(String value) {
            addCriterion("ExpInvCode like", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeNotLike(String value) {
            addCriterion("ExpInvCode not like", value, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeIn(List<String> values) {
            addCriterion("ExpInvCode in", values, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeNotIn(List<String> values) {
            addCriterion("ExpInvCode not in", values, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeBetween(String value1, String value2) {
            addCriterion("ExpInvCode between", value1, value2, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andExpinvcodeNotBetween(String value1, String value2) {
            addCriterion("ExpInvCode not between", value1, value2, "expinvcode");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNull() {
            addCriterion("OrdTransType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNotNull() {
            addCriterion("OrdTransType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeEqualTo(String value) {
            addCriterion("OrdTransType =", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotEqualTo(String value) {
            addCriterion("OrdTransType <>", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThan(String value) {
            addCriterion("OrdTransType >", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdTransType >=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThan(String value) {
            addCriterion("OrdTransType <", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThanOrEqualTo(String value) {
            addCriterion("OrdTransType <=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLike(String value) {
            addCriterion("OrdTransType like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotLike(String value) {
            addCriterion("OrdTransType not like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIn(List<String> values) {
            addCriterion("OrdTransType in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotIn(List<String> values) {
            addCriterion("OrdTransType not in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeBetween(String value1, String value2) {
            addCriterion("OrdTransType between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotBetween(String value1, String value2) {
            addCriterion("OrdTransType not between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoIsNull() {
            addCriterion("SpecOfferNo is null");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoIsNotNull() {
            addCriterion("SpecOfferNo is not null");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoEqualTo(String value) {
            addCriterion("SpecOfferNo =", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoNotEqualTo(String value) {
            addCriterion("SpecOfferNo <>", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoGreaterThan(String value) {
            addCriterion("SpecOfferNo >", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoGreaterThanOrEqualTo(String value) {
            addCriterion("SpecOfferNo >=", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoLessThan(String value) {
            addCriterion("SpecOfferNo <", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoLessThanOrEqualTo(String value) {
            addCriterion("SpecOfferNo <=", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoLike(String value) {
            addCriterion("SpecOfferNo like", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoNotLike(String value) {
            addCriterion("SpecOfferNo not like", value, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoIn(List<String> values) {
            addCriterion("SpecOfferNo in", values, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoNotIn(List<String> values) {
            addCriterion("SpecOfferNo not in", values, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoBetween(String value1, String value2) {
            addCriterion("SpecOfferNo between", value1, value2, "specofferno");
            return (Criteria) this;
        }

        public Criteria andSpecoffernoNotBetween(String value1, String value2) {
            addCriterion("SpecOfferNo not between", value1, value2, "specofferno");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNull() {
            addCriterion("OptDate is null");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNotNull() {
            addCriterion("OptDate is not null");
            return (Criteria) this;
        }

        public Criteria andOptdateEqualTo(Date value) {
            addCriterion("OptDate =", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotEqualTo(Date value) {
            addCriterion("OptDate <>", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThan(Date value) {
            addCriterion("OptDate >", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OptDate >=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThan(Date value) {
            addCriterion("OptDate <", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThanOrEqualTo(Date value) {
            addCriterion("OptDate <=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateIn(List<Date> values) {
            addCriterion("OptDate in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotIn(List<Date> values) {
            addCriterion("OptDate not in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateBetween(Date value1, Date value2) {
            addCriterion("OptDate between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotBetween(Date value1, Date value2) {
            addCriterion("OptDate not between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNull() {
            addCriterion("ProdFlag is null");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNotNull() {
            addCriterion("ProdFlag is not null");
            return (Criteria) this;
        }

        public Criteria andProdflagEqualTo(String value) {
            addCriterion("ProdFlag =", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotEqualTo(String value) {
            addCriterion("ProdFlag <>", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThan(String value) {
            addCriterion("ProdFlag >", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThanOrEqualTo(String value) {
            addCriterion("ProdFlag >=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThan(String value) {
            addCriterion("ProdFlag <", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThanOrEqualTo(String value) {
            addCriterion("ProdFlag <=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLike(String value) {
            addCriterion("ProdFlag like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotLike(String value) {
            addCriterion("ProdFlag not like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagIn(List<String> values) {
            addCriterion("ProdFlag in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotIn(List<String> values) {
            addCriterion("ProdFlag not in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagBetween(String value1, String value2) {
            addCriterion("ProdFlag between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotBetween(String value1, String value2) {
            addCriterion("ProdFlag not between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNull() {
            addCriterion("SpecMark is null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNotNull() {
            addCriterion("SpecMark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkEqualTo(String value) {
            addCriterion("SpecMark =", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotEqualTo(String value) {
            addCriterion("SpecMark <>", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThan(String value) {
            addCriterion("SpecMark >", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThanOrEqualTo(String value) {
            addCriterion("SpecMark >=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThan(String value) {
            addCriterion("SpecMark <", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThanOrEqualTo(String value) {
            addCriterion("SpecMark <=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLike(String value) {
            addCriterion("SpecMark like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotLike(String value) {
            addCriterion("SpecMark not like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIn(List<String> values) {
            addCriterion("SpecMark in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotIn(List<String> values) {
            addCriterion("SpecMark not in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkBetween(String value1, String value2) {
            addCriterion("SpecMark between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotBetween(String value1, String value2) {
            addCriterion("SpecMark not between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNull() {
            addCriterion("OROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNotNull() {
            addCriterion("OROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrordernoEqualTo(String value) {
            addCriterion("OROrderNo =", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotEqualTo(String value) {
            addCriterion("OROrderNo <>", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThan(String value) {
            addCriterion("OROrderNo >", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThanOrEqualTo(String value) {
            addCriterion("OROrderNo >=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThan(String value) {
            addCriterion("OROrderNo <", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThanOrEqualTo(String value) {
            addCriterion("OROrderNo <=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLike(String value) {
            addCriterion("OROrderNo like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotLike(String value) {
            addCriterion("OROrderNo not like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoIn(List<String> values) {
            addCriterion("OROrderNo in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotIn(List<String> values) {
            addCriterion("OROrderNo not in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoBetween(String value1, String value2) {
            addCriterion("OROrderNo between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotBetween(String value1, String value2) {
            addCriterion("OROrderNo not between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andInvflagIsNull() {
            addCriterion("InvFlag is null");
            return (Criteria) this;
        }

        public Criteria andInvflagIsNotNull() {
            addCriterion("InvFlag is not null");
            return (Criteria) this;
        }

        public Criteria andInvflagEqualTo(String value) {
            addCriterion("InvFlag =", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotEqualTo(String value) {
            addCriterion("InvFlag <>", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagGreaterThan(String value) {
            addCriterion("InvFlag >", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagGreaterThanOrEqualTo(String value) {
            addCriterion("InvFlag >=", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLessThan(String value) {
            addCriterion("InvFlag <", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLessThanOrEqualTo(String value) {
            addCriterion("InvFlag <=", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLike(String value) {
            addCriterion("InvFlag like", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotLike(String value) {
            addCriterion("InvFlag not like", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagIn(List<String> values) {
            addCriterion("InvFlag in", values, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotIn(List<String> values) {
            addCriterion("InvFlag not in", values, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagBetween(String value1, String value2) {
            addCriterion("InvFlag between", value1, value2, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotBetween(String value1, String value2) {
            addCriterion("InvFlag not between", value1, value2, "invflag");
            return (Criteria) this;
        }

        public Criteria andNdlvdateIsNull() {
            addCriterion("NDlvDate is null");
            return (Criteria) this;
        }

        public Criteria andNdlvdateIsNotNull() {
            addCriterion("NDlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andNdlvdateEqualTo(Date value) {
            addCriterion("NDlvDate =", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateNotEqualTo(Date value) {
            addCriterion("NDlvDate <>", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateGreaterThan(Date value) {
            addCriterion("NDlvDate >", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("NDlvDate >=", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateLessThan(Date value) {
            addCriterion("NDlvDate <", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateLessThanOrEqualTo(Date value) {
            addCriterion("NDlvDate <=", value, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateIn(List<Date> values) {
            addCriterion("NDlvDate in", values, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateNotIn(List<Date> values) {
            addCriterion("NDlvDate not in", values, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateBetween(Date value1, Date value2) {
            addCriterion("NDlvDate between", value1, value2, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andNdlvdateNotBetween(Date value1, Date value2) {
            addCriterion("NDlvDate not between", value1, value2, "ndlvdate");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Double value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Double value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Double value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Double value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Double value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Double value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Double> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Double> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Double value1, Double value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Double value1, Double value2) {
            addCriterion("Discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("Opttime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("Opttime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("Opttime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("Opttime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("Opttime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Opttime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("Opttime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("Opttime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("Opttime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("Opttime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("Opttime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("Opttime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("Operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("Operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("Operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("Operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("Operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("Operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("Operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("Operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("Operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("Operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("Operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("Operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("Operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("Operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNull() {
            addCriterion("SHIKOMI is null");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNotNull() {
            addCriterion("SHIKOMI is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiEqualTo(String value) {
            addCriterion("SHIKOMI =", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotEqualTo(String value) {
            addCriterion("SHIKOMI <>", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThan(String value) {
            addCriterion("SHIKOMI >", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThanOrEqualTo(String value) {
            addCriterion("SHIKOMI >=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThan(String value) {
            addCriterion("SHIKOMI <", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThanOrEqualTo(String value) {
            addCriterion("SHIKOMI <=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLike(String value) {
            addCriterion("SHIKOMI like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotLike(String value) {
            addCriterion("SHIKOMI not like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiIn(List<String> values) {
            addCriterion("SHIKOMI in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotIn(List<String> values) {
            addCriterion("SHIKOMI not in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiBetween(String value1, String value2) {
            addCriterion("SHIKOMI between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotBetween(String value1, String value2) {
            addCriterion("SHIKOMI not between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andOptrecordIsNull() {
            addCriterion("OptRecord is null");
            return (Criteria) this;
        }

        public Criteria andOptrecordIsNotNull() {
            addCriterion("OptRecord is not null");
            return (Criteria) this;
        }

        public Criteria andOptrecordEqualTo(String value) {
            addCriterion("OptRecord =", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotEqualTo(String value) {
            addCriterion("OptRecord <>", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordGreaterThan(String value) {
            addCriterion("OptRecord >", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordGreaterThanOrEqualTo(String value) {
            addCriterion("OptRecord >=", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLessThan(String value) {
            addCriterion("OptRecord <", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLessThanOrEqualTo(String value) {
            addCriterion("OptRecord <=", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLike(String value) {
            addCriterion("OptRecord like", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotLike(String value) {
            addCriterion("OptRecord not like", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordIn(List<String> values) {
            addCriterion("OptRecord in", values, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotIn(List<String> values) {
            addCriterion("OptRecord not in", values, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordBetween(String value1, String value2) {
            addCriterion("OptRecord between", value1, value2, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotBetween(String value1, String value2) {
            addCriterion("OptRecord not between", value1, value2, "optrecord");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNull() {
            addCriterion("Price_EndUser is null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNotNull() {
            addCriterion("Price_EndUser is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser =", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <>", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThan(BigDecimal value) {
            addCriterion("Price_EndUser >", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser >=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThan(BigDecimal value) {
            addCriterion("Price_EndUser <", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser not in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser not between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("SmcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("SmcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("SmcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("SmcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("SmcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("SmcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("SmcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("SmcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("SmcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("SmcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("SmcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("SmcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("SmcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("SmcCode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjIsNull() {
            addCriterion("DlvDate_BJ is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjIsNotNull() {
            addCriterion("DlvDate_BJ is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjEqualTo(Date value) {
            addCriterion("DlvDate_BJ =", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjNotEqualTo(Date value) {
            addCriterion("DlvDate_BJ <>", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjGreaterThan(Date value) {
            addCriterion("DlvDate_BJ >", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate_BJ >=", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjLessThan(Date value) {
            addCriterion("DlvDate_BJ <", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate_BJ <=", value, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjIn(List<Date> values) {
            addCriterion("DlvDate_BJ in", values, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjNotIn(List<Date> values) {
            addCriterion("DlvDate_BJ not in", values, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjBetween(Date value1, Date value2) {
            addCriterion("DlvDate_BJ between", value1, value2, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andDlvdateBjNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate_BJ not between", value1, value2, "dlvdateBj");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNull() {
            addCriterion("CProductName is null");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNotNull() {
            addCriterion("CProductName is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnameEqualTo(String value) {
            addCriterion("CProductName =", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotEqualTo(String value) {
            addCriterion("CProductName <>", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThan(String value) {
            addCriterion("CProductName >", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThanOrEqualTo(String value) {
            addCriterion("CProductName >=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThan(String value) {
            addCriterion("CProductName <", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThanOrEqualTo(String value) {
            addCriterion("CProductName <=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLike(String value) {
            addCriterion("CProductName like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotLike(String value) {
            addCriterion("CProductName not like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameIn(List<String> values) {
            addCriterion("CProductName in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotIn(List<String> values) {
            addCriterion("CProductName not in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameBetween(String value1, String value2) {
            addCriterion("CProductName between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotBetween(String value1, String value2) {
            addCriterion("CProductName not between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNull() {
            addCriterion("PreSaleOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNotNull() {
            addCriterion("PreSaleOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoEqualTo(String value) {
            addCriterion("PreSaleOrderNo =", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotEqualTo(String value) {
            addCriterion("PreSaleOrderNo <>", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThan(String value) {
            addCriterion("PreSaleOrderNo >", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo >=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThan(String value) {
            addCriterion("PreSaleOrderNo <", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo <=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLike(String value) {
            addCriterion("PreSaleOrderNo like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotLike(String value) {
            addCriterion("PreSaleOrderNo not like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIn(List<String> values) {
            addCriterion("PreSaleOrderNo in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotIn(List<String> values) {
            addCriterion("PreSaleOrderNo not in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo not between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNull() {
            addCriterion("Opponent is null");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNotNull() {
            addCriterion("Opponent is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentEqualTo(String value) {
            addCriterion("Opponent =", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotEqualTo(String value) {
            addCriterion("Opponent <>", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThan(String value) {
            addCriterion("Opponent >", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThanOrEqualTo(String value) {
            addCriterion("Opponent >=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThan(String value) {
            addCriterion("Opponent <", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThanOrEqualTo(String value) {
            addCriterion("Opponent <=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLike(String value) {
            addCriterion("Opponent like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotLike(String value) {
            addCriterion("Opponent not like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentIn(List<String> values) {
            addCriterion("Opponent in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotIn(List<String> values) {
            addCriterion("Opponent not in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentBetween(String value1, String value2) {
            addCriterion("Opponent between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotBetween(String value1, String value2) {
            addCriterion("Opponent not between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoIsNull() {
            addCriterion("StockCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoIsNotNull() {
            addCriterion("StockCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoEqualTo(String value) {
            addCriterion("StockCustomerNo =", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoNotEqualTo(String value) {
            addCriterion("StockCustomerNo <>", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoGreaterThan(String value) {
            addCriterion("StockCustomerNo >", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("StockCustomerNo >=", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoLessThan(String value) {
            addCriterion("StockCustomerNo <", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoLessThanOrEqualTo(String value) {
            addCriterion("StockCustomerNo <=", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoLike(String value) {
            addCriterion("StockCustomerNo like", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoNotLike(String value) {
            addCriterion("StockCustomerNo not like", value, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoIn(List<String> values) {
            addCriterion("StockCustomerNo in", values, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoNotIn(List<String> values) {
            addCriterion("StockCustomerNo not in", values, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoBetween(String value1, String value2) {
            addCriterion("StockCustomerNo between", value1, value2, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andStockcustomernoNotBetween(String value1, String value2) {
            addCriterion("StockCustomerNo not between", value1, value2, "stockcustomerno");
            return (Criteria) this;
        }

        public Criteria andFullordernoIsNull() {
            addCriterion("FullOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andFullordernoIsNotNull() {
            addCriterion("FullOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andFullordernoEqualTo(String value) {
            addCriterion("FullOrderNo =", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotEqualTo(String value) {
            addCriterion("FullOrderNo <>", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoGreaterThan(String value) {
            addCriterion("FullOrderNo >", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoGreaterThanOrEqualTo(String value) {
            addCriterion("FullOrderNo >=", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLessThan(String value) {
            addCriterion("FullOrderNo <", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLessThanOrEqualTo(String value) {
            addCriterion("FullOrderNo <=", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLike(String value) {
            addCriterion("FullOrderNo like", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotLike(String value) {
            addCriterion("FullOrderNo not like", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoIn(List<String> values) {
            addCriterion("FullOrderNo in", values, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotIn(List<String> values) {
            addCriterion("FullOrderNo not in", values, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoBetween(String value1, String value2) {
            addCriterion("FullOrderNo between", value1, value2, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotBetween(String value1, String value2) {
            addCriterion("FullOrderNo not between", value1, value2, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIsNull() {
            addCriterion("InvoiceGroupKey is null");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIsNotNull() {
            addCriterion("InvoiceGroupKey is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyEqualTo(String value) {
            addCriterion("InvoiceGroupKey =", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotEqualTo(String value) {
            addCriterion("InvoiceGroupKey <>", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyGreaterThan(String value) {
            addCriterion("InvoiceGroupKey >", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceGroupKey >=", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLessThan(String value) {
            addCriterion("InvoiceGroupKey <", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLessThanOrEqualTo(String value) {
            addCriterion("InvoiceGroupKey <=", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLike(String value) {
            addCriterion("InvoiceGroupKey like", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotLike(String value) {
            addCriterion("InvoiceGroupKey not like", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIn(List<String> values) {
            addCriterion("InvoiceGroupKey in", values, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotIn(List<String> values) {
            addCriterion("InvoiceGroupKey not in", values, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyBetween(String value1, String value2) {
            addCriterion("InvoiceGroupKey between", value1, value2, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotBetween(String value1, String value2) {
            addCriterion("InvoiceGroupKey not between", value1, value2, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoIsNull() {
            addCriterion("ext_orderno is null");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoIsNotNull() {
            addCriterion("ext_orderno is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoEqualTo(String value) {
            addCriterion("ext_orderno =", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotEqualTo(String value) {
            addCriterion("ext_orderno <>", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoGreaterThan(String value) {
            addCriterion("ext_orderno >", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("ext_orderno >=", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLessThan(String value) {
            addCriterion("ext_orderno <", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLessThanOrEqualTo(String value) {
            addCriterion("ext_orderno <=", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLike(String value) {
            addCriterion("ext_orderno like", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotLike(String value) {
            addCriterion("ext_orderno not like", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoIn(List<String> values) {
            addCriterion("ext_orderno in", values, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotIn(List<String> values) {
            addCriterion("ext_orderno not in", values, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoBetween(String value1, String value2) {
            addCriterion("ext_orderno between", value1, value2, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotBetween(String value1, String value2) {
            addCriterion("ext_orderno not between", value1, value2, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNull() {
            addCriterion("ext_order_item is null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNotNull() {
            addCriterion("ext_order_item is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemEqualTo(String value) {
            addCriterion("ext_order_item =", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotEqualTo(String value) {
            addCriterion("ext_order_item <>", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThan(String value) {
            addCriterion("ext_order_item >", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("ext_order_item >=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThan(String value) {
            addCriterion("ext_order_item <", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThanOrEqualTo(String value) {
            addCriterion("ext_order_item <=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLike(String value) {
            addCriterion("ext_order_item like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotLike(String value) {
            addCriterion("ext_order_item not like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIn(List<String> values) {
            addCriterion("ext_order_item in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotIn(List<String> values) {
            addCriterion("ext_order_item not in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemBetween(String value1, String value2) {
            addCriterion("ext_order_item between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotBetween(String value1, String value2) {
            addCriterion("ext_order_item not between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysIsNull() {
            addCriterion("OrderSourceSys is null");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysIsNotNull() {
            addCriterion("OrderSourceSys is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysEqualTo(String value) {
            addCriterion("OrderSourceSys =", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotEqualTo(String value) {
            addCriterion("OrderSourceSys <>", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysGreaterThan(String value) {
            addCriterion("OrderSourceSys >", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysGreaterThanOrEqualTo(String value) {
            addCriterion("OrderSourceSys >=", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLessThan(String value) {
            addCriterion("OrderSourceSys <", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLessThanOrEqualTo(String value) {
            addCriterion("OrderSourceSys <=", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLike(String value) {
            addCriterion("OrderSourceSys like", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotLike(String value) {
            addCriterion("OrderSourceSys not like", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysIn(List<String> values) {
            addCriterion("OrderSourceSys in", values, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotIn(List<String> values) {
            addCriterion("OrderSourceSys not in", values, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysBetween(String value1, String value2) {
            addCriterion("OrderSourceSys between", value1, value2, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotBetween(String value1, String value2) {
            addCriterion("OrderSourceSys not between", value1, value2, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("Inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("Inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("Inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("Inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("Inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("Inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("Inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("Inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("Inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("Inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("Inserttime not between", value1, value2, "inserttime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
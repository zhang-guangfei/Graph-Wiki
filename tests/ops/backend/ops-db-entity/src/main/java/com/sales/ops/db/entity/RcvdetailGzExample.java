package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RcvdetailGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvdetailGzExample() {
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

        public Criteria andDeptDlvdateIsNull() {
            addCriterion("Dept_Dlvdate is null");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateIsNotNull() {
            addCriterion("Dept_Dlvdate is not null");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateEqualTo(Date value) {
            addCriterion("Dept_Dlvdate =", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateNotEqualTo(Date value) {
            addCriterion("Dept_Dlvdate <>", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateGreaterThan(Date value) {
            addCriterion("Dept_Dlvdate >", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("Dept_Dlvdate >=", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateLessThan(Date value) {
            addCriterion("Dept_Dlvdate <", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("Dept_Dlvdate <=", value, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateIn(List<Date> values) {
            addCriterion("Dept_Dlvdate in", values, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateNotIn(List<Date> values) {
            addCriterion("Dept_Dlvdate not in", values, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateBetween(Date value1, Date value2) {
            addCriterion("Dept_Dlvdate between", value1, value2, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("Dept_Dlvdate not between", value1, value2, "deptDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateIsNull() {
            addCriterion("Cust_Dlvdate is null");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateIsNotNull() {
            addCriterion("Cust_Dlvdate is not null");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateEqualTo(Date value) {
            addCriterion("Cust_Dlvdate =", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateNotEqualTo(Date value) {
            addCriterion("Cust_Dlvdate <>", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateGreaterThan(Date value) {
            addCriterion("Cust_Dlvdate >", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("Cust_Dlvdate >=", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateLessThan(Date value) {
            addCriterion("Cust_Dlvdate <", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("Cust_Dlvdate <=", value, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateIn(List<Date> values) {
            addCriterion("Cust_Dlvdate in", values, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateNotIn(List<Date> values) {
            addCriterion("Cust_Dlvdate not in", values, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateBetween(Date value1, Date value2) {
            addCriterion("Cust_Dlvdate between", value1, value2, "custDlvdate");
            return (Criteria) this;
        }

        public Criteria andCustDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("Cust_Dlvdate not between", value1, value2, "custDlvdate");
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

        public Criteria andDlvaddressIsNull() {
            addCriterion("DlvAddress is null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIsNotNull() {
            addCriterion("DlvAddress is not null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressEqualTo(String value) {
            addCriterion("DlvAddress =", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotEqualTo(String value) {
            addCriterion("DlvAddress <>", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThan(String value) {
            addCriterion("DlvAddress >", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThanOrEqualTo(String value) {
            addCriterion("DlvAddress >=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThan(String value) {
            addCriterion("DlvAddress <", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThanOrEqualTo(String value) {
            addCriterion("DlvAddress <=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLike(String value) {
            addCriterion("DlvAddress like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotLike(String value) {
            addCriterion("DlvAddress not like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIn(List<String> values) {
            addCriterion("DlvAddress in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotIn(List<String> values) {
            addCriterion("DlvAddress not in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressBetween(String value1, String value2) {
            addCriterion("DlvAddress between", value1, value2, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotBetween(String value1, String value2) {
            addCriterion("DlvAddress not between", value1, value2, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceIsNull() {
            addCriterion("AgentSalesPrice is null");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceIsNotNull() {
            addCriterion("AgentSalesPrice is not null");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceEqualTo(BigDecimal value) {
            addCriterion("AgentSalesPrice =", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceNotEqualTo(BigDecimal value) {
            addCriterion("AgentSalesPrice <>", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceGreaterThan(BigDecimal value) {
            addCriterion("AgentSalesPrice >", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AgentSalesPrice >=", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceLessThan(BigDecimal value) {
            addCriterion("AgentSalesPrice <", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AgentSalesPrice <=", value, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceIn(List<BigDecimal> values) {
            addCriterion("AgentSalesPrice in", values, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceNotIn(List<BigDecimal> values) {
            addCriterion("AgentSalesPrice not in", values, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AgentSalesPrice between", value1, value2, "agentsalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentsalespriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AgentSalesPrice not between", value1, value2, "agentsalesprice");
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

        public Criteria andShipdateIsNull() {
            addCriterion("ShipDate is null");
            return (Criteria) this;
        }

        public Criteria andShipdateIsNotNull() {
            addCriterion("ShipDate is not null");
            return (Criteria) this;
        }

        public Criteria andShipdateEqualTo(Date value) {
            addCriterion("ShipDate =", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotEqualTo(Date value) {
            addCriterion("ShipDate <>", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateGreaterThan(Date value) {
            addCriterion("ShipDate >", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateGreaterThanOrEqualTo(Date value) {
            addCriterion("ShipDate >=", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateLessThan(Date value) {
            addCriterion("ShipDate <", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateLessThanOrEqualTo(Date value) {
            addCriterion("ShipDate <=", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateIn(List<Date> values) {
            addCriterion("ShipDate in", values, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotIn(List<Date> values) {
            addCriterion("ShipDate not in", values, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateBetween(Date value1, Date value2) {
            addCriterion("ShipDate between", value1, value2, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotBetween(Date value1, Date value2) {
            addCriterion("ShipDate not between", value1, value2, "shipdate");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIsNull() {
            addCriterion("ExpDlvType is null");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIsNotNull() {
            addCriterion("ExpDlvType is not null");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeEqualTo(Integer value) {
            addCriterion("ExpDlvType =", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotEqualTo(Integer value) {
            addCriterion("ExpDlvType <>", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeGreaterThan(Integer value) {
            addCriterion("ExpDlvType >", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ExpDlvType >=", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeLessThan(Integer value) {
            addCriterion("ExpDlvType <", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeLessThanOrEqualTo(Integer value) {
            addCriterion("ExpDlvType <=", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIn(List<Integer> values) {
            addCriterion("ExpDlvType in", values, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotIn(List<Integer> values) {
            addCriterion("ExpDlvType not in", values, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeBetween(Integer value1, Integer value2) {
            addCriterion("ExpDlvType between", value1, value2, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ExpDlvType not between", value1, value2, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidIsNull() {
            addCriterion("DlvAddressId is null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidIsNotNull() {
            addCriterion("DlvAddressId is not null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidEqualTo(String value) {
            addCriterion("DlvAddressId =", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidNotEqualTo(String value) {
            addCriterion("DlvAddressId <>", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidGreaterThan(String value) {
            addCriterion("DlvAddressId >", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidGreaterThanOrEqualTo(String value) {
            addCriterion("DlvAddressId >=", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidLessThan(String value) {
            addCriterion("DlvAddressId <", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidLessThanOrEqualTo(String value) {
            addCriterion("DlvAddressId <=", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidLike(String value) {
            addCriterion("DlvAddressId like", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidNotLike(String value) {
            addCriterion("DlvAddressId not like", value, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidIn(List<String> values) {
            addCriterion("DlvAddressId in", values, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidNotIn(List<String> values) {
            addCriterion("DlvAddressId not in", values, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidBetween(String value1, String value2) {
            addCriterion("DlvAddressId between", value1, value2, "dlvaddressid");
            return (Criteria) this;
        }

        public Criteria andDlvaddressidNotBetween(String value1, String value2) {
            addCriterion("DlvAddressId not between", value1, value2, "dlvaddressid");
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

        public Criteria andTaxrateIsNull() {
            addCriterion("TaxRate is null");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNotNull() {
            addCriterion("TaxRate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxrateEqualTo(BigDecimal value) {
            addCriterion("TaxRate =", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("TaxRate <>", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThan(BigDecimal value) {
            addCriterion("TaxRate >", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxRate >=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThan(BigDecimal value) {
            addCriterion("TaxRate <", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxRate <=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateIn(List<BigDecimal> values) {
            addCriterion("TaxRate in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("TaxRate not in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxRate between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxRate not between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andNtaxamountIsNull() {
            addCriterion("NTaxAmount is null");
            return (Criteria) this;
        }

        public Criteria andNtaxamountIsNotNull() {
            addCriterion("NTaxAmount is not null");
            return (Criteria) this;
        }

        public Criteria andNtaxamountEqualTo(BigDecimal value) {
            addCriterion("NTaxAmount =", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountNotEqualTo(BigDecimal value) {
            addCriterion("NTaxAmount <>", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountGreaterThan(BigDecimal value) {
            addCriterion("NTaxAmount >", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NTaxAmount >=", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountLessThan(BigDecimal value) {
            addCriterion("NTaxAmount <", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NTaxAmount <=", value, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountIn(List<BigDecimal> values) {
            addCriterion("NTaxAmount in", values, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountNotIn(List<BigDecimal> values) {
            addCriterion("NTaxAmount not in", values, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NTaxAmount between", value1, value2, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andNtaxamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NTaxAmount not between", value1, value2, "ntaxamount");
            return (Criteria) this;
        }

        public Criteria andExptimeIsNull() {
            addCriterion("ExpTime is null");
            return (Criteria) this;
        }

        public Criteria andExptimeIsNotNull() {
            addCriterion("ExpTime is not null");
            return (Criteria) this;
        }

        public Criteria andExptimeEqualTo(Date value) {
            addCriterion("ExpTime =", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotEqualTo(Date value) {
            addCriterion("ExpTime <>", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeGreaterThan(Date value) {
            addCriterion("ExpTime >", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ExpTime >=", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeLessThan(Date value) {
            addCriterion("ExpTime <", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeLessThanOrEqualTo(Date value) {
            addCriterion("ExpTime <=", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeIn(List<Date> values) {
            addCriterion("ExpTime in", values, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotIn(List<Date> values) {
            addCriterion("ExpTime not in", values, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeBetween(Date value1, Date value2) {
            addCriterion("ExpTime between", value1, value2, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotBetween(Date value1, Date value2) {
            addCriterion("ExpTime not between", value1, value2, "exptime");
            return (Criteria) this;
        }

        public Criteria andReadytimeIsNull() {
            addCriterion("ReadyTime is null");
            return (Criteria) this;
        }

        public Criteria andReadytimeIsNotNull() {
            addCriterion("ReadyTime is not null");
            return (Criteria) this;
        }

        public Criteria andReadytimeEqualTo(Date value) {
            addCriterion("ReadyTime =", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeNotEqualTo(Date value) {
            addCriterion("ReadyTime <>", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeGreaterThan(Date value) {
            addCriterion("ReadyTime >", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ReadyTime >=", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeLessThan(Date value) {
            addCriterion("ReadyTime <", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeLessThanOrEqualTo(Date value) {
            addCriterion("ReadyTime <=", value, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeIn(List<Date> values) {
            addCriterion("ReadyTime in", values, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeNotIn(List<Date> values) {
            addCriterion("ReadyTime not in", values, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeBetween(Date value1, Date value2) {
            addCriterion("ReadyTime between", value1, value2, "readytime");
            return (Criteria) this;
        }

        public Criteria andReadytimeNotBetween(Date value1, Date value2) {
            addCriterion("ReadyTime not between", value1, value2, "readytime");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIsNull() {
            addCriterion("OwnerCode is null");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIsNotNull() {
            addCriterion("OwnerCode is not null");
            return (Criteria) this;
        }

        public Criteria andOwnercodeEqualTo(String value) {
            addCriterion("OwnerCode =", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotEqualTo(String value) {
            addCriterion("OwnerCode <>", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeGreaterThan(String value) {
            addCriterion("OwnerCode >", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeGreaterThanOrEqualTo(String value) {
            addCriterion("OwnerCode >=", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLessThan(String value) {
            addCriterion("OwnerCode <", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLessThanOrEqualTo(String value) {
            addCriterion("OwnerCode <=", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLike(String value) {
            addCriterion("OwnerCode like", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotLike(String value) {
            addCriterion("OwnerCode not like", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIn(List<String> values) {
            addCriterion("OwnerCode in", values, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotIn(List<String> values) {
            addCriterion("OwnerCode not in", values, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeBetween(String value1, String value2) {
            addCriterion("OwnerCode between", value1, value2, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotBetween(String value1, String value2) {
            addCriterion("OwnerCode not between", value1, value2, "ownercode");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNull() {
            addCriterion("DetailMark is null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNotNull() {
            addCriterion("DetailMark is not null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkEqualTo(String value) {
            addCriterion("DetailMark =", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotEqualTo(String value) {
            addCriterion("DetailMark <>", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThan(String value) {
            addCriterion("DetailMark >", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThanOrEqualTo(String value) {
            addCriterion("DetailMark >=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThan(String value) {
            addCriterion("DetailMark <", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThanOrEqualTo(String value) {
            addCriterion("DetailMark <=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLike(String value) {
            addCriterion("DetailMark like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotLike(String value) {
            addCriterion("DetailMark not like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIn(List<String> values) {
            addCriterion("DetailMark in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotIn(List<String> values) {
            addCriterion("DetailMark not in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkBetween(String value1, String value2) {
            addCriterion("DetailMark between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotBetween(String value1, String value2) {
            addCriterion("DetailMark not between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andSigntimeIsNull() {
            addCriterion("SignTime is null");
            return (Criteria) this;
        }

        public Criteria andSigntimeIsNotNull() {
            addCriterion("SignTime is not null");
            return (Criteria) this;
        }

        public Criteria andSigntimeEqualTo(Date value) {
            addCriterion("SignTime =", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeNotEqualTo(Date value) {
            addCriterion("SignTime <>", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeGreaterThan(Date value) {
            addCriterion("SignTime >", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SignTime >=", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeLessThan(Date value) {
            addCriterion("SignTime <", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeLessThanOrEqualTo(Date value) {
            addCriterion("SignTime <=", value, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeIn(List<Date> values) {
            addCriterion("SignTime in", values, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeNotIn(List<Date> values) {
            addCriterion("SignTime not in", values, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeBetween(Date value1, Date value2) {
            addCriterion("SignTime between", value1, value2, "signtime");
            return (Criteria) this;
        }

        public Criteria andSigntimeNotBetween(Date value1, Date value2) {
            addCriterion("SignTime not between", value1, value2, "signtime");
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
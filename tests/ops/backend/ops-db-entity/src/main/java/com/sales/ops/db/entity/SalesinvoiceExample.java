package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SalesinvoiceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SalesinvoiceExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNull() {
            addCriterion("TradeCompanyId is null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNotNull() {
            addCriterion("TradeCompanyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidEqualTo(String value) {
            addCriterion("TradeCompanyId =", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotEqualTo(String value) {
            addCriterion("TradeCompanyId <>", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThan(String value) {
            addCriterion("TradeCompanyId >", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId >=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThan(String value) {
            addCriterion("TradeCompanyId <", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId <=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLike(String value) {
            addCriterion("TradeCompanyId like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotLike(String value) {
            addCriterion("TradeCompanyId not like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIn(List<String> values) {
            addCriterion("TradeCompanyId in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotIn(List<String> values) {
            addCriterion("TradeCompanyId not in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidBetween(String value1, String value2) {
            addCriterion("TradeCompanyId between", value1, value2, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotBetween(String value1, String value2) {
            addCriterion("TradeCompanyId not between", value1, value2, "tradecompanyid");
            return (Criteria) this;
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

        public Criteria andCustomernoIsNull() {
            addCriterion("CustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("CustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("CustomerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("CustomerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("CustomerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("CustomerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("CustomerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("CustomerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("CustomerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("CustomerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("CustomerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("CustomerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("CustomerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNull() {
            addCriterion("UserNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("UserNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("UserNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("UserNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("UserNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("UserNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("UserNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("UserNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("UserNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("UserNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("UserNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("UserNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("UserNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("UserNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andDlventireIsNull() {
            addCriterion("DlvEntire is null");
            return (Criteria) this;
        }

        public Criteria andDlventireIsNotNull() {
            addCriterion("DlvEntire is not null");
            return (Criteria) this;
        }

        public Criteria andDlventireEqualTo(String value) {
            addCriterion("DlvEntire =", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotEqualTo(String value) {
            addCriterion("DlvEntire <>", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThan(String value) {
            addCriterion("DlvEntire >", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThanOrEqualTo(String value) {
            addCriterion("DlvEntire >=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThan(String value) {
            addCriterion("DlvEntire <", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThanOrEqualTo(String value) {
            addCriterion("DlvEntire <=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLike(String value) {
            addCriterion("DlvEntire like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotLike(String value) {
            addCriterion("DlvEntire not like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireIn(List<String> values) {
            addCriterion("DlvEntire in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotIn(List<String> values) {
            addCriterion("DlvEntire not in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireBetween(String value1, String value2) {
            addCriterion("DlvEntire between", value1, value2, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotBetween(String value1, String value2) {
            addCriterion("DlvEntire not between", value1, value2, "dlventire");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("Amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("Amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("Amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("Amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("Amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("Amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("Amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("Amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTaxamountIsNull() {
            addCriterion("TaxAmount is null");
            return (Criteria) this;
        }

        public Criteria andTaxamountIsNotNull() {
            addCriterion("TaxAmount is not null");
            return (Criteria) this;
        }

        public Criteria andTaxamountEqualTo(BigDecimal value) {
            addCriterion("TaxAmount =", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotEqualTo(BigDecimal value) {
            addCriterion("TaxAmount <>", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountGreaterThan(BigDecimal value) {
            addCriterion("TaxAmount >", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxAmount >=", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountLessThan(BigDecimal value) {
            addCriterion("TaxAmount <", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxAmount <=", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountIn(List<BigDecimal> values) {
            addCriterion("TaxAmount in", values, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotIn(List<BigDecimal> values) {
            addCriterion("TaxAmount not in", values, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxAmount between", value1, value2, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxAmount not between", value1, value2, "taxamount");
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

        public Criteria andOptdateIsNull() {
            addCriterion("OptDate is null");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNotNull() {
            addCriterion("OptDate is not null");
            return (Criteria) this;
        }

        public Criteria andOptdateEqualTo(Date value) {
            addCriterionForJDBCDate("OptDate =", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("OptDate <>", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThan(Date value) {
            addCriterionForJDBCDate("OptDate >", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OptDate >=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThan(Date value) {
            addCriterionForJDBCDate("OptDate <", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OptDate <=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateIn(List<Date> values) {
            addCriterionForJDBCDate("OptDate in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("OptDate not in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OptDate between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OptDate not between", value1, value2, "optdate");
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

        public Criteria andClassflagIsNull() {
            addCriterion("ClassFlag is null");
            return (Criteria) this;
        }

        public Criteria andClassflagIsNotNull() {
            addCriterion("ClassFlag is not null");
            return (Criteria) this;
        }

        public Criteria andClassflagEqualTo(String value) {
            addCriterion("ClassFlag =", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagNotEqualTo(String value) {
            addCriterion("ClassFlag <>", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagGreaterThan(String value) {
            addCriterion("ClassFlag >", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagGreaterThanOrEqualTo(String value) {
            addCriterion("ClassFlag >=", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagLessThan(String value) {
            addCriterion("ClassFlag <", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagLessThanOrEqualTo(String value) {
            addCriterion("ClassFlag <=", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagLike(String value) {
            addCriterion("ClassFlag like", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagNotLike(String value) {
            addCriterion("ClassFlag not like", value, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagIn(List<String> values) {
            addCriterion("ClassFlag in", values, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagNotIn(List<String> values) {
            addCriterion("ClassFlag not in", values, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagBetween(String value1, String value2) {
            addCriterion("ClassFlag between", value1, value2, "classflag");
            return (Criteria) this;
        }

        public Criteria andClassflagNotBetween(String value1, String value2) {
            addCriterion("ClassFlag not between", value1, value2, "classflag");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("InvoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("InvoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("InvoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("InvoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("InvoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("InvoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("InvoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("InvoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("InvoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("InvoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("InvoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvdateIsNull() {
            addCriterion("InvDate is null");
            return (Criteria) this;
        }

        public Criteria andInvdateIsNotNull() {
            addCriterion("InvDate is not null");
            return (Criteria) this;
        }

        public Criteria andInvdateEqualTo(Date value) {
            addCriterionForJDBCDate("InvDate =", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("InvDate <>", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateGreaterThan(Date value) {
            addCriterionForJDBCDate("InvDate >", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("InvDate >=", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateLessThan(Date value) {
            addCriterionForJDBCDate("InvDate <", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("InvDate <=", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateIn(List<Date> values) {
            addCriterionForJDBCDate("InvDate in", values, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("InvDate not in", values, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("InvDate between", value1, value2, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("InvDate not between", value1, value2, "invdate");
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

        public Criteria andStocknoIsNull() {
            addCriterion("StockNo is null");
            return (Criteria) this;
        }

        public Criteria andStocknoIsNotNull() {
            addCriterion("StockNo is not null");
            return (Criteria) this;
        }

        public Criteria andStocknoEqualTo(String value) {
            addCriterion("StockNo =", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoNotEqualTo(String value) {
            addCriterion("StockNo <>", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoGreaterThan(String value) {
            addCriterion("StockNo >", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoGreaterThanOrEqualTo(String value) {
            addCriterion("StockNo >=", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoLessThan(String value) {
            addCriterion("StockNo <", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoLessThanOrEqualTo(String value) {
            addCriterion("StockNo <=", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoLike(String value) {
            addCriterion("StockNo like", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoNotLike(String value) {
            addCriterion("StockNo not like", value, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoIn(List<String> values) {
            addCriterion("StockNo in", values, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoNotIn(List<String> values) {
            addCriterion("StockNo not in", values, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoBetween(String value1, String value2) {
            addCriterion("StockNo between", value1, value2, "stockno");
            return (Criteria) this;
        }

        public Criteria andStocknoNotBetween(String value1, String value2) {
            addCriterion("StockNo not between", value1, value2, "stockno");
            return (Criteria) this;
        }

        public Criteria andInvflag1IsNull() {
            addCriterion("InvFlag1 is null");
            return (Criteria) this;
        }

        public Criteria andInvflag1IsNotNull() {
            addCriterion("InvFlag1 is not null");
            return (Criteria) this;
        }

        public Criteria andInvflag1EqualTo(String value) {
            addCriterion("InvFlag1 =", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1NotEqualTo(String value) {
            addCriterion("InvFlag1 <>", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1GreaterThan(String value) {
            addCriterion("InvFlag1 >", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1GreaterThanOrEqualTo(String value) {
            addCriterion("InvFlag1 >=", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1LessThan(String value) {
            addCriterion("InvFlag1 <", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1LessThanOrEqualTo(String value) {
            addCriterion("InvFlag1 <=", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1Like(String value) {
            addCriterion("InvFlag1 like", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1NotLike(String value) {
            addCriterion("InvFlag1 not like", value, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1In(List<String> values) {
            addCriterion("InvFlag1 in", values, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1NotIn(List<String> values) {
            addCriterion("InvFlag1 not in", values, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1Between(String value1, String value2) {
            addCriterion("InvFlag1 between", value1, value2, "invflag1");
            return (Criteria) this;
        }

        public Criteria andInvflag1NotBetween(String value1, String value2) {
            addCriterion("InvFlag1 not between", value1, value2, "invflag1");
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

        public Criteria andBillnoIsNull() {
            addCriterion("BillNo is null");
            return (Criteria) this;
        }

        public Criteria andBillnoIsNotNull() {
            addCriterion("BillNo is not null");
            return (Criteria) this;
        }

        public Criteria andBillnoEqualTo(String value) {
            addCriterion("BillNo =", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoNotEqualTo(String value) {
            addCriterion("BillNo <>", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoGreaterThan(String value) {
            addCriterion("BillNo >", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoGreaterThanOrEqualTo(String value) {
            addCriterion("BillNo >=", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoLessThan(String value) {
            addCriterion("BillNo <", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoLessThanOrEqualTo(String value) {
            addCriterion("BillNo <=", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoLike(String value) {
            addCriterion("BillNo like", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoNotLike(String value) {
            addCriterion("BillNo not like", value, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoIn(List<String> values) {
            addCriterion("BillNo in", values, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoNotIn(List<String> values) {
            addCriterion("BillNo not in", values, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoBetween(String value1, String value2) {
            addCriterion("BillNo between", value1, value2, "billno");
            return (Criteria) this;
        }

        public Criteria andBillnoNotBetween(String value1, String value2) {
            addCriterion("BillNo not between", value1, value2, "billno");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("Username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("Username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("Username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("Username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("Username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("Username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("Username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("Username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("Username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("Username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("Username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("Username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("Username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("Username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("OptTime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("OptTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("OptTime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("OptTime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("OptTime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OptTime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("OptTime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("OptTime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("OptTime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("OptTime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("OptTime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("OptTime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpIsNull() {
            addCriterion("Receive_Emp is null");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpIsNotNull() {
            addCriterion("Receive_Emp is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpEqualTo(String value) {
            addCriterion("Receive_Emp =", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpNotEqualTo(String value) {
            addCriterion("Receive_Emp <>", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpGreaterThan(String value) {
            addCriterion("Receive_Emp >", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpGreaterThanOrEqualTo(String value) {
            addCriterion("Receive_Emp >=", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpLessThan(String value) {
            addCriterion("Receive_Emp <", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpLessThanOrEqualTo(String value) {
            addCriterion("Receive_Emp <=", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpLike(String value) {
            addCriterion("Receive_Emp like", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpNotLike(String value) {
            addCriterion("Receive_Emp not like", value, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpIn(List<String> values) {
            addCriterion("Receive_Emp in", values, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpNotIn(List<String> values) {
            addCriterion("Receive_Emp not in", values, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpBetween(String value1, String value2) {
            addCriterion("Receive_Emp between", value1, value2, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveEmpNotBetween(String value1, String value2) {
            addCriterion("Receive_Emp not between", value1, value2, "receiveEmp");
            return (Criteria) this;
        }

        public Criteria andReceiveCustIsNull() {
            addCriterion("Receive_Cust is null");
            return (Criteria) this;
        }

        public Criteria andReceiveCustIsNotNull() {
            addCriterion("Receive_Cust is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveCustEqualTo(String value) {
            addCriterion("Receive_Cust =", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustNotEqualTo(String value) {
            addCriterion("Receive_Cust <>", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustGreaterThan(String value) {
            addCriterion("Receive_Cust >", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustGreaterThanOrEqualTo(String value) {
            addCriterion("Receive_Cust >=", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustLessThan(String value) {
            addCriterion("Receive_Cust <", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustLessThanOrEqualTo(String value) {
            addCriterion("Receive_Cust <=", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustLike(String value) {
            addCriterion("Receive_Cust like", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustNotLike(String value) {
            addCriterion("Receive_Cust not like", value, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustIn(List<String> values) {
            addCriterion("Receive_Cust in", values, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustNotIn(List<String> values) {
            addCriterion("Receive_Cust not in", values, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustBetween(String value1, String value2) {
            addCriterion("Receive_Cust between", value1, value2, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveCustNotBetween(String value1, String value2) {
            addCriterion("Receive_Cust not between", value1, value2, "receiveCust");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNull() {
            addCriterion("Receive_date is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNotNull() {
            addCriterion("Receive_date is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateEqualTo(Date value) {
            addCriterionForJDBCDate("Receive_date =", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("Receive_date <>", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThan(Date value) {
            addCriterionForJDBCDate("Receive_date >", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Receive_date >=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThan(Date value) {
            addCriterionForJDBCDate("Receive_date <", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Receive_date <=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIn(List<Date> values) {
            addCriterionForJDBCDate("Receive_date in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("Receive_date not in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Receive_date between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Receive_date not between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkIsNull() {
            addCriterion("Receive_Remark is null");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkIsNotNull() {
            addCriterion("Receive_Remark is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkEqualTo(String value) {
            addCriterion("Receive_Remark =", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkNotEqualTo(String value) {
            addCriterion("Receive_Remark <>", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkGreaterThan(String value) {
            addCriterion("Receive_Remark >", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Receive_Remark >=", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkLessThan(String value) {
            addCriterion("Receive_Remark <", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkLessThanOrEqualTo(String value) {
            addCriterion("Receive_Remark <=", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkLike(String value) {
            addCriterion("Receive_Remark like", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkNotLike(String value) {
            addCriterion("Receive_Remark not like", value, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkIn(List<String> values) {
            addCriterion("Receive_Remark in", values, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkNotIn(List<String> values) {
            addCriterion("Receive_Remark not in", values, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkBetween(String value1, String value2) {
            addCriterion("Receive_Remark between", value1, value2, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveRemarkNotBetween(String value1, String value2) {
            addCriterion("Receive_Remark not between", value1, value2, "receiveRemark");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateIsNull() {
            addCriterion("Receive_OptState is null");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateIsNotNull() {
            addCriterion("Receive_OptState is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateEqualTo(String value) {
            addCriterion("Receive_OptState =", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateNotEqualTo(String value) {
            addCriterion("Receive_OptState <>", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateGreaterThan(String value) {
            addCriterion("Receive_OptState >", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateGreaterThanOrEqualTo(String value) {
            addCriterion("Receive_OptState >=", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateLessThan(String value) {
            addCriterion("Receive_OptState <", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateLessThanOrEqualTo(String value) {
            addCriterion("Receive_OptState <=", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateLike(String value) {
            addCriterion("Receive_OptState like", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateNotLike(String value) {
            addCriterion("Receive_OptState not like", value, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateIn(List<String> values) {
            addCriterion("Receive_OptState in", values, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateNotIn(List<String> values) {
            addCriterion("Receive_OptState not in", values, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateBetween(String value1, String value2) {
            addCriterion("Receive_OptState between", value1, value2, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andReceiveOptstateNotBetween(String value1, String value2) {
            addCriterion("Receive_OptState not between", value1, value2, "receiveOptstate");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNull() {
            addCriterion("PurchaseNo is null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNotNull() {
            addCriterion("PurchaseNo is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoEqualTo(String value) {
            addCriterion("PurchaseNo =", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotEqualTo(String value) {
            addCriterion("PurchaseNo <>", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThan(String value) {
            addCriterion("PurchaseNo >", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseNo >=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThan(String value) {
            addCriterion("PurchaseNo <", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseNo <=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLike(String value) {
            addCriterion("PurchaseNo like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotLike(String value) {
            addCriterion("PurchaseNo not like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIn(List<String> values) {
            addCriterion("PurchaseNo in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotIn(List<String> values) {
            addCriterion("PurchaseNo not in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoBetween(String value1, String value2) {
            addCriterion("PurchaseNo between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotBetween(String value1, String value2) {
            addCriterion("PurchaseNo not between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagIsNull() {
            addCriterion("AgentPrice_Flag is null");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagIsNotNull() {
            addCriterion("AgentPrice_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagEqualTo(String value) {
            addCriterion("AgentPrice_Flag =", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagNotEqualTo(String value) {
            addCriterion("AgentPrice_Flag <>", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagGreaterThan(String value) {
            addCriterion("AgentPrice_Flag >", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("AgentPrice_Flag >=", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagLessThan(String value) {
            addCriterion("AgentPrice_Flag <", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagLessThanOrEqualTo(String value) {
            addCriterion("AgentPrice_Flag <=", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagLike(String value) {
            addCriterion("AgentPrice_Flag like", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagNotLike(String value) {
            addCriterion("AgentPrice_Flag not like", value, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagIn(List<String> values) {
            addCriterion("AgentPrice_Flag in", values, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagNotIn(List<String> values) {
            addCriterion("AgentPrice_Flag not in", values, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagBetween(String value1, String value2) {
            addCriterion("AgentPrice_Flag between", value1, value2, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andAgentpriceFlagNotBetween(String value1, String value2) {
            addCriterion("AgentPrice_Flag not between", value1, value2, "agentpriceFlag");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("DeptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("DeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("DeptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("DeptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("DeptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("DeptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("DeptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("DeptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("DeptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("DeptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("DeptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("DeptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("DeptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("DeptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDiscountamtIsNull() {
            addCriterion("DiscountAmt is null");
            return (Criteria) this;
        }

        public Criteria andDiscountamtIsNotNull() {
            addCriterion("DiscountAmt is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountamtEqualTo(BigDecimal value) {
            addCriterion("DiscountAmt =", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtNotEqualTo(BigDecimal value) {
            addCriterion("DiscountAmt <>", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtGreaterThan(BigDecimal value) {
            addCriterion("DiscountAmt >", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountAmt >=", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtLessThan(BigDecimal value) {
            addCriterion("DiscountAmt <", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountAmt <=", value, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtIn(List<BigDecimal> values) {
            addCriterion("DiscountAmt in", values, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtNotIn(List<BigDecimal> values) {
            addCriterion("DiscountAmt not in", values, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountAmt between", value1, value2, "discountamt");
            return (Criteria) this;
        }

        public Criteria andDiscountamtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountAmt not between", value1, value2, "discountamt");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("OrdType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("OrdType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("OrdType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("OrdType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("OrdType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("OrdType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("OrdType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("OrdType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("OrdType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("OrdType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("OrdType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("OrdType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("OrdType not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeIsNull() {
            addCriterion("invoicecode is null");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeIsNotNull() {
            addCriterion("invoicecode is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeEqualTo(String value) {
            addCriterion("invoicecode =", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeNotEqualTo(String value) {
            addCriterion("invoicecode <>", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeGreaterThan(String value) {
            addCriterion("invoicecode >", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeGreaterThanOrEqualTo(String value) {
            addCriterion("invoicecode >=", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeLessThan(String value) {
            addCriterion("invoicecode <", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeLessThanOrEqualTo(String value) {
            addCriterion("invoicecode <=", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeLike(String value) {
            addCriterion("invoicecode like", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeNotLike(String value) {
            addCriterion("invoicecode not like", value, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeIn(List<String> values) {
            addCriterion("invoicecode in", values, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeNotIn(List<String> values) {
            addCriterion("invoicecode not in", values, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeBetween(String value1, String value2) {
            addCriterion("invoicecode between", value1, value2, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvoicecodeNotBetween(String value1, String value2) {
            addCriterion("invoicecode not between", value1, value2, "invoicecode");
            return (Criteria) this;
        }

        public Criteria andInvtypeIsNull() {
            addCriterion("invtype is null");
            return (Criteria) this;
        }

        public Criteria andInvtypeIsNotNull() {
            addCriterion("invtype is not null");
            return (Criteria) this;
        }

        public Criteria andInvtypeEqualTo(String value) {
            addCriterion("invtype =", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeNotEqualTo(String value) {
            addCriterion("invtype <>", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeGreaterThan(String value) {
            addCriterion("invtype >", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("invtype >=", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeLessThan(String value) {
            addCriterion("invtype <", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeLessThanOrEqualTo(String value) {
            addCriterion("invtype <=", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeLike(String value) {
            addCriterion("invtype like", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeNotLike(String value) {
            addCriterion("invtype not like", value, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeIn(List<String> values) {
            addCriterion("invtype in", values, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeNotIn(List<String> values) {
            addCriterion("invtype not in", values, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeBetween(String value1, String value2) {
            addCriterion("invtype between", value1, value2, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvtypeNotBetween(String value1, String value2) {
            addCriterion("invtype not between", value1, value2, "invtype");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpIsNull() {
            addCriterion("InvoiceNo_Jp is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpIsNotNull() {
            addCriterion("InvoiceNo_Jp is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpEqualTo(String value) {
            addCriterion("InvoiceNo_Jp =", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpNotEqualTo(String value) {
            addCriterion("InvoiceNo_Jp <>", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpGreaterThan(String value) {
            addCriterion("InvoiceNo_Jp >", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo_Jp >=", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpLessThan(String value) {
            addCriterion("InvoiceNo_Jp <", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo_Jp <=", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpLike(String value) {
            addCriterion("InvoiceNo_Jp like", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpNotLike(String value) {
            addCriterion("InvoiceNo_Jp not like", value, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpIn(List<String> values) {
            addCriterion("InvoiceNo_Jp in", values, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpNotIn(List<String> values) {
            addCriterion("InvoiceNo_Jp not in", values, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpBetween(String value1, String value2) {
            addCriterion("InvoiceNo_Jp between", value1, value2, "invoicenoJp");
            return (Criteria) this;
        }

        public Criteria andInvoicenoJpNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo_Jp not between", value1, value2, "invoicenoJp");
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

        public Criteria andNtaxdiscountamtIsNull() {
            addCriterion("NTaxDiscountAmt is null");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtIsNotNull() {
            addCriterion("NTaxDiscountAmt is not null");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtEqualTo(BigDecimal value) {
            addCriterion("NTaxDiscountAmt =", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtNotEqualTo(BigDecimal value) {
            addCriterion("NTaxDiscountAmt <>", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtGreaterThan(BigDecimal value) {
            addCriterion("NTaxDiscountAmt >", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NTaxDiscountAmt >=", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtLessThan(BigDecimal value) {
            addCriterion("NTaxDiscountAmt <", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NTaxDiscountAmt <=", value, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtIn(List<BigDecimal> values) {
            addCriterion("NTaxDiscountAmt in", values, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtNotIn(List<BigDecimal> values) {
            addCriterion("NTaxDiscountAmt not in", values, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NTaxDiscountAmt between", value1, value2, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andNtaxdiscountamtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NTaxDiscountAmt not between", value1, value2, "ntaxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtIsNull() {
            addCriterion("TaxDiscountAmt is null");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtIsNotNull() {
            addCriterion("TaxDiscountAmt is not null");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtEqualTo(BigDecimal value) {
            addCriterion("TaxDiscountAmt =", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtNotEqualTo(BigDecimal value) {
            addCriterion("TaxDiscountAmt <>", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtGreaterThan(BigDecimal value) {
            addCriterion("TaxDiscountAmt >", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxDiscountAmt >=", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtLessThan(BigDecimal value) {
            addCriterion("TaxDiscountAmt <", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxDiscountAmt <=", value, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtIn(List<BigDecimal> values) {
            addCriterion("TaxDiscountAmt in", values, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtNotIn(List<BigDecimal> values) {
            addCriterion("TaxDiscountAmt not in", values, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxDiscountAmt between", value1, value2, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andTaxdiscountamtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxDiscountAmt not between", value1, value2, "taxdiscountamt");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("InsertTime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("InsertTime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("InsertTime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("InsertTime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("InsertTime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("InsertTime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("InsertTime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("InsertTime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("InsertTime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("InsertTime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("InsertTime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("InsertTime not between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andCanceldateIsNull() {
            addCriterion("CancelDate is null");
            return (Criteria) this;
        }

        public Criteria andCanceldateIsNotNull() {
            addCriterion("CancelDate is not null");
            return (Criteria) this;
        }

        public Criteria andCanceldateEqualTo(Date value) {
            addCriterionForJDBCDate("CancelDate =", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateNotEqualTo(Date value) {
            addCriterionForJDBCDate("CancelDate <>", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateGreaterThan(Date value) {
            addCriterionForJDBCDate("CancelDate >", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CancelDate >=", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateLessThan(Date value) {
            addCriterionForJDBCDate("CancelDate <", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CancelDate <=", value, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateIn(List<Date> values) {
            addCriterionForJDBCDate("CancelDate in", values, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateNotIn(List<Date> values) {
            addCriterionForJDBCDate("CancelDate not in", values, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CancelDate between", value1, value2, "canceldate");
            return (Criteria) this;
        }

        public Criteria andCanceldateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CancelDate not between", value1, value2, "canceldate");
            return (Criteria) this;
        }

        public Criteria andExtractedIsNull() {
            addCriterion("Extracted is null");
            return (Criteria) this;
        }

        public Criteria andExtractedIsNotNull() {
            addCriterion("Extracted is not null");
            return (Criteria) this;
        }

        public Criteria andExtractedEqualTo(String value) {
            addCriterion("Extracted =", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedNotEqualTo(String value) {
            addCriterion("Extracted <>", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedGreaterThan(String value) {
            addCriterion("Extracted >", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedGreaterThanOrEqualTo(String value) {
            addCriterion("Extracted >=", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedLessThan(String value) {
            addCriterion("Extracted <", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedLessThanOrEqualTo(String value) {
            addCriterion("Extracted <=", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedLike(String value) {
            addCriterion("Extracted like", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedNotLike(String value) {
            addCriterion("Extracted not like", value, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedIn(List<String> values) {
            addCriterion("Extracted in", values, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedNotIn(List<String> values) {
            addCriterion("Extracted not in", values, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedBetween(String value1, String value2) {
            addCriterion("Extracted between", value1, value2, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtractedNotBetween(String value1, String value2) {
            addCriterion("Extracted not between", value1, value2, "extracted");
            return (Criteria) this;
        }

        public Criteria andExtracttimeIsNull() {
            addCriterion("ExtractTime is null");
            return (Criteria) this;
        }

        public Criteria andExtracttimeIsNotNull() {
            addCriterion("ExtractTime is not null");
            return (Criteria) this;
        }

        public Criteria andExtracttimeEqualTo(Date value) {
            addCriterion("ExtractTime =", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeNotEqualTo(Date value) {
            addCriterion("ExtractTime <>", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeGreaterThan(Date value) {
            addCriterion("ExtractTime >", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ExtractTime >=", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeLessThan(Date value) {
            addCriterion("ExtractTime <", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeLessThanOrEqualTo(Date value) {
            addCriterion("ExtractTime <=", value, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeIn(List<Date> values) {
            addCriterion("ExtractTime in", values, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeNotIn(List<Date> values) {
            addCriterion("ExtractTime not in", values, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeBetween(Date value1, Date value2) {
            addCriterion("ExtractTime between", value1, value2, "extracttime");
            return (Criteria) this;
        }

        public Criteria andExtracttimeNotBetween(Date value1, Date value2) {
            addCriterion("ExtractTime not between", value1, value2, "extracttime");
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

        public Criteria andExpdateIsNull() {
            addCriterion("expdate is null");
            return (Criteria) this;
        }

        public Criteria andExpdateIsNotNull() {
            addCriterion("expdate is not null");
            return (Criteria) this;
        }

        public Criteria andExpdateEqualTo(Date value) {
            addCriterion("expdate =", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotEqualTo(Date value) {
            addCriterion("expdate <>", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThan(Date value) {
            addCriterion("expdate >", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("expdate >=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThan(Date value) {
            addCriterion("expdate <", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThanOrEqualTo(Date value) {
            addCriterion("expdate <=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateIn(List<Date> values) {
            addCriterion("expdate in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotIn(List<Date> values) {
            addCriterion("expdate not in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateBetween(Date value1, Date value2) {
            addCriterion("expdate between", value1, value2, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotBetween(Date value1, Date value2) {
            addCriterion("expdate not between", value1, value2, "expdate");
            return (Criteria) this;
        }

        public Criteria andAckdateIsNull() {
            addCriterion("ackdate is null");
            return (Criteria) this;
        }

        public Criteria andAckdateIsNotNull() {
            addCriterion("ackdate is not null");
            return (Criteria) this;
        }

        public Criteria andAckdateEqualTo(Date value) {
            addCriterion("ackdate =", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateNotEqualTo(Date value) {
            addCriterion("ackdate <>", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateGreaterThan(Date value) {
            addCriterion("ackdate >", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateGreaterThanOrEqualTo(Date value) {
            addCriterion("ackdate >=", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateLessThan(Date value) {
            addCriterion("ackdate <", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateLessThanOrEqualTo(Date value) {
            addCriterion("ackdate <=", value, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateIn(List<Date> values) {
            addCriterion("ackdate in", values, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateNotIn(List<Date> values) {
            addCriterion("ackdate not in", values, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateBetween(Date value1, Date value2) {
            addCriterion("ackdate between", value1, value2, "ackdate");
            return (Criteria) this;
        }

        public Criteria andAckdateNotBetween(Date value1, Date value2) {
            addCriterion("ackdate not between", value1, value2, "ackdate");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderno is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderno is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderno =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderno <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderno >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderno >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderno <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderno <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderno like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderno not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderno in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderno not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderno between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderno not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("itemno is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("itemno is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Integer value) {
            addCriterion("itemno =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Integer value) {
            addCriterion("itemno <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Integer value) {
            addCriterion("itemno >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemno >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Integer value) {
            addCriterion("itemno <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Integer value) {
            addCriterion("itemno <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Integer> values) {
            addCriterion("itemno in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Integer> values) {
            addCriterion("itemno not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Integer value1, Integer value2) {
            addCriterion("itemno between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("itemno not between", value1, value2, "itemno");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersalesGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrdersalesGzExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRecnoIsNull() {
            addCriterion("RecNo is null");
            return (Criteria) this;
        }

        public Criteria andRecnoIsNotNull() {
            addCriterion("RecNo is not null");
            return (Criteria) this;
        }

        public Criteria andRecnoEqualTo(String value) {
            addCriterion("RecNo =", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotEqualTo(String value) {
            addCriterion("RecNo <>", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThan(String value) {
            addCriterion("RecNo >", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThanOrEqualTo(String value) {
            addCriterion("RecNo >=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThan(String value) {
            addCriterion("RecNo <", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThanOrEqualTo(String value) {
            addCriterion("RecNo <=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLike(String value) {
            addCriterion("RecNo like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotLike(String value) {
            addCriterion("RecNo not like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoIn(List<String> values) {
            addCriterion("RecNo in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotIn(List<String> values) {
            addCriterion("RecNo not in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoBetween(String value1, String value2) {
            addCriterion("RecNo between", value1, value2, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotBetween(String value1, String value2) {
            addCriterion("RecNo not between", value1, value2, "recno");
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

        public Criteria andAccountIsNull() {
            addCriterion("Account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("Account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("Account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("Account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("Account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("Account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("Account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("Account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account not between", value1, value2, "account");
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

        public Criteria andPrjcodeIsNull() {
            addCriterion("PrjCode is null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIsNotNull() {
            addCriterion("PrjCode is not null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeEqualTo(String value) {
            addCriterion("PrjCode =", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotEqualTo(String value) {
            addCriterion("PrjCode <>", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThan(String value) {
            addCriterion("PrjCode >", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PrjCode >=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThan(String value) {
            addCriterion("PrjCode <", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThanOrEqualTo(String value) {
            addCriterion("PrjCode <=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLike(String value) {
            addCriterion("PrjCode like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotLike(String value) {
            addCriterion("PrjCode not like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIn(List<String> values) {
            addCriterion("PrjCode in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotIn(List<String> values) {
            addCriterion("PrjCode not in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeBetween(String value1, String value2) {
            addCriterion("PrjCode between", value1, value2, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotBetween(String value1, String value2) {
            addCriterion("PrjCode not between", value1, value2, "prjcode");
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

        public Criteria andSpcpriceIsNull() {
            addCriterion("SpcPrice is null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIsNotNull() {
            addCriterion("SpcPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceEqualTo(String value) {
            addCriterion("SpcPrice =", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotEqualTo(String value) {
            addCriterion("SpcPrice <>", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThan(String value) {
            addCriterion("SpcPrice >", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThanOrEqualTo(String value) {
            addCriterion("SpcPrice >=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThan(String value) {
            addCriterion("SpcPrice <", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThanOrEqualTo(String value) {
            addCriterion("SpcPrice <=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLike(String value) {
            addCriterion("SpcPrice like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotLike(String value) {
            addCriterion("SpcPrice not like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIn(List<String> values) {
            addCriterion("SpcPrice in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotIn(List<String> values) {
            addCriterion("SpcPrice not in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceBetween(String value1, String value2) {
            addCriterion("SpcPrice between", value1, value2, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotBetween(String value1, String value2) {
            addCriterion("SpcPrice not between", value1, value2, "spcprice");
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

        public Criteria andTransfeeIsNull() {
            addCriterion("TransFee is null");
            return (Criteria) this;
        }

        public Criteria andTransfeeIsNotNull() {
            addCriterion("TransFee is not null");
            return (Criteria) this;
        }

        public Criteria andTransfeeEqualTo(String value) {
            addCriterion("TransFee =", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotEqualTo(String value) {
            addCriterion("TransFee <>", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThan(String value) {
            addCriterion("TransFee >", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThanOrEqualTo(String value) {
            addCriterion("TransFee >=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThan(String value) {
            addCriterion("TransFee <", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThanOrEqualTo(String value) {
            addCriterion("TransFee <=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLike(String value) {
            addCriterion("TransFee like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotLike(String value) {
            addCriterion("TransFee not like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeIn(List<String> values) {
            addCriterion("TransFee in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotIn(List<String> values) {
            addCriterion("TransFee not in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeBetween(String value1, String value2) {
            addCriterion("TransFee between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotBetween(String value1, String value2) {
            addCriterion("TransFee not between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTranschannelIsNull() {
            addCriterion("TransChannel is null");
            return (Criteria) this;
        }

        public Criteria andTranschannelIsNotNull() {
            addCriterion("TransChannel is not null");
            return (Criteria) this;
        }

        public Criteria andTranschannelEqualTo(String value) {
            addCriterion("TransChannel =", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotEqualTo(String value) {
            addCriterion("TransChannel <>", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThan(String value) {
            addCriterion("TransChannel >", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThanOrEqualTo(String value) {
            addCriterion("TransChannel >=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThan(String value) {
            addCriterion("TransChannel <", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThanOrEqualTo(String value) {
            addCriterion("TransChannel <=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLike(String value) {
            addCriterion("TransChannel like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotLike(String value) {
            addCriterion("TransChannel not like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelIn(List<String> values) {
            addCriterion("TransChannel in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotIn(List<String> values) {
            addCriterion("TransChannel not in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelBetween(String value1, String value2) {
            addCriterion("TransChannel between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotBetween(String value1, String value2) {
            addCriterion("TransChannel not between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNull() {
            addCriterion("DlvSite is null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNotNull() {
            addCriterion("DlvSite is not null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteEqualTo(String value) {
            addCriterion("DlvSite =", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotEqualTo(String value) {
            addCriterion("DlvSite <>", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThan(String value) {
            addCriterion("DlvSite >", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThanOrEqualTo(String value) {
            addCriterion("DlvSite >=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThan(String value) {
            addCriterion("DlvSite <", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThanOrEqualTo(String value) {
            addCriterion("DlvSite <=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLike(String value) {
            addCriterion("DlvSite like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotLike(String value) {
            addCriterion("DlvSite not like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIn(List<String> values) {
            addCriterion("DlvSite in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotIn(List<String> values) {
            addCriterion("DlvSite not in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteBetween(String value1, String value2) {
            addCriterion("DlvSite between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotBetween(String value1, String value2) {
            addCriterion("DlvSite not between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvtype1IsNull() {
            addCriterion("DlvType1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype1IsNotNull() {
            addCriterion("DlvType1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype1EqualTo(String value) {
            addCriterion("DlvType1 =", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotEqualTo(String value) {
            addCriterion("DlvType1 <>", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1GreaterThan(String value) {
            addCriterion("DlvType1 >", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType1 >=", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1LessThan(String value) {
            addCriterion("DlvType1 <", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1LessThanOrEqualTo(String value) {
            addCriterion("DlvType1 <=", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1Like(String value) {
            addCriterion("DlvType1 like", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotLike(String value) {
            addCriterion("DlvType1 not like", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1In(List<String> values) {
            addCriterion("DlvType1 in", values, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotIn(List<String> values) {
            addCriterion("DlvType1 not in", values, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1Between(String value1, String value2) {
            addCriterion("DlvType1 between", value1, value2, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotBetween(String value1, String value2) {
            addCriterion("DlvType1 not between", value1, value2, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype2IsNull() {
            addCriterion("DlvType2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype2IsNotNull() {
            addCriterion("DlvType2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype2EqualTo(String value) {
            addCriterion("DlvType2 =", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotEqualTo(String value) {
            addCriterion("DlvType2 <>", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2GreaterThan(String value) {
            addCriterion("DlvType2 >", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType2 >=", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2LessThan(String value) {
            addCriterion("DlvType2 <", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2LessThanOrEqualTo(String value) {
            addCriterion("DlvType2 <=", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2Like(String value) {
            addCriterion("DlvType2 like", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotLike(String value) {
            addCriterion("DlvType2 not like", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2In(List<String> values) {
            addCriterion("DlvType2 in", values, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotIn(List<String> values) {
            addCriterion("DlvType2 not in", values, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2Between(String value1, String value2) {
            addCriterion("DlvType2 between", value1, value2, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotBetween(String value1, String value2) {
            addCriterion("DlvType2 not between", value1, value2, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype3IsNull() {
            addCriterion("DlvType3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype3IsNotNull() {
            addCriterion("DlvType3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype3EqualTo(String value) {
            addCriterion("DlvType3 =", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotEqualTo(String value) {
            addCriterion("DlvType3 <>", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3GreaterThan(String value) {
            addCriterion("DlvType3 >", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType3 >=", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3LessThan(String value) {
            addCriterion("DlvType3 <", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3LessThanOrEqualTo(String value) {
            addCriterion("DlvType3 <=", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3Like(String value) {
            addCriterion("DlvType3 like", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotLike(String value) {
            addCriterion("DlvType3 not like", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3In(List<String> values) {
            addCriterion("DlvType3 in", values, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotIn(List<String> values) {
            addCriterion("DlvType3 not in", values, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3Between(String value1, String value2) {
            addCriterion("DlvType3 between", value1, value2, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotBetween(String value1, String value2) {
            addCriterion("DlvType3 not between", value1, value2, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype4IsNull() {
            addCriterion("DlvType4 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype4IsNotNull() {
            addCriterion("DlvType4 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype4EqualTo(String value) {
            addCriterion("DlvType4 =", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4NotEqualTo(String value) {
            addCriterion("DlvType4 <>", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4GreaterThan(String value) {
            addCriterion("DlvType4 >", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType4 >=", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4LessThan(String value) {
            addCriterion("DlvType4 <", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4LessThanOrEqualTo(String value) {
            addCriterion("DlvType4 <=", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4Like(String value) {
            addCriterion("DlvType4 like", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4NotLike(String value) {
            addCriterion("DlvType4 not like", value, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4In(List<String> values) {
            addCriterion("DlvType4 in", values, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4NotIn(List<String> values) {
            addCriterion("DlvType4 not in", values, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4Between(String value1, String value2) {
            addCriterion("DlvType4 between", value1, value2, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andDlvtype4NotBetween(String value1, String value2) {
            addCriterion("DlvType4 not between", value1, value2, "dlvtype4");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNull() {
            addCriterion("ContractNo is null");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNotNull() {
            addCriterion("ContractNo is not null");
            return (Criteria) this;
        }

        public Criteria andContractnoEqualTo(String value) {
            addCriterion("ContractNo =", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotEqualTo(String value) {
            addCriterion("ContractNo <>", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThan(String value) {
            addCriterion("ContractNo >", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThanOrEqualTo(String value) {
            addCriterion("ContractNo >=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThan(String value) {
            addCriterion("ContractNo <", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThanOrEqualTo(String value) {
            addCriterion("ContractNo <=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLike(String value) {
            addCriterion("ContractNo like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotLike(String value) {
            addCriterion("ContractNo not like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoIn(List<String> values) {
            addCriterion("ContractNo in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotIn(List<String> values) {
            addCriterion("ContractNo not in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoBetween(String value1, String value2) {
            addCriterion("ContractNo between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotBetween(String value1, String value2) {
            addCriterion("ContractNo not between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIsNull() {
            addCriterion("QuotationNo is null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIsNotNull() {
            addCriterion("QuotationNo is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoEqualTo(String value) {
            addCriterion("QuotationNo =", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotEqualTo(String value) {
            addCriterion("QuotationNo <>", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThan(String value) {
            addCriterion("QuotationNo >", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThanOrEqualTo(String value) {
            addCriterion("QuotationNo >=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThan(String value) {
            addCriterion("QuotationNo <", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThanOrEqualTo(String value) {
            addCriterion("QuotationNo <=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLike(String value) {
            addCriterion("QuotationNo like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotLike(String value) {
            addCriterion("QuotationNo not like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIn(List<String> values) {
            addCriterion("QuotationNo in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotIn(List<String> values) {
            addCriterion("QuotationNo not in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoBetween(String value1, String value2) {
            addCriterion("QuotationNo between", value1, value2, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotBetween(String value1, String value2) {
            addCriterion("QuotationNo not between", value1, value2, "quotationno");
            return (Criteria) this;
        }

        public Criteria andCquerynoIsNull() {
            addCriterion("CQueryNo is null");
            return (Criteria) this;
        }

        public Criteria andCquerynoIsNotNull() {
            addCriterion("CQueryNo is not null");
            return (Criteria) this;
        }

        public Criteria andCquerynoEqualTo(String value) {
            addCriterion("CQueryNo =", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotEqualTo(String value) {
            addCriterion("CQueryNo <>", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoGreaterThan(String value) {
            addCriterion("CQueryNo >", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoGreaterThanOrEqualTo(String value) {
            addCriterion("CQueryNo >=", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLessThan(String value) {
            addCriterion("CQueryNo <", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLessThanOrEqualTo(String value) {
            addCriterion("CQueryNo <=", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLike(String value) {
            addCriterion("CQueryNo like", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotLike(String value) {
            addCriterion("CQueryNo not like", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoIn(List<String> values) {
            addCriterion("CQueryNo in", values, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotIn(List<String> values) {
            addCriterion("CQueryNo not in", values, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoBetween(String value1, String value2) {
            addCriterion("CQueryNo between", value1, value2, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotBetween(String value1, String value2) {
            addCriterion("CQueryNo not between", value1, value2, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNull() {
            addCriterion("COrderNo is null");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNotNull() {
            addCriterion("COrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andCordernoEqualTo(String value) {
            addCriterion("COrderNo =", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotEqualTo(String value) {
            addCriterion("COrderNo <>", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThan(String value) {
            addCriterion("COrderNo >", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThanOrEqualTo(String value) {
            addCriterion("COrderNo >=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThan(String value) {
            addCriterion("COrderNo <", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThanOrEqualTo(String value) {
            addCriterion("COrderNo <=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLike(String value) {
            addCriterion("COrderNo like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotLike(String value) {
            addCriterion("COrderNo not like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoIn(List<String> values) {
            addCriterion("COrderNo in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotIn(List<String> values) {
            addCriterion("COrderNo not in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoBetween(String value1, String value2) {
            addCriterion("COrderNo between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotBetween(String value1, String value2) {
            addCriterion("COrderNo not between", value1, value2, "corderno");
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

        public Criteria andOplogIsNull() {
            addCriterion("OpLog is null");
            return (Criteria) this;
        }

        public Criteria andOplogIsNotNull() {
            addCriterion("OpLog is not null");
            return (Criteria) this;
        }

        public Criteria andOplogEqualTo(String value) {
            addCriterion("OpLog =", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotEqualTo(String value) {
            addCriterion("OpLog <>", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogGreaterThan(String value) {
            addCriterion("OpLog >", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogGreaterThanOrEqualTo(String value) {
            addCriterion("OpLog >=", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLessThan(String value) {
            addCriterion("OpLog <", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLessThanOrEqualTo(String value) {
            addCriterion("OpLog <=", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLike(String value) {
            addCriterion("OpLog like", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotLike(String value) {
            addCriterion("OpLog not like", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogIn(List<String> values) {
            addCriterion("OpLog in", values, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotIn(List<String> values) {
            addCriterion("OpLog not in", values, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogBetween(String value1, String value2) {
            addCriterion("OpLog between", value1, value2, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotBetween(String value1, String value2) {
            addCriterion("OpLog not between", value1, value2, "oplog");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNull() {
            addCriterion("WorkDay is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNotNull() {
            addCriterion("WorkDay is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayEqualTo(Date value) {
            addCriterion("WorkDay =", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotEqualTo(Date value) {
            addCriterion("WorkDay <>", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThan(Date value) {
            addCriterion("WorkDay >", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThanOrEqualTo(Date value) {
            addCriterion("WorkDay >=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThan(Date value) {
            addCriterion("WorkDay <", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThanOrEqualTo(Date value) {
            addCriterion("WorkDay <=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayIn(List<Date> values) {
            addCriterion("WorkDay in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotIn(List<Date> values) {
            addCriterion("WorkDay not in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayBetween(Date value1, Date value2) {
            addCriterion("WorkDay between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotBetween(Date value1, Date value2) {
            addCriterion("WorkDay not between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNull() {
            addCriterion("Sendout is null");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNotNull() {
            addCriterion("Sendout is not null");
            return (Criteria) this;
        }

        public Criteria andSendoutEqualTo(String value) {
            addCriterion("Sendout =", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotEqualTo(String value) {
            addCriterion("Sendout <>", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThan(String value) {
            addCriterion("Sendout >", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThanOrEqualTo(String value) {
            addCriterion("Sendout >=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThan(String value) {
            addCriterion("Sendout <", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThanOrEqualTo(String value) {
            addCriterion("Sendout <=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLike(String value) {
            addCriterion("Sendout like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotLike(String value) {
            addCriterion("Sendout not like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutIn(List<String> values) {
            addCriterion("Sendout in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotIn(List<String> values) {
            addCriterion("Sendout not in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutBetween(String value1, String value2) {
            addCriterion("Sendout between", value1, value2, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotBetween(String value1, String value2) {
            addCriterion("Sendout not between", value1, value2, "sendout");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("Status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("Status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNull() {
            addCriterion("SendDay is null");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNotNull() {
            addCriterion("SendDay is not null");
            return (Criteria) this;
        }

        public Criteria andSenddayEqualTo(Date value) {
            addCriterion("SendDay =", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotEqualTo(Date value) {
            addCriterion("SendDay <>", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThan(Date value) {
            addCriterion("SendDay >", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThanOrEqualTo(Date value) {
            addCriterion("SendDay >=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThan(Date value) {
            addCriterion("SendDay <", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThanOrEqualTo(Date value) {
            addCriterion("SendDay <=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayIn(List<Date> values) {
            addCriterion("SendDay in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotIn(List<Date> values) {
            addCriterion("SendDay not in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayBetween(Date value1, Date value2) {
            addCriterion("SendDay between", value1, value2, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotBetween(Date value1, Date value2) {
            addCriterion("SendDay not between", value1, value2, "sendday");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNull() {
            addCriterion("NoPrice is null");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNotNull() {
            addCriterion("NoPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNopriceEqualTo(String value) {
            addCriterion("NoPrice =", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotEqualTo(String value) {
            addCriterion("NoPrice <>", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThan(String value) {
            addCriterion("NoPrice >", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThanOrEqualTo(String value) {
            addCriterion("NoPrice >=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThan(String value) {
            addCriterion("NoPrice <", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThanOrEqualTo(String value) {
            addCriterion("NoPrice <=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLike(String value) {
            addCriterion("NoPrice like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotLike(String value) {
            addCriterion("NoPrice not like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceIn(List<String> values) {
            addCriterion("NoPrice in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotIn(List<String> values) {
            addCriterion("NoPrice not in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceBetween(String value1, String value2) {
            addCriterion("NoPrice between", value1, value2, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotBetween(String value1, String value2) {
            addCriterion("NoPrice not between", value1, value2, "noprice");
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

        public Criteria andLocationnoIsNull() {
            addCriterion("LocationNo is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNotNull() {
            addCriterion("LocationNo is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoEqualTo(String value) {
            addCriterion("LocationNo =", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotEqualTo(String value) {
            addCriterion("LocationNo <>", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThan(String value) {
            addCriterion("LocationNo >", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThanOrEqualTo(String value) {
            addCriterion("LocationNo >=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThan(String value) {
            addCriterion("LocationNo <", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThanOrEqualTo(String value) {
            addCriterion("LocationNo <=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLike(String value) {
            addCriterion("LocationNo like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotLike(String value) {
            addCriterion("LocationNo not like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIn(List<String> values) {
            addCriterion("LocationNo in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotIn(List<String> values) {
            addCriterion("LocationNo not in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoBetween(String value1, String value2) {
            addCriterion("LocationNo between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotBetween(String value1, String value2) {
            addCriterion("LocationNo not between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNull() {
            addCriterion("zipCode is null");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNotNull() {
            addCriterion("zipCode is not null");
            return (Criteria) this;
        }

        public Criteria andZipcodeEqualTo(String value) {
            addCriterion("zipCode =", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotEqualTo(String value) {
            addCriterion("zipCode <>", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThan(String value) {
            addCriterion("zipCode >", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("zipCode >=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThan(String value) {
            addCriterion("zipCode <", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThanOrEqualTo(String value) {
            addCriterion("zipCode <=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLike(String value) {
            addCriterion("zipCode like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotLike(String value) {
            addCriterion("zipCode not like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeIn(List<String> values) {
            addCriterion("zipCode in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotIn(List<String> values) {
            addCriterion("zipCode not in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeBetween(String value1, String value2) {
            addCriterion("zipCode between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotBetween(String value1, String value2) {
            addCriterion("zipCode not between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNull() {
            addCriterion("orderType is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("orderType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(String value) {
            addCriterion("orderType =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(String value) {
            addCriterion("orderType <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(String value) {
            addCriterion("orderType >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(String value) {
            addCriterion("orderType >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(String value) {
            addCriterion("orderType <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(String value) {
            addCriterion("orderType <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLike(String value) {
            addCriterion("orderType like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotLike(String value) {
            addCriterion("orderType not like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<String> values) {
            addCriterion("orderType in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<String> values) {
            addCriterion("orderType not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(String value1, String value2) {
            addCriterion("orderType between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(String value1, String value2) {
            addCriterion("orderType not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityIsNull() {
            addCriterion("OrderSourceEntity is null");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityIsNotNull() {
            addCriterion("OrderSourceEntity is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityEqualTo(String value) {
            addCriterion("OrderSourceEntity =", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityNotEqualTo(String value) {
            addCriterion("OrderSourceEntity <>", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityGreaterThan(String value) {
            addCriterion("OrderSourceEntity >", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityGreaterThanOrEqualTo(String value) {
            addCriterion("OrderSourceEntity >=", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityLessThan(String value) {
            addCriterion("OrderSourceEntity <", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityLessThanOrEqualTo(String value) {
            addCriterion("OrderSourceEntity <=", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityLike(String value) {
            addCriterion("OrderSourceEntity like", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityNotLike(String value) {
            addCriterion("OrderSourceEntity not like", value, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityIn(List<String> values) {
            addCriterion("OrderSourceEntity in", values, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityNotIn(List<String> values) {
            addCriterion("OrderSourceEntity not in", values, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityBetween(String value1, String value2) {
            addCriterion("OrderSourceEntity between", value1, value2, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andOrdersourceentityNotBetween(String value1, String value2) {
            addCriterion("OrderSourceEntity not between", value1, value2, "ordersourceentity");
            return (Criteria) this;
        }

        public Criteria andGzordernoIsNull() {
            addCriterion("GZOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andGzordernoIsNotNull() {
            addCriterion("GZOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andGzordernoEqualTo(String value) {
            addCriterion("GZOrderNo =", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoNotEqualTo(String value) {
            addCriterion("GZOrderNo <>", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoGreaterThan(String value) {
            addCriterion("GZOrderNo >", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoGreaterThanOrEqualTo(String value) {
            addCriterion("GZOrderNo >=", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoLessThan(String value) {
            addCriterion("GZOrderNo <", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoLessThanOrEqualTo(String value) {
            addCriterion("GZOrderNo <=", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoLike(String value) {
            addCriterion("GZOrderNo like", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoNotLike(String value) {
            addCriterion("GZOrderNo not like", value, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoIn(List<String> values) {
            addCriterion("GZOrderNo in", values, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoNotIn(List<String> values) {
            addCriterion("GZOrderNo not in", values, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoBetween(String value1, String value2) {
            addCriterion("GZOrderNo between", value1, value2, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andGzordernoNotBetween(String value1, String value2) {
            addCriterion("GZOrderNo not between", value1, value2, "gzorderno");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNull() {
            addCriterion("AddressId is null");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNotNull() {
            addCriterion("AddressId is not null");
            return (Criteria) this;
        }

        public Criteria andAddressidEqualTo(String value) {
            addCriterion("AddressId =", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotEqualTo(String value) {
            addCriterion("AddressId <>", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThan(String value) {
            addCriterion("AddressId >", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThanOrEqualTo(String value) {
            addCriterion("AddressId >=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThan(String value) {
            addCriterion("AddressId <", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThanOrEqualTo(String value) {
            addCriterion("AddressId <=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLike(String value) {
            addCriterion("AddressId like", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotLike(String value) {
            addCriterion("AddressId not like", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidIn(List<String> values) {
            addCriterion("AddressId in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotIn(List<String> values) {
            addCriterion("AddressId not in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidBetween(String value1, String value2) {
            addCriterion("AddressId between", value1, value2, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotBetween(String value1, String value2) {
            addCriterion("AddressId not between", value1, value2, "addressid");
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
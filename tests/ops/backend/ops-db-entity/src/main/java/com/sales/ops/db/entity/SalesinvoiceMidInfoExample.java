package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesinvoiceMidInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SalesinvoiceMidInfoExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andCustomernoIsNull() {
            addCriterion("customerno is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerno is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerno =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerno <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerno >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerno >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerno <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerno <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerno like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerno not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerno in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerno not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerno between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerno not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNull() {
            addCriterion("userno is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("userno is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("userno =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("userno <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("userno >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("userno >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("userno <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("userno <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("userno like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("userno not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("userno in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("userno not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("userno between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("userno not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("deptno is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("deptno is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("deptno =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("deptno <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("deptno >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("deptno >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("deptno <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("deptno <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("deptno like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("deptno not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("deptno in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("deptno not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("deptno between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("deptno not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelno is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelno is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelno =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelno <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelno >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelno >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelno <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelno <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelno like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelno not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelno in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelno not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelno between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelno not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andFeerateIsNull() {
            addCriterion("feerate is null");
            return (Criteria) this;
        }

        public Criteria andFeerateIsNotNull() {
            addCriterion("feerate is not null");
            return (Criteria) this;
        }

        public Criteria andFeerateEqualTo(BigDecimal value) {
            addCriterion("feerate =", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateNotEqualTo(BigDecimal value) {
            addCriterion("feerate <>", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateGreaterThan(BigDecimal value) {
            addCriterion("feerate >", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("feerate >=", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateLessThan(BigDecimal value) {
            addCriterion("feerate <", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("feerate <=", value, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateIn(List<BigDecimal> values) {
            addCriterion("feerate in", values, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateNotIn(List<BigDecimal> values) {
            addCriterion("feerate not in", values, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("feerate between", value1, value2, "feerate");
            return (Criteria) this;
        }

        public Criteria andFeerateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("feerate not between", value1, value2, "feerate");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNull() {
            addCriterion("tradecompanyid is null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNotNull() {
            addCriterion("tradecompanyid is not null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidEqualTo(String value) {
            addCriterion("tradecompanyid =", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotEqualTo(String value) {
            addCriterion("tradecompanyid <>", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThan(String value) {
            addCriterion("tradecompanyid >", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("tradecompanyid >=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThan(String value) {
            addCriterion("tradecompanyid <", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThanOrEqualTo(String value) {
            addCriterion("tradecompanyid <=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLike(String value) {
            addCriterion("tradecompanyid like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotLike(String value) {
            addCriterion("tradecompanyid not like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIn(List<String> values) {
            addCriterion("tradecompanyid in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotIn(List<String> values) {
            addCriterion("tradecompanyid not in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidBetween(String value1, String value2) {
            addCriterion("tradecompanyid between", value1, value2, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotBetween(String value1, String value2) {
            addCriterion("tradecompanyid not between", value1, value2, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("inserttime not between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andIsnewIsNull() {
            addCriterion("isnew is null");
            return (Criteria) this;
        }

        public Criteria andIsnewIsNotNull() {
            addCriterion("isnew is not null");
            return (Criteria) this;
        }

        public Criteria andIsnewEqualTo(String value) {
            addCriterion("isnew =", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewNotEqualTo(String value) {
            addCriterion("isnew <>", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewGreaterThan(String value) {
            addCriterion("isnew >", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewGreaterThanOrEqualTo(String value) {
            addCriterion("isnew >=", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewLessThan(String value) {
            addCriterion("isnew <", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewLessThanOrEqualTo(String value) {
            addCriterion("isnew <=", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewLike(String value) {
            addCriterion("isnew like", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewNotLike(String value) {
            addCriterion("isnew not like", value, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewIn(List<String> values) {
            addCriterion("isnew in", values, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewNotIn(List<String> values) {
            addCriterion("isnew not in", values, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewBetween(String value1, String value2) {
            addCriterion("isnew between", value1, value2, "isnew");
            return (Criteria) this;
        }

        public Criteria andIsnewNotBetween(String value1, String value2) {
            addCriterion("isnew not between", value1, value2, "isnew");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNull() {
            addCriterion("applyno is null");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNotNull() {
            addCriterion("applyno is not null");
            return (Criteria) this;
        }

        public Criteria andApplynoEqualTo(String value) {
            addCriterion("applyno =", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotEqualTo(String value) {
            addCriterion("applyno <>", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThan(String value) {
            addCriterion("applyno >", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThanOrEqualTo(String value) {
            addCriterion("applyno >=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThan(String value) {
            addCriterion("applyno <", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThanOrEqualTo(String value) {
            addCriterion("applyno <=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLike(String value) {
            addCriterion("applyno like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotLike(String value) {
            addCriterion("applyno not like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoIn(List<String> values) {
            addCriterion("applyno in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotIn(List<String> values) {
            addCriterion("applyno not in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoBetween(String value1, String value2) {
            addCriterion("applyno between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotBetween(String value1, String value2) {
            addCriterion("applyno not between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoItemIsNull() {
            addCriterion("applyno_item is null");
            return (Criteria) this;
        }

        public Criteria andApplynoItemIsNotNull() {
            addCriterion("applyno_item is not null");
            return (Criteria) this;
        }

        public Criteria andApplynoItemEqualTo(Integer value) {
            addCriterion("applyno_item =", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemNotEqualTo(Integer value) {
            addCriterion("applyno_item <>", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemGreaterThan(Integer value) {
            addCriterion("applyno_item >", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("applyno_item >=", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemLessThan(Integer value) {
            addCriterion("applyno_item <", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemLessThanOrEqualTo(Integer value) {
            addCriterion("applyno_item <=", value, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemIn(List<Integer> values) {
            addCriterion("applyno_item in", values, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemNotIn(List<Integer> values) {
            addCriterion("applyno_item not in", values, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemBetween(Integer value1, Integer value2) {
            addCriterion("applyno_item between", value1, value2, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andApplynoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("applyno_item not between", value1, value2, "applynoItem");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityIsNull() {
            addCriterion("canbackquantity is null");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityIsNotNull() {
            addCriterion("canbackquantity is not null");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityEqualTo(Integer value) {
            addCriterion("canbackquantity =", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityNotEqualTo(Integer value) {
            addCriterion("canbackquantity <>", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityGreaterThan(Integer value) {
            addCriterion("canbackquantity >", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("canbackquantity >=", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityLessThan(Integer value) {
            addCriterion("canbackquantity <", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityLessThanOrEqualTo(Integer value) {
            addCriterion("canbackquantity <=", value, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityIn(List<Integer> values) {
            addCriterion("canbackquantity in", values, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityNotIn(List<Integer> values) {
            addCriterion("canbackquantity not in", values, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityBetween(Integer value1, Integer value2) {
            addCriterion("canbackquantity between", value1, value2, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andCanbackquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("canbackquantity not between", value1, value2, "canbackquantity");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockIsNull() {
            addCriterion("tocustomerstock is null");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockIsNotNull() {
            addCriterion("tocustomerstock is not null");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockEqualTo(String value) {
            addCriterion("tocustomerstock =", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockNotEqualTo(String value) {
            addCriterion("tocustomerstock <>", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockGreaterThan(String value) {
            addCriterion("tocustomerstock >", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockGreaterThanOrEqualTo(String value) {
            addCriterion("tocustomerstock >=", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockLessThan(String value) {
            addCriterion("tocustomerstock <", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockLessThanOrEqualTo(String value) {
            addCriterion("tocustomerstock <=", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockLike(String value) {
            addCriterion("tocustomerstock like", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockNotLike(String value) {
            addCriterion("tocustomerstock not like", value, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockIn(List<String> values) {
            addCriterion("tocustomerstock in", values, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockNotIn(List<String> values) {
            addCriterion("tocustomerstock not in", values, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockBetween(String value1, String value2) {
            addCriterion("tocustomerstock between", value1, value2, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andTocustomerstockNotBetween(String value1, String value2) {
            addCriterion("tocustomerstock not between", value1, value2, "tocustomerstock");
            return (Criteria) this;
        }

        public Criteria andNewordernoIsNull() {
            addCriterion("neworderno is null");
            return (Criteria) this;
        }

        public Criteria andNewordernoIsNotNull() {
            addCriterion("neworderno is not null");
            return (Criteria) this;
        }

        public Criteria andNewordernoEqualTo(String value) {
            addCriterion("neworderno =", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoNotEqualTo(String value) {
            addCriterion("neworderno <>", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoGreaterThan(String value) {
            addCriterion("neworderno >", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoGreaterThanOrEqualTo(String value) {
            addCriterion("neworderno >=", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoLessThan(String value) {
            addCriterion("neworderno <", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoLessThanOrEqualTo(String value) {
            addCriterion("neworderno <=", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoLike(String value) {
            addCriterion("neworderno like", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoNotLike(String value) {
            addCriterion("neworderno not like", value, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoIn(List<String> values) {
            addCriterion("neworderno in", values, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoNotIn(List<String> values) {
            addCriterion("neworderno not in", values, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoBetween(String value1, String value2) {
            addCriterion("neworderno between", value1, value2, "neworderno");
            return (Criteria) this;
        }

        public Criteria andNewordernoNotBetween(String value1, String value2) {
            addCriterion("neworderno not between", value1, value2, "neworderno");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNull() {
            addCriterion("rorder_no is null");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNotNull() {
            addCriterion("rorder_no is not null");
            return (Criteria) this;
        }

        public Criteria andRorderNoEqualTo(String value) {
            addCriterion("rorder_no =", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotEqualTo(String value) {
            addCriterion("rorder_no <>", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThan(String value) {
            addCriterion("rorder_no >", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_no >=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThan(String value) {
            addCriterion("rorder_no <", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThanOrEqualTo(String value) {
            addCriterion("rorder_no <=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLike(String value) {
            addCriterion("rorder_no like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotLike(String value) {
            addCriterion("rorder_no not like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoIn(List<String> values) {
            addCriterion("rorder_no in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotIn(List<String> values) {
            addCriterion("rorder_no not in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoBetween(String value1, String value2) {
            addCriterion("rorder_no between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotBetween(String value1, String value2) {
            addCriterion("rorder_no not between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNull() {
            addCriterion("rorder_item is null");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNotNull() {
            addCriterion("rorder_item is not null");
            return (Criteria) this;
        }

        public Criteria andRorderItemEqualTo(Integer value) {
            addCriterion("rorder_item =", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotEqualTo(Integer value) {
            addCriterion("rorder_item <>", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThan(Integer value) {
            addCriterion("rorder_item >", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("rorder_item >=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThan(Integer value) {
            addCriterion("rorder_item <", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThanOrEqualTo(Integer value) {
            addCriterion("rorder_item <=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemIn(List<Integer> values) {
            addCriterion("rorder_item in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotIn(List<Integer> values) {
            addCriterion("rorder_item not in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item not between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNull() {
            addCriterion("state_code is null");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNotNull() {
            addCriterion("state_code is not null");
            return (Criteria) this;
        }

        public Criteria andStateCodeEqualTo(String value) {
            addCriterion("state_code =", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotEqualTo(String value) {
            addCriterion("state_code <>", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThan(String value) {
            addCriterion("state_code >", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("state_code >=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThan(String value) {
            addCriterion("state_code <", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThanOrEqualTo(String value) {
            addCriterion("state_code <=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLike(String value) {
            addCriterion("state_code like", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotLike(String value) {
            addCriterion("state_code not like", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeIn(List<String> values) {
            addCriterion("state_code in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotIn(List<String> values) {
            addCriterion("state_code not in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeBetween(String value1, String value2) {
            addCriterion("state_code between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotBetween(String value1, String value2) {
            addCriterion("state_code not between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
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
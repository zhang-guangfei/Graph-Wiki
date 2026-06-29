package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderdataGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderdataGzExample() {
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
            addCriterion("rorderno is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("rorderno is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("rorderno =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("rorderno <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("rorderno >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("rorderno >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("rorderno <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("rorderno <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("rorderno like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("rorderno not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("rorderno in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("rorderno not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("rorderno between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("rorderno not between", value1, value2, "rorderno");
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

        public Criteria andItemnoEqualTo(String value) {
            addCriterion("itemno =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(String value) {
            addCriterion("itemno <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(String value) {
            addCriterion("itemno >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(String value) {
            addCriterion("itemno >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(String value) {
            addCriterion("itemno <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(String value) {
            addCriterion("itemno <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLike(String value) {
            addCriterion("itemno like", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotLike(String value) {
            addCriterion("itemno not like", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<String> values) {
            addCriterion("itemno in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<String> values) {
            addCriterion("itemno not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(String value1, String value2) {
            addCriterion("itemno between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(String value1, String value2) {
            addCriterion("itemno not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNull() {
            addCriterion("stateCode is null");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNotNull() {
            addCriterion("stateCode is not null");
            return (Criteria) this;
        }

        public Criteria andStatecodeEqualTo(String value) {
            addCriterion("stateCode =", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotEqualTo(String value) {
            addCriterion("stateCode <>", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThan(String value) {
            addCriterion("stateCode >", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("stateCode >=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThan(String value) {
            addCriterion("stateCode <", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThanOrEqualTo(String value) {
            addCriterion("stateCode <=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLike(String value) {
            addCriterion("stateCode like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotLike(String value) {
            addCriterion("stateCode not like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeIn(List<String> values) {
            addCriterion("stateCode in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotIn(List<String> values) {
            addCriterion("stateCode not in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeBetween(String value1, String value2) {
            addCriterion("stateCode between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotBetween(String value1, String value2) {
            addCriterion("stateCode not between", value1, value2, "statecode");
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

        public Criteria andDeptnoIsNull() {
            addCriterion("deptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("deptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("deptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("deptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("deptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("deptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("deptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("deptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("deptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("deptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("deptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("deptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("deptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("deptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("ordType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("ordType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("ordType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("ordType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("ordType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ordType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("ordType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("ordType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("ordType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("ordType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("ordType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("ordType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("ordType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("ordType not between", value1, value2, "ordtype");
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

        public Criteria andSpecmarkIsNull() {
            addCriterion("specMark is null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNotNull() {
            addCriterion("specMark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkEqualTo(String value) {
            addCriterion("specMark =", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotEqualTo(String value) {
            addCriterion("specMark <>", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThan(String value) {
            addCriterion("specMark >", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThanOrEqualTo(String value) {
            addCriterion("specMark >=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThan(String value) {
            addCriterion("specMark <", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThanOrEqualTo(String value) {
            addCriterion("specMark <=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLike(String value) {
            addCriterion("specMark like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotLike(String value) {
            addCriterion("specMark not like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIn(List<String> values) {
            addCriterion("specMark in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotIn(List<String> values) {
            addCriterion("specMark not in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkBetween(String value1, String value2) {
            addCriterion("specMark between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotBetween(String value1, String value2) {
            addCriterion("specMark not between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNull() {
            addCriterion("hopeDeliveryDate is null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNotNull() {
            addCriterion("hopeDeliveryDate is not null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateEqualTo(Date value) {
            addCriterion("hopeDeliveryDate =", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotEqualTo(Date value) {
            addCriterion("hopeDeliveryDate <>", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThan(Date value) {
            addCriterion("hopeDeliveryDate >", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThanOrEqualTo(Date value) {
            addCriterion("hopeDeliveryDate >=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThan(Date value) {
            addCriterion("hopeDeliveryDate <", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThanOrEqualTo(Date value) {
            addCriterion("hopeDeliveryDate <=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIn(List<Date> values) {
            addCriterion("hopeDeliveryDate in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotIn(List<Date> values) {
            addCriterion("hopeDeliveryDate not in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateBetween(Date value1, Date value2) {
            addCriterion("hopeDeliveryDate between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotBetween(Date value1, Date value2) {
            addCriterion("hopeDeliveryDate not between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIsNull() {
            addCriterion("requestTime is null");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIsNotNull() {
            addCriterion("requestTime is not null");
            return (Criteria) this;
        }

        public Criteria andRequesttimeEqualTo(Date value) {
            addCriterion("requestTime =", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotEqualTo(Date value) {
            addCriterion("requestTime <>", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeGreaterThan(Date value) {
            addCriterion("requestTime >", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("requestTime >=", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeLessThan(Date value) {
            addCriterion("requestTime <", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeLessThanOrEqualTo(Date value) {
            addCriterion("requestTime <=", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIn(List<Date> values) {
            addCriterion("requestTime in", values, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotIn(List<Date> values) {
            addCriterion("requestTime not in", values, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeBetween(Date value1, Date value2) {
            addCriterion("requestTime between", value1, value2, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotBetween(Date value1, Date value2) {
            addCriterion("requestTime not between", value1, value2, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIsNull() {
            addCriterion("requestWarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIsNotNull() {
            addCriterion("requestWarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidEqualTo(String value) {
            addCriterion("requestWarehouseId =", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotEqualTo(String value) {
            addCriterion("requestWarehouseId <>", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidGreaterThan(String value) {
            addCriterion("requestWarehouseId >", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("requestWarehouseId >=", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLessThan(String value) {
            addCriterion("requestWarehouseId <", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLessThanOrEqualTo(String value) {
            addCriterion("requestWarehouseId <=", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLike(String value) {
            addCriterion("requestWarehouseId like", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotLike(String value) {
            addCriterion("requestWarehouseId not like", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIn(List<String> values) {
            addCriterion("requestWarehouseId in", values, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotIn(List<String> values) {
            addCriterion("requestWarehouseId not in", values, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidBetween(String value1, String value2) {
            addCriterion("requestWarehouseId between", value1, value2, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotBetween(String value1, String value2) {
            addCriterion("requestWarehouseId not between", value1, value2, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNull() {
            addCriterion("purchaseType is null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNotNull() {
            addCriterion("purchaseType is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeEqualTo(String value) {
            addCriterion("purchaseType =", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotEqualTo(String value) {
            addCriterion("purchaseType <>", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThan(String value) {
            addCriterion("purchaseType >", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseType >=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThan(String value) {
            addCriterion("purchaseType <", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThanOrEqualTo(String value) {
            addCriterion("purchaseType <=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLike(String value) {
            addCriterion("purchaseType like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotLike(String value) {
            addCriterion("purchaseType not like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIn(List<String> values) {
            addCriterion("purchaseType in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotIn(List<String> values) {
            addCriterion("purchaseType not in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeBetween(String value1, String value2) {
            addCriterion("purchaseType between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotBetween(String value1, String value2) {
            addCriterion("purchaseType not between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNull() {
            addCriterion("OrdDate is null");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNotNull() {
            addCriterion("OrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andOrddateEqualTo(Date value) {
            addCriterion("OrdDate =", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotEqualTo(Date value) {
            addCriterion("OrdDate <>", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThan(Date value) {
            addCriterion("OrdDate >", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThanOrEqualTo(Date value) {
            addCriterion("OrdDate >=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThan(Date value) {
            addCriterion("OrdDate <", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThanOrEqualTo(Date value) {
            addCriterion("OrdDate <=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateIn(List<Date> values) {
            addCriterion("OrdDate in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotIn(List<Date> values) {
            addCriterion("OrdDate not in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateBetween(Date value1, Date value2) {
            addCriterion("OrdDate between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotBetween(Date value1, Date value2) {
            addCriterion("OrdDate not between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIsNull() {
            addCriterion("purchaseWarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIsNotNull() {
            addCriterion("purchaseWarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidEqualTo(String value) {
            addCriterion("purchaseWarehouseId =", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotEqualTo(String value) {
            addCriterion("purchaseWarehouseId <>", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidGreaterThan(String value) {
            addCriterion("purchaseWarehouseId >", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseWarehouseId >=", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLessThan(String value) {
            addCriterion("purchaseWarehouseId <", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLessThanOrEqualTo(String value) {
            addCriterion("purchaseWarehouseId <=", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLike(String value) {
            addCriterion("purchaseWarehouseId like", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotLike(String value) {
            addCriterion("purchaseWarehouseId not like", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIn(List<String> values) {
            addCriterion("purchaseWarehouseId in", values, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotIn(List<String> values) {
            addCriterion("purchaseWarehouseId not in", values, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidBetween(String value1, String value2) {
            addCriterion("purchaseWarehouseId between", value1, value2, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotBetween(String value1, String value2) {
            addCriterion("purchaseWarehouseId not between", value1, value2, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNull() {
            addCriterion("TransType is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("TransType is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("TransType =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("TransType <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("TransType >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("TransType >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("TransType <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("TransType <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("TransType like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("TransType not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("TransType in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("TransType not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("TransType between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("TransType not between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNull() {
            addCriterion("inventoryPropertyId is null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNotNull() {
            addCriterion("inventoryPropertyId is not null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidEqualTo(Integer value) {
            addCriterion("inventoryPropertyId =", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotEqualTo(Integer value) {
            addCriterion("inventoryPropertyId <>", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThan(Integer value) {
            addCriterion("inventoryPropertyId >", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThanOrEqualTo(Integer value) {
            addCriterion("inventoryPropertyId >=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThan(Integer value) {
            addCriterion("inventoryPropertyId <", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThanOrEqualTo(Integer value) {
            addCriterion("inventoryPropertyId <=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIn(List<Integer> values) {
            addCriterion("inventoryPropertyId in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotIn(List<Integer> values) {
            addCriterion("inventoryPropertyId not in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidBetween(Integer value1, Integer value2) {
            addCriterion("inventoryPropertyId between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotBetween(Integer value1, Integer value2) {
            addCriterion("inventoryPropertyId not between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNull() {
            addCriterion("corderNO is null");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNotNull() {
            addCriterion("corderNO is not null");
            return (Criteria) this;
        }

        public Criteria andCordernoEqualTo(String value) {
            addCriterion("corderNO =", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotEqualTo(String value) {
            addCriterion("corderNO <>", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThan(String value) {
            addCriterion("corderNO >", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThanOrEqualTo(String value) {
            addCriterion("corderNO >=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThan(String value) {
            addCriterion("corderNO <", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThanOrEqualTo(String value) {
            addCriterion("corderNO <=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLike(String value) {
            addCriterion("corderNO like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotLike(String value) {
            addCriterion("corderNO not like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoIn(List<String> values) {
            addCriterion("corderNO in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotIn(List<String> values) {
            addCriterion("corderNO not in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoBetween(String value1, String value2) {
            addCriterion("corderNO between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotBetween(String value1, String value2) {
            addCriterion("corderNO not between", value1, value2, "corderno");
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

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIsNull() {
            addCriterion("inventory_status is null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIsNotNull() {
            addCriterion("inventory_status is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusEqualTo(String value) {
            addCriterion("inventory_status =", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotEqualTo(String value) {
            addCriterion("inventory_status <>", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThan(String value) {
            addCriterion("inventory_status >", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_status >=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThan(String value) {
            addCriterion("inventory_status <", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThanOrEqualTo(String value) {
            addCriterion("inventory_status <=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLike(String value) {
            addCriterion("inventory_status like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotLike(String value) {
            addCriterion("inventory_status not like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIn(List<String> values) {
            addCriterion("inventory_status in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotIn(List<String> values) {
            addCriterion("inventory_status not in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusBetween(String value1, String value2) {
            addCriterion("inventory_status between", value1, value2, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotBetween(String value1, String value2) {
            addCriterion("inventory_status not between", value1, value2, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Integer value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Integer value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Integer value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Integer value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Integer value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Integer> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Integer> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Integer value1, Integer value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNull() {
            addCriterion("qa_status is null");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNotNull() {
            addCriterion("qa_status is not null");
            return (Criteria) this;
        }

        public Criteria andQaStatusEqualTo(Integer value) {
            addCriterion("qa_status =", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotEqualTo(Integer value) {
            addCriterion("qa_status <>", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThan(Integer value) {
            addCriterion("qa_status >", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("qa_status >=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThan(Integer value) {
            addCriterion("qa_status <", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThanOrEqualTo(Integer value) {
            addCriterion("qa_status <=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusIn(List<Integer> values) {
            addCriterion("qa_status in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotIn(List<Integer> values) {
            addCriterion("qa_status not in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusBetween(Integer value1, Integer value2) {
            addCriterion("qa_status between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("qa_status not between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIsNull() {
            addCriterion("prepare_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIsNotNull() {
            addCriterion("prepare_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityEqualTo(BigDecimal value) {
            addCriterion("prepare_quantity =", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotEqualTo(BigDecimal value) {
            addCriterion("prepare_quantity <>", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThan(BigDecimal value) {
            addCriterion("prepare_quantity >", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prepare_quantity >=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThan(BigDecimal value) {
            addCriterion("prepare_quantity <", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prepare_quantity <=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIn(List<BigDecimal> values) {
            addCriterion("prepare_quantity in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotIn(List<BigDecimal> values) {
            addCriterion("prepare_quantity not in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prepare_quantity between", value1, value2, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prepare_quantity not between", value1, value2, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("Code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("Code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("Code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("Code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("Code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("Code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("Code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("Code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("Code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("Code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("Code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("Code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("Code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateIsNull() {
            addCriterion("ExArrivalDate is null");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateIsNotNull() {
            addCriterion("ExArrivalDate is not null");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateEqualTo(Date value) {
            addCriterion("ExArrivalDate =", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateNotEqualTo(Date value) {
            addCriterion("ExArrivalDate <>", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateGreaterThan(Date value) {
            addCriterion("ExArrivalDate >", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateGreaterThanOrEqualTo(Date value) {
            addCriterion("ExArrivalDate >=", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateLessThan(Date value) {
            addCriterion("ExArrivalDate <", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateLessThanOrEqualTo(Date value) {
            addCriterion("ExArrivalDate <=", value, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateIn(List<Date> values) {
            addCriterion("ExArrivalDate in", values, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateNotIn(List<Date> values) {
            addCriterion("ExArrivalDate not in", values, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateBetween(Date value1, Date value2) {
            addCriterion("ExArrivalDate between", value1, value2, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andExarrivaldateNotBetween(Date value1, Date value2) {
            addCriterion("ExArrivalDate not between", value1, value2, "exarrivaldate");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceno is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceno is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceno =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceno <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceno >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceno >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceno <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceno <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceno like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceno not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceno in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceno not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceno between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceno not between", value1, value2, "invoiceno");
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
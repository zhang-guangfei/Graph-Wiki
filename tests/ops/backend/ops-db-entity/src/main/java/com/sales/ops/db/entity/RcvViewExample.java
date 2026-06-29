package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RcvViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvViewExample() {
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

        public Criteria andDeliveryDeptNoIsNull() {
            addCriterion("delivery_dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoIsNotNull() {
            addCriterion("delivery_dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoEqualTo(String value) {
            addCriterion("delivery_dept_no =", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotEqualTo(String value) {
            addCriterion("delivery_dept_no <>", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoGreaterThan(String value) {
            addCriterion("delivery_dept_no >", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_dept_no >=", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLessThan(String value) {
            addCriterion("delivery_dept_no <", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLessThanOrEqualTo(String value) {
            addCriterion("delivery_dept_no <=", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLike(String value) {
            addCriterion("delivery_dept_no like", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotLike(String value) {
            addCriterion("delivery_dept_no not like", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoIn(List<String> values) {
            addCriterion("delivery_dept_no in", values, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotIn(List<String> values) {
            addCriterion("delivery_dept_no not in", values, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoBetween(String value1, String value2) {
            addCriterion("delivery_dept_no between", value1, value2, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotBetween(String value1, String value2) {
            addCriterion("delivery_dept_no not between", value1, value2, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_No is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_No is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_No =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_No <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_No >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_No >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_No <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_No <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_No like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_No not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_No in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_No not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_No between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_No not between", value1, value2, "deptNo");
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

        public Criteria andRorderFnoIsNull() {
            addCriterion("rorder_fno is null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIsNotNull() {
            addCriterion("rorder_fno is not null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoEqualTo(String value) {
            addCriterion("rorder_fno =", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotEqualTo(String value) {
            addCriterion("rorder_fno <>", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThan(String value) {
            addCriterion("rorder_fno >", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_fno >=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThan(String value) {
            addCriterion("rorder_fno <", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThanOrEqualTo(String value) {
            addCriterion("rorder_fno <=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLike(String value) {
            addCriterion("rorder_fno like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotLike(String value) {
            addCriterion("rorder_fno not like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIn(List<String> values) {
            addCriterion("rorder_fno in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotIn(List<String> values) {
            addCriterion("rorder_fno not in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoBetween(String value1, String value2) {
            addCriterion("rorder_fno between", value1, value2, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotBetween(String value1, String value2) {
            addCriterion("rorder_fno not between", value1, value2, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNull() {
            addCriterion("model_no is null");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNotNull() {
            addCriterion("model_no is not null");
            return (Criteria) this;
        }

        public Criteria andModelNoEqualTo(String value) {
            addCriterion("model_no =", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotEqualTo(String value) {
            addCriterion("model_no <>", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThan(String value) {
            addCriterion("model_no >", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("model_no >=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThan(String value) {
            addCriterion("model_no <", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThanOrEqualTo(String value) {
            addCriterion("model_no <=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLike(String value) {
            addCriterion("model_no like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotLike(String value) {
            addCriterion("model_no not like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoIn(List<String> values) {
            addCriterion("model_no in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotIn(List<String> values) {
            addCriterion("model_no not in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoBetween(String value1, String value2) {
            addCriterion("model_no between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotBetween(String value1, String value2) {
            addCriterion("model_no not between", value1, value2, "modelNo");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andInterceptIsNull() {
            addCriterion("intercept is null");
            return (Criteria) this;
        }

        public Criteria andInterceptIsNotNull() {
            addCriterion("intercept is not null");
            return (Criteria) this;
        }

        public Criteria andInterceptEqualTo(Boolean value) {
            addCriterion("intercept =", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotEqualTo(Boolean value) {
            addCriterion("intercept <>", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptGreaterThan(Boolean value) {
            addCriterion("intercept >", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("intercept >=", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptLessThan(Boolean value) {
            addCriterion("intercept <", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptLessThanOrEqualTo(Boolean value) {
            addCriterion("intercept <=", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptIn(List<Boolean> values) {
            addCriterion("intercept in", values, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotIn(List<Boolean> values) {
            addCriterion("intercept not in", values, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptBetween(Boolean value1, Boolean value2) {
            addCriterion("intercept between", value1, value2, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("intercept not between", value1, value2, "intercept");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andCorderNoIsNull() {
            addCriterion("corder_no is null");
            return (Criteria) this;
        }

        public Criteria andCorderNoIsNotNull() {
            addCriterion("corder_no is not null");
            return (Criteria) this;
        }

        public Criteria andCorderNoEqualTo(String value) {
            addCriterion("corder_no =", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotEqualTo(String value) {
            addCriterion("corder_no <>", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThan(String value) {
            addCriterion("corder_no >", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("corder_no >=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThan(String value) {
            addCriterion("corder_no <", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThanOrEqualTo(String value) {
            addCriterion("corder_no <=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLike(String value) {
            addCriterion("corder_no like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotLike(String value) {
            addCriterion("corder_no not like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoIn(List<String> values) {
            addCriterion("corder_no in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotIn(List<String> values) {
            addCriterion("corder_no not in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoBetween(String value1, String value2) {
            addCriterion("corder_no between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotBetween(String value1, String value2) {
            addCriterion("corder_no not between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andStockTypeIsNull() {
            addCriterion("stock_type is null");
            return (Criteria) this;
        }

        public Criteria andStockTypeIsNotNull() {
            addCriterion("stock_type is not null");
            return (Criteria) this;
        }

        public Criteria andStockTypeEqualTo(String value) {
            addCriterion("stock_type =", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotEqualTo(String value) {
            addCriterion("stock_type <>", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThan(String value) {
            addCriterion("stock_type >", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_type >=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThan(String value) {
            addCriterion("stock_type <", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThanOrEqualTo(String value) {
            addCriterion("stock_type <=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLike(String value) {
            addCriterion("stock_type like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotLike(String value) {
            addCriterion("stock_type not like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeIn(List<String> values) {
            addCriterion("stock_type in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotIn(List<String> values) {
            addCriterion("stock_type not in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeBetween(String value1, String value2) {
            addCriterion("stock_type between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotBetween(String value1, String value2) {
            addCriterion("stock_type not between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNull() {
            addCriterion("stock_code is null");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNotNull() {
            addCriterion("stock_code is not null");
            return (Criteria) this;
        }

        public Criteria andStockCodeEqualTo(String value) {
            addCriterion("stock_code =", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotEqualTo(String value) {
            addCriterion("stock_code <>", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThan(String value) {
            addCriterion("stock_code >", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_code >=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThan(String value) {
            addCriterion("stock_code <", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThanOrEqualTo(String value) {
            addCriterion("stock_code <=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLike(String value) {
            addCriterion("stock_code like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotLike(String value) {
            addCriterion("stock_code not like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeIn(List<String> values) {
            addCriterion("stock_code in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotIn(List<String> values) {
            addCriterion("stock_code not in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeBetween(String value1, String value2) {
            addCriterion("stock_code between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotBetween(String value1, String value2) {
            addCriterion("stock_code not between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andProdFlagIsNull() {
            addCriterion("prod_flag is null");
            return (Criteria) this;
        }

        public Criteria andProdFlagIsNotNull() {
            addCriterion("prod_flag is not null");
            return (Criteria) this;
        }

        public Criteria andProdFlagEqualTo(String value) {
            addCriterion("prod_flag =", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotEqualTo(String value) {
            addCriterion("prod_flag <>", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagGreaterThan(String value) {
            addCriterion("prod_flag >", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagGreaterThanOrEqualTo(String value) {
            addCriterion("prod_flag >=", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLessThan(String value) {
            addCriterion("prod_flag <", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLessThanOrEqualTo(String value) {
            addCriterion("prod_flag <=", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLike(String value) {
            addCriterion("prod_flag like", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotLike(String value) {
            addCriterion("prod_flag not like", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagIn(List<String> values) {
            addCriterion("prod_flag in", values, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotIn(List<String> values) {
            addCriterion("prod_flag not in", values, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagBetween(String value1, String value2) {
            addCriterion("prod_flag between", value1, value2, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotBetween(String value1, String value2) {
            addCriterion("prod_flag not between", value1, value2, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIsNull() {
            addCriterion("ready_qty is null");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIsNotNull() {
            addCriterion("ready_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReadyQtyEqualTo(Integer value) {
            addCriterion("ready_qty =", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotEqualTo(Integer value) {
            addCriterion("ready_qty <>", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyGreaterThan(Integer value) {
            addCriterion("ready_qty >", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ready_qty >=", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyLessThan(Integer value) {
            addCriterion("ready_qty <", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyLessThanOrEqualTo(Integer value) {
            addCriterion("ready_qty <=", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIn(List<Integer> values) {
            addCriterion("ready_qty in", values, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotIn(List<Integer> values) {
            addCriterion("ready_qty not in", values, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyBetween(Integer value1, Integer value2) {
            addCriterion("ready_qty between", value1, value2, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ready_qty not between", value1, value2, "readyQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNull() {
            addCriterion("exp_qty is null");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNotNull() {
            addCriterion("exp_qty is not null");
            return (Criteria) this;
        }

        public Criteria andExpQtyEqualTo(Integer value) {
            addCriterion("exp_qty =", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotEqualTo(Integer value) {
            addCriterion("exp_qty <>", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThan(Integer value) {
            addCriterion("exp_qty >", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_qty >=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThan(Integer value) {
            addCriterion("exp_qty <", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThanOrEqualTo(Integer value) {
            addCriterion("exp_qty <=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyIn(List<Integer> values) {
            addCriterion("exp_qty in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotIn(List<Integer> values) {
            addCriterion("exp_qty not in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty not between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIsNull() {
            addCriterion("returned_qty is null");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIsNotNull() {
            addCriterion("returned_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyEqualTo(Integer value) {
            addCriterion("returned_qty =", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotEqualTo(Integer value) {
            addCriterion("returned_qty <>", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyGreaterThan(Integer value) {
            addCriterion("returned_qty >", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("returned_qty >=", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyLessThan(Integer value) {
            addCriterion("returned_qty <", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyLessThanOrEqualTo(Integer value) {
            addCriterion("returned_qty <=", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIn(List<Integer> values) {
            addCriterion("returned_qty in", values, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotIn(List<Integer> values) {
            addCriterion("returned_qty not in", values, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyBetween(Integer value1, Integer value2) {
            addCriterion("returned_qty between", value1, value2, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("returned_qty not between", value1, value2, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNull() {
            addCriterion("invoice_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNotNull() {
            addCriterion("invoice_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyEqualTo(Integer value) {
            addCriterion("invoice_qty =", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotEqualTo(Integer value) {
            addCriterion("invoice_qty <>", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThan(Integer value) {
            addCriterion("invoice_qty >", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty >=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThan(Integer value) {
            addCriterion("invoice_qty <", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty <=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIn(List<Integer> values) {
            addCriterion("invoice_qty in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotIn(List<Integer> values) {
            addCriterion("invoice_qty not in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty not between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Short value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Short value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Short value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Short value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Short value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Short> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Short> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Short value1, Short value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Short value1, Short value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNull() {
            addCriterion("ROrdDate is null");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNotNull() {
            addCriterion("ROrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andRorddateEqualTo(Date value) {
            addCriterion("ROrdDate =", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotEqualTo(Date value) {
            addCriterion("ROrdDate <>", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThan(Date value) {
            addCriterion("ROrdDate >", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThanOrEqualTo(Date value) {
            addCriterion("ROrdDate >=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThan(Date value) {
            addCriterion("ROrdDate <", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThanOrEqualTo(Date value) {
            addCriterion("ROrdDate <=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateIn(List<Date> values) {
            addCriterion("ROrdDate in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotIn(List<Date> values) {
            addCriterion("ROrdDate not in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateBetween(Date value1, Date value2) {
            addCriterion("ROrdDate between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotBetween(Date value1, Date value2) {
            addCriterion("ROrdDate not between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNull() {
            addCriterion("dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNotNull() {
            addCriterion("dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andDlvDateEqualTo(Date value) {
            addCriterion("dlv_date =", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotEqualTo(Date value) {
            addCriterion("dlv_date <>", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThan(Date value) {
            addCriterion("dlv_date >", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("dlv_date >=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThan(Date value) {
            addCriterion("dlv_date <", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("dlv_date <=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateIn(List<Date> values) {
            addCriterion("dlv_date in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotIn(List<Date> values) {
            addCriterion("dlv_date not in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateBetween(Date value1, Date value2) {
            addCriterion("dlv_date between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("dlv_date not between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIsNull() {
            addCriterion("wms_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIsNotNull() {
            addCriterion("wms_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date =", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <>", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThan(Date value) {
            addCriterionForJDBCDate("wms_dlv_date >", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date >=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThan(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIn(List<Date> values) {
            addCriterionForJDBCDate("wms_dlv_date in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wms_dlv_date not in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wms_dlv_date between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wms_dlv_date not between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateIsNull() {
            addCriterion("cdlv_date is null");
            return (Criteria) this;
        }

        public Criteria andCdlvDateIsNotNull() {
            addCriterion("cdlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andCdlvDateEqualTo(Date value) {
            addCriterion("cdlv_date =", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotEqualTo(Date value) {
            addCriterion("cdlv_date <>", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateGreaterThan(Date value) {
            addCriterion("cdlv_date >", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cdlv_date >=", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateLessThan(Date value) {
            addCriterion("cdlv_date <", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateLessThanOrEqualTo(Date value) {
            addCriterion("cdlv_date <=", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateIn(List<Date> values) {
            addCriterion("cdlv_date in", values, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotIn(List<Date> values) {
            addCriterion("cdlv_date not in", values, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateBetween(Date value1, Date value2) {
            addCriterion("cdlv_date between", value1, value2, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotBetween(Date value1, Date value2) {
            addCriterion("cdlv_date not between", value1, value2, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNull() {
            addCriterion("dlv_entire is null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNotNull() {
            addCriterion("dlv_entire is not null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireEqualTo(String value) {
            addCriterion("dlv_entire =", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotEqualTo(String value) {
            addCriterion("dlv_entire <>", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThan(String value) {
            addCriterion("dlv_entire >", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_entire >=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThan(String value) {
            addCriterion("dlv_entire <", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThanOrEqualTo(String value) {
            addCriterion("dlv_entire <=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLike(String value) {
            addCriterion("dlv_entire like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotLike(String value) {
            addCriterion("dlv_entire not like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIn(List<String> values) {
            addCriterion("dlv_entire in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotIn(List<String> values) {
            addCriterion("dlv_entire not in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireBetween(String value1, String value2) {
            addCriterion("dlv_entire between", value1, value2, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotBetween(String value1, String value2) {
            addCriterion("dlv_entire not between", value1, value2, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIsNull() {
            addCriterion("ready_time is null");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIsNotNull() {
            addCriterion("ready_time is not null");
            return (Criteria) this;
        }

        public Criteria andReadyTimeEqualTo(Date value) {
            addCriterion("ready_time =", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotEqualTo(Date value) {
            addCriterion("ready_time <>", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeGreaterThan(Date value) {
            addCriterion("ready_time >", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ready_time >=", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeLessThan(Date value) {
            addCriterion("ready_time <", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeLessThanOrEqualTo(Date value) {
            addCriterion("ready_time <=", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIn(List<Date> values) {
            addCriterion("ready_time in", values, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotIn(List<Date> values) {
            addCriterion("ready_time not in", values, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeBetween(Date value1, Date value2) {
            addCriterion("ready_time between", value1, value2, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotBetween(Date value1, Date value2) {
            addCriterion("ready_time not between", value1, value2, "readyTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeIsNull() {
            addCriterion("ship_time is null");
            return (Criteria) this;
        }

        public Criteria andShipTimeIsNotNull() {
            addCriterion("ship_time is not null");
            return (Criteria) this;
        }

        public Criteria andShipTimeEqualTo(Date value) {
            addCriterion("ship_time =", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotEqualTo(Date value) {
            addCriterion("ship_time <>", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeGreaterThan(Date value) {
            addCriterion("ship_time >", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_time >=", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeLessThan(Date value) {
            addCriterion("ship_time <", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeLessThanOrEqualTo(Date value) {
            addCriterion("ship_time <=", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeIn(List<Date> values) {
            addCriterion("ship_time in", values, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotIn(List<Date> values) {
            addCriterion("ship_time not in", values, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeBetween(Date value1, Date value2) {
            addCriterion("ship_time between", value1, value2, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotBetween(Date value1, Date value2) {
            addCriterion("ship_time not between", value1, value2, "shipTime");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIsNull() {
            addCriterion("spec_mark is null");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIsNotNull() {
            addCriterion("spec_mark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecMarkEqualTo(String value) {
            addCriterion("spec_mark =", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotEqualTo(String value) {
            addCriterion("spec_mark <>", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkGreaterThan(String value) {
            addCriterion("spec_mark >", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkGreaterThanOrEqualTo(String value) {
            addCriterion("spec_mark >=", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLessThan(String value) {
            addCriterion("spec_mark <", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLessThanOrEqualTo(String value) {
            addCriterion("spec_mark <=", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLike(String value) {
            addCriterion("spec_mark like", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotLike(String value) {
            addCriterion("spec_mark not like", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIn(List<String> values) {
            addCriterion("spec_mark in", values, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotIn(List<String> values) {
            addCriterion("spec_mark not in", values, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkBetween(String value1, String value2) {
            addCriterion("spec_mark between", value1, value2, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotBetween(String value1, String value2) {
            addCriterion("spec_mark not between", value1, value2, "specMark");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNull() {
            addCriterion("exp_dlv_type is null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNotNull() {
            addCriterion("exp_dlv_type is not null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeEqualTo(Integer value) {
            addCriterion("exp_dlv_type =", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotEqualTo(Integer value) {
            addCriterion("exp_dlv_type <>", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThan(Integer value) {
            addCriterion("exp_dlv_type >", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type >=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThan(Integer value) {
            addCriterion("exp_dlv_type <", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type <=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIn(List<Integer> values) {
            addCriterion("exp_dlv_type in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotIn(List<Integer> values) {
            addCriterion("exp_dlv_type not in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type between", value1, value2, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type not between", value1, value2, "expDlvType");
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

        public Criteria andTradeCompanyidIsNull() {
            addCriterion("trade_companyId is null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIsNotNull() {
            addCriterion("trade_companyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidEqualTo(String value) {
            addCriterion("trade_companyId =", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotEqualTo(String value) {
            addCriterion("trade_companyId <>", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThan(String value) {
            addCriterion("trade_companyId >", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("trade_companyId >=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThan(String value) {
            addCriterion("trade_companyId <", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThanOrEqualTo(String value) {
            addCriterion("trade_companyId <=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLike(String value) {
            addCriterion("trade_companyId like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotLike(String value) {
            addCriterion("trade_companyId not like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIn(List<String> values) {
            addCriterion("trade_companyId in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotIn(List<String> values) {
            addCriterion("trade_companyId not in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidBetween(String value1, String value2) {
            addCriterion("trade_companyId between", value1, value2, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotBetween(String value1, String value2) {
            addCriterion("trade_companyId not between", value1, value2, "tradeCompanyid");
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

        public Criteria andPurchasenoIsNull() {
            addCriterion("PurchaseNO is null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNotNull() {
            addCriterion("PurchaseNO is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoEqualTo(String value) {
            addCriterion("PurchaseNO =", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotEqualTo(String value) {
            addCriterion("PurchaseNO <>", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThan(String value) {
            addCriterion("PurchaseNO >", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseNO >=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThan(String value) {
            addCriterion("PurchaseNO <", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseNO <=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLike(String value) {
            addCriterion("PurchaseNO like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotLike(String value) {
            addCriterion("PurchaseNO not like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIn(List<String> values) {
            addCriterion("PurchaseNO in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotIn(List<String> values) {
            addCriterion("PurchaseNO not in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoBetween(String value1, String value2) {
            addCriterion("PurchaseNO between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotBetween(String value1, String value2) {
            addCriterion("PurchaseNO not between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andCproductNoIsNull() {
            addCriterion("cproduct_no is null");
            return (Criteria) this;
        }

        public Criteria andCproductNoIsNotNull() {
            addCriterion("cproduct_no is not null");
            return (Criteria) this;
        }

        public Criteria andCproductNoEqualTo(String value) {
            addCriterion("cproduct_no =", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotEqualTo(String value) {
            addCriterion("cproduct_no <>", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoGreaterThan(String value) {
            addCriterion("cproduct_no >", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoGreaterThanOrEqualTo(String value) {
            addCriterion("cproduct_no >=", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLessThan(String value) {
            addCriterion("cproduct_no <", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLessThanOrEqualTo(String value) {
            addCriterion("cproduct_no <=", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLike(String value) {
            addCriterion("cproduct_no like", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotLike(String value) {
            addCriterion("cproduct_no not like", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoIn(List<String> values) {
            addCriterion("cproduct_no in", values, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotIn(List<String> values) {
            addCriterion("cproduct_no not in", values, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoBetween(String value1, String value2) {
            addCriterion("cproduct_no between", value1, value2, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotBetween(String value1, String value2) {
            addCriterion("cproduct_no not between", value1, value2, "cproductNo");
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

        public Criteria andGroupCustomerNoIsNull() {
            addCriterion("group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIsNotNull() {
            addCriterion("group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoEqualTo(String value) {
            addCriterion("group_customer_no =", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotEqualTo(String value) {
            addCriterion("group_customer_no <>", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThan(String value) {
            addCriterion("group_customer_no >", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("group_customer_no >=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThan(String value) {
            addCriterion("group_customer_no <", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("group_customer_no <=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLike(String value) {
            addCriterion("group_customer_no like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotLike(String value) {
            addCriterion("group_customer_no not like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIn(List<String> values) {
            addCriterion("group_customer_no in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotIn(List<String> values) {
            addCriterion("group_customer_no not in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoBetween(String value1, String value2) {
            addCriterion("group_customer_no between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotBetween(String value1, String value2) {
            addCriterion("group_customer_no not between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andPplNoIsNull() {
            addCriterion("ppl_no is null");
            return (Criteria) this;
        }

        public Criteria andPplNoIsNotNull() {
            addCriterion("ppl_no is not null");
            return (Criteria) this;
        }

        public Criteria andPplNoEqualTo(String value) {
            addCriterion("ppl_no =", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotEqualTo(String value) {
            addCriterion("ppl_no <>", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoGreaterThan(String value) {
            addCriterion("ppl_no >", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoGreaterThanOrEqualTo(String value) {
            addCriterion("ppl_no >=", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLessThan(String value) {
            addCriterion("ppl_no <", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLessThanOrEqualTo(String value) {
            addCriterion("ppl_no <=", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLike(String value) {
            addCriterion("ppl_no like", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotLike(String value) {
            addCriterion("ppl_no not like", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoIn(List<String> values) {
            addCriterion("ppl_no in", values, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotIn(List<String> values) {
            addCriterion("ppl_no not in", values, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoBetween(String value1, String value2) {
            addCriterion("ppl_no between", value1, value2, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotBetween(String value1, String value2) {
            addCriterion("ppl_no not between", value1, value2, "pplNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNull() {
            addCriterion("project_no is null");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNotNull() {
            addCriterion("project_no is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNoEqualTo(String value) {
            addCriterion("project_no =", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotEqualTo(String value) {
            addCriterion("project_no <>", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThan(String value) {
            addCriterion("project_no >", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThanOrEqualTo(String value) {
            addCriterion("project_no >=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThan(String value) {
            addCriterion("project_no <", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThanOrEqualTo(String value) {
            addCriterion("project_no <=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLike(String value) {
            addCriterion("project_no like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotLike(String value) {
            addCriterion("project_no not like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIn(List<String> values) {
            addCriterion("project_no in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotIn(List<String> values) {
            addCriterion("project_no not in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoBetween(String value1, String value2) {
            addCriterion("project_no between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotBetween(String value1, String value2) {
            addCriterion("project_no not between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIsNull() {
            addCriterion("shikomi_no is null");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIsNotNull() {
            addCriterion("shikomi_no is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiNoEqualTo(String value) {
            addCriterion("shikomi_no =", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotEqualTo(String value) {
            addCriterion("shikomi_no <>", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoGreaterThan(String value) {
            addCriterion("shikomi_no >", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoGreaterThanOrEqualTo(String value) {
            addCriterion("shikomi_no >=", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLessThan(String value) {
            addCriterion("shikomi_no <", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLessThanOrEqualTo(String value) {
            addCriterion("shikomi_no <=", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLike(String value) {
            addCriterion("shikomi_no like", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotLike(String value) {
            addCriterion("shikomi_no not like", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIn(List<String> values) {
            addCriterion("shikomi_no in", values, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotIn(List<String> values) {
            addCriterion("shikomi_no not in", values, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoBetween(String value1, String value2) {
            addCriterion("shikomi_no between", value1, value2, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotBetween(String value1, String value2) {
            addCriterion("shikomi_no not between", value1, value2, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNull() {
            addCriterion("sales_info_no is null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNotNull() {
            addCriterion("sales_info_no is not null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoEqualTo(String value) {
            addCriterion("sales_info_no =", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotEqualTo(String value) {
            addCriterion("sales_info_no <>", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThan(String value) {
            addCriterion("sales_info_no >", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThanOrEqualTo(String value) {
            addCriterion("sales_info_no >=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThan(String value) {
            addCriterion("sales_info_no <", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThanOrEqualTo(String value) {
            addCriterion("sales_info_no <=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLike(String value) {
            addCriterion("sales_info_no like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotLike(String value) {
            addCriterion("sales_info_no not like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIn(List<String> values) {
            addCriterion("sales_info_no in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotIn(List<String> values) {
            addCriterion("sales_info_no not in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoBetween(String value1, String value2) {
            addCriterion("sales_info_no between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotBetween(String value1, String value2) {
            addCriterion("sales_info_no not between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIsNull() {
            addCriterion("expected_delivery_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIsNotNull() {
            addCriterion("expected_delivery_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeEqualTo(Date value) {
            addCriterion("expected_delivery_time =", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotEqualTo(Date value) {
            addCriterion("expected_delivery_time <>", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeGreaterThan(Date value) {
            addCriterion("expected_delivery_time >", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expected_delivery_time >=", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeLessThan(Date value) {
            addCriterion("expected_delivery_time <", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeLessThanOrEqualTo(Date value) {
            addCriterion("expected_delivery_time <=", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIn(List<Date> values) {
            addCriterion("expected_delivery_time in", values, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotIn(List<Date> values) {
            addCriterion("expected_delivery_time not in", values, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeBetween(Date value1, Date value2) {
            addCriterion("expected_delivery_time between", value1, value2, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotBetween(Date value1, Date value2) {
            addCriterion("expected_delivery_time not between", value1, value2, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIsNull() {
            addCriterion("estimated_delivery_day is null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIsNotNull() {
            addCriterion("estimated_delivery_day is not null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayEqualTo(Date value) {
            addCriterion("estimated_delivery_day =", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotEqualTo(Date value) {
            addCriterion("estimated_delivery_day <>", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayGreaterThan(Date value) {
            addCriterion("estimated_delivery_day >", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayGreaterThanOrEqualTo(Date value) {
            addCriterion("estimated_delivery_day >=", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayLessThan(Date value) {
            addCriterion("estimated_delivery_day <", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayLessThanOrEqualTo(Date value) {
            addCriterion("estimated_delivery_day <=", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIn(List<Date> values) {
            addCriterion("estimated_delivery_day in", values, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotIn(List<Date> values) {
            addCriterion("estimated_delivery_day not in", values, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayBetween(Date value1, Date value2) {
            addCriterion("estimated_delivery_day between", value1, value2, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotBetween(Date value1, Date value2) {
            addCriterion("estimated_delivery_day not between", value1, value2, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceIsNull() {
            addCriterion("has_low_price is null");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceIsNotNull() {
            addCriterion("has_low_price is not null");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceEqualTo(Integer value) {
            addCriterion("has_low_price =", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceNotEqualTo(Integer value) {
            addCriterion("has_low_price <>", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceGreaterThan(Integer value) {
            addCriterion("has_low_price >", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("has_low_price >=", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceLessThan(Integer value) {
            addCriterion("has_low_price <", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceLessThanOrEqualTo(Integer value) {
            addCriterion("has_low_price <=", value, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceIn(List<Integer> values) {
            addCriterion("has_low_price in", values, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceNotIn(List<Integer> values) {
            addCriterion("has_low_price not in", values, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceBetween(Integer value1, Integer value2) {
            addCriterion("has_low_price between", value1, value2, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andHasLowPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("has_low_price not between", value1, value2, "hasLowPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeIsNull() {
            addCriterion("purchase_unit_code is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeIsNotNull() {
            addCriterion("purchase_unit_code is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeEqualTo(String value) {
            addCriterion("purchase_unit_code =", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotEqualTo(String value) {
            addCriterion("purchase_unit_code <>", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeGreaterThan(String value) {
            addCriterion("purchase_unit_code >", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_unit_code >=", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLessThan(String value) {
            addCriterion("purchase_unit_code <", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLessThanOrEqualTo(String value) {
            addCriterion("purchase_unit_code <=", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLike(String value) {
            addCriterion("purchase_unit_code like", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotLike(String value) {
            addCriterion("purchase_unit_code not like", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeIn(List<String> values) {
            addCriterion("purchase_unit_code in", values, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotIn(List<String> values) {
            addCriterion("purchase_unit_code not in", values, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeBetween(String value1, String value2) {
            addCriterion("purchase_unit_code between", value1, value2, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotBetween(String value1, String value2) {
            addCriterion("purchase_unit_code not between", value1, value2, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIsNull() {
            addCriterion("ext_order_no is null");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIsNotNull() {
            addCriterion("ext_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoEqualTo(String value) {
            addCriterion("ext_order_no =", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotEqualTo(String value) {
            addCriterion("ext_order_no <>", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoGreaterThan(String value) {
            addCriterion("ext_order_no >", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ext_order_no >=", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLessThan(String value) {
            addCriterion("ext_order_no <", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ext_order_no <=", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLike(String value) {
            addCriterion("ext_order_no like", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotLike(String value) {
            addCriterion("ext_order_no not like", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIn(List<String> values) {
            addCriterion("ext_order_no in", values, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotIn(List<String> values) {
            addCriterion("ext_order_no not in", values, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoBetween(String value1, String value2) {
            addCriterion("ext_order_no between", value1, value2, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotBetween(String value1, String value2) {
            addCriterion("ext_order_no not between", value1, value2, "extOrderNo");
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

        public Criteria andAllotStartTimeIsNull() {
            addCriterion("allot_start_time is null");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeIsNotNull() {
            addCriterion("allot_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeEqualTo(Date value) {
            addCriterion("allot_start_time =", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotEqualTo(Date value) {
            addCriterion("allot_start_time <>", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeGreaterThan(Date value) {
            addCriterion("allot_start_time >", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("allot_start_time >=", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeLessThan(Date value) {
            addCriterion("allot_start_time <", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("allot_start_time <=", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeIn(List<Date> values) {
            addCriterion("allot_start_time in", values, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotIn(List<Date> values) {
            addCriterion("allot_start_time not in", values, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeBetween(Date value1, Date value2) {
            addCriterion("allot_start_time between", value1, value2, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("allot_start_time not between", value1, value2, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeIsNull() {
            addCriterion("allot_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeIsNotNull() {
            addCriterion("allot_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeEqualTo(Date value) {
            addCriterion("allot_end_time =", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeNotEqualTo(Date value) {
            addCriterion("allot_end_time <>", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeGreaterThan(Date value) {
            addCriterion("allot_end_time >", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("allot_end_time >=", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeLessThan(Date value) {
            addCriterion("allot_end_time <", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("allot_end_time <=", value, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeIn(List<Date> values) {
            addCriterion("allot_end_time in", values, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeNotIn(List<Date> values) {
            addCriterion("allot_end_time not in", values, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeBetween(Date value1, Date value2) {
            addCriterion("allot_end_time between", value1, value2, "allotEndTime");
            return (Criteria) this;
        }

        public Criteria andAllotEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("allot_end_time not between", value1, value2, "allotEndTime");
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
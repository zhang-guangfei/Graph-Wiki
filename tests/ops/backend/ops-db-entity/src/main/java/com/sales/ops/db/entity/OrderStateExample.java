package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderStateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderStateExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNull() {
            addCriterion("item_no is null");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNotNull() {
            addCriterion("item_no is not null");
            return (Criteria) this;
        }

        public Criteria andItemNoEqualTo(Integer value) {
            addCriterion("item_no =", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotEqualTo(Integer value) {
            addCriterion("item_no <>", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThan(Integer value) {
            addCriterion("item_no >", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_no >=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThan(Integer value) {
            addCriterion("item_no <", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThanOrEqualTo(Integer value) {
            addCriterion("item_no <=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIn(List<Integer> values) {
            addCriterion("item_no in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotIn(List<Integer> values) {
            addCriterion("item_no not in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoBetween(Integer value1, Integer value2) {
            addCriterion("item_no between", value1, value2, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotBetween(Integer value1, Integer value2) {
            addCriterion("item_no not between", value1, value2, "itemNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoIsNull() {
            addCriterion("split_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitNoIsNotNull() {
            addCriterion("split_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitNoEqualTo(Integer value) {
            addCriterion("split_no =", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotEqualTo(Integer value) {
            addCriterion("split_no <>", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThan(Integer value) {
            addCriterion("split_no >", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("split_no >=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThan(Integer value) {
            addCriterion("split_no <", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThanOrEqualTo(Integer value) {
            addCriterion("split_no <=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoIn(List<Integer> values) {
            addCriterion("split_no in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotIn(List<Integer> values) {
            addCriterion("split_no not in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoBetween(Integer value1, Integer value2) {
            addCriterion("split_no between", value1, value2, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotBetween(Integer value1, Integer value2) {
            addCriterion("split_no not between", value1, value2, "splitNo");
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

        public Criteria andStateCodeIsNull() {
            addCriterion("state_code is null");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNotNull() {
            addCriterion("state_code is not null");
            return (Criteria) this;
        }

        public Criteria andStateCodeEqualTo(Short value) {
            addCriterion("state_code =", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotEqualTo(Short value) {
            addCriterion("state_code <>", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThan(Short value) {
            addCriterion("state_code >", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThanOrEqualTo(Short value) {
            addCriterion("state_code >=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThan(Short value) {
            addCriterion("state_code <", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThanOrEqualTo(Short value) {
            addCriterion("state_code <=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeIn(List<Short> values) {
            addCriterion("state_code in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotIn(List<Short> values) {
            addCriterion("state_code not in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeBetween(Short value1, Short value2) {
            addCriterion("state_code between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotBetween(Short value1, Short value2) {
            addCriterion("state_code not between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateDesIsNull() {
            addCriterion("state_des is null");
            return (Criteria) this;
        }

        public Criteria andStateDesIsNotNull() {
            addCriterion("state_des is not null");
            return (Criteria) this;
        }

        public Criteria andStateDesEqualTo(String value) {
            addCriterion("state_des =", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotEqualTo(String value) {
            addCriterion("state_des <>", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesGreaterThan(String value) {
            addCriterion("state_des >", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesGreaterThanOrEqualTo(String value) {
            addCriterion("state_des >=", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLessThan(String value) {
            addCriterion("state_des <", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLessThanOrEqualTo(String value) {
            addCriterion("state_des <=", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLike(String value) {
            addCriterion("state_des like", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotLike(String value) {
            addCriterion("state_des not like", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesIn(List<String> values) {
            addCriterion("state_des in", values, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotIn(List<String> values) {
            addCriterion("state_des not in", values, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesBetween(String value1, String value2) {
            addCriterion("state_des between", value1, value2, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotBetween(String value1, String value2) {
            addCriterion("state_des not between", value1, value2, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDateIsNull() {
            addCriterion("state_date is null");
            return (Criteria) this;
        }

        public Criteria andStateDateIsNotNull() {
            addCriterion("state_date is not null");
            return (Criteria) this;
        }

        public Criteria andStateDateEqualTo(Date value) {
            addCriterion("state_date =", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotEqualTo(Date value) {
            addCriterion("state_date <>", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateGreaterThan(Date value) {
            addCriterion("state_date >", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("state_date >=", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateLessThan(Date value) {
            addCriterion("state_date <", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateLessThanOrEqualTo(Date value) {
            addCriterion("state_date <=", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateIn(List<Date> values) {
            addCriterion("state_date in", values, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotIn(List<Date> values) {
            addCriterion("state_date not in", values, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateBetween(Date value1, Date value2) {
            addCriterion("state_date between", value1, value2, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotBetween(Date value1, Date value2) {
            addCriterion("state_date not between", value1, value2, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateTypeIsNull() {
            addCriterion("state_type is null");
            return (Criteria) this;
        }

        public Criteria andStateTypeIsNotNull() {
            addCriterion("state_type is not null");
            return (Criteria) this;
        }

        public Criteria andStateTypeEqualTo(Integer value) {
            addCriterion("state_type =", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotEqualTo(Integer value) {
            addCriterion("state_type <>", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeGreaterThan(Integer value) {
            addCriterion("state_type >", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("state_type >=", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeLessThan(Integer value) {
            addCriterion("state_type <", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeLessThanOrEqualTo(Integer value) {
            addCriterion("state_type <=", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeIn(List<Integer> values) {
            addCriterion("state_type in", values, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotIn(List<Integer> values) {
            addCriterion("state_type not in", values, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeBetween(Integer value1, Integer value2) {
            addCriterion("state_type between", value1, value2, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("state_type not between", value1, value2, "stateType");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateIsNull() {
            addCriterion("cust_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateIsNotNull() {
            addCriterion("cust_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateEqualTo(Date value) {
            addCriterion("cust_dlv_date =", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateNotEqualTo(Date value) {
            addCriterion("cust_dlv_date <>", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateGreaterThan(Date value) {
            addCriterion("cust_dlv_date >", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cust_dlv_date >=", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateLessThan(Date value) {
            addCriterion("cust_dlv_date <", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("cust_dlv_date <=", value, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateIn(List<Date> values) {
            addCriterion("cust_dlv_date in", values, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateNotIn(List<Date> values) {
            addCriterion("cust_dlv_date not in", values, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateBetween(Date value1, Date value2) {
            addCriterion("cust_dlv_date between", value1, value2, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("cust_dlv_date not between", value1, value2, "custDlvDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateIsNull() {
            addCriterion("cust_ship_date is null");
            return (Criteria) this;
        }

        public Criteria andCustShipDateIsNotNull() {
            addCriterion("cust_ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andCustShipDateEqualTo(Date value) {
            addCriterion("cust_ship_date =", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateNotEqualTo(Date value) {
            addCriterion("cust_ship_date <>", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateGreaterThan(Date value) {
            addCriterion("cust_ship_date >", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cust_ship_date >=", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateLessThan(Date value) {
            addCriterion("cust_ship_date <", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateLessThanOrEqualTo(Date value) {
            addCriterion("cust_ship_date <=", value, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateIn(List<Date> values) {
            addCriterion("cust_ship_date in", values, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateNotIn(List<Date> values) {
            addCriterion("cust_ship_date not in", values, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateBetween(Date value1, Date value2) {
            addCriterion("cust_ship_date between", value1, value2, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustShipDateNotBetween(Date value1, Date value2) {
            addCriterion("cust_ship_date not between", value1, value2, "custShipDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateIsNull() {
            addCriterion("cust_sign_date is null");
            return (Criteria) this;
        }

        public Criteria andCustSignDateIsNotNull() {
            addCriterion("cust_sign_date is not null");
            return (Criteria) this;
        }

        public Criteria andCustSignDateEqualTo(Date value) {
            addCriterion("cust_sign_date =", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateNotEqualTo(Date value) {
            addCriterion("cust_sign_date <>", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateGreaterThan(Date value) {
            addCriterion("cust_sign_date >", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cust_sign_date >=", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateLessThan(Date value) {
            addCriterion("cust_sign_date <", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateLessThanOrEqualTo(Date value) {
            addCriterion("cust_sign_date <=", value, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateIn(List<Date> values) {
            addCriterion("cust_sign_date in", values, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateNotIn(List<Date> values) {
            addCriterion("cust_sign_date not in", values, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateBetween(Date value1, Date value2) {
            addCriterion("cust_sign_date between", value1, value2, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andCustSignDateNotBetween(Date value1, Date value2) {
            addCriterion("cust_sign_date not between", value1, value2, "custSignDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateIsNull() {
            addCriterion("dept_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateIsNotNull() {
            addCriterion("dept_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateEqualTo(Date value) {
            addCriterion("dept_dlv_date =", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateNotEqualTo(Date value) {
            addCriterion("dept_dlv_date <>", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateGreaterThan(Date value) {
            addCriterion("dept_dlv_date >", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("dept_dlv_date >=", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateLessThan(Date value) {
            addCriterion("dept_dlv_date <", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("dept_dlv_date <=", value, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateIn(List<Date> values) {
            addCriterion("dept_dlv_date in", values, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateNotIn(List<Date> values) {
            addCriterion("dept_dlv_date not in", values, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateBetween(Date value1, Date value2) {
            addCriterion("dept_dlv_date between", value1, value2, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andDeptDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("dept_dlv_date not between", value1, value2, "deptDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateIsNull() {
            addCriterion("po_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateIsNotNull() {
            addCriterion("po_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateEqualTo(Date value) {
            addCriterion("po_dlv_date =", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateNotEqualTo(Date value) {
            addCriterion("po_dlv_date <>", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateGreaterThan(Date value) {
            addCriterion("po_dlv_date >", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("po_dlv_date >=", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateLessThan(Date value) {
            addCriterion("po_dlv_date <", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("po_dlv_date <=", value, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateIn(List<Date> values) {
            addCriterion("po_dlv_date in", values, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateNotIn(List<Date> values) {
            addCriterion("po_dlv_date not in", values, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateBetween(Date value1, Date value2) {
            addCriterion("po_dlv_date between", value1, value2, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("po_dlv_date not between", value1, value2, "poDlvDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateIsNull() {
            addCriterion("po_reply_date is null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateIsNotNull() {
            addCriterion("po_reply_date is not null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateEqualTo(Date value) {
            addCriterion("po_reply_date =", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateNotEqualTo(Date value) {
            addCriterion("po_reply_date <>", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateGreaterThan(Date value) {
            addCriterion("po_reply_date >", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("po_reply_date >=", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateLessThan(Date value) {
            addCriterion("po_reply_date <", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateLessThanOrEqualTo(Date value) {
            addCriterion("po_reply_date <=", value, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateIn(List<Date> values) {
            addCriterion("po_reply_date in", values, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateNotIn(List<Date> values) {
            addCriterion("po_reply_date not in", values, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateBetween(Date value1, Date value2) {
            addCriterion("po_reply_date between", value1, value2, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateNotBetween(Date value1, Date value2) {
            addCriterion("po_reply_date not between", value1, value2, "poReplyDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateIsNull() {
            addCriterion("po_ship_date is null");
            return (Criteria) this;
        }

        public Criteria andPoShipDateIsNotNull() {
            addCriterion("po_ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andPoShipDateEqualTo(Date value) {
            addCriterion("po_ship_date =", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateNotEqualTo(Date value) {
            addCriterion("po_ship_date <>", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateGreaterThan(Date value) {
            addCriterion("po_ship_date >", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("po_ship_date >=", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateLessThan(Date value) {
            addCriterion("po_ship_date <", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateLessThanOrEqualTo(Date value) {
            addCriterion("po_ship_date <=", value, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateIn(List<Date> values) {
            addCriterion("po_ship_date in", values, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateNotIn(List<Date> values) {
            addCriterion("po_ship_date not in", values, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateBetween(Date value1, Date value2) {
            addCriterion("po_ship_date between", value1, value2, "poShipDate");
            return (Criteria) this;
        }

        public Criteria andPoShipDateNotBetween(Date value1, Date value2) {
            addCriterion("po_ship_date not between", value1, value2, "poShipDate");
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

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_no =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_no <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_no >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_no >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_no <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_no <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_no like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_no not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_no in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_no not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_no between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_no not between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIsNull() {
            addCriterion("supplier_no is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIsNotNull() {
            addCriterion("supplier_no is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoEqualTo(String value) {
            addCriterion("supplier_no =", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotEqualTo(String value) {
            addCriterion("supplier_no <>", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoGreaterThan(String value) {
            addCriterion("supplier_no >", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_no >=", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLessThan(String value) {
            addCriterion("supplier_no <", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLessThanOrEqualTo(String value) {
            addCriterion("supplier_no <=", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLike(String value) {
            addCriterion("supplier_no like", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotLike(String value) {
            addCriterion("supplier_no not like", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIn(List<String> values) {
            addCriterion("supplier_no in", values, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotIn(List<String> values) {
            addCriterion("supplier_no not in", values, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoBetween(String value1, String value2) {
            addCriterion("supplier_no between", value1, value2, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotBetween(String value1, String value2) {
            addCriterion("supplier_no not between", value1, value2, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateIsNull() {
            addCriterion("act_arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateIsNotNull() {
            addCriterion("act_arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateEqualTo(Date value) {
            addCriterion("act_arrival_date =", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateNotEqualTo(Date value) {
            addCriterion("act_arrival_date <>", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateGreaterThan(Date value) {
            addCriterion("act_arrival_date >", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterion("act_arrival_date >=", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateLessThan(Date value) {
            addCriterion("act_arrival_date <", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateLessThanOrEqualTo(Date value) {
            addCriterion("act_arrival_date <=", value, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateIn(List<Date> values) {
            addCriterion("act_arrival_date in", values, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateNotIn(List<Date> values) {
            addCriterion("act_arrival_date not in", values, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateBetween(Date value1, Date value2) {
            addCriterion("act_arrival_date between", value1, value2, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andActArrivalDateNotBetween(Date value1, Date value2) {
            addCriterion("act_arrival_date not between", value1, value2, "actArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateIsNull() {
            addCriterion("es_arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateIsNotNull() {
            addCriterion("es_arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateEqualTo(Date value) {
            addCriterion("es_arrival_date =", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateNotEqualTo(Date value) {
            addCriterion("es_arrival_date <>", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateGreaterThan(Date value) {
            addCriterion("es_arrival_date >", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterion("es_arrival_date >=", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateLessThan(Date value) {
            addCriterion("es_arrival_date <", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateLessThanOrEqualTo(Date value) {
            addCriterion("es_arrival_date <=", value, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateIn(List<Date> values) {
            addCriterion("es_arrival_date in", values, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateNotIn(List<Date> values) {
            addCriterion("es_arrival_date not in", values, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateBetween(Date value1, Date value2) {
            addCriterion("es_arrival_date between", value1, value2, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArrivalDateNotBetween(Date value1, Date value2) {
            addCriterion("es_arrival_date not between", value1, value2, "esArrivalDate");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqIsNull() {
            addCriterion("es_arrive_date_req is null");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqIsNotNull() {
            addCriterion("es_arrive_date_req is not null");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqEqualTo(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req =", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqNotEqualTo(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req <>", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqGreaterThan(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req >", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req >=", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqLessThan(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req <", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("es_arrive_date_req <=", value, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqIn(List<Date> values) {
            addCriterionForJDBCDate("es_arrive_date_req in", values, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqNotIn(List<Date> values) {
            addCriterionForJDBCDate("es_arrive_date_req not in", values, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("es_arrive_date_req between", value1, value2, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andEsArriveDateReqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("es_arrive_date_req not between", value1, value2, "esArriveDateReq");
            return (Criteria) this;
        }

        public Criteria andProviderIsNull() {
            addCriterion("provider is null");
            return (Criteria) this;
        }

        public Criteria andProviderIsNotNull() {
            addCriterion("provider is not null");
            return (Criteria) this;
        }

        public Criteria andProviderEqualTo(String value) {
            addCriterion("provider =", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotEqualTo(String value) {
            addCriterion("provider <>", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThan(String value) {
            addCriterion("provider >", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThanOrEqualTo(String value) {
            addCriterion("provider >=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThan(String value) {
            addCriterion("provider <", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThanOrEqualTo(String value) {
            addCriterion("provider <=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLike(String value) {
            addCriterion("provider like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotLike(String value) {
            addCriterion("provider not like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderIn(List<String> values) {
            addCriterion("provider in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotIn(List<String> values) {
            addCriterion("provider not in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderBetween(String value1, String value2) {
            addCriterion("provider between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotBetween(String value1, String value2) {
            addCriterion("provider not between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNull() {
            addCriterion("ship_date is null");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNotNull() {
            addCriterion("ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipDateEqualTo(Date value) {
            addCriterion("ship_date =", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotEqualTo(Date value) {
            addCriterion("ship_date <>", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThan(Date value) {
            addCriterion("ship_date >", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_date >=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThan(Date value) {
            addCriterion("ship_date <", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_date <=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateIn(List<Date> values) {
            addCriterion("ship_date in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotIn(List<Date> values) {
            addCriterion("ship_date not in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateBetween(Date value1, Date value2) {
            addCriterion("ship_date between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_date not between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateIsNull() {
            addCriterion("es_ship_date is null");
            return (Criteria) this;
        }

        public Criteria andEsShipDateIsNotNull() {
            addCriterion("es_ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andEsShipDateEqualTo(Date value) {
            addCriterion("es_ship_date =", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateNotEqualTo(Date value) {
            addCriterion("es_ship_date <>", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateGreaterThan(Date value) {
            addCriterion("es_ship_date >", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("es_ship_date >=", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateLessThan(Date value) {
            addCriterion("es_ship_date <", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateLessThanOrEqualTo(Date value) {
            addCriterion("es_ship_date <=", value, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateIn(List<Date> values) {
            addCriterion("es_ship_date in", values, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateNotIn(List<Date> values) {
            addCriterion("es_ship_date not in", values, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateBetween(Date value1, Date value2) {
            addCriterion("es_ship_date between", value1, value2, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andEsShipDateNotBetween(Date value1, Date value2) {
            addCriterion("es_ship_date not between", value1, value2, "esShipDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateIsNull() {
            addCriterion("express_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateIsNotNull() {
            addCriterion("express_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateEqualTo(Date value) {
            addCriterion("express_dlv_date =", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateNotEqualTo(Date value) {
            addCriterion("express_dlv_date <>", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateGreaterThan(Date value) {
            addCriterion("express_dlv_date >", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("express_dlv_date >=", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateLessThan(Date value) {
            addCriterion("express_dlv_date <", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("express_dlv_date <=", value, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateIn(List<Date> values) {
            addCriterion("express_dlv_date in", values, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateNotIn(List<Date> values) {
            addCriterion("express_dlv_date not in", values, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateBetween(Date value1, Date value2) {
            addCriterion("express_dlv_date between", value1, value2, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andExpressDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("express_dlv_date not between", value1, value2, "expressDlvDate");
            return (Criteria) this;
        }

        public Criteria andDelayDayIsNull() {
            addCriterion("delay_day is null");
            return (Criteria) this;
        }

        public Criteria andDelayDayIsNotNull() {
            addCriterion("delay_day is not null");
            return (Criteria) this;
        }

        public Criteria andDelayDayEqualTo(Integer value) {
            addCriterion("delay_day =", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayNotEqualTo(Integer value) {
            addCriterion("delay_day <>", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayGreaterThan(Integer value) {
            addCriterion("delay_day >", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay_day >=", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayLessThan(Integer value) {
            addCriterion("delay_day <", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayLessThanOrEqualTo(Integer value) {
            addCriterion("delay_day <=", value, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayIn(List<Integer> values) {
            addCriterion("delay_day in", values, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayNotIn(List<Integer> values) {
            addCriterion("delay_day not in", values, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayBetween(Integer value1, Integer value2) {
            addCriterion("delay_day between", value1, value2, "delayDay");
            return (Criteria) this;
        }

        public Criteria andDelayDayNotBetween(Integer value1, Integer value2) {
            addCriterion("delay_day not between", value1, value2, "delayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayIsNull() {
            addCriterion("po_delay_day is null");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayIsNotNull() {
            addCriterion("po_delay_day is not null");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayEqualTo(Integer value) {
            addCriterion("po_delay_day =", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayNotEqualTo(Integer value) {
            addCriterion("po_delay_day <>", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayGreaterThan(Integer value) {
            addCriterion("po_delay_day >", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("po_delay_day >=", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayLessThan(Integer value) {
            addCriterion("po_delay_day <", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayLessThanOrEqualTo(Integer value) {
            addCriterion("po_delay_day <=", value, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayIn(List<Integer> values) {
            addCriterion("po_delay_day in", values, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayNotIn(List<Integer> values) {
            addCriterion("po_delay_day not in", values, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayBetween(Integer value1, Integer value2) {
            addCriterion("po_delay_day between", value1, value2, "poDelayDay");
            return (Criteria) this;
        }

        public Criteria andPoDelayDayNotBetween(Integer value1, Integer value2) {
            addCriterion("po_delay_day not between", value1, value2, "poDelayDay");
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

        public Criteria andCmodelNoIsNull() {
            addCriterion("cmodel_no is null");
            return (Criteria) this;
        }

        public Criteria andCmodelNoIsNotNull() {
            addCriterion("cmodel_no is not null");
            return (Criteria) this;
        }

        public Criteria andCmodelNoEqualTo(String value) {
            addCriterion("cmodel_no =", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotEqualTo(String value) {
            addCriterion("cmodel_no <>", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoGreaterThan(String value) {
            addCriterion("cmodel_no >", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoGreaterThanOrEqualTo(String value) {
            addCriterion("cmodel_no >=", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLessThan(String value) {
            addCriterion("cmodel_no <", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLessThanOrEqualTo(String value) {
            addCriterion("cmodel_no <=", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLike(String value) {
            addCriterion("cmodel_no like", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotLike(String value) {
            addCriterion("cmodel_no not like", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoIn(List<String> values) {
            addCriterion("cmodel_no in", values, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotIn(List<String> values) {
            addCriterion("cmodel_no not in", values, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoBetween(String value1, String value2) {
            addCriterion("cmodel_no between", value1, value2, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotBetween(String value1, String value2) {
            addCriterion("cmodel_no not between", value1, value2, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_Type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_Type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(String value) {
            addCriterion("trans_Type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(String value) {
            addCriterion("trans_Type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(String value) {
            addCriterion("trans_Type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_Type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(String value) {
            addCriterion("trans_Type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(String value) {
            addCriterion("trans_Type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLike(String value) {
            addCriterion("trans_Type like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotLike(String value) {
            addCriterion("trans_Type not like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<String> values) {
            addCriterion("trans_Type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<String> values) {
            addCriterion("trans_Type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(String value1, String value2) {
            addCriterion("trans_Type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(String value1, String value2) {
            addCriterion("trans_Type not between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoIsNull() {
            addCriterion("supplier_orderNo is null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoIsNotNull() {
            addCriterion("supplier_orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoEqualTo(String value) {
            addCriterion("supplier_orderNo =", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoNotEqualTo(String value) {
            addCriterion("supplier_orderNo <>", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoGreaterThan(String value) {
            addCriterion("supplier_orderNo >", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_orderNo >=", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoLessThan(String value) {
            addCriterion("supplier_orderNo <", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoLessThanOrEqualTo(String value) {
            addCriterion("supplier_orderNo <=", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoLike(String value) {
            addCriterion("supplier_orderNo like", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoNotLike(String value) {
            addCriterion("supplier_orderNo not like", value, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoIn(List<String> values) {
            addCriterion("supplier_orderNo in", values, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoNotIn(List<String> values) {
            addCriterion("supplier_orderNo not in", values, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoBetween(String value1, String value2) {
            addCriterion("supplier_orderNo between", value1, value2, "supplierOrderno");
            return (Criteria) this;
        }

        public Criteria andSupplierOrdernoNotBetween(String value1, String value2) {
            addCriterion("supplier_orderNo not between", value1, value2, "supplierOrderno");
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

        public Criteria andSupplierRcvtimeIsNull() {
            addCriterion("supplier_rcvtime is null");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeIsNotNull() {
            addCriterion("supplier_rcvtime is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeEqualTo(Date value) {
            addCriterion("supplier_rcvtime =", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeNotEqualTo(Date value) {
            addCriterion("supplier_rcvtime <>", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeGreaterThan(Date value) {
            addCriterion("supplier_rcvtime >", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("supplier_rcvtime >=", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeLessThan(Date value) {
            addCriterion("supplier_rcvtime <", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeLessThanOrEqualTo(Date value) {
            addCriterion("supplier_rcvtime <=", value, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeIn(List<Date> values) {
            addCriterion("supplier_rcvtime in", values, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeNotIn(List<Date> values) {
            addCriterion("supplier_rcvtime not in", values, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeBetween(Date value1, Date value2) {
            addCriterion("supplier_rcvtime between", value1, value2, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andSupplierRcvtimeNotBetween(Date value1, Date value2) {
            addCriterion("supplier_rcvtime not between", value1, value2, "supplierRcvtime");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoIsNull() {
            addCriterion("order_psn_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoIsNotNull() {
            addCriterion("order_psn_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoEqualTo(String value) {
            addCriterion("order_psn_no =", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoNotEqualTo(String value) {
            addCriterion("order_psn_no <>", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoGreaterThan(String value) {
            addCriterion("order_psn_no >", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_psn_no >=", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoLessThan(String value) {
            addCriterion("order_psn_no <", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoLessThanOrEqualTo(String value) {
            addCriterion("order_psn_no <=", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoLike(String value) {
            addCriterion("order_psn_no like", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoNotLike(String value) {
            addCriterion("order_psn_no not like", value, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoIn(List<String> values) {
            addCriterion("order_psn_no in", values, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoNotIn(List<String> values) {
            addCriterion("order_psn_no not in", values, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoBetween(String value1, String value2) {
            addCriterion("order_psn_no between", value1, value2, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderPsnNoNotBetween(String value1, String value2) {
            addCriterion("order_psn_no not between", value1, value2, "orderPsnNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoIsNull() {
            addCriterion("order_appover_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoIsNotNull() {
            addCriterion("order_appover_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoEqualTo(String value) {
            addCriterion("order_appover_no =", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoNotEqualTo(String value) {
            addCriterion("order_appover_no <>", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoGreaterThan(String value) {
            addCriterion("order_appover_no >", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_appover_no >=", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoLessThan(String value) {
            addCriterion("order_appover_no <", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoLessThanOrEqualTo(String value) {
            addCriterion("order_appover_no <=", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoLike(String value) {
            addCriterion("order_appover_no like", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoNotLike(String value) {
            addCriterion("order_appover_no not like", value, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoIn(List<String> values) {
            addCriterion("order_appover_no in", values, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoNotIn(List<String> values) {
            addCriterion("order_appover_no not in", values, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoBetween(String value1, String value2) {
            addCriterion("order_appover_no between", value1, value2, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andOrderAppoverNoNotBetween(String value1, String value2) {
            addCriterion("order_appover_no not between", value1, value2, "orderAppoverNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoIsNull() {
            addCriterion("po_invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoIsNotNull() {
            addCriterion("po_invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoEqualTo(String value) {
            addCriterion("po_invoice_no =", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoNotEqualTo(String value) {
            addCriterion("po_invoice_no <>", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoGreaterThan(String value) {
            addCriterion("po_invoice_no >", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("po_invoice_no >=", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoLessThan(String value) {
            addCriterion("po_invoice_no <", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("po_invoice_no <=", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoLike(String value) {
            addCriterion("po_invoice_no like", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoNotLike(String value) {
            addCriterion("po_invoice_no not like", value, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoIn(List<String> values) {
            addCriterion("po_invoice_no in", values, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoNotIn(List<String> values) {
            addCriterion("po_invoice_no not in", values, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoBetween(String value1, String value2) {
            addCriterion("po_invoice_no between", value1, value2, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("po_invoice_no not between", value1, value2, "poInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeIsNull() {
            addCriterion("po_exp_type is null");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeIsNotNull() {
            addCriterion("po_exp_type is not null");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeEqualTo(String value) {
            addCriterion("po_exp_type =", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeNotEqualTo(String value) {
            addCriterion("po_exp_type <>", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeGreaterThan(String value) {
            addCriterion("po_exp_type >", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeGreaterThanOrEqualTo(String value) {
            addCriterion("po_exp_type >=", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeLessThan(String value) {
            addCriterion("po_exp_type <", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeLessThanOrEqualTo(String value) {
            addCriterion("po_exp_type <=", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeLike(String value) {
            addCriterion("po_exp_type like", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeNotLike(String value) {
            addCriterion("po_exp_type not like", value, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeIn(List<String> values) {
            addCriterion("po_exp_type in", values, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeNotIn(List<String> values) {
            addCriterion("po_exp_type not in", values, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeBetween(String value1, String value2) {
            addCriterion("po_exp_type between", value1, value2, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoExpTypeNotBetween(String value1, String value2) {
            addCriterion("po_exp_type not between", value1, value2, "poExpType");
            return (Criteria) this;
        }

        public Criteria andPoHolonIsNull() {
            addCriterion("po_holon is null");
            return (Criteria) this;
        }

        public Criteria andPoHolonIsNotNull() {
            addCriterion("po_holon is not null");
            return (Criteria) this;
        }

        public Criteria andPoHolonEqualTo(String value) {
            addCriterion("po_holon =", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonNotEqualTo(String value) {
            addCriterion("po_holon <>", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonGreaterThan(String value) {
            addCriterion("po_holon >", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonGreaterThanOrEqualTo(String value) {
            addCriterion("po_holon >=", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonLessThan(String value) {
            addCriterion("po_holon <", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonLessThanOrEqualTo(String value) {
            addCriterion("po_holon <=", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonLike(String value) {
            addCriterion("po_holon like", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonNotLike(String value) {
            addCriterion("po_holon not like", value, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonIn(List<String> values) {
            addCriterion("po_holon in", values, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonNotIn(List<String> values) {
            addCriterion("po_holon not in", values, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonBetween(String value1, String value2) {
            addCriterion("po_holon between", value1, value2, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoHolonNotBetween(String value1, String value2) {
            addCriterion("po_holon not between", value1, value2, "poHolon");
            return (Criteria) this;
        }

        public Criteria andPoNoIsNull() {
            addCriterion("po_no is null");
            return (Criteria) this;
        }

        public Criteria andPoNoIsNotNull() {
            addCriterion("po_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoNoEqualTo(String value) {
            addCriterion("po_no =", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotEqualTo(String value) {
            addCriterion("po_no <>", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThan(String value) {
            addCriterion("po_no >", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThanOrEqualTo(String value) {
            addCriterion("po_no >=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThan(String value) {
            addCriterion("po_no <", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThanOrEqualTo(String value) {
            addCriterion("po_no <=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLike(String value) {
            addCriterion("po_no like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotLike(String value) {
            addCriterion("po_no not like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoIn(List<String> values) {
            addCriterion("po_no in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotIn(List<String> values) {
            addCriterion("po_no not in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoBetween(String value1, String value2) {
            addCriterion("po_no between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotBetween(String value1, String value2) {
            addCriterion("po_no not between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNull() {
            addCriterion("order_date is null");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNotNull() {
            addCriterion("order_date is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDateEqualTo(Date value) {
            addCriterion("order_date =", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotEqualTo(Date value) {
            addCriterion("order_date <>", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThan(Date value) {
            addCriterion("order_date >", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThanOrEqualTo(Date value) {
            addCriterion("order_date >=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThan(Date value) {
            addCriterion("order_date <", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThanOrEqualTo(Date value) {
            addCriterion("order_date <=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateIn(List<Date> values) {
            addCriterion("order_date in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotIn(List<Date> values) {
            addCriterion("order_date not in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateBetween(Date value1, Date value2) {
            addCriterion("order_date between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotBetween(Date value1, Date value2) {
            addCriterion("order_date not between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNull() {
            addCriterion("Receive_Date is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNotNull() {
            addCriterion("Receive_Date is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateEqualTo(Date value) {
            addCriterion("Receive_Date =", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotEqualTo(Date value) {
            addCriterion("Receive_Date <>", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThan(Date value) {
            addCriterion("Receive_Date >", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("Receive_Date >=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThan(Date value) {
            addCriterion("Receive_Date <", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThanOrEqualTo(Date value) {
            addCriterion("Receive_Date <=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIn(List<Date> values) {
            addCriterion("Receive_Date in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotIn(List<Date> values) {
            addCriterion("Receive_Date not in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateBetween(Date value1, Date value2) {
            addCriterion("Receive_Date between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotBetween(Date value1, Date value2) {
            addCriterion("Receive_Date not between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andPoDateIsNull() {
            addCriterion("po_date is null");
            return (Criteria) this;
        }

        public Criteria andPoDateIsNotNull() {
            addCriterion("po_date is not null");
            return (Criteria) this;
        }

        public Criteria andPoDateEqualTo(Date value) {
            addCriterion("po_date =", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateNotEqualTo(Date value) {
            addCriterion("po_date <>", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateGreaterThan(Date value) {
            addCriterion("po_date >", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateGreaterThanOrEqualTo(Date value) {
            addCriterion("po_date >=", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateLessThan(Date value) {
            addCriterion("po_date <", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateLessThanOrEqualTo(Date value) {
            addCriterion("po_date <=", value, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateIn(List<Date> values) {
            addCriterion("po_date in", values, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateNotIn(List<Date> values) {
            addCriterion("po_date not in", values, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateBetween(Date value1, Date value2) {
            addCriterion("po_date between", value1, value2, "poDate");
            return (Criteria) this;
        }

        public Criteria andPoDateNotBetween(Date value1, Date value2) {
            addCriterion("po_date not between", value1, value2, "poDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIsNull() {
            addCriterion("purchase_type is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIsNotNull() {
            addCriterion("purchase_type is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeEqualTo(String value) {
            addCriterion("purchase_type =", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotEqualTo(String value) {
            addCriterion("purchase_type <>", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThan(String value) {
            addCriterion("purchase_type >", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_type >=", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThan(String value) {
            addCriterion("purchase_type <", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThanOrEqualTo(String value) {
            addCriterion("purchase_type <=", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLike(String value) {
            addCriterion("purchase_type like", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotLike(String value) {
            addCriterion("purchase_type not like", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIn(List<String> values) {
            addCriterion("purchase_type in", values, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotIn(List<String> values) {
            addCriterion("purchase_type not in", values, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeBetween(String value1, String value2) {
            addCriterion("purchase_type between", value1, value2, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotBetween(String value1, String value2) {
            addCriterion("purchase_type not between", value1, value2, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andOptUserNoIsNull() {
            addCriterion("opt_user_no is null");
            return (Criteria) this;
        }

        public Criteria andOptUserNoIsNotNull() {
            addCriterion("opt_user_no is not null");
            return (Criteria) this;
        }

        public Criteria andOptUserNoEqualTo(String value) {
            addCriterion("opt_user_no =", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoNotEqualTo(String value) {
            addCriterion("opt_user_no <>", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoGreaterThan(String value) {
            addCriterion("opt_user_no >", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("opt_user_no >=", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoLessThan(String value) {
            addCriterion("opt_user_no <", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoLessThanOrEqualTo(String value) {
            addCriterion("opt_user_no <=", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoLike(String value) {
            addCriterion("opt_user_no like", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoNotLike(String value) {
            addCriterion("opt_user_no not like", value, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoIn(List<String> values) {
            addCriterion("opt_user_no in", values, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoNotIn(List<String> values) {
            addCriterion("opt_user_no not in", values, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoBetween(String value1, String value2) {
            addCriterion("opt_user_no between", value1, value2, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNoNotBetween(String value1, String value2) {
            addCriterion("opt_user_no not between", value1, value2, "optUserNo");
            return (Criteria) this;
        }

        public Criteria andOptUserNameIsNull() {
            addCriterion("opt_user_name is null");
            return (Criteria) this;
        }

        public Criteria andOptUserNameIsNotNull() {
            addCriterion("opt_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andOptUserNameEqualTo(String value) {
            addCriterion("opt_user_name =", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameNotEqualTo(String value) {
            addCriterion("opt_user_name <>", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameGreaterThan(String value) {
            addCriterion("opt_user_name >", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("opt_user_name >=", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameLessThan(String value) {
            addCriterion("opt_user_name <", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameLessThanOrEqualTo(String value) {
            addCriterion("opt_user_name <=", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameLike(String value) {
            addCriterion("opt_user_name like", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameNotLike(String value) {
            addCriterion("opt_user_name not like", value, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameIn(List<String> values) {
            addCriterion("opt_user_name in", values, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameNotIn(List<String> values) {
            addCriterion("opt_user_name not in", values, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameBetween(String value1, String value2) {
            addCriterion("opt_user_name between", value1, value2, "optUserName");
            return (Criteria) this;
        }

        public Criteria andOptUserNameNotBetween(String value1, String value2) {
            addCriterion("opt_user_name not between", value1, value2, "optUserName");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeIsNull() {
            addCriterion("po_rcv_time is null");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeIsNotNull() {
            addCriterion("po_rcv_time is not null");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeEqualTo(Date value) {
            addCriterion("po_rcv_time =", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeNotEqualTo(Date value) {
            addCriterion("po_rcv_time <>", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeGreaterThan(Date value) {
            addCriterion("po_rcv_time >", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("po_rcv_time >=", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeLessThan(Date value) {
            addCriterion("po_rcv_time <", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeLessThanOrEqualTo(Date value) {
            addCriterion("po_rcv_time <=", value, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeIn(List<Date> values) {
            addCriterion("po_rcv_time in", values, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeNotIn(List<Date> values) {
            addCriterion("po_rcv_time not in", values, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeBetween(Date value1, Date value2) {
            addCriterion("po_rcv_time between", value1, value2, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andPoRcvTimeNotBetween(Date value1, Date value2) {
            addCriterion("po_rcv_time not between", value1, value2, "poRcvTime");
            return (Criteria) this;
        }

        public Criteria andInqaIsNull() {
            addCriterion("inqA is null");
            return (Criteria) this;
        }

        public Criteria andInqaIsNotNull() {
            addCriterion("inqA is not null");
            return (Criteria) this;
        }

        public Criteria andInqaEqualTo(String value) {
            addCriterion("inqA =", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaNotEqualTo(String value) {
            addCriterion("inqA <>", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaGreaterThan(String value) {
            addCriterion("inqA >", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaGreaterThanOrEqualTo(String value) {
            addCriterion("inqA >=", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaLessThan(String value) {
            addCriterion("inqA <", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaLessThanOrEqualTo(String value) {
            addCriterion("inqA <=", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaLike(String value) {
            addCriterion("inqA like", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaNotLike(String value) {
            addCriterion("inqA not like", value, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaIn(List<String> values) {
            addCriterion("inqA in", values, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaNotIn(List<String> values) {
            addCriterion("inqA not in", values, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaBetween(String value1, String value2) {
            addCriterion("inqA between", value1, value2, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqaNotBetween(String value1, String value2) {
            addCriterion("inqA not between", value1, value2, "inqa");
            return (Criteria) this;
        }

        public Criteria andInqbIsNull() {
            addCriterion("inqB is null");
            return (Criteria) this;
        }

        public Criteria andInqbIsNotNull() {
            addCriterion("inqB is not null");
            return (Criteria) this;
        }

        public Criteria andInqbEqualTo(String value) {
            addCriterion("inqB =", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbNotEqualTo(String value) {
            addCriterion("inqB <>", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbGreaterThan(String value) {
            addCriterion("inqB >", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbGreaterThanOrEqualTo(String value) {
            addCriterion("inqB >=", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbLessThan(String value) {
            addCriterion("inqB <", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbLessThanOrEqualTo(String value) {
            addCriterion("inqB <=", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbLike(String value) {
            addCriterion("inqB like", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbNotLike(String value) {
            addCriterion("inqB not like", value, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbIn(List<String> values) {
            addCriterion("inqB in", values, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbNotIn(List<String> values) {
            addCriterion("inqB not in", values, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbBetween(String value1, String value2) {
            addCriterion("inqB between", value1, value2, "inqb");
            return (Criteria) this;
        }

        public Criteria andInqbNotBetween(String value1, String value2) {
            addCriterion("inqB not between", value1, value2, "inqb");
            return (Criteria) this;
        }

        public Criteria andRcvStatusIsNull() {
            addCriterion("rcv_status is null");
            return (Criteria) this;
        }

        public Criteria andRcvStatusIsNotNull() {
            addCriterion("rcv_status is not null");
            return (Criteria) this;
        }

        public Criteria andRcvStatusEqualTo(Integer value) {
            addCriterion("rcv_status =", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusNotEqualTo(Integer value) {
            addCriterion("rcv_status <>", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusGreaterThan(Integer value) {
            addCriterion("rcv_status >", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("rcv_status >=", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusLessThan(Integer value) {
            addCriterion("rcv_status <", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusLessThanOrEqualTo(Integer value) {
            addCriterion("rcv_status <=", value, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusIn(List<Integer> values) {
            addCriterion("rcv_status in", values, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusNotIn(List<Integer> values) {
            addCriterion("rcv_status not in", values, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusBetween(Integer value1, Integer value2) {
            addCriterion("rcv_status between", value1, value2, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andRcvStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("rcv_status not between", value1, value2, "rcvStatus");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaIsNull() {
            addCriterion("po_reply_dateA is null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaIsNotNull() {
            addCriterion("po_reply_dateA is not null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaEqualTo(Date value) {
            addCriterion("po_reply_dateA =", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaNotEqualTo(Date value) {
            addCriterion("po_reply_dateA <>", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaGreaterThan(Date value) {
            addCriterion("po_reply_dateA >", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaGreaterThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateA >=", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaLessThan(Date value) {
            addCriterion("po_reply_dateA <", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaLessThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateA <=", value, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaIn(List<Date> values) {
            addCriterion("po_reply_dateA in", values, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaNotIn(List<Date> values) {
            addCriterion("po_reply_dateA not in", values, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateA between", value1, value2, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDateaNotBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateA not between", value1, value2, "poReplyDatea");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebIsNull() {
            addCriterion("po_reply_dateB is null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebIsNotNull() {
            addCriterion("po_reply_dateB is not null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebEqualTo(Date value) {
            addCriterion("po_reply_dateB =", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebNotEqualTo(Date value) {
            addCriterion("po_reply_dateB <>", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebGreaterThan(Date value) {
            addCriterion("po_reply_dateB >", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebGreaterThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateB >=", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebLessThan(Date value) {
            addCriterion("po_reply_dateB <", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebLessThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateB <=", value, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebIn(List<Date> values) {
            addCriterion("po_reply_dateB in", values, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebNotIn(List<Date> values) {
            addCriterion("po_reply_dateB not in", values, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateB between", value1, value2, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatebNotBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateB not between", value1, value2, "poReplyDateb");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecIsNull() {
            addCriterion("po_reply_dateC is null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecIsNotNull() {
            addCriterion("po_reply_dateC is not null");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecEqualTo(Date value) {
            addCriterion("po_reply_dateC =", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecNotEqualTo(Date value) {
            addCriterion("po_reply_dateC <>", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecGreaterThan(Date value) {
            addCriterion("po_reply_dateC >", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecGreaterThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateC >=", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecLessThan(Date value) {
            addCriterion("po_reply_dateC <", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecLessThanOrEqualTo(Date value) {
            addCriterion("po_reply_dateC <=", value, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecIn(List<Date> values) {
            addCriterion("po_reply_dateC in", values, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecNotIn(List<Date> values) {
            addCriterion("po_reply_dateC not in", values, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateC between", value1, value2, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoReplyDatecNotBetween(Date value1, Date value2) {
            addCriterion("po_reply_dateC not between", value1, value2, "poReplyDatec");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeIsNull() {
            addCriterion("po_trans_type is null");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeIsNotNull() {
            addCriterion("po_trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeEqualTo(String value) {
            addCriterion("po_trans_type =", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeNotEqualTo(String value) {
            addCriterion("po_trans_type <>", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeGreaterThan(String value) {
            addCriterion("po_trans_type >", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("po_trans_type >=", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeLessThan(String value) {
            addCriterion("po_trans_type <", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeLessThanOrEqualTo(String value) {
            addCriterion("po_trans_type <=", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeLike(String value) {
            addCriterion("po_trans_type like", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeNotLike(String value) {
            addCriterion("po_trans_type not like", value, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeIn(List<String> values) {
            addCriterion("po_trans_type in", values, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeNotIn(List<String> values) {
            addCriterion("po_trans_type not in", values, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeBetween(String value1, String value2) {
            addCriterion("po_trans_type between", value1, value2, "poTransType");
            return (Criteria) this;
        }

        public Criteria andPoTransTypeNotBetween(String value1, String value2) {
            addCriterion("po_trans_type not between", value1, value2, "poTransType");
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

        public Criteria andPortArrivedateIsNull() {
            addCriterion("port_arrivedate is null");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateIsNotNull() {
            addCriterion("port_arrivedate is not null");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateEqualTo(Date value) {
            addCriterion("port_arrivedate =", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotEqualTo(Date value) {
            addCriterion("port_arrivedate <>", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateGreaterThan(Date value) {
            addCriterion("port_arrivedate >", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateGreaterThanOrEqualTo(Date value) {
            addCriterion("port_arrivedate >=", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateLessThan(Date value) {
            addCriterion("port_arrivedate <", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateLessThanOrEqualTo(Date value) {
            addCriterion("port_arrivedate <=", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateIn(List<Date> values) {
            addCriterion("port_arrivedate in", values, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotIn(List<Date> values) {
            addCriterion("port_arrivedate not in", values, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateBetween(Date value1, Date value2) {
            addCriterion("port_arrivedate between", value1, value2, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotBetween(Date value1, Date value2) {
            addCriterion("port_arrivedate not between", value1, value2, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIsNull() {
            addCriterion("customs_date is null");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIsNotNull() {
            addCriterion("customs_date is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsDateEqualTo(Date value) {
            addCriterion("customs_date =", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotEqualTo(Date value) {
            addCriterion("customs_date <>", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateGreaterThan(Date value) {
            addCriterion("customs_date >", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateGreaterThanOrEqualTo(Date value) {
            addCriterion("customs_date >=", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateLessThan(Date value) {
            addCriterion("customs_date <", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateLessThanOrEqualTo(Date value) {
            addCriterion("customs_date <=", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIn(List<Date> values) {
            addCriterion("customs_date in", values, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotIn(List<Date> values) {
            addCriterion("customs_date not in", values, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateBetween(Date value1, Date value2) {
            addCriterion("customs_date between", value1, value2, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotBetween(Date value1, Date value2) {
            addCriterion("customs_date not between", value1, value2, "customsDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateIsNull() {
            addCriterion("begin_produce_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateIsNotNull() {
            addCriterion("begin_produce_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateEqualTo(Date value) {
            addCriterion("begin_produce_date =", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateNotEqualTo(Date value) {
            addCriterion("begin_produce_date <>", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateGreaterThan(Date value) {
            addCriterion("begin_produce_date >", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateGreaterThanOrEqualTo(Date value) {
            addCriterion("begin_produce_date >=", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateLessThan(Date value) {
            addCriterion("begin_produce_date <", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateLessThanOrEqualTo(Date value) {
            addCriterion("begin_produce_date <=", value, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateIn(List<Date> values) {
            addCriterion("begin_produce_date in", values, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateNotIn(List<Date> values) {
            addCriterion("begin_produce_date not in", values, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateBetween(Date value1, Date value2) {
            addCriterion("begin_produce_date between", value1, value2, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginProduceDateNotBetween(Date value1, Date value2) {
            addCriterion("begin_produce_date not between", value1, value2, "beginProduceDate");
            return (Criteria) this;
        }

        public Criteria andPoPriceIsNull() {
            addCriterion("po_price is null");
            return (Criteria) this;
        }

        public Criteria andPoPriceIsNotNull() {
            addCriterion("po_price is not null");
            return (Criteria) this;
        }

        public Criteria andPoPriceEqualTo(BigDecimal value) {
            addCriterion("po_price =", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceNotEqualTo(BigDecimal value) {
            addCriterion("po_price <>", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceGreaterThan(BigDecimal value) {
            addCriterion("po_price >", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("po_price >=", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceLessThan(BigDecimal value) {
            addCriterion("po_price <", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("po_price <=", value, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceIn(List<BigDecimal> values) {
            addCriterion("po_price in", values, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceNotIn(List<BigDecimal> values) {
            addCriterion("po_price not in", values, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("po_price between", value1, value2, "poPrice");
            return (Criteria) this;
        }

        public Criteria andPoPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("po_price not between", value1, value2, "poPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptIsNull() {
            addCriterion("order_psn_dept is null");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptIsNotNull() {
            addCriterion("order_psn_dept is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptEqualTo(String value) {
            addCriterion("order_psn_dept =", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptNotEqualTo(String value) {
            addCriterion("order_psn_dept <>", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptGreaterThan(String value) {
            addCriterion("order_psn_dept >", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptGreaterThanOrEqualTo(String value) {
            addCriterion("order_psn_dept >=", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptLessThan(String value) {
            addCriterion("order_psn_dept <", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptLessThanOrEqualTo(String value) {
            addCriterion("order_psn_dept <=", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptLike(String value) {
            addCriterion("order_psn_dept like", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptNotLike(String value) {
            addCriterion("order_psn_dept not like", value, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptIn(List<String> values) {
            addCriterion("order_psn_dept in", values, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptNotIn(List<String> values) {
            addCriterion("order_psn_dept not in", values, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptBetween(String value1, String value2) {
            addCriterion("order_psn_dept between", value1, value2, "orderPsnDept");
            return (Criteria) this;
        }

        public Criteria andOrderPsnDeptNotBetween(String value1, String value2) {
            addCriterion("order_psn_dept not between", value1, value2, "orderPsnDept");
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

        public Criteria andPoImptimeIsNull() {
            addCriterion("po_imptime is null");
            return (Criteria) this;
        }

        public Criteria andPoImptimeIsNotNull() {
            addCriterion("po_imptime is not null");
            return (Criteria) this;
        }

        public Criteria andPoImptimeEqualTo(Date value) {
            addCriterion("po_imptime =", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeNotEqualTo(Date value) {
            addCriterion("po_imptime <>", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeGreaterThan(Date value) {
            addCriterion("po_imptime >", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("po_imptime >=", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeLessThan(Date value) {
            addCriterion("po_imptime <", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeLessThanOrEqualTo(Date value) {
            addCriterion("po_imptime <=", value, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeIn(List<Date> values) {
            addCriterion("po_imptime in", values, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeNotIn(List<Date> values) {
            addCriterion("po_imptime not in", values, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeBetween(Date value1, Date value2) {
            addCriterion("po_imptime between", value1, value2, "poImptime");
            return (Criteria) this;
        }

        public Criteria andPoImptimeNotBetween(Date value1, Date value2) {
            addCriterion("po_imptime not between", value1, value2, "poImptime");
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
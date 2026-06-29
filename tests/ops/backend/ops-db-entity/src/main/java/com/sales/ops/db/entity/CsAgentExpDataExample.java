package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsAgentExpDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CsAgentExpDataExample() {
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

        public Criteria andOrderFnoIsNull() {
            addCriterion("order_fno is null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIsNotNull() {
            addCriterion("order_fno is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoEqualTo(String value) {
            addCriterion("order_fno =", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotEqualTo(String value) {
            addCriterion("order_fno <>", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThan(String value) {
            addCriterion("order_fno >", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("order_fno >=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThan(String value) {
            addCriterion("order_fno <", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThanOrEqualTo(String value) {
            addCriterion("order_fno <=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLike(String value) {
            addCriterion("order_fno like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotLike(String value) {
            addCriterion("order_fno not like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIn(List<String> values) {
            addCriterion("order_fno in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotIn(List<String> values) {
            addCriterion("order_fno not in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoBetween(String value1, String value2) {
            addCriterion("order_fno between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotBetween(String value1, String value2) {
            addCriterion("order_fno not between", value1, value2, "orderFno");
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

        public Criteria andAgentNoIsNull() {
            addCriterion("agent_No is null");
            return (Criteria) this;
        }

        public Criteria andAgentNoIsNotNull() {
            addCriterion("agent_No is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNoEqualTo(String value) {
            addCriterion("agent_No =", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoNotEqualTo(String value) {
            addCriterion("agent_No <>", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoGreaterThan(String value) {
            addCriterion("agent_No >", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoGreaterThanOrEqualTo(String value) {
            addCriterion("agent_No >=", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoLessThan(String value) {
            addCriterion("agent_No <", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoLessThanOrEqualTo(String value) {
            addCriterion("agent_No <=", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoLike(String value) {
            addCriterion("agent_No like", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoNotLike(String value) {
            addCriterion("agent_No not like", value, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoIn(List<String> values) {
            addCriterion("agent_No in", values, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoNotIn(List<String> values) {
            addCriterion("agent_No not in", values, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoBetween(String value1, String value2) {
            addCriterion("agent_No between", value1, value2, "agentNo");
            return (Criteria) this;
        }

        public Criteria andAgentNoNotBetween(String value1, String value2) {
            addCriterion("agent_No not between", value1, value2, "agentNo");
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

        public Criteria andOrderUserIsNull() {
            addCriterion("order_user is null");
            return (Criteria) this;
        }

        public Criteria andOrderUserIsNotNull() {
            addCriterion("order_user is not null");
            return (Criteria) this;
        }

        public Criteria andOrderUserEqualTo(String value) {
            addCriterion("order_user =", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserNotEqualTo(String value) {
            addCriterion("order_user <>", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserGreaterThan(String value) {
            addCriterion("order_user >", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserGreaterThanOrEqualTo(String value) {
            addCriterion("order_user >=", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserLessThan(String value) {
            addCriterion("order_user <", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserLessThanOrEqualTo(String value) {
            addCriterion("order_user <=", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserLike(String value) {
            addCriterion("order_user like", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserNotLike(String value) {
            addCriterion("order_user not like", value, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserIn(List<String> values) {
            addCriterion("order_user in", values, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserNotIn(List<String> values) {
            addCriterion("order_user not in", values, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserBetween(String value1, String value2) {
            addCriterion("order_user between", value1, value2, "orderUser");
            return (Criteria) this;
        }

        public Criteria andOrderUserNotBetween(String value1, String value2) {
            addCriterion("order_user not between", value1, value2, "orderUser");
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

        public Criteria andOptCodeIsNull() {
            addCriterion("opt_code is null");
            return (Criteria) this;
        }

        public Criteria andOptCodeIsNotNull() {
            addCriterion("opt_code is not null");
            return (Criteria) this;
        }

        public Criteria andOptCodeEqualTo(Short value) {
            addCriterion("opt_code =", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotEqualTo(Short value) {
            addCriterion("opt_code <>", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThan(Short value) {
            addCriterion("opt_code >", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThanOrEqualTo(Short value) {
            addCriterion("opt_code >=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThan(Short value) {
            addCriterion("opt_code <", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThanOrEqualTo(Short value) {
            addCriterion("opt_code <=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeIn(List<Short> values) {
            addCriterion("opt_code in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotIn(List<Short> values) {
            addCriterion("opt_code not in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeBetween(Short value1, Short value2) {
            addCriterion("opt_code between", value1, value2, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotBetween(Short value1, Short value2) {
            addCriterion("opt_code not between", value1, value2, "optCode");
            return (Criteria) this;
        }

        public Criteria andDnNoIsNull() {
            addCriterion("dn_no is null");
            return (Criteria) this;
        }

        public Criteria andDnNoIsNotNull() {
            addCriterion("dn_no is not null");
            return (Criteria) this;
        }

        public Criteria andDnNoEqualTo(String value) {
            addCriterion("dn_no =", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoNotEqualTo(String value) {
            addCriterion("dn_no <>", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoGreaterThan(String value) {
            addCriterion("dn_no >", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoGreaterThanOrEqualTo(String value) {
            addCriterion("dn_no >=", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoLessThan(String value) {
            addCriterion("dn_no <", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoLessThanOrEqualTo(String value) {
            addCriterion("dn_no <=", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoLike(String value) {
            addCriterion("dn_no like", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoNotLike(String value) {
            addCriterion("dn_no not like", value, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoIn(List<String> values) {
            addCriterion("dn_no in", values, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoNotIn(List<String> values) {
            addCriterion("dn_no not in", values, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoBetween(String value1, String value2) {
            addCriterion("dn_no between", value1, value2, "dnNo");
            return (Criteria) this;
        }

        public Criteria andDnNoNotBetween(String value1, String value2) {
            addCriterion("dn_no not between", value1, value2, "dnNo");
            return (Criteria) this;
        }

        public Criteria andExpUserIsNull() {
            addCriterion("exp_user is null");
            return (Criteria) this;
        }

        public Criteria andExpUserIsNotNull() {
            addCriterion("exp_user is not null");
            return (Criteria) this;
        }

        public Criteria andExpUserEqualTo(String value) {
            addCriterion("exp_user =", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserNotEqualTo(String value) {
            addCriterion("exp_user <>", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserGreaterThan(String value) {
            addCriterion("exp_user >", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserGreaterThanOrEqualTo(String value) {
            addCriterion("exp_user >=", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserLessThan(String value) {
            addCriterion("exp_user <", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserLessThanOrEqualTo(String value) {
            addCriterion("exp_user <=", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserLike(String value) {
            addCriterion("exp_user like", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserNotLike(String value) {
            addCriterion("exp_user not like", value, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserIn(List<String> values) {
            addCriterion("exp_user in", values, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserNotIn(List<String> values) {
            addCriterion("exp_user not in", values, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserBetween(String value1, String value2) {
            addCriterion("exp_user between", value1, value2, "expUser");
            return (Criteria) this;
        }

        public Criteria andExpUserNotBetween(String value1, String value2) {
            addCriterion("exp_user not between", value1, value2, "expUser");
            return (Criteria) this;
        }

        public Criteria andExportDateIsNull() {
            addCriterion("export_date is null");
            return (Criteria) this;
        }

        public Criteria andExportDateIsNotNull() {
            addCriterion("export_date is not null");
            return (Criteria) this;
        }

        public Criteria andExportDateEqualTo(Date value) {
            addCriterion("export_date =", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateNotEqualTo(Date value) {
            addCriterion("export_date <>", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateGreaterThan(Date value) {
            addCriterion("export_date >", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateGreaterThanOrEqualTo(Date value) {
            addCriterion("export_date >=", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateLessThan(Date value) {
            addCriterion("export_date <", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateLessThanOrEqualTo(Date value) {
            addCriterion("export_date <=", value, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateIn(List<Date> values) {
            addCriterion("export_date in", values, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateNotIn(List<Date> values) {
            addCriterion("export_date not in", values, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateBetween(Date value1, Date value2) {
            addCriterion("export_date between", value1, value2, "exportDate");
            return (Criteria) this;
        }

        public Criteria andExportDateNotBetween(Date value1, Date value2) {
            addCriterion("export_date not between", value1, value2, "exportDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNull() {
            addCriterion("finish_date is null");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNotNull() {
            addCriterion("finish_date is not null");
            return (Criteria) this;
        }

        public Criteria andFinishDateEqualTo(Date value) {
            addCriterion("finish_date =", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotEqualTo(Date value) {
            addCriterion("finish_date <>", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThan(Date value) {
            addCriterion("finish_date >", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_date >=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThan(Date value) {
            addCriterion("finish_date <", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThanOrEqualTo(Date value) {
            addCriterion("finish_date <=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIn(List<Date> values) {
            addCriterion("finish_date in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotIn(List<Date> values) {
            addCriterion("finish_date not in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateBetween(Date value1, Date value2) {
            addCriterion("finish_date between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotBetween(Date value1, Date value2) {
            addCriterion("finish_date not between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNull() {
            addCriterion("cancel_date is null");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNotNull() {
            addCriterion("cancel_date is not null");
            return (Criteria) this;
        }

        public Criteria andCancelDateEqualTo(Date value) {
            addCriterion("cancel_date =", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotEqualTo(Date value) {
            addCriterion("cancel_date <>", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThan(Date value) {
            addCriterion("cancel_date >", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cancel_date >=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThan(Date value) {
            addCriterion("cancel_date <", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThanOrEqualTo(Date value) {
            addCriterion("cancel_date <=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIn(List<Date> values) {
            addCriterion("cancel_date in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotIn(List<Date> values) {
            addCriterion("cancel_date not in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateBetween(Date value1, Date value2) {
            addCriterion("cancel_date between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotBetween(Date value1, Date value2) {
            addCriterion("cancel_date not between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelUserIsNull() {
            addCriterion("cancel_user is null");
            return (Criteria) this;
        }

        public Criteria andCancelUserIsNotNull() {
            addCriterion("cancel_user is not null");
            return (Criteria) this;
        }

        public Criteria andCancelUserEqualTo(String value) {
            addCriterion("cancel_user =", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserNotEqualTo(String value) {
            addCriterion("cancel_user <>", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserGreaterThan(String value) {
            addCriterion("cancel_user >", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_user >=", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserLessThan(String value) {
            addCriterion("cancel_user <", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserLessThanOrEqualTo(String value) {
            addCriterion("cancel_user <=", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserLike(String value) {
            addCriterion("cancel_user like", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserNotLike(String value) {
            addCriterion("cancel_user not like", value, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserIn(List<String> values) {
            addCriterion("cancel_user in", values, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserNotIn(List<String> values) {
            addCriterion("cancel_user not in", values, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserBetween(String value1, String value2) {
            addCriterion("cancel_user between", value1, value2, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andCancelUserNotBetween(String value1, String value2) {
            addCriterion("cancel_user not between", value1, value2, "cancelUser");
            return (Criteria) this;
        }

        public Criteria andPrintTimesIsNull() {
            addCriterion("print_times is null");
            return (Criteria) this;
        }

        public Criteria andPrintTimesIsNotNull() {
            addCriterion("print_times is not null");
            return (Criteria) this;
        }

        public Criteria andPrintTimesEqualTo(Integer value) {
            addCriterion("print_times =", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesNotEqualTo(Integer value) {
            addCriterion("print_times <>", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesGreaterThan(Integer value) {
            addCriterion("print_times >", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("print_times >=", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesLessThan(Integer value) {
            addCriterion("print_times <", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesLessThanOrEqualTo(Integer value) {
            addCriterion("print_times <=", value, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesIn(List<Integer> values) {
            addCriterion("print_times in", values, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesNotIn(List<Integer> values) {
            addCriterion("print_times not in", values, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesBetween(Integer value1, Integer value2) {
            addCriterion("print_times between", value1, value2, "printTimes");
            return (Criteria) this;
        }

        public Criteria andPrintTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("print_times not between", value1, value2, "printTimes");
            return (Criteria) this;
        }

        public Criteria andAddressNoIsNull() {
            addCriterion("address_no is null");
            return (Criteria) this;
        }

        public Criteria andAddressNoIsNotNull() {
            addCriterion("address_no is not null");
            return (Criteria) this;
        }

        public Criteria andAddressNoEqualTo(String value) {
            addCriterion("address_no =", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotEqualTo(String value) {
            addCriterion("address_no <>", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThan(String value) {
            addCriterion("address_no >", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThanOrEqualTo(String value) {
            addCriterion("address_no >=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThan(String value) {
            addCriterion("address_no <", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThanOrEqualTo(String value) {
            addCriterion("address_no <=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLike(String value) {
            addCriterion("address_no like", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotLike(String value) {
            addCriterion("address_no not like", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoIn(List<String> values) {
            addCriterion("address_no in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotIn(List<String> values) {
            addCriterion("address_no not in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoBetween(String value1, String value2) {
            addCriterion("address_no between", value1, value2, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotBetween(String value1, String value2) {
            addCriterion("address_no not between", value1, value2, "addressNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoIsNull() {
            addCriterion("stock_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoIsNotNull() {
            addCriterion("stock_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoEqualTo(String value) {
            addCriterion("stock_customer_no =", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoNotEqualTo(String value) {
            addCriterion("stock_customer_no <>", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoGreaterThan(String value) {
            addCriterion("stock_customer_no >", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("stock_customer_no >=", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoLessThan(String value) {
            addCriterion("stock_customer_no <", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("stock_customer_no <=", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoLike(String value) {
            addCriterion("stock_customer_no like", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoNotLike(String value) {
            addCriterion("stock_customer_no not like", value, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoIn(List<String> values) {
            addCriterion("stock_customer_no in", values, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoNotIn(List<String> values) {
            addCriterion("stock_customer_no not in", values, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoBetween(String value1, String value2) {
            addCriterion("stock_customer_no between", value1, value2, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andStockCustomerNoNotBetween(String value1, String value2) {
            addCriterion("stock_customer_no not between", value1, value2, "stockCustomerNo");
            return (Criteria) this;
        }

        public Criteria andDlvType1IsNull() {
            addCriterion("dlv_type1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvType1IsNotNull() {
            addCriterion("dlv_type1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvType1EqualTo(String value) {
            addCriterion("dlv_type1 =", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1NotEqualTo(String value) {
            addCriterion("dlv_type1 <>", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1GreaterThan(String value) {
            addCriterion("dlv_type1 >", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1GreaterThanOrEqualTo(String value) {
            addCriterion("dlv_type1 >=", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1LessThan(String value) {
            addCriterion("dlv_type1 <", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1LessThanOrEqualTo(String value) {
            addCriterion("dlv_type1 <=", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1Like(String value) {
            addCriterion("dlv_type1 like", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1NotLike(String value) {
            addCriterion("dlv_type1 not like", value, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1In(List<String> values) {
            addCriterion("dlv_type1 in", values, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1NotIn(List<String> values) {
            addCriterion("dlv_type1 not in", values, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1Between(String value1, String value2) {
            addCriterion("dlv_type1 between", value1, value2, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType1NotBetween(String value1, String value2) {
            addCriterion("dlv_type1 not between", value1, value2, "dlvType1");
            return (Criteria) this;
        }

        public Criteria andDlvType2IsNull() {
            addCriterion("dlv_type2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvType2IsNotNull() {
            addCriterion("dlv_type2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvType2EqualTo(String value) {
            addCriterion("dlv_type2 =", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2NotEqualTo(String value) {
            addCriterion("dlv_type2 <>", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2GreaterThan(String value) {
            addCriterion("dlv_type2 >", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2GreaterThanOrEqualTo(String value) {
            addCriterion("dlv_type2 >=", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2LessThan(String value) {
            addCriterion("dlv_type2 <", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2LessThanOrEqualTo(String value) {
            addCriterion("dlv_type2 <=", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2Like(String value) {
            addCriterion("dlv_type2 like", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2NotLike(String value) {
            addCriterion("dlv_type2 not like", value, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2In(List<String> values) {
            addCriterion("dlv_type2 in", values, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2NotIn(List<String> values) {
            addCriterion("dlv_type2 not in", values, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2Between(String value1, String value2) {
            addCriterion("dlv_type2 between", value1, value2, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andDlvType2NotBetween(String value1, String value2) {
            addCriterion("dlv_type2 not between", value1, value2, "dlvType2");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateIsNull() {
            addCriterion("spec_dlvdate is null");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateIsNotNull() {
            addCriterion("spec_dlvdate is not null");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateEqualTo(Date value) {
            addCriterion("spec_dlvdate =", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateNotEqualTo(Date value) {
            addCriterion("spec_dlvdate <>", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateGreaterThan(Date value) {
            addCriterion("spec_dlvdate >", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("spec_dlvdate >=", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateLessThan(Date value) {
            addCriterion("spec_dlvdate <", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("spec_dlvdate <=", value, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateIn(List<Date> values) {
            addCriterion("spec_dlvdate in", values, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateNotIn(List<Date> values) {
            addCriterion("spec_dlvdate not in", values, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateBetween(Date value1, Date value2) {
            addCriterion("spec_dlvdate between", value1, value2, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andSpecDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("spec_dlvdate not between", value1, value2, "specDlvdate");
            return (Criteria) this;
        }

        public Criteria andPackTypeIsNull() {
            addCriterion("pack_type is null");
            return (Criteria) this;
        }

        public Criteria andPackTypeIsNotNull() {
            addCriterion("pack_type is not null");
            return (Criteria) this;
        }

        public Criteria andPackTypeEqualTo(String value) {
            addCriterion("pack_type =", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeNotEqualTo(String value) {
            addCriterion("pack_type <>", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeGreaterThan(String value) {
            addCriterion("pack_type >", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pack_type >=", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeLessThan(String value) {
            addCriterion("pack_type <", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeLessThanOrEqualTo(String value) {
            addCriterion("pack_type <=", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeLike(String value) {
            addCriterion("pack_type like", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeNotLike(String value) {
            addCriterion("pack_type not like", value, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeIn(List<String> values) {
            addCriterion("pack_type in", values, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeNotIn(List<String> values) {
            addCriterion("pack_type not in", values, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeBetween(String value1, String value2) {
            addCriterion("pack_type between", value1, value2, "packType");
            return (Criteria) this;
        }

        public Criteria andPackTypeNotBetween(String value1, String value2) {
            addCriterion("pack_type not between", value1, value2, "packType");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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
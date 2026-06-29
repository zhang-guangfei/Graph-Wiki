package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RcvorderAllocateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvorderAllocateExample() {
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

        public Criteria andFromTypeIsNull() {
            addCriterion("from_type is null");
            return (Criteria) this;
        }

        public Criteria andFromTypeIsNotNull() {
            addCriterion("from_type is not null");
            return (Criteria) this;
        }

        public Criteria andFromTypeEqualTo(Integer value) {
            addCriterion("from_type =", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotEqualTo(Integer value) {
            addCriterion("from_type <>", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThan(Integer value) {
            addCriterion("from_type >", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("from_type >=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThan(Integer value) {
            addCriterion("from_type <", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThanOrEqualTo(Integer value) {
            addCriterion("from_type <=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeIn(List<Integer> values) {
            addCriterion("from_type in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotIn(List<Integer> values) {
            addCriterion("from_type not in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeBetween(Integer value1, Integer value2) {
            addCriterion("from_type between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("from_type not between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusIsNull() {
            addCriterion("delete_status is null");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusIsNotNull() {
            addCriterion("delete_status is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusEqualTo(Integer value) {
            addCriterion("delete_status =", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotEqualTo(Integer value) {
            addCriterion("delete_status <>", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusGreaterThan(Integer value) {
            addCriterion("delete_status >", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("delete_status >=", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusLessThan(Integer value) {
            addCriterion("delete_status <", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusLessThanOrEqualTo(Integer value) {
            addCriterion("delete_status <=", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusIn(List<Integer> values) {
            addCriterion("delete_status in", values, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotIn(List<Integer> values) {
            addCriterion("delete_status not in", values, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusBetween(Integer value1, Integer value2) {
            addCriterion("delete_status between", value1, value2, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("delete_status not between", value1, value2, "deleteStatus");
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

        public Criteria andDeliveryNoIsNull() {
            addCriterion("delivery_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIsNotNull() {
            addCriterion("delivery_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoEqualTo(String value) {
            addCriterion("delivery_no =", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotEqualTo(String value) {
            addCriterion("delivery_no <>", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThan(String value) {
            addCriterion("delivery_no >", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_no >=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThan(String value) {
            addCriterion("delivery_no <", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThanOrEqualTo(String value) {
            addCriterion("delivery_no <=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLike(String value) {
            addCriterion("delivery_no like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotLike(String value) {
            addCriterion("delivery_no not like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIn(List<String> values) {
            addCriterion("delivery_no in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotIn(List<String> values) {
            addCriterion("delivery_no not in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoBetween(String value1, String value2) {
            addCriterion("delivery_no between", value1, value2, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotBetween(String value1, String value2) {
            addCriterion("delivery_no not between", value1, value2, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIsNull() {
            addCriterion("inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIsNotNull() {
            addCriterion("inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdEqualTo(Long value) {
            addCriterion("inventory_id =", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotEqualTo(Long value) {
            addCriterion("inventory_id <>", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThan(Long value) {
            addCriterion("inventory_id >", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_id >=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThan(Long value) {
            addCriterion("inventory_id <", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_id <=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIn(List<Long> values) {
            addCriterion("inventory_id in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotIn(List<Long> values) {
            addCriterion("inventory_id not in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdBetween(Long value1, Long value2) {
            addCriterion("inventory_id between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_id not between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryNoIsNull() {
            addCriterion("inventory_no is null");
            return (Criteria) this;
        }

        public Criteria andInventoryNoIsNotNull() {
            addCriterion("inventory_no is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryNoEqualTo(String value) {
            addCriterion("inventory_no =", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoNotEqualTo(String value) {
            addCriterion("inventory_no <>", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoGreaterThan(String value) {
            addCriterion("inventory_no >", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_no >=", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoLessThan(String value) {
            addCriterion("inventory_no <", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoLessThanOrEqualTo(String value) {
            addCriterion("inventory_no <=", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoLike(String value) {
            addCriterion("inventory_no like", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoNotLike(String value) {
            addCriterion("inventory_no not like", value, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoIn(List<String> values) {
            addCriterion("inventory_no in", values, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoNotIn(List<String> values) {
            addCriterion("inventory_no not in", values, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoBetween(String value1, String value2) {
            addCriterion("inventory_no between", value1, value2, "inventoryNo");
            return (Criteria) this;
        }

        public Criteria andInventoryNoNotBetween(String value1, String value2) {
            addCriterion("inventory_no not between", value1, value2, "inventoryNo");
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

        public Criteria andTransactionNoIsNull() {
            addCriterion("transaction_no is null");
            return (Criteria) this;
        }

        public Criteria andTransactionNoIsNotNull() {
            addCriterion("transaction_no is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionNoEqualTo(String value) {
            addCriterion("transaction_no =", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoNotEqualTo(String value) {
            addCriterion("transaction_no <>", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoGreaterThan(String value) {
            addCriterion("transaction_no >", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoGreaterThanOrEqualTo(String value) {
            addCriterion("transaction_no >=", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoLessThan(String value) {
            addCriterion("transaction_no <", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoLessThanOrEqualTo(String value) {
            addCriterion("transaction_no <=", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoLike(String value) {
            addCriterion("transaction_no like", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoNotLike(String value) {
            addCriterion("transaction_no not like", value, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoIn(List<String> values) {
            addCriterion("transaction_no in", values, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoNotIn(List<String> values) {
            addCriterion("transaction_no not in", values, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoBetween(String value1, String value2) {
            addCriterion("transaction_no between", value1, value2, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionNoNotBetween(String value1, String value2) {
            addCriterion("transaction_no not between", value1, value2, "transactionNo");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIsNull() {
            addCriterion("transaction_type is null");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIsNotNull() {
            addCriterion("transaction_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeEqualTo(Integer value) {
            addCriterion("transaction_type =", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotEqualTo(Integer value) {
            addCriterion("transaction_type <>", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeGreaterThan(Integer value) {
            addCriterion("transaction_type >", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transaction_type >=", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeLessThan(Integer value) {
            addCriterion("transaction_type <", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("transaction_type <=", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIn(List<Integer> values) {
            addCriterion("transaction_type in", values, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotIn(List<Integer> values) {
            addCriterion("transaction_type not in", values, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeBetween(Integer value1, Integer value2) {
            addCriterion("transaction_type between", value1, value2, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("transaction_type not between", value1, value2, "transactionType");
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
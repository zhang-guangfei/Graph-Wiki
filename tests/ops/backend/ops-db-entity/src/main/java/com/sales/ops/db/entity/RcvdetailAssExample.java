package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RcvdetailAssExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvdetailAssExample() {
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

        public Criteria andUnitQtyIsNull() {
            addCriterion("unit_qty is null");
            return (Criteria) this;
        }

        public Criteria andUnitQtyIsNotNull() {
            addCriterion("unit_qty is not null");
            return (Criteria) this;
        }

        public Criteria andUnitQtyEqualTo(Integer value) {
            addCriterion("unit_qty =", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyNotEqualTo(Integer value) {
            addCriterion("unit_qty <>", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyGreaterThan(Integer value) {
            addCriterion("unit_qty >", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_qty >=", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyLessThan(Integer value) {
            addCriterion("unit_qty <", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyLessThanOrEqualTo(Integer value) {
            addCriterion("unit_qty <=", value, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyIn(List<Integer> values) {
            addCriterion("unit_qty in", values, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyNotIn(List<Integer> values) {
            addCriterion("unit_qty not in", values, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyBetween(Integer value1, Integer value2) {
            addCriterion("unit_qty between", value1, value2, "unitQty");
            return (Criteria) this;
        }

        public Criteria andUnitQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_qty not between", value1, value2, "unitQty");
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

        public Criteria andAllocatedQtyIsNull() {
            addCriterion("allocated_qty is null");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyIsNotNull() {
            addCriterion("allocated_qty is not null");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyEqualTo(Integer value) {
            addCriterion("allocated_qty =", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyNotEqualTo(Integer value) {
            addCriterion("allocated_qty <>", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyGreaterThan(Integer value) {
            addCriterion("allocated_qty >", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("allocated_qty >=", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyLessThan(Integer value) {
            addCriterion("allocated_qty <", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyLessThanOrEqualTo(Integer value) {
            addCriterion("allocated_qty <=", value, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyIn(List<Integer> values) {
            addCriterion("allocated_qty in", values, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyNotIn(List<Integer> values) {
            addCriterion("allocated_qty not in", values, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyBetween(Integer value1, Integer value2) {
            addCriterion("allocated_qty between", value1, value2, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andAllocatedQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("allocated_qty not between", value1, value2, "allocatedQty");
            return (Criteria) this;
        }

        public Criteria andStockNoIsNull() {
            addCriterion("stock_no is null");
            return (Criteria) this;
        }

        public Criteria andStockNoIsNotNull() {
            addCriterion("stock_no is not null");
            return (Criteria) this;
        }

        public Criteria andStockNoEqualTo(String value) {
            addCriterion("stock_no =", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoNotEqualTo(String value) {
            addCriterion("stock_no <>", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoGreaterThan(String value) {
            addCriterion("stock_no >", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoGreaterThanOrEqualTo(String value) {
            addCriterion("stock_no >=", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoLessThan(String value) {
            addCriterion("stock_no <", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoLessThanOrEqualTo(String value) {
            addCriterion("stock_no <=", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoLike(String value) {
            addCriterion("stock_no like", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoNotLike(String value) {
            addCriterion("stock_no not like", value, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoIn(List<String> values) {
            addCriterion("stock_no in", values, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoNotIn(List<String> values) {
            addCriterion("stock_no not in", values, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoBetween(String value1, String value2) {
            addCriterion("stock_no between", value1, value2, "stockNo");
            return (Criteria) this;
        }

        public Criteria andStockNoNotBetween(String value1, String value2) {
            addCriterion("stock_no not between", value1, value2, "stockNo");
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

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdIsNull() {
            addCriterion("parent_rorder_id is null");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdIsNotNull() {
            addCriterion("parent_rorder_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdEqualTo(Long value) {
            addCriterion("parent_rorder_id =", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdNotEqualTo(Long value) {
            addCriterion("parent_rorder_id <>", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdGreaterThan(Long value) {
            addCriterion("parent_rorder_id >", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_rorder_id >=", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdLessThan(Long value) {
            addCriterion("parent_rorder_id <", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_rorder_id <=", value, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdIn(List<Long> values) {
            addCriterion("parent_rorder_id in", values, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdNotIn(List<Long> values) {
            addCriterion("parent_rorder_id not in", values, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdBetween(Long value1, Long value2) {
            addCriterion("parent_rorder_id between", value1, value2, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentRorderIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_rorder_id not between", value1, value2, "parentRorderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoIsNull() {
            addCriterion("parent_order_no is null");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoIsNotNull() {
            addCriterion("parent_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoEqualTo(String value) {
            addCriterion("parent_order_no =", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoNotEqualTo(String value) {
            addCriterion("parent_order_no <>", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoGreaterThan(String value) {
            addCriterion("parent_order_no >", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("parent_order_no >=", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoLessThan(String value) {
            addCriterion("parent_order_no <", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoLessThanOrEqualTo(String value) {
            addCriterion("parent_order_no <=", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoLike(String value) {
            addCriterion("parent_order_no like", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoNotLike(String value) {
            addCriterion("parent_order_no not like", value, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoIn(List<String> values) {
            addCriterion("parent_order_no in", values, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoNotIn(List<String> values) {
            addCriterion("parent_order_no not in", values, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoBetween(String value1, String value2) {
            addCriterion("parent_order_no between", value1, value2, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentOrderNoNotBetween(String value1, String value2) {
            addCriterion("parent_order_no not between", value1, value2, "parentOrderNo");
            return (Criteria) this;
        }

        public Criteria andParentModelnoIsNull() {
            addCriterion("parent_modelno is null");
            return (Criteria) this;
        }

        public Criteria andParentModelnoIsNotNull() {
            addCriterion("parent_modelno is not null");
            return (Criteria) this;
        }

        public Criteria andParentModelnoEqualTo(String value) {
            addCriterion("parent_modelno =", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoNotEqualTo(String value) {
            addCriterion("parent_modelno <>", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoGreaterThan(String value) {
            addCriterion("parent_modelno >", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("parent_modelno >=", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoLessThan(String value) {
            addCriterion("parent_modelno <", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoLessThanOrEqualTo(String value) {
            addCriterion("parent_modelno <=", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoLike(String value) {
            addCriterion("parent_modelno like", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoNotLike(String value) {
            addCriterion("parent_modelno not like", value, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoIn(List<String> values) {
            addCriterion("parent_modelno in", values, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoNotIn(List<String> values) {
            addCriterion("parent_modelno not in", values, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoBetween(String value1, String value2) {
            addCriterion("parent_modelno between", value1, value2, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andParentModelnoNotBetween(String value1, String value2) {
            addCriterion("parent_modelno not between", value1, value2, "parentModelno");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeIsNull() {
            addCriterion("exp_dlvtype is null");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeIsNotNull() {
            addCriterion("exp_dlvtype is not null");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeEqualTo(Integer value) {
            addCriterion("exp_dlvtype =", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeNotEqualTo(Integer value) {
            addCriterion("exp_dlvtype <>", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeGreaterThan(Integer value) {
            addCriterion("exp_dlvtype >", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_dlvtype >=", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeLessThan(Integer value) {
            addCriterion("exp_dlvtype <", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeLessThanOrEqualTo(Integer value) {
            addCriterion("exp_dlvtype <=", value, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeIn(List<Integer> values) {
            addCriterion("exp_dlvtype in", values, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeNotIn(List<Integer> values) {
            addCriterion("exp_dlvtype not in", values, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlvtype between", value1, value2, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andExpDlvtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlvtype not between", value1, value2, "expDlvtype");
            return (Criteria) this;
        }

        public Criteria andAssTypeIsNull() {
            addCriterion("ass_type is null");
            return (Criteria) this;
        }

        public Criteria andAssTypeIsNotNull() {
            addCriterion("ass_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssTypeEqualTo(Short value) {
            addCriterion("ass_type =", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeNotEqualTo(Short value) {
            addCriterion("ass_type <>", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeGreaterThan(Short value) {
            addCriterion("ass_type >", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("ass_type >=", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeLessThan(Short value) {
            addCriterion("ass_type <", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeLessThanOrEqualTo(Short value) {
            addCriterion("ass_type <=", value, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeIn(List<Short> values) {
            addCriterion("ass_type in", values, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeNotIn(List<Short> values) {
            addCriterion("ass_type not in", values, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeBetween(Short value1, Short value2) {
            addCriterion("ass_type between", value1, value2, "assType");
            return (Criteria) this;
        }

        public Criteria andAssTypeNotBetween(Short value1, Short value2) {
            addCriterion("ass_type not between", value1, value2, "assType");
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

        public Criteria andAgentSalespriceIsNull() {
            addCriterion("agent_salesprice is null");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceIsNotNull() {
            addCriterion("agent_salesprice is not null");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceEqualTo(BigDecimal value) {
            addCriterion("agent_salesprice =", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceNotEqualTo(BigDecimal value) {
            addCriterion("agent_salesprice <>", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceGreaterThan(BigDecimal value) {
            addCriterion("agent_salesprice >", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("agent_salesprice >=", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceLessThan(BigDecimal value) {
            addCriterion("agent_salesprice <", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("agent_salesprice <=", value, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceIn(List<BigDecimal> values) {
            addCriterion("agent_salesprice in", values, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceNotIn(List<BigDecimal> values) {
            addCriterion("agent_salesprice not in", values, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agent_salesprice between", value1, value2, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andAgentSalespriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agent_salesprice not between", value1, value2, "agentSalesprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("eprice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("eprice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("eprice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("eprice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("eprice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("eprice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("eprice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("eprice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNull() {
            addCriterion("taxrate is null");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNotNull() {
            addCriterion("taxrate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxrateEqualTo(BigDecimal value) {
            addCriterion("taxrate =", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("taxrate <>", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThan(BigDecimal value) {
            addCriterion("taxrate >", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("taxrate >=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThan(BigDecimal value) {
            addCriterion("taxrate <", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("taxrate <=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateIn(List<BigDecimal> values) {
            addCriterion("taxrate in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("taxrate not in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxrate between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxrate not between", value1, value2, "taxrate");
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
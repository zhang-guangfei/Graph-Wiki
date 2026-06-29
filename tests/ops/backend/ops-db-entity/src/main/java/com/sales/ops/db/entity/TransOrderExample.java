package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransOrderExample() {
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

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(Integer value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(Integer value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(Integer value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(Integer value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(Integer value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<Integer> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<Integer> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(Integer value1, Integer value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransNoIsNull() {
            addCriterion("trans_no is null");
            return (Criteria) this;
        }

        public Criteria andTransNoIsNotNull() {
            addCriterion("trans_no is not null");
            return (Criteria) this;
        }

        public Criteria andTransNoEqualTo(String value) {
            addCriterion("trans_no =", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotEqualTo(String value) {
            addCriterion("trans_no <>", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoGreaterThan(String value) {
            addCriterion("trans_no >", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoGreaterThanOrEqualTo(String value) {
            addCriterion("trans_no >=", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLessThan(String value) {
            addCriterion("trans_no <", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLessThanOrEqualTo(String value) {
            addCriterion("trans_no <=", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLike(String value) {
            addCriterion("trans_no like", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotLike(String value) {
            addCriterion("trans_no not like", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoIn(List<String> values) {
            addCriterion("trans_no in", values, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotIn(List<String> values) {
            addCriterion("trans_no not in", values, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoBetween(String value1, String value2) {
            addCriterion("trans_no between", value1, value2, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotBetween(String value1, String value2) {
            addCriterion("trans_no not between", value1, value2, "transNo");
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

        public Criteria andFromNoIsNull() {
            addCriterion("from_no is null");
            return (Criteria) this;
        }

        public Criteria andFromNoIsNotNull() {
            addCriterion("from_no is not null");
            return (Criteria) this;
        }

        public Criteria andFromNoEqualTo(String value) {
            addCriterion("from_no =", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoNotEqualTo(String value) {
            addCriterion("from_no <>", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoGreaterThan(String value) {
            addCriterion("from_no >", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoGreaterThanOrEqualTo(String value) {
            addCriterion("from_no >=", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoLessThan(String value) {
            addCriterion("from_no <", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoLessThanOrEqualTo(String value) {
            addCriterion("from_no <=", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoLike(String value) {
            addCriterion("from_no like", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoNotLike(String value) {
            addCriterion("from_no not like", value, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoIn(List<String> values) {
            addCriterion("from_no in", values, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoNotIn(List<String> values) {
            addCriterion("from_no not in", values, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoBetween(String value1, String value2) {
            addCriterion("from_no between", value1, value2, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromNoNotBetween(String value1, String value2) {
            addCriterion("from_no not between", value1, value2, "fromNo");
            return (Criteria) this;
        }

        public Criteria andFromIdIsNull() {
            addCriterion("from_id is null");
            return (Criteria) this;
        }

        public Criteria andFromIdIsNotNull() {
            addCriterion("from_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromIdEqualTo(Long value) {
            addCriterion("from_id =", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdNotEqualTo(Long value) {
            addCriterion("from_id <>", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdGreaterThan(Long value) {
            addCriterion("from_id >", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_id >=", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdLessThan(Long value) {
            addCriterion("from_id <", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdLessThanOrEqualTo(Long value) {
            addCriterion("from_id <=", value, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdIn(List<Long> values) {
            addCriterion("from_id in", values, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdNotIn(List<Long> values) {
            addCriterion("from_id not in", values, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdBetween(Long value1, Long value2) {
            addCriterion("from_id between", value1, value2, "fromId");
            return (Criteria) this;
        }

        public Criteria andFromIdNotBetween(Long value1, Long value2) {
            addCriterion("from_id not between", value1, value2, "fromId");
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

        public Criteria andFromInventoryPropertyIdIsNull() {
            addCriterion("from_inventory_property_id is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdIsNotNull() {
            addCriterion("from_inventory_property_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdEqualTo(Long value) {
            addCriterion("from_inventory_property_id =", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdNotEqualTo(Long value) {
            addCriterion("from_inventory_property_id <>", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdGreaterThan(Long value) {
            addCriterion("from_inventory_property_id >", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_inventory_property_id >=", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdLessThan(Long value) {
            addCriterion("from_inventory_property_id <", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("from_inventory_property_id <=", value, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdIn(List<Long> values) {
            addCriterion("from_inventory_property_id in", values, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdNotIn(List<Long> values) {
            addCriterion("from_inventory_property_id not in", values, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdBetween(Long value1, Long value2) {
            addCriterion("from_inventory_property_id between", value1, value2, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("from_inventory_property_id not between", value1, value2, "fromInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeIsNull() {
            addCriterion("from_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeIsNotNull() {
            addCriterion("from_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeEqualTo(String value) {
            addCriterion("from_warehouse_code =", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeNotEqualTo(String value) {
            addCriterion("from_warehouse_code <>", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeGreaterThan(String value) {
            addCriterion("from_warehouse_code >", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("from_warehouse_code >=", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeLessThan(String value) {
            addCriterion("from_warehouse_code <", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("from_warehouse_code <=", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeLike(String value) {
            addCriterion("from_warehouse_code like", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeNotLike(String value) {
            addCriterion("from_warehouse_code not like", value, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeIn(List<String> values) {
            addCriterion("from_warehouse_code in", values, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeNotIn(List<String> values) {
            addCriterion("from_warehouse_code not in", values, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeBetween(String value1, String value2) {
            addCriterion("from_warehouse_code between", value1, value2, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("from_warehouse_code not between", value1, value2, "fromWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeIsNull() {
            addCriterion("from_inventory_type_code is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeIsNotNull() {
            addCriterion("from_inventory_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeEqualTo(String value) {
            addCriterion("from_inventory_type_code =", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeNotEqualTo(String value) {
            addCriterion("from_inventory_type_code <>", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeGreaterThan(String value) {
            addCriterion("from_inventory_type_code >", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("from_inventory_type_code >=", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeLessThan(String value) {
            addCriterion("from_inventory_type_code <", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("from_inventory_type_code <=", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeLike(String value) {
            addCriterion("from_inventory_type_code like", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeNotLike(String value) {
            addCriterion("from_inventory_type_code not like", value, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeIn(List<String> values) {
            addCriterion("from_inventory_type_code in", values, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeNotIn(List<String> values) {
            addCriterion("from_inventory_type_code not in", values, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeBetween(String value1, String value2) {
            addCriterion("from_inventory_type_code between", value1, value2, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTypeCodeNotBetween(String value1, String value2) {
            addCriterion("from_inventory_type_code not between", value1, value2, "fromInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andFromPplIsNull() {
            addCriterion("from_ppl is null");
            return (Criteria) this;
        }

        public Criteria andFromPplIsNotNull() {
            addCriterion("from_ppl is not null");
            return (Criteria) this;
        }

        public Criteria andFromPplEqualTo(String value) {
            addCriterion("from_ppl =", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplNotEqualTo(String value) {
            addCriterion("from_ppl <>", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplGreaterThan(String value) {
            addCriterion("from_ppl >", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplGreaterThanOrEqualTo(String value) {
            addCriterion("from_ppl >=", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplLessThan(String value) {
            addCriterion("from_ppl <", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplLessThanOrEqualTo(String value) {
            addCriterion("from_ppl <=", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplLike(String value) {
            addCriterion("from_ppl like", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplNotLike(String value) {
            addCriterion("from_ppl not like", value, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplIn(List<String> values) {
            addCriterion("from_ppl in", values, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplNotIn(List<String> values) {
            addCriterion("from_ppl not in", values, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplBetween(String value1, String value2) {
            addCriterion("from_ppl between", value1, value2, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromPplNotBetween(String value1, String value2) {
            addCriterion("from_ppl not between", value1, value2, "fromPpl");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeIsNull() {
            addCriterion("from_project_code is null");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeIsNotNull() {
            addCriterion("from_project_code is not null");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeEqualTo(String value) {
            addCriterion("from_project_code =", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeNotEqualTo(String value) {
            addCriterion("from_project_code <>", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeGreaterThan(String value) {
            addCriterion("from_project_code >", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("from_project_code >=", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeLessThan(String value) {
            addCriterion("from_project_code <", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeLessThanOrEqualTo(String value) {
            addCriterion("from_project_code <=", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeLike(String value) {
            addCriterion("from_project_code like", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeNotLike(String value) {
            addCriterion("from_project_code not like", value, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeIn(List<String> values) {
            addCriterion("from_project_code in", values, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeNotIn(List<String> values) {
            addCriterion("from_project_code not in", values, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeBetween(String value1, String value2) {
            addCriterion("from_project_code between", value1, value2, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromProjectCodeNotBetween(String value1, String value2) {
            addCriterion("from_project_code not between", value1, value2, "fromProjectCode");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoIsNull() {
            addCriterion("from_group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoIsNotNull() {
            addCriterion("from_group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoEqualTo(String value) {
            addCriterion("from_group_customer_no =", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoNotEqualTo(String value) {
            addCriterion("from_group_customer_no <>", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoGreaterThan(String value) {
            addCriterion("from_group_customer_no >", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("from_group_customer_no >=", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoLessThan(String value) {
            addCriterion("from_group_customer_no <", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("from_group_customer_no <=", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoLike(String value) {
            addCriterion("from_group_customer_no like", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoNotLike(String value) {
            addCriterion("from_group_customer_no not like", value, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoIn(List<String> values) {
            addCriterion("from_group_customer_no in", values, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoNotIn(List<String> values) {
            addCriterion("from_group_customer_no not in", values, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoBetween(String value1, String value2) {
            addCriterion("from_group_customer_no between", value1, value2, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromGroupCustomerNoNotBetween(String value1, String value2) {
            addCriterion("from_group_customer_no not between", value1, value2, "fromGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoIsNull() {
            addCriterion("from_sales_info_no is null");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoIsNotNull() {
            addCriterion("from_sales_info_no is not null");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoEqualTo(String value) {
            addCriterion("from_sales_info_no =", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoNotEqualTo(String value) {
            addCriterion("from_sales_info_no <>", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoGreaterThan(String value) {
            addCriterion("from_sales_info_no >", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoGreaterThanOrEqualTo(String value) {
            addCriterion("from_sales_info_no >=", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoLessThan(String value) {
            addCriterion("from_sales_info_no <", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoLessThanOrEqualTo(String value) {
            addCriterion("from_sales_info_no <=", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoLike(String value) {
            addCriterion("from_sales_info_no like", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoNotLike(String value) {
            addCriterion("from_sales_info_no not like", value, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoIn(List<String> values) {
            addCriterion("from_sales_info_no in", values, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoNotIn(List<String> values) {
            addCriterion("from_sales_info_no not in", values, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoBetween(String value1, String value2) {
            addCriterion("from_sales_info_no between", value1, value2, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromSalesInfoNoNotBetween(String value1, String value2) {
            addCriterion("from_sales_info_no not between", value1, value2, "fromSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoIsNull() {
            addCriterion("from_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoIsNotNull() {
            addCriterion("from_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoEqualTo(String value) {
            addCriterion("from_customer_no =", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoNotEqualTo(String value) {
            addCriterion("from_customer_no <>", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoGreaterThan(String value) {
            addCriterion("from_customer_no >", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("from_customer_no >=", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoLessThan(String value) {
            addCriterion("from_customer_no <", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("from_customer_no <=", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoLike(String value) {
            addCriterion("from_customer_no like", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoNotLike(String value) {
            addCriterion("from_customer_no not like", value, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoIn(List<String> values) {
            addCriterion("from_customer_no in", values, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoNotIn(List<String> values) {
            addCriterion("from_customer_no not in", values, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoBetween(String value1, String value2) {
            addCriterion("from_customer_no between", value1, value2, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andFromCustomerNoNotBetween(String value1, String value2) {
            addCriterion("from_customer_no not between", value1, value2, "fromCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdIsNull() {
            addCriterion("to_inventory_property_id is null");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdIsNotNull() {
            addCriterion("to_inventory_property_id is not null");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdEqualTo(Long value) {
            addCriterion("to_inventory_property_id =", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdNotEqualTo(Long value) {
            addCriterion("to_inventory_property_id <>", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdGreaterThan(Long value) {
            addCriterion("to_inventory_property_id >", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_inventory_property_id >=", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdLessThan(Long value) {
            addCriterion("to_inventory_property_id <", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("to_inventory_property_id <=", value, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdIn(List<Long> values) {
            addCriterion("to_inventory_property_id in", values, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdNotIn(List<Long> values) {
            addCriterion("to_inventory_property_id not in", values, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdBetween(Long value1, Long value2) {
            addCriterion("to_inventory_property_id between", value1, value2, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToInventoryPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("to_inventory_property_id not between", value1, value2, "toInventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIsNull() {
            addCriterion("to_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIsNotNull() {
            addCriterion("to_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeEqualTo(String value) {
            addCriterion("to_warehouse_code =", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotEqualTo(String value) {
            addCriterion("to_warehouse_code <>", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeGreaterThan(String value) {
            addCriterion("to_warehouse_code >", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("to_warehouse_code >=", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLessThan(String value) {
            addCriterion("to_warehouse_code <", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("to_warehouse_code <=", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLike(String value) {
            addCriterion("to_warehouse_code like", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotLike(String value) {
            addCriterion("to_warehouse_code not like", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIn(List<String> values) {
            addCriterion("to_warehouse_code in", values, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotIn(List<String> values) {
            addCriterion("to_warehouse_code not in", values, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeBetween(String value1, String value2) {
            addCriterion("to_warehouse_code between", value1, value2, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("to_warehouse_code not between", value1, value2, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeIsNull() {
            addCriterion("to_inventory_type_code is null");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeIsNotNull() {
            addCriterion("to_inventory_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeEqualTo(String value) {
            addCriterion("to_inventory_type_code =", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeNotEqualTo(String value) {
            addCriterion("to_inventory_type_code <>", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeGreaterThan(String value) {
            addCriterion("to_inventory_type_code >", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("to_inventory_type_code >=", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeLessThan(String value) {
            addCriterion("to_inventory_type_code <", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("to_inventory_type_code <=", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeLike(String value) {
            addCriterion("to_inventory_type_code like", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeNotLike(String value) {
            addCriterion("to_inventory_type_code not like", value, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeIn(List<String> values) {
            addCriterion("to_inventory_type_code in", values, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeNotIn(List<String> values) {
            addCriterion("to_inventory_type_code not in", values, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeBetween(String value1, String value2) {
            addCriterion("to_inventory_type_code between", value1, value2, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToInventoryTypeCodeNotBetween(String value1, String value2) {
            addCriterion("to_inventory_type_code not between", value1, value2, "toInventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andToPplIsNull() {
            addCriterion("to_ppl is null");
            return (Criteria) this;
        }

        public Criteria andToPplIsNotNull() {
            addCriterion("to_ppl is not null");
            return (Criteria) this;
        }

        public Criteria andToPplEqualTo(String value) {
            addCriterion("to_ppl =", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplNotEqualTo(String value) {
            addCriterion("to_ppl <>", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplGreaterThan(String value) {
            addCriterion("to_ppl >", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplGreaterThanOrEqualTo(String value) {
            addCriterion("to_ppl >=", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplLessThan(String value) {
            addCriterion("to_ppl <", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplLessThanOrEqualTo(String value) {
            addCriterion("to_ppl <=", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplLike(String value) {
            addCriterion("to_ppl like", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplNotLike(String value) {
            addCriterion("to_ppl not like", value, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplIn(List<String> values) {
            addCriterion("to_ppl in", values, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplNotIn(List<String> values) {
            addCriterion("to_ppl not in", values, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplBetween(String value1, String value2) {
            addCriterion("to_ppl between", value1, value2, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToPplNotBetween(String value1, String value2) {
            addCriterion("to_ppl not between", value1, value2, "toPpl");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeIsNull() {
            addCriterion("to_project_code is null");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeIsNotNull() {
            addCriterion("to_project_code is not null");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeEqualTo(String value) {
            addCriterion("to_project_code =", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeNotEqualTo(String value) {
            addCriterion("to_project_code <>", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeGreaterThan(String value) {
            addCriterion("to_project_code >", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("to_project_code >=", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeLessThan(String value) {
            addCriterion("to_project_code <", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeLessThanOrEqualTo(String value) {
            addCriterion("to_project_code <=", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeLike(String value) {
            addCriterion("to_project_code like", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeNotLike(String value) {
            addCriterion("to_project_code not like", value, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeIn(List<String> values) {
            addCriterion("to_project_code in", values, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeNotIn(List<String> values) {
            addCriterion("to_project_code not in", values, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeBetween(String value1, String value2) {
            addCriterion("to_project_code between", value1, value2, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToProjectCodeNotBetween(String value1, String value2) {
            addCriterion("to_project_code not between", value1, value2, "toProjectCode");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoIsNull() {
            addCriterion("to_group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoIsNotNull() {
            addCriterion("to_group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoEqualTo(String value) {
            addCriterion("to_group_customer_no =", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoNotEqualTo(String value) {
            addCriterion("to_group_customer_no <>", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoGreaterThan(String value) {
            addCriterion("to_group_customer_no >", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("to_group_customer_no >=", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoLessThan(String value) {
            addCriterion("to_group_customer_no <", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("to_group_customer_no <=", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoLike(String value) {
            addCriterion("to_group_customer_no like", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoNotLike(String value) {
            addCriterion("to_group_customer_no not like", value, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoIn(List<String> values) {
            addCriterion("to_group_customer_no in", values, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoNotIn(List<String> values) {
            addCriterion("to_group_customer_no not in", values, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoBetween(String value1, String value2) {
            addCriterion("to_group_customer_no between", value1, value2, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToGroupCustomerNoNotBetween(String value1, String value2) {
            addCriterion("to_group_customer_no not between", value1, value2, "toGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoIsNull() {
            addCriterion("to_sales_info_no is null");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoIsNotNull() {
            addCriterion("to_sales_info_no is not null");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoEqualTo(String value) {
            addCriterion("to_sales_info_no =", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoNotEqualTo(String value) {
            addCriterion("to_sales_info_no <>", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoGreaterThan(String value) {
            addCriterion("to_sales_info_no >", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoGreaterThanOrEqualTo(String value) {
            addCriterion("to_sales_info_no >=", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoLessThan(String value) {
            addCriterion("to_sales_info_no <", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoLessThanOrEqualTo(String value) {
            addCriterion("to_sales_info_no <=", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoLike(String value) {
            addCriterion("to_sales_info_no like", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoNotLike(String value) {
            addCriterion("to_sales_info_no not like", value, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoIn(List<String> values) {
            addCriterion("to_sales_info_no in", values, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoNotIn(List<String> values) {
            addCriterion("to_sales_info_no not in", values, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoBetween(String value1, String value2) {
            addCriterion("to_sales_info_no between", value1, value2, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToSalesInfoNoNotBetween(String value1, String value2) {
            addCriterion("to_sales_info_no not between", value1, value2, "toSalesInfoNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoIsNull() {
            addCriterion("to_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoIsNotNull() {
            addCriterion("to_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoEqualTo(String value) {
            addCriterion("to_customer_no =", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoNotEqualTo(String value) {
            addCriterion("to_customer_no <>", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoGreaterThan(String value) {
            addCriterion("to_customer_no >", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("to_customer_no >=", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoLessThan(String value) {
            addCriterion("to_customer_no <", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("to_customer_no <=", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoLike(String value) {
            addCriterion("to_customer_no like", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoNotLike(String value) {
            addCriterion("to_customer_no not like", value, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoIn(List<String> values) {
            addCriterion("to_customer_no in", values, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoNotIn(List<String> values) {
            addCriterion("to_customer_no not in", values, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoBetween(String value1, String value2) {
            addCriterion("to_customer_no between", value1, value2, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andToCustomerNoNotBetween(String value1, String value2) {
            addCriterion("to_customer_no not between", value1, value2, "toCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInQuantityIsNull() {
            addCriterion("in_quantity is null");
            return (Criteria) this;
        }

        public Criteria andInQuantityIsNotNull() {
            addCriterion("in_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andInQuantityEqualTo(Integer value) {
            addCriterion("in_quantity =", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityNotEqualTo(Integer value) {
            addCriterion("in_quantity <>", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityGreaterThan(Integer value) {
            addCriterion("in_quantity >", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_quantity >=", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityLessThan(Integer value) {
            addCriterion("in_quantity <", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("in_quantity <=", value, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityIn(List<Integer> values) {
            addCriterion("in_quantity in", values, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityNotIn(List<Integer> values) {
            addCriterion("in_quantity not in", values, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityBetween(Integer value1, Integer value2) {
            addCriterion("in_quantity between", value1, value2, "inQuantity");
            return (Criteria) this;
        }

        public Criteria andInQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("in_quantity not between", value1, value2, "inQuantity");
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
            addCriterion("wms_dlv_date =", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotEqualTo(Date value) {
            addCriterion("wms_dlv_date <>", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThan(Date value) {
            addCriterion("wms_dlv_date >", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("wms_dlv_date >=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThan(Date value) {
            addCriterion("wms_dlv_date <", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("wms_dlv_date <=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIn(List<Date> values) {
            addCriterion("wms_dlv_date in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotIn(List<Date> values) {
            addCriterion("wms_dlv_date not in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateBetween(Date value1, Date value2) {
            addCriterion("wms_dlv_date between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("wms_dlv_date not between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
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

        public Criteria andInvoiceNoIsNull() {
            addCriterion("invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNotNull() {
            addCriterion("invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoEqualTo(String value) {
            addCriterion("invoice_no =", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotEqualTo(String value) {
            addCriterion("invoice_no <>", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThan(String value) {
            addCriterion("invoice_no >", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no >=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThan(String value) {
            addCriterion("invoice_no <", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_no <=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLike(String value) {
            addCriterion("invoice_no like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotLike(String value) {
            addCriterion("invoice_no not like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIn(List<String> values) {
            addCriterion("invoice_no in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotIn(List<String> values) {
            addCriterion("invoice_no not in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoBetween(String value1, String value2) {
            addCriterion("invoice_no between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("invoice_no not between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andShipQtyIsNull() {
            addCriterion("ship_qty is null");
            return (Criteria) this;
        }

        public Criteria andShipQtyIsNotNull() {
            addCriterion("ship_qty is not null");
            return (Criteria) this;
        }

        public Criteria andShipQtyEqualTo(Integer value) {
            addCriterion("ship_qty =", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyNotEqualTo(Integer value) {
            addCriterion("ship_qty <>", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyGreaterThan(Integer value) {
            addCriterion("ship_qty >", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ship_qty >=", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyLessThan(Integer value) {
            addCriterion("ship_qty <", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyLessThanOrEqualTo(Integer value) {
            addCriterion("ship_qty <=", value, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyIn(List<Integer> values) {
            addCriterion("ship_qty in", values, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyNotIn(List<Integer> values) {
            addCriterion("ship_qty not in", values, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyBetween(Integer value1, Integer value2) {
            addCriterion("ship_qty between", value1, value2, "shipQty");
            return (Criteria) this;
        }

        public Criteria andShipQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ship_qty not between", value1, value2, "shipQty");
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

        public Criteria andTransFlagIsNull() {
            addCriterion("trans_flag is null");
            return (Criteria) this;
        }

        public Criteria andTransFlagIsNotNull() {
            addCriterion("trans_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTransFlagEqualTo(Boolean value) {
            addCriterion("trans_flag =", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagNotEqualTo(Boolean value) {
            addCriterion("trans_flag <>", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagGreaterThan(Boolean value) {
            addCriterion("trans_flag >", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("trans_flag >=", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagLessThan(Boolean value) {
            addCriterion("trans_flag <", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("trans_flag <=", value, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagIn(List<Boolean> values) {
            addCriterion("trans_flag in", values, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagNotIn(List<Boolean> values) {
            addCriterion("trans_flag not in", values, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("trans_flag between", value1, value2, "transFlag");
            return (Criteria) this;
        }

        public Criteria andTransFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("trans_flag not between", value1, value2, "transFlag");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIsNull() {
            addCriterion("from_inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIsNotNull() {
            addCriterion("from_inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdEqualTo(Long value) {
            addCriterion("from_inventory_id =", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotEqualTo(Long value) {
            addCriterion("from_inventory_id <>", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThan(Long value) {
            addCriterion("from_inventory_id >", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id >=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThan(Long value) {
            addCriterion("from_inventory_id <", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id <=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIn(List<Long> values) {
            addCriterion("from_inventory_id in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotIn(List<Long> values) {
            addCriterion("from_inventory_id not in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id not between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoIsNull() {
            addCriterion("from_associate_no is null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoIsNotNull() {
            addCriterion("from_associate_no is not null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoEqualTo(String value) {
            addCriterion("from_associate_no =", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoNotEqualTo(String value) {
            addCriterion("from_associate_no <>", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoGreaterThan(String value) {
            addCriterion("from_associate_no >", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoGreaterThanOrEqualTo(String value) {
            addCriterion("from_associate_no >=", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoLessThan(String value) {
            addCriterion("from_associate_no <", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoLessThanOrEqualTo(String value) {
            addCriterion("from_associate_no <=", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoLike(String value) {
            addCriterion("from_associate_no like", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoNotLike(String value) {
            addCriterion("from_associate_no not like", value, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoIn(List<String> values) {
            addCriterion("from_associate_no in", values, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoNotIn(List<String> values) {
            addCriterion("from_associate_no not in", values, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoBetween(String value1, String value2) {
            addCriterion("from_associate_no between", value1, value2, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoNotBetween(String value1, String value2) {
            addCriterion("from_associate_no not between", value1, value2, "fromAssociateNo");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemIsNull() {
            addCriterion("from_associate_no_item is null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemIsNotNull() {
            addCriterion("from_associate_no_item is not null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemEqualTo(Integer value) {
            addCriterion("from_associate_no_item =", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemNotEqualTo(Integer value) {
            addCriterion("from_associate_no_item <>", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemGreaterThan(Integer value) {
            addCriterion("from_associate_no_item >", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("from_associate_no_item >=", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemLessThan(Integer value) {
            addCriterion("from_associate_no_item <", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemLessThanOrEqualTo(Integer value) {
            addCriterion("from_associate_no_item <=", value, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemIn(List<Integer> values) {
            addCriterion("from_associate_no_item in", values, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemNotIn(List<Integer> values) {
            addCriterion("from_associate_no_item not in", values, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemBetween(Integer value1, Integer value2) {
            addCriterion("from_associate_no_item between", value1, value2, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("from_associate_no_item not between", value1, value2, "fromAssociateNoItem");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitIsNull() {
            addCriterion("from_associate_no_split is null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitIsNotNull() {
            addCriterion("from_associate_no_split is not null");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitEqualTo(Integer value) {
            addCriterion("from_associate_no_split =", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitNotEqualTo(Integer value) {
            addCriterion("from_associate_no_split <>", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitGreaterThan(Integer value) {
            addCriterion("from_associate_no_split >", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitGreaterThanOrEqualTo(Integer value) {
            addCriterion("from_associate_no_split >=", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitLessThan(Integer value) {
            addCriterion("from_associate_no_split <", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitLessThanOrEqualTo(Integer value) {
            addCriterion("from_associate_no_split <=", value, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitIn(List<Integer> values) {
            addCriterion("from_associate_no_split in", values, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitNotIn(List<Integer> values) {
            addCriterion("from_associate_no_split not in", values, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitBetween(Integer value1, Integer value2) {
            addCriterion("from_associate_no_split between", value1, value2, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andFromAssociateNoSplitNotBetween(Integer value1, Integer value2) {
            addCriterion("from_associate_no_split not between", value1, value2, "fromAssociateNoSplit");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNull() {
            addCriterion("invoice_id is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNotNull() {
            addCriterion("invoice_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdEqualTo(Long value) {
            addCriterion("invoice_id =", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotEqualTo(Long value) {
            addCriterion("invoice_id <>", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThan(Long value) {
            addCriterion("invoice_id >", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("invoice_id >=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThan(Long value) {
            addCriterion("invoice_id <", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThanOrEqualTo(Long value) {
            addCriterion("invoice_id <=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIn(List<Long> values) {
            addCriterion("invoice_id in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotIn(List<Long> values) {
            addCriterion("invoice_id not in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdBetween(Long value1, Long value2) {
            addCriterion("invoice_id between", value1, value2, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotBetween(Long value1, Long value2) {
            addCriterion("invoice_id not between", value1, value2, "invoiceId");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsInventoryMoveExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInventoryMoveExample() {
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

        public Criteria andSourceTypeIsNull() {
            addCriterion("source_type is null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIsNotNull() {
            addCriterion("source_type is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeEqualTo(String value) {
            addCriterion("source_type =", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotEqualTo(String value) {
            addCriterion("source_type <>", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThan(String value) {
            addCriterion("source_type >", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("source_type >=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThan(String value) {
            addCriterion("source_type <", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThanOrEqualTo(String value) {
            addCriterion("source_type <=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLike(String value) {
            addCriterion("source_type like", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotLike(String value) {
            addCriterion("source_type not like", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIn(List<String> values) {
            addCriterion("source_type in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotIn(List<String> values) {
            addCriterion("source_type not in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBetween(String value1, String value2) {
            addCriterion("source_type between", value1, value2, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotBetween(String value1, String value2) {
            addCriterion("source_type not between", value1, value2, "sourceType");
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

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andQaStatusEqualTo(String value) {
            addCriterion("qa_status =", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotEqualTo(String value) {
            addCriterion("qa_status <>", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThan(String value) {
            addCriterion("qa_status >", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThanOrEqualTo(String value) {
            addCriterion("qa_status >=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThan(String value) {
            addCriterion("qa_status <", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThanOrEqualTo(String value) {
            addCriterion("qa_status <=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLike(String value) {
            addCriterion("qa_status like", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotLike(String value) {
            addCriterion("qa_status not like", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusIn(List<String> values) {
            addCriterion("qa_status in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotIn(List<String> values) {
            addCriterion("qa_status not in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusBetween(String value1, String value2) {
            addCriterion("qa_status between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotBetween(String value1, String value2) {
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

        public Criteria andPrepareQuantityEqualTo(Integer value) {
            addCriterion("prepare_quantity =", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotEqualTo(Integer value) {
            addCriterion("prepare_quantity <>", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThan(Integer value) {
            addCriterion("prepare_quantity >", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("prepare_quantity >=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThan(Integer value) {
            addCriterion("prepare_quantity <", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("prepare_quantity <=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIn(List<Integer> values) {
            addCriterion("prepare_quantity in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotIn(List<Integer> values) {
            addCriterion("prepare_quantity not in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityBetween(Integer value1, Integer value2) {
            addCriterion("prepare_quantity between", value1, value2, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("prepare_quantity not between", value1, value2, "prepareQuantity");
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

        public Criteria andInventoryPropertyIdIsNull() {
            addCriterion("inventory_property_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIsNotNull() {
            addCriterion("inventory_property_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdEqualTo(Long value) {
            addCriterion("inventory_property_id =", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotEqualTo(Long value) {
            addCriterion("inventory_property_id <>", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThan(Long value) {
            addCriterion("inventory_property_id >", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id >=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThan(Long value) {
            addCriterion("inventory_property_id <", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id <=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIn(List<Long> values) {
            addCriterion("inventory_property_id in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotIn(List<Long> values) {
            addCriterion("inventory_property_id not in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id between", value1, value2, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id not between", value1, value2, "inventoryPropertyId");
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

        public Criteria andInTimeIsNull() {
            addCriterion("in_time is null");
            return (Criteria) this;
        }

        public Criteria andInTimeIsNotNull() {
            addCriterion("in_time is not null");
            return (Criteria) this;
        }

        public Criteria andInTimeEqualTo(Date value) {
            addCriterion("in_time =", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeNotEqualTo(Date value) {
            addCriterion("in_time <>", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeGreaterThan(Date value) {
            addCriterion("in_time >", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("in_time >=", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeLessThan(Date value) {
            addCriterion("in_time <", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeLessThanOrEqualTo(Date value) {
            addCriterion("in_time <=", value, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeIn(List<Date> values) {
            addCriterion("in_time in", values, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeNotIn(List<Date> values) {
            addCriterion("in_time not in", values, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeBetween(Date value1, Date value2) {
            addCriterion("in_time between", value1, value2, "inTime");
            return (Criteria) this;
        }

        public Criteria andInTimeNotBetween(Date value1, Date value2) {
            addCriterion("in_time not between", value1, value2, "inTime");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("itemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("itemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Integer value) {
            addCriterion("itemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Integer value) {
            addCriterion("itemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Integer value) {
            addCriterion("itemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Integer value) {
            addCriterion("itemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Integer value) {
            addCriterion("itemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Integer> values) {
            addCriterion("itemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Integer> values) {
            addCriterion("itemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Integer value1, Integer value2) {
            addCriterion("itemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("itemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNull() {
            addCriterion("splitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNotNull() {
            addCriterion("splitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoEqualTo(Integer value) {
            addCriterion("splitItemNo =", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotEqualTo(Integer value) {
            addCriterion("splitItemNo <>", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThan(Integer value) {
            addCriterion("splitItemNo >", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo >=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThan(Integer value) {
            addCriterion("splitItemNo <", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo <=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIn(List<Integer> values) {
            addCriterion("splitItemNo in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotIn(List<Integer> values) {
            addCriterion("splitItemNo not in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo not between", value1, value2, "splititemno");
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

        public Criteria andPrereceivedateIsNull() {
            addCriterion("preReceiveDate is null");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateIsNotNull() {
            addCriterion("preReceiveDate is not null");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateEqualTo(Date value) {
            addCriterionForJDBCDate("preReceiveDate =", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateNotEqualTo(Date value) {
            addCriterionForJDBCDate("preReceiveDate <>", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateGreaterThan(Date value) {
            addCriterionForJDBCDate("preReceiveDate >", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("preReceiveDate >=", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateLessThan(Date value) {
            addCriterionForJDBCDate("preReceiveDate <", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("preReceiveDate <=", value, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateIn(List<Date> values) {
            addCriterionForJDBCDate("preReceiveDate in", values, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateNotIn(List<Date> values) {
            addCriterionForJDBCDate("preReceiveDate not in", values, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("preReceiveDate between", value1, value2, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andPrereceivedateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("preReceiveDate not between", value1, value2, "prereceivedate");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIsNull() {
            addCriterion("associate_no is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIsNotNull() {
            addCriterion("associate_no is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoEqualTo(String value) {
            addCriterion("associate_no =", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotEqualTo(String value) {
            addCriterion("associate_no <>", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThan(String value) {
            addCriterion("associate_no >", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThanOrEqualTo(String value) {
            addCriterion("associate_no >=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThan(String value) {
            addCriterion("associate_no <", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThanOrEqualTo(String value) {
            addCriterion("associate_no <=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLike(String value) {
            addCriterion("associate_no like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotLike(String value) {
            addCriterion("associate_no not like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIn(List<String> values) {
            addCriterion("associate_no in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotIn(List<String> values) {
            addCriterion("associate_no not in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoBetween(String value1, String value2) {
            addCriterion("associate_no between", value1, value2, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotBetween(String value1, String value2) {
            addCriterion("associate_no not between", value1, value2, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIsNull() {
            addCriterion("associate_no_item is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIsNotNull() {
            addCriterion("associate_no_item is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemEqualTo(Integer value) {
            addCriterion("associate_no_item =", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotEqualTo(Integer value) {
            addCriterion("associate_no_item <>", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemGreaterThan(Integer value) {
            addCriterion("associate_no_item >", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("associate_no_item >=", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemLessThan(Integer value) {
            addCriterion("associate_no_item <", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemLessThanOrEqualTo(Integer value) {
            addCriterion("associate_no_item <=", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIn(List<Integer> values) {
            addCriterion("associate_no_item in", values, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotIn(List<Integer> values) {
            addCriterion("associate_no_item not in", values, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_item between", value1, value2, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_item not between", value1, value2, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIsNull() {
            addCriterion("associate_no_splitNo is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIsNotNull() {
            addCriterion("associate_no_splitNo is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoEqualTo(Integer value) {
            addCriterion("associate_no_splitNo =", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotEqualTo(Integer value) {
            addCriterion("associate_no_splitNo <>", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoGreaterThan(Integer value) {
            addCriterion("associate_no_splitNo >", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("associate_no_splitNo >=", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoLessThan(Integer value) {
            addCriterion("associate_no_splitNo <", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoLessThanOrEqualTo(Integer value) {
            addCriterion("associate_no_splitNo <=", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIn(List<Integer> values) {
            addCriterion("associate_no_splitNo in", values, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotIn(List<Integer> values) {
            addCriterion("associate_no_splitNo not in", values, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_splitNo between", value1, value2, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_splitNo not between", value1, value2, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIsNull() {
            addCriterion("sign_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIsNotNull() {
            addCriterion("sign_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeEqualTo(String value) {
            addCriterion("sign_warehouse_code =", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotEqualTo(String value) {
            addCriterion("sign_warehouse_code <>", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeGreaterThan(String value) {
            addCriterion("sign_warehouse_code >", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sign_warehouse_code >=", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLessThan(String value) {
            addCriterion("sign_warehouse_code <", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("sign_warehouse_code <=", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLike(String value) {
            addCriterion("sign_warehouse_code like", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotLike(String value) {
            addCriterion("sign_warehouse_code not like", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIn(List<String> values) {
            addCriterion("sign_warehouse_code in", values, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotIn(List<String> values) {
            addCriterion("sign_warehouse_code not in", values, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeBetween(String value1, String value2) {
            addCriterion("sign_warehouse_code between", value1, value2, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("sign_warehouse_code not between", value1, value2, "signWarehouseCode");
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

        public Criteria andGreencodeIsNull() {
            addCriterion("greenCode is null");
            return (Criteria) this;
        }

        public Criteria andGreencodeIsNotNull() {
            addCriterion("greenCode is not null");
            return (Criteria) this;
        }

        public Criteria andGreencodeEqualTo(String value) {
            addCriterion("greenCode =", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotEqualTo(String value) {
            addCriterion("greenCode <>", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThan(String value) {
            addCriterion("greenCode >", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThanOrEqualTo(String value) {
            addCriterion("greenCode >=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThan(String value) {
            addCriterion("greenCode <", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThanOrEqualTo(String value) {
            addCriterion("greenCode <=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLike(String value) {
            addCriterion("greenCode like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotLike(String value) {
            addCriterion("greenCode not like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeIn(List<String> values) {
            addCriterion("greenCode in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotIn(List<String> values) {
            addCriterion("greenCode not in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeBetween(String value1, String value2) {
            addCriterion("greenCode between", value1, value2, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotBetween(String value1, String value2) {
            addCriterion("greenCode not between", value1, value2, "greencode");
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

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNull() {
            addCriterion("opt_status is null");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNotNull() {
            addCriterion("opt_status is not null");
            return (Criteria) this;
        }

        public Criteria andOptStatusEqualTo(Integer value) {
            addCriterion("opt_status =", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotEqualTo(Integer value) {
            addCriterion("opt_status <>", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThan(Integer value) {
            addCriterion("opt_status >", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("opt_status >=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThan(Integer value) {
            addCriterion("opt_status <", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThanOrEqualTo(Integer value) {
            addCriterion("opt_status <=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusIn(List<Integer> values) {
            addCriterion("opt_status in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotIn(List<Integer> values) {
            addCriterion("opt_status not in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusBetween(Integer value1, Integer value2) {
            addCriterion("opt_status between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("opt_status not between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptTimeIsNull() {
            addCriterion("opt_time is null");
            return (Criteria) this;
        }

        public Criteria andOptTimeIsNotNull() {
            addCriterion("opt_time is not null");
            return (Criteria) this;
        }

        public Criteria andOptTimeEqualTo(Date value) {
            addCriterion("opt_time =", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeNotEqualTo(Date value) {
            addCriterion("opt_time <>", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeGreaterThan(Date value) {
            addCriterion("opt_time >", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("opt_time >=", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeLessThan(Date value) {
            addCriterion("opt_time <", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeLessThanOrEqualTo(Date value) {
            addCriterion("opt_time <=", value, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeIn(List<Date> values) {
            addCriterion("opt_time in", values, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeNotIn(List<Date> values) {
            addCriterion("opt_time not in", values, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeBetween(Date value1, Date value2) {
            addCriterion("opt_time between", value1, value2, "optTime");
            return (Criteria) this;
        }

        public Criteria andOptTimeNotBetween(Date value1, Date value2) {
            addCriterion("opt_time not between", value1, value2, "optTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNull() {
            addCriterion("invoiceId is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNotNull() {
            addCriterion("invoiceId is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidEqualTo(Long value) {
            addCriterion("invoiceId =", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotEqualTo(Long value) {
            addCriterion("invoiceId <>", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThan(Long value) {
            addCriterion("invoiceId >", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThanOrEqualTo(Long value) {
            addCriterion("invoiceId >=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThan(Long value) {
            addCriterion("invoiceId <", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThanOrEqualTo(Long value) {
            addCriterion("invoiceId <=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIn(List<Long> values) {
            addCriterion("invoiceId in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotIn(List<Long> values) {
            addCriterion("invoiceId not in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidBetween(Long value1, Long value2) {
            addCriterion("invoiceId between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotBetween(Long value1, Long value2) {
            addCriterion("invoiceId not between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andDelTimeIsNull() {
            addCriterion("del_time is null");
            return (Criteria) this;
        }

        public Criteria andDelTimeIsNotNull() {
            addCriterion("del_time is not null");
            return (Criteria) this;
        }

        public Criteria andDelTimeEqualTo(Date value) {
            addCriterion("del_time =", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeNotEqualTo(Date value) {
            addCriterion("del_time <>", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeGreaterThan(Date value) {
            addCriterion("del_time >", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("del_time >=", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeLessThan(Date value) {
            addCriterion("del_time <", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeLessThanOrEqualTo(Date value) {
            addCriterion("del_time <=", value, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeIn(List<Date> values) {
            addCriterion("del_time in", values, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeNotIn(List<Date> values) {
            addCriterion("del_time not in", values, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeBetween(Date value1, Date value2) {
            addCriterion("del_time between", value1, value2, "delTime");
            return (Criteria) this;
        }

        public Criteria andDelTimeNotBetween(Date value1, Date value2) {
            addCriterion("del_time not between", value1, value2, "delTime");
            return (Criteria) this;
        }

        public Criteria andPoQtyIsNull() {
            addCriterion("po_qty is null");
            return (Criteria) this;
        }

        public Criteria andPoQtyIsNotNull() {
            addCriterion("po_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPoQtyEqualTo(Integer value) {
            addCriterion("po_qty =", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotEqualTo(Integer value) {
            addCriterion("po_qty <>", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyGreaterThan(Integer value) {
            addCriterion("po_qty >", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("po_qty >=", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyLessThan(Integer value) {
            addCriterion("po_qty <", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyLessThanOrEqualTo(Integer value) {
            addCriterion("po_qty <=", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyIn(List<Integer> values) {
            addCriterion("po_qty in", values, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotIn(List<Integer> values) {
            addCriterion("po_qty not in", values, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyBetween(Integer value1, Integer value2) {
            addCriterion("po_qty between", value1, value2, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("po_qty not between", value1, value2, "poQty");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityIsNull() {
            addCriterion("pre_original_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityIsNotNull() {
            addCriterion("pre_original_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityEqualTo(Integer value) {
            addCriterion("pre_original_quantity =", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityNotEqualTo(Integer value) {
            addCriterion("pre_original_quantity <>", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityGreaterThan(Integer value) {
            addCriterion("pre_original_quantity >", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("pre_original_quantity >=", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityLessThan(Integer value) {
            addCriterion("pre_original_quantity <", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("pre_original_quantity <=", value, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityIn(List<Integer> values) {
            addCriterion("pre_original_quantity in", values, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityNotIn(List<Integer> values) {
            addCriterion("pre_original_quantity not in", values, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityBetween(Integer value1, Integer value2) {
            addCriterion("pre_original_quantity between", value1, value2, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andPreOriginalQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("pre_original_quantity not between", value1, value2, "preOriginalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityIsNull() {
            addCriterion("original_quantity is null");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityIsNotNull() {
            addCriterion("original_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityEqualTo(Integer value) {
            addCriterion("original_quantity =", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityNotEqualTo(Integer value) {
            addCriterion("original_quantity <>", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityGreaterThan(Integer value) {
            addCriterion("original_quantity >", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_quantity >=", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityLessThan(Integer value) {
            addCriterion("original_quantity <", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("original_quantity <=", value, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityIn(List<Integer> values) {
            addCriterion("original_quantity in", values, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityNotIn(List<Integer> values) {
            addCriterion("original_quantity not in", values, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityBetween(Integer value1, Integer value2) {
            addCriterion("original_quantity between", value1, value2, "originalQuantity");
            return (Criteria) this;
        }

        public Criteria andOriginalQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("original_quantity not between", value1, value2, "originalQuantity");
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

        public Criteria andAssociateNoSplitnoFlagIsNull() {
            addCriterion("associate_no_splitNo_flag is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagIsNotNull() {
            addCriterion("associate_no_splitNo_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagEqualTo(Boolean value) {
            addCriterion("associate_no_splitNo_flag =", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagNotEqualTo(Boolean value) {
            addCriterion("associate_no_splitNo_flag <>", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagGreaterThan(Boolean value) {
            addCriterion("associate_no_splitNo_flag >", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("associate_no_splitNo_flag >=", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagLessThan(Boolean value) {
            addCriterion("associate_no_splitNo_flag <", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("associate_no_splitNo_flag <=", value, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagIn(List<Boolean> values) {
            addCriterion("associate_no_splitNo_flag in", values, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagNotIn(List<Boolean> values) {
            addCriterion("associate_no_splitNo_flag not in", values, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("associate_no_splitNo_flag between", value1, value2, "associateNoSplitnoFlag");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("associate_no_splitNo_flag not between", value1, value2, "associateNoSplitnoFlag");
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
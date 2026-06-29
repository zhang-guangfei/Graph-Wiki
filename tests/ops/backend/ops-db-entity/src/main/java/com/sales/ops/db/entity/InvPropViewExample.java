package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class InvPropViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvPropViewExample() {
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

        public Criteria andInventoryTableTypeIsNull() {
            addCriterion("inventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIsNotNull() {
            addCriterion("inventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeEqualTo(String value) {
            addCriterion("inventory_table_type =", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotEqualTo(String value) {
            addCriterion("inventory_table_type <>", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThan(String value) {
            addCriterion("inventory_table_type >", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_table_type >=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThan(String value) {
            addCriterion("inventory_table_type <", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("inventory_table_type <=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLike(String value) {
            addCriterion("inventory_table_type like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotLike(String value) {
            addCriterion("inventory_table_type not like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIn(List<String> values) {
            addCriterion("inventory_table_type in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotIn(List<String> values) {
            addCriterion("inventory_table_type not in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeBetween(String value1, String value2) {
            addCriterion("inventory_table_type between", value1, value2, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("inventory_table_type not between", value1, value2, "inventoryTableType");
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

        public Criteria andInventoryTypeCodeIsNull() {
            addCriterion("inventory_type_code is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIsNotNull() {
            addCriterion("inventory_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeEqualTo(String value) {
            addCriterion("inventory_type_code =", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotEqualTo(String value) {
            addCriterion("inventory_type_code <>", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThan(String value) {
            addCriterion("inventory_type_code >", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_type_code >=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThan(String value) {
            addCriterion("inventory_type_code <", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("inventory_type_code <=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLike(String value) {
            addCriterion("inventory_type_code like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotLike(String value) {
            addCriterion("inventory_type_code not like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIn(List<String> values) {
            addCriterion("inventory_type_code in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotIn(List<String> values) {
            addCriterion("inventory_type_code not in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeBetween(String value1, String value2) {
            addCriterion("inventory_type_code between", value1, value2, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotBetween(String value1, String value2) {
            addCriterion("inventory_type_code not between", value1, value2, "inventoryTypeCode");
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
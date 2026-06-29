package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderInventoryRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderInventoryRelationExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNull() {
            addCriterion("order_item is null");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNotNull() {
            addCriterion("order_item is not null");
            return (Criteria) this;
        }

        public Criteria andOrderItemEqualTo(String value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(String value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(String value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(String value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(String value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLike(String value) {
            addCriterion("order_item like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotLike(String value) {
            addCriterion("order_item not like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<String> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<String> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(String value1, String value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(String value1, String value2) {
            addCriterion("order_item not between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andDoKeyIsNull() {
            addCriterion("do_key is null");
            return (Criteria) this;
        }

        public Criteria andDoKeyIsNotNull() {
            addCriterion("do_key is not null");
            return (Criteria) this;
        }

        public Criteria andDoKeyEqualTo(Long value) {
            addCriterion("do_key =", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyNotEqualTo(Long value) {
            addCriterion("do_key <>", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyGreaterThan(Long value) {
            addCriterion("do_key >", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("do_key >=", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyLessThan(Long value) {
            addCriterion("do_key <", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyLessThanOrEqualTo(Long value) {
            addCriterion("do_key <=", value, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyIn(List<Long> values) {
            addCriterion("do_key in", values, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyNotIn(List<Long> values) {
            addCriterion("do_key not in", values, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyBetween(Long value1, Long value2) {
            addCriterion("do_key between", value1, value2, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoKeyNotBetween(Long value1, Long value2) {
            addCriterion("do_key not between", value1, value2, "doKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyIsNull() {
            addCriterion("do_item_key is null");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyIsNotNull() {
            addCriterion("do_item_key is not null");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyEqualTo(Long value) {
            addCriterion("do_item_key =", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyNotEqualTo(Long value) {
            addCriterion("do_item_key <>", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyGreaterThan(Long value) {
            addCriterion("do_item_key >", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("do_item_key >=", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyLessThan(Long value) {
            addCriterion("do_item_key <", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyLessThanOrEqualTo(Long value) {
            addCriterion("do_item_key <=", value, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyIn(List<Long> values) {
            addCriterion("do_item_key in", values, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyNotIn(List<Long> values) {
            addCriterion("do_item_key not in", values, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyBetween(Long value1, Long value2) {
            addCriterion("do_item_key between", value1, value2, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemKeyNotBetween(Long value1, Long value2) {
            addCriterion("do_item_key not between", value1, value2, "doItemKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyIsNull() {
            addCriterion("do_item_inv_key is null");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyIsNotNull() {
            addCriterion("do_item_inv_key is not null");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyEqualTo(Long value) {
            addCriterion("do_item_inv_key =", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyNotEqualTo(Long value) {
            addCriterion("do_item_inv_key <>", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyGreaterThan(Long value) {
            addCriterion("do_item_inv_key >", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("do_item_inv_key >=", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyLessThan(Long value) {
            addCriterion("do_item_inv_key <", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyLessThanOrEqualTo(Long value) {
            addCriterion("do_item_inv_key <=", value, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyIn(List<Long> values) {
            addCriterion("do_item_inv_key in", values, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyNotIn(List<Long> values) {
            addCriterion("do_item_inv_key not in", values, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyBetween(Long value1, Long value2) {
            addCriterion("do_item_inv_key between", value1, value2, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoItemInvKeyNotBetween(Long value1, Long value2) {
            addCriterion("do_item_inv_key not between", value1, value2, "doItemInvKey");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNull() {
            addCriterion("do_id is null");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNotNull() {
            addCriterion("do_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoIdEqualTo(String value) {
            addCriterion("do_id =", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotEqualTo(String value) {
            addCriterion("do_id <>", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThan(String value) {
            addCriterion("do_id >", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThanOrEqualTo(String value) {
            addCriterion("do_id >=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThan(String value) {
            addCriterion("do_id <", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThanOrEqualTo(String value) {
            addCriterion("do_id <=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLike(String value) {
            addCriterion("do_id like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotLike(String value) {
            addCriterion("do_id not like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdIn(List<String> values) {
            addCriterion("do_id in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotIn(List<String> values) {
            addCriterion("do_id not in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdBetween(String value1, String value2) {
            addCriterion("do_id between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotBetween(String value1, String value2) {
            addCriterion("do_id not between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoItemIsNull() {
            addCriterion("do_item is null");
            return (Criteria) this;
        }

        public Criteria andDoItemIsNotNull() {
            addCriterion("do_item is not null");
            return (Criteria) this;
        }

        public Criteria andDoItemEqualTo(Integer value) {
            addCriterion("do_item =", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemNotEqualTo(Integer value) {
            addCriterion("do_item <>", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemGreaterThan(Integer value) {
            addCriterion("do_item >", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("do_item >=", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemLessThan(Integer value) {
            addCriterion("do_item <", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemLessThanOrEqualTo(Integer value) {
            addCriterion("do_item <=", value, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemIn(List<Integer> values) {
            addCriterion("do_item in", values, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemNotIn(List<Integer> values) {
            addCriterion("do_item not in", values, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemBetween(Integer value1, Integer value2) {
            addCriterion("do_item between", value1, value2, "doItem");
            return (Criteria) this;
        }

        public Criteria andDoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("do_item not between", value1, value2, "doItem");
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
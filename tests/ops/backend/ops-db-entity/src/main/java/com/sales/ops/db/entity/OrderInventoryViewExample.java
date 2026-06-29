package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderInventoryViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderInventoryViewExample() {
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

        public Criteria andGatherWarehouseCodeIsNull() {
            addCriterion("gather_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIsNotNull() {
            addCriterion("gather_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeEqualTo(String value) {
            addCriterion("gather_warehouse_code =", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotEqualTo(String value) {
            addCriterion("gather_warehouse_code <>", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThan(String value) {
            addCriterion("gather_warehouse_code >", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code >=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThan(String value) {
            addCriterion("gather_warehouse_code <", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code <=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLike(String value) {
            addCriterion("gather_warehouse_code like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotLike(String value) {
            addCriterion("gather_warehouse_code not like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIn(List<String> values) {
            addCriterion("gather_warehouse_code in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotIn(List<String> values) {
            addCriterion("gather_warehouse_code not in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code not between", value1, value2, "gatherWarehouseCode");
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

        public Criteria andUseQtyIsNull() {
            addCriterion("use_qty is null");
            return (Criteria) this;
        }

        public Criteria andUseQtyIsNotNull() {
            addCriterion("use_qty is not null");
            return (Criteria) this;
        }

        public Criteria andUseQtyEqualTo(Integer value) {
            addCriterion("use_qty =", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotEqualTo(Integer value) {
            addCriterion("use_qty <>", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThan(Integer value) {
            addCriterion("use_qty >", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("use_qty >=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThan(Integer value) {
            addCriterion("use_qty <", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThanOrEqualTo(Integer value) {
            addCriterion("use_qty <=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyIn(List<Integer> values) {
            addCriterion("use_qty in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotIn(List<Integer> values) {
            addCriterion("use_qty not in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyBetween(Integer value1, Integer value2) {
            addCriterion("use_qty between", value1, value2, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("use_qty not between", value1, value2, "useQty");
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

        public Criteria andDoStateIsNull() {
            addCriterion("do_state is null");
            return (Criteria) this;
        }

        public Criteria andDoStateIsNotNull() {
            addCriterion("do_state is not null");
            return (Criteria) this;
        }

        public Criteria andDoStateEqualTo(String value) {
            addCriterion("do_state =", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotEqualTo(String value) {
            addCriterion("do_state <>", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateGreaterThan(String value) {
            addCriterion("do_state >", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateGreaterThanOrEqualTo(String value) {
            addCriterion("do_state >=", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLessThan(String value) {
            addCriterion("do_state <", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLessThanOrEqualTo(String value) {
            addCriterion("do_state <=", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLike(String value) {
            addCriterion("do_state like", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotLike(String value) {
            addCriterion("do_state not like", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateIn(List<String> values) {
            addCriterion("do_state in", values, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotIn(List<String> values) {
            addCriterion("do_state not in", values, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateBetween(String value1, String value2) {
            addCriterion("do_state between", value1, value2, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotBetween(String value1, String value2) {
            addCriterion("do_state not between", value1, value2, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIsNull() {
            addCriterion("do_state_detail is null");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIsNotNull() {
            addCriterion("do_state_detail is not null");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailEqualTo(String value) {
            addCriterion("do_state_detail =", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotEqualTo(String value) {
            addCriterion("do_state_detail <>", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailGreaterThan(String value) {
            addCriterion("do_state_detail >", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailGreaterThanOrEqualTo(String value) {
            addCriterion("do_state_detail >=", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLessThan(String value) {
            addCriterion("do_state_detail <", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLessThanOrEqualTo(String value) {
            addCriterion("do_state_detail <=", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLike(String value) {
            addCriterion("do_state_detail like", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotLike(String value) {
            addCriterion("do_state_detail not like", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIn(List<String> values) {
            addCriterion("do_state_detail in", values, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotIn(List<String> values) {
            addCriterion("do_state_detail not in", values, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailBetween(String value1, String value2) {
            addCriterion("do_state_detail between", value1, value2, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotBetween(String value1, String value2) {
            addCriterion("do_state_detail not between", value1, value2, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andHaveQtyIsNull() {
            addCriterion("have_qty is null");
            return (Criteria) this;
        }

        public Criteria andHaveQtyIsNotNull() {
            addCriterion("have_qty is not null");
            return (Criteria) this;
        }

        public Criteria andHaveQtyEqualTo(Integer value) {
            addCriterion("have_qty =", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyNotEqualTo(Integer value) {
            addCriterion("have_qty <>", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyGreaterThan(Integer value) {
            addCriterion("have_qty >", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("have_qty >=", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyLessThan(Integer value) {
            addCriterion("have_qty <", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyLessThanOrEqualTo(Integer value) {
            addCriterion("have_qty <=", value, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyIn(List<Integer> values) {
            addCriterion("have_qty in", values, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyNotIn(List<Integer> values) {
            addCriterion("have_qty not in", values, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyBetween(Integer value1, Integer value2) {
            addCriterion("have_qty between", value1, value2, "haveQty");
            return (Criteria) this;
        }

        public Criteria andHaveQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("have_qty not between", value1, value2, "haveQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNull() {
            addCriterion("out_qty is null");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNotNull() {
            addCriterion("out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOutQtyEqualTo(Integer value) {
            addCriterion("out_qty =", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotEqualTo(Integer value) {
            addCriterion("out_qty <>", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThan(Integer value) {
            addCriterion("out_qty >", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_qty >=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThan(Integer value) {
            addCriterion("out_qty <", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThanOrEqualTo(Integer value) {
            addCriterion("out_qty <=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyIn(List<Integer> values) {
            addCriterion("out_qty in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotIn(List<Integer> values) {
            addCriterion("out_qty not in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyBetween(Integer value1, Integer value2) {
            addCriterion("out_qty between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("out_qty not between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNull() {
            addCriterion("wait_type is null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNotNull() {
            addCriterion("wait_type is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeEqualTo(String value) {
            addCriterion("wait_type =", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotEqualTo(String value) {
            addCriterion("wait_type <>", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThan(String value) {
            addCriterion("wait_type >", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("wait_type >=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThan(String value) {
            addCriterion("wait_type <", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThanOrEqualTo(String value) {
            addCriterion("wait_type <=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLike(String value) {
            addCriterion("wait_type like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotLike(String value) {
            addCriterion("wait_type not like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIn(List<String> values) {
            addCriterion("wait_type in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotIn(List<String> values) {
            addCriterion("wait_type not in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeBetween(String value1, String value2) {
            addCriterion("wait_type between", value1, value2, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotBetween(String value1, String value2) {
            addCriterion("wait_type not between", value1, value2, "waitType");
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

        public Criteria andAssociateNoIsNull() {
            addCriterion("Associate_no is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIsNotNull() {
            addCriterion("Associate_no is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoEqualTo(String value) {
            addCriterion("Associate_no =", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotEqualTo(String value) {
            addCriterion("Associate_no <>", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThan(String value) {
            addCriterion("Associate_no >", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThanOrEqualTo(String value) {
            addCriterion("Associate_no >=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThan(String value) {
            addCriterion("Associate_no <", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThanOrEqualTo(String value) {
            addCriterion("Associate_no <=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLike(String value) {
            addCriterion("Associate_no like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotLike(String value) {
            addCriterion("Associate_no not like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIn(List<String> values) {
            addCriterion("Associate_no in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotIn(List<String> values) {
            addCriterion("Associate_no not in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoBetween(String value1, String value2) {
            addCriterion("Associate_no between", value1, value2, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotBetween(String value1, String value2) {
            addCriterion("Associate_no not between", value1, value2, "associateNo");
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
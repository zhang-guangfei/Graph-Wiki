package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class TempReplaceIdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TempReplaceIdExample() {
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

        public Criteria andPcoIdIsNull() {
            addCriterion("pco_id is null");
            return (Criteria) this;
        }

        public Criteria andPcoIdIsNotNull() {
            addCriterion("pco_id is not null");
            return (Criteria) this;
        }

        public Criteria andPcoIdEqualTo(String value) {
            addCriterion("pco_id =", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotEqualTo(String value) {
            addCriterion("pco_id <>", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThan(String value) {
            addCriterion("pco_id >", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThanOrEqualTo(String value) {
            addCriterion("pco_id >=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThan(String value) {
            addCriterion("pco_id <", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThanOrEqualTo(String value) {
            addCriterion("pco_id <=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLike(String value) {
            addCriterion("pco_id like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotLike(String value) {
            addCriterion("pco_id not like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdIn(List<String> values) {
            addCriterion("pco_id in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotIn(List<String> values) {
            addCriterion("pco_id not in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdBetween(String value1, String value2) {
            addCriterion("pco_id between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotBetween(String value1, String value2) {
            addCriterion("pco_id not between", value1, value2, "pcoId");
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

        public Criteria andPcoItemIsNull() {
            addCriterion("pco_item is null");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNotNull() {
            addCriterion("pco_item is not null");
            return (Criteria) this;
        }

        public Criteria andPcoItemEqualTo(Integer value) {
            addCriterion("pco_item =", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotEqualTo(Integer value) {
            addCriterion("pco_item <>", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThan(Integer value) {
            addCriterion("pco_item >", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_item >=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThan(Integer value) {
            addCriterion("pco_item <", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThanOrEqualTo(Integer value) {
            addCriterion("pco_item <=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemIn(List<Integer> values) {
            addCriterion("pco_item in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotIn(List<Integer> values) {
            addCriterion("pco_item not in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemBetween(Integer value1, Integer value2) {
            addCriterion("pco_item between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_item not between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNull() {
            addCriterion("sub_modelno is null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNotNull() {
            addCriterion("sub_modelno is not null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoEqualTo(String value) {
            addCriterion("sub_modelno =", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotEqualTo(String value) {
            addCriterion("sub_modelno <>", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThan(String value) {
            addCriterion("sub_modelno >", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("sub_modelno >=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThan(String value) {
            addCriterion("sub_modelno <", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThanOrEqualTo(String value) {
            addCriterion("sub_modelno <=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLike(String value) {
            addCriterion("sub_modelno like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotLike(String value) {
            addCriterion("sub_modelno not like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIn(List<String> values) {
            addCriterion("sub_modelno in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotIn(List<String> values) {
            addCriterion("sub_modelno not in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoBetween(String value1, String value2) {
            addCriterion("sub_modelno between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotBetween(String value1, String value2) {
            addCriterion("sub_modelno not between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNull() {
            addCriterion("sub_qty is null");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNotNull() {
            addCriterion("sub_qty is not null");
            return (Criteria) this;
        }

        public Criteria andSubQtyEqualTo(Integer value) {
            addCriterion("sub_qty =", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotEqualTo(Integer value) {
            addCriterion("sub_qty <>", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThan(Integer value) {
            addCriterion("sub_qty >", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_qty >=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThan(Integer value) {
            addCriterion("sub_qty <", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThanOrEqualTo(Integer value) {
            addCriterion("sub_qty <=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyIn(List<Integer> values) {
            addCriterion("sub_qty in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotIn(List<Integer> values) {
            addCriterion("sub_qty not in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty between", value1, value2, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty not between", value1, value2, "subQty");
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

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
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
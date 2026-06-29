package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderStatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderStatusExample() {
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

        public Criteria andOrderItemEqualTo(Integer value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(Integer value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(Integer value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(Integer value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(Integer value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<Integer> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<Integer> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(Integer value1, Integer value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(Integer value1, Integer value2) {
            addCriterion("order_item not between", value1, value2, "orderItem");
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

        public Criteria andSplitTypeIsNull() {
            addCriterion("split_type is null");
            return (Criteria) this;
        }

        public Criteria andSplitTypeIsNotNull() {
            addCriterion("split_type is not null");
            return (Criteria) this;
        }

        public Criteria andSplitTypeEqualTo(String value) {
            addCriterion("split_type =", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotEqualTo(String value) {
            addCriterion("split_type <>", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeGreaterThan(String value) {
            addCriterion("split_type >", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("split_type >=", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLessThan(String value) {
            addCriterion("split_type <", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLessThanOrEqualTo(String value) {
            addCriterion("split_type <=", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLike(String value) {
            addCriterion("split_type like", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotLike(String value) {
            addCriterion("split_type not like", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeIn(List<String> values) {
            addCriterion("split_type in", values, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotIn(List<String> values) {
            addCriterion("split_type not in", values, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeBetween(String value1, String value2) {
            addCriterion("split_type between", value1, value2, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotBetween(String value1, String value2) {
            addCriterion("split_type not between", value1, value2, "splitType");
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

        public Criteria andWmOrderIdIsNull() {
            addCriterion("wm_order_id is null");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdIsNotNull() {
            addCriterion("wm_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdEqualTo(String value) {
            addCriterion("wm_order_id =", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdNotEqualTo(String value) {
            addCriterion("wm_order_id <>", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdGreaterThan(String value) {
            addCriterion("wm_order_id >", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("wm_order_id >=", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdLessThan(String value) {
            addCriterion("wm_order_id <", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdLessThanOrEqualTo(String value) {
            addCriterion("wm_order_id <=", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdLike(String value) {
            addCriterion("wm_order_id like", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdNotLike(String value) {
            addCriterion("wm_order_id not like", value, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdIn(List<String> values) {
            addCriterion("wm_order_id in", values, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdNotIn(List<String> values) {
            addCriterion("wm_order_id not in", values, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdBetween(String value1, String value2) {
            addCriterion("wm_order_id between", value1, value2, "wmOrderId");
            return (Criteria) this;
        }

        public Criteria andWmOrderIdNotBetween(String value1, String value2) {
            addCriterion("wm_order_id not between", value1, value2, "wmOrderId");
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

        public Criteria andQtyInIsNull() {
            addCriterion("qty_in is null");
            return (Criteria) this;
        }

        public Criteria andQtyInIsNotNull() {
            addCriterion("qty_in is not null");
            return (Criteria) this;
        }

        public Criteria andQtyInEqualTo(Integer value) {
            addCriterion("qty_in =", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInNotEqualTo(Integer value) {
            addCriterion("qty_in <>", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInGreaterThan(Integer value) {
            addCriterion("qty_in >", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_in >=", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInLessThan(Integer value) {
            addCriterion("qty_in <", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInLessThanOrEqualTo(Integer value) {
            addCriterion("qty_in <=", value, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInIn(List<Integer> values) {
            addCriterion("qty_in in", values, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInNotIn(List<Integer> values) {
            addCriterion("qty_in not in", values, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInBetween(Integer value1, Integer value2) {
            addCriterion("qty_in between", value1, value2, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyInNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_in not between", value1, value2, "qtyIn");
            return (Criteria) this;
        }

        public Criteria andQtyOutIsNull() {
            addCriterion("qty_out is null");
            return (Criteria) this;
        }

        public Criteria andQtyOutIsNotNull() {
            addCriterion("qty_out is not null");
            return (Criteria) this;
        }

        public Criteria andQtyOutEqualTo(Integer value) {
            addCriterion("qty_out =", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutNotEqualTo(Integer value) {
            addCriterion("qty_out <>", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutGreaterThan(Integer value) {
            addCriterion("qty_out >", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_out >=", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutLessThan(Integer value) {
            addCriterion("qty_out <", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutLessThanOrEqualTo(Integer value) {
            addCriterion("qty_out <=", value, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutIn(List<Integer> values) {
            addCriterion("qty_out in", values, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutNotIn(List<Integer> values) {
            addCriterion("qty_out not in", values, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutBetween(Integer value1, Integer value2) {
            addCriterion("qty_out between", value1, value2, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andQtyOutNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_out not between", value1, value2, "qtyOut");
            return (Criteria) this;
        }

        public Criteria andWlDateIsNull() {
            addCriterion("wl_date is null");
            return (Criteria) this;
        }

        public Criteria andWlDateIsNotNull() {
            addCriterion("wl_date is not null");
            return (Criteria) this;
        }

        public Criteria andWlDateEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date =", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <>", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThan(Date value) {
            addCriterionForJDBCDate("wl_date >", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date >=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThan(Date value) {
            addCriterionForJDBCDate("wl_date <", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date not in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date between", value1, value2, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date not between", value1, value2, "wlDate");
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
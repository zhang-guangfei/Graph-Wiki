package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoUnusualOrderLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoUnusualOrderLogExample() {
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

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(String value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(String value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(String value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(String value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(String value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLike(String value) {
            addCriterion("source_id like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotLike(String value) {
            addCriterion("source_id not like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<String> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<String> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(String value1, String value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(String value1, String value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andPonoIsNull() {
            addCriterion("pono is null");
            return (Criteria) this;
        }

        public Criteria andPonoIsNotNull() {
            addCriterion("pono is not null");
            return (Criteria) this;
        }

        public Criteria andPonoEqualTo(String value) {
            addCriterion("pono =", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotEqualTo(String value) {
            addCriterion("pono <>", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThan(String value) {
            addCriterion("pono >", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThanOrEqualTo(String value) {
            addCriterion("pono >=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThan(String value) {
            addCriterion("pono <", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThanOrEqualTo(String value) {
            addCriterion("pono <=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLike(String value) {
            addCriterion("pono like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotLike(String value) {
            addCriterion("pono not like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoIn(List<String> values) {
            addCriterion("pono in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotIn(List<String> values) {
            addCriterion("pono not in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoBetween(String value1, String value2) {
            addCriterion("pono between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotBetween(String value1, String value2) {
            addCriterion("pono not between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andLineItemIsNull() {
            addCriterion("line_item is null");
            return (Criteria) this;
        }

        public Criteria andLineItemIsNotNull() {
            addCriterion("line_item is not null");
            return (Criteria) this;
        }

        public Criteria andLineItemEqualTo(Integer value) {
            addCriterion("line_item =", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemNotEqualTo(Integer value) {
            addCriterion("line_item <>", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemGreaterThan(Integer value) {
            addCriterion("line_item >", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_item >=", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemLessThan(Integer value) {
            addCriterion("line_item <", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemLessThanOrEqualTo(Integer value) {
            addCriterion("line_item <=", value, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemIn(List<Integer> values) {
            addCriterion("line_item in", values, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemNotIn(List<Integer> values) {
            addCriterion("line_item not in", values, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemBetween(Integer value1, Integer value2) {
            addCriterion("line_item between", value1, value2, "lineItem");
            return (Criteria) this;
        }

        public Criteria andLineItemNotBetween(Integer value1, Integer value2) {
            addCriterion("line_item not between", value1, value2, "lineItem");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIsNull() {
            addCriterion("supplier_order_no is null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIsNotNull() {
            addCriterion("supplier_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoEqualTo(String value) {
            addCriterion("supplier_order_no =", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotEqualTo(String value) {
            addCriterion("supplier_order_no <>", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoGreaterThan(String value) {
            addCriterion("supplier_order_no >", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_order_no >=", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLessThan(String value) {
            addCriterion("supplier_order_no <", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLessThanOrEqualTo(String value) {
            addCriterion("supplier_order_no <=", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLike(String value) {
            addCriterion("supplier_order_no like", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotLike(String value) {
            addCriterion("supplier_order_no not like", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIn(List<String> values) {
            addCriterion("supplier_order_no in", values, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotIn(List<String> values) {
            addCriterion("supplier_order_no not in", values, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoBetween(String value1, String value2) {
            addCriterion("supplier_order_no between", value1, value2, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotBetween(String value1, String value2) {
            addCriterion("supplier_order_no not between", value1, value2, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoIsNull() {
            addCriterion("supplier_line_item_no is null");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoIsNotNull() {
            addCriterion("supplier_line_item_no is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoEqualTo(String value) {
            addCriterion("supplier_line_item_no =", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoNotEqualTo(String value) {
            addCriterion("supplier_line_item_no <>", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoGreaterThan(String value) {
            addCriterion("supplier_line_item_no >", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_line_item_no >=", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoLessThan(String value) {
            addCriterion("supplier_line_item_no <", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoLessThanOrEqualTo(String value) {
            addCriterion("supplier_line_item_no <=", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoLike(String value) {
            addCriterion("supplier_line_item_no like", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoNotLike(String value) {
            addCriterion("supplier_line_item_no not like", value, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoIn(List<String> values) {
            addCriterion("supplier_line_item_no in", values, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoNotIn(List<String> values) {
            addCriterion("supplier_line_item_no not in", values, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoBetween(String value1, String value2) {
            addCriterion("supplier_line_item_no between", value1, value2, "supplierLineItemNo");
            return (Criteria) this;
        }

        public Criteria andSupplierLineItemNoNotBetween(String value1, String value2) {
            addCriterion("supplier_line_item_no not between", value1, value2, "supplierLineItemNo");
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

        public Criteria andOrderUnusualReasonCodeIsNull() {
            addCriterion("order_unusual_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeIsNotNull() {
            addCriterion("order_unusual_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeEqualTo(String value) {
            addCriterion("order_unusual_reason_code =", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeNotEqualTo(String value) {
            addCriterion("order_unusual_reason_code <>", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeGreaterThan(String value) {
            addCriterion("order_unusual_reason_code >", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_unusual_reason_code >=", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeLessThan(String value) {
            addCriterion("order_unusual_reason_code <", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("order_unusual_reason_code <=", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeLike(String value) {
            addCriterion("order_unusual_reason_code like", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeNotLike(String value) {
            addCriterion("order_unusual_reason_code not like", value, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeIn(List<String> values) {
            addCriterion("order_unusual_reason_code in", values, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeNotIn(List<String> values) {
            addCriterion("order_unusual_reason_code not in", values, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeBetween(String value1, String value2) {
            addCriterion("order_unusual_reason_code between", value1, value2, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonCodeNotBetween(String value1, String value2) {
            addCriterion("order_unusual_reason_code not between", value1, value2, "orderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeIsNull() {
            addCriterion("sr_order_unusual_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeIsNotNull() {
            addCriterion("sr_order_unusual_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeEqualTo(String value) {
            addCriterion("sr_order_unusual_reason_code =", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeNotEqualTo(String value) {
            addCriterion("sr_order_unusual_reason_code <>", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeGreaterThan(String value) {
            addCriterion("sr_order_unusual_reason_code >", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sr_order_unusual_reason_code >=", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeLessThan(String value) {
            addCriterion("sr_order_unusual_reason_code <", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("sr_order_unusual_reason_code <=", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeLike(String value) {
            addCriterion("sr_order_unusual_reason_code like", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeNotLike(String value) {
            addCriterion("sr_order_unusual_reason_code not like", value, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeIn(List<String> values) {
            addCriterion("sr_order_unusual_reason_code in", values, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeNotIn(List<String> values) {
            addCriterion("sr_order_unusual_reason_code not in", values, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeBetween(String value1, String value2) {
            addCriterion("sr_order_unusual_reason_code between", value1, value2, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andSrOrderUnusualReasonCodeNotBetween(String value1, String value2) {
            addCriterion("sr_order_unusual_reason_code not between", value1, value2, "srOrderUnusualReasonCode");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonIsNull() {
            addCriterion("order_unusual_reason is null");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonIsNotNull() {
            addCriterion("order_unusual_reason is not null");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonEqualTo(String value) {
            addCriterion("order_unusual_reason =", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonNotEqualTo(String value) {
            addCriterion("order_unusual_reason <>", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonGreaterThan(String value) {
            addCriterion("order_unusual_reason >", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonGreaterThanOrEqualTo(String value) {
            addCriterion("order_unusual_reason >=", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonLessThan(String value) {
            addCriterion("order_unusual_reason <", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonLessThanOrEqualTo(String value) {
            addCriterion("order_unusual_reason <=", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonLike(String value) {
            addCriterion("order_unusual_reason like", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonNotLike(String value) {
            addCriterion("order_unusual_reason not like", value, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonIn(List<String> values) {
            addCriterion("order_unusual_reason in", values, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonNotIn(List<String> values) {
            addCriterion("order_unusual_reason not in", values, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonBetween(String value1, String value2) {
            addCriterion("order_unusual_reason between", value1, value2, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andOrderUnusualReasonNotBetween(String value1, String value2) {
            addCriterion("order_unusual_reason not between", value1, value2, "orderUnusualReason");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeIsNull() {
            addCriterion("supplier_system_exec_time is null");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeIsNotNull() {
            addCriterion("supplier_system_exec_time is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeEqualTo(Date value) {
            addCriterion("supplier_system_exec_time =", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeNotEqualTo(Date value) {
            addCriterion("supplier_system_exec_time <>", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeGreaterThan(Date value) {
            addCriterion("supplier_system_exec_time >", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("supplier_system_exec_time >=", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeLessThan(Date value) {
            addCriterion("supplier_system_exec_time <", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeLessThanOrEqualTo(Date value) {
            addCriterion("supplier_system_exec_time <=", value, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeIn(List<Date> values) {
            addCriterion("supplier_system_exec_time in", values, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeNotIn(List<Date> values) {
            addCriterion("supplier_system_exec_time not in", values, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeBetween(Date value1, Date value2) {
            addCriterion("supplier_system_exec_time between", value1, value2, "supplierSystemExecTime");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemExecTimeNotBetween(Date value1, Date value2) {
            addCriterion("supplier_system_exec_time not between", value1, value2, "supplierSystemExecTime");
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

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNull() {
            addCriterion("unusual_type is null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNotNull() {
            addCriterion("unusual_type is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeEqualTo(Integer value) {
            addCriterion("unusual_type =", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotEqualTo(Integer value) {
            addCriterion("unusual_type <>", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThan(Integer value) {
            addCriterion("unusual_type >", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("unusual_type >=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThan(Integer value) {
            addCriterion("unusual_type <", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThanOrEqualTo(Integer value) {
            addCriterion("unusual_type <=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIn(List<Integer> values) {
            addCriterion("unusual_type in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotIn(List<Integer> values) {
            addCriterion("unusual_type not in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeBetween(Integer value1, Integer value2) {
            addCriterion("unusual_type between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("unusual_type not between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentIsNull() {
            addCriterion("src_reply_content is null");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentIsNotNull() {
            addCriterion("src_reply_content is not null");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentEqualTo(String value) {
            addCriterion("src_reply_content =", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentNotEqualTo(String value) {
            addCriterion("src_reply_content <>", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentGreaterThan(String value) {
            addCriterion("src_reply_content >", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentGreaterThanOrEqualTo(String value) {
            addCriterion("src_reply_content >=", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentLessThan(String value) {
            addCriterion("src_reply_content <", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentLessThanOrEqualTo(String value) {
            addCriterion("src_reply_content <=", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentLike(String value) {
            addCriterion("src_reply_content like", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentNotLike(String value) {
            addCriterion("src_reply_content not like", value, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentIn(List<String> values) {
            addCriterion("src_reply_content in", values, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentNotIn(List<String> values) {
            addCriterion("src_reply_content not in", values, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentBetween(String value1, String value2) {
            addCriterion("src_reply_content between", value1, value2, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyContentNotBetween(String value1, String value2) {
            addCriterion("src_reply_content not between", value1, value2, "srcReplyContent");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateIsNull() {
            addCriterion("src_reply_date is null");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateIsNotNull() {
            addCriterion("src_reply_date is not null");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateEqualTo(String value) {
            addCriterion("src_reply_date =", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateNotEqualTo(String value) {
            addCriterion("src_reply_date <>", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateGreaterThan(String value) {
            addCriterion("src_reply_date >", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateGreaterThanOrEqualTo(String value) {
            addCriterion("src_reply_date >=", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateLessThan(String value) {
            addCriterion("src_reply_date <", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateLessThanOrEqualTo(String value) {
            addCriterion("src_reply_date <=", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateLike(String value) {
            addCriterion("src_reply_date like", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateNotLike(String value) {
            addCriterion("src_reply_date not like", value, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateIn(List<String> values) {
            addCriterion("src_reply_date in", values, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateNotIn(List<String> values) {
            addCriterion("src_reply_date not in", values, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateBetween(String value1, String value2) {
            addCriterion("src_reply_date between", value1, value2, "srcReplyDate");
            return (Criteria) this;
        }

        public Criteria andSrcReplyDateNotBetween(String value1, String value2) {
            addCriterion("src_reply_date not between", value1, value2, "srcReplyDate");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpInvoiceDetailTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ImpInvoiceDetailTempExample() {
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

        public Criteria andPonoIsNull() {
            addCriterion("poNo is null");
            return (Criteria) this;
        }

        public Criteria andPonoIsNotNull() {
            addCriterion("poNo is not null");
            return (Criteria) this;
        }

        public Criteria andPonoEqualTo(String value) {
            addCriterion("poNo =", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotEqualTo(String value) {
            addCriterion("poNo <>", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThan(String value) {
            addCriterion("poNo >", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThanOrEqualTo(String value) {
            addCriterion("poNo >=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThan(String value) {
            addCriterion("poNo <", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThanOrEqualTo(String value) {
            addCriterion("poNo <=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLike(String value) {
            addCriterion("poNo like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotLike(String value) {
            addCriterion("poNo not like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoIn(List<String> values) {
            addCriterion("poNo in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotIn(List<String> values) {
            addCriterion("poNo not in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoBetween(String value1, String value2) {
            addCriterion("poNo between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotBetween(String value1, String value2) {
            addCriterion("poNo not between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andPoitemnoIsNull() {
            addCriterion("poItemNo is null");
            return (Criteria) this;
        }

        public Criteria andPoitemnoIsNotNull() {
            addCriterion("poItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andPoitemnoEqualTo(Integer value) {
            addCriterion("poItemNo =", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoNotEqualTo(Integer value) {
            addCriterion("poItemNo <>", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoGreaterThan(Integer value) {
            addCriterion("poItemNo >", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("poItemNo >=", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoLessThan(Integer value) {
            addCriterion("poItemNo <", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoLessThanOrEqualTo(Integer value) {
            addCriterion("poItemNo <=", value, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoIn(List<Integer> values) {
            addCriterion("poItemNo in", values, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoNotIn(List<Integer> values) {
            addCriterion("poItemNo not in", values, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoBetween(Integer value1, Integer value2) {
            addCriterion("poItemNo between", value1, value2, "poitemno");
            return (Criteria) this;
        }

        public Criteria andPoitemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("poItemNo not between", value1, value2, "poitemno");
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

        public Criteria andSplitItemNoIsNull() {
            addCriterion("split_item_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoIsNotNull() {
            addCriterion("split_item_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoEqualTo(Integer value) {
            addCriterion("split_item_no =", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotEqualTo(Integer value) {
            addCriterion("split_item_no <>", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoGreaterThan(Integer value) {
            addCriterion("split_item_no >", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("split_item_no >=", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoLessThan(Integer value) {
            addCriterion("split_item_no <", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoLessThanOrEqualTo(Integer value) {
            addCriterion("split_item_no <=", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoIn(List<Integer> values) {
            addCriterion("split_item_no in", values, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotIn(List<Integer> values) {
            addCriterion("split_item_no not in", values, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoBetween(Integer value1, Integer value2) {
            addCriterion("split_item_no between", value1, value2, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotBetween(Integer value1, Integer value2) {
            addCriterion("split_item_no not between", value1, value2, "splitItemNo");
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

        public Criteria andFullOrderNoIsNull() {
            addCriterion("full_order_no is null");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoIsNotNull() {
            addCriterion("full_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoEqualTo(String value) {
            addCriterion("full_order_no =", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoNotEqualTo(String value) {
            addCriterion("full_order_no <>", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoGreaterThan(String value) {
            addCriterion("full_order_no >", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("full_order_no >=", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoLessThan(String value) {
            addCriterion("full_order_no <", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoLessThanOrEqualTo(String value) {
            addCriterion("full_order_no <=", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoLike(String value) {
            addCriterion("full_order_no like", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoNotLike(String value) {
            addCriterion("full_order_no not like", value, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoIn(List<String> values) {
            addCriterion("full_order_no in", values, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoNotIn(List<String> values) {
            addCriterion("full_order_no not in", values, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoBetween(String value1, String value2) {
            addCriterion("full_order_no between", value1, value2, "fullOrderNo");
            return (Criteria) this;
        }

        public Criteria andFullOrderNoNotBetween(String value1, String value2) {
            addCriterion("full_order_no not between", value1, value2, "fullOrderNo");
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

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
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

        public Criteria andShipMethodIsNull() {
            addCriterion("ship_method is null");
            return (Criteria) this;
        }

        public Criteria andShipMethodIsNotNull() {
            addCriterion("ship_method is not null");
            return (Criteria) this;
        }

        public Criteria andShipMethodEqualTo(String value) {
            addCriterion("ship_method =", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodNotEqualTo(String value) {
            addCriterion("ship_method <>", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodGreaterThan(String value) {
            addCriterion("ship_method >", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodGreaterThanOrEqualTo(String value) {
            addCriterion("ship_method >=", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodLessThan(String value) {
            addCriterion("ship_method <", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodLessThanOrEqualTo(String value) {
            addCriterion("ship_method <=", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodLike(String value) {
            addCriterion("ship_method like", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodNotLike(String value) {
            addCriterion("ship_method not like", value, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodIn(List<String> values) {
            addCriterion("ship_method in", values, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodNotIn(List<String> values) {
            addCriterion("ship_method not in", values, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodBetween(String value1, String value2) {
            addCriterion("ship_method between", value1, value2, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andShipMethodNotBetween(String value1, String value2) {
            addCriterion("ship_method not between", value1, value2, "shipMethod");
            return (Criteria) this;
        }

        public Criteria andCaseNoIsNull() {
            addCriterion("case_no is null");
            return (Criteria) this;
        }

        public Criteria andCaseNoIsNotNull() {
            addCriterion("case_no is not null");
            return (Criteria) this;
        }

        public Criteria andCaseNoEqualTo(String value) {
            addCriterion("case_no =", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotEqualTo(String value) {
            addCriterion("case_no <>", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoGreaterThan(String value) {
            addCriterion("case_no >", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoGreaterThanOrEqualTo(String value) {
            addCriterion("case_no >=", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLessThan(String value) {
            addCriterion("case_no <", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLessThanOrEqualTo(String value) {
            addCriterion("case_no <=", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLike(String value) {
            addCriterion("case_no like", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotLike(String value) {
            addCriterion("case_no not like", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoIn(List<String> values) {
            addCriterion("case_no in", values, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotIn(List<String> values) {
            addCriterion("case_no not in", values, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoBetween(String value1, String value2) {
            addCriterion("case_no between", value1, value2, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotBetween(String value1, String value2) {
            addCriterion("case_no not between", value1, value2, "caseNo");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNull() {
            addCriterion("barcode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("barcode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("barcode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("barcode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("barcode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("barcode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("barcode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("barcode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("barcode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("barcode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("barcode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("barcode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("barcode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("barcode not between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andEnNameIsNull() {
            addCriterion("en_name is null");
            return (Criteria) this;
        }

        public Criteria andEnNameIsNotNull() {
            addCriterion("en_name is not null");
            return (Criteria) this;
        }

        public Criteria andEnNameEqualTo(String value) {
            addCriterion("en_name =", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameNotEqualTo(String value) {
            addCriterion("en_name <>", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameGreaterThan(String value) {
            addCriterion("en_name >", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameGreaterThanOrEqualTo(String value) {
            addCriterion("en_name >=", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameLessThan(String value) {
            addCriterion("en_name <", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameLessThanOrEqualTo(String value) {
            addCriterion("en_name <=", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameLike(String value) {
            addCriterion("en_name like", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameNotLike(String value) {
            addCriterion("en_name not like", value, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameIn(List<String> values) {
            addCriterion("en_name in", values, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameNotIn(List<String> values) {
            addCriterion("en_name not in", values, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameBetween(String value1, String value2) {
            addCriterion("en_name between", value1, value2, "enName");
            return (Criteria) this;
        }

        public Criteria andEnNameNotBetween(String value1, String value2) {
            addCriterion("en_name not between", value1, value2, "enName");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
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

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
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

        public Criteria andRemarkiiIsNull() {
            addCriterion("remarkII is null");
            return (Criteria) this;
        }

        public Criteria andRemarkiiIsNotNull() {
            addCriterion("remarkII is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkiiEqualTo(String value) {
            addCriterion("remarkII =", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiNotEqualTo(String value) {
            addCriterion("remarkII <>", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiGreaterThan(String value) {
            addCriterion("remarkII >", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiGreaterThanOrEqualTo(String value) {
            addCriterion("remarkII >=", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiLessThan(String value) {
            addCriterion("remarkII <", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiLessThanOrEqualTo(String value) {
            addCriterion("remarkII <=", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiLike(String value) {
            addCriterion("remarkII like", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiNotLike(String value) {
            addCriterion("remarkII not like", value, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiIn(List<String> values) {
            addCriterion("remarkII in", values, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiNotIn(List<String> values) {
            addCriterion("remarkII not in", values, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiBetween(String value1, String value2) {
            addCriterion("remarkII between", value1, value2, "remarkii");
            return (Criteria) this;
        }

        public Criteria andRemarkiiNotBetween(String value1, String value2) {
            addCriterion("remarkII not between", value1, value2, "remarkii");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeIsNull() {
            addCriterion("roHS_code is null");
            return (Criteria) this;
        }

        public Criteria andRohsCodeIsNotNull() {
            addCriterion("roHS_code is not null");
            return (Criteria) this;
        }

        public Criteria andRohsCodeEqualTo(String value) {
            addCriterion("roHS_code =", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeNotEqualTo(String value) {
            addCriterion("roHS_code <>", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeGreaterThan(String value) {
            addCriterion("roHS_code >", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("roHS_code >=", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeLessThan(String value) {
            addCriterion("roHS_code <", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeLessThanOrEqualTo(String value) {
            addCriterion("roHS_code <=", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeLike(String value) {
            addCriterion("roHS_code like", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeNotLike(String value) {
            addCriterion("roHS_code not like", value, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeIn(List<String> values) {
            addCriterion("roHS_code in", values, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeNotIn(List<String> values) {
            addCriterion("roHS_code not in", values, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeBetween(String value1, String value2) {
            addCriterion("roHS_code between", value1, value2, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andRohsCodeNotBetween(String value1, String value2) {
            addCriterion("roHS_code not between", value1, value2, "rohsCode");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andFromCodeIsNull() {
            addCriterion("from_code is null");
            return (Criteria) this;
        }

        public Criteria andFromCodeIsNotNull() {
            addCriterion("from_code is not null");
            return (Criteria) this;
        }

        public Criteria andFromCodeEqualTo(String value) {
            addCriterion("from_code =", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeNotEqualTo(String value) {
            addCriterion("from_code <>", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeGreaterThan(String value) {
            addCriterion("from_code >", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeGreaterThanOrEqualTo(String value) {
            addCriterion("from_code >=", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeLessThan(String value) {
            addCriterion("from_code <", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeLessThanOrEqualTo(String value) {
            addCriterion("from_code <=", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeLike(String value) {
            addCriterion("from_code like", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeNotLike(String value) {
            addCriterion("from_code not like", value, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeIn(List<String> values) {
            addCriterion("from_code in", values, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeNotIn(List<String> values) {
            addCriterion("from_code not in", values, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeBetween(String value1, String value2) {
            addCriterion("from_code between", value1, value2, "fromCode");
            return (Criteria) this;
        }

        public Criteria andFromCodeNotBetween(String value1, String value2) {
            addCriterion("from_code not between", value1, value2, "fromCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeIsNull() {
            addCriterion("shelf_code is null");
            return (Criteria) this;
        }

        public Criteria andShelfCodeIsNotNull() {
            addCriterion("shelf_code is not null");
            return (Criteria) this;
        }

        public Criteria andShelfCodeEqualTo(String value) {
            addCriterion("shelf_code =", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeNotEqualTo(String value) {
            addCriterion("shelf_code <>", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeGreaterThan(String value) {
            addCriterion("shelf_code >", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeGreaterThanOrEqualTo(String value) {
            addCriterion("shelf_code >=", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeLessThan(String value) {
            addCriterion("shelf_code <", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeLessThanOrEqualTo(String value) {
            addCriterion("shelf_code <=", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeLike(String value) {
            addCriterion("shelf_code like", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeNotLike(String value) {
            addCriterion("shelf_code not like", value, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeIn(List<String> values) {
            addCriterion("shelf_code in", values, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeNotIn(List<String> values) {
            addCriterion("shelf_code not in", values, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeBetween(String value1, String value2) {
            addCriterion("shelf_code between", value1, value2, "shelfCode");
            return (Criteria) this;
        }

        public Criteria andShelfCodeNotBetween(String value1, String value2) {
            addCriterion("shelf_code not between", value1, value2, "shelfCode");
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

        public Criteria andImpOrderNoIsNull() {
            addCriterion("imp_order_no is null");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoIsNotNull() {
            addCriterion("imp_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoEqualTo(String value) {
            addCriterion("imp_order_no =", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoNotEqualTo(String value) {
            addCriterion("imp_order_no <>", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoGreaterThan(String value) {
            addCriterion("imp_order_no >", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("imp_order_no >=", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoLessThan(String value) {
            addCriterion("imp_order_no <", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoLessThanOrEqualTo(String value) {
            addCriterion("imp_order_no <=", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoLike(String value) {
            addCriterion("imp_order_no like", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoNotLike(String value) {
            addCriterion("imp_order_no not like", value, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoIn(List<String> values) {
            addCriterion("imp_order_no in", values, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoNotIn(List<String> values) {
            addCriterion("imp_order_no not in", values, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoBetween(String value1, String value2) {
            addCriterion("imp_order_no between", value1, value2, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andImpOrderNoNotBetween(String value1, String value2) {
            addCriterion("imp_order_no not between", value1, value2, "impOrderNo");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIsNull() {
            addCriterion("customs_fee is null");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIsNotNull() {
            addCriterion("customs_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeEqualTo(BigDecimal value) {
            addCriterion("customs_fee =", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotEqualTo(BigDecimal value) {
            addCriterion("customs_fee <>", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeGreaterThan(BigDecimal value) {
            addCriterion("customs_fee >", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_fee >=", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeLessThan(BigDecimal value) {
            addCriterion("customs_fee <", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_fee <=", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIn(List<BigDecimal> values) {
            addCriterion("customs_fee in", values, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotIn(List<BigDecimal> values) {
            addCriterion("customs_fee not in", values, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_fee between", value1, value2, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_fee not between", value1, value2, "customsFee");
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

        public Criteria andImpModelNoIsNull() {
            addCriterion("imp_model_no is null");
            return (Criteria) this;
        }

        public Criteria andImpModelNoIsNotNull() {
            addCriterion("imp_model_no is not null");
            return (Criteria) this;
        }

        public Criteria andImpModelNoEqualTo(String value) {
            addCriterion("imp_model_no =", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoNotEqualTo(String value) {
            addCriterion("imp_model_no <>", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoGreaterThan(String value) {
            addCriterion("imp_model_no >", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("imp_model_no >=", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoLessThan(String value) {
            addCriterion("imp_model_no <", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoLessThanOrEqualTo(String value) {
            addCriterion("imp_model_no <=", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoLike(String value) {
            addCriterion("imp_model_no like", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoNotLike(String value) {
            addCriterion("imp_model_no not like", value, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoIn(List<String> values) {
            addCriterion("imp_model_no in", values, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoNotIn(List<String> values) {
            addCriterion("imp_model_no not in", values, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoBetween(String value1, String value2) {
            addCriterion("imp_model_no between", value1, value2, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andImpModelNoNotBetween(String value1, String value2) {
            addCriterion("imp_model_no not between", value1, value2, "impModelNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoIsNull() {
            addCriterion("oversea_invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoIsNotNull() {
            addCriterion("oversea_invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoEqualTo(String value) {
            addCriterion("oversea_invoice_no =", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoNotEqualTo(String value) {
            addCriterion("oversea_invoice_no <>", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoGreaterThan(String value) {
            addCriterion("oversea_invoice_no >", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("oversea_invoice_no >=", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoLessThan(String value) {
            addCriterion("oversea_invoice_no <", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("oversea_invoice_no <=", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoLike(String value) {
            addCriterion("oversea_invoice_no like", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoNotLike(String value) {
            addCriterion("oversea_invoice_no not like", value, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoIn(List<String> values) {
            addCriterion("oversea_invoice_no in", values, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoNotIn(List<String> values) {
            addCriterion("oversea_invoice_no not in", values, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoBetween(String value1, String value2) {
            addCriterion("oversea_invoice_no between", value1, value2, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOverseaInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("oversea_invoice_no not between", value1, value2, "overseaInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andNoncommercialIsNull() {
            addCriterion("nonCommercial is null");
            return (Criteria) this;
        }

        public Criteria andNoncommercialIsNotNull() {
            addCriterion("nonCommercial is not null");
            return (Criteria) this;
        }

        public Criteria andNoncommercialEqualTo(String value) {
            addCriterion("nonCommercial =", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialNotEqualTo(String value) {
            addCriterion("nonCommercial <>", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialGreaterThan(String value) {
            addCriterion("nonCommercial >", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialGreaterThanOrEqualTo(String value) {
            addCriterion("nonCommercial >=", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialLessThan(String value) {
            addCriterion("nonCommercial <", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialLessThanOrEqualTo(String value) {
            addCriterion("nonCommercial <=", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialLike(String value) {
            addCriterion("nonCommercial like", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialNotLike(String value) {
            addCriterion("nonCommercial not like", value, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialIn(List<String> values) {
            addCriterion("nonCommercial in", values, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialNotIn(List<String> values) {
            addCriterion("nonCommercial not in", values, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialBetween(String value1, String value2) {
            addCriterion("nonCommercial between", value1, value2, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andNoncommercialNotBetween(String value1, String value2) {
            addCriterion("nonCommercial not between", value1, value2, "noncommercial");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIsNull() {
            addCriterion("excise_tax is null");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIsNotNull() {
            addCriterion("excise_tax is not null");
            return (Criteria) this;
        }

        public Criteria andExciseTaxEqualTo(BigDecimal value) {
            addCriterion("excise_tax =", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotEqualTo(BigDecimal value) {
            addCriterion("excise_tax <>", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxGreaterThan(BigDecimal value) {
            addCriterion("excise_tax >", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("excise_tax >=", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxLessThan(BigDecimal value) {
            addCriterion("excise_tax <", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("excise_tax <=", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIn(List<BigDecimal> values) {
            addCriterion("excise_tax in", values, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotIn(List<BigDecimal> values) {
            addCriterion("excise_tax not in", values, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("excise_tax between", value1, value2, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("excise_tax not between", value1, value2, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNull() {
            addCriterion("other_fee is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNotNull() {
            addCriterion("other_fee is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeEqualTo(BigDecimal value) {
            addCriterion("other_fee =", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotEqualTo(BigDecimal value) {
            addCriterion("other_fee <>", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThan(BigDecimal value) {
            addCriterion("other_fee >", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee >=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThan(BigDecimal value) {
            addCriterion("other_fee <", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee <=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIn(List<BigDecimal> values) {
            addCriterion("other_fee in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotIn(List<BigDecimal> values) {
            addCriterion("other_fee not in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee not between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNull() {
            addCriterion("trans_fee is null");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNotNull() {
            addCriterion("trans_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTransFeeEqualTo(BigDecimal value) {
            addCriterion("trans_fee =", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotEqualTo(BigDecimal value) {
            addCriterion("trans_fee <>", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThan(BigDecimal value) {
            addCriterion("trans_fee >", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_fee >=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThan(BigDecimal value) {
            addCriterion("trans_fee <", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_fee <=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIn(List<BigDecimal> values) {
            addCriterion("trans_fee in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotIn(List<BigDecimal> values) {
            addCriterion("trans_fee not in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_fee between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_fee not between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeIsNull() {
            addCriterion("po_warehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeIsNotNull() {
            addCriterion("po_warehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeEqualTo(String value) {
            addCriterion("po_warehouseCode =", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeNotEqualTo(String value) {
            addCriterion("po_warehouseCode <>", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeGreaterThan(String value) {
            addCriterion("po_warehouseCode >", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("po_warehouseCode >=", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeLessThan(String value) {
            addCriterion("po_warehouseCode <", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("po_warehouseCode <=", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeLike(String value) {
            addCriterion("po_warehouseCode like", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeNotLike(String value) {
            addCriterion("po_warehouseCode not like", value, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeIn(List<String> values) {
            addCriterion("po_warehouseCode in", values, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeNotIn(List<String> values) {
            addCriterion("po_warehouseCode not in", values, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeBetween(String value1, String value2) {
            addCriterion("po_warehouseCode between", value1, value2, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andPoWarehousecodeNotBetween(String value1, String value2) {
            addCriterion("po_warehouseCode not between", value1, value2, "poWarehousecode");
            return (Criteria) this;
        }

        public Criteria andVatFeeIsNull() {
            addCriterion("vat_fee is null");
            return (Criteria) this;
        }

        public Criteria andVatFeeIsNotNull() {
            addCriterion("vat_fee is not null");
            return (Criteria) this;
        }

        public Criteria andVatFeeEqualTo(BigDecimal value) {
            addCriterion("vat_fee =", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotEqualTo(BigDecimal value) {
            addCriterion("vat_fee <>", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeGreaterThan(BigDecimal value) {
            addCriterion("vat_fee >", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vat_fee >=", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeLessThan(BigDecimal value) {
            addCriterion("vat_fee <", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vat_fee <=", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeIn(List<BigDecimal> values) {
            addCriterion("vat_fee in", values, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotIn(List<BigDecimal> values) {
            addCriterion("vat_fee not in", values, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat_fee between", value1, value2, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat_fee not between", value1, value2, "vatFee");
            return (Criteria) this;
        }

        public Criteria andSnCodeIsNull() {
            addCriterion("sn_code is null");
            return (Criteria) this;
        }

        public Criteria andSnCodeIsNotNull() {
            addCriterion("sn_code is not null");
            return (Criteria) this;
        }

        public Criteria andSnCodeEqualTo(String value) {
            addCriterion("sn_code =", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeNotEqualTo(String value) {
            addCriterion("sn_code <>", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeGreaterThan(String value) {
            addCriterion("sn_code >", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sn_code >=", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeLessThan(String value) {
            addCriterion("sn_code <", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeLessThanOrEqualTo(String value) {
            addCriterion("sn_code <=", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeLike(String value) {
            addCriterion("sn_code like", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeNotLike(String value) {
            addCriterion("sn_code not like", value, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeIn(List<String> values) {
            addCriterion("sn_code in", values, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeNotIn(List<String> values) {
            addCriterion("sn_code not in", values, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeBetween(String value1, String value2) {
            addCriterion("sn_code between", value1, value2, "snCode");
            return (Criteria) this;
        }

        public Criteria andSnCodeNotBetween(String value1, String value2) {
            addCriterion("sn_code not between", value1, value2, "snCode");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("Amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("Amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("Amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("Amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("Amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("Amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("Amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("Amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount not between", value1, value2, "amount");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpdetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExpdetailExample() {
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

        public Criteria andDeliveryNoIsNull() {
            addCriterion("delivery_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIsNotNull() {
            addCriterion("delivery_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoEqualTo(String value) {
            addCriterion("delivery_no =", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotEqualTo(String value) {
            addCriterion("delivery_no <>", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThan(String value) {
            addCriterion("delivery_no >", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_no >=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThan(String value) {
            addCriterion("delivery_no <", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThanOrEqualTo(String value) {
            addCriterion("delivery_no <=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLike(String value) {
            addCriterion("delivery_no like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotLike(String value) {
            addCriterion("delivery_no not like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIn(List<String> values) {
            addCriterion("delivery_no in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotIn(List<String> values) {
            addCriterion("delivery_no not in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoBetween(String value1, String value2) {
            addCriterion("delivery_no between", value1, value2, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotBetween(String value1, String value2) {
            addCriterion("delivery_no not between", value1, value2, "deliveryNo");
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

        public Criteria andOrderFnoIsNull() {
            addCriterion("order_fno is null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIsNotNull() {
            addCriterion("order_fno is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoEqualTo(String value) {
            addCriterion("order_fno =", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotEqualTo(String value) {
            addCriterion("order_fno <>", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThan(String value) {
            addCriterion("order_fno >", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("order_fno >=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThan(String value) {
            addCriterion("order_fno <", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThanOrEqualTo(String value) {
            addCriterion("order_fno <=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLike(String value) {
            addCriterion("order_fno like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotLike(String value) {
            addCriterion("order_fno not like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIn(List<String> values) {
            addCriterion("order_fno in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotIn(List<String> values) {
            addCriterion("order_fno not in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoBetween(String value1, String value2) {
            addCriterion("order_fno between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotBetween(String value1, String value2) {
            addCriterion("order_fno not between", value1, value2, "orderFno");
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

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
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

        public Criteria andExpressNoIsNull() {
            addCriterion("express_no is null");
            return (Criteria) this;
        }

        public Criteria andExpressNoIsNotNull() {
            addCriterion("express_no is not null");
            return (Criteria) this;
        }

        public Criteria andExpressNoEqualTo(String value) {
            addCriterion("express_no =", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoNotEqualTo(String value) {
            addCriterion("express_no <>", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoGreaterThan(String value) {
            addCriterion("express_no >", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoGreaterThanOrEqualTo(String value) {
            addCriterion("express_no >=", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoLessThan(String value) {
            addCriterion("express_no <", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoLessThanOrEqualTo(String value) {
            addCriterion("express_no <=", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoLike(String value) {
            addCriterion("express_no like", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoNotLike(String value) {
            addCriterion("express_no not like", value, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoIn(List<String> values) {
            addCriterion("express_no in", values, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoNotIn(List<String> values) {
            addCriterion("express_no not in", values, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoBetween(String value1, String value2) {
            addCriterion("express_no between", value1, value2, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressNoNotBetween(String value1, String value2) {
            addCriterion("express_no not between", value1, value2, "expressNo");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNull() {
            addCriterion("express_company is null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNotNull() {
            addCriterion("express_company is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyEqualTo(String value) {
            addCriterion("express_company =", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotEqualTo(String value) {
            addCriterion("express_company <>", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThan(String value) {
            addCriterion("express_company >", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("express_company >=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThan(String value) {
            addCriterion("express_company <", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThanOrEqualTo(String value) {
            addCriterion("express_company <=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLike(String value) {
            addCriterion("express_company like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotLike(String value) {
            addCriterion("express_company not like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIn(List<String> values) {
            addCriterion("express_company in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotIn(List<String> values) {
            addCriterion("express_company not in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyBetween(String value1, String value2) {
            addCriterion("express_company between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotBetween(String value1, String value2) {
            addCriterion("express_company not between", value1, value2, "expressCompany");
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

        public Criteria andOptCodeIsNull() {
            addCriterion("opt_code is null");
            return (Criteria) this;
        }

        public Criteria andOptCodeIsNotNull() {
            addCriterion("opt_code is not null");
            return (Criteria) this;
        }

        public Criteria andOptCodeEqualTo(Integer value) {
            addCriterion("opt_code =", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotEqualTo(Integer value) {
            addCriterion("opt_code <>", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThan(Integer value) {
            addCriterion("opt_code >", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("opt_code >=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThan(Integer value) {
            addCriterion("opt_code <", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThanOrEqualTo(Integer value) {
            addCriterion("opt_code <=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeIn(List<Integer> values) {
            addCriterion("opt_code in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotIn(List<Integer> values) {
            addCriterion("opt_code not in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeBetween(Integer value1, Integer value2) {
            addCriterion("opt_code between", value1, value2, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("opt_code not between", value1, value2, "optCode");
            return (Criteria) this;
        }

        public Criteria andCorderNoIsNull() {
            addCriterion("corder_no is null");
            return (Criteria) this;
        }

        public Criteria andCorderNoIsNotNull() {
            addCriterion("corder_no is not null");
            return (Criteria) this;
        }

        public Criteria andCorderNoEqualTo(String value) {
            addCriterion("corder_no =", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotEqualTo(String value) {
            addCriterion("corder_no <>", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThan(String value) {
            addCriterion("corder_no >", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("corder_no >=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThan(String value) {
            addCriterion("corder_no <", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThanOrEqualTo(String value) {
            addCriterion("corder_no <=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLike(String value) {
            addCriterion("corder_no like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotLike(String value) {
            addCriterion("corder_no not like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoIn(List<String> values) {
            addCriterion("corder_no in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotIn(List<String> values) {
            addCriterion("corder_no not in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoBetween(String value1, String value2) {
            addCriterion("corder_no between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotBetween(String value1, String value2) {
            addCriterion("corder_no not between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoIsNull() {
            addCriterion("cmodel_no is null");
            return (Criteria) this;
        }

        public Criteria andCmodelNoIsNotNull() {
            addCriterion("cmodel_no is not null");
            return (Criteria) this;
        }

        public Criteria andCmodelNoEqualTo(String value) {
            addCriterion("cmodel_no =", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotEqualTo(String value) {
            addCriterion("cmodel_no <>", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoGreaterThan(String value) {
            addCriterion("cmodel_no >", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoGreaterThanOrEqualTo(String value) {
            addCriterion("cmodel_no >=", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLessThan(String value) {
            addCriterion("cmodel_no <", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLessThanOrEqualTo(String value) {
            addCriterion("cmodel_no <=", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoLike(String value) {
            addCriterion("cmodel_no like", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotLike(String value) {
            addCriterion("cmodel_no not like", value, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoIn(List<String> values) {
            addCriterion("cmodel_no in", values, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotIn(List<String> values) {
            addCriterion("cmodel_no not in", values, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoBetween(String value1, String value2) {
            addCriterion("cmodel_no between", value1, value2, "cmodelNo");
            return (Criteria) this;
        }

        public Criteria andCmodelNoNotBetween(String value1, String value2) {
            addCriterion("cmodel_no not between", value1, value2, "cmodelNo");
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

        public Criteria andInvoiceFlagIsNull() {
            addCriterion("invoice_flag is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagIsNotNull() {
            addCriterion("invoice_flag is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagEqualTo(String value) {
            addCriterion("invoice_flag =", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagNotEqualTo(String value) {
            addCriterion("invoice_flag <>", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagGreaterThan(String value) {
            addCriterion("invoice_flag >", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_flag >=", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagLessThan(String value) {
            addCriterion("invoice_flag <", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagLessThanOrEqualTo(String value) {
            addCriterion("invoice_flag <=", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagLike(String value) {
            addCriterion("invoice_flag like", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagNotLike(String value) {
            addCriterion("invoice_flag not like", value, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagIn(List<String> values) {
            addCriterion("invoice_flag in", values, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagNotIn(List<String> values) {
            addCriterion("invoice_flag not in", values, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagBetween(String value1, String value2) {
            addCriterion("invoice_flag between", value1, value2, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceFlagNotBetween(String value1, String value2) {
            addCriterion("invoice_flag not between", value1, value2, "invoiceFlag");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeIsNull() {
            addCriterion("invoice_time is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeIsNotNull() {
            addCriterion("invoice_time is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeEqualTo(Date value) {
            addCriterion("invoice_time =", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeNotEqualTo(Date value) {
            addCriterion("invoice_time <>", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeGreaterThan(Date value) {
            addCriterion("invoice_time >", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invoice_time >=", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeLessThan(Date value) {
            addCriterion("invoice_time <", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeLessThanOrEqualTo(Date value) {
            addCriterion("invoice_time <=", value, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeIn(List<Date> values) {
            addCriterion("invoice_time in", values, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeNotIn(List<Date> values) {
            addCriterion("invoice_time not in", values, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeBetween(Date value1, Date value2) {
            addCriterion("invoice_time between", value1, value2, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andInvoiceTimeNotBetween(Date value1, Date value2) {
            addCriterion("invoice_time not between", value1, value2, "invoiceTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNull() {
            addCriterion("sign_time is null");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNotNull() {
            addCriterion("sign_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignTimeEqualTo(Date value) {
            addCriterion("sign_time =", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotEqualTo(Date value) {
            addCriterion("sign_time <>", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThan(Date value) {
            addCriterion("sign_time >", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sign_time >=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThan(Date value) {
            addCriterion("sign_time <", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThanOrEqualTo(Date value) {
            addCriterion("sign_time <=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIn(List<Date> values) {
            addCriterion("sign_time in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotIn(List<Date> values) {
            addCriterion("sign_time not in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeBetween(Date value1, Date value2) {
            addCriterion("sign_time between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotBetween(Date value1, Date value2) {
            addCriterion("sign_time not between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNull() {
            addCriterion("stock_code is null");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNotNull() {
            addCriterion("stock_code is not null");
            return (Criteria) this;
        }

        public Criteria andStockCodeEqualTo(String value) {
            addCriterion("stock_code =", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotEqualTo(String value) {
            addCriterion("stock_code <>", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThan(String value) {
            addCriterion("stock_code >", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_code >=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThan(String value) {
            addCriterion("stock_code <", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThanOrEqualTo(String value) {
            addCriterion("stock_code <=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLike(String value) {
            addCriterion("stock_code like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotLike(String value) {
            addCriterion("stock_code not like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeIn(List<String> values) {
            addCriterion("stock_code in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotIn(List<String> values) {
            addCriterion("stock_code not in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeBetween(String value1, String value2) {
            addCriterion("stock_code between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotBetween(String value1, String value2) {
            addCriterion("stock_code not between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNull() {
            addCriterion("dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNotNull() {
            addCriterion("dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andDlvDateEqualTo(Date value) {
            addCriterion("dlv_date =", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotEqualTo(Date value) {
            addCriterion("dlv_date <>", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThan(Date value) {
            addCriterion("dlv_date >", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("dlv_date >=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThan(Date value) {
            addCriterion("dlv_date <", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("dlv_date <=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateIn(List<Date> values) {
            addCriterion("dlv_date in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotIn(List<Date> values) {
            addCriterion("dlv_date not in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateBetween(Date value1, Date value2) {
            addCriterion("dlv_date between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("dlv_date not between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNull() {
            addCriterion("orOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNotNull() {
            addCriterion("orOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrordernoEqualTo(String value) {
            addCriterion("orOrderNo =", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotEqualTo(String value) {
            addCriterion("orOrderNo <>", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThan(String value) {
            addCriterion("orOrderNo >", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThanOrEqualTo(String value) {
            addCriterion("orOrderNo >=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThan(String value) {
            addCriterion("orOrderNo <", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThanOrEqualTo(String value) {
            addCriterion("orOrderNo <=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLike(String value) {
            addCriterion("orOrderNo like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotLike(String value) {
            addCriterion("orOrderNo not like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoIn(List<String> values) {
            addCriterion("orOrderNo in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotIn(List<String> values) {
            addCriterion("orOrderNo not in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoBetween(String value1, String value2) {
            addCriterion("orOrderNo between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotBetween(String value1, String value2) {
            addCriterion("orOrderNo not between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andSignStatusIsNull() {
            addCriterion("sign_status is null");
            return (Criteria) this;
        }

        public Criteria andSignStatusIsNotNull() {
            addCriterion("sign_status is not null");
            return (Criteria) this;
        }

        public Criteria andSignStatusEqualTo(Short value) {
            addCriterion("sign_status =", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotEqualTo(Short value) {
            addCriterion("sign_status <>", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusGreaterThan(Short value) {
            addCriterion("sign_status >", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("sign_status >=", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusLessThan(Short value) {
            addCriterion("sign_status <", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusLessThanOrEqualTo(Short value) {
            addCriterion("sign_status <=", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusIn(List<Short> values) {
            addCriterion("sign_status in", values, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotIn(List<Short> values) {
            addCriterion("sign_status not in", values, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusBetween(Short value1, Short value2) {
            addCriterion("sign_status between", value1, value2, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotBetween(Short value1, Short value2) {
            addCriterion("sign_status not between", value1, value2, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSenderIsNull() {
            addCriterion("sender is null");
            return (Criteria) this;
        }

        public Criteria andSenderIsNotNull() {
            addCriterion("sender is not null");
            return (Criteria) this;
        }

        public Criteria andSenderEqualTo(String value) {
            addCriterion("sender =", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotEqualTo(String value) {
            addCriterion("sender <>", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThan(String value) {
            addCriterion("sender >", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThanOrEqualTo(String value) {
            addCriterion("sender >=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThan(String value) {
            addCriterion("sender <", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThanOrEqualTo(String value) {
            addCriterion("sender <=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLike(String value) {
            addCriterion("sender like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotLike(String value) {
            addCriterion("sender not like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderIn(List<String> values) {
            addCriterion("sender in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotIn(List<String> values) {
            addCriterion("sender not in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderBetween(String value1, String value2) {
            addCriterion("sender between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotBetween(String value1, String value2) {
            addCriterion("sender not between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIsNull() {
            addCriterion("dlv_site is null");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIsNotNull() {
            addCriterion("dlv_site is not null");
            return (Criteria) this;
        }

        public Criteria andDlvSiteEqualTo(String value) {
            addCriterion("dlv_site =", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotEqualTo(String value) {
            addCriterion("dlv_site <>", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteGreaterThan(String value) {
            addCriterion("dlv_site >", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_site >=", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLessThan(String value) {
            addCriterion("dlv_site <", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLessThanOrEqualTo(String value) {
            addCriterion("dlv_site <=", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLike(String value) {
            addCriterion("dlv_site like", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotLike(String value) {
            addCriterion("dlv_site not like", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIn(List<String> values) {
            addCriterion("dlv_site in", values, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotIn(List<String> values) {
            addCriterion("dlv_site not in", values, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteBetween(String value1, String value2) {
            addCriterion("dlv_site between", value1, value2, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotBetween(String value1, String value2) {
            addCriterion("dlv_site not between", value1, value2, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNull() {
            addCriterion("volume is null");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNotNull() {
            addCriterion("volume is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeEqualTo(String value) {
            addCriterion("volume =", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotEqualTo(String value) {
            addCriterion("volume <>", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThan(String value) {
            addCriterion("volume >", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThanOrEqualTo(String value) {
            addCriterion("volume >=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThan(String value) {
            addCriterion("volume <", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThanOrEqualTo(String value) {
            addCriterion("volume <=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLike(String value) {
            addCriterion("volume like", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotLike(String value) {
            addCriterion("volume not like", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeIn(List<String> values) {
            addCriterion("volume in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotIn(List<String> values) {
            addCriterion("volume not in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeBetween(String value1, String value2) {
            addCriterion("volume between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotBetween(String value1, String value2) {
            addCriterion("volume not between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIsNull() {
            addCriterion("box_type is null");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIsNotNull() {
            addCriterion("box_type is not null");
            return (Criteria) this;
        }

        public Criteria andBoxTypeEqualTo(String value) {
            addCriterion("box_type =", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotEqualTo(String value) {
            addCriterion("box_type <>", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeGreaterThan(String value) {
            addCriterion("box_type >", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeGreaterThanOrEqualTo(String value) {
            addCriterion("box_type >=", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLessThan(String value) {
            addCriterion("box_type <", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLessThanOrEqualTo(String value) {
            addCriterion("box_type <=", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLike(String value) {
            addCriterion("box_type like", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotLike(String value) {
            addCriterion("box_type not like", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIn(List<String> values) {
            addCriterion("box_type in", values, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotIn(List<String> values) {
            addCriterion("box_type not in", values, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeBetween(String value1, String value2) {
            addCriterion("box_type between", value1, value2, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotBetween(String value1, String value2) {
            addCriterion("box_type not between", value1, value2, "boxType");
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

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_no =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_no <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_no >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_no >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_no <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_no <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_no like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_no not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_no in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_no not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_no between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_no not between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoIsNull() {
            addCriterion("sign_order_no is null");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoIsNotNull() {
            addCriterion("sign_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoEqualTo(String value) {
            addCriterion("sign_order_no =", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoNotEqualTo(String value) {
            addCriterion("sign_order_no <>", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoGreaterThan(String value) {
            addCriterion("sign_order_no >", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("sign_order_no >=", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoLessThan(String value) {
            addCriterion("sign_order_no <", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoLessThanOrEqualTo(String value) {
            addCriterion("sign_order_no <=", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoLike(String value) {
            addCriterion("sign_order_no like", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoNotLike(String value) {
            addCriterion("sign_order_no not like", value, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoIn(List<String> values) {
            addCriterion("sign_order_no in", values, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoNotIn(List<String> values) {
            addCriterion("sign_order_no not in", values, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoBetween(String value1, String value2) {
            addCriterion("sign_order_no between", value1, value2, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andSignOrderNoNotBetween(String value1, String value2) {
            addCriterion("sign_order_no not between", value1, value2, "signOrderNo");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIsNull() {
            addCriterion("dlv_address is null");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIsNotNull() {
            addCriterion("dlv_address is not null");
            return (Criteria) this;
        }

        public Criteria andDlvAddressEqualTo(String value) {
            addCriterion("dlv_address =", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotEqualTo(String value) {
            addCriterion("dlv_address <>", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressGreaterThan(String value) {
            addCriterion("dlv_address >", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_address >=", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLessThan(String value) {
            addCriterion("dlv_address <", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLessThanOrEqualTo(String value) {
            addCriterion("dlv_address <=", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLike(String value) {
            addCriterion("dlv_address like", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotLike(String value) {
            addCriterion("dlv_address not like", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIn(List<String> values) {
            addCriterion("dlv_address in", values, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotIn(List<String> values) {
            addCriterion("dlv_address not in", values, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressBetween(String value1, String value2) {
            addCriterion("dlv_address between", value1, value2, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotBetween(String value1, String value2) {
            addCriterion("dlv_address not between", value1, value2, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNull() {
            addCriterion("ContactPsn is null");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNotNull() {
            addCriterion("ContactPsn is not null");
            return (Criteria) this;
        }

        public Criteria andContactpsnEqualTo(String value) {
            addCriterion("ContactPsn =", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotEqualTo(String value) {
            addCriterion("ContactPsn <>", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThan(String value) {
            addCriterion("ContactPsn >", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThanOrEqualTo(String value) {
            addCriterion("ContactPsn >=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThan(String value) {
            addCriterion("ContactPsn <", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThanOrEqualTo(String value) {
            addCriterion("ContactPsn <=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLike(String value) {
            addCriterion("ContactPsn like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotLike(String value) {
            addCriterion("ContactPsn not like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnIn(List<String> values) {
            addCriterion("ContactPsn in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotIn(List<String> values) {
            addCriterion("ContactPsn not in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnBetween(String value1, String value2) {
            addCriterion("ContactPsn between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotBetween(String value1, String value2) {
            addCriterion("ContactPsn not between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andBranchFlagIsNull() {
            addCriterion("branch_flag is null");
            return (Criteria) this;
        }

        public Criteria andBranchFlagIsNotNull() {
            addCriterion("branch_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBranchFlagEqualTo(Integer value) {
            addCriterion("branch_flag =", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagNotEqualTo(Integer value) {
            addCriterion("branch_flag <>", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagGreaterThan(Integer value) {
            addCriterion("branch_flag >", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_flag >=", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagLessThan(Integer value) {
            addCriterion("branch_flag <", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagLessThanOrEqualTo(Integer value) {
            addCriterion("branch_flag <=", value, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagIn(List<Integer> values) {
            addCriterion("branch_flag in", values, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagNotIn(List<Integer> values) {
            addCriterion("branch_flag not in", values, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagBetween(Integer value1, Integer value2) {
            addCriterion("branch_flag between", value1, value2, "branchFlag");
            return (Criteria) this;
        }

        public Criteria andBranchFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_flag not between", value1, value2, "branchFlag");
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
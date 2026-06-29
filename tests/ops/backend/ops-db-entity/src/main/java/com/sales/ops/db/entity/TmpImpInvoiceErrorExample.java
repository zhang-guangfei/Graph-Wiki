package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class TmpImpInvoiceErrorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TmpImpInvoiceErrorExample() {
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

        public Criteria andPoModelNoIsNull() {
            addCriterion("po_model_no is null");
            return (Criteria) this;
        }

        public Criteria andPoModelNoIsNotNull() {
            addCriterion("po_model_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoModelNoEqualTo(String value) {
            addCriterion("po_model_no =", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoNotEqualTo(String value) {
            addCriterion("po_model_no <>", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoGreaterThan(String value) {
            addCriterion("po_model_no >", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("po_model_no >=", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoLessThan(String value) {
            addCriterion("po_model_no <", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoLessThanOrEqualTo(String value) {
            addCriterion("po_model_no <=", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoLike(String value) {
            addCriterion("po_model_no like", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoNotLike(String value) {
            addCriterion("po_model_no not like", value, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoIn(List<String> values) {
            addCriterion("po_model_no in", values, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoNotIn(List<String> values) {
            addCriterion("po_model_no not in", values, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoBetween(String value1, String value2) {
            addCriterion("po_model_no between", value1, value2, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andPoModelNoNotBetween(String value1, String value2) {
            addCriterion("po_model_no not between", value1, value2, "poModelNo");
            return (Criteria) this;
        }

        public Criteria andErrorTypeIsNull() {
            addCriterion("error_type is null");
            return (Criteria) this;
        }

        public Criteria andErrorTypeIsNotNull() {
            addCriterion("error_type is not null");
            return (Criteria) this;
        }

        public Criteria andErrorTypeEqualTo(Integer value) {
            addCriterion("error_type =", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeNotEqualTo(Integer value) {
            addCriterion("error_type <>", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeGreaterThan(Integer value) {
            addCriterion("error_type >", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("error_type >=", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeLessThan(Integer value) {
            addCriterion("error_type <", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("error_type <=", value, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeIn(List<Integer> values) {
            addCriterion("error_type in", values, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeNotIn(List<Integer> values) {
            addCriterion("error_type not in", values, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeBetween(Integer value1, Integer value2) {
            addCriterion("error_type between", value1, value2, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("error_type not between", value1, value2, "errorType");
            return (Criteria) this;
        }

        public Criteria andErrorTextIsNull() {
            addCriterion("error_text is null");
            return (Criteria) this;
        }

        public Criteria andErrorTextIsNotNull() {
            addCriterion("error_text is not null");
            return (Criteria) this;
        }

        public Criteria andErrorTextEqualTo(String value) {
            addCriterion("error_text =", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextNotEqualTo(String value) {
            addCriterion("error_text <>", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextGreaterThan(String value) {
            addCriterion("error_text >", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextGreaterThanOrEqualTo(String value) {
            addCriterion("error_text >=", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextLessThan(String value) {
            addCriterion("error_text <", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextLessThanOrEqualTo(String value) {
            addCriterion("error_text <=", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextLike(String value) {
            addCriterion("error_text like", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextNotLike(String value) {
            addCriterion("error_text not like", value, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextIn(List<String> values) {
            addCriterion("error_text in", values, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextNotIn(List<String> values) {
            addCriterion("error_text not in", values, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextBetween(String value1, String value2) {
            addCriterion("error_text between", value1, value2, "errorText");
            return (Criteria) this;
        }

        public Criteria andErrorTextNotBetween(String value1, String value2) {
            addCriterion("error_text not between", value1, value2, "errorText");
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

        public Criteria andPackQtyIsNull() {
            addCriterion("pack_qty is null");
            return (Criteria) this;
        }

        public Criteria andPackQtyIsNotNull() {
            addCriterion("pack_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPackQtyEqualTo(Integer value) {
            addCriterion("pack_qty =", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyNotEqualTo(Integer value) {
            addCriterion("pack_qty <>", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyGreaterThan(Integer value) {
            addCriterion("pack_qty >", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("pack_qty >=", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyLessThan(Integer value) {
            addCriterion("pack_qty <", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyLessThanOrEqualTo(Integer value) {
            addCriterion("pack_qty <=", value, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyIn(List<Integer> values) {
            addCriterion("pack_qty in", values, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyNotIn(List<Integer> values) {
            addCriterion("pack_qty not in", values, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyBetween(Integer value1, Integer value2) {
            addCriterion("pack_qty between", value1, value2, "packQty");
            return (Criteria) this;
        }

        public Criteria andPackQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("pack_qty not between", value1, value2, "packQty");
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

        public Criteria andPoWarehouseCodeIsNull() {
            addCriterion("po_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeIsNotNull() {
            addCriterion("po_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeEqualTo(String value) {
            addCriterion("po_warehouse_code =", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeNotEqualTo(String value) {
            addCriterion("po_warehouse_code <>", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeGreaterThan(String value) {
            addCriterion("po_warehouse_code >", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("po_warehouse_code >=", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeLessThan(String value) {
            addCriterion("po_warehouse_code <", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("po_warehouse_code <=", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeLike(String value) {
            addCriterion("po_warehouse_code like", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeNotLike(String value) {
            addCriterion("po_warehouse_code not like", value, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeIn(List<String> values) {
            addCriterion("po_warehouse_code in", values, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeNotIn(List<String> values) {
            addCriterion("po_warehouse_code not in", values, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeBetween(String value1, String value2) {
            addCriterion("po_warehouse_code between", value1, value2, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("po_warehouse_code not between", value1, value2, "poWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeIsNull() {
            addCriterion("po_supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeIsNotNull() {
            addCriterion("po_supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeEqualTo(String value) {
            addCriterion("po_supplier_code =", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeNotEqualTo(String value) {
            addCriterion("po_supplier_code <>", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeGreaterThan(String value) {
            addCriterion("po_supplier_code >", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("po_supplier_code >=", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeLessThan(String value) {
            addCriterion("po_supplier_code <", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("po_supplier_code <=", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeLike(String value) {
            addCriterion("po_supplier_code like", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeNotLike(String value) {
            addCriterion("po_supplier_code not like", value, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeIn(List<String> values) {
            addCriterion("po_supplier_code in", values, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeNotIn(List<String> values) {
            addCriterion("po_supplier_code not in", values, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeBetween(String value1, String value2) {
            addCriterion("po_supplier_code between", value1, value2, "poSupplierCode");
            return (Criteria) this;
        }

        public Criteria andPoSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("po_supplier_code not between", value1, value2, "poSupplierCode");
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
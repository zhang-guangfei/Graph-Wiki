package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsPoInvoiceDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoInvoiceDetailExample() {
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

        public Criteria andImpDateIsNull() {
            addCriterion("imp_date is null");
            return (Criteria) this;
        }

        public Criteria andImpDateIsNotNull() {
            addCriterion("imp_date is not null");
            return (Criteria) this;
        }

        public Criteria andImpDateEqualTo(Date value) {
            addCriterionForJDBCDate("imp_date =", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("imp_date <>", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateGreaterThan(Date value) {
            addCriterionForJDBCDate("imp_date >", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("imp_date >=", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateLessThan(Date value) {
            addCriterionForJDBCDate("imp_date <", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("imp_date <=", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateIn(List<Date> values) {
            addCriterionForJDBCDate("imp_date in", values, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("imp_date not in", values, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("imp_date between", value1, value2, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("imp_date not between", value1, value2, "impDate");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustIsNull() {
            addCriterion("amount_adjust is null");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustIsNotNull() {
            addCriterion("amount_adjust is not null");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustEqualTo(BigDecimal value) {
            addCriterion("amount_adjust =", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustNotEqualTo(BigDecimal value) {
            addCriterion("amount_adjust <>", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustGreaterThan(BigDecimal value) {
            addCriterion("amount_adjust >", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_adjust >=", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustLessThan(BigDecimal value) {
            addCriterion("amount_adjust <", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_adjust <=", value, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustIn(List<BigDecimal> values) {
            addCriterion("amount_adjust in", values, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustNotIn(List<BigDecimal> values) {
            addCriterion("amount_adjust not in", values, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_adjust between", value1, value2, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andAmountAdjustNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_adjust not between", value1, value2, "amountAdjust");
            return (Criteria) this;
        }

        public Criteria andPriceRmbIsNull() {
            addCriterion("price_rmb is null");
            return (Criteria) this;
        }

        public Criteria andPriceRmbIsNotNull() {
            addCriterion("price_rmb is not null");
            return (Criteria) this;
        }

        public Criteria andPriceRmbEqualTo(BigDecimal value) {
            addCriterion("price_rmb =", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbNotEqualTo(BigDecimal value) {
            addCriterion("price_rmb <>", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbGreaterThan(BigDecimal value) {
            addCriterion("price_rmb >", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_rmb >=", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbLessThan(BigDecimal value) {
            addCriterion("price_rmb <", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_rmb <=", value, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbIn(List<BigDecimal> values) {
            addCriterion("price_rmb in", values, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbNotIn(List<BigDecimal> values) {
            addCriterion("price_rmb not in", values, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_rmb between", value1, value2, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andPriceRmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_rmb not between", value1, value2, "priceRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIsNull() {
            addCriterion("amount_rmb is null");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIsNotNull() {
            addCriterion("amount_rmb is not null");
            return (Criteria) this;
        }

        public Criteria andAmountRmbEqualTo(BigDecimal value) {
            addCriterion("amount_rmb =", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotEqualTo(BigDecimal value) {
            addCriterion("amount_rmb <>", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbGreaterThan(BigDecimal value) {
            addCriterion("amount_rmb >", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_rmb >=", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbLessThan(BigDecimal value) {
            addCriterion("amount_rmb <", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_rmb <=", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIn(List<BigDecimal> values) {
            addCriterion("amount_rmb in", values, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotIn(List<BigDecimal> values) {
            addCriterion("amount_rmb not in", values, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_rmb between", value1, value2, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_rmb not between", value1, value2, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andProductNameEnIsNull() {
            addCriterion("product_name_en is null");
            return (Criteria) this;
        }

        public Criteria andProductNameEnIsNotNull() {
            addCriterion("product_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEnEqualTo(String value) {
            addCriterion("product_name_en =", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnNotEqualTo(String value) {
            addCriterion("product_name_en <>", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnGreaterThan(String value) {
            addCriterion("product_name_en >", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("product_name_en >=", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnLessThan(String value) {
            addCriterion("product_name_en <", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnLessThanOrEqualTo(String value) {
            addCriterion("product_name_en <=", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnLike(String value) {
            addCriterion("product_name_en like", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnNotLike(String value) {
            addCriterion("product_name_en not like", value, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnIn(List<String> values) {
            addCriterion("product_name_en in", values, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnNotIn(List<String> values) {
            addCriterion("product_name_en not in", values, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnBetween(String value1, String value2) {
            addCriterion("product_name_en between", value1, value2, "productNameEn");
            return (Criteria) this;
        }

        public Criteria andProductNameEnNotBetween(String value1, String value2) {
            addCriterion("product_name_en not between", value1, value2, "productNameEn");
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

        public Criteria andProdCountryIsNull() {
            addCriterion("prod_country is null");
            return (Criteria) this;
        }

        public Criteria andProdCountryIsNotNull() {
            addCriterion("prod_country is not null");
            return (Criteria) this;
        }

        public Criteria andProdCountryEqualTo(String value) {
            addCriterion("prod_country =", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryNotEqualTo(String value) {
            addCriterion("prod_country <>", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryGreaterThan(String value) {
            addCriterion("prod_country >", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryGreaterThanOrEqualTo(String value) {
            addCriterion("prod_country >=", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryLessThan(String value) {
            addCriterion("prod_country <", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryLessThanOrEqualTo(String value) {
            addCriterion("prod_country <=", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryLike(String value) {
            addCriterion("prod_country like", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryNotLike(String value) {
            addCriterion("prod_country not like", value, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryIn(List<String> values) {
            addCriterion("prod_country in", values, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryNotIn(List<String> values) {
            addCriterion("prod_country not in", values, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryBetween(String value1, String value2) {
            addCriterion("prod_country between", value1, value2, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andProdCountryNotBetween(String value1, String value2) {
            addCriterion("prod_country not between", value1, value2, "prodCountry");
            return (Criteria) this;
        }

        public Criteria andImpTypeIsNull() {
            addCriterion("imp_type is null");
            return (Criteria) this;
        }

        public Criteria andImpTypeIsNotNull() {
            addCriterion("imp_type is not null");
            return (Criteria) this;
        }

        public Criteria andImpTypeEqualTo(String value) {
            addCriterion("imp_type =", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotEqualTo(String value) {
            addCriterion("imp_type <>", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeGreaterThan(String value) {
            addCriterion("imp_type >", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeGreaterThanOrEqualTo(String value) {
            addCriterion("imp_type >=", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeLessThan(String value) {
            addCriterion("imp_type <", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeLessThanOrEqualTo(String value) {
            addCriterion("imp_type <=", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeLike(String value) {
            addCriterion("imp_type like", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotLike(String value) {
            addCriterion("imp_type not like", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeIn(List<String> values) {
            addCriterion("imp_type in", values, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotIn(List<String> values) {
            addCriterion("imp_type not in", values, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeBetween(String value1, String value2) {
            addCriterion("imp_type between", value1, value2, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotBetween(String value1, String value2) {
            addCriterion("imp_type not between", value1, value2, "impType");
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

        public Criteria andPriceTotalIsNull() {
            addCriterion("price_total is null");
            return (Criteria) this;
        }

        public Criteria andPriceTotalIsNotNull() {
            addCriterion("price_total is not null");
            return (Criteria) this;
        }

        public Criteria andPriceTotalEqualTo(BigDecimal value) {
            addCriterion("price_total =", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotEqualTo(BigDecimal value) {
            addCriterion("price_total <>", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalGreaterThan(BigDecimal value) {
            addCriterion("price_total >", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_total >=", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalLessThan(BigDecimal value) {
            addCriterion("price_total <", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_total <=", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalIn(List<BigDecimal> values) {
            addCriterion("price_total in", values, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotIn(List<BigDecimal> values) {
            addCriterion("price_total not in", values, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_total between", value1, value2, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_total not between", value1, value2, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalIsNull() {
            addCriterion("amount_total is null");
            return (Criteria) this;
        }

        public Criteria andAmountTotalIsNotNull() {
            addCriterion("amount_total is not null");
            return (Criteria) this;
        }

        public Criteria andAmountTotalEqualTo(BigDecimal value) {
            addCriterion("amount_total =", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalNotEqualTo(BigDecimal value) {
            addCriterion("amount_total <>", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalGreaterThan(BigDecimal value) {
            addCriterion("amount_total >", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_total >=", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalLessThan(BigDecimal value) {
            addCriterion("amount_total <", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_total <=", value, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalIn(List<BigDecimal> values) {
            addCriterion("amount_total in", values, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalNotIn(List<BigDecimal> values) {
            addCriterion("amount_total not in", values, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_total between", value1, value2, "amountTotal");
            return (Criteria) this;
        }

        public Criteria andAmountTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_total not between", value1, value2, "amountTotal");
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

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Short value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Short value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Short value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Short value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Short value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Short> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Short> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Short value1, Short value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Short value1, Short value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
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

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNull() {
            addCriterion("ecode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("ecode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("ecode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("ecode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("ecode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ecode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("ecode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("ecode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("ecode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("ecode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("ecode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("ecode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("ecode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("ecode not between", value1, value2, "ecode");
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
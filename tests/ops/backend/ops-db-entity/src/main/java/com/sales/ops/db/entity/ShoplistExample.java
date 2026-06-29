package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoplistExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShoplistExample() {
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

        public Criteria andInvoiceNoOrgIsNull() {
            addCriterion("invoice_no_org is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgIsNotNull() {
            addCriterion("invoice_no_org is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgEqualTo(String value) {
            addCriterion("invoice_no_org =", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgNotEqualTo(String value) {
            addCriterion("invoice_no_org <>", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgGreaterThan(String value) {
            addCriterion("invoice_no_org >", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no_org >=", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgLessThan(String value) {
            addCriterion("invoice_no_org <", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgLessThanOrEqualTo(String value) {
            addCriterion("invoice_no_org <=", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgLike(String value) {
            addCriterion("invoice_no_org like", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgNotLike(String value) {
            addCriterion("invoice_no_org not like", value, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgIn(List<String> values) {
            addCriterion("invoice_no_org in", values, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgNotIn(List<String> values) {
            addCriterion("invoice_no_org not in", values, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgBetween(String value1, String value2) {
            addCriterion("invoice_no_org between", value1, value2, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoOrgNotBetween(String value1, String value2) {
            addCriterion("invoice_no_org not between", value1, value2, "invoiceNoOrg");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIsNull() {
            addCriterion("supplier_no is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIsNotNull() {
            addCriterion("supplier_no is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoEqualTo(String value) {
            addCriterion("supplier_no =", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotEqualTo(String value) {
            addCriterion("supplier_no <>", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoGreaterThan(String value) {
            addCriterion("supplier_no >", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_no >=", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLessThan(String value) {
            addCriterion("supplier_no <", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLessThanOrEqualTo(String value) {
            addCriterion("supplier_no <=", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoLike(String value) {
            addCriterion("supplier_no like", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotLike(String value) {
            addCriterion("supplier_no not like", value, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoIn(List<String> values) {
            addCriterion("supplier_no in", values, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotIn(List<String> values) {
            addCriterion("supplier_no not in", values, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoBetween(String value1, String value2) {
            addCriterion("supplier_no between", value1, value2, "supplierNo");
            return (Criteria) this;
        }

        public Criteria andSupplierNoNotBetween(String value1, String value2) {
            addCriterion("supplier_no not between", value1, value2, "supplierNo");
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
            addCriterion("imp_date =", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotEqualTo(Date value) {
            addCriterion("imp_date <>", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateGreaterThan(Date value) {
            addCriterion("imp_date >", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateGreaterThanOrEqualTo(Date value) {
            addCriterion("imp_date >=", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateLessThan(Date value) {
            addCriterion("imp_date <", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateLessThanOrEqualTo(Date value) {
            addCriterion("imp_date <=", value, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateIn(List<Date> values) {
            addCriterion("imp_date in", values, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotIn(List<Date> values) {
            addCriterion("imp_date not in", values, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateBetween(Date value1, Date value2) {
            addCriterion("imp_date between", value1, value2, "impDate");
            return (Criteria) this;
        }

        public Criteria andImpDateNotBetween(Date value1, Date value2) {
            addCriterion("imp_date not between", value1, value2, "impDate");
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

        public Criteria andPriceOrgIsNull() {
            addCriterion("price_org is null");
            return (Criteria) this;
        }

        public Criteria andPriceOrgIsNotNull() {
            addCriterion("price_org is not null");
            return (Criteria) this;
        }

        public Criteria andPriceOrgEqualTo(BigDecimal value) {
            addCriterion("price_org =", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgNotEqualTo(BigDecimal value) {
            addCriterion("price_org <>", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgGreaterThan(BigDecimal value) {
            addCriterion("price_org >", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_org >=", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgLessThan(BigDecimal value) {
            addCriterion("price_org <", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_org <=", value, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgIn(List<BigDecimal> values) {
            addCriterion("price_org in", values, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgNotIn(List<BigDecimal> values) {
            addCriterion("price_org not in", values, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_org between", value1, value2, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andPriceOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_org not between", value1, value2, "priceOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgIsNull() {
            addCriterion("amount_org is null");
            return (Criteria) this;
        }

        public Criteria andAmountOrgIsNotNull() {
            addCriterion("amount_org is not null");
            return (Criteria) this;
        }

        public Criteria andAmountOrgEqualTo(BigDecimal value) {
            addCriterion("amount_org =", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgNotEqualTo(BigDecimal value) {
            addCriterion("amount_org <>", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgGreaterThan(BigDecimal value) {
            addCriterion("amount_org >", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_org >=", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgLessThan(BigDecimal value) {
            addCriterion("amount_org <", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_org <=", value, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgIn(List<BigDecimal> values) {
            addCriterion("amount_org in", values, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgNotIn(List<BigDecimal> values) {
            addCriterion("amount_org not in", values, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_org between", value1, value2, "amountOrg");
            return (Criteria) this;
        }

        public Criteria andAmountOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_org not between", value1, value2, "amountOrg");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoicenoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvoicenoExample() {
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

        public Criteria andNameEnIsNull() {
            addCriterion("name_en is null");
            return (Criteria) this;
        }

        public Criteria andNameEnIsNotNull() {
            addCriterion("name_en is not null");
            return (Criteria) this;
        }

        public Criteria andNameEnEqualTo(String value) {
            addCriterion("name_en =", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotEqualTo(String value) {
            addCriterion("name_en <>", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThan(String value) {
            addCriterion("name_en >", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("name_en >=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThan(String value) {
            addCriterion("name_en <", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThanOrEqualTo(String value) {
            addCriterion("name_en <=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLike(String value) {
            addCriterion("name_en like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotLike(String value) {
            addCriterion("name_en not like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnIn(List<String> values) {
            addCriterion("name_en in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotIn(List<String> values) {
            addCriterion("name_en not in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnBetween(String value1, String value2) {
            addCriterion("name_en between", value1, value2, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotBetween(String value1, String value2) {
            addCriterion("name_en not between", value1, value2, "nameEn");
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

        public Criteria andOptCodeIsNull() {
            addCriterion("opt_code is null");
            return (Criteria) this;
        }

        public Criteria andOptCodeIsNotNull() {
            addCriterion("opt_code is not null");
            return (Criteria) this;
        }

        public Criteria andOptCodeEqualTo(String value) {
            addCriterion("opt_code =", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotEqualTo(String value) {
            addCriterion("opt_code <>", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThan(String value) {
            addCriterion("opt_code >", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("opt_code >=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThan(String value) {
            addCriterion("opt_code <", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLessThanOrEqualTo(String value) {
            addCriterion("opt_code <=", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeLike(String value) {
            addCriterion("opt_code like", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotLike(String value) {
            addCriterion("opt_code not like", value, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeIn(List<String> values) {
            addCriterion("opt_code in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotIn(List<String> values) {
            addCriterion("opt_code not in", values, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeBetween(String value1, String value2) {
            addCriterion("opt_code between", value1, value2, "optCode");
            return (Criteria) this;
        }

        public Criteria andOptCodeNotBetween(String value1, String value2) {
            addCriterion("opt_code not between", value1, value2, "optCode");
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

        public Criteria andImpTypeIsNull() {
            addCriterion("imp_type is null");
            return (Criteria) this;
        }

        public Criteria andImpTypeIsNotNull() {
            addCriterion("imp_type is not null");
            return (Criteria) this;
        }

        public Criteria andImpTypeEqualTo(Short value) {
            addCriterion("imp_type =", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotEqualTo(Short value) {
            addCriterion("imp_type <>", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeGreaterThan(Short value) {
            addCriterion("imp_type >", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("imp_type >=", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeLessThan(Short value) {
            addCriterion("imp_type <", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeLessThanOrEqualTo(Short value) {
            addCriterion("imp_type <=", value, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeIn(List<Short> values) {
            addCriterion("imp_type in", values, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotIn(List<Short> values) {
            addCriterion("imp_type not in", values, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeBetween(Short value1, Short value2) {
            addCriterion("imp_type between", value1, value2, "impType");
            return (Criteria) this;
        }

        public Criteria andImpTypeNotBetween(Short value1, Short value2) {
            addCriterion("imp_type not between", value1, value2, "impType");
            return (Criteria) this;
        }

        public Criteria andTaxTariffIsNull() {
            addCriterion("tax_tariff is null");
            return (Criteria) this;
        }

        public Criteria andTaxTariffIsNotNull() {
            addCriterion("tax_tariff is not null");
            return (Criteria) this;
        }

        public Criteria andTaxTariffEqualTo(BigDecimal value) {
            addCriterion("tax_tariff =", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffNotEqualTo(BigDecimal value) {
            addCriterion("tax_tariff <>", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffGreaterThan(BigDecimal value) {
            addCriterion("tax_tariff >", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_tariff >=", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffLessThan(BigDecimal value) {
            addCriterion("tax_tariff <", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_tariff <=", value, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffIn(List<BigDecimal> values) {
            addCriterion("tax_tariff in", values, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffNotIn(List<BigDecimal> values) {
            addCriterion("tax_tariff not in", values, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_tariff between", value1, value2, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxTariffNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_tariff not between", value1, value2, "taxTariff");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedIsNull() {
            addCriterion("tax_value_added is null");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedIsNotNull() {
            addCriterion("tax_value_added is not null");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedEqualTo(BigDecimal value) {
            addCriterion("tax_value_added =", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedNotEqualTo(BigDecimal value) {
            addCriterion("tax_value_added <>", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedGreaterThan(BigDecimal value) {
            addCriterion("tax_value_added >", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_value_added >=", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedLessThan(BigDecimal value) {
            addCriterion("tax_value_added <", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_value_added <=", value, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedIn(List<BigDecimal> values) {
            addCriterion("tax_value_added in", values, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedNotIn(List<BigDecimal> values) {
            addCriterion("tax_value_added not in", values, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_value_added between", value1, value2, "taxValueAdded");
            return (Criteria) this;
        }

        public Criteria andTaxValueAddedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_value_added not between", value1, value2, "taxValueAdded");
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
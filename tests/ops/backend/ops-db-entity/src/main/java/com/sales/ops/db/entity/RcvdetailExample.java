package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RcvdetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvdetailExample() {
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

        public Criteria andRorderNoIsNull() {
            addCriterion("rorder_no is null");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNotNull() {
            addCriterion("rorder_no is not null");
            return (Criteria) this;
        }

        public Criteria andRorderNoEqualTo(String value) {
            addCriterion("rorder_no =", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotEqualTo(String value) {
            addCriterion("rorder_no <>", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThan(String value) {
            addCriterion("rorder_no >", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_no >=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThan(String value) {
            addCriterion("rorder_no <", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThanOrEqualTo(String value) {
            addCriterion("rorder_no <=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLike(String value) {
            addCriterion("rorder_no like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotLike(String value) {
            addCriterion("rorder_no not like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoIn(List<String> values) {
            addCriterion("rorder_no in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotIn(List<String> values) {
            addCriterion("rorder_no not in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoBetween(String value1, String value2) {
            addCriterion("rorder_no between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotBetween(String value1, String value2) {
            addCriterion("rorder_no not between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNull() {
            addCriterion("rorder_item is null");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNotNull() {
            addCriterion("rorder_item is not null");
            return (Criteria) this;
        }

        public Criteria andRorderItemEqualTo(Integer value) {
            addCriterion("rorder_item =", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotEqualTo(Integer value) {
            addCriterion("rorder_item <>", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThan(Integer value) {
            addCriterion("rorder_item >", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("rorder_item >=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThan(Integer value) {
            addCriterion("rorder_item <", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThanOrEqualTo(Integer value) {
            addCriterion("rorder_item <=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemIn(List<Integer> values) {
            addCriterion("rorder_item in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotIn(List<Integer> values) {
            addCriterion("rorder_item not in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item not between", value1, value2, "rorderItem");
            return (Criteria) this;
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

        public Criteria andRorderFnoIsNull() {
            addCriterion("rorder_fno is null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIsNotNull() {
            addCriterion("rorder_fno is not null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoEqualTo(String value) {
            addCriterion("rorder_fno =", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotEqualTo(String value) {
            addCriterion("rorder_fno <>", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThan(String value) {
            addCriterion("rorder_fno >", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_fno >=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThan(String value) {
            addCriterion("rorder_fno <", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThanOrEqualTo(String value) {
            addCriterion("rorder_fno <=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLike(String value) {
            addCriterion("rorder_fno like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotLike(String value) {
            addCriterion("rorder_fno not like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIn(List<String> values) {
            addCriterion("rorder_fno in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotIn(List<String> values) {
            addCriterion("rorder_fno not in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoBetween(String value1, String value2) {
            addCriterion("rorder_fno between", value1, value2, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotBetween(String value1, String value2) {
            addCriterion("rorder_fno not between", value1, value2, "rorderFno");
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

        public Criteria andPriceEnduserIsNull() {
            addCriterion("price_enduser is null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNotNull() {
            addCriterion("price_enduser is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserEqualTo(BigDecimal value) {
            addCriterion("price_enduser =", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotEqualTo(BigDecimal value) {
            addCriterion("price_enduser <>", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThan(BigDecimal value) {
            addCriterion("price_enduser >", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_enduser >=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThan(BigDecimal value) {
            addCriterion("price_enduser <", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_enduser <=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIn(List<BigDecimal> values) {
            addCriterion("price_enduser in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotIn(List<BigDecimal> values) {
            addCriterion("price_enduser not in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_enduser between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_enduser not between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("eprice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("eprice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("eprice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("eprice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("eprice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("eprice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("eprice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("eprice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("tax_rate is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("tax_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("tax_rate =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("tax_rate <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("tax_rate >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("tax_rate <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("tax_rate in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("tax_rate not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceIsNull() {
            addCriterion("ntax_pice is null");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceIsNotNull() {
            addCriterion("ntax_pice is not null");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceEqualTo(BigDecimal value) {
            addCriterion("ntax_pice =", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceNotEqualTo(BigDecimal value) {
            addCriterion("ntax_pice <>", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceGreaterThan(BigDecimal value) {
            addCriterion("ntax_pice >", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ntax_pice >=", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceLessThan(BigDecimal value) {
            addCriterion("ntax_pice <", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ntax_pice <=", value, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceIn(List<BigDecimal> values) {
            addCriterion("ntax_pice in", values, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceNotIn(List<BigDecimal> values) {
            addCriterion("ntax_pice not in", values, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ntax_pice between", value1, value2, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxPiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ntax_pice not between", value1, value2, "ntaxPice");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountIsNull() {
            addCriterion("ntax_amount is null");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountIsNotNull() {
            addCriterion("ntax_amount is not null");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountEqualTo(BigDecimal value) {
            addCriterion("ntax_amount =", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountNotEqualTo(BigDecimal value) {
            addCriterion("ntax_amount <>", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountGreaterThan(BigDecimal value) {
            addCriterion("ntax_amount >", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ntax_amount >=", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountLessThan(BigDecimal value) {
            addCriterion("ntax_amount <", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ntax_amount <=", value, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountIn(List<BigDecimal> values) {
            addCriterion("ntax_amount in", values, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountNotIn(List<BigDecimal> values) {
            addCriterion("ntax_amount not in", values, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ntax_amount between", value1, value2, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andNtaxAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ntax_amount not between", value1, value2, "ntaxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIsNull() {
            addCriterion("tax_amount is null");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIsNotNull() {
            addCriterion("tax_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTaxAmountEqualTo(BigDecimal value) {
            addCriterion("tax_amount =", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotEqualTo(BigDecimal value) {
            addCriterion("tax_amount <>", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountGreaterThan(BigDecimal value) {
            addCriterion("tax_amount >", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_amount >=", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountLessThan(BigDecimal value) {
            addCriterion("tax_amount <", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_amount <=", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIn(List<BigDecimal> values) {
            addCriterion("tax_amount in", values, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotIn(List<BigDecimal> values) {
            addCriterion("tax_amount not in", values, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_amount between", value1, value2, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_amount not between", value1, value2, "taxAmount");
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

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount not between", value1, value2, "discount");
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

        public Criteria andCdlvDateIsNull() {
            addCriterion("cdlv_date is null");
            return (Criteria) this;
        }

        public Criteria andCdlvDateIsNotNull() {
            addCriterion("cdlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andCdlvDateEqualTo(Date value) {
            addCriterion("cdlv_date =", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotEqualTo(Date value) {
            addCriterion("cdlv_date <>", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateGreaterThan(Date value) {
            addCriterion("cdlv_date >", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cdlv_date >=", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateLessThan(Date value) {
            addCriterion("cdlv_date <", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateLessThanOrEqualTo(Date value) {
            addCriterion("cdlv_date <=", value, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateIn(List<Date> values) {
            addCriterion("cdlv_date in", values, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotIn(List<Date> values) {
            addCriterion("cdlv_date not in", values, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateBetween(Date value1, Date value2) {
            addCriterion("cdlv_date between", value1, value2, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andCdlvDateNotBetween(Date value1, Date value2) {
            addCriterion("cdlv_date not between", value1, value2, "cdlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIsNull() {
            addCriterion("wms_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIsNotNull() {
            addCriterion("wms_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date =", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <>", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThan(Date value) {
            addCriterionForJDBCDate("wms_dlv_date >", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date >=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThan(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wms_dlv_date <=", value, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateIn(List<Date> values) {
            addCriterionForJDBCDate("wms_dlv_date in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wms_dlv_date not in", values, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wms_dlv_date between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wms_dlv_date not between", value1, value2, "wmsDlvDate");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoIsNull() {
            addCriterion("spec_offer_no is null");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoIsNotNull() {
            addCriterion("spec_offer_no is not null");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoEqualTo(String value) {
            addCriterion("spec_offer_no =", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoNotEqualTo(String value) {
            addCriterion("spec_offer_no <>", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoGreaterThan(String value) {
            addCriterion("spec_offer_no >", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoGreaterThanOrEqualTo(String value) {
            addCriterion("spec_offer_no >=", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoLessThan(String value) {
            addCriterion("spec_offer_no <", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoLessThanOrEqualTo(String value) {
            addCriterion("spec_offer_no <=", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoLike(String value) {
            addCriterion("spec_offer_no like", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoNotLike(String value) {
            addCriterion("spec_offer_no not like", value, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoIn(List<String> values) {
            addCriterion("spec_offer_no in", values, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoNotIn(List<String> values) {
            addCriterion("spec_offer_no not in", values, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoBetween(String value1, String value2) {
            addCriterion("spec_offer_no between", value1, value2, "specOfferNo");
            return (Criteria) this;
        }

        public Criteria andSpecOfferNoNotBetween(String value1, String value2) {
            addCriterion("spec_offer_no not between", value1, value2, "specOfferNo");
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

        public Criteria andCproductNoIsNull() {
            addCriterion("cproduct_no is null");
            return (Criteria) this;
        }

        public Criteria andCproductNoIsNotNull() {
            addCriterion("cproduct_no is not null");
            return (Criteria) this;
        }

        public Criteria andCproductNoEqualTo(String value) {
            addCriterion("cproduct_no =", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotEqualTo(String value) {
            addCriterion("cproduct_no <>", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoGreaterThan(String value) {
            addCriterion("cproduct_no >", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoGreaterThanOrEqualTo(String value) {
            addCriterion("cproduct_no >=", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLessThan(String value) {
            addCriterion("cproduct_no <", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLessThanOrEqualTo(String value) {
            addCriterion("cproduct_no <=", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoLike(String value) {
            addCriterion("cproduct_no like", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotLike(String value) {
            addCriterion("cproduct_no not like", value, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoIn(List<String> values) {
            addCriterion("cproduct_no in", values, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotIn(List<String> values) {
            addCriterion("cproduct_no not in", values, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoBetween(String value1, String value2) {
            addCriterion("cproduct_no between", value1, value2, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andCproductNoNotBetween(String value1, String value2) {
            addCriterion("cproduct_no not between", value1, value2, "cproductNo");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIsNull() {
            addCriterion("spec_mark is null");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIsNotNull() {
            addCriterion("spec_mark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecMarkEqualTo(String value) {
            addCriterion("spec_mark =", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotEqualTo(String value) {
            addCriterion("spec_mark <>", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkGreaterThan(String value) {
            addCriterion("spec_mark >", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkGreaterThanOrEqualTo(String value) {
            addCriterion("spec_mark >=", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLessThan(String value) {
            addCriterion("spec_mark <", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLessThanOrEqualTo(String value) {
            addCriterion("spec_mark <=", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkLike(String value) {
            addCriterion("spec_mark like", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotLike(String value) {
            addCriterion("spec_mark not like", value, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkIn(List<String> values) {
            addCriterion("spec_mark in", values, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotIn(List<String> values) {
            addCriterion("spec_mark not in", values, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkBetween(String value1, String value2) {
            addCriterion("spec_mark between", value1, value2, "specMark");
            return (Criteria) this;
        }

        public Criteria andSpecMarkNotBetween(String value1, String value2) {
            addCriterion("spec_mark not between", value1, value2, "specMark");
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

        public Criteria andOpponentIsNull() {
            addCriterion("opponent is null");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNotNull() {
            addCriterion("opponent is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentEqualTo(String value) {
            addCriterion("opponent =", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotEqualTo(String value) {
            addCriterion("opponent <>", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThan(String value) {
            addCriterion("opponent >", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThanOrEqualTo(String value) {
            addCriterion("opponent >=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThan(String value) {
            addCriterion("opponent <", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThanOrEqualTo(String value) {
            addCriterion("opponent <=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLike(String value) {
            addCriterion("opponent like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotLike(String value) {
            addCriterion("opponent not like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentIn(List<String> values) {
            addCriterion("opponent in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotIn(List<String> values) {
            addCriterion("opponent not in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentBetween(String value1, String value2) {
            addCriterion("opponent between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotBetween(String value1, String value2) {
            addCriterion("opponent not between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andPplNoIsNull() {
            addCriterion("ppl_no is null");
            return (Criteria) this;
        }

        public Criteria andPplNoIsNotNull() {
            addCriterion("ppl_no is not null");
            return (Criteria) this;
        }

        public Criteria andPplNoEqualTo(String value) {
            addCriterion("ppl_no =", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotEqualTo(String value) {
            addCriterion("ppl_no <>", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoGreaterThan(String value) {
            addCriterion("ppl_no >", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoGreaterThanOrEqualTo(String value) {
            addCriterion("ppl_no >=", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLessThan(String value) {
            addCriterion("ppl_no <", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLessThanOrEqualTo(String value) {
            addCriterion("ppl_no <=", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoLike(String value) {
            addCriterion("ppl_no like", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotLike(String value) {
            addCriterion("ppl_no not like", value, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoIn(List<String> values) {
            addCriterion("ppl_no in", values, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotIn(List<String> values) {
            addCriterion("ppl_no not in", values, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoBetween(String value1, String value2) {
            addCriterion("ppl_no between", value1, value2, "pplNo");
            return (Criteria) this;
        }

        public Criteria andPplNoNotBetween(String value1, String value2) {
            addCriterion("ppl_no not between", value1, value2, "pplNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNull() {
            addCriterion("project_no is null");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNotNull() {
            addCriterion("project_no is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNoEqualTo(String value) {
            addCriterion("project_no =", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotEqualTo(String value) {
            addCriterion("project_no <>", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThan(String value) {
            addCriterion("project_no >", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThanOrEqualTo(String value) {
            addCriterion("project_no >=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThan(String value) {
            addCriterion("project_no <", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThanOrEqualTo(String value) {
            addCriterion("project_no <=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLike(String value) {
            addCriterion("project_no like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotLike(String value) {
            addCriterion("project_no not like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIn(List<String> values) {
            addCriterion("project_no in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotIn(List<String> values) {
            addCriterion("project_no not in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoBetween(String value1, String value2) {
            addCriterion("project_no between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotBetween(String value1, String value2) {
            addCriterion("project_no not between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIsNull() {
            addCriterion("group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIsNotNull() {
            addCriterion("group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoEqualTo(String value) {
            addCriterion("group_customer_no =", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotEqualTo(String value) {
            addCriterion("group_customer_no <>", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThan(String value) {
            addCriterion("group_customer_no >", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("group_customer_no >=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThan(String value) {
            addCriterion("group_customer_no <", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("group_customer_no <=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLike(String value) {
            addCriterion("group_customer_no like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotLike(String value) {
            addCriterion("group_customer_no not like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIn(List<String> values) {
            addCriterion("group_customer_no in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotIn(List<String> values) {
            addCriterion("group_customer_no not in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoBetween(String value1, String value2) {
            addCriterion("group_customer_no between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotBetween(String value1, String value2) {
            addCriterion("group_customer_no not between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIsNull() {
            addCriterion("shikomi_no is null");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIsNotNull() {
            addCriterion("shikomi_no is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiNoEqualTo(String value) {
            addCriterion("shikomi_no =", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotEqualTo(String value) {
            addCriterion("shikomi_no <>", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoGreaterThan(String value) {
            addCriterion("shikomi_no >", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoGreaterThanOrEqualTo(String value) {
            addCriterion("shikomi_no >=", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLessThan(String value) {
            addCriterion("shikomi_no <", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLessThanOrEqualTo(String value) {
            addCriterion("shikomi_no <=", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoLike(String value) {
            addCriterion("shikomi_no like", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotLike(String value) {
            addCriterion("shikomi_no not like", value, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoIn(List<String> values) {
            addCriterion("shikomi_no in", values, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotIn(List<String> values) {
            addCriterion("shikomi_no not in", values, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoBetween(String value1, String value2) {
            addCriterion("shikomi_no between", value1, value2, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andShikomiNoNotBetween(String value1, String value2) {
            addCriterion("shikomi_no not between", value1, value2, "shikomiNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNull() {
            addCriterion("sales_info_no is null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNotNull() {
            addCriterion("sales_info_no is not null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoEqualTo(String value) {
            addCriterion("sales_info_no =", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotEqualTo(String value) {
            addCriterion("sales_info_no <>", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThan(String value) {
            addCriterion("sales_info_no >", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThanOrEqualTo(String value) {
            addCriterion("sales_info_no >=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThan(String value) {
            addCriterion("sales_info_no <", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThanOrEqualTo(String value) {
            addCriterion("sales_info_no <=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLike(String value) {
            addCriterion("sales_info_no like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotLike(String value) {
            addCriterion("sales_info_no not like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIn(List<String> values) {
            addCriterion("sales_info_no in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotIn(List<String> values) {
            addCriterion("sales_info_no not in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoBetween(String value1, String value2) {
            addCriterion("sales_info_no between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotBetween(String value1, String value2) {
            addCriterion("sales_info_no not between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoIsNull() {
            addCriterion("pre_sales_order_no is null");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoIsNotNull() {
            addCriterion("pre_sales_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoEqualTo(String value) {
            addCriterion("pre_sales_order_no =", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoNotEqualTo(String value) {
            addCriterion("pre_sales_order_no <>", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoGreaterThan(String value) {
            addCriterion("pre_sales_order_no >", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("pre_sales_order_no >=", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoLessThan(String value) {
            addCriterion("pre_sales_order_no <", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoLessThanOrEqualTo(String value) {
            addCriterion("pre_sales_order_no <=", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoLike(String value) {
            addCriterion("pre_sales_order_no like", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoNotLike(String value) {
            addCriterion("pre_sales_order_no not like", value, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoIn(List<String> values) {
            addCriterion("pre_sales_order_no in", values, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoNotIn(List<String> values) {
            addCriterion("pre_sales_order_no not in", values, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoBetween(String value1, String value2) {
            addCriterion("pre_sales_order_no between", value1, value2, "preSalesOrderNo");
            return (Criteria) this;
        }

        public Criteria andPreSalesOrderNoNotBetween(String value1, String value2) {
            addCriterion("pre_sales_order_no not between", value1, value2, "preSalesOrderNo");
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

        public Criteria andCustomCodeIsNull() {
            addCriterion("custom_code is null");
            return (Criteria) this;
        }

        public Criteria andCustomCodeIsNotNull() {
            addCriterion("custom_code is not null");
            return (Criteria) this;
        }

        public Criteria andCustomCodeEqualTo(String value) {
            addCriterion("custom_code =", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeNotEqualTo(String value) {
            addCriterion("custom_code <>", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeGreaterThan(String value) {
            addCriterion("custom_code >", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeGreaterThanOrEqualTo(String value) {
            addCriterion("custom_code >=", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeLessThan(String value) {
            addCriterion("custom_code <", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeLessThanOrEqualTo(String value) {
            addCriterion("custom_code <=", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeLike(String value) {
            addCriterion("custom_code like", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeNotLike(String value) {
            addCriterion("custom_code not like", value, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeIn(List<String> values) {
            addCriterion("custom_code in", values, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeNotIn(List<String> values) {
            addCriterion("custom_code not in", values, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeBetween(String value1, String value2) {
            addCriterion("custom_code between", value1, value2, "customCode");
            return (Criteria) this;
        }

        public Criteria andCustomCodeNotBetween(String value1, String value2) {
            addCriterion("custom_code not between", value1, value2, "customCode");
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

        public Criteria andDeleteStatusIsNull() {
            addCriterion("delete_status is null");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusIsNotNull() {
            addCriterion("delete_status is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusEqualTo(Short value) {
            addCriterion("delete_status =", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotEqualTo(Short value) {
            addCriterion("delete_status <>", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusGreaterThan(Short value) {
            addCriterion("delete_status >", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("delete_status >=", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusLessThan(Short value) {
            addCriterion("delete_status <", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusLessThanOrEqualTo(Short value) {
            addCriterion("delete_status <=", value, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusIn(List<Short> values) {
            addCriterion("delete_status in", values, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotIn(List<Short> values) {
            addCriterion("delete_status not in", values, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusBetween(Short value1, Short value2) {
            addCriterion("delete_status between", value1, value2, "deleteStatus");
            return (Criteria) this;
        }

        public Criteria andDeleteStatusNotBetween(Short value1, Short value2) {
            addCriterion("delete_status not between", value1, value2, "deleteStatus");
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

        public Criteria andStockTypeIsNull() {
            addCriterion("stock_type is null");
            return (Criteria) this;
        }

        public Criteria andStockTypeIsNotNull() {
            addCriterion("stock_type is not null");
            return (Criteria) this;
        }

        public Criteria andStockTypeEqualTo(String value) {
            addCriterion("stock_type =", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotEqualTo(String value) {
            addCriterion("stock_type <>", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThan(String value) {
            addCriterion("stock_type >", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_type >=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThan(String value) {
            addCriterion("stock_type <", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThanOrEqualTo(String value) {
            addCriterion("stock_type <=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLike(String value) {
            addCriterion("stock_type like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotLike(String value) {
            addCriterion("stock_type not like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeIn(List<String> values) {
            addCriterion("stock_type in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotIn(List<String> values) {
            addCriterion("stock_type not in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeBetween(String value1, String value2) {
            addCriterion("stock_type between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotBetween(String value1, String value2) {
            addCriterion("stock_type not between", value1, value2, "stockType");
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

        public Criteria andProdFlagIsNull() {
            addCriterion("prod_flag is null");
            return (Criteria) this;
        }

        public Criteria andProdFlagIsNotNull() {
            addCriterion("prod_flag is not null");
            return (Criteria) this;
        }

        public Criteria andProdFlagEqualTo(String value) {
            addCriterion("prod_flag =", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotEqualTo(String value) {
            addCriterion("prod_flag <>", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagGreaterThan(String value) {
            addCriterion("prod_flag >", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagGreaterThanOrEqualTo(String value) {
            addCriterion("prod_flag >=", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLessThan(String value) {
            addCriterion("prod_flag <", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLessThanOrEqualTo(String value) {
            addCriterion("prod_flag <=", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagLike(String value) {
            addCriterion("prod_flag like", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotLike(String value) {
            addCriterion("prod_flag not like", value, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagIn(List<String> values) {
            addCriterion("prod_flag in", values, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotIn(List<String> values) {
            addCriterion("prod_flag not in", values, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagBetween(String value1, String value2) {
            addCriterion("prod_flag between", value1, value2, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andProdFlagNotBetween(String value1, String value2) {
            addCriterion("prod_flag not between", value1, value2, "prodFlag");
            return (Criteria) this;
        }

        public Criteria andAllotTimeIsNull() {
            addCriterion("allot_time is null");
            return (Criteria) this;
        }

        public Criteria andAllotTimeIsNotNull() {
            addCriterion("allot_time is not null");
            return (Criteria) this;
        }

        public Criteria andAllotTimeEqualTo(Date value) {
            addCriterion("allot_time =", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeNotEqualTo(Date value) {
            addCriterion("allot_time <>", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeGreaterThan(Date value) {
            addCriterion("allot_time >", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("allot_time >=", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeLessThan(Date value) {
            addCriterion("allot_time <", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeLessThanOrEqualTo(Date value) {
            addCriterion("allot_time <=", value, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeIn(List<Date> values) {
            addCriterion("allot_time in", values, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeNotIn(List<Date> values) {
            addCriterion("allot_time not in", values, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeBetween(Date value1, Date value2) {
            addCriterion("allot_time between", value1, value2, "allotTime");
            return (Criteria) this;
        }

        public Criteria andAllotTimeNotBetween(Date value1, Date value2) {
            addCriterion("allot_time not between", value1, value2, "allotTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIsNull() {
            addCriterion("ready_time is null");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIsNotNull() {
            addCriterion("ready_time is not null");
            return (Criteria) this;
        }

        public Criteria andReadyTimeEqualTo(Date value) {
            addCriterion("ready_time =", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotEqualTo(Date value) {
            addCriterion("ready_time <>", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeGreaterThan(Date value) {
            addCriterion("ready_time >", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ready_time >=", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeLessThan(Date value) {
            addCriterion("ready_time <", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeLessThanOrEqualTo(Date value) {
            addCriterion("ready_time <=", value, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeIn(List<Date> values) {
            addCriterion("ready_time in", values, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotIn(List<Date> values) {
            addCriterion("ready_time not in", values, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeBetween(Date value1, Date value2) {
            addCriterion("ready_time between", value1, value2, "readyTime");
            return (Criteria) this;
        }

        public Criteria andReadyTimeNotBetween(Date value1, Date value2) {
            addCriterion("ready_time not between", value1, value2, "readyTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeIsNull() {
            addCriterion("exp_time is null");
            return (Criteria) this;
        }

        public Criteria andExpTimeIsNotNull() {
            addCriterion("exp_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpTimeEqualTo(Date value) {
            addCriterion("exp_time =", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotEqualTo(Date value) {
            addCriterion("exp_time <>", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeGreaterThan(Date value) {
            addCriterion("exp_time >", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("exp_time >=", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeLessThan(Date value) {
            addCriterion("exp_time <", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeLessThanOrEqualTo(Date value) {
            addCriterion("exp_time <=", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeIn(List<Date> values) {
            addCriterion("exp_time in", values, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotIn(List<Date> values) {
            addCriterion("exp_time not in", values, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeBetween(Date value1, Date value2) {
            addCriterion("exp_time between", value1, value2, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotBetween(Date value1, Date value2) {
            addCriterion("exp_time not between", value1, value2, "expTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeIsNull() {
            addCriterion("ship_time is null");
            return (Criteria) this;
        }

        public Criteria andShipTimeIsNotNull() {
            addCriterion("ship_time is not null");
            return (Criteria) this;
        }

        public Criteria andShipTimeEqualTo(Date value) {
            addCriterion("ship_time =", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotEqualTo(Date value) {
            addCriterion("ship_time <>", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeGreaterThan(Date value) {
            addCriterion("ship_time >", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_time >=", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeLessThan(Date value) {
            addCriterion("ship_time <", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeLessThanOrEqualTo(Date value) {
            addCriterion("ship_time <=", value, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeIn(List<Date> values) {
            addCriterion("ship_time in", values, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotIn(List<Date> values) {
            addCriterion("ship_time not in", values, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeBetween(Date value1, Date value2) {
            addCriterion("ship_time between", value1, value2, "shipTime");
            return (Criteria) this;
        }

        public Criteria andShipTimeNotBetween(Date value1, Date value2) {
            addCriterion("ship_time not between", value1, value2, "shipTime");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIsNull() {
            addCriterion("ready_qty is null");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIsNotNull() {
            addCriterion("ready_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReadyQtyEqualTo(Integer value) {
            addCriterion("ready_qty =", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotEqualTo(Integer value) {
            addCriterion("ready_qty <>", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyGreaterThan(Integer value) {
            addCriterion("ready_qty >", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ready_qty >=", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyLessThan(Integer value) {
            addCriterion("ready_qty <", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyLessThanOrEqualTo(Integer value) {
            addCriterion("ready_qty <=", value, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyIn(List<Integer> values) {
            addCriterion("ready_qty in", values, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotIn(List<Integer> values) {
            addCriterion("ready_qty not in", values, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyBetween(Integer value1, Integer value2) {
            addCriterion("ready_qty between", value1, value2, "readyQty");
            return (Criteria) this;
        }

        public Criteria andReadyQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ready_qty not between", value1, value2, "readyQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNull() {
            addCriterion("exp_qty is null");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNotNull() {
            addCriterion("exp_qty is not null");
            return (Criteria) this;
        }

        public Criteria andExpQtyEqualTo(Integer value) {
            addCriterion("exp_qty =", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotEqualTo(Integer value) {
            addCriterion("exp_qty <>", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThan(Integer value) {
            addCriterion("exp_qty >", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_qty >=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThan(Integer value) {
            addCriterion("exp_qty <", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThanOrEqualTo(Integer value) {
            addCriterion("exp_qty <=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyIn(List<Integer> values) {
            addCriterion("exp_qty in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotIn(List<Integer> values) {
            addCriterion("exp_qty not in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty not between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIsNull() {
            addCriterion("returned_qty is null");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIsNotNull() {
            addCriterion("returned_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyEqualTo(Integer value) {
            addCriterion("returned_qty =", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotEqualTo(Integer value) {
            addCriterion("returned_qty <>", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyGreaterThan(Integer value) {
            addCriterion("returned_qty >", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("returned_qty >=", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyLessThan(Integer value) {
            addCriterion("returned_qty <", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyLessThanOrEqualTo(Integer value) {
            addCriterion("returned_qty <=", value, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyIn(List<Integer> values) {
            addCriterion("returned_qty in", values, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotIn(List<Integer> values) {
            addCriterion("returned_qty not in", values, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyBetween(Integer value1, Integer value2) {
            addCriterion("returned_qty between", value1, value2, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andReturnedQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("returned_qty not between", value1, value2, "returnedQty");
            return (Criteria) this;
        }

        public Criteria andAddressNoIsNull() {
            addCriterion("address_no is null");
            return (Criteria) this;
        }

        public Criteria andAddressNoIsNotNull() {
            addCriterion("address_no is not null");
            return (Criteria) this;
        }

        public Criteria andAddressNoEqualTo(Integer value) {
            addCriterion("address_no =", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotEqualTo(Integer value) {
            addCriterion("address_no <>", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThan(Integer value) {
            addCriterion("address_no >", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("address_no >=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThan(Integer value) {
            addCriterion("address_no <", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThanOrEqualTo(Integer value) {
            addCriterion("address_no <=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoIn(List<Integer> values) {
            addCriterion("address_no in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotIn(List<Integer> values) {
            addCriterion("address_no not in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoBetween(Integer value1, Integer value2) {
            addCriterion("address_no between", value1, value2, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotBetween(Integer value1, Integer value2) {
            addCriterion("address_no not between", value1, value2, "addressNo");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNull() {
            addCriterion("process_date is null");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNotNull() {
            addCriterion("process_date is not null");
            return (Criteria) this;
        }

        public Criteria andProcessDateEqualTo(Date value) {
            addCriterion("process_date =", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotEqualTo(Date value) {
            addCriterion("process_date <>", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThan(Date value) {
            addCriterion("process_date >", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanOrEqualTo(Date value) {
            addCriterion("process_date >=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThan(Date value) {
            addCriterion("process_date <", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanOrEqualTo(Date value) {
            addCriterion("process_date <=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateIn(List<Date> values) {
            addCriterion("process_date in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotIn(List<Date> values) {
            addCriterion("process_date not in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateBetween(Date value1, Date value2) {
            addCriterion("process_date between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotBetween(Date value1, Date value2) {
            addCriterion("process_date not between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIsNull() {
            addCriterion("borrow_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIsNotNull() {
            addCriterion("borrow_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNoEqualTo(String value) {
            addCriterion("borrow_no =", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotEqualTo(String value) {
            addCriterion("borrow_no <>", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoGreaterThan(String value) {
            addCriterion("borrow_no >", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_no >=", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLessThan(String value) {
            addCriterion("borrow_no <", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLessThanOrEqualTo(String value) {
            addCriterion("borrow_no <=", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoLike(String value) {
            addCriterion("borrow_no like", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotLike(String value) {
            addCriterion("borrow_no not like", value, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoIn(List<String> values) {
            addCriterion("borrow_no in", values, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotIn(List<String> values) {
            addCriterion("borrow_no not in", values, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoBetween(String value1, String value2) {
            addCriterion("borrow_no between", value1, value2, "borrowNo");
            return (Criteria) this;
        }

        public Criteria andBorrowNoNotBetween(String value1, String value2) {
            addCriterion("borrow_no not between", value1, value2, "borrowNo");
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

        public Criteria andExpMsgIsNull() {
            addCriterion("exp_msg is null");
            return (Criteria) this;
        }

        public Criteria andExpMsgIsNotNull() {
            addCriterion("exp_msg is not null");
            return (Criteria) this;
        }

        public Criteria andExpMsgEqualTo(String value) {
            addCriterion("exp_msg =", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgNotEqualTo(String value) {
            addCriterion("exp_msg <>", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgGreaterThan(String value) {
            addCriterion("exp_msg >", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgGreaterThanOrEqualTo(String value) {
            addCriterion("exp_msg >=", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgLessThan(String value) {
            addCriterion("exp_msg <", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgLessThanOrEqualTo(String value) {
            addCriterion("exp_msg <=", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgLike(String value) {
            addCriterion("exp_msg like", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgNotLike(String value) {
            addCriterion("exp_msg not like", value, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgIn(List<String> values) {
            addCriterion("exp_msg in", values, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgNotIn(List<String> values) {
            addCriterion("exp_msg not in", values, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgBetween(String value1, String value2) {
            addCriterion("exp_msg between", value1, value2, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpMsgNotBetween(String value1, String value2) {
            addCriterion("exp_msg not between", value1, value2, "expMsg");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNull() {
            addCriterion("exp_dlv_type is null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNotNull() {
            addCriterion("exp_dlv_type is not null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeEqualTo(Integer value) {
            addCriterion("exp_dlv_type =", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotEqualTo(Integer value) {
            addCriterion("exp_dlv_type <>", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThan(Integer value) {
            addCriterion("exp_dlv_type >", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type >=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThan(Integer value) {
            addCriterion("exp_dlv_type <", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type <=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIn(List<Integer> values) {
            addCriterion("exp_dlv_type in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotIn(List<Integer> values) {
            addCriterion("exp_dlv_type not in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type between", value1, value2, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type not between", value1, value2, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIsNull() {
            addCriterion("exp_link_no is null");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIsNotNull() {
            addCriterion("exp_link_no is not null");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoEqualTo(String value) {
            addCriterion("exp_link_no =", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotEqualTo(String value) {
            addCriterion("exp_link_no <>", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoGreaterThan(String value) {
            addCriterion("exp_link_no >", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoGreaterThanOrEqualTo(String value) {
            addCriterion("exp_link_no >=", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLessThan(String value) {
            addCriterion("exp_link_no <", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLessThanOrEqualTo(String value) {
            addCriterion("exp_link_no <=", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLike(String value) {
            addCriterion("exp_link_no like", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotLike(String value) {
            addCriterion("exp_link_no not like", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIn(List<String> values) {
            addCriterion("exp_link_no in", values, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotIn(List<String> values) {
            addCriterion("exp_link_no not in", values, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoBetween(String value1, String value2) {
            addCriterion("exp_link_no between", value1, value2, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotBetween(String value1, String value2) {
            addCriterion("exp_link_no not between", value1, value2, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIsNull() {
            addCriterion("invoiceGroupKey is null");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIsNotNull() {
            addCriterion("invoiceGroupKey is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyEqualTo(String value) {
            addCriterion("invoiceGroupKey =", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotEqualTo(String value) {
            addCriterion("invoiceGroupKey <>", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyGreaterThan(String value) {
            addCriterion("invoiceGroupKey >", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceGroupKey >=", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLessThan(String value) {
            addCriterion("invoiceGroupKey <", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLessThanOrEqualTo(String value) {
            addCriterion("invoiceGroupKey <=", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyLike(String value) {
            addCriterion("invoiceGroupKey like", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotLike(String value) {
            addCriterion("invoiceGroupKey not like", value, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyIn(List<String> values) {
            addCriterion("invoiceGroupKey in", values, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotIn(List<String> values) {
            addCriterion("invoiceGroupKey not in", values, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyBetween(String value1, String value2) {
            addCriterion("invoiceGroupKey between", value1, value2, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoicegroupkeyNotBetween(String value1, String value2) {
            addCriterion("invoiceGroupKey not between", value1, value2, "invoicegroupkey");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNull() {
            addCriterion("invoice_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNotNull() {
            addCriterion("invoice_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyEqualTo(Integer value) {
            addCriterion("invoice_qty =", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotEqualTo(Integer value) {
            addCriterion("invoice_qty <>", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThan(Integer value) {
            addCriterion("invoice_qty >", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty >=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThan(Integer value) {
            addCriterion("invoice_qty <", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty <=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIn(List<Integer> values) {
            addCriterion("invoice_qty in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotIn(List<Integer> values) {
            addCriterion("invoice_qty not in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty not between", value1, value2, "invoiceQty");
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

        public Criteria andCarrieridIsNull() {
            addCriterion("carrierId is null");
            return (Criteria) this;
        }

        public Criteria andCarrieridIsNotNull() {
            addCriterion("carrierId is not null");
            return (Criteria) this;
        }

        public Criteria andCarrieridEqualTo(String value) {
            addCriterion("carrierId =", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotEqualTo(String value) {
            addCriterion("carrierId <>", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridGreaterThan(String value) {
            addCriterion("carrierId >", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridGreaterThanOrEqualTo(String value) {
            addCriterion("carrierId >=", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLessThan(String value) {
            addCriterion("carrierId <", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLessThanOrEqualTo(String value) {
            addCriterion("carrierId <=", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLike(String value) {
            addCriterion("carrierId like", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotLike(String value) {
            addCriterion("carrierId not like", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridIn(List<String> values) {
            addCriterion("carrierId in", values, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotIn(List<String> values) {
            addCriterion("carrierId not in", values, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridBetween(String value1, String value2) {
            addCriterion("carrierId between", value1, value2, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotBetween(String value1, String value2) {
            addCriterion("carrierId not between", value1, value2, "carrierid");
            return (Criteria) this;
        }

        public Criteria andExpressnoIsNull() {
            addCriterion("expressNo is null");
            return (Criteria) this;
        }

        public Criteria andExpressnoIsNotNull() {
            addCriterion("expressNo is not null");
            return (Criteria) this;
        }

        public Criteria andExpressnoEqualTo(String value) {
            addCriterion("expressNo =", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotEqualTo(String value) {
            addCriterion("expressNo <>", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoGreaterThan(String value) {
            addCriterion("expressNo >", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoGreaterThanOrEqualTo(String value) {
            addCriterion("expressNo >=", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLessThan(String value) {
            addCriterion("expressNo <", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLessThanOrEqualTo(String value) {
            addCriterion("expressNo <=", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLike(String value) {
            addCriterion("expressNo like", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotLike(String value) {
            addCriterion("expressNo not like", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoIn(List<String> values) {
            addCriterion("expressNo in", values, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotIn(List<String> values) {
            addCriterion("expressNo not in", values, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoBetween(String value1, String value2) {
            addCriterion("expressNo between", value1, value2, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotBetween(String value1, String value2) {
            addCriterion("expressNo not between", value1, value2, "expressno");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIsNull() {
            addCriterion("handover_time is null");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIsNotNull() {
            addCriterion("handover_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeEqualTo(Date value) {
            addCriterion("handover_time =", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotEqualTo(Date value) {
            addCriterion("handover_time <>", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeGreaterThan(Date value) {
            addCriterion("handover_time >", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("handover_time >=", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeLessThan(Date value) {
            addCriterion("handover_time <", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeLessThanOrEqualTo(Date value) {
            addCriterion("handover_time <=", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIn(List<Date> values) {
            addCriterion("handover_time in", values, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotIn(List<Date> values) {
            addCriterion("handover_time not in", values, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeBetween(Date value1, Date value2) {
            addCriterion("handover_time between", value1, value2, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotBetween(Date value1, Date value2) {
            addCriterion("handover_time not between", value1, value2, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andPriceUserIsNull() {
            addCriterion("price_user is null");
            return (Criteria) this;
        }

        public Criteria andPriceUserIsNotNull() {
            addCriterion("price_user is not null");
            return (Criteria) this;
        }

        public Criteria andPriceUserEqualTo(BigDecimal value) {
            addCriterion("price_user =", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserNotEqualTo(BigDecimal value) {
            addCriterion("price_user <>", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserGreaterThan(BigDecimal value) {
            addCriterion("price_user >", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price_user >=", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserLessThan(BigDecimal value) {
            addCriterion("price_user <", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price_user <=", value, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserIn(List<BigDecimal> values) {
            addCriterion("price_user in", values, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserNotIn(List<BigDecimal> values) {
            addCriterion("price_user not in", values, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_user between", value1, value2, "priceUser");
            return (Criteria) this;
        }

        public Criteria andPriceUserNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price_user not between", value1, value2, "priceUser");
            return (Criteria) this;
        }

        public Criteria andInterceptIsNull() {
            addCriterion("intercept is null");
            return (Criteria) this;
        }

        public Criteria andInterceptIsNotNull() {
            addCriterion("intercept is not null");
            return (Criteria) this;
        }

        public Criteria andInterceptEqualTo(Boolean value) {
            addCriterion("intercept =", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotEqualTo(Boolean value) {
            addCriterion("intercept <>", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptGreaterThan(Boolean value) {
            addCriterion("intercept >", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("intercept >=", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptLessThan(Boolean value) {
            addCriterion("intercept <", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptLessThanOrEqualTo(Boolean value) {
            addCriterion("intercept <=", value, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptIn(List<Boolean> values) {
            addCriterion("intercept in", values, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotIn(List<Boolean> values) {
            addCriterion("intercept not in", values, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptBetween(Boolean value1, Boolean value2) {
            addCriterion("intercept between", value1, value2, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("intercept not between", value1, value2, "intercept");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeIsNull() {
            addCriterion("intercept_time is null");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeIsNotNull() {
            addCriterion("intercept_time is not null");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeEqualTo(Date value) {
            addCriterion("intercept_time =", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeNotEqualTo(Date value) {
            addCriterion("intercept_time <>", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeGreaterThan(Date value) {
            addCriterion("intercept_time >", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("intercept_time >=", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeLessThan(Date value) {
            addCriterion("intercept_time <", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeLessThanOrEqualTo(Date value) {
            addCriterion("intercept_time <=", value, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeIn(List<Date> values) {
            addCriterion("intercept_time in", values, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeNotIn(List<Date> values) {
            addCriterion("intercept_time not in", values, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeBetween(Date value1, Date value2) {
            addCriterion("intercept_time between", value1, value2, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andInterceptTimeNotBetween(Date value1, Date value2) {
            addCriterion("intercept_time not between", value1, value2, "interceptTime");
            return (Criteria) this;
        }

        public Criteria andIndustryidIsNull() {
            addCriterion("industryId is null");
            return (Criteria) this;
        }

        public Criteria andIndustryidIsNotNull() {
            addCriterion("industryId is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryidEqualTo(String value) {
            addCriterion("industryId =", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotEqualTo(String value) {
            addCriterion("industryId <>", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidGreaterThan(String value) {
            addCriterion("industryId >", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidGreaterThanOrEqualTo(String value) {
            addCriterion("industryId >=", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLessThan(String value) {
            addCriterion("industryId <", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLessThanOrEqualTo(String value) {
            addCriterion("industryId <=", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLike(String value) {
            addCriterion("industryId like", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotLike(String value) {
            addCriterion("industryId not like", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidIn(List<String> values) {
            addCriterion("industryId in", values, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotIn(List<String> values) {
            addCriterion("industryId not in", values, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidBetween(String value1, String value2) {
            addCriterion("industryId between", value1, value2, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotBetween(String value1, String value2) {
            addCriterion("industryId not between", value1, value2, "industryid");
            return (Criteria) this;
        }

        public Criteria andCustomertypeIsNull() {
            addCriterion("customerType is null");
            return (Criteria) this;
        }

        public Criteria andCustomertypeIsNotNull() {
            addCriterion("customerType is not null");
            return (Criteria) this;
        }

        public Criteria andCustomertypeEqualTo(String value) {
            addCriterion("customerType =", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotEqualTo(String value) {
            addCriterion("customerType <>", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeGreaterThan(String value) {
            addCriterion("customerType >", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeGreaterThanOrEqualTo(String value) {
            addCriterion("customerType >=", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLessThan(String value) {
            addCriterion("customerType <", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLessThanOrEqualTo(String value) {
            addCriterion("customerType <=", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLike(String value) {
            addCriterion("customerType like", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotLike(String value) {
            addCriterion("customerType not like", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeIn(List<String> values) {
            addCriterion("customerType in", values, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotIn(List<String> values) {
            addCriterion("customerType not in", values, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeBetween(String value1, String value2) {
            addCriterion("customerType between", value1, value2, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotBetween(String value1, String value2) {
            addCriterion("customerType not between", value1, value2, "customertype");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIsNull() {
            addCriterion("expected_delivery_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIsNotNull() {
            addCriterion("expected_delivery_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeEqualTo(Date value) {
            addCriterion("expected_delivery_time =", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotEqualTo(Date value) {
            addCriterion("expected_delivery_time <>", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeGreaterThan(Date value) {
            addCriterion("expected_delivery_time >", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expected_delivery_time >=", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeLessThan(Date value) {
            addCriterion("expected_delivery_time <", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeLessThanOrEqualTo(Date value) {
            addCriterion("expected_delivery_time <=", value, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeIn(List<Date> values) {
            addCriterion("expected_delivery_time in", values, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotIn(List<Date> values) {
            addCriterion("expected_delivery_time not in", values, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeBetween(Date value1, Date value2) {
            addCriterion("expected_delivery_time between", value1, value2, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryTimeNotBetween(Date value1, Date value2) {
            addCriterion("expected_delivery_time not between", value1, value2, "expectedDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIsNull() {
            addCriterion("estimated_delivery_day is null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIsNotNull() {
            addCriterion("estimated_delivery_day is not null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayEqualTo(Date value) {
            addCriterion("estimated_delivery_day =", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotEqualTo(Date value) {
            addCriterion("estimated_delivery_day <>", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayGreaterThan(Date value) {
            addCriterion("estimated_delivery_day >", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayGreaterThanOrEqualTo(Date value) {
            addCriterion("estimated_delivery_day >=", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayLessThan(Date value) {
            addCriterion("estimated_delivery_day <", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayLessThanOrEqualTo(Date value) {
            addCriterion("estimated_delivery_day <=", value, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayIn(List<Date> values) {
            addCriterion("estimated_delivery_day in", values, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotIn(List<Date> values) {
            addCriterion("estimated_delivery_day not in", values, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayBetween(Date value1, Date value2) {
            addCriterion("estimated_delivery_day between", value1, value2, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEstimatedDeliveryDayNotBetween(Date value1, Date value2) {
            addCriterion("estimated_delivery_day not between", value1, value2, "estimatedDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIsNull() {
            addCriterion("inqb_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIsNotNull() {
            addCriterion("inqb_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoEqualTo(String value) {
            addCriterion("inqb_apply_no =", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotEqualTo(String value) {
            addCriterion("inqb_apply_no <>", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThan(String value) {
            addCriterion("inqb_apply_no >", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no >=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThan(String value) {
            addCriterion("inqb_apply_no <", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no <=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLike(String value) {
            addCriterion("inqb_apply_no like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotLike(String value) {
            addCriterion("inqb_apply_no not like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIn(List<String> values) {
            addCriterion("inqb_apply_no in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotIn(List<String> values) {
            addCriterion("inqb_apply_no not in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoBetween(String value1, String value2) {
            addCriterion("inqb_apply_no between", value1, value2, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotBetween(String value1, String value2) {
            addCriterion("inqb_apply_no not between", value1, value2, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andEntryTimeIsNull() {
            addCriterion("entry_time is null");
            return (Criteria) this;
        }

        public Criteria andEntryTimeIsNotNull() {
            addCriterion("entry_time is not null");
            return (Criteria) this;
        }

        public Criteria andEntryTimeEqualTo(Date value) {
            addCriterion("entry_time =", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeNotEqualTo(Date value) {
            addCriterion("entry_time <>", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeGreaterThan(Date value) {
            addCriterion("entry_time >", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("entry_time >=", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeLessThan(Date value) {
            addCriterion("entry_time <", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeLessThanOrEqualTo(Date value) {
            addCriterion("entry_time <=", value, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeIn(List<Date> values) {
            addCriterion("entry_time in", values, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeNotIn(List<Date> values) {
            addCriterion("entry_time not in", values, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeBetween(Date value1, Date value2) {
            addCriterion("entry_time between", value1, value2, "entryTime");
            return (Criteria) this;
        }

        public Criteria andEntryTimeNotBetween(Date value1, Date value2) {
            addCriterion("entry_time not between", value1, value2, "entryTime");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIsNull() {
            addCriterion("ext_order_no is null");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIsNotNull() {
            addCriterion("ext_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoEqualTo(String value) {
            addCriterion("ext_order_no =", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotEqualTo(String value) {
            addCriterion("ext_order_no <>", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoGreaterThan(String value) {
            addCriterion("ext_order_no >", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ext_order_no >=", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLessThan(String value) {
            addCriterion("ext_order_no <", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ext_order_no <=", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoLike(String value) {
            addCriterion("ext_order_no like", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotLike(String value) {
            addCriterion("ext_order_no not like", value, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoIn(List<String> values) {
            addCriterion("ext_order_no in", values, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotIn(List<String> values) {
            addCriterion("ext_order_no not in", values, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoBetween(String value1, String value2) {
            addCriterion("ext_order_no between", value1, value2, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderNoNotBetween(String value1, String value2) {
            addCriterion("ext_order_no not between", value1, value2, "extOrderNo");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNull() {
            addCriterion("ext_order_item is null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNotNull() {
            addCriterion("ext_order_item is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemEqualTo(String value) {
            addCriterion("ext_order_item =", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotEqualTo(String value) {
            addCriterion("ext_order_item <>", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThan(String value) {
            addCriterion("ext_order_item >", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("ext_order_item >=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThan(String value) {
            addCriterion("ext_order_item <", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThanOrEqualTo(String value) {
            addCriterion("ext_order_item <=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLike(String value) {
            addCriterion("ext_order_item like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotLike(String value) {
            addCriterion("ext_order_item not like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIn(List<String> values) {
            addCriterion("ext_order_item in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotIn(List<String> values) {
            addCriterion("ext_order_item not in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemBetween(String value1, String value2) {
            addCriterion("ext_order_item between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotBetween(String value1, String value2) {
            addCriterion("ext_order_item not between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoIsNull() {
            addCriterion("prepare_orderNo is null");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoIsNotNull() {
            addCriterion("prepare_orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoEqualTo(String value) {
            addCriterion("prepare_orderNo =", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoNotEqualTo(String value) {
            addCriterion("prepare_orderNo <>", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoGreaterThan(String value) {
            addCriterion("prepare_orderNo >", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("prepare_orderNo >=", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoLessThan(String value) {
            addCriterion("prepare_orderNo <", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoLessThanOrEqualTo(String value) {
            addCriterion("prepare_orderNo <=", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoLike(String value) {
            addCriterion("prepare_orderNo like", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoNotLike(String value) {
            addCriterion("prepare_orderNo not like", value, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoIn(List<String> values) {
            addCriterion("prepare_orderNo in", values, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoNotIn(List<String> values) {
            addCriterion("prepare_orderNo not in", values, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoBetween(String value1, String value2) {
            addCriterion("prepare_orderNo between", value1, value2, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andPrepareOrdernoNotBetween(String value1, String value2) {
            addCriterion("prepare_orderNo not between", value1, value2, "prepareOrderno");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeIsNull() {
            addCriterion("allot_start_time is null");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeIsNotNull() {
            addCriterion("allot_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeEqualTo(Date value) {
            addCriterion("allot_start_time =", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotEqualTo(Date value) {
            addCriterion("allot_start_time <>", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeGreaterThan(Date value) {
            addCriterion("allot_start_time >", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("allot_start_time >=", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeLessThan(Date value) {
            addCriterion("allot_start_time <", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("allot_start_time <=", value, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeIn(List<Date> values) {
            addCriterion("allot_start_time in", values, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotIn(List<Date> values) {
            addCriterion("allot_start_time not in", values, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeBetween(Date value1, Date value2) {
            addCriterion("allot_start_time between", value1, value2, "allotStartTime");
            return (Criteria) this;
        }

        public Criteria andAllotStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("allot_start_time not between", value1, value2, "allotStartTime");
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
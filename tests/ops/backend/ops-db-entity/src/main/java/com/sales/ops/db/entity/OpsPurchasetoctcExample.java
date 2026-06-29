package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsPurchasetoctcExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPurchasetoctcExample() {
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

        public Criteria andLineitemIsNull() {
            addCriterion("lineItem is null");
            return (Criteria) this;
        }

        public Criteria andLineitemIsNotNull() {
            addCriterion("lineItem is not null");
            return (Criteria) this;
        }

        public Criteria andLineitemEqualTo(Integer value) {
            addCriterion("lineItem =", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotEqualTo(Integer value) {
            addCriterion("lineItem <>", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThan(Integer value) {
            addCriterion("lineItem >", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("lineItem >=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThan(Integer value) {
            addCriterion("lineItem <", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThanOrEqualTo(Integer value) {
            addCriterion("lineItem <=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemIn(List<Integer> values) {
            addCriterion("lineItem in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotIn(List<Integer> values) {
            addCriterion("lineItem not in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemBetween(Integer value1, Integer value2) {
            addCriterion("lineItem between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotBetween(Integer value1, Integer value2) {
            addCriterion("lineItem not between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("itemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("itemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Integer value) {
            addCriterion("itemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Integer value) {
            addCriterion("itemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Integer value) {
            addCriterion("itemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Integer value) {
            addCriterion("itemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Integer value) {
            addCriterion("itemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Integer> values) {
            addCriterion("itemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Integer> values) {
            addCriterion("itemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Integer value1, Integer value2) {
            addCriterion("itemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("itemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNull() {
            addCriterion("splitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNotNull() {
            addCriterion("splitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoEqualTo(Integer value) {
            addCriterion("splitItemNo =", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotEqualTo(Integer value) {
            addCriterion("splitItemNo <>", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThan(Integer value) {
            addCriterion("splitItemNo >", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo >=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThan(Integer value) {
            addCriterion("splitItemNo <", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo <=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIn(List<Integer> values) {
            addCriterion("splitItemNo in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotIn(List<Integer> values) {
            addCriterion("splitItemNo not in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo not between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
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

        public Criteria andStdpriceIsNull() {
            addCriterion("stdPrice is null");
            return (Criteria) this;
        }

        public Criteria andStdpriceIsNotNull() {
            addCriterion("stdPrice is not null");
            return (Criteria) this;
        }

        public Criteria andStdpriceEqualTo(BigDecimal value) {
            addCriterion("stdPrice =", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotEqualTo(BigDecimal value) {
            addCriterion("stdPrice <>", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThan(BigDecimal value) {
            addCriterion("stdPrice >", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("stdPrice >=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThan(BigDecimal value) {
            addCriterion("stdPrice <", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("stdPrice <=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceIn(List<BigDecimal> values) {
            addCriterion("stdPrice in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotIn(List<BigDecimal> values) {
            addCriterion("stdPrice not in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stdPrice between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stdPrice not between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andPurchasedateIsNull() {
            addCriterion("purchaseDate is null");
            return (Criteria) this;
        }

        public Criteria andPurchasedateIsNotNull() {
            addCriterion("purchaseDate is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasedateEqualTo(Date value) {
            addCriterion("purchaseDate =", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateNotEqualTo(Date value) {
            addCriterion("purchaseDate <>", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateGreaterThan(Date value) {
            addCriterion("purchaseDate >", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateGreaterThanOrEqualTo(Date value) {
            addCriterion("purchaseDate >=", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateLessThan(Date value) {
            addCriterion("purchaseDate <", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateLessThanOrEqualTo(Date value) {
            addCriterion("purchaseDate <=", value, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateIn(List<Date> values) {
            addCriterion("purchaseDate in", values, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateNotIn(List<Date> values) {
            addCriterion("purchaseDate not in", values, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateBetween(Date value1, Date value2) {
            addCriterion("purchaseDate between", value1, value2, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andPurchasedateNotBetween(Date value1, Date value2) {
            addCriterion("purchaseDate not between", value1, value2, "purchasedate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNull() {
            addCriterion("hopeDeliveryDate is null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNotNull() {
            addCriterion("hopeDeliveryDate is not null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate =", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <>", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThan(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate >", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate >=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThan(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIn(List<Date> values) {
            addCriterionForJDBCDate("hopeDeliveryDate in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hopeDeliveryDate not in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeDeliveryDate between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeDeliveryDate not between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidIsNull() {
            addCriterion("receiveWarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidIsNotNull() {
            addCriterion("receiveWarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidEqualTo(String value) {
            addCriterion("receiveWarehouseId =", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidNotEqualTo(String value) {
            addCriterion("receiveWarehouseId <>", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidGreaterThan(String value) {
            addCriterion("receiveWarehouseId >", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("receiveWarehouseId >=", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidLessThan(String value) {
            addCriterion("receiveWarehouseId <", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidLessThanOrEqualTo(String value) {
            addCriterion("receiveWarehouseId <=", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidLike(String value) {
            addCriterion("receiveWarehouseId like", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidNotLike(String value) {
            addCriterion("receiveWarehouseId not like", value, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidIn(List<String> values) {
            addCriterion("receiveWarehouseId in", values, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidNotIn(List<String> values) {
            addCriterion("receiveWarehouseId not in", values, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidBetween(String value1, String value2) {
            addCriterion("receiveWarehouseId between", value1, value2, "receivewarehouseid");
            return (Criteria) this;
        }

        public Criteria andReceivewarehouseidNotBetween(String value1, String value2) {
            addCriterion("receiveWarehouseId not between", value1, value2, "receivewarehouseid");
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

        public Criteria andHopeexportdateIsNull() {
            addCriterion("hopeExportDate is null");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateIsNotNull() {
            addCriterion("hopeExportDate is not null");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate =", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate <>", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateGreaterThan(Date value) {
            addCriterionForJDBCDate("hopeExportDate >", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate >=", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateLessThan(Date value) {
            addCriterionForJDBCDate("hopeExportDate <", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate <=", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateIn(List<Date> values) {
            addCriterionForJDBCDate("hopeExportDate in", values, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hopeExportDate not in", values, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeExportDate between", value1, value2, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeExportDate not between", value1, value2, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andInqnoIsNull() {
            addCriterion("inqNo is null");
            return (Criteria) this;
        }

        public Criteria andInqnoIsNotNull() {
            addCriterion("inqNo is not null");
            return (Criteria) this;
        }

        public Criteria andInqnoEqualTo(String value) {
            addCriterion("inqNo =", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotEqualTo(String value) {
            addCriterion("inqNo <>", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoGreaterThan(String value) {
            addCriterion("inqNo >", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoGreaterThanOrEqualTo(String value) {
            addCriterion("inqNo >=", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLessThan(String value) {
            addCriterion("inqNo <", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLessThanOrEqualTo(String value) {
            addCriterion("inqNo <=", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLike(String value) {
            addCriterion("inqNo like", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotLike(String value) {
            addCriterion("inqNo not like", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoIn(List<String> values) {
            addCriterion("inqNo in", values, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotIn(List<String> values) {
            addCriterion("inqNo not in", values, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoBetween(String value1, String value2) {
            addCriterion("inqNo between", value1, value2, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotBetween(String value1, String value2) {
            addCriterion("inqNo not between", value1, value2, "inqno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIsNull() {
            addCriterion("shikomiAnswerNo is null");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIsNotNull() {
            addCriterion("shikomiAnswerNo is not null");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoEqualTo(String value) {
            addCriterion("shikomiAnswerNo =", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotEqualTo(String value) {
            addCriterion("shikomiAnswerNo <>", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoGreaterThan(String value) {
            addCriterion("shikomiAnswerNo >", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoGreaterThanOrEqualTo(String value) {
            addCriterion("shikomiAnswerNo >=", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLessThan(String value) {
            addCriterion("shikomiAnswerNo <", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLessThanOrEqualTo(String value) {
            addCriterion("shikomiAnswerNo <=", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLike(String value) {
            addCriterion("shikomiAnswerNo like", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotLike(String value) {
            addCriterion("shikomiAnswerNo not like", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIn(List<String> values) {
            addCriterion("shikomiAnswerNo in", values, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotIn(List<String> values) {
            addCriterion("shikomiAnswerNo not in", values, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoBetween(String value1, String value2) {
            addCriterion("shikomiAnswerNo between", value1, value2, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotBetween(String value1, String value2) {
            addCriterion("shikomiAnswerNo not between", value1, value2, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("deptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("deptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("deptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("deptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("deptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("deptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("deptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("deptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("deptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("deptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("deptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("deptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("deptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("deptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIsNull() {
            addCriterion("deliveryFlag is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIsNotNull() {
            addCriterion("deliveryFlag is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagEqualTo(String value) {
            addCriterion("deliveryFlag =", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotEqualTo(String value) {
            addCriterion("deliveryFlag <>", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagGreaterThan(String value) {
            addCriterion("deliveryFlag >", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagGreaterThanOrEqualTo(String value) {
            addCriterion("deliveryFlag >=", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLessThan(String value) {
            addCriterion("deliveryFlag <", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLessThanOrEqualTo(String value) {
            addCriterion("deliveryFlag <=", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLike(String value) {
            addCriterion("deliveryFlag like", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotLike(String value) {
            addCriterion("deliveryFlag not like", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIn(List<String> values) {
            addCriterion("deliveryFlag in", values, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotIn(List<String> values) {
            addCriterion("deliveryFlag not in", values, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagBetween(String value1, String value2) {
            addCriterion("deliveryFlag between", value1, value2, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotBetween(String value1, String value2) {
            addCriterion("deliveryFlag not between", value1, value2, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("smcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("smcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("smcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("smcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("smcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("smcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("smcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("smcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("smcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("smcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("smcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("smcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("smcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("smcCode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNull() {
            addCriterion("hsCode is null");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNotNull() {
            addCriterion("hsCode is not null");
            return (Criteria) this;
        }

        public Criteria andHscodeEqualTo(String value) {
            addCriterion("hsCode =", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotEqualTo(String value) {
            addCriterion("hsCode <>", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThan(String value) {
            addCriterion("hsCode >", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThanOrEqualTo(String value) {
            addCriterion("hsCode >=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThan(String value) {
            addCriterion("hsCode <", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThanOrEqualTo(String value) {
            addCriterion("hsCode <=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLike(String value) {
            addCriterion("hsCode like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotLike(String value) {
            addCriterion("hsCode not like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeIn(List<String> values) {
            addCriterion("hsCode in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotIn(List<String> values) {
            addCriterion("hsCode not in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeBetween(String value1, String value2) {
            addCriterion("hsCode between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotBetween(String value1, String value2) {
            addCriterion("hsCode not between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNull() {
            addCriterion("userNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("userNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("userNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("userNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("userNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("userNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("userNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("userNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("userNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("userNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("userNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("userNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("userNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("userNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNull() {
            addCriterion("locationNo is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNotNull() {
            addCriterion("locationNo is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoEqualTo(String value) {
            addCriterion("locationNo =", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotEqualTo(String value) {
            addCriterion("locationNo <>", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThan(String value) {
            addCriterion("locationNo >", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThanOrEqualTo(String value) {
            addCriterion("locationNo >=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThan(String value) {
            addCriterion("locationNo <", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThanOrEqualTo(String value) {
            addCriterion("locationNo <=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLike(String value) {
            addCriterion("locationNo like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotLike(String value) {
            addCriterion("locationNo not like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIn(List<String> values) {
            addCriterion("locationNo in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotIn(List<String> values) {
            addCriterion("locationNo not in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoBetween(String value1, String value2) {
            addCriterion("locationNo between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotBetween(String value1, String value2) {
            addCriterion("locationNo not between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNull() {
            addCriterion("vipCode is null");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNotNull() {
            addCriterion("vipCode is not null");
            return (Criteria) this;
        }

        public Criteria andVipcodeEqualTo(String value) {
            addCriterion("vipCode =", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotEqualTo(String value) {
            addCriterion("vipCode <>", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThan(String value) {
            addCriterion("vipCode >", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("vipCode >=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThan(String value) {
            addCriterion("vipCode <", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThanOrEqualTo(String value) {
            addCriterion("vipCode <=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLike(String value) {
            addCriterion("vipCode like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotLike(String value) {
            addCriterion("vipCode not like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeIn(List<String> values) {
            addCriterion("vipCode in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotIn(List<String> values) {
            addCriterion("vipCode not in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeBetween(String value1, String value2) {
            addCriterion("vipCode between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotBetween(String value1, String value2) {
            addCriterion("vipCode not between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNull() {
            addCriterion("salesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("salesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("salesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("salesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("salesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("salesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("salesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("salesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("salesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("salesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("salesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("salesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("salesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("salesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNull() {
            addCriterion("purchaseType is null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNotNull() {
            addCriterion("purchaseType is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeEqualTo(String value) {
            addCriterion("purchaseType =", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotEqualTo(String value) {
            addCriterion("purchaseType <>", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThan(String value) {
            addCriterion("purchaseType >", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseType >=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThan(String value) {
            addCriterion("purchaseType <", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThanOrEqualTo(String value) {
            addCriterion("purchaseType <=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLike(String value) {
            addCriterion("purchaseType like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotLike(String value) {
            addCriterion("purchaseType not like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIn(List<String> values) {
            addCriterion("purchaseType in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotIn(List<String> values) {
            addCriterion("purchaseType not in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeBetween(String value1, String value2) {
            addCriterion("purchaseType between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotBetween(String value1, String value2) {
            addCriterion("purchaseType not between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNull() {
            addCriterion("supplierPartNo is null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNotNull() {
            addCriterion("supplierPartNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoEqualTo(String value) {
            addCriterion("supplierPartNo =", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotEqualTo(String value) {
            addCriterion("supplierPartNo <>", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThan(String value) {
            addCriterion("supplierPartNo >", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThanOrEqualTo(String value) {
            addCriterion("supplierPartNo >=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThan(String value) {
            addCriterion("supplierPartNo <", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThanOrEqualTo(String value) {
            addCriterion("supplierPartNo <=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLike(String value) {
            addCriterion("supplierPartNo like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotLike(String value) {
            addCriterion("supplierPartNo not like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIn(List<String> values) {
            addCriterion("supplierPartNo in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotIn(List<String> values) {
            addCriterion("supplierPartNo not in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoBetween(String value1, String value2) {
            addCriterion("supplierPartNo between", value1, value2, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotBetween(String value1, String value2) {
            addCriterion("supplierPartNo not between", value1, value2, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNull() {
            addCriterion("importFobPriceOriginal is null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNotNull() {
            addCriterion("importFobPriceOriginal is not null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal =", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal <>", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThan(BigDecimal value) {
            addCriterion("importFobPriceOriginal >", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal >=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThan(BigDecimal value) {
            addCriterion("importFobPriceOriginal <", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal <=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIn(List<BigDecimal> values) {
            addCriterion("importFobPriceOriginal in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotIn(List<BigDecimal> values) {
            addCriterion("importFobPriceOriginal not in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("importFobPriceOriginal between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("importFobPriceOriginal not between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNull() {
            addCriterion("importCurrencyId is null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNotNull() {
            addCriterion("importCurrencyId is not null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidEqualTo(String value) {
            addCriterion("importCurrencyId =", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotEqualTo(String value) {
            addCriterion("importCurrencyId <>", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThan(String value) {
            addCriterion("importCurrencyId >", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThanOrEqualTo(String value) {
            addCriterion("importCurrencyId >=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThan(String value) {
            addCriterion("importCurrencyId <", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThanOrEqualTo(String value) {
            addCriterion("importCurrencyId <=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLike(String value) {
            addCriterion("importCurrencyId like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotLike(String value) {
            addCriterion("importCurrencyId not like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIn(List<String> values) {
            addCriterion("importCurrencyId in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotIn(List<String> values) {
            addCriterion("importCurrencyId not in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidBetween(String value1, String value2) {
            addCriterion("importCurrencyId between", value1, value2, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotBetween(String value1, String value2) {
            addCriterion("importCurrencyId not between", value1, value2, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andOperatoridIsNull() {
            addCriterion("operatorId is null");
            return (Criteria) this;
        }

        public Criteria andOperatoridIsNotNull() {
            addCriterion("operatorId is not null");
            return (Criteria) this;
        }

        public Criteria andOperatoridEqualTo(String value) {
            addCriterion("operatorId =", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridNotEqualTo(String value) {
            addCriterion("operatorId <>", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridGreaterThan(String value) {
            addCriterion("operatorId >", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridGreaterThanOrEqualTo(String value) {
            addCriterion("operatorId >=", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridLessThan(String value) {
            addCriterion("operatorId <", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridLessThanOrEqualTo(String value) {
            addCriterion("operatorId <=", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridLike(String value) {
            addCriterion("operatorId like", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridNotLike(String value) {
            addCriterion("operatorId not like", value, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridIn(List<String> values) {
            addCriterion("operatorId in", values, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridNotIn(List<String> values) {
            addCriterion("operatorId not in", values, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridBetween(String value1, String value2) {
            addCriterion("operatorId between", value1, value2, "operatorid");
            return (Criteria) this;
        }

        public Criteria andOperatoridNotBetween(String value1, String value2) {
            addCriterion("operatorId not between", value1, value2, "operatorid");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNull() {
            addCriterion("cnNo is null");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNotNull() {
            addCriterion("cnNo is not null");
            return (Criteria) this;
        }

        public Criteria andCnnoEqualTo(String value) {
            addCriterion("cnNo =", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotEqualTo(String value) {
            addCriterion("cnNo <>", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThan(String value) {
            addCriterion("cnNo >", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThanOrEqualTo(String value) {
            addCriterion("cnNo >=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThan(String value) {
            addCriterion("cnNo <", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThanOrEqualTo(String value) {
            addCriterion("cnNo <=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLike(String value) {
            addCriterion("cnNo like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotLike(String value) {
            addCriterion("cnNo not like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoIn(List<String> values) {
            addCriterion("cnNo in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotIn(List<String> values) {
            addCriterion("cnNo not in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoBetween(String value1, String value2) {
            addCriterion("cnNo between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotBetween(String value1, String value2) {
            addCriterion("cnNo not between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNull() {
            addCriterion("invoiceId is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNotNull() {
            addCriterion("invoiceId is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidEqualTo(Long value) {
            addCriterion("invoiceId =", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotEqualTo(Long value) {
            addCriterion("invoiceId <>", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThan(Long value) {
            addCriterion("invoiceId >", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThanOrEqualTo(Long value) {
            addCriterion("invoiceId >=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThan(Long value) {
            addCriterion("invoiceId <", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThanOrEqualTo(Long value) {
            addCriterion("invoiceId <=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIn(List<Long> values) {
            addCriterion("invoiceId in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotIn(List<Long> values) {
            addCriterion("invoiceId not in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidBetween(Long value1, Long value2) {
            addCriterion("invoiceId between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotBetween(Long value1, Long value2) {
            addCriterion("invoiceId not between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andSendtimeIsNull() {
            addCriterion("sendTime is null");
            return (Criteria) this;
        }

        public Criteria andSendtimeIsNotNull() {
            addCriterion("sendTime is not null");
            return (Criteria) this;
        }

        public Criteria andSendtimeEqualTo(Date value) {
            addCriterion("sendTime =", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeNotEqualTo(Date value) {
            addCriterion("sendTime <>", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeGreaterThan(Date value) {
            addCriterion("sendTime >", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sendTime >=", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeLessThan(Date value) {
            addCriterion("sendTime <", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeLessThanOrEqualTo(Date value) {
            addCriterion("sendTime <=", value, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeIn(List<Date> values) {
            addCriterion("sendTime in", values, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeNotIn(List<Date> values) {
            addCriterion("sendTime not in", values, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeBetween(Date value1, Date value2) {
            addCriterion("sendTime between", value1, value2, "sendtime");
            return (Criteria) this;
        }

        public Criteria andSendtimeNotBetween(Date value1, Date value2) {
            addCriterion("sendTime not between", value1, value2, "sendtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDealtypeIsNull() {
            addCriterion("dealtype is null");
            return (Criteria) this;
        }

        public Criteria andDealtypeIsNotNull() {
            addCriterion("dealtype is not null");
            return (Criteria) this;
        }

        public Criteria andDealtypeEqualTo(String value) {
            addCriterion("dealtype =", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeNotEqualTo(String value) {
            addCriterion("dealtype <>", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeGreaterThan(String value) {
            addCriterion("dealtype >", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeGreaterThanOrEqualTo(String value) {
            addCriterion("dealtype >=", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeLessThan(String value) {
            addCriterion("dealtype <", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeLessThanOrEqualTo(String value) {
            addCriterion("dealtype <=", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeLike(String value) {
            addCriterion("dealtype like", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeNotLike(String value) {
            addCriterion("dealtype not like", value, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeIn(List<String> values) {
            addCriterion("dealtype in", values, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeNotIn(List<String> values) {
            addCriterion("dealtype not in", values, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeBetween(String value1, String value2) {
            addCriterion("dealtype between", value1, value2, "dealtype");
            return (Criteria) this;
        }

        public Criteria andDealtypeNotBetween(String value1, String value2) {
            addCriterion("dealtype not between", value1, value2, "dealtype");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNull() {
            addCriterion("error_msg is null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNotNull() {
            addCriterion("error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgEqualTo(String value) {
            addCriterion("error_msg =", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotEqualTo(String value) {
            addCriterion("error_msg <>", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThan(String value) {
            addCriterion("error_msg >", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("error_msg >=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThan(String value) {
            addCriterion("error_msg <", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("error_msg <=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLike(String value) {
            addCriterion("error_msg like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotLike(String value) {
            addCriterion("error_msg not like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIn(List<String> values) {
            addCriterion("error_msg in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotIn(List<String> values) {
            addCriterion("error_msg not in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgBetween(String value1, String value2) {
            addCriterion("error_msg between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotBetween(String value1, String value2) {
            addCriterion("error_msg not between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andFinishdateIsNull() {
            addCriterion("finishDate is null");
            return (Criteria) this;
        }

        public Criteria andFinishdateIsNotNull() {
            addCriterion("finishDate is not null");
            return (Criteria) this;
        }

        public Criteria andFinishdateEqualTo(Date value) {
            addCriterion("finishDate =", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotEqualTo(Date value) {
            addCriterion("finishDate <>", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateGreaterThan(Date value) {
            addCriterion("finishDate >", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateGreaterThanOrEqualTo(Date value) {
            addCriterion("finishDate >=", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateLessThan(Date value) {
            addCriterion("finishDate <", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateLessThanOrEqualTo(Date value) {
            addCriterion("finishDate <=", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateIn(List<Date> values) {
            addCriterion("finishDate in", values, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotIn(List<Date> values) {
            addCriterion("finishDate not in", values, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateBetween(Date value1, Date value2) {
            addCriterion("finishDate between", value1, value2, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotBetween(Date value1, Date value2) {
            addCriterion("finishDate not between", value1, value2, "finishdate");
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
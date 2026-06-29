package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StockAdjustExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StockAdjustExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andFullordernoIsNull() {
            addCriterion("fullOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andFullordernoIsNotNull() {
            addCriterion("fullOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andFullordernoEqualTo(String value) {
            addCriterion("fullOrderNo =", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotEqualTo(String value) {
            addCriterion("fullOrderNo <>", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoGreaterThan(String value) {
            addCriterion("fullOrderNo >", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoGreaterThanOrEqualTo(String value) {
            addCriterion("fullOrderNo >=", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLessThan(String value) {
            addCriterion("fullOrderNo <", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLessThanOrEqualTo(String value) {
            addCriterion("fullOrderNo <=", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoLike(String value) {
            addCriterion("fullOrderNo like", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotLike(String value) {
            addCriterion("fullOrderNo not like", value, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoIn(List<String> values) {
            addCriterion("fullOrderNo in", values, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotIn(List<String> values) {
            addCriterion("fullOrderNo not in", values, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoBetween(String value1, String value2) {
            addCriterion("fullOrderNo between", value1, value2, "fullorderno");
            return (Criteria) this;
        }

        public Criteria andFullordernoNotBetween(String value1, String value2) {
            addCriterion("fullOrderNo not between", value1, value2, "fullorderno");
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

        public Criteria andAdjusttypeIsNull() {
            addCriterion("adjustType is null");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeIsNotNull() {
            addCriterion("adjustType is not null");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeEqualTo(Integer value) {
            addCriterion("adjustType =", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeNotEqualTo(Integer value) {
            addCriterion("adjustType <>", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeGreaterThan(Integer value) {
            addCriterion("adjustType >", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("adjustType >=", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeLessThan(Integer value) {
            addCriterion("adjustType <", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeLessThanOrEqualTo(Integer value) {
            addCriterion("adjustType <=", value, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeIn(List<Integer> values) {
            addCriterion("adjustType in", values, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeNotIn(List<Integer> values) {
            addCriterion("adjustType not in", values, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeBetween(Integer value1, Integer value2) {
            addCriterion("adjustType between", value1, value2, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andAdjusttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("adjustType not between", value1, value2, "adjusttype");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNull() {
            addCriterion("optCode is null");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNotNull() {
            addCriterion("optCode is not null");
            return (Criteria) this;
        }

        public Criteria andOptcodeEqualTo(Integer value) {
            addCriterion("optCode =", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotEqualTo(Integer value) {
            addCriterion("optCode <>", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThan(Integer value) {
            addCriterion("optCode >", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("optCode >=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThan(Integer value) {
            addCriterion("optCode <", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThanOrEqualTo(Integer value) {
            addCriterion("optCode <=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeIn(List<Integer> values) {
            addCriterion("optCode in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotIn(List<Integer> values) {
            addCriterion("optCode not in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeBetween(Integer value1, Integer value2) {
            addCriterion("optCode between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotBetween(Integer value1, Integer value2) {
            addCriterion("optCode not between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
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

        public Criteria andCreateuserIsNull() {
            addCriterion("createUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("createUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(String value) {
            addCriterion("createUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(String value) {
            addCriterion("createUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(String value) {
            addCriterion("createUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(String value) {
            addCriterion("createUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(String value) {
            addCriterion("createUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(String value) {
            addCriterion("createUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLike(String value) {
            addCriterion("createUser like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotLike(String value) {
            addCriterion("createUser not like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<String> values) {
            addCriterion("createUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<String> values) {
            addCriterion("createUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(String value1, String value2) {
            addCriterion("createUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(String value1, String value2) {
            addCriterion("createUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNull() {
            addCriterion("updateUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNotNull() {
            addCriterion("updateUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserEqualTo(String value) {
            addCriterion("updateUser =", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotEqualTo(String value) {
            addCriterion("updateUser <>", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThan(String value) {
            addCriterion("updateUser >", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThanOrEqualTo(String value) {
            addCriterion("updateUser >=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThan(String value) {
            addCriterion("updateUser <", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThanOrEqualTo(String value) {
            addCriterion("updateUser <=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLike(String value) {
            addCriterion("updateUser like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotLike(String value) {
            addCriterion("updateUser not like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIn(List<String> values) {
            addCriterion("updateUser in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotIn(List<String> values) {
            addCriterion("updateUser not in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserBetween(String value1, String value2) {
            addCriterion("updateUser between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotBetween(String value1, String value2) {
            addCriterion("updateUser not between", value1, value2, "updateuser");
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

        public Criteria andWarehousecodeIsNull() {
            addCriterion("warehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeIsNotNull() {
            addCriterion("warehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeEqualTo(String value) {
            addCriterion("warehouseCode =", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotEqualTo(String value) {
            addCriterion("warehouseCode <>", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeGreaterThan(String value) {
            addCriterion("warehouseCode >", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouseCode >=", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLessThan(String value) {
            addCriterion("warehouseCode <", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("warehouseCode <=", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLike(String value) {
            addCriterion("warehouseCode like", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotLike(String value) {
            addCriterion("warehouseCode not like", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeIn(List<String> values) {
            addCriterion("warehouseCode in", values, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotIn(List<String> values) {
            addCriterion("warehouseCode not in", values, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeBetween(String value1, String value2) {
            addCriterion("warehouseCode between", value1, value2, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotBetween(String value1, String value2) {
            addCriterion("warehouseCode not between", value1, value2, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andPplnoIsNull() {
            addCriterion("pplNo is null");
            return (Criteria) this;
        }

        public Criteria andPplnoIsNotNull() {
            addCriterion("pplNo is not null");
            return (Criteria) this;
        }

        public Criteria andPplnoEqualTo(String value) {
            addCriterion("pplNo =", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotEqualTo(String value) {
            addCriterion("pplNo <>", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoGreaterThan(String value) {
            addCriterion("pplNo >", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoGreaterThanOrEqualTo(String value) {
            addCriterion("pplNo >=", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLessThan(String value) {
            addCriterion("pplNo <", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLessThanOrEqualTo(String value) {
            addCriterion("pplNo <=", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLike(String value) {
            addCriterion("pplNo like", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotLike(String value) {
            addCriterion("pplNo not like", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoIn(List<String> values) {
            addCriterion("pplNo in", values, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotIn(List<String> values) {
            addCriterion("pplNo not in", values, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoBetween(String value1, String value2) {
            addCriterion("pplNo between", value1, value2, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotBetween(String value1, String value2) {
            addCriterion("pplNo not between", value1, value2, "pplno");
            return (Criteria) this;
        }

        public Criteria andProjectnoIsNull() {
            addCriterion("projectNo is null");
            return (Criteria) this;
        }

        public Criteria andProjectnoIsNotNull() {
            addCriterion("projectNo is not null");
            return (Criteria) this;
        }

        public Criteria andProjectnoEqualTo(String value) {
            addCriterion("projectNo =", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotEqualTo(String value) {
            addCriterion("projectNo <>", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoGreaterThan(String value) {
            addCriterion("projectNo >", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoGreaterThanOrEqualTo(String value) {
            addCriterion("projectNo >=", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLessThan(String value) {
            addCriterion("projectNo <", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLessThanOrEqualTo(String value) {
            addCriterion("projectNo <=", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLike(String value) {
            addCriterion("projectNo like", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotLike(String value) {
            addCriterion("projectNo not like", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoIn(List<String> values) {
            addCriterion("projectNo in", values, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotIn(List<String> values) {
            addCriterion("projectNo not in", values, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoBetween(String value1, String value2) {
            addCriterion("projectNo between", value1, value2, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotBetween(String value1, String value2) {
            addCriterion("projectNo not between", value1, value2, "projectno");
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

        public Criteria andGroupcustomernoIsNull() {
            addCriterion("groupCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIsNotNull() {
            addCriterion("groupCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoEqualTo(String value) {
            addCriterion("groupCustomerNo =", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotEqualTo(String value) {
            addCriterion("groupCustomerNo <>", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThan(String value) {
            addCriterion("groupCustomerNo >", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo >=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThan(String value) {
            addCriterion("groupCustomerNo <", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo <=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLike(String value) {
            addCriterion("groupCustomerNo like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotLike(String value) {
            addCriterion("groupCustomerNo not like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIn(List<String> values) {
            addCriterion("groupCustomerNo in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotIn(List<String> values) {
            addCriterion("groupCustomerNo not in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoBetween(String value1, String value2) {
            addCriterion("groupCustomerNo between", value1, value2, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotBetween(String value1, String value2) {
            addCriterion("groupCustomerNo not between", value1, value2, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNull() {
            addCriterion("inventoryTypeCode is null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNotNull() {
            addCriterion("inventoryTypeCode is not null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeEqualTo(String value) {
            addCriterion("inventoryTypeCode =", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotEqualTo(String value) {
            addCriterion("inventoryTypeCode <>", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThan(String value) {
            addCriterion("inventoryTypeCode >", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode >=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThan(String value) {
            addCriterion("inventoryTypeCode <", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode <=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLike(String value) {
            addCriterion("inventoryTypeCode like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotLike(String value) {
            addCriterion("inventoryTypeCode not like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIn(List<String> values) {
            addCriterion("inventoryTypeCode in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotIn(List<String> values) {
            addCriterion("inventoryTypeCode not in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode between", value1, value2, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode not between", value1, value2, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNull() {
            addCriterion("inventoryPropertyId is null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNotNull() {
            addCriterion("inventoryPropertyId is not null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidEqualTo(Long value) {
            addCriterion("inventoryPropertyId =", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotEqualTo(Long value) {
            addCriterion("inventoryPropertyId <>", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThan(Long value) {
            addCriterion("inventoryPropertyId >", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThanOrEqualTo(Long value) {
            addCriterion("inventoryPropertyId >=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThan(Long value) {
            addCriterion("inventoryPropertyId <", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThanOrEqualTo(Long value) {
            addCriterion("inventoryPropertyId <=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIn(List<Long> values) {
            addCriterion("inventoryPropertyId in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotIn(List<Long> values) {
            addCriterion("inventoryPropertyId not in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidBetween(Long value1, Long value2) {
            addCriterion("inventoryPropertyId between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotBetween(Long value1, Long value2) {
            addCriterion("inventoryPropertyId not between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andAdjustdateIsNull() {
            addCriterion("adjustDate is null");
            return (Criteria) this;
        }

        public Criteria andAdjustdateIsNotNull() {
            addCriterion("adjustDate is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustdateEqualTo(Date value) {
            addCriterionForJDBCDate("adjustDate =", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("adjustDate <>", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateGreaterThan(Date value) {
            addCriterionForJDBCDate("adjustDate >", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("adjustDate >=", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateLessThan(Date value) {
            addCriterionForJDBCDate("adjustDate <", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("adjustDate <=", value, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateIn(List<Date> values) {
            addCriterionForJDBCDate("adjustDate in", values, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("adjustDate not in", values, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("adjustDate between", value1, value2, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andAdjustdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("adjustDate not between", value1, value2, "adjustdate");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNull() {
            addCriterion("supplierCode is null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNotNull() {
            addCriterion("supplierCode is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeEqualTo(String value) {
            addCriterion("supplierCode =", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotEqualTo(String value) {
            addCriterion("supplierCode <>", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThan(String value) {
            addCriterion("supplierCode >", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplierCode >=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThan(String value) {
            addCriterion("supplierCode <", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThanOrEqualTo(String value) {
            addCriterion("supplierCode <=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLike(String value) {
            addCriterion("supplierCode like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotLike(String value) {
            addCriterion("supplierCode not like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIn(List<String> values) {
            addCriterion("supplierCode in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotIn(List<String> values) {
            addCriterion("supplierCode not in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeBetween(String value1, String value2) {
            addCriterion("supplierCode between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotBetween(String value1, String value2) {
            addCriterion("supplierCode not between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andExchangerateIsNull() {
            addCriterion("exchangeRate is null");
            return (Criteria) this;
        }

        public Criteria andExchangerateIsNotNull() {
            addCriterion("exchangeRate is not null");
            return (Criteria) this;
        }

        public Criteria andExchangerateEqualTo(BigDecimal value) {
            addCriterion("exchangeRate =", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateNotEqualTo(BigDecimal value) {
            addCriterion("exchangeRate <>", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateGreaterThan(BigDecimal value) {
            addCriterion("exchangeRate >", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exchangeRate >=", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateLessThan(BigDecimal value) {
            addCriterion("exchangeRate <", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exchangeRate <=", value, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateIn(List<BigDecimal> values) {
            addCriterion("exchangeRate in", values, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateNotIn(List<BigDecimal> values) {
            addCriterion("exchangeRate not in", values, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchangeRate between", value1, value2, "exchangerate");
            return (Criteria) this;
        }

        public Criteria andExchangerateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchangeRate not between", value1, value2, "exchangerate");
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
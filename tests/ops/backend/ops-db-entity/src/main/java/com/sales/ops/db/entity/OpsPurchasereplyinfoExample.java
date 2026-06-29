package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsPurchasereplyinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPurchasereplyinfoExample() {
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

        public Criteria andTranstypeIsNull() {
            addCriterion("transType is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("transType is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("transType =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("transType <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("transType >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("transType >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("transType <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("transType <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("transType like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("transType not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("transType in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("transType not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("transType between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("transType not between", value1, value2, "transtype");
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

        public Criteria andReplyordernoIsNull() {
            addCriterion("replyOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andReplyordernoIsNotNull() {
            addCriterion("replyOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andReplyordernoEqualTo(String value) {
            addCriterion("replyOrderNo =", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoNotEqualTo(String value) {
            addCriterion("replyOrderNo <>", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoGreaterThan(String value) {
            addCriterion("replyOrderNo >", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoGreaterThanOrEqualTo(String value) {
            addCriterion("replyOrderNo >=", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoLessThan(String value) {
            addCriterion("replyOrderNo <", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoLessThanOrEqualTo(String value) {
            addCriterion("replyOrderNo <=", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoLike(String value) {
            addCriterion("replyOrderNo like", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoNotLike(String value) {
            addCriterion("replyOrderNo not like", value, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoIn(List<String> values) {
            addCriterion("replyOrderNo in", values, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoNotIn(List<String> values) {
            addCriterion("replyOrderNo not in", values, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoBetween(String value1, String value2) {
            addCriterion("replyOrderNo between", value1, value2, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyordernoNotBetween(String value1, String value2) {
            addCriterion("replyOrderNo not between", value1, value2, "replyorderno");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateIsNull() {
            addCriterion("replyOrderDate is null");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateIsNotNull() {
            addCriterion("replyOrderDate is not null");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateEqualTo(Date value) {
            addCriterionForJDBCDate("replyOrderDate =", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("replyOrderDate <>", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateGreaterThan(Date value) {
            addCriterionForJDBCDate("replyOrderDate >", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("replyOrderDate >=", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateLessThan(Date value) {
            addCriterionForJDBCDate("replyOrderDate <", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("replyOrderDate <=", value, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateIn(List<Date> values) {
            addCriterionForJDBCDate("replyOrderDate in", values, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("replyOrderDate not in", values, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("replyOrderDate between", value1, value2, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyorderdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("replyOrderDate not between", value1, value2, "replyorderdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateIsNull() {
            addCriterion("replyExportDate is null");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateIsNotNull() {
            addCriterion("replyExportDate is not null");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateEqualTo(Date value) {
            addCriterionForJDBCDate("replyExportDate =", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("replyExportDate <>", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateGreaterThan(Date value) {
            addCriterionForJDBCDate("replyExportDate >", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("replyExportDate >=", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateLessThan(Date value) {
            addCriterionForJDBCDate("replyExportDate <", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("replyExportDate <=", value, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateIn(List<Date> values) {
            addCriterionForJDBCDate("replyExportDate in", values, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("replyExportDate not in", values, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("replyExportDate between", value1, value2, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andReplyexportdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("replyExportDate not between", value1, value2, "replyexportdate");
            return (Criteria) this;
        }

        public Criteria andQtytransIsNull() {
            addCriterion("qtyTrans is null");
            return (Criteria) this;
        }

        public Criteria andQtytransIsNotNull() {
            addCriterion("qtyTrans is not null");
            return (Criteria) this;
        }

        public Criteria andQtytransEqualTo(Integer value) {
            addCriterion("qtyTrans =", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransNotEqualTo(Integer value) {
            addCriterion("qtyTrans <>", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransGreaterThan(Integer value) {
            addCriterion("qtyTrans >", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyTrans >=", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransLessThan(Integer value) {
            addCriterion("qtyTrans <", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransLessThanOrEqualTo(Integer value) {
            addCriterion("qtyTrans <=", value, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransIn(List<Integer> values) {
            addCriterion("qtyTrans in", values, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransNotIn(List<Integer> values) {
            addCriterion("qtyTrans not in", values, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransBetween(Integer value1, Integer value2) {
            addCriterion("qtyTrans between", value1, value2, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtytransNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyTrans not between", value1, value2, "qtytrans");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIsNull() {
            addCriterion("qtyReceive is null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIsNotNull() {
            addCriterion("qtyReceive is not null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveEqualTo(Integer value) {
            addCriterion("qtyReceive =", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotEqualTo(Integer value) {
            addCriterion("qtyReceive <>", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThan(Integer value) {
            addCriterion("qtyReceive >", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyReceive >=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThan(Integer value) {
            addCriterion("qtyReceive <", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThanOrEqualTo(Integer value) {
            addCriterion("qtyReceive <=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIn(List<Integer> values) {
            addCriterion("qtyReceive in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotIn(List<Integer> values) {
            addCriterion("qtyReceive not in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveBetween(Integer value1, Integer value2) {
            addCriterion("qtyReceive between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyReceive not between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andReasonremarkIsNull() {
            addCriterion("reasonRemark is null");
            return (Criteria) this;
        }

        public Criteria andReasonremarkIsNotNull() {
            addCriterion("reasonRemark is not null");
            return (Criteria) this;
        }

        public Criteria andReasonremarkEqualTo(String value) {
            addCriterion("reasonRemark =", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkNotEqualTo(String value) {
            addCriterion("reasonRemark <>", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkGreaterThan(String value) {
            addCriterion("reasonRemark >", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkGreaterThanOrEqualTo(String value) {
            addCriterion("reasonRemark >=", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkLessThan(String value) {
            addCriterion("reasonRemark <", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkLessThanOrEqualTo(String value) {
            addCriterion("reasonRemark <=", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkLike(String value) {
            addCriterion("reasonRemark like", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkNotLike(String value) {
            addCriterion("reasonRemark not like", value, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkIn(List<String> values) {
            addCriterion("reasonRemark in", values, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkNotIn(List<String> values) {
            addCriterion("reasonRemark not in", values, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkBetween(String value1, String value2) {
            addCriterion("reasonRemark between", value1, value2, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andReasonremarkNotBetween(String value1, String value2) {
            addCriterion("reasonRemark not between", value1, value2, "reasonremark");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoIsNull() {
            addCriterion("dlvAnswerNo is null");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoIsNotNull() {
            addCriterion("dlvAnswerNo is not null");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoEqualTo(String value) {
            addCriterion("dlvAnswerNo =", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoNotEqualTo(String value) {
            addCriterion("dlvAnswerNo <>", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoGreaterThan(String value) {
            addCriterion("dlvAnswerNo >", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoGreaterThanOrEqualTo(String value) {
            addCriterion("dlvAnswerNo >=", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoLessThan(String value) {
            addCriterion("dlvAnswerNo <", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoLessThanOrEqualTo(String value) {
            addCriterion("dlvAnswerNo <=", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoLike(String value) {
            addCriterion("dlvAnswerNo like", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoNotLike(String value) {
            addCriterion("dlvAnswerNo not like", value, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoIn(List<String> values) {
            addCriterion("dlvAnswerNo in", values, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoNotIn(List<String> values) {
            addCriterion("dlvAnswerNo not in", values, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoBetween(String value1, String value2) {
            addCriterion("dlvAnswerNo between", value1, value2, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andDlvanswernoNotBetween(String value1, String value2) {
            addCriterion("dlvAnswerNo not between", value1, value2, "dlvanswerno");
            return (Criteria) this;
        }

        public Criteria andProducefactoryIsNull() {
            addCriterion("produceFactory is null");
            return (Criteria) this;
        }

        public Criteria andProducefactoryIsNotNull() {
            addCriterion("produceFactory is not null");
            return (Criteria) this;
        }

        public Criteria andProducefactoryEqualTo(String value) {
            addCriterion("produceFactory =", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryNotEqualTo(String value) {
            addCriterion("produceFactory <>", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryGreaterThan(String value) {
            addCriterion("produceFactory >", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryGreaterThanOrEqualTo(String value) {
            addCriterion("produceFactory >=", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryLessThan(String value) {
            addCriterion("produceFactory <", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryLessThanOrEqualTo(String value) {
            addCriterion("produceFactory <=", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryLike(String value) {
            addCriterion("produceFactory like", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryNotLike(String value) {
            addCriterion("produceFactory not like", value, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryIn(List<String> values) {
            addCriterion("produceFactory in", values, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryNotIn(List<String> values) {
            addCriterion("produceFactory not in", values, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryBetween(String value1, String value2) {
            addCriterion("produceFactory between", value1, value2, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProducefactoryNotBetween(String value1, String value2) {
            addCriterion("produceFactory not between", value1, value2, "producefactory");
            return (Criteria) this;
        }

        public Criteria andProduceholonIsNull() {
            addCriterion("produceHolon is null");
            return (Criteria) this;
        }

        public Criteria andProduceholonIsNotNull() {
            addCriterion("produceHolon is not null");
            return (Criteria) this;
        }

        public Criteria andProduceholonEqualTo(String value) {
            addCriterion("produceHolon =", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonNotEqualTo(String value) {
            addCriterion("produceHolon <>", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonGreaterThan(String value) {
            addCriterion("produceHolon >", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonGreaterThanOrEqualTo(String value) {
            addCriterion("produceHolon >=", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonLessThan(String value) {
            addCriterion("produceHolon <", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonLessThanOrEqualTo(String value) {
            addCriterion("produceHolon <=", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonLike(String value) {
            addCriterion("produceHolon like", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonNotLike(String value) {
            addCriterion("produceHolon not like", value, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonIn(List<String> values) {
            addCriterion("produceHolon in", values, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonNotIn(List<String> values) {
            addCriterion("produceHolon not in", values, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonBetween(String value1, String value2) {
            addCriterion("produceHolon between", value1, value2, "produceholon");
            return (Criteria) this;
        }

        public Criteria andProduceholonNotBetween(String value1, String value2) {
            addCriterion("produceHolon not between", value1, value2, "produceholon");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIsNull() {
            addCriterion("errDescription is null");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIsNotNull() {
            addCriterion("errDescription is not null");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionEqualTo(String value) {
            addCriterion("errDescription =", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotEqualTo(String value) {
            addCriterion("errDescription <>", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionGreaterThan(String value) {
            addCriterion("errDescription >", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("errDescription >=", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLessThan(String value) {
            addCriterion("errDescription <", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLessThanOrEqualTo(String value) {
            addCriterion("errDescription <=", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLike(String value) {
            addCriterion("errDescription like", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotLike(String value) {
            addCriterion("errDescription not like", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIn(List<String> values) {
            addCriterion("errDescription in", values, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotIn(List<String> values) {
            addCriterion("errDescription not in", values, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionBetween(String value1, String value2) {
            addCriterion("errDescription between", value1, value2, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotBetween(String value1, String value2) {
            addCriterion("errDescription not between", value1, value2, "errdescription");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNull() {
            addCriterion("barCode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("barCode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("barCode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("barCode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("barCode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("barCode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("barCode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("barCode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("barCode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("barCode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("barCode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("barCode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("barCode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("barCode not between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andCasenoIsNull() {
            addCriterion("caseNo is null");
            return (Criteria) this;
        }

        public Criteria andCasenoIsNotNull() {
            addCriterion("caseNo is not null");
            return (Criteria) this;
        }

        public Criteria andCasenoEqualTo(String value) {
            addCriterion("caseNo =", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotEqualTo(String value) {
            addCriterion("caseNo <>", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThan(String value) {
            addCriterion("caseNo >", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThanOrEqualTo(String value) {
            addCriterion("caseNo >=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThan(String value) {
            addCriterion("caseNo <", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThanOrEqualTo(String value) {
            addCriterion("caseNo <=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLike(String value) {
            addCriterion("caseNo like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotLike(String value) {
            addCriterion("caseNo not like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoIn(List<String> values) {
            addCriterion("caseNo in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotIn(List<String> values) {
            addCriterion("caseNo not in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoBetween(String value1, String value2) {
            addCriterion("caseNo between", value1, value2, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotBetween(String value1, String value2) {
            addCriterion("caseNo not between", value1, value2, "caseno");
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
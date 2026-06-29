package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderdataBjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderdataBjExample() {
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

        public Criteria andOrdrnoIsNull() {
            addCriterion("OrdrNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdrnoIsNotNull() {
            addCriterion("OrdrNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdrnoEqualTo(String value) {
            addCriterion("OrdrNo =", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoNotEqualTo(String value) {
            addCriterion("OrdrNo <>", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoGreaterThan(String value) {
            addCriterion("OrdrNo >", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoGreaterThanOrEqualTo(String value) {
            addCriterion("OrdrNo >=", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoLessThan(String value) {
            addCriterion("OrdrNo <", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoLessThanOrEqualTo(String value) {
            addCriterion("OrdrNo <=", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoLike(String value) {
            addCriterion("OrdrNo like", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoNotLike(String value) {
            addCriterion("OrdrNo not like", value, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoIn(List<String> values) {
            addCriterion("OrdrNo in", values, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoNotIn(List<String> values) {
            addCriterion("OrdrNo not in", values, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoBetween(String value1, String value2) {
            addCriterion("OrdrNo between", value1, value2, "ordrno");
            return (Criteria) this;
        }

        public Criteria andOrdrnoNotBetween(String value1, String value2) {
            addCriterion("OrdrNo not between", value1, value2, "ordrno");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("Quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("Quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("Quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("Quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("Quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("Quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("Quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("Quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("Quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("Quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("Quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andPrespriceIsNull() {
            addCriterion("PresPrice is null");
            return (Criteria) this;
        }

        public Criteria andPrespriceIsNotNull() {
            addCriterion("PresPrice is not null");
            return (Criteria) this;
        }

        public Criteria andPrespriceEqualTo(BigDecimal value) {
            addCriterion("PresPrice =", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceNotEqualTo(BigDecimal value) {
            addCriterion("PresPrice <>", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceGreaterThan(BigDecimal value) {
            addCriterion("PresPrice >", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PresPrice >=", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceLessThan(BigDecimal value) {
            addCriterion("PresPrice <", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PresPrice <=", value, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceIn(List<BigDecimal> values) {
            addCriterion("PresPrice in", values, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceNotIn(List<BigDecimal> values) {
            addCriterion("PresPrice not in", values, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PresPrice between", value1, value2, "presprice");
            return (Criteria) this;
        }

        public Criteria andPrespriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PresPrice not between", value1, value2, "presprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceIsNull() {
            addCriterion("StdPrice is null");
            return (Criteria) this;
        }

        public Criteria andStdpriceIsNotNull() {
            addCriterion("StdPrice is not null");
            return (Criteria) this;
        }

        public Criteria andStdpriceEqualTo(BigDecimal value) {
            addCriterion("StdPrice =", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotEqualTo(BigDecimal value) {
            addCriterion("StdPrice <>", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThan(BigDecimal value) {
            addCriterion("StdPrice >", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("StdPrice >=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThan(BigDecimal value) {
            addCriterion("StdPrice <", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("StdPrice <=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceIn(List<BigDecimal> values) {
            addCriterion("StdPrice in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotIn(List<BigDecimal> values) {
            addCriterion("StdPrice not in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("StdPrice between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("StdPrice not between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNull() {
            addCriterion("TransType is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("TransType is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("TransType =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("TransType <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("TransType >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("TransType >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("TransType <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("TransType <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("TransType like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("TransType not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("TransType in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("TransType not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("TransType between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("TransType not between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNull() {
            addCriterion("OrdDate is null");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNotNull() {
            addCriterion("OrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andOrddateEqualTo(Date value) {
            addCriterion("OrdDate =", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotEqualTo(Date value) {
            addCriterion("OrdDate <>", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThan(Date value) {
            addCriterion("OrdDate >", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThanOrEqualTo(Date value) {
            addCriterion("OrdDate >=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThan(Date value) {
            addCriterion("OrdDate <", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThanOrEqualTo(Date value) {
            addCriterion("OrdDate <=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateIn(List<Date> values) {
            addCriterion("OrdDate in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotIn(List<Date> values) {
            addCriterion("OrdDate not in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateBetween(Date value1, Date value2) {
            addCriterion("OrdDate between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotBetween(Date value1, Date value2) {
            addCriterion("OrdDate not between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNull() {
            addCriterion("DlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("DlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterion("DlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterion("DlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterion("DlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterion("DlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterion("DlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterion("DlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterion("DlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate not between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("CustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("CustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("CustomerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("CustomerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("CustomerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("CustomerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("CustomerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("CustomerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("CustomerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("CustomerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("CustomerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("CustomerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("CustomerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNull() {
            addCriterion("UserNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("UserNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("UserNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("UserNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("UserNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("UserNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("UserNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("UserNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("UserNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("UserNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("UserNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("UserNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("UserNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("UserNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andVndcodeIsNull() {
            addCriterion("VndCode is null");
            return (Criteria) this;
        }

        public Criteria andVndcodeIsNotNull() {
            addCriterion("VndCode is not null");
            return (Criteria) this;
        }

        public Criteria andVndcodeEqualTo(String value) {
            addCriterion("VndCode =", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeNotEqualTo(String value) {
            addCriterion("VndCode <>", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeGreaterThan(String value) {
            addCriterion("VndCode >", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeGreaterThanOrEqualTo(String value) {
            addCriterion("VndCode >=", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeLessThan(String value) {
            addCriterion("VndCode <", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeLessThanOrEqualTo(String value) {
            addCriterion("VndCode <=", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeLike(String value) {
            addCriterion("VndCode like", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeNotLike(String value) {
            addCriterion("VndCode not like", value, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeIn(List<String> values) {
            addCriterion("VndCode in", values, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeNotIn(List<String> values) {
            addCriterion("VndCode not in", values, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeBetween(String value1, String value2) {
            addCriterion("VndCode between", value1, value2, "vndcode");
            return (Criteria) this;
        }

        public Criteria andVndcodeNotBetween(String value1, String value2) {
            addCriterion("VndCode not between", value1, value2, "vndcode");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNull() {
            addCriterion("OptState is null");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNotNull() {
            addCriterion("OptState is not null");
            return (Criteria) this;
        }

        public Criteria andOptstateEqualTo(String value) {
            addCriterion("OptState =", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotEqualTo(String value) {
            addCriterion("OptState <>", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThan(String value) {
            addCriterion("OptState >", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThanOrEqualTo(String value) {
            addCriterion("OptState >=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThan(String value) {
            addCriterion("OptState <", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThanOrEqualTo(String value) {
            addCriterion("OptState <=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLike(String value) {
            addCriterion("OptState like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotLike(String value) {
            addCriterion("OptState not like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateIn(List<String> values) {
            addCriterion("OptState in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotIn(List<String> values) {
            addCriterion("OptState not in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateBetween(String value1, String value2) {
            addCriterion("OptState between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotBetween(String value1, String value2) {
            addCriterion("OptState not between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNull() {
            addCriterion("SpecMark is null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNotNull() {
            addCriterion("SpecMark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkEqualTo(String value) {
            addCriterion("SpecMark =", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotEqualTo(String value) {
            addCriterion("SpecMark <>", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThan(String value) {
            addCriterion("SpecMark >", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThanOrEqualTo(String value) {
            addCriterion("SpecMark >=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThan(String value) {
            addCriterion("SpecMark <", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThanOrEqualTo(String value) {
            addCriterion("SpecMark <=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLike(String value) {
            addCriterion("SpecMark like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotLike(String value) {
            addCriterion("SpecMark not like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIn(List<String> values) {
            addCriterion("SpecMark in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotIn(List<String> values) {
            addCriterion("SpecMark not in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkBetween(String value1, String value2) {
            addCriterion("SpecMark between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotBetween(String value1, String value2) {
            addCriterion("SpecMark not between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNull() {
            addCriterion("LocationNo is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNotNull() {
            addCriterion("LocationNo is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoEqualTo(String value) {
            addCriterion("LocationNo =", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotEqualTo(String value) {
            addCriterion("LocationNo <>", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThan(String value) {
            addCriterion("LocationNo >", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThanOrEqualTo(String value) {
            addCriterion("LocationNo >=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThan(String value) {
            addCriterion("LocationNo <", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThanOrEqualTo(String value) {
            addCriterion("LocationNo <=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLike(String value) {
            addCriterion("LocationNo like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotLike(String value) {
            addCriterion("LocationNo not like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIn(List<String> values) {
            addCriterion("LocationNo in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotIn(List<String> values) {
            addCriterion("LocationNo not in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoBetween(String value1, String value2) {
            addCriterion("LocationNo between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotBetween(String value1, String value2) {
            addCriterion("LocationNo not between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("DeptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("DeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("DeptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("DeptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("DeptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("DeptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("DeptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("DeptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("DeptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("DeptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("DeptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("DeptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("DeptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("DeptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoIsNull() {
            addCriterion("DeliveryNo is null");
            return (Criteria) this;
        }

        public Criteria andDeliverynoIsNotNull() {
            addCriterion("DeliveryNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverynoEqualTo(String value) {
            addCriterion("DeliveryNo =", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoNotEqualTo(String value) {
            addCriterion("DeliveryNo <>", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoGreaterThan(String value) {
            addCriterion("DeliveryNo >", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoGreaterThanOrEqualTo(String value) {
            addCriterion("DeliveryNo >=", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoLessThan(String value) {
            addCriterion("DeliveryNo <", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoLessThanOrEqualTo(String value) {
            addCriterion("DeliveryNo <=", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoLike(String value) {
            addCriterion("DeliveryNo like", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoNotLike(String value) {
            addCriterion("DeliveryNo not like", value, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoIn(List<String> values) {
            addCriterion("DeliveryNo in", values, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoNotIn(List<String> values) {
            addCriterion("DeliveryNo not in", values, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoBetween(String value1, String value2) {
            addCriterion("DeliveryNo between", value1, value2, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andDeliverynoNotBetween(String value1, String value2) {
            addCriterion("DeliveryNo not between", value1, value2, "deliveryno");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNull() {
            addCriterion("StateFlag is null");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNotNull() {
            addCriterion("StateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andStateflagEqualTo(String value) {
            addCriterion("StateFlag =", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotEqualTo(String value) {
            addCriterion("StateFlag <>", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThan(String value) {
            addCriterion("StateFlag >", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThanOrEqualTo(String value) {
            addCriterion("StateFlag >=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThan(String value) {
            addCriterion("StateFlag <", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThanOrEqualTo(String value) {
            addCriterion("StateFlag <=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLike(String value) {
            addCriterion("StateFlag like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotLike(String value) {
            addCriterion("StateFlag not like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagIn(List<String> values) {
            addCriterion("StateFlag in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotIn(List<String> values) {
            addCriterion("StateFlag not in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagBetween(String value1, String value2) {
            addCriterion("StateFlag between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotBetween(String value1, String value2) {
            addCriterion("StateFlag not between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIsNull() {
            addCriterion("QtyReceive is null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIsNotNull() {
            addCriterion("QtyReceive is not null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveEqualTo(Integer value) {
            addCriterion("QtyReceive =", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotEqualTo(Integer value) {
            addCriterion("QtyReceive <>", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThan(Integer value) {
            addCriterion("QtyReceive >", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyReceive >=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThan(Integer value) {
            addCriterion("QtyReceive <", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThanOrEqualTo(Integer value) {
            addCriterion("QtyReceive <=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIn(List<Integer> values) {
            addCriterion("QtyReceive in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotIn(List<Integer> values) {
            addCriterion("QtyReceive not in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveBetween(Integer value1, Integer value2) {
            addCriterion("QtyReceive between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyReceive not between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andFinishdateIsNull() {
            addCriterion("FinishDate is null");
            return (Criteria) this;
        }

        public Criteria andFinishdateIsNotNull() {
            addCriterion("FinishDate is not null");
            return (Criteria) this;
        }

        public Criteria andFinishdateEqualTo(Date value) {
            addCriterion("FinishDate =", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotEqualTo(Date value) {
            addCriterion("FinishDate <>", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateGreaterThan(Date value) {
            addCriterion("FinishDate >", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateGreaterThanOrEqualTo(Date value) {
            addCriterion("FinishDate >=", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateLessThan(Date value) {
            addCriterion("FinishDate <", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateLessThanOrEqualTo(Date value) {
            addCriterion("FinishDate <=", value, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateIn(List<Date> values) {
            addCriterion("FinishDate in", values, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotIn(List<Date> values) {
            addCriterion("FinishDate not in", values, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateBetween(Date value1, Date value2) {
            addCriterion("FinishDate between", value1, value2, "finishdate");
            return (Criteria) this;
        }

        public Criteria andFinishdateNotBetween(Date value1, Date value2) {
            addCriterion("FinishDate not between", value1, value2, "finishdate");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("DisCount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("DisCount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Long value) {
            addCriterion("DisCount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Long value) {
            addCriterion("DisCount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Long value) {
            addCriterion("DisCount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Long value) {
            addCriterion("DisCount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Long value) {
            addCriterion("DisCount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Long value) {
            addCriterion("DisCount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Long> values) {
            addCriterion("DisCount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Long> values) {
            addCriterion("DisCount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Long value1, Long value2) {
            addCriterion("DisCount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Long value1, Long value2) {
            addCriterion("DisCount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andErrcodeIsNull() {
            addCriterion("ErrCode is null");
            return (Criteria) this;
        }

        public Criteria andErrcodeIsNotNull() {
            addCriterion("ErrCode is not null");
            return (Criteria) this;
        }

        public Criteria andErrcodeEqualTo(String value) {
            addCriterion("ErrCode =", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeNotEqualTo(String value) {
            addCriterion("ErrCode <>", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeGreaterThan(String value) {
            addCriterion("ErrCode >", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ErrCode >=", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeLessThan(String value) {
            addCriterion("ErrCode <", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeLessThanOrEqualTo(String value) {
            addCriterion("ErrCode <=", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeLike(String value) {
            addCriterion("ErrCode like", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeNotLike(String value) {
            addCriterion("ErrCode not like", value, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeIn(List<String> values) {
            addCriterion("ErrCode in", values, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeNotIn(List<String> values) {
            addCriterion("ErrCode not in", values, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeBetween(String value1, String value2) {
            addCriterion("ErrCode between", value1, value2, "errcode");
            return (Criteria) this;
        }

        public Criteria andErrcodeNotBetween(String value1, String value2) {
            addCriterion("ErrCode not between", value1, value2, "errcode");
            return (Criteria) this;
        }

        public Criteria andSpflagIsNull() {
            addCriterion("SpFlag is null");
            return (Criteria) this;
        }

        public Criteria andSpflagIsNotNull() {
            addCriterion("SpFlag is not null");
            return (Criteria) this;
        }

        public Criteria andSpflagEqualTo(String value) {
            addCriterion("SpFlag =", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagNotEqualTo(String value) {
            addCriterion("SpFlag <>", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagGreaterThan(String value) {
            addCriterion("SpFlag >", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagGreaterThanOrEqualTo(String value) {
            addCriterion("SpFlag >=", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagLessThan(String value) {
            addCriterion("SpFlag <", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagLessThanOrEqualTo(String value) {
            addCriterion("SpFlag <=", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagLike(String value) {
            addCriterion("SpFlag like", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagNotLike(String value) {
            addCriterion("SpFlag not like", value, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagIn(List<String> values) {
            addCriterion("SpFlag in", values, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagNotIn(List<String> values) {
            addCriterion("SpFlag not in", values, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagBetween(String value1, String value2) {
            addCriterion("SpFlag between", value1, value2, "spflag");
            return (Criteria) this;
        }

        public Criteria andSpflagNotBetween(String value1, String value2) {
            addCriterion("SpFlag not between", value1, value2, "spflag");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNull() {
            addCriterion("VIPCode is null");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNotNull() {
            addCriterion("VIPCode is not null");
            return (Criteria) this;
        }

        public Criteria andVipcodeEqualTo(String value) {
            addCriterion("VIPCode =", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotEqualTo(String value) {
            addCriterion("VIPCode <>", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThan(String value) {
            addCriterion("VIPCode >", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("VIPCode >=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThan(String value) {
            addCriterion("VIPCode <", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThanOrEqualTo(String value) {
            addCriterion("VIPCode <=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLike(String value) {
            addCriterion("VIPCode like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotLike(String value) {
            addCriterion("VIPCode not like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeIn(List<String> values) {
            addCriterion("VIPCode in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotIn(List<String> values) {
            addCriterion("VIPCode not in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeBetween(String value1, String value2) {
            addCriterion("VIPCode between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotBetween(String value1, String value2) {
            addCriterion("VIPCode not between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andOrddateflagIsNull() {
            addCriterion("OrdDateFlag is null");
            return (Criteria) this;
        }

        public Criteria andOrddateflagIsNotNull() {
            addCriterion("OrdDateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andOrddateflagEqualTo(String value) {
            addCriterion("OrdDateFlag =", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagNotEqualTo(String value) {
            addCriterion("OrdDateFlag <>", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagGreaterThan(String value) {
            addCriterion("OrdDateFlag >", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagGreaterThanOrEqualTo(String value) {
            addCriterion("OrdDateFlag >=", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagLessThan(String value) {
            addCriterion("OrdDateFlag <", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagLessThanOrEqualTo(String value) {
            addCriterion("OrdDateFlag <=", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagLike(String value) {
            addCriterion("OrdDateFlag like", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagNotLike(String value) {
            addCriterion("OrdDateFlag not like", value, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagIn(List<String> values) {
            addCriterion("OrdDateFlag in", values, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagNotIn(List<String> values) {
            addCriterion("OrdDateFlag not in", values, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagBetween(String value1, String value2) {
            addCriterion("OrdDateFlag between", value1, value2, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andOrddateflagNotBetween(String value1, String value2) {
            addCriterion("OrdDateFlag not between", value1, value2, "orddateflag");
            return (Criteria) this;
        }

        public Criteria andPreqtyordIsNull() {
            addCriterion("PreQtyOrd is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyordIsNotNull() {
            addCriterion("PreQtyOrd is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyordEqualTo(Integer value) {
            addCriterion("PreQtyOrd =", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordNotEqualTo(Integer value) {
            addCriterion("PreQtyOrd <>", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordGreaterThan(Integer value) {
            addCriterion("PreQtyOrd >", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordGreaterThanOrEqualTo(Integer value) {
            addCriterion("PreQtyOrd >=", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordLessThan(Integer value) {
            addCriterion("PreQtyOrd <", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordLessThanOrEqualTo(Integer value) {
            addCriterion("PreQtyOrd <=", value, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordIn(List<Integer> values) {
            addCriterion("PreQtyOrd in", values, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordNotIn(List<Integer> values) {
            addCriterion("PreQtyOrd not in", values, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordBetween(Integer value1, Integer value2) {
            addCriterion("PreQtyOrd between", value1, value2, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andPreqtyordNotBetween(Integer value1, Integer value2) {
            addCriterion("PreQtyOrd not between", value1, value2, "preqtyord");
            return (Criteria) this;
        }

        public Criteria andAnswernoIsNull() {
            addCriterion("AnswerNo is null");
            return (Criteria) this;
        }

        public Criteria andAnswernoIsNotNull() {
            addCriterion("AnswerNo is not null");
            return (Criteria) this;
        }

        public Criteria andAnswernoEqualTo(String value) {
            addCriterion("AnswerNo =", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotEqualTo(String value) {
            addCriterion("AnswerNo <>", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoGreaterThan(String value) {
            addCriterion("AnswerNo >", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoGreaterThanOrEqualTo(String value) {
            addCriterion("AnswerNo >=", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLessThan(String value) {
            addCriterion("AnswerNo <", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLessThanOrEqualTo(String value) {
            addCriterion("AnswerNo <=", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLike(String value) {
            addCriterion("AnswerNo like", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotLike(String value) {
            addCriterion("AnswerNo not like", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoIn(List<String> values) {
            addCriterion("AnswerNo in", values, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotIn(List<String> values) {
            addCriterion("AnswerNo not in", values, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoBetween(String value1, String value2) {
            addCriterion("AnswerNo between", value1, value2, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotBetween(String value1, String value2) {
            addCriterion("AnswerNo not between", value1, value2, "answerno");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("SmcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("SmcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("SmcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("SmcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("SmcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("SmcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("SmcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("SmcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("SmcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("SmcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("SmcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("SmcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("SmcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("SmcCode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andOrdflagIsNull() {
            addCriterion("OrdFlag is null");
            return (Criteria) this;
        }

        public Criteria andOrdflagIsNotNull() {
            addCriterion("OrdFlag is not null");
            return (Criteria) this;
        }

        public Criteria andOrdflagEqualTo(String value) {
            addCriterion("OrdFlag =", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotEqualTo(String value) {
            addCriterion("OrdFlag <>", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagGreaterThan(String value) {
            addCriterion("OrdFlag >", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagGreaterThanOrEqualTo(String value) {
            addCriterion("OrdFlag >=", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLessThan(String value) {
            addCriterion("OrdFlag <", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLessThanOrEqualTo(String value) {
            addCriterion("OrdFlag <=", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLike(String value) {
            addCriterion("OrdFlag like", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotLike(String value) {
            addCriterion("OrdFlag not like", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagIn(List<String> values) {
            addCriterion("OrdFlag in", values, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotIn(List<String> values) {
            addCriterion("OrdFlag not in", values, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagBetween(String value1, String value2) {
            addCriterion("OrdFlag between", value1, value2, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotBetween(String value1, String value2) {
            addCriterion("OrdFlag not between", value1, value2, "ordflag");
            return (Criteria) this;
        }

        public Criteria andSweightIsNull() {
            addCriterion("SWeight is null");
            return (Criteria) this;
        }

        public Criteria andSweightIsNotNull() {
            addCriterion("SWeight is not null");
            return (Criteria) this;
        }

        public Criteria andSweightEqualTo(BigDecimal value) {
            addCriterion("SWeight =", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightNotEqualTo(BigDecimal value) {
            addCriterion("SWeight <>", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightGreaterThan(BigDecimal value) {
            addCriterion("SWeight >", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SWeight >=", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightLessThan(BigDecimal value) {
            addCriterion("SWeight <", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SWeight <=", value, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightIn(List<BigDecimal> values) {
            addCriterion("SWeight in", values, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightNotIn(List<BigDecimal> values) {
            addCriterion("SWeight not in", values, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SWeight between", value1, value2, "sweight");
            return (Criteria) this;
        }

        public Criteria andSweightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SWeight not between", value1, value2, "sweight");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpIsNull() {
            addCriterion("DlvDate_JP is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpIsNotNull() {
            addCriterion("DlvDate_JP is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpEqualTo(Date value) {
            addCriterion("DlvDate_JP =", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpNotEqualTo(Date value) {
            addCriterion("DlvDate_JP <>", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpGreaterThan(Date value) {
            addCriterion("DlvDate_JP >", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate_JP >=", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpLessThan(Date value) {
            addCriterion("DlvDate_JP <", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate_JP <=", value, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpIn(List<Date> values) {
            addCriterion("DlvDate_JP in", values, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpNotIn(List<Date> values) {
            addCriterion("DlvDate_JP not in", values, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpBetween(Date value1, Date value2) {
            addCriterion("DlvDate_JP between", value1, value2, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andDlvdateJpNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate_JP not between", value1, value2, "dlvdateJp");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIsNull() {
            addCriterion("Expdate_GC is null");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIsNotNull() {
            addCriterion("Expdate_GC is not null");
            return (Criteria) this;
        }

        public Criteria andExpdateGcEqualTo(Date value) {
            addCriterion("Expdate_GC =", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotEqualTo(Date value) {
            addCriterion("Expdate_GC <>", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcGreaterThan(Date value) {
            addCriterion("Expdate_GC >", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcGreaterThanOrEqualTo(Date value) {
            addCriterion("Expdate_GC >=", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcLessThan(Date value) {
            addCriterion("Expdate_GC <", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcLessThanOrEqualTo(Date value) {
            addCriterion("Expdate_GC <=", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIn(List<Date> values) {
            addCriterion("Expdate_GC in", values, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotIn(List<Date> values) {
            addCriterion("Expdate_GC not in", values, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcBetween(Date value1, Date value2) {
            addCriterion("Expdate_GC between", value1, value2, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotBetween(Date value1, Date value2) {
            addCriterion("Expdate_GC not between", value1, value2, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIsNull() {
            addCriterion("DlvModDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIsNotNull() {
            addCriterion("DlvModDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateEqualTo(Date value) {
            addCriterion("DlvModDate =", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotEqualTo(Date value) {
            addCriterion("DlvModDate <>", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateGreaterThan(Date value) {
            addCriterion("DlvModDate >", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate >=", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateLessThan(Date value) {
            addCriterion("DlvModDate <", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateLessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate <=", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIn(List<Date> values) {
            addCriterion("DlvModDate in", values, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotIn(List<Date> values) {
            addCriterion("DlvModDate not in", values, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateBetween(Date value1, Date value2) {
            addCriterion("DlvModDate between", value1, value2, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate not between", value1, value2, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIsNull() {
            addCriterion("Remark_GC is null");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIsNotNull() {
            addCriterion("Remark_GC is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkGcEqualTo(String value) {
            addCriterion("Remark_GC =", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotEqualTo(String value) {
            addCriterion("Remark_GC <>", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcGreaterThan(String value) {
            addCriterion("Remark_GC >", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcGreaterThanOrEqualTo(String value) {
            addCriterion("Remark_GC >=", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLessThan(String value) {
            addCriterion("Remark_GC <", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLessThanOrEqualTo(String value) {
            addCriterion("Remark_GC <=", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLike(String value) {
            addCriterion("Remark_GC like", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotLike(String value) {
            addCriterion("Remark_GC not like", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIn(List<String> values) {
            addCriterion("Remark_GC in", values, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotIn(List<String> values) {
            addCriterion("Remark_GC not in", values, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcBetween(String value1, String value2) {
            addCriterion("Remark_GC between", value1, value2, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotBetween(String value1, String value2) {
            addCriterion("Remark_GC not between", value1, value2, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRedpriceIsNull() {
            addCriterion("RedPrice is null");
            return (Criteria) this;
        }

        public Criteria andRedpriceIsNotNull() {
            addCriterion("RedPrice is not null");
            return (Criteria) this;
        }

        public Criteria andRedpriceEqualTo(BigDecimal value) {
            addCriterion("RedPrice =", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceNotEqualTo(BigDecimal value) {
            addCriterion("RedPrice <>", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceGreaterThan(BigDecimal value) {
            addCriterion("RedPrice >", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPrice >=", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceLessThan(BigDecimal value) {
            addCriterion("RedPrice <", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPrice <=", value, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceIn(List<BigDecimal> values) {
            addCriterion("RedPrice in", values, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceNotIn(List<BigDecimal> values) {
            addCriterion("RedPrice not in", values, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPrice between", value1, value2, "redprice");
            return (Criteria) this;
        }

        public Criteria andRedpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPrice not between", value1, value2, "redprice");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagIsNull() {
            addCriterion("MultPrice_Flag is null");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagIsNotNull() {
            addCriterion("MultPrice_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagEqualTo(String value) {
            addCriterion("MultPrice_Flag =", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagNotEqualTo(String value) {
            addCriterion("MultPrice_Flag <>", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagGreaterThan(String value) {
            addCriterion("MultPrice_Flag >", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("MultPrice_Flag >=", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagLessThan(String value) {
            addCriterion("MultPrice_Flag <", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagLessThanOrEqualTo(String value) {
            addCriterion("MultPrice_Flag <=", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagLike(String value) {
            addCriterion("MultPrice_Flag like", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagNotLike(String value) {
            addCriterion("MultPrice_Flag not like", value, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagIn(List<String> values) {
            addCriterion("MultPrice_Flag in", values, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagNotIn(List<String> values) {
            addCriterion("MultPrice_Flag not in", values, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagBetween(String value1, String value2) {
            addCriterion("MultPrice_Flag between", value1, value2, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andMultpriceFlagNotBetween(String value1, String value2) {
            addCriterion("MultPrice_Flag not between", value1, value2, "multpriceFlag");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNull() {
            addCriterion("EmpOrdprocess is null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNotNull() {
            addCriterion("EmpOrdprocess is not null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessEqualTo(String value) {
            addCriterion("EmpOrdprocess =", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotEqualTo(String value) {
            addCriterion("EmpOrdprocess <>", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThan(String value) {
            addCriterion("EmpOrdprocess >", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThanOrEqualTo(String value) {
            addCriterion("EmpOrdprocess >=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThan(String value) {
            addCriterion("EmpOrdprocess <", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThanOrEqualTo(String value) {
            addCriterion("EmpOrdprocess <=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLike(String value) {
            addCriterion("EmpOrdprocess like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotLike(String value) {
            addCriterion("EmpOrdprocess not like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIn(List<String> values) {
            addCriterion("EmpOrdprocess in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotIn(List<String> values) {
            addCriterion("EmpOrdprocess not in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessBetween(String value1, String value2) {
            addCriterion("EmpOrdprocess between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotBetween(String value1, String value2) {
            addCriterion("EmpOrdprocess not between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andQtyimputIsNull() {
            addCriterion("QtyImput is null");
            return (Criteria) this;
        }

        public Criteria andQtyimputIsNotNull() {
            addCriterion("QtyImput is not null");
            return (Criteria) this;
        }

        public Criteria andQtyimputEqualTo(Integer value) {
            addCriterion("QtyImput =", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputNotEqualTo(Integer value) {
            addCriterion("QtyImput <>", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputGreaterThan(Integer value) {
            addCriterion("QtyImput >", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyImput >=", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputLessThan(Integer value) {
            addCriterion("QtyImput <", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputLessThanOrEqualTo(Integer value) {
            addCriterion("QtyImput <=", value, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputIn(List<Integer> values) {
            addCriterion("QtyImput in", values, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputNotIn(List<Integer> values) {
            addCriterion("QtyImput not in", values, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputBetween(Integer value1, Integer value2) {
            addCriterion("QtyImput between", value1, value2, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andQtyimputNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyImput not between", value1, value2, "qtyimput");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1IsNull() {
            addCriterion("DlvModDate1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1IsNotNull() {
            addCriterion("DlvModDate1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1EqualTo(Date value) {
            addCriterion("DlvModDate1 =", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotEqualTo(Date value) {
            addCriterion("DlvModDate1 <>", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1GreaterThan(Date value) {
            addCriterion("DlvModDate1 >", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate1 >=", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1LessThan(Date value) {
            addCriterion("DlvModDate1 <", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate1 <=", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1In(List<Date> values) {
            addCriterion("DlvModDate1 in", values, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotIn(List<Date> values) {
            addCriterion("DlvModDate1 not in", values, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1Between(Date value1, Date value2) {
            addCriterion("DlvModDate1 between", value1, value2, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate1 not between", value1, value2, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2IsNull() {
            addCriterion("DlvModDate2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2IsNotNull() {
            addCriterion("DlvModDate2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2EqualTo(Date value) {
            addCriterion("DlvModDate2 =", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotEqualTo(Date value) {
            addCriterion("DlvModDate2 <>", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2GreaterThan(Date value) {
            addCriterion("DlvModDate2 >", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate2 >=", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2LessThan(Date value) {
            addCriterion("DlvModDate2 <", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate2 <=", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2In(List<Date> values) {
            addCriterion("DlvModDate2 in", values, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotIn(List<Date> values) {
            addCriterion("DlvModDate2 not in", values, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2Between(Date value1, Date value2) {
            addCriterion("DlvModDate2 between", value1, value2, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate2 not between", value1, value2, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIsNull() {
            addCriterion("Reason_Remark is null");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIsNotNull() {
            addCriterion("Reason_Remark is not null");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkEqualTo(String value) {
            addCriterion("Reason_Remark =", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotEqualTo(String value) {
            addCriterion("Reason_Remark <>", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkGreaterThan(String value) {
            addCriterion("Reason_Remark >", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Reason_Remark >=", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLessThan(String value) {
            addCriterion("Reason_Remark <", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLessThanOrEqualTo(String value) {
            addCriterion("Reason_Remark <=", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLike(String value) {
            addCriterion("Reason_Remark like", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotLike(String value) {
            addCriterion("Reason_Remark not like", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIn(List<String> values) {
            addCriterion("Reason_Remark in", values, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotIn(List<String> values) {
            addCriterion("Reason_Remark not in", values, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkBetween(String value1, String value2) {
            addCriterion("Reason_Remark between", value1, value2, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotBetween(String value1, String value2) {
            addCriterion("Reason_Remark not between", value1, value2, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoIsNull() {
            addCriterion("Produce_DeptNo is null");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoIsNotNull() {
            addCriterion("Produce_DeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoEqualTo(String value) {
            addCriterion("Produce_DeptNo =", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoNotEqualTo(String value) {
            addCriterion("Produce_DeptNo <>", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoGreaterThan(String value) {
            addCriterion("Produce_DeptNo >", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("Produce_DeptNo >=", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoLessThan(String value) {
            addCriterion("Produce_DeptNo <", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoLessThanOrEqualTo(String value) {
            addCriterion("Produce_DeptNo <=", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoLike(String value) {
            addCriterion("Produce_DeptNo like", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoNotLike(String value) {
            addCriterion("Produce_DeptNo not like", value, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoIn(List<String> values) {
            addCriterion("Produce_DeptNo in", values, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoNotIn(List<String> values) {
            addCriterion("Produce_DeptNo not in", values, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoBetween(String value1, String value2) {
            addCriterion("Produce_DeptNo between", value1, value2, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andProduceDeptnoNotBetween(String value1, String value2) {
            addCriterion("Produce_DeptNo not between", value1, value2, "produceDeptno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("InvoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("InvoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("InvoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("InvoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("InvoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("InvoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("InvoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("InvoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("InvoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("InvoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("InvoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNull() {
            addCriterion("Impdate is null");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNotNull() {
            addCriterion("Impdate is not null");
            return (Criteria) this;
        }

        public Criteria andImpdateEqualTo(Date value) {
            addCriterionForJDBCDate("Impdate =", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("Impdate <>", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThan(Date value) {
            addCriterionForJDBCDate("Impdate >", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Impdate >=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThan(Date value) {
            addCriterionForJDBCDate("Impdate <", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Impdate <=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateIn(List<Date> values) {
            addCriterionForJDBCDate("Impdate in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("Impdate not in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Impdate between", value1, value2, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Impdate not between", value1, value2, "impdate");
            return (Criteria) this;
        }

        public Criteria andTranstypeModIsNull() {
            addCriterion("TransType_Mod is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeModIsNotNull() {
            addCriterion("TransType_Mod is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeModEqualTo(String value) {
            addCriterion("TransType_Mod =", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModNotEqualTo(String value) {
            addCriterion("TransType_Mod <>", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModGreaterThan(String value) {
            addCriterion("TransType_Mod >", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModGreaterThanOrEqualTo(String value) {
            addCriterion("TransType_Mod >=", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModLessThan(String value) {
            addCriterion("TransType_Mod <", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModLessThanOrEqualTo(String value) {
            addCriterion("TransType_Mod <=", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModLike(String value) {
            addCriterion("TransType_Mod like", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModNotLike(String value) {
            addCriterion("TransType_Mod not like", value, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModIn(List<String> values) {
            addCriterion("TransType_Mod in", values, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModNotIn(List<String> values) {
            addCriterion("TransType_Mod not in", values, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModBetween(String value1, String value2) {
            addCriterion("TransType_Mod between", value1, value2, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andTranstypeModNotBetween(String value1, String value2) {
            addCriterion("TransType_Mod not between", value1, value2, "transtypeMod");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1IsNull() {
            addCriterion("DlvAnswerNo1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1IsNotNull() {
            addCriterion("DlvAnswerNo1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1EqualTo(String value) {
            addCriterion("DlvAnswerNo1 =", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1NotEqualTo(String value) {
            addCriterion("DlvAnswerNo1 <>", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1GreaterThan(String value) {
            addCriterion("DlvAnswerNo1 >", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1GreaterThanOrEqualTo(String value) {
            addCriterion("DlvAnswerNo1 >=", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1LessThan(String value) {
            addCriterion("DlvAnswerNo1 <", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1LessThanOrEqualTo(String value) {
            addCriterion("DlvAnswerNo1 <=", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1Like(String value) {
            addCriterion("DlvAnswerNo1 like", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1NotLike(String value) {
            addCriterion("DlvAnswerNo1 not like", value, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1In(List<String> values) {
            addCriterion("DlvAnswerNo1 in", values, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1NotIn(List<String> values) {
            addCriterion("DlvAnswerNo1 not in", values, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1Between(String value1, String value2) {
            addCriterion("DlvAnswerNo1 between", value1, value2, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno1NotBetween(String value1, String value2) {
            addCriterion("DlvAnswerNo1 not between", value1, value2, "dlvanswerno1");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2IsNull() {
            addCriterion("DlvAnswerNo2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2IsNotNull() {
            addCriterion("DlvAnswerNo2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2EqualTo(String value) {
            addCriterion("DlvAnswerNo2 =", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2NotEqualTo(String value) {
            addCriterion("DlvAnswerNo2 <>", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2GreaterThan(String value) {
            addCriterion("DlvAnswerNo2 >", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2GreaterThanOrEqualTo(String value) {
            addCriterion("DlvAnswerNo2 >=", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2LessThan(String value) {
            addCriterion("DlvAnswerNo2 <", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2LessThanOrEqualTo(String value) {
            addCriterion("DlvAnswerNo2 <=", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2Like(String value) {
            addCriterion("DlvAnswerNo2 like", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2NotLike(String value) {
            addCriterion("DlvAnswerNo2 not like", value, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2In(List<String> values) {
            addCriterion("DlvAnswerNo2 in", values, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2NotIn(List<String> values) {
            addCriterion("DlvAnswerNo2 not in", values, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2Between(String value1, String value2) {
            addCriterion("DlvAnswerNo2 between", value1, value2, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andDlvanswerno2NotBetween(String value1, String value2) {
            addCriterion("DlvAnswerNo2 not between", value1, value2, "dlvanswerno2");
            return (Criteria) this;
        }

        public Criteria andQtyProducedIsNull() {
            addCriterion("Qty_Produced is null");
            return (Criteria) this;
        }

        public Criteria andQtyProducedIsNotNull() {
            addCriterion("Qty_Produced is not null");
            return (Criteria) this;
        }

        public Criteria andQtyProducedEqualTo(Integer value) {
            addCriterion("Qty_Produced =", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedNotEqualTo(Integer value) {
            addCriterion("Qty_Produced <>", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedGreaterThan(Integer value) {
            addCriterion("Qty_Produced >", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedGreaterThanOrEqualTo(Integer value) {
            addCriterion("Qty_Produced >=", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedLessThan(Integer value) {
            addCriterion("Qty_Produced <", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedLessThanOrEqualTo(Integer value) {
            addCriterion("Qty_Produced <=", value, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedIn(List<Integer> values) {
            addCriterion("Qty_Produced in", values, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedNotIn(List<Integer> values) {
            addCriterion("Qty_Produced not in", values, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedBetween(Integer value1, Integer value2) {
            addCriterion("Qty_Produced between", value1, value2, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andQtyProducedNotBetween(Integer value1, Integer value2) {
            addCriterion("Qty_Produced not between", value1, value2, "qtyProduced");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNull() {
            addCriterion("CnNo is null");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNotNull() {
            addCriterion("CnNo is not null");
            return (Criteria) this;
        }

        public Criteria andCnnoEqualTo(String value) {
            addCriterion("CnNo =", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotEqualTo(String value) {
            addCriterion("CnNo <>", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThan(String value) {
            addCriterion("CnNo >", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThanOrEqualTo(String value) {
            addCriterion("CnNo >=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThan(String value) {
            addCriterion("CnNo <", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThanOrEqualTo(String value) {
            addCriterion("CnNo <=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLike(String value) {
            addCriterion("CnNo like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotLike(String value) {
            addCriterion("CnNo not like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoIn(List<String> values) {
            addCriterion("CnNo in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotIn(List<String> values) {
            addCriterion("CnNo not in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoBetween(String value1, String value2) {
            addCriterion("CnNo between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotBetween(String value1, String value2) {
            addCriterion("CnNo not between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNull() {
            addCriterion("ShiKomiNo is null");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNotNull() {
            addCriterion("ShiKomiNo is not null");
            return (Criteria) this;
        }

        public Criteria andShikominoEqualTo(String value) {
            addCriterion("ShiKomiNo =", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotEqualTo(String value) {
            addCriterion("ShiKomiNo <>", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThan(String value) {
            addCriterion("ShiKomiNo >", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThanOrEqualTo(String value) {
            addCriterion("ShiKomiNo >=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThan(String value) {
            addCriterion("ShiKomiNo <", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThanOrEqualTo(String value) {
            addCriterion("ShiKomiNo <=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLike(String value) {
            addCriterion("ShiKomiNo like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotLike(String value) {
            addCriterion("ShiKomiNo not like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoIn(List<String> values) {
            addCriterion("ShiKomiNo in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotIn(List<String> values) {
            addCriterion("ShiKomiNo not in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoBetween(String value1, String value2) {
            addCriterion("ShiKomiNo between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotBetween(String value1, String value2) {
            addCriterion("ShiKomiNo not between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3IsNull() {
            addCriterion("DlvModDate3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3IsNotNull() {
            addCriterion("DlvModDate3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3EqualTo(Date value) {
            addCriterion("DlvModDate3 =", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotEqualTo(Date value) {
            addCriterion("DlvModDate3 <>", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3GreaterThan(Date value) {
            addCriterion("DlvModDate3 >", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate3 >=", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3LessThan(Date value) {
            addCriterion("DlvModDate3 <", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate3 <=", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3In(List<Date> values) {
            addCriterion("DlvModDate3 in", values, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotIn(List<Date> values) {
            addCriterion("DlvModDate3 not in", values, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3Between(Date value1, Date value2) {
            addCriterion("DlvModDate3 between", value1, value2, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate3 not between", value1, value2, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("OrdType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("OrdType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("OrdType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("OrdType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("OrdType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("OrdType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("OrdType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("OrdType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("OrdType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("OrdType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("OrdType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("OrdType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("OrdType not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagIsNull() {
            addCriterion("OrdAdmit_Flag is null");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagIsNotNull() {
            addCriterion("OrdAdmit_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagEqualTo(String value) {
            addCriterion("OrdAdmit_Flag =", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagNotEqualTo(String value) {
            addCriterion("OrdAdmit_Flag <>", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagGreaterThan(String value) {
            addCriterion("OrdAdmit_Flag >", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagGreaterThanOrEqualTo(String value) {
            addCriterion("OrdAdmit_Flag >=", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagLessThan(String value) {
            addCriterion("OrdAdmit_Flag <", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagLessThanOrEqualTo(String value) {
            addCriterion("OrdAdmit_Flag <=", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagLike(String value) {
            addCriterion("OrdAdmit_Flag like", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagNotLike(String value) {
            addCriterion("OrdAdmit_Flag not like", value, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagIn(List<String> values) {
            addCriterion("OrdAdmit_Flag in", values, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagNotIn(List<String> values) {
            addCriterion("OrdAdmit_Flag not in", values, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagBetween(String value1, String value2) {
            addCriterion("OrdAdmit_Flag between", value1, value2, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andOrdadmitFlagNotBetween(String value1, String value2) {
            addCriterion("OrdAdmit_Flag not between", value1, value2, "ordadmitFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIsNull() {
            addCriterion("DeliveryFlag is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIsNotNull() {
            addCriterion("DeliveryFlag is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagEqualTo(String value) {
            addCriterion("DeliveryFlag =", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotEqualTo(String value) {
            addCriterion("DeliveryFlag <>", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagGreaterThan(String value) {
            addCriterion("DeliveryFlag >", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagGreaterThanOrEqualTo(String value) {
            addCriterion("DeliveryFlag >=", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLessThan(String value) {
            addCriterion("DeliveryFlag <", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLessThanOrEqualTo(String value) {
            addCriterion("DeliveryFlag <=", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagLike(String value) {
            addCriterion("DeliveryFlag like", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotLike(String value) {
            addCriterion("DeliveryFlag not like", value, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagIn(List<String> values) {
            addCriterion("DeliveryFlag in", values, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotIn(List<String> values) {
            addCriterion("DeliveryFlag not in", values, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagBetween(String value1, String value2) {
            addCriterion("DeliveryFlag between", value1, value2, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andDeliveryflagNotBetween(String value1, String value2) {
            addCriterion("DeliveryFlag not between", value1, value2, "deliveryflag");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNull() {
            addCriterion("QtyOnHand is null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNotNull() {
            addCriterion("QtyOnHand is not null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandEqualTo(Integer value) {
            addCriterion("QtyOnHand =", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotEqualTo(Integer value) {
            addCriterion("QtyOnHand <>", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThan(Integer value) {
            addCriterion("QtyOnHand >", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOnHand >=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThan(Integer value) {
            addCriterion("QtyOnHand <", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOnHand <=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIn(List<Integer> values) {
            addCriterion("QtyOnHand in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotIn(List<Integer> values) {
            addCriterion("QtyOnHand not in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnHand between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnHand not between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIsNull() {
            addCriterion("Produce_Factory is null");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIsNotNull() {
            addCriterion("Produce_Factory is not null");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryEqualTo(String value) {
            addCriterion("Produce_Factory =", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotEqualTo(String value) {
            addCriterion("Produce_Factory <>", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryGreaterThan(String value) {
            addCriterion("Produce_Factory >", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryGreaterThanOrEqualTo(String value) {
            addCriterion("Produce_Factory >=", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLessThan(String value) {
            addCriterion("Produce_Factory <", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLessThanOrEqualTo(String value) {
            addCriterion("Produce_Factory <=", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLike(String value) {
            addCriterion("Produce_Factory like", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotLike(String value) {
            addCriterion("Produce_Factory not like", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIn(List<String> values) {
            addCriterion("Produce_Factory in", values, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotIn(List<String> values) {
            addCriterion("Produce_Factory not in", values, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryBetween(String value1, String value2) {
            addCriterion("Produce_Factory between", value1, value2, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotBetween(String value1, String value2) {
            addCriterion("Produce_Factory not between", value1, value2, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIsNull() {
            addCriterion("Produce_Holon is null");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIsNotNull() {
            addCriterion("Produce_Holon is not null");
            return (Criteria) this;
        }

        public Criteria andProduceHolonEqualTo(String value) {
            addCriterion("Produce_Holon =", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotEqualTo(String value) {
            addCriterion("Produce_Holon <>", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonGreaterThan(String value) {
            addCriterion("Produce_Holon >", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonGreaterThanOrEqualTo(String value) {
            addCriterion("Produce_Holon >=", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLessThan(String value) {
            addCriterion("Produce_Holon <", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLessThanOrEqualTo(String value) {
            addCriterion("Produce_Holon <=", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLike(String value) {
            addCriterion("Produce_Holon like", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotLike(String value) {
            addCriterion("Produce_Holon not like", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIn(List<String> values) {
            addCriterion("Produce_Holon in", values, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotIn(List<String> values) {
            addCriterion("Produce_Holon not in", values, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonBetween(String value1, String value2) {
            addCriterion("Produce_Holon between", value1, value2, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotBetween(String value1, String value2) {
            addCriterion("Produce_Holon not between", value1, value2, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIsNull() {
            addCriterion("Errdescription is null");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIsNotNull() {
            addCriterion("Errdescription is not null");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionEqualTo(String value) {
            addCriterion("Errdescription =", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotEqualTo(String value) {
            addCriterion("Errdescription <>", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionGreaterThan(String value) {
            addCriterion("Errdescription >", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("Errdescription >=", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLessThan(String value) {
            addCriterion("Errdescription <", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLessThanOrEqualTo(String value) {
            addCriterion("Errdescription <=", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionLike(String value) {
            addCriterion("Errdescription like", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotLike(String value) {
            addCriterion("Errdescription not like", value, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionIn(List<String> values) {
            addCriterion("Errdescription in", values, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotIn(List<String> values) {
            addCriterion("Errdescription not in", values, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionBetween(String value1, String value2) {
            addCriterion("Errdescription between", value1, value2, "errdescription");
            return (Criteria) this;
        }

        public Criteria andErrdescriptionNotBetween(String value1, String value2) {
            addCriterion("Errdescription not between", value1, value2, "errdescription");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNull() {
            addCriterion("SalesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("SalesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("SalesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("SalesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("SalesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("SalesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("SalesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("SalesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("SalesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("SalesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("SalesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("SalesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNull() {
            addCriterion("HSCode is null");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNotNull() {
            addCriterion("HSCode is not null");
            return (Criteria) this;
        }

        public Criteria andHscodeEqualTo(String value) {
            addCriterion("HSCode =", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotEqualTo(String value) {
            addCriterion("HSCode <>", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThan(String value) {
            addCriterion("HSCode >", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThanOrEqualTo(String value) {
            addCriterion("HSCode >=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThan(String value) {
            addCriterion("HSCode <", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThanOrEqualTo(String value) {
            addCriterion("HSCode <=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLike(String value) {
            addCriterion("HSCode like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotLike(String value) {
            addCriterion("HSCode not like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeIn(List<String> values) {
            addCriterion("HSCode in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotIn(List<String> values) {
            addCriterion("HSCode not in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeBetween(String value1, String value2) {
            addCriterion("HSCode between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotBetween(String value1, String value2) {
            addCriterion("HSCode not between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andGreencodeIsNull() {
            addCriterion("GreenCode is null");
            return (Criteria) this;
        }

        public Criteria andGreencodeIsNotNull() {
            addCriterion("GreenCode is not null");
            return (Criteria) this;
        }

        public Criteria andGreencodeEqualTo(String value) {
            addCriterion("GreenCode =", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotEqualTo(String value) {
            addCriterion("GreenCode <>", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThan(String value) {
            addCriterion("GreenCode >", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThanOrEqualTo(String value) {
            addCriterion("GreenCode >=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThan(String value) {
            addCriterion("GreenCode <", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThanOrEqualTo(String value) {
            addCriterion("GreenCode <=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLike(String value) {
            addCriterion("GreenCode like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotLike(String value) {
            addCriterion("GreenCode not like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeIn(List<String> values) {
            addCriterion("GreenCode in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotIn(List<String> values) {
            addCriterion("GreenCode not in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeBetween(String value1, String value2) {
            addCriterion("GreenCode between", value1, value2, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotBetween(String value1, String value2) {
            addCriterion("GreenCode not between", value1, value2, "greencode");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssIsNull() {
            addCriterion("OrderNO_GSS is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssIsNotNull() {
            addCriterion("OrderNO_GSS is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssEqualTo(String value) {
            addCriterion("OrderNO_GSS =", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssNotEqualTo(String value) {
            addCriterion("OrderNO_GSS <>", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssGreaterThan(String value) {
            addCriterion("OrderNO_GSS >", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssGreaterThanOrEqualTo(String value) {
            addCriterion("OrderNO_GSS >=", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssLessThan(String value) {
            addCriterion("OrderNO_GSS <", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssLessThanOrEqualTo(String value) {
            addCriterion("OrderNO_GSS <=", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssLike(String value) {
            addCriterion("OrderNO_GSS like", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssNotLike(String value) {
            addCriterion("OrderNO_GSS not like", value, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssIn(List<String> values) {
            addCriterion("OrderNO_GSS in", values, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssNotIn(List<String> values) {
            addCriterion("OrderNO_GSS not in", values, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssBetween(String value1, String value2) {
            addCriterion("OrderNO_GSS between", value1, value2, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoGssNotBetween(String value1, String value2) {
            addCriterion("OrderNO_GSS not between", value1, value2, "ordernoGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssIsNull() {
            addCriterion("OrderNO_Item_GSS is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssIsNotNull() {
            addCriterion("OrderNO_Item_GSS is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssEqualTo(String value) {
            addCriterion("OrderNO_Item_GSS =", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssNotEqualTo(String value) {
            addCriterion("OrderNO_Item_GSS <>", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssGreaterThan(String value) {
            addCriterion("OrderNO_Item_GSS >", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssGreaterThanOrEqualTo(String value) {
            addCriterion("OrderNO_Item_GSS >=", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssLessThan(String value) {
            addCriterion("OrderNO_Item_GSS <", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssLessThanOrEqualTo(String value) {
            addCriterion("OrderNO_Item_GSS <=", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssLike(String value) {
            addCriterion("OrderNO_Item_GSS like", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssNotLike(String value) {
            addCriterion("OrderNO_Item_GSS not like", value, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssIn(List<String> values) {
            addCriterion("OrderNO_Item_GSS in", values, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssNotIn(List<String> values) {
            addCriterion("OrderNO_Item_GSS not in", values, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssBetween(String value1, String value2) {
            addCriterion("OrderNO_Item_GSS between", value1, value2, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andOrdernoItemGssNotBetween(String value1, String value2) {
            addCriterion("OrderNO_Item_GSS not between", value1, value2, "ordernoItemGss");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNull() {
            addCriterion("InDate_theory is null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNotNull() {
            addCriterion("InDate_theory is not null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryEqualTo(Date value) {
            addCriterion("InDate_theory =", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotEqualTo(Date value) {
            addCriterion("InDate_theory <>", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThan(Date value) {
            addCriterion("InDate_theory >", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_theory >=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThan(Date value) {
            addCriterion("InDate_theory <", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThanOrEqualTo(Date value) {
            addCriterion("InDate_theory <=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIn(List<Date> values) {
            addCriterion("InDate_theory in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotIn(List<Date> values) {
            addCriterion("InDate_theory not in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryBetween(Date value1, Date value2) {
            addCriterion("InDate_theory between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotBetween(Date value1, Date value2) {
            addCriterion("InDate_theory not between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNull() {
            addCriterion("RcvOrd_Date is null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNotNull() {
            addCriterion("RcvOrd_Date is not null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateEqualTo(Date value) {
            addCriterion("RcvOrd_Date =", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotEqualTo(Date value) {
            addCriterion("RcvOrd_Date <>", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThan(Date value) {
            addCriterion("RcvOrd_Date >", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date >=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThan(Date value) {
            addCriterion("RcvOrd_Date <", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date <=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIn(List<Date> values) {
            addCriterion("RcvOrd_Date in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotIn(List<Date> values) {
            addCriterion("RcvOrd_Date not in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date not between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNull() {
            addCriterion("InDate_InFact is null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNotNull() {
            addCriterion("InDate_InFact is not null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactEqualTo(Date value) {
            addCriterion("InDate_InFact =", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotEqualTo(Date value) {
            addCriterion("InDate_InFact <>", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThan(Date value) {
            addCriterion("InDate_InFact >", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact >=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThan(Date value) {
            addCriterion("InDate_InFact <", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact <=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIn(List<Date> values) {
            addCriterion("InDate_InFact in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotIn(List<Date> values) {
            addCriterion("InDate_InFact not in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact not between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNull() {
            addCriterion("Exp_date is null");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNotNull() {
            addCriterion("Exp_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpDateEqualTo(Date value) {
            addCriterion("Exp_date =", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotEqualTo(Date value) {
            addCriterion("Exp_date <>", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThan(Date value) {
            addCriterion("Exp_date >", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThanOrEqualTo(Date value) {
            addCriterion("Exp_date >=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThan(Date value) {
            addCriterion("Exp_date <", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThanOrEqualTo(Date value) {
            addCriterion("Exp_date <=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateIn(List<Date> values) {
            addCriterion("Exp_date in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotIn(List<Date> values) {
            addCriterion("Exp_date not in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateBetween(Date value1, Date value2) {
            addCriterion("Exp_date between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotBetween(Date value1, Date value2) {
            addCriterion("Exp_date not between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNull() {
            addCriterion("Export_Flag is null");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNotNull() {
            addCriterion("Export_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andExportFlagEqualTo(String value) {
            addCriterion("Export_Flag =", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotEqualTo(String value) {
            addCriterion("Export_Flag <>", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThan(String value) {
            addCriterion("Export_Flag >", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThanOrEqualTo(String value) {
            addCriterion("Export_Flag >=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThan(String value) {
            addCriterion("Export_Flag <", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThanOrEqualTo(String value) {
            addCriterion("Export_Flag <=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLike(String value) {
            addCriterion("Export_Flag like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotLike(String value) {
            addCriterion("Export_Flag not like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagIn(List<String> values) {
            addCriterion("Export_Flag in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotIn(List<String> values) {
            addCriterion("Export_Flag not in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagBetween(String value1, String value2) {
            addCriterion("Export_Flag between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotBetween(String value1, String value2) {
            addCriterion("Export_Flag not between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNull() {
            addCriterion("Holon_Name is null");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNotNull() {
            addCriterion("Holon_Name is not null");
            return (Criteria) this;
        }

        public Criteria andHolonNameEqualTo(String value) {
            addCriterion("Holon_Name =", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotEqualTo(String value) {
            addCriterion("Holon_Name <>", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThan(String value) {
            addCriterion("Holon_Name >", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThanOrEqualTo(String value) {
            addCriterion("Holon_Name >=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThan(String value) {
            addCriterion("Holon_Name <", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThanOrEqualTo(String value) {
            addCriterion("Holon_Name <=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLike(String value) {
            addCriterion("Holon_Name like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotLike(String value) {
            addCriterion("Holon_Name not like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameIn(List<String> values) {
            addCriterion("Holon_Name in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotIn(List<String> values) {
            addCriterion("Holon_Name not in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameBetween(String value1, String value2) {
            addCriterion("Holon_Name between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotBetween(String value1, String value2) {
            addCriterion("Holon_Name not between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNull() {
            addCriterion("BeginProduce_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNotNull() {
            addCriterion("BeginProduce_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateEqualTo(Date value) {
            addCriterion("BeginProduce_date =", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotEqualTo(Date value) {
            addCriterion("BeginProduce_date <>", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThan(Date value) {
            addCriterion("BeginProduce_date >", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date >=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThan(Date value) {
            addCriterion("BeginProduce_date <", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date <=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIn(List<Date> values) {
            addCriterion("BeginProduce_date in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotIn(List<Date> values) {
            addCriterion("BeginProduce_date not in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date not between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1IsNull() {
            addCriterion("DlvModTime1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1IsNotNull() {
            addCriterion("DlvModTime1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1EqualTo(Date value) {
            addCriterion("DlvModTime1 =", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotEqualTo(Date value) {
            addCriterion("DlvModTime1 <>", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1GreaterThan(Date value) {
            addCriterion("DlvModTime1 >", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime1 >=", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1LessThan(Date value) {
            addCriterion("DlvModTime1 <", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime1 <=", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1In(List<Date> values) {
            addCriterion("DlvModTime1 in", values, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotIn(List<Date> values) {
            addCriterion("DlvModTime1 not in", values, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1Between(Date value1, Date value2) {
            addCriterion("DlvModTime1 between", value1, value2, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime1 not between", value1, value2, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2IsNull() {
            addCriterion("DlvModTime2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2IsNotNull() {
            addCriterion("DlvModTime2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2EqualTo(Date value) {
            addCriterion("DlvModTime2 =", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotEqualTo(Date value) {
            addCriterion("DlvModTime2 <>", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2GreaterThan(Date value) {
            addCriterion("DlvModTime2 >", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime2 >=", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2LessThan(Date value) {
            addCriterion("DlvModTime2 <", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime2 <=", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2In(List<Date> values) {
            addCriterion("DlvModTime2 in", values, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotIn(List<Date> values) {
            addCriterion("DlvModTime2 not in", values, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2Between(Date value1, Date value2) {
            addCriterion("DlvModTime2 between", value1, value2, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime2 not between", value1, value2, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3IsNull() {
            addCriterion("DlvModTime3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3IsNotNull() {
            addCriterion("DlvModTime3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3EqualTo(Date value) {
            addCriterion("DlvModTime3 =", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotEqualTo(Date value) {
            addCriterion("DlvModTime3 <>", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3GreaterThan(Date value) {
            addCriterion("DlvModTime3 >", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime3 >=", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3LessThan(Date value) {
            addCriterion("DlvModTime3 <", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime3 <=", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3In(List<Date> values) {
            addCriterion("DlvModTime3 in", values, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotIn(List<Date> values) {
            addCriterion("DlvModTime3 not in", values, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3Between(Date value1, Date value2) {
            addCriterion("DlvModTime3 between", value1, value2, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime3 not between", value1, value2, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNull() {
            addCriterion("TradeCompanyId is null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNotNull() {
            addCriterion("TradeCompanyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidEqualTo(String value) {
            addCriterion("TradeCompanyId =", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotEqualTo(String value) {
            addCriterion("TradeCompanyId <>", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThan(String value) {
            addCriterion("TradeCompanyId >", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId >=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThan(String value) {
            addCriterion("TradeCompanyId <", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId <=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLike(String value) {
            addCriterion("TradeCompanyId like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotLike(String value) {
            addCriterion("TradeCompanyId not like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIn(List<String> values) {
            addCriterion("TradeCompanyId in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotIn(List<String> values) {
            addCriterion("TradeCompanyId not in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidBetween(String value1, String value2) {
            addCriterion("TradeCompanyId between", value1, value2, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotBetween(String value1, String value2) {
            addCriterion("TradeCompanyId not between", value1, value2, "tradecompanyid");
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
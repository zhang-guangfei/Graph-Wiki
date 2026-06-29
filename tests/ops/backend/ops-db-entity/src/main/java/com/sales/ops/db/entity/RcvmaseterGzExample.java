package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RcvmaseterGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvmaseterGzExample() {
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

        public Criteria andRordernoIsNull() {
            addCriterion("ROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("ROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("ROrderNo =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("ROrderNo <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("ROrderNo >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ROrderNo >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("ROrderNo <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("ROrderNo <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("ROrderNo like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("ROrderNo not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("ROrderNo in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("ROrderNo not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("ROrderNo between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("ROrderNo not between", value1, value2, "rorderno");
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

        public Criteria andEnduserIsNull() {
            addCriterion("EndUser is null");
            return (Criteria) this;
        }

        public Criteria andEnduserIsNotNull() {
            addCriterion("EndUser is not null");
            return (Criteria) this;
        }

        public Criteria andEnduserEqualTo(String value) {
            addCriterion("EndUser =", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserNotEqualTo(String value) {
            addCriterion("EndUser <>", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserGreaterThan(String value) {
            addCriterion("EndUser >", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserGreaterThanOrEqualTo(String value) {
            addCriterion("EndUser >=", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserLessThan(String value) {
            addCriterion("EndUser <", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserLessThanOrEqualTo(String value) {
            addCriterion("EndUser <=", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserLike(String value) {
            addCriterion("EndUser like", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserNotLike(String value) {
            addCriterion("EndUser not like", value, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserIn(List<String> values) {
            addCriterion("EndUser in", values, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserNotIn(List<String> values) {
            addCriterion("EndUser not in", values, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserBetween(String value1, String value2) {
            addCriterion("EndUser between", value1, value2, "enduser");
            return (Criteria) this;
        }

        public Criteria andEnduserNotBetween(String value1, String value2) {
            addCriterion("EndUser not between", value1, value2, "enduser");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNull() {
            addCriterion("ROrdDate is null");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNotNull() {
            addCriterion("ROrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andRorddateEqualTo(Date value) {
            addCriterion("ROrdDate =", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotEqualTo(Date value) {
            addCriterion("ROrdDate <>", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThan(Date value) {
            addCriterion("ROrdDate >", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThanOrEqualTo(Date value) {
            addCriterion("ROrdDate >=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThan(Date value) {
            addCriterion("ROrdDate <", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThanOrEqualTo(Date value) {
            addCriterion("ROrdDate <=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateIn(List<Date> values) {
            addCriterion("ROrdDate in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotIn(List<Date> values) {
            addCriterion("ROrdDate not in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateBetween(Date value1, Date value2) {
            addCriterion("ROrdDate between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotBetween(Date value1, Date value2) {
            addCriterion("ROrdDate not between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andDlventireIsNull() {
            addCriterion("DlvEntire is null");
            return (Criteria) this;
        }

        public Criteria andDlventireIsNotNull() {
            addCriterion("DlvEntire is not null");
            return (Criteria) this;
        }

        public Criteria andDlventireEqualTo(String value) {
            addCriterion("DlvEntire =", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotEqualTo(String value) {
            addCriterion("DlvEntire <>", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThan(String value) {
            addCriterion("DlvEntire >", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThanOrEqualTo(String value) {
            addCriterion("DlvEntire >=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThan(String value) {
            addCriterion("DlvEntire <", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThanOrEqualTo(String value) {
            addCriterion("DlvEntire <=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLike(String value) {
            addCriterion("DlvEntire like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotLike(String value) {
            addCriterion("DlvEntire not like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireIn(List<String> values) {
            addCriterion("DlvEntire in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotIn(List<String> values) {
            addCriterion("DlvEntire not in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireBetween(String value1, String value2) {
            addCriterion("DlvEntire between", value1, value2, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotBetween(String value1, String value2) {
            addCriterion("DlvEntire not between", value1, value2, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNull() {
            addCriterion("DlvSite is null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNotNull() {
            addCriterion("DlvSite is not null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteEqualTo(String value) {
            addCriterion("DlvSite =", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotEqualTo(String value) {
            addCriterion("DlvSite <>", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThan(String value) {
            addCriterion("DlvSite >", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThanOrEqualTo(String value) {
            addCriterion("DlvSite >=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThan(String value) {
            addCriterion("DlvSite <", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThanOrEqualTo(String value) {
            addCriterion("DlvSite <=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLike(String value) {
            addCriterion("DlvSite like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotLike(String value) {
            addCriterion("DlvSite not like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIn(List<String> values) {
            addCriterion("DlvSite in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotIn(List<String> values) {
            addCriterion("DlvSite not in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteBetween(String value1, String value2) {
            addCriterion("DlvSite between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotBetween(String value1, String value2) {
            addCriterion("DlvSite not between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andTransfeeIsNull() {
            addCriterion("TransFee is null");
            return (Criteria) this;
        }

        public Criteria andTransfeeIsNotNull() {
            addCriterion("TransFee is not null");
            return (Criteria) this;
        }

        public Criteria andTransfeeEqualTo(String value) {
            addCriterion("TransFee =", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotEqualTo(String value) {
            addCriterion("TransFee <>", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThan(String value) {
            addCriterion("TransFee >", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThanOrEqualTo(String value) {
            addCriterion("TransFee >=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThan(String value) {
            addCriterion("TransFee <", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThanOrEqualTo(String value) {
            addCriterion("TransFee <=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLike(String value) {
            addCriterion("TransFee like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotLike(String value) {
            addCriterion("TransFee not like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeIn(List<String> values) {
            addCriterion("TransFee in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotIn(List<String> values) {
            addCriterion("TransFee not in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeBetween(String value1, String value2) {
            addCriterion("TransFee between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotBetween(String value1, String value2) {
            addCriterion("TransFee not between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTranschannelIsNull() {
            addCriterion("TransChannel is null");
            return (Criteria) this;
        }

        public Criteria andTranschannelIsNotNull() {
            addCriterion("TransChannel is not null");
            return (Criteria) this;
        }

        public Criteria andTranschannelEqualTo(String value) {
            addCriterion("TransChannel =", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotEqualTo(String value) {
            addCriterion("TransChannel <>", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThan(String value) {
            addCriterion("TransChannel >", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThanOrEqualTo(String value) {
            addCriterion("TransChannel >=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThan(String value) {
            addCriterion("TransChannel <", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThanOrEqualTo(String value) {
            addCriterion("TransChannel <=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLike(String value) {
            addCriterion("TransChannel like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotLike(String value) {
            addCriterion("TransChannel not like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelIn(List<String> values) {
            addCriterion("TransChannel in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotIn(List<String> values) {
            addCriterion("TransChannel not in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelBetween(String value1, String value2) {
            addCriterion("TransChannel between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotBetween(String value1, String value2) {
            addCriterion("TransChannel not between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIsNull() {
            addCriterion("PrjCode is null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIsNotNull() {
            addCriterion("PrjCode is not null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeEqualTo(String value) {
            addCriterion("PrjCode =", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotEqualTo(String value) {
            addCriterion("PrjCode <>", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThan(String value) {
            addCriterion("PrjCode >", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PrjCode >=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThan(String value) {
            addCriterion("PrjCode <", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThanOrEqualTo(String value) {
            addCriterion("PrjCode <=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLike(String value) {
            addCriterion("PrjCode like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotLike(String value) {
            addCriterion("PrjCode not like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIn(List<String> values) {
            addCriterion("PrjCode in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotIn(List<String> values) {
            addCriterion("PrjCode not in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeBetween(String value1, String value2) {
            addCriterion("PrjCode between", value1, value2, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotBetween(String value1, String value2) {
            addCriterion("PrjCode not between", value1, value2, "prjcode");
            return (Criteria) this;
        }

        public Criteria andEmployeeIsNull() {
            addCriterion("Employee is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIsNotNull() {
            addCriterion("Employee is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeEqualTo(String value) {
            addCriterion("Employee =", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeNotEqualTo(String value) {
            addCriterion("Employee <>", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeGreaterThan(String value) {
            addCriterion("Employee >", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeGreaterThanOrEqualTo(String value) {
            addCriterion("Employee >=", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeLessThan(String value) {
            addCriterion("Employee <", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeLessThanOrEqualTo(String value) {
            addCriterion("Employee <=", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeLike(String value) {
            addCriterion("Employee like", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeNotLike(String value) {
            addCriterion("Employee not like", value, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeIn(List<String> values) {
            addCriterion("Employee in", values, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeNotIn(List<String> values) {
            addCriterion("Employee not in", values, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeBetween(String value1, String value2) {
            addCriterion("Employee between", value1, value2, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeeNotBetween(String value1, String value2) {
            addCriterion("Employee not between", value1, value2, "employee");
            return (Criteria) this;
        }

        public Criteria andEmployeenoIsNull() {
            addCriterion("EmployeeNo is null");
            return (Criteria) this;
        }

        public Criteria andEmployeenoIsNotNull() {
            addCriterion("EmployeeNo is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeenoEqualTo(String value) {
            addCriterion("EmployeeNo =", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoNotEqualTo(String value) {
            addCriterion("EmployeeNo <>", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoGreaterThan(String value) {
            addCriterion("EmployeeNo >", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoGreaterThanOrEqualTo(String value) {
            addCriterion("EmployeeNo >=", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoLessThan(String value) {
            addCriterion("EmployeeNo <", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoLessThanOrEqualTo(String value) {
            addCriterion("EmployeeNo <=", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoLike(String value) {
            addCriterion("EmployeeNo like", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoNotLike(String value) {
            addCriterion("EmployeeNo not like", value, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoIn(List<String> values) {
            addCriterion("EmployeeNo in", values, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoNotIn(List<String> values) {
            addCriterion("EmployeeNo not in", values, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoBetween(String value1, String value2) {
            addCriterion("EmployeeNo between", value1, value2, "employeeno");
            return (Criteria) this;
        }

        public Criteria andEmployeenoNotBetween(String value1, String value2) {
            addCriterion("EmployeeNo not between", value1, value2, "employeeno");
            return (Criteria) this;
        }

        public Criteria andDlvtype1IsNull() {
            addCriterion("DlvType1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype1IsNotNull() {
            addCriterion("DlvType1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype1EqualTo(String value) {
            addCriterion("DlvType1 =", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotEqualTo(String value) {
            addCriterion("DlvType1 <>", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1GreaterThan(String value) {
            addCriterion("DlvType1 >", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType1 >=", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1LessThan(String value) {
            addCriterion("DlvType1 <", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1LessThanOrEqualTo(String value) {
            addCriterion("DlvType1 <=", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1Like(String value) {
            addCriterion("DlvType1 like", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotLike(String value) {
            addCriterion("DlvType1 not like", value, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1In(List<String> values) {
            addCriterion("DlvType1 in", values, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotIn(List<String> values) {
            addCriterion("DlvType1 not in", values, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1Between(String value1, String value2) {
            addCriterion("DlvType1 between", value1, value2, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype1NotBetween(String value1, String value2) {
            addCriterion("DlvType1 not between", value1, value2, "dlvtype1");
            return (Criteria) this;
        }

        public Criteria andDlvtype2IsNull() {
            addCriterion("DlvType2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype2IsNotNull() {
            addCriterion("DlvType2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype2EqualTo(String value) {
            addCriterion("DlvType2 =", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotEqualTo(String value) {
            addCriterion("DlvType2 <>", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2GreaterThan(String value) {
            addCriterion("DlvType2 >", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType2 >=", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2LessThan(String value) {
            addCriterion("DlvType2 <", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2LessThanOrEqualTo(String value) {
            addCriterion("DlvType2 <=", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2Like(String value) {
            addCriterion("DlvType2 like", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotLike(String value) {
            addCriterion("DlvType2 not like", value, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2In(List<String> values) {
            addCriterion("DlvType2 in", values, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotIn(List<String> values) {
            addCriterion("DlvType2 not in", values, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2Between(String value1, String value2) {
            addCriterion("DlvType2 between", value1, value2, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype2NotBetween(String value1, String value2) {
            addCriterion("DlvType2 not between", value1, value2, "dlvtype2");
            return (Criteria) this;
        }

        public Criteria andDlvtype3IsNull() {
            addCriterion("DlvType3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvtype3IsNotNull() {
            addCriterion("DlvType3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtype3EqualTo(String value) {
            addCriterion("DlvType3 =", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotEqualTo(String value) {
            addCriterion("DlvType3 <>", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3GreaterThan(String value) {
            addCriterion("DlvType3 >", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3GreaterThanOrEqualTo(String value) {
            addCriterion("DlvType3 >=", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3LessThan(String value) {
            addCriterion("DlvType3 <", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3LessThanOrEqualTo(String value) {
            addCriterion("DlvType3 <=", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3Like(String value) {
            addCriterion("DlvType3 like", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotLike(String value) {
            addCriterion("DlvType3 not like", value, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3In(List<String> values) {
            addCriterion("DlvType3 in", values, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotIn(List<String> values) {
            addCriterion("DlvType3 not in", values, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3Between(String value1, String value2) {
            addCriterion("DlvType3 between", value1, value2, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andDlvtype3NotBetween(String value1, String value2) {
            addCriterion("DlvType3 not between", value1, value2, "dlvtype3");
            return (Criteria) this;
        }

        public Criteria andPacktypeIsNull() {
            addCriterion("PackType is null");
            return (Criteria) this;
        }

        public Criteria andPacktypeIsNotNull() {
            addCriterion("PackType is not null");
            return (Criteria) this;
        }

        public Criteria andPacktypeEqualTo(String value) {
            addCriterion("PackType =", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotEqualTo(String value) {
            addCriterion("PackType <>", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeGreaterThan(String value) {
            addCriterion("PackType >", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeGreaterThanOrEqualTo(String value) {
            addCriterion("PackType >=", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLessThan(String value) {
            addCriterion("PackType <", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLessThanOrEqualTo(String value) {
            addCriterion("PackType <=", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLike(String value) {
            addCriterion("PackType like", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotLike(String value) {
            addCriterion("PackType not like", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeIn(List<String> values) {
            addCriterion("PackType in", values, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotIn(List<String> values) {
            addCriterion("PackType not in", values, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeBetween(String value1, String value2) {
            addCriterion("PackType between", value1, value2, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotBetween(String value1, String value2) {
            addCriterion("PackType not between", value1, value2, "packtype");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIsNull() {
            addCriterion("Customer_HK is null");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIsNotNull() {
            addCriterion("Customer_HK is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerHkEqualTo(String value) {
            addCriterion("Customer_HK =", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotEqualTo(String value) {
            addCriterion("Customer_HK <>", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkGreaterThan(String value) {
            addCriterion("Customer_HK >", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkGreaterThanOrEqualTo(String value) {
            addCriterion("Customer_HK >=", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLessThan(String value) {
            addCriterion("Customer_HK <", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLessThanOrEqualTo(String value) {
            addCriterion("Customer_HK <=", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLike(String value) {
            addCriterion("Customer_HK like", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotLike(String value) {
            addCriterion("Customer_HK not like", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIn(List<String> values) {
            addCriterion("Customer_HK in", values, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotIn(List<String> values) {
            addCriterion("Customer_HK not in", values, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkBetween(String value1, String value2) {
            addCriterion("Customer_HK between", value1, value2, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotBetween(String value1, String value2) {
            addCriterion("Customer_HK not between", value1, value2, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCquerynoIsNull() {
            addCriterion("CQueryNo is null");
            return (Criteria) this;
        }

        public Criteria andCquerynoIsNotNull() {
            addCriterion("CQueryNo is not null");
            return (Criteria) this;
        }

        public Criteria andCquerynoEqualTo(String value) {
            addCriterion("CQueryNo =", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotEqualTo(String value) {
            addCriterion("CQueryNo <>", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoGreaterThan(String value) {
            addCriterion("CQueryNo >", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoGreaterThanOrEqualTo(String value) {
            addCriterion("CQueryNo >=", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLessThan(String value) {
            addCriterion("CQueryNo <", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLessThanOrEqualTo(String value) {
            addCriterion("CQueryNo <=", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoLike(String value) {
            addCriterion("CQueryNo like", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotLike(String value) {
            addCriterion("CQueryNo not like", value, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoIn(List<String> values) {
            addCriterion("CQueryNo in", values, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotIn(List<String> values) {
            addCriterion("CQueryNo not in", values, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoBetween(String value1, String value2) {
            addCriterion("CQueryNo between", value1, value2, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCquerynoNotBetween(String value1, String value2) {
            addCriterion("CQueryNo not between", value1, value2, "cqueryno");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNull() {
            addCriterion("COrderNo is null");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNotNull() {
            addCriterion("COrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andCordernoEqualTo(String value) {
            addCriterion("COrderNo =", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotEqualTo(String value) {
            addCriterion("COrderNo <>", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThan(String value) {
            addCriterion("COrderNo >", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThanOrEqualTo(String value) {
            addCriterion("COrderNo >=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThan(String value) {
            addCriterion("COrderNo <", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThanOrEqualTo(String value) {
            addCriterion("COrderNo <=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLike(String value) {
            addCriterion("COrderNo like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotLike(String value) {
            addCriterion("COrderNo not like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoIn(List<String> values) {
            addCriterion("COrderNo in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotIn(List<String> values) {
            addCriterion("COrderNo not in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoBetween(String value1, String value2) {
            addCriterion("COrderNo between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotBetween(String value1, String value2) {
            addCriterion("COrderNo not between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("OptTime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("OptTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("OptTime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("OptTime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("OptTime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OptTime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("OptTime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("OptTime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("OptTime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("OptTime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("OptTime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("OptTime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("Operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("Operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("Operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("Operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("Operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("Operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("Operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("Operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("Operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("Operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("Operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("Operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("Operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("Operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNull() {
            addCriterion("ContractNo is null");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNotNull() {
            addCriterion("ContractNo is not null");
            return (Criteria) this;
        }

        public Criteria andContractnoEqualTo(String value) {
            addCriterion("ContractNo =", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotEqualTo(String value) {
            addCriterion("ContractNo <>", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThan(String value) {
            addCriterion("ContractNo >", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThanOrEqualTo(String value) {
            addCriterion("ContractNo >=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThan(String value) {
            addCriterion("ContractNo <", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThanOrEqualTo(String value) {
            addCriterion("ContractNo <=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLike(String value) {
            addCriterion("ContractNo like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotLike(String value) {
            addCriterion("ContractNo not like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoIn(List<String> values) {
            addCriterion("ContractNo in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotIn(List<String> values) {
            addCriterion("ContractNo not in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoBetween(String value1, String value2) {
            addCriterion("ContractNo between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotBetween(String value1, String value2) {
            addCriterion("ContractNo not between", value1, value2, "contractno");
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
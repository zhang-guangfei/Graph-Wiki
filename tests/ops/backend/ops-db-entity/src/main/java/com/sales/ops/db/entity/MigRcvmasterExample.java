package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigRcvmasterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MigRcvmasterExample() {
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

        public Criteria andOrordernoIsNull() {
            addCriterion("ORorderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNotNull() {
            addCriterion("ORorderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrordernoEqualTo(String value) {
            addCriterion("ORorderNo =", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotEqualTo(String value) {
            addCriterion("ORorderNo <>", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThan(String value) {
            addCriterion("ORorderNo >", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ORorderNo >=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThan(String value) {
            addCriterion("ORorderNo <", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThanOrEqualTo(String value) {
            addCriterion("ORorderNo <=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLike(String value) {
            addCriterion("ORorderNo like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotLike(String value) {
            addCriterion("ORorderNo not like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoIn(List<String> values) {
            addCriterion("ORorderNo in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotIn(List<String> values) {
            addCriterion("ORorderNo not in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoBetween(String value1, String value2) {
            addCriterion("ORorderNo between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotBetween(String value1, String value2) {
            addCriterion("ORorderNo not between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNull() {
            addCriterion("DlvType is null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNotNull() {
            addCriterion("DlvType is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeEqualTo(String value) {
            addCriterion("DlvType =", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotEqualTo(String value) {
            addCriterion("DlvType <>", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThan(String value) {
            addCriterion("DlvType >", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("DlvType >=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThan(String value) {
            addCriterion("DlvType <", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThanOrEqualTo(String value) {
            addCriterion("DlvType <=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLike(String value) {
            addCriterion("DlvType like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotLike(String value) {
            addCriterion("DlvType not like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIn(List<String> values) {
            addCriterion("DlvType in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotIn(List<String> values) {
            addCriterion("DlvType not in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeBetween(String value1, String value2) {
            addCriterion("DlvType between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotBetween(String value1, String value2) {
            addCriterion("DlvType not between", value1, value2, "dlvtype");
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

        public Criteria andQuotationnoIsNull() {
            addCriterion("QuotationNo is null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIsNotNull() {
            addCriterion("QuotationNo is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoEqualTo(String value) {
            addCriterion("QuotationNo =", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotEqualTo(String value) {
            addCriterion("QuotationNo <>", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThan(String value) {
            addCriterion("QuotationNo >", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThanOrEqualTo(String value) {
            addCriterion("QuotationNo >=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThan(String value) {
            addCriterion("QuotationNo <", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThanOrEqualTo(String value) {
            addCriterion("QuotationNo <=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLike(String value) {
            addCriterion("QuotationNo like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotLike(String value) {
            addCriterion("QuotationNo not like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIn(List<String> values) {
            addCriterion("QuotationNo in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotIn(List<String> values) {
            addCriterion("QuotationNo not in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoBetween(String value1, String value2) {
            addCriterion("QuotationNo between", value1, value2, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotBetween(String value1, String value2) {
            addCriterion("QuotationNo not between", value1, value2, "quotationno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNull() {
            addCriterion("PurchaseNO is null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNotNull() {
            addCriterion("PurchaseNO is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoEqualTo(String value) {
            addCriterion("PurchaseNO =", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotEqualTo(String value) {
            addCriterion("PurchaseNO <>", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThan(String value) {
            addCriterion("PurchaseNO >", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseNO >=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThan(String value) {
            addCriterion("PurchaseNO <", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseNO <=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLike(String value) {
            addCriterion("PurchaseNO like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotLike(String value) {
            addCriterion("PurchaseNO not like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIn(List<String> values) {
            addCriterion("PurchaseNO in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotIn(List<String> values) {
            addCriterion("PurchaseNO not in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoBetween(String value1, String value2) {
            addCriterion("PurchaseNO between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotBetween(String value1, String value2) {
            addCriterion("PurchaseNO not between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNull() {
            addCriterion("CttFlag is null");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNotNull() {
            addCriterion("CttFlag is not null");
            return (Criteria) this;
        }

        public Criteria andCttflagEqualTo(String value) {
            addCriterion("CttFlag =", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotEqualTo(String value) {
            addCriterion("CttFlag <>", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThan(String value) {
            addCriterion("CttFlag >", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThanOrEqualTo(String value) {
            addCriterion("CttFlag >=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThan(String value) {
            addCriterion("CttFlag <", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThanOrEqualTo(String value) {
            addCriterion("CttFlag <=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLike(String value) {
            addCriterion("CttFlag like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotLike(String value) {
            addCriterion("CttFlag not like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagIn(List<String> values) {
            addCriterion("CttFlag in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotIn(List<String> values) {
            addCriterion("CttFlag not in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagBetween(String value1, String value2) {
            addCriterion("CttFlag between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotBetween(String value1, String value2) {
            addCriterion("CttFlag not between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("Ordtype is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("Ordtype is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("Ordtype =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("Ordtype <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("Ordtype >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("Ordtype >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("Ordtype <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("Ordtype <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("Ordtype like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("Ordtype not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("Ordtype in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("Ordtype not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("Ordtype between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("Ordtype not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andTypecodeIsNull() {
            addCriterion("TypeCode is null");
            return (Criteria) this;
        }

        public Criteria andTypecodeIsNotNull() {
            addCriterion("TypeCode is not null");
            return (Criteria) this;
        }

        public Criteria andTypecodeEqualTo(String value) {
            addCriterion("TypeCode =", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotEqualTo(String value) {
            addCriterion("TypeCode <>", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThan(String value) {
            addCriterion("TypeCode >", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThanOrEqualTo(String value) {
            addCriterion("TypeCode >=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThan(String value) {
            addCriterion("TypeCode <", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThanOrEqualTo(String value) {
            addCriterion("TypeCode <=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLike(String value) {
            addCriterion("TypeCode like", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotLike(String value) {
            addCriterion("TypeCode not like", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeIn(List<String> values) {
            addCriterion("TypeCode in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotIn(List<String> values) {
            addCriterion("TypeCode not in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeBetween(String value1, String value2) {
            addCriterion("TypeCode between", value1, value2, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotBetween(String value1, String value2) {
            addCriterion("TypeCode not between", value1, value2, "typecode");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIsNull() {
            addCriterion("PriceChecked is null");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIsNotNull() {
            addCriterion("PriceChecked is not null");
            return (Criteria) this;
        }

        public Criteria andPricecheckedEqualTo(Boolean value) {
            addCriterion("PriceChecked =", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotEqualTo(Boolean value) {
            addCriterion("PriceChecked <>", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedGreaterThan(Boolean value) {
            addCriterion("PriceChecked >", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("PriceChecked >=", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedLessThan(Boolean value) {
            addCriterion("PriceChecked <", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedLessThanOrEqualTo(Boolean value) {
            addCriterion("PriceChecked <=", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIn(List<Boolean> values) {
            addCriterion("PriceChecked in", values, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotIn(List<Boolean> values) {
            addCriterion("PriceChecked not in", values, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedBetween(Boolean value1, Boolean value2) {
            addCriterion("PriceChecked between", value1, value2, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("PriceChecked not between", value1, value2, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andHlcodeIsNull() {
            addCriterion("HLCode is null");
            return (Criteria) this;
        }

        public Criteria andHlcodeIsNotNull() {
            addCriterion("HLCode is not null");
            return (Criteria) this;
        }

        public Criteria andHlcodeEqualTo(String value) {
            addCriterion("HLCode =", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotEqualTo(String value) {
            addCriterion("HLCode <>", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeGreaterThan(String value) {
            addCriterion("HLCode >", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeGreaterThanOrEqualTo(String value) {
            addCriterion("HLCode >=", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLessThan(String value) {
            addCriterion("HLCode <", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLessThanOrEqualTo(String value) {
            addCriterion("HLCode <=", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLike(String value) {
            addCriterion("HLCode like", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotLike(String value) {
            addCriterion("HLCode not like", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeIn(List<String> values) {
            addCriterion("HLCode in", values, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotIn(List<String> values) {
            addCriterion("HLCode not in", values, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeBetween(String value1, String value2) {
            addCriterion("HLCode between", value1, value2, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotBetween(String value1, String value2) {
            addCriterion("HLCode not between", value1, value2, "hlcode");
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

        public Criteria andIsconfirmIsNull() {
            addCriterion("IsConfirm is null");
            return (Criteria) this;
        }

        public Criteria andIsconfirmIsNotNull() {
            addCriterion("IsConfirm is not null");
            return (Criteria) this;
        }

        public Criteria andIsconfirmEqualTo(Boolean value) {
            addCriterion("IsConfirm =", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotEqualTo(Boolean value) {
            addCriterion("IsConfirm <>", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmGreaterThan(Boolean value) {
            addCriterion("IsConfirm >", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsConfirm >=", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmLessThan(Boolean value) {
            addCriterion("IsConfirm <", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmLessThanOrEqualTo(Boolean value) {
            addCriterion("IsConfirm <=", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmIn(List<Boolean> values) {
            addCriterion("IsConfirm in", values, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotIn(List<Boolean> values) {
            addCriterion("IsConfirm not in", values, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmBetween(Boolean value1, Boolean value2) {
            addCriterion("IsConfirm between", value1, value2, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsConfirm not between", value1, value2, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("Inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("Inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("Inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("Inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("Inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("Inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("Inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("Inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("Inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("Inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("Inserttime not between", value1, value2, "inserttime");
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
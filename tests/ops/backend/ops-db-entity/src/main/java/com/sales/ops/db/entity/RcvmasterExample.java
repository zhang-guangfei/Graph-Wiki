package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RcvmasterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RcvmasterExample() {
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

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
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

        public Criteria andDlvEntireIsNull() {
            addCriterion("dlv_entire is null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNotNull() {
            addCriterion("dlv_entire is not null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireEqualTo(String value) {
            addCriterion("dlv_entire =", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotEqualTo(String value) {
            addCriterion("dlv_entire <>", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThan(String value) {
            addCriterion("dlv_entire >", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_entire >=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThan(String value) {
            addCriterion("dlv_entire <", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThanOrEqualTo(String value) {
            addCriterion("dlv_entire <=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLike(String value) {
            addCriterion("dlv_entire like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotLike(String value) {
            addCriterion("dlv_entire not like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIn(List<String> values) {
            addCriterion("dlv_entire in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotIn(List<String> values) {
            addCriterion("dlv_entire not in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireBetween(String value1, String value2) {
            addCriterion("dlv_entire between", value1, value2, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotBetween(String value1, String value2) {
            addCriterion("dlv_entire not between", value1, value2, "dlvEntire");
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

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_No is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_No is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_No =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_No <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_No >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_No >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_No <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_No <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_No like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_No not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_No in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_No not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_No between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_No not between", value1, value2, "deptNo");
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

        public Criteria andDeliveryDeptNoIsNull() {
            addCriterion("delivery_dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoIsNotNull() {
            addCriterion("delivery_dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoEqualTo(String value) {
            addCriterion("delivery_dept_no =", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotEqualTo(String value) {
            addCriterion("delivery_dept_no <>", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoGreaterThan(String value) {
            addCriterion("delivery_dept_no >", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_dept_no >=", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLessThan(String value) {
            addCriterion("delivery_dept_no <", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLessThanOrEqualTo(String value) {
            addCriterion("delivery_dept_no <=", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoLike(String value) {
            addCriterion("delivery_dept_no like", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotLike(String value) {
            addCriterion("delivery_dept_no not like", value, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoIn(List<String> values) {
            addCriterion("delivery_dept_no in", values, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotIn(List<String> values) {
            addCriterion("delivery_dept_no not in", values, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoBetween(String value1, String value2) {
            addCriterion("delivery_dept_no between", value1, value2, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeptNoNotBetween(String value1, String value2) {
            addCriterion("delivery_dept_no not between", value1, value2, "deliveryDeptNo");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIsNull() {
            addCriterion("trade_companyId is null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIsNotNull() {
            addCriterion("trade_companyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidEqualTo(String value) {
            addCriterion("trade_companyId =", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotEqualTo(String value) {
            addCriterion("trade_companyId <>", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThan(String value) {
            addCriterion("trade_companyId >", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("trade_companyId >=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThan(String value) {
            addCriterion("trade_companyId <", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThanOrEqualTo(String value) {
            addCriterion("trade_companyId <=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLike(String value) {
            addCriterion("trade_companyId like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotLike(String value) {
            addCriterion("trade_companyId not like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIn(List<String> values) {
            addCriterion("trade_companyId in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotIn(List<String> values) {
            addCriterion("trade_companyId not in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidBetween(String value1, String value2) {
            addCriterion("trade_companyId between", value1, value2, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotBetween(String value1, String value2) {
            addCriterion("trade_companyId not between", value1, value2, "tradeCompanyid");
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

        public Criteria andPurchaseUnitCodeIsNull() {
            addCriterion("purchase_unit_code is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeIsNotNull() {
            addCriterion("purchase_unit_code is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeEqualTo(String value) {
            addCriterion("purchase_unit_code =", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotEqualTo(String value) {
            addCriterion("purchase_unit_code <>", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeGreaterThan(String value) {
            addCriterion("purchase_unit_code >", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_unit_code >=", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLessThan(String value) {
            addCriterion("purchase_unit_code <", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLessThanOrEqualTo(String value) {
            addCriterion("purchase_unit_code <=", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeLike(String value) {
            addCriterion("purchase_unit_code like", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotLike(String value) {
            addCriterion("purchase_unit_code not like", value, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeIn(List<String> values) {
            addCriterion("purchase_unit_code in", values, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotIn(List<String> values) {
            addCriterion("purchase_unit_code not in", values, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeBetween(String value1, String value2) {
            addCriterion("purchase_unit_code between", value1, value2, "purchaseUnitCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseUnitCodeNotBetween(String value1, String value2) {
            addCriterion("purchase_unit_code not between", value1, value2, "purchaseUnitCode");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsBsQuerypriceNewmodelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsBsQuerypriceNewmodelExample() {
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

        public Criteria andOrgcountryidIsNull() {
            addCriterion("orgCountryId is null");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidIsNotNull() {
            addCriterion("orgCountryId is not null");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidEqualTo(String value) {
            addCriterion("orgCountryId =", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotEqualTo(String value) {
            addCriterion("orgCountryId <>", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidGreaterThan(String value) {
            addCriterion("orgCountryId >", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidGreaterThanOrEqualTo(String value) {
            addCriterion("orgCountryId >=", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLessThan(String value) {
            addCriterion("orgCountryId <", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLessThanOrEqualTo(String value) {
            addCriterion("orgCountryId <=", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLike(String value) {
            addCriterion("orgCountryId like", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotLike(String value) {
            addCriterion("orgCountryId not like", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidIn(List<String> values) {
            addCriterion("orgCountryId in", values, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotIn(List<String> values) {
            addCriterion("orgCountryId not in", values, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidBetween(String value1, String value2) {
            addCriterion("orgCountryId between", value1, value2, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotBetween(String value1, String value2) {
            addCriterion("orgCountryId not between", value1, value2, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIsNull() {
            addCriterion("stdDlvDay is null");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIsNotNull() {
            addCriterion("stdDlvDay is not null");
            return (Criteria) this;
        }

        public Criteria andStddlvdayEqualTo(Integer value) {
            addCriterion("stdDlvDay =", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotEqualTo(Integer value) {
            addCriterion("stdDlvDay <>", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayGreaterThan(Integer value) {
            addCriterion("stdDlvDay >", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDay >=", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayLessThan(Integer value) {
            addCriterion("stdDlvDay <", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayLessThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDay <=", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIn(List<Integer> values) {
            addCriterion("stdDlvDay in", values, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotIn(List<Integer> values) {
            addCriterion("stdDlvDay not in", values, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDay between", value1, value2, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDay not between", value1, value2, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIsNull() {
            addCriterion("stdDlvDateMaxNumber is null");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIsNotNull() {
            addCriterion("stdDlvDateMaxNumber is not null");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber =", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber <>", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberGreaterThan(Integer value) {
            addCriterion("stdDlvDateMaxNumber >", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber >=", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberLessThan(Integer value) {
            addCriterion("stdDlvDateMaxNumber <", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberLessThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber <=", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIn(List<Integer> values) {
            addCriterion("stdDlvDateMaxNumber in", values, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotIn(List<Integer> values) {
            addCriterion("stdDlvDateMaxNumber not in", values, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDateMaxNumber between", value1, value2, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDateMaxNumber not between", value1, value2, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStatuscodeIsNull() {
            addCriterion("statusCode is null");
            return (Criteria) this;
        }

        public Criteria andStatuscodeIsNotNull() {
            addCriterion("statusCode is not null");
            return (Criteria) this;
        }

        public Criteria andStatuscodeEqualTo(String value) {
            addCriterion("statusCode =", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeNotEqualTo(String value) {
            addCriterion("statusCode <>", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeGreaterThan(String value) {
            addCriterion("statusCode >", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeGreaterThanOrEqualTo(String value) {
            addCriterion("statusCode >=", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeLessThan(String value) {
            addCriterion("statusCode <", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeLessThanOrEqualTo(String value) {
            addCriterion("statusCode <=", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeLike(String value) {
            addCriterion("statusCode like", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeNotLike(String value) {
            addCriterion("statusCode not like", value, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeIn(List<String> values) {
            addCriterion("statusCode in", values, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeNotIn(List<String> values) {
            addCriterion("statusCode not in", values, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeBetween(String value1, String value2) {
            addCriterion("statusCode between", value1, value2, "statuscode");
            return (Criteria) this;
        }

        public Criteria andStatuscodeNotBetween(String value1, String value2) {
            addCriterion("statusCode not between", value1, value2, "statuscode");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("insertTime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("insertTime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("insertTime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("insertTime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("insertTime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("insertTime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("insertTime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("insertTime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("insertTime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("insertTime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("insertTime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("insertTime not between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInsertuserIsNull() {
            addCriterion("insertUser is null");
            return (Criteria) this;
        }

        public Criteria andInsertuserIsNotNull() {
            addCriterion("insertUser is not null");
            return (Criteria) this;
        }

        public Criteria andInsertuserEqualTo(String value) {
            addCriterion("insertUser =", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserNotEqualTo(String value) {
            addCriterion("insertUser <>", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserGreaterThan(String value) {
            addCriterion("insertUser >", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserGreaterThanOrEqualTo(String value) {
            addCriterion("insertUser >=", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserLessThan(String value) {
            addCriterion("insertUser <", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserLessThanOrEqualTo(String value) {
            addCriterion("insertUser <=", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserLike(String value) {
            addCriterion("insertUser like", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserNotLike(String value) {
            addCriterion("insertUser not like", value, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserIn(List<String> values) {
            addCriterion("insertUser in", values, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserNotIn(List<String> values) {
            addCriterion("insertUser not in", values, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserBetween(String value1, String value2) {
            addCriterion("insertUser between", value1, value2, "insertuser");
            return (Criteria) this;
        }

        public Criteria andInsertuserNotBetween(String value1, String value2) {
            addCriterion("insertUser not between", value1, value2, "insertuser");
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

        public Criteria andSourcetypeIsNull() {
            addCriterion("sourcetype is null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeIsNotNull() {
            addCriterion("sourcetype is not null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeEqualTo(String value) {
            addCriterion("sourcetype =", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotEqualTo(String value) {
            addCriterion("sourcetype <>", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeGreaterThan(String value) {
            addCriterion("sourcetype >", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeGreaterThanOrEqualTo(String value) {
            addCriterion("sourcetype >=", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLessThan(String value) {
            addCriterion("sourcetype <", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLessThanOrEqualTo(String value) {
            addCriterion("sourcetype <=", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLike(String value) {
            addCriterion("sourcetype like", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotLike(String value) {
            addCriterion("sourcetype not like", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeIn(List<String> values) {
            addCriterion("sourcetype in", values, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotIn(List<String> values) {
            addCriterion("sourcetype not in", values, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeBetween(String value1, String value2) {
            addCriterion("sourcetype between", value1, value2, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotBetween(String value1, String value2) {
            addCriterion("sourcetype not between", value1, value2, "sourcetype");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersalesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrdersalesExample() {
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
            addCriterion("rorderno is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("rorderno is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("rorderno =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("rorderno <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("rorderno >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("rorderno >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("rorderno <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("rorderno <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("rorderno like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("rorderno not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("rorderno in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("rorderno not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("rorderno between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("rorderno not between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNull() {
            addCriterion("ROrderItem is null");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNotNull() {
            addCriterion("ROrderItem is not null");
            return (Criteria) this;
        }

        public Criteria andRorderitemEqualTo(Short value) {
            addCriterion("ROrderItem =", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotEqualTo(Short value) {
            addCriterion("ROrderItem <>", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThan(Short value) {
            addCriterion("ROrderItem >", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThanOrEqualTo(Short value) {
            addCriterion("ROrderItem >=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThan(Short value) {
            addCriterion("ROrderItem <", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThanOrEqualTo(Short value) {
            addCriterion("ROrderItem <=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemIn(List<Short> values) {
            addCriterion("ROrderItem in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotIn(List<Short> values) {
            addCriterion("ROrderItem not in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemBetween(Short value1, Short value2) {
            addCriterion("ROrderItem between", value1, value2, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotBetween(Short value1, Short value2) {
            addCriterion("ROrderItem not between", value1, value2, "rorderitem");
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

        public Criteria andWorkdayIsNull() {
            addCriterion("WorkDay is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNotNull() {
            addCriterion("WorkDay is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayEqualTo(Date value) {
            addCriterion("WorkDay =", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotEqualTo(Date value) {
            addCriterion("WorkDay <>", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThan(Date value) {
            addCriterion("WorkDay >", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThanOrEqualTo(Date value) {
            addCriterion("WorkDay >=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThan(Date value) {
            addCriterion("WorkDay <", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThanOrEqualTo(Date value) {
            addCriterion("WorkDay <=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayIn(List<Date> values) {
            addCriterion("WorkDay in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotIn(List<Date> values) {
            addCriterion("WorkDay not in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayBetween(Date value1, Date value2) {
            addCriterion("WorkDay between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotBetween(Date value1, Date value2) {
            addCriterion("WorkDay not between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNull() {
            addCriterion("Sendout is null");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNotNull() {
            addCriterion("Sendout is not null");
            return (Criteria) this;
        }

        public Criteria andSendoutEqualTo(String value) {
            addCriterion("Sendout =", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotEqualTo(String value) {
            addCriterion("Sendout <>", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThan(String value) {
            addCriterion("Sendout >", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThanOrEqualTo(String value) {
            addCriterion("Sendout >=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThan(String value) {
            addCriterion("Sendout <", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThanOrEqualTo(String value) {
            addCriterion("Sendout <=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLike(String value) {
            addCriterion("Sendout like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotLike(String value) {
            addCriterion("Sendout not like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutIn(List<String> values) {
            addCriterion("Sendout in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotIn(List<String> values) {
            addCriterion("Sendout not in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutBetween(String value1, String value2) {
            addCriterion("Sendout between", value1, value2, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotBetween(String value1, String value2) {
            addCriterion("Sendout not between", value1, value2, "sendout");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("Status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("Status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNull() {
            addCriterion("SendDay is null");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNotNull() {
            addCriterion("SendDay is not null");
            return (Criteria) this;
        }

        public Criteria andSenddayEqualTo(Date value) {
            addCriterion("SendDay =", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotEqualTo(Date value) {
            addCriterion("SendDay <>", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThan(Date value) {
            addCriterion("SendDay >", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThanOrEqualTo(Date value) {
            addCriterion("SendDay >=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThan(Date value) {
            addCriterion("SendDay <", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThanOrEqualTo(Date value) {
            addCriterion("SendDay <=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayIn(List<Date> values) {
            addCriterion("SendDay in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotIn(List<Date> values) {
            addCriterion("SendDay not in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayBetween(Date value1, Date value2) {
            addCriterion("SendDay between", value1, value2, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotBetween(Date value1, Date value2) {
            addCriterion("SendDay not between", value1, value2, "sendday");
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

        public Criteria andPriceIsNull() {
            addCriterion("Price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("Price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("Price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("Price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("Price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("Price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("Price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("Price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNull() {
            addCriterion("Price_EndUser is null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNotNull() {
            addCriterion("Price_EndUser is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser =", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <>", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThan(BigDecimal value) {
            addCriterion("Price_EndUser >", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser >=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThan(BigDecimal value) {
            addCriterion("Price_EndUser <", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser not in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser not between", value1, value2, "priceEnduser");
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

        public Criteria andRcvclassifyIsNull() {
            addCriterion("RcvClassify is null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIsNotNull() {
            addCriterion("RcvClassify is not null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyEqualTo(String value) {
            addCriterion("RcvClassify =", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotEqualTo(String value) {
            addCriterion("RcvClassify <>", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThan(String value) {
            addCriterion("RcvClassify >", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThanOrEqualTo(String value) {
            addCriterion("RcvClassify >=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThan(String value) {
            addCriterion("RcvClassify <", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThanOrEqualTo(String value) {
            addCriterion("RcvClassify <=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLike(String value) {
            addCriterion("RcvClassify like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotLike(String value) {
            addCriterion("RcvClassify not like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIn(List<String> values) {
            addCriterion("RcvClassify in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotIn(List<String> values) {
            addCriterion("RcvClassify not in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyBetween(String value1, String value2) {
            addCriterion("RcvClassify between", value1, value2, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotBetween(String value1, String value2) {
            addCriterion("RcvClassify not between", value1, value2, "rcvclassify");
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

        public Criteria andStockcodeIsNull() {
            addCriterion("StockCode is null");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNotNull() {
            addCriterion("StockCode is not null");
            return (Criteria) this;
        }

        public Criteria andStockcodeEqualTo(String value) {
            addCriterion("StockCode =", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotEqualTo(String value) {
            addCriterion("StockCode <>", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThan(String value) {
            addCriterion("StockCode >", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThanOrEqualTo(String value) {
            addCriterion("StockCode >=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThan(String value) {
            addCriterion("StockCode <", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThanOrEqualTo(String value) {
            addCriterion("StockCode <=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLike(String value) {
            addCriterion("StockCode like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotLike(String value) {
            addCriterion("StockCode not like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeIn(List<String> values) {
            addCriterion("StockCode in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotIn(List<String> values) {
            addCriterion("StockCode not in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeBetween(String value1, String value2) {
            addCriterion("StockCode between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotBetween(String value1, String value2) {
            addCriterion("StockCode not between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andCproductnoIsNull() {
            addCriterion("CProductNo is null");
            return (Criteria) this;
        }

        public Criteria andCproductnoIsNotNull() {
            addCriterion("CProductNo is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnoEqualTo(String value) {
            addCriterion("CProductNo =", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotEqualTo(String value) {
            addCriterion("CProductNo <>", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThan(String value) {
            addCriterion("CProductNo >", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThanOrEqualTo(String value) {
            addCriterion("CProductNo >=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThan(String value) {
            addCriterion("CProductNo <", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThanOrEqualTo(String value) {
            addCriterion("CProductNo <=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLike(String value) {
            addCriterion("CProductNo like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotLike(String value) {
            addCriterion("CProductNo not like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoIn(List<String> values) {
            addCriterion("CProductNo in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotIn(List<String> values) {
            addCriterion("CProductNo not in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoBetween(String value1, String value2) {
            addCriterion("CProductNo between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotBetween(String value1, String value2) {
            addCriterion("CProductNo not between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNull() {
            addCriterion("OrdTransType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNotNull() {
            addCriterion("OrdTransType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeEqualTo(String value) {
            addCriterion("OrdTransType =", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotEqualTo(String value) {
            addCriterion("OrdTransType <>", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThan(String value) {
            addCriterion("OrdTransType >", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdTransType >=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThan(String value) {
            addCriterion("OrdTransType <", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThanOrEqualTo(String value) {
            addCriterion("OrdTransType <=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLike(String value) {
            addCriterion("OrdTransType like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotLike(String value) {
            addCriterion("OrdTransType not like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIn(List<String> values) {
            addCriterion("OrdTransType in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotIn(List<String> values) {
            addCriterion("OrdTransType not in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeBetween(String value1, String value2) {
            addCriterion("OrdTransType between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotBetween(String value1, String value2) {
            addCriterion("OrdTransType not between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIsNull() {
            addCriterion("SpcPrice is null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIsNotNull() {
            addCriterion("SpcPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceEqualTo(String value) {
            addCriterion("SpcPrice =", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotEqualTo(String value) {
            addCriterion("SpcPrice <>", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThan(String value) {
            addCriterion("SpcPrice >", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThanOrEqualTo(String value) {
            addCriterion("SpcPrice >=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThan(String value) {
            addCriterion("SpcPrice <", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThanOrEqualTo(String value) {
            addCriterion("SpcPrice <=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLike(String value) {
            addCriterion("SpcPrice like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotLike(String value) {
            addCriterion("SpcPrice not like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIn(List<String> values) {
            addCriterion("SpcPrice in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotIn(List<String> values) {
            addCriterion("SpcPrice not in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceBetween(String value1, String value2) {
            addCriterion("SpcPrice between", value1, value2, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotBetween(String value1, String value2) {
            addCriterion("SpcPrice not between", value1, value2, "spcprice");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNull() {
            addCriterion("NoPrice is null");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNotNull() {
            addCriterion("NoPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNopriceEqualTo(String value) {
            addCriterion("NoPrice =", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotEqualTo(String value) {
            addCriterion("NoPrice <>", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThan(String value) {
            addCriterion("NoPrice >", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThanOrEqualTo(String value) {
            addCriterion("NoPrice >=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThan(String value) {
            addCriterion("NoPrice <", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThanOrEqualTo(String value) {
            addCriterion("NoPrice <=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLike(String value) {
            addCriterion("NoPrice like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotLike(String value) {
            addCriterion("NoPrice not like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceIn(List<String> values) {
            addCriterion("NoPrice in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotIn(List<String> values) {
            addCriterion("NoPrice not in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceBetween(String value1, String value2) {
            addCriterion("NoPrice between", value1, value2, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotBetween(String value1, String value2) {
            addCriterion("NoPrice not between", value1, value2, "noprice");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("Account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("Account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("Account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("Account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("Account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("Account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("Account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("Account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account not between", value1, value2, "account");
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

        public Criteria andRecnoIsNull() {
            addCriterion("RecNo is null");
            return (Criteria) this;
        }

        public Criteria andRecnoIsNotNull() {
            addCriterion("RecNo is not null");
            return (Criteria) this;
        }

        public Criteria andRecnoEqualTo(String value) {
            addCriterion("RecNo =", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotEqualTo(String value) {
            addCriterion("RecNo <>", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThan(String value) {
            addCriterion("RecNo >", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThanOrEqualTo(String value) {
            addCriterion("RecNo >=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThan(String value) {
            addCriterion("RecNo <", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThanOrEqualTo(String value) {
            addCriterion("RecNo <=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLike(String value) {
            addCriterion("RecNo like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotLike(String value) {
            addCriterion("RecNo not like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoIn(List<String> values) {
            addCriterion("RecNo in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotIn(List<String> values) {
            addCriterion("RecNo not in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoBetween(String value1, String value2) {
            addCriterion("RecNo between", value1, value2, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotBetween(String value1, String value2) {
            addCriterion("RecNo not between", value1, value2, "recno");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNull() {
            addCriterion("DetailMark is null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNotNull() {
            addCriterion("DetailMark is not null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkEqualTo(String value) {
            addCriterion("DetailMark =", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotEqualTo(String value) {
            addCriterion("DetailMark <>", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThan(String value) {
            addCriterion("DetailMark >", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThanOrEqualTo(String value) {
            addCriterion("DetailMark >=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThan(String value) {
            addCriterion("DetailMark <", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThanOrEqualTo(String value) {
            addCriterion("DetailMark <=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLike(String value) {
            addCriterion("DetailMark like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotLike(String value) {
            addCriterion("DetailMark not like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIn(List<String> values) {
            addCriterion("DetailMark in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotIn(List<String> values) {
            addCriterion("DetailMark not in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkBetween(String value1, String value2) {
            addCriterion("DetailMark between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotBetween(String value1, String value2) {
            addCriterion("DetailMark not between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNull() {
            addCriterion("OptDate is null");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNotNull() {
            addCriterion("OptDate is not null");
            return (Criteria) this;
        }

        public Criteria andOptdateEqualTo(Date value) {
            addCriterion("OptDate =", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotEqualTo(Date value) {
            addCriterion("OptDate <>", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThan(Date value) {
            addCriterion("OptDate >", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OptDate >=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThan(Date value) {
            addCriterion("OptDate <", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThanOrEqualTo(Date value) {
            addCriterion("OptDate <=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateIn(List<Date> values) {
            addCriterion("OptDate in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotIn(List<Date> values) {
            addCriterion("OptDate not in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateBetween(Date value1, Date value2) {
            addCriterion("OptDate between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotBetween(Date value1, Date value2) {
            addCriterion("OptDate not between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIsNull() {
            addCriterion("EmpSale is null");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIsNotNull() {
            addCriterion("EmpSale is not null");
            return (Criteria) this;
        }

        public Criteria andEmpsaleEqualTo(String value) {
            addCriterion("EmpSale =", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotEqualTo(String value) {
            addCriterion("EmpSale <>", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleGreaterThan(String value) {
            addCriterion("EmpSale >", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleGreaterThanOrEqualTo(String value) {
            addCriterion("EmpSale >=", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLessThan(String value) {
            addCriterion("EmpSale <", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLessThanOrEqualTo(String value) {
            addCriterion("EmpSale <=", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLike(String value) {
            addCriterion("EmpSale like", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotLike(String value) {
            addCriterion("EmpSale not like", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIn(List<String> values) {
            addCriterion("EmpSale in", values, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotIn(List<String> values) {
            addCriterion("EmpSale not in", values, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleBetween(String value1, String value2) {
            addCriterion("EmpSale between", value1, value2, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotBetween(String value1, String value2) {
            addCriterion("EmpSale not between", value1, value2, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIsNull() {
            addCriterion("EmpOffice is null");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIsNotNull() {
            addCriterion("EmpOffice is not null");
            return (Criteria) this;
        }

        public Criteria andEmpofficeEqualTo(String value) {
            addCriterion("EmpOffice =", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotEqualTo(String value) {
            addCriterion("EmpOffice <>", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeGreaterThan(String value) {
            addCriterion("EmpOffice >", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeGreaterThanOrEqualTo(String value) {
            addCriterion("EmpOffice >=", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLessThan(String value) {
            addCriterion("EmpOffice <", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLessThanOrEqualTo(String value) {
            addCriterion("EmpOffice <=", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLike(String value) {
            addCriterion("EmpOffice like", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotLike(String value) {
            addCriterion("EmpOffice not like", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIn(List<String> values) {
            addCriterion("EmpOffice in", values, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotIn(List<String> values) {
            addCriterion("EmpOffice not in", values, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeBetween(String value1, String value2) {
            addCriterion("EmpOffice between", value1, value2, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotBetween(String value1, String value2) {
            addCriterion("EmpOffice not between", value1, value2, "empoffice");
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

        public Criteria andPurchasenoIsNull() {
            addCriterion("PurchaseNo is null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNotNull() {
            addCriterion("PurchaseNo is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoEqualTo(String value) {
            addCriterion("PurchaseNo =", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotEqualTo(String value) {
            addCriterion("PurchaseNo <>", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThan(String value) {
            addCriterion("PurchaseNo >", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseNo >=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThan(String value) {
            addCriterion("PurchaseNo <", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseNo <=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLike(String value) {
            addCriterion("PurchaseNo like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotLike(String value) {
            addCriterion("PurchaseNo not like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIn(List<String> values) {
            addCriterion("PurchaseNo in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotIn(List<String> values) {
            addCriterion("PurchaseNo not in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoBetween(String value1, String value2) {
            addCriterion("PurchaseNo between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotBetween(String value1, String value2) {
            addCriterion("PurchaseNo not between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNull() {
            addCriterion("Shikomi is null");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNotNull() {
            addCriterion("Shikomi is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiEqualTo(String value) {
            addCriterion("Shikomi =", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotEqualTo(String value) {
            addCriterion("Shikomi <>", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThan(String value) {
            addCriterion("Shikomi >", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThanOrEqualTo(String value) {
            addCriterion("Shikomi >=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThan(String value) {
            addCriterion("Shikomi <", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThanOrEqualTo(String value) {
            addCriterion("Shikomi <=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLike(String value) {
            addCriterion("Shikomi like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotLike(String value) {
            addCriterion("Shikomi not like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiIn(List<String> values) {
            addCriterion("Shikomi in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotIn(List<String> values) {
            addCriterion("Shikomi not in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiBetween(String value1, String value2) {
            addCriterion("Shikomi between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotBetween(String value1, String value2) {
            addCriterion("Shikomi not between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNull() {
            addCriterion("cttFlag is null");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNotNull() {
            addCriterion("cttFlag is not null");
            return (Criteria) this;
        }

        public Criteria andCttflagEqualTo(String value) {
            addCriterion("cttFlag =", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotEqualTo(String value) {
            addCriterion("cttFlag <>", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThan(String value) {
            addCriterion("cttFlag >", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThanOrEqualTo(String value) {
            addCriterion("cttFlag >=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThan(String value) {
            addCriterion("cttFlag <", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThanOrEqualTo(String value) {
            addCriterion("cttFlag <=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLike(String value) {
            addCriterion("cttFlag like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotLike(String value) {
            addCriterion("cttFlag not like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagIn(List<String> values) {
            addCriterion("cttFlag in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotIn(List<String> values) {
            addCriterion("cttFlag not in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagBetween(String value1, String value2) {
            addCriterion("cttFlag between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotBetween(String value1, String value2) {
            addCriterion("cttFlag not between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIsNull() {
            addCriterion("WarehouseSendDate is null");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIsNotNull() {
            addCriterion("WarehouseSendDate is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateEqualTo(Date value) {
            addCriterion("WarehouseSendDate =", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotEqualTo(Date value) {
            addCriterion("WarehouseSendDate <>", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateGreaterThan(Date value) {
            addCriterion("WarehouseSendDate >", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateGreaterThanOrEqualTo(Date value) {
            addCriterion("WarehouseSendDate >=", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateLessThan(Date value) {
            addCriterion("WarehouseSendDate <", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateLessThanOrEqualTo(Date value) {
            addCriterion("WarehouseSendDate <=", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIn(List<Date> values) {
            addCriterion("WarehouseSendDate in", values, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotIn(List<Date> values) {
            addCriterion("WarehouseSendDate not in", values, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateBetween(Date value1, Date value2) {
            addCriterion("WarehouseSendDate between", value1, value2, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotBetween(Date value1, Date value2) {
            addCriterion("WarehouseSendDate not between", value1, value2, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNull() {
            addCriterion("CProductName is null");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNotNull() {
            addCriterion("CProductName is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnameEqualTo(String value) {
            addCriterion("CProductName =", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotEqualTo(String value) {
            addCriterion("CProductName <>", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThan(String value) {
            addCriterion("CProductName >", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThanOrEqualTo(String value) {
            addCriterion("CProductName >=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThan(String value) {
            addCriterion("CProductName <", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThanOrEqualTo(String value) {
            addCriterion("CProductName <=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLike(String value) {
            addCriterion("CProductName like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotLike(String value) {
            addCriterion("CProductName not like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameIn(List<String> values) {
            addCriterion("CProductName in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotIn(List<String> values) {
            addCriterion("CProductName not in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameBetween(String value1, String value2) {
            addCriterion("CProductName between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotBetween(String value1, String value2) {
            addCriterion("CProductName not between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNull() {
            addCriterion("PreSaleOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNotNull() {
            addCriterion("PreSaleOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoEqualTo(String value) {
            addCriterion("PreSaleOrderNo =", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotEqualTo(String value) {
            addCriterion("PreSaleOrderNo <>", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThan(String value) {
            addCriterion("PreSaleOrderNo >", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo >=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThan(String value) {
            addCriterion("PreSaleOrderNo <", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo <=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLike(String value) {
            addCriterion("PreSaleOrderNo like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotLike(String value) {
            addCriterion("PreSaleOrderNo not like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIn(List<String> values) {
            addCriterion("PreSaleOrderNo in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotIn(List<String> values) {
            addCriterion("PreSaleOrderNo not in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo not between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNull() {
            addCriterion("Opponent is null");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNotNull() {
            addCriterion("Opponent is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentEqualTo(String value) {
            addCriterion("Opponent =", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotEqualTo(String value) {
            addCriterion("Opponent <>", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThan(String value) {
            addCriterion("Opponent >", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThanOrEqualTo(String value) {
            addCriterion("Opponent >=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThan(String value) {
            addCriterion("Opponent <", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThanOrEqualTo(String value) {
            addCriterion("Opponent <=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLike(String value) {
            addCriterion("Opponent like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotLike(String value) {
            addCriterion("Opponent not like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentIn(List<String> values) {
            addCriterion("Opponent in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotIn(List<String> values) {
            addCriterion("Opponent not in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentBetween(String value1, String value2) {
            addCriterion("Opponent between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotBetween(String value1, String value2) {
            addCriterion("Opponent not between", value1, value2, "opponent");
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

        public Criteria andTypecodeEqualTo(Short value) {
            addCriterion("TypeCode =", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotEqualTo(Short value) {
            addCriterion("TypeCode <>", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThan(Short value) {
            addCriterion("TypeCode >", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThanOrEqualTo(Short value) {
            addCriterion("TypeCode >=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThan(Short value) {
            addCriterion("TypeCode <", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThanOrEqualTo(Short value) {
            addCriterion("TypeCode <=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeIn(List<Short> values) {
            addCriterion("TypeCode in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotIn(List<Short> values) {
            addCriterion("TypeCode not in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeBetween(Short value1, Short value2) {
            addCriterion("TypeCode between", value1, value2, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotBetween(Short value1, Short value2) {
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

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
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

        public Criteria andAddressNoIsNull() {
            addCriterion("address_no is null");
            return (Criteria) this;
        }

        public Criteria andAddressNoIsNotNull() {
            addCriterion("address_no is not null");
            return (Criteria) this;
        }

        public Criteria andAddressNoEqualTo(String value) {
            addCriterion("address_no =", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotEqualTo(String value) {
            addCriterion("address_no <>", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThan(String value) {
            addCriterion("address_no >", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoGreaterThanOrEqualTo(String value) {
            addCriterion("address_no >=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThan(String value) {
            addCriterion("address_no <", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLessThanOrEqualTo(String value) {
            addCriterion("address_no <=", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoLike(String value) {
            addCriterion("address_no like", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotLike(String value) {
            addCriterion("address_no not like", value, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoIn(List<String> values) {
            addCriterion("address_no in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotIn(List<String> values) {
            addCriterion("address_no not in", values, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoBetween(String value1, String value2) {
            addCriterion("address_no between", value1, value2, "addressNo");
            return (Criteria) this;
        }

        public Criteria andAddressNoNotBetween(String value1, String value2) {
            addCriterion("address_no not between", value1, value2, "addressNo");
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

        public Criteria andCdlvdateIsNull() {
            addCriterion("CDlvDate is null");
            return (Criteria) this;
        }

        public Criteria andCdlvdateIsNotNull() {
            addCriterion("CDlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andCdlvdateEqualTo(Date value) {
            addCriterion("CDlvDate =", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateNotEqualTo(Date value) {
            addCriterion("CDlvDate <>", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateGreaterThan(Date value) {
            addCriterion("CDlvDate >", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("CDlvDate >=", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateLessThan(Date value) {
            addCriterion("CDlvDate <", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateLessThanOrEqualTo(Date value) {
            addCriterion("CDlvDate <=", value, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateIn(List<Date> values) {
            addCriterion("CDlvDate in", values, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateNotIn(List<Date> values) {
            addCriterion("CDlvDate not in", values, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateBetween(Date value1, Date value2) {
            addCriterion("CDlvDate between", value1, value2, "cdlvdate");
            return (Criteria) this;
        }

        public Criteria andCdlvdateNotBetween(Date value1, Date value2) {
            addCriterion("CDlvDate not between", value1, value2, "cdlvdate");
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
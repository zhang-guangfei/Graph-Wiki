package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPayTermExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPayTermExample() {
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

        public Criteria andCredittermIsNull() {
            addCriterion("CreditTerm is null");
            return (Criteria) this;
        }

        public Criteria andCredittermIsNotNull() {
            addCriterion("CreditTerm is not null");
            return (Criteria) this;
        }

        public Criteria andCredittermEqualTo(Integer value) {
            addCriterion("CreditTerm =", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermNotEqualTo(Integer value) {
            addCriterion("CreditTerm <>", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermGreaterThan(Integer value) {
            addCriterion("CreditTerm >", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermGreaterThanOrEqualTo(Integer value) {
            addCriterion("CreditTerm >=", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermLessThan(Integer value) {
            addCriterion("CreditTerm <", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermLessThanOrEqualTo(Integer value) {
            addCriterion("CreditTerm <=", value, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermIn(List<Integer> values) {
            addCriterion("CreditTerm in", values, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermNotIn(List<Integer> values) {
            addCriterion("CreditTerm not in", values, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermBetween(Integer value1, Integer value2) {
            addCriterion("CreditTerm between", value1, value2, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCredittermNotBetween(Integer value1, Integer value2) {
            addCriterion("CreditTerm not between", value1, value2, "creditterm");
            return (Criteria) this;
        }

        public Criteria andCreditgradeIsNull() {
            addCriterion("CreditGrade is null");
            return (Criteria) this;
        }

        public Criteria andCreditgradeIsNotNull() {
            addCriterion("CreditGrade is not null");
            return (Criteria) this;
        }

        public Criteria andCreditgradeEqualTo(String value) {
            addCriterion("CreditGrade =", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeNotEqualTo(String value) {
            addCriterion("CreditGrade <>", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeGreaterThan(String value) {
            addCriterion("CreditGrade >", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeGreaterThanOrEqualTo(String value) {
            addCriterion("CreditGrade >=", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeLessThan(String value) {
            addCriterion("CreditGrade <", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeLessThanOrEqualTo(String value) {
            addCriterion("CreditGrade <=", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeLike(String value) {
            addCriterion("CreditGrade like", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeNotLike(String value) {
            addCriterion("CreditGrade not like", value, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeIn(List<String> values) {
            addCriterion("CreditGrade in", values, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeNotIn(List<String> values) {
            addCriterion("CreditGrade not in", values, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeBetween(String value1, String value2) {
            addCriterion("CreditGrade between", value1, value2, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andCreditgradeNotBetween(String value1, String value2) {
            addCriterion("CreditGrade not between", value1, value2, "creditgrade");
            return (Criteria) this;
        }

        public Criteria andPaytermIsNull() {
            addCriterion("PayTerm is null");
            return (Criteria) this;
        }

        public Criteria andPaytermIsNotNull() {
            addCriterion("PayTerm is not null");
            return (Criteria) this;
        }

        public Criteria andPaytermEqualTo(String value) {
            addCriterion("PayTerm =", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermNotEqualTo(String value) {
            addCriterion("PayTerm <>", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermGreaterThan(String value) {
            addCriterion("PayTerm >", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermGreaterThanOrEqualTo(String value) {
            addCriterion("PayTerm >=", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermLessThan(String value) {
            addCriterion("PayTerm <", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermLessThanOrEqualTo(String value) {
            addCriterion("PayTerm <=", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermLike(String value) {
            addCriterion("PayTerm like", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermNotLike(String value) {
            addCriterion("PayTerm not like", value, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermIn(List<String> values) {
            addCriterion("PayTerm in", values, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermNotIn(List<String> values) {
            addCriterion("PayTerm not in", values, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermBetween(String value1, String value2) {
            addCriterion("PayTerm between", value1, value2, "payterm");
            return (Criteria) this;
        }

        public Criteria andPaytermNotBetween(String value1, String value2) {
            addCriterion("PayTerm not between", value1, value2, "payterm");
            return (Criteria) this;
        }

        public Criteria andCreditlimitIsNull() {
            addCriterion("CreditLimit is null");
            return (Criteria) this;
        }

        public Criteria andCreditlimitIsNotNull() {
            addCriterion("CreditLimit is not null");
            return (Criteria) this;
        }

        public Criteria andCreditlimitEqualTo(BigDecimal value) {
            addCriterion("CreditLimit =", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitNotEqualTo(BigDecimal value) {
            addCriterion("CreditLimit <>", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitGreaterThan(BigDecimal value) {
            addCriterion("CreditLimit >", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CreditLimit >=", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitLessThan(BigDecimal value) {
            addCriterion("CreditLimit <", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CreditLimit <=", value, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitIn(List<BigDecimal> values) {
            addCriterion("CreditLimit in", values, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitNotIn(List<BigDecimal> values) {
            addCriterion("CreditLimit not in", values, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CreditLimit between", value1, value2, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andCreditlimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CreditLimit not between", value1, value2, "creditlimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitIsNull() {
            addCriterion("GraceLimit is null");
            return (Criteria) this;
        }

        public Criteria andGracelimitIsNotNull() {
            addCriterion("GraceLimit is not null");
            return (Criteria) this;
        }

        public Criteria andGracelimitEqualTo(BigDecimal value) {
            addCriterion("GraceLimit =", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitNotEqualTo(BigDecimal value) {
            addCriterion("GraceLimit <>", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitGreaterThan(BigDecimal value) {
            addCriterion("GraceLimit >", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GraceLimit >=", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitLessThan(BigDecimal value) {
            addCriterion("GraceLimit <", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GraceLimit <=", value, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitIn(List<BigDecimal> values) {
            addCriterion("GraceLimit in", values, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitNotIn(List<BigDecimal> values) {
            addCriterion("GraceLimit not in", values, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GraceLimit between", value1, value2, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andGracelimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GraceLimit not between", value1, value2, "gracelimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitIsNull() {
            addCriterion("AdjustLimit is null");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitIsNotNull() {
            addCriterion("AdjustLimit is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitEqualTo(BigDecimal value) {
            addCriterion("AdjustLimit =", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitNotEqualTo(BigDecimal value) {
            addCriterion("AdjustLimit <>", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitGreaterThan(BigDecimal value) {
            addCriterion("AdjustLimit >", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AdjustLimit >=", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitLessThan(BigDecimal value) {
            addCriterion("AdjustLimit <", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AdjustLimit <=", value, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitIn(List<BigDecimal> values) {
            addCriterion("AdjustLimit in", values, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitNotIn(List<BigDecimal> values) {
            addCriterion("AdjustLimit not in", values, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AdjustLimit between", value1, value2, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andAdjustlimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AdjustLimit not between", value1, value2, "adjustlimit");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNull() {
            addCriterion("CreateUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("CreateUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(String value) {
            addCriterion("CreateUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(String value) {
            addCriterion("CreateUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(String value) {
            addCriterion("CreateUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(String value) {
            addCriterion("CreateUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(String value) {
            addCriterion("CreateUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(String value) {
            addCriterion("CreateUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLike(String value) {
            addCriterion("CreateUser like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotLike(String value) {
            addCriterion("CreateUser not like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<String> values) {
            addCriterion("CreateUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<String> values) {
            addCriterion("CreateUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(String value1, String value2) {
            addCriterion("CreateUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(String value1, String value2) {
            addCriterion("CreateUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNull() {
            addCriterion("UpdateUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNotNull() {
            addCriterion("UpdateUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserEqualTo(String value) {
            addCriterion("UpdateUser =", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotEqualTo(String value) {
            addCriterion("UpdateUser <>", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThan(String value) {
            addCriterion("UpdateUser >", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThanOrEqualTo(String value) {
            addCriterion("UpdateUser >=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThan(String value) {
            addCriterion("UpdateUser <", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThanOrEqualTo(String value) {
            addCriterion("UpdateUser <=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLike(String value) {
            addCriterion("UpdateUser like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotLike(String value) {
            addCriterion("UpdateUser not like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIn(List<String> values) {
            addCriterion("UpdateUser in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotIn(List<String> values) {
            addCriterion("UpdateUser not in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserBetween(String value1, String value2) {
            addCriterion("UpdateUser between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotBetween(String value1, String value2) {
            addCriterion("UpdateUser not between", value1, value2, "updateuser");
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
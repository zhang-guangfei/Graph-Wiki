package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaxcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MaxcodeExample() {
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

        public Criteria andOpttypeIsNull() {
            addCriterion("OptType is null");
            return (Criteria) this;
        }

        public Criteria andOpttypeIsNotNull() {
            addCriterion("OptType is not null");
            return (Criteria) this;
        }

        public Criteria andOpttypeEqualTo(String value) {
            addCriterion("OptType =", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeNotEqualTo(String value) {
            addCriterion("OptType <>", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeGreaterThan(String value) {
            addCriterion("OptType >", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeGreaterThanOrEqualTo(String value) {
            addCriterion("OptType >=", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeLessThan(String value) {
            addCriterion("OptType <", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeLessThanOrEqualTo(String value) {
            addCriterion("OptType <=", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeLike(String value) {
            addCriterion("OptType like", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeNotLike(String value) {
            addCriterion("OptType not like", value, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeIn(List<String> values) {
            addCriterion("OptType in", values, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeNotIn(List<String> values) {
            addCriterion("OptType not in", values, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeBetween(String value1, String value2) {
            addCriterion("OptType between", value1, value2, "opttype");
            return (Criteria) this;
        }

        public Criteria andOpttypeNotBetween(String value1, String value2) {
            addCriterion("OptType not between", value1, value2, "opttype");
            return (Criteria) this;
        }

        public Criteria andPickcodeIsNull() {
            addCriterion("PickCode is null");
            return (Criteria) this;
        }

        public Criteria andPickcodeIsNotNull() {
            addCriterion("PickCode is not null");
            return (Criteria) this;
        }

        public Criteria andPickcodeEqualTo(String value) {
            addCriterion("PickCode =", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeNotEqualTo(String value) {
            addCriterion("PickCode <>", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeGreaterThan(String value) {
            addCriterion("PickCode >", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PickCode >=", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeLessThan(String value) {
            addCriterion("PickCode <", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeLessThanOrEqualTo(String value) {
            addCriterion("PickCode <=", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeLike(String value) {
            addCriterion("PickCode like", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeNotLike(String value) {
            addCriterion("PickCode not like", value, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeIn(List<String> values) {
            addCriterion("PickCode in", values, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeNotIn(List<String> values) {
            addCriterion("PickCode not in", values, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeBetween(String value1, String value2) {
            addCriterion("PickCode between", value1, value2, "pickcode");
            return (Criteria) this;
        }

        public Criteria andPickcodeNotBetween(String value1, String value2) {
            addCriterion("PickCode not between", value1, value2, "pickcode");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("Description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("Description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("Description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("Description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("Description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("Description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("Description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("Description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("Description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("Description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("Description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("Description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("Description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("Description not between", value1, value2, "description");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoTranstypeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoTranstypeConfigExample() {
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

        public Criteria andMatchtypeIsNull() {
            addCriterion("matchType is null");
            return (Criteria) this;
        }

        public Criteria andMatchtypeIsNotNull() {
            addCriterion("matchType is not null");
            return (Criteria) this;
        }

        public Criteria andMatchtypeEqualTo(Boolean value) {
            addCriterion("matchType =", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeNotEqualTo(Boolean value) {
            addCriterion("matchType <>", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeGreaterThan(Boolean value) {
            addCriterion("matchType >", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("matchType >=", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeLessThan(Boolean value) {
            addCriterion("matchType <", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeLessThanOrEqualTo(Boolean value) {
            addCriterion("matchType <=", value, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeIn(List<Boolean> values) {
            addCriterion("matchType in", values, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeNotIn(List<Boolean> values) {
            addCriterion("matchType not in", values, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeBetween(Boolean value1, Boolean value2) {
            addCriterion("matchType between", value1, value2, "matchtype");
            return (Criteria) this;
        }

        public Criteria andMatchtypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("matchType not between", value1, value2, "matchtype");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andTranstypeIsNull() {
            addCriterion("transtype is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("transtype is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("transtype =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("transtype <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("transtype >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("transtype >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("transtype <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("transtype <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("transtype like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("transtype not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("transtype in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("transtype not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("transtype between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("transtype not between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andComparegreaterIsNull() {
            addCriterion("compareGreater is null");
            return (Criteria) this;
        }

        public Criteria andComparegreaterIsNotNull() {
            addCriterion("compareGreater is not null");
            return (Criteria) this;
        }

        public Criteria andComparegreaterEqualTo(Boolean value) {
            addCriterion("compareGreater =", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterNotEqualTo(Boolean value) {
            addCriterion("compareGreater <>", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterGreaterThan(Boolean value) {
            addCriterion("compareGreater >", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("compareGreater >=", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterLessThan(Boolean value) {
            addCriterion("compareGreater <", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterLessThanOrEqualTo(Boolean value) {
            addCriterion("compareGreater <=", value, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterIn(List<Boolean> values) {
            addCriterion("compareGreater in", values, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterNotIn(List<Boolean> values) {
            addCriterion("compareGreater not in", values, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterBetween(Boolean value1, Boolean value2) {
            addCriterion("compareGreater between", value1, value2, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparegreaterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("compareGreater not between", value1, value2, "comparegreater");
            return (Criteria) this;
        }

        public Criteria andComparequantityIsNull() {
            addCriterion("compareQuantity is null");
            return (Criteria) this;
        }

        public Criteria andComparequantityIsNotNull() {
            addCriterion("compareQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andComparequantityEqualTo(Integer value) {
            addCriterion("compareQuantity =", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityNotEqualTo(Integer value) {
            addCriterion("compareQuantity <>", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityGreaterThan(Integer value) {
            addCriterion("compareQuantity >", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("compareQuantity >=", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityLessThan(Integer value) {
            addCriterion("compareQuantity <", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityLessThanOrEqualTo(Integer value) {
            addCriterion("compareQuantity <=", value, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityIn(List<Integer> values) {
            addCriterion("compareQuantity in", values, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityNotIn(List<Integer> values) {
            addCriterion("compareQuantity not in", values, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityBetween(Integer value1, Integer value2) {
            addCriterion("compareQuantity between", value1, value2, "comparequantity");
            return (Criteria) this;
        }

        public Criteria andComparequantityNotBetween(Integer value1, Integer value2) {
            addCriterion("compareQuantity not between", value1, value2, "comparequantity");
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
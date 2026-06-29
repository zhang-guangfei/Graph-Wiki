package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SamplemodelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SamplemodelExample() {
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

        public Criteria andSamplemodelnoIsNull() {
            addCriterion("SampleModelNo is null");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoIsNotNull() {
            addCriterion("SampleModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoEqualTo(String value) {
            addCriterion("SampleModelNo =", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoNotEqualTo(String value) {
            addCriterion("SampleModelNo <>", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoGreaterThan(String value) {
            addCriterion("SampleModelNo >", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoGreaterThanOrEqualTo(String value) {
            addCriterion("SampleModelNo >=", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoLessThan(String value) {
            addCriterion("SampleModelNo <", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoLessThanOrEqualTo(String value) {
            addCriterion("SampleModelNo <=", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoLike(String value) {
            addCriterion("SampleModelNo like", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoNotLike(String value) {
            addCriterion("SampleModelNo not like", value, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoIn(List<String> values) {
            addCriterion("SampleModelNo in", values, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoNotIn(List<String> values) {
            addCriterion("SampleModelNo not in", values, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoBetween(String value1, String value2) {
            addCriterion("SampleModelNo between", value1, value2, "samplemodelno");
            return (Criteria) this;
        }

        public Criteria andSamplemodelnoNotBetween(String value1, String value2) {
            addCriterion("SampleModelNo not between", value1, value2, "samplemodelno");
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

        public Criteria andDeptjpIsNull() {
            addCriterion("DeptJp is null");
            return (Criteria) this;
        }

        public Criteria andDeptjpIsNotNull() {
            addCriterion("DeptJp is not null");
            return (Criteria) this;
        }

        public Criteria andDeptjpEqualTo(String value) {
            addCriterion("DeptJp =", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpNotEqualTo(String value) {
            addCriterion("DeptJp <>", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpGreaterThan(String value) {
            addCriterion("DeptJp >", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpGreaterThanOrEqualTo(String value) {
            addCriterion("DeptJp >=", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpLessThan(String value) {
            addCriterion("DeptJp <", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpLessThanOrEqualTo(String value) {
            addCriterion("DeptJp <=", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpLike(String value) {
            addCriterion("DeptJp like", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpNotLike(String value) {
            addCriterion("DeptJp not like", value, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpIn(List<String> values) {
            addCriterion("DeptJp in", values, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpNotIn(List<String> values) {
            addCriterion("DeptJp not in", values, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpBetween(String value1, String value2) {
            addCriterion("DeptJp between", value1, value2, "deptjp");
            return (Criteria) this;
        }

        public Criteria andDeptjpNotBetween(String value1, String value2) {
            addCriterion("DeptJp not between", value1, value2, "deptjp");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("UpdDate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("UpdDate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("UpdDate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("UpdDate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("UpdDate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdDate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("UpdDate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("UpdDate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("UpdDate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("UpdDate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("UpdDate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("UpdDate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andModelseirIsNull() {
            addCriterion("ModelSeir is null");
            return (Criteria) this;
        }

        public Criteria andModelseirIsNotNull() {
            addCriterion("ModelSeir is not null");
            return (Criteria) this;
        }

        public Criteria andModelseirEqualTo(String value) {
            addCriterion("ModelSeir =", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirNotEqualTo(String value) {
            addCriterion("ModelSeir <>", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirGreaterThan(String value) {
            addCriterion("ModelSeir >", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirGreaterThanOrEqualTo(String value) {
            addCriterion("ModelSeir >=", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirLessThan(String value) {
            addCriterion("ModelSeir <", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirLessThanOrEqualTo(String value) {
            addCriterion("ModelSeir <=", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirLike(String value) {
            addCriterion("ModelSeir like", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirNotLike(String value) {
            addCriterion("ModelSeir not like", value, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirIn(List<String> values) {
            addCriterion("ModelSeir in", values, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirNotIn(List<String> values) {
            addCriterion("ModelSeir not in", values, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirBetween(String value1, String value2) {
            addCriterion("ModelSeir between", value1, value2, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModelseirNotBetween(String value1, String value2) {
            addCriterion("ModelSeir not between", value1, value2, "modelseir");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNull() {
            addCriterion("ModelType is null");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNotNull() {
            addCriterion("ModelType is not null");
            return (Criteria) this;
        }

        public Criteria andModeltypeEqualTo(String value) {
            addCriterion("ModelType =", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotEqualTo(String value) {
            addCriterion("ModelType <>", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThan(String value) {
            addCriterion("ModelType >", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThanOrEqualTo(String value) {
            addCriterion("ModelType >=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThan(String value) {
            addCriterion("ModelType <", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThanOrEqualTo(String value) {
            addCriterion("ModelType <=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLike(String value) {
            addCriterion("ModelType like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotLike(String value) {
            addCriterion("ModelType not like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeIn(List<String> values) {
            addCriterion("ModelType in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotIn(List<String> values) {
            addCriterion("ModelType not in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeBetween(String value1, String value2) {
            addCriterion("ModelType between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotBetween(String value1, String value2) {
            addCriterion("ModelType not between", value1, value2, "modeltype");
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
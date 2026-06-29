package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class OpsModelNotcal3sbomExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsModelNotcal3sbomExample() {
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

        public Criteria andMatchstringIsNull() {
            addCriterion("matchString is null");
            return (Criteria) this;
        }

        public Criteria andMatchstringIsNotNull() {
            addCriterion("matchString is not null");
            return (Criteria) this;
        }

        public Criteria andMatchstringEqualTo(String value) {
            addCriterion("matchString =", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringNotEqualTo(String value) {
            addCriterion("matchString <>", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringGreaterThan(String value) {
            addCriterion("matchString >", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringGreaterThanOrEqualTo(String value) {
            addCriterion("matchString >=", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringLessThan(String value) {
            addCriterion("matchString <", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringLessThanOrEqualTo(String value) {
            addCriterion("matchString <=", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringLike(String value) {
            addCriterion("matchString like", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringNotLike(String value) {
            addCriterion("matchString not like", value, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringIn(List<String> values) {
            addCriterion("matchString in", values, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringNotIn(List<String> values) {
            addCriterion("matchString not in", values, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringBetween(String value1, String value2) {
            addCriterion("matchString between", value1, value2, "matchstring");
            return (Criteria) this;
        }

        public Criteria andMatchstringNotBetween(String value1, String value2) {
            addCriterion("matchString not between", value1, value2, "matchstring");
            return (Criteria) this;
        }

        public Criteria andSeriesbigIsNull() {
            addCriterion("seriesBig is null");
            return (Criteria) this;
        }

        public Criteria andSeriesbigIsNotNull() {
            addCriterion("seriesBig is not null");
            return (Criteria) this;
        }

        public Criteria andSeriesbigEqualTo(String value) {
            addCriterion("seriesBig =", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigNotEqualTo(String value) {
            addCriterion("seriesBig <>", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigGreaterThan(String value) {
            addCriterion("seriesBig >", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigGreaterThanOrEqualTo(String value) {
            addCriterion("seriesBig >=", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigLessThan(String value) {
            addCriterion("seriesBig <", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigLessThanOrEqualTo(String value) {
            addCriterion("seriesBig <=", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigLike(String value) {
            addCriterion("seriesBig like", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigNotLike(String value) {
            addCriterion("seriesBig not like", value, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigIn(List<String> values) {
            addCriterion("seriesBig in", values, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigNotIn(List<String> values) {
            addCriterion("seriesBig not in", values, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigBetween(String value1, String value2) {
            addCriterion("seriesBig between", value1, value2, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriesbigNotBetween(String value1, String value2) {
            addCriterion("seriesBig not between", value1, value2, "seriesbig");
            return (Criteria) this;
        }

        public Criteria andSeriessmallIsNull() {
            addCriterion("seriesSmall is null");
            return (Criteria) this;
        }

        public Criteria andSeriessmallIsNotNull() {
            addCriterion("seriesSmall is not null");
            return (Criteria) this;
        }

        public Criteria andSeriessmallEqualTo(String value) {
            addCriterion("seriesSmall =", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallNotEqualTo(String value) {
            addCriterion("seriesSmall <>", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallGreaterThan(String value) {
            addCriterion("seriesSmall >", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallGreaterThanOrEqualTo(String value) {
            addCriterion("seriesSmall >=", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallLessThan(String value) {
            addCriterion("seriesSmall <", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallLessThanOrEqualTo(String value) {
            addCriterion("seriesSmall <=", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallLike(String value) {
            addCriterion("seriesSmall like", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallNotLike(String value) {
            addCriterion("seriesSmall not like", value, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallIn(List<String> values) {
            addCriterion("seriesSmall in", values, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallNotIn(List<String> values) {
            addCriterion("seriesSmall not in", values, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallBetween(String value1, String value2) {
            addCriterion("seriesSmall between", value1, value2, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andSeriessmallNotBetween(String value1, String value2) {
            addCriterion("seriesSmall not between", value1, value2, "seriessmall");
            return (Criteria) this;
        }

        public Criteria andDescInfoIsNull() {
            addCriterion("desc_info is null");
            return (Criteria) this;
        }

        public Criteria andDescInfoIsNotNull() {
            addCriterion("desc_info is not null");
            return (Criteria) this;
        }

        public Criteria andDescInfoEqualTo(String value) {
            addCriterion("desc_info =", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoNotEqualTo(String value) {
            addCriterion("desc_info <>", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoGreaterThan(String value) {
            addCriterion("desc_info >", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoGreaterThanOrEqualTo(String value) {
            addCriterion("desc_info >=", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoLessThan(String value) {
            addCriterion("desc_info <", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoLessThanOrEqualTo(String value) {
            addCriterion("desc_info <=", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoLike(String value) {
            addCriterion("desc_info like", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoNotLike(String value) {
            addCriterion("desc_info not like", value, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoIn(List<String> values) {
            addCriterion("desc_info in", values, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoNotIn(List<String> values) {
            addCriterion("desc_info not in", values, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoBetween(String value1, String value2) {
            addCriterion("desc_info between", value1, value2, "descInfo");
            return (Criteria) this;
        }

        public Criteria andDescInfoNotBetween(String value1, String value2) {
            addCriterion("desc_info not between", value1, value2, "descInfo");
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
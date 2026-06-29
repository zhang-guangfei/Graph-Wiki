package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class YyLongmodelexchangeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public YyLongmodelexchangeExample() {
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

        public Criteria andYymodelIsNull() {
            addCriterion("YYModel is null");
            return (Criteria) this;
        }

        public Criteria andYymodelIsNotNull() {
            addCriterion("YYModel is not null");
            return (Criteria) this;
        }

        public Criteria andYymodelEqualTo(String value) {
            addCriterion("YYModel =", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelNotEqualTo(String value) {
            addCriterion("YYModel <>", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelGreaterThan(String value) {
            addCriterion("YYModel >", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelGreaterThanOrEqualTo(String value) {
            addCriterion("YYModel >=", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelLessThan(String value) {
            addCriterion("YYModel <", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelLessThanOrEqualTo(String value) {
            addCriterion("YYModel <=", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelLike(String value) {
            addCriterion("YYModel like", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelNotLike(String value) {
            addCriterion("YYModel not like", value, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelIn(List<String> values) {
            addCriterion("YYModel in", values, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelNotIn(List<String> values) {
            addCriterion("YYModel not in", values, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelBetween(String value1, String value2) {
            addCriterion("YYModel between", value1, value2, "yymodel");
            return (Criteria) this;
        }

        public Criteria andYymodelNotBetween(String value1, String value2) {
            addCriterion("YYModel not between", value1, value2, "yymodel");
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

        public Criteria andModelIsNull() {
            addCriterion("Model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("Model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("Model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("Model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("Model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("Model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("Model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("Model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("Model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("Model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("Model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("Model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("Model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("Model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelcopyIsNull() {
            addCriterion("ModelCopy is null");
            return (Criteria) this;
        }

        public Criteria andModelcopyIsNotNull() {
            addCriterion("ModelCopy is not null");
            return (Criteria) this;
        }

        public Criteria andModelcopyEqualTo(String value) {
            addCriterion("ModelCopy =", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyNotEqualTo(String value) {
            addCriterion("ModelCopy <>", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyGreaterThan(String value) {
            addCriterion("ModelCopy >", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyGreaterThanOrEqualTo(String value) {
            addCriterion("ModelCopy >=", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyLessThan(String value) {
            addCriterion("ModelCopy <", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyLessThanOrEqualTo(String value) {
            addCriterion("ModelCopy <=", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyLike(String value) {
            addCriterion("ModelCopy like", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyNotLike(String value) {
            addCriterion("ModelCopy not like", value, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyIn(List<String> values) {
            addCriterion("ModelCopy in", values, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyNotIn(List<String> values) {
            addCriterion("ModelCopy not in", values, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyBetween(String value1, String value2) {
            addCriterion("ModelCopy between", value1, value2, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andModelcopyNotBetween(String value1, String value2) {
            addCriterion("ModelCopy not between", value1, value2, "modelcopy");
            return (Criteria) this;
        }

        public Criteria andOtherrulesIsNull() {
            addCriterion("OtherRules is null");
            return (Criteria) this;
        }

        public Criteria andOtherrulesIsNotNull() {
            addCriterion("OtherRules is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrulesEqualTo(String value) {
            addCriterion("OtherRules =", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesNotEqualTo(String value) {
            addCriterion("OtherRules <>", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesGreaterThan(String value) {
            addCriterion("OtherRules >", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesGreaterThanOrEqualTo(String value) {
            addCriterion("OtherRules >=", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesLessThan(String value) {
            addCriterion("OtherRules <", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesLessThanOrEqualTo(String value) {
            addCriterion("OtherRules <=", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesLike(String value) {
            addCriterion("OtherRules like", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesNotLike(String value) {
            addCriterion("OtherRules not like", value, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesIn(List<String> values) {
            addCriterion("OtherRules in", values, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesNotIn(List<String> values) {
            addCriterion("OtherRules not in", values, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesBetween(String value1, String value2) {
            addCriterion("OtherRules between", value1, value2, "otherrules");
            return (Criteria) this;
        }

        public Criteria andOtherrulesNotBetween(String value1, String value2) {
            addCriterion("OtherRules not between", value1, value2, "otherrules");
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
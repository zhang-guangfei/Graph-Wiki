package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class SupplierTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierTempExample() {
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

        public Criteria andNewCodeIsNull() {
            addCriterion("new_code is null");
            return (Criteria) this;
        }

        public Criteria andNewCodeIsNotNull() {
            addCriterion("new_code is not null");
            return (Criteria) this;
        }

        public Criteria andNewCodeEqualTo(String value) {
            addCriterion("new_code =", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeNotEqualTo(String value) {
            addCriterion("new_code <>", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeGreaterThan(String value) {
            addCriterion("new_code >", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeGreaterThanOrEqualTo(String value) {
            addCriterion("new_code >=", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeLessThan(String value) {
            addCriterion("new_code <", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeLessThanOrEqualTo(String value) {
            addCriterion("new_code <=", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeLike(String value) {
            addCriterion("new_code like", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeNotLike(String value) {
            addCriterion("new_code not like", value, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeIn(List<String> values) {
            addCriterion("new_code in", values, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeNotIn(List<String> values) {
            addCriterion("new_code not in", values, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeBetween(String value1, String value2) {
            addCriterion("new_code between", value1, value2, "newCode");
            return (Criteria) this;
        }

        public Criteria andNewCodeNotBetween(String value1, String value2) {
            addCriterion("new_code not between", value1, value2, "newCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeIsNull() {
            addCriterion("old_code is null");
            return (Criteria) this;
        }

        public Criteria andOldCodeIsNotNull() {
            addCriterion("old_code is not null");
            return (Criteria) this;
        }

        public Criteria andOldCodeEqualTo(String value) {
            addCriterion("old_code =", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeNotEqualTo(String value) {
            addCriterion("old_code <>", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeGreaterThan(String value) {
            addCriterion("old_code >", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeGreaterThanOrEqualTo(String value) {
            addCriterion("old_code >=", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeLessThan(String value) {
            addCriterion("old_code <", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeLessThanOrEqualTo(String value) {
            addCriterion("old_code <=", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeLike(String value) {
            addCriterion("old_code like", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeNotLike(String value) {
            addCriterion("old_code not like", value, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeIn(List<String> values) {
            addCriterion("old_code in", values, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeNotIn(List<String> values) {
            addCriterion("old_code not in", values, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeBetween(String value1, String value2) {
            addCriterion("old_code between", value1, value2, "oldCode");
            return (Criteria) this;
        }

        public Criteria andOldCodeNotBetween(String value1, String value2) {
            addCriterion("old_code not between", value1, value2, "oldCode");
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
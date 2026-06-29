package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrepareOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrepareOrderExample() {
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

        public Criteria andPrepareNoIsNull() {
            addCriterion("prepare_no is null");
            return (Criteria) this;
        }

        public Criteria andPrepareNoIsNotNull() {
            addCriterion("prepare_no is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareNoEqualTo(String value) {
            addCriterion("prepare_no =", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoNotEqualTo(String value) {
            addCriterion("prepare_no <>", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoGreaterThan(String value) {
            addCriterion("prepare_no >", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoGreaterThanOrEqualTo(String value) {
            addCriterion("prepare_no >=", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoLessThan(String value) {
            addCriterion("prepare_no <", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoLessThanOrEqualTo(String value) {
            addCriterion("prepare_no <=", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoLike(String value) {
            addCriterion("prepare_no like", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoNotLike(String value) {
            addCriterion("prepare_no not like", value, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoIn(List<String> values) {
            addCriterion("prepare_no in", values, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoNotIn(List<String> values) {
            addCriterion("prepare_no not in", values, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoBetween(String value1, String value2) {
            addCriterion("prepare_no between", value1, value2, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareNoNotBetween(String value1, String value2) {
            addCriterion("prepare_no not between", value1, value2, "prepareNo");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeIsNull() {
            addCriterion("prepare_type is null");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeIsNotNull() {
            addCriterion("prepare_type is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeEqualTo(Integer value) {
            addCriterion("prepare_type =", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeNotEqualTo(Integer value) {
            addCriterion("prepare_type <>", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeGreaterThan(Integer value) {
            addCriterion("prepare_type >", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prepare_type >=", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeLessThan(Integer value) {
            addCriterion("prepare_type <", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeLessThanOrEqualTo(Integer value) {
            addCriterion("prepare_type <=", value, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeIn(List<Integer> values) {
            addCriterion("prepare_type in", values, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeNotIn(List<Integer> values) {
            addCriterion("prepare_type not in", values, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeBetween(Integer value1, Integer value2) {
            addCriterion("prepare_type between", value1, value2, "prepareType");
            return (Criteria) this;
        }

        public Criteria andPrepareTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("prepare_type not between", value1, value2, "prepareType");
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

        public Criteria andModelnoIsNull() {
            addCriterion("modelno is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelno is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelno =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelno <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelno >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelno >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelno <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelno <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelno like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelno not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelno in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelno not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelno between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelno not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNull() {
            addCriterion("optstate is null");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNotNull() {
            addCriterion("optstate is not null");
            return (Criteria) this;
        }

        public Criteria andOptstateEqualTo(Integer value) {
            addCriterion("optstate =", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotEqualTo(Integer value) {
            addCriterion("optstate <>", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThan(Integer value) {
            addCriterion("optstate >", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("optstate >=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThan(Integer value) {
            addCriterion("optstate <", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThanOrEqualTo(Integer value) {
            addCriterion("optstate <=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateIn(List<Integer> values) {
            addCriterion("optstate in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotIn(List<Integer> values) {
            addCriterion("optstate not in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateBetween(Integer value1, Integer value2) {
            addCriterion("optstate between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotBetween(Integer value1, Integer value2) {
            addCriterion("optstate not between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("opttime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("opttime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("opttime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("opttime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("opttime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("opttime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("opttime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("opttime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("opttime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("opttime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("opttime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("opttime not between", value1, value2, "opttime");
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
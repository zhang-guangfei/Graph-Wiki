package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShikomiModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShikomiModelExample() {
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

        public Criteria andShikominoIsNull() {
            addCriterion("shikomino is null");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNotNull() {
            addCriterion("shikomino is not null");
            return (Criteria) this;
        }

        public Criteria andShikominoEqualTo(String value) {
            addCriterion("shikomino =", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotEqualTo(String value) {
            addCriterion("shikomino <>", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThan(String value) {
            addCriterion("shikomino >", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThanOrEqualTo(String value) {
            addCriterion("shikomino >=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThan(String value) {
            addCriterion("shikomino <", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThanOrEqualTo(String value) {
            addCriterion("shikomino <=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLike(String value) {
            addCriterion("shikomino like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotLike(String value) {
            addCriterion("shikomino not like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoIn(List<String> values) {
            addCriterion("shikomino in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotIn(List<String> values) {
            addCriterion("shikomino not in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoBetween(String value1, String value2) {
            addCriterion("shikomino between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotBetween(String value1, String value2) {
            addCriterion("shikomino not between", value1, value2, "shikomino");
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

        public Criteria andSerialmodelIsNull() {
            addCriterion("serialModel is null");
            return (Criteria) this;
        }

        public Criteria andSerialmodelIsNotNull() {
            addCriterion("serialModel is not null");
            return (Criteria) this;
        }

        public Criteria andSerialmodelEqualTo(String value) {
            addCriterion("serialModel =", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotEqualTo(String value) {
            addCriterion("serialModel <>", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelGreaterThan(String value) {
            addCriterion("serialModel >", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelGreaterThanOrEqualTo(String value) {
            addCriterion("serialModel >=", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLessThan(String value) {
            addCriterion("serialModel <", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLessThanOrEqualTo(String value) {
            addCriterion("serialModel <=", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLike(String value) {
            addCriterion("serialModel like", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotLike(String value) {
            addCriterion("serialModel not like", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelIn(List<String> values) {
            addCriterion("serialModel in", values, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotIn(List<String> values) {
            addCriterion("serialModel not in", values, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelBetween(String value1, String value2) {
            addCriterion("serialModel between", value1, value2, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotBetween(String value1, String value2) {
            addCriterion("serialModel not between", value1, value2, "serialmodel");
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

        public Criteria andModeltypeIsNull() {
            addCriterion("modelType is null");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNotNull() {
            addCriterion("modelType is not null");
            return (Criteria) this;
        }

        public Criteria andModeltypeEqualTo(String value) {
            addCriterion("modelType =", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotEqualTo(String value) {
            addCriterion("modelType <>", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThan(String value) {
            addCriterion("modelType >", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThanOrEqualTo(String value) {
            addCriterion("modelType >=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThan(String value) {
            addCriterion("modelType <", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThanOrEqualTo(String value) {
            addCriterion("modelType <=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLike(String value) {
            addCriterion("modelType like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotLike(String value) {
            addCriterion("modelType not like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeIn(List<String> values) {
            addCriterion("modelType in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotIn(List<String> values) {
            addCriterion("modelType not in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeBetween(String value1, String value2) {
            addCriterion("modelType between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotBetween(String value1, String value2) {
            addCriterion("modelType not between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagIsNull() {
            addCriterion("mainModelFlag is null");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagIsNotNull() {
            addCriterion("mainModelFlag is not null");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagEqualTo(String value) {
            addCriterion("mainModelFlag =", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagNotEqualTo(String value) {
            addCriterion("mainModelFlag <>", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagGreaterThan(String value) {
            addCriterion("mainModelFlag >", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagGreaterThanOrEqualTo(String value) {
            addCriterion("mainModelFlag >=", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagLessThan(String value) {
            addCriterion("mainModelFlag <", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagLessThanOrEqualTo(String value) {
            addCriterion("mainModelFlag <=", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagLike(String value) {
            addCriterion("mainModelFlag like", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagNotLike(String value) {
            addCriterion("mainModelFlag not like", value, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagIn(List<String> values) {
            addCriterion("mainModelFlag in", values, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagNotIn(List<String> values) {
            addCriterion("mainModelFlag not in", values, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagBetween(String value1, String value2) {
            addCriterion("mainModelFlag between", value1, value2, "mainmodelflag");
            return (Criteria) this;
        }

        public Criteria andMainmodelflagNotBetween(String value1, String value2) {
            addCriterion("mainModelFlag not between", value1, value2, "mainmodelflag");
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
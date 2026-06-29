package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductBomTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductBomTempExample() {
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

        public Criteria andBomidIsNull() {
            addCriterion("bomId is null");
            return (Criteria) this;
        }

        public Criteria andBomidIsNotNull() {
            addCriterion("bomId is not null");
            return (Criteria) this;
        }

        public Criteria andBomidEqualTo(Long value) {
            addCriterion("bomId =", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotEqualTo(Long value) {
            addCriterion("bomId <>", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidGreaterThan(Long value) {
            addCriterion("bomId >", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidGreaterThanOrEqualTo(Long value) {
            addCriterion("bomId >=", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidLessThan(Long value) {
            addCriterion("bomId <", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidLessThanOrEqualTo(Long value) {
            addCriterion("bomId <=", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidIn(List<Long> values) {
            addCriterion("bomId in", values, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotIn(List<Long> values) {
            addCriterion("bomId not in", values, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidBetween(Long value1, Long value2) {
            addCriterion("bomId between", value1, value2, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotBetween(Long value1, Long value2) {
            addCriterion("bomId not between", value1, value2, "bomid");
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

        public Criteria andPriorityCompleteIsNull() {
            addCriterion("Priority_Complete is null");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteIsNotNull() {
            addCriterion("Priority_Complete is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteEqualTo(Boolean value) {
            addCriterion("Priority_Complete =", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteNotEqualTo(Boolean value) {
            addCriterion("Priority_Complete <>", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteGreaterThan(Boolean value) {
            addCriterion("Priority_Complete >", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("Priority_Complete >=", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteLessThan(Boolean value) {
            addCriterion("Priority_Complete <", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteLessThanOrEqualTo(Boolean value) {
            addCriterion("Priority_Complete <=", value, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteIn(List<Boolean> values) {
            addCriterion("Priority_Complete in", values, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteNotIn(List<Boolean> values) {
            addCriterion("Priority_Complete not in", values, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteBetween(Boolean value1, Boolean value2) {
            addCriterion("Priority_Complete between", value1, value2, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityCompleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("Priority_Complete not between", value1, value2, "priorityComplete");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNull() {
            addCriterion("Priority_level is null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNotNull() {
            addCriterion("Priority_level is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelEqualTo(Integer value) {
            addCriterion("Priority_level =", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotEqualTo(Integer value) {
            addCriterion("Priority_level <>", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThan(Integer value) {
            addCriterion("Priority_level >", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("Priority_level >=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThan(Integer value) {
            addCriterion("Priority_level <", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThanOrEqualTo(Integer value) {
            addCriterion("Priority_level <=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIn(List<Integer> values) {
            addCriterion("Priority_level in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotIn(List<Integer> values) {
            addCriterion("Priority_level not in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelBetween(Integer value1, Integer value2) {
            addCriterion("Priority_level between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("Priority_level not between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andSchemeIsNull() {
            addCriterion("scheme is null");
            return (Criteria) this;
        }

        public Criteria andSchemeIsNotNull() {
            addCriterion("scheme is not null");
            return (Criteria) this;
        }

        public Criteria andSchemeEqualTo(String value) {
            addCriterion("scheme =", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotEqualTo(String value) {
            addCriterion("scheme <>", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeGreaterThan(String value) {
            addCriterion("scheme >", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeGreaterThanOrEqualTo(String value) {
            addCriterion("scheme >=", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLessThan(String value) {
            addCriterion("scheme <", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLessThanOrEqualTo(String value) {
            addCriterion("scheme <=", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLike(String value) {
            addCriterion("scheme like", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotLike(String value) {
            addCriterion("scheme not like", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeIn(List<String> values) {
            addCriterion("scheme in", values, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotIn(List<String> values) {
            addCriterion("scheme not in", values, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeBetween(String value1, String value2) {
            addCriterion("scheme between", value1, value2, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotBetween(String value1, String value2) {
            addCriterion("scheme not between", value1, value2, "scheme");
            return (Criteria) this;
        }

        public Criteria andIsWmsIsNull() {
            addCriterion("is_wms is null");
            return (Criteria) this;
        }

        public Criteria andIsWmsIsNotNull() {
            addCriterion("is_wms is not null");
            return (Criteria) this;
        }

        public Criteria andIsWmsEqualTo(Integer value) {
            addCriterion("is_wms =", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotEqualTo(Integer value) {
            addCriterion("is_wms <>", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsGreaterThan(Integer value) {
            addCriterion("is_wms >", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_wms >=", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsLessThan(Integer value) {
            addCriterion("is_wms <", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsLessThanOrEqualTo(Integer value) {
            addCriterion("is_wms <=", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsIn(List<Integer> values) {
            addCriterion("is_wms in", values, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotIn(List<Integer> values) {
            addCriterion("is_wms not in", values, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsBetween(Integer value1, Integer value2) {
            addCriterion("is_wms between", value1, value2, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotBetween(Integer value1, Integer value2) {
            addCriterion("is_wms not between", value1, value2, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNull() {
            addCriterion("IsValid is null");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNotNull() {
            addCriterion("IsValid is not null");
            return (Criteria) this;
        }

        public Criteria andIsvalidEqualTo(Boolean value) {
            addCriterion("IsValid =", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotEqualTo(Boolean value) {
            addCriterion("IsValid <>", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThan(Boolean value) {
            addCriterion("IsValid >", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsValid >=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThan(Boolean value) {
            addCriterion("IsValid <", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThanOrEqualTo(Boolean value) {
            addCriterion("IsValid <=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidIn(List<Boolean> values) {
            addCriterion("IsValid in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotIn(List<Boolean> values) {
            addCriterion("IsValid not in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidBetween(Boolean value1, Boolean value2) {
            addCriterion("IsValid between", value1, value2, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsValid not between", value1, value2, "isvalid");
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
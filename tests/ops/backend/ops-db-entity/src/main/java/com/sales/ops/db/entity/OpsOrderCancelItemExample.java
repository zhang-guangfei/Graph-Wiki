package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsOrderCancelItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsOrderCancelItemExample() {
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

        public Criteria andCancelIdIsNull() {
            addCriterion("cancel_id is null");
            return (Criteria) this;
        }

        public Criteria andCancelIdIsNotNull() {
            addCriterion("cancel_id is not null");
            return (Criteria) this;
        }

        public Criteria andCancelIdEqualTo(Long value) {
            addCriterion("cancel_id =", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdNotEqualTo(Long value) {
            addCriterion("cancel_id <>", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdGreaterThan(Long value) {
            addCriterion("cancel_id >", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cancel_id >=", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdLessThan(Long value) {
            addCriterion("cancel_id <", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdLessThanOrEqualTo(Long value) {
            addCriterion("cancel_id <=", value, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdIn(List<Long> values) {
            addCriterion("cancel_id in", values, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdNotIn(List<Long> values) {
            addCriterion("cancel_id not in", values, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdBetween(Long value1, Long value2) {
            addCriterion("cancel_id between", value1, value2, "cancelId");
            return (Criteria) this;
        }

        public Criteria andCancelIdNotBetween(Long value1, Long value2) {
            addCriterion("cancel_id not between", value1, value2, "cancelId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdIsNull() {
            addCriterion("wl_order_id is null");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdIsNotNull() {
            addCriterion("wl_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdEqualTo(String value) {
            addCriterion("wl_order_id =", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdNotEqualTo(String value) {
            addCriterion("wl_order_id <>", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdGreaterThan(String value) {
            addCriterion("wl_order_id >", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("wl_order_id >=", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdLessThan(String value) {
            addCriterion("wl_order_id <", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdLessThanOrEqualTo(String value) {
            addCriterion("wl_order_id <=", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdLike(String value) {
            addCriterion("wl_order_id like", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdNotLike(String value) {
            addCriterion("wl_order_id not like", value, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdIn(List<String> values) {
            addCriterion("wl_order_id in", values, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdNotIn(List<String> values) {
            addCriterion("wl_order_id not in", values, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdBetween(String value1, String value2) {
            addCriterion("wl_order_id between", value1, value2, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlOrderIdNotBetween(String value1, String value2) {
            addCriterion("wl_order_id not between", value1, value2, "wlOrderId");
            return (Criteria) this;
        }

        public Criteria andWlTypeIsNull() {
            addCriterion("wl_type is null");
            return (Criteria) this;
        }

        public Criteria andWlTypeIsNotNull() {
            addCriterion("wl_type is not null");
            return (Criteria) this;
        }

        public Criteria andWlTypeEqualTo(String value) {
            addCriterion("wl_type =", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeNotEqualTo(String value) {
            addCriterion("wl_type <>", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeGreaterThan(String value) {
            addCriterion("wl_type >", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeGreaterThanOrEqualTo(String value) {
            addCriterion("wl_type >=", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeLessThan(String value) {
            addCriterion("wl_type <", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeLessThanOrEqualTo(String value) {
            addCriterion("wl_type <=", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeLike(String value) {
            addCriterion("wl_type like", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeNotLike(String value) {
            addCriterion("wl_type not like", value, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeIn(List<String> values) {
            addCriterion("wl_type in", values, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeNotIn(List<String> values) {
            addCriterion("wl_type not in", values, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeBetween(String value1, String value2) {
            addCriterion("wl_type between", value1, value2, "wlType");
            return (Criteria) this;
        }

        public Criteria andWlTypeNotBetween(String value1, String value2) {
            addCriterion("wl_type not between", value1, value2, "wlType");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoWarehouseDeliverydayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoWarehouseDeliverydayExample() {
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

        public Criteria andFromwarehouseIsNull() {
            addCriterion("fromWarehouse is null");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseIsNotNull() {
            addCriterion("fromWarehouse is not null");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseEqualTo(String value) {
            addCriterion("fromWarehouse =", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseNotEqualTo(String value) {
            addCriterion("fromWarehouse <>", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseGreaterThan(String value) {
            addCriterion("fromWarehouse >", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("fromWarehouse >=", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseLessThan(String value) {
            addCriterion("fromWarehouse <", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseLessThanOrEqualTo(String value) {
            addCriterion("fromWarehouse <=", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseLike(String value) {
            addCriterion("fromWarehouse like", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseNotLike(String value) {
            addCriterion("fromWarehouse not like", value, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseIn(List<String> values) {
            addCriterion("fromWarehouse in", values, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseNotIn(List<String> values) {
            addCriterion("fromWarehouse not in", values, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseBetween(String value1, String value2) {
            addCriterion("fromWarehouse between", value1, value2, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andFromwarehouseNotBetween(String value1, String value2) {
            addCriterion("fromWarehouse not between", value1, value2, "fromwarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseIsNull() {
            addCriterion("toWarehouse is null");
            return (Criteria) this;
        }

        public Criteria andTowarehouseIsNotNull() {
            addCriterion("toWarehouse is not null");
            return (Criteria) this;
        }

        public Criteria andTowarehouseEqualTo(String value) {
            addCriterion("toWarehouse =", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseNotEqualTo(String value) {
            addCriterion("toWarehouse <>", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseGreaterThan(String value) {
            addCriterion("toWarehouse >", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("toWarehouse >=", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseLessThan(String value) {
            addCriterion("toWarehouse <", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseLessThanOrEqualTo(String value) {
            addCriterion("toWarehouse <=", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseLike(String value) {
            addCriterion("toWarehouse like", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseNotLike(String value) {
            addCriterion("toWarehouse not like", value, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseIn(List<String> values) {
            addCriterion("toWarehouse in", values, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseNotIn(List<String> values) {
            addCriterion("toWarehouse not in", values, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseBetween(String value1, String value2) {
            addCriterion("toWarehouse between", value1, value2, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andTowarehouseNotBetween(String value1, String value2) {
            addCriterion("toWarehouse not between", value1, value2, "towarehouse");
            return (Criteria) this;
        }

        public Criteria andDeliverydayIsNull() {
            addCriterion("deliveryDay is null");
            return (Criteria) this;
        }

        public Criteria andDeliverydayIsNotNull() {
            addCriterion("deliveryDay is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverydayEqualTo(Integer value) {
            addCriterion("deliveryDay =", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayNotEqualTo(Integer value) {
            addCriterion("deliveryDay <>", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayGreaterThan(Integer value) {
            addCriterion("deliveryDay >", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliveryDay >=", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayLessThan(Integer value) {
            addCriterion("deliveryDay <", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayLessThanOrEqualTo(Integer value) {
            addCriterion("deliveryDay <=", value, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayIn(List<Integer> values) {
            addCriterion("deliveryDay in", values, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayNotIn(List<Integer> values) {
            addCriterion("deliveryDay not in", values, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayBetween(Integer value1, Integer value2) {
            addCriterion("deliveryDay between", value1, value2, "deliveryday");
            return (Criteria) this;
        }

        public Criteria andDeliverydayNotBetween(Integer value1, Integer value2) {
            addCriterion("deliveryDay not between", value1, value2, "deliveryday");
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
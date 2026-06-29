package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WRegionwarehouseServicedorganizationsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WRegionwarehouseServicedorganizationsExample() {
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

        public Criteria andWarehouseidIsNull() {
            addCriterion("WarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseidIsNotNull() {
            addCriterion("WarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseidEqualTo(String value) {
            addCriterion("WarehouseId =", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotEqualTo(String value) {
            addCriterion("WarehouseId <>", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidGreaterThan(String value) {
            addCriterion("WarehouseId >", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("WarehouseId >=", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLessThan(String value) {
            addCriterion("WarehouseId <", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLessThanOrEqualTo(String value) {
            addCriterion("WarehouseId <=", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLike(String value) {
            addCriterion("WarehouseId like", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotLike(String value) {
            addCriterion("WarehouseId not like", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidIn(List<String> values) {
            addCriterion("WarehouseId in", values, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotIn(List<String> values) {
            addCriterion("WarehouseId not in", values, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidBetween(String value1, String value2) {
            addCriterion("WarehouseId between", value1, value2, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotBetween(String value1, String value2) {
            addCriterion("WarehouseId not between", value1, value2, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNull() {
            addCriterion("OrgId is null");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNotNull() {
            addCriterion("OrgId is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidEqualTo(String value) {
            addCriterion("OrgId =", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotEqualTo(String value) {
            addCriterion("OrgId <>", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThan(String value) {
            addCriterion("OrgId >", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThanOrEqualTo(String value) {
            addCriterion("OrgId >=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThan(String value) {
            addCriterion("OrgId <", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThanOrEqualTo(String value) {
            addCriterion("OrgId <=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLike(String value) {
            addCriterion("OrgId like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotLike(String value) {
            addCriterion("OrgId not like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidIn(List<String> values) {
            addCriterion("OrgId in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotIn(List<String> values) {
            addCriterion("OrgId not in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidBetween(String value1, String value2) {
            addCriterion("OrgId between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotBetween(String value1, String value2) {
            addCriterion("OrgId not between", value1, value2, "orgid");
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

        public Criteria andExpinvSwitchIsNull() {
            addCriterion("ExpInv_Switch is null");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchIsNotNull() {
            addCriterion("ExpInv_Switch is not null");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchEqualTo(String value) {
            addCriterion("ExpInv_Switch =", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchNotEqualTo(String value) {
            addCriterion("ExpInv_Switch <>", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchGreaterThan(String value) {
            addCriterion("ExpInv_Switch >", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchGreaterThanOrEqualTo(String value) {
            addCriterion("ExpInv_Switch >=", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchLessThan(String value) {
            addCriterion("ExpInv_Switch <", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchLessThanOrEqualTo(String value) {
            addCriterion("ExpInv_Switch <=", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchLike(String value) {
            addCriterion("ExpInv_Switch like", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchNotLike(String value) {
            addCriterion("ExpInv_Switch not like", value, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchIn(List<String> values) {
            addCriterion("ExpInv_Switch in", values, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchNotIn(List<String> values) {
            addCriterion("ExpInv_Switch not in", values, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchBetween(String value1, String value2) {
            addCriterion("ExpInv_Switch between", value1, value2, "expinvSwitch");
            return (Criteria) this;
        }

        public Criteria andExpinvSwitchNotBetween(String value1, String value2) {
            addCriterion("ExpInv_Switch not between", value1, value2, "expinvSwitch");
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
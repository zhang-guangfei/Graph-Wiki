package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class MtWarehousedeptconfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MtWarehousedeptconfigExample() {
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

        public Criteria andDeptnotwoIsNull() {
            addCriterion("deptNoTwo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoIsNotNull() {
            addCriterion("deptNoTwo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoEqualTo(String value) {
            addCriterion("deptNoTwo =", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoNotEqualTo(String value) {
            addCriterion("deptNoTwo <>", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoGreaterThan(String value) {
            addCriterion("deptNoTwo >", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoGreaterThanOrEqualTo(String value) {
            addCriterion("deptNoTwo >=", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoLessThan(String value) {
            addCriterion("deptNoTwo <", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoLessThanOrEqualTo(String value) {
            addCriterion("deptNoTwo <=", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoLike(String value) {
            addCriterion("deptNoTwo like", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoNotLike(String value) {
            addCriterion("deptNoTwo not like", value, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoIn(List<String> values) {
            addCriterion("deptNoTwo in", values, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoNotIn(List<String> values) {
            addCriterion("deptNoTwo not in", values, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoBetween(String value1, String value2) {
            addCriterion("deptNoTwo between", value1, value2, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnotwoNotBetween(String value1, String value2) {
            addCriterion("deptNoTwo not between", value1, value2, "deptnotwo");
            return (Criteria) this;
        }

        public Criteria andDeptnameIsNull() {
            addCriterion("deptName is null");
            return (Criteria) this;
        }

        public Criteria andDeptnameIsNotNull() {
            addCriterion("deptName is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnameEqualTo(String value) {
            addCriterion("deptName =", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotEqualTo(String value) {
            addCriterion("deptName <>", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameGreaterThan(String value) {
            addCriterion("deptName >", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameGreaterThanOrEqualTo(String value) {
            addCriterion("deptName >=", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLessThan(String value) {
            addCriterion("deptName <", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLessThanOrEqualTo(String value) {
            addCriterion("deptName <=", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLike(String value) {
            addCriterion("deptName like", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotLike(String value) {
            addCriterion("deptName not like", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameIn(List<String> values) {
            addCriterion("deptName in", values, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotIn(List<String> values) {
            addCriterion("deptName not in", values, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameBetween(String value1, String value2) {
            addCriterion("deptName between", value1, value2, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotBetween(String value1, String value2) {
            addCriterion("deptName not between", value1, value2, "deptname");
            return (Criteria) this;
        }

        public Criteria andWarehouseIsNull() {
            addCriterion("warehouse is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseIsNotNull() {
            addCriterion("warehouse is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseEqualTo(String value) {
            addCriterion("warehouse =", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseNotEqualTo(String value) {
            addCriterion("warehouse <>", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseGreaterThan(String value) {
            addCriterion("warehouse >", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse >=", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseLessThan(String value) {
            addCriterion("warehouse <", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseLessThanOrEqualTo(String value) {
            addCriterion("warehouse <=", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseLike(String value) {
            addCriterion("warehouse like", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseNotLike(String value) {
            addCriterion("warehouse not like", value, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseIn(List<String> values) {
            addCriterion("warehouse in", values, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseNotIn(List<String> values) {
            addCriterion("warehouse not in", values, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseBetween(String value1, String value2) {
            addCriterion("warehouse between", value1, value2, "warehouse");
            return (Criteria) this;
        }

        public Criteria andWarehouseNotBetween(String value1, String value2) {
            addCriterion("warehouse not between", value1, value2, "warehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseIsNull() {
            addCriterion("newWarehouse is null");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseIsNotNull() {
            addCriterion("newWarehouse is not null");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseEqualTo(String value) {
            addCriterion("newWarehouse =", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseNotEqualTo(String value) {
            addCriterion("newWarehouse <>", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseGreaterThan(String value) {
            addCriterion("newWarehouse >", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("newWarehouse >=", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseLessThan(String value) {
            addCriterion("newWarehouse <", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseLessThanOrEqualTo(String value) {
            addCriterion("newWarehouse <=", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseLike(String value) {
            addCriterion("newWarehouse like", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseNotLike(String value) {
            addCriterion("newWarehouse not like", value, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseIn(List<String> values) {
            addCriterion("newWarehouse in", values, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseNotIn(List<String> values) {
            addCriterion("newWarehouse not in", values, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseBetween(String value1, String value2) {
            addCriterion("newWarehouse between", value1, value2, "newwarehouse");
            return (Criteria) this;
        }

        public Criteria andNewwarehouseNotBetween(String value1, String value2) {
            addCriterion("newWarehouse not between", value1, value2, "newwarehouse");
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
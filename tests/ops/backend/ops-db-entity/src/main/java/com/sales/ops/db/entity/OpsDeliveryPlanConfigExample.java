package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsDeliveryPlanConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsDeliveryPlanConfigExample() {
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

        public Criteria andCurrentCycleIsNull() {
            addCriterion("current_cycle is null");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleIsNotNull() {
            addCriterion("current_cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleEqualTo(String value) {
            addCriterion("current_cycle =", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotEqualTo(String value) {
            addCriterion("current_cycle <>", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleGreaterThan(String value) {
            addCriterion("current_cycle >", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleGreaterThanOrEqualTo(String value) {
            addCriterion("current_cycle >=", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLessThan(String value) {
            addCriterion("current_cycle <", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLessThanOrEqualTo(String value) {
            addCriterion("current_cycle <=", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLike(String value) {
            addCriterion("current_cycle like", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotLike(String value) {
            addCriterion("current_cycle not like", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleIn(List<String> values) {
            addCriterion("current_cycle in", values, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotIn(List<String> values) {
            addCriterion("current_cycle not in", values, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleBetween(String value1, String value2) {
            addCriterion("current_cycle between", value1, value2, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotBetween(String value1, String value2) {
            addCriterion("current_cycle not between", value1, value2, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeIsNull() {
            addCriterion("cycle_describe is null");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeIsNotNull() {
            addCriterion("cycle_describe is not null");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeEqualTo(String value) {
            addCriterion("cycle_describe =", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeNotEqualTo(String value) {
            addCriterion("cycle_describe <>", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeGreaterThan(String value) {
            addCriterion("cycle_describe >", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("cycle_describe >=", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeLessThan(String value) {
            addCriterion("cycle_describe <", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeLessThanOrEqualTo(String value) {
            addCriterion("cycle_describe <=", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeLike(String value) {
            addCriterion("cycle_describe like", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeNotLike(String value) {
            addCriterion("cycle_describe not like", value, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeIn(List<String> values) {
            addCriterion("cycle_describe in", values, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeNotIn(List<String> values) {
            addCriterion("cycle_describe not in", values, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeBetween(String value1, String value2) {
            addCriterion("cycle_describe between", value1, value2, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCycleDescribeNotBetween(String value1, String value2) {
            addCriterion("cycle_describe not between", value1, value2, "cycleDescribe");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaIsNull() {
            addCriterion("calculation_formula is null");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaIsNotNull() {
            addCriterion("calculation_formula is not null");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaEqualTo(String value) {
            addCriterion("calculation_formula =", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaNotEqualTo(String value) {
            addCriterion("calculation_formula <>", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaGreaterThan(String value) {
            addCriterion("calculation_formula >", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaGreaterThanOrEqualTo(String value) {
            addCriterion("calculation_formula >=", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaLessThan(String value) {
            addCriterion("calculation_formula <", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaLessThanOrEqualTo(String value) {
            addCriterion("calculation_formula <=", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaLike(String value) {
            addCriterion("calculation_formula like", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaNotLike(String value) {
            addCriterion("calculation_formula not like", value, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaIn(List<String> values) {
            addCriterion("calculation_formula in", values, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaNotIn(List<String> values) {
            addCriterion("calculation_formula not in", values, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaBetween(String value1, String value2) {
            addCriterion("calculation_formula between", value1, value2, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andCalculationFormulaNotBetween(String value1, String value2) {
            addCriterion("calculation_formula not between", value1, value2, "calculationFormula");
            return (Criteria) this;
        }

        public Criteria andDaysIsNull() {
            addCriterion("days is null");
            return (Criteria) this;
        }

        public Criteria andDaysIsNotNull() {
            addCriterion("days is not null");
            return (Criteria) this;
        }

        public Criteria andDaysEqualTo(Integer value) {
            addCriterion("days =", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotEqualTo(Integer value) {
            addCriterion("days <>", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThan(Integer value) {
            addCriterion("days >", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("days >=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThan(Integer value) {
            addCriterion("days <", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThanOrEqualTo(Integer value) {
            addCriterion("days <=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysIn(List<Integer> values) {
            addCriterion("days in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotIn(List<Integer> values) {
            addCriterion("days not in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysBetween(Integer value1, Integer value2) {
            addCriterion("days between", value1, value2, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("days not between", value1, value2, "days");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNull() {
            addCriterion("workDay is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNotNull() {
            addCriterion("workDay is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayEqualTo(Integer value) {
            addCriterion("workDay =", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotEqualTo(Integer value) {
            addCriterion("workDay <>", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThan(Integer value) {
            addCriterion("workDay >", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("workDay >=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThan(Integer value) {
            addCriterion("workDay <", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThanOrEqualTo(Integer value) {
            addCriterion("workDay <=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayIn(List<Integer> values) {
            addCriterion("workDay in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotIn(List<Integer> values) {
            addCriterion("workDay not in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayBetween(Integer value1, Integer value2) {
            addCriterion("workDay between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotBetween(Integer value1, Integer value2) {
            addCriterion("workDay not between", value1, value2, "workday");
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

        public Criteria andReliabilityIsNull() {
            addCriterion("reliability is null");
            return (Criteria) this;
        }

        public Criteria andReliabilityIsNotNull() {
            addCriterion("reliability is not null");
            return (Criteria) this;
        }

        public Criteria andReliabilityEqualTo(String value) {
            addCriterion("reliability =", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotEqualTo(String value) {
            addCriterion("reliability <>", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityGreaterThan(String value) {
            addCriterion("reliability >", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityGreaterThanOrEqualTo(String value) {
            addCriterion("reliability >=", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityLessThan(String value) {
            addCriterion("reliability <", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityLessThanOrEqualTo(String value) {
            addCriterion("reliability <=", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityLike(String value) {
            addCriterion("reliability like", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotLike(String value) {
            addCriterion("reliability not like", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityIn(List<String> values) {
            addCriterion("reliability in", values, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotIn(List<String> values) {
            addCriterion("reliability not in", values, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityBetween(String value1, String value2) {
            addCriterion("reliability between", value1, value2, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotBetween(String value1, String value2) {
            addCriterion("reliability not between", value1, value2, "reliability");
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
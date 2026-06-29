package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsDrawupinventoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsDrawupinventoryExample() {
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

        public Criteria andBathidIsNull() {
            addCriterion("bathid is null");
            return (Criteria) this;
        }

        public Criteria andBathidIsNotNull() {
            addCriterion("bathid is not null");
            return (Criteria) this;
        }

        public Criteria andBathidEqualTo(String value) {
            addCriterion("bathid =", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidNotEqualTo(String value) {
            addCriterion("bathid <>", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidGreaterThan(String value) {
            addCriterion("bathid >", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidGreaterThanOrEqualTo(String value) {
            addCriterion("bathid >=", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidLessThan(String value) {
            addCriterion("bathid <", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidLessThanOrEqualTo(String value) {
            addCriterion("bathid <=", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidLike(String value) {
            addCriterion("bathid like", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidNotLike(String value) {
            addCriterion("bathid not like", value, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidIn(List<String> values) {
            addCriterion("bathid in", values, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidNotIn(List<String> values) {
            addCriterion("bathid not in", values, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidBetween(String value1, String value2) {
            addCriterion("bathid between", value1, value2, "bathid");
            return (Criteria) this;
        }

        public Criteria andBathidNotBetween(String value1, String value2) {
            addCriterion("bathid not between", value1, value2, "bathid");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
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

        public Criteria andPracticalityqtyIsNull() {
            addCriterion("practicalityqty is null");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyIsNotNull() {
            addCriterion("practicalityqty is not null");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyEqualTo(Integer value) {
            addCriterion("practicalityqty =", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyNotEqualTo(Integer value) {
            addCriterion("practicalityqty <>", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyGreaterThan(Integer value) {
            addCriterion("practicalityqty >", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("practicalityqty >=", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyLessThan(Integer value) {
            addCriterion("practicalityqty <", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyLessThanOrEqualTo(Integer value) {
            addCriterion("practicalityqty <=", value, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyIn(List<Integer> values) {
            addCriterion("practicalityqty in", values, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyNotIn(List<Integer> values) {
            addCriterion("practicalityqty not in", values, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyBetween(Integer value1, Integer value2) {
            addCriterion("practicalityqty between", value1, value2, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andPracticalityqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("practicalityqty not between", value1, value2, "practicalityqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyIsNull() {
            addCriterion("differenceqty is null");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyIsNotNull() {
            addCriterion("differenceqty is not null");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyEqualTo(Integer value) {
            addCriterion("differenceqty =", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyNotEqualTo(Integer value) {
            addCriterion("differenceqty <>", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyGreaterThan(Integer value) {
            addCriterion("differenceqty >", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("differenceqty >=", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyLessThan(Integer value) {
            addCriterion("differenceqty <", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyLessThanOrEqualTo(Integer value) {
            addCriterion("differenceqty <=", value, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyIn(List<Integer> values) {
            addCriterion("differenceqty in", values, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyNotIn(List<Integer> values) {
            addCriterion("differenceqty not in", values, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyBetween(Integer value1, Integer value2) {
            addCriterion("differenceqty between", value1, value2, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andDifferenceqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("differenceqty not between", value1, value2, "differenceqty");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNull() {
            addCriterion("qa_status is null");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNotNull() {
            addCriterion("qa_status is not null");
            return (Criteria) this;
        }

        public Criteria andQaStatusEqualTo(Integer value) {
            addCriterion("qa_status =", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotEqualTo(Integer value) {
            addCriterion("qa_status <>", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThan(Integer value) {
            addCriterion("qa_status >", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("qa_status >=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThan(Integer value) {
            addCriterion("qa_status <", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThanOrEqualTo(Integer value) {
            addCriterion("qa_status <=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusIn(List<Integer> values) {
            addCriterion("qa_status in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotIn(List<Integer> values) {
            addCriterion("qa_status not in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusBetween(Integer value1, Integer value2) {
            addCriterion("qa_status between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("qa_status not between", value1, value2, "qaStatus");
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
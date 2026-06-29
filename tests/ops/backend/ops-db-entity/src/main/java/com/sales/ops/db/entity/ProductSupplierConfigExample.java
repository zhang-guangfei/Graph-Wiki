package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductSupplierConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductSupplierConfigExample() {
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

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andStddlvdateIsNull() {
            addCriterion("stddlvdate is null");
            return (Criteria) this;
        }

        public Criteria andStddlvdateIsNotNull() {
            addCriterion("stddlvdate is not null");
            return (Criteria) this;
        }

        public Criteria andStddlvdateEqualTo(Integer value) {
            addCriterion("stddlvdate =", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateNotEqualTo(Integer value) {
            addCriterion("stddlvdate <>", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateGreaterThan(Integer value) {
            addCriterion("stddlvdate >", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateGreaterThanOrEqualTo(Integer value) {
            addCriterion("stddlvdate >=", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateLessThan(Integer value) {
            addCriterion("stddlvdate <", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateLessThanOrEqualTo(Integer value) {
            addCriterion("stddlvdate <=", value, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateIn(List<Integer> values) {
            addCriterion("stddlvdate in", values, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateNotIn(List<Integer> values) {
            addCriterion("stddlvdate not in", values, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateBetween(Integer value1, Integer value2) {
            addCriterion("stddlvdate between", value1, value2, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andStddlvdateNotBetween(Integer value1, Integer value2) {
            addCriterion("stddlvdate not between", value1, value2, "stddlvdate");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIsNull() {
            addCriterion("maxProdQty is null");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIsNotNull() {
            addCriterion("maxProdQty is not null");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyEqualTo(Integer value) {
            addCriterion("maxProdQty =", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotEqualTo(Integer value) {
            addCriterion("maxProdQty <>", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyGreaterThan(Integer value) {
            addCriterion("maxProdQty >", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxProdQty >=", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyLessThan(Integer value) {
            addCriterion("maxProdQty <", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyLessThanOrEqualTo(Integer value) {
            addCriterion("maxProdQty <=", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIn(List<Integer> values) {
            addCriterion("maxProdQty in", values, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotIn(List<Integer> values) {
            addCriterion("maxProdQty not in", values, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyBetween(Integer value1, Integer value2) {
            addCriterion("maxProdQty between", value1, value2, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("maxProdQty not between", value1, value2, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIsNull() {
            addCriterion("enableMaxProdQty is null");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIsNotNull() {
            addCriterion("enableMaxProdQty is not null");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty =", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty <>", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyGreaterThan(Boolean value) {
            addCriterion("enableMaxProdQty >", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty >=", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyLessThan(Boolean value) {
            addCriterion("enableMaxProdQty <", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyLessThanOrEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty <=", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIn(List<Boolean> values) {
            addCriterion("enableMaxProdQty in", values, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotIn(List<Boolean> values) {
            addCriterion("enableMaxProdQty not in", values, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyBetween(Boolean value1, Boolean value2) {
            addCriterion("enableMaxProdQty between", value1, value2, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enableMaxProdQty not between", value1, value2, "enablemaxprodqty");
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
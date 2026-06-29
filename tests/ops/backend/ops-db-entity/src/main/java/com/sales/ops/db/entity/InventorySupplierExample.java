package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventorySupplierExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventorySupplierExample() {
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

        public Criteria andQuantityassemblyIsNull() {
            addCriterion("quantityAssembly is null");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyIsNotNull() {
            addCriterion("quantityAssembly is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyEqualTo(Integer value) {
            addCriterion("quantityAssembly =", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyNotEqualTo(Integer value) {
            addCriterion("quantityAssembly <>", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyGreaterThan(Integer value) {
            addCriterion("quantityAssembly >", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantityAssembly >=", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyLessThan(Integer value) {
            addCriterion("quantityAssembly <", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyLessThanOrEqualTo(Integer value) {
            addCriterion("quantityAssembly <=", value, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyIn(List<Integer> values) {
            addCriterion("quantityAssembly in", values, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyNotIn(List<Integer> values) {
            addCriterion("quantityAssembly not in", values, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyBetween(Integer value1, Integer value2) {
            addCriterion("quantityAssembly between", value1, value2, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityassemblyNotBetween(Integer value1, Integer value2) {
            addCriterion("quantityAssembly not between", value1, value2, "quantityassembly");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceIsNull() {
            addCriterion("quantityProduce is null");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceIsNotNull() {
            addCriterion("quantityProduce is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceEqualTo(Integer value) {
            addCriterion("quantityProduce =", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceNotEqualTo(Integer value) {
            addCriterion("quantityProduce <>", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceGreaterThan(Integer value) {
            addCriterion("quantityProduce >", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantityProduce >=", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceLessThan(Integer value) {
            addCriterion("quantityProduce <", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceLessThanOrEqualTo(Integer value) {
            addCriterion("quantityProduce <=", value, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceIn(List<Integer> values) {
            addCriterion("quantityProduce in", values, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceNotIn(List<Integer> values) {
            addCriterion("quantityProduce not in", values, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceBetween(Integer value1, Integer value2) {
            addCriterion("quantityProduce between", value1, value2, "quantityproduce");
            return (Criteria) this;
        }

        public Criteria andQuantityproduceNotBetween(Integer value1, Integer value2) {
            addCriterion("quantityProduce not between", value1, value2, "quantityproduce");
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

        public Criteria andQuantityprepareIsNull() {
            addCriterion("quantityPrepare is null");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareIsNotNull() {
            addCriterion("quantityPrepare is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareEqualTo(Integer value) {
            addCriterion("quantityPrepare =", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareNotEqualTo(Integer value) {
            addCriterion("quantityPrepare <>", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareGreaterThan(Integer value) {
            addCriterion("quantityPrepare >", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantityPrepare >=", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareLessThan(Integer value) {
            addCriterion("quantityPrepare <", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareLessThanOrEqualTo(Integer value) {
            addCriterion("quantityPrepare <=", value, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareIn(List<Integer> values) {
            addCriterion("quantityPrepare in", values, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareNotIn(List<Integer> values) {
            addCriterion("quantityPrepare not in", values, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareBetween(Integer value1, Integer value2) {
            addCriterion("quantityPrepare between", value1, value2, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andQuantityprepareNotBetween(Integer value1, Integer value2) {
            addCriterion("quantityPrepare not between", value1, value2, "quantityprepare");
            return (Criteria) this;
        }

        public Criteria andBinflagIsNull() {
            addCriterion("binflag is null");
            return (Criteria) this;
        }

        public Criteria andBinflagIsNotNull() {
            addCriterion("binflag is not null");
            return (Criteria) this;
        }

        public Criteria andBinflagEqualTo(Integer value) {
            addCriterion("binflag =", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagNotEqualTo(Integer value) {
            addCriterion("binflag <>", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagGreaterThan(Integer value) {
            addCriterion("binflag >", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("binflag >=", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagLessThan(Integer value) {
            addCriterion("binflag <", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagLessThanOrEqualTo(Integer value) {
            addCriterion("binflag <=", value, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagIn(List<Integer> values) {
            addCriterion("binflag in", values, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagNotIn(List<Integer> values) {
            addCriterion("binflag not in", values, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagBetween(Integer value1, Integer value2) {
            addCriterion("binflag between", value1, value2, "binflag");
            return (Criteria) this;
        }

        public Criteria andBinflagNotBetween(Integer value1, Integer value2) {
            addCriterion("binflag not between", value1, value2, "binflag");
            return (Criteria) this;
        }

        public Criteria andInventoryClassIsNull() {
            addCriterion("inventory_class is null");
            return (Criteria) this;
        }

        public Criteria andInventoryClassIsNotNull() {
            addCriterion("inventory_class is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryClassEqualTo(String value) {
            addCriterion("inventory_class =", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassNotEqualTo(String value) {
            addCriterion("inventory_class <>", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassGreaterThan(String value) {
            addCriterion("inventory_class >", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_class >=", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassLessThan(String value) {
            addCriterion("inventory_class <", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassLessThanOrEqualTo(String value) {
            addCriterion("inventory_class <=", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassLike(String value) {
            addCriterion("inventory_class like", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassNotLike(String value) {
            addCriterion("inventory_class not like", value, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassIn(List<String> values) {
            addCriterion("inventory_class in", values, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassNotIn(List<String> values) {
            addCriterion("inventory_class not in", values, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassBetween(String value1, String value2) {
            addCriterion("inventory_class between", value1, value2, "inventoryClass");
            return (Criteria) this;
        }

        public Criteria andInventoryClassNotBetween(String value1, String value2) {
            addCriterion("inventory_class not between", value1, value2, "inventoryClass");
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
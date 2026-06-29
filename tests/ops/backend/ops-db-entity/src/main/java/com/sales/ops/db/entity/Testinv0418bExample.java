package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class Testinv0418bExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Testinv0418bExample() {
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

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Integer value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Integer value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Integer value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Integer value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Integer value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Integer> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Integer> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Integer value1, Integer value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyIsNull() {
            addCriterion("specQty is null");
            return (Criteria) this;
        }

        public Criteria andSpecqtyIsNotNull() {
            addCriterion("specQty is not null");
            return (Criteria) this;
        }

        public Criteria andSpecqtyEqualTo(Integer value) {
            addCriterion("specQty =", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyNotEqualTo(Integer value) {
            addCriterion("specQty <>", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyGreaterThan(Integer value) {
            addCriterion("specQty >", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("specQty >=", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyLessThan(Integer value) {
            addCriterion("specQty <", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyLessThanOrEqualTo(Integer value) {
            addCriterion("specQty <=", value, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyIn(List<Integer> values) {
            addCriterion("specQty in", values, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyNotIn(List<Integer> values) {
            addCriterion("specQty not in", values, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyBetween(Integer value1, Integer value2) {
            addCriterion("specQty between", value1, value2, "specqty");
            return (Criteria) this;
        }

        public Criteria andSpecqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("specQty not between", value1, value2, "specqty");
            return (Criteria) this;
        }

        public Criteria andQtybinIsNull() {
            addCriterion("qtybin is null");
            return (Criteria) this;
        }

        public Criteria andQtybinIsNotNull() {
            addCriterion("qtybin is not null");
            return (Criteria) this;
        }

        public Criteria andQtybinEqualTo(Integer value) {
            addCriterion("qtybin =", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotEqualTo(Integer value) {
            addCriterion("qtybin <>", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinGreaterThan(Integer value) {
            addCriterion("qtybin >", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtybin >=", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinLessThan(Integer value) {
            addCriterion("qtybin <", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinLessThanOrEqualTo(Integer value) {
            addCriterion("qtybin <=", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinIn(List<Integer> values) {
            addCriterion("qtybin in", values, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotIn(List<Integer> values) {
            addCriterion("qtybin not in", values, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinBetween(Integer value1, Integer value2) {
            addCriterion("qtybin between", value1, value2, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotBetween(Integer value1, Integer value2) {
            addCriterion("qtybin not between", value1, value2, "qtybin");
            return (Criteria) this;
        }

        public Criteria andBincellIsNull() {
            addCriterion("bincell is null");
            return (Criteria) this;
        }

        public Criteria andBincellIsNotNull() {
            addCriterion("bincell is not null");
            return (Criteria) this;
        }

        public Criteria andBincellEqualTo(Integer value) {
            addCriterion("bincell =", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotEqualTo(Integer value) {
            addCriterion("bincell <>", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellGreaterThan(Integer value) {
            addCriterion("bincell >", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellGreaterThanOrEqualTo(Integer value) {
            addCriterion("bincell >=", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellLessThan(Integer value) {
            addCriterion("bincell <", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellLessThanOrEqualTo(Integer value) {
            addCriterion("bincell <=", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellIn(List<Integer> values) {
            addCriterion("bincell in", values, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotIn(List<Integer> values) {
            addCriterion("bincell not in", values, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellBetween(Integer value1, Integer value2) {
            addCriterion("bincell between", value1, value2, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotBetween(Integer value1, Integer value2) {
            addCriterion("bincell not between", value1, value2, "bincell");
            return (Criteria) this;
        }

        public Criteria andOverqtyIsNull() {
            addCriterion("overqty is null");
            return (Criteria) this;
        }

        public Criteria andOverqtyIsNotNull() {
            addCriterion("overqty is not null");
            return (Criteria) this;
        }

        public Criteria andOverqtyEqualTo(Integer value) {
            addCriterion("overqty =", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyNotEqualTo(Integer value) {
            addCriterion("overqty <>", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyGreaterThan(Integer value) {
            addCriterion("overqty >", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("overqty >=", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyLessThan(Integer value) {
            addCriterion("overqty <", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyLessThanOrEqualTo(Integer value) {
            addCriterion("overqty <=", value, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyIn(List<Integer> values) {
            addCriterion("overqty in", values, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyNotIn(List<Integer> values) {
            addCriterion("overqty not in", values, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyBetween(Integer value1, Integer value2) {
            addCriterion("overqty between", value1, value2, "overqty");
            return (Criteria) this;
        }

        public Criteria andOverqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("overqty not between", value1, value2, "overqty");
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
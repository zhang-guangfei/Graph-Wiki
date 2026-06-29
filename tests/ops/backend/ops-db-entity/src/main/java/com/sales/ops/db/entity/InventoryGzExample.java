package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InventoryGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryGzExample() {
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

        public Criteria andStockcodeIsNull() {
            addCriterion("stockCode is null");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNotNull() {
            addCriterion("stockCode is not null");
            return (Criteria) this;
        }

        public Criteria andStockcodeEqualTo(String value) {
            addCriterion("stockCode =", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotEqualTo(String value) {
            addCriterion("stockCode <>", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThan(String value) {
            addCriterion("stockCode >", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThanOrEqualTo(String value) {
            addCriterion("stockCode >=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThan(String value) {
            addCriterion("stockCode <", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThanOrEqualTo(String value) {
            addCriterion("stockCode <=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLike(String value) {
            addCriterion("stockCode like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotLike(String value) {
            addCriterion("stockCode not like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeIn(List<String> values) {
            addCriterion("stockCode in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotIn(List<String> values) {
            addCriterion("stockCode not in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeBetween(String value1, String value2) {
            addCriterion("stockCode between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotBetween(String value1, String value2) {
            addCriterion("stockCode not between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNull() {
            addCriterion("qtyonhand is null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNotNull() {
            addCriterion("qtyonhand is not null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandEqualTo(BigDecimal value) {
            addCriterion("qtyonhand =", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotEqualTo(BigDecimal value) {
            addCriterion("qtyonhand <>", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThan(BigDecimal value) {
            addCriterion("qtyonhand >", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("qtyonhand >=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThan(BigDecimal value) {
            addCriterion("qtyonhand <", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThanOrEqualTo(BigDecimal value) {
            addCriterion("qtyonhand <=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIn(List<BigDecimal> values) {
            addCriterion("qtyonhand in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotIn(List<BigDecimal> values) {
            addCriterion("qtyonhand not in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qtyonhand between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qtyonhand not between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIsNull() {
            addCriterion("qtyprepare is null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIsNotNull() {
            addCriterion("qtyprepare is not null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareEqualTo(BigDecimal value) {
            addCriterion("qtyprepare =", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotEqualTo(BigDecimal value) {
            addCriterion("qtyprepare <>", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThan(BigDecimal value) {
            addCriterion("qtyprepare >", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("qtyprepare >=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThan(BigDecimal value) {
            addCriterion("qtyprepare <", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThanOrEqualTo(BigDecimal value) {
            addCriterion("qtyprepare <=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIn(List<BigDecimal> values) {
            addCriterion("qtyprepare in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotIn(List<BigDecimal> values) {
            addCriterion("qtyprepare not in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qtyprepare between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qtyprepare not between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Long value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Long value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Long value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Long value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Long> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Long> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Long value1, Long value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
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
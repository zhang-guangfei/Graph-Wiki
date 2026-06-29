package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class DoConfirmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DoConfirmExample() {
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

        public Criteria andGatherWarehouseCodeIsNull() {
            addCriterion("gather_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIsNotNull() {
            addCriterion("gather_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeEqualTo(String value) {
            addCriterion("gather_warehouse_code =", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotEqualTo(String value) {
            addCriterion("gather_warehouse_code <>", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThan(String value) {
            addCriterion("gather_warehouse_code >", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code >=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThan(String value) {
            addCriterion("gather_warehouse_code <", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code <=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLike(String value) {
            addCriterion("gather_warehouse_code like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotLike(String value) {
            addCriterion("gather_warehouse_code not like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIn(List<String> values) {
            addCriterion("gather_warehouse_code in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotIn(List<String> values) {
            addCriterion("gather_warehouse_code not in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code not between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeIsNull() {
            addCriterion("deliveryOrderCode is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeIsNotNull() {
            addCriterion("deliveryOrderCode is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeEqualTo(String value) {
            addCriterion("deliveryOrderCode =", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeNotEqualTo(String value) {
            addCriterion("deliveryOrderCode <>", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeGreaterThan(String value) {
            addCriterion("deliveryOrderCode >", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeGreaterThanOrEqualTo(String value) {
            addCriterion("deliveryOrderCode >=", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeLessThan(String value) {
            addCriterion("deliveryOrderCode <", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeLessThanOrEqualTo(String value) {
            addCriterion("deliveryOrderCode <=", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeLike(String value) {
            addCriterion("deliveryOrderCode like", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeNotLike(String value) {
            addCriterion("deliveryOrderCode not like", value, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeIn(List<String> values) {
            addCriterion("deliveryOrderCode in", values, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeNotIn(List<String> values) {
            addCriterion("deliveryOrderCode not in", values, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeBetween(String value1, String value2) {
            addCriterion("deliveryOrderCode between", value1, value2, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andDeliveryordercodeNotBetween(String value1, String value2) {
            addCriterion("deliveryOrderCode not between", value1, value2, "deliveryordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeIsNull() {
            addCriterion("wmsOrderCode is null");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeIsNotNull() {
            addCriterion("wmsOrderCode is not null");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeEqualTo(String value) {
            addCriterion("wmsOrderCode =", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeNotEqualTo(String value) {
            addCriterion("wmsOrderCode <>", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeGreaterThan(String value) {
            addCriterion("wmsOrderCode >", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeGreaterThanOrEqualTo(String value) {
            addCriterion("wmsOrderCode >=", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeLessThan(String value) {
            addCriterion("wmsOrderCode <", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeLessThanOrEqualTo(String value) {
            addCriterion("wmsOrderCode <=", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeLike(String value) {
            addCriterion("wmsOrderCode like", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeNotLike(String value) {
            addCriterion("wmsOrderCode not like", value, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeIn(List<String> values) {
            addCriterion("wmsOrderCode in", values, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeNotIn(List<String> values) {
            addCriterion("wmsOrderCode not in", values, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeBetween(String value1, String value2) {
            addCriterion("wmsOrderCode between", value1, value2, "wmsordercode");
            return (Criteria) this;
        }

        public Criteria andWmsordercodeNotBetween(String value1, String value2) {
            addCriterion("wmsOrderCode not between", value1, value2, "wmsordercode");
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
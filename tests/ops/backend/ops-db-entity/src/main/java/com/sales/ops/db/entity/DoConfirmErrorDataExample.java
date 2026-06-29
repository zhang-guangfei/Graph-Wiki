package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoConfirmErrorDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DoConfirmErrorDataExample() {
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

        public Criteria andOutQtyIsNull() {
            addCriterion("out_qty is null");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNotNull() {
            addCriterion("out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOutQtyEqualTo(String value) {
            addCriterion("out_qty =", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotEqualTo(String value) {
            addCriterion("out_qty <>", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThan(String value) {
            addCriterion("out_qty >", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThanOrEqualTo(String value) {
            addCriterion("out_qty >=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThan(String value) {
            addCriterion("out_qty <", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThanOrEqualTo(String value) {
            addCriterion("out_qty <=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLike(String value) {
            addCriterion("out_qty like", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotLike(String value) {
            addCriterion("out_qty not like", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyIn(List<String> values) {
            addCriterion("out_qty in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotIn(List<String> values) {
            addCriterion("out_qty not in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyBetween(String value1, String value2) {
            addCriterion("out_qty between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotBetween(String value1, String value2) {
            addCriterion("out_qty not between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIsNull() {
            addCriterion("delivery_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIsNotNull() {
            addCriterion("delivery_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoEqualTo(String value) {
            addCriterion("delivery_no =", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotEqualTo(String value) {
            addCriterion("delivery_no <>", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThan(String value) {
            addCriterion("delivery_no >", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_no >=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThan(String value) {
            addCriterion("delivery_no <", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLessThanOrEqualTo(String value) {
            addCriterion("delivery_no <=", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoLike(String value) {
            addCriterion("delivery_no like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotLike(String value) {
            addCriterion("delivery_no not like", value, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoIn(List<String> values) {
            addCriterion("delivery_no in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotIn(List<String> values) {
            addCriterion("delivery_no not in", values, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoBetween(String value1, String value2) {
            addCriterion("delivery_no between", value1, value2, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andDeliveryNoNotBetween(String value1, String value2) {
            addCriterion("delivery_no not between", value1, value2, "deliveryNo");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleMsgIsNull() {
            addCriterion("handle_msg is null");
            return (Criteria) this;
        }

        public Criteria andHandleMsgIsNotNull() {
            addCriterion("handle_msg is not null");
            return (Criteria) this;
        }

        public Criteria andHandleMsgEqualTo(String value) {
            addCriterion("handle_msg =", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgNotEqualTo(String value) {
            addCriterion("handle_msg <>", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgGreaterThan(String value) {
            addCriterion("handle_msg >", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgGreaterThanOrEqualTo(String value) {
            addCriterion("handle_msg >=", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgLessThan(String value) {
            addCriterion("handle_msg <", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgLessThanOrEqualTo(String value) {
            addCriterion("handle_msg <=", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgLike(String value) {
            addCriterion("handle_msg like", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgNotLike(String value) {
            addCriterion("handle_msg not like", value, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgIn(List<String> values) {
            addCriterion("handle_msg in", values, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgNotIn(List<String> values) {
            addCriterion("handle_msg not in", values, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgBetween(String value1, String value2) {
            addCriterion("handle_msg between", value1, value2, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andHandleMsgNotBetween(String value1, String value2) {
            addCriterion("handle_msg not between", value1, value2, "handleMsg");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNull() {
            addCriterion("insert_time is null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNotNull() {
            addCriterion("insert_time is not null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeEqualTo(Date value) {
            addCriterion("insert_time =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(Date value) {
            addCriterion("insert_time <>", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThan(Date value) {
            addCriterion("insert_time >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("insert_time >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(Date value) {
            addCriterion("insert_time <", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThanOrEqualTo(Date value) {
            addCriterion("insert_time <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<Date> values) {
            addCriterion("insert_time in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<Date> values) {
            addCriterion("insert_time not in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeBetween(Date value1, Date value2) {
            addCriterion("insert_time between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotBetween(Date value1, Date value2) {
            addCriterion("insert_time not between", value1, value2, "insertTime");
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
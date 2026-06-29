package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsDeliveryPlanResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsDeliveryPlanResultExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNull() {
            addCriterion("order_item is null");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNotNull() {
            addCriterion("order_item is not null");
            return (Criteria) this;
        }

        public Criteria andOrderItemEqualTo(String value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(String value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(String value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(String value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(String value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLike(String value) {
            addCriterion("order_item like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotLike(String value) {
            addCriterion("order_item not like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<String> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<String> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(String value1, String value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(String value1, String value2) {
            addCriterion("order_item not between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNull() {
            addCriterion("do_id is null");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNotNull() {
            addCriterion("do_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoIdEqualTo(String value) {
            addCriterion("do_id =", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotEqualTo(String value) {
            addCriterion("do_id <>", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThan(String value) {
            addCriterion("do_id >", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThanOrEqualTo(String value) {
            addCriterion("do_id >=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThan(String value) {
            addCriterion("do_id <", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThanOrEqualTo(String value) {
            addCriterion("do_id <=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLike(String value) {
            addCriterion("do_id like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotLike(String value) {
            addCriterion("do_id not like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdIn(List<String> values) {
            addCriterion("do_id in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotIn(List<String> values) {
            addCriterion("do_id not in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdBetween(String value1, String value2) {
            addCriterion("do_id between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotBetween(String value1, String value2) {
            addCriterion("do_id not between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayIsNull() {
            addCriterion("expect_delivery_day is null");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayIsNotNull() {
            addCriterion("expect_delivery_day is not null");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayEqualTo(Date value) {
            addCriterionForJDBCDate("expect_delivery_day =", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_delivery_day <>", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_delivery_day >", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_delivery_day >=", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayLessThan(Date value) {
            addCriterionForJDBCDate("expect_delivery_day <", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_delivery_day <=", value, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayIn(List<Date> values) {
            addCriterionForJDBCDate("expect_delivery_day in", values, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_delivery_day not in", values, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_delivery_day between", value1, value2, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andExpectDeliveryDayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_delivery_day not between", value1, value2, "expectDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayIsNull() {
            addCriterion("plan_delivery_day is null");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayIsNotNull() {
            addCriterion("plan_delivery_day is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayEqualTo(Date value) {
            addCriterionForJDBCDate("plan_delivery_day =", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayNotEqualTo(Date value) {
            addCriterionForJDBCDate("plan_delivery_day <>", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayGreaterThan(Date value) {
            addCriterionForJDBCDate("plan_delivery_day >", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_delivery_day >=", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayLessThan(Date value) {
            addCriterionForJDBCDate("plan_delivery_day <", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_delivery_day <=", value, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayIn(List<Date> values) {
            addCriterionForJDBCDate("plan_delivery_day in", values, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayNotIn(List<Date> values) {
            addCriterionForJDBCDate("plan_delivery_day not in", values, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_delivery_day between", value1, value2, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryDayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_delivery_day not between", value1, value2, "planDeliveryDay");
            return (Criteria) this;
        }

        public Criteria andEventSourceIsNull() {
            addCriterion("event_source is null");
            return (Criteria) this;
        }

        public Criteria andEventSourceIsNotNull() {
            addCriterion("event_source is not null");
            return (Criteria) this;
        }

        public Criteria andEventSourceEqualTo(String value) {
            addCriterion("event_source =", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotEqualTo(String value) {
            addCriterion("event_source <>", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceGreaterThan(String value) {
            addCriterion("event_source >", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceGreaterThanOrEqualTo(String value) {
            addCriterion("event_source >=", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLessThan(String value) {
            addCriterion("event_source <", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLessThanOrEqualTo(String value) {
            addCriterion("event_source <=", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLike(String value) {
            addCriterion("event_source like", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotLike(String value) {
            addCriterion("event_source not like", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceIn(List<String> values) {
            addCriterion("event_source in", values, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotIn(List<String> values) {
            addCriterion("event_source not in", values, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceBetween(String value1, String value2) {
            addCriterion("event_source between", value1, value2, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotBetween(String value1, String value2) {
            addCriterion("event_source not between", value1, value2, "eventSource");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIsNull() {
            addCriterion("current_valid is null");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIsNotNull() {
            addCriterion("current_valid is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentValidEqualTo(Integer value) {
            addCriterion("current_valid =", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotEqualTo(Integer value) {
            addCriterion("current_valid <>", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidGreaterThan(Integer value) {
            addCriterion("current_valid >", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_valid >=", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidLessThan(Integer value) {
            addCriterion("current_valid <", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidLessThanOrEqualTo(Integer value) {
            addCriterion("current_valid <=", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIn(List<Integer> values) {
            addCriterion("current_valid in", values, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotIn(List<Integer> values) {
            addCriterion("current_valid not in", values, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidBetween(Integer value1, Integer value2) {
            addCriterion("current_valid between", value1, value2, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotBetween(Integer value1, Integer value2) {
            addCriterion("current_valid not between", value1, value2, "currentValid");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
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

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andExRelationIsNull() {
            addCriterion("ex_relation is null");
            return (Criteria) this;
        }

        public Criteria andExRelationIsNotNull() {
            addCriterion("ex_relation is not null");
            return (Criteria) this;
        }

        public Criteria andExRelationEqualTo(Integer value) {
            addCriterion("ex_relation =", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotEqualTo(Integer value) {
            addCriterion("ex_relation <>", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationGreaterThan(Integer value) {
            addCriterion("ex_relation >", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationGreaterThanOrEqualTo(Integer value) {
            addCriterion("ex_relation >=", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationLessThan(Integer value) {
            addCriterion("ex_relation <", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationLessThanOrEqualTo(Integer value) {
            addCriterion("ex_relation <=", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationIn(List<Integer> values) {
            addCriterion("ex_relation in", values, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotIn(List<Integer> values) {
            addCriterion("ex_relation not in", values, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationBetween(Integer value1, Integer value2) {
            addCriterion("ex_relation between", value1, value2, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotBetween(Integer value1, Integer value2) {
            addCriterion("ex_relation not between", value1, value2, "exRelation");
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

        public Criteria andCurrentCyclePointIsNull() {
            addCriterion("current_cycle_point is null");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointIsNotNull() {
            addCriterion("current_cycle_point is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointEqualTo(String value) {
            addCriterion("current_cycle_point =", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointNotEqualTo(String value) {
            addCriterion("current_cycle_point <>", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointGreaterThan(String value) {
            addCriterion("current_cycle_point >", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointGreaterThanOrEqualTo(String value) {
            addCriterion("current_cycle_point >=", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointLessThan(String value) {
            addCriterion("current_cycle_point <", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointLessThanOrEqualTo(String value) {
            addCriterion("current_cycle_point <=", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointLike(String value) {
            addCriterion("current_cycle_point like", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointNotLike(String value) {
            addCriterion("current_cycle_point not like", value, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointIn(List<String> values) {
            addCriterion("current_cycle_point in", values, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointNotIn(List<String> values) {
            addCriterion("current_cycle_point not in", values, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointBetween(String value1, String value2) {
            addCriterion("current_cycle_point between", value1, value2, "currentCyclePoint");
            return (Criteria) this;
        }

        public Criteria andCurrentCyclePointNotBetween(String value1, String value2) {
            addCriterion("current_cycle_point not between", value1, value2, "currentCyclePoint");
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

        public Criteria andReliabilityEqualTo(Integer value) {
            addCriterion("reliability =", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotEqualTo(Integer value) {
            addCriterion("reliability <>", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityGreaterThan(Integer value) {
            addCriterion("reliability >", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityGreaterThanOrEqualTo(Integer value) {
            addCriterion("reliability >=", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityLessThan(Integer value) {
            addCriterion("reliability <", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityLessThanOrEqualTo(Integer value) {
            addCriterion("reliability <=", value, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityIn(List<Integer> values) {
            addCriterion("reliability in", values, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotIn(List<Integer> values) {
            addCriterion("reliability not in", values, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityBetween(Integer value1, Integer value2) {
            addCriterion("reliability between", value1, value2, "reliability");
            return (Criteria) this;
        }

        public Criteria andReliabilityNotBetween(Integer value1, Integer value2) {
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
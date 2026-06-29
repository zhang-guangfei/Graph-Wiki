package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderStatedetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderStatedetailExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andDateTypeIsNull() {
            addCriterion("date_type is null");
            return (Criteria) this;
        }

        public Criteria andDateTypeIsNotNull() {
            addCriterion("date_type is not null");
            return (Criteria) this;
        }

        public Criteria andDateTypeEqualTo(Integer value) {
            addCriterion("date_type =", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeNotEqualTo(Integer value) {
            addCriterion("date_type <>", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeGreaterThan(Integer value) {
            addCriterion("date_type >", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("date_type >=", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeLessThan(Integer value) {
            addCriterion("date_type <", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeLessThanOrEqualTo(Integer value) {
            addCriterion("date_type <=", value, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeIn(List<Integer> values) {
            addCriterion("date_type in", values, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeNotIn(List<Integer> values) {
            addCriterion("date_type not in", values, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeBetween(Integer value1, Integer value2) {
            addCriterion("date_type between", value1, value2, "dateType");
            return (Criteria) this;
        }

        public Criteria andDateTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("date_type not between", value1, value2, "dateType");
            return (Criteria) this;
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

        public Criteria andDateNameIsNull() {
            addCriterion("date_name is null");
            return (Criteria) this;
        }

        public Criteria andDateNameIsNotNull() {
            addCriterion("date_name is not null");
            return (Criteria) this;
        }

        public Criteria andDateNameEqualTo(String value) {
            addCriterion("date_name =", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameNotEqualTo(String value) {
            addCriterion("date_name <>", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameGreaterThan(String value) {
            addCriterion("date_name >", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameGreaterThanOrEqualTo(String value) {
            addCriterion("date_name >=", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameLessThan(String value) {
            addCriterion("date_name <", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameLessThanOrEqualTo(String value) {
            addCriterion("date_name <=", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameLike(String value) {
            addCriterion("date_name like", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameNotLike(String value) {
            addCriterion("date_name not like", value, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameIn(List<String> values) {
            addCriterion("date_name in", values, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameNotIn(List<String> values) {
            addCriterion("date_name not in", values, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameBetween(String value1, String value2) {
            addCriterion("date_name between", value1, value2, "dateName");
            return (Criteria) this;
        }

        public Criteria andDateNameNotBetween(String value1, String value2) {
            addCriterion("date_name not between", value1, value2, "dateName");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNull() {
            addCriterion("state_code is null");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNotNull() {
            addCriterion("state_code is not null");
            return (Criteria) this;
        }

        public Criteria andStateCodeEqualTo(Integer value) {
            addCriterion("state_code =", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotEqualTo(Integer value) {
            addCriterion("state_code <>", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThan(Integer value) {
            addCriterion("state_code >", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("state_code >=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThan(Integer value) {
            addCriterion("state_code <", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThanOrEqualTo(Integer value) {
            addCriterion("state_code <=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeIn(List<Integer> values) {
            addCriterion("state_code in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotIn(List<Integer> values) {
            addCriterion("state_code not in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeBetween(Integer value1, Integer value2) {
            addCriterion("state_code between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("state_code not between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andChangeTimesIsNull() {
            addCriterion("change_times is null");
            return (Criteria) this;
        }

        public Criteria andChangeTimesIsNotNull() {
            addCriterion("change_times is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTimesEqualTo(Integer value) {
            addCriterion("change_times =", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesNotEqualTo(Integer value) {
            addCriterion("change_times <>", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesGreaterThan(Integer value) {
            addCriterion("change_times >", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_times >=", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesLessThan(Integer value) {
            addCriterion("change_times <", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesLessThanOrEqualTo(Integer value) {
            addCriterion("change_times <=", value, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesIn(List<Integer> values) {
            addCriterion("change_times in", values, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesNotIn(List<Integer> values) {
            addCriterion("change_times not in", values, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesBetween(Integer value1, Integer value2) {
            addCriterion("change_times between", value1, value2, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andChangeTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("change_times not between", value1, value2, "changeTimes");
            return (Criteria) this;
        }

        public Criteria andStateDateIsNull() {
            addCriterion("state_date is null");
            return (Criteria) this;
        }

        public Criteria andStateDateIsNotNull() {
            addCriterion("state_date is not null");
            return (Criteria) this;
        }

        public Criteria andStateDateEqualTo(Date value) {
            addCriterion("state_date =", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotEqualTo(Date value) {
            addCriterion("state_date <>", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateGreaterThan(Date value) {
            addCriterion("state_date >", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("state_date >=", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateLessThan(Date value) {
            addCriterion("state_date <", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateLessThanOrEqualTo(Date value) {
            addCriterion("state_date <=", value, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateIn(List<Date> values) {
            addCriterion("state_date in", values, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotIn(List<Date> values) {
            addCriterion("state_date not in", values, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateBetween(Date value1, Date value2) {
            addCriterion("state_date between", value1, value2, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDateNotBetween(Date value1, Date value2) {
            addCriterion("state_date not between", value1, value2, "stateDate");
            return (Criteria) this;
        }

        public Criteria andStateDesIsNull() {
            addCriterion("state_des is null");
            return (Criteria) this;
        }

        public Criteria andStateDesIsNotNull() {
            addCriterion("state_des is not null");
            return (Criteria) this;
        }

        public Criteria andStateDesEqualTo(String value) {
            addCriterion("state_des =", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotEqualTo(String value) {
            addCriterion("state_des <>", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesGreaterThan(String value) {
            addCriterion("state_des >", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesGreaterThanOrEqualTo(String value) {
            addCriterion("state_des >=", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLessThan(String value) {
            addCriterion("state_des <", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLessThanOrEqualTo(String value) {
            addCriterion("state_des <=", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesLike(String value) {
            addCriterion("state_des like", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotLike(String value) {
            addCriterion("state_des not like", value, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesIn(List<String> values) {
            addCriterion("state_des in", values, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotIn(List<String> values) {
            addCriterion("state_des not in", values, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesBetween(String value1, String value2) {
            addCriterion("state_des between", value1, value2, "stateDes");
            return (Criteria) this;
        }

        public Criteria andStateDesNotBetween(String value1, String value2) {
            addCriterion("state_des not between", value1, value2, "stateDes");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andMindateIsNull() {
            addCriterion("mindate is null");
            return (Criteria) this;
        }

        public Criteria andMindateIsNotNull() {
            addCriterion("mindate is not null");
            return (Criteria) this;
        }

        public Criteria andMindateEqualTo(Date value) {
            addCriterion("mindate =", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateNotEqualTo(Date value) {
            addCriterion("mindate <>", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateGreaterThan(Date value) {
            addCriterion("mindate >", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateGreaterThanOrEqualTo(Date value) {
            addCriterion("mindate >=", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateLessThan(Date value) {
            addCriterion("mindate <", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateLessThanOrEqualTo(Date value) {
            addCriterion("mindate <=", value, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateIn(List<Date> values) {
            addCriterion("mindate in", values, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateNotIn(List<Date> values) {
            addCriterion("mindate not in", values, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateBetween(Date value1, Date value2) {
            addCriterion("mindate between", value1, value2, "mindate");
            return (Criteria) this;
        }

        public Criteria andMindateNotBetween(Date value1, Date value2) {
            addCriterion("mindate not between", value1, value2, "mindate");
            return (Criteria) this;
        }

        public Criteria andMaxdateIsNull() {
            addCriterion("maxdate is null");
            return (Criteria) this;
        }

        public Criteria andMaxdateIsNotNull() {
            addCriterion("maxdate is not null");
            return (Criteria) this;
        }

        public Criteria andMaxdateEqualTo(Date value) {
            addCriterion("maxdate =", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateNotEqualTo(Date value) {
            addCriterion("maxdate <>", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateGreaterThan(Date value) {
            addCriterion("maxdate >", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateGreaterThanOrEqualTo(Date value) {
            addCriterion("maxdate >=", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateLessThan(Date value) {
            addCriterion("maxdate <", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateLessThanOrEqualTo(Date value) {
            addCriterion("maxdate <=", value, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateIn(List<Date> values) {
            addCriterion("maxdate in", values, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateNotIn(List<Date> values) {
            addCriterion("maxdate not in", values, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateBetween(Date value1, Date value2) {
            addCriterion("maxdate between", value1, value2, "maxdate");
            return (Criteria) this;
        }

        public Criteria andMaxdateNotBetween(Date value1, Date value2) {
            addCriterion("maxdate not between", value1, value2, "maxdate");
            return (Criteria) this;
        }

        public Criteria andProviderIsNull() {
            addCriterion("provider is null");
            return (Criteria) this;
        }

        public Criteria andProviderIsNotNull() {
            addCriterion("provider is not null");
            return (Criteria) this;
        }

        public Criteria andProviderEqualTo(String value) {
            addCriterion("provider =", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotEqualTo(String value) {
            addCriterion("provider <>", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThan(String value) {
            addCriterion("provider >", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThanOrEqualTo(String value) {
            addCriterion("provider >=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThan(String value) {
            addCriterion("provider <", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThanOrEqualTo(String value) {
            addCriterion("provider <=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLike(String value) {
            addCriterion("provider like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotLike(String value) {
            addCriterion("provider not like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderIn(List<String> values) {
            addCriterion("provider in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotIn(List<String> values) {
            addCriterion("provider not in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderBetween(String value1, String value2) {
            addCriterion("provider between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotBetween(String value1, String value2) {
            addCriterion("provider not between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andDelaydayIsNull() {
            addCriterion("delayday is null");
            return (Criteria) this;
        }

        public Criteria andDelaydayIsNotNull() {
            addCriterion("delayday is not null");
            return (Criteria) this;
        }

        public Criteria andDelaydayEqualTo(Integer value) {
            addCriterion("delayday =", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayNotEqualTo(Integer value) {
            addCriterion("delayday <>", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayGreaterThan(Integer value) {
            addCriterion("delayday >", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayGreaterThanOrEqualTo(Integer value) {
            addCriterion("delayday >=", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayLessThan(Integer value) {
            addCriterion("delayday <", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayLessThanOrEqualTo(Integer value) {
            addCriterion("delayday <=", value, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayIn(List<Integer> values) {
            addCriterion("delayday in", values, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayNotIn(List<Integer> values) {
            addCriterion("delayday not in", values, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayBetween(Integer value1, Integer value2) {
            addCriterion("delayday between", value1, value2, "delayday");
            return (Criteria) this;
        }

        public Criteria andDelaydayNotBetween(Integer value1, Integer value2) {
            addCriterion("delayday not between", value1, value2, "delayday");
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

        public Criteria andFirstdateIsNull() {
            addCriterion("firstdate is null");
            return (Criteria) this;
        }

        public Criteria andFirstdateIsNotNull() {
            addCriterion("firstdate is not null");
            return (Criteria) this;
        }

        public Criteria andFirstdateEqualTo(Date value) {
            addCriterion("firstdate =", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateNotEqualTo(Date value) {
            addCriterion("firstdate <>", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateGreaterThan(Date value) {
            addCriterion("firstdate >", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateGreaterThanOrEqualTo(Date value) {
            addCriterion("firstdate >=", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateLessThan(Date value) {
            addCriterion("firstdate <", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateLessThanOrEqualTo(Date value) {
            addCriterion("firstdate <=", value, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateIn(List<Date> values) {
            addCriterion("firstdate in", values, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateNotIn(List<Date> values) {
            addCriterion("firstdate not in", values, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateBetween(Date value1, Date value2) {
            addCriterion("firstdate between", value1, value2, "firstdate");
            return (Criteria) this;
        }

        public Criteria andFirstdateNotBetween(Date value1, Date value2) {
            addCriterion("firstdate not between", value1, value2, "firstdate");
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
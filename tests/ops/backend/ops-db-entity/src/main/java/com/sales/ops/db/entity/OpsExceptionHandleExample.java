package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsExceptionHandleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsExceptionHandleExample() {
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

        public Criteria andBusinessTypeIsNull() {
            addCriterion("business_type is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNotNull() {
            addCriterion("business_type is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeEqualTo(String value) {
            addCriterion("business_type =", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotEqualTo(String value) {
            addCriterion("business_type <>", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThan(String value) {
            addCriterion("business_type >", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("business_type >=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThan(String value) {
            addCriterion("business_type <", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThanOrEqualTo(String value) {
            addCriterion("business_type <=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLike(String value) {
            addCriterion("business_type like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotLike(String value) {
            addCriterion("business_type not like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIn(List<String> values) {
            addCriterion("business_type in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotIn(List<String> values) {
            addCriterion("business_type not in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeBetween(String value1, String value2) {
            addCriterion("business_type between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotBetween(String value1, String value2) {
            addCriterion("business_type not between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNull() {
            addCriterion("err_type is null");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNotNull() {
            addCriterion("err_type is not null");
            return (Criteria) this;
        }

        public Criteria andErrTypeEqualTo(String value) {
            addCriterion("err_type =", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotEqualTo(String value) {
            addCriterion("err_type <>", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThan(String value) {
            addCriterion("err_type >", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThanOrEqualTo(String value) {
            addCriterion("err_type >=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThan(String value) {
            addCriterion("err_type <", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThanOrEqualTo(String value) {
            addCriterion("err_type <=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLike(String value) {
            addCriterion("err_type like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotLike(String value) {
            addCriterion("err_type not like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeIn(List<String> values) {
            addCriterion("err_type in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotIn(List<String> values) {
            addCriterion("err_type not in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeBetween(String value1, String value2) {
            addCriterion("err_type between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotBetween(String value1, String value2) {
            addCriterion("err_type not between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andApiNameIsNull() {
            addCriterion("api_name is null");
            return (Criteria) this;
        }

        public Criteria andApiNameIsNotNull() {
            addCriterion("api_name is not null");
            return (Criteria) this;
        }

        public Criteria andApiNameEqualTo(String value) {
            addCriterion("api_name =", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotEqualTo(String value) {
            addCriterion("api_name <>", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameGreaterThan(String value) {
            addCriterion("api_name >", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameGreaterThanOrEqualTo(String value) {
            addCriterion("api_name >=", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLessThan(String value) {
            addCriterion("api_name <", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLessThanOrEqualTo(String value) {
            addCriterion("api_name <=", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLike(String value) {
            addCriterion("api_name like", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotLike(String value) {
            addCriterion("api_name not like", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameIn(List<String> values) {
            addCriterion("api_name in", values, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotIn(List<String> values) {
            addCriterion("api_name not in", values, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameBetween(String value1, String value2) {
            addCriterion("api_name between", value1, value2, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotBetween(String value1, String value2) {
            addCriterion("api_name not between", value1, value2, "apiName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andMsgIdIsNull() {
            addCriterion("msg_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNotNull() {
            addCriterion("msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgIdEqualTo(String value) {
            addCriterion("msg_id =", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotEqualTo(String value) {
            addCriterion("msg_id <>", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThan(String value) {
            addCriterion("msg_id >", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_id >=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThan(String value) {
            addCriterion("msg_id <", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThanOrEqualTo(String value) {
            addCriterion("msg_id <=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLike(String value) {
            addCriterion("msg_id like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotLike(String value) {
            addCriterion("msg_id not like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdIn(List<String> values) {
            addCriterion("msg_id in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotIn(List<String> values) {
            addCriterion("msg_id not in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdBetween(String value1, String value2) {
            addCriterion("msg_id between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotBetween(String value1, String value2) {
            addCriterion("msg_id not between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andInputMsgIsNull() {
            addCriterion("input_msg is null");
            return (Criteria) this;
        }

        public Criteria andInputMsgIsNotNull() {
            addCriterion("input_msg is not null");
            return (Criteria) this;
        }

        public Criteria andInputMsgEqualTo(String value) {
            addCriterion("input_msg =", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgNotEqualTo(String value) {
            addCriterion("input_msg <>", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgGreaterThan(String value) {
            addCriterion("input_msg >", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgGreaterThanOrEqualTo(String value) {
            addCriterion("input_msg >=", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgLessThan(String value) {
            addCriterion("input_msg <", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgLessThanOrEqualTo(String value) {
            addCriterion("input_msg <=", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgLike(String value) {
            addCriterion("input_msg like", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgNotLike(String value) {
            addCriterion("input_msg not like", value, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgIn(List<String> values) {
            addCriterion("input_msg in", values, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgNotIn(List<String> values) {
            addCriterion("input_msg not in", values, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgBetween(String value1, String value2) {
            addCriterion("input_msg between", value1, value2, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andInputMsgNotBetween(String value1, String value2) {
            addCriterion("input_msg not between", value1, value2, "inputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgIsNull() {
            addCriterion("output_msg is null");
            return (Criteria) this;
        }

        public Criteria andOutputMsgIsNotNull() {
            addCriterion("output_msg is not null");
            return (Criteria) this;
        }

        public Criteria andOutputMsgEqualTo(String value) {
            addCriterion("output_msg =", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgNotEqualTo(String value) {
            addCriterion("output_msg <>", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgGreaterThan(String value) {
            addCriterion("output_msg >", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgGreaterThanOrEqualTo(String value) {
            addCriterion("output_msg >=", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgLessThan(String value) {
            addCriterion("output_msg <", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgLessThanOrEqualTo(String value) {
            addCriterion("output_msg <=", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgLike(String value) {
            addCriterion("output_msg like", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgNotLike(String value) {
            addCriterion("output_msg not like", value, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgIn(List<String> values) {
            addCriterion("output_msg in", values, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgNotIn(List<String> values) {
            addCriterion("output_msg not in", values, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgBetween(String value1, String value2) {
            addCriterion("output_msg between", value1, value2, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andOutputMsgNotBetween(String value1, String value2) {
            addCriterion("output_msg not between", value1, value2, "outputMsg");
            return (Criteria) this;
        }

        public Criteria andParameter1IsNull() {
            addCriterion("parameter_1 is null");
            return (Criteria) this;
        }

        public Criteria andParameter1IsNotNull() {
            addCriterion("parameter_1 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter1EqualTo(String value) {
            addCriterion("parameter_1 =", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1NotEqualTo(String value) {
            addCriterion("parameter_1 <>", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1GreaterThan(String value) {
            addCriterion("parameter_1 >", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_1 >=", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1LessThan(String value) {
            addCriterion("parameter_1 <", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1LessThanOrEqualTo(String value) {
            addCriterion("parameter_1 <=", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1Like(String value) {
            addCriterion("parameter_1 like", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1NotLike(String value) {
            addCriterion("parameter_1 not like", value, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1In(List<String> values) {
            addCriterion("parameter_1 in", values, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1NotIn(List<String> values) {
            addCriterion("parameter_1 not in", values, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1Between(String value1, String value2) {
            addCriterion("parameter_1 between", value1, value2, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter1NotBetween(String value1, String value2) {
            addCriterion("parameter_1 not between", value1, value2, "parameter1");
            return (Criteria) this;
        }

        public Criteria andParameter2IsNull() {
            addCriterion("parameter_2 is null");
            return (Criteria) this;
        }

        public Criteria andParameter2IsNotNull() {
            addCriterion("parameter_2 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter2EqualTo(String value) {
            addCriterion("parameter_2 =", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2NotEqualTo(String value) {
            addCriterion("parameter_2 <>", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2GreaterThan(String value) {
            addCriterion("parameter_2 >", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_2 >=", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2LessThan(String value) {
            addCriterion("parameter_2 <", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2LessThanOrEqualTo(String value) {
            addCriterion("parameter_2 <=", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2Like(String value) {
            addCriterion("parameter_2 like", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2NotLike(String value) {
            addCriterion("parameter_2 not like", value, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2In(List<String> values) {
            addCriterion("parameter_2 in", values, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2NotIn(List<String> values) {
            addCriterion("parameter_2 not in", values, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2Between(String value1, String value2) {
            addCriterion("parameter_2 between", value1, value2, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter2NotBetween(String value1, String value2) {
            addCriterion("parameter_2 not between", value1, value2, "parameter2");
            return (Criteria) this;
        }

        public Criteria andParameter3IsNull() {
            addCriterion("parameter_3 is null");
            return (Criteria) this;
        }

        public Criteria andParameter3IsNotNull() {
            addCriterion("parameter_3 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter3EqualTo(String value) {
            addCriterion("parameter_3 =", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3NotEqualTo(String value) {
            addCriterion("parameter_3 <>", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3GreaterThan(String value) {
            addCriterion("parameter_3 >", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_3 >=", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3LessThan(String value) {
            addCriterion("parameter_3 <", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3LessThanOrEqualTo(String value) {
            addCriterion("parameter_3 <=", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3Like(String value) {
            addCriterion("parameter_3 like", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3NotLike(String value) {
            addCriterion("parameter_3 not like", value, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3In(List<String> values) {
            addCriterion("parameter_3 in", values, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3NotIn(List<String> values) {
            addCriterion("parameter_3 not in", values, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3Between(String value1, String value2) {
            addCriterion("parameter_3 between", value1, value2, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter3NotBetween(String value1, String value2) {
            addCriterion("parameter_3 not between", value1, value2, "parameter3");
            return (Criteria) this;
        }

        public Criteria andParameter4IsNull() {
            addCriterion("parameter_4 is null");
            return (Criteria) this;
        }

        public Criteria andParameter4IsNotNull() {
            addCriterion("parameter_4 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter4EqualTo(String value) {
            addCriterion("parameter_4 =", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4NotEqualTo(String value) {
            addCriterion("parameter_4 <>", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4GreaterThan(String value) {
            addCriterion("parameter_4 >", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_4 >=", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4LessThan(String value) {
            addCriterion("parameter_4 <", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4LessThanOrEqualTo(String value) {
            addCriterion("parameter_4 <=", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4Like(String value) {
            addCriterion("parameter_4 like", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4NotLike(String value) {
            addCriterion("parameter_4 not like", value, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4In(List<String> values) {
            addCriterion("parameter_4 in", values, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4NotIn(List<String> values) {
            addCriterion("parameter_4 not in", values, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4Between(String value1, String value2) {
            addCriterion("parameter_4 between", value1, value2, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter4NotBetween(String value1, String value2) {
            addCriterion("parameter_4 not between", value1, value2, "parameter4");
            return (Criteria) this;
        }

        public Criteria andParameter5IsNull() {
            addCriterion("parameter_5 is null");
            return (Criteria) this;
        }

        public Criteria andParameter5IsNotNull() {
            addCriterion("parameter_5 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter5EqualTo(String value) {
            addCriterion("parameter_5 =", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5NotEqualTo(String value) {
            addCriterion("parameter_5 <>", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5GreaterThan(String value) {
            addCriterion("parameter_5 >", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_5 >=", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5LessThan(String value) {
            addCriterion("parameter_5 <", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5LessThanOrEqualTo(String value) {
            addCriterion("parameter_5 <=", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5Like(String value) {
            addCriterion("parameter_5 like", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5NotLike(String value) {
            addCriterion("parameter_5 not like", value, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5In(List<String> values) {
            addCriterion("parameter_5 in", values, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5NotIn(List<String> values) {
            addCriterion("parameter_5 not in", values, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5Between(String value1, String value2) {
            addCriterion("parameter_5 between", value1, value2, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter5NotBetween(String value1, String value2) {
            addCriterion("parameter_5 not between", value1, value2, "parameter5");
            return (Criteria) this;
        }

        public Criteria andParameter6IsNull() {
            addCriterion("parameter_6 is null");
            return (Criteria) this;
        }

        public Criteria andParameter6IsNotNull() {
            addCriterion("parameter_6 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter6EqualTo(String value) {
            addCriterion("parameter_6 =", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6NotEqualTo(String value) {
            addCriterion("parameter_6 <>", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6GreaterThan(String value) {
            addCriterion("parameter_6 >", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_6 >=", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6LessThan(String value) {
            addCriterion("parameter_6 <", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6LessThanOrEqualTo(String value) {
            addCriterion("parameter_6 <=", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6Like(String value) {
            addCriterion("parameter_6 like", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6NotLike(String value) {
            addCriterion("parameter_6 not like", value, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6In(List<String> values) {
            addCriterion("parameter_6 in", values, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6NotIn(List<String> values) {
            addCriterion("parameter_6 not in", values, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6Between(String value1, String value2) {
            addCriterion("parameter_6 between", value1, value2, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter6NotBetween(String value1, String value2) {
            addCriterion("parameter_6 not between", value1, value2, "parameter6");
            return (Criteria) this;
        }

        public Criteria andParameter7IsNull() {
            addCriterion("parameter_7 is null");
            return (Criteria) this;
        }

        public Criteria andParameter7IsNotNull() {
            addCriterion("parameter_7 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter7EqualTo(String value) {
            addCriterion("parameter_7 =", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7NotEqualTo(String value) {
            addCriterion("parameter_7 <>", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7GreaterThan(String value) {
            addCriterion("parameter_7 >", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_7 >=", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7LessThan(String value) {
            addCriterion("parameter_7 <", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7LessThanOrEqualTo(String value) {
            addCriterion("parameter_7 <=", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7Like(String value) {
            addCriterion("parameter_7 like", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7NotLike(String value) {
            addCriterion("parameter_7 not like", value, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7In(List<String> values) {
            addCriterion("parameter_7 in", values, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7NotIn(List<String> values) {
            addCriterion("parameter_7 not in", values, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7Between(String value1, String value2) {
            addCriterion("parameter_7 between", value1, value2, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter7NotBetween(String value1, String value2) {
            addCriterion("parameter_7 not between", value1, value2, "parameter7");
            return (Criteria) this;
        }

        public Criteria andParameter8IsNull() {
            addCriterion("parameter_8 is null");
            return (Criteria) this;
        }

        public Criteria andParameter8IsNotNull() {
            addCriterion("parameter_8 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter8EqualTo(String value) {
            addCriterion("parameter_8 =", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8NotEqualTo(String value) {
            addCriterion("parameter_8 <>", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8GreaterThan(String value) {
            addCriterion("parameter_8 >", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_8 >=", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8LessThan(String value) {
            addCriterion("parameter_8 <", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8LessThanOrEqualTo(String value) {
            addCriterion("parameter_8 <=", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8Like(String value) {
            addCriterion("parameter_8 like", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8NotLike(String value) {
            addCriterion("parameter_8 not like", value, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8In(List<String> values) {
            addCriterion("parameter_8 in", values, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8NotIn(List<String> values) {
            addCriterion("parameter_8 not in", values, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8Between(String value1, String value2) {
            addCriterion("parameter_8 between", value1, value2, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter8NotBetween(String value1, String value2) {
            addCriterion("parameter_8 not between", value1, value2, "parameter8");
            return (Criteria) this;
        }

        public Criteria andParameter9IsNull() {
            addCriterion("parameter_9 is null");
            return (Criteria) this;
        }

        public Criteria andParameter9IsNotNull() {
            addCriterion("parameter_9 is not null");
            return (Criteria) this;
        }

        public Criteria andParameter9EqualTo(String value) {
            addCriterion("parameter_9 =", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9NotEqualTo(String value) {
            addCriterion("parameter_9 <>", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9GreaterThan(String value) {
            addCriterion("parameter_9 >", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9GreaterThanOrEqualTo(String value) {
            addCriterion("parameter_9 >=", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9LessThan(String value) {
            addCriterion("parameter_9 <", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9LessThanOrEqualTo(String value) {
            addCriterion("parameter_9 <=", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9Like(String value) {
            addCriterion("parameter_9 like", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9NotLike(String value) {
            addCriterion("parameter_9 not like", value, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9In(List<String> values) {
            addCriterion("parameter_9 in", values, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9NotIn(List<String> values) {
            addCriterion("parameter_9 not in", values, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9Between(String value1, String value2) {
            addCriterion("parameter_9 between", value1, value2, "parameter9");
            return (Criteria) this;
        }

        public Criteria andParameter9NotBetween(String value1, String value2) {
            addCriterion("parameter_9 not between", value1, value2, "parameter9");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andReadQtyIsNull() {
            addCriterion("read_qty is null");
            return (Criteria) this;
        }

        public Criteria andReadQtyIsNotNull() {
            addCriterion("read_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReadQtyEqualTo(Integer value) {
            addCriterion("read_qty =", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyNotEqualTo(Integer value) {
            addCriterion("read_qty <>", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyGreaterThan(Integer value) {
            addCriterion("read_qty >", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("read_qty >=", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyLessThan(Integer value) {
            addCriterion("read_qty <", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyLessThanOrEqualTo(Integer value) {
            addCriterion("read_qty <=", value, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyIn(List<Integer> values) {
            addCriterion("read_qty in", values, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyNotIn(List<Integer> values) {
            addCriterion("read_qty not in", values, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyBetween(Integer value1, Integer value2) {
            addCriterion("read_qty between", value1, value2, "readQty");
            return (Criteria) this;
        }

        public Criteria andReadQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("read_qty not between", value1, value2, "readQty");
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
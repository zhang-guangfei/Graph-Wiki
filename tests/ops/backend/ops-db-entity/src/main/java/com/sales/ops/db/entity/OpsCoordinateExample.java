package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsCoordinateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsCoordinateExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
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

        public Criteria andMessageIsNull() {
            addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(String value) {
            addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(String value) {
            addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThan(String value) {
            addCriterion("message >", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThanOrEqualTo(String value) {
            addCriterion("message >=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThan(String value) {
            addCriterion("message <", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThanOrEqualTo(String value) {
            addCriterion("message <=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLike(String value) {
            addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(String value) {
            addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(List<String> values) {
            addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(List<String> values) {
            addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(String value1, String value2) {
            addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(String value1, String value2) {
            addCriterion("message not between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andUsetimeIsNull() {
            addCriterion("useTime is null");
            return (Criteria) this;
        }

        public Criteria andUsetimeIsNotNull() {
            addCriterion("useTime is not null");
            return (Criteria) this;
        }

        public Criteria andUsetimeEqualTo(Date value) {
            addCriterion("useTime =", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeNotEqualTo(Date value) {
            addCriterion("useTime <>", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeGreaterThan(Date value) {
            addCriterion("useTime >", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("useTime >=", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeLessThan(Date value) {
            addCriterion("useTime <", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeLessThanOrEqualTo(Date value) {
            addCriterion("useTime <=", value, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeIn(List<Date> values) {
            addCriterion("useTime in", values, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeNotIn(List<Date> values) {
            addCriterion("useTime not in", values, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeBetween(Date value1, Date value2) {
            addCriterion("useTime between", value1, value2, "usetime");
            return (Criteria) this;
        }

        public Criteria andUsetimeNotBetween(Date value1, Date value2) {
            addCriterion("useTime not between", value1, value2, "usetime");
            return (Criteria) this;
        }

        public Criteria andIssuccessIsNull() {
            addCriterion("isSuccess is null");
            return (Criteria) this;
        }

        public Criteria andIssuccessIsNotNull() {
            addCriterion("isSuccess is not null");
            return (Criteria) this;
        }

        public Criteria andIssuccessEqualTo(Boolean value) {
            addCriterion("isSuccess =", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotEqualTo(Boolean value) {
            addCriterion("isSuccess <>", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessGreaterThan(Boolean value) {
            addCriterion("isSuccess >", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isSuccess >=", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessLessThan(Boolean value) {
            addCriterion("isSuccess <", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("isSuccess <=", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessIn(List<Boolean> values) {
            addCriterion("isSuccess in", values, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotIn(List<Boolean> values) {
            addCriterion("isSuccess not in", values, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("isSuccess between", value1, value2, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isSuccess not between", value1, value2, "issuccess");
            return (Criteria) this;
        }

        public Criteria andApinameIsNull() {
            addCriterion("APIname is null");
            return (Criteria) this;
        }

        public Criteria andApinameIsNotNull() {
            addCriterion("APIname is not null");
            return (Criteria) this;
        }

        public Criteria andApinameEqualTo(String value) {
            addCriterion("APIname =", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameNotEqualTo(String value) {
            addCriterion("APIname <>", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameGreaterThan(String value) {
            addCriterion("APIname >", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameGreaterThanOrEqualTo(String value) {
            addCriterion("APIname >=", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameLessThan(String value) {
            addCriterion("APIname <", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameLessThanOrEqualTo(String value) {
            addCriterion("APIname <=", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameLike(String value) {
            addCriterion("APIname like", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameNotLike(String value) {
            addCriterion("APIname not like", value, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameIn(List<String> values) {
            addCriterion("APIname in", values, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameNotIn(List<String> values) {
            addCriterion("APIname not in", values, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameBetween(String value1, String value2) {
            addCriterion("APIname between", value1, value2, "apiname");
            return (Criteria) this;
        }

        public Criteria andApinameNotBetween(String value1, String value2) {
            addCriterion("APIname not between", value1, value2, "apiname");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIsNull() {
            addCriterion("return_message is null");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIsNotNull() {
            addCriterion("return_message is not null");
            return (Criteria) this;
        }

        public Criteria andReturnMessageEqualTo(String value) {
            addCriterion("return_message =", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotEqualTo(String value) {
            addCriterion("return_message <>", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageGreaterThan(String value) {
            addCriterion("return_message >", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageGreaterThanOrEqualTo(String value) {
            addCriterion("return_message >=", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLessThan(String value) {
            addCriterion("return_message <", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLessThanOrEqualTo(String value) {
            addCriterion("return_message <=", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLike(String value) {
            addCriterion("return_message like", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotLike(String value) {
            addCriterion("return_message not like", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIn(List<String> values) {
            addCriterion("return_message in", values, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotIn(List<String> values) {
            addCriterion("return_message not in", values, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageBetween(String value1, String value2) {
            addCriterion("return_message between", value1, value2, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotBetween(String value1, String value2) {
            addCriterion("return_message not between", value1, value2, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNull() {
            addCriterion("cost_time is null");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNotNull() {
            addCriterion("cost_time is not null");
            return (Criteria) this;
        }

        public Criteria andCostTimeEqualTo(Long value) {
            addCriterion("cost_time =", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotEqualTo(Long value) {
            addCriterion("cost_time <>", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThan(Long value) {
            addCriterion("cost_time >", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("cost_time >=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThan(Long value) {
            addCriterion("cost_time <", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThanOrEqualTo(Long value) {
            addCriterion("cost_time <=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeIn(List<Long> values) {
            addCriterion("cost_time in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotIn(List<Long> values) {
            addCriterion("cost_time not in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeBetween(Long value1, Long value2) {
            addCriterion("cost_time between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotBetween(Long value1, Long value2) {
            addCriterion("cost_time not between", value1, value2, "costTime");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductEosExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductEosExample() {
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
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNull() {
            addCriterion("dataSource is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNotNull() {
            addCriterion("dataSource is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceEqualTo(String value) {
            addCriterion("dataSource =", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotEqualTo(String value) {
            addCriterion("dataSource <>", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThan(String value) {
            addCriterion("dataSource >", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThanOrEqualTo(String value) {
            addCriterion("dataSource >=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThan(String value) {
            addCriterion("dataSource <", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThanOrEqualTo(String value) {
            addCriterion("dataSource <=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLike(String value) {
            addCriterion("dataSource like", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotLike(String value) {
            addCriterion("dataSource not like", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceIn(List<String> values) {
            addCriterion("dataSource in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotIn(List<String> values) {
            addCriterion("dataSource not in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceBetween(String value1, String value2) {
            addCriterion("dataSource between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotBetween(String value1, String value2) {
            addCriterion("dataSource not between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andEosTypeIsNull() {
            addCriterion("eos_type is null");
            return (Criteria) this;
        }

        public Criteria andEosTypeIsNotNull() {
            addCriterion("eos_type is not null");
            return (Criteria) this;
        }

        public Criteria andEosTypeEqualTo(String value) {
            addCriterion("eos_type =", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeNotEqualTo(String value) {
            addCriterion("eos_type <>", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeGreaterThan(String value) {
            addCriterion("eos_type >", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeGreaterThanOrEqualTo(String value) {
            addCriterion("eos_type >=", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeLessThan(String value) {
            addCriterion("eos_type <", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeLessThanOrEqualTo(String value) {
            addCriterion("eos_type <=", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeLike(String value) {
            addCriterion("eos_type like", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeNotLike(String value) {
            addCriterion("eos_type not like", value, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeIn(List<String> values) {
            addCriterion("eos_type in", values, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeNotIn(List<String> values) {
            addCriterion("eos_type not in", values, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeBetween(String value1, String value2) {
            addCriterion("eos_type between", value1, value2, "eosType");
            return (Criteria) this;
        }

        public Criteria andEosTypeNotBetween(String value1, String value2) {
            addCriterion("eos_type not between", value1, value2, "eosType");
            return (Criteria) this;
        }

        public Criteria andWarningdateIsNull() {
            addCriterion("warningDate is null");
            return (Criteria) this;
        }

        public Criteria andWarningdateIsNotNull() {
            addCriterion("warningDate is not null");
            return (Criteria) this;
        }

        public Criteria andWarningdateEqualTo(Date value) {
            addCriterion("warningDate =", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateNotEqualTo(Date value) {
            addCriterion("warningDate <>", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateGreaterThan(Date value) {
            addCriterion("warningDate >", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateGreaterThanOrEqualTo(Date value) {
            addCriterion("warningDate >=", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateLessThan(Date value) {
            addCriterion("warningDate <", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateLessThanOrEqualTo(Date value) {
            addCriterion("warningDate <=", value, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateIn(List<Date> values) {
            addCriterion("warningDate in", values, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateNotIn(List<Date> values) {
            addCriterion("warningDate not in", values, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateBetween(Date value1, Date value2) {
            addCriterion("warningDate between", value1, value2, "warningdate");
            return (Criteria) this;
        }

        public Criteria andWarningdateNotBetween(Date value1, Date value2) {
            addCriterion("warningDate not between", value1, value2, "warningdate");
            return (Criteria) this;
        }

        public Criteria andEosDateIsNull() {
            addCriterion("eos_date is null");
            return (Criteria) this;
        }

        public Criteria andEosDateIsNotNull() {
            addCriterion("eos_date is not null");
            return (Criteria) this;
        }

        public Criteria andEosDateEqualTo(Date value) {
            addCriterion("eos_date =", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateNotEqualTo(Date value) {
            addCriterion("eos_date <>", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateGreaterThan(Date value) {
            addCriterion("eos_date >", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateGreaterThanOrEqualTo(Date value) {
            addCriterion("eos_date >=", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateLessThan(Date value) {
            addCriterion("eos_date <", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateLessThanOrEqualTo(Date value) {
            addCriterion("eos_date <=", value, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateIn(List<Date> values) {
            addCriterion("eos_date in", values, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateNotIn(List<Date> values) {
            addCriterion("eos_date not in", values, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateBetween(Date value1, Date value2) {
            addCriterion("eos_date between", value1, value2, "eosDate");
            return (Criteria) this;
        }

        public Criteria andEosDateNotBetween(Date value1, Date value2) {
            addCriterion("eos_date not between", value1, value2, "eosDate");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendIsNull() {
            addCriterion("modelNo_recommend is null");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendIsNotNull() {
            addCriterion("modelNo_recommend is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendEqualTo(String value) {
            addCriterion("modelNo_recommend =", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendNotEqualTo(String value) {
            addCriterion("modelNo_recommend <>", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendGreaterThan(String value) {
            addCriterion("modelNo_recommend >", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo_recommend >=", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendLessThan(String value) {
            addCriterion("modelNo_recommend <", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendLessThanOrEqualTo(String value) {
            addCriterion("modelNo_recommend <=", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendLike(String value) {
            addCriterion("modelNo_recommend like", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendNotLike(String value) {
            addCriterion("modelNo_recommend not like", value, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendIn(List<String> values) {
            addCriterion("modelNo_recommend in", values, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendNotIn(List<String> values) {
            addCriterion("modelNo_recommend not in", values, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendBetween(String value1, String value2) {
            addCriterion("modelNo_recommend between", value1, value2, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andModelnoRecommendNotBetween(String value1, String value2) {
            addCriterion("modelNo_recommend not between", value1, value2, "modelnoRecommend");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNull() {
            addCriterion("priority_level is null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNotNull() {
            addCriterion("priority_level is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelEqualTo(String value) {
            addCriterion("priority_level =", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotEqualTo(String value) {
            addCriterion("priority_level <>", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThan(String value) {
            addCriterion("priority_level >", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThanOrEqualTo(String value) {
            addCriterion("priority_level >=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThan(String value) {
            addCriterion("priority_level <", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThanOrEqualTo(String value) {
            addCriterion("priority_level <=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLike(String value) {
            addCriterion("priority_level like", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotLike(String value) {
            addCriterion("priority_level not like", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIn(List<String> values) {
            addCriterion("priority_level in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotIn(List<String> values) {
            addCriterion("priority_level not in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelBetween(String value1, String value2) {
            addCriterion("priority_level between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotBetween(String value1, String value2) {
            addCriterion("priority_level not between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("describe is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("describe is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("describe =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("describe <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("describe >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("describe >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("describe <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("describe <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("describe like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("describe not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List<String> values) {
            addCriterion("describe in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List<String> values) {
            addCriterion("describe not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("describe between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("describe not between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andStopdateIsNull() {
            addCriterion("stopDate is null");
            return (Criteria) this;
        }

        public Criteria andStopdateIsNotNull() {
            addCriterion("stopDate is not null");
            return (Criteria) this;
        }

        public Criteria andStopdateEqualTo(Date value) {
            addCriterion("stopDate =", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateNotEqualTo(Date value) {
            addCriterion("stopDate <>", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateGreaterThan(Date value) {
            addCriterion("stopDate >", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateGreaterThanOrEqualTo(Date value) {
            addCriterion("stopDate >=", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateLessThan(Date value) {
            addCriterion("stopDate <", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateLessThanOrEqualTo(Date value) {
            addCriterion("stopDate <=", value, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateIn(List<Date> values) {
            addCriterion("stopDate in", values, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateNotIn(List<Date> values) {
            addCriterion("stopDate not in", values, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateBetween(Date value1, Date value2) {
            addCriterion("stopDate between", value1, value2, "stopdate");
            return (Criteria) this;
        }

        public Criteria andStopdateNotBetween(Date value1, Date value2) {
            addCriterion("stopDate not between", value1, value2, "stopdate");
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

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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
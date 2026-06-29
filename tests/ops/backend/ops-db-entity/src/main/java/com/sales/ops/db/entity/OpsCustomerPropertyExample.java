package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsCustomerPropertyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsCustomerPropertyExample() {
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

        public Criteria andVipcodeIsNull() {
            addCriterion("vipCode is null");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNotNull() {
            addCriterion("vipCode is not null");
            return (Criteria) this;
        }

        public Criteria andVipcodeEqualTo(String value) {
            addCriterion("vipCode =", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotEqualTo(String value) {
            addCriterion("vipCode <>", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThan(String value) {
            addCriterion("vipCode >", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("vipCode >=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThan(String value) {
            addCriterion("vipCode <", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThanOrEqualTo(String value) {
            addCriterion("vipCode <=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLike(String value) {
            addCriterion("vipCode like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotLike(String value) {
            addCriterion("vipCode not like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeIn(List<String> values) {
            addCriterion("vipCode in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotIn(List<String> values) {
            addCriterion("vipCode not in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeBetween(String value1, String value2) {
            addCriterion("vipCode between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotBetween(String value1, String value2) {
            addCriterion("vipCode not between", value1, value2, "vipcode");
            return (Criteria) this;
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

        public Criteria andCustomerGroupIsNull() {
            addCriterion("customer_group is null");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupIsNotNull() {
            addCriterion("customer_group is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupEqualTo(String value) {
            addCriterion("customer_group =", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotEqualTo(String value) {
            addCriterion("customer_group <>", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupGreaterThan(String value) {
            addCriterion("customer_group >", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupGreaterThanOrEqualTo(String value) {
            addCriterion("customer_group >=", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupLessThan(String value) {
            addCriterion("customer_group <", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupLessThanOrEqualTo(String value) {
            addCriterion("customer_group <=", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupLike(String value) {
            addCriterion("customer_group like", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotLike(String value) {
            addCriterion("customer_group not like", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupIn(List<String> values) {
            addCriterion("customer_group in", values, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotIn(List<String> values) {
            addCriterion("customer_group not in", values, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupBetween(String value1, String value2) {
            addCriterion("customer_group between", value1, value2, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotBetween(String value1, String value2) {
            addCriterion("customer_group not between", value1, value2, "customerGroup");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoModelSendConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoModelSendConfigExample() {
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

        public Criteria andModelIsNull() {
            addCriterion("model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andFuzzyIsNull() {
            addCriterion("fuzzy is null");
            return (Criteria) this;
        }

        public Criteria andFuzzyIsNotNull() {
            addCriterion("fuzzy is not null");
            return (Criteria) this;
        }

        public Criteria andFuzzyEqualTo(Boolean value) {
            addCriterion("fuzzy =", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyNotEqualTo(Boolean value) {
            addCriterion("fuzzy <>", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyGreaterThan(Boolean value) {
            addCriterion("fuzzy >", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("fuzzy >=", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyLessThan(Boolean value) {
            addCriterion("fuzzy <", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyLessThanOrEqualTo(Boolean value) {
            addCriterion("fuzzy <=", value, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyIn(List<Boolean> values) {
            addCriterion("fuzzy in", values, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyNotIn(List<Boolean> values) {
            addCriterion("fuzzy not in", values, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyBetween(Boolean value1, Boolean value2) {
            addCriterion("fuzzy between", value1, value2, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andFuzzyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("fuzzy not between", value1, value2, "fuzzy");
            return (Criteria) this;
        }

        public Criteria andModiIsNull() {
            addCriterion("modi is null");
            return (Criteria) this;
        }

        public Criteria andModiIsNotNull() {
            addCriterion("modi is not null");
            return (Criteria) this;
        }

        public Criteria andModiEqualTo(String value) {
            addCriterion("modi =", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiNotEqualTo(String value) {
            addCriterion("modi <>", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiGreaterThan(String value) {
            addCriterion("modi >", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiGreaterThanOrEqualTo(String value) {
            addCriterion("modi >=", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiLessThan(String value) {
            addCriterion("modi <", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiLessThanOrEqualTo(String value) {
            addCriterion("modi <=", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiLike(String value) {
            addCriterion("modi like", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiNotLike(String value) {
            addCriterion("modi not like", value, "modi");
            return (Criteria) this;
        }

        public Criteria andModiIn(List<String> values) {
            addCriterion("modi in", values, "modi");
            return (Criteria) this;
        }

        public Criteria andModiNotIn(List<String> values) {
            addCriterion("modi not in", values, "modi");
            return (Criteria) this;
        }

        public Criteria andModiBetween(String value1, String value2) {
            addCriterion("modi between", value1, value2, "modi");
            return (Criteria) this;
        }

        public Criteria andModiNotBetween(String value1, String value2) {
            addCriterion("modi not between", value1, value2, "modi");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNull() {
            addCriterion("supplier is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNotNull() {
            addCriterion("supplier is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualTo(String value) {
            addCriterion("supplier =", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(String value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(String value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(String value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(String value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(String value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLike(String value) {
            addCriterion("supplier like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotLike(String value) {
            addCriterion("supplier not like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierIn(List<String> values) {
            addCriterion("supplier in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotIn(List<String> values) {
            addCriterion("supplier not in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierBetween(String value1, String value2) {
            addCriterion("supplier between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotBetween(String value1, String value2) {
            addCriterion("supplier not between", value1, value2, "supplier");
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

        public Criteria andInsertUserIsNull() {
            addCriterion("insert_user is null");
            return (Criteria) this;
        }

        public Criteria andInsertUserIsNotNull() {
            addCriterion("insert_user is not null");
            return (Criteria) this;
        }

        public Criteria andInsertUserEqualTo(String value) {
            addCriterion("insert_user =", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserNotEqualTo(String value) {
            addCriterion("insert_user <>", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserGreaterThan(String value) {
            addCriterion("insert_user >", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserGreaterThanOrEqualTo(String value) {
            addCriterion("insert_user >=", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserLessThan(String value) {
            addCriterion("insert_user <", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserLessThanOrEqualTo(String value) {
            addCriterion("insert_user <=", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserLike(String value) {
            addCriterion("insert_user like", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserNotLike(String value) {
            addCriterion("insert_user not like", value, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserIn(List<String> values) {
            addCriterion("insert_user in", values, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserNotIn(List<String> values) {
            addCriterion("insert_user not in", values, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserBetween(String value1, String value2) {
            addCriterion("insert_user between", value1, value2, "insertUser");
            return (Criteria) this;
        }

        public Criteria andInsertUserNotBetween(String value1, String value2) {
            addCriterion("insert_user not between", value1, value2, "insertUser");
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
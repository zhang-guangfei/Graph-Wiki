package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInqbCodeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInqbCodeConfigExample() {
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

        public Criteria andCodeTypeIsNull() {
            addCriterion("code_type is null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIsNotNull() {
            addCriterion("code_type is not null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeEqualTo(String value) {
            addCriterion("code_type =", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotEqualTo(String value) {
            addCriterion("code_type <>", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThan(String value) {
            addCriterion("code_type >", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("code_type >=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThan(String value) {
            addCriterion("code_type <", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThanOrEqualTo(String value) {
            addCriterion("code_type <=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLike(String value) {
            addCriterion("code_type like", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotLike(String value) {
            addCriterion("code_type not like", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIn(List<String> values) {
            addCriterion("code_type in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotIn(List<String> values) {
            addCriterion("code_type not in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeBetween(String value1, String value2) {
            addCriterion("code_type between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotBetween(String value1, String value2) {
            addCriterion("code_type not between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIsNull() {
            addCriterion("ops_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIsNotNull() {
            addCriterion("ops_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeEqualTo(String value) {
            addCriterion("ops_reason_code =", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotEqualTo(String value) {
            addCriterion("ops_reason_code <>", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeGreaterThan(String value) {
            addCriterion("ops_reason_code >", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ops_reason_code >=", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLessThan(String value) {
            addCriterion("ops_reason_code <", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("ops_reason_code <=", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLike(String value) {
            addCriterion("ops_reason_code like", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotLike(String value) {
            addCriterion("ops_reason_code not like", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIn(List<String> values) {
            addCriterion("ops_reason_code in", values, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotIn(List<String> values) {
            addCriterion("ops_reason_code not in", values, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeBetween(String value1, String value2) {
            addCriterion("ops_reason_code between", value1, value2, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotBetween(String value1, String value2) {
            addCriterion("ops_reason_code not between", value1, value2, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIsNull() {
            addCriterion("ops_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIsNotNull() {
            addCriterion("ops_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescEqualTo(String value) {
            addCriterion("ops_reason_desc =", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotEqualTo(String value) {
            addCriterion("ops_reason_desc <>", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescGreaterThan(String value) {
            addCriterion("ops_reason_desc >", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("ops_reason_desc >=", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLessThan(String value) {
            addCriterion("ops_reason_desc <", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLessThanOrEqualTo(String value) {
            addCriterion("ops_reason_desc <=", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLike(String value) {
            addCriterion("ops_reason_desc like", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotLike(String value) {
            addCriterion("ops_reason_desc not like", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIn(List<String> values) {
            addCriterion("ops_reason_desc in", values, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotIn(List<String> values) {
            addCriterion("ops_reason_desc not in", values, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescBetween(String value1, String value2) {
            addCriterion("ops_reason_desc between", value1, value2, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotBetween(String value1, String value2) {
            addCriterion("ops_reason_desc not between", value1, value2, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeIsNull() {
            addCriterion("supplie_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeIsNotNull() {
            addCriterion("supplie_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeEqualTo(String value) {
            addCriterion("supplie_code =", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeNotEqualTo(String value) {
            addCriterion("supplie_code <>", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeGreaterThan(String value) {
            addCriterion("supplie_code >", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplie_code >=", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeLessThan(String value) {
            addCriterion("supplie_code <", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeLessThanOrEqualTo(String value) {
            addCriterion("supplie_code <=", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeLike(String value) {
            addCriterion("supplie_code like", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeNotLike(String value) {
            addCriterion("supplie_code not like", value, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeIn(List<String> values) {
            addCriterion("supplie_code in", values, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeNotIn(List<String> values) {
            addCriterion("supplie_code not in", values, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeBetween(String value1, String value2) {
            addCriterion("supplie_code between", value1, value2, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieCodeNotBetween(String value1, String value2) {
            addCriterion("supplie_code not between", value1, value2, "supplieCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeIsNull() {
            addCriterion("supplie_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeIsNotNull() {
            addCriterion("supplie_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeEqualTo(String value) {
            addCriterion("supplie_reason_code =", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeNotEqualTo(String value) {
            addCriterion("supplie_reason_code <>", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeGreaterThan(String value) {
            addCriterion("supplie_reason_code >", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplie_reason_code >=", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeLessThan(String value) {
            addCriterion("supplie_reason_code <", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("supplie_reason_code <=", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeLike(String value) {
            addCriterion("supplie_reason_code like", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeNotLike(String value) {
            addCriterion("supplie_reason_code not like", value, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeIn(List<String> values) {
            addCriterion("supplie_reason_code in", values, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeNotIn(List<String> values) {
            addCriterion("supplie_reason_code not in", values, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeBetween(String value1, String value2) {
            addCriterion("supplie_reason_code between", value1, value2, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonCodeNotBetween(String value1, String value2) {
            addCriterion("supplie_reason_code not between", value1, value2, "supplieReasonCode");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescIsNull() {
            addCriterion("supplie_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescIsNotNull() {
            addCriterion("supplie_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescEqualTo(String value) {
            addCriterion("supplie_reason_desc =", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescNotEqualTo(String value) {
            addCriterion("supplie_reason_desc <>", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescGreaterThan(String value) {
            addCriterion("supplie_reason_desc >", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("supplie_reason_desc >=", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescLessThan(String value) {
            addCriterion("supplie_reason_desc <", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescLessThanOrEqualTo(String value) {
            addCriterion("supplie_reason_desc <=", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescLike(String value) {
            addCriterion("supplie_reason_desc like", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescNotLike(String value) {
            addCriterion("supplie_reason_desc not like", value, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescIn(List<String> values) {
            addCriterion("supplie_reason_desc in", values, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescNotIn(List<String> values) {
            addCriterion("supplie_reason_desc not in", values, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescBetween(String value1, String value2) {
            addCriterion("supplie_reason_desc between", value1, value2, "supplieReasonDesc");
            return (Criteria) this;
        }

        public Criteria andSupplieReasonDescNotBetween(String value1, String value2) {
            addCriterion("supplie_reason_desc not between", value1, value2, "supplieReasonDesc");
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

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("update_time like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("update_time not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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
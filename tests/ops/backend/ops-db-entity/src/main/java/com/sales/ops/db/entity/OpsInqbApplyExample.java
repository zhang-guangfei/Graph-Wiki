package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInqbApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInqbApplyExample() {
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

        public Criteria andInqbApplyNoIsNull() {
            addCriterion("inqb_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIsNotNull() {
            addCriterion("inqb_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoEqualTo(String value) {
            addCriterion("inqb_apply_no =", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotEqualTo(String value) {
            addCriterion("inqb_apply_no <>", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThan(String value) {
            addCriterion("inqb_apply_no >", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no >=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThan(String value) {
            addCriterion("inqb_apply_no <", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no <=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLike(String value) {
            addCriterion("inqb_apply_no like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotLike(String value) {
            addCriterion("inqb_apply_no not like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIn(List<String> values) {
            addCriterion("inqb_apply_no in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotIn(List<String> values) {
            addCriterion("inqb_apply_no not in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoBetween(String value1, String value2) {
            addCriterion("inqb_apply_no between", value1, value2, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotBetween(String value1, String value2) {
            addCriterion("inqb_apply_no not between", value1, value2, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoIsNull() {
            addCriterion("sources_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoIsNotNull() {
            addCriterion("sources_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoEqualTo(String value) {
            addCriterion("sources_apply_no =", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoNotEqualTo(String value) {
            addCriterion("sources_apply_no <>", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoGreaterThan(String value) {
            addCriterion("sources_apply_no >", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("sources_apply_no >=", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoLessThan(String value) {
            addCriterion("sources_apply_no <", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoLessThanOrEqualTo(String value) {
            addCriterion("sources_apply_no <=", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoLike(String value) {
            addCriterion("sources_apply_no like", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoNotLike(String value) {
            addCriterion("sources_apply_no not like", value, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoIn(List<String> values) {
            addCriterion("sources_apply_no in", values, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoNotIn(List<String> values) {
            addCriterion("sources_apply_no not in", values, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoBetween(String value1, String value2) {
            addCriterion("sources_apply_no between", value1, value2, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andSourcesApplyNoNotBetween(String value1, String value2) {
            addCriterion("sources_apply_no not between", value1, value2, "sourcesApplyNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIsNull() {
            addCriterion("data_sources is null");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIsNotNull() {
            addCriterion("data_sources is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourcesEqualTo(String value) {
            addCriterion("data_sources =", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotEqualTo(String value) {
            addCriterion("data_sources <>", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesGreaterThan(String value) {
            addCriterion("data_sources >", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesGreaterThanOrEqualTo(String value) {
            addCriterion("data_sources >=", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLessThan(String value) {
            addCriterion("data_sources <", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLessThanOrEqualTo(String value) {
            addCriterion("data_sources <=", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLike(String value) {
            addCriterion("data_sources like", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotLike(String value) {
            addCriterion("data_sources not like", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIn(List<String> values) {
            addCriterion("data_sources in", values, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotIn(List<String> values) {
            addCriterion("data_sources not in", values, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesBetween(String value1, String value2) {
            addCriterion("data_sources between", value1, value2, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotBetween(String value1, String value2) {
            addCriterion("data_sources not between", value1, value2, "dataSources");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNull() {
            addCriterion("model_no is null");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNotNull() {
            addCriterion("model_no is not null");
            return (Criteria) this;
        }

        public Criteria andModelNoEqualTo(String value) {
            addCriterion("model_no =", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotEqualTo(String value) {
            addCriterion("model_no <>", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThan(String value) {
            addCriterion("model_no >", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("model_no >=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThan(String value) {
            addCriterion("model_no <", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThanOrEqualTo(String value) {
            addCriterion("model_no <=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLike(String value) {
            addCriterion("model_no like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotLike(String value) {
            addCriterion("model_no not like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoIn(List<String> values) {
            addCriterion("model_no in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotIn(List<String> values) {
            addCriterion("model_no not in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoBetween(String value1, String value2) {
            addCriterion("model_no between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotBetween(String value1, String value2) {
            addCriterion("model_no not between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andUserDeptIsNull() {
            addCriterion("user_dept is null");
            return (Criteria) this;
        }

        public Criteria andUserDeptIsNotNull() {
            addCriterion("user_dept is not null");
            return (Criteria) this;
        }

        public Criteria andUserDeptEqualTo(String value) {
            addCriterion("user_dept =", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotEqualTo(String value) {
            addCriterion("user_dept <>", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptGreaterThan(String value) {
            addCriterion("user_dept >", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptGreaterThanOrEqualTo(String value) {
            addCriterion("user_dept >=", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLessThan(String value) {
            addCriterion("user_dept <", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLessThanOrEqualTo(String value) {
            addCriterion("user_dept <=", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLike(String value) {
            addCriterion("user_dept like", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotLike(String value) {
            addCriterion("user_dept not like", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptIn(List<String> values) {
            addCriterion("user_dept in", values, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotIn(List<String> values) {
            addCriterion("user_dept not in", values, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptBetween(String value1, String value2) {
            addCriterion("user_dept between", value1, value2, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotBetween(String value1, String value2) {
            addCriterion("user_dept not between", value1, value2, "userDept");
            return (Criteria) this;
        }

        public Criteria andInqbStatusIsNull() {
            addCriterion("inqb_status is null");
            return (Criteria) this;
        }

        public Criteria andInqbStatusIsNotNull() {
            addCriterion("inqb_status is not null");
            return (Criteria) this;
        }

        public Criteria andInqbStatusEqualTo(Integer value) {
            addCriterion("inqb_status =", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusNotEqualTo(Integer value) {
            addCriterion("inqb_status <>", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusGreaterThan(Integer value) {
            addCriterion("inqb_status >", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("inqb_status >=", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusLessThan(Integer value) {
            addCriterion("inqb_status <", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusLessThanOrEqualTo(Integer value) {
            addCriterion("inqb_status <=", value, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusIn(List<Integer> values) {
            addCriterion("inqb_status in", values, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusNotIn(List<Integer> values) {
            addCriterion("inqb_status not in", values, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusBetween(Integer value1, Integer value2) {
            addCriterion("inqb_status between", value1, value2, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("inqb_status not between", value1, value2, "inqbStatus");
            return (Criteria) this;
        }

        public Criteria andInqbValidityIsNull() {
            addCriterion("inqb_validity is null");
            return (Criteria) this;
        }

        public Criteria andInqbValidityIsNotNull() {
            addCriterion("inqb_validity is not null");
            return (Criteria) this;
        }

        public Criteria andInqbValidityEqualTo(String value) {
            addCriterion("inqb_validity =", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityNotEqualTo(String value) {
            addCriterion("inqb_validity <>", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityGreaterThan(String value) {
            addCriterion("inqb_validity >", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_validity >=", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityLessThan(String value) {
            addCriterion("inqb_validity <", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityLessThanOrEqualTo(String value) {
            addCriterion("inqb_validity <=", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityLike(String value) {
            addCriterion("inqb_validity like", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityNotLike(String value) {
            addCriterion("inqb_validity not like", value, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityIn(List<String> values) {
            addCriterion("inqb_validity in", values, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityNotIn(List<String> values) {
            addCriterion("inqb_validity not in", values, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityBetween(String value1, String value2) {
            addCriterion("inqb_validity between", value1, value2, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andInqbValidityNotBetween(String value1, String value2) {
            addCriterion("inqb_validity not between", value1, value2, "inqbValidity");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNull() {
            addCriterion("expiration_date is null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNotNull() {
            addCriterion("expiration_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateEqualTo(Date value) {
            addCriterion("expiration_date =", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotEqualTo(Date value) {
            addCriterion("expiration_date <>", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThan(Date value) {
            addCriterion("expiration_date >", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("expiration_date >=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThan(Date value) {
            addCriterion("expiration_date <", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThanOrEqualTo(Date value) {
            addCriterion("expiration_date <=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIn(List<Date> values) {
            addCriterion("expiration_date in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotIn(List<Date> values) {
            addCriterion("expiration_date not in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateBetween(Date value1, Date value2) {
            addCriterion("expiration_date between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotBetween(Date value1, Date value2) {
            addCriterion("expiration_date not between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIsNull() {
            addCriterion("expected_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIsNotNull() {
            addCriterion("expected_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateEqualTo(Integer value) {
            addCriterion("expected_delivery_date =", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotEqualTo(Integer value) {
            addCriterion("expected_delivery_date <>", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateGreaterThan(Integer value) {
            addCriterion("expected_delivery_date >", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("expected_delivery_date >=", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateLessThan(Integer value) {
            addCriterion("expected_delivery_date <", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateLessThanOrEqualTo(Integer value) {
            addCriterion("expected_delivery_date <=", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIn(List<Integer> values) {
            addCriterion("expected_delivery_date in", values, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotIn(List<Integer> values) {
            addCriterion("expected_delivery_date not in", values, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateBetween(Integer value1, Integer value2) {
            addCriterion("expected_delivery_date between", value1, value2, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotBetween(Integer value1, Integer value2) {
            addCriterion("expected_delivery_date not between", value1, value2, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andInqbDateIsNull() {
            addCriterion("inqb_date is null");
            return (Criteria) this;
        }

        public Criteria andInqbDateIsNotNull() {
            addCriterion("inqb_date is not null");
            return (Criteria) this;
        }

        public Criteria andInqbDateEqualTo(Date value) {
            addCriterion("inqb_date =", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateNotEqualTo(Date value) {
            addCriterion("inqb_date <>", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateGreaterThan(Date value) {
            addCriterion("inqb_date >", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateGreaterThanOrEqualTo(Date value) {
            addCriterion("inqb_date >=", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateLessThan(Date value) {
            addCriterion("inqb_date <", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateLessThanOrEqualTo(Date value) {
            addCriterion("inqb_date <=", value, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateIn(List<Date> values) {
            addCriterion("inqb_date in", values, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateNotIn(List<Date> values) {
            addCriterion("inqb_date not in", values, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateBetween(Date value1, Date value2) {
            addCriterion("inqb_date between", value1, value2, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbDateNotBetween(Date value1, Date value2) {
            addCriterion("inqb_date not between", value1, value2, "inqbDate");
            return (Criteria) this;
        }

        public Criteria andInqbTypeIsNull() {
            addCriterion("inqb_type is null");
            return (Criteria) this;
        }

        public Criteria andInqbTypeIsNotNull() {
            addCriterion("inqb_type is not null");
            return (Criteria) this;
        }

        public Criteria andInqbTypeEqualTo(Integer value) {
            addCriterion("inqb_type =", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeNotEqualTo(Integer value) {
            addCriterion("inqb_type <>", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeGreaterThan(Integer value) {
            addCriterion("inqb_type >", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("inqb_type >=", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeLessThan(Integer value) {
            addCriterion("inqb_type <", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeLessThanOrEqualTo(Integer value) {
            addCriterion("inqb_type <=", value, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeIn(List<Integer> values) {
            addCriterion("inqb_type in", values, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeNotIn(List<Integer> values) {
            addCriterion("inqb_type not in", values, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeBetween(Integer value1, Integer value2) {
            addCriterion("inqb_type between", value1, value2, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("inqb_type not between", value1, value2, "inqbType");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyIsNull() {
            addCriterion("inqb_classify is null");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyIsNotNull() {
            addCriterion("inqb_classify is not null");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyEqualTo(String value) {
            addCriterion("inqb_classify =", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyNotEqualTo(String value) {
            addCriterion("inqb_classify <>", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyGreaterThan(String value) {
            addCriterion("inqb_classify >", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_classify >=", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyLessThan(String value) {
            addCriterion("inqb_classify <", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyLessThanOrEqualTo(String value) {
            addCriterion("inqb_classify <=", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyLike(String value) {
            addCriterion("inqb_classify like", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyNotLike(String value) {
            addCriterion("inqb_classify not like", value, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyIn(List<String> values) {
            addCriterion("inqb_classify in", values, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyNotIn(List<String> values) {
            addCriterion("inqb_classify not in", values, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyBetween(String value1, String value2) {
            addCriterion("inqb_classify between", value1, value2, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbClassifyNotBetween(String value1, String value2) {
            addCriterion("inqb_classify not between", value1, value2, "inqbClassify");
            return (Criteria) this;
        }

        public Criteria andInqbDeptIsNull() {
            addCriterion("inqb_dept is null");
            return (Criteria) this;
        }

        public Criteria andInqbDeptIsNotNull() {
            addCriterion("inqb_dept is not null");
            return (Criteria) this;
        }

        public Criteria andInqbDeptEqualTo(String value) {
            addCriterion("inqb_dept =", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptNotEqualTo(String value) {
            addCriterion("inqb_dept <>", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptGreaterThan(String value) {
            addCriterion("inqb_dept >", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_dept >=", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptLessThan(String value) {
            addCriterion("inqb_dept <", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptLessThanOrEqualTo(String value) {
            addCriterion("inqb_dept <=", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptLike(String value) {
            addCriterion("inqb_dept like", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptNotLike(String value) {
            addCriterion("inqb_dept not like", value, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptIn(List<String> values) {
            addCriterion("inqb_dept in", values, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptNotIn(List<String> values) {
            addCriterion("inqb_dept not in", values, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptBetween(String value1, String value2) {
            addCriterion("inqb_dept between", value1, value2, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbDeptNotBetween(String value1, String value2) {
            addCriterion("inqb_dept not between", value1, value2, "inqbDept");
            return (Criteria) this;
        }

        public Criteria andInqbUserIsNull() {
            addCriterion("inqb_user is null");
            return (Criteria) this;
        }

        public Criteria andInqbUserIsNotNull() {
            addCriterion("inqb_user is not null");
            return (Criteria) this;
        }

        public Criteria andInqbUserEqualTo(String value) {
            addCriterion("inqb_user =", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserNotEqualTo(String value) {
            addCriterion("inqb_user <>", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserGreaterThan(String value) {
            addCriterion("inqb_user >", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_user >=", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserLessThan(String value) {
            addCriterion("inqb_user <", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserLessThanOrEqualTo(String value) {
            addCriterion("inqb_user <=", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserLike(String value) {
            addCriterion("inqb_user like", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserNotLike(String value) {
            addCriterion("inqb_user not like", value, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserIn(List<String> values) {
            addCriterion("inqb_user in", values, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserNotIn(List<String> values) {
            addCriterion("inqb_user not in", values, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserBetween(String value1, String value2) {
            addCriterion("inqb_user between", value1, value2, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserNotBetween(String value1, String value2) {
            addCriterion("inqb_user not between", value1, value2, "inqbUser");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameIsNull() {
            addCriterion("inqb_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameIsNotNull() {
            addCriterion("inqb_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameEqualTo(String value) {
            addCriterion("inqb_user_name =", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameNotEqualTo(String value) {
            addCriterion("inqb_user_name <>", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameGreaterThan(String value) {
            addCriterion("inqb_user_name >", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_user_name >=", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameLessThan(String value) {
            addCriterion("inqb_user_name <", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameLessThanOrEqualTo(String value) {
            addCriterion("inqb_user_name <=", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameLike(String value) {
            addCriterion("inqb_user_name like", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameNotLike(String value) {
            addCriterion("inqb_user_name not like", value, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameIn(List<String> values) {
            addCriterion("inqb_user_name in", values, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameNotIn(List<String> values) {
            addCriterion("inqb_user_name not in", values, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameBetween(String value1, String value2) {
            addCriterion("inqb_user_name between", value1, value2, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andInqbUserNameNotBetween(String value1, String value2) {
            addCriterion("inqb_user_name not between", value1, value2, "inqbUserName");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNull() {
            addCriterion("reply_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNotNull() {
            addCriterion("reply_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptEqualTo(String value) {
            addCriterion("reply_dept =", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotEqualTo(String value) {
            addCriterion("reply_dept <>", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThan(String value) {
            addCriterion("reply_dept >", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_dept >=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThan(String value) {
            addCriterion("reply_dept <", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_dept <=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLike(String value) {
            addCriterion("reply_dept like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotLike(String value) {
            addCriterion("reply_dept not like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIn(List<String> values) {
            addCriterion("reply_dept in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotIn(List<String> values) {
            addCriterion("reply_dept not in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptBetween(String value1, String value2) {
            addCriterion("reply_dept between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotBetween(String value1, String value2) {
            addCriterion("reply_dept not between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIsNull() {
            addCriterion("reply_supplier_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIsNotNull() {
            addCriterion("reply_supplier_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptEqualTo(String value) {
            addCriterion("reply_supplier_dept =", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotEqualTo(String value) {
            addCriterion("reply_supplier_dept <>", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThan(String value) {
            addCriterion("reply_supplier_dept >", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept >=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThan(String value) {
            addCriterion("reply_supplier_dept <", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept <=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLike(String value) {
            addCriterion("reply_supplier_dept like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotLike(String value) {
            addCriterion("reply_supplier_dept not like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIn(List<String> values) {
            addCriterion("reply_supplier_dept in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotIn(List<String> values) {
            addCriterion("reply_supplier_dept not in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept not between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIsNull() {
            addCriterion("reply_accept_hl is null");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIsNotNull() {
            addCriterion("reply_accept_hl is not null");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlEqualTo(String value) {
            addCriterion("reply_accept_hl =", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotEqualTo(String value) {
            addCriterion("reply_accept_hl <>", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlGreaterThan(String value) {
            addCriterion("reply_accept_hl >", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlGreaterThanOrEqualTo(String value) {
            addCriterion("reply_accept_hl >=", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLessThan(String value) {
            addCriterion("reply_accept_hl <", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLessThanOrEqualTo(String value) {
            addCriterion("reply_accept_hl <=", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLike(String value) {
            addCriterion("reply_accept_hl like", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotLike(String value) {
            addCriterion("reply_accept_hl not like", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIn(List<String> values) {
            addCriterion("reply_accept_hl in", values, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotIn(List<String> values) {
            addCriterion("reply_accept_hl not in", values, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlBetween(String value1, String value2) {
            addCriterion("reply_accept_hl between", value1, value2, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotBetween(String value1, String value2) {
            addCriterion("reply_accept_hl not between", value1, value2, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNull() {
            addCriterion("reply_no is null");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNotNull() {
            addCriterion("reply_no is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNoEqualTo(String value) {
            addCriterion("reply_no =", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotEqualTo(String value) {
            addCriterion("reply_no <>", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThan(String value) {
            addCriterion("reply_no >", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("reply_no >=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThan(String value) {
            addCriterion("reply_no <", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThanOrEqualTo(String value) {
            addCriterion("reply_no <=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLike(String value) {
            addCriterion("reply_no like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotLike(String value) {
            addCriterion("reply_no not like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoIn(List<String> values) {
            addCriterion("reply_no in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotIn(List<String> values) {
            addCriterion("reply_no not in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoBetween(String value1, String value2) {
            addCriterion("reply_no between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotBetween(String value1, String value2) {
            addCriterion("reply_no not between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIsNull() {
            addCriterion("reply_delivery_days is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIsNotNull() {
            addCriterion("reply_delivery_days is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysEqualTo(Integer value) {
            addCriterion("reply_delivery_days =", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotEqualTo(Integer value) {
            addCriterion("reply_delivery_days <>", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysGreaterThan(Integer value) {
            addCriterion("reply_delivery_days >", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_delivery_days >=", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysLessThan(Integer value) {
            addCriterion("reply_delivery_days <", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysLessThanOrEqualTo(Integer value) {
            addCriterion("reply_delivery_days <=", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIn(List<Integer> values) {
            addCriterion("reply_delivery_days in", values, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotIn(List<Integer> values) {
            addCriterion("reply_delivery_days not in", values, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysBetween(Integer value1, Integer value2) {
            addCriterion("reply_delivery_days between", value1, value2, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_delivery_days not between", value1, value2, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNull() {
            addCriterion("reply_user is null");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNotNull() {
            addCriterion("reply_user is not null");
            return (Criteria) this;
        }

        public Criteria andReplyUserEqualTo(String value) {
            addCriterion("reply_user =", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotEqualTo(String value) {
            addCriterion("reply_user <>", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThan(String value) {
            addCriterion("reply_user >", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("reply_user >=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThan(String value) {
            addCriterion("reply_user <", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThanOrEqualTo(String value) {
            addCriterion("reply_user <=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLike(String value) {
            addCriterion("reply_user like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotLike(String value) {
            addCriterion("reply_user not like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserIn(List<String> values) {
            addCriterion("reply_user in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotIn(List<String> values) {
            addCriterion("reply_user not in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserBetween(String value1, String value2) {
            addCriterion("reply_user between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotBetween(String value1, String value2) {
            addCriterion("reply_user not between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNull() {
            addCriterion("reply_time is null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNotNull() {
            addCriterion("reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeEqualTo(Date value) {
            addCriterion("reply_time =", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotEqualTo(Date value) {
            addCriterion("reply_time <>", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThan(Date value) {
            addCriterion("reply_time >", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_time >=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThan(Date value) {
            addCriterion("reply_time <", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("reply_time <=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIn(List<Date> values) {
            addCriterion("reply_time in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotIn(List<Date> values) {
            addCriterion("reply_time not in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeBetween(Date value1, Date value2) {
            addCriterion("reply_time between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("reply_time not between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIsNull() {
            addCriterion("reply_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIsNotNull() {
            addCriterion("reply_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeEqualTo(String value) {
            addCriterion("reply_reason_code =", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotEqualTo(String value) {
            addCriterion("reply_reason_code <>", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeGreaterThan(String value) {
            addCriterion("reply_reason_code >", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("reply_reason_code >=", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLessThan(String value) {
            addCriterion("reply_reason_code <", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("reply_reason_code <=", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLike(String value) {
            addCriterion("reply_reason_code like", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotLike(String value) {
            addCriterion("reply_reason_code not like", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIn(List<String> values) {
            addCriterion("reply_reason_code in", values, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotIn(List<String> values) {
            addCriterion("reply_reason_code not in", values, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeBetween(String value1, String value2) {
            addCriterion("reply_reason_code between", value1, value2, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotBetween(String value1, String value2) {
            addCriterion("reply_reason_code not between", value1, value2, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIsNull() {
            addCriterion("reply_reason is null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIsNotNull() {
            addCriterion("reply_reason is not null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonEqualTo(String value) {
            addCriterion("reply_reason =", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotEqualTo(String value) {
            addCriterion("reply_reason <>", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonGreaterThan(String value) {
            addCriterion("reply_reason >", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reply_reason >=", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLessThan(String value) {
            addCriterion("reply_reason <", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLessThanOrEqualTo(String value) {
            addCriterion("reply_reason <=", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLike(String value) {
            addCriterion("reply_reason like", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotLike(String value) {
            addCriterion("reply_reason not like", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIn(List<String> values) {
            addCriterion("reply_reason in", values, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotIn(List<String> values) {
            addCriterion("reply_reason not in", values, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonBetween(String value1, String value2) {
            addCriterion("reply_reason between", value1, value2, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotBetween(String value1, String value2) {
            addCriterion("reply_reason not between", value1, value2, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNull() {
            addCriterion("reply_remark is null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNotNull() {
            addCriterion("reply_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkEqualTo(String value) {
            addCriterion("reply_remark =", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotEqualTo(String value) {
            addCriterion("reply_remark <>", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThan(String value) {
            addCriterion("reply_remark >", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reply_remark >=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThan(String value) {
            addCriterion("reply_remark <", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThanOrEqualTo(String value) {
            addCriterion("reply_remark <=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLike(String value) {
            addCriterion("reply_remark like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotLike(String value) {
            addCriterion("reply_remark not like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIn(List<String> values) {
            addCriterion("reply_remark in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotIn(List<String> values) {
            addCriterion("reply_remark not in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkBetween(String value1, String value2) {
            addCriterion("reply_remark between", value1, value2, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotBetween(String value1, String value2) {
            addCriterion("reply_remark not between", value1, value2, "replyRemark");
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
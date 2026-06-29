package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinOrderCalcExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BinOrderCalcExample() {
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

        public Criteria andCalcTimeIsNull() {
            addCriterion("calc_time is null");
            return (Criteria) this;
        }

        public Criteria andCalcTimeIsNotNull() {
            addCriterion("calc_time is not null");
            return (Criteria) this;
        }

        public Criteria andCalcTimeEqualTo(Date value) {
            addCriterion("calc_time =", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeNotEqualTo(Date value) {
            addCriterion("calc_time <>", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeGreaterThan(Date value) {
            addCriterion("calc_time >", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("calc_time >=", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeLessThan(Date value) {
            addCriterion("calc_time <", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeLessThanOrEqualTo(Date value) {
            addCriterion("calc_time <=", value, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeIn(List<Date> values) {
            addCriterion("calc_time in", values, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeNotIn(List<Date> values) {
            addCriterion("calc_time not in", values, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeBetween(Date value1, Date value2) {
            addCriterion("calc_time between", value1, value2, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcTimeNotBetween(Date value1, Date value2) {
            addCriterion("calc_time not between", value1, value2, "calcTime");
            return (Criteria) this;
        }

        public Criteria andCalcPsnIsNull() {
            addCriterion("calc_psn is null");
            return (Criteria) this;
        }

        public Criteria andCalcPsnIsNotNull() {
            addCriterion("calc_psn is not null");
            return (Criteria) this;
        }

        public Criteria andCalcPsnEqualTo(String value) {
            addCriterion("calc_psn =", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnNotEqualTo(String value) {
            addCriterion("calc_psn <>", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnGreaterThan(String value) {
            addCriterion("calc_psn >", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnGreaterThanOrEqualTo(String value) {
            addCriterion("calc_psn >=", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnLessThan(String value) {
            addCriterion("calc_psn <", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnLessThanOrEqualTo(String value) {
            addCriterion("calc_psn <=", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnLike(String value) {
            addCriterion("calc_psn like", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnNotLike(String value) {
            addCriterion("calc_psn not like", value, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnIn(List<String> values) {
            addCriterion("calc_psn in", values, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnNotIn(List<String> values) {
            addCriterion("calc_psn not in", values, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnBetween(String value1, String value2) {
            addCriterion("calc_psn between", value1, value2, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andCalcPsnNotBetween(String value1, String value2) {
            addCriterion("calc_psn not between", value1, value2, "calcPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnIsNull() {
            addCriterion("confirm_psn is null");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnIsNotNull() {
            addCriterion("confirm_psn is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnEqualTo(String value) {
            addCriterion("confirm_psn =", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnNotEqualTo(String value) {
            addCriterion("confirm_psn <>", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnGreaterThan(String value) {
            addCriterion("confirm_psn >", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnGreaterThanOrEqualTo(String value) {
            addCriterion("confirm_psn >=", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnLessThan(String value) {
            addCriterion("confirm_psn <", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnLessThanOrEqualTo(String value) {
            addCriterion("confirm_psn <=", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnLike(String value) {
            addCriterion("confirm_psn like", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnNotLike(String value) {
            addCriterion("confirm_psn not like", value, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnIn(List<String> values) {
            addCriterion("confirm_psn in", values, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnNotIn(List<String> values) {
            addCriterion("confirm_psn not in", values, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnBetween(String value1, String value2) {
            addCriterion("confirm_psn between", value1, value2, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andConfirmPsnNotBetween(String value1, String value2) {
            addCriterion("confirm_psn not between", value1, value2, "confirmPsn");
            return (Criteria) this;
        }

        public Criteria andGssIsNull() {
            addCriterion("gss is null");
            return (Criteria) this;
        }

        public Criteria andGssIsNotNull() {
            addCriterion("gss is not null");
            return (Criteria) this;
        }

        public Criteria andGssEqualTo(Boolean value) {
            addCriterion("gss =", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssNotEqualTo(Boolean value) {
            addCriterion("gss <>", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssGreaterThan(Boolean value) {
            addCriterion("gss >", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssGreaterThanOrEqualTo(Boolean value) {
            addCriterion("gss >=", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssLessThan(Boolean value) {
            addCriterion("gss <", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssLessThanOrEqualTo(Boolean value) {
            addCriterion("gss <=", value, "gss");
            return (Criteria) this;
        }

        public Criteria andGssIn(List<Boolean> values) {
            addCriterion("gss in", values, "gss");
            return (Criteria) this;
        }

        public Criteria andGssNotIn(List<Boolean> values) {
            addCriterion("gss not in", values, "gss");
            return (Criteria) this;
        }

        public Criteria andGssBetween(Boolean value1, Boolean value2) {
            addCriterion("gss between", value1, value2, "gss");
            return (Criteria) this;
        }

        public Criteria andGssNotBetween(Boolean value1, Boolean value2) {
            addCriterion("gss not between", value1, value2, "gss");
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

        public Criteria andCalcTypeIsNull() {
            addCriterion("calc_type is null");
            return (Criteria) this;
        }

        public Criteria andCalcTypeIsNotNull() {
            addCriterion("calc_type is not null");
            return (Criteria) this;
        }

        public Criteria andCalcTypeEqualTo(Short value) {
            addCriterion("calc_type =", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeNotEqualTo(Short value) {
            addCriterion("calc_type <>", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeGreaterThan(Short value) {
            addCriterion("calc_type >", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("calc_type >=", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeLessThan(Short value) {
            addCriterion("calc_type <", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeLessThanOrEqualTo(Short value) {
            addCriterion("calc_type <=", value, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeIn(List<Short> values) {
            addCriterion("calc_type in", values, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeNotIn(List<Short> values) {
            addCriterion("calc_type not in", values, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeBetween(Short value1, Short value2) {
            addCriterion("calc_type between", value1, value2, "calcType");
            return (Criteria) this;
        }

        public Criteria andCalcTypeNotBetween(Short value1, Short value2) {
            addCriterion("calc_type not between", value1, value2, "calcType");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoIsNull() {
            addCriterion("custoemr_no is null");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoIsNotNull() {
            addCriterion("custoemr_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoEqualTo(String value) {
            addCriterion("custoemr_no =", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoNotEqualTo(String value) {
            addCriterion("custoemr_no <>", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoGreaterThan(String value) {
            addCriterion("custoemr_no >", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoGreaterThanOrEqualTo(String value) {
            addCriterion("custoemr_no >=", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoLessThan(String value) {
            addCriterion("custoemr_no <", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoLessThanOrEqualTo(String value) {
            addCriterion("custoemr_no <=", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoLike(String value) {
            addCriterion("custoemr_no like", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoNotLike(String value) {
            addCriterion("custoemr_no not like", value, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoIn(List<String> values) {
            addCriterion("custoemr_no in", values, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoNotIn(List<String> values) {
            addCriterion("custoemr_no not in", values, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoBetween(String value1, String value2) {
            addCriterion("custoemr_no between", value1, value2, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andCustoemrNoNotBetween(String value1, String value2) {
            addCriterion("custoemr_no not between", value1, value2, "custoemrNo");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Long value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Long value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Long value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Long value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Long> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Long> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Long value1, Long value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andReportTypeIsNull() {
            addCriterion("report_type is null");
            return (Criteria) this;
        }

        public Criteria andReportTypeIsNotNull() {
            addCriterion("report_type is not null");
            return (Criteria) this;
        }

        public Criteria andReportTypeEqualTo(Integer value) {
            addCriterion("report_type =", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeNotEqualTo(Integer value) {
            addCriterion("report_type <>", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeGreaterThan(Integer value) {
            addCriterion("report_type >", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("report_type >=", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeLessThan(Integer value) {
            addCriterion("report_type <", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeLessThanOrEqualTo(Integer value) {
            addCriterion("report_type <=", value, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeIn(List<Integer> values) {
            addCriterion("report_type in", values, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeNotIn(List<Integer> values) {
            addCriterion("report_type not in", values, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeBetween(Integer value1, Integer value2) {
            addCriterion("report_type between", value1, value2, "reportType");
            return (Criteria) this;
        }

        public Criteria andReportTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("report_type not between", value1, value2, "reportType");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeIsNull() {
            addCriterion("calc_finish_time is null");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeIsNotNull() {
            addCriterion("calc_finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeEqualTo(Date value) {
            addCriterion("calc_finish_time =", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeNotEqualTo(Date value) {
            addCriterion("calc_finish_time <>", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeGreaterThan(Date value) {
            addCriterion("calc_finish_time >", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("calc_finish_time >=", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeLessThan(Date value) {
            addCriterion("calc_finish_time <", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("calc_finish_time <=", value, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeIn(List<Date> values) {
            addCriterion("calc_finish_time in", values, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeNotIn(List<Date> values) {
            addCriterion("calc_finish_time not in", values, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeBetween(Date value1, Date value2) {
            addCriterion("calc_finish_time between", value1, value2, "calcFinishTime");
            return (Criteria) this;
        }

        public Criteria andCalcFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("calc_finish_time not between", value1, value2, "calcFinishTime");
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
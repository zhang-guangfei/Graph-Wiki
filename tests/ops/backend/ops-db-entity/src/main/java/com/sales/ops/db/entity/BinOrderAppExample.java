package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinOrderAppExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BinOrderAppExample() {
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

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Long value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Long value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Long value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Long value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Long value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Long value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Long> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Long> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Long value1, Long value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Long value1, Long value2) {
            addCriterion("app_id not between", value1, value2, "appId");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andAppUserIsNull() {
            addCriterion("app_user is null");
            return (Criteria) this;
        }

        public Criteria andAppUserIsNotNull() {
            addCriterion("app_user is not null");
            return (Criteria) this;
        }

        public Criteria andAppUserEqualTo(String value) {
            addCriterion("app_user =", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserNotEqualTo(String value) {
            addCriterion("app_user <>", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserGreaterThan(String value) {
            addCriterion("app_user >", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserGreaterThanOrEqualTo(String value) {
            addCriterion("app_user >=", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserLessThan(String value) {
            addCriterion("app_user <", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserLessThanOrEqualTo(String value) {
            addCriterion("app_user <=", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserLike(String value) {
            addCriterion("app_user like", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserNotLike(String value) {
            addCriterion("app_user not like", value, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserIn(List<String> values) {
            addCriterion("app_user in", values, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserNotIn(List<String> values) {
            addCriterion("app_user not in", values, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserBetween(String value1, String value2) {
            addCriterion("app_user between", value1, value2, "appUser");
            return (Criteria) this;
        }

        public Criteria andAppUserNotBetween(String value1, String value2) {
            addCriterion("app_user not between", value1, value2, "appUser");
            return (Criteria) this;
        }

        public Criteria andApproveTextIsNull() {
            addCriterion("approve_text is null");
            return (Criteria) this;
        }

        public Criteria andApproveTextIsNotNull() {
            addCriterion("approve_text is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTextEqualTo(String value) {
            addCriterion("approve_text =", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextNotEqualTo(String value) {
            addCriterion("approve_text <>", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextGreaterThan(String value) {
            addCriterion("approve_text >", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextGreaterThanOrEqualTo(String value) {
            addCriterion("approve_text >=", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextLessThan(String value) {
            addCriterion("approve_text <", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextLessThanOrEqualTo(String value) {
            addCriterion("approve_text <=", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextLike(String value) {
            addCriterion("approve_text like", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextNotLike(String value) {
            addCriterion("approve_text not like", value, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextIn(List<String> values) {
            addCriterion("approve_text in", values, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextNotIn(List<String> values) {
            addCriterion("approve_text not in", values, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextBetween(String value1, String value2) {
            addCriterion("approve_text between", value1, value2, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTextNotBetween(String value1, String value2) {
            addCriterion("approve_text not between", value1, value2, "approveText");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNull() {
            addCriterion("approve_time is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("approve_time is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("approve_time =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("approve_time <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("approve_time >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approve_time >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("approve_time <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("approve_time <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("approve_time in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("approve_time not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("approve_time between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("approve_time not between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApplyTextIsNull() {
            addCriterion("apply_text is null");
            return (Criteria) this;
        }

        public Criteria andApplyTextIsNotNull() {
            addCriterion("apply_text is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTextEqualTo(String value) {
            addCriterion("apply_text =", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextNotEqualTo(String value) {
            addCriterion("apply_text <>", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextGreaterThan(String value) {
            addCriterion("apply_text >", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextGreaterThanOrEqualTo(String value) {
            addCriterion("apply_text >=", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextLessThan(String value) {
            addCriterion("apply_text <", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextLessThanOrEqualTo(String value) {
            addCriterion("apply_text <=", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextLike(String value) {
            addCriterion("apply_text like", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextNotLike(String value) {
            addCriterion("apply_text not like", value, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextIn(List<String> values) {
            addCriterion("apply_text in", values, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextNotIn(List<String> values) {
            addCriterion("apply_text not in", values, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextBetween(String value1, String value2) {
            addCriterion("apply_text between", value1, value2, "applyText");
            return (Criteria) this;
        }

        public Criteria andApplyTextNotBetween(String value1, String value2) {
            addCriterion("apply_text not between", value1, value2, "applyText");
            return (Criteria) this;
        }

        public Criteria andModelCountIsNull() {
            addCriterion("model_count is null");
            return (Criteria) this;
        }

        public Criteria andModelCountIsNotNull() {
            addCriterion("model_count is not null");
            return (Criteria) this;
        }

        public Criteria andModelCountEqualTo(Integer value) {
            addCriterion("model_count =", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountNotEqualTo(Integer value) {
            addCriterion("model_count <>", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountGreaterThan(Integer value) {
            addCriterion("model_count >", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("model_count >=", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountLessThan(Integer value) {
            addCriterion("model_count <", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountLessThanOrEqualTo(Integer value) {
            addCriterion("model_count <=", value, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountIn(List<Integer> values) {
            addCriterion("model_count in", values, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountNotIn(List<Integer> values) {
            addCriterion("model_count not in", values, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountBetween(Integer value1, Integer value2) {
            addCriterion("model_count between", value1, value2, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelCountNotBetween(Integer value1, Integer value2) {
            addCriterion("model_count not between", value1, value2, "modelCount");
            return (Criteria) this;
        }

        public Criteria andModelQtyIsNull() {
            addCriterion("model_qty is null");
            return (Criteria) this;
        }

        public Criteria andModelQtyIsNotNull() {
            addCriterion("model_qty is not null");
            return (Criteria) this;
        }

        public Criteria andModelQtyEqualTo(Integer value) {
            addCriterion("model_qty =", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyNotEqualTo(Integer value) {
            addCriterion("model_qty <>", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyGreaterThan(Integer value) {
            addCriterion("model_qty >", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("model_qty >=", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyLessThan(Integer value) {
            addCriterion("model_qty <", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyLessThanOrEqualTo(Integer value) {
            addCriterion("model_qty <=", value, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyIn(List<Integer> values) {
            addCriterion("model_qty in", values, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyNotIn(List<Integer> values) {
            addCriterion("model_qty not in", values, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyBetween(Integer value1, Integer value2) {
            addCriterion("model_qty between", value1, value2, "modelQty");
            return (Criteria) this;
        }

        public Criteria andModelQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("model_qty not between", value1, value2, "modelQty");
            return (Criteria) this;
        }

        public Criteria andApproveUserIsNull() {
            addCriterion("approve_user is null");
            return (Criteria) this;
        }

        public Criteria andApproveUserIsNotNull() {
            addCriterion("approve_user is not null");
            return (Criteria) this;
        }

        public Criteria andApproveUserEqualTo(String value) {
            addCriterion("approve_user =", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotEqualTo(String value) {
            addCriterion("approve_user <>", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserGreaterThan(String value) {
            addCriterion("approve_user >", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserGreaterThanOrEqualTo(String value) {
            addCriterion("approve_user >=", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLessThan(String value) {
            addCriterion("approve_user <", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLessThanOrEqualTo(String value) {
            addCriterion("approve_user <=", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLike(String value) {
            addCriterion("approve_user like", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotLike(String value) {
            addCriterion("approve_user not like", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserIn(List<String> values) {
            addCriterion("approve_user in", values, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotIn(List<String> values) {
            addCriterion("approve_user not in", values, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserBetween(String value1, String value2) {
            addCriterion("approve_user between", value1, value2, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotBetween(String value1, String value2) {
            addCriterion("approve_user not between", value1, value2, "approveUser");
            return (Criteria) this;
        }

        public Criteria andCalcIdIsNull() {
            addCriterion("calc_id is null");
            return (Criteria) this;
        }

        public Criteria andCalcIdIsNotNull() {
            addCriterion("calc_id is not null");
            return (Criteria) this;
        }

        public Criteria andCalcIdEqualTo(Long value) {
            addCriterion("calc_id =", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdNotEqualTo(Long value) {
            addCriterion("calc_id <>", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdGreaterThan(Long value) {
            addCriterion("calc_id >", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdGreaterThanOrEqualTo(Long value) {
            addCriterion("calc_id >=", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdLessThan(Long value) {
            addCriterion("calc_id <", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdLessThanOrEqualTo(Long value) {
            addCriterion("calc_id <=", value, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdIn(List<Long> values) {
            addCriterion("calc_id in", values, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdNotIn(List<Long> values) {
            addCriterion("calc_id not in", values, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdBetween(Long value1, Long value2) {
            addCriterion("calc_id between", value1, value2, "calcId");
            return (Criteria) this;
        }

        public Criteria andCalcIdNotBetween(Long value1, Long value2) {
            addCriterion("calc_id not between", value1, value2, "calcId");
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

        public Criteria andStockTypeIsNull() {
            addCriterion("stock_type is null");
            return (Criteria) this;
        }

        public Criteria andStockTypeIsNotNull() {
            addCriterion("stock_type is not null");
            return (Criteria) this;
        }

        public Criteria andStockTypeEqualTo(Integer value) {
            addCriterion("stock_type =", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotEqualTo(Integer value) {
            addCriterion("stock_type <>", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThan(Integer value) {
            addCriterion("stock_type >", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_type >=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThan(Integer value) {
            addCriterion("stock_type <", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThanOrEqualTo(Integer value) {
            addCriterion("stock_type <=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeIn(List<Integer> values) {
            addCriterion("stock_type in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotIn(List<Integer> values) {
            addCriterion("stock_type not in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeBetween(Integer value1, Integer value2) {
            addCriterion("stock_type between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_type not between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
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

        public Criteria andEamountIsNull() {
            addCriterion("eAmount is null");
            return (Criteria) this;
        }

        public Criteria andEamountIsNotNull() {
            addCriterion("eAmount is not null");
            return (Criteria) this;
        }

        public Criteria andEamountEqualTo(BigDecimal value) {
            addCriterion("eAmount =", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountNotEqualTo(BigDecimal value) {
            addCriterion("eAmount <>", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountGreaterThan(BigDecimal value) {
            addCriterion("eAmount >", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("eAmount >=", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountLessThan(BigDecimal value) {
            addCriterion("eAmount <", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("eAmount <=", value, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountIn(List<BigDecimal> values) {
            addCriterion("eAmount in", values, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountNotIn(List<BigDecimal> values) {
            addCriterion("eAmount not in", values, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eAmount between", value1, value2, "eamount");
            return (Criteria) this;
        }

        public Criteria andEamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eAmount not between", value1, value2, "eamount");
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
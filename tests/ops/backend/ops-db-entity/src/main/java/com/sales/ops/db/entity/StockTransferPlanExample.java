package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockTransferPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StockTransferPlanExample() {
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

        public Criteria andPlanNoIsNull() {
            addCriterion("plan_no is null");
            return (Criteria) this;
        }

        public Criteria andPlanNoIsNotNull() {
            addCriterion("plan_no is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNoEqualTo(String value) {
            addCriterion("plan_no =", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoNotEqualTo(String value) {
            addCriterion("plan_no <>", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoGreaterThan(String value) {
            addCriterion("plan_no >", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoGreaterThanOrEqualTo(String value) {
            addCriterion("plan_no >=", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoLessThan(String value) {
            addCriterion("plan_no <", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoLessThanOrEqualTo(String value) {
            addCriterion("plan_no <=", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoLike(String value) {
            addCriterion("plan_no like", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoNotLike(String value) {
            addCriterion("plan_no not like", value, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoIn(List<String> values) {
            addCriterion("plan_no in", values, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoNotIn(List<String> values) {
            addCriterion("plan_no not in", values, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoBetween(String value1, String value2) {
            addCriterion("plan_no between", value1, value2, "planNo");
            return (Criteria) this;
        }

        public Criteria andPlanNoNotBetween(String value1, String value2) {
            addCriterion("plan_no not between", value1, value2, "planNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIsNull() {
            addCriterion("associate_no is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIsNotNull() {
            addCriterion("associate_no is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoEqualTo(String value) {
            addCriterion("associate_no =", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotEqualTo(String value) {
            addCriterion("associate_no <>", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThan(String value) {
            addCriterion("associate_no >", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoGreaterThanOrEqualTo(String value) {
            addCriterion("associate_no >=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThan(String value) {
            addCriterion("associate_no <", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLessThanOrEqualTo(String value) {
            addCriterion("associate_no <=", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoLike(String value) {
            addCriterion("associate_no like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotLike(String value) {
            addCriterion("associate_no not like", value, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoIn(List<String> values) {
            addCriterion("associate_no in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotIn(List<String> values) {
            addCriterion("associate_no not in", values, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoBetween(String value1, String value2) {
            addCriterion("associate_no between", value1, value2, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoNotBetween(String value1, String value2) {
            addCriterion("associate_no not between", value1, value2, "associateNo");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIsNull() {
            addCriterion("associate_no_item is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIsNotNull() {
            addCriterion("associate_no_item is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemEqualTo(Integer value) {
            addCriterion("associate_no_item =", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotEqualTo(Integer value) {
            addCriterion("associate_no_item <>", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemGreaterThan(Integer value) {
            addCriterion("associate_no_item >", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("associate_no_item >=", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemLessThan(Integer value) {
            addCriterion("associate_no_item <", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemLessThanOrEqualTo(Integer value) {
            addCriterion("associate_no_item <=", value, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemIn(List<Integer> values) {
            addCriterion("associate_no_item in", values, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotIn(List<Integer> values) {
            addCriterion("associate_no_item not in", values, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_item between", value1, value2, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_item not between", value1, value2, "associateNoItem");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIsNull() {
            addCriterion("associate_no_splitNo is null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIsNotNull() {
            addCriterion("associate_no_splitNo is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoEqualTo(Integer value) {
            addCriterion("associate_no_splitNo =", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotEqualTo(Integer value) {
            addCriterion("associate_no_splitNo <>", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoGreaterThan(Integer value) {
            addCriterion("associate_no_splitNo >", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("associate_no_splitNo >=", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoLessThan(Integer value) {
            addCriterion("associate_no_splitNo <", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoLessThanOrEqualTo(Integer value) {
            addCriterion("associate_no_splitNo <=", value, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoIn(List<Integer> values) {
            addCriterion("associate_no_splitNo in", values, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotIn(List<Integer> values) {
            addCriterion("associate_no_splitNo not in", values, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_splitNo between", value1, value2, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andAssociateNoSplitnoNotBetween(Integer value1, Integer value2) {
            addCriterion("associate_no_splitNo not between", value1, value2, "associateNoSplitno");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeIsNull() {
            addCriterion("init_inv_type_code is null");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeIsNotNull() {
            addCriterion("init_inv_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeEqualTo(String value) {
            addCriterion("init_inv_type_code =", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeNotEqualTo(String value) {
            addCriterion("init_inv_type_code <>", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeGreaterThan(String value) {
            addCriterion("init_inv_type_code >", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("init_inv_type_code >=", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeLessThan(String value) {
            addCriterion("init_inv_type_code <", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("init_inv_type_code <=", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeLike(String value) {
            addCriterion("init_inv_type_code like", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeNotLike(String value) {
            addCriterion("init_inv_type_code not like", value, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeIn(List<String> values) {
            addCriterion("init_inv_type_code in", values, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeNotIn(List<String> values) {
            addCriterion("init_inv_type_code not in", values, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeBetween(String value1, String value2) {
            addCriterion("init_inv_type_code between", value1, value2, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeCodeNotBetween(String value1, String value2) {
            addCriterion("init_inv_type_code not between", value1, value2, "initInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoIsNull() {
            addCriterion("init_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoIsNotNull() {
            addCriterion("init_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoEqualTo(String value) {
            addCriterion("init_customer_no =", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoNotEqualTo(String value) {
            addCriterion("init_customer_no <>", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoGreaterThan(String value) {
            addCriterion("init_customer_no >", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("init_customer_no >=", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoLessThan(String value) {
            addCriterion("init_customer_no <", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("init_customer_no <=", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoLike(String value) {
            addCriterion("init_customer_no like", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoNotLike(String value) {
            addCriterion("init_customer_no not like", value, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoIn(List<String> values) {
            addCriterion("init_customer_no in", values, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoNotIn(List<String> values) {
            addCriterion("init_customer_no not in", values, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoBetween(String value1, String value2) {
            addCriterion("init_customer_no between", value1, value2, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitCustomerNoNotBetween(String value1, String value2) {
            addCriterion("init_customer_no not between", value1, value2, "initCustomerNo");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueIsNull() {
            addCriterion("init_inv_type_value is null");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueIsNotNull() {
            addCriterion("init_inv_type_value is not null");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueEqualTo(String value) {
            addCriterion("init_inv_type_value =", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueNotEqualTo(String value) {
            addCriterion("init_inv_type_value <>", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueGreaterThan(String value) {
            addCriterion("init_inv_type_value >", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueGreaterThanOrEqualTo(String value) {
            addCriterion("init_inv_type_value >=", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueLessThan(String value) {
            addCriterion("init_inv_type_value <", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueLessThanOrEqualTo(String value) {
            addCriterion("init_inv_type_value <=", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueLike(String value) {
            addCriterion("init_inv_type_value like", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueNotLike(String value) {
            addCriterion("init_inv_type_value not like", value, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueIn(List<String> values) {
            addCriterion("init_inv_type_value in", values, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueNotIn(List<String> values) {
            addCriterion("init_inv_type_value not in", values, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueBetween(String value1, String value2) {
            addCriterion("init_inv_type_value between", value1, value2, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andInitInvTypeValueNotBetween(String value1, String value2) {
            addCriterion("init_inv_type_value not between", value1, value2, "initInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeIsNull() {
            addCriterion("goal_inv_type_code is null");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeIsNotNull() {
            addCriterion("goal_inv_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeEqualTo(String value) {
            addCriterion("goal_inv_type_code =", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeNotEqualTo(String value) {
            addCriterion("goal_inv_type_code <>", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeGreaterThan(String value) {
            addCriterion("goal_inv_type_code >", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("goal_inv_type_code >=", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeLessThan(String value) {
            addCriterion("goal_inv_type_code <", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("goal_inv_type_code <=", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeLike(String value) {
            addCriterion("goal_inv_type_code like", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeNotLike(String value) {
            addCriterion("goal_inv_type_code not like", value, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeIn(List<String> values) {
            addCriterion("goal_inv_type_code in", values, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeNotIn(List<String> values) {
            addCriterion("goal_inv_type_code not in", values, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeBetween(String value1, String value2) {
            addCriterion("goal_inv_type_code between", value1, value2, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeCodeNotBetween(String value1, String value2) {
            addCriterion("goal_inv_type_code not between", value1, value2, "goalInvTypeCode");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoIsNull() {
            addCriterion("goal_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoIsNotNull() {
            addCriterion("goal_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoEqualTo(String value) {
            addCriterion("goal_customer_no =", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoNotEqualTo(String value) {
            addCriterion("goal_customer_no <>", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoGreaterThan(String value) {
            addCriterion("goal_customer_no >", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("goal_customer_no >=", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoLessThan(String value) {
            addCriterion("goal_customer_no <", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("goal_customer_no <=", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoLike(String value) {
            addCriterion("goal_customer_no like", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoNotLike(String value) {
            addCriterion("goal_customer_no not like", value, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoIn(List<String> values) {
            addCriterion("goal_customer_no in", values, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoNotIn(List<String> values) {
            addCriterion("goal_customer_no not in", values, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoBetween(String value1, String value2) {
            addCriterion("goal_customer_no between", value1, value2, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalCustomerNoNotBetween(String value1, String value2) {
            addCriterion("goal_customer_no not between", value1, value2, "goalCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueIsNull() {
            addCriterion("goal_inv_type_value is null");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueIsNotNull() {
            addCriterion("goal_inv_type_value is not null");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueEqualTo(String value) {
            addCriterion("goal_inv_type_value =", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueNotEqualTo(String value) {
            addCriterion("goal_inv_type_value <>", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueGreaterThan(String value) {
            addCriterion("goal_inv_type_value >", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueGreaterThanOrEqualTo(String value) {
            addCriterion("goal_inv_type_value >=", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueLessThan(String value) {
            addCriterion("goal_inv_type_value <", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueLessThanOrEqualTo(String value) {
            addCriterion("goal_inv_type_value <=", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueLike(String value) {
            addCriterion("goal_inv_type_value like", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueNotLike(String value) {
            addCriterion("goal_inv_type_value not like", value, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueIn(List<String> values) {
            addCriterion("goal_inv_type_value in", values, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueNotIn(List<String> values) {
            addCriterion("goal_inv_type_value not in", values, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueBetween(String value1, String value2) {
            addCriterion("goal_inv_type_value between", value1, value2, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andGoalInvTypeValueNotBetween(String value1, String value2) {
            addCriterion("goal_inv_type_value not between", value1, value2, "goalInvTypeValue");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelno is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelno is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelno =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelno <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelno >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelno >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelno <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelno <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelno like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelno not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelno in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelno not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelno between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelno not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIsNull() {
            addCriterion("plan_qty is null");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIsNotNull() {
            addCriterion("plan_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPlanQtyEqualTo(Integer value) {
            addCriterion("plan_qty =", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotEqualTo(Integer value) {
            addCriterion("plan_qty <>", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyGreaterThan(Integer value) {
            addCriterion("plan_qty >", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_qty >=", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyLessThan(Integer value) {
            addCriterion("plan_qty <", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyLessThanOrEqualTo(Integer value) {
            addCriterion("plan_qty <=", value, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyIn(List<Integer> values) {
            addCriterion("plan_qty in", values, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotIn(List<Integer> values) {
            addCriterion("plan_qty not in", values, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyBetween(Integer value1, Integer value2) {
            addCriterion("plan_qty between", value1, value2, "planQty");
            return (Criteria) this;
        }

        public Criteria andPlanQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_qty not between", value1, value2, "planQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyIsNull() {
            addCriterion("finish_qty is null");
            return (Criteria) this;
        }

        public Criteria andFinishQtyIsNotNull() {
            addCriterion("finish_qty is not null");
            return (Criteria) this;
        }

        public Criteria andFinishQtyEqualTo(Integer value) {
            addCriterion("finish_qty =", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyNotEqualTo(Integer value) {
            addCriterion("finish_qty <>", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyGreaterThan(Integer value) {
            addCriterion("finish_qty >", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_qty >=", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyLessThan(Integer value) {
            addCriterion("finish_qty <", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyLessThanOrEqualTo(Integer value) {
            addCriterion("finish_qty <=", value, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyIn(List<Integer> values) {
            addCriterion("finish_qty in", values, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyNotIn(List<Integer> values) {
            addCriterion("finish_qty not in", values, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyBetween(Integer value1, Integer value2) {
            addCriterion("finish_qty between", value1, value2, "finishQty");
            return (Criteria) this;
        }

        public Criteria andFinishQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_qty not between", value1, value2, "finishQty");
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

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
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

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
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

        public Criteria andUpdatorIsNull() {
            addCriterion("updator is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("updator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("updator =", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("updator <>", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("updator >", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("updator >=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("updator <", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("updator <=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("updator like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("updator not like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorIn(List<String> values) {
            addCriterion("updator in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotIn(List<String> values) {
            addCriterion("updator not in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("updator between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("updator not between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andDoidIsNull() {
            addCriterion("doId is null");
            return (Criteria) this;
        }

        public Criteria andDoidIsNotNull() {
            addCriterion("doId is not null");
            return (Criteria) this;
        }

        public Criteria andDoidEqualTo(String value) {
            addCriterion("doId =", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotEqualTo(String value) {
            addCriterion("doId <>", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidGreaterThan(String value) {
            addCriterion("doId >", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidGreaterThanOrEqualTo(String value) {
            addCriterion("doId >=", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLessThan(String value) {
            addCriterion("doId <", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLessThanOrEqualTo(String value) {
            addCriterion("doId <=", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLike(String value) {
            addCriterion("doId like", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotLike(String value) {
            addCriterion("doId not like", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidIn(List<String> values) {
            addCriterion("doId in", values, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotIn(List<String> values) {
            addCriterion("doId not in", values, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidBetween(String value1, String value2) {
            addCriterion("doId between", value1, value2, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotBetween(String value1, String value2) {
            addCriterion("doId not between", value1, value2, "doid");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsRequestpurchaseInterceptConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsRequestpurchaseInterceptConfigExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNull() {
            addCriterion("typeId is null");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNotNull() {
            addCriterion("typeId is not null");
            return (Criteria) this;
        }

        public Criteria andTypeidEqualTo(String value) {
            addCriterion("typeId =", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotEqualTo(String value) {
            addCriterion("typeId <>", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThan(String value) {
            addCriterion("typeId >", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThanOrEqualTo(String value) {
            addCriterion("typeId >=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThan(String value) {
            addCriterion("typeId <", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThanOrEqualTo(String value) {
            addCriterion("typeId <=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLike(String value) {
            addCriterion("typeId like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotLike(String value) {
            addCriterion("typeId not like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidIn(List<String> values) {
            addCriterion("typeId in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotIn(List<String> values) {
            addCriterion("typeId not in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidBetween(String value1, String value2) {
            addCriterion("typeId between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotBetween(String value1, String value2) {
            addCriterion("typeId not between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andRulekeyIsNull() {
            addCriterion("ruleKey is null");
            return (Criteria) this;
        }

        public Criteria andRulekeyIsNotNull() {
            addCriterion("ruleKey is not null");
            return (Criteria) this;
        }

        public Criteria andRulekeyEqualTo(String value) {
            addCriterion("ruleKey =", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyNotEqualTo(String value) {
            addCriterion("ruleKey <>", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyGreaterThan(String value) {
            addCriterion("ruleKey >", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyGreaterThanOrEqualTo(String value) {
            addCriterion("ruleKey >=", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyLessThan(String value) {
            addCriterion("ruleKey <", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyLessThanOrEqualTo(String value) {
            addCriterion("ruleKey <=", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyLike(String value) {
            addCriterion("ruleKey like", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyNotLike(String value) {
            addCriterion("ruleKey not like", value, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyIn(List<String> values) {
            addCriterion("ruleKey in", values, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyNotIn(List<String> values) {
            addCriterion("ruleKey not in", values, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyBetween(String value1, String value2) {
            addCriterion("ruleKey between", value1, value2, "rulekey");
            return (Criteria) this;
        }

        public Criteria andRulekeyNotBetween(String value1, String value2) {
            addCriterion("ruleKey not between", value1, value2, "rulekey");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
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

        public Criteria andEnableIsNull() {
            addCriterion("enable is null");
            return (Criteria) this;
        }

        public Criteria andEnableIsNotNull() {
            addCriterion("enable is not null");
            return (Criteria) this;
        }

        public Criteria andEnableEqualTo(Boolean value) {
            addCriterion("enable =", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotEqualTo(Boolean value) {
            addCriterion("enable <>", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThan(Boolean value) {
            addCriterion("enable >", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enable >=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThan(Boolean value) {
            addCriterion("enable <", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThanOrEqualTo(Boolean value) {
            addCriterion("enable <=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableIn(List<Boolean> values) {
            addCriterion("enable in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotIn(List<Boolean> values) {
            addCriterion("enable not in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableBetween(Boolean value1, Boolean value2) {
            addCriterion("enable between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enable not between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andDefaultactionIsNull() {
            addCriterion("defaultAction is null");
            return (Criteria) this;
        }

        public Criteria andDefaultactionIsNotNull() {
            addCriterion("defaultAction is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultactionEqualTo(String value) {
            addCriterion("defaultAction =", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionNotEqualTo(String value) {
            addCriterion("defaultAction <>", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionGreaterThan(String value) {
            addCriterion("defaultAction >", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionGreaterThanOrEqualTo(String value) {
            addCriterion("defaultAction >=", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionLessThan(String value) {
            addCriterion("defaultAction <", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionLessThanOrEqualTo(String value) {
            addCriterion("defaultAction <=", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionLike(String value) {
            addCriterion("defaultAction like", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionNotLike(String value) {
            addCriterion("defaultAction not like", value, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionIn(List<String> values) {
            addCriterion("defaultAction in", values, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionNotIn(List<String> values) {
            addCriterion("defaultAction not in", values, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionBetween(String value1, String value2) {
            addCriterion("defaultAction between", value1, value2, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andDefaultactionNotBetween(String value1, String value2) {
            addCriterion("defaultAction not between", value1, value2, "defaultaction");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutIsNull() {
            addCriterion("auto_stock_out is null");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutIsNotNull() {
            addCriterion("auto_stock_out is not null");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutEqualTo(Boolean value) {
            addCriterion("auto_stock_out =", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutNotEqualTo(Boolean value) {
            addCriterion("auto_stock_out <>", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutGreaterThan(Boolean value) {
            addCriterion("auto_stock_out >", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutGreaterThanOrEqualTo(Boolean value) {
            addCriterion("auto_stock_out >=", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutLessThan(Boolean value) {
            addCriterion("auto_stock_out <", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutLessThanOrEqualTo(Boolean value) {
            addCriterion("auto_stock_out <=", value, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutIn(List<Boolean> values) {
            addCriterion("auto_stock_out in", values, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutNotIn(List<Boolean> values) {
            addCriterion("auto_stock_out not in", values, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_stock_out between", value1, value2, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andAutoStockOutNotBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_stock_out not between", value1, value2, "autoStockOut");
            return (Criteria) this;
        }

        public Criteria andRuleKey1IsNull() {
            addCriterion("rule_key1 is null");
            return (Criteria) this;
        }

        public Criteria andRuleKey1IsNotNull() {
            addCriterion("rule_key1 is not null");
            return (Criteria) this;
        }

        public Criteria andRuleKey1EqualTo(String value) {
            addCriterion("rule_key1 =", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1NotEqualTo(String value) {
            addCriterion("rule_key1 <>", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1GreaterThan(String value) {
            addCriterion("rule_key1 >", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1GreaterThanOrEqualTo(String value) {
            addCriterion("rule_key1 >=", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1LessThan(String value) {
            addCriterion("rule_key1 <", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1LessThanOrEqualTo(String value) {
            addCriterion("rule_key1 <=", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1Like(String value) {
            addCriterion("rule_key1 like", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1NotLike(String value) {
            addCriterion("rule_key1 not like", value, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1In(List<String> values) {
            addCriterion("rule_key1 in", values, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1NotIn(List<String> values) {
            addCriterion("rule_key1 not in", values, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1Between(String value1, String value2) {
            addCriterion("rule_key1 between", value1, value2, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey1NotBetween(String value1, String value2) {
            addCriterion("rule_key1 not between", value1, value2, "ruleKey1");
            return (Criteria) this;
        }

        public Criteria andRuleKey2IsNull() {
            addCriterion("rule_key2 is null");
            return (Criteria) this;
        }

        public Criteria andRuleKey2IsNotNull() {
            addCriterion("rule_key2 is not null");
            return (Criteria) this;
        }

        public Criteria andRuleKey2EqualTo(Integer value) {
            addCriterion("rule_key2 =", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2NotEqualTo(Integer value) {
            addCriterion("rule_key2 <>", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2GreaterThan(Integer value) {
            addCriterion("rule_key2 >", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2GreaterThanOrEqualTo(Integer value) {
            addCriterion("rule_key2 >=", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2LessThan(Integer value) {
            addCriterion("rule_key2 <", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2LessThanOrEqualTo(Integer value) {
            addCriterion("rule_key2 <=", value, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2In(List<Integer> values) {
            addCriterion("rule_key2 in", values, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2NotIn(List<Integer> values) {
            addCriterion("rule_key2 not in", values, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2Between(Integer value1, Integer value2) {
            addCriterion("rule_key2 between", value1, value2, "ruleKey2");
            return (Criteria) this;
        }

        public Criteria andRuleKey2NotBetween(Integer value1, Integer value2) {
            addCriterion("rule_key2 not between", value1, value2, "ruleKey2");
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
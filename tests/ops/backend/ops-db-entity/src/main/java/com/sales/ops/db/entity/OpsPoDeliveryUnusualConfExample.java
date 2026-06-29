package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoDeliveryUnusualConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoDeliveryUnusualConfExample() {
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

        public Criteria andUnusualIdentificationCodeIsNull() {
            addCriterion("unusual_identification_code is null");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeIsNotNull() {
            addCriterion("unusual_identification_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeEqualTo(String value) {
            addCriterion("unusual_identification_code =", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotEqualTo(String value) {
            addCriterion("unusual_identification_code <>", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeGreaterThan(String value) {
            addCriterion("unusual_identification_code >", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_identification_code >=", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLessThan(String value) {
            addCriterion("unusual_identification_code <", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLessThanOrEqualTo(String value) {
            addCriterion("unusual_identification_code <=", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLike(String value) {
            addCriterion("unusual_identification_code like", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotLike(String value) {
            addCriterion("unusual_identification_code not like", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeIn(List<String> values) {
            addCriterion("unusual_identification_code in", values, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotIn(List<String> values) {
            addCriterion("unusual_identification_code not in", values, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeBetween(String value1, String value2) {
            addCriterion("unusual_identification_code between", value1, value2, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotBetween(String value1, String value2) {
            addCriterion("unusual_identification_code not between", value1, value2, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIsNull() {
            addCriterion("unusual_classify is null");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIsNotNull() {
            addCriterion("unusual_classify is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyEqualTo(String value) {
            addCriterion("unusual_classify =", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotEqualTo(String value) {
            addCriterion("unusual_classify <>", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyGreaterThan(String value) {
            addCriterion("unusual_classify >", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_classify >=", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLessThan(String value) {
            addCriterion("unusual_classify <", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLessThanOrEqualTo(String value) {
            addCriterion("unusual_classify <=", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLike(String value) {
            addCriterion("unusual_classify like", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotLike(String value) {
            addCriterion("unusual_classify not like", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIn(List<String> values) {
            addCriterion("unusual_classify in", values, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotIn(List<String> values) {
            addCriterion("unusual_classify not in", values, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyBetween(String value1, String value2) {
            addCriterion("unusual_classify between", value1, value2, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotBetween(String value1, String value2) {
            addCriterion("unusual_classify not between", value1, value2, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNull() {
            addCriterion("unusual_type is null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNotNull() {
            addCriterion("unusual_type is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeEqualTo(String value) {
            addCriterion("unusual_type =", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotEqualTo(String value) {
            addCriterion("unusual_type <>", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThan(String value) {
            addCriterion("unusual_type >", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_type >=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThan(String value) {
            addCriterion("unusual_type <", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThanOrEqualTo(String value) {
            addCriterion("unusual_type <=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLike(String value) {
            addCriterion("unusual_type like", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotLike(String value) {
            addCriterion("unusual_type not like", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIn(List<String> values) {
            addCriterion("unusual_type in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotIn(List<String> values) {
            addCriterion("unusual_type not in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeBetween(String value1, String value2) {
            addCriterion("unusual_type between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotBetween(String value1, String value2) {
            addCriterion("unusual_type not between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelIsNull() {
            addCriterion("unusual_level is null");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelIsNotNull() {
            addCriterion("unusual_level is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelEqualTo(Integer value) {
            addCriterion("unusual_level =", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelNotEqualTo(Integer value) {
            addCriterion("unusual_level <>", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelGreaterThan(Integer value) {
            addCriterion("unusual_level >", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("unusual_level >=", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelLessThan(Integer value) {
            addCriterion("unusual_level <", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelLessThanOrEqualTo(Integer value) {
            addCriterion("unusual_level <=", value, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelIn(List<Integer> values) {
            addCriterion("unusual_level in", values, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelNotIn(List<Integer> values) {
            addCriterion("unusual_level not in", values, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelBetween(Integer value1, Integer value2) {
            addCriterion("unusual_level between", value1, value2, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("unusual_level not between", value1, value2, "unusualLevel");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleIsNull() {
            addCriterion("unusual_rule is null");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleIsNotNull() {
            addCriterion("unusual_rule is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleEqualTo(String value) {
            addCriterion("unusual_rule =", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleNotEqualTo(String value) {
            addCriterion("unusual_rule <>", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleGreaterThan(String value) {
            addCriterion("unusual_rule >", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_rule >=", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleLessThan(String value) {
            addCriterion("unusual_rule <", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleLessThanOrEqualTo(String value) {
            addCriterion("unusual_rule <=", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleLike(String value) {
            addCriterion("unusual_rule like", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleNotLike(String value) {
            addCriterion("unusual_rule not like", value, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleIn(List<String> values) {
            addCriterion("unusual_rule in", values, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleNotIn(List<String> values) {
            addCriterion("unusual_rule not in", values, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleBetween(String value1, String value2) {
            addCriterion("unusual_rule between", value1, value2, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleNotBetween(String value1, String value2) {
            addCriterion("unusual_rule not between", value1, value2, "unusualRule");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionIsNull() {
            addCriterion("unusual_rule_condition is null");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionIsNotNull() {
            addCriterion("unusual_rule_condition is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionEqualTo(String value) {
            addCriterion("unusual_rule_condition =", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionNotEqualTo(String value) {
            addCriterion("unusual_rule_condition <>", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionGreaterThan(String value) {
            addCriterion("unusual_rule_condition >", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_rule_condition >=", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionLessThan(String value) {
            addCriterion("unusual_rule_condition <", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionLessThanOrEqualTo(String value) {
            addCriterion("unusual_rule_condition <=", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionLike(String value) {
            addCriterion("unusual_rule_condition like", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionNotLike(String value) {
            addCriterion("unusual_rule_condition not like", value, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionIn(List<String> values) {
            addCriterion("unusual_rule_condition in", values, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionNotIn(List<String> values) {
            addCriterion("unusual_rule_condition not in", values, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionBetween(String value1, String value2) {
            addCriterion("unusual_rule_condition between", value1, value2, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualRuleConditionNotBetween(String value1, String value2) {
            addCriterion("unusual_rule_condition not between", value1, value2, "unusualRuleCondition");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeIsNull() {
            addCriterion("unusual_code is null");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeIsNotNull() {
            addCriterion("unusual_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeEqualTo(String value) {
            addCriterion("unusual_code =", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeNotEqualTo(String value) {
            addCriterion("unusual_code <>", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeGreaterThan(String value) {
            addCriterion("unusual_code >", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_code >=", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeLessThan(String value) {
            addCriterion("unusual_code <", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeLessThanOrEqualTo(String value) {
            addCriterion("unusual_code <=", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeLike(String value) {
            addCriterion("unusual_code like", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeNotLike(String value) {
            addCriterion("unusual_code not like", value, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeIn(List<String> values) {
            addCriterion("unusual_code in", values, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeNotIn(List<String> values) {
            addCriterion("unusual_code not in", values, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeBetween(String value1, String value2) {
            addCriterion("unusual_code between", value1, value2, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualCodeNotBetween(String value1, String value2) {
            addCriterion("unusual_code not between", value1, value2, "unusualCode");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIsNull() {
            addCriterion("unusual_desc_eng is null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIsNotNull() {
            addCriterion("unusual_desc_eng is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngEqualTo(String value) {
            addCriterion("unusual_desc_eng =", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotEqualTo(String value) {
            addCriterion("unusual_desc_eng <>", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngGreaterThan(String value) {
            addCriterion("unusual_desc_eng >", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_desc_eng >=", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLessThan(String value) {
            addCriterion("unusual_desc_eng <", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLessThanOrEqualTo(String value) {
            addCriterion("unusual_desc_eng <=", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLike(String value) {
            addCriterion("unusual_desc_eng like", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotLike(String value) {
            addCriterion("unusual_desc_eng not like", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIn(List<String> values) {
            addCriterion("unusual_desc_eng in", values, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotIn(List<String> values) {
            addCriterion("unusual_desc_eng not in", values, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngBetween(String value1, String value2) {
            addCriterion("unusual_desc_eng between", value1, value2, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotBetween(String value1, String value2) {
            addCriterion("unusual_desc_eng not between", value1, value2, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnIsNull() {
            addCriterion("unusual_desc_chn is null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnIsNotNull() {
            addCriterion("unusual_desc_chn is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnEqualTo(String value) {
            addCriterion("unusual_desc_chn =", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnNotEqualTo(String value) {
            addCriterion("unusual_desc_chn <>", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnGreaterThan(String value) {
            addCriterion("unusual_desc_chn >", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_desc_chn >=", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnLessThan(String value) {
            addCriterion("unusual_desc_chn <", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnLessThanOrEqualTo(String value) {
            addCriterion("unusual_desc_chn <=", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnLike(String value) {
            addCriterion("unusual_desc_chn like", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnNotLike(String value) {
            addCriterion("unusual_desc_chn not like", value, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnIn(List<String> values) {
            addCriterion("unusual_desc_chn in", values, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnNotIn(List<String> values) {
            addCriterion("unusual_desc_chn not in", values, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnBetween(String value1, String value2) {
            addCriterion("unusual_desc_chn between", value1, value2, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andUnusualDescChnNotBetween(String value1, String value2) {
            addCriterion("unusual_desc_chn not between", value1, value2, "unusualDescChn");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobTypeEqualTo(Integer value) {
            addCriterion("job_type =", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotEqualTo(Integer value) {
            addCriterion("job_type <>", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThan(Integer value) {
            addCriterion("job_type >", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("job_type >=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThan(Integer value) {
            addCriterion("job_type <", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(Integer value) {
            addCriterion("job_type <=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIn(List<Integer> values) {
            addCriterion("job_type in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotIn(List<Integer> values) {
            addCriterion("job_type not in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeBetween(Integer value1, Integer value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeIsNull() {
            addCriterion("job_dept_code is null");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeIsNotNull() {
            addCriterion("job_dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeEqualTo(String value) {
            addCriterion("job_dept_code =", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeNotEqualTo(String value) {
            addCriterion("job_dept_code <>", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeGreaterThan(String value) {
            addCriterion("job_dept_code >", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("job_dept_code >=", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeLessThan(String value) {
            addCriterion("job_dept_code <", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("job_dept_code <=", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeLike(String value) {
            addCriterion("job_dept_code like", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeNotLike(String value) {
            addCriterion("job_dept_code not like", value, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeIn(List<String> values) {
            addCriterion("job_dept_code in", values, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeNotIn(List<String> values) {
            addCriterion("job_dept_code not in", values, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeBetween(String value1, String value2) {
            addCriterion("job_dept_code between", value1, value2, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptCodeNotBetween(String value1, String value2) {
            addCriterion("job_dept_code not between", value1, value2, "jobDeptCode");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameIsNull() {
            addCriterion("job_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameIsNotNull() {
            addCriterion("job_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameEqualTo(String value) {
            addCriterion("job_dept_name =", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameNotEqualTo(String value) {
            addCriterion("job_dept_name <>", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameGreaterThan(String value) {
            addCriterion("job_dept_name >", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_dept_name >=", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameLessThan(String value) {
            addCriterion("job_dept_name <", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameLessThanOrEqualTo(String value) {
            addCriterion("job_dept_name <=", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameLike(String value) {
            addCriterion("job_dept_name like", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameNotLike(String value) {
            addCriterion("job_dept_name not like", value, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameIn(List<String> values) {
            addCriterion("job_dept_name in", values, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameNotIn(List<String> values) {
            addCriterion("job_dept_name not in", values, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameBetween(String value1, String value2) {
            addCriterion("job_dept_name between", value1, value2, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobDeptNameNotBetween(String value1, String value2) {
            addCriterion("job_dept_name not between", value1, value2, "jobDeptName");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeIsNull() {
            addCriterion("job_user_code is null");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeIsNotNull() {
            addCriterion("job_user_code is not null");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeEqualTo(String value) {
            addCriterion("job_user_code =", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeNotEqualTo(String value) {
            addCriterion("job_user_code <>", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeGreaterThan(String value) {
            addCriterion("job_user_code >", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("job_user_code >=", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeLessThan(String value) {
            addCriterion("job_user_code <", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeLessThanOrEqualTo(String value) {
            addCriterion("job_user_code <=", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeLike(String value) {
            addCriterion("job_user_code like", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeNotLike(String value) {
            addCriterion("job_user_code not like", value, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeIn(List<String> values) {
            addCriterion("job_user_code in", values, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeNotIn(List<String> values) {
            addCriterion("job_user_code not in", values, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeBetween(String value1, String value2) {
            addCriterion("job_user_code between", value1, value2, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserCodeNotBetween(String value1, String value2) {
            addCriterion("job_user_code not between", value1, value2, "jobUserCode");
            return (Criteria) this;
        }

        public Criteria andJobUserNameIsNull() {
            addCriterion("job_user_name is null");
            return (Criteria) this;
        }

        public Criteria andJobUserNameIsNotNull() {
            addCriterion("job_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobUserNameEqualTo(String value) {
            addCriterion("job_user_name =", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameNotEqualTo(String value) {
            addCriterion("job_user_name <>", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameGreaterThan(String value) {
            addCriterion("job_user_name >", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_user_name >=", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameLessThan(String value) {
            addCriterion("job_user_name <", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameLessThanOrEqualTo(String value) {
            addCriterion("job_user_name <=", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameLike(String value) {
            addCriterion("job_user_name like", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameNotLike(String value) {
            addCriterion("job_user_name not like", value, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameIn(List<String> values) {
            addCriterion("job_user_name in", values, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameNotIn(List<String> values) {
            addCriterion("job_user_name not in", values, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameBetween(String value1, String value2) {
            addCriterion("job_user_name between", value1, value2, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserNameNotBetween(String value1, String value2) {
            addCriterion("job_user_name not between", value1, value2, "jobUserName");
            return (Criteria) this;
        }

        public Criteria andJobUserMailIsNull() {
            addCriterion("job_user_mail is null");
            return (Criteria) this;
        }

        public Criteria andJobUserMailIsNotNull() {
            addCriterion("job_user_mail is not null");
            return (Criteria) this;
        }

        public Criteria andJobUserMailEqualTo(String value) {
            addCriterion("job_user_mail =", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailNotEqualTo(String value) {
            addCriterion("job_user_mail <>", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailGreaterThan(String value) {
            addCriterion("job_user_mail >", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailGreaterThanOrEqualTo(String value) {
            addCriterion("job_user_mail >=", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailLessThan(String value) {
            addCriterion("job_user_mail <", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailLessThanOrEqualTo(String value) {
            addCriterion("job_user_mail <=", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailLike(String value) {
            addCriterion("job_user_mail like", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailNotLike(String value) {
            addCriterion("job_user_mail not like", value, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailIn(List<String> values) {
            addCriterion("job_user_mail in", values, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailNotIn(List<String> values) {
            addCriterion("job_user_mail not in", values, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailBetween(String value1, String value2) {
            addCriterion("job_user_mail between", value1, value2, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobUserMailNotBetween(String value1, String value2) {
            addCriterion("job_user_mail not between", value1, value2, "jobUserMail");
            return (Criteria) this;
        }

        public Criteria andJobHandleIsNull() {
            addCriterion("job_handle is null");
            return (Criteria) this;
        }

        public Criteria andJobHandleIsNotNull() {
            addCriterion("job_handle is not null");
            return (Criteria) this;
        }

        public Criteria andJobHandleEqualTo(String value) {
            addCriterion("job_handle =", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleNotEqualTo(String value) {
            addCriterion("job_handle <>", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleGreaterThan(String value) {
            addCriterion("job_handle >", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleGreaterThanOrEqualTo(String value) {
            addCriterion("job_handle >=", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleLessThan(String value) {
            addCriterion("job_handle <", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleLessThanOrEqualTo(String value) {
            addCriterion("job_handle <=", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleLike(String value) {
            addCriterion("job_handle like", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleNotLike(String value) {
            addCriterion("job_handle not like", value, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleIn(List<String> values) {
            addCriterion("job_handle in", values, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleNotIn(List<String> values) {
            addCriterion("job_handle not in", values, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleBetween(String value1, String value2) {
            addCriterion("job_handle between", value1, value2, "jobHandle");
            return (Criteria) this;
        }

        public Criteria andJobHandleNotBetween(String value1, String value2) {
            addCriterion("job_handle not between", value1, value2, "jobHandle");
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

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Boolean value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Boolean value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Boolean value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Boolean value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Boolean> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Boolean> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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
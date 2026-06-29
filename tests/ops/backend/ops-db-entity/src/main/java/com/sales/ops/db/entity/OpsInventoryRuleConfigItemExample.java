package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInventoryRuleConfigItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInventoryRuleConfigItemExample() {
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

        public Criteria andInventoryRuleConfigItemIdIsNull() {
            addCriterion("inventory_rule_config_item_ID is null");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdIsNotNull() {
            addCriterion("inventory_rule_config_item_ID is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdEqualTo(Integer value) {
            addCriterion("inventory_rule_config_item_ID =", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdNotEqualTo(Integer value) {
            addCriterion("inventory_rule_config_item_ID <>", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdGreaterThan(Integer value) {
            addCriterion("inventory_rule_config_item_ID >", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inventory_rule_config_item_ID >=", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdLessThan(Integer value) {
            addCriterion("inventory_rule_config_item_ID <", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdLessThanOrEqualTo(Integer value) {
            addCriterion("inventory_rule_config_item_ID <=", value, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdIn(List<Integer> values) {
            addCriterion("inventory_rule_config_item_ID in", values, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdNotIn(List<Integer> values) {
            addCriterion("inventory_rule_config_item_ID not in", values, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdBetween(Integer value1, Integer value2) {
            addCriterion("inventory_rule_config_item_ID between", value1, value2, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andInventoryRuleConfigItemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inventory_rule_config_item_ID not between", value1, value2, "inventoryRuleConfigItemId");
            return (Criteria) this;
        }

        public Criteria andRuleCodeIsNull() {
            addCriterion("rule_code is null");
            return (Criteria) this;
        }

        public Criteria andRuleCodeIsNotNull() {
            addCriterion("rule_code is not null");
            return (Criteria) this;
        }

        public Criteria andRuleCodeEqualTo(String value) {
            addCriterion("rule_code =", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeNotEqualTo(String value) {
            addCriterion("rule_code <>", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeGreaterThan(String value) {
            addCriterion("rule_code >", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("rule_code >=", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeLessThan(String value) {
            addCriterion("rule_code <", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeLessThanOrEqualTo(String value) {
            addCriterion("rule_code <=", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeLike(String value) {
            addCriterion("rule_code like", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeNotLike(String value) {
            addCriterion("rule_code not like", value, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeIn(List<String> values) {
            addCriterion("rule_code in", values, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeNotIn(List<String> values) {
            addCriterion("rule_code not in", values, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeBetween(String value1, String value2) {
            addCriterion("rule_code between", value1, value2, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleCodeNotBetween(String value1, String value2) {
            addCriterion("rule_code not between", value1, value2, "ruleCode");
            return (Criteria) this;
        }

        public Criteria andRuleItemIsNull() {
            addCriterion("rule_item is null");
            return (Criteria) this;
        }

        public Criteria andRuleItemIsNotNull() {
            addCriterion("rule_item is not null");
            return (Criteria) this;
        }

        public Criteria andRuleItemEqualTo(Integer value) {
            addCriterion("rule_item =", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemNotEqualTo(Integer value) {
            addCriterion("rule_item <>", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemGreaterThan(Integer value) {
            addCriterion("rule_item >", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("rule_item >=", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemLessThan(Integer value) {
            addCriterion("rule_item <", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemLessThanOrEqualTo(Integer value) {
            addCriterion("rule_item <=", value, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemIn(List<Integer> values) {
            addCriterion("rule_item in", values, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemNotIn(List<Integer> values) {
            addCriterion("rule_item not in", values, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemBetween(Integer value1, Integer value2) {
            addCriterion("rule_item between", value1, value2, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andRuleItemNotBetween(Integer value1, Integer value2) {
            addCriterion("rule_item not between", value1, value2, "ruleItem");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeIsNull() {
            addCriterion("inventory_match_code is null");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeIsNotNull() {
            addCriterion("inventory_match_code is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeEqualTo(String value) {
            addCriterion("inventory_match_code =", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeNotEqualTo(String value) {
            addCriterion("inventory_match_code <>", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeGreaterThan(String value) {
            addCriterion("inventory_match_code >", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_match_code >=", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeLessThan(String value) {
            addCriterion("inventory_match_code <", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeLessThanOrEqualTo(String value) {
            addCriterion("inventory_match_code <=", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeLike(String value) {
            addCriterion("inventory_match_code like", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeNotLike(String value) {
            addCriterion("inventory_match_code not like", value, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeIn(List<String> values) {
            addCriterion("inventory_match_code in", values, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeNotIn(List<String> values) {
            addCriterion("inventory_match_code not in", values, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeBetween(String value1, String value2) {
            addCriterion("inventory_match_code between", value1, value2, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andInventoryMatchCodeNotBetween(String value1, String value2) {
            addCriterion("inventory_match_code not between", value1, value2, "inventoryMatchCode");
            return (Criteria) this;
        }

        public Criteria andRuleSortIsNull() {
            addCriterion("rule_sort is null");
            return (Criteria) this;
        }

        public Criteria andRuleSortIsNotNull() {
            addCriterion("rule_sort is not null");
            return (Criteria) this;
        }

        public Criteria andRuleSortEqualTo(Integer value) {
            addCriterion("rule_sort =", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortNotEqualTo(Integer value) {
            addCriterion("rule_sort <>", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortGreaterThan(Integer value) {
            addCriterion("rule_sort >", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("rule_sort >=", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortLessThan(Integer value) {
            addCriterion("rule_sort <", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortLessThanOrEqualTo(Integer value) {
            addCriterion("rule_sort <=", value, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortIn(List<Integer> values) {
            addCriterion("rule_sort in", values, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortNotIn(List<Integer> values) {
            addCriterion("rule_sort not in", values, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortBetween(Integer value1, Integer value2) {
            addCriterion("rule_sort between", value1, value2, "ruleSort");
            return (Criteria) this;
        }

        public Criteria andRuleSortNotBetween(Integer value1, Integer value2) {
            addCriterion("rule_sort not between", value1, value2, "ruleSort");
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

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
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

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
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
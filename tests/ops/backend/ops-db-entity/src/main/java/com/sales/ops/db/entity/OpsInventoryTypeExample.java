package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInventoryTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInventoryTypeExample() {
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

        public Criteria andInventoryTypeCodeIsNull() {
            addCriterion("inventory_type_code is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIsNotNull() {
            addCriterion("inventory_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeEqualTo(String value) {
            addCriterion("inventory_type_code =", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotEqualTo(String value) {
            addCriterion("inventory_type_code <>", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThan(String value) {
            addCriterion("inventory_type_code >", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_type_code >=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThan(String value) {
            addCriterion("inventory_type_code <", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("inventory_type_code <=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLike(String value) {
            addCriterion("inventory_type_code like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotLike(String value) {
            addCriterion("inventory_type_code not like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIn(List<String> values) {
            addCriterion("inventory_type_code in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotIn(List<String> values) {
            addCriterion("inventory_type_code not in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeBetween(String value1, String value2) {
            addCriterion("inventory_type_code between", value1, value2, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotBetween(String value1, String value2) {
            addCriterion("inventory_type_code not between", value1, value2, "inventoryTypeCode");
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

        public Criteria andMatchCustomerNoIsNull() {
            addCriterion("match_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoIsNotNull() {
            addCriterion("match_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoEqualTo(Boolean value) {
            addCriterion("match_customer_no =", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoNotEqualTo(Boolean value) {
            addCriterion("match_customer_no <>", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoGreaterThan(Boolean value) {
            addCriterion("match_customer_no >", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("match_customer_no >=", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoLessThan(Boolean value) {
            addCriterion("match_customer_no <", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoLessThanOrEqualTo(Boolean value) {
            addCriterion("match_customer_no <=", value, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoIn(List<Boolean> values) {
            addCriterion("match_customer_no in", values, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoNotIn(List<Boolean> values) {
            addCriterion("match_customer_no not in", values, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoBetween(Boolean value1, Boolean value2) {
            addCriterion("match_customer_no between", value1, value2, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchCustomerNoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("match_customer_no not between", value1, value2, "matchCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeIsNull() {
            addCriterion("match_project_code is null");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeIsNotNull() {
            addCriterion("match_project_code is not null");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeEqualTo(Boolean value) {
            addCriterion("match_project_code =", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeNotEqualTo(Boolean value) {
            addCriterion("match_project_code <>", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeGreaterThan(Boolean value) {
            addCriterion("match_project_code >", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("match_project_code >=", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeLessThan(Boolean value) {
            addCriterion("match_project_code <", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeLessThanOrEqualTo(Boolean value) {
            addCriterion("match_project_code <=", value, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeIn(List<Boolean> values) {
            addCriterion("match_project_code in", values, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeNotIn(List<Boolean> values) {
            addCriterion("match_project_code not in", values, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeBetween(Boolean value1, Boolean value2) {
            addCriterion("match_project_code between", value1, value2, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMatchProjectCodeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("match_project_code not between", value1, value2, "matchProjectCode");
            return (Criteria) this;
        }

        public Criteria andMathchPplIsNull() {
            addCriterion("mathch_ppl is null");
            return (Criteria) this;
        }

        public Criteria andMathchPplIsNotNull() {
            addCriterion("mathch_ppl is not null");
            return (Criteria) this;
        }

        public Criteria andMathchPplEqualTo(Boolean value) {
            addCriterion("mathch_ppl =", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplNotEqualTo(Boolean value) {
            addCriterion("mathch_ppl <>", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplGreaterThan(Boolean value) {
            addCriterion("mathch_ppl >", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mathch_ppl >=", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplLessThan(Boolean value) {
            addCriterion("mathch_ppl <", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplLessThanOrEqualTo(Boolean value) {
            addCriterion("mathch_ppl <=", value, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplIn(List<Boolean> values) {
            addCriterion("mathch_ppl in", values, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplNotIn(List<Boolean> values) {
            addCriterion("mathch_ppl not in", values, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_ppl between", value1, value2, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMathchPplNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_ppl not between", value1, value2, "mathchPpl");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoIsNull() {
            addCriterion("match_group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoIsNotNull() {
            addCriterion("match_group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoEqualTo(Boolean value) {
            addCriterion("match_group_customer_no =", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoNotEqualTo(Boolean value) {
            addCriterion("match_group_customer_no <>", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoGreaterThan(Boolean value) {
            addCriterion("match_group_customer_no >", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("match_group_customer_no >=", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoLessThan(Boolean value) {
            addCriterion("match_group_customer_no <", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoLessThanOrEqualTo(Boolean value) {
            addCriterion("match_group_customer_no <=", value, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoIn(List<Boolean> values) {
            addCriterion("match_group_customer_no in", values, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoNotIn(List<Boolean> values) {
            addCriterion("match_group_customer_no not in", values, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoBetween(Boolean value1, Boolean value2) {
            addCriterion("match_group_customer_no between", value1, value2, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerNoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("match_group_customer_no not between", value1, value2, "matchGroupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeIsNull() {
            addCriterion("match_group_customer_type is null");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeIsNotNull() {
            addCriterion("match_group_customer_type is not null");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeEqualTo(String value) {
            addCriterion("match_group_customer_type =", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeNotEqualTo(String value) {
            addCriterion("match_group_customer_type <>", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeGreaterThan(String value) {
            addCriterion("match_group_customer_type >", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("match_group_customer_type >=", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeLessThan(String value) {
            addCriterion("match_group_customer_type <", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeLessThanOrEqualTo(String value) {
            addCriterion("match_group_customer_type <=", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeLike(String value) {
            addCriterion("match_group_customer_type like", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeNotLike(String value) {
            addCriterion("match_group_customer_type not like", value, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeIn(List<String> values) {
            addCriterion("match_group_customer_type in", values, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeNotIn(List<String> values) {
            addCriterion("match_group_customer_type not in", values, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeBetween(String value1, String value2) {
            addCriterion("match_group_customer_type between", value1, value2, "matchGroupCustomerType");
            return (Criteria) this;
        }

        public Criteria andMatchGroupCustomerTypeNotBetween(String value1, String value2) {
            addCriterion("match_group_customer_type not between", value1, value2, "matchGroupCustomerType");
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

        public Criteria andMathchQbNoIsNull() {
            addCriterion("mathch_qb_no is null");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoIsNotNull() {
            addCriterion("mathch_qb_no is not null");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoEqualTo(Boolean value) {
            addCriterion("mathch_qb_no =", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoNotEqualTo(Boolean value) {
            addCriterion("mathch_qb_no <>", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoGreaterThan(Boolean value) {
            addCriterion("mathch_qb_no >", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mathch_qb_no >=", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoLessThan(Boolean value) {
            addCriterion("mathch_qb_no <", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoLessThanOrEqualTo(Boolean value) {
            addCriterion("mathch_qb_no <=", value, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoIn(List<Boolean> values) {
            addCriterion("mathch_qb_no in", values, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoNotIn(List<Boolean> values) {
            addCriterion("mathch_qb_no not in", values, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_qb_no between", value1, value2, "mathchQbNo");
            return (Criteria) this;
        }

        public Criteria andMathchQbNoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_qb_no not between", value1, value2, "mathchQbNo");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInventoryMatchConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInventoryMatchConfigExample() {
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

        public Criteria andMatchWarehouseTypeCodeIsNull() {
            addCriterion("match_warehouse_type_code is null");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeIsNotNull() {
            addCriterion("match_warehouse_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeEqualTo(String value) {
            addCriterion("match_warehouse_type_code =", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeNotEqualTo(String value) {
            addCriterion("match_warehouse_type_code <>", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeGreaterThan(String value) {
            addCriterion("match_warehouse_type_code >", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("match_warehouse_type_code >=", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeLessThan(String value) {
            addCriterion("match_warehouse_type_code <", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("match_warehouse_type_code <=", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeLike(String value) {
            addCriterion("match_warehouse_type_code like", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeNotLike(String value) {
            addCriterion("match_warehouse_type_code not like", value, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeIn(List<String> values) {
            addCriterion("match_warehouse_type_code in", values, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeNotIn(List<String> values) {
            addCriterion("match_warehouse_type_code not in", values, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeBetween(String value1, String value2) {
            addCriterion("match_warehouse_type_code between", value1, value2, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchWarehouseTypeCodeNotBetween(String value1, String value2) {
            addCriterion("match_warehouse_type_code not between", value1, value2, "matchWarehouseTypeCode");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusIsNull() {
            addCriterion("match_inventory_status is null");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusIsNotNull() {
            addCriterion("match_inventory_status is not null");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusEqualTo(String value) {
            addCriterion("match_inventory_status =", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusNotEqualTo(String value) {
            addCriterion("match_inventory_status <>", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusGreaterThan(String value) {
            addCriterion("match_inventory_status >", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusGreaterThanOrEqualTo(String value) {
            addCriterion("match_inventory_status >=", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusLessThan(String value) {
            addCriterion("match_inventory_status <", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusLessThanOrEqualTo(String value) {
            addCriterion("match_inventory_status <=", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusLike(String value) {
            addCriterion("match_inventory_status like", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusNotLike(String value) {
            addCriterion("match_inventory_status not like", value, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusIn(List<String> values) {
            addCriterion("match_inventory_status in", values, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusNotIn(List<String> values) {
            addCriterion("match_inventory_status not in", values, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusBetween(String value1, String value2) {
            addCriterion("match_inventory_status between", value1, value2, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryStatusNotBetween(String value1, String value2) {
            addCriterion("match_inventory_status not between", value1, value2, "matchInventoryStatus");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateIsNull() {
            addCriterion("mathch_pre_rec_date is null");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateIsNotNull() {
            addCriterion("mathch_pre_rec_date is not null");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateEqualTo(Boolean value) {
            addCriterion("mathch_pre_rec_date =", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateNotEqualTo(Boolean value) {
            addCriterion("mathch_pre_rec_date <>", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateGreaterThan(Boolean value) {
            addCriterion("mathch_pre_rec_date >", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mathch_pre_rec_date >=", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateLessThan(Boolean value) {
            addCriterion("mathch_pre_rec_date <", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateLessThanOrEqualTo(Boolean value) {
            addCriterion("mathch_pre_rec_date <=", value, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateIn(List<Boolean> values) {
            addCriterion("mathch_pre_rec_date in", values, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateNotIn(List<Boolean> values) {
            addCriterion("mathch_pre_rec_date not in", values, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_pre_rec_date between", value1, value2, "mathchPreRecDate");
            return (Criteria) this;
        }

        public Criteria andMathchPreRecDateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mathch_pre_rec_date not between", value1, value2, "mathchPreRecDate");
            return (Criteria) this;
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

        public Criteria andMatchInventoryRiskIsNull() {
            addCriterion("match_inventory_risk is null");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskIsNotNull() {
            addCriterion("match_inventory_risk is not null");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskEqualTo(Boolean value) {
            addCriterion("match_inventory_risk =", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskNotEqualTo(Boolean value) {
            addCriterion("match_inventory_risk <>", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskGreaterThan(Boolean value) {
            addCriterion("match_inventory_risk >", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskGreaterThanOrEqualTo(Boolean value) {
            addCriterion("match_inventory_risk >=", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskLessThan(Boolean value) {
            addCriterion("match_inventory_risk <", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskLessThanOrEqualTo(Boolean value) {
            addCriterion("match_inventory_risk <=", value, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskIn(List<Boolean> values) {
            addCriterion("match_inventory_risk in", values, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskNotIn(List<Boolean> values) {
            addCriterion("match_inventory_risk not in", values, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskBetween(Boolean value1, Boolean value2) {
            addCriterion("match_inventory_risk between", value1, value2, "matchInventoryRisk");
            return (Criteria) this;
        }

        public Criteria andMatchInventoryRiskNotBetween(Boolean value1, Boolean value2) {
            addCriterion("match_inventory_risk not between", value1, value2, "matchInventoryRisk");
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
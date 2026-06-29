package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPcoItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPcoItemExample() {
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

        public Criteria andPcoIdIsNull() {
            addCriterion("pco_id is null");
            return (Criteria) this;
        }

        public Criteria andPcoIdIsNotNull() {
            addCriterion("pco_id is not null");
            return (Criteria) this;
        }

        public Criteria andPcoIdEqualTo(String value) {
            addCriterion("pco_id =", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotEqualTo(String value) {
            addCriterion("pco_id <>", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThan(String value) {
            addCriterion("pco_id >", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThanOrEqualTo(String value) {
            addCriterion("pco_id >=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThan(String value) {
            addCriterion("pco_id <", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThanOrEqualTo(String value) {
            addCriterion("pco_id <=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLike(String value) {
            addCriterion("pco_id like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotLike(String value) {
            addCriterion("pco_id not like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdIn(List<String> values) {
            addCriterion("pco_id in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotIn(List<String> values) {
            addCriterion("pco_id not in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdBetween(String value1, String value2) {
            addCriterion("pco_id between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotBetween(String value1, String value2) {
            addCriterion("pco_id not between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNull() {
            addCriterion("pco_item is null");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNotNull() {
            addCriterion("pco_item is not null");
            return (Criteria) this;
        }

        public Criteria andPcoItemEqualTo(Integer value) {
            addCriterion("pco_item =", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotEqualTo(Integer value) {
            addCriterion("pco_item <>", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThan(Integer value) {
            addCriterion("pco_item >", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_item >=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThan(Integer value) {
            addCriterion("pco_item <", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThanOrEqualTo(Integer value) {
            addCriterion("pco_item <=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemIn(List<Integer> values) {
            addCriterion("pco_item in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotIn(List<Integer> values) {
            addCriterion("pco_item not in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemBetween(Integer value1, Integer value2) {
            addCriterion("pco_item between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_item not between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNull() {
            addCriterion("sub_modelno is null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNotNull() {
            addCriterion("sub_modelno is not null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoEqualTo(String value) {
            addCriterion("sub_modelno =", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotEqualTo(String value) {
            addCriterion("sub_modelno <>", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThan(String value) {
            addCriterion("sub_modelno >", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("sub_modelno >=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThan(String value) {
            addCriterion("sub_modelno <", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThanOrEqualTo(String value) {
            addCriterion("sub_modelno <=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLike(String value) {
            addCriterion("sub_modelno like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotLike(String value) {
            addCriterion("sub_modelno not like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIn(List<String> values) {
            addCriterion("sub_modelno in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotIn(List<String> values) {
            addCriterion("sub_modelno not in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoBetween(String value1, String value2) {
            addCriterion("sub_modelno between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotBetween(String value1, String value2) {
            addCriterion("sub_modelno not between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNull() {
            addCriterion("sub_qty is null");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNotNull() {
            addCriterion("sub_qty is not null");
            return (Criteria) this;
        }

        public Criteria andSubQtyEqualTo(Integer value) {
            addCriterion("sub_qty =", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotEqualTo(Integer value) {
            addCriterion("sub_qty <>", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThan(Integer value) {
            addCriterion("sub_qty >", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_qty >=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThan(Integer value) {
            addCriterion("sub_qty <", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThanOrEqualTo(Integer value) {
            addCriterion("sub_qty <=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyIn(List<Integer> values) {
            addCriterion("sub_qty in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotIn(List<Integer> values) {
            addCriterion("sub_qty not in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty between", value1, value2, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty not between", value1, value2, "subQty");
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

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNull() {
            addCriterion("wait_type is null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNotNull() {
            addCriterion("wait_type is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeEqualTo(String value) {
            addCriterion("wait_type =", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotEqualTo(String value) {
            addCriterion("wait_type <>", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThan(String value) {
            addCriterion("wait_type >", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("wait_type >=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThan(String value) {
            addCriterion("wait_type <", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThanOrEqualTo(String value) {
            addCriterion("wait_type <=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLike(String value) {
            addCriterion("wait_type like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotLike(String value) {
            addCriterion("wait_type not like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIn(List<String> values) {
            addCriterion("wait_type in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotIn(List<String> values) {
            addCriterion("wait_type not in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeBetween(String value1, String value2) {
            addCriterion("wait_type between", value1, value2, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotBetween(String value1, String value2) {
            addCriterion("wait_type not between", value1, value2, "waitType");
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

        public Criteria andIsCrossIsNull() {
            addCriterion("is_cross is null");
            return (Criteria) this;
        }

        public Criteria andIsCrossIsNotNull() {
            addCriterion("is_cross is not null");
            return (Criteria) this;
        }

        public Criteria andIsCrossEqualTo(Boolean value) {
            addCriterion("is_cross =", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossNotEqualTo(Boolean value) {
            addCriterion("is_cross <>", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossGreaterThan(Boolean value) {
            addCriterion("is_cross >", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_cross >=", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossLessThan(Boolean value) {
            addCriterion("is_cross <", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossLessThanOrEqualTo(Boolean value) {
            addCriterion("is_cross <=", value, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossIn(List<Boolean> values) {
            addCriterion("is_cross in", values, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossNotIn(List<Boolean> values) {
            addCriterion("is_cross not in", values, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cross between", value1, value2, "isCross");
            return (Criteria) this;
        }

        public Criteria andIsCrossNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cross not between", value1, value2, "isCross");
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

        public Criteria andRelocationIsNull() {
            addCriterion("relocation is null");
            return (Criteria) this;
        }

        public Criteria andRelocationIsNotNull() {
            addCriterion("relocation is not null");
            return (Criteria) this;
        }

        public Criteria andRelocationEqualTo(String value) {
            addCriterion("relocation =", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotEqualTo(String value) {
            addCriterion("relocation <>", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationGreaterThan(String value) {
            addCriterion("relocation >", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationGreaterThanOrEqualTo(String value) {
            addCriterion("relocation >=", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLessThan(String value) {
            addCriterion("relocation <", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLessThanOrEqualTo(String value) {
            addCriterion("relocation <=", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLike(String value) {
            addCriterion("relocation like", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotLike(String value) {
            addCriterion("relocation not like", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationIn(List<String> values) {
            addCriterion("relocation in", values, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotIn(List<String> values) {
            addCriterion("relocation not in", values, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationBetween(String value1, String value2) {
            addCriterion("relocation between", value1, value2, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotBetween(String value1, String value2) {
            addCriterion("relocation not between", value1, value2, "relocation");
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
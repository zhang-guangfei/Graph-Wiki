package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsResultExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andModelIsNull() {
            addCriterion("Model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("Model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("Model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("Model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("Model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("Model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("Model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("Model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("Model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("Model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("Model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("Model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("Model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("Model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNull() {
            addCriterion("TypeId is null");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNotNull() {
            addCriterion("TypeId is not null");
            return (Criteria) this;
        }

        public Criteria andTypeidEqualTo(String value) {
            addCriterion("TypeId =", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotEqualTo(String value) {
            addCriterion("TypeId <>", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThan(String value) {
            addCriterion("TypeId >", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThanOrEqualTo(String value) {
            addCriterion("TypeId >=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThan(String value) {
            addCriterion("TypeId <", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThanOrEqualTo(String value) {
            addCriterion("TypeId <=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLike(String value) {
            addCriterion("TypeId like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotLike(String value) {
            addCriterion("TypeId not like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidIn(List<String> values) {
            addCriterion("TypeId in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotIn(List<String> values) {
            addCriterion("TypeId not in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidBetween(String value1, String value2) {
            addCriterion("TypeId between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotBetween(String value1, String value2) {
            addCriterion("TypeId not between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andSplitmethodIsNull() {
            addCriterion("splitMethod is null");
            return (Criteria) this;
        }

        public Criteria andSplitmethodIsNotNull() {
            addCriterion("splitMethod is not null");
            return (Criteria) this;
        }

        public Criteria andSplitmethodEqualTo(String value) {
            addCriterion("splitMethod =", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodNotEqualTo(String value) {
            addCriterion("splitMethod <>", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodGreaterThan(String value) {
            addCriterion("splitMethod >", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodGreaterThanOrEqualTo(String value) {
            addCriterion("splitMethod >=", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodLessThan(String value) {
            addCriterion("splitMethod <", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodLessThanOrEqualTo(String value) {
            addCriterion("splitMethod <=", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodLike(String value) {
            addCriterion("splitMethod like", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodNotLike(String value) {
            addCriterion("splitMethod not like", value, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodIn(List<String> values) {
            addCriterion("splitMethod in", values, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodNotIn(List<String> values) {
            addCriterion("splitMethod not in", values, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodBetween(String value1, String value2) {
            addCriterion("splitMethod between", value1, value2, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmethodNotBetween(String value1, String value2) {
            addCriterion("splitMethod not between", value1, value2, "splitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoIsNull() {
            addCriterion("splitModelNo is null");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoIsNotNull() {
            addCriterion("splitModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoEqualTo(String value) {
            addCriterion("splitModelNo =", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoNotEqualTo(String value) {
            addCriterion("splitModelNo <>", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoGreaterThan(String value) {
            addCriterion("splitModelNo >", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoGreaterThanOrEqualTo(String value) {
            addCriterion("splitModelNo >=", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoLessThan(String value) {
            addCriterion("splitModelNo <", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoLessThanOrEqualTo(String value) {
            addCriterion("splitModelNo <=", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoLike(String value) {
            addCriterion("splitModelNo like", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoNotLike(String value) {
            addCriterion("splitModelNo not like", value, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoIn(List<String> values) {
            addCriterion("splitModelNo in", values, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoNotIn(List<String> values) {
            addCriterion("splitModelNo not in", values, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoBetween(String value1, String value2) {
            addCriterion("splitModelNo between", value1, value2, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andSplitmodelnoNotBetween(String value1, String value2) {
            addCriterion("splitModelNo not between", value1, value2, "splitmodelno");
            return (Criteria) this;
        }

        public Criteria andDecideresultIsNull() {
            addCriterion("decideResult is null");
            return (Criteria) this;
        }

        public Criteria andDecideresultIsNotNull() {
            addCriterion("decideResult is not null");
            return (Criteria) this;
        }

        public Criteria andDecideresultEqualTo(String value) {
            addCriterion("decideResult =", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultNotEqualTo(String value) {
            addCriterion("decideResult <>", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultGreaterThan(String value) {
            addCriterion("decideResult >", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultGreaterThanOrEqualTo(String value) {
            addCriterion("decideResult >=", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultLessThan(String value) {
            addCriterion("decideResult <", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultLessThanOrEqualTo(String value) {
            addCriterion("decideResult <=", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultLike(String value) {
            addCriterion("decideResult like", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultNotLike(String value) {
            addCriterion("decideResult not like", value, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultIn(List<String> values) {
            addCriterion("decideResult in", values, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultNotIn(List<String> values) {
            addCriterion("decideResult not in", values, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultBetween(String value1, String value2) {
            addCriterion("decideResult between", value1, value2, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDecideresultNotBetween(String value1, String value2) {
            addCriterion("decideResult not between", value1, value2, "decideresult");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodIsNull() {
            addCriterion("defaultSplitMethod is null");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodIsNotNull() {
            addCriterion("defaultSplitMethod is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodEqualTo(String value) {
            addCriterion("defaultSplitMethod =", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodNotEqualTo(String value) {
            addCriterion("defaultSplitMethod <>", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodGreaterThan(String value) {
            addCriterion("defaultSplitMethod >", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodGreaterThanOrEqualTo(String value) {
            addCriterion("defaultSplitMethod >=", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodLessThan(String value) {
            addCriterion("defaultSplitMethod <", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodLessThanOrEqualTo(String value) {
            addCriterion("defaultSplitMethod <=", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodLike(String value) {
            addCriterion("defaultSplitMethod like", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodNotLike(String value) {
            addCriterion("defaultSplitMethod not like", value, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodIn(List<String> values) {
            addCriterion("defaultSplitMethod in", values, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodNotIn(List<String> values) {
            addCriterion("defaultSplitMethod not in", values, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodBetween(String value1, String value2) {
            addCriterion("defaultSplitMethod between", value1, value2, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andDefaultsplitmethodNotBetween(String value1, String value2) {
            addCriterion("defaultSplitMethod not between", value1, value2, "defaultsplitmethod");
            return (Criteria) this;
        }

        public Criteria andSplitquantityIsNull() {
            addCriterion("splitQuantity is null");
            return (Criteria) this;
        }

        public Criteria andSplitquantityIsNotNull() {
            addCriterion("splitQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andSplitquantityEqualTo(Integer value) {
            addCriterion("splitQuantity =", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityNotEqualTo(Integer value) {
            addCriterion("splitQuantity <>", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityGreaterThan(Integer value) {
            addCriterion("splitQuantity >", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitQuantity >=", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityLessThan(Integer value) {
            addCriterion("splitQuantity <", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityLessThanOrEqualTo(Integer value) {
            addCriterion("splitQuantity <=", value, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityIn(List<Integer> values) {
            addCriterion("splitQuantity in", values, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityNotIn(List<Integer> values) {
            addCriterion("splitQuantity not in", values, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityBetween(Integer value1, Integer value2) {
            addCriterion("splitQuantity between", value1, value2, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andSplitquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("splitQuantity not between", value1, value2, "splitquantity");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("describe is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("describe is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("describe =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("describe <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("describe >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("describe >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("describe <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("describe <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("describe like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("describe not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List<String> values) {
            addCriterion("describe in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List<String> values) {
            addCriterion("describe not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("describe between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("describe not between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andClassifyIsNull() {
            addCriterion("Classify is null");
            return (Criteria) this;
        }

        public Criteria andClassifyIsNotNull() {
            addCriterion("Classify is not null");
            return (Criteria) this;
        }

        public Criteria andClassifyEqualTo(Boolean value) {
            addCriterion("Classify =", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotEqualTo(Boolean value) {
            addCriterion("Classify <>", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyGreaterThan(Boolean value) {
            addCriterion("Classify >", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("Classify >=", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyLessThan(Boolean value) {
            addCriterion("Classify <", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyLessThanOrEqualTo(Boolean value) {
            addCriterion("Classify <=", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyIn(List<Boolean> values) {
            addCriterion("Classify in", values, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotIn(List<Boolean> values) {
            addCriterion("Classify not in", values, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyBetween(Boolean value1, Boolean value2) {
            addCriterion("Classify between", value1, value2, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("Classify not between", value1, value2, "classify");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNull() {
            addCriterion("CreatedUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNotNull() {
            addCriterion("CreatedUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateduserEqualTo(String value) {
            addCriterion("CreatedUser =", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotEqualTo(String value) {
            addCriterion("CreatedUser <>", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThan(String value) {
            addCriterion("CreatedUser >", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThanOrEqualTo(String value) {
            addCriterion("CreatedUser >=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThan(String value) {
            addCriterion("CreatedUser <", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThanOrEqualTo(String value) {
            addCriterion("CreatedUser <=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLike(String value) {
            addCriterion("CreatedUser like", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotLike(String value) {
            addCriterion("CreatedUser not like", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserIn(List<String> values) {
            addCriterion("CreatedUser in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotIn(List<String> values) {
            addCriterion("CreatedUser not in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserBetween(String value1, String value2) {
            addCriterion("CreatedUser between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotBetween(String value1, String value2) {
            addCriterion("CreatedUser not between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNull() {
            addCriterion("CreatedTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNotNull() {
            addCriterion("CreatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeEqualTo(Date value) {
            addCriterion("CreatedTime =", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotEqualTo(Date value) {
            addCriterion("CreatedTime <>", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThan(Date value) {
            addCriterion("CreatedTime >", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreatedTime >=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThan(Date value) {
            addCriterion("CreatedTime <", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("CreatedTime <=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIn(List<Date> values) {
            addCriterion("CreatedTime in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotIn(List<Date> values) {
            addCriterion("CreatedTime not in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeBetween(Date value1, Date value2) {
            addCriterion("CreatedTime between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("CreatedTime not between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIsNull() {
            addCriterion("UpdatedUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIsNotNull() {
            addCriterion("UpdatedUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateduserEqualTo(String value) {
            addCriterion("UpdatedUser =", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotEqualTo(String value) {
            addCriterion("UpdatedUser <>", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserGreaterThan(String value) {
            addCriterion("UpdatedUser >", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserGreaterThanOrEqualTo(String value) {
            addCriterion("UpdatedUser >=", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLessThan(String value) {
            addCriterion("UpdatedUser <", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLessThanOrEqualTo(String value) {
            addCriterion("UpdatedUser <=", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLike(String value) {
            addCriterion("UpdatedUser like", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotLike(String value) {
            addCriterion("UpdatedUser not like", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIn(List<String> values) {
            addCriterion("UpdatedUser in", values, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotIn(List<String> values) {
            addCriterion("UpdatedUser not in", values, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserBetween(String value1, String value2) {
            addCriterion("UpdatedUser between", value1, value2, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotBetween(String value1, String value2) {
            addCriterion("UpdatedUser not between", value1, value2, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNull() {
            addCriterion("UpdatedTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNotNull() {
            addCriterion("UpdatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeEqualTo(Date value) {
            addCriterion("UpdatedTime =", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotEqualTo(Date value) {
            addCriterion("UpdatedTime <>", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThan(Date value) {
            addCriterion("UpdatedTime >", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdatedTime >=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThan(Date value) {
            addCriterion("UpdatedTime <", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdatedTime <=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIn(List<Date> values) {
            addCriterion("UpdatedTime in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotIn(List<Date> values) {
            addCriterion("UpdatedTime not in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeBetween(Date value1, Date value2) {
            addCriterion("UpdatedTime between", value1, value2, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdatedTime not between", value1, value2, "updatedtime");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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
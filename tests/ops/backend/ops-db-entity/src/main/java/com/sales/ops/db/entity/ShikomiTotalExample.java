package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShikomiTotalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShikomiTotalExample() {
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

        public Criteria andShikominoIsNull() {
            addCriterion("ShikomiNo is null");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNotNull() {
            addCriterion("ShikomiNo is not null");
            return (Criteria) this;
        }

        public Criteria andShikominoEqualTo(String value) {
            addCriterion("ShikomiNo =", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotEqualTo(String value) {
            addCriterion("ShikomiNo <>", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThan(String value) {
            addCriterion("ShikomiNo >", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThanOrEqualTo(String value) {
            addCriterion("ShikomiNo >=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThan(String value) {
            addCriterion("ShikomiNo <", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThanOrEqualTo(String value) {
            addCriterion("ShikomiNo <=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLike(String value) {
            addCriterion("ShikomiNo like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotLike(String value) {
            addCriterion("ShikomiNo not like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoIn(List<String> values) {
            addCriterion("ShikomiNo in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotIn(List<String> values) {
            addCriterion("ShikomiNo not in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoBetween(String value1, String value2) {
            addCriterion("ShikomiNo between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotBetween(String value1, String value2) {
            addCriterion("ShikomiNo not between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNull() {
            addCriterion("SupplierCode is null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNotNull() {
            addCriterion("SupplierCode is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeEqualTo(String value) {
            addCriterion("SupplierCode =", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotEqualTo(String value) {
            addCriterion("SupplierCode <>", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThan(String value) {
            addCriterion("SupplierCode >", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThanOrEqualTo(String value) {
            addCriterion("SupplierCode >=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThan(String value) {
            addCriterion("SupplierCode <", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThanOrEqualTo(String value) {
            addCriterion("SupplierCode <=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLike(String value) {
            addCriterion("SupplierCode like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotLike(String value) {
            addCriterion("SupplierCode not like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIn(List<String> values) {
            addCriterion("SupplierCode in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotIn(List<String> values) {
            addCriterion("SupplierCode not in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeBetween(String value1, String value2) {
            addCriterion("SupplierCode between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotBetween(String value1, String value2) {
            addCriterion("SupplierCode not between", value1, value2, "suppliercode");
            return (Criteria) this;
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

        public Criteria andModelnoIsNull() {
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andLastqtyIsNull() {
            addCriterion("LastQty is null");
            return (Criteria) this;
        }

        public Criteria andLastqtyIsNotNull() {
            addCriterion("LastQty is not null");
            return (Criteria) this;
        }

        public Criteria andLastqtyEqualTo(Integer value) {
            addCriterion("LastQty =", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyNotEqualTo(Integer value) {
            addCriterion("LastQty <>", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyGreaterThan(Integer value) {
            addCriterion("LastQty >", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("LastQty >=", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyLessThan(Integer value) {
            addCriterion("LastQty <", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyLessThanOrEqualTo(Integer value) {
            addCriterion("LastQty <=", value, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyIn(List<Integer> values) {
            addCriterion("LastQty in", values, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyNotIn(List<Integer> values) {
            addCriterion("LastQty not in", values, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyBetween(Integer value1, Integer value2) {
            addCriterion("LastQty between", value1, value2, "lastqty");
            return (Criteria) this;
        }

        public Criteria andLastqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("LastQty not between", value1, value2, "lastqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNull() {
            addCriterion("ApplyQty is null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNotNull() {
            addCriterion("ApplyQty is not null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyEqualTo(Integer value) {
            addCriterion("ApplyQty =", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotEqualTo(Integer value) {
            addCriterion("ApplyQty <>", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThan(Integer value) {
            addCriterion("ApplyQty >", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty >=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThan(Integer value) {
            addCriterion("ApplyQty <", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty <=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIn(List<Integer> values) {
            addCriterion("ApplyQty in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotIn(List<Integer> values) {
            addCriterion("ApplyQty not in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty between", value1, value2, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty not between", value1, value2, "applyqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIsNull() {
            addCriterion("RemainQty is null");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIsNotNull() {
            addCriterion("RemainQty is not null");
            return (Criteria) this;
        }

        public Criteria andRemainqtyEqualTo(Integer value) {
            addCriterion("RemainQty =", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotEqualTo(Integer value) {
            addCriterion("RemainQty <>", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyGreaterThan(Integer value) {
            addCriterion("RemainQty >", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("RemainQty >=", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyLessThan(Integer value) {
            addCriterion("RemainQty <", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyLessThanOrEqualTo(Integer value) {
            addCriterion("RemainQty <=", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIn(List<Integer> values) {
            addCriterion("RemainQty in", values, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotIn(List<Integer> values) {
            addCriterion("RemainQty not in", values, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyBetween(Integer value1, Integer value2) {
            addCriterion("RemainQty between", value1, value2, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("RemainQty not between", value1, value2, "remainqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIsNull() {
            addCriterion("MaxQty is null");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIsNotNull() {
            addCriterion("MaxQty is not null");
            return (Criteria) this;
        }

        public Criteria andMaxqtyEqualTo(Integer value) {
            addCriterion("MaxQty =", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotEqualTo(Integer value) {
            addCriterion("MaxQty <>", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyGreaterThan(Integer value) {
            addCriterion("MaxQty >", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("MaxQty >=", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyLessThan(Integer value) {
            addCriterion("MaxQty <", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyLessThanOrEqualTo(Integer value) {
            addCriterion("MaxQty <=", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIn(List<Integer> values) {
            addCriterion("MaxQty in", values, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotIn(List<Integer> values) {
            addCriterion("MaxQty not in", values, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyBetween(Integer value1, Integer value2) {
            addCriterion("MaxQty between", value1, value2, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("MaxQty not between", value1, value2, "maxqty");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIsNull() {
            addCriterion("QtyOrdPre is null");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIsNotNull() {
            addCriterion("QtyOrdPre is not null");
            return (Criteria) this;
        }

        public Criteria andQtyordpreEqualTo(Integer value) {
            addCriterion("QtyOrdPre =", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotEqualTo(Integer value) {
            addCriterion("QtyOrdPre <>", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreGreaterThan(Integer value) {
            addCriterion("QtyOrdPre >", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdPre >=", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreLessThan(Integer value) {
            addCriterion("QtyOrdPre <", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdPre <=", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIn(List<Integer> values) {
            addCriterion("QtyOrdPre in", values, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotIn(List<Integer> values) {
            addCriterion("QtyOrdPre not in", values, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdPre between", value1, value2, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdPre not between", value1, value2, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andSerialmodelIsNull() {
            addCriterion("serialModel is null");
            return (Criteria) this;
        }

        public Criteria andSerialmodelIsNotNull() {
            addCriterion("serialModel is not null");
            return (Criteria) this;
        }

        public Criteria andSerialmodelEqualTo(String value) {
            addCriterion("serialModel =", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotEqualTo(String value) {
            addCriterion("serialModel <>", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelGreaterThan(String value) {
            addCriterion("serialModel >", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelGreaterThanOrEqualTo(String value) {
            addCriterion("serialModel >=", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLessThan(String value) {
            addCriterion("serialModel <", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLessThanOrEqualTo(String value) {
            addCriterion("serialModel <=", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelLike(String value) {
            addCriterion("serialModel like", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotLike(String value) {
            addCriterion("serialModel not like", value, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelIn(List<String> values) {
            addCriterion("serialModel in", values, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotIn(List<String> values) {
            addCriterion("serialModel not in", values, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelBetween(String value1, String value2) {
            addCriterion("serialModel between", value1, value2, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andSerialmodelNotBetween(String value1, String value2) {
            addCriterion("serialModel not between", value1, value2, "serialmodel");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("CustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("CustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("CustomerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("CustomerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("CustomerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("CustomerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("CustomerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("CustomerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("CustomerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("CustomerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("CustomerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("CustomerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("CustomerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andAnswertextIsNull() {
            addCriterion("AnswerText is null");
            return (Criteria) this;
        }

        public Criteria andAnswertextIsNotNull() {
            addCriterion("AnswerText is not null");
            return (Criteria) this;
        }

        public Criteria andAnswertextEqualTo(String value) {
            addCriterion("AnswerText =", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotEqualTo(String value) {
            addCriterion("AnswerText <>", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextGreaterThan(String value) {
            addCriterion("AnswerText >", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextGreaterThanOrEqualTo(String value) {
            addCriterion("AnswerText >=", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLessThan(String value) {
            addCriterion("AnswerText <", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLessThanOrEqualTo(String value) {
            addCriterion("AnswerText <=", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLike(String value) {
            addCriterion("AnswerText like", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotLike(String value) {
            addCriterion("AnswerText not like", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextIn(List<String> values) {
            addCriterion("AnswerText in", values, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotIn(List<String> values) {
            addCriterion("AnswerText not in", values, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextBetween(String value1, String value2) {
            addCriterion("AnswerText between", value1, value2, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotBetween(String value1, String value2) {
            addCriterion("AnswerText not between", value1, value2, "answertext");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRegistdateIsNull() {
            addCriterion("RegistDate is null");
            return (Criteria) this;
        }

        public Criteria andRegistdateIsNotNull() {
            addCriterion("RegistDate is not null");
            return (Criteria) this;
        }

        public Criteria andRegistdateEqualTo(Date value) {
            addCriterion("RegistDate =", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateNotEqualTo(Date value) {
            addCriterion("RegistDate <>", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateGreaterThan(Date value) {
            addCriterion("RegistDate >", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateGreaterThanOrEqualTo(Date value) {
            addCriterion("RegistDate >=", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateLessThan(Date value) {
            addCriterion("RegistDate <", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateLessThanOrEqualTo(Date value) {
            addCriterion("RegistDate <=", value, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateIn(List<Date> values) {
            addCriterion("RegistDate in", values, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateNotIn(List<Date> values) {
            addCriterion("RegistDate not in", values, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateBetween(Date value1, Date value2) {
            addCriterion("RegistDate between", value1, value2, "registdate");
            return (Criteria) this;
        }

        public Criteria andRegistdateNotBetween(Date value1, Date value2) {
            addCriterion("RegistDate not between", value1, value2, "registdate");
            return (Criteria) this;
        }

        public Criteria andRevisedateIsNull() {
            addCriterion("ReviseDate is null");
            return (Criteria) this;
        }

        public Criteria andRevisedateIsNotNull() {
            addCriterion("ReviseDate is not null");
            return (Criteria) this;
        }

        public Criteria andRevisedateEqualTo(Date value) {
            addCriterion("ReviseDate =", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateNotEqualTo(Date value) {
            addCriterion("ReviseDate <>", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateGreaterThan(Date value) {
            addCriterion("ReviseDate >", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateGreaterThanOrEqualTo(Date value) {
            addCriterion("ReviseDate >=", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateLessThan(Date value) {
            addCriterion("ReviseDate <", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateLessThanOrEqualTo(Date value) {
            addCriterion("ReviseDate <=", value, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateIn(List<Date> values) {
            addCriterion("ReviseDate in", values, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateNotIn(List<Date> values) {
            addCriterion("ReviseDate not in", values, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateBetween(Date value1, Date value2) {
            addCriterion("ReviseDate between", value1, value2, "revisedate");
            return (Criteria) this;
        }

        public Criteria andRevisedateNotBetween(Date value1, Date value2) {
            addCriterion("ReviseDate not between", value1, value2, "revisedate");
            return (Criteria) this;
        }

        public Criteria andClasstypeIsNull() {
            addCriterion("ClassType is null");
            return (Criteria) this;
        }

        public Criteria andClasstypeIsNotNull() {
            addCriterion("ClassType is not null");
            return (Criteria) this;
        }

        public Criteria andClasstypeEqualTo(String value) {
            addCriterion("ClassType =", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeNotEqualTo(String value) {
            addCriterion("ClassType <>", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeGreaterThan(String value) {
            addCriterion("ClassType >", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeGreaterThanOrEqualTo(String value) {
            addCriterion("ClassType >=", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeLessThan(String value) {
            addCriterion("ClassType <", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeLessThanOrEqualTo(String value) {
            addCriterion("ClassType <=", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeLike(String value) {
            addCriterion("ClassType like", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeNotLike(String value) {
            addCriterion("ClassType not like", value, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeIn(List<String> values) {
            addCriterion("ClassType in", values, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeNotIn(List<String> values) {
            addCriterion("ClassType not in", values, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeBetween(String value1, String value2) {
            addCriterion("ClassType between", value1, value2, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasstypeNotBetween(String value1, String value2) {
            addCriterion("ClassType not between", value1, value2, "classtype");
            return (Criteria) this;
        }

        public Criteria andClasscodeIsNull() {
            addCriterion("ClassCode is null");
            return (Criteria) this;
        }

        public Criteria andClasscodeIsNotNull() {
            addCriterion("ClassCode is not null");
            return (Criteria) this;
        }

        public Criteria andClasscodeEqualTo(String value) {
            addCriterion("ClassCode =", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotEqualTo(String value) {
            addCriterion("ClassCode <>", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThan(String value) {
            addCriterion("ClassCode >", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThanOrEqualTo(String value) {
            addCriterion("ClassCode >=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThan(String value) {
            addCriterion("ClassCode <", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThanOrEqualTo(String value) {
            addCriterion("ClassCode <=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLike(String value) {
            addCriterion("ClassCode like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotLike(String value) {
            addCriterion("ClassCode not like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeIn(List<String> values) {
            addCriterion("ClassCode in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotIn(List<String> values) {
            addCriterion("ClassCode not in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeBetween(String value1, String value2) {
            addCriterion("ClassCode between", value1, value2, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotBetween(String value1, String value2) {
            addCriterion("ClassCode not between", value1, value2, "classcode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeIsNull() {
            addCriterion("SubsidiaryCode is null");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeIsNotNull() {
            addCriterion("SubsidiaryCode is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeEqualTo(String value) {
            addCriterion("SubsidiaryCode =", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeNotEqualTo(String value) {
            addCriterion("SubsidiaryCode <>", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeGreaterThan(String value) {
            addCriterion("SubsidiaryCode >", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeGreaterThanOrEqualTo(String value) {
            addCriterion("SubsidiaryCode >=", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeLessThan(String value) {
            addCriterion("SubsidiaryCode <", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeLessThanOrEqualTo(String value) {
            addCriterion("SubsidiaryCode <=", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeLike(String value) {
            addCriterion("SubsidiaryCode like", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeNotLike(String value) {
            addCriterion("SubsidiaryCode not like", value, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeIn(List<String> values) {
            addCriterion("SubsidiaryCode in", values, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeNotIn(List<String> values) {
            addCriterion("SubsidiaryCode not in", values, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeBetween(String value1, String value2) {
            addCriterion("SubsidiaryCode between", value1, value2, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andSubsidiarycodeNotBetween(String value1, String value2) {
            addCriterion("SubsidiaryCode not between", value1, value2, "subsidiarycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNull() {
            addCriterion("CompanyCode is null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNotNull() {
            addCriterion("CompanyCode is not null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeEqualTo(String value) {
            addCriterion("CompanyCode =", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotEqualTo(String value) {
            addCriterion("CompanyCode <>", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThan(String value) {
            addCriterion("CompanyCode >", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyCode >=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThan(String value) {
            addCriterion("CompanyCode <", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThanOrEqualTo(String value) {
            addCriterion("CompanyCode <=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLike(String value) {
            addCriterion("CompanyCode like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotLike(String value) {
            addCriterion("CompanyCode not like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIn(List<String> values) {
            addCriterion("CompanyCode in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotIn(List<String> values) {
            addCriterion("CompanyCode not in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeBetween(String value1, String value2) {
            addCriterion("CompanyCode between", value1, value2, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotBetween(String value1, String value2) {
            addCriterion("CompanyCode not between", value1, value2, "companycode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeIsNull() {
            addCriterion("BranchCode is null");
            return (Criteria) this;
        }

        public Criteria andBranchcodeIsNotNull() {
            addCriterion("BranchCode is not null");
            return (Criteria) this;
        }

        public Criteria andBranchcodeEqualTo(String value) {
            addCriterion("BranchCode =", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeNotEqualTo(String value) {
            addCriterion("BranchCode <>", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeGreaterThan(String value) {
            addCriterion("BranchCode >", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BranchCode >=", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeLessThan(String value) {
            addCriterion("BranchCode <", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeLessThanOrEqualTo(String value) {
            addCriterion("BranchCode <=", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeLike(String value) {
            addCriterion("BranchCode like", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeNotLike(String value) {
            addCriterion("BranchCode not like", value, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeIn(List<String> values) {
            addCriterion("BranchCode in", values, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeNotIn(List<String> values) {
            addCriterion("BranchCode not in", values, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeBetween(String value1, String value2) {
            addCriterion("BranchCode between", value1, value2, "branchcode");
            return (Criteria) this;
        }

        public Criteria andBranchcodeNotBetween(String value1, String value2) {
            addCriterion("BranchCode not between", value1, value2, "branchcode");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNull() {
            addCriterion("DlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("DlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterion("DlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterion("DlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterion("DlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterion("DlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterion("DlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterion("DlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterion("DlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate not between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("DeptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("DeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("DeptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("DeptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("DeptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("DeptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("DeptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("DeptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("DeptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("DeptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("DeptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("DeptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("DeptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("DeptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andApplytimeIsNull() {
            addCriterion("ApplyTime is null");
            return (Criteria) this;
        }

        public Criteria andApplytimeIsNotNull() {
            addCriterion("ApplyTime is not null");
            return (Criteria) this;
        }

        public Criteria andApplytimeEqualTo(Date value) {
            addCriterion("ApplyTime =", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotEqualTo(Date value) {
            addCriterion("ApplyTime <>", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeGreaterThan(Date value) {
            addCriterion("ApplyTime >", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ApplyTime >=", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeLessThan(Date value) {
            addCriterion("ApplyTime <", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeLessThanOrEqualTo(Date value) {
            addCriterion("ApplyTime <=", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeIn(List<Date> values) {
            addCriterion("ApplyTime in", values, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotIn(List<Date> values) {
            addCriterion("ApplyTime not in", values, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeBetween(Date value1, Date value2) {
            addCriterion("ApplyTime between", value1, value2, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotBetween(Date value1, Date value2) {
            addCriterion("ApplyTime not between", value1, value2, "applytime");
            return (Criteria) this;
        }

        public Criteria andOrderqtyIsNull() {
            addCriterion("OrderQty is null");
            return (Criteria) this;
        }

        public Criteria andOrderqtyIsNotNull() {
            addCriterion("OrderQty is not null");
            return (Criteria) this;
        }

        public Criteria andOrderqtyEqualTo(Integer value) {
            addCriterion("OrderQty =", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyNotEqualTo(Integer value) {
            addCriterion("OrderQty <>", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyGreaterThan(Integer value) {
            addCriterion("OrderQty >", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrderQty >=", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyLessThan(Integer value) {
            addCriterion("OrderQty <", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyLessThanOrEqualTo(Integer value) {
            addCriterion("OrderQty <=", value, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyIn(List<Integer> values) {
            addCriterion("OrderQty in", values, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyNotIn(List<Integer> values) {
            addCriterion("OrderQty not in", values, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyBetween(Integer value1, Integer value2) {
            addCriterion("OrderQty between", value1, value2, "orderqty");
            return (Criteria) this;
        }

        public Criteria andOrderqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("OrderQty not between", value1, value2, "orderqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIsNull() {
            addCriterion("CancelQty is null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIsNotNull() {
            addCriterion("CancelQty is not null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyEqualTo(Integer value) {
            addCriterion("CancelQty =", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotEqualTo(Integer value) {
            addCriterion("CancelQty <>", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyGreaterThan(Integer value) {
            addCriterion("CancelQty >", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("CancelQty >=", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyLessThan(Integer value) {
            addCriterion("CancelQty <", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyLessThanOrEqualTo(Integer value) {
            addCriterion("CancelQty <=", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIn(List<Integer> values) {
            addCriterion("CancelQty in", values, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotIn(List<Integer> values) {
            addCriterion("CancelQty not in", values, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyBetween(Integer value1, Integer value2) {
            addCriterion("CancelQty between", value1, value2, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("CancelQty not between", value1, value2, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNull() {
            addCriterion("ModelType is null");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNotNull() {
            addCriterion("ModelType is not null");
            return (Criteria) this;
        }

        public Criteria andModeltypeEqualTo(String value) {
            addCriterion("ModelType =", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotEqualTo(String value) {
            addCriterion("ModelType <>", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThan(String value) {
            addCriterion("ModelType >", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThanOrEqualTo(String value) {
            addCriterion("ModelType >=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThan(String value) {
            addCriterion("ModelType <", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThanOrEqualTo(String value) {
            addCriterion("ModelType <=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLike(String value) {
            addCriterion("ModelType like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotLike(String value) {
            addCriterion("ModelType not like", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeIn(List<String> values) {
            addCriterion("ModelType in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotIn(List<String> values) {
            addCriterion("ModelType not in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeBetween(String value1, String value2) {
            addCriterion("ModelType between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotBetween(String value1, String value2) {
            addCriterion("ModelType not between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNull() {
            addCriterion("CreateUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("CreateUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(String value) {
            addCriterion("CreateUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(String value) {
            addCriterion("CreateUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(String value) {
            addCriterion("CreateUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(String value) {
            addCriterion("CreateUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(String value) {
            addCriterion("CreateUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(String value) {
            addCriterion("CreateUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLike(String value) {
            addCriterion("CreateUser like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotLike(String value) {
            addCriterion("CreateUser not like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<String> values) {
            addCriterion("CreateUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<String> values) {
            addCriterion("CreateUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(String value1, String value2) {
            addCriterion("CreateUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(String value1, String value2) {
            addCriterion("CreateUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNull() {
            addCriterion("UpdateUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNotNull() {
            addCriterion("UpdateUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserEqualTo(String value) {
            addCriterion("UpdateUser =", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotEqualTo(String value) {
            addCriterion("UpdateUser <>", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThan(String value) {
            addCriterion("UpdateUser >", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThanOrEqualTo(String value) {
            addCriterion("UpdateUser >=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThan(String value) {
            addCriterion("UpdateUser <", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThanOrEqualTo(String value) {
            addCriterion("UpdateUser <=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLike(String value) {
            addCriterion("UpdateUser like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotLike(String value) {
            addCriterion("UpdateUser not like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIn(List<String> values) {
            addCriterion("UpdateUser in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotIn(List<String> values) {
            addCriterion("UpdateUser not in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserBetween(String value1, String value2) {
            addCriterion("UpdateUser between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotBetween(String value1, String value2) {
            addCriterion("UpdateUser not between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameIsNull() {
            addCriterion("ApplyPsnName is null");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameIsNotNull() {
            addCriterion("ApplyPsnName is not null");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameEqualTo(String value) {
            addCriterion("ApplyPsnName =", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameNotEqualTo(String value) {
            addCriterion("ApplyPsnName <>", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameGreaterThan(String value) {
            addCriterion("ApplyPsnName >", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameGreaterThanOrEqualTo(String value) {
            addCriterion("ApplyPsnName >=", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameLessThan(String value) {
            addCriterion("ApplyPsnName <", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameLessThanOrEqualTo(String value) {
            addCriterion("ApplyPsnName <=", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameLike(String value) {
            addCriterion("ApplyPsnName like", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameNotLike(String value) {
            addCriterion("ApplyPsnName not like", value, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameIn(List<String> values) {
            addCriterion("ApplyPsnName in", values, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameNotIn(List<String> values) {
            addCriterion("ApplyPsnName not in", values, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameBetween(String value1, String value2) {
            addCriterion("ApplyPsnName between", value1, value2, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andApplypsnnameNotBetween(String value1, String value2) {
            addCriterion("ApplyPsnName not between", value1, value2, "applypsnname");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNull() {
            addCriterion("QtyWarning is null");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNotNull() {
            addCriterion("QtyWarning is not null");
            return (Criteria) this;
        }

        public Criteria andQtywarningEqualTo(Integer value) {
            addCriterion("QtyWarning =", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotEqualTo(Integer value) {
            addCriterion("QtyWarning <>", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThan(Integer value) {
            addCriterion("QtyWarning >", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning >=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThan(Integer value) {
            addCriterion("QtyWarning <", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning <=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningIn(List<Integer> values) {
            addCriterion("QtyWarning in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotIn(List<Integer> values) {
            addCriterion("QtyWarning not in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning not between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIsNull() {
            addCriterion("ApplicantNo is null");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIsNotNull() {
            addCriterion("ApplicantNo is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantnoEqualTo(String value) {
            addCriterion("ApplicantNo =", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotEqualTo(String value) {
            addCriterion("ApplicantNo <>", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoGreaterThan(String value) {
            addCriterion("ApplicantNo >", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantNo >=", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLessThan(String value) {
            addCriterion("ApplicantNo <", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLessThanOrEqualTo(String value) {
            addCriterion("ApplicantNo <=", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLike(String value) {
            addCriterion("ApplicantNo like", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotLike(String value) {
            addCriterion("ApplicantNo not like", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIn(List<String> values) {
            addCriterion("ApplicantNo in", values, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotIn(List<String> values) {
            addCriterion("ApplicantNo not in", values, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoBetween(String value1, String value2) {
            addCriterion("ApplicantNo between", value1, value2, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotBetween(String value1, String value2) {
            addCriterion("ApplicantNo not between", value1, value2, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIsNull() {
            addCriterion("ApplicantName is null");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIsNotNull() {
            addCriterion("ApplicantName is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantnameEqualTo(String value) {
            addCriterion("ApplicantName =", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotEqualTo(String value) {
            addCriterion("ApplicantName <>", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameGreaterThan(String value) {
            addCriterion("ApplicantName >", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantName >=", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLessThan(String value) {
            addCriterion("ApplicantName <", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLessThanOrEqualTo(String value) {
            addCriterion("ApplicantName <=", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLike(String value) {
            addCriterion("ApplicantName like", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotLike(String value) {
            addCriterion("ApplicantName not like", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIn(List<String> values) {
            addCriterion("ApplicantName in", values, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotIn(List<String> values) {
            addCriterion("ApplicantName not in", values, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameBetween(String value1, String value2) {
            addCriterion("ApplicantName between", value1, value2, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotBetween(String value1, String value2) {
            addCriterion("ApplicantName not between", value1, value2, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIsNull() {
            addCriterion("ApplicantEmail is null");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIsNotNull() {
            addCriterion("ApplicantEmail is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantemailEqualTo(String value) {
            addCriterion("ApplicantEmail =", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotEqualTo(String value) {
            addCriterion("ApplicantEmail <>", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailGreaterThan(String value) {
            addCriterion("ApplicantEmail >", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantEmail >=", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLessThan(String value) {
            addCriterion("ApplicantEmail <", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLessThanOrEqualTo(String value) {
            addCriterion("ApplicantEmail <=", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLike(String value) {
            addCriterion("ApplicantEmail like", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotLike(String value) {
            addCriterion("ApplicantEmail not like", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIn(List<String> values) {
            addCriterion("ApplicantEmail in", values, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotIn(List<String> values) {
            addCriterion("ApplicantEmail not in", values, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailBetween(String value1, String value2) {
            addCriterion("ApplicantEmail between", value1, value2, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotBetween(String value1, String value2) {
            addCriterion("ApplicantEmail not between", value1, value2, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApprovernoIsNull() {
            addCriterion("ApproverNo is null");
            return (Criteria) this;
        }

        public Criteria andApprovernoIsNotNull() {
            addCriterion("ApproverNo is not null");
            return (Criteria) this;
        }

        public Criteria andApprovernoEqualTo(String value) {
            addCriterion("ApproverNo =", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoNotEqualTo(String value) {
            addCriterion("ApproverNo <>", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoGreaterThan(String value) {
            addCriterion("ApproverNo >", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoGreaterThanOrEqualTo(String value) {
            addCriterion("ApproverNo >=", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoLessThan(String value) {
            addCriterion("ApproverNo <", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoLessThanOrEqualTo(String value) {
            addCriterion("ApproverNo <=", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoLike(String value) {
            addCriterion("ApproverNo like", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoNotLike(String value) {
            addCriterion("ApproverNo not like", value, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoIn(List<String> values) {
            addCriterion("ApproverNo in", values, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoNotIn(List<String> values) {
            addCriterion("ApproverNo not in", values, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoBetween(String value1, String value2) {
            addCriterion("ApproverNo between", value1, value2, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernoNotBetween(String value1, String value2) {
            addCriterion("ApproverNo not between", value1, value2, "approverno");
            return (Criteria) this;
        }

        public Criteria andApprovernameIsNull() {
            addCriterion("ApproverName is null");
            return (Criteria) this;
        }

        public Criteria andApprovernameIsNotNull() {
            addCriterion("ApproverName is not null");
            return (Criteria) this;
        }

        public Criteria andApprovernameEqualTo(String value) {
            addCriterion("ApproverName =", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotEqualTo(String value) {
            addCriterion("ApproverName <>", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameGreaterThan(String value) {
            addCriterion("ApproverName >", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameGreaterThanOrEqualTo(String value) {
            addCriterion("ApproverName >=", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLessThan(String value) {
            addCriterion("ApproverName <", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLessThanOrEqualTo(String value) {
            addCriterion("ApproverName <=", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLike(String value) {
            addCriterion("ApproverName like", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotLike(String value) {
            addCriterion("ApproverName not like", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameIn(List<String> values) {
            addCriterion("ApproverName in", values, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotIn(List<String> values) {
            addCriterion("ApproverName not in", values, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameBetween(String value1, String value2) {
            addCriterion("ApproverName between", value1, value2, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotBetween(String value1, String value2) {
            addCriterion("ApproverName not between", value1, value2, "approvername");
            return (Criteria) this;
        }

        public Criteria andApproveremailIsNull() {
            addCriterion("ApproverEmail is null");
            return (Criteria) this;
        }

        public Criteria andApproveremailIsNotNull() {
            addCriterion("ApproverEmail is not null");
            return (Criteria) this;
        }

        public Criteria andApproveremailEqualTo(String value) {
            addCriterion("ApproverEmail =", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotEqualTo(String value) {
            addCriterion("ApproverEmail <>", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailGreaterThan(String value) {
            addCriterion("ApproverEmail >", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailGreaterThanOrEqualTo(String value) {
            addCriterion("ApproverEmail >=", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLessThan(String value) {
            addCriterion("ApproverEmail <", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLessThanOrEqualTo(String value) {
            addCriterion("ApproverEmail <=", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLike(String value) {
            addCriterion("ApproverEmail like", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotLike(String value) {
            addCriterion("ApproverEmail not like", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailIn(List<String> values) {
            addCriterion("ApproverEmail in", values, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotIn(List<String> values) {
            addCriterion("ApproverEmail not in", values, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailBetween(String value1, String value2) {
            addCriterion("ApproverEmail between", value1, value2, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotBetween(String value1, String value2) {
            addCriterion("ApproverEmail not between", value1, value2, "approveremail");
            return (Criteria) this;
        }

        public Criteria andAssedaysIsNull() {
            addCriterion("AsseDays is null");
            return (Criteria) this;
        }

        public Criteria andAssedaysIsNotNull() {
            addCriterion("AsseDays is not null");
            return (Criteria) this;
        }

        public Criteria andAssedaysEqualTo(Integer value) {
            addCriterion("AsseDays =", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysNotEqualTo(Integer value) {
            addCriterion("AsseDays <>", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysGreaterThan(Integer value) {
            addCriterion("AsseDays >", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("AsseDays >=", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysLessThan(Integer value) {
            addCriterion("AsseDays <", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysLessThanOrEqualTo(Integer value) {
            addCriterion("AsseDays <=", value, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysIn(List<Integer> values) {
            addCriterion("AsseDays in", values, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysNotIn(List<Integer> values) {
            addCriterion("AsseDays not in", values, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysBetween(Integer value1, Integer value2) {
            addCriterion("AsseDays between", value1, value2, "assedays");
            return (Criteria) this;
        }

        public Criteria andAssedaysNotBetween(Integer value1, Integer value2) {
            addCriterion("AsseDays not between", value1, value2, "assedays");
            return (Criteria) this;
        }

        public Criteria andQtynoordIsNull() {
            addCriterion("QtyNoord is null");
            return (Criteria) this;
        }

        public Criteria andQtynoordIsNotNull() {
            addCriterion("QtyNoord is not null");
            return (Criteria) this;
        }

        public Criteria andQtynoordEqualTo(Integer value) {
            addCriterion("QtyNoord =", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotEqualTo(Integer value) {
            addCriterion("QtyNoord <>", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordGreaterThan(Integer value) {
            addCriterion("QtyNoord >", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyNoord >=", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordLessThan(Integer value) {
            addCriterion("QtyNoord <", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordLessThanOrEqualTo(Integer value) {
            addCriterion("QtyNoord <=", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordIn(List<Integer> values) {
            addCriterion("QtyNoord in", values, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotIn(List<Integer> values) {
            addCriterion("QtyNoord not in", values, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordBetween(Integer value1, Integer value2) {
            addCriterion("QtyNoord between", value1, value2, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyNoord not between", value1, value2, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andPriceLotIsNull() {
            addCriterion("Price_lot is null");
            return (Criteria) this;
        }

        public Criteria andPriceLotIsNotNull() {
            addCriterion("Price_lot is not null");
            return (Criteria) this;
        }

        public Criteria andPriceLotEqualTo(BigDecimal value) {
            addCriterion("Price_lot =", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotEqualTo(BigDecimal value) {
            addCriterion("Price_lot <>", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotGreaterThan(BigDecimal value) {
            addCriterion("Price_lot >", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_lot >=", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotLessThan(BigDecimal value) {
            addCriterion("Price_lot <", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_lot <=", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotIn(List<BigDecimal> values) {
            addCriterion("Price_lot in", values, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotIn(List<BigDecimal> values) {
            addCriterion("Price_lot not in", values, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_lot between", value1, value2, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_lot not between", value1, value2, "priceLot");
            return (Criteria) this;
        }

        public Criteria andLotqtyIsNull() {
            addCriterion("LotQty is null");
            return (Criteria) this;
        }

        public Criteria andLotqtyIsNotNull() {
            addCriterion("LotQty is not null");
            return (Criteria) this;
        }

        public Criteria andLotqtyEqualTo(Integer value) {
            addCriterion("LotQty =", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyNotEqualTo(Integer value) {
            addCriterion("LotQty <>", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyGreaterThan(Integer value) {
            addCriterion("LotQty >", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("LotQty >=", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyLessThan(Integer value) {
            addCriterion("LotQty <", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyLessThanOrEqualTo(Integer value) {
            addCriterion("LotQty <=", value, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyIn(List<Integer> values) {
            addCriterion("LotQty in", values, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyNotIn(List<Integer> values) {
            addCriterion("LotQty not in", values, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyBetween(Integer value1, Integer value2) {
            addCriterion("LotQty between", value1, value2, "lotqty");
            return (Criteria) this;
        }

        public Criteria andLotqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("LotQty not between", value1, value2, "lotqty");
            return (Criteria) this;
        }

        public Criteria andIndcodeIsNull() {
            addCriterion("IndCode is null");
            return (Criteria) this;
        }

        public Criteria andIndcodeIsNotNull() {
            addCriterion("IndCode is not null");
            return (Criteria) this;
        }

        public Criteria andIndcodeEqualTo(String value) {
            addCriterion("IndCode =", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotEqualTo(String value) {
            addCriterion("IndCode <>", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeGreaterThan(String value) {
            addCriterion("IndCode >", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeGreaterThanOrEqualTo(String value) {
            addCriterion("IndCode >=", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLessThan(String value) {
            addCriterion("IndCode <", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLessThanOrEqualTo(String value) {
            addCriterion("IndCode <=", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLike(String value) {
            addCriterion("IndCode like", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotLike(String value) {
            addCriterion("IndCode not like", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeIn(List<String> values) {
            addCriterion("IndCode in", values, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotIn(List<String> values) {
            addCriterion("IndCode not in", values, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeBetween(String value1, String value2) {
            addCriterion("IndCode between", value1, value2, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotBetween(String value1, String value2) {
            addCriterion("IndCode not between", value1, value2, "indcode");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysIsNull() {
            addCriterion("partPrepareDays is null");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysIsNotNull() {
            addCriterion("partPrepareDays is not null");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysEqualTo(Integer value) {
            addCriterion("partPrepareDays =", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysNotEqualTo(Integer value) {
            addCriterion("partPrepareDays <>", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysGreaterThan(Integer value) {
            addCriterion("partPrepareDays >", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("partPrepareDays >=", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysLessThan(Integer value) {
            addCriterion("partPrepareDays <", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysLessThanOrEqualTo(Integer value) {
            addCriterion("partPrepareDays <=", value, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysIn(List<Integer> values) {
            addCriterion("partPrepareDays in", values, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysNotIn(List<Integer> values) {
            addCriterion("partPrepareDays not in", values, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysBetween(Integer value1, Integer value2) {
            addCriterion("partPrepareDays between", value1, value2, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andPartpreparedaysNotBetween(Integer value1, Integer value2) {
            addCriterion("partPrepareDays not between", value1, value2, "partpreparedays");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNull() {
            addCriterion("applyNo is null");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNotNull() {
            addCriterion("applyNo is not null");
            return (Criteria) this;
        }

        public Criteria andApplynoEqualTo(String value) {
            addCriterion("applyNo =", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotEqualTo(String value) {
            addCriterion("applyNo <>", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThan(String value) {
            addCriterion("applyNo >", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThanOrEqualTo(String value) {
            addCriterion("applyNo >=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThan(String value) {
            addCriterion("applyNo <", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThanOrEqualTo(String value) {
            addCriterion("applyNo <=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLike(String value) {
            addCriterion("applyNo like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotLike(String value) {
            addCriterion("applyNo not like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoIn(List<String> values) {
            addCriterion("applyNo in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotIn(List<String> values) {
            addCriterion("applyNo not in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoBetween(String value1, String value2) {
            addCriterion("applyNo between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotBetween(String value1, String value2) {
            addCriterion("applyNo not between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andRohsIsNull() {
            addCriterion("Rohs is null");
            return (Criteria) this;
        }

        public Criteria andRohsIsNotNull() {
            addCriterion("Rohs is not null");
            return (Criteria) this;
        }

        public Criteria andRohsEqualTo(String value) {
            addCriterion("Rohs =", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotEqualTo(String value) {
            addCriterion("Rohs <>", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThan(String value) {
            addCriterion("Rohs >", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThanOrEqualTo(String value) {
            addCriterion("Rohs >=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThan(String value) {
            addCriterion("Rohs <", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThanOrEqualTo(String value) {
            addCriterion("Rohs <=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLike(String value) {
            addCriterion("Rohs like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotLike(String value) {
            addCriterion("Rohs not like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsIn(List<String> values) {
            addCriterion("Rohs in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotIn(List<String> values) {
            addCriterion("Rohs not in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsBetween(String value1, String value2) {
            addCriterion("Rohs between", value1, value2, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotBetween(String value1, String value2) {
            addCriterion("Rohs not between", value1, value2, "rohs");
            return (Criteria) this;
        }

        public Criteria andQtypoIsNull() {
            addCriterion("qtyPO is null");
            return (Criteria) this;
        }

        public Criteria andQtypoIsNotNull() {
            addCriterion("qtyPO is not null");
            return (Criteria) this;
        }

        public Criteria andQtypoEqualTo(Integer value) {
            addCriterion("qtyPO =", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoNotEqualTo(Integer value) {
            addCriterion("qtyPO <>", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoGreaterThan(Integer value) {
            addCriterion("qtyPO >", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyPO >=", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoLessThan(Integer value) {
            addCriterion("qtyPO <", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoLessThanOrEqualTo(Integer value) {
            addCriterion("qtyPO <=", value, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoIn(List<Integer> values) {
            addCriterion("qtyPO in", values, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoNotIn(List<Integer> values) {
            addCriterion("qtyPO not in", values, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoBetween(Integer value1, Integer value2) {
            addCriterion("qtyPO between", value1, value2, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtypoNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyPO not between", value1, value2, "qtypo");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNull() {
            addCriterion("qtyOnhand is null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNotNull() {
            addCriterion("qtyOnhand is not null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandEqualTo(Integer value) {
            addCriterion("qtyOnhand =", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotEqualTo(Integer value) {
            addCriterion("qtyOnhand <>", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThan(Integer value) {
            addCriterion("qtyOnhand >", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyOnhand >=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThan(Integer value) {
            addCriterion("qtyOnhand <", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThanOrEqualTo(Integer value) {
            addCriterion("qtyOnhand <=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIn(List<Integer> values) {
            addCriterion("qtyOnhand in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotIn(List<Integer> values) {
            addCriterion("qtyOnhand not in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandBetween(Integer value1, Integer value2) {
            addCriterion("qtyOnhand between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyOnhand not between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIsNull() {
            addCriterion("inspectStatus is null");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIsNotNull() {
            addCriterion("inspectStatus is not null");
            return (Criteria) this;
        }

        public Criteria andInspectstatusEqualTo(Short value) {
            addCriterion("inspectStatus =", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotEqualTo(Short value) {
            addCriterion("inspectStatus <>", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusGreaterThan(Short value) {
            addCriterion("inspectStatus >", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectStatus >=", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusLessThan(Short value) {
            addCriterion("inspectStatus <", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusLessThanOrEqualTo(Short value) {
            addCriterion("inspectStatus <=", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIn(List<Short> values) {
            addCriterion("inspectStatus in", values, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotIn(List<Short> values) {
            addCriterion("inspectStatus not in", values, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusBetween(Short value1, Short value2) {
            addCriterion("inspectStatus between", value1, value2, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotBetween(Short value1, Short value2) {
            addCriterion("inspectStatus not between", value1, value2, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeIsNull() {
            addCriterion("inspectSendTime is null");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeIsNotNull() {
            addCriterion("inspectSendTime is not null");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeEqualTo(Date value) {
            addCriterion("inspectSendTime =", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeNotEqualTo(Date value) {
            addCriterion("inspectSendTime <>", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeGreaterThan(Date value) {
            addCriterion("inspectSendTime >", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inspectSendTime >=", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeLessThan(Date value) {
            addCriterion("inspectSendTime <", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeLessThanOrEqualTo(Date value) {
            addCriterion("inspectSendTime <=", value, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeIn(List<Date> values) {
            addCriterion("inspectSendTime in", values, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeNotIn(List<Date> values) {
            addCriterion("inspectSendTime not in", values, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeBetween(Date value1, Date value2) {
            addCriterion("inspectSendTime between", value1, value2, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectsendtimeNotBetween(Date value1, Date value2) {
            addCriterion("inspectSendTime not between", value1, value2, "inspectsendtime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeIsNull() {
            addCriterion("InspectAnswerTime is null");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeIsNotNull() {
            addCriterion("InspectAnswerTime is not null");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeEqualTo(Date value) {
            addCriterion("InspectAnswerTime =", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeNotEqualTo(Date value) {
            addCriterion("InspectAnswerTime <>", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeGreaterThan(Date value) {
            addCriterion("InspectAnswerTime >", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("InspectAnswerTime >=", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeLessThan(Date value) {
            addCriterion("InspectAnswerTime <", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeLessThanOrEqualTo(Date value) {
            addCriterion("InspectAnswerTime <=", value, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeIn(List<Date> values) {
            addCriterion("InspectAnswerTime in", values, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeNotIn(List<Date> values) {
            addCriterion("InspectAnswerTime not in", values, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeBetween(Date value1, Date value2) {
            addCriterion("InspectAnswerTime between", value1, value2, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswertimeNotBetween(Date value1, Date value2) {
            addCriterion("InspectAnswerTime not between", value1, value2, "inspectanswertime");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameIsNull() {
            addCriterion("InspectAnswerPsnName is null");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameIsNotNull() {
            addCriterion("InspectAnswerPsnName is not null");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameEqualTo(String value) {
            addCriterion("InspectAnswerPsnName =", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameNotEqualTo(String value) {
            addCriterion("InspectAnswerPsnName <>", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameGreaterThan(String value) {
            addCriterion("InspectAnswerPsnName >", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameGreaterThanOrEqualTo(String value) {
            addCriterion("InspectAnswerPsnName >=", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameLessThan(String value) {
            addCriterion("InspectAnswerPsnName <", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameLessThanOrEqualTo(String value) {
            addCriterion("InspectAnswerPsnName <=", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameLike(String value) {
            addCriterion("InspectAnswerPsnName like", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameNotLike(String value) {
            addCriterion("InspectAnswerPsnName not like", value, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameIn(List<String> values) {
            addCriterion("InspectAnswerPsnName in", values, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameNotIn(List<String> values) {
            addCriterion("InspectAnswerPsnName not in", values, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameBetween(String value1, String value2) {
            addCriterion("InspectAnswerPsnName between", value1, value2, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnameNotBetween(String value1, String value2) {
            addCriterion("InspectAnswerPsnName not between", value1, value2, "inspectanswerpsnname");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoIsNull() {
            addCriterion("InspectAnswerPsnNo is null");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoIsNotNull() {
            addCriterion("InspectAnswerPsnNo is not null");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoEqualTo(String value) {
            addCriterion("InspectAnswerPsnNo =", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoNotEqualTo(String value) {
            addCriterion("InspectAnswerPsnNo <>", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoGreaterThan(String value) {
            addCriterion("InspectAnswerPsnNo >", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoGreaterThanOrEqualTo(String value) {
            addCriterion("InspectAnswerPsnNo >=", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoLessThan(String value) {
            addCriterion("InspectAnswerPsnNo <", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoLessThanOrEqualTo(String value) {
            addCriterion("InspectAnswerPsnNo <=", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoLike(String value) {
            addCriterion("InspectAnswerPsnNo like", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoNotLike(String value) {
            addCriterion("InspectAnswerPsnNo not like", value, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoIn(List<String> values) {
            addCriterion("InspectAnswerPsnNo in", values, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoNotIn(List<String> values) {
            addCriterion("InspectAnswerPsnNo not in", values, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoBetween(String value1, String value2) {
            addCriterion("InspectAnswerPsnNo between", value1, value2, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectanswerpsnnoNotBetween(String value1, String value2) {
            addCriterion("InspectAnswerPsnNo not between", value1, value2, "inspectanswerpsnno");
            return (Criteria) this;
        }

        public Criteria andInspectdailyIsNull() {
            addCriterion("inspectDaily is null");
            return (Criteria) this;
        }

        public Criteria andInspectdailyIsNotNull() {
            addCriterion("inspectDaily is not null");
            return (Criteria) this;
        }

        public Criteria andInspectdailyEqualTo(Short value) {
            addCriterion("inspectDaily =", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyNotEqualTo(Short value) {
            addCriterion("inspectDaily <>", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyGreaterThan(Short value) {
            addCriterion("inspectDaily >", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectDaily >=", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyLessThan(Short value) {
            addCriterion("inspectDaily <", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyLessThanOrEqualTo(Short value) {
            addCriterion("inspectDaily <=", value, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyIn(List<Short> values) {
            addCriterion("inspectDaily in", values, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyNotIn(List<Short> values) {
            addCriterion("inspectDaily not in", values, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyBetween(Short value1, Short value2) {
            addCriterion("inspectDaily between", value1, value2, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspectdailyNotBetween(Short value1, Short value2) {
            addCriterion("inspectDaily not between", value1, value2, "inspectdaily");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIsNull() {
            addCriterion("inspectType is null");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIsNotNull() {
            addCriterion("inspectType is not null");
            return (Criteria) this;
        }

        public Criteria andInspecttypeEqualTo(Short value) {
            addCriterion("inspectType =", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotEqualTo(Short value) {
            addCriterion("inspectType <>", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeGreaterThan(Short value) {
            addCriterion("inspectType >", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectType >=", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeLessThan(Short value) {
            addCriterion("inspectType <", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeLessThanOrEqualTo(Short value) {
            addCriterion("inspectType <=", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIn(List<Short> values) {
            addCriterion("inspectType in", values, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotIn(List<Short> values) {
            addCriterion("inspectType not in", values, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeBetween(Short value1, Short value2) {
            addCriterion("inspectType between", value1, value2, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotBetween(Short value1, Short value2) {
            addCriterion("inspectType not between", value1, value2, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextIsNull() {
            addCriterion("InspectAnswerText is null");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextIsNotNull() {
            addCriterion("InspectAnswerText is not null");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextEqualTo(String value) {
            addCriterion("InspectAnswerText =", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextNotEqualTo(String value) {
            addCriterion("InspectAnswerText <>", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextGreaterThan(String value) {
            addCriterion("InspectAnswerText >", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextGreaterThanOrEqualTo(String value) {
            addCriterion("InspectAnswerText >=", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextLessThan(String value) {
            addCriterion("InspectAnswerText <", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextLessThanOrEqualTo(String value) {
            addCriterion("InspectAnswerText <=", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextLike(String value) {
            addCriterion("InspectAnswerText like", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextNotLike(String value) {
            addCriterion("InspectAnswerText not like", value, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextIn(List<String> values) {
            addCriterion("InspectAnswerText in", values, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextNotIn(List<String> values) {
            addCriterion("InspectAnswerText not in", values, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextBetween(String value1, String value2) {
            addCriterion("InspectAnswerText between", value1, value2, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andInspectanswertextNotBetween(String value1, String value2) {
            addCriterion("InspectAnswerText not between", value1, value2, "inspectanswertext");
            return (Criteria) this;
        }

        public Criteria andPlanusedateIsNull() {
            addCriterion("PlanUseDate is null");
            return (Criteria) this;
        }

        public Criteria andPlanusedateIsNotNull() {
            addCriterion("PlanUseDate is not null");
            return (Criteria) this;
        }

        public Criteria andPlanusedateEqualTo(Date value) {
            addCriterion("PlanUseDate =", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateNotEqualTo(Date value) {
            addCriterion("PlanUseDate <>", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateGreaterThan(Date value) {
            addCriterion("PlanUseDate >", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateGreaterThanOrEqualTo(Date value) {
            addCriterion("PlanUseDate >=", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateLessThan(Date value) {
            addCriterion("PlanUseDate <", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateLessThanOrEqualTo(Date value) {
            addCriterion("PlanUseDate <=", value, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateIn(List<Date> values) {
            addCriterion("PlanUseDate in", values, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateNotIn(List<Date> values) {
            addCriterion("PlanUseDate not in", values, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateBetween(Date value1, Date value2) {
            addCriterion("PlanUseDate between", value1, value2, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPlanusedateNotBetween(Date value1, Date value2) {
            addCriterion("PlanUseDate not between", value1, value2, "planusedate");
            return (Criteria) this;
        }

        public Criteria andPplnoIsNull() {
            addCriterion("pplNo is null");
            return (Criteria) this;
        }

        public Criteria andPplnoIsNotNull() {
            addCriterion("pplNo is not null");
            return (Criteria) this;
        }

        public Criteria andPplnoEqualTo(String value) {
            addCriterion("pplNo =", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotEqualTo(String value) {
            addCriterion("pplNo <>", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoGreaterThan(String value) {
            addCriterion("pplNo >", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoGreaterThanOrEqualTo(String value) {
            addCriterion("pplNo >=", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLessThan(String value) {
            addCriterion("pplNo <", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLessThanOrEqualTo(String value) {
            addCriterion("pplNo <=", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoLike(String value) {
            addCriterion("pplNo like", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotLike(String value) {
            addCriterion("pplNo not like", value, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoIn(List<String> values) {
            addCriterion("pplNo in", values, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotIn(List<String> values) {
            addCriterion("pplNo not in", values, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoBetween(String value1, String value2) {
            addCriterion("pplNo between", value1, value2, "pplno");
            return (Criteria) this;
        }

        public Criteria andPplnoNotBetween(String value1, String value2) {
            addCriterion("pplNo not between", value1, value2, "pplno");
            return (Criteria) this;
        }

        public Criteria andProjectnoIsNull() {
            addCriterion("projectNo is null");
            return (Criteria) this;
        }

        public Criteria andProjectnoIsNotNull() {
            addCriterion("projectNo is not null");
            return (Criteria) this;
        }

        public Criteria andProjectnoEqualTo(String value) {
            addCriterion("projectNo =", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotEqualTo(String value) {
            addCriterion("projectNo <>", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoGreaterThan(String value) {
            addCriterion("projectNo >", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoGreaterThanOrEqualTo(String value) {
            addCriterion("projectNo >=", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLessThan(String value) {
            addCriterion("projectNo <", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLessThanOrEqualTo(String value) {
            addCriterion("projectNo <=", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoLike(String value) {
            addCriterion("projectNo like", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotLike(String value) {
            addCriterion("projectNo not like", value, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoIn(List<String> values) {
            addCriterion("projectNo in", values, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotIn(List<String> values) {
            addCriterion("projectNo not in", values, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoBetween(String value1, String value2) {
            addCriterion("projectNo between", value1, value2, "projectno");
            return (Criteria) this;
        }

        public Criteria andProjectnoNotBetween(String value1, String value2) {
            addCriterion("projectNo not between", value1, value2, "projectno");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyIsNull() {
            addCriterion("avgOrdQty is null");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyIsNotNull() {
            addCriterion("avgOrdQty is not null");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyEqualTo(Integer value) {
            addCriterion("avgOrdQty =", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyNotEqualTo(Integer value) {
            addCriterion("avgOrdQty <>", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyGreaterThan(Integer value) {
            addCriterion("avgOrdQty >", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("avgOrdQty >=", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyLessThan(Integer value) {
            addCriterion("avgOrdQty <", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyLessThanOrEqualTo(Integer value) {
            addCriterion("avgOrdQty <=", value, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyIn(List<Integer> values) {
            addCriterion("avgOrdQty in", values, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyNotIn(List<Integer> values) {
            addCriterion("avgOrdQty not in", values, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyBetween(Integer value1, Integer value2) {
            addCriterion("avgOrdQty between", value1, value2, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andAvgordqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("avgOrdQty not between", value1, value2, "avgordqty");
            return (Criteria) this;
        }

        public Criteria andLastorddateIsNull() {
            addCriterion("LastOrdDate is null");
            return (Criteria) this;
        }

        public Criteria andLastorddateIsNotNull() {
            addCriterion("LastOrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andLastorddateEqualTo(Date value) {
            addCriterion("LastOrdDate =", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateNotEqualTo(Date value) {
            addCriterion("LastOrdDate <>", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateGreaterThan(Date value) {
            addCriterion("LastOrdDate >", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateGreaterThanOrEqualTo(Date value) {
            addCriterion("LastOrdDate >=", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateLessThan(Date value) {
            addCriterion("LastOrdDate <", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateLessThanOrEqualTo(Date value) {
            addCriterion("LastOrdDate <=", value, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateIn(List<Date> values) {
            addCriterion("LastOrdDate in", values, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateNotIn(List<Date> values) {
            addCriterion("LastOrdDate not in", values, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateBetween(Date value1, Date value2) {
            addCriterion("LastOrdDate between", value1, value2, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andLastorddateNotBetween(Date value1, Date value2) {
            addCriterion("LastOrdDate not between", value1, value2, "lastorddate");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeIsNull() {
            addCriterion("inspectApplyType is null");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeIsNotNull() {
            addCriterion("inspectApplyType is not null");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeEqualTo(Short value) {
            addCriterion("inspectApplyType =", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeNotEqualTo(Short value) {
            addCriterion("inspectApplyType <>", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeGreaterThan(Short value) {
            addCriterion("inspectApplyType >", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectApplyType >=", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeLessThan(Short value) {
            addCriterion("inspectApplyType <", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeLessThanOrEqualTo(Short value) {
            addCriterion("inspectApplyType <=", value, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeIn(List<Short> values) {
            addCriterion("inspectApplyType in", values, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeNotIn(List<Short> values) {
            addCriterion("inspectApplyType not in", values, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeBetween(Short value1, Short value2) {
            addCriterion("inspectApplyType between", value1, value2, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapplytypeNotBetween(Short value1, Short value2) {
            addCriterion("inspectApplyType not between", value1, value2, "inspectapplytype");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoIsNull() {
            addCriterion("InspectApproverNo is null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoIsNotNull() {
            addCriterion("InspectApproverNo is not null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoEqualTo(String value) {
            addCriterion("InspectApproverNo =", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoNotEqualTo(String value) {
            addCriterion("InspectApproverNo <>", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoGreaterThan(String value) {
            addCriterion("InspectApproverNo >", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoGreaterThanOrEqualTo(String value) {
            addCriterion("InspectApproverNo >=", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoLessThan(String value) {
            addCriterion("InspectApproverNo <", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoLessThanOrEqualTo(String value) {
            addCriterion("InspectApproverNo <=", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoLike(String value) {
            addCriterion("InspectApproverNo like", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoNotLike(String value) {
            addCriterion("InspectApproverNo not like", value, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoIn(List<String> values) {
            addCriterion("InspectApproverNo in", values, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoNotIn(List<String> values) {
            addCriterion("InspectApproverNo not in", values, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoBetween(String value1, String value2) {
            addCriterion("InspectApproverNo between", value1, value2, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernoNotBetween(String value1, String value2) {
            addCriterion("InspectApproverNo not between", value1, value2, "inspectapproverno");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameIsNull() {
            addCriterion("InspectApproverName is null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameIsNotNull() {
            addCriterion("InspectApproverName is not null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameEqualTo(String value) {
            addCriterion("InspectApproverName =", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameNotEqualTo(String value) {
            addCriterion("InspectApproverName <>", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameGreaterThan(String value) {
            addCriterion("InspectApproverName >", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameGreaterThanOrEqualTo(String value) {
            addCriterion("InspectApproverName >=", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameLessThan(String value) {
            addCriterion("InspectApproverName <", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameLessThanOrEqualTo(String value) {
            addCriterion("InspectApproverName <=", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameLike(String value) {
            addCriterion("InspectApproverName like", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameNotLike(String value) {
            addCriterion("InspectApproverName not like", value, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameIn(List<String> values) {
            addCriterion("InspectApproverName in", values, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameNotIn(List<String> values) {
            addCriterion("InspectApproverName not in", values, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameBetween(String value1, String value2) {
            addCriterion("InspectApproverName between", value1, value2, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovernameNotBetween(String value1, String value2) {
            addCriterion("InspectApproverName not between", value1, value2, "inspectapprovername");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeIsNull() {
            addCriterion("InspectApproverTime is null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeIsNotNull() {
            addCriterion("InspectApproverTime is not null");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeEqualTo(Date value) {
            addCriterion("InspectApproverTime =", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeNotEqualTo(Date value) {
            addCriterion("InspectApproverTime <>", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeGreaterThan(Date value) {
            addCriterion("InspectApproverTime >", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("InspectApproverTime >=", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeLessThan(Date value) {
            addCriterion("InspectApproverTime <", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeLessThanOrEqualTo(Date value) {
            addCriterion("InspectApproverTime <=", value, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeIn(List<Date> values) {
            addCriterion("InspectApproverTime in", values, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeNotIn(List<Date> values) {
            addCriterion("InspectApproverTime not in", values, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeBetween(Date value1, Date value2) {
            addCriterion("InspectApproverTime between", value1, value2, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andInspectapprovertimeNotBetween(Date value1, Date value2) {
            addCriterion("InspectApproverTime not between", value1, value2, "inspectapprovertime");
            return (Criteria) this;
        }

        public Criteria andModelcountIsNull() {
            addCriterion("modelCount is null");
            return (Criteria) this;
        }

        public Criteria andModelcountIsNotNull() {
            addCriterion("modelCount is not null");
            return (Criteria) this;
        }

        public Criteria andModelcountEqualTo(Integer value) {
            addCriterion("modelCount =", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountNotEqualTo(Integer value) {
            addCriterion("modelCount <>", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountGreaterThan(Integer value) {
            addCriterion("modelCount >", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("modelCount >=", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountLessThan(Integer value) {
            addCriterion("modelCount <", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountLessThanOrEqualTo(Integer value) {
            addCriterion("modelCount <=", value, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountIn(List<Integer> values) {
            addCriterion("modelCount in", values, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountNotIn(List<Integer> values) {
            addCriterion("modelCount not in", values, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountBetween(Integer value1, Integer value2) {
            addCriterion("modelCount between", value1, value2, "modelcount");
            return (Criteria) this;
        }

        public Criteria andModelcountNotBetween(Integer value1, Integer value2) {
            addCriterion("modelCount not between", value1, value2, "modelcount");
            return (Criteria) this;
        }

        public Criteria andInspectqtyIsNull() {
            addCriterion("inspectQty is null");
            return (Criteria) this;
        }

        public Criteria andInspectqtyIsNotNull() {
            addCriterion("inspectQty is not null");
            return (Criteria) this;
        }

        public Criteria andInspectqtyEqualTo(Integer value) {
            addCriterion("inspectQty =", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyNotEqualTo(Integer value) {
            addCriterion("inspectQty <>", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyGreaterThan(Integer value) {
            addCriterion("inspectQty >", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspectQty >=", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyLessThan(Integer value) {
            addCriterion("inspectQty <", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyLessThanOrEqualTo(Integer value) {
            addCriterion("inspectQty <=", value, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyIn(List<Integer> values) {
            addCriterion("inspectQty in", values, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyNotIn(List<Integer> values) {
            addCriterion("inspectQty not in", values, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyBetween(Integer value1, Integer value2) {
            addCriterion("inspectQty between", value1, value2, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("inspectQty not between", value1, value2, "inspectqty");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoIsNull() {
            addCriterion("inspectApplyNo is null");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoIsNotNull() {
            addCriterion("inspectApplyNo is not null");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoEqualTo(String value) {
            addCriterion("inspectApplyNo =", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoNotEqualTo(String value) {
            addCriterion("inspectApplyNo <>", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoGreaterThan(String value) {
            addCriterion("inspectApplyNo >", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoGreaterThanOrEqualTo(String value) {
            addCriterion("inspectApplyNo >=", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoLessThan(String value) {
            addCriterion("inspectApplyNo <", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoLessThanOrEqualTo(String value) {
            addCriterion("inspectApplyNo <=", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoLike(String value) {
            addCriterion("inspectApplyNo like", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoNotLike(String value) {
            addCriterion("inspectApplyNo not like", value, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoIn(List<String> values) {
            addCriterion("inspectApplyNo in", values, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoNotIn(List<String> values) {
            addCriterion("inspectApplyNo not in", values, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoBetween(String value1, String value2) {
            addCriterion("inspectApplyNo between", value1, value2, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andInspectapplynoNotBetween(String value1, String value2) {
            addCriterion("inspectApplyNo not between", value1, value2, "inspectapplyno");
            return (Criteria) this;
        }

        public Criteria andIswarningIsNull() {
            addCriterion("isWarning is null");
            return (Criteria) this;
        }

        public Criteria andIswarningIsNotNull() {
            addCriterion("isWarning is not null");
            return (Criteria) this;
        }

        public Criteria andIswarningEqualTo(String value) {
            addCriterion("isWarning =", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningNotEqualTo(String value) {
            addCriterion("isWarning <>", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningGreaterThan(String value) {
            addCriterion("isWarning >", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningGreaterThanOrEqualTo(String value) {
            addCriterion("isWarning >=", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningLessThan(String value) {
            addCriterion("isWarning <", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningLessThanOrEqualTo(String value) {
            addCriterion("isWarning <=", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningLike(String value) {
            addCriterion("isWarning like", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningNotLike(String value) {
            addCriterion("isWarning not like", value, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningIn(List<String> values) {
            addCriterion("isWarning in", values, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningNotIn(List<String> values) {
            addCriterion("isWarning not in", values, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningBetween(String value1, String value2) {
            addCriterion("isWarning between", value1, value2, "iswarning");
            return (Criteria) this;
        }

        public Criteria andIswarningNotBetween(String value1, String value2) {
            addCriterion("isWarning not between", value1, value2, "iswarning");
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
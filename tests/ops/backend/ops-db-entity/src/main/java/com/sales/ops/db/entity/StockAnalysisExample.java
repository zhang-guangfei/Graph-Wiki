package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StockAnalysisExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StockAnalysisExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andCalcidIsNull() {
            addCriterion("calcId is null");
            return (Criteria) this;
        }

        public Criteria andCalcidIsNotNull() {
            addCriterion("calcId is not null");
            return (Criteria) this;
        }

        public Criteria andCalcidEqualTo(Integer value) {
            addCriterion("calcId =", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidNotEqualTo(Integer value) {
            addCriterion("calcId <>", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidGreaterThan(Integer value) {
            addCriterion("calcId >", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidGreaterThanOrEqualTo(Integer value) {
            addCriterion("calcId >=", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidLessThan(Integer value) {
            addCriterion("calcId <", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidLessThanOrEqualTo(Integer value) {
            addCriterion("calcId <=", value, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidIn(List<Integer> values) {
            addCriterion("calcId in", values, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidNotIn(List<Integer> values) {
            addCriterion("calcId not in", values, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidBetween(Integer value1, Integer value2) {
            addCriterion("calcId between", value1, value2, "calcid");
            return (Criteria) this;
        }

        public Criteria andCalcidNotBetween(Integer value1, Integer value2) {
            addCriterion("calcId not between", value1, value2, "calcid");
            return (Criteria) this;
        }

        public Criteria andMonthdateIsNull() {
            addCriterion("monthDate is null");
            return (Criteria) this;
        }

        public Criteria andMonthdateIsNotNull() {
            addCriterion("monthDate is not null");
            return (Criteria) this;
        }

        public Criteria andMonthdateEqualTo(Date value) {
            addCriterionForJDBCDate("monthDate =", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("monthDate <>", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateGreaterThan(Date value) {
            addCriterionForJDBCDate("monthDate >", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("monthDate >=", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateLessThan(Date value) {
            addCriterionForJDBCDate("monthDate <", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("monthDate <=", value, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateIn(List<Date> values) {
            addCriterionForJDBCDate("monthDate in", values, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("monthDate not in", values, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("monthDate between", value1, value2, "monthdate");
            return (Criteria) this;
        }

        public Criteria andMonthdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("monthDate not between", value1, value2, "monthdate");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeIsNull() {
            addCriterion("warehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeIsNotNull() {
            addCriterion("warehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeEqualTo(String value) {
            addCriterion("warehouseCode =", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotEqualTo(String value) {
            addCriterion("warehouseCode <>", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeGreaterThan(String value) {
            addCriterion("warehouseCode >", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouseCode >=", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLessThan(String value) {
            addCriterion("warehouseCode <", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("warehouseCode <=", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeLike(String value) {
            addCriterion("warehouseCode like", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotLike(String value) {
            addCriterion("warehouseCode not like", value, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeIn(List<String> values) {
            addCriterion("warehouseCode in", values, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotIn(List<String> values) {
            addCriterion("warehouseCode not in", values, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeBetween(String value1, String value2) {
            addCriterion("warehouseCode between", value1, value2, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andWarehousecodeNotBetween(String value1, String value2) {
            addCriterion("warehouseCode not between", value1, value2, "warehousecode");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andAvaqtyIsNull() {
            addCriterion("avaQty is null");
            return (Criteria) this;
        }

        public Criteria andAvaqtyIsNotNull() {
            addCriterion("avaQty is not null");
            return (Criteria) this;
        }

        public Criteria andAvaqtyEqualTo(Integer value) {
            addCriterion("avaQty =", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyNotEqualTo(Integer value) {
            addCriterion("avaQty <>", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyGreaterThan(Integer value) {
            addCriterion("avaQty >", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("avaQty >=", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyLessThan(Integer value) {
            addCriterion("avaQty <", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyLessThanOrEqualTo(Integer value) {
            addCriterion("avaQty <=", value, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyIn(List<Integer> values) {
            addCriterion("avaQty in", values, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyNotIn(List<Integer> values) {
            addCriterion("avaQty not in", values, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyBetween(Integer value1, Integer value2) {
            addCriterion("avaQty between", value1, value2, "avaqty");
            return (Criteria) this;
        }

        public Criteria andAvaqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("avaQty not between", value1, value2, "avaqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyIsNull() {
            addCriterion("ordQty is null");
            return (Criteria) this;
        }

        public Criteria andOrdqtyIsNotNull() {
            addCriterion("ordQty is not null");
            return (Criteria) this;
        }

        public Criteria andOrdqtyEqualTo(Integer value) {
            addCriterion("ordQty =", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyNotEqualTo(Integer value) {
            addCriterion("ordQty <>", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyGreaterThan(Integer value) {
            addCriterion("ordQty >", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ordQty >=", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyLessThan(Integer value) {
            addCriterion("ordQty <", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyLessThanOrEqualTo(Integer value) {
            addCriterion("ordQty <=", value, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyIn(List<Integer> values) {
            addCriterion("ordQty in", values, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyNotIn(List<Integer> values) {
            addCriterion("ordQty not in", values, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyBetween(Integer value1, Integer value2) {
            addCriterion("ordQty between", value1, value2, "ordqty");
            return (Criteria) this;
        }

        public Criteria andOrdqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ordQty not between", value1, value2, "ordqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyIsNull() {
            addCriterion("transQty is null");
            return (Criteria) this;
        }

        public Criteria andTransqtyIsNotNull() {
            addCriterion("transQty is not null");
            return (Criteria) this;
        }

        public Criteria andTransqtyEqualTo(Integer value) {
            addCriterion("transQty =", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyNotEqualTo(Integer value) {
            addCriterion("transQty <>", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyGreaterThan(Integer value) {
            addCriterion("transQty >", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("transQty >=", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyLessThan(Integer value) {
            addCriterion("transQty <", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyLessThanOrEqualTo(Integer value) {
            addCriterion("transQty <=", value, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyIn(List<Integer> values) {
            addCriterion("transQty in", values, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyNotIn(List<Integer> values) {
            addCriterion("transQty not in", values, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyBetween(Integer value1, Integer value2) {
            addCriterion("transQty between", value1, value2, "transqty");
            return (Criteria) this;
        }

        public Criteria andTransqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("transQty not between", value1, value2, "transqty");
            return (Criteria) this;
        }

        public Criteria andIntimeIsNull() {
            addCriterion("inTime is null");
            return (Criteria) this;
        }

        public Criteria andIntimeIsNotNull() {
            addCriterion("inTime is not null");
            return (Criteria) this;
        }

        public Criteria andIntimeEqualTo(Date value) {
            addCriterion("inTime =", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeNotEqualTo(Date value) {
            addCriterion("inTime <>", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeGreaterThan(Date value) {
            addCriterion("inTime >", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inTime >=", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeLessThan(Date value) {
            addCriterion("inTime <", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeLessThanOrEqualTo(Date value) {
            addCriterion("inTime <=", value, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeIn(List<Date> values) {
            addCriterion("inTime in", values, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeNotIn(List<Date> values) {
            addCriterion("inTime not in", values, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeBetween(Date value1, Date value2) {
            addCriterion("inTime between", value1, value2, "intime");
            return (Criteria) this;
        }

        public Criteria andIntimeNotBetween(Date value1, Date value2) {
            addCriterion("inTime not between", value1, value2, "intime");
            return (Criteria) this;
        }

        public Criteria andOuttimeIsNull() {
            addCriterion("outTime is null");
            return (Criteria) this;
        }

        public Criteria andOuttimeIsNotNull() {
            addCriterion("outTime is not null");
            return (Criteria) this;
        }

        public Criteria andOuttimeEqualTo(Date value) {
            addCriterion("outTime =", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeNotEqualTo(Date value) {
            addCriterion("outTime <>", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeGreaterThan(Date value) {
            addCriterion("outTime >", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("outTime >=", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeLessThan(Date value) {
            addCriterion("outTime <", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeLessThanOrEqualTo(Date value) {
            addCriterion("outTime <=", value, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeIn(List<Date> values) {
            addCriterion("outTime in", values, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeNotIn(List<Date> values) {
            addCriterion("outTime not in", values, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeBetween(Date value1, Date value2) {
            addCriterion("outTime between", value1, value2, "outtime");
            return (Criteria) this;
        }

        public Criteria andOuttimeNotBetween(Date value1, Date value2) {
            addCriterion("outTime not between", value1, value2, "outtime");
            return (Criteria) this;
        }

        public Criteria andPropertyidIsNull() {
            addCriterion("propertyId is null");
            return (Criteria) this;
        }

        public Criteria andPropertyidIsNotNull() {
            addCriterion("propertyId is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyidEqualTo(Long value) {
            addCriterion("propertyId =", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidNotEqualTo(Long value) {
            addCriterion("propertyId <>", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidGreaterThan(Long value) {
            addCriterion("propertyId >", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidGreaterThanOrEqualTo(Long value) {
            addCriterion("propertyId >=", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidLessThan(Long value) {
            addCriterion("propertyId <", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidLessThanOrEqualTo(Long value) {
            addCriterion("propertyId <=", value, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidIn(List<Long> values) {
            addCriterion("propertyId in", values, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidNotIn(List<Long> values) {
            addCriterion("propertyId not in", values, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidBetween(Long value1, Long value2) {
            addCriterion("propertyId between", value1, value2, "propertyid");
            return (Criteria) this;
        }

        public Criteria andPropertyidNotBetween(Long value1, Long value2) {
            addCriterion("propertyId not between", value1, value2, "propertyid");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNull() {
            addCriterion("inventoryTypeCode is null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNotNull() {
            addCriterion("inventoryTypeCode is not null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeEqualTo(String value) {
            addCriterion("inventoryTypeCode =", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotEqualTo(String value) {
            addCriterion("inventoryTypeCode <>", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThan(String value) {
            addCriterion("inventoryTypeCode >", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode >=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThan(String value) {
            addCriterion("inventoryTypeCode <", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode <=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLike(String value) {
            addCriterion("inventoryTypeCode like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotLike(String value) {
            addCriterion("inventoryTypeCode not like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIn(List<String> values) {
            addCriterion("inventoryTypeCode in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotIn(List<String> values) {
            addCriterion("inventoryTypeCode not in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode between", value1, value2, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode not between", value1, value2, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andPplIsNull() {
            addCriterion("ppl is null");
            return (Criteria) this;
        }

        public Criteria andPplIsNotNull() {
            addCriterion("ppl is not null");
            return (Criteria) this;
        }

        public Criteria andPplEqualTo(String value) {
            addCriterion("ppl =", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotEqualTo(String value) {
            addCriterion("ppl <>", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplGreaterThan(String value) {
            addCriterion("ppl >", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplGreaterThanOrEqualTo(String value) {
            addCriterion("ppl >=", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLessThan(String value) {
            addCriterion("ppl <", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLessThanOrEqualTo(String value) {
            addCriterion("ppl <=", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLike(String value) {
            addCriterion("ppl like", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotLike(String value) {
            addCriterion("ppl not like", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplIn(List<String> values) {
            addCriterion("ppl in", values, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotIn(List<String> values) {
            addCriterion("ppl not in", values, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplBetween(String value1, String value2) {
            addCriterion("ppl between", value1, value2, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotBetween(String value1, String value2) {
            addCriterion("ppl not between", value1, value2, "ppl");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIsNull() {
            addCriterion("groupCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIsNotNull() {
            addCriterion("groupCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoEqualTo(String value) {
            addCriterion("groupCustomerNo =", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotEqualTo(String value) {
            addCriterion("groupCustomerNo <>", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThan(String value) {
            addCriterion("groupCustomerNo >", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo >=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThan(String value) {
            addCriterion("groupCustomerNo <", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo <=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLike(String value) {
            addCriterion("groupCustomerNo like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotLike(String value) {
            addCriterion("groupCustomerNo not like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIn(List<String> values) {
            addCriterion("groupCustomerNo in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotIn(List<String> values) {
            addCriterion("groupCustomerNo not in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoBetween(String value1, String value2) {
            addCriterion("groupCustomerNo between", value1, value2, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotBetween(String value1, String value2) {
            addCriterion("groupCustomerNo not between", value1, value2, "groupcustomerno");
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

        public Criteria andSalesinfonoIsNull() {
            addCriterion("salesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("salesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("salesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("salesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("salesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("salesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("salesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("salesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("salesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("salesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("salesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("salesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("salesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("salesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("deptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("deptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("deptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("deptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("deptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("deptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("deptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("deptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("deptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("deptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("deptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("deptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("deptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("deptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andFreqIsNull() {
            addCriterion("freq is null");
            return (Criteria) this;
        }

        public Criteria andFreqIsNotNull() {
            addCriterion("freq is not null");
            return (Criteria) this;
        }

        public Criteria andFreqEqualTo(Integer value) {
            addCriterion("freq =", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotEqualTo(Integer value) {
            addCriterion("freq <>", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqGreaterThan(Integer value) {
            addCriterion("freq >", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqGreaterThanOrEqualTo(Integer value) {
            addCriterion("freq >=", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqLessThan(Integer value) {
            addCriterion("freq <", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqLessThanOrEqualTo(Integer value) {
            addCriterion("freq <=", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqIn(List<Integer> values) {
            addCriterion("freq in", values, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotIn(List<Integer> values) {
            addCriterion("freq not in", values, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqBetween(Integer value1, Integer value2) {
            addCriterion("freq between", value1, value2, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotBetween(Integer value1, Integer value2) {
            addCriterion("freq not between", value1, value2, "freq");
            return (Criteria) this;
        }

        public Criteria andMeanIsNull() {
            addCriterion("mean is null");
            return (Criteria) this;
        }

        public Criteria andMeanIsNotNull() {
            addCriterion("mean is not null");
            return (Criteria) this;
        }

        public Criteria andMeanEqualTo(Integer value) {
            addCriterion("mean =", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotEqualTo(Integer value) {
            addCriterion("mean <>", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanGreaterThan(Integer value) {
            addCriterion("mean >", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanGreaterThanOrEqualTo(Integer value) {
            addCriterion("mean >=", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanLessThan(Integer value) {
            addCriterion("mean <", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanLessThanOrEqualTo(Integer value) {
            addCriterion("mean <=", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanIn(List<Integer> values) {
            addCriterion("mean in", values, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotIn(List<Integer> values) {
            addCriterion("mean not in", values, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanBetween(Integer value1, Integer value2) {
            addCriterion("mean between", value1, value2, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotBetween(Integer value1, Integer value2) {
            addCriterion("mean not between", value1, value2, "mean");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("eprice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("eprice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("eprice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("eprice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("eprice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("eprice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("eprice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("eprice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice not between", value1, value2, "eprice");
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

        public Criteria andApplyoutqtyIsNull() {
            addCriterion("applyOutQty is null");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyIsNotNull() {
            addCriterion("applyOutQty is not null");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyEqualTo(Integer value) {
            addCriterion("applyOutQty =", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyNotEqualTo(Integer value) {
            addCriterion("applyOutQty <>", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyGreaterThan(Integer value) {
            addCriterion("applyOutQty >", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("applyOutQty >=", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyLessThan(Integer value) {
            addCriterion("applyOutQty <", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyLessThanOrEqualTo(Integer value) {
            addCriterion("applyOutQty <=", value, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyIn(List<Integer> values) {
            addCriterion("applyOutQty in", values, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyNotIn(List<Integer> values) {
            addCriterion("applyOutQty not in", values, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyBetween(Integer value1, Integer value2) {
            addCriterion("applyOutQty between", value1, value2, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andApplyoutqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("applyOutQty not between", value1, value2, "applyoutqty");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeIsNull() {
            addCriterion("analyseTime is null");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeIsNotNull() {
            addCriterion("analyseTime is not null");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeEqualTo(Date value) {
            addCriterion("analyseTime =", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeNotEqualTo(Date value) {
            addCriterion("analyseTime <>", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeGreaterThan(Date value) {
            addCriterion("analyseTime >", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("analyseTime >=", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeLessThan(Date value) {
            addCriterion("analyseTime <", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeLessThanOrEqualTo(Date value) {
            addCriterion("analyseTime <=", value, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeIn(List<Date> values) {
            addCriterion("analyseTime in", values, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeNotIn(List<Date> values) {
            addCriterion("analyseTime not in", values, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeBetween(Date value1, Date value2) {
            addCriterion("analyseTime between", value1, value2, "analysetime");
            return (Criteria) this;
        }

        public Criteria andAnalysetimeNotBetween(Date value1, Date value2) {
            addCriterion("analyseTime not between", value1, value2, "analysetime");
            return (Criteria) this;
        }

        public Criteria andDelaydateIsNull() {
            addCriterion("delayDate is null");
            return (Criteria) this;
        }

        public Criteria andDelaydateIsNotNull() {
            addCriterion("delayDate is not null");
            return (Criteria) this;
        }

        public Criteria andDelaydateEqualTo(Date value) {
            addCriterionForJDBCDate("delayDate =", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("delayDate <>", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateGreaterThan(Date value) {
            addCriterionForJDBCDate("delayDate >", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delayDate >=", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateLessThan(Date value) {
            addCriterionForJDBCDate("delayDate <", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delayDate <=", value, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateIn(List<Date> values) {
            addCriterionForJDBCDate("delayDate in", values, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("delayDate not in", values, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delayDate between", value1, value2, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelaydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delayDate not between", value1, value2, "delaydate");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeIsNull() {
            addCriterion("delayConsume is null");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeIsNotNull() {
            addCriterion("delayConsume is not null");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeEqualTo(Boolean value) {
            addCriterion("delayConsume =", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeNotEqualTo(Boolean value) {
            addCriterion("delayConsume <>", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeGreaterThan(Boolean value) {
            addCriterion("delayConsume >", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delayConsume >=", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeLessThan(Boolean value) {
            addCriterion("delayConsume <", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeLessThanOrEqualTo(Boolean value) {
            addCriterion("delayConsume <=", value, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeIn(List<Boolean> values) {
            addCriterion("delayConsume in", values, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeNotIn(List<Boolean> values) {
            addCriterion("delayConsume not in", values, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeBetween(Boolean value1, Boolean value2) {
            addCriterion("delayConsume between", value1, value2, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andDelayconsumeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delayConsume not between", value1, value2, "delayconsume");
            return (Criteria) this;
        }

        public Criteria andApplypsnIsNull() {
            addCriterion("applyPsn is null");
            return (Criteria) this;
        }

        public Criteria andApplypsnIsNotNull() {
            addCriterion("applyPsn is not null");
            return (Criteria) this;
        }

        public Criteria andApplypsnEqualTo(String value) {
            addCriterion("applyPsn =", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnNotEqualTo(String value) {
            addCriterion("applyPsn <>", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnGreaterThan(String value) {
            addCriterion("applyPsn >", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnGreaterThanOrEqualTo(String value) {
            addCriterion("applyPsn >=", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnLessThan(String value) {
            addCriterion("applyPsn <", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnLessThanOrEqualTo(String value) {
            addCriterion("applyPsn <=", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnLike(String value) {
            addCriterion("applyPsn like", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnNotLike(String value) {
            addCriterion("applyPsn not like", value, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnIn(List<String> values) {
            addCriterion("applyPsn in", values, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnNotIn(List<String> values) {
            addCriterion("applyPsn not in", values, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnBetween(String value1, String value2) {
            addCriterion("applyPsn between", value1, value2, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplypsnNotBetween(String value1, String value2) {
            addCriterion("applyPsn not between", value1, value2, "applypsn");
            return (Criteria) this;
        }

        public Criteria andApplytimeIsNull() {
            addCriterion("applyTime is null");
            return (Criteria) this;
        }

        public Criteria andApplytimeIsNotNull() {
            addCriterion("applyTime is not null");
            return (Criteria) this;
        }

        public Criteria andApplytimeEqualTo(Date value) {
            addCriterion("applyTime =", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotEqualTo(Date value) {
            addCriterion("applyTime <>", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeGreaterThan(Date value) {
            addCriterion("applyTime >", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("applyTime >=", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeLessThan(Date value) {
            addCriterion("applyTime <", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeLessThanOrEqualTo(Date value) {
            addCriterion("applyTime <=", value, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeIn(List<Date> values) {
            addCriterion("applyTime in", values, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotIn(List<Date> values) {
            addCriterion("applyTime not in", values, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeBetween(Date value1, Date value2) {
            addCriterion("applyTime between", value1, value2, "applytime");
            return (Criteria) this;
        }

        public Criteria andApplytimeNotBetween(Date value1, Date value2) {
            addCriterion("applyTime not between", value1, value2, "applytime");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNull() {
            addCriterion("dlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("dlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterionForJDBCDate("dlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("dlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterionForJDBCDate("dlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterionForJDBCDate("dlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterionForJDBCDate("dlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("dlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dlvDate not between", value1, value2, "dlvdate");
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
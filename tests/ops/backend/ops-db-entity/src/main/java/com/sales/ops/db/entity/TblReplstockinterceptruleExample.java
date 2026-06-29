package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblReplstockinterceptruleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TblReplstockinterceptruleExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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

        public Criteria andProcesstypeIsNull() {
            addCriterion("processType is null");
            return (Criteria) this;
        }

        public Criteria andProcesstypeIsNotNull() {
            addCriterion("processType is not null");
            return (Criteria) this;
        }

        public Criteria andProcesstypeEqualTo(Integer value) {
            addCriterion("processType =", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeNotEqualTo(Integer value) {
            addCriterion("processType <>", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeGreaterThan(Integer value) {
            addCriterion("processType >", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("processType >=", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeLessThan(Integer value) {
            addCriterion("processType <", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeLessThanOrEqualTo(Integer value) {
            addCriterion("processType <=", value, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeIn(List<Integer> values) {
            addCriterion("processType in", values, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeNotIn(List<Integer> values) {
            addCriterion("processType not in", values, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeBetween(Integer value1, Integer value2) {
            addCriterion("processType between", value1, value2, "processtype");
            return (Criteria) this;
        }

        public Criteria andProcesstypeNotBetween(Integer value1, Integer value2) {
            addCriterion("processType not between", value1, value2, "processtype");
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

        public Criteria andModeltypeIsNull() {
            addCriterion("modelType is null");
            return (Criteria) this;
        }

        public Criteria andModeltypeIsNotNull() {
            addCriterion("modelType is not null");
            return (Criteria) this;
        }

        public Criteria andModeltypeEqualTo(Short value) {
            addCriterion("modelType =", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotEqualTo(Short value) {
            addCriterion("modelType <>", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThan(Short value) {
            addCriterion("modelType >", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeGreaterThanOrEqualTo(Short value) {
            addCriterion("modelType >=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThan(Short value) {
            addCriterion("modelType <", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeLessThanOrEqualTo(Short value) {
            addCriterion("modelType <=", value, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeIn(List<Short> values) {
            addCriterion("modelType in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotIn(List<Short> values) {
            addCriterion("modelType not in", values, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeBetween(Short value1, Short value2) {
            addCriterion("modelType between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andModeltypeNotBetween(Short value1, Short value2) {
            addCriterion("modelType not between", value1, value2, "modeltype");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
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

        public Criteria andAllowcustomernosIsNull() {
            addCriterion("allowCustomerNos is null");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosIsNotNull() {
            addCriterion("allowCustomerNos is not null");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosEqualTo(String value) {
            addCriterion("allowCustomerNos =", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosNotEqualTo(String value) {
            addCriterion("allowCustomerNos <>", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosGreaterThan(String value) {
            addCriterion("allowCustomerNos >", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosGreaterThanOrEqualTo(String value) {
            addCriterion("allowCustomerNos >=", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosLessThan(String value) {
            addCriterion("allowCustomerNos <", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosLessThanOrEqualTo(String value) {
            addCriterion("allowCustomerNos <=", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosLike(String value) {
            addCriterion("allowCustomerNos like", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosNotLike(String value) {
            addCriterion("allowCustomerNos not like", value, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosIn(List<String> values) {
            addCriterion("allowCustomerNos in", values, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosNotIn(List<String> values) {
            addCriterion("allowCustomerNos not in", values, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosBetween(String value1, String value2) {
            addCriterion("allowCustomerNos between", value1, value2, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andAllowcustomernosNotBetween(String value1, String value2) {
            addCriterion("allowCustomerNos not between", value1, value2, "allowcustomernos");
            return (Criteria) this;
        }

        public Criteria andMinqtyIsNull() {
            addCriterion("minQty is null");
            return (Criteria) this;
        }

        public Criteria andMinqtyIsNotNull() {
            addCriterion("minQty is not null");
            return (Criteria) this;
        }

        public Criteria andMinqtyEqualTo(Integer value) {
            addCriterion("minQty =", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyNotEqualTo(Integer value) {
            addCriterion("minQty <>", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyGreaterThan(Integer value) {
            addCriterion("minQty >", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("minQty >=", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyLessThan(Integer value) {
            addCriterion("minQty <", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyLessThanOrEqualTo(Integer value) {
            addCriterion("minQty <=", value, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyIn(List<Integer> values) {
            addCriterion("minQty in", values, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyNotIn(List<Integer> values) {
            addCriterion("minQty not in", values, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyBetween(Integer value1, Integer value2) {
            addCriterion("minQty between", value1, value2, "minqty");
            return (Criteria) this;
        }

        public Criteria andMinqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("minQty not between", value1, value2, "minqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIsNull() {
            addCriterion("maxQty is null");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIsNotNull() {
            addCriterion("maxQty is not null");
            return (Criteria) this;
        }

        public Criteria andMaxqtyEqualTo(Integer value) {
            addCriterion("maxQty =", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotEqualTo(Integer value) {
            addCriterion("maxQty <>", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyGreaterThan(Integer value) {
            addCriterion("maxQty >", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxQty >=", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyLessThan(Integer value) {
            addCriterion("maxQty <", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyLessThanOrEqualTo(Integer value) {
            addCriterion("maxQty <=", value, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyIn(List<Integer> values) {
            addCriterion("maxQty in", values, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotIn(List<Integer> values) {
            addCriterion("maxQty not in", values, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyBetween(Integer value1, Integer value2) {
            addCriterion("maxQty between", value1, value2, "maxqty");
            return (Criteria) this;
        }

        public Criteria andMaxqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("maxQty not between", value1, value2, "maxqty");
            return (Criteria) this;
        }

        public Criteria andActiontypeIsNull() {
            addCriterion("actionType is null");
            return (Criteria) this;
        }

        public Criteria andActiontypeIsNotNull() {
            addCriterion("actionType is not null");
            return (Criteria) this;
        }

        public Criteria andActiontypeEqualTo(Integer value) {
            addCriterion("actionType =", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeNotEqualTo(Integer value) {
            addCriterion("actionType <>", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeGreaterThan(Integer value) {
            addCriterion("actionType >", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("actionType >=", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeLessThan(Integer value) {
            addCriterion("actionType <", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeLessThanOrEqualTo(Integer value) {
            addCriterion("actionType <=", value, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeIn(List<Integer> values) {
            addCriterion("actionType in", values, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeNotIn(List<Integer> values) {
            addCriterion("actionType not in", values, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeBetween(Integer value1, Integer value2) {
            addCriterion("actionType between", value1, value2, "actiontype");
            return (Criteria) this;
        }

        public Criteria andActiontypeNotBetween(Integer value1, Integer value2) {
            addCriterion("actionType not between", value1, value2, "actiontype");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIsNull() {
            addCriterion("toWarehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIsNotNull() {
            addCriterion("toWarehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeEqualTo(String value) {
            addCriterion("toWarehouseCode =", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotEqualTo(String value) {
            addCriterion("toWarehouseCode <>", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeGreaterThan(String value) {
            addCriterion("toWarehouseCode >", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("toWarehouseCode >=", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLessThan(String value) {
            addCriterion("toWarehouseCode <", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("toWarehouseCode <=", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLike(String value) {
            addCriterion("toWarehouseCode like", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotLike(String value) {
            addCriterion("toWarehouseCode not like", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIn(List<String> values) {
            addCriterion("toWarehouseCode in", values, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotIn(List<String> values) {
            addCriterion("toWarehouseCode not in", values, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeBetween(String value1, String value2) {
            addCriterion("toWarehouseCode between", value1, value2, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotBetween(String value1, String value2) {
            addCriterion("toWarehouseCode not between", value1, value2, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTosupplieridIsNull() {
            addCriterion("toSupplierId is null");
            return (Criteria) this;
        }

        public Criteria andTosupplieridIsNotNull() {
            addCriterion("toSupplierId is not null");
            return (Criteria) this;
        }

        public Criteria andTosupplieridEqualTo(String value) {
            addCriterion("toSupplierId =", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridNotEqualTo(String value) {
            addCriterion("toSupplierId <>", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridGreaterThan(String value) {
            addCriterion("toSupplierId >", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("toSupplierId >=", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridLessThan(String value) {
            addCriterion("toSupplierId <", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridLessThanOrEqualTo(String value) {
            addCriterion("toSupplierId <=", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridLike(String value) {
            addCriterion("toSupplierId like", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridNotLike(String value) {
            addCriterion("toSupplierId not like", value, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridIn(List<String> values) {
            addCriterion("toSupplierId in", values, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridNotIn(List<String> values) {
            addCriterion("toSupplierId not in", values, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridBetween(String value1, String value2) {
            addCriterion("toSupplierId between", value1, value2, "tosupplierid");
            return (Criteria) this;
        }

        public Criteria andTosupplieridNotBetween(String value1, String value2) {
            addCriterion("toSupplierId not between", value1, value2, "tosupplierid");
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

        public Criteria andUpdateuserIsNull() {
            addCriterion("updateUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIsNotNull() {
            addCriterion("updateUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateuserEqualTo(String value) {
            addCriterion("updateUser =", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotEqualTo(String value) {
            addCriterion("updateUser <>", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThan(String value) {
            addCriterion("updateUser >", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserGreaterThanOrEqualTo(String value) {
            addCriterion("updateUser >=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThan(String value) {
            addCriterion("updateUser <", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLessThanOrEqualTo(String value) {
            addCriterion("updateUser <=", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserLike(String value) {
            addCriterion("updateUser like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotLike(String value) {
            addCriterion("updateUser not like", value, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserIn(List<String> values) {
            addCriterion("updateUser in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotIn(List<String> values) {
            addCriterion("updateUser not in", values, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserBetween(String value1, String value2) {
            addCriterion("updateUser between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andUpdateuserNotBetween(String value1, String value2) {
            addCriterion("updateUser not between", value1, value2, "updateuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNull() {
            addCriterion("createUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("createUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(String value) {
            addCriterion("createUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(String value) {
            addCriterion("createUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(String value) {
            addCriterion("createUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(String value) {
            addCriterion("createUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(String value) {
            addCriterion("createUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(String value) {
            addCriterion("createUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLike(String value) {
            addCriterion("createUser like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotLike(String value) {
            addCriterion("createUser not like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<String> values) {
            addCriterion("createUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<String> values) {
            addCriterion("createUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(String value1, String value2) {
            addCriterion("createUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(String value1, String value2) {
            addCriterion("createUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
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

        public Criteria andRestartflagIsNull() {
            addCriterion("restartFlag is null");
            return (Criteria) this;
        }

        public Criteria andRestartflagIsNotNull() {
            addCriterion("restartFlag is not null");
            return (Criteria) this;
        }

        public Criteria andRestartflagEqualTo(Short value) {
            addCriterion("restartFlag =", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagNotEqualTo(Short value) {
            addCriterion("restartFlag <>", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagGreaterThan(Short value) {
            addCriterion("restartFlag >", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagGreaterThanOrEqualTo(Short value) {
            addCriterion("restartFlag >=", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagLessThan(Short value) {
            addCriterion("restartFlag <", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagLessThanOrEqualTo(Short value) {
            addCriterion("restartFlag <=", value, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagIn(List<Short> values) {
            addCriterion("restartFlag in", values, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagNotIn(List<Short> values) {
            addCriterion("restartFlag not in", values, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagBetween(Short value1, Short value2) {
            addCriterion("restartFlag between", value1, value2, "restartflag");
            return (Criteria) this;
        }

        public Criteria andRestartflagNotBetween(Short value1, Short value2) {
            addCriterion("restartFlag not between", value1, value2, "restartflag");
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
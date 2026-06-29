package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDeliveryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductDeliveryExample() {
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

        public Criteria andOrgcountryidIsNull() {
            addCriterion("orgCountryId is null");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidIsNotNull() {
            addCriterion("orgCountryId is not null");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidEqualTo(String value) {
            addCriterion("orgCountryId =", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotEqualTo(String value) {
            addCriterion("orgCountryId <>", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidGreaterThan(String value) {
            addCriterion("orgCountryId >", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidGreaterThanOrEqualTo(String value) {
            addCriterion("orgCountryId >=", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLessThan(String value) {
            addCriterion("orgCountryId <", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLessThanOrEqualTo(String value) {
            addCriterion("orgCountryId <=", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidLike(String value) {
            addCriterion("orgCountryId like", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotLike(String value) {
            addCriterion("orgCountryId not like", value, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidIn(List<String> values) {
            addCriterion("orgCountryId in", values, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotIn(List<String> values) {
            addCriterion("orgCountryId not in", values, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidBetween(String value1, String value2) {
            addCriterion("orgCountryId between", value1, value2, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andOrgcountryidNotBetween(String value1, String value2) {
            addCriterion("orgCountryId not between", value1, value2, "orgcountryid");
            return (Criteria) this;
        }

        public Criteria andSupplyidIsNull() {
            addCriterion("supplyId is null");
            return (Criteria) this;
        }

        public Criteria andSupplyidIsNotNull() {
            addCriterion("supplyId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyidEqualTo(String value) {
            addCriterion("supplyId =", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidNotEqualTo(String value) {
            addCriterion("supplyId <>", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidGreaterThan(String value) {
            addCriterion("supplyId >", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidGreaterThanOrEqualTo(String value) {
            addCriterion("supplyId >=", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidLessThan(String value) {
            addCriterion("supplyId <", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidLessThanOrEqualTo(String value) {
            addCriterion("supplyId <=", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidLike(String value) {
            addCriterion("supplyId like", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidNotLike(String value) {
            addCriterion("supplyId not like", value, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidIn(List<String> values) {
            addCriterion("supplyId in", values, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidNotIn(List<String> values) {
            addCriterion("supplyId not in", values, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidBetween(String value1, String value2) {
            addCriterion("supplyId between", value1, value2, "supplyid");
            return (Criteria) this;
        }

        public Criteria andSupplyidNotBetween(String value1, String value2) {
            addCriterion("supplyId not between", value1, value2, "supplyid");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIsNull() {
            addCriterion("stdDlvDay is null");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIsNotNull() {
            addCriterion("stdDlvDay is not null");
            return (Criteria) this;
        }

        public Criteria andStddlvdayEqualTo(Integer value) {
            addCriterion("stdDlvDay =", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotEqualTo(Integer value) {
            addCriterion("stdDlvDay <>", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayGreaterThan(Integer value) {
            addCriterion("stdDlvDay >", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDay >=", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayLessThan(Integer value) {
            addCriterion("stdDlvDay <", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayLessThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDay <=", value, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayIn(List<Integer> values) {
            addCriterion("stdDlvDay in", values, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotIn(List<Integer> values) {
            addCriterion("stdDlvDay not in", values, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDay between", value1, value2, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdayNotBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDay not between", value1, value2, "stddlvday");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIsNull() {
            addCriterion("stdDlvDateMaxNumber is null");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIsNotNull() {
            addCriterion("stdDlvDateMaxNumber is not null");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber =", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber <>", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberGreaterThan(Integer value) {
            addCriterion("stdDlvDateMaxNumber >", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber >=", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberLessThan(Integer value) {
            addCriterion("stdDlvDateMaxNumber <", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberLessThanOrEqualTo(Integer value) {
            addCriterion("stdDlvDateMaxNumber <=", value, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberIn(List<Integer> values) {
            addCriterion("stdDlvDateMaxNumber in", values, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotIn(List<Integer> values) {
            addCriterion("stdDlvDateMaxNumber not in", values, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDateMaxNumber between", value1, value2, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andStddlvdatemaxnumberNotBetween(Integer value1, Integer value2) {
            addCriterion("stdDlvDateMaxNumber not between", value1, value2, "stddlvdatemaxnumber");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIsNull() {
            addCriterion("maxProdQty is null");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIsNotNull() {
            addCriterion("maxProdQty is not null");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyEqualTo(Integer value) {
            addCriterion("maxProdQty =", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotEqualTo(Integer value) {
            addCriterion("maxProdQty <>", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyGreaterThan(Integer value) {
            addCriterion("maxProdQty >", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxProdQty >=", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyLessThan(Integer value) {
            addCriterion("maxProdQty <", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyLessThanOrEqualTo(Integer value) {
            addCriterion("maxProdQty <=", value, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyIn(List<Integer> values) {
            addCriterion("maxProdQty in", values, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotIn(List<Integer> values) {
            addCriterion("maxProdQty not in", values, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyBetween(Integer value1, Integer value2) {
            addCriterion("maxProdQty between", value1, value2, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andMaxprodqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("maxProdQty not between", value1, value2, "maxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIsNull() {
            addCriterion("enableMaxProdQty is null");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIsNotNull() {
            addCriterion("enableMaxProdQty is not null");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty =", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty <>", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyGreaterThan(Boolean value) {
            addCriterion("enableMaxProdQty >", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty >=", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyLessThan(Boolean value) {
            addCriterion("enableMaxProdQty <", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyLessThanOrEqualTo(Boolean value) {
            addCriterion("enableMaxProdQty <=", value, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyIn(List<Boolean> values) {
            addCriterion("enableMaxProdQty in", values, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotIn(List<Boolean> values) {
            addCriterion("enableMaxProdQty not in", values, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyBetween(Boolean value1, Boolean value2) {
            addCriterion("enableMaxProdQty between", value1, value2, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andEnablemaxprodqtyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enableMaxProdQty not between", value1, value2, "enablemaxprodqty");
            return (Criteria) this;
        }

        public Criteria andIsprimaryIsNull() {
            addCriterion("isPrimary is null");
            return (Criteria) this;
        }

        public Criteria andIsprimaryIsNotNull() {
            addCriterion("isPrimary is not null");
            return (Criteria) this;
        }

        public Criteria andIsprimaryEqualTo(Boolean value) {
            addCriterion("isPrimary =", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryNotEqualTo(Boolean value) {
            addCriterion("isPrimary <>", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryGreaterThan(Boolean value) {
            addCriterion("isPrimary >", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isPrimary >=", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryLessThan(Boolean value) {
            addCriterion("isPrimary <", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryLessThanOrEqualTo(Boolean value) {
            addCriterion("isPrimary <=", value, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryIn(List<Boolean> values) {
            addCriterion("isPrimary in", values, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryNotIn(List<Boolean> values) {
            addCriterion("isPrimary not in", values, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryBetween(Boolean value1, Boolean value2) {
            addCriterion("isPrimary between", value1, value2, "isprimary");
            return (Criteria) this;
        }

        public Criteria andIsprimaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isPrimary not between", value1, value2, "isprimary");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNull() {
            addCriterion("supplierPartNo is null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNotNull() {
            addCriterion("supplierPartNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoEqualTo(String value) {
            addCriterion("supplierPartNo =", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotEqualTo(String value) {
            addCriterion("supplierPartNo <>", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThan(String value) {
            addCriterion("supplierPartNo >", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThanOrEqualTo(String value) {
            addCriterion("supplierPartNo >=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThan(String value) {
            addCriterion("supplierPartNo <", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThanOrEqualTo(String value) {
            addCriterion("supplierPartNo <=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLike(String value) {
            addCriterion("supplierPartNo like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotLike(String value) {
            addCriterion("supplierPartNo not like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIn(List<String> values) {
            addCriterion("supplierPartNo in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotIn(List<String> values) {
            addCriterion("supplierPartNo not in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoBetween(String value1, String value2) {
            addCriterion("supplierPartNo between", value1, value2, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotBetween(String value1, String value2) {
            addCriterion("supplierPartNo not between", value1, value2, "supplierpartno");
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

        public Criteria andResultsourceIsNull() {
            addCriterion("resultSource is null");
            return (Criteria) this;
        }

        public Criteria andResultsourceIsNotNull() {
            addCriterion("resultSource is not null");
            return (Criteria) this;
        }

        public Criteria andResultsourceEqualTo(String value) {
            addCriterion("resultSource =", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceNotEqualTo(String value) {
            addCriterion("resultSource <>", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceGreaterThan(String value) {
            addCriterion("resultSource >", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceGreaterThanOrEqualTo(String value) {
            addCriterion("resultSource >=", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceLessThan(String value) {
            addCriterion("resultSource <", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceLessThanOrEqualTo(String value) {
            addCriterion("resultSource <=", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceLike(String value) {
            addCriterion("resultSource like", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceNotLike(String value) {
            addCriterion("resultSource not like", value, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceIn(List<String> values) {
            addCriterion("resultSource in", values, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceNotIn(List<String> values) {
            addCriterion("resultSource not in", values, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceBetween(String value1, String value2) {
            addCriterion("resultSource between", value1, value2, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourceNotBetween(String value1, String value2) {
            addCriterion("resultSource not between", value1, value2, "resultsource");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescIsNull() {
            addCriterion("resultSourceDesc is null");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescIsNotNull() {
            addCriterion("resultSourceDesc is not null");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescEqualTo(String value) {
            addCriterion("resultSourceDesc =", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescNotEqualTo(String value) {
            addCriterion("resultSourceDesc <>", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescGreaterThan(String value) {
            addCriterion("resultSourceDesc >", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescGreaterThanOrEqualTo(String value) {
            addCriterion("resultSourceDesc >=", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescLessThan(String value) {
            addCriterion("resultSourceDesc <", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescLessThanOrEqualTo(String value) {
            addCriterion("resultSourceDesc <=", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescLike(String value) {
            addCriterion("resultSourceDesc like", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescNotLike(String value) {
            addCriterion("resultSourceDesc not like", value, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescIn(List<String> values) {
            addCriterion("resultSourceDesc in", values, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescNotIn(List<String> values) {
            addCriterion("resultSourceDesc not in", values, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescBetween(String value1, String value2) {
            addCriterion("resultSourceDesc between", value1, value2, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andResultsourcedescNotBetween(String value1, String value2) {
            addCriterion("resultSourceDesc not between", value1, value2, "resultsourcedesc");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
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

        public Criteria andCompanyidIsNull() {
            addCriterion("companyId is null");
            return (Criteria) this;
        }

        public Criteria andCompanyidIsNotNull() {
            addCriterion("companyId is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyidEqualTo(String value) {
            addCriterion("companyId =", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidNotEqualTo(String value) {
            addCriterion("companyId <>", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidGreaterThan(String value) {
            addCriterion("companyId >", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("companyId >=", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidLessThan(String value) {
            addCriterion("companyId <", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidLessThanOrEqualTo(String value) {
            addCriterion("companyId <=", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidLike(String value) {
            addCriterion("companyId like", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidNotLike(String value) {
            addCriterion("companyId not like", value, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidIn(List<String> values) {
            addCriterion("companyId in", values, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidNotIn(List<String> values) {
            addCriterion("companyId not in", values, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidBetween(String value1, String value2) {
            addCriterion("companyId between", value1, value2, "companyid");
            return (Criteria) this;
        }

        public Criteria andCompanyidNotBetween(String value1, String value2) {
            addCriterion("companyId not between", value1, value2, "companyid");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayIsNull() {
            addCriterion("stdDeliveryDay is null");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayIsNotNull() {
            addCriterion("stdDeliveryDay is not null");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayEqualTo(Integer value) {
            addCriterion("stdDeliveryDay =", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayNotEqualTo(Integer value) {
            addCriterion("stdDeliveryDay <>", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayGreaterThan(Integer value) {
            addCriterion("stdDeliveryDay >", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdDeliveryDay >=", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayLessThan(Integer value) {
            addCriterion("stdDeliveryDay <", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayLessThanOrEqualTo(Integer value) {
            addCriterion("stdDeliveryDay <=", value, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayIn(List<Integer> values) {
            addCriterion("stdDeliveryDay in", values, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayNotIn(List<Integer> values) {
            addCriterion("stdDeliveryDay not in", values, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayBetween(Integer value1, Integer value2) {
            addCriterion("stdDeliveryDay between", value1, value2, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andStddeliverydayNotBetween(Integer value1, Integer value2) {
            addCriterion("stdDeliveryDay not between", value1, value2, "stddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayIsNull() {
            addCriterion("enableStdDeliveryDay is null");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayIsNotNull() {
            addCriterion("enableStdDeliveryDay is not null");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayEqualTo(Boolean value) {
            addCriterion("enableStdDeliveryDay =", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayNotEqualTo(Boolean value) {
            addCriterion("enableStdDeliveryDay <>", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayGreaterThan(Boolean value) {
            addCriterion("enableStdDeliveryDay >", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enableStdDeliveryDay >=", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayLessThan(Boolean value) {
            addCriterion("enableStdDeliveryDay <", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayLessThanOrEqualTo(Boolean value) {
            addCriterion("enableStdDeliveryDay <=", value, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayIn(List<Boolean> values) {
            addCriterion("enableStdDeliveryDay in", values, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayNotIn(List<Boolean> values) {
            addCriterion("enableStdDeliveryDay not in", values, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayBetween(Boolean value1, Boolean value2) {
            addCriterion("enableStdDeliveryDay between", value1, value2, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnablestddeliverydayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enableStdDeliveryDay not between", value1, value2, "enablestddeliveryday");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryIsNull() {
            addCriterion("enableInventory is null");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryIsNotNull() {
            addCriterion("enableInventory is not null");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryEqualTo(Boolean value) {
            addCriterion("enableInventory =", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryNotEqualTo(Boolean value) {
            addCriterion("enableInventory <>", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryGreaterThan(Boolean value) {
            addCriterion("enableInventory >", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enableInventory >=", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryLessThan(Boolean value) {
            addCriterion("enableInventory <", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryLessThanOrEqualTo(Boolean value) {
            addCriterion("enableInventory <=", value, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryIn(List<Boolean> values) {
            addCriterion("enableInventory in", values, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryNotIn(List<Boolean> values) {
            addCriterion("enableInventory not in", values, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryBetween(Boolean value1, Boolean value2) {
            addCriterion("enableInventory between", value1, value2, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andEnableinventoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enableInventory not between", value1, value2, "enableinventory");
            return (Criteria) this;
        }

        public Criteria andFcostCodeIsNull() {
            addCriterion("fcost_code is null");
            return (Criteria) this;
        }

        public Criteria andFcostCodeIsNotNull() {
            addCriterion("fcost_code is not null");
            return (Criteria) this;
        }

        public Criteria andFcostCodeEqualTo(String value) {
            addCriterion("fcost_code =", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeNotEqualTo(String value) {
            addCriterion("fcost_code <>", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeGreaterThan(String value) {
            addCriterion("fcost_code >", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("fcost_code >=", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeLessThan(String value) {
            addCriterion("fcost_code <", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeLessThanOrEqualTo(String value) {
            addCriterion("fcost_code <=", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeLike(String value) {
            addCriterion("fcost_code like", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeNotLike(String value) {
            addCriterion("fcost_code not like", value, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeIn(List<String> values) {
            addCriterion("fcost_code in", values, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeNotIn(List<String> values) {
            addCriterion("fcost_code not in", values, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeBetween(String value1, String value2) {
            addCriterion("fcost_code between", value1, value2, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFcostCodeNotBetween(String value1, String value2) {
            addCriterion("fcost_code not between", value1, value2, "fcostCode");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayIsNull() {
            addCriterion("fstDeliveryDay is null");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayIsNotNull() {
            addCriterion("fstDeliveryDay is not null");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayEqualTo(Integer value) {
            addCriterion("fstDeliveryDay =", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayNotEqualTo(Integer value) {
            addCriterion("fstDeliveryDay <>", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayGreaterThan(Integer value) {
            addCriterion("fstDeliveryDay >", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayGreaterThanOrEqualTo(Integer value) {
            addCriterion("fstDeliveryDay >=", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayLessThan(Integer value) {
            addCriterion("fstDeliveryDay <", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayLessThanOrEqualTo(Integer value) {
            addCriterion("fstDeliveryDay <=", value, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayIn(List<Integer> values) {
            addCriterion("fstDeliveryDay in", values, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayNotIn(List<Integer> values) {
            addCriterion("fstDeliveryDay not in", values, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayBetween(Integer value1, Integer value2) {
            addCriterion("fstDeliveryDay between", value1, value2, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFstdeliverydayNotBetween(Integer value1, Integer value2) {
            addCriterion("fstDeliveryDay not between", value1, value2, "fstdeliveryday");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidIsNull() {
            addCriterion("fstTransTypeId is null");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidIsNotNull() {
            addCriterion("fstTransTypeId is not null");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidEqualTo(String value) {
            addCriterion("fstTransTypeId =", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidNotEqualTo(String value) {
            addCriterion("fstTransTypeId <>", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidGreaterThan(String value) {
            addCriterion("fstTransTypeId >", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidGreaterThanOrEqualTo(String value) {
            addCriterion("fstTransTypeId >=", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidLessThan(String value) {
            addCriterion("fstTransTypeId <", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidLessThanOrEqualTo(String value) {
            addCriterion("fstTransTypeId <=", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidLike(String value) {
            addCriterion("fstTransTypeId like", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidNotLike(String value) {
            addCriterion("fstTransTypeId not like", value, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidIn(List<String> values) {
            addCriterion("fstTransTypeId in", values, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidNotIn(List<String> values) {
            addCriterion("fstTransTypeId not in", values, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidBetween(String value1, String value2) {
            addCriterion("fstTransTypeId between", value1, value2, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andFsttranstypeidNotBetween(String value1, String value2) {
            addCriterion("fstTransTypeId not between", value1, value2, "fsttranstypeid");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayIsNull() {
            addCriterion("shipDeliveryDay is null");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayIsNotNull() {
            addCriterion("shipDeliveryDay is not null");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayEqualTo(Integer value) {
            addCriterion("shipDeliveryDay =", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayNotEqualTo(Integer value) {
            addCriterion("shipDeliveryDay <>", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayGreaterThan(Integer value) {
            addCriterion("shipDeliveryDay >", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayGreaterThanOrEqualTo(Integer value) {
            addCriterion("shipDeliveryDay >=", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayLessThan(Integer value) {
            addCriterion("shipDeliveryDay <", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayLessThanOrEqualTo(Integer value) {
            addCriterion("shipDeliveryDay <=", value, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayIn(List<Integer> values) {
            addCriterion("shipDeliveryDay in", values, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayNotIn(List<Integer> values) {
            addCriterion("shipDeliveryDay not in", values, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayBetween(Integer value1, Integer value2) {
            addCriterion("shipDeliveryDay between", value1, value2, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andShipdeliverydayNotBetween(Integer value1, Integer value2) {
            addCriterion("shipDeliveryDay not between", value1, value2, "shipdeliveryday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayIsNull() {
            addCriterion("stdProductManuDay is null");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayIsNotNull() {
            addCriterion("stdProductManuDay is not null");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayEqualTo(Integer value) {
            addCriterion("stdProductManuDay =", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayNotEqualTo(Integer value) {
            addCriterion("stdProductManuDay <>", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayGreaterThan(Integer value) {
            addCriterion("stdProductManuDay >", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayGreaterThanOrEqualTo(Integer value) {
            addCriterion("stdProductManuDay >=", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayLessThan(Integer value) {
            addCriterion("stdProductManuDay <", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayLessThanOrEqualTo(Integer value) {
            addCriterion("stdProductManuDay <=", value, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayIn(List<Integer> values) {
            addCriterion("stdProductManuDay in", values, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayNotIn(List<Integer> values) {
            addCriterion("stdProductManuDay not in", values, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayBetween(Integer value1, Integer value2) {
            addCriterion("stdProductManuDay between", value1, value2, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andStdproductmanudayNotBetween(Integer value1, Integer value2) {
            addCriterion("stdProductManuDay not between", value1, value2, "stdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayIsNull() {
            addCriterion("nstdProductManuDay is null");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayIsNotNull() {
            addCriterion("nstdProductManuDay is not null");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayEqualTo(Integer value) {
            addCriterion("nstdProductManuDay =", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayNotEqualTo(Integer value) {
            addCriterion("nstdProductManuDay <>", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayGreaterThan(Integer value) {
            addCriterion("nstdProductManuDay >", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayGreaterThanOrEqualTo(Integer value) {
            addCriterion("nstdProductManuDay >=", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayLessThan(Integer value) {
            addCriterion("nstdProductManuDay <", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayLessThanOrEqualTo(Integer value) {
            addCriterion("nstdProductManuDay <=", value, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayIn(List<Integer> values) {
            addCriterion("nstdProductManuDay in", values, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayNotIn(List<Integer> values) {
            addCriterion("nstdProductManuDay not in", values, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayBetween(Integer value1, Integer value2) {
            addCriterion("nstdProductManuDay between", value1, value2, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andNstdproductmanudayNotBetween(Integer value1, Integer value2) {
            addCriterion("nstdProductManuDay not between", value1, value2, "nstdproductmanuday");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNull() {
            addCriterion("fullName is null");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNotNull() {
            addCriterion("fullName is not null");
            return (Criteria) this;
        }

        public Criteria andFullnameEqualTo(String value) {
            addCriterion("fullName =", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotEqualTo(String value) {
            addCriterion("fullName <>", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThan(String value) {
            addCriterion("fullName >", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThanOrEqualTo(String value) {
            addCriterion("fullName >=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThan(String value) {
            addCriterion("fullName <", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThanOrEqualTo(String value) {
            addCriterion("fullName <=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLike(String value) {
            addCriterion("fullName like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotLike(String value) {
            addCriterion("fullName not like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameIn(List<String> values) {
            addCriterion("fullName in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotIn(List<String> values) {
            addCriterion("fullName not in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameBetween(String value1, String value2) {
            addCriterion("fullName between", value1, value2, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotBetween(String value1, String value2) {
            addCriterion("fullName not between", value1, value2, "fullname");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyIsNull() {
            addCriterion("TransCurrency is null");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyIsNotNull() {
            addCriterion("TransCurrency is not null");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyEqualTo(String value) {
            addCriterion("TransCurrency =", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyNotEqualTo(String value) {
            addCriterion("TransCurrency <>", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyGreaterThan(String value) {
            addCriterion("TransCurrency >", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("TransCurrency >=", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyLessThan(String value) {
            addCriterion("TransCurrency <", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyLessThanOrEqualTo(String value) {
            addCriterion("TransCurrency <=", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyLike(String value) {
            addCriterion("TransCurrency like", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyNotLike(String value) {
            addCriterion("TransCurrency not like", value, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyIn(List<String> values) {
            addCriterion("TransCurrency in", values, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyNotIn(List<String> values) {
            addCriterion("TransCurrency not in", values, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyBetween(String value1, String value2) {
            addCriterion("TransCurrency between", value1, value2, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andTranscurrencyNotBetween(String value1, String value2) {
            addCriterion("TransCurrency not between", value1, value2, "transcurrency");
            return (Criteria) this;
        }

        public Criteria andPaymentdayIsNull() {
            addCriterion("paymentDay is null");
            return (Criteria) this;
        }

        public Criteria andPaymentdayIsNotNull() {
            addCriterion("paymentDay is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentdayEqualTo(Integer value) {
            addCriterion("paymentDay =", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayNotEqualTo(Integer value) {
            addCriterion("paymentDay <>", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayGreaterThan(Integer value) {
            addCriterion("paymentDay >", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("paymentDay >=", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayLessThan(Integer value) {
            addCriterion("paymentDay <", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayLessThanOrEqualTo(Integer value) {
            addCriterion("paymentDay <=", value, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayIn(List<Integer> values) {
            addCriterion("paymentDay in", values, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayNotIn(List<Integer> values) {
            addCriterion("paymentDay not in", values, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayBetween(Integer value1, Integer value2) {
            addCriterion("paymentDay between", value1, value2, "paymentday");
            return (Criteria) this;
        }

        public Criteria andPaymentdayNotBetween(Integer value1, Integer value2) {
            addCriterion("paymentDay not between", value1, value2, "paymentday");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andUpdatorIsNull() {
            addCriterion("updator is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("updator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("updator =", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("updator <>", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("updator >", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("updator >=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("updator <", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("updator <=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("updator like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("updator not like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorIn(List<String> values) {
            addCriterion("updator in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotIn(List<String> values) {
            addCriterion("updator not in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("updator between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("updator not between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andIsautosendIsNull() {
            addCriterion("isAutoSend is null");
            return (Criteria) this;
        }

        public Criteria andIsautosendIsNotNull() {
            addCriterion("isAutoSend is not null");
            return (Criteria) this;
        }

        public Criteria andIsautosendEqualTo(Boolean value) {
            addCriterion("isAutoSend =", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendNotEqualTo(Boolean value) {
            addCriterion("isAutoSend <>", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendGreaterThan(Boolean value) {
            addCriterion("isAutoSend >", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isAutoSend >=", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendLessThan(Boolean value) {
            addCriterion("isAutoSend <", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendLessThanOrEqualTo(Boolean value) {
            addCriterion("isAutoSend <=", value, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendIn(List<Boolean> values) {
            addCriterion("isAutoSend in", values, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendNotIn(List<Boolean> values) {
            addCriterion("isAutoSend not in", values, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendBetween(Boolean value1, Boolean value2) {
            addCriterion("isAutoSend between", value1, value2, "isautosend");
            return (Criteria) this;
        }

        public Criteria andIsautosendNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isAutoSend not between", value1, value2, "isautosend");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andSupplierClassIsNull() {
            addCriterion("supplier_class is null");
            return (Criteria) this;
        }

        public Criteria andSupplierClassIsNotNull() {
            addCriterion("supplier_class is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierClassEqualTo(String value) {
            addCriterion("supplier_class =", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotEqualTo(String value) {
            addCriterion("supplier_class <>", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassGreaterThan(String value) {
            addCriterion("supplier_class >", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_class >=", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLessThan(String value) {
            addCriterion("supplier_class <", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLessThanOrEqualTo(String value) {
            addCriterion("supplier_class <=", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLike(String value) {
            addCriterion("supplier_class like", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotLike(String value) {
            addCriterion("supplier_class not like", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassIn(List<String> values) {
            addCriterion("supplier_class in", values, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotIn(List<String> values) {
            addCriterion("supplier_class not in", values, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassBetween(String value1, String value2) {
            addCriterion("supplier_class between", value1, value2, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotBetween(String value1, String value2) {
            addCriterion("supplier_class not between", value1, value2, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalIsNull() {
            addCriterion("enable_shikomi_cal is null");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalIsNotNull() {
            addCriterion("enable_shikomi_cal is not null");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalEqualTo(Boolean value) {
            addCriterion("enable_shikomi_cal =", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalNotEqualTo(Boolean value) {
            addCriterion("enable_shikomi_cal <>", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalGreaterThan(Boolean value) {
            addCriterion("enable_shikomi_cal >", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enable_shikomi_cal >=", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalLessThan(Boolean value) {
            addCriterion("enable_shikomi_cal <", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalLessThanOrEqualTo(Boolean value) {
            addCriterion("enable_shikomi_cal <=", value, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalIn(List<Boolean> values) {
            addCriterion("enable_shikomi_cal in", values, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalNotIn(List<Boolean> values) {
            addCriterion("enable_shikomi_cal not in", values, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalBetween(Boolean value1, Boolean value2) {
            addCriterion("enable_shikomi_cal between", value1, value2, "enableShikomiCal");
            return (Criteria) this;
        }

        public Criteria andEnableShikomiCalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enable_shikomi_cal not between", value1, value2, "enableShikomiCal");
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
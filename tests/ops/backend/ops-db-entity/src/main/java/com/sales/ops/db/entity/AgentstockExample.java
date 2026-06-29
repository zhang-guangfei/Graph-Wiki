package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentstockExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AgentstockExample() {
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

        public Criteria andStockcodeIsNull() {
            addCriterion("StockCode is null");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNotNull() {
            addCriterion("StockCode is not null");
            return (Criteria) this;
        }

        public Criteria andStockcodeEqualTo(String value) {
            addCriterion("StockCode =", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotEqualTo(String value) {
            addCriterion("StockCode <>", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThan(String value) {
            addCriterion("StockCode >", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThanOrEqualTo(String value) {
            addCriterion("StockCode >=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThan(String value) {
            addCriterion("StockCode <", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThanOrEqualTo(String value) {
            addCriterion("StockCode <=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLike(String value) {
            addCriterion("StockCode like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotLike(String value) {
            addCriterion("StockCode not like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeIn(List<String> values) {
            addCriterion("StockCode in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotIn(List<String> values) {
            addCriterion("StockCode not in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeBetween(String value1, String value2) {
            addCriterion("StockCode between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotBetween(String value1, String value2) {
            addCriterion("StockCode not between", value1, value2, "stockcode");
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

        public Criteria andStocknameIsNull() {
            addCriterion("StockName is null");
            return (Criteria) this;
        }

        public Criteria andStocknameIsNotNull() {
            addCriterion("StockName is not null");
            return (Criteria) this;
        }

        public Criteria andStocknameEqualTo(String value) {
            addCriterion("StockName =", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameNotEqualTo(String value) {
            addCriterion("StockName <>", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameGreaterThan(String value) {
            addCriterion("StockName >", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameGreaterThanOrEqualTo(String value) {
            addCriterion("StockName >=", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameLessThan(String value) {
            addCriterion("StockName <", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameLessThanOrEqualTo(String value) {
            addCriterion("StockName <=", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameLike(String value) {
            addCriterion("StockName like", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameNotLike(String value) {
            addCriterion("StockName not like", value, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameIn(List<String> values) {
            addCriterion("StockName in", values, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameNotIn(List<String> values) {
            addCriterion("StockName not in", values, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameBetween(String value1, String value2) {
            addCriterion("StockName between", value1, value2, "stockname");
            return (Criteria) this;
        }

        public Criteria andStocknameNotBetween(String value1, String value2) {
            addCriterion("StockName not between", value1, value2, "stockname");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNull() {
            addCriterion("UpdTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNotNull() {
            addCriterion("UpdTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeEqualTo(Date value) {
            addCriterion("UpdTime =", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotEqualTo(Date value) {
            addCriterion("UpdTime <>", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThan(Date value) {
            addCriterion("UpdTime >", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdTime >=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThan(Date value) {
            addCriterion("UpdTime <", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdTime <=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIn(List<Date> values) {
            addCriterion("UpdTime in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotIn(List<Date> values) {
            addCriterion("UpdTime not in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeBetween(Date value1, Date value2) {
            addCriterion("UpdTime between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdTime not between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNull() {
            addCriterion("ContractNo is null");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNotNull() {
            addCriterion("ContractNo is not null");
            return (Criteria) this;
        }

        public Criteria andContractnoEqualTo(String value) {
            addCriterion("ContractNo =", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotEqualTo(String value) {
            addCriterion("ContractNo <>", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThan(String value) {
            addCriterion("ContractNo >", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThanOrEqualTo(String value) {
            addCriterion("ContractNo >=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThan(String value) {
            addCriterion("ContractNo <", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThanOrEqualTo(String value) {
            addCriterion("ContractNo <=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLike(String value) {
            addCriterion("ContractNo like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotLike(String value) {
            addCriterion("ContractNo not like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoIn(List<String> values) {
            addCriterion("ContractNo in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotIn(List<String> values) {
            addCriterion("ContractNo not in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoBetween(String value1, String value2) {
            addCriterion("ContractNo between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotBetween(String value1, String value2) {
            addCriterion("ContractNo not between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andShortaddressIsNull() {
            addCriterion("ShortAddress is null");
            return (Criteria) this;
        }

        public Criteria andShortaddressIsNotNull() {
            addCriterion("ShortAddress is not null");
            return (Criteria) this;
        }

        public Criteria andShortaddressEqualTo(String value) {
            addCriterion("ShortAddress =", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressNotEqualTo(String value) {
            addCriterion("ShortAddress <>", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressGreaterThan(String value) {
            addCriterion("ShortAddress >", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressGreaterThanOrEqualTo(String value) {
            addCriterion("ShortAddress >=", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressLessThan(String value) {
            addCriterion("ShortAddress <", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressLessThanOrEqualTo(String value) {
            addCriterion("ShortAddress <=", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressLike(String value) {
            addCriterion("ShortAddress like", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressNotLike(String value) {
            addCriterion("ShortAddress not like", value, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressIn(List<String> values) {
            addCriterion("ShortAddress in", values, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressNotIn(List<String> values) {
            addCriterion("ShortAddress not in", values, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressBetween(String value1, String value2) {
            addCriterion("ShortAddress between", value1, value2, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andShortaddressNotBetween(String value1, String value2) {
            addCriterion("ShortAddress not between", value1, value2, "shortaddress");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNull() {
            addCriterion("AddressID is null");
            return (Criteria) this;
        }

        public Criteria andAddressidIsNotNull() {
            addCriterion("AddressID is not null");
            return (Criteria) this;
        }

        public Criteria andAddressidEqualTo(String value) {
            addCriterion("AddressID =", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotEqualTo(String value) {
            addCriterion("AddressID <>", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThan(String value) {
            addCriterion("AddressID >", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidGreaterThanOrEqualTo(String value) {
            addCriterion("AddressID >=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThan(String value) {
            addCriterion("AddressID <", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLessThanOrEqualTo(String value) {
            addCriterion("AddressID <=", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidLike(String value) {
            addCriterion("AddressID like", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotLike(String value) {
            addCriterion("AddressID not like", value, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidIn(List<String> values) {
            addCriterion("AddressID in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotIn(List<String> values) {
            addCriterion("AddressID not in", values, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidBetween(String value1, String value2) {
            addCriterion("AddressID between", value1, value2, "addressid");
            return (Criteria) this;
        }

        public Criteria andAddressidNotBetween(String value1, String value2) {
            addCriterion("AddressID not between", value1, value2, "addressid");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount not between", value1, value2, "discount");
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

        public Criteria andDeptnosIsNull() {
            addCriterion("DeptNos is null");
            return (Criteria) this;
        }

        public Criteria andDeptnosIsNotNull() {
            addCriterion("DeptNos is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnosEqualTo(String value) {
            addCriterion("DeptNos =", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosNotEqualTo(String value) {
            addCriterion("DeptNos <>", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosGreaterThan(String value) {
            addCriterion("DeptNos >", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosGreaterThanOrEqualTo(String value) {
            addCriterion("DeptNos >=", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosLessThan(String value) {
            addCriterion("DeptNos <", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosLessThanOrEqualTo(String value) {
            addCriterion("DeptNos <=", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosLike(String value) {
            addCriterion("DeptNos like", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosNotLike(String value) {
            addCriterion("DeptNos not like", value, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosIn(List<String> values) {
            addCriterion("DeptNos in", values, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosNotIn(List<String> values) {
            addCriterion("DeptNos not in", values, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosBetween(String value1, String value2) {
            addCriterion("DeptNos between", value1, value2, "deptnos");
            return (Criteria) this;
        }

        public Criteria andDeptnosNotBetween(String value1, String value2) {
            addCriterion("DeptNos not between", value1, value2, "deptnos");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountIsNull() {
            addCriterion("TotalModelCount is null");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountIsNotNull() {
            addCriterion("TotalModelCount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountEqualTo(Integer value) {
            addCriterion("TotalModelCount =", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountNotEqualTo(Integer value) {
            addCriterion("TotalModelCount <>", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountGreaterThan(Integer value) {
            addCriterion("TotalModelCount >", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("TotalModelCount >=", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountLessThan(Integer value) {
            addCriterion("TotalModelCount <", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountLessThanOrEqualTo(Integer value) {
            addCriterion("TotalModelCount <=", value, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountIn(List<Integer> values) {
            addCriterion("TotalModelCount in", values, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountNotIn(List<Integer> values) {
            addCriterion("TotalModelCount not in", values, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountBetween(Integer value1, Integer value2) {
            addCriterion("TotalModelCount between", value1, value2, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andTotalmodelcountNotBetween(Integer value1, Integer value2) {
            addCriterion("TotalModelCount not between", value1, value2, "totalmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountIsNull() {
            addCriterion("UnExpModelCount is null");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountIsNotNull() {
            addCriterion("UnExpModelCount is not null");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountEqualTo(Integer value) {
            addCriterion("UnExpModelCount =", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountNotEqualTo(Integer value) {
            addCriterion("UnExpModelCount <>", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountGreaterThan(Integer value) {
            addCriterion("UnExpModelCount >", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("UnExpModelCount >=", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountLessThan(Integer value) {
            addCriterion("UnExpModelCount <", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountLessThanOrEqualTo(Integer value) {
            addCriterion("UnExpModelCount <=", value, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountIn(List<Integer> values) {
            addCriterion("UnExpModelCount in", values, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountNotIn(List<Integer> values) {
            addCriterion("UnExpModelCount not in", values, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountBetween(Integer value1, Integer value2) {
            addCriterion("UnExpModelCount between", value1, value2, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andUnexpmodelcountNotBetween(Integer value1, Integer value2) {
            addCriterion("UnExpModelCount not between", value1, value2, "unexpmodelcount");
            return (Criteria) this;
        }

        public Criteria andBegindateIsNull() {
            addCriterion("BeginDate is null");
            return (Criteria) this;
        }

        public Criteria andBegindateIsNotNull() {
            addCriterion("BeginDate is not null");
            return (Criteria) this;
        }

        public Criteria andBegindateEqualTo(Date value) {
            addCriterion("BeginDate =", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateNotEqualTo(Date value) {
            addCriterion("BeginDate <>", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateGreaterThan(Date value) {
            addCriterion("BeginDate >", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateGreaterThanOrEqualTo(Date value) {
            addCriterion("BeginDate >=", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateLessThan(Date value) {
            addCriterion("BeginDate <", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateLessThanOrEqualTo(Date value) {
            addCriterion("BeginDate <=", value, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateIn(List<Date> values) {
            addCriterion("BeginDate in", values, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateNotIn(List<Date> values) {
            addCriterion("BeginDate not in", values, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateBetween(Date value1, Date value2) {
            addCriterion("BeginDate between", value1, value2, "begindate");
            return (Criteria) this;
        }

        public Criteria andBegindateNotBetween(Date value1, Date value2) {
            addCriterion("BeginDate not between", value1, value2, "begindate");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("Email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("Email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("Email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("Email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("Email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("Email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("Email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("Email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("Email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("Email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("Email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("Email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("Email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("Email not between", value1, value2, "email");
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

        public Criteria andExporttypeIsNull() {
            addCriterion("ExportType is null");
            return (Criteria) this;
        }

        public Criteria andExporttypeIsNotNull() {
            addCriterion("ExportType is not null");
            return (Criteria) this;
        }

        public Criteria andExporttypeEqualTo(Integer value) {
            addCriterion("ExportType =", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeNotEqualTo(Integer value) {
            addCriterion("ExportType <>", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeGreaterThan(Integer value) {
            addCriterion("ExportType >", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ExportType >=", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeLessThan(Integer value) {
            addCriterion("ExportType <", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeLessThanOrEqualTo(Integer value) {
            addCriterion("ExportType <=", value, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeIn(List<Integer> values) {
            addCriterion("ExportType in", values, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeNotIn(List<Integer> values) {
            addCriterion("ExportType not in", values, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeBetween(Integer value1, Integer value2) {
            addCriterion("ExportType between", value1, value2, "exporttype");
            return (Criteria) this;
        }

        public Criteria andExporttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ExportType not between", value1, value2, "exporttype");
            return (Criteria) this;
        }

        public Criteria andAutoexportIsNull() {
            addCriterion("AutoExport is null");
            return (Criteria) this;
        }

        public Criteria andAutoexportIsNotNull() {
            addCriterion("AutoExport is not null");
            return (Criteria) this;
        }

        public Criteria andAutoexportEqualTo(Boolean value) {
            addCriterion("AutoExport =", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportNotEqualTo(Boolean value) {
            addCriterion("AutoExport <>", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportGreaterThan(Boolean value) {
            addCriterion("AutoExport >", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportGreaterThanOrEqualTo(Boolean value) {
            addCriterion("AutoExport >=", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportLessThan(Boolean value) {
            addCriterion("AutoExport <", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportLessThanOrEqualTo(Boolean value) {
            addCriterion("AutoExport <=", value, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportIn(List<Boolean> values) {
            addCriterion("AutoExport in", values, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportNotIn(List<Boolean> values) {
            addCriterion("AutoExport not in", values, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportBetween(Boolean value1, Boolean value2) {
            addCriterion("AutoExport between", value1, value2, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAutoexportNotBetween(Boolean value1, Boolean value2) {
            addCriterion("AutoExport not between", value1, value2, "autoexport");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("Address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("Address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("Address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("Address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("Address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("Address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("Address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("Address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("Address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("Address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("Address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("Address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("Address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("Address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNull() {
            addCriterion("ContactPsn is null");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNotNull() {
            addCriterion("ContactPsn is not null");
            return (Criteria) this;
        }

        public Criteria andContactpsnEqualTo(String value) {
            addCriterion("ContactPsn =", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotEqualTo(String value) {
            addCriterion("ContactPsn <>", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThan(String value) {
            addCriterion("ContactPsn >", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThanOrEqualTo(String value) {
            addCriterion("ContactPsn >=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThan(String value) {
            addCriterion("ContactPsn <", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThanOrEqualTo(String value) {
            addCriterion("ContactPsn <=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLike(String value) {
            addCriterion("ContactPsn like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotLike(String value) {
            addCriterion("ContactPsn not like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnIn(List<String> values) {
            addCriterion("ContactPsn in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotIn(List<String> values) {
            addCriterion("ContactPsn not in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnBetween(String value1, String value2) {
            addCriterion("ContactPsn between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotBetween(String value1, String value2) {
            addCriterion("ContactPsn not between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContacttelIsNull() {
            addCriterion("ContactTel is null");
            return (Criteria) this;
        }

        public Criteria andContacttelIsNotNull() {
            addCriterion("ContactTel is not null");
            return (Criteria) this;
        }

        public Criteria andContacttelEqualTo(String value) {
            addCriterion("ContactTel =", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelNotEqualTo(String value) {
            addCriterion("ContactTel <>", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelGreaterThan(String value) {
            addCriterion("ContactTel >", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelGreaterThanOrEqualTo(String value) {
            addCriterion("ContactTel >=", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelLessThan(String value) {
            addCriterion("ContactTel <", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelLessThanOrEqualTo(String value) {
            addCriterion("ContactTel <=", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelLike(String value) {
            addCriterion("ContactTel like", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelNotLike(String value) {
            addCriterion("ContactTel not like", value, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelIn(List<String> values) {
            addCriterion("ContactTel in", values, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelNotIn(List<String> values) {
            addCriterion("ContactTel not in", values, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelBetween(String value1, String value2) {
            addCriterion("ContactTel between", value1, value2, "contacttel");
            return (Criteria) this;
        }

        public Criteria andContacttelNotBetween(String value1, String value2) {
            addCriterion("ContactTel not between", value1, value2, "contacttel");
            return (Criteria) this;
        }

        public Criteria andStockidIsNull() {
            addCriterion("StockId is null");
            return (Criteria) this;
        }

        public Criteria andStockidIsNotNull() {
            addCriterion("StockId is not null");
            return (Criteria) this;
        }

        public Criteria andStockidEqualTo(String value) {
            addCriterion("StockId =", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidNotEqualTo(String value) {
            addCriterion("StockId <>", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidGreaterThan(String value) {
            addCriterion("StockId >", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidGreaterThanOrEqualTo(String value) {
            addCriterion("StockId >=", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidLessThan(String value) {
            addCriterion("StockId <", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidLessThanOrEqualTo(String value) {
            addCriterion("StockId <=", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidLike(String value) {
            addCriterion("StockId like", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidNotLike(String value) {
            addCriterion("StockId not like", value, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidIn(List<String> values) {
            addCriterion("StockId in", values, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidNotIn(List<String> values) {
            addCriterion("StockId not in", values, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidBetween(String value1, String value2) {
            addCriterion("StockId between", value1, value2, "stockid");
            return (Criteria) this;
        }

        public Criteria andStockidNotBetween(String value1, String value2) {
            addCriterion("StockId not between", value1, value2, "stockid");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoIsNull() {
            addCriterion("MaxCaseNo is null");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoIsNotNull() {
            addCriterion("MaxCaseNo is not null");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoEqualTo(String value) {
            addCriterion("MaxCaseNo =", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoNotEqualTo(String value) {
            addCriterion("MaxCaseNo <>", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoGreaterThan(String value) {
            addCriterion("MaxCaseNo >", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoGreaterThanOrEqualTo(String value) {
            addCriterion("MaxCaseNo >=", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoLessThan(String value) {
            addCriterion("MaxCaseNo <", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoLessThanOrEqualTo(String value) {
            addCriterion("MaxCaseNo <=", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoLike(String value) {
            addCriterion("MaxCaseNo like", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoNotLike(String value) {
            addCriterion("MaxCaseNo not like", value, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoIn(List<String> values) {
            addCriterion("MaxCaseNo in", values, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoNotIn(List<String> values) {
            addCriterion("MaxCaseNo not in", values, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoBetween(String value1, String value2) {
            addCriterion("MaxCaseNo between", value1, value2, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andMaxcasenoNotBetween(String value1, String value2) {
            addCriterion("MaxCaseNo not between", value1, value2, "maxcaseno");
            return (Criteria) this;
        }

        public Criteria andProvincecodeIsNull() {
            addCriterion("ProvinceCode is null");
            return (Criteria) this;
        }

        public Criteria andProvincecodeIsNotNull() {
            addCriterion("ProvinceCode is not null");
            return (Criteria) this;
        }

        public Criteria andProvincecodeEqualTo(String value) {
            addCriterion("ProvinceCode =", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotEqualTo(String value) {
            addCriterion("ProvinceCode <>", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeGreaterThan(String value) {
            addCriterion("ProvinceCode >", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeGreaterThanOrEqualTo(String value) {
            addCriterion("ProvinceCode >=", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLessThan(String value) {
            addCriterion("ProvinceCode <", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLessThanOrEqualTo(String value) {
            addCriterion("ProvinceCode <=", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLike(String value) {
            addCriterion("ProvinceCode like", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotLike(String value) {
            addCriterion("ProvinceCode not like", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeIn(List<String> values) {
            addCriterion("ProvinceCode in", values, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotIn(List<String> values) {
            addCriterion("ProvinceCode not in", values, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeBetween(String value1, String value2) {
            addCriterion("ProvinceCode between", value1, value2, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotBetween(String value1, String value2) {
            addCriterion("ProvinceCode not between", value1, value2, "provincecode");
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
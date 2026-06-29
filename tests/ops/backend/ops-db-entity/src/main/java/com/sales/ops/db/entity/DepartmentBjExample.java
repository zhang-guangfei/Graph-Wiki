package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepartmentBjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepartmentBjExample() {
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

        public Criteria andDeptdescIsNull() {
            addCriterion("DeptDesc is null");
            return (Criteria) this;
        }

        public Criteria andDeptdescIsNotNull() {
            addCriterion("DeptDesc is not null");
            return (Criteria) this;
        }

        public Criteria andDeptdescEqualTo(String value) {
            addCriterion("DeptDesc =", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotEqualTo(String value) {
            addCriterion("DeptDesc <>", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescGreaterThan(String value) {
            addCriterion("DeptDesc >", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescGreaterThanOrEqualTo(String value) {
            addCriterion("DeptDesc >=", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLessThan(String value) {
            addCriterion("DeptDesc <", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLessThanOrEqualTo(String value) {
            addCriterion("DeptDesc <=", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLike(String value) {
            addCriterion("DeptDesc like", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotLike(String value) {
            addCriterion("DeptDesc not like", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescIn(List<String> values) {
            addCriterion("DeptDesc in", values, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotIn(List<String> values) {
            addCriterion("DeptDesc not in", values, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescBetween(String value1, String value2) {
            addCriterion("DeptDesc between", value1, value2, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotBetween(String value1, String value2) {
            addCriterion("DeptDesc not between", value1, value2, "deptdesc");
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

        public Criteria andPostcodeIsNull() {
            addCriterion("PostCode is null");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNotNull() {
            addCriterion("PostCode is not null");
            return (Criteria) this;
        }

        public Criteria andPostcodeEqualTo(String value) {
            addCriterion("PostCode =", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotEqualTo(String value) {
            addCriterion("PostCode <>", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThan(String value) {
            addCriterion("PostCode >", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PostCode >=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThan(String value) {
            addCriterion("PostCode <", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThanOrEqualTo(String value) {
            addCriterion("PostCode <=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLike(String value) {
            addCriterion("PostCode like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotLike(String value) {
            addCriterion("PostCode not like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIn(List<String> values) {
            addCriterion("PostCode in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotIn(List<String> values) {
            addCriterion("PostCode not in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeBetween(String value1, String value2) {
            addCriterion("PostCode between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotBetween(String value1, String value2) {
            addCriterion("PostCode not between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andTelenoIsNull() {
            addCriterion("TeleNo is null");
            return (Criteria) this;
        }

        public Criteria andTelenoIsNotNull() {
            addCriterion("TeleNo is not null");
            return (Criteria) this;
        }

        public Criteria andTelenoEqualTo(String value) {
            addCriterion("TeleNo =", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotEqualTo(String value) {
            addCriterion("TeleNo <>", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoGreaterThan(String value) {
            addCriterion("TeleNo >", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoGreaterThanOrEqualTo(String value) {
            addCriterion("TeleNo >=", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLessThan(String value) {
            addCriterion("TeleNo <", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLessThanOrEqualTo(String value) {
            addCriterion("TeleNo <=", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLike(String value) {
            addCriterion("TeleNo like", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotLike(String value) {
            addCriterion("TeleNo not like", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoIn(List<String> values) {
            addCriterion("TeleNo in", values, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotIn(List<String> values) {
            addCriterion("TeleNo not in", values, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoBetween(String value1, String value2) {
            addCriterion("TeleNo between", value1, value2, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotBetween(String value1, String value2) {
            addCriterion("TeleNo not between", value1, value2, "teleno");
            return (Criteria) this;
        }

        public Criteria andFaxnoIsNull() {
            addCriterion("FaxNo is null");
            return (Criteria) this;
        }

        public Criteria andFaxnoIsNotNull() {
            addCriterion("FaxNo is not null");
            return (Criteria) this;
        }

        public Criteria andFaxnoEqualTo(String value) {
            addCriterion("FaxNo =", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoNotEqualTo(String value) {
            addCriterion("FaxNo <>", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoGreaterThan(String value) {
            addCriterion("FaxNo >", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoGreaterThanOrEqualTo(String value) {
            addCriterion("FaxNo >=", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoLessThan(String value) {
            addCriterion("FaxNo <", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoLessThanOrEqualTo(String value) {
            addCriterion("FaxNo <=", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoLike(String value) {
            addCriterion("FaxNo like", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoNotLike(String value) {
            addCriterion("FaxNo not like", value, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoIn(List<String> values) {
            addCriterion("FaxNo in", values, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoNotIn(List<String> values) {
            addCriterion("FaxNo not in", values, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoBetween(String value1, String value2) {
            addCriterion("FaxNo between", value1, value2, "faxno");
            return (Criteria) this;
        }

        public Criteria andFaxnoNotBetween(String value1, String value2) {
            addCriterion("FaxNo not between", value1, value2, "faxno");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIsNull() {
            addCriterion("EmailAddr is null");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIsNotNull() {
            addCriterion("EmailAddr is not null");
            return (Criteria) this;
        }

        public Criteria andEmailaddrEqualTo(String value) {
            addCriterion("EmailAddr =", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotEqualTo(String value) {
            addCriterion("EmailAddr <>", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrGreaterThan(String value) {
            addCriterion("EmailAddr >", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrGreaterThanOrEqualTo(String value) {
            addCriterion("EmailAddr >=", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLessThan(String value) {
            addCriterion("EmailAddr <", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLessThanOrEqualTo(String value) {
            addCriterion("EmailAddr <=", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLike(String value) {
            addCriterion("EmailAddr like", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotLike(String value) {
            addCriterion("EmailAddr not like", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIn(List<String> values) {
            addCriterion("EmailAddr in", values, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotIn(List<String> values) {
            addCriterion("EmailAddr not in", values, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrBetween(String value1, String value2) {
            addCriterion("EmailAddr between", value1, value2, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotBetween(String value1, String value2) {
            addCriterion("EmailAddr not between", value1, value2, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andRelacodeIsNull() {
            addCriterion("RelaCode is null");
            return (Criteria) this;
        }

        public Criteria andRelacodeIsNotNull() {
            addCriterion("RelaCode is not null");
            return (Criteria) this;
        }

        public Criteria andRelacodeEqualTo(String value) {
            addCriterion("RelaCode =", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeNotEqualTo(String value) {
            addCriterion("RelaCode <>", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeGreaterThan(String value) {
            addCriterion("RelaCode >", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeGreaterThanOrEqualTo(String value) {
            addCriterion("RelaCode >=", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeLessThan(String value) {
            addCriterion("RelaCode <", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeLessThanOrEqualTo(String value) {
            addCriterion("RelaCode <=", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeLike(String value) {
            addCriterion("RelaCode like", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeNotLike(String value) {
            addCriterion("RelaCode not like", value, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeIn(List<String> values) {
            addCriterion("RelaCode in", values, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeNotIn(List<String> values) {
            addCriterion("RelaCode not in", values, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeBetween(String value1, String value2) {
            addCriterion("RelaCode between", value1, value2, "relacode");
            return (Criteria) this;
        }

        public Criteria andRelacodeNotBetween(String value1, String value2) {
            addCriterion("RelaCode not between", value1, value2, "relacode");
            return (Criteria) this;
        }

        public Criteria andFilepathIsNull() {
            addCriterion("FilePath is null");
            return (Criteria) this;
        }

        public Criteria andFilepathIsNotNull() {
            addCriterion("FilePath is not null");
            return (Criteria) this;
        }

        public Criteria andFilepathEqualTo(String value) {
            addCriterion("FilePath =", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotEqualTo(String value) {
            addCriterion("FilePath <>", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathGreaterThan(String value) {
            addCriterion("FilePath >", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathGreaterThanOrEqualTo(String value) {
            addCriterion("FilePath >=", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLessThan(String value) {
            addCriterion("FilePath <", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLessThanOrEqualTo(String value) {
            addCriterion("FilePath <=", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLike(String value) {
            addCriterion("FilePath like", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotLike(String value) {
            addCriterion("FilePath not like", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathIn(List<String> values) {
            addCriterion("FilePath in", values, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotIn(List<String> values) {
            addCriterion("FilePath not in", values, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathBetween(String value1, String value2) {
            addCriterion("FilePath between", value1, value2, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotBetween(String value1, String value2) {
            addCriterion("FilePath not between", value1, value2, "filepath");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNull() {
            addCriterion("StateFlag is null");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNotNull() {
            addCriterion("StateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andStateflagEqualTo(String value) {
            addCriterion("StateFlag =", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotEqualTo(String value) {
            addCriterion("StateFlag <>", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThan(String value) {
            addCriterion("StateFlag >", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThanOrEqualTo(String value) {
            addCriterion("StateFlag >=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThan(String value) {
            addCriterion("StateFlag <", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThanOrEqualTo(String value) {
            addCriterion("StateFlag <=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLike(String value) {
            addCriterion("StateFlag like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotLike(String value) {
            addCriterion("StateFlag not like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagIn(List<String> values) {
            addCriterion("StateFlag in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotIn(List<String> values) {
            addCriterion("StateFlag not in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagBetween(String value1, String value2) {
            addCriterion("StateFlag between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotBetween(String value1, String value2) {
            addCriterion("StateFlag not between", value1, value2, "stateflag");
            return (Criteria) this;
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

        public Criteria andLocationnoIsNull() {
            addCriterion("LocationNo is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNotNull() {
            addCriterion("LocationNo is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoEqualTo(String value) {
            addCriterion("LocationNo =", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotEqualTo(String value) {
            addCriterion("LocationNo <>", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThan(String value) {
            addCriterion("LocationNo >", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThanOrEqualTo(String value) {
            addCriterion("LocationNo >=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThan(String value) {
            addCriterion("LocationNo <", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThanOrEqualTo(String value) {
            addCriterion("LocationNo <=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLike(String value) {
            addCriterion("LocationNo like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotLike(String value) {
            addCriterion("LocationNo not like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIn(List<String> values) {
            addCriterion("LocationNo in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotIn(List<String> values) {
            addCriterion("LocationNo not in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoBetween(String value1, String value2) {
            addCriterion("LocationNo between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotBetween(String value1, String value2) {
            addCriterion("LocationNo not between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andSuperintendentIsNull() {
            addCriterion("SuperIntendent is null");
            return (Criteria) this;
        }

        public Criteria andSuperintendentIsNotNull() {
            addCriterion("SuperIntendent is not null");
            return (Criteria) this;
        }

        public Criteria andSuperintendentEqualTo(String value) {
            addCriterion("SuperIntendent =", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentNotEqualTo(String value) {
            addCriterion("SuperIntendent <>", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentGreaterThan(String value) {
            addCriterion("SuperIntendent >", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentGreaterThanOrEqualTo(String value) {
            addCriterion("SuperIntendent >=", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentLessThan(String value) {
            addCriterion("SuperIntendent <", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentLessThanOrEqualTo(String value) {
            addCriterion("SuperIntendent <=", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentLike(String value) {
            addCriterion("SuperIntendent like", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentNotLike(String value) {
            addCriterion("SuperIntendent not like", value, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentIn(List<String> values) {
            addCriterion("SuperIntendent in", values, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentNotIn(List<String> values) {
            addCriterion("SuperIntendent not in", values, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentBetween(String value1, String value2) {
            addCriterion("SuperIntendent between", value1, value2, "superintendent");
            return (Criteria) this;
        }

        public Criteria andSuperintendentNotBetween(String value1, String value2) {
            addCriterion("SuperIntendent not between", value1, value2, "superintendent");
            return (Criteria) this;
        }

        public Criteria andInsidepsnIsNull() {
            addCriterion("InsidePsn is null");
            return (Criteria) this;
        }

        public Criteria andInsidepsnIsNotNull() {
            addCriterion("InsidePsn is not null");
            return (Criteria) this;
        }

        public Criteria andInsidepsnEqualTo(String value) {
            addCriterion("InsidePsn =", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnNotEqualTo(String value) {
            addCriterion("InsidePsn <>", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnGreaterThan(String value) {
            addCriterion("InsidePsn >", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnGreaterThanOrEqualTo(String value) {
            addCriterion("InsidePsn >=", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnLessThan(String value) {
            addCriterion("InsidePsn <", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnLessThanOrEqualTo(String value) {
            addCriterion("InsidePsn <=", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnLike(String value) {
            addCriterion("InsidePsn like", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnNotLike(String value) {
            addCriterion("InsidePsn not like", value, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnIn(List<String> values) {
            addCriterion("InsidePsn in", values, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnNotIn(List<String> values) {
            addCriterion("InsidePsn not in", values, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnBetween(String value1, String value2) {
            addCriterion("InsidePsn between", value1, value2, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andInsidepsnNotBetween(String value1, String value2) {
            addCriterion("InsidePsn not between", value1, value2, "insidepsn");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("UpdDate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("UpdDate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("UpdDate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("UpdDate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("UpdDate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdDate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("UpdDate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("UpdDate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("UpdDate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("UpdDate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("UpdDate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("UpdDate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1IsNull() {
            addCriterion("EmailAddr1 is null");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1IsNotNull() {
            addCriterion("EmailAddr1 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1EqualTo(String value) {
            addCriterion("EmailAddr1 =", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1NotEqualTo(String value) {
            addCriterion("EmailAddr1 <>", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1GreaterThan(String value) {
            addCriterion("EmailAddr1 >", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1GreaterThanOrEqualTo(String value) {
            addCriterion("EmailAddr1 >=", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1LessThan(String value) {
            addCriterion("EmailAddr1 <", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1LessThanOrEqualTo(String value) {
            addCriterion("EmailAddr1 <=", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1Like(String value) {
            addCriterion("EmailAddr1 like", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1NotLike(String value) {
            addCriterion("EmailAddr1 not like", value, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1In(List<String> values) {
            addCriterion("EmailAddr1 in", values, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1NotIn(List<String> values) {
            addCriterion("EmailAddr1 not in", values, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1Between(String value1, String value2) {
            addCriterion("EmailAddr1 between", value1, value2, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andEmailaddr1NotBetween(String value1, String value2) {
            addCriterion("EmailAddr1 not between", value1, value2, "emailaddr1");
            return (Criteria) this;
        }

        public Criteria andInvflagIsNull() {
            addCriterion("InvFlag is null");
            return (Criteria) this;
        }

        public Criteria andInvflagIsNotNull() {
            addCriterion("InvFlag is not null");
            return (Criteria) this;
        }

        public Criteria andInvflagEqualTo(String value) {
            addCriterion("InvFlag =", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotEqualTo(String value) {
            addCriterion("InvFlag <>", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagGreaterThan(String value) {
            addCriterion("InvFlag >", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagGreaterThanOrEqualTo(String value) {
            addCriterion("InvFlag >=", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLessThan(String value) {
            addCriterion("InvFlag <", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLessThanOrEqualTo(String value) {
            addCriterion("InvFlag <=", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagLike(String value) {
            addCriterion("InvFlag like", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotLike(String value) {
            addCriterion("InvFlag not like", value, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagIn(List<String> values) {
            addCriterion("InvFlag in", values, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotIn(List<String> values) {
            addCriterion("InvFlag not in", values, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagBetween(String value1, String value2) {
            addCriterion("InvFlag between", value1, value2, "invflag");
            return (Criteria) this;
        }

        public Criteria andInvflagNotBetween(String value1, String value2) {
            addCriterion("InvFlag not between", value1, value2, "invflag");
            return (Criteria) this;
        }

        public Criteria andPostaddressIsNull() {
            addCriterion("PostAddress is null");
            return (Criteria) this;
        }

        public Criteria andPostaddressIsNotNull() {
            addCriterion("PostAddress is not null");
            return (Criteria) this;
        }

        public Criteria andPostaddressEqualTo(String value) {
            addCriterion("PostAddress =", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressNotEqualTo(String value) {
            addCriterion("PostAddress <>", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressGreaterThan(String value) {
            addCriterion("PostAddress >", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressGreaterThanOrEqualTo(String value) {
            addCriterion("PostAddress >=", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressLessThan(String value) {
            addCriterion("PostAddress <", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressLessThanOrEqualTo(String value) {
            addCriterion("PostAddress <=", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressLike(String value) {
            addCriterion("PostAddress like", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressNotLike(String value) {
            addCriterion("PostAddress not like", value, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressIn(List<String> values) {
            addCriterion("PostAddress in", values, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressNotIn(List<String> values) {
            addCriterion("PostAddress not in", values, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressBetween(String value1, String value2) {
            addCriterion("PostAddress between", value1, value2, "postaddress");
            return (Criteria) this;
        }

        public Criteria andPostaddressNotBetween(String value1, String value2) {
            addCriterion("PostAddress not between", value1, value2, "postaddress");
            return (Criteria) this;
        }

        public Criteria andBankIsNull() {
            addCriterion("Bank is null");
            return (Criteria) this;
        }

        public Criteria andBankIsNotNull() {
            addCriterion("Bank is not null");
            return (Criteria) this;
        }

        public Criteria andBankEqualTo(String value) {
            addCriterion("Bank =", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotEqualTo(String value) {
            addCriterion("Bank <>", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThan(String value) {
            addCriterion("Bank >", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThanOrEqualTo(String value) {
            addCriterion("Bank >=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThan(String value) {
            addCriterion("Bank <", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThanOrEqualTo(String value) {
            addCriterion("Bank <=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLike(String value) {
            addCriterion("Bank like", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotLike(String value) {
            addCriterion("Bank not like", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankIn(List<String> values) {
            addCriterion("Bank in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotIn(List<String> values) {
            addCriterion("Bank not in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankBetween(String value1, String value2) {
            addCriterion("Bank between", value1, value2, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotBetween(String value1, String value2) {
            addCriterion("Bank not between", value1, value2, "bank");
            return (Criteria) this;
        }

        public Criteria andAccountnoIsNull() {
            addCriterion("AccountNo is null");
            return (Criteria) this;
        }

        public Criteria andAccountnoIsNotNull() {
            addCriterion("AccountNo is not null");
            return (Criteria) this;
        }

        public Criteria andAccountnoEqualTo(String value) {
            addCriterion("AccountNo =", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoNotEqualTo(String value) {
            addCriterion("AccountNo <>", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoGreaterThan(String value) {
            addCriterion("AccountNo >", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoGreaterThanOrEqualTo(String value) {
            addCriterion("AccountNo >=", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoLessThan(String value) {
            addCriterion("AccountNo <", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoLessThanOrEqualTo(String value) {
            addCriterion("AccountNo <=", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoLike(String value) {
            addCriterion("AccountNo like", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoNotLike(String value) {
            addCriterion("AccountNo not like", value, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoIn(List<String> values) {
            addCriterion("AccountNo in", values, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoNotIn(List<String> values) {
            addCriterion("AccountNo not in", values, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoBetween(String value1, String value2) {
            addCriterion("AccountNo between", value1, value2, "accountno");
            return (Criteria) this;
        }

        public Criteria andAccountnoNotBetween(String value1, String value2) {
            addCriterion("AccountNo not between", value1, value2, "accountno");
            return (Criteria) this;
        }

        public Criteria andTaxnoIsNull() {
            addCriterion("TaxNo is null");
            return (Criteria) this;
        }

        public Criteria andTaxnoIsNotNull() {
            addCriterion("TaxNo is not null");
            return (Criteria) this;
        }

        public Criteria andTaxnoEqualTo(String value) {
            addCriterion("TaxNo =", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoNotEqualTo(String value) {
            addCriterion("TaxNo <>", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoGreaterThan(String value) {
            addCriterion("TaxNo >", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoGreaterThanOrEqualTo(String value) {
            addCriterion("TaxNo >=", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoLessThan(String value) {
            addCriterion("TaxNo <", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoLessThanOrEqualTo(String value) {
            addCriterion("TaxNo <=", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoLike(String value) {
            addCriterion("TaxNo like", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoNotLike(String value) {
            addCriterion("TaxNo not like", value, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoIn(List<String> values) {
            addCriterion("TaxNo in", values, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoNotIn(List<String> values) {
            addCriterion("TaxNo not in", values, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoBetween(String value1, String value2) {
            addCriterion("TaxNo between", value1, value2, "taxno");
            return (Criteria) this;
        }

        public Criteria andTaxnoNotBetween(String value1, String value2) {
            addCriterion("TaxNo not between", value1, value2, "taxno");
            return (Criteria) this;
        }

        public Criteria andKfangidIsNull() {
            addCriterion("KFangID is null");
            return (Criteria) this;
        }

        public Criteria andKfangidIsNotNull() {
            addCriterion("KFangID is not null");
            return (Criteria) this;
        }

        public Criteria andKfangidEqualTo(String value) {
            addCriterion("KFangID =", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidNotEqualTo(String value) {
            addCriterion("KFangID <>", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidGreaterThan(String value) {
            addCriterion("KFangID >", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidGreaterThanOrEqualTo(String value) {
            addCriterion("KFangID >=", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidLessThan(String value) {
            addCriterion("KFangID <", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidLessThanOrEqualTo(String value) {
            addCriterion("KFangID <=", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidLike(String value) {
            addCriterion("KFangID like", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidNotLike(String value) {
            addCriterion("KFangID not like", value, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidIn(List<String> values) {
            addCriterion("KFangID in", values, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidNotIn(List<String> values) {
            addCriterion("KFangID not in", values, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidBetween(String value1, String value2) {
            addCriterion("KFangID between", value1, value2, "kfangid");
            return (Criteria) this;
        }

        public Criteria andKfangidNotBetween(String value1, String value2) {
            addCriterion("KFangID not between", value1, value2, "kfangid");
            return (Criteria) this;
        }

        public Criteria andYgidIsNull() {
            addCriterion("YGid is null");
            return (Criteria) this;
        }

        public Criteria andYgidIsNotNull() {
            addCriterion("YGid is not null");
            return (Criteria) this;
        }

        public Criteria andYgidEqualTo(String value) {
            addCriterion("YGid =", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidNotEqualTo(String value) {
            addCriterion("YGid <>", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidGreaterThan(String value) {
            addCriterion("YGid >", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidGreaterThanOrEqualTo(String value) {
            addCriterion("YGid >=", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidLessThan(String value) {
            addCriterion("YGid <", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidLessThanOrEqualTo(String value) {
            addCriterion("YGid <=", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidLike(String value) {
            addCriterion("YGid like", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidNotLike(String value) {
            addCriterion("YGid not like", value, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidIn(List<String> values) {
            addCriterion("YGid in", values, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidNotIn(List<String> values) {
            addCriterion("YGid not in", values, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidBetween(String value1, String value2) {
            addCriterion("YGid between", value1, value2, "ygid");
            return (Criteria) this;
        }

        public Criteria andYgidNotBetween(String value1, String value2) {
            addCriterion("YGid not between", value1, value2, "ygid");
            return (Criteria) this;
        }

        public Criteria andShidIsNull() {
            addCriterion("SHid is null");
            return (Criteria) this;
        }

        public Criteria andShidIsNotNull() {
            addCriterion("SHid is not null");
            return (Criteria) this;
        }

        public Criteria andShidEqualTo(String value) {
            addCriterion("SHid =", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidNotEqualTo(String value) {
            addCriterion("SHid <>", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidGreaterThan(String value) {
            addCriterion("SHid >", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidGreaterThanOrEqualTo(String value) {
            addCriterion("SHid >=", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidLessThan(String value) {
            addCriterion("SHid <", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidLessThanOrEqualTo(String value) {
            addCriterion("SHid <=", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidLike(String value) {
            addCriterion("SHid like", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidNotLike(String value) {
            addCriterion("SHid not like", value, "shid");
            return (Criteria) this;
        }

        public Criteria andShidIn(List<String> values) {
            addCriterion("SHid in", values, "shid");
            return (Criteria) this;
        }

        public Criteria andShidNotIn(List<String> values) {
            addCriterion("SHid not in", values, "shid");
            return (Criteria) this;
        }

        public Criteria andShidBetween(String value1, String value2) {
            addCriterion("SHid between", value1, value2, "shid");
            return (Criteria) this;
        }

        public Criteria andShidNotBetween(String value1, String value2) {
            addCriterion("SHid not between", value1, value2, "shid");
            return (Criteria) this;
        }

        public Criteria andDlvflagIsNull() {
            addCriterion("DlvFlag is null");
            return (Criteria) this;
        }

        public Criteria andDlvflagIsNotNull() {
            addCriterion("DlvFlag is not null");
            return (Criteria) this;
        }

        public Criteria andDlvflagEqualTo(String value) {
            addCriterion("DlvFlag =", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotEqualTo(String value) {
            addCriterion("DlvFlag <>", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagGreaterThan(String value) {
            addCriterion("DlvFlag >", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagGreaterThanOrEqualTo(String value) {
            addCriterion("DlvFlag >=", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLessThan(String value) {
            addCriterion("DlvFlag <", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLessThanOrEqualTo(String value) {
            addCriterion("DlvFlag <=", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLike(String value) {
            addCriterion("DlvFlag like", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotLike(String value) {
            addCriterion("DlvFlag not like", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagIn(List<String> values) {
            addCriterion("DlvFlag in", values, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotIn(List<String> values) {
            addCriterion("DlvFlag not in", values, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagBetween(String value1, String value2) {
            addCriterion("DlvFlag between", value1, value2, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotBetween(String value1, String value2) {
            addCriterion("DlvFlag not between", value1, value2, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andInvamountIsNull() {
            addCriterion("InvAmount is null");
            return (Criteria) this;
        }

        public Criteria andInvamountIsNotNull() {
            addCriterion("InvAmount is not null");
            return (Criteria) this;
        }

        public Criteria andInvamountEqualTo(BigDecimal value) {
            addCriterion("InvAmount =", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountNotEqualTo(BigDecimal value) {
            addCriterion("InvAmount <>", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountGreaterThan(BigDecimal value) {
            addCriterion("InvAmount >", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("InvAmount >=", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountLessThan(BigDecimal value) {
            addCriterion("InvAmount <", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("InvAmount <=", value, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountIn(List<BigDecimal> values) {
            addCriterion("InvAmount in", values, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountNotIn(List<BigDecimal> values) {
            addCriterion("InvAmount not in", values, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("InvAmount between", value1, value2, "invamount");
            return (Criteria) this;
        }

        public Criteria andInvamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("InvAmount not between", value1, value2, "invamount");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceIsNull() {
            addCriterion("EmpInvoice is null");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceIsNotNull() {
            addCriterion("EmpInvoice is not null");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceEqualTo(String value) {
            addCriterion("EmpInvoice =", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceNotEqualTo(String value) {
            addCriterion("EmpInvoice <>", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceGreaterThan(String value) {
            addCriterion("EmpInvoice >", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceGreaterThanOrEqualTo(String value) {
            addCriterion("EmpInvoice >=", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceLessThan(String value) {
            addCriterion("EmpInvoice <", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceLessThanOrEqualTo(String value) {
            addCriterion("EmpInvoice <=", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceLike(String value) {
            addCriterion("EmpInvoice like", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceNotLike(String value) {
            addCriterion("EmpInvoice not like", value, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceIn(List<String> values) {
            addCriterion("EmpInvoice in", values, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceNotIn(List<String> values) {
            addCriterion("EmpInvoice not in", values, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceBetween(String value1, String value2) {
            addCriterion("EmpInvoice between", value1, value2, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andEmpinvoiceNotBetween(String value1, String value2) {
            addCriterion("EmpInvoice not between", value1, value2, "empinvoice");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjIsNull() {
            addCriterion("DlvFlag_Bj is null");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjIsNotNull() {
            addCriterion("DlvFlag_Bj is not null");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjEqualTo(String value) {
            addCriterion("DlvFlag_Bj =", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjNotEqualTo(String value) {
            addCriterion("DlvFlag_Bj <>", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjGreaterThan(String value) {
            addCriterion("DlvFlag_Bj >", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjGreaterThanOrEqualTo(String value) {
            addCriterion("DlvFlag_Bj >=", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjLessThan(String value) {
            addCriterion("DlvFlag_Bj <", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjLessThanOrEqualTo(String value) {
            addCriterion("DlvFlag_Bj <=", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjLike(String value) {
            addCriterion("DlvFlag_Bj like", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjNotLike(String value) {
            addCriterion("DlvFlag_Bj not like", value, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjIn(List<String> values) {
            addCriterion("DlvFlag_Bj in", values, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjNotIn(List<String> values) {
            addCriterion("DlvFlag_Bj not in", values, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjBetween(String value1, String value2) {
            addCriterion("DlvFlag_Bj between", value1, value2, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagBjNotBetween(String value1, String value2) {
            addCriterion("DlvFlag_Bj not between", value1, value2, "dlvflagBj");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnIsNull() {
            addCriterion("DlvFlag_CN is null");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnIsNotNull() {
            addCriterion("DlvFlag_CN is not null");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnEqualTo(String value) {
            addCriterion("DlvFlag_CN =", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnNotEqualTo(String value) {
            addCriterion("DlvFlag_CN <>", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnGreaterThan(String value) {
            addCriterion("DlvFlag_CN >", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnGreaterThanOrEqualTo(String value) {
            addCriterion("DlvFlag_CN >=", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnLessThan(String value) {
            addCriterion("DlvFlag_CN <", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnLessThanOrEqualTo(String value) {
            addCriterion("DlvFlag_CN <=", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnLike(String value) {
            addCriterion("DlvFlag_CN like", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnNotLike(String value) {
            addCriterion("DlvFlag_CN not like", value, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnIn(List<String> values) {
            addCriterion("DlvFlag_CN in", values, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnNotIn(List<String> values) {
            addCriterion("DlvFlag_CN not in", values, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnBetween(String value1, String value2) {
            addCriterion("DlvFlag_CN between", value1, value2, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andDlvflagCnNotBetween(String value1, String value2) {
            addCriterion("DlvFlag_CN not between", value1, value2, "dlvflagCn");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryIsNull() {
            addCriterion("EmpDelivery is null");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryIsNotNull() {
            addCriterion("EmpDelivery is not null");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryEqualTo(String value) {
            addCriterion("EmpDelivery =", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryNotEqualTo(String value) {
            addCriterion("EmpDelivery <>", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryGreaterThan(String value) {
            addCriterion("EmpDelivery >", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryGreaterThanOrEqualTo(String value) {
            addCriterion("EmpDelivery >=", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryLessThan(String value) {
            addCriterion("EmpDelivery <", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryLessThanOrEqualTo(String value) {
            addCriterion("EmpDelivery <=", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryLike(String value) {
            addCriterion("EmpDelivery like", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryNotLike(String value) {
            addCriterion("EmpDelivery not like", value, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryIn(List<String> values) {
            addCriterion("EmpDelivery in", values, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryNotIn(List<String> values) {
            addCriterion("EmpDelivery not in", values, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryBetween(String value1, String value2) {
            addCriterion("EmpDelivery between", value1, value2, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andEmpdeliveryNotBetween(String value1, String value2) {
            addCriterion("EmpDelivery not between", value1, value2, "empdelivery");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoIsNull() {
            addCriterion("DeptInvNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoIsNotNull() {
            addCriterion("DeptInvNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoEqualTo(String value) {
            addCriterion("DeptInvNo =", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoNotEqualTo(String value) {
            addCriterion("DeptInvNo <>", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoGreaterThan(String value) {
            addCriterion("DeptInvNo >", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoGreaterThanOrEqualTo(String value) {
            addCriterion("DeptInvNo >=", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoLessThan(String value) {
            addCriterion("DeptInvNo <", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoLessThanOrEqualTo(String value) {
            addCriterion("DeptInvNo <=", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoLike(String value) {
            addCriterion("DeptInvNo like", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoNotLike(String value) {
            addCriterion("DeptInvNo not like", value, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoIn(List<String> values) {
            addCriterion("DeptInvNo in", values, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoNotIn(List<String> values) {
            addCriterion("DeptInvNo not in", values, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoBetween(String value1, String value2) {
            addCriterion("DeptInvNo between", value1, value2, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andDeptinvnoNotBetween(String value1, String value2) {
            addCriterion("DeptInvNo not between", value1, value2, "deptinvno");
            return (Criteria) this;
        }

        public Criteria andFeecodeIsNull() {
            addCriterion("FeeCode is null");
            return (Criteria) this;
        }

        public Criteria andFeecodeIsNotNull() {
            addCriterion("FeeCode is not null");
            return (Criteria) this;
        }

        public Criteria andFeecodeEqualTo(String value) {
            addCriterion("FeeCode =", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeNotEqualTo(String value) {
            addCriterion("FeeCode <>", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeGreaterThan(String value) {
            addCriterion("FeeCode >", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeGreaterThanOrEqualTo(String value) {
            addCriterion("FeeCode >=", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeLessThan(String value) {
            addCriterion("FeeCode <", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeLessThanOrEqualTo(String value) {
            addCriterion("FeeCode <=", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeLike(String value) {
            addCriterion("FeeCode like", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeNotLike(String value) {
            addCriterion("FeeCode not like", value, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeIn(List<String> values) {
            addCriterion("FeeCode in", values, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeNotIn(List<String> values) {
            addCriterion("FeeCode not in", values, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeBetween(String value1, String value2) {
            addCriterion("FeeCode between", value1, value2, "feecode");
            return (Criteria) this;
        }

        public Criteria andFeecodeNotBetween(String value1, String value2) {
            addCriterion("FeeCode not between", value1, value2, "feecode");
            return (Criteria) this;
        }

        public Criteria andSampleordnoIsNull() {
            addCriterion("SampleOrdNo is null");
            return (Criteria) this;
        }

        public Criteria andSampleordnoIsNotNull() {
            addCriterion("SampleOrdNo is not null");
            return (Criteria) this;
        }

        public Criteria andSampleordnoEqualTo(String value) {
            addCriterion("SampleOrdNo =", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoNotEqualTo(String value) {
            addCriterion("SampleOrdNo <>", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoGreaterThan(String value) {
            addCriterion("SampleOrdNo >", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoGreaterThanOrEqualTo(String value) {
            addCriterion("SampleOrdNo >=", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoLessThan(String value) {
            addCriterion("SampleOrdNo <", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoLessThanOrEqualTo(String value) {
            addCriterion("SampleOrdNo <=", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoLike(String value) {
            addCriterion("SampleOrdNo like", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoNotLike(String value) {
            addCriterion("SampleOrdNo not like", value, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoIn(List<String> values) {
            addCriterion("SampleOrdNo in", values, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoNotIn(List<String> values) {
            addCriterion("SampleOrdNo not in", values, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoBetween(String value1, String value2) {
            addCriterion("SampleOrdNo between", value1, value2, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andSampleordnoNotBetween(String value1, String value2) {
            addCriterion("SampleOrdNo not between", value1, value2, "sampleordno");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagIsNull() {
            addCriterion("DlvEntire_Flag is null");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagIsNotNull() {
            addCriterion("DlvEntire_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagEqualTo(String value) {
            addCriterion("DlvEntire_Flag =", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagNotEqualTo(String value) {
            addCriterion("DlvEntire_Flag <>", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagGreaterThan(String value) {
            addCriterion("DlvEntire_Flag >", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DlvEntire_Flag >=", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagLessThan(String value) {
            addCriterion("DlvEntire_Flag <", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagLessThanOrEqualTo(String value) {
            addCriterion("DlvEntire_Flag <=", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagLike(String value) {
            addCriterion("DlvEntire_Flag like", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagNotLike(String value) {
            addCriterion("DlvEntire_Flag not like", value, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagIn(List<String> values) {
            addCriterion("DlvEntire_Flag in", values, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagNotIn(List<String> values) {
            addCriterion("DlvEntire_Flag not in", values, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagBetween(String value1, String value2) {
            addCriterion("DlvEntire_Flag between", value1, value2, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDlventireFlagNotBetween(String value1, String value2) {
            addCriterion("DlvEntire_Flag not between", value1, value2, "dlventireFlag");
            return (Criteria) this;
        }

        public Criteria andDeptlevelIsNull() {
            addCriterion("DeptLevel is null");
            return (Criteria) this;
        }

        public Criteria andDeptlevelIsNotNull() {
            addCriterion("DeptLevel is not null");
            return (Criteria) this;
        }

        public Criteria andDeptlevelEqualTo(String value) {
            addCriterion("DeptLevel =", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelNotEqualTo(String value) {
            addCriterion("DeptLevel <>", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelGreaterThan(String value) {
            addCriterion("DeptLevel >", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelGreaterThanOrEqualTo(String value) {
            addCriterion("DeptLevel >=", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelLessThan(String value) {
            addCriterion("DeptLevel <", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelLessThanOrEqualTo(String value) {
            addCriterion("DeptLevel <=", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelLike(String value) {
            addCriterion("DeptLevel like", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelNotLike(String value) {
            addCriterion("DeptLevel not like", value, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelIn(List<String> values) {
            addCriterion("DeptLevel in", values, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelNotIn(List<String> values) {
            addCriterion("DeptLevel not in", values, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelBetween(String value1, String value2) {
            addCriterion("DeptLevel between", value1, value2, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andDeptlevelNotBetween(String value1, String value2) {
            addCriterion("DeptLevel not between", value1, value2, "deptlevel");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNull() {
            addCriterion("EmpOrdProcess is null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNotNull() {
            addCriterion("EmpOrdProcess is not null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessEqualTo(String value) {
            addCriterion("EmpOrdProcess =", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotEqualTo(String value) {
            addCriterion("EmpOrdProcess <>", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThan(String value) {
            addCriterion("EmpOrdProcess >", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThanOrEqualTo(String value) {
            addCriterion("EmpOrdProcess >=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThan(String value) {
            addCriterion("EmpOrdProcess <", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThanOrEqualTo(String value) {
            addCriterion("EmpOrdProcess <=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLike(String value) {
            addCriterion("EmpOrdProcess like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotLike(String value) {
            addCriterion("EmpOrdProcess not like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIn(List<String> values) {
            addCriterion("EmpOrdProcess in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotIn(List<String> values) {
            addCriterion("EmpOrdProcess not in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessBetween(String value1, String value2) {
            addCriterion("EmpOrdProcess between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotBetween(String value1, String value2) {
            addCriterion("EmpOrdProcess not between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNull() {
            addCriterion("StateCode is null");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNotNull() {
            addCriterion("StateCode is not null");
            return (Criteria) this;
        }

        public Criteria andStatecodeEqualTo(String value) {
            addCriterion("StateCode =", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotEqualTo(String value) {
            addCriterion("StateCode <>", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThan(String value) {
            addCriterion("StateCode >", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("StateCode >=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThan(String value) {
            addCriterion("StateCode <", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThanOrEqualTo(String value) {
            addCriterion("StateCode <=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLike(String value) {
            addCriterion("StateCode like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotLike(String value) {
            addCriterion("StateCode not like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeIn(List<String> values) {
            addCriterion("StateCode in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotIn(List<String> values) {
            addCriterion("StateCode not in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeBetween(String value1, String value2) {
            addCriterion("StateCode between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotBetween(String value1, String value2) {
            addCriterion("StateCode not between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andEmailSampleIsNull() {
            addCriterion("Email_Sample is null");
            return (Criteria) this;
        }

        public Criteria andEmailSampleIsNotNull() {
            addCriterion("Email_Sample is not null");
            return (Criteria) this;
        }

        public Criteria andEmailSampleEqualTo(String value) {
            addCriterion("Email_Sample =", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleNotEqualTo(String value) {
            addCriterion("Email_Sample <>", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleGreaterThan(String value) {
            addCriterion("Email_Sample >", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleGreaterThanOrEqualTo(String value) {
            addCriterion("Email_Sample >=", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleLessThan(String value) {
            addCriterion("Email_Sample <", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleLessThanOrEqualTo(String value) {
            addCriterion("Email_Sample <=", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleLike(String value) {
            addCriterion("Email_Sample like", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleNotLike(String value) {
            addCriterion("Email_Sample not like", value, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleIn(List<String> values) {
            addCriterion("Email_Sample in", values, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleNotIn(List<String> values) {
            addCriterion("Email_Sample not in", values, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleBetween(String value1, String value2) {
            addCriterion("Email_Sample between", value1, value2, "emailSample");
            return (Criteria) this;
        }

        public Criteria andEmailSampleNotBetween(String value1, String value2) {
            addCriterion("Email_Sample not between", value1, value2, "emailSample");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNull() {
            addCriterion("AreaCode is null");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNotNull() {
            addCriterion("AreaCode is not null");
            return (Criteria) this;
        }

        public Criteria andAreacodeEqualTo(String value) {
            addCriterion("AreaCode =", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotEqualTo(String value) {
            addCriterion("AreaCode <>", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThan(String value) {
            addCriterion("AreaCode >", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThanOrEqualTo(String value) {
            addCriterion("AreaCode >=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThan(String value) {
            addCriterion("AreaCode <", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThanOrEqualTo(String value) {
            addCriterion("AreaCode <=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLike(String value) {
            addCriterion("AreaCode like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotLike(String value) {
            addCriterion("AreaCode not like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeIn(List<String> values) {
            addCriterion("AreaCode in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotIn(List<String> values) {
            addCriterion("AreaCode not in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeBetween(String value1, String value2) {
            addCriterion("AreaCode between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotBetween(String value1, String value2) {
            addCriterion("AreaCode not between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoIsNull() {
            addCriterion("SupDeptNo is null");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoIsNotNull() {
            addCriterion("SupDeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoEqualTo(String value) {
            addCriterion("SupDeptNo =", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoNotEqualTo(String value) {
            addCriterion("SupDeptNo <>", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoGreaterThan(String value) {
            addCriterion("SupDeptNo >", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("SupDeptNo >=", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoLessThan(String value) {
            addCriterion("SupDeptNo <", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoLessThanOrEqualTo(String value) {
            addCriterion("SupDeptNo <=", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoLike(String value) {
            addCriterion("SupDeptNo like", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoNotLike(String value) {
            addCriterion("SupDeptNo not like", value, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoIn(List<String> values) {
            addCriterion("SupDeptNo in", values, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoNotIn(List<String> values) {
            addCriterion("SupDeptNo not in", values, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoBetween(String value1, String value2) {
            addCriterion("SupDeptNo between", value1, value2, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andSupdeptnoNotBetween(String value1, String value2) {
            addCriterion("SupDeptNo not between", value1, value2, "supdeptno");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerIsNull() {
            addCriterion("QPrice_Answer is null");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerIsNotNull() {
            addCriterion("QPrice_Answer is not null");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerEqualTo(String value) {
            addCriterion("QPrice_Answer =", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerNotEqualTo(String value) {
            addCriterion("QPrice_Answer <>", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerGreaterThan(String value) {
            addCriterion("QPrice_Answer >", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("QPrice_Answer >=", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerLessThan(String value) {
            addCriterion("QPrice_Answer <", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerLessThanOrEqualTo(String value) {
            addCriterion("QPrice_Answer <=", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerLike(String value) {
            addCriterion("QPrice_Answer like", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerNotLike(String value) {
            addCriterion("QPrice_Answer not like", value, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerIn(List<String> values) {
            addCriterion("QPrice_Answer in", values, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerNotIn(List<String> values) {
            addCriterion("QPrice_Answer not in", values, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerBetween(String value1, String value2) {
            addCriterion("QPrice_Answer between", value1, value2, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andQpriceAnswerNotBetween(String value1, String value2) {
            addCriterion("QPrice_Answer not between", value1, value2, "qpriceAnswer");
            return (Criteria) this;
        }

        public Criteria andDepartcodeIsNull() {
            addCriterion("DepartCode is null");
            return (Criteria) this;
        }

        public Criteria andDepartcodeIsNotNull() {
            addCriterion("DepartCode is not null");
            return (Criteria) this;
        }

        public Criteria andDepartcodeEqualTo(String value) {
            addCriterion("DepartCode =", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeNotEqualTo(String value) {
            addCriterion("DepartCode <>", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeGreaterThan(String value) {
            addCriterion("DepartCode >", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeGreaterThanOrEqualTo(String value) {
            addCriterion("DepartCode >=", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeLessThan(String value) {
            addCriterion("DepartCode <", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeLessThanOrEqualTo(String value) {
            addCriterion("DepartCode <=", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeLike(String value) {
            addCriterion("DepartCode like", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeNotLike(String value) {
            addCriterion("DepartCode not like", value, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeIn(List<String> values) {
            addCriterion("DepartCode in", values, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeNotIn(List<String> values) {
            addCriterion("DepartCode not in", values, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeBetween(String value1, String value2) {
            addCriterion("DepartCode between", value1, value2, "departcode");
            return (Criteria) this;
        }

        public Criteria andDepartcodeNotBetween(String value1, String value2) {
            addCriterion("DepartCode not between", value1, value2, "departcode");
            return (Criteria) this;
        }

        public Criteria andCustAnswerIsNull() {
            addCriterion("Cust_Answer is null");
            return (Criteria) this;
        }

        public Criteria andCustAnswerIsNotNull() {
            addCriterion("Cust_Answer is not null");
            return (Criteria) this;
        }

        public Criteria andCustAnswerEqualTo(String value) {
            addCriterion("Cust_Answer =", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerNotEqualTo(String value) {
            addCriterion("Cust_Answer <>", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerGreaterThan(String value) {
            addCriterion("Cust_Answer >", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("Cust_Answer >=", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerLessThan(String value) {
            addCriterion("Cust_Answer <", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerLessThanOrEqualTo(String value) {
            addCriterion("Cust_Answer <=", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerLike(String value) {
            addCriterion("Cust_Answer like", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerNotLike(String value) {
            addCriterion("Cust_Answer not like", value, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerIn(List<String> values) {
            addCriterion("Cust_Answer in", values, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerNotIn(List<String> values) {
            addCriterion("Cust_Answer not in", values, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerBetween(String value1, String value2) {
            addCriterion("Cust_Answer between", value1, value2, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andCustAnswerNotBetween(String value1, String value2) {
            addCriterion("Cust_Answer not between", value1, value2, "custAnswer");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNull() {
            addCriterion("OrganizationId is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNotNull() {
            addCriterion("OrganizationId is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidEqualTo(String value) {
            addCriterion("OrganizationId =", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotEqualTo(String value) {
            addCriterion("OrganizationId <>", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThan(String value) {
            addCriterion("OrganizationId >", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThanOrEqualTo(String value) {
            addCriterion("OrganizationId >=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThan(String value) {
            addCriterion("OrganizationId <", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThanOrEqualTo(String value) {
            addCriterion("OrganizationId <=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLike(String value) {
            addCriterion("OrganizationId like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotLike(String value) {
            addCriterion("OrganizationId not like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIn(List<String> values) {
            addCriterion("OrganizationId in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotIn(List<String> values) {
            addCriterion("OrganizationId not in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidBetween(String value1, String value2) {
            addCriterion("OrganizationId between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotBetween(String value1, String value2) {
            addCriterion("OrganizationId not between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andIntegratespsIsNull() {
            addCriterion("IntegrateSPS is null");
            return (Criteria) this;
        }

        public Criteria andIntegratespsIsNotNull() {
            addCriterion("IntegrateSPS is not null");
            return (Criteria) this;
        }

        public Criteria andIntegratespsEqualTo(Boolean value) {
            addCriterion("IntegrateSPS =", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsNotEqualTo(Boolean value) {
            addCriterion("IntegrateSPS <>", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsGreaterThan(Boolean value) {
            addCriterion("IntegrateSPS >", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IntegrateSPS >=", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsLessThan(Boolean value) {
            addCriterion("IntegrateSPS <", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsLessThanOrEqualTo(Boolean value) {
            addCriterion("IntegrateSPS <=", value, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsIn(List<Boolean> values) {
            addCriterion("IntegrateSPS in", values, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsNotIn(List<Boolean> values) {
            addCriterion("IntegrateSPS not in", values, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsBetween(Boolean value1, Boolean value2) {
            addCriterion("IntegrateSPS between", value1, value2, "integratesps");
            return (Criteria) this;
        }

        public Criteria andIntegratespsNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IntegrateSPS not between", value1, value2, "integratesps");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagIsNull() {
            addCriterion("AdministrativeStateFlag is null");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagIsNotNull() {
            addCriterion("AdministrativeStateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagEqualTo(String value) {
            addCriterion("AdministrativeStateFlag =", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagNotEqualTo(String value) {
            addCriterion("AdministrativeStateFlag <>", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagGreaterThan(String value) {
            addCriterion("AdministrativeStateFlag >", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagGreaterThanOrEqualTo(String value) {
            addCriterion("AdministrativeStateFlag >=", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagLessThan(String value) {
            addCriterion("AdministrativeStateFlag <", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagLessThanOrEqualTo(String value) {
            addCriterion("AdministrativeStateFlag <=", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagLike(String value) {
            addCriterion("AdministrativeStateFlag like", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagNotLike(String value) {
            addCriterion("AdministrativeStateFlag not like", value, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagIn(List<String> values) {
            addCriterion("AdministrativeStateFlag in", values, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagNotIn(List<String> values) {
            addCriterion("AdministrativeStateFlag not in", values, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagBetween(String value1, String value2) {
            addCriterion("AdministrativeStateFlag between", value1, value2, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andAdministrativestateflagNotBetween(String value1, String value2) {
            addCriterion("AdministrativeStateFlag not between", value1, value2, "administrativestateflag");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNull() {
            addCriterion("TradeCompanyId is null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIsNotNull() {
            addCriterion("TradeCompanyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidEqualTo(String value) {
            addCriterion("TradeCompanyId =", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotEqualTo(String value) {
            addCriterion("TradeCompanyId <>", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThan(String value) {
            addCriterion("TradeCompanyId >", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId >=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThan(String value) {
            addCriterion("TradeCompanyId <", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLessThanOrEqualTo(String value) {
            addCriterion("TradeCompanyId <=", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidLike(String value) {
            addCriterion("TradeCompanyId like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotLike(String value) {
            addCriterion("TradeCompanyId not like", value, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidIn(List<String> values) {
            addCriterion("TradeCompanyId in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotIn(List<String> values) {
            addCriterion("TradeCompanyId not in", values, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidBetween(String value1, String value2) {
            addCriterion("TradeCompanyId between", value1, value2, "tradecompanyid");
            return (Criteria) this;
        }

        public Criteria andTradecompanyidNotBetween(String value1, String value2) {
            addCriterion("TradeCompanyId not between", value1, value2, "tradecompanyid");
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
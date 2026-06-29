package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerGzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomerGzExample() {
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

        public Criteria andUsernoIsNull() {
            addCriterion("UserNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("UserNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("UserNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("UserNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("UserNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("UserNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("UserNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("UserNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("UserNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("UserNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("UserNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("UserNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("UserNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("UserNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNull() {
            addCriterion("CstmName is null");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNotNull() {
            addCriterion("CstmName is not null");
            return (Criteria) this;
        }

        public Criteria andCstmnameEqualTo(String value) {
            addCriterion("CstmName =", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotEqualTo(String value) {
            addCriterion("CstmName <>", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThan(String value) {
            addCriterion("CstmName >", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThanOrEqualTo(String value) {
            addCriterion("CstmName >=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThan(String value) {
            addCriterion("CstmName <", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThanOrEqualTo(String value) {
            addCriterion("CstmName <=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLike(String value) {
            addCriterion("CstmName like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotLike(String value) {
            addCriterion("CstmName not like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameIn(List<String> values) {
            addCriterion("CstmName in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotIn(List<String> values) {
            addCriterion("CstmName not in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameBetween(String value1, String value2) {
            addCriterion("CstmName between", value1, value2, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotBetween(String value1, String value2) {
            addCriterion("CstmName not between", value1, value2, "cstmname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIsNull() {
            addCriterion("EnglishName is null");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIsNotNull() {
            addCriterion("EnglishName is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishnameEqualTo(String value) {
            addCriterion("EnglishName =", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotEqualTo(String value) {
            addCriterion("EnglishName <>", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameGreaterThan(String value) {
            addCriterion("EnglishName >", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameGreaterThanOrEqualTo(String value) {
            addCriterion("EnglishName >=", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLessThan(String value) {
            addCriterion("EnglishName <", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLessThanOrEqualTo(String value) {
            addCriterion("EnglishName <=", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLike(String value) {
            addCriterion("EnglishName like", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotLike(String value) {
            addCriterion("EnglishName not like", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIn(List<String> values) {
            addCriterion("EnglishName in", values, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotIn(List<String> values) {
            addCriterion("EnglishName not in", values, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameBetween(String value1, String value2) {
            addCriterion("EnglishName between", value1, value2, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotBetween(String value1, String value2) {
            addCriterion("EnglishName not between", value1, value2, "englishname");
            return (Criteria) this;
        }

        public Criteria andCstmtypeIsNull() {
            addCriterion("CstmType is null");
            return (Criteria) this;
        }

        public Criteria andCstmtypeIsNotNull() {
            addCriterion("CstmType is not null");
            return (Criteria) this;
        }

        public Criteria andCstmtypeEqualTo(String value) {
            addCriterion("CstmType =", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeNotEqualTo(String value) {
            addCriterion("CstmType <>", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeGreaterThan(String value) {
            addCriterion("CstmType >", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeGreaterThanOrEqualTo(String value) {
            addCriterion("CstmType >=", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeLessThan(String value) {
            addCriterion("CstmType <", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeLessThanOrEqualTo(String value) {
            addCriterion("CstmType <=", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeLike(String value) {
            addCriterion("CstmType like", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeNotLike(String value) {
            addCriterion("CstmType not like", value, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeIn(List<String> values) {
            addCriterion("CstmType in", values, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeNotIn(List<String> values) {
            addCriterion("CstmType not in", values, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeBetween(String value1, String value2) {
            addCriterion("CstmType between", value1, value2, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmtypeNotBetween(String value1, String value2) {
            addCriterion("CstmType not between", value1, value2, "cstmtype");
            return (Criteria) this;
        }

        public Criteria andCstmgradeIsNull() {
            addCriterion("CstmGrade is null");
            return (Criteria) this;
        }

        public Criteria andCstmgradeIsNotNull() {
            addCriterion("CstmGrade is not null");
            return (Criteria) this;
        }

        public Criteria andCstmgradeEqualTo(String value) {
            addCriterion("CstmGrade =", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeNotEqualTo(String value) {
            addCriterion("CstmGrade <>", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeGreaterThan(String value) {
            addCriterion("CstmGrade >", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeGreaterThanOrEqualTo(String value) {
            addCriterion("CstmGrade >=", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeLessThan(String value) {
            addCriterion("CstmGrade <", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeLessThanOrEqualTo(String value) {
            addCriterion("CstmGrade <=", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeLike(String value) {
            addCriterion("CstmGrade like", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeNotLike(String value) {
            addCriterion("CstmGrade not like", value, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeIn(List<String> values) {
            addCriterion("CstmGrade in", values, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeNotIn(List<String> values) {
            addCriterion("CstmGrade not in", values, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeBetween(String value1, String value2) {
            addCriterion("CstmGrade between", value1, value2, "cstmgrade");
            return (Criteria) this;
        }

        public Criteria andCstmgradeNotBetween(String value1, String value2) {
            addCriterion("CstmGrade not between", value1, value2, "cstmgrade");
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

        public Criteria andPrjcodeIsNull() {
            addCriterion("PrjCode is null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIsNotNull() {
            addCriterion("PrjCode is not null");
            return (Criteria) this;
        }

        public Criteria andPrjcodeEqualTo(String value) {
            addCriterion("PrjCode =", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotEqualTo(String value) {
            addCriterion("PrjCode <>", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThan(String value) {
            addCriterion("PrjCode >", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PrjCode >=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThan(String value) {
            addCriterion("PrjCode <", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLessThanOrEqualTo(String value) {
            addCriterion("PrjCode <=", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeLike(String value) {
            addCriterion("PrjCode like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotLike(String value) {
            addCriterion("PrjCode not like", value, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeIn(List<String> values) {
            addCriterion("PrjCode in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotIn(List<String> values) {
            addCriterion("PrjCode not in", values, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeBetween(String value1, String value2) {
            addCriterion("PrjCode between", value1, value2, "prjcode");
            return (Criteria) this;
        }

        public Criteria andPrjcodeNotBetween(String value1, String value2) {
            addCriterion("PrjCode not between", value1, value2, "prjcode");
            return (Criteria) this;
        }

        public Criteria andEtpofjpIsNull() {
            addCriterion("EtpOfJp is null");
            return (Criteria) this;
        }

        public Criteria andEtpofjpIsNotNull() {
            addCriterion("EtpOfJp is not null");
            return (Criteria) this;
        }

        public Criteria andEtpofjpEqualTo(String value) {
            addCriterion("EtpOfJp =", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpNotEqualTo(String value) {
            addCriterion("EtpOfJp <>", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpGreaterThan(String value) {
            addCriterion("EtpOfJp >", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpGreaterThanOrEqualTo(String value) {
            addCriterion("EtpOfJp >=", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpLessThan(String value) {
            addCriterion("EtpOfJp <", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpLessThanOrEqualTo(String value) {
            addCriterion("EtpOfJp <=", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpLike(String value) {
            addCriterion("EtpOfJp like", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpNotLike(String value) {
            addCriterion("EtpOfJp not like", value, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpIn(List<String> values) {
            addCriterion("EtpOfJp in", values, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpNotIn(List<String> values) {
            addCriterion("EtpOfJp not in", values, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpBetween(String value1, String value2) {
            addCriterion("EtpOfJp between", value1, value2, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andEtpofjpNotBetween(String value1, String value2) {
            addCriterion("EtpOfJp not between", value1, value2, "etpofjp");
            return (Criteria) this;
        }

        public Criteria andLgealrepIsNull() {
            addCriterion("LgealRep is null");
            return (Criteria) this;
        }

        public Criteria andLgealrepIsNotNull() {
            addCriterion("LgealRep is not null");
            return (Criteria) this;
        }

        public Criteria andLgealrepEqualTo(String value) {
            addCriterion("LgealRep =", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepNotEqualTo(String value) {
            addCriterion("LgealRep <>", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepGreaterThan(String value) {
            addCriterion("LgealRep >", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepGreaterThanOrEqualTo(String value) {
            addCriterion("LgealRep >=", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepLessThan(String value) {
            addCriterion("LgealRep <", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepLessThanOrEqualTo(String value) {
            addCriterion("LgealRep <=", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepLike(String value) {
            addCriterion("LgealRep like", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepNotLike(String value) {
            addCriterion("LgealRep not like", value, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepIn(List<String> values) {
            addCriterion("LgealRep in", values, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepNotIn(List<String> values) {
            addCriterion("LgealRep not in", values, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepBetween(String value1, String value2) {
            addCriterion("LgealRep between", value1, value2, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andLgealrepNotBetween(String value1, String value2) {
            addCriterion("LgealRep not between", value1, value2, "lgealrep");
            return (Criteria) this;
        }

        public Criteria andCaddress1IsNull() {
            addCriterion("CAddress1 is null");
            return (Criteria) this;
        }

        public Criteria andCaddress1IsNotNull() {
            addCriterion("CAddress1 is not null");
            return (Criteria) this;
        }

        public Criteria andCaddress1EqualTo(String value) {
            addCriterion("CAddress1 =", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1NotEqualTo(String value) {
            addCriterion("CAddress1 <>", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1GreaterThan(String value) {
            addCriterion("CAddress1 >", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1GreaterThanOrEqualTo(String value) {
            addCriterion("CAddress1 >=", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1LessThan(String value) {
            addCriterion("CAddress1 <", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1LessThanOrEqualTo(String value) {
            addCriterion("CAddress1 <=", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1Like(String value) {
            addCriterion("CAddress1 like", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1NotLike(String value) {
            addCriterion("CAddress1 not like", value, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1In(List<String> values) {
            addCriterion("CAddress1 in", values, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1NotIn(List<String> values) {
            addCriterion("CAddress1 not in", values, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1Between(String value1, String value2) {
            addCriterion("CAddress1 between", value1, value2, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress1NotBetween(String value1, String value2) {
            addCriterion("CAddress1 not between", value1, value2, "caddress1");
            return (Criteria) this;
        }

        public Criteria andCaddress2IsNull() {
            addCriterion("CAddress2 is null");
            return (Criteria) this;
        }

        public Criteria andCaddress2IsNotNull() {
            addCriterion("CAddress2 is not null");
            return (Criteria) this;
        }

        public Criteria andCaddress2EqualTo(String value) {
            addCriterion("CAddress2 =", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2NotEqualTo(String value) {
            addCriterion("CAddress2 <>", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2GreaterThan(String value) {
            addCriterion("CAddress2 >", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2GreaterThanOrEqualTo(String value) {
            addCriterion("CAddress2 >=", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2LessThan(String value) {
            addCriterion("CAddress2 <", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2LessThanOrEqualTo(String value) {
            addCriterion("CAddress2 <=", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2Like(String value) {
            addCriterion("CAddress2 like", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2NotLike(String value) {
            addCriterion("CAddress2 not like", value, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2In(List<String> values) {
            addCriterion("CAddress2 in", values, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2NotIn(List<String> values) {
            addCriterion("CAddress2 not in", values, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2Between(String value1, String value2) {
            addCriterion("CAddress2 between", value1, value2, "caddress2");
            return (Criteria) this;
        }

        public Criteria andCaddress2NotBetween(String value1, String value2) {
            addCriterion("CAddress2 not between", value1, value2, "caddress2");
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

        public Criteria andTelnofirstIsNull() {
            addCriterion("TelNoFirst is null");
            return (Criteria) this;
        }

        public Criteria andTelnofirstIsNotNull() {
            addCriterion("TelNoFirst is not null");
            return (Criteria) this;
        }

        public Criteria andTelnofirstEqualTo(String value) {
            addCriterion("TelNoFirst =", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstNotEqualTo(String value) {
            addCriterion("TelNoFirst <>", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstGreaterThan(String value) {
            addCriterion("TelNoFirst >", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstGreaterThanOrEqualTo(String value) {
            addCriterion("TelNoFirst >=", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstLessThan(String value) {
            addCriterion("TelNoFirst <", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstLessThanOrEqualTo(String value) {
            addCriterion("TelNoFirst <=", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstLike(String value) {
            addCriterion("TelNoFirst like", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstNotLike(String value) {
            addCriterion("TelNoFirst not like", value, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstIn(List<String> values) {
            addCriterion("TelNoFirst in", values, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstNotIn(List<String> values) {
            addCriterion("TelNoFirst not in", values, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstBetween(String value1, String value2) {
            addCriterion("TelNoFirst between", value1, value2, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnofirstNotBetween(String value1, String value2) {
            addCriterion("TelNoFirst not between", value1, value2, "telnofirst");
            return (Criteria) this;
        }

        public Criteria andTelnolastIsNull() {
            addCriterion("TelNoLast is null");
            return (Criteria) this;
        }

        public Criteria andTelnolastIsNotNull() {
            addCriterion("TelNoLast is not null");
            return (Criteria) this;
        }

        public Criteria andTelnolastEqualTo(String value) {
            addCriterion("TelNoLast =", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastNotEqualTo(String value) {
            addCriterion("TelNoLast <>", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastGreaterThan(String value) {
            addCriterion("TelNoLast >", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastGreaterThanOrEqualTo(String value) {
            addCriterion("TelNoLast >=", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastLessThan(String value) {
            addCriterion("TelNoLast <", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastLessThanOrEqualTo(String value) {
            addCriterion("TelNoLast <=", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastLike(String value) {
            addCriterion("TelNoLast like", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastNotLike(String value) {
            addCriterion("TelNoLast not like", value, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastIn(List<String> values) {
            addCriterion("TelNoLast in", values, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastNotIn(List<String> values) {
            addCriterion("TelNoLast not in", values, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastBetween(String value1, String value2) {
            addCriterion("TelNoLast between", value1, value2, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelnolastNotBetween(String value1, String value2) {
            addCriterion("TelNoLast not between", value1, value2, "telnolast");
            return (Criteria) this;
        }

        public Criteria andTelotherIsNull() {
            addCriterion("TelOther is null");
            return (Criteria) this;
        }

        public Criteria andTelotherIsNotNull() {
            addCriterion("TelOther is not null");
            return (Criteria) this;
        }

        public Criteria andTelotherEqualTo(String value) {
            addCriterion("TelOther =", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherNotEqualTo(String value) {
            addCriterion("TelOther <>", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherGreaterThan(String value) {
            addCriterion("TelOther >", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherGreaterThanOrEqualTo(String value) {
            addCriterion("TelOther >=", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherLessThan(String value) {
            addCriterion("TelOther <", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherLessThanOrEqualTo(String value) {
            addCriterion("TelOther <=", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherLike(String value) {
            addCriterion("TelOther like", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherNotLike(String value) {
            addCriterion("TelOther not like", value, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherIn(List<String> values) {
            addCriterion("TelOther in", values, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherNotIn(List<String> values) {
            addCriterion("TelOther not in", values, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherBetween(String value1, String value2) {
            addCriterion("TelOther between", value1, value2, "telother");
            return (Criteria) this;
        }

        public Criteria andTelotherNotBetween(String value1, String value2) {
            addCriterion("TelOther not between", value1, value2, "telother");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstIsNull() {
            addCriterion("FaxNoFirst is null");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstIsNotNull() {
            addCriterion("FaxNoFirst is not null");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstEqualTo(String value) {
            addCriterion("FaxNoFirst =", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstNotEqualTo(String value) {
            addCriterion("FaxNoFirst <>", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstGreaterThan(String value) {
            addCriterion("FaxNoFirst >", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstGreaterThanOrEqualTo(String value) {
            addCriterion("FaxNoFirst >=", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstLessThan(String value) {
            addCriterion("FaxNoFirst <", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstLessThanOrEqualTo(String value) {
            addCriterion("FaxNoFirst <=", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstLike(String value) {
            addCriterion("FaxNoFirst like", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstNotLike(String value) {
            addCriterion("FaxNoFirst not like", value, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstIn(List<String> values) {
            addCriterion("FaxNoFirst in", values, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstNotIn(List<String> values) {
            addCriterion("FaxNoFirst not in", values, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstBetween(String value1, String value2) {
            addCriterion("FaxNoFirst between", value1, value2, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnofirstNotBetween(String value1, String value2) {
            addCriterion("FaxNoFirst not between", value1, value2, "faxnofirst");
            return (Criteria) this;
        }

        public Criteria andFaxnolastIsNull() {
            addCriterion("FaxNoLast is null");
            return (Criteria) this;
        }

        public Criteria andFaxnolastIsNotNull() {
            addCriterion("FaxNoLast is not null");
            return (Criteria) this;
        }

        public Criteria andFaxnolastEqualTo(String value) {
            addCriterion("FaxNoLast =", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastNotEqualTo(String value) {
            addCriterion("FaxNoLast <>", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastGreaterThan(String value) {
            addCriterion("FaxNoLast >", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastGreaterThanOrEqualTo(String value) {
            addCriterion("FaxNoLast >=", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastLessThan(String value) {
            addCriterion("FaxNoLast <", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastLessThanOrEqualTo(String value) {
            addCriterion("FaxNoLast <=", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastLike(String value) {
            addCriterion("FaxNoLast like", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastNotLike(String value) {
            addCriterion("FaxNoLast not like", value, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastIn(List<String> values) {
            addCriterion("FaxNoLast in", values, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastNotIn(List<String> values) {
            addCriterion("FaxNoLast not in", values, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastBetween(String value1, String value2) {
            addCriterion("FaxNoLast between", value1, value2, "faxnolast");
            return (Criteria) this;
        }

        public Criteria andFaxnolastNotBetween(String value1, String value2) {
            addCriterion("FaxNoLast not between", value1, value2, "faxnolast");
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

        public Criteria andContpsndutyIsNull() {
            addCriterion("ContPsnDuty is null");
            return (Criteria) this;
        }

        public Criteria andContpsndutyIsNotNull() {
            addCriterion("ContPsnDuty is not null");
            return (Criteria) this;
        }

        public Criteria andContpsndutyEqualTo(String value) {
            addCriterion("ContPsnDuty =", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyNotEqualTo(String value) {
            addCriterion("ContPsnDuty <>", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyGreaterThan(String value) {
            addCriterion("ContPsnDuty >", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyGreaterThanOrEqualTo(String value) {
            addCriterion("ContPsnDuty >=", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyLessThan(String value) {
            addCriterion("ContPsnDuty <", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyLessThanOrEqualTo(String value) {
            addCriterion("ContPsnDuty <=", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyLike(String value) {
            addCriterion("ContPsnDuty like", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyNotLike(String value) {
            addCriterion("ContPsnDuty not like", value, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyIn(List<String> values) {
            addCriterion("ContPsnDuty in", values, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyNotIn(List<String> values) {
            addCriterion("ContPsnDuty not in", values, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyBetween(String value1, String value2) {
            addCriterion("ContPsnDuty between", value1, value2, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContpsndutyNotBetween(String value1, String value2) {
            addCriterion("ContPsnDuty not between", value1, value2, "contpsnduty");
            return (Criteria) this;
        }

        public Criteria andContdeptIsNull() {
            addCriterion("ContDept is null");
            return (Criteria) this;
        }

        public Criteria andContdeptIsNotNull() {
            addCriterion("ContDept is not null");
            return (Criteria) this;
        }

        public Criteria andContdeptEqualTo(String value) {
            addCriterion("ContDept =", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptNotEqualTo(String value) {
            addCriterion("ContDept <>", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptGreaterThan(String value) {
            addCriterion("ContDept >", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptGreaterThanOrEqualTo(String value) {
            addCriterion("ContDept >=", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptLessThan(String value) {
            addCriterion("ContDept <", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptLessThanOrEqualTo(String value) {
            addCriterion("ContDept <=", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptLike(String value) {
            addCriterion("ContDept like", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptNotLike(String value) {
            addCriterion("ContDept not like", value, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptIn(List<String> values) {
            addCriterion("ContDept in", values, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptNotIn(List<String> values) {
            addCriterion("ContDept not in", values, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptBetween(String value1, String value2) {
            addCriterion("ContDept between", value1, value2, "contdept");
            return (Criteria) this;
        }

        public Criteria andContdeptNotBetween(String value1, String value2) {
            addCriterion("ContDept not between", value1, value2, "contdept");
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

        public Criteria andDeptcodeIsNull() {
            addCriterion("DeptCode is null");
            return (Criteria) this;
        }

        public Criteria andDeptcodeIsNotNull() {
            addCriterion("DeptCode is not null");
            return (Criteria) this;
        }

        public Criteria andDeptcodeEqualTo(String value) {
            addCriterion("DeptCode =", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotEqualTo(String value) {
            addCriterion("DeptCode <>", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeGreaterThan(String value) {
            addCriterion("DeptCode >", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("DeptCode >=", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLessThan(String value) {
            addCriterion("DeptCode <", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLessThanOrEqualTo(String value) {
            addCriterion("DeptCode <=", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLike(String value) {
            addCriterion("DeptCode like", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotLike(String value) {
            addCriterion("DeptCode not like", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeIn(List<String> values) {
            addCriterion("DeptCode in", values, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotIn(List<String> values) {
            addCriterion("DeptCode not in", values, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeBetween(String value1, String value2) {
            addCriterion("DeptCode between", value1, value2, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotBetween(String value1, String value2) {
            addCriterion("DeptCode not between", value1, value2, "deptcode");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIsNull() {
            addCriterion("PsnSMCID is null");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIsNotNull() {
            addCriterion("PsnSMCID is not null");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidEqualTo(String value) {
            addCriterion("PsnSMCID =", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotEqualTo(String value) {
            addCriterion("PsnSMCID <>", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidGreaterThan(String value) {
            addCriterion("PsnSMCID >", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidGreaterThanOrEqualTo(String value) {
            addCriterion("PsnSMCID >=", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLessThan(String value) {
            addCriterion("PsnSMCID <", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLessThanOrEqualTo(String value) {
            addCriterion("PsnSMCID <=", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLike(String value) {
            addCriterion("PsnSMCID like", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotLike(String value) {
            addCriterion("PsnSMCID not like", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIn(List<String> values) {
            addCriterion("PsnSMCID in", values, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotIn(List<String> values) {
            addCriterion("PsnSMCID not in", values, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidBetween(String value1, String value2) {
            addCriterion("PsnSMCID between", value1, value2, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotBetween(String value1, String value2) {
            addCriterion("PsnSMCID not between", value1, value2, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPricelvlIsNull() {
            addCriterion("PriceLvl is null");
            return (Criteria) this;
        }

        public Criteria andPricelvlIsNotNull() {
            addCriterion("PriceLvl is not null");
            return (Criteria) this;
        }

        public Criteria andPricelvlEqualTo(String value) {
            addCriterion("PriceLvl =", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlNotEqualTo(String value) {
            addCriterion("PriceLvl <>", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlGreaterThan(String value) {
            addCriterion("PriceLvl >", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlGreaterThanOrEqualTo(String value) {
            addCriterion("PriceLvl >=", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlLessThan(String value) {
            addCriterion("PriceLvl <", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlLessThanOrEqualTo(String value) {
            addCriterion("PriceLvl <=", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlLike(String value) {
            addCriterion("PriceLvl like", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlNotLike(String value) {
            addCriterion("PriceLvl not like", value, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlIn(List<String> values) {
            addCriterion("PriceLvl in", values, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlNotIn(List<String> values) {
            addCriterion("PriceLvl not in", values, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlBetween(String value1, String value2) {
            addCriterion("PriceLvl between", value1, value2, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andPricelvlNotBetween(String value1, String value2) {
            addCriterion("PriceLvl not between", value1, value2, "pricelvl");
            return (Criteria) this;
        }

        public Criteria andFiledateIsNull() {
            addCriterion("FileDate is null");
            return (Criteria) this;
        }

        public Criteria andFiledateIsNotNull() {
            addCriterion("FileDate is not null");
            return (Criteria) this;
        }

        public Criteria andFiledateEqualTo(Date value) {
            addCriterion("FileDate =", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateNotEqualTo(Date value) {
            addCriterion("FileDate <>", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateGreaterThan(Date value) {
            addCriterion("FileDate >", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateGreaterThanOrEqualTo(Date value) {
            addCriterion("FileDate >=", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateLessThan(Date value) {
            addCriterion("FileDate <", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateLessThanOrEqualTo(Date value) {
            addCriterion("FileDate <=", value, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateIn(List<Date> values) {
            addCriterion("FileDate in", values, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateNotIn(List<Date> values) {
            addCriterion("FileDate not in", values, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateBetween(Date value1, Date value2) {
            addCriterion("FileDate between", value1, value2, "filedate");
            return (Criteria) this;
        }

        public Criteria andFiledateNotBetween(Date value1, Date value2) {
            addCriterion("FileDate not between", value1, value2, "filedate");
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

        public Criteria andObinvoiceIsNull() {
            addCriterion("ObInvoice is null");
            return (Criteria) this;
        }

        public Criteria andObinvoiceIsNotNull() {
            addCriterion("ObInvoice is not null");
            return (Criteria) this;
        }

        public Criteria andObinvoiceEqualTo(String value) {
            addCriterion("ObInvoice =", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceNotEqualTo(String value) {
            addCriterion("ObInvoice <>", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceGreaterThan(String value) {
            addCriterion("ObInvoice >", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceGreaterThanOrEqualTo(String value) {
            addCriterion("ObInvoice >=", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceLessThan(String value) {
            addCriterion("ObInvoice <", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceLessThanOrEqualTo(String value) {
            addCriterion("ObInvoice <=", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceLike(String value) {
            addCriterion("ObInvoice like", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceNotLike(String value) {
            addCriterion("ObInvoice not like", value, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceIn(List<String> values) {
            addCriterion("ObInvoice in", values, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceNotIn(List<String> values) {
            addCriterion("ObInvoice not in", values, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceBetween(String value1, String value2) {
            addCriterion("ObInvoice between", value1, value2, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andObinvoiceNotBetween(String value1, String value2) {
            addCriterion("ObInvoice not between", value1, value2, "obinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceIsNull() {
            addCriterion("NoTaxInvoice is null");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceIsNotNull() {
            addCriterion("NoTaxInvoice is not null");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceEqualTo(String value) {
            addCriterion("NoTaxInvoice =", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceNotEqualTo(String value) {
            addCriterion("NoTaxInvoice <>", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceGreaterThan(String value) {
            addCriterion("NoTaxInvoice >", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceGreaterThanOrEqualTo(String value) {
            addCriterion("NoTaxInvoice >=", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceLessThan(String value) {
            addCriterion("NoTaxInvoice <", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceLessThanOrEqualTo(String value) {
            addCriterion("NoTaxInvoice <=", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceLike(String value) {
            addCriterion("NoTaxInvoice like", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceNotLike(String value) {
            addCriterion("NoTaxInvoice not like", value, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceIn(List<String> values) {
            addCriterion("NoTaxInvoice in", values, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceNotIn(List<String> values) {
            addCriterion("NoTaxInvoice not in", values, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceBetween(String value1, String value2) {
            addCriterion("NoTaxInvoice between", value1, value2, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andNotaxinvoiceNotBetween(String value1, String value2) {
            addCriterion("NoTaxInvoice not between", value1, value2, "notaxinvoice");
            return (Criteria) this;
        }

        public Criteria andEaddressIsNull() {
            addCriterion("EAddress is null");
            return (Criteria) this;
        }

        public Criteria andEaddressIsNotNull() {
            addCriterion("EAddress is not null");
            return (Criteria) this;
        }

        public Criteria andEaddressEqualTo(String value) {
            addCriterion("EAddress =", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressNotEqualTo(String value) {
            addCriterion("EAddress <>", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressGreaterThan(String value) {
            addCriterion("EAddress >", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressGreaterThanOrEqualTo(String value) {
            addCriterion("EAddress >=", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressLessThan(String value) {
            addCriterion("EAddress <", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressLessThanOrEqualTo(String value) {
            addCriterion("EAddress <=", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressLike(String value) {
            addCriterion("EAddress like", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressNotLike(String value) {
            addCriterion("EAddress not like", value, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressIn(List<String> values) {
            addCriterion("EAddress in", values, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressNotIn(List<String> values) {
            addCriterion("EAddress not in", values, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressBetween(String value1, String value2) {
            addCriterion("EAddress between", value1, value2, "eaddress");
            return (Criteria) this;
        }

        public Criteria andEaddressNotBetween(String value1, String value2) {
            addCriterion("EAddress not between", value1, value2, "eaddress");
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

        public Criteria andRequirementIsNull() {
            addCriterion("Requirement is null");
            return (Criteria) this;
        }

        public Criteria andRequirementIsNotNull() {
            addCriterion("Requirement is not null");
            return (Criteria) this;
        }

        public Criteria andRequirementEqualTo(BigDecimal value) {
            addCriterion("Requirement =", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementNotEqualTo(BigDecimal value) {
            addCriterion("Requirement <>", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementGreaterThan(BigDecimal value) {
            addCriterion("Requirement >", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Requirement >=", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementLessThan(BigDecimal value) {
            addCriterion("Requirement <", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Requirement <=", value, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementIn(List<BigDecimal> values) {
            addCriterion("Requirement in", values, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementNotIn(List<BigDecimal> values) {
            addCriterion("Requirement not in", values, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Requirement between", value1, value2, "requirement");
            return (Criteria) this;
        }

        public Criteria andRequirementNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Requirement not between", value1, value2, "requirement");
            return (Criteria) this;
        }

        public Criteria andAgentnoIsNull() {
            addCriterion("AgentNo is null");
            return (Criteria) this;
        }

        public Criteria andAgentnoIsNotNull() {
            addCriterion("AgentNo is not null");
            return (Criteria) this;
        }

        public Criteria andAgentnoEqualTo(String value) {
            addCriterion("AgentNo =", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoNotEqualTo(String value) {
            addCriterion("AgentNo <>", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoGreaterThan(String value) {
            addCriterion("AgentNo >", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoGreaterThanOrEqualTo(String value) {
            addCriterion("AgentNo >=", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoLessThan(String value) {
            addCriterion("AgentNo <", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoLessThanOrEqualTo(String value) {
            addCriterion("AgentNo <=", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoLike(String value) {
            addCriterion("AgentNo like", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoNotLike(String value) {
            addCriterion("AgentNo not like", value, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoIn(List<String> values) {
            addCriterion("AgentNo in", values, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoNotIn(List<String> values) {
            addCriterion("AgentNo not in", values, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoBetween(String value1, String value2) {
            addCriterion("AgentNo between", value1, value2, "agentno");
            return (Criteria) this;
        }

        public Criteria andAgentnoNotBetween(String value1, String value2) {
            addCriterion("AgentNo not between", value1, value2, "agentno");
            return (Criteria) this;
        }

        public Criteria andStockflagIsNull() {
            addCriterion("StockFlag is null");
            return (Criteria) this;
        }

        public Criteria andStockflagIsNotNull() {
            addCriterion("StockFlag is not null");
            return (Criteria) this;
        }

        public Criteria andStockflagEqualTo(String value) {
            addCriterion("StockFlag =", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagNotEqualTo(String value) {
            addCriterion("StockFlag <>", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagGreaterThan(String value) {
            addCriterion("StockFlag >", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagGreaterThanOrEqualTo(String value) {
            addCriterion("StockFlag >=", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagLessThan(String value) {
            addCriterion("StockFlag <", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagLessThanOrEqualTo(String value) {
            addCriterion("StockFlag <=", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagLike(String value) {
            addCriterion("StockFlag like", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagNotLike(String value) {
            addCriterion("StockFlag not like", value, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagIn(List<String> values) {
            addCriterion("StockFlag in", values, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagNotIn(List<String> values) {
            addCriterion("StockFlag not in", values, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagBetween(String value1, String value2) {
            addCriterion("StockFlag between", value1, value2, "stockflag");
            return (Criteria) this;
        }

        public Criteria andStockflagNotBetween(String value1, String value2) {
            addCriterion("StockFlag not between", value1, value2, "stockflag");
            return (Criteria) this;
        }

        public Criteria andInvagentIsNull() {
            addCriterion("InvAgent is null");
            return (Criteria) this;
        }

        public Criteria andInvagentIsNotNull() {
            addCriterion("InvAgent is not null");
            return (Criteria) this;
        }

        public Criteria andInvagentEqualTo(String value) {
            addCriterion("InvAgent =", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentNotEqualTo(String value) {
            addCriterion("InvAgent <>", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentGreaterThan(String value) {
            addCriterion("InvAgent >", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentGreaterThanOrEqualTo(String value) {
            addCriterion("InvAgent >=", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentLessThan(String value) {
            addCriterion("InvAgent <", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentLessThanOrEqualTo(String value) {
            addCriterion("InvAgent <=", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentLike(String value) {
            addCriterion("InvAgent like", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentNotLike(String value) {
            addCriterion("InvAgent not like", value, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentIn(List<String> values) {
            addCriterion("InvAgent in", values, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentNotIn(List<String> values) {
            addCriterion("InvAgent not in", values, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentBetween(String value1, String value2) {
            addCriterion("InvAgent between", value1, value2, "invagent");
            return (Criteria) this;
        }

        public Criteria andInvagentNotBetween(String value1, String value2) {
            addCriterion("InvAgent not between", value1, value2, "invagent");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeIsNull() {
            addCriterion("CstmGCode is null");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeIsNotNull() {
            addCriterion("CstmGCode is not null");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeEqualTo(String value) {
            addCriterion("CstmGCode =", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeNotEqualTo(String value) {
            addCriterion("CstmGCode <>", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeGreaterThan(String value) {
            addCriterion("CstmGCode >", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeGreaterThanOrEqualTo(String value) {
            addCriterion("CstmGCode >=", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeLessThan(String value) {
            addCriterion("CstmGCode <", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeLessThanOrEqualTo(String value) {
            addCriterion("CstmGCode <=", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeLike(String value) {
            addCriterion("CstmGCode like", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeNotLike(String value) {
            addCriterion("CstmGCode not like", value, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeIn(List<String> values) {
            addCriterion("CstmGCode in", values, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeNotIn(List<String> values) {
            addCriterion("CstmGCode not in", values, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeBetween(String value1, String value2) {
            addCriterion("CstmGCode between", value1, value2, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andCstmgcodeNotBetween(String value1, String value2) {
            addCriterion("CstmGCode not between", value1, value2, "cstmgcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeIsNull() {
            addCriterion("AreaGCode is null");
            return (Criteria) this;
        }

        public Criteria andAreagcodeIsNotNull() {
            addCriterion("AreaGCode is not null");
            return (Criteria) this;
        }

        public Criteria andAreagcodeEqualTo(String value) {
            addCriterion("AreaGCode =", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeNotEqualTo(String value) {
            addCriterion("AreaGCode <>", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeGreaterThan(String value) {
            addCriterion("AreaGCode >", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeGreaterThanOrEqualTo(String value) {
            addCriterion("AreaGCode >=", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeLessThan(String value) {
            addCriterion("AreaGCode <", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeLessThanOrEqualTo(String value) {
            addCriterion("AreaGCode <=", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeLike(String value) {
            addCriterion("AreaGCode like", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeNotLike(String value) {
            addCriterion("AreaGCode not like", value, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeIn(List<String> values) {
            addCriterion("AreaGCode in", values, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeNotIn(List<String> values) {
            addCriterion("AreaGCode not in", values, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeBetween(String value1, String value2) {
            addCriterion("AreaGCode between", value1, value2, "areagcode");
            return (Criteria) this;
        }

        public Criteria andAreagcodeNotBetween(String value1, String value2) {
            addCriterion("AreaGCode not between", value1, value2, "areagcode");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIsNull() {
            addCriterion("Customer_HK is null");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIsNotNull() {
            addCriterion("Customer_HK is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerHkEqualTo(String value) {
            addCriterion("Customer_HK =", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotEqualTo(String value) {
            addCriterion("Customer_HK <>", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkGreaterThan(String value) {
            addCriterion("Customer_HK >", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkGreaterThanOrEqualTo(String value) {
            addCriterion("Customer_HK >=", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLessThan(String value) {
            addCriterion("Customer_HK <", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLessThanOrEqualTo(String value) {
            addCriterion("Customer_HK <=", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkLike(String value) {
            addCriterion("Customer_HK like", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotLike(String value) {
            addCriterion("Customer_HK not like", value, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkIn(List<String> values) {
            addCriterion("Customer_HK in", values, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotIn(List<String> values) {
            addCriterion("Customer_HK not in", values, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkBetween(String value1, String value2) {
            addCriterion("Customer_HK between", value1, value2, "customerHk");
            return (Criteria) this;
        }

        public Criteria andCustomerHkNotBetween(String value1, String value2) {
            addCriterion("Customer_HK not between", value1, value2, "customerHk");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("Deposit is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("Deposit is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(BigDecimal value) {
            addCriterion("Deposit =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(BigDecimal value) {
            addCriterion("Deposit <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(BigDecimal value) {
            addCriterion("Deposit >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Deposit >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(BigDecimal value) {
            addCriterion("Deposit <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Deposit <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<BigDecimal> values) {
            addCriterion("Deposit in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<BigDecimal> values) {
            addCriterion("Deposit not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Deposit between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Deposit not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andPriceflagIsNull() {
            addCriterion("PriceFlag is null");
            return (Criteria) this;
        }

        public Criteria andPriceflagIsNotNull() {
            addCriterion("PriceFlag is not null");
            return (Criteria) this;
        }

        public Criteria andPriceflagEqualTo(Integer value) {
            addCriterion("PriceFlag =", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagNotEqualTo(Integer value) {
            addCriterion("PriceFlag <>", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagGreaterThan(Integer value) {
            addCriterion("PriceFlag >", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("PriceFlag >=", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagLessThan(Integer value) {
            addCriterion("PriceFlag <", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagLessThanOrEqualTo(Integer value) {
            addCriterion("PriceFlag <=", value, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagIn(List<Integer> values) {
            addCriterion("PriceFlag in", values, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagNotIn(List<Integer> values) {
            addCriterion("PriceFlag not in", values, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagBetween(Integer value1, Integer value2) {
            addCriterion("PriceFlag between", value1, value2, "priceflag");
            return (Criteria) this;
        }

        public Criteria andPriceflagNotBetween(Integer value1, Integer value2) {
            addCriterion("PriceFlag not between", value1, value2, "priceflag");
            return (Criteria) this;
        }

        public Criteria andSpecpackIsNull() {
            addCriterion("SpecPack is null");
            return (Criteria) this;
        }

        public Criteria andSpecpackIsNotNull() {
            addCriterion("SpecPack is not null");
            return (Criteria) this;
        }

        public Criteria andSpecpackEqualTo(Integer value) {
            addCriterion("SpecPack =", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackNotEqualTo(Integer value) {
            addCriterion("SpecPack <>", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackGreaterThan(Integer value) {
            addCriterion("SpecPack >", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackGreaterThanOrEqualTo(Integer value) {
            addCriterion("SpecPack >=", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackLessThan(Integer value) {
            addCriterion("SpecPack <", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackLessThanOrEqualTo(Integer value) {
            addCriterion("SpecPack <=", value, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackIn(List<Integer> values) {
            addCriterion("SpecPack in", values, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackNotIn(List<Integer> values) {
            addCriterion("SpecPack not in", values, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackBetween(Integer value1, Integer value2) {
            addCriterion("SpecPack between", value1, value2, "specpack");
            return (Criteria) this;
        }

        public Criteria andSpecpackNotBetween(Integer value1, Integer value2) {
            addCriterion("SpecPack not between", value1, value2, "specpack");
            return (Criteria) this;
        }

        public Criteria andLajiIsNull() {
            addCriterion("laji is null");
            return (Criteria) this;
        }

        public Criteria andLajiIsNotNull() {
            addCriterion("laji is not null");
            return (Criteria) this;
        }

        public Criteria andLajiEqualTo(String value) {
            addCriterion("laji =", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotEqualTo(String value) {
            addCriterion("laji <>", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiGreaterThan(String value) {
            addCriterion("laji >", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiGreaterThanOrEqualTo(String value) {
            addCriterion("laji >=", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLessThan(String value) {
            addCriterion("laji <", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLessThanOrEqualTo(String value) {
            addCriterion("laji <=", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLike(String value) {
            addCriterion("laji like", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotLike(String value) {
            addCriterion("laji not like", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiIn(List<String> values) {
            addCriterion("laji in", values, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotIn(List<String> values) {
            addCriterion("laji not in", values, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiBetween(String value1, String value2) {
            addCriterion("laji between", value1, value2, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotBetween(String value1, String value2) {
            addCriterion("laji not between", value1, value2, "laji");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionIsNull() {
            addCriterion("CstmDivision is null");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionIsNotNull() {
            addCriterion("CstmDivision is not null");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionEqualTo(String value) {
            addCriterion("CstmDivision =", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionNotEqualTo(String value) {
            addCriterion("CstmDivision <>", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionGreaterThan(String value) {
            addCriterion("CstmDivision >", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionGreaterThanOrEqualTo(String value) {
            addCriterion("CstmDivision >=", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionLessThan(String value) {
            addCriterion("CstmDivision <", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionLessThanOrEqualTo(String value) {
            addCriterion("CstmDivision <=", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionLike(String value) {
            addCriterion("CstmDivision like", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionNotLike(String value) {
            addCriterion("CstmDivision not like", value, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionIn(List<String> values) {
            addCriterion("CstmDivision in", values, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionNotIn(List<String> values) {
            addCriterion("CstmDivision not in", values, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionBetween(String value1, String value2) {
            addCriterion("CstmDivision between", value1, value2, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andCstmdivisionNotBetween(String value1, String value2) {
            addCriterion("CstmDivision not between", value1, value2, "cstmdivision");
            return (Criteria) this;
        }

        public Criteria andSalesinIsNull() {
            addCriterion("SalesIn is null");
            return (Criteria) this;
        }

        public Criteria andSalesinIsNotNull() {
            addCriterion("SalesIn is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinEqualTo(String value) {
            addCriterion("SalesIn =", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinNotEqualTo(String value) {
            addCriterion("SalesIn <>", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinGreaterThan(String value) {
            addCriterion("SalesIn >", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinGreaterThanOrEqualTo(String value) {
            addCriterion("SalesIn >=", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinLessThan(String value) {
            addCriterion("SalesIn <", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinLessThanOrEqualTo(String value) {
            addCriterion("SalesIn <=", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinLike(String value) {
            addCriterion("SalesIn like", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinNotLike(String value) {
            addCriterion("SalesIn not like", value, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinIn(List<String> values) {
            addCriterion("SalesIn in", values, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinNotIn(List<String> values) {
            addCriterion("SalesIn not in", values, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinBetween(String value1, String value2) {
            addCriterion("SalesIn between", value1, value2, "salesin");
            return (Criteria) this;
        }

        public Criteria andSalesinNotBetween(String value1, String value2) {
            addCriterion("SalesIn not between", value1, value2, "salesin");
            return (Criteria) this;
        }

        public Criteria andIndclass1IsNull() {
            addCriterion("IndClass1 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass1IsNotNull() {
            addCriterion("IndClass1 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass1EqualTo(String value) {
            addCriterion("IndClass1 =", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1NotEqualTo(String value) {
            addCriterion("IndClass1 <>", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1GreaterThan(String value) {
            addCriterion("IndClass1 >", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass1 >=", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1LessThan(String value) {
            addCriterion("IndClass1 <", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1LessThanOrEqualTo(String value) {
            addCriterion("IndClass1 <=", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1Like(String value) {
            addCriterion("IndClass1 like", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1NotLike(String value) {
            addCriterion("IndClass1 not like", value, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1In(List<String> values) {
            addCriterion("IndClass1 in", values, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1NotIn(List<String> values) {
            addCriterion("IndClass1 not in", values, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1Between(String value1, String value2) {
            addCriterion("IndClass1 between", value1, value2, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass1NotBetween(String value1, String value2) {
            addCriterion("IndClass1 not between", value1, value2, "indclass1");
            return (Criteria) this;
        }

        public Criteria andIndclass2IsNull() {
            addCriterion("IndClass2 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass2IsNotNull() {
            addCriterion("IndClass2 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass2EqualTo(String value) {
            addCriterion("IndClass2 =", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2NotEqualTo(String value) {
            addCriterion("IndClass2 <>", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2GreaterThan(String value) {
            addCriterion("IndClass2 >", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass2 >=", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2LessThan(String value) {
            addCriterion("IndClass2 <", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2LessThanOrEqualTo(String value) {
            addCriterion("IndClass2 <=", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2Like(String value) {
            addCriterion("IndClass2 like", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2NotLike(String value) {
            addCriterion("IndClass2 not like", value, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2In(List<String> values) {
            addCriterion("IndClass2 in", values, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2NotIn(List<String> values) {
            addCriterion("IndClass2 not in", values, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2Between(String value1, String value2) {
            addCriterion("IndClass2 between", value1, value2, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass2NotBetween(String value1, String value2) {
            addCriterion("IndClass2 not between", value1, value2, "indclass2");
            return (Criteria) this;
        }

        public Criteria andIndclass3IsNull() {
            addCriterion("IndClass3 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass3IsNotNull() {
            addCriterion("IndClass3 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass3EqualTo(String value) {
            addCriterion("IndClass3 =", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3NotEqualTo(String value) {
            addCriterion("IndClass3 <>", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3GreaterThan(String value) {
            addCriterion("IndClass3 >", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass3 >=", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3LessThan(String value) {
            addCriterion("IndClass3 <", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3LessThanOrEqualTo(String value) {
            addCriterion("IndClass3 <=", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3Like(String value) {
            addCriterion("IndClass3 like", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3NotLike(String value) {
            addCriterion("IndClass3 not like", value, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3In(List<String> values) {
            addCriterion("IndClass3 in", values, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3NotIn(List<String> values) {
            addCriterion("IndClass3 not in", values, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3Between(String value1, String value2) {
            addCriterion("IndClass3 between", value1, value2, "indclass3");
            return (Criteria) this;
        }

        public Criteria andIndclass3NotBetween(String value1, String value2) {
            addCriterion("IndClass3 not between", value1, value2, "indclass3");
            return (Criteria) this;
        }

        public Criteria andBusinessIsNull() {
            addCriterion("Business is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIsNotNull() {
            addCriterion("Business is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessEqualTo(String value) {
            addCriterion("Business =", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotEqualTo(String value) {
            addCriterion("Business <>", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessGreaterThan(String value) {
            addCriterion("Business >", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessGreaterThanOrEqualTo(String value) {
            addCriterion("Business >=", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessLessThan(String value) {
            addCriterion("Business <", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessLessThanOrEqualTo(String value) {
            addCriterion("Business <=", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessLike(String value) {
            addCriterion("Business like", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotLike(String value) {
            addCriterion("Business not like", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessIn(List<String> values) {
            addCriterion("Business in", values, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotIn(List<String> values) {
            addCriterion("Business not in", values, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessBetween(String value1, String value2) {
            addCriterion("Business between", value1, value2, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotBetween(String value1, String value2) {
            addCriterion("Business not between", value1, value2, "business");
            return (Criteria) this;
        }

        public Criteria andGcodeIsNull() {
            addCriterion("GCode is null");
            return (Criteria) this;
        }

        public Criteria andGcodeIsNotNull() {
            addCriterion("GCode is not null");
            return (Criteria) this;
        }

        public Criteria andGcodeEqualTo(String value) {
            addCriterion("GCode =", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeNotEqualTo(String value) {
            addCriterion("GCode <>", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeGreaterThan(String value) {
            addCriterion("GCode >", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeGreaterThanOrEqualTo(String value) {
            addCriterion("GCode >=", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeLessThan(String value) {
            addCriterion("GCode <", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeLessThanOrEqualTo(String value) {
            addCriterion("GCode <=", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeLike(String value) {
            addCriterion("GCode like", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeNotLike(String value) {
            addCriterion("GCode not like", value, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeIn(List<String> values) {
            addCriterion("GCode in", values, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeNotIn(List<String> values) {
            addCriterion("GCode not in", values, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeBetween(String value1, String value2) {
            addCriterion("GCode between", value1, value2, "gcode");
            return (Criteria) this;
        }

        public Criteria andGcodeNotBetween(String value1, String value2) {
            addCriterion("GCode not between", value1, value2, "gcode");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNull() {
            addCriterion("GroupName is null");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNotNull() {
            addCriterion("GroupName is not null");
            return (Criteria) this;
        }

        public Criteria andGroupnameEqualTo(String value) {
            addCriterion("GroupName =", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotEqualTo(String value) {
            addCriterion("GroupName <>", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThan(String value) {
            addCriterion("GroupName >", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThanOrEqualTo(String value) {
            addCriterion("GroupName >=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThan(String value) {
            addCriterion("GroupName <", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThanOrEqualTo(String value) {
            addCriterion("GroupName <=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLike(String value) {
            addCriterion("GroupName like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotLike(String value) {
            addCriterion("GroupName not like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameIn(List<String> values) {
            addCriterion("GroupName in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotIn(List<String> values) {
            addCriterion("GroupName not in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameBetween(String value1, String value2) {
            addCriterion("GroupName between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotBetween(String value1, String value2) {
            addCriterion("GroupName not between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andComefromIsNull() {
            addCriterion("ComeFrom is null");
            return (Criteria) this;
        }

        public Criteria andComefromIsNotNull() {
            addCriterion("ComeFrom is not null");
            return (Criteria) this;
        }

        public Criteria andComefromEqualTo(String value) {
            addCriterion("ComeFrom =", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromNotEqualTo(String value) {
            addCriterion("ComeFrom <>", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromGreaterThan(String value) {
            addCriterion("ComeFrom >", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromGreaterThanOrEqualTo(String value) {
            addCriterion("ComeFrom >=", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromLessThan(String value) {
            addCriterion("ComeFrom <", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromLessThanOrEqualTo(String value) {
            addCriterion("ComeFrom <=", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromLike(String value) {
            addCriterion("ComeFrom like", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromNotLike(String value) {
            addCriterion("ComeFrom not like", value, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromIn(List<String> values) {
            addCriterion("ComeFrom in", values, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromNotIn(List<String> values) {
            addCriterion("ComeFrom not in", values, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromBetween(String value1, String value2) {
            addCriterion("ComeFrom between", value1, value2, "comefrom");
            return (Criteria) this;
        }

        public Criteria andComefromNotBetween(String value1, String value2) {
            addCriterion("ComeFrom not between", value1, value2, "comefrom");
            return (Criteria) this;
        }

        public Criteria andWebIsNull() {
            addCriterion("Web is null");
            return (Criteria) this;
        }

        public Criteria andWebIsNotNull() {
            addCriterion("Web is not null");
            return (Criteria) this;
        }

        public Criteria andWebEqualTo(String value) {
            addCriterion("Web =", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotEqualTo(String value) {
            addCriterion("Web <>", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThan(String value) {
            addCriterion("Web >", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThanOrEqualTo(String value) {
            addCriterion("Web >=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThan(String value) {
            addCriterion("Web <", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThanOrEqualTo(String value) {
            addCriterion("Web <=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLike(String value) {
            addCriterion("Web like", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotLike(String value) {
            addCriterion("Web not like", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebIn(List<String> values) {
            addCriterion("Web in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotIn(List<String> values) {
            addCriterion("Web not in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebBetween(String value1, String value2) {
            addCriterion("Web between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotBetween(String value1, String value2) {
            addCriterion("Web not between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andPricegradeIsNull() {
            addCriterion("PriceGrade is null");
            return (Criteria) this;
        }

        public Criteria andPricegradeIsNotNull() {
            addCriterion("PriceGrade is not null");
            return (Criteria) this;
        }

        public Criteria andPricegradeEqualTo(String value) {
            addCriterion("PriceGrade =", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeNotEqualTo(String value) {
            addCriterion("PriceGrade <>", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeGreaterThan(String value) {
            addCriterion("PriceGrade >", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeGreaterThanOrEqualTo(String value) {
            addCriterion("PriceGrade >=", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeLessThan(String value) {
            addCriterion("PriceGrade <", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeLessThanOrEqualTo(String value) {
            addCriterion("PriceGrade <=", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeLike(String value) {
            addCriterion("PriceGrade like", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeNotLike(String value) {
            addCriterion("PriceGrade not like", value, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeIn(List<String> values) {
            addCriterion("PriceGrade in", values, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeNotIn(List<String> values) {
            addCriterion("PriceGrade not in", values, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeBetween(String value1, String value2) {
            addCriterion("PriceGrade between", value1, value2, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andPricegradeNotBetween(String value1, String value2) {
            addCriterion("PriceGrade not between", value1, value2, "pricegrade");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoIsNull() {
            addCriterion("SameCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoIsNotNull() {
            addCriterion("SameCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoEqualTo(String value) {
            addCriterion("SameCustomerNo =", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoNotEqualTo(String value) {
            addCriterion("SameCustomerNo <>", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoGreaterThan(String value) {
            addCriterion("SameCustomerNo >", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("SameCustomerNo >=", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoLessThan(String value) {
            addCriterion("SameCustomerNo <", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoLessThanOrEqualTo(String value) {
            addCriterion("SameCustomerNo <=", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoLike(String value) {
            addCriterion("SameCustomerNo like", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoNotLike(String value) {
            addCriterion("SameCustomerNo not like", value, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoIn(List<String> values) {
            addCriterion("SameCustomerNo in", values, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoNotIn(List<String> values) {
            addCriterion("SameCustomerNo not in", values, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoBetween(String value1, String value2) {
            addCriterion("SameCustomerNo between", value1, value2, "samecustomerno");
            return (Criteria) this;
        }

        public Criteria andSamecustomernoNotBetween(String value1, String value2) {
            addCriterion("SameCustomerNo not between", value1, value2, "samecustomerno");
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

        public Criteria andJpvipcodeIsNull() {
            addCriterion("JPVIPCode is null");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeIsNotNull() {
            addCriterion("JPVIPCode is not null");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeEqualTo(String value) {
            addCriterion("JPVIPCode =", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeNotEqualTo(String value) {
            addCriterion("JPVIPCode <>", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeGreaterThan(String value) {
            addCriterion("JPVIPCode >", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("JPVIPCode >=", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeLessThan(String value) {
            addCriterion("JPVIPCode <", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeLessThanOrEqualTo(String value) {
            addCriterion("JPVIPCode <=", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeLike(String value) {
            addCriterion("JPVIPCode like", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeNotLike(String value) {
            addCriterion("JPVIPCode not like", value, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeIn(List<String> values) {
            addCriterion("JPVIPCode in", values, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeNotIn(List<String> values) {
            addCriterion("JPVIPCode not in", values, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeBetween(String value1, String value2) {
            addCriterion("JPVIPCode between", value1, value2, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andJpvipcodeNotBetween(String value1, String value2) {
            addCriterion("JPVIPCode not between", value1, value2, "jpvipcode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIsNull() {
            addCriterion("VillageCode is null");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIsNotNull() {
            addCriterion("VillageCode is not null");
            return (Criteria) this;
        }

        public Criteria andVillagecodeEqualTo(String value) {
            addCriterion("VillageCode =", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotEqualTo(String value) {
            addCriterion("VillageCode <>", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeGreaterThan(String value) {
            addCriterion("VillageCode >", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeGreaterThanOrEqualTo(String value) {
            addCriterion("VillageCode >=", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLessThan(String value) {
            addCriterion("VillageCode <", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLessThanOrEqualTo(String value) {
            addCriterion("VillageCode <=", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLike(String value) {
            addCriterion("VillageCode like", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotLike(String value) {
            addCriterion("VillageCode not like", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIn(List<String> values) {
            addCriterion("VillageCode in", values, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotIn(List<String> values) {
            addCriterion("VillageCode not in", values, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeBetween(String value1, String value2) {
            addCriterion("VillageCode between", value1, value2, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotBetween(String value1, String value2) {
            addCriterion("VillageCode not between", value1, value2, "villagecode");
            return (Criteria) this;
        }

        public Criteria andIndclass4IsNull() {
            addCriterion("IndClass4 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass4IsNotNull() {
            addCriterion("IndClass4 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass4EqualTo(String value) {
            addCriterion("IndClass4 =", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4NotEqualTo(String value) {
            addCriterion("IndClass4 <>", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4GreaterThan(String value) {
            addCriterion("IndClass4 >", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass4 >=", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4LessThan(String value) {
            addCriterion("IndClass4 <", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4LessThanOrEqualTo(String value) {
            addCriterion("IndClass4 <=", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4Like(String value) {
            addCriterion("IndClass4 like", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4NotLike(String value) {
            addCriterion("IndClass4 not like", value, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4In(List<String> values) {
            addCriterion("IndClass4 in", values, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4NotIn(List<String> values) {
            addCriterion("IndClass4 not in", values, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4Between(String value1, String value2) {
            addCriterion("IndClass4 between", value1, value2, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass4NotBetween(String value1, String value2) {
            addCriterion("IndClass4 not between", value1, value2, "indclass4");
            return (Criteria) this;
        }

        public Criteria andIndclass5IsNull() {
            addCriterion("IndClass5 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass5IsNotNull() {
            addCriterion("IndClass5 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass5EqualTo(String value) {
            addCriterion("IndClass5 =", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5NotEqualTo(String value) {
            addCriterion("IndClass5 <>", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5GreaterThan(String value) {
            addCriterion("IndClass5 >", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass5 >=", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5LessThan(String value) {
            addCriterion("IndClass5 <", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5LessThanOrEqualTo(String value) {
            addCriterion("IndClass5 <=", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5Like(String value) {
            addCriterion("IndClass5 like", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5NotLike(String value) {
            addCriterion("IndClass5 not like", value, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5In(List<String> values) {
            addCriterion("IndClass5 in", values, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5NotIn(List<String> values) {
            addCriterion("IndClass5 not in", values, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5Between(String value1, String value2) {
            addCriterion("IndClass5 between", value1, value2, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass5NotBetween(String value1, String value2) {
            addCriterion("IndClass5 not between", value1, value2, "indclass5");
            return (Criteria) this;
        }

        public Criteria andIndclass6IsNull() {
            addCriterion("IndClass6 is null");
            return (Criteria) this;
        }

        public Criteria andIndclass6IsNotNull() {
            addCriterion("IndClass6 is not null");
            return (Criteria) this;
        }

        public Criteria andIndclass6EqualTo(String value) {
            addCriterion("IndClass6 =", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6NotEqualTo(String value) {
            addCriterion("IndClass6 <>", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6GreaterThan(String value) {
            addCriterion("IndClass6 >", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6GreaterThanOrEqualTo(String value) {
            addCriterion("IndClass6 >=", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6LessThan(String value) {
            addCriterion("IndClass6 <", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6LessThanOrEqualTo(String value) {
            addCriterion("IndClass6 <=", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6Like(String value) {
            addCriterion("IndClass6 like", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6NotLike(String value) {
            addCriterion("IndClass6 not like", value, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6In(List<String> values) {
            addCriterion("IndClass6 in", values, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6NotIn(List<String> values) {
            addCriterion("IndClass6 not in", values, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6Between(String value1, String value2) {
            addCriterion("IndClass6 between", value1, value2, "indclass6");
            return (Criteria) this;
        }

        public Criteria andIndclass6NotBetween(String value1, String value2) {
            addCriterion("IndClass6 not between", value1, value2, "indclass6");
            return (Criteria) this;
        }

        public Criteria andLastorderdateIsNull() {
            addCriterion("LastOrderDate is null");
            return (Criteria) this;
        }

        public Criteria andLastorderdateIsNotNull() {
            addCriterion("LastOrderDate is not null");
            return (Criteria) this;
        }

        public Criteria andLastorderdateEqualTo(Date value) {
            addCriterion("LastOrderDate =", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateNotEqualTo(Date value) {
            addCriterion("LastOrderDate <>", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateGreaterThan(Date value) {
            addCriterion("LastOrderDate >", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateGreaterThanOrEqualTo(Date value) {
            addCriterion("LastOrderDate >=", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateLessThan(Date value) {
            addCriterion("LastOrderDate <", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateLessThanOrEqualTo(Date value) {
            addCriterion("LastOrderDate <=", value, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateIn(List<Date> values) {
            addCriterion("LastOrderDate in", values, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateNotIn(List<Date> values) {
            addCriterion("LastOrderDate not in", values, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateBetween(Date value1, Date value2) {
            addCriterion("LastOrderDate between", value1, value2, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andLastorderdateNotBetween(Date value1, Date value2) {
            addCriterion("LastOrderDate not between", value1, value2, "lastorderdate");
            return (Criteria) this;
        }

        public Criteria andNccustomernoIsNull() {
            addCriterion("NCCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andNccustomernoIsNotNull() {
            addCriterion("NCCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andNccustomernoEqualTo(String value) {
            addCriterion("NCCustomerNo =", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoNotEqualTo(String value) {
            addCriterion("NCCustomerNo <>", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoGreaterThan(String value) {
            addCriterion("NCCustomerNo >", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("NCCustomerNo >=", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoLessThan(String value) {
            addCriterion("NCCustomerNo <", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoLessThanOrEqualTo(String value) {
            addCriterion("NCCustomerNo <=", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoLike(String value) {
            addCriterion("NCCustomerNo like", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoNotLike(String value) {
            addCriterion("NCCustomerNo not like", value, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoIn(List<String> values) {
            addCriterion("NCCustomerNo in", values, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoNotIn(List<String> values) {
            addCriterion("NCCustomerNo not in", values, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoBetween(String value1, String value2) {
            addCriterion("NCCustomerNo between", value1, value2, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andNccustomernoNotBetween(String value1, String value2) {
            addCriterion("NCCustomerNo not between", value1, value2, "nccustomerno");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailIsNull() {
            addCriterion("RcvInvoiceEmail is null");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailIsNotNull() {
            addCriterion("RcvInvoiceEmail is not null");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailEqualTo(String value) {
            addCriterion("RcvInvoiceEmail =", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailNotEqualTo(String value) {
            addCriterion("RcvInvoiceEmail <>", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailGreaterThan(String value) {
            addCriterion("RcvInvoiceEmail >", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailGreaterThanOrEqualTo(String value) {
            addCriterion("RcvInvoiceEmail >=", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailLessThan(String value) {
            addCriterion("RcvInvoiceEmail <", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailLessThanOrEqualTo(String value) {
            addCriterion("RcvInvoiceEmail <=", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailLike(String value) {
            addCriterion("RcvInvoiceEmail like", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailNotLike(String value) {
            addCriterion("RcvInvoiceEmail not like", value, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailIn(List<String> values) {
            addCriterion("RcvInvoiceEmail in", values, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailNotIn(List<String> values) {
            addCriterion("RcvInvoiceEmail not in", values, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailBetween(String value1, String value2) {
            addCriterion("RcvInvoiceEmail between", value1, value2, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andRcvinvoiceemailNotBetween(String value1, String value2) {
            addCriterion("RcvInvoiceEmail not between", value1, value2, "rcvinvoiceemail");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerIsNull() {
            addCriterion("sleepcustomer is null");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerIsNotNull() {
            addCriterion("sleepcustomer is not null");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerEqualTo(String value) {
            addCriterion("sleepcustomer =", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerNotEqualTo(String value) {
            addCriterion("sleepcustomer <>", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerGreaterThan(String value) {
            addCriterion("sleepcustomer >", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerGreaterThanOrEqualTo(String value) {
            addCriterion("sleepcustomer >=", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerLessThan(String value) {
            addCriterion("sleepcustomer <", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerLessThanOrEqualTo(String value) {
            addCriterion("sleepcustomer <=", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerLike(String value) {
            addCriterion("sleepcustomer like", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerNotLike(String value) {
            addCriterion("sleepcustomer not like", value, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerIn(List<String> values) {
            addCriterion("sleepcustomer in", values, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerNotIn(List<String> values) {
            addCriterion("sleepcustomer not in", values, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerBetween(String value1, String value2) {
            addCriterion("sleepcustomer between", value1, value2, "sleepcustomer");
            return (Criteria) this;
        }

        public Criteria andSleepcustomerNotBetween(String value1, String value2) {
            addCriterion("sleepcustomer not between", value1, value2, "sleepcustomer");
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
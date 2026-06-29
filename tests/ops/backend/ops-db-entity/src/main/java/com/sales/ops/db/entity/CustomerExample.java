package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomerExample() {
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

        public Criteria andVipcodeIsNull() {
            addCriterion("VIPCode is null");
            return (Criteria) this;
        }

        public Criteria andVipcodeIsNotNull() {
            addCriterion("VIPCode is not null");
            return (Criteria) this;
        }

        public Criteria andVipcodeEqualTo(String value) {
            addCriterion("VIPCode =", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotEqualTo(String value) {
            addCriterion("VIPCode <>", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThan(String value) {
            addCriterion("VIPCode >", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("VIPCode >=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThan(String value) {
            addCriterion("VIPCode <", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLessThanOrEqualTo(String value) {
            addCriterion("VIPCode <=", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeLike(String value) {
            addCriterion("VIPCode like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotLike(String value) {
            addCriterion("VIPCode not like", value, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeIn(List<String> values) {
            addCriterion("VIPCode in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotIn(List<String> values) {
            addCriterion("VIPCode not in", values, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeBetween(String value1, String value2) {
            addCriterion("VIPCode between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andVipcodeNotBetween(String value1, String value2) {
            addCriterion("VIPCode not between", value1, value2, "vipcode");
            return (Criteria) this;
        }

        public Criteria andLajiIsNull() {
            addCriterion("Laji is null");
            return (Criteria) this;
        }

        public Criteria andLajiIsNotNull() {
            addCriterion("Laji is not null");
            return (Criteria) this;
        }

        public Criteria andLajiEqualTo(String value) {
            addCriterion("Laji =", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotEqualTo(String value) {
            addCriterion("Laji <>", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiGreaterThan(String value) {
            addCriterion("Laji >", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiGreaterThanOrEqualTo(String value) {
            addCriterion("Laji >=", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLessThan(String value) {
            addCriterion("Laji <", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLessThanOrEqualTo(String value) {
            addCriterion("Laji <=", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiLike(String value) {
            addCriterion("Laji like", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotLike(String value) {
            addCriterion("Laji not like", value, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiIn(List<String> values) {
            addCriterion("Laji in", values, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotIn(List<String> values) {
            addCriterion("Laji not in", values, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiBetween(String value1, String value2) {
            addCriterion("Laji between", value1, value2, "laji");
            return (Criteria) this;
        }

        public Criteria andLajiNotBetween(String value1, String value2) {
            addCriterion("Laji not between", value1, value2, "laji");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnIsNull() {
            addCriterion("VIPCode_Cn is null");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnIsNotNull() {
            addCriterion("VIPCode_Cn is not null");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnEqualTo(String value) {
            addCriterion("VIPCode_Cn =", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnNotEqualTo(String value) {
            addCriterion("VIPCode_Cn <>", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnGreaterThan(String value) {
            addCriterion("VIPCode_Cn >", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnGreaterThanOrEqualTo(String value) {
            addCriterion("VIPCode_Cn >=", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnLessThan(String value) {
            addCriterion("VIPCode_Cn <", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnLessThanOrEqualTo(String value) {
            addCriterion("VIPCode_Cn <=", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnLike(String value) {
            addCriterion("VIPCode_Cn like", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnNotLike(String value) {
            addCriterion("VIPCode_Cn not like", value, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnIn(List<String> values) {
            addCriterion("VIPCode_Cn in", values, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnNotIn(List<String> values) {
            addCriterion("VIPCode_Cn not in", values, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnBetween(String value1, String value2) {
            addCriterion("VIPCode_Cn between", value1, value2, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andVipcodeCnNotBetween(String value1, String value2) {
            addCriterion("VIPCode_Cn not between", value1, value2, "vipcodeCn");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeIsNull() {
            addCriterion("EndUser_Type is null");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeIsNotNull() {
            addCriterion("EndUser_Type is not null");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeEqualTo(String value) {
            addCriterion("EndUser_Type =", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeNotEqualTo(String value) {
            addCriterion("EndUser_Type <>", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeGreaterThan(String value) {
            addCriterion("EndUser_Type >", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("EndUser_Type >=", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeLessThan(String value) {
            addCriterion("EndUser_Type <", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeLessThanOrEqualTo(String value) {
            addCriterion("EndUser_Type <=", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeLike(String value) {
            addCriterion("EndUser_Type like", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeNotLike(String value) {
            addCriterion("EndUser_Type not like", value, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeIn(List<String> values) {
            addCriterion("EndUser_Type in", values, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeNotIn(List<String> values) {
            addCriterion("EndUser_Type not in", values, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeBetween(String value1, String value2) {
            addCriterion("EndUser_Type between", value1, value2, "enduserType");
            return (Criteria) this;
        }

        public Criteria andEnduserTypeNotBetween(String value1, String value2) {
            addCriterion("EndUser_Type not between", value1, value2, "enduserType");
            return (Criteria) this;
        }

        public Criteria andBeijianIsNull() {
            addCriterion("Beijian is null");
            return (Criteria) this;
        }

        public Criteria andBeijianIsNotNull() {
            addCriterion("Beijian is not null");
            return (Criteria) this;
        }

        public Criteria andBeijianEqualTo(Boolean value) {
            addCriterion("Beijian =", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianNotEqualTo(Boolean value) {
            addCriterion("Beijian <>", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianGreaterThan(Boolean value) {
            addCriterion("Beijian >", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianGreaterThanOrEqualTo(Boolean value) {
            addCriterion("Beijian >=", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianLessThan(Boolean value) {
            addCriterion("Beijian <", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianLessThanOrEqualTo(Boolean value) {
            addCriterion("Beijian <=", value, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianIn(List<Boolean> values) {
            addCriterion("Beijian in", values, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianNotIn(List<Boolean> values) {
            addCriterion("Beijian not in", values, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianBetween(Boolean value1, Boolean value2) {
            addCriterion("Beijian between", value1, value2, "beijian");
            return (Criteria) this;
        }

        public Criteria andBeijianNotBetween(Boolean value1, Boolean value2) {
            addCriterion("Beijian not between", value1, value2, "beijian");
            return (Criteria) this;
        }

        public Criteria andPeitaoIsNull() {
            addCriterion("PeiTao is null");
            return (Criteria) this;
        }

        public Criteria andPeitaoIsNotNull() {
            addCriterion("PeiTao is not null");
            return (Criteria) this;
        }

        public Criteria andPeitaoEqualTo(Boolean value) {
            addCriterion("PeiTao =", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoNotEqualTo(Boolean value) {
            addCriterion("PeiTao <>", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoGreaterThan(Boolean value) {
            addCriterion("PeiTao >", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("PeiTao >=", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoLessThan(Boolean value) {
            addCriterion("PeiTao <", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoLessThanOrEqualTo(Boolean value) {
            addCriterion("PeiTao <=", value, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoIn(List<Boolean> values) {
            addCriterion("PeiTao in", values, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoNotIn(List<Boolean> values) {
            addCriterion("PeiTao not in", values, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoBetween(Boolean value1, Boolean value2) {
            addCriterion("PeiTao between", value1, value2, "peitao");
            return (Criteria) this;
        }

        public Criteria andPeitaoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("PeiTao not between", value1, value2, "peitao");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstIsNull() {
            addCriterion("Phone_first is null");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstIsNotNull() {
            addCriterion("Phone_first is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstEqualTo(String value) {
            addCriterion("Phone_first =", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstNotEqualTo(String value) {
            addCriterion("Phone_first <>", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstGreaterThan(String value) {
            addCriterion("Phone_first >", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstGreaterThanOrEqualTo(String value) {
            addCriterion("Phone_first >=", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstLessThan(String value) {
            addCriterion("Phone_first <", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstLessThanOrEqualTo(String value) {
            addCriterion("Phone_first <=", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstLike(String value) {
            addCriterion("Phone_first like", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstNotLike(String value) {
            addCriterion("Phone_first not like", value, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstIn(List<String> values) {
            addCriterion("Phone_first in", values, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstNotIn(List<String> values) {
            addCriterion("Phone_first not in", values, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstBetween(String value1, String value2) {
            addCriterion("Phone_first between", value1, value2, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneFirstNotBetween(String value1, String value2) {
            addCriterion("Phone_first not between", value1, value2, "phoneFirst");
            return (Criteria) this;
        }

        public Criteria andPhoneLastIsNull() {
            addCriterion("Phone_Last is null");
            return (Criteria) this;
        }

        public Criteria andPhoneLastIsNotNull() {
            addCriterion("Phone_Last is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneLastEqualTo(String value) {
            addCriterion("Phone_Last =", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastNotEqualTo(String value) {
            addCriterion("Phone_Last <>", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastGreaterThan(String value) {
            addCriterion("Phone_Last >", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastGreaterThanOrEqualTo(String value) {
            addCriterion("Phone_Last >=", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastLessThan(String value) {
            addCriterion("Phone_Last <", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastLessThanOrEqualTo(String value) {
            addCriterion("Phone_Last <=", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastLike(String value) {
            addCriterion("Phone_Last like", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastNotLike(String value) {
            addCriterion("Phone_Last not like", value, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastIn(List<String> values) {
            addCriterion("Phone_Last in", values, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastNotIn(List<String> values) {
            addCriterion("Phone_Last not in", values, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastBetween(String value1, String value2) {
            addCriterion("Phone_Last between", value1, value2, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andPhoneLastNotBetween(String value1, String value2) {
            addCriterion("Phone_Last not between", value1, value2, "phoneLast");
            return (Criteria) this;
        }

        public Criteria andRestrictCustIsNull() {
            addCriterion("Restrict_Cust is null");
            return (Criteria) this;
        }

        public Criteria andRestrictCustIsNotNull() {
            addCriterion("Restrict_Cust is not null");
            return (Criteria) this;
        }

        public Criteria andRestrictCustEqualTo(String value) {
            addCriterion("Restrict_Cust =", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustNotEqualTo(String value) {
            addCriterion("Restrict_Cust <>", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustGreaterThan(String value) {
            addCriterion("Restrict_Cust >", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustGreaterThanOrEqualTo(String value) {
            addCriterion("Restrict_Cust >=", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustLessThan(String value) {
            addCriterion("Restrict_Cust <", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustLessThanOrEqualTo(String value) {
            addCriterion("Restrict_Cust <=", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustLike(String value) {
            addCriterion("Restrict_Cust like", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustNotLike(String value) {
            addCriterion("Restrict_Cust not like", value, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustIn(List<String> values) {
            addCriterion("Restrict_Cust in", values, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustNotIn(List<String> values) {
            addCriterion("Restrict_Cust not in", values, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustBetween(String value1, String value2) {
            addCriterion("Restrict_Cust between", value1, value2, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andRestrictCustNotBetween(String value1, String value2) {
            addCriterion("Restrict_Cust not between", value1, value2, "restrictCust");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIsNull() {
            addCriterion("Delivery_Flag is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIsNotNull() {
            addCriterion("Delivery_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagEqualTo(String value) {
            addCriterion("Delivery_Flag =", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotEqualTo(String value) {
            addCriterion("Delivery_Flag <>", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagGreaterThan(String value) {
            addCriterion("Delivery_Flag >", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagGreaterThanOrEqualTo(String value) {
            addCriterion("Delivery_Flag >=", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLessThan(String value) {
            addCriterion("Delivery_Flag <", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLessThanOrEqualTo(String value) {
            addCriterion("Delivery_Flag <=", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLike(String value) {
            addCriterion("Delivery_Flag like", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotLike(String value) {
            addCriterion("Delivery_Flag not like", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIn(List<String> values) {
            addCriterion("Delivery_Flag in", values, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotIn(List<String> values) {
            addCriterion("Delivery_Flag not in", values, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagBetween(String value1, String value2) {
            addCriterion("Delivery_Flag between", value1, value2, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotBetween(String value1, String value2) {
            addCriterion("Delivery_Flag not between", value1, value2, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andAgenttypeIsNull() {
            addCriterion("AgentType is null");
            return (Criteria) this;
        }

        public Criteria andAgenttypeIsNotNull() {
            addCriterion("AgentType is not null");
            return (Criteria) this;
        }

        public Criteria andAgenttypeEqualTo(String value) {
            addCriterion("AgentType =", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeNotEqualTo(String value) {
            addCriterion("AgentType <>", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeGreaterThan(String value) {
            addCriterion("AgentType >", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeGreaterThanOrEqualTo(String value) {
            addCriterion("AgentType >=", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeLessThan(String value) {
            addCriterion("AgentType <", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeLessThanOrEqualTo(String value) {
            addCriterion("AgentType <=", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeLike(String value) {
            addCriterion("AgentType like", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeNotLike(String value) {
            addCriterion("AgentType not like", value, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeIn(List<String> values) {
            addCriterion("AgentType in", values, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeNotIn(List<String> values) {
            addCriterion("AgentType not in", values, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeBetween(String value1, String value2) {
            addCriterion("AgentType between", value1, value2, "agenttype");
            return (Criteria) this;
        }

        public Criteria andAgenttypeNotBetween(String value1, String value2) {
            addCriterion("AgentType not between", value1, value2, "agenttype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeIsNull() {
            addCriterion("DeliveryType is null");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeIsNotNull() {
            addCriterion("DeliveryType is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeEqualTo(String value) {
            addCriterion("DeliveryType =", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeNotEqualTo(String value) {
            addCriterion("DeliveryType <>", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeGreaterThan(String value) {
            addCriterion("DeliveryType >", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeGreaterThanOrEqualTo(String value) {
            addCriterion("DeliveryType >=", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeLessThan(String value) {
            addCriterion("DeliveryType <", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeLessThanOrEqualTo(String value) {
            addCriterion("DeliveryType <=", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeLike(String value) {
            addCriterion("DeliveryType like", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeNotLike(String value) {
            addCriterion("DeliveryType not like", value, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeIn(List<String> values) {
            addCriterion("DeliveryType in", values, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeNotIn(List<String> values) {
            addCriterion("DeliveryType not in", values, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeBetween(String value1, String value2) {
            addCriterion("DeliveryType between", value1, value2, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andDeliverytypeNotBetween(String value1, String value2) {
            addCriterion("DeliveryType not between", value1, value2, "deliverytype");
            return (Criteria) this;
        }

        public Criteria andLicencenoIsNull() {
            addCriterion("LicenceNo is null");
            return (Criteria) this;
        }

        public Criteria andLicencenoIsNotNull() {
            addCriterion("LicenceNo is not null");
            return (Criteria) this;
        }

        public Criteria andLicencenoEqualTo(String value) {
            addCriterion("LicenceNo =", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoNotEqualTo(String value) {
            addCriterion("LicenceNo <>", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoGreaterThan(String value) {
            addCriterion("LicenceNo >", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoGreaterThanOrEqualTo(String value) {
            addCriterion("LicenceNo >=", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoLessThan(String value) {
            addCriterion("LicenceNo <", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoLessThanOrEqualTo(String value) {
            addCriterion("LicenceNo <=", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoLike(String value) {
            addCriterion("LicenceNo like", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoNotLike(String value) {
            addCriterion("LicenceNo not like", value, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoIn(List<String> values) {
            addCriterion("LicenceNo in", values, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoNotIn(List<String> values) {
            addCriterion("LicenceNo not in", values, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoBetween(String value1, String value2) {
            addCriterion("LicenceNo between", value1, value2, "licenceno");
            return (Criteria) this;
        }

        public Criteria andLicencenoNotBetween(String value1, String value2) {
            addCriterion("LicenceNo not between", value1, value2, "licenceno");
            return (Criteria) this;
        }

        public Criteria andNolicenceIsNull() {
            addCriterion("NoLicence is null");
            return (Criteria) this;
        }

        public Criteria andNolicenceIsNotNull() {
            addCriterion("NoLicence is not null");
            return (Criteria) this;
        }

        public Criteria andNolicenceEqualTo(Boolean value) {
            addCriterion("NoLicence =", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceNotEqualTo(Boolean value) {
            addCriterion("NoLicence <>", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceGreaterThan(Boolean value) {
            addCriterion("NoLicence >", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("NoLicence >=", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceLessThan(Boolean value) {
            addCriterion("NoLicence <", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceLessThanOrEqualTo(Boolean value) {
            addCriterion("NoLicence <=", value, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceIn(List<Boolean> values) {
            addCriterion("NoLicence in", values, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceNotIn(List<Boolean> values) {
            addCriterion("NoLicence not in", values, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceBetween(Boolean value1, Boolean value2) {
            addCriterion("NoLicence between", value1, value2, "nolicence");
            return (Criteria) this;
        }

        public Criteria andNolicenceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("NoLicence not between", value1, value2, "nolicence");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupIsNull() {
            addCriterion("BelongedGroup is null");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupIsNotNull() {
            addCriterion("BelongedGroup is not null");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupEqualTo(String value) {
            addCriterion("BelongedGroup =", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupNotEqualTo(String value) {
            addCriterion("BelongedGroup <>", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupGreaterThan(String value) {
            addCriterion("BelongedGroup >", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupGreaterThanOrEqualTo(String value) {
            addCriterion("BelongedGroup >=", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupLessThan(String value) {
            addCriterion("BelongedGroup <", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupLessThanOrEqualTo(String value) {
            addCriterion("BelongedGroup <=", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupLike(String value) {
            addCriterion("BelongedGroup like", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupNotLike(String value) {
            addCriterion("BelongedGroup not like", value, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupIn(List<String> values) {
            addCriterion("BelongedGroup in", values, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupNotIn(List<String> values) {
            addCriterion("BelongedGroup not in", values, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupBetween(String value1, String value2) {
            addCriterion("BelongedGroup between", value1, value2, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andBelongedgroupNotBetween(String value1, String value2) {
            addCriterion("BelongedGroup not between", value1, value2, "belongedgroup");
            return (Criteria) this;
        }

        public Criteria andStatementemailIsNull() {
            addCriterion("StatementEmail is null");
            return (Criteria) this;
        }

        public Criteria andStatementemailIsNotNull() {
            addCriterion("StatementEmail is not null");
            return (Criteria) this;
        }

        public Criteria andStatementemailEqualTo(String value) {
            addCriterion("StatementEmail =", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailNotEqualTo(String value) {
            addCriterion("StatementEmail <>", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailGreaterThan(String value) {
            addCriterion("StatementEmail >", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailGreaterThanOrEqualTo(String value) {
            addCriterion("StatementEmail >=", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailLessThan(String value) {
            addCriterion("StatementEmail <", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailLessThanOrEqualTo(String value) {
            addCriterion("StatementEmail <=", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailLike(String value) {
            addCriterion("StatementEmail like", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailNotLike(String value) {
            addCriterion("StatementEmail not like", value, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailIn(List<String> values) {
            addCriterion("StatementEmail in", values, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailNotIn(List<String> values) {
            addCriterion("StatementEmail not in", values, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailBetween(String value1, String value2) {
            addCriterion("StatementEmail between", value1, value2, "statementemail");
            return (Criteria) this;
        }

        public Criteria andStatementemailNotBetween(String value1, String value2) {
            addCriterion("StatementEmail not between", value1, value2, "statementemail");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressIsNull() {
            addCriterion("InvoiceAddress is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressIsNotNull() {
            addCriterion("InvoiceAddress is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressEqualTo(String value) {
            addCriterion("InvoiceAddress =", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressNotEqualTo(String value) {
            addCriterion("InvoiceAddress <>", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressGreaterThan(String value) {
            addCriterion("InvoiceAddress >", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceAddress >=", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressLessThan(String value) {
            addCriterion("InvoiceAddress <", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressLessThanOrEqualTo(String value) {
            addCriterion("InvoiceAddress <=", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressLike(String value) {
            addCriterion("InvoiceAddress like", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressNotLike(String value) {
            addCriterion("InvoiceAddress not like", value, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressIn(List<String> values) {
            addCriterion("InvoiceAddress in", values, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressNotIn(List<String> values) {
            addCriterion("InvoiceAddress not in", values, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressBetween(String value1, String value2) {
            addCriterion("InvoiceAddress between", value1, value2, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andInvoiceaddressNotBetween(String value1, String value2) {
            addCriterion("InvoiceAddress not between", value1, value2, "invoiceaddress");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidIsNull() {
            addCriterion("IndCode_GroupId is null");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidIsNotNull() {
            addCriterion("IndCode_GroupId is not null");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidEqualTo(String value) {
            addCriterion("IndCode_GroupId =", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidNotEqualTo(String value) {
            addCriterion("IndCode_GroupId <>", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidGreaterThan(String value) {
            addCriterion("IndCode_GroupId >", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidGreaterThanOrEqualTo(String value) {
            addCriterion("IndCode_GroupId >=", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidLessThan(String value) {
            addCriterion("IndCode_GroupId <", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidLessThanOrEqualTo(String value) {
            addCriterion("IndCode_GroupId <=", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidLike(String value) {
            addCriterion("IndCode_GroupId like", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidNotLike(String value) {
            addCriterion("IndCode_GroupId not like", value, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidIn(List<String> values) {
            addCriterion("IndCode_GroupId in", values, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidNotIn(List<String> values) {
            addCriterion("IndCode_GroupId not in", values, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidBetween(String value1, String value2) {
            addCriterion("IndCode_GroupId between", value1, value2, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodeGroupidNotBetween(String value1, String value2) {
            addCriterion("IndCode_GroupId not between", value1, value2, "indcodeGroupid");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateIsNull() {
            addCriterion("IndCode_PointRate is null");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateIsNotNull() {
            addCriterion("IndCode_PointRate is not null");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateEqualTo(String value) {
            addCriterion("IndCode_PointRate =", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateNotEqualTo(String value) {
            addCriterion("IndCode_PointRate <>", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateGreaterThan(String value) {
            addCriterion("IndCode_PointRate >", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateGreaterThanOrEqualTo(String value) {
            addCriterion("IndCode_PointRate >=", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateLessThan(String value) {
            addCriterion("IndCode_PointRate <", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateLessThanOrEqualTo(String value) {
            addCriterion("IndCode_PointRate <=", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateLike(String value) {
            addCriterion("IndCode_PointRate like", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateNotLike(String value) {
            addCriterion("IndCode_PointRate not like", value, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateIn(List<String> values) {
            addCriterion("IndCode_PointRate in", values, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateNotIn(List<String> values) {
            addCriterion("IndCode_PointRate not in", values, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateBetween(String value1, String value2) {
            addCriterion("IndCode_PointRate between", value1, value2, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andIndcodePointrateNotBetween(String value1, String value2) {
            addCriterion("IndCode_PointRate not between", value1, value2, "indcodePointrate");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagIsNull() {
            addCriterion("UnUse_Flag is null");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagIsNotNull() {
            addCriterion("UnUse_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagEqualTo(String value) {
            addCriterion("UnUse_Flag =", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagNotEqualTo(String value) {
            addCriterion("UnUse_Flag <>", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagGreaterThan(String value) {
            addCriterion("UnUse_Flag >", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagGreaterThanOrEqualTo(String value) {
            addCriterion("UnUse_Flag >=", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagLessThan(String value) {
            addCriterion("UnUse_Flag <", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagLessThanOrEqualTo(String value) {
            addCriterion("UnUse_Flag <=", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagLike(String value) {
            addCriterion("UnUse_Flag like", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagNotLike(String value) {
            addCriterion("UnUse_Flag not like", value, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagIn(List<String> values) {
            addCriterion("UnUse_Flag in", values, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagNotIn(List<String> values) {
            addCriterion("UnUse_Flag not in", values, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagBetween(String value1, String value2) {
            addCriterion("UnUse_Flag between", value1, value2, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andUnuseFlagNotBetween(String value1, String value2) {
            addCriterion("UnUse_Flag not between", value1, value2, "unuseFlag");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcIsNull() {
            addCriterion("CustomerNO_NC is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcIsNotNull() {
            addCriterion("CustomerNO_NC is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcEqualTo(String value) {
            addCriterion("CustomerNO_NC =", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcNotEqualTo(String value) {
            addCriterion("CustomerNO_NC <>", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcGreaterThan(String value) {
            addCriterion("CustomerNO_NC >", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerNO_NC >=", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcLessThan(String value) {
            addCriterion("CustomerNO_NC <", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcLessThanOrEqualTo(String value) {
            addCriterion("CustomerNO_NC <=", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcLike(String value) {
            addCriterion("CustomerNO_NC like", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcNotLike(String value) {
            addCriterion("CustomerNO_NC not like", value, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcIn(List<String> values) {
            addCriterion("CustomerNO_NC in", values, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcNotIn(List<String> values) {
            addCriterion("CustomerNO_NC not in", values, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcBetween(String value1, String value2) {
            addCriterion("CustomerNO_NC between", value1, value2, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andCustomernoNcNotBetween(String value1, String value2) {
            addCriterion("CustomerNO_NC not between", value1, value2, "customernoNc");
            return (Criteria) this;
        }

        public Criteria andRisktipIsNull() {
            addCriterion("RiskTip is null");
            return (Criteria) this;
        }

        public Criteria andRisktipIsNotNull() {
            addCriterion("RiskTip is not null");
            return (Criteria) this;
        }

        public Criteria andRisktipEqualTo(String value) {
            addCriterion("RiskTip =", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipNotEqualTo(String value) {
            addCriterion("RiskTip <>", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipGreaterThan(String value) {
            addCriterion("RiskTip >", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipGreaterThanOrEqualTo(String value) {
            addCriterion("RiskTip >=", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipLessThan(String value) {
            addCriterion("RiskTip <", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipLessThanOrEqualTo(String value) {
            addCriterion("RiskTip <=", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipLike(String value) {
            addCriterion("RiskTip like", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipNotLike(String value) {
            addCriterion("RiskTip not like", value, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipIn(List<String> values) {
            addCriterion("RiskTip in", values, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipNotIn(List<String> values) {
            addCriterion("RiskTip not in", values, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipBetween(String value1, String value2) {
            addCriterion("RiskTip between", value1, value2, "risktip");
            return (Criteria) this;
        }

        public Criteria andRisktipNotBetween(String value1, String value2) {
            addCriterion("RiskTip not between", value1, value2, "risktip");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionIsNull() {
            addCriterion("SurvivalStatusDescription is null");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionIsNotNull() {
            addCriterion("SurvivalStatusDescription is not null");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionEqualTo(String value) {
            addCriterion("SurvivalStatusDescription =", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionNotEqualTo(String value) {
            addCriterion("SurvivalStatusDescription <>", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionGreaterThan(String value) {
            addCriterion("SurvivalStatusDescription >", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("SurvivalStatusDescription >=", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionLessThan(String value) {
            addCriterion("SurvivalStatusDescription <", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionLessThanOrEqualTo(String value) {
            addCriterion("SurvivalStatusDescription <=", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionLike(String value) {
            addCriterion("SurvivalStatusDescription like", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionNotLike(String value) {
            addCriterion("SurvivalStatusDescription not like", value, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionIn(List<String> values) {
            addCriterion("SurvivalStatusDescription in", values, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionNotIn(List<String> values) {
            addCriterion("SurvivalStatusDescription not in", values, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionBetween(String value1, String value2) {
            addCriterion("SurvivalStatusDescription between", value1, value2, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andSurvivalstatusdescriptionNotBetween(String value1, String value2) {
            addCriterion("SurvivalStatusDescription not between", value1, value2, "survivalstatusdescription");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalIsNull() {
            addCriterion("RegisteredCapital is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalIsNotNull() {
            addCriterion("RegisteredCapital is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalEqualTo(String value) {
            addCriterion("RegisteredCapital =", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalNotEqualTo(String value) {
            addCriterion("RegisteredCapital <>", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalGreaterThan(String value) {
            addCriterion("RegisteredCapital >", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalGreaterThanOrEqualTo(String value) {
            addCriterion("RegisteredCapital >=", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalLessThan(String value) {
            addCriterion("RegisteredCapital <", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalLessThanOrEqualTo(String value) {
            addCriterion("RegisteredCapital <=", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalLike(String value) {
            addCriterion("RegisteredCapital like", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalNotLike(String value) {
            addCriterion("RegisteredCapital not like", value, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalIn(List<String> values) {
            addCriterion("RegisteredCapital in", values, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalNotIn(List<String> values) {
            addCriterion("RegisteredCapital not in", values, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalBetween(String value1, String value2) {
            addCriterion("RegisteredCapital between", value1, value2, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredcapitalNotBetween(String value1, String value2) {
            addCriterion("RegisteredCapital not between", value1, value2, "registeredcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalIsNull() {
            addCriterion("ContributedCapital is null");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalIsNotNull() {
            addCriterion("ContributedCapital is not null");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalEqualTo(String value) {
            addCriterion("ContributedCapital =", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalNotEqualTo(String value) {
            addCriterion("ContributedCapital <>", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalGreaterThan(String value) {
            addCriterion("ContributedCapital >", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalGreaterThanOrEqualTo(String value) {
            addCriterion("ContributedCapital >=", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalLessThan(String value) {
            addCriterion("ContributedCapital <", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalLessThanOrEqualTo(String value) {
            addCriterion("ContributedCapital <=", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalLike(String value) {
            addCriterion("ContributedCapital like", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalNotLike(String value) {
            addCriterion("ContributedCapital not like", value, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalIn(List<String> values) {
            addCriterion("ContributedCapital in", values, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalNotIn(List<String> values) {
            addCriterion("ContributedCapital not in", values, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalBetween(String value1, String value2) {
            addCriterion("ContributedCapital between", value1, value2, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andContributedcapitalNotBetween(String value1, String value2) {
            addCriterion("ContributedCapital not between", value1, value2, "contributedcapital");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionIsNull() {
            addCriterion("CompanyTypeDescription is null");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionIsNotNull() {
            addCriterion("CompanyTypeDescription is not null");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionEqualTo(String value) {
            addCriterion("CompanyTypeDescription =", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionNotEqualTo(String value) {
            addCriterion("CompanyTypeDescription <>", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionGreaterThan(String value) {
            addCriterion("CompanyTypeDescription >", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyTypeDescription >=", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionLessThan(String value) {
            addCriterion("CompanyTypeDescription <", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionLessThanOrEqualTo(String value) {
            addCriterion("CompanyTypeDescription <=", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionLike(String value) {
            addCriterion("CompanyTypeDescription like", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionNotLike(String value) {
            addCriterion("CompanyTypeDescription not like", value, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionIn(List<String> values) {
            addCriterion("CompanyTypeDescription in", values, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionNotIn(List<String> values) {
            addCriterion("CompanyTypeDescription not in", values, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionBetween(String value1, String value2) {
            addCriterion("CompanyTypeDescription between", value1, value2, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andCompanytypedescriptionNotBetween(String value1, String value2) {
            addCriterion("CompanyTypeDescription not between", value1, value2, "companytypedescription");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeIsNull() {
            addCriterion("MarketCategoryCode is null");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeIsNotNull() {
            addCriterion("MarketCategoryCode is not null");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeEqualTo(String value) {
            addCriterion("MarketCategoryCode =", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeNotEqualTo(String value) {
            addCriterion("MarketCategoryCode <>", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeGreaterThan(String value) {
            addCriterion("MarketCategoryCode >", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeGreaterThanOrEqualTo(String value) {
            addCriterion("MarketCategoryCode >=", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeLessThan(String value) {
            addCriterion("MarketCategoryCode <", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeLessThanOrEqualTo(String value) {
            addCriterion("MarketCategoryCode <=", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeLike(String value) {
            addCriterion("MarketCategoryCode like", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeNotLike(String value) {
            addCriterion("MarketCategoryCode not like", value, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeIn(List<String> values) {
            addCriterion("MarketCategoryCode in", values, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeNotIn(List<String> values) {
            addCriterion("MarketCategoryCode not in", values, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeBetween(String value1, String value2) {
            addCriterion("MarketCategoryCode between", value1, value2, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andMarketcategorycodeNotBetween(String value1, String value2) {
            addCriterion("MarketCategoryCode not between", value1, value2, "marketcategorycode");
            return (Criteria) this;
        }

        public Criteria andHlcodeIsNull() {
            addCriterion("HLCode is null");
            return (Criteria) this;
        }

        public Criteria andHlcodeIsNotNull() {
            addCriterion("HLCode is not null");
            return (Criteria) this;
        }

        public Criteria andHlcodeEqualTo(String value) {
            addCriterion("HLCode =", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotEqualTo(String value) {
            addCriterion("HLCode <>", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeGreaterThan(String value) {
            addCriterion("HLCode >", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeGreaterThanOrEqualTo(String value) {
            addCriterion("HLCode >=", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLessThan(String value) {
            addCriterion("HLCode <", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLessThanOrEqualTo(String value) {
            addCriterion("HLCode <=", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeLike(String value) {
            addCriterion("HLCode like", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotLike(String value) {
            addCriterion("HLCode not like", value, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeIn(List<String> values) {
            addCriterion("HLCode in", values, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotIn(List<String> values) {
            addCriterion("HLCode not in", values, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeBetween(String value1, String value2) {
            addCriterion("HLCode between", value1, value2, "hlcode");
            return (Criteria) this;
        }

        public Criteria andHlcodeNotBetween(String value1, String value2) {
            addCriterion("HLCode not between", value1, value2, "hlcode");
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

        public Criteria andSmcgroupidIsNull() {
            addCriterion("SMCGroupID is null");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidIsNotNull() {
            addCriterion("SMCGroupID is not null");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidEqualTo(String value) {
            addCriterion("SMCGroupID =", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotEqualTo(String value) {
            addCriterion("SMCGroupID <>", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidGreaterThan(String value) {
            addCriterion("SMCGroupID >", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidGreaterThanOrEqualTo(String value) {
            addCriterion("SMCGroupID >=", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLessThan(String value) {
            addCriterion("SMCGroupID <", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLessThanOrEqualTo(String value) {
            addCriterion("SMCGroupID <=", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLike(String value) {
            addCriterion("SMCGroupID like", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotLike(String value) {
            addCriterion("SMCGroupID not like", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidIn(List<String> values) {
            addCriterion("SMCGroupID in", values, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotIn(List<String> values) {
            addCriterion("SMCGroupID not in", values, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidBetween(String value1, String value2) {
            addCriterion("SMCGroupID between", value1, value2, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotBetween(String value1, String value2) {
            addCriterion("SMCGroupID not between", value1, value2, "smcgroupid");
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
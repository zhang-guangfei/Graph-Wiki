package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GzCustdlvdataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GzCustdlvdataExample() {
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

        public Criteria andSidIsNull() {
            addCriterion("SID is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("SID is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(String value) {
            addCriterion("SID =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(String value) {
            addCriterion("SID <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(String value) {
            addCriterion("SID >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(String value) {
            addCriterion("SID >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(String value) {
            addCriterion("SID <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(String value) {
            addCriterion("SID <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLike(String value) {
            addCriterion("SID like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotLike(String value) {
            addCriterion("SID not like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<String> values) {
            addCriterion("SID in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<String> values) {
            addCriterion("SID not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(String value1, String value2) {
            addCriterion("SID between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(String value1, String value2) {
            addCriterion("SID not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andAddcodeIsNull() {
            addCriterion("AddCode is null");
            return (Criteria) this;
        }

        public Criteria andAddcodeIsNotNull() {
            addCriterion("AddCode is not null");
            return (Criteria) this;
        }

        public Criteria andAddcodeEqualTo(String value) {
            addCriterion("AddCode =", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeNotEqualTo(String value) {
            addCriterion("AddCode <>", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeGreaterThan(String value) {
            addCriterion("AddCode >", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeGreaterThanOrEqualTo(String value) {
            addCriterion("AddCode >=", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeLessThan(String value) {
            addCriterion("AddCode <", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeLessThanOrEqualTo(String value) {
            addCriterion("AddCode <=", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeLike(String value) {
            addCriterion("AddCode like", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeNotLike(String value) {
            addCriterion("AddCode not like", value, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeIn(List<String> values) {
            addCriterion("AddCode in", values, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeNotIn(List<String> values) {
            addCriterion("AddCode not in", values, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeBetween(String value1, String value2) {
            addCriterion("AddCode between", value1, value2, "addcode");
            return (Criteria) this;
        }

        public Criteria andAddcodeNotBetween(String value1, String value2) {
            addCriterion("AddCode not between", value1, value2, "addcode");
            return (Criteria) this;
        }

        public Criteria andSaddressIsNull() {
            addCriterion("SAddress is null");
            return (Criteria) this;
        }

        public Criteria andSaddressIsNotNull() {
            addCriterion("SAddress is not null");
            return (Criteria) this;
        }

        public Criteria andSaddressEqualTo(String value) {
            addCriterion("SAddress =", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotEqualTo(String value) {
            addCriterion("SAddress <>", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressGreaterThan(String value) {
            addCriterion("SAddress >", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressGreaterThanOrEqualTo(String value) {
            addCriterion("SAddress >=", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLessThan(String value) {
            addCriterion("SAddress <", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLessThanOrEqualTo(String value) {
            addCriterion("SAddress <=", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLike(String value) {
            addCriterion("SAddress like", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotLike(String value) {
            addCriterion("SAddress not like", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressIn(List<String> values) {
            addCriterion("SAddress in", values, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotIn(List<String> values) {
            addCriterion("SAddress not in", values, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressBetween(String value1, String value2) {
            addCriterion("SAddress between", value1, value2, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotBetween(String value1, String value2) {
            addCriterion("SAddress not between", value1, value2, "saddress");
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

        public Criteria andDlvaddressIsNull() {
            addCriterion("DlvAddress is null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIsNotNull() {
            addCriterion("DlvAddress is not null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressEqualTo(String value) {
            addCriterion("DlvAddress =", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotEqualTo(String value) {
            addCriterion("DlvAddress <>", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThan(String value) {
            addCriterion("DlvAddress >", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThanOrEqualTo(String value) {
            addCriterion("DlvAddress >=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThan(String value) {
            addCriterion("DlvAddress <", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThanOrEqualTo(String value) {
            addCriterion("DlvAddress <=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLike(String value) {
            addCriterion("DlvAddress like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotLike(String value) {
            addCriterion("DlvAddress not like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIn(List<String> values) {
            addCriterion("DlvAddress in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotIn(List<String> values) {
            addCriterion("DlvAddress not in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressBetween(String value1, String value2) {
            addCriterion("DlvAddress between", value1, value2, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotBetween(String value1, String value2) {
            addCriterion("DlvAddress not between", value1, value2, "dlvaddress");
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

        public Criteria andTransfeeIsNull() {
            addCriterion("TransFee is null");
            return (Criteria) this;
        }

        public Criteria andTransfeeIsNotNull() {
            addCriterion("TransFee is not null");
            return (Criteria) this;
        }

        public Criteria andTransfeeEqualTo(String value) {
            addCriterion("TransFee =", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotEqualTo(String value) {
            addCriterion("TransFee <>", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThan(String value) {
            addCriterion("TransFee >", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThanOrEqualTo(String value) {
            addCriterion("TransFee >=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThan(String value) {
            addCriterion("TransFee <", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThanOrEqualTo(String value) {
            addCriterion("TransFee <=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLike(String value) {
            addCriterion("TransFee like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotLike(String value) {
            addCriterion("TransFee not like", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeIn(List<String> values) {
            addCriterion("TransFee in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotIn(List<String> values) {
            addCriterion("TransFee not in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeBetween(String value1, String value2) {
            addCriterion("TransFee between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotBetween(String value1, String value2) {
            addCriterion("TransFee not between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNull() {
            addCriterion("Dlvtype is null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNotNull() {
            addCriterion("Dlvtype is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeEqualTo(String value) {
            addCriterion("Dlvtype =", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotEqualTo(String value) {
            addCriterion("Dlvtype <>", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThan(String value) {
            addCriterion("Dlvtype >", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("Dlvtype >=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThan(String value) {
            addCriterion("Dlvtype <", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThanOrEqualTo(String value) {
            addCriterion("Dlvtype <=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLike(String value) {
            addCriterion("Dlvtype like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotLike(String value) {
            addCriterion("Dlvtype not like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIn(List<String> values) {
            addCriterion("Dlvtype in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotIn(List<String> values) {
            addCriterion("Dlvtype not in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeBetween(String value1, String value2) {
            addCriterion("Dlvtype between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotBetween(String value1, String value2) {
            addCriterion("Dlvtype not between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("Upddate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("Upddate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("Upddate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("Upddate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("Upddate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("Upddate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("Upddate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("Upddate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("Upddate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("Upddate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("Upddate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("Upddate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("CreateDate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("CreateDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("CreateDate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("CreateDate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("CreateDate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateDate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("CreateDate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("CreateDate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("CreateDate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("CreateDate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("CreateDate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("CreateDate not between", value1, value2, "createdate");
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

        public Criteria andOptuserIsNull() {
            addCriterion("OptUser is null");
            return (Criteria) this;
        }

        public Criteria andOptuserIsNotNull() {
            addCriterion("OptUser is not null");
            return (Criteria) this;
        }

        public Criteria andOptuserEqualTo(String value) {
            addCriterion("OptUser =", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserNotEqualTo(String value) {
            addCriterion("OptUser <>", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserGreaterThan(String value) {
            addCriterion("OptUser >", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserGreaterThanOrEqualTo(String value) {
            addCriterion("OptUser >=", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserLessThan(String value) {
            addCriterion("OptUser <", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserLessThanOrEqualTo(String value) {
            addCriterion("OptUser <=", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserLike(String value) {
            addCriterion("OptUser like", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserNotLike(String value) {
            addCriterion("OptUser not like", value, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserIn(List<String> values) {
            addCriterion("OptUser in", values, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserNotIn(List<String> values) {
            addCriterion("OptUser not in", values, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserBetween(String value1, String value2) {
            addCriterion("OptUser between", value1, value2, "optuser");
            return (Criteria) this;
        }

        public Criteria andOptuserNotBetween(String value1, String value2) {
            addCriterion("OptUser not between", value1, value2, "optuser");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonIsNull() {
            addCriterion("ModifyExpressReason is null");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonIsNotNull() {
            addCriterion("ModifyExpressReason is not null");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonEqualTo(String value) {
            addCriterion("ModifyExpressReason =", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonNotEqualTo(String value) {
            addCriterion("ModifyExpressReason <>", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonGreaterThan(String value) {
            addCriterion("ModifyExpressReason >", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonGreaterThanOrEqualTo(String value) {
            addCriterion("ModifyExpressReason >=", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonLessThan(String value) {
            addCriterion("ModifyExpressReason <", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonLessThanOrEqualTo(String value) {
            addCriterion("ModifyExpressReason <=", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonLike(String value) {
            addCriterion("ModifyExpressReason like", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonNotLike(String value) {
            addCriterion("ModifyExpressReason not like", value, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonIn(List<String> values) {
            addCriterion("ModifyExpressReason in", values, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonNotIn(List<String> values) {
            addCriterion("ModifyExpressReason not in", values, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonBetween(String value1, String value2) {
            addCriterion("ModifyExpressReason between", value1, value2, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andModifyexpressreasonNotBetween(String value1, String value2) {
            addCriterion("ModifyExpressReason not between", value1, value2, "modifyexpressreason");
            return (Criteria) this;
        }

        public Criteria andAddresstypeIsNull() {
            addCriterion("AddressType is null");
            return (Criteria) this;
        }

        public Criteria andAddresstypeIsNotNull() {
            addCriterion("AddressType is not null");
            return (Criteria) this;
        }

        public Criteria andAddresstypeEqualTo(String value) {
            addCriterion("AddressType =", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeNotEqualTo(String value) {
            addCriterion("AddressType <>", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeGreaterThan(String value) {
            addCriterion("AddressType >", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeGreaterThanOrEqualTo(String value) {
            addCriterion("AddressType >=", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeLessThan(String value) {
            addCriterion("AddressType <", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeLessThanOrEqualTo(String value) {
            addCriterion("AddressType <=", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeLike(String value) {
            addCriterion("AddressType like", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeNotLike(String value) {
            addCriterion("AddressType not like", value, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeIn(List<String> values) {
            addCriterion("AddressType in", values, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeNotIn(List<String> values) {
            addCriterion("AddressType not in", values, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeBetween(String value1, String value2) {
            addCriterion("AddressType between", value1, value2, "addresstype");
            return (Criteria) this;
        }

        public Criteria andAddresstypeNotBetween(String value1, String value2) {
            addCriterion("AddressType not between", value1, value2, "addresstype");
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

        public Criteria andIsdefaultIsNull() {
            addCriterion("IsDefault is null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIsNotNull() {
            addCriterion("IsDefault is not null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultEqualTo(Boolean value) {
            addCriterion("IsDefault =", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotEqualTo(Boolean value) {
            addCriterion("IsDefault <>", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThan(Boolean value) {
            addCriterion("IsDefault >", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsDefault >=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThan(Boolean value) {
            addCriterion("IsDefault <", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThanOrEqualTo(Boolean value) {
            addCriterion("IsDefault <=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIn(List<Boolean> values) {
            addCriterion("IsDefault in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotIn(List<Boolean> values) {
            addCriterion("IsDefault not in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultBetween(Boolean value1, Boolean value2) {
            addCriterion("IsDefault between", value1, value2, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsDefault not between", value1, value2, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIscommonuseIsNull() {
            addCriterion("IsCommonUse is null");
            return (Criteria) this;
        }

        public Criteria andIscommonuseIsNotNull() {
            addCriterion("IsCommonUse is not null");
            return (Criteria) this;
        }

        public Criteria andIscommonuseEqualTo(Boolean value) {
            addCriterion("IsCommonUse =", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseNotEqualTo(Boolean value) {
            addCriterion("IsCommonUse <>", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseGreaterThan(Boolean value) {
            addCriterion("IsCommonUse >", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsCommonUse >=", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseLessThan(Boolean value) {
            addCriterion("IsCommonUse <", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseLessThanOrEqualTo(Boolean value) {
            addCriterion("IsCommonUse <=", value, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseIn(List<Boolean> values) {
            addCriterion("IsCommonUse in", values, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseNotIn(List<Boolean> values) {
            addCriterion("IsCommonUse not in", values, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseBetween(Boolean value1, Boolean value2) {
            addCriterion("IsCommonUse between", value1, value2, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIscommonuseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsCommonUse not between", value1, value2, "iscommonuse");
            return (Criteria) this;
        }

        public Criteria andIsconfirmIsNull() {
            addCriterion("IsConfirm is null");
            return (Criteria) this;
        }

        public Criteria andIsconfirmIsNotNull() {
            addCriterion("IsConfirm is not null");
            return (Criteria) this;
        }

        public Criteria andIsconfirmEqualTo(Boolean value) {
            addCriterion("IsConfirm =", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotEqualTo(Boolean value) {
            addCriterion("IsConfirm <>", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmGreaterThan(Boolean value) {
            addCriterion("IsConfirm >", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsConfirm >=", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmLessThan(Boolean value) {
            addCriterion("IsConfirm <", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmLessThanOrEqualTo(Boolean value) {
            addCriterion("IsConfirm <=", value, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmIn(List<Boolean> values) {
            addCriterion("IsConfirm in", values, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotIn(List<Boolean> values) {
            addCriterion("IsConfirm not in", values, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmBetween(Boolean value1, Boolean value2) {
            addCriterion("IsConfirm between", value1, value2, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andIsconfirmNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsConfirm not between", value1, value2, "isconfirm");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("Province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("Province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("Province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("Province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("Province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("Province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("Province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("Province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("Province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("Province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("Province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("Province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("Province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("Province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("City is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("City is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("City =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("City <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("City >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("City >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("City <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("City <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("City like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("City not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("City in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("City not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("City between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("City not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("Area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("Area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("Area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("Area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("Area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("Area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("Area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("Area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("Area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("Area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("Area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("Area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("Area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("Area not between", value1, value2, "area");
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

        public Criteria andHashcodeIsNull() {
            addCriterion("HashCode is null");
            return (Criteria) this;
        }

        public Criteria andHashcodeIsNotNull() {
            addCriterion("HashCode is not null");
            return (Criteria) this;
        }

        public Criteria andHashcodeEqualTo(String value) {
            addCriterion("HashCode =", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeNotEqualTo(String value) {
            addCriterion("HashCode <>", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeGreaterThan(String value) {
            addCriterion("HashCode >", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeGreaterThanOrEqualTo(String value) {
            addCriterion("HashCode >=", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeLessThan(String value) {
            addCriterion("HashCode <", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeLessThanOrEqualTo(String value) {
            addCriterion("HashCode <=", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeLike(String value) {
            addCriterion("HashCode like", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeNotLike(String value) {
            addCriterion("HashCode not like", value, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeIn(List<String> values) {
            addCriterion("HashCode in", values, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeNotIn(List<String> values) {
            addCriterion("HashCode not in", values, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeBetween(String value1, String value2) {
            addCriterion("HashCode between", value1, value2, "hashcode");
            return (Criteria) this;
        }

        public Criteria andHashcodeNotBetween(String value1, String value2) {
            addCriterion("HashCode not between", value1, value2, "hashcode");
            return (Criteria) this;
        }

        public Criteria andIntennoIsNull() {
            addCriterion("IntenNO is null");
            return (Criteria) this;
        }

        public Criteria andIntennoIsNotNull() {
            addCriterion("IntenNO is not null");
            return (Criteria) this;
        }

        public Criteria andIntennoEqualTo(String value) {
            addCriterion("IntenNO =", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoNotEqualTo(String value) {
            addCriterion("IntenNO <>", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoGreaterThan(String value) {
            addCriterion("IntenNO >", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoGreaterThanOrEqualTo(String value) {
            addCriterion("IntenNO >=", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoLessThan(String value) {
            addCriterion("IntenNO <", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoLessThanOrEqualTo(String value) {
            addCriterion("IntenNO <=", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoLike(String value) {
            addCriterion("IntenNO like", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoNotLike(String value) {
            addCriterion("IntenNO not like", value, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoIn(List<String> values) {
            addCriterion("IntenNO in", values, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoNotIn(List<String> values) {
            addCriterion("IntenNO not in", values, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoBetween(String value1, String value2) {
            addCriterion("IntenNO between", value1, value2, "intenno");
            return (Criteria) this;
        }

        public Criteria andIntennoNotBetween(String value1, String value2) {
            addCriterion("IntenNO not between", value1, value2, "intenno");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIsNull() {
            addCriterion("ExpDlvType is null");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIsNotNull() {
            addCriterion("ExpDlvType is not null");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeEqualTo(Integer value) {
            addCriterion("ExpDlvType =", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotEqualTo(Integer value) {
            addCriterion("ExpDlvType <>", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeGreaterThan(Integer value) {
            addCriterion("ExpDlvType >", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ExpDlvType >=", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeLessThan(Integer value) {
            addCriterion("ExpDlvType <", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeLessThanOrEqualTo(Integer value) {
            addCriterion("ExpDlvType <=", value, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeIn(List<Integer> values) {
            addCriterion("ExpDlvType in", values, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotIn(List<Integer> values) {
            addCriterion("ExpDlvType not in", values, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeBetween(Integer value1, Integer value2) {
            addCriterion("ExpDlvType between", value1, value2, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andExpdlvtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ExpDlvType not between", value1, value2, "expdlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeIsNull() {
            addCriterion("NoDlvType is null");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeIsNotNull() {
            addCriterion("NoDlvType is not null");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeEqualTo(Integer value) {
            addCriterion("NoDlvType =", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeNotEqualTo(Integer value) {
            addCriterion("NoDlvType <>", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeGreaterThan(Integer value) {
            addCriterion("NoDlvType >", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("NoDlvType >=", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeLessThan(Integer value) {
            addCriterion("NoDlvType <", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeLessThanOrEqualTo(Integer value) {
            addCriterion("NoDlvType <=", value, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeIn(List<Integer> values) {
            addCriterion("NoDlvType in", values, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeNotIn(List<Integer> values) {
            addCriterion("NoDlvType not in", values, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeBetween(Integer value1, Integer value2) {
            addCriterion("NoDlvType between", value1, value2, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andNodlvtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("NoDlvType not between", value1, value2, "nodlvtype");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairIsNull() {
            addCriterion("HeavyUpstair is null");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairIsNotNull() {
            addCriterion("HeavyUpstair is not null");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairEqualTo(Boolean value) {
            addCriterion("HeavyUpstair =", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairNotEqualTo(Boolean value) {
            addCriterion("HeavyUpstair <>", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairGreaterThan(Boolean value) {
            addCriterion("HeavyUpstair >", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairGreaterThanOrEqualTo(Boolean value) {
            addCriterion("HeavyUpstair >=", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairLessThan(Boolean value) {
            addCriterion("HeavyUpstair <", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairLessThanOrEqualTo(Boolean value) {
            addCriterion("HeavyUpstair <=", value, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairIn(List<Boolean> values) {
            addCriterion("HeavyUpstair in", values, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairNotIn(List<Boolean> values) {
            addCriterion("HeavyUpstair not in", values, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairBetween(Boolean value1, Boolean value2) {
            addCriterion("HeavyUpstair between", value1, value2, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andHeavyupstairNotBetween(Boolean value1, Boolean value2) {
            addCriterion("HeavyUpstair not between", value1, value2, "heavyupstair");
            return (Criteria) this;
        }

        public Criteria andValidateIsNull() {
            addCriterion("ValiDate is null");
            return (Criteria) this;
        }

        public Criteria andValidateIsNotNull() {
            addCriterion("ValiDate is not null");
            return (Criteria) this;
        }

        public Criteria andValidateEqualTo(Date value) {
            addCriterion("ValiDate =", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateNotEqualTo(Date value) {
            addCriterion("ValiDate <>", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateGreaterThan(Date value) {
            addCriterion("ValiDate >", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateGreaterThanOrEqualTo(Date value) {
            addCriterion("ValiDate >=", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateLessThan(Date value) {
            addCriterion("ValiDate <", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateLessThanOrEqualTo(Date value) {
            addCriterion("ValiDate <=", value, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateIn(List<Date> values) {
            addCriterion("ValiDate in", values, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateNotIn(List<Date> values) {
            addCriterion("ValiDate not in", values, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateBetween(Date value1, Date value2) {
            addCriterion("ValiDate between", value1, value2, "validate");
            return (Criteria) this;
        }

        public Criteria andValidateNotBetween(Date value1, Date value2) {
            addCriterion("ValiDate not between", value1, value2, "validate");
            return (Criteria) this;
        }

        public Criteria andEtimesIsNull() {
            addCriterion("Etimes is null");
            return (Criteria) this;
        }

        public Criteria andEtimesIsNotNull() {
            addCriterion("Etimes is not null");
            return (Criteria) this;
        }

        public Criteria andEtimesEqualTo(Short value) {
            addCriterion("Etimes =", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesNotEqualTo(Short value) {
            addCriterion("Etimes <>", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesGreaterThan(Short value) {
            addCriterion("Etimes >", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesGreaterThanOrEqualTo(Short value) {
            addCriterion("Etimes >=", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesLessThan(Short value) {
            addCriterion("Etimes <", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesLessThanOrEqualTo(Short value) {
            addCriterion("Etimes <=", value, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesIn(List<Short> values) {
            addCriterion("Etimes in", values, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesNotIn(List<Short> values) {
            addCriterion("Etimes not in", values, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesBetween(Short value1, Short value2) {
            addCriterion("Etimes between", value1, value2, "etimes");
            return (Criteria) this;
        }

        public Criteria andEtimesNotBetween(Short value1, Short value2) {
            addCriterion("Etimes not between", value1, value2, "etimes");
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
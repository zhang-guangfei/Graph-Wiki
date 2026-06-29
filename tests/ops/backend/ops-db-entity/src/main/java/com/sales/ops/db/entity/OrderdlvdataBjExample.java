package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderdlvdataBjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderdlvdataBjExample() {
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

        public Criteria andRordernoIsNull() {
            addCriterion("rorderno is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("rorderno is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("rorderno =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("rorderno <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("rorderno >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("rorderno >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("rorderno <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("rorderno <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("rorderno like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("rorderno not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("rorderno in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("rorderno not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("rorderno between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("rorderno not between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerno is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerno is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerno =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerno <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerno >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerno >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerno <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerno <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerno like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerno not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerno in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerno not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerno between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerno not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNull() {
            addCriterion("cstmname is null");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNotNull() {
            addCriterion("cstmname is not null");
            return (Criteria) this;
        }

        public Criteria andCstmnameEqualTo(String value) {
            addCriterion("cstmname =", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotEqualTo(String value) {
            addCriterion("cstmname <>", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThan(String value) {
            addCriterion("cstmname >", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThanOrEqualTo(String value) {
            addCriterion("cstmname >=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThan(String value) {
            addCriterion("cstmname <", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThanOrEqualTo(String value) {
            addCriterion("cstmname <=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLike(String value) {
            addCriterion("cstmname like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotLike(String value) {
            addCriterion("cstmname not like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameIn(List<String> values) {
            addCriterion("cstmname in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotIn(List<String> values) {
            addCriterion("cstmname not in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameBetween(String value1, String value2) {
            addCriterion("cstmname between", value1, value2, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotBetween(String value1, String value2) {
            addCriterion("cstmname not between", value1, value2, "cstmname");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIsNull() {
            addCriterion("dlvaddress is null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIsNotNull() {
            addCriterion("dlvaddress is not null");
            return (Criteria) this;
        }

        public Criteria andDlvaddressEqualTo(String value) {
            addCriterion("dlvaddress =", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotEqualTo(String value) {
            addCriterion("dlvaddress <>", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThan(String value) {
            addCriterion("dlvaddress >", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressGreaterThanOrEqualTo(String value) {
            addCriterion("dlvaddress >=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThan(String value) {
            addCriterion("dlvaddress <", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLessThanOrEqualTo(String value) {
            addCriterion("dlvaddress <=", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressLike(String value) {
            addCriterion("dlvaddress like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotLike(String value) {
            addCriterion("dlvaddress not like", value, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressIn(List<String> values) {
            addCriterion("dlvaddress in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotIn(List<String> values) {
            addCriterion("dlvaddress not in", values, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressBetween(String value1, String value2) {
            addCriterion("dlvaddress between", value1, value2, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andDlvaddressNotBetween(String value1, String value2) {
            addCriterion("dlvaddress not between", value1, value2, "dlvaddress");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNull() {
            addCriterion("contactPsn is null");
            return (Criteria) this;
        }

        public Criteria andContactpsnIsNotNull() {
            addCriterion("contactPsn is not null");
            return (Criteria) this;
        }

        public Criteria andContactpsnEqualTo(String value) {
            addCriterion("contactPsn =", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotEqualTo(String value) {
            addCriterion("contactPsn <>", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThan(String value) {
            addCriterion("contactPsn >", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnGreaterThanOrEqualTo(String value) {
            addCriterion("contactPsn >=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThan(String value) {
            addCriterion("contactPsn <", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLessThanOrEqualTo(String value) {
            addCriterion("contactPsn <=", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnLike(String value) {
            addCriterion("contactPsn like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotLike(String value) {
            addCriterion("contactPsn not like", value, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnIn(List<String> values) {
            addCriterion("contactPsn in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotIn(List<String> values) {
            addCriterion("contactPsn not in", values, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnBetween(String value1, String value2) {
            addCriterion("contactPsn between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andContactpsnNotBetween(String value1, String value2) {
            addCriterion("contactPsn not between", value1, value2, "contactpsn");
            return (Criteria) this;
        }

        public Criteria andTelenoIsNull() {
            addCriterion("teleNo is null");
            return (Criteria) this;
        }

        public Criteria andTelenoIsNotNull() {
            addCriterion("teleNo is not null");
            return (Criteria) this;
        }

        public Criteria andTelenoEqualTo(String value) {
            addCriterion("teleNo =", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotEqualTo(String value) {
            addCriterion("teleNo <>", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoGreaterThan(String value) {
            addCriterion("teleNo >", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoGreaterThanOrEqualTo(String value) {
            addCriterion("teleNo >=", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLessThan(String value) {
            addCriterion("teleNo <", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLessThanOrEqualTo(String value) {
            addCriterion("teleNo <=", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoLike(String value) {
            addCriterion("teleNo like", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotLike(String value) {
            addCriterion("teleNo not like", value, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoIn(List<String> values) {
            addCriterion("teleNo in", values, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotIn(List<String> values) {
            addCriterion("teleNo not in", values, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoBetween(String value1, String value2) {
            addCriterion("teleNo between", value1, value2, "teleno");
            return (Criteria) this;
        }

        public Criteria andTelenoNotBetween(String value1, String value2) {
            addCriterion("teleNo not between", value1, value2, "teleno");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNull() {
            addCriterion("postcode is null");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNotNull() {
            addCriterion("postcode is not null");
            return (Criteria) this;
        }

        public Criteria andPostcodeEqualTo(String value) {
            addCriterion("postcode =", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotEqualTo(String value) {
            addCriterion("postcode <>", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThan(String value) {
            addCriterion("postcode >", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("postcode >=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThan(String value) {
            addCriterion("postcode <", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThanOrEqualTo(String value) {
            addCriterion("postcode <=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLike(String value) {
            addCriterion("postcode like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotLike(String value) {
            addCriterion("postcode not like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIn(List<String> values) {
            addCriterion("postcode in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotIn(List<String> values) {
            addCriterion("postcode not in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeBetween(String value1, String value2) {
            addCriterion("postcode between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotBetween(String value1, String value2) {
            addCriterion("postcode not between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNull() {
            addCriterion("dlvtype is null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNotNull() {
            addCriterion("dlvtype is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeEqualTo(String value) {
            addCriterion("dlvtype =", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotEqualTo(String value) {
            addCriterion("dlvtype <>", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThan(String value) {
            addCriterion("dlvtype >", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("dlvtype >=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThan(String value) {
            addCriterion("dlvtype <", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThanOrEqualTo(String value) {
            addCriterion("dlvtype <=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLike(String value) {
            addCriterion("dlvtype like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotLike(String value) {
            addCriterion("dlvtype not like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIn(List<String> values) {
            addCriterion("dlvtype in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotIn(List<String> values) {
            addCriterion("dlvtype not in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeBetween(String value1, String value2) {
            addCriterion("dlvtype between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotBetween(String value1, String value2) {
            addCriterion("dlvtype not between", value1, value2, "dlvtype");
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

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andArriveddateIsNull() {
            addCriterion("arriveddate is null");
            return (Criteria) this;
        }

        public Criteria andArriveddateIsNotNull() {
            addCriterion("arriveddate is not null");
            return (Criteria) this;
        }

        public Criteria andArriveddateEqualTo(Date value) {
            addCriterion("arriveddate =", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateNotEqualTo(Date value) {
            addCriterion("arriveddate <>", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateGreaterThan(Date value) {
            addCriterion("arriveddate >", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateGreaterThanOrEqualTo(Date value) {
            addCriterion("arriveddate >=", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateLessThan(Date value) {
            addCriterion("arriveddate <", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateLessThanOrEqualTo(Date value) {
            addCriterion("arriveddate <=", value, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateIn(List<Date> values) {
            addCriterion("arriveddate in", values, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateNotIn(List<Date> values) {
            addCriterion("arriveddate not in", values, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateBetween(Date value1, Date value2) {
            addCriterion("arriveddate between", value1, value2, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andArriveddateNotBetween(Date value1, Date value2) {
            addCriterion("arriveddate not between", value1, value2, "arriveddate");
            return (Criteria) this;
        }

        public Criteria andPreflagIsNull() {
            addCriterion("preflag is null");
            return (Criteria) this;
        }

        public Criteria andPreflagIsNotNull() {
            addCriterion("preflag is not null");
            return (Criteria) this;
        }

        public Criteria andPreflagEqualTo(String value) {
            addCriterion("preflag =", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagNotEqualTo(String value) {
            addCriterion("preflag <>", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagGreaterThan(String value) {
            addCriterion("preflag >", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagGreaterThanOrEqualTo(String value) {
            addCriterion("preflag >=", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagLessThan(String value) {
            addCriterion("preflag <", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagLessThanOrEqualTo(String value) {
            addCriterion("preflag <=", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagLike(String value) {
            addCriterion("preflag like", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagNotLike(String value) {
            addCriterion("preflag not like", value, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagIn(List<String> values) {
            addCriterion("preflag in", values, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagNotIn(List<String> values) {
            addCriterion("preflag not in", values, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagBetween(String value1, String value2) {
            addCriterion("preflag between", value1, value2, "preflag");
            return (Criteria) this;
        }

        public Criteria andPreflagNotBetween(String value1, String value2) {
            addCriterion("preflag not between", value1, value2, "preflag");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidIsNull() {
            addCriterion("editEmployeeid is null");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidIsNotNull() {
            addCriterion("editEmployeeid is not null");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidEqualTo(String value) {
            addCriterion("editEmployeeid =", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidNotEqualTo(String value) {
            addCriterion("editEmployeeid <>", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidGreaterThan(String value) {
            addCriterion("editEmployeeid >", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidGreaterThanOrEqualTo(String value) {
            addCriterion("editEmployeeid >=", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidLessThan(String value) {
            addCriterion("editEmployeeid <", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidLessThanOrEqualTo(String value) {
            addCriterion("editEmployeeid <=", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidLike(String value) {
            addCriterion("editEmployeeid like", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidNotLike(String value) {
            addCriterion("editEmployeeid not like", value, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidIn(List<String> values) {
            addCriterion("editEmployeeid in", values, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidNotIn(List<String> values) {
            addCriterion("editEmployeeid not in", values, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidBetween(String value1, String value2) {
            addCriterion("editEmployeeid between", value1, value2, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andEditemployeeidNotBetween(String value1, String value2) {
            addCriterion("editEmployeeid not between", value1, value2, "editemployeeid");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("upddate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("upddate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("upddate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("upddate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("upddate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("upddate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("upddate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("upddate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("upddate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("upddate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("upddate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("upddate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andDlvflagIsNull() {
            addCriterion("dlvflag is null");
            return (Criteria) this;
        }

        public Criteria andDlvflagIsNotNull() {
            addCriterion("dlvflag is not null");
            return (Criteria) this;
        }

        public Criteria andDlvflagEqualTo(String value) {
            addCriterion("dlvflag =", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotEqualTo(String value) {
            addCriterion("dlvflag <>", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagGreaterThan(String value) {
            addCriterion("dlvflag >", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagGreaterThanOrEqualTo(String value) {
            addCriterion("dlvflag >=", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLessThan(String value) {
            addCriterion("dlvflag <", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLessThanOrEqualTo(String value) {
            addCriterion("dlvflag <=", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagLike(String value) {
            addCriterion("dlvflag like", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotLike(String value) {
            addCriterion("dlvflag not like", value, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagIn(List<String> values) {
            addCriterion("dlvflag in", values, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotIn(List<String> values) {
            addCriterion("dlvflag not in", values, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagBetween(String value1, String value2) {
            addCriterion("dlvflag between", value1, value2, "dlvflag");
            return (Criteria) this;
        }

        public Criteria andDlvflagNotBetween(String value1, String value2) {
            addCriterion("dlvflag not between", value1, value2, "dlvflag");
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
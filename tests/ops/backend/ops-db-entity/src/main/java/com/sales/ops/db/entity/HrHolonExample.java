package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class HrHolonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HrHolonExample() {
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

        public Criteria andCompanyIsNull() {
            addCriterion("COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("COMPANY =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("COMPANY <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("COMPANY >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("COMPANY >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("COMPANY <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("COMPANY <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("COMPANY like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("COMPANY not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("COMPANY in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("COMPANY not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("COMPANY between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("COMPANY not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNull() {
            addCriterion("COMPANYCODE is null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNotNull() {
            addCriterion("COMPANYCODE is not null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeEqualTo(String value) {
            addCriterion("COMPANYCODE =", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotEqualTo(String value) {
            addCriterion("COMPANYCODE <>", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThan(String value) {
            addCriterion("COMPANYCODE >", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThanOrEqualTo(String value) {
            addCriterion("COMPANYCODE >=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThan(String value) {
            addCriterion("COMPANYCODE <", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThanOrEqualTo(String value) {
            addCriterion("COMPANYCODE <=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLike(String value) {
            addCriterion("COMPANYCODE like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotLike(String value) {
            addCriterion("COMPANYCODE not like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIn(List<String> values) {
            addCriterion("COMPANYCODE in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotIn(List<String> values) {
            addCriterion("COMPANYCODE not in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeBetween(String value1, String value2) {
            addCriterion("COMPANYCODE between", value1, value2, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotBetween(String value1, String value2) {
            addCriterion("COMPANYCODE not between", value1, value2, "companycode");
            return (Criteria) this;
        }

        public Criteria andBenbuIsNull() {
            addCriterion("Benbu is null");
            return (Criteria) this;
        }

        public Criteria andBenbuIsNotNull() {
            addCriterion("Benbu is not null");
            return (Criteria) this;
        }

        public Criteria andBenbuEqualTo(String value) {
            addCriterion("Benbu =", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuNotEqualTo(String value) {
            addCriterion("Benbu <>", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuGreaterThan(String value) {
            addCriterion("Benbu >", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuGreaterThanOrEqualTo(String value) {
            addCriterion("Benbu >=", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuLessThan(String value) {
            addCriterion("Benbu <", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuLessThanOrEqualTo(String value) {
            addCriterion("Benbu <=", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuLike(String value) {
            addCriterion("Benbu like", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuNotLike(String value) {
            addCriterion("Benbu not like", value, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuIn(List<String> values) {
            addCriterion("Benbu in", values, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuNotIn(List<String> values) {
            addCriterion("Benbu not in", values, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuBetween(String value1, String value2) {
            addCriterion("Benbu between", value1, value2, "benbu");
            return (Criteria) this;
        }

        public Criteria andBenbuNotBetween(String value1, String value2) {
            addCriterion("Benbu not between", value1, value2, "benbu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuIsNull() {
            addCriterion("dept_erjibu is null");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuIsNotNull() {
            addCriterion("dept_erjibu is not null");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuEqualTo(String value) {
            addCriterion("dept_erjibu =", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuNotEqualTo(String value) {
            addCriterion("dept_erjibu <>", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuGreaterThan(String value) {
            addCriterion("dept_erjibu >", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuGreaterThanOrEqualTo(String value) {
            addCriterion("dept_erjibu >=", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuLessThan(String value) {
            addCriterion("dept_erjibu <", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuLessThanOrEqualTo(String value) {
            addCriterion("dept_erjibu <=", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuLike(String value) {
            addCriterion("dept_erjibu like", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuNotLike(String value) {
            addCriterion("dept_erjibu not like", value, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuIn(List<String> values) {
            addCriterion("dept_erjibu in", values, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuNotIn(List<String> values) {
            addCriterion("dept_erjibu not in", values, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuBetween(String value1, String value2) {
            addCriterion("dept_erjibu between", value1, value2, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibuNotBetween(String value1, String value2) {
            addCriterion("dept_erjibu not between", value1, value2, "deptErjibu");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeIsNull() {
            addCriterion("dept_erjibuCode is null");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeIsNotNull() {
            addCriterion("dept_erjibuCode is not null");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeEqualTo(String value) {
            addCriterion("dept_erjibuCode =", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeNotEqualTo(String value) {
            addCriterion("dept_erjibuCode <>", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeGreaterThan(String value) {
            addCriterion("dept_erjibuCode >", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeGreaterThanOrEqualTo(String value) {
            addCriterion("dept_erjibuCode >=", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeLessThan(String value) {
            addCriterion("dept_erjibuCode <", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeLessThanOrEqualTo(String value) {
            addCriterion("dept_erjibuCode <=", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeLike(String value) {
            addCriterion("dept_erjibuCode like", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeNotLike(String value) {
            addCriterion("dept_erjibuCode not like", value, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeIn(List<String> values) {
            addCriterion("dept_erjibuCode in", values, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeNotIn(List<String> values) {
            addCriterion("dept_erjibuCode not in", values, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeBetween(String value1, String value2) {
            addCriterion("dept_erjibuCode between", value1, value2, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andDeptErjibucodeNotBetween(String value1, String value2) {
            addCriterion("dept_erjibuCode not between", value1, value2, "deptErjibucode");
            return (Criteria) this;
        }

        public Criteria andFnumberIsNull() {
            addCriterion("FNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andFnumberIsNotNull() {
            addCriterion("FNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andFnumberEqualTo(String value) {
            addCriterion("FNUMBER =", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberNotEqualTo(String value) {
            addCriterion("FNUMBER <>", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberGreaterThan(String value) {
            addCriterion("FNUMBER >", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberGreaterThanOrEqualTo(String value) {
            addCriterion("FNUMBER >=", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberLessThan(String value) {
            addCriterion("FNUMBER <", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberLessThanOrEqualTo(String value) {
            addCriterion("FNUMBER <=", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberLike(String value) {
            addCriterion("FNUMBER like", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberNotLike(String value) {
            addCriterion("FNUMBER not like", value, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberIn(List<String> values) {
            addCriterion("FNUMBER in", values, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberNotIn(List<String> values) {
            addCriterion("FNUMBER not in", values, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberBetween(String value1, String value2) {
            addCriterion("FNUMBER between", value1, value2, "fnumber");
            return (Criteria) this;
        }

        public Criteria andFnumberNotBetween(String value1, String value2) {
            addCriterion("FNUMBER not between", value1, value2, "fnumber");
            return (Criteria) this;
        }

        public Criteria andDaquIsNull() {
            addCriterion("Daqu is null");
            return (Criteria) this;
        }

        public Criteria andDaquIsNotNull() {
            addCriterion("Daqu is not null");
            return (Criteria) this;
        }

        public Criteria andDaquEqualTo(String value) {
            addCriterion("Daqu =", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquNotEqualTo(String value) {
            addCriterion("Daqu <>", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquGreaterThan(String value) {
            addCriterion("Daqu >", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquGreaterThanOrEqualTo(String value) {
            addCriterion("Daqu >=", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquLessThan(String value) {
            addCriterion("Daqu <", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquLessThanOrEqualTo(String value) {
            addCriterion("Daqu <=", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquLike(String value) {
            addCriterion("Daqu like", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquNotLike(String value) {
            addCriterion("Daqu not like", value, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquIn(List<String> values) {
            addCriterion("Daqu in", values, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquNotIn(List<String> values) {
            addCriterion("Daqu not in", values, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquBetween(String value1, String value2) {
            addCriterion("Daqu between", value1, value2, "daqu");
            return (Criteria) this;
        }

        public Criteria andDaquNotBetween(String value1, String value2) {
            addCriterion("Daqu not between", value1, value2, "daqu");
            return (Criteria) this;
        }

        public Criteria andFdeptnameIsNull() {
            addCriterion("FDEPTNAME is null");
            return (Criteria) this;
        }

        public Criteria andFdeptnameIsNotNull() {
            addCriterion("FDEPTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andFdeptnameEqualTo(String value) {
            addCriterion("FDEPTNAME =", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameNotEqualTo(String value) {
            addCriterion("FDEPTNAME <>", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameGreaterThan(String value) {
            addCriterion("FDEPTNAME >", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameGreaterThanOrEqualTo(String value) {
            addCriterion("FDEPTNAME >=", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameLessThan(String value) {
            addCriterion("FDEPTNAME <", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameLessThanOrEqualTo(String value) {
            addCriterion("FDEPTNAME <=", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameLike(String value) {
            addCriterion("FDEPTNAME like", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameNotLike(String value) {
            addCriterion("FDEPTNAME not like", value, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameIn(List<String> values) {
            addCriterion("FDEPTNAME in", values, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameNotIn(List<String> values) {
            addCriterion("FDEPTNAME not in", values, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameBetween(String value1, String value2) {
            addCriterion("FDEPTNAME between", value1, value2, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFdeptnameNotBetween(String value1, String value2) {
            addCriterion("FDEPTNAME not between", value1, value2, "fdeptname");
            return (Criteria) this;
        }

        public Criteria andFlongnumberIsNull() {
            addCriterion("FLONGNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andFlongnumberIsNotNull() {
            addCriterion("FLONGNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andFlongnumberEqualTo(String value) {
            addCriterion("FLONGNUMBER =", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberNotEqualTo(String value) {
            addCriterion("FLONGNUMBER <>", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberGreaterThan(String value) {
            addCriterion("FLONGNUMBER >", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberGreaterThanOrEqualTo(String value) {
            addCriterion("FLONGNUMBER >=", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberLessThan(String value) {
            addCriterion("FLONGNUMBER <", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberLessThanOrEqualTo(String value) {
            addCriterion("FLONGNUMBER <=", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberLike(String value) {
            addCriterion("FLONGNUMBER like", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberNotLike(String value) {
            addCriterion("FLONGNUMBER not like", value, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberIn(List<String> values) {
            addCriterion("FLONGNUMBER in", values, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberNotIn(List<String> values) {
            addCriterion("FLONGNUMBER not in", values, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberBetween(String value1, String value2) {
            addCriterion("FLONGNUMBER between", value1, value2, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFlongnumberNotBetween(String value1, String value2) {
            addCriterion("FLONGNUMBER not between", value1, value2, "flongnumber");
            return (Criteria) this;
        }

        public Criteria andFfullnameIsNull() {
            addCriterion("FFULLNAME is null");
            return (Criteria) this;
        }

        public Criteria andFfullnameIsNotNull() {
            addCriterion("FFULLNAME is not null");
            return (Criteria) this;
        }

        public Criteria andFfullnameEqualTo(String value) {
            addCriterion("FFULLNAME =", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameNotEqualTo(String value) {
            addCriterion("FFULLNAME <>", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameGreaterThan(String value) {
            addCriterion("FFULLNAME >", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameGreaterThanOrEqualTo(String value) {
            addCriterion("FFULLNAME >=", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameLessThan(String value) {
            addCriterion("FFULLNAME <", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameLessThanOrEqualTo(String value) {
            addCriterion("FFULLNAME <=", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameLike(String value) {
            addCriterion("FFULLNAME like", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameNotLike(String value) {
            addCriterion("FFULLNAME not like", value, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameIn(List<String> values) {
            addCriterion("FFULLNAME in", values, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameNotIn(List<String> values) {
            addCriterion("FFULLNAME not in", values, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameBetween(String value1, String value2) {
            addCriterion("FFULLNAME between", value1, value2, "ffullname");
            return (Criteria) this;
        }

        public Criteria andFfullnameNotBetween(String value1, String value2) {
            addCriterion("FFULLNAME not between", value1, value2, "ffullname");
            return (Criteria) this;
        }

        public Criteria andParentfnumberIsNull() {
            addCriterion("PARENTFNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andParentfnumberIsNotNull() {
            addCriterion("PARENTFNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andParentfnumberEqualTo(String value) {
            addCriterion("PARENTFNUMBER =", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberNotEqualTo(String value) {
            addCriterion("PARENTFNUMBER <>", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberGreaterThan(String value) {
            addCriterion("PARENTFNUMBER >", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberGreaterThanOrEqualTo(String value) {
            addCriterion("PARENTFNUMBER >=", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberLessThan(String value) {
            addCriterion("PARENTFNUMBER <", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberLessThanOrEqualTo(String value) {
            addCriterion("PARENTFNUMBER <=", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberLike(String value) {
            addCriterion("PARENTFNUMBER like", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberNotLike(String value) {
            addCriterion("PARENTFNUMBER not like", value, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberIn(List<String> values) {
            addCriterion("PARENTFNUMBER in", values, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberNotIn(List<String> values) {
            addCriterion("PARENTFNUMBER not in", values, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberBetween(String value1, String value2) {
            addCriterion("PARENTFNUMBER between", value1, value2, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnumberNotBetween(String value1, String value2) {
            addCriterion("PARENTFNUMBER not between", value1, value2, "parentfnumber");
            return (Criteria) this;
        }

        public Criteria andParentfnameIsNull() {
            addCriterion("PARENTFNAME is null");
            return (Criteria) this;
        }

        public Criteria andParentfnameIsNotNull() {
            addCriterion("PARENTFNAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentfnameEqualTo(String value) {
            addCriterion("PARENTFNAME =", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameNotEqualTo(String value) {
            addCriterion("PARENTFNAME <>", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameGreaterThan(String value) {
            addCriterion("PARENTFNAME >", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENTFNAME >=", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameLessThan(String value) {
            addCriterion("PARENTFNAME <", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameLessThanOrEqualTo(String value) {
            addCriterion("PARENTFNAME <=", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameLike(String value) {
            addCriterion("PARENTFNAME like", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameNotLike(String value) {
            addCriterion("PARENTFNAME not like", value, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameIn(List<String> values) {
            addCriterion("PARENTFNAME in", values, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameNotIn(List<String> values) {
            addCriterion("PARENTFNAME not in", values, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameBetween(String value1, String value2) {
            addCriterion("PARENTFNAME between", value1, value2, "parentfname");
            return (Criteria) this;
        }

        public Criteria andParentfnameNotBetween(String value1, String value2) {
            addCriterion("PARENTFNAME not between", value1, value2, "parentfname");
            return (Criteria) this;
        }

        public Criteria andDeptcodeIsNull() {
            addCriterion("DEPTCODE is null");
            return (Criteria) this;
        }

        public Criteria andDeptcodeIsNotNull() {
            addCriterion("DEPTCODE is not null");
            return (Criteria) this;
        }

        public Criteria andDeptcodeEqualTo(String value) {
            addCriterion("DEPTCODE =", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotEqualTo(String value) {
            addCriterion("DEPTCODE <>", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeGreaterThan(String value) {
            addCriterion("DEPTCODE >", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("DEPTCODE >=", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLessThan(String value) {
            addCriterion("DEPTCODE <", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLessThanOrEqualTo(String value) {
            addCriterion("DEPTCODE <=", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeLike(String value) {
            addCriterion("DEPTCODE like", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotLike(String value) {
            addCriterion("DEPTCODE not like", value, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeIn(List<String> values) {
            addCriterion("DEPTCODE in", values, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotIn(List<String> values) {
            addCriterion("DEPTCODE not in", values, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeBetween(String value1, String value2) {
            addCriterion("DEPTCODE between", value1, value2, "deptcode");
            return (Criteria) this;
        }

        public Criteria andDeptcodeNotBetween(String value1, String value2) {
            addCriterion("DEPTCODE not between", value1, value2, "deptcode");
            return (Criteria) this;
        }

        public Criteria andUnitlevelIsNull() {
            addCriterion("UNITLEVEL is null");
            return (Criteria) this;
        }

        public Criteria andUnitlevelIsNotNull() {
            addCriterion("UNITLEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andUnitlevelEqualTo(Long value) {
            addCriterion("UNITLEVEL =", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelNotEqualTo(Long value) {
            addCriterion("UNITLEVEL <>", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelGreaterThan(Long value) {
            addCriterion("UNITLEVEL >", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelGreaterThanOrEqualTo(Long value) {
            addCriterion("UNITLEVEL >=", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelLessThan(Long value) {
            addCriterion("UNITLEVEL <", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelLessThanOrEqualTo(Long value) {
            addCriterion("UNITLEVEL <=", value, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelIn(List<Long> values) {
            addCriterion("UNITLEVEL in", values, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelNotIn(List<Long> values) {
            addCriterion("UNITLEVEL not in", values, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelBetween(Long value1, Long value2) {
            addCriterion("UNITLEVEL between", value1, value2, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelNotBetween(Long value1, Long value2) {
            addCriterion("UNITLEVEL not between", value1, value2, "unitlevel");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameIsNull() {
            addCriterion("UNITLEVELNAME is null");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameIsNotNull() {
            addCriterion("UNITLEVELNAME is not null");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameEqualTo(String value) {
            addCriterion("UNITLEVELNAME =", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameNotEqualTo(String value) {
            addCriterion("UNITLEVELNAME <>", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameGreaterThan(String value) {
            addCriterion("UNITLEVELNAME >", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameGreaterThanOrEqualTo(String value) {
            addCriterion("UNITLEVELNAME >=", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameLessThan(String value) {
            addCriterion("UNITLEVELNAME <", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameLessThanOrEqualTo(String value) {
            addCriterion("UNITLEVELNAME <=", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameLike(String value) {
            addCriterion("UNITLEVELNAME like", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameNotLike(String value) {
            addCriterion("UNITLEVELNAME not like", value, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameIn(List<String> values) {
            addCriterion("UNITLEVELNAME in", values, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameNotIn(List<String> values) {
            addCriterion("UNITLEVELNAME not in", values, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameBetween(String value1, String value2) {
            addCriterion("UNITLEVELNAME between", value1, value2, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andUnitlevelnameNotBetween(String value1, String value2) {
            addCriterion("UNITLEVELNAME not between", value1, value2, "unitlevelname");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIsNull() {
            addCriterion("PSNSMCID is null");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIsNotNull() {
            addCriterion("PSNSMCID is not null");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidEqualTo(String value) {
            addCriterion("PSNSMCID =", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotEqualTo(String value) {
            addCriterion("PSNSMCID <>", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidGreaterThan(String value) {
            addCriterion("PSNSMCID >", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidGreaterThanOrEqualTo(String value) {
            addCriterion("PSNSMCID >=", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLessThan(String value) {
            addCriterion("PSNSMCID <", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLessThanOrEqualTo(String value) {
            addCriterion("PSNSMCID <=", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidLike(String value) {
            addCriterion("PSNSMCID like", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotLike(String value) {
            addCriterion("PSNSMCID not like", value, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidIn(List<String> values) {
            addCriterion("PSNSMCID in", values, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotIn(List<String> values) {
            addCriterion("PSNSMCID not in", values, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidBetween(String value1, String value2) {
            addCriterion("PSNSMCID between", value1, value2, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnsmcidNotBetween(String value1, String value2) {
            addCriterion("PSNSMCID not between", value1, value2, "psnsmcid");
            return (Criteria) this;
        }

        public Criteria andPsnnameIsNull() {
            addCriterion("PSNNAME is null");
            return (Criteria) this;
        }

        public Criteria andPsnnameIsNotNull() {
            addCriterion("PSNNAME is not null");
            return (Criteria) this;
        }

        public Criteria andPsnnameEqualTo(String value) {
            addCriterion("PSNNAME =", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameNotEqualTo(String value) {
            addCriterion("PSNNAME <>", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameGreaterThan(String value) {
            addCriterion("PSNNAME >", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameGreaterThanOrEqualTo(String value) {
            addCriterion("PSNNAME >=", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameLessThan(String value) {
            addCriterion("PSNNAME <", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameLessThanOrEqualTo(String value) {
            addCriterion("PSNNAME <=", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameLike(String value) {
            addCriterion("PSNNAME like", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameNotLike(String value) {
            addCriterion("PSNNAME not like", value, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameIn(List<String> values) {
            addCriterion("PSNNAME in", values, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameNotIn(List<String> values) {
            addCriterion("PSNNAME not in", values, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameBetween(String value1, String value2) {
            addCriterion("PSNNAME between", value1, value2, "psnname");
            return (Criteria) this;
        }

        public Criteria andPsnnameNotBetween(String value1, String value2) {
            addCriterion("PSNNAME not between", value1, value2, "psnname");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionIsNull() {
            addCriterion("FUZERENPOSITION is null");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionIsNotNull() {
            addCriterion("FUZERENPOSITION is not null");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionEqualTo(String value) {
            addCriterion("FUZERENPOSITION =", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionNotEqualTo(String value) {
            addCriterion("FUZERENPOSITION <>", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionGreaterThan(String value) {
            addCriterion("FUZERENPOSITION >", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionGreaterThanOrEqualTo(String value) {
            addCriterion("FUZERENPOSITION >=", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionLessThan(String value) {
            addCriterion("FUZERENPOSITION <", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionLessThanOrEqualTo(String value) {
            addCriterion("FUZERENPOSITION <=", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionLike(String value) {
            addCriterion("FUZERENPOSITION like", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionNotLike(String value) {
            addCriterion("FUZERENPOSITION not like", value, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionIn(List<String> values) {
            addCriterion("FUZERENPOSITION in", values, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionNotIn(List<String> values) {
            addCriterion("FUZERENPOSITION not in", values, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionBetween(String value1, String value2) {
            addCriterion("FUZERENPOSITION between", value1, value2, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andFuzerenpositionNotBetween(String value1, String value2) {
            addCriterion("FUZERENPOSITION not between", value1, value2, "fuzerenposition");
            return (Criteria) this;
        }

        public Criteria andPositionnameIsNull() {
            addCriterion("POSITIONNAME is null");
            return (Criteria) this;
        }

        public Criteria andPositionnameIsNotNull() {
            addCriterion("POSITIONNAME is not null");
            return (Criteria) this;
        }

        public Criteria andPositionnameEqualTo(String value) {
            addCriterion("POSITIONNAME =", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameNotEqualTo(String value) {
            addCriterion("POSITIONNAME <>", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameGreaterThan(String value) {
            addCriterion("POSITIONNAME >", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameGreaterThanOrEqualTo(String value) {
            addCriterion("POSITIONNAME >=", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameLessThan(String value) {
            addCriterion("POSITIONNAME <", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameLessThanOrEqualTo(String value) {
            addCriterion("POSITIONNAME <=", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameLike(String value) {
            addCriterion("POSITIONNAME like", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameNotLike(String value) {
            addCriterion("POSITIONNAME not like", value, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameIn(List<String> values) {
            addCriterion("POSITIONNAME in", values, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameNotIn(List<String> values) {
            addCriterion("POSITIONNAME not in", values, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameBetween(String value1, String value2) {
            addCriterion("POSITIONNAME between", value1, value2, "positionname");
            return (Criteria) this;
        }

        public Criteria andPositionnameNotBetween(String value1, String value2) {
            addCriterion("POSITIONNAME not between", value1, value2, "positionname");
            return (Criteria) this;
        }

        public Criteria andIssaleholonIsNull() {
            addCriterion("ISSALEHOLON is null");
            return (Criteria) this;
        }

        public Criteria andIssaleholonIsNotNull() {
            addCriterion("ISSALEHOLON is not null");
            return (Criteria) this;
        }

        public Criteria andIssaleholonEqualTo(String value) {
            addCriterion("ISSALEHOLON =", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonNotEqualTo(String value) {
            addCriterion("ISSALEHOLON <>", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonGreaterThan(String value) {
            addCriterion("ISSALEHOLON >", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonGreaterThanOrEqualTo(String value) {
            addCriterion("ISSALEHOLON >=", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonLessThan(String value) {
            addCriterion("ISSALEHOLON <", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonLessThanOrEqualTo(String value) {
            addCriterion("ISSALEHOLON <=", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonLike(String value) {
            addCriterion("ISSALEHOLON like", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonNotLike(String value) {
            addCriterion("ISSALEHOLON not like", value, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonIn(List<String> values) {
            addCriterion("ISSALEHOLON in", values, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonNotIn(List<String> values) {
            addCriterion("ISSALEHOLON not in", values, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonBetween(String value1, String value2) {
            addCriterion("ISSALEHOLON between", value1, value2, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andIssaleholonNotBetween(String value1, String value2) {
            addCriterion("ISSALEHOLON not between", value1, value2, "issaleholon");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberIsNull() {
            addCriterion("SALEDEPTFNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberIsNotNull() {
            addCriterion("SALEDEPTFNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberEqualTo(String value) {
            addCriterion("SALEDEPTFNUMBER =", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberNotEqualTo(String value) {
            addCriterion("SALEDEPTFNUMBER <>", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberGreaterThan(String value) {
            addCriterion("SALEDEPTFNUMBER >", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberGreaterThanOrEqualTo(String value) {
            addCriterion("SALEDEPTFNUMBER >=", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberLessThan(String value) {
            addCriterion("SALEDEPTFNUMBER <", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberLessThanOrEqualTo(String value) {
            addCriterion("SALEDEPTFNUMBER <=", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberLike(String value) {
            addCriterion("SALEDEPTFNUMBER like", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberNotLike(String value) {
            addCriterion("SALEDEPTFNUMBER not like", value, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberIn(List<String> values) {
            addCriterion("SALEDEPTFNUMBER in", values, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberNotIn(List<String> values) {
            addCriterion("SALEDEPTFNUMBER not in", values, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberBetween(String value1, String value2) {
            addCriterion("SALEDEPTFNUMBER between", value1, value2, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnumberNotBetween(String value1, String value2) {
            addCriterion("SALEDEPTFNUMBER not between", value1, value2, "saledeptfnumber");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameIsNull() {
            addCriterion("SALEDEPTFNAME is null");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameIsNotNull() {
            addCriterion("SALEDEPTFNAME is not null");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameEqualTo(String value) {
            addCriterion("SALEDEPTFNAME =", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameNotEqualTo(String value) {
            addCriterion("SALEDEPTFNAME <>", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameGreaterThan(String value) {
            addCriterion("SALEDEPTFNAME >", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameGreaterThanOrEqualTo(String value) {
            addCriterion("SALEDEPTFNAME >=", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameLessThan(String value) {
            addCriterion("SALEDEPTFNAME <", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameLessThanOrEqualTo(String value) {
            addCriterion("SALEDEPTFNAME <=", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameLike(String value) {
            addCriterion("SALEDEPTFNAME like", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameNotLike(String value) {
            addCriterion("SALEDEPTFNAME not like", value, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameIn(List<String> values) {
            addCriterion("SALEDEPTFNAME in", values, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameNotIn(List<String> values) {
            addCriterion("SALEDEPTFNAME not in", values, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameBetween(String value1, String value2) {
            addCriterion("SALEDEPTFNAME between", value1, value2, "saledeptfname");
            return (Criteria) this;
        }

        public Criteria andSaledeptfnameNotBetween(String value1, String value2) {
            addCriterion("SALEDEPTFNAME not between", value1, value2, "saledeptfname");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class DepartmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepartmentExample() {
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

        public Criteria andOlddeptnoIsNull() {
            addCriterion("OldDeptNo is null");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoIsNotNull() {
            addCriterion("OldDeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoEqualTo(String value) {
            addCriterion("OldDeptNo =", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoNotEqualTo(String value) {
            addCriterion("OldDeptNo <>", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoGreaterThan(String value) {
            addCriterion("OldDeptNo >", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("OldDeptNo >=", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoLessThan(String value) {
            addCriterion("OldDeptNo <", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoLessThanOrEqualTo(String value) {
            addCriterion("OldDeptNo <=", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoLike(String value) {
            addCriterion("OldDeptNo like", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoNotLike(String value) {
            addCriterion("OldDeptNo not like", value, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoIn(List<String> values) {
            addCriterion("OldDeptNo in", values, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoNotIn(List<String> values) {
            addCriterion("OldDeptNo not in", values, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoBetween(String value1, String value2) {
            addCriterion("OldDeptNo between", value1, value2, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andOlddeptnoNotBetween(String value1, String value2) {
            addCriterion("OldDeptNo not between", value1, value2, "olddeptno");
            return (Criteria) this;
        }

        public Criteria andDeptnameIsNull() {
            addCriterion("DeptName is null");
            return (Criteria) this;
        }

        public Criteria andDeptnameIsNotNull() {
            addCriterion("DeptName is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnameEqualTo(String value) {
            addCriterion("DeptName =", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotEqualTo(String value) {
            addCriterion("DeptName <>", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameGreaterThan(String value) {
            addCriterion("DeptName >", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameGreaterThanOrEqualTo(String value) {
            addCriterion("DeptName >=", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLessThan(String value) {
            addCriterion("DeptName <", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLessThanOrEqualTo(String value) {
            addCriterion("DeptName <=", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameLike(String value) {
            addCriterion("DeptName like", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotLike(String value) {
            addCriterion("DeptName not like", value, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameIn(List<String> values) {
            addCriterion("DeptName in", values, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotIn(List<String> values) {
            addCriterion("DeptName not in", values, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameBetween(String value1, String value2) {
            addCriterion("DeptName between", value1, value2, "deptname");
            return (Criteria) this;
        }

        public Criteria andDeptnameNotBetween(String value1, String value2) {
            addCriterion("DeptName not between", value1, value2, "deptname");
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

        public Criteria andEmailacIsNull() {
            addCriterion("EmailAC is null");
            return (Criteria) this;
        }

        public Criteria andEmailacIsNotNull() {
            addCriterion("EmailAC is not null");
            return (Criteria) this;
        }

        public Criteria andEmailacEqualTo(String value) {
            addCriterion("EmailAC =", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacNotEqualTo(String value) {
            addCriterion("EmailAC <>", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacGreaterThan(String value) {
            addCriterion("EmailAC >", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacGreaterThanOrEqualTo(String value) {
            addCriterion("EmailAC >=", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacLessThan(String value) {
            addCriterion("EmailAC <", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacLessThanOrEqualTo(String value) {
            addCriterion("EmailAC <=", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacLike(String value) {
            addCriterion("EmailAC like", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacNotLike(String value) {
            addCriterion("EmailAC not like", value, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacIn(List<String> values) {
            addCriterion("EmailAC in", values, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacNotIn(List<String> values) {
            addCriterion("EmailAC not in", values, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacBetween(String value1, String value2) {
            addCriterion("EmailAC between", value1, value2, "emailac");
            return (Criteria) this;
        }

        public Criteria andEmailacNotBetween(String value1, String value2) {
            addCriterion("EmailAC not between", value1, value2, "emailac");
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

        public Criteria andProvincenameIsNull() {
            addCriterion("ProvinceName is null");
            return (Criteria) this;
        }

        public Criteria andProvincenameIsNotNull() {
            addCriterion("ProvinceName is not null");
            return (Criteria) this;
        }

        public Criteria andProvincenameEqualTo(String value) {
            addCriterion("ProvinceName =", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotEqualTo(String value) {
            addCriterion("ProvinceName <>", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameGreaterThan(String value) {
            addCriterion("ProvinceName >", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameGreaterThanOrEqualTo(String value) {
            addCriterion("ProvinceName >=", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLessThan(String value) {
            addCriterion("ProvinceName <", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLessThanOrEqualTo(String value) {
            addCriterion("ProvinceName <=", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLike(String value) {
            addCriterion("ProvinceName like", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotLike(String value) {
            addCriterion("ProvinceName not like", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameIn(List<String> values) {
            addCriterion("ProvinceName in", values, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotIn(List<String> values) {
            addCriterion("ProvinceName not in", values, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameBetween(String value1, String value2) {
            addCriterion("ProvinceName between", value1, value2, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotBetween(String value1, String value2) {
            addCriterion("ProvinceName not between", value1, value2, "provincename");
            return (Criteria) this;
        }

        public Criteria andDeptennameIsNull() {
            addCriterion("DeptENName is null");
            return (Criteria) this;
        }

        public Criteria andDeptennameIsNotNull() {
            addCriterion("DeptENName is not null");
            return (Criteria) this;
        }

        public Criteria andDeptennameEqualTo(String value) {
            addCriterion("DeptENName =", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameNotEqualTo(String value) {
            addCriterion("DeptENName <>", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameGreaterThan(String value) {
            addCriterion("DeptENName >", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameGreaterThanOrEqualTo(String value) {
            addCriterion("DeptENName >=", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameLessThan(String value) {
            addCriterion("DeptENName <", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameLessThanOrEqualTo(String value) {
            addCriterion("DeptENName <=", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameLike(String value) {
            addCriterion("DeptENName like", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameNotLike(String value) {
            addCriterion("DeptENName not like", value, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameIn(List<String> values) {
            addCriterion("DeptENName in", values, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameNotIn(List<String> values) {
            addCriterion("DeptENName not in", values, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameBetween(String value1, String value2) {
            addCriterion("DeptENName between", value1, value2, "deptenname");
            return (Criteria) this;
        }

        public Criteria andDeptennameNotBetween(String value1, String value2) {
            addCriterion("DeptENName not between", value1, value2, "deptenname");
            return (Criteria) this;
        }

        public Criteria andGpslngIsNull() {
            addCriterion("GPSLng is null");
            return (Criteria) this;
        }

        public Criteria andGpslngIsNotNull() {
            addCriterion("GPSLng is not null");
            return (Criteria) this;
        }

        public Criteria andGpslngEqualTo(Double value) {
            addCriterion("GPSLng =", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngNotEqualTo(Double value) {
            addCriterion("GPSLng <>", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngGreaterThan(Double value) {
            addCriterion("GPSLng >", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngGreaterThanOrEqualTo(Double value) {
            addCriterion("GPSLng >=", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngLessThan(Double value) {
            addCriterion("GPSLng <", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngLessThanOrEqualTo(Double value) {
            addCriterion("GPSLng <=", value, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngIn(List<Double> values) {
            addCriterion("GPSLng in", values, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngNotIn(List<Double> values) {
            addCriterion("GPSLng not in", values, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngBetween(Double value1, Double value2) {
            addCriterion("GPSLng between", value1, value2, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslngNotBetween(Double value1, Double value2) {
            addCriterion("GPSLng not between", value1, value2, "gpslng");
            return (Criteria) this;
        }

        public Criteria andGpslatIsNull() {
            addCriterion("GPSLat is null");
            return (Criteria) this;
        }

        public Criteria andGpslatIsNotNull() {
            addCriterion("GPSLat is not null");
            return (Criteria) this;
        }

        public Criteria andGpslatEqualTo(Double value) {
            addCriterion("GPSLat =", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatNotEqualTo(Double value) {
            addCriterion("GPSLat <>", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatGreaterThan(Double value) {
            addCriterion("GPSLat >", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatGreaterThanOrEqualTo(Double value) {
            addCriterion("GPSLat >=", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatLessThan(Double value) {
            addCriterion("GPSLat <", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatLessThanOrEqualTo(Double value) {
            addCriterion("GPSLat <=", value, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatIn(List<Double> values) {
            addCriterion("GPSLat in", values, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatNotIn(List<Double> values) {
            addCriterion("GPSLat not in", values, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatBetween(Double value1, Double value2) {
            addCriterion("GPSLat between", value1, value2, "gpslat");
            return (Criteria) this;
        }

        public Criteria andGpslatNotBetween(Double value1, Double value2) {
            addCriterion("GPSLat not between", value1, value2, "gpslat");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoIsNull() {
            addCriterion("ParentDeptNo is null");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoIsNotNull() {
            addCriterion("ParentDeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoEqualTo(String value) {
            addCriterion("ParentDeptNo =", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoNotEqualTo(String value) {
            addCriterion("ParentDeptNo <>", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoGreaterThan(String value) {
            addCriterion("ParentDeptNo >", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("ParentDeptNo >=", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoLessThan(String value) {
            addCriterion("ParentDeptNo <", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoLessThanOrEqualTo(String value) {
            addCriterion("ParentDeptNo <=", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoLike(String value) {
            addCriterion("ParentDeptNo like", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoNotLike(String value) {
            addCriterion("ParentDeptNo not like", value, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoIn(List<String> values) {
            addCriterion("ParentDeptNo in", values, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoNotIn(List<String> values) {
            addCriterion("ParentDeptNo not in", values, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoBetween(String value1, String value2) {
            addCriterion("ParentDeptNo between", value1, value2, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andParentdeptnoNotBetween(String value1, String value2) {
            addCriterion("ParentDeptNo not between", value1, value2, "parentdeptno");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeIsNull() {
            addCriterion("sub_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeIsNotNull() {
            addCriterion("sub_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeEqualTo(String value) {
            addCriterion("sub_warehouse_code =", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeNotEqualTo(String value) {
            addCriterion("sub_warehouse_code <>", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeGreaterThan(String value) {
            addCriterion("sub_warehouse_code >", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_warehouse_code >=", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeLessThan(String value) {
            addCriterion("sub_warehouse_code <", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("sub_warehouse_code <=", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeLike(String value) {
            addCriterion("sub_warehouse_code like", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeNotLike(String value) {
            addCriterion("sub_warehouse_code not like", value, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeIn(List<String> values) {
            addCriterion("sub_warehouse_code in", values, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeNotIn(List<String> values) {
            addCriterion("sub_warehouse_code not in", values, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeBetween(String value1, String value2) {
            addCriterion("sub_warehouse_code between", value1, value2, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSubWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("sub_warehouse_code not between", value1, value2, "subWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReportemailIsNull() {
            addCriterion("ReportEmail is null");
            return (Criteria) this;
        }

        public Criteria andReportemailIsNotNull() {
            addCriterion("ReportEmail is not null");
            return (Criteria) this;
        }

        public Criteria andReportemailEqualTo(String value) {
            addCriterion("ReportEmail =", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailNotEqualTo(String value) {
            addCriterion("ReportEmail <>", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailGreaterThan(String value) {
            addCriterion("ReportEmail >", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailGreaterThanOrEqualTo(String value) {
            addCriterion("ReportEmail >=", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailLessThan(String value) {
            addCriterion("ReportEmail <", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailLessThanOrEqualTo(String value) {
            addCriterion("ReportEmail <=", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailLike(String value) {
            addCriterion("ReportEmail like", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailNotLike(String value) {
            addCriterion("ReportEmail not like", value, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailIn(List<String> values) {
            addCriterion("ReportEmail in", values, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailNotIn(List<String> values) {
            addCriterion("ReportEmail not in", values, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailBetween(String value1, String value2) {
            addCriterion("ReportEmail between", value1, value2, "reportemail");
            return (Criteria) this;
        }

        public Criteria andReportemailNotBetween(String value1, String value2) {
            addCriterion("ReportEmail not between", value1, value2, "reportemail");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIsNull() {
            addCriterion("trade_companyId is null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIsNotNull() {
            addCriterion("trade_companyId is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidEqualTo(String value) {
            addCriterion("trade_companyId =", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotEqualTo(String value) {
            addCriterion("trade_companyId <>", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThan(String value) {
            addCriterion("trade_companyId >", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidGreaterThanOrEqualTo(String value) {
            addCriterion("trade_companyId >=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThan(String value) {
            addCriterion("trade_companyId <", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLessThanOrEqualTo(String value) {
            addCriterion("trade_companyId <=", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidLike(String value) {
            addCriterion("trade_companyId like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotLike(String value) {
            addCriterion("trade_companyId not like", value, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidIn(List<String> values) {
            addCriterion("trade_companyId in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotIn(List<String> values) {
            addCriterion("trade_companyId not in", values, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidBetween(String value1, String value2) {
            addCriterion("trade_companyId between", value1, value2, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andTradeCompanyidNotBetween(String value1, String value2) {
            addCriterion("trade_companyId not between", value1, value2, "tradeCompanyid");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("CityCode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("CityCode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(String value) {
            addCriterion("CityCode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(String value) {
            addCriterion("CityCode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(String value) {
            addCriterion("CityCode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
            addCriterion("CityCode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(String value) {
            addCriterion("CityCode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(String value) {
            addCriterion("CityCode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLike(String value) {
            addCriterion("CityCode like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotLike(String value) {
            addCriterion("CityCode not like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<String> values) {
            addCriterion("CityCode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<String> values) {
            addCriterion("CityCode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(String value1, String value2) {
            addCriterion("CityCode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(String value1, String value2) {
            addCriterion("CityCode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andDlvdayIsNull() {
            addCriterion("DlvDay is null");
            return (Criteria) this;
        }

        public Criteria andDlvdayIsNotNull() {
            addCriterion("DlvDay is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdayEqualTo(Integer value) {
            addCriterion("DlvDay =", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayNotEqualTo(Integer value) {
            addCriterion("DlvDay <>", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayGreaterThan(Integer value) {
            addCriterion("DlvDay >", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("DlvDay >=", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayLessThan(Integer value) {
            addCriterion("DlvDay <", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayLessThanOrEqualTo(Integer value) {
            addCriterion("DlvDay <=", value, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayIn(List<Integer> values) {
            addCriterion("DlvDay in", values, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayNotIn(List<Integer> values) {
            addCriterion("DlvDay not in", values, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayBetween(Integer value1, Integer value2) {
            addCriterion("DlvDay between", value1, value2, "dlvday");
            return (Criteria) this;
        }

        public Criteria andDlvdayNotBetween(Integer value1, Integer value2) {
            addCriterion("DlvDay not between", value1, value2, "dlvday");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNull() {
            addCriterion("IsValid is null");
            return (Criteria) this;
        }

        public Criteria andIsvalidIsNotNull() {
            addCriterion("IsValid is not null");
            return (Criteria) this;
        }

        public Criteria andIsvalidEqualTo(Boolean value) {
            addCriterion("IsValid =", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotEqualTo(Boolean value) {
            addCriterion("IsValid <>", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThan(Boolean value) {
            addCriterion("IsValid >", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsValid >=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThan(Boolean value) {
            addCriterion("IsValid <", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidLessThanOrEqualTo(Boolean value) {
            addCriterion("IsValid <=", value, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidIn(List<Boolean> values) {
            addCriterion("IsValid in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotIn(List<Boolean> values) {
            addCriterion("IsValid not in", values, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidBetween(Boolean value1, Boolean value2) {
            addCriterion("IsValid between", value1, value2, "isvalid");
            return (Criteria) this;
        }

        public Criteria andIsvalidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsValid not between", value1, value2, "isvalid");
            return (Criteria) this;
        }

        public Criteria andEmailorderIsNull() {
            addCriterion("emailOrder is null");
            return (Criteria) this;
        }

        public Criteria andEmailorderIsNotNull() {
            addCriterion("emailOrder is not null");
            return (Criteria) this;
        }

        public Criteria andEmailorderEqualTo(String value) {
            addCriterion("emailOrder =", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderNotEqualTo(String value) {
            addCriterion("emailOrder <>", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderGreaterThan(String value) {
            addCriterion("emailOrder >", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderGreaterThanOrEqualTo(String value) {
            addCriterion("emailOrder >=", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderLessThan(String value) {
            addCriterion("emailOrder <", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderLessThanOrEqualTo(String value) {
            addCriterion("emailOrder <=", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderLike(String value) {
            addCriterion("emailOrder like", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderNotLike(String value) {
            addCriterion("emailOrder not like", value, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderIn(List<String> values) {
            addCriterion("emailOrder in", values, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderNotIn(List<String> values) {
            addCriterion("emailOrder not in", values, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderBetween(String value1, String value2) {
            addCriterion("emailOrder between", value1, value2, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailorderNotBetween(String value1, String value2) {
            addCriterion("emailOrder not between", value1, value2, "emailorder");
            return (Criteria) this;
        }

        public Criteria andEmailstockIsNull() {
            addCriterion("EmailStock is null");
            return (Criteria) this;
        }

        public Criteria andEmailstockIsNotNull() {
            addCriterion("EmailStock is not null");
            return (Criteria) this;
        }

        public Criteria andEmailstockEqualTo(String value) {
            addCriterion("EmailStock =", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockNotEqualTo(String value) {
            addCriterion("EmailStock <>", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockGreaterThan(String value) {
            addCriterion("EmailStock >", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockGreaterThanOrEqualTo(String value) {
            addCriterion("EmailStock >=", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockLessThan(String value) {
            addCriterion("EmailStock <", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockLessThanOrEqualTo(String value) {
            addCriterion("EmailStock <=", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockLike(String value) {
            addCriterion("EmailStock like", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockNotLike(String value) {
            addCriterion("EmailStock not like", value, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockIn(List<String> values) {
            addCriterion("EmailStock in", values, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockNotIn(List<String> values) {
            addCriterion("EmailStock not in", values, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockBetween(String value1, String value2) {
            addCriterion("EmailStock between", value1, value2, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailstockNotBetween(String value1, String value2) {
            addCriterion("EmailStock not between", value1, value2, "emailstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockIsNull() {
            addCriterion("EmailCSStock is null");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockIsNotNull() {
            addCriterion("EmailCSStock is not null");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockEqualTo(String value) {
            addCriterion("EmailCSStock =", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockNotEqualTo(String value) {
            addCriterion("EmailCSStock <>", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockGreaterThan(String value) {
            addCriterion("EmailCSStock >", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockGreaterThanOrEqualTo(String value) {
            addCriterion("EmailCSStock >=", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockLessThan(String value) {
            addCriterion("EmailCSStock <", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockLessThanOrEqualTo(String value) {
            addCriterion("EmailCSStock <=", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockLike(String value) {
            addCriterion("EmailCSStock like", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockNotLike(String value) {
            addCriterion("EmailCSStock not like", value, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockIn(List<String> values) {
            addCriterion("EmailCSStock in", values, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockNotIn(List<String> values) {
            addCriterion("EmailCSStock not in", values, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockBetween(String value1, String value2) {
            addCriterion("EmailCSStock between", value1, value2, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailcsstockNotBetween(String value1, String value2) {
            addCriterion("EmailCSStock not between", value1, value2, "emailcsstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockIsNull() {
            addCriterion("EmailSubStock is null");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockIsNotNull() {
            addCriterion("EmailSubStock is not null");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockEqualTo(String value) {
            addCriterion("EmailSubStock =", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockNotEqualTo(String value) {
            addCriterion("EmailSubStock <>", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockGreaterThan(String value) {
            addCriterion("EmailSubStock >", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockGreaterThanOrEqualTo(String value) {
            addCriterion("EmailSubStock >=", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockLessThan(String value) {
            addCriterion("EmailSubStock <", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockLessThanOrEqualTo(String value) {
            addCriterion("EmailSubStock <=", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockLike(String value) {
            addCriterion("EmailSubStock like", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockNotLike(String value) {
            addCriterion("EmailSubStock not like", value, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockIn(List<String> values) {
            addCriterion("EmailSubStock in", values, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockNotIn(List<String> values) {
            addCriterion("EmailSubStock not in", values, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockBetween(String value1, String value2) {
            addCriterion("EmailSubStock between", value1, value2, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailsubstockNotBetween(String value1, String value2) {
            addCriterion("EmailSubStock not between", value1, value2, "emailsubstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockIsNull() {
            addCriterion("EmailUserStock is null");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockIsNotNull() {
            addCriterion("EmailUserStock is not null");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockEqualTo(String value) {
            addCriterion("EmailUserStock =", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockNotEqualTo(String value) {
            addCriterion("EmailUserStock <>", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockGreaterThan(String value) {
            addCriterion("EmailUserStock >", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockGreaterThanOrEqualTo(String value) {
            addCriterion("EmailUserStock >=", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockLessThan(String value) {
            addCriterion("EmailUserStock <", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockLessThanOrEqualTo(String value) {
            addCriterion("EmailUserStock <=", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockLike(String value) {
            addCriterion("EmailUserStock like", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockNotLike(String value) {
            addCriterion("EmailUserStock not like", value, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockIn(List<String> values) {
            addCriterion("EmailUserStock in", values, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockNotIn(List<String> values) {
            addCriterion("EmailUserStock not in", values, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockBetween(String value1, String value2) {
            addCriterion("EmailUserStock between", value1, value2, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmailuserstockNotBetween(String value1, String value2) {
            addCriterion("EmailUserStock not between", value1, value2, "emailuserstock");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorIsNull() {
            addCriterion("EmailDirector is null");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorIsNotNull() {
            addCriterion("EmailDirector is not null");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorEqualTo(String value) {
            addCriterion("EmailDirector =", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorNotEqualTo(String value) {
            addCriterion("EmailDirector <>", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorGreaterThan(String value) {
            addCriterion("EmailDirector >", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorGreaterThanOrEqualTo(String value) {
            addCriterion("EmailDirector >=", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorLessThan(String value) {
            addCriterion("EmailDirector <", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorLessThanOrEqualTo(String value) {
            addCriterion("EmailDirector <=", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorLike(String value) {
            addCriterion("EmailDirector like", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorNotLike(String value) {
            addCriterion("EmailDirector not like", value, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorIn(List<String> values) {
            addCriterion("EmailDirector in", values, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorNotIn(List<String> values) {
            addCriterion("EmailDirector not in", values, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorBetween(String value1, String value2) {
            addCriterion("EmailDirector between", value1, value2, "emaildirector");
            return (Criteria) this;
        }

        public Criteria andEmaildirectorNotBetween(String value1, String value2) {
            addCriterion("EmailDirector not between", value1, value2, "emaildirector");
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
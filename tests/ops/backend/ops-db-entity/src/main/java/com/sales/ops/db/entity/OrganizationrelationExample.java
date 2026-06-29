package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrganizationrelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrganizationrelationExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("Id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("Id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParentidIsNull() {
            addCriterion("ParentId is null");
            return (Criteria) this;
        }

        public Criteria andParentidIsNotNull() {
            addCriterion("ParentId is not null");
            return (Criteria) this;
        }

        public Criteria andParentidEqualTo(String value) {
            addCriterion("ParentId =", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotEqualTo(String value) {
            addCriterion("ParentId <>", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThan(String value) {
            addCriterion("ParentId >", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThanOrEqualTo(String value) {
            addCriterion("ParentId >=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThan(String value) {
            addCriterion("ParentId <", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThanOrEqualTo(String value) {
            addCriterion("ParentId <=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLike(String value) {
            addCriterion("ParentId like", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotLike(String value) {
            addCriterion("ParentId not like", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidIn(List<String> values) {
            addCriterion("ParentId in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotIn(List<String> values) {
            addCriterion("ParentId not in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidBetween(String value1, String value2) {
            addCriterion("ParentId between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotBetween(String value1, String value2) {
            addCriterion("ParentId not between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andFormalidIsNull() {
            addCriterion("FormalId is null");
            return (Criteria) this;
        }

        public Criteria andFormalidIsNotNull() {
            addCriterion("FormalId is not null");
            return (Criteria) this;
        }

        public Criteria andFormalidEqualTo(String value) {
            addCriterion("FormalId =", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidNotEqualTo(String value) {
            addCriterion("FormalId <>", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidGreaterThan(String value) {
            addCriterion("FormalId >", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidGreaterThanOrEqualTo(String value) {
            addCriterion("FormalId >=", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidLessThan(String value) {
            addCriterion("FormalId <", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidLessThanOrEqualTo(String value) {
            addCriterion("FormalId <=", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidLike(String value) {
            addCriterion("FormalId like", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidNotLike(String value) {
            addCriterion("FormalId not like", value, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidIn(List<String> values) {
            addCriterion("FormalId in", values, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidNotIn(List<String> values) {
            addCriterion("FormalId not in", values, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidBetween(String value1, String value2) {
            addCriterion("FormalId between", value1, value2, "formalid");
            return (Criteria) this;
        }

        public Criteria andFormalidNotBetween(String value1, String value2) {
            addCriterion("FormalId not between", value1, value2, "formalid");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeIsNull() {
            addCriterion("OrganizationType is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeIsNotNull() {
            addCriterion("OrganizationType is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeEqualTo(Integer value) {
            addCriterion("OrganizationType =", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeNotEqualTo(Integer value) {
            addCriterion("OrganizationType <>", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeGreaterThan(Integer value) {
            addCriterion("OrganizationType >", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrganizationType >=", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeLessThan(Integer value) {
            addCriterion("OrganizationType <", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeLessThanOrEqualTo(Integer value) {
            addCriterion("OrganizationType <=", value, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeIn(List<Integer> values) {
            addCriterion("OrganizationType in", values, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeNotIn(List<Integer> values) {
            addCriterion("OrganizationType not in", values, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeBetween(Integer value1, Integer value2) {
            addCriterion("OrganizationType between", value1, value2, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andOrganizationtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("OrganizationType not between", value1, value2, "organizationtype");
            return (Criteria) this;
        }

        public Criteria andHrunitidIsNull() {
            addCriterion("HRUnitId is null");
            return (Criteria) this;
        }

        public Criteria andHrunitidIsNotNull() {
            addCriterion("HRUnitId is not null");
            return (Criteria) this;
        }

        public Criteria andHrunitidEqualTo(String value) {
            addCriterion("HRUnitId =", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidNotEqualTo(String value) {
            addCriterion("HRUnitId <>", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidGreaterThan(String value) {
            addCriterion("HRUnitId >", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidGreaterThanOrEqualTo(String value) {
            addCriterion("HRUnitId >=", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidLessThan(String value) {
            addCriterion("HRUnitId <", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidLessThanOrEqualTo(String value) {
            addCriterion("HRUnitId <=", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidLike(String value) {
            addCriterion("HRUnitId like", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidNotLike(String value) {
            addCriterion("HRUnitId not like", value, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidIn(List<String> values) {
            addCriterion("HRUnitId in", values, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidNotIn(List<String> values) {
            addCriterion("HRUnitId not in", values, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidBetween(String value1, String value2) {
            addCriterion("HRUnitId between", value1, value2, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andHrunitidNotBetween(String value1, String value2) {
            addCriterion("HRUnitId not between", value1, value2, "hrunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidIsNull() {
            addCriterion("CNUnitId is null");
            return (Criteria) this;
        }

        public Criteria andCnunitidIsNotNull() {
            addCriterion("CNUnitId is not null");
            return (Criteria) this;
        }

        public Criteria andCnunitidEqualTo(String value) {
            addCriterion("CNUnitId =", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidNotEqualTo(String value) {
            addCriterion("CNUnitId <>", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidGreaterThan(String value) {
            addCriterion("CNUnitId >", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidGreaterThanOrEqualTo(String value) {
            addCriterion("CNUnitId >=", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidLessThan(String value) {
            addCriterion("CNUnitId <", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidLessThanOrEqualTo(String value) {
            addCriterion("CNUnitId <=", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidLike(String value) {
            addCriterion("CNUnitId like", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidNotLike(String value) {
            addCriterion("CNUnitId not like", value, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidIn(List<String> values) {
            addCriterion("CNUnitId in", values, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidNotIn(List<String> values) {
            addCriterion("CNUnitId not in", values, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidBetween(String value1, String value2) {
            addCriterion("CNUnitId between", value1, value2, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andCnunitidNotBetween(String value1, String value2) {
            addCriterion("CNUnitId not between", value1, value2, "cnunitid");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
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
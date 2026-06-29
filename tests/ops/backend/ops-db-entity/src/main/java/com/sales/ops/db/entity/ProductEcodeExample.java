package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductEcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductEcodeExample() {
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

        public Criteria andEcodeIsNull() {
            addCriterion("Ecode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("Ecode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("Ecode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("Ecode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("Ecode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("Ecode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("Ecode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("Ecode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("Ecode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("Ecode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("Ecode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("Ecode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("Ecode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("Ecode not between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andParameterIsNull() {
            addCriterion("Parameter is null");
            return (Criteria) this;
        }

        public Criteria andParameterIsNotNull() {
            addCriterion("Parameter is not null");
            return (Criteria) this;
        }

        public Criteria andParameterEqualTo(BigDecimal value) {
            addCriterion("Parameter =", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterNotEqualTo(BigDecimal value) {
            addCriterion("Parameter <>", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterGreaterThan(BigDecimal value) {
            addCriterion("Parameter >", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Parameter >=", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterLessThan(BigDecimal value) {
            addCriterion("Parameter <", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Parameter <=", value, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterIn(List<BigDecimal> values) {
            addCriterion("Parameter in", values, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterNotIn(List<BigDecimal> values) {
            addCriterion("Parameter not in", values, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Parameter between", value1, value2, "parameter");
            return (Criteria) this;
        }

        public Criteria andParameterNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Parameter not between", value1, value2, "parameter");
            return (Criteria) this;
        }

        public Criteria andEparameterIsNull() {
            addCriterion("EParameter is null");
            return (Criteria) this;
        }

        public Criteria andEparameterIsNotNull() {
            addCriterion("EParameter is not null");
            return (Criteria) this;
        }

        public Criteria andEparameterEqualTo(BigDecimal value) {
            addCriterion("EParameter =", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterNotEqualTo(BigDecimal value) {
            addCriterion("EParameter <>", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterGreaterThan(BigDecimal value) {
            addCriterion("EParameter >", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EParameter >=", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterLessThan(BigDecimal value) {
            addCriterion("EParameter <", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EParameter <=", value, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterIn(List<BigDecimal> values) {
            addCriterion("EParameter in", values, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterNotIn(List<BigDecimal> values) {
            addCriterion("EParameter not in", values, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EParameter between", value1, value2, "eparameter");
            return (Criteria) this;
        }

        public Criteria andEparameterNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EParameter not between", value1, value2, "eparameter");
            return (Criteria) this;
        }

        public Criteria andClassnameIsNull() {
            addCriterion("ClassName is null");
            return (Criteria) this;
        }

        public Criteria andClassnameIsNotNull() {
            addCriterion("ClassName is not null");
            return (Criteria) this;
        }

        public Criteria andClassnameEqualTo(String value) {
            addCriterion("ClassName =", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotEqualTo(String value) {
            addCriterion("ClassName <>", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameGreaterThan(String value) {
            addCriterion("ClassName >", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameGreaterThanOrEqualTo(String value) {
            addCriterion("ClassName >=", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLessThan(String value) {
            addCriterion("ClassName <", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLessThanOrEqualTo(String value) {
            addCriterion("ClassName <=", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLike(String value) {
            addCriterion("ClassName like", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotLike(String value) {
            addCriterion("ClassName not like", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameIn(List<String> values) {
            addCriterion("ClassName in", values, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotIn(List<String> values) {
            addCriterion("ClassName not in", values, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameBetween(String value1, String value2) {
            addCriterion("ClassName between", value1, value2, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotBetween(String value1, String value2) {
            addCriterion("ClassName not between", value1, value2, "classname");
            return (Criteria) this;
        }

        public Criteria andClassmodelIsNull() {
            addCriterion("ClassModel is null");
            return (Criteria) this;
        }

        public Criteria andClassmodelIsNotNull() {
            addCriterion("ClassModel is not null");
            return (Criteria) this;
        }

        public Criteria andClassmodelEqualTo(String value) {
            addCriterion("ClassModel =", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelNotEqualTo(String value) {
            addCriterion("ClassModel <>", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelGreaterThan(String value) {
            addCriterion("ClassModel >", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelGreaterThanOrEqualTo(String value) {
            addCriterion("ClassModel >=", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelLessThan(String value) {
            addCriterion("ClassModel <", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelLessThanOrEqualTo(String value) {
            addCriterion("ClassModel <=", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelLike(String value) {
            addCriterion("ClassModel like", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelNotLike(String value) {
            addCriterion("ClassModel not like", value, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelIn(List<String> values) {
            addCriterion("ClassModel in", values, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelNotIn(List<String> values) {
            addCriterion("ClassModel not in", values, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelBetween(String value1, String value2) {
            addCriterion("ClassModel between", value1, value2, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassmodelNotBetween(String value1, String value2) {
            addCriterion("ClassModel not between", value1, value2, "classmodel");
            return (Criteria) this;
        }

        public Criteria andClassname6IsNull() {
            addCriterion("ClassName6 is null");
            return (Criteria) this;
        }

        public Criteria andClassname6IsNotNull() {
            addCriterion("ClassName6 is not null");
            return (Criteria) this;
        }

        public Criteria andClassname6EqualTo(String value) {
            addCriterion("ClassName6 =", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6NotEqualTo(String value) {
            addCriterion("ClassName6 <>", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6GreaterThan(String value) {
            addCriterion("ClassName6 >", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6GreaterThanOrEqualTo(String value) {
            addCriterion("ClassName6 >=", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6LessThan(String value) {
            addCriterion("ClassName6 <", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6LessThanOrEqualTo(String value) {
            addCriterion("ClassName6 <=", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6Like(String value) {
            addCriterion("ClassName6 like", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6NotLike(String value) {
            addCriterion("ClassName6 not like", value, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6In(List<String> values) {
            addCriterion("ClassName6 in", values, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6NotIn(List<String> values) {
            addCriterion("ClassName6 not in", values, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6Between(String value1, String value2) {
            addCriterion("ClassName6 between", value1, value2, "classname6");
            return (Criteria) this;
        }

        public Criteria andClassname6NotBetween(String value1, String value2) {
            addCriterion("ClassName6 not between", value1, value2, "classname6");
            return (Criteria) this;
        }

        public Criteria andClasscodeIsNull() {
            addCriterion("ClassCode is null");
            return (Criteria) this;
        }

        public Criteria andClasscodeIsNotNull() {
            addCriterion("ClassCode is not null");
            return (Criteria) this;
        }

        public Criteria andClasscodeEqualTo(String value) {
            addCriterion("ClassCode =", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotEqualTo(String value) {
            addCriterion("ClassCode <>", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThan(String value) {
            addCriterion("ClassCode >", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThanOrEqualTo(String value) {
            addCriterion("ClassCode >=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThan(String value) {
            addCriterion("ClassCode <", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThanOrEqualTo(String value) {
            addCriterion("ClassCode <=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLike(String value) {
            addCriterion("ClassCode like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotLike(String value) {
            addCriterion("ClassCode not like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeIn(List<String> values) {
            addCriterion("ClassCode in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotIn(List<String> values) {
            addCriterion("ClassCode not in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeBetween(String value1, String value2) {
            addCriterion("ClassCode between", value1, value2, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotBetween(String value1, String value2) {
            addCriterion("ClassCode not between", value1, value2, "classcode");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameIsNull() {
            addCriterion("ImpCustCName is null");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameIsNotNull() {
            addCriterion("ImpCustCName is not null");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameEqualTo(String value) {
            addCriterion("ImpCustCName =", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameNotEqualTo(String value) {
            addCriterion("ImpCustCName <>", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameGreaterThan(String value) {
            addCriterion("ImpCustCName >", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameGreaterThanOrEqualTo(String value) {
            addCriterion("ImpCustCName >=", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameLessThan(String value) {
            addCriterion("ImpCustCName <", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameLessThanOrEqualTo(String value) {
            addCriterion("ImpCustCName <=", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameLike(String value) {
            addCriterion("ImpCustCName like", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameNotLike(String value) {
            addCriterion("ImpCustCName not like", value, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameIn(List<String> values) {
            addCriterion("ImpCustCName in", values, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameNotIn(List<String> values) {
            addCriterion("ImpCustCName not in", values, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameBetween(String value1, String value2) {
            addCriterion("ImpCustCName between", value1, value2, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andImpcustcnameNotBetween(String value1, String value2) {
            addCriterion("ImpCustCName not between", value1, value2, "impcustcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameIsNull() {
            addCriterion("JFormCName is null");
            return (Criteria) this;
        }

        public Criteria andJformcnameIsNotNull() {
            addCriterion("JFormCName is not null");
            return (Criteria) this;
        }

        public Criteria andJformcnameEqualTo(String value) {
            addCriterion("JFormCName =", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameNotEqualTo(String value) {
            addCriterion("JFormCName <>", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameGreaterThan(String value) {
            addCriterion("JFormCName >", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameGreaterThanOrEqualTo(String value) {
            addCriterion("JFormCName >=", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameLessThan(String value) {
            addCriterion("JFormCName <", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameLessThanOrEqualTo(String value) {
            addCriterion("JFormCName <=", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameLike(String value) {
            addCriterion("JFormCName like", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameNotLike(String value) {
            addCriterion("JFormCName not like", value, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameIn(List<String> values) {
            addCriterion("JFormCName in", values, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameNotIn(List<String> values) {
            addCriterion("JFormCName not in", values, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameBetween(String value1, String value2) {
            addCriterion("JFormCName between", value1, value2, "jformcname");
            return (Criteria) this;
        }

        public Criteria andJformcnameNotBetween(String value1, String value2) {
            addCriterion("JFormCName not between", value1, value2, "jformcname");
            return (Criteria) this;
        }

        public Criteria andCusttaxIsNull() {
            addCriterion("CustTax is null");
            return (Criteria) this;
        }

        public Criteria andCusttaxIsNotNull() {
            addCriterion("CustTax is not null");
            return (Criteria) this;
        }

        public Criteria andCusttaxEqualTo(BigDecimal value) {
            addCriterion("CustTax =", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxNotEqualTo(BigDecimal value) {
            addCriterion("CustTax <>", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxGreaterThan(BigDecimal value) {
            addCriterion("CustTax >", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CustTax >=", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxLessThan(BigDecimal value) {
            addCriterion("CustTax <", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CustTax <=", value, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxIn(List<BigDecimal> values) {
            addCriterion("CustTax in", values, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxNotIn(List<BigDecimal> values) {
            addCriterion("CustTax not in", values, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CustTax between", value1, value2, "custtax");
            return (Criteria) this;
        }

        public Criteria andCusttaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CustTax not between", value1, value2, "custtax");
            return (Criteria) this;
        }

        public Criteria andInvnameIsNull() {
            addCriterion("InvName is null");
            return (Criteria) this;
        }

        public Criteria andInvnameIsNotNull() {
            addCriterion("InvName is not null");
            return (Criteria) this;
        }

        public Criteria andInvnameEqualTo(String value) {
            addCriterion("InvName =", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameNotEqualTo(String value) {
            addCriterion("InvName <>", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameGreaterThan(String value) {
            addCriterion("InvName >", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameGreaterThanOrEqualTo(String value) {
            addCriterion("InvName >=", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameLessThan(String value) {
            addCriterion("InvName <", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameLessThanOrEqualTo(String value) {
            addCriterion("InvName <=", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameLike(String value) {
            addCriterion("InvName like", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameNotLike(String value) {
            addCriterion("InvName not like", value, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameIn(List<String> values) {
            addCriterion("InvName in", values, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameNotIn(List<String> values) {
            addCriterion("InvName not in", values, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameBetween(String value1, String value2) {
            addCriterion("InvName between", value1, value2, "invname");
            return (Criteria) this;
        }

        public Criteria andInvnameNotBetween(String value1, String value2) {
            addCriterion("InvName not between", value1, value2, "invname");
            return (Criteria) this;
        }

        public Criteria andEngnameIsNull() {
            addCriterion("EngName is null");
            return (Criteria) this;
        }

        public Criteria andEngnameIsNotNull() {
            addCriterion("EngName is not null");
            return (Criteria) this;
        }

        public Criteria andEngnameEqualTo(String value) {
            addCriterion("EngName =", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameNotEqualTo(String value) {
            addCriterion("EngName <>", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameGreaterThan(String value) {
            addCriterion("EngName >", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameGreaterThanOrEqualTo(String value) {
            addCriterion("EngName >=", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameLessThan(String value) {
            addCriterion("EngName <", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameLessThanOrEqualTo(String value) {
            addCriterion("EngName <=", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameLike(String value) {
            addCriterion("EngName like", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameNotLike(String value) {
            addCriterion("EngName not like", value, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameIn(List<String> values) {
            addCriterion("EngName in", values, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameNotIn(List<String> values) {
            addCriterion("EngName not in", values, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameBetween(String value1, String value2) {
            addCriterion("EngName between", value1, value2, "engname");
            return (Criteria) this;
        }

        public Criteria andEngnameNotBetween(String value1, String value2) {
            addCriterion("EngName not between", value1, value2, "engname");
            return (Criteria) this;
        }

        public Criteria andCrmtypeIsNull() {
            addCriterion("CRMType is null");
            return (Criteria) this;
        }

        public Criteria andCrmtypeIsNotNull() {
            addCriterion("CRMType is not null");
            return (Criteria) this;
        }

        public Criteria andCrmtypeEqualTo(String value) {
            addCriterion("CRMType =", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeNotEqualTo(String value) {
            addCriterion("CRMType <>", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeGreaterThan(String value) {
            addCriterion("CRMType >", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeGreaterThanOrEqualTo(String value) {
            addCriterion("CRMType >=", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeLessThan(String value) {
            addCriterion("CRMType <", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeLessThanOrEqualTo(String value) {
            addCriterion("CRMType <=", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeLike(String value) {
            addCriterion("CRMType like", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeNotLike(String value) {
            addCriterion("CRMType not like", value, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeIn(List<String> values) {
            addCriterion("CRMType in", values, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeNotIn(List<String> values) {
            addCriterion("CRMType not in", values, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeBetween(String value1, String value2) {
            addCriterion("CRMType between", value1, value2, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmtypeNotBetween(String value1, String value2) {
            addCriterion("CRMType not between", value1, value2, "crmtype");
            return (Criteria) this;
        }

        public Criteria andCrmcodeIsNull() {
            addCriterion("CRMCode is null");
            return (Criteria) this;
        }

        public Criteria andCrmcodeIsNotNull() {
            addCriterion("CRMCode is not null");
            return (Criteria) this;
        }

        public Criteria andCrmcodeEqualTo(String value) {
            addCriterion("CRMCode =", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeNotEqualTo(String value) {
            addCriterion("CRMCode <>", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeGreaterThan(String value) {
            addCriterion("CRMCode >", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeGreaterThanOrEqualTo(String value) {
            addCriterion("CRMCode >=", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeLessThan(String value) {
            addCriterion("CRMCode <", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeLessThanOrEqualTo(String value) {
            addCriterion("CRMCode <=", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeLike(String value) {
            addCriterion("CRMCode like", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeNotLike(String value) {
            addCriterion("CRMCode not like", value, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeIn(List<String> values) {
            addCriterion("CRMCode in", values, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeNotIn(List<String> values) {
            addCriterion("CRMCode not in", values, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeBetween(String value1, String value2) {
            addCriterion("CRMCode between", value1, value2, "crmcode");
            return (Criteria) this;
        }

        public Criteria andCrmcodeNotBetween(String value1, String value2) {
            addCriterion("CRMCode not between", value1, value2, "crmcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeIsNull() {
            addCriterion("TaxCode is null");
            return (Criteria) this;
        }

        public Criteria andTaxcodeIsNotNull() {
            addCriterion("TaxCode is not null");
            return (Criteria) this;
        }

        public Criteria andTaxcodeEqualTo(String value) {
            addCriterion("TaxCode =", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeNotEqualTo(String value) {
            addCriterion("TaxCode <>", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeGreaterThan(String value) {
            addCriterion("TaxCode >", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeGreaterThanOrEqualTo(String value) {
            addCriterion("TaxCode >=", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeLessThan(String value) {
            addCriterion("TaxCode <", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeLessThanOrEqualTo(String value) {
            addCriterion("TaxCode <=", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeLike(String value) {
            addCriterion("TaxCode like", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeNotLike(String value) {
            addCriterion("TaxCode not like", value, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeIn(List<String> values) {
            addCriterion("TaxCode in", values, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeNotIn(List<String> values) {
            addCriterion("TaxCode not in", values, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeBetween(String value1, String value2) {
            addCriterion("TaxCode between", value1, value2, "taxcode");
            return (Criteria) this;
        }

        public Criteria andTaxcodeNotBetween(String value1, String value2) {
            addCriterion("TaxCode not between", value1, value2, "taxcode");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("desc is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("desc is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("desc =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("desc <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("desc >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("desc >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("desc <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("desc <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("desc like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("desc not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("desc in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("desc not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("desc between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("desc not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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
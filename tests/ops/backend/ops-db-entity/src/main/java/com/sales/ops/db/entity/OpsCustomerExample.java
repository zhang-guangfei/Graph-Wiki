package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsCustomerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsCustomerExample() {
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

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andEnameIsNull() {
            addCriterion("ename is null");
            return (Criteria) this;
        }

        public Criteria andEnameIsNotNull() {
            addCriterion("ename is not null");
            return (Criteria) this;
        }

        public Criteria andEnameEqualTo(String value) {
            addCriterion("ename =", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotEqualTo(String value) {
            addCriterion("ename <>", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameGreaterThan(String value) {
            addCriterion("ename >", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameGreaterThanOrEqualTo(String value) {
            addCriterion("ename >=", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLessThan(String value) {
            addCriterion("ename <", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLessThanOrEqualTo(String value) {
            addCriterion("ename <=", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLike(String value) {
            addCriterion("ename like", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotLike(String value) {
            addCriterion("ename not like", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameIn(List<String> values) {
            addCriterion("ename in", values, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotIn(List<String> values) {
            addCriterion("ename not in", values, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameBetween(String value1, String value2) {
            addCriterion("ename between", value1, value2, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotBetween(String value1, String value2) {
            addCriterion("ename not between", value1, value2, "ename");
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

        public Criteria andCustomertypeIsNull() {
            addCriterion("CustomerType is null");
            return (Criteria) this;
        }

        public Criteria andCustomertypeIsNotNull() {
            addCriterion("CustomerType is not null");
            return (Criteria) this;
        }

        public Criteria andCustomertypeEqualTo(String value) {
            addCriterion("CustomerType =", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotEqualTo(String value) {
            addCriterion("CustomerType <>", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeGreaterThan(String value) {
            addCriterion("CustomerType >", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerType >=", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLessThan(String value) {
            addCriterion("CustomerType <", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLessThanOrEqualTo(String value) {
            addCriterion("CustomerType <=", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeLike(String value) {
            addCriterion("CustomerType like", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotLike(String value) {
            addCriterion("CustomerType not like", value, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeIn(List<String> values) {
            addCriterion("CustomerType in", values, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotIn(List<String> values) {
            addCriterion("CustomerType not in", values, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeBetween(String value1, String value2) {
            addCriterion("CustomerType between", value1, value2, "customertype");
            return (Criteria) this;
        }

        public Criteria andCustomertypeNotBetween(String value1, String value2) {
            addCriterion("CustomerType not between", value1, value2, "customertype");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidIsNull() {
            addCriterion("SMCGroupId is null");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidIsNotNull() {
            addCriterion("SMCGroupId is not null");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidEqualTo(String value) {
            addCriterion("SMCGroupId =", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotEqualTo(String value) {
            addCriterion("SMCGroupId <>", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidGreaterThan(String value) {
            addCriterion("SMCGroupId >", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidGreaterThanOrEqualTo(String value) {
            addCriterion("SMCGroupId >=", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLessThan(String value) {
            addCriterion("SMCGroupId <", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLessThanOrEqualTo(String value) {
            addCriterion("SMCGroupId <=", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidLike(String value) {
            addCriterion("SMCGroupId like", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotLike(String value) {
            addCriterion("SMCGroupId not like", value, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidIn(List<String> values) {
            addCriterion("SMCGroupId in", values, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotIn(List<String> values) {
            addCriterion("SMCGroupId not in", values, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidBetween(String value1, String value2) {
            addCriterion("SMCGroupId between", value1, value2, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andSmcgroupidNotBetween(String value1, String value2) {
            addCriterion("SMCGroupId not between", value1, value2, "smcgroupid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidIsNull() {
            addCriterion("tradeSubjectId is null");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidIsNotNull() {
            addCriterion("tradeSubjectId is not null");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidEqualTo(String value) {
            addCriterion("tradeSubjectId =", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidNotEqualTo(String value) {
            addCriterion("tradeSubjectId <>", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidGreaterThan(String value) {
            addCriterion("tradeSubjectId >", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidGreaterThanOrEqualTo(String value) {
            addCriterion("tradeSubjectId >=", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidLessThan(String value) {
            addCriterion("tradeSubjectId <", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidLessThanOrEqualTo(String value) {
            addCriterion("tradeSubjectId <=", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidLike(String value) {
            addCriterion("tradeSubjectId like", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidNotLike(String value) {
            addCriterion("tradeSubjectId not like", value, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidIn(List<String> values) {
            addCriterion("tradeSubjectId in", values, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidNotIn(List<String> values) {
            addCriterion("tradeSubjectId not in", values, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidBetween(String value1, String value2) {
            addCriterion("tradeSubjectId between", value1, value2, "tradesubjectid");
            return (Criteria) this;
        }

        public Criteria andTradesubjectidNotBetween(String value1, String value2) {
            addCriterion("tradeSubjectId not between", value1, value2, "tradesubjectid");
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

        public Criteria andBankIsNull() {
            addCriterion("bank is null");
            return (Criteria) this;
        }

        public Criteria andBankIsNotNull() {
            addCriterion("bank is not null");
            return (Criteria) this;
        }

        public Criteria andBankEqualTo(String value) {
            addCriterion("bank =", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotEqualTo(String value) {
            addCriterion("bank <>", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThan(String value) {
            addCriterion("bank >", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThanOrEqualTo(String value) {
            addCriterion("bank >=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThan(String value) {
            addCriterion("bank <", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThanOrEqualTo(String value) {
            addCriterion("bank <=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLike(String value) {
            addCriterion("bank like", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotLike(String value) {
            addCriterion("bank not like", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankIn(List<String> values) {
            addCriterion("bank in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotIn(List<String> values) {
            addCriterion("bank not in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankBetween(String value1, String value2) {
            addCriterion("bank between", value1, value2, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotBetween(String value1, String value2) {
            addCriterion("bank not between", value1, value2, "bank");
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

        public Criteria andInvoicephonenoIsNull() {
            addCriterion("InvoicePhoneNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoIsNotNull() {
            addCriterion("InvoicePhoneNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoEqualTo(String value) {
            addCriterion("InvoicePhoneNo =", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoNotEqualTo(String value) {
            addCriterion("InvoicePhoneNo <>", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoGreaterThan(String value) {
            addCriterion("InvoicePhoneNo >", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoGreaterThanOrEqualTo(String value) {
            addCriterion("InvoicePhoneNo >=", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoLessThan(String value) {
            addCriterion("InvoicePhoneNo <", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoLessThanOrEqualTo(String value) {
            addCriterion("InvoicePhoneNo <=", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoLike(String value) {
            addCriterion("InvoicePhoneNo like", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoNotLike(String value) {
            addCriterion("InvoicePhoneNo not like", value, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoIn(List<String> values) {
            addCriterion("InvoicePhoneNo in", values, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoNotIn(List<String> values) {
            addCriterion("InvoicePhoneNo not in", values, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoBetween(String value1, String value2) {
            addCriterion("InvoicePhoneNo between", value1, value2, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicephonenoNotBetween(String value1, String value2) {
            addCriterion("InvoicePhoneNo not between", value1, value2, "invoicephoneno");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIsNull() {
            addCriterion("InvoiceType is null");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIsNotNull() {
            addCriterion("InvoiceType is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeEqualTo(String value) {
            addCriterion("InvoiceType =", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotEqualTo(String value) {
            addCriterion("InvoiceType <>", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeGreaterThan(String value) {
            addCriterion("InvoiceType >", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceType >=", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeLessThan(String value) {
            addCriterion("InvoiceType <", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeLessThanOrEqualTo(String value) {
            addCriterion("InvoiceType <=", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeLike(String value) {
            addCriterion("InvoiceType like", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotLike(String value) {
            addCriterion("InvoiceType not like", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIn(List<String> values) {
            addCriterion("InvoiceType in", values, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotIn(List<String> values) {
            addCriterion("InvoiceType not in", values, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeBetween(String value1, String value2) {
            addCriterion("InvoiceType between", value1, value2, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotBetween(String value1, String value2) {
            addCriterion("InvoiceType not between", value1, value2, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
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

        public Criteria andCustomerlevelIsNull() {
            addCriterion("customerLevel is null");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelIsNotNull() {
            addCriterion("customerLevel is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelEqualTo(Integer value) {
            addCriterion("customerLevel =", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelNotEqualTo(Integer value) {
            addCriterion("customerLevel <>", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelGreaterThan(Integer value) {
            addCriterion("customerLevel >", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("customerLevel >=", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelLessThan(Integer value) {
            addCriterion("customerLevel <", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelLessThanOrEqualTo(Integer value) {
            addCriterion("customerLevel <=", value, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelIn(List<Integer> values) {
            addCriterion("customerLevel in", values, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelNotIn(List<Integer> values) {
            addCriterion("customerLevel not in", values, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelBetween(Integer value1, Integer value2) {
            addCriterion("customerLevel between", value1, value2, "customerlevel");
            return (Criteria) this;
        }

        public Criteria andCustomerlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("customerLevel not between", value1, value2, "customerlevel");
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

        public Criteria andAliasEnameIsNull() {
            addCriterion("alias_ename is null");
            return (Criteria) this;
        }

        public Criteria andAliasEnameIsNotNull() {
            addCriterion("alias_ename is not null");
            return (Criteria) this;
        }

        public Criteria andAliasEnameEqualTo(String value) {
            addCriterion("alias_ename =", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameNotEqualTo(String value) {
            addCriterion("alias_ename <>", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameGreaterThan(String value) {
            addCriterion("alias_ename >", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameGreaterThanOrEqualTo(String value) {
            addCriterion("alias_ename >=", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameLessThan(String value) {
            addCriterion("alias_ename <", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameLessThanOrEqualTo(String value) {
            addCriterion("alias_ename <=", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameLike(String value) {
            addCriterion("alias_ename like", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameNotLike(String value) {
            addCriterion("alias_ename not like", value, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameIn(List<String> values) {
            addCriterion("alias_ename in", values, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameNotIn(List<String> values) {
            addCriterion("alias_ename not in", values, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameBetween(String value1, String value2) {
            addCriterion("alias_ename between", value1, value2, "aliasEname");
            return (Criteria) this;
        }

        public Criteria andAliasEnameNotBetween(String value1, String value2) {
            addCriterion("alias_ename not between", value1, value2, "aliasEname");
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
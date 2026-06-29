package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersalesBjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrdersalesBjExample() {
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
            addCriterion("ROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("ROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("ROrderNo =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("ROrderNo <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("ROrderNo >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ROrderNo >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("ROrderNo <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("ROrderNo <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("ROrderNo like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("ROrderNo not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("ROrderNo in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("ROrderNo not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("ROrderNo between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("ROrderNo not between", value1, value2, "rorderno");
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

        public Criteria andDlventireIsNull() {
            addCriterion("DlvEntire is null");
            return (Criteria) this;
        }

        public Criteria andDlventireIsNotNull() {
            addCriterion("DlvEntire is not null");
            return (Criteria) this;
        }

        public Criteria andDlventireEqualTo(String value) {
            addCriterion("DlvEntire =", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotEqualTo(String value) {
            addCriterion("DlvEntire <>", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThan(String value) {
            addCriterion("DlvEntire >", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireGreaterThanOrEqualTo(String value) {
            addCriterion("DlvEntire >=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThan(String value) {
            addCriterion("DlvEntire <", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLessThanOrEqualTo(String value) {
            addCriterion("DlvEntire <=", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireLike(String value) {
            addCriterion("DlvEntire like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotLike(String value) {
            addCriterion("DlvEntire not like", value, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireIn(List<String> values) {
            addCriterion("DlvEntire in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotIn(List<String> values) {
            addCriterion("DlvEntire not in", values, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireBetween(String value1, String value2) {
            addCriterion("DlvEntire between", value1, value2, "dlventire");
            return (Criteria) this;
        }

        public Criteria andDlventireNotBetween(String value1, String value2) {
            addCriterion("DlvEntire not between", value1, value2, "dlventire");
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

        public Criteria andTranschannelIsNull() {
            addCriterion("TransChannel is null");
            return (Criteria) this;
        }

        public Criteria andTranschannelIsNotNull() {
            addCriterion("TransChannel is not null");
            return (Criteria) this;
        }

        public Criteria andTranschannelEqualTo(String value) {
            addCriterion("TransChannel =", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotEqualTo(String value) {
            addCriterion("TransChannel <>", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThan(String value) {
            addCriterion("TransChannel >", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelGreaterThanOrEqualTo(String value) {
            addCriterion("TransChannel >=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThan(String value) {
            addCriterion("TransChannel <", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLessThanOrEqualTo(String value) {
            addCriterion("TransChannel <=", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelLike(String value) {
            addCriterion("TransChannel like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotLike(String value) {
            addCriterion("TransChannel not like", value, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelIn(List<String> values) {
            addCriterion("TransChannel in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotIn(List<String> values) {
            addCriterion("TransChannel not in", values, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelBetween(String value1, String value2) {
            addCriterion("TransChannel between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andTranschannelNotBetween(String value1, String value2) {
            addCriterion("TransChannel not between", value1, value2, "transchannel");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNull() {
            addCriterion("DlvSite is null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIsNotNull() {
            addCriterion("DlvSite is not null");
            return (Criteria) this;
        }

        public Criteria andDlvsiteEqualTo(String value) {
            addCriterion("DlvSite =", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotEqualTo(String value) {
            addCriterion("DlvSite <>", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThan(String value) {
            addCriterion("DlvSite >", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteGreaterThanOrEqualTo(String value) {
            addCriterion("DlvSite >=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThan(String value) {
            addCriterion("DlvSite <", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLessThanOrEqualTo(String value) {
            addCriterion("DlvSite <=", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteLike(String value) {
            addCriterion("DlvSite like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotLike(String value) {
            addCriterion("DlvSite not like", value, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteIn(List<String> values) {
            addCriterion("DlvSite in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotIn(List<String> values) {
            addCriterion("DlvSite not in", values, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteBetween(String value1, String value2) {
            addCriterion("DlvSite between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvsiteNotBetween(String value1, String value2) {
            addCriterion("DlvSite not between", value1, value2, "dlvsite");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNull() {
            addCriterion("DlvType is null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIsNotNull() {
            addCriterion("DlvType is not null");
            return (Criteria) this;
        }

        public Criteria andDlvtypeEqualTo(String value) {
            addCriterion("DlvType =", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotEqualTo(String value) {
            addCriterion("DlvType <>", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThan(String value) {
            addCriterion("DlvType >", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeGreaterThanOrEqualTo(String value) {
            addCriterion("DlvType >=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThan(String value) {
            addCriterion("DlvType <", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLessThanOrEqualTo(String value) {
            addCriterion("DlvType <=", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeLike(String value) {
            addCriterion("DlvType like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotLike(String value) {
            addCriterion("DlvType not like", value, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeIn(List<String> values) {
            addCriterion("DlvType in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotIn(List<String> values) {
            addCriterion("DlvType not in", values, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeBetween(String value1, String value2) {
            addCriterion("DlvType between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andDlvtypeNotBetween(String value1, String value2) {
            addCriterion("DlvType not between", value1, value2, "dlvtype");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNull() {
            addCriterion("ContractNo is null");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNotNull() {
            addCriterion("ContractNo is not null");
            return (Criteria) this;
        }

        public Criteria andContractnoEqualTo(String value) {
            addCriterion("ContractNo =", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotEqualTo(String value) {
            addCriterion("ContractNo <>", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThan(String value) {
            addCriterion("ContractNo >", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThanOrEqualTo(String value) {
            addCriterion("ContractNo >=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThan(String value) {
            addCriterion("ContractNo <", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThanOrEqualTo(String value) {
            addCriterion("ContractNo <=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLike(String value) {
            addCriterion("ContractNo like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotLike(String value) {
            addCriterion("ContractNo not like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoIn(List<String> values) {
            addCriterion("ContractNo in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotIn(List<String> values) {
            addCriterion("ContractNo not in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoBetween(String value1, String value2) {
            addCriterion("ContractNo between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotBetween(String value1, String value2) {
            addCriterion("ContractNo not between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIsNull() {
            addCriterion("QuotationNo is null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIsNotNull() {
            addCriterion("QuotationNo is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationnoEqualTo(String value) {
            addCriterion("QuotationNo =", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotEqualTo(String value) {
            addCriterion("QuotationNo <>", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThan(String value) {
            addCriterion("QuotationNo >", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoGreaterThanOrEqualTo(String value) {
            addCriterion("QuotationNo >=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThan(String value) {
            addCriterion("QuotationNo <", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLessThanOrEqualTo(String value) {
            addCriterion("QuotationNo <=", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoLike(String value) {
            addCriterion("QuotationNo like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotLike(String value) {
            addCriterion("QuotationNo not like", value, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoIn(List<String> values) {
            addCriterion("QuotationNo in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotIn(List<String> values) {
            addCriterion("QuotationNo not in", values, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoBetween(String value1, String value2) {
            addCriterion("QuotationNo between", value1, value2, "quotationno");
            return (Criteria) this;
        }

        public Criteria andQuotationnoNotBetween(String value1, String value2) {
            addCriterion("QuotationNo not between", value1, value2, "quotationno");
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

        public Criteria andOplogIsNull() {
            addCriterion("OpLog is null");
            return (Criteria) this;
        }

        public Criteria andOplogIsNotNull() {
            addCriterion("OpLog is not null");
            return (Criteria) this;
        }

        public Criteria andOplogEqualTo(String value) {
            addCriterion("OpLog =", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotEqualTo(String value) {
            addCriterion("OpLog <>", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogGreaterThan(String value) {
            addCriterion("OpLog >", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogGreaterThanOrEqualTo(String value) {
            addCriterion("OpLog >=", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLessThan(String value) {
            addCriterion("OpLog <", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLessThanOrEqualTo(String value) {
            addCriterion("OpLog <=", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogLike(String value) {
            addCriterion("OpLog like", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotLike(String value) {
            addCriterion("OpLog not like", value, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogIn(List<String> values) {
            addCriterion("OpLog in", values, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotIn(List<String> values) {
            addCriterion("OpLog not in", values, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogBetween(String value1, String value2) {
            addCriterion("OpLog between", value1, value2, "oplog");
            return (Criteria) this;
        }

        public Criteria andOplogNotBetween(String value1, String value2) {
            addCriterion("OpLog not between", value1, value2, "oplog");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNull() {
            addCriterion("WorkDay is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayIsNotNull() {
            addCriterion("WorkDay is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayEqualTo(Date value) {
            addCriterion("WorkDay =", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotEqualTo(Date value) {
            addCriterion("WorkDay <>", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThan(Date value) {
            addCriterion("WorkDay >", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayGreaterThanOrEqualTo(Date value) {
            addCriterion("WorkDay >=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThan(Date value) {
            addCriterion("WorkDay <", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayLessThanOrEqualTo(Date value) {
            addCriterion("WorkDay <=", value, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayIn(List<Date> values) {
            addCriterion("WorkDay in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotIn(List<Date> values) {
            addCriterion("WorkDay not in", values, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayBetween(Date value1, Date value2) {
            addCriterion("WorkDay between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andWorkdayNotBetween(Date value1, Date value2) {
            addCriterion("WorkDay not between", value1, value2, "workday");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNull() {
            addCriterion("Sendout is null");
            return (Criteria) this;
        }

        public Criteria andSendoutIsNotNull() {
            addCriterion("Sendout is not null");
            return (Criteria) this;
        }

        public Criteria andSendoutEqualTo(String value) {
            addCriterion("Sendout =", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotEqualTo(String value) {
            addCriterion("Sendout <>", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThan(String value) {
            addCriterion("Sendout >", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutGreaterThanOrEqualTo(String value) {
            addCriterion("Sendout >=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThan(String value) {
            addCriterion("Sendout <", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLessThanOrEqualTo(String value) {
            addCriterion("Sendout <=", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutLike(String value) {
            addCriterion("Sendout like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotLike(String value) {
            addCriterion("Sendout not like", value, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutIn(List<String> values) {
            addCriterion("Sendout in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotIn(List<String> values) {
            addCriterion("Sendout not in", values, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutBetween(String value1, String value2) {
            addCriterion("Sendout between", value1, value2, "sendout");
            return (Criteria) this;
        }

        public Criteria andSendoutNotBetween(String value1, String value2) {
            addCriterion("Sendout not between", value1, value2, "sendout");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("Status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("Status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNull() {
            addCriterion("SendDay is null");
            return (Criteria) this;
        }

        public Criteria andSenddayIsNotNull() {
            addCriterion("SendDay is not null");
            return (Criteria) this;
        }

        public Criteria andSenddayEqualTo(Date value) {
            addCriterion("SendDay =", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotEqualTo(Date value) {
            addCriterion("SendDay <>", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThan(Date value) {
            addCriterion("SendDay >", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayGreaterThanOrEqualTo(Date value) {
            addCriterion("SendDay >=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThan(Date value) {
            addCriterion("SendDay <", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayLessThanOrEqualTo(Date value) {
            addCriterion("SendDay <=", value, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayIn(List<Date> values) {
            addCriterion("SendDay in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotIn(List<Date> values) {
            addCriterion("SendDay not in", values, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayBetween(Date value1, Date value2) {
            addCriterion("SendDay between", value1, value2, "sendday");
            return (Criteria) this;
        }

        public Criteria andSenddayNotBetween(Date value1, Date value2) {
            addCriterion("SendDay not between", value1, value2, "sendday");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNull() {
            addCriterion("ROrderItem is null");
            return (Criteria) this;
        }

        public Criteria andRorderitemIsNotNull() {
            addCriterion("ROrderItem is not null");
            return (Criteria) this;
        }

        public Criteria andRorderitemEqualTo(Short value) {
            addCriterion("ROrderItem =", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotEqualTo(Short value) {
            addCriterion("ROrderItem <>", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThan(Short value) {
            addCriterion("ROrderItem >", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemGreaterThanOrEqualTo(Short value) {
            addCriterion("ROrderItem >=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThan(Short value) {
            addCriterion("ROrderItem <", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemLessThanOrEqualTo(Short value) {
            addCriterion("ROrderItem <=", value, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemIn(List<Short> values) {
            addCriterion("ROrderItem in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotIn(List<Short> values) {
            addCriterion("ROrderItem not in", values, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemBetween(Short value1, Short value2) {
            addCriterion("ROrderItem between", value1, value2, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andRorderitemNotBetween(Short value1, Short value2) {
            addCriterion("ROrderItem not between", value1, value2, "rorderitem");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("Quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("Quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("Quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("Quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("Quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("Quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("Quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("Quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("Quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("Quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("Quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("Price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("Price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("Price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("Price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("Price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("Price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("Price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("Price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNull() {
            addCriterion("DlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("DlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterion("DlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterion("DlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterion("DlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterion("DlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterion("DlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterion("DlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterion("DlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate not between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIsNull() {
            addCriterion("RcvClassify is null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIsNotNull() {
            addCriterion("RcvClassify is not null");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyEqualTo(String value) {
            addCriterion("RcvClassify =", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotEqualTo(String value) {
            addCriterion("RcvClassify <>", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThan(String value) {
            addCriterion("RcvClassify >", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyGreaterThanOrEqualTo(String value) {
            addCriterion("RcvClassify >=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThan(String value) {
            addCriterion("RcvClassify <", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLessThanOrEqualTo(String value) {
            addCriterion("RcvClassify <=", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyLike(String value) {
            addCriterion("RcvClassify like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotLike(String value) {
            addCriterion("RcvClassify not like", value, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyIn(List<String> values) {
            addCriterion("RcvClassify in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotIn(List<String> values) {
            addCriterion("RcvClassify not in", values, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyBetween(String value1, String value2) {
            addCriterion("RcvClassify between", value1, value2, "rcvclassify");
            return (Criteria) this;
        }

        public Criteria andRcvclassifyNotBetween(String value1, String value2) {
            addCriterion("RcvClassify not between", value1, value2, "rcvclassify");
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

        public Criteria andCproductnoIsNull() {
            addCriterion("CProductNo is null");
            return (Criteria) this;
        }

        public Criteria andCproductnoIsNotNull() {
            addCriterion("CProductNo is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnoEqualTo(String value) {
            addCriterion("CProductNo =", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotEqualTo(String value) {
            addCriterion("CProductNo <>", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThan(String value) {
            addCriterion("CProductNo >", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoGreaterThanOrEqualTo(String value) {
            addCriterion("CProductNo >=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThan(String value) {
            addCriterion("CProductNo <", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLessThanOrEqualTo(String value) {
            addCriterion("CProductNo <=", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoLike(String value) {
            addCriterion("CProductNo like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotLike(String value) {
            addCriterion("CProductNo not like", value, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoIn(List<String> values) {
            addCriterion("CProductNo in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotIn(List<String> values) {
            addCriterion("CProductNo not in", values, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoBetween(String value1, String value2) {
            addCriterion("CProductNo between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andCproductnoNotBetween(String value1, String value2) {
            addCriterion("CProductNo not between", value1, value2, "cproductno");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNull() {
            addCriterion("OrdTransType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIsNotNull() {
            addCriterion("OrdTransType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeEqualTo(String value) {
            addCriterion("OrdTransType =", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotEqualTo(String value) {
            addCriterion("OrdTransType <>", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThan(String value) {
            addCriterion("OrdTransType >", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdTransType >=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThan(String value) {
            addCriterion("OrdTransType <", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLessThanOrEqualTo(String value) {
            addCriterion("OrdTransType <=", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeLike(String value) {
            addCriterion("OrdTransType like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotLike(String value) {
            addCriterion("OrdTransType not like", value, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeIn(List<String> values) {
            addCriterion("OrdTransType in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotIn(List<String> values) {
            addCriterion("OrdTransType not in", values, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeBetween(String value1, String value2) {
            addCriterion("OrdTransType between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andOrdtranstypeNotBetween(String value1, String value2) {
            addCriterion("OrdTransType not between", value1, value2, "ordtranstype");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIsNull() {
            addCriterion("SpcPrice is null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIsNotNull() {
            addCriterion("SpcPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSpcpriceEqualTo(String value) {
            addCriterion("SpcPrice =", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotEqualTo(String value) {
            addCriterion("SpcPrice <>", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThan(String value) {
            addCriterion("SpcPrice >", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceGreaterThanOrEqualTo(String value) {
            addCriterion("SpcPrice >=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThan(String value) {
            addCriterion("SpcPrice <", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLessThanOrEqualTo(String value) {
            addCriterion("SpcPrice <=", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceLike(String value) {
            addCriterion("SpcPrice like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotLike(String value) {
            addCriterion("SpcPrice not like", value, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceIn(List<String> values) {
            addCriterion("SpcPrice in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotIn(List<String> values) {
            addCriterion("SpcPrice not in", values, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceBetween(String value1, String value2) {
            addCriterion("SpcPrice between", value1, value2, "spcprice");
            return (Criteria) this;
        }

        public Criteria andSpcpriceNotBetween(String value1, String value2) {
            addCriterion("SpcPrice not between", value1, value2, "spcprice");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNull() {
            addCriterion("NoPrice is null");
            return (Criteria) this;
        }

        public Criteria andNopriceIsNotNull() {
            addCriterion("NoPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNopriceEqualTo(String value) {
            addCriterion("NoPrice =", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotEqualTo(String value) {
            addCriterion("NoPrice <>", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThan(String value) {
            addCriterion("NoPrice >", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceGreaterThanOrEqualTo(String value) {
            addCriterion("NoPrice >=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThan(String value) {
            addCriterion("NoPrice <", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLessThanOrEqualTo(String value) {
            addCriterion("NoPrice <=", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceLike(String value) {
            addCriterion("NoPrice like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotLike(String value) {
            addCriterion("NoPrice not like", value, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceIn(List<String> values) {
            addCriterion("NoPrice in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotIn(List<String> values) {
            addCriterion("NoPrice not in", values, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceBetween(String value1, String value2) {
            addCriterion("NoPrice between", value1, value2, "noprice");
            return (Criteria) this;
        }

        public Criteria andNopriceNotBetween(String value1, String value2) {
            addCriterion("NoPrice not between", value1, value2, "noprice");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("Account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("Account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("Account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("Account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("Account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("Account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("Account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("Account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNull() {
            addCriterion("SpecMark is null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNotNull() {
            addCriterion("SpecMark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkEqualTo(String value) {
            addCriterion("SpecMark =", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotEqualTo(String value) {
            addCriterion("SpecMark <>", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThan(String value) {
            addCriterion("SpecMark >", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThanOrEqualTo(String value) {
            addCriterion("SpecMark >=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThan(String value) {
            addCriterion("SpecMark <", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThanOrEqualTo(String value) {
            addCriterion("SpecMark <=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLike(String value) {
            addCriterion("SpecMark like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotLike(String value) {
            addCriterion("SpecMark not like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIn(List<String> values) {
            addCriterion("SpecMark in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotIn(List<String> values) {
            addCriterion("SpecMark not in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkBetween(String value1, String value2) {
            addCriterion("SpecMark between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotBetween(String value1, String value2) {
            addCriterion("SpecMark not between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andRecnoIsNull() {
            addCriterion("RecNo is null");
            return (Criteria) this;
        }

        public Criteria andRecnoIsNotNull() {
            addCriterion("RecNo is not null");
            return (Criteria) this;
        }

        public Criteria andRecnoEqualTo(String value) {
            addCriterion("RecNo =", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotEqualTo(String value) {
            addCriterion("RecNo <>", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThan(String value) {
            addCriterion("RecNo >", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoGreaterThanOrEqualTo(String value) {
            addCriterion("RecNo >=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThan(String value) {
            addCriterion("RecNo <", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLessThanOrEqualTo(String value) {
            addCriterion("RecNo <=", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoLike(String value) {
            addCriterion("RecNo like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotLike(String value) {
            addCriterion("RecNo not like", value, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoIn(List<String> values) {
            addCriterion("RecNo in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotIn(List<String> values) {
            addCriterion("RecNo not in", values, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoBetween(String value1, String value2) {
            addCriterion("RecNo between", value1, value2, "recno");
            return (Criteria) this;
        }

        public Criteria andRecnoNotBetween(String value1, String value2) {
            addCriterion("RecNo not between", value1, value2, "recno");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNull() {
            addCriterion("DetailMark is null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIsNotNull() {
            addCriterion("DetailMark is not null");
            return (Criteria) this;
        }

        public Criteria andDetailmarkEqualTo(String value) {
            addCriterion("DetailMark =", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotEqualTo(String value) {
            addCriterion("DetailMark <>", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThan(String value) {
            addCriterion("DetailMark >", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkGreaterThanOrEqualTo(String value) {
            addCriterion("DetailMark >=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThan(String value) {
            addCriterion("DetailMark <", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLessThanOrEqualTo(String value) {
            addCriterion("DetailMark <=", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkLike(String value) {
            addCriterion("DetailMark like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotLike(String value) {
            addCriterion("DetailMark not like", value, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkIn(List<String> values) {
            addCriterion("DetailMark in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotIn(List<String> values) {
            addCriterion("DetailMark not in", values, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkBetween(String value1, String value2) {
            addCriterion("DetailMark between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andDetailmarkNotBetween(String value1, String value2) {
            addCriterion("DetailMark not between", value1, value2, "detailmark");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNull() {
            addCriterion("OptDate is null");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNotNull() {
            addCriterion("OptDate is not null");
            return (Criteria) this;
        }

        public Criteria andOptdateEqualTo(Date value) {
            addCriterion("OptDate =", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotEqualTo(Date value) {
            addCriterion("OptDate <>", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThan(Date value) {
            addCriterion("OptDate >", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OptDate >=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThan(Date value) {
            addCriterion("OptDate <", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThanOrEqualTo(Date value) {
            addCriterion("OptDate <=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateIn(List<Date> values) {
            addCriterion("OptDate in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotIn(List<Date> values) {
            addCriterion("OptDate not in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateBetween(Date value1, Date value2) {
            addCriterion("OptDate between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotBetween(Date value1, Date value2) {
            addCriterion("OptDate not between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andRowguidIsNull() {
            addCriterion("rowguid is null");
            return (Criteria) this;
        }

        public Criteria andRowguidIsNotNull() {
            addCriterion("rowguid is not null");
            return (Criteria) this;
        }

        public Criteria andRowguidEqualTo(String value) {
            addCriterion("rowguid =", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidNotEqualTo(String value) {
            addCriterion("rowguid <>", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidGreaterThan(String value) {
            addCriterion("rowguid >", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidGreaterThanOrEqualTo(String value) {
            addCriterion("rowguid >=", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidLessThan(String value) {
            addCriterion("rowguid <", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidLessThanOrEqualTo(String value) {
            addCriterion("rowguid <=", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidLike(String value) {
            addCriterion("rowguid like", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidNotLike(String value) {
            addCriterion("rowguid not like", value, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidIn(List<String> values) {
            addCriterion("rowguid in", values, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidNotIn(List<String> values) {
            addCriterion("rowguid not in", values, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidBetween(String value1, String value2) {
            addCriterion("rowguid between", value1, value2, "rowguid");
            return (Criteria) this;
        }

        public Criteria andRowguidNotBetween(String value1, String value2) {
            addCriterion("rowguid not between", value1, value2, "rowguid");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIsNull() {
            addCriterion("EmpSale is null");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIsNotNull() {
            addCriterion("EmpSale is not null");
            return (Criteria) this;
        }

        public Criteria andEmpsaleEqualTo(String value) {
            addCriterion("EmpSale =", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotEqualTo(String value) {
            addCriterion("EmpSale <>", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleGreaterThan(String value) {
            addCriterion("EmpSale >", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleGreaterThanOrEqualTo(String value) {
            addCriterion("EmpSale >=", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLessThan(String value) {
            addCriterion("EmpSale <", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLessThanOrEqualTo(String value) {
            addCriterion("EmpSale <=", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleLike(String value) {
            addCriterion("EmpSale like", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotLike(String value) {
            addCriterion("EmpSale not like", value, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleIn(List<String> values) {
            addCriterion("EmpSale in", values, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotIn(List<String> values) {
            addCriterion("EmpSale not in", values, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleBetween(String value1, String value2) {
            addCriterion("EmpSale between", value1, value2, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpsaleNotBetween(String value1, String value2) {
            addCriterion("EmpSale not between", value1, value2, "empsale");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIsNull() {
            addCriterion("EmpOffice is null");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIsNotNull() {
            addCriterion("EmpOffice is not null");
            return (Criteria) this;
        }

        public Criteria andEmpofficeEqualTo(String value) {
            addCriterion("EmpOffice =", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotEqualTo(String value) {
            addCriterion("EmpOffice <>", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeGreaterThan(String value) {
            addCriterion("EmpOffice >", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeGreaterThanOrEqualTo(String value) {
            addCriterion("EmpOffice >=", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLessThan(String value) {
            addCriterion("EmpOffice <", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLessThanOrEqualTo(String value) {
            addCriterion("EmpOffice <=", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeLike(String value) {
            addCriterion("EmpOffice like", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotLike(String value) {
            addCriterion("EmpOffice not like", value, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeIn(List<String> values) {
            addCriterion("EmpOffice in", values, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotIn(List<String> values) {
            addCriterion("EmpOffice not in", values, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeBetween(String value1, String value2) {
            addCriterion("EmpOffice between", value1, value2, "empoffice");
            return (Criteria) this;
        }

        public Criteria andEmpofficeNotBetween(String value1, String value2) {
            addCriterion("EmpOffice not between", value1, value2, "empoffice");
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

        public Criteria andPurchasenoIsNull() {
            addCriterion("PurchaseNo is null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIsNotNull() {
            addCriterion("PurchaseNo is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasenoEqualTo(String value) {
            addCriterion("PurchaseNo =", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotEqualTo(String value) {
            addCriterion("PurchaseNo <>", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThan(String value) {
            addCriterion("PurchaseNo >", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseNo >=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThan(String value) {
            addCriterion("PurchaseNo <", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseNo <=", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoLike(String value) {
            addCriterion("PurchaseNo like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotLike(String value) {
            addCriterion("PurchaseNo not like", value, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoIn(List<String> values) {
            addCriterion("PurchaseNo in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotIn(List<String> values) {
            addCriterion("PurchaseNo not in", values, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoBetween(String value1, String value2) {
            addCriterion("PurchaseNo between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andPurchasenoNotBetween(String value1, String value2) {
            addCriterion("PurchaseNo not between", value1, value2, "purchaseno");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNull() {
            addCriterion("Shikomi is null");
            return (Criteria) this;
        }

        public Criteria andShikomiIsNotNull() {
            addCriterion("Shikomi is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiEqualTo(String value) {
            addCriterion("Shikomi =", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotEqualTo(String value) {
            addCriterion("Shikomi <>", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThan(String value) {
            addCriterion("Shikomi >", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiGreaterThanOrEqualTo(String value) {
            addCriterion("Shikomi >=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThan(String value) {
            addCriterion("Shikomi <", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLessThanOrEqualTo(String value) {
            addCriterion("Shikomi <=", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiLike(String value) {
            addCriterion("Shikomi like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotLike(String value) {
            addCriterion("Shikomi not like", value, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiIn(List<String> values) {
            addCriterion("Shikomi in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotIn(List<String> values) {
            addCriterion("Shikomi not in", values, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiBetween(String value1, String value2) {
            addCriterion("Shikomi between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andShikomiNotBetween(String value1, String value2) {
            addCriterion("Shikomi not between", value1, value2, "shikomi");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNull() {
            addCriterion("Price_EndUser is null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIsNotNull() {
            addCriterion("Price_EndUser is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser =", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <>", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThan(BigDecimal value) {
            addCriterion("Price_EndUser >", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser >=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThan(BigDecimal value) {
            addCriterion("Price_EndUser <", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_EndUser <=", value, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotIn(List<BigDecimal> values) {
            addCriterion("Price_EndUser not in", values, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andPriceEnduserNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_EndUser not between", value1, value2, "priceEnduser");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNull() {
            addCriterion("cttFlag is null");
            return (Criteria) this;
        }

        public Criteria andCttflagIsNotNull() {
            addCriterion("cttFlag is not null");
            return (Criteria) this;
        }

        public Criteria andCttflagEqualTo(String value) {
            addCriterion("cttFlag =", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotEqualTo(String value) {
            addCriterion("cttFlag <>", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThan(String value) {
            addCriterion("cttFlag >", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagGreaterThanOrEqualTo(String value) {
            addCriterion("cttFlag >=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThan(String value) {
            addCriterion("cttFlag <", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLessThanOrEqualTo(String value) {
            addCriterion("cttFlag <=", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagLike(String value) {
            addCriterion("cttFlag like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotLike(String value) {
            addCriterion("cttFlag not like", value, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagIn(List<String> values) {
            addCriterion("cttFlag in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotIn(List<String> values) {
            addCriterion("cttFlag not in", values, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagBetween(String value1, String value2) {
            addCriterion("cttFlag between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andCttflagNotBetween(String value1, String value2) {
            addCriterion("cttFlag not between", value1, value2, "cttflag");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIsNull() {
            addCriterion("WarehouseSendDate is null");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIsNotNull() {
            addCriterion("WarehouseSendDate is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateEqualTo(Date value) {
            addCriterion("WarehouseSendDate =", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotEqualTo(Date value) {
            addCriterion("WarehouseSendDate <>", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateGreaterThan(Date value) {
            addCriterion("WarehouseSendDate >", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateGreaterThanOrEqualTo(Date value) {
            addCriterion("WarehouseSendDate >=", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateLessThan(Date value) {
            addCriterion("WarehouseSendDate <", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateLessThanOrEqualTo(Date value) {
            addCriterion("WarehouseSendDate <=", value, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateIn(List<Date> values) {
            addCriterion("WarehouseSendDate in", values, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotIn(List<Date> values) {
            addCriterion("WarehouseSendDate not in", values, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateBetween(Date value1, Date value2) {
            addCriterion("WarehouseSendDate between", value1, value2, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andWarehousesenddateNotBetween(Date value1, Date value2) {
            addCriterion("WarehouseSendDate not between", value1, value2, "warehousesenddate");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNull() {
            addCriterion("CProductName is null");
            return (Criteria) this;
        }

        public Criteria andCproductnameIsNotNull() {
            addCriterion("CProductName is not null");
            return (Criteria) this;
        }

        public Criteria andCproductnameEqualTo(String value) {
            addCriterion("CProductName =", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotEqualTo(String value) {
            addCriterion("CProductName <>", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThan(String value) {
            addCriterion("CProductName >", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameGreaterThanOrEqualTo(String value) {
            addCriterion("CProductName >=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThan(String value) {
            addCriterion("CProductName <", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLessThanOrEqualTo(String value) {
            addCriterion("CProductName <=", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameLike(String value) {
            addCriterion("CProductName like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotLike(String value) {
            addCriterion("CProductName not like", value, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameIn(List<String> values) {
            addCriterion("CProductName in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotIn(List<String> values) {
            addCriterion("CProductName not in", values, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameBetween(String value1, String value2) {
            addCriterion("CProductName between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andCproductnameNotBetween(String value1, String value2) {
            addCriterion("CProductName not between", value1, value2, "cproductname");
            return (Criteria) this;
        }

        public Criteria andOptrecordIsNull() {
            addCriterion("OptRecord is null");
            return (Criteria) this;
        }

        public Criteria andOptrecordIsNotNull() {
            addCriterion("OptRecord is not null");
            return (Criteria) this;
        }

        public Criteria andOptrecordEqualTo(String value) {
            addCriterion("OptRecord =", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotEqualTo(String value) {
            addCriterion("OptRecord <>", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordGreaterThan(String value) {
            addCriterion("OptRecord >", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordGreaterThanOrEqualTo(String value) {
            addCriterion("OptRecord >=", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLessThan(String value) {
            addCriterion("OptRecord <", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLessThanOrEqualTo(String value) {
            addCriterion("OptRecord <=", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordLike(String value) {
            addCriterion("OptRecord like", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotLike(String value) {
            addCriterion("OptRecord not like", value, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordIn(List<String> values) {
            addCriterion("OptRecord in", values, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotIn(List<String> values) {
            addCriterion("OptRecord not in", values, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordBetween(String value1, String value2) {
            addCriterion("OptRecord between", value1, value2, "optrecord");
            return (Criteria) this;
        }

        public Criteria andOptrecordNotBetween(String value1, String value2) {
            addCriterion("OptRecord not between", value1, value2, "optrecord");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNull() {
            addCriterion("PreSaleOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIsNotNull() {
            addCriterion("PreSaleOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoEqualTo(String value) {
            addCriterion("PreSaleOrderNo =", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotEqualTo(String value) {
            addCriterion("PreSaleOrderNo <>", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThan(String value) {
            addCriterion("PreSaleOrderNo >", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoGreaterThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo >=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThan(String value) {
            addCriterion("PreSaleOrderNo <", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLessThanOrEqualTo(String value) {
            addCriterion("PreSaleOrderNo <=", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoLike(String value) {
            addCriterion("PreSaleOrderNo like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotLike(String value) {
            addCriterion("PreSaleOrderNo not like", value, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoIn(List<String> values) {
            addCriterion("PreSaleOrderNo in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotIn(List<String> values) {
            addCriterion("PreSaleOrderNo not in", values, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andPresaleordernoNotBetween(String value1, String value2) {
            addCriterion("PreSaleOrderNo not between", value1, value2, "presaleorderno");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNull() {
            addCriterion("Opponent is null");
            return (Criteria) this;
        }

        public Criteria andOpponentIsNotNull() {
            addCriterion("Opponent is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentEqualTo(String value) {
            addCriterion("Opponent =", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotEqualTo(String value) {
            addCriterion("Opponent <>", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThan(String value) {
            addCriterion("Opponent >", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentGreaterThanOrEqualTo(String value) {
            addCriterion("Opponent >=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThan(String value) {
            addCriterion("Opponent <", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLessThanOrEqualTo(String value) {
            addCriterion("Opponent <=", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentLike(String value) {
            addCriterion("Opponent like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotLike(String value) {
            addCriterion("Opponent not like", value, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentIn(List<String> values) {
            addCriterion("Opponent in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotIn(List<String> values) {
            addCriterion("Opponent not in", values, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentBetween(String value1, String value2) {
            addCriterion("Opponent between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andOpponentNotBetween(String value1, String value2) {
            addCriterion("Opponent not between", value1, value2, "opponent");
            return (Criteria) this;
        }

        public Criteria andTypecodeIsNull() {
            addCriterion("TypeCode is null");
            return (Criteria) this;
        }

        public Criteria andTypecodeIsNotNull() {
            addCriterion("TypeCode is not null");
            return (Criteria) this;
        }

        public Criteria andTypecodeEqualTo(String value) {
            addCriterion("TypeCode =", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotEqualTo(String value) {
            addCriterion("TypeCode <>", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThan(String value) {
            addCriterion("TypeCode >", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeGreaterThanOrEqualTo(String value) {
            addCriterion("TypeCode >=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThan(String value) {
            addCriterion("TypeCode <", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLessThanOrEqualTo(String value) {
            addCriterion("TypeCode <=", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeLike(String value) {
            addCriterion("TypeCode like", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotLike(String value) {
            addCriterion("TypeCode not like", value, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeIn(List<String> values) {
            addCriterion("TypeCode in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotIn(List<String> values) {
            addCriterion("TypeCode not in", values, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeBetween(String value1, String value2) {
            addCriterion("TypeCode between", value1, value2, "typecode");
            return (Criteria) this;
        }

        public Criteria andTypecodeNotBetween(String value1, String value2) {
            addCriterion("TypeCode not between", value1, value2, "typecode");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIsNull() {
            addCriterion("PriceChecked is null");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIsNotNull() {
            addCriterion("PriceChecked is not null");
            return (Criteria) this;
        }

        public Criteria andPricecheckedEqualTo(Boolean value) {
            addCriterion("PriceChecked =", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotEqualTo(Boolean value) {
            addCriterion("PriceChecked <>", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedGreaterThan(Boolean value) {
            addCriterion("PriceChecked >", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("PriceChecked >=", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedLessThan(Boolean value) {
            addCriterion("PriceChecked <", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedLessThanOrEqualTo(Boolean value) {
            addCriterion("PriceChecked <=", value, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedIn(List<Boolean> values) {
            addCriterion("PriceChecked in", values, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotIn(List<Boolean> values) {
            addCriterion("PriceChecked not in", values, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedBetween(Boolean value1, Boolean value2) {
            addCriterion("PriceChecked between", value1, value2, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andPricecheckedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("PriceChecked not between", value1, value2, "pricechecked");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
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

        public Criteria andOrdersourcesysIsNull() {
            addCriterion("OrderSourceSys is null");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysIsNotNull() {
            addCriterion("OrderSourceSys is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysEqualTo(String value) {
            addCriterion("OrderSourceSys =", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotEqualTo(String value) {
            addCriterion("OrderSourceSys <>", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysGreaterThan(String value) {
            addCriterion("OrderSourceSys >", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysGreaterThanOrEqualTo(String value) {
            addCriterion("OrderSourceSys >=", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLessThan(String value) {
            addCriterion("OrderSourceSys <", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLessThanOrEqualTo(String value) {
            addCriterion("OrderSourceSys <=", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysLike(String value) {
            addCriterion("OrderSourceSys like", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotLike(String value) {
            addCriterion("OrderSourceSys not like", value, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysIn(List<String> values) {
            addCriterion("OrderSourceSys in", values, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotIn(List<String> values) {
            addCriterion("OrderSourceSys not in", values, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysBetween(String value1, String value2) {
            addCriterion("OrderSourceSys between", value1, value2, "ordersourcesys");
            return (Criteria) this;
        }

        public Criteria andOrdersourcesysNotBetween(String value1, String value2) {
            addCriterion("OrderSourceSys not between", value1, value2, "ordersourcesys");
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
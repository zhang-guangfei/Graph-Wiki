package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoInvoiceMasterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoInvoiceMasterExample() {
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

        public Criteria andInvoicenoIsNull() {
            addCriterion("InvoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("InvoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("InvoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("InvoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("InvoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("InvoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("InvoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("InvoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("InvoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("InvoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("InvoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNull() {
            addCriterion("ImpDate is null");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNotNull() {
            addCriterion("ImpDate is not null");
            return (Criteria) this;
        }

        public Criteria andImpdateEqualTo(Date value) {
            addCriterion("ImpDate =", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotEqualTo(Date value) {
            addCriterion("ImpDate <>", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThan(Date value) {
            addCriterion("ImpDate >", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("ImpDate >=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThan(Date value) {
            addCriterion("ImpDate <", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThanOrEqualTo(Date value) {
            addCriterion("ImpDate <=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateIn(List<Date> values) {
            addCriterion("ImpDate in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotIn(List<Date> values) {
            addCriterion("ImpDate not in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateBetween(Date value1, Date value2) {
            addCriterion("ImpDate between", value1, value2, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotBetween(Date value1, Date value2) {
            addCriterion("ImpDate not between", value1, value2, "impdate");
            return (Criteria) this;
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

        public Criteria andAmountjpIsNull() {
            addCriterion("AmountJp is null");
            return (Criteria) this;
        }

        public Criteria andAmountjpIsNotNull() {
            addCriterion("AmountJp is not null");
            return (Criteria) this;
        }

        public Criteria andAmountjpEqualTo(BigDecimal value) {
            addCriterion("AmountJp =", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpNotEqualTo(BigDecimal value) {
            addCriterion("AmountJp <>", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpGreaterThan(BigDecimal value) {
            addCriterion("AmountJp >", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountJp >=", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpLessThan(BigDecimal value) {
            addCriterion("AmountJp <", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountJp <=", value, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpIn(List<BigDecimal> values) {
            addCriterion("AmountJp in", values, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpNotIn(List<BigDecimal> values) {
            addCriterion("AmountJp not in", values, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountJp between", value1, value2, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountjpNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountJp not between", value1, value2, "amountjp");
            return (Criteria) this;
        }

        public Criteria andAmountrmbIsNull() {
            addCriterion("AmountRMB is null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbIsNotNull() {
            addCriterion("AmountRMB is not null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbEqualTo(BigDecimal value) {
            addCriterion("AmountRMB =", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbNotEqualTo(BigDecimal value) {
            addCriterion("AmountRMB <>", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbGreaterThan(BigDecimal value) {
            addCriterion("AmountRMB >", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB >=", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbLessThan(BigDecimal value) {
            addCriterion("AmountRMB <", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB <=", value, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbIn(List<BigDecimal> values) {
            addCriterion("AmountRMB in", values, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbNotIn(List<BigDecimal> values) {
            addCriterion("AmountRMB not in", values, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB between", value1, value2, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andAmountrmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB not between", value1, value2, "amountrmb");
            return (Criteria) this;
        }

        public Criteria andCustomfeeIsNull() {
            addCriterion("CustomFee is null");
            return (Criteria) this;
        }

        public Criteria andCustomfeeIsNotNull() {
            addCriterion("CustomFee is not null");
            return (Criteria) this;
        }

        public Criteria andCustomfeeEqualTo(BigDecimal value) {
            addCriterion("CustomFee =", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeNotEqualTo(BigDecimal value) {
            addCriterion("CustomFee <>", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeGreaterThan(BigDecimal value) {
            addCriterion("CustomFee >", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CustomFee >=", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeLessThan(BigDecimal value) {
            addCriterion("CustomFee <", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CustomFee <=", value, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeIn(List<BigDecimal> values) {
            addCriterion("CustomFee in", values, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeNotIn(List<BigDecimal> values) {
            addCriterion("CustomFee not in", values, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CustomFee between", value1, value2, "customfee");
            return (Criteria) this;
        }

        public Criteria andCustomfeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CustomFee not between", value1, value2, "customfee");
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

        public Criteria andTransfeeEqualTo(BigDecimal value) {
            addCriterion("TransFee =", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotEqualTo(BigDecimal value) {
            addCriterion("TransFee <>", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThan(BigDecimal value) {
            addCriterion("TransFee >", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TransFee >=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThan(BigDecimal value) {
            addCriterion("TransFee <", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TransFee <=", value, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeIn(List<BigDecimal> values) {
            addCriterion("TransFee in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotIn(List<BigDecimal> values) {
            addCriterion("TransFee not in", values, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TransFee between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andTransfeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TransFee not between", value1, value2, "transfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeIsNull() {
            addCriterion("OtherFee is null");
            return (Criteria) this;
        }

        public Criteria andOtherfeeIsNotNull() {
            addCriterion("OtherFee is not null");
            return (Criteria) this;
        }

        public Criteria andOtherfeeEqualTo(BigDecimal value) {
            addCriterion("OtherFee =", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeNotEqualTo(BigDecimal value) {
            addCriterion("OtherFee <>", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeGreaterThan(BigDecimal value) {
            addCriterion("OtherFee >", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OtherFee >=", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeLessThan(BigDecimal value) {
            addCriterion("OtherFee <", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OtherFee <=", value, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeIn(List<BigDecimal> values) {
            addCriterion("OtherFee in", values, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeNotIn(List<BigDecimal> values) {
            addCriterion("OtherFee not in", values, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OtherFee between", value1, value2, "otherfee");
            return (Criteria) this;
        }

        public Criteria andOtherfeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OtherFee not between", value1, value2, "otherfee");
            return (Criteria) this;
        }

        public Criteria andRateIsNull() {
            addCriterion("Rate is null");
            return (Criteria) this;
        }

        public Criteria andRateIsNotNull() {
            addCriterion("Rate is not null");
            return (Criteria) this;
        }

        public Criteria andRateEqualTo(Double value) {
            addCriterion("Rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(Double value) {
            addCriterion("Rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(Double value) {
            addCriterion("Rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(Double value) {
            addCriterion("Rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(Double value) {
            addCriterion("Rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(Double value) {
            addCriterion("Rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<Double> values) {
            addCriterion("Rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<Double> values) {
            addCriterion("Rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(Double value1, Double value2) {
            addCriterion("Rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(Double value1, Double value2) {
            addCriterion("Rate not between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andProdcountryIsNull() {
            addCriterion("ProdCountry is null");
            return (Criteria) this;
        }

        public Criteria andProdcountryIsNotNull() {
            addCriterion("ProdCountry is not null");
            return (Criteria) this;
        }

        public Criteria andProdcountryEqualTo(String value) {
            addCriterion("ProdCountry =", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryNotEqualTo(String value) {
            addCriterion("ProdCountry <>", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryGreaterThan(String value) {
            addCriterion("ProdCountry >", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryGreaterThanOrEqualTo(String value) {
            addCriterion("ProdCountry >=", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryLessThan(String value) {
            addCriterion("ProdCountry <", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryLessThanOrEqualTo(String value) {
            addCriterion("ProdCountry <=", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryLike(String value) {
            addCriterion("ProdCountry like", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryNotLike(String value) {
            addCriterion("ProdCountry not like", value, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryIn(List<String> values) {
            addCriterion("ProdCountry in", values, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryNotIn(List<String> values) {
            addCriterion("ProdCountry not in", values, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryBetween(String value1, String value2) {
            addCriterion("ProdCountry between", value1, value2, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andProdcountryNotBetween(String value1, String value2) {
            addCriterion("ProdCountry not between", value1, value2, "prodcountry");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIsNull() {
            addCriterion("MoneyType is null");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIsNotNull() {
            addCriterion("MoneyType is not null");
            return (Criteria) this;
        }

        public Criteria andMoneytypeEqualTo(String value) {
            addCriterion("MoneyType =", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotEqualTo(String value) {
            addCriterion("MoneyType <>", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeGreaterThan(String value) {
            addCriterion("MoneyType >", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeGreaterThanOrEqualTo(String value) {
            addCriterion("MoneyType >=", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeLessThan(String value) {
            addCriterion("MoneyType <", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeLessThanOrEqualTo(String value) {
            addCriterion("MoneyType <=", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeLike(String value) {
            addCriterion("MoneyType like", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotLike(String value) {
            addCriterion("MoneyType not like", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIn(List<String> values) {
            addCriterion("MoneyType in", values, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotIn(List<String> values) {
            addCriterion("MoneyType not in", values, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeBetween(String value1, String value2) {
            addCriterion("MoneyType between", value1, value2, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotBetween(String value1, String value2) {
            addCriterion("MoneyType not between", value1, value2, "moneytype");
            return (Criteria) this;
        }

        public Criteria andImpflagIsNull() {
            addCriterion("ImpFlag is null");
            return (Criteria) this;
        }

        public Criteria andImpflagIsNotNull() {
            addCriterion("ImpFlag is not null");
            return (Criteria) this;
        }

        public Criteria andImpflagEqualTo(String value) {
            addCriterion("ImpFlag =", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagNotEqualTo(String value) {
            addCriterion("ImpFlag <>", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagGreaterThan(String value) {
            addCriterion("ImpFlag >", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagGreaterThanOrEqualTo(String value) {
            addCriterion("ImpFlag >=", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagLessThan(String value) {
            addCriterion("ImpFlag <", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagLessThanOrEqualTo(String value) {
            addCriterion("ImpFlag <=", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagLike(String value) {
            addCriterion("ImpFlag like", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagNotLike(String value) {
            addCriterion("ImpFlag not like", value, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagIn(List<String> values) {
            addCriterion("ImpFlag in", values, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagNotIn(List<String> values) {
            addCriterion("ImpFlag not in", values, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagBetween(String value1, String value2) {
            addCriterion("ImpFlag between", value1, value2, "impflag");
            return (Criteria) this;
        }

        public Criteria andImpflagNotBetween(String value1, String value2) {
            addCriterion("ImpFlag not between", value1, value2, "impflag");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoIsNull() {
            addCriterion("SupplierInvoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoIsNotNull() {
            addCriterion("SupplierInvoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoEqualTo(String value) {
            addCriterion("SupplierInvoiceNo =", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoNotEqualTo(String value) {
            addCriterion("SupplierInvoiceNo <>", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoGreaterThan(String value) {
            addCriterion("SupplierInvoiceNo >", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("SupplierInvoiceNo >=", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoLessThan(String value) {
            addCriterion("SupplierInvoiceNo <", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoLessThanOrEqualTo(String value) {
            addCriterion("SupplierInvoiceNo <=", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoLike(String value) {
            addCriterion("SupplierInvoiceNo like", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoNotLike(String value) {
            addCriterion("SupplierInvoiceNo not like", value, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoIn(List<String> values) {
            addCriterion("SupplierInvoiceNo in", values, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoNotIn(List<String> values) {
            addCriterion("SupplierInvoiceNo not in", values, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoBetween(String value1, String value2) {
            addCriterion("SupplierInvoiceNo between", value1, value2, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplierinvoicenoNotBetween(String value1, String value2) {
            addCriterion("SupplierInvoiceNo not between", value1, value2, "supplierinvoiceno");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNull() {
            addCriterion("SupplierCode is null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNotNull() {
            addCriterion("SupplierCode is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeEqualTo(String value) {
            addCriterion("SupplierCode =", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotEqualTo(String value) {
            addCriterion("SupplierCode <>", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThan(String value) {
            addCriterion("SupplierCode >", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThanOrEqualTo(String value) {
            addCriterion("SupplierCode >=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThan(String value) {
            addCriterion("SupplierCode <", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThanOrEqualTo(String value) {
            addCriterion("SupplierCode <=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLike(String value) {
            addCriterion("SupplierCode like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotLike(String value) {
            addCriterion("SupplierCode not like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIn(List<String> values) {
            addCriterion("SupplierCode in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotIn(List<String> values) {
            addCriterion("SupplierCode not in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeBetween(String value1, String value2) {
            addCriterion("SupplierCode between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotBetween(String value1, String value2) {
            addCriterion("SupplierCode not between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andShipdateIsNull() {
            addCriterion("ShipDate is null");
            return (Criteria) this;
        }

        public Criteria andShipdateIsNotNull() {
            addCriterion("ShipDate is not null");
            return (Criteria) this;
        }

        public Criteria andShipdateEqualTo(Date value) {
            addCriterion("ShipDate =", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotEqualTo(Date value) {
            addCriterion("ShipDate <>", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateGreaterThan(Date value) {
            addCriterion("ShipDate >", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateGreaterThanOrEqualTo(Date value) {
            addCriterion("ShipDate >=", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateLessThan(Date value) {
            addCriterion("ShipDate <", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateLessThanOrEqualTo(Date value) {
            addCriterion("ShipDate <=", value, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateIn(List<Date> values) {
            addCriterion("ShipDate in", values, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotIn(List<Date> values) {
            addCriterion("ShipDate not in", values, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateBetween(Date value1, Date value2) {
            addCriterion("ShipDate between", value1, value2, "shipdate");
            return (Criteria) this;
        }

        public Criteria andShipdateNotBetween(Date value1, Date value2) {
            addCriterion("ShipDate not between", value1, value2, "shipdate");
            return (Criteria) this;
        }

        public Criteria andCostdateIsNull() {
            addCriterion("CostDate is null");
            return (Criteria) this;
        }

        public Criteria andCostdateIsNotNull() {
            addCriterion("CostDate is not null");
            return (Criteria) this;
        }

        public Criteria andCostdateEqualTo(Date value) {
            addCriterion("CostDate =", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateNotEqualTo(Date value) {
            addCriterion("CostDate <>", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateGreaterThan(Date value) {
            addCriterion("CostDate >", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateGreaterThanOrEqualTo(Date value) {
            addCriterion("CostDate >=", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateLessThan(Date value) {
            addCriterion("CostDate <", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateLessThanOrEqualTo(Date value) {
            addCriterion("CostDate <=", value, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateIn(List<Date> values) {
            addCriterion("CostDate in", values, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateNotIn(List<Date> values) {
            addCriterion("CostDate not in", values, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateBetween(Date value1, Date value2) {
            addCriterion("CostDate between", value1, value2, "costdate");
            return (Criteria) this;
        }

        public Criteria andCostdateNotBetween(Date value1, Date value2) {
            addCriterion("CostDate not between", value1, value2, "costdate");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNull() {
            addCriterion("OptState is null");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNotNull() {
            addCriterion("OptState is not null");
            return (Criteria) this;
        }

        public Criteria andOptstateEqualTo(String value) {
            addCriterion("OptState =", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotEqualTo(String value) {
            addCriterion("OptState <>", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThan(String value) {
            addCriterion("OptState >", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThanOrEqualTo(String value) {
            addCriterion("OptState >=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThan(String value) {
            addCriterion("OptState <", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThanOrEqualTo(String value) {
            addCriterion("OptState <=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLike(String value) {
            addCriterion("OptState like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotLike(String value) {
            addCriterion("OptState not like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateIn(List<String> values) {
            addCriterion("OptState in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotIn(List<String> values) {
            addCriterion("OptState not in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateBetween(String value1, String value2) {
            addCriterion("OptState between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotBetween(String value1, String value2) {
            addCriterion("OptState not between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andAdjamountIsNull() {
            addCriterion("AdjAmount is null");
            return (Criteria) this;
        }

        public Criteria andAdjamountIsNotNull() {
            addCriterion("AdjAmount is not null");
            return (Criteria) this;
        }

        public Criteria andAdjamountEqualTo(BigDecimal value) {
            addCriterion("AdjAmount =", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountNotEqualTo(BigDecimal value) {
            addCriterion("AdjAmount <>", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountGreaterThan(BigDecimal value) {
            addCriterion("AdjAmount >", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AdjAmount >=", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountLessThan(BigDecimal value) {
            addCriterion("AdjAmount <", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AdjAmount <=", value, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountIn(List<BigDecimal> values) {
            addCriterion("AdjAmount in", values, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountNotIn(List<BigDecimal> values) {
            addCriterion("AdjAmount not in", values, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AdjAmount between", value1, value2, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAdjamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AdjAmount not between", value1, value2, "adjamount");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMIsNull() {
            addCriterion("AmountRMB_M is null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMIsNotNull() {
            addCriterion("AmountRMB_M is not null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_M =", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMNotEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_M <>", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMGreaterThan(BigDecimal value) {
            addCriterion("AmountRMB_M >", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_M >=", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMLessThan(BigDecimal value) {
            addCriterion("AmountRMB_M <", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_M <=", value, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMIn(List<BigDecimal> values) {
            addCriterion("AmountRMB_M in", values, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMNotIn(List<BigDecimal> values) {
            addCriterion("AmountRMB_M not in", values, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB_M between", value1, value2, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbMNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB_M not between", value1, value2, "amountrmbM");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPIsNull() {
            addCriterion("AmountRMB_P is null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPIsNotNull() {
            addCriterion("AmountRMB_P is not null");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_P =", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPNotEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_P <>", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPGreaterThan(BigDecimal value) {
            addCriterion("AmountRMB_P >", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_P >=", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPLessThan(BigDecimal value) {
            addCriterion("AmountRMB_P <", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AmountRMB_P <=", value, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPIn(List<BigDecimal> values) {
            addCriterion("AmountRMB_P in", values, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPNotIn(List<BigDecimal> values) {
            addCriterion("AmountRMB_P not in", values, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB_P between", value1, value2, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andAmountrmbPNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AmountRMB_P not between", value1, value2, "amountrmbP");
            return (Criteria) this;
        }

        public Criteria andGzidIsNull() {
            addCriterion("GZID is null");
            return (Criteria) this;
        }

        public Criteria andGzidIsNotNull() {
            addCriterion("GZID is not null");
            return (Criteria) this;
        }

        public Criteria andGzidEqualTo(Integer value) {
            addCriterion("GZID =", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotEqualTo(Integer value) {
            addCriterion("GZID <>", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidGreaterThan(Integer value) {
            addCriterion("GZID >", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidGreaterThanOrEqualTo(Integer value) {
            addCriterion("GZID >=", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidLessThan(Integer value) {
            addCriterion("GZID <", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidLessThanOrEqualTo(Integer value) {
            addCriterion("GZID <=", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidIn(List<Integer> values) {
            addCriterion("GZID in", values, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotIn(List<Integer> values) {
            addCriterion("GZID not in", values, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidBetween(Integer value1, Integer value2) {
            addCriterion("GZID between", value1, value2, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotBetween(Integer value1, Integer value2) {
            addCriterion("GZID not between", value1, value2, "gzid");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateIsNull() {
            addCriterion("SupplierInvDate is null");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateIsNotNull() {
            addCriterion("SupplierInvDate is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateEqualTo(Date value) {
            addCriterion("SupplierInvDate =", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateNotEqualTo(Date value) {
            addCriterion("SupplierInvDate <>", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateGreaterThan(Date value) {
            addCriterion("SupplierInvDate >", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("SupplierInvDate >=", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateLessThan(Date value) {
            addCriterion("SupplierInvDate <", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateLessThanOrEqualTo(Date value) {
            addCriterion("SupplierInvDate <=", value, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateIn(List<Date> values) {
            addCriterion("SupplierInvDate in", values, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateNotIn(List<Date> values) {
            addCriterion("SupplierInvDate not in", values, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateBetween(Date value1, Date value2) {
            addCriterion("SupplierInvDate between", value1, value2, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andSupplierinvdateNotBetween(Date value1, Date value2) {
            addCriterion("SupplierInvDate not between", value1, value2, "supplierinvdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateIsNull() {
            addCriterion("POCostDate is null");
            return (Criteria) this;
        }

        public Criteria andPocostdateIsNotNull() {
            addCriterion("POCostDate is not null");
            return (Criteria) this;
        }

        public Criteria andPocostdateEqualTo(Date value) {
            addCriterion("POCostDate =", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateNotEqualTo(Date value) {
            addCriterion("POCostDate <>", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateGreaterThan(Date value) {
            addCriterion("POCostDate >", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateGreaterThanOrEqualTo(Date value) {
            addCriterion("POCostDate >=", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateLessThan(Date value) {
            addCriterion("POCostDate <", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateLessThanOrEqualTo(Date value) {
            addCriterion("POCostDate <=", value, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateIn(List<Date> values) {
            addCriterion("POCostDate in", values, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateNotIn(List<Date> values) {
            addCriterion("POCostDate not in", values, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateBetween(Date value1, Date value2) {
            addCriterion("POCostDate between", value1, value2, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andPocostdateNotBetween(Date value1, Date value2) {
            addCriterion("POCostDate not between", value1, value2, "pocostdate");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIsNull() {
            addCriterion("OwnerCode is null");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIsNotNull() {
            addCriterion("OwnerCode is not null");
            return (Criteria) this;
        }

        public Criteria andOwnercodeEqualTo(String value) {
            addCriterion("OwnerCode =", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotEqualTo(String value) {
            addCriterion("OwnerCode <>", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeGreaterThan(String value) {
            addCriterion("OwnerCode >", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeGreaterThanOrEqualTo(String value) {
            addCriterion("OwnerCode >=", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLessThan(String value) {
            addCriterion("OwnerCode <", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLessThanOrEqualTo(String value) {
            addCriterion("OwnerCode <=", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeLike(String value) {
            addCriterion("OwnerCode like", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotLike(String value) {
            addCriterion("OwnerCode not like", value, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeIn(List<String> values) {
            addCriterion("OwnerCode in", values, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotIn(List<String> values) {
            addCriterion("OwnerCode not in", values, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeBetween(String value1, String value2) {
            addCriterion("OwnerCode between", value1, value2, "ownercode");
            return (Criteria) this;
        }

        public Criteria andOwnercodeNotBetween(String value1, String value2) {
            addCriterion("OwnerCode not between", value1, value2, "ownercode");
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
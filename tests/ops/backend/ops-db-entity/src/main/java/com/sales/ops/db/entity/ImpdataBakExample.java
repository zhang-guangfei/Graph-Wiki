package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpdataBakExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ImpdataBakExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
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

        public Criteria andCasenoIsNull() {
            addCriterion("CaseNo is null");
            return (Criteria) this;
        }

        public Criteria andCasenoIsNotNull() {
            addCriterion("CaseNo is not null");
            return (Criteria) this;
        }

        public Criteria andCasenoEqualTo(String value) {
            addCriterion("CaseNo =", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotEqualTo(String value) {
            addCriterion("CaseNo <>", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThan(String value) {
            addCriterion("CaseNo >", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThanOrEqualTo(String value) {
            addCriterion("CaseNo >=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThan(String value) {
            addCriterion("CaseNo <", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThanOrEqualTo(String value) {
            addCriterion("CaseNo <=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLike(String value) {
            addCriterion("CaseNo like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotLike(String value) {
            addCriterion("CaseNo not like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoIn(List<String> values) {
            addCriterion("CaseNo in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotIn(List<String> values) {
            addCriterion("CaseNo not in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoBetween(String value1, String value2) {
            addCriterion("CaseNo between", value1, value2, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotBetween(String value1, String value2) {
            addCriterion("CaseNo not between", value1, value2, "caseno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("OrderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("OrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("OrderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("OrderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("OrderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("OrderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("OrderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("OrderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("OrderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("OrderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("OrderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("OrderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("OrderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("OrderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andRordernoAllIsNull() {
            addCriterion("RorderNo_All is null");
            return (Criteria) this;
        }

        public Criteria andRordernoAllIsNotNull() {
            addCriterion("RorderNo_All is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoAllEqualTo(String value) {
            addCriterion("RorderNo_All =", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllNotEqualTo(String value) {
            addCriterion("RorderNo_All <>", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllGreaterThan(String value) {
            addCriterion("RorderNo_All >", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllGreaterThanOrEqualTo(String value) {
            addCriterion("RorderNo_All >=", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllLessThan(String value) {
            addCriterion("RorderNo_All <", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllLessThanOrEqualTo(String value) {
            addCriterion("RorderNo_All <=", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllLike(String value) {
            addCriterion("RorderNo_All like", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllNotLike(String value) {
            addCriterion("RorderNo_All not like", value, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllIn(List<String> values) {
            addCriterion("RorderNo_All in", values, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllNotIn(List<String> values) {
            addCriterion("RorderNo_All not in", values, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllBetween(String value1, String value2) {
            addCriterion("RorderNo_All between", value1, value2, "rordernoAll");
            return (Criteria) this;
        }

        public Criteria andRordernoAllNotBetween(String value1, String value2) {
            addCriterion("RorderNo_All not between", value1, value2, "rordernoAll");
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

        public Criteria andTranstypeIsNull() {
            addCriterion("TransType is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("TransType is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("TransType =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("TransType <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("TransType >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("TransType >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("TransType <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("TransType <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("TransType like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("TransType not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("TransType in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("TransType not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("TransType between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("TransType not between", value1, value2, "transtype");
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

        public Criteria andOptcodeIsNull() {
            addCriterion("OptCode is null");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNotNull() {
            addCriterion("OptCode is not null");
            return (Criteria) this;
        }

        public Criteria andOptcodeEqualTo(String value) {
            addCriterion("OptCode =", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotEqualTo(String value) {
            addCriterion("OptCode <>", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThan(String value) {
            addCriterion("OptCode >", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("OptCode >=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThan(String value) {
            addCriterion("OptCode <", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThanOrEqualTo(String value) {
            addCriterion("OptCode <=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLike(String value) {
            addCriterion("OptCode like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotLike(String value) {
            addCriterion("OptCode not like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeIn(List<String> values) {
            addCriterion("OptCode in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotIn(List<String> values) {
            addCriterion("OptCode not in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeBetween(String value1, String value2) {
            addCriterion("OptCode between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotBetween(String value1, String value2) {
            addCriterion("OptCode not between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNull() {
            addCriterion("StateFlag is null");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNotNull() {
            addCriterion("StateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andStateflagEqualTo(String value) {
            addCriterion("StateFlag =", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotEqualTo(String value) {
            addCriterion("StateFlag <>", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThan(String value) {
            addCriterion("StateFlag >", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThanOrEqualTo(String value) {
            addCriterion("StateFlag >=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThan(String value) {
            addCriterion("StateFlag <", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThanOrEqualTo(String value) {
            addCriterion("StateFlag <=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLike(String value) {
            addCriterion("StateFlag like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotLike(String value) {
            addCriterion("StateFlag not like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagIn(List<String> values) {
            addCriterion("StateFlag in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotIn(List<String> values) {
            addCriterion("StateFlag not in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagBetween(String value1, String value2) {
            addCriterion("StateFlag between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotBetween(String value1, String value2) {
            addCriterion("StateFlag not between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andExpdescIsNull() {
            addCriterion("ExpDesc is null");
            return (Criteria) this;
        }

        public Criteria andExpdescIsNotNull() {
            addCriterion("ExpDesc is not null");
            return (Criteria) this;
        }

        public Criteria andExpdescEqualTo(String value) {
            addCriterion("ExpDesc =", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescNotEqualTo(String value) {
            addCriterion("ExpDesc <>", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescGreaterThan(String value) {
            addCriterion("ExpDesc >", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescGreaterThanOrEqualTo(String value) {
            addCriterion("ExpDesc >=", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescLessThan(String value) {
            addCriterion("ExpDesc <", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescLessThanOrEqualTo(String value) {
            addCriterion("ExpDesc <=", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescLike(String value) {
            addCriterion("ExpDesc like", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescNotLike(String value) {
            addCriterion("ExpDesc not like", value, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescIn(List<String> values) {
            addCriterion("ExpDesc in", values, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescNotIn(List<String> values) {
            addCriterion("ExpDesc not in", values, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescBetween(String value1, String value2) {
            addCriterion("ExpDesc between", value1, value2, "expdesc");
            return (Criteria) this;
        }

        public Criteria andExpdescNotBetween(String value1, String value2) {
            addCriterion("ExpDesc not between", value1, value2, "expdesc");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNull() {
            addCriterion("BarCode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("BarCode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("BarCode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("BarCode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("BarCode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BarCode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("BarCode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("BarCode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("BarCode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("BarCode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("BarCode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("BarCode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("BarCode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("BarCode not between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andOrdflagIsNull() {
            addCriterion("OrdFlag is null");
            return (Criteria) this;
        }

        public Criteria andOrdflagIsNotNull() {
            addCriterion("OrdFlag is not null");
            return (Criteria) this;
        }

        public Criteria andOrdflagEqualTo(String value) {
            addCriterion("OrdFlag =", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotEqualTo(String value) {
            addCriterion("OrdFlag <>", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagGreaterThan(String value) {
            addCriterion("OrdFlag >", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagGreaterThanOrEqualTo(String value) {
            addCriterion("OrdFlag >=", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLessThan(String value) {
            addCriterion("OrdFlag <", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLessThanOrEqualTo(String value) {
            addCriterion("OrdFlag <=", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagLike(String value) {
            addCriterion("OrdFlag like", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotLike(String value) {
            addCriterion("OrdFlag not like", value, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagIn(List<String> values) {
            addCriterion("OrdFlag in", values, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotIn(List<String> values) {
            addCriterion("OrdFlag not in", values, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagBetween(String value1, String value2) {
            addCriterion("OrdFlag between", value1, value2, "ordflag");
            return (Criteria) this;
        }

        public Criteria andOrdflagNotBetween(String value1, String value2) {
            addCriterion("OrdFlag not between", value1, value2, "ordflag");
            return (Criteria) this;
        }

        public Criteria andQtyimpIsNull() {
            addCriterion("QtyImp is null");
            return (Criteria) this;
        }

        public Criteria andQtyimpIsNotNull() {
            addCriterion("QtyImp is not null");
            return (Criteria) this;
        }

        public Criteria andQtyimpEqualTo(Integer value) {
            addCriterion("QtyImp =", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpNotEqualTo(Integer value) {
            addCriterion("QtyImp <>", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpGreaterThan(Integer value) {
            addCriterion("QtyImp >", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyImp >=", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpLessThan(Integer value) {
            addCriterion("QtyImp <", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpLessThanOrEqualTo(Integer value) {
            addCriterion("QtyImp <=", value, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpIn(List<Integer> values) {
            addCriterion("QtyImp in", values, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpNotIn(List<Integer> values) {
            addCriterion("QtyImp not in", values, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpBetween(Integer value1, Integer value2) {
            addCriterion("QtyImp between", value1, value2, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andQtyimpNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyImp not between", value1, value2, "qtyimp");
            return (Criteria) this;
        }

        public Criteria andOptflagIsNull() {
            addCriterion("OptFlag is null");
            return (Criteria) this;
        }

        public Criteria andOptflagIsNotNull() {
            addCriterion("OptFlag is not null");
            return (Criteria) this;
        }

        public Criteria andOptflagEqualTo(String value) {
            addCriterion("OptFlag =", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotEqualTo(String value) {
            addCriterion("OptFlag <>", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagGreaterThan(String value) {
            addCriterion("OptFlag >", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagGreaterThanOrEqualTo(String value) {
            addCriterion("OptFlag >=", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLessThan(String value) {
            addCriterion("OptFlag <", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLessThanOrEqualTo(String value) {
            addCriterion("OptFlag <=", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLike(String value) {
            addCriterion("OptFlag like", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotLike(String value) {
            addCriterion("OptFlag not like", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagIn(List<String> values) {
            addCriterion("OptFlag in", values, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotIn(List<String> values) {
            addCriterion("OptFlag not in", values, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagBetween(String value1, String value2) {
            addCriterion("OptFlag between", value1, value2, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotBetween(String value1, String value2) {
            addCriterion("OptFlag not between", value1, value2, "optflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagIsNull() {
            addCriterion("LabelPrtFlag is null");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagIsNotNull() {
            addCriterion("LabelPrtFlag is not null");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagEqualTo(String value) {
            addCriterion("LabelPrtFlag =", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagNotEqualTo(String value) {
            addCriterion("LabelPrtFlag <>", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagGreaterThan(String value) {
            addCriterion("LabelPrtFlag >", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagGreaterThanOrEqualTo(String value) {
            addCriterion("LabelPrtFlag >=", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagLessThan(String value) {
            addCriterion("LabelPrtFlag <", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagLessThanOrEqualTo(String value) {
            addCriterion("LabelPrtFlag <=", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagLike(String value) {
            addCriterion("LabelPrtFlag like", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagNotLike(String value) {
            addCriterion("LabelPrtFlag not like", value, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagIn(List<String> values) {
            addCriterion("LabelPrtFlag in", values, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagNotIn(List<String> values) {
            addCriterion("LabelPrtFlag not in", values, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagBetween(String value1, String value2) {
            addCriterion("LabelPrtFlag between", value1, value2, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andLabelprtflagNotBetween(String value1, String value2) {
            addCriterion("LabelPrtFlag not between", value1, value2, "labelprtflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagIsNull() {
            addCriterion("AsseFlag is null");
            return (Criteria) this;
        }

        public Criteria andAsseflagIsNotNull() {
            addCriterion("AsseFlag is not null");
            return (Criteria) this;
        }

        public Criteria andAsseflagEqualTo(String value) {
            addCriterion("AsseFlag =", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagNotEqualTo(String value) {
            addCriterion("AsseFlag <>", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagGreaterThan(String value) {
            addCriterion("AsseFlag >", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagGreaterThanOrEqualTo(String value) {
            addCriterion("AsseFlag >=", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagLessThan(String value) {
            addCriterion("AsseFlag <", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagLessThanOrEqualTo(String value) {
            addCriterion("AsseFlag <=", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagLike(String value) {
            addCriterion("AsseFlag like", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagNotLike(String value) {
            addCriterion("AsseFlag not like", value, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagIn(List<String> values) {
            addCriterion("AsseFlag in", values, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagNotIn(List<String> values) {
            addCriterion("AsseFlag not in", values, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagBetween(String value1, String value2) {
            addCriterion("AsseFlag between", value1, value2, "asseflag");
            return (Criteria) this;
        }

        public Criteria andAsseflagNotBetween(String value1, String value2) {
            addCriterion("AsseFlag not between", value1, value2, "asseflag");
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

        public Criteria andPrenoIsNull() {
            addCriterion("PreNo is null");
            return (Criteria) this;
        }

        public Criteria andPrenoIsNotNull() {
            addCriterion("PreNo is not null");
            return (Criteria) this;
        }

        public Criteria andPrenoEqualTo(String value) {
            addCriterion("PreNo =", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoNotEqualTo(String value) {
            addCriterion("PreNo <>", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoGreaterThan(String value) {
            addCriterion("PreNo >", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoGreaterThanOrEqualTo(String value) {
            addCriterion("PreNo >=", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoLessThan(String value) {
            addCriterion("PreNo <", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoLessThanOrEqualTo(String value) {
            addCriterion("PreNo <=", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoLike(String value) {
            addCriterion("PreNo like", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoNotLike(String value) {
            addCriterion("PreNo not like", value, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoIn(List<String> values) {
            addCriterion("PreNo in", values, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoNotIn(List<String> values) {
            addCriterion("PreNo not in", values, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoBetween(String value1, String value2) {
            addCriterion("PreNo between", value1, value2, "preno");
            return (Criteria) this;
        }

        public Criteria andPrenoNotBetween(String value1, String value2) {
            addCriterion("PreNo not between", value1, value2, "preno");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("UserName is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("UserName is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("UserName =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("UserName <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("UserName >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("UserName >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("UserName <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("UserName <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("UserName like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("UserName not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("UserName in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("UserName not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("UserName between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("UserName not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("OptTime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("OptTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("OptTime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("OptTime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("OptTime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OptTime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("OptTime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("OptTime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("OptTime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("OptTime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("OptTime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("OptTime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("SmcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("SmcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("SmcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("SmcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("SmcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("SmcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("SmcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("SmcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("SmcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("SmcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("SmcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("SmcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("SmcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("SmcCode not between", value1, value2, "smccode");
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

        public Criteria andOrdtypeIsNull() {
            addCriterion("OrdType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("OrdType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("OrdType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("OrdType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("OrdType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("OrdType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("OrdType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("OrdType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("OrdType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("OrdType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("OrdType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("OrdType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("OrdType not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andUnitweightIsNull() {
            addCriterion("unitWeight is null");
            return (Criteria) this;
        }

        public Criteria andUnitweightIsNotNull() {
            addCriterion("unitWeight is not null");
            return (Criteria) this;
        }

        public Criteria andUnitweightEqualTo(BigDecimal value) {
            addCriterion("unitWeight =", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightNotEqualTo(BigDecimal value) {
            addCriterion("unitWeight <>", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightGreaterThan(BigDecimal value) {
            addCriterion("unitWeight >", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unitWeight >=", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightLessThan(BigDecimal value) {
            addCriterion("unitWeight <", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unitWeight <=", value, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightIn(List<BigDecimal> values) {
            addCriterion("unitWeight in", values, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightNotIn(List<BigDecimal> values) {
            addCriterion("unitWeight not in", values, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unitWeight between", value1, value2, "unitweight");
            return (Criteria) this;
        }

        public Criteria andUnitweightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unitWeight not between", value1, value2, "unitweight");
            return (Criteria) this;
        }

        public Criteria andSerialnoIsNull() {
            addCriterion("SerialNo is null");
            return (Criteria) this;
        }

        public Criteria andSerialnoIsNotNull() {
            addCriterion("SerialNo is not null");
            return (Criteria) this;
        }

        public Criteria andSerialnoEqualTo(String value) {
            addCriterion("SerialNo =", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoNotEqualTo(String value) {
            addCriterion("SerialNo <>", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoGreaterThan(String value) {
            addCriterion("SerialNo >", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoGreaterThanOrEqualTo(String value) {
            addCriterion("SerialNo >=", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoLessThan(String value) {
            addCriterion("SerialNo <", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoLessThanOrEqualTo(String value) {
            addCriterion("SerialNo <=", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoLike(String value) {
            addCriterion("SerialNo like", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoNotLike(String value) {
            addCriterion("SerialNo not like", value, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoIn(List<String> values) {
            addCriterion("SerialNo in", values, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoNotIn(List<String> values) {
            addCriterion("SerialNo not in", values, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoBetween(String value1, String value2) {
            addCriterion("SerialNo between", value1, value2, "serialno");
            return (Criteria) this;
        }

        public Criteria andSerialnoNotBetween(String value1, String value2) {
            addCriterion("SerialNo not between", value1, value2, "serialno");
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
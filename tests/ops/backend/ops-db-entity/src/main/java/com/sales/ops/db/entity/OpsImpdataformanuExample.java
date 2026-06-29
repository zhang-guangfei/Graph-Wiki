package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsImpdataformanuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsImpdataformanuExample() {
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

        public Criteria andUsernameIsNull() {
            addCriterion("Username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("Username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("Username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("Username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("Username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("Username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("Username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("Username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("Username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("Username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("Username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("Username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("Username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("Username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("Opttime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("Opttime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("Opttime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("Opttime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("Opttime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Opttime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("Opttime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("Opttime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("Opttime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("Opttime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("Opttime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("Opttime not between", value1, value2, "opttime");
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

        public Criteria andPalletnoIsNull() {
            addCriterion("PalletNo is null");
            return (Criteria) this;
        }

        public Criteria andPalletnoIsNotNull() {
            addCriterion("PalletNo is not null");
            return (Criteria) this;
        }

        public Criteria andPalletnoEqualTo(String value) {
            addCriterion("PalletNo =", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoNotEqualTo(String value) {
            addCriterion("PalletNo <>", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoGreaterThan(String value) {
            addCriterion("PalletNo >", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoGreaterThanOrEqualTo(String value) {
            addCriterion("PalletNo >=", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoLessThan(String value) {
            addCriterion("PalletNo <", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoLessThanOrEqualTo(String value) {
            addCriterion("PalletNo <=", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoLike(String value) {
            addCriterion("PalletNo like", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoNotLike(String value) {
            addCriterion("PalletNo not like", value, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoIn(List<String> values) {
            addCriterion("PalletNo in", values, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoNotIn(List<String> values) {
            addCriterion("PalletNo not in", values, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoBetween(String value1, String value2) {
            addCriterion("PalletNo between", value1, value2, "palletno");
            return (Criteria) this;
        }

        public Criteria andPalletnoNotBetween(String value1, String value2) {
            addCriterion("PalletNo not between", value1, value2, "palletno");
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

        public Criteria andExtOrdernoIsNull() {
            addCriterion("ext_orderno is null");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoIsNotNull() {
            addCriterion("ext_orderno is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoEqualTo(String value) {
            addCriterion("ext_orderno =", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotEqualTo(String value) {
            addCriterion("ext_orderno <>", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoGreaterThan(String value) {
            addCriterion("ext_orderno >", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("ext_orderno >=", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLessThan(String value) {
            addCriterion("ext_orderno <", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLessThanOrEqualTo(String value) {
            addCriterion("ext_orderno <=", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoLike(String value) {
            addCriterion("ext_orderno like", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotLike(String value) {
            addCriterion("ext_orderno not like", value, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoIn(List<String> values) {
            addCriterion("ext_orderno in", values, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotIn(List<String> values) {
            addCriterion("ext_orderno not in", values, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoBetween(String value1, String value2) {
            addCriterion("ext_orderno between", value1, value2, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrdernoNotBetween(String value1, String value2) {
            addCriterion("ext_orderno not between", value1, value2, "extOrderno");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNull() {
            addCriterion("ext_order_item is null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIsNotNull() {
            addCriterion("ext_order_item is not null");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemEqualTo(String value) {
            addCriterion("ext_order_item =", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotEqualTo(String value) {
            addCriterion("ext_order_item <>", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThan(String value) {
            addCriterion("ext_order_item >", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("ext_order_item >=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThan(String value) {
            addCriterion("ext_order_item <", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLessThanOrEqualTo(String value) {
            addCriterion("ext_order_item <=", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemLike(String value) {
            addCriterion("ext_order_item like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotLike(String value) {
            addCriterion("ext_order_item not like", value, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemIn(List<String> values) {
            addCriterion("ext_order_item in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotIn(List<String> values) {
            addCriterion("ext_order_item not in", values, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemBetween(String value1, String value2) {
            addCriterion("ext_order_item between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andExtOrderItemNotBetween(String value1, String value2) {
            addCriterion("ext_order_item not between", value1, value2, "extOrderItem");
            return (Criteria) this;
        }

        public Criteria andTaxpriceIsNull() {
            addCriterion("taxPrice is null");
            return (Criteria) this;
        }

        public Criteria andTaxpriceIsNotNull() {
            addCriterion("taxPrice is not null");
            return (Criteria) this;
        }

        public Criteria andTaxpriceEqualTo(BigDecimal value) {
            addCriterion("taxPrice =", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceNotEqualTo(BigDecimal value) {
            addCriterion("taxPrice <>", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceGreaterThan(BigDecimal value) {
            addCriterion("taxPrice >", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("taxPrice >=", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceLessThan(BigDecimal value) {
            addCriterion("taxPrice <", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("taxPrice <=", value, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceIn(List<BigDecimal> values) {
            addCriterion("taxPrice in", values, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceNotIn(List<BigDecimal> values) {
            addCriterion("taxPrice not in", values, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxPrice between", value1, value2, "taxprice");
            return (Criteria) this;
        }

        public Criteria andTaxpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxPrice not between", value1, value2, "taxprice");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("Amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("Amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("Amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("Amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("Amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("Amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("Amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("Amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTaxamountIsNull() {
            addCriterion("TaxAmount is null");
            return (Criteria) this;
        }

        public Criteria andTaxamountIsNotNull() {
            addCriterion("TaxAmount is not null");
            return (Criteria) this;
        }

        public Criteria andTaxamountEqualTo(BigDecimal value) {
            addCriterion("TaxAmount =", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotEqualTo(BigDecimal value) {
            addCriterion("TaxAmount <>", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountGreaterThan(BigDecimal value) {
            addCriterion("TaxAmount >", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxAmount >=", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountLessThan(BigDecimal value) {
            addCriterion("TaxAmount <", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxAmount <=", value, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountIn(List<BigDecimal> values) {
            addCriterion("TaxAmount in", values, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotIn(List<BigDecimal> values) {
            addCriterion("TaxAmount not in", values, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxAmount between", value1, value2, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxAmount not between", value1, value2, "taxamount");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNull() {
            addCriterion("TaxRate is null");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNotNull() {
            addCriterion("TaxRate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxrateEqualTo(BigDecimal value) {
            addCriterion("TaxRate =", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("TaxRate <>", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThan(BigDecimal value) {
            addCriterion("TaxRate >", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxRate >=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThan(BigDecimal value) {
            addCriterion("TaxRate <", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TaxRate <=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateIn(List<BigDecimal> values) {
            addCriterion("TaxRate in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("TaxRate not in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxRate between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TaxRate not between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andFromidIsNull() {
            addCriterion("FromId is null");
            return (Criteria) this;
        }

        public Criteria andFromidIsNotNull() {
            addCriterion("FromId is not null");
            return (Criteria) this;
        }

        public Criteria andFromidEqualTo(Long value) {
            addCriterion("FromId =", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidNotEqualTo(Long value) {
            addCriterion("FromId <>", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidGreaterThan(Long value) {
            addCriterion("FromId >", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidGreaterThanOrEqualTo(Long value) {
            addCriterion("FromId >=", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidLessThan(Long value) {
            addCriterion("FromId <", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidLessThanOrEqualTo(Long value) {
            addCriterion("FromId <=", value, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidIn(List<Long> values) {
            addCriterion("FromId in", values, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidNotIn(List<Long> values) {
            addCriterion("FromId not in", values, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidBetween(Long value1, Long value2) {
            addCriterion("FromId between", value1, value2, "fromid");
            return (Criteria) this;
        }

        public Criteria andFromidNotBetween(Long value1, Long value2) {
            addCriterion("FromId not between", value1, value2, "fromid");
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
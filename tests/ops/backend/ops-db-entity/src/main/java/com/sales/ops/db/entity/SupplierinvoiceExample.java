package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierinvoiceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierinvoiceExample() {
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

        public Criteria andGzidIsNull() {
            addCriterion("GZId is null");
            return (Criteria) this;
        }

        public Criteria andGzidIsNotNull() {
            addCriterion("GZId is not null");
            return (Criteria) this;
        }

        public Criteria andGzidEqualTo(Integer value) {
            addCriterion("GZId =", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotEqualTo(Integer value) {
            addCriterion("GZId <>", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidGreaterThan(Integer value) {
            addCriterion("GZId >", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidGreaterThanOrEqualTo(Integer value) {
            addCriterion("GZId >=", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidLessThan(Integer value) {
            addCriterion("GZId <", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidLessThanOrEqualTo(Integer value) {
            addCriterion("GZId <=", value, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidIn(List<Integer> values) {
            addCriterion("GZId in", values, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotIn(List<Integer> values) {
            addCriterion("GZId not in", values, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidBetween(Integer value1, Integer value2) {
            addCriterion("GZId between", value1, value2, "gzid");
            return (Criteria) this;
        }

        public Criteria andGzidNotBetween(Integer value1, Integer value2) {
            addCriterion("GZId not between", value1, value2, "gzid");
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

        public Criteria andBarcodeIsNull() {
            addCriterion("Barcode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("Barcode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("Barcode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("Barcode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("Barcode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("Barcode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("Barcode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("Barcode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("Barcode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("Barcode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("Barcode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("Barcode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("Barcode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("Barcode not between", value1, value2, "barcode");
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

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andOrigincodeIsNull() {
            addCriterion("OriginCode is null");
            return (Criteria) this;
        }

        public Criteria andOrigincodeIsNotNull() {
            addCriterion("OriginCode is not null");
            return (Criteria) this;
        }

        public Criteria andOrigincodeEqualTo(String value) {
            addCriterion("OriginCode =", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeNotEqualTo(String value) {
            addCriterion("OriginCode <>", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeGreaterThan(String value) {
            addCriterion("OriginCode >", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeGreaterThanOrEqualTo(String value) {
            addCriterion("OriginCode >=", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeLessThan(String value) {
            addCriterion("OriginCode <", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeLessThanOrEqualTo(String value) {
            addCriterion("OriginCode <=", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeLike(String value) {
            addCriterion("OriginCode like", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeNotLike(String value) {
            addCriterion("OriginCode not like", value, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeIn(List<String> values) {
            addCriterion("OriginCode in", values, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeNotIn(List<String> values) {
            addCriterion("OriginCode not in", values, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeBetween(String value1, String value2) {
            addCriterion("OriginCode between", value1, value2, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincodeNotBetween(String value1, String value2) {
            addCriterion("OriginCode not between", value1, value2, "origincode");
            return (Criteria) this;
        }

        public Criteria andOrigincountryIsNull() {
            addCriterion("OriginCountry is null");
            return (Criteria) this;
        }

        public Criteria andOrigincountryIsNotNull() {
            addCriterion("OriginCountry is not null");
            return (Criteria) this;
        }

        public Criteria andOrigincountryEqualTo(String value) {
            addCriterion("OriginCountry =", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryNotEqualTo(String value) {
            addCriterion("OriginCountry <>", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryGreaterThan(String value) {
            addCriterion("OriginCountry >", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryGreaterThanOrEqualTo(String value) {
            addCriterion("OriginCountry >=", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryLessThan(String value) {
            addCriterion("OriginCountry <", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryLessThanOrEqualTo(String value) {
            addCriterion("OriginCountry <=", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryLike(String value) {
            addCriterion("OriginCountry like", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryNotLike(String value) {
            addCriterion("OriginCountry not like", value, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryIn(List<String> values) {
            addCriterion("OriginCountry in", values, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryNotIn(List<String> values) {
            addCriterion("OriginCountry not in", values, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryBetween(String value1, String value2) {
            addCriterion("OriginCountry between", value1, value2, "origincountry");
            return (Criteria) this;
        }

        public Criteria andOrigincountryNotBetween(String value1, String value2) {
            addCriterion("OriginCountry not between", value1, value2, "origincountry");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("Unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("Unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("Unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("Unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("Unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("Unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("Unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("Unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("Unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("Unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("Unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("Unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("Unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("Unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andNorefIsNull() {
            addCriterion("NoRef is null");
            return (Criteria) this;
        }

        public Criteria andNorefIsNotNull() {
            addCriterion("NoRef is not null");
            return (Criteria) this;
        }

        public Criteria andNorefEqualTo(String value) {
            addCriterion("NoRef =", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefNotEqualTo(String value) {
            addCriterion("NoRef <>", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefGreaterThan(String value) {
            addCriterion("NoRef >", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefGreaterThanOrEqualTo(String value) {
            addCriterion("NoRef >=", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefLessThan(String value) {
            addCriterion("NoRef <", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefLessThanOrEqualTo(String value) {
            addCriterion("NoRef <=", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefLike(String value) {
            addCriterion("NoRef like", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefNotLike(String value) {
            addCriterion("NoRef not like", value, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefIn(List<String> values) {
            addCriterion("NoRef in", values, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefNotIn(List<String> values) {
            addCriterion("NoRef not in", values, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefBetween(String value1, String value2) {
            addCriterion("NoRef between", value1, value2, "noref");
            return (Criteria) this;
        }

        public Criteria andNorefNotBetween(String value1, String value2) {
            addCriterion("NoRef not between", value1, value2, "noref");
            return (Criteria) this;
        }

        public Criteria andProductcodeIsNull() {
            addCriterion("ProductCode is null");
            return (Criteria) this;
        }

        public Criteria andProductcodeIsNotNull() {
            addCriterion("ProductCode is not null");
            return (Criteria) this;
        }

        public Criteria andProductcodeEqualTo(String value) {
            addCriterion("ProductCode =", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeNotEqualTo(String value) {
            addCriterion("ProductCode <>", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeGreaterThan(String value) {
            addCriterion("ProductCode >", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ProductCode >=", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeLessThan(String value) {
            addCriterion("ProductCode <", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeLessThanOrEqualTo(String value) {
            addCriterion("ProductCode <=", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeLike(String value) {
            addCriterion("ProductCode like", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeNotLike(String value) {
            addCriterion("ProductCode not like", value, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeIn(List<String> values) {
            addCriterion("ProductCode in", values, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeNotIn(List<String> values) {
            addCriterion("ProductCode not in", values, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeBetween(String value1, String value2) {
            addCriterion("ProductCode between", value1, value2, "productcode");
            return (Criteria) this;
        }

        public Criteria andProductcodeNotBetween(String value1, String value2) {
            addCriterion("ProductCode not between", value1, value2, "productcode");
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

        public Criteria andImptimeIsNull() {
            addCriterion("ImpTime is null");
            return (Criteria) this;
        }

        public Criteria andImptimeIsNotNull() {
            addCriterion("ImpTime is not null");
            return (Criteria) this;
        }

        public Criteria andImptimeEqualTo(Date value) {
            addCriterion("ImpTime =", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeNotEqualTo(Date value) {
            addCriterion("ImpTime <>", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeGreaterThan(Date value) {
            addCriterion("ImpTime >", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ImpTime >=", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeLessThan(Date value) {
            addCriterion("ImpTime <", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeLessThanOrEqualTo(Date value) {
            addCriterion("ImpTime <=", value, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeIn(List<Date> values) {
            addCriterion("ImpTime in", values, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeNotIn(List<Date> values) {
            addCriterion("ImpTime not in", values, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeBetween(Date value1, Date value2) {
            addCriterion("ImpTime between", value1, value2, "imptime");
            return (Criteria) this;
        }

        public Criteria andImptimeNotBetween(Date value1, Date value2) {
            addCriterion("ImpTime not between", value1, value2, "imptime");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("Weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("Weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("Weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("Weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("Weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("Weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("Weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("Weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andModelennameIsNull() {
            addCriterion("ModelENName is null");
            return (Criteria) this;
        }

        public Criteria andModelennameIsNotNull() {
            addCriterion("ModelENName is not null");
            return (Criteria) this;
        }

        public Criteria andModelennameEqualTo(String value) {
            addCriterion("ModelENName =", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameNotEqualTo(String value) {
            addCriterion("ModelENName <>", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameGreaterThan(String value) {
            addCriterion("ModelENName >", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameGreaterThanOrEqualTo(String value) {
            addCriterion("ModelENName >=", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameLessThan(String value) {
            addCriterion("ModelENName <", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameLessThanOrEqualTo(String value) {
            addCriterion("ModelENName <=", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameLike(String value) {
            addCriterion("ModelENName like", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameNotLike(String value) {
            addCriterion("ModelENName not like", value, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameIn(List<String> values) {
            addCriterion("ModelENName in", values, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameNotIn(List<String> values) {
            addCriterion("ModelENName not in", values, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameBetween(String value1, String value2) {
            addCriterion("ModelENName between", value1, value2, "modelenname");
            return (Criteria) this;
        }

        public Criteria andModelennameNotBetween(String value1, String value2) {
            addCriterion("ModelENName not between", value1, value2, "modelenname");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNull() {
            addCriterion("OrderType is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("OrderType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(String value) {
            addCriterion("OrderType =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(String value) {
            addCriterion("OrderType <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(String value) {
            addCriterion("OrderType >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrderType >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(String value) {
            addCriterion("OrderType <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(String value) {
            addCriterion("OrderType <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLike(String value) {
            addCriterion("OrderType like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotLike(String value) {
            addCriterion("OrderType not like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<String> values) {
            addCriterion("OrderType in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<String> values) {
            addCriterion("OrderType not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(String value1, String value2) {
            addCriterion("OrderType between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(String value1, String value2) {
            addCriterion("OrderType not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andTariffrateIsNull() {
            addCriterion("TariffRate is null");
            return (Criteria) this;
        }

        public Criteria andTariffrateIsNotNull() {
            addCriterion("TariffRate is not null");
            return (Criteria) this;
        }

        public Criteria andTariffrateEqualTo(BigDecimal value) {
            addCriterion("TariffRate =", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateNotEqualTo(BigDecimal value) {
            addCriterion("TariffRate <>", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateGreaterThan(BigDecimal value) {
            addCriterion("TariffRate >", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TariffRate >=", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateLessThan(BigDecimal value) {
            addCriterion("TariffRate <", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TariffRate <=", value, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateIn(List<BigDecimal> values) {
            addCriterion("TariffRate in", values, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateNotIn(List<BigDecimal> values) {
            addCriterion("TariffRate not in", values, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TariffRate between", value1, value2, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andTariffrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TariffRate not between", value1, value2, "tariffrate");
            return (Criteria) this;
        }

        public Criteria andCnnameIsNull() {
            addCriterion("CNName is null");
            return (Criteria) this;
        }

        public Criteria andCnnameIsNotNull() {
            addCriterion("CNName is not null");
            return (Criteria) this;
        }

        public Criteria andCnnameEqualTo(String value) {
            addCriterion("CNName =", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameNotEqualTo(String value) {
            addCriterion("CNName <>", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameGreaterThan(String value) {
            addCriterion("CNName >", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameGreaterThanOrEqualTo(String value) {
            addCriterion("CNName >=", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameLessThan(String value) {
            addCriterion("CNName <", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameLessThanOrEqualTo(String value) {
            addCriterion("CNName <=", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameLike(String value) {
            addCriterion("CNName like", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameNotLike(String value) {
            addCriterion("CNName not like", value, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameIn(List<String> values) {
            addCriterion("CNName in", values, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameNotIn(List<String> values) {
            addCriterion("CNName not in", values, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameBetween(String value1, String value2) {
            addCriterion("CNName between", value1, value2, "cnname");
            return (Criteria) this;
        }

        public Criteria andCnnameNotBetween(String value1, String value2) {
            addCriterion("CNName not between", value1, value2, "cnname");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNull() {
            addCriterion("UpdTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNotNull() {
            addCriterion("UpdTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeEqualTo(Date value) {
            addCriterion("UpdTime =", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotEqualTo(Date value) {
            addCriterion("UpdTime <>", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThan(Date value) {
            addCriterion("UpdTime >", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdTime >=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThan(Date value) {
            addCriterion("UpdTime <", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdTime <=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIn(List<Date> values) {
            addCriterion("UpdTime in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotIn(List<Date> values) {
            addCriterion("UpdTime not in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeBetween(Date value1, Date value2) {
            addCriterion("UpdTime between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdTime not between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andJphscodeIsNull() {
            addCriterion("JPHSCode is null");
            return (Criteria) this;
        }

        public Criteria andJphscodeIsNotNull() {
            addCriterion("JPHSCode is not null");
            return (Criteria) this;
        }

        public Criteria andJphscodeEqualTo(String value) {
            addCriterion("JPHSCode =", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeNotEqualTo(String value) {
            addCriterion("JPHSCode <>", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeGreaterThan(String value) {
            addCriterion("JPHSCode >", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeGreaterThanOrEqualTo(String value) {
            addCriterion("JPHSCode >=", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeLessThan(String value) {
            addCriterion("JPHSCode <", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeLessThanOrEqualTo(String value) {
            addCriterion("JPHSCode <=", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeLike(String value) {
            addCriterion("JPHSCode like", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeNotLike(String value) {
            addCriterion("JPHSCode not like", value, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeIn(List<String> values) {
            addCriterion("JPHSCode in", values, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeNotIn(List<String> values) {
            addCriterion("JPHSCode not in", values, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeBetween(String value1, String value2) {
            addCriterion("JPHSCode between", value1, value2, "jphscode");
            return (Criteria) this;
        }

        public Criteria andJphscodeNotBetween(String value1, String value2) {
            addCriterion("JPHSCode not between", value1, value2, "jphscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeIsNull() {
            addCriterion("CNHSCode is null");
            return (Criteria) this;
        }

        public Criteria andCnhscodeIsNotNull() {
            addCriterion("CNHSCode is not null");
            return (Criteria) this;
        }

        public Criteria andCnhscodeEqualTo(String value) {
            addCriterion("CNHSCode =", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeNotEqualTo(String value) {
            addCriterion("CNHSCode <>", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeGreaterThan(String value) {
            addCriterion("CNHSCode >", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeGreaterThanOrEqualTo(String value) {
            addCriterion("CNHSCode >=", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeLessThan(String value) {
            addCriterion("CNHSCode <", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeLessThanOrEqualTo(String value) {
            addCriterion("CNHSCode <=", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeLike(String value) {
            addCriterion("CNHSCode like", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeNotLike(String value) {
            addCriterion("CNHSCode not like", value, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeIn(List<String> values) {
            addCriterion("CNHSCode in", values, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeNotIn(List<String> values) {
            addCriterion("CNHSCode not in", values, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeBetween(String value1, String value2) {
            addCriterion("CNHSCode between", value1, value2, "cnhscode");
            return (Criteria) this;
        }

        public Criteria andCnhscodeNotBetween(String value1, String value2) {
            addCriterion("CNHSCode not between", value1, value2, "cnhscode");
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
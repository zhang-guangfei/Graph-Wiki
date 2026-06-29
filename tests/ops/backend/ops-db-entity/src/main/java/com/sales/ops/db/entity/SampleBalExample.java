package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleBalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SampleBalExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andSubmodelnoIsNull() {
            addCriterion("SubModelNo is null");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoIsNotNull() {
            addCriterion("SubModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoEqualTo(String value) {
            addCriterion("SubModelNo =", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoNotEqualTo(String value) {
            addCriterion("SubModelNo <>", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoGreaterThan(String value) {
            addCriterion("SubModelNo >", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoGreaterThanOrEqualTo(String value) {
            addCriterion("SubModelNo >=", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoLessThan(String value) {
            addCriterion("SubModelNo <", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoLessThanOrEqualTo(String value) {
            addCriterion("SubModelNo <=", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoLike(String value) {
            addCriterion("SubModelNo like", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoNotLike(String value) {
            addCriterion("SubModelNo not like", value, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoIn(List<String> values) {
            addCriterion("SubModelNo in", values, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoNotIn(List<String> values) {
            addCriterion("SubModelNo not in", values, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoBetween(String value1, String value2) {
            addCriterion("SubModelNo between", value1, value2, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubmodelnoNotBetween(String value1, String value2) {
            addCriterion("SubModelNo not between", value1, value2, "submodelno");
            return (Criteria) this;
        }

        public Criteria andSubqtyIsNull() {
            addCriterion("SubQty is null");
            return (Criteria) this;
        }

        public Criteria andSubqtyIsNotNull() {
            addCriterion("SubQty is not null");
            return (Criteria) this;
        }

        public Criteria andSubqtyEqualTo(Integer value) {
            addCriterion("SubQty =", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyNotEqualTo(Integer value) {
            addCriterion("SubQty <>", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyGreaterThan(Integer value) {
            addCriterion("SubQty >", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("SubQty >=", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyLessThan(Integer value) {
            addCriterion("SubQty <", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyLessThanOrEqualTo(Integer value) {
            addCriterion("SubQty <=", value, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyIn(List<Integer> values) {
            addCriterion("SubQty in", values, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyNotIn(List<Integer> values) {
            addCriterion("SubQty not in", values, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyBetween(Integer value1, Integer value2) {
            addCriterion("SubQty between", value1, value2, "subqty");
            return (Criteria) this;
        }

        public Criteria andSubqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("SubQty not between", value1, value2, "subqty");
            return (Criteria) this;
        }

        public Criteria andDeptdescIsNull() {
            addCriterion("DeptDesc is null");
            return (Criteria) this;
        }

        public Criteria andDeptdescIsNotNull() {
            addCriterion("DeptDesc is not null");
            return (Criteria) this;
        }

        public Criteria andDeptdescEqualTo(String value) {
            addCriterion("DeptDesc =", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotEqualTo(String value) {
            addCriterion("DeptDesc <>", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescGreaterThan(String value) {
            addCriterion("DeptDesc >", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescGreaterThanOrEqualTo(String value) {
            addCriterion("DeptDesc >=", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLessThan(String value) {
            addCriterion("DeptDesc <", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLessThanOrEqualTo(String value) {
            addCriterion("DeptDesc <=", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescLike(String value) {
            addCriterion("DeptDesc like", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotLike(String value) {
            addCriterion("DeptDesc not like", value, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescIn(List<String> values) {
            addCriterion("DeptDesc in", values, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotIn(List<String> values) {
            addCriterion("DeptDesc not in", values, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescBetween(String value1, String value2) {
            addCriterion("DeptDesc between", value1, value2, "deptdesc");
            return (Criteria) this;
        }

        public Criteria andDeptdescNotBetween(String value1, String value2) {
            addCriterion("DeptDesc not between", value1, value2, "deptdesc");
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

        public Criteria andApptypeIsNull() {
            addCriterion("AppType is null");
            return (Criteria) this;
        }

        public Criteria andApptypeIsNotNull() {
            addCriterion("AppType is not null");
            return (Criteria) this;
        }

        public Criteria andApptypeEqualTo(String value) {
            addCriterion("AppType =", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeNotEqualTo(String value) {
            addCriterion("AppType <>", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeGreaterThan(String value) {
            addCriterion("AppType >", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeGreaterThanOrEqualTo(String value) {
            addCriterion("AppType >=", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeLessThan(String value) {
            addCriterion("AppType <", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeLessThanOrEqualTo(String value) {
            addCriterion("AppType <=", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeLike(String value) {
            addCriterion("AppType like", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeNotLike(String value) {
            addCriterion("AppType not like", value, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeIn(List<String> values) {
            addCriterion("AppType in", values, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeNotIn(List<String> values) {
            addCriterion("AppType not in", values, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeBetween(String value1, String value2) {
            addCriterion("AppType between", value1, value2, "apptype");
            return (Criteria) this;
        }

        public Criteria andApptypeNotBetween(String value1, String value2) {
            addCriterion("AppType not between", value1, value2, "apptype");
            return (Criteria) this;
        }

        public Criteria andBaltypeIsNull() {
            addCriterion("BalType is null");
            return (Criteria) this;
        }

        public Criteria andBaltypeIsNotNull() {
            addCriterion("BalType is not null");
            return (Criteria) this;
        }

        public Criteria andBaltypeEqualTo(String value) {
            addCriterion("BalType =", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeNotEqualTo(String value) {
            addCriterion("BalType <>", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeGreaterThan(String value) {
            addCriterion("BalType >", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeGreaterThanOrEqualTo(String value) {
            addCriterion("BalType >=", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeLessThan(String value) {
            addCriterion("BalType <", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeLessThanOrEqualTo(String value) {
            addCriterion("BalType <=", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeLike(String value) {
            addCriterion("BalType like", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeNotLike(String value) {
            addCriterion("BalType not like", value, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeIn(List<String> values) {
            addCriterion("BalType in", values, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeNotIn(List<String> values) {
            addCriterion("BalType not in", values, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeBetween(String value1, String value2) {
            addCriterion("BalType between", value1, value2, "baltype");
            return (Criteria) this;
        }

        public Criteria andBaltypeNotBetween(String value1, String value2) {
            addCriterion("BalType not between", value1, value2, "baltype");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNull() {
            addCriterion("ProdFlag is null");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNotNull() {
            addCriterion("ProdFlag is not null");
            return (Criteria) this;
        }

        public Criteria andProdflagEqualTo(String value) {
            addCriterion("ProdFlag =", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotEqualTo(String value) {
            addCriterion("ProdFlag <>", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThan(String value) {
            addCriterion("ProdFlag >", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThanOrEqualTo(String value) {
            addCriterion("ProdFlag >=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThan(String value) {
            addCriterion("ProdFlag <", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThanOrEqualTo(String value) {
            addCriterion("ProdFlag <=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLike(String value) {
            addCriterion("ProdFlag like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotLike(String value) {
            addCriterion("ProdFlag not like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagIn(List<String> values) {
            addCriterion("ProdFlag in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotIn(List<String> values) {
            addCriterion("ProdFlag not in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagBetween(String value1, String value2) {
            addCriterion("ProdFlag between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotBetween(String value1, String value2) {
            addCriterion("ProdFlag not between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdcodeIsNull() {
            addCriterion("ProdCode is null");
            return (Criteria) this;
        }

        public Criteria andProdcodeIsNotNull() {
            addCriterion("ProdCode is not null");
            return (Criteria) this;
        }

        public Criteria andProdcodeEqualTo(String value) {
            addCriterion("ProdCode =", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotEqualTo(String value) {
            addCriterion("ProdCode <>", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeGreaterThan(String value) {
            addCriterion("ProdCode >", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ProdCode >=", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLessThan(String value) {
            addCriterion("ProdCode <", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLessThanOrEqualTo(String value) {
            addCriterion("ProdCode <=", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLike(String value) {
            addCriterion("ProdCode like", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotLike(String value) {
            addCriterion("ProdCode not like", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeIn(List<String> values) {
            addCriterion("ProdCode in", values, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotIn(List<String> values) {
            addCriterion("ProdCode not in", values, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeBetween(String value1, String value2) {
            addCriterion("ProdCode between", value1, value2, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotBetween(String value1, String value2) {
            addCriterion("ProdCode not between", value1, value2, "prodcode");
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

        public Criteria andEcodeIsNull() {
            addCriterion("ECode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("ECode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("ECode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("ECode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("ECode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ECode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("ECode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("ECode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("ECode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("ECode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("ECode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("ECode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("ECode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("ECode not between", value1, value2, "ecode");
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

        public Criteria andExpdateIsNull() {
            addCriterion("Expdate is null");
            return (Criteria) this;
        }

        public Criteria andExpdateIsNotNull() {
            addCriterion("Expdate is not null");
            return (Criteria) this;
        }

        public Criteria andExpdateEqualTo(Date value) {
            addCriterion("Expdate =", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotEqualTo(Date value) {
            addCriterion("Expdate <>", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThan(Date value) {
            addCriterion("Expdate >", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("Expdate >=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThan(Date value) {
            addCriterion("Expdate <", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThanOrEqualTo(Date value) {
            addCriterion("Expdate <=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateIn(List<Date> values) {
            addCriterion("Expdate in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotIn(List<Date> values) {
            addCriterion("Expdate not in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateBetween(Date value1, Date value2) {
            addCriterion("Expdate between", value1, value2, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotBetween(Date value1, Date value2) {
            addCriterion("Expdate not between", value1, value2, "expdate");
            return (Criteria) this;
        }

        public Criteria andModelinchnIsNull() {
            addCriterion("ModelInChn is null");
            return (Criteria) this;
        }

        public Criteria andModelinchnIsNotNull() {
            addCriterion("ModelInChn is not null");
            return (Criteria) this;
        }

        public Criteria andModelinchnEqualTo(String value) {
            addCriterion("ModelInChn =", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnNotEqualTo(String value) {
            addCriterion("ModelInChn <>", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnGreaterThan(String value) {
            addCriterion("ModelInChn >", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnGreaterThanOrEqualTo(String value) {
            addCriterion("ModelInChn >=", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnLessThan(String value) {
            addCriterion("ModelInChn <", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnLessThanOrEqualTo(String value) {
            addCriterion("ModelInChn <=", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnLike(String value) {
            addCriterion("ModelInChn like", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnNotLike(String value) {
            addCriterion("ModelInChn not like", value, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnIn(List<String> values) {
            addCriterion("ModelInChn in", values, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnNotIn(List<String> values) {
            addCriterion("ModelInChn not in", values, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnBetween(String value1, String value2) {
            addCriterion("ModelInChn between", value1, value2, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andModelinchnNotBetween(String value1, String value2) {
            addCriterion("ModelInChn not between", value1, value2, "modelinchn");
            return (Criteria) this;
        }

        public Criteria andIndateIsNull() {
            addCriterion("InDate is null");
            return (Criteria) this;
        }

        public Criteria andIndateIsNotNull() {
            addCriterion("InDate is not null");
            return (Criteria) this;
        }

        public Criteria andIndateEqualTo(Date value) {
            addCriterion("InDate =", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotEqualTo(Date value) {
            addCriterion("InDate <>", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThan(Date value) {
            addCriterion("InDate >", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate >=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThan(Date value) {
            addCriterion("InDate <", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThanOrEqualTo(Date value) {
            addCriterion("InDate <=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateIn(List<Date> values) {
            addCriterion("InDate in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotIn(List<Date> values) {
            addCriterion("InDate not in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateBetween(Date value1, Date value2) {
            addCriterion("InDate between", value1, value2, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotBetween(Date value1, Date value2) {
            addCriterion("InDate not between", value1, value2, "indate");
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

        public Criteria andApplycodeIsNull() {
            addCriterion("ApplyCode is null");
            return (Criteria) this;
        }

        public Criteria andApplycodeIsNotNull() {
            addCriterion("ApplyCode is not null");
            return (Criteria) this;
        }

        public Criteria andApplycodeEqualTo(String value) {
            addCriterion("ApplyCode =", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeNotEqualTo(String value) {
            addCriterion("ApplyCode <>", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeGreaterThan(String value) {
            addCriterion("ApplyCode >", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeGreaterThanOrEqualTo(String value) {
            addCriterion("ApplyCode >=", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeLessThan(String value) {
            addCriterion("ApplyCode <", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeLessThanOrEqualTo(String value) {
            addCriterion("ApplyCode <=", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeLike(String value) {
            addCriterion("ApplyCode like", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeNotLike(String value) {
            addCriterion("ApplyCode not like", value, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeIn(List<String> values) {
            addCriterion("ApplyCode in", values, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeNotIn(List<String> values) {
            addCriterion("ApplyCode not in", values, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeBetween(String value1, String value2) {
            addCriterion("ApplyCode between", value1, value2, "applycode");
            return (Criteria) this;
        }

        public Criteria andApplycodeNotBetween(String value1, String value2) {
            addCriterion("ApplyCode not between", value1, value2, "applycode");
            return (Criteria) this;
        }

        public Criteria andPriceApplyIsNull() {
            addCriterion("Price_apply is null");
            return (Criteria) this;
        }

        public Criteria andPriceApplyIsNotNull() {
            addCriterion("Price_apply is not null");
            return (Criteria) this;
        }

        public Criteria andPriceApplyEqualTo(BigDecimal value) {
            addCriterion("Price_apply =", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyNotEqualTo(BigDecimal value) {
            addCriterion("Price_apply <>", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyGreaterThan(BigDecimal value) {
            addCriterion("Price_apply >", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_apply >=", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyLessThan(BigDecimal value) {
            addCriterion("Price_apply <", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_apply <=", value, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyIn(List<BigDecimal> values) {
            addCriterion("Price_apply in", values, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyNotIn(List<BigDecimal> values) {
            addCriterion("Price_apply not in", values, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_apply between", value1, value2, "priceApply");
            return (Criteria) this;
        }

        public Criteria andPriceApplyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_apply not between", value1, value2, "priceApply");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createtime");
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

        public Criteria andRcvdeptnoIsNull() {
            addCriterion("RcvDeptNo is null");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoIsNotNull() {
            addCriterion("RcvDeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoEqualTo(String value) {
            addCriterion("RcvDeptNo =", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoNotEqualTo(String value) {
            addCriterion("RcvDeptNo <>", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoGreaterThan(String value) {
            addCriterion("RcvDeptNo >", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("RcvDeptNo >=", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoLessThan(String value) {
            addCriterion("RcvDeptNo <", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoLessThanOrEqualTo(String value) {
            addCriterion("RcvDeptNo <=", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoLike(String value) {
            addCriterion("RcvDeptNo like", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoNotLike(String value) {
            addCriterion("RcvDeptNo not like", value, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoIn(List<String> values) {
            addCriterion("RcvDeptNo in", values, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoNotIn(List<String> values) {
            addCriterion("RcvDeptNo not in", values, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoBetween(String value1, String value2) {
            addCriterion("RcvDeptNo between", value1, value2, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andRcvdeptnoNotBetween(String value1, String value2) {
            addCriterion("RcvDeptNo not between", value1, value2, "rcvdeptno");
            return (Criteria) this;
        }

        public Criteria andClaimNoIsNull() {
            addCriterion("claim_no is null");
            return (Criteria) this;
        }

        public Criteria andClaimNoIsNotNull() {
            addCriterion("claim_no is not null");
            return (Criteria) this;
        }

        public Criteria andClaimNoEqualTo(String value) {
            addCriterion("claim_no =", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoNotEqualTo(String value) {
            addCriterion("claim_no <>", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoGreaterThan(String value) {
            addCriterion("claim_no >", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoGreaterThanOrEqualTo(String value) {
            addCriterion("claim_no >=", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoLessThan(String value) {
            addCriterion("claim_no <", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoLessThanOrEqualTo(String value) {
            addCriterion("claim_no <=", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoLike(String value) {
            addCriterion("claim_no like", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoNotLike(String value) {
            addCriterion("claim_no not like", value, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoIn(List<String> values) {
            addCriterion("claim_no in", values, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoNotIn(List<String> values) {
            addCriterion("claim_no not in", values, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoBetween(String value1, String value2) {
            addCriterion("claim_no between", value1, value2, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimNoNotBetween(String value1, String value2) {
            addCriterion("claim_no not between", value1, value2, "claimNo");
            return (Criteria) this;
        }

        public Criteria andClaimAmountIsNull() {
            addCriterion("claim_amount is null");
            return (Criteria) this;
        }

        public Criteria andClaimAmountIsNotNull() {
            addCriterion("claim_amount is not null");
            return (Criteria) this;
        }

        public Criteria andClaimAmountEqualTo(BigDecimal value) {
            addCriterion("claim_amount =", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountNotEqualTo(BigDecimal value) {
            addCriterion("claim_amount <>", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountGreaterThan(BigDecimal value) {
            addCriterion("claim_amount >", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("claim_amount >=", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountLessThan(BigDecimal value) {
            addCriterion("claim_amount <", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("claim_amount <=", value, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountIn(List<BigDecimal> values) {
            addCriterion("claim_amount in", values, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountNotIn(List<BigDecimal> values) {
            addCriterion("claim_amount not in", values, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("claim_amount between", value1, value2, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andClaimAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("claim_amount not between", value1, value2, "claimAmount");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNull() {
            addCriterion("express_company is null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNotNull() {
            addCriterion("express_company is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyEqualTo(String value) {
            addCriterion("express_company =", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotEqualTo(String value) {
            addCriterion("express_company <>", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThan(String value) {
            addCriterion("express_company >", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("express_company >=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThan(String value) {
            addCriterion("express_company <", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThanOrEqualTo(String value) {
            addCriterion("express_company <=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLike(String value) {
            addCriterion("express_company like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotLike(String value) {
            addCriterion("express_company not like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIn(List<String> values) {
            addCriterion("express_company in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotIn(List<String> values) {
            addCriterion("express_company not in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyBetween(String value1, String value2) {
            addCriterion("express_company between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotBetween(String value1, String value2) {
            addCriterion("express_company not between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
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

        public Criteria andExpdetailIdIsNull() {
            addCriterion("expdetail_id is null");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdIsNotNull() {
            addCriterion("expdetail_id is not null");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdEqualTo(String value) {
            addCriterion("expdetail_id =", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdNotEqualTo(String value) {
            addCriterion("expdetail_id <>", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdGreaterThan(String value) {
            addCriterion("expdetail_id >", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("expdetail_id >=", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdLessThan(String value) {
            addCriterion("expdetail_id <", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdLessThanOrEqualTo(String value) {
            addCriterion("expdetail_id <=", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdLike(String value) {
            addCriterion("expdetail_id like", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdNotLike(String value) {
            addCriterion("expdetail_id not like", value, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdIn(List<String> values) {
            addCriterion("expdetail_id in", values, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdNotIn(List<String> values) {
            addCriterion("expdetail_id not in", values, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdBetween(String value1, String value2) {
            addCriterion("expdetail_id between", value1, value2, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andExpdetailIdNotBetween(String value1, String value2) {
            addCriterion("expdetail_id not between", value1, value2, "expdetailId");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
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
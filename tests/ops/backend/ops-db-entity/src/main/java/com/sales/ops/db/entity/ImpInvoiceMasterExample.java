package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ImpInvoiceMasterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ImpInvoiceMasterExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNull() {
            addCriterion("invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNotNull() {
            addCriterion("invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoEqualTo(String value) {
            addCriterion("invoice_no =", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotEqualTo(String value) {
            addCriterion("invoice_no <>", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThan(String value) {
            addCriterion("invoice_no >", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no >=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThan(String value) {
            addCriterion("invoice_no <", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_no <=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLike(String value) {
            addCriterion("invoice_no like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotLike(String value) {
            addCriterion("invoice_no not like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIn(List<String> values) {
            addCriterion("invoice_no in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotIn(List<String> values) {
            addCriterion("invoice_no not in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoBetween(String value1, String value2) {
            addCriterion("invoice_no between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("invoice_no not between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andTotalQtyIsNull() {
            addCriterion("total_qty is null");
            return (Criteria) this;
        }

        public Criteria andTotalQtyIsNotNull() {
            addCriterion("total_qty is not null");
            return (Criteria) this;
        }

        public Criteria andTotalQtyEqualTo(Integer value) {
            addCriterion("total_qty =", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyNotEqualTo(Integer value) {
            addCriterion("total_qty <>", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyGreaterThan(Integer value) {
            addCriterion("total_qty >", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_qty >=", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyLessThan(Integer value) {
            addCriterion("total_qty <", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyLessThanOrEqualTo(Integer value) {
            addCriterion("total_qty <=", value, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyIn(List<Integer> values) {
            addCriterion("total_qty in", values, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyNotIn(List<Integer> values) {
            addCriterion("total_qty not in", values, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyBetween(Integer value1, Integer value2) {
            addCriterion("total_qty between", value1, value2, "totalQty");
            return (Criteria) this;
        }

        public Criteria andTotalQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("total_qty not between", value1, value2, "totalQty");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andBoxQtyIsNull() {
            addCriterion("box_qty is null");
            return (Criteria) this;
        }

        public Criteria andBoxQtyIsNotNull() {
            addCriterion("box_qty is not null");
            return (Criteria) this;
        }

        public Criteria andBoxQtyEqualTo(Integer value) {
            addCriterion("box_qty =", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyNotEqualTo(Integer value) {
            addCriterion("box_qty <>", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyGreaterThan(Integer value) {
            addCriterion("box_qty >", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("box_qty >=", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyLessThan(Integer value) {
            addCriterion("box_qty <", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyLessThanOrEqualTo(Integer value) {
            addCriterion("box_qty <=", value, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyIn(List<Integer> values) {
            addCriterion("box_qty in", values, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyNotIn(List<Integer> values) {
            addCriterion("box_qty not in", values, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyBetween(Integer value1, Integer value2) {
            addCriterion("box_qty between", value1, value2, "boxQty");
            return (Criteria) this;
        }

        public Criteria andBoxQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("box_qty not between", value1, value2, "boxQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyIsNull() {
            addCriterion("order_qty is null");
            return (Criteria) this;
        }

        public Criteria andOrderQtyIsNotNull() {
            addCriterion("order_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOrderQtyEqualTo(Integer value) {
            addCriterion("order_qty =", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyNotEqualTo(Integer value) {
            addCriterion("order_qty <>", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyGreaterThan(Integer value) {
            addCriterion("order_qty >", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_qty >=", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyLessThan(Integer value) {
            addCriterion("order_qty <", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyLessThanOrEqualTo(Integer value) {
            addCriterion("order_qty <=", value, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyIn(List<Integer> values) {
            addCriterion("order_qty in", values, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyNotIn(List<Integer> values) {
            addCriterion("order_qty not in", values, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyBetween(Integer value1, Integer value2) {
            addCriterion("order_qty between", value1, value2, "orderQty");
            return (Criteria) this;
        }

        public Criteria andOrderQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("order_qty not between", value1, value2, "orderQty");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNull() {
            addCriterion("ship_date is null");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNotNull() {
            addCriterion("ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipDateEqualTo(Date value) {
            addCriterion("ship_date =", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotEqualTo(Date value) {
            addCriterion("ship_date <>", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThan(Date value) {
            addCriterion("ship_date >", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_date >=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThan(Date value) {
            addCriterion("ship_date <", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_date <=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateIn(List<Date> values) {
            addCriterion("ship_date in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotIn(List<Date> values) {
            addCriterion("ship_date not in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateBetween(Date value1, Date value2) {
            addCriterion("ship_date between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_date not between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIsNull() {
            addCriterion("customs_date is null");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIsNotNull() {
            addCriterion("customs_date is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsDateEqualTo(Date value) {
            addCriterion("customs_date =", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotEqualTo(Date value) {
            addCriterion("customs_date <>", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateGreaterThan(Date value) {
            addCriterion("customs_date >", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateGreaterThanOrEqualTo(Date value) {
            addCriterion("customs_date >=", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateLessThan(Date value) {
            addCriterion("customs_date <", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateLessThanOrEqualTo(Date value) {
            addCriterion("customs_date <=", value, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateIn(List<Date> values) {
            addCriterion("customs_date in", values, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotIn(List<Date> values) {
            addCriterion("customs_date not in", values, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateBetween(Date value1, Date value2) {
            addCriterion("customs_date between", value1, value2, "customsDate");
            return (Criteria) this;
        }

        public Criteria andCustomsDateNotBetween(Date value1, Date value2) {
            addCriterion("customs_date not between", value1, value2, "customsDate");
            return (Criteria) this;
        }

        public Criteria andGrossWeightIsNull() {
            addCriterion("gross_weight is null");
            return (Criteria) this;
        }

        public Criteria andGrossWeightIsNotNull() {
            addCriterion("gross_weight is not null");
            return (Criteria) this;
        }

        public Criteria andGrossWeightEqualTo(BigDecimal value) {
            addCriterion("gross_weight =", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightNotEqualTo(BigDecimal value) {
            addCriterion("gross_weight <>", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightGreaterThan(BigDecimal value) {
            addCriterion("gross_weight >", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_weight >=", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightLessThan(BigDecimal value) {
            addCriterion("gross_weight <", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_weight <=", value, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightIn(List<BigDecimal> values) {
            addCriterion("gross_weight in", values, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightNotIn(List<BigDecimal> values) {
            addCriterion("gross_weight not in", values, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_weight between", value1, value2, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andGrossWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_weight not between", value1, value2, "grossWeight");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIsNull() {
            addCriterion("customs_fee is null");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIsNotNull() {
            addCriterion("customs_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeEqualTo(BigDecimal value) {
            addCriterion("customs_fee =", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotEqualTo(BigDecimal value) {
            addCriterion("customs_fee <>", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeGreaterThan(BigDecimal value) {
            addCriterion("customs_fee >", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_fee >=", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeLessThan(BigDecimal value) {
            addCriterion("customs_fee <", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_fee <=", value, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeIn(List<BigDecimal> values) {
            addCriterion("customs_fee in", values, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotIn(List<BigDecimal> values) {
            addCriterion("customs_fee not in", values, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_fee between", value1, value2, "customsFee");
            return (Criteria) this;
        }

        public Criteria andCustomsFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_fee not between", value1, value2, "customsFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeIsNull() {
            addCriterion("vat_fee is null");
            return (Criteria) this;
        }

        public Criteria andVatFeeIsNotNull() {
            addCriterion("vat_fee is not null");
            return (Criteria) this;
        }

        public Criteria andVatFeeEqualTo(BigDecimal value) {
            addCriterion("vat_fee =", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotEqualTo(BigDecimal value) {
            addCriterion("vat_fee <>", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeGreaterThan(BigDecimal value) {
            addCriterion("vat_fee >", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vat_fee >=", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeLessThan(BigDecimal value) {
            addCriterion("vat_fee <", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vat_fee <=", value, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeIn(List<BigDecimal> values) {
            addCriterion("vat_fee in", values, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotIn(List<BigDecimal> values) {
            addCriterion("vat_fee not in", values, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat_fee between", value1, value2, "vatFee");
            return (Criteria) this;
        }

        public Criteria andVatFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat_fee not between", value1, value2, "vatFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNull() {
            addCriterion("other_fee is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNotNull() {
            addCriterion("other_fee is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeEqualTo(BigDecimal value) {
            addCriterion("other_fee =", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotEqualTo(BigDecimal value) {
            addCriterion("other_fee <>", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThan(BigDecimal value) {
            addCriterion("other_fee >", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee >=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThan(BigDecimal value) {
            addCriterion("other_fee <", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee <=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIn(List<BigDecimal> values) {
            addCriterion("other_fee in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotIn(List<BigDecimal> values) {
            addCriterion("other_fee not in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee not between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNull() {
            addCriterion("exchange_rate is null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNotNull() {
            addCriterion("exchange_rate is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateEqualTo(BigDecimal value) {
            addCriterion("exchange_rate =", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <>", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThan(BigDecimal value) {
            addCriterion("exchange_rate >", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate >=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThan(BigDecimal value) {
            addCriterion("exchange_rate <", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIn(List<BigDecimal> values) {
            addCriterion("exchange_rate in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotIn(List<BigDecimal> values) {
            addCriterion("exchange_rate not in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate not between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIsNull() {
            addCriterion("amount_rmb is null");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIsNotNull() {
            addCriterion("amount_rmb is not null");
            return (Criteria) this;
        }

        public Criteria andAmountRmbEqualTo(BigDecimal value) {
            addCriterion("amount_rmb =", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotEqualTo(BigDecimal value) {
            addCriterion("amount_rmb <>", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbGreaterThan(BigDecimal value) {
            addCriterion("amount_rmb >", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_rmb >=", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbLessThan(BigDecimal value) {
            addCriterion("amount_rmb <", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_rmb <=", value, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbIn(List<BigDecimal> values) {
            addCriterion("amount_rmb in", values, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotIn(List<BigDecimal> values) {
            addCriterion("amount_rmb not in", values, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_rmb between", value1, value2, "amountRmb");
            return (Criteria) this;
        }

        public Criteria andAmountRmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_rmb not between", value1, value2, "amountRmb");
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

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(String value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(String value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(String value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(String value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(String value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLike(String value) {
            addCriterion("trans_type like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotLike(String value) {
            addCriterion("trans_type not like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<String> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<String> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(String value1, String value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(String value1, String value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateIsNull() {
            addCriterion("prearrive_date is null");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateIsNotNull() {
            addCriterion("prearrive_date is not null");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateEqualTo(Date value) {
            addCriterion("prearrive_date =", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateNotEqualTo(Date value) {
            addCriterion("prearrive_date <>", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateGreaterThan(Date value) {
            addCriterion("prearrive_date >", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("prearrive_date >=", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateLessThan(Date value) {
            addCriterion("prearrive_date <", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateLessThanOrEqualTo(Date value) {
            addCriterion("prearrive_date <=", value, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateIn(List<Date> values) {
            addCriterion("prearrive_date in", values, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateNotIn(List<Date> values) {
            addCriterion("prearrive_date not in", values, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateBetween(Date value1, Date value2) {
            addCriterion("prearrive_date between", value1, value2, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andPrearriveDateNotBetween(Date value1, Date value2) {
            addCriterion("prearrive_date not between", value1, value2, "prearriveDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andShipmentIsNull() {
            addCriterion("shipment is null");
            return (Criteria) this;
        }

        public Criteria andShipmentIsNotNull() {
            addCriterion("shipment is not null");
            return (Criteria) this;
        }

        public Criteria andShipmentEqualTo(String value) {
            addCriterion("shipment =", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentNotEqualTo(String value) {
            addCriterion("shipment <>", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentGreaterThan(String value) {
            addCriterion("shipment >", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentGreaterThanOrEqualTo(String value) {
            addCriterion("shipment >=", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentLessThan(String value) {
            addCriterion("shipment <", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentLessThanOrEqualTo(String value) {
            addCriterion("shipment <=", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentLike(String value) {
            addCriterion("shipment like", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentNotLike(String value) {
            addCriterion("shipment not like", value, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentIn(List<String> values) {
            addCriterion("shipment in", values, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentNotIn(List<String> values) {
            addCriterion("shipment not in", values, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentBetween(String value1, String value2) {
            addCriterion("shipment between", value1, value2, "shipment");
            return (Criteria) this;
        }

        public Criteria andShipmentNotBetween(String value1, String value2) {
            addCriterion("shipment not between", value1, value2, "shipment");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoIsNull() {
            addCriterion("cinvoice_no is null");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoIsNotNull() {
            addCriterion("cinvoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoEqualTo(String value) {
            addCriterion("cinvoice_no =", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoNotEqualTo(String value) {
            addCriterion("cinvoice_no <>", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoGreaterThan(String value) {
            addCriterion("cinvoice_no >", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("cinvoice_no >=", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoLessThan(String value) {
            addCriterion("cinvoice_no <", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("cinvoice_no <=", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoLike(String value) {
            addCriterion("cinvoice_no like", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoNotLike(String value) {
            addCriterion("cinvoice_no not like", value, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoIn(List<String> values) {
            addCriterion("cinvoice_no in", values, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoNotIn(List<String> values) {
            addCriterion("cinvoice_no not in", values, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoBetween(String value1, String value2) {
            addCriterion("cinvoice_no between", value1, value2, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andCinvoiceNoNotBetween(String value1, String value2) {
            addCriterion("cinvoice_no not between", value1, value2, "cinvoiceNo");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNull() {
            addCriterion("trans_fee is null");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNotNull() {
            addCriterion("trans_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTransFeeEqualTo(BigDecimal value) {
            addCriterion("trans_fee =", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotEqualTo(BigDecimal value) {
            addCriterion("trans_fee <>", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThan(BigDecimal value) {
            addCriterion("trans_fee >", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_fee >=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThan(BigDecimal value) {
            addCriterion("trans_fee <", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_fee <=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIn(List<BigDecimal> values) {
            addCriterion("trans_fee in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotIn(List<BigDecimal> values) {
            addCriterion("trans_fee not in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_fee between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_fee not between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIsNull() {
            addCriterion("invoice_date is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIsNotNull() {
            addCriterion("invoice_date is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateEqualTo(Date value) {
            addCriterionForJDBCDate("invoice_date =", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("invoice_date <>", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThan(Date value) {
            addCriterionForJDBCDate("invoice_date >", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("invoice_date >=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThan(Date value) {
            addCriterionForJDBCDate("invoice_date <", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("invoice_date <=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIn(List<Date> values) {
            addCriterionForJDBCDate("invoice_date in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("invoice_date not in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("invoice_date between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("invoice_date not between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeIsNull() {
            addCriterion("arrived_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeIsNotNull() {
            addCriterion("arrived_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeEqualTo(String value) {
            addCriterion("arrived_warehouse_code =", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeNotEqualTo(String value) {
            addCriterion("arrived_warehouse_code <>", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeGreaterThan(String value) {
            addCriterion("arrived_warehouse_code >", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("arrived_warehouse_code >=", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeLessThan(String value) {
            addCriterion("arrived_warehouse_code <", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("arrived_warehouse_code <=", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeLike(String value) {
            addCriterion("arrived_warehouse_code like", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeNotLike(String value) {
            addCriterion("arrived_warehouse_code not like", value, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeIn(List<String> values) {
            addCriterion("arrived_warehouse_code in", values, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeNotIn(List<String> values) {
            addCriterion("arrived_warehouse_code not in", values, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeBetween(String value1, String value2) {
            addCriterion("arrived_warehouse_code between", value1, value2, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArrivedWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("arrived_warehouse_code not between", value1, value2, "arrivedWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeIsNull() {
            addCriterion("receive_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeIsNotNull() {
            addCriterion("receive_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeEqualTo(String value) {
            addCriterion("receive_warehouse_code =", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeNotEqualTo(String value) {
            addCriterion("receive_warehouse_code <>", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeGreaterThan(String value) {
            addCriterion("receive_warehouse_code >", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("receive_warehouse_code >=", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeLessThan(String value) {
            addCriterion("receive_warehouse_code <", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("receive_warehouse_code <=", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeLike(String value) {
            addCriterion("receive_warehouse_code like", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeNotLike(String value) {
            addCriterion("receive_warehouse_code not like", value, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeIn(List<String> values) {
            addCriterion("receive_warehouse_code in", values, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeNotIn(List<String> values) {
            addCriterion("receive_warehouse_code not in", values, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeBetween(String value1, String value2) {
            addCriterion("receive_warehouse_code between", value1, value2, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andReceiveWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("receive_warehouse_code not between", value1, value2, "receiveWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andArriveDateIsNull() {
            addCriterion("arrive_date is null");
            return (Criteria) this;
        }

        public Criteria andArriveDateIsNotNull() {
            addCriterion("arrive_date is not null");
            return (Criteria) this;
        }

        public Criteria andArriveDateEqualTo(Date value) {
            addCriterion("arrive_date =", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotEqualTo(Date value) {
            addCriterion("arrive_date <>", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateGreaterThan(Date value) {
            addCriterion("arrive_date >", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("arrive_date >=", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateLessThan(Date value) {
            addCriterion("arrive_date <", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateLessThanOrEqualTo(Date value) {
            addCriterion("arrive_date <=", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateIn(List<Date> values) {
            addCriterion("arrive_date in", values, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotIn(List<Date> values) {
            addCriterion("arrive_date not in", values, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateBetween(Date value1, Date value2) {
            addCriterion("arrive_date between", value1, value2, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotBetween(Date value1, Date value2) {
            addCriterion("arrive_date not between", value1, value2, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(Integer value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(Integer value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(Integer value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(Integer value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(Integer value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<Integer> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<Integer> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(Integer value1, Integer value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNull() {
            addCriterion("receive_date is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNotNull() {
            addCriterion("receive_date is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateEqualTo(Date value) {
            addCriterion("receive_date =", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotEqualTo(Date value) {
            addCriterion("receive_date <>", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThan(Date value) {
            addCriterion("receive_date >", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("receive_date >=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThan(Date value) {
            addCriterion("receive_date <", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThanOrEqualTo(Date value) {
            addCriterion("receive_date <=", value, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIn(List<Date> values) {
            addCriterion("receive_date in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotIn(List<Date> values) {
            addCriterion("receive_date not in", values, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateBetween(Date value1, Date value2) {
            addCriterion("receive_date between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotBetween(Date value1, Date value2) {
            addCriterion("receive_date not between", value1, value2, "receiveDate");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoIsNull() {
            addCriterion("declaration_no is null");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoIsNotNull() {
            addCriterion("declaration_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoEqualTo(String value) {
            addCriterion("declaration_no =", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoNotEqualTo(String value) {
            addCriterion("declaration_no <>", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoGreaterThan(String value) {
            addCriterion("declaration_no >", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoGreaterThanOrEqualTo(String value) {
            addCriterion("declaration_no >=", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoLessThan(String value) {
            addCriterion("declaration_no <", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoLessThanOrEqualTo(String value) {
            addCriterion("declaration_no <=", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoLike(String value) {
            addCriterion("declaration_no like", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoNotLike(String value) {
            addCriterion("declaration_no not like", value, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoIn(List<String> values) {
            addCriterion("declaration_no in", values, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoNotIn(List<String> values) {
            addCriterion("declaration_no not in", values, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoBetween(String value1, String value2) {
            addCriterion("declaration_no between", value1, value2, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andDeclarationNoNotBetween(String value1, String value2) {
            addCriterion("declaration_no not between", value1, value2, "declarationNo");
            return (Criteria) this;
        }

        public Criteria andPlantmarkIsNull() {
            addCriterion("plantMark is null");
            return (Criteria) this;
        }

        public Criteria andPlantmarkIsNotNull() {
            addCriterion("plantMark is not null");
            return (Criteria) this;
        }

        public Criteria andPlantmarkEqualTo(String value) {
            addCriterion("plantMark =", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkNotEqualTo(String value) {
            addCriterion("plantMark <>", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkGreaterThan(String value) {
            addCriterion("plantMark >", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkGreaterThanOrEqualTo(String value) {
            addCriterion("plantMark >=", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkLessThan(String value) {
            addCriterion("plantMark <", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkLessThanOrEqualTo(String value) {
            addCriterion("plantMark <=", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkLike(String value) {
            addCriterion("plantMark like", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkNotLike(String value) {
            addCriterion("plantMark not like", value, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkIn(List<String> values) {
            addCriterion("plantMark in", values, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkNotIn(List<String> values) {
            addCriterion("plantMark not in", values, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkBetween(String value1, String value2) {
            addCriterion("plantMark between", value1, value2, "plantmark");
            return (Criteria) this;
        }

        public Criteria andPlantmarkNotBetween(String value1, String value2) {
            addCriterion("plantMark not between", value1, value2, "plantmark");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIsNull() {
            addCriterion("excise_tax is null");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIsNotNull() {
            addCriterion("excise_tax is not null");
            return (Criteria) this;
        }

        public Criteria andExciseTaxEqualTo(BigDecimal value) {
            addCriterion("excise_tax =", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotEqualTo(BigDecimal value) {
            addCriterion("excise_tax <>", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxGreaterThan(BigDecimal value) {
            addCriterion("excise_tax >", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("excise_tax >=", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxLessThan(BigDecimal value) {
            addCriterion("excise_tax <", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("excise_tax <=", value, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxIn(List<BigDecimal> values) {
            addCriterion("excise_tax in", values, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotIn(List<BigDecimal> values) {
            addCriterion("excise_tax not in", values, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("excise_tax between", value1, value2, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andExciseTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("excise_tax not between", value1, value2, "exciseTax");
            return (Criteria) this;
        }

        public Criteria andBargainTypeIsNull() {
            addCriterion("bargain_type is null");
            return (Criteria) this;
        }

        public Criteria andBargainTypeIsNotNull() {
            addCriterion("bargain_type is not null");
            return (Criteria) this;
        }

        public Criteria andBargainTypeEqualTo(String value) {
            addCriterion("bargain_type =", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeNotEqualTo(String value) {
            addCriterion("bargain_type <>", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeGreaterThan(String value) {
            addCriterion("bargain_type >", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bargain_type >=", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeLessThan(String value) {
            addCriterion("bargain_type <", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeLessThanOrEqualTo(String value) {
            addCriterion("bargain_type <=", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeLike(String value) {
            addCriterion("bargain_type like", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeNotLike(String value) {
            addCriterion("bargain_type not like", value, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeIn(List<String> values) {
            addCriterion("bargain_type in", values, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeNotIn(List<String> values) {
            addCriterion("bargain_type not in", values, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeBetween(String value1, String value2) {
            addCriterion("bargain_type between", value1, value2, "bargainType");
            return (Criteria) this;
        }

        public Criteria andBargainTypeNotBetween(String value1, String value2) {
            addCriterion("bargain_type not between", value1, value2, "bargainType");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateIsNull() {
            addCriterion("port_arrivedate is null");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateIsNotNull() {
            addCriterion("port_arrivedate is not null");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateEqualTo(Date value) {
            addCriterion("port_arrivedate =", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotEqualTo(Date value) {
            addCriterion("port_arrivedate <>", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateGreaterThan(Date value) {
            addCriterion("port_arrivedate >", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateGreaterThanOrEqualTo(Date value) {
            addCriterion("port_arrivedate >=", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateLessThan(Date value) {
            addCriterion("port_arrivedate <", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateLessThanOrEqualTo(Date value) {
            addCriterion("port_arrivedate <=", value, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateIn(List<Date> values) {
            addCriterion("port_arrivedate in", values, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotIn(List<Date> values) {
            addCriterion("port_arrivedate not in", values, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateBetween(Date value1, Date value2) {
            addCriterion("port_arrivedate between", value1, value2, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andPortArrivedateNotBetween(Date value1, Date value2) {
            addCriterion("port_arrivedate not between", value1, value2, "portArrivedate");
            return (Criteria) this;
        }

        public Criteria andGwStateIsNull() {
            addCriterion("gw_state is null");
            return (Criteria) this;
        }

        public Criteria andGwStateIsNotNull() {
            addCriterion("gw_state is not null");
            return (Criteria) this;
        }

        public Criteria andGwStateEqualTo(String value) {
            addCriterion("gw_state =", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateNotEqualTo(String value) {
            addCriterion("gw_state <>", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateGreaterThan(String value) {
            addCriterion("gw_state >", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateGreaterThanOrEqualTo(String value) {
            addCriterion("gw_state >=", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateLessThan(String value) {
            addCriterion("gw_state <", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateLessThanOrEqualTo(String value) {
            addCriterion("gw_state <=", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateLike(String value) {
            addCriterion("gw_state like", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateNotLike(String value) {
            addCriterion("gw_state not like", value, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateIn(List<String> values) {
            addCriterion("gw_state in", values, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateNotIn(List<String> values) {
            addCriterion("gw_state not in", values, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateBetween(String value1, String value2) {
            addCriterion("gw_state between", value1, value2, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStateNotBetween(String value1, String value2) {
            addCriterion("gw_state not between", value1, value2, "gwState");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeIsNull() {
            addCriterion("gw_stateCode is null");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeIsNotNull() {
            addCriterion("gw_stateCode is not null");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeEqualTo(String value) {
            addCriterion("gw_stateCode =", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeNotEqualTo(String value) {
            addCriterion("gw_stateCode <>", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeGreaterThan(String value) {
            addCriterion("gw_stateCode >", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("gw_stateCode >=", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeLessThan(String value) {
            addCriterion("gw_stateCode <", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeLessThanOrEqualTo(String value) {
            addCriterion("gw_stateCode <=", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeLike(String value) {
            addCriterion("gw_stateCode like", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeNotLike(String value) {
            addCriterion("gw_stateCode not like", value, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeIn(List<String> values) {
            addCriterion("gw_stateCode in", values, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeNotIn(List<String> values) {
            addCriterion("gw_stateCode not in", values, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeBetween(String value1, String value2) {
            addCriterion("gw_stateCode between", value1, value2, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andGwStatecodeNotBetween(String value1, String value2) {
            addCriterion("gw_stateCode not between", value1, value2, "gwStatecode");
            return (Criteria) this;
        }

        public Criteria andPayDayIsNull() {
            addCriterion("pay_day is null");
            return (Criteria) this;
        }

        public Criteria andPayDayIsNotNull() {
            addCriterion("pay_day is not null");
            return (Criteria) this;
        }

        public Criteria andPayDayEqualTo(Integer value) {
            addCriterion("pay_day =", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayNotEqualTo(Integer value) {
            addCriterion("pay_day <>", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayGreaterThan(Integer value) {
            addCriterion("pay_day >", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_day >=", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayLessThan(Integer value) {
            addCriterion("pay_day <", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayLessThanOrEqualTo(Integer value) {
            addCriterion("pay_day <=", value, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayIn(List<Integer> values) {
            addCriterion("pay_day in", values, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayNotIn(List<Integer> values) {
            addCriterion("pay_day not in", values, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayBetween(Integer value1, Integer value2) {
            addCriterion("pay_day between", value1, value2, "payDay");
            return (Criteria) this;
        }

        public Criteria andPayDayNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_day not between", value1, value2, "payDay");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateIsNull() {
            addCriterion("customs_shipdate is null");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateIsNotNull() {
            addCriterion("customs_shipdate is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateEqualTo(Date value) {
            addCriterion("customs_shipdate =", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateNotEqualTo(Date value) {
            addCriterion("customs_shipdate <>", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateGreaterThan(Date value) {
            addCriterion("customs_shipdate >", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateGreaterThanOrEqualTo(Date value) {
            addCriterion("customs_shipdate >=", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateLessThan(Date value) {
            addCriterion("customs_shipdate <", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateLessThanOrEqualTo(Date value) {
            addCriterion("customs_shipdate <=", value, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateIn(List<Date> values) {
            addCriterion("customs_shipdate in", values, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateNotIn(List<Date> values) {
            addCriterion("customs_shipdate not in", values, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateBetween(Date value1, Date value2) {
            addCriterion("customs_shipdate between", value1, value2, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andCustomsShipdateNotBetween(Date value1, Date value2) {
            addCriterion("customs_shipdate not between", value1, value2, "customsShipdate");
            return (Criteria) this;
        }

        public Criteria andShipAmountIsNull() {
            addCriterion("ship_amount is null");
            return (Criteria) this;
        }

        public Criteria andShipAmountIsNotNull() {
            addCriterion("ship_amount is not null");
            return (Criteria) this;
        }

        public Criteria andShipAmountEqualTo(BigDecimal value) {
            addCriterion("ship_amount =", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountNotEqualTo(BigDecimal value) {
            addCriterion("ship_amount <>", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountGreaterThan(BigDecimal value) {
            addCriterion("ship_amount >", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ship_amount >=", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountLessThan(BigDecimal value) {
            addCriterion("ship_amount <", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ship_amount <=", value, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountIn(List<BigDecimal> values) {
            addCriterion("ship_amount in", values, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountNotIn(List<BigDecimal> values) {
            addCriterion("ship_amount not in", values, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ship_amount between", value1, value2, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andShipAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ship_amount not between", value1, value2, "shipAmount");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNull() {
            addCriterion("invoice_type is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNotNull() {
            addCriterion("invoice_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeEqualTo(Integer value) {
            addCriterion("invoice_type =", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotEqualTo(Integer value) {
            addCriterion("invoice_type <>", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThan(Integer value) {
            addCriterion("invoice_type >", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_type >=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThan(Integer value) {
            addCriterion("invoice_type <", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_type <=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIn(List<Integer> values) {
            addCriterion("invoice_type in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotIn(List<Integer> values) {
            addCriterion("invoice_type not in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeBetween(Integer value1, Integer value2) {
            addCriterion("invoice_type between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_type not between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNull() {
            addCriterion("confirm_date is null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNotNull() {
            addCriterion("confirm_date is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateEqualTo(Date value) {
            addCriterion("confirm_date =", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotEqualTo(Date value) {
            addCriterion("confirm_date <>", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThan(Date value) {
            addCriterion("confirm_date >", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_date >=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThan(Date value) {
            addCriterion("confirm_date <", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThanOrEqualTo(Date value) {
            addCriterion("confirm_date <=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIn(List<Date> values) {
            addCriterion("confirm_date in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotIn(List<Date> values) {
            addCriterion("confirm_date not in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateBetween(Date value1, Date value2) {
            addCriterion("confirm_date between", value1, value2, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotBetween(Date value1, Date value2) {
            addCriterion("confirm_date not between", value1, value2, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNull() {
            addCriterion("opt_status is null");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNotNull() {
            addCriterion("opt_status is not null");
            return (Criteria) this;
        }

        public Criteria andOptStatusEqualTo(String value) {
            addCriterion("opt_status =", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotEqualTo(String value) {
            addCriterion("opt_status <>", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThan(String value) {
            addCriterion("opt_status >", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThanOrEqualTo(String value) {
            addCriterion("opt_status >=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThan(String value) {
            addCriterion("opt_status <", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThanOrEqualTo(String value) {
            addCriterion("opt_status <=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLike(String value) {
            addCriterion("opt_status like", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotLike(String value) {
            addCriterion("opt_status not like", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusIn(List<String> values) {
            addCriterion("opt_status in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotIn(List<String> values) {
            addCriterion("opt_status not in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusBetween(String value1, String value2) {
            addCriterion("opt_status between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotBetween(String value1, String value2) {
            addCriterion("opt_status not between", value1, value2, "optStatus");
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
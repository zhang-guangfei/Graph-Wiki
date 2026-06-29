package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempMergeInvExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TempMergeInvExample() {
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

        public Criteria andInvoiceidIsNull() {
            addCriterion("invoiceId is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNotNull() {
            addCriterion("invoiceId is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidEqualTo(Long value) {
            addCriterion("invoiceId =", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotEqualTo(Long value) {
            addCriterion("invoiceId <>", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThan(Long value) {
            addCriterion("invoiceId >", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThanOrEqualTo(Long value) {
            addCriterion("invoiceId >=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThan(Long value) {
            addCriterion("invoiceId <", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThanOrEqualTo(Long value) {
            addCriterion("invoiceId <=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIn(List<Long> values) {
            addCriterion("invoiceId in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotIn(List<Long> values) {
            addCriterion("invoiceId not in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidBetween(Long value1, Long value2) {
            addCriterion("invoiceId between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotBetween(Long value1, Long value2) {
            addCriterion("invoiceId not between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrderitemIsNull() {
            addCriterion("orderItem is null");
            return (Criteria) this;
        }

        public Criteria andOrderitemIsNotNull() {
            addCriterion("orderItem is not null");
            return (Criteria) this;
        }

        public Criteria andOrderitemEqualTo(Integer value) {
            addCriterion("orderItem =", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotEqualTo(Integer value) {
            addCriterion("orderItem <>", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemGreaterThan(Integer value) {
            addCriterion("orderItem >", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderItem >=", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemLessThan(Integer value) {
            addCriterion("orderItem <", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemLessThanOrEqualTo(Integer value) {
            addCriterion("orderItem <=", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemIn(List<Integer> values) {
            addCriterion("orderItem in", values, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotIn(List<Integer> values) {
            addCriterion("orderItem not in", values, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemBetween(Integer value1, Integer value2) {
            addCriterion("orderItem between", value1, value2, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotBetween(Integer value1, Integer value2) {
            addCriterion("orderItem not between", value1, value2, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIsNull() {
            addCriterion("orderItemSplit is null");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIsNotNull() {
            addCriterion("orderItemSplit is not null");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitEqualTo(Integer value) {
            addCriterion("orderItemSplit =", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotEqualTo(Integer value) {
            addCriterion("orderItemSplit <>", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitGreaterThan(Integer value) {
            addCriterion("orderItemSplit >", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderItemSplit >=", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitLessThan(Integer value) {
            addCriterion("orderItemSplit <", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitLessThanOrEqualTo(Integer value) {
            addCriterion("orderItemSplit <=", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIn(List<Integer> values) {
            addCriterion("orderItemSplit in", values, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotIn(List<Integer> values) {
            addCriterion("orderItemSplit not in", values, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitBetween(Integer value1, Integer value2) {
            addCriterion("orderItemSplit between", value1, value2, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotBetween(Integer value1, Integer value2) {
            addCriterion("orderItemSplit not between", value1, value2, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andPreQtyIsNull() {
            addCriterion("pre_qty is null");
            return (Criteria) this;
        }

        public Criteria andPreQtyIsNotNull() {
            addCriterion("pre_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPreQtyEqualTo(Integer value) {
            addCriterion("pre_qty =", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyNotEqualTo(Integer value) {
            addCriterion("pre_qty <>", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyGreaterThan(Integer value) {
            addCriterion("pre_qty >", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("pre_qty >=", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyLessThan(Integer value) {
            addCriterion("pre_qty <", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyLessThanOrEqualTo(Integer value) {
            addCriterion("pre_qty <=", value, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyIn(List<Integer> values) {
            addCriterion("pre_qty in", values, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyNotIn(List<Integer> values) {
            addCriterion("pre_qty not in", values, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyBetween(Integer value1, Integer value2) {
            addCriterion("pre_qty between", value1, value2, "preQty");
            return (Criteria) this;
        }

        public Criteria andPreQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("pre_qty not between", value1, value2, "preQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNull() {
            addCriterion("invoice_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNotNull() {
            addCriterion("invoice_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyEqualTo(Integer value) {
            addCriterion("invoice_qty =", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotEqualTo(Integer value) {
            addCriterion("invoice_qty <>", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThan(Integer value) {
            addCriterion("invoice_qty >", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty >=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThan(Integer value) {
            addCriterion("invoice_qty <", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty <=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIn(List<Integer> values) {
            addCriterion("invoice_qty in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotIn(List<Integer> values) {
            addCriterion("invoice_qty not in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty not between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyIsNull() {
            addCriterion("po_qty is null");
            return (Criteria) this;
        }

        public Criteria andPoQtyIsNotNull() {
            addCriterion("po_qty is not null");
            return (Criteria) this;
        }

        public Criteria andPoQtyEqualTo(Integer value) {
            addCriterion("po_qty =", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotEqualTo(Integer value) {
            addCriterion("po_qty <>", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyGreaterThan(Integer value) {
            addCriterion("po_qty >", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("po_qty >=", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyLessThan(Integer value) {
            addCriterion("po_qty <", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyLessThanOrEqualTo(Integer value) {
            addCriterion("po_qty <=", value, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyIn(List<Integer> values) {
            addCriterion("po_qty in", values, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotIn(List<Integer> values) {
            addCriterion("po_qty not in", values, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyBetween(Integer value1, Integer value2) {
            addCriterion("po_qty between", value1, value2, "poQty");
            return (Criteria) this;
        }

        public Criteria andPoQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("po_qty not between", value1, value2, "poQty");
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

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
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

        public Criteria andDoIdIsNull() {
            addCriterion("do_id is null");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNotNull() {
            addCriterion("do_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoIdEqualTo(String value) {
            addCriterion("do_id =", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotEqualTo(String value) {
            addCriterion("do_id <>", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThan(String value) {
            addCriterion("do_id >", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThanOrEqualTo(String value) {
            addCriterion("do_id >=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThan(String value) {
            addCriterion("do_id <", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThanOrEqualTo(String value) {
            addCriterion("do_id <=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLike(String value) {
            addCriterion("do_id like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotLike(String value) {
            addCriterion("do_id not like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdIn(List<String> values) {
            addCriterion("do_id in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotIn(List<String> values) {
            addCriterion("do_id not in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdBetween(String value1, String value2) {
            addCriterion("do_id between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotBetween(String value1, String value2) {
            addCriterion("do_id not between", value1, value2, "doId");
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
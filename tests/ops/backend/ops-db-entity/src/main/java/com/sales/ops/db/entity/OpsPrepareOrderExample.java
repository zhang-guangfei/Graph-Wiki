package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPrepareOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPrepareOrderExample() {
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

        public Criteria andOrderFnoIsNull() {
            addCriterion("order_fno is null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIsNotNull() {
            addCriterion("order_fno is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoEqualTo(String value) {
            addCriterion("order_fno =", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotEqualTo(String value) {
            addCriterion("order_fno <>", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThan(String value) {
            addCriterion("order_fno >", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("order_fno >=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThan(String value) {
            addCriterion("order_fno <", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThanOrEqualTo(String value) {
            addCriterion("order_fno <=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLike(String value) {
            addCriterion("order_fno like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotLike(String value) {
            addCriterion("order_fno not like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIn(List<String> values) {
            addCriterion("order_fno in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotIn(List<String> values) {
            addCriterion("order_fno not in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoBetween(String value1, String value2) {
            addCriterion("order_fno between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotBetween(String value1, String value2) {
            addCriterion("order_fno not between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNull() {
            addCriterion("item_no is null");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNotNull() {
            addCriterion("item_no is not null");
            return (Criteria) this;
        }

        public Criteria andItemNoEqualTo(Integer value) {
            addCriterion("item_no =", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotEqualTo(Integer value) {
            addCriterion("item_no <>", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThan(Integer value) {
            addCriterion("item_no >", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_no >=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThan(Integer value) {
            addCriterion("item_no <", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThanOrEqualTo(Integer value) {
            addCriterion("item_no <=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIn(List<Integer> values) {
            addCriterion("item_no in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotIn(List<Integer> values) {
            addCriterion("item_no not in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoBetween(Integer value1, Integer value2) {
            addCriterion("item_no between", value1, value2, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotBetween(Integer value1, Integer value2) {
            addCriterion("item_no not between", value1, value2, "itemNo");
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

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateIsNull() {
            addCriterion("hope_export_date is null");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateIsNotNull() {
            addCriterion("hope_export_date is not null");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateEqualTo(Date value) {
            addCriterion("hope_export_date =", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotEqualTo(Date value) {
            addCriterion("hope_export_date <>", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateGreaterThan(Date value) {
            addCriterion("hope_export_date >", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateGreaterThanOrEqualTo(Date value) {
            addCriterion("hope_export_date >=", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateLessThan(Date value) {
            addCriterion("hope_export_date <", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateLessThanOrEqualTo(Date value) {
            addCriterion("hope_export_date <=", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateIn(List<Date> values) {
            addCriterion("hope_export_date in", values, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotIn(List<Date> values) {
            addCriterion("hope_export_date not in", values, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateBetween(Date value1, Date value2) {
            addCriterion("hope_export_date between", value1, value2, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotBetween(Date value1, Date value2) {
            addCriterion("hope_export_date not between", value1, value2, "hopeExportDate");
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

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNull() {
            addCriterion("model_no is null");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNotNull() {
            addCriterion("model_no is not null");
            return (Criteria) this;
        }

        public Criteria andModelNoEqualTo(String value) {
            addCriterion("model_no =", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotEqualTo(String value) {
            addCriterion("model_no <>", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThan(String value) {
            addCriterion("model_no >", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("model_no >=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThan(String value) {
            addCriterion("model_no <", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThanOrEqualTo(String value) {
            addCriterion("model_no <=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLike(String value) {
            addCriterion("model_no like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotLike(String value) {
            addCriterion("model_no not like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoIn(List<String> values) {
            addCriterion("model_no in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotIn(List<String> values) {
            addCriterion("model_no not in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoBetween(String value1, String value2) {
            addCriterion("model_no between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotBetween(String value1, String value2) {
            addCriterion("model_no not between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNull() {
            addCriterion("dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNotNull() {
            addCriterion("dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andDlvDateEqualTo(Date value) {
            addCriterion("dlv_date =", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotEqualTo(Date value) {
            addCriterion("dlv_date <>", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThan(Date value) {
            addCriterion("dlv_date >", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("dlv_date >=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThan(Date value) {
            addCriterion("dlv_date <", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("dlv_date <=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateIn(List<Date> values) {
            addCriterion("dlv_date in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotIn(List<Date> values) {
            addCriterion("dlv_date not in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateBetween(Date value1, Date value2) {
            addCriterion("dlv_date between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("dlv_date not between", value1, value2, "dlvDate");
            return (Criteria) this;
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

        public Criteria andAvailableCustomersIsNull() {
            addCriterion("available_customers is null");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersIsNotNull() {
            addCriterion("available_customers is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersEqualTo(String value) {
            addCriterion("available_customers =", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersNotEqualTo(String value) {
            addCriterion("available_customers <>", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersGreaterThan(String value) {
            addCriterion("available_customers >", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersGreaterThanOrEqualTo(String value) {
            addCriterion("available_customers >=", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersLessThan(String value) {
            addCriterion("available_customers <", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersLessThanOrEqualTo(String value) {
            addCriterion("available_customers <=", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersLike(String value) {
            addCriterion("available_customers like", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersNotLike(String value) {
            addCriterion("available_customers not like", value, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersIn(List<String> values) {
            addCriterion("available_customers in", values, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersNotIn(List<String> values) {
            addCriterion("available_customers not in", values, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersBetween(String value1, String value2) {
            addCriterion("available_customers between", value1, value2, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andAvailableCustomersNotBetween(String value1, String value2) {
            addCriterion("available_customers not between", value1, value2, "availableCustomers");
            return (Criteria) this;
        }

        public Criteria andApplyNoIsNull() {
            addCriterion("apply_no is null");
            return (Criteria) this;
        }

        public Criteria andApplyNoIsNotNull() {
            addCriterion("apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andApplyNoEqualTo(String value) {
            addCriterion("apply_no =", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotEqualTo(String value) {
            addCriterion("apply_no <>", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoGreaterThan(String value) {
            addCriterion("apply_no >", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("apply_no >=", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLessThan(String value) {
            addCriterion("apply_no <", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLessThanOrEqualTo(String value) {
            addCriterion("apply_no <=", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoLike(String value) {
            addCriterion("apply_no like", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotLike(String value) {
            addCriterion("apply_no not like", value, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoIn(List<String> values) {
            addCriterion("apply_no in", values, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotIn(List<String> values) {
            addCriterion("apply_no not in", values, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoBetween(String value1, String value2) {
            addCriterion("apply_no between", value1, value2, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyNoNotBetween(String value1, String value2) {
            addCriterion("apply_no not between", value1, value2, "applyNo");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Long value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Long value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Long value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Long value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Long value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Long> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Long> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Long value1, Long value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Long value1, Long value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyQtyIsNull() {
            addCriterion("apply_qty is null");
            return (Criteria) this;
        }

        public Criteria andApplyQtyIsNotNull() {
            addCriterion("apply_qty is not null");
            return (Criteria) this;
        }

        public Criteria andApplyQtyEqualTo(Integer value) {
            addCriterion("apply_qty =", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyNotEqualTo(Integer value) {
            addCriterion("apply_qty <>", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyGreaterThan(Integer value) {
            addCriterion("apply_qty >", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_qty >=", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyLessThan(Integer value) {
            addCriterion("apply_qty <", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyLessThanOrEqualTo(Integer value) {
            addCriterion("apply_qty <=", value, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyIn(List<Integer> values) {
            addCriterion("apply_qty in", values, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyNotIn(List<Integer> values) {
            addCriterion("apply_qty not in", values, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyBetween(Integer value1, Integer value2) {
            addCriterion("apply_qty between", value1, value2, "applyQty");
            return (Criteria) this;
        }

        public Criteria andApplyQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_qty not between", value1, value2, "applyQty");
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

        public Criteria andRohsIsNull() {
            addCriterion("rohs is null");
            return (Criteria) this;
        }

        public Criteria andRohsIsNotNull() {
            addCriterion("rohs is not null");
            return (Criteria) this;
        }

        public Criteria andRohsEqualTo(String value) {
            addCriterion("rohs =", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotEqualTo(String value) {
            addCriterion("rohs <>", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThan(String value) {
            addCriterion("rohs >", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThanOrEqualTo(String value) {
            addCriterion("rohs >=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThan(String value) {
            addCriterion("rohs <", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThanOrEqualTo(String value) {
            addCriterion("rohs <=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLike(String value) {
            addCriterion("rohs like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotLike(String value) {
            addCriterion("rohs not like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsIn(List<String> values) {
            addCriterion("rohs in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotIn(List<String> values) {
            addCriterion("rohs not in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsBetween(String value1, String value2) {
            addCriterion("rohs between", value1, value2, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotBetween(String value1, String value2) {
            addCriterion("rohs not between", value1, value2, "rohs");
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

        public Criteria andApplyPsnIsNull() {
            addCriterion("apply_psn is null");
            return (Criteria) this;
        }

        public Criteria andApplyPsnIsNotNull() {
            addCriterion("apply_psn is not null");
            return (Criteria) this;
        }

        public Criteria andApplyPsnEqualTo(String value) {
            addCriterion("apply_psn =", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnNotEqualTo(String value) {
            addCriterion("apply_psn <>", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnGreaterThan(String value) {
            addCriterion("apply_psn >", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnGreaterThanOrEqualTo(String value) {
            addCriterion("apply_psn >=", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnLessThan(String value) {
            addCriterion("apply_psn <", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnLessThanOrEqualTo(String value) {
            addCriterion("apply_psn <=", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnLike(String value) {
            addCriterion("apply_psn like", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnNotLike(String value) {
            addCriterion("apply_psn not like", value, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnIn(List<String> values) {
            addCriterion("apply_psn in", values, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnNotIn(List<String> values) {
            addCriterion("apply_psn not in", values, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnBetween(String value1, String value2) {
            addCriterion("apply_psn between", value1, value2, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyPsnNotBetween(String value1, String value2) {
            addCriterion("apply_psn not between", value1, value2, "applyPsn");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIsNull() {
            addCriterion("apply_dept_no is null");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIsNotNull() {
            addCriterion("apply_dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoEqualTo(String value) {
            addCriterion("apply_dept_no =", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotEqualTo(String value) {
            addCriterion("apply_dept_no <>", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoGreaterThan(String value) {
            addCriterion("apply_dept_no >", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("apply_dept_no >=", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLessThan(String value) {
            addCriterion("apply_dept_no <", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLessThanOrEqualTo(String value) {
            addCriterion("apply_dept_no <=", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLike(String value) {
            addCriterion("apply_dept_no like", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotLike(String value) {
            addCriterion("apply_dept_no not like", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIn(List<String> values) {
            addCriterion("apply_dept_no in", values, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotIn(List<String> values) {
            addCriterion("apply_dept_no not in", values, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoBetween(String value1, String value2) {
            addCriterion("apply_dept_no between", value1, value2, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotBetween(String value1, String value2) {
            addCriterion("apply_dept_no not between", value1, value2, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIsNull() {
            addCriterion("inventory_property_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIsNotNull() {
            addCriterion("inventory_property_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdEqualTo(Long value) {
            addCriterion("inventory_property_id =", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotEqualTo(Long value) {
            addCriterion("inventory_property_id <>", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThan(Long value) {
            addCriterion("inventory_property_id >", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id >=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThan(Long value) {
            addCriterion("inventory_property_id <", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id <=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIn(List<Long> values) {
            addCriterion("inventory_property_id in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotIn(List<Long> values) {
            addCriterion("inventory_property_id not in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id between", value1, value2, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id not between", value1, value2, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andUseQtyIsNull() {
            addCriterion("use_qty is null");
            return (Criteria) this;
        }

        public Criteria andUseQtyIsNotNull() {
            addCriterion("use_qty is not null");
            return (Criteria) this;
        }

        public Criteria andUseQtyEqualTo(Integer value) {
            addCriterion("use_qty =", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotEqualTo(Integer value) {
            addCriterion("use_qty <>", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThan(Integer value) {
            addCriterion("use_qty >", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("use_qty >=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThan(Integer value) {
            addCriterion("use_qty <", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThanOrEqualTo(Integer value) {
            addCriterion("use_qty <=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyIn(List<Integer> values) {
            addCriterion("use_qty in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotIn(List<Integer> values) {
            addCriterion("use_qty not in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyBetween(Integer value1, Integer value2) {
            addCriterion("use_qty between", value1, value2, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("use_qty not between", value1, value2, "useQty");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoIsNull() {
            addCriterion("manu_order_no is null");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoIsNotNull() {
            addCriterion("manu_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoEqualTo(String value) {
            addCriterion("manu_order_no =", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoNotEqualTo(String value) {
            addCriterion("manu_order_no <>", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoGreaterThan(String value) {
            addCriterion("manu_order_no >", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("manu_order_no >=", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoLessThan(String value) {
            addCriterion("manu_order_no <", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoLessThanOrEqualTo(String value) {
            addCriterion("manu_order_no <=", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoLike(String value) {
            addCriterion("manu_order_no like", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoNotLike(String value) {
            addCriterion("manu_order_no not like", value, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoIn(List<String> values) {
            addCriterion("manu_order_no in", values, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoNotIn(List<String> values) {
            addCriterion("manu_order_no not in", values, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoBetween(String value1, String value2) {
            addCriterion("manu_order_no between", value1, value2, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuOrderNoNotBetween(String value1, String value2) {
            addCriterion("manu_order_no not between", value1, value2, "manuOrderNo");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeIsNull() {
            addCriterion("manu_fact_supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeIsNotNull() {
            addCriterion("manu_fact_supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeEqualTo(String value) {
            addCriterion("manu_fact_supplier_code =", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeNotEqualTo(String value) {
            addCriterion("manu_fact_supplier_code <>", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeGreaterThan(String value) {
            addCriterion("manu_fact_supplier_code >", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("manu_fact_supplier_code >=", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeLessThan(String value) {
            addCriterion("manu_fact_supplier_code <", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("manu_fact_supplier_code <=", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeLike(String value) {
            addCriterion("manu_fact_supplier_code like", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeNotLike(String value) {
            addCriterion("manu_fact_supplier_code not like", value, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeIn(List<String> values) {
            addCriterion("manu_fact_supplier_code in", values, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeNotIn(List<String> values) {
            addCriterion("manu_fact_supplier_code not in", values, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeBetween(String value1, String value2) {
            addCriterion("manu_fact_supplier_code between", value1, value2, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andManuFactSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("manu_fact_supplier_code not between", value1, value2, "manuFactSupplierCode");
            return (Criteria) this;
        }

        public Criteria andVerificationDateIsNull() {
            addCriterion("verification_date is null");
            return (Criteria) this;
        }

        public Criteria andVerificationDateIsNotNull() {
            addCriterion("verification_date is not null");
            return (Criteria) this;
        }

        public Criteria andVerificationDateEqualTo(Date value) {
            addCriterion("verification_date =", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateNotEqualTo(Date value) {
            addCriterion("verification_date <>", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateGreaterThan(Date value) {
            addCriterion("verification_date >", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("verification_date >=", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateLessThan(Date value) {
            addCriterion("verification_date <", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateLessThanOrEqualTo(Date value) {
            addCriterion("verification_date <=", value, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateIn(List<Date> values) {
            addCriterion("verification_date in", values, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateNotIn(List<Date> values) {
            addCriterion("verification_date not in", values, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateBetween(Date value1, Date value2) {
            addCriterion("verification_date between", value1, value2, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andVerificationDateNotBetween(Date value1, Date value2) {
            addCriterion("verification_date not between", value1, value2, "verificationDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateIsNull() {
            addCriterion("manu_dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateIsNotNull() {
            addCriterion("manu_dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateEqualTo(Date value) {
            addCriterion("manu_dlv_date =", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateNotEqualTo(Date value) {
            addCriterion("manu_dlv_date <>", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateGreaterThan(Date value) {
            addCriterion("manu_dlv_date >", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("manu_dlv_date >=", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateLessThan(Date value) {
            addCriterion("manu_dlv_date <", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("manu_dlv_date <=", value, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateIn(List<Date> values) {
            addCriterion("manu_dlv_date in", values, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateNotIn(List<Date> values) {
            addCriterion("manu_dlv_date not in", values, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateBetween(Date value1, Date value2) {
            addCriterion("manu_dlv_date between", value1, value2, "manuDlvDate");
            return (Criteria) this;
        }

        public Criteria andManuDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("manu_dlv_date not between", value1, value2, "manuDlvDate");
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

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Boolean value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Boolean value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Boolean value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Boolean value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Boolean> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Boolean> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagIsNull() {
            addCriterion("can_use_flag is null");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagIsNotNull() {
            addCriterion("can_use_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagEqualTo(Boolean value) {
            addCriterion("can_use_flag =", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagNotEqualTo(Boolean value) {
            addCriterion("can_use_flag <>", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagGreaterThan(Boolean value) {
            addCriterion("can_use_flag >", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("can_use_flag >=", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagLessThan(Boolean value) {
            addCriterion("can_use_flag <", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("can_use_flag <=", value, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagIn(List<Boolean> values) {
            addCriterion("can_use_flag in", values, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagNotIn(List<Boolean> values) {
            addCriterion("can_use_flag not in", values, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("can_use_flag between", value1, value2, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andCanUseFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("can_use_flag not between", value1, value2, "canUseFlag");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andApplyDetailIdIsNull() {
            addCriterion("apply_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdIsNotNull() {
            addCriterion("apply_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdEqualTo(Long value) {
            addCriterion("apply_detail_id =", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdNotEqualTo(Long value) {
            addCriterion("apply_detail_id <>", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdGreaterThan(Long value) {
            addCriterion("apply_detail_id >", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("apply_detail_id >=", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdLessThan(Long value) {
            addCriterion("apply_detail_id <", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("apply_detail_id <=", value, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdIn(List<Long> values) {
            addCriterion("apply_detail_id in", values, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdNotIn(List<Long> values) {
            addCriterion("apply_detail_id not in", values, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdBetween(Long value1, Long value2) {
            addCriterion("apply_detail_id between", value1, value2, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andApplyDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("apply_detail_id not between", value1, value2, "applyDetailId");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeIsNull() {
            addCriterion("supplier_operate_time is null");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeIsNotNull() {
            addCriterion("supplier_operate_time is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeEqualTo(Date value) {
            addCriterion("supplier_operate_time =", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeNotEqualTo(Date value) {
            addCriterion("supplier_operate_time <>", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeGreaterThan(Date value) {
            addCriterion("supplier_operate_time >", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("supplier_operate_time >=", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeLessThan(Date value) {
            addCriterion("supplier_operate_time <", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeLessThanOrEqualTo(Date value) {
            addCriterion("supplier_operate_time <=", value, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeIn(List<Date> values) {
            addCriterion("supplier_operate_time in", values, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeNotIn(List<Date> values) {
            addCriterion("supplier_operate_time not in", values, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeBetween(Date value1, Date value2) {
            addCriterion("supplier_operate_time between", value1, value2, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierOperateTimeNotBetween(Date value1, Date value2) {
            addCriterion("supplier_operate_time not between", value1, value2, "supplierOperateTime");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeIsNull() {
            addCriterion("supplier_expinv_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeIsNotNull() {
            addCriterion("supplier_expinv_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeEqualTo(String value) {
            addCriterion("supplier_expinv_code =", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeNotEqualTo(String value) {
            addCriterion("supplier_expinv_code <>", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeGreaterThan(String value) {
            addCriterion("supplier_expinv_code >", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_expinv_code >=", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeLessThan(String value) {
            addCriterion("supplier_expinv_code <", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_expinv_code <=", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeLike(String value) {
            addCriterion("supplier_expinv_code like", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeNotLike(String value) {
            addCriterion("supplier_expinv_code not like", value, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeIn(List<String> values) {
            addCriterion("supplier_expinv_code in", values, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeNotIn(List<String> values) {
            addCriterion("supplier_expinv_code not in", values, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeBetween(String value1, String value2) {
            addCriterion("supplier_expinv_code between", value1, value2, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_expinv_code not between", value1, value2, "supplierExpinvCode");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkIsNull() {
            addCriterion("supplier_expinv_remark is null");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkIsNotNull() {
            addCriterion("supplier_expinv_remark is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkEqualTo(String value) {
            addCriterion("supplier_expinv_remark =", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkNotEqualTo(String value) {
            addCriterion("supplier_expinv_remark <>", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkGreaterThan(String value) {
            addCriterion("supplier_expinv_remark >", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_expinv_remark >=", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkLessThan(String value) {
            addCriterion("supplier_expinv_remark <", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkLessThanOrEqualTo(String value) {
            addCriterion("supplier_expinv_remark <=", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkLike(String value) {
            addCriterion("supplier_expinv_remark like", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkNotLike(String value) {
            addCriterion("supplier_expinv_remark not like", value, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkIn(List<String> values) {
            addCriterion("supplier_expinv_remark in", values, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkNotIn(List<String> values) {
            addCriterion("supplier_expinv_remark not in", values, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkBetween(String value1, String value2) {
            addCriterion("supplier_expinv_remark between", value1, value2, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andSupplierExpinvRemarkNotBetween(String value1, String value2) {
            addCriterion("supplier_expinv_remark not between", value1, value2, "supplierExpinvRemark");
            return (Criteria) this;
        }

        public Criteria andRemainQtyIsNull() {
            addCriterion("remain_qty is null");
            return (Criteria) this;
        }

        public Criteria andRemainQtyIsNotNull() {
            addCriterion("remain_qty is not null");
            return (Criteria) this;
        }

        public Criteria andRemainQtyEqualTo(Integer value) {
            addCriterion("remain_qty =", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyNotEqualTo(Integer value) {
            addCriterion("remain_qty <>", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyGreaterThan(Integer value) {
            addCriterion("remain_qty >", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("remain_qty >=", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyLessThan(Integer value) {
            addCriterion("remain_qty <", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyLessThanOrEqualTo(Integer value) {
            addCriterion("remain_qty <=", value, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyIn(List<Integer> values) {
            addCriterion("remain_qty in", values, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyNotIn(List<Integer> values) {
            addCriterion("remain_qty not in", values, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyBetween(Integer value1, Integer value2) {
            addCriterion("remain_qty between", value1, value2, "remainQty");
            return (Criteria) this;
        }

        public Criteria andRemainQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("remain_qty not between", value1, value2, "remainQty");
            return (Criteria) this;
        }

        public Criteria andTerminateDateIsNull() {
            addCriterion("terminate_date is null");
            return (Criteria) this;
        }

        public Criteria andTerminateDateIsNotNull() {
            addCriterion("terminate_date is not null");
            return (Criteria) this;
        }

        public Criteria andTerminateDateEqualTo(Date value) {
            addCriterion("terminate_date =", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateNotEqualTo(Date value) {
            addCriterion("terminate_date <>", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateGreaterThan(Date value) {
            addCriterion("terminate_date >", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("terminate_date >=", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateLessThan(Date value) {
            addCriterion("terminate_date <", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateLessThanOrEqualTo(Date value) {
            addCriterion("terminate_date <=", value, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateIn(List<Date> values) {
            addCriterion("terminate_date in", values, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateNotIn(List<Date> values) {
            addCriterion("terminate_date not in", values, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateBetween(Date value1, Date value2) {
            addCriterion("terminate_date between", value1, value2, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andTerminateDateNotBetween(Date value1, Date value2) {
            addCriterion("terminate_date not between", value1, value2, "terminateDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateIsNull() {
            addCriterion("final_account_date is null");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateIsNotNull() {
            addCriterion("final_account_date is not null");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateEqualTo(Date value) {
            addCriterion("final_account_date =", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateNotEqualTo(Date value) {
            addCriterion("final_account_date <>", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateGreaterThan(Date value) {
            addCriterion("final_account_date >", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateGreaterThanOrEqualTo(Date value) {
            addCriterion("final_account_date >=", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateLessThan(Date value) {
            addCriterion("final_account_date <", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateLessThanOrEqualTo(Date value) {
            addCriterion("final_account_date <=", value, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateIn(List<Date> values) {
            addCriterion("final_account_date in", values, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateNotIn(List<Date> values) {
            addCriterion("final_account_date not in", values, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateBetween(Date value1, Date value2) {
            addCriterion("final_account_date between", value1, value2, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andFinalAccountDateNotBetween(Date value1, Date value2) {
            addCriterion("final_account_date not between", value1, value2, "finalAccountDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateIsNull() {
            addCriterion("liquidation_date is null");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateIsNotNull() {
            addCriterion("liquidation_date is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateEqualTo(Date value) {
            addCriterion("liquidation_date =", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateNotEqualTo(Date value) {
            addCriterion("liquidation_date <>", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateGreaterThan(Date value) {
            addCriterion("liquidation_date >", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("liquidation_date >=", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateLessThan(Date value) {
            addCriterion("liquidation_date <", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateLessThanOrEqualTo(Date value) {
            addCriterion("liquidation_date <=", value, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateIn(List<Date> values) {
            addCriterion("liquidation_date in", values, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateNotIn(List<Date> values) {
            addCriterion("liquidation_date not in", values, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateBetween(Date value1, Date value2) {
            addCriterion("liquidation_date between", value1, value2, "liquidationDate");
            return (Criteria) this;
        }

        public Criteria andLiquidationDateNotBetween(Date value1, Date value2) {
            addCriterion("liquidation_date not between", value1, value2, "liquidationDate");
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
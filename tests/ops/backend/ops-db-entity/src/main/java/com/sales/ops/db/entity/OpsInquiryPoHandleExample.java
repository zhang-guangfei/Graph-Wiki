package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsInquiryPoHandleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInquiryPoHandleExample() {
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

        public Criteria andTaskNoIsNull() {
            addCriterion("task_no is null");
            return (Criteria) this;
        }

        public Criteria andTaskNoIsNotNull() {
            addCriterion("task_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNoEqualTo(String value) {
            addCriterion("task_no =", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotEqualTo(String value) {
            addCriterion("task_no <>", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoGreaterThan(String value) {
            addCriterion("task_no >", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoGreaterThanOrEqualTo(String value) {
            addCriterion("task_no >=", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLessThan(String value) {
            addCriterion("task_no <", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLessThanOrEqualTo(String value) {
            addCriterion("task_no <=", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLike(String value) {
            addCriterion("task_no like", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotLike(String value) {
            addCriterion("task_no not like", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoIn(List<String> values) {
            addCriterion("task_no in", values, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotIn(List<String> values) {
            addCriterion("task_no not in", values, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoBetween(String value1, String value2) {
            addCriterion("task_no between", value1, value2, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotBetween(String value1, String value2) {
            addCriterion("task_no not between", value1, value2, "taskNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoIsNull() {
            addCriterion("inquiry_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoIsNotNull() {
            addCriterion("inquiry_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoEqualTo(String value) {
            addCriterion("inquiry_apply_no =", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoNotEqualTo(String value) {
            addCriterion("inquiry_apply_no <>", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoGreaterThan(String value) {
            addCriterion("inquiry_apply_no >", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_apply_no >=", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoLessThan(String value) {
            addCriterion("inquiry_apply_no <", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoLessThanOrEqualTo(String value) {
            addCriterion("inquiry_apply_no <=", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoLike(String value) {
            addCriterion("inquiry_apply_no like", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoNotLike(String value) {
            addCriterion("inquiry_apply_no not like", value, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoIn(List<String> values) {
            addCriterion("inquiry_apply_no in", values, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoNotIn(List<String> values) {
            addCriterion("inquiry_apply_no not in", values, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoBetween(String value1, String value2) {
            addCriterion("inquiry_apply_no between", value1, value2, "inquiryApplyNo");
            return (Criteria) this;
        }

        public Criteria andInquiryApplyNoNotBetween(String value1, String value2) {
            addCriterion("inquiry_apply_no not between", value1, value2, "inquiryApplyNo");
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

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
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

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoIsNull() {
            addCriterion("purchase_no is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoIsNotNull() {
            addCriterion("purchase_no is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoEqualTo(String value) {
            addCriterion("purchase_no =", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoNotEqualTo(String value) {
            addCriterion("purchase_no <>", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoGreaterThan(String value) {
            addCriterion("purchase_no >", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_no >=", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoLessThan(String value) {
            addCriterion("purchase_no <", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoLessThanOrEqualTo(String value) {
            addCriterion("purchase_no <=", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoLike(String value) {
            addCriterion("purchase_no like", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoNotLike(String value) {
            addCriterion("purchase_no not like", value, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoIn(List<String> values) {
            addCriterion("purchase_no in", values, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoNotIn(List<String> values) {
            addCriterion("purchase_no not in", values, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoBetween(String value1, String value2) {
            addCriterion("purchase_no between", value1, value2, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andPurchaseNoNotBetween(String value1, String value2) {
            addCriterion("purchase_no not between", value1, value2, "purchaseNo");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("supplier_id like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("supplier_id not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusIsNull() {
            addCriterion("inquiry_status is null");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusIsNotNull() {
            addCriterion("inquiry_status is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusEqualTo(Integer value) {
            addCriterion("inquiry_status =", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusNotEqualTo(Integer value) {
            addCriterion("inquiry_status <>", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusGreaterThan(Integer value) {
            addCriterion("inquiry_status >", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("inquiry_status >=", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusLessThan(Integer value) {
            addCriterion("inquiry_status <", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusLessThanOrEqualTo(Integer value) {
            addCriterion("inquiry_status <=", value, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusIn(List<Integer> values) {
            addCriterion("inquiry_status in", values, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusNotIn(List<Integer> values) {
            addCriterion("inquiry_status not in", values, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusBetween(Integer value1, Integer value2) {
            addCriterion("inquiry_status between", value1, value2, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("inquiry_status not between", value1, value2, "inquiryStatus");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultIsNull() {
            addCriterion("inquiry_handle_result is null");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultIsNotNull() {
            addCriterion("inquiry_handle_result is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultEqualTo(String value) {
            addCriterion("inquiry_handle_result =", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultNotEqualTo(String value) {
            addCriterion("inquiry_handle_result <>", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultGreaterThan(String value) {
            addCriterion("inquiry_handle_result >", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_handle_result >=", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultLessThan(String value) {
            addCriterion("inquiry_handle_result <", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultLessThanOrEqualTo(String value) {
            addCriterion("inquiry_handle_result <=", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultLike(String value) {
            addCriterion("inquiry_handle_result like", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultNotLike(String value) {
            addCriterion("inquiry_handle_result not like", value, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultIn(List<String> values) {
            addCriterion("inquiry_handle_result in", values, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultNotIn(List<String> values) {
            addCriterion("inquiry_handle_result not in", values, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultBetween(String value1, String value2) {
            addCriterion("inquiry_handle_result between", value1, value2, "inquiryHandleResult");
            return (Criteria) this;
        }

        public Criteria andInquiryHandleResultNotBetween(String value1, String value2) {
            addCriterion("inquiry_handle_result not between", value1, value2, "inquiryHandleResult");
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
            addCriterionForJDBCDate("hope_export_date =", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hope_export_date <>", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hope_export_date >", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_export_date >=", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateLessThan(Date value) {
            addCriterionForJDBCDate("hope_export_date <", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_export_date <=", value, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateIn(List<Date> values) {
            addCriterionForJDBCDate("hope_export_date in", values, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hope_export_date not in", values, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_export_date between", value1, value2, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andHopeExportDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_export_date not between", value1, value2, "hopeExportDate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateIsNull() {
            addCriterion("dlv_moddate is null");
            return (Criteria) this;
        }

        public Criteria andDlvModdateIsNotNull() {
            addCriterion("dlv_moddate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvModdateEqualTo(Date value) {
            addCriterionForJDBCDate("dlv_moddate =", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("dlv_moddate <>", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateGreaterThan(Date value) {
            addCriterionForJDBCDate("dlv_moddate >", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dlv_moddate >=", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateLessThan(Date value) {
            addCriterionForJDBCDate("dlv_moddate <", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dlv_moddate <=", value, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateIn(List<Date> values) {
            addCriterionForJDBCDate("dlv_moddate in", values, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("dlv_moddate not in", values, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dlv_moddate between", value1, value2, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andDlvModdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dlv_moddate not between", value1, value2, "dlvModdate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateIsNull() {
            addCriterion("hope_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateIsNotNull() {
            addCriterion("hope_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateEqualTo(Date value) {
            addCriterionForJDBCDate("hope_delivery_date =", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hope_delivery_date <>", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hope_delivery_date >", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_delivery_date >=", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateLessThan(Date value) {
            addCriterionForJDBCDate("hope_delivery_date <", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_delivery_date <=", value, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateIn(List<Date> values) {
            addCriterionForJDBCDate("hope_delivery_date in", values, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hope_delivery_date not in", values, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_delivery_date between", value1, value2, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andHopeDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_delivery_date not between", value1, value2, "hopeDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeIsNull() {
            addCriterion("inquiry_reason_type is null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeIsNotNull() {
            addCriterion("inquiry_reason_type is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeEqualTo(String value) {
            addCriterion("inquiry_reason_type =", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeNotEqualTo(String value) {
            addCriterion("inquiry_reason_type <>", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeGreaterThan(String value) {
            addCriterion("inquiry_reason_type >", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_reason_type >=", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeLessThan(String value) {
            addCriterion("inquiry_reason_type <", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeLessThanOrEqualTo(String value) {
            addCriterion("inquiry_reason_type <=", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeLike(String value) {
            addCriterion("inquiry_reason_type like", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeNotLike(String value) {
            addCriterion("inquiry_reason_type not like", value, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeIn(List<String> values) {
            addCriterion("inquiry_reason_type in", values, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeNotIn(List<String> values) {
            addCriterion("inquiry_reason_type not in", values, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeBetween(String value1, String value2) {
            addCriterion("inquiry_reason_type between", value1, value2, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonTypeNotBetween(String value1, String value2) {
            addCriterion("inquiry_reason_type not between", value1, value2, "inquiryReasonType");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonIsNull() {
            addCriterion("inquiry_reason is null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonIsNotNull() {
            addCriterion("inquiry_reason is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonEqualTo(String value) {
            addCriterion("inquiry_reason =", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonNotEqualTo(String value) {
            addCriterion("inquiry_reason <>", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonGreaterThan(String value) {
            addCriterion("inquiry_reason >", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_reason >=", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonLessThan(String value) {
            addCriterion("inquiry_reason <", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonLessThanOrEqualTo(String value) {
            addCriterion("inquiry_reason <=", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonLike(String value) {
            addCriterion("inquiry_reason like", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonNotLike(String value) {
            addCriterion("inquiry_reason not like", value, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonIn(List<String> values) {
            addCriterion("inquiry_reason in", values, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonNotIn(List<String> values) {
            addCriterion("inquiry_reason not in", values, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonBetween(String value1, String value2) {
            addCriterion("inquiry_reason between", value1, value2, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonNotBetween(String value1, String value2) {
            addCriterion("inquiry_reason not between", value1, value2, "inquiryReason");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionIsNull() {
            addCriterion("inquiry_description is null");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionIsNotNull() {
            addCriterion("inquiry_description is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionEqualTo(String value) {
            addCriterion("inquiry_description =", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionNotEqualTo(String value) {
            addCriterion("inquiry_description <>", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionGreaterThan(String value) {
            addCriterion("inquiry_description >", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_description >=", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionLessThan(String value) {
            addCriterion("inquiry_description <", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionLessThanOrEqualTo(String value) {
            addCriterion("inquiry_description <=", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionLike(String value) {
            addCriterion("inquiry_description like", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionNotLike(String value) {
            addCriterion("inquiry_description not like", value, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionIn(List<String> values) {
            addCriterion("inquiry_description in", values, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionNotIn(List<String> values) {
            addCriterion("inquiry_description not in", values, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionBetween(String value1, String value2) {
            addCriterion("inquiry_description between", value1, value2, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryDescriptionNotBetween(String value1, String value2) {
            addCriterion("inquiry_description not between", value1, value2, "inquiryDescription");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkIsNull() {
            addCriterion("inquiry_remark is null");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkIsNotNull() {
            addCriterion("inquiry_remark is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkEqualTo(String value) {
            addCriterion("inquiry_remark =", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkNotEqualTo(String value) {
            addCriterion("inquiry_remark <>", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkGreaterThan(String value) {
            addCriterion("inquiry_remark >", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_remark >=", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkLessThan(String value) {
            addCriterion("inquiry_remark <", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkLessThanOrEqualTo(String value) {
            addCriterion("inquiry_remark <=", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkLike(String value) {
            addCriterion("inquiry_remark like", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkNotLike(String value) {
            addCriterion("inquiry_remark not like", value, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkIn(List<String> values) {
            addCriterion("inquiry_remark in", values, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkNotIn(List<String> values) {
            addCriterion("inquiry_remark not in", values, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkBetween(String value1, String value2) {
            addCriterion("inquiry_remark between", value1, value2, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryRemarkNotBetween(String value1, String value2) {
            addCriterion("inquiry_remark not between", value1, value2, "inquiryRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeIsNull() {
            addCriterion("inquiry_type is null");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeIsNotNull() {
            addCriterion("inquiry_type is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeEqualTo(String value) {
            addCriterion("inquiry_type =", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeNotEqualTo(String value) {
            addCriterion("inquiry_type <>", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeGreaterThan(String value) {
            addCriterion("inquiry_type >", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_type >=", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeLessThan(String value) {
            addCriterion("inquiry_type <", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeLessThanOrEqualTo(String value) {
            addCriterion("inquiry_type <=", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeLike(String value) {
            addCriterion("inquiry_type like", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeNotLike(String value) {
            addCriterion("inquiry_type not like", value, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeIn(List<String> values) {
            addCriterion("inquiry_type in", values, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeNotIn(List<String> values) {
            addCriterion("inquiry_type not in", values, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeBetween(String value1, String value2) {
            addCriterion("inquiry_type between", value1, value2, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTypeNotBetween(String value1, String value2) {
            addCriterion("inquiry_type not between", value1, value2, "inquiryType");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeIsNull() {
            addCriterion("inquiry_time is null");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeIsNotNull() {
            addCriterion("inquiry_time is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeEqualTo(Date value) {
            addCriterion("inquiry_time =", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeNotEqualTo(Date value) {
            addCriterion("inquiry_time <>", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeGreaterThan(Date value) {
            addCriterion("inquiry_time >", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inquiry_time >=", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeLessThan(Date value) {
            addCriterion("inquiry_time <", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeLessThanOrEqualTo(Date value) {
            addCriterion("inquiry_time <=", value, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeIn(List<Date> values) {
            addCriterion("inquiry_time in", values, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeNotIn(List<Date> values) {
            addCriterion("inquiry_time not in", values, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeBetween(Date value1, Date value2) {
            addCriterion("inquiry_time between", value1, value2, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryTimeNotBetween(Date value1, Date value2) {
            addCriterion("inquiry_time not between", value1, value2, "inquiryTime");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptIsNull() {
            addCriterion("inquiry_dept is null");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptIsNotNull() {
            addCriterion("inquiry_dept is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptEqualTo(String value) {
            addCriterion("inquiry_dept =", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptNotEqualTo(String value) {
            addCriterion("inquiry_dept <>", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptGreaterThan(String value) {
            addCriterion("inquiry_dept >", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_dept >=", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptLessThan(String value) {
            addCriterion("inquiry_dept <", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptLessThanOrEqualTo(String value) {
            addCriterion("inquiry_dept <=", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptLike(String value) {
            addCriterion("inquiry_dept like", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptNotLike(String value) {
            addCriterion("inquiry_dept not like", value, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptIn(List<String> values) {
            addCriterion("inquiry_dept in", values, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptNotIn(List<String> values) {
            addCriterion("inquiry_dept not in", values, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptBetween(String value1, String value2) {
            addCriterion("inquiry_dept between", value1, value2, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryDeptNotBetween(String value1, String value2) {
            addCriterion("inquiry_dept not between", value1, value2, "inquiryDept");
            return (Criteria) this;
        }

        public Criteria andInquiryUserIsNull() {
            addCriterion("inquiry_user is null");
            return (Criteria) this;
        }

        public Criteria andInquiryUserIsNotNull() {
            addCriterion("inquiry_user is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryUserEqualTo(String value) {
            addCriterion("inquiry_user =", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNotEqualTo(String value) {
            addCriterion("inquiry_user <>", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserGreaterThan(String value) {
            addCriterion("inquiry_user >", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_user >=", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserLessThan(String value) {
            addCriterion("inquiry_user <", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserLessThanOrEqualTo(String value) {
            addCriterion("inquiry_user <=", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserLike(String value) {
            addCriterion("inquiry_user like", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNotLike(String value) {
            addCriterion("inquiry_user not like", value, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserIn(List<String> values) {
            addCriterion("inquiry_user in", values, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNotIn(List<String> values) {
            addCriterion("inquiry_user not in", values, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserBetween(String value1, String value2) {
            addCriterion("inquiry_user between", value1, value2, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNotBetween(String value1, String value2) {
            addCriterion("inquiry_user not between", value1, value2, "inquiryUser");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameIsNull() {
            addCriterion("inquiry_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameIsNotNull() {
            addCriterion("inquiry_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameEqualTo(String value) {
            addCriterion("inquiry_user_name =", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameNotEqualTo(String value) {
            addCriterion("inquiry_user_name <>", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameGreaterThan(String value) {
            addCriterion("inquiry_user_name >", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_user_name >=", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameLessThan(String value) {
            addCriterion("inquiry_user_name <", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameLessThanOrEqualTo(String value) {
            addCriterion("inquiry_user_name <=", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameLike(String value) {
            addCriterion("inquiry_user_name like", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameNotLike(String value) {
            addCriterion("inquiry_user_name not like", value, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameIn(List<String> values) {
            addCriterion("inquiry_user_name in", values, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameNotIn(List<String> values) {
            addCriterion("inquiry_user_name not in", values, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameBetween(String value1, String value2) {
            addCriterion("inquiry_user_name between", value1, value2, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andInquiryUserNameNotBetween(String value1, String value2) {
            addCriterion("inquiry_user_name not between", value1, value2, "inquiryUserName");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNull() {
            addCriterion("reply_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNotNull() {
            addCriterion("reply_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptEqualTo(String value) {
            addCriterion("reply_dept =", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotEqualTo(String value) {
            addCriterion("reply_dept <>", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThan(String value) {
            addCriterion("reply_dept >", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_dept >=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThan(String value) {
            addCriterion("reply_dept <", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_dept <=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLike(String value) {
            addCriterion("reply_dept like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotLike(String value) {
            addCriterion("reply_dept not like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIn(List<String> values) {
            addCriterion("reply_dept in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotIn(List<String> values) {
            addCriterion("reply_dept not in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptBetween(String value1, String value2) {
            addCriterion("reply_dept between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotBetween(String value1, String value2) {
            addCriterion("reply_dept not between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIsNull() {
            addCriterion("supplier_order_no is null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIsNotNull() {
            addCriterion("supplier_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoEqualTo(String value) {
            addCriterion("supplier_order_no =", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotEqualTo(String value) {
            addCriterion("supplier_order_no <>", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoGreaterThan(String value) {
            addCriterion("supplier_order_no >", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_order_no >=", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLessThan(String value) {
            addCriterion("supplier_order_no <", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLessThanOrEqualTo(String value) {
            addCriterion("supplier_order_no <=", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoLike(String value) {
            addCriterion("supplier_order_no like", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotLike(String value) {
            addCriterion("supplier_order_no not like", value, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoIn(List<String> values) {
            addCriterion("supplier_order_no in", values, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotIn(List<String> values) {
            addCriterion("supplier_order_no not in", values, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoBetween(String value1, String value2) {
            addCriterion("supplier_order_no between", value1, value2, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andSupplierOrderNoNotBetween(String value1, String value2) {
            addCriterion("supplier_order_no not between", value1, value2, "supplierOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNull() {
            addCriterion("reply_no is null");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNotNull() {
            addCriterion("reply_no is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNoEqualTo(String value) {
            addCriterion("reply_no =", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotEqualTo(String value) {
            addCriterion("reply_no <>", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThan(String value) {
            addCriterion("reply_no >", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("reply_no >=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThan(String value) {
            addCriterion("reply_no <", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThanOrEqualTo(String value) {
            addCriterion("reply_no <=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLike(String value) {
            addCriterion("reply_no like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotLike(String value) {
            addCriterion("reply_no not like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoIn(List<String> values) {
            addCriterion("reply_no in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotIn(List<String> values) {
            addCriterion("reply_no not in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoBetween(String value1, String value2) {
            addCriterion("reply_no between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotBetween(String value1, String value2) {
            addCriterion("reply_no not between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateIsNull() {
            addCriterion("reply_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateIsNotNull() {
            addCriterion("reply_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateEqualTo(Date value) {
            addCriterion("reply_delivery_date =", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateNotEqualTo(Date value) {
            addCriterion("reply_delivery_date <>", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateGreaterThan(Date value) {
            addCriterion("reply_delivery_date >", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_delivery_date >=", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateLessThan(Date value) {
            addCriterion("reply_delivery_date <", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("reply_delivery_date <=", value, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateIn(List<Date> values) {
            addCriterion("reply_delivery_date in", values, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateNotIn(List<Date> values) {
            addCriterion("reply_delivery_date not in", values, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("reply_delivery_date between", value1, value2, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("reply_delivery_date not between", value1, value2, "replyDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNull() {
            addCriterion("reply_user is null");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNotNull() {
            addCriterion("reply_user is not null");
            return (Criteria) this;
        }

        public Criteria andReplyUserEqualTo(String value) {
            addCriterion("reply_user =", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotEqualTo(String value) {
            addCriterion("reply_user <>", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThan(String value) {
            addCriterion("reply_user >", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("reply_user >=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThan(String value) {
            addCriterion("reply_user <", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThanOrEqualTo(String value) {
            addCriterion("reply_user <=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLike(String value) {
            addCriterion("reply_user like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotLike(String value) {
            addCriterion("reply_user not like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserIn(List<String> values) {
            addCriterion("reply_user in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotIn(List<String> values) {
            addCriterion("reply_user not in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserBetween(String value1, String value2) {
            addCriterion("reply_user between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotBetween(String value1, String value2) {
            addCriterion("reply_user not between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNull() {
            addCriterion("reply_time is null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNotNull() {
            addCriterion("reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeEqualTo(Date value) {
            addCriterion("reply_time =", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotEqualTo(Date value) {
            addCriterion("reply_time <>", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThan(Date value) {
            addCriterion("reply_time >", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_time >=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThan(Date value) {
            addCriterion("reply_time <", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("reply_time <=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIn(List<Date> values) {
            addCriterion("reply_time in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotIn(List<Date> values) {
            addCriterion("reply_time not in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeBetween(Date value1, Date value2) {
            addCriterion("reply_time between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("reply_time not between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonIsNull() {
            addCriterion("reply_delay_reason is null");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonIsNotNull() {
            addCriterion("reply_delay_reason is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonEqualTo(String value) {
            addCriterion("reply_delay_reason =", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonNotEqualTo(String value) {
            addCriterion("reply_delay_reason <>", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonGreaterThan(String value) {
            addCriterion("reply_delay_reason >", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reply_delay_reason >=", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonLessThan(String value) {
            addCriterion("reply_delay_reason <", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonLessThanOrEqualTo(String value) {
            addCriterion("reply_delay_reason <=", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonLike(String value) {
            addCriterion("reply_delay_reason like", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonNotLike(String value) {
            addCriterion("reply_delay_reason not like", value, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonIn(List<String> values) {
            addCriterion("reply_delay_reason in", values, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonNotIn(List<String> values) {
            addCriterion("reply_delay_reason not in", values, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonBetween(String value1, String value2) {
            addCriterion("reply_delay_reason between", value1, value2, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyDelayReasonNotBetween(String value1, String value2) {
            addCriterion("reply_delay_reason not between", value1, value2, "replyDelayReason");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNull() {
            addCriterion("reply_remark is null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNotNull() {
            addCriterion("reply_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkEqualTo(String value) {
            addCriterion("reply_remark =", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotEqualTo(String value) {
            addCriterion("reply_remark <>", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThan(String value) {
            addCriterion("reply_remark >", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reply_remark >=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThan(String value) {
            addCriterion("reply_remark <", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThanOrEqualTo(String value) {
            addCriterion("reply_remark <=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLike(String value) {
            addCriterion("reply_remark like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotLike(String value) {
            addCriterion("reply_remark not like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIn(List<String> values) {
            addCriterion("reply_remark in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotIn(List<String> values) {
            addCriterion("reply_remark not in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkBetween(String value1, String value2) {
            addCriterion("reply_remark between", value1, value2, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotBetween(String value1, String value2) {
            addCriterion("reply_remark not between", value1, value2, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelIsNull() {
            addCriterion("inquiry_level is null");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelIsNotNull() {
            addCriterion("inquiry_level is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelEqualTo(String value) {
            addCriterion("inquiry_level =", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelNotEqualTo(String value) {
            addCriterion("inquiry_level <>", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelGreaterThan(String value) {
            addCriterion("inquiry_level >", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_level >=", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelLessThan(String value) {
            addCriterion("inquiry_level <", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelLessThanOrEqualTo(String value) {
            addCriterion("inquiry_level <=", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelLike(String value) {
            addCriterion("inquiry_level like", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelNotLike(String value) {
            addCriterion("inquiry_level not like", value, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelIn(List<String> values) {
            addCriterion("inquiry_level in", values, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelNotIn(List<String> values) {
            addCriterion("inquiry_level not in", values, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelBetween(String value1, String value2) {
            addCriterion("inquiry_level between", value1, value2, "inquiryLevel");
            return (Criteria) this;
        }

        public Criteria andInquiryLevelNotBetween(String value1, String value2) {
            addCriterion("inquiry_level not between", value1, value2, "inquiryLevel");
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

        public Criteria andReplySupplierDeptIsNull() {
            addCriterion("reply_supplier_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIsNotNull() {
            addCriterion("reply_supplier_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptEqualTo(String value) {
            addCriterion("reply_supplier_dept =", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotEqualTo(String value) {
            addCriterion("reply_supplier_dept <>", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThan(String value) {
            addCriterion("reply_supplier_dept >", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept >=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThan(String value) {
            addCriterion("reply_supplier_dept <", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept <=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLike(String value) {
            addCriterion("reply_supplier_dept like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotLike(String value) {
            addCriterion("reply_supplier_dept not like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIn(List<String> values) {
            addCriterion("reply_supplier_dept in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotIn(List<String> values) {
            addCriterion("reply_supplier_dept not in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept not between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andPonoIsNull() {
            addCriterion("poNo is null");
            return (Criteria) this;
        }

        public Criteria andPonoIsNotNull() {
            addCriterion("poNo is not null");
            return (Criteria) this;
        }

        public Criteria andPonoEqualTo(String value) {
            addCriterion("poNo =", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotEqualTo(String value) {
            addCriterion("poNo <>", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThan(String value) {
            addCriterion("poNo >", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThanOrEqualTo(String value) {
            addCriterion("poNo >=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThan(String value) {
            addCriterion("poNo <", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThanOrEqualTo(String value) {
            addCriterion("poNo <=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLike(String value) {
            addCriterion("poNo like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotLike(String value) {
            addCriterion("poNo not like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoIn(List<String> values) {
            addCriterion("poNo in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotIn(List<String> values) {
            addCriterion("poNo not in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoBetween(String value1, String value2) {
            addCriterion("poNo between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotBetween(String value1, String value2) {
            addCriterion("poNo not between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andLineitemIsNull() {
            addCriterion("lineItem is null");
            return (Criteria) this;
        }

        public Criteria andLineitemIsNotNull() {
            addCriterion("lineItem is not null");
            return (Criteria) this;
        }

        public Criteria andLineitemEqualTo(Integer value) {
            addCriterion("lineItem =", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotEqualTo(Integer value) {
            addCriterion("lineItem <>", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThan(Integer value) {
            addCriterion("lineItem >", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("lineItem >=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThan(Integer value) {
            addCriterion("lineItem <", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThanOrEqualTo(Integer value) {
            addCriterion("lineItem <=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemIn(List<Integer> values) {
            addCriterion("lineItem in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotIn(List<Integer> values) {
            addCriterion("lineItem not in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemBetween(Integer value1, Integer value2) {
            addCriterion("lineItem between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotBetween(Integer value1, Integer value2) {
            addCriterion("lineItem not between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNull() {
            addCriterion("rorder_no is null");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNotNull() {
            addCriterion("rorder_no is not null");
            return (Criteria) this;
        }

        public Criteria andRorderNoEqualTo(String value) {
            addCriterion("rorder_no =", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotEqualTo(String value) {
            addCriterion("rorder_no <>", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThan(String value) {
            addCriterion("rorder_no >", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_no >=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThan(String value) {
            addCriterion("rorder_no <", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThanOrEqualTo(String value) {
            addCriterion("rorder_no <=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLike(String value) {
            addCriterion("rorder_no like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotLike(String value) {
            addCriterion("rorder_no not like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoIn(List<String> values) {
            addCriterion("rorder_no in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotIn(List<String> values) {
            addCriterion("rorder_no not in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoBetween(String value1, String value2) {
            addCriterion("rorder_no between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotBetween(String value1, String value2) {
            addCriterion("rorder_no not between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNull() {
            addCriterion("rorder_item is null");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNotNull() {
            addCriterion("rorder_item is not null");
            return (Criteria) this;
        }

        public Criteria andRorderItemEqualTo(Integer value) {
            addCriterion("rorder_item =", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotEqualTo(Integer value) {
            addCriterion("rorder_item <>", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThan(Integer value) {
            addCriterion("rorder_item >", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("rorder_item >=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThan(Integer value) {
            addCriterion("rorder_item <", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThanOrEqualTo(Integer value) {
            addCriterion("rorder_item <=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemIn(List<Integer> values) {
            addCriterion("rorder_item in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotIn(List<Integer> values) {
            addCriterion("rorder_item not in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item not between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNull() {
            addCriterion("splitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNotNull() {
            addCriterion("splitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoEqualTo(Integer value) {
            addCriterion("splitItemNo =", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotEqualTo(Integer value) {
            addCriterion("splitItemNo <>", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThan(Integer value) {
            addCriterion("splitItemNo >", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo >=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThan(Integer value) {
            addCriterion("splitItemNo <", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo <=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIn(List<Integer> values) {
            addCriterion("splitItemNo in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotIn(List<Integer> values) {
            addCriterion("splitItemNo not in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo not between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("smcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("smcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("smcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("smcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("smcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("smcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("smcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("smcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("smcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("smcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("smcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("smcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("smcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("smcCode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNull() {
            addCriterion("purchaseType is null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNotNull() {
            addCriterion("purchaseType is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeEqualTo(String value) {
            addCriterion("purchaseType =", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotEqualTo(String value) {
            addCriterion("purchaseType <>", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThan(String value) {
            addCriterion("purchaseType >", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseType >=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThan(String value) {
            addCriterion("purchaseType <", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThanOrEqualTo(String value) {
            addCriterion("purchaseType <=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLike(String value) {
            addCriterion("purchaseType like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotLike(String value) {
            addCriterion("purchaseType not like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIn(List<String> values) {
            addCriterion("purchaseType in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotIn(List<String> values) {
            addCriterion("purchaseType not in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeBetween(String value1, String value2) {
            addCriterion("purchaseType between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotBetween(String value1, String value2) {
            addCriterion("purchaseType not between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamIsNull() {
            addCriterion("inquiry_reason_param is null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamIsNotNull() {
            addCriterion("inquiry_reason_param is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamEqualTo(String value) {
            addCriterion("inquiry_reason_param =", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamNotEqualTo(String value) {
            addCriterion("inquiry_reason_param <>", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamGreaterThan(String value) {
            addCriterion("inquiry_reason_param >", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_reason_param >=", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamLessThan(String value) {
            addCriterion("inquiry_reason_param <", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamLessThanOrEqualTo(String value) {
            addCriterion("inquiry_reason_param <=", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamLike(String value) {
            addCriterion("inquiry_reason_param like", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamNotLike(String value) {
            addCriterion("inquiry_reason_param not like", value, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamIn(List<String> values) {
            addCriterion("inquiry_reason_param in", values, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamNotIn(List<String> values) {
            addCriterion("inquiry_reason_param not in", values, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamBetween(String value1, String value2) {
            addCriterion("inquiry_reason_param between", value1, value2, "inquiryReasonParam");
            return (Criteria) this;
        }

        public Criteria andInquiryReasonParamNotBetween(String value1, String value2) {
            addCriterion("inquiry_reason_param not between", value1, value2, "inquiryReasonParam");
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

        public Criteria andReplyDeliveryDateSrcIsNull() {
            addCriterion("reply_delivery_date_src is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcIsNotNull() {
            addCriterion("reply_delivery_date_src is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcEqualTo(String value) {
            addCriterion("reply_delivery_date_src =", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcNotEqualTo(String value) {
            addCriterion("reply_delivery_date_src <>", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcGreaterThan(String value) {
            addCriterion("reply_delivery_date_src >", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcGreaterThanOrEqualTo(String value) {
            addCriterion("reply_delivery_date_src >=", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcLessThan(String value) {
            addCriterion("reply_delivery_date_src <", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcLessThanOrEqualTo(String value) {
            addCriterion("reply_delivery_date_src <=", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcLike(String value) {
            addCriterion("reply_delivery_date_src like", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcNotLike(String value) {
            addCriterion("reply_delivery_date_src not like", value, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcIn(List<String> values) {
            addCriterion("reply_delivery_date_src in", values, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcNotIn(List<String> values) {
            addCriterion("reply_delivery_date_src not in", values, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcBetween(String value1, String value2) {
            addCriterion("reply_delivery_date_src between", value1, value2, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDateSrcNotBetween(String value1, String value2) {
            addCriterion("reply_delivery_date_src not between", value1, value2, "replyDeliveryDateSrc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescIsNull() {
            addCriterion("reply_result_desc is null");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescIsNotNull() {
            addCriterion("reply_result_desc is not null");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescEqualTo(String value) {
            addCriterion("reply_result_desc =", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescNotEqualTo(String value) {
            addCriterion("reply_result_desc <>", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescGreaterThan(String value) {
            addCriterion("reply_result_desc >", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescGreaterThanOrEqualTo(String value) {
            addCriterion("reply_result_desc >=", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescLessThan(String value) {
            addCriterion("reply_result_desc <", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescLessThanOrEqualTo(String value) {
            addCriterion("reply_result_desc <=", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescLike(String value) {
            addCriterion("reply_result_desc like", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescNotLike(String value) {
            addCriterion("reply_result_desc not like", value, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescIn(List<String> values) {
            addCriterion("reply_result_desc in", values, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescNotIn(List<String> values) {
            addCriterion("reply_result_desc not in", values, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescBetween(String value1, String value2) {
            addCriterion("reply_result_desc between", value1, value2, "replyResultDesc");
            return (Criteria) this;
        }

        public Criteria andReplyResultDescNotBetween(String value1, String value2) {
            addCriterion("reply_result_desc not between", value1, value2, "replyResultDesc");
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
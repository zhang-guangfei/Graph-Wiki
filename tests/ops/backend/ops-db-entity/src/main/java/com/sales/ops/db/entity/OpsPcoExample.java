package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsPcoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPcoExample() {
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

        public Criteria andPcoIdIsNull() {
            addCriterion("pco_id is null");
            return (Criteria) this;
        }

        public Criteria andPcoIdIsNotNull() {
            addCriterion("pco_id is not null");
            return (Criteria) this;
        }

        public Criteria andPcoIdEqualTo(String value) {
            addCriterion("pco_id =", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotEqualTo(String value) {
            addCriterion("pco_id <>", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThan(String value) {
            addCriterion("pco_id >", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThanOrEqualTo(String value) {
            addCriterion("pco_id >=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThan(String value) {
            addCriterion("pco_id <", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThanOrEqualTo(String value) {
            addCriterion("pco_id <=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLike(String value) {
            addCriterion("pco_id like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotLike(String value) {
            addCriterion("pco_id not like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdIn(List<String> values) {
            addCriterion("pco_id in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotIn(List<String> values) {
            addCriterion("pco_id not in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdBetween(String value1, String value2) {
            addCriterion("pco_id between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotBetween(String value1, String value2) {
            addCriterion("pco_id not between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNull() {
            addCriterion("order_item is null");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNotNull() {
            addCriterion("order_item is not null");
            return (Criteria) this;
        }

        public Criteria andOrderItemEqualTo(String value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(String value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(String value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(String value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(String value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLike(String value) {
            addCriterion("order_item like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotLike(String value) {
            addCriterion("order_item not like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<String> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<String> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(String value1, String value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(String value1, String value2) {
            addCriterion("order_item not between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andAssNumIsNull() {
            addCriterion("ass_num is null");
            return (Criteria) this;
        }

        public Criteria andAssNumIsNotNull() {
            addCriterion("ass_num is not null");
            return (Criteria) this;
        }

        public Criteria andAssNumEqualTo(Integer value) {
            addCriterion("ass_num =", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotEqualTo(Integer value) {
            addCriterion("ass_num <>", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumGreaterThan(Integer value) {
            addCriterion("ass_num >", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ass_num >=", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumLessThan(Integer value) {
            addCriterion("ass_num <", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumLessThanOrEqualTo(Integer value) {
            addCriterion("ass_num <=", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumIn(List<Integer> values) {
            addCriterion("ass_num in", values, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotIn(List<Integer> values) {
            addCriterion("ass_num not in", values, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumBetween(Integer value1, Integer value2) {
            addCriterion("ass_num between", value1, value2, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ass_num not between", value1, value2, "assNum");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andPcoSourceIsNull() {
            addCriterion("pco_source is null");
            return (Criteria) this;
        }

        public Criteria andPcoSourceIsNotNull() {
            addCriterion("pco_source is not null");
            return (Criteria) this;
        }

        public Criteria andPcoSourceEqualTo(Integer value) {
            addCriterion("pco_source =", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceNotEqualTo(Integer value) {
            addCriterion("pco_source <>", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceGreaterThan(Integer value) {
            addCriterion("pco_source >", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_source >=", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceLessThan(Integer value) {
            addCriterion("pco_source <", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceLessThanOrEqualTo(Integer value) {
            addCriterion("pco_source <=", value, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceIn(List<Integer> values) {
            addCriterion("pco_source in", values, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceNotIn(List<Integer> values) {
            addCriterion("pco_source not in", values, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceBetween(Integer value1, Integer value2) {
            addCriterion("pco_source between", value1, value2, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_source not between", value1, value2, "pcoSource");
            return (Criteria) this;
        }

        public Criteria andPcoTypeIsNull() {
            addCriterion("pco_type is null");
            return (Criteria) this;
        }

        public Criteria andPcoTypeIsNotNull() {
            addCriterion("pco_type is not null");
            return (Criteria) this;
        }

        public Criteria andPcoTypeEqualTo(Integer value) {
            addCriterion("pco_type =", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeNotEqualTo(Integer value) {
            addCriterion("pco_type <>", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeGreaterThan(Integer value) {
            addCriterion("pco_type >", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_type >=", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeLessThan(Integer value) {
            addCriterion("pco_type <", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pco_type <=", value, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeIn(List<Integer> values) {
            addCriterion("pco_type in", values, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeNotIn(List<Integer> values) {
            addCriterion("pco_type not in", values, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeBetween(Integer value1, Integer value2) {
            addCriterion("pco_type between", value1, value2, "pcoType");
            return (Criteria) this;
        }

        public Criteria andPcoTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_type not between", value1, value2, "pcoType");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNull() {
            addCriterion("prod_type is null");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNotNull() {
            addCriterion("prod_type is not null");
            return (Criteria) this;
        }

        public Criteria andProdTypeEqualTo(String value) {
            addCriterion("prod_type =", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotEqualTo(String value) {
            addCriterion("prod_type <>", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThan(String value) {
            addCriterion("prod_type >", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("prod_type >=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThan(String value) {
            addCriterion("prod_type <", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThanOrEqualTo(String value) {
            addCriterion("prod_type <=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLike(String value) {
            addCriterion("prod_type like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotLike(String value) {
            addCriterion("prod_type not like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeIn(List<String> values) {
            addCriterion("prod_type in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotIn(List<String> values) {
            addCriterion("prod_type not in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeBetween(String value1, String value2) {
            addCriterion("prod_type between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotBetween(String value1, String value2) {
            addCriterion("prod_type not between", value1, value2, "prodType");
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

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Integer value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Integer value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Integer value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Integer value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Integer value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Integer> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Integer> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Integer value1, Integer value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andPcoStatusIsNull() {
            addCriterion("pco_status is null");
            return (Criteria) this;
        }

        public Criteria andPcoStatusIsNotNull() {
            addCriterion("pco_status is not null");
            return (Criteria) this;
        }

        public Criteria andPcoStatusEqualTo(Integer value) {
            addCriterion("pco_status =", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusNotEqualTo(Integer value) {
            addCriterion("pco_status <>", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusGreaterThan(Integer value) {
            addCriterion("pco_status >", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_status >=", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusLessThan(Integer value) {
            addCriterion("pco_status <", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pco_status <=", value, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusIn(List<Integer> values) {
            addCriterion("pco_status in", values, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusNotIn(List<Integer> values) {
            addCriterion("pco_status not in", values, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusBetween(Integer value1, Integer value2) {
            addCriterion("pco_status between", value1, value2, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andPcoStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_status not between", value1, value2, "pcoStatus");
            return (Criteria) this;
        }

        public Criteria andWlDateIsNull() {
            addCriterion("wl_date is null");
            return (Criteria) this;
        }

        public Criteria andWlDateIsNotNull() {
            addCriterion("wl_date is not null");
            return (Criteria) this;
        }

        public Criteria andWlDateEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date =", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <>", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThan(Date value) {
            addCriterionForJDBCDate("wl_date >", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date >=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThan(Date value) {
            addCriterionForJDBCDate("wl_date <", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date not in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date between", value1, value2, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date not between", value1, value2, "wlDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateIsNull() {
            addCriterion("hope_date is null");
            return (Criteria) this;
        }

        public Criteria andHopeDateIsNotNull() {
            addCriterion("hope_date is not null");
            return (Criteria) this;
        }

        public Criteria andHopeDateEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date =", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date <>", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hope_date >", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date >=", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateLessThan(Date value) {
            addCriterionForJDBCDate("hope_date <", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date <=", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateIn(List<Date> values) {
            addCriterionForJDBCDate("hope_date in", values, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hope_date not in", values, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_date between", value1, value2, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_date not between", value1, value2, "hopeDate");
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

        public Criteria andGreenCodeIsNull() {
            addCriterion("green_code is null");
            return (Criteria) this;
        }

        public Criteria andGreenCodeIsNotNull() {
            addCriterion("green_code is not null");
            return (Criteria) this;
        }

        public Criteria andGreenCodeEqualTo(String value) {
            addCriterion("green_code =", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotEqualTo(String value) {
            addCriterion("green_code <>", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeGreaterThan(String value) {
            addCriterion("green_code >", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeGreaterThanOrEqualTo(String value) {
            addCriterion("green_code >=", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLessThan(String value) {
            addCriterion("green_code <", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLessThanOrEqualTo(String value) {
            addCriterion("green_code <=", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLike(String value) {
            addCriterion("green_code like", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotLike(String value) {
            addCriterion("green_code not like", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeIn(List<String> values) {
            addCriterion("green_code in", values, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotIn(List<String> values) {
            addCriterion("green_code not in", values, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeBetween(String value1, String value2) {
            addCriterion("green_code between", value1, value2, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotBetween(String value1, String value2) {
            addCriterion("green_code not between", value1, value2, "greenCode");
            return (Criteria) this;
        }

        public Criteria andIsWmsIsNull() {
            addCriterion("is_wms is null");
            return (Criteria) this;
        }

        public Criteria andIsWmsIsNotNull() {
            addCriterion("is_wms is not null");
            return (Criteria) this;
        }

        public Criteria andIsWmsEqualTo(Integer value) {
            addCriterion("is_wms =", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotEqualTo(Integer value) {
            addCriterion("is_wms <>", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsGreaterThan(Integer value) {
            addCriterion("is_wms >", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_wms >=", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsLessThan(Integer value) {
            addCriterion("is_wms <", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsLessThanOrEqualTo(Integer value) {
            addCriterion("is_wms <=", value, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsIn(List<Integer> values) {
            addCriterion("is_wms in", values, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotIn(List<Integer> values) {
            addCriterion("is_wms not in", values, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsBetween(Integer value1, Integer value2) {
            addCriterion("is_wms between", value1, value2, "isWms");
            return (Criteria) this;
        }

        public Criteria andIsWmsNotBetween(Integer value1, Integer value2) {
            addCriterion("is_wms not between", value1, value2, "isWms");
            return (Criteria) this;
        }

        public Criteria andBomidIsNull() {
            addCriterion("bomId is null");
            return (Criteria) this;
        }

        public Criteria andBomidIsNotNull() {
            addCriterion("bomId is not null");
            return (Criteria) this;
        }

        public Criteria andBomidEqualTo(Long value) {
            addCriterion("bomId =", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotEqualTo(Long value) {
            addCriterion("bomId <>", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidGreaterThan(Long value) {
            addCriterion("bomId >", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidGreaterThanOrEqualTo(Long value) {
            addCriterion("bomId >=", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidLessThan(Long value) {
            addCriterion("bomId <", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidLessThanOrEqualTo(Long value) {
            addCriterion("bomId <=", value, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidIn(List<Long> values) {
            addCriterion("bomId in", values, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotIn(List<Long> values) {
            addCriterion("bomId not in", values, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidBetween(Long value1, Long value2) {
            addCriterion("bomId between", value1, value2, "bomid");
            return (Criteria) this;
        }

        public Criteria andBomidNotBetween(Long value1, Long value2) {
            addCriterion("bomId not between", value1, value2, "bomid");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagIsNull() {
            addCriterion("spceial_flag is null");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagIsNotNull() {
            addCriterion("spceial_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagEqualTo(Integer value) {
            addCriterion("spceial_flag =", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagNotEqualTo(Integer value) {
            addCriterion("spceial_flag <>", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagGreaterThan(Integer value) {
            addCriterion("spceial_flag >", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("spceial_flag >=", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagLessThan(Integer value) {
            addCriterion("spceial_flag <", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagLessThanOrEqualTo(Integer value) {
            addCriterion("spceial_flag <=", value, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagIn(List<Integer> values) {
            addCriterion("spceial_flag in", values, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagNotIn(List<Integer> values) {
            addCriterion("spceial_flag not in", values, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagBetween(Integer value1, Integer value2) {
            addCriterion("spceial_flag between", value1, value2, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andSpceialFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("spceial_flag not between", value1, value2, "spceialFlag");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNull() {
            addCriterion("out_qty is null");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNotNull() {
            addCriterion("out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOutQtyEqualTo(Integer value) {
            addCriterion("out_qty =", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotEqualTo(Integer value) {
            addCriterion("out_qty <>", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThan(Integer value) {
            addCriterion("out_qty >", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_qty >=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThan(Integer value) {
            addCriterion("out_qty <", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThanOrEqualTo(Integer value) {
            addCriterion("out_qty <=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyIn(List<Integer> values) {
            addCriterion("out_qty in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotIn(List<Integer> values) {
            addCriterion("out_qty not in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyBetween(Integer value1, Integer value2) {
            addCriterion("out_qty between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("out_qty not between", value1, value2, "outQty");
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

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andRoIdIsNull() {
            addCriterion("ro_id is null");
            return (Criteria) this;
        }

        public Criteria andRoIdIsNotNull() {
            addCriterion("ro_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoIdEqualTo(String value) {
            addCriterion("ro_id =", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdNotEqualTo(String value) {
            addCriterion("ro_id <>", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdGreaterThan(String value) {
            addCriterion("ro_id >", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdGreaterThanOrEqualTo(String value) {
            addCriterion("ro_id >=", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdLessThan(String value) {
            addCriterion("ro_id <", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdLessThanOrEqualTo(String value) {
            addCriterion("ro_id <=", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdLike(String value) {
            addCriterion("ro_id like", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdNotLike(String value) {
            addCriterion("ro_id not like", value, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdIn(List<String> values) {
            addCriterion("ro_id in", values, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdNotIn(List<String> values) {
            addCriterion("ro_id not in", values, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdBetween(String value1, String value2) {
            addCriterion("ro_id between", value1, value2, "roId");
            return (Criteria) this;
        }

        public Criteria andRoIdNotBetween(String value1, String value2) {
            addCriterion("ro_id not between", value1, value2, "roId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdIsNull() {
            addCriterion("special_bom_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdIsNotNull() {
            addCriterion("special_bom_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdEqualTo(Long value) {
            addCriterion("special_bom_id =", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdNotEqualTo(Long value) {
            addCriterion("special_bom_id <>", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdGreaterThan(Long value) {
            addCriterion("special_bom_id >", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdGreaterThanOrEqualTo(Long value) {
            addCriterion("special_bom_id >=", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdLessThan(Long value) {
            addCriterion("special_bom_id <", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdLessThanOrEqualTo(Long value) {
            addCriterion("special_bom_id <=", value, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdIn(List<Long> values) {
            addCriterion("special_bom_id in", values, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdNotIn(List<Long> values) {
            addCriterion("special_bom_id not in", values, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdBetween(Long value1, Long value2) {
            addCriterion("special_bom_id between", value1, value2, "specialBomId");
            return (Criteria) this;
        }

        public Criteria andSpecialBomIdNotBetween(Long value1, Long value2) {
            addCriterion("special_bom_id not between", value1, value2, "specialBomId");
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
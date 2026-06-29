package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsDeliveryPlanDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsDeliveryPlanDetailExample() {
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

        public Criteria andPoNoIsNull() {
            addCriterion("po_no is null");
            return (Criteria) this;
        }

        public Criteria andPoNoIsNotNull() {
            addCriterion("po_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoNoEqualTo(String value) {
            addCriterion("po_no =", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotEqualTo(String value) {
            addCriterion("po_no <>", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThan(String value) {
            addCriterion("po_no >", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThanOrEqualTo(String value) {
            addCriterion("po_no >=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThan(String value) {
            addCriterion("po_no <", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThanOrEqualTo(String value) {
            addCriterion("po_no <=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLike(String value) {
            addCriterion("po_no like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotLike(String value) {
            addCriterion("po_no not like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoIn(List<String> values) {
            addCriterion("po_no in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotIn(List<String> values) {
            addCriterion("po_no not in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoBetween(String value1, String value2) {
            addCriterion("po_no between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotBetween(String value1, String value2) {
            addCriterion("po_no not between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoItemIsNull() {
            addCriterion("po_no_item is null");
            return (Criteria) this;
        }

        public Criteria andPoNoItemIsNotNull() {
            addCriterion("po_no_item is not null");
            return (Criteria) this;
        }

        public Criteria andPoNoItemEqualTo(Integer value) {
            addCriterion("po_no_item =", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemNotEqualTo(Integer value) {
            addCriterion("po_no_item <>", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemGreaterThan(Integer value) {
            addCriterion("po_no_item >", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("po_no_item >=", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemLessThan(Integer value) {
            addCriterion("po_no_item <", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemLessThanOrEqualTo(Integer value) {
            addCriterion("po_no_item <=", value, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemIn(List<Integer> values) {
            addCriterion("po_no_item in", values, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemNotIn(List<Integer> values) {
            addCriterion("po_no_item not in", values, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemBetween(Integer value1, Integer value2) {
            addCriterion("po_no_item between", value1, value2, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("po_no_item not between", value1, value2, "poNoItem");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoIsNull() {
            addCriterion("po_no_split_no is null");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoIsNotNull() {
            addCriterion("po_no_split_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoEqualTo(Integer value) {
            addCriterion("po_no_split_no =", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoNotEqualTo(Integer value) {
            addCriterion("po_no_split_no <>", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoGreaterThan(Integer value) {
            addCriterion("po_no_split_no >", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("po_no_split_no >=", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoLessThan(Integer value) {
            addCriterion("po_no_split_no <", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoLessThanOrEqualTo(Integer value) {
            addCriterion("po_no_split_no <=", value, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoIn(List<Integer> values) {
            addCriterion("po_no_split_no in", values, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoNotIn(List<Integer> values) {
            addCriterion("po_no_split_no not in", values, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoBetween(Integer value1, Integer value2) {
            addCriterion("po_no_split_no between", value1, value2, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andPoNoSplitNoNotBetween(Integer value1, Integer value2) {
            addCriterion("po_no_split_no not between", value1, value2, "poNoSplitNo");
            return (Criteria) this;
        }

        public Criteria andDoSourceIsNull() {
            addCriterion("do_source is null");
            return (Criteria) this;
        }

        public Criteria andDoSourceIsNotNull() {
            addCriterion("do_source is not null");
            return (Criteria) this;
        }

        public Criteria andDoSourceEqualTo(String value) {
            addCriterion("do_source =", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotEqualTo(String value) {
            addCriterion("do_source <>", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceGreaterThan(String value) {
            addCriterion("do_source >", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceGreaterThanOrEqualTo(String value) {
            addCriterion("do_source >=", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLessThan(String value) {
            addCriterion("do_source <", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLessThanOrEqualTo(String value) {
            addCriterion("do_source <=", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLike(String value) {
            addCriterion("do_source like", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotLike(String value) {
            addCriterion("do_source not like", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceIn(List<String> values) {
            addCriterion("do_source in", values, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotIn(List<String> values) {
            addCriterion("do_source not in", values, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceBetween(String value1, String value2) {
            addCriterion("do_source between", value1, value2, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotBetween(String value1, String value2) {
            addCriterion("do_source not between", value1, value2, "doSource");
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

        public Criteria andDbIdIsNull() {
            addCriterion("db_id is null");
            return (Criteria) this;
        }

        public Criteria andDbIdIsNotNull() {
            addCriterion("db_id is not null");
            return (Criteria) this;
        }

        public Criteria andDbIdEqualTo(String value) {
            addCriterion("db_id =", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotEqualTo(String value) {
            addCriterion("db_id <>", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdGreaterThan(String value) {
            addCriterion("db_id >", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdGreaterThanOrEqualTo(String value) {
            addCriterion("db_id >=", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdLessThan(String value) {
            addCriterion("db_id <", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdLessThanOrEqualTo(String value) {
            addCriterion("db_id <=", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdLike(String value) {
            addCriterion("db_id like", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotLike(String value) {
            addCriterion("db_id not like", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdIn(List<String> values) {
            addCriterion("db_id in", values, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotIn(List<String> values) {
            addCriterion("db_id not in", values, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdBetween(String value1, String value2) {
            addCriterion("db_id between", value1, value2, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotBetween(String value1, String value2) {
            addCriterion("db_id not between", value1, value2, "dbId");
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

        public Criteria andInventoryIdIsNull() {
            addCriterion("inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIsNotNull() {
            addCriterion("inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdEqualTo(Long value) {
            addCriterion("inventory_id =", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotEqualTo(Long value) {
            addCriterion("inventory_id <>", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThan(Long value) {
            addCriterion("inventory_id >", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_id >=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThan(Long value) {
            addCriterion("inventory_id <", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_id <=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIn(List<Long> values) {
            addCriterion("inventory_id in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotIn(List<Long> values) {
            addCriterion("inventory_id not in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdBetween(Long value1, Long value2) {
            addCriterion("inventory_id between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_id not between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIsNull() {
            addCriterion("inventory_status is null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIsNotNull() {
            addCriterion("inventory_status is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusEqualTo(String value) {
            addCriterion("inventory_status =", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotEqualTo(String value) {
            addCriterion("inventory_status <>", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThan(String value) {
            addCriterion("inventory_status >", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_status >=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThan(String value) {
            addCriterion("inventory_status <", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThanOrEqualTo(String value) {
            addCriterion("inventory_status <=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLike(String value) {
            addCriterion("inventory_status like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotLike(String value) {
            addCriterion("inventory_status not like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIn(List<String> values) {
            addCriterion("inventory_status in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotIn(List<String> values) {
            addCriterion("inventory_status not in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusBetween(String value1, String value2) {
            addCriterion("inventory_status between", value1, value2, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotBetween(String value1, String value2) {
            addCriterion("inventory_status not between", value1, value2, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryIsNull() {
            addCriterion("delivery is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryIsNotNull() {
            addCriterion("delivery is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryEqualTo(String value) {
            addCriterion("delivery =", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryNotEqualTo(String value) {
            addCriterion("delivery <>", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryGreaterThan(String value) {
            addCriterion("delivery >", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryGreaterThanOrEqualTo(String value) {
            addCriterion("delivery >=", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryLessThan(String value) {
            addCriterion("delivery <", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryLessThanOrEqualTo(String value) {
            addCriterion("delivery <=", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryLike(String value) {
            addCriterion("delivery like", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryNotLike(String value) {
            addCriterion("delivery not like", value, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryIn(List<String> values) {
            addCriterion("delivery in", values, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryNotIn(List<String> values) {
            addCriterion("delivery not in", values, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryBetween(String value1, String value2) {
            addCriterion("delivery between", value1, value2, "delivery");
            return (Criteria) this;
        }

        public Criteria andDeliveryNotBetween(String value1, String value2) {
            addCriterion("delivery not between", value1, value2, "delivery");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNull() {
            addCriterion("receive is null");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNotNull() {
            addCriterion("receive is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveEqualTo(String value) {
            addCriterion("receive =", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotEqualTo(String value) {
            addCriterion("receive <>", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThan(String value) {
            addCriterion("receive >", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThanOrEqualTo(String value) {
            addCriterion("receive >=", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThan(String value) {
            addCriterion("receive <", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThanOrEqualTo(String value) {
            addCriterion("receive <=", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLike(String value) {
            addCriterion("receive like", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotLike(String value) {
            addCriterion("receive not like", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveIn(List<String> values) {
            addCriterion("receive in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotIn(List<String> values) {
            addCriterion("receive not in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveBetween(String value1, String value2) {
            addCriterion("receive between", value1, value2, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotBetween(String value1, String value2) {
            addCriterion("receive not between", value1, value2, "receive");
            return (Criteria) this;
        }

        public Criteria andEventSourceIsNull() {
            addCriterion("event_source is null");
            return (Criteria) this;
        }

        public Criteria andEventSourceIsNotNull() {
            addCriterion("event_source is not null");
            return (Criteria) this;
        }

        public Criteria andEventSourceEqualTo(String value) {
            addCriterion("event_source =", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotEqualTo(String value) {
            addCriterion("event_source <>", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceGreaterThan(String value) {
            addCriterion("event_source >", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceGreaterThanOrEqualTo(String value) {
            addCriterion("event_source >=", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLessThan(String value) {
            addCriterion("event_source <", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLessThanOrEqualTo(String value) {
            addCriterion("event_source <=", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceLike(String value) {
            addCriterion("event_source like", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotLike(String value) {
            addCriterion("event_source not like", value, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceIn(List<String> values) {
            addCriterion("event_source in", values, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotIn(List<String> values) {
            addCriterion("event_source not in", values, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceBetween(String value1, String value2) {
            addCriterion("event_source between", value1, value2, "eventSource");
            return (Criteria) this;
        }

        public Criteria andEventSourceNotBetween(String value1, String value2) {
            addCriterion("event_source not between", value1, value2, "eventSource");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleIsNull() {
            addCriterion("current_cycle is null");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleIsNotNull() {
            addCriterion("current_cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleEqualTo(String value) {
            addCriterion("current_cycle =", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotEqualTo(String value) {
            addCriterion("current_cycle <>", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleGreaterThan(String value) {
            addCriterion("current_cycle >", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleGreaterThanOrEqualTo(String value) {
            addCriterion("current_cycle >=", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLessThan(String value) {
            addCriterion("current_cycle <", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLessThanOrEqualTo(String value) {
            addCriterion("current_cycle <=", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleLike(String value) {
            addCriterion("current_cycle like", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotLike(String value) {
            addCriterion("current_cycle not like", value, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleIn(List<String> values) {
            addCriterion("current_cycle in", values, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotIn(List<String> values) {
            addCriterion("current_cycle not in", values, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleBetween(String value1, String value2) {
            addCriterion("current_cycle between", value1, value2, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCurrentCycleNotBetween(String value1, String value2) {
            addCriterion("current_cycle not between", value1, value2, "currentCycle");
            return (Criteria) this;
        }

        public Criteria andCycleNameIsNull() {
            addCriterion("cycle_name is null");
            return (Criteria) this;
        }

        public Criteria andCycleNameIsNotNull() {
            addCriterion("cycle_name is not null");
            return (Criteria) this;
        }

        public Criteria andCycleNameEqualTo(String value) {
            addCriterion("cycle_name =", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameNotEqualTo(String value) {
            addCriterion("cycle_name <>", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameGreaterThan(String value) {
            addCriterion("cycle_name >", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameGreaterThanOrEqualTo(String value) {
            addCriterion("cycle_name >=", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameLessThan(String value) {
            addCriterion("cycle_name <", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameLessThanOrEqualTo(String value) {
            addCriterion("cycle_name <=", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameLike(String value) {
            addCriterion("cycle_name like", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameNotLike(String value) {
            addCriterion("cycle_name not like", value, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameIn(List<String> values) {
            addCriterion("cycle_name in", values, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameNotIn(List<String> values) {
            addCriterion("cycle_name not in", values, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameBetween(String value1, String value2) {
            addCriterion("cycle_name between", value1, value2, "cycleName");
            return (Criteria) this;
        }

        public Criteria andCycleNameNotBetween(String value1, String value2) {
            addCriterion("cycle_name not between", value1, value2, "cycleName");
            return (Criteria) this;
        }

        public Criteria andBeginNameIsNull() {
            addCriterion("begin_name is null");
            return (Criteria) this;
        }

        public Criteria andBeginNameIsNotNull() {
            addCriterion("begin_name is not null");
            return (Criteria) this;
        }

        public Criteria andBeginNameEqualTo(String value) {
            addCriterion("begin_name =", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameNotEqualTo(String value) {
            addCriterion("begin_name <>", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameGreaterThan(String value) {
            addCriterion("begin_name >", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameGreaterThanOrEqualTo(String value) {
            addCriterion("begin_name >=", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameLessThan(String value) {
            addCriterion("begin_name <", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameLessThanOrEqualTo(String value) {
            addCriterion("begin_name <=", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameLike(String value) {
            addCriterion("begin_name like", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameNotLike(String value) {
            addCriterion("begin_name not like", value, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameIn(List<String> values) {
            addCriterion("begin_name in", values, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameNotIn(List<String> values) {
            addCriterion("begin_name not in", values, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameBetween(String value1, String value2) {
            addCriterion("begin_name between", value1, value2, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginNameNotBetween(String value1, String value2) {
            addCriterion("begin_name not between", value1, value2, "beginName");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNull() {
            addCriterion("begin_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNotNull() {
            addCriterion("begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginDateEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date =", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <>", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThan(Date value) {
            addCriterionForJDBCDate("begin_date >", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date >=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThan(Date value) {
            addCriterionForJDBCDate("begin_date <", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date not in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date not between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andCycleDaysIsNull() {
            addCriterion("cycle_days is null");
            return (Criteria) this;
        }

        public Criteria andCycleDaysIsNotNull() {
            addCriterion("cycle_days is not null");
            return (Criteria) this;
        }

        public Criteria andCycleDaysEqualTo(Integer value) {
            addCriterion("cycle_days =", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysNotEqualTo(Integer value) {
            addCriterion("cycle_days <>", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysGreaterThan(Integer value) {
            addCriterion("cycle_days >", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle_days >=", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysLessThan(Integer value) {
            addCriterion("cycle_days <", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysLessThanOrEqualTo(Integer value) {
            addCriterion("cycle_days <=", value, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysIn(List<Integer> values) {
            addCriterion("cycle_days in", values, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysNotIn(List<Integer> values) {
            addCriterion("cycle_days not in", values, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysBetween(Integer value1, Integer value2) {
            addCriterion("cycle_days between", value1, value2, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andCycleDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle_days not between", value1, value2, "cycleDays");
            return (Criteria) this;
        }

        public Criteria andWorkDayIsNull() {
            addCriterion("work_day is null");
            return (Criteria) this;
        }

        public Criteria andWorkDayIsNotNull() {
            addCriterion("work_day is not null");
            return (Criteria) this;
        }

        public Criteria andWorkDayEqualTo(Integer value) {
            addCriterion("work_day =", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotEqualTo(Integer value) {
            addCriterion("work_day <>", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayGreaterThan(Integer value) {
            addCriterion("work_day >", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("work_day >=", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayLessThan(Integer value) {
            addCriterion("work_day <", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayLessThanOrEqualTo(Integer value) {
            addCriterion("work_day <=", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayIn(List<Integer> values) {
            addCriterion("work_day in", values, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotIn(List<Integer> values) {
            addCriterion("work_day not in", values, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayBetween(Integer value1, Integer value2) {
            addCriterion("work_day between", value1, value2, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotBetween(Integer value1, Integer value2) {
            addCriterion("work_day not between", value1, value2, "workDay");
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

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIsNull() {
            addCriterion("current_valid is null");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIsNotNull() {
            addCriterion("current_valid is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentValidEqualTo(Integer value) {
            addCriterion("current_valid =", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotEqualTo(Integer value) {
            addCriterion("current_valid <>", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidGreaterThan(Integer value) {
            addCriterion("current_valid >", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_valid >=", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidLessThan(Integer value) {
            addCriterion("current_valid <", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidLessThanOrEqualTo(Integer value) {
            addCriterion("current_valid <=", value, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidIn(List<Integer> values) {
            addCriterion("current_valid in", values, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotIn(List<Integer> values) {
            addCriterion("current_valid not in", values, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidBetween(Integer value1, Integer value2) {
            addCriterion("current_valid between", value1, value2, "currentValid");
            return (Criteria) this;
        }

        public Criteria andCurrentValidNotBetween(Integer value1, Integer value2) {
            addCriterion("current_valid not between", value1, value2, "currentValid");
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

        public Criteria andExRelationIsNull() {
            addCriterion("ex_relation is null");
            return (Criteria) this;
        }

        public Criteria andExRelationIsNotNull() {
            addCriterion("ex_relation is not null");
            return (Criteria) this;
        }

        public Criteria andExRelationEqualTo(Integer value) {
            addCriterion("ex_relation =", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotEqualTo(Integer value) {
            addCriterion("ex_relation <>", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationGreaterThan(Integer value) {
            addCriterion("ex_relation >", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationGreaterThanOrEqualTo(Integer value) {
            addCriterion("ex_relation >=", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationLessThan(Integer value) {
            addCriterion("ex_relation <", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationLessThanOrEqualTo(Integer value) {
            addCriterion("ex_relation <=", value, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationIn(List<Integer> values) {
            addCriterion("ex_relation in", values, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotIn(List<Integer> values) {
            addCriterion("ex_relation not in", values, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationBetween(Integer value1, Integer value2) {
            addCriterion("ex_relation between", value1, value2, "exRelation");
            return (Criteria) this;
        }

        public Criteria andExRelationNotBetween(Integer value1, Integer value2) {
            addCriterion("ex_relation not between", value1, value2, "exRelation");
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
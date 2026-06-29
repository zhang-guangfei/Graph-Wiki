package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsDoExample() {
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

        public Criteria andDoTypeIsNull() {
            addCriterion("do_type is null");
            return (Criteria) this;
        }

        public Criteria andDoTypeIsNotNull() {
            addCriterion("do_type is not null");
            return (Criteria) this;
        }

        public Criteria andDoTypeEqualTo(String value) {
            addCriterion("do_type =", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotEqualTo(String value) {
            addCriterion("do_type <>", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeGreaterThan(String value) {
            addCriterion("do_type >", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeGreaterThanOrEqualTo(String value) {
            addCriterion("do_type >=", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLessThan(String value) {
            addCriterion("do_type <", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLessThanOrEqualTo(String value) {
            addCriterion("do_type <=", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLike(String value) {
            addCriterion("do_type like", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotLike(String value) {
            addCriterion("do_type not like", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeIn(List<String> values) {
            addCriterion("do_type in", values, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotIn(List<String> values) {
            addCriterion("do_type not in", values, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeBetween(String value1, String value2) {
            addCriterion("do_type between", value1, value2, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotBetween(String value1, String value2) {
            addCriterion("do_type not between", value1, value2, "doType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNull() {
            addCriterion("wait_type is null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIsNotNull() {
            addCriterion("wait_type is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTypeEqualTo(String value) {
            addCriterion("wait_type =", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotEqualTo(String value) {
            addCriterion("wait_type <>", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThan(String value) {
            addCriterion("wait_type >", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("wait_type >=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThan(String value) {
            addCriterion("wait_type <", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLessThanOrEqualTo(String value) {
            addCriterion("wait_type <=", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeLike(String value) {
            addCriterion("wait_type like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotLike(String value) {
            addCriterion("wait_type not like", value, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeIn(List<String> values) {
            addCriterion("wait_type in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotIn(List<String> values) {
            addCriterion("wait_type not in", values, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeBetween(String value1, String value2) {
            addCriterion("wait_type between", value1, value2, "waitType");
            return (Criteria) this;
        }

        public Criteria andWaitTypeNotBetween(String value1, String value2) {
            addCriterion("wait_type not between", value1, value2, "waitType");
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

        public Criteria andGatherWarehouseCodeIsNull() {
            addCriterion("gather_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIsNotNull() {
            addCriterion("gather_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeEqualTo(String value) {
            addCriterion("gather_warehouse_code =", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotEqualTo(String value) {
            addCriterion("gather_warehouse_code <>", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThan(String value) {
            addCriterion("gather_warehouse_code >", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code >=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThan(String value) {
            addCriterion("gather_warehouse_code <", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code <=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLike(String value) {
            addCriterion("gather_warehouse_code like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotLike(String value) {
            addCriterion("gather_warehouse_code not like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIn(List<String> values) {
            addCriterion("gather_warehouse_code in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotIn(List<String> values) {
            addCriterion("gather_warehouse_code not in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code not between", value1, value2, "gatherWarehouseCode");
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

        public Criteria andDoStatusIsNull() {
            addCriterion("do_status is null");
            return (Criteria) this;
        }

        public Criteria andDoStatusIsNotNull() {
            addCriterion("do_status is not null");
            return (Criteria) this;
        }

        public Criteria andDoStatusEqualTo(Integer value) {
            addCriterion("do_status =", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusNotEqualTo(Integer value) {
            addCriterion("do_status <>", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusGreaterThan(Integer value) {
            addCriterion("do_status >", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("do_status >=", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusLessThan(Integer value) {
            addCriterion("do_status <", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusLessThanOrEqualTo(Integer value) {
            addCriterion("do_status <=", value, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusIn(List<Integer> values) {
            addCriterion("do_status in", values, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusNotIn(List<Integer> values) {
            addCriterion("do_status not in", values, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusBetween(Integer value1, Integer value2) {
            addCriterion("do_status between", value1, value2, "doStatus");
            return (Criteria) this;
        }

        public Criteria andDoStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("do_status not between", value1, value2, "doStatus");
            return (Criteria) this;
        }

        public Criteria andCarriedIsNull() {
            addCriterion("carried is null");
            return (Criteria) this;
        }

        public Criteria andCarriedIsNotNull() {
            addCriterion("carried is not null");
            return (Criteria) this;
        }

        public Criteria andCarriedEqualTo(String value) {
            addCriterion("carried =", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotEqualTo(String value) {
            addCriterion("carried <>", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedGreaterThan(String value) {
            addCriterion("carried >", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedGreaterThanOrEqualTo(String value) {
            addCriterion("carried >=", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLessThan(String value) {
            addCriterion("carried <", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLessThanOrEqualTo(String value) {
            addCriterion("carried <=", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLike(String value) {
            addCriterion("carried like", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotLike(String value) {
            addCriterion("carried not like", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedIn(List<String> values) {
            addCriterion("carried in", values, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotIn(List<String> values) {
            addCriterion("carried not in", values, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedBetween(String value1, String value2) {
            addCriterion("carried between", value1, value2, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotBetween(String value1, String value2) {
            addCriterion("carried not between", value1, value2, "carried");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIsNull() {
            addCriterion("express_code is null");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIsNotNull() {
            addCriterion("express_code is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCodeEqualTo(String value) {
            addCriterion("express_code =", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotEqualTo(String value) {
            addCriterion("express_code <>", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeGreaterThan(String value) {
            addCriterion("express_code >", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeGreaterThanOrEqualTo(String value) {
            addCriterion("express_code >=", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLessThan(String value) {
            addCriterion("express_code <", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLessThanOrEqualTo(String value) {
            addCriterion("express_code <=", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLike(String value) {
            addCriterion("express_code like", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotLike(String value) {
            addCriterion("express_code not like", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIn(List<String> values) {
            addCriterion("express_code in", values, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotIn(List<String> values) {
            addCriterion("express_code not in", values, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeBetween(String value1, String value2) {
            addCriterion("express_code between", value1, value2, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotBetween(String value1, String value2) {
            addCriterion("express_code not between", value1, value2, "expressCode");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNull() {
            addCriterion("district is null");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNotNull() {
            addCriterion("district is not null");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualTo(String value) {
            addCriterion("district =", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualTo(String value) {
            addCriterion("district <>", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThan(String value) {
            addCriterion("district >", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("district >=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThan(String value) {
            addCriterion("district <", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualTo(String value) {
            addCriterion("district <=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLike(String value) {
            addCriterion("district like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotLike(String value) {
            addCriterion("district not like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictIn(List<String> values) {
            addCriterion("district in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotIn(List<String> values) {
            addCriterion("district not in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictBetween(String value1, String value2) {
            addCriterion("district between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotBetween(String value1, String value2) {
            addCriterion("district not between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andStreetIsNull() {
            addCriterion("street is null");
            return (Criteria) this;
        }

        public Criteria andStreetIsNotNull() {
            addCriterion("street is not null");
            return (Criteria) this;
        }

        public Criteria andStreetEqualTo(String value) {
            addCriterion("street =", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotEqualTo(String value) {
            addCriterion("street <>", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThan(String value) {
            addCriterion("street >", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThanOrEqualTo(String value) {
            addCriterion("street >=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThan(String value) {
            addCriterion("street <", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThanOrEqualTo(String value) {
            addCriterion("street <=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLike(String value) {
            addCriterion("street like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotLike(String value) {
            addCriterion("street not like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetIn(List<String> values) {
            addCriterion("street in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotIn(List<String> values) {
            addCriterion("street not in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetBetween(String value1, String value2) {
            addCriterion("street between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotBetween(String value1, String value2) {
            addCriterion("street not between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNull() {
            addCriterion("linkman is null");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNotNull() {
            addCriterion("linkman is not null");
            return (Criteria) this;
        }

        public Criteria andLinkmanEqualTo(String value) {
            addCriterion("linkman =", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotEqualTo(String value) {
            addCriterion("linkman <>", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThan(String value) {
            addCriterion("linkman >", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("linkman >=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThan(String value) {
            addCriterion("linkman <", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThanOrEqualTo(String value) {
            addCriterion("linkman <=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLike(String value) {
            addCriterion("linkman like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotLike(String value) {
            addCriterion("linkman not like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanIn(List<String> values) {
            addCriterion("linkman in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotIn(List<String> values) {
            addCriterion("linkman not in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanBetween(String value1, String value2) {
            addCriterion("linkman between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotBetween(String value1, String value2) {
            addCriterion("linkman not between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
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

        public Criteria andPakageTypeIsNull() {
            addCriterion("pakage_type is null");
            return (Criteria) this;
        }

        public Criteria andPakageTypeIsNotNull() {
            addCriterion("pakage_type is not null");
            return (Criteria) this;
        }

        public Criteria andPakageTypeEqualTo(String value) {
            addCriterion("pakage_type =", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeNotEqualTo(String value) {
            addCriterion("pakage_type <>", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeGreaterThan(String value) {
            addCriterion("pakage_type >", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pakage_type >=", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeLessThan(String value) {
            addCriterion("pakage_type <", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeLessThanOrEqualTo(String value) {
            addCriterion("pakage_type <=", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeLike(String value) {
            addCriterion("pakage_type like", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeNotLike(String value) {
            addCriterion("pakage_type not like", value, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeIn(List<String> values) {
            addCriterion("pakage_type in", values, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeNotIn(List<String> values) {
            addCriterion("pakage_type not in", values, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeBetween(String value1, String value2) {
            addCriterion("pakage_type between", value1, value2, "pakageType");
            return (Criteria) this;
        }

        public Criteria andPakageTypeNotBetween(String value1, String value2) {
            addCriterion("pakage_type not between", value1, value2, "pakageType");
            return (Criteria) this;
        }

        public Criteria andExtNumIsNull() {
            addCriterion("ext_num is null");
            return (Criteria) this;
        }

        public Criteria andExtNumIsNotNull() {
            addCriterion("ext_num is not null");
            return (Criteria) this;
        }

        public Criteria andExtNumEqualTo(Integer value) {
            addCriterion("ext_num =", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumNotEqualTo(Integer value) {
            addCriterion("ext_num <>", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumGreaterThan(Integer value) {
            addCriterion("ext_num >", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ext_num >=", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumLessThan(Integer value) {
            addCriterion("ext_num <", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumLessThanOrEqualTo(Integer value) {
            addCriterion("ext_num <=", value, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumIn(List<Integer> values) {
            addCriterion("ext_num in", values, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumNotIn(List<Integer> values) {
            addCriterion("ext_num not in", values, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumBetween(Integer value1, Integer value2) {
            addCriterion("ext_num between", value1, value2, "extNum");
            return (Criteria) this;
        }

        public Criteria andExtNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ext_num not between", value1, value2, "extNum");
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

        public Criteria andPostcodeIsNull() {
            addCriterion("postcode is null");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNotNull() {
            addCriterion("postcode is not null");
            return (Criteria) this;
        }

        public Criteria andPostcodeEqualTo(String value) {
            addCriterion("postcode =", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotEqualTo(String value) {
            addCriterion("postcode <>", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThan(String value) {
            addCriterion("postcode >", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("postcode >=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThan(String value) {
            addCriterion("postcode <", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThanOrEqualTo(String value) {
            addCriterion("postcode <=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLike(String value) {
            addCriterion("postcode like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotLike(String value) {
            addCriterion("postcode not like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIn(List<String> values) {
            addCriterion("postcode in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotIn(List<String> values) {
            addCriterion("postcode not in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeBetween(String value1, String value2) {
            addCriterion("postcode between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotBetween(String value1, String value2) {
            addCriterion("postcode not between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andOrderCountIsNull() {
            addCriterion("order_count is null");
            return (Criteria) this;
        }

        public Criteria andOrderCountIsNotNull() {
            addCriterion("order_count is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCountEqualTo(Integer value) {
            addCriterion("order_count =", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotEqualTo(Integer value) {
            addCriterion("order_count <>", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountGreaterThan(Integer value) {
            addCriterion("order_count >", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_count >=", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountLessThan(Integer value) {
            addCriterion("order_count <", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountLessThanOrEqualTo(Integer value) {
            addCriterion("order_count <=", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountIn(List<Integer> values) {
            addCriterion("order_count in", values, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotIn(List<Integer> values) {
            addCriterion("order_count not in", values, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountBetween(Integer value1, Integer value2) {
            addCriterion("order_count between", value1, value2, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotBetween(Integer value1, Integer value2) {
            addCriterion("order_count not between", value1, value2, "orderCount");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNull() {
            addCriterion("dlv_entire is null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNotNull() {
            addCriterion("dlv_entire is not null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireEqualTo(String value) {
            addCriterion("dlv_entire =", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotEqualTo(String value) {
            addCriterion("dlv_entire <>", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThan(String value) {
            addCriterion("dlv_entire >", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_entire >=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThan(String value) {
            addCriterion("dlv_entire <", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThanOrEqualTo(String value) {
            addCriterion("dlv_entire <=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLike(String value) {
            addCriterion("dlv_entire like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotLike(String value) {
            addCriterion("dlv_entire not like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIn(List<String> values) {
            addCriterion("dlv_entire in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotIn(List<String> values) {
            addCriterion("dlv_entire not in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireBetween(String value1, String value2) {
            addCriterion("dlv_entire between", value1, value2, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotBetween(String value1, String value2) {
            addCriterion("dlv_entire not between", value1, value2, "dlvEntire");
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

        public Criteria andCorderNoIsNull() {
            addCriterion("corder_no is null");
            return (Criteria) this;
        }

        public Criteria andCorderNoIsNotNull() {
            addCriterion("corder_no is not null");
            return (Criteria) this;
        }

        public Criteria andCorderNoEqualTo(String value) {
            addCriterion("corder_no =", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotEqualTo(String value) {
            addCriterion("corder_no <>", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThan(String value) {
            addCriterion("corder_no >", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("corder_no >=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThan(String value) {
            addCriterion("corder_no <", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLessThanOrEqualTo(String value) {
            addCriterion("corder_no <=", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoLike(String value) {
            addCriterion("corder_no like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotLike(String value) {
            addCriterion("corder_no not like", value, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoIn(List<String> values) {
            addCriterion("corder_no in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotIn(List<String> values) {
            addCriterion("corder_no not in", values, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoBetween(String value1, String value2) {
            addCriterion("corder_no between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andCorderNoNotBetween(String value1, String value2) {
            addCriterion("corder_no not between", value1, value2, "corderNo");
            return (Criteria) this;
        }

        public Criteria andExtRoIdIsNull() {
            addCriterion("ext_ro_id is null");
            return (Criteria) this;
        }

        public Criteria andExtRoIdIsNotNull() {
            addCriterion("ext_ro_id is not null");
            return (Criteria) this;
        }

        public Criteria andExtRoIdEqualTo(Integer value) {
            addCriterion("ext_ro_id =", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdNotEqualTo(Integer value) {
            addCriterion("ext_ro_id <>", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdGreaterThan(Integer value) {
            addCriterion("ext_ro_id >", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ext_ro_id >=", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdLessThan(Integer value) {
            addCriterion("ext_ro_id <", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdLessThanOrEqualTo(Integer value) {
            addCriterion("ext_ro_id <=", value, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdIn(List<Integer> values) {
            addCriterion("ext_ro_id in", values, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdNotIn(List<Integer> values) {
            addCriterion("ext_ro_id not in", values, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdBetween(Integer value1, Integer value2) {
            addCriterion("ext_ro_id between", value1, value2, "extRoId");
            return (Criteria) this;
        }

        public Criteria andExtRoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ext_ro_id not between", value1, value2, "extRoId");
            return (Criteria) this;
        }

        public Criteria andSpceialNumIsNull() {
            addCriterion("spceial_num is null");
            return (Criteria) this;
        }

        public Criteria andSpceialNumIsNotNull() {
            addCriterion("spceial_num is not null");
            return (Criteria) this;
        }

        public Criteria andSpceialNumEqualTo(Integer value) {
            addCriterion("spceial_num =", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumNotEqualTo(Integer value) {
            addCriterion("spceial_num <>", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumGreaterThan(Integer value) {
            addCriterion("spceial_num >", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("spceial_num >=", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumLessThan(Integer value) {
            addCriterion("spceial_num <", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumLessThanOrEqualTo(Integer value) {
            addCriterion("spceial_num <=", value, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumIn(List<Integer> values) {
            addCriterion("spceial_num in", values, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumNotIn(List<Integer> values) {
            addCriterion("spceial_num not in", values, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumBetween(Integer value1, Integer value2) {
            addCriterion("spceial_num between", value1, value2, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andSpceialNumNotBetween(Integer value1, Integer value2) {
            addCriterion("spceial_num not between", value1, value2, "spceialNum");
            return (Criteria) this;
        }

        public Criteria andDoStateIsNull() {
            addCriterion("do_state is null");
            return (Criteria) this;
        }

        public Criteria andDoStateIsNotNull() {
            addCriterion("do_state is not null");
            return (Criteria) this;
        }

        public Criteria andDoStateEqualTo(String value) {
            addCriterion("do_state =", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotEqualTo(String value) {
            addCriterion("do_state <>", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateGreaterThan(String value) {
            addCriterion("do_state >", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateGreaterThanOrEqualTo(String value) {
            addCriterion("do_state >=", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLessThan(String value) {
            addCriterion("do_state <", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLessThanOrEqualTo(String value) {
            addCriterion("do_state <=", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateLike(String value) {
            addCriterion("do_state like", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotLike(String value) {
            addCriterion("do_state not like", value, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateIn(List<String> values) {
            addCriterion("do_state in", values, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotIn(List<String> values) {
            addCriterion("do_state not in", values, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateBetween(String value1, String value2) {
            addCriterion("do_state between", value1, value2, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateNotBetween(String value1, String value2) {
            addCriterion("do_state not between", value1, value2, "doState");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIsNull() {
            addCriterion("do_state_detail is null");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIsNotNull() {
            addCriterion("do_state_detail is not null");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailEqualTo(String value) {
            addCriterion("do_state_detail =", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotEqualTo(String value) {
            addCriterion("do_state_detail <>", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailGreaterThan(String value) {
            addCriterion("do_state_detail >", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailGreaterThanOrEqualTo(String value) {
            addCriterion("do_state_detail >=", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLessThan(String value) {
            addCriterion("do_state_detail <", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLessThanOrEqualTo(String value) {
            addCriterion("do_state_detail <=", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailLike(String value) {
            addCriterion("do_state_detail like", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotLike(String value) {
            addCriterion("do_state_detail not like", value, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailIn(List<String> values) {
            addCriterion("do_state_detail in", values, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotIn(List<String> values) {
            addCriterion("do_state_detail not in", values, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailBetween(String value1, String value2) {
            addCriterion("do_state_detail between", value1, value2, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDoStateDetailNotBetween(String value1, String value2) {
            addCriterion("do_state_detail not between", value1, value2, "doStateDetail");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIsNull() {
            addCriterion("dlv_site is null");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIsNotNull() {
            addCriterion("dlv_site is not null");
            return (Criteria) this;
        }

        public Criteria andDlvSiteEqualTo(String value) {
            addCriterion("dlv_site =", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotEqualTo(String value) {
            addCriterion("dlv_site <>", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteGreaterThan(String value) {
            addCriterion("dlv_site >", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_site >=", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLessThan(String value) {
            addCriterion("dlv_site <", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLessThanOrEqualTo(String value) {
            addCriterion("dlv_site <=", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteLike(String value) {
            addCriterion("dlv_site like", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotLike(String value) {
            addCriterion("dlv_site not like", value, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteIn(List<String> values) {
            addCriterion("dlv_site in", values, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotIn(List<String> values) {
            addCriterion("dlv_site not in", values, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteBetween(String value1, String value2) {
            addCriterion("dlv_site between", value1, value2, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDlvSiteNotBetween(String value1, String value2) {
            addCriterion("dlv_site not between", value1, value2, "dlvSite");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_no =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_no <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_no >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_no >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_no <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_no <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_no like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_no not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_no in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_no not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_no between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_no not between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNull() {
            addCriterion("exp_dlv_type is null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIsNotNull() {
            addCriterion("exp_dlv_type is not null");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeEqualTo(Integer value) {
            addCriterion("exp_dlv_type =", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotEqualTo(Integer value) {
            addCriterion("exp_dlv_type <>", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThan(Integer value) {
            addCriterion("exp_dlv_type >", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type >=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThan(Integer value) {
            addCriterion("exp_dlv_type <", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeLessThanOrEqualTo(Integer value) {
            addCriterion("exp_dlv_type <=", value, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeIn(List<Integer> values) {
            addCriterion("exp_dlv_type in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotIn(List<Integer> values) {
            addCriterion("exp_dlv_type not in", values, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type between", value1, value2, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpDlvTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_dlv_type not between", value1, value2, "expDlvType");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIsNull() {
            addCriterion("exp_link_no is null");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIsNotNull() {
            addCriterion("exp_link_no is not null");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoEqualTo(String value) {
            addCriterion("exp_link_no =", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotEqualTo(String value) {
            addCriterion("exp_link_no <>", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoGreaterThan(String value) {
            addCriterion("exp_link_no >", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoGreaterThanOrEqualTo(String value) {
            addCriterion("exp_link_no >=", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLessThan(String value) {
            addCriterion("exp_link_no <", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLessThanOrEqualTo(String value) {
            addCriterion("exp_link_no <=", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoLike(String value) {
            addCriterion("exp_link_no like", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotLike(String value) {
            addCriterion("exp_link_no not like", value, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoIn(List<String> values) {
            addCriterion("exp_link_no in", values, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotIn(List<String> values) {
            addCriterion("exp_link_no not in", values, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoBetween(String value1, String value2) {
            addCriterion("exp_link_no between", value1, value2, "expLinkNo");
            return (Criteria) this;
        }

        public Criteria andExpLinkNoNotBetween(String value1, String value2) {
            addCriterion("exp_link_no not between", value1, value2, "expLinkNo");
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

        public Criteria andRoCrossTypeIsNull() {
            addCriterion("ro_cross_type is null");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeIsNotNull() {
            addCriterion("ro_cross_type is not null");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeEqualTo(Integer value) {
            addCriterion("ro_cross_type =", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeNotEqualTo(Integer value) {
            addCriterion("ro_cross_type <>", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeGreaterThan(Integer value) {
            addCriterion("ro_cross_type >", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ro_cross_type >=", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeLessThan(Integer value) {
            addCriterion("ro_cross_type <", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ro_cross_type <=", value, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeIn(List<Integer> values) {
            addCriterion("ro_cross_type in", values, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeNotIn(List<Integer> values) {
            addCriterion("ro_cross_type not in", values, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeBetween(Integer value1, Integer value2) {
            addCriterion("ro_cross_type between", value1, value2, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRoCrossTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ro_cross_type not between", value1, value2, "roCrossType");
            return (Criteria) this;
        }

        public Criteria andRelocationIsNull() {
            addCriterion("relocation is null");
            return (Criteria) this;
        }

        public Criteria andRelocationIsNotNull() {
            addCriterion("relocation is not null");
            return (Criteria) this;
        }

        public Criteria andRelocationEqualTo(String value) {
            addCriterion("relocation =", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotEqualTo(String value) {
            addCriterion("relocation <>", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationGreaterThan(String value) {
            addCriterion("relocation >", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationGreaterThanOrEqualTo(String value) {
            addCriterion("relocation >=", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLessThan(String value) {
            addCriterion("relocation <", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLessThanOrEqualTo(String value) {
            addCriterion("relocation <=", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationLike(String value) {
            addCriterion("relocation like", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotLike(String value) {
            addCriterion("relocation not like", value, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationIn(List<String> values) {
            addCriterion("relocation in", values, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotIn(List<String> values) {
            addCriterion("relocation not in", values, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationBetween(String value1, String value2) {
            addCriterion("relocation between", value1, value2, "relocation");
            return (Criteria) this;
        }

        public Criteria andRelocationNotBetween(String value1, String value2) {
            addCriterion("relocation not between", value1, value2, "relocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationIsNull() {
            addCriterion("wt_warehouse_location is null");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationIsNotNull() {
            addCriterion("wt_warehouse_location is not null");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationEqualTo(String value) {
            addCriterion("wt_warehouse_location =", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationNotEqualTo(String value) {
            addCriterion("wt_warehouse_location <>", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationGreaterThan(String value) {
            addCriterion("wt_warehouse_location >", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationGreaterThanOrEqualTo(String value) {
            addCriterion("wt_warehouse_location >=", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationLessThan(String value) {
            addCriterion("wt_warehouse_location <", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationLessThanOrEqualTo(String value) {
            addCriterion("wt_warehouse_location <=", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationLike(String value) {
            addCriterion("wt_warehouse_location like", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationNotLike(String value) {
            addCriterion("wt_warehouse_location not like", value, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationIn(List<String> values) {
            addCriterion("wt_warehouse_location in", values, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationNotIn(List<String> values) {
            addCriterion("wt_warehouse_location not in", values, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationBetween(String value1, String value2) {
            addCriterion("wt_warehouse_location between", value1, value2, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWtWarehouseLocationNotBetween(String value1, String value2) {
            addCriterion("wt_warehouse_location not between", value1, value2, "wtWarehouseLocation");
            return (Criteria) this;
        }

        public Criteria andWmsStatusIsNull() {
            addCriterion("wms_status is null");
            return (Criteria) this;
        }

        public Criteria andWmsStatusIsNotNull() {
            addCriterion("wms_status is not null");
            return (Criteria) this;
        }

        public Criteria andWmsStatusEqualTo(Integer value) {
            addCriterion("wms_status =", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusNotEqualTo(Integer value) {
            addCriterion("wms_status <>", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusGreaterThan(Integer value) {
            addCriterion("wms_status >", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("wms_status >=", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusLessThan(Integer value) {
            addCriterion("wms_status <", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusLessThanOrEqualTo(Integer value) {
            addCriterion("wms_status <=", value, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusIn(List<Integer> values) {
            addCriterion("wms_status in", values, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusNotIn(List<Integer> values) {
            addCriterion("wms_status not in", values, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusBetween(Integer value1, Integer value2) {
            addCriterion("wms_status between", value1, value2, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWmsStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("wms_status not between", value1, value2, "wmsStatus");
            return (Criteria) this;
        }

        public Criteria andWaveTimeIsNull() {
            addCriterion("wave_time is null");
            return (Criteria) this;
        }

        public Criteria andWaveTimeIsNotNull() {
            addCriterion("wave_time is not null");
            return (Criteria) this;
        }

        public Criteria andWaveTimeEqualTo(Date value) {
            addCriterion("wave_time =", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeNotEqualTo(Date value) {
            addCriterion("wave_time <>", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeGreaterThan(Date value) {
            addCriterion("wave_time >", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("wave_time >=", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeLessThan(Date value) {
            addCriterion("wave_time <", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeLessThanOrEqualTo(Date value) {
            addCriterion("wave_time <=", value, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeIn(List<Date> values) {
            addCriterion("wave_time in", values, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeNotIn(List<Date> values) {
            addCriterion("wave_time not in", values, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeBetween(Date value1, Date value2) {
            addCriterion("wave_time between", value1, value2, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWaveTimeNotBetween(Date value1, Date value2) {
            addCriterion("wave_time not between", value1, value2, "waveTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeIsNull() {
            addCriterion("wms_time is null");
            return (Criteria) this;
        }

        public Criteria andWmsTimeIsNotNull() {
            addCriterion("wms_time is not null");
            return (Criteria) this;
        }

        public Criteria andWmsTimeEqualTo(Date value) {
            addCriterion("wms_time =", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeNotEqualTo(Date value) {
            addCriterion("wms_time <>", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeGreaterThan(Date value) {
            addCriterion("wms_time >", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("wms_time >=", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeLessThan(Date value) {
            addCriterion("wms_time <", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeLessThanOrEqualTo(Date value) {
            addCriterion("wms_time <=", value, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeIn(List<Date> values) {
            addCriterion("wms_time in", values, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeNotIn(List<Date> values) {
            addCriterion("wms_time not in", values, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeBetween(Date value1, Date value2) {
            addCriterion("wms_time between", value1, value2, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andWmsTimeNotBetween(Date value1, Date value2) {
            addCriterion("wms_time not between", value1, value2, "wmsTime");
            return (Criteria) this;
        }

        public Criteria andReadyReasonIsNull() {
            addCriterion("ready_reason is null");
            return (Criteria) this;
        }

        public Criteria andReadyReasonIsNotNull() {
            addCriterion("ready_reason is not null");
            return (Criteria) this;
        }

        public Criteria andReadyReasonEqualTo(String value) {
            addCriterion("ready_reason =", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonNotEqualTo(String value) {
            addCriterion("ready_reason <>", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonGreaterThan(String value) {
            addCriterion("ready_reason >", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("ready_reason >=", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonLessThan(String value) {
            addCriterion("ready_reason <", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonLessThanOrEqualTo(String value) {
            addCriterion("ready_reason <=", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonLike(String value) {
            addCriterion("ready_reason like", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonNotLike(String value) {
            addCriterion("ready_reason not like", value, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonIn(List<String> values) {
            addCriterion("ready_reason in", values, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonNotIn(List<String> values) {
            addCriterion("ready_reason not in", values, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonBetween(String value1, String value2) {
            addCriterion("ready_reason between", value1, value2, "readyReason");
            return (Criteria) this;
        }

        public Criteria andReadyReasonNotBetween(String value1, String value2) {
            addCriterion("ready_reason not between", value1, value2, "readyReason");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateIsNull() {
            addCriterion("tms_expected_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateIsNotNull() {
            addCriterion("tms_expected_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateEqualTo(Date value) {
            addCriterion("tms_expected_delivery_date =", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateNotEqualTo(Date value) {
            addCriterion("tms_expected_delivery_date <>", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateGreaterThan(Date value) {
            addCriterion("tms_expected_delivery_date >", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("tms_expected_delivery_date >=", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateLessThan(Date value) {
            addCriterion("tms_expected_delivery_date <", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("tms_expected_delivery_date <=", value, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateIn(List<Date> values) {
            addCriterion("tms_expected_delivery_date in", values, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateNotIn(List<Date> values) {
            addCriterion("tms_expected_delivery_date not in", values, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("tms_expected_delivery_date between", value1, value2, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTmsExpectedDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("tms_expected_delivery_date not between", value1, value2, "tmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateIsNull() {
            addCriterion("wms_expected_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateIsNotNull() {
            addCriterion("wms_expected_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateEqualTo(Date value) {
            addCriterion("wms_expected_delivery_date =", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateNotEqualTo(Date value) {
            addCriterion("wms_expected_delivery_date <>", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateGreaterThan(Date value) {
            addCriterion("wms_expected_delivery_date >", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("wms_expected_delivery_date >=", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateLessThan(Date value) {
            addCriterion("wms_expected_delivery_date <", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("wms_expected_delivery_date <=", value, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateIn(List<Date> values) {
            addCriterion("wms_expected_delivery_date in", values, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateNotIn(List<Date> values) {
            addCriterion("wms_expected_delivery_date not in", values, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("wms_expected_delivery_date between", value1, value2, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andWmsExpectedDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("wms_expected_delivery_date not between", value1, value2, "wmsExpectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeIsNull() {
            addCriterion("do_ready_time is null");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeIsNotNull() {
            addCriterion("do_ready_time is not null");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeEqualTo(Date value) {
            addCriterion("do_ready_time =", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeNotEqualTo(Date value) {
            addCriterion("do_ready_time <>", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeGreaterThan(Date value) {
            addCriterion("do_ready_time >", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("do_ready_time >=", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeLessThan(Date value) {
            addCriterion("do_ready_time <", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeLessThanOrEqualTo(Date value) {
            addCriterion("do_ready_time <=", value, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeIn(List<Date> values) {
            addCriterion("do_ready_time in", values, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeNotIn(List<Date> values) {
            addCriterion("do_ready_time not in", values, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeBetween(Date value1, Date value2) {
            addCriterion("do_ready_time between", value1, value2, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDoReadyTimeNotBetween(Date value1, Date value2) {
            addCriterion("do_ready_time not between", value1, value2, "doReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeIsNull() {
            addCriterion("db_ready_time is null");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeIsNotNull() {
            addCriterion("db_ready_time is not null");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeEqualTo(Date value) {
            addCriterion("db_ready_time =", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeNotEqualTo(Date value) {
            addCriterion("db_ready_time <>", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeGreaterThan(Date value) {
            addCriterion("db_ready_time >", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("db_ready_time >=", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeLessThan(Date value) {
            addCriterion("db_ready_time <", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeLessThanOrEqualTo(Date value) {
            addCriterion("db_ready_time <=", value, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeIn(List<Date> values) {
            addCriterion("db_ready_time in", values, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeNotIn(List<Date> values) {
            addCriterion("db_ready_time not in", values, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeBetween(Date value1, Date value2) {
            addCriterion("db_ready_time between", value1, value2, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andDbReadyTimeNotBetween(Date value1, Date value2) {
            addCriterion("db_ready_time not between", value1, value2, "dbReadyTime");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIsNull() {
            addCriterion("source_type is null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIsNotNull() {
            addCriterion("source_type is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeEqualTo(String value) {
            addCriterion("source_type =", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotEqualTo(String value) {
            addCriterion("source_type <>", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThan(String value) {
            addCriterion("source_type >", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("source_type >=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThan(String value) {
            addCriterion("source_type <", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThanOrEqualTo(String value) {
            addCriterion("source_type <=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLike(String value) {
            addCriterion("source_type like", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotLike(String value) {
            addCriterion("source_type not like", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIn(List<String> values) {
            addCriterion("source_type in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotIn(List<String> values) {
            addCriterion("source_type not in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBetween(String value1, String value2) {
            addCriterion("source_type between", value1, value2, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotBetween(String value1, String value2) {
            addCriterion("source_type not between", value1, value2, "sourceType");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNull() {
            addCriterion("CstmName is null");
            return (Criteria) this;
        }

        public Criteria andCstmnameIsNotNull() {
            addCriterion("CstmName is not null");
            return (Criteria) this;
        }

        public Criteria andCstmnameEqualTo(String value) {
            addCriterion("CstmName =", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotEqualTo(String value) {
            addCriterion("CstmName <>", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThan(String value) {
            addCriterion("CstmName >", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameGreaterThanOrEqualTo(String value) {
            addCriterion("CstmName >=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThan(String value) {
            addCriterion("CstmName <", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLessThanOrEqualTo(String value) {
            addCriterion("CstmName <=", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameLike(String value) {
            addCriterion("CstmName like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotLike(String value) {
            addCriterion("CstmName not like", value, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameIn(List<String> values) {
            addCriterion("CstmName in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotIn(List<String> values) {
            addCriterion("CstmName not in", values, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameBetween(String value1, String value2) {
            addCriterion("CstmName between", value1, value2, "cstmname");
            return (Criteria) this;
        }

        public Criteria andCstmnameNotBetween(String value1, String value2) {
            addCriterion("CstmName not between", value1, value2, "cstmname");
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
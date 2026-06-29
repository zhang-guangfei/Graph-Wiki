package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbnormalCreateDbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AbnormalCreateDbExample() {
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

        public Criteria andPcoItemIsNull() {
            addCriterion("pco_item is null");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNotNull() {
            addCriterion("pco_item is not null");
            return (Criteria) this;
        }

        public Criteria andPcoItemEqualTo(Integer value) {
            addCriterion("pco_item =", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotEqualTo(Integer value) {
            addCriterion("pco_item <>", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThan(Integer value) {
            addCriterion("pco_item >", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_item >=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThan(Integer value) {
            addCriterion("pco_item <", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThanOrEqualTo(Integer value) {
            addCriterion("pco_item <=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemIn(List<Integer> values) {
            addCriterion("pco_item in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotIn(List<Integer> values) {
            addCriterion("pco_item not in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemBetween(Integer value1, Integer value2) {
            addCriterion("pco_item between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_item not between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIsNull() {
            addCriterion("sign_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIsNotNull() {
            addCriterion("sign_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeEqualTo(String value) {
            addCriterion("sign_warehouse_code =", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotEqualTo(String value) {
            addCriterion("sign_warehouse_code <>", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeGreaterThan(String value) {
            addCriterion("sign_warehouse_code >", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sign_warehouse_code >=", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLessThan(String value) {
            addCriterion("sign_warehouse_code <", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("sign_warehouse_code <=", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeLike(String value) {
            addCriterion("sign_warehouse_code like", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotLike(String value) {
            addCriterion("sign_warehouse_code not like", value, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeIn(List<String> values) {
            addCriterion("sign_warehouse_code in", values, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotIn(List<String> values) {
            addCriterion("sign_warehouse_code not in", values, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeBetween(String value1, String value2) {
            addCriterion("sign_warehouse_code between", value1, value2, "signWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("sign_warehouse_code not between", value1, value2, "signWarehouseCode");
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

        public Criteria andYyBatchidIsNull() {
            addCriterion("yy_batchId is null");
            return (Criteria) this;
        }

        public Criteria andYyBatchidIsNotNull() {
            addCriterion("yy_batchId is not null");
            return (Criteria) this;
        }

        public Criteria andYyBatchidEqualTo(String value) {
            addCriterion("yy_batchId =", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidNotEqualTo(String value) {
            addCriterion("yy_batchId <>", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidGreaterThan(String value) {
            addCriterion("yy_batchId >", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidGreaterThanOrEqualTo(String value) {
            addCriterion("yy_batchId >=", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidLessThan(String value) {
            addCriterion("yy_batchId <", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidLessThanOrEqualTo(String value) {
            addCriterion("yy_batchId <=", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidLike(String value) {
            addCriterion("yy_batchId like", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidNotLike(String value) {
            addCriterion("yy_batchId not like", value, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidIn(List<String> values) {
            addCriterion("yy_batchId in", values, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidNotIn(List<String> values) {
            addCriterion("yy_batchId not in", values, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidBetween(String value1, String value2) {
            addCriterion("yy_batchId between", value1, value2, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andYyBatchidNotBetween(String value1, String value2) {
            addCriterion("yy_batchId not between", value1, value2, "yyBatchid");
            return (Criteria) this;
        }

        public Criteria andHandFlagIsNull() {
            addCriterion("hand_flag is null");
            return (Criteria) this;
        }

        public Criteria andHandFlagIsNotNull() {
            addCriterion("hand_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHandFlagEqualTo(Integer value) {
            addCriterion("hand_flag =", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagNotEqualTo(Integer value) {
            addCriterion("hand_flag <>", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagGreaterThan(Integer value) {
            addCriterion("hand_flag >", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("hand_flag >=", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagLessThan(Integer value) {
            addCriterion("hand_flag <", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagLessThanOrEqualTo(Integer value) {
            addCriterion("hand_flag <=", value, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagIn(List<Integer> values) {
            addCriterion("hand_flag in", values, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagNotIn(List<Integer> values) {
            addCriterion("hand_flag not in", values, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagBetween(Integer value1, Integer value2) {
            addCriterion("hand_flag between", value1, value2, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("hand_flag not between", value1, value2, "handFlag");
            return (Criteria) this;
        }

        public Criteria andHandMsgIsNull() {
            addCriterion("hand_msg is null");
            return (Criteria) this;
        }

        public Criteria andHandMsgIsNotNull() {
            addCriterion("hand_msg is not null");
            return (Criteria) this;
        }

        public Criteria andHandMsgEqualTo(String value) {
            addCriterion("hand_msg =", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgNotEqualTo(String value) {
            addCriterion("hand_msg <>", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgGreaterThan(String value) {
            addCriterion("hand_msg >", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgGreaterThanOrEqualTo(String value) {
            addCriterion("hand_msg >=", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgLessThan(String value) {
            addCriterion("hand_msg <", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgLessThanOrEqualTo(String value) {
            addCriterion("hand_msg <=", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgLike(String value) {
            addCriterion("hand_msg like", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgNotLike(String value) {
            addCriterion("hand_msg not like", value, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgIn(List<String> values) {
            addCriterion("hand_msg in", values, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgNotIn(List<String> values) {
            addCriterion("hand_msg not in", values, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgBetween(String value1, String value2) {
            addCriterion("hand_msg between", value1, value2, "handMsg");
            return (Criteria) this;
        }

        public Criteria andHandMsgNotBetween(String value1, String value2) {
            addCriterion("hand_msg not between", value1, value2, "handMsg");
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

        public Criteria andOrderItemEqualTo(Integer value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(Integer value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(Integer value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(Integer value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(Integer value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<Integer> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<Integer> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(Integer value1, Integer value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(Integer value1, Integer value2) {
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

        public Criteria andInventoryTableTypeIsNull() {
            addCriterion("inventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIsNotNull() {
            addCriterion("inventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeEqualTo(String value) {
            addCriterion("inventory_table_type =", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotEqualTo(String value) {
            addCriterion("inventory_table_type <>", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThan(String value) {
            addCriterion("inventory_table_type >", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_table_type >=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThan(String value) {
            addCriterion("inventory_table_type <", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("inventory_table_type <=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLike(String value) {
            addCriterion("inventory_table_type like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotLike(String value) {
            addCriterion("inventory_table_type not like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIn(List<String> values) {
            addCriterion("inventory_table_type in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotIn(List<String> values) {
            addCriterion("inventory_table_type not in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeBetween(String value1, String value2) {
            addCriterion("inventory_table_type between", value1, value2, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("inventory_table_type not between", value1, value2, "inventoryTableType");
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

        public Criteria andYyOrdernoIsNull() {
            addCriterion("yy_orderNo is null");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoIsNotNull() {
            addCriterion("yy_orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoEqualTo(String value) {
            addCriterion("yy_orderNo =", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoNotEqualTo(String value) {
            addCriterion("yy_orderNo <>", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoGreaterThan(String value) {
            addCriterion("yy_orderNo >", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("yy_orderNo >=", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoLessThan(String value) {
            addCriterion("yy_orderNo <", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoLessThanOrEqualTo(String value) {
            addCriterion("yy_orderNo <=", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoLike(String value) {
            addCriterion("yy_orderNo like", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoNotLike(String value) {
            addCriterion("yy_orderNo not like", value, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoIn(List<String> values) {
            addCriterion("yy_orderNo in", values, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoNotIn(List<String> values) {
            addCriterion("yy_orderNo not in", values, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoBetween(String value1, String value2) {
            addCriterion("yy_orderNo between", value1, value2, "yyOrderno");
            return (Criteria) this;
        }

        public Criteria andYyOrdernoNotBetween(String value1, String value2) {
            addCriterion("yy_orderNo not between", value1, value2, "yyOrderno");
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

        public Criteria andShelfNoIsNull() {
            addCriterion("shelf_no is null");
            return (Criteria) this;
        }

        public Criteria andShelfNoIsNotNull() {
            addCriterion("shelf_no is not null");
            return (Criteria) this;
        }

        public Criteria andShelfNoEqualTo(String value) {
            addCriterion("shelf_no =", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotEqualTo(String value) {
            addCriterion("shelf_no <>", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoGreaterThan(String value) {
            addCriterion("shelf_no >", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoGreaterThanOrEqualTo(String value) {
            addCriterion("shelf_no >=", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoLessThan(String value) {
            addCriterion("shelf_no <", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoLessThanOrEqualTo(String value) {
            addCriterion("shelf_no <=", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoLike(String value) {
            addCriterion("shelf_no like", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotLike(String value) {
            addCriterion("shelf_no not like", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoIn(List<String> values) {
            addCriterion("shelf_no in", values, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotIn(List<String> values) {
            addCriterion("shelf_no not in", values, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoBetween(String value1, String value2) {
            addCriterion("shelf_no between", value1, value2, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotBetween(String value1, String value2) {
            addCriterion("shelf_no not between", value1, value2, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoIsNull() {
            addCriterion("shelf_model_no is null");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoIsNotNull() {
            addCriterion("shelf_model_no is not null");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoEqualTo(String value) {
            addCriterion("shelf_model_no =", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoNotEqualTo(String value) {
            addCriterion("shelf_model_no <>", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoGreaterThan(String value) {
            addCriterion("shelf_model_no >", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("shelf_model_no >=", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoLessThan(String value) {
            addCriterion("shelf_model_no <", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoLessThanOrEqualTo(String value) {
            addCriterion("shelf_model_no <=", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoLike(String value) {
            addCriterion("shelf_model_no like", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoNotLike(String value) {
            addCriterion("shelf_model_no not like", value, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoIn(List<String> values) {
            addCriterion("shelf_model_no in", values, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoNotIn(List<String> values) {
            addCriterion("shelf_model_no not in", values, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoBetween(String value1, String value2) {
            addCriterion("shelf_model_no between", value1, value2, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfModelNoNotBetween(String value1, String value2) {
            addCriterion("shelf_model_no not between", value1, value2, "shelfModelNo");
            return (Criteria) this;
        }

        public Criteria andShelfQtyIsNull() {
            addCriterion("shelf_qty is null");
            return (Criteria) this;
        }

        public Criteria andShelfQtyIsNotNull() {
            addCriterion("shelf_qty is not null");
            return (Criteria) this;
        }

        public Criteria andShelfQtyEqualTo(Integer value) {
            addCriterion("shelf_qty =", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyNotEqualTo(Integer value) {
            addCriterion("shelf_qty <>", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyGreaterThan(Integer value) {
            addCriterion("shelf_qty >", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("shelf_qty >=", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyLessThan(Integer value) {
            addCriterion("shelf_qty <", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyLessThanOrEqualTo(Integer value) {
            addCriterion("shelf_qty <=", value, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyIn(List<Integer> values) {
            addCriterion("shelf_qty in", values, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyNotIn(List<Integer> values) {
            addCriterion("shelf_qty not in", values, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyBetween(Integer value1, Integer value2) {
            addCriterion("shelf_qty between", value1, value2, "shelfQty");
            return (Criteria) this;
        }

        public Criteria andShelfQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("shelf_qty not between", value1, value2, "shelfQty");
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
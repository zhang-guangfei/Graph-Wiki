package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsPoDeliveryPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoDeliveryPlanExample() {
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

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Long value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Long value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Long value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Long value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Long value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Long> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Long> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Long value1, Long value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Long value1, Long value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
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

        public Criteria andSplitNoIsNull() {
            addCriterion("split_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitNoIsNotNull() {
            addCriterion("split_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitNoEqualTo(Integer value) {
            addCriterion("split_no =", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotEqualTo(Integer value) {
            addCriterion("split_no <>", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThan(Integer value) {
            addCriterion("split_no >", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("split_no >=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThan(Integer value) {
            addCriterion("split_no <", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThanOrEqualTo(Integer value) {
            addCriterion("split_no <=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoIn(List<Integer> values) {
            addCriterion("split_no in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotIn(List<Integer> values) {
            addCriterion("split_no not in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoBetween(Integer value1, Integer value2) {
            addCriterion("split_no between", value1, value2, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotBetween(Integer value1, Integer value2) {
            addCriterion("split_no not between", value1, value2, "splitNo");
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

        public Criteria andLatestDeliveryTimeIsNull() {
            addCriterion("latest_delivery_time is null");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeIsNotNull() {
            addCriterion("latest_delivery_time is not null");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeEqualTo(Date value) {
            addCriterionForJDBCDate("latest_delivery_time =", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("latest_delivery_time <>", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("latest_delivery_time >", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("latest_delivery_time >=", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeLessThan(Date value) {
            addCriterionForJDBCDate("latest_delivery_time <", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("latest_delivery_time <=", value, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeIn(List<Date> values) {
            addCriterionForJDBCDate("latest_delivery_time in", values, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("latest_delivery_time not in", values, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("latest_delivery_time between", value1, value2, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andLatestDeliveryTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("latest_delivery_time not between", value1, value2, "latestDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeIsNull() {
            addCriterion("src_delivery_time is null");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeIsNotNull() {
            addCriterion("src_delivery_time is not null");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeEqualTo(String value) {
            addCriterion("src_delivery_time =", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeNotEqualTo(String value) {
            addCriterion("src_delivery_time <>", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeGreaterThan(String value) {
            addCriterion("src_delivery_time >", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeGreaterThanOrEqualTo(String value) {
            addCriterion("src_delivery_time >=", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeLessThan(String value) {
            addCriterion("src_delivery_time <", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeLessThanOrEqualTo(String value) {
            addCriterion("src_delivery_time <=", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeLike(String value) {
            addCriterion("src_delivery_time like", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeNotLike(String value) {
            addCriterion("src_delivery_time not like", value, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeIn(List<String> values) {
            addCriterion("src_delivery_time in", values, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeNotIn(List<String> values) {
            addCriterion("src_delivery_time not in", values, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeBetween(String value1, String value2) {
            addCriterion("src_delivery_time between", value1, value2, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andSrcDeliveryTimeNotBetween(String value1, String value2) {
            addCriterion("src_delivery_time not between", value1, value2, "srcDeliveryTime");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeIsNull() {
            addCriterion("transtype_code is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeIsNotNull() {
            addCriterion("transtype_code is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeEqualTo(String value) {
            addCriterion("transtype_code =", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeNotEqualTo(String value) {
            addCriterion("transtype_code <>", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeGreaterThan(String value) {
            addCriterion("transtype_code >", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("transtype_code >=", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeLessThan(String value) {
            addCriterion("transtype_code <", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeLessThanOrEqualTo(String value) {
            addCriterion("transtype_code <=", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeLike(String value) {
            addCriterion("transtype_code like", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeNotLike(String value) {
            addCriterion("transtype_code not like", value, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeIn(List<String> values) {
            addCriterion("transtype_code in", values, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeNotIn(List<String> values) {
            addCriterion("transtype_code not in", values, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeBetween(String value1, String value2) {
            addCriterion("transtype_code between", value1, value2, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andTranstypeCodeNotBetween(String value1, String value2) {
            addCriterion("transtype_code not between", value1, value2, "transtypeCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeIsNull() {
            addCriterion("reason_code is null");
            return (Criteria) this;
        }

        public Criteria andReasonCodeIsNotNull() {
            addCriterion("reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andReasonCodeEqualTo(String value) {
            addCriterion("reason_code =", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeNotEqualTo(String value) {
            addCriterion("reason_code <>", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeGreaterThan(String value) {
            addCriterion("reason_code >", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("reason_code >=", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeLessThan(String value) {
            addCriterion("reason_code <", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("reason_code <=", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeLike(String value) {
            addCriterion("reason_code like", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeNotLike(String value) {
            addCriterion("reason_code not like", value, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeIn(List<String> values) {
            addCriterion("reason_code in", values, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeNotIn(List<String> values) {
            addCriterion("reason_code not in", values, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeBetween(String value1, String value2) {
            addCriterion("reason_code between", value1, value2, "reasonCode");
            return (Criteria) this;
        }

        public Criteria andReasonCodeNotBetween(String value1, String value2) {
            addCriterion("reason_code not between", value1, value2, "reasonCode");
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

        public Criteria andNewestIsNull() {
            addCriterion("newest is null");
            return (Criteria) this;
        }

        public Criteria andNewestIsNotNull() {
            addCriterion("newest is not null");
            return (Criteria) this;
        }

        public Criteria andNewestEqualTo(Boolean value) {
            addCriterion("newest =", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestNotEqualTo(Boolean value) {
            addCriterion("newest <>", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestGreaterThan(Boolean value) {
            addCriterion("newest >", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestGreaterThanOrEqualTo(Boolean value) {
            addCriterion("newest >=", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestLessThan(Boolean value) {
            addCriterion("newest <", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestLessThanOrEqualTo(Boolean value) {
            addCriterion("newest <=", value, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestIn(List<Boolean> values) {
            addCriterion("newest in", values, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestNotIn(List<Boolean> values) {
            addCriterion("newest not in", values, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestBetween(Boolean value1, Boolean value2) {
            addCriterion("newest between", value1, value2, "newest");
            return (Criteria) this;
        }

        public Criteria andNewestNotBetween(Boolean value1, Boolean value2) {
            addCriterion("newest not between", value1, value2, "newest");
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

        public Criteria andCreatUserIsNull() {
            addCriterion("creat_user is null");
            return (Criteria) this;
        }

        public Criteria andCreatUserIsNotNull() {
            addCriterion("creat_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreatUserEqualTo(String value) {
            addCriterion("creat_user =", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotEqualTo(String value) {
            addCriterion("creat_user <>", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserGreaterThan(String value) {
            addCriterion("creat_user >", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserGreaterThanOrEqualTo(String value) {
            addCriterion("creat_user >=", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserLessThan(String value) {
            addCriterion("creat_user <", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserLessThanOrEqualTo(String value) {
            addCriterion("creat_user <=", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserLike(String value) {
            addCriterion("creat_user like", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotLike(String value) {
            addCriterion("creat_user not like", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserIn(List<String> values) {
            addCriterion("creat_user in", values, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotIn(List<String> values) {
            addCriterion("creat_user not in", values, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserBetween(String value1, String value2) {
            addCriterion("creat_user between", value1, value2, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotBetween(String value1, String value2) {
            addCriterion("creat_user not between", value1, value2, "creatUser");
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

        public Criteria andUpdatorIsNull() {
            addCriterion("updator is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("updator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("updator =", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("updator <>", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("updator >", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("updator >=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("updator <", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("updator <=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("updator like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("updator not like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorIn(List<String> values) {
            addCriterion("updator in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotIn(List<String> values) {
            addCriterion("updator not in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("updator between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("updator not between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andDeliveryHIsNull() {
            addCriterion("delivery_H is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryHIsNotNull() {
            addCriterion("delivery_H is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryHEqualTo(Date value) {
            addCriterion("delivery_H =", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHNotEqualTo(Date value) {
            addCriterion("delivery_H <>", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHGreaterThan(Date value) {
            addCriterion("delivery_H >", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_H >=", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHLessThan(Date value) {
            addCriterion("delivery_H <", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHLessThanOrEqualTo(Date value) {
            addCriterion("delivery_H <=", value, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHIn(List<Date> values) {
            addCriterion("delivery_H in", values, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHNotIn(List<Date> values) {
            addCriterion("delivery_H not in", values, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHBetween(Date value1, Date value2) {
            addCriterion("delivery_H between", value1, value2, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryHNotBetween(Date value1, Date value2) {
            addCriterion("delivery_H not between", value1, value2, "deliveryH");
            return (Criteria) this;
        }

        public Criteria andDeliveryWIsNull() {
            addCriterion("delivery_W is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryWIsNotNull() {
            addCriterion("delivery_W is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryWEqualTo(Date value) {
            addCriterion("delivery_W =", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWNotEqualTo(Date value) {
            addCriterion("delivery_W <>", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWGreaterThan(Date value) {
            addCriterion("delivery_W >", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_W >=", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWLessThan(Date value) {
            addCriterion("delivery_W <", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWLessThanOrEqualTo(Date value) {
            addCriterion("delivery_W <=", value, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWIn(List<Date> values) {
            addCriterion("delivery_W in", values, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWNotIn(List<Date> values) {
            addCriterion("delivery_W not in", values, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWBetween(Date value1, Date value2) {
            addCriterion("delivery_W between", value1, value2, "deliveryW");
            return (Criteria) this;
        }

        public Criteria andDeliveryWNotBetween(Date value1, Date value2) {
            addCriterion("delivery_W not between", value1, value2, "deliveryW");
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
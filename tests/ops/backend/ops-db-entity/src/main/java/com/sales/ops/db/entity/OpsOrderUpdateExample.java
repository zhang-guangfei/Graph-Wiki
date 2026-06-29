package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsOrderUpdateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsOrderUpdateExample() {
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

        public Criteria andOrderidIsNull() {
            addCriterion("orderid is null");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNotNull() {
            addCriterion("orderid is not null");
            return (Criteria) this;
        }

        public Criteria andOrderidEqualTo(String value) {
            addCriterion("orderid =", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotEqualTo(String value) {
            addCriterion("orderid <>", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThan(String value) {
            addCriterion("orderid >", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThanOrEqualTo(String value) {
            addCriterion("orderid >=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThan(String value) {
            addCriterion("orderid <", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThanOrEqualTo(String value) {
            addCriterion("orderid <=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLike(String value) {
            addCriterion("orderid like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotLike(String value) {
            addCriterion("orderid not like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidIn(List<String> values) {
            addCriterion("orderid in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotIn(List<String> values) {
            addCriterion("orderid not in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidBetween(String value1, String value2) {
            addCriterion("orderid between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotBetween(String value1, String value2) {
            addCriterion("orderid not between", value1, value2, "orderid");
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

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andRangeIsNull() {
            addCriterion("range is null");
            return (Criteria) this;
        }

        public Criteria andRangeIsNotNull() {
            addCriterion("range is not null");
            return (Criteria) this;
        }

        public Criteria andRangeEqualTo(Integer value) {
            addCriterion("range =", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeNotEqualTo(Integer value) {
            addCriterion("range <>", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeGreaterThan(Integer value) {
            addCriterion("range >", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("range >=", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeLessThan(Integer value) {
            addCriterion("range <", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeLessThanOrEqualTo(Integer value) {
            addCriterion("range <=", value, "range");
            return (Criteria) this;
        }

        public Criteria andRangeIn(List<Integer> values) {
            addCriterion("range in", values, "range");
            return (Criteria) this;
        }

        public Criteria andRangeNotIn(List<Integer> values) {
            addCriterion("range not in", values, "range");
            return (Criteria) this;
        }

        public Criteria andRangeBetween(Integer value1, Integer value2) {
            addCriterion("range between", value1, value2, "range");
            return (Criteria) this;
        }

        public Criteria andRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("range not between", value1, value2, "range");
            return (Criteria) this;
        }

        public Criteria andSCkTypeIsNull() {
            addCriterion("s_ck_type is null");
            return (Criteria) this;
        }

        public Criteria andSCkTypeIsNotNull() {
            addCriterion("s_ck_type is not null");
            return (Criteria) this;
        }

        public Criteria andSCkTypeEqualTo(String value) {
            addCriterion("s_ck_type =", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeNotEqualTo(String value) {
            addCriterion("s_ck_type <>", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeGreaterThan(String value) {
            addCriterion("s_ck_type >", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeGreaterThanOrEqualTo(String value) {
            addCriterion("s_ck_type >=", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeLessThan(String value) {
            addCriterion("s_ck_type <", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeLessThanOrEqualTo(String value) {
            addCriterion("s_ck_type <=", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeLike(String value) {
            addCriterion("s_ck_type like", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeNotLike(String value) {
            addCriterion("s_ck_type not like", value, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeIn(List<String> values) {
            addCriterion("s_ck_type in", values, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeNotIn(List<String> values) {
            addCriterion("s_ck_type not in", values, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeBetween(String value1, String value2) {
            addCriterion("s_ck_type between", value1, value2, "sCkType");
            return (Criteria) this;
        }

        public Criteria andSCkTypeNotBetween(String value1, String value2) {
            addCriterion("s_ck_type not between", value1, value2, "sCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeIsNull() {
            addCriterion("t_ck_type is null");
            return (Criteria) this;
        }

        public Criteria andTCkTypeIsNotNull() {
            addCriterion("t_ck_type is not null");
            return (Criteria) this;
        }

        public Criteria andTCkTypeEqualTo(String value) {
            addCriterion("t_ck_type =", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeNotEqualTo(String value) {
            addCriterion("t_ck_type <>", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeGreaterThan(String value) {
            addCriterion("t_ck_type >", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeGreaterThanOrEqualTo(String value) {
            addCriterion("t_ck_type >=", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeLessThan(String value) {
            addCriterion("t_ck_type <", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeLessThanOrEqualTo(String value) {
            addCriterion("t_ck_type <=", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeLike(String value) {
            addCriterion("t_ck_type like", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeNotLike(String value) {
            addCriterion("t_ck_type not like", value, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeIn(List<String> values) {
            addCriterion("t_ck_type in", values, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeNotIn(List<String> values) {
            addCriterion("t_ck_type not in", values, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeBetween(String value1, String value2) {
            addCriterion("t_ck_type between", value1, value2, "tCkType");
            return (Criteria) this;
        }

        public Criteria andTCkTypeNotBetween(String value1, String value2) {
            addCriterion("t_ck_type not between", value1, value2, "tCkType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeIsNull() {
            addCriterion("s_package_type is null");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeIsNotNull() {
            addCriterion("s_package_type is not null");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeEqualTo(String value) {
            addCriterion("s_package_type =", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeNotEqualTo(String value) {
            addCriterion("s_package_type <>", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeGreaterThan(String value) {
            addCriterion("s_package_type >", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("s_package_type >=", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeLessThan(String value) {
            addCriterion("s_package_type <", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeLessThanOrEqualTo(String value) {
            addCriterion("s_package_type <=", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeLike(String value) {
            addCriterion("s_package_type like", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeNotLike(String value) {
            addCriterion("s_package_type not like", value, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeIn(List<String> values) {
            addCriterion("s_package_type in", values, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeNotIn(List<String> values) {
            addCriterion("s_package_type not in", values, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeBetween(String value1, String value2) {
            addCriterion("s_package_type between", value1, value2, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andSPackageTypeNotBetween(String value1, String value2) {
            addCriterion("s_package_type not between", value1, value2, "sPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeIsNull() {
            addCriterion("t_package_type is null");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeIsNotNull() {
            addCriterion("t_package_type is not null");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeEqualTo(String value) {
            addCriterion("t_package_type =", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeNotEqualTo(String value) {
            addCriterion("t_package_type <>", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeGreaterThan(String value) {
            addCriterion("t_package_type >", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("t_package_type >=", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeLessThan(String value) {
            addCriterion("t_package_type <", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeLessThanOrEqualTo(String value) {
            addCriterion("t_package_type <=", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeLike(String value) {
            addCriterion("t_package_type like", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeNotLike(String value) {
            addCriterion("t_package_type not like", value, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeIn(List<String> values) {
            addCriterion("t_package_type in", values, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeNotIn(List<String> values) {
            addCriterion("t_package_type not in", values, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeBetween(String value1, String value2) {
            addCriterion("t_package_type between", value1, value2, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andTPackageTypeNotBetween(String value1, String value2) {
            addCriterion("t_package_type not between", value1, value2, "tPackageType");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateIsNull() {
            addCriterion("s_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateIsNotNull() {
            addCriterion("s_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateEqualTo(Date value) {
            addCriterion("s_delivery_date =", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateNotEqualTo(Date value) {
            addCriterion("s_delivery_date <>", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateGreaterThan(Date value) {
            addCriterion("s_delivery_date >", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("s_delivery_date >=", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateLessThan(Date value) {
            addCriterion("s_delivery_date <", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("s_delivery_date <=", value, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateIn(List<Date> values) {
            addCriterion("s_delivery_date in", values, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateNotIn(List<Date> values) {
            addCriterion("s_delivery_date not in", values, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("s_delivery_date between", value1, value2, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("s_delivery_date not between", value1, value2, "sDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateIsNull() {
            addCriterion("t_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateIsNotNull() {
            addCriterion("t_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateEqualTo(Date value) {
            addCriterion("t_delivery_date =", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateNotEqualTo(Date value) {
            addCriterion("t_delivery_date <>", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateGreaterThan(Date value) {
            addCriterion("t_delivery_date >", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("t_delivery_date >=", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateLessThan(Date value) {
            addCriterion("t_delivery_date <", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("t_delivery_date <=", value, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateIn(List<Date> values) {
            addCriterion("t_delivery_date in", values, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateNotIn(List<Date> values) {
            addCriterion("t_delivery_date not in", values, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("t_delivery_date between", value1, value2, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andTDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("t_delivery_date not between", value1, value2, "tDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andSAddressIsNull() {
            addCriterion("s_address is null");
            return (Criteria) this;
        }

        public Criteria andSAddressIsNotNull() {
            addCriterion("s_address is not null");
            return (Criteria) this;
        }

        public Criteria andSAddressEqualTo(String value) {
            addCriterion("s_address =", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressNotEqualTo(String value) {
            addCriterion("s_address <>", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressGreaterThan(String value) {
            addCriterion("s_address >", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressGreaterThanOrEqualTo(String value) {
            addCriterion("s_address >=", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressLessThan(String value) {
            addCriterion("s_address <", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressLessThanOrEqualTo(String value) {
            addCriterion("s_address <=", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressLike(String value) {
            addCriterion("s_address like", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressNotLike(String value) {
            addCriterion("s_address not like", value, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressIn(List<String> values) {
            addCriterion("s_address in", values, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressNotIn(List<String> values) {
            addCriterion("s_address not in", values, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressBetween(String value1, String value2) {
            addCriterion("s_address between", value1, value2, "sAddress");
            return (Criteria) this;
        }

        public Criteria andSAddressNotBetween(String value1, String value2) {
            addCriterion("s_address not between", value1, value2, "sAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressIsNull() {
            addCriterion("t_address is null");
            return (Criteria) this;
        }

        public Criteria andTAddressIsNotNull() {
            addCriterion("t_address is not null");
            return (Criteria) this;
        }

        public Criteria andTAddressEqualTo(String value) {
            addCriterion("t_address =", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressNotEqualTo(String value) {
            addCriterion("t_address <>", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressGreaterThan(String value) {
            addCriterion("t_address >", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressGreaterThanOrEqualTo(String value) {
            addCriterion("t_address >=", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressLessThan(String value) {
            addCriterion("t_address <", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressLessThanOrEqualTo(String value) {
            addCriterion("t_address <=", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressLike(String value) {
            addCriterion("t_address like", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressNotLike(String value) {
            addCriterion("t_address not like", value, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressIn(List<String> values) {
            addCriterion("t_address in", values, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressNotIn(List<String> values) {
            addCriterion("t_address not in", values, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressBetween(String value1, String value2) {
            addCriterion("t_address between", value1, value2, "tAddress");
            return (Criteria) this;
        }

        public Criteria andTAddressNotBetween(String value1, String value2) {
            addCriterion("t_address not between", value1, value2, "tAddress");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andSuccessIsNull() {
            addCriterion("success is null");
            return (Criteria) this;
        }

        public Criteria andSuccessIsNotNull() {
            addCriterion("success is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessEqualTo(String value) {
            addCriterion("success =", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotEqualTo(String value) {
            addCriterion("success <>", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessGreaterThan(String value) {
            addCriterion("success >", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessGreaterThanOrEqualTo(String value) {
            addCriterion("success >=", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessLessThan(String value) {
            addCriterion("success <", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessLessThanOrEqualTo(String value) {
            addCriterion("success <=", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessLike(String value) {
            addCriterion("success like", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotLike(String value) {
            addCriterion("success not like", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessIn(List<String> values) {
            addCriterion("success in", values, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotIn(List<String> values) {
            addCriterion("success not in", values, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessBetween(String value1, String value2) {
            addCriterion("success between", value1, value2, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotBetween(String value1, String value2) {
            addCriterion("success not between", value1, value2, "success");
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

        public Criteria andSSpecialflagIsNull() {
            addCriterion("s_specialFlag is null");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagIsNotNull() {
            addCriterion("s_specialFlag is not null");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagEqualTo(String value) {
            addCriterion("s_specialFlag =", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagNotEqualTo(String value) {
            addCriterion("s_specialFlag <>", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagGreaterThan(String value) {
            addCriterion("s_specialFlag >", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagGreaterThanOrEqualTo(String value) {
            addCriterion("s_specialFlag >=", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagLessThan(String value) {
            addCriterion("s_specialFlag <", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagLessThanOrEqualTo(String value) {
            addCriterion("s_specialFlag <=", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagLike(String value) {
            addCriterion("s_specialFlag like", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagNotLike(String value) {
            addCriterion("s_specialFlag not like", value, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagIn(List<String> values) {
            addCriterion("s_specialFlag in", values, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagNotIn(List<String> values) {
            addCriterion("s_specialFlag not in", values, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagBetween(String value1, String value2) {
            addCriterion("s_specialFlag between", value1, value2, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSSpecialflagNotBetween(String value1, String value2) {
            addCriterion("s_specialFlag not between", value1, value2, "sSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagIsNull() {
            addCriterion("t_specialFlag is null");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagIsNotNull() {
            addCriterion("t_specialFlag is not null");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagEqualTo(Integer value) {
            addCriterion("t_specialFlag =", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagNotEqualTo(Integer value) {
            addCriterion("t_specialFlag <>", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagGreaterThan(Integer value) {
            addCriterion("t_specialFlag >", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("t_specialFlag >=", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagLessThan(Integer value) {
            addCriterion("t_specialFlag <", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagLessThanOrEqualTo(Integer value) {
            addCriterion("t_specialFlag <=", value, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagIn(List<Integer> values) {
            addCriterion("t_specialFlag in", values, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagNotIn(List<Integer> values) {
            addCriterion("t_specialFlag not in", values, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagBetween(Integer value1, Integer value2) {
            addCriterion("t_specialFlag between", value1, value2, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andTSpecialflagNotBetween(Integer value1, Integer value2) {
            addCriterion("t_specialFlag not between", value1, value2, "tSpecialflag");
            return (Criteria) this;
        }

        public Criteria andSCarriedIsNull() {
            addCriterion("s_carried is null");
            return (Criteria) this;
        }

        public Criteria andSCarriedIsNotNull() {
            addCriterion("s_carried is not null");
            return (Criteria) this;
        }

        public Criteria andSCarriedEqualTo(String value) {
            addCriterion("s_carried =", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedNotEqualTo(String value) {
            addCriterion("s_carried <>", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedGreaterThan(String value) {
            addCriterion("s_carried >", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedGreaterThanOrEqualTo(String value) {
            addCriterion("s_carried >=", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedLessThan(String value) {
            addCriterion("s_carried <", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedLessThanOrEqualTo(String value) {
            addCriterion("s_carried <=", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedLike(String value) {
            addCriterion("s_carried like", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedNotLike(String value) {
            addCriterion("s_carried not like", value, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedIn(List<String> values) {
            addCriterion("s_carried in", values, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedNotIn(List<String> values) {
            addCriterion("s_carried not in", values, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedBetween(String value1, String value2) {
            addCriterion("s_carried between", value1, value2, "sCarried");
            return (Criteria) this;
        }

        public Criteria andSCarriedNotBetween(String value1, String value2) {
            addCriterion("s_carried not between", value1, value2, "sCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedIsNull() {
            addCriterion("t_carried is null");
            return (Criteria) this;
        }

        public Criteria andTCarriedIsNotNull() {
            addCriterion("t_carried is not null");
            return (Criteria) this;
        }

        public Criteria andTCarriedEqualTo(String value) {
            addCriterion("t_carried =", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedNotEqualTo(String value) {
            addCriterion("t_carried <>", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedGreaterThan(String value) {
            addCriterion("t_carried >", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedGreaterThanOrEqualTo(String value) {
            addCriterion("t_carried >=", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedLessThan(String value) {
            addCriterion("t_carried <", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedLessThanOrEqualTo(String value) {
            addCriterion("t_carried <=", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedLike(String value) {
            addCriterion("t_carried like", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedNotLike(String value) {
            addCriterion("t_carried not like", value, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedIn(List<String> values) {
            addCriterion("t_carried in", values, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedNotIn(List<String> values) {
            addCriterion("t_carried not in", values, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedBetween(String value1, String value2) {
            addCriterion("t_carried between", value1, value2, "tCarried");
            return (Criteria) this;
        }

        public Criteria andTCarriedNotBetween(String value1, String value2) {
            addCriterion("t_carried not between", value1, value2, "tCarried");
            return (Criteria) this;
        }

        public Criteria andAssmodelIsNull() {
            addCriterion("assModel is null");
            return (Criteria) this;
        }

        public Criteria andAssmodelIsNotNull() {
            addCriterion("assModel is not null");
            return (Criteria) this;
        }

        public Criteria andAssmodelEqualTo(Boolean value) {
            addCriterion("assModel =", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelNotEqualTo(Boolean value) {
            addCriterion("assModel <>", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelGreaterThan(Boolean value) {
            addCriterion("assModel >", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelGreaterThanOrEqualTo(Boolean value) {
            addCriterion("assModel >=", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelLessThan(Boolean value) {
            addCriterion("assModel <", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelLessThanOrEqualTo(Boolean value) {
            addCriterion("assModel <=", value, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelIn(List<Boolean> values) {
            addCriterion("assModel in", values, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelNotIn(List<Boolean> values) {
            addCriterion("assModel not in", values, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelBetween(Boolean value1, Boolean value2) {
            addCriterion("assModel between", value1, value2, "assmodel");
            return (Criteria) this;
        }

        public Criteria andAssmodelNotBetween(Boolean value1, Boolean value2) {
            addCriterion("assModel not between", value1, value2, "assmodel");
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
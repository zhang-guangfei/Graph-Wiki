package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsOrderUpdateLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsOrderUpdateLogExample() {
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

        public Criteria andParamsIsNull() {
            addCriterion("params is null");
            return (Criteria) this;
        }

        public Criteria andParamsIsNotNull() {
            addCriterion("params is not null");
            return (Criteria) this;
        }

        public Criteria andParamsEqualTo(String value) {
            addCriterion("params =", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotEqualTo(String value) {
            addCriterion("params <>", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThan(String value) {
            addCriterion("params >", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThanOrEqualTo(String value) {
            addCriterion("params >=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThan(String value) {
            addCriterion("params <", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThanOrEqualTo(String value) {
            addCriterion("params <=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLike(String value) {
            addCriterion("params like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotLike(String value) {
            addCriterion("params not like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsIn(List<String> values) {
            addCriterion("params in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotIn(List<String> values) {
            addCriterion("params not in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsBetween(String value1, String value2) {
            addCriterion("params between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotBetween(String value1, String value2) {
            addCriterion("params not between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andMasterIsNull() {
            addCriterion("master is null");
            return (Criteria) this;
        }

        public Criteria andMasterIsNotNull() {
            addCriterion("master is not null");
            return (Criteria) this;
        }

        public Criteria andMasterEqualTo(Boolean value) {
            addCriterion("master =", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotEqualTo(Boolean value) {
            addCriterion("master <>", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterGreaterThan(Boolean value) {
            addCriterion("master >", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("master >=", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterLessThan(Boolean value) {
            addCriterion("master <", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterLessThanOrEqualTo(Boolean value) {
            addCriterion("master <=", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterIn(List<Boolean> values) {
            addCriterion("master in", values, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotIn(List<Boolean> values) {
            addCriterion("master not in", values, "master");
            return (Criteria) this;
        }

        public Criteria andMasterBetween(Boolean value1, Boolean value2) {
            addCriterion("master between", value1, value2, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("master not between", value1, value2, "master");
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

        public Criteria andDlvTypeIsNull() {
            addCriterion("dlv_type is null");
            return (Criteria) this;
        }

        public Criteria andDlvTypeIsNotNull() {
            addCriterion("dlv_type is not null");
            return (Criteria) this;
        }

        public Criteria andDlvTypeEqualTo(String value) {
            addCriterion("dlv_type =", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeNotEqualTo(String value) {
            addCriterion("dlv_type <>", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeGreaterThan(String value) {
            addCriterion("dlv_type >", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_type >=", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeLessThan(String value) {
            addCriterion("dlv_type <", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeLessThanOrEqualTo(String value) {
            addCriterion("dlv_type <=", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeLike(String value) {
            addCriterion("dlv_type like", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeNotLike(String value) {
            addCriterion("dlv_type not like", value, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeIn(List<String> values) {
            addCriterion("dlv_type in", values, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeNotIn(List<String> values) {
            addCriterion("dlv_type not in", values, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeBetween(String value1, String value2) {
            addCriterion("dlv_type between", value1, value2, "dlvType");
            return (Criteria) this;
        }

        public Criteria andDlvTypeNotBetween(String value1, String value2) {
            addCriterion("dlv_type not between", value1, value2, "dlvType");
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

        public Criteria andWmsDlvDataIsNull() {
            addCriterion("wms_dlv_data is null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataIsNotNull() {
            addCriterion("wms_dlv_data is not null");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataEqualTo(Date value) {
            addCriterion("wms_dlv_data =", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataNotEqualTo(Date value) {
            addCriterion("wms_dlv_data <>", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataGreaterThan(Date value) {
            addCriterion("wms_dlv_data >", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataGreaterThanOrEqualTo(Date value) {
            addCriterion("wms_dlv_data >=", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataLessThan(Date value) {
            addCriterion("wms_dlv_data <", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataLessThanOrEqualTo(Date value) {
            addCriterion("wms_dlv_data <=", value, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataIn(List<Date> values) {
            addCriterion("wms_dlv_data in", values, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataNotIn(List<Date> values) {
            addCriterion("wms_dlv_data not in", values, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataBetween(Date value1, Date value2) {
            addCriterion("wms_dlv_data between", value1, value2, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andWmsDlvDataNotBetween(Date value1, Date value2) {
            addCriterion("wms_dlv_data not between", value1, value2, "wmsDlvData");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialIsNull() {
            addCriterion("dlv_special is null");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialIsNotNull() {
            addCriterion("dlv_special is not null");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialEqualTo(Boolean value) {
            addCriterion("dlv_special =", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialNotEqualTo(Boolean value) {
            addCriterion("dlv_special <>", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialGreaterThan(Boolean value) {
            addCriterion("dlv_special >", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialGreaterThanOrEqualTo(Boolean value) {
            addCriterion("dlv_special >=", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialLessThan(Boolean value) {
            addCriterion("dlv_special <", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialLessThanOrEqualTo(Boolean value) {
            addCriterion("dlv_special <=", value, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialIn(List<Boolean> values) {
            addCriterion("dlv_special in", values, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialNotIn(List<Boolean> values) {
            addCriterion("dlv_special not in", values, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_special between", value1, value2, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSpecialNotBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_special not between", value1, value2, "dlvSpecial");
            return (Criteria) this;
        }

        public Criteria andDlvSelfIsNull() {
            addCriterion("dlv_self is null");
            return (Criteria) this;
        }

        public Criteria andDlvSelfIsNotNull() {
            addCriterion("dlv_self is not null");
            return (Criteria) this;
        }

        public Criteria andDlvSelfEqualTo(Boolean value) {
            addCriterion("dlv_self =", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfNotEqualTo(Boolean value) {
            addCriterion("dlv_self <>", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfGreaterThan(Boolean value) {
            addCriterion("dlv_self >", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfGreaterThanOrEqualTo(Boolean value) {
            addCriterion("dlv_self >=", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfLessThan(Boolean value) {
            addCriterion("dlv_self <", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfLessThanOrEqualTo(Boolean value) {
            addCriterion("dlv_self <=", value, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfIn(List<Boolean> values) {
            addCriterion("dlv_self in", values, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfNotIn(List<Boolean> values) {
            addCriterion("dlv_self not in", values, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_self between", value1, value2, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSelfNotBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_self not between", value1, value2, "dlvSelf");
            return (Criteria) this;
        }

        public Criteria andDlvSplitIsNull() {
            addCriterion("dlv_split is null");
            return (Criteria) this;
        }

        public Criteria andDlvSplitIsNotNull() {
            addCriterion("dlv_split is not null");
            return (Criteria) this;
        }

        public Criteria andDlvSplitEqualTo(Boolean value) {
            addCriterion("dlv_split =", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitNotEqualTo(Boolean value) {
            addCriterion("dlv_split <>", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitGreaterThan(Boolean value) {
            addCriterion("dlv_split >", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("dlv_split >=", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitLessThan(Boolean value) {
            addCriterion("dlv_split <", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitLessThanOrEqualTo(Boolean value) {
            addCriterion("dlv_split <=", value, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitIn(List<Boolean> values) {
            addCriterion("dlv_split in", values, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitNotIn(List<Boolean> values) {
            addCriterion("dlv_split not in", values, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_split between", value1, value2, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvSplitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("dlv_split not between", value1, value2, "dlvSplit");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIsNull() {
            addCriterion("dlv_address is null");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIsNotNull() {
            addCriterion("dlv_address is not null");
            return (Criteria) this;
        }

        public Criteria andDlvAddressEqualTo(String value) {
            addCriterion("dlv_address =", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotEqualTo(String value) {
            addCriterion("dlv_address <>", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressGreaterThan(String value) {
            addCriterion("dlv_address >", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_address >=", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLessThan(String value) {
            addCriterion("dlv_address <", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLessThanOrEqualTo(String value) {
            addCriterion("dlv_address <=", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressLike(String value) {
            addCriterion("dlv_address like", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotLike(String value) {
            addCriterion("dlv_address not like", value, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressIn(List<String> values) {
            addCriterion("dlv_address in", values, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotIn(List<String> values) {
            addCriterion("dlv_address not in", values, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressBetween(String value1, String value2) {
            addCriterion("dlv_address between", value1, value2, "dlvAddress");
            return (Criteria) this;
        }

        public Criteria andDlvAddressNotBetween(String value1, String value2) {
            addCriterion("dlv_address not between", value1, value2, "dlvAddress");
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

        public Criteria andChangeFlagIsNull() {
            addCriterion("change_flag is null");
            return (Criteria) this;
        }

        public Criteria andChangeFlagIsNotNull() {
            addCriterion("change_flag is not null");
            return (Criteria) this;
        }

        public Criteria andChangeFlagEqualTo(Boolean value) {
            addCriterion("change_flag =", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotEqualTo(Boolean value) {
            addCriterion("change_flag <>", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagGreaterThan(Boolean value) {
            addCriterion("change_flag >", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("change_flag >=", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagLessThan(Boolean value) {
            addCriterion("change_flag <", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("change_flag <=", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagIn(List<Boolean> values) {
            addCriterion("change_flag in", values, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotIn(List<Boolean> values) {
            addCriterion("change_flag not in", values, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("change_flag between", value1, value2, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("change_flag not between", value1, value2, "changeFlag");
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
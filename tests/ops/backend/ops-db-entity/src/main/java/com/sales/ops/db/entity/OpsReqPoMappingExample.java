package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsReqPoMappingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsReqPoMappingExample() {
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

        public Criteria andRequestpurchaseidIsNull() {
            addCriterion("RequestPurchaseId is null");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidIsNotNull() {
            addCriterion("RequestPurchaseId is not null");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidEqualTo(Long value) {
            addCriterion("RequestPurchaseId =", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidNotEqualTo(Long value) {
            addCriterion("RequestPurchaseId <>", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidGreaterThan(Long value) {
            addCriterion("RequestPurchaseId >", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidGreaterThanOrEqualTo(Long value) {
            addCriterion("RequestPurchaseId >=", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidLessThan(Long value) {
            addCriterion("RequestPurchaseId <", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidLessThanOrEqualTo(Long value) {
            addCriterion("RequestPurchaseId <=", value, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidIn(List<Long> values) {
            addCriterion("RequestPurchaseId in", values, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidNotIn(List<Long> values) {
            addCriterion("RequestPurchaseId not in", values, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidBetween(Long value1, Long value2) {
            addCriterion("RequestPurchaseId between", value1, value2, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andRequestpurchaseidNotBetween(Long value1, Long value2) {
            addCriterion("RequestPurchaseId not between", value1, value2, "requestpurchaseid");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoIsNull() {
            addCriterion("PurchaseOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoIsNotNull() {
            addCriterion("PurchaseOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoEqualTo(String value) {
            addCriterion("PurchaseOrderNo =", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoNotEqualTo(String value) {
            addCriterion("PurchaseOrderNo <>", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoGreaterThan(String value) {
            addCriterion("PurchaseOrderNo >", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoGreaterThanOrEqualTo(String value) {
            addCriterion("PurchaseOrderNo >=", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoLessThan(String value) {
            addCriterion("PurchaseOrderNo <", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoLessThanOrEqualTo(String value) {
            addCriterion("PurchaseOrderNo <=", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoLike(String value) {
            addCriterion("PurchaseOrderNo like", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoNotLike(String value) {
            addCriterion("PurchaseOrderNo not like", value, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoIn(List<String> values) {
            addCriterion("PurchaseOrderNo in", values, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoNotIn(List<String> values) {
            addCriterion("PurchaseOrderNo not in", values, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoBetween(String value1, String value2) {
            addCriterion("PurchaseOrderNo between", value1, value2, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andPurchaseordernoNotBetween(String value1, String value2) {
            addCriterion("PurchaseOrderNo not between", value1, value2, "purchaseorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoIsNull() {
            addCriterion("RequestOrderno is null");
            return (Criteria) this;
        }

        public Criteria andRequestordernoIsNotNull() {
            addCriterion("RequestOrderno is not null");
            return (Criteria) this;
        }

        public Criteria andRequestordernoEqualTo(String value) {
            addCriterion("RequestOrderno =", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoNotEqualTo(String value) {
            addCriterion("RequestOrderno <>", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoGreaterThan(String value) {
            addCriterion("RequestOrderno >", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoGreaterThanOrEqualTo(String value) {
            addCriterion("RequestOrderno >=", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoLessThan(String value) {
            addCriterion("RequestOrderno <", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoLessThanOrEqualTo(String value) {
            addCriterion("RequestOrderno <=", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoLike(String value) {
            addCriterion("RequestOrderno like", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoNotLike(String value) {
            addCriterion("RequestOrderno not like", value, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoIn(List<String> values) {
            addCriterion("RequestOrderno in", values, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoNotIn(List<String> values) {
            addCriterion("RequestOrderno not in", values, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoBetween(String value1, String value2) {
            addCriterion("RequestOrderno between", value1, value2, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestordernoNotBetween(String value1, String value2) {
            addCriterion("RequestOrderno not between", value1, value2, "requestorderno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoIsNull() {
            addCriterion("RequestItemno is null");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoIsNotNull() {
            addCriterion("RequestItemno is not null");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoEqualTo(Integer value) {
            addCriterion("RequestItemno =", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoNotEqualTo(Integer value) {
            addCriterion("RequestItemno <>", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoGreaterThan(Integer value) {
            addCriterion("RequestItemno >", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("RequestItemno >=", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoLessThan(Integer value) {
            addCriterion("RequestItemno <", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoLessThanOrEqualTo(Integer value) {
            addCriterion("RequestItemno <=", value, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoIn(List<Integer> values) {
            addCriterion("RequestItemno in", values, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoNotIn(List<Integer> values) {
            addCriterion("RequestItemno not in", values, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoBetween(Integer value1, Integer value2) {
            addCriterion("RequestItemno between", value1, value2, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestitemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("RequestItemno not between", value1, value2, "requestitemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoIsNull() {
            addCriterion("RequestSplitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoIsNotNull() {
            addCriterion("RequestSplitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoEqualTo(Integer value) {
            addCriterion("RequestSplitItemNo =", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoNotEqualTo(Integer value) {
            addCriterion("RequestSplitItemNo <>", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoGreaterThan(Integer value) {
            addCriterion("RequestSplitItemNo >", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("RequestSplitItemNo >=", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoLessThan(Integer value) {
            addCriterion("RequestSplitItemNo <", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("RequestSplitItemNo <=", value, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoIn(List<Integer> values) {
            addCriterion("RequestSplitItemNo in", values, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoNotIn(List<Integer> values) {
            addCriterion("RequestSplitItemNo not in", values, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("RequestSplitItemNo between", value1, value2, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andRequestsplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("RequestSplitItemNo not between", value1, value2, "requestsplititemno");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
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
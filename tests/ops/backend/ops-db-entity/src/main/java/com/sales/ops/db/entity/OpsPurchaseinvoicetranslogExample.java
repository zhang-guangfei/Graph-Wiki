package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPurchaseinvoicetranslogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPurchaseinvoicetranslogExample() {
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

        public Criteria andPonoIsNull() {
            addCriterion("pono is null");
            return (Criteria) this;
        }

        public Criteria andPonoIsNotNull() {
            addCriterion("pono is not null");
            return (Criteria) this;
        }

        public Criteria andPonoEqualTo(String value) {
            addCriterion("pono =", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotEqualTo(String value) {
            addCriterion("pono <>", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThan(String value) {
            addCriterion("pono >", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoGreaterThanOrEqualTo(String value) {
            addCriterion("pono >=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThan(String value) {
            addCriterion("pono <", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLessThanOrEqualTo(String value) {
            addCriterion("pono <=", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoLike(String value) {
            addCriterion("pono like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotLike(String value) {
            addCriterion("pono not like", value, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoIn(List<String> values) {
            addCriterion("pono in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotIn(List<String> values) {
            addCriterion("pono not in", values, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoBetween(String value1, String value2) {
            addCriterion("pono between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andPonoNotBetween(String value1, String value2) {
            addCriterion("pono not between", value1, value2, "pono");
            return (Criteria) this;
        }

        public Criteria andLineitemIsNull() {
            addCriterion("lineitem is null");
            return (Criteria) this;
        }

        public Criteria andLineitemIsNotNull() {
            addCriterion("lineitem is not null");
            return (Criteria) this;
        }

        public Criteria andLineitemEqualTo(Integer value) {
            addCriterion("lineitem =", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotEqualTo(Integer value) {
            addCriterion("lineitem <>", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThan(Integer value) {
            addCriterion("lineitem >", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("lineitem >=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThan(Integer value) {
            addCriterion("lineitem <", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemLessThanOrEqualTo(Integer value) {
            addCriterion("lineitem <=", value, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemIn(List<Integer> values) {
            addCriterion("lineitem in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotIn(List<Integer> values) {
            addCriterion("lineitem not in", values, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemBetween(Integer value1, Integer value2) {
            addCriterion("lineitem between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andLineitemNotBetween(Integer value1, Integer value2) {
            addCriterion("lineitem not between", value1, value2, "lineitem");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNull() {
            addCriterion("invoiceid is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIsNotNull() {
            addCriterion("invoiceid is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceidEqualTo(Long value) {
            addCriterion("invoiceid =", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotEqualTo(Long value) {
            addCriterion("invoiceid <>", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThan(Long value) {
            addCriterion("invoiceid >", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidGreaterThanOrEqualTo(Long value) {
            addCriterion("invoiceid >=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThan(Long value) {
            addCriterion("invoiceid <", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidLessThanOrEqualTo(Long value) {
            addCriterion("invoiceid <=", value, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidIn(List<Long> values) {
            addCriterion("invoiceid in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotIn(List<Long> values) {
            addCriterion("invoiceid not in", values, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidBetween(Long value1, Long value2) {
            addCriterion("invoiceid between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoiceidNotBetween(Long value1, Long value2) {
            addCriterion("invoiceid not between", value1, value2, "invoiceid");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceno is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceno is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceno =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceno <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceno >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceno >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceno <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceno <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceno like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceno not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceno in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceno not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceno between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceno not between", value1, value2, "invoiceno");
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

        public Criteria andSrcTypeIsNull() {
            addCriterion("src_type is null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIsNotNull() {
            addCriterion("src_type is not null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeEqualTo(String value) {
            addCriterion("src_type =", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotEqualTo(String value) {
            addCriterion("src_type <>", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThan(String value) {
            addCriterion("src_type >", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThanOrEqualTo(String value) {
            addCriterion("src_type >=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThan(String value) {
            addCriterion("src_type <", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThanOrEqualTo(String value) {
            addCriterion("src_type <=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLike(String value) {
            addCriterion("src_type like", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotLike(String value) {
            addCriterion("src_type not like", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIn(List<String> values) {
            addCriterion("src_type in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotIn(List<String> values) {
            addCriterion("src_type not in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeBetween(String value1, String value2) {
            addCriterion("src_type between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotBetween(String value1, String value2) {
            addCriterion("src_type not between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("inserttime not between", value1, value2, "inserttime");
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
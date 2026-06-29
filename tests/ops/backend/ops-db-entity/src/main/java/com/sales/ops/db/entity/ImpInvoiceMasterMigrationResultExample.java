package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpInvoiceMasterMigrationResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ImpInvoiceMasterMigrationResultExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNull() {
            addCriterion("InvoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("InvoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("InvoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("InvoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("InvoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("InvoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("InvoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("InvoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("InvoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("InvoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("InvoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("SupplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("SupplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("SupplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("SupplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("SupplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("SupplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("SupplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("SupplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("SupplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("SupplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("SupplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("SupplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("SupplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("SupplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("Result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("Result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(Boolean value) {
            addCriterion("Result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(Boolean value) {
            addCriterion("Result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(Boolean value) {
            addCriterion("Result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(Boolean value) {
            addCriterion("Result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(Boolean value) {
            addCriterion("Result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(Boolean value) {
            addCriterion("Result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<Boolean> values) {
            addCriterion("Result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<Boolean> values) {
            addCriterion("Result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(Boolean value1, Boolean value2) {
            addCriterion("Result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(Boolean value1, Boolean value2) {
            addCriterion("Result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("Msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("Msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("Msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("Msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("Msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("Msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("Msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("Msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("Msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("Msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("Msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("Msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("Msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("Msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("Updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("Updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("Updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("Updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("Updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("Updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("Updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("Updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("Updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("Updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("Updatetime not between", value1, value2, "updatetime");
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
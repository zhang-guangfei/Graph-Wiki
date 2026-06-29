package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class OpsPurchaseErrorLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPurchaseErrorLogExample() {
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

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("itemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("itemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Integer value) {
            addCriterion("itemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Integer value) {
            addCriterion("itemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Integer value) {
            addCriterion("itemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Integer value) {
            addCriterion("itemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Integer value) {
            addCriterion("itemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Integer> values) {
            addCriterion("itemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Integer> values) {
            addCriterion("itemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Integer value1, Integer value2) {
            addCriterion("itemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("itemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNull() {
            addCriterion("splitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNotNull() {
            addCriterion("splitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoEqualTo(Integer value) {
            addCriterion("splitItemNo =", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotEqualTo(Integer value) {
            addCriterion("splitItemNo <>", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThan(Integer value) {
            addCriterion("splitItemNo >", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo >=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThan(Integer value) {
            addCriterion("splitItemNo <", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo <=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIn(List<Integer> values) {
            addCriterion("splitItemNo in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotIn(List<Integer> values) {
            addCriterion("splitItemNo not in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo not between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andMergeflagIsNull() {
            addCriterion("mergeflag is null");
            return (Criteria) this;
        }

        public Criteria andMergeflagIsNotNull() {
            addCriterion("mergeflag is not null");
            return (Criteria) this;
        }

        public Criteria andMergeflagEqualTo(Boolean value) {
            addCriterion("mergeflag =", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotEqualTo(Boolean value) {
            addCriterion("mergeflag <>", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagGreaterThan(Boolean value) {
            addCriterion("mergeflag >", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mergeflag >=", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagLessThan(Boolean value) {
            addCriterion("mergeflag <", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagLessThanOrEqualTo(Boolean value) {
            addCriterion("mergeflag <=", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagIn(List<Boolean> values) {
            addCriterion("mergeflag in", values, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotIn(List<Boolean> values) {
            addCriterion("mergeflag not in", values, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagBetween(Boolean value1, Boolean value2) {
            addCriterion("mergeflag between", value1, value2, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mergeflag not between", value1, value2, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andSourcetypeIsNull() {
            addCriterion("sourceType is null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeIsNotNull() {
            addCriterion("sourceType is not null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeEqualTo(String value) {
            addCriterion("sourceType =", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotEqualTo(String value) {
            addCriterion("sourceType <>", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeGreaterThan(String value) {
            addCriterion("sourceType >", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeGreaterThanOrEqualTo(String value) {
            addCriterion("sourceType >=", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLessThan(String value) {
            addCriterion("sourceType <", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLessThanOrEqualTo(String value) {
            addCriterion("sourceType <=", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeLike(String value) {
            addCriterion("sourceType like", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotLike(String value) {
            addCriterion("sourceType not like", value, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeIn(List<String> values) {
            addCriterion("sourceType in", values, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotIn(List<String> values) {
            addCriterion("sourceType not in", values, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeBetween(String value1, String value2) {
            addCriterion("sourceType between", value1, value2, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andSourcetypeNotBetween(String value1, String value2) {
            addCriterion("sourceType not between", value1, value2, "sourcetype");
            return (Criteria) this;
        }

        public Criteria andErrorcodeIsNull() {
            addCriterion("errorcode is null");
            return (Criteria) this;
        }

        public Criteria andErrorcodeIsNotNull() {
            addCriterion("errorcode is not null");
            return (Criteria) this;
        }

        public Criteria andErrorcodeEqualTo(String value) {
            addCriterion("errorcode =", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeNotEqualTo(String value) {
            addCriterion("errorcode <>", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeGreaterThan(String value) {
            addCriterion("errorcode >", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeGreaterThanOrEqualTo(String value) {
            addCriterion("errorcode >=", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeLessThan(String value) {
            addCriterion("errorcode <", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeLessThanOrEqualTo(String value) {
            addCriterion("errorcode <=", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeLike(String value) {
            addCriterion("errorcode like", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeNotLike(String value) {
            addCriterion("errorcode not like", value, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeIn(List<String> values) {
            addCriterion("errorcode in", values, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeNotIn(List<String> values) {
            addCriterion("errorcode not in", values, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeBetween(String value1, String value2) {
            addCriterion("errorcode between", value1, value2, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrorcodeNotBetween(String value1, String value2) {
            addCriterion("errorcode not between", value1, value2, "errorcode");
            return (Criteria) this;
        }

        public Criteria andErrormsgIsNull() {
            addCriterion("errormsg is null");
            return (Criteria) this;
        }

        public Criteria andErrormsgIsNotNull() {
            addCriterion("errormsg is not null");
            return (Criteria) this;
        }

        public Criteria andErrormsgEqualTo(String value) {
            addCriterion("errormsg =", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotEqualTo(String value) {
            addCriterion("errormsg <>", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgGreaterThan(String value) {
            addCriterion("errormsg >", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgGreaterThanOrEqualTo(String value) {
            addCriterion("errormsg >=", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLessThan(String value) {
            addCriterion("errormsg <", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLessThanOrEqualTo(String value) {
            addCriterion("errormsg <=", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLike(String value) {
            addCriterion("errormsg like", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotLike(String value) {
            addCriterion("errormsg not like", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgIn(List<String> values) {
            addCriterion("errormsg in", values, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotIn(List<String> values) {
            addCriterion("errormsg not in", values, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgBetween(String value1, String value2) {
            addCriterion("errormsg between", value1, value2, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotBetween(String value1, String value2) {
            addCriterion("errormsg not between", value1, value2, "errormsg");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("operator not between", value1, value2, "operator");
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
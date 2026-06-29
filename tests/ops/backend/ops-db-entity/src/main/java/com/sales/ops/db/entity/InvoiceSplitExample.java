package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceSplitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvoiceSplitExample() {
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

        public Criteria andOriginalInvoiceNoIsNull() {
            addCriterion("original_invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoIsNotNull() {
            addCriterion("original_invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoEqualTo(String value) {
            addCriterion("original_invoice_no =", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoNotEqualTo(String value) {
            addCriterion("original_invoice_no <>", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoGreaterThan(String value) {
            addCriterion("original_invoice_no >", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("original_invoice_no >=", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoLessThan(String value) {
            addCriterion("original_invoice_no <", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("original_invoice_no <=", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoLike(String value) {
            addCriterion("original_invoice_no like", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoNotLike(String value) {
            addCriterion("original_invoice_no not like", value, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoIn(List<String> values) {
            addCriterion("original_invoice_no in", values, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoNotIn(List<String> values) {
            addCriterion("original_invoice_no not in", values, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoBetween(String value1, String value2) {
            addCriterion("original_invoice_no between", value1, value2, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andOriginalInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("original_invoice_no not between", value1, value2, "originalInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdIsNull() {
            addCriterion("merge_invoice_id is null");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdIsNotNull() {
            addCriterion("merge_invoice_id is not null");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdEqualTo(Long value) {
            addCriterion("merge_invoice_id =", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdNotEqualTo(Long value) {
            addCriterion("merge_invoice_id <>", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdGreaterThan(Long value) {
            addCriterion("merge_invoice_id >", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("merge_invoice_id >=", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdLessThan(Long value) {
            addCriterion("merge_invoice_id <", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdLessThanOrEqualTo(Long value) {
            addCriterion("merge_invoice_id <=", value, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdIn(List<Long> values) {
            addCriterion("merge_invoice_id in", values, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdNotIn(List<Long> values) {
            addCriterion("merge_invoice_id not in", values, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdBetween(Long value1, Long value2) {
            addCriterion("merge_invoice_id between", value1, value2, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andMergeInvoiceIdNotBetween(Long value1, Long value2) {
            addCriterion("merge_invoice_id not between", value1, value2, "mergeInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdIsNull() {
            addCriterion("split_invoice_id is null");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdIsNotNull() {
            addCriterion("split_invoice_id is not null");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdEqualTo(Long value) {
            addCriterion("split_invoice_id =", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdNotEqualTo(Long value) {
            addCriterion("split_invoice_id <>", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdGreaterThan(Long value) {
            addCriterion("split_invoice_id >", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("split_invoice_id >=", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdLessThan(Long value) {
            addCriterion("split_invoice_id <", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdLessThanOrEqualTo(Long value) {
            addCriterion("split_invoice_id <=", value, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdIn(List<Long> values) {
            addCriterion("split_invoice_id in", values, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdNotIn(List<Long> values) {
            addCriterion("split_invoice_id not in", values, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdBetween(Long value1, Long value2) {
            addCriterion("split_invoice_id between", value1, value2, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceIdNotBetween(Long value1, Long value2) {
            addCriterion("split_invoice_id not between", value1, value2, "splitInvoiceId");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoIsNull() {
            addCriterion("split_invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoIsNotNull() {
            addCriterion("split_invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoEqualTo(String value) {
            addCriterion("split_invoice_no =", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoNotEqualTo(String value) {
            addCriterion("split_invoice_no <>", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoGreaterThan(String value) {
            addCriterion("split_invoice_no >", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("split_invoice_no >=", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoLessThan(String value) {
            addCriterion("split_invoice_no <", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("split_invoice_no <=", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoLike(String value) {
            addCriterion("split_invoice_no like", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoNotLike(String value) {
            addCriterion("split_invoice_no not like", value, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoIn(List<String> values) {
            addCriterion("split_invoice_no in", values, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoNotIn(List<String> values) {
            addCriterion("split_invoice_no not in", values, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoBetween(String value1, String value2) {
            addCriterion("split_invoice_no between", value1, value2, "splitInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andSplitInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("split_invoice_no not between", value1, value2, "splitInvoiceNo");
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
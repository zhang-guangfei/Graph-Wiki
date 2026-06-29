package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInventoryAdjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInventoryAdjExample() {
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

        public Criteria andAdjDateIsNull() {
            addCriterion("adj_date is null");
            return (Criteria) this;
        }

        public Criteria andAdjDateIsNotNull() {
            addCriterion("adj_date is not null");
            return (Criteria) this;
        }

        public Criteria andAdjDateEqualTo(Date value) {
            addCriterion("adj_date =", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateNotEqualTo(Date value) {
            addCriterion("adj_date <>", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateGreaterThan(Date value) {
            addCriterion("adj_date >", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateGreaterThanOrEqualTo(Date value) {
            addCriterion("adj_date >=", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateLessThan(Date value) {
            addCriterion("adj_date <", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateLessThanOrEqualTo(Date value) {
            addCriterion("adj_date <=", value, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateIn(List<Date> values) {
            addCriterion("adj_date in", values, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateNotIn(List<Date> values) {
            addCriterion("adj_date not in", values, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateBetween(Date value1, Date value2) {
            addCriterion("adj_date between", value1, value2, "adjDate");
            return (Criteria) this;
        }

        public Criteria andAdjDateNotBetween(Date value1, Date value2) {
            addCriterion("adj_date not between", value1, value2, "adjDate");
            return (Criteria) this;
        }

        public Criteria andDiffIdIsNull() {
            addCriterion("diff_id is null");
            return (Criteria) this;
        }

        public Criteria andDiffIdIsNotNull() {
            addCriterion("diff_id is not null");
            return (Criteria) this;
        }

        public Criteria andDiffIdEqualTo(Long value) {
            addCriterion("diff_id =", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdNotEqualTo(Long value) {
            addCriterion("diff_id <>", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdGreaterThan(Long value) {
            addCriterion("diff_id >", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdGreaterThanOrEqualTo(Long value) {
            addCriterion("diff_id >=", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdLessThan(Long value) {
            addCriterion("diff_id <", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdLessThanOrEqualTo(Long value) {
            addCriterion("diff_id <=", value, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdIn(List<Long> values) {
            addCriterion("diff_id in", values, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdNotIn(List<Long> values) {
            addCriterion("diff_id not in", values, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdBetween(Long value1, Long value2) {
            addCriterion("diff_id between", value1, value2, "diffId");
            return (Criteria) this;
        }

        public Criteria andDiffIdNotBetween(Long value1, Long value2) {
            addCriterion("diff_id not between", value1, value2, "diffId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIsNull() {
            addCriterion("inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIsNotNull() {
            addCriterion("inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryIdEqualTo(Long value) {
            addCriterion("inventory_id =", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotEqualTo(Long value) {
            addCriterion("inventory_id <>", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThan(Long value) {
            addCriterion("inventory_id >", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_id >=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThan(Long value) {
            addCriterion("inventory_id <", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_id <=", value, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdIn(List<Long> values) {
            addCriterion("inventory_id in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotIn(List<Long> values) {
            addCriterion("inventory_id not in", values, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdBetween(Long value1, Long value2) {
            addCriterion("inventory_id between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_id not between", value1, value2, "inventoryId");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeIsNull() {
            addCriterion("quantity_before is null");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeIsNotNull() {
            addCriterion("quantity_before is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeEqualTo(Integer value) {
            addCriterion("quantity_before =", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeNotEqualTo(Integer value) {
            addCriterion("quantity_before <>", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeGreaterThan(Integer value) {
            addCriterion("quantity_before >", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity_before >=", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeLessThan(Integer value) {
            addCriterion("quantity_before <", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeLessThanOrEqualTo(Integer value) {
            addCriterion("quantity_before <=", value, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeIn(List<Integer> values) {
            addCriterion("quantity_before in", values, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeNotIn(List<Integer> values) {
            addCriterion("quantity_before not in", values, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeBetween(Integer value1, Integer value2) {
            addCriterion("quantity_before between", value1, value2, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity_before not between", value1, value2, "quantityBefore");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjIsNull() {
            addCriterion("quantity_adj is null");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjIsNotNull() {
            addCriterion("quantity_adj is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjEqualTo(Integer value) {
            addCriterion("quantity_adj =", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjNotEqualTo(Integer value) {
            addCriterion("quantity_adj <>", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjGreaterThan(Integer value) {
            addCriterion("quantity_adj >", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity_adj >=", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjLessThan(Integer value) {
            addCriterion("quantity_adj <", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjLessThanOrEqualTo(Integer value) {
            addCriterion("quantity_adj <=", value, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjIn(List<Integer> values) {
            addCriterion("quantity_adj in", values, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjNotIn(List<Integer> values) {
            addCriterion("quantity_adj not in", values, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjBetween(Integer value1, Integer value2) {
            addCriterion("quantity_adj between", value1, value2, "quantityAdj");
            return (Criteria) this;
        }

        public Criteria andQuantityAdjNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity_adj not between", value1, value2, "quantityAdj");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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
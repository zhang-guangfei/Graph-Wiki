package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsRoItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsRoItemExample() {
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

        public Criteria andRoItemIsNull() {
            addCriterion("ro_item is null");
            return (Criteria) this;
        }

        public Criteria andRoItemIsNotNull() {
            addCriterion("ro_item is not null");
            return (Criteria) this;
        }

        public Criteria andRoItemEqualTo(Integer value) {
            addCriterion("ro_item =", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemNotEqualTo(Integer value) {
            addCriterion("ro_item <>", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemGreaterThan(Integer value) {
            addCriterion("ro_item >", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("ro_item >=", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemLessThan(Integer value) {
            addCriterion("ro_item <", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemLessThanOrEqualTo(Integer value) {
            addCriterion("ro_item <=", value, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemIn(List<Integer> values) {
            addCriterion("ro_item in", values, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemNotIn(List<Integer> values) {
            addCriterion("ro_item not in", values, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemBetween(Integer value1, Integer value2) {
            addCriterion("ro_item between", value1, value2, "roItem");
            return (Criteria) this;
        }

        public Criteria andRoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("ro_item not between", value1, value2, "roItem");
            return (Criteria) this;
        }

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Integer value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Integer value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Integer value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Integer value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Integer value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Integer> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Integer> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Integer value1, Integer value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andRecQtyIsNull() {
            addCriterion("rec_qty is null");
            return (Criteria) this;
        }

        public Criteria andRecQtyIsNotNull() {
            addCriterion("rec_qty is not null");
            return (Criteria) this;
        }

        public Criteria andRecQtyEqualTo(Integer value) {
            addCriterion("rec_qty =", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyNotEqualTo(Integer value) {
            addCriterion("rec_qty <>", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyGreaterThan(Integer value) {
            addCriterion("rec_qty >", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("rec_qty >=", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyLessThan(Integer value) {
            addCriterion("rec_qty <", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyLessThanOrEqualTo(Integer value) {
            addCriterion("rec_qty <=", value, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyIn(List<Integer> values) {
            addCriterion("rec_qty in", values, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyNotIn(List<Integer> values) {
            addCriterion("rec_qty not in", values, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyBetween(Integer value1, Integer value2) {
            addCriterion("rec_qty between", value1, value2, "recQty");
            return (Criteria) this;
        }

        public Criteria andRecQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("rec_qty not between", value1, value2, "recQty");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelno is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelno is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelno =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelno <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelno >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelno >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelno <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelno <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelno like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelno not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelno in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelno not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelno between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelno not between", value1, value2, "modelno");
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

        public Criteria andFromInventoryIdIsNull() {
            addCriterion("from_inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIsNotNull() {
            addCriterion("from_inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdEqualTo(Long value) {
            addCriterion("from_inventory_id =", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotEqualTo(Long value) {
            addCriterion("from_inventory_id <>", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThan(Long value) {
            addCriterion("from_inventory_id >", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id >=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThan(Long value) {
            addCriterion("from_inventory_id <", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id <=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIn(List<Long> values) {
            addCriterion("from_inventory_id in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotIn(List<Long> values) {
            addCriterion("from_inventory_id not in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id not between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeIsNull() {
            addCriterion("from_inventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeIsNotNull() {
            addCriterion("from_inventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeEqualTo(String value) {
            addCriterion("from_inventory_table_type =", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeNotEqualTo(String value) {
            addCriterion("from_inventory_table_type <>", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeGreaterThan(String value) {
            addCriterion("from_inventory_table_type >", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("from_inventory_table_type >=", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeLessThan(String value) {
            addCriterion("from_inventory_table_type <", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("from_inventory_table_type <=", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeLike(String value) {
            addCriterion("from_inventory_table_type like", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeNotLike(String value) {
            addCriterion("from_inventory_table_type not like", value, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeIn(List<String> values) {
            addCriterion("from_inventory_table_type in", values, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeNotIn(List<String> values) {
            addCriterion("from_inventory_table_type not in", values, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeBetween(String value1, String value2) {
            addCriterion("from_inventory_table_type between", value1, value2, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFromInventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("from_inventory_table_type not between", value1, value2, "fromInventoryTableType");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNull() {
            addCriterion("qa_status is null");
            return (Criteria) this;
        }

        public Criteria andQaStatusIsNotNull() {
            addCriterion("qa_status is not null");
            return (Criteria) this;
        }

        public Criteria andQaStatusEqualTo(String value) {
            addCriterion("qa_status =", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotEqualTo(String value) {
            addCriterion("qa_status <>", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThan(String value) {
            addCriterion("qa_status >", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusGreaterThanOrEqualTo(String value) {
            addCriterion("qa_status >=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThan(String value) {
            addCriterion("qa_status <", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLessThanOrEqualTo(String value) {
            addCriterion("qa_status <=", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusLike(String value) {
            addCriterion("qa_status like", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotLike(String value) {
            addCriterion("qa_status not like", value, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusIn(List<String> values) {
            addCriterion("qa_status in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotIn(List<String> values) {
            addCriterion("qa_status not in", values, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusBetween(String value1, String value2) {
            addCriterion("qa_status between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andQaStatusNotBetween(String value1, String value2) {
            addCriterion("qa_status not between", value1, value2, "qaStatus");
            return (Criteria) this;
        }

        public Criteria andGreenCodeIsNull() {
            addCriterion("green_code is null");
            return (Criteria) this;
        }

        public Criteria andGreenCodeIsNotNull() {
            addCriterion("green_code is not null");
            return (Criteria) this;
        }

        public Criteria andGreenCodeEqualTo(String value) {
            addCriterion("green_code =", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotEqualTo(String value) {
            addCriterion("green_code <>", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeGreaterThan(String value) {
            addCriterion("green_code >", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeGreaterThanOrEqualTo(String value) {
            addCriterion("green_code >=", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLessThan(String value) {
            addCriterion("green_code <", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLessThanOrEqualTo(String value) {
            addCriterion("green_code <=", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeLike(String value) {
            addCriterion("green_code like", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotLike(String value) {
            addCriterion("green_code not like", value, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeIn(List<String> values) {
            addCriterion("green_code in", values, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotIn(List<String> values) {
            addCriterion("green_code not in", values, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeBetween(String value1, String value2) {
            addCriterion("green_code between", value1, value2, "greenCode");
            return (Criteria) this;
        }

        public Criteria andGreenCodeNotBetween(String value1, String value2) {
            addCriterion("green_code not between", value1, value2, "greenCode");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNull() {
            addCriterion("netWeight is null");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNotNull() {
            addCriterion("netWeight is not null");
            return (Criteria) this;
        }

        public Criteria andNetweightEqualTo(BigDecimal value) {
            addCriterion("netWeight =", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotEqualTo(BigDecimal value) {
            addCriterion("netWeight <>", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThan(BigDecimal value) {
            addCriterion("netWeight >", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("netWeight >=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThan(BigDecimal value) {
            addCriterion("netWeight <", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("netWeight <=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightIn(List<BigDecimal> values) {
            addCriterion("netWeight in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotIn(List<BigDecimal> values) {
            addCriterion("netWeight not in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netWeight between", value1, value2, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netWeight not between", value1, value2, "netweight");
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
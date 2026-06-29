package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInqbDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInqbDetailExample() {
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

        public Criteria andInqbApplyNoIsNull() {
            addCriterion("inqb_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIsNotNull() {
            addCriterion("inqb_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoEqualTo(String value) {
            addCriterion("inqb_apply_no =", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotEqualTo(String value) {
            addCriterion("inqb_apply_no <>", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThan(String value) {
            addCriterion("inqb_apply_no >", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no >=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThan(String value) {
            addCriterion("inqb_apply_no <", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLessThanOrEqualTo(String value) {
            addCriterion("inqb_apply_no <=", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoLike(String value) {
            addCriterion("inqb_apply_no like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotLike(String value) {
            addCriterion("inqb_apply_no not like", value, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoIn(List<String> values) {
            addCriterion("inqb_apply_no in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotIn(List<String> values) {
            addCriterion("inqb_apply_no not in", values, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoBetween(String value1, String value2) {
            addCriterion("inqb_apply_no between", value1, value2, "inqbApplyNo");
            return (Criteria) this;
        }

        public Criteria andInqbApplyNoNotBetween(String value1, String value2) {
            addCriterion("inqb_apply_no not between", value1, value2, "inqbApplyNo");
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

        public Criteria andSplitTypeIsNull() {
            addCriterion("split_type is null");
            return (Criteria) this;
        }

        public Criteria andSplitTypeIsNotNull() {
            addCriterion("split_type is not null");
            return (Criteria) this;
        }

        public Criteria andSplitTypeEqualTo(String value) {
            addCriterion("split_type =", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotEqualTo(String value) {
            addCriterion("split_type <>", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeGreaterThan(String value) {
            addCriterion("split_type >", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("split_type >=", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLessThan(String value) {
            addCriterion("split_type <", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLessThanOrEqualTo(String value) {
            addCriterion("split_type <=", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeLike(String value) {
            addCriterion("split_type like", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotLike(String value) {
            addCriterion("split_type not like", value, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeIn(List<String> values) {
            addCriterion("split_type in", values, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotIn(List<String> values) {
            addCriterion("split_type not in", values, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeBetween(String value1, String value2) {
            addCriterion("split_type between", value1, value2, "splitType");
            return (Criteria) this;
        }

        public Criteria andSplitTypeNotBetween(String value1, String value2) {
            addCriterion("split_type not between", value1, value2, "splitType");
            return (Criteria) this;
        }

        public Criteria andTaskNoIsNull() {
            addCriterion("task_no is null");
            return (Criteria) this;
        }

        public Criteria andTaskNoIsNotNull() {
            addCriterion("task_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNoEqualTo(String value) {
            addCriterion("task_no =", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotEqualTo(String value) {
            addCriterion("task_no <>", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoGreaterThan(String value) {
            addCriterion("task_no >", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoGreaterThanOrEqualTo(String value) {
            addCriterion("task_no >=", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLessThan(String value) {
            addCriterion("task_no <", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLessThanOrEqualTo(String value) {
            addCriterion("task_no <=", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoLike(String value) {
            addCriterion("task_no like", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotLike(String value) {
            addCriterion("task_no not like", value, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoIn(List<String> values) {
            addCriterion("task_no in", values, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotIn(List<String> values) {
            addCriterion("task_no not in", values, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoBetween(String value1, String value2) {
            addCriterion("task_no between", value1, value2, "taskNo");
            return (Criteria) this;
        }

        public Criteria andTaskNoNotBetween(String value1, String value2) {
            addCriterion("task_no not between", value1, value2, "taskNo");
            return (Criteria) this;
        }

        public Criteria andBomIdIsNull() {
            addCriterion("bom_id is null");
            return (Criteria) this;
        }

        public Criteria andBomIdIsNotNull() {
            addCriterion("bom_id is not null");
            return (Criteria) this;
        }

        public Criteria andBomIdEqualTo(String value) {
            addCriterion("bom_id =", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdNotEqualTo(String value) {
            addCriterion("bom_id <>", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdGreaterThan(String value) {
            addCriterion("bom_id >", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdGreaterThanOrEqualTo(String value) {
            addCriterion("bom_id >=", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdLessThan(String value) {
            addCriterion("bom_id <", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdLessThanOrEqualTo(String value) {
            addCriterion("bom_id <=", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdLike(String value) {
            addCriterion("bom_id like", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdNotLike(String value) {
            addCriterion("bom_id not like", value, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdIn(List<String> values) {
            addCriterion("bom_id in", values, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdNotIn(List<String> values) {
            addCriterion("bom_id not in", values, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdBetween(String value1, String value2) {
            addCriterion("bom_id between", value1, value2, "bomId");
            return (Criteria) this;
        }

        public Criteria andBomIdNotBetween(String value1, String value2) {
            addCriterion("bom_id not between", value1, value2, "bomId");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNull() {
            addCriterion("stock_code is null");
            return (Criteria) this;
        }

        public Criteria andStockCodeIsNotNull() {
            addCriterion("stock_code is not null");
            return (Criteria) this;
        }

        public Criteria andStockCodeEqualTo(String value) {
            addCriterion("stock_code =", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotEqualTo(String value) {
            addCriterion("stock_code <>", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThan(String value) {
            addCriterion("stock_code >", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_code >=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThan(String value) {
            addCriterion("stock_code <", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLessThanOrEqualTo(String value) {
            addCriterion("stock_code <=", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeLike(String value) {
            addCriterion("stock_code like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotLike(String value) {
            addCriterion("stock_code not like", value, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeIn(List<String> values) {
            addCriterion("stock_code in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotIn(List<String> values) {
            addCriterion("stock_code not in", values, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeBetween(String value1, String value2) {
            addCriterion("stock_code between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andStockCodeNotBetween(String value1, String value2) {
            addCriterion("stock_code not between", value1, value2, "stockCode");
            return (Criteria) this;
        }

        public Criteria andHandleResultIsNull() {
            addCriterion("handle_result is null");
            return (Criteria) this;
        }

        public Criteria andHandleResultIsNotNull() {
            addCriterion("handle_result is not null");
            return (Criteria) this;
        }

        public Criteria andHandleResultEqualTo(String value) {
            addCriterion("handle_result =", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotEqualTo(String value) {
            addCriterion("handle_result <>", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultGreaterThan(String value) {
            addCriterion("handle_result >", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultGreaterThanOrEqualTo(String value) {
            addCriterion("handle_result >=", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLessThan(String value) {
            addCriterion("handle_result <", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLessThanOrEqualTo(String value) {
            addCriterion("handle_result <=", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLike(String value) {
            addCriterion("handle_result like", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotLike(String value) {
            addCriterion("handle_result not like", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultIn(List<String> values) {
            addCriterion("handle_result in", values, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotIn(List<String> values) {
            addCriterion("handle_result not in", values, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultBetween(String value1, String value2) {
            addCriterion("handle_result between", value1, value2, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotBetween(String value1, String value2) {
            addCriterion("handle_result not between", value1, value2, "handleResult");
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

        public Criteria andUseQtyIsNull() {
            addCriterion("use_qty is null");
            return (Criteria) this;
        }

        public Criteria andUseQtyIsNotNull() {
            addCriterion("use_qty is not null");
            return (Criteria) this;
        }

        public Criteria andUseQtyEqualTo(Integer value) {
            addCriterion("use_qty =", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotEqualTo(Integer value) {
            addCriterion("use_qty <>", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThan(Integer value) {
            addCriterion("use_qty >", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("use_qty >=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThan(Integer value) {
            addCriterion("use_qty <", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyLessThanOrEqualTo(Integer value) {
            addCriterion("use_qty <=", value, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyIn(List<Integer> values) {
            addCriterion("use_qty in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotIn(List<Integer> values) {
            addCriterion("use_qty not in", values, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyBetween(Integer value1, Integer value2) {
            addCriterion("use_qty between", value1, value2, "useQty");
            return (Criteria) this;
        }

        public Criteria andUseQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("use_qty not between", value1, value2, "useQty");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIsNull() {
            addCriterion("expected_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIsNotNull() {
            addCriterion("expected_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateEqualTo(Integer value) {
            addCriterion("expected_delivery_date =", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotEqualTo(Integer value) {
            addCriterion("expected_delivery_date <>", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateGreaterThan(Integer value) {
            addCriterion("expected_delivery_date >", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("expected_delivery_date >=", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateLessThan(Integer value) {
            addCriterion("expected_delivery_date <", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateLessThanOrEqualTo(Integer value) {
            addCriterion("expected_delivery_date <=", value, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateIn(List<Integer> values) {
            addCriterion("expected_delivery_date in", values, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotIn(List<Integer> values) {
            addCriterion("expected_delivery_date not in", values, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateBetween(Integer value1, Integer value2) {
            addCriterion("expected_delivery_date between", value1, value2, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpectedDeliveryDateNotBetween(Integer value1, Integer value2) {
            addCriterion("expected_delivery_date not between", value1, value2, "expectedDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNull() {
            addCriterion("end_user is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIsNotNull() {
            addCriterion("end_user is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserEqualTo(String value) {
            addCriterion("end_user =", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotEqualTo(String value) {
            addCriterion("end_user <>", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThan(String value) {
            addCriterion("end_user >", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserGreaterThanOrEqualTo(String value) {
            addCriterion("end_user >=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThan(String value) {
            addCriterion("end_user <", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLessThanOrEqualTo(String value) {
            addCriterion("end_user <=", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserLike(String value) {
            addCriterion("end_user like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotLike(String value) {
            addCriterion("end_user not like", value, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserIn(List<String> values) {
            addCriterion("end_user in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotIn(List<String> values) {
            addCriterion("end_user not in", values, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserBetween(String value1, String value2) {
            addCriterion("end_user between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andEndUserNotBetween(String value1, String value2) {
            addCriterion("end_user not between", value1, value2, "endUser");
            return (Criteria) this;
        }

        public Criteria andUserDeptIsNull() {
            addCriterion("user_dept is null");
            return (Criteria) this;
        }

        public Criteria andUserDeptIsNotNull() {
            addCriterion("user_dept is not null");
            return (Criteria) this;
        }

        public Criteria andUserDeptEqualTo(String value) {
            addCriterion("user_dept =", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotEqualTo(String value) {
            addCriterion("user_dept <>", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptGreaterThan(String value) {
            addCriterion("user_dept >", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptGreaterThanOrEqualTo(String value) {
            addCriterion("user_dept >=", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLessThan(String value) {
            addCriterion("user_dept <", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLessThanOrEqualTo(String value) {
            addCriterion("user_dept <=", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptLike(String value) {
            addCriterion("user_dept like", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotLike(String value) {
            addCriterion("user_dept not like", value, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptIn(List<String> values) {
            addCriterion("user_dept in", values, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotIn(List<String> values) {
            addCriterion("user_dept not in", values, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptBetween(String value1, String value2) {
            addCriterion("user_dept between", value1, value2, "userDept");
            return (Criteria) this;
        }

        public Criteria andUserDeptNotBetween(String value1, String value2) {
            addCriterion("user_dept not between", value1, value2, "userDept");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNull() {
            addCriterion("expiration_date is null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNotNull() {
            addCriterion("expiration_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateEqualTo(Date value) {
            addCriterion("expiration_date =", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotEqualTo(Date value) {
            addCriterion("expiration_date <>", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThan(Date value) {
            addCriterion("expiration_date >", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("expiration_date >=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThan(Date value) {
            addCriterion("expiration_date <", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThanOrEqualTo(Date value) {
            addCriterion("expiration_date <=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIn(List<Date> values) {
            addCriterion("expiration_date in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotIn(List<Date> values) {
            addCriterion("expiration_date not in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateBetween(Date value1, Date value2) {
            addCriterion("expiration_date between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotBetween(Date value1, Date value2) {
            addCriterion("expiration_date not between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNull() {
            addCriterion("reply_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIsNotNull() {
            addCriterion("reply_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeptEqualTo(String value) {
            addCriterion("reply_dept =", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotEqualTo(String value) {
            addCriterion("reply_dept <>", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThan(String value) {
            addCriterion("reply_dept >", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_dept >=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThan(String value) {
            addCriterion("reply_dept <", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_dept <=", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptLike(String value) {
            addCriterion("reply_dept like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotLike(String value) {
            addCriterion("reply_dept not like", value, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptIn(List<String> values) {
            addCriterion("reply_dept in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotIn(List<String> values) {
            addCriterion("reply_dept not in", values, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptBetween(String value1, String value2) {
            addCriterion("reply_dept between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplyDeptNotBetween(String value1, String value2) {
            addCriterion("reply_dept not between", value1, value2, "replyDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIsNull() {
            addCriterion("reply_supplier_dept is null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIsNotNull() {
            addCriterion("reply_supplier_dept is not null");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptEqualTo(String value) {
            addCriterion("reply_supplier_dept =", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotEqualTo(String value) {
            addCriterion("reply_supplier_dept <>", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThan(String value) {
            addCriterion("reply_supplier_dept >", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptGreaterThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept >=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThan(String value) {
            addCriterion("reply_supplier_dept <", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLessThanOrEqualTo(String value) {
            addCriterion("reply_supplier_dept <=", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptLike(String value) {
            addCriterion("reply_supplier_dept like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotLike(String value) {
            addCriterion("reply_supplier_dept not like", value, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptIn(List<String> values) {
            addCriterion("reply_supplier_dept in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotIn(List<String> values) {
            addCriterion("reply_supplier_dept not in", values, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplySupplierDeptNotBetween(String value1, String value2) {
            addCriterion("reply_supplier_dept not between", value1, value2, "replySupplierDept");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNull() {
            addCriterion("reply_no is null");
            return (Criteria) this;
        }

        public Criteria andReplyNoIsNotNull() {
            addCriterion("reply_no is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNoEqualTo(String value) {
            addCriterion("reply_no =", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotEqualTo(String value) {
            addCriterion("reply_no <>", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThan(String value) {
            addCriterion("reply_no >", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("reply_no >=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThan(String value) {
            addCriterion("reply_no <", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLessThanOrEqualTo(String value) {
            addCriterion("reply_no <=", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoLike(String value) {
            addCriterion("reply_no like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotLike(String value) {
            addCriterion("reply_no not like", value, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoIn(List<String> values) {
            addCriterion("reply_no in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotIn(List<String> values) {
            addCriterion("reply_no not in", values, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoBetween(String value1, String value2) {
            addCriterion("reply_no between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyNoNotBetween(String value1, String value2) {
            addCriterion("reply_no not between", value1, value2, "replyNo");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIsNull() {
            addCriterion("reply_delivery_days is null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIsNotNull() {
            addCriterion("reply_delivery_days is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysEqualTo(Integer value) {
            addCriterion("reply_delivery_days =", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotEqualTo(Integer value) {
            addCriterion("reply_delivery_days <>", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysGreaterThan(Integer value) {
            addCriterion("reply_delivery_days >", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_delivery_days >=", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysLessThan(Integer value) {
            addCriterion("reply_delivery_days <", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysLessThanOrEqualTo(Integer value) {
            addCriterion("reply_delivery_days <=", value, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysIn(List<Integer> values) {
            addCriterion("reply_delivery_days in", values, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotIn(List<Integer> values) {
            addCriterion("reply_delivery_days not in", values, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysBetween(Integer value1, Integer value2) {
            addCriterion("reply_delivery_days between", value1, value2, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyDeliveryDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_delivery_days not between", value1, value2, "replyDeliveryDays");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIsNull() {
            addCriterion("reply_accept_hl is null");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIsNotNull() {
            addCriterion("reply_accept_hl is not null");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlEqualTo(String value) {
            addCriterion("reply_accept_hl =", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotEqualTo(String value) {
            addCriterion("reply_accept_hl <>", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlGreaterThan(String value) {
            addCriterion("reply_accept_hl >", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlGreaterThanOrEqualTo(String value) {
            addCriterion("reply_accept_hl >=", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLessThan(String value) {
            addCriterion("reply_accept_hl <", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLessThanOrEqualTo(String value) {
            addCriterion("reply_accept_hl <=", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlLike(String value) {
            addCriterion("reply_accept_hl like", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotLike(String value) {
            addCriterion("reply_accept_hl not like", value, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlIn(List<String> values) {
            addCriterion("reply_accept_hl in", values, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotIn(List<String> values) {
            addCriterion("reply_accept_hl not in", values, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlBetween(String value1, String value2) {
            addCriterion("reply_accept_hl between", value1, value2, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyAcceptHlNotBetween(String value1, String value2) {
            addCriterion("reply_accept_hl not between", value1, value2, "replyAcceptHl");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNull() {
            addCriterion("reply_user is null");
            return (Criteria) this;
        }

        public Criteria andReplyUserIsNotNull() {
            addCriterion("reply_user is not null");
            return (Criteria) this;
        }

        public Criteria andReplyUserEqualTo(String value) {
            addCriterion("reply_user =", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotEqualTo(String value) {
            addCriterion("reply_user <>", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThan(String value) {
            addCriterion("reply_user >", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("reply_user >=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThan(String value) {
            addCriterion("reply_user <", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLessThanOrEqualTo(String value) {
            addCriterion("reply_user <=", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserLike(String value) {
            addCriterion("reply_user like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotLike(String value) {
            addCriterion("reply_user not like", value, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserIn(List<String> values) {
            addCriterion("reply_user in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotIn(List<String> values) {
            addCriterion("reply_user not in", values, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserBetween(String value1, String value2) {
            addCriterion("reply_user between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyUserNotBetween(String value1, String value2) {
            addCriterion("reply_user not between", value1, value2, "replyUser");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNull() {
            addCriterion("reply_time is null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNotNull() {
            addCriterion("reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeEqualTo(Date value) {
            addCriterion("reply_time =", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotEqualTo(Date value) {
            addCriterion("reply_time <>", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThan(Date value) {
            addCriterion("reply_time >", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_time >=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThan(Date value) {
            addCriterion("reply_time <", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("reply_time <=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIn(List<Date> values) {
            addCriterion("reply_time in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotIn(List<Date> values) {
            addCriterion("reply_time not in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeBetween(Date value1, Date value2) {
            addCriterion("reply_time between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("reply_time not between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIsNull() {
            addCriterion("reply_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIsNotNull() {
            addCriterion("reply_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeEqualTo(String value) {
            addCriterion("reply_reason_code =", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotEqualTo(String value) {
            addCriterion("reply_reason_code <>", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeGreaterThan(String value) {
            addCriterion("reply_reason_code >", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("reply_reason_code >=", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLessThan(String value) {
            addCriterion("reply_reason_code <", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("reply_reason_code <=", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeLike(String value) {
            addCriterion("reply_reason_code like", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotLike(String value) {
            addCriterion("reply_reason_code not like", value, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeIn(List<String> values) {
            addCriterion("reply_reason_code in", values, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotIn(List<String> values) {
            addCriterion("reply_reason_code not in", values, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeBetween(String value1, String value2) {
            addCriterion("reply_reason_code between", value1, value2, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonCodeNotBetween(String value1, String value2) {
            addCriterion("reply_reason_code not between", value1, value2, "replyReasonCode");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIsNull() {
            addCriterion("reply_reason is null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIsNotNull() {
            addCriterion("reply_reason is not null");
            return (Criteria) this;
        }

        public Criteria andReplyReasonEqualTo(String value) {
            addCriterion("reply_reason =", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotEqualTo(String value) {
            addCriterion("reply_reason <>", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonGreaterThan(String value) {
            addCriterion("reply_reason >", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reply_reason >=", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLessThan(String value) {
            addCriterion("reply_reason <", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLessThanOrEqualTo(String value) {
            addCriterion("reply_reason <=", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonLike(String value) {
            addCriterion("reply_reason like", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotLike(String value) {
            addCriterion("reply_reason not like", value, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonIn(List<String> values) {
            addCriterion("reply_reason in", values, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotIn(List<String> values) {
            addCriterion("reply_reason not in", values, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonBetween(String value1, String value2) {
            addCriterion("reply_reason between", value1, value2, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyReasonNotBetween(String value1, String value2) {
            addCriterion("reply_reason not between", value1, value2, "replyReason");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNull() {
            addCriterion("reply_remark is null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIsNotNull() {
            addCriterion("reply_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkEqualTo(String value) {
            addCriterion("reply_remark =", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotEqualTo(String value) {
            addCriterion("reply_remark <>", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThan(String value) {
            addCriterion("reply_remark >", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reply_remark >=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThan(String value) {
            addCriterion("reply_remark <", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLessThanOrEqualTo(String value) {
            addCriterion("reply_remark <=", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkLike(String value) {
            addCriterion("reply_remark like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotLike(String value) {
            addCriterion("reply_remark not like", value, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkIn(List<String> values) {
            addCriterion("reply_remark in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotIn(List<String> values) {
            addCriterion("reply_remark not in", values, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkBetween(String value1, String value2) {
            addCriterion("reply_remark between", value1, value2, "replyRemark");
            return (Criteria) this;
        }

        public Criteria andReplyRemarkNotBetween(String value1, String value2) {
            addCriterion("reply_remark not between", value1, value2, "replyRemark");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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
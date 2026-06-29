package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempDoconfirmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TempDoconfirmExample() {
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

        public Criteria andRorderNoIsNull() {
            addCriterion("rorder_no is null");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNotNull() {
            addCriterion("rorder_no is not null");
            return (Criteria) this;
        }

        public Criteria andRorderNoEqualTo(String value) {
            addCriterion("rorder_no =", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotEqualTo(String value) {
            addCriterion("rorder_no <>", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThan(String value) {
            addCriterion("rorder_no >", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_no >=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThan(String value) {
            addCriterion("rorder_no <", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThanOrEqualTo(String value) {
            addCriterion("rorder_no <=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLike(String value) {
            addCriterion("rorder_no like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotLike(String value) {
            addCriterion("rorder_no not like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoIn(List<String> values) {
            addCriterion("rorder_no in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotIn(List<String> values) {
            addCriterion("rorder_no not in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoBetween(String value1, String value2) {
            addCriterion("rorder_no between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotBetween(String value1, String value2) {
            addCriterion("rorder_no not between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNull() {
            addCriterion("rorder_item is null");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNotNull() {
            addCriterion("rorder_item is not null");
            return (Criteria) this;
        }

        public Criteria andRorderItemEqualTo(String value) {
            addCriterion("rorder_item =", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotEqualTo(String value) {
            addCriterion("rorder_item <>", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThan(String value) {
            addCriterion("rorder_item >", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_item >=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThan(String value) {
            addCriterion("rorder_item <", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThanOrEqualTo(String value) {
            addCriterion("rorder_item <=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLike(String value) {
            addCriterion("rorder_item like", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotLike(String value) {
            addCriterion("rorder_item not like", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemIn(List<String> values) {
            addCriterion("rorder_item in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotIn(List<String> values) {
            addCriterion("rorder_item not in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemBetween(String value1, String value2) {
            addCriterion("rorder_item between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotBetween(String value1, String value2) {
            addCriterion("rorder_item not between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNull() {
            addCriterion("do_id is null");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNotNull() {
            addCriterion("do_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoIdEqualTo(String value) {
            addCriterion("do_id =", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotEqualTo(String value) {
            addCriterion("do_id <>", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThan(String value) {
            addCriterion("do_id >", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThanOrEqualTo(String value) {
            addCriterion("do_id >=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThan(String value) {
            addCriterion("do_id <", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThanOrEqualTo(String value) {
            addCriterion("do_id <=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLike(String value) {
            addCriterion("do_id like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotLike(String value) {
            addCriterion("do_id not like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdIn(List<String> values) {
            addCriterion("do_id in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotIn(List<String> values) {
            addCriterion("do_id not in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdBetween(String value1, String value2) {
            addCriterion("do_id between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotBetween(String value1, String value2) {
            addCriterion("do_id not between", value1, value2, "doId");
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

        public Criteria andOpsOutQtyIsNull() {
            addCriterion("ops_out_qty is null");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyIsNotNull() {
            addCriterion("ops_out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyEqualTo(Integer value) {
            addCriterion("ops_out_qty =", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyNotEqualTo(Integer value) {
            addCriterion("ops_out_qty <>", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyGreaterThan(Integer value) {
            addCriterion("ops_out_qty >", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ops_out_qty >=", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyLessThan(Integer value) {
            addCriterion("ops_out_qty <", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyLessThanOrEqualTo(Integer value) {
            addCriterion("ops_out_qty <=", value, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyIn(List<Integer> values) {
            addCriterion("ops_out_qty in", values, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyNotIn(List<Integer> values) {
            addCriterion("ops_out_qty not in", values, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyBetween(Integer value1, Integer value2) {
            addCriterion("ops_out_qty between", value1, value2, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andOpsOutQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ops_out_qty not between", value1, value2, "opsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyIsNull() {
            addCriterion("wms_out_qty is null");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyIsNotNull() {
            addCriterion("wms_out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyEqualTo(Integer value) {
            addCriterion("wms_out_qty =", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyNotEqualTo(Integer value) {
            addCriterion("wms_out_qty <>", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyGreaterThan(Integer value) {
            addCriterion("wms_out_qty >", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("wms_out_qty >=", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyLessThan(Integer value) {
            addCriterion("wms_out_qty <", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyLessThanOrEqualTo(Integer value) {
            addCriterion("wms_out_qty <=", value, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyIn(List<Integer> values) {
            addCriterion("wms_out_qty in", values, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyNotIn(List<Integer> values) {
            addCriterion("wms_out_qty not in", values, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyBetween(Integer value1, Integer value2) {
            addCriterion("wms_out_qty between", value1, value2, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andWmsOutQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("wms_out_qty not between", value1, value2, "wmsOutQty");
            return (Criteria) this;
        }

        public Criteria andQtyDiffIsNull() {
            addCriterion("qty_diff is null");
            return (Criteria) this;
        }

        public Criteria andQtyDiffIsNotNull() {
            addCriterion("qty_diff is not null");
            return (Criteria) this;
        }

        public Criteria andQtyDiffEqualTo(Integer value) {
            addCriterion("qty_diff =", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffNotEqualTo(Integer value) {
            addCriterion("qty_diff <>", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffGreaterThan(Integer value) {
            addCriterion("qty_diff >", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_diff >=", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffLessThan(Integer value) {
            addCriterion("qty_diff <", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffLessThanOrEqualTo(Integer value) {
            addCriterion("qty_diff <=", value, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffIn(List<Integer> values) {
            addCriterion("qty_diff in", values, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffNotIn(List<Integer> values) {
            addCriterion("qty_diff not in", values, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffBetween(Integer value1, Integer value2) {
            addCriterion("qty_diff between", value1, value2, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andQtyDiffNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_diff not between", value1, value2, "qtyDiff");
            return (Criteria) this;
        }

        public Criteria andDoDelflagIsNull() {
            addCriterion("do_delflag is null");
            return (Criteria) this;
        }

        public Criteria andDoDelflagIsNotNull() {
            addCriterion("do_delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDoDelflagEqualTo(String value) {
            addCriterion("do_delflag =", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagNotEqualTo(String value) {
            addCriterion("do_delflag <>", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagGreaterThan(String value) {
            addCriterion("do_delflag >", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagGreaterThanOrEqualTo(String value) {
            addCriterion("do_delflag >=", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagLessThan(String value) {
            addCriterion("do_delflag <", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagLessThanOrEqualTo(String value) {
            addCriterion("do_delflag <=", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagLike(String value) {
            addCriterion("do_delflag like", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagNotLike(String value) {
            addCriterion("do_delflag not like", value, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagIn(List<String> values) {
            addCriterion("do_delflag in", values, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagNotIn(List<String> values) {
            addCriterion("do_delflag not in", values, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagBetween(String value1, String value2) {
            addCriterion("do_delflag between", value1, value2, "doDelflag");
            return (Criteria) this;
        }

        public Criteria andDoDelflagNotBetween(String value1, String value2) {
            addCriterion("do_delflag not between", value1, value2, "doDelflag");
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

        public Criteria andGatherWarehouseCodeIsNull() {
            addCriterion("gather_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIsNotNull() {
            addCriterion("gather_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeEqualTo(String value) {
            addCriterion("gather_warehouse_code =", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotEqualTo(String value) {
            addCriterion("gather_warehouse_code <>", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThan(String value) {
            addCriterion("gather_warehouse_code >", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code >=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThan(String value) {
            addCriterion("gather_warehouse_code <", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("gather_warehouse_code <=", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeLike(String value) {
            addCriterion("gather_warehouse_code like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotLike(String value) {
            addCriterion("gather_warehouse_code not like", value, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeIn(List<String> values) {
            addCriterion("gather_warehouse_code in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotIn(List<String> values) {
            addCriterion("gather_warehouse_code not in", values, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andGatherWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("gather_warehouse_code not between", value1, value2, "gatherWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNull() {
            addCriterion("ship_date is null");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNotNull() {
            addCriterion("ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipDateEqualTo(Date value) {
            addCriterion("ship_date =", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotEqualTo(Date value) {
            addCriterion("ship_date <>", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThan(Date value) {
            addCriterion("ship_date >", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_date >=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThan(Date value) {
            addCriterion("ship_date <", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_date <=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateIn(List<Date> values) {
            addCriterion("ship_date in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotIn(List<Date> values) {
            addCriterion("ship_date not in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateBetween(Date value1, Date value2) {
            addCriterion("ship_date between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_date not between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andOrderId13IsNull() {
            addCriterion("order_id_13 is null");
            return (Criteria) this;
        }

        public Criteria andOrderId13IsNotNull() {
            addCriterion("order_id_13 is not null");
            return (Criteria) this;
        }

        public Criteria andOrderId13EqualTo(String value) {
            addCriterion("order_id_13 =", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13NotEqualTo(String value) {
            addCriterion("order_id_13 <>", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13GreaterThan(String value) {
            addCriterion("order_id_13 >", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13GreaterThanOrEqualTo(String value) {
            addCriterion("order_id_13 >=", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13LessThan(String value) {
            addCriterion("order_id_13 <", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13LessThanOrEqualTo(String value) {
            addCriterion("order_id_13 <=", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13Like(String value) {
            addCriterion("order_id_13 like", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13NotLike(String value) {
            addCriterion("order_id_13 not like", value, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13In(List<String> values) {
            addCriterion("order_id_13 in", values, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13NotIn(List<String> values) {
            addCriterion("order_id_13 not in", values, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13Between(String value1, String value2) {
            addCriterion("order_id_13 between", value1, value2, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOrderId13NotBetween(String value1, String value2) {
            addCriterion("order_id_13 not between", value1, value2, "orderId13");
            return (Criteria) this;
        }

        public Criteria andOptFlgIsNull() {
            addCriterion("opt_flg is null");
            return (Criteria) this;
        }

        public Criteria andOptFlgIsNotNull() {
            addCriterion("opt_flg is not null");
            return (Criteria) this;
        }

        public Criteria andOptFlgEqualTo(String value) {
            addCriterion("opt_flg =", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgNotEqualTo(String value) {
            addCriterion("opt_flg <>", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgGreaterThan(String value) {
            addCriterion("opt_flg >", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgGreaterThanOrEqualTo(String value) {
            addCriterion("opt_flg >=", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgLessThan(String value) {
            addCriterion("opt_flg <", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgLessThanOrEqualTo(String value) {
            addCriterion("opt_flg <=", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgLike(String value) {
            addCriterion("opt_flg like", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgNotLike(String value) {
            addCriterion("opt_flg not like", value, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgIn(List<String> values) {
            addCriterion("opt_flg in", values, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgNotIn(List<String> values) {
            addCriterion("opt_flg not in", values, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgBetween(String value1, String value2) {
            addCriterion("opt_flg between", value1, value2, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptFlgNotBetween(String value1, String value2) {
            addCriterion("opt_flg not between", value1, value2, "optFlg");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNull() {
            addCriterion("opt_status is null");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNotNull() {
            addCriterion("opt_status is not null");
            return (Criteria) this;
        }

        public Criteria andOptStatusEqualTo(String value) {
            addCriterion("opt_status =", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotEqualTo(String value) {
            addCriterion("opt_status <>", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThan(String value) {
            addCriterion("opt_status >", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThanOrEqualTo(String value) {
            addCriterion("opt_status >=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThan(String value) {
            addCriterion("opt_status <", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThanOrEqualTo(String value) {
            addCriterion("opt_status <=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLike(String value) {
            addCriterion("opt_status like", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotLike(String value) {
            addCriterion("opt_status not like", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusIn(List<String> values) {
            addCriterion("opt_status in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotIn(List<String> values) {
            addCriterion("opt_status not in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusBetween(String value1, String value2) {
            addCriterion("opt_status between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotBetween(String value1, String value2) {
            addCriterion("opt_status not between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptMsgIsNull() {
            addCriterion("opt_msg is null");
            return (Criteria) this;
        }

        public Criteria andOptMsgIsNotNull() {
            addCriterion("opt_msg is not null");
            return (Criteria) this;
        }

        public Criteria andOptMsgEqualTo(String value) {
            addCriterion("opt_msg =", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgNotEqualTo(String value) {
            addCriterion("opt_msg <>", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgGreaterThan(String value) {
            addCriterion("opt_msg >", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgGreaterThanOrEqualTo(String value) {
            addCriterion("opt_msg >=", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgLessThan(String value) {
            addCriterion("opt_msg <", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgLessThanOrEqualTo(String value) {
            addCriterion("opt_msg <=", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgLike(String value) {
            addCriterion("opt_msg like", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgNotLike(String value) {
            addCriterion("opt_msg not like", value, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgIn(List<String> values) {
            addCriterion("opt_msg in", values, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgNotIn(List<String> values) {
            addCriterion("opt_msg not in", values, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgBetween(String value1, String value2) {
            addCriterion("opt_msg between", value1, value2, "optMsg");
            return (Criteria) this;
        }

        public Criteria andOptMsgNotBetween(String value1, String value2) {
            addCriterion("opt_msg not between", value1, value2, "optMsg");
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
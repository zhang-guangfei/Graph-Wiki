package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IpsReceiveSignImpInfoFromAllExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IpsReceiveSignImpInfoFromAllExample() {
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

        public Criteria andSourceSystemIsNull() {
            addCriterion("source_system is null");
            return (Criteria) this;
        }

        public Criteria andSourceSystemIsNotNull() {
            addCriterion("source_system is not null");
            return (Criteria) this;
        }

        public Criteria andSourceSystemEqualTo(String value) {
            addCriterion("source_system =", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemNotEqualTo(String value) {
            addCriterion("source_system <>", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemGreaterThan(String value) {
            addCriterion("source_system >", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemGreaterThanOrEqualTo(String value) {
            addCriterion("source_system >=", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemLessThan(String value) {
            addCriterion("source_system <", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemLessThanOrEqualTo(String value) {
            addCriterion("source_system <=", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemLike(String value) {
            addCriterion("source_system like", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemNotLike(String value) {
            addCriterion("source_system not like", value, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemIn(List<String> values) {
            addCriterion("source_system in", values, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemNotIn(List<String> values) {
            addCriterion("source_system not in", values, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemBetween(String value1, String value2) {
            addCriterion("source_system between", value1, value2, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSourceSystemNotBetween(String value1, String value2) {
            addCriterion("source_system not between", value1, value2, "sourceSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemIsNull() {
            addCriterion("supplier_system is null");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemIsNotNull() {
            addCriterion("supplier_system is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemEqualTo(String value) {
            addCriterion("supplier_system =", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemNotEqualTo(String value) {
            addCriterion("supplier_system <>", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemGreaterThan(String value) {
            addCriterion("supplier_system >", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_system >=", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemLessThan(String value) {
            addCriterion("supplier_system <", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemLessThanOrEqualTo(String value) {
            addCriterion("supplier_system <=", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemLike(String value) {
            addCriterion("supplier_system like", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemNotLike(String value) {
            addCriterion("supplier_system not like", value, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemIn(List<String> values) {
            addCriterion("supplier_system in", values, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemNotIn(List<String> values) {
            addCriterion("supplier_system not in", values, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemBetween(String value1, String value2) {
            addCriterion("supplier_system between", value1, value2, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSupplierSystemNotBetween(String value1, String value2) {
            addCriterion("supplier_system not between", value1, value2, "supplierSystem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoIsNull() {
            addCriterion("src_order_no is null");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoIsNotNull() {
            addCriterion("src_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoEqualTo(String value) {
            addCriterion("src_order_no =", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoNotEqualTo(String value) {
            addCriterion("src_order_no <>", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoGreaterThan(String value) {
            addCriterion("src_order_no >", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("src_order_no >=", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoLessThan(String value) {
            addCriterion("src_order_no <", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoLessThanOrEqualTo(String value) {
            addCriterion("src_order_no <=", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoLike(String value) {
            addCriterion("src_order_no like", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoNotLike(String value) {
            addCriterion("src_order_no not like", value, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoIn(List<String> values) {
            addCriterion("src_order_no in", values, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoNotIn(List<String> values) {
            addCriterion("src_order_no not in", values, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoBetween(String value1, String value2) {
            addCriterion("src_order_no between", value1, value2, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderNoNotBetween(String value1, String value2) {
            addCriterion("src_order_no not between", value1, value2, "srcOrderNo");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemIsNull() {
            addCriterion("src_order_Item is null");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemIsNotNull() {
            addCriterion("src_order_Item is not null");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemEqualTo(String value) {
            addCriterion("src_order_Item =", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemNotEqualTo(String value) {
            addCriterion("src_order_Item <>", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemGreaterThan(String value) {
            addCriterion("src_order_Item >", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("src_order_Item >=", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemLessThan(String value) {
            addCriterion("src_order_Item <", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemLessThanOrEqualTo(String value) {
            addCriterion("src_order_Item <=", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemLike(String value) {
            addCriterion("src_order_Item like", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemNotLike(String value) {
            addCriterion("src_order_Item not like", value, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemIn(List<String> values) {
            addCriterion("src_order_Item in", values, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemNotIn(List<String> values) {
            addCriterion("src_order_Item not in", values, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemBetween(String value1, String value2) {
            addCriterion("src_order_Item between", value1, value2, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andSrcOrderItemNotBetween(String value1, String value2) {
            addCriterion("src_order_Item not between", value1, value2, "srcOrderItem");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNull() {
            addCriterion("order_Item is null");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNotNull() {
            addCriterion("order_Item is not null");
            return (Criteria) this;
        }

        public Criteria andOrderItemEqualTo(String value) {
            addCriterion("order_Item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(String value) {
            addCriterion("order_Item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(String value) {
            addCriterion("order_Item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("order_Item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(String value) {
            addCriterion("order_Item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(String value) {
            addCriterion("order_Item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLike(String value) {
            addCriterion("order_Item like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotLike(String value) {
            addCriterion("order_Item not like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<String> values) {
            addCriterion("order_Item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<String> values) {
            addCriterion("order_Item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(String value1, String value2) {
            addCriterion("order_Item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(String value1, String value2) {
            addCriterion("order_Item not between", value1, value2, "orderItem");
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

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("data_type like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("data_type not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
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

        public Criteria andQtyEqualTo(BigDecimal value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(BigDecimal value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(BigDecimal value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(BigDecimal value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<BigDecimal> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<BigDecimal> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyIsNull() {
            addCriterion("deviation_qty is null");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyIsNotNull() {
            addCriterion("deviation_qty is not null");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyEqualTo(BigDecimal value) {
            addCriterion("deviation_qty =", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyNotEqualTo(BigDecimal value) {
            addCriterion("deviation_qty <>", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyGreaterThan(BigDecimal value) {
            addCriterion("deviation_qty >", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deviation_qty >=", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyLessThan(BigDecimal value) {
            addCriterion("deviation_qty <", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deviation_qty <=", value, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyIn(List<BigDecimal> values) {
            addCriterion("deviation_qty in", values, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyNotIn(List<BigDecimal> values) {
            addCriterion("deviation_qty not in", values, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deviation_qty between", value1, value2, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationQtyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deviation_qty not between", value1, value2, "deviationQty");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonIsNull() {
            addCriterion("deviation_reason is null");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonIsNotNull() {
            addCriterion("deviation_reason is not null");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonEqualTo(String value) {
            addCriterion("deviation_reason =", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonNotEqualTo(String value) {
            addCriterion("deviation_reason <>", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonGreaterThan(String value) {
            addCriterion("deviation_reason >", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonGreaterThanOrEqualTo(String value) {
            addCriterion("deviation_reason >=", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonLessThan(String value) {
            addCriterion("deviation_reason <", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonLessThanOrEqualTo(String value) {
            addCriterion("deviation_reason <=", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonLike(String value) {
            addCriterion("deviation_reason like", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonNotLike(String value) {
            addCriterion("deviation_reason not like", value, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonIn(List<String> values) {
            addCriterion("deviation_reason in", values, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonNotIn(List<String> values) {
            addCriterion("deviation_reason not in", values, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonBetween(String value1, String value2) {
            addCriterion("deviation_reason between", value1, value2, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andDeviationReasonNotBetween(String value1, String value2) {
            addCriterion("deviation_reason not between", value1, value2, "deviationReason");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIsNull() {
            addCriterion("process_status is null");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIsNotNull() {
            addCriterion("process_status is not null");
            return (Criteria) this;
        }

        public Criteria andProcessStatusEqualTo(String value) {
            addCriterion("process_status =", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotEqualTo(String value) {
            addCriterion("process_status <>", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusGreaterThan(String value) {
            addCriterion("process_status >", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusGreaterThanOrEqualTo(String value) {
            addCriterion("process_status >=", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLessThan(String value) {
            addCriterion("process_status <", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLessThanOrEqualTo(String value) {
            addCriterion("process_status <=", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLike(String value) {
            addCriterion("process_status like", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotLike(String value) {
            addCriterion("process_status not like", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIn(List<String> values) {
            addCriterion("process_status in", values, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotIn(List<String> values) {
            addCriterion("process_status not in", values, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusBetween(String value1, String value2) {
            addCriterion("process_status between", value1, value2, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotBetween(String value1, String value2) {
            addCriterion("process_status not between", value1, value2, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceIsNull() {
            addCriterion("process_place is null");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceIsNotNull() {
            addCriterion("process_place is not null");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceEqualTo(String value) {
            addCriterion("process_place =", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceNotEqualTo(String value) {
            addCriterion("process_place <>", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceGreaterThan(String value) {
            addCriterion("process_place >", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("process_place >=", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceLessThan(String value) {
            addCriterion("process_place <", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceLessThanOrEqualTo(String value) {
            addCriterion("process_place <=", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceLike(String value) {
            addCriterion("process_place like", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceNotLike(String value) {
            addCriterion("process_place not like", value, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceIn(List<String> values) {
            addCriterion("process_place in", values, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceNotIn(List<String> values) {
            addCriterion("process_place not in", values, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceBetween(String value1, String value2) {
            addCriterion("process_place between", value1, value2, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessPlaceNotBetween(String value1, String value2) {
            addCriterion("process_place not between", value1, value2, "processPlace");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNull() {
            addCriterion("process_date is null");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNotNull() {
            addCriterion("process_date is not null");
            return (Criteria) this;
        }

        public Criteria andProcessDateEqualTo(Date value) {
            addCriterion("process_date =", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotEqualTo(Date value) {
            addCriterion("process_date <>", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThan(Date value) {
            addCriterion("process_date >", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanOrEqualTo(Date value) {
            addCriterion("process_date >=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThan(Date value) {
            addCriterion("process_date <", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanOrEqualTo(Date value) {
            addCriterion("process_date <=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateIn(List<Date> values) {
            addCriterion("process_date in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotIn(List<Date> values) {
            addCriterion("process_date not in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateBetween(Date value1, Date value2) {
            addCriterion("process_date between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotBetween(Date value1, Date value2) {
            addCriterion("process_date not between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeIsNull() {
            addCriterion("processor_code is null");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeIsNotNull() {
            addCriterion("processor_code is not null");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeEqualTo(String value) {
            addCriterion("processor_code =", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeNotEqualTo(String value) {
            addCriterion("processor_code <>", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeGreaterThan(String value) {
            addCriterion("processor_code >", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("processor_code >=", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeLessThan(String value) {
            addCriterion("processor_code <", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeLessThanOrEqualTo(String value) {
            addCriterion("processor_code <=", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeLike(String value) {
            addCriterion("processor_code like", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeNotLike(String value) {
            addCriterion("processor_code not like", value, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeIn(List<String> values) {
            addCriterion("processor_code in", values, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeNotIn(List<String> values) {
            addCriterion("processor_code not in", values, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeBetween(String value1, String value2) {
            addCriterion("processor_code between", value1, value2, "processorCode");
            return (Criteria) this;
        }

        public Criteria andProcessorCodeNotBetween(String value1, String value2) {
            addCriterion("processor_code not between", value1, value2, "processorCode");
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

        public Criteria andInvoiceNoIsNull() {
            addCriterion("invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNotNull() {
            addCriterion("invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoEqualTo(String value) {
            addCriterion("invoice_no =", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotEqualTo(String value) {
            addCriterion("invoice_no <>", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThan(String value) {
            addCriterion("invoice_no >", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no >=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThan(String value) {
            addCriterion("invoice_no <", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_no <=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLike(String value) {
            addCriterion("invoice_no like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotLike(String value) {
            addCriterion("invoice_no not like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIn(List<String> values) {
            addCriterion("invoice_no in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotIn(List<String> values) {
            addCriterion("invoice_no not in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoBetween(String value1, String value2) {
            addCriterion("invoice_no between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("invoice_no not between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoIsNull() {
            addCriterion("invoice_batch_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoIsNotNull() {
            addCriterion("invoice_batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoEqualTo(String value) {
            addCriterion("invoice_batch_no =", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoNotEqualTo(String value) {
            addCriterion("invoice_batch_no <>", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoGreaterThan(String value) {
            addCriterion("invoice_batch_no >", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_batch_no >=", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoLessThan(String value) {
            addCriterion("invoice_batch_no <", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_batch_no <=", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoLike(String value) {
            addCriterion("invoice_batch_no like", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoNotLike(String value) {
            addCriterion("invoice_batch_no not like", value, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoIn(List<String> values) {
            addCriterion("invoice_batch_no in", values, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoNotIn(List<String> values) {
            addCriterion("invoice_batch_no not in", values, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoBetween(String value1, String value2) {
            addCriterion("invoice_batch_no between", value1, value2, "invoiceBatchNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBatchNoNotBetween(String value1, String value2) {
            addCriterion("invoice_batch_no not between", value1, value2, "invoiceBatchNo");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempMergePcoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TempMergePcoExample() {
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

        public Criteria andInvoiceQtyIsNull() {
            addCriterion("invoice_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIsNotNull() {
            addCriterion("invoice_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyEqualTo(Integer value) {
            addCriterion("invoice_qty =", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotEqualTo(Integer value) {
            addCriterion("invoice_qty <>", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThan(Integer value) {
            addCriterion("invoice_qty >", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty >=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThan(Integer value) {
            addCriterion("invoice_qty <", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_qty <=", value, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyIn(List<Integer> values) {
            addCriterion("invoice_qty in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotIn(List<Integer> values) {
            addCriterion("invoice_qty not in", values, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty between", value1, value2, "invoiceQty");
            return (Criteria) this;
        }

        public Criteria andInvoiceQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_qty not between", value1, value2, "invoiceQty");
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

        public Criteria andOrderitemIsNull() {
            addCriterion("orderItem is null");
            return (Criteria) this;
        }

        public Criteria andOrderitemIsNotNull() {
            addCriterion("orderItem is not null");
            return (Criteria) this;
        }

        public Criteria andOrderitemEqualTo(Integer value) {
            addCriterion("orderItem =", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotEqualTo(Integer value) {
            addCriterion("orderItem <>", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemGreaterThan(Integer value) {
            addCriterion("orderItem >", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderItem >=", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemLessThan(Integer value) {
            addCriterion("orderItem <", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemLessThanOrEqualTo(Integer value) {
            addCriterion("orderItem <=", value, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemIn(List<Integer> values) {
            addCriterion("orderItem in", values, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotIn(List<Integer> values) {
            addCriterion("orderItem not in", values, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemBetween(Integer value1, Integer value2) {
            addCriterion("orderItem between", value1, value2, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemNotBetween(Integer value1, Integer value2) {
            addCriterion("orderItem not between", value1, value2, "orderitem");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIsNull() {
            addCriterion("orderItemSplit is null");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIsNotNull() {
            addCriterion("orderItemSplit is not null");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitEqualTo(Integer value) {
            addCriterion("orderItemSplit =", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotEqualTo(Integer value) {
            addCriterion("orderItemSplit <>", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitGreaterThan(Integer value) {
            addCriterion("orderItemSplit >", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderItemSplit >=", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitLessThan(Integer value) {
            addCriterion("orderItemSplit <", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitLessThanOrEqualTo(Integer value) {
            addCriterion("orderItemSplit <=", value, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitIn(List<Integer> values) {
            addCriterion("orderItemSplit in", values, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotIn(List<Integer> values) {
            addCriterion("orderItemSplit not in", values, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitBetween(Integer value1, Integer value2) {
            addCriterion("orderItemSplit between", value1, value2, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andOrderitemsplitNotBetween(Integer value1, Integer value2) {
            addCriterion("orderItemSplit not between", value1, value2, "orderitemsplit");
            return (Criteria) this;
        }

        public Criteria andPcoIdIsNull() {
            addCriterion("pco_id is null");
            return (Criteria) this;
        }

        public Criteria andPcoIdIsNotNull() {
            addCriterion("pco_id is not null");
            return (Criteria) this;
        }

        public Criteria andPcoIdEqualTo(String value) {
            addCriterion("pco_id =", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotEqualTo(String value) {
            addCriterion("pco_id <>", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThan(String value) {
            addCriterion("pco_id >", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdGreaterThanOrEqualTo(String value) {
            addCriterion("pco_id >=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThan(String value) {
            addCriterion("pco_id <", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLessThanOrEqualTo(String value) {
            addCriterion("pco_id <=", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdLike(String value) {
            addCriterion("pco_id like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotLike(String value) {
            addCriterion("pco_id not like", value, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdIn(List<String> values) {
            addCriterion("pco_id in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotIn(List<String> values) {
            addCriterion("pco_id not in", values, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdBetween(String value1, String value2) {
            addCriterion("pco_id between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoIdNotBetween(String value1, String value2) {
            addCriterion("pco_id not between", value1, value2, "pcoId");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNull() {
            addCriterion("pco_item is null");
            return (Criteria) this;
        }

        public Criteria andPcoItemIsNotNull() {
            addCriterion("pco_item is not null");
            return (Criteria) this;
        }

        public Criteria andPcoItemEqualTo(Integer value) {
            addCriterion("pco_item =", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotEqualTo(Integer value) {
            addCriterion("pco_item <>", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThan(Integer value) {
            addCriterion("pco_item >", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("pco_item >=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThan(Integer value) {
            addCriterion("pco_item <", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemLessThanOrEqualTo(Integer value) {
            addCriterion("pco_item <=", value, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemIn(List<Integer> values) {
            addCriterion("pco_item in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotIn(List<Integer> values) {
            addCriterion("pco_item not in", values, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemBetween(Integer value1, Integer value2) {
            addCriterion("pco_item between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andPcoItemNotBetween(Integer value1, Integer value2) {
            addCriterion("pco_item not between", value1, value2, "pcoItem");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNull() {
            addCriterion("sub_modelno is null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIsNotNull() {
            addCriterion("sub_modelno is not null");
            return (Criteria) this;
        }

        public Criteria andSubModelnoEqualTo(String value) {
            addCriterion("sub_modelno =", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotEqualTo(String value) {
            addCriterion("sub_modelno <>", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThan(String value) {
            addCriterion("sub_modelno >", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("sub_modelno >=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThan(String value) {
            addCriterion("sub_modelno <", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLessThanOrEqualTo(String value) {
            addCriterion("sub_modelno <=", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoLike(String value) {
            addCriterion("sub_modelno like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotLike(String value) {
            addCriterion("sub_modelno not like", value, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoIn(List<String> values) {
            addCriterion("sub_modelno in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotIn(List<String> values) {
            addCriterion("sub_modelno not in", values, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoBetween(String value1, String value2) {
            addCriterion("sub_modelno between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubModelnoNotBetween(String value1, String value2) {
            addCriterion("sub_modelno not between", value1, value2, "subModelno");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNull() {
            addCriterion("sub_qty is null");
            return (Criteria) this;
        }

        public Criteria andSubQtyIsNotNull() {
            addCriterion("sub_qty is not null");
            return (Criteria) this;
        }

        public Criteria andSubQtyEqualTo(Integer value) {
            addCriterion("sub_qty =", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotEqualTo(Integer value) {
            addCriterion("sub_qty <>", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThan(Integer value) {
            addCriterion("sub_qty >", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_qty >=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThan(Integer value) {
            addCriterion("sub_qty <", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyLessThanOrEqualTo(Integer value) {
            addCriterion("sub_qty <=", value, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyIn(List<Integer> values) {
            addCriterion("sub_qty in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotIn(List<Integer> values) {
            addCriterion("sub_qty not in", values, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty between", value1, value2, "subQty");
            return (Criteria) this;
        }

        public Criteria andSubQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_qty not between", value1, value2, "subQty");
            return (Criteria) this;
        }

        public Criteria andInvIdIsNull() {
            addCriterion("inv_id is null");
            return (Criteria) this;
        }

        public Criteria andInvIdIsNotNull() {
            addCriterion("inv_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvIdEqualTo(Long value) {
            addCriterion("inv_id =", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdNotEqualTo(Long value) {
            addCriterion("inv_id <>", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdGreaterThan(Long value) {
            addCriterion("inv_id >", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inv_id >=", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdLessThan(Long value) {
            addCriterion("inv_id <", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdLessThanOrEqualTo(Long value) {
            addCriterion("inv_id <=", value, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdIn(List<Long> values) {
            addCriterion("inv_id in", values, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdNotIn(List<Long> values) {
            addCriterion("inv_id not in", values, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdBetween(Long value1, Long value2) {
            addCriterion("inv_id between", value1, value2, "invId");
            return (Criteria) this;
        }

        public Criteria andInvIdNotBetween(Long value1, Long value2) {
            addCriterion("inv_id not between", value1, value2, "invId");
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

        public Criteria andInventoryTableTypeIsNull() {
            addCriterion("inventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIsNotNull() {
            addCriterion("inventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeEqualTo(String value) {
            addCriterion("inventory_table_type =", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotEqualTo(String value) {
            addCriterion("inventory_table_type <>", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThan(String value) {
            addCriterion("inventory_table_type >", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_table_type >=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThan(String value) {
            addCriterion("inventory_table_type <", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("inventory_table_type <=", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeLike(String value) {
            addCriterion("inventory_table_type like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotLike(String value) {
            addCriterion("inventory_table_type not like", value, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeIn(List<String> values) {
            addCriterion("inventory_table_type in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotIn(List<String> values) {
            addCriterion("inventory_table_type not in", values, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeBetween(String value1, String value2) {
            addCriterion("inventory_table_type between", value1, value2, "inventoryTableType");
            return (Criteria) this;
        }

        public Criteria andInventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("inventory_table_type not between", value1, value2, "inventoryTableType");
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

        public Criteria andOutQtyIsNull() {
            addCriterion("out_qty is null");
            return (Criteria) this;
        }

        public Criteria andOutQtyIsNotNull() {
            addCriterion("out_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOutQtyEqualTo(Integer value) {
            addCriterion("out_qty =", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotEqualTo(Integer value) {
            addCriterion("out_qty <>", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThan(Integer value) {
            addCriterion("out_qty >", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_qty >=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThan(Integer value) {
            addCriterion("out_qty <", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyLessThanOrEqualTo(Integer value) {
            addCriterion("out_qty <=", value, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyIn(List<Integer> values) {
            addCriterion("out_qty in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotIn(List<Integer> values) {
            addCriterion("out_qty not in", values, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyBetween(Integer value1, Integer value2) {
            addCriterion("out_qty between", value1, value2, "outQty");
            return (Criteria) this;
        }

        public Criteria andOutQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("out_qty not between", value1, value2, "outQty");
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

        public Criteria andInventoryStatusIsNull() {
            addCriterion("inventory_status is null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIsNotNull() {
            addCriterion("inventory_status is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusEqualTo(String value) {
            addCriterion("inventory_status =", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotEqualTo(String value) {
            addCriterion("inventory_status <>", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThan(String value) {
            addCriterion("inventory_status >", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_status >=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThan(String value) {
            addCriterion("inventory_status <", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLessThanOrEqualTo(String value) {
            addCriterion("inventory_status <=", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusLike(String value) {
            addCriterion("inventory_status like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotLike(String value) {
            addCriterion("inventory_status not like", value, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusIn(List<String> values) {
            addCriterion("inventory_status in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotIn(List<String> values) {
            addCriterion("inventory_status not in", values, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusBetween(String value1, String value2) {
            addCriterion("inventory_status between", value1, value2, "inventoryStatus");
            return (Criteria) this;
        }

        public Criteria andInventoryStatusNotBetween(String value1, String value2) {
            addCriterion("inventory_status not between", value1, value2, "inventoryStatus");
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

        public Criteria andPrepareQuantityIsNull() {
            addCriterion("prepare_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIsNotNull() {
            addCriterion("prepare_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityEqualTo(Integer value) {
            addCriterion("prepare_quantity =", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotEqualTo(Integer value) {
            addCriterion("prepare_quantity <>", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThan(Integer value) {
            addCriterion("prepare_quantity >", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("prepare_quantity >=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThan(Integer value) {
            addCriterion("prepare_quantity <", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("prepare_quantity <=", value, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityIn(List<Integer> values) {
            addCriterion("prepare_quantity in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotIn(List<Integer> values) {
            addCriterion("prepare_quantity not in", values, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityBetween(Integer value1, Integer value2) {
            addCriterion("prepare_quantity between", value1, value2, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andPrepareQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("prepare_quantity not between", value1, value2, "prepareQuantity");
            return (Criteria) this;
        }

        public Criteria andDelflag2IsNull() {
            addCriterion("delflag2 is null");
            return (Criteria) this;
        }

        public Criteria andDelflag2IsNotNull() {
            addCriterion("delflag2 is not null");
            return (Criteria) this;
        }

        public Criteria andDelflag2EqualTo(Integer value) {
            addCriterion("delflag2 =", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2NotEqualTo(Integer value) {
            addCriterion("delflag2 <>", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2GreaterThan(Integer value) {
            addCriterion("delflag2 >", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2GreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag2 >=", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2LessThan(Integer value) {
            addCriterion("delflag2 <", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2LessThanOrEqualTo(Integer value) {
            addCriterion("delflag2 <=", value, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2In(List<Integer> values) {
            addCriterion("delflag2 in", values, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2NotIn(List<Integer> values) {
            addCriterion("delflag2 not in", values, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2Between(Integer value1, Integer value2) {
            addCriterion("delflag2 between", value1, value2, "delflag2");
            return (Criteria) this;
        }

        public Criteria andDelflag2NotBetween(Integer value1, Integer value2) {
            addCriterion("delflag2 not between", value1, value2, "delflag2");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
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
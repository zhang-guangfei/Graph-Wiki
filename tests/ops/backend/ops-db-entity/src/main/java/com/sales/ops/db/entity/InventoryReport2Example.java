package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class InventoryReport2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryReport2Example() {
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

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNull() {
            addCriterion("warehouse_name is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNotNull() {
            addCriterion("warehouse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameEqualTo(String value) {
            addCriterion("warehouse_name =", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotEqualTo(String value) {
            addCriterion("warehouse_name <>", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThan(String value) {
            addCriterion("warehouse_name >", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_name >=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThan(String value) {
            addCriterion("warehouse_name <", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
            addCriterion("warehouse_name <=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLike(String value) {
            addCriterion("warehouse_name like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotLike(String value) {
            addCriterion("warehouse_name not like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIn(List<String> values) {
            addCriterion("warehouse_name in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotIn(List<String> values) {
            addCriterion("warehouse_name not in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameBetween(String value1, String value2) {
            addCriterion("warehouse_name between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotBetween(String value1, String value2) {
            addCriterion("warehouse_name not between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIsNull() {
            addCriterion("warehouse_type is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIsNotNull() {
            addCriterion("warehouse_type is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeEqualTo(String value) {
            addCriterion("warehouse_type =", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotEqualTo(String value) {
            addCriterion("warehouse_type <>", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeGreaterThan(String value) {
            addCriterion("warehouse_type >", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_type >=", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLessThan(String value) {
            addCriterion("warehouse_type <", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_type <=", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLike(String value) {
            addCriterion("warehouse_type like", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotLike(String value) {
            addCriterion("warehouse_type not like", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIn(List<String> values) {
            addCriterion("warehouse_type in", values, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotIn(List<String> values) {
            addCriterion("warehouse_type not in", values, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeBetween(String value1, String value2) {
            addCriterion("warehouse_type between", value1, value2, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotBetween(String value1, String value2) {
            addCriterion("warehouse_type not between", value1, value2, "warehouseType");
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

        public Criteria andInventoryTypeCodeIsNull() {
            addCriterion("inventory_type_code is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIsNotNull() {
            addCriterion("inventory_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeEqualTo(String value) {
            addCriterion("inventory_type_code =", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotEqualTo(String value) {
            addCriterion("inventory_type_code <>", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThan(String value) {
            addCriterion("inventory_type_code >", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_type_code >=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThan(String value) {
            addCriterion("inventory_type_code <", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("inventory_type_code <=", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeLike(String value) {
            addCriterion("inventory_type_code like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotLike(String value) {
            addCriterion("inventory_type_code not like", value, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeIn(List<String> values) {
            addCriterion("inventory_type_code in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotIn(List<String> values) {
            addCriterion("inventory_type_code not in", values, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeBetween(String value1, String value2) {
            addCriterion("inventory_type_code between", value1, value2, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeCodeNotBetween(String value1, String value2) {
            addCriterion("inventory_type_code not between", value1, value2, "inventoryTypeCode");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andPplIsNull() {
            addCriterion("ppl is null");
            return (Criteria) this;
        }

        public Criteria andPplIsNotNull() {
            addCriterion("ppl is not null");
            return (Criteria) this;
        }

        public Criteria andPplEqualTo(String value) {
            addCriterion("ppl =", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotEqualTo(String value) {
            addCriterion("ppl <>", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplGreaterThan(String value) {
            addCriterion("ppl >", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplGreaterThanOrEqualTo(String value) {
            addCriterion("ppl >=", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLessThan(String value) {
            addCriterion("ppl <", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLessThanOrEqualTo(String value) {
            addCriterion("ppl <=", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplLike(String value) {
            addCriterion("ppl like", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotLike(String value) {
            addCriterion("ppl not like", value, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplIn(List<String> values) {
            addCriterion("ppl in", values, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotIn(List<String> values) {
            addCriterion("ppl not in", values, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplBetween(String value1, String value2) {
            addCriterion("ppl between", value1, value2, "ppl");
            return (Criteria) this;
        }

        public Criteria andPplNotBetween(String value1, String value2) {
            addCriterion("ppl not between", value1, value2, "ppl");
            return (Criteria) this;
        }

        public Criteria andProjectCodeIsNull() {
            addCriterion("project_code is null");
            return (Criteria) this;
        }

        public Criteria andProjectCodeIsNotNull() {
            addCriterion("project_code is not null");
            return (Criteria) this;
        }

        public Criteria andProjectCodeEqualTo(String value) {
            addCriterion("project_code =", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotEqualTo(String value) {
            addCriterion("project_code <>", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeGreaterThan(String value) {
            addCriterion("project_code >", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("project_code >=", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLessThan(String value) {
            addCriterion("project_code <", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLessThanOrEqualTo(String value) {
            addCriterion("project_code <=", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLike(String value) {
            addCriterion("project_code like", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotLike(String value) {
            addCriterion("project_code not like", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeIn(List<String> values) {
            addCriterion("project_code in", values, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotIn(List<String> values) {
            addCriterion("project_code not in", values, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeBetween(String value1, String value2) {
            addCriterion("project_code between", value1, value2, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotBetween(String value1, String value2) {
            addCriterion("project_code not between", value1, value2, "projectCode");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIsNull() {
            addCriterion("group_customer_no is null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIsNotNull() {
            addCriterion("group_customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoEqualTo(String value) {
            addCriterion("group_customer_no =", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotEqualTo(String value) {
            addCriterion("group_customer_no <>", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThan(String value) {
            addCriterion("group_customer_no >", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("group_customer_no >=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThan(String value) {
            addCriterion("group_customer_no <", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("group_customer_no <=", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoLike(String value) {
            addCriterion("group_customer_no like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotLike(String value) {
            addCriterion("group_customer_no not like", value, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoIn(List<String> values) {
            addCriterion("group_customer_no in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotIn(List<String> values) {
            addCriterion("group_customer_no not in", values, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoBetween(String value1, String value2) {
            addCriterion("group_customer_no between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andGroupCustomerNoNotBetween(String value1, String value2) {
            addCriterion("group_customer_no not between", value1, value2, "groupCustomerNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNull() {
            addCriterion("sales_info_no is null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIsNotNull() {
            addCriterion("sales_info_no is not null");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoEqualTo(String value) {
            addCriterion("sales_info_no =", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotEqualTo(String value) {
            addCriterion("sales_info_no <>", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThan(String value) {
            addCriterion("sales_info_no >", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoGreaterThanOrEqualTo(String value) {
            addCriterion("sales_info_no >=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThan(String value) {
            addCriterion("sales_info_no <", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLessThanOrEqualTo(String value) {
            addCriterion("sales_info_no <=", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoLike(String value) {
            addCriterion("sales_info_no like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotLike(String value) {
            addCriterion("sales_info_no not like", value, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoIn(List<String> values) {
            addCriterion("sales_info_no in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotIn(List<String> values) {
            addCriterion("sales_info_no not in", values, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoBetween(String value1, String value2) {
            addCriterion("sales_info_no between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andSalesInfoNoNotBetween(String value1, String value2) {
            addCriterion("sales_info_no not between", value1, value2, "salesInfoNo");
            return (Criteria) this;
        }

        public Criteria andQtynIsNull() {
            addCriterion("qtyN is null");
            return (Criteria) this;
        }

        public Criteria andQtynIsNotNull() {
            addCriterion("qtyN is not null");
            return (Criteria) this;
        }

        public Criteria andQtynEqualTo(Integer value) {
            addCriterion("qtyN =", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynNotEqualTo(Integer value) {
            addCriterion("qtyN <>", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynGreaterThan(Integer value) {
            addCriterion("qtyN >", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyN >=", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynLessThan(Integer value) {
            addCriterion("qtyN <", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynLessThanOrEqualTo(Integer value) {
            addCriterion("qtyN <=", value, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynIn(List<Integer> values) {
            addCriterion("qtyN in", values, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynNotIn(List<Integer> values) {
            addCriterion("qtyN not in", values, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynBetween(Integer value1, Integer value2) {
            addCriterion("qtyN between", value1, value2, "qtyn");
            return (Criteria) this;
        }

        public Criteria andQtynNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyN not between", value1, value2, "qtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynIsNull() {
            addCriterion("pqtyN is null");
            return (Criteria) this;
        }

        public Criteria andPqtynIsNotNull() {
            addCriterion("pqtyN is not null");
            return (Criteria) this;
        }

        public Criteria andPqtynEqualTo(Integer value) {
            addCriterion("pqtyN =", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynNotEqualTo(Integer value) {
            addCriterion("pqtyN <>", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynGreaterThan(Integer value) {
            addCriterion("pqtyN >", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynGreaterThanOrEqualTo(Integer value) {
            addCriterion("pqtyN >=", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynLessThan(Integer value) {
            addCriterion("pqtyN <", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynLessThanOrEqualTo(Integer value) {
            addCriterion("pqtyN <=", value, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynIn(List<Integer> values) {
            addCriterion("pqtyN in", values, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynNotIn(List<Integer> values) {
            addCriterion("pqtyN not in", values, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynBetween(Integer value1, Integer value2) {
            addCriterion("pqtyN between", value1, value2, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andPqtynNotBetween(Integer value1, Integer value2) {
            addCriterion("pqtyN not between", value1, value2, "pqtyn");
            return (Criteria) this;
        }

        public Criteria andQtywIsNull() {
            addCriterion("qtyW is null");
            return (Criteria) this;
        }

        public Criteria andQtywIsNotNull() {
            addCriterion("qtyW is not null");
            return (Criteria) this;
        }

        public Criteria andQtywEqualTo(Integer value) {
            addCriterion("qtyW =", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywNotEqualTo(Integer value) {
            addCriterion("qtyW <>", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywGreaterThan(Integer value) {
            addCriterion("qtyW >", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyW >=", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywLessThan(Integer value) {
            addCriterion("qtyW <", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywLessThanOrEqualTo(Integer value) {
            addCriterion("qtyW <=", value, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywIn(List<Integer> values) {
            addCriterion("qtyW in", values, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywNotIn(List<Integer> values) {
            addCriterion("qtyW not in", values, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywBetween(Integer value1, Integer value2) {
            addCriterion("qtyW between", value1, value2, "qtyw");
            return (Criteria) this;
        }

        public Criteria andQtywNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyW not between", value1, value2, "qtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywIsNull() {
            addCriterion("pqtyW is null");
            return (Criteria) this;
        }

        public Criteria andPqtywIsNotNull() {
            addCriterion("pqtyW is not null");
            return (Criteria) this;
        }

        public Criteria andPqtywEqualTo(Integer value) {
            addCriterion("pqtyW =", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywNotEqualTo(Integer value) {
            addCriterion("pqtyW <>", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywGreaterThan(Integer value) {
            addCriterion("pqtyW >", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywGreaterThanOrEqualTo(Integer value) {
            addCriterion("pqtyW >=", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywLessThan(Integer value) {
            addCriterion("pqtyW <", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywLessThanOrEqualTo(Integer value) {
            addCriterion("pqtyW <=", value, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywIn(List<Integer> values) {
            addCriterion("pqtyW in", values, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywNotIn(List<Integer> values) {
            addCriterion("pqtyW not in", values, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywBetween(Integer value1, Integer value2) {
            addCriterion("pqtyW between", value1, value2, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andPqtywNotBetween(Integer value1, Integer value2) {
            addCriterion("pqtyW not between", value1, value2, "pqtyw");
            return (Criteria) this;
        }

        public Criteria andQtydIsNull() {
            addCriterion("qtyD is null");
            return (Criteria) this;
        }

        public Criteria andQtydIsNotNull() {
            addCriterion("qtyD is not null");
            return (Criteria) this;
        }

        public Criteria andQtydEqualTo(Integer value) {
            addCriterion("qtyD =", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydNotEqualTo(Integer value) {
            addCriterion("qtyD <>", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydGreaterThan(Integer value) {
            addCriterion("qtyD >", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyD >=", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydLessThan(Integer value) {
            addCriterion("qtyD <", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydLessThanOrEqualTo(Integer value) {
            addCriterion("qtyD <=", value, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydIn(List<Integer> values) {
            addCriterion("qtyD in", values, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydNotIn(List<Integer> values) {
            addCriterion("qtyD not in", values, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydBetween(Integer value1, Integer value2) {
            addCriterion("qtyD between", value1, value2, "qtyd");
            return (Criteria) this;
        }

        public Criteria andQtydNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyD not between", value1, value2, "qtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydIsNull() {
            addCriterion("pqtyD is null");
            return (Criteria) this;
        }

        public Criteria andPqtydIsNotNull() {
            addCriterion("pqtyD is not null");
            return (Criteria) this;
        }

        public Criteria andPqtydEqualTo(Integer value) {
            addCriterion("pqtyD =", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydNotEqualTo(Integer value) {
            addCriterion("pqtyD <>", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydGreaterThan(Integer value) {
            addCriterion("pqtyD >", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydGreaterThanOrEqualTo(Integer value) {
            addCriterion("pqtyD >=", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydLessThan(Integer value) {
            addCriterion("pqtyD <", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydLessThanOrEqualTo(Integer value) {
            addCriterion("pqtyD <=", value, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydIn(List<Integer> values) {
            addCriterion("pqtyD in", values, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydNotIn(List<Integer> values) {
            addCriterion("pqtyD not in", values, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydBetween(Integer value1, Integer value2) {
            addCriterion("pqtyD between", value1, value2, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andPqtydNotBetween(Integer value1, Integer value2) {
            addCriterion("pqtyD not between", value1, value2, "pqtyd");
            return (Criteria) this;
        }

        public Criteria andQtycIsNull() {
            addCriterion("qtyC is null");
            return (Criteria) this;
        }

        public Criteria andQtycIsNotNull() {
            addCriterion("qtyC is not null");
            return (Criteria) this;
        }

        public Criteria andQtycEqualTo(Integer value) {
            addCriterion("qtyC =", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycNotEqualTo(Integer value) {
            addCriterion("qtyC <>", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycGreaterThan(Integer value) {
            addCriterion("qtyC >", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyC >=", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycLessThan(Integer value) {
            addCriterion("qtyC <", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycLessThanOrEqualTo(Integer value) {
            addCriterion("qtyC <=", value, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycIn(List<Integer> values) {
            addCriterion("qtyC in", values, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycNotIn(List<Integer> values) {
            addCriterion("qtyC not in", values, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycBetween(Integer value1, Integer value2) {
            addCriterion("qtyC between", value1, value2, "qtyc");
            return (Criteria) this;
        }

        public Criteria andQtycNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyC not between", value1, value2, "qtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycIsNull() {
            addCriterion("pqtyC is null");
            return (Criteria) this;
        }

        public Criteria andPqtycIsNotNull() {
            addCriterion("pqtyC is not null");
            return (Criteria) this;
        }

        public Criteria andPqtycEqualTo(Integer value) {
            addCriterion("pqtyC =", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycNotEqualTo(Integer value) {
            addCriterion("pqtyC <>", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycGreaterThan(Integer value) {
            addCriterion("pqtyC >", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycGreaterThanOrEqualTo(Integer value) {
            addCriterion("pqtyC >=", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycLessThan(Integer value) {
            addCriterion("pqtyC <", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycLessThanOrEqualTo(Integer value) {
            addCriterion("pqtyC <=", value, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycIn(List<Integer> values) {
            addCriterion("pqtyC in", values, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycNotIn(List<Integer> values) {
            addCriterion("pqtyC not in", values, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycBetween(Integer value1, Integer value2) {
            addCriterion("pqtyC between", value1, value2, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andPqtycNotBetween(Integer value1, Integer value2) {
            addCriterion("pqtyC not between", value1, value2, "pqtyc");
            return (Criteria) this;
        }

        public Criteria andQtypIsNull() {
            addCriterion("qtyP is null");
            return (Criteria) this;
        }

        public Criteria andQtypIsNotNull() {
            addCriterion("qtyP is not null");
            return (Criteria) this;
        }

        public Criteria andQtypEqualTo(Integer value) {
            addCriterion("qtyP =", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypNotEqualTo(Integer value) {
            addCriterion("qtyP <>", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypGreaterThan(Integer value) {
            addCriterion("qtyP >", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyP >=", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypLessThan(Integer value) {
            addCriterion("qtyP <", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypLessThanOrEqualTo(Integer value) {
            addCriterion("qtyP <=", value, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypIn(List<Integer> values) {
            addCriterion("qtyP in", values, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypNotIn(List<Integer> values) {
            addCriterion("qtyP not in", values, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypBetween(Integer value1, Integer value2) {
            addCriterion("qtyP between", value1, value2, "qtyp");
            return (Criteria) this;
        }

        public Criteria andQtypNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyP not between", value1, value2, "qtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypIsNull() {
            addCriterion("pqtyP is null");
            return (Criteria) this;
        }

        public Criteria andPqtypIsNotNull() {
            addCriterion("pqtyP is not null");
            return (Criteria) this;
        }

        public Criteria andPqtypEqualTo(Integer value) {
            addCriterion("pqtyP =", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypNotEqualTo(Integer value) {
            addCriterion("pqtyP <>", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypGreaterThan(Integer value) {
            addCriterion("pqtyP >", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypGreaterThanOrEqualTo(Integer value) {
            addCriterion("pqtyP >=", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypLessThan(Integer value) {
            addCriterion("pqtyP <", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypLessThanOrEqualTo(Integer value) {
            addCriterion("pqtyP <=", value, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypIn(List<Integer> values) {
            addCriterion("pqtyP in", values, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypNotIn(List<Integer> values) {
            addCriterion("pqtyP not in", values, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypBetween(Integer value1, Integer value2) {
            addCriterion("pqtyP between", value1, value2, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andPqtypNotBetween(Integer value1, Integer value2) {
            addCriterion("pqtyP not between", value1, value2, "pqtyp");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("Name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("Name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("Name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("Name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("Name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("Name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("Name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("Name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("Name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("Name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("Name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("Name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("Name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("Name not between", value1, value2, "name");
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
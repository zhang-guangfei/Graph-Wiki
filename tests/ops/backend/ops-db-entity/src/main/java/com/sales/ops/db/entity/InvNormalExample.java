package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.List;

public class InvNormalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvNormalExample() {
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

        public Criteria andInventoryPropertyIdIsNull() {
            addCriterion("inventory_property_id is null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIsNotNull() {
            addCriterion("inventory_property_id is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdEqualTo(Long value) {
            addCriterion("inventory_property_id =", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotEqualTo(Long value) {
            addCriterion("inventory_property_id <>", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThan(Long value) {
            addCriterion("inventory_property_id >", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id >=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThan(Long value) {
            addCriterion("inventory_property_id <", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("inventory_property_id <=", value, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdIn(List<Long> values) {
            addCriterion("inventory_property_id in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotIn(List<Long> values) {
            addCriterion("inventory_property_id not in", values, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id between", value1, value2, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andInventoryPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("inventory_property_id not between", value1, value2, "inventoryPropertyId");
            return (Criteria) this;
        }

        public Criteria andQtyNormalIsNull() {
            addCriterion("qty_normal is null");
            return (Criteria) this;
        }

        public Criteria andQtyNormalIsNotNull() {
            addCriterion("qty_normal is not null");
            return (Criteria) this;
        }

        public Criteria andQtyNormalEqualTo(Integer value) {
            addCriterion("qty_normal =", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalNotEqualTo(Integer value) {
            addCriterion("qty_normal <>", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalGreaterThan(Integer value) {
            addCriterion("qty_normal >", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_normal >=", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalLessThan(Integer value) {
            addCriterion("qty_normal <", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalLessThanOrEqualTo(Integer value) {
            addCriterion("qty_normal <=", value, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalIn(List<Integer> values) {
            addCriterion("qty_normal in", values, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalNotIn(List<Integer> values) {
            addCriterion("qty_normal not in", values, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalBetween(Integer value1, Integer value2) {
            addCriterion("qty_normal between", value1, value2, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyNormalNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_normal not between", value1, value2, "qtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalIsNull() {
            addCriterion("preqty_normal is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalIsNotNull() {
            addCriterion("preqty_normal is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalEqualTo(Integer value) {
            addCriterion("preqty_normal =", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalNotEqualTo(Integer value) {
            addCriterion("preqty_normal <>", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalGreaterThan(Integer value) {
            addCriterion("preqty_normal >", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalGreaterThanOrEqualTo(Integer value) {
            addCriterion("preqty_normal >=", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalLessThan(Integer value) {
            addCriterion("preqty_normal <", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalLessThanOrEqualTo(Integer value) {
            addCriterion("preqty_normal <=", value, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalIn(List<Integer> values) {
            addCriterion("preqty_normal in", values, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalNotIn(List<Integer> values) {
            addCriterion("preqty_normal not in", values, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalBetween(Integer value1, Integer value2) {
            addCriterion("preqty_normal between", value1, value2, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andPreqtyNormalNotBetween(Integer value1, Integer value2) {
            addCriterion("preqty_normal not between", value1, value2, "preqtyNormal");
            return (Criteria) this;
        }

        public Criteria andQtyWIsNull() {
            addCriterion("qty_w is null");
            return (Criteria) this;
        }

        public Criteria andQtyWIsNotNull() {
            addCriterion("qty_w is not null");
            return (Criteria) this;
        }

        public Criteria andQtyWEqualTo(Integer value) {
            addCriterion("qty_w =", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWNotEqualTo(Integer value) {
            addCriterion("qty_w <>", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWGreaterThan(Integer value) {
            addCriterion("qty_w >", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_w >=", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWLessThan(Integer value) {
            addCriterion("qty_w <", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWLessThanOrEqualTo(Integer value) {
            addCriterion("qty_w <=", value, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWIn(List<Integer> values) {
            addCriterion("qty_w in", values, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWNotIn(List<Integer> values) {
            addCriterion("qty_w not in", values, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWBetween(Integer value1, Integer value2) {
            addCriterion("qty_w between", value1, value2, "qtyW");
            return (Criteria) this;
        }

        public Criteria andQtyWNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_w not between", value1, value2, "qtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWIsNull() {
            addCriterion("preqty_w is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyWIsNotNull() {
            addCriterion("preqty_w is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyWEqualTo(Integer value) {
            addCriterion("preqty_w =", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWNotEqualTo(Integer value) {
            addCriterion("preqty_w <>", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWGreaterThan(Integer value) {
            addCriterion("preqty_w >", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWGreaterThanOrEqualTo(Integer value) {
            addCriterion("preqty_w >=", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWLessThan(Integer value) {
            addCriterion("preqty_w <", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWLessThanOrEqualTo(Integer value) {
            addCriterion("preqty_w <=", value, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWIn(List<Integer> values) {
            addCriterion("preqty_w in", values, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWNotIn(List<Integer> values) {
            addCriterion("preqty_w not in", values, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWBetween(Integer value1, Integer value2) {
            addCriterion("preqty_w between", value1, value2, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andPreqtyWNotBetween(Integer value1, Integer value2) {
            addCriterion("preqty_w not between", value1, value2, "preqtyW");
            return (Criteria) this;
        }

        public Criteria andQtyProductIsNull() {
            addCriterion("qty_product is null");
            return (Criteria) this;
        }

        public Criteria andQtyProductIsNotNull() {
            addCriterion("qty_product is not null");
            return (Criteria) this;
        }

        public Criteria andQtyProductEqualTo(Integer value) {
            addCriterion("qty_product =", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductNotEqualTo(Integer value) {
            addCriterion("qty_product <>", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductGreaterThan(Integer value) {
            addCriterion("qty_product >", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_product >=", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductLessThan(Integer value) {
            addCriterion("qty_product <", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductLessThanOrEqualTo(Integer value) {
            addCriterion("qty_product <=", value, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductIn(List<Integer> values) {
            addCriterion("qty_product in", values, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductNotIn(List<Integer> values) {
            addCriterion("qty_product not in", values, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductBetween(Integer value1, Integer value2) {
            addCriterion("qty_product between", value1, value2, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyProductNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_product not between", value1, value2, "qtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductIsNull() {
            addCriterion("preqty_product is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductIsNotNull() {
            addCriterion("preqty_product is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductEqualTo(Integer value) {
            addCriterion("preqty_product =", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductNotEqualTo(Integer value) {
            addCriterion("preqty_product <>", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductGreaterThan(Integer value) {
            addCriterion("preqty_product >", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductGreaterThanOrEqualTo(Integer value) {
            addCriterion("preqty_product >=", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductLessThan(Integer value) {
            addCriterion("preqty_product <", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductLessThanOrEqualTo(Integer value) {
            addCriterion("preqty_product <=", value, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductIn(List<Integer> values) {
            addCriterion("preqty_product in", values, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductNotIn(List<Integer> values) {
            addCriterion("preqty_product not in", values, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductBetween(Integer value1, Integer value2) {
            addCriterion("preqty_product between", value1, value2, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andPreqtyProductNotBetween(Integer value1, Integer value2) {
            addCriterion("preqty_product not between", value1, value2, "preqtyProduct");
            return (Criteria) this;
        }

        public Criteria andQtyDbIsNull() {
            addCriterion("qty_db is null");
            return (Criteria) this;
        }

        public Criteria andQtyDbIsNotNull() {
            addCriterion("qty_db is not null");
            return (Criteria) this;
        }

        public Criteria andQtyDbEqualTo(Integer value) {
            addCriterion("qty_db =", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbNotEqualTo(Integer value) {
            addCriterion("qty_db <>", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbGreaterThan(Integer value) {
            addCriterion("qty_db >", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_db >=", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbLessThan(Integer value) {
            addCriterion("qty_db <", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbLessThanOrEqualTo(Integer value) {
            addCriterion("qty_db <=", value, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbIn(List<Integer> values) {
            addCriterion("qty_db in", values, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbNotIn(List<Integer> values) {
            addCriterion("qty_db not in", values, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbBetween(Integer value1, Integer value2) {
            addCriterion("qty_db between", value1, value2, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyDbNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_db not between", value1, value2, "qtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbIsNull() {
            addCriterion("preqty_db is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbIsNotNull() {
            addCriterion("preqty_db is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbEqualTo(Integer value) {
            addCriterion("preqty_db =", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbNotEqualTo(Integer value) {
            addCriterion("preqty_db <>", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbGreaterThan(Integer value) {
            addCriterion("preqty_db >", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbGreaterThanOrEqualTo(Integer value) {
            addCriterion("preqty_db >=", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbLessThan(Integer value) {
            addCriterion("preqty_db <", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbLessThanOrEqualTo(Integer value) {
            addCriterion("preqty_db <=", value, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbIn(List<Integer> values) {
            addCriterion("preqty_db in", values, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbNotIn(List<Integer> values) {
            addCriterion("preqty_db not in", values, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbBetween(Integer value1, Integer value2) {
            addCriterion("preqty_db between", value1, value2, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andPreqtyDbNotBetween(Integer value1, Integer value2) {
            addCriterion("preqty_db not between", value1, value2, "preqtyDb");
            return (Criteria) this;
        }

        public Criteria andQtyCgIsNull() {
            addCriterion("qty_cg is null");
            return (Criteria) this;
        }

        public Criteria andQtyCgIsNotNull() {
            addCriterion("qty_cg is not null");
            return (Criteria) this;
        }

        public Criteria andQtyCgEqualTo(Integer value) {
            addCriterion("qty_cg =", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgNotEqualTo(Integer value) {
            addCriterion("qty_cg <>", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgGreaterThan(Integer value) {
            addCriterion("qty_cg >", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty_cg >=", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgLessThan(Integer value) {
            addCriterion("qty_cg <", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgLessThanOrEqualTo(Integer value) {
            addCriterion("qty_cg <=", value, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgIn(List<Integer> values) {
            addCriterion("qty_cg in", values, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgNotIn(List<Integer> values) {
            addCriterion("qty_cg not in", values, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgBetween(Integer value1, Integer value2) {
            addCriterion("qty_cg between", value1, value2, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andQtyCgNotBetween(Integer value1, Integer value2) {
            addCriterion("qty_cg not between", value1, value2, "qtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgIsNull() {
            addCriterion("preqty_cg is null");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgIsNotNull() {
            addCriterion("preqty_cg is not null");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgEqualTo(Integer value) {
            addCriterion("preqty_cg =", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgNotEqualTo(Integer value) {
            addCriterion("preqty_cg <>", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgGreaterThan(Integer value) {
            addCriterion("preqty_cg >", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgGreaterThanOrEqualTo(Integer value) {
            addCriterion("preqty_cg >=", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgLessThan(Integer value) {
            addCriterion("preqty_cg <", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgLessThanOrEqualTo(Integer value) {
            addCriterion("preqty_cg <=", value, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgIn(List<Integer> values) {
            addCriterion("preqty_cg in", values, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgNotIn(List<Integer> values) {
            addCriterion("preqty_cg not in", values, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgBetween(Integer value1, Integer value2) {
            addCriterion("preqty_cg between", value1, value2, "preqtyCg");
            return (Criteria) this;
        }

        public Criteria andPreqtyCgNotBetween(Integer value1, Integer value2) {
            addCriterion("preqty_cg not between", value1, value2, "preqtyCg");
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
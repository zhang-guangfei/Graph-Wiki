package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InventoryRelationOrderViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryRelationOrderViewExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andDeptCodeIsNull() {
            addCriterion("dept_code is null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNotNull() {
            addCriterion("dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeEqualTo(String value) {
            addCriterion("dept_code =", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotEqualTo(String value) {
            addCriterion("dept_code <>", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThan(String value) {
            addCriterion("dept_code >", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dept_code >=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThan(String value) {
            addCriterion("dept_code <", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("dept_code <=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLike(String value) {
            addCriterion("dept_code like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotLike(String value) {
            addCriterion("dept_code not like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIn(List<String> values) {
            addCriterion("dept_code in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotIn(List<String> values) {
            addCriterion("dept_code not in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeBetween(String value1, String value2) {
            addCriterion("dept_code between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotBetween(String value1, String value2) {
            addCriterion("dept_code not between", value1, value2, "deptCode");
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

        public Criteria andInvQtyIsNull() {
            addCriterion("inv_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvQtyIsNotNull() {
            addCriterion("inv_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvQtyEqualTo(Integer value) {
            addCriterion("inv_qty =", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyNotEqualTo(Integer value) {
            addCriterion("inv_qty <>", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyGreaterThan(Integer value) {
            addCriterion("inv_qty >", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("inv_qty >=", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyLessThan(Integer value) {
            addCriterion("inv_qty <", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyLessThanOrEqualTo(Integer value) {
            addCriterion("inv_qty <=", value, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyIn(List<Integer> values) {
            addCriterion("inv_qty in", values, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyNotIn(List<Integer> values) {
            addCriterion("inv_qty not in", values, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyBetween(Integer value1, Integer value2) {
            addCriterion("inv_qty between", value1, value2, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("inv_qty not between", value1, value2, "invQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyIsNull() {
            addCriterion("inv_pre_qty is null");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyIsNotNull() {
            addCriterion("inv_pre_qty is not null");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyEqualTo(Integer value) {
            addCriterion("inv_pre_qty =", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyNotEqualTo(Integer value) {
            addCriterion("inv_pre_qty <>", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyGreaterThan(Integer value) {
            addCriterion("inv_pre_qty >", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("inv_pre_qty >=", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyLessThan(Integer value) {
            addCriterion("inv_pre_qty <", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyLessThanOrEqualTo(Integer value) {
            addCriterion("inv_pre_qty <=", value, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyIn(List<Integer> values) {
            addCriterion("inv_pre_qty in", values, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyNotIn(List<Integer> values) {
            addCriterion("inv_pre_qty not in", values, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyBetween(Integer value1, Integer value2) {
            addCriterion("inv_pre_qty between", value1, value2, "invPreQty");
            return (Criteria) this;
        }

        public Criteria andInvPreQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("inv_pre_qty not between", value1, value2, "invPreQty");
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNull() {
            addCriterion("order_item is null");
            return (Criteria) this;
        }

        public Criteria andOrderItemIsNotNull() {
            addCriterion("order_item is not null");
            return (Criteria) this;
        }

        public Criteria andOrderItemEqualTo(String value) {
            addCriterion("order_item =", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotEqualTo(String value) {
            addCriterion("order_item <>", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThan(String value) {
            addCriterion("order_item >", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemGreaterThanOrEqualTo(String value) {
            addCriterion("order_item >=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThan(String value) {
            addCriterion("order_item <", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLessThanOrEqualTo(String value) {
            addCriterion("order_item <=", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemLike(String value) {
            addCriterion("order_item like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotLike(String value) {
            addCriterion("order_item not like", value, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemIn(List<String> values) {
            addCriterion("order_item in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotIn(List<String> values) {
            addCriterion("order_item not in", values, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemBetween(String value1, String value2) {
            addCriterion("order_item between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andOrderItemNotBetween(String value1, String value2) {
            addCriterion("order_item not between", value1, value2, "orderItem");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andAssNumIsNull() {
            addCriterion("ass_num is null");
            return (Criteria) this;
        }

        public Criteria andAssNumIsNotNull() {
            addCriterion("ass_num is not null");
            return (Criteria) this;
        }

        public Criteria andAssNumEqualTo(Integer value) {
            addCriterion("ass_num =", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotEqualTo(Integer value) {
            addCriterion("ass_num <>", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumGreaterThan(Integer value) {
            addCriterion("ass_num >", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ass_num >=", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumLessThan(Integer value) {
            addCriterion("ass_num <", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumLessThanOrEqualTo(Integer value) {
            addCriterion("ass_num <=", value, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumIn(List<Integer> values) {
            addCriterion("ass_num in", values, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotIn(List<Integer> values) {
            addCriterion("ass_num not in", values, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumBetween(Integer value1, Integer value2) {
            addCriterion("ass_num between", value1, value2, "assNum");
            return (Criteria) this;
        }

        public Criteria andAssNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ass_num not between", value1, value2, "assNum");
            return (Criteria) this;
        }

        public Criteria andDoSourceIsNull() {
            addCriterion("do_source is null");
            return (Criteria) this;
        }

        public Criteria andDoSourceIsNotNull() {
            addCriterion("do_source is not null");
            return (Criteria) this;
        }

        public Criteria andDoSourceEqualTo(String value) {
            addCriterion("do_source =", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotEqualTo(String value) {
            addCriterion("do_source <>", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceGreaterThan(String value) {
            addCriterion("do_source >", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceGreaterThanOrEqualTo(String value) {
            addCriterion("do_source >=", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLessThan(String value) {
            addCriterion("do_source <", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLessThanOrEqualTo(String value) {
            addCriterion("do_source <=", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceLike(String value) {
            addCriterion("do_source like", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotLike(String value) {
            addCriterion("do_source not like", value, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceIn(List<String> values) {
            addCriterion("do_source in", values, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotIn(List<String> values) {
            addCriterion("do_source not in", values, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceBetween(String value1, String value2) {
            addCriterion("do_source between", value1, value2, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoSourceNotBetween(String value1, String value2) {
            addCriterion("do_source not between", value1, value2, "doSource");
            return (Criteria) this;
        }

        public Criteria andDoTypeIsNull() {
            addCriterion("do_type is null");
            return (Criteria) this;
        }

        public Criteria andDoTypeIsNotNull() {
            addCriterion("do_type is not null");
            return (Criteria) this;
        }

        public Criteria andDoTypeEqualTo(String value) {
            addCriterion("do_type =", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotEqualTo(String value) {
            addCriterion("do_type <>", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeGreaterThan(String value) {
            addCriterion("do_type >", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeGreaterThanOrEqualTo(String value) {
            addCriterion("do_type >=", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLessThan(String value) {
            addCriterion("do_type <", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLessThanOrEqualTo(String value) {
            addCriterion("do_type <=", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeLike(String value) {
            addCriterion("do_type like", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotLike(String value) {
            addCriterion("do_type not like", value, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeIn(List<String> values) {
            addCriterion("do_type in", values, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotIn(List<String> values) {
            addCriterion("do_type not in", values, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeBetween(String value1, String value2) {
            addCriterion("do_type between", value1, value2, "doType");
            return (Criteria) this;
        }

        public Criteria andDoTypeNotBetween(String value1, String value2) {
            addCriterion("do_type not between", value1, value2, "doType");
            return (Criteria) this;
        }

        public Criteria andDoQtyIsNull() {
            addCriterion("do_qty is null");
            return (Criteria) this;
        }

        public Criteria andDoQtyIsNotNull() {
            addCriterion("do_qty is not null");
            return (Criteria) this;
        }

        public Criteria andDoQtyEqualTo(Integer value) {
            addCriterion("do_qty =", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyNotEqualTo(Integer value) {
            addCriterion("do_qty <>", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyGreaterThan(Integer value) {
            addCriterion("do_qty >", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("do_qty >=", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyLessThan(Integer value) {
            addCriterion("do_qty <", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyLessThanOrEqualTo(Integer value) {
            addCriterion("do_qty <=", value, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyIn(List<Integer> values) {
            addCriterion("do_qty in", values, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyNotIn(List<Integer> values) {
            addCriterion("do_qty not in", values, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyBetween(Integer value1, Integer value2) {
            addCriterion("do_qty between", value1, value2, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("do_qty not between", value1, value2, "doQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyIsNull() {
            addCriterion("do_use_qty is null");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyIsNotNull() {
            addCriterion("do_use_qty is not null");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyEqualTo(Integer value) {
            addCriterion("do_use_qty =", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyNotEqualTo(Integer value) {
            addCriterion("do_use_qty <>", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyGreaterThan(Integer value) {
            addCriterion("do_use_qty >", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("do_use_qty >=", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyLessThan(Integer value) {
            addCriterion("do_use_qty <", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyLessThanOrEqualTo(Integer value) {
            addCriterion("do_use_qty <=", value, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyIn(List<Integer> values) {
            addCriterion("do_use_qty in", values, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyNotIn(List<Integer> values) {
            addCriterion("do_use_qty not in", values, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyBetween(Integer value1, Integer value2) {
            addCriterion("do_use_qty between", value1, value2, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andDoUseQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("do_use_qty not between", value1, value2, "doUseQty");
            return (Criteria) this;
        }

        public Criteria andHopeDateIsNull() {
            addCriterion("hope_date is null");
            return (Criteria) this;
        }

        public Criteria andHopeDateIsNotNull() {
            addCriterion("hope_date is not null");
            return (Criteria) this;
        }

        public Criteria andHopeDateEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date =", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date <>", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hope_date >", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date >=", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateLessThan(Date value) {
            addCriterionForJDBCDate("hope_date <", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hope_date <=", value, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateIn(List<Date> values) {
            addCriterionForJDBCDate("hope_date in", values, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hope_date not in", values, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_date between", value1, value2, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andHopeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hope_date not between", value1, value2, "hopeDate");
            return (Criteria) this;
        }

        public Criteria andCustomerIsNull() {
            addCriterion("customer is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIsNotNull() {
            addCriterion("customer is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerEqualTo(String value) {
            addCriterion("customer =", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerNotEqualTo(String value) {
            addCriterion("customer <>", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerGreaterThan(String value) {
            addCriterion("customer >", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerGreaterThanOrEqualTo(String value) {
            addCriterion("customer >=", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerLessThan(String value) {
            addCriterion("customer <", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerLessThanOrEqualTo(String value) {
            addCriterion("customer <=", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerLike(String value) {
            addCriterion("customer like", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerNotLike(String value) {
            addCriterion("customer not like", value, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerIn(List<String> values) {
            addCriterion("customer in", values, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerNotIn(List<String> values) {
            addCriterion("customer not in", values, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerBetween(String value1, String value2) {
            addCriterion("customer between", value1, value2, "customer");
            return (Criteria) this;
        }

        public Criteria andCustomerNotBetween(String value1, String value2) {
            addCriterion("customer not between", value1, value2, "customer");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_no =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_no <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_no >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_no >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_no <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_no <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_no like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_no not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_no in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_no not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_no between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_no not between", value1, value2, "deptNo");
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

        public Criteria andWlDateIsNull() {
            addCriterion("wl_date is null");
            return (Criteria) this;
        }

        public Criteria andWlDateIsNotNull() {
            addCriterion("wl_date is not null");
            return (Criteria) this;
        }

        public Criteria andWlDateEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date =", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <>", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThan(Date value) {
            addCriterionForJDBCDate("wl_date >", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date >=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThan(Date value) {
            addCriterionForJDBCDate("wl_date <", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wl_date <=", value, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wl_date not in", values, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date between", value1, value2, "wlDate");
            return (Criteria) this;
        }

        public Criteria andWlDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wl_date not between", value1, value2, "wlDate");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNull() {
            addCriterion("dlv_entire is null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIsNotNull() {
            addCriterion("dlv_entire is not null");
            return (Criteria) this;
        }

        public Criteria andDlvEntireEqualTo(String value) {
            addCriterion("dlv_entire =", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotEqualTo(String value) {
            addCriterion("dlv_entire <>", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThan(String value) {
            addCriterion("dlv_entire >", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireGreaterThanOrEqualTo(String value) {
            addCriterion("dlv_entire >=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThan(String value) {
            addCriterion("dlv_entire <", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLessThanOrEqualTo(String value) {
            addCriterion("dlv_entire <=", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireLike(String value) {
            addCriterion("dlv_entire like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotLike(String value) {
            addCriterion("dlv_entire not like", value, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireIn(List<String> values) {
            addCriterion("dlv_entire in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotIn(List<String> values) {
            addCriterion("dlv_entire not in", values, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireBetween(String value1, String value2) {
            addCriterion("dlv_entire between", value1, value2, "dlvEntire");
            return (Criteria) this;
        }

        public Criteria andDlvEntireNotBetween(String value1, String value2) {
            addCriterion("dlv_entire not between", value1, value2, "dlvEntire");
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
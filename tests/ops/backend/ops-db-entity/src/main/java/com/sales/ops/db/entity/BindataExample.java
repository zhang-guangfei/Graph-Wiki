package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BindataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BindataExample() {
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

        public Criteria andStocktypeIsNull() {
            addCriterion("StockType is null");
            return (Criteria) this;
        }

        public Criteria andStocktypeIsNotNull() {
            addCriterion("StockType is not null");
            return (Criteria) this;
        }

        public Criteria andStocktypeEqualTo(Integer value) {
            addCriterion("StockType =", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeNotEqualTo(Integer value) {
            addCriterion("StockType <>", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeGreaterThan(Integer value) {
            addCriterion("StockType >", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("StockType >=", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeLessThan(Integer value) {
            addCriterion("StockType <", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeLessThanOrEqualTo(Integer value) {
            addCriterion("StockType <=", value, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeIn(List<Integer> values) {
            addCriterion("StockType in", values, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeNotIn(List<Integer> values) {
            addCriterion("StockType not in", values, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeBetween(Integer value1, Integer value2) {
            addCriterion("StockType between", value1, value2, "stocktype");
            return (Criteria) this;
        }

        public Criteria andStocktypeNotBetween(Integer value1, Integer value2) {
            addCriterion("StockType not between", value1, value2, "stocktype");
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

        public Criteria andModelnoIsNull() {
            addCriterion("ModelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("ModelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("ModelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("ModelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("ModelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("ModelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("ModelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("ModelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("ModelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("ModelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("ModelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("ModelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("ModelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("CustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("CustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("CustomerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("CustomerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("CustomerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("CustomerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("CustomerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("CustomerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("CustomerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("CustomerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("CustomerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("CustomerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("CustomerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("Property_ID is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("Property_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Long value) {
            addCriterion("Property_ID =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Long value) {
            addCriterion("Property_ID <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Long value) {
            addCriterion("Property_ID >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Property_ID >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Long value) {
            addCriterion("Property_ID <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("Property_ID <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Long> values) {
            addCriterion("Property_ID in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Long> values) {
            addCriterion("Property_ID not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Long value1, Long value2) {
            addCriterion("Property_ID between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("Property_ID not between", value1, value2, "propertyId");
            return (Criteria) this;
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

        public Criteria andQtybinIsNull() {
            addCriterion("QtyBin is null");
            return (Criteria) this;
        }

        public Criteria andQtybinIsNotNull() {
            addCriterion("QtyBin is not null");
            return (Criteria) this;
        }

        public Criteria andQtybinEqualTo(Integer value) {
            addCriterion("QtyBin =", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotEqualTo(Integer value) {
            addCriterion("QtyBin <>", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinGreaterThan(Integer value) {
            addCriterion("QtyBin >", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyBin >=", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinLessThan(Integer value) {
            addCriterion("QtyBin <", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinLessThanOrEqualTo(Integer value) {
            addCriterion("QtyBin <=", value, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinIn(List<Integer> values) {
            addCriterion("QtyBin in", values, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotIn(List<Integer> values) {
            addCriterion("QtyBin not in", values, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinBetween(Integer value1, Integer value2) {
            addCriterion("QtyBin between", value1, value2, "qtybin");
            return (Criteria) this;
        }

        public Criteria andQtybinNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyBin not between", value1, value2, "qtybin");
            return (Criteria) this;
        }

        public Criteria andBincellIsNull() {
            addCriterion("BinCell is null");
            return (Criteria) this;
        }

        public Criteria andBincellIsNotNull() {
            addCriterion("BinCell is not null");
            return (Criteria) this;
        }

        public Criteria andBincellEqualTo(Integer value) {
            addCriterion("BinCell =", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotEqualTo(Integer value) {
            addCriterion("BinCell <>", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellGreaterThan(Integer value) {
            addCriterion("BinCell >", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellGreaterThanOrEqualTo(Integer value) {
            addCriterion("BinCell >=", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellLessThan(Integer value) {
            addCriterion("BinCell <", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellLessThanOrEqualTo(Integer value) {
            addCriterion("BinCell <=", value, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellIn(List<Integer> values) {
            addCriterion("BinCell in", values, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotIn(List<Integer> values) {
            addCriterion("BinCell not in", values, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellBetween(Integer value1, Integer value2) {
            addCriterion("BinCell between", value1, value2, "bincell");
            return (Criteria) this;
        }

        public Criteria andBincellNotBetween(Integer value1, Integer value2) {
            addCriterion("BinCell not between", value1, value2, "bincell");
            return (Criteria) this;
        }

        public Criteria andCasetypeIsNull() {
            addCriterion("CaseType is null");
            return (Criteria) this;
        }

        public Criteria andCasetypeIsNotNull() {
            addCriterion("CaseType is not null");
            return (Criteria) this;
        }

        public Criteria andCasetypeEqualTo(String value) {
            addCriterion("CaseType =", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeNotEqualTo(String value) {
            addCriterion("CaseType <>", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeGreaterThan(String value) {
            addCriterion("CaseType >", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeGreaterThanOrEqualTo(String value) {
            addCriterion("CaseType >=", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeLessThan(String value) {
            addCriterion("CaseType <", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeLessThanOrEqualTo(String value) {
            addCriterion("CaseType <=", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeLike(String value) {
            addCriterion("CaseType like", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeNotLike(String value) {
            addCriterion("CaseType not like", value, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeIn(List<String> values) {
            addCriterion("CaseType in", values, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeNotIn(List<String> values) {
            addCriterion("CaseType not in", values, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeBetween(String value1, String value2) {
            addCriterion("CaseType between", value1, value2, "casetype");
            return (Criteria) this;
        }

        public Criteria andCasetypeNotBetween(String value1, String value2) {
            addCriterion("CaseType not between", value1, value2, "casetype");
            return (Criteria) this;
        }

        public Criteria andProdtypeIsNull() {
            addCriterion("ProdType is null");
            return (Criteria) this;
        }

        public Criteria andProdtypeIsNotNull() {
            addCriterion("ProdType is not null");
            return (Criteria) this;
        }

        public Criteria andProdtypeEqualTo(String value) {
            addCriterion("ProdType =", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotEqualTo(String value) {
            addCriterion("ProdType <>", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeGreaterThan(String value) {
            addCriterion("ProdType >", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ProdType >=", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeLessThan(String value) {
            addCriterion("ProdType <", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeLessThanOrEqualTo(String value) {
            addCriterion("ProdType <=", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeLike(String value) {
            addCriterion("ProdType like", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotLike(String value) {
            addCriterion("ProdType not like", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeIn(List<String> values) {
            addCriterion("ProdType in", values, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotIn(List<String> values) {
            addCriterion("ProdType not in", values, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeBetween(String value1, String value2) {
            addCriterion("ProdType between", value1, value2, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotBetween(String value1, String value2) {
            addCriterion("ProdType not between", value1, value2, "prodtype");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andPositionnoIsNull() {
            addCriterion("PositionNo is null");
            return (Criteria) this;
        }

        public Criteria andPositionnoIsNotNull() {
            addCriterion("PositionNo is not null");
            return (Criteria) this;
        }

        public Criteria andPositionnoEqualTo(String value) {
            addCriterion("PositionNo =", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoNotEqualTo(String value) {
            addCriterion("PositionNo <>", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoGreaterThan(String value) {
            addCriterion("PositionNo >", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoGreaterThanOrEqualTo(String value) {
            addCriterion("PositionNo >=", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoLessThan(String value) {
            addCriterion("PositionNo <", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoLessThanOrEqualTo(String value) {
            addCriterion("PositionNo <=", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoLike(String value) {
            addCriterion("PositionNo like", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoNotLike(String value) {
            addCriterion("PositionNo not like", value, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoIn(List<String> values) {
            addCriterion("PositionNo in", values, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoNotIn(List<String> values) {
            addCriterion("PositionNo not in", values, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoBetween(String value1, String value2) {
            addCriterion("PositionNo between", value1, value2, "positionno");
            return (Criteria) this;
        }

        public Criteria andPositionnoNotBetween(String value1, String value2) {
            addCriterion("PositionNo not between", value1, value2, "positionno");
            return (Criteria) this;
        }

        public Criteria andMeshtypeIsNull() {
            addCriterion("MeshType is null");
            return (Criteria) this;
        }

        public Criteria andMeshtypeIsNotNull() {
            addCriterion("MeshType is not null");
            return (Criteria) this;
        }

        public Criteria andMeshtypeEqualTo(String value) {
            addCriterion("MeshType =", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeNotEqualTo(String value) {
            addCriterion("MeshType <>", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeGreaterThan(String value) {
            addCriterion("MeshType >", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeGreaterThanOrEqualTo(String value) {
            addCriterion("MeshType >=", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeLessThan(String value) {
            addCriterion("MeshType <", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeLessThanOrEqualTo(String value) {
            addCriterion("MeshType <=", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeLike(String value) {
            addCriterion("MeshType like", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeNotLike(String value) {
            addCriterion("MeshType not like", value, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeIn(List<String> values) {
            addCriterion("MeshType in", values, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeNotIn(List<String> values) {
            addCriterion("MeshType not in", values, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeBetween(String value1, String value2) {
            addCriterion("MeshType between", value1, value2, "meshtype");
            return (Criteria) this;
        }

        public Criteria andMeshtypeNotBetween(String value1, String value2) {
            addCriterion("MeshType not between", value1, value2, "meshtype");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyIsNull() {
            addCriterion("InCaseQty is null");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyIsNotNull() {
            addCriterion("InCaseQty is not null");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyEqualTo(Integer value) {
            addCriterion("InCaseQty =", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyNotEqualTo(Integer value) {
            addCriterion("InCaseQty <>", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyGreaterThan(Integer value) {
            addCriterion("InCaseQty >", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("InCaseQty >=", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyLessThan(Integer value) {
            addCriterion("InCaseQty <", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyLessThanOrEqualTo(Integer value) {
            addCriterion("InCaseQty <=", value, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyIn(List<Integer> values) {
            addCriterion("InCaseQty in", values, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyNotIn(List<Integer> values) {
            addCriterion("InCaseQty not in", values, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyBetween(Integer value1, Integer value2) {
            addCriterion("InCaseQty between", value1, value2, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andIncaseqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("InCaseQty not between", value1, value2, "incaseqty");
            return (Criteria) this;
        }

        public Criteria andAdjustableIsNull() {
            addCriterion("Adjustable is null");
            return (Criteria) this;
        }

        public Criteria andAdjustableIsNotNull() {
            addCriterion("Adjustable is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustableEqualTo(String value) {
            addCriterion("Adjustable =", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableNotEqualTo(String value) {
            addCriterion("Adjustable <>", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableGreaterThan(String value) {
            addCriterion("Adjustable >", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableGreaterThanOrEqualTo(String value) {
            addCriterion("Adjustable >=", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableLessThan(String value) {
            addCriterion("Adjustable <", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableLessThanOrEqualTo(String value) {
            addCriterion("Adjustable <=", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableLike(String value) {
            addCriterion("Adjustable like", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableNotLike(String value) {
            addCriterion("Adjustable not like", value, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableIn(List<String> values) {
            addCriterion("Adjustable in", values, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableNotIn(List<String> values) {
            addCriterion("Adjustable not in", values, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableBetween(String value1, String value2) {
            addCriterion("Adjustable between", value1, value2, "adjustable");
            return (Criteria) this;
        }

        public Criteria andAdjustableNotBetween(String value1, String value2) {
            addCriterion("Adjustable not between", value1, value2, "adjustable");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNull() {
            addCriterion("delFlag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delFlag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Short value) {
            addCriterion("delFlag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Short value) {
            addCriterion("delFlag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Short value) {
            addCriterion("delFlag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Short value) {
            addCriterion("delFlag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Short value) {
            addCriterion("delFlag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Short value) {
            addCriterion("delFlag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Short> values) {
            addCriterion("delFlag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Short> values) {
            addCriterion("delFlag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Short value1, Short value2) {
            addCriterion("delFlag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Short value1, Short value2) {
            addCriterion("delFlag not between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagIsNull() {
            addCriterion("LastdelFlag is null");
            return (Criteria) this;
        }

        public Criteria andLastdelflagIsNotNull() {
            addCriterion("LastdelFlag is not null");
            return (Criteria) this;
        }

        public Criteria andLastdelflagEqualTo(Short value) {
            addCriterion("LastdelFlag =", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagNotEqualTo(Short value) {
            addCriterion("LastdelFlag <>", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagGreaterThan(Short value) {
            addCriterion("LastdelFlag >", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagGreaterThanOrEqualTo(Short value) {
            addCriterion("LastdelFlag >=", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagLessThan(Short value) {
            addCriterion("LastdelFlag <", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagLessThanOrEqualTo(Short value) {
            addCriterion("LastdelFlag <=", value, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagIn(List<Short> values) {
            addCriterion("LastdelFlag in", values, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagNotIn(List<Short> values) {
            addCriterion("LastdelFlag not in", values, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagBetween(Short value1, Short value2) {
            addCriterion("LastdelFlag between", value1, value2, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andLastdelflagNotBetween(Short value1, Short value2) {
            addCriterion("LastdelFlag not between", value1, value2, "lastdelflag");
            return (Criteria) this;
        }

        public Criteria andBintypeIsNull() {
            addCriterion("BinType is null");
            return (Criteria) this;
        }

        public Criteria andBintypeIsNotNull() {
            addCriterion("BinType is not null");
            return (Criteria) this;
        }

        public Criteria andBintypeEqualTo(String value) {
            addCriterion("BinType =", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeNotEqualTo(String value) {
            addCriterion("BinType <>", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeGreaterThan(String value) {
            addCriterion("BinType >", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeGreaterThanOrEqualTo(String value) {
            addCriterion("BinType >=", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeLessThan(String value) {
            addCriterion("BinType <", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeLessThanOrEqualTo(String value) {
            addCriterion("BinType <=", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeLike(String value) {
            addCriterion("BinType like", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeNotLike(String value) {
            addCriterion("BinType not like", value, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeIn(List<String> values) {
            addCriterion("BinType in", values, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeNotIn(List<String> values) {
            addCriterion("BinType not in", values, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeBetween(String value1, String value2) {
            addCriterion("BinType between", value1, value2, "bintype");
            return (Criteria) this;
        }

        public Criteria andBintypeNotBetween(String value1, String value2) {
            addCriterion("BinType not between", value1, value2, "bintype");
            return (Criteria) this;
        }

        public Criteria andSafeqtyIsNull() {
            addCriterion("safeQty is null");
            return (Criteria) this;
        }

        public Criteria andSafeqtyIsNotNull() {
            addCriterion("safeQty is not null");
            return (Criteria) this;
        }

        public Criteria andSafeqtyEqualTo(Integer value) {
            addCriterion("safeQty =", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyNotEqualTo(Integer value) {
            addCriterion("safeQty <>", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyGreaterThan(Integer value) {
            addCriterion("safeQty >", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("safeQty >=", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyLessThan(Integer value) {
            addCriterion("safeQty <", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyLessThanOrEqualTo(Integer value) {
            addCriterion("safeQty <=", value, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyIn(List<Integer> values) {
            addCriterion("safeQty in", values, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyNotIn(List<Integer> values) {
            addCriterion("safeQty not in", values, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyBetween(Integer value1, Integer value2) {
            addCriterion("safeQty between", value1, value2, "safeqty");
            return (Criteria) this;
        }

        public Criteria andSafeqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("safeQty not between", value1, value2, "safeqty");
            return (Criteria) this;
        }

        public Criteria andFreqIsNull() {
            addCriterion("freq is null");
            return (Criteria) this;
        }

        public Criteria andFreqIsNotNull() {
            addCriterion("freq is not null");
            return (Criteria) this;
        }

        public Criteria andFreqEqualTo(Integer value) {
            addCriterion("freq =", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotEqualTo(Integer value) {
            addCriterion("freq <>", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqGreaterThan(Integer value) {
            addCriterion("freq >", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqGreaterThanOrEqualTo(Integer value) {
            addCriterion("freq >=", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqLessThan(Integer value) {
            addCriterion("freq <", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqLessThanOrEqualTo(Integer value) {
            addCriterion("freq <=", value, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqIn(List<Integer> values) {
            addCriterion("freq in", values, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotIn(List<Integer> values) {
            addCriterion("freq not in", values, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqBetween(Integer value1, Integer value2) {
            addCriterion("freq between", value1, value2, "freq");
            return (Criteria) this;
        }

        public Criteria andFreqNotBetween(Integer value1, Integer value2) {
            addCriterion("freq not between", value1, value2, "freq");
            return (Criteria) this;
        }

        public Criteria andMeanIsNull() {
            addCriterion("mean is null");
            return (Criteria) this;
        }

        public Criteria andMeanIsNotNull() {
            addCriterion("mean is not null");
            return (Criteria) this;
        }

        public Criteria andMeanEqualTo(Integer value) {
            addCriterion("mean =", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotEqualTo(Integer value) {
            addCriterion("mean <>", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanGreaterThan(Integer value) {
            addCriterion("mean >", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanGreaterThanOrEqualTo(Integer value) {
            addCriterion("mean >=", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanLessThan(Integer value) {
            addCriterion("mean <", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanLessThanOrEqualTo(Integer value) {
            addCriterion("mean <=", value, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanIn(List<Integer> values) {
            addCriterion("mean in", values, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotIn(List<Integer> values) {
            addCriterion("mean not in", values, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanBetween(Integer value1, Integer value2) {
            addCriterion("mean between", value1, value2, "mean");
            return (Criteria) this;
        }

        public Criteria andMeanNotBetween(Integer value1, Integer value2) {
            addCriterion("mean not between", value1, value2, "mean");
            return (Criteria) this;
        }

        public Criteria andSetlevelIsNull() {
            addCriterion("SetLevel is null");
            return (Criteria) this;
        }

        public Criteria andSetlevelIsNotNull() {
            addCriterion("SetLevel is not null");
            return (Criteria) this;
        }

        public Criteria andSetlevelEqualTo(String value) {
            addCriterion("SetLevel =", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelNotEqualTo(String value) {
            addCriterion("SetLevel <>", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelGreaterThan(String value) {
            addCriterion("SetLevel >", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelGreaterThanOrEqualTo(String value) {
            addCriterion("SetLevel >=", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelLessThan(String value) {
            addCriterion("SetLevel <", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelLessThanOrEqualTo(String value) {
            addCriterion("SetLevel <=", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelLike(String value) {
            addCriterion("SetLevel like", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelNotLike(String value) {
            addCriterion("SetLevel not like", value, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelIn(List<String> values) {
            addCriterion("SetLevel in", values, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelNotIn(List<String> values) {
            addCriterion("SetLevel not in", values, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelBetween(String value1, String value2) {
            addCriterion("SetLevel between", value1, value2, "setlevel");
            return (Criteria) this;
        }

        public Criteria andSetlevelNotBetween(String value1, String value2) {
            addCriterion("SetLevel not between", value1, value2, "setlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelIsNull() {
            addCriterion("NewLevel is null");
            return (Criteria) this;
        }

        public Criteria andNewlevelIsNotNull() {
            addCriterion("NewLevel is not null");
            return (Criteria) this;
        }

        public Criteria andNewlevelEqualTo(String value) {
            addCriterion("NewLevel =", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelNotEqualTo(String value) {
            addCriterion("NewLevel <>", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelGreaterThan(String value) {
            addCriterion("NewLevel >", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelGreaterThanOrEqualTo(String value) {
            addCriterion("NewLevel >=", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelLessThan(String value) {
            addCriterion("NewLevel <", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelLessThanOrEqualTo(String value) {
            addCriterion("NewLevel <=", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelLike(String value) {
            addCriterion("NewLevel like", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelNotLike(String value) {
            addCriterion("NewLevel not like", value, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelIn(List<String> values) {
            addCriterion("NewLevel in", values, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelNotIn(List<String> values) {
            addCriterion("NewLevel not in", values, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelBetween(String value1, String value2) {
            addCriterion("NewLevel between", value1, value2, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewlevelNotBetween(String value1, String value2) {
            addCriterion("NewLevel not between", value1, value2, "newlevel");
            return (Criteria) this;
        }

        public Criteria andNewfreqIsNull() {
            addCriterion("NewFreq is null");
            return (Criteria) this;
        }

        public Criteria andNewfreqIsNotNull() {
            addCriterion("NewFreq is not null");
            return (Criteria) this;
        }

        public Criteria andNewfreqEqualTo(Integer value) {
            addCriterion("NewFreq =", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqNotEqualTo(Integer value) {
            addCriterion("NewFreq <>", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqGreaterThan(Integer value) {
            addCriterion("NewFreq >", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqGreaterThanOrEqualTo(Integer value) {
            addCriterion("NewFreq >=", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqLessThan(Integer value) {
            addCriterion("NewFreq <", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqLessThanOrEqualTo(Integer value) {
            addCriterion("NewFreq <=", value, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqIn(List<Integer> values) {
            addCriterion("NewFreq in", values, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqNotIn(List<Integer> values) {
            addCriterion("NewFreq not in", values, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqBetween(Integer value1, Integer value2) {
            addCriterion("NewFreq between", value1, value2, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewfreqNotBetween(Integer value1, Integer value2) {
            addCriterion("NewFreq not between", value1, value2, "newfreq");
            return (Criteria) this;
        }

        public Criteria andNewmeanIsNull() {
            addCriterion("NewMean is null");
            return (Criteria) this;
        }

        public Criteria andNewmeanIsNotNull() {
            addCriterion("NewMean is not null");
            return (Criteria) this;
        }

        public Criteria andNewmeanEqualTo(Integer value) {
            addCriterion("NewMean =", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanNotEqualTo(Integer value) {
            addCriterion("NewMean <>", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanGreaterThan(Integer value) {
            addCriterion("NewMean >", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanGreaterThanOrEqualTo(Integer value) {
            addCriterion("NewMean >=", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanLessThan(Integer value) {
            addCriterion("NewMean <", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanLessThanOrEqualTo(Integer value) {
            addCriterion("NewMean <=", value, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanIn(List<Integer> values) {
            addCriterion("NewMean in", values, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanNotIn(List<Integer> values) {
            addCriterion("NewMean not in", values, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanBetween(Integer value1, Integer value2) {
            addCriterion("NewMean between", value1, value2, "newmean");
            return (Criteria) this;
        }

        public Criteria andNewmeanNotBetween(Integer value1, Integer value2) {
            addCriterion("NewMean not between", value1, value2, "newmean");
            return (Criteria) this;
        }

        public Criteria andSetfreqIsNull() {
            addCriterion("SetFreq is null");
            return (Criteria) this;
        }

        public Criteria andSetfreqIsNotNull() {
            addCriterion("SetFreq is not null");
            return (Criteria) this;
        }

        public Criteria andSetfreqEqualTo(Integer value) {
            addCriterion("SetFreq =", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqNotEqualTo(Integer value) {
            addCriterion("SetFreq <>", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqGreaterThan(Integer value) {
            addCriterion("SetFreq >", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqGreaterThanOrEqualTo(Integer value) {
            addCriterion("SetFreq >=", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqLessThan(Integer value) {
            addCriterion("SetFreq <", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqLessThanOrEqualTo(Integer value) {
            addCriterion("SetFreq <=", value, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqIn(List<Integer> values) {
            addCriterion("SetFreq in", values, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqNotIn(List<Integer> values) {
            addCriterion("SetFreq not in", values, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqBetween(Integer value1, Integer value2) {
            addCriterion("SetFreq between", value1, value2, "setfreq");
            return (Criteria) this;
        }

        public Criteria andSetfreqNotBetween(Integer value1, Integer value2) {
            addCriterion("SetFreq not between", value1, value2, "setfreq");
            return (Criteria) this;
        }

        public Criteria andPoTypeIsNull() {
            addCriterion("po_type is null");
            return (Criteria) this;
        }

        public Criteria andPoTypeIsNotNull() {
            addCriterion("po_type is not null");
            return (Criteria) this;
        }

        public Criteria andPoTypeEqualTo(String value) {
            addCriterion("po_type =", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeNotEqualTo(String value) {
            addCriterion("po_type <>", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeGreaterThan(String value) {
            addCriterion("po_type >", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeGreaterThanOrEqualTo(String value) {
            addCriterion("po_type >=", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeLessThan(String value) {
            addCriterion("po_type <", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeLessThanOrEqualTo(String value) {
            addCriterion("po_type <=", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeLike(String value) {
            addCriterion("po_type like", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeNotLike(String value) {
            addCriterion("po_type not like", value, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeIn(List<String> values) {
            addCriterion("po_type in", values, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeNotIn(List<String> values) {
            addCriterion("po_type not in", values, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeBetween(String value1, String value2) {
            addCriterion("po_type between", value1, value2, "poType");
            return (Criteria) this;
        }

        public Criteria andPoTypeNotBetween(String value1, String value2) {
            addCriterion("po_type not between", value1, value2, "poType");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNull() {
            addCriterion("SupplierCode is null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIsNotNull() {
            addCriterion("SupplierCode is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeEqualTo(String value) {
            addCriterion("SupplierCode =", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotEqualTo(String value) {
            addCriterion("SupplierCode <>", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThan(String value) {
            addCriterion("SupplierCode >", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeGreaterThanOrEqualTo(String value) {
            addCriterion("SupplierCode >=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThan(String value) {
            addCriterion("SupplierCode <", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLessThanOrEqualTo(String value) {
            addCriterion("SupplierCode <=", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeLike(String value) {
            addCriterion("SupplierCode like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotLike(String value) {
            addCriterion("SupplierCode not like", value, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeIn(List<String> values) {
            addCriterion("SupplierCode in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotIn(List<String> values) {
            addCriterion("SupplierCode not in", values, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeBetween(String value1, String value2) {
            addCriterion("SupplierCode between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andSuppliercodeNotBetween(String value1, String value2) {
            addCriterion("SupplierCode not between", value1, value2, "suppliercode");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNull() {
            addCriterion("orderType is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("orderType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(String value) {
            addCriterion("orderType =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(String value) {
            addCriterion("orderType <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(String value) {
            addCriterion("orderType >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(String value) {
            addCriterion("orderType >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(String value) {
            addCriterion("orderType <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(String value) {
            addCriterion("orderType <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLike(String value) {
            addCriterion("orderType like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotLike(String value) {
            addCriterion("orderType not like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<String> values) {
            addCriterion("orderType in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<String> values) {
            addCriterion("orderType not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(String value1, String value2) {
            addCriterion("orderType between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(String value1, String value2) {
            addCriterion("orderType not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andProdseriIsNull() {
            addCriterion("ProdSeri is null");
            return (Criteria) this;
        }

        public Criteria andProdseriIsNotNull() {
            addCriterion("ProdSeri is not null");
            return (Criteria) this;
        }

        public Criteria andProdseriEqualTo(String value) {
            addCriterion("ProdSeri =", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriNotEqualTo(String value) {
            addCriterion("ProdSeri <>", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriGreaterThan(String value) {
            addCriterion("ProdSeri >", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriGreaterThanOrEqualTo(String value) {
            addCriterion("ProdSeri >=", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriLessThan(String value) {
            addCriterion("ProdSeri <", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriLessThanOrEqualTo(String value) {
            addCriterion("ProdSeri <=", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriLike(String value) {
            addCriterion("ProdSeri like", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriNotLike(String value) {
            addCriterion("ProdSeri not like", value, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriIn(List<String> values) {
            addCriterion("ProdSeri in", values, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriNotIn(List<String> values) {
            addCriterion("ProdSeri not in", values, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriBetween(String value1, String value2) {
            addCriterion("ProdSeri between", value1, value2, "prodseri");
            return (Criteria) this;
        }

        public Criteria andProdseriNotBetween(String value1, String value2) {
            addCriterion("ProdSeri not between", value1, value2, "prodseri");
            return (Criteria) this;
        }

        public Criteria andStaterangeIsNull() {
            addCriterion("StateRange is null");
            return (Criteria) this;
        }

        public Criteria andStaterangeIsNotNull() {
            addCriterion("StateRange is not null");
            return (Criteria) this;
        }

        public Criteria andStaterangeEqualTo(String value) {
            addCriterion("StateRange =", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeNotEqualTo(String value) {
            addCriterion("StateRange <>", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeGreaterThan(String value) {
            addCriterion("StateRange >", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeGreaterThanOrEqualTo(String value) {
            addCriterion("StateRange >=", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeLessThan(String value) {
            addCriterion("StateRange <", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeLessThanOrEqualTo(String value) {
            addCriterion("StateRange <=", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeLike(String value) {
            addCriterion("StateRange like", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeNotLike(String value) {
            addCriterion("StateRange not like", value, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeIn(List<String> values) {
            addCriterion("StateRange in", values, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeNotIn(List<String> values) {
            addCriterion("StateRange not in", values, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeBetween(String value1, String value2) {
            addCriterion("StateRange between", value1, value2, "staterange");
            return (Criteria) this;
        }

        public Criteria andStaterangeNotBetween(String value1, String value2) {
            addCriterion("StateRange not between", value1, value2, "staterange");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyIsNull() {
            addCriterion("minPackageQty is null");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyIsNotNull() {
            addCriterion("minPackageQty is not null");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyEqualTo(Integer value) {
            addCriterion("minPackageQty =", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyNotEqualTo(Integer value) {
            addCriterion("minPackageQty <>", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyGreaterThan(Integer value) {
            addCriterion("minPackageQty >", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("minPackageQty >=", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyLessThan(Integer value) {
            addCriterion("minPackageQty <", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyLessThanOrEqualTo(Integer value) {
            addCriterion("minPackageQty <=", value, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyIn(List<Integer> values) {
            addCriterion("minPackageQty in", values, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyNotIn(List<Integer> values) {
            addCriterion("minPackageQty not in", values, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyBetween(Integer value1, Integer value2) {
            addCriterion("minPackageQty between", value1, value2, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andMinpackageqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("minPackageQty not between", value1, value2, "minpackageqty");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseIsNull() {
            addCriterion("DirectPurchase is null");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseIsNotNull() {
            addCriterion("DirectPurchase is not null");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseEqualTo(Short value) {
            addCriterion("DirectPurchase =", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseNotEqualTo(Short value) {
            addCriterion("DirectPurchase <>", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseGreaterThan(Short value) {
            addCriterion("DirectPurchase >", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseGreaterThanOrEqualTo(Short value) {
            addCriterion("DirectPurchase >=", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseLessThan(Short value) {
            addCriterion("DirectPurchase <", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseLessThanOrEqualTo(Short value) {
            addCriterion("DirectPurchase <=", value, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseIn(List<Short> values) {
            addCriterion("DirectPurchase in", values, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseNotIn(List<Short> values) {
            addCriterion("DirectPurchase not in", values, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseBetween(Short value1, Short value2) {
            addCriterion("DirectPurchase between", value1, value2, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andDirectpurchaseNotBetween(Short value1, Short value2) {
            addCriterion("DirectPurchase not between", value1, value2, "directpurchase");
            return (Criteria) this;
        }

        public Criteria andAutoreplIsNull() {
            addCriterion("AutoRepl is null");
            return (Criteria) this;
        }

        public Criteria andAutoreplIsNotNull() {
            addCriterion("AutoRepl is not null");
            return (Criteria) this;
        }

        public Criteria andAutoreplEqualTo(Short value) {
            addCriterion("AutoRepl =", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplNotEqualTo(Short value) {
            addCriterion("AutoRepl <>", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplGreaterThan(Short value) {
            addCriterion("AutoRepl >", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplGreaterThanOrEqualTo(Short value) {
            addCriterion("AutoRepl >=", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplLessThan(Short value) {
            addCriterion("AutoRepl <", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplLessThanOrEqualTo(Short value) {
            addCriterion("AutoRepl <=", value, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplIn(List<Short> values) {
            addCriterion("AutoRepl in", values, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplNotIn(List<Short> values) {
            addCriterion("AutoRepl not in", values, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplBetween(Short value1, Short value2) {
            addCriterion("AutoRepl between", value1, value2, "autorepl");
            return (Criteria) this;
        }

        public Criteria andAutoreplNotBetween(Short value1, Short value2) {
            addCriterion("AutoRepl not between", value1, value2, "autorepl");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryIsNull() {
            addCriterion("DirectDelivery is null");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryIsNotNull() {
            addCriterion("DirectDelivery is not null");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryEqualTo(Short value) {
            addCriterion("DirectDelivery =", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryNotEqualTo(Short value) {
            addCriterion("DirectDelivery <>", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryGreaterThan(Short value) {
            addCriterion("DirectDelivery >", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryGreaterThanOrEqualTo(Short value) {
            addCriterion("DirectDelivery >=", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryLessThan(Short value) {
            addCriterion("DirectDelivery <", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryLessThanOrEqualTo(Short value) {
            addCriterion("DirectDelivery <=", value, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryIn(List<Short> values) {
            addCriterion("DirectDelivery in", values, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryNotIn(List<Short> values) {
            addCriterion("DirectDelivery not in", values, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryBetween(Short value1, Short value2) {
            addCriterion("DirectDelivery between", value1, value2, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andDirectdeliveryNotBetween(Short value1, Short value2) {
            addCriterion("DirectDelivery not between", value1, value2, "directdelivery");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNull() {
            addCriterion("ECode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("ECode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("ECode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("ECode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("ECode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ECode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("ECode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("ECode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("ECode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("ECode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("ECode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("ECode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("ECode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("ECode not between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andModelseriesIsNull() {
            addCriterion("ModelSeries is null");
            return (Criteria) this;
        }

        public Criteria andModelseriesIsNotNull() {
            addCriterion("ModelSeries is not null");
            return (Criteria) this;
        }

        public Criteria andModelseriesEqualTo(String value) {
            addCriterion("ModelSeries =", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesNotEqualTo(String value) {
            addCriterion("ModelSeries <>", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesGreaterThan(String value) {
            addCriterion("ModelSeries >", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesGreaterThanOrEqualTo(String value) {
            addCriterion("ModelSeries >=", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesLessThan(String value) {
            addCriterion("ModelSeries <", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesLessThanOrEqualTo(String value) {
            addCriterion("ModelSeries <=", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesLike(String value) {
            addCriterion("ModelSeries like", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesNotLike(String value) {
            addCriterion("ModelSeries not like", value, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesIn(List<String> values) {
            addCriterion("ModelSeries in", values, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesNotIn(List<String> values) {
            addCriterion("ModelSeries not in", values, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesBetween(String value1, String value2) {
            addCriterion("ModelSeries between", value1, value2, "modelseries");
            return (Criteria) this;
        }

        public Criteria andModelseriesNotBetween(String value1, String value2) {
            addCriterion("ModelSeries not between", value1, value2, "modelseries");
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

        public Criteria andProjectNoIsNull() {
            addCriterion("project_no is null");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNotNull() {
            addCriterion("project_no is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNoEqualTo(String value) {
            addCriterion("project_no =", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotEqualTo(String value) {
            addCriterion("project_no <>", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThan(String value) {
            addCriterion("project_no >", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThanOrEqualTo(String value) {
            addCriterion("project_no >=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThan(String value) {
            addCriterion("project_no <", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThanOrEqualTo(String value) {
            addCriterion("project_no <=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLike(String value) {
            addCriterion("project_no like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotLike(String value) {
            addCriterion("project_no not like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIn(List<String> values) {
            addCriterion("project_no in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotIn(List<String> values) {
            addCriterion("project_no not in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoBetween(String value1, String value2) {
            addCriterion("project_no between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotBetween(String value1, String value2) {
            addCriterion("project_no not between", value1, value2, "projectNo");
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

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andLogindateIsNull() {
            addCriterion("loginDate is null");
            return (Criteria) this;
        }

        public Criteria andLogindateIsNotNull() {
            addCriterion("loginDate is not null");
            return (Criteria) this;
        }

        public Criteria andLogindateEqualTo(Date value) {
            addCriterion("loginDate =", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotEqualTo(Date value) {
            addCriterion("loginDate <>", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateGreaterThan(Date value) {
            addCriterion("loginDate >", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateGreaterThanOrEqualTo(Date value) {
            addCriterion("loginDate >=", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateLessThan(Date value) {
            addCriterion("loginDate <", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateLessThanOrEqualTo(Date value) {
            addCriterion("loginDate <=", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateIn(List<Date> values) {
            addCriterion("loginDate in", values, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotIn(List<Date> values) {
            addCriterion("loginDate not in", values, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateBetween(Date value1, Date value2) {
            addCriterion("loginDate between", value1, value2, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotBetween(Date value1, Date value2) {
            addCriterion("loginDate not between", value1, value2, "logindate");
            return (Criteria) this;
        }

        public Criteria andReplqtyIsNull() {
            addCriterion("ReplQty is null");
            return (Criteria) this;
        }

        public Criteria andReplqtyIsNotNull() {
            addCriterion("ReplQty is not null");
            return (Criteria) this;
        }

        public Criteria andReplqtyEqualTo(Integer value) {
            addCriterion("ReplQty =", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyNotEqualTo(Integer value) {
            addCriterion("ReplQty <>", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyGreaterThan(Integer value) {
            addCriterion("ReplQty >", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ReplQty >=", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyLessThan(Integer value) {
            addCriterion("ReplQty <", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyLessThanOrEqualTo(Integer value) {
            addCriterion("ReplQty <=", value, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyIn(List<Integer> values) {
            addCriterion("ReplQty in", values, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyNotIn(List<Integer> values) {
            addCriterion("ReplQty not in", values, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyBetween(Integer value1, Integer value2) {
            addCriterion("ReplQty between", value1, value2, "replqty");
            return (Criteria) this;
        }

        public Criteria andReplqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ReplQty not between", value1, value2, "replqty");
            return (Criteria) this;
        }

        public Criteria andErrormodelIsNull() {
            addCriterion("ErrorModel is null");
            return (Criteria) this;
        }

        public Criteria andErrormodelIsNotNull() {
            addCriterion("ErrorModel is not null");
            return (Criteria) this;
        }

        public Criteria andErrormodelEqualTo(Integer value) {
            addCriterion("ErrorModel =", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelNotEqualTo(Integer value) {
            addCriterion("ErrorModel <>", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelGreaterThan(Integer value) {
            addCriterion("ErrorModel >", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelGreaterThanOrEqualTo(Integer value) {
            addCriterion("ErrorModel >=", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelLessThan(Integer value) {
            addCriterion("ErrorModel <", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelLessThanOrEqualTo(Integer value) {
            addCriterion("ErrorModel <=", value, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelIn(List<Integer> values) {
            addCriterion("ErrorModel in", values, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelNotIn(List<Integer> values) {
            addCriterion("ErrorModel not in", values, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelBetween(Integer value1, Integer value2) {
            addCriterion("ErrorModel between", value1, value2, "errormodel");
            return (Criteria) this;
        }

        public Criteria andErrormodelNotBetween(Integer value1, Integer value2) {
            addCriterion("ErrorModel not between", value1, value2, "errormodel");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeIsNull() {
            addCriterion("SetSupplierCode is null");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeIsNotNull() {
            addCriterion("SetSupplierCode is not null");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeEqualTo(String value) {
            addCriterion("SetSupplierCode =", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeNotEqualTo(String value) {
            addCriterion("SetSupplierCode <>", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeGreaterThan(String value) {
            addCriterion("SetSupplierCode >", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeGreaterThanOrEqualTo(String value) {
            addCriterion("SetSupplierCode >=", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeLessThan(String value) {
            addCriterion("SetSupplierCode <", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeLessThanOrEqualTo(String value) {
            addCriterion("SetSupplierCode <=", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeLike(String value) {
            addCriterion("SetSupplierCode like", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeNotLike(String value) {
            addCriterion("SetSupplierCode not like", value, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeIn(List<String> values) {
            addCriterion("SetSupplierCode in", values, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeNotIn(List<String> values) {
            addCriterion("SetSupplierCode not in", values, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeBetween(String value1, String value2) {
            addCriterion("SetSupplierCode between", value1, value2, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andSetsuppliercodeNotBetween(String value1, String value2) {
            addCriterion("SetSupplierCode not between", value1, value2, "setsuppliercode");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNull() {
            addCriterion("ApplyQty is null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNotNull() {
            addCriterion("ApplyQty is not null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyEqualTo(Integer value) {
            addCriterion("ApplyQty =", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotEqualTo(Integer value) {
            addCriterion("ApplyQty <>", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThan(Integer value) {
            addCriterion("ApplyQty >", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty >=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThan(Integer value) {
            addCriterion("ApplyQty <", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty <=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIn(List<Integer> values) {
            addCriterion("ApplyQty in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotIn(List<Integer> values) {
            addCriterion("ApplyQty not in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty between", value1, value2, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty not between", value1, value2, "applyqty");
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
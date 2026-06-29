package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigInventoryAgentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MigInventoryAgentExample() {
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

        public Criteria andQtyopenningIsNull() {
            addCriterion("QtyOpenning is null");
            return (Criteria) this;
        }

        public Criteria andQtyopenningIsNotNull() {
            addCriterion("QtyOpenning is not null");
            return (Criteria) this;
        }

        public Criteria andQtyopenningEqualTo(Integer value) {
            addCriterion("QtyOpenning =", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningNotEqualTo(Integer value) {
            addCriterion("QtyOpenning <>", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningGreaterThan(Integer value) {
            addCriterion("QtyOpenning >", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOpenning >=", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningLessThan(Integer value) {
            addCriterion("QtyOpenning <", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOpenning <=", value, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningIn(List<Integer> values) {
            addCriterion("QtyOpenning in", values, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningNotIn(List<Integer> values) {
            addCriterion("QtyOpenning not in", values, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningBetween(Integer value1, Integer value2) {
            addCriterion("QtyOpenning between", value1, value2, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyopenningNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOpenning not between", value1, value2, "qtyopenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNull() {
            addCriterion("QtyOnHand is null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIsNotNull() {
            addCriterion("QtyOnHand is not null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandEqualTo(Integer value) {
            addCriterion("QtyOnHand =", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotEqualTo(Integer value) {
            addCriterion("QtyOnHand <>", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThan(Integer value) {
            addCriterion("QtyOnHand >", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOnHand >=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThan(Integer value) {
            addCriterion("QtyOnHand <", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOnHand <=", value, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandIn(List<Integer> values) {
            addCriterion("QtyOnHand in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotIn(List<Integer> values) {
            addCriterion("QtyOnHand not in", values, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnHand between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyonhandNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnHand not between", value1, value2, "qtyonhand");
            return (Criteria) this;
        }

        public Criteria andQtyimportIsNull() {
            addCriterion("QtyImport is null");
            return (Criteria) this;
        }

        public Criteria andQtyimportIsNotNull() {
            addCriterion("QtyImport is not null");
            return (Criteria) this;
        }

        public Criteria andQtyimportEqualTo(Integer value) {
            addCriterion("QtyImport =", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotEqualTo(Integer value) {
            addCriterion("QtyImport <>", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportGreaterThan(Integer value) {
            addCriterion("QtyImport >", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyImport >=", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportLessThan(Integer value) {
            addCriterion("QtyImport <", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportLessThanOrEqualTo(Integer value) {
            addCriterion("QtyImport <=", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportIn(List<Integer> values) {
            addCriterion("QtyImport in", values, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotIn(List<Integer> values) {
            addCriterion("QtyImport not in", values, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportBetween(Integer value1, Integer value2) {
            addCriterion("QtyImport between", value1, value2, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyImport not between", value1, value2, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyexportIsNull() {
            addCriterion("QtyExport is null");
            return (Criteria) this;
        }

        public Criteria andQtyexportIsNotNull() {
            addCriterion("QtyExport is not null");
            return (Criteria) this;
        }

        public Criteria andQtyexportEqualTo(Integer value) {
            addCriterion("QtyExport =", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportNotEqualTo(Integer value) {
            addCriterion("QtyExport <>", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportGreaterThan(Integer value) {
            addCriterion("QtyExport >", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyExport >=", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportLessThan(Integer value) {
            addCriterion("QtyExport <", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportLessThanOrEqualTo(Integer value) {
            addCriterion("QtyExport <=", value, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportIn(List<Integer> values) {
            addCriterion("QtyExport in", values, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportNotIn(List<Integer> values) {
            addCriterion("QtyExport not in", values, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportBetween(Integer value1, Integer value2) {
            addCriterion("QtyExport between", value1, value2, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyexportNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyExport not between", value1, value2, "qtyexport");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIsNull() {
            addCriterion("QtyPrepare is null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIsNotNull() {
            addCriterion("QtyPrepare is not null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareEqualTo(Integer value) {
            addCriterion("QtyPrepare =", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotEqualTo(Integer value) {
            addCriterion("QtyPrepare <>", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThan(Integer value) {
            addCriterion("QtyPrepare >", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyPrepare >=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThan(Integer value) {
            addCriterion("QtyPrepare <", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThanOrEqualTo(Integer value) {
            addCriterion("QtyPrepare <=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIn(List<Integer> values) {
            addCriterion("QtyPrepare in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotIn(List<Integer> values) {
            addCriterion("QtyPrepare not in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareBetween(Integer value1, Integer value2) {
            addCriterion("QtyPrepare between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyPrepare not between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andIndateIsNull() {
            addCriterion("InDate is null");
            return (Criteria) this;
        }

        public Criteria andIndateIsNotNull() {
            addCriterion("InDate is not null");
            return (Criteria) this;
        }

        public Criteria andIndateEqualTo(Date value) {
            addCriterion("InDate =", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotEqualTo(Date value) {
            addCriterion("InDate <>", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThan(Date value) {
            addCriterion("InDate >", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate >=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThan(Date value) {
            addCriterion("InDate <", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThanOrEqualTo(Date value) {
            addCriterion("InDate <=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateIn(List<Date> values) {
            addCriterion("InDate in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotIn(List<Date> values) {
            addCriterion("InDate not in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateBetween(Date value1, Date value2) {
            addCriterion("InDate between", value1, value2, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotBetween(Date value1, Date value2) {
            addCriterion("InDate not between", value1, value2, "indate");
            return (Criteria) this;
        }

        public Criteria andOutdateIsNull() {
            addCriterion("OutDate is null");
            return (Criteria) this;
        }

        public Criteria andOutdateIsNotNull() {
            addCriterion("OutDate is not null");
            return (Criteria) this;
        }

        public Criteria andOutdateEqualTo(Date value) {
            addCriterion("OutDate =", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateNotEqualTo(Date value) {
            addCriterion("OutDate <>", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateGreaterThan(Date value) {
            addCriterion("OutDate >", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OutDate >=", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateLessThan(Date value) {
            addCriterion("OutDate <", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateLessThanOrEqualTo(Date value) {
            addCriterion("OutDate <=", value, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateIn(List<Date> values) {
            addCriterion("OutDate in", values, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateNotIn(List<Date> values) {
            addCriterion("OutDate not in", values, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateBetween(Date value1, Date value2) {
            addCriterion("OutDate between", value1, value2, "outdate");
            return (Criteria) this;
        }

        public Criteria andOutdateNotBetween(Date value1, Date value2) {
            addCriterion("OutDate not between", value1, value2, "outdate");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNull() {
            addCriterion("StockCode is null");
            return (Criteria) this;
        }

        public Criteria andStockcodeIsNotNull() {
            addCriterion("StockCode is not null");
            return (Criteria) this;
        }

        public Criteria andStockcodeEqualTo(String value) {
            addCriterion("StockCode =", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotEqualTo(String value) {
            addCriterion("StockCode <>", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThan(String value) {
            addCriterion("StockCode >", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeGreaterThanOrEqualTo(String value) {
            addCriterion("StockCode >=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThan(String value) {
            addCriterion("StockCode <", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLessThanOrEqualTo(String value) {
            addCriterion("StockCode <=", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeLike(String value) {
            addCriterion("StockCode like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotLike(String value) {
            addCriterion("StockCode not like", value, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeIn(List<String> values) {
            addCriterion("StockCode in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotIn(List<String> values) {
            addCriterion("StockCode not in", values, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeBetween(String value1, String value2) {
            addCriterion("StockCode between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andStockcodeNotBetween(String value1, String value2) {
            addCriterion("StockCode not between", value1, value2, "stockcode");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyIsNull() {
            addCriterion("OrdMinQty is null");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyIsNotNull() {
            addCriterion("OrdMinQty is not null");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyEqualTo(Integer value) {
            addCriterion("OrdMinQty =", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyNotEqualTo(Integer value) {
            addCriterion("OrdMinQty <>", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyGreaterThan(Integer value) {
            addCriterion("OrdMinQty >", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrdMinQty >=", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyLessThan(Integer value) {
            addCriterion("OrdMinQty <", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyLessThanOrEqualTo(Integer value) {
            addCriterion("OrdMinQty <=", value, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyIn(List<Integer> values) {
            addCriterion("OrdMinQty in", values, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyNotIn(List<Integer> values) {
            addCriterion("OrdMinQty not in", values, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyBetween(Integer value1, Integer value2) {
            addCriterion("OrdMinQty between", value1, value2, "ordminqty");
            return (Criteria) this;
        }

        public Criteria andOrdminqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("OrdMinQty not between", value1, value2, "ordminqty");
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

        public Criteria andLocationnoIsNull() {
            addCriterion("LocationNo is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoIsNotNull() {
            addCriterion("LocationNo is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoEqualTo(String value) {
            addCriterion("LocationNo =", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotEqualTo(String value) {
            addCriterion("LocationNo <>", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThan(String value) {
            addCriterion("LocationNo >", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoGreaterThanOrEqualTo(String value) {
            addCriterion("LocationNo >=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThan(String value) {
            addCriterion("LocationNo <", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLessThanOrEqualTo(String value) {
            addCriterion("LocationNo <=", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoLike(String value) {
            addCriterion("LocationNo like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotLike(String value) {
            addCriterion("LocationNo not like", value, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoIn(List<String> values) {
            addCriterion("LocationNo in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotIn(List<String> values) {
            addCriterion("LocationNo not in", values, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoBetween(String value1, String value2) {
            addCriterion("LocationNo between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andLocationnoNotBetween(String value1, String value2) {
            addCriterion("LocationNo not between", value1, value2, "locationno");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("Inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("Inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("Inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("Inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("Inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("Inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("Inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("Inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("Inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("Inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("Inserttime not between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andParentcodeIsNull() {
            addCriterion("ParentCode is null");
            return (Criteria) this;
        }

        public Criteria andParentcodeIsNotNull() {
            addCriterion("ParentCode is not null");
            return (Criteria) this;
        }

        public Criteria andParentcodeEqualTo(String value) {
            addCriterion("ParentCode =", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeNotEqualTo(String value) {
            addCriterion("ParentCode <>", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeGreaterThan(String value) {
            addCriterion("ParentCode >", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ParentCode >=", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeLessThan(String value) {
            addCriterion("ParentCode <", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeLessThanOrEqualTo(String value) {
            addCriterion("ParentCode <=", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeLike(String value) {
            addCriterion("ParentCode like", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeNotLike(String value) {
            addCriterion("ParentCode not like", value, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeIn(List<String> values) {
            addCriterion("ParentCode in", values, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeNotIn(List<String> values) {
            addCriterion("ParentCode not in", values, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeBetween(String value1, String value2) {
            addCriterion("ParentCode between", value1, value2, "parentcode");
            return (Criteria) this;
        }

        public Criteria andParentcodeNotBetween(String value1, String value2) {
            addCriterion("ParentCode not between", value1, value2, "parentcode");
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
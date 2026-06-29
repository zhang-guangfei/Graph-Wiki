package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigInventoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MigInventoryExample() {
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
            addCriterion("QtyPrePare is null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIsNotNull() {
            addCriterion("QtyPrePare is not null");
            return (Criteria) this;
        }

        public Criteria andQtyprepareEqualTo(Integer value) {
            addCriterion("QtyPrePare =", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotEqualTo(Integer value) {
            addCriterion("QtyPrePare <>", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThan(Integer value) {
            addCriterion("QtyPrePare >", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyPrePare >=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThan(Integer value) {
            addCriterion("QtyPrePare <", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareLessThanOrEqualTo(Integer value) {
            addCriterion("QtyPrePare <=", value, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareIn(List<Integer> values) {
            addCriterion("QtyPrePare in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotIn(List<Integer> values) {
            addCriterion("QtyPrePare not in", values, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareBetween(Integer value1, Integer value2) {
            addCriterion("QtyPrePare between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andQtyprepareNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyPrePare not between", value1, value2, "qtyprepare");
            return (Criteria) this;
        }

        public Criteria andItemtypeIsNull() {
            addCriterion("ItemType is null");
            return (Criteria) this;
        }

        public Criteria andItemtypeIsNotNull() {
            addCriterion("ItemType is not null");
            return (Criteria) this;
        }

        public Criteria andItemtypeEqualTo(String value) {
            addCriterion("ItemType =", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeNotEqualTo(String value) {
            addCriterion("ItemType <>", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeGreaterThan(String value) {
            addCriterion("ItemType >", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ItemType >=", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeLessThan(String value) {
            addCriterion("ItemType <", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeLessThanOrEqualTo(String value) {
            addCriterion("ItemType <=", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeLike(String value) {
            addCriterion("ItemType like", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeNotLike(String value) {
            addCriterion("ItemType not like", value, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeIn(List<String> values) {
            addCriterion("ItemType in", values, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeNotIn(List<String> values) {
            addCriterion("ItemType not in", values, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeBetween(String value1, String value2) {
            addCriterion("ItemType between", value1, value2, "itemtype");
            return (Criteria) this;
        }

        public Criteria andItemtypeNotBetween(String value1, String value2) {
            addCriterion("ItemType not between", value1, value2, "itemtype");
            return (Criteria) this;
        }

        public Criteria andOrdcellsIsNull() {
            addCriterion("OrdCells is null");
            return (Criteria) this;
        }

        public Criteria andOrdcellsIsNotNull() {
            addCriterion("OrdCells is not null");
            return (Criteria) this;
        }

        public Criteria andOrdcellsEqualTo(Integer value) {
            addCriterion("OrdCells =", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsNotEqualTo(Integer value) {
            addCriterion("OrdCells <>", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsGreaterThan(Integer value) {
            addCriterion("OrdCells >", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrdCells >=", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsLessThan(Integer value) {
            addCriterion("OrdCells <", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsLessThanOrEqualTo(Integer value) {
            addCriterion("OrdCells <=", value, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsIn(List<Integer> values) {
            addCriterion("OrdCells in", values, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsNotIn(List<Integer> values) {
            addCriterion("OrdCells not in", values, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsBetween(Integer value1, Integer value2) {
            addCriterion("OrdCells between", value1, value2, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdcellsNotBetween(Integer value1, Integer value2) {
            addCriterion("OrdCells not between", value1, value2, "ordcells");
            return (Criteria) this;
        }

        public Criteria andOrdquantityIsNull() {
            addCriterion("OrdQuantity is null");
            return (Criteria) this;
        }

        public Criteria andOrdquantityIsNotNull() {
            addCriterion("OrdQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andOrdquantityEqualTo(Integer value) {
            addCriterion("OrdQuantity =", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityNotEqualTo(Integer value) {
            addCriterion("OrdQuantity <>", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityGreaterThan(Integer value) {
            addCriterion("OrdQuantity >", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrdQuantity >=", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityLessThan(Integer value) {
            addCriterion("OrdQuantity <", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityLessThanOrEqualTo(Integer value) {
            addCriterion("OrdQuantity <=", value, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityIn(List<Integer> values) {
            addCriterion("OrdQuantity in", values, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityNotIn(List<Integer> values) {
            addCriterion("OrdQuantity not in", values, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityBetween(Integer value1, Integer value2) {
            addCriterion("OrdQuantity between", value1, value2, "ordquantity");
            return (Criteria) this;
        }

        public Criteria andOrdquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("OrdQuantity not between", value1, value2, "ordquantity");
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

        public Criteria andOptrecIsNull() {
            addCriterion("OptRec is null");
            return (Criteria) this;
        }

        public Criteria andOptrecIsNotNull() {
            addCriterion("OptRec is not null");
            return (Criteria) this;
        }

        public Criteria andOptrecEqualTo(String value) {
            addCriterion("OptRec =", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecNotEqualTo(String value) {
            addCriterion("OptRec <>", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecGreaterThan(String value) {
            addCriterion("OptRec >", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecGreaterThanOrEqualTo(String value) {
            addCriterion("OptRec >=", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecLessThan(String value) {
            addCriterion("OptRec <", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecLessThanOrEqualTo(String value) {
            addCriterion("OptRec <=", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecLike(String value) {
            addCriterion("OptRec like", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecNotLike(String value) {
            addCriterion("OptRec not like", value, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecIn(List<String> values) {
            addCriterion("OptRec in", values, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecNotIn(List<String> values) {
            addCriterion("OptRec not in", values, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecBetween(String value1, String value2) {
            addCriterion("OptRec between", value1, value2, "optrec");
            return (Criteria) this;
        }

        public Criteria andOptrecNotBetween(String value1, String value2) {
            addCriterion("OptRec not between", value1, value2, "optrec");
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

        public Criteria andLocaplaceIsNull() {
            addCriterion("LocaPlace is null");
            return (Criteria) this;
        }

        public Criteria andLocaplaceIsNotNull() {
            addCriterion("LocaPlace is not null");
            return (Criteria) this;
        }

        public Criteria andLocaplaceEqualTo(String value) {
            addCriterion("LocaPlace =", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceNotEqualTo(String value) {
            addCriterion("LocaPlace <>", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceGreaterThan(String value) {
            addCriterion("LocaPlace >", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceGreaterThanOrEqualTo(String value) {
            addCriterion("LocaPlace >=", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceLessThan(String value) {
            addCriterion("LocaPlace <", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceLessThanOrEqualTo(String value) {
            addCriterion("LocaPlace <=", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceLike(String value) {
            addCriterion("LocaPlace like", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceNotLike(String value) {
            addCriterion("LocaPlace not like", value, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceIn(List<String> values) {
            addCriterion("LocaPlace in", values, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceNotIn(List<String> values) {
            addCriterion("LocaPlace not in", values, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceBetween(String value1, String value2) {
            addCriterion("LocaPlace between", value1, value2, "locaplace");
            return (Criteria) this;
        }

        public Criteria andLocaplaceNotBetween(String value1, String value2) {
            addCriterion("LocaPlace not between", value1, value2, "locaplace");
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

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andProductdescIsNull() {
            addCriterion("ProductDesc is null");
            return (Criteria) this;
        }

        public Criteria andProductdescIsNotNull() {
            addCriterion("ProductDesc is not null");
            return (Criteria) this;
        }

        public Criteria andProductdescEqualTo(String value) {
            addCriterion("ProductDesc =", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescNotEqualTo(String value) {
            addCriterion("ProductDesc <>", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescGreaterThan(String value) {
            addCriterion("ProductDesc >", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescGreaterThanOrEqualTo(String value) {
            addCriterion("ProductDesc >=", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescLessThan(String value) {
            addCriterion("ProductDesc <", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescLessThanOrEqualTo(String value) {
            addCriterion("ProductDesc <=", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescLike(String value) {
            addCriterion("ProductDesc like", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescNotLike(String value) {
            addCriterion("ProductDesc not like", value, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescIn(List<String> values) {
            addCriterion("ProductDesc in", values, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescNotIn(List<String> values) {
            addCriterion("ProductDesc not in", values, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescBetween(String value1, String value2) {
            addCriterion("ProductDesc between", value1, value2, "productdesc");
            return (Criteria) this;
        }

        public Criteria andProductdescNotBetween(String value1, String value2) {
            addCriterion("ProductDesc not between", value1, value2, "productdesc");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddIsNull() {
            addCriterion("LocationNo_Add is null");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddIsNotNull() {
            addCriterion("LocationNo_Add is not null");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddEqualTo(String value) {
            addCriterion("LocationNo_Add =", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddNotEqualTo(String value) {
            addCriterion("LocationNo_Add <>", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddGreaterThan(String value) {
            addCriterion("LocationNo_Add >", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddGreaterThanOrEqualTo(String value) {
            addCriterion("LocationNo_Add >=", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddLessThan(String value) {
            addCriterion("LocationNo_Add <", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddLessThanOrEqualTo(String value) {
            addCriterion("LocationNo_Add <=", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddLike(String value) {
            addCriterion("LocationNo_Add like", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddNotLike(String value) {
            addCriterion("LocationNo_Add not like", value, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddIn(List<String> values) {
            addCriterion("LocationNo_Add in", values, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddNotIn(List<String> values) {
            addCriterion("LocationNo_Add not in", values, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddBetween(String value1, String value2) {
            addCriterion("LocationNo_Add between", value1, value2, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andLocationnoAddNotBetween(String value1, String value2) {
            addCriterion("LocationNo_Add not between", value1, value2, "locationnoAdd");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningIsNull() {
            addCriterion("QtyOnhand_Openning is null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningIsNotNull() {
            addCriterion("QtyOnhand_Openning is not null");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningEqualTo(Integer value) {
            addCriterion("QtyOnhand_Openning =", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningNotEqualTo(Integer value) {
            addCriterion("QtyOnhand_Openning <>", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningGreaterThan(Integer value) {
            addCriterion("QtyOnhand_Openning >", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOnhand_Openning >=", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningLessThan(Integer value) {
            addCriterion("QtyOnhand_Openning <", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOnhand_Openning <=", value, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningIn(List<Integer> values) {
            addCriterion("QtyOnhand_Openning in", values, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningNotIn(List<Integer> values) {
            addCriterion("QtyOnhand_Openning not in", values, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnhand_Openning between", value1, value2, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyonhandOpenningNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOnhand_Openning not between", value1, value2, "qtyonhandOpenning");
            return (Criteria) this;
        }

        public Criteria andQtyforecastIsNull() {
            addCriterion("QtyForecast is null");
            return (Criteria) this;
        }

        public Criteria andQtyforecastIsNotNull() {
            addCriterion("QtyForecast is not null");
            return (Criteria) this;
        }

        public Criteria andQtyforecastEqualTo(Integer value) {
            addCriterion("QtyForecast =", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastNotEqualTo(Integer value) {
            addCriterion("QtyForecast <>", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastGreaterThan(Integer value) {
            addCriterion("QtyForecast >", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyForecast >=", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastLessThan(Integer value) {
            addCriterion("QtyForecast <", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastLessThanOrEqualTo(Integer value) {
            addCriterion("QtyForecast <=", value, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastIn(List<Integer> values) {
            addCriterion("QtyForecast in", values, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastNotIn(List<Integer> values) {
            addCriterion("QtyForecast not in", values, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastBetween(Integer value1, Integer value2) {
            addCriterion("QtyForecast between", value1, value2, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtyforecastNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyForecast not between", value1, value2, "qtyforecast");
            return (Criteria) this;
        }

        public Criteria andQtypreinIsNull() {
            addCriterion("QtyPreIn is null");
            return (Criteria) this;
        }

        public Criteria andQtypreinIsNotNull() {
            addCriterion("QtyPreIn is not null");
            return (Criteria) this;
        }

        public Criteria andQtypreinEqualTo(Integer value) {
            addCriterion("QtyPreIn =", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinNotEqualTo(Integer value) {
            addCriterion("QtyPreIn <>", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinGreaterThan(Integer value) {
            addCriterion("QtyPreIn >", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyPreIn >=", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinLessThan(Integer value) {
            addCriterion("QtyPreIn <", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinLessThanOrEqualTo(Integer value) {
            addCriterion("QtyPreIn <=", value, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinIn(List<Integer> values) {
            addCriterion("QtyPreIn in", values, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinNotIn(List<Integer> values) {
            addCriterion("QtyPreIn not in", values, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinBetween(Integer value1, Integer value2) {
            addCriterion("QtyPreIn between", value1, value2, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreinNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyPreIn not between", value1, value2, "qtyprein");
            return (Criteria) this;
        }

        public Criteria andQtypreoutIsNull() {
            addCriterion("QtyPreOut is null");
            return (Criteria) this;
        }

        public Criteria andQtypreoutIsNotNull() {
            addCriterion("QtyPreOut is not null");
            return (Criteria) this;
        }

        public Criteria andQtypreoutEqualTo(Integer value) {
            addCriterion("QtyPreOut =", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutNotEqualTo(Integer value) {
            addCriterion("QtyPreOut <>", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutGreaterThan(Integer value) {
            addCriterion("QtyPreOut >", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyPreOut >=", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutLessThan(Integer value) {
            addCriterion("QtyPreOut <", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutLessThanOrEqualTo(Integer value) {
            addCriterion("QtyPreOut <=", value, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutIn(List<Integer> values) {
            addCriterion("QtyPreOut in", values, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutNotIn(List<Integer> values) {
            addCriterion("QtyPreOut not in", values, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutBetween(Integer value1, Integer value2) {
            addCriterion("QtyPreOut between", value1, value2, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andQtypreoutNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyPreOut not between", value1, value2, "qtypreout");
            return (Criteria) this;
        }

        public Criteria andPickupflagIsNull() {
            addCriterion("PickUpFlag is null");
            return (Criteria) this;
        }

        public Criteria andPickupflagIsNotNull() {
            addCriterion("PickUpFlag is not null");
            return (Criteria) this;
        }

        public Criteria andPickupflagEqualTo(String value) {
            addCriterion("PickUpFlag =", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagNotEqualTo(String value) {
            addCriterion("PickUpFlag <>", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagGreaterThan(String value) {
            addCriterion("PickUpFlag >", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagGreaterThanOrEqualTo(String value) {
            addCriterion("PickUpFlag >=", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagLessThan(String value) {
            addCriterion("PickUpFlag <", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagLessThanOrEqualTo(String value) {
            addCriterion("PickUpFlag <=", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagLike(String value) {
            addCriterion("PickUpFlag like", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagNotLike(String value) {
            addCriterion("PickUpFlag not like", value, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagIn(List<String> values) {
            addCriterion("PickUpFlag in", values, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagNotIn(List<String> values) {
            addCriterion("PickUpFlag not in", values, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagBetween(String value1, String value2) {
            addCriterion("PickUpFlag between", value1, value2, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andPickupflagNotBetween(String value1, String value2) {
            addCriterion("PickUpFlag not between", value1, value2, "pickupflag");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIsNull() {
            addCriterion("ProjectCode is null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIsNotNull() {
            addCriterion("ProjectCode is not null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeEqualTo(String value) {
            addCriterion("ProjectCode =", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotEqualTo(String value) {
            addCriterion("ProjectCode <>", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThan(String value) {
            addCriterion("ProjectCode >", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ProjectCode >=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThan(String value) {
            addCriterion("ProjectCode <", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThanOrEqualTo(String value) {
            addCriterion("ProjectCode <=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLike(String value) {
            addCriterion("ProjectCode like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotLike(String value) {
            addCriterion("ProjectCode not like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIn(List<String> values) {
            addCriterion("ProjectCode in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotIn(List<String> values) {
            addCriterion("ProjectCode not in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeBetween(String value1, String value2) {
            addCriterion("ProjectCode between", value1, value2, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotBetween(String value1, String value2) {
            addCriterion("ProjectCode not between", value1, value2, "projectcode");
            return (Criteria) this;
        }

        public Criteria andVipnoIsNull() {
            addCriterion("VIPNo is null");
            return (Criteria) this;
        }

        public Criteria andVipnoIsNotNull() {
            addCriterion("VIPNo is not null");
            return (Criteria) this;
        }

        public Criteria andVipnoEqualTo(String value) {
            addCriterion("VIPNo =", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoNotEqualTo(String value) {
            addCriterion("VIPNo <>", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoGreaterThan(String value) {
            addCriterion("VIPNo >", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoGreaterThanOrEqualTo(String value) {
            addCriterion("VIPNo >=", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoLessThan(String value) {
            addCriterion("VIPNo <", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoLessThanOrEqualTo(String value) {
            addCriterion("VIPNo <=", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoLike(String value) {
            addCriterion("VIPNo like", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoNotLike(String value) {
            addCriterion("VIPNo not like", value, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoIn(List<String> values) {
            addCriterion("VIPNo in", values, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoNotIn(List<String> values) {
            addCriterion("VIPNo not in", values, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoBetween(String value1, String value2) {
            addCriterion("VIPNo between", value1, value2, "vipno");
            return (Criteria) this;
        }

        public Criteria andVipnoNotBetween(String value1, String value2) {
            addCriterion("VIPNo not between", value1, value2, "vipno");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvIsNull() {
            addCriterion("QtyOfficeInv is null");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvIsNotNull() {
            addCriterion("QtyOfficeInv is not null");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvEqualTo(Integer value) {
            addCriterion("QtyOfficeInv =", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvNotEqualTo(Integer value) {
            addCriterion("QtyOfficeInv <>", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvGreaterThan(Integer value) {
            addCriterion("QtyOfficeInv >", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOfficeInv >=", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvLessThan(Integer value) {
            addCriterion("QtyOfficeInv <", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOfficeInv <=", value, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvIn(List<Integer> values) {
            addCriterion("QtyOfficeInv in", values, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvNotIn(List<Integer> values) {
            addCriterion("QtyOfficeInv not in", values, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvBetween(Integer value1, Integer value2) {
            addCriterion("QtyOfficeInv between", value1, value2, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andQtyofficeinvNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOfficeInv not between", value1, value2, "qtyofficeinv");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagIsNull() {
            addCriterion("UnSafe_Flag is null");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagIsNotNull() {
            addCriterion("UnSafe_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagEqualTo(String value) {
            addCriterion("UnSafe_Flag =", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagNotEqualTo(String value) {
            addCriterion("UnSafe_Flag <>", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagGreaterThan(String value) {
            addCriterion("UnSafe_Flag >", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagGreaterThanOrEqualTo(String value) {
            addCriterion("UnSafe_Flag >=", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagLessThan(String value) {
            addCriterion("UnSafe_Flag <", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagLessThanOrEqualTo(String value) {
            addCriterion("UnSafe_Flag <=", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagLike(String value) {
            addCriterion("UnSafe_Flag like", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagNotLike(String value) {
            addCriterion("UnSafe_Flag not like", value, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagIn(List<String> values) {
            addCriterion("UnSafe_Flag in", values, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagNotIn(List<String> values) {
            addCriterion("UnSafe_Flag not in", values, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagBetween(String value1, String value2) {
            addCriterion("UnSafe_Flag between", value1, value2, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andUnsafeFlagNotBetween(String value1, String value2) {
            addCriterion("UnSafe_Flag not between", value1, value2, "unsafeFlag");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthIsNull() {
            addCriterion("AvgQty_Month is null");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthIsNotNull() {
            addCriterion("AvgQty_Month is not null");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthEqualTo(Integer value) {
            addCriterion("AvgQty_Month =", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthNotEqualTo(Integer value) {
            addCriterion("AvgQty_Month <>", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthGreaterThan(Integer value) {
            addCriterion("AvgQty_Month >", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("AvgQty_Month >=", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthLessThan(Integer value) {
            addCriterion("AvgQty_Month <", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthLessThanOrEqualTo(Integer value) {
            addCriterion("AvgQty_Month <=", value, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthIn(List<Integer> values) {
            addCriterion("AvgQty_Month in", values, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthNotIn(List<Integer> values) {
            addCriterion("AvgQty_Month not in", values, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthBetween(Integer value1, Integer value2) {
            addCriterion("AvgQty_Month between", value1, value2, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andAvgqtyMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("AvgQty_Month not between", value1, value2, "avgqtyMonth");
            return (Criteria) this;
        }

        public Criteria andFreqExportIsNull() {
            addCriterion("Freq_Export is null");
            return (Criteria) this;
        }

        public Criteria andFreqExportIsNotNull() {
            addCriterion("Freq_Export is not null");
            return (Criteria) this;
        }

        public Criteria andFreqExportEqualTo(Integer value) {
            addCriterion("Freq_Export =", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportNotEqualTo(Integer value) {
            addCriterion("Freq_Export <>", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportGreaterThan(Integer value) {
            addCriterion("Freq_Export >", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportGreaterThanOrEqualTo(Integer value) {
            addCriterion("Freq_Export >=", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportLessThan(Integer value) {
            addCriterion("Freq_Export <", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportLessThanOrEqualTo(Integer value) {
            addCriterion("Freq_Export <=", value, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportIn(List<Integer> values) {
            addCriterion("Freq_Export in", values, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportNotIn(List<Integer> values) {
            addCriterion("Freq_Export not in", values, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportBetween(Integer value1, Integer value2) {
            addCriterion("Freq_Export between", value1, value2, "freqExport");
            return (Criteria) this;
        }

        public Criteria andFreqExportNotBetween(Integer value1, Integer value2) {
            addCriterion("Freq_Export not between", value1, value2, "freqExport");
            return (Criteria) this;
        }

        public Criteria andProductintypeIsNull() {
            addCriterion("ProductInType is null");
            return (Criteria) this;
        }

        public Criteria andProductintypeIsNotNull() {
            addCriterion("ProductInType is not null");
            return (Criteria) this;
        }

        public Criteria andProductintypeEqualTo(String value) {
            addCriterion("ProductInType =", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeNotEqualTo(String value) {
            addCriterion("ProductInType <>", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeGreaterThan(String value) {
            addCriterion("ProductInType >", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeGreaterThanOrEqualTo(String value) {
            addCriterion("ProductInType >=", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeLessThan(String value) {
            addCriterion("ProductInType <", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeLessThanOrEqualTo(String value) {
            addCriterion("ProductInType <=", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeLike(String value) {
            addCriterion("ProductInType like", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeNotLike(String value) {
            addCriterion("ProductInType not like", value, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeIn(List<String> values) {
            addCriterion("ProductInType in", values, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeNotIn(List<String> values) {
            addCriterion("ProductInType not in", values, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeBetween(String value1, String value2) {
            addCriterion("ProductInType between", value1, value2, "productintype");
            return (Criteria) this;
        }

        public Criteria andProductintypeNotBetween(String value1, String value2) {
            addCriterion("ProductInType not between", value1, value2, "productintype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNull() {
            addCriterion("ProductType is null");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNotNull() {
            addCriterion("ProductType is not null");
            return (Criteria) this;
        }

        public Criteria andProducttypeEqualTo(String value) {
            addCriterion("ProductType =", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotEqualTo(String value) {
            addCriterion("ProductType <>", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThan(String value) {
            addCriterion("ProductType >", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThanOrEqualTo(String value) {
            addCriterion("ProductType >=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThan(String value) {
            addCriterion("ProductType <", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThanOrEqualTo(String value) {
            addCriterion("ProductType <=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLike(String value) {
            addCriterion("ProductType like", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotLike(String value) {
            addCriterion("ProductType not like", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIn(List<String> values) {
            addCriterion("ProductType in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotIn(List<String> values) {
            addCriterion("ProductType not in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeBetween(String value1, String value2) {
            addCriterion("ProductType between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotBetween(String value1, String value2) {
            addCriterion("ProductType not between", value1, value2, "producttype");
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
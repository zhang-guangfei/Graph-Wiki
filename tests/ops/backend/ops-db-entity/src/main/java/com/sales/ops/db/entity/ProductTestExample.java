package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductTestExample() {
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

        public Criteria andMinquantityIsNull() {
            addCriterion("MinQuantity is null");
            return (Criteria) this;
        }

        public Criteria andMinquantityIsNotNull() {
            addCriterion("MinQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andMinquantityEqualTo(Integer value) {
            addCriterion("MinQuantity =", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotEqualTo(Integer value) {
            addCriterion("MinQuantity <>", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityGreaterThan(Integer value) {
            addCriterion("MinQuantity >", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("MinQuantity >=", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityLessThan(Integer value) {
            addCriterion("MinQuantity <", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityLessThanOrEqualTo(Integer value) {
            addCriterion("MinQuantity <=", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityIn(List<Integer> values) {
            addCriterion("MinQuantity in", values, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotIn(List<Integer> values) {
            addCriterion("MinQuantity not in", values, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityBetween(Integer value1, Integer value2) {
            addCriterion("MinQuantity between", value1, value2, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("MinQuantity not between", value1, value2, "minquantity");
            return (Criteria) this;
        }

        public Criteria andFobpriceIsNull() {
            addCriterion("FobPrice is null");
            return (Criteria) this;
        }

        public Criteria andFobpriceIsNotNull() {
            addCriterion("FobPrice is not null");
            return (Criteria) this;
        }

        public Criteria andFobpriceEqualTo(BigDecimal value) {
            addCriterion("FobPrice =", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceNotEqualTo(BigDecimal value) {
            addCriterion("FobPrice <>", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceGreaterThan(BigDecimal value) {
            addCriterion("FobPrice >", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FobPrice >=", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceLessThan(BigDecimal value) {
            addCriterion("FobPrice <", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FobPrice <=", value, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceIn(List<BigDecimal> values) {
            addCriterion("FobPrice in", values, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceNotIn(List<BigDecimal> values) {
            addCriterion("FobPrice not in", values, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FobPrice between", value1, value2, "fobprice");
            return (Criteria) this;
        }

        public Criteria andFobpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FobPrice not between", value1, value2, "fobprice");
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

        public Criteria andEpricejpIsNull() {
            addCriterion("EPriceJp is null");
            return (Criteria) this;
        }

        public Criteria andEpricejpIsNotNull() {
            addCriterion("EPriceJp is not null");
            return (Criteria) this;
        }

        public Criteria andEpricejpEqualTo(BigDecimal value) {
            addCriterion("EPriceJp =", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpNotEqualTo(BigDecimal value) {
            addCriterion("EPriceJp <>", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpGreaterThan(BigDecimal value) {
            addCriterion("EPriceJp >", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPriceJp >=", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpLessThan(BigDecimal value) {
            addCriterion("EPriceJp <", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPriceJp <=", value, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpIn(List<BigDecimal> values) {
            addCriterion("EPriceJp in", values, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpNotIn(List<BigDecimal> values) {
            addCriterion("EPriceJp not in", values, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPriceJp between", value1, value2, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricejpNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPriceJp not between", value1, value2, "epricejp");
            return (Criteria) this;
        }

        public Criteria andEpricepraIsNull() {
            addCriterion("EpricePra is null");
            return (Criteria) this;
        }

        public Criteria andEpricepraIsNotNull() {
            addCriterion("EpricePra is not null");
            return (Criteria) this;
        }

        public Criteria andEpricepraEqualTo(BigDecimal value) {
            addCriterion("EpricePra =", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraNotEqualTo(BigDecimal value) {
            addCriterion("EpricePra <>", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraGreaterThan(BigDecimal value) {
            addCriterion("EpricePra >", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EpricePra >=", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraLessThan(BigDecimal value) {
            addCriterion("EpricePra <", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EpricePra <=", value, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraIn(List<BigDecimal> values) {
            addCriterion("EpricePra in", values, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraNotIn(List<BigDecimal> values) {
            addCriterion("EpricePra not in", values, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EpricePra between", value1, value2, "epricepra");
            return (Criteria) this;
        }

        public Criteria andEpricepraNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EpricePra not between", value1, value2, "epricepra");
            return (Criteria) this;
        }

        public Criteria andSpricermbIsNull() {
            addCriterion("SPriceRMB is null");
            return (Criteria) this;
        }

        public Criteria andSpricermbIsNotNull() {
            addCriterion("SPriceRMB is not null");
            return (Criteria) this;
        }

        public Criteria andSpricermbEqualTo(BigDecimal value) {
            addCriterion("SPriceRMB =", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbNotEqualTo(BigDecimal value) {
            addCriterion("SPriceRMB <>", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbGreaterThan(BigDecimal value) {
            addCriterion("SPriceRMB >", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SPriceRMB >=", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbLessThan(BigDecimal value) {
            addCriterion("SPriceRMB <", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SPriceRMB <=", value, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbIn(List<BigDecimal> values) {
            addCriterion("SPriceRMB in", values, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbNotIn(List<BigDecimal> values) {
            addCriterion("SPriceRMB not in", values, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPriceRMB between", value1, value2, "spricermb");
            return (Criteria) this;
        }

        public Criteria andSpricermbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPriceRMB not between", value1, value2, "spricermb");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIsNull() {
            addCriterion("LowestPrice is null");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIsNotNull() {
            addCriterion("LowestPrice is not null");
            return (Criteria) this;
        }

        public Criteria andLowestpriceEqualTo(BigDecimal value) {
            addCriterion("LowestPrice =", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotEqualTo(BigDecimal value) {
            addCriterion("LowestPrice <>", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceGreaterThan(BigDecimal value) {
            addCriterion("LowestPrice >", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LowestPrice >=", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceLessThan(BigDecimal value) {
            addCriterion("LowestPrice <", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LowestPrice <=", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIn(List<BigDecimal> values) {
            addCriterion("LowestPrice in", values, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotIn(List<BigDecimal> values) {
            addCriterion("LowestPrice not in", values, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LowestPrice between", value1, value2, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LowestPrice not between", value1, value2, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceIsNull() {
            addCriterion("ImportFobPrice is null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceIsNotNull() {
            addCriterion("ImportFobPrice is not null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceEqualTo(BigDecimal value) {
            addCriterion("ImportFobPrice =", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceNotEqualTo(BigDecimal value) {
            addCriterion("ImportFobPrice <>", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceGreaterThan(BigDecimal value) {
            addCriterion("ImportFobPrice >", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ImportFobPrice >=", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceLessThan(BigDecimal value) {
            addCriterion("ImportFobPrice <", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ImportFobPrice <=", value, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceIn(List<BigDecimal> values) {
            addCriterion("ImportFobPrice in", values, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceNotIn(List<BigDecimal> values) {
            addCriterion("ImportFobPrice not in", values, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ImportFobPrice between", value1, value2, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ImportFobPrice not between", value1, value2, "importfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceIsNull() {
            addCriterion("ExportFobPrice is null");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceIsNotNull() {
            addCriterion("ExportFobPrice is not null");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceEqualTo(BigDecimal value) {
            addCriterion("ExportFobPrice =", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceNotEqualTo(BigDecimal value) {
            addCriterion("ExportFobPrice <>", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceGreaterThan(BigDecimal value) {
            addCriterion("ExportFobPrice >", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ExportFobPrice >=", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceLessThan(BigDecimal value) {
            addCriterion("ExportFobPrice <", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ExportFobPrice <=", value, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceIn(List<BigDecimal> values) {
            addCriterion("ExportFobPrice in", values, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceNotIn(List<BigDecimal> values) {
            addCriterion("ExportFobPrice not in", values, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ExportFobPrice between", value1, value2, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andExportfobpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ExportFobPrice not between", value1, value2, "exportfobprice");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNull() {
            addCriterion("ImportFobPriceOriginal is null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNotNull() {
            addCriterion("ImportFobPriceOriginal is not null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalEqualTo(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal =", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotEqualTo(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal <>", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThan(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal >", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal >=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThan(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal <", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ImportFobPriceOriginal <=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIn(List<BigDecimal> values) {
            addCriterion("ImportFobPriceOriginal in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotIn(List<BigDecimal> values) {
            addCriterion("ImportFobPriceOriginal not in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ImportFobPriceOriginal between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ImportFobPriceOriginal not between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNull() {
            addCriterion("ImportCurrencyId is null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNotNull() {
            addCriterion("ImportCurrencyId is not null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidEqualTo(String value) {
            addCriterion("ImportCurrencyId =", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotEqualTo(String value) {
            addCriterion("ImportCurrencyId <>", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThan(String value) {
            addCriterion("ImportCurrencyId >", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThanOrEqualTo(String value) {
            addCriterion("ImportCurrencyId >=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThan(String value) {
            addCriterion("ImportCurrencyId <", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThanOrEqualTo(String value) {
            addCriterion("ImportCurrencyId <=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLike(String value) {
            addCriterion("ImportCurrencyId like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotLike(String value) {
            addCriterion("ImportCurrencyId not like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIn(List<String> values) {
            addCriterion("ImportCurrencyId in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotIn(List<String> values) {
            addCriterion("ImportCurrencyId not in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidBetween(String value1, String value2) {
            addCriterion("ImportCurrencyId between", value1, value2, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotBetween(String value1, String value2) {
            addCriterion("ImportCurrencyId not between", value1, value2, "importcurrencyid");
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
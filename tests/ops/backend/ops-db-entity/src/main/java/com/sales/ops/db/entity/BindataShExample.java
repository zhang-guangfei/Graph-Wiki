package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BindataShExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BindataShExample() {
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

        public Criteria andClasscodeIsNull() {
            addCriterion("ClassCode is null");
            return (Criteria) this;
        }

        public Criteria andClasscodeIsNotNull() {
            addCriterion("ClassCode is not null");
            return (Criteria) this;
        }

        public Criteria andClasscodeEqualTo(String value) {
            addCriterion("ClassCode =", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotEqualTo(String value) {
            addCriterion("ClassCode <>", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThan(String value) {
            addCriterion("ClassCode >", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeGreaterThanOrEqualTo(String value) {
            addCriterion("ClassCode >=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThan(String value) {
            addCriterion("ClassCode <", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLessThanOrEqualTo(String value) {
            addCriterion("ClassCode <=", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeLike(String value) {
            addCriterion("ClassCode like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotLike(String value) {
            addCriterion("ClassCode not like", value, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeIn(List<String> values) {
            addCriterion("ClassCode in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotIn(List<String> values) {
            addCriterion("ClassCode not in", values, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeBetween(String value1, String value2) {
            addCriterion("ClassCode between", value1, value2, "classcode");
            return (Criteria) this;
        }

        public Criteria andClasscodeNotBetween(String value1, String value2) {
            addCriterion("ClassCode not between", value1, value2, "classcode");
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

        public Criteria andLogindateIsNull() {
            addCriterion("LogInDate is null");
            return (Criteria) this;
        }

        public Criteria andLogindateIsNotNull() {
            addCriterion("LogInDate is not null");
            return (Criteria) this;
        }

        public Criteria andLogindateEqualTo(Date value) {
            addCriterion("LogInDate =", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotEqualTo(Date value) {
            addCriterion("LogInDate <>", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateGreaterThan(Date value) {
            addCriterion("LogInDate >", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateGreaterThanOrEqualTo(Date value) {
            addCriterion("LogInDate >=", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateLessThan(Date value) {
            addCriterion("LogInDate <", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateLessThanOrEqualTo(Date value) {
            addCriterion("LogInDate <=", value, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateIn(List<Date> values) {
            addCriterion("LogInDate in", values, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotIn(List<Date> values) {
            addCriterion("LogInDate not in", values, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateBetween(Date value1, Date value2) {
            addCriterion("LogInDate between", value1, value2, "logindate");
            return (Criteria) this;
        }

        public Criteria andLogindateNotBetween(Date value1, Date value2) {
            addCriterion("LogInDate not between", value1, value2, "logindate");
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

        public Criteria andUpddateIsNull() {
            addCriterion("UpdDate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("UpdDate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("UpdDate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("UpdDate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("UpdDate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdDate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("UpdDate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("UpdDate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("UpdDate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("UpdDate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("UpdDate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("UpdDate not between", value1, value2, "upddate");
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

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andQtyordpointIsNull() {
            addCriterion("QtyOrdpoint is null");
            return (Criteria) this;
        }

        public Criteria andQtyordpointIsNotNull() {
            addCriterion("QtyOrdpoint is not null");
            return (Criteria) this;
        }

        public Criteria andQtyordpointEqualTo(Integer value) {
            addCriterion("QtyOrdpoint =", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointNotEqualTo(Integer value) {
            addCriterion("QtyOrdpoint <>", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointGreaterThan(Integer value) {
            addCriterion("QtyOrdpoint >", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdpoint >=", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointLessThan(Integer value) {
            addCriterion("QtyOrdpoint <", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdpoint <=", value, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointIn(List<Integer> values) {
            addCriterion("QtyOrdpoint in", values, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointNotIn(List<Integer> values) {
            addCriterion("QtyOrdpoint not in", values, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdpoint between", value1, value2, "qtyordpoint");
            return (Criteria) this;
        }

        public Criteria andQtyordpointNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdpoint not between", value1, value2, "qtyordpoint");
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
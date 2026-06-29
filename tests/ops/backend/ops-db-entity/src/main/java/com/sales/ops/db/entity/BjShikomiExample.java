package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BjShikomiExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BjShikomiExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNull() {
            addCriterion("ApplyNo is null");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNotNull() {
            addCriterion("ApplyNo is not null");
            return (Criteria) this;
        }

        public Criteria andApplynoEqualTo(String value) {
            addCriterion("ApplyNo =", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotEqualTo(String value) {
            addCriterion("ApplyNo <>", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThan(String value) {
            addCriterion("ApplyNo >", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThanOrEqualTo(String value) {
            addCriterion("ApplyNo >=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThan(String value) {
            addCriterion("ApplyNo <", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThanOrEqualTo(String value) {
            addCriterion("ApplyNo <=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLike(String value) {
            addCriterion("ApplyNo like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotLike(String value) {
            addCriterion("ApplyNo not like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoIn(List<String> values) {
            addCriterion("ApplyNo in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotIn(List<String> values) {
            addCriterion("ApplyNo not in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoBetween(String value1, String value2) {
            addCriterion("ApplyNo between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotBetween(String value1, String value2) {
            addCriterion("ApplyNo not between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNull() {
            addCriterion("Applydate is null");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNotNull() {
            addCriterion("Applydate is not null");
            return (Criteria) this;
        }

        public Criteria andApplydateEqualTo(Date value) {
            addCriterionForJDBCDate("Applydate =", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("Applydate <>", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThan(Date value) {
            addCriterionForJDBCDate("Applydate >", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Applydate >=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThan(Date value) {
            addCriterionForJDBCDate("Applydate <", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Applydate <=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateIn(List<Date> values) {
            addCriterionForJDBCDate("Applydate in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("Applydate not in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Applydate between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Applydate not between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andAnswernoIsNull() {
            addCriterion("AnswerNo is null");
            return (Criteria) this;
        }

        public Criteria andAnswernoIsNotNull() {
            addCriterion("AnswerNo is not null");
            return (Criteria) this;
        }

        public Criteria andAnswernoEqualTo(String value) {
            addCriterion("AnswerNo =", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotEqualTo(String value) {
            addCriterion("AnswerNo <>", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoGreaterThan(String value) {
            addCriterion("AnswerNo >", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoGreaterThanOrEqualTo(String value) {
            addCriterion("AnswerNo >=", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLessThan(String value) {
            addCriterion("AnswerNo <", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLessThanOrEqualTo(String value) {
            addCriterion("AnswerNo <=", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoLike(String value) {
            addCriterion("AnswerNo like", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotLike(String value) {
            addCriterion("AnswerNo not like", value, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoIn(List<String> values) {
            addCriterion("AnswerNo in", values, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotIn(List<String> values) {
            addCriterion("AnswerNo not in", values, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoBetween(String value1, String value2) {
            addCriterion("AnswerNo between", value1, value2, "answerno");
            return (Criteria) this;
        }

        public Criteria andAnswernoNotBetween(String value1, String value2) {
            addCriterion("AnswerNo not between", value1, value2, "answerno");
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

        public Criteria andQuantityIsNull() {
            addCriterion("Quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("Quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("Quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("Quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("Quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("Quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("Quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("Quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("Quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("Quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("Quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNull() {
            addCriterion("OrdDate is null");
            return (Criteria) this;
        }

        public Criteria andOrddateIsNotNull() {
            addCriterion("OrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andOrddateEqualTo(Date value) {
            addCriterion("OrdDate =", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotEqualTo(Date value) {
            addCriterion("OrdDate <>", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThan(Date value) {
            addCriterion("OrdDate >", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateGreaterThanOrEqualTo(Date value) {
            addCriterion("OrdDate >=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThan(Date value) {
            addCriterion("OrdDate <", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateLessThanOrEqualTo(Date value) {
            addCriterion("OrdDate <=", value, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateIn(List<Date> values) {
            addCriterion("OrdDate in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotIn(List<Date> values) {
            addCriterion("OrdDate not in", values, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateBetween(Date value1, Date value2) {
            addCriterion("OrdDate between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andOrddateNotBetween(Date value1, Date value2) {
            addCriterion("OrdDate not between", value1, value2, "orddate");
            return (Criteria) this;
        }

        public Criteria andAssedateIsNull() {
            addCriterion("AsseDate is null");
            return (Criteria) this;
        }

        public Criteria andAssedateIsNotNull() {
            addCriterion("AsseDate is not null");
            return (Criteria) this;
        }

        public Criteria andAssedateEqualTo(Integer value) {
            addCriterion("AsseDate =", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateNotEqualTo(Integer value) {
            addCriterion("AsseDate <>", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateGreaterThan(Integer value) {
            addCriterion("AsseDate >", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateGreaterThanOrEqualTo(Integer value) {
            addCriterion("AsseDate >=", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateLessThan(Integer value) {
            addCriterion("AsseDate <", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateLessThanOrEqualTo(Integer value) {
            addCriterion("AsseDate <=", value, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateIn(List<Integer> values) {
            addCriterion("AsseDate in", values, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateNotIn(List<Integer> values) {
            addCriterion("AsseDate not in", values, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateBetween(Integer value1, Integer value2) {
            addCriterion("AsseDate between", value1, value2, "assedate");
            return (Criteria) this;
        }

        public Criteria andAssedateNotBetween(Integer value1, Integer value2) {
            addCriterion("AsseDate not between", value1, value2, "assedate");
            return (Criteria) this;
        }

        public Criteria andResiduenoIsNull() {
            addCriterion("ResidueNo is null");
            return (Criteria) this;
        }

        public Criteria andResiduenoIsNotNull() {
            addCriterion("ResidueNo is not null");
            return (Criteria) this;
        }

        public Criteria andResiduenoEqualTo(Integer value) {
            addCriterion("ResidueNo =", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoNotEqualTo(Integer value) {
            addCriterion("ResidueNo <>", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoGreaterThan(Integer value) {
            addCriterion("ResidueNo >", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoGreaterThanOrEqualTo(Integer value) {
            addCriterion("ResidueNo >=", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoLessThan(Integer value) {
            addCriterion("ResidueNo <", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoLessThanOrEqualTo(Integer value) {
            addCriterion("ResidueNo <=", value, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoIn(List<Integer> values) {
            addCriterion("ResidueNo in", values, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoNotIn(List<Integer> values) {
            addCriterion("ResidueNo not in", values, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoBetween(Integer value1, Integer value2) {
            addCriterion("ResidueNo between", value1, value2, "residueno");
            return (Criteria) this;
        }

        public Criteria andResiduenoNotBetween(Integer value1, Integer value2) {
            addCriterion("ResidueNo not between", value1, value2, "residueno");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("Pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("Pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("Pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("Pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("Pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("Pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("Pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("Pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("Pid like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("Pid not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("Pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("Pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("Pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("Pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIsNull() {
            addCriterion("QtyOrdpre is null");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIsNotNull() {
            addCriterion("QtyOrdpre is not null");
            return (Criteria) this;
        }

        public Criteria andQtyordpreEqualTo(Integer value) {
            addCriterion("QtyOrdpre =", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotEqualTo(Integer value) {
            addCriterion("QtyOrdpre <>", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreGreaterThan(Integer value) {
            addCriterion("QtyOrdpre >", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdpre >=", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreLessThan(Integer value) {
            addCriterion("QtyOrdpre <", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreLessThanOrEqualTo(Integer value) {
            addCriterion("QtyOrdpre <=", value, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreIn(List<Integer> values) {
            addCriterion("QtyOrdpre in", values, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotIn(List<Integer> values) {
            addCriterion("QtyOrdpre not in", values, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdpre between", value1, value2, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andQtyordpreNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyOrdpre not between", value1, value2, "qtyordpre");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNull() {
            addCriterion("Optstate is null");
            return (Criteria) this;
        }

        public Criteria andOptstateIsNotNull() {
            addCriterion("Optstate is not null");
            return (Criteria) this;
        }

        public Criteria andOptstateEqualTo(String value) {
            addCriterion("Optstate =", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotEqualTo(String value) {
            addCriterion("Optstate <>", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThan(String value) {
            addCriterion("Optstate >", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateGreaterThanOrEqualTo(String value) {
            addCriterion("Optstate >=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThan(String value) {
            addCriterion("Optstate <", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLessThanOrEqualTo(String value) {
            addCriterion("Optstate <=", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateLike(String value) {
            addCriterion("Optstate like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotLike(String value) {
            addCriterion("Optstate not like", value, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateIn(List<String> values) {
            addCriterion("Optstate in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotIn(List<String> values) {
            addCriterion("Optstate not in", values, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateBetween(String value1, String value2) {
            addCriterion("Optstate between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andOptstateNotBetween(String value1, String value2) {
            addCriterion("Optstate not between", value1, value2, "optstate");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNull() {
            addCriterion("QtyWarning is null");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNotNull() {
            addCriterion("QtyWarning is not null");
            return (Criteria) this;
        }

        public Criteria andQtywarningEqualTo(Integer value) {
            addCriterion("QtyWarning =", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotEqualTo(Integer value) {
            addCriterion("QtyWarning <>", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThan(Integer value) {
            addCriterion("QtyWarning >", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning >=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThan(Integer value) {
            addCriterion("QtyWarning <", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning <=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningIn(List<Integer> values) {
            addCriterion("QtyWarning in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotIn(List<Integer> values) {
            addCriterion("QtyWarning not in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning not between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtynoordIsNull() {
            addCriterion("QtyNoord is null");
            return (Criteria) this;
        }

        public Criteria andQtynoordIsNotNull() {
            addCriterion("QtyNoord is not null");
            return (Criteria) this;
        }

        public Criteria andQtynoordEqualTo(Integer value) {
            addCriterion("QtyNoord =", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotEqualTo(Integer value) {
            addCriterion("QtyNoord <>", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordGreaterThan(Integer value) {
            addCriterion("QtyNoord >", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyNoord >=", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordLessThan(Integer value) {
            addCriterion("QtyNoord <", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordLessThanOrEqualTo(Integer value) {
            addCriterion("QtyNoord <=", value, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordIn(List<Integer> values) {
            addCriterion("QtyNoord in", values, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotIn(List<Integer> values) {
            addCriterion("QtyNoord not in", values, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordBetween(Integer value1, Integer value2) {
            addCriterion("QtyNoord between", value1, value2, "qtynoord");
            return (Criteria) this;
        }

        public Criteria andQtynoordNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyNoord not between", value1, value2, "qtynoord");
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

        public Criteria andPriceLotIsNull() {
            addCriterion("Price_lot is null");
            return (Criteria) this;
        }

        public Criteria andPriceLotIsNotNull() {
            addCriterion("Price_lot is not null");
            return (Criteria) this;
        }

        public Criteria andPriceLotEqualTo(BigDecimal value) {
            addCriterion("Price_lot =", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotEqualTo(BigDecimal value) {
            addCriterion("Price_lot <>", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotGreaterThan(BigDecimal value) {
            addCriterion("Price_lot >", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_lot >=", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotLessThan(BigDecimal value) {
            addCriterion("Price_lot <", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_lot <=", value, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotIn(List<BigDecimal> values) {
            addCriterion("Price_lot in", values, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotIn(List<BigDecimal> values) {
            addCriterion("Price_lot not in", values, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_lot between", value1, value2, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPriceLotNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_lot not between", value1, value2, "priceLot");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysIsNull() {
            addCriterion("PartPrepare_days is null");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysIsNotNull() {
            addCriterion("PartPrepare_days is not null");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysEqualTo(Integer value) {
            addCriterion("PartPrepare_days =", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysNotEqualTo(Integer value) {
            addCriterion("PartPrepare_days <>", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysGreaterThan(Integer value) {
            addCriterion("PartPrepare_days >", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("PartPrepare_days >=", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysLessThan(Integer value) {
            addCriterion("PartPrepare_days <", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysLessThanOrEqualTo(Integer value) {
            addCriterion("PartPrepare_days <=", value, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysIn(List<Integer> values) {
            addCriterion("PartPrepare_days in", values, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysNotIn(List<Integer> values) {
            addCriterion("PartPrepare_days not in", values, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysBetween(Integer value1, Integer value2) {
            addCriterion("PartPrepare_days between", value1, value2, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andPartprepareDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("PartPrepare_days not between", value1, value2, "partprepareDays");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkIsNull() {
            addCriterion("Description_Remark is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkIsNotNull() {
            addCriterion("Description_Remark is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkEqualTo(String value) {
            addCriterion("Description_Remark =", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkNotEqualTo(String value) {
            addCriterion("Description_Remark <>", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkGreaterThan(String value) {
            addCriterion("Description_Remark >", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Description_Remark >=", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkLessThan(String value) {
            addCriterion("Description_Remark <", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkLessThanOrEqualTo(String value) {
            addCriterion("Description_Remark <=", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkLike(String value) {
            addCriterion("Description_Remark like", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkNotLike(String value) {
            addCriterion("Description_Remark not like", value, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkIn(List<String> values) {
            addCriterion("Description_Remark in", values, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkNotIn(List<String> values) {
            addCriterion("Description_Remark not in", values, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkBetween(String value1, String value2) {
            addCriterion("Description_Remark between", value1, value2, "descriptionRemark");
            return (Criteria) this;
        }

        public Criteria andDescriptionRemarkNotBetween(String value1, String value2) {
            addCriterion("Description_Remark not between", value1, value2, "descriptionRemark");
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

        public Criteria andDeptnoIsNull() {
            addCriterion("DeptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("DeptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("DeptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("DeptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("DeptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("DeptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("DeptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("DeptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("DeptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("DeptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("DeptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("DeptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("DeptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("DeptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andIndcodeIsNull() {
            addCriterion("IndCode is null");
            return (Criteria) this;
        }

        public Criteria andIndcodeIsNotNull() {
            addCriterion("IndCode is not null");
            return (Criteria) this;
        }

        public Criteria andIndcodeEqualTo(String value) {
            addCriterion("IndCode =", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotEqualTo(String value) {
            addCriterion("IndCode <>", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeGreaterThan(String value) {
            addCriterion("IndCode >", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeGreaterThanOrEqualTo(String value) {
            addCriterion("IndCode >=", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLessThan(String value) {
            addCriterion("IndCode <", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLessThanOrEqualTo(String value) {
            addCriterion("IndCode <=", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeLike(String value) {
            addCriterion("IndCode like", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotLike(String value) {
            addCriterion("IndCode not like", value, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeIn(List<String> values) {
            addCriterion("IndCode in", values, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotIn(List<String> values) {
            addCriterion("IndCode not in", values, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeBetween(String value1, String value2) {
            addCriterion("IndCode between", value1, value2, "indcode");
            return (Criteria) this;
        }

        public Criteria andIndcodeNotBetween(String value1, String value2) {
            addCriterion("IndCode not between", value1, value2, "indcode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassIsNull() {
            addCriterion("Shikomi_Class is null");
            return (Criteria) this;
        }

        public Criteria andShikomiClassIsNotNull() {
            addCriterion("Shikomi_Class is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiClassEqualTo(String value) {
            addCriterion("Shikomi_Class =", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassNotEqualTo(String value) {
            addCriterion("Shikomi_Class <>", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassGreaterThan(String value) {
            addCriterion("Shikomi_Class >", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassGreaterThanOrEqualTo(String value) {
            addCriterion("Shikomi_Class >=", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassLessThan(String value) {
            addCriterion("Shikomi_Class <", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassLessThanOrEqualTo(String value) {
            addCriterion("Shikomi_Class <=", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassLike(String value) {
            addCriterion("Shikomi_Class like", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassNotLike(String value) {
            addCriterion("Shikomi_Class not like", value, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassIn(List<String> values) {
            addCriterion("Shikomi_Class in", values, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassNotIn(List<String> values) {
            addCriterion("Shikomi_Class not in", values, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassBetween(String value1, String value2) {
            addCriterion("Shikomi_Class between", value1, value2, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassNotBetween(String value1, String value2) {
            addCriterion("Shikomi_Class not between", value1, value2, "shikomiClass");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeIsNull() {
            addCriterion("Shikomi_Class_Code is null");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeIsNotNull() {
            addCriterion("Shikomi_Class_Code is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeEqualTo(String value) {
            addCriterion("Shikomi_Class_Code =", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeNotEqualTo(String value) {
            addCriterion("Shikomi_Class_Code <>", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeGreaterThan(String value) {
            addCriterion("Shikomi_Class_Code >", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Shikomi_Class_Code >=", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeLessThan(String value) {
            addCriterion("Shikomi_Class_Code <", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeLessThanOrEqualTo(String value) {
            addCriterion("Shikomi_Class_Code <=", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeLike(String value) {
            addCriterion("Shikomi_Class_Code like", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeNotLike(String value) {
            addCriterion("Shikomi_Class_Code not like", value, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeIn(List<String> values) {
            addCriterion("Shikomi_Class_Code in", values, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeNotIn(List<String> values) {
            addCriterion("Shikomi_Class_Code not in", values, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeBetween(String value1, String value2) {
            addCriterion("Shikomi_Class_Code between", value1, value2, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andShikomiClassCodeNotBetween(String value1, String value2) {
            addCriterion("Shikomi_Class_Code not between", value1, value2, "shikomiClassCode");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("UserName is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("UserName is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("UserName =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("UserName <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("UserName >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("UserName >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("UserName <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("UserName <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("UserName like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("UserName not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("UserName in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("UserName not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("UserName between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("UserName not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("Opttime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("Opttime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("Opttime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("Opttime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("Opttime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Opttime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("Opttime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("Opttime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("Opttime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("Opttime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("Opttime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("Opttime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andIsremindIsNull() {
            addCriterion("IsRemind is null");
            return (Criteria) this;
        }

        public Criteria andIsremindIsNotNull() {
            addCriterion("IsRemind is not null");
            return (Criteria) this;
        }

        public Criteria andIsremindEqualTo(Integer value) {
            addCriterion("IsRemind =", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindNotEqualTo(Integer value) {
            addCriterion("IsRemind <>", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindGreaterThan(Integer value) {
            addCriterion("IsRemind >", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindGreaterThanOrEqualTo(Integer value) {
            addCriterion("IsRemind >=", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindLessThan(Integer value) {
            addCriterion("IsRemind <", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindLessThanOrEqualTo(Integer value) {
            addCriterion("IsRemind <=", value, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindIn(List<Integer> values) {
            addCriterion("IsRemind in", values, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindNotIn(List<Integer> values) {
            addCriterion("IsRemind not in", values, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindBetween(Integer value1, Integer value2) {
            addCriterion("IsRemind between", value1, value2, "isremind");
            return (Criteria) this;
        }

        public Criteria andIsremindNotBetween(Integer value1, Integer value2) {
            addCriterion("IsRemind not between", value1, value2, "isremind");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIsNull() {
            addCriterion("ApplicantName is null");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIsNotNull() {
            addCriterion("ApplicantName is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantnameEqualTo(String value) {
            addCriterion("ApplicantName =", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotEqualTo(String value) {
            addCriterion("ApplicantName <>", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameGreaterThan(String value) {
            addCriterion("ApplicantName >", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantName >=", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLessThan(String value) {
            addCriterion("ApplicantName <", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLessThanOrEqualTo(String value) {
            addCriterion("ApplicantName <=", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameLike(String value) {
            addCriterion("ApplicantName like", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotLike(String value) {
            addCriterion("ApplicantName not like", value, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameIn(List<String> values) {
            addCriterion("ApplicantName in", values, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotIn(List<String> values) {
            addCriterion("ApplicantName not in", values, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameBetween(String value1, String value2) {
            addCriterion("ApplicantName between", value1, value2, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantnameNotBetween(String value1, String value2) {
            addCriterion("ApplicantName not between", value1, value2, "applicantname");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIsNull() {
            addCriterion("ApplicantEmail is null");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIsNotNull() {
            addCriterion("ApplicantEmail is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantemailEqualTo(String value) {
            addCriterion("ApplicantEmail =", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotEqualTo(String value) {
            addCriterion("ApplicantEmail <>", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailGreaterThan(String value) {
            addCriterion("ApplicantEmail >", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantEmail >=", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLessThan(String value) {
            addCriterion("ApplicantEmail <", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLessThanOrEqualTo(String value) {
            addCriterion("ApplicantEmail <=", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailLike(String value) {
            addCriterion("ApplicantEmail like", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotLike(String value) {
            addCriterion("ApplicantEmail not like", value, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailIn(List<String> values) {
            addCriterion("ApplicantEmail in", values, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotIn(List<String> values) {
            addCriterion("ApplicantEmail not in", values, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailBetween(String value1, String value2) {
            addCriterion("ApplicantEmail between", value1, value2, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApplicantemailNotBetween(String value1, String value2) {
            addCriterion("ApplicantEmail not between", value1, value2, "applicantemail");
            return (Criteria) this;
        }

        public Criteria andApprovernameIsNull() {
            addCriterion("ApproverName is null");
            return (Criteria) this;
        }

        public Criteria andApprovernameIsNotNull() {
            addCriterion("ApproverName is not null");
            return (Criteria) this;
        }

        public Criteria andApprovernameEqualTo(String value) {
            addCriterion("ApproverName =", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotEqualTo(String value) {
            addCriterion("ApproverName <>", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameGreaterThan(String value) {
            addCriterion("ApproverName >", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameGreaterThanOrEqualTo(String value) {
            addCriterion("ApproverName >=", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLessThan(String value) {
            addCriterion("ApproverName <", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLessThanOrEqualTo(String value) {
            addCriterion("ApproverName <=", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameLike(String value) {
            addCriterion("ApproverName like", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotLike(String value) {
            addCriterion("ApproverName not like", value, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameIn(List<String> values) {
            addCriterion("ApproverName in", values, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotIn(List<String> values) {
            addCriterion("ApproverName not in", values, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameBetween(String value1, String value2) {
            addCriterion("ApproverName between", value1, value2, "approvername");
            return (Criteria) this;
        }

        public Criteria andApprovernameNotBetween(String value1, String value2) {
            addCriterion("ApproverName not between", value1, value2, "approvername");
            return (Criteria) this;
        }

        public Criteria andApproveremailIsNull() {
            addCriterion("ApproverEmail is null");
            return (Criteria) this;
        }

        public Criteria andApproveremailIsNotNull() {
            addCriterion("ApproverEmail is not null");
            return (Criteria) this;
        }

        public Criteria andApproveremailEqualTo(String value) {
            addCriterion("ApproverEmail =", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotEqualTo(String value) {
            addCriterion("ApproverEmail <>", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailGreaterThan(String value) {
            addCriterion("ApproverEmail >", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailGreaterThanOrEqualTo(String value) {
            addCriterion("ApproverEmail >=", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLessThan(String value) {
            addCriterion("ApproverEmail <", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLessThanOrEqualTo(String value) {
            addCriterion("ApproverEmail <=", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailLike(String value) {
            addCriterion("ApproverEmail like", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotLike(String value) {
            addCriterion("ApproverEmail not like", value, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailIn(List<String> values) {
            addCriterion("ApproverEmail in", values, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotIn(List<String> values) {
            addCriterion("ApproverEmail not in", values, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailBetween(String value1, String value2) {
            addCriterion("ApproverEmail between", value1, value2, "approveremail");
            return (Criteria) this;
        }

        public Criteria andApproveremailNotBetween(String value1, String value2) {
            addCriterion("ApproverEmail not between", value1, value2, "approveremail");
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
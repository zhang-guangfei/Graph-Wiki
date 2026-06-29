package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PsdataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PsdataExample() {
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

        public Criteria andRordernoIsNull() {
            addCriterion("ROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andRordernoIsNotNull() {
            addCriterion("ROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andRordernoEqualTo(String value) {
            addCriterion("ROrderNo =", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotEqualTo(String value) {
            addCriterion("ROrderNo <>", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThan(String value) {
            addCriterion("ROrderNo >", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ROrderNo >=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThan(String value) {
            addCriterion("ROrderNo <", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLessThanOrEqualTo(String value) {
            addCriterion("ROrderNo <=", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoLike(String value) {
            addCriterion("ROrderNo like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotLike(String value) {
            addCriterion("ROrderNo not like", value, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoIn(List<String> values) {
            addCriterion("ROrderNo in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotIn(List<String> values) {
            addCriterion("ROrderNo not in", values, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoBetween(String value1, String value2) {
            addCriterion("ROrderNo between", value1, value2, "rorderno");
            return (Criteria) this;
        }

        public Criteria andRordernoNotBetween(String value1, String value2) {
            addCriterion("ROrderNo not between", value1, value2, "rorderno");
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

        public Criteria andUsernoIsNull() {
            addCriterion("UserNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("UserNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("UserNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("UserNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("UserNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("UserNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("UserNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("UserNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("UserNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("UserNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("UserNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("UserNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("UserNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("UserNo not between", value1, value2, "userno");
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

        public Criteria andDlvdateIsNull() {
            addCriterion("DlvDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvdateIsNotNull() {
            addCriterion("DlvDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvdateEqualTo(Date value) {
            addCriterion("DlvDate =", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotEqualTo(Date value) {
            addCriterion("DlvDate <>", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThan(Date value) {
            addCriterion("DlvDate >", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvDate >=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThan(Date value) {
            addCriterion("DlvDate <", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateLessThanOrEqualTo(Date value) {
            addCriterion("DlvDate <=", value, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateIn(List<Date> values) {
            addCriterion("DlvDate in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotIn(List<Date> values) {
            addCriterion("DlvDate not in", values, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateBetween(Date value1, Date value2) {
            addCriterion("DlvDate between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andDlvdateNotBetween(Date value1, Date value2) {
            addCriterion("DlvDate not between", value1, value2, "dlvdate");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNull() {
            addCriterion("OptCode is null");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNotNull() {
            addCriterion("OptCode is not null");
            return (Criteria) this;
        }

        public Criteria andOptcodeEqualTo(String value) {
            addCriterion("OptCode =", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotEqualTo(String value) {
            addCriterion("OptCode <>", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThan(String value) {
            addCriterion("OptCode >", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("OptCode >=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThan(String value) {
            addCriterion("OptCode <", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThanOrEqualTo(String value) {
            addCriterion("OptCode <=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLike(String value) {
            addCriterion("OptCode like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotLike(String value) {
            addCriterion("OptCode not like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeIn(List<String> values) {
            addCriterion("OptCode in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotIn(List<String> values) {
            addCriterion("OptCode not in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeBetween(String value1, String value2) {
            addCriterion("OptCode between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotBetween(String value1, String value2) {
            addCriterion("OptCode not between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNull() {
            addCriterion("MidSize is null");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNotNull() {
            addCriterion("MidSize is not null");
            return (Criteria) this;
        }

        public Criteria andMidsizeEqualTo(String value) {
            addCriterion("MidSize =", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotEqualTo(String value) {
            addCriterion("MidSize <>", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThan(String value) {
            addCriterion("MidSize >", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThanOrEqualTo(String value) {
            addCriterion("MidSize >=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThan(String value) {
            addCriterion("MidSize <", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThanOrEqualTo(String value) {
            addCriterion("MidSize <=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLike(String value) {
            addCriterion("MidSize like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotLike(String value) {
            addCriterion("MidSize not like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeIn(List<String> values) {
            addCriterion("MidSize in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotIn(List<String> values) {
            addCriterion("MidSize not in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeBetween(String value1, String value2) {
            addCriterion("MidSize between", value1, value2, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotBetween(String value1, String value2) {
            addCriterion("MidSize not between", value1, value2, "midsize");
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

        public Criteria andOrordernoIsNull() {
            addCriterion("OROrderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNotNull() {
            addCriterion("OROrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrordernoEqualTo(String value) {
            addCriterion("OROrderNo =", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotEqualTo(String value) {
            addCriterion("OROrderNo <>", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThan(String value) {
            addCriterion("OROrderNo >", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThanOrEqualTo(String value) {
            addCriterion("OROrderNo >=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThan(String value) {
            addCriterion("OROrderNo <", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThanOrEqualTo(String value) {
            addCriterion("OROrderNo <=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLike(String value) {
            addCriterion("OROrderNo like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotLike(String value) {
            addCriterion("OROrderNo not like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoIn(List<String> values) {
            addCriterion("OROrderNo in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotIn(List<String> values) {
            addCriterion("OROrderNo not in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoBetween(String value1, String value2) {
            addCriterion("OROrderNo between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotBetween(String value1, String value2) {
            addCriterion("OROrderNo not between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNull() {
            addCriterion("ImpDate is null");
            return (Criteria) this;
        }

        public Criteria andImpdateIsNotNull() {
            addCriterion("ImpDate is not null");
            return (Criteria) this;
        }

        public Criteria andImpdateEqualTo(Date value) {
            addCriterion("ImpDate =", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotEqualTo(Date value) {
            addCriterion("ImpDate <>", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThan(Date value) {
            addCriterion("ImpDate >", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("ImpDate >=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThan(Date value) {
            addCriterion("ImpDate <", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateLessThanOrEqualTo(Date value) {
            addCriterion("ImpDate <=", value, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateIn(List<Date> values) {
            addCriterion("ImpDate in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotIn(List<Date> values) {
            addCriterion("ImpDate not in", values, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateBetween(Date value1, Date value2) {
            addCriterion("ImpDate between", value1, value2, "impdate");
            return (Criteria) this;
        }

        public Criteria andImpdateNotBetween(Date value1, Date value2) {
            addCriterion("ImpDate not between", value1, value2, "impdate");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNull() {
            addCriterion("CnNo is null");
            return (Criteria) this;
        }

        public Criteria andCnnoIsNotNull() {
            addCriterion("CnNo is not null");
            return (Criteria) this;
        }

        public Criteria andCnnoEqualTo(String value) {
            addCriterion("CnNo =", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotEqualTo(String value) {
            addCriterion("CnNo <>", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThan(String value) {
            addCriterion("CnNo >", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoGreaterThanOrEqualTo(String value) {
            addCriterion("CnNo >=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThan(String value) {
            addCriterion("CnNo <", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLessThanOrEqualTo(String value) {
            addCriterion("CnNo <=", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoLike(String value) {
            addCriterion("CnNo like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotLike(String value) {
            addCriterion("CnNo not like", value, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoIn(List<String> values) {
            addCriterion("CnNo in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotIn(List<String> values) {
            addCriterion("CnNo not in", values, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoBetween(String value1, String value2) {
            addCriterion("CnNo between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andCnnoNotBetween(String value1, String value2) {
            addCriterion("CnNo not between", value1, value2, "cnno");
            return (Criteria) this;
        }

        public Criteria andInvdateIsNull() {
            addCriterion("InvDate is null");
            return (Criteria) this;
        }

        public Criteria andInvdateIsNotNull() {
            addCriterion("InvDate is not null");
            return (Criteria) this;
        }

        public Criteria andInvdateEqualTo(Date value) {
            addCriterion("InvDate =", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotEqualTo(Date value) {
            addCriterion("InvDate <>", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateGreaterThan(Date value) {
            addCriterion("InvDate >", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateGreaterThanOrEqualTo(Date value) {
            addCriterion("InvDate >=", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateLessThan(Date value) {
            addCriterion("InvDate <", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateLessThanOrEqualTo(Date value) {
            addCriterion("InvDate <=", value, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateIn(List<Date> values) {
            addCriterion("InvDate in", values, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotIn(List<Date> values) {
            addCriterion("InvDate not in", values, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateBetween(Date value1, Date value2) {
            addCriterion("InvDate between", value1, value2, "invdate");
            return (Criteria) this;
        }

        public Criteria andInvdateNotBetween(Date value1, Date value2) {
            addCriterion("InvDate not between", value1, value2, "invdate");
            return (Criteria) this;
        }

        public Criteria andIssuedateIsNull() {
            addCriterion("Issuedate is null");
            return (Criteria) this;
        }

        public Criteria andIssuedateIsNotNull() {
            addCriterion("Issuedate is not null");
            return (Criteria) this;
        }

        public Criteria andIssuedateEqualTo(Date value) {
            addCriterion("Issuedate =", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotEqualTo(Date value) {
            addCriterion("Issuedate <>", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateGreaterThan(Date value) {
            addCriterion("Issuedate >", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateGreaterThanOrEqualTo(Date value) {
            addCriterion("Issuedate >=", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateLessThan(Date value) {
            addCriterion("Issuedate <", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateLessThanOrEqualTo(Date value) {
            addCriterion("Issuedate <=", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateIn(List<Date> values) {
            addCriterion("Issuedate in", values, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotIn(List<Date> values) {
            addCriterion("Issuedate not in", values, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateBetween(Date value1, Date value2) {
            addCriterion("Issuedate between", value1, value2, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotBetween(Date value1, Date value2) {
            addCriterion("Issuedate not between", value1, value2, "issuedate");
            return (Criteria) this;
        }

        public Criteria andCiqflagIsNull() {
            addCriterion("CIQFlag is null");
            return (Criteria) this;
        }

        public Criteria andCiqflagIsNotNull() {
            addCriterion("CIQFlag is not null");
            return (Criteria) this;
        }

        public Criteria andCiqflagEqualTo(String value) {
            addCriterion("CIQFlag =", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagNotEqualTo(String value) {
            addCriterion("CIQFlag <>", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagGreaterThan(String value) {
            addCriterion("CIQFlag >", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagGreaterThanOrEqualTo(String value) {
            addCriterion("CIQFlag >=", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagLessThan(String value) {
            addCriterion("CIQFlag <", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagLessThanOrEqualTo(String value) {
            addCriterion("CIQFlag <=", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagLike(String value) {
            addCriterion("CIQFlag like", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagNotLike(String value) {
            addCriterion("CIQFlag not like", value, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagIn(List<String> values) {
            addCriterion("CIQFlag in", values, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagNotIn(List<String> values) {
            addCriterion("CIQFlag not in", values, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagBetween(String value1, String value2) {
            addCriterion("CIQFlag between", value1, value2, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqflagNotBetween(String value1, String value2) {
            addCriterion("CIQFlag not between", value1, value2, "ciqflag");
            return (Criteria) this;
        }

        public Criteria andCiqdateIsNull() {
            addCriterion("CIQDate is null");
            return (Criteria) this;
        }

        public Criteria andCiqdateIsNotNull() {
            addCriterion("CIQDate is not null");
            return (Criteria) this;
        }

        public Criteria andCiqdateEqualTo(Date value) {
            addCriterion("CIQDate =", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateNotEqualTo(Date value) {
            addCriterion("CIQDate <>", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateGreaterThan(Date value) {
            addCriterion("CIQDate >", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateGreaterThanOrEqualTo(Date value) {
            addCriterion("CIQDate >=", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateLessThan(Date value) {
            addCriterion("CIQDate <", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateLessThanOrEqualTo(Date value) {
            addCriterion("CIQDate <=", value, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateIn(List<Date> values) {
            addCriterion("CIQDate in", values, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateNotIn(List<Date> values) {
            addCriterion("CIQDate not in", values, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateBetween(Date value1, Date value2) {
            addCriterion("CIQDate between", value1, value2, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andCiqdateNotBetween(Date value1, Date value2) {
            addCriterion("CIQDate not between", value1, value2, "ciqdate");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNull() {
            addCriterion("ProdFlag is null");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNotNull() {
            addCriterion("ProdFlag is not null");
            return (Criteria) this;
        }

        public Criteria andProdflagEqualTo(String value) {
            addCriterion("ProdFlag =", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotEqualTo(String value) {
            addCriterion("ProdFlag <>", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThan(String value) {
            addCriterion("ProdFlag >", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThanOrEqualTo(String value) {
            addCriterion("ProdFlag >=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThan(String value) {
            addCriterion("ProdFlag <", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThanOrEqualTo(String value) {
            addCriterion("ProdFlag <=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLike(String value) {
            addCriterion("ProdFlag like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotLike(String value) {
            addCriterion("ProdFlag not like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagIn(List<String> values) {
            addCriterion("ProdFlag in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotIn(List<String> values) {
            addCriterion("ProdFlag not in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagBetween(String value1, String value2) {
            addCriterion("ProdFlag between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotBetween(String value1, String value2) {
            addCriterion("ProdFlag not between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIsNull() {
            addCriterion("DlvModDate is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIsNotNull() {
            addCriterion("DlvModDate is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateEqualTo(Date value) {
            addCriterion("DlvModDate =", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotEqualTo(Date value) {
            addCriterion("DlvModDate <>", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateGreaterThan(Date value) {
            addCriterion("DlvModDate >", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate >=", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateLessThan(Date value) {
            addCriterion("DlvModDate <", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateLessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate <=", value, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateIn(List<Date> values) {
            addCriterion("DlvModDate in", values, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotIn(List<Date> values) {
            addCriterion("DlvModDate not in", values, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateBetween(Date value1, Date value2) {
            addCriterion("DlvModDate between", value1, value2, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvmoddateNotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate not between", value1, value2, "dlvmoddate");
            return (Criteria) this;
        }

        public Criteria andDlvcustIsNull() {
            addCriterion("DlvCust is null");
            return (Criteria) this;
        }

        public Criteria andDlvcustIsNotNull() {
            addCriterion("DlvCust is not null");
            return (Criteria) this;
        }

        public Criteria andDlvcustEqualTo(Date value) {
            addCriterion("DlvCust =", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustNotEqualTo(Date value) {
            addCriterion("DlvCust <>", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustGreaterThan(Date value) {
            addCriterion("DlvCust >", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustGreaterThanOrEqualTo(Date value) {
            addCriterion("DlvCust >=", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustLessThan(Date value) {
            addCriterion("DlvCust <", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustLessThanOrEqualTo(Date value) {
            addCriterion("DlvCust <=", value, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustIn(List<Date> values) {
            addCriterion("DlvCust in", values, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustNotIn(List<Date> values) {
            addCriterion("DlvCust not in", values, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustBetween(Date value1, Date value2) {
            addCriterion("DlvCust between", value1, value2, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andDlvcustNotBetween(Date value1, Date value2) {
            addCriterion("DlvCust not between", value1, value2, "dlvcust");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNull() {
            addCriterion("StateFlag is null");
            return (Criteria) this;
        }

        public Criteria andStateflagIsNotNull() {
            addCriterion("StateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andStateflagEqualTo(String value) {
            addCriterion("StateFlag =", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotEqualTo(String value) {
            addCriterion("StateFlag <>", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThan(String value) {
            addCriterion("StateFlag >", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagGreaterThanOrEqualTo(String value) {
            addCriterion("StateFlag >=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThan(String value) {
            addCriterion("StateFlag <", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLessThanOrEqualTo(String value) {
            addCriterion("StateFlag <=", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagLike(String value) {
            addCriterion("StateFlag like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotLike(String value) {
            addCriterion("StateFlag not like", value, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagIn(List<String> values) {
            addCriterion("StateFlag in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotIn(List<String> values) {
            addCriterion("StateFlag not in", values, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagBetween(String value1, String value2) {
            addCriterion("StateFlag between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andStateflagNotBetween(String value1, String value2) {
            addCriterion("StateFlag not between", value1, value2, "stateflag");
            return (Criteria) this;
        }

        public Criteria andOptflagIsNull() {
            addCriterion("OptFlag is null");
            return (Criteria) this;
        }

        public Criteria andOptflagIsNotNull() {
            addCriterion("OptFlag is not null");
            return (Criteria) this;
        }

        public Criteria andOptflagEqualTo(String value) {
            addCriterion("OptFlag =", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotEqualTo(String value) {
            addCriterion("OptFlag <>", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagGreaterThan(String value) {
            addCriterion("OptFlag >", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagGreaterThanOrEqualTo(String value) {
            addCriterion("OptFlag >=", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLessThan(String value) {
            addCriterion("OptFlag <", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLessThanOrEqualTo(String value) {
            addCriterion("OptFlag <=", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagLike(String value) {
            addCriterion("OptFlag like", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotLike(String value) {
            addCriterion("OptFlag not like", value, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagIn(List<String> values) {
            addCriterion("OptFlag in", values, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotIn(List<String> values) {
            addCriterion("OptFlag not in", values, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagBetween(String value1, String value2) {
            addCriterion("OptFlag between", value1, value2, "optflag");
            return (Criteria) this;
        }

        public Criteria andOptflagNotBetween(String value1, String value2) {
            addCriterion("OptFlag not between", value1, value2, "optflag");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("SMCCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("SMCCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("SMCCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("SMCCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("SMCCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("SMCCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("SMCCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("SMCCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("SMCCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("SMCCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("SMCCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("SMCCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("SMCCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("SMCCode not between", value1, value2, "smccode");
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

        public Criteria andQtyreceiveIsNull() {
            addCriterion("QtyReceive is null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIsNotNull() {
            addCriterion("QtyReceive is not null");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveEqualTo(Integer value) {
            addCriterion("QtyReceive =", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotEqualTo(Integer value) {
            addCriterion("QtyReceive <>", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThan(Integer value) {
            addCriterion("QtyReceive >", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyReceive >=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThan(Integer value) {
            addCriterion("QtyReceive <", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveLessThanOrEqualTo(Integer value) {
            addCriterion("QtyReceive <=", value, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveIn(List<Integer> values) {
            addCriterion("QtyReceive in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotIn(List<Integer> values) {
            addCriterion("QtyReceive not in", values, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveBetween(Integer value1, Integer value2) {
            addCriterion("QtyReceive between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andQtyreceiveNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyReceive not between", value1, value2, "qtyreceive");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("Operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("Operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("Operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("Operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("Operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("Operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("Operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("Operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("Operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("Operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("Operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("Operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("Operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("Operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNull() {
            addCriterion("OptTime is null");
            return (Criteria) this;
        }

        public Criteria andOpttimeIsNotNull() {
            addCriterion("OptTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpttimeEqualTo(Date value) {
            addCriterion("OptTime =", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotEqualTo(Date value) {
            addCriterion("OptTime <>", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThan(Date value) {
            addCriterion("OptTime >", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OptTime >=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThan(Date value) {
            addCriterion("OptTime <", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeLessThanOrEqualTo(Date value) {
            addCriterion("OptTime <=", value, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeIn(List<Date> values) {
            addCriterion("OptTime in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotIn(List<Date> values) {
            addCriterion("OptTime not in", values, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeBetween(Date value1, Date value2) {
            addCriterion("OptTime between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andOpttimeNotBetween(Date value1, Date value2) {
            addCriterion("OptTime not between", value1, value2, "opttime");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIsNull() {
            addCriterion("Expdate_GC is null");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIsNotNull() {
            addCriterion("Expdate_GC is not null");
            return (Criteria) this;
        }

        public Criteria andExpdateGcEqualTo(Date value) {
            addCriterion("Expdate_GC =", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotEqualTo(Date value) {
            addCriterion("Expdate_GC <>", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcGreaterThan(Date value) {
            addCriterion("Expdate_GC >", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcGreaterThanOrEqualTo(Date value) {
            addCriterion("Expdate_GC >=", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcLessThan(Date value) {
            addCriterion("Expdate_GC <", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcLessThanOrEqualTo(Date value) {
            addCriterion("Expdate_GC <=", value, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcIn(List<Date> values) {
            addCriterion("Expdate_GC in", values, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotIn(List<Date> values) {
            addCriterion("Expdate_GC not in", values, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcBetween(Date value1, Date value2) {
            addCriterion("Expdate_GC between", value1, value2, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andExpdateGcNotBetween(Date value1, Date value2) {
            addCriterion("Expdate_GC not between", value1, value2, "expdateGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIsNull() {
            addCriterion("Remark_GC is null");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIsNotNull() {
            addCriterion("Remark_GC is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkGcEqualTo(String value) {
            addCriterion("Remark_GC =", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotEqualTo(String value) {
            addCriterion("Remark_GC <>", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcGreaterThan(String value) {
            addCriterion("Remark_GC >", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcGreaterThanOrEqualTo(String value) {
            addCriterion("Remark_GC >=", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLessThan(String value) {
            addCriterion("Remark_GC <", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLessThanOrEqualTo(String value) {
            addCriterion("Remark_GC <=", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcLike(String value) {
            addCriterion("Remark_GC like", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotLike(String value) {
            addCriterion("Remark_GC not like", value, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcIn(List<String> values) {
            addCriterion("Remark_GC in", values, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotIn(List<String> values) {
            addCriterion("Remark_GC not in", values, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcBetween(String value1, String value2) {
            addCriterion("Remark_GC between", value1, value2, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andRemarkGcNotBetween(String value1, String value2) {
            addCriterion("Remark_GC not between", value1, value2, "remarkGc");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNull() {
            addCriterion("EmpOrdProcess is null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIsNotNull() {
            addCriterion("EmpOrdProcess is not null");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessEqualTo(String value) {
            addCriterion("EmpOrdProcess =", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotEqualTo(String value) {
            addCriterion("EmpOrdProcess <>", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThan(String value) {
            addCriterion("EmpOrdProcess >", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessGreaterThanOrEqualTo(String value) {
            addCriterion("EmpOrdProcess >=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThan(String value) {
            addCriterion("EmpOrdProcess <", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLessThanOrEqualTo(String value) {
            addCriterion("EmpOrdProcess <=", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessLike(String value) {
            addCriterion("EmpOrdProcess like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotLike(String value) {
            addCriterion("EmpOrdProcess not like", value, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessIn(List<String> values) {
            addCriterion("EmpOrdProcess in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotIn(List<String> values) {
            addCriterion("EmpOrdProcess not in", values, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessBetween(String value1, String value2) {
            addCriterion("EmpOrdProcess between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andEmpordprocessNotBetween(String value1, String value2) {
            addCriterion("EmpOrdProcess not between", value1, value2, "empordprocess");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1IsNull() {
            addCriterion("DlvModDate1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1IsNotNull() {
            addCriterion("DlvModDate1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1EqualTo(Date value) {
            addCriterion("DlvModDate1 =", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotEqualTo(Date value) {
            addCriterion("DlvModDate1 <>", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1GreaterThan(Date value) {
            addCriterion("DlvModDate1 >", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate1 >=", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1LessThan(Date value) {
            addCriterion("DlvModDate1 <", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate1 <=", value, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1In(List<Date> values) {
            addCriterion("DlvModDate1 in", values, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotIn(List<Date> values) {
            addCriterion("DlvModDate1 not in", values, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1Between(Date value1, Date value2) {
            addCriterion("DlvModDate1 between", value1, value2, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate1NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate1 not between", value1, value2, "dlvmoddate1");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2IsNull() {
            addCriterion("DlvModDate2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2IsNotNull() {
            addCriterion("DlvModDate2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2EqualTo(Date value) {
            addCriterion("DlvModDate2 =", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotEqualTo(Date value) {
            addCriterion("DlvModDate2 <>", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2GreaterThan(Date value) {
            addCriterion("DlvModDate2 >", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate2 >=", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2LessThan(Date value) {
            addCriterion("DlvModDate2 <", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate2 <=", value, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2In(List<Date> values) {
            addCriterion("DlvModDate2 in", values, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotIn(List<Date> values) {
            addCriterion("DlvModDate2 not in", values, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2Between(Date value1, Date value2) {
            addCriterion("DlvModDate2 between", value1, value2, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate2NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate2 not between", value1, value2, "dlvmoddate2");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3IsNull() {
            addCriterion("DlvModDate3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3IsNotNull() {
            addCriterion("DlvModDate3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3EqualTo(Date value) {
            addCriterion("DlvModDate3 =", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotEqualTo(Date value) {
            addCriterion("DlvModDate3 <>", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3GreaterThan(Date value) {
            addCriterion("DlvModDate3 >", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModDate3 >=", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3LessThan(Date value) {
            addCriterion("DlvModDate3 <", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3LessThanOrEqualTo(Date value) {
            addCriterion("DlvModDate3 <=", value, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3In(List<Date> values) {
            addCriterion("DlvModDate3 in", values, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotIn(List<Date> values) {
            addCriterion("DlvModDate3 not in", values, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3Between(Date value1, Date value2) {
            addCriterion("DlvModDate3 between", value1, value2, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andDlvmoddate3NotBetween(Date value1, Date value2) {
            addCriterion("DlvModDate3 not between", value1, value2, "dlvmoddate3");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIsNull() {
            addCriterion("Reason_Remark is null");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIsNotNull() {
            addCriterion("Reason_Remark is not null");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkEqualTo(String value) {
            addCriterion("Reason_Remark =", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotEqualTo(String value) {
            addCriterion("Reason_Remark <>", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkGreaterThan(String value) {
            addCriterion("Reason_Remark >", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Reason_Remark >=", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLessThan(String value) {
            addCriterion("Reason_Remark <", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLessThanOrEqualTo(String value) {
            addCriterion("Reason_Remark <=", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkLike(String value) {
            addCriterion("Reason_Remark like", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotLike(String value) {
            addCriterion("Reason_Remark not like", value, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkIn(List<String> values) {
            addCriterion("Reason_Remark in", values, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotIn(List<String> values) {
            addCriterion("Reason_Remark not in", values, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkBetween(String value1, String value2) {
            addCriterion("Reason_Remark between", value1, value2, "reasonRemark");
            return (Criteria) this;
        }

        public Criteria andReasonRemarkNotBetween(String value1, String value2) {
            addCriterion("Reason_Remark not between", value1, value2, "reasonRemark");
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

        public Criteria andShikominoIsNull() {
            addCriterion("SHIKOMINo is null");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNotNull() {
            addCriterion("SHIKOMINo is not null");
            return (Criteria) this;
        }

        public Criteria andShikominoEqualTo(String value) {
            addCriterion("SHIKOMINo =", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotEqualTo(String value) {
            addCriterion("SHIKOMINo <>", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThan(String value) {
            addCriterion("SHIKOMINo >", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThanOrEqualTo(String value) {
            addCriterion("SHIKOMINo >=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThan(String value) {
            addCriterion("SHIKOMINo <", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThanOrEqualTo(String value) {
            addCriterion("SHIKOMINo <=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLike(String value) {
            addCriterion("SHIKOMINo like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotLike(String value) {
            addCriterion("SHIKOMINo not like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoIn(List<String> values) {
            addCriterion("SHIKOMINo in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotIn(List<String> values) {
            addCriterion("SHIKOMINo not in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoBetween(String value1, String value2) {
            addCriterion("SHIKOMINo between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotBetween(String value1, String value2) {
            addCriterion("SHIKOMINo not between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("OrdType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("OrdType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("OrdType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("OrdType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("OrdType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("OrdType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("OrdType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("OrdType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("OrdType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("OrdType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("OrdType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("OrdType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("OrdType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("OrdType not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIsNull() {
            addCriterion("Produce_Factory is null");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIsNotNull() {
            addCriterion("Produce_Factory is not null");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryEqualTo(String value) {
            addCriterion("Produce_Factory =", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotEqualTo(String value) {
            addCriterion("Produce_Factory <>", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryGreaterThan(String value) {
            addCriterion("Produce_Factory >", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryGreaterThanOrEqualTo(String value) {
            addCriterion("Produce_Factory >=", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLessThan(String value) {
            addCriterion("Produce_Factory <", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLessThanOrEqualTo(String value) {
            addCriterion("Produce_Factory <=", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryLike(String value) {
            addCriterion("Produce_Factory like", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotLike(String value) {
            addCriterion("Produce_Factory not like", value, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryIn(List<String> values) {
            addCriterion("Produce_Factory in", values, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotIn(List<String> values) {
            addCriterion("Produce_Factory not in", values, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryBetween(String value1, String value2) {
            addCriterion("Produce_Factory between", value1, value2, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceFactoryNotBetween(String value1, String value2) {
            addCriterion("Produce_Factory not between", value1, value2, "produceFactory");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIsNull() {
            addCriterion("Produce_Holon is null");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIsNotNull() {
            addCriterion("Produce_Holon is not null");
            return (Criteria) this;
        }

        public Criteria andProduceHolonEqualTo(String value) {
            addCriterion("Produce_Holon =", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotEqualTo(String value) {
            addCriterion("Produce_Holon <>", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonGreaterThan(String value) {
            addCriterion("Produce_Holon >", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonGreaterThanOrEqualTo(String value) {
            addCriterion("Produce_Holon >=", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLessThan(String value) {
            addCriterion("Produce_Holon <", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLessThanOrEqualTo(String value) {
            addCriterion("Produce_Holon <=", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonLike(String value) {
            addCriterion("Produce_Holon like", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotLike(String value) {
            addCriterion("Produce_Holon not like", value, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonIn(List<String> values) {
            addCriterion("Produce_Holon in", values, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotIn(List<String> values) {
            addCriterion("Produce_Holon not in", values, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonBetween(String value1, String value2) {
            addCriterion("Produce_Holon between", value1, value2, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andProduceHolonNotBetween(String value1, String value2) {
            addCriterion("Produce_Holon not between", value1, value2, "produceHolon");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNull() {
            addCriterion("SalesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("SalesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("SalesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("SalesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("SalesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("SalesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("SalesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("SalesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("SalesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("SalesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("SalesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("SalesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andGreencodeIsNull() {
            addCriterion("GreenCode is null");
            return (Criteria) this;
        }

        public Criteria andGreencodeIsNotNull() {
            addCriterion("GreenCode is not null");
            return (Criteria) this;
        }

        public Criteria andGreencodeEqualTo(String value) {
            addCriterion("GreenCode =", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotEqualTo(String value) {
            addCriterion("GreenCode <>", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThan(String value) {
            addCriterion("GreenCode >", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeGreaterThanOrEqualTo(String value) {
            addCriterion("GreenCode >=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThan(String value) {
            addCriterion("GreenCode <", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLessThanOrEqualTo(String value) {
            addCriterion("GreenCode <=", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeLike(String value) {
            addCriterion("GreenCode like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotLike(String value) {
            addCriterion("GreenCode not like", value, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeIn(List<String> values) {
            addCriterion("GreenCode in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotIn(List<String> values) {
            addCriterion("GreenCode not in", values, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeBetween(String value1, String value2) {
            addCriterion("GreenCode between", value1, value2, "greencode");
            return (Criteria) this;
        }

        public Criteria andGreencodeNotBetween(String value1, String value2) {
            addCriterion("GreenCode not between", value1, value2, "greencode");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNull() {
            addCriterion("InDate_theory is null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNotNull() {
            addCriterion("InDate_theory is not null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryEqualTo(Date value) {
            addCriterion("InDate_theory =", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotEqualTo(Date value) {
            addCriterion("InDate_theory <>", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThan(Date value) {
            addCriterion("InDate_theory >", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_theory >=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThan(Date value) {
            addCriterion("InDate_theory <", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThanOrEqualTo(Date value) {
            addCriterion("InDate_theory <=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIn(List<Date> values) {
            addCriterion("InDate_theory in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotIn(List<Date> values) {
            addCriterion("InDate_theory not in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryBetween(Date value1, Date value2) {
            addCriterion("InDate_theory between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotBetween(Date value1, Date value2) {
            addCriterion("InDate_theory not between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNull() {
            addCriterion("RcvOrd_Date is null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNotNull() {
            addCriterion("RcvOrd_Date is not null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateEqualTo(Date value) {
            addCriterion("RcvOrd_Date =", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotEqualTo(Date value) {
            addCriterion("RcvOrd_Date <>", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThan(Date value) {
            addCriterion("RcvOrd_Date >", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date >=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThan(Date value) {
            addCriterion("RcvOrd_Date <", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date <=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIn(List<Date> values) {
            addCriterion("RcvOrd_Date in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotIn(List<Date> values) {
            addCriterion("RcvOrd_Date not in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date not between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNull() {
            addCriterion("BeginProduce_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNotNull() {
            addCriterion("BeginProduce_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateEqualTo(Date value) {
            addCriterion("BeginProduce_date =", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotEqualTo(Date value) {
            addCriterion("BeginProduce_date <>", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThan(Date value) {
            addCriterion("BeginProduce_date >", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date >=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThan(Date value) {
            addCriterion("BeginProduce_date <", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date <=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIn(List<Date> values) {
            addCriterion("BeginProduce_date in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotIn(List<Date> values) {
            addCriterion("BeginProduce_date not in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date not between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNull() {
            addCriterion("InDate_InFact is null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNotNull() {
            addCriterion("InDate_InFact is not null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactEqualTo(Date value) {
            addCriterion("InDate_InFact =", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotEqualTo(Date value) {
            addCriterion("InDate_InFact <>", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThan(Date value) {
            addCriterion("InDate_InFact >", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact >=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThan(Date value) {
            addCriterion("InDate_InFact <", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact <=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIn(List<Date> values) {
            addCriterion("InDate_InFact in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotIn(List<Date> values) {
            addCriterion("InDate_InFact not in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact not between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNull() {
            addCriterion("Exp_date is null");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNotNull() {
            addCriterion("Exp_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpDateEqualTo(Date value) {
            addCriterion("Exp_date =", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotEqualTo(Date value) {
            addCriterion("Exp_date <>", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThan(Date value) {
            addCriterion("Exp_date >", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThanOrEqualTo(Date value) {
            addCriterion("Exp_date >=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThan(Date value) {
            addCriterion("Exp_date <", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThanOrEqualTo(Date value) {
            addCriterion("Exp_date <=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateIn(List<Date> values) {
            addCriterion("Exp_date in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotIn(List<Date> values) {
            addCriterion("Exp_date not in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateBetween(Date value1, Date value2) {
            addCriterion("Exp_date between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotBetween(Date value1, Date value2) {
            addCriterion("Exp_date not between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNull() {
            addCriterion("Export_Flag is null");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNotNull() {
            addCriterion("Export_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andExportFlagEqualTo(String value) {
            addCriterion("Export_Flag =", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotEqualTo(String value) {
            addCriterion("Export_Flag <>", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThan(String value) {
            addCriterion("Export_Flag >", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThanOrEqualTo(String value) {
            addCriterion("Export_Flag >=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThan(String value) {
            addCriterion("Export_Flag <", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThanOrEqualTo(String value) {
            addCriterion("Export_Flag <=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLike(String value) {
            addCriterion("Export_Flag like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotLike(String value) {
            addCriterion("Export_Flag not like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagIn(List<String> values) {
            addCriterion("Export_Flag in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotIn(List<String> values) {
            addCriterion("Export_Flag not in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagBetween(String value1, String value2) {
            addCriterion("Export_Flag between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotBetween(String value1, String value2) {
            addCriterion("Export_Flag not between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNull() {
            addCriterion("Holon_Name is null");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNotNull() {
            addCriterion("Holon_Name is not null");
            return (Criteria) this;
        }

        public Criteria andHolonNameEqualTo(String value) {
            addCriterion("Holon_Name =", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotEqualTo(String value) {
            addCriterion("Holon_Name <>", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThan(String value) {
            addCriterion("Holon_Name >", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThanOrEqualTo(String value) {
            addCriterion("Holon_Name >=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThan(String value) {
            addCriterion("Holon_Name <", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThanOrEqualTo(String value) {
            addCriterion("Holon_Name <=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLike(String value) {
            addCriterion("Holon_Name like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotLike(String value) {
            addCriterion("Holon_Name not like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameIn(List<String> values) {
            addCriterion("Holon_Name in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotIn(List<String> values) {
            addCriterion("Holon_Name not in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameBetween(String value1, String value2) {
            addCriterion("Holon_Name between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotBetween(String value1, String value2) {
            addCriterion("Holon_Name not between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1IsNull() {
            addCriterion("DlvModTime1 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1IsNotNull() {
            addCriterion("DlvModTime1 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1EqualTo(Date value) {
            addCriterion("DlvModTime1 =", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotEqualTo(Date value) {
            addCriterion("DlvModTime1 <>", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1GreaterThan(Date value) {
            addCriterion("DlvModTime1 >", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime1 >=", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1LessThan(Date value) {
            addCriterion("DlvModTime1 <", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime1 <=", value, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1In(List<Date> values) {
            addCriterion("DlvModTime1 in", values, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotIn(List<Date> values) {
            addCriterion("DlvModTime1 not in", values, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1Between(Date value1, Date value2) {
            addCriterion("DlvModTime1 between", value1, value2, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime1NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime1 not between", value1, value2, "dlvmodtime1");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2IsNull() {
            addCriterion("DlvModTime2 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2IsNotNull() {
            addCriterion("DlvModTime2 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2EqualTo(Date value) {
            addCriterion("DlvModTime2 =", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotEqualTo(Date value) {
            addCriterion("DlvModTime2 <>", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2GreaterThan(Date value) {
            addCriterion("DlvModTime2 >", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime2 >=", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2LessThan(Date value) {
            addCriterion("DlvModTime2 <", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime2 <=", value, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2In(List<Date> values) {
            addCriterion("DlvModTime2 in", values, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotIn(List<Date> values) {
            addCriterion("DlvModTime2 not in", values, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2Between(Date value1, Date value2) {
            addCriterion("DlvModTime2 between", value1, value2, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime2NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime2 not between", value1, value2, "dlvmodtime2");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3IsNull() {
            addCriterion("DlvModTime3 is null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3IsNotNull() {
            addCriterion("DlvModTime3 is not null");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3EqualTo(Date value) {
            addCriterion("DlvModTime3 =", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotEqualTo(Date value) {
            addCriterion("DlvModTime3 <>", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3GreaterThan(Date value) {
            addCriterion("DlvModTime3 >", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3GreaterThanOrEqualTo(Date value) {
            addCriterion("DlvModTime3 >=", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3LessThan(Date value) {
            addCriterion("DlvModTime3 <", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3LessThanOrEqualTo(Date value) {
            addCriterion("DlvModTime3 <=", value, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3In(List<Date> values) {
            addCriterion("DlvModTime3 in", values, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotIn(List<Date> values) {
            addCriterion("DlvModTime3 not in", values, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3Between(Date value1, Date value2) {
            addCriterion("DlvModTime3 between", value1, value2, "dlvmodtime3");
            return (Criteria) this;
        }

        public Criteria andDlvmodtime3NotBetween(Date value1, Date value2) {
            addCriterion("DlvModTime3 not between", value1, value2, "dlvmodtime3");
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
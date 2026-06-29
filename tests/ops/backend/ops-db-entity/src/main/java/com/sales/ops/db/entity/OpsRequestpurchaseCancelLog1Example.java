package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OpsRequestpurchaseCancelLog1Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsRequestpurchaseCancelLog1Example() {
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

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("itemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("itemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Integer value) {
            addCriterion("itemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Integer value) {
            addCriterion("itemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Integer value) {
            addCriterion("itemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Integer value) {
            addCriterion("itemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Integer value) {
            addCriterion("itemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Integer> values) {
            addCriterion("itemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Integer> values) {
            addCriterion("itemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Integer value1, Integer value2) {
            addCriterion("itemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("itemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNull() {
            addCriterion("splitItemNo is null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIsNotNull() {
            addCriterion("splitItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andSplititemnoEqualTo(Integer value) {
            addCriterion("splitItemNo =", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotEqualTo(Integer value) {
            addCriterion("splitItemNo <>", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThan(Integer value) {
            addCriterion("splitItemNo >", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo >=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThan(Integer value) {
            addCriterion("splitItemNo <", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoLessThanOrEqualTo(Integer value) {
            addCriterion("splitItemNo <=", value, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoIn(List<Integer> values) {
            addCriterion("splitItemNo in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotIn(List<Integer> values) {
            addCriterion("splitItemNo not in", values, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andSplititemnoNotBetween(Integer value1, Integer value2) {
            addCriterion("splitItemNo not between", value1, value2, "splititemno");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNull() {
            addCriterion("stateCode is null");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNotNull() {
            addCriterion("stateCode is not null");
            return (Criteria) this;
        }

        public Criteria andStatecodeEqualTo(String value) {
            addCriterion("stateCode =", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotEqualTo(String value) {
            addCriterion("stateCode <>", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThan(String value) {
            addCriterion("stateCode >", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("stateCode >=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThan(String value) {
            addCriterion("stateCode <", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThanOrEqualTo(String value) {
            addCriterion("stateCode <=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLike(String value) {
            addCriterion("stateCode like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotLike(String value) {
            addCriterion("stateCode not like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeIn(List<String> values) {
            addCriterion("stateCode in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotIn(List<String> values) {
            addCriterion("stateCode not in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeBetween(String value1, String value2) {
            addCriterion("stateCode between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotBetween(String value1, String value2) {
            addCriterion("stateCode not between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andMergeflagIsNull() {
            addCriterion("mergeflag is null");
            return (Criteria) this;
        }

        public Criteria andMergeflagIsNotNull() {
            addCriterion("mergeflag is not null");
            return (Criteria) this;
        }

        public Criteria andMergeflagEqualTo(Boolean value) {
            addCriterion("mergeflag =", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotEqualTo(Boolean value) {
            addCriterion("mergeflag <>", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagGreaterThan(Boolean value) {
            addCriterion("mergeflag >", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mergeflag >=", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagLessThan(Boolean value) {
            addCriterion("mergeflag <", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagLessThanOrEqualTo(Boolean value) {
            addCriterion("mergeflag <=", value, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagIn(List<Boolean> values) {
            addCriterion("mergeflag in", values, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotIn(List<Boolean> values) {
            addCriterion("mergeflag not in", values, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagBetween(Boolean value1, Boolean value2) {
            addCriterion("mergeflag between", value1, value2, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andMergeflagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mergeflag not between", value1, value2, "mergeflag");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNull() {
            addCriterion("userNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("userNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("userNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("userNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("userNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("userNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("userNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("userNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("userNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("userNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("userNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("userNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("userNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("userNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNull() {
            addCriterion("deptNo is null");
            return (Criteria) this;
        }

        public Criteria andDeptnoIsNotNull() {
            addCriterion("deptNo is not null");
            return (Criteria) this;
        }

        public Criteria andDeptnoEqualTo(String value) {
            addCriterion("deptNo =", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotEqualTo(String value) {
            addCriterion("deptNo <>", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThan(String value) {
            addCriterion("deptNo >", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoGreaterThanOrEqualTo(String value) {
            addCriterion("deptNo >=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThan(String value) {
            addCriterion("deptNo <", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLessThanOrEqualTo(String value) {
            addCriterion("deptNo <=", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoLike(String value) {
            addCriterion("deptNo like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotLike(String value) {
            addCriterion("deptNo not like", value, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoIn(List<String> values) {
            addCriterion("deptNo in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotIn(List<String> values) {
            addCriterion("deptNo not in", values, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoBetween(String value1, String value2) {
            addCriterion("deptNo between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andDeptnoNotBetween(String value1, String value2) {
            addCriterion("deptNo not between", value1, value2, "deptno");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIsNull() {
            addCriterion("apply_dept_no is null");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIsNotNull() {
            addCriterion("apply_dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoEqualTo(String value) {
            addCriterion("apply_dept_no =", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotEqualTo(String value) {
            addCriterion("apply_dept_no <>", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoGreaterThan(String value) {
            addCriterion("apply_dept_no >", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("apply_dept_no >=", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLessThan(String value) {
            addCriterion("apply_dept_no <", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLessThanOrEqualTo(String value) {
            addCriterion("apply_dept_no <=", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoLike(String value) {
            addCriterion("apply_dept_no like", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotLike(String value) {
            addCriterion("apply_dept_no not like", value, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoIn(List<String> values) {
            addCriterion("apply_dept_no in", values, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotIn(List<String> values) {
            addCriterion("apply_dept_no not in", values, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoBetween(String value1, String value2) {
            addCriterion("apply_dept_no between", value1, value2, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andApplyDeptNoNotBetween(String value1, String value2) {
            addCriterion("apply_dept_no not between", value1, value2, "applyDeptNo");
            return (Criteria) this;
        }

        public Criteria andInqnoIsNull() {
            addCriterion("inqNo is null");
            return (Criteria) this;
        }

        public Criteria andInqnoIsNotNull() {
            addCriterion("inqNo is not null");
            return (Criteria) this;
        }

        public Criteria andInqnoEqualTo(String value) {
            addCriterion("inqNo =", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotEqualTo(String value) {
            addCriterion("inqNo <>", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoGreaterThan(String value) {
            addCriterion("inqNo >", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoGreaterThanOrEqualTo(String value) {
            addCriterion("inqNo >=", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLessThan(String value) {
            addCriterion("inqNo <", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLessThanOrEqualTo(String value) {
            addCriterion("inqNo <=", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoLike(String value) {
            addCriterion("inqNo like", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotLike(String value) {
            addCriterion("inqNo not like", value, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoIn(List<String> values) {
            addCriterion("inqNo in", values, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotIn(List<String> values) {
            addCriterion("inqNo not in", values, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoBetween(String value1, String value2) {
            addCriterion("inqNo between", value1, value2, "inqno");
            return (Criteria) this;
        }

        public Criteria andInqnoNotBetween(String value1, String value2) {
            addCriterion("inqNo not between", value1, value2, "inqno");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNull() {
            addCriterion("ordType is null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIsNotNull() {
            addCriterion("ordType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdtypeEqualTo(String value) {
            addCriterion("ordType =", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotEqualTo(String value) {
            addCriterion("ordType <>", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThan(String value) {
            addCriterion("ordType >", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ordType >=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThan(String value) {
            addCriterion("ordType <", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLessThanOrEqualTo(String value) {
            addCriterion("ordType <=", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeLike(String value) {
            addCriterion("ordType like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotLike(String value) {
            addCriterion("ordType not like", value, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeIn(List<String> values) {
            addCriterion("ordType in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotIn(List<String> values) {
            addCriterion("ordType not in", values, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeBetween(String value1, String value2) {
            addCriterion("ordType between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andOrdtypeNotBetween(String value1, String value2) {
            addCriterion("ordType not between", value1, value2, "ordtype");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andStdpriceIsNull() {
            addCriterion("stdPrice is null");
            return (Criteria) this;
        }

        public Criteria andStdpriceIsNotNull() {
            addCriterion("stdPrice is not null");
            return (Criteria) this;
        }

        public Criteria andStdpriceEqualTo(BigDecimal value) {
            addCriterion("stdPrice =", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotEqualTo(BigDecimal value) {
            addCriterion("stdPrice <>", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThan(BigDecimal value) {
            addCriterion("stdPrice >", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("stdPrice >=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThan(BigDecimal value) {
            addCriterion("stdPrice <", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("stdPrice <=", value, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceIn(List<BigDecimal> values) {
            addCriterion("stdPrice in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotIn(List<BigDecimal> values) {
            addCriterion("stdPrice not in", values, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stdPrice between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andStdpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stdPrice not between", value1, value2, "stdprice");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNull() {
            addCriterion("specMark is null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIsNotNull() {
            addCriterion("specMark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecmarkEqualTo(String value) {
            addCriterion("specMark =", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotEqualTo(String value) {
            addCriterion("specMark <>", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThan(String value) {
            addCriterion("specMark >", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkGreaterThanOrEqualTo(String value) {
            addCriterion("specMark >=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThan(String value) {
            addCriterion("specMark <", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLessThanOrEqualTo(String value) {
            addCriterion("specMark <=", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkLike(String value) {
            addCriterion("specMark like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotLike(String value) {
            addCriterion("specMark not like", value, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkIn(List<String> values) {
            addCriterion("specMark in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotIn(List<String> values) {
            addCriterion("specMark not in", values, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkBetween(String value1, String value2) {
            addCriterion("specMark between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andSpecmarkNotBetween(String value1, String value2) {
            addCriterion("specMark not between", value1, value2, "specmark");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIsNull() {
            addCriterion("shikomiAnswerNo is null");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIsNotNull() {
            addCriterion("shikomiAnswerNo is not null");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoEqualTo(String value) {
            addCriterion("shikomiAnswerNo =", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotEqualTo(String value) {
            addCriterion("shikomiAnswerNo <>", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoGreaterThan(String value) {
            addCriterion("shikomiAnswerNo >", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoGreaterThanOrEqualTo(String value) {
            addCriterion("shikomiAnswerNo >=", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLessThan(String value) {
            addCriterion("shikomiAnswerNo <", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLessThanOrEqualTo(String value) {
            addCriterion("shikomiAnswerNo <=", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoLike(String value) {
            addCriterion("shikomiAnswerNo like", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotLike(String value) {
            addCriterion("shikomiAnswerNo not like", value, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoIn(List<String> values) {
            addCriterion("shikomiAnswerNo in", values, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotIn(List<String> values) {
            addCriterion("shikomiAnswerNo not in", values, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoBetween(String value1, String value2) {
            addCriterion("shikomiAnswerNo between", value1, value2, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomianswernoNotBetween(String value1, String value2) {
            addCriterion("shikomiAnswerNo not between", value1, value2, "shikomianswerno");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyIsNull() {
            addCriterion("shikomiRemainQty is null");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyIsNotNull() {
            addCriterion("shikomiRemainQty is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyEqualTo(Integer value) {
            addCriterion("shikomiRemainQty =", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyNotEqualTo(Integer value) {
            addCriterion("shikomiRemainQty <>", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyGreaterThan(Integer value) {
            addCriterion("shikomiRemainQty >", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("shikomiRemainQty >=", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyLessThan(Integer value) {
            addCriterion("shikomiRemainQty <", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyLessThanOrEqualTo(Integer value) {
            addCriterion("shikomiRemainQty <=", value, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyIn(List<Integer> values) {
            addCriterion("shikomiRemainQty in", values, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyNotIn(List<Integer> values) {
            addCriterion("shikomiRemainQty not in", values, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyBetween(Integer value1, Integer value2) {
            addCriterion("shikomiRemainQty between", value1, value2, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andShikomiremainqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("shikomiRemainQty not between", value1, value2, "shikomiremainqty");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNull() {
            addCriterion("hopeDeliveryDate is null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIsNotNull() {
            addCriterion("hopeDeliveryDate is not null");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate =", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <>", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThan(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate >", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate >=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThan(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeDeliveryDate <=", value, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateIn(List<Date> values) {
            addCriterionForJDBCDate("hopeDeliveryDate in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hopeDeliveryDate not in", values, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeDeliveryDate between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andHopedeliverydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeDeliveryDate not between", value1, value2, "hopedeliverydate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNull() {
            addCriterion("salesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("salesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("salesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("salesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("salesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("salesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("salesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("salesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("salesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("salesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("salesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("salesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("salesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("salesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIsNull() {
            addCriterion("requestTime is null");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIsNotNull() {
            addCriterion("requestTime is not null");
            return (Criteria) this;
        }

        public Criteria andRequesttimeEqualTo(Date value) {
            addCriterion("requestTime =", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotEqualTo(Date value) {
            addCriterion("requestTime <>", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeGreaterThan(Date value) {
            addCriterion("requestTime >", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("requestTime >=", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeLessThan(Date value) {
            addCriterion("requestTime <", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeLessThanOrEqualTo(Date value) {
            addCriterion("requestTime <=", value, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeIn(List<Date> values) {
            addCriterion("requestTime in", values, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotIn(List<Date> values) {
            addCriterion("requestTime not in", values, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeBetween(Date value1, Date value2) {
            addCriterion("requestTime between", value1, value2, "requesttime");
            return (Criteria) this;
        }

        public Criteria andRequesttimeNotBetween(Date value1, Date value2) {
            addCriterion("requestTime not between", value1, value2, "requesttime");
            return (Criteria) this;
        }

        public Criteria andProducttagIsNull() {
            addCriterion("productTag is null");
            return (Criteria) this;
        }

        public Criteria andProducttagIsNotNull() {
            addCriterion("productTag is not null");
            return (Criteria) this;
        }

        public Criteria andProducttagEqualTo(String value) {
            addCriterion("productTag =", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagNotEqualTo(String value) {
            addCriterion("productTag <>", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagGreaterThan(String value) {
            addCriterion("productTag >", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagGreaterThanOrEqualTo(String value) {
            addCriterion("productTag >=", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagLessThan(String value) {
            addCriterion("productTag <", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagLessThanOrEqualTo(String value) {
            addCriterion("productTag <=", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagLike(String value) {
            addCriterion("productTag like", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagNotLike(String value) {
            addCriterion("productTag not like", value, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagIn(List<String> values) {
            addCriterion("productTag in", values, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagNotIn(List<String> values) {
            addCriterion("productTag not in", values, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagBetween(String value1, String value2) {
            addCriterion("productTag between", value1, value2, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagNotBetween(String value1, String value2) {
            addCriterion("productTag not between", value1, value2, "producttag");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkIsNull() {
            addCriterion("productTagRemark is null");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkIsNotNull() {
            addCriterion("productTagRemark is not null");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkEqualTo(String value) {
            addCriterion("productTagRemark =", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkNotEqualTo(String value) {
            addCriterion("productTagRemark <>", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkGreaterThan(String value) {
            addCriterion("productTagRemark >", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkGreaterThanOrEqualTo(String value) {
            addCriterion("productTagRemark >=", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkLessThan(String value) {
            addCriterion("productTagRemark <", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkLessThanOrEqualTo(String value) {
            addCriterion("productTagRemark <=", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkLike(String value) {
            addCriterion("productTagRemark like", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkNotLike(String value) {
            addCriterion("productTagRemark not like", value, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkIn(List<String> values) {
            addCriterion("productTagRemark in", values, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkNotIn(List<String> values) {
            addCriterion("productTagRemark not in", values, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkBetween(String value1, String value2) {
            addCriterion("productTagRemark between", value1, value2, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andProducttagremarkNotBetween(String value1, String value2) {
            addCriterion("productTagRemark not between", value1, value2, "producttagremark");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIsNull() {
            addCriterion("requestWarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIsNotNull() {
            addCriterion("requestWarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidEqualTo(String value) {
            addCriterion("requestWarehouseId =", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotEqualTo(String value) {
            addCriterion("requestWarehouseId <>", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidGreaterThan(String value) {
            addCriterion("requestWarehouseId >", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("requestWarehouseId >=", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLessThan(String value) {
            addCriterion("requestWarehouseId <", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLessThanOrEqualTo(String value) {
            addCriterion("requestWarehouseId <=", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidLike(String value) {
            addCriterion("requestWarehouseId like", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotLike(String value) {
            addCriterion("requestWarehouseId not like", value, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidIn(List<String> values) {
            addCriterion("requestWarehouseId in", values, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotIn(List<String> values) {
            addCriterion("requestWarehouseId not in", values, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidBetween(String value1, String value2) {
            addCriterion("requestWarehouseId between", value1, value2, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andRequestwarehouseidNotBetween(String value1, String value2) {
            addCriterion("requestWarehouseId not between", value1, value2, "requestwarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNull() {
            addCriterion("purchaseType is null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIsNotNull() {
            addCriterion("purchaseType is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeEqualTo(String value) {
            addCriterion("purchaseType =", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotEqualTo(String value) {
            addCriterion("purchaseType <>", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThan(String value) {
            addCriterion("purchaseType >", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseType >=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThan(String value) {
            addCriterion("purchaseType <", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLessThanOrEqualTo(String value) {
            addCriterion("purchaseType <=", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeLike(String value) {
            addCriterion("purchaseType like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotLike(String value) {
            addCriterion("purchaseType not like", value, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeIn(List<String> values) {
            addCriterion("purchaseType in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotIn(List<String> values) {
            addCriterion("purchaseType not in", values, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeBetween(String value1, String value2) {
            addCriterion("purchaseType between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andPurchasetypeNotBetween(String value1, String value2) {
            addCriterion("purchaseType not between", value1, value2, "purchasetype");
            return (Criteria) this;
        }

        public Criteria andOrderdateIsNull() {
            addCriterion("orderDate is null");
            return (Criteria) this;
        }

        public Criteria andOrderdateIsNotNull() {
            addCriterion("orderDate is not null");
            return (Criteria) this;
        }

        public Criteria andOrderdateEqualTo(Date value) {
            addCriterionForJDBCDate("orderDate =", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("orderDate <>", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateGreaterThan(Date value) {
            addCriterionForJDBCDate("orderDate >", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("orderDate >=", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateLessThan(Date value) {
            addCriterionForJDBCDate("orderDate <", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("orderDate <=", value, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateIn(List<Date> values) {
            addCriterionForJDBCDate("orderDate in", values, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("orderDate not in", values, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("orderDate between", value1, value2, "orderdate");
            return (Criteria) this;
        }

        public Criteria andOrderdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("orderDate not between", value1, value2, "orderdate");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierId is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierId is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierId =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierId <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierId >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierId >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierId <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierId <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierId like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierId not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierId in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierId not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierId between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierId not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIsNull() {
            addCriterion("purchaseWarehouseId is null");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIsNotNull() {
            addCriterion("purchaseWarehouseId is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidEqualTo(String value) {
            addCriterion("purchaseWarehouseId =", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotEqualTo(String value) {
            addCriterion("purchaseWarehouseId <>", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidGreaterThan(String value) {
            addCriterion("purchaseWarehouseId >", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("purchaseWarehouseId >=", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLessThan(String value) {
            addCriterion("purchaseWarehouseId <", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLessThanOrEqualTo(String value) {
            addCriterion("purchaseWarehouseId <=", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidLike(String value) {
            addCriterion("purchaseWarehouseId like", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotLike(String value) {
            addCriterion("purchaseWarehouseId not like", value, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidIn(List<String> values) {
            addCriterion("purchaseWarehouseId in", values, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotIn(List<String> values) {
            addCriterion("purchaseWarehouseId not in", values, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidBetween(String value1, String value2) {
            addCriterion("purchaseWarehouseId between", value1, value2, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andPurchasewarehouseidNotBetween(String value1, String value2) {
            addCriterion("purchaseWarehouseId not between", value1, value2, "purchasewarehouseid");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNull() {
            addCriterion("transType is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("transType is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(String value) {
            addCriterion("transType =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(String value) {
            addCriterion("transType <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(String value) {
            addCriterion("transType >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(String value) {
            addCriterion("transType >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(String value) {
            addCriterion("transType <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(String value) {
            addCriterion("transType <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLike(String value) {
            addCriterion("transType like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotLike(String value) {
            addCriterion("transType not like", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<String> values) {
            addCriterion("transType in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<String> values) {
            addCriterion("transType not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(String value1, String value2) {
            addCriterion("transType between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(String value1, String value2) {
            addCriterion("transType not between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateIsNull() {
            addCriterion("hopeExportDate is null");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateIsNotNull() {
            addCriterion("hopeExportDate is not null");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate =", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate <>", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateGreaterThan(Date value) {
            addCriterionForJDBCDate("hopeExportDate >", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate >=", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateLessThan(Date value) {
            addCriterionForJDBCDate("hopeExportDate <", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hopeExportDate <=", value, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateIn(List<Date> values) {
            addCriterionForJDBCDate("hopeExportDate in", values, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hopeExportDate not in", values, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeExportDate between", value1, value2, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andHopeexportdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hopeExportDate not between", value1, value2, "hopeexportdate");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("smcCode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("smcCode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("smcCode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("smcCode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("smcCode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("smcCode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("smcCode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("smcCode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("smcCode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("smcCode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("smcCode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("smcCode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("smcCode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("smcCode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andIslotIsNull() {
            addCriterion("isLot is null");
            return (Criteria) this;
        }

        public Criteria andIslotIsNotNull() {
            addCriterion("isLot is not null");
            return (Criteria) this;
        }

        public Criteria andIslotEqualTo(Boolean value) {
            addCriterion("isLot =", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotNotEqualTo(Boolean value) {
            addCriterion("isLot <>", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotGreaterThan(Boolean value) {
            addCriterion("isLot >", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isLot >=", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotLessThan(Boolean value) {
            addCriterion("isLot <", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotLessThanOrEqualTo(Boolean value) {
            addCriterion("isLot <=", value, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotIn(List<Boolean> values) {
            addCriterion("isLot in", values, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotNotIn(List<Boolean> values) {
            addCriterion("isLot not in", values, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotBetween(Boolean value1, Boolean value2) {
            addCriterion("isLot between", value1, value2, "islot");
            return (Criteria) this;
        }

        public Criteria andIslotNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isLot not between", value1, value2, "islot");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgIsNull() {
            addCriterion("interceptMsg is null");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgIsNotNull() {
            addCriterion("interceptMsg is not null");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgEqualTo(String value) {
            addCriterion("interceptMsg =", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgNotEqualTo(String value) {
            addCriterion("interceptMsg <>", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgGreaterThan(String value) {
            addCriterion("interceptMsg >", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgGreaterThanOrEqualTo(String value) {
            addCriterion("interceptMsg >=", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgLessThan(String value) {
            addCriterion("interceptMsg <", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgLessThanOrEqualTo(String value) {
            addCriterion("interceptMsg <=", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgLike(String value) {
            addCriterion("interceptMsg like", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgNotLike(String value) {
            addCriterion("interceptMsg not like", value, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgIn(List<String> values) {
            addCriterion("interceptMsg in", values, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgNotIn(List<String> values) {
            addCriterion("interceptMsg not in", values, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgBetween(String value1, String value2) {
            addCriterion("interceptMsg between", value1, value2, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andInterceptmsgNotBetween(String value1, String value2) {
            addCriterion("interceptMsg not between", value1, value2, "interceptmsg");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNull() {
            addCriterion("netWeight is null");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNotNull() {
            addCriterion("netWeight is not null");
            return (Criteria) this;
        }

        public Criteria andNetweightEqualTo(BigDecimal value) {
            addCriterion("netWeight =", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotEqualTo(BigDecimal value) {
            addCriterion("netWeight <>", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThan(BigDecimal value) {
            addCriterion("netWeight >", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("netWeight >=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThan(BigDecimal value) {
            addCriterion("netWeight <", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("netWeight <=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightIn(List<BigDecimal> values) {
            addCriterion("netWeight in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotIn(List<BigDecimal> values) {
            addCriterion("netWeight not in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netWeight between", value1, value2, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netWeight not between", value1, value2, "netweight");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiIsNull() {
            addCriterion("notUseShikomi is null");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiIsNotNull() {
            addCriterion("notUseShikomi is not null");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiEqualTo(Boolean value) {
            addCriterion("notUseShikomi =", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiNotEqualTo(Boolean value) {
            addCriterion("notUseShikomi <>", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiGreaterThan(Boolean value) {
            addCriterion("notUseShikomi >", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiGreaterThanOrEqualTo(Boolean value) {
            addCriterion("notUseShikomi >=", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiLessThan(Boolean value) {
            addCriterion("notUseShikomi <", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiLessThanOrEqualTo(Boolean value) {
            addCriterion("notUseShikomi <=", value, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiIn(List<Boolean> values) {
            addCriterion("notUseShikomi in", values, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiNotIn(List<Boolean> values) {
            addCriterion("notUseShikomi not in", values, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiBetween(Boolean value1, Boolean value2) {
            addCriterion("notUseShikomi between", value1, value2, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andNotuseshikomiNotBetween(Boolean value1, Boolean value2) {
            addCriterion("notUseShikomi not between", value1, value2, "notuseshikomi");
            return (Criteria) this;
        }

        public Criteria andReleasereasonIsNull() {
            addCriterion("releaseReason is null");
            return (Criteria) this;
        }

        public Criteria andReleasereasonIsNotNull() {
            addCriterion("releaseReason is not null");
            return (Criteria) this;
        }

        public Criteria andReleasereasonEqualTo(String value) {
            addCriterion("releaseReason =", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonNotEqualTo(String value) {
            addCriterion("releaseReason <>", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonGreaterThan(String value) {
            addCriterion("releaseReason >", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonGreaterThanOrEqualTo(String value) {
            addCriterion("releaseReason >=", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonLessThan(String value) {
            addCriterion("releaseReason <", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonLessThanOrEqualTo(String value) {
            addCriterion("releaseReason <=", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonLike(String value) {
            addCriterion("releaseReason like", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonNotLike(String value) {
            addCriterion("releaseReason not like", value, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonIn(List<String> values) {
            addCriterion("releaseReason in", values, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonNotIn(List<String> values) {
            addCriterion("releaseReason not in", values, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonBetween(String value1, String value2) {
            addCriterion("releaseReason between", value1, value2, "releasereason");
            return (Criteria) this;
        }

        public Criteria andReleasereasonNotBetween(String value1, String value2) {
            addCriterion("releaseReason not between", value1, value2, "releasereason");
            return (Criteria) this;
        }

        public Criteria andIseditedIsNull() {
            addCriterion("isEdited is null");
            return (Criteria) this;
        }

        public Criteria andIseditedIsNotNull() {
            addCriterion("isEdited is not null");
            return (Criteria) this;
        }

        public Criteria andIseditedEqualTo(Boolean value) {
            addCriterion("isEdited =", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedNotEqualTo(Boolean value) {
            addCriterion("isEdited <>", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedGreaterThan(Boolean value) {
            addCriterion("isEdited >", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isEdited >=", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedLessThan(Boolean value) {
            addCriterion("isEdited <", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedLessThanOrEqualTo(Boolean value) {
            addCriterion("isEdited <=", value, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedIn(List<Boolean> values) {
            addCriterion("isEdited in", values, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedNotIn(List<Boolean> values) {
            addCriterion("isEdited not in", values, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedBetween(Boolean value1, Boolean value2) {
            addCriterion("isEdited between", value1, value2, "isedited");
            return (Criteria) this;
        }

        public Criteria andIseditedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isEdited not between", value1, value2, "isedited");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNull() {
            addCriterion("productType is null");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNotNull() {
            addCriterion("productType is not null");
            return (Criteria) this;
        }

        public Criteria andProducttypeEqualTo(Integer value) {
            addCriterion("productType =", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotEqualTo(Integer value) {
            addCriterion("productType <>", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThan(Integer value) {
            addCriterion("productType >", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("productType >=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThan(Integer value) {
            addCriterion("productType <", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThanOrEqualTo(Integer value) {
            addCriterion("productType <=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIn(List<Integer> values) {
            addCriterion("productType in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotIn(List<Integer> values) {
            addCriterion("productType not in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeBetween(Integer value1, Integer value2) {
            addCriterion("productType between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("productType not between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeIsNull() {
            addCriterion("warehouseType is null");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeIsNotNull() {
            addCriterion("warehouseType is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeEqualTo(String value) {
            addCriterion("warehouseType =", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeNotEqualTo(String value) {
            addCriterion("warehouseType <>", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeGreaterThan(String value) {
            addCriterion("warehouseType >", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouseType >=", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeLessThan(String value) {
            addCriterion("warehouseType <", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeLessThanOrEqualTo(String value) {
            addCriterion("warehouseType <=", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeLike(String value) {
            addCriterion("warehouseType like", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeNotLike(String value) {
            addCriterion("warehouseType not like", value, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeIn(List<String> values) {
            addCriterion("warehouseType in", values, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeNotIn(List<String> values) {
            addCriterion("warehouseType not in", values, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeBetween(String value1, String value2) {
            addCriterion("warehouseType between", value1, value2, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andWarehousetypeNotBetween(String value1, String value2) {
            addCriterion("warehouseType not between", value1, value2, "warehousetype");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeIsNull() {
            addCriterion("industryCode is null");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeIsNotNull() {
            addCriterion("industryCode is not null");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeEqualTo(String value) {
            addCriterion("industryCode =", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeNotEqualTo(String value) {
            addCriterion("industryCode <>", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeGreaterThan(String value) {
            addCriterion("industryCode >", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeGreaterThanOrEqualTo(String value) {
            addCriterion("industryCode >=", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeLessThan(String value) {
            addCriterion("industryCode <", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeLessThanOrEqualTo(String value) {
            addCriterion("industryCode <=", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeLike(String value) {
            addCriterion("industryCode like", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeNotLike(String value) {
            addCriterion("industryCode not like", value, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeIn(List<String> values) {
            addCriterion("industryCode in", values, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeNotIn(List<String> values) {
            addCriterion("industryCode not in", values, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeBetween(String value1, String value2) {
            addCriterion("industryCode between", value1, value2, "industrycode");
            return (Criteria) this;
        }

        public Criteria andIndustrycodeNotBetween(String value1, String value2) {
            addCriterion("industryCode not between", value1, value2, "industrycode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNull() {
            addCriterion("inventoryTypeCode is null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIsNotNull() {
            addCriterion("inventoryTypeCode is not null");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeEqualTo(String value) {
            addCriterion("inventoryTypeCode =", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotEqualTo(String value) {
            addCriterion("inventoryTypeCode <>", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThan(String value) {
            addCriterion("inventoryTypeCode >", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeGreaterThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode >=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThan(String value) {
            addCriterion("inventoryTypeCode <", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLessThanOrEqualTo(String value) {
            addCriterion("inventoryTypeCode <=", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeLike(String value) {
            addCriterion("inventoryTypeCode like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotLike(String value) {
            addCriterion("inventoryTypeCode not like", value, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeIn(List<String> values) {
            addCriterion("inventoryTypeCode in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotIn(List<String> values) {
            addCriterion("inventoryTypeCode not in", values, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode between", value1, value2, "inventorytypecode");
            return (Criteria) this;
        }

        public Criteria andInventorytypecodeNotBetween(String value1, String value2) {
            addCriterion("inventoryTypeCode not between", value1, value2, "inventorytypecode");
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

        public Criteria andProjectcodeIsNull() {
            addCriterion("projectCode is null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIsNotNull() {
            addCriterion("projectCode is not null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeEqualTo(String value) {
            addCriterion("projectCode =", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotEqualTo(String value) {
            addCriterion("projectCode <>", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThan(String value) {
            addCriterion("projectCode >", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThanOrEqualTo(String value) {
            addCriterion("projectCode >=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThan(String value) {
            addCriterion("projectCode <", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThanOrEqualTo(String value) {
            addCriterion("projectCode <=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLike(String value) {
            addCriterion("projectCode like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotLike(String value) {
            addCriterion("projectCode not like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIn(List<String> values) {
            addCriterion("projectCode in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotIn(List<String> values) {
            addCriterion("projectCode not in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeBetween(String value1, String value2) {
            addCriterion("projectCode between", value1, value2, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotBetween(String value1, String value2) {
            addCriterion("projectCode not between", value1, value2, "projectcode");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIsNull() {
            addCriterion("groupCustomerNo is null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIsNotNull() {
            addCriterion("groupCustomerNo is not null");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoEqualTo(String value) {
            addCriterion("groupCustomerNo =", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotEqualTo(String value) {
            addCriterion("groupCustomerNo <>", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThan(String value) {
            addCriterion("groupCustomerNo >", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo >=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThan(String value) {
            addCriterion("groupCustomerNo <", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLessThanOrEqualTo(String value) {
            addCriterion("groupCustomerNo <=", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoLike(String value) {
            addCriterion("groupCustomerNo like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotLike(String value) {
            addCriterion("groupCustomerNo not like", value, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoIn(List<String> values) {
            addCriterion("groupCustomerNo in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotIn(List<String> values) {
            addCriterion("groupCustomerNo not in", values, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoBetween(String value1, String value2) {
            addCriterion("groupCustomerNo between", value1, value2, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andGroupcustomernoNotBetween(String value1, String value2) {
            addCriterion("groupCustomerNo not between", value1, value2, "groupcustomerno");
            return (Criteria) this;
        }

        public Criteria andWmtagIsNull() {
            addCriterion("wmTag is null");
            return (Criteria) this;
        }

        public Criteria andWmtagIsNotNull() {
            addCriterion("wmTag is not null");
            return (Criteria) this;
        }

        public Criteria andWmtagEqualTo(String value) {
            addCriterion("wmTag =", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotEqualTo(String value) {
            addCriterion("wmTag <>", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagGreaterThan(String value) {
            addCriterion("wmTag >", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagGreaterThanOrEqualTo(String value) {
            addCriterion("wmTag >=", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLessThan(String value) {
            addCriterion("wmTag <", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLessThanOrEqualTo(String value) {
            addCriterion("wmTag <=", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLike(String value) {
            addCriterion("wmTag like", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotLike(String value) {
            addCriterion("wmTag not like", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagIn(List<String> values) {
            addCriterion("wmTag in", values, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotIn(List<String> values) {
            addCriterion("wmTag not in", values, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagBetween(String value1, String value2) {
            addCriterion("wmTag between", value1, value2, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotBetween(String value1, String value2) {
            addCriterion("wmTag not between", value1, value2, "wmtag");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNull() {
            addCriterion("hsCode is null");
            return (Criteria) this;
        }

        public Criteria andHscodeIsNotNull() {
            addCriterion("hsCode is not null");
            return (Criteria) this;
        }

        public Criteria andHscodeEqualTo(String value) {
            addCriterion("hsCode =", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotEqualTo(String value) {
            addCriterion("hsCode <>", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThan(String value) {
            addCriterion("hsCode >", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeGreaterThanOrEqualTo(String value) {
            addCriterion("hsCode >=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThan(String value) {
            addCriterion("hsCode <", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLessThanOrEqualTo(String value) {
            addCriterion("hsCode <=", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeLike(String value) {
            addCriterion("hsCode like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotLike(String value) {
            addCriterion("hsCode not like", value, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeIn(List<String> values) {
            addCriterion("hsCode in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotIn(List<String> values) {
            addCriterion("hsCode not in", values, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeBetween(String value1, String value2) {
            addCriterion("hsCode between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andHscodeNotBetween(String value1, String value2) {
            addCriterion("hsCode not between", value1, value2, "hscode");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNull() {
            addCriterion("supplierPartNo is null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIsNotNull() {
            addCriterion("supplierPartNo is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoEqualTo(String value) {
            addCriterion("supplierPartNo =", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotEqualTo(String value) {
            addCriterion("supplierPartNo <>", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThan(String value) {
            addCriterion("supplierPartNo >", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoGreaterThanOrEqualTo(String value) {
            addCriterion("supplierPartNo >=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThan(String value) {
            addCriterion("supplierPartNo <", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLessThanOrEqualTo(String value) {
            addCriterion("supplierPartNo <=", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoLike(String value) {
            addCriterion("supplierPartNo like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotLike(String value) {
            addCriterion("supplierPartNo not like", value, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoIn(List<String> values) {
            addCriterion("supplierPartNo in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotIn(List<String> values) {
            addCriterion("supplierPartNo not in", values, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoBetween(String value1, String value2) {
            addCriterion("supplierPartNo between", value1, value2, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andSupplierpartnoNotBetween(String value1, String value2) {
            addCriterion("supplierPartNo not between", value1, value2, "supplierpartno");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNull() {
            addCriterion("importFobPriceOriginal is null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIsNotNull() {
            addCriterion("importFobPriceOriginal is not null");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal =", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal <>", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThan(BigDecimal value) {
            addCriterion("importFobPriceOriginal >", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal >=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThan(BigDecimal value) {
            addCriterion("importFobPriceOriginal <", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("importFobPriceOriginal <=", value, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalIn(List<BigDecimal> values) {
            addCriterion("importFobPriceOriginal in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotIn(List<BigDecimal> values) {
            addCriterion("importFobPriceOriginal not in", values, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("importFobPriceOriginal between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportfobpriceoriginalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("importFobPriceOriginal not between", value1, value2, "importfobpriceoriginal");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNull() {
            addCriterion("importCurrencyId is null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIsNotNull() {
            addCriterion("importCurrencyId is not null");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidEqualTo(String value) {
            addCriterion("importCurrencyId =", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotEqualTo(String value) {
            addCriterion("importCurrencyId <>", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThan(String value) {
            addCriterion("importCurrencyId >", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidGreaterThanOrEqualTo(String value) {
            addCriterion("importCurrencyId >=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThan(String value) {
            addCriterion("importCurrencyId <", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLessThanOrEqualTo(String value) {
            addCriterion("importCurrencyId <=", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidLike(String value) {
            addCriterion("importCurrencyId like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotLike(String value) {
            addCriterion("importCurrencyId not like", value, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidIn(List<String> values) {
            addCriterion("importCurrencyId in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotIn(List<String> values) {
            addCriterion("importCurrencyId not in", values, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidBetween(String value1, String value2) {
            addCriterion("importCurrencyId between", value1, value2, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andImportcurrencyidNotBetween(String value1, String value2) {
            addCriterion("importCurrencyId not between", value1, value2, "importcurrencyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNull() {
            addCriterion("inventoryPropertyId is null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIsNotNull() {
            addCriterion("inventoryPropertyId is not null");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidEqualTo(Long value) {
            addCriterion("inventoryPropertyId =", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotEqualTo(Long value) {
            addCriterion("inventoryPropertyId <>", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThan(Long value) {
            addCriterion("inventoryPropertyId >", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidGreaterThanOrEqualTo(Long value) {
            addCriterion("inventoryPropertyId >=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThan(Long value) {
            addCriterion("inventoryPropertyId <", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidLessThanOrEqualTo(Long value) {
            addCriterion("inventoryPropertyId <=", value, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidIn(List<Long> values) {
            addCriterion("inventoryPropertyId in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotIn(List<Long> values) {
            addCriterion("inventoryPropertyId not in", values, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidBetween(Long value1, Long value2) {
            addCriterion("inventoryPropertyId between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andInventorypropertyidNotBetween(Long value1, Long value2) {
            addCriterion("inventoryPropertyId not between", value1, value2, "inventorypropertyid");
            return (Criteria) this;
        }

        public Criteria andServerremarkIsNull() {
            addCriterion("serverremark is null");
            return (Criteria) this;
        }

        public Criteria andServerremarkIsNotNull() {
            addCriterion("serverremark is not null");
            return (Criteria) this;
        }

        public Criteria andServerremarkEqualTo(String value) {
            addCriterion("serverremark =", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkNotEqualTo(String value) {
            addCriterion("serverremark <>", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkGreaterThan(String value) {
            addCriterion("serverremark >", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkGreaterThanOrEqualTo(String value) {
            addCriterion("serverremark >=", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkLessThan(String value) {
            addCriterion("serverremark <", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkLessThanOrEqualTo(String value) {
            addCriterion("serverremark <=", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkLike(String value) {
            addCriterion("serverremark like", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkNotLike(String value) {
            addCriterion("serverremark not like", value, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkIn(List<String> values) {
            addCriterion("serverremark in", values, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkNotIn(List<String> values) {
            addCriterion("serverremark not in", values, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkBetween(String value1, String value2) {
            addCriterion("serverremark between", value1, value2, "serverremark");
            return (Criteria) this;
        }

        public Criteria andServerremarkNotBetween(String value1, String value2) {
            addCriterion("serverremark not between", value1, value2, "serverremark");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryIsNull() {
            addCriterion("supplierInventory is null");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryIsNotNull() {
            addCriterion("supplierInventory is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryEqualTo(Integer value) {
            addCriterion("supplierInventory =", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryNotEqualTo(Integer value) {
            addCriterion("supplierInventory <>", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryGreaterThan(Integer value) {
            addCriterion("supplierInventory >", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplierInventory >=", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryLessThan(Integer value) {
            addCriterion("supplierInventory <", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryLessThanOrEqualTo(Integer value) {
            addCriterion("supplierInventory <=", value, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryIn(List<Integer> values) {
            addCriterion("supplierInventory in", values, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryNotIn(List<Integer> values) {
            addCriterion("supplierInventory not in", values, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryBetween(Integer value1, Integer value2) {
            addCriterion("supplierInventory between", value1, value2, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andSupplierinventoryNotBetween(Integer value1, Integer value2) {
            addCriterion("supplierInventory not between", value1, value2, "supplierinventory");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNull() {
            addCriterion("corderNO is null");
            return (Criteria) this;
        }

        public Criteria andCordernoIsNotNull() {
            addCriterion("corderNO is not null");
            return (Criteria) this;
        }

        public Criteria andCordernoEqualTo(String value) {
            addCriterion("corderNO =", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotEqualTo(String value) {
            addCriterion("corderNO <>", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThan(String value) {
            addCriterion("corderNO >", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoGreaterThanOrEqualTo(String value) {
            addCriterion("corderNO >=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThan(String value) {
            addCriterion("corderNO <", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLessThanOrEqualTo(String value) {
            addCriterion("corderNO <=", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoLike(String value) {
            addCriterion("corderNO like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotLike(String value) {
            addCriterion("corderNO not like", value, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoIn(List<String> values) {
            addCriterion("corderNO in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotIn(List<String> values) {
            addCriterion("corderNO not in", values, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoBetween(String value1, String value2) {
            addCriterion("corderNO between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andCordernoNotBetween(String value1, String value2) {
            addCriterion("corderNO not between", value1, value2, "corderno");
            return (Criteria) this;
        }

        public Criteria andIsevenIsNull() {
            addCriterion("iseven is null");
            return (Criteria) this;
        }

        public Criteria andIsevenIsNotNull() {
            addCriterion("iseven is not null");
            return (Criteria) this;
        }

        public Criteria andIsevenEqualTo(Boolean value) {
            addCriterion("iseven =", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenNotEqualTo(Boolean value) {
            addCriterion("iseven <>", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenGreaterThan(Boolean value) {
            addCriterion("iseven >", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenGreaterThanOrEqualTo(Boolean value) {
            addCriterion("iseven >=", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenLessThan(Boolean value) {
            addCriterion("iseven <", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenLessThanOrEqualTo(Boolean value) {
            addCriterion("iseven <=", value, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenIn(List<Boolean> values) {
            addCriterion("iseven in", values, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenNotIn(List<Boolean> values) {
            addCriterion("iseven not in", values, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenBetween(Boolean value1, Boolean value2) {
            addCriterion("iseven between", value1, value2, "iseven");
            return (Criteria) this;
        }

        public Criteria andIsevenNotBetween(Boolean value1, Boolean value2) {
            addCriterion("iseven not between", value1, value2, "iseven");
            return (Criteria) this;
        }

        public Criteria andMinpackunitIsNull() {
            addCriterion("minpackunit is null");
            return (Criteria) this;
        }

        public Criteria andMinpackunitIsNotNull() {
            addCriterion("minpackunit is not null");
            return (Criteria) this;
        }

        public Criteria andMinpackunitEqualTo(Integer value) {
            addCriterion("minpackunit =", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitNotEqualTo(Integer value) {
            addCriterion("minpackunit <>", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitGreaterThan(Integer value) {
            addCriterion("minpackunit >", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitGreaterThanOrEqualTo(Integer value) {
            addCriterion("minpackunit >=", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitLessThan(Integer value) {
            addCriterion("minpackunit <", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitLessThanOrEqualTo(Integer value) {
            addCriterion("minpackunit <=", value, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitIn(List<Integer> values) {
            addCriterion("minpackunit in", values, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitNotIn(List<Integer> values) {
            addCriterion("minpackunit not in", values, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitBetween(Integer value1, Integer value2) {
            addCriterion("minpackunit between", value1, value2, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andMinpackunitNotBetween(Integer value1, Integer value2) {
            addCriterion("minpackunit not between", value1, value2, "minpackunit");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIsNull() {
            addCriterion("nonstandard_sized_product is null");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIsNotNull() {
            addCriterion("nonstandard_sized_product is not null");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductEqualTo(String value) {
            addCriterion("nonstandard_sized_product =", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotEqualTo(String value) {
            addCriterion("nonstandard_sized_product <>", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductGreaterThan(String value) {
            addCriterion("nonstandard_sized_product >", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductGreaterThanOrEqualTo(String value) {
            addCriterion("nonstandard_sized_product >=", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLessThan(String value) {
            addCriterion("nonstandard_sized_product <", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLessThanOrEqualTo(String value) {
            addCriterion("nonstandard_sized_product <=", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLike(String value) {
            addCriterion("nonstandard_sized_product like", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotLike(String value) {
            addCriterion("nonstandard_sized_product not like", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIn(List<String> values) {
            addCriterion("nonstandard_sized_product in", values, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotIn(List<String> values) {
            addCriterion("nonstandard_sized_product not in", values, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductBetween(String value1, String value2) {
            addCriterion("nonstandard_sized_product between", value1, value2, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotBetween(String value1, String value2) {
            addCriterion("nonstandard_sized_product not between", value1, value2, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andQtyimportIsNull() {
            addCriterion("qtyImport is null");
            return (Criteria) this;
        }

        public Criteria andQtyimportIsNotNull() {
            addCriterion("qtyImport is not null");
            return (Criteria) this;
        }

        public Criteria andQtyimportEqualTo(Integer value) {
            addCriterion("qtyImport =", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotEqualTo(Integer value) {
            addCriterion("qtyImport <>", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportGreaterThan(Integer value) {
            addCriterion("qtyImport >", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportGreaterThanOrEqualTo(Integer value) {
            addCriterion("qtyImport >=", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportLessThan(Integer value) {
            addCriterion("qtyImport <", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportLessThanOrEqualTo(Integer value) {
            addCriterion("qtyImport <=", value, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportIn(List<Integer> values) {
            addCriterion("qtyImport in", values, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotIn(List<Integer> values) {
            addCriterion("qtyImport not in", values, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportBetween(Integer value1, Integer value2) {
            addCriterion("qtyImport between", value1, value2, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andQtyimportNotBetween(Integer value1, Integer value2) {
            addCriterion("qtyImport not between", value1, value2, "qtyimport");
            return (Criteria) this;
        }

        public Criteria andFinishtimeIsNull() {
            addCriterion("finishTime is null");
            return (Criteria) this;
        }

        public Criteria andFinishtimeIsNotNull() {
            addCriterion("finishTime is not null");
            return (Criteria) this;
        }

        public Criteria andFinishtimeEqualTo(Date value) {
            addCriterion("finishTime =", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeNotEqualTo(Date value) {
            addCriterion("finishTime <>", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeGreaterThan(Date value) {
            addCriterion("finishTime >", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finishTime >=", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeLessThan(Date value) {
            addCriterion("finishTime <", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeLessThanOrEqualTo(Date value) {
            addCriterion("finishTime <=", value, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeIn(List<Date> values) {
            addCriterion("finishTime in", values, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeNotIn(List<Date> values) {
            addCriterion("finishTime not in", values, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeBetween(Date value1, Date value2) {
            addCriterion("finishTime between", value1, value2, "finishtime");
            return (Criteria) this;
        }

        public Criteria andFinishtimeNotBetween(Date value1, Date value2) {
            addCriterion("finishTime not between", value1, value2, "finishtime");
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
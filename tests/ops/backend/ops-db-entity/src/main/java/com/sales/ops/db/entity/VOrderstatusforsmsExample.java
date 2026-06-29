package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VOrderstatusforsmsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VOrderstatusforsmsExample() {
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

        public Criteria andRorderNoIsNull() {
            addCriterion("rorder_no is null");
            return (Criteria) this;
        }

        public Criteria andRorderNoIsNotNull() {
            addCriterion("rorder_no is not null");
            return (Criteria) this;
        }

        public Criteria andRorderNoEqualTo(String value) {
            addCriterion("rorder_no =", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotEqualTo(String value) {
            addCriterion("rorder_no <>", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThan(String value) {
            addCriterion("rorder_no >", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_no >=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThan(String value) {
            addCriterion("rorder_no <", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLessThanOrEqualTo(String value) {
            addCriterion("rorder_no <=", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoLike(String value) {
            addCriterion("rorder_no like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotLike(String value) {
            addCriterion("rorder_no not like", value, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoIn(List<String> values) {
            addCriterion("rorder_no in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotIn(List<String> values) {
            addCriterion("rorder_no not in", values, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoBetween(String value1, String value2) {
            addCriterion("rorder_no between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andRorderNoNotBetween(String value1, String value2) {
            addCriterion("rorder_no not between", value1, value2, "rorderNo");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNull() {
            addCriterion("ORorderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrordernoIsNotNull() {
            addCriterion("ORorderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrordernoEqualTo(String value) {
            addCriterion("ORorderNo =", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotEqualTo(String value) {
            addCriterion("ORorderNo <>", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThan(String value) {
            addCriterion("ORorderNo >", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoGreaterThanOrEqualTo(String value) {
            addCriterion("ORorderNo >=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThan(String value) {
            addCriterion("ORorderNo <", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLessThanOrEqualTo(String value) {
            addCriterion("ORorderNo <=", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoLike(String value) {
            addCriterion("ORorderNo like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotLike(String value) {
            addCriterion("ORorderNo not like", value, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoIn(List<String> values) {
            addCriterion("ORorderNo in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotIn(List<String> values) {
            addCriterion("ORorderNo not in", values, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoBetween(String value1, String value2) {
            addCriterion("ORorderNo between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andOrordernoNotBetween(String value1, String value2) {
            addCriterion("ORorderNo not between", value1, value2, "ororderno");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIsNull() {
            addCriterion("rorder_fno is null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIsNotNull() {
            addCriterion("rorder_fno is not null");
            return (Criteria) this;
        }

        public Criteria andRorderFnoEqualTo(String value) {
            addCriterion("rorder_fno =", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotEqualTo(String value) {
            addCriterion("rorder_fno <>", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThan(String value) {
            addCriterion("rorder_fno >", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("rorder_fno >=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThan(String value) {
            addCriterion("rorder_fno <", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLessThanOrEqualTo(String value) {
            addCriterion("rorder_fno <=", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoLike(String value) {
            addCriterion("rorder_fno like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotLike(String value) {
            addCriterion("rorder_fno not like", value, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoIn(List<String> values) {
            addCriterion("rorder_fno in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotIn(List<String> values) {
            addCriterion("rorder_fno not in", values, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoBetween(String value1, String value2) {
            addCriterion("rorder_fno between", value1, value2, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderFnoNotBetween(String value1, String value2) {
            addCriterion("rorder_fno not between", value1, value2, "rorderFno");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNull() {
            addCriterion("rorder_item is null");
            return (Criteria) this;
        }

        public Criteria andRorderItemIsNotNull() {
            addCriterion("rorder_item is not null");
            return (Criteria) this;
        }

        public Criteria andRorderItemEqualTo(Integer value) {
            addCriterion("rorder_item =", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotEqualTo(Integer value) {
            addCriterion("rorder_item <>", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThan(Integer value) {
            addCriterion("rorder_item >", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("rorder_item >=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThan(Integer value) {
            addCriterion("rorder_item <", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemLessThanOrEqualTo(Integer value) {
            addCriterion("rorder_item <=", value, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemIn(List<Integer> values) {
            addCriterion("rorder_item in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotIn(List<Integer> values) {
            addCriterion("rorder_item not in", values, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item between", value1, value2, "rorderItem");
            return (Criteria) this;
        }

        public Criteria andRorderItemNotBetween(Integer value1, Integer value2) {
            addCriterion("rorder_item not between", value1, value2, "rorderItem");
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

        public Criteria andModelNoIsNull() {
            addCriterion("model_no is null");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNotNull() {
            addCriterion("model_no is not null");
            return (Criteria) this;
        }

        public Criteria andModelNoEqualTo(String value) {
            addCriterion("model_no =", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotEqualTo(String value) {
            addCriterion("model_no <>", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThan(String value) {
            addCriterion("model_no >", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("model_no >=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThan(String value) {
            addCriterion("model_no <", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThanOrEqualTo(String value) {
            addCriterion("model_no <=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLike(String value) {
            addCriterion("model_no like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotLike(String value) {
            addCriterion("model_no not like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoIn(List<String> values) {
            addCriterion("model_no in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotIn(List<String> values) {
            addCriterion("model_no not in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoBetween(String value1, String value2) {
            addCriterion("model_no between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotBetween(String value1, String value2) {
            addCriterion("model_no not between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNull() {
            addCriterion("dlv_date is null");
            return (Criteria) this;
        }

        public Criteria andDlvDateIsNotNull() {
            addCriterion("dlv_date is not null");
            return (Criteria) this;
        }

        public Criteria andDlvDateEqualTo(Date value) {
            addCriterion("dlv_date =", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotEqualTo(Date value) {
            addCriterion("dlv_date <>", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThan(Date value) {
            addCriterion("dlv_date >", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateGreaterThanOrEqualTo(Date value) {
            addCriterion("dlv_date >=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThan(Date value) {
            addCriterion("dlv_date <", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateLessThanOrEqualTo(Date value) {
            addCriterion("dlv_date <=", value, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateIn(List<Date> values) {
            addCriterion("dlv_date in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotIn(List<Date> values) {
            addCriterion("dlv_date not in", values, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateBetween(Date value1, Date value2) {
            addCriterion("dlv_date between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andDlvDateNotBetween(Date value1, Date value2) {
            addCriterion("dlv_date not between", value1, value2, "dlvDate");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNull() {
            addCriterion("exp_qty is null");
            return (Criteria) this;
        }

        public Criteria andExpQtyIsNotNull() {
            addCriterion("exp_qty is not null");
            return (Criteria) this;
        }

        public Criteria andExpQtyEqualTo(Integer value) {
            addCriterion("exp_qty =", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotEqualTo(Integer value) {
            addCriterion("exp_qty <>", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThan(Integer value) {
            addCriterion("exp_qty >", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_qty >=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThan(Integer value) {
            addCriterion("exp_qty <", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyLessThanOrEqualTo(Integer value) {
            addCriterion("exp_qty <=", value, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyIn(List<Integer> values) {
            addCriterion("exp_qty in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotIn(List<Integer> values) {
            addCriterion("exp_qty not in", values, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andExpQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_qty not between", value1, value2, "expQty");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNull() {
            addCriterion("ROrdDate is null");
            return (Criteria) this;
        }

        public Criteria andRorddateIsNotNull() {
            addCriterion("ROrdDate is not null");
            return (Criteria) this;
        }

        public Criteria andRorddateEqualTo(Date value) {
            addCriterion("ROrdDate =", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotEqualTo(Date value) {
            addCriterion("ROrdDate <>", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThan(Date value) {
            addCriterion("ROrdDate >", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateGreaterThanOrEqualTo(Date value) {
            addCriterion("ROrdDate >=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThan(Date value) {
            addCriterion("ROrdDate <", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateLessThanOrEqualTo(Date value) {
            addCriterion("ROrdDate <=", value, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateIn(List<Date> values) {
            addCriterion("ROrdDate in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotIn(List<Date> values) {
            addCriterion("ROrdDate not in", values, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateBetween(Date value1, Date value2) {
            addCriterion("ROrdDate between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andRorddateNotBetween(Date value1, Date value2) {
            addCriterion("ROrdDate not between", value1, value2, "rorddate");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("eprice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("eprice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("eprice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("eprice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("eprice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("eprice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("eprice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("eprice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("eprice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("eprice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount not between", value1, value2, "discount");
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
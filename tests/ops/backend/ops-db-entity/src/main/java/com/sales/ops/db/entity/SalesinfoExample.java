package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SalesinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SalesinfoExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeIsNull() {
            addCriterion("s_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeIsNotNull() {
            addCriterion("s_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeEqualTo(String value) {
            addCriterion("s_warehouse_code =", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeNotEqualTo(String value) {
            addCriterion("s_warehouse_code <>", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeGreaterThan(String value) {
            addCriterion("s_warehouse_code >", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("s_warehouse_code >=", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeLessThan(String value) {
            addCriterion("s_warehouse_code <", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("s_warehouse_code <=", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeLike(String value) {
            addCriterion("s_warehouse_code like", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeNotLike(String value) {
            addCriterion("s_warehouse_code not like", value, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeIn(List<String> values) {
            addCriterion("s_warehouse_code in", values, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeNotIn(List<String> values) {
            addCriterion("s_warehouse_code not in", values, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeBetween(String value1, String value2) {
            addCriterion("s_warehouse_code between", value1, value2, "sWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andSWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("s_warehouse_code not between", value1, value2, "sWarehouseCode");
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

        public Criteria andSalsesinfonoIsNull() {
            addCriterion("salsesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoIsNotNull() {
            addCriterion("salsesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoEqualTo(String value) {
            addCriterion("salsesInfoNo =", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoNotEqualTo(String value) {
            addCriterion("salsesInfoNo <>", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoGreaterThan(String value) {
            addCriterion("salsesInfoNo >", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("salsesInfoNo >=", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoLessThan(String value) {
            addCriterion("salsesInfoNo <", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoLessThanOrEqualTo(String value) {
            addCriterion("salsesInfoNo <=", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoLike(String value) {
            addCriterion("salsesInfoNo like", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoNotLike(String value) {
            addCriterion("salsesInfoNo not like", value, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoIn(List<String> values) {
            addCriterion("salsesInfoNo in", values, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoNotIn(List<String> values) {
            addCriterion("salsesInfoNo not in", values, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoBetween(String value1, String value2) {
            addCriterion("salsesInfoNo between", value1, value2, "salsesinfono");
            return (Criteria) this;
        }

        public Criteria andSalsesinfonoNotBetween(String value1, String value2) {
            addCriterion("salsesInfoNo not between", value1, value2, "salsesinfono");
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

        public Criteria andExpdateIsNull() {
            addCriterion("expdate is null");
            return (Criteria) this;
        }

        public Criteria andExpdateIsNotNull() {
            addCriterion("expdate is not null");
            return (Criteria) this;
        }

        public Criteria andExpdateEqualTo(Date value) {
            addCriterionForJDBCDate("expdate =", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expdate <>", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThan(Date value) {
            addCriterionForJDBCDate("expdate >", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expdate >=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThan(Date value) {
            addCriterionForJDBCDate("expdate <", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expdate <=", value, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateIn(List<Date> values) {
            addCriterionForJDBCDate("expdate in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expdate not in", values, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expdate between", value1, value2, "expdate");
            return (Criteria) this;
        }

        public Criteria andExpdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expdate not between", value1, value2, "expdate");
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

        public Criteria andStatecodeIsNull() {
            addCriterion("statecode is null");
            return (Criteria) this;
        }

        public Criteria andStatecodeIsNotNull() {
            addCriterion("statecode is not null");
            return (Criteria) this;
        }

        public Criteria andStatecodeEqualTo(String value) {
            addCriterion("statecode =", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotEqualTo(String value) {
            addCriterion("statecode <>", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThan(String value) {
            addCriterion("statecode >", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeGreaterThanOrEqualTo(String value) {
            addCriterion("statecode >=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThan(String value) {
            addCriterion("statecode <", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLessThanOrEqualTo(String value) {
            addCriterion("statecode <=", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeLike(String value) {
            addCriterion("statecode like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotLike(String value) {
            addCriterion("statecode not like", value, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeIn(List<String> values) {
            addCriterion("statecode in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotIn(List<String> values) {
            addCriterion("statecode not in", values, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeBetween(String value1, String value2) {
            addCriterion("statecode between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andStatecodeNotBetween(String value1, String value2) {
            addCriterion("statecode not between", value1, value2, "statecode");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNull() {
            addCriterion("inserttime is null");
            return (Criteria) this;
        }

        public Criteria andInserttimeIsNotNull() {
            addCriterion("inserttime is not null");
            return (Criteria) this;
        }

        public Criteria andInserttimeEqualTo(Date value) {
            addCriterion("inserttime =", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotEqualTo(Date value) {
            addCriterion("inserttime <>", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThan(Date value) {
            addCriterion("inserttime >", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("inserttime >=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThan(Date value) {
            addCriterion("inserttime <", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeLessThanOrEqualTo(Date value) {
            addCriterion("inserttime <=", value, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeIn(List<Date> values) {
            addCriterion("inserttime in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotIn(List<Date> values) {
            addCriterion("inserttime not in", values, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeBetween(Date value1, Date value2) {
            addCriterion("inserttime between", value1, value2, "inserttime");
            return (Criteria) this;
        }

        public Criteria andInserttimeNotBetween(Date value1, Date value2) {
            addCriterion("inserttime not between", value1, value2, "inserttime");
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
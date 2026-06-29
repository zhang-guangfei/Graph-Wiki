package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TablestructureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TablestructureExample() {
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

        public Criteria andDbnameIsNull() {
            addCriterion("DBName is null");
            return (Criteria) this;
        }

        public Criteria andDbnameIsNotNull() {
            addCriterion("DBName is not null");
            return (Criteria) this;
        }

        public Criteria andDbnameEqualTo(String value) {
            addCriterion("DBName =", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotEqualTo(String value) {
            addCriterion("DBName <>", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameGreaterThan(String value) {
            addCriterion("DBName >", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameGreaterThanOrEqualTo(String value) {
            addCriterion("DBName >=", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLessThan(String value) {
            addCriterion("DBName <", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLessThanOrEqualTo(String value) {
            addCriterion("DBName <=", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLike(String value) {
            addCriterion("DBName like", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotLike(String value) {
            addCriterion("DBName not like", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameIn(List<String> values) {
            addCriterion("DBName in", values, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotIn(List<String> values) {
            addCriterion("DBName not in", values, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameBetween(String value1, String value2) {
            addCriterion("DBName between", value1, value2, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotBetween(String value1, String value2) {
            addCriterion("DBName not between", value1, value2, "dbname");
            return (Criteria) this;
        }

        public Criteria andTblnameIsNull() {
            addCriterion("Tblname is null");
            return (Criteria) this;
        }

        public Criteria andTblnameIsNotNull() {
            addCriterion("Tblname is not null");
            return (Criteria) this;
        }

        public Criteria andTblnameEqualTo(String value) {
            addCriterion("Tblname =", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameNotEqualTo(String value) {
            addCriterion("Tblname <>", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameGreaterThan(String value) {
            addCriterion("Tblname >", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameGreaterThanOrEqualTo(String value) {
            addCriterion("Tblname >=", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameLessThan(String value) {
            addCriterion("Tblname <", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameLessThanOrEqualTo(String value) {
            addCriterion("Tblname <=", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameLike(String value) {
            addCriterion("Tblname like", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameNotLike(String value) {
            addCriterion("Tblname not like", value, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameIn(List<String> values) {
            addCriterion("Tblname in", values, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameNotIn(List<String> values) {
            addCriterion("Tblname not in", values, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameBetween(String value1, String value2) {
            addCriterion("Tblname between", value1, value2, "tblname");
            return (Criteria) this;
        }

        public Criteria andTblnameNotBetween(String value1, String value2) {
            addCriterion("Tblname not between", value1, value2, "tblname");
            return (Criteria) this;
        }

        public Criteria andColnameIsNull() {
            addCriterion("ColName is null");
            return (Criteria) this;
        }

        public Criteria andColnameIsNotNull() {
            addCriterion("ColName is not null");
            return (Criteria) this;
        }

        public Criteria andColnameEqualTo(String value) {
            addCriterion("ColName =", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameNotEqualTo(String value) {
            addCriterion("ColName <>", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameGreaterThan(String value) {
            addCriterion("ColName >", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameGreaterThanOrEqualTo(String value) {
            addCriterion("ColName >=", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameLessThan(String value) {
            addCriterion("ColName <", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameLessThanOrEqualTo(String value) {
            addCriterion("ColName <=", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameLike(String value) {
            addCriterion("ColName like", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameNotLike(String value) {
            addCriterion("ColName not like", value, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameIn(List<String> values) {
            addCriterion("ColName in", values, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameNotIn(List<String> values) {
            addCriterion("ColName not in", values, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameBetween(String value1, String value2) {
            addCriterion("ColName between", value1, value2, "colname");
            return (Criteria) this;
        }

        public Criteria andColnameNotBetween(String value1, String value2) {
            addCriterion("ColName not between", value1, value2, "colname");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("ItemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("ItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(Short value) {
            addCriterion("ItemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(Short value) {
            addCriterion("ItemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(Short value) {
            addCriterion("ItemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(Short value) {
            addCriterion("ItemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(Short value) {
            addCriterion("ItemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(Short value) {
            addCriterion("ItemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<Short> values) {
            addCriterion("ItemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<Short> values) {
            addCriterion("ItemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(Short value1, Short value2) {
            addCriterion("ItemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(Short value1, Short value2) {
            addCriterion("ItemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andBepkIsNull() {
            addCriterion("BePK is null");
            return (Criteria) this;
        }

        public Criteria andBepkIsNotNull() {
            addCriterion("BePK is not null");
            return (Criteria) this;
        }

        public Criteria andBepkEqualTo(String value) {
            addCriterion("BePK =", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkNotEqualTo(String value) {
            addCriterion("BePK <>", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkGreaterThan(String value) {
            addCriterion("BePK >", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkGreaterThanOrEqualTo(String value) {
            addCriterion("BePK >=", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkLessThan(String value) {
            addCriterion("BePK <", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkLessThanOrEqualTo(String value) {
            addCriterion("BePK <=", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkLike(String value) {
            addCriterion("BePK like", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkNotLike(String value) {
            addCriterion("BePK not like", value, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkIn(List<String> values) {
            addCriterion("BePK in", values, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkNotIn(List<String> values) {
            addCriterion("BePK not in", values, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkBetween(String value1, String value2) {
            addCriterion("BePK between", value1, value2, "bepk");
            return (Criteria) this;
        }

        public Criteria andBepkNotBetween(String value1, String value2) {
            addCriterion("BePK not between", value1, value2, "bepk");
            return (Criteria) this;
        }

        public Criteria andBeidentityIsNull() {
            addCriterion("BeIdentity is null");
            return (Criteria) this;
        }

        public Criteria andBeidentityIsNotNull() {
            addCriterion("BeIdentity is not null");
            return (Criteria) this;
        }

        public Criteria andBeidentityEqualTo(String value) {
            addCriterion("BeIdentity =", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityNotEqualTo(String value) {
            addCriterion("BeIdentity <>", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityGreaterThan(String value) {
            addCriterion("BeIdentity >", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityGreaterThanOrEqualTo(String value) {
            addCriterion("BeIdentity >=", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityLessThan(String value) {
            addCriterion("BeIdentity <", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityLessThanOrEqualTo(String value) {
            addCriterion("BeIdentity <=", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityLike(String value) {
            addCriterion("BeIdentity like", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityNotLike(String value) {
            addCriterion("BeIdentity not like", value, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityIn(List<String> values) {
            addCriterion("BeIdentity in", values, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityNotIn(List<String> values) {
            addCriterion("BeIdentity not in", values, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityBetween(String value1, String value2) {
            addCriterion("BeIdentity between", value1, value2, "beidentity");
            return (Criteria) this;
        }

        public Criteria andBeidentityNotBetween(String value1, String value2) {
            addCriterion("BeIdentity not between", value1, value2, "beidentity");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNull() {
            addCriterion("DataType is null");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNotNull() {
            addCriterion("DataType is not null");
            return (Criteria) this;
        }

        public Criteria andDatatypeEqualTo(String value) {
            addCriterion("DataType =", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotEqualTo(String value) {
            addCriterion("DataType <>", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThan(String value) {
            addCriterion("DataType >", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThanOrEqualTo(String value) {
            addCriterion("DataType >=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThan(String value) {
            addCriterion("DataType <", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThanOrEqualTo(String value) {
            addCriterion("DataType <=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLike(String value) {
            addCriterion("DataType like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotLike(String value) {
            addCriterion("DataType not like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeIn(List<String> values) {
            addCriterion("DataType in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotIn(List<String> values) {
            addCriterion("DataType not in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeBetween(String value1, String value2) {
            addCriterion("DataType between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotBetween(String value1, String value2) {
            addCriterion("DataType not between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatalengthIsNull() {
            addCriterion("DataLength is null");
            return (Criteria) this;
        }

        public Criteria andDatalengthIsNotNull() {
            addCriterion("DataLength is not null");
            return (Criteria) this;
        }

        public Criteria andDatalengthEqualTo(Short value) {
            addCriterion("DataLength =", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthNotEqualTo(Short value) {
            addCriterion("DataLength <>", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthGreaterThan(Short value) {
            addCriterion("DataLength >", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthGreaterThanOrEqualTo(Short value) {
            addCriterion("DataLength >=", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthLessThan(Short value) {
            addCriterion("DataLength <", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthLessThanOrEqualTo(Short value) {
            addCriterion("DataLength <=", value, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthIn(List<Short> values) {
            addCriterion("DataLength in", values, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthNotIn(List<Short> values) {
            addCriterion("DataLength not in", values, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthBetween(Short value1, Short value2) {
            addCriterion("DataLength between", value1, value2, "datalength");
            return (Criteria) this;
        }

        public Criteria andDatalengthNotBetween(Short value1, Short value2) {
            addCriterion("DataLength not between", value1, value2, "datalength");
            return (Criteria) this;
        }

        public Criteria andBenullIsNull() {
            addCriterion("BeNull is null");
            return (Criteria) this;
        }

        public Criteria andBenullIsNotNull() {
            addCriterion("BeNull is not null");
            return (Criteria) this;
        }

        public Criteria andBenullEqualTo(String value) {
            addCriterion("BeNull =", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullNotEqualTo(String value) {
            addCriterion("BeNull <>", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullGreaterThan(String value) {
            addCriterion("BeNull >", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullGreaterThanOrEqualTo(String value) {
            addCriterion("BeNull >=", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullLessThan(String value) {
            addCriterion("BeNull <", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullLessThanOrEqualTo(String value) {
            addCriterion("BeNull <=", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullLike(String value) {
            addCriterion("BeNull like", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullNotLike(String value) {
            addCriterion("BeNull not like", value, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullIn(List<String> values) {
            addCriterion("BeNull in", values, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullNotIn(List<String> values) {
            addCriterion("BeNull not in", values, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullBetween(String value1, String value2) {
            addCriterion("BeNull between", value1, value2, "benull");
            return (Criteria) this;
        }

        public Criteria andBenullNotBetween(String value1, String value2) {
            addCriterion("BeNull not between", value1, value2, "benull");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNull() {
            addCriterion("DefaultValue is null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNotNull() {
            addCriterion("DefaultValue is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueEqualTo(String value) {
            addCriterion("DefaultValue =", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotEqualTo(String value) {
            addCriterion("DefaultValue <>", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThan(String value) {
            addCriterion("DefaultValue >", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThanOrEqualTo(String value) {
            addCriterion("DefaultValue >=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThan(String value) {
            addCriterion("DefaultValue <", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThanOrEqualTo(String value) {
            addCriterion("DefaultValue <=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLike(String value) {
            addCriterion("DefaultValue like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotLike(String value) {
            addCriterion("DefaultValue not like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIn(List<String> values) {
            addCriterion("DefaultValue in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotIn(List<String> values) {
            addCriterion("DefaultValue not in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueBetween(String value1, String value2) {
            addCriterion("DefaultValue between", value1, value2, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotBetween(String value1, String value2) {
            addCriterion("DefaultValue not between", value1, value2, "defaultvalue");
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

        public Criteria andRemarkEqualTo(Object value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(Object value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(Object value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(Object value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(Object value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(Object value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<Object> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<Object> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(Object value1, Object value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(Object value1, Object value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNull() {
            addCriterion("UpdTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIsNotNull() {
            addCriterion("UpdTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdtimeEqualTo(Date value) {
            addCriterion("UpdTime =", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotEqualTo(Date value) {
            addCriterion("UpdTime <>", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThan(Date value) {
            addCriterion("UpdTime >", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdTime >=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThan(Date value) {
            addCriterion("UpdTime <", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdTime <=", value, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeIn(List<Date> values) {
            addCriterion("UpdTime in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotIn(List<Date> values) {
            addCriterion("UpdTime not in", values, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeBetween(Date value1, Date value2) {
            addCriterion("UpdTime between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andUpdtimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdTime not between", value1, value2, "updtime");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIsNull() {
            addCriterion("DecimalLength is null");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIsNotNull() {
            addCriterion("DecimalLength is not null");
            return (Criteria) this;
        }

        public Criteria andDecimallengthEqualTo(Integer value) {
            addCriterion("DecimalLength =", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotEqualTo(Integer value) {
            addCriterion("DecimalLength <>", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthGreaterThan(Integer value) {
            addCriterion("DecimalLength >", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("DecimalLength >=", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthLessThan(Integer value) {
            addCriterion("DecimalLength <", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthLessThanOrEqualTo(Integer value) {
            addCriterion("DecimalLength <=", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIn(List<Integer> values) {
            addCriterion("DecimalLength in", values, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotIn(List<Integer> values) {
            addCriterion("DecimalLength not in", values, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthBetween(Integer value1, Integer value2) {
            addCriterion("DecimalLength between", value1, value2, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotBetween(Integer value1, Integer value2) {
            addCriterion("DecimalLength not between", value1, value2, "decimallength");
            return (Criteria) this;
        }

        public Criteria andColdesIsNull() {
            addCriterion("ColDes is null");
            return (Criteria) this;
        }

        public Criteria andColdesIsNotNull() {
            addCriterion("ColDes is not null");
            return (Criteria) this;
        }

        public Criteria andColdesEqualTo(String value) {
            addCriterion("ColDes =", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesNotEqualTo(String value) {
            addCriterion("ColDes <>", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesGreaterThan(String value) {
            addCriterion("ColDes >", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesGreaterThanOrEqualTo(String value) {
            addCriterion("ColDes >=", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesLessThan(String value) {
            addCriterion("ColDes <", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesLessThanOrEqualTo(String value) {
            addCriterion("ColDes <=", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesLike(String value) {
            addCriterion("ColDes like", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesNotLike(String value) {
            addCriterion("ColDes not like", value, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesIn(List<String> values) {
            addCriterion("ColDes in", values, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesNotIn(List<String> values) {
            addCriterion("ColDes not in", values, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesBetween(String value1, String value2) {
            addCriterion("ColDes between", value1, value2, "coldes");
            return (Criteria) this;
        }

        public Criteria andColdesNotBetween(String value1, String value2) {
            addCriterion("ColDes not between", value1, value2, "coldes");
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
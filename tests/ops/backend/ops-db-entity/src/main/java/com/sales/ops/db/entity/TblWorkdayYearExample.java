package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TblWorkdayYearExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TblWorkdayYearExample() {
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

        public Criteria andWorkdayDateIsNull() {
            addCriterion("Workday_date is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateIsNotNull() {
            addCriterion("Workday_date is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateEqualTo(Date value) {
            addCriterionForJDBCDate("Workday_date =", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("Workday_date <>", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateGreaterThan(Date value) {
            addCriterionForJDBCDate("Workday_date >", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Workday_date >=", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateLessThan(Date value) {
            addCriterionForJDBCDate("Workday_date <", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Workday_date <=", value, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateIn(List<Date> values) {
            addCriterionForJDBCDate("Workday_date in", values, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("Workday_date not in", values, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Workday_date between", value1, value2, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andWorkdayDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Workday_date not between", value1, value2, "workdayDate");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNull() {
            addCriterion("Optdate is null");
            return (Criteria) this;
        }

        public Criteria andOptdateIsNotNull() {
            addCriterion("Optdate is not null");
            return (Criteria) this;
        }

        public Criteria andOptdateEqualTo(Date value) {
            addCriterionForJDBCDate("Optdate =", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("Optdate <>", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThan(Date value) {
            addCriterionForJDBCDate("Optdate >", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Optdate >=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThan(Date value) {
            addCriterionForJDBCDate("Optdate <", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("Optdate <=", value, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateIn(List<Date> values) {
            addCriterionForJDBCDate("Optdate in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("Optdate not in", values, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Optdate between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andOptdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("Optdate not between", value1, value2, "optdate");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("Username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("Username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("Username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("Username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("Username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("Username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("Username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("Username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("Username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("Username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("Username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("Username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("Username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("Username not between", value1, value2, "username");
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

        public Criteria andOptflagJpIsNull() {
            addCriterion("OptFlag_JP is null");
            return (Criteria) this;
        }

        public Criteria andOptflagJpIsNotNull() {
            addCriterion("OptFlag_JP is not null");
            return (Criteria) this;
        }

        public Criteria andOptflagJpEqualTo(String value) {
            addCriterion("OptFlag_JP =", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpNotEqualTo(String value) {
            addCriterion("OptFlag_JP <>", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpGreaterThan(String value) {
            addCriterion("OptFlag_JP >", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpGreaterThanOrEqualTo(String value) {
            addCriterion("OptFlag_JP >=", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpLessThan(String value) {
            addCriterion("OptFlag_JP <", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpLessThanOrEqualTo(String value) {
            addCriterion("OptFlag_JP <=", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpLike(String value) {
            addCriterion("OptFlag_JP like", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpNotLike(String value) {
            addCriterion("OptFlag_JP not like", value, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpIn(List<String> values) {
            addCriterion("OptFlag_JP in", values, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpNotIn(List<String> values) {
            addCriterion("OptFlag_JP not in", values, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpBetween(String value1, String value2) {
            addCriterion("OptFlag_JP between", value1, value2, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagJpNotBetween(String value1, String value2) {
            addCriterion("OptFlag_JP not between", value1, value2, "optflagJp");
            return (Criteria) this;
        }

        public Criteria andOptflagCnIsNull() {
            addCriterion("OptFlag_CN is null");
            return (Criteria) this;
        }

        public Criteria andOptflagCnIsNotNull() {
            addCriterion("OptFlag_CN is not null");
            return (Criteria) this;
        }

        public Criteria andOptflagCnEqualTo(String value) {
            addCriterion("OptFlag_CN =", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnNotEqualTo(String value) {
            addCriterion("OptFlag_CN <>", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnGreaterThan(String value) {
            addCriterion("OptFlag_CN >", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnGreaterThanOrEqualTo(String value) {
            addCriterion("OptFlag_CN >=", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnLessThan(String value) {
            addCriterion("OptFlag_CN <", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnLessThanOrEqualTo(String value) {
            addCriterion("OptFlag_CN <=", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnLike(String value) {
            addCriterion("OptFlag_CN like", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnNotLike(String value) {
            addCriterion("OptFlag_CN not like", value, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnIn(List<String> values) {
            addCriterion("OptFlag_CN in", values, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnNotIn(List<String> values) {
            addCriterion("OptFlag_CN not in", values, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnBetween(String value1, String value2) {
            addCriterion("OptFlag_CN between", value1, value2, "optflagCn");
            return (Criteria) this;
        }

        public Criteria andOptflagCnNotBetween(String value1, String value2) {
            addCriterion("OptFlag_CN not between", value1, value2, "optflagCn");
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
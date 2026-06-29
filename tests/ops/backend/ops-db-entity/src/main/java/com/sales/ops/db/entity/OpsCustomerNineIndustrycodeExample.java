package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsCustomerNineIndustrycodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsCustomerNineIndustrycodeExample() {
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

        public Criteria andOrdernumberIsNull() {
            addCriterion("OrderNumber is null");
            return (Criteria) this;
        }

        public Criteria andOrdernumberIsNotNull() {
            addCriterion("OrderNumber is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernumberEqualTo(Integer value) {
            addCriterion("OrderNumber =", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotEqualTo(Integer value) {
            addCriterion("OrderNumber <>", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberGreaterThan(Integer value) {
            addCriterion("OrderNumber >", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("OrderNumber >=", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberLessThan(Integer value) {
            addCriterion("OrderNumber <", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberLessThanOrEqualTo(Integer value) {
            addCriterion("OrderNumber <=", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberIn(List<Integer> values) {
            addCriterion("OrderNumber in", values, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotIn(List<Integer> values) {
            addCriterion("OrderNumber not in", values, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberBetween(Integer value1, Integer value2) {
            addCriterion("OrderNumber between", value1, value2, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotBetween(Integer value1, Integer value2) {
            addCriterion("OrderNumber not between", value1, value2, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeIsNull() {
            addCriterion("IndustryMediamCode is null");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeIsNotNull() {
            addCriterion("IndustryMediamCode is not null");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeEqualTo(String value) {
            addCriterion("IndustryMediamCode =", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeNotEqualTo(String value) {
            addCriterion("IndustryMediamCode <>", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeGreaterThan(String value) {
            addCriterion("IndustryMediamCode >", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeGreaterThanOrEqualTo(String value) {
            addCriterion("IndustryMediamCode >=", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeLessThan(String value) {
            addCriterion("IndustryMediamCode <", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeLessThanOrEqualTo(String value) {
            addCriterion("IndustryMediamCode <=", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeLike(String value) {
            addCriterion("IndustryMediamCode like", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeNotLike(String value) {
            addCriterion("IndustryMediamCode not like", value, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeIn(List<String> values) {
            addCriterion("IndustryMediamCode in", values, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeNotIn(List<String> values) {
            addCriterion("IndustryMediamCode not in", values, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeBetween(String value1, String value2) {
            addCriterion("IndustryMediamCode between", value1, value2, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustrymediamcodeNotBetween(String value1, String value2) {
            addCriterion("IndustryMediamCode not between", value1, value2, "industrymediamcode");
            return (Criteria) this;
        }

        public Criteria andIndustry4IsNull() {
            addCriterion("Industry4 is null");
            return (Criteria) this;
        }

        public Criteria andIndustry4IsNotNull() {
            addCriterion("Industry4 is not null");
            return (Criteria) this;
        }

        public Criteria andIndustry4EqualTo(String value) {
            addCriterion("Industry4 =", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4NotEqualTo(String value) {
            addCriterion("Industry4 <>", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4GreaterThan(String value) {
            addCriterion("Industry4 >", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4GreaterThanOrEqualTo(String value) {
            addCriterion("Industry4 >=", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4LessThan(String value) {
            addCriterion("Industry4 <", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4LessThanOrEqualTo(String value) {
            addCriterion("Industry4 <=", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4Like(String value) {
            addCriterion("Industry4 like", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4NotLike(String value) {
            addCriterion("Industry4 not like", value, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4In(List<String> values) {
            addCriterion("Industry4 in", values, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4NotIn(List<String> values) {
            addCriterion("Industry4 not in", values, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4Between(String value1, String value2) {
            addCriterion("Industry4 between", value1, value2, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry4NotBetween(String value1, String value2) {
            addCriterion("Industry4 not between", value1, value2, "industry4");
            return (Criteria) this;
        }

        public Criteria andIndustry56IsNull() {
            addCriterion("Industry56 is null");
            return (Criteria) this;
        }

        public Criteria andIndustry56IsNotNull() {
            addCriterion("Industry56 is not null");
            return (Criteria) this;
        }

        public Criteria andIndustry56EqualTo(String value) {
            addCriterion("Industry56 =", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56NotEqualTo(String value) {
            addCriterion("Industry56 <>", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56GreaterThan(String value) {
            addCriterion("Industry56 >", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56GreaterThanOrEqualTo(String value) {
            addCriterion("Industry56 >=", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56LessThan(String value) {
            addCriterion("Industry56 <", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56LessThanOrEqualTo(String value) {
            addCriterion("Industry56 <=", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56Like(String value) {
            addCriterion("Industry56 like", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56NotLike(String value) {
            addCriterion("Industry56 not like", value, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56In(List<String> values) {
            addCriterion("Industry56 in", values, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56NotIn(List<String> values) {
            addCriterion("Industry56 not in", values, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56Between(String value1, String value2) {
            addCriterion("Industry56 between", value1, value2, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustry56NotBetween(String value1, String value2) {
            addCriterion("Industry56 not between", value1, value2, "industry56");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentIsNull() {
            addCriterion("IndustryPercent is null");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentIsNotNull() {
            addCriterion("IndustryPercent is not null");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentEqualTo(Integer value) {
            addCriterion("IndustryPercent =", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentNotEqualTo(Integer value) {
            addCriterion("IndustryPercent <>", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentGreaterThan(Integer value) {
            addCriterion("IndustryPercent >", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentGreaterThanOrEqualTo(Integer value) {
            addCriterion("IndustryPercent >=", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentLessThan(Integer value) {
            addCriterion("IndustryPercent <", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentLessThanOrEqualTo(Integer value) {
            addCriterion("IndustryPercent <=", value, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentIn(List<Integer> values) {
            addCriterion("IndustryPercent in", values, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentNotIn(List<Integer> values) {
            addCriterion("IndustryPercent not in", values, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentBetween(Integer value1, Integer value2) {
            addCriterion("IndustryPercent between", value1, value2, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andIndustrypercentNotBetween(Integer value1, Integer value2) {
            addCriterion("IndustryPercent not between", value1, value2, "industrypercent");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNull() {
            addCriterion("CreatedUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateduserIsNotNull() {
            addCriterion("CreatedUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateduserEqualTo(String value) {
            addCriterion("CreatedUser =", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotEqualTo(String value) {
            addCriterion("CreatedUser <>", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThan(String value) {
            addCriterion("CreatedUser >", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserGreaterThanOrEqualTo(String value) {
            addCriterion("CreatedUser >=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThan(String value) {
            addCriterion("CreatedUser <", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLessThanOrEqualTo(String value) {
            addCriterion("CreatedUser <=", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserLike(String value) {
            addCriterion("CreatedUser like", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotLike(String value) {
            addCriterion("CreatedUser not like", value, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserIn(List<String> values) {
            addCriterion("CreatedUser in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotIn(List<String> values) {
            addCriterion("CreatedUser not in", values, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserBetween(String value1, String value2) {
            addCriterion("CreatedUser between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreateduserNotBetween(String value1, String value2) {
            addCriterion("CreatedUser not between", value1, value2, "createduser");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNull() {
            addCriterion("CreatedTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNotNull() {
            addCriterion("CreatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeEqualTo(Date value) {
            addCriterion("CreatedTime =", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotEqualTo(Date value) {
            addCriterion("CreatedTime <>", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThan(Date value) {
            addCriterion("CreatedTime >", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreatedTime >=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThan(Date value) {
            addCriterion("CreatedTime <", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("CreatedTime <=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIn(List<Date> values) {
            addCriterion("CreatedTime in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotIn(List<Date> values) {
            addCriterion("CreatedTime not in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeBetween(Date value1, Date value2) {
            addCriterion("CreatedTime between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("CreatedTime not between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIsNull() {
            addCriterion("UpdatedUser is null");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIsNotNull() {
            addCriterion("UpdatedUser is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateduserEqualTo(String value) {
            addCriterion("UpdatedUser =", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotEqualTo(String value) {
            addCriterion("UpdatedUser <>", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserGreaterThan(String value) {
            addCriterion("UpdatedUser >", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserGreaterThanOrEqualTo(String value) {
            addCriterion("UpdatedUser >=", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLessThan(String value) {
            addCriterion("UpdatedUser <", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLessThanOrEqualTo(String value) {
            addCriterion("UpdatedUser <=", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserLike(String value) {
            addCriterion("UpdatedUser like", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotLike(String value) {
            addCriterion("UpdatedUser not like", value, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserIn(List<String> values) {
            addCriterion("UpdatedUser in", values, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotIn(List<String> values) {
            addCriterion("UpdatedUser not in", values, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserBetween(String value1, String value2) {
            addCriterion("UpdatedUser between", value1, value2, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdateduserNotBetween(String value1, String value2) {
            addCriterion("UpdatedUser not between", value1, value2, "updateduser");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNull() {
            addCriterion("UpdatedTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNotNull() {
            addCriterion("UpdatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeEqualTo(Date value) {
            addCriterion("UpdatedTime =", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotEqualTo(Date value) {
            addCriterion("UpdatedTime <>", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThan(Date value) {
            addCriterion("UpdatedTime >", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdatedTime >=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThan(Date value) {
            addCriterion("UpdatedTime <", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdatedTime <=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIn(List<Date> values) {
            addCriterion("UpdatedTime in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotIn(List<Date> values) {
            addCriterion("UpdatedTime not in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeBetween(Date value1, Date value2) {
            addCriterion("UpdatedTime between", value1, value2, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdatedTime not between", value1, value2, "updatedtime");
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
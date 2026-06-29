package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InvViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvViewExample() {
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

        public Criteria and营业所IsNull() {
            addCriterion("营业所 is null");
            return (Criteria) this;
        }

        public Criteria and营业所IsNotNull() {
            addCriterion("营业所 is not null");
            return (Criteria) this;
        }

        public Criteria and营业所EqualTo(String value) {
            addCriterion("营业所 =", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所NotEqualTo(String value) {
            addCriterion("营业所 <>", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所GreaterThan(String value) {
            addCriterion("营业所 >", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所GreaterThanOrEqualTo(String value) {
            addCriterion("营业所 >=", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所LessThan(String value) {
            addCriterion("营业所 <", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所LessThanOrEqualTo(String value) {
            addCriterion("营业所 <=", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所Like(String value) {
            addCriterion("营业所 like", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所NotLike(String value) {
            addCriterion("营业所 not like", value, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所In(List<String> values) {
            addCriterion("营业所 in", values, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所NotIn(List<String> values) {
            addCriterion("营业所 not in", values, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所Between(String value1, String value2) {
            addCriterion("营业所 between", value1, value2, "营业所");
            return (Criteria) this;
        }

        public Criteria and营业所NotBetween(String value1, String value2) {
            addCriterion("营业所 not between", value1, value2, "营业所");
            return (Criteria) this;
        }

        public Criteria and接单号IsNull() {
            addCriterion("接单号 is null");
            return (Criteria) this;
        }

        public Criteria and接单号IsNotNull() {
            addCriterion("接单号 is not null");
            return (Criteria) this;
        }

        public Criteria and接单号EqualTo(String value) {
            addCriterion("接单号 =", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号NotEqualTo(String value) {
            addCriterion("接单号 <>", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号GreaterThan(String value) {
            addCriterion("接单号 >", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号GreaterThanOrEqualTo(String value) {
            addCriterion("接单号 >=", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号LessThan(String value) {
            addCriterion("接单号 <", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号LessThanOrEqualTo(String value) {
            addCriterion("接单号 <=", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号Like(String value) {
            addCriterion("接单号 like", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号NotLike(String value) {
            addCriterion("接单号 not like", value, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号In(List<String> values) {
            addCriterion("接单号 in", values, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号NotIn(List<String> values) {
            addCriterion("接单号 not in", values, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号Between(String value1, String value2) {
            addCriterion("接单号 between", value1, value2, "接单号");
            return (Criteria) this;
        }

        public Criteria and接单号NotBetween(String value1, String value2) {
            addCriterion("接单号 not between", value1, value2, "接单号");
            return (Criteria) this;
        }

        public Criteria and客户号IsNull() {
            addCriterion("客户号 is null");
            return (Criteria) this;
        }

        public Criteria and客户号IsNotNull() {
            addCriterion("客户号 is not null");
            return (Criteria) this;
        }

        public Criteria and客户号EqualTo(String value) {
            addCriterion("客户号 =", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号NotEqualTo(String value) {
            addCriterion("客户号 <>", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号GreaterThan(String value) {
            addCriterion("客户号 >", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号GreaterThanOrEqualTo(String value) {
            addCriterion("客户号 >=", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号LessThan(String value) {
            addCriterion("客户号 <", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号LessThanOrEqualTo(String value) {
            addCriterion("客户号 <=", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号Like(String value) {
            addCriterion("客户号 like", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号NotLike(String value) {
            addCriterion("客户号 not like", value, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号In(List<String> values) {
            addCriterion("客户号 in", values, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号NotIn(List<String> values) {
            addCriterion("客户号 not in", values, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号Between(String value1, String value2) {
            addCriterion("客户号 between", value1, value2, "客户号");
            return (Criteria) this;
        }

        public Criteria and客户号NotBetween(String value1, String value2) {
            addCriterion("客户号 not between", value1, value2, "客户号");
            return (Criteria) this;
        }

        public Criteria and用户号IsNull() {
            addCriterion("用户号 is null");
            return (Criteria) this;
        }

        public Criteria and用户号IsNotNull() {
            addCriterion("用户号 is not null");
            return (Criteria) this;
        }

        public Criteria and用户号EqualTo(String value) {
            addCriterion("用户号 =", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号NotEqualTo(String value) {
            addCriterion("用户号 <>", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号GreaterThan(String value) {
            addCriterion("用户号 >", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号GreaterThanOrEqualTo(String value) {
            addCriterion("用户号 >=", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号LessThan(String value) {
            addCriterion("用户号 <", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号LessThanOrEqualTo(String value) {
            addCriterion("用户号 <=", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号Like(String value) {
            addCriterion("用户号 like", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号NotLike(String value) {
            addCriterion("用户号 not like", value, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号In(List<String> values) {
            addCriterion("用户号 in", values, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号NotIn(List<String> values) {
            addCriterion("用户号 not in", values, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号Between(String value1, String value2) {
            addCriterion("用户号 between", value1, value2, "用户号");
            return (Criteria) this;
        }

        public Criteria and用户号NotBetween(String value1, String value2) {
            addCriterion("用户号 not between", value1, value2, "用户号");
            return (Criteria) this;
        }

        public Criteria and接单时间IsNull() {
            addCriterion("接单时间 is null");
            return (Criteria) this;
        }

        public Criteria and接单时间IsNotNull() {
            addCriterion("接单时间 is not null");
            return (Criteria) this;
        }

        public Criteria and接单时间EqualTo(Date value) {
            addCriterion("接单时间 =", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间NotEqualTo(Date value) {
            addCriterion("接单时间 <>", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间GreaterThan(Date value) {
            addCriterion("接单时间 >", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间GreaterThanOrEqualTo(Date value) {
            addCriterion("接单时间 >=", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间LessThan(Date value) {
            addCriterion("接单时间 <", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间LessThanOrEqualTo(Date value) {
            addCriterion("接单时间 <=", value, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间In(List<Date> values) {
            addCriterion("接单时间 in", values, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间NotIn(List<Date> values) {
            addCriterion("接单时间 not in", values, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间Between(Date value1, Date value2) {
            addCriterion("接单时间 between", value1, value2, "接单时间");
            return (Criteria) this;
        }

        public Criteria and接单时间NotBetween(Date value1, Date value2) {
            addCriterion("接单时间 not between", value1, value2, "接单时间");
            return (Criteria) this;
        }

        public Criteria and客户交货期IsNull() {
            addCriterion("客户交货期 is null");
            return (Criteria) this;
        }

        public Criteria and客户交货期IsNotNull() {
            addCriterion("客户交货期 is not null");
            return (Criteria) this;
        }

        public Criteria and客户交货期EqualTo(Date value) {
            addCriterion("客户交货期 =", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期NotEqualTo(Date value) {
            addCriterion("客户交货期 <>", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期GreaterThan(Date value) {
            addCriterion("客户交货期 >", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期GreaterThanOrEqualTo(Date value) {
            addCriterion("客户交货期 >=", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期LessThan(Date value) {
            addCriterion("客户交货期 <", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期LessThanOrEqualTo(Date value) {
            addCriterion("客户交货期 <=", value, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期In(List<Date> values) {
            addCriterion("客户交货期 in", values, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期NotIn(List<Date> values) {
            addCriterion("客户交货期 not in", values, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期Between(Date value1, Date value2) {
            addCriterion("客户交货期 between", value1, value2, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and客户交货期NotBetween(Date value1, Date value2) {
            addCriterion("客户交货期 not between", value1, value2, "客户交货期");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日IsNull() {
            addCriterion("指定物流发货日 is null");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日IsNotNull() {
            addCriterion("指定物流发货日 is not null");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日EqualTo(Date value) {
            addCriterionForJDBCDate("指定物流发货日 =", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日NotEqualTo(Date value) {
            addCriterionForJDBCDate("指定物流发货日 <>", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日GreaterThan(Date value) {
            addCriterionForJDBCDate("指定物流发货日 >", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日GreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("指定物流发货日 >=", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日LessThan(Date value) {
            addCriterionForJDBCDate("指定物流发货日 <", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日LessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("指定物流发货日 <=", value, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日In(List<Date> values) {
            addCriterionForJDBCDate("指定物流发货日 in", values, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日NotIn(List<Date> values) {
            addCriterionForJDBCDate("指定物流发货日 not in", values, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日Between(Date value1, Date value2) {
            addCriterionForJDBCDate("指定物流发货日 between", value1, value2, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and指定物流发货日NotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("指定物流发货日 not between", value1, value2, "指定物流发货日");
            return (Criteria) this;
        }

        public Criteria and订单类型IsNull() {
            addCriterion("订单类型 is null");
            return (Criteria) this;
        }

        public Criteria and订单类型IsNotNull() {
            addCriterion("订单类型 is not null");
            return (Criteria) this;
        }

        public Criteria and订单类型EqualTo(Short value) {
            addCriterion("订单类型 =", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型NotEqualTo(Short value) {
            addCriterion("订单类型 <>", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型GreaterThan(Short value) {
            addCriterion("订单类型 >", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型GreaterThanOrEqualTo(Short value) {
            addCriterion("订单类型 >=", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型LessThan(Short value) {
            addCriterion("订单类型 <", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型LessThanOrEqualTo(Short value) {
            addCriterion("订单类型 <=", value, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型In(List<Short> values) {
            addCriterion("订单类型 in", values, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型NotIn(List<Short> values) {
            addCriterion("订单类型 not in", values, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型Between(Short value1, Short value2) {
            addCriterion("订单类型 between", value1, value2, "订单类型");
            return (Criteria) this;
        }

        public Criteria and订单类型NotBetween(Short value1, Short value2) {
            addCriterion("订单类型 not between", value1, value2, "订单类型");
            return (Criteria) this;
        }

        public Criteria and出货方式IsNull() {
            addCriterion("出货方式 is null");
            return (Criteria) this;
        }

        public Criteria and出货方式IsNotNull() {
            addCriterion("出货方式 is not null");
            return (Criteria) this;
        }

        public Criteria and出货方式EqualTo(String value) {
            addCriterion("出货方式 =", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式NotEqualTo(String value) {
            addCriterion("出货方式 <>", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式GreaterThan(String value) {
            addCriterion("出货方式 >", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式GreaterThanOrEqualTo(String value) {
            addCriterion("出货方式 >=", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式LessThan(String value) {
            addCriterion("出货方式 <", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式LessThanOrEqualTo(String value) {
            addCriterion("出货方式 <=", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式Like(String value) {
            addCriterion("出货方式 like", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式NotLike(String value) {
            addCriterion("出货方式 not like", value, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式In(List<String> values) {
            addCriterion("出货方式 in", values, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式NotIn(List<String> values) {
            addCriterion("出货方式 not in", values, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式Between(String value1, String value2) {
            addCriterion("出货方式 between", value1, value2, "出货方式");
            return (Criteria) this;
        }

        public Criteria and出货方式NotBetween(String value1, String value2) {
            addCriterion("出货方式 not between", value1, value2, "出货方式");
            return (Criteria) this;
        }

        public Criteria and是否组装IsNull() {
            addCriterion("是否组装 is null");
            return (Criteria) this;
        }

        public Criteria and是否组装IsNotNull() {
            addCriterion("是否组装 is not null");
            return (Criteria) this;
        }

        public Criteria and是否组装EqualTo(String value) {
            addCriterion("是否组装 =", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装NotEqualTo(String value) {
            addCriterion("是否组装 <>", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装GreaterThan(String value) {
            addCriterion("是否组装 >", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装GreaterThanOrEqualTo(String value) {
            addCriterion("是否组装 >=", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装LessThan(String value) {
            addCriterion("是否组装 <", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装LessThanOrEqualTo(String value) {
            addCriterion("是否组装 <=", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装Like(String value) {
            addCriterion("是否组装 like", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装NotLike(String value) {
            addCriterion("是否组装 not like", value, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装In(List<String> values) {
            addCriterion("是否组装 in", values, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装NotIn(List<String> values) {
            addCriterion("是否组装 not in", values, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装Between(String value1, String value2) {
            addCriterion("是否组装 between", value1, value2, "是否组装");
            return (Criteria) this;
        }

        public Criteria and是否组装NotBetween(String value1, String value2) {
            addCriterion("是否组装 not between", value1, value2, "是否组装");
            return (Criteria) this;
        }

        public Criteria and特殊标识IsNull() {
            addCriterion("特殊标识 is null");
            return (Criteria) this;
        }

        public Criteria and特殊标识IsNotNull() {
            addCriterion("特殊标识 is not null");
            return (Criteria) this;
        }

        public Criteria and特殊标识EqualTo(Integer value) {
            addCriterion("特殊标识 =", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识NotEqualTo(Integer value) {
            addCriterion("特殊标识 <>", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识GreaterThan(Integer value) {
            addCriterion("特殊标识 >", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识GreaterThanOrEqualTo(Integer value) {
            addCriterion("特殊标识 >=", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识LessThan(Integer value) {
            addCriterion("特殊标识 <", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识LessThanOrEqualTo(Integer value) {
            addCriterion("特殊标识 <=", value, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识In(List<Integer> values) {
            addCriterion("特殊标识 in", values, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识NotIn(List<Integer> values) {
            addCriterion("特殊标识 not in", values, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识Between(Integer value1, Integer value2) {
            addCriterion("特殊标识 between", value1, value2, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and特殊标识NotBetween(Integer value1, Integer value2) {
            addCriterion("特殊标识 not between", value1, value2, "特殊标识");
            return (Criteria) this;
        }

        public Criteria and接单型号IsNull() {
            addCriterion("接单型号 is null");
            return (Criteria) this;
        }

        public Criteria and接单型号IsNotNull() {
            addCriterion("接单型号 is not null");
            return (Criteria) this;
        }

        public Criteria and接单型号EqualTo(String value) {
            addCriterion("接单型号 =", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号NotEqualTo(String value) {
            addCriterion("接单型号 <>", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号GreaterThan(String value) {
            addCriterion("接单型号 >", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号GreaterThanOrEqualTo(String value) {
            addCriterion("接单型号 >=", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号LessThan(String value) {
            addCriterion("接单型号 <", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号LessThanOrEqualTo(String value) {
            addCriterion("接单型号 <=", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号Like(String value) {
            addCriterion("接单型号 like", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号NotLike(String value) {
            addCriterion("接单型号 not like", value, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号In(List<String> values) {
            addCriterion("接单型号 in", values, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号NotIn(List<String> values) {
            addCriterion("接单型号 not in", values, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号Between(String value1, String value2) {
            addCriterion("接单型号 between", value1, value2, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单型号NotBetween(String value1, String value2) {
            addCriterion("接单型号 not between", value1, value2, "接单型号");
            return (Criteria) this;
        }

        public Criteria and接单数量IsNull() {
            addCriterion("接单数量 is null");
            return (Criteria) this;
        }

        public Criteria and接单数量IsNotNull() {
            addCriterion("接单数量 is not null");
            return (Criteria) this;
        }

        public Criteria and接单数量EqualTo(Integer value) {
            addCriterion("接单数量 =", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量NotEqualTo(Integer value) {
            addCriterion("接单数量 <>", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量GreaterThan(Integer value) {
            addCriterion("接单数量 >", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量GreaterThanOrEqualTo(Integer value) {
            addCriterion("接单数量 >=", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量LessThan(Integer value) {
            addCriterion("接单数量 <", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量LessThanOrEqualTo(Integer value) {
            addCriterion("接单数量 <=", value, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量In(List<Integer> values) {
            addCriterion("接单数量 in", values, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量NotIn(List<Integer> values) {
            addCriterion("接单数量 not in", values, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量Between(Integer value1, Integer value2) {
            addCriterion("接单数量 between", value1, value2, "接单数量");
            return (Criteria) this;
        }

        public Criteria and接单数量NotBetween(Integer value1, Integer value2) {
            addCriterion("接单数量 not between", value1, value2, "接单数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和IsNull() {
            addCriterion("拆分数量总和 is null");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和IsNotNull() {
            addCriterion("拆分数量总和 is not null");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和EqualTo(Integer value) {
            addCriterion("拆分数量总和 =", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和NotEqualTo(Integer value) {
            addCriterion("拆分数量总和 <>", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和GreaterThan(Integer value) {
            addCriterion("拆分数量总和 >", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和GreaterThanOrEqualTo(Integer value) {
            addCriterion("拆分数量总和 >=", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和LessThan(Integer value) {
            addCriterion("拆分数量总和 <", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和LessThanOrEqualTo(Integer value) {
            addCriterion("拆分数量总和 <=", value, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和In(List<Integer> values) {
            addCriterion("拆分数量总和 in", values, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和NotIn(List<Integer> values) {
            addCriterion("拆分数量总和 not in", values, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和Between(Integer value1, Integer value2) {
            addCriterion("拆分数量总和 between", value1, value2, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and拆分数量总和NotBetween(Integer value1, Integer value2) {
            addCriterion("拆分数量总和 not between", value1, value2, "拆分数量总和");
            return (Criteria) this;
        }

        public Criteria and分配项号IsNull() {
            addCriterion("分配项号 is null");
            return (Criteria) this;
        }

        public Criteria and分配项号IsNotNull() {
            addCriterion("分配项号 is not null");
            return (Criteria) this;
        }

        public Criteria and分配项号EqualTo(Integer value) {
            addCriterion("分配项号 =", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号NotEqualTo(Integer value) {
            addCriterion("分配项号 <>", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号GreaterThan(Integer value) {
            addCriterion("分配项号 >", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号GreaterThanOrEqualTo(Integer value) {
            addCriterion("分配项号 >=", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号LessThan(Integer value) {
            addCriterion("分配项号 <", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号LessThanOrEqualTo(Integer value) {
            addCriterion("分配项号 <=", value, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号In(List<Integer> values) {
            addCriterion("分配项号 in", values, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号NotIn(List<Integer> values) {
            addCriterion("分配项号 not in", values, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号Between(Integer value1, Integer value2) {
            addCriterion("分配项号 between", value1, value2, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配项号NotBetween(Integer value1, Integer value2) {
            addCriterion("分配项号 not between", value1, value2, "分配项号");
            return (Criteria) this;
        }

        public Criteria and分配类型IsNull() {
            addCriterion("分配类型 is null");
            return (Criteria) this;
        }

        public Criteria and分配类型IsNotNull() {
            addCriterion("分配类型 is not null");
            return (Criteria) this;
        }

        public Criteria and分配类型EqualTo(String value) {
            addCriterion("分配类型 =", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型NotEqualTo(String value) {
            addCriterion("分配类型 <>", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型GreaterThan(String value) {
            addCriterion("分配类型 >", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型GreaterThanOrEqualTo(String value) {
            addCriterion("分配类型 >=", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型LessThan(String value) {
            addCriterion("分配类型 <", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型LessThanOrEqualTo(String value) {
            addCriterion("分配类型 <=", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型Like(String value) {
            addCriterion("分配类型 like", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型NotLike(String value) {
            addCriterion("分配类型 not like", value, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型In(List<String> values) {
            addCriterion("分配类型 in", values, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型NotIn(List<String> values) {
            addCriterion("分配类型 not in", values, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型Between(String value1, String value2) {
            addCriterion("分配类型 between", value1, value2, "分配类型");
            return (Criteria) this;
        }

        public Criteria and分配类型NotBetween(String value1, String value2) {
            addCriterion("分配类型 not between", value1, value2, "分配类型");
            return (Criteria) this;
        }

        public Criteria and发货仓IsNull() {
            addCriterion("发货仓 is null");
            return (Criteria) this;
        }

        public Criteria and发货仓IsNotNull() {
            addCriterion("发货仓 is not null");
            return (Criteria) this;
        }

        public Criteria and发货仓EqualTo(String value) {
            addCriterion("发货仓 =", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓NotEqualTo(String value) {
            addCriterion("发货仓 <>", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓GreaterThan(String value) {
            addCriterion("发货仓 >", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓GreaterThanOrEqualTo(String value) {
            addCriterion("发货仓 >=", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓LessThan(String value) {
            addCriterion("发货仓 <", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓LessThanOrEqualTo(String value) {
            addCriterion("发货仓 <=", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓Like(String value) {
            addCriterion("发货仓 like", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓NotLike(String value) {
            addCriterion("发货仓 not like", value, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓In(List<String> values) {
            addCriterion("发货仓 in", values, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓NotIn(List<String> values) {
            addCriterion("发货仓 not in", values, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓Between(String value1, String value2) {
            addCriterion("发货仓 between", value1, value2, "发货仓");
            return (Criteria) this;
        }

        public Criteria and发货仓NotBetween(String value1, String value2) {
            addCriterion("发货仓 not between", value1, value2, "发货仓");
            return (Criteria) this;
        }

        public Criteria and型号IsNull() {
            addCriterion("型号 is null");
            return (Criteria) this;
        }

        public Criteria and型号IsNotNull() {
            addCriterion("型号 is not null");
            return (Criteria) this;
        }

        public Criteria and型号EqualTo(String value) {
            addCriterion("型号 =", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号NotEqualTo(String value) {
            addCriterion("型号 <>", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号GreaterThan(String value) {
            addCriterion("型号 >", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号GreaterThanOrEqualTo(String value) {
            addCriterion("型号 >=", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号LessThan(String value) {
            addCriterion("型号 <", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号LessThanOrEqualTo(String value) {
            addCriterion("型号 <=", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号Like(String value) {
            addCriterion("型号 like", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号NotLike(String value) {
            addCriterion("型号 not like", value, "型号");
            return (Criteria) this;
        }

        public Criteria and型号In(List<String> values) {
            addCriterion("型号 in", values, "型号");
            return (Criteria) this;
        }

        public Criteria and型号NotIn(List<String> values) {
            addCriterion("型号 not in", values, "型号");
            return (Criteria) this;
        }

        public Criteria and型号Between(String value1, String value2) {
            addCriterion("型号 between", value1, value2, "型号");
            return (Criteria) this;
        }

        public Criteria and型号NotBetween(String value1, String value2) {
            addCriterion("型号 not between", value1, value2, "型号");
            return (Criteria) this;
        }

        public Criteria and拆分数量IsNull() {
            addCriterion("拆分数量 is null");
            return (Criteria) this;
        }

        public Criteria and拆分数量IsNotNull() {
            addCriterion("拆分数量 is not null");
            return (Criteria) this;
        }

        public Criteria and拆分数量EqualTo(Integer value) {
            addCriterion("拆分数量 =", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量NotEqualTo(Integer value) {
            addCriterion("拆分数量 <>", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量GreaterThan(Integer value) {
            addCriterion("拆分数量 >", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量GreaterThanOrEqualTo(Integer value) {
            addCriterion("拆分数量 >=", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量LessThan(Integer value) {
            addCriterion("拆分数量 <", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量LessThanOrEqualTo(Integer value) {
            addCriterion("拆分数量 <=", value, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量In(List<Integer> values) {
            addCriterion("拆分数量 in", values, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量NotIn(List<Integer> values) {
            addCriterion("拆分数量 not in", values, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量Between(Integer value1, Integer value2) {
            addCriterion("拆分数量 between", value1, value2, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and拆分数量NotBetween(Integer value1, Integer value2) {
            addCriterion("拆分数量 not between", value1, value2, "拆分数量");
            return (Criteria) this;
        }

        public Criteria and出库区分类别IsNull() {
            addCriterion("出库区分类别 is null");
            return (Criteria) this;
        }

        public Criteria and出库区分类别IsNotNull() {
            addCriterion("出库区分类别 is not null");
            return (Criteria) this;
        }

        public Criteria and出库区分类别EqualTo(String value) {
            addCriterion("出库区分类别 =", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别NotEqualTo(String value) {
            addCriterion("出库区分类别 <>", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别GreaterThan(String value) {
            addCriterion("出库区分类别 >", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别GreaterThanOrEqualTo(String value) {
            addCriterion("出库区分类别 >=", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别LessThan(String value) {
            addCriterion("出库区分类别 <", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别LessThanOrEqualTo(String value) {
            addCriterion("出库区分类别 <=", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别Like(String value) {
            addCriterion("出库区分类别 like", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别NotLike(String value) {
            addCriterion("出库区分类别 not like", value, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别In(List<String> values) {
            addCriterion("出库区分类别 in", values, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别NotIn(List<String> values) {
            addCriterion("出库区分类别 not in", values, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别Between(String value1, String value2) {
            addCriterion("出库区分类别 between", value1, value2, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分类别NotBetween(String value1, String value2) {
            addCriterion("出库区分类别 not between", value1, value2, "出库区分类别");
            return (Criteria) this;
        }

        public Criteria and出库区分代码IsNull() {
            addCriterion("出库区分代码 is null");
            return (Criteria) this;
        }

        public Criteria and出库区分代码IsNotNull() {
            addCriterion("出库区分代码 is not null");
            return (Criteria) this;
        }

        public Criteria and出库区分代码EqualTo(String value) {
            addCriterion("出库区分代码 =", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码NotEqualTo(String value) {
            addCriterion("出库区分代码 <>", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码GreaterThan(String value) {
            addCriterion("出库区分代码 >", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码GreaterThanOrEqualTo(String value) {
            addCriterion("出库区分代码 >=", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码LessThan(String value) {
            addCriterion("出库区分代码 <", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码LessThanOrEqualTo(String value) {
            addCriterion("出库区分代码 <=", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码Like(String value) {
            addCriterion("出库区分代码 like", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码NotLike(String value) {
            addCriterion("出库区分代码 not like", value, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码In(List<String> values) {
            addCriterion("出库区分代码 in", values, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码NotIn(List<String> values) {
            addCriterion("出库区分代码 not in", values, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码Between(String value1, String value2) {
            addCriterion("出库区分代码 between", value1, value2, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分代码NotBetween(String value1, String value2) {
            addCriterion("出库区分代码 not between", value1, value2, "出库区分代码");
            return (Criteria) this;
        }

        public Criteria and出库区分分类IsNull() {
            addCriterion("出库区分分类 is null");
            return (Criteria) this;
        }

        public Criteria and出库区分分类IsNotNull() {
            addCriterion("出库区分分类 is not null");
            return (Criteria) this;
        }

        public Criteria and出库区分分类EqualTo(String value) {
            addCriterion("出库区分分类 =", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类NotEqualTo(String value) {
            addCriterion("出库区分分类 <>", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类GreaterThan(String value) {
            addCriterion("出库区分分类 >", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类GreaterThanOrEqualTo(String value) {
            addCriterion("出库区分分类 >=", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类LessThan(String value) {
            addCriterion("出库区分分类 <", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类LessThanOrEqualTo(String value) {
            addCriterion("出库区分分类 <=", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类Like(String value) {
            addCriterion("出库区分分类 like", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类NotLike(String value) {
            addCriterion("出库区分分类 not like", value, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类In(List<String> values) {
            addCriterion("出库区分分类 in", values, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类NotIn(List<String> values) {
            addCriterion("出库区分分类 not in", values, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类Between(String value1, String value2) {
            addCriterion("出库区分分类 between", value1, value2, "出库区分分类");
            return (Criteria) this;
        }

        public Criteria and出库区分分类NotBetween(String value1, String value2) {
            addCriterion("出库区分分类 not between", value1, value2, "出库区分分类");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTypeCostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductTypeCostExample() {
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

        public Criteria andMinquantityIsNull() {
            addCriterion("MinQuantity is null");
            return (Criteria) this;
        }

        public Criteria andMinquantityIsNotNull() {
            addCriterion("MinQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andMinquantityEqualTo(Integer value) {
            addCriterion("MinQuantity =", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotEqualTo(Integer value) {
            addCriterion("MinQuantity <>", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityGreaterThan(Integer value) {
            addCriterion("MinQuantity >", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("MinQuantity >=", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityLessThan(Integer value) {
            addCriterion("MinQuantity <", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityLessThanOrEqualTo(Integer value) {
            addCriterion("MinQuantity <=", value, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityIn(List<Integer> values) {
            addCriterion("MinQuantity in", values, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotIn(List<Integer> values) {
            addCriterion("MinQuantity not in", values, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityBetween(Integer value1, Integer value2) {
            addCriterion("MinQuantity between", value1, value2, "minquantity");
            return (Criteria) this;
        }

        public Criteria andMinquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("MinQuantity not between", value1, value2, "minquantity");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNull() {
            addCriterion("EPrice is null");
            return (Criteria) this;
        }

        public Criteria andEpriceIsNotNull() {
            addCriterion("EPrice is not null");
            return (Criteria) this;
        }

        public Criteria andEpriceEqualTo(BigDecimal value) {
            addCriterion("EPrice =", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotEqualTo(BigDecimal value) {
            addCriterion("EPrice <>", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThan(BigDecimal value) {
            addCriterion("EPrice >", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice >=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThan(BigDecimal value) {
            addCriterion("EPrice <", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EPrice <=", value, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceIn(List<BigDecimal> values) {
            addCriterion("EPrice in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotIn(List<BigDecimal> values) {
            addCriterion("EPrice not in", values, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andEpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EPrice not between", value1, value2, "eprice");
            return (Criteria) this;
        }

        public Criteria andCostpricerIsNull() {
            addCriterion("CostPriceR is null");
            return (Criteria) this;
        }

        public Criteria andCostpricerIsNotNull() {
            addCriterion("CostPriceR is not null");
            return (Criteria) this;
        }

        public Criteria andCostpricerEqualTo(BigDecimal value) {
            addCriterion("CostPriceR =", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerNotEqualTo(BigDecimal value) {
            addCriterion("CostPriceR <>", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerGreaterThan(BigDecimal value) {
            addCriterion("CostPriceR >", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CostPriceR >=", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerLessThan(BigDecimal value) {
            addCriterion("CostPriceR <", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CostPriceR <=", value, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerIn(List<BigDecimal> values) {
            addCriterion("CostPriceR in", values, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerNotIn(List<BigDecimal> values) {
            addCriterion("CostPriceR not in", values, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CostPriceR between", value1, value2, "costpricer");
            return (Criteria) this;
        }

        public Criteria andCostpricerNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CostPriceR not between", value1, value2, "costpricer");
            return (Criteria) this;
        }

        public Criteria andRerateIsNull() {
            addCriterion("RERate is null");
            return (Criteria) this;
        }

        public Criteria andRerateIsNotNull() {
            addCriterion("RERate is not null");
            return (Criteria) this;
        }

        public Criteria andRerateEqualTo(BigDecimal value) {
            addCriterion("RERate =", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateNotEqualTo(BigDecimal value) {
            addCriterion("RERate <>", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateGreaterThan(BigDecimal value) {
            addCriterion("RERate >", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RERate >=", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateLessThan(BigDecimal value) {
            addCriterion("RERate <", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RERate <=", value, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateIn(List<BigDecimal> values) {
            addCriterion("RERate in", values, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateNotIn(List<BigDecimal> values) {
            addCriterion("RERate not in", values, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RERate between", value1, value2, "rerate");
            return (Criteria) this;
        }

        public Criteria andRerateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RERate not between", value1, value2, "rerate");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidIsNull() {
            addCriterion("SourceTypeId is null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidIsNotNull() {
            addCriterion("SourceTypeId is not null");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidEqualTo(Integer value) {
            addCriterion("SourceTypeId =", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidNotEqualTo(Integer value) {
            addCriterion("SourceTypeId <>", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidGreaterThan(Integer value) {
            addCriterion("SourceTypeId >", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("SourceTypeId >=", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidLessThan(Integer value) {
            addCriterion("SourceTypeId <", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidLessThanOrEqualTo(Integer value) {
            addCriterion("SourceTypeId <=", value, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidIn(List<Integer> values) {
            addCriterion("SourceTypeId in", values, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidNotIn(List<Integer> values) {
            addCriterion("SourceTypeId not in", values, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidBetween(Integer value1, Integer value2) {
            addCriterion("SourceTypeId between", value1, value2, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andSourcetypeidNotBetween(Integer value1, Integer value2) {
            addCriterion("SourceTypeId not between", value1, value2, "sourcetypeid");
            return (Criteria) this;
        }

        public Criteria andConfirmcostIsNull() {
            addCriterion("ConfirmCost is null");
            return (Criteria) this;
        }

        public Criteria andConfirmcostIsNotNull() {
            addCriterion("ConfirmCost is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmcostEqualTo(BigDecimal value) {
            addCriterion("ConfirmCost =", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostNotEqualTo(BigDecimal value) {
            addCriterion("ConfirmCost <>", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostGreaterThan(BigDecimal value) {
            addCriterion("ConfirmCost >", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ConfirmCost >=", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostLessThan(BigDecimal value) {
            addCriterion("ConfirmCost <", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ConfirmCost <=", value, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostIn(List<BigDecimal> values) {
            addCriterion("ConfirmCost in", values, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostNotIn(List<BigDecimal> values) {
            addCriterion("ConfirmCost not in", values, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ConfirmCost between", value1, value2, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andConfirmcostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ConfirmCost not between", value1, value2, "confirmcost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostIsNull() {
            addCriterion("CalculateCost is null");
            return (Criteria) this;
        }

        public Criteria andCalculatecostIsNotNull() {
            addCriterion("CalculateCost is not null");
            return (Criteria) this;
        }

        public Criteria andCalculatecostEqualTo(BigDecimal value) {
            addCriterion("CalculateCost =", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostNotEqualTo(BigDecimal value) {
            addCriterion("CalculateCost <>", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostGreaterThan(BigDecimal value) {
            addCriterion("CalculateCost >", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CalculateCost >=", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostLessThan(BigDecimal value) {
            addCriterion("CalculateCost <", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CalculateCost <=", value, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostIn(List<BigDecimal> values) {
            addCriterion("CalculateCost in", values, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostNotIn(List<BigDecimal> values) {
            addCriterion("CalculateCost not in", values, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CalculateCost between", value1, value2, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andCalculatecostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CalculateCost not between", value1, value2, "calculatecost");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
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
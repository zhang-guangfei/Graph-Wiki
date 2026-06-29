package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GroupcompanypriceruleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GroupcompanypriceruleExample() {
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

        public Criteria andSellergroupidIsNull() {
            addCriterion("SellerGroupId is null");
            return (Criteria) this;
        }

        public Criteria andSellergroupidIsNotNull() {
            addCriterion("SellerGroupId is not null");
            return (Criteria) this;
        }

        public Criteria andSellergroupidEqualTo(String value) {
            addCriterion("SellerGroupId =", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidNotEqualTo(String value) {
            addCriterion("SellerGroupId <>", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidGreaterThan(String value) {
            addCriterion("SellerGroupId >", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidGreaterThanOrEqualTo(String value) {
            addCriterion("SellerGroupId >=", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidLessThan(String value) {
            addCriterion("SellerGroupId <", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidLessThanOrEqualTo(String value) {
            addCriterion("SellerGroupId <=", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidLike(String value) {
            addCriterion("SellerGroupId like", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidNotLike(String value) {
            addCriterion("SellerGroupId not like", value, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidIn(List<String> values) {
            addCriterion("SellerGroupId in", values, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidNotIn(List<String> values) {
            addCriterion("SellerGroupId not in", values, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidBetween(String value1, String value2) {
            addCriterion("SellerGroupId between", value1, value2, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andSellergroupidNotBetween(String value1, String value2) {
            addCriterion("SellerGroupId not between", value1, value2, "sellergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidIsNull() {
            addCriterion("BuyerGroupId is null");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidIsNotNull() {
            addCriterion("BuyerGroupId is not null");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidEqualTo(String value) {
            addCriterion("BuyerGroupId =", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidNotEqualTo(String value) {
            addCriterion("BuyerGroupId <>", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidGreaterThan(String value) {
            addCriterion("BuyerGroupId >", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidGreaterThanOrEqualTo(String value) {
            addCriterion("BuyerGroupId >=", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidLessThan(String value) {
            addCriterion("BuyerGroupId <", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidLessThanOrEqualTo(String value) {
            addCriterion("BuyerGroupId <=", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidLike(String value) {
            addCriterion("BuyerGroupId like", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidNotLike(String value) {
            addCriterion("BuyerGroupId not like", value, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidIn(List<String> values) {
            addCriterion("BuyerGroupId in", values, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidNotIn(List<String> values) {
            addCriterion("BuyerGroupId not in", values, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidBetween(String value1, String value2) {
            addCriterion("BuyerGroupId between", value1, value2, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andBuyergroupidNotBetween(String value1, String value2) {
            addCriterion("BuyerGroupId not between", value1, value2, "buyergroupid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidIsNull() {
            addCriterion("ProductSourceTypeId is null");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidIsNotNull() {
            addCriterion("ProductSourceTypeId is not null");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidEqualTo(String value) {
            addCriterion("ProductSourceTypeId =", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidNotEqualTo(String value) {
            addCriterion("ProductSourceTypeId <>", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidGreaterThan(String value) {
            addCriterion("ProductSourceTypeId >", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidGreaterThanOrEqualTo(String value) {
            addCriterion("ProductSourceTypeId >=", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidLessThan(String value) {
            addCriterion("ProductSourceTypeId <", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidLessThanOrEqualTo(String value) {
            addCriterion("ProductSourceTypeId <=", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidLike(String value) {
            addCriterion("ProductSourceTypeId like", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidNotLike(String value) {
            addCriterion("ProductSourceTypeId not like", value, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidIn(List<String> values) {
            addCriterion("ProductSourceTypeId in", values, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidNotIn(List<String> values) {
            addCriterion("ProductSourceTypeId not in", values, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidBetween(String value1, String value2) {
            addCriterion("ProductSourceTypeId between", value1, value2, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andProductsourcetypeidNotBetween(String value1, String value2) {
            addCriterion("ProductSourceTypeId not between", value1, value2, "productsourcetypeid");
            return (Criteria) this;
        }

        public Criteria andErateIsNull() {
            addCriterion("ERate is null");
            return (Criteria) this;
        }

        public Criteria andErateIsNotNull() {
            addCriterion("ERate is not null");
            return (Criteria) this;
        }

        public Criteria andErateEqualTo(BigDecimal value) {
            addCriterion("ERate =", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateNotEqualTo(BigDecimal value) {
            addCriterion("ERate <>", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateGreaterThan(BigDecimal value) {
            addCriterion("ERate >", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ERate >=", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateLessThan(BigDecimal value) {
            addCriterion("ERate <", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ERate <=", value, "erate");
            return (Criteria) this;
        }

        public Criteria andErateIn(List<BigDecimal> values) {
            addCriterion("ERate in", values, "erate");
            return (Criteria) this;
        }

        public Criteria andErateNotIn(List<BigDecimal> values) {
            addCriterion("ERate not in", values, "erate");
            return (Criteria) this;
        }

        public Criteria andErateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ERate between", value1, value2, "erate");
            return (Criteria) this;
        }

        public Criteria andErateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ERate not between", value1, value2, "erate");
            return (Criteria) this;
        }

        public Criteria andRuledescIsNull() {
            addCriterion("RuleDesc is null");
            return (Criteria) this;
        }

        public Criteria andRuledescIsNotNull() {
            addCriterion("RuleDesc is not null");
            return (Criteria) this;
        }

        public Criteria andRuledescEqualTo(String value) {
            addCriterion("RuleDesc =", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescNotEqualTo(String value) {
            addCriterion("RuleDesc <>", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescGreaterThan(String value) {
            addCriterion("RuleDesc >", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescGreaterThanOrEqualTo(String value) {
            addCriterion("RuleDesc >=", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescLessThan(String value) {
            addCriterion("RuleDesc <", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescLessThanOrEqualTo(String value) {
            addCriterion("RuleDesc <=", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescLike(String value) {
            addCriterion("RuleDesc like", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescNotLike(String value) {
            addCriterion("RuleDesc not like", value, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescIn(List<String> values) {
            addCriterion("RuleDesc in", values, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescNotIn(List<String> values) {
            addCriterion("RuleDesc not in", values, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescBetween(String value1, String value2) {
            addCriterion("RuleDesc between", value1, value2, "ruledesc");
            return (Criteria) this;
        }

        public Criteria andRuledescNotBetween(String value1, String value2) {
            addCriterion("RuleDesc not between", value1, value2, "ruledesc");
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
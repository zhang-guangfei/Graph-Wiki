package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OverseaModelPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OverseaModelPriceExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
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

        public Criteria andEcodeIsNull() {
            addCriterion("Ecode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("Ecode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("Ecode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("Ecode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("Ecode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("Ecode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("Ecode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("Ecode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("Ecode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("Ecode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("Ecode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("Ecode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("Ecode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("Ecode not between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andCountryIdIsNull() {
            addCriterion("Country_id is null");
            return (Criteria) this;
        }

        public Criteria andCountryIdIsNotNull() {
            addCriterion("Country_id is not null");
            return (Criteria) this;
        }

        public Criteria andCountryIdEqualTo(String value) {
            addCriterion("Country_id =", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdNotEqualTo(String value) {
            addCriterion("Country_id <>", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdGreaterThan(String value) {
            addCriterion("Country_id >", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdGreaterThanOrEqualTo(String value) {
            addCriterion("Country_id >=", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdLessThan(String value) {
            addCriterion("Country_id <", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdLessThanOrEqualTo(String value) {
            addCriterion("Country_id <=", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdLike(String value) {
            addCriterion("Country_id like", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdNotLike(String value) {
            addCriterion("Country_id not like", value, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdIn(List<String> values) {
            addCriterion("Country_id in", values, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdNotIn(List<String> values) {
            addCriterion("Country_id not in", values, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdBetween(String value1, String value2) {
            addCriterion("Country_id between", value1, value2, "countryId");
            return (Criteria) this;
        }

        public Criteria andCountryIdNotBetween(String value1, String value2) {
            addCriterion("Country_id not between", value1, value2, "countryId");
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

        public Criteria andPriceOverseaIsNull() {
            addCriterion("Price_OverSea is null");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaIsNotNull() {
            addCriterion("Price_OverSea is not null");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaEqualTo(BigDecimal value) {
            addCriterion("Price_OverSea =", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaNotEqualTo(BigDecimal value) {
            addCriterion("Price_OverSea <>", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaGreaterThan(BigDecimal value) {
            addCriterion("Price_OverSea >", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_OverSea >=", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaLessThan(BigDecimal value) {
            addCriterion("Price_OverSea <", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_OverSea <=", value, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaIn(List<BigDecimal> values) {
            addCriterion("Price_OverSea in", values, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaNotIn(List<BigDecimal> values) {
            addCriterion("Price_OverSea not in", values, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_OverSea between", value1, value2, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andPriceOverseaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_OverSea not between", value1, value2, "priceOversea");
            return (Criteria) this;
        }

        public Criteria andRetoIsNull() {
            addCriterion("Reto is null");
            return (Criteria) this;
        }

        public Criteria andRetoIsNotNull() {
            addCriterion("Reto is not null");
            return (Criteria) this;
        }

        public Criteria andRetoEqualTo(BigDecimal value) {
            addCriterion("Reto =", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoNotEqualTo(BigDecimal value) {
            addCriterion("Reto <>", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoGreaterThan(BigDecimal value) {
            addCriterion("Reto >", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Reto >=", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoLessThan(BigDecimal value) {
            addCriterion("Reto <", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Reto <=", value, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoIn(List<BigDecimal> values) {
            addCriterion("Reto in", values, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoNotIn(List<BigDecimal> values) {
            addCriterion("Reto not in", values, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Reto between", value1, value2, "reto");
            return (Criteria) this;
        }

        public Criteria andRetoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Reto not between", value1, value2, "reto");
            return (Criteria) this;
        }

        public Criteria andCustTaxIsNull() {
            addCriterion("Cust_Tax is null");
            return (Criteria) this;
        }

        public Criteria andCustTaxIsNotNull() {
            addCriterion("Cust_Tax is not null");
            return (Criteria) this;
        }

        public Criteria andCustTaxEqualTo(BigDecimal value) {
            addCriterion("Cust_Tax =", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxNotEqualTo(BigDecimal value) {
            addCriterion("Cust_Tax <>", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxGreaterThan(BigDecimal value) {
            addCriterion("Cust_Tax >", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Cust_Tax >=", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxLessThan(BigDecimal value) {
            addCriterion("Cust_Tax <", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Cust_Tax <=", value, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxIn(List<BigDecimal> values) {
            addCriterion("Cust_Tax in", values, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxNotIn(List<BigDecimal> values) {
            addCriterion("Cust_Tax not in", values, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cust_Tax between", value1, value2, "custTax");
            return (Criteria) this;
        }

        public Criteria andCustTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cust_Tax not between", value1, value2, "custTax");
            return (Criteria) this;
        }

        public Criteria andQttTypeIsNull() {
            addCriterion("Qtt_Type is null");
            return (Criteria) this;
        }

        public Criteria andQttTypeIsNotNull() {
            addCriterion("Qtt_Type is not null");
            return (Criteria) this;
        }

        public Criteria andQttTypeEqualTo(String value) {
            addCriterion("Qtt_Type =", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeNotEqualTo(String value) {
            addCriterion("Qtt_Type <>", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeGreaterThan(String value) {
            addCriterion("Qtt_Type >", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeGreaterThanOrEqualTo(String value) {
            addCriterion("Qtt_Type >=", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeLessThan(String value) {
            addCriterion("Qtt_Type <", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeLessThanOrEqualTo(String value) {
            addCriterion("Qtt_Type <=", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeLike(String value) {
            addCriterion("Qtt_Type like", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeNotLike(String value) {
            addCriterion("Qtt_Type not like", value, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeIn(List<String> values) {
            addCriterion("Qtt_Type in", values, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeNotIn(List<String> values) {
            addCriterion("Qtt_Type not in", values, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeBetween(String value1, String value2) {
            addCriterion("Qtt_Type between", value1, value2, "qttType");
            return (Criteria) this;
        }

        public Criteria andQttTypeNotBetween(String value1, String value2) {
            addCriterion("Qtt_Type not between", value1, value2, "qttType");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("Weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("Weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("Weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("Weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("Weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("Weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("Weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("Weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNull() {
            addCriterion("Trans_Fee is null");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNotNull() {
            addCriterion("Trans_Fee is not null");
            return (Criteria) this;
        }

        public Criteria andTransFeeEqualTo(BigDecimal value) {
            addCriterion("Trans_Fee =", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotEqualTo(BigDecimal value) {
            addCriterion("Trans_Fee <>", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThan(BigDecimal value) {
            addCriterion("Trans_Fee >", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Trans_Fee >=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThan(BigDecimal value) {
            addCriterion("Trans_Fee <", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Trans_Fee <=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIn(List<BigDecimal> values) {
            addCriterion("Trans_Fee in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotIn(List<BigDecimal> values) {
            addCriterion("Trans_Fee not in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Trans_Fee between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Trans_Fee not between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNull() {
            addCriterion("Other_Fee is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIsNotNull() {
            addCriterion("Other_Fee is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeEqualTo(BigDecimal value) {
            addCriterion("Other_Fee =", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotEqualTo(BigDecimal value) {
            addCriterion("Other_Fee <>", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThan(BigDecimal value) {
            addCriterion("Other_Fee >", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Other_Fee >=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThan(BigDecimal value) {
            addCriterion("Other_Fee <", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Other_Fee <=", value, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeIn(List<BigDecimal> values) {
            addCriterion("Other_Fee in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotIn(List<BigDecimal> values) {
            addCriterion("Other_Fee not in", values, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Other_Fee between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andOtherFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Other_Fee not between", value1, value2, "otherFee");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbIsNull() {
            addCriterion("Price_StdRMB is null");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbIsNotNull() {
            addCriterion("Price_StdRMB is not null");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbEqualTo(BigDecimal value) {
            addCriterion("Price_StdRMB =", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbNotEqualTo(BigDecimal value) {
            addCriterion("Price_StdRMB <>", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbGreaterThan(BigDecimal value) {
            addCriterion("Price_StdRMB >", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_StdRMB >=", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbLessThan(BigDecimal value) {
            addCriterion("Price_StdRMB <", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Price_StdRMB <=", value, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbIn(List<BigDecimal> values) {
            addCriterion("Price_StdRMB in", values, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbNotIn(List<BigDecimal> values) {
            addCriterion("Price_StdRMB not in", values, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_StdRMB between", value1, value2, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andPriceStdrmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Price_StdRMB not between", value1, value2, "priceStdrmb");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("Upddate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("Upddate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("Upddate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("Upddate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("Upddate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("Upddate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("Upddate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("Upddate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("Upddate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("Upddate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("Upddate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("Upddate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andAppartnumberIsNull() {
            addCriterion("APPartNumber is null");
            return (Criteria) this;
        }

        public Criteria andAppartnumberIsNotNull() {
            addCriterion("APPartNumber is not null");
            return (Criteria) this;
        }

        public Criteria andAppartnumberEqualTo(String value) {
            addCriterion("APPartNumber =", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberNotEqualTo(String value) {
            addCriterion("APPartNumber <>", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberGreaterThan(String value) {
            addCriterion("APPartNumber >", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberGreaterThanOrEqualTo(String value) {
            addCriterion("APPartNumber >=", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberLessThan(String value) {
            addCriterion("APPartNumber <", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberLessThanOrEqualTo(String value) {
            addCriterion("APPartNumber <=", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberLike(String value) {
            addCriterion("APPartNumber like", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberNotLike(String value) {
            addCriterion("APPartNumber not like", value, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberIn(List<String> values) {
            addCriterion("APPartNumber in", values, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberNotIn(List<String> values) {
            addCriterion("APPartNumber not in", values, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberBetween(String value1, String value2) {
            addCriterion("APPartNumber between", value1, value2, "appartnumber");
            return (Criteria) this;
        }

        public Criteria andAppartnumberNotBetween(String value1, String value2) {
            addCriterion("APPartNumber not between", value1, value2, "appartnumber");
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
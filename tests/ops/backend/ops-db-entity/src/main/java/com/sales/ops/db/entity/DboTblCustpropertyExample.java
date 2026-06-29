package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DboTblCustpropertyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DboTblCustpropertyExample() {
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

        public Criteria andPidIsNull() {
            addCriterion("PId is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("PId is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("PId =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("PId <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("PId >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("PId >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("PId <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("PId <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("PId like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("PId not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("PId in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("PId not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("PId between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("PId not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("Code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("Code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("Code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("Code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("Code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("Code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("Code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("Code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("Code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("Code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("Code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("Code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("Code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeIsNull() {
            addCriterion("ProdCountry_Code is null");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeIsNotNull() {
            addCriterion("ProdCountry_Code is not null");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeEqualTo(String value) {
            addCriterion("ProdCountry_Code =", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeNotEqualTo(String value) {
            addCriterion("ProdCountry_Code <>", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeGreaterThan(String value) {
            addCriterion("ProdCountry_Code >", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ProdCountry_Code >=", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeLessThan(String value) {
            addCriterion("ProdCountry_Code <", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeLessThanOrEqualTo(String value) {
            addCriterion("ProdCountry_Code <=", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeLike(String value) {
            addCriterion("ProdCountry_Code like", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeNotLike(String value) {
            addCriterion("ProdCountry_Code not like", value, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeIn(List<String> values) {
            addCriterion("ProdCountry_Code in", values, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeNotIn(List<String> values) {
            addCriterion("ProdCountry_Code not in", values, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeBetween(String value1, String value2) {
            addCriterion("ProdCountry_Code between", value1, value2, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andProdcountryCodeNotBetween(String value1, String value2) {
            addCriterion("ProdCountry_Code not between", value1, value2, "prodcountryCode");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("Description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("Description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("Description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("Description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("Description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("Description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("Description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("Description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("Description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("Description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("Description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("Description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("Description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("Description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNull() {
            addCriterion("FullName is null");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNotNull() {
            addCriterion("FullName is not null");
            return (Criteria) this;
        }

        public Criteria andFullnameEqualTo(String value) {
            addCriterion("FullName =", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotEqualTo(String value) {
            addCriterion("FullName <>", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThan(String value) {
            addCriterion("FullName >", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThanOrEqualTo(String value) {
            addCriterion("FullName >=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThan(String value) {
            addCriterion("FullName <", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThanOrEqualTo(String value) {
            addCriterion("FullName <=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLike(String value) {
            addCriterion("FullName like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotLike(String value) {
            addCriterion("FullName not like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameIn(List<String> values) {
            addCriterion("FullName in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotIn(List<String> values) {
            addCriterion("FullName not in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameBetween(String value1, String value2) {
            addCriterion("FullName between", value1, value2, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotBetween(String value1, String value2) {
            addCriterion("FullName not between", value1, value2, "fullname");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("FileName is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FileName is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FileName =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FileName <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FileName >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FileName >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FileName <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FileName <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FileName like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FileName not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FileName in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FileName not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FileName between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FileName not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andPricetermIsNull() {
            addCriterion("PriceTerm is null");
            return (Criteria) this;
        }

        public Criteria andPricetermIsNotNull() {
            addCriterion("PriceTerm is not null");
            return (Criteria) this;
        }

        public Criteria andPricetermEqualTo(String value) {
            addCriterion("PriceTerm =", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermNotEqualTo(String value) {
            addCriterion("PriceTerm <>", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermGreaterThan(String value) {
            addCriterion("PriceTerm >", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermGreaterThanOrEqualTo(String value) {
            addCriterion("PriceTerm >=", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermLessThan(String value) {
            addCriterion("PriceTerm <", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermLessThanOrEqualTo(String value) {
            addCriterion("PriceTerm <=", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermLike(String value) {
            addCriterion("PriceTerm like", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermNotLike(String value) {
            addCriterion("PriceTerm not like", value, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermIn(List<String> values) {
            addCriterion("PriceTerm in", values, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermNotIn(List<String> values) {
            addCriterion("PriceTerm not in", values, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermBetween(String value1, String value2) {
            addCriterion("PriceTerm between", value1, value2, "priceterm");
            return (Criteria) this;
        }

        public Criteria andPricetermNotBetween(String value1, String value2) {
            addCriterion("PriceTerm not between", value1, value2, "priceterm");
            return (Criteria) this;
        }

        public Criteria andInvnoIsNull() {
            addCriterion("InvNO is null");
            return (Criteria) this;
        }

        public Criteria andInvnoIsNotNull() {
            addCriterion("InvNO is not null");
            return (Criteria) this;
        }

        public Criteria andInvnoEqualTo(String value) {
            addCriterion("InvNO =", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoNotEqualTo(String value) {
            addCriterion("InvNO <>", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoGreaterThan(String value) {
            addCriterion("InvNO >", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoGreaterThanOrEqualTo(String value) {
            addCriterion("InvNO >=", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoLessThan(String value) {
            addCriterion("InvNO <", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoLessThanOrEqualTo(String value) {
            addCriterion("InvNO <=", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoLike(String value) {
            addCriterion("InvNO like", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoNotLike(String value) {
            addCriterion("InvNO not like", value, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoIn(List<String> values) {
            addCriterion("InvNO in", values, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoNotIn(List<String> values) {
            addCriterion("InvNO not in", values, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoBetween(String value1, String value2) {
            addCriterion("InvNO between", value1, value2, "invno");
            return (Criteria) this;
        }

        public Criteria andInvnoNotBetween(String value1, String value2) {
            addCriterion("InvNO not between", value1, value2, "invno");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNull() {
            addCriterion("UpdDate is null");
            return (Criteria) this;
        }

        public Criteria andUpddateIsNotNull() {
            addCriterion("UpdDate is not null");
            return (Criteria) this;
        }

        public Criteria andUpddateEqualTo(Date value) {
            addCriterion("UpdDate =", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotEqualTo(Date value) {
            addCriterion("UpdDate <>", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThan(Date value) {
            addCriterion("UpdDate >", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdDate >=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThan(Date value) {
            addCriterion("UpdDate <", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateLessThanOrEqualTo(Date value) {
            addCriterion("UpdDate <=", value, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateIn(List<Date> values) {
            addCriterion("UpdDate in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotIn(List<Date> values) {
            addCriterion("UpdDate not in", values, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateBetween(Date value1, Date value2) {
            addCriterion("UpdDate between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andUpddateNotBetween(Date value1, Date value2) {
            addCriterion("UpdDate not between", value1, value2, "upddate");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81IsNull() {
            addCriterion("Company_Code_Db81 is null");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81IsNotNull() {
            addCriterion("Company_Code_Db81 is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81EqualTo(String value) {
            addCriterion("Company_Code_Db81 =", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81NotEqualTo(String value) {
            addCriterion("Company_Code_Db81 <>", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81GreaterThan(String value) {
            addCriterion("Company_Code_Db81 >", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81GreaterThanOrEqualTo(String value) {
            addCriterion("Company_Code_Db81 >=", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81LessThan(String value) {
            addCriterion("Company_Code_Db81 <", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81LessThanOrEqualTo(String value) {
            addCriterion("Company_Code_Db81 <=", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81Like(String value) {
            addCriterion("Company_Code_Db81 like", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81NotLike(String value) {
            addCriterion("Company_Code_Db81 not like", value, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81In(List<String> values) {
            addCriterion("Company_Code_Db81 in", values, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81NotIn(List<String> values) {
            addCriterion("Company_Code_Db81 not in", values, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81Between(String value1, String value2) {
            addCriterion("Company_Code_Db81 between", value1, value2, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeDb81NotBetween(String value1, String value2) {
            addCriterion("Company_Code_Db81 not between", value1, value2, "companyCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81IsNull() {
            addCriterion("CounTry_Code_Db81 is null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81IsNotNull() {
            addCriterion("CounTry_Code_Db81 is not null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81EqualTo(String value) {
            addCriterion("CounTry_Code_Db81 =", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81NotEqualTo(String value) {
            addCriterion("CounTry_Code_Db81 <>", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81GreaterThan(String value) {
            addCriterion("CounTry_Code_Db81 >", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81GreaterThanOrEqualTo(String value) {
            addCriterion("CounTry_Code_Db81 >=", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81LessThan(String value) {
            addCriterion("CounTry_Code_Db81 <", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81LessThanOrEqualTo(String value) {
            addCriterion("CounTry_Code_Db81 <=", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81Like(String value) {
            addCriterion("CounTry_Code_Db81 like", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81NotLike(String value) {
            addCriterion("CounTry_Code_Db81 not like", value, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81In(List<String> values) {
            addCriterion("CounTry_Code_Db81 in", values, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81NotIn(List<String> values) {
            addCriterion("CounTry_Code_Db81 not in", values, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81Between(String value1, String value2) {
            addCriterion("CounTry_Code_Db81 between", value1, value2, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCountryCodeDb81NotBetween(String value1, String value2) {
            addCriterion("CounTry_Code_Db81 not between", value1, value2, "countryCodeDb81");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNull() {
            addCriterion("Currency_Type is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNotNull() {
            addCriterion("Currency_Type is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeEqualTo(String value) {
            addCriterion("Currency_Type =", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotEqualTo(String value) {
            addCriterion("Currency_Type <>", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThan(String value) {
            addCriterion("Currency_Type >", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("Currency_Type >=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThan(String value) {
            addCriterion("Currency_Type <", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThanOrEqualTo(String value) {
            addCriterion("Currency_Type <=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLike(String value) {
            addCriterion("Currency_Type like", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotLike(String value) {
            addCriterion("Currency_Type not like", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIn(List<String> values) {
            addCriterion("Currency_Type in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotIn(List<String> values) {
            addCriterion("Currency_Type not in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeBetween(String value1, String value2) {
            addCriterion("Currency_Type between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotBetween(String value1, String value2) {
            addCriterion("Currency_Type not between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andFeeInitialIsNull() {
            addCriterion("Fee_Initial is null");
            return (Criteria) this;
        }

        public Criteria andFeeInitialIsNotNull() {
            addCriterion("Fee_Initial is not null");
            return (Criteria) this;
        }

        public Criteria andFeeInitialEqualTo(BigDecimal value) {
            addCriterion("Fee_Initial =", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialNotEqualTo(BigDecimal value) {
            addCriterion("Fee_Initial <>", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialGreaterThan(BigDecimal value) {
            addCriterion("Fee_Initial >", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Fee_Initial >=", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialLessThan(BigDecimal value) {
            addCriterion("Fee_Initial <", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Fee_Initial <=", value, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialIn(List<BigDecimal> values) {
            addCriterion("Fee_Initial in", values, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialNotIn(List<BigDecimal> values) {
            addCriterion("Fee_Initial not in", values, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Fee_Initial between", value1, value2, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeInitialNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Fee_Initial not between", value1, value2, "feeInitial");
            return (Criteria) this;
        }

        public Criteria andFeeAddIsNull() {
            addCriterion("Fee_Add is null");
            return (Criteria) this;
        }

        public Criteria andFeeAddIsNotNull() {
            addCriterion("Fee_Add is not null");
            return (Criteria) this;
        }

        public Criteria andFeeAddEqualTo(BigDecimal value) {
            addCriterion("Fee_Add =", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddNotEqualTo(BigDecimal value) {
            addCriterion("Fee_Add <>", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddGreaterThan(BigDecimal value) {
            addCriterion("Fee_Add >", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Fee_Add >=", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddLessThan(BigDecimal value) {
            addCriterion("Fee_Add <", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Fee_Add <=", value, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddIn(List<BigDecimal> values) {
            addCriterion("Fee_Add in", values, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddNotIn(List<BigDecimal> values) {
            addCriterion("Fee_Add not in", values, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Fee_Add between", value1, value2, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andFeeAddNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Fee_Add not between", value1, value2, "feeAdd");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIsNull() {
            addCriterion("EmailAddr is null");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIsNotNull() {
            addCriterion("EmailAddr is not null");
            return (Criteria) this;
        }

        public Criteria andEmailaddrEqualTo(String value) {
            addCriterion("EmailAddr =", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotEqualTo(String value) {
            addCriterion("EmailAddr <>", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrGreaterThan(String value) {
            addCriterion("EmailAddr >", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrGreaterThanOrEqualTo(String value) {
            addCriterion("EmailAddr >=", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLessThan(String value) {
            addCriterion("EmailAddr <", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLessThanOrEqualTo(String value) {
            addCriterion("EmailAddr <=", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrLike(String value) {
            addCriterion("EmailAddr like", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotLike(String value) {
            addCriterion("EmailAddr not like", value, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrIn(List<String> values) {
            addCriterion("EmailAddr in", values, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotIn(List<String> values) {
            addCriterion("EmailAddr not in", values, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrBetween(String value1, String value2) {
            addCriterion("EmailAddr between", value1, value2, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andEmailaddrNotBetween(String value1, String value2) {
            addCriterion("EmailAddr not between", value1, value2, "emailaddr");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxIsNull() {
            addCriterion("InvoiceNo_max is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxIsNotNull() {
            addCriterion("InvoiceNo_max is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxEqualTo(String value) {
            addCriterion("InvoiceNo_max =", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxNotEqualTo(String value) {
            addCriterion("InvoiceNo_max <>", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxGreaterThan(String value) {
            addCriterion("InvoiceNo_max >", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNo_max >=", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxLessThan(String value) {
            addCriterion("InvoiceNo_max <", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNo_max <=", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxLike(String value) {
            addCriterion("InvoiceNo_max like", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxNotLike(String value) {
            addCriterion("InvoiceNo_max not like", value, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxIn(List<String> values) {
            addCriterion("InvoiceNo_max in", values, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxNotIn(List<String> values) {
            addCriterion("InvoiceNo_max not in", values, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxBetween(String value1, String value2) {
            addCriterion("InvoiceNo_max between", value1, value2, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andInvoicenoMaxNotBetween(String value1, String value2) {
            addCriterion("InvoiceNo_max not between", value1, value2, "invoicenoMax");
            return (Criteria) this;
        }

        public Criteria andRemaekIsNull() {
            addCriterion("Remaek is null");
            return (Criteria) this;
        }

        public Criteria andRemaekIsNotNull() {
            addCriterion("Remaek is not null");
            return (Criteria) this;
        }

        public Criteria andRemaekEqualTo(String value) {
            addCriterion("Remaek =", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekNotEqualTo(String value) {
            addCriterion("Remaek <>", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekGreaterThan(String value) {
            addCriterion("Remaek >", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekGreaterThanOrEqualTo(String value) {
            addCriterion("Remaek >=", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekLessThan(String value) {
            addCriterion("Remaek <", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekLessThanOrEqualTo(String value) {
            addCriterion("Remaek <=", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekLike(String value) {
            addCriterion("Remaek like", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekNotLike(String value) {
            addCriterion("Remaek not like", value, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekIn(List<String> values) {
            addCriterion("Remaek in", values, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekNotIn(List<String> values) {
            addCriterion("Remaek not in", values, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekBetween(String value1, String value2) {
            addCriterion("Remaek between", value1, value2, "remaek");
            return (Criteria) this;
        }

        public Criteria andRemaekNotBetween(String value1, String value2) {
            addCriterion("Remaek not between", value1, value2, "remaek");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagIsNull() {
            addCriterion("Foreigncurrency_flag is null");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagIsNotNull() {
            addCriterion("Foreigncurrency_flag is not null");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagEqualTo(String value) {
            addCriterion("Foreigncurrency_flag =", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagNotEqualTo(String value) {
            addCriterion("Foreigncurrency_flag <>", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagGreaterThan(String value) {
            addCriterion("Foreigncurrency_flag >", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagGreaterThanOrEqualTo(String value) {
            addCriterion("Foreigncurrency_flag >=", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagLessThan(String value) {
            addCriterion("Foreigncurrency_flag <", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagLessThanOrEqualTo(String value) {
            addCriterion("Foreigncurrency_flag <=", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagLike(String value) {
            addCriterion("Foreigncurrency_flag like", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagNotLike(String value) {
            addCriterion("Foreigncurrency_flag not like", value, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagIn(List<String> values) {
            addCriterion("Foreigncurrency_flag in", values, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagNotIn(List<String> values) {
            addCriterion("Foreigncurrency_flag not in", values, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagBetween(String value1, String value2) {
            addCriterion("Foreigncurrency_flag between", value1, value2, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andForeigncurrencyFlagNotBetween(String value1, String value2) {
            addCriterion("Foreigncurrency_flag not between", value1, value2, "foreigncurrencyFlag");
            return (Criteria) this;
        }

        public Criteria andFinancenameIsNull() {
            addCriterion("FinanceName is null");
            return (Criteria) this;
        }

        public Criteria andFinancenameIsNotNull() {
            addCriterion("FinanceName is not null");
            return (Criteria) this;
        }

        public Criteria andFinancenameEqualTo(String value) {
            addCriterion("FinanceName =", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameNotEqualTo(String value) {
            addCriterion("FinanceName <>", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameGreaterThan(String value) {
            addCriterion("FinanceName >", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameGreaterThanOrEqualTo(String value) {
            addCriterion("FinanceName >=", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameLessThan(String value) {
            addCriterion("FinanceName <", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameLessThanOrEqualTo(String value) {
            addCriterion("FinanceName <=", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameLike(String value) {
            addCriterion("FinanceName like", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameNotLike(String value) {
            addCriterion("FinanceName not like", value, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameIn(List<String> values) {
            addCriterion("FinanceName in", values, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameNotIn(List<String> values) {
            addCriterion("FinanceName not in", values, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameBetween(String value1, String value2) {
            addCriterion("FinanceName between", value1, value2, "financename");
            return (Criteria) this;
        }

        public Criteria andFinancenameNotBetween(String value1, String value2) {
            addCriterion("FinanceName not between", value1, value2, "financename");
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
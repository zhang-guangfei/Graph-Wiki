package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductRestrictTExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductRestrictTExample() {
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

        public Criteria andIndustryCodeIsNull() {
            addCriterion("industry_code is null");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeIsNotNull() {
            addCriterion("industry_code is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeEqualTo(String value) {
            addCriterion("industry_code =", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotEqualTo(String value) {
            addCriterion("industry_code <>", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeGreaterThan(String value) {
            addCriterion("industry_code >", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("industry_code >=", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLessThan(String value) {
            addCriterion("industry_code <", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLessThanOrEqualTo(String value) {
            addCriterion("industry_code <=", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLike(String value) {
            addCriterion("industry_code like", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotLike(String value) {
            addCriterion("industry_code not like", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeIn(List<String> values) {
            addCriterion("industry_code in", values, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotIn(List<String> values) {
            addCriterion("industry_code not in", values, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeBetween(String value1, String value2) {
            addCriterion("industry_code between", value1, value2, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotBetween(String value1, String value2) {
            addCriterion("industry_code not between", value1, value2, "industryCode");
            return (Criteria) this;
        }

        public Criteria andTypeAspIsNull() {
            addCriterion("type_ASP is null");
            return (Criteria) this;
        }

        public Criteria andTypeAspIsNotNull() {
            addCriterion("type_ASP is not null");
            return (Criteria) this;
        }

        public Criteria andTypeAspEqualTo(String value) {
            addCriterion("type_ASP =", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspNotEqualTo(String value) {
            addCriterion("type_ASP <>", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspGreaterThan(String value) {
            addCriterion("type_ASP >", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspGreaterThanOrEqualTo(String value) {
            addCriterion("type_ASP >=", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspLessThan(String value) {
            addCriterion("type_ASP <", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspLessThanOrEqualTo(String value) {
            addCriterion("type_ASP <=", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspLike(String value) {
            addCriterion("type_ASP like", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspNotLike(String value) {
            addCriterion("type_ASP not like", value, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspIn(List<String> values) {
            addCriterion("type_ASP in", values, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspNotIn(List<String> values) {
            addCriterion("type_ASP not in", values, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspBetween(String value1, String value2) {
            addCriterion("type_ASP between", value1, value2, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypeAspNotBetween(String value1, String value2) {
            addCriterion("type_ASP not between", value1, value2, "typeAsp");
            return (Criteria) this;
        }

        public Criteria andTypePncIsNull() {
            addCriterion("type_PNC is null");
            return (Criteria) this;
        }

        public Criteria andTypePncIsNotNull() {
            addCriterion("type_PNC is not null");
            return (Criteria) this;
        }

        public Criteria andTypePncEqualTo(String value) {
            addCriterion("type_PNC =", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncNotEqualTo(String value) {
            addCriterion("type_PNC <>", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncGreaterThan(String value) {
            addCriterion("type_PNC >", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncGreaterThanOrEqualTo(String value) {
            addCriterion("type_PNC >=", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncLessThan(String value) {
            addCriterion("type_PNC <", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncLessThanOrEqualTo(String value) {
            addCriterion("type_PNC <=", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncLike(String value) {
            addCriterion("type_PNC like", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncNotLike(String value) {
            addCriterion("type_PNC not like", value, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncIn(List<String> values) {
            addCriterion("type_PNC in", values, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncNotIn(List<String> values) {
            addCriterion("type_PNC not in", values, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncBetween(String value1, String value2) {
            addCriterion("type_PNC between", value1, value2, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypePncNotBetween(String value1, String value2) {
            addCriterion("type_PNC not between", value1, value2, "typePnc");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupIsNull() {
            addCriterion("type_S_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupIsNotNull() {
            addCriterion("type_S_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupEqualTo(String value) {
            addCriterion("type_S_GROUP =", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupNotEqualTo(String value) {
            addCriterion("type_S_GROUP <>", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupGreaterThan(String value) {
            addCriterion("type_S_GROUP >", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupGreaterThanOrEqualTo(String value) {
            addCriterion("type_S_GROUP >=", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupLessThan(String value) {
            addCriterion("type_S_GROUP <", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupLessThanOrEqualTo(String value) {
            addCriterion("type_S_GROUP <=", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupLike(String value) {
            addCriterion("type_S_GROUP like", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupNotLike(String value) {
            addCriterion("type_S_GROUP not like", value, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupIn(List<String> values) {
            addCriterion("type_S_GROUP in", values, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupNotIn(List<String> values) {
            addCriterion("type_S_GROUP not in", values, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupBetween(String value1, String value2) {
            addCriterion("type_S_GROUP between", value1, value2, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSGroupNotBetween(String value1, String value2) {
            addCriterion("type_S_GROUP not between", value1, value2, "typeSGroup");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceIsNull() {
            addCriterion("type_S_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceIsNotNull() {
            addCriterion("type_S_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceEqualTo(String value) {
            addCriterion("type_S_PRICE =", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceNotEqualTo(String value) {
            addCriterion("type_S_PRICE <>", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceGreaterThan(String value) {
            addCriterion("type_S_PRICE >", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceGreaterThanOrEqualTo(String value) {
            addCriterion("type_S_PRICE >=", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceLessThan(String value) {
            addCriterion("type_S_PRICE <", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceLessThanOrEqualTo(String value) {
            addCriterion("type_S_PRICE <=", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceLike(String value) {
            addCriterion("type_S_PRICE like", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceNotLike(String value) {
            addCriterion("type_S_PRICE not like", value, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceIn(List<String> values) {
            addCriterion("type_S_PRICE in", values, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceNotIn(List<String> values) {
            addCriterion("type_S_PRICE not in", values, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceBetween(String value1, String value2) {
            addCriterion("type_S_PRICE between", value1, value2, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSPriceNotBetween(String value1, String value2) {
            addCriterion("type_S_PRICE not between", value1, value2, "typeSPrice");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteIsNull() {
            addCriterion("type_S_ROUTE is null");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteIsNotNull() {
            addCriterion("type_S_ROUTE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteEqualTo(String value) {
            addCriterion("type_S_ROUTE =", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteNotEqualTo(String value) {
            addCriterion("type_S_ROUTE <>", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteGreaterThan(String value) {
            addCriterion("type_S_ROUTE >", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteGreaterThanOrEqualTo(String value) {
            addCriterion("type_S_ROUTE >=", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteLessThan(String value) {
            addCriterion("type_S_ROUTE <", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteLessThanOrEqualTo(String value) {
            addCriterion("type_S_ROUTE <=", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteLike(String value) {
            addCriterion("type_S_ROUTE like", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteNotLike(String value) {
            addCriterion("type_S_ROUTE not like", value, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteIn(List<String> values) {
            addCriterion("type_S_ROUTE in", values, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteNotIn(List<String> values) {
            addCriterion("type_S_ROUTE not in", values, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteBetween(String value1, String value2) {
            addCriterion("type_S_ROUTE between", value1, value2, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andTypeSRouteNotBetween(String value1, String value2) {
            addCriterion("type_S_ROUTE not between", value1, value2, "typeSRoute");
            return (Criteria) this;
        }

        public Criteria andAuth95012IsNull() {
            addCriterion("Auth_95012 is null");
            return (Criteria) this;
        }

        public Criteria andAuth95012IsNotNull() {
            addCriterion("Auth_95012 is not null");
            return (Criteria) this;
        }

        public Criteria andAuth95012EqualTo(String value) {
            addCriterion("Auth_95012 =", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012NotEqualTo(String value) {
            addCriterion("Auth_95012 <>", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012GreaterThan(String value) {
            addCriterion("Auth_95012 >", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012GreaterThanOrEqualTo(String value) {
            addCriterion("Auth_95012 >=", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012LessThan(String value) {
            addCriterion("Auth_95012 <", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012LessThanOrEqualTo(String value) {
            addCriterion("Auth_95012 <=", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012Like(String value) {
            addCriterion("Auth_95012 like", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012NotLike(String value) {
            addCriterion("Auth_95012 not like", value, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012In(List<String> values) {
            addCriterion("Auth_95012 in", values, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012NotIn(List<String> values) {
            addCriterion("Auth_95012 not in", values, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012Between(String value1, String value2) {
            addCriterion("Auth_95012 between", value1, value2, "auth95012");
            return (Criteria) this;
        }

        public Criteria andAuth95012NotBetween(String value1, String value2) {
            addCriterion("Auth_95012 not between", value1, value2, "auth95012");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("desc is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("desc is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("desc =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("desc <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("desc >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("desc >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("desc <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("desc <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("desc like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("desc not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("desc in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("desc not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("desc between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("desc not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNull() {
            addCriterion("created_user is null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNotNull() {
            addCriterion("created_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserEqualTo(String value) {
            addCriterion("created_user =", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotEqualTo(String value) {
            addCriterion("created_user <>", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThan(String value) {
            addCriterion("created_user >", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThanOrEqualTo(String value) {
            addCriterion("created_user >=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThan(String value) {
            addCriterion("created_user <", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThanOrEqualTo(String value) {
            addCriterion("created_user <=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLike(String value) {
            addCriterion("created_user like", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotLike(String value) {
            addCriterion("created_user not like", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIn(List<String> values) {
            addCriterion("created_user in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotIn(List<String> values) {
            addCriterion("created_user not in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserBetween(String value1, String value2) {
            addCriterion("created_user between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotBetween(String value1, String value2) {
            addCriterion("created_user not between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNull() {
            addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(Date value) {
            addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(Date value) {
            addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(Date value) {
            addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(Date value) {
            addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(Date value) {
            addCriterion("updated_date <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(List<Date> values) {
            addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(List<Date> values) {
            addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(Date value1, Date value2) {
            addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(Date value1, Date value2) {
            addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIsNull() {
            addCriterion("updated_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIsNotNull() {
            addCriterion("updated_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserEqualTo(String value) {
            addCriterion("updated_user =", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotEqualTo(String value) {
            addCriterion("updated_user <>", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserGreaterThan(String value) {
            addCriterion("updated_user >", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserGreaterThanOrEqualTo(String value) {
            addCriterion("updated_user >=", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLessThan(String value) {
            addCriterion("updated_user <", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLessThanOrEqualTo(String value) {
            addCriterion("updated_user <=", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLike(String value) {
            addCriterion("updated_user like", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotLike(String value) {
            addCriterion("updated_user not like", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIn(List<String> values) {
            addCriterion("updated_user in", values, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotIn(List<String> values) {
            addCriterion("updated_user not in", values, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserBetween(String value1, String value2) {
            addCriterion("updated_user between", value1, value2, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotBetween(String value1, String value2) {
            addCriterion("updated_user not between", value1, value2, "updatedUser");
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
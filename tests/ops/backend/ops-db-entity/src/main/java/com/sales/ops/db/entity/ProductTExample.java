package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductTExample() {
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

        public Criteria andEcodeIsNull() {
            addCriterion("ECode is null");
            return (Criteria) this;
        }

        public Criteria andEcodeIsNotNull() {
            addCriterion("ECode is not null");
            return (Criteria) this;
        }

        public Criteria andEcodeEqualTo(String value) {
            addCriterion("ECode =", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotEqualTo(String value) {
            addCriterion("ECode <>", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThan(String value) {
            addCriterion("ECode >", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ECode >=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThan(String value) {
            addCriterion("ECode <", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLessThanOrEqualTo(String value) {
            addCriterion("ECode <=", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeLike(String value) {
            addCriterion("ECode like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotLike(String value) {
            addCriterion("ECode not like", value, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeIn(List<String> values) {
            addCriterion("ECode in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotIn(List<String> values) {
            addCriterion("ECode not in", values, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeBetween(String value1, String value2) {
            addCriterion("ECode between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andEcodeNotBetween(String value1, String value2) {
            addCriterion("ECode not between", value1, value2, "ecode");
            return (Criteria) this;
        }

        public Criteria andChinesenameIsNull() {
            addCriterion("ChineseName is null");
            return (Criteria) this;
        }

        public Criteria andChinesenameIsNotNull() {
            addCriterion("ChineseName is not null");
            return (Criteria) this;
        }

        public Criteria andChinesenameEqualTo(String value) {
            addCriterion("ChineseName =", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameNotEqualTo(String value) {
            addCriterion("ChineseName <>", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameGreaterThan(String value) {
            addCriterion("ChineseName >", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameGreaterThanOrEqualTo(String value) {
            addCriterion("ChineseName >=", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameLessThan(String value) {
            addCriterion("ChineseName <", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameLessThanOrEqualTo(String value) {
            addCriterion("ChineseName <=", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameLike(String value) {
            addCriterion("ChineseName like", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameNotLike(String value) {
            addCriterion("ChineseName not like", value, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameIn(List<String> values) {
            addCriterion("ChineseName in", values, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameNotIn(List<String> values) {
            addCriterion("ChineseName not in", values, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameBetween(String value1, String value2) {
            addCriterion("ChineseName between", value1, value2, "chinesename");
            return (Criteria) this;
        }

        public Criteria andChinesenameNotBetween(String value1, String value2) {
            addCriterion("ChineseName not between", value1, value2, "chinesename");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIsNull() {
            addCriterion("EnglishName is null");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIsNotNull() {
            addCriterion("EnglishName is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishnameEqualTo(String value) {
            addCriterion("EnglishName =", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotEqualTo(String value) {
            addCriterion("EnglishName <>", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameGreaterThan(String value) {
            addCriterion("EnglishName >", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameGreaterThanOrEqualTo(String value) {
            addCriterion("EnglishName >=", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLessThan(String value) {
            addCriterion("EnglishName <", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLessThanOrEqualTo(String value) {
            addCriterion("EnglishName <=", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameLike(String value) {
            addCriterion("EnglishName like", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotLike(String value) {
            addCriterion("EnglishName not like", value, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameIn(List<String> values) {
            addCriterion("EnglishName in", values, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotIn(List<String> values) {
            addCriterion("EnglishName not in", values, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameBetween(String value1, String value2) {
            addCriterion("EnglishName between", value1, value2, "englishname");
            return (Criteria) this;
        }

        public Criteria andEnglishnameNotBetween(String value1, String value2) {
            addCriterion("EnglishName not between", value1, value2, "englishname");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNull() {
            addCriterion("TypeId is null");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNotNull() {
            addCriterion("TypeId is not null");
            return (Criteria) this;
        }

        public Criteria andTypeidEqualTo(String value) {
            addCriterion("TypeId =", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotEqualTo(String value) {
            addCriterion("TypeId <>", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThan(String value) {
            addCriterion("TypeId >", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThanOrEqualTo(String value) {
            addCriterion("TypeId >=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThan(String value) {
            addCriterion("TypeId <", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThanOrEqualTo(String value) {
            addCriterion("TypeId <=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLike(String value) {
            addCriterion("TypeId like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotLike(String value) {
            addCriterion("TypeId not like", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidIn(List<String> values) {
            addCriterion("TypeId in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotIn(List<String> values) {
            addCriterion("TypeId not in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidBetween(String value1, String value2) {
            addCriterion("TypeId between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotBetween(String value1, String value2) {
            addCriterion("TypeId not between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andClassifycode1IsNull() {
            addCriterion("ClassifyCode1 is null");
            return (Criteria) this;
        }

        public Criteria andClassifycode1IsNotNull() {
            addCriterion("ClassifyCode1 is not null");
            return (Criteria) this;
        }

        public Criteria andClassifycode1EqualTo(String value) {
            addCriterion("ClassifyCode1 =", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1NotEqualTo(String value) {
            addCriterion("ClassifyCode1 <>", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1GreaterThan(String value) {
            addCriterion("ClassifyCode1 >", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1GreaterThanOrEqualTo(String value) {
            addCriterion("ClassifyCode1 >=", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1LessThan(String value) {
            addCriterion("ClassifyCode1 <", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1LessThanOrEqualTo(String value) {
            addCriterion("ClassifyCode1 <=", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1Like(String value) {
            addCriterion("ClassifyCode1 like", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1NotLike(String value) {
            addCriterion("ClassifyCode1 not like", value, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1In(List<String> values) {
            addCriterion("ClassifyCode1 in", values, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1NotIn(List<String> values) {
            addCriterion("ClassifyCode1 not in", values, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1Between(String value1, String value2) {
            addCriterion("ClassifyCode1 between", value1, value2, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode1NotBetween(String value1, String value2) {
            addCriterion("ClassifyCode1 not between", value1, value2, "classifycode1");
            return (Criteria) this;
        }

        public Criteria andClassifycode2IsNull() {
            addCriterion("ClassifyCode2 is null");
            return (Criteria) this;
        }

        public Criteria andClassifycode2IsNotNull() {
            addCriterion("ClassifyCode2 is not null");
            return (Criteria) this;
        }

        public Criteria andClassifycode2EqualTo(String value) {
            addCriterion("ClassifyCode2 =", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2NotEqualTo(String value) {
            addCriterion("ClassifyCode2 <>", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2GreaterThan(String value) {
            addCriterion("ClassifyCode2 >", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2GreaterThanOrEqualTo(String value) {
            addCriterion("ClassifyCode2 >=", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2LessThan(String value) {
            addCriterion("ClassifyCode2 <", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2LessThanOrEqualTo(String value) {
            addCriterion("ClassifyCode2 <=", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2Like(String value) {
            addCriterion("ClassifyCode2 like", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2NotLike(String value) {
            addCriterion("ClassifyCode2 not like", value, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2In(List<String> values) {
            addCriterion("ClassifyCode2 in", values, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2NotIn(List<String> values) {
            addCriterion("ClassifyCode2 not in", values, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2Between(String value1, String value2) {
            addCriterion("ClassifyCode2 between", value1, value2, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode2NotBetween(String value1, String value2) {
            addCriterion("ClassifyCode2 not between", value1, value2, "classifycode2");
            return (Criteria) this;
        }

        public Criteria andClassifycode3IsNull() {
            addCriterion("ClassifyCode3 is null");
            return (Criteria) this;
        }

        public Criteria andClassifycode3IsNotNull() {
            addCriterion("ClassifyCode3 is not null");
            return (Criteria) this;
        }

        public Criteria andClassifycode3EqualTo(String value) {
            addCriterion("ClassifyCode3 =", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3NotEqualTo(String value) {
            addCriterion("ClassifyCode3 <>", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3GreaterThan(String value) {
            addCriterion("ClassifyCode3 >", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3GreaterThanOrEqualTo(String value) {
            addCriterion("ClassifyCode3 >=", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3LessThan(String value) {
            addCriterion("ClassifyCode3 <", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3LessThanOrEqualTo(String value) {
            addCriterion("ClassifyCode3 <=", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3Like(String value) {
            addCriterion("ClassifyCode3 like", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3NotLike(String value) {
            addCriterion("ClassifyCode3 not like", value, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3In(List<String> values) {
            addCriterion("ClassifyCode3 in", values, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3NotIn(List<String> values) {
            addCriterion("ClassifyCode3 not in", values, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3Between(String value1, String value2) {
            addCriterion("ClassifyCode3 between", value1, value2, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andClassifycode3NotBetween(String value1, String value2) {
            addCriterion("ClassifyCode3 not between", value1, value2, "classifycode3");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidIsNull() {
            addCriterion("CompetitivenessID is null");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidIsNotNull() {
            addCriterion("CompetitivenessID is not null");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidEqualTo(String value) {
            addCriterion("CompetitivenessID =", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidNotEqualTo(String value) {
            addCriterion("CompetitivenessID <>", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidGreaterThan(String value) {
            addCriterion("CompetitivenessID >", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidGreaterThanOrEqualTo(String value) {
            addCriterion("CompetitivenessID >=", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidLessThan(String value) {
            addCriterion("CompetitivenessID <", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidLessThanOrEqualTo(String value) {
            addCriterion("CompetitivenessID <=", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidLike(String value) {
            addCriterion("CompetitivenessID like", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidNotLike(String value) {
            addCriterion("CompetitivenessID not like", value, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidIn(List<String> values) {
            addCriterion("CompetitivenessID in", values, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidNotIn(List<String> values) {
            addCriterion("CompetitivenessID not in", values, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidBetween(String value1, String value2) {
            addCriterion("CompetitivenessID between", value1, value2, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andCompetitivenessidNotBetween(String value1, String value2) {
            addCriterion("CompetitivenessID not between", value1, value2, "competitivenessid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidIsNull() {
            addCriterion("DesignTypeId is null");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidIsNotNull() {
            addCriterion("DesignTypeId is not null");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidEqualTo(String value) {
            addCriterion("DesignTypeId =", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidNotEqualTo(String value) {
            addCriterion("DesignTypeId <>", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidGreaterThan(String value) {
            addCriterion("DesignTypeId >", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidGreaterThanOrEqualTo(String value) {
            addCriterion("DesignTypeId >=", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidLessThan(String value) {
            addCriterion("DesignTypeId <", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidLessThanOrEqualTo(String value) {
            addCriterion("DesignTypeId <=", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidLike(String value) {
            addCriterion("DesignTypeId like", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidNotLike(String value) {
            addCriterion("DesignTypeId not like", value, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidIn(List<String> values) {
            addCriterion("DesignTypeId in", values, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidNotIn(List<String> values) {
            addCriterion("DesignTypeId not in", values, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidBetween(String value1, String value2) {
            addCriterion("DesignTypeId between", value1, value2, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andDesigntypeidNotBetween(String value1, String value2) {
            addCriterion("DesignTypeId not between", value1, value2, "designtypeid");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andIsRestrictIsNull() {
            addCriterion("is_restrict is null");
            return (Criteria) this;
        }

        public Criteria andIsRestrictIsNotNull() {
            addCriterion("is_restrict is not null");
            return (Criteria) this;
        }

        public Criteria andIsRestrictEqualTo(String value) {
            addCriterion("is_restrict =", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictNotEqualTo(String value) {
            addCriterion("is_restrict <>", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictGreaterThan(String value) {
            addCriterion("is_restrict >", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictGreaterThanOrEqualTo(String value) {
            addCriterion("is_restrict >=", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictLessThan(String value) {
            addCriterion("is_restrict <", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictLessThanOrEqualTo(String value) {
            addCriterion("is_restrict <=", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictLike(String value) {
            addCriterion("is_restrict like", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictNotLike(String value) {
            addCriterion("is_restrict not like", value, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictIn(List<String> values) {
            addCriterion("is_restrict in", values, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictNotIn(List<String> values) {
            addCriterion("is_restrict not in", values, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictBetween(String value1, String value2) {
            addCriterion("is_restrict between", value1, value2, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsRestrictNotBetween(String value1, String value2) {
            addCriterion("is_restrict not between", value1, value2, "isRestrict");
            return (Criteria) this;
        }

        public Criteria andIsEosIsNull() {
            addCriterion("is_eos is null");
            return (Criteria) this;
        }

        public Criteria andIsEosIsNotNull() {
            addCriterion("is_eos is not null");
            return (Criteria) this;
        }

        public Criteria andIsEosEqualTo(String value) {
            addCriterion("is_eos =", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotEqualTo(String value) {
            addCriterion("is_eos <>", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosGreaterThan(String value) {
            addCriterion("is_eos >", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosGreaterThanOrEqualTo(String value) {
            addCriterion("is_eos >=", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosLessThan(String value) {
            addCriterion("is_eos <", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosLessThanOrEqualTo(String value) {
            addCriterion("is_eos <=", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosLike(String value) {
            addCriterion("is_eos like", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotLike(String value) {
            addCriterion("is_eos not like", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosIn(List<String> values) {
            addCriterion("is_eos in", values, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotIn(List<String> values) {
            addCriterion("is_eos not in", values, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosBetween(String value1, String value2) {
            addCriterion("is_eos between", value1, value2, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotBetween(String value1, String value2) {
            addCriterion("is_eos not between", value1, value2, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsErrorIsNull() {
            addCriterion("is_error is null");
            return (Criteria) this;
        }

        public Criteria andIsErrorIsNotNull() {
            addCriterion("is_error is not null");
            return (Criteria) this;
        }

        public Criteria andIsErrorEqualTo(String value) {
            addCriterion("is_error =", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotEqualTo(String value) {
            addCriterion("is_error <>", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorGreaterThan(String value) {
            addCriterion("is_error >", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorGreaterThanOrEqualTo(String value) {
            addCriterion("is_error >=", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorLessThan(String value) {
            addCriterion("is_error <", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorLessThanOrEqualTo(String value) {
            addCriterion("is_error <=", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorLike(String value) {
            addCriterion("is_error like", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotLike(String value) {
            addCriterion("is_error not like", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorIn(List<String> values) {
            addCriterion("is_error in", values, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotIn(List<String> values) {
            addCriterion("is_error not in", values, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorBetween(String value1, String value2) {
            addCriterion("is_error between", value1, value2, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotBetween(String value1, String value2) {
            addCriterion("is_error not between", value1, value2, "isError");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeIsNull() {
            addCriterion("long_lead_time is null");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeIsNotNull() {
            addCriterion("long_lead_time is not null");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeEqualTo(String value) {
            addCriterion("long_lead_time =", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeNotEqualTo(String value) {
            addCriterion("long_lead_time <>", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeGreaterThan(String value) {
            addCriterion("long_lead_time >", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeGreaterThanOrEqualTo(String value) {
            addCriterion("long_lead_time >=", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeLessThan(String value) {
            addCriterion("long_lead_time <", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeLessThanOrEqualTo(String value) {
            addCriterion("long_lead_time <=", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeLike(String value) {
            addCriterion("long_lead_time like", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeNotLike(String value) {
            addCriterion("long_lead_time not like", value, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeIn(List<String> values) {
            addCriterion("long_lead_time in", values, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeNotIn(List<String> values) {
            addCriterion("long_lead_time not in", values, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeBetween(String value1, String value2) {
            addCriterion("long_lead_time between", value1, value2, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andLongLeadTimeNotBetween(String value1, String value2) {
            addCriterion("long_lead_time not between", value1, value2, "longLeadTime");
            return (Criteria) this;
        }

        public Criteria andIsOverweightIsNull() {
            addCriterion("is_overweight is null");
            return (Criteria) this;
        }

        public Criteria andIsOverweightIsNotNull() {
            addCriterion("is_overweight is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverweightEqualTo(String value) {
            addCriterion("is_overweight =", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightNotEqualTo(String value) {
            addCriterion("is_overweight <>", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightGreaterThan(String value) {
            addCriterion("is_overweight >", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightGreaterThanOrEqualTo(String value) {
            addCriterion("is_overweight >=", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightLessThan(String value) {
            addCriterion("is_overweight <", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightLessThanOrEqualTo(String value) {
            addCriterion("is_overweight <=", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightLike(String value) {
            addCriterion("is_overweight like", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightNotLike(String value) {
            addCriterion("is_overweight not like", value, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightIn(List<String> values) {
            addCriterion("is_overweight in", values, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightNotIn(List<String> values) {
            addCriterion("is_overweight not in", values, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightBetween(String value1, String value2) {
            addCriterion("is_overweight between", value1, value2, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverweightNotBetween(String value1, String value2) {
            addCriterion("is_overweight not between", value1, value2, "isOverweight");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthIsNull() {
            addCriterion("is_overlength is null");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthIsNotNull() {
            addCriterion("is_overlength is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthEqualTo(String value) {
            addCriterion("is_overlength =", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthNotEqualTo(String value) {
            addCriterion("is_overlength <>", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthGreaterThan(String value) {
            addCriterion("is_overlength >", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthGreaterThanOrEqualTo(String value) {
            addCriterion("is_overlength >=", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthLessThan(String value) {
            addCriterion("is_overlength <", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthLessThanOrEqualTo(String value) {
            addCriterion("is_overlength <=", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthLike(String value) {
            addCriterion("is_overlength like", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthNotLike(String value) {
            addCriterion("is_overlength not like", value, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthIn(List<String> values) {
            addCriterion("is_overlength in", values, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthNotIn(List<String> values) {
            addCriterion("is_overlength not in", values, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthBetween(String value1, String value2) {
            addCriterion("is_overlength between", value1, value2, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andIsOverlengthNotBetween(String value1, String value2) {
            addCriterion("is_overlength not between", value1, value2, "isOverlength");
            return (Criteria) this;
        }

        public Criteria andShiptypeIsNull() {
            addCriterion("ShipType is null");
            return (Criteria) this;
        }

        public Criteria andShiptypeIsNotNull() {
            addCriterion("ShipType is not null");
            return (Criteria) this;
        }

        public Criteria andShiptypeEqualTo(String value) {
            addCriterion("ShipType =", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeNotEqualTo(String value) {
            addCriterion("ShipType <>", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeGreaterThan(String value) {
            addCriterion("ShipType >", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeGreaterThanOrEqualTo(String value) {
            addCriterion("ShipType >=", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeLessThan(String value) {
            addCriterion("ShipType <", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeLessThanOrEqualTo(String value) {
            addCriterion("ShipType <=", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeLike(String value) {
            addCriterion("ShipType like", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeNotLike(String value) {
            addCriterion("ShipType not like", value, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeIn(List<String> values) {
            addCriterion("ShipType in", values, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeNotIn(List<String> values) {
            addCriterion("ShipType not in", values, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeBetween(String value1, String value2) {
            addCriterion("ShipType between", value1, value2, "shiptype");
            return (Criteria) this;
        }

        public Criteria andShiptypeNotBetween(String value1, String value2) {
            addCriterion("ShipType not between", value1, value2, "shiptype");
            return (Criteria) this;
        }

        public Criteria andBucklingsignIsNull() {
            addCriterion("bucklingSign is null");
            return (Criteria) this;
        }

        public Criteria andBucklingsignIsNotNull() {
            addCriterion("bucklingSign is not null");
            return (Criteria) this;
        }

        public Criteria andBucklingsignEqualTo(String value) {
            addCriterion("bucklingSign =", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignNotEqualTo(String value) {
            addCriterion("bucklingSign <>", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignGreaterThan(String value) {
            addCriterion("bucklingSign >", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignGreaterThanOrEqualTo(String value) {
            addCriterion("bucklingSign >=", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignLessThan(String value) {
            addCriterion("bucklingSign <", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignLessThanOrEqualTo(String value) {
            addCriterion("bucklingSign <=", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignLike(String value) {
            addCriterion("bucklingSign like", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignNotLike(String value) {
            addCriterion("bucklingSign not like", value, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignIn(List<String> values) {
            addCriterion("bucklingSign in", values, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignNotIn(List<String> values) {
            addCriterion("bucklingSign not in", values, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignBetween(String value1, String value2) {
            addCriterion("bucklingSign between", value1, value2, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andBucklingsignNotBetween(String value1, String value2) {
            addCriterion("bucklingSign not between", value1, value2, "bucklingsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignIsNull() {
            addCriterion("manifoldsign is null");
            return (Criteria) this;
        }

        public Criteria andManifoldsignIsNotNull() {
            addCriterion("manifoldsign is not null");
            return (Criteria) this;
        }

        public Criteria andManifoldsignEqualTo(String value) {
            addCriterion("manifoldsign =", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignNotEqualTo(String value) {
            addCriterion("manifoldsign <>", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignGreaterThan(String value) {
            addCriterion("manifoldsign >", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignGreaterThanOrEqualTo(String value) {
            addCriterion("manifoldsign >=", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignLessThan(String value) {
            addCriterion("manifoldsign <", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignLessThanOrEqualTo(String value) {
            addCriterion("manifoldsign <=", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignLike(String value) {
            addCriterion("manifoldsign like", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignNotLike(String value) {
            addCriterion("manifoldsign not like", value, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignIn(List<String> values) {
            addCriterion("manifoldsign in", values, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignNotIn(List<String> values) {
            addCriterion("manifoldsign not in", values, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignBetween(String value1, String value2) {
            addCriterion("manifoldsign between", value1, value2, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andManifoldsignNotBetween(String value1, String value2) {
            addCriterion("manifoldsign not between", value1, value2, "manifoldsign");
            return (Criteria) this;
        }

        public Criteria andIsAtexIsNull() {
            addCriterion("is_ATEX is null");
            return (Criteria) this;
        }

        public Criteria andIsAtexIsNotNull() {
            addCriterion("is_ATEX is not null");
            return (Criteria) this;
        }

        public Criteria andIsAtexEqualTo(String value) {
            addCriterion("is_ATEX =", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexNotEqualTo(String value) {
            addCriterion("is_ATEX <>", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexGreaterThan(String value) {
            addCriterion("is_ATEX >", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexGreaterThanOrEqualTo(String value) {
            addCriterion("is_ATEX >=", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexLessThan(String value) {
            addCriterion("is_ATEX <", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexLessThanOrEqualTo(String value) {
            addCriterion("is_ATEX <=", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexLike(String value) {
            addCriterion("is_ATEX like", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexNotLike(String value) {
            addCriterion("is_ATEX not like", value, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexIn(List<String> values) {
            addCriterion("is_ATEX in", values, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexNotIn(List<String> values) {
            addCriterion("is_ATEX not in", values, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexBetween(String value1, String value2) {
            addCriterion("is_ATEX between", value1, value2, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsAtexNotBetween(String value1, String value2) {
            addCriterion("is_ATEX not between", value1, value2, "isAtex");
            return (Criteria) this;
        }

        public Criteria andIsEvenIsNull() {
            addCriterion("is_Even is null");
            return (Criteria) this;
        }

        public Criteria andIsEvenIsNotNull() {
            addCriterion("is_Even is not null");
            return (Criteria) this;
        }

        public Criteria andIsEvenEqualTo(String value) {
            addCriterion("is_Even =", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenNotEqualTo(String value) {
            addCriterion("is_Even <>", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenGreaterThan(String value) {
            addCriterion("is_Even >", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenGreaterThanOrEqualTo(String value) {
            addCriterion("is_Even >=", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenLessThan(String value) {
            addCriterion("is_Even <", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenLessThanOrEqualTo(String value) {
            addCriterion("is_Even <=", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenLike(String value) {
            addCriterion("is_Even like", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenNotLike(String value) {
            addCriterion("is_Even not like", value, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenIn(List<String> values) {
            addCriterion("is_Even in", values, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenNotIn(List<String> values) {
            addCriterion("is_Even not in", values, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenBetween(String value1, String value2) {
            addCriterion("is_Even between", value1, value2, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsEvenNotBetween(String value1, String value2) {
            addCriterion("is_Even not between", value1, value2, "isEven");
            return (Criteria) this;
        }

        public Criteria andIsSecurityIsNull() {
            addCriterion("is_Security is null");
            return (Criteria) this;
        }

        public Criteria andIsSecurityIsNotNull() {
            addCriterion("is_Security is not null");
            return (Criteria) this;
        }

        public Criteria andIsSecurityEqualTo(String value) {
            addCriterion("is_Security =", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityNotEqualTo(String value) {
            addCriterion("is_Security <>", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityGreaterThan(String value) {
            addCriterion("is_Security >", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityGreaterThanOrEqualTo(String value) {
            addCriterion("is_Security >=", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityLessThan(String value) {
            addCriterion("is_Security <", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityLessThanOrEqualTo(String value) {
            addCriterion("is_Security <=", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityLike(String value) {
            addCriterion("is_Security like", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityNotLike(String value) {
            addCriterion("is_Security not like", value, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityIn(List<String> values) {
            addCriterion("is_Security in", values, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityNotIn(List<String> values) {
            addCriterion("is_Security not in", values, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityBetween(String value1, String value2) {
            addCriterion("is_Security between", value1, value2, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andIsSecurityNotBetween(String value1, String value2) {
            addCriterion("is_Security not between", value1, value2, "isSecurity");
            return (Criteria) this;
        }

        public Criteria andRohsIsNull() {
            addCriterion("ROHS is null");
            return (Criteria) this;
        }

        public Criteria andRohsIsNotNull() {
            addCriterion("ROHS is not null");
            return (Criteria) this;
        }

        public Criteria andRohsEqualTo(String value) {
            addCriterion("ROHS =", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotEqualTo(String value) {
            addCriterion("ROHS <>", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThan(String value) {
            addCriterion("ROHS >", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsGreaterThanOrEqualTo(String value) {
            addCriterion("ROHS >=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThan(String value) {
            addCriterion("ROHS <", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLessThanOrEqualTo(String value) {
            addCriterion("ROHS <=", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsLike(String value) {
            addCriterion("ROHS like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotLike(String value) {
            addCriterion("ROHS not like", value, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsIn(List<String> values) {
            addCriterion("ROHS in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotIn(List<String> values) {
            addCriterion("ROHS not in", values, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsBetween(String value1, String value2) {
            addCriterion("ROHS between", value1, value2, "rohs");
            return (Criteria) this;
        }

        public Criteria andRohsNotBetween(String value1, String value2) {
            addCriterion("ROHS not between", value1, value2, "rohs");
            return (Criteria) this;
        }

        public Criteria andLargeSizeIsNull() {
            addCriterion("Large_size is null");
            return (Criteria) this;
        }

        public Criteria andLargeSizeIsNotNull() {
            addCriterion("Large_size is not null");
            return (Criteria) this;
        }

        public Criteria andLargeSizeEqualTo(String value) {
            addCriterion("Large_size =", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeNotEqualTo(String value) {
            addCriterion("Large_size <>", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeGreaterThan(String value) {
            addCriterion("Large_size >", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeGreaterThanOrEqualTo(String value) {
            addCriterion("Large_size >=", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeLessThan(String value) {
            addCriterion("Large_size <", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeLessThanOrEqualTo(String value) {
            addCriterion("Large_size <=", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeLike(String value) {
            addCriterion("Large_size like", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeNotLike(String value) {
            addCriterion("Large_size not like", value, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeIn(List<String> values) {
            addCriterion("Large_size in", values, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeNotIn(List<String> values) {
            addCriterion("Large_size not in", values, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeBetween(String value1, String value2) {
            addCriterion("Large_size between", value1, value2, "largeSize");
            return (Criteria) this;
        }

        public Criteria andLargeSizeNotBetween(String value1, String value2) {
            addCriterion("Large_size not between", value1, value2, "largeSize");
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

        public Criteria andMinPackUnitIsNull() {
            addCriterion("min_pack_unit is null");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitIsNotNull() {
            addCriterion("min_pack_unit is not null");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitEqualTo(Integer value) {
            addCriterion("min_pack_unit =", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitNotEqualTo(Integer value) {
            addCriterion("min_pack_unit <>", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitGreaterThan(Integer value) {
            addCriterion("min_pack_unit >", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_pack_unit >=", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitLessThan(Integer value) {
            addCriterion("min_pack_unit <", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitLessThanOrEqualTo(Integer value) {
            addCriterion("min_pack_unit <=", value, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitIn(List<Integer> values) {
            addCriterion("min_pack_unit in", values, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitNotIn(List<Integer> values) {
            addCriterion("min_pack_unit not in", values, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitBetween(Integer value1, Integer value2) {
            addCriterion("min_pack_unit between", value1, value2, "minPackUnit");
            return (Criteria) this;
        }

        public Criteria andMinPackUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("min_pack_unit not between", value1, value2, "minPackUnit");
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

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoIsNull() {
            addCriterion("OuterBoxPartNo is null");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoIsNotNull() {
            addCriterion("OuterBoxPartNo is not null");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoEqualTo(String value) {
            addCriterion("OuterBoxPartNo =", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoNotEqualTo(String value) {
            addCriterion("OuterBoxPartNo <>", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoGreaterThan(String value) {
            addCriterion("OuterBoxPartNo >", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoGreaterThanOrEqualTo(String value) {
            addCriterion("OuterBoxPartNo >=", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoLessThan(String value) {
            addCriterion("OuterBoxPartNo <", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoLessThanOrEqualTo(String value) {
            addCriterion("OuterBoxPartNo <=", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoLike(String value) {
            addCriterion("OuterBoxPartNo like", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoNotLike(String value) {
            addCriterion("OuterBoxPartNo not like", value, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoIn(List<String> values) {
            addCriterion("OuterBoxPartNo in", values, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoNotIn(List<String> values) {
            addCriterion("OuterBoxPartNo not in", values, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoBetween(String value1, String value2) {
            addCriterion("OuterBoxPartNo between", value1, value2, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxpartnoNotBetween(String value1, String value2) {
            addCriterion("OuterBoxPartNo not between", value1, value2, "outerboxpartno");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityIsNull() {
            addCriterion("OuterBoxQuantity is null");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityIsNotNull() {
            addCriterion("OuterBoxQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityEqualTo(Integer value) {
            addCriterion("OuterBoxQuantity =", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityNotEqualTo(Integer value) {
            addCriterion("OuterBoxQuantity <>", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityGreaterThan(Integer value) {
            addCriterion("OuterBoxQuantity >", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("OuterBoxQuantity >=", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityLessThan(Integer value) {
            addCriterion("OuterBoxQuantity <", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityLessThanOrEqualTo(Integer value) {
            addCriterion("OuterBoxQuantity <=", value, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityIn(List<Integer> values) {
            addCriterion("OuterBoxQuantity in", values, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityNotIn(List<Integer> values) {
            addCriterion("OuterBoxQuantity not in", values, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityBetween(Integer value1, Integer value2) {
            addCriterion("OuterBoxQuantity between", value1, value2, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andOuterboxquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("OuterBoxQuantity not between", value1, value2, "outerboxquantity");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIsNull() {
            addCriterion("nonstandard_sized_product is null");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIsNotNull() {
            addCriterion("nonstandard_sized_product is not null");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductEqualTo(String value) {
            addCriterion("nonstandard_sized_product =", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotEqualTo(String value) {
            addCriterion("nonstandard_sized_product <>", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductGreaterThan(String value) {
            addCriterion("nonstandard_sized_product >", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductGreaterThanOrEqualTo(String value) {
            addCriterion("nonstandard_sized_product >=", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLessThan(String value) {
            addCriterion("nonstandard_sized_product <", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLessThanOrEqualTo(String value) {
            addCriterion("nonstandard_sized_product <=", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductLike(String value) {
            addCriterion("nonstandard_sized_product like", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotLike(String value) {
            addCriterion("nonstandard_sized_product not like", value, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductIn(List<String> values) {
            addCriterion("nonstandard_sized_product in", values, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotIn(List<String> values) {
            addCriterion("nonstandard_sized_product not in", values, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductBetween(String value1, String value2) {
            addCriterion("nonstandard_sized_product between", value1, value2, "nonstandardSizedProduct");
            return (Criteria) this;
        }

        public Criteria andNonstandardSizedProductNotBetween(String value1, String value2) {
            addCriterion("nonstandard_sized_product not between", value1, value2, "nonstandardSizedProduct");
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
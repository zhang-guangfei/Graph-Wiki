package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductExample() {
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andIsEosIsNull() {
            addCriterion("is_eos is null");
            return (Criteria) this;
        }

        public Criteria andIsEosIsNotNull() {
            addCriterion("is_eos is not null");
            return (Criteria) this;
        }

        public Criteria andIsEosEqualTo(Boolean value) {
            addCriterion("is_eos =", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotEqualTo(Boolean value) {
            addCriterion("is_eos <>", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosGreaterThan(Boolean value) {
            addCriterion("is_eos >", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_eos >=", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosLessThan(Boolean value) {
            addCriterion("is_eos <", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosLessThanOrEqualTo(Boolean value) {
            addCriterion("is_eos <=", value, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosIn(List<Boolean> values) {
            addCriterion("is_eos in", values, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotIn(List<Boolean> values) {
            addCriterion("is_eos not in", values, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosBetween(Boolean value1, Boolean value2) {
            addCriterion("is_eos between", value1, value2, "isEos");
            return (Criteria) this;
        }

        public Criteria andIsEosNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_eos not between", value1, value2, "isEos");
            return (Criteria) this;
        }

        public Criteria andMinQuantityIsNull() {
            addCriterion("min_quantity is null");
            return (Criteria) this;
        }

        public Criteria andMinQuantityIsNotNull() {
            addCriterion("min_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andMinQuantityEqualTo(Integer value) {
            addCriterion("min_quantity =", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityNotEqualTo(Integer value) {
            addCriterion("min_quantity <>", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityGreaterThan(Integer value) {
            addCriterion("min_quantity >", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_quantity >=", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityLessThan(Integer value) {
            addCriterion("min_quantity <", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("min_quantity <=", value, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityIn(List<Integer> values) {
            addCriterion("min_quantity in", values, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityNotIn(List<Integer> values) {
            addCriterion("min_quantity not in", values, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityBetween(Integer value1, Integer value2) {
            addCriterion("min_quantity between", value1, value2, "minQuantity");
            return (Criteria) this;
        }

        public Criteria andMinQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("min_quantity not between", value1, value2, "minQuantity");
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
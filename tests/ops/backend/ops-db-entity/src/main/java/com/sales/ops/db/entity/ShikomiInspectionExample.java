package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShikomiInspectionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShikomiInspectionExample() {
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andShikomiidIsNull() {
            addCriterion("ShikomiId is null");
            return (Criteria) this;
        }

        public Criteria andShikomiidIsNotNull() {
            addCriterion("ShikomiId is not null");
            return (Criteria) this;
        }

        public Criteria andShikomiidEqualTo(Long value) {
            addCriterion("ShikomiId =", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidNotEqualTo(Long value) {
            addCriterion("ShikomiId <>", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidGreaterThan(Long value) {
            addCriterion("ShikomiId >", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidGreaterThanOrEqualTo(Long value) {
            addCriterion("ShikomiId >=", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidLessThan(Long value) {
            addCriterion("ShikomiId <", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidLessThanOrEqualTo(Long value) {
            addCriterion("ShikomiId <=", value, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidIn(List<Long> values) {
            addCriterion("ShikomiId in", values, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidNotIn(List<Long> values) {
            addCriterion("ShikomiId not in", values, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidBetween(Long value1, Long value2) {
            addCriterion("ShikomiId between", value1, value2, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikomiidNotBetween(Long value1, Long value2) {
            addCriterion("ShikomiId not between", value1, value2, "shikomiid");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNull() {
            addCriterion("ShikomiNo is null");
            return (Criteria) this;
        }

        public Criteria andShikominoIsNotNull() {
            addCriterion("ShikomiNo is not null");
            return (Criteria) this;
        }

        public Criteria andShikominoEqualTo(String value) {
            addCriterion("ShikomiNo =", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotEqualTo(String value) {
            addCriterion("ShikomiNo <>", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThan(String value) {
            addCriterion("ShikomiNo >", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoGreaterThanOrEqualTo(String value) {
            addCriterion("ShikomiNo >=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThan(String value) {
            addCriterion("ShikomiNo <", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLessThanOrEqualTo(String value) {
            addCriterion("ShikomiNo <=", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoLike(String value) {
            addCriterion("ShikomiNo like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotLike(String value) {
            addCriterion("ShikomiNo not like", value, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoIn(List<String> values) {
            addCriterion("ShikomiNo in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotIn(List<String> values) {
            addCriterion("ShikomiNo not in", values, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoBetween(String value1, String value2) {
            addCriterion("ShikomiNo between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andShikominoNotBetween(String value1, String value2) {
            addCriterion("ShikomiNo not between", value1, value2, "shikomino");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIsNull() {
            addCriterion("inspectType is null");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIsNotNull() {
            addCriterion("inspectType is not null");
            return (Criteria) this;
        }

        public Criteria andInspecttypeEqualTo(Short value) {
            addCriterion("inspectType =", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotEqualTo(Short value) {
            addCriterion("inspectType <>", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeGreaterThan(Short value) {
            addCriterion("inspectType >", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectType >=", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeLessThan(Short value) {
            addCriterion("inspectType <", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeLessThanOrEqualTo(Short value) {
            addCriterion("inspectType <=", value, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeIn(List<Short> values) {
            addCriterion("inspectType in", values, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotIn(List<Short> values) {
            addCriterion("inspectType not in", values, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeBetween(Short value1, Short value2) {
            addCriterion("inspectType between", value1, value2, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andInspecttypeNotBetween(Short value1, Short value2) {
            addCriterion("inspectType not between", value1, value2, "inspecttype");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNull() {
            addCriterion("QtyWarning is null");
            return (Criteria) this;
        }

        public Criteria andQtywarningIsNotNull() {
            addCriterion("QtyWarning is not null");
            return (Criteria) this;
        }

        public Criteria andQtywarningEqualTo(Integer value) {
            addCriterion("QtyWarning =", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotEqualTo(Integer value) {
            addCriterion("QtyWarning <>", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThan(Integer value) {
            addCriterion("QtyWarning >", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningGreaterThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning >=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThan(Integer value) {
            addCriterion("QtyWarning <", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningLessThanOrEqualTo(Integer value) {
            addCriterion("QtyWarning <=", value, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningIn(List<Integer> values) {
            addCriterion("QtyWarning in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotIn(List<Integer> values) {
            addCriterion("QtyWarning not in", values, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andQtywarningNotBetween(Integer value1, Integer value2) {
            addCriterion("QtyWarning not between", value1, value2, "qtywarning");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNull() {
            addCriterion("createUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("createUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(String value) {
            addCriterion("createUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(String value) {
            addCriterion("createUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(String value) {
            addCriterion("createUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(String value) {
            addCriterion("createUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(String value) {
            addCriterion("createUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(String value) {
            addCriterion("createUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLike(String value) {
            addCriterion("createUser like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotLike(String value) {
            addCriterion("createUser not like", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<String> values) {
            addCriterion("createUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<String> values) {
            addCriterion("createUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(String value1, String value2) {
            addCriterion("createUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(String value1, String value2) {
            addCriterion("createUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeIsNull() {
            addCriterion("WarnQtyOptCode is null");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeIsNotNull() {
            addCriterion("WarnQtyOptCode is not null");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeEqualTo(Short value) {
            addCriterion("WarnQtyOptCode =", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeNotEqualTo(Short value) {
            addCriterion("WarnQtyOptCode <>", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeGreaterThan(Short value) {
            addCriterion("WarnQtyOptCode >", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeGreaterThanOrEqualTo(Short value) {
            addCriterion("WarnQtyOptCode >=", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeLessThan(Short value) {
            addCriterion("WarnQtyOptCode <", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeLessThanOrEqualTo(Short value) {
            addCriterion("WarnQtyOptCode <=", value, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeIn(List<Short> values) {
            addCriterion("WarnQtyOptCode in", values, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeNotIn(List<Short> values) {
            addCriterion("WarnQtyOptCode not in", values, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeBetween(Short value1, Short value2) {
            addCriterion("WarnQtyOptCode between", value1, value2, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andWarnqtyoptcodeNotBetween(Short value1, Short value2) {
            addCriterion("WarnQtyOptCode not between", value1, value2, "warnqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeIsNull() {
            addCriterion("DemandQtyOptCode is null");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeIsNotNull() {
            addCriterion("DemandQtyOptCode is not null");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeEqualTo(Short value) {
            addCriterion("DemandQtyOptCode =", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeNotEqualTo(Short value) {
            addCriterion("DemandQtyOptCode <>", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeGreaterThan(Short value) {
            addCriterion("DemandQtyOptCode >", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeGreaterThanOrEqualTo(Short value) {
            addCriterion("DemandQtyOptCode >=", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeLessThan(Short value) {
            addCriterion("DemandQtyOptCode <", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeLessThanOrEqualTo(Short value) {
            addCriterion("DemandQtyOptCode <=", value, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeIn(List<Short> values) {
            addCriterion("DemandQtyOptCode in", values, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeNotIn(List<Short> values) {
            addCriterion("DemandQtyOptCode not in", values, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeBetween(Short value1, Short value2) {
            addCriterion("DemandQtyOptCode between", value1, value2, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andDemandqtyoptcodeNotBetween(Short value1, Short value2) {
            addCriterion("DemandQtyOptCode not between", value1, value2, "demandqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andAnswertextIsNull() {
            addCriterion("AnswerText is null");
            return (Criteria) this;
        }

        public Criteria andAnswertextIsNotNull() {
            addCriterion("AnswerText is not null");
            return (Criteria) this;
        }

        public Criteria andAnswertextEqualTo(String value) {
            addCriterion("AnswerText =", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotEqualTo(String value) {
            addCriterion("AnswerText <>", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextGreaterThan(String value) {
            addCriterion("AnswerText >", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextGreaterThanOrEqualTo(String value) {
            addCriterion("AnswerText >=", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLessThan(String value) {
            addCriterion("AnswerText <", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLessThanOrEqualTo(String value) {
            addCriterion("AnswerText <=", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextLike(String value) {
            addCriterion("AnswerText like", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotLike(String value) {
            addCriterion("AnswerText not like", value, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextIn(List<String> values) {
            addCriterion("AnswerText in", values, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotIn(List<String> values) {
            addCriterion("AnswerText not in", values, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextBetween(String value1, String value2) {
            addCriterion("AnswerText between", value1, value2, "answertext");
            return (Criteria) this;
        }

        public Criteria andAnswertextNotBetween(String value1, String value2) {
            addCriterion("AnswerText not between", value1, value2, "answertext");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeIsNull() {
            addCriterion("CancelQtyOptCode is null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeIsNotNull() {
            addCriterion("CancelQtyOptCode is not null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeEqualTo(Short value) {
            addCriterion("CancelQtyOptCode =", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeNotEqualTo(Short value) {
            addCriterion("CancelQtyOptCode <>", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeGreaterThan(Short value) {
            addCriterion("CancelQtyOptCode >", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeGreaterThanOrEqualTo(Short value) {
            addCriterion("CancelQtyOptCode >=", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeLessThan(Short value) {
            addCriterion("CancelQtyOptCode <", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeLessThanOrEqualTo(Short value) {
            addCriterion("CancelQtyOptCode <=", value, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeIn(List<Short> values) {
            addCriterion("CancelQtyOptCode in", values, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeNotIn(List<Short> values) {
            addCriterion("CancelQtyOptCode not in", values, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeBetween(Short value1, Short value2) {
            addCriterion("CancelQtyOptCode between", value1, value2, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andCancelqtyoptcodeNotBetween(Short value1, Short value2) {
            addCriterion("CancelQtyOptCode not between", value1, value2, "cancelqtyoptcode");
            return (Criteria) this;
        }

        public Criteria andOpentoworldIsNull() {
            addCriterion("OpenToWorld is null");
            return (Criteria) this;
        }

        public Criteria andOpentoworldIsNotNull() {
            addCriterion("OpenToWorld is not null");
            return (Criteria) this;
        }

        public Criteria andOpentoworldEqualTo(Short value) {
            addCriterion("OpenToWorld =", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldNotEqualTo(Short value) {
            addCriterion("OpenToWorld <>", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldGreaterThan(Short value) {
            addCriterion("OpenToWorld >", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldGreaterThanOrEqualTo(Short value) {
            addCriterion("OpenToWorld >=", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldLessThan(Short value) {
            addCriterion("OpenToWorld <", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldLessThanOrEqualTo(Short value) {
            addCriterion("OpenToWorld <=", value, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldIn(List<Short> values) {
            addCriterion("OpenToWorld in", values, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldNotIn(List<Short> values) {
            addCriterion("OpenToWorld not in", values, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldBetween(Short value1, Short value2) {
            addCriterion("OpenToWorld between", value1, value2, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andOpentoworldNotBetween(Short value1, Short value2) {
            addCriterion("OpenToWorld not between", value1, value2, "opentoworld");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelIsNull() {
            addCriterion("DelayToCancel is null");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelIsNotNull() {
            addCriterion("DelayToCancel is not null");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelEqualTo(Short value) {
            addCriterion("DelayToCancel =", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelNotEqualTo(Short value) {
            addCriterion("DelayToCancel <>", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelGreaterThan(Short value) {
            addCriterion("DelayToCancel >", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelGreaterThanOrEqualTo(Short value) {
            addCriterion("DelayToCancel >=", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelLessThan(Short value) {
            addCriterion("DelayToCancel <", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelLessThanOrEqualTo(Short value) {
            addCriterion("DelayToCancel <=", value, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelIn(List<Short> values) {
            addCriterion("DelayToCancel in", values, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelNotIn(List<Short> values) {
            addCriterion("DelayToCancel not in", values, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelBetween(Short value1, Short value2) {
            addCriterion("DelayToCancel between", value1, value2, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andDelaytocancelNotBetween(Short value1, Short value2) {
            addCriterion("DelayToCancel not between", value1, value2, "delaytocancel");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNull() {
            addCriterion("ApplyNo is null");
            return (Criteria) this;
        }

        public Criteria andApplynoIsNotNull() {
            addCriterion("ApplyNo is not null");
            return (Criteria) this;
        }

        public Criteria andApplynoEqualTo(String value) {
            addCriterion("ApplyNo =", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotEqualTo(String value) {
            addCriterion("ApplyNo <>", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThan(String value) {
            addCriterion("ApplyNo >", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoGreaterThanOrEqualTo(String value) {
            addCriterion("ApplyNo >=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThan(String value) {
            addCriterion("ApplyNo <", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLessThanOrEqualTo(String value) {
            addCriterion("ApplyNo <=", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoLike(String value) {
            addCriterion("ApplyNo like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotLike(String value) {
            addCriterion("ApplyNo not like", value, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoIn(List<String> values) {
            addCriterion("ApplyNo in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotIn(List<String> values) {
            addCriterion("ApplyNo not in", values, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoBetween(String value1, String value2) {
            addCriterion("ApplyNo between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplynoNotBetween(String value1, String value2) {
            addCriterion("ApplyNo not between", value1, value2, "applyno");
            return (Criteria) this;
        }

        public Criteria andApplytypeIsNull() {
            addCriterion("ApplyType is null");
            return (Criteria) this;
        }

        public Criteria andApplytypeIsNotNull() {
            addCriterion("ApplyType is not null");
            return (Criteria) this;
        }

        public Criteria andApplytypeEqualTo(Short value) {
            addCriterion("ApplyType =", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeNotEqualTo(Short value) {
            addCriterion("ApplyType <>", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeGreaterThan(Short value) {
            addCriterion("ApplyType >", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeGreaterThanOrEqualTo(Short value) {
            addCriterion("ApplyType >=", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeLessThan(Short value) {
            addCriterion("ApplyType <", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeLessThanOrEqualTo(Short value) {
            addCriterion("ApplyType <=", value, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeIn(List<Short> values) {
            addCriterion("ApplyType in", values, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeNotIn(List<Short> values) {
            addCriterion("ApplyType not in", values, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeBetween(Short value1, Short value2) {
            addCriterion("ApplyType between", value1, value2, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplytypeNotBetween(Short value1, Short value2) {
            addCriterion("ApplyType not between", value1, value2, "applytype");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNull() {
            addCriterion("ApplyQty is null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIsNotNull() {
            addCriterion("ApplyQty is not null");
            return (Criteria) this;
        }

        public Criteria andApplyqtyEqualTo(Integer value) {
            addCriterion("ApplyQty =", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotEqualTo(Integer value) {
            addCriterion("ApplyQty <>", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThan(Integer value) {
            addCriterion("ApplyQty >", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty >=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThan(Integer value) {
            addCriterion("ApplyQty <", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyLessThanOrEqualTo(Integer value) {
            addCriterion("ApplyQty <=", value, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyIn(List<Integer> values) {
            addCriterion("ApplyQty in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotIn(List<Integer> values) {
            addCriterion("ApplyQty not in", values, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty between", value1, value2, "applyqty");
            return (Criteria) this;
        }

        public Criteria andApplyqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("ApplyQty not between", value1, value2, "applyqty");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("Reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("Reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("Reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("Reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("Reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("Reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("Reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("Reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("Reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("Reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("Reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("Reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("Reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("Reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNull() {
            addCriterion("ApplyDate is null");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNotNull() {
            addCriterion("ApplyDate is not null");
            return (Criteria) this;
        }

        public Criteria andApplydateEqualTo(Date value) {
            addCriterion("ApplyDate =", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotEqualTo(Date value) {
            addCriterion("ApplyDate <>", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThan(Date value) {
            addCriterion("ApplyDate >", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThanOrEqualTo(Date value) {
            addCriterion("ApplyDate >=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThan(Date value) {
            addCriterion("ApplyDate <", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThanOrEqualTo(Date value) {
            addCriterion("ApplyDate <=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateIn(List<Date> values) {
            addCriterion("ApplyDate in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotIn(List<Date> values) {
            addCriterion("ApplyDate not in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateBetween(Date value1, Date value2) {
            addCriterion("ApplyDate between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotBetween(Date value1, Date value2) {
            addCriterion("ApplyDate not between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIsNull() {
            addCriterion("ApplicantNo is null");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIsNotNull() {
            addCriterion("ApplicantNo is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantnoEqualTo(String value) {
            addCriterion("ApplicantNo =", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotEqualTo(String value) {
            addCriterion("ApplicantNo <>", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoGreaterThan(String value) {
            addCriterion("ApplicantNo >", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoGreaterThanOrEqualTo(String value) {
            addCriterion("ApplicantNo >=", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLessThan(String value) {
            addCriterion("ApplicantNo <", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLessThanOrEqualTo(String value) {
            addCriterion("ApplicantNo <=", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoLike(String value) {
            addCriterion("ApplicantNo like", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotLike(String value) {
            addCriterion("ApplicantNo not like", value, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoIn(List<String> values) {
            addCriterion("ApplicantNo in", values, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotIn(List<String> values) {
            addCriterion("ApplicantNo not in", values, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoBetween(String value1, String value2) {
            addCriterion("ApplicantNo between", value1, value2, "applicantno");
            return (Criteria) this;
        }

        public Criteria andApplicantnoNotBetween(String value1, String value2) {
            addCriterion("ApplicantNo not between", value1, value2, "applicantno");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIsNull() {
            addCriterion("RemainQty is null");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIsNotNull() {
            addCriterion("RemainQty is not null");
            return (Criteria) this;
        }

        public Criteria andRemainqtyEqualTo(Integer value) {
            addCriterion("RemainQty =", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotEqualTo(Integer value) {
            addCriterion("RemainQty <>", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyGreaterThan(Integer value) {
            addCriterion("RemainQty >", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("RemainQty >=", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyLessThan(Integer value) {
            addCriterion("RemainQty <", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyLessThanOrEqualTo(Integer value) {
            addCriterion("RemainQty <=", value, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyIn(List<Integer> values) {
            addCriterion("RemainQty in", values, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotIn(List<Integer> values) {
            addCriterion("RemainQty not in", values, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyBetween(Integer value1, Integer value2) {
            addCriterion("RemainQty between", value1, value2, "remainqty");
            return (Criteria) this;
        }

        public Criteria andRemainqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("RemainQty not between", value1, value2, "remainqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIsNull() {
            addCriterion("CancelQty is null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIsNotNull() {
            addCriterion("CancelQty is not null");
            return (Criteria) this;
        }

        public Criteria andCancelqtyEqualTo(Integer value) {
            addCriterion("CancelQty =", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotEqualTo(Integer value) {
            addCriterion("CancelQty <>", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyGreaterThan(Integer value) {
            addCriterion("CancelQty >", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("CancelQty >=", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyLessThan(Integer value) {
            addCriterion("CancelQty <", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyLessThanOrEqualTo(Integer value) {
            addCriterion("CancelQty <=", value, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyIn(List<Integer> values) {
            addCriterion("CancelQty in", values, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotIn(List<Integer> values) {
            addCriterion("CancelQty not in", values, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyBetween(Integer value1, Integer value2) {
            addCriterion("CancelQty between", value1, value2, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCancelqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("CancelQty not between", value1, value2, "cancelqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyIsNull() {
            addCriterion("CustomerQty is null");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyIsNotNull() {
            addCriterion("CustomerQty is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyEqualTo(Integer value) {
            addCriterion("CustomerQty =", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyNotEqualTo(Integer value) {
            addCriterion("CustomerQty <>", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyGreaterThan(Integer value) {
            addCriterion("CustomerQty >", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("CustomerQty >=", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyLessThan(Integer value) {
            addCriterion("CustomerQty <", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyLessThanOrEqualTo(Integer value) {
            addCriterion("CustomerQty <=", value, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyIn(List<Integer> values) {
            addCriterion("CustomerQty in", values, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyNotIn(List<Integer> values) {
            addCriterion("CustomerQty not in", values, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyBetween(Integer value1, Integer value2) {
            addCriterion("CustomerQty between", value1, value2, "customerqty");
            return (Criteria) this;
        }

        public Criteria andCustomerqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("CustomerQty not between", value1, value2, "customerqty");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateIsNull() {
            addCriterion("RetentionDurationDate is null");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateIsNotNull() {
            addCriterion("RetentionDurationDate is not null");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateEqualTo(Date value) {
            addCriterion("RetentionDurationDate =", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateNotEqualTo(Date value) {
            addCriterion("RetentionDurationDate <>", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateGreaterThan(Date value) {
            addCriterion("RetentionDurationDate >", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateGreaterThanOrEqualTo(Date value) {
            addCriterion("RetentionDurationDate >=", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateLessThan(Date value) {
            addCriterion("RetentionDurationDate <", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateLessThanOrEqualTo(Date value) {
            addCriterion("RetentionDurationDate <=", value, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateIn(List<Date> values) {
            addCriterion("RetentionDurationDate in", values, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateNotIn(List<Date> values) {
            addCriterion("RetentionDurationDate not in", values, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateBetween(Date value1, Date value2) {
            addCriterion("RetentionDurationDate between", value1, value2, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andRetentiondurationdateNotBetween(Date value1, Date value2) {
            addCriterion("RetentionDurationDate not between", value1, value2, "retentiondurationdate");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleIsNull() {
            addCriterion("ExpirationHandle is null");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleIsNotNull() {
            addCriterion("ExpirationHandle is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleEqualTo(String value) {
            addCriterion("ExpirationHandle =", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleNotEqualTo(String value) {
            addCriterion("ExpirationHandle <>", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleGreaterThan(String value) {
            addCriterion("ExpirationHandle >", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleGreaterThanOrEqualTo(String value) {
            addCriterion("ExpirationHandle >=", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleLessThan(String value) {
            addCriterion("ExpirationHandle <", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleLessThanOrEqualTo(String value) {
            addCriterion("ExpirationHandle <=", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleLike(String value) {
            addCriterion("ExpirationHandle like", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleNotLike(String value) {
            addCriterion("ExpirationHandle not like", value, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleIn(List<String> values) {
            addCriterion("ExpirationHandle in", values, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleNotIn(List<String> values) {
            addCriterion("ExpirationHandle not in", values, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleBetween(String value1, String value2) {
            addCriterion("ExpirationHandle between", value1, value2, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andExpirationhandleNotBetween(String value1, String value2) {
            addCriterion("ExpirationHandle not between", value1, value2, "expirationhandle");
            return (Criteria) this;
        }

        public Criteria andRepairqtyIsNull() {
            addCriterion("RepairQty is null");
            return (Criteria) this;
        }

        public Criteria andRepairqtyIsNotNull() {
            addCriterion("RepairQty is not null");
            return (Criteria) this;
        }

        public Criteria andRepairqtyEqualTo(Integer value) {
            addCriterion("RepairQty =", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyNotEqualTo(Integer value) {
            addCriterion("RepairQty <>", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyGreaterThan(Integer value) {
            addCriterion("RepairQty >", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("RepairQty >=", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyLessThan(Integer value) {
            addCriterion("RepairQty <", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyLessThanOrEqualTo(Integer value) {
            addCriterion("RepairQty <=", value, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyIn(List<Integer> values) {
            addCriterion("RepairQty in", values, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyNotIn(List<Integer> values) {
            addCriterion("RepairQty not in", values, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyBetween(Integer value1, Integer value2) {
            addCriterion("RepairQty between", value1, value2, "repairqty");
            return (Criteria) this;
        }

        public Criteria andRepairqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("RepairQty not between", value1, value2, "repairqty");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIsNull() {
            addCriterion("inspectStatus is null");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIsNotNull() {
            addCriterion("inspectStatus is not null");
            return (Criteria) this;
        }

        public Criteria andInspectstatusEqualTo(Short value) {
            addCriterion("inspectStatus =", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotEqualTo(Short value) {
            addCriterion("inspectStatus <>", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusGreaterThan(Short value) {
            addCriterion("inspectStatus >", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusGreaterThanOrEqualTo(Short value) {
            addCriterion("inspectStatus >=", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusLessThan(Short value) {
            addCriterion("inspectStatus <", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusLessThanOrEqualTo(Short value) {
            addCriterion("inspectStatus <=", value, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusIn(List<Short> values) {
            addCriterion("inspectStatus in", values, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotIn(List<Short> values) {
            addCriterion("inspectStatus not in", values, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusBetween(Short value1, Short value2) {
            addCriterion("inspectStatus between", value1, value2, "inspectstatus");
            return (Criteria) this;
        }

        public Criteria andInspectstatusNotBetween(Short value1, Short value2) {
            addCriterion("inspectStatus not between", value1, value2, "inspectstatus");
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
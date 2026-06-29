package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductPhysicsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductPhysicsExample() {
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

        public Criteria andLengthIsNull() {
            addCriterion("length is null");
            return (Criteria) this;
        }

        public Criteria andLengthIsNotNull() {
            addCriterion("length is not null");
            return (Criteria) this;
        }

        public Criteria andLengthEqualTo(BigDecimal value) {
            addCriterion("length =", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotEqualTo(BigDecimal value) {
            addCriterion("length <>", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThan(BigDecimal value) {
            addCriterion("length >", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("length >=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThan(BigDecimal value) {
            addCriterion("length <", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("length <=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthIn(List<BigDecimal> values) {
            addCriterion("length in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotIn(List<BigDecimal> values) {
            addCriterion("length not in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("length between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("length not between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andWidthIsNull() {
            addCriterion("width is null");
            return (Criteria) this;
        }

        public Criteria andWidthIsNotNull() {
            addCriterion("width is not null");
            return (Criteria) this;
        }

        public Criteria andWidthEqualTo(BigDecimal value) {
            addCriterion("width =", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotEqualTo(BigDecimal value) {
            addCriterion("width <>", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThan(BigDecimal value) {
            addCriterion("width >", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("width >=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThan(BigDecimal value) {
            addCriterion("width <", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("width <=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthIn(List<BigDecimal> values) {
            addCriterion("width in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotIn(List<BigDecimal> values) {
            addCriterion("width not in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("width between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("width not between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(BigDecimal value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(BigDecimal value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(BigDecimal value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(BigDecimal value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<BigDecimal> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<BigDecimal> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNull() {
            addCriterion("netweight is null");
            return (Criteria) this;
        }

        public Criteria andNetweightIsNotNull() {
            addCriterion("netweight is not null");
            return (Criteria) this;
        }

        public Criteria andNetweightEqualTo(BigDecimal value) {
            addCriterion("netweight =", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotEqualTo(BigDecimal value) {
            addCriterion("netweight <>", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThan(BigDecimal value) {
            addCriterion("netweight >", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("netweight >=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThan(BigDecimal value) {
            addCriterion("netweight <", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("netweight <=", value, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightIn(List<BigDecimal> values) {
            addCriterion("netweight in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotIn(List<BigDecimal> values) {
            addCriterion("netweight not in", values, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netweight between", value1, value2, "netweight");
            return (Criteria) this;
        }

        public Criteria andNetweightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("netweight not between", value1, value2, "netweight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthIsNull() {
            addCriterion("AfterPackaging_length is null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthIsNotNull() {
            addCriterion("AfterPackaging_length is not null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_length =", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthNotEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_length <>", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthGreaterThan(BigDecimal value) {
            addCriterion("AfterPackaging_length >", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_length >=", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthLessThan(BigDecimal value) {
            addCriterion("AfterPackaging_length <", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_length <=", value, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_length in", values, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthNotIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_length not in", values, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_length between", value1, value2, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_length not between", value1, value2, "afterpackagingLength");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthIsNull() {
            addCriterion("AfterPackaging_width is null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthIsNotNull() {
            addCriterion("AfterPackaging_width is not null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_width =", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthNotEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_width <>", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthGreaterThan(BigDecimal value) {
            addCriterion("AfterPackaging_width >", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_width >=", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthLessThan(BigDecimal value) {
            addCriterion("AfterPackaging_width <", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_width <=", value, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_width in", values, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthNotIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_width not in", values, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_width between", value1, value2, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingWidthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_width not between", value1, value2, "afterpackagingWidth");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightIsNull() {
            addCriterion("AfterPackaging_height is null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightIsNotNull() {
            addCriterion("AfterPackaging_height is not null");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_height =", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightNotEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_height <>", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightGreaterThan(BigDecimal value) {
            addCriterion("AfterPackaging_height >", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_height >=", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightLessThan(BigDecimal value) {
            addCriterion("AfterPackaging_height <", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AfterPackaging_height <=", value, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_height in", values, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightNotIn(List<BigDecimal> values) {
            addCriterion("AfterPackaging_height not in", values, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_height between", value1, value2, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andAfterpackagingHeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AfterPackaging_height not between", value1, value2, "afterpackagingHeight");
            return (Criteria) this;
        }

        public Criteria andCyldiameterIsNull() {
            addCriterion("CylDiameter is null");
            return (Criteria) this;
        }

        public Criteria andCyldiameterIsNotNull() {
            addCriterion("CylDiameter is not null");
            return (Criteria) this;
        }

        public Criteria andCyldiameterEqualTo(BigDecimal value) {
            addCriterion("CylDiameter =", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterNotEqualTo(BigDecimal value) {
            addCriterion("CylDiameter <>", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterGreaterThan(BigDecimal value) {
            addCriterion("CylDiameter >", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CylDiameter >=", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterLessThan(BigDecimal value) {
            addCriterion("CylDiameter <", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CylDiameter <=", value, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterIn(List<BigDecimal> values) {
            addCriterion("CylDiameter in", values, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterNotIn(List<BigDecimal> values) {
            addCriterion("CylDiameter not in", values, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CylDiameter between", value1, value2, "cyldiameter");
            return (Criteria) this;
        }

        public Criteria andCyldiameterNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CylDiameter not between", value1, value2, "cyldiameter");
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
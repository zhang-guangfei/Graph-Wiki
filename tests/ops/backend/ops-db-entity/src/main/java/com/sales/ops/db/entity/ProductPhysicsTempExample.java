package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductPhysicsTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductPhysicsTempExample() {
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

        public Criteria andCylminlenIsNull() {
            addCriterion("Cylminlen is null");
            return (Criteria) this;
        }

        public Criteria andCylminlenIsNotNull() {
            addCriterion("Cylminlen is not null");
            return (Criteria) this;
        }

        public Criteria andCylminlenEqualTo(BigDecimal value) {
            addCriterion("Cylminlen =", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenNotEqualTo(BigDecimal value) {
            addCriterion("Cylminlen <>", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenGreaterThan(BigDecimal value) {
            addCriterion("Cylminlen >", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Cylminlen >=", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenLessThan(BigDecimal value) {
            addCriterion("Cylminlen <", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Cylminlen <=", value, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenIn(List<BigDecimal> values) {
            addCriterion("Cylminlen in", values, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenNotIn(List<BigDecimal> values) {
            addCriterion("Cylminlen not in", values, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cylminlen between", value1, value2, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylminlenNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cylminlen not between", value1, value2, "cylminlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenIsNull() {
            addCriterion("Cylmaxlen is null");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenIsNotNull() {
            addCriterion("Cylmaxlen is not null");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenEqualTo(BigDecimal value) {
            addCriterion("Cylmaxlen =", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenNotEqualTo(BigDecimal value) {
            addCriterion("Cylmaxlen <>", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenGreaterThan(BigDecimal value) {
            addCriterion("Cylmaxlen >", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Cylmaxlen >=", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenLessThan(BigDecimal value) {
            addCriterion("Cylmaxlen <", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Cylmaxlen <=", value, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenIn(List<BigDecimal> values) {
            addCriterion("Cylmaxlen in", values, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenNotIn(List<BigDecimal> values) {
            addCriterion("Cylmaxlen not in", values, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cylmaxlen between", value1, value2, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andCylmaxlenNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cylmaxlen not between", value1, value2, "cylmaxlen");
            return (Criteria) this;
        }

        public Criteria andMsizeminIsNull() {
            addCriterion("MSizeMin is null");
            return (Criteria) this;
        }

        public Criteria andMsizeminIsNotNull() {
            addCriterion("MSizeMin is not null");
            return (Criteria) this;
        }

        public Criteria andMsizeminEqualTo(BigDecimal value) {
            addCriterion("MSizeMin =", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminNotEqualTo(BigDecimal value) {
            addCriterion("MSizeMin <>", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminGreaterThan(BigDecimal value) {
            addCriterion("MSizeMin >", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MSizeMin >=", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminLessThan(BigDecimal value) {
            addCriterion("MSizeMin <", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MSizeMin <=", value, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminIn(List<BigDecimal> values) {
            addCriterion("MSizeMin in", values, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminNotIn(List<BigDecimal> values) {
            addCriterion("MSizeMin not in", values, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSizeMin between", value1, value2, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizeminNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSizeMin not between", value1, value2, "msizemin");
            return (Criteria) this;
        }

        public Criteria andMsizemaxIsNull() {
            addCriterion("MSizeMax is null");
            return (Criteria) this;
        }

        public Criteria andMsizemaxIsNotNull() {
            addCriterion("MSizeMax is not null");
            return (Criteria) this;
        }

        public Criteria andMsizemaxEqualTo(BigDecimal value) {
            addCriterion("MSizeMax =", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxNotEqualTo(BigDecimal value) {
            addCriterion("MSizeMax <>", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxGreaterThan(BigDecimal value) {
            addCriterion("MSizeMax >", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MSizeMax >=", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxLessThan(BigDecimal value) {
            addCriterion("MSizeMax <", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MSizeMax <=", value, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxIn(List<BigDecimal> values) {
            addCriterion("MSizeMax in", values, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxNotIn(List<BigDecimal> values) {
            addCriterion("MSizeMax not in", values, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSizeMax between", value1, value2, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMsizemaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSizeMax not between", value1, value2, "msizemax");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNull() {
            addCriterion("MidSize is null");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNotNull() {
            addCriterion("MidSize is not null");
            return (Criteria) this;
        }

        public Criteria andMidsizeEqualTo(String value) {
            addCriterion("MidSize =", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotEqualTo(String value) {
            addCriterion("MidSize <>", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThan(String value) {
            addCriterion("MidSize >", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThanOrEqualTo(String value) {
            addCriterion("MidSize >=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThan(String value) {
            addCriterion("MidSize <", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThanOrEqualTo(String value) {
            addCriterion("MidSize <=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLike(String value) {
            addCriterion("MidSize like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotLike(String value) {
            addCriterion("MidSize not like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeIn(List<String> values) {
            addCriterion("MidSize in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotIn(List<String> values) {
            addCriterion("MidSize not in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeBetween(String value1, String value2) {
            addCriterion("MidSize between", value1, value2, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotBetween(String value1, String value2) {
            addCriterion("MidSize not between", value1, value2, "midsize");
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
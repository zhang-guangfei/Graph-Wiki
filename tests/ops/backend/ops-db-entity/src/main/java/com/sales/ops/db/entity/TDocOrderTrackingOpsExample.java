package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TDocOrderTrackingOpsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TDocOrderTrackingOpsExample() {
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

        public Criteria andTrackingseqnoIsNull() {
            addCriterion("trackingSeqNo is null");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoIsNotNull() {
            addCriterion("trackingSeqNo is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoEqualTo(String value) {
            addCriterion("trackingSeqNo =", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoNotEqualTo(String value) {
            addCriterion("trackingSeqNo <>", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoGreaterThan(String value) {
            addCriterion("trackingSeqNo >", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoGreaterThanOrEqualTo(String value) {
            addCriterion("trackingSeqNo >=", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoLessThan(String value) {
            addCriterion("trackingSeqNo <", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoLessThanOrEqualTo(String value) {
            addCriterion("trackingSeqNo <=", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoLike(String value) {
            addCriterion("trackingSeqNo like", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoNotLike(String value) {
            addCriterion("trackingSeqNo not like", value, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoIn(List<String> values) {
            addCriterion("trackingSeqNo in", values, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoNotIn(List<String> values) {
            addCriterion("trackingSeqNo not in", values, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoBetween(String value1, String value2) {
            addCriterion("trackingSeqNo between", value1, value2, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andTrackingseqnoNotBetween(String value1, String value2) {
            addCriterion("trackingSeqNo not between", value1, value2, "trackingseqno");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNull() {
            addCriterion("organizationid is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNotNull() {
            addCriterion("organizationid is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidEqualTo(String value) {
            addCriterion("organizationid =", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotEqualTo(String value) {
            addCriterion("organizationid <>", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThan(String value) {
            addCriterion("organizationid >", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThanOrEqualTo(String value) {
            addCriterion("organizationid >=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThan(String value) {
            addCriterion("organizationid <", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThanOrEqualTo(String value) {
            addCriterion("organizationid <=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLike(String value) {
            addCriterion("organizationid like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotLike(String value) {
            addCriterion("organizationid not like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIn(List<String> values) {
            addCriterion("organizationid in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotIn(List<String> values) {
            addCriterion("organizationid not in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidBetween(String value1, String value2) {
            addCriterion("organizationid between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotBetween(String value1, String value2) {
            addCriterion("organizationid not between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderno is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderno is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderno =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderno <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderno >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderno >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderno <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderno <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderno like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderno not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderno in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderno not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderno between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderno not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andTasknoIsNull() {
            addCriterion("taskno is null");
            return (Criteria) this;
        }

        public Criteria andTasknoIsNotNull() {
            addCriterion("taskno is not null");
            return (Criteria) this;
        }

        public Criteria andTasknoEqualTo(String value) {
            addCriterion("taskno =", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoNotEqualTo(String value) {
            addCriterion("taskno <>", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoGreaterThan(String value) {
            addCriterion("taskno >", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoGreaterThanOrEqualTo(String value) {
            addCriterion("taskno >=", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoLessThan(String value) {
            addCriterion("taskno <", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoLessThanOrEqualTo(String value) {
            addCriterion("taskno <=", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoLike(String value) {
            addCriterion("taskno like", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoNotLike(String value) {
            addCriterion("taskno not like", value, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoIn(List<String> values) {
            addCriterion("taskno in", values, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoNotIn(List<String> values) {
            addCriterion("taskno not in", values, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoBetween(String value1, String value2) {
            addCriterion("taskno between", value1, value2, "taskno");
            return (Criteria) this;
        }

        public Criteria andTasknoNotBetween(String value1, String value2) {
            addCriterion("taskno not between", value1, value2, "taskno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoIsNull() {
            addCriterion("waybillno is null");
            return (Criteria) this;
        }

        public Criteria andWaybillnoIsNotNull() {
            addCriterion("waybillno is not null");
            return (Criteria) this;
        }

        public Criteria andWaybillnoEqualTo(String value) {
            addCriterion("waybillno =", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoNotEqualTo(String value) {
            addCriterion("waybillno <>", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoGreaterThan(String value) {
            addCriterion("waybillno >", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoGreaterThanOrEqualTo(String value) {
            addCriterion("waybillno >=", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoLessThan(String value) {
            addCriterion("waybillno <", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoLessThanOrEqualTo(String value) {
            addCriterion("waybillno <=", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoLike(String value) {
            addCriterion("waybillno like", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoNotLike(String value) {
            addCriterion("waybillno not like", value, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoIn(List<String> values) {
            addCriterion("waybillno in", values, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoNotIn(List<String> values) {
            addCriterion("waybillno not in", values, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoBetween(String value1, String value2) {
            addCriterion("waybillno between", value1, value2, "waybillno");
            return (Criteria) this;
        }

        public Criteria andWaybillnoNotBetween(String value1, String value2) {
            addCriterion("waybillno not between", value1, value2, "waybillno");
            return (Criteria) this;
        }

        public Criteria andExpressnoIsNull() {
            addCriterion("expressno is null");
            return (Criteria) this;
        }

        public Criteria andExpressnoIsNotNull() {
            addCriterion("expressno is not null");
            return (Criteria) this;
        }

        public Criteria andExpressnoEqualTo(String value) {
            addCriterion("expressno =", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotEqualTo(String value) {
            addCriterion("expressno <>", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoGreaterThan(String value) {
            addCriterion("expressno >", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoGreaterThanOrEqualTo(String value) {
            addCriterion("expressno >=", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLessThan(String value) {
            addCriterion("expressno <", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLessThanOrEqualTo(String value) {
            addCriterion("expressno <=", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoLike(String value) {
            addCriterion("expressno like", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotLike(String value) {
            addCriterion("expressno not like", value, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoIn(List<String> values) {
            addCriterion("expressno in", values, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotIn(List<String> values) {
            addCriterion("expressno not in", values, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoBetween(String value1, String value2) {
            addCriterion("expressno between", value1, value2, "expressno");
            return (Criteria) this;
        }

        public Criteria andExpressnoNotBetween(String value1, String value2) {
            addCriterion("expressno not between", value1, value2, "expressno");
            return (Criteria) this;
        }

        public Criteria andShowstatusIsNull() {
            addCriterion("showStatus is null");
            return (Criteria) this;
        }

        public Criteria andShowstatusIsNotNull() {
            addCriterion("showStatus is not null");
            return (Criteria) this;
        }

        public Criteria andShowstatusEqualTo(String value) {
            addCriterion("showStatus =", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusNotEqualTo(String value) {
            addCriterion("showStatus <>", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusGreaterThan(String value) {
            addCriterion("showStatus >", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusGreaterThanOrEqualTo(String value) {
            addCriterion("showStatus >=", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusLessThan(String value) {
            addCriterion("showStatus <", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusLessThanOrEqualTo(String value) {
            addCriterion("showStatus <=", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusLike(String value) {
            addCriterion("showStatus like", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusNotLike(String value) {
            addCriterion("showStatus not like", value, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusIn(List<String> values) {
            addCriterion("showStatus in", values, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusNotIn(List<String> values) {
            addCriterion("showStatus not in", values, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusBetween(String value1, String value2) {
            addCriterion("showStatus between", value1, value2, "showstatus");
            return (Criteria) this;
        }

        public Criteria andShowstatusNotBetween(String value1, String value2) {
            addCriterion("showStatus not between", value1, value2, "showstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeIsNull() {
            addCriterion("trackingtime is null");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeIsNotNull() {
            addCriterion("trackingtime is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeEqualTo(Date value) {
            addCriterion("trackingtime =", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeNotEqualTo(Date value) {
            addCriterion("trackingtime <>", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeGreaterThan(Date value) {
            addCriterion("trackingtime >", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trackingtime >=", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeLessThan(Date value) {
            addCriterion("trackingtime <", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeLessThanOrEqualTo(Date value) {
            addCriterion("trackingtime <=", value, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeIn(List<Date> values) {
            addCriterion("trackingtime in", values, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeNotIn(List<Date> values) {
            addCriterion("trackingtime not in", values, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeBetween(Date value1, Date value2) {
            addCriterion("trackingtime between", value1, value2, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andTrackingtimeNotBetween(Date value1, Date value2) {
            addCriterion("trackingtime not between", value1, value2, "trackingtime");
            return (Criteria) this;
        }

        public Criteria andMilestoneIsNull() {
            addCriterion("milestone is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneIsNotNull() {
            addCriterion("milestone is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneEqualTo(String value) {
            addCriterion("milestone =", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneNotEqualTo(String value) {
            addCriterion("milestone <>", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneGreaterThan(String value) {
            addCriterion("milestone >", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneGreaterThanOrEqualTo(String value) {
            addCriterion("milestone >=", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneLessThan(String value) {
            addCriterion("milestone <", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneLessThanOrEqualTo(String value) {
            addCriterion("milestone <=", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneLike(String value) {
            addCriterion("milestone like", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneNotLike(String value) {
            addCriterion("milestone not like", value, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneIn(List<String> values) {
            addCriterion("milestone in", values, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneNotIn(List<String> values) {
            addCriterion("milestone not in", values, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneBetween(String value1, String value2) {
            addCriterion("milestone between", value1, value2, "milestone");
            return (Criteria) this;
        }

        public Criteria andMilestoneNotBetween(String value1, String value2) {
            addCriterion("milestone not between", value1, value2, "milestone");
            return (Criteria) this;
        }

        public Criteria andShowflagIsNull() {
            addCriterion("showflag is null");
            return (Criteria) this;
        }

        public Criteria andShowflagIsNotNull() {
            addCriterion("showflag is not null");
            return (Criteria) this;
        }

        public Criteria andShowflagEqualTo(String value) {
            addCriterion("showflag =", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagNotEqualTo(String value) {
            addCriterion("showflag <>", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagGreaterThan(String value) {
            addCriterion("showflag >", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagGreaterThanOrEqualTo(String value) {
            addCriterion("showflag >=", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagLessThan(String value) {
            addCriterion("showflag <", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagLessThanOrEqualTo(String value) {
            addCriterion("showflag <=", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagLike(String value) {
            addCriterion("showflag like", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagNotLike(String value) {
            addCriterion("showflag not like", value, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagIn(List<String> values) {
            addCriterion("showflag in", values, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagNotIn(List<String> values) {
            addCriterion("showflag not in", values, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagBetween(String value1, String value2) {
            addCriterion("showflag between", value1, value2, "showflag");
            return (Criteria) this;
        }

        public Criteria andShowflagNotBetween(String value1, String value2) {
            addCriterion("showflag not between", value1, value2, "showflag");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNull() {
            addCriterion("branchid is null");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNotNull() {
            addCriterion("branchid is not null");
            return (Criteria) this;
        }

        public Criteria andBranchidEqualTo(String value) {
            addCriterion("branchid =", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotEqualTo(String value) {
            addCriterion("branchid <>", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThan(String value) {
            addCriterion("branchid >", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThanOrEqualTo(String value) {
            addCriterion("branchid >=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThan(String value) {
            addCriterion("branchid <", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThanOrEqualTo(String value) {
            addCriterion("branchid <=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLike(String value) {
            addCriterion("branchid like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotLike(String value) {
            addCriterion("branchid not like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidIn(List<String> values) {
            addCriterion("branchid in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotIn(List<String> values) {
            addCriterion("branchid not in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidBetween(String value1, String value2) {
            addCriterion("branchid between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotBetween(String value1, String value2) {
            addCriterion("branchid not between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andCarrieridIsNull() {
            addCriterion("carrierid is null");
            return (Criteria) this;
        }

        public Criteria andCarrieridIsNotNull() {
            addCriterion("carrierid is not null");
            return (Criteria) this;
        }

        public Criteria andCarrieridEqualTo(String value) {
            addCriterion("carrierid =", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotEqualTo(String value) {
            addCriterion("carrierid <>", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridGreaterThan(String value) {
            addCriterion("carrierid >", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridGreaterThanOrEqualTo(String value) {
            addCriterion("carrierid >=", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLessThan(String value) {
            addCriterion("carrierid <", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLessThanOrEqualTo(String value) {
            addCriterion("carrierid <=", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridLike(String value) {
            addCriterion("carrierid like", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotLike(String value) {
            addCriterion("carrierid not like", value, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridIn(List<String> values) {
            addCriterion("carrierid in", values, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotIn(List<String> values) {
            addCriterion("carrierid not in", values, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridBetween(String value1, String value2) {
            addCriterion("carrierid between", value1, value2, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarrieridNotBetween(String value1, String value2) {
            addCriterion("carrierid not between", value1, value2, "carrierid");
            return (Criteria) this;
        }

        public Criteria andCarriernameIsNull() {
            addCriterion("carrierName is null");
            return (Criteria) this;
        }

        public Criteria andCarriernameIsNotNull() {
            addCriterion("carrierName is not null");
            return (Criteria) this;
        }

        public Criteria andCarriernameEqualTo(String value) {
            addCriterion("carrierName =", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameNotEqualTo(String value) {
            addCriterion("carrierName <>", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameGreaterThan(String value) {
            addCriterion("carrierName >", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameGreaterThanOrEqualTo(String value) {
            addCriterion("carrierName >=", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameLessThan(String value) {
            addCriterion("carrierName <", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameLessThanOrEqualTo(String value) {
            addCriterion("carrierName <=", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameLike(String value) {
            addCriterion("carrierName like", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameNotLike(String value) {
            addCriterion("carrierName not like", value, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameIn(List<String> values) {
            addCriterion("carrierName in", values, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameNotIn(List<String> values) {
            addCriterion("carrierName not in", values, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameBetween(String value1, String value2) {
            addCriterion("carrierName between", value1, value2, "carriername");
            return (Criteria) this;
        }

        public Criteria andCarriernameNotBetween(String value1, String value2) {
            addCriterion("carrierName not between", value1, value2, "carriername");
            return (Criteria) this;
        }

        public Criteria andVehicleidIsNull() {
            addCriterion("vehicleid is null");
            return (Criteria) this;
        }

        public Criteria andVehicleidIsNotNull() {
            addCriterion("vehicleid is not null");
            return (Criteria) this;
        }

        public Criteria andVehicleidEqualTo(String value) {
            addCriterion("vehicleid =", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidNotEqualTo(String value) {
            addCriterion("vehicleid <>", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidGreaterThan(String value) {
            addCriterion("vehicleid >", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidGreaterThanOrEqualTo(String value) {
            addCriterion("vehicleid >=", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidLessThan(String value) {
            addCriterion("vehicleid <", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidLessThanOrEqualTo(String value) {
            addCriterion("vehicleid <=", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidLike(String value) {
            addCriterion("vehicleid like", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidNotLike(String value) {
            addCriterion("vehicleid not like", value, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidIn(List<String> values) {
            addCriterion("vehicleid in", values, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidNotIn(List<String> values) {
            addCriterion("vehicleid not in", values, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidBetween(String value1, String value2) {
            addCriterion("vehicleid between", value1, value2, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andVehicleidNotBetween(String value1, String value2) {
            addCriterion("vehicleid not between", value1, value2, "vehicleid");
            return (Criteria) this;
        }

        public Criteria andDriveridIsNull() {
            addCriterion("driverid is null");
            return (Criteria) this;
        }

        public Criteria andDriveridIsNotNull() {
            addCriterion("driverid is not null");
            return (Criteria) this;
        }

        public Criteria andDriveridEqualTo(String value) {
            addCriterion("driverid =", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridNotEqualTo(String value) {
            addCriterion("driverid <>", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridGreaterThan(String value) {
            addCriterion("driverid >", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridGreaterThanOrEqualTo(String value) {
            addCriterion("driverid >=", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridLessThan(String value) {
            addCriterion("driverid <", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridLessThanOrEqualTo(String value) {
            addCriterion("driverid <=", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridLike(String value) {
            addCriterion("driverid like", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridNotLike(String value) {
            addCriterion("driverid not like", value, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridIn(List<String> values) {
            addCriterion("driverid in", values, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridNotIn(List<String> values) {
            addCriterion("driverid not in", values, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridBetween(String value1, String value2) {
            addCriterion("driverid between", value1, value2, "driverid");
            return (Criteria) this;
        }

        public Criteria andDriveridNotBetween(String value1, String value2) {
            addCriterion("driverid not between", value1, value2, "driverid");
            return (Criteria) this;
        }

        public Criteria andDrivernameIsNull() {
            addCriterion("drivername is null");
            return (Criteria) this;
        }

        public Criteria andDrivernameIsNotNull() {
            addCriterion("drivername is not null");
            return (Criteria) this;
        }

        public Criteria andDrivernameEqualTo(String value) {
            addCriterion("drivername =", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotEqualTo(String value) {
            addCriterion("drivername <>", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameGreaterThan(String value) {
            addCriterion("drivername >", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameGreaterThanOrEqualTo(String value) {
            addCriterion("drivername >=", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLessThan(String value) {
            addCriterion("drivername <", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLessThanOrEqualTo(String value) {
            addCriterion("drivername <=", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLike(String value) {
            addCriterion("drivername like", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotLike(String value) {
            addCriterion("drivername not like", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameIn(List<String> values) {
            addCriterion("drivername in", values, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotIn(List<String> values) {
            addCriterion("drivername not in", values, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameBetween(String value1, String value2) {
            addCriterion("drivername between", value1, value2, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotBetween(String value1, String value2) {
            addCriterion("drivername not between", value1, value2, "drivername");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryIsNull() {
            addCriterion("trackingcountry is null");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryIsNotNull() {
            addCriterion("trackingcountry is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryEqualTo(String value) {
            addCriterion("trackingcountry =", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryNotEqualTo(String value) {
            addCriterion("trackingcountry <>", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryGreaterThan(String value) {
            addCriterion("trackingcountry >", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryGreaterThanOrEqualTo(String value) {
            addCriterion("trackingcountry >=", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryLessThan(String value) {
            addCriterion("trackingcountry <", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryLessThanOrEqualTo(String value) {
            addCriterion("trackingcountry <=", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryLike(String value) {
            addCriterion("trackingcountry like", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryNotLike(String value) {
            addCriterion("trackingcountry not like", value, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryIn(List<String> values) {
            addCriterion("trackingcountry in", values, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryNotIn(List<String> values) {
            addCriterion("trackingcountry not in", values, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryBetween(String value1, String value2) {
            addCriterion("trackingcountry between", value1, value2, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingcountryNotBetween(String value1, String value2) {
            addCriterion("trackingcountry not between", value1, value2, "trackingcountry");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceIsNull() {
            addCriterion("trackingprovince is null");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceIsNotNull() {
            addCriterion("trackingprovince is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceEqualTo(String value) {
            addCriterion("trackingprovince =", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceNotEqualTo(String value) {
            addCriterion("trackingprovince <>", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceGreaterThan(String value) {
            addCriterion("trackingprovince >", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceGreaterThanOrEqualTo(String value) {
            addCriterion("trackingprovince >=", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceLessThan(String value) {
            addCriterion("trackingprovince <", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceLessThanOrEqualTo(String value) {
            addCriterion("trackingprovince <=", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceLike(String value) {
            addCriterion("trackingprovince like", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceNotLike(String value) {
            addCriterion("trackingprovince not like", value, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceIn(List<String> values) {
            addCriterion("trackingprovince in", values, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceNotIn(List<String> values) {
            addCriterion("trackingprovince not in", values, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceBetween(String value1, String value2) {
            addCriterion("trackingprovince between", value1, value2, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingprovinceNotBetween(String value1, String value2) {
            addCriterion("trackingprovince not between", value1, value2, "trackingprovince");
            return (Criteria) this;
        }

        public Criteria andTrackingcityIsNull() {
            addCriterion("trackingcity is null");
            return (Criteria) this;
        }

        public Criteria andTrackingcityIsNotNull() {
            addCriterion("trackingcity is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingcityEqualTo(String value) {
            addCriterion("trackingcity =", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityNotEqualTo(String value) {
            addCriterion("trackingcity <>", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityGreaterThan(String value) {
            addCriterion("trackingcity >", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityGreaterThanOrEqualTo(String value) {
            addCriterion("trackingcity >=", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityLessThan(String value) {
            addCriterion("trackingcity <", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityLessThanOrEqualTo(String value) {
            addCriterion("trackingcity <=", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityLike(String value) {
            addCriterion("trackingcity like", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityNotLike(String value) {
            addCriterion("trackingcity not like", value, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityIn(List<String> values) {
            addCriterion("trackingcity in", values, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityNotIn(List<String> values) {
            addCriterion("trackingcity not in", values, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityBetween(String value1, String value2) {
            addCriterion("trackingcity between", value1, value2, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingcityNotBetween(String value1, String value2) {
            addCriterion("trackingcity not between", value1, value2, "trackingcity");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictIsNull() {
            addCriterion("trackingdistrict is null");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictIsNotNull() {
            addCriterion("trackingdistrict is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictEqualTo(String value) {
            addCriterion("trackingdistrict =", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictNotEqualTo(String value) {
            addCriterion("trackingdistrict <>", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictGreaterThan(String value) {
            addCriterion("trackingdistrict >", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictGreaterThanOrEqualTo(String value) {
            addCriterion("trackingdistrict >=", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictLessThan(String value) {
            addCriterion("trackingdistrict <", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictLessThanOrEqualTo(String value) {
            addCriterion("trackingdistrict <=", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictLike(String value) {
            addCriterion("trackingdistrict like", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictNotLike(String value) {
            addCriterion("trackingdistrict not like", value, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictIn(List<String> values) {
            addCriterion("trackingdistrict in", values, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictNotIn(List<String> values) {
            addCriterion("trackingdistrict not in", values, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictBetween(String value1, String value2) {
            addCriterion("trackingdistrict between", value1, value2, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingdistrictNotBetween(String value1, String value2) {
            addCriterion("trackingdistrict not between", value1, value2, "trackingdistrict");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetIsNull() {
            addCriterion("trackingstreet is null");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetIsNotNull() {
            addCriterion("trackingstreet is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetEqualTo(String value) {
            addCriterion("trackingstreet =", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetNotEqualTo(String value) {
            addCriterion("trackingstreet <>", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetGreaterThan(String value) {
            addCriterion("trackingstreet >", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetGreaterThanOrEqualTo(String value) {
            addCriterion("trackingstreet >=", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetLessThan(String value) {
            addCriterion("trackingstreet <", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetLessThanOrEqualTo(String value) {
            addCriterion("trackingstreet <=", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetLike(String value) {
            addCriterion("trackingstreet like", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetNotLike(String value) {
            addCriterion("trackingstreet not like", value, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetIn(List<String> values) {
            addCriterion("trackingstreet in", values, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetNotIn(List<String> values) {
            addCriterion("trackingstreet not in", values, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetBetween(String value1, String value2) {
            addCriterion("trackingstreet between", value1, value2, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andTrackingstreetNotBetween(String value1, String value2) {
            addCriterion("trackingstreet not between", value1, value2, "trackingstreet");
            return (Criteria) this;
        }

        public Criteria andPositionidIsNull() {
            addCriterion("positionid is null");
            return (Criteria) this;
        }

        public Criteria andPositionidIsNotNull() {
            addCriterion("positionid is not null");
            return (Criteria) this;
        }

        public Criteria andPositionidEqualTo(String value) {
            addCriterion("positionid =", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotEqualTo(String value) {
            addCriterion("positionid <>", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidGreaterThan(String value) {
            addCriterion("positionid >", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidGreaterThanOrEqualTo(String value) {
            addCriterion("positionid >=", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidLessThan(String value) {
            addCriterion("positionid <", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidLessThanOrEqualTo(String value) {
            addCriterion("positionid <=", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidLike(String value) {
            addCriterion("positionid like", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotLike(String value) {
            addCriterion("positionid not like", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidIn(List<String> values) {
            addCriterion("positionid in", values, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotIn(List<String> values) {
            addCriterion("positionid not in", values, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidBetween(String value1, String value2) {
            addCriterion("positionid between", value1, value2, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotBetween(String value1, String value2) {
            addCriterion("positionid not between", value1, value2, "positionid");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(BigDecimal value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(BigDecimal value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(BigDecimal value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<BigDecimal> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(BigDecimal value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(BigDecimal value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(BigDecimal value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<BigDecimal> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andUdf01IsNull() {
            addCriterion("udf01 is null");
            return (Criteria) this;
        }

        public Criteria andUdf01IsNotNull() {
            addCriterion("udf01 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf01EqualTo(String value) {
            addCriterion("udf01 =", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01NotEqualTo(String value) {
            addCriterion("udf01 <>", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01GreaterThan(String value) {
            addCriterion("udf01 >", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01GreaterThanOrEqualTo(String value) {
            addCriterion("udf01 >=", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01LessThan(String value) {
            addCriterion("udf01 <", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01LessThanOrEqualTo(String value) {
            addCriterion("udf01 <=", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01Like(String value) {
            addCriterion("udf01 like", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01NotLike(String value) {
            addCriterion("udf01 not like", value, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01In(List<String> values) {
            addCriterion("udf01 in", values, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01NotIn(List<String> values) {
            addCriterion("udf01 not in", values, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01Between(String value1, String value2) {
            addCriterion("udf01 between", value1, value2, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf01NotBetween(String value1, String value2) {
            addCriterion("udf01 not between", value1, value2, "udf01");
            return (Criteria) this;
        }

        public Criteria andUdf02IsNull() {
            addCriterion("udf02 is null");
            return (Criteria) this;
        }

        public Criteria andUdf02IsNotNull() {
            addCriterion("udf02 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf02EqualTo(String value) {
            addCriterion("udf02 =", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02NotEqualTo(String value) {
            addCriterion("udf02 <>", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02GreaterThan(String value) {
            addCriterion("udf02 >", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02GreaterThanOrEqualTo(String value) {
            addCriterion("udf02 >=", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02LessThan(String value) {
            addCriterion("udf02 <", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02LessThanOrEqualTo(String value) {
            addCriterion("udf02 <=", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02Like(String value) {
            addCriterion("udf02 like", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02NotLike(String value) {
            addCriterion("udf02 not like", value, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02In(List<String> values) {
            addCriterion("udf02 in", values, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02NotIn(List<String> values) {
            addCriterion("udf02 not in", values, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02Between(String value1, String value2) {
            addCriterion("udf02 between", value1, value2, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf02NotBetween(String value1, String value2) {
            addCriterion("udf02 not between", value1, value2, "udf02");
            return (Criteria) this;
        }

        public Criteria andUdf03IsNull() {
            addCriterion("udf03 is null");
            return (Criteria) this;
        }

        public Criteria andUdf03IsNotNull() {
            addCriterion("udf03 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf03EqualTo(String value) {
            addCriterion("udf03 =", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03NotEqualTo(String value) {
            addCriterion("udf03 <>", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03GreaterThan(String value) {
            addCriterion("udf03 >", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03GreaterThanOrEqualTo(String value) {
            addCriterion("udf03 >=", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03LessThan(String value) {
            addCriterion("udf03 <", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03LessThanOrEqualTo(String value) {
            addCriterion("udf03 <=", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03Like(String value) {
            addCriterion("udf03 like", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03NotLike(String value) {
            addCriterion("udf03 not like", value, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03In(List<String> values) {
            addCriterion("udf03 in", values, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03NotIn(List<String> values) {
            addCriterion("udf03 not in", values, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03Between(String value1, String value2) {
            addCriterion("udf03 between", value1, value2, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf03NotBetween(String value1, String value2) {
            addCriterion("udf03 not between", value1, value2, "udf03");
            return (Criteria) this;
        }

        public Criteria andUdf04IsNull() {
            addCriterion("udf04 is null");
            return (Criteria) this;
        }

        public Criteria andUdf04IsNotNull() {
            addCriterion("udf04 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf04EqualTo(String value) {
            addCriterion("udf04 =", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04NotEqualTo(String value) {
            addCriterion("udf04 <>", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04GreaterThan(String value) {
            addCriterion("udf04 >", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04GreaterThanOrEqualTo(String value) {
            addCriterion("udf04 >=", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04LessThan(String value) {
            addCriterion("udf04 <", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04LessThanOrEqualTo(String value) {
            addCriterion("udf04 <=", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04Like(String value) {
            addCriterion("udf04 like", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04NotLike(String value) {
            addCriterion("udf04 not like", value, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04In(List<String> values) {
            addCriterion("udf04 in", values, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04NotIn(List<String> values) {
            addCriterion("udf04 not in", values, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04Between(String value1, String value2) {
            addCriterion("udf04 between", value1, value2, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf04NotBetween(String value1, String value2) {
            addCriterion("udf04 not between", value1, value2, "udf04");
            return (Criteria) this;
        }

        public Criteria andUdf05IsNull() {
            addCriterion("udf05 is null");
            return (Criteria) this;
        }

        public Criteria andUdf05IsNotNull() {
            addCriterion("udf05 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf05EqualTo(String value) {
            addCriterion("udf05 =", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05NotEqualTo(String value) {
            addCriterion("udf05 <>", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05GreaterThan(String value) {
            addCriterion("udf05 >", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05GreaterThanOrEqualTo(String value) {
            addCriterion("udf05 >=", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05LessThan(String value) {
            addCriterion("udf05 <", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05LessThanOrEqualTo(String value) {
            addCriterion("udf05 <=", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05Like(String value) {
            addCriterion("udf05 like", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05NotLike(String value) {
            addCriterion("udf05 not like", value, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05In(List<String> values) {
            addCriterion("udf05 in", values, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05NotIn(List<String> values) {
            addCriterion("udf05 not in", values, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05Between(String value1, String value2) {
            addCriterion("udf05 between", value1, value2, "udf05");
            return (Criteria) this;
        }

        public Criteria andUdf05NotBetween(String value1, String value2) {
            addCriterion("udf05 not between", value1, value2, "udf05");
            return (Criteria) this;
        }

        public Criteria andCurrentversionIsNull() {
            addCriterion("currentversion is null");
            return (Criteria) this;
        }

        public Criteria andCurrentversionIsNotNull() {
            addCriterion("currentversion is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentversionEqualTo(Integer value) {
            addCriterion("currentversion =", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionNotEqualTo(Integer value) {
            addCriterion("currentversion <>", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionGreaterThan(Integer value) {
            addCriterion("currentversion >", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionGreaterThanOrEqualTo(Integer value) {
            addCriterion("currentversion >=", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionLessThan(Integer value) {
            addCriterion("currentversion <", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionLessThanOrEqualTo(Integer value) {
            addCriterion("currentversion <=", value, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionIn(List<Integer> values) {
            addCriterion("currentversion in", values, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionNotIn(List<Integer> values) {
            addCriterion("currentversion not in", values, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionBetween(Integer value1, Integer value2) {
            addCriterion("currentversion between", value1, value2, "currentversion");
            return (Criteria) this;
        }

        public Criteria andCurrentversionNotBetween(Integer value1, Integer value2) {
            addCriterion("currentversion not between", value1, value2, "currentversion");
            return (Criteria) this;
        }

        public Criteria andOprseqflagIsNull() {
            addCriterion("oprseqflag is null");
            return (Criteria) this;
        }

        public Criteria andOprseqflagIsNotNull() {
            addCriterion("oprseqflag is not null");
            return (Criteria) this;
        }

        public Criteria andOprseqflagEqualTo(String value) {
            addCriterion("oprseqflag =", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagNotEqualTo(String value) {
            addCriterion("oprseqflag <>", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagGreaterThan(String value) {
            addCriterion("oprseqflag >", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagGreaterThanOrEqualTo(String value) {
            addCriterion("oprseqflag >=", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagLessThan(String value) {
            addCriterion("oprseqflag <", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagLessThanOrEqualTo(String value) {
            addCriterion("oprseqflag <=", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagLike(String value) {
            addCriterion("oprseqflag like", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagNotLike(String value) {
            addCriterion("oprseqflag not like", value, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagIn(List<String> values) {
            addCriterion("oprseqflag in", values, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagNotIn(List<String> values) {
            addCriterion("oprseqflag not in", values, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagBetween(String value1, String value2) {
            addCriterion("oprseqflag between", value1, value2, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andOprseqflagNotBetween(String value1, String value2) {
            addCriterion("oprseqflag not between", value1, value2, "oprseqflag");
            return (Criteria) this;
        }

        public Criteria andAddwhoIsNull() {
            addCriterion("addwho is null");
            return (Criteria) this;
        }

        public Criteria andAddwhoIsNotNull() {
            addCriterion("addwho is not null");
            return (Criteria) this;
        }

        public Criteria andAddwhoEqualTo(String value) {
            addCriterion("addwho =", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoNotEqualTo(String value) {
            addCriterion("addwho <>", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoGreaterThan(String value) {
            addCriterion("addwho >", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoGreaterThanOrEqualTo(String value) {
            addCriterion("addwho >=", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoLessThan(String value) {
            addCriterion("addwho <", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoLessThanOrEqualTo(String value) {
            addCriterion("addwho <=", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoLike(String value) {
            addCriterion("addwho like", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoNotLike(String value) {
            addCriterion("addwho not like", value, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoIn(List<String> values) {
            addCriterion("addwho in", values, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoNotIn(List<String> values) {
            addCriterion("addwho not in", values, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoBetween(String value1, String value2) {
            addCriterion("addwho between", value1, value2, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddwhoNotBetween(String value1, String value2) {
            addCriterion("addwho not between", value1, value2, "addwho");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNull() {
            addCriterion("addtime is null");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNotNull() {
            addCriterion("addtime is not null");
            return (Criteria) this;
        }

        public Criteria andAddtimeEqualTo(Date value) {
            addCriterion("addtime =", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotEqualTo(Date value) {
            addCriterion("addtime <>", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThan(Date value) {
            addCriterion("addtime >", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("addtime >=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThan(Date value) {
            addCriterion("addtime <", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThanOrEqualTo(Date value) {
            addCriterion("addtime <=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeIn(List<Date> values) {
            addCriterion("addtime in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotIn(List<Date> values) {
            addCriterion("addtime not in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeBetween(Date value1, Date value2) {
            addCriterion("addtime between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotBetween(Date value1, Date value2) {
            addCriterion("addtime not between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andEditwhoIsNull() {
            addCriterion("editwho is null");
            return (Criteria) this;
        }

        public Criteria andEditwhoIsNotNull() {
            addCriterion("editwho is not null");
            return (Criteria) this;
        }

        public Criteria andEditwhoEqualTo(String value) {
            addCriterion("editwho =", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoNotEqualTo(String value) {
            addCriterion("editwho <>", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoGreaterThan(String value) {
            addCriterion("editwho >", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoGreaterThanOrEqualTo(String value) {
            addCriterion("editwho >=", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoLessThan(String value) {
            addCriterion("editwho <", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoLessThanOrEqualTo(String value) {
            addCriterion("editwho <=", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoLike(String value) {
            addCriterion("editwho like", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoNotLike(String value) {
            addCriterion("editwho not like", value, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoIn(List<String> values) {
            addCriterion("editwho in", values, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoNotIn(List<String> values) {
            addCriterion("editwho not in", values, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoBetween(String value1, String value2) {
            addCriterion("editwho between", value1, value2, "editwho");
            return (Criteria) this;
        }

        public Criteria andEditwhoNotBetween(String value1, String value2) {
            addCriterion("editwho not between", value1, value2, "editwho");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNull() {
            addCriterion("edittime is null");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNotNull() {
            addCriterion("edittime is not null");
            return (Criteria) this;
        }

        public Criteria andEdittimeEqualTo(Date value) {
            addCriterion("edittime =", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotEqualTo(Date value) {
            addCriterion("edittime <>", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThan(Date value) {
            addCriterion("edittime >", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThanOrEqualTo(Date value) {
            addCriterion("edittime >=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThan(Date value) {
            addCriterion("edittime <", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThanOrEqualTo(Date value) {
            addCriterion("edittime <=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeIn(List<Date> values) {
            addCriterion("edittime in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotIn(List<Date> values) {
            addCriterion("edittime not in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeBetween(Date value1, Date value2) {
            addCriterion("edittime between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotBetween(Date value1, Date value2) {
            addCriterion("edittime not between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andActiveflagIsNull() {
            addCriterion("activeflag is null");
            return (Criteria) this;
        }

        public Criteria andActiveflagIsNotNull() {
            addCriterion("activeflag is not null");
            return (Criteria) this;
        }

        public Criteria andActiveflagEqualTo(String value) {
            addCriterion("activeflag =", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotEqualTo(String value) {
            addCriterion("activeflag <>", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagGreaterThan(String value) {
            addCriterion("activeflag >", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagGreaterThanOrEqualTo(String value) {
            addCriterion("activeflag >=", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagLessThan(String value) {
            addCriterion("activeflag <", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagLessThanOrEqualTo(String value) {
            addCriterion("activeflag <=", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagLike(String value) {
            addCriterion("activeflag like", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotLike(String value) {
            addCriterion("activeflag not like", value, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagIn(List<String> values) {
            addCriterion("activeflag in", values, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotIn(List<String> values) {
            addCriterion("activeflag not in", values, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagBetween(String value1, String value2) {
            addCriterion("activeflag between", value1, value2, "activeflag");
            return (Criteria) this;
        }

        public Criteria andActiveflagNotBetween(String value1, String value2) {
            addCriterion("activeflag not between", value1, value2, "activeflag");
            return (Criteria) this;
        }

        public Criteria andOmsnoIsNull() {
            addCriterion("omsno is null");
            return (Criteria) this;
        }

        public Criteria andOmsnoIsNotNull() {
            addCriterion("omsno is not null");
            return (Criteria) this;
        }

        public Criteria andOmsnoEqualTo(String value) {
            addCriterion("omsno =", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoNotEqualTo(String value) {
            addCriterion("omsno <>", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoGreaterThan(String value) {
            addCriterion("omsno >", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoGreaterThanOrEqualTo(String value) {
            addCriterion("omsno >=", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoLessThan(String value) {
            addCriterion("omsno <", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoLessThanOrEqualTo(String value) {
            addCriterion("omsno <=", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoLike(String value) {
            addCriterion("omsno like", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoNotLike(String value) {
            addCriterion("omsno not like", value, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoIn(List<String> values) {
            addCriterion("omsno in", values, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoNotIn(List<String> values) {
            addCriterion("omsno not in", values, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoBetween(String value1, String value2) {
            addCriterion("omsno between", value1, value2, "omsno");
            return (Criteria) this;
        }

        public Criteria andOmsnoNotBetween(String value1, String value2) {
            addCriterion("omsno not between", value1, value2, "omsno");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusIsNull() {
            addCriterion("trackingstatus is null");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusIsNotNull() {
            addCriterion("trackingstatus is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusEqualTo(String value) {
            addCriterion("trackingstatus =", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusNotEqualTo(String value) {
            addCriterion("trackingstatus <>", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusGreaterThan(String value) {
            addCriterion("trackingstatus >", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusGreaterThanOrEqualTo(String value) {
            addCriterion("trackingstatus >=", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusLessThan(String value) {
            addCriterion("trackingstatus <", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusLessThanOrEqualTo(String value) {
            addCriterion("trackingstatus <=", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusLike(String value) {
            addCriterion("trackingstatus like", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusNotLike(String value) {
            addCriterion("trackingstatus not like", value, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusIn(List<String> values) {
            addCriterion("trackingstatus in", values, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusNotIn(List<String> values) {
            addCriterion("trackingstatus not in", values, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusBetween(String value1, String value2) {
            addCriterion("trackingstatus between", value1, value2, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andTrackingstatusNotBetween(String value1, String value2) {
            addCriterion("trackingstatus not between", value1, value2, "trackingstatus");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNull() {
            addCriterion("ordertype is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("ordertype is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(String value) {
            addCriterion("ordertype =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(String value) {
            addCriterion("ordertype <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(String value) {
            addCriterion("ordertype >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(String value) {
            addCriterion("ordertype >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(String value) {
            addCriterion("ordertype <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(String value) {
            addCriterion("ordertype <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLike(String value) {
            addCriterion("ordertype like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotLike(String value) {
            addCriterion("ordertype not like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<String> values) {
            addCriterion("ordertype in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<String> values) {
            addCriterion("ordertype not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(String value1, String value2) {
            addCriterion("ordertype between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(String value1, String value2) {
            addCriterion("ordertype not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1IsNull() {
            addCriterion("edisendflag1 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1IsNotNull() {
            addCriterion("edisendflag1 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1EqualTo(String value) {
            addCriterion("edisendflag1 =", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1NotEqualTo(String value) {
            addCriterion("edisendflag1 <>", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1GreaterThan(String value) {
            addCriterion("edisendflag1 >", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1GreaterThanOrEqualTo(String value) {
            addCriterion("edisendflag1 >=", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1LessThan(String value) {
            addCriterion("edisendflag1 <", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1LessThanOrEqualTo(String value) {
            addCriterion("edisendflag1 <=", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1Like(String value) {
            addCriterion("edisendflag1 like", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1NotLike(String value) {
            addCriterion("edisendflag1 not like", value, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1In(List<String> values) {
            addCriterion("edisendflag1 in", values, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1NotIn(List<String> values) {
            addCriterion("edisendflag1 not in", values, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1Between(String value1, String value2) {
            addCriterion("edisendflag1 between", value1, value2, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag1NotBetween(String value1, String value2) {
            addCriterion("edisendflag1 not between", value1, value2, "edisendflag1");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2IsNull() {
            addCriterion("edisendflag2 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2IsNotNull() {
            addCriterion("edisendflag2 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2EqualTo(String value) {
            addCriterion("edisendflag2 =", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2NotEqualTo(String value) {
            addCriterion("edisendflag2 <>", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2GreaterThan(String value) {
            addCriterion("edisendflag2 >", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2GreaterThanOrEqualTo(String value) {
            addCriterion("edisendflag2 >=", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2LessThan(String value) {
            addCriterion("edisendflag2 <", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2LessThanOrEqualTo(String value) {
            addCriterion("edisendflag2 <=", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2Like(String value) {
            addCriterion("edisendflag2 like", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2NotLike(String value) {
            addCriterion("edisendflag2 not like", value, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2In(List<String> values) {
            addCriterion("edisendflag2 in", values, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2NotIn(List<String> values) {
            addCriterion("edisendflag2 not in", values, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2Between(String value1, String value2) {
            addCriterion("edisendflag2 between", value1, value2, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag2NotBetween(String value1, String value2) {
            addCriterion("edisendflag2 not between", value1, value2, "edisendflag2");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3IsNull() {
            addCriterion("edisendflag3 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3IsNotNull() {
            addCriterion("edisendflag3 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3EqualTo(String value) {
            addCriterion("edisendflag3 =", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3NotEqualTo(String value) {
            addCriterion("edisendflag3 <>", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3GreaterThan(String value) {
            addCriterion("edisendflag3 >", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3GreaterThanOrEqualTo(String value) {
            addCriterion("edisendflag3 >=", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3LessThan(String value) {
            addCriterion("edisendflag3 <", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3LessThanOrEqualTo(String value) {
            addCriterion("edisendflag3 <=", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3Like(String value) {
            addCriterion("edisendflag3 like", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3NotLike(String value) {
            addCriterion("edisendflag3 not like", value, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3In(List<String> values) {
            addCriterion("edisendflag3 in", values, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3NotIn(List<String> values) {
            addCriterion("edisendflag3 not in", values, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3Between(String value1, String value2) {
            addCriterion("edisendflag3 between", value1, value2, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendflag3NotBetween(String value1, String value2) {
            addCriterion("edisendflag3 not between", value1, value2, "edisendflag3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1IsNull() {
            addCriterion("edisendtime1 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1IsNotNull() {
            addCriterion("edisendtime1 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1EqualTo(Date value) {
            addCriterion("edisendtime1 =", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1NotEqualTo(Date value) {
            addCriterion("edisendtime1 <>", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1GreaterThan(Date value) {
            addCriterion("edisendtime1 >", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1GreaterThanOrEqualTo(Date value) {
            addCriterion("edisendtime1 >=", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1LessThan(Date value) {
            addCriterion("edisendtime1 <", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1LessThanOrEqualTo(Date value) {
            addCriterion("edisendtime1 <=", value, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1In(List<Date> values) {
            addCriterion("edisendtime1 in", values, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1NotIn(List<Date> values) {
            addCriterion("edisendtime1 not in", values, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1Between(Date value1, Date value2) {
            addCriterion("edisendtime1 between", value1, value2, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime1NotBetween(Date value1, Date value2) {
            addCriterion("edisendtime1 not between", value1, value2, "edisendtime1");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2IsNull() {
            addCriterion("edisendtime2 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2IsNotNull() {
            addCriterion("edisendtime2 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2EqualTo(Date value) {
            addCriterion("edisendtime2 =", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2NotEqualTo(Date value) {
            addCriterion("edisendtime2 <>", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2GreaterThan(Date value) {
            addCriterion("edisendtime2 >", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2GreaterThanOrEqualTo(Date value) {
            addCriterion("edisendtime2 >=", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2LessThan(Date value) {
            addCriterion("edisendtime2 <", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2LessThanOrEqualTo(Date value) {
            addCriterion("edisendtime2 <=", value, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2In(List<Date> values) {
            addCriterion("edisendtime2 in", values, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2NotIn(List<Date> values) {
            addCriterion("edisendtime2 not in", values, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2Between(Date value1, Date value2) {
            addCriterion("edisendtime2 between", value1, value2, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime2NotBetween(Date value1, Date value2) {
            addCriterion("edisendtime2 not between", value1, value2, "edisendtime2");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3IsNull() {
            addCriterion("edisendtime3 is null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3IsNotNull() {
            addCriterion("edisendtime3 is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3EqualTo(Date value) {
            addCriterion("edisendtime3 =", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3NotEqualTo(Date value) {
            addCriterion("edisendtime3 <>", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3GreaterThan(Date value) {
            addCriterion("edisendtime3 >", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3GreaterThanOrEqualTo(Date value) {
            addCriterion("edisendtime3 >=", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3LessThan(Date value) {
            addCriterion("edisendtime3 <", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3LessThanOrEqualTo(Date value) {
            addCriterion("edisendtime3 <=", value, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3In(List<Date> values) {
            addCriterion("edisendtime3 in", values, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3NotIn(List<Date> values) {
            addCriterion("edisendtime3 not in", values, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3Between(Date value1, Date value2) {
            addCriterion("edisendtime3 between", value1, value2, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andEdisendtime3NotBetween(Date value1, Date value2) {
            addCriterion("edisendtime3 not between", value1, value2, "edisendtime3");
            return (Criteria) this;
        }

        public Criteria andDrivertel1IsNull() {
            addCriterion("drivertel1 is null");
            return (Criteria) this;
        }

        public Criteria andDrivertel1IsNotNull() {
            addCriterion("drivertel1 is not null");
            return (Criteria) this;
        }

        public Criteria andDrivertel1EqualTo(String value) {
            addCriterion("drivertel1 =", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1NotEqualTo(String value) {
            addCriterion("drivertel1 <>", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1GreaterThan(String value) {
            addCriterion("drivertel1 >", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1GreaterThanOrEqualTo(String value) {
            addCriterion("drivertel1 >=", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1LessThan(String value) {
            addCriterion("drivertel1 <", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1LessThanOrEqualTo(String value) {
            addCriterion("drivertel1 <=", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1Like(String value) {
            addCriterion("drivertel1 like", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1NotLike(String value) {
            addCriterion("drivertel1 not like", value, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1In(List<String> values) {
            addCriterion("drivertel1 in", values, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1NotIn(List<String> values) {
            addCriterion("drivertel1 not in", values, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1Between(String value1, String value2) {
            addCriterion("drivertel1 between", value1, value2, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andDrivertel1NotBetween(String value1, String value2) {
            addCriterion("drivertel1 not between", value1, value2, "drivertel1");
            return (Criteria) this;
        }

        public Criteria andWmsordernoIsNull() {
            addCriterion("wmsOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andWmsordernoIsNotNull() {
            addCriterion("wmsOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andWmsordernoEqualTo(String value) {
            addCriterion("wmsOrderNo =", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoNotEqualTo(String value) {
            addCriterion("wmsOrderNo <>", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoGreaterThan(String value) {
            addCriterion("wmsOrderNo >", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoGreaterThanOrEqualTo(String value) {
            addCriterion("wmsOrderNo >=", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoLessThan(String value) {
            addCriterion("wmsOrderNo <", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoLessThanOrEqualTo(String value) {
            addCriterion("wmsOrderNo <=", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoLike(String value) {
            addCriterion("wmsOrderNo like", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoNotLike(String value) {
            addCriterion("wmsOrderNo not like", value, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoIn(List<String> values) {
            addCriterion("wmsOrderNo in", values, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoNotIn(List<String> values) {
            addCriterion("wmsOrderNo not in", values, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoBetween(String value1, String value2) {
            addCriterion("wmsOrderNo between", value1, value2, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andWmsordernoNotBetween(String value1, String value2) {
            addCriterion("wmsOrderNo not between", value1, value2, "wmsorderno");
            return (Criteria) this;
        }

        public Criteria andReference1IsNull() {
            addCriterion("reference1 is null");
            return (Criteria) this;
        }

        public Criteria andReference1IsNotNull() {
            addCriterion("reference1 is not null");
            return (Criteria) this;
        }

        public Criteria andReference1EqualTo(String value) {
            addCriterion("reference1 =", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1NotEqualTo(String value) {
            addCriterion("reference1 <>", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1GreaterThan(String value) {
            addCriterion("reference1 >", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1GreaterThanOrEqualTo(String value) {
            addCriterion("reference1 >=", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1LessThan(String value) {
            addCriterion("reference1 <", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1LessThanOrEqualTo(String value) {
            addCriterion("reference1 <=", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1Like(String value) {
            addCriterion("reference1 like", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1NotLike(String value) {
            addCriterion("reference1 not like", value, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1In(List<String> values) {
            addCriterion("reference1 in", values, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1NotIn(List<String> values) {
            addCriterion("reference1 not in", values, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1Between(String value1, String value2) {
            addCriterion("reference1 between", value1, value2, "reference1");
            return (Criteria) this;
        }

        public Criteria andReference1NotBetween(String value1, String value2) {
            addCriterion("reference1 not between", value1, value2, "reference1");
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
package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TBilBillingHeaderOpsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBilBillingHeaderOpsExample() {
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

        public Criteria andBillingnoIsNull() {
            addCriterion("billingno is null");
            return (Criteria) this;
        }

        public Criteria andBillingnoIsNotNull() {
            addCriterion("billingno is not null");
            return (Criteria) this;
        }

        public Criteria andBillingnoEqualTo(String value) {
            addCriterion("billingno =", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoNotEqualTo(String value) {
            addCriterion("billingno <>", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoGreaterThan(String value) {
            addCriterion("billingno >", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoGreaterThanOrEqualTo(String value) {
            addCriterion("billingno >=", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoLessThan(String value) {
            addCriterion("billingno <", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoLessThanOrEqualTo(String value) {
            addCriterion("billingno <=", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoLike(String value) {
            addCriterion("billingno like", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoNotLike(String value) {
            addCriterion("billingno not like", value, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoIn(List<String> values) {
            addCriterion("billingno in", values, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoNotIn(List<String> values) {
            addCriterion("billingno not in", values, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoBetween(String value1, String value2) {
            addCriterion("billingno between", value1, value2, "billingno");
            return (Criteria) this;
        }

        public Criteria andBillingnoNotBetween(String value1, String value2) {
            addCriterion("billingno not between", value1, value2, "billingno");
            return (Criteria) this;
        }

        public Criteria andCustomeridIsNull() {
            addCriterion("customerid is null");
            return (Criteria) this;
        }

        public Criteria andCustomeridIsNotNull() {
            addCriterion("customerid is not null");
            return (Criteria) this;
        }

        public Criteria andCustomeridEqualTo(String value) {
            addCriterion("customerid =", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotEqualTo(String value) {
            addCriterion("customerid <>", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridGreaterThan(String value) {
            addCriterion("customerid >", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridGreaterThanOrEqualTo(String value) {
            addCriterion("customerid >=", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridLessThan(String value) {
            addCriterion("customerid <", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridLessThanOrEqualTo(String value) {
            addCriterion("customerid <=", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridLike(String value) {
            addCriterion("customerid like", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotLike(String value) {
            addCriterion("customerid not like", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridIn(List<String> values) {
            addCriterion("customerid in", values, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotIn(List<String> values) {
            addCriterion("customerid not in", values, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridBetween(String value1, String value2) {
            addCriterion("customerid between", value1, value2, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotBetween(String value1, String value2) {
            addCriterion("customerid not between", value1, value2, "customerid");
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

        public Criteria andBillingtypeIsNull() {
            addCriterion("billingtype is null");
            return (Criteria) this;
        }

        public Criteria andBillingtypeIsNotNull() {
            addCriterion("billingtype is not null");
            return (Criteria) this;
        }

        public Criteria andBillingtypeEqualTo(String value) {
            addCriterion("billingtype =", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotEqualTo(String value) {
            addCriterion("billingtype <>", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeGreaterThan(String value) {
            addCriterion("billingtype >", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeGreaterThanOrEqualTo(String value) {
            addCriterion("billingtype >=", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLessThan(String value) {
            addCriterion("billingtype <", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLessThanOrEqualTo(String value) {
            addCriterion("billingtype <=", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLike(String value) {
            addCriterion("billingtype like", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotLike(String value) {
            addCriterion("billingtype not like", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeIn(List<String> values) {
            addCriterion("billingtype in", values, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotIn(List<String> values) {
            addCriterion("billingtype not in", values, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeBetween(String value1, String value2) {
            addCriterion("billingtype between", value1, value2, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotBetween(String value1, String value2) {
            addCriterion("billingtype not between", value1, value2, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillmonthIsNull() {
            addCriterion("billmonth is null");
            return (Criteria) this;
        }

        public Criteria andBillmonthIsNotNull() {
            addCriterion("billmonth is not null");
            return (Criteria) this;
        }

        public Criteria andBillmonthEqualTo(Date value) {
            addCriterion("billmonth =", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthNotEqualTo(Date value) {
            addCriterion("billmonth <>", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthGreaterThan(Date value) {
            addCriterion("billmonth >", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthGreaterThanOrEqualTo(Date value) {
            addCriterion("billmonth >=", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthLessThan(Date value) {
            addCriterion("billmonth <", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthLessThanOrEqualTo(Date value) {
            addCriterion("billmonth <=", value, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthIn(List<Date> values) {
            addCriterion("billmonth in", values, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthNotIn(List<Date> values) {
            addCriterion("billmonth not in", values, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthBetween(Date value1, Date value2) {
            addCriterion("billmonth between", value1, value2, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBillmonthNotBetween(Date value1, Date value2) {
            addCriterion("billmonth not between", value1, value2, "billmonth");
            return (Criteria) this;
        }

        public Criteria andBilldatefmIsNull() {
            addCriterion("billdatefm is null");
            return (Criteria) this;
        }

        public Criteria andBilldatefmIsNotNull() {
            addCriterion("billdatefm is not null");
            return (Criteria) this;
        }

        public Criteria andBilldatefmEqualTo(Date value) {
            addCriterion("billdatefm =", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmNotEqualTo(Date value) {
            addCriterion("billdatefm <>", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmGreaterThan(Date value) {
            addCriterion("billdatefm >", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmGreaterThanOrEqualTo(Date value) {
            addCriterion("billdatefm >=", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmLessThan(Date value) {
            addCriterion("billdatefm <", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmLessThanOrEqualTo(Date value) {
            addCriterion("billdatefm <=", value, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmIn(List<Date> values) {
            addCriterion("billdatefm in", values, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmNotIn(List<Date> values) {
            addCriterion("billdatefm not in", values, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmBetween(Date value1, Date value2) {
            addCriterion("billdatefm between", value1, value2, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatefmNotBetween(Date value1, Date value2) {
            addCriterion("billdatefm not between", value1, value2, "billdatefm");
            return (Criteria) this;
        }

        public Criteria andBilldatetoIsNull() {
            addCriterion("billdateto is null");
            return (Criteria) this;
        }

        public Criteria andBilldatetoIsNotNull() {
            addCriterion("billdateto is not null");
            return (Criteria) this;
        }

        public Criteria andBilldatetoEqualTo(Date value) {
            addCriterion("billdateto =", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoNotEqualTo(Date value) {
            addCriterion("billdateto <>", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoGreaterThan(Date value) {
            addCriterion("billdateto >", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoGreaterThanOrEqualTo(Date value) {
            addCriterion("billdateto >=", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoLessThan(Date value) {
            addCriterion("billdateto <", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoLessThanOrEqualTo(Date value) {
            addCriterion("billdateto <=", value, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoIn(List<Date> values) {
            addCriterion("billdateto in", values, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoNotIn(List<Date> values) {
            addCriterion("billdateto not in", values, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoBetween(Date value1, Date value2) {
            addCriterion("billdateto between", value1, value2, "billdateto");
            return (Criteria) this;
        }

        public Criteria andBilldatetoNotBetween(Date value1, Date value2) {
            addCriterion("billdateto not between", value1, value2, "billdateto");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTotalamountIsNull() {
            addCriterion("totalamount is null");
            return (Criteria) this;
        }

        public Criteria andTotalamountIsNotNull() {
            addCriterion("totalamount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalamountEqualTo(BigDecimal value) {
            addCriterion("totalamount =", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountNotEqualTo(BigDecimal value) {
            addCriterion("totalamount <>", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountGreaterThan(BigDecimal value) {
            addCriterion("totalamount >", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("totalamount >=", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountLessThan(BigDecimal value) {
            addCriterion("totalamount <", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("totalamount <=", value, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountIn(List<BigDecimal> values) {
            addCriterion("totalamount in", values, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountNotIn(List<BigDecimal> values) {
            addCriterion("totalamount not in", values, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("totalamount between", value1, value2, "totalamount");
            return (Criteria) this;
        }

        public Criteria andTotalamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("totalamount not between", value1, value2, "totalamount");
            return (Criteria) this;
        }

        public Criteria andDiscountstartIsNull() {
            addCriterion("discountstart is null");
            return (Criteria) this;
        }

        public Criteria andDiscountstartIsNotNull() {
            addCriterion("discountstart is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountstartEqualTo(BigDecimal value) {
            addCriterion("discountstart =", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartNotEqualTo(BigDecimal value) {
            addCriterion("discountstart <>", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartGreaterThan(BigDecimal value) {
            addCriterion("discountstart >", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discountstart >=", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartLessThan(BigDecimal value) {
            addCriterion("discountstart <", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discountstart <=", value, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartIn(List<BigDecimal> values) {
            addCriterion("discountstart in", values, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartNotIn(List<BigDecimal> values) {
            addCriterion("discountstart not in", values, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discountstart between", value1, value2, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountstartNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discountstart not between", value1, value2, "discountstart");
            return (Criteria) this;
        }

        public Criteria andDiscountrateIsNull() {
            addCriterion("discountrate is null");
            return (Criteria) this;
        }

        public Criteria andDiscountrateIsNotNull() {
            addCriterion("discountrate is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountrateEqualTo(BigDecimal value) {
            addCriterion("discountrate =", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateNotEqualTo(BigDecimal value) {
            addCriterion("discountrate <>", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateGreaterThan(BigDecimal value) {
            addCriterion("discountrate >", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discountrate >=", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateLessThan(BigDecimal value) {
            addCriterion("discountrate <", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discountrate <=", value, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateIn(List<BigDecimal> values) {
            addCriterion("discountrate in", values, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateNotIn(List<BigDecimal> values) {
            addCriterion("discountrate not in", values, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discountrate between", value1, value2, "discountrate");
            return (Criteria) this;
        }

        public Criteria andDiscountrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discountrate not between", value1, value2, "discountrate");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountIsNull() {
            addCriterion("adjustedamount is null");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountIsNotNull() {
            addCriterion("adjustedamount is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountEqualTo(BigDecimal value) {
            addCriterion("adjustedamount =", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountNotEqualTo(BigDecimal value) {
            addCriterion("adjustedamount <>", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountGreaterThan(BigDecimal value) {
            addCriterion("adjustedamount >", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("adjustedamount >=", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountLessThan(BigDecimal value) {
            addCriterion("adjustedamount <", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("adjustedamount <=", value, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountIn(List<BigDecimal> values) {
            addCriterion("adjustedamount in", values, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountNotIn(List<BigDecimal> values) {
            addCriterion("adjustedamount not in", values, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("adjustedamount between", value1, value2, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustedamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("adjustedamount not between", value1, value2, "adjustedamount");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesIsNull() {
            addCriterion("adjustnotes is null");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesIsNotNull() {
            addCriterion("adjustnotes is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesEqualTo(String value) {
            addCriterion("adjustnotes =", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesNotEqualTo(String value) {
            addCriterion("adjustnotes <>", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesGreaterThan(String value) {
            addCriterion("adjustnotes >", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesGreaterThanOrEqualTo(String value) {
            addCriterion("adjustnotes >=", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesLessThan(String value) {
            addCriterion("adjustnotes <", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesLessThanOrEqualTo(String value) {
            addCriterion("adjustnotes <=", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesLike(String value) {
            addCriterion("adjustnotes like", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesNotLike(String value) {
            addCriterion("adjustnotes not like", value, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesIn(List<String> values) {
            addCriterion("adjustnotes in", values, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesNotIn(List<String> values) {
            addCriterion("adjustnotes not in", values, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesBetween(String value1, String value2) {
            addCriterion("adjustnotes between", value1, value2, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andAdjustnotesNotBetween(String value1, String value2) {
            addCriterion("adjustnotes not between", value1, value2, "adjustnotes");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeIsNull() {
            addCriterion("currencytype is null");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeIsNotNull() {
            addCriterion("currencytype is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeEqualTo(String value) {
            addCriterion("currencytype =", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeNotEqualTo(String value) {
            addCriterion("currencytype <>", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeGreaterThan(String value) {
            addCriterion("currencytype >", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeGreaterThanOrEqualTo(String value) {
            addCriterion("currencytype >=", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeLessThan(String value) {
            addCriterion("currencytype <", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeLessThanOrEqualTo(String value) {
            addCriterion("currencytype <=", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeLike(String value) {
            addCriterion("currencytype like", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeNotLike(String value) {
            addCriterion("currencytype not like", value, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeIn(List<String> values) {
            addCriterion("currencytype in", values, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeNotIn(List<String> values) {
            addCriterion("currencytype not in", values, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeBetween(String value1, String value2) {
            addCriterion("currencytype between", value1, value2, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencytypeNotBetween(String value1, String value2) {
            addCriterion("currencytype not between", value1, value2, "currencytype");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateIsNull() {
            addCriterion("currencyrate is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateIsNotNull() {
            addCriterion("currencyrate is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateEqualTo(BigDecimal value) {
            addCriterion("currencyrate =", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateNotEqualTo(BigDecimal value) {
            addCriterion("currencyrate <>", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateGreaterThan(BigDecimal value) {
            addCriterion("currencyrate >", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("currencyrate >=", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateLessThan(BigDecimal value) {
            addCriterion("currencyrate <", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("currencyrate <=", value, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateIn(List<BigDecimal> values) {
            addCriterion("currencyrate in", values, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateNotIn(List<BigDecimal> values) {
            addCriterion("currencyrate not in", values, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("currencyrate between", value1, value2, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andCurrencyrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("currencyrate not between", value1, value2, "currencyrate");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountIsNull() {
            addCriterion("totalbillingamount is null");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountIsNotNull() {
            addCriterion("totalbillingamount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountEqualTo(BigDecimal value) {
            addCriterion("totalbillingamount =", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountNotEqualTo(BigDecimal value) {
            addCriterion("totalbillingamount <>", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountGreaterThan(BigDecimal value) {
            addCriterion("totalbillingamount >", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("totalbillingamount >=", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountLessThan(BigDecimal value) {
            addCriterion("totalbillingamount <", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("totalbillingamount <=", value, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountIn(List<BigDecimal> values) {
            addCriterion("totalbillingamount in", values, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountNotIn(List<BigDecimal> values) {
            addCriterion("totalbillingamount not in", values, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("totalbillingamount between", value1, value2, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andTotalbillingamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("totalbillingamount not between", value1, value2, "totalbillingamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountIsNull() {
            addCriterion("invoiceamount is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountIsNotNull() {
            addCriterion("invoiceamount is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountEqualTo(BigDecimal value) {
            addCriterion("invoiceamount =", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountNotEqualTo(BigDecimal value) {
            addCriterion("invoiceamount <>", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountGreaterThan(BigDecimal value) {
            addCriterion("invoiceamount >", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invoiceamount >=", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountLessThan(BigDecimal value) {
            addCriterion("invoiceamount <", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invoiceamount <=", value, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountIn(List<BigDecimal> values) {
            addCriterion("invoiceamount in", values, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountNotIn(List<BigDecimal> values) {
            addCriterion("invoiceamount not in", values, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invoiceamount between", value1, value2, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoiceamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invoiceamount not between", value1, value2, "invoiceamount");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusIsNull() {
            addCriterion("invoicestatus is null");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusIsNotNull() {
            addCriterion("invoicestatus is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusEqualTo(String value) {
            addCriterion("invoicestatus =", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusNotEqualTo(String value) {
            addCriterion("invoicestatus <>", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusGreaterThan(String value) {
            addCriterion("invoicestatus >", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusGreaterThanOrEqualTo(String value) {
            addCriterion("invoicestatus >=", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusLessThan(String value) {
            addCriterion("invoicestatus <", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusLessThanOrEqualTo(String value) {
            addCriterion("invoicestatus <=", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusLike(String value) {
            addCriterion("invoicestatus like", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusNotLike(String value) {
            addCriterion("invoicestatus not like", value, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusIn(List<String> values) {
            addCriterion("invoicestatus in", values, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusNotIn(List<String> values) {
            addCriterion("invoicestatus not in", values, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusBetween(String value1, String value2) {
            addCriterion("invoicestatus between", value1, value2, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andInvoicestatusNotBetween(String value1, String value2) {
            addCriterion("invoicestatus not between", value1, value2, "invoicestatus");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidIsNull() {
            addCriterion("createbranchid is null");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidIsNotNull() {
            addCriterion("createbranchid is not null");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidEqualTo(String value) {
            addCriterion("createbranchid =", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidNotEqualTo(String value) {
            addCriterion("createbranchid <>", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidGreaterThan(String value) {
            addCriterion("createbranchid >", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidGreaterThanOrEqualTo(String value) {
            addCriterion("createbranchid >=", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidLessThan(String value) {
            addCriterion("createbranchid <", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidLessThanOrEqualTo(String value) {
            addCriterion("createbranchid <=", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidLike(String value) {
            addCriterion("createbranchid like", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidNotLike(String value) {
            addCriterion("createbranchid not like", value, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidIn(List<String> values) {
            addCriterion("createbranchid in", values, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidNotIn(List<String> values) {
            addCriterion("createbranchid not in", values, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidBetween(String value1, String value2) {
            addCriterion("createbranchid between", value1, value2, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andCreatebranchidNotBetween(String value1, String value2) {
            addCriterion("createbranchid not between", value1, value2, "createbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidIsNull() {
            addCriterion("settlementbranchid is null");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidIsNotNull() {
            addCriterion("settlementbranchid is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidEqualTo(String value) {
            addCriterion("settlementbranchid =", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidNotEqualTo(String value) {
            addCriterion("settlementbranchid <>", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidGreaterThan(String value) {
            addCriterion("settlementbranchid >", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidGreaterThanOrEqualTo(String value) {
            addCriterion("settlementbranchid >=", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidLessThan(String value) {
            addCriterion("settlementbranchid <", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidLessThanOrEqualTo(String value) {
            addCriterion("settlementbranchid <=", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidLike(String value) {
            addCriterion("settlementbranchid like", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidNotLike(String value) {
            addCriterion("settlementbranchid not like", value, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidIn(List<String> values) {
            addCriterion("settlementbranchid in", values, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidNotIn(List<String> values) {
            addCriterion("settlementbranchid not in", values, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidBetween(String value1, String value2) {
            addCriterion("settlementbranchid between", value1, value2, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andSettlementbranchidNotBetween(String value1, String value2) {
            addCriterion("settlementbranchid not between", value1, value2, "settlementbranchid");
            return (Criteria) this;
        }

        public Criteria andTariffidIsNull() {
            addCriterion("tariffid is null");
            return (Criteria) this;
        }

        public Criteria andTariffidIsNotNull() {
            addCriterion("tariffid is not null");
            return (Criteria) this;
        }

        public Criteria andTariffidEqualTo(String value) {
            addCriterion("tariffid =", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidNotEqualTo(String value) {
            addCriterion("tariffid <>", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidGreaterThan(String value) {
            addCriterion("tariffid >", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidGreaterThanOrEqualTo(String value) {
            addCriterion("tariffid >=", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidLessThan(String value) {
            addCriterion("tariffid <", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidLessThanOrEqualTo(String value) {
            addCriterion("tariffid <=", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidLike(String value) {
            addCriterion("tariffid like", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidNotLike(String value) {
            addCriterion("tariffid not like", value, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidIn(List<String> values) {
            addCriterion("tariffid in", values, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidNotIn(List<String> values) {
            addCriterion("tariffid not in", values, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidBetween(String value1, String value2) {
            addCriterion("tariffid between", value1, value2, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTariffidNotBetween(String value1, String value2) {
            addCriterion("tariffid not between", value1, value2, "tariffid");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNull() {
            addCriterion("taxrate is null");
            return (Criteria) this;
        }

        public Criteria andTaxrateIsNotNull() {
            addCriterion("taxrate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxrateEqualTo(BigDecimal value) {
            addCriterion("taxrate =", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("taxrate <>", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThan(BigDecimal value) {
            addCriterion("taxrate >", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("taxrate >=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThan(BigDecimal value) {
            addCriterion("taxrate <", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("taxrate <=", value, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateIn(List<BigDecimal> values) {
            addCriterion("taxrate in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("taxrate not in", values, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxrate between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("taxrate not between", value1, value2, "taxrate");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodIsNull() {
            addCriterion("paymentmethod is null");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodIsNotNull() {
            addCriterion("paymentmethod is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodEqualTo(String value) {
            addCriterion("paymentmethod =", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodNotEqualTo(String value) {
            addCriterion("paymentmethod <>", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodGreaterThan(String value) {
            addCriterion("paymentmethod >", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodGreaterThanOrEqualTo(String value) {
            addCriterion("paymentmethod >=", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodLessThan(String value) {
            addCriterion("paymentmethod <", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodLessThanOrEqualTo(String value) {
            addCriterion("paymentmethod <=", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodLike(String value) {
            addCriterion("paymentmethod like", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodNotLike(String value) {
            addCriterion("paymentmethod not like", value, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodIn(List<String> values) {
            addCriterion("paymentmethod in", values, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodNotIn(List<String> values) {
            addCriterion("paymentmethod not in", values, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodBetween(String value1, String value2) {
            addCriterion("paymentmethod between", value1, value2, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andPaymentmethodNotBetween(String value1, String value2) {
            addCriterion("paymentmethod not between", value1, value2, "paymentmethod");
            return (Criteria) this;
        }

        public Criteria andNotesIsNull() {
            addCriterion("notes is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("notes is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("notes =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("notes <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("notes >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("notes >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("notes <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("notes <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("notes like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("notes not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("notes in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("notes not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("notes between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("notes not between", value1, value2, "notes");
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

        public Criteria andEdisendflagIsNull() {
            addCriterion("edisendflag is null");
            return (Criteria) this;
        }

        public Criteria andEdisendflagIsNotNull() {
            addCriterion("edisendflag is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendflagEqualTo(String value) {
            addCriterion("edisendflag =", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagNotEqualTo(String value) {
            addCriterion("edisendflag <>", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagGreaterThan(String value) {
            addCriterion("edisendflag >", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagGreaterThanOrEqualTo(String value) {
            addCriterion("edisendflag >=", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagLessThan(String value) {
            addCriterion("edisendflag <", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagLessThanOrEqualTo(String value) {
            addCriterion("edisendflag <=", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagLike(String value) {
            addCriterion("edisendflag like", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagNotLike(String value) {
            addCriterion("edisendflag not like", value, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagIn(List<String> values) {
            addCriterion("edisendflag in", values, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagNotIn(List<String> values) {
            addCriterion("edisendflag not in", values, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagBetween(String value1, String value2) {
            addCriterion("edisendflag between", value1, value2, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdisendflagNotBetween(String value1, String value2) {
            addCriterion("edisendflag not between", value1, value2, "edisendflag");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrIsNull() {
            addCriterion("edierrordescr is null");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrIsNotNull() {
            addCriterion("edierrordescr is not null");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrEqualTo(String value) {
            addCriterion("edierrordescr =", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrNotEqualTo(String value) {
            addCriterion("edierrordescr <>", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrGreaterThan(String value) {
            addCriterion("edierrordescr >", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrGreaterThanOrEqualTo(String value) {
            addCriterion("edierrordescr >=", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrLessThan(String value) {
            addCriterion("edierrordescr <", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrLessThanOrEqualTo(String value) {
            addCriterion("edierrordescr <=", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrLike(String value) {
            addCriterion("edierrordescr like", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrNotLike(String value) {
            addCriterion("edierrordescr not like", value, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrIn(List<String> values) {
            addCriterion("edierrordescr in", values, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrNotIn(List<String> values) {
            addCriterion("edierrordescr not in", values, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrBetween(String value1, String value2) {
            addCriterion("edierrordescr between", value1, value2, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdierrordescrNotBetween(String value1, String value2) {
            addCriterion("edierrordescr not between", value1, value2, "edierrordescr");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeIsNull() {
            addCriterion("ediSendTime is null");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeIsNotNull() {
            addCriterion("ediSendTime is not null");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeEqualTo(Date value) {
            addCriterion("ediSendTime =", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeNotEqualTo(Date value) {
            addCriterion("ediSendTime <>", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeGreaterThan(Date value) {
            addCriterion("ediSendTime >", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ediSendTime >=", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeLessThan(Date value) {
            addCriterion("ediSendTime <", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeLessThanOrEqualTo(Date value) {
            addCriterion("ediSendTime <=", value, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeIn(List<Date> values) {
            addCriterion("ediSendTime in", values, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeNotIn(List<Date> values) {
            addCriterion("ediSendTime not in", values, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeBetween(Date value1, Date value2) {
            addCriterion("ediSendTime between", value1, value2, "edisendtime");
            return (Criteria) this;
        }

        public Criteria andEdisendtimeNotBetween(Date value1, Date value2) {
            addCriterion("ediSendTime not between", value1, value2, "edisendtime");
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
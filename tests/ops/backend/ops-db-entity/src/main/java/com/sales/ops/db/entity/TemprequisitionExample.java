package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemprequisitionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemprequisitionExample() {
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

        public Criteria andRequestnoIsNull() {
            addCriterion("RequestNo is null");
            return (Criteria) this;
        }

        public Criteria andRequestnoIsNotNull() {
            addCriterion("RequestNo is not null");
            return (Criteria) this;
        }

        public Criteria andRequestnoEqualTo(String value) {
            addCriterion("RequestNo =", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoNotEqualTo(String value) {
            addCriterion("RequestNo <>", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoGreaterThan(String value) {
            addCriterion("RequestNo >", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoGreaterThanOrEqualTo(String value) {
            addCriterion("RequestNo >=", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoLessThan(String value) {
            addCriterion("RequestNo <", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoLessThanOrEqualTo(String value) {
            addCriterion("RequestNo <=", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoLike(String value) {
            addCriterion("RequestNo like", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoNotLike(String value) {
            addCriterion("RequestNo not like", value, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoIn(List<String> values) {
            addCriterion("RequestNo in", values, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoNotIn(List<String> values) {
            addCriterion("RequestNo not in", values, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoBetween(String value1, String value2) {
            addCriterion("RequestNo between", value1, value2, "requestno");
            return (Criteria) this;
        }

        public Criteria andRequestnoNotBetween(String value1, String value2) {
            addCriterion("RequestNo not between", value1, value2, "requestno");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNull() {
            addCriterion("ItemNo is null");
            return (Criteria) this;
        }

        public Criteria andItemnoIsNotNull() {
            addCriterion("ItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andItemnoEqualTo(String value) {
            addCriterion("ItemNo =", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotEqualTo(String value) {
            addCriterion("ItemNo <>", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThan(String value) {
            addCriterion("ItemNo >", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoGreaterThanOrEqualTo(String value) {
            addCriterion("ItemNo >=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThan(String value) {
            addCriterion("ItemNo <", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLessThanOrEqualTo(String value) {
            addCriterion("ItemNo <=", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoLike(String value) {
            addCriterion("ItemNo like", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotLike(String value) {
            addCriterion("ItemNo not like", value, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoIn(List<String> values) {
            addCriterion("ItemNo in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotIn(List<String> values) {
            addCriterion("ItemNo not in", values, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoBetween(String value1, String value2) {
            addCriterion("ItemNo between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andItemnoNotBetween(String value1, String value2) {
            addCriterion("ItemNo not between", value1, value2, "itemno");
            return (Criteria) this;
        }

        public Criteria andCustomercodeIsNull() {
            addCriterion("CustomerCode is null");
            return (Criteria) this;
        }

        public Criteria andCustomercodeIsNotNull() {
            addCriterion("CustomerCode is not null");
            return (Criteria) this;
        }

        public Criteria andCustomercodeEqualTo(String value) {
            addCriterion("CustomerCode =", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeNotEqualTo(String value) {
            addCriterion("CustomerCode <>", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeGreaterThan(String value) {
            addCriterion("CustomerCode >", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeGreaterThanOrEqualTo(String value) {
            addCriterion("CustomerCode >=", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeLessThan(String value) {
            addCriterion("CustomerCode <", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeLessThanOrEqualTo(String value) {
            addCriterion("CustomerCode <=", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeLike(String value) {
            addCriterion("CustomerCode like", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeNotLike(String value) {
            addCriterion("CustomerCode not like", value, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeIn(List<String> values) {
            addCriterion("CustomerCode in", values, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeNotIn(List<String> values) {
            addCriterion("CustomerCode not in", values, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeBetween(String value1, String value2) {
            addCriterion("CustomerCode between", value1, value2, "customercode");
            return (Criteria) this;
        }

        public Criteria andCustomercodeNotBetween(String value1, String value2) {
            addCriterion("CustomerCode not between", value1, value2, "customercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeIsNull() {
            addCriterion("AcceptCustomerCode is null");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeIsNotNull() {
            addCriterion("AcceptCustomerCode is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeEqualTo(String value) {
            addCriterion("AcceptCustomerCode =", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeNotEqualTo(String value) {
            addCriterion("AcceptCustomerCode <>", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeGreaterThan(String value) {
            addCriterion("AcceptCustomerCode >", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeGreaterThanOrEqualTo(String value) {
            addCriterion("AcceptCustomerCode >=", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeLessThan(String value) {
            addCriterion("AcceptCustomerCode <", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeLessThanOrEqualTo(String value) {
            addCriterion("AcceptCustomerCode <=", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeLike(String value) {
            addCriterion("AcceptCustomerCode like", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeNotLike(String value) {
            addCriterion("AcceptCustomerCode not like", value, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeIn(List<String> values) {
            addCriterion("AcceptCustomerCode in", values, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeNotIn(List<String> values) {
            addCriterion("AcceptCustomerCode not in", values, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeBetween(String value1, String value2) {
            addCriterion("AcceptCustomerCode between", value1, value2, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andAcceptcustomercodeNotBetween(String value1, String value2) {
            addCriterion("AcceptCustomerCode not between", value1, value2, "acceptcustomercode");
            return (Criteria) this;
        }

        public Criteria andJpkogoIsNull() {
            addCriterion("JPKOGO is null");
            return (Criteria) this;
        }

        public Criteria andJpkogoIsNotNull() {
            addCriterion("JPKOGO is not null");
            return (Criteria) this;
        }

        public Criteria andJpkogoEqualTo(String value) {
            addCriterion("JPKOGO =", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoNotEqualTo(String value) {
            addCriterion("JPKOGO <>", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoGreaterThan(String value) {
            addCriterion("JPKOGO >", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoGreaterThanOrEqualTo(String value) {
            addCriterion("JPKOGO >=", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoLessThan(String value) {
            addCriterion("JPKOGO <", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoLessThanOrEqualTo(String value) {
            addCriterion("JPKOGO <=", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoLike(String value) {
            addCriterion("JPKOGO like", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoNotLike(String value) {
            addCriterion("JPKOGO not like", value, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoIn(List<String> values) {
            addCriterion("JPKOGO in", values, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoNotIn(List<String> values) {
            addCriterion("JPKOGO not in", values, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoBetween(String value1, String value2) {
            addCriterion("JPKOGO between", value1, value2, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoNotBetween(String value1, String value2) {
            addCriterion("JPKOGO not between", value1, value2, "jpkogo");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemIsNull() {
            addCriterion("JPKOGOItem is null");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemIsNotNull() {
            addCriterion("JPKOGOItem is not null");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemEqualTo(String value) {
            addCriterion("JPKOGOItem =", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemNotEqualTo(String value) {
            addCriterion("JPKOGOItem <>", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemGreaterThan(String value) {
            addCriterion("JPKOGOItem >", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemGreaterThanOrEqualTo(String value) {
            addCriterion("JPKOGOItem >=", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemLessThan(String value) {
            addCriterion("JPKOGOItem <", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemLessThanOrEqualTo(String value) {
            addCriterion("JPKOGOItem <=", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemLike(String value) {
            addCriterion("JPKOGOItem like", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemNotLike(String value) {
            addCriterion("JPKOGOItem not like", value, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemIn(List<String> values) {
            addCriterion("JPKOGOItem in", values, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemNotIn(List<String> values) {
            addCriterion("JPKOGOItem not in", values, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemBetween(String value1, String value2) {
            addCriterion("JPKOGOItem between", value1, value2, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andJpkogoitemNotBetween(String value1, String value2) {
            addCriterion("JPKOGOItem not between", value1, value2, "jpkogoitem");
            return (Criteria) this;
        }

        public Criteria andRequestmodelIsNull() {
            addCriterion("RequestModel is null");
            return (Criteria) this;
        }

        public Criteria andRequestmodelIsNotNull() {
            addCriterion("RequestModel is not null");
            return (Criteria) this;
        }

        public Criteria andRequestmodelEqualTo(String value) {
            addCriterion("RequestModel =", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelNotEqualTo(String value) {
            addCriterion("RequestModel <>", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelGreaterThan(String value) {
            addCriterion("RequestModel >", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelGreaterThanOrEqualTo(String value) {
            addCriterion("RequestModel >=", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelLessThan(String value) {
            addCriterion("RequestModel <", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelLessThanOrEqualTo(String value) {
            addCriterion("RequestModel <=", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelLike(String value) {
            addCriterion("RequestModel like", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelNotLike(String value) {
            addCriterion("RequestModel not like", value, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelIn(List<String> values) {
            addCriterion("RequestModel in", values, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelNotIn(List<String> values) {
            addCriterion("RequestModel not in", values, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelBetween(String value1, String value2) {
            addCriterion("RequestModel between", value1, value2, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestmodelNotBetween(String value1, String value2) {
            addCriterion("RequestModel not between", value1, value2, "requestmodel");
            return (Criteria) this;
        }

        public Criteria andRequestqtyIsNull() {
            addCriterion("RequestQty is null");
            return (Criteria) this;
        }

        public Criteria andRequestqtyIsNotNull() {
            addCriterion("RequestQty is not null");
            return (Criteria) this;
        }

        public Criteria andRequestqtyEqualTo(Integer value) {
            addCriterion("RequestQty =", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyNotEqualTo(Integer value) {
            addCriterion("RequestQty <>", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyGreaterThan(Integer value) {
            addCriterion("RequestQty >", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("RequestQty >=", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyLessThan(Integer value) {
            addCriterion("RequestQty <", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyLessThanOrEqualTo(Integer value) {
            addCriterion("RequestQty <=", value, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyIn(List<Integer> values) {
            addCriterion("RequestQty in", values, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyNotIn(List<Integer> values) {
            addCriterion("RequestQty not in", values, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyBetween(Integer value1, Integer value2) {
            addCriterion("RequestQty between", value1, value2, "requestqty");
            return (Criteria) this;
        }

        public Criteria andRequestqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("RequestQty not between", value1, value2, "requestqty");
            return (Criteria) this;
        }

        public Criteria andIssuedateIsNull() {
            addCriterion("IssueDate is null");
            return (Criteria) this;
        }

        public Criteria andIssuedateIsNotNull() {
            addCriterion("IssueDate is not null");
            return (Criteria) this;
        }

        public Criteria andIssuedateEqualTo(Date value) {
            addCriterion("IssueDate =", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotEqualTo(Date value) {
            addCriterion("IssueDate <>", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateGreaterThan(Date value) {
            addCriterion("IssueDate >", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateGreaterThanOrEqualTo(Date value) {
            addCriterion("IssueDate >=", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateLessThan(Date value) {
            addCriterion("IssueDate <", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateLessThanOrEqualTo(Date value) {
            addCriterion("IssueDate <=", value, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateIn(List<Date> values) {
            addCriterion("IssueDate in", values, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotIn(List<Date> values) {
            addCriterion("IssueDate not in", values, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateBetween(Date value1, Date value2) {
            addCriterion("IssueDate between", value1, value2, "issuedate");
            return (Criteria) this;
        }

        public Criteria andIssuedateNotBetween(Date value1, Date value2) {
            addCriterion("IssueDate not between", value1, value2, "issuedate");
            return (Criteria) this;
        }

        public Criteria andInstrtypeIsNull() {
            addCriterion("InstrType is null");
            return (Criteria) this;
        }

        public Criteria andInstrtypeIsNotNull() {
            addCriterion("InstrType is not null");
            return (Criteria) this;
        }

        public Criteria andInstrtypeEqualTo(String value) {
            addCriterion("InstrType =", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeNotEqualTo(String value) {
            addCriterion("InstrType <>", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeGreaterThan(String value) {
            addCriterion("InstrType >", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeGreaterThanOrEqualTo(String value) {
            addCriterion("InstrType >=", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeLessThan(String value) {
            addCriterion("InstrType <", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeLessThanOrEqualTo(String value) {
            addCriterion("InstrType <=", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeLike(String value) {
            addCriterion("InstrType like", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeNotLike(String value) {
            addCriterion("InstrType not like", value, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeIn(List<String> values) {
            addCriterion("InstrType in", values, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeNotIn(List<String> values) {
            addCriterion("InstrType not in", values, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeBetween(String value1, String value2) {
            addCriterion("InstrType between", value1, value2, "instrtype");
            return (Criteria) this;
        }

        public Criteria andInstrtypeNotBetween(String value1, String value2) {
            addCriterion("InstrType not between", value1, value2, "instrtype");
            return (Criteria) this;
        }

        public Criteria andDlvywayIsNull() {
            addCriterion("DlvyWay is null");
            return (Criteria) this;
        }

        public Criteria andDlvywayIsNotNull() {
            addCriterion("DlvyWay is not null");
            return (Criteria) this;
        }

        public Criteria andDlvywayEqualTo(String value) {
            addCriterion("DlvyWay =", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayNotEqualTo(String value) {
            addCriterion("DlvyWay <>", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayGreaterThan(String value) {
            addCriterion("DlvyWay >", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayGreaterThanOrEqualTo(String value) {
            addCriterion("DlvyWay >=", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayLessThan(String value) {
            addCriterion("DlvyWay <", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayLessThanOrEqualTo(String value) {
            addCriterion("DlvyWay <=", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayLike(String value) {
            addCriterion("DlvyWay like", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayNotLike(String value) {
            addCriterion("DlvyWay not like", value, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayIn(List<String> values) {
            addCriterion("DlvyWay in", values, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayNotIn(List<String> values) {
            addCriterion("DlvyWay not in", values, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayBetween(String value1, String value2) {
            addCriterion("DlvyWay between", value1, value2, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andDlvywayNotBetween(String value1, String value2) {
            addCriterion("DlvyWay not between", value1, value2, "dlvyway");
            return (Criteria) this;
        }

        public Criteria andJpordernoIsNull() {
            addCriterion("JPOrderNo is null");
            return (Criteria) this;
        }

        public Criteria andJpordernoIsNotNull() {
            addCriterion("JPOrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andJpordernoEqualTo(String value) {
            addCriterion("JPOrderNo =", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoNotEqualTo(String value) {
            addCriterion("JPOrderNo <>", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoGreaterThan(String value) {
            addCriterion("JPOrderNo >", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoGreaterThanOrEqualTo(String value) {
            addCriterion("JPOrderNo >=", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoLessThan(String value) {
            addCriterion("JPOrderNo <", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoLessThanOrEqualTo(String value) {
            addCriterion("JPOrderNo <=", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoLike(String value) {
            addCriterion("JPOrderNo like", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoNotLike(String value) {
            addCriterion("JPOrderNo not like", value, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoIn(List<String> values) {
            addCriterion("JPOrderNo in", values, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoNotIn(List<String> values) {
            addCriterion("JPOrderNo not in", values, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoBetween(String value1, String value2) {
            addCriterion("JPOrderNo between", value1, value2, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpordernoNotBetween(String value1, String value2) {
            addCriterion("JPOrderNo not between", value1, value2, "jporderno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoIsNull() {
            addCriterion("JPItemNo is null");
            return (Criteria) this;
        }

        public Criteria andJpitemnoIsNotNull() {
            addCriterion("JPItemNo is not null");
            return (Criteria) this;
        }

        public Criteria andJpitemnoEqualTo(String value) {
            addCriterion("JPItemNo =", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoNotEqualTo(String value) {
            addCriterion("JPItemNo <>", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoGreaterThan(String value) {
            addCriterion("JPItemNo >", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoGreaterThanOrEqualTo(String value) {
            addCriterion("JPItemNo >=", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoLessThan(String value) {
            addCriterion("JPItemNo <", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoLessThanOrEqualTo(String value) {
            addCriterion("JPItemNo <=", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoLike(String value) {
            addCriterion("JPItemNo like", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoNotLike(String value) {
            addCriterion("JPItemNo not like", value, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoIn(List<String> values) {
            addCriterion("JPItemNo in", values, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoNotIn(List<String> values) {
            addCriterion("JPItemNo not in", values, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoBetween(String value1, String value2) {
            addCriterion("JPItemNo between", value1, value2, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpitemnoNotBetween(String value1, String value2) {
            addCriterion("JPItemNo not between", value1, value2, "jpitemno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoIsNull() {
            addCriterion("JPShelfNo is null");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoIsNotNull() {
            addCriterion("JPShelfNo is not null");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoEqualTo(String value) {
            addCriterion("JPShelfNo =", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoNotEqualTo(String value) {
            addCriterion("JPShelfNo <>", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoGreaterThan(String value) {
            addCriterion("JPShelfNo >", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoGreaterThanOrEqualTo(String value) {
            addCriterion("JPShelfNo >=", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoLessThan(String value) {
            addCriterion("JPShelfNo <", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoLessThanOrEqualTo(String value) {
            addCriterion("JPShelfNo <=", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoLike(String value) {
            addCriterion("JPShelfNo like", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoNotLike(String value) {
            addCriterion("JPShelfNo not like", value, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoIn(List<String> values) {
            addCriterion("JPShelfNo in", values, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoNotIn(List<String> values) {
            addCriterion("JPShelfNo not in", values, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoBetween(String value1, String value2) {
            addCriterion("JPShelfNo between", value1, value2, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andJpshelfnoNotBetween(String value1, String value2) {
            addCriterion("JPShelfNo not between", value1, value2, "jpshelfno");
            return (Criteria) this;
        }

        public Criteria andGatenoIsNull() {
            addCriterion("GateNo is null");
            return (Criteria) this;
        }

        public Criteria andGatenoIsNotNull() {
            addCriterion("GateNo is not null");
            return (Criteria) this;
        }

        public Criteria andGatenoEqualTo(String value) {
            addCriterion("GateNo =", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoNotEqualTo(String value) {
            addCriterion("GateNo <>", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoGreaterThan(String value) {
            addCriterion("GateNo >", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoGreaterThanOrEqualTo(String value) {
            addCriterion("GateNo >=", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoLessThan(String value) {
            addCriterion("GateNo <", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoLessThanOrEqualTo(String value) {
            addCriterion("GateNo <=", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoLike(String value) {
            addCriterion("GateNo like", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoNotLike(String value) {
            addCriterion("GateNo not like", value, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoIn(List<String> values) {
            addCriterion("GateNo in", values, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoNotIn(List<String> values) {
            addCriterion("GateNo not in", values, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoBetween(String value1, String value2) {
            addCriterion("GateNo between", value1, value2, "gateno");
            return (Criteria) this;
        }

        public Criteria andGatenoNotBetween(String value1, String value2) {
            addCriterion("GateNo not between", value1, value2, "gateno");
            return (Criteria) this;
        }

        public Criteria andZonemarkIsNull() {
            addCriterion("ZoneMark is null");
            return (Criteria) this;
        }

        public Criteria andZonemarkIsNotNull() {
            addCriterion("ZoneMark is not null");
            return (Criteria) this;
        }

        public Criteria andZonemarkEqualTo(String value) {
            addCriterion("ZoneMark =", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkNotEqualTo(String value) {
            addCriterion("ZoneMark <>", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkGreaterThan(String value) {
            addCriterion("ZoneMark >", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkGreaterThanOrEqualTo(String value) {
            addCriterion("ZoneMark >=", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkLessThan(String value) {
            addCriterion("ZoneMark <", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkLessThanOrEqualTo(String value) {
            addCriterion("ZoneMark <=", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkLike(String value) {
            addCriterion("ZoneMark like", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkNotLike(String value) {
            addCriterion("ZoneMark not like", value, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkIn(List<String> values) {
            addCriterion("ZoneMark in", values, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkNotIn(List<String> values) {
            addCriterion("ZoneMark not in", values, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkBetween(String value1, String value2) {
            addCriterion("ZoneMark between", value1, value2, "zonemark");
            return (Criteria) this;
        }

        public Criteria andZonemarkNotBetween(String value1, String value2) {
            addCriterion("ZoneMark not between", value1, value2, "zonemark");
            return (Criteria) this;
        }

        public Criteria andJpremarksIsNull() {
            addCriterion("JPRemarks is null");
            return (Criteria) this;
        }

        public Criteria andJpremarksIsNotNull() {
            addCriterion("JPRemarks is not null");
            return (Criteria) this;
        }

        public Criteria andJpremarksEqualTo(String value) {
            addCriterion("JPRemarks =", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksNotEqualTo(String value) {
            addCriterion("JPRemarks <>", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksGreaterThan(String value) {
            addCriterion("JPRemarks >", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksGreaterThanOrEqualTo(String value) {
            addCriterion("JPRemarks >=", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksLessThan(String value) {
            addCriterion("JPRemarks <", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksLessThanOrEqualTo(String value) {
            addCriterion("JPRemarks <=", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksLike(String value) {
            addCriterion("JPRemarks like", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksNotLike(String value) {
            addCriterion("JPRemarks not like", value, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksIn(List<String> values) {
            addCriterion("JPRemarks in", values, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksNotIn(List<String> values) {
            addCriterion("JPRemarks not in", values, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksBetween(String value1, String value2) {
            addCriterion("JPRemarks between", value1, value2, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andJpremarksNotBetween(String value1, String value2) {
            addCriterion("JPRemarks not between", value1, value2, "jpremarks");
            return (Criteria) this;
        }

        public Criteria andAccepterIsNull() {
            addCriterion("Accepter is null");
            return (Criteria) this;
        }

        public Criteria andAccepterIsNotNull() {
            addCriterion("Accepter is not null");
            return (Criteria) this;
        }

        public Criteria andAccepterEqualTo(String value) {
            addCriterion("Accepter =", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterNotEqualTo(String value) {
            addCriterion("Accepter <>", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterGreaterThan(String value) {
            addCriterion("Accepter >", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterGreaterThanOrEqualTo(String value) {
            addCriterion("Accepter >=", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterLessThan(String value) {
            addCriterion("Accepter <", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterLessThanOrEqualTo(String value) {
            addCriterion("Accepter <=", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterLike(String value) {
            addCriterion("Accepter like", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterNotLike(String value) {
            addCriterion("Accepter not like", value, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterIn(List<String> values) {
            addCriterion("Accepter in", values, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterNotIn(List<String> values) {
            addCriterion("Accepter not in", values, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterBetween(String value1, String value2) {
            addCriterion("Accepter between", value1, value2, "accepter");
            return (Criteria) this;
        }

        public Criteria andAccepterNotBetween(String value1, String value2) {
            addCriterion("Accepter not between", value1, value2, "accepter");
            return (Criteria) this;
        }

        public Criteria andRequesttypeIsNull() {
            addCriterion("RequestType is null");
            return (Criteria) this;
        }

        public Criteria andRequesttypeIsNotNull() {
            addCriterion("RequestType is not null");
            return (Criteria) this;
        }

        public Criteria andRequesttypeEqualTo(Byte value) {
            addCriterion("RequestType =", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeNotEqualTo(Byte value) {
            addCriterion("RequestType <>", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeGreaterThan(Byte value) {
            addCriterion("RequestType >", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("RequestType >=", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeLessThan(Byte value) {
            addCriterion("RequestType <", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeLessThanOrEqualTo(Byte value) {
            addCriterion("RequestType <=", value, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeIn(List<Byte> values) {
            addCriterion("RequestType in", values, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeNotIn(List<Byte> values) {
            addCriterion("RequestType not in", values, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeBetween(Byte value1, Byte value2) {
            addCriterion("RequestType between", value1, value2, "requesttype");
            return (Criteria) this;
        }

        public Criteria andRequesttypeNotBetween(Byte value1, Byte value2) {
            addCriterion("RequestType not between", value1, value2, "requesttype");
            return (Criteria) this;
        }

        public Criteria andSortnoIsNull() {
            addCriterion("SortNo is null");
            return (Criteria) this;
        }

        public Criteria andSortnoIsNotNull() {
            addCriterion("SortNo is not null");
            return (Criteria) this;
        }

        public Criteria andSortnoEqualTo(String value) {
            addCriterion("SortNo =", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotEqualTo(String value) {
            addCriterion("SortNo <>", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoGreaterThan(String value) {
            addCriterion("SortNo >", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoGreaterThanOrEqualTo(String value) {
            addCriterion("SortNo >=", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoLessThan(String value) {
            addCriterion("SortNo <", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoLessThanOrEqualTo(String value) {
            addCriterion("SortNo <=", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoLike(String value) {
            addCriterion("SortNo like", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotLike(String value) {
            addCriterion("SortNo not like", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoIn(List<String> values) {
            addCriterion("SortNo in", values, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotIn(List<String> values) {
            addCriterion("SortNo not in", values, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoBetween(String value1, String value2) {
            addCriterion("SortNo between", value1, value2, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotBetween(String value1, String value2) {
            addCriterion("SortNo not between", value1, value2, "sortno");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateIsNull() {
            addCriterion("ReqDlvyDate is null");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateIsNotNull() {
            addCriterion("ReqDlvyDate is not null");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateEqualTo(Date value) {
            addCriterion("ReqDlvyDate =", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateNotEqualTo(Date value) {
            addCriterion("ReqDlvyDate <>", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateGreaterThan(Date value) {
            addCriterion("ReqDlvyDate >", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateGreaterThanOrEqualTo(Date value) {
            addCriterion("ReqDlvyDate >=", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateLessThan(Date value) {
            addCriterion("ReqDlvyDate <", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateLessThanOrEqualTo(Date value) {
            addCriterion("ReqDlvyDate <=", value, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateIn(List<Date> values) {
            addCriterion("ReqDlvyDate in", values, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateNotIn(List<Date> values) {
            addCriterion("ReqDlvyDate not in", values, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateBetween(Date value1, Date value2) {
            addCriterion("ReqDlvyDate between", value1, value2, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andReqdlvydateNotBetween(Date value1, Date value2) {
            addCriterion("ReqDlvyDate not between", value1, value2, "reqdlvydate");
            return (Criteria) this;
        }

        public Criteria andBagtypeIsNull() {
            addCriterion("BagType is null");
            return (Criteria) this;
        }

        public Criteria andBagtypeIsNotNull() {
            addCriterion("BagType is not null");
            return (Criteria) this;
        }

        public Criteria andBagtypeEqualTo(String value) {
            addCriterion("BagType =", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeNotEqualTo(String value) {
            addCriterion("BagType <>", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeGreaterThan(String value) {
            addCriterion("BagType >", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeGreaterThanOrEqualTo(String value) {
            addCriterion("BagType >=", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeLessThan(String value) {
            addCriterion("BagType <", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeLessThanOrEqualTo(String value) {
            addCriterion("BagType <=", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeLike(String value) {
            addCriterion("BagType like", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeNotLike(String value) {
            addCriterion("BagType not like", value, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeIn(List<String> values) {
            addCriterion("BagType in", values, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeNotIn(List<String> values) {
            addCriterion("BagType not in", values, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeBetween(String value1, String value2) {
            addCriterion("BagType between", value1, value2, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagtypeNotBetween(String value1, String value2) {
            addCriterion("BagType not between", value1, value2, "bagtype");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyIsNull() {
            addCriterion("BagFixedQty is null");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyIsNotNull() {
            addCriterion("BagFixedQty is not null");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyEqualTo(Integer value) {
            addCriterion("BagFixedQty =", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyNotEqualTo(Integer value) {
            addCriterion("BagFixedQty <>", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyGreaterThan(Integer value) {
            addCriterion("BagFixedQty >", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("BagFixedQty >=", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyLessThan(Integer value) {
            addCriterion("BagFixedQty <", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyLessThanOrEqualTo(Integer value) {
            addCriterion("BagFixedQty <=", value, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyIn(List<Integer> values) {
            addCriterion("BagFixedQty in", values, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyNotIn(List<Integer> values) {
            addCriterion("BagFixedQty not in", values, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyBetween(Integer value1, Integer value2) {
            addCriterion("BagFixedQty between", value1, value2, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBagfixedqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("BagFixedQty not between", value1, value2, "bagfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyIsNull() {
            addCriterion("BoxFixedQty is null");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyIsNotNull() {
            addCriterion("BoxFixedQty is not null");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyEqualTo(Integer value) {
            addCriterion("BoxFixedQty =", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyNotEqualTo(Integer value) {
            addCriterion("BoxFixedQty <>", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyGreaterThan(Integer value) {
            addCriterion("BoxFixedQty >", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("BoxFixedQty >=", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyLessThan(Integer value) {
            addCriterion("BoxFixedQty <", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyLessThanOrEqualTo(Integer value) {
            addCriterion("BoxFixedQty <=", value, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyIn(List<Integer> values) {
            addCriterion("BoxFixedQty in", values, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyNotIn(List<Integer> values) {
            addCriterion("BoxFixedQty not in", values, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyBetween(Integer value1, Integer value2) {
            addCriterion("BoxFixedQty between", value1, value2, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxfixedqtyNotBetween(Integer value1, Integer value2) {
            addCriterion("BoxFixedQty not between", value1, value2, "boxfixedqty");
            return (Criteria) this;
        }

        public Criteria andBoxtypeIsNull() {
            addCriterion("BoxType is null");
            return (Criteria) this;
        }

        public Criteria andBoxtypeIsNotNull() {
            addCriterion("BoxType is not null");
            return (Criteria) this;
        }

        public Criteria andBoxtypeEqualTo(String value) {
            addCriterion("BoxType =", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeNotEqualTo(String value) {
            addCriterion("BoxType <>", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeGreaterThan(String value) {
            addCriterion("BoxType >", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeGreaterThanOrEqualTo(String value) {
            addCriterion("BoxType >=", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeLessThan(String value) {
            addCriterion("BoxType <", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeLessThanOrEqualTo(String value) {
            addCriterion("BoxType <=", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeLike(String value) {
            addCriterion("BoxType like", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeNotLike(String value) {
            addCriterion("BoxType not like", value, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeIn(List<String> values) {
            addCriterion("BoxType in", values, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeNotIn(List<String> values) {
            addCriterion("BoxType not in", values, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeBetween(String value1, String value2) {
            addCriterion("BoxType between", value1, value2, "boxtype");
            return (Criteria) this;
        }

        public Criteria andBoxtypeNotBetween(String value1, String value2) {
            addCriterion("BoxType not between", value1, value2, "boxtype");
            return (Criteria) this;
        }

        public Criteria andSimplemodelIsNull() {
            addCriterion("SimpleModel is null");
            return (Criteria) this;
        }

        public Criteria andSimplemodelIsNotNull() {
            addCriterion("SimpleModel is not null");
            return (Criteria) this;
        }

        public Criteria andSimplemodelEqualTo(String value) {
            addCriterion("SimpleModel =", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelNotEqualTo(String value) {
            addCriterion("SimpleModel <>", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelGreaterThan(String value) {
            addCriterion("SimpleModel >", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelGreaterThanOrEqualTo(String value) {
            addCriterion("SimpleModel >=", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelLessThan(String value) {
            addCriterion("SimpleModel <", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelLessThanOrEqualTo(String value) {
            addCriterion("SimpleModel <=", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelLike(String value) {
            addCriterion("SimpleModel like", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelNotLike(String value) {
            addCriterion("SimpleModel not like", value, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelIn(List<String> values) {
            addCriterion("SimpleModel in", values, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelNotIn(List<String> values) {
            addCriterion("SimpleModel not in", values, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelBetween(String value1, String value2) {
            addCriterion("SimpleModel between", value1, value2, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andSimplemodelNotBetween(String value1, String value2) {
            addCriterion("SimpleModel not between", value1, value2, "simplemodel");
            return (Criteria) this;
        }

        public Criteria andPacktypeIsNull() {
            addCriterion("PackType is null");
            return (Criteria) this;
        }

        public Criteria andPacktypeIsNotNull() {
            addCriterion("PackType is not null");
            return (Criteria) this;
        }

        public Criteria andPacktypeEqualTo(String value) {
            addCriterion("PackType =", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotEqualTo(String value) {
            addCriterion("PackType <>", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeGreaterThan(String value) {
            addCriterion("PackType >", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeGreaterThanOrEqualTo(String value) {
            addCriterion("PackType >=", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLessThan(String value) {
            addCriterion("PackType <", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLessThanOrEqualTo(String value) {
            addCriterion("PackType <=", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeLike(String value) {
            addCriterion("PackType like", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotLike(String value) {
            addCriterion("PackType not like", value, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeIn(List<String> values) {
            addCriterion("PackType in", values, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotIn(List<String> values) {
            addCriterion("PackType not in", values, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeBetween(String value1, String value2) {
            addCriterion("PackType between", value1, value2, "packtype");
            return (Criteria) this;
        }

        public Criteria andPacktypeNotBetween(String value1, String value2) {
            addCriterion("PackType not between", value1, value2, "packtype");
            return (Criteria) this;
        }

        public Criteria andGreenmarkIsNull() {
            addCriterion("GreenMark is null");
            return (Criteria) this;
        }

        public Criteria andGreenmarkIsNotNull() {
            addCriterion("GreenMark is not null");
            return (Criteria) this;
        }

        public Criteria andGreenmarkEqualTo(String value) {
            addCriterion("GreenMark =", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkNotEqualTo(String value) {
            addCriterion("GreenMark <>", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkGreaterThan(String value) {
            addCriterion("GreenMark >", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkGreaterThanOrEqualTo(String value) {
            addCriterion("GreenMark >=", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkLessThan(String value) {
            addCriterion("GreenMark <", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkLessThanOrEqualTo(String value) {
            addCriterion("GreenMark <=", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkLike(String value) {
            addCriterion("GreenMark like", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkNotLike(String value) {
            addCriterion("GreenMark not like", value, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkIn(List<String> values) {
            addCriterion("GreenMark in", values, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkNotIn(List<String> values) {
            addCriterion("GreenMark not in", values, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkBetween(String value1, String value2) {
            addCriterion("GreenMark between", value1, value2, "greenmark");
            return (Criteria) this;
        }

        public Criteria andGreenmarkNotBetween(String value1, String value2) {
            addCriterion("GreenMark not between", value1, value2, "greenmark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkIsNull() {
            addCriterion("JPSerialNoMark is null");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkIsNotNull() {
            addCriterion("JPSerialNoMark is not null");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkEqualTo(String value) {
            addCriterion("JPSerialNoMark =", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkNotEqualTo(String value) {
            addCriterion("JPSerialNoMark <>", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkGreaterThan(String value) {
            addCriterion("JPSerialNoMark >", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkGreaterThanOrEqualTo(String value) {
            addCriterion("JPSerialNoMark >=", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkLessThan(String value) {
            addCriterion("JPSerialNoMark <", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkLessThanOrEqualTo(String value) {
            addCriterion("JPSerialNoMark <=", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkLike(String value) {
            addCriterion("JPSerialNoMark like", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkNotLike(String value) {
            addCriterion("JPSerialNoMark not like", value, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkIn(List<String> values) {
            addCriterion("JPSerialNoMark in", values, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkNotIn(List<String> values) {
            addCriterion("JPSerialNoMark not in", values, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkBetween(String value1, String value2) {
            addCriterion("JPSerialNoMark between", value1, value2, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andJpserialnomarkNotBetween(String value1, String value2) {
            addCriterion("JPSerialNoMark not between", value1, value2, "jpserialnomark");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNull() {
            addCriterion("Midsize is null");
            return (Criteria) this;
        }

        public Criteria andMidsizeIsNotNull() {
            addCriterion("Midsize is not null");
            return (Criteria) this;
        }

        public Criteria andMidsizeEqualTo(String value) {
            addCriterion("Midsize =", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotEqualTo(String value) {
            addCriterion("Midsize <>", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThan(String value) {
            addCriterion("Midsize >", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeGreaterThanOrEqualTo(String value) {
            addCriterion("Midsize >=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThan(String value) {
            addCriterion("Midsize <", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLessThanOrEqualTo(String value) {
            addCriterion("Midsize <=", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeLike(String value) {
            addCriterion("Midsize like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotLike(String value) {
            addCriterion("Midsize not like", value, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeIn(List<String> values) {
            addCriterion("Midsize in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotIn(List<String> values) {
            addCriterion("Midsize not in", values, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeBetween(String value1, String value2) {
            addCriterion("Midsize between", value1, value2, "midsize");
            return (Criteria) this;
        }

        public Criteria andMidsizeNotBetween(String value1, String value2) {
            addCriterion("Midsize not between", value1, value2, "midsize");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNull() {
            addCriterion("prodflag is null");
            return (Criteria) this;
        }

        public Criteria andProdflagIsNotNull() {
            addCriterion("prodflag is not null");
            return (Criteria) this;
        }

        public Criteria andProdflagEqualTo(String value) {
            addCriterion("prodflag =", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotEqualTo(String value) {
            addCriterion("prodflag <>", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThan(String value) {
            addCriterion("prodflag >", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagGreaterThanOrEqualTo(String value) {
            addCriterion("prodflag >=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThan(String value) {
            addCriterion("prodflag <", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLessThanOrEqualTo(String value) {
            addCriterion("prodflag <=", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagLike(String value) {
            addCriterion("prodflag like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotLike(String value) {
            addCriterion("prodflag not like", value, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagIn(List<String> values) {
            addCriterion("prodflag in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotIn(List<String> values) {
            addCriterion("prodflag not in", values, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagBetween(String value1, String value2) {
            addCriterion("prodflag between", value1, value2, "prodflag");
            return (Criteria) this;
        }

        public Criteria andProdflagNotBetween(String value1, String value2) {
            addCriterion("prodflag not between", value1, value2, "prodflag");
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

        public Criteria andUsernoIsNull() {
            addCriterion("UserNo is null");
            return (Criteria) this;
        }

        public Criteria andUsernoIsNotNull() {
            addCriterion("UserNo is not null");
            return (Criteria) this;
        }

        public Criteria andUsernoEqualTo(String value) {
            addCriterion("UserNo =", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotEqualTo(String value) {
            addCriterion("UserNo <>", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThan(String value) {
            addCriterion("UserNo >", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoGreaterThanOrEqualTo(String value) {
            addCriterion("UserNo >=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThan(String value) {
            addCriterion("UserNo <", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLessThanOrEqualTo(String value) {
            addCriterion("UserNo <=", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoLike(String value) {
            addCriterion("UserNo like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotLike(String value) {
            addCriterion("UserNo not like", value, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoIn(List<String> values) {
            addCriterion("UserNo in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotIn(List<String> values) {
            addCriterion("UserNo not in", values, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoBetween(String value1, String value2) {
            addCriterion("UserNo between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernoNotBetween(String value1, String value2) {
            addCriterion("UserNo not between", value1, value2, "userno");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("UserName is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("UserName is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("UserName =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("UserName <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("UserName >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("UserName >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("UserName <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("UserName <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("UserName like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("UserName not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("UserName in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("UserName not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("UserName between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("UserName not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNull() {
            addCriterion("OptCode is null");
            return (Criteria) this;
        }

        public Criteria andOptcodeIsNotNull() {
            addCriterion("OptCode is not null");
            return (Criteria) this;
        }

        public Criteria andOptcodeEqualTo(String value) {
            addCriterion("OptCode =", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotEqualTo(String value) {
            addCriterion("OptCode <>", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThan(String value) {
            addCriterion("OptCode >", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeGreaterThanOrEqualTo(String value) {
            addCriterion("OptCode >=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThan(String value) {
            addCriterion("OptCode <", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLessThanOrEqualTo(String value) {
            addCriterion("OptCode <=", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeLike(String value) {
            addCriterion("OptCode like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotLike(String value) {
            addCriterion("OptCode not like", value, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeIn(List<String> values) {
            addCriterion("OptCode in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotIn(List<String> values) {
            addCriterion("OptCode not in", values, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeBetween(String value1, String value2) {
            addCriterion("OptCode between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andOptcodeNotBetween(String value1, String value2) {
            addCriterion("OptCode not between", value1, value2, "optcode");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNull() {
            addCriterion("ContractNo is null");
            return (Criteria) this;
        }

        public Criteria andContractnoIsNotNull() {
            addCriterion("ContractNo is not null");
            return (Criteria) this;
        }

        public Criteria andContractnoEqualTo(String value) {
            addCriterion("ContractNo =", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotEqualTo(String value) {
            addCriterion("ContractNo <>", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThan(String value) {
            addCriterion("ContractNo >", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoGreaterThanOrEqualTo(String value) {
            addCriterion("ContractNo >=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThan(String value) {
            addCriterion("ContractNo <", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLessThanOrEqualTo(String value) {
            addCriterion("ContractNo <=", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoLike(String value) {
            addCriterion("ContractNo like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotLike(String value) {
            addCriterion("ContractNo not like", value, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoIn(List<String> values) {
            addCriterion("ContractNo in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotIn(List<String> values) {
            addCriterion("ContractNo not in", values, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoBetween(String value1, String value2) {
            addCriterion("ContractNo between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractnoNotBetween(String value1, String value2) {
            addCriterion("ContractNo not between", value1, value2, "contractno");
            return (Criteria) this;
        }

        public Criteria andContractremnantIsNull() {
            addCriterion("ContractRemnant is null");
            return (Criteria) this;
        }

        public Criteria andContractremnantIsNotNull() {
            addCriterion("ContractRemnant is not null");
            return (Criteria) this;
        }

        public Criteria andContractremnantEqualTo(Integer value) {
            addCriterion("ContractRemnant =", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantNotEqualTo(Integer value) {
            addCriterion("ContractRemnant <>", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantGreaterThan(Integer value) {
            addCriterion("ContractRemnant >", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantGreaterThanOrEqualTo(Integer value) {
            addCriterion("ContractRemnant >=", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantLessThan(Integer value) {
            addCriterion("ContractRemnant <", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantLessThanOrEqualTo(Integer value) {
            addCriterion("ContractRemnant <=", value, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantIn(List<Integer> values) {
            addCriterion("ContractRemnant in", values, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantNotIn(List<Integer> values) {
            addCriterion("ContractRemnant not in", values, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantBetween(Integer value1, Integer value2) {
            addCriterion("ContractRemnant between", value1, value2, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andContractremnantNotBetween(Integer value1, Integer value2) {
            addCriterion("ContractRemnant not between", value1, value2, "contractremnant");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNull() {
            addCriterion("SalesInfoNo is null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIsNotNull() {
            addCriterion("SalesInfoNo is not null");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoEqualTo(String value) {
            addCriterion("SalesInfoNo =", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotEqualTo(String value) {
            addCriterion("SalesInfoNo <>", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThan(String value) {
            addCriterion("SalesInfoNo >", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoGreaterThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo >=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThan(String value) {
            addCriterion("SalesInfoNo <", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLessThanOrEqualTo(String value) {
            addCriterion("SalesInfoNo <=", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoLike(String value) {
            addCriterion("SalesInfoNo like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotLike(String value) {
            addCriterion("SalesInfoNo not like", value, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoIn(List<String> values) {
            addCriterion("SalesInfoNo in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotIn(List<String> values) {
            addCriterion("SalesInfoNo not in", values, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoBetween(String value1, String value2) {
            addCriterion("SalesInfoNo between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andSalesinfonoNotBetween(String value1, String value2) {
            addCriterion("SalesInfoNo not between", value1, value2, "salesinfono");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNull() {
            addCriterion("InDate_theory is null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIsNotNull() {
            addCriterion("InDate_theory is not null");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryEqualTo(Date value) {
            addCriterion("InDate_theory =", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotEqualTo(Date value) {
            addCriterion("InDate_theory <>", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThan(Date value) {
            addCriterion("InDate_theory >", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_theory >=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThan(Date value) {
            addCriterion("InDate_theory <", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryLessThanOrEqualTo(Date value) {
            addCriterion("InDate_theory <=", value, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryIn(List<Date> values) {
            addCriterion("InDate_theory in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotIn(List<Date> values) {
            addCriterion("InDate_theory not in", values, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryBetween(Date value1, Date value2) {
            addCriterion("InDate_theory between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andIndateTheoryNotBetween(Date value1, Date value2) {
            addCriterion("InDate_theory not between", value1, value2, "indateTheory");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNull() {
            addCriterion("RcvOrd_Date is null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIsNotNull() {
            addCriterion("RcvOrd_Date is not null");
            return (Criteria) this;
        }

        public Criteria andRcvordDateEqualTo(Date value) {
            addCriterion("RcvOrd_Date =", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotEqualTo(Date value) {
            addCriterion("RcvOrd_Date <>", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThan(Date value) {
            addCriterion("RcvOrd_Date >", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateGreaterThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date >=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThan(Date value) {
            addCriterion("RcvOrd_Date <", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateLessThanOrEqualTo(Date value) {
            addCriterion("RcvOrd_Date <=", value, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateIn(List<Date> values) {
            addCriterion("RcvOrd_Date in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotIn(List<Date> values) {
            addCriterion("RcvOrd_Date not in", values, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andRcvordDateNotBetween(Date value1, Date value2) {
            addCriterion("RcvOrd_Date not between", value1, value2, "rcvordDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNull() {
            addCriterion("BeginProduce_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIsNotNull() {
            addCriterion("BeginProduce_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateEqualTo(Date value) {
            addCriterion("BeginProduce_date =", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotEqualTo(Date value) {
            addCriterion("BeginProduce_date <>", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThan(Date value) {
            addCriterion("BeginProduce_date >", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateGreaterThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date >=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThan(Date value) {
            addCriterion("BeginProduce_date <", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateLessThanOrEqualTo(Date value) {
            addCriterion("BeginProduce_date <=", value, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateIn(List<Date> values) {
            addCriterion("BeginProduce_date in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotIn(List<Date> values) {
            addCriterion("BeginProduce_date not in", values, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andBeginproduceDateNotBetween(Date value1, Date value2) {
            addCriterion("BeginProduce_date not between", value1, value2, "beginproduceDate");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNull() {
            addCriterion("InDate_InFact is null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIsNotNull() {
            addCriterion("InDate_InFact is not null");
            return (Criteria) this;
        }

        public Criteria andIndateInfactEqualTo(Date value) {
            addCriterion("InDate_InFact =", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotEqualTo(Date value) {
            addCriterion("InDate_InFact <>", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThan(Date value) {
            addCriterion("InDate_InFact >", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactGreaterThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact >=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThan(Date value) {
            addCriterion("InDate_InFact <", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactLessThanOrEqualTo(Date value) {
            addCriterion("InDate_InFact <=", value, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactIn(List<Date> values) {
            addCriterion("InDate_InFact in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotIn(List<Date> values) {
            addCriterion("InDate_InFact not in", values, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andIndateInfactNotBetween(Date value1, Date value2) {
            addCriterion("InDate_InFact not between", value1, value2, "indateInfact");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNull() {
            addCriterion("Exp_date is null");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNotNull() {
            addCriterion("Exp_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpDateEqualTo(Date value) {
            addCriterion("Exp_date =", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotEqualTo(Date value) {
            addCriterion("Exp_date <>", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThan(Date value) {
            addCriterion("Exp_date >", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThanOrEqualTo(Date value) {
            addCriterion("Exp_date >=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThan(Date value) {
            addCriterion("Exp_date <", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThanOrEqualTo(Date value) {
            addCriterion("Exp_date <=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateIn(List<Date> values) {
            addCriterion("Exp_date in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotIn(List<Date> values) {
            addCriterion("Exp_date not in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateBetween(Date value1, Date value2) {
            addCriterion("Exp_date between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotBetween(Date value1, Date value2) {
            addCriterion("Exp_date not between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNull() {
            addCriterion("Export_Flag is null");
            return (Criteria) this;
        }

        public Criteria andExportFlagIsNotNull() {
            addCriterion("Export_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andExportFlagEqualTo(String value) {
            addCriterion("Export_Flag =", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotEqualTo(String value) {
            addCriterion("Export_Flag <>", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThan(String value) {
            addCriterion("Export_Flag >", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagGreaterThanOrEqualTo(String value) {
            addCriterion("Export_Flag >=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThan(String value) {
            addCriterion("Export_Flag <", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLessThanOrEqualTo(String value) {
            addCriterion("Export_Flag <=", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagLike(String value) {
            addCriterion("Export_Flag like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotLike(String value) {
            addCriterion("Export_Flag not like", value, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagIn(List<String> values) {
            addCriterion("Export_Flag in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotIn(List<String> values) {
            addCriterion("Export_Flag not in", values, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagBetween(String value1, String value2) {
            addCriterion("Export_Flag between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andExportFlagNotBetween(String value1, String value2) {
            addCriterion("Export_Flag not between", value1, value2, "exportFlag");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNull() {
            addCriterion("Holon_Name is null");
            return (Criteria) this;
        }

        public Criteria andHolonNameIsNotNull() {
            addCriterion("Holon_Name is not null");
            return (Criteria) this;
        }

        public Criteria andHolonNameEqualTo(String value) {
            addCriterion("Holon_Name =", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotEqualTo(String value) {
            addCriterion("Holon_Name <>", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThan(String value) {
            addCriterion("Holon_Name >", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameGreaterThanOrEqualTo(String value) {
            addCriterion("Holon_Name >=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThan(String value) {
            addCriterion("Holon_Name <", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLessThanOrEqualTo(String value) {
            addCriterion("Holon_Name <=", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameLike(String value) {
            addCriterion("Holon_Name like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotLike(String value) {
            addCriterion("Holon_Name not like", value, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameIn(List<String> values) {
            addCriterion("Holon_Name in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotIn(List<String> values) {
            addCriterion("Holon_Name not in", values, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameBetween(String value1, String value2) {
            addCriterion("Holon_Name between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andHolonNameNotBetween(String value1, String value2) {
            addCriterion("Holon_Name not between", value1, value2, "holonName");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoIsNull() {
            addCriterion("ShippingLabelNo is null");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoIsNotNull() {
            addCriterion("ShippingLabelNo is not null");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoEqualTo(String value) {
            addCriterion("ShippingLabelNo =", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoNotEqualTo(String value) {
            addCriterion("ShippingLabelNo <>", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoGreaterThan(String value) {
            addCriterion("ShippingLabelNo >", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoGreaterThanOrEqualTo(String value) {
            addCriterion("ShippingLabelNo >=", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoLessThan(String value) {
            addCriterion("ShippingLabelNo <", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoLessThanOrEqualTo(String value) {
            addCriterion("ShippingLabelNo <=", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoLike(String value) {
            addCriterion("ShippingLabelNo like", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoNotLike(String value) {
            addCriterion("ShippingLabelNo not like", value, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoIn(List<String> values) {
            addCriterion("ShippingLabelNo in", values, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoNotIn(List<String> values) {
            addCriterion("ShippingLabelNo not in", values, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoBetween(String value1, String value2) {
            addCriterion("ShippingLabelNo between", value1, value2, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andShippinglabelnoNotBetween(String value1, String value2) {
            addCriterion("ShippingLabelNo not between", value1, value2, "shippinglabelno");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightIsNull() {
            addCriterion("RequestModelRight is null");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightIsNotNull() {
            addCriterion("RequestModelRight is not null");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightEqualTo(String value) {
            addCriterion("RequestModelRight =", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightNotEqualTo(String value) {
            addCriterion("RequestModelRight <>", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightGreaterThan(String value) {
            addCriterion("RequestModelRight >", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightGreaterThanOrEqualTo(String value) {
            addCriterion("RequestModelRight >=", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightLessThan(String value) {
            addCriterion("RequestModelRight <", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightLessThanOrEqualTo(String value) {
            addCriterion("RequestModelRight <=", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightLike(String value) {
            addCriterion("RequestModelRight like", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightNotLike(String value) {
            addCriterion("RequestModelRight not like", value, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightIn(List<String> values) {
            addCriterion("RequestModelRight in", values, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightNotIn(List<String> values) {
            addCriterion("RequestModelRight not in", values, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightBetween(String value1, String value2) {
            addCriterion("RequestModelRight between", value1, value2, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andRequestmodelrightNotBetween(String value1, String value2) {
            addCriterion("RequestModelRight not between", value1, value2, "requestmodelright");
            return (Criteria) this;
        }

        public Criteria andIssueidIsNull() {
            addCriterion("IssueID is null");
            return (Criteria) this;
        }

        public Criteria andIssueidIsNotNull() {
            addCriterion("IssueID is not null");
            return (Criteria) this;
        }

        public Criteria andIssueidEqualTo(Long value) {
            addCriterion("IssueID =", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidNotEqualTo(Long value) {
            addCriterion("IssueID <>", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidGreaterThan(Long value) {
            addCriterion("IssueID >", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidGreaterThanOrEqualTo(Long value) {
            addCriterion("IssueID >=", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidLessThan(Long value) {
            addCriterion("IssueID <", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidLessThanOrEqualTo(Long value) {
            addCriterion("IssueID <=", value, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidIn(List<Long> values) {
            addCriterion("IssueID in", values, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidNotIn(List<Long> values) {
            addCriterion("IssueID not in", values, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidBetween(Long value1, Long value2) {
            addCriterion("IssueID between", value1, value2, "issueid");
            return (Criteria) this;
        }

        public Criteria andIssueidNotBetween(Long value1, Long value2) {
            addCriterion("IssueID not between", value1, value2, "issueid");
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
package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ActionLogExample() {
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

        public Criteria andUserNoIsNull() {
            addCriterion("user_no is null");
            return (Criteria) this;
        }

        public Criteria andUserNoIsNotNull() {
            addCriterion("user_no is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoEqualTo(String value) {
            addCriterion("user_no =", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotEqualTo(String value) {
            addCriterion("user_no <>", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThan(String value) {
            addCriterion("user_no >", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoGreaterThanOrEqualTo(String value) {
            addCriterion("user_no >=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThan(String value) {
            addCriterion("user_no <", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLessThanOrEqualTo(String value) {
            addCriterion("user_no <=", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoLike(String value) {
            addCriterion("user_no like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotLike(String value) {
            addCriterion("user_no not like", value, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoIn(List<String> values) {
            addCriterion("user_no in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotIn(List<String> values) {
            addCriterion("user_no not in", values, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoBetween(String value1, String value2) {
            addCriterion("user_no between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andUserNoNotBetween(String value1, String value2) {
            addCriterion("user_no not between", value1, value2, "userNo");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNull() {
            addCriterion("client_ip is null");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNotNull() {
            addCriterion("client_ip is not null");
            return (Criteria) this;
        }

        public Criteria andClientIpEqualTo(String value) {
            addCriterion("client_ip =", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotEqualTo(String value) {
            addCriterion("client_ip <>", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThan(String value) {
            addCriterion("client_ip >", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThanOrEqualTo(String value) {
            addCriterion("client_ip >=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThan(String value) {
            addCriterion("client_ip <", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThanOrEqualTo(String value) {
            addCriterion("client_ip <=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLike(String value) {
            addCriterion("client_ip like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotLike(String value) {
            addCriterion("client_ip not like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpIn(List<String> values) {
            addCriterion("client_ip in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotIn(List<String> values) {
            addCriterion("client_ip not in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpBetween(String value1, String value2) {
            addCriterion("client_ip between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotBetween(String value1, String value2) {
            addCriterion("client_ip not between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientAgentIsNull() {
            addCriterion("client_agent is null");
            return (Criteria) this;
        }

        public Criteria andClientAgentIsNotNull() {
            addCriterion("client_agent is not null");
            return (Criteria) this;
        }

        public Criteria andClientAgentEqualTo(String value) {
            addCriterion("client_agent =", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentNotEqualTo(String value) {
            addCriterion("client_agent <>", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentGreaterThan(String value) {
            addCriterion("client_agent >", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentGreaterThanOrEqualTo(String value) {
            addCriterion("client_agent >=", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentLessThan(String value) {
            addCriterion("client_agent <", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentLessThanOrEqualTo(String value) {
            addCriterion("client_agent <=", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentLike(String value) {
            addCriterion("client_agent like", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentNotLike(String value) {
            addCriterion("client_agent not like", value, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentIn(List<String> values) {
            addCriterion("client_agent in", values, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentNotIn(List<String> values) {
            addCriterion("client_agent not in", values, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentBetween(String value1, String value2) {
            addCriterion("client_agent between", value1, value2, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andClientAgentNotBetween(String value1, String value2) {
            addCriterion("client_agent not between", value1, value2, "clientAgent");
            return (Criteria) this;
        }

        public Criteria andRequestUrlIsNull() {
            addCriterion("request_url is null");
            return (Criteria) this;
        }

        public Criteria andRequestUrlIsNotNull() {
            addCriterion("request_url is not null");
            return (Criteria) this;
        }

        public Criteria andRequestUrlEqualTo(String value) {
            addCriterion("request_url =", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlNotEqualTo(String value) {
            addCriterion("request_url <>", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlGreaterThan(String value) {
            addCriterion("request_url >", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlGreaterThanOrEqualTo(String value) {
            addCriterion("request_url >=", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlLessThan(String value) {
            addCriterion("request_url <", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlLessThanOrEqualTo(String value) {
            addCriterion("request_url <=", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlLike(String value) {
            addCriterion("request_url like", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlNotLike(String value) {
            addCriterion("request_url not like", value, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlIn(List<String> values) {
            addCriterion("request_url in", values, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlNotIn(List<String> values) {
            addCriterion("request_url not in", values, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlBetween(String value1, String value2) {
            addCriterion("request_url between", value1, value2, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestUrlNotBetween(String value1, String value2) {
            addCriterion("request_url not between", value1, value2, "requestUrl");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionIsNull() {
            addCriterion("request_description is null");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionIsNotNull() {
            addCriterion("request_description is not null");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionEqualTo(String value) {
            addCriterion("request_description =", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionNotEqualTo(String value) {
            addCriterion("request_description <>", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionGreaterThan(String value) {
            addCriterion("request_description >", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("request_description >=", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionLessThan(String value) {
            addCriterion("request_description <", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionLessThanOrEqualTo(String value) {
            addCriterion("request_description <=", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionLike(String value) {
            addCriterion("request_description like", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionNotLike(String value) {
            addCriterion("request_description not like", value, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionIn(List<String> values) {
            addCriterion("request_description in", values, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionNotIn(List<String> values) {
            addCriterion("request_description not in", values, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionBetween(String value1, String value2) {
            addCriterion("request_description between", value1, value2, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDescriptionNotBetween(String value1, String value2) {
            addCriterion("request_description not between", value1, value2, "requestDescription");
            return (Criteria) this;
        }

        public Criteria andRequestDataIsNull() {
            addCriterion("request_data is null");
            return (Criteria) this;
        }

        public Criteria andRequestDataIsNotNull() {
            addCriterion("request_data is not null");
            return (Criteria) this;
        }

        public Criteria andRequestDataEqualTo(String value) {
            addCriterion("request_data =", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataNotEqualTo(String value) {
            addCriterion("request_data <>", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataGreaterThan(String value) {
            addCriterion("request_data >", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataGreaterThanOrEqualTo(String value) {
            addCriterion("request_data >=", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataLessThan(String value) {
            addCriterion("request_data <", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataLessThanOrEqualTo(String value) {
            addCriterion("request_data <=", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataLike(String value) {
            addCriterion("request_data like", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataNotLike(String value) {
            addCriterion("request_data not like", value, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataIn(List<String> values) {
            addCriterion("request_data in", values, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataNotIn(List<String> values) {
            addCriterion("request_data not in", values, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataBetween(String value1, String value2) {
            addCriterion("request_data between", value1, value2, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestDataNotBetween(String value1, String value2) {
            addCriterion("request_data not between", value1, value2, "requestData");
            return (Criteria) this;
        }

        public Criteria andRequestTimeIsNull() {
            addCriterion("request_time is null");
            return (Criteria) this;
        }

        public Criteria andRequestTimeIsNotNull() {
            addCriterion("request_time is not null");
            return (Criteria) this;
        }

        public Criteria andRequestTimeEqualTo(Date value) {
            addCriterion("request_time =", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeNotEqualTo(Date value) {
            addCriterion("request_time <>", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeGreaterThan(Date value) {
            addCriterion("request_time >", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("request_time >=", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeLessThan(Date value) {
            addCriterion("request_time <", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeLessThanOrEqualTo(Date value) {
            addCriterion("request_time <=", value, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeIn(List<Date> values) {
            addCriterion("request_time in", values, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeNotIn(List<Date> values) {
            addCriterion("request_time not in", values, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeBetween(Date value1, Date value2) {
            addCriterion("request_time between", value1, value2, "requestTime");
            return (Criteria) this;
        }

        public Criteria andRequestTimeNotBetween(Date value1, Date value2) {
            addCriterion("request_time not between", value1, value2, "requestTime");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIsNull() {
            addCriterion("response_url is null");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIsNotNull() {
            addCriterion("response_url is not null");
            return (Criteria) this;
        }

        public Criteria andResponseUrlEqualTo(String value) {
            addCriterion("response_url =", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotEqualTo(String value) {
            addCriterion("response_url <>", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlGreaterThan(String value) {
            addCriterion("response_url >", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlGreaterThanOrEqualTo(String value) {
            addCriterion("response_url >=", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLessThan(String value) {
            addCriterion("response_url <", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLessThanOrEqualTo(String value) {
            addCriterion("response_url <=", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLike(String value) {
            addCriterion("response_url like", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotLike(String value) {
            addCriterion("response_url not like", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIn(List<String> values) {
            addCriterion("response_url in", values, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotIn(List<String> values) {
            addCriterion("response_url not in", values, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlBetween(String value1, String value2) {
            addCriterion("response_url between", value1, value2, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotBetween(String value1, String value2) {
            addCriterion("response_url not between", value1, value2, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseResultIsNull() {
            addCriterion("response_result is null");
            return (Criteria) this;
        }

        public Criteria andResponseResultIsNotNull() {
            addCriterion("response_result is not null");
            return (Criteria) this;
        }

        public Criteria andResponseResultEqualTo(Boolean value) {
            addCriterion("response_result =", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultNotEqualTo(Boolean value) {
            addCriterion("response_result <>", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultGreaterThan(Boolean value) {
            addCriterion("response_result >", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultGreaterThanOrEqualTo(Boolean value) {
            addCriterion("response_result >=", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultLessThan(Boolean value) {
            addCriterion("response_result <", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultLessThanOrEqualTo(Boolean value) {
            addCriterion("response_result <=", value, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultIn(List<Boolean> values) {
            addCriterion("response_result in", values, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultNotIn(List<Boolean> values) {
            addCriterion("response_result not in", values, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultBetween(Boolean value1, Boolean value2) {
            addCriterion("response_result between", value1, value2, "responseResult");
            return (Criteria) this;
        }

        public Criteria andResponseResultNotBetween(Boolean value1, Boolean value2) {
            addCriterion("response_result not between", value1, value2, "responseResult");
            return (Criteria) this;
        }

        public Criteria andReponseDataIsNull() {
            addCriterion("reponse_data is null");
            return (Criteria) this;
        }

        public Criteria andReponseDataIsNotNull() {
            addCriterion("reponse_data is not null");
            return (Criteria) this;
        }

        public Criteria andReponseDataEqualTo(String value) {
            addCriterion("reponse_data =", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataNotEqualTo(String value) {
            addCriterion("reponse_data <>", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataGreaterThan(String value) {
            addCriterion("reponse_data >", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataGreaterThanOrEqualTo(String value) {
            addCriterion("reponse_data >=", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataLessThan(String value) {
            addCriterion("reponse_data <", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataLessThanOrEqualTo(String value) {
            addCriterion("reponse_data <=", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataLike(String value) {
            addCriterion("reponse_data like", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataNotLike(String value) {
            addCriterion("reponse_data not like", value, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataIn(List<String> values) {
            addCriterion("reponse_data in", values, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataNotIn(List<String> values) {
            addCriterion("reponse_data not in", values, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataBetween(String value1, String value2) {
            addCriterion("reponse_data between", value1, value2, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseDataNotBetween(String value1, String value2) {
            addCriterion("reponse_data not between", value1, value2, "reponseData");
            return (Criteria) this;
        }

        public Criteria andReponseTimeIsNull() {
            addCriterion("reponse_time is null");
            return (Criteria) this;
        }

        public Criteria andReponseTimeIsNotNull() {
            addCriterion("reponse_time is not null");
            return (Criteria) this;
        }

        public Criteria andReponseTimeEqualTo(Date value) {
            addCriterion("reponse_time =", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeNotEqualTo(Date value) {
            addCriterion("reponse_time <>", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeGreaterThan(Date value) {
            addCriterion("reponse_time >", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reponse_time >=", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeLessThan(Date value) {
            addCriterion("reponse_time <", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeLessThanOrEqualTo(Date value) {
            addCriterion("reponse_time <=", value, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeIn(List<Date> values) {
            addCriterion("reponse_time in", values, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeNotIn(List<Date> values) {
            addCriterion("reponse_time not in", values, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeBetween(Date value1, Date value2) {
            addCriterion("reponse_time between", value1, value2, "reponseTime");
            return (Criteria) this;
        }

        public Criteria andReponseTimeNotBetween(Date value1, Date value2) {
            addCriterion("reponse_time not between", value1, value2, "reponseTime");
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
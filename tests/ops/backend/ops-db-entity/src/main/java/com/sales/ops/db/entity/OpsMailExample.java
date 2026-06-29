package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsMailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsMailExample() {
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

        public Criteria andMailIdIsNull() {
            addCriterion("mail_id is null");
            return (Criteria) this;
        }

        public Criteria andMailIdIsNotNull() {
            addCriterion("mail_id is not null");
            return (Criteria) this;
        }

        public Criteria andMailIdEqualTo(Long value) {
            addCriterion("mail_id =", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotEqualTo(Long value) {
            addCriterion("mail_id <>", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdGreaterThan(Long value) {
            addCriterion("mail_id >", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("mail_id >=", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdLessThan(Long value) {
            addCriterion("mail_id <", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdLessThanOrEqualTo(Long value) {
            addCriterion("mail_id <=", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdIn(List<Long> values) {
            addCriterion("mail_id in", values, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotIn(List<Long> values) {
            addCriterion("mail_id not in", values, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdBetween(Long value1, Long value2) {
            addCriterion("mail_id between", value1, value2, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotBetween(Long value1, Long value2) {
            addCriterion("mail_id not between", value1, value2, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailFromIsNull() {
            addCriterion("mail_from is null");
            return (Criteria) this;
        }

        public Criteria andMailFromIsNotNull() {
            addCriterion("mail_from is not null");
            return (Criteria) this;
        }

        public Criteria andMailFromEqualTo(String value) {
            addCriterion("mail_from =", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromNotEqualTo(String value) {
            addCriterion("mail_from <>", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromGreaterThan(String value) {
            addCriterion("mail_from >", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromGreaterThanOrEqualTo(String value) {
            addCriterion("mail_from >=", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromLessThan(String value) {
            addCriterion("mail_from <", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromLessThanOrEqualTo(String value) {
            addCriterion("mail_from <=", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromLike(String value) {
            addCriterion("mail_from like", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromNotLike(String value) {
            addCriterion("mail_from not like", value, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromIn(List<String> values) {
            addCriterion("mail_from in", values, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromNotIn(List<String> values) {
            addCriterion("mail_from not in", values, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromBetween(String value1, String value2) {
            addCriterion("mail_from between", value1, value2, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailFromNotBetween(String value1, String value2) {
            addCriterion("mail_from not between", value1, value2, "mailFrom");
            return (Criteria) this;
        }

        public Criteria andMailToIsNull() {
            addCriterion("mail_to is null");
            return (Criteria) this;
        }

        public Criteria andMailToIsNotNull() {
            addCriterion("mail_to is not null");
            return (Criteria) this;
        }

        public Criteria andMailToEqualTo(String value) {
            addCriterion("mail_to =", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToNotEqualTo(String value) {
            addCriterion("mail_to <>", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToGreaterThan(String value) {
            addCriterion("mail_to >", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToGreaterThanOrEqualTo(String value) {
            addCriterion("mail_to >=", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToLessThan(String value) {
            addCriterion("mail_to <", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToLessThanOrEqualTo(String value) {
            addCriterion("mail_to <=", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToLike(String value) {
            addCriterion("mail_to like", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToNotLike(String value) {
            addCriterion("mail_to not like", value, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToIn(List<String> values) {
            addCriterion("mail_to in", values, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToNotIn(List<String> values) {
            addCriterion("mail_to not in", values, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToBetween(String value1, String value2) {
            addCriterion("mail_to between", value1, value2, "mailTo");
            return (Criteria) this;
        }

        public Criteria andMailToNotBetween(String value1, String value2) {
            addCriterion("mail_to not between", value1, value2, "mailTo");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andContextIsNull() {
            addCriterion("context is null");
            return (Criteria) this;
        }

        public Criteria andContextIsNotNull() {
            addCriterion("context is not null");
            return (Criteria) this;
        }

        public Criteria andContextEqualTo(String value) {
            addCriterion("context =", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextNotEqualTo(String value) {
            addCriterion("context <>", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextGreaterThan(String value) {
            addCriterion("context >", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextGreaterThanOrEqualTo(String value) {
            addCriterion("context >=", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextLessThan(String value) {
            addCriterion("context <", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextLessThanOrEqualTo(String value) {
            addCriterion("context <=", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextLike(String value) {
            addCriterion("context like", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextNotLike(String value) {
            addCriterion("context not like", value, "context");
            return (Criteria) this;
        }

        public Criteria andContextIn(List<String> values) {
            addCriterion("context in", values, "context");
            return (Criteria) this;
        }

        public Criteria andContextNotIn(List<String> values) {
            addCriterion("context not in", values, "context");
            return (Criteria) this;
        }

        public Criteria andContextBetween(String value1, String value2) {
            addCriterion("context between", value1, value2, "context");
            return (Criteria) this;
        }

        public Criteria andContextNotBetween(String value1, String value2) {
            addCriterion("context not between", value1, value2, "context");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("send_date is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("send_date is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterion("send_date =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterion("send_date <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterion("send_date >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterion("send_date >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterion("send_date <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterion("send_date <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterion("send_date in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterion("send_date not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterion("send_date between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterion("send_date not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andCcIsNull() {
            addCriterion("cc is null");
            return (Criteria) this;
        }

        public Criteria andCcIsNotNull() {
            addCriterion("cc is not null");
            return (Criteria) this;
        }

        public Criteria andCcEqualTo(String value) {
            addCriterion("cc =", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcNotEqualTo(String value) {
            addCriterion("cc <>", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcGreaterThan(String value) {
            addCriterion("cc >", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcGreaterThanOrEqualTo(String value) {
            addCriterion("cc >=", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcLessThan(String value) {
            addCriterion("cc <", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcLessThanOrEqualTo(String value) {
            addCriterion("cc <=", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcLike(String value) {
            addCriterion("cc like", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcNotLike(String value) {
            addCriterion("cc not like", value, "cc");
            return (Criteria) this;
        }

        public Criteria andCcIn(List<String> values) {
            addCriterion("cc in", values, "cc");
            return (Criteria) this;
        }

        public Criteria andCcNotIn(List<String> values) {
            addCriterion("cc not in", values, "cc");
            return (Criteria) this;
        }

        public Criteria andCcBetween(String value1, String value2) {
            addCriterion("cc between", value1, value2, "cc");
            return (Criteria) this;
        }

        public Criteria andCcNotBetween(String value1, String value2) {
            addCriterion("cc not between", value1, value2, "cc");
            return (Criteria) this;
        }

        public Criteria andBccIsNull() {
            addCriterion("bcc is null");
            return (Criteria) this;
        }

        public Criteria andBccIsNotNull() {
            addCriterion("bcc is not null");
            return (Criteria) this;
        }

        public Criteria andBccEqualTo(String value) {
            addCriterion("bcc =", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotEqualTo(String value) {
            addCriterion("bcc <>", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccGreaterThan(String value) {
            addCriterion("bcc >", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccGreaterThanOrEqualTo(String value) {
            addCriterion("bcc >=", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccLessThan(String value) {
            addCriterion("bcc <", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccLessThanOrEqualTo(String value) {
            addCriterion("bcc <=", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccLike(String value) {
            addCriterion("bcc like", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotLike(String value) {
            addCriterion("bcc not like", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccIn(List<String> values) {
            addCriterion("bcc in", values, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotIn(List<String> values) {
            addCriterion("bcc not in", values, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccBetween(String value1, String value2) {
            addCriterion("bcc between", value1, value2, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotBetween(String value1, String value2) {
            addCriterion("bcc not between", value1, value2, "bcc");
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

        public Criteria andErrorMsgIsNull() {
            addCriterion("error_msg is null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNotNull() {
            addCriterion("error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgEqualTo(String value) {
            addCriterion("error_msg =", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotEqualTo(String value) {
            addCriterion("error_msg <>", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThan(String value) {
            addCriterion("error_msg >", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("error_msg >=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThan(String value) {
            addCriterion("error_msg <", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("error_msg <=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLike(String value) {
            addCriterion("error_msg like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotLike(String value) {
            addCriterion("error_msg not like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIn(List<String> values) {
            addCriterion("error_msg in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotIn(List<String> values) {
            addCriterion("error_msg not in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgBetween(String value1, String value2) {
            addCriterion("error_msg between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotBetween(String value1, String value2) {
            addCriterion("error_msg not between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andFileUrlsIsNull() {
            addCriterion("file_urls is null");
            return (Criteria) this;
        }

        public Criteria andFileUrlsIsNotNull() {
            addCriterion("file_urls is not null");
            return (Criteria) this;
        }

        public Criteria andFileUrlsEqualTo(String value) {
            addCriterion("file_urls =", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsNotEqualTo(String value) {
            addCriterion("file_urls <>", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsGreaterThan(String value) {
            addCriterion("file_urls >", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsGreaterThanOrEqualTo(String value) {
            addCriterion("file_urls >=", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsLessThan(String value) {
            addCriterion("file_urls <", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsLessThanOrEqualTo(String value) {
            addCriterion("file_urls <=", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsLike(String value) {
            addCriterion("file_urls like", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsNotLike(String value) {
            addCriterion("file_urls not like", value, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsIn(List<String> values) {
            addCriterion("file_urls in", values, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsNotIn(List<String> values) {
            addCriterion("file_urls not in", values, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsBetween(String value1, String value2) {
            addCriterion("file_urls between", value1, value2, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andFileUrlsNotBetween(String value1, String value2) {
            addCriterion("file_urls not between", value1, value2, "fileUrls");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andFileListIsNull() {
            addCriterion("file_list is null");
            return (Criteria) this;
        }

        public Criteria andFileListIsNotNull() {
            addCriterion("file_list is not null");
            return (Criteria) this;
        }

        public Criteria andFileListEqualTo(String value) {
            addCriterion("file_list =", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListNotEqualTo(String value) {
            addCriterion("file_list <>", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListGreaterThan(String value) {
            addCriterion("file_list >", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListGreaterThanOrEqualTo(String value) {
            addCriterion("file_list >=", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListLessThan(String value) {
            addCriterion("file_list <", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListLessThanOrEqualTo(String value) {
            addCriterion("file_list <=", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListLike(String value) {
            addCriterion("file_list like", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListNotLike(String value) {
            addCriterion("file_list not like", value, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListIn(List<String> values) {
            addCriterion("file_list in", values, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListNotIn(List<String> values) {
            addCriterion("file_list not in", values, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListBetween(String value1, String value2) {
            addCriterion("file_list between", value1, value2, "fileList");
            return (Criteria) this;
        }

        public Criteria andFileListNotBetween(String value1, String value2) {
            addCriterion("file_list not between", value1, value2, "fileList");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNull() {
            addCriterion("insert_time is null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNotNull() {
            addCriterion("insert_time is not null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeEqualTo(Date value) {
            addCriterion("insert_time =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(Date value) {
            addCriterion("insert_time <>", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThan(Date value) {
            addCriterion("insert_time >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("insert_time >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(Date value) {
            addCriterion("insert_time <", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThanOrEqualTo(Date value) {
            addCriterion("insert_time <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<Date> values) {
            addCriterion("insert_time in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<Date> values) {
            addCriterion("insert_time not in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeBetween(Date value1, Date value2) {
            addCriterion("insert_time between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotBetween(Date value1, Date value2) {
            addCriterion("insert_time not between", value1, value2, "insertTime");
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
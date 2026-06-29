package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsSysLog implements Serializable {
    private Long id;

    private String status;

    private String title;

    private String serviceid;

    private String createid;

    private Date createtime;

    private String remoteaddr;

    private String useragent;

    private String requesturi;

    private String method;

    private String params;

    private Long time;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid == null ? null : serviceid.trim();
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemoteaddr() {
        return remoteaddr;
    }

    public void setRemoteaddr(String remoteaddr) {
        this.remoteaddr = remoteaddr == null ? null : remoteaddr.trim();
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent == null ? null : useragent.trim();
    }

    public String getRequesturi() {
        return requesturi;
    }

    public void setRequesturi(String requesturi) {
        this.requesturi = requesturi == null ? null : requesturi.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OpsSysLog other = (OpsSysLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getServiceid() == null ? other.getServiceid() == null : this.getServiceid().equals(other.getServiceid()))
            && (this.getCreateid() == null ? other.getCreateid() == null : this.getCreateid().equals(other.getCreateid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getRemoteaddr() == null ? other.getRemoteaddr() == null : this.getRemoteaddr().equals(other.getRemoteaddr()))
            && (this.getUseragent() == null ? other.getUseragent() == null : this.getUseragent().equals(other.getUseragent()))
            && (this.getRequesturi() == null ? other.getRequesturi() == null : this.getRequesturi().equals(other.getRequesturi()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getServiceid() == null) ? 0 : getServiceid().hashCode());
        result = prime * result + ((getCreateid() == null) ? 0 : getCreateid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getRemoteaddr() == null) ? 0 : getRemoteaddr().hashCode());
        result = prime * result + ((getUseragent() == null) ? 0 : getUseragent().hashCode());
        result = prime * result + ((getRequesturi() == null) ? 0 : getRequesturi().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }
}
package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ErrorMsgNoticeConfig implements Serializable {
    private Long id;

    private String keyMsg;

    private Integer subFlag;

    private String subOpen;

    private String subClose;

    private Date creTime;

    private String creator;

    private Integer delflag;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyMsg() {
        return keyMsg;
    }

    public void setKeyMsg(String keyMsg) {
        this.keyMsg = keyMsg == null ? null : keyMsg.trim();
    }

    public Integer getSubFlag() {
        return subFlag;
    }

    public void setSubFlag(Integer subFlag) {
        this.subFlag = subFlag;
    }

    public String getSubOpen() {
        return subOpen;
    }

    public void setSubOpen(String subOpen) {
        this.subOpen = subOpen == null ? null : subOpen.trim();
    }

    public String getSubClose() {
        return subClose;
    }

    public void setSubClose(String subClose) {
        this.subClose = subClose == null ? null : subClose.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        ErrorMsgNoticeConfig other = (ErrorMsgNoticeConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getKeyMsg() == null ? other.getKeyMsg() == null : this.getKeyMsg().equals(other.getKeyMsg()))
            && (this.getSubFlag() == null ? other.getSubFlag() == null : this.getSubFlag().equals(other.getSubFlag()))
            && (this.getSubOpen() == null ? other.getSubOpen() == null : this.getSubOpen().equals(other.getSubOpen()))
            && (this.getSubClose() == null ? other.getSubClose() == null : this.getSubClose().equals(other.getSubClose()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getKeyMsg() == null) ? 0 : getKeyMsg().hashCode());
        result = prime * result + ((getSubFlag() == null) ? 0 : getSubFlag().hashCode());
        result = prime * result + ((getSubOpen() == null) ? 0 : getSubOpen().hashCode());
        result = prime * result + ((getSubClose() == null) ? 0 : getSubClose().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}
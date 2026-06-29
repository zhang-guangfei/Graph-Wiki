package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ErrorMsgNotice implements Serializable {
    private Long id;

    private String modelNo;

    private String resultKey;

    private String keyMsg;

    private Date creTime;

    private String creator;

    private Integer handleFlag;

    private Integer delflag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey == null ? null : resultKey.trim();
    }

    public String getKeyMsg() {
        return keyMsg;
    }

    public void setKeyMsg(String keyMsg) {
        this.keyMsg = keyMsg == null ? null : keyMsg.trim();
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

    public Integer getHandleFlag() {
        return handleFlag;
    }

    public void setHandleFlag(Integer handleFlag) {
        this.handleFlag = handleFlag;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
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
        ErrorMsgNotice other = (ErrorMsgNotice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getResultKey() == null ? other.getResultKey() == null : this.getResultKey().equals(other.getResultKey()))
            && (this.getKeyMsg() == null ? other.getKeyMsg() == null : this.getKeyMsg().equals(other.getKeyMsg()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getHandleFlag() == null ? other.getHandleFlag() == null : this.getHandleFlag().equals(other.getHandleFlag()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getResultKey() == null) ? 0 : getResultKey().hashCode());
        result = prime * result + ((getKeyMsg() == null) ? 0 : getKeyMsg().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getHandleFlag() == null) ? 0 : getHandleFlag().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        return result;
    }
}
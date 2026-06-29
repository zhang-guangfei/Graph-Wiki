package com.sales.ops.dto.eta;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/12/12 10:05
 */
public class InvProHashKeyDto implements Serializable {
    private static final long serialVersionUID = 2770535071882073396L;

    private String qb;
    private String projectCode;
    private String ppl;
    private String groupCustomerNo;
    private String CustomerNo;
    private String invTypeCode;

    public InvProHashKeyDto(){};

    public InvProHashKeyDto(String qb,String projectCode,String ppl ,String groupCustomerNo,String CustomerNo,String invTypeCode){
        this.qb = qb;
        this.projectCode = projectCode;
        this.ppl = ppl;
        this.groupCustomerNo = groupCustomerNo;
        this.CustomerNo = CustomerNo;
        this.invTypeCode = invTypeCode;
    };


    public String getQb() {
        return qb;
    }

    public void setQb(String qb) {
        this.qb = qb;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }

    public String getInvTypeCode() {
        return invTypeCode;
    }

    public void setInvTypeCode(String invTypeCode) {
        this.invTypeCode = invTypeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvProHashKeyDto that = (InvProHashKeyDto) o;
        return Objects.equals(qb, that.qb) && Objects.equals(projectCode, that.projectCode) && Objects.equals(ppl, that.ppl) && Objects.equals(groupCustomerNo, that.groupCustomerNo) && Objects.equals(CustomerNo, that.CustomerNo) && Objects.equals(invTypeCode, that.invTypeCode);
    }

    public long hashCodePa() {
        final long prime = 31;
        long  result = 1;
        result = prime * result + ((getQb() == null) ? 0 : getQb().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : hash(getCustomerNo()));
        result = prime * result + ((getInvTypeCode() == null) ? 0 : getInvTypeCode().hashCode());
        return result;
    }


    /**
     * get hash code on 2^32 ring (md5散列的方式计算hash值)
     * @param key
     * @return
     */
    public long hash(String key) {

        // md5 byte
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + key, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }
}

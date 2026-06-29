package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class RcvmaseterGz implements Serializable {
    private String rorderno;

    private String customerno;

    private String userno;

    private String enduser;

    private Date rorddate;

    private String dlventire;

    private String dlvsite;

    private String transfee;

    private String transchannel;

    private String prjcode;

    private String employee;

    private String employeeno;

    private String dlvtype1;

    private String dlvtype2;

    private String dlvtype3;

    private String packtype;

    private String customerHk;

    private String cqueryno;

    private String corderno;

    private Date opttime;

    private String operator;

    private String contractno;

    private String deptno;

    private byte[] rv;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getEnduser() {
        return enduser;
    }

    public void setEnduser(String enduser) {
        this.enduser = enduser == null ? null : enduser.trim();
    }

    public Date getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date rorddate) {
        this.rorddate = rorddate;
    }

    public String getDlventire() {
        return dlventire;
    }

    public void setDlventire(String dlventire) {
        this.dlventire = dlventire == null ? null : dlventire.trim();
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite == null ? null : dlvsite.trim();
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee == null ? null : transfee.trim();
    }

    public String getTranschannel() {
        return transchannel;
    }

    public void setTranschannel(String transchannel) {
        this.transchannel = transchannel == null ? null : transchannel.trim();
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode == null ? null : prjcode.trim();
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee == null ? null : employee.trim();
    }

    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno == null ? null : employeeno.trim();
    }

    public String getDlvtype1() {
        return dlvtype1;
    }

    public void setDlvtype1(String dlvtype1) {
        this.dlvtype1 = dlvtype1 == null ? null : dlvtype1.trim();
    }

    public String getDlvtype2() {
        return dlvtype2;
    }

    public void setDlvtype2(String dlvtype2) {
        this.dlvtype2 = dlvtype2 == null ? null : dlvtype2.trim();
    }

    public String getDlvtype3() {
        return dlvtype3;
    }

    public void setDlvtype3(String dlvtype3) {
        this.dlvtype3 = dlvtype3 == null ? null : dlvtype3.trim();
    }

    public String getPacktype() {
        return packtype;
    }

    public void setPacktype(String packtype) {
        this.packtype = packtype == null ? null : packtype.trim();
    }

    public String getCustomerHk() {
        return customerHk;
    }

    public void setCustomerHk(String customerHk) {
        this.customerHk = customerHk == null ? null : customerHk.trim();
    }

    public String getCqueryno() {
        return cqueryno;
    }

    public void setCqueryno(String cqueryno) {
        this.cqueryno = cqueryno == null ? null : cqueryno.trim();
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno == null ? null : corderno.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public byte[] getRv() {
        return rv;
    }

    public void setRv(byte[] rv) {
        this.rv = rv;
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
        RcvmaseterGz other = (RcvmaseterGz) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getEnduser() == null ? other.getEnduser() == null : this.getEnduser().equals(other.getEnduser()))
            && (this.getRorddate() == null ? other.getRorddate() == null : this.getRorddate().equals(other.getRorddate()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getTranschannel() == null ? other.getTranschannel() == null : this.getTranschannel().equals(other.getTranschannel()))
            && (this.getPrjcode() == null ? other.getPrjcode() == null : this.getPrjcode().equals(other.getPrjcode()))
            && (this.getEmployee() == null ? other.getEmployee() == null : this.getEmployee().equals(other.getEmployee()))
            && (this.getEmployeeno() == null ? other.getEmployeeno() == null : this.getEmployeeno().equals(other.getEmployeeno()))
            && (this.getDlvtype1() == null ? other.getDlvtype1() == null : this.getDlvtype1().equals(other.getDlvtype1()))
            && (this.getDlvtype2() == null ? other.getDlvtype2() == null : this.getDlvtype2().equals(other.getDlvtype2()))
            && (this.getDlvtype3() == null ? other.getDlvtype3() == null : this.getDlvtype3().equals(other.getDlvtype3()))
            && (this.getPacktype() == null ? other.getPacktype() == null : this.getPacktype().equals(other.getPacktype()))
            && (this.getCustomerHk() == null ? other.getCustomerHk() == null : this.getCustomerHk().equals(other.getCustomerHk()))
            && (this.getCqueryno() == null ? other.getCqueryno() == null : this.getCqueryno().equals(other.getCqueryno()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (Arrays.equals(this.getRv(), other.getRv()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getEnduser() == null) ? 0 : getEnduser().hashCode());
        result = prime * result + ((getRorddate() == null) ? 0 : getRorddate().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getTranschannel() == null) ? 0 : getTranschannel().hashCode());
        result = prime * result + ((getPrjcode() == null) ? 0 : getPrjcode().hashCode());
        result = prime * result + ((getEmployee() == null) ? 0 : getEmployee().hashCode());
        result = prime * result + ((getEmployeeno() == null) ? 0 : getEmployeeno().hashCode());
        result = prime * result + ((getDlvtype1() == null) ? 0 : getDlvtype1().hashCode());
        result = prime * result + ((getDlvtype2() == null) ? 0 : getDlvtype2().hashCode());
        result = prime * result + ((getDlvtype3() == null) ? 0 : getDlvtype3().hashCode());
        result = prime * result + ((getPacktype() == null) ? 0 : getPacktype().hashCode());
        result = prime * result + ((getCustomerHk() == null) ? 0 : getCustomerHk().hashCode());
        result = prime * result + ((getCqueryno() == null) ? 0 : getCqueryno().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + (Arrays.hashCode(getRv()));
        return result;
    }
}
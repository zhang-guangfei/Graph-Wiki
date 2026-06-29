package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Temprequisition extends TemprequisitionKey implements Serializable {
    private String customercode;

    private String acceptcustomercode;

    private String jpkogo;

    private String jpkogoitem;

    private String requestmodel;

    private Integer requestqty;

    private Date issuedate;

    private String instrtype;

    private String dlvyway;

    private String jporderno;

    private String jpitemno;

    private String jpshelfno;

    private String gateno;

    private String zonemark;

    private String jpremarks;

    private String accepter;

    private Byte requesttype;

    private String sortno;

    private Date reqdlvydate;

    private String bagtype;

    private Integer bagfixedqty;

    private Integer boxfixedqty;

    private String boxtype;

    private String simplemodel;

    private String packtype;

    private String greenmark;

    private String jpserialnomark;

    private String midsize;

    private String prodflag;

    private String customerno;

    private String userno;

    private String username;

    private String optcode;

    private String contractno;

    private Integer contractremnant;

    private String salesinfono;

    private Date indateTheory;

    private Date rcvordDate;

    private Date beginproduceDate;

    private Date indateInfact;

    private Date expDate;

    private String exportFlag;

    private String holonName;

    private String shippinglabelno;

    private String requestmodelright;

    private Long issueid;

    private static final long serialVersionUID = 1L;

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode == null ? null : customercode.trim();
    }

    public String getAcceptcustomercode() {
        return acceptcustomercode;
    }

    public void setAcceptcustomercode(String acceptcustomercode) {
        this.acceptcustomercode = acceptcustomercode == null ? null : acceptcustomercode.trim();
    }

    public String getJpkogo() {
        return jpkogo;
    }

    public void setJpkogo(String jpkogo) {
        this.jpkogo = jpkogo == null ? null : jpkogo.trim();
    }

    public String getJpkogoitem() {
        return jpkogoitem;
    }

    public void setJpkogoitem(String jpkogoitem) {
        this.jpkogoitem = jpkogoitem == null ? null : jpkogoitem.trim();
    }

    public String getRequestmodel() {
        return requestmodel;
    }

    public void setRequestmodel(String requestmodel) {
        this.requestmodel = requestmodel == null ? null : requestmodel.trim();
    }

    public Integer getRequestqty() {
        return requestqty;
    }

    public void setRequestqty(Integer requestqty) {
        this.requestqty = requestqty;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public String getInstrtype() {
        return instrtype;
    }

    public void setInstrtype(String instrtype) {
        this.instrtype = instrtype == null ? null : instrtype.trim();
    }

    public String getDlvyway() {
        return dlvyway;
    }

    public void setDlvyway(String dlvyway) {
        this.dlvyway = dlvyway == null ? null : dlvyway.trim();
    }

    public String getJporderno() {
        return jporderno;
    }

    public void setJporderno(String jporderno) {
        this.jporderno = jporderno == null ? null : jporderno.trim();
    }

    public String getJpitemno() {
        return jpitemno;
    }

    public void setJpitemno(String jpitemno) {
        this.jpitemno = jpitemno == null ? null : jpitemno.trim();
    }

    public String getJpshelfno() {
        return jpshelfno;
    }

    public void setJpshelfno(String jpshelfno) {
        this.jpshelfno = jpshelfno == null ? null : jpshelfno.trim();
    }

    public String getGateno() {
        return gateno;
    }

    public void setGateno(String gateno) {
        this.gateno = gateno == null ? null : gateno.trim();
    }

    public String getZonemark() {
        return zonemark;
    }

    public void setZonemark(String zonemark) {
        this.zonemark = zonemark == null ? null : zonemark.trim();
    }

    public String getJpremarks() {
        return jpremarks;
    }

    public void setJpremarks(String jpremarks) {
        this.jpremarks = jpremarks == null ? null : jpremarks.trim();
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter == null ? null : accepter.trim();
    }

    public Byte getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(Byte requesttype) {
        this.requesttype = requesttype;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno == null ? null : sortno.trim();
    }

    public Date getReqdlvydate() {
        return reqdlvydate;
    }

    public void setReqdlvydate(Date reqdlvydate) {
        this.reqdlvydate = reqdlvydate;
    }

    public String getBagtype() {
        return bagtype;
    }

    public void setBagtype(String bagtype) {
        this.bagtype = bagtype == null ? null : bagtype.trim();
    }

    public Integer getBagfixedqty() {
        return bagfixedqty;
    }

    public void setBagfixedqty(Integer bagfixedqty) {
        this.bagfixedqty = bagfixedqty;
    }

    public Integer getBoxfixedqty() {
        return boxfixedqty;
    }

    public void setBoxfixedqty(Integer boxfixedqty) {
        this.boxfixedqty = boxfixedqty;
    }

    public String getBoxtype() {
        return boxtype;
    }

    public void setBoxtype(String boxtype) {
        this.boxtype = boxtype == null ? null : boxtype.trim();
    }

    public String getSimplemodel() {
        return simplemodel;
    }

    public void setSimplemodel(String simplemodel) {
        this.simplemodel = simplemodel == null ? null : simplemodel.trim();
    }

    public String getPacktype() {
        return packtype;
    }

    public void setPacktype(String packtype) {
        this.packtype = packtype == null ? null : packtype.trim();
    }

    public String getGreenmark() {
        return greenmark;
    }

    public void setGreenmark(String greenmark) {
        this.greenmark = greenmark == null ? null : greenmark.trim();
    }

    public String getJpserialnomark() {
        return jpserialnomark;
    }

    public void setJpserialnomark(String jpserialnomark) {
        this.jpserialnomark = jpserialnomark == null ? null : jpserialnomark.trim();
    }

    public String getMidsize() {
        return midsize;
    }

    public void setMidsize(String midsize) {
        this.midsize = midsize == null ? null : midsize.trim();
    }

    public String getProdflag() {
        return prodflag;
    }

    public void setProdflag(String prodflag) {
        this.prodflag = prodflag == null ? null : prodflag.trim();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public Integer getContractremnant() {
        return contractremnant;
    }

    public void setContractremnant(Integer contractremnant) {
        this.contractremnant = contractremnant;
    }

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public Date getIndateTheory() {
        return indateTheory;
    }

    public void setIndateTheory(Date indateTheory) {
        this.indateTheory = indateTheory;
    }

    public Date getRcvordDate() {
        return rcvordDate;
    }

    public void setRcvordDate(Date rcvordDate) {
        this.rcvordDate = rcvordDate;
    }

    public Date getBeginproduceDate() {
        return beginproduceDate;
    }

    public void setBeginproduceDate(Date beginproduceDate) {
        this.beginproduceDate = beginproduceDate;
    }

    public Date getIndateInfact() {
        return indateInfact;
    }

    public void setIndateInfact(Date indateInfact) {
        this.indateInfact = indateInfact;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getExportFlag() {
        return exportFlag;
    }

    public void setExportFlag(String exportFlag) {
        this.exportFlag = exportFlag == null ? null : exportFlag.trim();
    }

    public String getHolonName() {
        return holonName;
    }

    public void setHolonName(String holonName) {
        this.holonName = holonName == null ? null : holonName.trim();
    }

    public String getShippinglabelno() {
        return shippinglabelno;
    }

    public void setShippinglabelno(String shippinglabelno) {
        this.shippinglabelno = shippinglabelno == null ? null : shippinglabelno.trim();
    }

    public String getRequestmodelright() {
        return requestmodelright;
    }

    public void setRequestmodelright(String requestmodelright) {
        this.requestmodelright = requestmodelright == null ? null : requestmodelright.trim();
    }

    public Long getIssueid() {
        return issueid;
    }

    public void setIssueid(Long issueid) {
        this.issueid = issueid;
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
        Temprequisition other = (Temprequisition) that;
        return (this.getRequestno() == null ? other.getRequestno() == null : this.getRequestno().equals(other.getRequestno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getCustomercode() == null ? other.getCustomercode() == null : this.getCustomercode().equals(other.getCustomercode()))
            && (this.getAcceptcustomercode() == null ? other.getAcceptcustomercode() == null : this.getAcceptcustomercode().equals(other.getAcceptcustomercode()))
            && (this.getJpkogo() == null ? other.getJpkogo() == null : this.getJpkogo().equals(other.getJpkogo()))
            && (this.getJpkogoitem() == null ? other.getJpkogoitem() == null : this.getJpkogoitem().equals(other.getJpkogoitem()))
            && (this.getRequestmodel() == null ? other.getRequestmodel() == null : this.getRequestmodel().equals(other.getRequestmodel()))
            && (this.getRequestqty() == null ? other.getRequestqty() == null : this.getRequestqty().equals(other.getRequestqty()))
            && (this.getIssuedate() == null ? other.getIssuedate() == null : this.getIssuedate().equals(other.getIssuedate()))
            && (this.getInstrtype() == null ? other.getInstrtype() == null : this.getInstrtype().equals(other.getInstrtype()))
            && (this.getDlvyway() == null ? other.getDlvyway() == null : this.getDlvyway().equals(other.getDlvyway()))
            && (this.getJporderno() == null ? other.getJporderno() == null : this.getJporderno().equals(other.getJporderno()))
            && (this.getJpitemno() == null ? other.getJpitemno() == null : this.getJpitemno().equals(other.getJpitemno()))
            && (this.getJpshelfno() == null ? other.getJpshelfno() == null : this.getJpshelfno().equals(other.getJpshelfno()))
            && (this.getGateno() == null ? other.getGateno() == null : this.getGateno().equals(other.getGateno()))
            && (this.getZonemark() == null ? other.getZonemark() == null : this.getZonemark().equals(other.getZonemark()))
            && (this.getJpremarks() == null ? other.getJpremarks() == null : this.getJpremarks().equals(other.getJpremarks()))
            && (this.getAccepter() == null ? other.getAccepter() == null : this.getAccepter().equals(other.getAccepter()))
            && (this.getRequesttype() == null ? other.getRequesttype() == null : this.getRequesttype().equals(other.getRequesttype()))
            && (this.getSortno() == null ? other.getSortno() == null : this.getSortno().equals(other.getSortno()))
            && (this.getReqdlvydate() == null ? other.getReqdlvydate() == null : this.getReqdlvydate().equals(other.getReqdlvydate()))
            && (this.getBagtype() == null ? other.getBagtype() == null : this.getBagtype().equals(other.getBagtype()))
            && (this.getBagfixedqty() == null ? other.getBagfixedqty() == null : this.getBagfixedqty().equals(other.getBagfixedqty()))
            && (this.getBoxfixedqty() == null ? other.getBoxfixedqty() == null : this.getBoxfixedqty().equals(other.getBoxfixedqty()))
            && (this.getBoxtype() == null ? other.getBoxtype() == null : this.getBoxtype().equals(other.getBoxtype()))
            && (this.getSimplemodel() == null ? other.getSimplemodel() == null : this.getSimplemodel().equals(other.getSimplemodel()))
            && (this.getPacktype() == null ? other.getPacktype() == null : this.getPacktype().equals(other.getPacktype()))
            && (this.getGreenmark() == null ? other.getGreenmark() == null : this.getGreenmark().equals(other.getGreenmark()))
            && (this.getJpserialnomark() == null ? other.getJpserialnomark() == null : this.getJpserialnomark().equals(other.getJpserialnomark()))
            && (this.getMidsize() == null ? other.getMidsize() == null : this.getMidsize().equals(other.getMidsize()))
            && (this.getProdflag() == null ? other.getProdflag() == null : this.getProdflag().equals(other.getProdflag()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getContractremnant() == null ? other.getContractremnant() == null : this.getContractremnant().equals(other.getContractremnant()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getIndateTheory() == null ? other.getIndateTheory() == null : this.getIndateTheory().equals(other.getIndateTheory()))
            && (this.getRcvordDate() == null ? other.getRcvordDate() == null : this.getRcvordDate().equals(other.getRcvordDate()))
            && (this.getBeginproduceDate() == null ? other.getBeginproduceDate() == null : this.getBeginproduceDate().equals(other.getBeginproduceDate()))
            && (this.getIndateInfact() == null ? other.getIndateInfact() == null : this.getIndateInfact().equals(other.getIndateInfact()))
            && (this.getExpDate() == null ? other.getExpDate() == null : this.getExpDate().equals(other.getExpDate()))
            && (this.getExportFlag() == null ? other.getExportFlag() == null : this.getExportFlag().equals(other.getExportFlag()))
            && (this.getHolonName() == null ? other.getHolonName() == null : this.getHolonName().equals(other.getHolonName()))
            && (this.getShippinglabelno() == null ? other.getShippinglabelno() == null : this.getShippinglabelno().equals(other.getShippinglabelno()))
            && (this.getRequestmodelright() == null ? other.getRequestmodelright() == null : this.getRequestmodelright().equals(other.getRequestmodelright()))
            && (this.getIssueid() == null ? other.getIssueid() == null : this.getIssueid().equals(other.getIssueid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRequestno() == null) ? 0 : getRequestno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getCustomercode() == null) ? 0 : getCustomercode().hashCode());
        result = prime * result + ((getAcceptcustomercode() == null) ? 0 : getAcceptcustomercode().hashCode());
        result = prime * result + ((getJpkogo() == null) ? 0 : getJpkogo().hashCode());
        result = prime * result + ((getJpkogoitem() == null) ? 0 : getJpkogoitem().hashCode());
        result = prime * result + ((getRequestmodel() == null) ? 0 : getRequestmodel().hashCode());
        result = prime * result + ((getRequestqty() == null) ? 0 : getRequestqty().hashCode());
        result = prime * result + ((getIssuedate() == null) ? 0 : getIssuedate().hashCode());
        result = prime * result + ((getInstrtype() == null) ? 0 : getInstrtype().hashCode());
        result = prime * result + ((getDlvyway() == null) ? 0 : getDlvyway().hashCode());
        result = prime * result + ((getJporderno() == null) ? 0 : getJporderno().hashCode());
        result = prime * result + ((getJpitemno() == null) ? 0 : getJpitemno().hashCode());
        result = prime * result + ((getJpshelfno() == null) ? 0 : getJpshelfno().hashCode());
        result = prime * result + ((getGateno() == null) ? 0 : getGateno().hashCode());
        result = prime * result + ((getZonemark() == null) ? 0 : getZonemark().hashCode());
        result = prime * result + ((getJpremarks() == null) ? 0 : getJpremarks().hashCode());
        result = prime * result + ((getAccepter() == null) ? 0 : getAccepter().hashCode());
        result = prime * result + ((getRequesttype() == null) ? 0 : getRequesttype().hashCode());
        result = prime * result + ((getSortno() == null) ? 0 : getSortno().hashCode());
        result = prime * result + ((getReqdlvydate() == null) ? 0 : getReqdlvydate().hashCode());
        result = prime * result + ((getBagtype() == null) ? 0 : getBagtype().hashCode());
        result = prime * result + ((getBagfixedqty() == null) ? 0 : getBagfixedqty().hashCode());
        result = prime * result + ((getBoxfixedqty() == null) ? 0 : getBoxfixedqty().hashCode());
        result = prime * result + ((getBoxtype() == null) ? 0 : getBoxtype().hashCode());
        result = prime * result + ((getSimplemodel() == null) ? 0 : getSimplemodel().hashCode());
        result = prime * result + ((getPacktype() == null) ? 0 : getPacktype().hashCode());
        result = prime * result + ((getGreenmark() == null) ? 0 : getGreenmark().hashCode());
        result = prime * result + ((getJpserialnomark() == null) ? 0 : getJpserialnomark().hashCode());
        result = prime * result + ((getMidsize() == null) ? 0 : getMidsize().hashCode());
        result = prime * result + ((getProdflag() == null) ? 0 : getProdflag().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getContractremnant() == null) ? 0 : getContractremnant().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getIndateTheory() == null) ? 0 : getIndateTheory().hashCode());
        result = prime * result + ((getRcvordDate() == null) ? 0 : getRcvordDate().hashCode());
        result = prime * result + ((getBeginproduceDate() == null) ? 0 : getBeginproduceDate().hashCode());
        result = prime * result + ((getIndateInfact() == null) ? 0 : getIndateInfact().hashCode());
        result = prime * result + ((getExpDate() == null) ? 0 : getExpDate().hashCode());
        result = prime * result + ((getExportFlag() == null) ? 0 : getExportFlag().hashCode());
        result = prime * result + ((getHolonName() == null) ? 0 : getHolonName().hashCode());
        result = prime * result + ((getShippinglabelno() == null) ? 0 : getShippinglabelno().hashCode());
        result = prime * result + ((getRequestmodelright() == null) ? 0 : getRequestmodelright().hashCode());
        result = prime * result + ((getIssueid() == null) ? 0 : getIssueid().hashCode());
        return result;
    }
}
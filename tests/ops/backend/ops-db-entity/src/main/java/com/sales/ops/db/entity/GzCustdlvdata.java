package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class GzCustdlvdata implements Serializable {
    private Integer id;

    private String customerno;

    private String sid;

    private String addcode;

    private String saddress;

    private String cstmname;

    private String dlvaddress;

    private String contactpsn;

    private String teleno;

    private String postcode;

    private String transfee;

    private String dlvtype;

    private Date upddate;

    private Date createdate;

    private String statecode;

    private String optuser;

    private String modifyexpressreason;

    private String addresstype;

    private String createuser;

    private Boolean isdefault;

    private Boolean iscommonuse;

    private Boolean isconfirm;

    private String province;

    private String city;

    private String area;

    private Short status;

    private String hashcode;

    private String intenno;

    private Integer expdlvtype;

    private Integer nodlvtype;

    private Boolean heavyupstair;

    private Date validate;

    private Short etimes;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getAddcode() {
        return addcode;
    }

    public void setAddcode(String addcode) {
        this.addcode = addcode == null ? null : addcode.trim();
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress == null ? null : saddress.trim();
    }

    public String getCstmname() {
        return cstmname;
    }

    public void setCstmname(String cstmname) {
        this.cstmname = cstmname == null ? null : cstmname.trim();
    }

    public String getDlvaddress() {
        return dlvaddress;
    }

    public void setDlvaddress(String dlvaddress) {
        this.dlvaddress = dlvaddress == null ? null : dlvaddress.trim();
    }

    public String getContactpsn() {
        return contactpsn;
    }

    public void setContactpsn(String contactpsn) {
        this.contactpsn = contactpsn == null ? null : contactpsn.trim();
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno == null ? null : teleno.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee == null ? null : transfee.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser == null ? null : optuser.trim();
    }

    public String getModifyexpressreason() {
        return modifyexpressreason;
    }

    public void setModifyexpressreason(String modifyexpressreason) {
        this.modifyexpressreason = modifyexpressreason == null ? null : modifyexpressreason.trim();
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype == null ? null : addresstype.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Boolean getIscommonuse() {
        return iscommonuse;
    }

    public void setIscommonuse(Boolean iscommonuse) {
        this.iscommonuse = iscommonuse;
    }

    public Boolean getIsconfirm() {
        return isconfirm;
    }

    public void setIsconfirm(Boolean isconfirm) {
        this.isconfirm = isconfirm;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode == null ? null : hashcode.trim();
    }

    public String getIntenno() {
        return intenno;
    }

    public void setIntenno(String intenno) {
        this.intenno = intenno == null ? null : intenno.trim();
    }

    public Integer getExpdlvtype() {
        return expdlvtype;
    }

    public void setExpdlvtype(Integer expdlvtype) {
        this.expdlvtype = expdlvtype;
    }

    public Integer getNodlvtype() {
        return nodlvtype;
    }

    public void setNodlvtype(Integer nodlvtype) {
        this.nodlvtype = nodlvtype;
    }

    public Boolean getHeavyupstair() {
        return heavyupstair;
    }

    public void setHeavyupstair(Boolean heavyupstair) {
        this.heavyupstair = heavyupstair;
    }

    public Date getValidate() {
        return validate;
    }

    public void setValidate(Date validate) {
        this.validate = validate;
    }

    public Short getEtimes() {
        return etimes;
    }

    public void setEtimes(Short etimes) {
        this.etimes = etimes;
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
        GzCustdlvdata other = (GzCustdlvdata) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
            && (this.getAddcode() == null ? other.getAddcode() == null : this.getAddcode().equals(other.getAddcode()))
            && (this.getSaddress() == null ? other.getSaddress() == null : this.getSaddress().equals(other.getSaddress()))
            && (this.getCstmname() == null ? other.getCstmname() == null : this.getCstmname().equals(other.getCstmname()))
            && (this.getDlvaddress() == null ? other.getDlvaddress() == null : this.getDlvaddress().equals(other.getDlvaddress()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getTeleno() == null ? other.getTeleno() == null : this.getTeleno().equals(other.getTeleno()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getCreatedate() == null ? other.getCreatedate() == null : this.getCreatedate().equals(other.getCreatedate()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getOptuser() == null ? other.getOptuser() == null : this.getOptuser().equals(other.getOptuser()))
            && (this.getModifyexpressreason() == null ? other.getModifyexpressreason() == null : this.getModifyexpressreason().equals(other.getModifyexpressreason()))
            && (this.getAddresstype() == null ? other.getAddresstype() == null : this.getAddresstype().equals(other.getAddresstype()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getIsdefault() == null ? other.getIsdefault() == null : this.getIsdefault().equals(other.getIsdefault()))
            && (this.getIscommonuse() == null ? other.getIscommonuse() == null : this.getIscommonuse().equals(other.getIscommonuse()))
            && (this.getIsconfirm() == null ? other.getIsconfirm() == null : this.getIsconfirm().equals(other.getIsconfirm()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getHashcode() == null ? other.getHashcode() == null : this.getHashcode().equals(other.getHashcode()))
            && (this.getIntenno() == null ? other.getIntenno() == null : this.getIntenno().equals(other.getIntenno()))
            && (this.getExpdlvtype() == null ? other.getExpdlvtype() == null : this.getExpdlvtype().equals(other.getExpdlvtype()))
            && (this.getNodlvtype() == null ? other.getNodlvtype() == null : this.getNodlvtype().equals(other.getNodlvtype()))
            && (this.getHeavyupstair() == null ? other.getHeavyupstair() == null : this.getHeavyupstair().equals(other.getHeavyupstair()))
            && (this.getValidate() == null ? other.getValidate() == null : this.getValidate().equals(other.getValidate()))
            && (this.getEtimes() == null ? other.getEtimes() == null : this.getEtimes().equals(other.getEtimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getAddcode() == null) ? 0 : getAddcode().hashCode());
        result = prime * result + ((getSaddress() == null) ? 0 : getSaddress().hashCode());
        result = prime * result + ((getCstmname() == null) ? 0 : getCstmname().hashCode());
        result = prime * result + ((getDlvaddress() == null) ? 0 : getDlvaddress().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getTeleno() == null) ? 0 : getTeleno().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getCreatedate() == null) ? 0 : getCreatedate().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getOptuser() == null) ? 0 : getOptuser().hashCode());
        result = prime * result + ((getModifyexpressreason() == null) ? 0 : getModifyexpressreason().hashCode());
        result = prime * result + ((getAddresstype() == null) ? 0 : getAddresstype().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getIsdefault() == null) ? 0 : getIsdefault().hashCode());
        result = prime * result + ((getIscommonuse() == null) ? 0 : getIscommonuse().hashCode());
        result = prime * result + ((getIsconfirm() == null) ? 0 : getIsconfirm().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getHashcode() == null) ? 0 : getHashcode().hashCode());
        result = prime * result + ((getIntenno() == null) ? 0 : getIntenno().hashCode());
        result = prime * result + ((getExpdlvtype() == null) ? 0 : getExpdlvtype().hashCode());
        result = prime * result + ((getNodlvtype() == null) ? 0 : getNodlvtype().hashCode());
        result = prime * result + ((getHeavyupstair() == null) ? 0 : getHeavyupstair().hashCode());
        result = prime * result + ((getValidate() == null) ? 0 : getValidate().hashCode());
        result = prime * result + ((getEtimes() == null) ? 0 : getEtimes().hashCode());
        return result;
    }
}
package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Orderdlvdata extends OrderdlvdataKey implements Serializable {
    private Long id;

    private String customerno;

    private String cstmname;

    private String dlvaddress;

    private String contactpsn;

    private String telno;

    private String postcode;

    private String idcard;

    private String dlvflag;

    private Date createtime;

    private Date updatetime;

    private String createuser;

    private String updateuser;

    private String province;

    private String city;

    private String district;

    private String carrierid;

    private String email;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
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

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno == null ? null : telno.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getDlvflag() {
        return dlvflag;
    }

    public void setDlvflag(String dlvflag) {
        this.dlvflag = dlvflag == null ? null : dlvflag.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid == null ? null : carrierid.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        Orderdlvdata other = (Orderdlvdata) that;
        return (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getCstmname() == null ? other.getCstmname() == null : this.getCstmname().equals(other.getCstmname()))
            && (this.getDlvaddress() == null ? other.getDlvaddress() == null : this.getDlvaddress().equals(other.getDlvaddress()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getTelno() == null ? other.getTelno() == null : this.getTelno().equals(other.getTelno()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getDlvflag() == null ? other.getDlvflag() == null : this.getDlvflag().equals(other.getDlvflag()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getCarrierid() == null ? other.getCarrierid() == null : this.getCarrierid().equals(other.getCarrierid()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getCstmname() == null) ? 0 : getCstmname().hashCode());
        result = prime * result + ((getDlvaddress() == null) ? 0 : getDlvaddress().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getTelno() == null) ? 0 : getTelno().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getDlvflag() == null) ? 0 : getDlvflag().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getCarrierid() == null) ? 0 : getCarrierid().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }
}
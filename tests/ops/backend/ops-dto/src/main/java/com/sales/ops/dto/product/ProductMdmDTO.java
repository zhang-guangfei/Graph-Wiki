package com.sales.ops.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/11/22 12:06
 */
public class ProductMdmDTO implements Serializable {

    private static final long serialVersionUID = -4524809749094506220L;


    /**
     * 型号
     */
    private String modelNo;

    /**
     * ecode码
     */
    private String eCode;

    /**
     * 中文名
     */
    private String chineseName;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 3S系列标识
     */
    private String typeId;

    /**
     * 大区分
     */
    private String classifyCode1;

    /**
     * 中区分
     */
    private String classifyCode2;

    /**
     * 小区分
     */
    private String classifyCode3;

    /**
     * 战略标识/竞争系数ID
     */
    private String competitivenessId;


    /**
     * 生产形式分类（标准品:1、简易特注品:2、一般特注品:3、完全特注品:4、修理品:5）
     */
    private String designTypeId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否限制贩卖品
     */
    private Boolean isRestrict;

    /**
     * 是否收敛
     */
    private Boolean isEos;


    /**
     * 竞争系数名称
     */
    private String competitivenessName;


    /**
     * 产品来源ID
     */
    private String sourceTypeId;

    /**
     * 产品来源名字
     */
    private String sourceTypeName;


    /**
     * 是否已删除
     */
    private Boolean isDeleted;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    /**
     * 创建人
     */
    private String createdUser;


    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updatedUser;

    /**
     * 最小包装量
     */
    private Integer minPackUnit;

    /**
     * 最小起订量
     */
    private Integer minQuantity;

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String geteCode() {
        return eCode;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getClassifyCode1() {
        return classifyCode1;
    }

    public void setClassifyCode1(String classifyCode1) {
        this.classifyCode1 = classifyCode1;
    }

    public String getClassifyCode2() {
        return classifyCode2;
    }

    public void setClassifyCode2(String classifyCode2) {
        this.classifyCode2 = classifyCode2;
    }

    public String getClassifyCode3() {
        return classifyCode3;
    }

    public void setClassifyCode3(String classifyCode3) {
        this.classifyCode3 = classifyCode3;
    }

    public String getCompetitivenessId() {
        return competitivenessId;
    }

    public void setCompetitivenessId(String competitivenessId) {
        this.competitivenessId = competitivenessId;
    }

    public String getDesignTypeId() {
        return designTypeId;
    }

    public void setDesignTypeId(String designTypeId) {
        this.designTypeId = designTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getRestrict() {
        return isRestrict;
    }

    public void setRestrict(Boolean restrict) {
        isRestrict = restrict;
    }

    public Boolean getEos() {
        return isEos;
    }

    public void setEos(Boolean eos) {
        isEos = eos;
    }

    public String getCompetitivenessName() {
        return competitivenessName;
    }

    public void setCompetitivenessName(String competitivenessName) {
        this.competitivenessName = competitivenessName;
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Integer getMinPackUnit() {
        return minPackUnit;
    }

    public void setMinPackUnit(Integer minPackUnit) {
        this.minPackUnit = minPackUnit;
    }
}

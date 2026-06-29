package com.sales.ops.dto.expdetail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class DataCodeDto {
    // 编码
    private String code;
    // 编码名称
    private String codeName;
    // 节点一
    private String extNote1;
    // 节点二
    private String extNote2;
    // 节点三
    private String extNote3;

    private int sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getExtNote1() {
        return extNote1;
    }

    public void setExtNote1(String extNote1) {
        this.extNote1 = extNote1;
    }

    public String getExtNote2() {
        return extNote2;
    }

    public void setExtNote2(String extNote2) {
        this.extNote2 = extNote2;
    }

    public String getExtNote3() {
        return extNote3;
    }

    public void setExtNote3(String extNote3) {
        this.extNote3 = extNote3;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public DataCodeDto()
    {

    }

    public DataCodeDto(String code, String codeName)
    {
        this.code=code;
        this.codeName=codeName;
    }

    public DataCodeDto(String code, String codeName, int sort)
    {
        this.code=code;
        this.codeName=codeName;
        this.sort = sort;
    }
    private List<DataCodeDto> children = new ArrayList<>(0); // 孩子节点
}

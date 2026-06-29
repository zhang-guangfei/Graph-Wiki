package com.smc.smccloud.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataCodeVO {
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

    public DataCodeVO()
    {

    }

    public DataCodeVO(String code, String codeName)
    {
        this.code=code;
        this.codeName=codeName;
    }

    public DataCodeVO(String code, String codeName, int sort)
    {
        this.code=code;
        this.codeName=codeName;
        this.sort = sort;
    }
    private List<DataCodeVO> children = new ArrayList<>(0); // 孩子节点 
}

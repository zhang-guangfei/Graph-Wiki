/*
 * @Descripttion:
 * @version:
 * @Author: zhang rui
 * @Date: 2019-11-12 09:50:51
 * @LastEditors: zhang rui
 * @LastEditTime: 2019-11-27 11:09:45
 */

package com.smc.smccloud.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeName implements Serializable {

    private static final long serialVersionUID = -7853120790032491063L;
    private String value; // 编码
    private String label; // 描述
    private String remark;
    private List<CodeName> children = new ArrayList<>(0); // 孩子节点

    public CodeName(String value, String label) {
        this.value = value;
        this.label = label;
    }
}

package com.sales.ops.dto.units;

import java.util.List;

/**
 * @author C12961
 * @date 2022/6/7 16:21
 */
public class ElementUITree {

    private String value;
    private String label;
    private List<ElementUITree> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ElementUITree> getChildren() {
        return children;
    }

    public void setChildren(List<ElementUITree> children) {
        this.children = children;
    }
}

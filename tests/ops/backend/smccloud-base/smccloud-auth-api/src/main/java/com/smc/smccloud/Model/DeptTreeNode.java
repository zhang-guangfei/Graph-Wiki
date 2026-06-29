package com.smc.smccloud.Model;

import lombok.Data;

import java.util.List;

@Data
public class DeptTreeNode {
    private String id;
    private String pid;
    private String name;
    private Boolean flag = false;
    private List<DeptTreeNode> children;
}

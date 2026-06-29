package com.smc.smccloud.model.Organ;

/**
 * @author C12961
 * @date 2021/11/26 10:09
 */
public class DeptDictDTO {

    private String deptId;
    private String deptName;

    public DeptDictDTO(String deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public DeptDictDTO() {
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}

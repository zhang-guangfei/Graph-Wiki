package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Employee 员工实体
 */

@Data
@TableName("hr_employee")
public class SaleEmployee
{

    /**
     * serialVersionUID:
     *
     * @since JDK 1.8
     */
    private static final long serialVersionUID = 4873228120289275456L;

    /**
     * 员工id
     */
    @TableField(value = "Id")
    private String id;
    /**
     * 员工姓名
     */
    @TableField(value = "Name")
    private String name;
    /**
     * 手机号码
     */
    @TableField(value = "CellPhone")
    private String cellPhone;
    /**
     * 邮箱
     */
    @TableField(value = "Email")
    private String Email;

    /**
     * 状态
     */
    @TableField(value = "Status")
    private String status;
    /**
     * 岗位信息
     */
    private List<SalePosition> salePostions = new LinkedList<SalePosition>();

}

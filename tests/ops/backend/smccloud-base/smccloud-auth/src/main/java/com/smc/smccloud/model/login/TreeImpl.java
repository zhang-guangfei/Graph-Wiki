package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class TreeImpl<T> extends BaseEntity {
    @TableField(value = "PID")
    private String pid;
    @TableField(value = "NAME")
    private String name;
    @TableField(value = "CODE")
    private String code;
    @TableField(value = "STATUS")
    private String status = "有效";

}

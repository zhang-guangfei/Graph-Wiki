package com.smc.smccloud.model.csstock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CsApplyVO {

    private static final long serialVersionUID = 1L;

    private Long applyId;

    /**
     * 代理代码
     */
    private String customerNo;

    /**
     * 仓库代码
     */
    private String stockCode;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 总申请数量
     */
    private Integer totalQty;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    /**
     * 门户状态管理，可以不用设置
     * 1-正式提交
     */
    private Integer status;

    private String remark;

    private String orderNo;

    /**
     * 专备-ppl
     */
    private  String pplNo;

    /**
     * 专备-项目代码
     */
    private String projectNo;

    /**
     * 专备-客户集团代码
     */
    private  String groupCustomerNo;

    /**
     * 营业所代码
     */
    private String deptNo;

    /**
     * 1-设置补货数量 2-申请补货
     */
    private Integer applyType;

    /**
     * 门户自定义申请号-不可为空
     */
    @NotEmpty
    private  String cApplyNo;


    /**
     * 专备-客户代码
     */
    private  String userNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    // po号
    private String corderNo;


}

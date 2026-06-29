package com.sales.ops.dto.order;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工单下发组装指令实体
 * @date ：Created in 2022/9/4 13:53
 */
public class OpsSendPcoAndDoMidDto implements Serializable {
    private static final long serialVersionUID = 1045515683806021267L;
    private String ForderNo;//10位订单号
    private OpsDoAndItemDto doDto;//do下发报文
    private OpsPcoAddItemDto pcoDto;//pco下发报文
    private String xfName;//下发区分

    public OpsSendPcoAndDoMidDto(){}

    public OpsSendPcoAndDoMidDto(OpsDoAndItemDto doDto,OpsPcoAddItemDto pcoDto,String ForderNo,String xfName){
        this.ForderNo = ForderNo;
        this.doDto = doDto;
        this.pcoDto = pcoDto;
        if(StringUtils.isNotBlank(xfName)){
            this.xfName = xfName;
        }
    }

    public String getForderNo() {
        return ForderNo;
    }

    public void setForderNo(String forderNo) {
        ForderNo = forderNo;
    }

    public OpsDoAndItemDto getDoDto() {
        return doDto;
    }

    public void setDoDto(OpsDoAndItemDto doDto) {
        this.doDto = doDto;
    }

    public OpsPcoAddItemDto getPcoDto() {
        return pcoDto;
    }

    public void setPcoDto(OpsPcoAddItemDto pcoDto) {
        this.pcoDto = pcoDto;
    }

    public String getXfName() {
        return xfName;
    }

    public void setXfName(String xfName) {
        this.xfName = xfName;
    }
}

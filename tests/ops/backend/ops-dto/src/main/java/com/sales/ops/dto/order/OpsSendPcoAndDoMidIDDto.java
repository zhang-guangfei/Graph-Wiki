package com.sales.ops.dto.order;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPco;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工单下发组装指令实体
 * @date ：Created in 2022/9/4 13:53
 */
public class OpsSendPcoAndDoMidIDDto implements Serializable {
    private static final long serialVersionUID = 1045515683806021267L;
    private String ForderNo;//10位订单号
    private String doId;//do下发报文
    private String pcoId;//pco下发报文
    private String xfName;//货齐下发来源；
    private OpsDo updateOpsDo;//订单修改
    private OpsPco udateOpsPco;//订单修改
    private String cproductNo;//订单修改

    private Map<String,String> yuKuMap;//<subModelNo,pcoId> 越库 上预约用

    private String yuKuRoId;//越库用 传roId

    public OpsSendPcoAndDoMidIDDto(){}

    public OpsSendPcoAndDoMidIDDto(String doId,String pcoId,String forderNo,String xfName){
        this.doId = doId;
        this.pcoId = pcoId;
        this.ForderNo = forderNo;
        this.xfName = xfName;
    }

    public String getForderNo() {
        return ForderNo;
    }

    public void setForderNo(String forderNo) {
        ForderNo = forderNo;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

    public Map<String, String> getYuKuMap() {
        return yuKuMap;
    }

    public void setYuKuMap(Map<String, String> yuKuMap) {
        this.yuKuMap = yuKuMap;
    }

    public String getYuKuRoId() {
        return yuKuRoId;
    }

    public void setYuKuRoId(String yuKuRoId) {
        this.yuKuRoId = yuKuRoId;
    }

    public String getXfName() {
        return xfName;
    }

    public void setXfName(String xfName) {
        this.xfName = xfName;
    }

    public OpsDo getUpdateOpsDo() {
        return updateOpsDo;
    }

    public void setUpdateOpsDo(OpsDo updateOpsDo) {
        this.updateOpsDo = updateOpsDo;
    }

    public OpsPco getUdateOpsPco() {
        return udateOpsPco;
    }

    public void setUdateOpsPco(OpsPco udateOpsPco) {
        this.udateOpsPco = udateOpsPco;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }
}

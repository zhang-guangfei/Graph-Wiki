package com.sales.ops.dto.ips.zb;



import java.util.Date;
import java.util.List;


public class PrepareOrderInfoDto {
    // 可被下游系统使用：0不可使用；1可使用
    private String canbeDownstreamUsed;

    // "1",是否需要决算： 0不需要；1需要
    private String needLiquidation;

    // 是否提供决算信息 0 未提供 1 已提供
    private String supplyLiquidationinfo;

    private Date planDeadlineDate;

    // 中止时间
    private Date terminateDate;
    // 决算时间
    private Date finalAccountDate;

    // 决算清单是否确定：0 不确定；1 已确定(过三个月核销期变为1)
    private String ackFinalAccount;

    // 订单残信息(废弃)
    private List<OrderResidueInfoDto> orderResidueInfo;

    // 核销信息
    private List<VerificationInfoDto> verificationInfo;

    // 清算信息
    private List<LiquiDationInfoDto> liquiDationInfo;

    // 备库残信息
    private List<ReserveResInfo> reserveResInfo;

    // 决算信息
    private List<AccountInfoDto> finalAccountInfo;

    public List<AccountInfoDto> getFinalAccountInfo() {
        return finalAccountInfo;
    }

    public void setFinalAccountInfo(List<AccountInfoDto> finalAccountInfo) {
        this.finalAccountInfo = finalAccountInfo;
    }

    public String getCanbeDownstreamUsed() {
        return canbeDownstreamUsed;
    }

    public Date getPlanDeadlineDate() {
        return planDeadlineDate;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public Date getFinalAccountDate() {
        return finalAccountDate;
    }

    public void setFinalAccountDate(Date finalAccountDate) {
        this.finalAccountDate = finalAccountDate;
    }

    public String getAckFinalAccount() {
        return ackFinalAccount;
    }

    public void setAckFinalAccount(String ackFinalAccount) {
        this.ackFinalAccount = ackFinalAccount;
    }

    public List<ReserveResInfo> getReserveResInfo() {
        return reserveResInfo;
    }

    public void setReserveResInfo(List<ReserveResInfo> reserveResInfo) {
        this.reserveResInfo = reserveResInfo;
    }

    public void setPlanDeadlineDate(Date planDeadlineDate) {
        this.planDeadlineDate = planDeadlineDate;
    }

    public void setCanbeDownstreamUsed(String canbeDownstreamUsed) {
        this.canbeDownstreamUsed = canbeDownstreamUsed;
    }

    public String getNeedLiquidation() {
        return needLiquidation;
    }

    public void setNeedLiquidation(String needLiquidation) {
        this.needLiquidation = needLiquidation;
    }

    public String getSupplyLiquidationinfo() {
        return supplyLiquidationinfo;
    }

    public void setSupplyLiquidationinfo(String supplyLiquidationinfo) {
        this.supplyLiquidationinfo = supplyLiquidationinfo;
    }

    public List<OrderResidueInfoDto> getOrderResidueInfo() {
        return orderResidueInfo;
    }

    public void setOrderResidueInfo(List<OrderResidueInfoDto> orderResidueInfo) {
        this.orderResidueInfo = orderResidueInfo;
    }

    public List<VerificationInfoDto> getVerificationInfo() {
        return verificationInfo;
    }

    public void setVerificationInfo(List<VerificationInfoDto> verificationInfo) {
        this.verificationInfo = verificationInfo;
    }

    public List<LiquiDationInfoDto> getLiquiDationInfo() {
        return liquiDationInfo;
    }

    public void setLiquiDationInfo(List<LiquiDationInfoDto> liquiDationInfo) {
        this.liquiDationInfo = liquiDationInfo;
    }
}

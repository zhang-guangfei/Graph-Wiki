package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreStockDetailViewVO;
import com.smc.smccloud.model.purchase.OpsPurchaseOrderVO;
import com.smc.smccloud.model.shikomi.HrOrganizationDTO;
import com.smc.smccloud.model.shikomi.ShikomiTotalDO;
import com.smc.smccloud.model.shikomi.VShikomiTotalDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;
import java.util.List;

@Mapper
@DS("opsdb")
public interface ShikomiTotalMapper extends BaseMapper<ShikomiTotalDO> {

    @Select("{ call dbo.shikomi_updateinfo }")
    @Options(statementType = StatementType.CALLABLE)
    String calshikomiUpdateInfo();

    @Select("select sum(RemainQty) from v_shikomi_total  where modelno = #{modelNo} AND ClassCode !=4 AND Status = 1")
    Integer listRemainQty(@Param("modelNo") String modelNo);

    @Select("<script>"+
            "SELECT ApplyNo,ShikomiNo,CustomerNo,DeptNo,RemainQty,ApplyQty,CompanyCode,modelno,QtyWarning,ClassCode,LotQty FROM v_shikomi_total " +
            "WHERE (ModelNo=#{modelNo} or #{modelNo} like serialModel) AND Status = 1 " +
            " <if test='customerNo!=null and customerNo!=\"\" '> and CustomerNo=#{customerNo} </if> "+
            " <if test='quantity!=null and quantity!=\"\" '> and RemainQty>=#{quantity} </if> "+
            "</script>")
    List<VShikomiTotalDO> findByModelNoList(@Param("modelNo") String modelNo, @Param("customerNo") String customerNo, @Param("quantity") Integer quantity);

    @Select("SELECT Level,Name,unitCode, ParentId FROM hr_organization WHERE id=#{deptNo}")
    HrOrganizationDTO selectDeptNoInfo(@Param("deptNo") String deptNo);

    @Select("<script>"+
            "SELECT TOP (1) orderNo,itemNo,splitItemNo FROM ops_requestPurchase_cancel_log WHERE orderNo = #{orderNo} and itemNo=#{itemNo} "+
            " <if test='splitItemNo!=null and splitItemNo!=\"\" '> and splitItemNo=#{splitItemNo} </if> "+
            "</script>")
    OpsPurchaseOrderVO selectPurchaseOrder(@Param("orderNo")String orderNo,@Param("itemNo") Integer itemNo,@Param("splitItemNo") Integer splitItemNo);

    @Select("SELECT HRUnitId FROM OrganizationRelation WHERE id = #{deptNo}")
    String getDept(@Param("deptNo") String deptNo);

    @Select("SELECT count(*) FROM ops_purchaseOrder WHERE shikomiAnswerNo = #{shikomiNo} and purchaseDate>=#{startDate} and purchaseDate<=#{endDate} and stateCode in (2,3,5)")
    Integer getShikomiNoord(@Param("shikomiNo") String shikomiNo,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Select("<script>" +
            "SELECT id,ShikomiNo,ModelNo,SupplierCode,LastQty,ApplyQty,RemainQty,MaxQty,QtyOrdPre,serialModel,Status,CustomerNo,AnswerText,Remark," +
            "RegistDate,ReviseDate,ClassType,ClassCode,SubsidiaryCode,CompanyCode,BranchCode,DlvDate,DeptNo,ApplyTime,OrderQty,CancelQty,ModelType," +
            "UpdateTime,CreateTime,CreateUser,UpdateUser,ApplyPsnName,EPrice,QtyWarning,ApplicantNo,ApplicantName,ApplicantEmail,ApproverNo," +
            "ApproverName,ApproverEmail,AsseDays,isWarning,inspectApplyNo,modelCount,InspectApproverTime,inspectQty,InspectApproverName," +
            "InspectApproverNo,inspectApplyType,LastOrdDate,avgOrdQty,warehouseCode,projectNo,PlanUseDate,pplNo,InspectAnswerText,inspectType," +
            "inspectDaily,InspectAnswerPsnNo,InspectAnswerPsnName,InspectAnswerTime,inspectSendTime,inspectStatus,qtyOnhand,qtyPO,Rohs,applyNo," +
            "partPrepareDays,IndCode,LotQty,Price_lot,QtyNoord " +
            " FROM shikomi_total where " +
            " <foreach collection='totalList' item='item' index='index' open='(' separator=') or (' close=')'> " +
            " ShikomiNo = #{item.shikomiNo} and SupplierCode = #{item.supplierCode} " +
            " </foreach>" +
            "</script>")
    List<ShikomiTotalDO> selectShikomiTotalByList(@Param("totalList") List<ShikomiTotalDO> totalList);

    @Update("<script>" +
            " <foreach collection='list' item='item' index='index' separator=';' > " +
            " update shikomi_total set ModelNo=#{item.modelNo}, SupplierCode=#{item.supplierCode}, ShikomiNo=#{item.shikomiNo}, SubsidiaryCode=#{item.subsidiaryCode}, ClassType=#{item.classType}, CompanyCode=#{item.companyCode}, " +
            " BranchCode=#{item.branchCode},LotQty=#{item.lotQty},RemainQty=#{item.remainQty},MaxQty=#{item.maxQty},QtyOrdPre=#{item.qtyOrdPre},Status=#{item.status},RegistDate=#{item.registDate},ReviseDate=#{item.reviseDate}, " +
            " partPrepareDays=#{item.partPrepareDays},AsseDays=#{item.asseDays},QtyNoord=#{item.qtyNoord},Remark=#{item.remark},Rohs=#{item.rohs},ClassCode=#{item.classCode},ModelType=#{item.modelType},UpdateTime=GETDATE(),UpdateUser='JP' " +
            " where ShikomiNo=#{item.shikomiNo} and SupplierCode=#{item.supplierCode} " +
            " </foreach>" +
            "</script>")
    int updateShikomiTotalByList(@Param("list") List<ShikomiTotalDO> list);


    /**
     * 根据申请号查询prestock_detail_view
     */
    @Select("select * from ops_sharedb.dbo.prestock_detail_view where apply_no = #{applyNo} and delFlag = '0' ")
    List<PreStockDetailViewVO> getPreStockDetailViewByApplyNo(@Param("applyNo") String applyNo);

}

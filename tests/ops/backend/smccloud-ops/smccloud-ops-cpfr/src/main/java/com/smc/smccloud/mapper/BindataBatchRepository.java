package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.bindata.BindataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BindataBatchRepository extends BaseMapper<BindataDO> {

    @Select("select * from bindata where delFlag=0 and stockType=1 and warehouse_code=#{warehouseCode} and inventory_type_code='TY' and qtyBin>0")
    List<BindataDO> findWarehouseBindata(String warehouseCode);

    //MonthsOf8,CustomersOf8
    @Update("update bin_order_detail set freq=f.MonthsOf8,customers=f.CustomersOf8\n" +
            "from bin_order_detail b \n" +
            "inner join ops_report.dbo.model_exp_freq f on f.modelno=b.model_no and f.StockType=1 and f.StockCode=b.warehouse_code\n" +
            "where b.calc_id=#{calcId} and (b.freq is null or b.freq=0)")
    void updateFreq(Long calcId);

    //mean_all
    @Update("update bin_order_detail set mean_all=case isnull(f.AvgQtyOf12,0) when 0 then 1 else f.AvgQtyOf12 end,\n" +
            "stock_months_all=round(1.0*stock_qty_all/case isnull(f.AvgQtyOf12,0) when 0 then 1 else f.AvgQtyOf12 end,1)\n" +
            "from bin_order_detail b\n" +
            "inner join ops_report.dbo.model_exp_freq f on f.ModelNo=b.model_no\n" +
            "and f.StockType=0 and f.StockCode='ALL' and f.ModelType=1\n" +
            "where b.calc_id=#{calcId}")
    void updateMeanAll(Long calcId);


    //bincell_all,qtybin_all
    @Update("update bin_order_detail set bincell_all=a.bincell,qtybin_all = a.qtybin\n" +
            "from bin_order_detail b \n" +
            "inner join (select modelno,sum(bincell) as bincell ,sum(QtyBin) as qtybin\n" +
            "from Bindata b where stockType=1 and delFlag=0 group by ModelNo) a on b.model_no=a.ModelNo\n" +
            "where b.calc_id=#{calcId}")
    void updateBinCellAll(Long calcId);



    @Select("WITH MaxDates AS (\n" +
            "SELECT b.model_no, MAX(a.hopeExportDate) AS maxdate\n" +
            "FROM ops_requestPurchase a\n" +
            "INNER JOIN bin_order_detail b ON b.model_no = a.modelNo\n" +
            "WHERE a.requestWarehouseId = #{warehouseCode} AND b.calc_id = #{calcId} AND a.purchaseType = 'K' AND a.stateCode NOT IN ('9', '6')\n" +
            "GROUP BY b.model_no\n" +
            ")\n" +
            "UPDATE m SET m.last_dlvdate = c.maxdate\n" +
            "FROM bin_order_detail m\n" +
            "INNER JOIN MaxDates c ON c.model_no = m.model_no\n" +
            "WHERE m.calc_id = #{calcId}  ")
    void updateLastDlvdate(Long calcId, String warehouseCode);

    @Update("update bin_order_detail set center_warehouse=d.warehouse_code\n" +
            "from  bin_order_detail b \n" +
            "inner join Bindata d on b.calc_type=d.StockType and d.warehouse_code=b.warehouse_code and delFlag=0 and b.model_no=d.ModelNo\n" +
            "where b.calc_id=#{calcId} and d.centre_flag=1 ")
    void updateCenterWarehouse(Long calcId);

    @Update("update bin_order_detail set trans_type=b.transtype\n" +
            "from bin_order_detail a inner join ops_po_transtype_config b on b.modelNo=a.model_no\n" +
            "where a.calc_id=#{calcId} and b.type='1'")
    void updateTransType(Long calcId);


    @Update("update bin_order_detail set eprice=p.EPrice,weight=s.weight,std_lead_time=d.stdDlvDay,min_pack_qty=t.min_pack_unit, \n" +
            "box_qty=t.OuterBoxQuantity,box_no=t.OuterBoxPartNo,main_supplier_code=isnull(a.main_supplier_code,d.supplyId) " +
            "FROM bin_order_detail a " +
            "LEFT JOIN product_price p on p.ModelNo =a.model_no " +
            "LEFT JOIN product_physics s ON s.ModelNo=a.model_no " +
            "LEFT JOIN product_delivery d ON d.ModelNo=a.model_no and d.isPrimary=1 and d.is_deleted=0 " +
            "LEFT JOIN product t ON t.ModelNo = a.model_no " +
            "WHERE a.calc_id=#{calcId} and p.MinQuantity=1 ")
    void updateProductInfo(Long calcId);

    @Select("select b.* from bindata b " +
            "inner join ops_warehouse w on b.warehouse_code=w.warehouse_code and w.warehouse_type='MASTER' and w.delflag=0 " +
            "where b.delFlag=0 and b.stockType='1' ")
    List<BindataDO> findBindataByMasterWarehouse();

}

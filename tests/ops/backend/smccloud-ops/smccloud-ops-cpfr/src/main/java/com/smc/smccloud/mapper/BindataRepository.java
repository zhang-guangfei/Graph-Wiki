package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.bindata.ProductInfoDTO;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface BindataRepository extends BaseMapper<BindataDO> {


    @Select("select * from bindata where delFlag=0 and stockType=4 and qtyBin>0 and adjustable=0 ")
    List<BindataDO> findCustomerBindata();

    @Select("select * from bindata where delFlag=0 and stockType=1 and warehouse_code=#{warehouseCode}  and inventory_type_code='TY' and qtyBin>0")
    List<BindataDO> findWarehouseBindata(String warehouseCode);

    @Select("select top 1 * from bindata where stockType=#{stockType} and modelNo=#{modelno} and warehouse_code=#{warehouseCode} order by delFlag")
    BindataDO findBindataIncludeNotBIN(Integer stockType, String modelno, String warehouseCode);

    // 批量版必须保留 order by delFlag 的优先级：未删除记录优先，取不到时软删除记录仍可参与非 BIN 计算。
    @Select("<script>" +
            "select x.id,x.stockType,x.warehouse_code,x.ModelNo,x.QtyBin,x.BinCell,x.delFlag," +
            "x.freq,x.mean,x.SetLevel,x.SupplierCode,x.orderType,x.SetFreq " +
            "from (" +
            "select b.id,b.stockType,b.warehouse_code,b.ModelNo,b.QtyBin,b.BinCell,b.delFlag," +
            "b.freq,b.mean,b.SetLevel,b.SupplierCode,b.orderType,b.SetFreq," +
            "row_number() over (partition by b.stockType,b.ModelNo,b.warehouse_code order by b.delFlag asc) as rn " +
            "from bindata b " +
            "where b.stockType=#{stockType} and b.warehouse_code=#{warehouseCode} and b.ModelNo in " +
            "<foreach item='modelNo' collection='modelNos' open='(' separator=',' close=')'>#{modelNo}</foreach>" +
            ") x where x.rn=1" +
            "</script>")
    List<BindataDO> findBindataIncludeNotBINBatch(@Param("stockType") Integer stockType,
                                                   @Param("warehouseCode") String warehouseCode,
                                                   @Param("modelNos") List<String> modelNos);


    @Select("  select modelno,sum(bincell) as bincell_all ,sum(QtyBin) as qtybin_all \n" +
            "  from Bindata b where stockType=1 and delFlag=0 and modelno=#{modelno} \n" +
            "  group by ModelNo \n")
    BinOrderDetailDO getBinCellSumByStockType(String modelno);

    @Select("SELECT TOP 1 p.EPrice,s.weight,d.stdDlvDay,t.min_pack_unit,t.OuterBoxQuantity,t.OuterBoxPartNo,d.supplyId " +
            "FROM product_price p " +
            "LEFT JOIN product_physics s ON p.ModelNo=s.ModelNo " +
            "LEFT JOIN product_delivery d ON d.ModelNo=p.ModelNo and d.isPrimary=1 and d.is_deleted=0" +
            "LEFT JOIN product t ON t.ModelNo = p.ModelNo " +
            "WHERE p.ModelNo =#{modelNo} and p.MinQuantity=1 ")
    ProductInfoDTO getProductInfo(@Param("modelNo") String modelNo);

    //type 0:优先使用此运输方式;1:必须使用此运输方式
    @Select("select top 1 transtype from ops_po_transtype_config where type='1' and modelno=#{modelno}")
    String getTransType(String modelno);


    @Select("select MAX(hopeExportDate) from ops_requestPurchase " +
            "where modelno=#{modelno} and requestWarehouseId=#{warehouseCode} " +
            "and purchaseType = 'K' and stateCode NOT IN ('9', '6')  " +
            "group by modelno")
    Date getMaxDlvDate(String modelno, String warehouseCode);

    @Select("select parent_code from ops_warehouse where warehouse_code=#{warehouseCode}")
    String getParentCode(String warehouseCode);

    @Select("select top 1 warehouse_code,client from bindata where delFlag=0 and stockType=1 and modelNo=#{modelno} and centre_flag=1 ")
    BindataDO getCenterWarehouse(String modelno);

    @Select("select warehouse_code from bindata_client_warehouse where bindata_id=#{id} and del_flag=0")
    List<String> getClientsById(Long id);
}

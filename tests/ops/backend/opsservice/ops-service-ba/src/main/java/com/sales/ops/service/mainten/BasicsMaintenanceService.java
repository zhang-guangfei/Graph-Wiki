package com.sales.ops.service.mainten;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsBsQuerypriceNewmodel;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.dto.mainten.ModelData;

import java.util.List;
import java.util.Map;

public interface BasicsMaintenanceService {

    /**
     * 产品型号维护： 根据型号代码模糊查询产品型号的信息（分页）
     *
     * @param modelNo 型号
     * @param pageNumber 页码
     * @param pageSize 页数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页型号数据
     */
    PageInfo<ModelData> findProductModelByNo(String modelNo,Boolean fuzzy, Integer pageNumber
            , Integer pageSize, String startTime, String endTime);

    /**
     * 产品型号维护： 根据型号代码模糊查询产品型号的信息（不分页）
     *
     * @param modelNo
     * @return List<ModelData>
     */
    List<ModelData> findProductModelListByNo(String modelNo);

    /**
     * 产品型号维护： 根据整型号查询产品型号信息
     * @param modelNo
     * @return
     */
    List<ModelData> findSplitInfoByWholeNo(String modelNo);

    /**
     * 产品型号维护： 根据整型号只查询整型号的信息
     *
     * @param modelNo
     * @return
     */
    List<ProductBom> findWholeModelInfoByModelNo(String modelNo);

    /**
     * 产品型号维护： 根据整型号只修改整型号的信息
     *
     * @param productBom
     */
    Integer updateWholeModelInfoByModelNo(List<ProductBom> productBom);

    /**
     * 增加产品型号： 根据表单提交的信息新增产品型号
     * @param data 表单信息
     */
    Integer insertProductModel(List<ModelData> data);

    /**
     * 供应商维护： 根据型号代码模糊查询供应商信息
     *
     * @param modelNo 型号
     * @param pageNumber 页码
     * @param pageSize 页数
     * @return 分页供应商数据
     */
    PageInfo<ProductDelivery> findProductDeliveryByNo(String modelNo, Integer pageNumber
            , Integer pageSize, String startTime, String endTime);

    /**
     * 获取所有的供应商数据
     * @return List<Map<String, String>>
     */
    List<Map<String, String>> findAllSupplier();

    /**
     * 获取所有原产国数据
     * @return Map<String, String>
     */
    Map<String, String> findCountryBySupplierId(String supplyId);

    /**
     * 获取所有原产国数据
     *
     * @return List<Map<String, String>>
     */
    List<Map<String, String>> findAllCountry();

    /**
     * 增加供应商： 根据表单提交的信息新增供应商
     *
     * @param lists
     * @param dto
     * @return
     */
    int insertProductDelivery(List<ProductDelivery> lists);

    /**
     * @See根据入参类的属性，编辑供应商数据
     *
     * @Author SuPeng
     * @Param delivery 入参实体
     * @Date 2022/11/08 14:59
     * @Version 版本1.0 修改Bug8480，内容：（编辑供应商时，将“非主供应商”修改为“主供应商”。不会对原来的“主供应商”重置为“非主供应商”，导致的结果：会出现多条主供应商同时存在的情况）
     */
    void updateProductDelivery(ProductDelivery delivery);

    /**
     * 删除供应商： 根据型号删除供应商记录
     * @param modelNo 型号
     */
    void deleteProductDelivery(String modelNo, String supplyId);

    /**
     * 根据信号查询供应商记录
     * @param modelNo 型号
     * @return ProductDelivery
     */
    ProductDelivery selectDeliveryByModelNo(String modelNo, String supplierId);

    /**
     * 查询供应商： 根据型号模糊查询所有供应商
     * @param modelNo 型号
     * @return List<ProductDelivery>
     */
    List<ProductDelivery> selectDeliveryListByNo(String modelNo);

    /**
     *
     * @param modelNo
     * @return
     */
    List<ProductDelivery> findBeingSuppliersByModelNo(String modelNo);

    /**
     * 询价 分页
     * @return
     */
    List<OpsBsQuerypriceNewmodel> findQueryPriceNewModelByModelNo(String modelNo);

    /**
     * 编辑询价信息
     * @param lists
     * @param userDto
     * @return
     */
    int insertQueryPrice(List<ProductDelivery> lists);
}

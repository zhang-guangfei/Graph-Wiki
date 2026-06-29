package com.smc.smccloud.service;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.bindata.BinDataQueryRequest;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.CustomerExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.service.hystrix.BinServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

// url = "http://10.116.194.236:8102"

@CrossOrigin
@FeignClient(name = "cpfr-service",
        contextId = "cpfr-bindata",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = BinServiceFeignHystrix.class)
public interface BinServiceFeignApi {
    /**
     * 查询型号的BIN信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/listModelBinData", method = RequestMethod.POST)
    ResultVo<BindataVO[]> listModelBinData(@RequestBody BindataRequest dto);

    /**
     * 查询BIN管理
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/listBindata", method = RequestMethod.POST)
    ResultVo<PageInfo<BindataVO>> listBindata(@RequestBody BindataRequest dto);

    /**
     * 导入Excel导入Bindata
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/importBindata", method = RequestMethod.POST)
    ResultVo<String> importBindata(@RequestParam("file") MultipartFile file);

//    @RequestMapping(value = "/cpfr/bindata/importBindata", method = RequestMethod.POST)
//    ResultVo<String> checkImpBindataFileStatus(String key);

    /**
     * 保存Bindata
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/saveBindata", method = RequestMethod.POST)
    ResultVo<String> saveBindata(@RequestBody BindataVO dto);

    /**
     * 删除Bindata
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/deleteBindata", method = RequestMethod.POST)
    ResultVo<String> deleteBindata(@RequestBody BindataRequest request);

    /**
     * 查询型号的BINdata信息
     */
    @RequestMapping(value = "/cpfr/bindata/getBindataByModelNo", method = RequestMethod.POST)
    ResultVo<BindataVO> getBindataByModelNo(@RequestParam("modelNo") String modelNo,
                                            @RequestParam("warehouseCode") String warehouseCode);

    @RequestMapping(value = "/cpfr/bindata/getBinCountByModelNo", method = RequestMethod.POST)
    ResultVo<List<Integer>> getBinCountByModelNo(@RequestBody List<String> modelNos,@RequestParam("warehouseCode") String warehouseCode);

    /**
     * 查询型号的BINdata信息
     */
    @RequestMapping(value = "/cpfr/bindata/getByModelNoAndWarehouse", method = RequestMethod.POST)
    ResultVo<Map<String, BindataVO>> getBindataByModelNoAndWarehouse(@RequestParam("modelNo") String modelNo,
                                                                     @RequestBody List<String> warehouseList);
    @RequestMapping(value = "/cpfr/bindata/getBindataByModelNoAnBinType", method = RequestMethod.POST)
    ResultVo<List<BindataVO>> getBindataByModelNoAnBinType(@RequestParam("modelNo") String modelNo,
                                                           @RequestParam("binType") String binType);
    /**
     * 判断型号是否为BIN
     * @param modelNos
     * @param warehouseList
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/checkModelIsBin", method = RequestMethod.POST)
    ResultVo<Map<String, Boolean>> checkModelIsBin(@RequestBody BindataRequest request);
    /**
     * 查询型号(集合)的BINdata信息
     */
    @RequestMapping(value = "/cpfr/bindata/getBindataByModelNos", method = RequestMethod.POST)
    ResultVo<List<BindataVO>> getBindataByModelNos(@RequestBody BinDataQueryRequest request);


    /**
     * 判断是否存在该型号的bin
     *
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/isBinModel", method = RequestMethod.GET)
    ResultVo<Boolean> isBinModel(@RequestParam("modelNo") String modelNo);

    /**
     * 导出binData数据
     *
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/exportBinData", method = RequestMethod.POST)
    void exportBinData(@RequestBody BindataRequest dto);

    /**
     * 更新产品信息
     *
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/updateProductInfo", method = RequestMethod.POST)
    ResultVo<String> updateProductInfo();

    /**
     * 推送给门户补库
     *
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/listBinDataForReplQty", method = RequestMethod.GET)
    ResultVo<List<BindataVO>> listBinDataForReplQty();

    /**
     * 查询客户先行在库自动补库清单
     *
     * @param request 补库客户信息
     * @return 客户自动补库清单
     */
    @RequestMapping(value = "/cpfr/bindata/getBinDataForAutoPreStock", method = RequestMethod.POST)
    ResultVo<List<BindataVO>> getBinDataForAutoPreStock(@RequestBody BindataRequest request);


    /**
     * 查询客户型号的仓库明细
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/getCustomerModelFreq", method = RequestMethod.POST)
    ResultVo<List<ModelExpFreqVO>> getCustomerModelFreq(@RequestBody CustomerExpFreqRequest request);

    @RequestMapping(value = "/cpfr/bindata/getModelExpFreq", method = RequestMethod.POST)
    ResultVo<List<ModelExpFreqVO>> getModelExpFreq(@RequestBody ModelExpFreqRequest request) ;

    @RequestMapping(value = "/cpfr/bindata/getModelExpFreqWithRiskLevel", method = RequestMethod.POST)
    ResultVo<List<ModelExpFreqVO>> getModelExpFreqWithRiskLevel(@RequestBody ModelExpFreqRequest request) ;

//    ResultVo<List<ModelExpFreqVO>> getModelExpFreq(@RequestParam("modelType") String modelType, @RequestParam("modelNo") String modelNo,
//                                                   @RequestParam("stockCode") String stockCode, @RequestParam("stockType") String stockType);

    /**
     * bug11986,订单删除需要增加风险验证，新增查询接口获取所有大仓的 AvgQtyOf8字段
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/getMasterFreq", method = RequestMethod.GET)
    ResultVo<List<ModelExpFreqVO>> getMasterFreq(@RequestParam("modelNo") String modelNo);

//    @RequestMapping(value = "/cpfr/bindata/getBindataInfo", method = RequestMethod.POST)
//    ResultVo<List<BindataVO>> getBindataInfo(@RequestBody CsModelQryRequest csModelQryRequest);

    /**
     * 刷新bin缓存信息
     *
     * @return
     */
    @RequestMapping(value = "/cpfr/bindata/refreshBindataCache", method = RequestMethod.POST)
    ResultVo<String> refreshBindataCache();

}

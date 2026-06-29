package com.smc.smccloud.web.Controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.model.customer.*;
import com.smc.smccloud.model.warehouse.UpWarehouseDelflagVO;
import com.smc.smccloud.service.CustomerService;
import com.smc.smccloud.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/11/1 10:32
 * @Descripton TODO
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private CustomerService customerService;

    /**
     * 根据仓库类型获取仓库列表
     */
    @GetMapping("/wareHouse/findWareHouseByType")
    public ResultVo<List<WarehouseDO>> findWareHouseByType(@RequestParam("wareHouseType") String wareHouseType) {
        return warehouseService.findWareHouseByType(wareHouseType);
    }

    @GetMapping("/wareHouse/findWareHouseInfoWithLike")
    public ResultVo<List<WarehouseDO>> findWareHouseInfoWithLike(@RequestParam("code") String code,@RequestParam("type") String type) {
        return warehouseService.findWareHouseInfoWithLike(code, type);
    }

    @RequestMapping(value = "/wareHouse/getWarehouseParentCode", method = RequestMethod.POST)
    ResultVo<String> getWarehouseParentCode(@RequestParam( "warehouseCode") String warehouseCode){
        return warehouseService.getWarehouseParentCode(warehouseCode);
    }
    
    @GetMapping("/customer/getAllCustomerInfo")
    public ResultVo<List<CustomerVO>> getAllCustomerInfo(@RequestParam("customerNo") String customerNo) {
        return customerService.getAllCustomerInfo(customerNo);
    }

    // add by LiYingChao from bug 8759 in 2022-12-12
    /**
     * 获取备案客户列表
     */
    @PostMapping("/customer/findCustWldList")
    public ResultVo<PageInfo<OpsCustomerWldateVO>> findCustWldList(@RequestBody CustomerWldateRequest request) {
        return customerService.getOpsCustomerWldateList(request);
    }

    /**
     * 客户备案导出
     */
    @PostMapping("/customer/exportCustomerWldList")
    public void exportCustomerWldList(@RequestBody CustomerWldateRequest request) {
        customerService.exportCustomerWldList(request);
    }

    /**
     * 软删除客户备案
     */
    @PostMapping("/customer/delCustomerWldByCustomerNos")
    public ResultVo<String> delCustomerWldByCustomerNos(@RequestBody CustomerWldDelVO customerWldDelVO) {
        return customerService.delCustomerWldByCustomerNos(customerWldDelVO);
    }

    /**
     * 根据客户代码获取客户信息
     */
    @GetMapping("/customer/getCustomerInfoByCustomerNo")
    public ResultVo<OpsCustomerWldateVO> getCustomerInfoByCustomerNo(@RequestParam("customerNo") String customerNo) {
        return customerService.getCustomerInfoByCustomerNo(customerNo);
    }
    /**
     * 新增客户备案
     */
    @PostMapping("/customer/addCustomerWldInfo")
    public ResultVo<String> addCustomerWldInfo(@RequestBody OpsCustomerWldateVO opsCustomerWldateVO) {
        return customerService.addCustomerWldInfo(opsCustomerWldateVO);
    }
    /**
     * 客户备案模板下载
     */
    @GetMapping("/customer/downLoadExcel")
    public void downLoadExcel() {
        customerService.downLoadExcel();
    }

    /**
     * 导入客户备案
     * @param file
     * @return
     */
    @RequestMapping(value = "/customer/importCustomerWld", method = RequestMethod.POST)
    public ResultVo<String> importCustomerWld(@RequestParam("file") MultipartFile file,@RequestParam("loginUser") String loginUser) {
        return customerService.importCustomerWld(file,loginUser);
    }

    /**
     * 批量新增
     * @return
     */
    @RequestMapping(value = "/customer/batchCustomerWld", method = RequestMethod.POST)
    public ResultVo<String> batchCustomerWld(@RequestBody BatchAddCustomerWldVO batchAddCustomerWldVO) {
        return customerService.batchAddCustomerWld(batchAddCustomerWldVO);
    }

    @PostMapping("/upWarehouseDelFlag")
    public ResultVo<String> upWarehouseDelFlag (@RequestBody UpWarehouseDelflagVO upinfo) {
        return warehouseService.upWarehouseDelFlag(upinfo);
    }

}

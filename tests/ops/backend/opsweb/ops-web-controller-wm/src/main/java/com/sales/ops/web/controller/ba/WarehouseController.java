package com.sales.ops.web.controller.ba;


import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseAddress;
import com.sales.ops.db.entity.OpsWarehouseAddressConfig;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.warehouse.WarehouseAddressDto;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.ba.OpsWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@CrossOrigin
@RestController
@RequestMapping(value = "/warehouseManage/warehouse")
@Slf4j
public class WarehouseController {

    @Resource
    private OpsWarehouseService warehouseService;
    @Autowired
    private OpsRedissonLock opsRedissonLock;


    /**
     * 仓库多地址查询页面 list
     * @param pageModel
     * @return
     */
    @PostMapping(value = "/warehouseMulAddress/list")
    public CommonResult getWarehouseMulAddress(@RequestBody PageModel<OpsWarehouseAddress> pageModel) {
        PageInfo<OpsWarehouseAddress> result = warehouseService.findMulAddressByPage(pageModel);
        return result.getList().isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }

    @PostMapping(value = "/warehouseMulAddress/add")
    public CommonResult warehouseMulAddressAdd(@RequestBody OpsWarehouseAddress warehouse) {
        Integer result = warehouseService.addMulAddress(warehouse);
        return result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",obj:" + warehouse) :
                CommonResult.success("result:" + result + ",obj:" + warehouse);
    }


    @PostMapping(value = "/warehouseMulAddress/checkAddressConfig")
    public CommonResult<Long> checkAddressConfig(@RequestBody List<Long> list) {
        Long result = warehouseService.getMulAddressCountByAddressId(list);
        if(result.equals(0L)){
            return CommonResult.success();
        }else {
            return CommonResult.failure("存在关联关系，不可删除，请先取消关联关系");
        }

    }
    @PostMapping(value = "/warehouseMulAddress/update")
    public CommonResult warehouseMulAddressUpdate(@RequestBody OpsWarehouseAddress record) {
        Integer result = warehouseService.modifyMulAddress(record);
        return result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",id:" + record.getWarehouseCode()) :
                CommonResult.success("result:" + result + ",id:" + record.getWarehouseCode());
    }


    @GetMapping(value = "/warehouseMulAddress/delete/{code}")
    public CommonResult warehouseMulAddressDeleteByCode(@PathVariable Long code) {
        List<Long> list = new ArrayList<Long>();
        list.add(code);
        Integer result = warehouseService.removeMulAddressList(list);
        return result == null ||  result.equals(0)?
                CommonResult.failure("result:" + result + ",id:" + code) :
                CommonResult.success("result:" + result + ",id:" + code);
    }

    @PostMapping(value = "/warehouseMulAddress/deletes")
    public CommonResult warehouseMulAddressDeleteByCodes(@RequestBody List<Long> list) {
        Integer result = warehouseService.removeMulAddressList(list);
        return result == null || result.equals(0)  ?
                CommonResult.failure("result:" + result + ",id:" + list) :
                CommonResult.success("result:" + result + ",id:" + list);
    }

    /**
     * bugid:11445 c14717 20230721
     * 保存仓库多地址
     * @param warehouseAddressDto
     * @return
     */
    @PostMapping(value = "/saveMultAaddressConfig")
    public CommonResult<Integer> saveMultAaddressConfig(@RequestBody WarehouseAddressDto warehouseAddressDto) {
        boolean lock = false;
        Integer result = null;
        String lockKey = "ops:saveMultAaddress:" + warehouseAddressDto.getWarehouseCode();
        try {
            lock = opsRedissonLock.addLock(lockKey);
            result = warehouseService.saveMultAaddressConfig(warehouseAddressDto);
        }catch (OpsException e) {
            return CommonResult.failure(e.getMessage());
        }catch (Exception e) {
            log.error("多地址",e);
            return CommonResult.failure("保存失败");
        } finally {
            if (lock) {
                opsRedissonLock.releaseLock(lockKey);
            }
        }
        return CommonResult.success(result);
    }

    /**
     * bugid:11445 c14717 20230721
     * 根据warehouseCode和doType（调拨类型） 获取多地址实体
     * @param warehouseAddress
     * @return
     */
    @PostMapping(value = "/getMultAdress")
    public CommonResult<List<OpsWarehouseAddress>> getMultAdress(@RequestBody OpsWarehouseAddress warehouseAddress) {
        List<OpsWarehouseAddress> wAdd =  warehouseService.getMultAdress(warehouseAddress);
        if(Objects.nonNull(wAdd)){
            return CommonResult.success(wAdd);
        }else {
            return CommonResult.success(new ArrayList<OpsWarehouseAddress>());
        }
    }

    /**
     * getMultAdressConfig
     * @param id
     * @return
     */

    /**
     * bugid:11445 c14717 20230721
     * 根据warehouseCode和doType（调拨类型） 获取多地址实体
     * @param
     * @return
     */
    @PostMapping(value = "/getMultAdressConfig")
    public CommonResult<List<String>> getMultAdressConfig(@RequestBody OpsWarehouseAddressConfig obj) {
        List<String> wAdd =  warehouseService.getMultAdressConfig(obj);
        if(Objects.nonNull(wAdd)){
            return CommonResult.success(wAdd);
        }else {
            return CommonResult.success(new ArrayList<String>());
        }
    }

    @PostMapping(value = "/getMultAdressById")
    public CommonResult<OpsWarehouseAddress> getMultAdress(@RequestBody Long id) {
        OpsWarehouseAddress wAdd =  warehouseService.getMultAdressById(id);
        if(Objects.nonNull(wAdd)){
            return CommonResult.success(wAdd);
        }else {
            return CommonResult.failure();
        }
    }

    @GetMapping(value = "/test")
    public CommonResult getTest() {
        List<OpsWarehouse> list = null;
        return list.isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(list);
    }

    @GetMapping(value = "/list")
    public CommonResult getAll() {
        List<OpsWarehouse> list = warehouseService.findAll();
        return list.isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(list);
    }

    @PostMapping(value = "/search")
    public CommonResult getByExample(@RequestBody PageModel<OpsWarehouse> pageModel) {
        PageInfo<OpsWarehouse> result = warehouseService.findByPage(pageModel);
        return result.getList().isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }


    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpsWarehouse warehouse) {
        Integer result = warehouseService.add(warehouse);
        return result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",obj:" + warehouse) :
                CommonResult.success("result:" + result + ",obj:" + warehouse);
    }

    @PostMapping(value = "update")
    public CommonResult update(@RequestBody OpsWarehouse record) {
        Integer result = warehouseService.modify(record);
        return result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",id:" + record.getWarehouseCode()) :
                CommonResult.success("result:" + result + ",id:" + record.getWarehouseCode());
    }


    @GetMapping(value = "delete/{code}")
    public CommonResult deleteByCode(@PathVariable String code) {
        List<String> list = new ArrayList<String>();
        list.add(code);
        Integer result = warehouseService.removeList(list);
        return result == null ||  result.equals(0)?
                CommonResult.failure("result:" + result + ",id:" + code) :
                CommonResult.success("result:" + result + ",id:" + code);
    }

    @PostMapping(value = "deletes")
    public CommonResult deleteByCodes(@RequestBody List<String> list) {
        Integer result = warehouseService.removeList(list);
        return result == null || result.equals(0)  ?
                CommonResult.failure("result:" + result + ",id:" + list) :
                CommonResult.success("result:" + result + ",id:" + list);
    }


    @GetMapping(value = "/types")
    public CommonResult getTypes() {
        WarehouseTypeEnum[] types = warehouseService.findTypes();
        return types.length == 0 ?
                CommonResult.success("没有记录") : CommonResult.success(types);
    }

    @GetMapping("/names")
    public CommonResult getNames(@RequestParam(value = "type", required = false) String type) {
        List<String> names = warehouseService.findNamesByType(type);
        return names.size() == 0 ?
                CommonResult.success("") : CommonResult.success(names);
    }
    @GetMapping("/codeAndName")
    public CommonResult getCodeAndNames(@RequestParam(value = "type", required = false) String type) {
        List<OpsWarehouse> list = warehouseService.findCodeAndNameByType(type);
        return list.size() == 0 ?
                CommonResult.success("") : CommonResult.success(list);
    }

}

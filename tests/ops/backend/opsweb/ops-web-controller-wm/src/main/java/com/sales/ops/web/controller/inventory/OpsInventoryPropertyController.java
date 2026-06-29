package com.sales.ops.web.controller.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author C12961
 * @date 2022/10/19 9:04
 */

@RestController
@RequestMapping("/inventory/property")
public class OpsInventoryPropertyController {

    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;

    @RequestMapping("/find")
    public CommonResult findProperty(@RequestBody OpsInventoryProperty property) {
        try {
            UserDto userDto = UserDto.AUTO;
            if (property.getCreator() != null) {
                userDto.setUserName(property.getCreator());
            }
            if (InventoryTypeEnum.QB.getType().equals(property.getInventoryTypeCode())) {
                return CommonResult.failure("无法查询营业情报");
            }
            Long id = opsInventoryPropertyService.findPropertyWithInsertByExample(property, userDto);
            return CommonResult.success(id);
        } catch (OpsException e) {
            e.printStackTrace();
            return CommonResult.failure(e.getMessage());
        }
    }


}

package com.sales.ops.web.controller.units;

import com.sales.ops.service.units.OpsUnitsTreeService;
import com.smc.base.TreeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/unitsTree")
public class OpsUnitsTreeController {

    @Autowired
    private OpsUnitsTreeService opsUnitsTreeService;

    @RequestMapping("/departInfo")
    public List<TreeInfo> findAfterFiltrationByBusinessOffice() {
         return opsUnitsTreeService.findAfterFiltrationByBusinessOffice();
    }
}

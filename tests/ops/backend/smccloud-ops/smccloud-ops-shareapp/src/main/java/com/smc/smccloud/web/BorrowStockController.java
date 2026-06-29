package com.smc.smccloud.web;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.borrowstock.*;
import com.smc.smccloud.service.BorrowStockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/shareapp/borrowstock")
@RestController
public class BorrowStockController {

    @Resource
    private BorrowStockService borrowStockService;

    @PostMapping("/listBrwStockInfo")
    public ResultVo<PageInfo<BrwMasterVO>> listBrwStockInfo(@RequestBody BrwMasterVO brwMasterVO, Page page) {

        return borrowStockService.listBrwStockInfo(brwMasterVO, page);

    }

    @PostMapping("/listNotReturn")
    public ResultVo<PageInfo<BrwMasterDTO>> listNotReturn(@RequestBody BrwStockRequest request, Page page) {

        return borrowStockService.listNotReturn(request, page);

    }


    @PostMapping("/listBrwDetail")
    public ResultVo<List<BrwDetailVO>> listBrwDetail(@RequestParam("id") Integer id) {

        return borrowStockService.listBrwDetail(id);

    }

    @PostMapping("/saveApply")
    public ResultVo<String> saveBorrowStockApply(@RequestBody BrwApplyDTO brwApplyDTO) {

        return borrowStockService.saveBrwStockApply(brwApplyDTO);
    }
}

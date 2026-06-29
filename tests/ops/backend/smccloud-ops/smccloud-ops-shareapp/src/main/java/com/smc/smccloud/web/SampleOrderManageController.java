package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.SampleOrderManageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/3/10 8:46
 * @Descripton TODO
 */
@RestController
@RequestMapping("/shareapp/sampleorder")
public class SampleOrderManageController {

    @Resource
    private SampleOrderManageService sampleOrderManageService;


    /**
     * 展览展示品管理界面-列表查询
     */
    @PostMapping("/listSampleOrderManage")
    public ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(@RequestBody SampleOrderManageQuery request){
        return sampleOrderManageService.listSampleOrderManage(request,request.getPage());
    }

    /**
     * 导出展览展示品数据
     */
    @PostMapping("/exportSampleOrderManageData")
    public void exportSampleOrderManageData(@RequestBody SampleOrderManageQuery request) {
        sampleOrderManageService.exportSampleOrderManage(request);
    }

    /**
     * 导出各所展示品盘点excel
     */
    @PostMapping("/exportZlzsManageData")
    public void exportZlzsManageData(@RequestBody SampleOrderManageQuery request) {
        sampleOrderManageService.exportZlzsManageData(request);
    }

    /**
     * 展示品盘点票发布
     */
    @PostMapping("/pushZlzsSampleOrderManageForPdf")
    public ResultVo<String> pushZlzsSampleOrderManageForPdf(@RequestBody SampleOrderManageQuery request) {
        return sampleOrderManageService.pushZlzsSampleOrderManageForPdf(request);
    }

    /**
     * 单次展示品销账
     */
    @PostMapping("/zlzsOrderWriteOff")
    public ResultVo<String> zlzsOrderWriteOff(@RequestBody SampleOrderManageVO dto) {
        return sampleOrderManageService.zlzsOrderWriteOff(dto);
    }
    /**
     * 批量展示品销账(通过excel导入)
     */
    @PostMapping("/batchImportWriteOffData")
    public ResultVo<String> batchImportWriteOffData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return sampleOrderManageService.batchImportWriteOffData(file,loginUser);
    }

    /**
     * 单次变更实物所在部门
     */

    @PostMapping("/upSampleOrderManageDeptNo")
    public ResultVo<String> upSampleOrderManageDeptNo(@RequestBody SampleOrderManageVO sampleOrderManageVO) {
        return sampleOrderManageService.upSampleOrderManageDeptNo(sampleOrderManageVO);
    }

    /**
     * 批量变更实物所在部门
     */
    @PostMapping("/batchUpSampleOrderManageDeptNo")
    public ResultVo<String> batchUpSampleOrderManageDeptNo(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return sampleOrderManageService.batchUpSampleOrderManageDeptNo(file,loginUser);
    }

    /**
     * 批量导入历史盘点票数据
     */
    @PostMapping("/batchImportSampleOrderManageData")
    public ResultVo<String> batchImportSampleOrderManageData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return sampleOrderManageService.batchImportSampleOrderManageData(file, loginUser);
    }

    /**
     * 单次导入盘点票数据
     */
    @PostMapping("/importSampleOrderManageData")
    public ResultVo<String> importSampleOrderManageData(@RequestBody SampleOrderManageDO sampleOrderManageDO) {
        return sampleOrderManageService.importSampleOrderManageData(sampleOrderManageDO);
    }

    /**
     * 单次编辑盘点票
     */
    @PostMapping("/editSampleOrderManage")
    public ResultVo<String> editSampleOrderManage(@RequestBody SampleOrderManageDO sampleOrderManageDO) {
        return sampleOrderManageService.editSampleOrderManage(sampleOrderManageDO);
    }

    /**
     * 下载历史盘点票导入模板
     */
    @GetMapping("/downLoadHistorySampleOrderManageExcel")
    public void downLoadHistorySampleOrderManageExcel() {
        sampleOrderManageService.downLoadHistorySampleOrderManageExcel();;
    }


}

package com.smc.smccloud;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.service.AdapterService;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.DepartmentService;
import com.smc.smccloud.service.DictDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonServiceWithGzTest {

    @Resource
    private CommonService commonService;
    @Resource
    private AdapterService adapterService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private DepartmentService departmentService;

    @Test
    public void generaterBillNoTest() {
        ResultVo<String> result = commonService.generatorBillNo("32");
        log.info("{}", result);
    }

    @Test
    public void getDataCodesTreeTest() {
//        String classCode = "9004";
//        ResultVo<List<DataCodeVO>> treeResult = dictDataService.getDataCodesTree(classCode);
//        log.info("treeResult = {}", treeResult);
        ResultVo<Integer> departmentDlvDayByDeptNo = departmentService.getDepartmentDlvDayByDeptNo("244321");

    }

    @Test
    public void importDataCodeTest() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File("D:\\SMC-Work\\OPS项目\\smccode 对应仓库.xlsx"));
        ExcelHelper excelHelper = new ExcelHelper(inputStream);
        excelHelper.openSheet(2);

        int row = 1;
        int colIndex;
        String type;
        String remark;
        String warehouse;
        String supplier;
        String smcCode;
        DataTypeDO info;
        Set<String> codeSet = new HashSet<>(128);

        for (int i = 0; i < 70; i++) {
            colIndex = 1;
            type = excelHelper.getCellString(excelHelper.getCell(row, colIndex++)).trim();
            remark = excelHelper.getCellString(excelHelper.getCell(row, colIndex++)).trim();
            warehouse = excelHelper.getCellString(excelHelper.getCell(row, colIndex++)).trim();
            supplier = excelHelper.getCellString(excelHelper.getCell(row, colIndex++)).trim();
            smcCode = excelHelper.getCellString(excelHelper.getCell(row, colIndex)).trim();

            System.out.println("==> row = " + row + ", " + type + "  ---  " + remark + "  ---  " + warehouse + "  ---  " + supplier + "  ---  " + smcCode);
            if ("无".equals(smcCode) || codeSet.contains(smcCode)) {
                row++;
                continue;
            }

            info = new DataTypeDO();
            info.setClassCode("2048");
            info.setCode(smcCode);
            info.setCodeName(type + " " + remark);
            if (warehouse.contains("北京物流中心")) {
                info.setExtNote1("KBJ");
            }
            if (warehouse.contains("上海物流中心")) {
                info.setExtNote1("KSH");
            }
            if (warehouse.contains("广州物流中心")) {
                info.setExtNote1("KGZ");
            }
            if (warehouse.contains("常州物流中心")) {
                info.setExtNote1("SCZ");
            }
            //info.setExtNote2(supplier);
            info.setStatus(1);
            dictDataService.saveDict(info);
            codeSet.add(info.getCode());
            row++;
        }
        System.out.println("smcCode size = " + codeSet.size());
    }
}

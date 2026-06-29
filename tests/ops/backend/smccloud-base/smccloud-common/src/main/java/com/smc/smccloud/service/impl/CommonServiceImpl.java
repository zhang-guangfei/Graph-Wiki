package com.smc.smccloud.service.impl;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private DictDataService dictDataService;

    @Override
    public ResultVo<String> generatorBillNo(String billType) {
        String classCode = "9001";
        DataTypeVO codeInfo = dictDataService.getDataCodesInfo(classCode, billType);
        if (codeInfo ==null) {
            return ResultVo.failure("单据类型不存在" + billType);
        }
        // 单号前缀
        String prefixNo = codeInfo.getExtNote1();
        // 单号长度
        String lenth = codeInfo.getExtNote3();
        String serialNo;
        String toUpdExtNo2;
        int index = prefixNo.indexOf("{");

        //带自定义格式
        if(index!=-1) {
            String format = prefixNo.substring(index + 1, prefixNo.indexOf("}", index));
            String datePrefixNo = DateUtil.getFormatDate(new Date(), format);
            //prefixNo = prefixNo.replace("{" + format + "}", datePrefixNo);
            String[] arrs = codeInfo.getExtNote2().split("-", 2);
            if (arrs.length == 2)
            {
                //不同前缀时从1开始
                if(arrs[0].equalsIgnoreCase(datePrefixNo))
                {
                     serialNo = String.format("%0"+lenth+"d",Integer.parseInt(arrs[1])+1);
                }
                else {
                    serialNo = String.format("%0"+lenth+"d",1);
                }
            }
            else
            {
                serialNo = String.format("%0"+lenth+"d",1);
            }
            toUpdExtNo2 = datePrefixNo+  "-" + serialNo;
            prefixNo = prefixNo.replace("{" + format + "}", datePrefixNo);;
        }
        else
        {
             serialNo = String.format("%0"+lenth+"d",Integer.parseInt(codeInfo.getExtNote2())+1);
             toUpdExtNo2 = Integer.parseInt(serialNo)+"";
        }
        ResultVo<Boolean> result = dictDataService.updateExtNote2(codeInfo.getId(), toUpdExtNo2,codeInfo.getExtNote2());
        if (!result.isSuccess()) {
            return  ResultVo.failure(null, "生成单据号失败");
        }
        return  ResultVo.success(prefixNo+serialNo);

    }

    @Override
    public ResultVo<Set<String>> batchGeneratorBillNo(String billType,Integer number) {
        String classCode = "9001";
        DataTypeVO codeInfo = dictDataService.getDataCodesInfo(classCode, billType);
        if (codeInfo ==null) {
            return ResultVo.failure("单据类型不存在" + billType);
        }
        String prefixNo = codeInfo.getExtNote1();
        int index = prefixNo.indexOf("{");
        if(index!=-1)
        {
            String format = prefixNo.substring(index+1, prefixNo.indexOf("}", index));
            String str = DateUtil.getFormatDate(new Date(), format);
            prefixNo= prefixNo.replace("{" + format+"}", str);
        }
        String lenth = codeInfo.getExtNote3();

        Set<String> set = new HashSet<>();
        String serialNo = "";

        for (int i = 0; i < number; i++) {
            serialNo = String.format("%0"+lenth+"d",Integer.parseInt(codeInfo.getExtNote2())+(i+1));
            set.add(prefixNo+serialNo);
        }
        String toUpdExtNo2 = Integer.parseInt(serialNo)+"";
        ResultVo<Boolean> result = dictDataService.updateExtNote2(codeInfo.getId(), toUpdExtNo2,codeInfo.getExtNote2());
        if (!result.isSuccess()) {
            return  ResultVo.failure(null, "生成单据号失败");
        }
        return ResultVo.success(set);
    }

}

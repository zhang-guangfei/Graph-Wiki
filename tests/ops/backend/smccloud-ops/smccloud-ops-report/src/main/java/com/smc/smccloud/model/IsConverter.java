package com.smc.smccloud.model;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class IsConverter implements Converter<Boolean> {

    //导出数据到excel
    @Override
    public WriteCellData<Boolean> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(value ? "是" : "否");
    }

}
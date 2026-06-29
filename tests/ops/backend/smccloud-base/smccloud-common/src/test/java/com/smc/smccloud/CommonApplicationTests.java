package com.smc.smccloud;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeRequest;
import com.smc.smccloud.service.DictDataService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonApplicationTests {
    @Resource
    private DictDataService dictDataService;

    @Test
    public void contextLoads() {
        DataTypeDO dataTypeDO=new DataTypeDO();
        dataTypeDO.setCode("555");
        dataTypeDO.setRemark("aaa");
        ResultVo<String> stringResultVo = dictDataService.saveDict(dataTypeDO);
        log.info("stringResultVo = " + stringResultVo);
        log.info("stringResultVo.getMessage() = " + stringResultVo.getMessage());
    }

    @Test
    public void testFind(){

        ////List<RegionBeanTree> treeList1 = dictDataService.queryAll();

       // List<RegionBeanTree> nodes = TreeUtils.buildTree(treeList1,"0");
       // JSONArray jsonArray3 =  JSONArray.fromObject(nodes);
       // String treeListStr = jsonArray3.toString();
        // log.info("strJson3 = " + treeListStr);
    }

    @Test
    public void getDataTypes(){

        ResultVo<List<DataCodeVO>> result =  dictDataService.getDataCodes("0");
        JSONArray jsonArray3 =  JSONArray.fromObject(result.getData());
        String sss = jsonArray3.toString();
        log.info("sss = " + sss);

    }

    @Test
    public void getDataTypeTree() {
        ResultVo<List<DataCodeVO>> result  = dictDataService.getDataCodesTree("1013");

        log.info(result.toString());
    }

    @Test
    public void queryList()
    {
        DataTypeRequest query= new DataTypeRequest();
        query.setClassCode("0");
        Page page = new Page();
        page.setPageSize(10);
        page.setPageNumber(1);

       ResultVo<PageInfo<DataTypeDO>> list =   dictDataService.queryByClassCode(query, page);

        log.info(list.toString());
    }

}

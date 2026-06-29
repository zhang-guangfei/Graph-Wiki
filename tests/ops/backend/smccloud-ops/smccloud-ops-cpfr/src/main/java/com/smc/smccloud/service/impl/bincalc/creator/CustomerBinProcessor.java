package com.smc.smccloud.service.impl.bincalc.creator;

import com.smc.smccloud.mapper.BindataRepository;
import com.smc.smccloud.mapper.binorder.BinorderDetailRepository;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import com.smc.smccloud.service.impl.NewBinOrderCalcServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerBinProcessor {
    @Resource
    private BinorderDetailRepository binorderDetailRepository;
    @Resource
    private BindataRepository bindataRepository;

    public void processCustomerBin(Long calcId, Integer calcType, String warehouseCode) {
        // 客户 BIN 的 detail 生成与频率回填入口；当前主要复用 Task 4 的 IncludeNotBIN 批量索引能力。
        // 1. 插入客户bin数据，如果已有预设数据，先排除预设型号，再插入客户bin数据
        insertCustomerBindetails(calcId, calcType);
        // 2. 更新频率和月用量
        updateFreqForCustomerBinOrderDetail(calcId, calcType);
    }


    private void insertCustomerBindetails(Long calcId, Integer calcType) {
        //查询当前detail表的型号
        List<BinOrderDetailDO> customerBinOrderDetailList = binorderDetailRepository.findBinOrderDetailByCalcType(calcId, calcType);
        List<String> modelnoList = customerBinOrderDetailList.stream()
                .map(BinOrderDetailDO::getModelNo).collect(Collectors.toList());
        //查询出客户bindata
        List<BindataDO> bindataList = bindataRepository.findCustomerBindata();
        //过滤掉当前detail表的型号
        bindataList = bindataList.stream().filter(bindataDO -> !modelnoList.contains(bindataDO.getModelNo())).collect(Collectors.toList());
        //将bindata转换为detail,然后插入
        List<BinOrderDetailDO> binOrderDetailDOList = NewBinOrderCalcServiceImpl.convertToBinOrderDetailList(calcId, bindataList);
        for (BinOrderDetailDO binOrderDetailDO : binOrderDetailDOList) {
            binorderDetailRepository.insert(binOrderDetailDO);
        }
    }


    private void updateFreqForCustomerBinOrderDetail(Long calcId, Integer calcType) {
        List<BinOrderDetailDO> list = binorderDetailRepository.findBinOrderDetailByCalcType(calcId, calcType);
        // Task 4：客户 BIN 更新频率分支复用 IncludeNotBIN 批量索引，避免逐条查 bindata。
        // 外层 key=warehouseCode，内层 key=modelNo，value=IncludeNotBIN 语义下选中的 BindataDO。
        Map<String, Map<String, BindataDO>> bindataIncludeNotBinIndex =
                BindataBatchLoader.load(bindataRepository, calcType, list);
        for (BinOrderDetailDO detailDO : list) {
            BindataDO bindata = BindataBatchLoader.get(bindataIncludeNotBinIndex, detailDO);
            if (bindata != null) {
                //更新频率和月用量
                BinOrderDetailDO update = new BinOrderDetailDO();
                update.setId(detailDO.getId());
                update.setFreq(bindata.getFreq());
                update.setMean(bindata.getMean().doubleValue());
                binorderDetailRepository.updateById(update);
            }
        }
    }

}

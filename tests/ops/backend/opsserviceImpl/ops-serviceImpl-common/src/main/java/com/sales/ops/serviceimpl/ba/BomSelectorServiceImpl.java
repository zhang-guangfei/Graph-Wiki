package com.sales.ops.serviceimpl.ba;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.db.extdao.OPSProductDao;
import com.sales.ops.db.extdao.OpsSpecialBomDao;
import com.sales.ops.dto.common.*;
import com.sales.ops.dto.product.BomDto;
import com.sales.ops.enums.common.BomSelectEnum;
import com.sales.ops.service.ba.BomSelectorService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：C14717
 * @version: $ bugid:17799 bom选择器 包含自定义bom和常规bom
 * @description：
 * @date ：Created in 2025/6/13 9:20
 */
@Service
public class BomSelectorServiceImpl implements BomSelectorService {

    @Autowired
    private OPSProductDao productDao;
    @Autowired
    private OpsSpecialBomDao opsSpecialBomDao;

    //0.外部调用获取bom
    @Override
    public ResultVo<BomSelectResult> selectBom(BomSelectParam param) throws OpsException {
        // 1.1 获取自定义bom信息
        BomSelectResult bom = getSpecialBom(param.getModelNo(), param.getCustomerCode());
        if(Objects.isNull(bom)){
            //1.2 获取普通bom信息
            bom = getBomV1(param.getModelNo());
        }
        //2.验证bom
        BomSelectEnum bomSelectEnum = checkResult(bom);
        return new ResultVo<>(bomSelectEnum.getSuccess(), bomSelectEnum.getCode(), bomSelectEnum.getDesc(), bom);
    }

    // bom验证
    public BomSelectEnum checkResult(BomSelectResult result) throws OpsException{
        if(Objects.isNull(result)){
            // 1.未匹配到任何bom
            return BomSelectEnum.NONE;
        }
        for(BomVersions bomVersions : result.getBomVersionsList()) {
            if(bomVersions.getBomType()){//拆分
                if(CollectionUtils.isEmpty(bomVersions.getBomDetails())){
                    // 2.bom子项缺失
                    return BomSelectEnum.NONE_DETAIL_ERROR;
                }
                if(bomVersions.getBomDetails().size() == 1){
                    // 3.bom子项仅1条非法
                    return BomSelectEnum.ONE_DETAIL_ERROR;
                }
                for(ProductBomDetail detail : bomVersions.getBomDetails()){
                    if(Objects.isNull(detail.getQuantity()) || detail.getQuantity() <=0 ){
                        // 4.bom子项数量非法
                        return BomSelectEnum.QTY_ERROR;
                    }
                }
            }
        }
        return BomSelectEnum.SUCCESS;

    }

    //1.获取自定义bom
    public BomSelectResult getSpecialBom(String modelNo, String customerNo){
        // 1.查询自定义bom
        OpsSpecialBomDto sBom = opsSpecialBomDao.selectOpsSpecialBomDto(modelNo, customerNo);
        if(Objects.isNull(sBom)){
            return null;
        }
        // 2.初始化返回结果，特殊bom置true
        BomSelectResult result = new BomSelectResult();
        result.setSpecialBom(true);
        // 3.初始化bom版本列表
        List<BomVersions> list = new ArrayList<>();
        // 4.初始化特殊bom实体
        List<OpsSpecialBomDetailDto> sBomDetailDtos = null;
        // 5.判断是否是特殊bom拆分
        if(sBom.getBomType()){
            // 6.查询特殊bomDetail实体
            sBomDetailDtos = opsSpecialBomDao.selectOpsSpecialBomDetailDto(sBom.getBomId());
        }
        // 7.封装特殊bom实体为正常bom实体，并且封装为bomVersion
        BomVersions bomVersions = initSpecialBomVersions(sBom, sBomDetailDtos,sBom.getBomType());
        // 8.添加bom版本
        list.add(bomVersions);
        // 9.拆分版本保存到返回结果
        result.setBomVersionsList(list);
        return result;
    }


    /**
     * 获取常规bom
     * bom联查
     * @param modelNo
     * @return
     */
    public BomSelectResult getBomV1(String modelNo){
        // 1.常规获取bom
        List<BomDto> productBoms = productDao.queryBomAndDetailByModelNo(modelNo);
        if(CollectionUtils.isEmpty(productBoms)){
            return null;
        }
        //2. 初始化返回结果，特殊bom置false
        BomSelectResult result = new BomSelectResult();
        result.setSpecialBom(false);
        // 3.初始化bom版本列表
        List<BomVersions> list = new ArrayList<>();
        HashMap<Long, ProductBom> bomMap = new HashMap<>();
        HashMap<Long, List<ProductBomDetail>> bomDetailMap = new HashMap<>();
        for(BomDto obj : productBoms){
            if(!bomMap.containsKey(obj.getBomid())){
                ProductBom bom = new ProductBom();
                bom.setBomid(obj.getBomid());
                bom.setModelno(obj.getModelno());
                bom.setPriorityComplete(obj.getPriorityComplete());
                bom.setPriorityLevel(obj.getPriorityLevel());
                bom.setIsvalid(obj.getIsvalid());
                bomMap.put(bom.getBomid(), bom);
            }
            ProductBomDetail detail = new ProductBomDetail();
            detail.setBomid(obj.getBomid());
            detail.setModelno(obj.getSubModelNo());
            detail.setQuantity(obj.getSubQty());
            detail.setClassify(obj.getSubClassify());
            if(!bomDetailMap.containsKey(obj.getBomid())){
                List<ProductBomDetail> bomDetails = new ArrayList<>();
                bomDetails.add(detail);
                bomDetailMap.put(obj.getBomid(), bomDetails);
            }else {
                bomDetailMap.get(obj.getBomid()).add(detail);
            }
        }
        // 5.封装为bomVersion
        for(Long key : bomMap.keySet()){
            ProductBom bom = bomMap.get(key);
            List<ProductBomDetail> productBomDetails = bomDetailMap.get(key);
            // 整形号采购标识
            boolean bomTypeFlag = !bom.getPriorityComplete();
            BomVersions bomVersions = initBomVersions(bom, productBomDetails,bomTypeFlag);
            // 6.添加bom版本
            list.add(bomVersions);
        }
        // 排序
        list.sort(Comparator.comparing(
                o -> o.getProductBom() != null ? o.getProductBom().getPriorityLevel() : null,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
        // 7.拆分版本保存到返回结果
        result.setBomVersionsList(list);
        return result;
    }

    //3.获取常规bom
    public BomSelectResult getBom(String modelNo){
        // 1.常规获取bom
        List<ProductBom> productBoms = productDao.queryProductBomByModelNo(modelNo);
        if(CollectionUtils.isEmpty(productBoms)){
            return null;
        }
        //2. 初始化返回结果，特殊bom置false
        BomSelectResult result = new BomSelectResult();
        result.setSpecialBom(false);
        // 3.初始化bom版本列表
        List<BomVersions> list = new ArrayList<>();
        for(ProductBom bom : productBoms){
            // 4.获取常规bomdetai
            List<ProductBomDetail> productBomDetails = productDao.queryProductBomDetailByModelNo(bom.getBomid());
            boolean bomTypeFlag = true;
            if (bom.getPriorityComplete()) {
                bomTypeFlag = false;
            }
            // 5.封装为bomVersion
            BomVersions bomVersions = initBomVersions(bom, productBomDetails,bomTypeFlag);
            // 6.添加bom版本
            list.add(bomVersions);
        }
        // 7.拆分版本保存到返回结果
        result.setBomVersionsList(list);
        return result;
    }

    //2.封装自定义bom转化为bom实体
    public BomVersions initSpecialBomVersions(OpsSpecialBomDto sBom, List<OpsSpecialBomDetailDto> opsSpecialBomDetailDtos,Boolean bomType){
        // 1.初始化bom,自定义bom封装为常规bom
        ProductBom bom = initBom(sBom);
        List<ProductBomDetail> bomDetails = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(opsSpecialBomDetailDtos)){
            for(OpsSpecialBomDetailDto sDetail : opsSpecialBomDetailDtos){
                // 2.初始化bomdetail,自定义bomDetail 封装为常规bomdetail
                bomDetails.add(initBomDetail(sDetail));
            }
        }
        //3.封装bom版本列表
        return initBomVersions(bom, bomDetails,bomType);
    }
    // 自定义bom实体转化bom实体
    public ProductBom initBom(OpsSpecialBomDto sBom){
        ProductBom bom  = new ProductBom();
        bom.setBomid(sBom.getBomId());
        bom.setModelno(sBom.getModelNo());
        bom.setPriorityComplete(false);
        return bom;
    }

    // 自定义bomDetail实体转化为bomDetail实体
    public ProductBomDetail initBomDetail(OpsSpecialBomDetailDto sDetail){
        ProductBomDetail bomDetail = new ProductBomDetail();
        bomDetail.setBomid(sDetail.getBomId());
        bomDetail.setQuantity(sDetail.getQuantity());
        bomDetail.setModelno(sDetail.getSubModelNo());
        return bomDetail;
    }


    //4.封装返回参数
    public BomVersions initBomVersions(ProductBom productBom,List<ProductBomDetail> bomDetails,Boolean bomType){
        BomVersions bomVersions = new BomVersions();
        bomVersions.setBomType(bomType);
        bomVersions.setProductBom(productBom);
        bomVersions.setBomDetails(bomDetails);
        return bomVersions;
    }
}
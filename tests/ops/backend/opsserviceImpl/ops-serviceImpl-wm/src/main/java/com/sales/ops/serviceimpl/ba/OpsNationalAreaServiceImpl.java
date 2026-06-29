package com.sales.ops.serviceimpl.ba;

import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.OpsNationalAreaMapper;
import com.sales.ops.db.entity.OpsNationalArea;
import com.sales.ops.db.entity.OpsNationalAreaExample;
import com.sales.ops.dto.units.ElementUITree;
import com.sales.ops.service.ba.OpsNationalAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author C12961
 * @date 2022/6/7 15:08
 */
@Service
public class OpsNationalAreaServiceImpl implements OpsNationalAreaService {

    @Resource
    private OpsNationalAreaMapper opsNationalAreaMapper;
    @Resource
    private OPSRedisUtils opsRedisUtils;

    @Override
    public List<OpsNationalArea> selectRootArea() {
        OpsNationalAreaExample ex = new OpsNationalAreaExample();
        ex.createCriteria().andParentcodeIsNull();
        List<OpsNationalArea> provinces = opsNationalAreaMapper.selectByExample(ex);
        return provinces;
    }

    @Override
    public List<OpsNationalArea> selectByParentCode(String parentCode) {
        OpsNationalAreaExample ex = new OpsNationalAreaExample();
        ex.createCriteria().andParentcodeEqualTo(parentCode);
        List<OpsNationalArea> areas = opsNationalAreaMapper.selectByExample(ex);
        return areas;
    }

    @Override
    public Object getAreaTree() {
        Object obj = opsRedisUtils.get("ops:areas");
        if (obj == null) {
            createAreaTree();
            obj = opsRedisUtils.get("ops:areas");
        }
        return obj;
    }

    //省级名单
    @Override
    public List<ElementUITree> createAreaTree() {
        List<ElementUITree> tree = new ArrayList<>();
        //根目录
        List<OpsNationalArea> roots = selectRootArea();
        for (OpsNationalArea root : roots) {
            ElementUITree areaTree = new ElementUITree();
            //如果是省
            if ("0".equals(root.getType())) {
                areaTree.setLabel(root.getName());
                areaTree.setValue(root.getCode());
                //获得市列表
                areaTree.setChildren(getCityTreeByParentCode(root.getCode()));
            } //如果是直辖市
            else if ("1".equals(root.getType())) {
                areaTree.setLabel(root.getName());
                areaTree.setValue(root.getCode());
                //以当前直辖市作为市列表
                ElementUITree city = new ElementUITree();
                city.setLabel(root.getName());
                city.setValue(root.getCode());
                //获得区列表
                city.setChildren(getDistrictTreeByParentCode(root.getCode()));
                areaTree.setChildren(Collections.singletonList(city));
            }
            tree.add(areaTree);
        }
        opsRedisUtils.set("ops:areas", tree);
        return tree;
    }

    //获取市列表
    public List<ElementUITree> getCityTreeByParentCode(String parentCode) {
        List<ElementUITree> tree = new ArrayList<>();
        List<OpsNationalArea> citys = selectByParentCode(parentCode);
        for (OpsNationalArea city : citys) {
            ElementUITree cityTree = new ElementUITree();
            cityTree.setLabel(city.getName());
            cityTree.setValue(city.getCode());
            //获得区列表
            List<ElementUITree> DistrictList = getDistrictTreeByParentCode(city.getCode());
            //如果区列表为空，则以当前市作为区列表
            if (DistrictList == null) {
                ElementUITree districtTree = new ElementUITree();
                districtTree.setLabel(city.getName());
                districtTree.setValue(city.getCode());
                DistrictList = Collections.singletonList(districtTree);
            }
            cityTree.setChildren(DistrictList);
            tree.add(cityTree);
        }
        return tree.size() == 0 ? null : tree;
    }

    //获取区县列表
    public List<ElementUITree> getDistrictTreeByParentCode(String parentCode) {
        List<ElementUITree> tree = new ArrayList<>();
        List<OpsNationalArea> districts = selectByParentCode(parentCode);
        for (OpsNationalArea district : districts) {
            if ("3".equals(district.getType())) {
                ElementUITree districtTree = new ElementUITree();
                districtTree.setLabel(district.getName());
                districtTree.setValue(district.getCode());
                tree.add(districtTree);
            }
        }
        return tree.size() == 0 ? null : tree;
    }

}

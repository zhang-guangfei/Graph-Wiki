package com.sales.ops.serviceimpl.units;

import com.sales.ops.db.extdao.OpsUnitsTreeDao;
import com.sales.ops.dto.units.OpsUnitsTree;
import com.sales.ops.service.units.OpsUnitsTreeService;
import com.smc.base.TreeInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OpsUnitsTreeServiceImpl implements OpsUnitsTreeService {

    private static final String SUB_NAME = "驻在";
    private static final String LEVEL_NAME = "课内机构(驻在所)";

    @Autowired
    private OpsUnitsTreeDao opsUnitsTreeDao;

    @Override
    public List<TreeInfo> findAfterFiltrationDeptInfo() {
        // 查询3个分公司下的全部部门，需过滤HL级别
        List<OpsUnitsTree> departInfo = opsUnitsTreeDao.findAfterFiltrationDeptInfo();
        List<TreeInfo> listTree = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(departInfo)) {
            for (OpsUnitsTree depart : departInfo) {
                TreeInfo treeInfo = new TreeInfo();
                treeInfo.setId(depart.getId());
                String[] deptCodes = depart.getUnitCode().split("!");
                treeInfo.setPid(deptCodes.length == 1 ? null : deptCodes[deptCodes.length - 2]);
                treeInfo.setName(depart.getName());
                treeInfo.setLevel(depart.getLevel());
                treeInfo.setCode(depart.getId());
                treeInfo.setK3Code(depart.getUnitCode());
                treeInfo.setDescription(depart.getFullName());
                listTree.add(treeInfo);
            }
        }
        return listTree;
    }

    @Override
    // @Cacheable(value = "AfterFiltration", key = "businessOffice_filtration", cacheManager = "cacheManager")
    public List<TreeInfo> findAfterFiltrationByBusinessOffice() {
        // 查询3个分公司、行业开发部下所有的部门，需过滤HL级别
        List<OpsUnitsTree> departInfo = opsUnitsTreeDao.findAfterFiltrationDeptInfo();
        List<TreeInfo> listTree = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(departInfo)) {
            for (OpsUnitsTree depart : departInfo) {
                TreeInfo treeInfo = new TreeInfo();
                treeInfo.setId(depart.getId());
                String subName = depart.getName().substring(depart.getName().length() - 2);
                // 特殊处理驻在所设置与营业所同一级别
                String[] deptCodes;
                if (subName.equals(SUB_NAME) && depart.getLevel().equals(LEVEL_NAME)) {
                    deptCodes = this.getAfterSpecialTreatmentByCodes(depart.getUnitCode());
                } else {
                    deptCodes = depart.getUnitCode().split("!");
                }
                treeInfo.setPid(deptCodes.length == 1 ? null : deptCodes[deptCodes.length - 2]);
                treeInfo.setName(depart.getName());
                treeInfo.setLevel(depart.getLevel());
                treeInfo.setCode(depart.getId());
                treeInfo.setK3Code(depart.getUnitCode());
                treeInfo.setDescription(depart.getFullName());
                listTree.add(treeInfo);
            }
        }
        return listTree;
    }

    /**
     * 将驻在所设置与营业所同一级别
     * @param unitCode 部门代码
     * @return String[]
     */
    private String[] getAfterSpecialTreatmentByCodes(String unitCode) {
            if (StringUtils.isBlank(unitCode)) {
                return unitCode.split("!");
            }
            String[] splits = unitCode.split("!");
            List<String> codes = new ArrayList<>(Arrays.asList(splits));
            codes.remove(splits.length - 2);
            return codes.toArray(new String[codes.size()]);
    }
}

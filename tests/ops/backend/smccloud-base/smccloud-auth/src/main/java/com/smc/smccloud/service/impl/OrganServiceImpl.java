package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.DictionaryMapper;
import com.smc.smccloud.mapper.SaleEmployeePositionMapper;
import com.smc.smccloud.mapper.SaleOrganizationMapper;
import com.smc.smccloud.model.Constant;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.Organ.DeptDictDTO;
import com.smc.smccloud.model.Organ.DeptTreeUtil;
import com.smc.smccloud.model.Organ.OpsUnitsTree;
import com.smc.smccloud.model.authority.OrgPositionCondition;
import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.SaleOrganization;
import com.smc.smccloud.model.authority.TreeInfo;
import com.smc.smccloud.service.DataTypeService;
import com.smc.smccloud.service.employeeAndOrgan.OrganService;
import com.smc.smccloud.service.employeeAndOrgan.SaleOrganizationService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganServiceImpl implements OrganService
{
    @Resource
    private SaleOrganizationService saleOrganizationService;

    @Resource
    private SaleOrganizationMapper saleOrganizationMapper;

    @Resource
    private DataTypeService dataTypeService;

    @Resource
    private RedisManager redisManager;

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Resource
    private SaleEmployeePositionMapper saleEmployeePositionMapper;

    private static final String SUB_NAME = "驻在";
    private static final String LEVEL_NAME = "课内机构(驻在所)";


    @Override
    public List<TreeInfo> findAllOrganizations() {
        List<SaleOrganization> organizations = saleOrganizationService.findAllOrganizations();
//        log.info("organizations.toString() = " + organizations.toString());
        if (CollectionUtils.isEmpty(organizations)) {
            return null;
        }
        List<TreeInfo> treeInfos = new ArrayList<TreeInfo>();
        for (int i = 0; i <organizations.size() ; i++) {
            // 下面数据转换不能删 否则报错>> java.util.LinkedHashMap cannot be cast to com.smc.smccloud.model.authority.SaleOrganization
            JSONObject jsonObject= JSONObject.fromObject(organizations.get(i)); // 将数据转成json字符串
            SaleOrganization saleOrganization = (SaleOrganization)JSONObject.toBean(jsonObject, SaleOrganization.class); //将json转成对象
            TreeInfo treeInfo = new TreeInfo();
            treeInfo.setId(saleOrganization.getId());
            treeInfo.setPid(saleOrganization.getPid());
            treeInfo.setName(saleOrganization.getName());
            treeInfo.setLevel(saleOrganization.getLevel());
            treeInfo.setDescription(saleOrganization.getFullName());
            treeInfos.add(treeInfo);
        }
        return treeInfos;
    }

    @Override
    public List<DeptTreeNode> findAllDeptToTree() {
        List<SaleOrganization> organizations = saleOrganizationService.findAllOrganizations();
        if (CollectionUtils.isEmpty(organizations)) {
            return null;
        }
        // 数据去重
        List<SaleOrganization> dataList = dataDuplicate(organizations);
        if (PublicUtil.isEmpty(dataList))
        {
            return null;
        }

        List<DeptTreeNode> treeInfos = new ArrayList<DeptTreeNode>();
        for (int i = 0; i <dataList.size() ; i++) {
            // 下面数据转换不能删 否则报错>> java.util.LinkedHashMap cannot be cast to com.smc.smccloud.model.authority.SaleOrganization
            JSONObject jsonObject= JSONObject.fromObject(dataList.get(i)); // 将数据转成json字符串
            SaleOrganization saleOrganization = (SaleOrganization)JSONObject.toBean(jsonObject, SaleOrganization.class); //将json转成对象
            DeptTreeNode treeInfo = new DeptTreeNode();
            treeInfo.setId(saleOrganization.getId());
            treeInfo.setPid(saleOrganization.getPid());
            treeInfo.setName(saleOrganization.getName());
            treeInfos.add(treeInfo);
        }
        return DeptTreeUtil.listToTree(treeInfos);
    }

    @Override
    public List<DeptTreeNode> findDeptByParentIdsToTree(String ids) {
        String[] split = ids.split("-");
        List<String> idList = Arrays.asList(split);
        QueryWrapper<SaleOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ParentId",idList);
        List<SaleOrganization> saleOrganizations = saleOrganizationMapper.selectList(queryWrapper);
        if (PublicUtil.isEmpty(saleOrganizations)) {
            return null;
        }
        // 数据去重
        List<SaleOrganization> dataDuplicate = dataDuplicate(saleOrganizations);
        List<DeptTreeNode> treeInfos = new ArrayList<DeptTreeNode>();
        if (PublicUtil.isEmpty(dataDuplicate)) {
            return null;
        }
        for (SaleOrganization organization : dataDuplicate) {
            // 下面数据转换不能删 否则报错>> java.util.LinkedHashMap cannot be cast to com.smc.smccloud.model.authority.SaleOrganization
            JSONObject jsonObject = JSONObject.fromObject(organization); // 将数据转成json字符串
            SaleOrganization saleOrganization = (SaleOrganization) JSONObject.toBean(jsonObject, SaleOrganization.class); //将json转成对象
            DeptTreeNode treeInfo = new DeptTreeNode();
            treeInfo.setId(saleOrganization.getId());
            treeInfo.setPid(saleOrganization.getPid());
            treeInfo.setName(saleOrganization.getName());
            treeInfos.add(treeInfo);
        }
        return DeptTreeUtil.listToTree(treeInfos);
    }


    /**
     * list数据去重
     * @param list
     * @return
     */
    public static List<SaleOrganization> dataDuplicate(List<SaleOrganization> list){
        List<String> idList = new ArrayList<>(); //用来临时存储id

        List<SaleOrganization> dataList = list.stream().filter(// 过滤去重
                v -> {
                    boolean flag = !idList.contains(v.getId());
                    idList.add(v.getId());
                    return flag;
                }
        ).collect(Collectors.toList());
        if (PublicUtil.isEmpty(dataList)) {
            return null;
        }
        return dataList;
    }


    @Override
    public List<SaleOrgPositionBean> queryOrgPosition(OrgPositionCondition condition) {
        List<SaleOrgPositionBean> saleOrgPositionBeans = new ArrayList<>();
        // 当部门id为空
        if (PublicUtil.isEmpty(condition.getUnitId())) {
            // 精确查询
            if (condition.isAccurate()) {
              // 岗位名称不为空时
              if (PublicUtil.isNotEmpty(condition.getPositionName())) {
                  /**
                   * 根据岗位名称精确查询
                   */
                  saleOrgPositionBeans = saleOrganizationService.findByUnitIdAndPositionName(null, condition.getPositionName());
              }
            }else {
                if (PublicUtil.isNotEmpty(condition.getPositionName())) {
                    /**
                     * 根据岗位名称模糊查询
                     */
                    saleOrgPositionBeans = saleOrganizationService.findByUnitIdAndPositionNameLike(null, condition.getPositionName());
                }
            }
        }
        // 当岗位名称为空
        if (PublicUtil.isEmpty(condition.getPositionName())) {
            /**
             * 根据部门Id查询
             */
            saleOrgPositionBeans = saleOrganizationService.findByUnitIdAndPositionName(condition.getUnitId(), null);
        }
       // 当部门编号和岗位名称都不为空时
       if ( PublicUtil.isNotEmpty(condition.getUnitId()) && PublicUtil.isNotEmpty(condition.getPositionName()) ) {
           if (condition.isAccurate()) {
              // 根据部门Id和岗位名称精确查询
               saleOrgPositionBeans = saleOrganizationService.findByUnitIdAndPositionName(condition.getUnitId(), condition.getPositionName());
           }else {
               // 根据部门Id和岗位名称模糊查询
               saleOrgPositionBeans = saleOrganizationService.findByUnitIdAndPositionNameLike(condition.getUnitId(), condition.getPositionName());
           }

       }
        int count = 0;
        for (SaleOrgPositionBean saleOrgPositionBean : saleOrgPositionBeans) {
            count = saleEmployeePositionMapper.getCountByPositionId(saleOrgPositionBean.getId());
            saleOrgPositionBean.setName(saleOrgPositionBean.getName() + "(" + count + ")");
        }
        return saleOrgPositionBeans;
    }

    @Override
    public List<String> listOrganIdByPid(String ids) {
        if (null == ids ||StringUtils.isBlank(ids)) {
            return null;
        }
        String[] split = ids.split("-");

        QueryWrapper<SaleOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ParentId",Arrays.asList(split));
        List<SaleOrganization> saleOrganizationList = saleOrganizationMapper.selectList(queryWrapper);
        if (saleOrganizationList.isEmpty()) {
            return null;
        }
        return saleOrganizationList.stream().map(SaleOrganization::getId).collect(Collectors.toList());
    }


    /**
     * @description 北京用于前端查询营业所树状菜单
     * @author C12961
     * @date 2022/3/16 8:54
     */
    @Override
    public List<TreeInfo> findAfterFiltrationByBusinessOffice() {
        // 查询3个分公司、行业开发部下所有的部门，需过滤HL级别
        List<OpsUnitsTree> departInfo = saleOrganizationMapper.findAfterFiltrationDeptInfo();
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

    @Override
    public List<TreeInfo> findAfterFiltrationByBusinessOffice2() {

        List<DataCodeVO> data = null;
        Object o = redisManager.get(Constant.SELUI_DEPART_CODE_KEY);
        if(o != null) {
            data = JSONArray.parseArray(o.toString(), DataCodeVO.class);
        } else {
            // 字典表获取公司编码
            ResultVo<List<DataCodeVO>> dataCodes = dataTypeService.getDataCodes(Constant.SELUI_DEPART_CODE);
            if (!dataCodes.isSuccess() || dataCodes.getData() == null) {
                return null;
            }
            data = dataCodes.getData();
            redisManager.set(Constant.SELUI_DEPART_CODE_KEY, JSONArray.toJSONString(data));
        }
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        List<String> deptNos = data.stream().map(DataCodeVO::getCode).collect(Collectors.toList());
        // 查询3个分公司、行业开发部下所有的部门，需过滤HL级别
        List<OpsUnitsTree> departInfo = saleOrganizationMapper.findAfterFiltrationDeptInfo2(deptNos);
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
        if (org.apache.commons.lang.StringUtils.isBlank(unitCode)) {
            return unitCode.split("!");
        }
        String[] splits = unitCode.split("!");
        List<String> codes = new ArrayList<>(Arrays.asList(splits));
        codes.remove(splits.length - 2);
        return codes.toArray(new String[codes.size()]);
    }
    /**
     * @description 北京用于查询营业所字典
     * @author C12961
     * @date 2022/3/16 8:54
     */
    public List<DeptDictDTO> findDeptAsDict(){
        return saleOrganizationMapper.findDeptAsDict();
    }



}

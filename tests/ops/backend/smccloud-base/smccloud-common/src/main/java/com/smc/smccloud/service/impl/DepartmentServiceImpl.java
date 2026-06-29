package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.DepartmentMapper;
import com.smc.smccloud.mapper.HRHolonMapper;
import com.smc.smccloud.mapper.HROrganizationMapper;
import com.smc.smccloud.mapper.OrganizationRelationMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.Department.DepartmentDTO;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.DepartmentService;
import com.smc.smccloud.service.DictDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private HROrganizationMapper hrOrganizationMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private OrganizationRelationMapper organizationRelationMapper;
    @Resource
    private HRHolonMapper hrHolonMapper;
    @Resource
    private RedisManager redisManager;

    @Resource
    private DictDataService dictDataService;


    @Override
    public PageInfo<DepartmentDO> findDepartmentData(DepartmentDTO info, Page page) {
        LambdaQueryWrapper<DepartmentDO> query = new LambdaQueryWrapper<>();
        query
            .eq(StringUtils.isNotBlank(info.getDeptNo()), DepartmentDO::getDeptNo, info.getDeptNo())
            .like(StringUtils.isNotBlank(info.getDeptName()), DepartmentDO::getDeptName, info.getDeptName())
            .eq(DepartmentDO::getIsValid,1);


        return PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> departmentMapper.selectList(query));
    }


    @Override
    public ResultVo<String> saveOrUpdateDeptInfo(DepartmentVO info) {

        if (PublicUtil.isEmpty(info.getOldDeptNo())) {
            return ResultVo.success("请填写旧部门代码");
        }

        DepartmentDO departmentDO = BeanCopyUtil.copy(info, DepartmentDO.class);
        departmentDO.setIsValid(true);
        if (PublicUtil.isEmpty(info.getId())) {
            int insert = departmentMapper.insert(departmentDO);

            return insert == 1 ? ResultVo.success("保存成功") : ResultVo.failure("保存失败");
        } else {
            departmentDO.setId(info.getId());
            int i = departmentMapper.updateById(departmentDO);

            return i == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
        }

    }

    @Override
    public ResultVo<String> deleteDeptInfoById(Integer id) {
        LambdaUpdateWrapper<DepartmentDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DepartmentDO::getId,id).set(DepartmentDO::getIsValid,0);
        int update =   departmentMapper.update(null,updateWrapper);
        return update == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<DepartmentVO> getDepartmentInfo(String deptNo) {
        Object o = redisManager.hGet(Constants.REDIS_DEPARTMENT_INFO, deptNo);
        if (o != null) {
            return ResultVo.success(JSONObject.parseObject(o.toString(), DepartmentVO.class));
        }
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepartmentDO::getDeptNo, deptNo);
        queryWrapper.eq(DepartmentDO::getIsValid, 1);
        List<DepartmentDO> departmentDOList = departmentMapper.selectList(queryWrapper);
        if (departmentDOList.isEmpty()) {
            return ResultVo.failure("部门不存在");
        }
        DepartmentVO departmentVO = BeanCopyUtil.copy(departmentDOList.get(0), DepartmentVO.class);
        redisManager.hPut(Constants.REDIS_DEPARTMENT_INFO, deptNo, JSONObject.toJSONString(departmentDOList.get(0)));
        return ResultVo.success(departmentVO);
    }

    @Override
    public void cacheAllDepartmentInfo() {
        // 获取所有部门信息
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepartmentDO::getIsValid,1);
        List<DepartmentDO> list = departmentMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (DepartmentDO info : list) {
            if (StringUtils.isBlank(info.getDeptNo())) {
                continue;
            }

            boolean b = redisManager.hHasKey(Constants.REDIS_DEPARTMENT_INFO, info.getDeptNo());

            boolean b1 = redisManager.hHasKey(Constants.REDIS_DEPARTMENT_NAME_ALL, info.getDeptNo());

            if (b) {
                redisManager.hdel(Constants.REDIS_DEPARTMENT_INFO, info.getDeptNo());
            }
            if (b1) {
                redisManager.hdel(Constants.REDIS_DEPARTMENT_NAME_ALL, info.getDeptNo());
            }

            redisManager.hPut(Constants.REDIS_DEPARTMENT_INFO, info.getDeptNo(), JSON.toJSONString(info));
            redisManager.hPut(Constants.REDIS_DEPARTMENT_NAME_ALL, info.getDeptNo(), info.getDeptName());
        }
    }

    @Override
    public ResultVo<DepartmentVO> getDeptNoByOldNo(String oldDeptNo) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepartmentDO::getOldDeptNo, oldDeptNo);
        queryWrapper.eq(DepartmentDO::getIsValid, 1);
        DepartmentDO departmentDO = departmentMapper.selectOne(queryWrapper);
        DepartmentVO departmentVO = BeanCopyUtil.copy(departmentDO, DepartmentVO.class);
        return ResultVo.success(departmentVO);
    }

    @Override
    public ResultVo<List<DataCodeVO>> findDepartments() {
        List<DepartmentDO> list = departmentMapper.selectList(null);
        List<DataCodeVO> listVO = new ArrayList<>(list.size());
        DataCodeVO vo;
        for (DepartmentDO info : list) {
            vo = new DataCodeVO();
            vo.setCode(info.getDeptNo());
            vo.setCodeName(info.getDeptName());
            listVO.add(vo);
        }
        return ResultVo.success(listVO);
    }

    @Override
    public ResultVo<List<DepartmentVO>> listDepartment() {
        List<DepartmentDO> departmentDOList = departmentMapper.selectList(null);
        if (departmentDOList.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        List<DepartmentVO> departmentVOS = BeanCopyUtil.copyList(departmentDOList, DepartmentVO.class);
        return ResultVo.success(departmentVOS);
    }

    @Override
    public ResultVo<String> getDeptNameByNo(String deptNo) {
        if (PublicUtil.isEmpty(deptNo)) {
            return ResultVo.failure("该部门编号不存在");
        }
        String deptName = null;
        Object obj = redisManager.hGet(Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo);

        if (obj != null) {
            deptName = obj.toString();
        } else {
            LambdaQueryWrapper<HROrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(HROrganizationDO::getId, HROrganizationDO::getName);
            queryWrapper.eq(HROrganizationDO::getId, deptNo)
                        .eq(HROrganizationDO::getStatus,"0");
            HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(queryWrapper);

            if (PublicUtil.isNotEmpty(hrOrganizationDO)) {
                deptName = hrOrganizationDO.getName();
                redisManager.hPut(Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo, deptName);
            }
        }

        return ResultVo.success(deptName);
    }

    @Override
    public ResultVo<String> getSixDeptNoByTwoDeptNo(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return ResultVo.failure("部门编码参数不可为空");
        }

        OrganizationRelationDO organizationRelationDO = organizationRelationMapper.selectById(deptNo);
        if (organizationRelationDO == null) {
            return ResultVo.failure("二位部门编码" + deptNo + "不存在");
        }
        return ResultVo.success(organizationRelationDO.getHRUnitId());
    }

    /**
     * xxx 根据FNUMBER查出有数据 xx是Holon 取SALEDEPTFNUMBER为营业所
     * 无数据  不是holon 则是营业所
     *
     * @param hrSalesDeptNo 销售部门代码
     */
    @Override
    public ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo) {
        // 查询销售部门代码是否是Holon,如果是则返回所属的营业所代码,否则其本身就是营业所代码
//        LambdaQueryWrapper<HRHolonDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(HRHolonDO::getFNUMBER, HRHolonDO::getPARENTFNUMBER);
//        queryWrapper.eq(HRHolonDO::getFNUMBER, hrSalesDeptNo);
//        HRHolonDO hrHolonDO = hrHolonMapper.selectOne(queryWrapper);
//        if (hrHolonDO == null) {
//            return ResultVo.success(hrSalesDeptNo);
//        } else {
//            return ResultVo.success(hrHolonDO.getPARENTFNUMBER());
//        }
        if (StringUtils.isBlank(hrSalesDeptNo)) {
            return ResultVo.failure("入参不可为空");
        }
        List<String> hrLevel = new ArrayList<>();
        hrLevel.add("课内机构(HL)");
        hrLevel.add("驻在所HL");
        LambdaQueryWrapper<HROrganizationDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(HROrganizationDO::getId, HROrganizationDO::getParentId)
                .in(HROrganizationDO::getLevel, hrLevel)
                .eq(HROrganizationDO::getCompanyCode, "200000")
                .eq(HROrganizationDO::getId, hrSalesDeptNo);
        HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(lambdaQueryWrapper);
        if (hrOrganizationDO == null) {
            return ResultVo.success(hrSalesDeptNo);
        }
        return ResultVo.success(hrOrganizationDO.getParentId());


    }

    @Override
    public ResultVo<String> getParentNumberByDeptNo(String deptNo) {
//        LambdaQueryWrapper<HRHolonDO> hrHolonQuery = new LambdaQueryWrapper<>();
//        hrHolonQuery.select(HRHolonDO::getFNUMBER, HRHolonDO::getSALEDEPTFNUMBER);
//        hrHolonQuery.eq(HRHolonDO::getFNUMBER, deptNo);
//        HRHolonDO hrHolonDO = hrHolonMapper.selectOne(hrHolonQuery);
//        if (hrHolonDO != null) {
//            return ResultVo.success(hrHolonDO.getSALEDEPTFNUMBER());
//        } else {
//            LambdaQueryWrapper<HROrganizationDO> hrOrgQuery = new LambdaQueryWrapper<>();
//            hrOrgQuery.select(HROrganizationDO::getId, HROrganizationDO::getParentId);
//            hrOrgQuery.eq(HROrganizationDO::getId, deptNo);
//            HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(hrOrgQuery);
//            return ResultVo.success(hrOrganizationDO.getParentId());
//        }
        if (StringUtils.isBlank(deptNo)) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<HROrganizationDO> hrOrgQuery = new LambdaQueryWrapper<>();
        hrOrgQuery.select(HROrganizationDO::getId, HROrganizationDO::getParentId);
        hrOrgQuery.eq(HROrganizationDO::getId, deptNo);
        HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(hrOrgQuery);
        return ResultVo.success(hrOrganizationDO.getParentId());
    }

    @Override
    public ResultVo<HROrganizationVO> findHrOrganByDeptNo(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return ResultVo.failure("参数不可为空.");
        }
        Object object = redisManager.hGet(Constants.REDIS_ORGAN_DEPTNO, deptNo);
        if (object != null) {
            JSONObject.parseObject(object.toString(), HROrganizationVO.class);
        }
        HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectById(deptNo);
        if (hrOrganizationDO == null) {
            return ResultVo.failure("暂无数据");
        }
        redisManager.hPut(Constants.REDIS_ORGAN_DEPTNO, deptNo, JSONObject.toJSONString(hrOrganizationDO));
        return ResultVo.success(BeanCopyUtil.copy(hrOrganizationDO, HROrganizationVO.class));
    }

    @Override
    public ResultVo<List<HROrganizationVO>> listHrOrganAllData(String deptNo) {
        LambdaQueryWrapper<HROrganizationDO> query = new LambdaQueryWrapper<>();
        query.likeRight(HROrganizationDO::getId, "2")
                .eq(StringUtils.isNotBlank(deptNo), HROrganizationDO::getId, deptNo);
        List<HROrganizationDO> list = hrOrganizationMapper.selectList(query);
        for (HROrganizationDO item : list) {
            item.setName(item.getId() + "-" + item.getName());
        }
        List<HROrganizationVO> voList = BeanCopyUtil.copyList(list, HROrganizationVO.class);
        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<Integer> getDepartmentDlvDayByDeptNo(String deptNo) {
        Object o = redisManager.get(Constants.REDIS_TRANSPORTDAY_DEPTNO + deptNo);
        if (o != null) {
            return ResultVo.success(Integer.parseInt(o.toString()));
        }
        DepartmentVO departmentInfo = this.getDepartmentInfo(deptNo).getData();
        if (departmentInfo == null) {
            HROrganizationVO hrOrganizationVO = this.findHrOrganByDeptNo(deptNo).getData();
            if (hrOrganizationVO != null && StringUtils.isNotBlank(hrOrganizationVO.getParentId())) {
                departmentInfo = this.getDepartmentInfo(hrOrganizationVO.getParentId()).getData();
            }
        }

        if (departmentInfo != null) {
            redisManager.set(Constants.REDIS_TRANSPORTDAY_DEPTNO + deptNo, departmentInfo.getDlvDay());
            return ResultVo.success(Optional.ofNullable(departmentInfo.getDlvDay()).orElse(0));
        }
        return ResultVo.success(0);
    }

    @Override
    public ResultVo<List<CodeName>> getDeptTreeByNo(List<String> deptNos) {
        LambdaQueryWrapper<HROrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HROrganizationDO::getStatus, "0");
        List<HROrganizationDO> hrOrganizationList = hrOrganizationMapper.selectList(queryWrapper);
        //    <!--add by WuWeiDong 20230531  bug 10950  营业信息查询空指针错误 -->

        Map<String, List<HROrganizationDO>> allMap = hrOrganizationList.stream()
                .filter(f -> f.getParentId() != null)
                .collect(Collectors.groupingBy(HROrganizationDO::getParentId));

        String[] nos;
        String[] names;
        if (CollectionUtils.isEmpty(deptNos)) {
            //    <!--add by WuWeiDong 20230531  bug 10950  用字典数据 -->
            ResultVo<List<DataCodeVO>> dataCodes = dictDataService.getDataCodes("9005");
            if (!dataCodes.isSuccess() || dataCodes.getData() == null) {
                // BJ, SH, GZ, HY
//                nos = new String[]{"220000", "240000", "260000", "235000", "280000"};
//                names = new String[]{"北京分公司", "上海分公司", "广州分公司", "行业开发部", "江苏分公司"};
                return ResultVo.failure("分公司字典没有数据，请建立。");
            } else {
                nos = new String[dataCodes.getData().size()];
                names = new String[dataCodes.getData().size()];
                for (int i = 0; i < dataCodes.getData().size(); i++) {
                    DataCodeVO dataCodeVO = dataCodes.getData().get(i);
                    nos[i] = dataCodeVO.getCode();
                    names[i] = dataCodeVO.getCodeName();
                }
            }

        } else {
            nos = deptNos.toArray(new String[0]);
            names = new String[nos.length];
            ResultVo<String> deptName;
            for (int i = 0; i < nos.length; i++) {
                deptName = this.getDeptNameByNo(nos[i]);
                names[i] = deptName.getData();
            }
        }

        List<CodeName> codeList = new ArrayList<>(4);
        CodeName info;
        for (int i = 0; i < nos.length; i++) {
            info = new CodeName(nos[i], names[i]);
            codeList.add(info);
            this.createDeptTree(info, allMap);
        }

        return ResultVo.success(codeList);
    }

    private void createDeptTree(CodeName parentCode, Map<String, List<HROrganizationDO>> deptMap) {
        if (deptMap.containsKey(parentCode.getValue())) {
            List<CodeName> codeList = new ArrayList<>();
            CodeName codeInfo;
            for (HROrganizationDO deptInfo : deptMap.get(parentCode.getValue())) {
                codeInfo = new CodeName(deptInfo.getId(), deptInfo.getName());
                codeList.add(codeInfo);
                this.createDeptTree(codeInfo, deptMap);
            }
            parentCode.setChildren(codeList);
        }
    }
}

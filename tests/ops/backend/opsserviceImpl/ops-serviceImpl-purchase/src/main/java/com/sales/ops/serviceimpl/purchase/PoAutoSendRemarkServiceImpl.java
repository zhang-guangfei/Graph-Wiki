package com.sales.ops.serviceimpl.purchase;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.HrOrganizationMapper;
import com.sales.ops.db.dao.OpsPoAutosendRemarkConfigMapper;
import com.sales.ops.db.dao.OpsRequestpurchaseInterceptConfigMapper;
import com.sales.ops.db.dao.OpsWarehouseMapper;
import com.sales.ops.db.entity.HrOrganization;
import com.sales.ops.db.entity.HrOrganizationExample;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfigExample;
import com.sales.ops.dto.query.PoAutoSendRemarkQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.purchase.PoAutoSendRemarkService;
import com.smc.smccloud.core.utils.ExcelHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author B91717
 * @date 2023/08/24
 * @apiNote 采购自动发单，备注配置
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PoAutoSendRemarkServiceImpl implements PoAutoSendRemarkService {


    @Autowired
    private OpsRequestpurchaseInterceptConfigMapper opsRequestpurchaseInterceptConfigMapper;

    @Autowired
    private OpsWarehouseMapper opsWarehouseMapper;

    @Autowired
    private OpsPoAutosendRemarkConfigMapper opsPoAutosendRemarkConfigMapper;

    @Autowired
    private HrOrganizationMapper hrOrganizationMapper;


    /**
     * 条件查询，自定义拦截清单查询
     *
     * @param pageModel
     */
    @Override
    public PageInfo<OpsPoAutosendRemarkConfig> findAll(PageModel<PoAutoSendRemarkQO> pageModel) {

        PageInfo<OpsPoAutosendRemarkConfig> pageInfo = new PageInfo<OpsPoAutosendRemarkConfig>();
        OpsPoAutosendRemarkConfigExample example = new OpsPoAutosendRemarkConfigExample();
        OpsPoAutosendRemarkConfigExample.Criteria criteria = example.createCriteria();
//        criteria.andIsDeletedEqualTo(false);
        // 筛选初始列表状态
        if (pageModel.getCondition() == null) {
            pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
                    .doSelectPageInfo(() -> opsPoAutosendRemarkConfigMapper.selectByExample(example));
            return pageInfo;
        }
        PoAutoSendRemarkQO condition = pageModel.getCondition();

        if (StringUtils.isNotBlank(condition.getModelno())) {
            String RuleKey = "%" + condition.getModelno() + "%";
            criteria.andModelnoLike(RuleKey);
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            String remark = "%" + condition.getRemark() + "%";
            criteria.andRemarkLike(remark);
        }
        if (!CollectionUtils.isEmpty(condition.getDeptNos())) {
            criteria.andDeptnoIn(condition.getDeptNos());
        }
        if (condition.getDeleted()!=null) {
            criteria.andIsDeletedEqualTo(condition.getDeleted());
        }
        if (StringUtils.isNotBlank(condition.getInsertUser())) {
            criteria.andInsertUserEqualTo(condition.getInsertUser());
        }
//        bug13477,采购发单配置编辑功能修改
        if (StringUtils.isNotBlank(condition.getSupplierClass())) {
            criteria.andSupplierClassEqualTo(condition.getSupplierClass());
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(condition.getCustomerNo());
        }

        pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
                .doSelectPageInfo(() -> opsPoAutosendRemarkConfigMapper.selectByExample(example));
        return pageInfo;
    }

    @Override
    public Integer updateData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException {

        // 创建更新实体
        OpsPoAutosendRemarkConfig updates = new OpsPoAutosendRemarkConfig();
        updates.setUpdateTime(new Date());
        updates.setUpdateUser(opsPoAutosendRemarkConfig.getUpdateUser());
        updates.setDeptno(opsPoAutosendRemarkConfig.getDeptno());
        updates.setRemark(opsPoAutosendRemarkConfig.getRemark());
        updates.setModelno(opsPoAutosendRemarkConfig.getModelno());
        // bug13477 采购发单配置编辑功能修改 B91717
        updates.setSupplierClass(opsPoAutosendRemarkConfig.getSupplierClass());
        updates.setCustomerNo(opsPoAutosendRemarkConfig.getCustomerNo());
        updates.setIsDeleted(opsPoAutosendRemarkConfig.getIsDeleted());
        // 更新
        OpsPoAutosendRemarkConfigExample example = new OpsPoAutosendRemarkConfigExample();
        example.createCriteria().andIdEqualTo(opsPoAutosendRemarkConfig.getId());
        int result = opsPoAutosendRemarkConfigMapper.updateByExampleSelective(updates, example);
        return result;
    }

    /**
     * 插入
     *
     * @param opsPoAutosendRemarkConfig
     * @return
     * @throws OpsException
     */
    @Override
    public Integer insertData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException {
        opsPoAutosendRemarkConfig.setInsertTime(new Date());
        return opsPoAutosendRemarkConfigMapper.insertSelective(opsPoAutosendRemarkConfig);
    }

    /**
     * 删除
     *
     * @param opsPoAutosendRemarkConfig
     * @throws OpsException
     */
    @Override
    public void deleteData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException {
        OpsPoAutosendRemarkConfig localData = opsPoAutosendRemarkConfigMapper.selectByPrimaryKey(opsPoAutosendRemarkConfig.getId());
        if (localData.getIsDeleted()) {
            throw Exceptions.OpsException("当前单据已经为删除状态，请勿重复操作！");
        }
        // 创建删除实体
        OpsPoAutosendRemarkConfig deletes = new OpsPoAutosendRemarkConfig();
        deletes.setId(opsPoAutosendRemarkConfig.getId());
        deletes.setUpdateTime(new Date());
        deletes.setUpdateUser(opsPoAutosendRemarkConfig.getUpdateUser());
        deletes.setIsDeleted(true);
        // 逻辑删除
        opsPoAutosendRemarkConfigMapper.updateByPrimaryKeySelective(deletes);
    }

    @Override
    public void restoreData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException {
        OpsPoAutosendRemarkConfig localData = opsPoAutosendRemarkConfigMapper.selectByPrimaryKey(opsPoAutosendRemarkConfig.getId());
        if (!localData.getIsDeleted()) {
            throw Exceptions.OpsException("当前单据已经正常状态，无需恢复！");
        }
        // 创建删除实体
        OpsPoAutosendRemarkConfig deletes = new OpsPoAutosendRemarkConfig();
        deletes.setId(opsPoAutosendRemarkConfig.getId());
        deletes.setUpdateTime(new Date());
        deletes.setUpdateUser(opsPoAutosendRemarkConfig.getUpdateUser());
        deletes.setIsDeleted(false);
        // 逻辑删除
        opsPoAutosendRemarkConfigMapper.updateByPrimaryKeySelective(deletes);
    }

    /**
     * 批量删除功能
     *
     * @param list
     * @return
     * @throws OpsException
     */
    @Override
    public Integer deleteBatch(List<OpsPoAutosendRemarkConfig> list) throws OpsException {
        for (OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig: list) {
            OpsPoAutosendRemarkConfig localData = opsPoAutosendRemarkConfigMapper.selectByPrimaryKey(opsPoAutosendRemarkConfig.getId());
            if (localData.getIsDeleted()) {
                throw Exceptions.OpsException("当前单配置型号：" +opsPoAutosendRemarkConfig.getModelno() + "部门代码：" + opsPoAutosendRemarkConfig.getDeptno() + "已经为删除状态，请勿重复操作！");
            }
        }
        // 获取批量id集合
        List<Long> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        OpsPoAutosendRemarkConfigExample example = new OpsPoAutosendRemarkConfigExample();
        example.createCriteria().andIdIn(idList);
        // 创建删除实体
        OpsPoAutosendRemarkConfig deletes = new OpsPoAutosendRemarkConfig();
        deletes.setUpdateTime(new Date());
        deletes.setUpdateUser(list.get(0).getUpdateUser());
        deletes.setIsDeleted(true);
        return opsPoAutosendRemarkConfigMapper.updateByExampleSelective(deletes, example);
    }

    /**
     * excel导入
     *
     * @param file
     * @param loginUser
     */
    @Override
    public String importFile(MultipartFile file, String loginUser) throws Exception {
        if (file == null) {
            throw Exceptions.OpsException("请上传文件:");
        }
        if (StringUtils.isBlank(loginUser)) {
            throw Exceptions.OpsException("操作人为空:");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            throw Exceptions.OpsException("文件格式错误,请按照模板文件格式进行导入:");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            throw Exceptions.OpsException("未读取到文件");
        }
        Sheet sheet = excel.getSheet();
        Row rows;
        OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig;
        // 获取部门转换map,用于匹配部门代码和中文
        Map<String, String> orgMap = getOrganization();
        int count = 0;
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            rows = sheet.getRow(j);
            if (rows == null) {
                break;
            }
            // 通过department Map进行翻译，如果找不到对应代码，则前端提示
            String deptname = excel.getCellString(rows.getCell(0)).trim();
            if (StringUtils.isBlank(deptname)) {
                throw Exceptions.OpsException("第" + j + "行的营业所为空.请仔细检查数据");
            }
            // 将中文转换为部门代码
            String deptno = orgMap.get(deptname);
            if (StringUtils.isBlank(deptno)) {
                throw Exceptions.OpsException("第" + j + "行的营业所名称未找到对应 部门代码.请仔细检查数据");
            }
            String modelno = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(modelno)) {
                throw Exceptions.OpsException("第" + j + "行的型号为空.请仔细检查数据");
            }
            String remark = excel.getCellString(rows.getCell(2));
            if (StringUtils.isBlank(remark)) {
                throw Exceptions.OpsException("第" + j + "行的备注为空.请仔细检查数据");
            }
//            bug13477,采购发单配置编辑功能修改
            String suppilytype = excel.getCellString(rows.getCell(3));
//            if (StringUtils.isBlank(remark)) {
//                throw Exceptions.OpsException("第" + j + "行的供应商分类为空.请仔细检查数据");
//            }
            String customer = excel.getCellString(rows.getCell(4));
//            if (StringUtils.isBlank(remark)) {
//                throw Exceptions.OpsException("第" + j + "行的备注为空.请仔细检查数据");
//            }
            // 对特殊字符进行格式化
            remark = replaceSpace(remark);
            opsPoAutosendRemarkConfig = new OpsPoAutosendRemarkConfig();
            opsPoAutosendRemarkConfig.setDeptno(deptno);
            opsPoAutosendRemarkConfig.setModelno(modelno);
            opsPoAutosendRemarkConfig.setRemark(remark);
            opsPoAutosendRemarkConfig.setInsertUser(loginUser);
            opsPoAutosendRemarkConfig.setInsertTime(new Date());
            // bug13477 采购发单配置编辑功能修改 B91717
            if (StringUtils.isNotBlank(suppilytype)) {
                opsPoAutosendRemarkConfig.setSupplierClass(suppilytype);
            }
            if (StringUtils.isNotBlank(customer)) {
                opsPoAutosendRemarkConfig.setCustomerNo(customer);
            }
            opsPoAutosendRemarkConfigMapper.insertSelective(opsPoAutosendRemarkConfig);
            count++;
        }
        return "批量导入完毕.共计" + count + "条";
    }

    @Override
    public List<HrOrganization> findDepartment() {
        HrOrganizationExample hrOrganizationExample = new HrOrganizationExample();
        HrOrganizationExample.Criteria HrOrganizationCriteria = hrOrganizationExample.createCriteria();
        HrOrganizationCriteria.andCompanycodeEqualTo("200000");
        return hrOrganizationMapper.selectByExample(hrOrganizationExample);

    }

    /**
     * 根据hr_org 返回 代码、营业所名称对照map
     *
     * @return
     */
    private Map<String, String> getOrganization() {
        HrOrganizationExample hrOrganizationExample = new HrOrganizationExample();
        HrOrganizationExample.Criteria HrOrganizationCriteria = hrOrganizationExample.createCriteria();
        HrOrganizationCriteria.andCompanycodeEqualTo("200000");
        List<HrOrganization> list = hrOrganizationMapper.selectByExample(hrOrganizationExample);
        return list.stream().collect(Collectors.toMap(
                HrOrganization::getName, HrOrganization::getId,
                (val1, val2) -> val2
        ));
//        return list.stream().collect(Collectors.toMap(HrOrganization::getName,HrOrganization::getId));
    }

    /**
     * 字符串中，特殊字符，替换为空格
     */
    private String replaceSpace(String content) {
        byte[] space = new byte[]{(byte)0xc2,(byte)0xa0};
        String UTFSpace = null;
        try {
            UTFSpace = new String(space,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = content.replace(UTFSpace," ");
        return content;
    }
}

package com.smc.smccloud.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.OpsKdInfoMapper;
import com.smc.smccloud.model.OpsKdInfoDO;
import com.smc.smccloud.model.customer.OpsCustomerWldateVO;
import com.smc.smccloud.model.kd.KdInfoParams;
import com.smc.smccloud.model.kd.KdInfoVO;
import com.smc.smccloud.model.sampleorder.ExportSampleOrderVO;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.OpsKdInfoService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2024-10-22
 */
@Service
public class OpsKdInfoServiceImpl extends ServiceImpl<OpsKdInfoMapper, OpsKdInfoDO> implements OpsKdInfoService {


    @Resource
    private OpsKdInfoMapper opsKdInfoMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private HttpServletResponse response;

    @Override
    public ResultVo<PageInfo<OpsKdInfoDO>> listKdInfos(KdInfoParams params) {
        if (params == null) {
            return ResultVo.success();
        }
        LambdaQueryWrapper<OpsKdInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(params.getModelNos()), OpsKdInfoDO::getModelNo, params.getModelNos())
                .in(CollectionUtils.isNotEmpty(params.getModelSorts()),OpsKdInfoDO::getModelSort,params.getModelSorts())
                .in(CollectionUtils.isNotEmpty(params.getProdFactorys()),OpsKdInfoDO::getProdFactory,params.getProdFactorys())
                .in(CollectionUtils.isNotEmpty(params.getStates()),OpsKdInfoDO::getState,params.getStates())
                .ge(StringUtils.isNotBlank(params.getUpdateTimeStart()),OpsKdInfoDO::getSourceUpdatedTime, params.getUpdateTimeStart())
                .le(StringUtils.isNotBlank(params.getUpdateTimeEnd()),OpsKdInfoDO::getSourceUpdatedTime, params.getUpdateTimeEnd())
                .ge(StringUtils.isNotBlank(params.getBatchProdStartTimeStart()),OpsKdInfoDO::getBatchProdStartTime, params.getBatchProdStartTimeStart())
                .le(StringUtils.isNotBlank(params.getBatchProdStartTimeEnd()),OpsKdInfoDO::getBatchProdStartTime, params.getBatchProdStartTimeEnd());


        PageInfo<OpsKdInfoDO> pageInfo = PageHelper.startPage(params.getPage().getPageNumber(), params.getPage().getPageSize()).doSelectPageInfo(() -> {
            opsKdInfoMapper.selectList(queryWrapper);
        });

        List<OpsKdInfoDO> opsKdInfoDOS = conventKdDataList(pageInfo.getList());
        pageInfo.setList(opsKdInfoDOS);
        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportKdInfoData(KdInfoParams params) {
        if (params == null) {
            return;
        }
        QueryWrapper<OpsKdInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select( "top 20000 * ");
        queryWrapper.lambda()
                .in(CollectionUtils.isNotEmpty(params.getModelNos()), OpsKdInfoDO::getModelNo, params.getModelNos())
                .in(CollectionUtils.isNotEmpty(params.getModelSorts()),OpsKdInfoDO::getModelSort,params.getModelSorts())
                .in(CollectionUtils.isNotEmpty(params.getProdFactorys()),OpsKdInfoDO::getProdFactory,params.getProdFactorys())
                .in(CollectionUtils.isNotEmpty(params.getStates()),OpsKdInfoDO::getState,params.getStates())
                .ge(StringUtils.isNotBlank(params.getUpdateTimeStart()),OpsKdInfoDO::getSourceUpdatedTime, params.getUpdateTimeStart())
                .le(StringUtils.isNotBlank(params.getUpdateTimeEnd()),OpsKdInfoDO::getSourceUpdatedTime, params.getUpdateTimeEnd())
                .ge(StringUtils.isNotBlank(params.getBatchProdStartTimeStart()),OpsKdInfoDO::getBatchProdStartTime, params.getBatchProdStartTimeStart())
                .le(StringUtils.isNotBlank(params.getBatchProdStartTimeEnd()),OpsKdInfoDO::getBatchProdStartTime, params.getBatchProdStartTimeEnd());

        List<OpsKdInfoDO> opsKdInfoDOS = opsKdInfoMapper.selectList(queryWrapper);

        if(CollectionUtils.isEmpty(opsKdInfoDOS)) {
            return;
        }
        conventKdDataList(opsKdInfoDOS);
        // List<KdInfoVO> kdInfoVOS = BeanCopyUtil.copyList(opsKdInfoDOS, KdInfoVO.class);
        try {
            String fileName = URLEncoder.encode("KD查询数据导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), KdInfoVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(opsKdInfoDOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }


    private List<OpsKdInfoDO> conventKdDataList(List<OpsKdInfoDO> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpsKdInfoDO item : list) {
                if (StringUtils.isNotBlank(item.getCreatedBy())) {
                    item.setCreatedBy(opsCommonService.getEmpSaleNameByNo(item.getCreatedBy()));
                }
                if (StringUtils.isNotBlank(item.getUpdatedBy())) {
                    item.setUpdatedBy(opsCommonService.getEmpSaleNameByNo(item.getUpdatedBy()));
                }
            }
        }
        return list;
    }

}

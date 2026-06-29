package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.BrwDetailMapper;
import com.smc.smccloud.mapper.BrwMasterMapper;
import com.smc.smccloud.model.borrowstock.*;
import com.smc.smccloud.service.BorrowStockService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class BorrowStockServiceImpl implements BorrowStockService {

    @Resource
    private BrwMasterMapper brwMasterMapper;

    @Resource
    private BrwDetailMapper brwDetailMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;


    @Override
    public ResultVo<String> saveBrwStockApply(BrwApplyDTO dto) {

        if (PublicUtil.isEmpty(dto.getDeptNo()) || PublicUtil.isEmpty(dto.getCustomerName())){

            return ResultVo.failure("无效请求");
        }

        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
//        String userDTO = "测试";
        BrwMasterDO brwMasterDO = BeanCopyUtil.copy(dto, BrwMasterDO.class);

        if (PublicUtil.isEmpty(dto.getBrwNo())){
            ResultVo<String> brwNo = dictDataServiceFeignApi.getRandomOrderNoGenerator("9001", "8");
            brwMasterDO.setBrwNo(brwNo.getData());
        }else {
            brwMasterDO.setBrwNo(dto.getBrwNo());
        }

//        String brwNo = "BR001";
//        String uuid = UUIDGenerator.getRandomNum();

        if (brwMasterDO.getId()==null){
            brwMasterDO.setStatus(1);
            brwMasterDO.setCreateUser(userDTO.getUserNo());
            brwMasterMapper.insert(brwMasterDO);
        }else {
            brwMasterDO.setUpdateUser(userDTO.getUserNo());
            brwMasterMapper.updateById(brwMasterDO);
        }


        List<BrwDetailDO> brwDetailDO = BeanCopyUtil.copyList(dto.getDetails(), BrwDetailDO.class);

        for (BrwDetailDO detail : brwDetailDO) {

            if (PublicUtil.isEmpty(detail.getModelNo()) || detail.getItemId() == null || detail.getQuantity() == null){
                continue;
            }

            if (detail.getId() == null) {
                detail.setBrwId(brwMasterDO.getId());
                detail.setCreateUser(userDTO.getUserNo());
                brwDetailMapper.insert(detail);
            }else {
                detail.setUpdateUser(userDTO.getUserNo());
                brwDetailMapper.updateById(detail);
            }
        }

        return ResultVo.success("保存成功");
    }


    @Override
    public ResultVo<PageInfo<BrwMasterVO>> listBrwStockInfo(BrwMasterVO brwMasterVO, Page page) {

        LambdaQueryWrapper<BrwMasterDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(brwMasterVO.getStatus()),BrwMasterDO::getStatus,brwMasterVO.getStatus())
                    .eq(PublicUtil.isNotEmpty(brwMasterVO.getBrwNo()),BrwMasterDO::getBrwNo,brwMasterVO.getBrwNo())
                    .eq(PublicUtil.isNotEmpty(brwMasterVO.getCustomerName()),BrwMasterDO::getCustomerName,brwMasterVO.getCustomerName())
                    .eq(PublicUtil.isNotEmpty(brwMasterVO.getDeptNo()),BrwMasterDO::getDeptNo,brwMasterVO.getDeptNo())
                    .between(PublicUtil.isNotEmpty(brwMasterVO.getTimeStart()),BrwMasterDO::getPassDate,brwMasterVO.getTimeStart(),brwMasterVO.getTimeEnd());

        PageInfo<BrwMasterVO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> brwMasterMapper.selectList(queryWrapper));

//        if (PublicUtil.isEmpty(pageInfo.getList())) {
//            return ResultVo.failure("未查询到数据");
//        }
        return ResultVo.success(pageInfo);
    }


    @Override
    public ResultVo<PageInfo<BrwMasterDTO>> listNotReturn(BrwStockRequest request, Page page) {

        PageInfo<BrwMasterDTO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> brwMasterMapper.listNotReturnDTO(request.getBrwNo(), request.getModelNo()));

//        if (PublicUtil.isEmpty(pageInfo.getList())) {
//            return ResultVo.failure("0","未查询到数据");
//        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<BrwDetailVO>> listBrwDetail(Integer id) {

        List<BrwDetailVO> list = brwDetailMapper.listBrwDetailData(id);

//        if (PublicUtil.isEmpty(list)) {
//            return ResultVo.failure("未查询到借货明细");
//        }

        return ResultVo.success(list);
    }
}

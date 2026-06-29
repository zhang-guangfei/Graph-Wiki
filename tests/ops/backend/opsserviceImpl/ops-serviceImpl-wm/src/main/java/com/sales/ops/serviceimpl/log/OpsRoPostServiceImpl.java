package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsRoPostMapper;
import com.sales.ops.db.entity.OpsRoPost;
import com.sales.ops.db.entity.OpsRoPostExample;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.service.log.OpsRoPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/4/20 10:35
 */
@Slf4j
@Service
public class OpsRoPostServiceImpl implements OpsRoPostService {

    @Autowired
    private OpsRoPostMapper opsRoPostMapper;

    /**
     * 存在ROPost
     */
    @Override
    public boolean exitsRoPost(String roId, String barcdoe, String wmsCode) {
        OpsRoPostExample opsRoPostExample = new OpsRoPostExample();
        OpsRoPostExample.Criteria opsRoPostCriteria = opsRoPostExample.createCriteria();
        opsRoPostCriteria.andDelflagEqualTo(0);// 删除标识
        opsRoPostCriteria.andRoIdEqualTo(roId);// 收货指令
        opsRoPostCriteria.andMsgIdEqualTo(wmsCode);// wms 接口指令
        opsRoPostCriteria.andReceiveCodeEqualTo(barcdoe);// barcode
        long countPost = opsRoPostMapper.countByExample(opsRoPostExample);
        // 表中已存在直接返回成功
        if (countPost > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void insertRoPostForRoConfirm(WmRoConfirmDto wmRoConfirmDto) {
        OpsRoPost opsRoPost = new OpsRoPost();
        opsRoPost.setMsgId(wmRoConfirmDto.getWmsOrderCode());
        opsRoPost.setRoId(wmRoConfirmDto.getReceiveOrderCode());
        opsRoPost.setWarehouseCode(wmRoConfirmDto.getWarehouseCode());
        opsRoPost.setReceiveCode(wmRoConfirmDto.getReceiveCode());
        opsRoPost.setQty(Integer.parseInt(wmRoConfirmDto.getQty()));
        opsRoPost.setInventoryType(wmRoConfirmDto.getInventoryType());
        opsRoPost.setScanType(wmRoConfirmDto.getScanType());
        opsRoPost.setModelno(wmRoConfirmDto.getModelNo());
        opsRoPost.setInvoiceno(wmRoConfirmDto.getInvoiceNo());
        opsRoPost.setInvoiceid(wmRoConfirmDto.getInvoiceId());
        opsRoPost.setUsername(wmRoConfirmDto.getUsername());
        opsRoPost.setReceivetime(wmRoConfirmDto.getReceiveTime());
        opsRoPost.setCreator(wmRoConfirmDto.getUsername());
        addOpsRoPost(opsRoPost);
    }

    @Override
    public void addOpsRoPost(OpsRoPost opsRoPost) {
        //存入对账表
        opsRoPost.setDelflag(0);
        opsRoPost.setCreTime(new Date());
        opsRoPostMapper.insertSelective(opsRoPost);
    }

    @Override
    public List<OpsRoPost> findOpsRoPostByRoId(String roId) {
        OpsRoPostExample roPostExample = new OpsRoPostExample();
        roPostExample.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(roId);
        return opsRoPostMapper.selectByExample(roPostExample);
    }

    @Override
    public OpsRoPost getRoPostByAsnId(String roId, String asnId) throws OpsException {
        OpsRoPostExample roExample = new OpsRoPostExample();
        roExample.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(roId).andMsgIdEqualTo(asnId);

        List<OpsRoPost> list = opsRoPostMapper.selectByExample(roExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw Exceptions.OpsException("Ro-POST存在多行记录。入库指令：" + roId + "-ASNID:" + asnId);
        }
        return list.get(0);
    }

}

package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRoPost;
import com.sales.ops.dto.inventory.WmRoConfirmDto;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/20 10:35
 */
public interface OpsRoPostService {
    boolean exitsRoPost(String roId, String barcdoe, String wmsCode);

    void insertRoPostForRoConfirm(WmRoConfirmDto wmRoConfirmDto);

    /**
     * @description 写入WMS回传收货数据
     * @author C12961
     * @date 2023/4/20 10:37
     */
    void addOpsRoPost(OpsRoPost opsRoPost);

    List<OpsRoPost> findOpsRoPostByRoId(String roId);

    OpsRoPost getRoPostByAsnId(String roId, String asnId) throws OpsException;
}

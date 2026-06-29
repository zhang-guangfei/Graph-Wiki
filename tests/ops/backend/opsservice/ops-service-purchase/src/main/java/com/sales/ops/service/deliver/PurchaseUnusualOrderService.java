package com.sales.ops.service.deliver;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPoDeliveryUnusual;
import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.purchase.UnusualQueryDto;
import com.sales.ops.dto.purchase.UnusualViewDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;

import java.util.List;
import java.util.Map;

public interface PurchaseUnusualOrderService {


    List<UnusualViewDto> exportUnusual(UnusualQueryDto queryDto);

    PageInfo<UnusualViewDto> searchUnusual(PageModel<UnusualQueryDto> queryDto);

    Integer pullUnusualsFromLog(OpsPoUnusualOrderLog log) throws OpsException;

    void handleUnusualForCNC(OpsPoDeliveryUnusual unusual, Map<Long, OpsPurchaseinvoice> map);

    void handUsualListForCNCEmail(List<OpsPoDeliveryUnusual> unusualList,Map<Long, OpsPurchaseinvoice> map);

    void handleUnusualListForJP(List<OpsPoDeliveryUnusual> unusualList);

    Integer updateStatusByReplyJapan(Long id, UserDto userDto);

    Integer deleteUnusualByIds(List<Long> ids, UserDto userDto);
}

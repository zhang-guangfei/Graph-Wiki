package com.sales.ops.serviceimpl.mdm;

import com.sales.ops.dto.po.MdmRequest;
import com.sales.ops.dto.po.MdmResponseDto;
import com.sales.ops.dto.po.MdmTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/30 13:30
 */
@FeignClient(name = "mdmFeign", url = "${mdm.url}")
public interface MdmFeignApi {

    @PostMapping(value = "/manage/oauth/token", consumes = "application/x-www-form-urlencoded")
    MdmTokenResponseDto getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret
    );

    @RequestMapping(value = "/mdm/product/inquiry/receiveOPS", method = RequestMethod.POST)
    MdmResponseDto doBusiness(
            @RequestHeader("Authorization") String token,
            @RequestBody List<MdmRequest> request
    );
}

package com.sales.ops.serviceimpl.tms;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.until.MenhuTokenUtil;
import com.sales.ops.dto.order.ExpressInfoDto;
import com.sales.ops.dto.util.MenhuResult;
import com.sales.ops.service.tms.ExpressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/1/6 10:34
 */
@Slf4j
@Service
public class ExpressInfoServiceImpl implements ExpressInfoService {


    @Autowired
    private MenhuTokenUtil menhuTokenUtil;

    @Override
    public ExpressInfoDto getExpressInfo(String courierNo, String type) {
        try {
            MenhuResult menhuResult = getResponse(courierNo, type);
            if (menhuResult != null && menhuResult.isSuccess()) {
                Object content = menhuResult.getContent();
                if (content instanceof JSONObject) {
                    ExpressInfoDto expressinfo = JSONUtil.toBean((JSONObject) content, ExpressInfoDto.class);
                    return expressinfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MenhuResult getResponse(String courierNo, String type) {
        String url = menhuTokenUtil.getMenHuUrl()+"/saleManageSystem/express/findExpressByCourierNo";

        String token = menhuTokenUtil.getMHToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json;charset=UTF-8");

        Map<String, Object> params = new HashMap<>();
        params.put("courierNo", courierNo);
        params.put("type", type);
        String body = HttpUtil.createGet(url).addHeaders(headers).form(params).charset(StandardCharsets.UTF_8).execute().body();
        MenhuResult result = JSONUtil.toBean(body, MenhuResult.class);
        return result;
    }

}

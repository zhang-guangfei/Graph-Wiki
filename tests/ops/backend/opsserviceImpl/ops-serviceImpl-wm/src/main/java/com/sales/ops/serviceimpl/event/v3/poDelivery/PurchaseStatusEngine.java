package com.sales.ops.serviceimpl.event.v3.poDelivery;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.ops.db.entity.PurchaseStatusRule;
import com.sales.ops.dto.poDeliver.PoDeliverQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PurchaseStatusEngine {

    private final ExpressionParser parser = new SpelExpressionParser();
    private final TemplateParserContext tplContext = new TemplateParserContext();
    private final Map<Integer, List<PurchaseStatusRule>> ruleGroups;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 初始化：按大状态分组并预排序
     */
    public PurchaseStatusEngine(List<PurchaseStatusRule> allRules) {
        this.ruleGroups = allRules.stream()
                .collect(Collectors.groupingBy(
                        PurchaseStatusRule::getStatus,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparingInt(PurchaseStatusRule::getPriority).reversed())
                                        .collect(Collectors.toList())
                        )
                ));
    }

    /**
     * 计算详细状态逻辑
     */
    public void execute(PoDeliverQueryDto order) {
        // 1. 获取当前大状态对应的规则组
        List<PurchaseStatusRule> rules = ruleGroups.get(Integer.valueOf(order.getStateCode()));
        if (rules == null || rules.isEmpty()) {
            return;
        }

        // 2. 准备 SpEL 上下文
        StandardEvaluationContext context = new StandardEvaluationContext(order);
        try {
            // 注册常用工具函数
            context.registerFunction("hasText", StringUtils.class.getDeclaredMethod("hasText", String.class));

            // ★★★ 注册日期格式化函数：#{formatDate(date,'yyyy-MM-dd')} ★★★
            // 直接在 DTO 中定义静态方法，模板中可直接使用，无需 T() 前缀
            context.registerFunction("formatDate",
                    PoDeliverQueryDto.class.getDeclaredMethod("formatDate", Date.class, String.class));
        } catch (Exception ignored) {
        }

        // 3. 顺序遍历匹配
        for (PurchaseStatusRule rule : rules) {
            try {
                //匹配规则表中的表达式
                Boolean isMatch = parser.parseExpression(rule.getConditionExpr()).getValue(context, Boolean.class);

                if (Boolean.TRUE.equals(isMatch)) {
                    // 匹配成功：填充二级状态 Code
                    order.setDetailStatusCode(rule.getDetailStatus().toString());
                    String description = "";
                    // 动态渲染描述模板
                    String descriptionTpl = rule.getDescriptionTpl();
                    if (JSONUtil.isJson(descriptionTpl)) {
                        try {
                            description = parseJsonSpel(descriptionTpl, context);
                        } catch (Exception e) {
                            description = parser.parseExpression(descriptionTpl, tplContext)
                                    .getValue(context, String.class);
                        }
                    } else {
                        description = parser.parseExpression(descriptionTpl, tplContext)
                                .getValue(context, String.class);
                    }
                    order.setStatusDescription(description);
                    log.info("匹配成功：{} \n---> {}", rule.getConditionExpr(), description);
                    // 短路机制：一旦命中最高优先级，立即跳出
                    return;
                }
            } catch (Exception e) {
                log.error("采购交付状态信息计算失败", e);
            }
        }
    }

    public String parseJsonSpel(String jsonTemplate, StandardEvaluationContext context) throws Exception {
        // 1. 将 JSON 字符串解析为 Map<String, Object> 保持顺序
        JSONObject jsonObject = JSONUtil.parseObj(jsonTemplate, false, true);
        List<String> nullKeyList = new ArrayList<>();
        // 2. 遍历 Map，对 Value 进行 SpEL 解析
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                if (isSpelExpression((String) value)) {
                    //获取SpEL表达式
                    String expressionString = (String) value;
                    try {
                        // 执行 SpEL 解析
                        Object resolvedValue = parser.parseExpression(expressionString, tplContext).getValue(context);
                        if (StringUtils.isEmpty(resolvedValue)) {
                            nullKeyList.add(entry.getKey());
                        } else {
                            entry.setValue(resolvedValue);
                        }
                    } catch (Exception e) {
                        // 可选：记录日志或抛出异常，取决于业务需求
                        throw new RuntimeException("SpEL 解析失败，字段: " + entry.getKey() + ", 表达式: " + expressionString, e);
                    }
                }
            }
            // 如果 value 不是字符串，或者不包含 #{ }，则保持原样 (可能是普通字符串、数字、null)
        }
        for (String key : nullKeyList) {
            jsonObject.remove(key);
        }
        // 3. 将处理后的 Map 重新序列化为 JSON 字符串
        return jsonObject.toString();
    }

    public String parseJsonSpelMap(String jsonTemplate, StandardEvaluationContext context) throws Exception {
        // 1. 将 JSON 字符串解析为 Map<String, Object>
        Map<String, Object> map = JSONUtil.toBean(jsonTemplate, TreeMap.class, true);
        // 2. 遍历 Map，对 Value 进行 SpEL 解析
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                if (isSpelExpression((String) value)) {
                    //获取SpEL表达式
                    String expressionString = (String) value;
                    try {
                        // 执行 SpEL 解析
                        Object resolvedValue = parser.parseExpression(expressionString, tplContext).getValue(context);
                        entry.setValue(resolvedValue);
                    } catch (Exception e) {
                        // 可选：记录日志或抛出异常，取决于业务需求
                        throw new RuntimeException("SpEL 解析失败，字段: " + entry.getKey() + ", 表达式: " + expressionString, e);
                    }
                }
            }
            // 如果 value 不是字符串，或者不包含 #{ }，则保持原样 (可能是普通字符串、数字、null)
        }
        // 3. 将处理后的 Map 重新序列化为 JSON 字符串
        return JSONUtil.toJsonStr(map);
    }

    // 只有当值是字符串，且有 #{ } 时，才视为 SpEL 表达式
    private boolean isSpelExpression(String str) {
        if (str == null || str.length() < 4) return false;
        return str.contains("#{") && str.contains("}");
    }
}
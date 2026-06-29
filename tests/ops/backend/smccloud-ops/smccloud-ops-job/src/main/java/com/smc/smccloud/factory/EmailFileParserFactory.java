package com.smc.smccloud.factory;

import com.smc.smccloud.service.EmailFileParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-12-24 11:05
 * Description:
 */
@Component
public class EmailFileParserFactory implements ApplicationContextAware {

    private static Map<String, EmailFileParser> emailFileParserMap;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        Map<String, EmailFileParser> map = applicationContext.getBeansOfType(EmailFileParser.class);
        emailFileParserMap = new HashMap<>();
        map.forEach((key, value) -> emailFileParserMap.put(value.keyword(), value));
    }

    public EmailFileParser getEmailFileParser(String keyword) {
        for (EmailFileParser emailFileParser:emailFileParserMap.values()) {
            if(emailFileParser.validEmail(keyword))
            {
                return emailFileParser;
            }
        }
        return emailFileParserMap.get(keyword);
    }
}

package com.sales.ops.serviceimpl.ips;

import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @description:
 * @author: B91717
 */
@Component
public class IpsBatchnoUtils {

    public static final String EMPTY = "";

    /**
     * 字符串格式化拼接 前缀 %0  用于String.format("%04d", 1);补0 4代表补0位数
     */
    public static final String FORMAT_ZERO = "%0";

    /**
     * 字符串格式化拼接 后缀d  用于String.format("%04d", 1);补0 4代表补0位数
     */
    public static final String FORMAT_D = "d";

    /**
     * 数字 0
     */
    public static final int ZERO = 0;

    /**
     * 数字 1
     */
    public static final int ONE = 1;



    /**
     *
     * createIncrementId:(创建自增id包括前缀XXX 对应格式的日期中间yyMMdd 初始自增补0位数 如TJD2112250001 ). <br/>
     * TODO:(如果lastId 为空创建今天的首个 Id 如果有值 +1).<br/>
     * TODO:(例如：getId(new Date(), null) -> yyMMdd0001).<br/>
     * TODO:(例如：getId(new Date(), yyMMdd0001) -> yyMMdd0002).<br/>
     * @author B91717
     * @param date 日期
     * @param dateFormat 日期格式
     * @param digit 自增后缀位数  如 4 则第一个后缀为 0001
     * @param lastId 上次id
     * @return
     */
    public synchronized static String createIncrementIdIps(String prefix, Date date, String dateFormat, int digit , String lastId) {

        /** - id的日期格式前缀 - */
        StringBuffer idDatePrefix = new StringBuffer(prefix);

        idDatePrefix.append(new DateTime(date).toString(dateFormat));

        /** - 如果是第一次创建对后缀进行对应位数补0 - */
        if (StringUtils.isEmpty(lastId)) {
            return idDatePrefix.append(String.format(FORMAT_ZERO + digit + FORMAT_D, ONE)).toString();
        }
        /** - 自增后缀实现 - */
        long incrementPostfix = Long.valueOf(StringUtils.substringAfter(lastId, idDatePrefix.toString()) == EMPTY ? String.valueOf(ZERO) : StringUtils.substringAfter(lastId, idDatePrefix.toString()));
        incrementPostfix += ONE;

        /** - 如果自增后缀位数超出补0 位数 则直接拼接返回 - */
        if (String.valueOf(incrementPostfix).length() > digit) {
            return idDatePrefix.append(incrementPostfix).toString();
        }

        /** - 按指定位数 补0 - */
        return idDatePrefix.append(String.format(FORMAT_ZERO + digit + FORMAT_D, incrementPostfix)).toString();
    }


    public static Date getNextWorkDay(Date toDate) {
        // 获取下一个自然日
        LocalDate localDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusDays(1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDayTimeEnd(Date toDate) {
        // 获取当前日期的23:59:59秒
        LocalDate localDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) {
        System.out.println(createIncrementIdIps("CN", new Date(), "yyyyMMdd", 4, "CN202503061000"));
    }




}

package com.smc.smccloud.common;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import org.junit.jupiter.api.Assertions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2022/10/26 16:58
 * @Descripton TODO
 */
public class TestUtil {

    public static void main(String[] args) {

//        try {
//            // 编码
////            String encode = Base64.getEncoder().encodeToString("".getBytes("UTF-8"));
////            System.out.println(encode);
//            // 解码
//            byte[] decode = Base64.getDecoder().decode("QzAyMDM4JjE2NzY1MzU2NDE2NDA");
//            System.out.println(new String(decode, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String ss = "-";
////        String s2s = "aa-aa";
////        System.out.println("s2s = " + s2s.contains("-"));
////        System.out.println("ss = " + ss.contains("-"));
//        RcvDetailVO rcvDetailVO = new RcvDetailVO();
//        rcvDetailVO.setRorderNo("aaaa");
//        rcvDetailVO.setRorderItem(12);
//        rcvDetailVO.setStatus(7);
//
//        RcvDetailVO rcvDetailVO2 = new RcvDetailVO();
//        rcvDetailVO2.setRorderNo("aaaa");
//        rcvDetailVO2.setRorderItem(2);
//        rcvDetailVO2.setStatus(6);
//
//        RcvDetailVO rcvDetailVO3 = new RcvDetailVO();
//        rcvDetailVO3.setRorderNo("bbbb");
//        rcvDetailVO3.setRorderItem(0);
//        rcvDetailVO3.setStatus(5);
//
//        List<RcvDetailVO> list= new ArrayList<>();
//        list.add(rcvDetailVO);
//        list.add(rcvDetailVO2);
//        list.add(rcvDetailVO3);
//        list = list.stream().filter(RcvDetailVO -> RcvDetailVO.getRorderItem() != 0).collect(Collectors.toList());
//        list.forEach(i -> {
//            System.out.println("i = " + JSONUtil.toJsonPrettyStr(i));
//        });
//
//        System.out.println("i = " + i);

//        int wordByteLen = PublicUtil.getWordByteLen("用户：A98006");
//        System.out.println("wordByteLen = " + wordByteLen);
        String nextMonthDate = PublicUtil.getNextMonthDate("2025-08-01");
        System.out.println("nextMonthDate = " + nextMonthDate);
    }


  public static int testEntity(RcvDetailVO rcvDetailVO) {
        rcvDetailVO.setId(123L);
        rcvDetailVO.setRemark("测试");
        return 0;
  }


}

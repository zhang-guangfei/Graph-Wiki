package com.smc.ops.delivery.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
@Slf4j
public class OrderNoInfo {
    private Integer orderType;
    private String orderNo;
    private Integer itemNo;
    private Integer splitItem;
    private String fullOrderNo;
    private String purchaseType;
    private String strItemNo;
    private String orFullOrderNo;//原始全订单号
    private String poNo;
    private Integer poItemNo;
    private boolean isError;//是否错误单号
    private String PURCHASE_TYPES = "AKBU";


    public OrderNoInfo() {
        //new OrderNoInfo().convertJPOrder();

        this.isError = false;
    }

    /**
     * 转化日本订单号
     * jporderNo=AG000002      jpitemNo=010
     * jporderNo=AG000002-2    jpitemNo=1
     * jporderNo=AG000002-2-1  jpitemNo=1
     *
     * @param jporderNo 包含订单类型前缀 例如 AG00002
     * @param jpitemNo  例如 1
     */
    public OrderNoInfo convertJPOrder(String jporderNo, String jpitemNo) {
        if (StringUtils.isEmpty(jporderNo)) {
            this.orderType = 99;
            if (StringUtils.isNumeric(jpitemNo)) {
                jpitemNo = "1";
            }
            this.poItemNo = Integer.parseInt(jpitemNo);
            this.poNo = "";
            this.fullOrderNo = "";
            this.orderNo = "";
            this.orFullOrderNo = jporderNo + "-" + jpitemNo;
            this.isError = true;
            return this;
        }
        orFullOrderNo = jporderNo + "-" + jpitemNo;

        try {
            if ("0".equalsIgnoreCase(jpitemNo)) {
                    return convertFullOrderNo(jporderNo);
            }

            if (this.PURCHASE_TYPES.contains(jporderNo.substring(0, 1))) {
                //采购类型
                this.purchaseType = jporderNo.substring(0, 1);
                jporderNo = jporderNo.substring(1);
                // this.orderType = convertOrderType(purchaseType);
            }

            this.poNo = jporderNo;
            this.poItemNo = Integer.parseInt(jpitemNo);

            //是否为DR/CR订单
            if (jporderNo.endsWith("-DR") || jporderNo.endsWith("-CR")) {
                String endStr = jporderNo.substring(jporderNo.length() - 3);
                jporderNo = jporderNo.substring(0, jporderNo.length() - 3);
                if (endStr.equalsIgnoreCase("-DR")) {
                    this.orderType = 24;
                } else {
                    this.orderType = 25;
                }
            }

            if (jporderNo.contains("-")) {
                //ops带-的订单号
                this.splitBySeparator(jporderNo);
                this.poNo = jporderNo;
                this.poItemNo = Integer.parseInt(jpitemNo);
                return this;
            }
            //旧系统不带-的订单
            if (jporderNo.length() <= 7) {
                this.orderNo = jporderNo;
            } else {
                //Edit by A78027, 2023-5-6 for bug-10674
                //ops新订单，也存在不带-
                // Edit by A78027, 2023-9-1 for bug-11977
                //20230901 旧订单号去掉前面的CN,SH,按后7位截取
                if(jporderNo.length()==9 && (jporderNo.startsWith("CN") || jporderNo.startsWith("SH")
                        || jporderNo.startsWith("OT") || jporderNo.startsWith("ST")))
                {
                    this.orderNo = jporderNo.substring(jporderNo.length() - 7);
                }
                else
                {
                 // this.orderNo=jporderNo;
                    this.splitBySeparator(jporderNo+"-"+ jpitemNo);
                    this.poNo = jporderNo;
                    this.poItemNo = Integer.parseInt(jpitemNo);
                    return this;
                }
            }
            convertStrItemNo(jpitemNo);
            //旧系统单号不加-
            this.fullOrderNo = this.orderNo + this.strItemNo;
            splitByPos(this.fullOrderNo);
            this.poNo = this.orderNo;
        } catch (Exception ex) {
            log.error(jporderNo);
            log.error(jpitemNo);
            ex.printStackTrace();

            this.orderNo = jporderNo;
            this.itemNo = Integer.parseInt(jpitemNo);
            this.fullOrderNo = jporderNo + "-" + jpitemNo;
            this.poNo = jporderNo;
            this.poItemNo = this.itemNo;
            this.orFullOrderNo = jporderNo + "-" + jpitemNo;
            this.orderType = 0;
            this.isError = true;
        } finally {
            if (this.orderType == null) {
                this.orderType = 99;
            }
        }
        return this;
    }

    public OrderNoInfo convertCNOrder(String cnOrderNo) {
        cnOrderNo = cnOrderNo.trim();
        if (cnOrderNo.contains("-")) {
            cnOrderNo = cnOrderNo.substring(0, cnOrderNo.length() - 1);
            this.splitBySeparator(cnOrderNo);
            return this;
        } else if (cnOrderNo.length() == 10) {
            splitByPos(cnOrderNo);
        } else {
            this.orderNo = cnOrderNo;
            this.itemNo = 0;
            this.poNo = poNo;
            this.poItemNo = this.itemNo;
            this.fullOrderNo = cnOrderNo;
            this.orFullOrderNo = cnOrderNo;
        }
        return this;
    }

    /**
     * 可以转化单号
     * <p>
     * poNo=G12345678-20   itemNo=1
     * poNo=9900000      itemNo=010
     * poNo=9900000010   itemNo=null
     *
     * @param poNo
     * @param itemNo new OrderNoInfo().convertPOOrder("G123456-10-4","1")
     *               OrderNoInfo(orderType=null, orderNo=G123456, itemNo=10, splitItem=4, fullOrderNo=G123456-10-4, purchaseType=null, strItemNo=010, orFullOrderNo=null, poNo=G123456-10-4, poItemNo=1, isError=false)
     * @return
     */
    public OrderNoInfo convertPOOrder(String poNo, String itemNo) {
        if (poNo.contains("-")) {
            this.fullOrderNo = poNo;
            this.splitBySeparator(poNo);
            this.poItemNo = Integer.parseInt(itemNo);
            this.poNo=poNo;
            this.orFullOrderNo = poNo;
        } else {
            if (poNo.length() == 10) {
                splitByPos(poNo);
            } else {
                this.orderNo = poNo;
                this.itemNo = Integer.parseInt(itemNo);
                this.poNo = poNo;
                this.poItemNo = this.itemNo;
                this.strItemNo = itemNo;
                convertStrItemNo(this.strItemNo);
                if(this.orderNo.length()==7)
                {
                    this.fullOrderNo = this.orderNo + this.strItemNo;
                }
                else {
                    this.fullOrderNo=this.orderNo+"-"+ this.itemNo;
                }
                this.orFullOrderNo = poNo;
            }
        }
        return this;
    }

    /**
     * orderNo：9910000851，ItemNo：2，splitItem：0 -->9910000851-2
     * orderNo：9910000851，ItemNo：2，splitItem：1 -->9910000851-2-1
     * orderNo：9910000851-2，ItemNo：0，splitItem：0 -->9910000851-2
     * @param orderNo
     * @param ItemNo
     * @param splitItem
     * @return
     */
   public OrderNoInfo createFullOrderNo(String orderNo,Integer ItemNo,Integer splitItem){
       if (orderNo.contains("-")) {
           convertPOOrder(orderNo, ItemNo.toString());
       }else {
           this.orderNo = orderNo;
           this.itemNo = ItemNo;
           this.splitItem = splitItem;
           if (Objects.nonNull(splitItem) && splitItem.compareTo(0) > 0) {
               this.fullOrderNo = String.join("-", orderNo, ItemNo.toString(), splitItem.toString());
           } else if (Objects.nonNull(ItemNo) && ItemNo.compareTo(0) > 0) {
               this.fullOrderNo = String.join("-", orderNo, ItemNo.toString());
           } else{
               this.fullOrderNo=orderNo;
           }
       }
       return this;
   }
    /**
     * 可以解析订单号：
     * G123456-12-1
     * G123456010
     *
     * @param fullOrderNo
     * @return
     */
    public OrderNoInfo convertFullOrderNo(String fullOrderNo) {

        fullOrderNo = fullOrderNo.trim();
        if (fullOrderNo.contains("-")) {
            splitBySeparator(fullOrderNo);
        } else {
            splitByPos(fullOrderNo);
        }
        return this;
    }

    /**
     * 10003856-4-2-1
     *
     * @param fullPoNo
     * @return
     */
    public OrderNoInfo convertFullPONo(String fullPoNo) {
        fullPoNo = fullPoNo.trim();
        if (fullPoNo.length() == 10) {
            return convertFullOrderNo(fullPoNo);
        }
        if (fullPoNo.contains("-")) {
            int lastIndex = fullPoNo.lastIndexOf("-");
            String poNo = fullPoNo.substring(0, lastIndex);
            String poItemNo = fullPoNo.substring(lastIndex + 1, fullPoNo.length());
            return convertPOOrder(poNo, poItemNo);
        }
        return convertFullOrderNo(fullPoNo);
    }

    /**
     * 项号补位
     *
     * @param strItemNo
     */
    private void convertStrItemNo(String strItemNo) {
        if (!StringUtils.isNotEmpty(strItemNo)) {
            this.strItemNo = "000";
            this.itemNo = 0;
            return;
        }
        this.itemNo = Integer.parseInt(strItemNo);
        if (this.itemNo < 10) {
            this.strItemNo = "00" + itemNo.toString();
            return;
        }
        if (this.itemNo < 100) {
            this.strItemNo = "0" + itemNo.toString();
            return;
        }
        this.strItemNo = itemNo.toString();
    }

    /**
     * 拆分10位订单号
     *
     * @param no
     */
    private void splitByPos(String no) {
        if (no.length() == 10) {
            this.orderNo = no.substring(0, 7);
            this.strItemNo = no.substring(7, 10);
            this.itemNo = Integer.parseInt(this.strItemNo);
            //旧单号不加-
            this.fullOrderNo = no;
            this.poNo = this.orderNo;
            this.poItemNo = this.itemNo;
            this.orFullOrderNo = no;
            //G1234567812  拆成 项号81 拆分项2
            if (!this.orderNo.startsWith("99")) {
                if (!no.endsWith("0")) {
                    this.splitItem = this.itemNo % 10;
                }
                this.itemNo = (this.itemNo - this.itemNo % 10) / 10;
            }
        } else {
            this.isError = true;
            this.fullOrderNo = no;
            this.poNo = no;
            this.poItemNo = 1;
            this.orderNo = no;
            this.itemNo = 0;
            this.strItemNo = "0";
        }
    }

    /**
     * 按分隔符分隔
     *
     * @param poNo
     */
    private void splitBySeparator(String poNo) {
        String[] arrs = poNo.split("-");

        if (arrs.length == 2) {
            if (StringUtils.isNotBlank(arrs[1])) {
                if (StringUtils.isNumeric(arrs[1])) {
                    this.orderNo = arrs[0];
                    this.itemNo = Integer.parseInt(arrs[1]);
                    this.fullOrderNo = this.orderNo + "-" + this.itemNo;
                    this.poNo = this.fullOrderNo;
                    this.poItemNo = 1;
                    convertStrItemNo(this.itemNo.toString());
                } else {
                    this.isError = true;
                }
            }
        }
        if (arrs.length == 3) {
            if (arrs[0].length() >= 7) {
                if (StringUtils.isNumeric(arrs[1]) && StringUtils.isNumeric(arrs[2])) {
                    this.orderNo = arrs[0];
                    this.itemNo = Integer.parseInt(arrs[1]);
                    this.splitItem = Integer.parseInt(arrs[2]);
                    this.fullOrderNo = this.orderNo + "-" + this.itemNo.toString() + "-" + this.splitItem.toString();
                    this.poNo = this.fullOrderNo;
                    this.poItemNo = 1;
                } else {
                    this.isError = true;
                }
            } else {
                if (StringUtils.isNumeric(arrs[2])) {
                    this.orderNo = arrs[0] + "-" + arrs[1];
                    this.itemNo = Integer.parseInt(arrs[2]);
                    this.fullOrderNo = this.orderNo + "-" + this.itemNo;
                    this.poNo = this.fullOrderNo;
                    this.poItemNo = 1;
                    convertStrItemNo(this.itemNo.toString());
                } else {
                    this.isError = true;
                }
            }
        }

        if (arrs.length == 1 || arrs.length>3 || this.isError) {
            this.isError = true;
            this.orderNo = poNo;
            this.itemNo = 0;
            this.strItemNo = "0";
            this.orderType = 99;
            this.fullOrderNo = poNo;
            this.poNo = poNo;
            this.poItemNo = 1;
        }

    }

    private int convertOrderType(String jpPOType) {
        int orderType;
        switch (jpPOType) {
            case "A":
                orderType = 1;
                break;
            case "B":
                orderType = 20;
                break;
            case "K":
                orderType = 20;
                break;
            case "U":
                orderType = 20;
                break;
            default:
                orderType = 0;
                break;
        }
        return orderType;
    }

    // 判断 '-' 分隔符出现的次数
    public static int showCountByStr(String str) {
        int oldLength = str.length();
        String newStr = str.replaceAll("-", "");
        int newLength = newStr.length();
        return oldLength - newLength;
    }

    public static void main(String[] args) {
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullPONo("111122221-1-1");

        // OrderNoInfo orderNoInfo2 = new OrderNoInfo().convertFullPONo("11270078-2-3 ");


        System.out.println("orderNoInfo = " + JSON.toJSONString(orderNoInfo));

        // System.out.println("orderNoInfo2 = " + JSON.toJSONString(orderNoInfo2));

    }
}


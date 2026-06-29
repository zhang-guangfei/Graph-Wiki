package com.smc.smccloud.model.adapter.order;


import com.smc.smccloud.model.order.OrderNoInfo;
import lombok.Data;

/**
 * @author wuweidong
 * @date 2022/11/04 10:13
 */
@Data
public class POOrderNODTO {

    /**
     * 关务订单号
     */
   private  String orderNo;

    /**
     * 关务项号
     */
   private  String orderItem;
//    /**
//     * 采购单
//     */
//    private String poNo;
//    /**
//     * 采购项
//     */
//    private Integer lineItem;
//    public POOrderNODTO(String orderNO,String orderItem)
//    {
//        this.orderNO=orderNO;
//        this.orderItem=orderItem;
//        this.convertPOorderNo();
//    }
//    public String convertPOorderNo() {
//        OrderNoInfo orderNoInfo=new OrderNoInfo().convertJPOrder(this.orderNO,this.orderItem);
//      this.poNo=orderNoInfo.getPoNo();
//      this.lineItem=orderNoInfo.getPoItemNo();
//      return  orderNoInfo.getFullOrderNo();
//    }
}

package com.smc.smccloud.model.ordermodify;

import com.smc.smccloud.model.receiveorder.ReceiveOrderVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author edp02 @Date 2021/11/5 10:53
 */
@Data
public class ModifyRcvOrderDTO implements Serializable {

    private static final long serialVersionUID = -8564429559682753898L;

    private String modifyType;

    private String modifyReason;

    private String modifyRemark;

    private List<ReceiveOrderVO> modifyItems;
}

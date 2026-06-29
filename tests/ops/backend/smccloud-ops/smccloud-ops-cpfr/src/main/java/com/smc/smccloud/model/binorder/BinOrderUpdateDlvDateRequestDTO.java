package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/11/16 14:43
 */
@Data
public class BinOrderUpdateDlvDateRequestDTO {

    private Long calcId;

    private String transType;

    private Date kdlvDate;

    private Date udlvDate;

    private Date bdlvDate;

    private Date xdlvDate;

    private List<String> modelNos;

    private List<Long> ids;
}

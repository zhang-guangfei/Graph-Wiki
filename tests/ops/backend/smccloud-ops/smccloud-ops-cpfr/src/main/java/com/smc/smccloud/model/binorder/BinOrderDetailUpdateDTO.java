package com.smc.smccloud.model.binorder;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BinOrderDetailUpdateDTO {
    
    private Long id;
    private String modelNo;
    private String orderType;
    private ArrayList<BinOrderDetailVO> items;

}

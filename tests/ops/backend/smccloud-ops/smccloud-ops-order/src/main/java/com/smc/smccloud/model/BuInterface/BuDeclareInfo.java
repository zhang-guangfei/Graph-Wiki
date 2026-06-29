package com.smc.smccloud.model.BuInterface;

import lombok.Data;

import java.util.Date;

/**
 * @Author edp02 @Date 2022/4/22 14:55
 */
@Data
public class BuDeclareInfo {

    private  String customsDeclarationNo;

    private  String customsDeclarationPage;

    private Date importDate;

    private Date customsDate;

    private  String entryPort;
}

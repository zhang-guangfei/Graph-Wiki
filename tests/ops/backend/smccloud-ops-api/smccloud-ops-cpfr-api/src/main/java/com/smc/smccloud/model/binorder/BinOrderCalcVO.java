package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BinOrderCalcVO {

        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date calcTime;

        private Long id;

        private String calcPsn;

        private String confirmPsn;

        private Boolean gss;

        private Integer status;

        private String createUser;

        private  String updateUser;

        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createTime;

        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date updateTime;

        private Integer calcType;

        private String warehouseCode;

        private String custoemrNo;

        private Long propertyId;

        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date calcFinishTime;
}

package com.svnplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Changelist操作请求DTO
 */
@Data
public class ChangelistRequest {

    /**
     * changelist名称
     */
    private String changelist;

    /**
     * 要操作的文件路径列表
     */
    private List<String> files;
}

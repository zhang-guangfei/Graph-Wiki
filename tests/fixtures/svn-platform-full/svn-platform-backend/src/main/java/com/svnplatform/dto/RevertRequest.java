package com.svnplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Revert请求DTO
 */
@Data
public class RevertRequest {

    /**
     * 要还原的文件路径列表
     */
    private List<String> files;
}

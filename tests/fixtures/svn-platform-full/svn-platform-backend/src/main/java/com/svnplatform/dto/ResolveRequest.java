package com.svnplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * 冲突解决请求DTO
 */
@Data
public class ResolveRequest {

    /**
     * 要解决冲突的文件路径列表
     */
    private List<String> files;

    /**
     * 解决策略: working, mine-full, theirs-full, mine-conflict, theirs-conflict
     */
    private String acceptStrategy = "working";
}

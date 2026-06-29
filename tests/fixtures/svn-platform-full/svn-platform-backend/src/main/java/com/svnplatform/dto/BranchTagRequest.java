package com.svnplatform.dto;

import lombok.Data;

@Data
public class BranchTagRequest {
    private String sourceUrl;
    private String name;
    private String pathType;
    private String message;
}
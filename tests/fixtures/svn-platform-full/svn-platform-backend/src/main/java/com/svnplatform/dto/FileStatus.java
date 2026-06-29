package com.svnplatform.dto;

import lombok.Data;

@Data
public class FileStatus {
    private String path;
    private String status;
    private String props;
    private String revision;
    private String changelist;
}

package com.svnplatform.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileStatusList {
    private List<FileStatus> files;
    private int totalFiles;
    private int modifiedCount;
    private int addedCount;
    private int deletedCount;
    private int conflictedCount;
    private int unversionedCount;
}

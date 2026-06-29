package com.svnplatform.dto;

import lombok.Data;

@Data
public class LocalRepoStatus {
    private String localPath;
    private String url;
    private String relativeUrl;
    private String branch;
    private String repositoryRoot;
    private String repositoryUuid;
    private Long revision;
    private Long latestRemoteRevision;
    private String lastAuthor;
    private String lastDate;
    private String nodeKind;
    private String schedule;
    private String workingCopyFormat;
    private String overallStatus;
}

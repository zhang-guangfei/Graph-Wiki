package com.svnplatform.util;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public final class ConflictArtifactLocator {

    private ConflictArtifactLocator() {
    }

    public static File resolveMineFile(File baseDir, String filePath, String preferredPath) {
        File preferredFile = resolvePreferredFile(baseDir, preferredPath);
        if (preferredFile != null) {
            return preferredFile;
        }

        File workingFile = resolveMergedFile(baseDir, filePath);
        if (workingFile == null) {
            return null;
        }

        File mineFile = resolveSiblingByExactName(workingFile, workingFile.getName() + ".mine");
        if (mineFile != null) {
            return mineFile;
        }

        return resolveSiblingByExactName(workingFile, workingFile.getName() + ".working");
    }

    public static File resolveBaseFile(File baseDir, String filePath, String preferredPath) {
        File preferredFile = resolvePreferredFile(baseDir, preferredPath);
        if (preferredFile != null) {
            return preferredFile;
        }

        File workingFile = resolveMergedFile(baseDir, filePath);
        if (workingFile == null) {
            return null;
        }

        return resolveSiblingByPrefix(workingFile, workingFile.getName() + ".merge-left.r");
    }

    public static File resolveTheirsFile(File baseDir, String filePath, String preferredPath) {
        File preferredFile = resolvePreferredFile(baseDir, preferredPath);
        if (preferredFile != null) {
            return preferredFile;
        }

        File workingFile = resolveMergedFile(baseDir, filePath);
        if (workingFile == null) {
            return null;
        }

        File mergeRightFile = resolveSiblingByPrefix(workingFile, workingFile.getName() + ".merge-right.r");
        if (mergeRightFile != null) {
            return mergeRightFile;
        }

        return resolveSiblingByPrefix(workingFile, workingFile.getName() + ".r");
    }

    public static File resolveMergedFile(File baseDir, String filePath) {
        if (baseDir == null || isBlank(filePath)) {
            return null;
        }

        File mergedFile = new File(baseDir, filePath);
        return mergedFile.exists() ? mergedFile : null;
    }

    private static File resolvePreferredFile(File baseDir, String preferredPath) {
        if (isBlank(preferredPath)) {
            return null;
        }

        File directFile = new File(preferredPath);
        if (directFile.exists()) {
            return directFile;
        }

        if (baseDir == null) {
            return null;
        }

        File relativeFile = new File(baseDir, preferredPath);
        return relativeFile.exists() ? relativeFile : null;
    }

    private static File resolveSiblingByExactName(File workingFile, String fileName) {
        File parentDir = workingFile.getParentFile();
        if (parentDir == null || !parentDir.exists()) {
            return null;
        }

        File sibling = new File(parentDir, fileName);
        return sibling.exists() ? sibling : null;
    }

    private static File resolveSiblingByPrefix(File workingFile, String fileNamePrefix) {
        File parentDir = workingFile.getParentFile();
        if (parentDir == null || !parentDir.exists()) {
            return null;
        }

        File[] candidates = parentDir.listFiles((dir, name) -> name.startsWith(fileNamePrefix));
        if (candidates == null || candidates.length == 0) {
            return null;
        }

        return Arrays.stream(candidates)
            .filter(File::isFile)
            .max(Comparator.comparing(File::getName))
            .orElse(null);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

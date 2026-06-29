package com.svnplatform.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConflictArtifactLocatorTest {

    @TempDir
    Path tempDir;

    @Test
    void resolvesWorkingStyleConflictArtifacts() throws IOException {
        Path workingFile = tempDir.resolve("src/Main.java");
        Files.createDirectories(workingFile.getParent());
        Files.write(workingFile, "merged".getBytes(StandardCharsets.UTF_8));
        Files.write(tempDir.resolve("src/Main.java.working"), "mine".getBytes(StandardCharsets.UTF_8));
        Files.write(tempDir.resolve("src/Main.java.merge-left.r12"), "base".getBytes(StandardCharsets.UTF_8));
        Files.write(tempDir.resolve("src/Main.java.merge-right.r13"), "theirs".getBytes(StandardCharsets.UTF_8));

        File mineFile = ConflictArtifactLocator.resolveMineFile(tempDir.toFile(), "src/Main.java", null);
        File baseFile = ConflictArtifactLocator.resolveBaseFile(tempDir.toFile(), "src/Main.java", null);
        File theirsFile = ConflictArtifactLocator.resolveTheirsFile(tempDir.toFile(), "src/Main.java", null);

        assertNotNull(mineFile);
        assertNotNull(baseFile);
        assertNotNull(theirsFile);
        assertEquals("Main.java.working", mineFile.getName());
        assertEquals("Main.java.merge-left.r12", baseFile.getName());
        assertEquals("Main.java.merge-right.r13", theirsFile.getName());
    }

    @Test
    void prefersExplicitArtifactPathWhenProvided() throws IOException {
        Path artifact = tempDir.resolve("explicit.mine");
        Files.write(artifact, "mine".getBytes(StandardCharsets.UTF_8));

        File mineFile = ConflictArtifactLocator.resolveMineFile(tempDir.toFile(), "src/Main.java", artifact.toString());

        assertNotNull(mineFile);
        assertEquals(artifact.toFile().getAbsolutePath(), mineFile.getAbsolutePath());
    }
}

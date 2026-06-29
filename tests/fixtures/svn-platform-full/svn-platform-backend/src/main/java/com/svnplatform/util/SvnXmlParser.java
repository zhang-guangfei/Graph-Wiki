package com.svnplatform.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * SVN XML输出解析工具
 */
@Slf4j
@Component
public class SvnXmlParser {

    private final XmlMapper xmlMapper = new XmlMapper();

    /**
     * 解析svn info --xml的输出
     */
    public Map<String, String> parseInfo(String xml) {
        Map<String, String> info = new HashMap<>();
        try {
            // 简单解析关键字段
            info.put("url", extractTag(xml, "url"));
            info.put("relative-url", extractTag(xml, "relative-url"));
            info.put("repository-root", extractTag(xml, "root"));
            info.put("repository-uuid", extractTag(xml, "uuid"));
            info.put("revision", extractAttribute(xml, "commit", "revision"));
            info.put("last-author", extractTag(xml, "author"));
            info.put("last-date", extractTag(xml, "date"));

            // 从entry节点获取revision
            String entryRevision = extractAttribute(xml, "entry", "revision");
            if (entryRevision != null) {
                info.put("entry-revision", entryRevision);
            }
        } catch (Exception e) {
            log.error("Failed to parse SVN info XML", e);
        }
        return info;
    }

    /**
     * 解析svn status --xml的输出
     * SVN status XML中changelist信息以 <changelist name="xxx"> 节点包裹
     */
    public List<Map<String, String>> parseStatus(String xml) {
        List<Map<String, String>> statusList = new ArrayList<>();
        try {
            // 先提取changelist信息
            Map<String, String> pathToChangelist = new HashMap<>();
            String[] clSections = xml.split("<changelist");
            for (int i = 1; i < clSections.length; i++) {
                String clSection = clSections[i];
                String clName = extractAttributeValue(clSection, "name");
                if (clName != null) {
                    // 解析该changelist下的entry
                    String[] clEntries = clSection.split("<entry");
                    for (int j = 1; j < clEntries.length; j++) {
                        // 如果遇到</changelist>则停止
                        if (clEntries[j].contains("</changelist>")) {
                            String path = extractAttributeValue(clEntries[j].split("</changelist>")[0], "path");
                            if (path != null) pathToChangelist.put(path, clName);
                            break;
                        }
                        String path = extractAttributeValue(clEntries[j], "path");
                        if (path != null) pathToChangelist.put(path, clName);
                    }
                }
            }

            // 解析所有entry节点
            String[] entries = xml.split("<entry");
            for (int i = 1; i < entries.length; i++) {
                String entry = entries[i];
                Map<String, String> item = new HashMap<>();

                // 获取path属性
                String path = extractAttributeValue(entry, "path");
                item.put("path", path);

                // 获取wc-status的item属性（状态）
                String status = extractAttributeFromTag(entry, "wc-status", "item");
                item.put("status", status);

                // 获取props属性
                String props = extractAttributeFromTag(entry, "wc-status", "props");
                item.put("props", props);

                // 获取revision
                String revision = extractAttributeFromTag(entry, "wc-status", "revision");
                item.put("revision", revision);

                // 添加changelist信息
                if (path != null && pathToChangelist.containsKey(path)) {
                    item.put("changelist", pathToChangelist.get(path));
                }

                statusList.add(item);
            }
        } catch (Exception e) {
            log.error("Failed to parse SVN status XML", e);
        }
        return statusList;
    }

    /**
     * 解析svn log --xml的输出
     */
    public List<Map<String, Object>> parseLog(String xml) {
        List<Map<String, Object>> logList = new ArrayList<>();
        try {
            String[] entries = xml.split("<logentry");
            for (int i = 1; i < entries.length; i++) {
                String entry = entries[i];
                Map<String, Object> item = new HashMap<>();

                String revision = extractAttributeValue(entry, "revision");
                item.put("revision", revision);
                item.put("author", extractTag(entry, "author"));
                item.put("date", extractTag(entry, "date"));
                item.put("message", extractTag(entry, "msg"));

                // 解析paths（如果有verbose输出）
                if (entry.contains("<paths>")) {
                    List<Map<String, String>> paths = parsePaths(entry);
                    item.put("paths", paths);
                }

                logList.add(item);
            }
        } catch (Exception e) {
            log.error("Failed to parse SVN log XML", e);
        }
        return logList;
    }

    private List<Map<String, String>> parsePaths(String entry) {
        List<Map<String, String>> paths = new ArrayList<>();
        String pathsSection = extractBetween(entry, "<paths>", "</paths>");
        if (pathsSection != null) {
            String[] pathEntries = pathsSection.split("<path");
            for (int j = 1; j < pathEntries.length; j++) {
                Map<String, String> pathItem = new HashMap<>();
                String pathEntry = pathEntries[j];
                String action = extractAttributeValue(pathEntry, "action");
                String kind = extractAttributeValue(pathEntry, "kind");
                String pathValue = extractBetween(pathEntry, ">", "</path>");
                pathItem.put("action", action);
                pathItem.put("kind", kind);
                pathItem.put("path", pathValue);
                paths.add(pathItem);
            }
        }
        return paths;
    }

    // === Helper methods for XML parsing ===

    private String extractTag(String xml, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        int start = xml.indexOf(startTag);
        if (start == -1) return null;
        start += startTag.length();
        int end = xml.indexOf(endTag, start);
        if (end == -1) return null;
        return xml.substring(start, end).trim();
    }

    private String extractAttribute(String xml, String tagName, String attrName) {
        String tagStart = "<" + tagName;
        int tagIndex = xml.indexOf(tagStart);
        if (tagIndex == -1) return null;
        String afterTag = xml.substring(tagIndex);
        return extractAttributeValue(afterTag, attrName);
    }

    private String extractAttributeValue(String text, String attrName) {
        String pattern = attrName + "=\"";
        int start = text.indexOf(pattern);
        if (start == -1) return null;
        start += pattern.length();
        int end = text.indexOf("\"", start);
        if (end == -1) return null;
        return text.substring(start, end);
    }

    private String extractAttributeFromTag(String text, String tagName, String attrName) {
        String tagStart = "<" + tagName;
        int tagIndex = text.indexOf(tagStart);
        if (tagIndex == -1) return null;
        int tagEnd = text.indexOf(">", tagIndex);
        if (tagEnd == -1) return null;
        String tagContent = text.substring(tagIndex, tagEnd);
        return extractAttributeValue(tagContent, attrName);
    }

    private String extractBetween(String text, String start, String end) {
        int startIndex = text.indexOf(start);
        if (startIndex == -1) return null;
        startIndex += start.length();
        int endIndex = text.indexOf(end, startIndex);
        if (endIndex == -1) return null;
        return text.substring(startIndex, endIndex);
    }
}

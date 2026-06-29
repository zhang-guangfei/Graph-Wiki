package com.smc.smccloud.core.utils;

import com.smc.smccloud.core.exception.BusinessException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.String.format;

/**
 * @Author edp02 @Date 2021/12/27 16:09
 */
public class FileUtil {

    /**
     *  从resource目录下获取模板
     * @param path "ex: 'templates/**'"
     * @return
     * @throws IOException
     */
    public static InputStream getTemplate(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        // 打jar包后Spring无法使用resource.getFile()访问JAR中的路径的文件，必须使用输入流resource.getInputStream()
        InputStream inputStream = null;
        try {

            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(format("Get template file from %s failed: %s", path, e));
        }
        return inputStream;
    }

    /**
     *
     * @param fileName 文件名
     * @param inputStream
     * @return
     */
    public static MultipartFile getMultipartFile(String fileName, InputStream inputStream) {
        String fieldName = "file"; // @RequestPart("file")

        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem(fieldName,
                MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);

        try {
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(inputStream, os);
        } catch (IOException e) {
            throw new RuntimeException(format("Get MultipartFile from %s failed: %s", fileName, e));
        }

        return new CommonsMultipartFile(fileItem);
    }

    public static MultipartFile[] getMultipartFile(String[] fileNames, InputStream[] inputStreams) {
        String fieldName = "files"; // @RequestPart("files")
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i=0; i < inputStreams.length; i++) {
            String fileName = fileNames[i];
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem(fieldName,
                    MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
            try {
                InputStream is = inputStreams[i];
                OutputStream os = fileItem.getOutputStream();
                IOUtils.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException(format("Get MultipartFile[] from %s failed: %s", fileName, e));
            }
            multipartFileList.add(new CommonsMultipartFile(fileItem));
        }
        MultipartFile[] multipartFiles = new MultipartFile[multipartFileList.size()];
        return multipartFileList.toArray(multipartFiles);
    }

    /**
     * 将InputStream写入本地文件
     * @param destination 写入本地目录
     * @param input 输入流
     * @throws IOException IOException
     */
    public static void writeFileToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();

    }

    /**
     * 上传文件
     * multipartFile 需要上传的文件
     * serverPath 服务路径 /ops/files/
     * path 自定义目录 /路径/
     * @return
     */
    public static Boolean uploadFile(MultipartFile multipartFile, String serverPath, String path) {
        OutputStream os = null;
        try {
            InputStream stream = multipartFile.getInputStream();
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(serverPath + DateUtil.getYearMonthCode(new Date()) + path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + multipartFile.getOriginalFilename());
            while ((len = stream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 上传文件
     * multipartFile 需要上传的文件
     * serverPath 服务路径 /ops/files/
     * path 自定义目录 /路径/
     * @return
     */
    public static Boolean uploadFileWithStream(InputStream stream, String serverPath,String fileName) {
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len = 0;
            // 输出的文件流保存到本地文件
            File tempFile = new File(serverPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            while ((len = stream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 获取路径下所有的文件名称
     * @param path
     * @return
     */
    public static List<String> getFileNames(String path) {
        List<String> fileNames = new ArrayList<>();
        File file = new File(path);
        // 判断是否为文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果不是文件夹 获取里面的文件
                if (!files[i].isDirectory()) {
                    fileNames.add(files[i].getName());
                }
            }
        }
        return fileNames;
    }


    /**
     * 下载服务器上面的文件
     * @param path
     * @param response
     */
    public static void downloadFileToResponse(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.resetBuffer();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * 获取某个文件夹下的所有文件
     */
    public static Vector<File> getPathAllFiles(File file,Vector<File> vector){
        if(file.isFile()){//如果是文件，直接装载
            vector.add(file);
        }else{//文件夹
            File[] files = file.listFiles();
            for(File f : files){//递归
                if(f.isDirectory()){
                    getPathAllFiles(f,vector);
                }else{
                    vector.add(f);
                }
            }
        }
        return vector;
    }

    /**
     * 把某个文件路径下面的文件包含文件夹压缩到一个文件下
     * @param file
     * @param rootPath
     * @param zipoutputStream
     */
    public static void zipFileFun(File file,String rootPath,ZipOutputStream zipoutputStream) {
        //文件存在才合法
        if (file.exists()) {
            if (file.isFile()) {
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    String relativeFilePath = file.getPath().replace(rootPath + File.separator, "");
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis, 10 * 1024);
                    ZipEntry zipEntry;
                    if (!relativeFilePath.contains("\\")) {
                        zipEntry = new ZipEntry(file.getName()); //此处值不能重复，要唯一，否则同名文件会报错
                    } else {
                        zipEntry = new ZipEntry(relativeFilePath);
                    }
                    zipoutputStream.putNextEntry(zipEntry);
                    //开始写文件
                    byte[] b = new byte[10 * 1024];
                    int size = 0;
                    while ((size = bis.read(b, 0, 10 * 1024)) != -1) { // 没有读完
                        zipoutputStream.write(b, 0, size);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 读完以后关闭相关流操作
                        if (bis != null) {
                            bis.close();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (Exception e2) {
                        System.out.println("流关闭错误！");
                    }
                }
            }
        }
    }

    /**
     * 下载文件夹
     * @param response
     */
    public static void zipDownloadRelativePath(HttpServletResponse response, String filePath) throws UnsupportedEncodingException {

        String root = filePath;
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1)+".zip";
        Vector<File> fileVector = new Vector<File>();
        File file = new File(root);
        File [] subFile = file.listFiles();
        System.out.println("root path = " + root);
        if (subFile == null) {
            System.out.println(root+"路径下没有文件.");
            return;
        }
        //判断文件性质并取文件
        for(int i = 0; i<subFile.length; i++){
            if (!subFile[i].isDirectory()){
                fileVector.add(subFile[i]);
            } else {
                File [] files = subFile[i].listFiles();
                Vector v = FileUtil.getPathAllFiles(subFile[i],new Vector<File>());
                fileVector.addAll(v);
            }
        }
        response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes(),"iso-8859-1") + "\""); // 如果有中文需要转码
        OutputStream out = null;
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;

        String zipBasePath = filePath;
        String zipFilePath = zipBasePath+"/"+fileName;
        System.out.println("生成zipFilePath压缩文件路径 = " + zipFilePath);
        File zipFile = new File(zipFilePath);
        try {
            if(!zipFile.exists()){
                zipFile.createNewFile();
            }
            out = response.getOutputStream();
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            //循环文件，一个一个按顺序压缩
            for(int i = 0;i< fileVector.size();i++){
                FileUtil.zipFileFun(fileVector.get(i),root,zos);
            }
            // 压缩完成以后必须要在此处执行关闭 否则下载文件会有问题
            if(zos != null){
                zos.closeEntry();
                zos.close();
                zos = null;
            }
            byte[] bt = new byte[10*1024];
            int size = 0;
            bis = new BufferedInputStream(new FileInputStream(zipFilePath),10*1024);
            while((size=bis.read(bt,0,10*1024)) != -1){
                out.write(bt,0,size);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {

                if(bis != null){
                    bis.close();
                }
                //避免出异常时无法关闭
                if(zos != null){
                    zos.closeEntry();
                    zos.close();
                }
                if(out != null){
                    out.close();
                }
                if(zipFile.exists()){
                    zipFile.delete(); // 删除
                }
            } catch (Exception e2) {
                System.out.println("流关闭出错！");
            }

        }
    }


    /**
     * @param input 输入流
     * @param count 复制个数
     * @return 返回
     */
    public static List<InputStream> cloneInputStream(InputStream input, Integer count) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        List<InputStream> inputStreamList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inputStreamList.add(new ByteArrayInputStream(baos.toByteArray()));
        }
        return inputStreamList;
    }

    /**
     * 文件复制
     * @param resourceFilePath 源文件
     * @param newFilePath 复制的新文件
     * @return
     */
    public static void copyFile(String resourceFilePath,String newFilePath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File oldfile = new File(resourceFilePath);
            fis = new FileInputStream(oldfile);
            File newfile = new File(newFilePath);
            // 获取父目录
            File fileParent = newfile.getParentFile();
            // 判断是否存在
            if (!fileParent.exists()) {
                // 创建父目录文件夹
                fileParent.mkdirs();
            }
            // 判断文件是否存在
            if (!newfile.exists()) {
                // 创建文件
                newfile.createNewFile();
            }
            fos = new FileOutputStream(newfile);
            byte[] data=new byte[1024*10];
            int len = -1;
            while((len=fis.read(data))!=-1){
                fos.write(data,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件复制异常"+e.getMessage());
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 1）判断文件是否存在，存在删除
     * 2）重新创建文件
     * @param path
     */
    public static  File checkAndCreateFile(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        // 如果文件已存在，则删掉
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            throw new BusinessException("文件创建失败: " + e.getMessage(), e);
        }
    }

    /**
     * 压缩文件
     * @param filePath
     * @param toZipFile
     */
    public static void CovertToZipFile(String  filePath,String toZipFile){
        File zipFile = new File(toZipFile);
        File fromFile = new File(filePath);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        FileCompressUtil.zipFiles(new File[]{fromFile}, zipFile);  //压缩
    }

    /**
     * 前端下载文件，用于导出文件
     * @param filePath
     * @param response
     */
    public static void exportFile(String  filePath,HttpServletResponse response) throws  Exception{
        InputStream is = new FileInputStream(filePath);
        byte[] b = new byte[1024];
        int len;
        try {
            while ((len = is.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new Exception(e);
            }
        }

    }

}

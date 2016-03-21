package com.hwsoft.model.file;

import com.google.common.io.Files;
import com.hwsoft.exception.file.FileException;
import com.hwsoft.spring.MessageSource;
import org.apache.commons.logging.Log;

import java.io.File;
import java.io.IOException;

/**
 * 用于传递文件信息
 */
public class CustomFile {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件数据
     */
    private byte[] data;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CustomFile{" +
                "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    /**
     * 文件写入
     *
     * @param customFile
     * @param messageSource
     * @param logger
     * @return
     * @throws FileException
     */
    public static String writeFile(CustomFile customFile, MessageSource messageSource ,Log logger,String fileLocationPath) throws FileException {
        String fileStorePath = fileLocationPath + customFile.getFileName();
        File file = new File(fileStorePath);

        try {
            // 创建文件
            Files.createParentDirs(file);
        } catch (IOException e) {
            logger.error("文件创建失败，请检查服务是否有权限创建路径:" + fileStorePath, e);
            e.printStackTrace();
            throw new FileException(messageSource.getMessage("file.create.file"));
        }

        try {
            // 文件写入
            Files.write(customFile.getData(), file);
        } catch (IOException e) {
            logger.error("文件写入失败", e);
            throw new FileException(messageSource.getMessage("file.write.error"));
        }
        return fileStorePath;
    }
}
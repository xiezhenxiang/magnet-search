package indi.shine.magnet.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Getter
@Setter
public class FileInfo {

    private String fileName;
    private String fileSize;

    public FileInfo(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}

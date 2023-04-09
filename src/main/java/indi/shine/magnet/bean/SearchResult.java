package indi.shine.magnet.bean;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Getter
@Setter
@ApiModel
public class SearchResult {

    private String url;
    private String size;
    private Double len;
    private String title;
    private String createTime;
    private List<FileInfo> fileInfos;
    private Double relevance;
}

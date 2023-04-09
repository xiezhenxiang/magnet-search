package indi.shine.magnet.bean.rsp;

import indi.shine.magnet.bean.SearchResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xiezhenxiang 2023/4/9
 */
@Getter
@Setter
@AllArgsConstructor
public class SearchRsp {

    private List<SearchResult> results;
    private boolean isHistory = false;
}

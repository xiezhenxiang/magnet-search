package indi.shine.magnet.bean.param;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Getter
@Setter
@ApiModel
public class SearchParam {
    private String kw;
}

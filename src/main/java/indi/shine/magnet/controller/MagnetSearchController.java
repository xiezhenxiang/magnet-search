package indi.shine.magnet.controller;

import indi.shine.common.bean.response.ReturnT;
import indi.shine.magnet.bean.param.SearchParam;
import indi.shine.magnet.bean.rsp.SearchRsp;
import indi.shine.magnet.biz.MagnetSearchBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Slf4j
@Validated
@RestController
@RequestMapping("magnet/search")
@Api(value = "响应自定义",tags = "响应自定义")
public class MagnetSearchController {

    @ApiOperation("mg搜索")
    @PostMapping
    public ReturnT<SearchRsp> search(@RequestBody SearchParam param) {
        return ReturnT.success(MagnetSearchBiz.search(param.getKw()));
    }

    @ApiOperation("mg详情")
    @GetMapping("mg")
    public ReturnT<String> search(@RequestParam("uri") String uri)  {
        return ReturnT.success(MagnetSearchBiz.getMagnet(uri));
    }
}
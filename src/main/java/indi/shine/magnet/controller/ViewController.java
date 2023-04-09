package indi.shine.magnet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiezhenxiang 2023/4/9
 */
@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}

package indi.shine.magnet.config;

import com.alibaba.fastjson.JSONObject;
import indi.shine.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Slf4j
@Component
public class ScheduleTask {

    /** 每二十分钟刷新cookie */
    @Scheduled(cron = "0 */10 * * * *")
    public void refreshCookie() {
        long startTime = System.currentTimeMillis();
        String aywcUid = genOrGetAywcUid();
        String url = "https://iknight.buzz/anti/recaptcha/v4/gen?aywcUid="+ aywcUid +"&_=" + System.currentTimeMillis();
        String token = JSONObject.parseObject(HttpUtil.sendGet(url)).getString("token");

        url = "https://bt1207ra.top/anti/recaptcha/v4/verify?token="+ token +"&aywcUid="+ aywcUid +"&costtime=" + (System.currentTimeMillis() - startTime);
        HttpUtil.sendGet(url);
        log.info("refresh cookie");
    }


    private String genOrGetAywcUid() {
        String unifyidKey = "aywcUid";
        return randomString(10) + "_" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    }

    private String randomString(Integer len) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return charSet.substring(0, len);
    }
}

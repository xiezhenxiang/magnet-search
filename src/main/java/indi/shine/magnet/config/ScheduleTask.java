package indi.shine.magnet.config;

import com.alibaba.fastjson.JSONObject;
import indi.shine.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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


        url = "https://www.javbus.com/forum/home.php?mod=spacecp&ac=credit";
        HashMap<String, String> head = new HashMap<>();
        head.put("cookie", "existmag=mag; PHPSESSID=gf1dqq5trd0jeb5mvgieh0koq1; 4fJN_2132_lastvisit=1680794023; 4fJN_2132_saltkey=iqeffzwL; 4fJN_2132_seccodecSAG98anAzXN=26389.00a8a3db440e9a8052; 4fJN_2132_lastcheckfeed=492493%7C1680797728; 4fJN_2132_auth=c0198SXJ5BCW923CZMEHJ2T0rhZwhd88G4aVQ6hVQBjNx5XpgMhcd0A9z6Y1BhjmNE5pZivCR9vow6r%2Bujb%2F%2B3pVkYY; bus_auth=298aB2aLHC6dBr5DTR3KptVoAbApG%2BxDm4YFb10jwuUHzG9Ls2kFxHFlUr%2BalpgmSQ; 4fJN_2132_st_t=492493%7C1680797731%7C49f11e29264bfeb1b88f5a7115baa285; 4fJN_2132_forum_lastvisit=D_2_1680797731; 4fJN_2132_visitedfid=36D2; 4fJN_2132_nofavfid=1; 4fJN_2132_st_p=492493%7C1680799912%7Cbdec971abf643b5a1319c7ebbdc3e7c7; 4fJN_2132_viewid=tid_67547; 4fJN_2132_lip=47.240.65.214%2C1680799387; 4fJN_2132_ulastactivity=beb0VWxXopxtpLwV%2BZ6BZmD8wHoKJHKtI04PtLdhwvqYoW3c77Im");
        HttpUtil.setProxyHost("127.0.0.1:10809");
        String rs = HttpUtil.sendGet(url, head);
        HttpUtil.setProxyHost(null);
        String lic = Jsoup.parse(rs).select("#ct > div.mn > div > ul.creditl.mtm.bbda.cl > li:nth-child(2)").text();
        log.info("里程：{}", lic);
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

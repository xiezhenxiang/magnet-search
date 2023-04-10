package indi.shine.magnet.config;

import indi.shine.magnet.util.MongoUtil;
import indi.shine.web.util.HttpUtil;

/**
 * @author xiezhenxiang 2022/10/9
 */
public class EnvConfig {

    private static final String MONGO_ADDRESS = "139.196.94.148:19130";
    public static final MongoUtil MONGO_UTIL = MongoUtil.getInstance(MONGO_ADDRESS, "root", "root@shine");

    private static final String STOCK_K_URL = "https://18.push2his.eastmoney.com/api/qt/stock/kline/get?secid=%s.%s&ut=fa5fd1943c7b386f172d6893dbfba10b" +
            "&fields1=f1,f2,f3,f4,f5,f6&fields2=f51,f52,f53,f54,f55,f56,f57,f58,f59,f60," +
            "f61&klt=101&fqt=0&end=20500101&lmt=%s";

    static {
        HttpUtil.setReadTimeout(10000);
        HttpUtil.setRetryNum(3);
    }
}

package indi.shine.magnet.biz;

import com.mongodb.client.MongoCursor;
import indi.shine.magnet.bean.FileInfo;
import indi.shine.magnet.bean.SearchResult;
import indi.shine.magnet.bean.rsp.SearchRsp;
import indi.shine.web.util.HttpUtil;
import indi.shine.web.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static indi.shine.magnet.config.EnvConfig.MONGO_UTIL;

/**
 * @author xiezhenxiang 2023/4/8
 */
@Slf4j
public class MagnetSearchBiz {

    public static final String MAGNET_DB = "magnet";
    public static final String SEARCH_HISTORY_TB = "search_history";

    public static SearchRsp search(String kw) {
        log.info("search: {}", kw);
        List<SearchResult> ls = new ArrayList<>();

        for (int page = 1; page <= 5; page++) {
            String url = "https://bt1207ra.top/search?keyword=" + kw + "&sos=relevance&sofs=all&sot=all&soft=all&som=exact&p=" + page;
            Map<String, String> headMap = new HashMap<>();
            headMap.put("referer", "https://bt1207ra.top");
            String rs = HttpUtil.sendGet(url, headMap);
            Document root = Jsoup.parse(rs);
            Elements elements = root.select("body > div > div:nth-child(6) > div.col-md-6 > ul");
            if (elements.isEmpty()) {
                break;
            }
            for (Element ul : elements) {
                String title = ul.select("li:nth-child(1) > a").text();
                String detailUrl = "https://bt1207ra.top" + ul.select("li:nth-child(1) > a").attr("href");
                List<FileInfo> fileLs = new ArrayList<>();
                for (int i = 2; i <= 12; i ++) {
                    Elements li = ul.select("li:nth-child(" + i + ")");
                    if (li.isEmpty()) {
                        break;
                    }
                    String fileName = li.select("span:nth-child(1)").text().replace("&nbsp", "");
                    String fileSize = li.select("span:nth-child(2)").text();
                    fileLs.add(new FileInfo(fileName, fileSize));
                }
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(title);
                searchResult.setFileInfos(fileLs.subList(0, fileLs.size() -1));
                FileInfo endLine = fileLs.get(fileLs.size() - 1);
                searchResult.setCreateTime(endLine.getFileName());
                searchResult.setSize(endLine.getFileSize());
                searchResult.setUrl(detailUrl);
                ls.add(searchResult);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        resultSort(kw, ls);
        SearchRsp rsp = new SearchRsp(ls, false);
        saveHistory(kw, rsp);
        return rsp;
    }

    private static void saveHistory(String kw, SearchRsp rsp) {
        kw = kw.toLowerCase();
        org.bson.Document doc = new org.bson.Document("_id", kw);
        MongoCursor<org.bson.Document> cursor = MONGO_UTIL.find(MAGNET_DB, SEARCH_HISTORY_TB, doc);
        if (cursor.hasNext()) {
            rsp.setHistory(true);
        } else {
            MONGO_UTIL.insertOne(MAGNET_DB, SEARCH_HISTORY_TB, doc);
        }
    }

    private static void resultSort(String kw, List<SearchResult> ls) {
        kw = kw.toLowerCase().replace(" ", "");
        for (SearchResult result : ls) {
            String title = result.getTitle().toLowerCase().replace(" ", "");
            boolean allMath = true;
            for (int i = 0; i < kw.length(); i++) {
                if (!title.contains(String.valueOf(kw.charAt(i)))) {
                    allMath = false;
                    break;
                }
            }
            double relevance = allMath ? 1 : 0;
            relevance += Math.min(kw.length(), title.length()) * 1.0 / Math.max(kw.length(), title.length());
            result.setRelevance(relevance);
            String[] arr = result.getSize().split(" ");
            double len = Double.parseDouble(arr[0]);
            if (arr[1].contains("G")) {
                len = len * 1024;
            }
            result.setLen(len);
        }
        ls.sort((a, b) -> b.getRelevance().compareTo(a.getRelevance()));
        int index = 0;
        for (int i = 0; i < 10 && i < ls.size(); i++) {
            if (ls.get(i).getRelevance() >= 1 ) {
                index = i + 1;
            } else {
                break;
            }
        }
        if (index > 0) {
            List<SearchResult> topLs = new ArrayList<>(ls.subList(0, index));
            List<SearchResult> tailLs = new ArrayList<>();
            if (index < ls.size()) {
                tailLs.addAll(ls.subList(index, ls.size()));
            }
            topLs.sort((a, b) -> b.getLen().compareTo(a.getLen()));
            ls.clear();
            ls.addAll(topLs);
            ls.addAll(tailLs);
        }
    }

    public static String getMagnet(String url) {
        HashMap<String, String> headMap = new HashMap<>();
        String refer = "https://bt1207ra.top/search?keyword=开心家族&sos=relevance&sofs=all&sot=all&soft=all&som=exact&p=1";
        headMap.put("referer", refer);
        String rs = HttpUtil.sendGet(url, headMap);
        if (StringUtil.isEmpty(rs)) {
            return "";
        }
        Document root = Jsoup.parse(rs);
        return root.select("#magnet").text();
    }


}

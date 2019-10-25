package cn.aybss.blog.util;

import cn.aybss.blog.model.dto.BlogConst;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Commons {

    private static final String[] ICONS = { "bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code",
            "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock" };

    public static String show_icon(int id){
        return ICONS[id % ICONS.length];
    }

    public static String show_categories(String categorysUrl, String categorysName) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if(StrUtil.isNotBlank(categorysUrl)){
            String[] categoryUrl = categorysUrl.split(",");
            String[] categoryName = categorysName.split(",");
            int i = 0;
            for(String url : categoryUrl){
                sb.append("<a href=\"/category/" + URLEncoder.encode(url, "UTF-8") + "\">" + categoryName[i] + "</a>");
                i++;
            }
        }
        return sb.toString();
    }

    public static Map<String,String> social(){
        final String prefix = "pinghsu_";
        Map<String,String> map = new HashMap<>();
        map.put("weibo", BlogConst.OPTIONS.get(prefix+"weibo"));
        map.put("zhihu",BlogConst.OPTIONS.get(prefix+"zhihu"));
        map.put("github",BlogConst.OPTIONS.get(prefix+"github"));
        map.put("twitter",BlogConst.OPTIONS.get(prefix+"twitter"));
        if(StrUtil.isNotEmpty(BlogConst.OPTIONS.get(prefix+"qq"))){
            map.put("qq", "http://wpa.qq.com/msgrd?v=3&uin="+BlogConst.OPTIONS.get(prefix+"qq")+"&site=qq&menu=yes");
        }
        if(StrUtil.isNotEmpty(BlogConst.OPTIONS.get(prefix+"email"))){
            map.put("email","mailto:"+BlogConst.OPTIONS.get(prefix+"email"));
        }
        if("rss_open".equals(BlogConst.OPTIONS.get(prefix+"rss"))) {
            map.put("rss", BlogConst.OPTIONS.get("blog_url")+"/index.xml");
        }
        return map;
    }

    public static int getYear(){
        return DateUtil.year(new Date());
    }

    public static String show_tags(String tagsUrl,String tagsName) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if(StrUtil.isNotBlank(tagsUrl)){
            String[] tagUrl = tagsUrl.split(",");
            String[] tagName = tagsName.split(",");
            int i = 0;
            for(String url:tagUrl){
                sb.append("<a href=\"/tags/" + URLEncoder.encode(url, "UTF-8") + "\">" + tagName[i] + "</a>");
                i++;
            }
        }
        return sb.toString();
    }



}

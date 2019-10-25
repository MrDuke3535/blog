package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.dto.BlogConst;
import cn.hutool.core.text.StrBuilder;

public class BaseController {

    public static String THEME = "pinghsu";

    /**
     * 根据主题名称渲染页面
     *
     * @param pageName
     *            pageName
     * @return 返回拼接好的模板路径
     */
    public String render(String pageName) {
        //加载主题
        if(BlogConst.THEME_NAME !=null) {
            THEME=BlogConst.THEME_NAME;
        }
        StrBuilder themeStr = new StrBuilder("themes/");
        themeStr.append(THEME);
        themeStr.append("/");
        return themeStr.append(pageName).toString();
    }

    public String render_404() {
        return "error/404";
    }

}

package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.*;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.dto.LogConstant;
import cn.aybss.blog.model.enums.ArticleStatus;
import cn.aybss.blog.model.enums.PostType;
import cn.aybss.blog.model.enums.ThemeStatus;
import cn.aybss.blog.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.youbenzi.mdtool.tool.MDTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/install")
public class InstallController {

    @Autowired
    private UserService userService;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LogService logService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ThemeService themeService;

    @GetMapping
    public String install(Model model){
        if(StrUtil.equals("true", BlogConst.OPTIONS.get("is_install"))){
            model.addAttribute("isInstall",true);
        }else {
            model.addAttribute("isInstall",false);
        }
        return "admin/install";
    }

    @PostMapping("/execute")
    @ResponseBody
    public JsonResult execute(@RequestParam(value = "blogName") String blogName,
                              @RequestParam(value = "blogUrl") String blogUrl,
                              @RequestParam(value = "emailUsername") String emailUsername,
                              @RequestParam(value = "userName") String username,
                              @RequestParam(value = "userDisplayName") String userDisplayName,
                              @RequestParam(value = "nowPwd") String nowPwd,
                              @RequestParam(value = "confirmPwd") String confirmPwd,
                              HttpServletRequest request){
        if(!StrUtil.equals(nowPwd,confirmPwd)){
            return new JsonResult(false,"两次输入的密码不一样，请重新输入");
        }

        try{
            //保存用户
            User user = new User();
            user.setUserDisplayName(userDisplayName);
            user.setUserName(username);
            user.setUserPwd(SecureUtil.md5(confirmPwd));
            user.setUserEmail(emailUsername);
            userService.save(user);
            //保存设置项
            Map<String,String> optionsMap = new HashMap<>();
            optionsMap.put("blog_name",blogName);
            optionsMap.put("blog_url",blogUrl);
            optionsMap.put("email_username",emailUsername);
            optionsMap.put("blog_start", DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
            optionsMap.put("attachment_location","server");
            optionsMap.put("is_install","true");
            optionsService.save(optionsMap);
            //第一篇文章
            Article article = new Article();
            article.setArticleTitle("Hello Mayday!");
            article.setArticleContentMd("# Hello Mayday!\n" +
                    "欢迎使用Mayday进行创作，删除这篇文章后赶紧开始吧。");
            article.setArticleContent(MDTool.markdown2Html(article.getArticleContentMd()));
            article.setArticleNewstime(new Date());
            article.setArticleStatus(ArticleStatus.PUBLISH.getStatus());
            article.setArticleSummary("欢迎使用Mayday进行创作，删除这篇文章后赶紧开始吧。");
            article.setArticleThumbnail("/static/img/rand/"+ RandomUtil.randomInt(1,19)+".jpg");
            article.setArticleType(ThemeStatus.THEME_NOT_ENABLED.getValue());
            article.setArticlePost(PostType.POST_TYPE_POST.getValue());
            article.setArticleComment(ThemeStatus.THEME_NOT_ENABLED.getValue());
            article.setArticleUpdatetime(new Date());
            article.setArticleUrl("hello-blog2");
            articleService.save(article,null,null);
            //添加日志
            logService.save(new Log(LogConstant.INSTALL_SUCCESS,LogConstant.SUCCESS, ServletUtil.getClientIP(request),new Date()));
            //添加菜单
            Menu menuIndex = new Menu();
            menuIndex.setMenuName("首页");
            menuIndex.setMenuUrl("/");
            menuIndex.setMenuTarget("_self");
            menuIndex.setMenuSort(1);
            menuService.save(menuIndex);
            Menu menuArchives = new Menu();
            menuArchives.setMenuName("归档");
            menuArchives.setMenuUrl("/archives");
            menuArchives.setMenuTarget("_self");
            menuArchives.setMenuSort(2);
            menuService.save(menuArchives);
            Menu menuLinks = new Menu();
            menuLinks.setMenuName("友链");
            menuLinks.setMenuUrl("/links");
            menuLinks.setMenuTarget("_self");
            menuLinks.setMenuSort(3);
            menuService.save(menuLinks);

            BlogConst.OPTIONS.clear();
            List<Options> optionsList = optionsService.selectMap();
            if(optionsList.size()>0&&!optionsList.isEmpty()){
                for(Options option:optionsList){
                    BlogConst.OPTIONS.put(option.getOptionName(),option.getOptionValue());
                }
            }

            //重置菜单
            BlogConst.MENUS.clear();
            BlogConst.MENUS = menuService.findMenus();
            //添加默认主题
            Theme theme = new Theme();
            theme.setThemeName("pinghsu");
            theme.setThemeDescribe("pinghsu");
            theme.setThemeImg("/static/img/pinghsu.jpg");
            theme.setCreateTime(new Date());
            theme.setThemeStatus(1);
            themeService.saveTheme(theme);
            BlogConst.THEME_NAME = "pinghsu";
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"系统错误");
        }
        return new JsonResult(true,"注册成功");
    }
}

package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.*;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.dto.LogConstant;
import cn.aybss.blog.model.enums.ArticleStatus;
import cn.aybss.blog.model.enums.BlogEnum;
import cn.aybss.blog.model.enums.PostType;
import cn.aybss.blog.model.enums.ThemeStatus;
import cn.aybss.blog.service.ArticleService;
import cn.aybss.blog.service.CategoryService;
import cn.aybss.blog.service.LogService;
import cn.aybss.blog.service.TagService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {


    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LogService logService;


    @GetMapping
    public String article(Model model, @RequestParam(value = "page",defaultValue = "1")int page,
                          @RequestParam(value = "limit",defaultValue = "10")int limit,
                          @RequestParam(value = "status",defaultValue = "0")int status){
        ArticleCustom articleCustom = new ArticleCustom();
        articleCustom.setArticleStatus(status);
        articleCustom.setArticlePost(PostType.POST_TYPE_POST.getValue());
        PageInfo<ArticleCustom> info = articleService.findPageArticle(page,limit,articleCustom);
        model.addAttribute("info",info);
        //已发布条数
        model.addAttribute("published",articleService.countByStatus(ArticleStatus.PUBLISH.getStatus(),PostType.POST_TYPE_POST.getValue()));
        //草稿条数
        model.addAttribute("draft",articleService.countByStatus(ArticleStatus.DRAFT.getStatus(),PostType.POST_TYPE_POST.getValue()));
        //回收站条数
        model.addAttribute("recycle",articleService.countByStatus(ArticleStatus.RECYCLE.getStatus(),PostType.POST_TYPE_POST.getValue()));
        model.addAttribute("status",status);
        return "admin/admin_article";
    }

    @GetMapping(value = "/edit")
    public String editArticle(Model model,@RequestParam(value = "article_id")Integer articleId){
        try {
            List<Category> categories = categoryService.findCategory();
            List<Tag> tags = tagService.findTags();
            ArticleCustom articleCustom = articleService.findByArticleId(articleId);
            model.addAttribute("categorys",categories);
            model.addAttribute("tags",tags);
            model.addAttribute("articleCustom",articleCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/admin_edit_article";
    }

    @GetMapping(value = "/new")
    public String newArticle(Model model){
        try {
            List<Category> categorys = categoryService.findCategory();
            List<Tag> tags = tagService.findTags();
            model.addAttribute("categorys",categorys);
            model.addAttribute("tags",tags);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/admin_new_article";
    }

    @PostMapping(value = "/new/save")
    @ResponseBody
    public JsonResult saveArticle(Article article, Long[] tags, Long[] categorys, HttpServletRequest request){
        try {
            if(StrUtil.isEmpty(article.getArticleTitle())){
                return new JsonResult(false,"标题不能为空");
            }
            if(article.getId()==null){
                if(!StrUtil.isEmpty(article.getArticleUrl())) {
                    if (article.getArticleUrl().length() > 50) {
                        return new JsonResult(false, "路径不能大于50");
                    }
                    int repeat = articleService.findRepeatByUrl(article.getArticleUrl());
                    if (repeat != 0) {
                        return new JsonResult(false, "路径已存在");
                    }
                }
                    User user = (User)request.getSession().getAttribute(BlogConst.USER_SESSION_KEY);
                    article.setUserId(user.getUserId());
                    article.setArticleNewstime(DateUtil.date());
                    article.setArticleUpdatetime(DateUtil.date());
                    //如果链接为空，则使用时间戳
                    if(StrUtil.isEmpty(article.getArticleUrl())){
                        article.setArticleUrl(String.valueOf(System.currentTimeMillis()/1000));
                    }
                    //如果没有选择缩略图则随机选择一张图片
                    if(StrUtil.isEmpty(article.getArticleThumbnail())){
                        article.setArticleThumbnail("/static/img/rand/"+ RandomUtil.randomInt(1,19)+".jpg");
                    }
                    //如果文章摘要为空，则取前50字为摘要
                    if(StrUtil.isEmpty(article.getArticleSummary())){
                        int post_summary = 50;
                        if(StrUtil.isNotEmpty(BlogConst.OPTIONS.get("post_summary"))){
                            post_summary = Integer.parseInt(BlogConst.OPTIONS.get("post_summary"));
                        }
                        String summaryText = StrUtil.cleanBlank(HtmlUtil.cleanHtmlTag(article.getArticleContent()));
                        if(summaryText.length()>post_summary){
                            article.setArticleSummary(summaryText.substring(0,post_summary));
                        }else {
                            article.setArticleSummary(summaryText);
                        }
                    }
                    articleService.save(article,tags,categorys);
                    logService.save(new Log(LogConstant.PUBLISH_AN_ARTICLE,LogConstant.SUCCESS, ServletUtil.getClientIP(request),DateUtil.date()));
            }else {
                //如果没有选择缩略图则随机选择一张图片
                if(StrUtil.isEmpty(article.getArticleThumbnail())){
                    article.setArticleThumbnail("/static/img/rand/"+ RandomUtil.randomInt(1,19)+".jpg");
                }
                //如果文章摘要为空，则取前50字为摘要
                if(StrUtil.isEmpty(article.getArticleSummary())){
                    int post_summary = 50;
                    if(StrUtil.isNotEmpty(BlogConst.OPTIONS.get("post_summary"))){
                        post_summary = Integer.parseInt(BlogConst.OPTIONS.get("post_summary"));
                    }
                    String summaryText = StrUtil.cleanBlank(HtmlUtil.cleanHtmlTag(article.getArticleContent()));
                    if(summaryText.length()>post_summary){
                        article.setArticleSummary(summaryText.substring(0,post_summary));
                    }else {
                        article.setArticleSummary(summaryText);
                    }
                }
                article.setArticleUpdatetime(DateUtil.date());
                articleService.update(article,tags,categorys);
                logService.save(new Log(LogConstant.UPDATE_AN_ARTICLE,LogConstant.SUCCESS,ServletUtil.getClientIP(request),DateUtil.date()));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(BlogEnum.ERROR.isFlag(),BlogEnum.ERROR.getMessage());
        }
        return new JsonResult(BlogEnum.PRESERVE_SUCCESS.isFlag(),BlogEnum.PRESERVE_SUCCESS.getMessage());
    }

    @GetMapping(value = "/recycle")
    public String updateStatus(@RequestParam(value = "id")Integer id){
        try {
            articleService.recycle(id,ArticleStatus.RECYCLE.getStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/article?status=0";
    }

    @PostMapping(value = "/restore")
    @ResponseBody
    public JsonResult restore(@RequestParam(value = "id")int id){
        try {
            articleService.recycle(id,ArticleStatus.PUBLISH.getStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult(BlogEnum.OPERATION_SUCCESS.isFlag(),BlogEnum.OPERATION_SUCCESS.getMessage());
    }

    @GetMapping(value = "/remove")
    public String remove(@RequestParam(value = "id") Integer articleId,HttpServletRequest request){
        try {
            articleService.remove(articleId);
            logService.save(new Log(LogConstant.REMOVE_AN_ARTICLE,LogConstant.SUCCESS,ServletUtil.getClientIP(request),DateUtil.date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/article?status=2";
    }


}

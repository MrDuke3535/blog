package cn.aybss.blog.web.controller.front;

import cn.aybss.blog.model.domain.ArticleCustom;
import cn.aybss.blog.model.dto.ArchiveBo;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.model.enums.ArticleStatus;
import cn.aybss.blog.model.enums.PageNumber;
import cn.aybss.blog.model.enums.PostType;
import cn.aybss.blog.service.ArticleService;
import cn.aybss.blog.web.controller.admin.BaseController;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ArticleService articleService = null;


    @GetMapping(value = {"/","index"} )
    public String index(Model model,HttpServletResponse response,HttpServletRequest request){
        return this.index(model,1,response,request);
    }

    @GetMapping(value = "page/{page}")
    public String index(Model model, @PathVariable Integer page,HttpServletResponse response,HttpServletRequest request){
        page = page<0 || page> BlogConst.MAX_PAGE ? 1 : page;
        Integer limit = StrUtil.isEmpty(BlogConst.OPTIONS.get("index_article")) ? PageNumber.POST_INDEX_LIMIT.getLimit() : Integer.parseInt(BlogConst.OPTIONS.get("index_article"));
        ArticleCustom articleCustom = new ArticleCustom();
        articleCustom.setArticleStatus(ArticleStatus.PUBLISH.getStatus());
        articleCustom.setArticlePost(PostType.POST_TYPE_POST.getValue());
        PageInfo pageInfo = articleService.findPageArticle(page,limit,articleCustom);
        model.addAttribute("articles",pageInfo);
        return this.render("index");
    }

    @GetMapping(value = {"post/{articleUrl}","post/{articleUrl}.html"})
    public String post(Model model,@PathVariable(value = "articleUrl") String articleUrl){
        ArticleCustom articleCustom = articleService.findByArticleUrl(articleUrl);
        if(articleCustom==null){
            this.render_404();
        }
        model.addAttribute("article",articleCustom);
        return this.render("post");
    }

    @GetMapping(value = "archives")
    public String archives(Model model){
        List<ArchiveBo> archiveBos = articleService.archives();
        model.addAttribute("articleList",archiveBos);
        return this.render("archives");
    }

}

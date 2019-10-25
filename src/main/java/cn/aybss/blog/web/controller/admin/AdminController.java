package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.*;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.dto.LogConstant;
import cn.aybss.blog.model.enums.PostType;
import cn.aybss.blog.service.*;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LinksService linksService;
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping(value = {"","/index"})
    public String index(Model model){
        //已发布文章数
        Integer countPublish = articleService.countByStatus(null, PostType.POST_TYPE_POST.getValue());
        model.addAttribute("countPublish",countPublish);
        //友链总数
        List<Link> links = linksService.findLinks();
        model.addAttribute("countLinks",links.size());
        //附件总数
        Integer countAttachment = attachmentService.countAttachment().size();
        model.addAttribute("countAttachment",countAttachment);
        //成立天数
        Date blogStart = DateUtil.parse(BlogConst.OPTIONS.get("blog_start"));
        model.addAttribute("establishDate",DateUtil.between(blogStart,DateUtil.date(),DateUnit.DAY));
        //查询最新的文章
        ArticleCustom articleCustom = new ArticleCustom();
        articleCustom.setArticlePost(PostType.POST_TYPE_POST.getValue());
        PageInfo<ArticleCustom> pageInfo = articleService.findPageArticle(1,5,articleCustom);
        model.addAttribute("articles",pageInfo.getList());
        //查询最新的日志
        PageInfo<Log> logs = logService.findLogs(1,5);
        model.addAttribute("logs",logs.getList());
        return "admin/admin_index";
    }

    @GetMapping(value = "/login")
    public String login(HttpSession session){
        User user = (User)session.getAttribute(BlogConst.USER_SESSION_KEY);
        if(user!=null){
            return "redirect:/admin";
        }
        return "admin/admin_login";
    }

    @PostMapping(value = "getLogin")
    @ResponseBody
    public JsonResult getLogin(@RequestParam(value = "userName")String userName,
                               @RequestParam(value = "userPwd") String userPwd, HttpSession session){
        try {
            //密码出错后禁止时间
            int inhibitTime = 10;
            //为true禁止登录
            String flag = "true";
            //错误总次数
            int errorCount = 5;
            User users = userService.findUser();
            //判断用户是否被禁止登录
            Date date = DateUtil.date();
            if(users.getLoginLastTime()!=null){
                date = users.getLoginLastTime();
            }
            long between = DateUtil.between(date,DateUtil.date(), DateUnit.MINUTE);
            if(StrUtil.equals(users.getLoginEnable(),flag)&&between<inhibitTime){
                return new JsonResult(false,"账户被禁止登录10分钟，请稍后重试");
            }
            User user = userService.getByNameAndPwd(userName,userPwd);
            userService.updateLoginLastTime(new Date(),users.getUserId());
            if(user!=null){
                session.setAttribute(BlogConst.USER_SESSION_KEY,user);
                //登录成功 重置用户为正常状态
                userService.updateUserNormal(user.getUserId());
                //添加登录日志
                logService.save(new Log(LogConstant.LOGIN,LogConstant.LOGIN_SUCCES, ServletUtil.getClientIP(request),new Date()));
                return new JsonResult(true,"登录成功");
            }else {
                Integer error = userService.updateError();
                if(error == errorCount){
                    userService.updateLoginEnable("true",0);
                }else if(error==1){
                    userService.updateLoginEnable("false",1);
                }
                logService.save(new Log(LogConstant.LOGIN,LogConstant.LOGIN_ERROR,ServletUtil.getClientIP(request),new Date()));
                return new JsonResult(false,"用户名或密码错误！你还有" + (5 - error) + "次机会");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"未知错误");
        }

    }

    @RequestMapping("/exitLogon")
    public String  exitLogon(HttpSession session){
        session.invalidate();
        return "admin/admin_login";
    }



}

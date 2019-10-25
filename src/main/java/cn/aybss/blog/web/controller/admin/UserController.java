package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.Attachment;
import cn.aybss.blog.model.domain.User;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.enums.BlogEnum;
import cn.aybss.blog.service.AttachmentService;
import cn.aybss.blog.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin/profile")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public String profile(Model model){
        User user = userService.findUser();
        model.addAttribute("user",user);
        return "admin/admin_user";
    }

    @GetMapping(value = "/openChoice/{id}")
    public String openChoice(Model model, @PathVariable String id, @RequestParam(value = "page",defaultValue = "1")int page,
                             @RequestParam(value = "limit",defaultValue = "18")int limit){
        PageInfo<Attachment> info = attachmentService.getAttachment(page,limit);
        model.addAttribute("info",info);
        model.addAttribute("id",id);
        return "admin/part/open_choice";
    }

    @PostMapping(value = "updateProfile")
    @ResponseBody
    public JsonResult updateProfile(User user, HttpSession session){
        try {
            userService.updateDatum(user);
            session.invalidate();
        }catch (Exception e){
            return new JsonResult(BlogEnum.ERROR.isFlag(),BlogEnum.ERROR.getMessage());
        }
        return new JsonResult(BlogEnum.PRESERVE_SUCCESS.isFlag(),BlogEnum.PRESERVE_SUCCESS.getMessage());
    }

}

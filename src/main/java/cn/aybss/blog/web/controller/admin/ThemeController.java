package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.Theme;
import cn.aybss.blog.service.ThemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/theme")
public class ThemeController extends BaseController {

    @Autowired
    private ThemeService themeService;

    @GetMapping
    public String theme(Model model,@RequestParam(value = "page",defaultValue = "1")int page,
                        @RequestParam(value = "limit",defaultValue = "10")int limit){
        PageInfo<Theme> info = themeService.findPageTheme(page,limit);
        model.addAttribute("info",info);
        return "admin/admin_theme";
    }

}

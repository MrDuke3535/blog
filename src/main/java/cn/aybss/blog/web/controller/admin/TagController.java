package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.Tag;
import cn.aybss.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String tag(Model model){
        List<Tag> tags = tagService.findTags();
        model.addAttribute("tags",tags);
        return "admin/admin_tag";
    }

    @PostMapping(value = "/save")
    public String save(Tag tag){
        try {
            if(tag.getTagId()!=null){
                tagService.update(tag);
            }else {
                tagService.save(tag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/tag";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam(value = "tagId")int tagId,Model model){
        Tag tag = tagService.findByTagId(tagId);
        List<Tag> tags = tagService.findTags();
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);
        return "admin/admin_tag";
    }

    @GetMapping(value = "/remove")
    public String remove(@RequestParam("tagId")int tagId){
        try {
            tagService.delete(tagId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/tag";
    }

}

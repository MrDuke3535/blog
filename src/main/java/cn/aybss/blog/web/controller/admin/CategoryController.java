package cn.aybss.blog.web.controller.admin;


import cn.aybss.blog.model.domain.Category;
import cn.aybss.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String catrgory(Model model){
        List<Category> categories = categoryService.findCategory();
        model.addAttribute("Categorys",categories);
        return "admin/admin_category";
    }

    @PostMapping(value = "/save")
    public String save(Category category){
        try {
            if(category.getCategoryId()!=null){
                categoryService.update(category);
            }else {
                categoryService.save(category);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/category";

    }

    @GetMapping(value = "/edit")
    public String updateCategory(Model model, @RequestParam(value = "categoryId")Integer categoryId){
        try {
            List<Category> categories = categoryService.findCategory();
            Category category = categoryService.findByCategoryId(categoryId);
            model.addAttribute("Categorys",categories);
            model.addAttribute("category",category);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/admin_category";

    }


    @GetMapping(value = "/remove")
    public String remove(@RequestParam(value = "categoryId")int categoryId){
        try {
            categoryService.delete(categoryId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/category";
    }

}

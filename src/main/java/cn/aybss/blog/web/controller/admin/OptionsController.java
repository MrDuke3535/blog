package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.Options;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.enums.BlogEnum;
import cn.aybss.blog.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/option")
public class OptionsController extends BaseController {

    @Autowired
    private OptionsService optionsService;

    @GetMapping
    public String index(){
        return "admin/admin_options";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult save(@RequestParam Map<String,String> map){
        try {
            optionsService.save(map);
            BlogConst.OPTIONS.clear();
            List<Options> options = optionsService.selectMap();
            for(Options option:options){
                BlogConst.OPTIONS.put(option.getOptionName(),option.getOptionValue());
            }
        }catch (Exception e){
            return new JsonResult(BlogEnum.PRESERVE_ERROR.isFlag(),BlogEnum.PRESERVE_ERROR.getMessage());
        }
        return new JsonResult(BlogEnum.PRESERVE_SUCCESS.isFlag(),BlogEnum.PRESERVE_SUCCESS.getMessage());
    }

}

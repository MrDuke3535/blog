package cn.aybss.blog.config;

import cn.aybss.blog.model.domain.Menu;
import cn.aybss.blog.model.domain.Options;
import cn.aybss.blog.model.dto.BlogConst;
import cn.aybss.blog.service.MenuService;
import cn.aybss.blog.service.OptionsService;
import cn.aybss.blog.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StartupConfig implements ApplicationListener {

    @Autowired
    private OptionsService optionsService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ThemeService themeService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        this.loadOptions();
        this.loadMenus();
        this.loadTheme();
    }

    private void loadOptions(){
        List<Options> optionsList = optionsService.selectMap();
        if(optionsList.size()>0&&!optionsList.isEmpty()){
            for(Options options:optionsList){
                BlogConst.OPTIONS.put(options.getOptionName(),options.getOptionValue());
            }
        }
    }

    private void loadMenus(){
        BlogConst.MENUS = menuService.findMenus();
    }

    private void loadTheme(){
        BlogConst.THEME_NAME = themeService.getEnabledTheme();
    }

}

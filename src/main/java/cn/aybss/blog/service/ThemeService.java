package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Theme;
import com.github.pagehelper.PageInfo;

public interface ThemeService {

    public void saveTheme(Theme theme);

    public String getEnabledTheme();

    public PageInfo<Theme> findPageTheme(int page,int limit);

}

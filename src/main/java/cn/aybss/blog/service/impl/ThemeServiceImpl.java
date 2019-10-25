package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.ThemeMapper;
import cn.aybss.blog.model.domain.Theme;
import cn.aybss.blog.model.domain.ThemeExample;
import cn.aybss.blog.model.domain.ThemeExample.Criteria;
import cn.aybss.blog.model.enums.ThemeStatus;
import cn.aybss.blog.service.ThemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeMapper themeMapper;

    @Override
    public void saveTheme(Theme theme) {
        theme.setCreateTime(new Date());
        themeMapper.insertSelective(theme);
    }

    @Override
    public String getEnabledTheme() {
        ThemeExample example = new ThemeExample();
        Criteria criteria = example.createCriteria();
        criteria.andThemeStatusEqualTo(ThemeStatus.THEME_ENABLED.getValue());
        List<Theme> themes = themeMapper.selectByExample(example);
        if(themes.size()>0){
            return themes.get(0).getThemeName();
        }
        return null;
    }

    @Override
    public PageInfo<Theme> findPageTheme(int page, int limit) {
        PageHelper.startPage(page,limit);
        ThemeExample themeExample = new ThemeExample();
        themeExample.setOrderByClause("id desc");
        List<Theme> lists = themeMapper.selectByExample(themeExample);
        return new PageInfo<>(lists);
    }
}

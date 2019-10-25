package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Theme;
import cn.aybss.blog.model.domain.ThemeExample;

import java.util.List;

public interface ThemeMapper {

    public int insertSelective(Theme themes);

    List<Theme> selectByExample(ThemeExample example);

}

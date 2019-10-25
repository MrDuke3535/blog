package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Menu;
import cn.aybss.blog.model.domain.MenuExample;

import java.util.List;

public interface MenuMapper {

    public int insertSelective(Menu menu);

    public List<Menu> selectByExample(MenuExample example);

}

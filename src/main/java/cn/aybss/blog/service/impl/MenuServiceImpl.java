package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.MenuMapper;
import cn.aybss.blog.model.domain.Menu;
import cn.aybss.blog.model.domain.MenuExample;
import cn.aybss.blog.model.domain.MenuExample.Criteria;
import cn.aybss.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void save(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public List<Menu> findMenus() {
        MenuExample example = new MenuExample();
        example.setOrderByClause("menu_sort");
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    }
}

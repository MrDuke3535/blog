package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Menu;

import java.util.List;

public interface MenuService {

    public void save(Menu menu);

    public List<Menu> findMenus();

}

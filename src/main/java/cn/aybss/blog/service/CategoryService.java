package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> findCategory();

    public void save(Category category);

    public void update(Category category);

    public Category findByCategoryId(Integer categoryId);

    public void delete(int categoryId);
}

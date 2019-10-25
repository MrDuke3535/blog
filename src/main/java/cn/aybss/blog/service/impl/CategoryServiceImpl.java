package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.CategoryMapper;
import cn.aybss.blog.model.domain.Category;
import cn.aybss.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategory() {
        return categoryMapper.selectByExample(null);
    }

    @Override
    public void save(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public Category findByCategoryId(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public void delete(int categoryId) {
        categoryMapper.deleteByPrimaryKey(categoryId);
    }
}

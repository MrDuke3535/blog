package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.ArticleCategory;
import cn.aybss.blog.model.domain.Category;
import cn.aybss.blog.model.domain.CategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    public List<Category> selectByExample(CategoryExample example);

    public int insert(Category category);

    public int updateByExampleSelective(@Param(value = "record") Category record);

    public Category selectByPrimaryKey(Integer categoryId);

    public int updateByPrimaryKeySelective(Category category);

    public int deleteByPrimaryKey(Integer categoryId);
}

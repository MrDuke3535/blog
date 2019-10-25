package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.ArticleCategory;
import cn.aybss.blog.model.domain.ArticleCategoryExample;

public interface ArticleCategoryMapper {


    public void deleteByExample(ArticleCategoryExample example);

    public int insert(ArticleCategory articleCategory);

}

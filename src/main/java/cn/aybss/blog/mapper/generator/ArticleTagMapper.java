package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.ArticleTag;
import cn.aybss.blog.model.domain.ArticleTagExample;

public interface ArticleTagMapper {

    public void deleteByExample(ArticleTagExample example);

    public int insert(ArticleTag articleTag);

}

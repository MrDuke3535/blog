package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Article;
import cn.aybss.blog.model.domain.ArticleExample;

import java.util.List;

public interface ArticleMapper {

    public int insert(Article article);

    public List<Article> selectByExample(ArticleExample example);

    public int updateByPrimaryKeySelective(Article article);

    public int deleteByPrimaryKey(int id);

}

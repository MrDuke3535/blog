package cn.aybss.blog.mapper.custom;

import cn.aybss.blog.model.domain.ArticleCategory;
import cn.aybss.blog.model.domain.ArticleTag;
import cn.aybss.blog.model.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapperCustom {

    public List<Integer> selectByarticleId(Integer articleId);

    public void delete(@Param(value = "list")List<Integer> tagList, @Param(value = "articleId")Integer articleId);

}

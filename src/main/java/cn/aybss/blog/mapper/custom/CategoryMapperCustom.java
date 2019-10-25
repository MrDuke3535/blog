package cn.aybss.blog.mapper.custom;

import cn.aybss.blog.model.domain.ArticleCategory;
import cn.aybss.blog.model.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapperCustom {

    public List<Integer> selectByarticleId(Integer articleId);

    public void delete(@Param(value = "list")List<Integer> categoryList,@Param(value = "articleId")Integer articleId);

}

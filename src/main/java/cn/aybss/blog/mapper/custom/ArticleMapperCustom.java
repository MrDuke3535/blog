package cn.aybss.blog.mapper.custom;

import cn.aybss.blog.model.domain.ArticleCustom;
import cn.aybss.blog.model.dto.ArchiveBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapperCustom {

    public List<ArticleCustom> findPageArticle(ArticleCustom articleCustom);

    public ArticleCustom findByArticleUrl(String articleUrl);

    public List<ArchiveBo> findDateAndCount();

    public Integer countByStatus(@Param(value = "status") Integer status,@Param(value = "post") String post);

    public ArticleCustom findByArticleId(@Param(value = "id") Integer articleId);

    public int findRepeatByUrl(String articleUrl);

    public void updateStatus(@Param(value = "id")int id,@Param(value = "status")int status);

}

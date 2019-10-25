package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Article;
import cn.aybss.blog.model.domain.ArticleCustom;
import cn.aybss.blog.model.dto.ArchiveBo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {

    public PageInfo<ArticleCustom> findPageArticle(Integer page, Integer limit, ArticleCustom articleCustom);

    public void save(Article article,Long[] tagsName,Long[] categorys);

    public ArticleCustom findByArticleUrl(String articleUrl);

    public List<ArchiveBo> archives();

    public Integer countByStatus(Integer status,String post);

    public ArticleCustom findByArticleId(Integer articleId);

    public int findRepeatByUrl(String url);

    public void update(Article article,Long[] categorys,Long[] tags);

    public void recycle(int articleId,Integer status);

    public void remove(int id);

}

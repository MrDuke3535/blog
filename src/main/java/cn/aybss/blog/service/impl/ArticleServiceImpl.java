package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.custom.ArticleMapperCustom;
import cn.aybss.blog.mapper.custom.CategoryMapperCustom;
import cn.aybss.blog.mapper.custom.TagMapperCustom;
import cn.aybss.blog.mapper.generator.*;
import cn.aybss.blog.model.domain.*;
import cn.aybss.blog.model.domain.ArticleExample.Criteria;
import cn.aybss.blog.model.dto.ArchiveBo;
import cn.aybss.blog.service.ArticleService;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapperCustom articleMapperCustom;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapperCustom tagMapperCustom;
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;


    @Override
    public PageInfo<ArticleCustom> findPageArticle(Integer page, Integer limit, ArticleCustom articleCustom) {
        PageHelper.startPage(page,limit);
        List<ArticleCustom> lists = articleMapperCustom.findPageArticle(articleCustom);
        PageInfo pageInfo = new PageInfo(lists);
        return pageInfo;
    }

    @Override
    public void save(Article article, Long[] tagsName, Long[] categorys) {
        articleMapper.insert(article);

    }

    @Override
    public ArticleCustom findByArticleUrl(String articleUrl) {
        return articleMapperCustom.findByArticleUrl(articleUrl);
    }

    @Override
    public List<ArchiveBo> archives() {
        List<ArchiveBo> archiveBos = articleMapperCustom.findDateAndCount();
        if(archiveBos!=null){
            for(ArchiveBo archiveBo:archiveBos){
                ArticleExample example = new ArticleExample();
                Criteria criteria = example.createCriteria().andArticleStatusEqualTo(0)
                        .andArticlePostEqualTo("post");
                example.setOrderByClause("article_newstime desc");
                String date = archiveBo.getDate();
                Date sd = DateUtil.parse(date,"yyyy年MM月");
                criteria.andArticleNewstimeEqualTo(sd);
                Calendar calendar = Calendar.getInstance();
                if(sd.getMonth()<12){
                    calendar.setTime(sd);
                    calendar.add(Calendar.MONTH,1);
                }else {
                    calendar.setTime(sd);
                    calendar.add(Calendar.YEAR,1);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
                String date2 = sdf.format(calendar.getTime());
                Date date3 = DateUtil.parse(date2,"yyyy年MM月");
                criteria.andArticleNewstimeLessThan(date3);
                List<Article> articles = articleMapper.selectByExample(example);
                archiveBo.setArticles(articles);
            }
        }
        return archiveBos;
    }

    @Override
    public Integer countByStatus(Integer status, String post) {
        return articleMapperCustom.countByStatus(status,post);
    }

    @Override
    public ArticleCustom findByArticleId(Integer articleId) {
        return articleMapperCustom.findByArticleId(articleId);
    }

    @Override
    public int findRepeatByUrl(String url) {
        return articleMapperCustom.findRepeatByUrl(url);
    }

    @Override
    public void update(Article article, Long[] categorys, Long[] tags) {
        //修改文章
        articleMapper.updateByPrimaryKeySelective(article);
        List<Integer> categoryList = categoryMapperCustom.selectByarticleId(article.getId());
        List<Integer> tagList = tagMapperCustom.selectByarticleId(article.getId());
        if(categoryList!=null&&categoryList.size()>0){
            categoryMapperCustom.delete(categoryList,article.getId());
        }
        if(tagList!=null&&categoryList.size()>0){
            tagMapperCustom.delete(tagList,article.getId());
        }
        if(categorys!=null){
            Arrays.asList(categorys).stream().forEach(cate->{
                ArticleCategory articleCategory = new ArticleCategory();
                articleCategory.setArticleId(article.getId());
                articleCategory.setCategoryId(cate);
                articleCategoryMapper.insert(articleCategory);
            });
        }
        if(tags!=null){
            Arrays.asList(tags).stream().forEach(tag->{
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag);
                articleTagMapper.insert(articleTag);
            });
        }

    }

    @Override
    public void recycle(int articleId, Integer status) {
        articleMapperCustom.updateStatus(articleId,status);
    }

    @Override
    public void remove(int id) {
        articleMapper.deleteByPrimaryKey(id);
        ArticleTagExample tagExample = new ArticleTagExample();
        ArticleTagExample.Criteria tagCriteria = tagExample.createCriteria();
        tagCriteria.andArticleIdEqualTo(id);
        ArticleCategoryExample categoryExample = new ArticleCategoryExample();
        ArticleCategoryExample.Criteria categoryCriteria = categoryExample.createCriteria();
        categoryCriteria.andArticleIdEqualTo(id);
        articleCategoryMapper.deleteByExample(categoryExample);
        articleTagMapper.deleteByExample(tagExample);
    }
}

package cn.aybss.blog.model.dto;

import cn.aybss.blog.model.domain.Article;

import java.io.Serializable;
import java.util.List;

public class ArchiveBo implements Serializable {

    private String date;

    private List<Article> articles;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

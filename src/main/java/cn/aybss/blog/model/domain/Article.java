package cn.aybss.blog.model.domain;

import java.util.Date;

public class Article {

    //文章状态 发布/草稿/回收箱
    private Integer articleStatus;
    //         文章/页面
    private String articlePost;
    //文章简介（鼠标移动到文章后显示）
    private String articleSummary;
    //文章链接
    private String articleUrl;
    //封面图片
    private String articleThumbnail;
    //文章标题
    private String articleTitle;
    //文章id
    private Integer id;
    //文章内容
    private String articleContentMd;
    //文章内容 html
    private String articleContent;
    //文章类型 已启用/未启用
    private Integer articleType;
    //文章创建时间
    private Date articleNewstime;
    //文章评论 已启用/未启用
    private Integer articleComment;
    //文章更新时间
    private Date articleUpdatetime;

    private Integer userId;

    private Long articleViews;


    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getArticlePost() {
        return articlePost;
    }

    public void setArticlePost(String articlePost) {
        this.articlePost = articlePost;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleThumbnail() {
        return articleThumbnail;
    }

    public void setArticleThumbnail(String articleThumbnail) {
        this.articleThumbnail = articleThumbnail;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleContentMd() {
        return articleContentMd;
    }

    public void setArticleContentMd(String articleContentMd) {
        this.articleContentMd = articleContentMd;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Date getArticleNewstime() {
        return articleNewstime;
    }

    public void setArticleNewstime(Date articleNewstime) {
        this.articleNewstime = articleNewstime;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Integer getArticleComment() {
        return articleComment;
    }

    public void setArticleComment(Integer articleComment) {
        this.articleComment = articleComment;
    }

    public Date getArticleUpdatetime() {
        return articleUpdatetime;
    }

    public void setArticleUpdatetime(Date articleUpdatetime) {
        this.articleUpdatetime = articleUpdatetime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getArticleViews() {
        return articleViews;
    }

    public void setArticleViews(Long articleViews) {
        this.articleViews = articleViews;
    }
}

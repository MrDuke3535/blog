package cn.aybss.blog.model.domain;

public class ArticleCustom extends Article{
    //分类路径
    private String categorysUrl;
    //分类名称
    private String categorysName;

    private String tagsName;

    private String tagsUrl;

    public String getCategorysUrl() {
        return categorysUrl;
    }

    public void setCategorysUrl(String categorysUrl) {
        this.categorysUrl = categorysUrl;
    }

    public String getCategorysName() {
        return categorysName;
    }

    public void setCategorysName(String categorysName) {
        this.categorysName = categorysName;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public String getTagsUrl() {
        return tagsUrl;
    }

    public void setTagsUrl(String tagsUrl) {
        this.tagsUrl = tagsUrl;
    }
}

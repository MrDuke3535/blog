package cn.aybss.blog.model.domain;

import java.util.Date;

public class Theme {

    private Integer id;

    private String themeName;

    private String themeDescribe;

    private String themeImg;

    private Integer themeStatus;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDescribe() {
        return themeDescribe;
    }

    public void setThemeDescribe(String themeDescribe) {
        this.themeDescribe = themeDescribe;
    }

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public Integer getThemeStatus() {
        return themeStatus;
    }

    public void setThemeStatus(Integer themeStatus) {
        this.themeStatus = themeStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

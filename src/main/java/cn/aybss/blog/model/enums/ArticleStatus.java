package cn.aybss.blog.model.enums;

public enum ArticleStatus {

    PUBLISH(0,"已发布"),

    DRAFT(1,"草稿"),

    RECYCLE(2,"回收箱");

    private Integer status;
    private String desc;

    private ArticleStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package cn.aybss.blog.model.enums;

public enum ThemeStatus {

    //未启用
    THEME_NOT_ENABLED(0),

    //已启用
    THEME_ENABLED(1);

    private int value;

    private ThemeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

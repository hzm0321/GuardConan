package cn.hzmeurasia.guardconan.entity;

/**
 * 类名: Options<br>
 * 功能:(主页选项)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/11 15:17
 */
public class Options {

    private String content;
    private int imageId;

    public Options(String content, int imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public String getContent() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }
}

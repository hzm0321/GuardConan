package cn.hzmeurasia.guardconan.db;

import android.graphics.drawable.Drawable;

import org.litepal.crud.LitePalSupport;

/**
 * 类名: UnlockDb<br>
 * 功能:(TODO用一句话描述该类的功能)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/20 15:29
 */
public class UnlockDb extends LitePalSupport {

    private int id;

    private Drawable icon;

    private String name;

    private int imageId;

    public UnlockDb(int id, Drawable icon, String name, int imageId) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

package cn.hzmeurasia.guardconan.entity;

/**
 * 类名: AppInfo<br>
 * 功能:(App配置信息)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/10 11:20
 */
public class AppInfo {

    private int versionCode;
    private String versionName;
    private String description;
    private String downloadUrl;


    public AppInfo() {
        this(0, "0");
    }

    public AppInfo(int versionCode, String versionName) {
        this(versionCode, versionName,"");
    }

    public AppInfo(int versionCode, String versionName, String description) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
        this.downloadUrl = "";
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getdownloadUrl() {
        return downloadUrl;
    }

    public void setdownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String toJson() {
        return "{" +
                "versionCode:" + versionCode +
                ", versionName:'" + versionName + '\'' +
                ", description:'" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}

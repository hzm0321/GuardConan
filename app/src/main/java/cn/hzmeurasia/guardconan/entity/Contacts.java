package cn.hzmeurasia.guardconan.entity;

/**
 * 类名: Contacts<br>
 * 功能:(联系人实体类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 10:16
 */
public class Contacts {

    private String name;

    private String phone;

    public Contacts(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

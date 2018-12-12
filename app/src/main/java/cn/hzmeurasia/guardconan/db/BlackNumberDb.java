package cn.hzmeurasia.guardconan.db;

import org.litepal.crud.LitePalSupport;

/**
 * 类名: BlackNumberDb<br>
 * 功能:(黑名单列表实体类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/12 11:08
 */
public class BlackNumberDb extends LitePalSupport {

    private int id;

    private int number;

    private String name;

    private String phone;

    private String message;

    public BlackNumberDb() {

    }

    public BlackNumberDb(int id, int number, String name, String phone, String message) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.phone = phone;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

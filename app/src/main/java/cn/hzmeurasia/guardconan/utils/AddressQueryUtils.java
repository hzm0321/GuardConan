package cn.hzmeurasia.guardconan.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * 类名: AddressQueryUtils<br>
 * 功能:(电话地址查询)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/20 11:12
 */
public class AddressQueryUtils {
    public static final String DB_NAME = "address.db";

    /**
     * 电话归属地查询
     *
     * @param context
     * @param number 电话号码
     */
    public static String getAddress(Context context, String number) {
        String address = "未知号码";
        String path = new File(context.getFilesDir(), DB_NAME).getAbsolutePath();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        // 判断是否是手机号码
        // 1[3-8]+9数字
        // 正则表达式
        // ^1[3-8]\d{9}$
        if (number.matches("^1[3-8]\\d{9}$")) {// 匹配是否是手机号码
            Cursor cursor = database.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)",
                    new String[]{number.substring(0, 7)});

            if (cursor.moveToFirst()) {
                address = cursor.getString(0);
            }

            cursor.close();
        }else {
            switch (number.length()){
                case 3:
                    address = "报警电话";
                    break;
                case 4:
                    address = "模拟器";
                    break;
                case 5:
                    address = "客服电话";
                    break;
                case 7:
                case 8:
                    // 8888 8888
                    address = "本地电话";
                    break;
                default:
                    // 010 8888 888
                    // 0910 8888 8888
                    if (number.startsWith("0") && number.length() >= 10
                            && number.length() <= 12) {
                        // 有可能是长途电话
                        // 区号是4位的情况
                        Cursor cursor = database.rawQuery(
                                "select location from data2 where area=?",
                                new String[] { number.substring(1, 4) });
                        if (cursor.moveToFirst()) {// 查到4位区号
                            address = cursor.getString(0);
                        }

                        cursor.close();

                        // 区号是3位的情况
                        if ("未知号码".equals(address)) {// 4位区号没有查到,开始查3位
                            cursor = database.rawQuery(
                                    "select location from data2 where area=?",
                                    new String[] { number.substring(1, 3) });
                            if (cursor.moveToFirst()) {// 查到3位区号
                                address = cursor.getString(0);
                            }

                            cursor.close();
                        }
                    }
                    break;
            }
        }
        database.close();//关闭数据库
        return address;
    }
}

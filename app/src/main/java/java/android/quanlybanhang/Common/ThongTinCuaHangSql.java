package java.android.quanlybanhang.Common;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.android.quanlybanhang.function.MainActivity;

public class ThongTinCuaHangSql {

    private Context context;

    public ThongTinCuaHangSql(Context context) {
        this.context = context;
    }

    public String IDCuaHang() {
        java.android.quanlybanhang.database.ThongTinCuaHangSql thongTinCuaHangSql = new java.android.quanlybanhang.database.ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTable();
        Cursor cursor = thongTinCuaHangSql.selectThongTin();
        String id = "";
        if (cursor.getCount() > 0) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                }
            } else {

            }
        }
        return id;
    }
}

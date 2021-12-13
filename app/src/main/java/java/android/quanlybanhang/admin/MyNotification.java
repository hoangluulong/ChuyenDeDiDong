package java.android.quanlybanhang.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyNotification extends NotificationListenerService {
    Context context;
    private List<String> key;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // We can read notification while posted.
        for (StatusBarNotification sbm : MyNotification.this.getActiveNotifications()) {
            String title = sbm.getNotification().extras.getString("android.title");
            String text = sbm.getNotification().extras.getString("android.text");
            String package_name = sbm.getPackageName();
//            Toast.makeText(context, title +"abc",Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, text+"abc",Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, package_name+"abcxyz",Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "abc",Toast.LENGTH_SHORT).show();

            if(text!=null && text.contains("NamABank"))
            {

                if(checkTaiKhoan(text))
                {
                    Intent intent=new Intent(getApplicationContext(), SanPhamQuangCaoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle=new Bundle();
                    bundle.putString("sotien",key.get(0));
                    bundle.putString("key",key.get(1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
//            Toast.makeText(getApplicationContext(),"abc",Toast.LENGTH_SHORT).show();


        }
    }


    private boolean checkTaiKhoan(String text)
    {
        String [] list= text.split(" ");
        if(text.contains("NamABank"))
        {
            key=new ArrayList<>();
            key.add(list[4]);
            key.add(list[11]);
        }
        if(key.size()>0)
        {
            return true;
        }else return false;
    }
}

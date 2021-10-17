package java.android.quanlybanhang.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.Account.SignInActivity;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.DonHangOnline.DuyetDonHangActivity;

public class MainActivity extends AppCompatActivity {

    Button signout;
    FirebaseAuth auth;
    RelativeLayout baocao, bi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signout = findViewById(R.id.signout);
        auth = FirebaseAuth.getInstance();
        baocao= findViewById(R.id.baocao);
        bi = findViewById(R.id.bi);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaoCaoTongQuanActivity.class);
                startActivity(intent);
            }
        });

        bi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DuyetDonHangActivity.class);
                startActivity(intent);
            }
        });
    }
}
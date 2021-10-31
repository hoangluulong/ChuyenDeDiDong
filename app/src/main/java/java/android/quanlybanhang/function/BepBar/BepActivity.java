package java.android.quanlybanhang.function.BepBar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.Fragment.DonHangOfflineFragment;
import java.android.quanlybanhang.function.BepBar.Fragment.Fragment;

public class BepActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bep);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Cửa hàng - Chờ xử lí"));
        tabLayout.addTab(tabLayout.newTab().setText("Cửa hàng - Đang sử lí"));
        tabLayout.addTab(tabLayout.newTab().setText("Cửa hàng - Hoàn thành"));
        tabLayout.addTab(tabLayout.newTab().setText("Online - chờ sử lí"));
        tabLayout.addTab(tabLayout.newTab().setText("Online - đang sử lí"));
        tabLayout.addTab(tabLayout.newTab().setText("Online - hoàn thành"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Fragment adapter = new Fragment(this,getSupportFragmentManager(), tabLayout.getTabCount(),this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
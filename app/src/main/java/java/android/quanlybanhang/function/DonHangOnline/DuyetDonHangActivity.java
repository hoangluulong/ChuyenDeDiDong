package java.android.quanlybanhang.function.DonHangOnline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.adapter.TablayoutAdapter;
import java.android.quanlybanhang.function.DonHangOnline.fragment.FragmentAdapter;
import java.util.ArrayList;

public class DuyetDonHangActivity extends AppCompatActivity {

    private TablayoutAdapter tablayoutAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> title;
    private ViewPager2 pager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_don_hang);

        title = new ArrayList<String>();
        title.add("Chờ xác nhận");
        title.add("Đã xác nhận");
        title.add("Đang thực hiện");
        title.add("Đơn đang giao");
        title.add("Đơn hoàn thành");
        title.add("Đơn hủy");

        recyclerView = findViewById(R.id.recylerView);
        pager = findViewById(R.id.viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        displayItem();
    }

    private void displayItem(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tablayoutAdapter = new TablayoutAdapter(this, title, pager);
        recyclerView.setAdapter(tablayoutAdapter);
        recyclerView.setLayoutManager(layoutManager);

        tablayoutAdapter.notifyDataSetChanged();
    }


}
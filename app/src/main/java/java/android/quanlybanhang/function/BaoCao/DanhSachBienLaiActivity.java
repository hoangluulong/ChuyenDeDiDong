package java.android.quanlybanhang.function.BaoCao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.HelperClasses.DanhSachHoaDonAdapter;
import java.android.quanlybanhang.R;

public class DanhSachBienLaiActivity extends AppCompatActivity {

    private RecyclerView recycleview;
    private DanhSachHoaDonAdapter danhSachHoaDonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bien_lai);

        displayItem();
    }

    private void displayItem(){
        recycleview = findViewById(R.id.recycleview);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));

        danhSachHoaDonAdapter = new DanhSachHoaDonAdapter(this);
        recycleview.setAdapter(danhSachHoaDonAdapter);

        danhSachHoaDonAdapter.notifyDataSetChanged();
    }
}
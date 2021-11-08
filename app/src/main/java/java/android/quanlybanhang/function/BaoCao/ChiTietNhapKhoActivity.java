package java.android.quanlybanhang.function.BaoCao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.HelperClasses.ChiTietNhapKhoAdapter;
import java.android.quanlybanhang.HelperClasses.QuanLyKhoAdapter;
import java.android.quanlybanhang.Model.BaoCaoKho;
import java.android.quanlybanhang.Model.ChiTietNhapKho;
import java.android.quanlybanhang.R;
import java.util.List;

public class ChiTietNhapKhoActivity extends AppCompatActivity {
    public static final String KEY_BAOCAO = "BAOCAO";
    private ChiTietNhapKhoAdapter chiTietNhapKhoAdapter;
    private RecyclerView recyclerView;
    private Button back;
    private TextView ngayGio,tenNhanVien;
    private BaoCaoKho baoCaoKho;
    private List<ChiTietNhapKho> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhap_kho);
        back = findViewById(R.id.back_btn);
        ngayGio=findViewById(R.id.ngaygio);
        tenNhanVien=findViewById(R.id.tennhanvien);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        baoCaoKho = (BaoCaoKho) bundle.getSerializable(QuanLyKhoAdapter.KEY_BAOCAO);
        Log.d("triet", "onCreate: "+baoCaoKho.getNhanVien());
        ngayGio.setText(baoCaoKho.getNgay()+" "+baoCaoKho.getGio());
        tenNhanVien.setText(baoCaoKho.getNhanVien());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        displayItem();
    }

    private void displayItem(){
        recyclerView = findViewById(R.id.recylerView_chi_tiet_cap_nhat_kho);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        chiTietNhapKhoAdapter = new ChiTietNhapKhoAdapter(this,baoCaoKho.getChiTietNhapKho());
        recyclerView.setAdapter(chiTietNhapKhoAdapter);

        chiTietNhapKhoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
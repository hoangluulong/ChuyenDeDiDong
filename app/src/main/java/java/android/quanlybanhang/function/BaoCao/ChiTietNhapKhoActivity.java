package java.android.quanlybanhang.function.BaoCao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.HelperClasses.ChiTietNhapKhoAdapter;
import java.android.quanlybanhang.R;

public class ChiTietNhapKhoActivity extends AppCompatActivity {

    private ChiTietNhapKhoAdapter chiTietNhapKhoAdapter;
    private RecyclerView recyclerView;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhap_kho);
        back = findViewById(R.id.back_btn);
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

        chiTietNhapKhoAdapter = new ChiTietNhapKhoAdapter(this);
        recyclerView.setAdapter(chiTietNhapKhoAdapter);

        chiTietNhapKhoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
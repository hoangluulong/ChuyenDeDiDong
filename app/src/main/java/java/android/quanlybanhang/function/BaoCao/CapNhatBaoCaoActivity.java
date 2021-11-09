package java.android.quanlybanhang.function.BaoCao;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.HelperClasses.CapNhatBaoCaoKhoAdapter;
import java.android.quanlybanhang.Model.BaoCaoKho;
import java.android.quanlybanhang.Model.ChiTietNhapKho;
import java.android.quanlybanhang.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CapNhatBaoCaoActivity extends AppCompatActivity {
    private EditText tenNhanVien,ngayNhapKho,tenSanPham,soLieuMoi,soLieuCu;
    private Button addBaoCao,addSanPham,thoat,them;
    private DatabaseReference mDatabase;
    private BaoCaoKho baoCaoKho;
    private CapNhatBaoCaoKhoAdapter capNhatBaoCaoKhoAdapter;
    private Dialog dialog;
    private Window window;
    private RecyclerView recyclerView;
    private ArrayList<ChiTietNhapKho> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_chi_tiet_kho);
        tenNhanVien=findViewById(R.id.textTenNhanVien);
        ngayNhapKho=findViewById(R.id.textNgayBaoCao);
        addSanPham=findViewById(R.id.themChiTietSanPham);
        recyclerView=findViewById(R.id.listSanPhamKho);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BaoCaoKho").child("id_CuaHang");
        dialog = new Dialog(CapNhatBaoCaoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_chitietkho);
        window = dialog.getWindow();
        tenSanPham=dialog.findViewById(R.id.textSanPham);
        soLieuMoi=dialog.findViewById(R.id.textsoLieuMoi);
        soLieuCu=dialog.findViewById(R.id.textSoLieuCu);
        thoat=dialog.findViewById(R.id.thoat);
        them=dialog.findViewById(R.id.addChiTietKho);
        addBaoCao=findViewById(R.id.btnAddBaoCao);
        list=new ArrayList<>();
        addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChiTietKho(Gravity.CENTER);
            }
        });
        addBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBaoCaoKho();
            }
        });

    }
    public void addBaoCaoKho(){
        String TenNhanVien=tenNhanVien.getText().toString();
        Date date = Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);
        mDatabase.push().setValue(new BaoCaoKho(TenNhanVien,today,java.time.LocalTime.now().toString(),list));
    }
    public void addChiTietKho(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietNhapKho chiTietNhapKho=new ChiTietNhapKho();
                String TenSanPham=tenSanPham.getText().toString();
                String SoLieuCu=soLieuCu.getText().toString();
                String SoLieuMoi=soLieuMoi.getText().toString();
                chiTietNhapKho=new ChiTietNhapKho(TenSanPham,SoLieuCu,SoLieuMoi);
                list.add(chiTietNhapKho);
                capNhatBaoCaoKhoAdapter=new CapNhatBaoCaoKhoAdapter(CapNhatBaoCaoActivity.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(CapNhatBaoCaoActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(capNhatBaoCaoKhoAdapter);
                capNhatBaoCaoKhoAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }
}

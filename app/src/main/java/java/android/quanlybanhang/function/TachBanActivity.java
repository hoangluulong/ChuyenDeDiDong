package java.android.quanlybanhang.function;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.android.quanlybanhang.Common.ArrayListTachBan;
import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.HelperClasses.PackageTachBan.AdapterTachBan;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.KhuyenMaiOffLine.KhuyenMaiOff;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TachBanActivity extends AppCompatActivity implements ArrayListTachBan {
    private ArrayList<ProuductPushFB1> listmon;
    private Toolbar toolbar;
    private ArrayList<ProductPushFB> ListDate_yc;
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    String id_ban_thanhtoan;
    String id_khuvuc_thanhtoan;
    ArrayList<ProductPushFB> carsList1;
    ArrayList<ProuductPushFB1> carsList;
    AdapterTachBan adapterTachBan;
    RecyclerView recyclerView;
    Button bnt_thanhtoan;
    ArrayList<ProductPushFB> arrayList;
    ArrayList<ProductPushFB> listDatach = new ArrayList<>();
    private Window window, window1;
    private Dialog dialog, dialog1;
    ArrayList<ProuductPushFB1> arrayList1;
    String code_chucnang;
    String id_CuaHang ;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tach_ban);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang ="CuaHangOder/"+thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        bnt_thanhtoan = findViewById(R.id.bnt_thanhtoan);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tách bàn");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Hamdialogthongbao();
        Intent intent1 = getIntent();
        if(intent1.getStringExtra("id_trangthai")!= null){
            code_chucnang = intent1.getStringExtra("id_trangthai");
        }
        id_ban_thanhtoan = intent1.getStringExtra("id_ban");
        id_khuvuc_thanhtoan = intent1.getStringExtra("id_khuvuc");
        String carListAsString = getIntent().getStringExtra("list_as_string");
        String carListAsString1 = getIntent().getStringExtra("list_as_string1");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProuductPushFB1>>() {
        }.getType();
        Type type1 = new TypeToken<ArrayList<ProductPushFB>>() {
        }.getType();
        carsList1 = gson.fromJson(carListAsString1, type1);

        carsList = gson.fromJson(carListAsString, type);
        adapterTachBan = new AdapterTachBan(this, this);
        adapterTachBan.setData(carsList1);
        recyclerView = findViewById(R.id.rv_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(TachBanActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterTachBan);
        adapterTachBan.notifyDataSetChanged();

        bnt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductPushFB kq = adapterTachBan.PublicArraylist();
                for (int i = 0; i < carsList.size(); i++) {
                    for (int x = 0; x < kq.getSanpham().size(); x++) {
                        if (carsList.size() > 0) {
                            if (carsList.get(i).getNameProduct().equals(kq.getSanpham().get(x).getNameProduct()) && carsList.get(i).getLoai().equals(kq.getSanpham().get(x).getLoai())) {
                                if (carsList.get(i).getSoluong() > kq.getSanpham().get(x).getSoluong()) {
                                    carsList.get(i).setSoluong(carsList.get(i).getSoluong() - kq.getSanpham().get(x).getSoluong());
                                } else if (carsList.get(i).getSoluong() == kq.getSanpham().get(x).getSoluong()) {
                                    if(carsList.size()>0){
                                    carsList.remove(i);
                                   }
                                    else {

                                    }
                                }
                            }
                        } else {
                            Toast.makeText(TachBanActivity.this, "Danh Sách Rỗng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if(kq.getSanpham().size()>0){
                    for (int i = 0; i < kq.getSanpham().size(); i++) {
                     if( kq.getSanpham().get(i).getSoluong()>0) {

                         FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("3");
                         Intent intent = new Intent(TachBanActivity.this, OrderMenu.class);
                         Bundle bundle = new Bundle();
                         intent.putExtra("id_banTachBan", id_ban_thanhtoan);
                         intent.putExtra("id_khuvucTachBan", id_khuvuc_thanhtoan);
                         intent.putExtra("en", kq);
                         intent.putExtra("id_trangthai", code_chucnang);
                         Gson gson = new Gson();
                         String b = gson.toJson(carsList);
                         intent.putExtra("carsList", b);
                         intent.putExtras(bundle);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                     }
                     else {
                         Toast.makeText(TachBanActivity.this,"so Luong phai lon hon 0",Toast.LENGTH_LONG).show();
                         title.setText("Số Lượng Phải Lớn Hơn 0");
                         dialog.show();
                     }
                    }
                }
                else {
                    dialog.show();
                    title.setText("Không Có sản Phẩm Trong Danh Sách");
                    Toast.makeText(TachBanActivity.this,"khong co san pham trong danh sach",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void arrTachBan(ArrayList<ProuductPushFB1> arrayList) {
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TachBanActivity.this, OrderMenu.class);
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public  void Hamdialogthongbao(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_canhbao);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
        Button Cancel = dialog.findViewById(R.id.btn_cancel);
        title= dialog.findViewById(R.id.title);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
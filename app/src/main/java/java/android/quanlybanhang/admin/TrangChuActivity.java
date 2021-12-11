package java.android.quanlybanhang.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.cuahang.CuaHangActivity;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.util.ArrayList;
import java.util.List;

public class TrangChuActivity extends AppCompatActivity {

    private ConstraintLayout btn_chuyenkhoan, btn_don_hang_quang_cao, cuahang,btn_thong_bao_khoa,btn_logout, btn_doi_pass;
    private DatabaseReference mDatabase;
    private TextView tv_soluong_cho_duyet, tv_tong_so_sp_quang_cao;
    private int tongSoSPChoDuyet;
    private List<Product> sanphamnoibat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        btn_chuyenkhoan = findViewById(R.id.btn_chuyenkhoan);
        btn_don_hang_quang_cao = findViewById(R.id.btn_don_hang_quang_cao);
        tv_soluong_cho_duyet = findViewById(R.id.tv_soluong_cho_duyet);
        tv_tong_so_sp_quang_cao = findViewById(R.id.tv_tong_so_sp_quang_cao);
        btn_thong_bao_khoa = findViewById(R.id.btn_thong_bao_khoa);
        cuahang = findViewById(R.id.cuahang);
        btn_doi_pass = findViewById(R.id.btn_doi_pass);
        btn_logout = findViewById(R.id.btn_logout);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_chuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, ThongTinChuyenKhoanActivity.class);
                startActivity(intent);
            }
        });

        btn_don_hang_quang_cao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, SanPhamQuangCaoActivity.class);
                startActivity(intent);
            }
        });

        cuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, CuaHangActivity.class);
                startActivity(intent);
            }
        });

        btn_thong_bao_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, ThongBaoKhoaCuaHangActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_doi_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, DoiPasswordActivity.class);
                startActivity(intent);
            }
        });


        getFirebase();
    }

    private void getFirebase () {
        mDatabase.child("ChoXacNhan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("sssss", snapshot.getChildrenCount() + "");
                tv_soluong_cho_duyet.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("sanPhamQuangCao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongSoSPChoDuyet = 0;
                for (DataSnapshot data: snapshot.getChildren()) {
                    tongSoSPChoDuyet += data.child("sanpham").getChildrenCount();
                }
                tv_tong_so_sp_quang_cao.setText(tongSoSPChoDuyet+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sanphamnoibat=new ArrayList<>();

        mDatabase.child("CuaHangOder").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    if(snapshot1.getKey().equals("donhangonline"))
                    {
                        for (DataSnapshot snapshot2: snapshot1.getChildren())
                        {
                            if(snapshot2.getKey().equals("dondadat"))
                            {
                                for (DataSnapshot snapshot3: snapshot2.getChildren())
                                {
                                   for (DataSnapshot snapshot4: snapshot3.getChildren())
                                   {

                                       for (DataSnapshot snapshot5: snapshot4.getChildren())
                                       {
                                           if (snapshot5.getKey().equals("sanpham"))
                                           {
                                               for (DataSnapshot snapshot6: snapshot5.getChildren())
                                               {
                                                   Product product= snapshot6.getValue(Product.class);
                                                  if (sanphamnoibat.size()>0)
                                                  {
                                                      for (int i = 0; i < sanphamnoibat.size(); i++) {
                                                          if(product.getNameProduct().equals(sanphamnoibat.get(i).getNameProduct()))
                                                          {
                                                              sanphamnoibat.get(i).setSoluong(sanphamnoibat.get(i).getSoluong()+ product.getSoluong());
                                                          }else {
                                                              sanphamnoibat.add(product);
                                                          }
                                                      }
                                                  }else {
                                                      sanphamnoibat.add(product);
                                                  }

                                                  List<Product> kq= listSapxep(sanphamnoibat);

                                                  mDatabase.child("sanphamnoibat").setValue(kq);

                                               }
                                           }
                                       }

                                   }
                                }
                            }
                        }
                    }


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<Product> listSapxep(List<Product> firstList)
    {
        List<Product> kq=new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            if(kq.size()<5)
            {
                kq.add(firstList.get(i));
            }else {
                boolean flag= false;
                int vitri = -1;
                int min=0;
                for (int j = i; j < kq.size(); j++) {
                    if(firstList.get(i).getSoluong()>kq.get(j).getSoluong()  && kq.get(j).getSoluong()<min)
                    {
                        flag=true;
                        min=kq.get(j).getSoluong();
                        vitri=j;
                    }
                }
                if(flag)
                {
                    kq.remove(vitri);
                    kq.add(firstList.get(i));
                }
            }


        }

        return kq;
    }
}
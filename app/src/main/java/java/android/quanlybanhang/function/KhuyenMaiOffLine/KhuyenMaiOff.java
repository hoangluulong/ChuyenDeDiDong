package java.android.quanlybanhang.function.KhuyenMaiOffLine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterNhomKhachHang;
import java.android.quanlybanhang.Model.KhachHang.NhomKhachHang;
import java.android.quanlybanhang.Model.KhuyenMaiOffModel;
import java.android.quanlybanhang.Model.SanPham.DonViTinh;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.KhachHang.ListNhomKhachHang;
import java.android.quanlybanhang.function.SanPham.AddProduct;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.util.ArrayList;


public class KhuyenMaiOff extends AppCompatActivity {
    private Toolbar toolbar;
    ProgressBar progressBar;
    String id_CuaHang ;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView spnNhomKhachHang;
    private DatabaseReference mDatabase,mDatabase2;
    private ArrayList<NhomKhachHang> nhomKhachHangs;
    private FirebaseDatabase firebaseDatabase;
    private String STR_NKH = "nhomkhachhang";
    private NhomKhachHang nhomKhachHang;
    private ArrayList<String> arrayListKhachHang;
    private  String stamps;
    Button giakhuyenmai,themkhoanggia,btnDialogHuyThemDVT,btnThemDialogThemDVT;
    private Window window, window1;
    private Dialog dialog, dialog1;
    private KhuyenMaiOffModel  khuyenMaiOffModel;
    private EditText giakhuyenmaitu,giakhuyenmaiden,giakhuyenmais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai_off);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang ="CuaHangOder/"+thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thiết lập Khuyến Mãi");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(id_CuaHang).child(STR_NKH);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuyenmaioff");
        spnNhomKhachHang = findViewById(R.id.spnNhomKhachHang);
        giakhuyenmai= findViewById(R.id.giakhuyenmai);
//
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(KhuyenMaiOff.this, "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(KhuyenMaiOff.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        DanhSachNhomKhachHang();
        dialog1 = new Dialog(KhuyenMaiOff.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemgiakhuyenmaioff);
        window1 = dialog1.getWindow();
        giakhuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailongGiaKhuyenMai(10);
            }
        });


    }
    public void DanhSachNhomKhachHang(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListKhachHang = new ArrayList<>();
                if(snapshot.getValue()!=null){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        nhomKhachHang = snapshot1.getValue(NhomKhachHang.class);
                        String nhomkhachHang = nhomKhachHang.getTenNhomKh();
                        arrayListKhachHang.add(nhomkhachHang);
                    }
                    if(arrayListKhachHang.size()!=0){
                        adapter = new ArrayAdapter<String>(KhuyenMaiOff.this,R.layout.support_simple_spinner_dropdown_item,arrayListKhachHang);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                        spnNhomKhachHang.setAdapter(adapter);

                    }
                    spnNhomKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            stamps =parent.getItemAtPosition(position).toString();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dailongGiaKhuyenMai(int gravity) {
        giakhuyenmaitu = dialog1.findViewById(R.id.giakhuyenmaitu);
        giakhuyenmaiden = dialog1.findViewById(R.id.giakhuyenmaiden);
        giakhuyenmais = dialog1.findViewById(R.id.giakhuyenmais);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);

        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        windownAttributes.gravity = gravity;
        window1.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }

        btnDialogHuyThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = giakhuyenmaitu.getText().toString();
                String name1 =giakhuyenmaiden.getText().toString();
                String name2 =giakhuyenmais.getText().toString();
                if (giakhuyenmaitu.getText().toString().isEmpty()) {
                    giakhuyenmaitu.setError("Hãy nhập giá Khuyến mãi từ");
                    giakhuyenmaitu.requestFocus();
                } else if (giakhuyenmaiden.getText().toString().isEmpty()) {
                    giakhuyenmaiden.setError("Hãy nhập giá Khuyến mãi đến");
                    giakhuyenmaiden.requestFocus();
                } else if (giakhuyenmais.getText().toString().isEmpty()) {
                    giakhuyenmais.setError("Hãy nhập giá nhập khuyến mãi");
                    giakhuyenmais.requestFocus();
                }
                else {
                    String id = mDatabase2.push().getKey();
                    khuyenMaiOffModel = new KhuyenMaiOffModel (name,name1,name2,id);
                    mDatabase2.child(id).setValue(khuyenMaiOffModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            giakhuyenmaitu.setText("");
                            giakhuyenmaiden.setText("");
                            giakhuyenmais.setText("");
                            dialog1.dismiss();
                            dialog.show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }


            }
        });
        dialog1.show();


    }

}
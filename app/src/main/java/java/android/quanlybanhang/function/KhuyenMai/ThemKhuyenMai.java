package java.android.quanlybanhang.function.KhuyenMai;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Model.KhuyenMai.KhuyenMai;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ThemKhuyenMai extends AppCompatActivity {
    private AutoCompleteTextView spnLoaiKM;
    private TextView chonLoaiKN,thongtinKM;
    private EditText editGiaKM,editPhantramKM,editQuan,editGia;
    private Button btnThemKM,btnHuyKM,btnThemKM1,btnHuyKM1,btnThemKM2,btnHuyKM2;
    private Dialog dialog,dialog1;
    private Window window,window1;
    private DatabaseReference mDatabase;
    private KhuyenMai khuyenMai;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_KM = "khuyenmai";
    private Double gia;
    private Double phanTram;
    private String quan;
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;
    private int vitri = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themkhuyenmaikhachhang);

        spnLoaiKM = findViewById(R.id.spinner_nhomkhuyenmai);
        thongtinKM = findViewById(R.id.edtThongtinkhuyenmai);
        btnThemKM = findViewById(R.id.btnTaoKhuyenMai);
        btnHuyKM = findViewById(R.id.btnhuyTaoKhuyenMai);

        dialog = new Dialog(ThemKhuyenMai.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_khuyenmai1);
        window = dialog.getWindow();
        dialog1 = new Dialog(ThemKhuyenMai.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_khuyenmai2);
        window1 = dialog1.getWindow();
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_KM);
        arrayList.add(1);
        arrayList.add(2);
        adapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        spnLoaiKM.setAdapter(adapter);
        Click();
        ThemKhuyenMai();
        btnHuyKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(ThemKhuyenMai.this, ListKhuyenMai.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void dialogKM1(int gravity){
        editGiaKM = dialog.findViewById(R.id.edtGiakhuyenmai);
        editPhantramKM = dialog.findViewById(R.id.edtphantramkhuyenmai);
        btnHuyKM1 = dialog.findViewById(R.id.btnhuyDiaLogThemKM1);
        btnThemKM1 = dialog.findViewById(R.id.btnthemDiaLogThemKM1);

        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        btnHuyKM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemKM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editGiaKM.getText().toString().isEmpty()){
                    editGiaKM.setError("Hãy nhập giá khuyến mãi");
                    editGiaKM.requestFocus();
                }
                else if(editPhantramKM.getText().toString().isEmpty()){
                    editPhantramKM.setError("Hãy nhập phần trăm");

                    if(Double.parseDouble(editPhantramKM.getText().toString()) > 100){
                        editPhantramKM.setError("Không đúng định dạng");
                        editPhantramKM.requestFocus();
                    }
                }
                else {
                     gia = Double.parseDouble(editGiaKM.getText().toString());
                     phanTram = Double.parseDouble(editPhantramKM.getText().toString());
                     thongtinKM.setText("Giá: "+gia+" khuyến mãi: "+phanTram+"%");
                     editGiaKM.setText("");
                     editPhantramKM.setText("");
                     dialog.dismiss();
                }

            }
        });
        dialog.show();

    }

    public void dialogKM2(int gravity){
        editQuan = dialog1.findViewById(R.id.edtquanhuyen);
        editGia = dialog1.findViewById(R.id.edtgia);
        btnHuyKM2 = dialog1.findViewById(R.id.btnhuyDiaLogThemKM2);
        btnThemKM2 = dialog1.findViewById(R.id.btnthemDiaLogThemKM2);


        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        window1.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }
        btnHuyKM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemKM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editQuan.getText().toString().isEmpty()){
                    editQuan.setError("Hãy nhập giá");
                }else  if(editQuan.getText().toString().isEmpty()){
                    editQuan.setError("Hãy nhập quận/huyện");
                    editQuan.requestFocus();
                }else{
                    quan = editQuan.getText().toString();
                    gia = Double.parseDouble(editGia.getText().toString());
                    thongtinKM.setText("Giá: "+gia+" Địa chỉ: "+quan);
                    editQuan.setText("");
                    editGia.setText("");
                    dialog1.dismiss();
                }
            }
        });
        dialog1.show();

    }

    public void Click(){
        spnLoaiKM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("1")){
                    vitri = 1;
                    dialogKM1(Gravity.CENTER);
                }
                else {
                    vitri = 2;
                    dialogKM2(Gravity.CENTER);
                }
            }
        });
    }

    public void ThemKhuyenMai(){
      btnThemKM.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(vitri == 1){
                  khuyenMai = new KhuyenMai(gia,STR_CUAHANG,vitri,phanTram);
              }
              else {
                  vitri = 2;
                  khuyenMai = new KhuyenMai(STR_CUAHANG,vitri,quan,gia);
              }
              mDatabase.child(STR_CUAHANG).push().setValue(khuyenMai);
              Intent intent = new Intent();
              intent = new Intent(ThemKhuyenMai.this, ListKhuyenMai.class);
              startActivity(intent);
              finish();
          }
      });

    }
}

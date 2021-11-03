package java.android.quanlybanhang.function.NhanVien;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;

import java.android.quanlybanhang.Model.NhanVien_CaLam.CaLam;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.Model.Product;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.android.quanlybanhang.function.SanPham.SuaSanPhamActivity;
import java.util.ArrayList;

public class ActivityUpdateNhanVien extends AppCompatActivity {

    private EditText editHoTen, editPhone;
    private CheckBox checkBep,checkQLNV,checkQLSP,checkOder ,checkThuchi;
    private TextView Th2,Th3,Th4,Th5,Th6,Th7,chuNhat;
    private CaLam caLam = new CaLam();
    private   Boolean QUANLYSP = false;
    private  Boolean QUANLYNV = false;
    private  Boolean THUCHI = false;
    private  Boolean ODER = false;
    private  Boolean BEP = false;
    private Button btnCapNhat, btnHuy,btnCapNhatDialog,btnHuyDialog,checkBoxCaSang, checkBoxCaChieu, checkBoxCaToi;
    private NhanVien nhanVien;
    private ArrayList<Boolean> congViec = new ArrayList<>(5);
    private  Boolean T2 = false;
    private  Boolean T3 = false;
    private  Boolean T4 = false;
    private  Boolean T5 = false;
    private  Boolean T6 = false;
    private  Boolean T7 = false;
    private  Boolean CN = false;
    private Dialog dialog;
    private Window window;
    private Boolean[][] mangNgay2 = new Boolean[3][7];
    private Boolean[] cSang = new Boolean[] {false, false, false, false, false,false,false};
    private Boolean[] cTrua = new Boolean[] {false, false, false, false, false,false,false};
    private Boolean[] cToi = new Boolean[] {false, false, false, false, false,false,false};
    private int loai = 1; //1: casang, 2: trua, 3: toi
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_USER = "user";
    private DatabaseReference mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenhanvien);
        editHoTen = findViewById(R.id.edtTenNhanVienUpdate);
        editPhone = findViewById(R.id.edtPhoneUpdate);
        checkQLNV = findViewById(R.id.checkcongviecQuanlynhanvienUpdate);
        checkQLSP = findViewById(R.id.checkcongviecQuanlysanphamUpdate);
        checkBep = findViewById(R.id.checkcongviecBepUpdate);
        checkOder = findViewById(R.id.checkcongviecOderUpdate);
        checkThuchi = findViewById(R.id.checkcongviecThuchiUpdate);
        btnCapNhat = findViewById(R.id.btnThayDoiNhanVien);
        btnHuy = findViewById(R.id.btnhuyThayDoi);
        checkBoxCaSang = findViewById(R.id.checkCaSangUpdate);
        checkBoxCaChieu = findViewById(R.id.checkCaChieuUpdate);
        checkBoxCaToi = findViewById(R.id.checkCaToiUpdate);
        nhanVien = (NhanVien) getIntent().getSerializableExtra("Key_arrayNV");

        //dialog

        dialog = new Dialog(ActivityUpdateNhanVien.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thutrongtuan);
        window = dialog.getWindow();
        btnHuyDialog = dialog.findViewById(R.id.btnhuyDiaLogNgay);
        btnCapNhatDialog = dialog.findViewById(R.id.btnthemDiaLogNgay);

        Th2 = dialog.findViewById(R.id.checkBox2);
        Th3 = dialog.findViewById(R.id.checkBox3);
        Th4 = dialog.findViewById(R.id.checkBox4);
        Th5 = dialog.findViewById(R.id.checkBox5);
        Th6 = dialog.findViewById(R.id.checkBox6);
        Th7 = dialog.findViewById(R.id.checkBox7);
        chuNhat = dialog.findViewById(R.id.checkBox8);
        //setDulieu
        editHoTen.setText(nhanVien.getUsername());
        editPhone.setText(nhanVien.getPhone());
        congViec = nhanVien.getChucVu();
        caLam = nhanVien.getCaLam();
        cSang = caLam.getCaSang().toArray(new Boolean[0]);
        cTrua = caLam.getCaChieu().toArray(new Boolean[0]);
        cToi = caLam.getCaToi().toArray(new Boolean[0]);

        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 7; j ++) {
                mangNgay2[i][j] = false;
            }
        }
        //firebase
        mData = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_USER);
        onClickThu();
        getChucVu();
        getCaSang();
        getCaChieu();
        getCaToi();
        checkBoxCaSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 1;
                Casang(Gravity.CENTER);
            }
        });
        checkBoxCaChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 2;
                Cachieu(Gravity.CENTER);
            }
        });
        checkBoxCaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 3;
                Catoi(Gravity.CENTER);
            }
        });
        CapNhatNhanVien();
    }


    public void Casang(int gravity){
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

        if(cSang[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }



        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 7; i++) {
                    mangNgay2[0][i] = cSang[i];
                }

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void Cachieu(int gravity){

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

        if(cTrua[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }


        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i =0; i < 7; i ++) {
                    mangNgay2[1][i] = cTrua[i];
                }
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    public void Catoi(int gravity){
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

        if(cToi[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }


        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 7; i++) {
                    mangNgay2[2][i] = cToi[i];
                }

                dialog.dismiss();
            }


        });
        dialog.show();
    }

    public void getChucVu(){
        if(congViec.get(0) == true){
            checkQLNV.setChecked(true);
        } else {
            checkQLNV.setChecked(false);
        }
        if (congViec.get(1) == true){
            checkQLSP.setChecked(true);
        }else {
            checkQLSP.setChecked(false);
        }
        if (congViec.get(2) == true){
            checkThuchi.setChecked(true);
        }else {
            checkThuchi.setChecked(false);
        }
        if (congViec.get(3) == true){
            checkBep.setChecked(true);
        }else {
            checkBep.setChecked(false);
        }
        if (congViec.get(4) == true){
            checkOder.setChecked(true);
        }else {
            checkOder.setChecked(false);
        }
    }

    public void getCaSang(){
        if(cSang[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }
    }

    public void getCaChieu(){
        if(cTrua[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }

    }

    public void getCaToi(){
        if(cToi[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }
    }

    public void onClickThu(){
        Th2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang[0] == false){
                        T2 = true;
                        cSang[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
                        cSang[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[0] == false){
                        T2 = true;
                        cTrua[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
                        cTrua[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[0] == false){
                        T2 = true;
//                        cToi[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
//                        cToi[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        Th3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang[1] == false){
                        T3 = true;
                        cSang[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cSang[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[1] == false){
                        T3 = true;
                        cTrua[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cTrua[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[1] == false){
                        T3 = true;
                        cToi[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cToi[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang[2] == false){
                        T4 = true;
                        cSang[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cSang[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[2] == false){
                        T4 = true;
                        cTrua[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cSang[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[2] == false){
                        T4 = true;
                        cToi[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cToi[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang[3] == false){
                        T5= true;
                        cSang[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cSang[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[3] == false){
                        T5= true;
                        cTrua[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cTrua[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[3] == false){
                        T5= true;
                        cToi[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cToi[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        Th6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang[4] == false){
                        T6= true;
                        cSang[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cSang[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[4] == false){
                        T6= true;
                        cTrua[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cTrua[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[4] == false){
                        T6= true;
                        cToi[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cToi[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang[5] == false){
                        T7= true;
                        cSang[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cSang[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[5] == false){
                        T7= true;
                        cTrua[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cTrua[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[5] == false){
                        T7= true;
                        cToi[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cToi[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        chuNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang[6] == false){
                        CN= true;
                        cSang[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cSang[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua[6] == false){
                        CN= true;
                        cTrua[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cTrua[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi[6] == false){
                        CN= true;
                        cToi[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cToi[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
    }

    public void CapNhatNhanVien(){
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editHoTen.getText().toString().isEmpty()){
                    Toast.makeText(ActivityUpdateNhanVien.this,"Hãy nhập tên nhân viên!", Toast.LENGTH_LONG).show();
                }else if(editPhone.getText().toString().isEmpty()){
                    Toast.makeText(ActivityUpdateNhanVien.this,"Hãy nhập số điện thoại!", Toast.LENGTH_LONG).show();
                }else {
                    //cong viec
                    if (checkQLNV.isChecked()){
                        QUANLYNV = true;
                        congViec.set(0,QUANLYNV);
                    }
                    if (checkQLSP.isChecked()){
                        QUANLYSP = true;
                        congViec.set(1,QUANLYSP);
                    }
                    if (checkThuchi.isChecked()){
                        THUCHI = true;
                        congViec.set(2,THUCHI);
                    }
                    if (checkBep.isChecked()){
                        BEP = true;
                        congViec.set(3,BEP);
                    }
                    if (checkOder.isChecked()){
                        ODER = true;
                        congViec.set(4,ODER);
                    }
                    caLam.set1(mangNgay2[0]);
                    caLam.set2(mangNgay2[1]);
                    caLam.set3(mangNgay2[2]);
                    String name = editHoTen.getText().toString();
                    String phone = editPhone.getText().toString();

                    mData.child(nhanVien.getId()).child("username").setValue(name);
                    mData.child(nhanVien.getId()).child("phone").setValue(phone);
                    mData.child(nhanVien.getId()).child("caLam").setValue(caLam);
                    mData.child(nhanVien.getId()).child("chucVu").setValue(congViec);
                    Intent intent = new Intent();
                    intent = new Intent(ActivityUpdateNhanVien.this, ListNhanVien.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

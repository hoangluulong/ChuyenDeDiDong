package java.android.quanlybanhang.function.NhanVien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Data.CaLam;
import java.android.quanlybanhang.Data.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.AddCategory;
import java.android.quanlybanhang.function.SanPham.ListCategory;
import java.util.ArrayList;
import java.util.List;

public class AddNhanVien extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mData;
    private FirebaseUser user;
    private AlertDialog.Builder builder;
    private EditText edtTenNhanVien, edtEmail, edtPassword, edtPhone;
    private Button btnTaoNhanVien,checkBoxCaSang, checkBoxCaChieu, checkBoxCaToi;
    private CaLam caLam = new CaLam();
    private   Boolean QUANLYSP = false;
    private  Boolean QUANLYNV = false;
    private  Boolean THUCHI = false;
    private  Boolean ODER = false;
    private  Boolean BEP = false;
    private View customLayout;
    private List<CheckBox> checkBoxes;
    private ArrayList<Boolean> congViec;
    private  CheckBox Th2,Th3,Th4,Th5,Th6,Th7,chuNhat,checkBep,checkQLNV,checkQLSP,checkOder ,checkThuchi;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_USER = "user";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themnhanvien);
        builder = new android.app.AlertDialog.Builder(AddNhanVien.this);
        LayoutInflater inflater = AddNhanVien.this.getLayoutInflater();
        customLayout = inflater.inflate(R.layout.acivity_dailongtu, null);
        builder.setView(customLayout);
        //ca lam
        checkBoxes = new ArrayList<>();
        Th2 = customLayout.findViewById(R.id.checkBox2);
        Th3 = customLayout.findViewById(R.id.checkBox3);
        Th4 = customLayout.findViewById(R.id.checkBox4);
        Th5 = customLayout.findViewById(R.id.checkBox5);
        Th6 = customLayout.findViewById(R.id.checkBox6);
        Th7 = customLayout.findViewById(R.id.checkBox7);
        chuNhat = customLayout.findViewById(R.id.checkBox8);
        checkBoxes.add(Th2);
        checkBoxes.add(Th3);
        checkBoxes.add(Th4);
        checkBoxes.add(Th5);
        checkBoxes.add(Th6);
        checkBoxes.add(Th7);
        checkBoxes.add(chuNhat);
        //congviec

        checkBep = findViewById(R.id.checkcongviecBep);
        checkQLSP = findViewById(R.id.checkcongviecQuanlysanpham);
        checkQLNV = findViewById(R.id.checkcongviecQuanlynhanvien);
        checkOder = findViewById(R.id.checkcongviecOder);
        checkThuchi =  findViewById(R.id.checkcongviecThuchi);

        //firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        edtTenNhanVien = findViewById(R.id.edtTenNhanVien);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        checkBoxCaSang = findViewById(R.id.checkCaSang);
        checkBoxCaChieu = findViewById(R.id.checkCaChieu);
        checkBoxCaToi = findViewById(R.id.checkCaToi);

        btnTaoNhanVien = findViewById(R.id.btnTaoUser);
        mFirebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_USER);

        Cachieu();
        Casang();
        Catoi();
        Taonhanvien();
    }

    public void Casang(){
        checkBoxCaSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customLayout.getParent() != null){
                    ((ViewGroup)customLayout.getParent()).removeView(customLayout);
                }
                if(caLam.getCaSang().size() > 0){
                    for(int i =0; i<checkBoxes.size();i++){
                        if(caLam.getCaSang().get(i)){
                            checkBoxes.get(i).setChecked(true);
                        }
                        else {
                            checkBoxes.get(i).setChecked(false);
                        }
                    }
                }

                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i =0;i<checkBoxes.toArray().length;i++){
                            if(checkBoxes.get(i).isChecked()){
                                caLam.getCaSang().add(true);
                            }
                            else {
                                caLam.getCaSang().add(false);
                            }
                            checkBoxes.get(i).setChecked(false);
                        }

                    }

                });

                builder.show();
            }
        });
    }

    public void Cachieu(){
        checkBoxCaChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customLayout.getParent() != null){
                    ((ViewGroup)customLayout.getParent()).removeView(customLayout);
                }
                if(caLam.getCaChieu().size() > 0){
                    for(int i =0; i<checkBoxes.size();i++){
                        if(caLam.getCaChieu().get(i)){
                            checkBoxes.get(i).setChecked(true);
                        }
                        else {
                            checkBoxes.get(i).setChecked(false);
                        }
                    }
                }

                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i =0;i<checkBoxes.toArray().length;i++){
                            if(checkBoxes.get(i).isChecked()){
                                caLam.getCaChieu().add(true);
                            }
                            else {
                                caLam.getCaChieu().add(false);
                            }
                            checkBoxes.get(i).setChecked(false);
                        }

                    }

                });

                builder.show();


            }
        });
    }

    public void Catoi(){
        checkBoxCaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customLayout.getParent() != null){
                    ((ViewGroup)customLayout.getParent()).removeView(customLayout);
                }

                if(caLam.getCaToi().size() > 0){
                    for(int i =0; i<checkBoxes.size();i++){
                        if(caLam.getCaToi().get(i)){
                            checkBoxes.get(i).setChecked(true);
                        }
                        else {
                            checkBoxes.get(i).setChecked(false);
                        }
                    }
                }

                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i =0;i<checkBoxes.toArray().length;i++){
                            if(checkBoxes.get(i).isChecked()){
                                caLam.getCaToi().add(true);
                            }
                            else {
                                caLam.getCaToi().add(false);
                            }
                            checkBoxes.get(i).setChecked(false);
                        }

                    }

                });

                builder.show();
            }
        });
    }

    public void Taonhanvien(){
        btnTaoNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();
                mFirebaseAuth = FirebaseAuth.getInstance();

                if(email.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập Email!", Toast.LENGTH_LONG).show();
                }else if(pass.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập password!", Toast.LENGTH_LONG).show();
                } else if(edtTenNhanVien.getText().toString().isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập tên nhân viên!", Toast.LENGTH_LONG).show();
                }else if(edtPhone.getText().toString().isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập số điện thoại!", Toast.LENGTH_LONG).show();
                }else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Fialdssss Are Empty!", Toast.LENGTH_LONG).show();
                }else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(AddNhanVien.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddNhanVien.this, "SignUp UnSuccessful, plese Try Again ", Toast.LENGTH_SHORT).show();
                            }
                            //cong viec
                            congViec = new ArrayList<>();
                            if (checkQLNV.isChecked()){
                                QUANLYNV = true;
                                congViec.add(QUANLYNV);
                            }
                            if (checkQLSP.isChecked()){
                                QUANLYSP = true;
                                congViec.add(QUANLYSP);
                            }
                            if (checkBep.isChecked()){
                                BEP = true;
                                congViec.add(BEP);
                            }
                            if (checkOder.isChecked()){
                                ODER = true;
                                congViec.add(ODER);
                            }
                            if (checkThuchi.isChecked()){
                                THUCHI = true;
                                congViec.add(THUCHI);
                            }

                            String id = mData.push().getKey();
                            String name = edtTenNhanVien.getText().toString();
                            String phone = edtPhone.getText().toString();
                            NhanVien nhanVien = new NhanVien(name,email,congViec,caLam,phone,id);
                            mData.child(id).setValue(nhanVien);

                            edtEmail.setText("");
                            edtPassword.setText("");
                            edtTenNhanVien.setText("");
                            edtPhone.setText("");
                            Intent intent = new Intent();
                            intent = new Intent(AddNhanVien.this, ListNhanVien.class);
                            startActivity(intent);
                            finish();

                        }

                    });
                }else {
                    Toast.makeText(AddNhanVien.this,"Error Occurred!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

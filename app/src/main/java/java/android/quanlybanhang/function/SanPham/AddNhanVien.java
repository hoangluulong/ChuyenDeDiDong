package java.android.quanlybanhang.function.SanPham;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.android.quanlybanhang.Data.NhanVien;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class AddNhanVien extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mData;
    private FirebaseUser user;

    private EditText edtTenNhanVien, edtEmail, edtPassword, edtTenQuan, edtPhone;
    private RadioButton checkQuanLy, checkPhucVu, checkBep, checkShiper;
    private CheckBox checkBoxCaSang, checkBoxCaChieu, checkBoxCaToi;
    private Button btnTaoNhanVien;
    private static final int QUANLY = 1;
    private static final int PHUCVU = 2;
    private static final int BEP = 3;
    private static final int SHIPPER = 4;
    private static final int CASANG = 1;
    private static final int CACHIEU = 2;
    private static final int CATOI = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themnhanvien);

        mFirebaseAuth = FirebaseAuth.getInstance();

        edtTenNhanVien = findViewById(R.id.edtTenNhanVien);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
//        edtTenQuan = findViewById(R.id.edtTenQuan);
        edtPhone = findViewById(R.id.edtPhone);

//        checkQuanLy = findViewById(R.id.checkQuanLy);
//        checkBep = findViewById(R.id.checkBepBar);
//        checkShiper = findViewById(R.id.checkThuNgan);
//        checkPhucVu = findViewById(R.id.checkPhucVu);
//
//        checkBoxCaSang = findViewById(R.id.checkCaSang);
//        checkBoxCaChieu = findViewById(R.id.checkCaChieu);
//        checkBoxCaToi = findViewById(R.id.checkCaToi);

        btnTaoNhanVien = findViewById(R.id.btnTaoUser);
        mFirebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("user");

        String mail = "";
        if (user != null) {
            mail = user.getEmail();
        }

        final int[] chucVu = {QUANLY, SHIPPER, BEP, PHUCVU};
        final int[] caLamViec = {CASANG};

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
                            if (checkBep.isChecked()) {
                                chucVu[0] = BEP;
                            } else if (checkShiper.isChecked()) {
                                chucVu[0] = SHIPPER;
                            } else if (checkQuanLy.isChecked()) {
                                chucVu[0] = QUANLY;
                            } else if (checkPhucVu.isChecked()) {
                                chucVu[0] = PHUCVU;
                            }
                            ArrayList<Integer> listCaLamViec = new ArrayList<Integer>();
                            if (checkBoxCaSang.isChecked()) {
                                listCaLamViec.add(CASANG);
                            }
                            if (checkBoxCaChieu.isChecked()) {
                                listCaLamViec.add(CACHIEU);
                            }
                            if (checkBoxCaToi.isChecked()) {
                                listCaLamViec.add(CATOI);
                            }
                            String id = mData.push().getKey();
                            String name = edtTenNhanVien.getText().toString();
                            String phone = edtPhone.getText().toString();
                            String tenquan = edtTenQuan.getText().toString();
                            int calam = caLamViec[0];
                            int chucvu = chucVu[0];
                            NhanVien nhanVien = new NhanVien(name,email,chucvu,calam,phone,tenquan);
                            mData.child(id).setValue(nhanVien);

                            edtEmail.setText("");
                            edtPassword.setText("");
                            edtTenNhanVien.setText("");
                            edtPhone.setText("");
                            edtTenQuan.setText("");
                            checkBoxCaChieu.setChecked(false);
                            checkBoxCaSang.setChecked(false);
                            checkBoxCaToi.setChecked(false);
                        }

                    });
                }else {
                    Toast.makeText(AddNhanVien.this,"Error Occurred!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

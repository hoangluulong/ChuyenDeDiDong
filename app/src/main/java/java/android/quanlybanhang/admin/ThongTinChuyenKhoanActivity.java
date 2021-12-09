package java.android.quanlybanhang.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;

public class ThongTinChuyenKhoanActivity extends AppCompatActivity {
    private Button btn_luu;
    private TextInputEditText edt_thong_tin_chuyen_khoan;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chuyen_khoan);
        btn_luu = findViewById(R.id.btn_luu);
        edt_thong_tin_chuyen_khoan = findViewById(R.id.edt_thong_tin_chuyen_khoan);
        mDatabase = FirebaseDatabase.getInstance().getReference("ADMIN/ThongTinChuyenKhoan");
        btn_luu.setEnabled(false);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    edt_thong_tin_chuyen_khoan.setText(snapshot.getValue().toString());
                }
                btn_luu.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                btn_luu.setEnabled(true);
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_luu.setEnabled(false);
                String text = edt_thong_tin_chuyen_khoan.getText().toString();

                if (text.isEmpty()) {
                    edt_thong_tin_chuyen_khoan.setError("Hãy nhập thông tin");
                    edt_thong_tin_chuyen_khoan.requestFocus();
                }else {
                    mDatabase.setValue(text).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ThongTinChuyenKhoanActivity.this, "Đã lưu!", Toast.LENGTH_LONG).show();
                            btn_luu.setEnabled(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThongTinChuyenKhoanActivity.this, "Thất bại!", Toast.LENGTH_LONG).show();
                            btn_luu.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
package java.android.quanlybanhang.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.login.LoginAdminActivity;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DoiPasswordActivity extends AppCompatActivity {

    private TextInputEditText edt_password, edt_password_new, edt_password_new2;
    private Button doi_mat_khau;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_password);
        edt_password = findViewById(R.id.edt_password);
        edt_password_new = findViewById(R.id.edt_password_new);
        doi_mat_khau = findViewById(R.id.doi_mat_khau);
        edt_password_new2 = findViewById(R.id.edt_password_new2);
        mDatabase = FirebaseDatabase.getInstance().getReference("ADMIN/Login");


        doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPassword();
            }
        });

    }

    private void CheckPassword () {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    String pass = snapshot.getValue().toString();
                    if (getMd5(edt_password.getText().toString()).equals(pass)) {
                        String passmoi = edt_password_new.getText().toString();
                        String passmoi2 = edt_password_new2.getText().toString();
                        if (passmoi.isEmpty()) {
                            edt_password_new.setError("Chưa nhập");
                            edt_password_new.requestFocus();
                        }else if (passmoi.length() < 6) {
                            edt_password_new.setError("6 ký tự trở lên");
                            edt_password_new.requestFocus();
                        }else if (!passmoi.equals(passmoi2)) {
                            edt_password_new2.setError("Không giống nhau");
                            edt_password_new2.requestFocus();
                        } else {
                            mDatabase.setValue(getMd5(passmoi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DoiPasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DoiPasswordActivity.this, "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }else {
                        edt_password.setError("Password sai!");
                        edt_password.requestFocus();
                    }
                }else {
                    Toast.makeText(DoiPasswordActivity.this, "Đã xảy ra sự cố, kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    };

    public static String getMd5(String input)
    {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
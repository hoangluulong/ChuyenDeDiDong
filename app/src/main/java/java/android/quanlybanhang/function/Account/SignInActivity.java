package java.android.quanlybanhang.function.Account;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Model.CuaHangSignIn;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.DbBaoCao;
import java.android.quanlybanhang.function.MainActivity;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sigupNow, login;
    private TextView logoText, sloganText, forgetPass;
    private ImageView imageView;
    private TextInputEditText username, password;
    private CardView google;

    private ArrayList<CuaHangSignIn> dataAccount;
    private String idUser;
    private Boolean thietLap;
    private Boolean checkThietLap = false;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    //SQLite
    private DbBaoCao dataSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sigupNow = findViewById(R.id.btn_signup_now);
        login = findViewById(R.id.btn_login);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        username = findViewById(R.id.edt_email_username);
        password = findViewById(R.id.edt_password);
        imageView = findViewById(R.id.imageView);
        forgetPass = findViewById(R.id.lbl_forget_pass);
//        facebook =  findViewById(R.id.btn_facebook);
        google = findViewById(R.id.btn_google);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        Intent intent = getIntent();
        String emailIntent = intent.getStringExtra("email");

        if (emailIntent != null) {
            username.setText(emailIntent);
            sloganText.setText("Sign up succes! Sign in to continue...");
        }

//        createRequest();
        mAuth = FirebaseAuth.getInstance();

        //setOnclick
//        facebook.setOnClickListener(this);
        sigupNow.setOnClickListener(this);
        google.setOnClickListener(this);
//        facebook.setOnClickListener(this);
        login.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == sigupNow) {
            callSignup();
        } else if (v == google) {
//            signIn();
        } else if (v == login) {
            login();
        } else if (v == forgetPass) {
            callForgetPassword();
        }
    }

    private void callSignup() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);

        Pair[] pairs = new Pair[9];

        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_welcome");
        pairs[2] = new Pair<View, String>(sloganText, "logo_signin");
        pairs[3] = new Pair<View, String>(username, "edt_username");
        pairs[4] = new Pair<View, String>(password, "edt_password");
        pairs[5] = new Pair<View, String>(forgetPass, "lbl_forget");
        pairs[6] = new Pair<View, String>(login, "button_sign");
        pairs[7] = new Pair<View, String>(sigupNow, "btn_signup_now");
        pairs[8] = new Pair<View, String>(google, "button_google");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }

    private void callForgetPassword() {
        Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);

        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_welcome");
        pairs[2] = new Pair<View, String>(sloganText, "logo_signin");
        pairs[3] = new Pair<View, String>(username, "edt_username");
        pairs[4] = new Pair<View, String>(login, "button_sign");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }

    // onclick login
    private void login() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        if (email.isEmpty()) {
            username.setError("Plese enter email id");
            username.requestFocus();
        } else if (pass.isEmpty()) {
            password.setError("Plese enter your password");
            password.requestFocus();
        } else if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Fialds Are Empty!", Toast.LENGTH_LONG).show();
        } else if (!(email.isEmpty() && pass.isEmpty())) {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Signin error", Toast.LENGTH_SHORT).show();
                    } else {
                        idUser = mAuth.getUid();

                        getDataAccount(idUser);

                        delaySignin(idUser);
                    }

                }
            });
        } else {
            Toast.makeText(SignInActivity.this, "Error Occurred!", Toast.LENGTH_LONG).show();
        }
    }

    private void getDataAccount(String UID) {

        dataAccount = new ArrayList<>();
        mFirebaseDatabase.child("ACCOUNT_LOGIN/" + UID + "/CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String name = snap.child("name").getValue().toString();
                    String chucvu = snap.child("ChucVu").getValue().toString();
                    String ID = snap.child("ID").getValue().toString();

                    dataAccount.add(new CuaHangSignIn(chucvu, ID, name));
                }
            }

            //      hoanghuulong@gmail.com
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BBB", "onCancelled");
            }

        });
    }

    int soVongLap = 50;
    private void delaySignin(String UID) {
        if ( soVongLap > 0){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dataAccount.size() >= 1){
                        soVongLap = 50;
                        if (dataAccount.size() > 1) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("LIST_CHINHANH",(ArrayList<CuaHangSignIn>) dataAccount);
                            bundle.putString("ID_USER" , UID );
                            Intent intent1 = new Intent(SignInActivity.this, ChiNhanhSignInActivity.class);
                            intent1.putExtras(bundle);
                            startActivity(intent1);
                            finish();
                        }else{
                            getDataThietLap();
                            delayDataThietLap(UID);
                        }
                    }else{
                        delaySignin(UID);
                    }
                }
            }, 100);
        }else return;

        soVongLap--;
    }

    private void getDataThietLap() {
        mFirebaseDatabase.child("CuaHangOder/"+dataAccount.get(0).getID()+"/ThongTinCuaHang/ThietLap").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thietLap = (Boolean) snapshot.getValue();
                Log.d("AAA", thietLap+"");
                checkThietLap = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void delayDataThietLap(String UID) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkThietLap == true) {
                    Log.d("AAA delay", "delayDataThietLap");
                    if (thietLap == true) {
                        checkThietLap = false;
                        Bundle bundle = new Bundle();
                        bundle.putString("ID_STORE", UID);
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtras(intent);
                        startActivity(intent);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("ID_USER" , UID );
                        Toast.makeText(SignInActivity.this, "Signup succes", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, StoreSetting.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }

                    finish();
                }else {
                    delayDataThietLap(UID);
                }
            }
        }, 100);
    }

    private void getDataUser (){

    }

    /**
     * SQLite
     * gọi hàm MangNgay, CustomNgay2
     */
    private void DatabaseSQlite() {
        // Tạo database
        dataSql = new DbBaoCao(this, "app_database.sqlite", null, 1);
        dataSql.QueryData("CREATE TABLE IF NOT EXISTS Test(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Ten, " +
                "ChucVu, " +
                "Quyen, ");

        Cursor dataHienThiBaoCao = dataSql.GetData("SELECT * FROM Test");

        if (dataHienThiBaoCao.getCount() > 0) {
            while (dataHienThiBaoCao.moveToNext()) {
                //dataSql.QueryData("UPDATE CongViec SET NgayBatDau = 'Long' WHERE Id = '5'");
            }
        } else {
            dataSql.QueryData("INSERT INTO Test VALUES(null, '" + "" + "', '" + "" + "', 1)");
        }
    }
}
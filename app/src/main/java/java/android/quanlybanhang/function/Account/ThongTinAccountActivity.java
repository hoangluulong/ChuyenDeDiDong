package java.android.quanlybanhang.function.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private String STR_CUAHANG;
    private String ID_USER;
    private TextView userName, usernameSmoll, email, soDienThoai;
    private TextView mondaySang, tuesdaySang, wednesdaySang, thursdaySang, fridaySang, saturdaySang, sundaySang;
    private TextView mondayChieu, tuesdayChieu, wednesdayChieu, thursdayChieu, fridayChieu, saturdayChieu, sundayChieu;
    private TextView mondayToi, tuesdayToi, wednesdayToi, thursdayToi, fridayToi, saturdayToi, sundayToi;
    private CircleImageView imageAvata;
    private ImageView editIMG;
    private ProgressBar progressbar;
    private ScrollView scrollView;
    ThongTinCuaHangSql thongTinCuaHangSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_account);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        STR_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        ID_USER = thongTinCuaHangSql.IDUser();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/"+STR_CUAHANG).child("user/"+ID_USER);
        getFirebase();
    }

    private void getFirebase() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() !=  null) {
                    NhanVien nhanVien  = snapshot.getValue(NhanVien.class);
                    Log.d("qq", nhanVien.getUsername());
                    soDienThoai.setText(nhanVien.getPhone());
                    userName.setText(nhanVien.getUsername());
                    usernameSmoll.setText(nhanVien.getUsername());
                    email.setText(nhanVien.getEmail());
                    if (nhanVien.getCaLam().getCaSang().get(0) == true) {
                        mondaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(1) == true) {
                        tuesdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(2) == true) {
                        wednesdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(3) == true) {
                        thursdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(4) == true) {
                        fridaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(5) == true) {
                        saturdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(6) == true) {
                        sundaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                    if (nhanVien.getCaLam().getCaChieu().get(0) == true) {
                        mondayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(1) == true) {
                        tuesdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(2) == true) {
                        wednesdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(3) == true) {
                        thursdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(4) == true) {
                        fridayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(5) == true) {
                        saturdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(6) == true) {
                        sundayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                    if (nhanVien.getCaLam().getCaToi().get(0) == true) {
                        mondayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(1) == true) {
                        tuesdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(2) == true) {
                        wednesdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(3) == true) {
                        thursdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(4) == true) {
                        fridayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(5) == true) {
                        saturdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(6) == true) {
                        sundayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                }else {
                }
                scrollView.setAlpha(1);
                progressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void IDLayout() {
        imageAvata = findViewById(R.id.imageAvata);
        editIMG = findViewById(R.id.editIMG);
        userName = findViewById(R.id.userName);
        usernameSmoll = findViewById(R.id.usernameSmoll);
        email = findViewById(R.id.email);
        soDienThoai = findViewById(R.id.soDienThoai);
        editIMG = findViewById(R.id.editIMG);

        mondaySang = findViewById(R.id.mondaySang);
        tuesdaySang = findViewById(R.id.tuesdaySang);
        wednesdaySang = findViewById(R.id.wednesdaySang);
        thursdaySang = findViewById(R.id.thursdaySang);
        fridaySang = findViewById(R.id.fridaySang);
        saturdaySang = findViewById(R.id.saturdaySang);
        sundaySang = findViewById(R.id.sundaySang);

        mondayChieu = findViewById(R.id.mondayChieu);
        tuesdayChieu = findViewById(R.id.tuesdayChieu);
        wednesdayChieu = findViewById(R.id.wednesdayChieu);
        thursdayChieu = findViewById(R.id.thursdayChieu);
        fridayChieu = findViewById(R.id.fridayChieu);
        saturdayChieu = findViewById(R.id.saturdayChieu);
        sundayChieu = findViewById(R.id.sundayChieu);

        mondayToi = findViewById(R.id.mondayToi);
        tuesdayToi = findViewById(R.id.tuesdayToi);
        wednesdayToi = findViewById(R.id.wednesdayToi);
        thursdayToi = findViewById(R.id.thursdayToi);
        fridayToi = findViewById(R.id.fridayToi);
        saturdayToi = findViewById(R.id.saturdayToi);
        sundayToi = findViewById(R.id.sundayToi);
        scrollView = findViewById(R.id.scrollView);
        progressbar = findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {

    }

//    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
//    ImagePicker imagePicker = new ImagePicker();
//
//    public void onPickImage() {
//
//        Intent chooseImageIntent = imagePicker.getPickImageIntent(this);
//        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch(requestCode) {
//            case PICK_IMAGE_ID:
//                Bitmap bitmap = imagePicker.getImageFromResult(this, resultCode, data);
//                // TODO use bitmap
//                break;
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
//                break;
//        }
//    }
}
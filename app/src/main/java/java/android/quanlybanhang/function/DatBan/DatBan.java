package java.android.quanlybanhang.function.DatBan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.MainActivity;
import java.android.quanlybanhang.function.MonOrder;
import java.android.quanlybanhang.function.OrderMenu;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.Timer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatBan extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Toolbar toolbar;
    String id_ban;
    String id_khuvuc;
    String tenban;
    String ids;
    Button bnt_datngay,bnt_datgio,bnt_datgiokt,bnt_datban;
    TextView txtDate, txtTime,txttimekt,tvngayhientai;
//    TextInputLayout edttenkhachang;
    EditText edttenkhachang, editTextPhone,editTextNumber;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT_12 = "hh:mm:ss a";
    private static final String TIME_FORMAT_24 = "HH:mm:ss";
    Date date ;
    Timestamp timestamp;
    Timestamp timestamp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        tenban = intent.getStringExtra("tenban");
        actionBar.setTitle("Đặt Bàn -"+tenban+"");

        bnt_datngay = findViewById(R.id.bnt_datngay);
        bnt_datgio = findViewById(R.id.bnt_datgio);
        bnt_datgiokt = findViewById(R.id.bnt_datgiokt);
        //textview
        txtDate = findViewById(R.id.tvngaydatban);
        txtTime = findViewById(R.id.tvgiodatban);
        txttimekt= findViewById(R.id.tvgiodatbankt);
        tvngayhientai= findViewById(R.id.tvngayhientai);
        bnt_datban = findViewById(R.id.bnt_datban);
        //edittext
        edttenkhachang= findViewById(R.id.edttenkhachang);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumber = findViewById(R.id.editTextNumber);
        bnt_datgio.setOnClickListener(this);
        bnt_datngay.setOnClickListener(this);
        bnt_datgiokt.setOnClickListener(this);
        bnt_datban.setOnClickListener(this);

        tvngayhientai.setText(hamlaydate());
    }


    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
            switch (v.getId() )
            {
                case R.id.bnt_datngay:
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    break;
                case R.id.bnt_datgio:
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                    break;
                case R.id.bnt_datgiokt:
                    TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txttimekt.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog1.show();
                    break;
                case R.id.bnt_datban:
                    datBan();

                    break;
            }
    }

    public  String hamlaydate(){
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new java.util.Date());
        Log.d("datenowww",date+"");
        return date;
    }
    public String Hamlaygiohientai(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww",timestamp.getTime()+"");
        return  timestamp.getTime()+"";
    }


    public void  datBan(){
        String aa =txtTime.getText().toString()+" "+txtDate.getText().toString();
        String dd =txttimekt.getText().toString()+" "+txtDate.getText().toString();
        Log.d("hh:mm",aa);
        try {
            java.util.Date date1 = new SimpleDateFormat("hh:mm dd-MM-yyyy").parse(aa);
             timestamp = new Timestamp(date1.getTime());

            timestamp.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            java.util.Date date = new SimpleDateFormat("hh:mm dd-MM-yyyy").parse(dd);
            timestamp1 = new Timestamp(date.getTime());
            timestamp1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (edttenkhachang.getText().toString().isEmpty()){
            edttenkhachang.setError("Hãy nhập tên khách Hàng");
            edttenkhachang.requestFocus();
        }else if (editTextPhone.getText().toString().isEmpty()){
            editTextPhone.setError("Hãy nhập Sô điện thoại");
            editTextPhone.requestFocus();
        }
        else if (txtDate.getText().toString().equals("00:00:00")){
            Toast.makeText(DatBan.this,"chưa nhập ngày đặt",Toast.LENGTH_LONG).show();
        }
        else if (txtTime.getText().toString().equals("00:00:00")){
            Toast.makeText(DatBan.this,"chưa chọn giờ đặt",Toast.LENGTH_LONG).show();
        }
        else if (txttimekt.getText().toString().equals("00:00:00")){
            Toast.makeText(DatBan.this,"chưa chọn giờ kết thúc",Toast.LENGTH_LONG).show();
        }
        else if(timestamp.getTime()>timestamp1.getTime()){
            Toast.makeText(DatBan.this,"Giờ kết thúc nhỏ hơn giờ đặt",Toast.LENGTH_LONG).show();
        }
        else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("DatBan").child(id_ban + "_" + id_khuvuc).child(timestamp.getTime() + "");
            databaseReference.child("tenkhachhang").setValue(edttenkhachang.getText().toString());
            databaseReference.child("id_bk").setValue(id_ban + "_" + id_khuvuc);
            databaseReference.child("sodienthoai").setValue(editTextPhone.getText().toString());
            databaseReference.child("sotiendattruoc").setValue(editTextNumber.getText().toString());
            databaseReference.child("ngayhientai").setValue(tvngayhientai.getText().toString());
            databaseReference.child("ngaydat").setValue(txtDate.getText().toString());
            databaseReference.child("giodat").setValue(txtTime.getText().toString());
            databaseReference.child("tenban").setValue(tenban);
            databaseReference.child("gioketthuc").setValue(txttimekt.getText().toString());
            Intent intent = new Intent(DatBan.this, OrderMenu.class);
            intent.putExtra("id_ban",id_ban);
            intent.putExtra("id_khuvuc",id_khuvuc);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DatBan.this, OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
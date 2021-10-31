package java.android.quanlybanhang.function.DatBan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.android.quanlybanhang.R;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;

public class DatBan extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Toolbar toolbar;
    String id_ban;
    String id_khuvuc;
    String tenban;
    Button bnt_datngay,bnt_datgio;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

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

        bnt_datgio.setOnClickListener(this);
        bnt_datngay.setOnClickListener(this);

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
            }
    }
}
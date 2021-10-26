package java.android.quanlybanhang.function.BaoCao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.android.quanlybanhang.Common.FormatDate;
import java.android.quanlybanhang.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class BaoCaoChiSoActivity extends AppCompatActivity {

    private ValueLineChart lineChart;
    private BarChart mBarChart;
    private String ID_CUA_HANG = "Meskv6p2bkf89ferNygy5Kp1aAA3";
    private ArrayList<String> listNgayTrongTuan;
    private String ngayBatDau;
    private String ngayKetThuc;
    private ArrayList<String> listNgay;

    //Firebase
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_chi_so);

        IDLayout();
        setLineChart();
        setBarChart();
        getNgayTrongTuan();
        ngayBatDau = "01/01/2021";
        ngayKetThuc = "10/10/2021";
        listNgay = MangNgay();
        getFirebaseThu();
    }

    private void IDLayout() {
        lineChart = (ValueLineChart)findViewById(R.id.lineChart);
        mBarChart = findViewById(R.id.doanhThuNgay);
    }

    private void setLineChart() {
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(getColor(R.color.colorPrimary));

        ValueLineSeries series2 = new ValueLineSeries();
        series2.setColor(getColor(R.color.CPP));
        Random rd = new Random();

        for (int i = 0 ; i < 10; i++) {
            int number1 = rd.nextInt(50);
            int number2 = rd.nextInt(50);
            series.addPoint(new ValueLinePoint(i+"", number1));
            series2.addPoint(new ValueLinePoint(i+"", number2));
        }

        series.addPoint(new ValueLinePoint("24", 2f));
        series2.addPoint(new ValueLinePoint("1", 1f));

        lineChart.addSeries(series);
        lineChart.addSeries(series2);
        lineChart.startAnimation();
    }

    private void setBarChart () {
        mBarChart.addBar(new BarModel(2.3f, 0xFF123456));
        mBarChart.addBar(new BarModel(2.f,  0xFF343456));
        mBarChart.addBar(new BarModel(3.3f, 0xFF563456));
        mBarChart.addBar(new BarModel(1.1f, 0xFF873F56));
        mBarChart.addBar(new BarModel(2.7f, 0xFF56B7F1));
        mBarChart.addBar(new BarModel(2.f,  0xFF343456));
        mBarChart.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        mBarChart.addBar(new BarModel(4.f,  0xFF1BA4E6));

        mBarChart.startAnimation();
    }

    private void getFirebaseThu() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        FirebaseDatabase mFBInstance;
        DatabaseReference mFBDatabase;

        mFirebaseDatabase.child("CuaHangOder/"+ID_CUA_HANG+"/bienlai/thu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Double> listTienTuan = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        for (int i = 0; i < listNgay.size(); i++) {
                            if (snap.getKey().equals(listNgay.get(i))) {
                                double tongTienCuaMotNgay = 0;
                                for (DataSnapshot sn: snap.getChildren()) {
//                                    Log.d("qq", sn.child("tongthanhtoan").getValue().toString());
//                                    Log.d("qq", sn.getKey());
                                    tongTienCuaMotNgay += Double.parseDouble(sn.child("tongthanhtoan").getValue().toString());
                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getNgayTrongTuan() {
        // Get calendar set to current date and time
        Calendar c = GregorianCalendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        ngayBatDau = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        ngayKetThuc = df.format(c.getTime());
    }

    private ArrayList<String> MangNgay() {
        ArrayList<String> arrNgay = new ArrayList<String>();
        FormatDate formatDate = new FormatDate();

        int days = formatDate.truThoiGian(ngayBatDau, ngayKetThuc);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThuc);
        } catch (ParseException e) {

        }
        cal.setTime(date);

        if (days != 0) {
            arrNgay.add(ngayKetThuc.replaceAll("/", "-"));
            for (int i = 0; i < days; i++) {
                String ngay = CustomNgay(cal, -1).replaceAll("/", "-");
                arrNgay.add(ngay);
            }
        } else {
            String ngay = ngayBatDau.replaceAll("/", "-");
            arrNgay.add(ngay);
        }
        return arrNgay;
    }

    public String CustomNgay(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }
}
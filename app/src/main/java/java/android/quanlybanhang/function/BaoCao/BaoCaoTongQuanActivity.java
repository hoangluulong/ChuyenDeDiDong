package java.android.quanlybanhang.function.BaoCao;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDate;
import java.android.quanlybanhang.Model.PieTongQuan;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.DbBaoCao;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BaoCaoTongQuanActivity extends AppCompatActivity implements View.OnClickListener {

    private Locale localeVN = new Locale("vi", "VN");
    private NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    private TextView tien_tongtien, tien_nophaithu, tien_nophaitra, tien_doanhthu, tien_doanhso, tien_chuathanhtoan, tien_dathanhtoan, tien_dichvuphuthu, tien_comonhuy,
            tien_hoadononline, tien_chitieu,
            sl_doanhso, sl_chuathanhtoan, sl_dathanhtoan, sl_dichvuphuthu, sl_comonhuy, sl_hoadononline;
    private Button thoiGianLamViec, btnChiNhanh;
    private CardView cv_tongtien, cv_nophaithu, cv_nophaitra, cv_doanhthu, cv_chi, cv_doanhso, cv_chuathantoan, cv_dathanhtoan, cv_dicvuphuthu, cv_comonhuy, cv_hoadononline;
    private BottomSheetDialog bottomSheetDialog;
    private View sheetView;
    private PieChart pieChart;

    private int doanhThu = 0;
    private int chiTieu = 0;
    private int tongTien = 0;
    private int noPhaiTra = 0;
    private int noPhaiThu = 0;
    private int doanhSo = 0;
    private int chuaThanhToan = 0;
    private int dichVuPhuThu = 0;
    private int hoaDonBiHuy = 0;
    private int hoaDonOnline = 0;

    private int kieuHienThi;
    private String ngayBatDau;
    private String ngayKetThuc;
    private int id;
    private ArrayList<PieTongQuan> dsSanPham = new ArrayList<>();
    private ArrayList<String> dsNhomSanPham = new ArrayList<>();
    private ArrayList<String> listDays;
    private ArrayList<DataSnapshot> dataBienLai = new ArrayList<>();
    private ArrayList<PieTongQuan> sanPham = new ArrayList<>();

    //Firebase
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    //Database
    private DbBaoCao dataSql;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_tong_quan);
        getIDLayout();

        DatabaseSQlite();
        bieuDoSanPham();
    }

    //
    private void bieuDoSanPham() {
        ArrayList<PieEntry> pieE = new ArrayList<>();
        if (dsSanPham.size() == 0) {
            pieE.add(new PieEntry(0, "Chưa có dữ liệu"));
        } else {
            pieE.clear();
            int tongSPKhac = 0;

            ArrayList<PieTongQuan> sanPham = new ArrayList<>();
            for (int i = 0; i < dsSanPham.size(); i++) {
                if (dsSanPham.get(i).getSoLuong() > 0) {
                    sanPham.add(dsSanPham.get(i));
                }
            }

            if (sanPham.size() >= 5) {
                for (int i = 0; i < sanPham.size(); i++) {
                    if (i <= 4) {
                        pieE.add(new PieEntry(sanPham.get(i).getSoLuong(), sanPham.get(i).getName()));
                    } else {
                        tongSPKhac += sanPham.get(i).getSoLuong();
                    }
                }
                pieE.add(new PieEntry(tongSPKhac, "Khác"));

            } else if (sanPham.size() < 5) {
                for (int i = 0; i < sanPham.size(); i++) {
                    pieE.add(new PieEntry(sanPham.get(i).getSoLuong(), sanPham.get(i).getName()));
                }
            }
        }

        PieDataSet dataSet = new PieDataSet(pieE, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(22f);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("Top 5 sản phẩm bán chạy nhất");
        pieChart.animate();
        pieChart.invalidate();
    }

    //get id layout
    private void getIDLayout() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        //textview tien
        tien_tongtien = findViewById(R.id.tien_tongtien);
        tien_nophaithu = findViewById(R.id.tien_nophaithu);
        tien_nophaitra = findViewById(R.id.tien_nophaitra);
        tien_doanhthu = findViewById(R.id.tien_doanhthu);
        tien_doanhso = findViewById(R.id.tien_doanhso);
        tien_chuathanhtoan = findViewById(R.id.tien_chuathanhtoan);
        tien_dathanhtoan = findViewById(R.id.tien_dathanhtoan);
        tien_dichvuphuthu = findViewById(R.id.tien_dichvuphuthu);
        tien_comonhuy = findViewById(R.id.tien_comonhuy);
        tien_hoadononline = findViewById(R.id.tien_hoadononline);
        tien_chitieu = findViewById(R.id.tien_chitieu);

        //Cardview
        cv_tongtien = findViewById(R.id.cv_tongtien);
        cv_nophaithu = findViewById(R.id.cv_nophaithu);
        cv_nophaitra = findViewById(R.id.cv_nophaitra);
        cv_doanhthu = findViewById(R.id.cv_doanhthu);
        cv_doanhso = findViewById(R.id.cv_doanhso);
        cv_chuathantoan = findViewById(R.id.cv_chuathantoan);
        cv_dathanhtoan = findViewById(R.id.cv_dathanhtoan);
        cv_dicvuphuthu = findViewById(R.id.cv_dicvuphuthu);
        cv_comonhuy = findViewById(R.id.cv_comonhuy);
        cv_hoadononline = findViewById(R.id.cv_hoadononline);
        cv_chi = findViewById(R.id.cv_chi);

        //textview soluong
        sl_doanhso = findViewById(R.id.sl_doanhso);
        sl_chuathanhtoan = findViewById(R.id.sl_chuathanhtoan);
        sl_dathanhtoan = findViewById(R.id.sl_dathanhtoan);
        sl_dichvuphuthu = findViewById(R.id.sl_dichvuphuthu);
        sl_comonhuy = findViewById(R.id.sl_comonhuy);
        sl_hoadononline = findViewById(R.id.sl_hoadononline);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        setOnclick();
    }

    //onlcik
    private void setOnclick() {
        cv_tongtien.setOnClickListener(this);
        cv_nophaithu.setOnClickListener(this);
        cv_nophaitra.setOnClickListener(this);
        cv_doanhthu.setOnClickListener(this);
        cv_doanhso.setOnClickListener(this);
        cv_chuathantoan.setOnClickListener(this);
        cv_dathanhtoan.setOnClickListener(this);
        cv_dicvuphuthu.setOnClickListener(this);
        cv_comonhuy.setOnClickListener(this);
        cv_hoadononline.setOnClickListener(this);

        //Dialog
        bottomSheetDialog = new BottomSheetDialog(BaoCaoTongQuanActivity.this, R.style.BottomSheetBaoCao);
        sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bao_cao_sheet_dialog, (ViewGroup) findViewById(R.id.sheet_baocao));
        sheetView.findViewById(R.id.viewTongQuan).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.viewChiSo).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.viewBanChay).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.viewHoaDon).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.viewKho).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.btnBaoCaoSetting).setOnClickListener(BaoCaoTongQuanActivity.this);
        sheetView.findViewById(R.id.btnBaoCaoClose).setOnClickListener(BaoCaoTongQuanActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.cv_tongtien:
                Toast.makeText(this, "Tổng tiền", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_nophaithu:
                Toast.makeText(this, "Nợ phải thu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_nophaitra:
                Toast.makeText(this, "nợ phải trả", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_doanhthu:
                Toast.makeText(this, "Doanh thu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_chi:
                Toast.makeText(this, "Chi tiêu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_doanhso:
                Toast.makeText(this, "Doanh số", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_chuathantoan:
                Toast.makeText(this, "Chưa thanh toán", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_dathanhtoan:
                Toast.makeText(this, "Đã thanh toán", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_dicvuphuthu:
                Toast.makeText(this, "Dịch vụ phụ thu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_comonhuy:
                Toast.makeText(this, "Có món hủy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_hoadononline:
                Toast.makeText(this, "Hóa đơn online", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewTongQuan:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo tổng quan", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
                break;
            case R.id.viewChiSo:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo chỉ số", Toast.LENGTH_LONG).show();
//                intent = new Intent(BaoCaoTongQuanActivity.this, ChiSoKinhDoanhActivity.class);
//                startActivity(intent);
                bottomSheetDialog.dismiss();
                break;
            case R.id.viewBanChay:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo sản phẩm bán chạy", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
//                intent = new Intent(BaoCaoTongQuanActivity.this, TopSanPhamBanChayActivity.class);
//                startActivity(intent);
                break;
            case R.id.viewHoaDon:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo hóa đơn", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
                break;
            case R.id.viewKho:
                intent = new Intent(BaoCaoTongQuanActivity.this, BaoCaoKhoActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
                break;
            case R.id.btnBaoCaoSetting:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Cài đặt", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
                break;
            case R.id.btnBaoCaoClose:
                bottomSheetDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_baobao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_baocao:
                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
                break;
            case android.R.id.home:
//                Intent intent = new Intent(BaoCaoTongQuanActivity.this, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_down_animate, R.anim.slide_out_down_animate);
                finish();
                break;
        }
        return true;
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

    //get date today +-n
    private String CustomNgay2(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);
        Calendar cal = calendar;
        cal.add(Calendar.DAY_OF_YEAR, amount);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }


    /**
     * SQLite
     * gọi hàm MangNgay, CustomNgay2
     */
    private void DatabaseSQlite() {
        // Tạo database
        dataSql = new DbBaoCao(this, "app_database.sqlite", null, 1);
        dataSql.QueryData("CREATE TABLE IF NOT EXISTS KieuHienThiBaoCao(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NgayBatDau VARCHAR(200), " +
                "NgayKetThuc VARCHAR(200), " +
                "KieuHienThi INTEGER DEFAULT 1);");


        //dataSql.QueryData("UPDATE CongViec SET NgayBatDau = 'Long' WHERE Id = '5'");

        Cursor dataHienThiBaoCao = dataSql.GetData("SELECT * FROM KieuHienThiBaoCao");

        if (dataHienThiBaoCao.getCount() > 0) {
            while (dataHienThiBaoCao.moveToNext()) {
                id = dataHienThiBaoCao.getInt(0);
                ngayBatDau = dataHienThiBaoCao.getString(1);
                ngayKetThuc = dataHienThiBaoCao.getString(2);
                kieuHienThi = dataHienThiBaoCao.getInt(3);
            }
        } else {
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.clear();
            Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
            calendar.setTimeInMillis(toDay);
            String homNay = CustomNgay2(calendar, 0);
            ngayBatDau = homNay;
            ngayKetThuc = homNay;

            dataSql.QueryData("INSERT INTO KieuHienThiBaoCao VALUES(null, '" + homNay + "', '" + homNay + "', 1)");
        }

        listDays = MangNgay();
        getDataDSSanPham();
        getDataBienLai();
        SetDuLieu();
    }

    /**
     * - get data tổng tiền
     * - get data hóa đơn
     * - get data danh sach sản phẩm
     * Data
     */
    private void getDataDSSanPham() {
        FirebaseDatabase mFBInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFBDatabase = mFBInstance.getReference();

        mFBDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanpham");
        mFBDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    dsNhomSanPham.add(snap.getKey());
                    for (DataSnapshot s : snap.getChildren()) {
                        dsSanPham.add(new PieTongQuan(s.child("nameProduct").getValue() + "", 0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataBienLai() {
        for (String st : listDays) {
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot a : dataSnapshot.getChildren()) {
                            Log.d("qqq", a.toString());
                            dataBienLai.add(a);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    throw databaseError.toException();
                }

            });
        }
    }

    private void SetDuLieu() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //xoa linearlayout
                if (dataBienLai.size() != 0 || dataBienLai != null) {

                    int slDaThanhToan = 0;
                    double tienDTT = 0;
                    double tienDoanhThu = 0;
                    int slChuaThanhToan = 0;
                    int tienCTT = 0;

                    ArrayList<DataSnapshot> listSP = new ArrayList<>();

                    for (DataSnapshot a : dataBienLai) {
                        tienDoanhThu += Double.parseDouble(a.child("tongthanhtoan").getValue() + "");

                        listSP.add(a.child("sanpham"));

                        if (Integer.parseInt(a.child("status").getValue() + "") == 1) {
                            slDaThanhToan++;
                            tienDTT += Double.parseDouble(a.child("tongthanhtoan").getValue() + "");
                        } else if (Integer.parseInt(a.child("status").getValue() + "") == 2) {
                            slChuaThanhToan++;
                            tienCTT += Double.parseDouble(a.child("tongthanhtoan").getValue() + "");
                        }
                    }

                    tien_doanhthu.setText(formatStr(tienDTT));

                    tien_dathanhtoan.setText(formatStr(tienDTT));
                    sl_dathanhtoan.setText(slDaThanhToan+"");

                    tien_doanhso.setText(formatStr(tienDTT));
                    sl_doanhso.setText(slDaThanhToan+"");

                    tien_chuathanhtoan.setText(formatStr(tienCTT));
                    sl_chuathanhtoan.setText(slChuaThanhToan+"");

                    for (DataSnapshot sp : listSP) {
                        for (DataSnapshot s : sp.getChildren()) {
                            sanPham.add(new PieTongQuan(s.child("tensanpham").getValue().toString(), Integer.parseInt(s.child("soluong").getValue().toString())));
                        }
                    }

                    for (int i = 0; i < dsSanPham.size(); i++) {
                        for (int j = 0; j < sanPham.size(); j++) {
                            if (dsSanPham.get(i).getName().equals(sanPham.get(j).getName())) {
                                dsSanPham.get(i).setSoLuong(dsSanPham.get(i).getSoLuong() + sanPham.get(j).getSoLuong());
                            }
                        }
                    }

                    dsSanPham.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());
                    bieuDoSanPham();
                } else {
                    SetDuLieu();
                }
            }
        }, 3000);
    }

    public String formatStr(double val) {
        return String.format(Locale.US, "%,.2f", val);
    }

    private void guiDuLieu() {
        int dem = 4;
        for (String a : listDays) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_ban").setValue("b2");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_khuvuc").setValue("k2");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_khachhang").setValue("kh2");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_nhanvien").setValue("nv2");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/id_khuyenmai").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/status").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/value").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/phiphu/id_" + dem + "/mieuta").setValue("null");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/phiphu/id_" + dem + "/tienphi").setValue("null");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/tensanpham").setValue("Readbull");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/nhomsanpham").setValue("Nước có ga");
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/giamgia").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/giatien").setValue(20000);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/soluong").setValue(2);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/thanhtien").setValue(40000);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/status").setValue(1);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/tongthanhtoan").setValue(40000);
            mFirebaseDatabase.child("CuaHangOder/XDtrGrfpqvZCk5FzyqP896twSwK2/bienlai/thu/" + a + "/" + timestamp.getTime() + "/tongtien").setValue(40000);
            dem++;
        }
    }

}